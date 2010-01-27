
package com.mob.forms;
import java.util.*;

public class CustomEnumChoiceField<T extends Enum<T> & CustomChoice> extends AbstractFormField<T> implements FormField{

     private final Class<T> enumClass;
     private T value = null;
 
 	public CustomEnumChoiceField(String name, Class<T> enumClass){//{{{
  		super( name );
         this.enumClass = enumClass;
 	}//}}}

  	@Override
  	public T getValue(){//{{{
  		return this.value;
  	}//}}}

  	@Override
	public void parseValue(String[] paramValues) throws FieldParseException{//{{{
		if( paramValues == null || paramValues.length == 0 ){
			this.raw = null;
			this.value = null;
		}else{
			this.raw = paramValues[0];
            Integer val = new Integer(this.raw);
            this.value = this.enumClass.getEnumConstants()[val];
		}
	}//}}}
 
// 	public String toString(){//{{{
//         return ":(";
// 		//return "ChoiceField["+this.name+"]: <" + value + ">";
// 	}//}}}
 
	public Widget getWidget(){
		if( this.widget != null ) return this.widget;
		return new Widget(){
			public String getHtml(){
				StringBuilder sb = new StringBuilder("<select name=\"" + name + "\">\n");
                T values[] = enumClass.getEnumConstants();
                for( T val : values ){
                     String optionVal = val.getValue();
                     String optionName = val.getOption();
 					sb.append("<option value=\"" + optionVal + "\">" + optionName + "</option>\n") ;
                }
				sb.append("</select>");
				return sb.toString();
			}

			public String getLabel(){
				return "<label for=\"" + name + "\">" + name + "</label>";
			}
		};
	}
 
	public CustomEnumChoiceField skipWhen( Condition con ){
		this.skipWhenCondition = con;
		return this;
	}
}
