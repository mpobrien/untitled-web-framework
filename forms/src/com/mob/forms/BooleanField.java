package com.mob.forms;

public class BooleanField extends AbstractFormField<Boolean>{

    private Boolean value;

    public BooleanField(String name){
        super(name);
    }

    public BooleanField(String name, Boolean defaultVal){
        super(name);
        this.value = defaultVal;
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
