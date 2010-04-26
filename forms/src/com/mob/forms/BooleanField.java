package com.mob.forms;
import com.mob.forms.widgets.*;

public class BooleanField extends AbstractFormField<Boolean>{

    private Boolean value;

    public BooleanField(String name){
        super(name);
    }

    public BooleanField(String name, Boolean defaultVal){
        super(name);
        this.value = defaultVal;
		this.widget = WidgetGenerator.checkbox(this);
    }

    @Override
    public Boolean getValue(){ return this.value; }

    @Override
    public void setValue(Boolean val){ this.value = val; }

	@Override
	public void bind() throws BindValueException{//{{{
		if( this.raw == null || this.raw.equals("") ){
			setValue( false );
		}else{
			setValue( true );
		}
	}//}}}
    
}
