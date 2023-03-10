package MidtermDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankClerk {
	String username;
	String password;
	String firstName;
	String lastName;
	String employeeID;
	String userID;
	String membershipType = "Clerk";
	
	BankClerk(String username, String password, String firstName, String lastName, String employeeID, String userID){
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeID = employeeID;
		this.userID = userID;
	}
	
	
public static String createBasicMember(BasicMember member) {
		
		
		
	    String info = "";
	    
	    try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
	        PreparedStatement stmt = myConnection.prepareStatement("INSERT INTO `BasicMembers`(`Username`, `Password`, `FirstName`,`LastName`, `Address`, `CheckingAccountNumber`, `SavingsAccountNumber`, `UserID` ) VALUES (?,?,?,?,?,?,?,?)");
	    	PreparedStatement stmt2 = myConnection.prepareStatement("INSERT INTO `Credentials`(`Username`, `Password`, `UserID`,`Type`) VALUES (?,?,?,?)");
	    	PreparedStatement stmt3 = myConnection.prepareStatement("INSERT INTO `CheckingAccounts`(`UserID`, `CheckingBalance`, `Transactions`, `AccountNumber`) VALUES (?,?,?,?)");
	    	PreparedStatement stmt4 = myConnection.prepareStatement("INSERT INTO `SavingsAccounts`(`UserID`, `SavingsBalance`, `Transactions`, `AccountNumber`) VALUES (?,?,?,?)");	){
	    	
	    	stmt.setString(1, member.username);
	    	stmt.setString(2, member.password);    
	    	stmt.setString(3, member.firstName);
	    	stmt.setString(4, member.lastName); 
	    	stmt.setString(5, member.customerAddress);
	    	stmt.setString(6, member.checkingAccount.accountNumber);    
	    	stmt.setString(7, member.savingsAccount.accountNumber);   
	    	stmt.setString(8, member.userID); 
	    	int rowsAffected = stmt.executeUpdate();
	    	
	    	stmt2.setString(1, member.username);
	        stmt2.setString(2, member.password);    
	        stmt2.setString(3, member.userID); 
	        stmt2.setString(4, member.membershipType);
	        stmt2.executeUpdate();
	        
	        stmt3.setString(1,member.userID);
	        stmt3.setDouble(2,member.checkingAccount.balance);
	        stmt3.setInt   (3,member.checkingAccount.transactions);
	        stmt3.setString(4,member.checkingAccount.accountNumber);
	        stmt3.executeUpdate();
	        
	        stmt4.setString(1,member.userID);
	        stmt4.setDouble(2,member.savingsAccount.balance);
	        stmt4.setInt   (3,member.savingsAccount.transactions);
	        stmt4.setString(4,member.savingsAccount.accountNumber);
	        stmt4.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            info += "It worked!"; 
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
	
	
	
	
	public static String createStandardMember(StandardMember member) {
		
		
		
	    String info = "";
	    
	    try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root"); //// not checked after this point
	        PreparedStatement stmt = myConnection.prepareStatement("INSERT INTO `StandardMembers`(`Username`, `Password`, `FirstName`,`LastName`, `Address`, `CheckingAccountNumber`, `SavingsAccountNumber`,`CreditCardAccountNumber`, `UserID` ) VALUES (?,?,?,?,?,?,?,?,?)");
	    	PreparedStatement stmt2 = myConnection.prepareStatement("INSERT INTO `Credentials`(`Username`, `Password`, `UserID`,`Type`) VALUES (?,?,?,?)");
	    	PreparedStatement stmt3 = myConnection.prepareStatement("INSERT INTO `CheckingAccounts`(`UserID`, `CheckingBalance`, `Transactions`, `AccountNumber`) VALUES (?,?,?,?)");
	    	PreparedStatement stmt4 = myConnection.prepareStatement("INSERT INTO `SavingsAccounts`(`UserID`, `SavingsBalance`, `Transactions`, `AccountNumber`) VALUES (?,?,?,?)");
	    	PreparedStatement stmt5 = myConnection.prepareStatement("INSERT INTO `CreditCardAccounts`(`UserID`, `CreditCardBalance`,`CreditLimit`, `Transactions`, `AccountNumber`) VALUES (?,?,?,?,?)");){
	    	
	    	stmt.setString(1, member.username);
	    	stmt.setString(2, member.password);    
	    	stmt.setString(3, member.firstName);
	    	stmt.setString(4, member.lastName); 
	    	stmt.setString(5, member.customerAddress);
	    	stmt.setString(6, member.checkingAccount.accountNumber);    
	    	stmt.setString(7, member.savingsAccount.accountNumber);  
	    	stmt.setString(8, member.creditAccount.accountNumber); 
	    	stmt.setString(9, member.userID); 
	    	int rowsAffected = stmt.executeUpdate();
	    	
	    	stmt2.setString(1, member.username);
	        stmt2.setString(2, member.password);    
	        stmt2.setString(3, member.userID); 
	        stmt2.setString(4, member.membershipType);
	        stmt2.executeUpdate();
	        
	        stmt3.setString(1,member.userID);
	        stmt3.setDouble(2,member.checkingAccount.balance);
	        stmt3.setInt   (3,member.checkingAccount.transactions);
	        stmt3.setString(4,member.checkingAccount.accountNumber);
	        stmt3.executeUpdate();
	        
	        stmt4.setString(1,member.userID);
	        stmt4.setDouble(2,member.savingsAccount.balance);
	        stmt4.setInt   (3,member.savingsAccount.transactions);
	        stmt4.setString(4,member.savingsAccount.accountNumber);
	        stmt4.executeUpdate();
	        
	        stmt5.setString(1,member.userID);
	        stmt5.setDouble(2,member.creditAccount.balance);
	        stmt5.setDouble(3,member.creditAccount.limit);
	        stmt5.setInt   (4,member.creditAccount.transactions);
	        stmt5.setString(5,member.creditAccount.accountNumber);
	        stmt5.executeUpdate();
	        
	        
	        
	        if (rowsAffected > 0) {
	            info += "It worked!"; 
	        }        
	        else {
	            info += "It did not work!";
	        }
	        
	        
	    } catch (SQLException exc) {
	        exc.printStackTrace();
	        info += "It did not work!";
	    }
	    return info;
	}
	
	
	
	
public static String createPremiumMember(PremiumMember member) {
		
		
		
	    String info = "";
	    
	    try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root"); //// not checked after this point
	        PreparedStatement stmt = myConnection.prepareStatement("INSERT INTO `PremiumMembers`(`Username`, `Password`, `FirstName`,`LastName`, `Address`, `CheckingAccountNumber`, `SavingsAccountNumber`,`CreditCardAccountNumber`,`MortgageAccountNumber`, `UserID` ) VALUES (?,?,?,?,?,?,?,?,?,?)");
	    	PreparedStatement stmt2 = myConnection.prepareStatement("INSERT INTO `Credentials`(`Username`, `Password`, `UserID`,`Type`) VALUES (?,?,?,?)");
	    	PreparedStatement stmt3 = myConnection.prepareStatement("INSERT INTO `CheckingAccounts`(`UserID`, `CheckingBalance`, `Transactions`, `AccountNumber`) VALUES (?,?,?,?)");
	    	PreparedStatement stmt4 = myConnection.prepareStatement("INSERT INTO `SavingsAccounts`(`UserID`, `SavingsBalance`, `Transactions`, `AccountNumber`) VALUES (?,?,?,?)");
	    	PreparedStatement stmt5 = myConnection.prepareStatement("INSERT INTO `CreditCardAccounts`(`UserID`, `CreditCardBalance`,`CreditLimit`, `Transactions`, `AccountNumber`) VALUES (?,?,?,?,?)");
	    	PreparedStatement stmt6 = myConnection.prepareStatement("INSERT INTO `MortgageAccounts`(`UserID`, `MortgageBalance`,`LoanAmount`, `Transactions`, `AccountNumber`) VALUES (?,?,?,?,?)");){
	    	
	    	stmt.setString(1, member.username);
	    	stmt.setString(2, member.password);    
	    	stmt.setString(3, member.firstName);
	    	stmt.setString(4, member.lastName); 
	    	stmt.setString(5, member.customerAddress);
	    	stmt.setString(6, member.checkingAccount.accountNumber);    
	    	stmt.setString(7, member.savingsAccount.accountNumber);  
	    	stmt.setString(8, member.creditAccount.accountNumber); 
	    	stmt.setString(9, member.mortgageAccount.accountNumber); 
	    	stmt.setString(10, member.userID); 
	    	int rowsAffected = stmt.executeUpdate();
	    	
	    	stmt2.setString(1, member.username);
	        stmt2.setString(2, member.password);    
	        stmt2.setString(3, member.userID); 
	        stmt2.setString(4, member.membershipType);
	        stmt2.executeUpdate();
	        
	        stmt3.setString(1,member.userID);
	        stmt3.setDouble(2,member.checkingAccount.balance);
	        stmt3.setInt   (3,member.checkingAccount.transactions);
	        stmt3.setString(4,member.checkingAccount.accountNumber);
	        stmt3.executeUpdate();
	        
	        stmt4.setString(1,member.userID);
	        stmt4.setDouble(2,member.savingsAccount.balance);
	        stmt4.setInt   (3,member.savingsAccount.transactions);
	        stmt4.setString(4,member.savingsAccount.accountNumber);
	        stmt4.executeUpdate();
	        
	        stmt5.setString(1,member.userID);
	        stmt5.setDouble(2,member.creditAccount.balance);
	        stmt5.setDouble(3,member.creditAccount.limit);
	        stmt5.setInt   (4,member.creditAccount.transactions);
	        stmt5.setString(5,member.creditAccount.accountNumber);
	        stmt5.executeUpdate();
	        
	        stmt6.setString(1,member.userID);
	        stmt6.setDouble(2,member.mortgageAccount.balance);
	        stmt6.setDouble(3,member.mortgageAccount.loanAmount);
	        stmt6.setInt   (4,member.mortgageAccount.transactions);
	        stmt6.setString(5,member.mortgageAccount.accountNumber);
	        stmt6.executeUpdate();
	        
	        
	        
	        if (rowsAffected > 0) {
	            info += "It worked!"; 
	        }        
	        else {
	            info += "It did not work!";
	        }
	        
	        
	    } catch (SQLException exc) {
	        exc.printStackTrace();
	        info += "It did not work!";
	    }
	    return info;
	}
	
	
	
	
	
}
