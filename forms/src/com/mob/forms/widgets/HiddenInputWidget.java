package com.mob.forms;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

public class HiddenInputWidget{
	
    public String getHtml(String name, String value){
		return "<input type=\"hidden\" name=\"" + escapeHtml(name) + "\" value=\"" + escapeHtml(value) + "\"/>";
	}




}
