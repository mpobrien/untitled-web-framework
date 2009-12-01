package com.mob.bootstrap;
import com.mob.web.*;
import org.mortbay.jetty.*;
import org.mortbay.jetty.webapp.*;
import org.mortbay.jetty.bio.*;
import com.google.inject.*;
import com.google.inject.name.Names;

public class Jetty {
    private static final Server SERVER = new Server();
 
	public static void main(String[] args) {
		Injector injector;
		if( args.length > 0 ){
			injector = Guice.createInjector( new AppConfigModule( args[0] ) );
		}else{
			injector = Guice.createInjector( new AppConfigModule() );
		}
		TestServer server = injector.getInstance( TestServer.class );
		server.launch();
	}
 
}
