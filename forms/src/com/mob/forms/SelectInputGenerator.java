package com.mob.forms;

public class TextInputGenerator implements WidgetGenerator{

	@Override
    public Widget getWidget( final FormField field ){
		return new Widget(){
			public String getHtml(){
				return "<textarea name=\"" + this.field.getName() + "\">" + 
					   (field.getRaw() != null ?  field.getRaw() : "") + "</textarea>";
			}
			public String getLabel(){
				return "<label for=\"" + field.getName() + "\">" + field.getName() + "</label>";
			}
		};
	}

}
