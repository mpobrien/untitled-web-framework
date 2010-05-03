package com.mob.forms;
import com.mob.forms.widgets.*;

public class IntegerField extends AbstractFormField<Integer>{

    public IntegerField(String name){
        super(name);
		this.widget = WidgetGenerator.text(this);
    }

    public IntegerField(String name, Integer defaultVal){
        super(name);
        this.value = defaultVal;
		this.widget = WidgetGenerator.text(this);
    }

	@Override
	public void bind() throws BindValueException{//{{{
		setValue( coerceValue( this.raw ) );
		if( this.getValue() == null ){
			if( (this.raw + "").length() > 0 ){
				throw new BindValueException("This field must be an integer.");
			}else{
				if( this.required ){
					throw new BindValueException("This field is required.");
				}
			}
		}
	}//}}}
    
	@Override
	public Integer coerceValue(String val){
		try{
			return new Integer ( val + "" );
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public String getFormVal(Integer val){
		return val != null ? val.toString() : "";
	}
}
