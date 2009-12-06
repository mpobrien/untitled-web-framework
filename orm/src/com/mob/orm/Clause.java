package com.mob.orm;
import com.google.common.base.*;
import com.google.common.collect.*;

public abstract class Clause{
	
	protected boolean not = false;

    public abstract String getSql();

	public abstract Iterable<Object> getValues();

	public Clause negate(){
		this.not = !this.not;
		return this;
	};

}
