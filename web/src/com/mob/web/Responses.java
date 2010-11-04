package com.mob.web;
// import freemarker.core.*;
// import freemarker.template.*;
import org.mob.templ8.*;
import java.util.Map;
import java.util.Iterator;
import com.google.inject.Inject;
import java.net.*;
import java.io.UnsupportedEncodingException;

public class Responses{

	private TemplateLoader templateLoader;
	//private Configuration config;
	private ReverseMapper reverseMapper;

	@Inject
	public Responses(TemplateLoader templateLoader, ReverseMapper reverseMapper){//{{{
		this.templateLoader = templateLoader;
		this.reverseMapper = reverseMapper;
	}//}}}

	public static RedirectResponse redirect(String redirectUrl){//{{{
		return new RedirectResponse( redirectUrl );
	}//}}}

	public TemplateResponse render(String templateName, Map context){//{{{
		try{
			Template template = templateLoader.getTemplate(templateName);  
			return new TemplateResponse( template, context);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}//}}}

	public TemplateResponse render(String templateName){//{{{
		try{
			Template template = templateLoader.getTemplate(templateName);  
			return new TemplateResponse( template );
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}//}}}

	public String reverseUrl(Class<? extends Controller> c1, String... groupArgs){//{{{
		return this.reverseMapper.reverseMatch(c1, groupArgs);
	}//}}}

	public String reverseUrl(Class<? extends Controller> c1, Map<String,String> urlArgs, String... groupArgs){//{{{
		String baseUrl = reverseMapper.reverseMatch(c1, groupArgs);
		return getFullUrl(baseUrl, urlArgs);
	}//}}}

	private static String getFullUrl(String baseUrl, Map<String,String> params){//{{{
        StringBuilder sb = new StringBuilder(baseUrl);
		if( params == null || params.isEmpty() ) return sb.toString();
    	Iterator it = params.entrySet().iterator();
		char separator = (baseUrl.indexOf('?') > 0) ? '&' : '?';

    	while (it.hasNext()) {
			sb.append( separator );
        	Map.Entry<String,String> pair = (Map.Entry<String,String>)it.next();
			String name = pair.getKey();
			String value = pair.getValue();
 			try{
				sb.append(URLEncoder.encode(name, "UTF-8"));
				if (value != null) {
					sb.append('=');
					sb.append( URLEncoder.encode(value, "UTF-8") );
				}
 			}catch(UnsupportedEncodingException e){
                 throw new IllegalStateException("No UTF-8"); // not gonna happen
 			}
			separator = '&';
    	}
		return sb.toString();
	}//}}}

	public RedirectResponse reverseRedirect( Class<? extends Controller> c1, Map<String,String> urlArgs, String... groupArgs){//{{{
		String redirUrl = reverseUrl( c1, urlArgs, groupArgs);
		return redirect( redirUrl );
	}//}}}

	public RedirectResponse reverseRedirect( Class<? extends Controller> c1, String... groupArgs){//{{{
		return reverseRedirect( c1, null, groupArgs);
	}//}}}

	public JsonResponse json(Object o){
		return new JsonResponse( o );
	}

}
