package com.mob.web;
import com.mob.orm.ConnectionProvider;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.inject.*;
import com.google.inject.name.Names;
import freemarker.core.*;
import freemarker.template.*;
import org.apache.log4j.*;
import java.util.Properties;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import java.io.*;

public class AppConfigModule extends AbstractModule {
	private static Logger log = Logger.getLogger(AppConfigModule.class);

	private static final String TEMPLATE_PREFIX = "template.";
	private static final String DB_PREFIX = "db.";
	private static final String URLMAP_KEY = "controller.packages";
	private static final String SERVERPORT_KEY = "server.port";
	private static final String SERVERDATA_KEY = "server.data";
	private final Properties propsFile;

	public AppConfigModule(){
		// Load settings from the default location
		Properties p = new Properties();
		try{
			p = loadProperties();
		}catch(Exception e){
			//TODO: fix
			e.printStackTrace();
		}
		this.propsFile = p;
	}

	public AppConfigModule(File propertiesFile){
		this.propsFile = new Properties();
		FileInputStream fis = null;
		try{
			this.propsFile.load(fis);    
		}catch(Exception e){
			//TODO: fix
			e.printStackTrace();
		}finally{
			if( fis != null ){
				try{ fis.close(); }catch(Exception e){}
			}
		}
	}

	public AppConfigModule(String propertiesFile){
		this( new File( propertiesFile ) );
	}

	public void configure() {                           
		//Load template settings
		Map<String,String> templateSettings = Maps.filterKeys( 
												Maps.fromProperties(this.propsFile),
												C.prefixFilter( TEMPLATE_PREFIX ) ); 
		Names.bindProperties( binder(), templateSettings );
		Configuration config = new Configuration();
		bind(Configuration.class).toInstance( config );

		//Load Database settings
		Map<String,String> dbSettings = Maps.filterKeys(
											Maps.fromProperties(this.propsFile), 
											C.prefixFilter( DB_PREFIX ) );
		Names.bindProperties( binder(), dbSettings );
		bind(ConnectionProvider.class).to(SimpleConnectionProvider.class);

		//Load Url mapping settings
		String controllerPackages = propsFile.getProperty( URLMAP_KEY );
		List<Package> packages = loadPackages( parsePackageList( controllerPackages ) );
		bind(new TypeLiteral<List<Package>>() {}).annotatedWith( Names.named("controller.packages") ).toInstance( packages );
		
		

		//Server settings:
		bind(String.class).annotatedWith(Names.named("server.port")).toInstance(this.propsFile.getProperty(SERVERPORT_KEY));
		bind(String.class).annotatedWith(Names.named("server.data")).toInstance(this.propsFile.getProperty(SERVERDATA_KEY));


	}

	private static Properties loadProperties() throws Exception {
		Properties properties = new Properties();
		ClassLoader loader = MyServletContextListener.class.getClassLoader();
		URL url = loader.getResource("settings.properties");
		properties.load(url.openStream());
		return properties;
	}

    private static List<String> parsePackageList( String commaDelimitedPackages){
		if( commaDelimitedPackages == null || commaDelimitedPackages.trim().equals("") ){
			return new ArrayList<String>(0);
		}else{
			String[] packages = commaDelimitedPackages.split(",");
			if( packages == null || packages.length == 0 ){
				return new ArrayList<String>(0);
			}else{
				List<String> returnVal = new ArrayList<String>();
				for( int i=0; i < packages.length; i++ ){
					returnVal.add( packages[ i ] .trim() );
				}
				log.info(returnVal);
				return returnVal;
			}
		}
	}

	private static List<Package> loadPackages( List<String> packageNames ){
		List<Package> packages = new ArrayList<Package>();
		for( String packageName : packageNames ){
			try{
				log.info( AppConfigModule.class.getClassLoader().getResources(packageName + ".*") );
			}catch(Exception e){
				//TODO fix
				e.printStackTrace();
			}
			Package p = Package.getPackage( packageName );
			if( p != null ){
				packages.add( p );
			}else{
				log.warn("Couldn't find package: " + packageName );
			}
		}
		return packages;
	}

}
