package com.mob.forms;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractForm{

	protected LinkedHashMap<String,AbstractFormField> fields = new LinkedHashMap<String, AbstractFormField>();
	private boolean valid = false;
	protected FormErrors errors = new FormErrors();

	public AbstractFormField getField(String fieldName){
		return fields.get( fieldName );
	}

	public void field(AbstractFormField field){
		this.fields.put(field.getName(), field);
	}

	public void bind(Map<String, String[]> params){//{{{
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

}
