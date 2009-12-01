package com.mob.web;
import com.google.inject.matcher.Matcher;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;
import org.apache.log4j.*;

class Classes {

	private final Matcher<? super Class<?>> matcher;
	private final Logger log = Logger.getLogger(Classes.class.getName());

	private Classes(Matcher<? super Class<?>> matcher) {
		this.matcher = matcher;
	}

  /**
   * @param pack A list of packages to scan, recursively.
   * @return A set of all the classes inside this package
   * @throws PackageScanFailedException Thrown when error reading from disk or jar.
   * @throws IllegalStateException      Thrown when something very odd is
   *                                    happening with classloaders.
   */
  	public Set<Class<?>> in(Package pack) {//{{{
		String packageName = pack.getName();
		String packageOnly = pack.getName();
		final boolean recursive = true;
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		String packageDirName = packageOnly.replace('.', '/');
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
		} catch (IOException e) {
		  throw new RuntimeException( "Could not read from package directory: " + packageDirName, e);
		}

		while (dirs.hasMoreElements()) {
			URL url = dirs.nextElement();
			String protocol = url.getProtocol();
			if ("file".equals(protocol)) {
				try {
					findClassesInDirPackage(packageOnly, URLDecoder.decode(url.getFile(), "UTF-8"), recursive, classes);
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException("Could not read from file: " + url, e);
				}
			} else if ("jar".equals(protocol)) {
				JarFile jar;
				try {
					jar = ((JarURLConnection) url.openConnection()).getJarFile();
				} catch (IOException e) {
					throw new RuntimeException("Could not read from jar url: " + url, e);
				}

				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry entry = entries.nextElement();
					String name = entry.getName();
					if (name.charAt(0) == '/') {
						name = name.substring(1);
					}
					if (name.startsWith(packageDirName)) {
						int idx = name.lastIndexOf('/');
						if (idx != -1) {
							packageName = name.substring(0, idx).replace('/', '.');
						}

						if ((idx != -1) || recursive) {
							//it's not inside a deeper dir
							if (name.endsWith(".class") && !entry.isDirectory()) {
								String className = name.substring(packageName.length() + 1, name.length() - 6);
								//include this class in our results
								add(packageName, classes, className);
							}
						}
					}
				}
			}
		}
		return classes;
 	}//}}}

	private void add(String packageName, Set<Class<?>> classes, String className) {
		Class<?> clazz;
		try {
			clazz = Class.forName(packageName + '.' + className);
		} catch (ClassNotFoundException e) {
			log.error("A class discovered by the scanner could not be found by the ClassLoader, " +
					   "something very odd has happened with the classloading (see root cause): " +
					   e.toString() );

			throw new IllegalStateException( "A class discovered by the scanner could not be found by the ClassLoader", e);
		}
		if (matcher.matches(clazz))
			classes.add(clazz);
	}

	private void findClassesInDirPackage(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes) {//{{{
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) return;

		File[] dirfiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});

		for (File file : dirfiles) {
			if (file.isDirectory()) {
				findClassesInDirPackage(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				//include class
				add(packageName, classes, className);
			}
		}
	}//}}}

	public static Classes matching(Matcher<? super Class<?>> matcher) {//{{{
		return new Classes(matcher);
	}//}}}

}

