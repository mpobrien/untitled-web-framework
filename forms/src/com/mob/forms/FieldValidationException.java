package com.mob.forms;

public class FieldValidationException extends Exception{

	public FieldValidationException(String message){
		super(message);
	}

	public FieldValidationException(String message, Throwable err){
		super(message, err);
	}
}
