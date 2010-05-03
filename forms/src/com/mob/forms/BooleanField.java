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
	public Boolean coerceValue(String val){
		return val != null && !val.equals("");
	}

	public String getFormVal(Boolean val){
		return  val != null && val.booleanValue() ? "on" : "";
	}
    
}
