package com.mob.web;

import com.google.inject.*;
//import com.google.sitebricks.routing.RoutingDispatcher;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.apache.log4j.*;

@Singleton
class SiteFilter implements Filter {
	private static Logger log = Logger.getLogger(SiteFilter.class);
	private final UrlMapper mapper;
	private final Injector injector;

  	@Inject
	public SiteFilter(UrlMapper mapper, Injector injector){
		this.mapper = mapper;
		this.injector = injector;
	}

     public void init(FilterConfig filterConfig) throws ServletException {
     }

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String requestUri = request.getRequestURI();
		log.debug(request.getMethod() + ": " + requestUri );
		ControllerRequest controlRequest = this.mapper.matchUrl( requestUri ); 
		if( controlRequest != null ){
			Class controllerClass = controlRequest.getControllerClass();
			List<String> args = controlRequest.getArgs();
			Controller controller = (Controller)injector.getInstance( controllerClass );
			controller.setArgs( args );
			controller.processArgs();
			controller.setContext(); //TODO set up contexts here

			WebResponse result;
			if( request.getMethod().equals("GET") ){
				result = controller.get( request );
			}else if( request.getMethod().equals("POST") ){
				result = controller.post( request );
			}else{
				result = null; // TODO: unsupported method?
			}
	 
			if( result != null ){
				result.writeResponse(request, response);
			}
		}
	}

    public void destroy() { }
}

