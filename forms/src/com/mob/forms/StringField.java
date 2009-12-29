package com.mob.forms;

public class StringField extends AbstractFormField<String> implements FormField{

	public StringField(String name){
		super( name );
	}

	@Override
	public String getValue(){
		return this.value;
	}

	@Override
	public void parseValue(String[] paramValues) throws FieldParseException{//{{{
		System.out.println(paramValues);
		if( paramValues == null || paramValues.length == 0 ){
			System.out.println("mob1");
			this.raw = null;
			this.value = this.raw;
		}else{
			System.out.println("mob2");
			this.raw = paramValues[0] + "";
			this.value = this.raw;
			System.out.println(value);
			System.out.println(this.raw);

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

}
