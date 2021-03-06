package HelperClasses;
import java.sql.*;
import java.util.*;

public class ConnectionDatabase{
    public Connection createConnection(String database_name){
        Connection connection   = null;
        try {
			  Class.forName("org.postgresql.Driver");
		} 
		catch (ClassNotFoundException e) {
		    
			System.out.println("Class Not found");
		}
		System.out.println("class loaded");
		try {

String jdbcUrl = String.format(
    "jdbc:postgresql://google/%s?socketFactory=com.google.cloud.sql.postgres.SocketFactory"
        + "&socketFactoryArg=%s",
        database_name,
    "gamecenter");

 connection = DriverManager.getConnection(jdbcUrl, "postgres", "postgres");
//			  connection = DriverManager.getConnection(
//					"jdbc:postgresql://localhost:5432/"+database_name, "postgres", "postgres");
					
		} catch (Exception e) {
			System.out.println("conection failed");
		}
		return connection;
    }
    
    public String insert(Connection connection,String table_name,String column_name,String value) {
	    try{
		    Statement stmt = connection.createStatement();//Statement class creates a object that can execute our query in the connected database in connection object
			String Query="insert into "+table_name+" ("+column_name+") values ("+value+")";//Query to be passed
			
			stmt.executeUpdate(Query);
			try {
				  connection.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			return "Signup Successfull";
	    } catch (SQLException e) {
	    	try {
				  connection.close();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
	        return e+"";
	    }
        
    }
    
    public ResultSet select(Connection connection,String table_name,String column_name,String name) {
    	try{
		    Statement stmt = connection.createStatement();//Statement class creates a object that can execute our query in the connected database in connection object
			String Query="select "+column_name+" from "+table_name+" where username='"+name+"'";//Query to be passed
			System.out.println(Query);
			ResultSet data_table=stmt.executeQuery(Query);//execution
			try {
				  connection.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			return data_table;
	    } catch (SQLException e) {
	    	
	        System.out.println(e+"");
	    }
    	try {
			  connection.close();
			} catch (SQLException e2) {
				
				e2.printStackTrace();
			}
    	return null;
    }
    
    public ResultSet selectCondition(Connection connection,String table_name,String column_name,String conditionValue,String conditionfromColumn) {
    	try{
		    Statement stmt = connection.createStatement();//Statement class creates a object that can execute our query in the connected database in connection object
			String Query="select "+column_name+" from "+table_name+" where "+conditionfromColumn+" in ("+conditionValue+")";//Query to be passed
			System.out.println(Query);
			ResultSet data_table=stmt.executeQuery(Query);//execution
			return data_table;
	    } catch (SQLException e) {
	        System.out.println(e+"");
	    }
    	try {
			  connection.close();
			} catch (SQLException e2) {
				
				e2.printStackTrace();
			}
    	return null;
    }
    
    public ResultSet selectUnCondition(Connection connection,String table_name,String column_name) {
    	try{
		    Statement stmt = connection.createStatement();//Statement class creates a object that can execute our query in the connected database in connection object
			String Query="select "+column_name+" from "+table_name;//Query to be passed
			System.out.println(Query);
			ResultSet data_table=stmt.executeQuery(Query);//execution
			return data_table;
	    } catch (SQLException e) {
	        System.out.println(e+"");
	    }
    	try {
			  connection.close();
			} catch (SQLException e2) {
				
				e2.printStackTrace();
			}
    	return null;
    }
    
}