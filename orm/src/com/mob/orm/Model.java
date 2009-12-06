package com.mob.orm;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.List;

public abstract class Model{

	public abstract List<DbField> fields();
	public abstract DbField pkField();
	public abstract Integer getPrimaryKey();
	public abstract void populateWithRS( BasicResultSet rs, int offset, int size );

}
