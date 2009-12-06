package com.mob.orm;
import com.google.common.base.*;
import com.google.common.collect.*;

public interface ClauseGenerator{

	public Clause make(Object value);

}
