package com.mob.forms;

public interface FormField{

	public Widget getWidget();

	public String getName();

	public String getRaw();

	public Object getValue();

	public void parseValue(String[] paramValues) throws FieldParseException;

	public void validate() throws FieldValidationException;

}
