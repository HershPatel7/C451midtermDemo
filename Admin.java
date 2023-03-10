package MidtermDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Admin extends BankManager {
	
	Admin(String username, String password, String firstName, String lastName, String employeeID, String userID){
		super(username, password, firstName, lastName, employeeID, userID);
		this.membershipType = "Admin";
	}
	
public static String createBankManager(BankManager mgr) {
		
	    String info = "";
	    
	    try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
	        PreparedStatement stmt = myConnection.prepareStatement("INSERT INTO `BankManagers`(`Username`, `Password`, `FirstName`,`LastName`, `EmployeeID`, `UserID`) VALUES (?,?,?,?,?,?)");
	    	PreparedStatement stmt2 = myConnection.prepareStatement("INSERT INTO `Credentials`(`Username`,`Password`, `UserID`, `Type`) VALUES (?,?,?,?)");){
	    	stmt.setString(1, mgr.username);
	    	stmt.setString(2, mgr.password);
	    	stmt.setString(3, mgr.firstName);
	    	stmt.setString(4, mgr.lastName);
	    	stmt.setString(5, mgr.employeeID);
	    	stmt.setString(6, mgr.userID);
	    	int rowsAffected = stmt.executeUpdate();

	    	stmt2.setString(1, mgr.username);
	    	stmt2.setString(2, mgr.password);
	    	stmt2.setString(3, mgr.userID);
	    	stmt2.setString(4, mgr.membershipType);
	    	stmt2.executeUpdate();
	    	
	    	
	        if (rowsAffected > 0) {
	            info += "Bank Manager Created."; 
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


