package com.mob.orm;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import javax.sql.*;
import org.apache.log4j.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.Set;
import java.util.List;

public class QueryExecutor{
	private static final Logger log = Logger.getLogger( QueryExecutor.class );

    private final ConnectionProvider connProvider;

    public QueryExecutor(ConnectionProvider connProvider){//{{{
        this.connProvider = connProvider;
    }//}}}

	public BasicResultSet execute(String sql, Object... args){//{{{
		debug( sql, args );
		if( args == null || args.length == 0){
			return execute( sql );
		}
		return execute( sql, Arrays.asList( args ) );
	}//}}}

    public BasicResultSet execute( String sql ){//{{{
		debug(sql, (Object[])null);
        ResultSet rs = null;
        Statement stmt = null;
		Connection conn = connProvider.getConnection();
        try{
			stmt = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			rs = stmt.executeQuery( sql );
            BasicResultSet brs = new BasicResultSet( rs );
            rs.close();
            return brs;
        }catch(SQLException e){
			e.printStackTrace();
			return null;
            //TODO handle the sql exception here somehow
        }finally{
			cleanUp( rs, stmt, conn );
        }
    }//}}}

	public BasicResultSet execute( String sql, Iterable<Object> args ){//{{{
		debug(sql, args);
		if( args == null || !args.iterator().hasNext() ) 
			return execute( sql );

		ResultSet rs = null;
		PreparedStatement prepStmt = null;
		Connection conn = connProvider.getConnection();
		try{
			prepStmt = conn.prepareStatement( sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			Iterator<Object> it = args.iterator();
			int i = 0;
			while( it.hasNext() ){
				Object o = it.next();
				prepStmt.setObject( i + 1, o );
				i++ ;
			}
			prepStmt.execute(); 
            BasicResultSet brs = new BasicResultSet( prepStmt.getResultSet());
			return brs;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
			//TODO handle the sql exception here somehow
		}finally{
			cleanUp( rs, prepStmt, conn );
		}
	}//}}}

	private static void cleanUp(ResultSet rs, Statement stmt, Connection conn){//{{{
		if ( rs != null ) {
			try { rs.close(); } catch ( SQLException e ) { }
			rs = null;
		}
		if ( stmt != null ) {
			try { stmt.close(); } catch ( SQLException e ) { }
			stmt = null;
		}
		if ( conn != null ) {
			try { conn.close(); } catch ( SQLException e ) { }
		}
	}//}}}

    public BasicResultSet execute( SelectQuerySet selectStatement ){//{{{
        String sql = selectStatement.getSql();
        Iterable<Object> args = selectStatement.getValues();
		return execute( sql, args );
    }//}}}

    public Integer updateGetKey( String sql, Object... args){//{{{
		debug(sql, args);
		PreparedStatement stmt = null;
		ResultSet rs   = null;
		Connection conn = connProvider.getConnection();

		try {
			stmt = conn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			for ( int i = 0; i < args.length; i++ ) {
				stmt.setObject( i + 1, args[i] );
			}
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if ( rs.next() ) {
				return rs.getInt( 1 );
			} else {
				//TODO fix
				return null;
			}
		}
		catch ( SQLException e ) {
			//TODO fix
			e.printStackTrace();
			return null;
		}
		finally {
			cleanUp( rs, stmt, conn );
		}
	}//}}}

    public void update( String sql, Object... args){//{{{
		debug(sql, args);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = connProvider.getConnection();

		try {
			stmt = conn.prepareStatement( sql );
			for ( int i = 0; i < args.length; i++ ) {
				stmt.setObject( i + 1, args[i] );
			}
			stmt.execute();
		}
		catch ( SQLException e ) {
			//TODO fix
			e.printStackTrace();
		}
		finally {
			cleanUp( rs, stmt, conn );
		}
	}//}}}

	public void debug(String sql, Object... args){
		log.info(sql);
		if( args != null && args.length > 0 ){
			List<Object> lArgs = Arrays.asList(args);
			log.info( Constants.joinCommas.join( Iterables.transform( lArgs, Constants.toString ) ) );
		}
	}

	public void debug(String sql, Iterable<Object> lArgs){
		log.info(sql);
		if( lArgs != null && lArgs.iterator().hasNext() ){
			log.info( Constants.joinCommas.join( Iterables.transform( lArgs, Constants.toString ) ) );
		}
	}

}
