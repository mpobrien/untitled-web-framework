package com.mob.web;
import javax.servlet.http.*;
import java.io.*;

public class StringWebResponse implements WebResponse{

	private final String outputText;

	public StringWebResponse( String outputText ){
		this.outputText = outputText;
	}

	public void writeResponse(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.getWriter().write( outputText != null ? outputText : "" );
	}

}
