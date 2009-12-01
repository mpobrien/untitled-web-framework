package com.mob.web;
import freemarker.core.*;
import freemarker.template.*;
import java.util.Map;
import com.google.inject.Inject;

public class Responses{

	private Configuration config;

	@Inject
	public Responses(Configuration config){//{{{
		this.config = config;
		this.config.setObjectWrapper( new DefaultObjectWrapper() );
	}//}}}

	public static RedirectResponse redirect(String redirectUrl){//{{{
		return new RedirectResponse( redirectUrl );
	}//}}}

	public TemplateResponse render(String templateName, Map context){//{{{
		try{
			Template template = config.getTemplate(templateName);  
			return new TemplateResponse( template, context);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}//}}}

	public TemplateResponse render(String templateName){//{{{
		try{
			Template template = config.getTemplate(templateName);  
			return new TemplateResponse( template );
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}//}}}

}
