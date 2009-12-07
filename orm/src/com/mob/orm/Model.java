package com.mob.orm;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.List;

public abstract class Model{

	public abstract void insert(ConnectionProvider cp);
	public abstract List<DbField> fields();
	public abstract void populateWithRS( BasicResultSet rs, int offset, int size );

}
