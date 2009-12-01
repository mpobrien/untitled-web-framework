package com.mob.web;
import javax.servlet.http.*;
import java.io.*;

public class RedirectResponse implements WebResponse{

	private final String redirectUrl;
	
	public RedirectResponse(String redirectUrl){
		this.redirectUrl = redirectUrl;
	}

	public void writeResponse(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.sendRedirect( redirectUrl );
	}

}
