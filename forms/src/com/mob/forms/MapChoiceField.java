package com.mob.forms;
import java.util.*;
import com.mob.forms.ChoiceField.Pair;
import com.google.common.collect.AbstractIterator;

public class MapChoiceField extends AbstractFormField<String> implements ChoiceField{

    private final Map<String, String> choices;

	public MapChoiceField(String name, Map<String, String> choices){//{{{
		super( name );
        this.choices = choices;
	}//}}}

	public MapChoiceField(String name, Set<String> choiceSet){//{{{
		super( name );
		this.choices = new HashMap( choiceSet.size() );
		for( String s : choiceSet ){
			this.choices.put( s, s );
		}
	}//}}}

	@Override
	public Iterator<Pair<String,String>> getOptionsIterator(){
		final Iterator<Map.Entry<String,String>> in = this.choices.entrySet().iterator();
		return new AbstractIterator<Pair<String,String>>() {
			protected Pair<String,String> computeNext() {
				while (in.hasNext()) {
					Map.Entry<String,String> pair = in.next();
					return new Pair<String,String>( pair.getKey(), pair.getValue() );
				 }
				return endOfData();
		   }
		};
	}

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
		
		WidgetGenerator widgGen = new SelectInputGenerator( this );
		return widgGen.getWidget();
	}

}
