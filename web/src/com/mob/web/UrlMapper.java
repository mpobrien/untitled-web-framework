package com.mob.web;
import com.google.inject.*;

@ImplementedBy(AnnotatedUrlMapper.class)
public interface UrlMapper{

	public ControllerRequest matchUrl(String requestUri);

}
