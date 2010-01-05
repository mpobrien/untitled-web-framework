package com.mob.forms;

public class TextInputGenerator implements WidgetGenerator{

	@Override
    public Widget getWidget( final FormField field ){
		return new Widget(){
			public String getHtml(){
				return "<input type=\"text\" name=\"" + field.getName() + "\"" + 
						(field.getRaw() != null ?  "value=\"" + field.getRaw() + "\"" : "") + ">";
			}
			public String getLabel(){
				return "<label for=\"" + field.getName() + "\">" + field.getName() + "</label>";
			}
		};
	}

}
