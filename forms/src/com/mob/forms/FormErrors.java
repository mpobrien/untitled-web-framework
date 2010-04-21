package com.mob.forms;
import com.google.common.collect.*;
import java.util.*;

public class FormErrors{

	private HashMultimap<String,String> fieldErrors = HashMultimap.create();

	public Set<String> getErrors(String fieldName){
		return fieldErrors.get(fieldName);
	}

	public void addError(String fieldName, String error){
		this.fieldErrors.put(fieldName, error);
	}

	public boolean hasErrors(){
		return !this.fieldErrors.isEmpty();
	}

}

