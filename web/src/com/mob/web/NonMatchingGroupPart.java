package com.mob.web;

public class NonMatchingGroupPart extends LiteralPart implements MatchingPart{ 

	private boolean optional = false;

	public NonMatchingGroupPart(Character c){
		super(c);
	}

	public NonMatchingGroupPart(String s){
		super(s);
		this.literalString = s;
	}


	public String toString(){
		return "(?:" + this.literalString + ")";
	}

	public boolean getOptional(){ return this.optional; }
	public void setOptional( boolean optional ){ this.optional = optional; }
	
}
