package com.mob.forms.widgets;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import com.mob.forms.*;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

public class WidgetGenerator{

	public static Widget hidden(final AbstractFormField field){//{{{
		return new Widget(){

			public String getHtml(){
				return "<input type=\"hidden\" name=\"" + escapeHtml(field.getName()) + "\" value=\"" + escapeHtml(field.getRawValue()) + "\"/>";
			}

			public String getLabel(){
				return "";
			}

		};
	}//}}}

	public static Widget text(final AbstractFormField field){//{{{
		return new Widget(){
			public String getHtml(){
				return "<input type=\"text\" name=\"" + escapeHtml(field.getName()) + "\" value=\"" + escapeHtml(field.getRawValue()) + "\"/>";
			}

			public String getLabel(){
				return "<label for=\"" + escapeHtml(field.getName()) + "\">" + escapeHtml(getNiceName(field.getName())) + "</label>";
			}
		};
	}//}}}
	
	public static Widget password(final AbstractFormField field){//{{{
		return new Widget(){
			public String getHtml(){
				return "<input type=\"password\" name=\"" + escapeHtml(field.getName()) + "\" value=\"" + escapeHtml(field.getRawValue()) + "\"/>";
			}

			public String getLabel(){
				return "<label for=\"" + escapeHtml(field.getName()) + "\">" + escapeHtml(getNiceName(field.getName())) + "</label>";
			}
		};
	}//}}}

	public static Widget textArea(final AbstractFormField field){//{{{
		return new Widget(){
			public String getHtml(){
				return "<textarea name=\"" + escapeHtml(field.getName()) + "\">" + escapeHtml(field.getRawValue()) + "</textarea>";
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

}
