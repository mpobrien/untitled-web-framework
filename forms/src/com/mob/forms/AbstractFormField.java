package com.mob.forms;
import com.mob.forms.widgets.*;
import java.util.*;

public abstract class AbstractFormField<T>{

	protected boolean required = true;
	protected String raw = null;
	protected Set<String> errors = new HashSet();
	protected Widget widget;

	protected final String fieldName;

    public AbstractFormField(String fieldName){
		this.fieldName = fieldName;
	}

	public String getName(){
		return this.fieldName;
	}

	public boolean isRequired(){
		return this.required;
	}

	public abstract void bind() throws BindValueException;

	public void setRawValue(String raw){
		this.raw = raw;
	}

	public String getRawValue(){
		return this.raw + "";
	}

	public void validateField() throws FieldValidationException{
	}

	public Set<String> getErrors(){
		return this.errors;
	}

	public void setErrors(Set<String> errors){
		this.errors = errors;
	}

	public Widget getWidget(){
		return this.widget;
	}

	public void setWidget(Widget widget){
		this.widget = widget;
	}

    public abstract T getValue();
    public abstract void setValue(T val);

	//public abstract Widget getWidget();

}
