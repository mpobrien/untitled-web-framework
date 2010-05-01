package com.mob.forms.widgets;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import com.mob.forms.*;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

class MultiOption{//{{{

	private final String value;
	private final String option;
	private final boolean selected;

	public MultiOption(String value, String option){
		this.value = value;
		this.option = option;
		this.selected = false;
	}

	public MultiOption(String value, String option, boolean selected){
		this.value = value;
		this.option = option;
		this.selected = selected;
	}

	public String getValue(){    return value;  }
	public String getOption(){    return option;  }
	public boolean getSelected(){    return selected;  }

	public String getOptionHtml(){//{{{
		return "<option value=\"" + escapeHtml(getValue()) + "\"" +
			   (this.selected ? " selected>" : ">") + 
			   escapeHtml(getValue()) + "</option>";
	}//}}}

}//}}}

