package com.mob.web;

public class HttpException extends Exception{

	private final int code;
	private final WebResponse output;

    public HttpException(String message, int code){
        super(message);
		this.code = code;
		this.output = null;
    }

    public HttpException(String message, Throwable t, int code){
        super(message, t);
		this.code = code;
		this.output = null;
    }

    public HttpException(String message, Throwable t, int code, WebResponse output){
        super(message, t);
		this.code = code;
		this.output = output;
    }

}
