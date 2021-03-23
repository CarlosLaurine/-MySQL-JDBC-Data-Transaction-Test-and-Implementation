package db;

/*Creating custom exception DBIntegrityException extending 
RuntimeException for best development purposes
*/

/*DBIntegrityException is set to treat usual DataBase Integrity
  Exceptions at data Deletion process, e.g the deletion of a 
  category which is connected through a foreign key to one or more
  products at a product table, thus leaving the product with
  an error at its FK
   */

public class DbIntegrityException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public DbIntegrityException(String message) {
		
		super(message);
		
	}
	

}
