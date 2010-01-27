package com.mob.forms;

public class StringField extends AbstractFormField<String> implements FormField{

	private String defaultWhenValue = null;

	public StringField(String name){
		super( name );
	}

	@Override
	public String getValue(){
		return this.value;
	}

	@Override
	public void parseValue(String[] paramValues) throws FieldParseException{//{{{
		if( paramValues == null || paramValues.length == 0 ){
			this.raw = null;
			this.value = this.raw;
		}else{
			this.raw = paramValues[0] + "";
			this.value = this.raw;
		}
	}//}}}

	public String toString(){
		return "StringField["+this.name+"]: <" + this.value + ">";
	}

	@Override
	public Widget getWidget(){
		if( this.widget != null ) return this.widget;
		final String widgetValue = this.value != null ? value : "";
		return new Widget(){
			public String getHtml(){ return "<input type=\"text\" name=\"" + name + "\" value=\"" + widgetValue + "\">"; }
			public String getLabel(){ return "<label for=\"" + name + "\">" + name + "</label>"; }
		};
	}

    public Condition valueEquals( final String value ){
		return new Condition(){
			public boolean isSatisfied(){
				return value.equals( getValue() );
			}
		};
	}

    public Condition blank(){
		return new Condition(){
			public boolean isSatisfied(){
				return getValue() == null || getValue().equals( "" );
			}
		};
	}

	public StringField skipWhen( Condition con ){
		this.skipWhenCondition = con;
		return this;
	}

}
