package com.mob.web;
import freemarker.core.*;
import freemarker.template.*;
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import org.apache.log4j.*;

public class TemplateResponse implements WebResponse{
	private static Logger log = Logger.getLogger(TemplateResponse.class);
	
	private final Template template;
	private final Map context;

	public TemplateResponse(Template template, Map context){
		this.template = template;
		this.context = context;
	}

	public TemplateResponse(Template template){
		this.template = template;
		this.context = new HashMap();;
	}

	public boolean writeResponse(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Writer out = new OutputStreamWriter(System.out);
		try{
			this.template.process(this.context, response.getWriter());
		}catch(TemplateException te){
			//TODO
			log.error("something went wrong", te);
		}
		return false;
	}

}

  
