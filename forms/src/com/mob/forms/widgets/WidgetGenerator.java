package com.mob.forms.widgets;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import com.mob.forms.*;
import com.google.common.collect.*;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

public class WidgetGenerator{

	public static Widget hidden(final AbstractFormField field){//{{{
		return new Widget(){

			public String getHtml(){
				return "<input type=\"hidden\" name=\"" + escapeHtml(field.getName()) + "\" value=\"" + escapeHtml(field.getRawValue()) + "\"/>" + getErrorsHtml(field.getErrors());
			}

			public String getLabel(){
				return basicLabel(field);
			}
			

		};
	}//}}}

	public static Widget checkbox(final AbstractFormField field){//{{{
		return new Widget(){
			public String getHtml(){
				boolean checked = false;
				if( field instanceof BooleanField) checked = ((BooleanField)field).getValue();
				return "<input type=\"checkbox\" name=\"" + escapeHtml(field.getName()) + "\" value=\"on\"" + (checked?" checked":"") + "/>" + getErrorsHtml(field.getErrors()); 
			}

			public String getLabel(){
				return basicLabel(field);
			}

		};
	}//}}}

	public static Widget text(final AbstractFormField field){//{{{ 
		return new Widget(){ 
			public String getHtml(){ 
				return "<input type=\"text\" name=\"" + escapeHtml(field.getName()) + "\" value=\"" + escapeHtml(field.getRawValue() + "") + "\"/>" + getErrorsHtml(field.getErrors());  
			} 
			
			public String getLabel(){ 
				return basicLabel(field); 
			}
		};
	}//}}}
	
	public static Widget password(final AbstractFormField field){//{{{
		return new Widget(){
			public String getHtml(){
				// Don't repopulate with text
				//return "<input type=\"password\" name=\"" + escapeHtml(field.getName()) + "\" value=\"" + escapeHtml(field.getRawValue()) + "\"/>"  + getErrorsHtml(field.getErrors());  
				return "<input type=\"password\" name=\"" + escapeHtml(field.getName()) + "\" value=\"\"/>"  + getErrorsHtml(field.getErrors());  
			}

			public String getLabel(){
				return "<label for=\"" + escapeHtml(field.getName()) + "\">" + escapeHtml(getNiceName(field.getName())) + "</label>";
			}
		};
	}//}}}

	public static Widget textArea(final AbstractFormField field){//{{{
		return new Widget(){
			public String getHtml(){
				return "<textarea name=\"" + escapeHtml(field.getName()) + "\">" + escapeHtml(field.getRawValue()) + "</textarea>" + getErrorsHtml(field.getErrors());   
			}

			public String getLabel(){
				return "<label for=\"" + escapeHtml(field.getName()) + "\">" + escapeHtml(getNiceName(field.getName())) + "</label>";
			}
		};
	}//}}}

	public static String getNiceName(String origName){//{{{
		String output = origName + "";
		output = output.replaceAll("_", " ");
		String[] words = output.split(" ");
		if( words == null || words.length == 0 ){
			return output;
		}else{
			StringBuilder fullOutput = new StringBuilder("");
			for( int i=0; i<words.length; i++ ){
				String word = words[i];
				if( word == null || word.trim().length() == 0){
					continue;
				}else{
					fullOutput.append( word.substring(0,1).toUpperCase() + word.substring(1) );
					if( i != words.length - 1 ) {
						fullOutput.append(" ");
					}
				}
			}
			return fullOutput.toString();
		}
	}//}}}

	public static String basicLabel(final AbstractFormField field){//{{{
		return "<label for=\"" + escapeHtml(field.getName()) + "\">" + escapeHtml(getNiceName(field.getName())) + "</label>";
	}//}}}

	public static Widget selectOptions(final AbstractMultiFormField field){//{{{
		return new Widget(){
			public String getHtml(){
				StringBuilder sb = new StringBuilder("<select name=\"" + escapeHtml(field.getName()) + "\">\n");
				Iterator<MultiOption> options = field.getHtmlOptions();

				while( options.hasNext() ){
					MultiOption option = options.next();
					sb.append( option.getOptionHtml() ).append("\n");
				}

// TODO: sucks that this won't work, but we can't use the parameterized type here.
// 				for(T option : field.getOptions() ){
// 					MultiOption htmlOpt = new MultiOption( field.getFormVal(option),
// 					                                       field.getFormName(option),
// 					                                       field.getValue()!=null && field.getValue() == option );
// 					sb.append( htmlOpt.getOptionHtml() );
// 				}
				sb.append("</select>\n");
				return sb.toString();
			}

			public String getLabel(){
				return basicLabel(field);
			}
		};
	}
//}}}

	public static String getErrorsHtml(Set<String> errors){
		if (errors == null || errors.isEmpty() ) return "";
		StringBuilder result = new StringBuilder( "<ul class=\"errorlist\">" );
		for( String error : errors ){
			result.append("<li class=\"error\">" + error + "</li>");
		}
		result.append("</ul>");
		return result.toString();
	}

}
