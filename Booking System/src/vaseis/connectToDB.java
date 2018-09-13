package vaseis;

import java.sql.*;
public class connectToDB {
	
	public Connection conn;
	private String db;
	
	
	public connectToDB(String db) throws Exception{
		
		this.db=db;
		//Trying to get the driver
		try {
			Class.forName("org.postgresql.Driver");
			
			
			
		}
		catch (java.lang.ClassNotFoundException e) {
			java.lang.System.err.print("ClassNotFoundException: Postgres Server JDBC");
			java.lang.System.err.println(e.getMessage());
			throw new Exception("No JDBC Driver found in Server");
		}
		
		//Trying to connectpostgresql:/
		try {
			
			String mysqlURL= "jdbc:postgresql://localhost:5432/MyDB?searchpath=project";
			conn = DriverManager.getConnection(mysqlURL,"postgres","3637");
     		
			
			//conn.setCatalog(db);
			System.out.println("Connection with: "+db+"!!");
		}
		catch (SQLException E) {

			java.lang.System.out.println("SQLException: " + E.getMessage());
			java.lang.System.out.println("SQLState: " + E.getSQLState());
			java.lang.System.out.println("VendorError: " + E.getErrorCode());
		
		}
	}
	
	
	//Close Conn
	public void close() throws SQLException{
		
		try {
			conn.close();
			System.out.println("Connection close ");
		} catch (SQLException E) {
			
			
			java.lang.System.out.println("SQLException: " + E.getMessage());
			java.lang.System.out.println("SQLState: " + E.getSQLState());
			java.lang.System.out.println("VendorError: " + E.getErrorCode());
			throw E;
		}
		
	}
	
	

}
