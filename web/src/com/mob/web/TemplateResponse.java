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
	private String encoding = "utf-8";
	private String outCode = "utf-8";

	public TemplateResponse(Template template, Map context){
		this.template = template;
		this.context = context;
	}

	public TemplateResponse(Template template){
		this.template = template;
		this.context = new HashMap();;
	}

	public void setCharEncoding(String encoding){
		this.encoding = encoding;
	}

	public void setOutputEncoding(String outCode){
		this.outCode = outCode;
	}


	public boolean writeResponse(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try{
			response.setContentType("text/html");
			response.setCharacterEncoding(this.encoding);

			OutputStreamWriter w = new OutputStreamWriter(response.getOutputStream(), this.outCode);
			template.process(context, w);
			//w.write(d.get("conditional_we"));
			w.flush();
			return false;      
// 			Writer w = new OutputStreamWriter(response.getOutputStream());
// 			Environment env = this.template.createProcessingEnvironment(context, w);
// 			env.process();  
		}catch(TemplateException te){
			//TODO
			log.error("something went wrong", te);
		}
		return false;
	}

}

  
