package com.mob.web;
import javax.servlet.http.*;
import java.util.*;
import com.google.inject.*;
import com.google.inject.servlet.*;

public class UserCookie{
	private final String info;

	public UserCookie(String info){
		this.info = info;
	}

	public String getUserInfo(){
		return this.info;
	}

}
