package com.mob.forms;
import java.util.*;
import com.mob.forms.ChoiceField.Pair;
import com.google.common.collect.AbstractIterator;

public abstract class AbstractWidget implements Widget{

	protected final static String START_TAG = "<";
	protected final static String END_ELEM = "</";
	protected final static String END_TAG   = ">";
	protected FormField field;
	protected final static String HTMLATTR_ID   = "id";
	protected final static String HTMLATTR_NAME = "name";
	protected final static String HTMLATTR_DISABLED = "disabled";
	protected final static String HTMLATTR_TYPE = "type";
	protected boolean disabled = false;

    protected abstract String getTagElement();

	@Override
	public abstract String getHtml();

	@Override
	public abstract String getLabel();

	protected static String getHtmlAttr(String name, String value){
		return name + "=\"" + value + "\"";
	}


	public String getOpenTag(FormField field){
		return START_TAG +
			   getTagElement() + " " +
			   getHtmlAttr( HTMLATTR_ID, "id_" + field.getName() ) + " " +
			   getHtmlAttr( HTMLATTR_NAME, field.getName() ) +
			   ( this.getDisabled() ? " " + HTMLATTR_DISABLED : "") + 
			   END_TAG;
	}

	public String getCloseTag(){
		return END_ELEM + getTagElement() + END_TAG;
	}

	public boolean getDisabled(){
		return this.disabled;
	}

	public void setDisabled( boolean disabled ){
		this.disabled = disabled;
	}

	public void disable(){
		this.disabled = true;;
	}


}
