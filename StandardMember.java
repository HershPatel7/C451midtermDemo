package MidtermDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StandardMember extends BasicMember {
	CreditCardAccount creditAccount; 
	
	

	StandardMember(String username, String password, String firstName, String lastName, String customerAddress,
			CheckingAccount checkingAccount, SavingsAccount savingsAccount, CreditCardAccount creditAccount, String userID) {
		super(username, password, firstName, lastName, customerAddress, checkingAccount, savingsAccount, userID);
		this.creditAccount = creditAccount;
		this.membershipType = "Standard";
		
	
	} // end of Constructor
	
	
	static String resetPassword(String password, String userID){
		String info = "";
		try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");

		        PreparedStatement stmt = myConnection.prepareStatement("UPDATE `StandardMembers` SET `Password`= ?  WHERE UserID = ?");
				PreparedStatement stmt2 = myConnection.prepareStatement("UPDATE `Credentials` SET `Password`= ?  WHERE UserID = ?")){
		    	stmt.setString(1, password);
		    	stmt.setString(2, userID);
		    	int rowsAffected = stmt.executeUpdate();
		    	
		    	stmt2.setString(1, password);
		    	stmt2.setString(2, userID);
		    	stmt2.executeUpdate();


		        
		        if (rowsAffected > 0) {
		            info += "Result: Successfully Changed Password."; 
		        }
		        
		        if (rowsAffected == 0) {
		        	info += "Result: Sorry, Password Change Unsucessful.";
		        }
		        System.out.println("");
		} 
		catch (SQLException exc) {
		        exc.printStackTrace();
		        
		}

		return info;
		
	}
	
	
	static void viewCCinfo(StandardMember member){
		System.out.println("");
		System.out.println("Credit Card Account Information");
		System.out.println("--------------------------------------------------");
		System.out.println("Member Name: " + member.firstName + " " + member.lastName);
		System.out.println("Member Type: " + member.membershipType);
		System.out.println("Credit Card Account Number: " + member.creditAccount.accountNumber);
		System.out.println("Credit Card Balance: " + member.creditAccount.balance);
		System.out.println("Credit Limit: " + member.creditAccount.limit);
		System.out.println("Number of Payments Made: " + member.creditAccount.transactions);
		System.out.println("--------------------------------------------------");
	}
	
	
	static String makeCCPayment(double amount, String checkingAccountNum, String creditCardAccountNum, double checkingBalance, double creditCardBalance, int creditTransactions){
		String info = "";
		double newCheckingBalance = checkingBalance - amount;
		double newCreditCardBalance = creditCardBalance - amount;
		int transx = creditTransactions ++;
		System.out.println("");
		
		
		try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");

		        PreparedStatement stmt = myConnection.prepareStatement("UPDATE `CreditCardAccounts` SET `CreditCardBalance`= ?, `Transactions`= ? WHERE AccountNumber = ?");
				PreparedStatement stmt2 = myConnection.prepareStatement("UPDATE `CheckingAccounts` SET `CheckingBalance`= ? WHERE AccountNumber = ?")){
		    	stmt.setDouble(1, newCreditCardBalance);
		    	stmt.setInt(2, transx);
		    	stmt.setString(3, creditCardAccountNum);    

		    	int rowsAffected = stmt.executeUpdate();
		    	
		    	stmt2.setDouble(1, newCheckingBalance);
		    	stmt2.setString(2,checkingAccountNum);
		    	stmt2.executeUpdate();

		        
		        
		        if (rowsAffected > 0) {
		            info += "Result: Successful Payment Made."; 
		        }
		        
		        if (rowsAffected == 0) {
		        	info += "Result: Sorry, Payment Was Unsuccessful.";
		        }
		        System.out.println("");
		} 
		catch (SQLException exc) {
		        exc.printStackTrace();
		        
		}
		
		
		return info;
		
	}
	
	

}
