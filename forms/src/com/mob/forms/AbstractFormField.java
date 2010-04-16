package com.mob.forms;

public class AbstractFormField{

	protected final String fieldName;

    public AbstractFormField(String fieldName){
		this.fieldName = fieldName;
	}

	public String getName(){
		return this.fieldName;
	}

}
