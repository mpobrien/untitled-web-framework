package com.mob.forms;
import java.lang.reflect.*;
import java.util.*;

public class TestForm extends Form{

	public enum Crap{
	//public enum Crap implements CustomChoice{
		POOP("1","a"),
		BARF("2","b");
		private final String v, n;
		Crap(String v, String n){ 
			this.v = v;
			this.n = n;
		}
		public String getValue(){ return this.v; }
		public String getOption(){ return this.n; }
	}

	@Override
	protected void fields(){
		stringField( "field1", Rules.required() );
		choiceField( "testoptions", Crap.class );
	}

}
