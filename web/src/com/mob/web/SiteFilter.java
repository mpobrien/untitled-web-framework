package com.mob.web;

import com.google.inject.*;
//import com.google.sitebricks.routing.RoutingDispatcher;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.apache.log4j.*;

@Singleton
public class SiteFilter implements Filter {
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
		long startTime = System.currentTimeMillis();
		ControllerRequest controlRequest = this.mapper.matchUrl( requestUri ); 
		boolean continueFilter = true;
		if( controlRequest != null ){
			Class controllerClass = controlRequest.getControllerClass();
			List<String> args = controlRequest.getArgs();
			Controller controller = (Controller)injector.getInstance( controllerClass );
			controller.setArgs( args );
			controller.preprocess( request, response );
			controller.setContext(); //TODO set up contexts here

			WebResponse result;
			if( request.getMethod().equals("GET") ){
				result = controller.get( request, response );
			}else if( request.getMethod().equals("POST") ){
				result = controller.post( request, response );
			}else{
				result = null; // TODO: unsupported method?
			}
	 
			if( result != null ){
				continueFilter = result.writeResponse(request, response);
			}
		}
		if( continueFilter ) filterChain.doFilter(request, response);
		long endTime = System.currentTimeMillis();
		log.info(request.getMethod() + ": " + requestUri + " rendered in " + (endTime-startTime) + "ms" );
	}

    public void destroy() { }
}

