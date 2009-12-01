package com.mob.web;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.inject.*;
import com.google.inject.name.*;
import java.util.*;
import java.util.regex.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;
import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.subclassesOf;

public class ControllerRequest{

	private final Class<? extends Controller> controllerClass;
	private final List<String> args;

	public ControllerRequest( Class<? extends Controller> controllerClass, Matcher matchedUrl){
		this.controllerClass = controllerClass;
		if( matchedUrl.groupCount() > 0 ){
			List<String> argsList = new ArrayList<String>(matchedUrl.groupCount());
			for( int i = 1; i<= matchedUrl.groupCount(); i++ ){
				argsList.add( matchedUrl.group( i ) );
			}
			this.args = argsList;
		}else{
			this.args = new ArrayList<String>( 0 );
		}
	}

	public Class<? extends Controller> getControllerClass(){
		return this.controllerClass;
	}

	public List<String> getArgs(){
		return this.args;
	}

}
