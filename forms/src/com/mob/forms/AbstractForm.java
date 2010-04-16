package com.mob.forms;
import java.util.LinkedHashMap;

public class AbstractForm{

	private LinkedHashMap<String, FormField> fields = new LinkedHashMap<String, FormField>();

	public FormField getField(String fieldName){
		return fields.get( fieldName );
	}




    // composed of 1 or more fields
    // initial state of each field is unbound (null value) unless overridden
    // Call the validate() method
    // if validate() is successful:
        // all values are bound and getValue() returns a val
        // if the field isn't required and there was no value, val returns nothin

    // Necessary capabilities:
    // Integer, String, Float, Option, Boolean


}
