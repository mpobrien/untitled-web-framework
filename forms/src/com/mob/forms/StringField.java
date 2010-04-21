package com.mob.forms;
import com.mob.forms.widgets.*;

public class StringField extends AbstractFormField<String>{
    private String value;

    public StringField(String name){
        super(name);
		//this.widget = new TextInputWidget(name, this);
    }

    public StringField(String name, String defaultVal){//{{{
        super(name);
        this.value = defaultVal;
		this.widget = WidgetGenerator.text(this);
    }//}}}

    @Override
    public String getValue(){ return this.value; }

    @Override
    public void setValue(String val){ this.value = val; }

	@Override
	public void bind(){//{{{
		setValue( this.raw );
	}//}}}

}
