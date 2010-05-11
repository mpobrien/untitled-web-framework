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

	private final Properties propsFile;
	private List<Package> packages;

	public AppConfigModule(Package... pkgs){//{{{
		// Load settings from the default location
		Properties p = new Properties();
		try{
			p = loadProperties();
		}catch(Exception e){
			//TODO: fix
			e.printStackTrace();
		}
		this.propsFile = p;
		this.packages = Lists.newArrayList(pkgs);
	}//}}}

	public AppConfigModule(File propertiesFile, Package... pkgs){//{{{
		this.propsFile = new Properties();
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(propertiesFile);
			this.propsFile.load(fis);    
		}catch(Exception e){
			//TODO: fix
			e.printStackTrace();
		}finally{
			if( fis != null ){
				try{ fis.close(); }catch(Exception e){}
			}
		}
		this.packages = Lists.newArrayList(pkgs);
	}//}}}

	public AppConfigModule(String propertiesFile){//{{{
		this( new File( propertiesFile ) );
	}//}}}

	public void configure() {                           //{{{
		//Template Settings
		Configuration config = new Configuration();
		try{
			config.setDirectoryForTemplateLoading( new File( this.propsFile.getProperty("template.dir" ) ) );
		}catch(Exception e){
			e.printStackTrace();
			log.warn("couldn't load template directory");
		}
		bind(Configuration.class).toInstance( config );

		//Database settings
		Names.bindProperties( binder(), Maps.fromProperties(this.propsFile) );
		bind(ConnectionProvider.class).to(SimpleConnectionProvider.class);
		
		String contextProcessorsList = this.propsFile.getProperty("contextprocessors.classes");
		bind( ContextProcessorChain.class ).toInstance( new ContextProcessorChain(contextProcessorsList) );

		// Url Mappings
		bind(new TypeLiteral<List<Package>>() {}).annotatedWith( Names.named("controller.packages") ).toInstance( this.packages );

		bind(ReverseMapper.class).to(AnnotatedUrlMapper.class);

	}//}}}

	public ImmutableMap<String,String> settings(){
		return Maps.fromProperties(this.propsFile);
	}

	private static Properties loadProperties() throws Exception {//{{{
		Properties properties = new Properties();
		ClassLoader loader = AppConfigModule.class.getClassLoader();
		URL url = loader.getResource("settings.properties");
		if( url == null ){
			log.warn("couldn't find the settings.properties file!");
			return properties;
		}
		properties.load(url.openStream());
		return properties;
	}//}}}

}
