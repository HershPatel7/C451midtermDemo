package MidtermDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PremiumMember extends StandardMember {
	MortgageAccount mortgageAccount;

	PremiumMember(String username, String password, String firstName, String lastName, String customerAddress,
			CheckingAccount checkingAccount, SavingsAccount savingsAccount,
			CreditCardAccount creditAccount, MortgageAccount mortgageAccount, String userID) {
		super(username, password, firstName, lastName, customerAddress, checkingAccount, savingsAccount, creditAccount, userID);
		this.mortgageAccount = mortgageAccount;
		this.membershipType = "Premium";
		
	} // end of constructor
	
	
	static String resetPassword(String password, String userID){
		String info = "";
		try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");

		        PreparedStatement stmt = myConnection.prepareStatement("UPDATE `PremiumMembers` SET `Password`= ?  WHERE UserID = ?");
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
	
	
	
	
	static void viewHomeLoanInfo(PremiumMember member){
		System.out.println("");
		System.out.println("Mortgage Account Information");
		System.out.println("--------------------------------------------------");
		System.out.println("Member Name: " + member.firstName + " " + member.lastName);
		System.out.println("Member Type: " + member.membershipType);
		System.out.println("Mortgage Account Number: " + member.mortgageAccount.accountNumber);
		System.out.println("Balance Remaining: " + member.mortgageAccount.balance);
		System.out.println("Total Loan Amount: " + member.mortgageAccount.loanAmount);
		System.out.println("Number of Payments Made: " + member.mortgageAccount.transactions);
		System.out.println("--------------------------------------------------");
	}
	
	static String makeMortgagePayment(double amount, String checkingAccountNum, String mortgageAccountNum, double checkingBalance, double mortgageBalance, int mortgageTransactions){
		String info = "";
		double newCheckingBalance = checkingBalance - amount;
		double newMortgageBalance = mortgageBalance - amount;
		int transx = mortgageTransactions + 1;
		System.out.println("");
		
		
		try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");

		        PreparedStatement stmt = myConnection.prepareStatement("UPDATE `MortgageAccounts` SET `MortgageBalance`= ?, `Transactions`= ? WHERE AccountNumber = ?");
				PreparedStatement stmt2 = myConnection.prepareStatement("UPDATE `CheckingAccounts` SET `CheckingBalance`= ? WHERE AccountNumber = ?")){
		    	stmt.setDouble(1, newMortgageBalance);
		    	stmt.setInt(2, transx);
		    	stmt.setString(3, mortgageAccountNum);    

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

