package com.mob.web;
import javax.servlet.http.*;
import java.util.*;
import com.google.inject.*;

public abstract class Controller{

	protected List<String> args = new ArrayList<String>(0);
	protected Responses responses;

	@Inject
	public void setResponses(Responses responses){
		this.responses = responses;
	}

	public void processArgs(){
		return;
	}

	public WebResponse get(HttpServletRequest request){
		return null;
	}

	public WebResponse post(HttpServletRequest request){
		return null;
	}

	protected void setArgs( List<String> args ){
		this.args = args;
	}

	public void setContext(){
		return;
	}

}
