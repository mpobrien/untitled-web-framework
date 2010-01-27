package com.mob.forms;

public class Conditions{

	public static Condition all( final Condition... clauses ){
		return new Condition(){
			public boolean isSatisfied(){
				for( Condition c : clauses ){
					if( !c.isSatisfied() ) return false;
				}
				return true;
			}
		};
	}

	public static Condition any( final Condition... clauses ){
		return new Condition(){
			public boolean isSatisfied(){
				for( Condition c : clauses ){
					if( c.isSatisfied() ) return true;
				}
				return false;
			}
		};
	}

	public static Condition not( final Condition con ){
		return new Condition(){
			public boolean isSatisfied(){
				return !con.isSatisfied();
			}
		};
	}

}
