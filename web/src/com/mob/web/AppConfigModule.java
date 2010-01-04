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
	private List<Package> packages;

	public AppConfigModule(Package... pkgs){
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
	}

	public AppConfigModule(File propertiesFile, Package... pkgs){
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
	}

	public AppConfigModule(String propertiesFile){
		this( new File( propertiesFile ) );
	}

	public void configure() {                           

		//Template Settings//////////
		Configuration config = new Configuration();
		try{
			config.setDirectoryForTemplateLoading( new File( this.propsFile.getProperty("template.dir" ) ) );
		}catch(Exception e){
			e.printStackTrace();
			log.warn("couldn't load template directory");
		}
		bind(Configuration.class).toInstance( config );

		//Database settings/////////////
// 		Map<String,String> dbSettings = Maps.filterKeys( Maps.fromProperties(this.propsFile), C.prefixFilter( DB_PREFIX ) );
		Names.bindProperties( binder(), Maps.fromProperties(this.propsFile) );
		bind(ConnectionProvider.class).to(SimpleConnectionProvider.class);

// 		//Server settings:
// 		bind(String.class).annotatedWith(Names.named("server.port")).toInstance(this.propsFile.getProperty(SERVERPORT_KEY));
// 		bind(String.class).annotatedWith(Names.named("server.data")).toInstance(this.propsFile.getProperty(SERVERDATA_KEY));
// 
		// Url Mappings
		bind(new TypeLiteral<List<Package>>() {}).annotatedWith( Names.named("controller.packages") ).toInstance( this.packages );

		bind(ReverseMapper.class).to(AnnotatedUrlMapper.class);

	}

	public ImmutableMap<String,String> settings(){
		return Maps.fromProperties(this.propsFile);
	}

	private static Properties loadProperties() throws Exception {
		Properties properties = new Properties();
		ClassLoader loader = MyServletContextListener.class.getClassLoader();
		URL url = loader.getResource("settings.properties");
		if( url == null ){
			log.warn("couldn't find the settings.properties file!");
			return properties;
		}
		properties.load(url.openStream());
		return properties;
	}

}
