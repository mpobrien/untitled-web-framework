package com.mob.forms;
import java.util.Set;

public interface FormField{

	public Widget getWidget();

	public String getName();

	public String getRaw();

	public Object getValue();

	public boolean proceed();

	public void parseValue(String[] paramValues) throws FieldParseException;

	public void validate() throws FieldValidationException;

	public Set<String> getErrors();

	public void setErrors( Set<String> errors );

}
