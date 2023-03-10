package MidtermDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BasicMember {
	String username;
	String password;
	String firstName;
	String lastName;
	String customerAddress;
	CheckingAccount checkingAccount;
	SavingsAccount savingsAccount;
	String userID;
	String membershipType = "Basic";
	
	
	BasicMember(String username, String password, String firstName, String lastName, String customerAddress, CheckingAccount checkingAccount, SavingsAccount savingsAccount, String userID){
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.customerAddress = customerAddress;
		this.checkingAccount = checkingAccount;
		this.savingsAccount = savingsAccount;
		this.userID = userID;
	}
	
	

///////////////////////////////////////////////////////////////////////////////////
// Methods // 
///////////////////////////////////////////////////////////////////////////////////
	
	static String makeTransaction(Transaction tran){
		String info = "";
		try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
	        PreparedStatement stmt = myConnection.prepareStatement("INSERT INTO `Transactions`(`UserID`, `EmployeeID`, `TransactionType`,`AccountType`, `AccountID`, `Amount` ) VALUES (?,?,?,?,?,?)");	){
	    	stmt.setString(1,tran.userID );
	    	stmt.setString(2,tran.employeeID );
	    	stmt.setString(3,tran.tranxType);
	    	stmt.setString(4,tran.accountType);
	    	stmt.setString(5,tran.accountID);
	    	stmt.setDouble(6,tran.amount );
	
	    	int rowsAffected = stmt.executeUpdate();
	
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
	
	
	
	
	static String makeDepositChecking(double amount, String accountNumber, double balance, int transactions){
		String info = "";
		double newBalance = amount + balance;
		int transx = transactions + 1;
		System.out.println("");
		
		
		try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");

		        PreparedStatement stmt = myConnection.prepareStatement("UPDATE `CheckingAccounts` SET `CheckingBalance`= ?, `Transactions`= ? WHERE AccountNumber = ?")){
		    	stmt.setDouble(1, newBalance);
		    	stmt.setInt   (2, transx);
		    	stmt.setString(3, accountNumber);    

		    	int rowsAffected = stmt.executeUpdate();

		        
		        
		        if (rowsAffected > 0) {
		            info += "Result: Successful Deposit made."; 
		        }
		        
		        if (rowsAffected == 0) {
		        	info += "Result: Sorry, Deposit Was Unsuccessful.";
		        	System.out.println(newBalance + " ... " + accountNumber);
		        }
		        System.out.println("");
		} 
		catch (SQLException exc) {
		        exc.printStackTrace();
		        
		}
		
		    return info;
		
	}
	
	
	
	static String makeDepositSavings(double amount, String accountNumber, double balance, int transactions){
		String info = "";
		double newBalance = amount + balance;
		int transx = transactions + 1;
		System.out.println("");
		
		try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");

		        PreparedStatement stmt = myConnection.prepareStatement("UPDATE `SavingsAccounts` SET `SavingsBalance`= ?, `Transactions`= ? WHERE AccountNumber = ?")){
		    	stmt.setDouble(1, newBalance);
		    	stmt.setInt   (2, transx);
		    	stmt.setString(3, accountNumber);    

		    	int rowsAffected = stmt.executeUpdate();

		        
		        
		        if (rowsAffected > 0) {
		            info += "Result: Successful Deposit Made."; 
		        }
		        
		        if (rowsAffected == 0) {
		        	info += "Result: Sorry, Deposit Was Unsuccessful.";
		        }
		        System.out.println("");
		} 
		catch (SQLException exc) {
		        exc.printStackTrace();
		        
		}
		
		    return info;
		
	}
	
	
	
	
	
	
	
	
	static String makeWithdrawalChecking(double amount, String accountNumber, double balance, int transactions){
		String info = "";
		double newBalance = balance - amount;
		int transx = transactions + 1;
		System.out.println("");
		
		try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");

		        PreparedStatement stmt = myConnection.prepareStatement("UPDATE `CheckingAccounts` SET `CheckingBalance`= ?, `Transactions`= ? WHERE AccountNumber = ?")){
		    	stmt.setDouble(1, newBalance);
		    	stmt.setInt   (2, transx);
		    	stmt.setString(3, accountNumber);    

		    	int rowsAffected = stmt.executeUpdate();

		        
		        
		        if (rowsAffected > 0) {
		            info += "Result: Successful Withdrawal Made."; 
		        }
		        
		        if (rowsAffected == 0) {
		        	info += "Result: Sorry, Withdrawal Was Unsuccessful.";
		        }
		        System.out.println("");
		} 
		catch (SQLException exc) {
		        exc.printStackTrace();
		        
		}
		
		    return info;
		
	}
	
	
	static String makeWithdrawalSavings(double amount, String accountNumber, double balance, int transactions){
		String info = "";
		double newBalance = balance - amount;
		int transx = transactions + 1;
		System.out.println("");
		
		try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");

		        PreparedStatement stmt = myConnection.prepareStatement("UPDATE `SavingsAccounts` SET `SavingsBalance`= ?, `Transactions`= ? WHERE AccountNumber = ?")){
		    	stmt.setDouble(1, newBalance);
		    	stmt.setInt   (2, transx);
		    	stmt.setString(3, accountNumber);    

		    	int rowsAffected = stmt.executeUpdate();

		        
		        
		        if (rowsAffected > 0) {
		            info += "Result: Successful Withdrawal Made."; 
		        }
		        
		        if (rowsAffected == 0) {
		        	info += "Result: Sorry, Withdrawal Was Unsuccessful.";
		        	System.out.println(newBalance + " ... " + accountNumber);
		        }
		        System.out.println("");
		} 
		catch (SQLException exc) {
		        exc.printStackTrace();
		        
		}
		
		    return info;
		
	}
	
	
	
	static String transferFundsFromChecking(double amount, String checkingAccountNum, String savingsAccountNum, double checkingBalance, double savingsBalance, int checkingTransactions){
		String info = "";
		double newCheckingBalance = checkingBalance - amount;
		double newSavingsBalance = savingsBalance + amount;
		int transx = checkingTransactions + 1;
		System.out.println("");
		
		
		try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");

		        PreparedStatement stmt = myConnection.prepareStatement("UPDATE `SavingsAccounts` SET `SavingsBalance`= ? WHERE AccountNumber = ?");
				PreparedStatement stmt2 = myConnection.prepareStatement("UPDATE `CheckingAccounts` SET `CheckingBalance`= ?, `Transactions`= ? WHERE AccountNumber = ?")){
		    	stmt.setDouble(1, newSavingsBalance);
		    	stmt.setString(2, savingsAccountNum);    

		    	int rowsAffected = stmt.executeUpdate();
		    	
		    	stmt2.setDouble(1, newCheckingBalance);
		    	stmt2.setInt(2, transx);
		    	stmt2.setString(3,checkingAccountNum);
		    	stmt2.executeUpdate();

		        
		        
		        if (rowsAffected > 0) {
		            info += "Result: Successful Transfer Made."; 
		        }
		        
		        if (rowsAffected == 0) {
		        	info += "Result: Sorry, Transfer Was Unsuccessful.";
		        }
		        System.out.println("");
		} 
		catch (SQLException exc) {
		        exc.printStackTrace();
		        
		}
		
		
		return info;
		
	}
	
	
	static String transferFundsFromSavings(double amount, String checkingAccountNum, String savingsAccountNum, double checkingBalance, double savingsBalance, int savingsTransactions){
		String info = "";
		double newCheckingBalance = checkingBalance + amount;
		double newSavingsBalance = savingsBalance - amount;
		int transx = savingsTransactions + 1;
		System.out.println("");
		
		
		try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");

		        PreparedStatement stmt = myConnection.prepareStatement("UPDATE `SavingsAccounts` SET `SavingsBalance`= ?, `Transactions` = ? WHERE AccountNumber = ?");
				PreparedStatement stmt2 = myConnection.prepareStatement("UPDATE `CheckingAccounts` SET `CheckingBalance`= ? WHERE AccountNumber = ?")){
		    	
				stmt.setDouble(1, newSavingsBalance);
		    	stmt.setInt(2, transx);
		    	stmt.setString(3, savingsAccountNum);

		    	int rowsAffected = stmt.executeUpdate();
		    	
		    	stmt2.setDouble(1, newCheckingBalance);
		    	stmt2.setString(2,checkingAccountNum);
		    	stmt2.executeUpdate();

		        
		        
		        if (rowsAffected > 0) {
		            info += "Result: Successful Transfer Made."; 
		        }
		        
		        if (rowsAffected == 0) {
		        	info += "Result: Sorry, Transfer Was Unsuccessful.";
		        }
		        System.out.println("");
		} 
		catch (SQLException exc) {
		        exc.printStackTrace();
		        
		}
		
		
		return info;
		
	}
	
	
	
	
	
	
	static void viewChecking(BasicMember member){
		System.out.println("");
		System.out.println("Checking Account Information");
		System.out.println("--------------------------------------------------");
		System.out.println("Member Name: " + member.firstName + " " + member.lastName);
		System.out.println("Member Type: " + member.membershipType);
		System.out.println("Checking Account Number: " + member.checkingAccount.accountNumber);
		System.out.println("Checking Account Balance: " + member.checkingAccount.balance);
		System.out.println("Number of Transactions: " + member.checkingAccount.transactions);
		System.out.println("--------------------------------------------------");
		
	}
	
	static void viewSavings(BasicMember member){
		System.out.println("");
		System.out.println("Savings Account Information");
		System.out.println("--------------------------------------------------");
		System.out.println("Member Name: " + member.firstName + " " + member.lastName);
		System.out.println("Member Type: " + member.membershipType);
		System.out.println("Savings Account Number: " + member.savingsAccount.accountNumber);
		System.out.println("Savings Account Balance: " + member.savingsAccount.balance);
		System.out.println("Number of Transactions: " + member.savingsAccount.transactions);
		System.out.println("--------------------------------------------------");
		
	}
	
	
	static String resetPassword(String password, String userID){
		String info = "";
		try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");

		        PreparedStatement stmt = myConnection.prepareStatement("UPDATE `BasicMembers` SET `Password`= ?  WHERE UserID = ?");
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
	
	static String signOut(){
		String info = "";
		
		
		return info;
		
	}
	
	
	
	
}

