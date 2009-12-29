package com.mob.forms;

public class TextInput implements Widget{

	private final FormField field;
	private final String contents;

	public TextInput(FormField field, String contents){
		this.field = field;
		this.contents = "";
	}

	public String getHtml(){
		return "<input type=\"text\" name=\"" + this.field.getName() + "\" value=\"" + this.contents + "\">";
	}

	public String getLabel(){
		return "<label for=\"" + this.field.getName() + "\">" + this.field.getName() + "</label>";
	}

}
