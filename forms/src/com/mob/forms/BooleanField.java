package com.mob.forms;

public class BooleanField extends AbstractFormField<Boolean> implements FormField{

	public BooleanField(String name){
		super( name );
	}

	@Override
	public String getValue(){
		return this.raw;
	}

	@Override
	public void parseValue(String[] paramValues) throws FieldParseException{
		if( paramValues == null || paramValues.length == 0 ){
			this.raw = null;
			this.value = false;
		}else{
			this.raw = paramValues[0];
            if( this.raw != null && this.raw.length() > 0 ){
                this.value = true;
            }
		}
	}

	public String toString(){
		return "BooleanField["+this.name+"]: <" + value + ">";
	}

	public Widget getWidget(){
		if( this.widget != null ) return this.widget;
		return new Widget(){
			public String getHtml(){
				return "<input type=\"checkbox\" name=\"" +
				       name + "\"" + (value ? " checked>" : ">");
			}

			public String getLabel(){
				return "<label for=\"" + name + "\">" + name + "</label>";
			}
		};
	}

}
