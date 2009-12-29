package com.mob.forms;

public class FieldValidationException extends FieldException{

    public FieldValidationException(String fieldName, String errorKey){
		super( fieldName, errorKey );
	}

}
