package com.mob.web;
import java.util.*;
import com.google.inject.*;
import org.apache.log4j.*;
import com.google.common.collect.*;

public class ContextProcessorChain implements Iterable<Class<? extends ContextProcessor>>{

	private static Logger log = Logger.getLogger(ContextProcessorChain.class);

	private ArrayList<Class<? extends ContextProcessor>> contextProcessors = Lists.newArrayList();

	public ContextProcessorChain(String classesList){//{{{
		if( classesList == null ) return;
		String[] classesListItems = classesList.split(",");
		for( String className : classesListItems ){
			try{
				Class c = Class.forName(className.trim());
				contextProcessors.add((Class<? extends ContextProcessor>)c);
			}catch(ClassNotFoundException cnfe){
				log.error("Class could not be found for name: " + className);
			}catch(ClassCastException cce){
				log.error("Class is not a subclass of ContextProcessor");
			}finally{
				continue;
			}
		}
	}//}}}

	public UnmodifiableIterator<Class<? extends ContextProcessor>> iterator(){//{{{
		return Iterators.unmodifiableIterator( contextProcessors.iterator() );
	}//}}}

}
