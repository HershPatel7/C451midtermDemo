package MidtermDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankManager extends BankClerk {

	BankManager(String username, String password, String firstName, String lastName, String employeeID, String userID){
		super(username, password, firstName, lastName, employeeID, userID);
		this.membershipType = "Manager";
	}
	
	
public static String createBankClerk(BankClerk clerk) {
		
	    String info = "";
	    
	    try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
	        PreparedStatement stmt = myConnection.prepareStatement("INSERT INTO `BankClerks`(`Username`, `Password`, `FirstName`,`LastName`, `EmployeeID`, `UserID`) VALUES (?,?,?,?,?,?)");
	    	PreparedStatement stmt2 = myConnection.prepareStatement("INSERT INTO `Credentials`(`Username`,`Password`, `UserID`, `Type`) VALUES (?,?,?,?)");){
	    	stmt.setString(1, clerk.username);
	    	stmt.setString(2, clerk.password);
	    	stmt.setString(3, clerk.firstName);
	    	stmt.setString(4, clerk.lastName);
	    	stmt.setString(5, clerk.employeeID);
	    	stmt.setString(6, clerk.userID);
	    	int rowsAffected = stmt.executeUpdate();

	    	stmt2.setString(1, clerk.username);
	    	stmt2.setString(2, clerk.password);
	    	stmt2.setString(3, clerk.userID);
	    	stmt2.setString(4, clerk.membershipType);
	    	stmt2.executeUpdate();
	    	
	    	
	        if (rowsAffected > 0) {
	            info += "Bank Clerk Created."; 
	        }        
	        else {
	            info += "It did not work!";
	        }
	        
	        
	    } 
	    
	    catch (SQLException exc) {
	        exc.printStackTrace();
	        info += "It did not work!";
	    }
	    return info;
	}
}
