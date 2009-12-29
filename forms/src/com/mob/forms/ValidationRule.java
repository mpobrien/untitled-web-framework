package com.mob.forms;
import java.lang.reflect.*;
import java.util.*;

public interface ValidationRule<T>{

    public boolean validate(T value);

	public String key();

}
