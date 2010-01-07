package com.mob.forms;

public class CheckboxWidget extends AbstractWidget implements Widget{

	private final FormField field;
	private static final String tag = "input";

	public CheckboxWidget(FormField field){
		this.field = field;
	}

	@Override
    protected String getTagElement(){ return tag; }

	public String getHtml(){
		Object value = field.getValue();
		boolean checked = false;
		if( value == null ){
			checked = false;
		}else if( value instanceof Boolean ){
			checked = (Boolean)value;
		}
		return "<input type=\"checkbox\" name=\"" + field.getName() + "\"" + (checked ? " checked>" : ">");
	}

	public String getLabel(){
		return "<label for=\"" + field.getName() + "\">" + field.getName() + "</label>";
	}

}
