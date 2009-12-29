package com.mob.forms;

public class FieldException extends Exception{

	protected final String fieldName;
	protected final String errorKey;

    public FieldException(String fieldName, String errorKey){
		this.fieldName = fieldName;
		this.errorKey = errorKey;
	}

	public String getFieldName(){
		return fieldName;
	}

	public String getErrorKey(){
		return errorKey;
	}

}
