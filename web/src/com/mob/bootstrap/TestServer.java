package com.mob.bootstrap;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.webapp.*;
import org.eclipse.jetty.io.bio.*;
import org.eclipse.jetty.server.bio.*;
import com.google.inject.*;
import com.google.inject.name.*;

public class TestServer {

    private static final Server SERVER = new Server();

	private final String port;
	private final String webapp;

	@Inject
	public TestServer( @Named("server.port") String port, @Named("server.data") String webapp ){
		this.port = port;
		this.webapp = webapp;
	}

	public TestServer(){
		this.port = "8080";
		this.webapp = "WebContent";
	}

    public void launch() {
		//TODO
        WebAppContext app = new WebAppContext();
        app.setContextPath("/");
        app.setWar(webapp);
        // Avoid the taglib configuration because its a PITA if you don't have a net connection
        app.setConfigurationClasses(new String[] { WebInfConfiguration.class.getName(), WebXmlConfiguration.class.getName() });
        app.setParentLoaderPriority(true);
 
        // We explicitly use the SocketConnector because the SelectChannelConnector locks files
        Connector connector = new SocketConnector();
        connector.setPort(Integer.parseInt(System.getProperty("jetty.port", this.port )));
        connector.setMaxIdleTime(60000);
 
        TestServer.SERVER.setConnectors(new Connector[] { connector });
        TestServer.SERVER.setHandler( app );
        TestServer.SERVER.setAttribute("org.mortbay.jetty.Request.maxFormContentSize", 0);
        TestServer.SERVER.setStopAtShutdown(true);
 
        try {
            TestServer.SERVER.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
