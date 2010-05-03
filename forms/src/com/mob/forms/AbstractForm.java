package com.mob.forms;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Enumeration;
import javax.servlet.ServletRequest;
import java.util.*;
import com.google.common.collect.*;

public abstract class AbstractForm{

	protected LinkedHashMap<String,AbstractFormField> fields = new LinkedHashMap<String, AbstractFormField>();
	private boolean valid = false;
	protected FormErrors errors = new FormErrors();
	private Map<String, String[]> requestParams;

	public AbstractFormField getField(String fieldName){
		return fields.get( fieldName );
	}

	public LinkedHashMap<String,AbstractFormField> getFields(){
		return fields;
	}

	public void field(AbstractFormField field){
		this.fields.put(field.getName(), field);
	}
                                      
	public static Map<String, String[]> getParams(ServletRequest req){
		HashMap<String,String[]> params = new HashMap<String,String[]>();
		Enumeration<String> paramNames = req.getParameterNames();
		if( paramNames == null ) return params;
		while( paramNames.hasMoreElements() ){
			String name = paramNames.nextElement();
			String[] values = req.getParameterValues(name);
			params.put(name, values);
		}
		return params;
	}

	public void bind(ServletRequest req){
		bind( getParams(req) );
	}

	public void bind(Map<String, String[]> params){//{{{
		this.requestParams = params;
		for( AbstractFormField field : this.fields.values() ){
			String[] rawVal = params.get( field.getName() );
			if( rawVal == null || rawVal.length == 0 ){
				field.setRawValue(null);
			} else {
				field.setRawValue( rawVal[0] );
			}
		}

		for( AbstractFormField field : this.fields.values() ){
			try{
				field.bind();
			}catch(BindValueException bve){
				errors.addError(field.getName(), bve.getMessage());
				continue;
			}
// 			catch(FieldValidationException fve){
// 				errors.addError(field.getName(), fve.getMessage());
// 				continue;
// 			}
		}
	}//}}}

	public abstract void validate();

	public void error(String key, String error){//{{{
		this.errors.addError(key, error);
	}//}}}

	public void error(AbstractFormField field, String error){//{{{
		this.errors.addError(field.getName(), error);
		field.setErrors( this.errors.getErrors( field.getName() ) );
	}//}}}

	public String getTable(){//{{{
		StringBuilder sb = new StringBuilder("");
		for( AbstractFormField field : this.fields.values() ){
			sb.append( "<tr><td>" + field.getWidget().getLabel() + "</td>");
			sb.append( "<td>" + field.getWidget().getHtml() + "</td></tr>\n");
		}
		return sb.toString();
	}//}}}

	public String getUl(){//{{{
		StringBuilder sb = new StringBuilder("");
		for( AbstractFormField field : this.fields.values() ){
			sb.append( "<li>" + field.getWidget().getLabel() );
			sb.append( field.getWidget().getHtml() + "</li>\n");
		}
		return sb.toString();
	}//}}}

    // composed of 1 or more fields
    // initial state of each field is unbound (null value) unless overridden
    // Call the validate() method
    // if validate() is successful:
        // all values are bound and getValue() returns a val
        // if the field isn't required and there was no value, val returns nothin

    // Necessary capabilities:
    // Integer, String, Float, Option, Boolean
	
	public FormErrors getErrors(){    return errors;  }

	public boolean hasErrors(){
		return this.getErrors().hasErrors();
	}

	public StringField string(String name){//{{{
		StringField f = new StringField(name);
		field(f);
		return f; 
	}//}}}

	public StringField string(String name, String defaultValue){//{{{
		StringField f = new StringField(name, defaultValue);
		field(f);
		return f; 
	}//}}}

	public IntegerField integer(String name){//{{{
		IntegerField intf = new IntegerField(name);
		field(intf);
		return intf;
	}//}}}

	public IntegerField integer(String name, Integer defaultValue){//{{{
		IntegerField intf = new IntegerField(name, defaultValue);
		field(intf);
		return intf;
	}//}}}

	public EnumField choices(String name, Class<? extends Enum<?>> enumClass){//{{{
		EnumField newField = new EnumField( name, enumClass );
		field( newField );
		return newField;
	}//}}}

	public SetField choices(String name, Set<String> choices){//{{{
		SetField newField = new SetField( name, choices );
		field( newField );
		return newField;
	}//}}}

    public Iterable<String> getStrings(String name){//{{{
		if( this.requestParams.get(name) == null ){
			return new Iterable<String>(){
				public Iterator<String> iterator(){ return Iterators.emptyIterator(); };
			};
		}

		final Iterator<String> stringVals = Iterators.forArray(this.requestParams.get(name));
		return new Iterable<String>(){
			public Iterator<String> iterator(){
				return stringVals;
			}
		};
	}
//}}}

    public Iterable<String> getStringsMatching(String name, final String regex){//{{{
		if( this.requestParams.get(name) == null ){
			return new Iterable<String>(){
				public Iterator<String> iterator(){ return Iterators.emptyIterator(); };
			};
		}

		final Iterator<String> stringVals = Iterators.forArray(this.requestParams.get(name));
		return new Iterable<String>(){
			public Iterator<String> iterator(){
				return new AbstractIterator<String>(){
					public String computeNext(){
						while( stringVals.hasNext() ){
							String nextVal = stringVals.next();
							if( nextVal != null && nextVal.matches(regex) ){
								return nextVal;
							}else{
								continue;
							}
						}
						return endOfData();
					}
				};
			}
		};
	}//}}}

}
