package com.mob.forms;

public class IntegerField extends AbstractFormField<Integer> implements FormField{

	public IntegerField(String name){
		super( name );
	}

	@Override
	public String getRaw(){
		return this.raw;
	}

	@Override
	public Integer getValue(){
		return this.value;
	}

	@Override
	public void parseValue(String[] paramValues) throws FieldParseException{//{{{
		if( paramValues == null || paramValues.length == 0 ){
			this.raw = null;
			this.value = null;
		}else{
			try{
				this.raw = paramValues[0];
				this.value = new Integer( paramValues[0] );
				this.raw = this.value.toString();
			}catch(NumberFormatException e){
				throw new FieldParseException( this.name, "invalid"); 
			}
		}
	}//}}}

	public String toString(){
		return "IntegerField["+this.name+"]: <" + this.value + ">";
	}

	@Override
	public Widget getWidget(){
		if( this.widget != null ) return this.widget;
		final String widgetValue = this.value != null ? this.value.toString() : "";
		return new Widget(){
			public String getHtml(){ return "<input type=\"text\" name=\"" + name + "\" value=\"" + widgetValue + "\">"; }
			public String getLabel(){ return "<label for=\"" + name + "\">" + name + "</label>"; }
		};
	}

	public IntegerField skipWhen( Condition con ){
		this.skipWhenCondition = con;
		return this;
	}

}
