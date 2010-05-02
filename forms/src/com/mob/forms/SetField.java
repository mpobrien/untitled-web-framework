package com.mob.forms;
import com.mob.forms.widgets.*;
import java.util.*;
import com.google.common.collect.*;

public class SetField extends AbstractMultiFormField<String>{

    private final Set<String> choices;
    private String value;

    public SetField(String name, Set<String> choices){//{{{
        super(name);
        this.choices = choices;
    }//}}}

    @Override
    public String getValue(){ return this.value; }

    @Override
    public void setValue(String val){ this.value = val; }

	@Override
	public void bind() throws BindValueException{//{{{
        if( this.raw == null ){
			setValue( null );
			if( this.required ){
				throw new BindValueException("This field is required.");
			}
        }else{
            this.setValue( lookupOption( this.raw ) );
            if( this.required && this.getValue() == null ){
                throw new BindValueException("Invalid index to enum choices");
            }
        }
	}//}}}

    @Override
    public Iterator<String> getOptions(){//{{{
        return this.choices.iterator();
    }//}}}

    @Override
    public String getFormVal(String value){//{{{
        return value;
    }//}}}

    @Override
    public String getFormName(String value){//{{{
        return value;
    }//}}}

    @Override
	public String lookupOption(String val){//{{{
        try{
            if( this.choices.contains( val ) ) return val;
            else return null;
        }catch(Exception e){
            return null;
        }
    }//}}}

}
