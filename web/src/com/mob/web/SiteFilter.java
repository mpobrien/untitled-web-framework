package com.mob.web;

import com.google.inject.*;
//import com.google.sitebricks.routing.RoutingDispatcher;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.apache.log4j.*;
import com.google.common.collect.*;

@Singleton
public class SiteFilter implements Filter {
	private static Logger log = Logger.getLogger(SiteFilter.class);
	private final UrlMapper mapper;
	private final Injector injector;
	private final ContextProcessorChain contextProcessorChain;

  	@Inject
	public SiteFilter(UrlMapper mapper, Injector injector, ContextProcessorChain contexts){
		this.mapper = mapper;
		this.injector = injector;
		this.contextProcessorChain = contexts;
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

			for( Class<? extends ContextProcessor> contextClass : this.contextProcessorChain ){
				ContextProcessor cp = (ContextProcessor)injector.getInstance(contextClass);
				cp.process(request, response);
			}

			Class controllerClass = controlRequest.getControllerClass();
			List<String> args = controlRequest.getArgs();

			Controller controller = (Controller)injector.getInstance( controllerClass );
			controller.setArgs( args );
			controller.preprocess( request, response );

			WebResponse result;
			if( request.getMethod().equals("GET") ){
				result = controller.get( request, response );
			}else if( request.getMethod().equals("POST") ){
				result = controller.post( request, response );
			}else{
				result = null; // TODO: unsupported method? log an error?
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

