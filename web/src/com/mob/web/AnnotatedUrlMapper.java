package com.mob.web;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.inject.*;
import com.google.inject.name.*;
import java.util.*;
import java.util.regex.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;
import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.subclassesOf;

@Singleton
public class AnnotatedUrlMapper implements UrlMapper, ReverseMapper{

	private static Logger log = Logger.getLogger(AnnotatedUrlMapper.class);
	private Map<java.util.regex.Matcher,Class<? extends Controller>> mappings = Maps.newHashMap();
	private final List<Package> controllerPackages;
	private final Map<Class<? extends Controller>, ReverseMatch> reverseMatchers = Maps.newHashMap();

	@Inject
	public AnnotatedUrlMapper( @Named("controller.packages") List<Package> controllerPackages ){//{{{
		this.controllerPackages = controllerPackages;
		Set<Class<?>> set = Sets.newHashSet();
    	for (Package pkg : controllerPackages) {
      		set.addAll( Classes.matching( subclassesOf( Controller.class ).and( annotatedWith( At.class ) ) ).in( pkg ) );
		}

		Iterator<Class<?>> it = set.iterator();
		while( it.hasNext() ){
			Class<? extends Controller> c1 = (Class<? extends Controller>)it.next();
			At mapping = c1.getAnnotation(At.class);
			String regex = mapping.value();
			Pattern pat = Pattern.compile(regex);
			Matcher mat = pat.matcher("");
			mappings.put( mat, c1 );
			try{
				ReverseMatch rm = new ReverseMatch( regex );
				this.reverseMatchers.put( c1, rm );
			}catch(Exception e){
				log.warn("Couldn't create reversible mapping for: " + c1);
			}
		}

    }//}}}

	@Override
	public ControllerRequest matchUrl(String requestUri){//{{{
		Iterator it = this.mappings.entrySet().iterator();
		while ( it.hasNext() ) {
			Map.Entry<Matcher,Class<? extends Controller>> pairs = (Map.Entry<Matcher, Class<? extends Controller>>)it.next();
			Matcher matcher = pairs.getKey().reset( requestUri );
			Class<? extends Controller> controlClass = pairs.getValue();
			if( matcher.matches() ){
				return new ControllerRequest(controlClass, matcher);
			}
		}
		return null; //no match found.
	}//}}}

	public Map<java.util.regex.Matcher,Class<? extends Controller>> getMappings(){
		return this.mappings;
	}

    public String toString(){
		return "AnnotatedUrlMapper -mappings: " + mappings.toString();
	}

	public String reverseMatch(Class<? extends Controller> c1, String... args){
		ReverseMatch rm = this.reverseMatchers.get( c1 );
		if( rm == null ){
			throw new RuntimeException("No reverse matcher found for class: " + c1);
		}else{
			return rm.reverse( args );
		}
	}

}
