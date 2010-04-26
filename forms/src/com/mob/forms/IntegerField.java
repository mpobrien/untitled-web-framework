package com.mob.forms;
import com.mob.forms.widgets.*;

public class IntegerField extends AbstractFormField<Integer>{

    private Integer value;

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
    public Integer getValue(){ return this.value; }

    @Override
    public void setValue(Integer val){ this.value = val; }

	@Override
	public void bind() throws BindValueException{//{{{
		if( this.raw == null ){
			setValue( null );
			if( this.required ){
				throw new BindValueException("This field is required.");
			}
		}else{
			try{
				setValue( new Integer( this.raw ) );
			}catch(Exception e){
				setValue( null );
				throw new BindValueException("This field must be an integer.");
			}
		}
	}//}}}
    
}
