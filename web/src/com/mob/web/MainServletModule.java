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


public class MainServletModule extends ServletModule {

     protected void configurePreFilters() {
         //filter("/*").through(MyPreFilter.class);
     }
// 
//     protected void configurePostFilters() {
//         //filter("/*").through(MyPostFilter.class);
//     }

	@Override
    protected void configureServlets() {

 		filter("/*").through(SiteFilter.class);
        //serve("/*").with(MainServlet.class);
    }

}
