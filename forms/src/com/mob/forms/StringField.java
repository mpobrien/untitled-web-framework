package com.mob.forms;
import com.mob.forms.widgets.*;

public class StringField extends AbstractFormField<String>{

    public StringField(String name){
        super(name);
		this.widget = WidgetGenerator.text(this);
		this.raw = "";
    }

    public StringField(String name, String defaultVal){//{{{
        super(name);
        this.value = defaultVal;
		this.widget = WidgetGenerator.text(this);
		this.raw = "";
    }//}}}

	@Override
	public void bind(){//{{{
		setValue( this.raw );
	}//}}}

	@Override
	public String coerceValue(String val){
		return val;
	}

	@Override
	public String getFormVal(String val){
		return val + "";
	}

}
