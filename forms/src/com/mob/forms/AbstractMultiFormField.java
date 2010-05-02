package com.mob.forms;
import com.mob.forms.widgets.*;
import java.util.*;
import com.google.common.collect.*;

public abstract class AbstractMultiFormField<T> extends AbstractFormField<T>{

    public AbstractMultiFormField(String fieldName){
		super(fieldName);
		this.widget = WidgetGenerator.selectOptions(this);
	}

	public abstract void bind() throws BindValueException;
    public abstract T getValue();
    public abstract void setValue(T val);
	public abstract Iterator<T> getOptions();
	public abstract T lookupOption(String val);
	public abstract String getFormVal(T val);
	public abstract String getFormName(T val);

	public Iterator<MultiOption> getHtmlOptions(){
		return new AbstractIterator<MultiOption>(){
			private Iterator<T> optionsIterator = getOptions();
			public MultiOption computeNext(){
				while( optionsIterator.hasNext() ){
					T option = optionsIterator.next();
					return new MultiOption( getFormVal(option), 
											getFormName(option),
											getValue() != null && getValue() == option);
				}
				return endOfData();
			}
		};
	}
		
}
