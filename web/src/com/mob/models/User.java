package com.mob.models;
import com.mob.orm.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import javax.sql.*;
import java.sql.*;

public class User extends Model{
    private boolean isDirty = false;
    private boolean isNew = true;

//Fields
    protected Integer userId;
    protected String name;
    protected Integer photoId;

	public enum UserField implements DbField {
        user_id("user_id"),
        name("name"),
        photo_id("photo_id");

        private String column;
        UserField(String column){ this.column = column; }
        public String table(){ return "user"; } 
        public String column(){ return this.column; }
	}

    public Integer getPrimaryKey(){ return 0; }

    public DbField pkField(){ return UserField.user_id; }
    public static final ImmutableList<DbField> FIELDS = new ImmutableList.Builder<DbField>().addAll( Lists.newArrayList( UserField.values() ) ).build();
    private static ModelManagerFactory<User> managerFactory = new ModelManagerFactory<User>( User.class );
    public static ModelManager<User> objects( ConnectionProvider connProvider ){ return managerFactory.get( connProvider ); }
    public static <T extends User> ModelManager<T> objects( Class<T> returnClass, ConnectionProvider connProvider ){ return new ModelManager<T>(returnClass, connProvider ); }
    public ImmutableList<DbField> fields(){ return FIELDS; }


    private void setDirty(){ this.isDirty = true; }
    private void unsetDirty(){ this.isDirty = false; }

//Getters//{{{
    public Integer getUserId(){ return this.userId; }
    public String getName(){ return this.name; }
    public Integer getPhotoId(){ return this.photoId; }
//}}}

//Setters //{{{
    public void setUserId( Integer userId ){
        this.userId = userId; 
    }
    public void setName( String name ){
        this.name = name; 
    }
    public void setPhotoId( Integer photoId ){
        this.photoId = photoId; 
    }
//}}}

	public User(){}

	public User( BasicResultSet rs, int offset, int size ){
		this.populateWithRS( rs, offset, size );
	}

	public void populateWithRS( BasicResultSet rs, int offset, int size ) {
		this.userId = rs.getInteger(offset + 0 );
		this.name = rs.getString(offset + 1 );
		this.photoId = rs.getInteger(offset + 2 );
        this.isNew = false;
        this.isDirty = false;
    }


    public void save(ConnectionProvider cp){
		Object[] args = new Object[3];
        if( this.isNew ){
            String sql = "INSERT INTO user (user_id,name,photo_id) values (?,?,?)";
            args[ 0 ] = null;
            args[ 1 ] = this.name;
            args[ 2 ] = this.photoId;
            this.userId = this.objects(cp).updateGetKey(sql, args);
            this.isNew = false;
            this.isDirty = false;
        }else{
		    String sql = "UPDATE user SET name = ?, photo_id = ? WHERE user_id = ?";
			System.out.println(sql);
			
		    args[0] = this.name;

		    args[1] = this.photoId;
		    args[2] = this.userId;
            for( int i = 0; i< args.length; i++){
			    System.out.println(args[i]);
            }
            this.objects(cp).update(sql, args);
        }
    }

    public void delete(ConnectionProvider cp){
        if( this.userId == null || this.isNew ){
            throw new IllegalStateException("Can't delete an instance of a model that has no primary key.");
        }else{
            String sql = "DELETE FROM user WHERE user.user_id = ?";
            this.objects(cp).update(sql, this.userId );
        }
    }

    protected String showFields(){
        StringBuilder retVal = new StringBuilder("");
        retVal.append("{").append("userId=").append( this.userId != null ? this.userId.toString() : "null").append("},");
        retVal.append("{").append("name=").append( this.name != null ? this.name.toString() : "null").append("},");
        retVal.append("{").append("photoId=").append( this.photoId != null ? this.photoId.toString() : "null").append("}]");
        return retVal.toString();
    }

    public String toString(){
        StringBuilder retVal = new StringBuilder("[" + this.getClass().getName() + "<userId=" + this.userId + ">: ");
        retVal.append( showFields() );
        return retVal.toString();
    }

}
