package com.mob.web;

public class RegexChar{

	Character character;
	boolean representative;

	public RegexChar(Character character, boolean representative){
		this.character = character;
		this.representative = representative;
	}

	public String toString(){
		return (new String()) + character;
	}

}

