package com.mob.forms;
import java.util.*;

public abstract class AbstractFormField<T> implements FormField{

	protected final String name;
	protected String raw;
	protected List<ValidationRule<T>> rules = null;
	protected T value;
	protected Widget widget = null;
	protected Set<String> errors = null;

	public AbstractFormField(String name){
		if( name == null || name.equals("") ) throw new IllegalArgumentException("null or blank string not allowed for field name");
		this.name = name;
	}

	@Override
	public String getName(){
		return this.name;
	}

	public void setRules(List<ValidationRule<T>> rules){
		this.rules = rules;
	}

	public String getRaw(){
		return this.raw;
	}

	public abstract Object getValue();

	public abstract void parseValue(String[] paramValues) throws FieldParseException;

	public void validate() throws FieldValidationException{
		if( this.rules == null || this.rules.isEmpty() ){
			return;
		}else{
			for( ValidationRule<T> rule : this.rules ){
				boolean test = rule.validate( (T)this.getValue() );
				if( test == false ){
					throw new FieldValidationException(this.name, rule.key() );
				}
			}
		}
	}

	public abstract Widget getWidget();

	public void setWidget(Widget widget){
		this.widget = widget;
	}

	@Override
	public Set<String> getErrors(){
		return this.errors;
	}

	@Override
	public void setErrors( Set<String> errs ){
		this.errors = errors;
	}

}
