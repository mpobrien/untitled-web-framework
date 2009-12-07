package com.mob.orm;

public abstract class SingleKeyedModel extends Model{

 	public abstract DbField pkField();
	public abstract Object getPrimaryKey();

}
