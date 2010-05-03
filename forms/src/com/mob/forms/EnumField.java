package com.mob.forms;
import com.mob.forms.widgets.*;
import java.util.*;
import com.google.common.collect.*;

public class EnumField<T extends Enum<T>> extends AbstractMultiFormField<T>{

    private final Class<T> enumClass;
    private T value;

    public EnumField(String name, Class<T> enumClass){
        super(name);
        this.enumClass = enumClass;
    }

    @Override
    public T getValue(){ return this.value; }

    @Override
    public void setValue(T val){ this.value = val; }

	@Override
	public void bind() throws BindValueException{//{{{
        if( this.raw == null ){
			setValue( null );
			if( this.required ){
				throw new BindValueException("This field is required.");
			}
        }else{
            this.setValue( coerceValue( this.raw ) );
            if( this.required && this.getValue() == null ){
                throw new BindValueException("Invalid index to enum choices");
            }
        }
	}//}}}

    @Override
    public Iterator<T> getOptions(){
        return Iterators.forArray( enumClass.getEnumConstants() );
    }

    @Override
    public String getFormVal(T value){
        return Integer.toString( value.ordinal() );
    }

    @Override
    public String getFormName(T value){
        return WidgetGenerator.getNiceName(value.toString());
    }

    @Override
	public T coerceValue(String val){
        try{
            Integer index = new Integer(val);
            return this.enumClass.getEnumConstants()[index];
        }catch(Exception e){
            return null;
        }
    }

}
