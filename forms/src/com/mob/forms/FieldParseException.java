package com.mob.forms;

public class FieldParseException extends FieldException{

    public FieldParseException(String fieldName, String errorKey){
		super( fieldName, errorKey );
	}

}
