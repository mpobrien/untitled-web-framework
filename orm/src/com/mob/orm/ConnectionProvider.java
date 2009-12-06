package com.mob.orm;
import java.sql.*;
import javax.sql.*;

public interface ConnectionProvider{

    public Connection getConnection();

}
