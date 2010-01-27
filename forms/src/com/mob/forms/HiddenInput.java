package com.mob.forms;

public class HiddenInput implements Widget{

	private final FormField field;
	private final String contents;

	public HiddenInput(FormField field, String contents){
		this.field = field;
		this.contents = contents + "";
	}

	public String getHtml(){
		return "<input type=\"hidden\" name=\"" + this.field.getName() + "\" value=\"" + this.contents + "\">";
	}

	public String getLabel(){
		return "<label for=\"" + this.field.getName() + "\">" + this.field.getName() + "</label>";
	}

}
