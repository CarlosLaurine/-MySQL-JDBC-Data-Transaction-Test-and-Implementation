  package application;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DBException;

public class MainProgram {

	public static void main(String[] args) {
		
		
		//Initiating null variables with the JDBC Class-Types needed for selection
		Connection con = null;
		
		//Using Method Statement to set query command
		Statement st = null;
		
		
		//Using try catch body to treat SQL exceptions
		try {
			
		//Opening db connection	
		con = DB.getConnection();
		
	/*============================================= TRANSACTION IMPLEMENTATION ============================================= */	
		
		
		/*Calling .setAutoCommit(false) in order to disable the operations'
		  auto-commits, thus leaving this responsibility to the explicit 
		  declaration from the programmer. This technique allows the 
		  Transaction to be securely implemented (if a single operation 
		  is interrupted, then all operations are interrupted)
		*/
		
		con.setAutoCommit(false);
		
		//Instancing Statement
		
		st = con.createStatement();
		
		/*OBS1: It is needed to always pay attention to the existence of restriction
		  "where", since a update process without restriction will be spread to 
		  all the elements at the table. */
		
		//OBS2: No case sensitivity at the command string
		
		/*Executing command with all updates with an integer of net number of 
        lines as return, and saving this information in an int variable 
        rowsChanged1
        */
		
		int rowsChanged1 = st.executeUpdate("UPDATE seller SET BaseSalary = 3755 WHERE DepartmentId = 1");
		
		/* ================================ Transaction Failure Simulation ================================
		  int x = 1;
		  
		  //Obvious condition:
		  
		  if(x<20){
		  
		  	throw new SQLException ("Simulation Error without Transaction")
		  
		  }
		  
		 /*This way, it was tested that, if an exception happens between
		   update executions without a transaction protection, the ones 
		   that happened before the exception was thrown will remain, while
		   the others will never happen. This is a big point for database 
		   manipulation (e.g bank transactions) and should be taken care of 
		   carefully */
		
		/*OBS: After complete transaction logic was implemented, this same
		  test has shown that all operations were securely and fully implemented
		  (Only two options : all of them or none) 
		*/
		 		
		/*Executing command with all updates with an integer of net number of 
        lines as return, and saving this information in an int variable 
        rowsChanged2
        */
		int rowsChanged2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3755 WHERE DepartmentId = 1");

		/*Explicit permission from the programmer for the operations to be fully commited.
		  In other words, .commit() confirms that the Transaction is over. 
		 */
		con.commit();
		
		//Printing the number of rows changed by each update
		
		System.out.println("Done! Number of Rows Changed: " + rowsChanged1);
		
		System.out.println("Done! Number of Rows Changed: " + rowsChanged2);

		}
		//Handling specific exceptions
		catch (SQLException e) {
			//Treating rollback exceptions with try catch
			try{
			//Rolling back transaction in case it is interrupted
			con.rollback();
			throw new DBException ("The Transaction needed to be rolled back! This was caused by -> " + e.getMessage());
			}
			catch(SQLException e1) {
				throw new DBException ("An Error Occurred While Trying To Roll Back the Transaction! Caused by -> " + e1.getMessage());
			}
			}
		
		
		//Using finally block to ensure all external resources to JVM will be closed
		finally {
			
			DB.closeConnection();
			
			//Upcast in closeStatement as PreparedStatement st is passed as a Statement parameter
			
			DB.closeStatement(st);
			
			
		}


}}