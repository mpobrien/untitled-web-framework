package com.mob.web;

public class LiteralPart implements MatchingPart, OptionalPart { 
	
	protected String literalString = "";

	public LiteralPart(Character c){
		this.literalString = "" + c;
	}

	public LiteralPart(String s){
		this.literalString = s;
	}

	public void append(String s){
		this.literalString += s;
	}

	public void append(Character c){
		this.literalString += c;
	}

	public void reset(String s){
		this.literalString = s;
	}

	public String getString(){
		return this.literalString;
	}
	
	public String toString(){
		return this.literalString;
	}

	public boolean getOptional(){ return true; }

	public void setOptional(boolean optional){
		if( optional ){
			if( this.literalString.length() > 0 ){
				this.literalString = this.literalString.substring( 0, literalString.length() - 1 );
			}
		}
	}

}
