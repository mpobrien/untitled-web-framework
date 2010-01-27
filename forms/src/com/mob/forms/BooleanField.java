package com.mob.forms;

public class BooleanField extends AbstractFormField<Boolean> implements FormField{

	public BooleanField(String name){
		super( name );
	}

	@Override
	public Boolean getValue(){
		return this.value;
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

	public void setValue(boolean val){
		this.value = value;
	}

	public Widget getWidget(){
		if( this.widget != null ) return this.widget;
		final boolean val = this.getValue() != null ? this.getValue() : false;
		return new Widget(){
			public String getHtml(){
				return "<input type=\"checkbox\" name=\"" +
				       name + "\"" + (val ? " checked>" : ">");
			}

			public String getLabel(){
				return "<label for=\"" + name + "\">" + name + "</label>";
			}
		};
	}

	public Condition isChecked(){
		return new Condition(){
			public boolean isSatisfied(){
				return getValue();
			}
		};
	}

	public BooleanField skipWhen( Condition con ){
		this.skipWhenCondition = con;
		return this;
	}

}
