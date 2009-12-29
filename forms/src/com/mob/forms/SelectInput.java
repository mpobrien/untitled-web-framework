package com.mob.forms;
import java.util.*;

public class SelectInput implements Widget{

	private final FormField field;
	private final String value;
	private final Map<String,String> values;

	public SelectInput(FormField field, Map<String,String> values, String value){
		this.field = field;
		this.values = values;
		this.value = value;
	}

	public SelectInput(FormField field, Map<String,String> values){
		this.field = field;
		this.values = values;
		this.value = null;
	}

	public String getHtml(){
		StringBuilder sb = new StringBuilder("<select name=\"" + this.field.getName() + "\">\n");
		for (Map.Entry<String, String> entry : this.values.entrySet()) {
			String optionKey = entry.getKey();
			String optionVal = entry.getValue();
			sb.append("<option value=\"" + optionVal + "\">" + optionKey + "</option>\n");
		}
		sb.append("</select>");
		return sb.toString();
	}

	public String getLabel(){
		return "<label for=\"" + this.field.getName() + "\">" + this.field.getName() + "</label>";
	}

}
