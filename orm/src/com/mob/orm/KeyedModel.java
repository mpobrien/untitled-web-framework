package com.mob.orm;
import java.util.List;

public abstract class KeyedModel extends Model{

 	public abstract List<DbField> pkFields();
	public abstract void update(ConnectionProvider cp);

}
