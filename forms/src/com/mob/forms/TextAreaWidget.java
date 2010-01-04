package com.mob.forms;

public class TextAreaWidget implements Widget{

	private final FormField field;
	private final String contents;

	public TextAreaWidget(FormField field, String contents){
		this.field = field;
		this.contents = contents + "";
	}

	public String getHtml(){
		return "<textarea name=\"" + this.field.getName() + "\">" + this.contents + "</textarea>";
	}

	public String getLabel(){
		return "<label for=\"" + this.field.getName() + "\">" + this.field.getName() + "</label>";
	}

}
