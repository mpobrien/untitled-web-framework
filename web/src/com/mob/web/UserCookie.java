package com.mob.web;
import javax.servlet.http.*;
import java.util.*;
import com.google.inject.*;
import com.google.inject.servlet.*;

public class UserCookie{

	private final String authString;

	public UserCookie(String authString){//{{{
		this.authString = authString;
	}//}}}

	public String getAuthString(){ return this.authString; }

}
