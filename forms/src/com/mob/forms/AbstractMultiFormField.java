package com.mob.forms;
import com.mob.forms.widgets.*;
import java.util.*;

public abstract class AbstractMultiFormField<T> extends AbstractFormField<T>{

    public AbstractMultiFormField(String fieldName){
		super(fieldName);
	}

	public abstract void bind() throws BindValueException;
    public abstract T getValue();
    public abstract void setValue(T val);
	public abstract Iterator<T> getOptions();
	public abstract T lookupOption(String val);
	public abstract String getFormVal(T val);
	public abstract String getFormName(T val);

	//public abstract Widget getWidget();

		
		
}
