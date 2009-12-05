package com.mob.web;
import com.google.inject.*;
import com.google.inject.servlet.*;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.regex.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.lang.reflect.*;

@Singleton
public class MainServlet extends HttpServlet {

	public MainServlet(){
		System.out.println("main servlet");
	}

    public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String requestUri = req.getRequestURI();
		System.out.println( requestUri + " request uri");
    }

}

