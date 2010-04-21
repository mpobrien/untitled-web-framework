package com.mob.forms;

public class BindValueException extends Exception{

	public BindValueException(String message){
		super(message);
	}

	public BindValueException(String message, Throwable err){
		super(message, err);
	}
}
