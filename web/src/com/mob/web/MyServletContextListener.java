package com.mob.web;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.inject.*;
import com.google.inject.servlet.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.URL;
import java.util.*;
import java.util.regex.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;

public @Singleton class MyServletContextListener extends GuiceServletContextListener {
	private static Logger log = Logger.getLogger(AnnotatedUrlMapper.class);

	//@Override
	protected Injector getInjector() {
		Injector injector = Guice.createInjector( new MainServletModule(), new AppConfigModule() );
		return injector;
	}

}
