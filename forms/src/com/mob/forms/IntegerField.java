package com.mob.forms;

public class IntegerField extends AbstractFormField<Integer>{

    private Integer value;

    public IntegerField(String name){
        super(name);
    }

    public IntegerField(String name, Integer defaultVal){
        super(name);
        this.value = defaultVal;
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
