package com.mob.web;
import com.google.inject.*;
import java.util.*;

@ImplementedBy(AnnotatedUrlMapper.class)
public interface UrlMapper{

	public ControllerRequest matchUrl(String requestUri);

	public Map<java.util.regex.Matcher,Class<? extends Controller>> getMappings();

}
