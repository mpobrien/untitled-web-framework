package com.mob.web;
import javax.servlet.http.*;
import java.io.*;
import com.google.gson.Gson;
public class JsonResponse implements WebResponse{

	private final Object jsonObj;

	public JsonResponse( Object jsonObj ){
		this.jsonObj = jsonObj;
	}

	public boolean writeResponse(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(this.jsonObj); 
		response.getWriter().write( json );
		return false;
	}

}
