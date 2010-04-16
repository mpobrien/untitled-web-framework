package com.mob.forms;

public interface FormField<T>{

    public T getValue();

    public void setValue(T val);

	public String getName();
    
}
