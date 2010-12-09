package com.mob.web;
import javax.servlet.http.*;
import java.io.*;
import com.google.common.collect.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
public class JsonResponse implements WebResponse{

	private final Object jsonObj;
	private ExclusionStrategy exclusionStrategy = null;

	public JsonResponse( Object jsonObj ){
		this.jsonObj = jsonObj;
	}

	public JsonResponse( Object jsonObj, final String... fieldNames){
		this.jsonObj = jsonObj;
		final ImmutableList<String> fieldNamesList = ImmutableList.of(fieldNames);
		this.exclusionStrategy = new ExclusionStrategy(){
			public boolean shouldSkipClass(Class clazz){
				return false;
			}
			public boolean shouldSkipField(FieldAttributes f){
				return !fieldNamesList.contains(f.getName());
			}
		};
	}

	public boolean writeResponse(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json");
        Gson gson;
		if( this.exclusionStrategy != null ){
			gson = new GsonBuilder()
				   .setExclusionStrategies(this.exclusionStrategy)
				   .create();
		}else{
			gson = new Gson();
		}
        String json = gson.toJson(this.jsonObj); 
		response.getWriter().write( json );
		return false;
	}

    public String toString(){
        Gson gson;
		if( this.exclusionStrategy != null ){
			gson = new GsonBuilder()
				   .setExclusionStrategies(this.exclusionStrategy)
				   .create();
		}else{
			gson = new Gson();
		}
		return gson.toJson(this.jsonObj); 
	}

}
