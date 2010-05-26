package com.mob.web;
import javax.servlet.http.*;
import java.io.*;
import java.security.*;
import java.util.*;
import java.io.UnsupportedEncodingException;
import javax.servlet.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import com.google.inject.*;
import com.google.inject.servlet.*;

@RequestScoped
public class FlashProvider extends ContextProcessor implements com.google.inject.Provider<Flash>{

	public static final String FLASH_REQ_KEY = "FLASH";
	public static final String FLASH_SESSION_PREFIX  = "FLS_";
	public static final String FLASH_PREFIX = "FL_";

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private Flash flash = null;

	@Inject
	public FlashProvider(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
	}

	public void process(HttpServletRequest request, HttpServletResponse response){//{{{
		this.flash = new Flash(request, response);
	}//}}}

	public Flash get(){
		return this.flash;
	}


}
