package com.mob.forms;
import java.lang.reflect.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class FormErrors{
	
	private HashMap<String, Set<String>> errors = new HashMap<String, Set<String>>();

    public Set<String> addError(String fieldName, String errorCode){
        if( fieldName == null || errorCode == null ) throw new IllegalArgumentException("fieldName and errorCode can not be null");
        Set<String> returnVal = this.errors.get( fieldName );
        if( returnVal == null ){
            returnVal = new HashSet<String>();
        }
        returnVal.add( errorCode );
        this.errors.put( fieldName, returnVal );
        return returnVal;
    }

    public boolean isEmpty(){
        return this.errors.isEmpty();
    }

    public Set<String> getFieldErrors(String fieldName){
        return this.errors.get( fieldName );
    }

    public Set<String> getFieldErrors( FormField field ){
        return this.errors.get( field.getName() );
    }

}
