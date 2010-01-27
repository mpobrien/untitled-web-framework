package com.mob.forms;
import java.lang.reflect.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public abstract class Form{

	protected LinkedHashMap<String, FormField> formFields = null;
	protected FormErrors errors = null;

    public Form(){//{{{
		this.fields();
    }//}}}

	public Form(HttpServletRequest request){//{{{
		this.fields();
		this.populateFromRequest( request );
	}//}}}

	public void populateFromRequest(HttpServletRequest request){//{{{
		this.populateFromMap( (Map<String, String[]>)request.getParameterMap() );
	}//}}}

	public FormErrors populateFromMap(Map<String, String[]> requestValues){//{{{
		if( this.errors != null ){
			return this.errors;
		}else{
			this.errors = new FormErrors();
		}
		for (Map.Entry<String, FormField> entry : formFields.entrySet()) {
			String key = entry.getKey();
			FormField value = entry.getValue();
			try{
				if( value.proceed() ){
					value.parseValue( requestValues.get( key ) );
					value.validate();
				}
			}catch(FieldException fp){
				fp.printStackTrace();
				Set<String> fieldErrors = this.errors.addError( fp.getFieldName(), fp.getErrorKey() );
				value.setErrors( fieldErrors );
			}
		}
		return this.errors;
	}//}}}

	public boolean isValid(){//{{{
		if( this.errors == null ){
			throw new IllegalStateException("Form has not been populated yet.");
		}else{
			return this.errors.isEmpty();
		}
	}//}}}

	protected abstract void fields();

	public FormField getField(String fieldName){//{{{
		if( this.formFields == null ){
			return null;
		}else{
			return this.formFields.get( fieldName );
		}
	}//}}}

	public FormErrors getErrors(){//{{{
		return this.errors;
	}//}}}

	protected final void field( FormField field ){//{{{
		if( field == null ) return;
		this.formFields = (formFields == null ?  new LinkedHashMap<String, FormField>() : formFields);
		this.formFields.put( field.getName(), field );
	}//}}}

	protected StringField stringField(String name, ValidationRule<String>... rules){//{{{
		StringField newField = new StringField( name );
		field( newField );
		newField.setRules( Arrays.asList( rules ) );
		return newField;
	}//}}}

	protected BooleanField boolField(String name){//{{{
		BooleanField newField = new BooleanField( name );
		field( newField );
		//newField.setRules( Arrays.asList( rules ) );
		return newField;
	}//}}}

	protected IntegerField intField(String name, ValidationRule<Integer>... rules){//{{{
		IntegerField newField = new IntegerField( name );
		newField.setRules( Arrays.asList( rules ) );
		field( newField );
		return newField;
	}//}}}

	protected ChoiceField choiceField(String name, Set<String> choices, ValidationRule<String>... rules){//{{{
		ChoiceField newField = new ChoiceField( name, choices );
		newField.setRules( Arrays.asList( rules ) );
		field( newField );
		return newField;
	}//}}}

	protected EnumChoiceField choiceField(String name, Class<? extends Enum<?>> enumClass, ValidationRule<String>... rules){//{{{
		EnumChoiceField newField = new EnumChoiceField( name, enumClass );
		newField.setRules( Arrays.asList( rules ) );
		field( newField );
		return newField;
	}//}}}

	protected <T extends Enum<T> & CustomChoice> CustomEnumChoiceField choiceField(String name, Class<T> enumClass, ValidationRule<String>... rules){//{{{
		CustomEnumChoiceField newField = new CustomEnumChoiceField( name, enumClass );
		newField.setRules( Arrays.asList( rules ) );
		field( newField );
		return newField;
	}//}}}

	public String toString(){//{{{
		StringBuilder sb = new StringBuilder("Form:{\n");
		for( FormField field : this.formFields.values() ){
			sb.append( "\t" + field.toString() + "\n" );
		}
		sb.append("}");
		return sb.toString();
	}//}}}

	public String getTable(){//{{{
		StringBuilder sb = new StringBuilder("<table>");
		for(FormField field : formFields.values()){
			sb.append( "<tr><td>" + field.getWidget().getLabel() + "</td>" );
			sb.append( "<td>" + field.getWidget().getHtml() + "</td></tr>" );
		}
		sb.append("</table>");
		return sb.toString();
	}//}}}

	public String getUl(){//{{{
		StringBuilder sb = new StringBuilder("<ul>");
		for(FormField field : formFields.values()){
			sb.append( "<li>" + field.getWidget().getLabel() );
			sb.append( field.getWidget().getHtml() + "</li>" );
		}
		sb.append("</ul>");
		return sb.toString();
	}//}}}

}
