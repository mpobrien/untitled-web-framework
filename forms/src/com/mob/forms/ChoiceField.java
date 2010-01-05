package com.mob.forms;
import java.util.*;

public class ChoiceField extends AbstractFormField<String> implements FormField{

    private final Map<String, String> choices;

	public ChoiceField(String name, Map<String, String> choices){//{{{
		super( name );
        this.choices = choices;
	}//}}}

	public ChoiceField(String name, Set<String> choiceSet){//{{{
		super( name );
		this.choices = new HashMap( choiceSet.size() );
		for( String s : choiceSet ){
			this.choices.put( s, s );
		}
	}//}}}

	@Override
	public String getValue(){//{{{
		return this.value;
	}//}}}

	@Override
	public void parseValue(String[] paramValues) throws FieldParseException{//{{{
		if( paramValues == null || paramValues.length == 0 ){
			this.raw = null;
			this.value = this.raw;
		}else{
			this.raw = paramValues[0];
            if( this.choices.containsKey( this.raw ) ){
                this.value = this.raw;
            }else{
                this.value = null;
            }
		}
	}//}}}

	public String toString(){//{{{
		return "ChoiceField["+this.name+"]: <" + value + ">";
	}//}}}

	public Widget getWidget(){
		if( this.widget != null ) return this.widget;
		final String fieldVal = this.getValue();
		return new Widget(){
			public String getHtml(){
				StringBuilder sb = new StringBuilder("<select name=\"" + name + "\">\n");
				for (Map.Entry<String, String> entry : choices.entrySet()) {
					String key = entry.getKey() + "";
					String val = entry.getValue() + "";
					sb.append("<option value=\"" + val + "\"" +
							((fieldVal == null || !fieldVal.equals(val)) ? "" : " selected") +
							">" + key + "</option>\n") ;
				}
				sb.append("</select>");
				return sb.toString();
			}

			public String getLabel(){
				return "<label for=\"" + name + "\">" + name + "</label>";
			}
		};
	}

}
