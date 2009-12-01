package com.mob.web;
import com.google.common.base.*;
import com.google.common.collect.*;

public class C{

	public static Predicate prefixFilter(String prefix){
		final String prefixStr = prefix;
		return new Predicate<String>(){ 
			public boolean apply(String key){
				return key.startsWith( prefixStr );
			}
		};
	}

}
