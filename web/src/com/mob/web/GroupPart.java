package com.mob.web;

public class GroupPart implements MatchingPart, OptionalPart{ 

	private final String name;
	private boolean optional = false;
	private int count = 0;

	public GroupPart(String name){
		this.name = name;
	}
	
	public GroupPart(){
		this.name = null;
	}

	public String toString(){
		if( name != null ){
			return "(<" + this.name + ">)";
		}else{
			return "(_)";
		}
	}

	@Override
	public boolean getOptional(){ return this.optional; }

	@Override
	public void setOptional( boolean optional ){ this.optional = optional; }
	
}
