package MidtermDemo;

import java.sql.*;
//import java.time.*;
//import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;


public class Branch {
	
//////////////////////////////////////////////////////////////////////////////////
//* Local Storage & Variables *
//////////////////////////////////////////////////////////////////////////////////
	
//Admin
static ArrayList<Admin> admins = new ArrayList<Admin>();
	
//Members
static ArrayList<BasicMember> basicMembers = new ArrayList<BasicMember>();
static ArrayList<StandardMember> standardMembers = new ArrayList<StandardMember>();
static ArrayList<PremiumMember> premiumMembers = new ArrayList<PremiumMember>();

//Bank Users
static ArrayList<BankClerk> bankClerks = new ArrayList<BankClerk>();
static ArrayList<BankManager> bankManagers = new ArrayList<BankManager>();

//Accounts
static ArrayList<CheckingAccount> checkingAccounts = new ArrayList<CheckingAccount>();
static ArrayList<SavingsAccount> savingsAccounts = new ArrayList<SavingsAccount>();
static ArrayList<CreditCardAccount> creditCardAccounts = new ArrayList<CreditCardAccount>();
static ArrayList<MortgageAccount> mortgageAccounts = new ArrayList<MortgageAccount>();

//Transactions
static ArrayList<Transaction> transactions = new ArrayList<Transaction>();

//Logged In Details
static String userType;
static String userID;

static BasicMember basicMember;
static StandardMember standardMember;
static PremiumMember premiumMember;
static BankClerk bankClerk;
static BankManager bankManager;
static Admin admin;


//CreditCardAccount Class variable sets $1,500 as credit card limit for all members
static double creditLimit = 1500.00;

//MortgageAccount Class variable sets $150,000 as mortgage loan total for all members 
static double mortgageTotal = 150000;

//User Input [Scanner]
static Scanner input = new Scanner(System.in);
	

//////////////////////////////////////////////////////////////////////////////////
// * Main Method *
//////////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws SQLException {
		
		//Create an Admin [After Clearing Database]
		//Admin hersh = new Admin("harpatel", "sharky7", "Hersh", "Patel", generateRandom(), generateRandom());	
		//admins.add(hersh);
		//createAdmin(hersh);
		
		
		//----LOCALLY RECREATE CLOUD DATABASE----
		//Recreate Accounts
		gatherCheckingAccounts();
		gatherSavingsAccounts();
		gatherCreditCardAccounts();
		gatherMortgageAccounts();

		//Recreate Members
		gatherBasicMembers();
		gatherStandardMembers();
		gatherPremiumMembers();
		
		//Recreate Admins
		gatherAdmins();
		
		//Recreate Transactions
		gatherTransactions();

		//Display Login Screen [MAIN MENU]
		loginScreen();	
	} 
	
//////////////////////////////////////////////////////////////////////////////////
// * DISPLAY LOGIN SCREEN  *
//////////////////////////////////////////////////////////////////////////////////
	
	static void loginScreen() throws SQLException {
		System.out.println("---------------------------------------------------------------");
		System.out.println("                   Welcome, Please Log In ");
		System.out.println("---------------------------------------------------------------");
		System.out.print("Enter Username: ");
		String LoginUsername = input.next();
		System.out.print("Enter Password: ");
		String LoginPassword = input.next();
		
		if (checkLogin(LoginUsername, LoginPassword)) {
			System.out.println("");
			System.out.println("You have successfully logged in.");
			System.out.println("===============================================================");
			System.out.println("===============================================================");
			System.out.println("");
			System.out.println("");
			

			
			//////////////////////////////////////////////////////////////////////////////
			// BASIC MEMBER PORTAL
			//////////////////////////////////////////////////////////////////////////////
			
			if (userType.equals("Basic")) {
				for (BasicMember member : basicMembers) {
					if (member.userID == userID) {	
						basicMember = member;
						}
					}
				
				while(true) {                                                
					System.out.println("---------------------------------------------------------------");
					System.out.println("                  *  BASIC MEMBER MENU  *");
					System.out.println("---------------------------------------------------------------");
					System.out.println("Name: " + basicMember.firstName + " " + basicMember.lastName + " | " + "User ID: " + basicMember.userID);
					System.out.println("Checking Balance: $" + basicMember.checkingAccount.balance + " | " + "Account Number: " + basicMember.checkingAccount.accountNumber);
					System.out.println("Savings Balance: $" + basicMember.savingsAccount.balance + " | " + "Account Number: " + basicMember.savingsAccount.accountNumber);
					System.out.println("---------------------------------------------------------------");
					System.out.println("1.  Make a Deposit");
					System.out.println("2.  Make a Withdrawal");
					System.out.println("3.  Transfer Funds");
					System.out.println("4.  View Checking Account Transactions");
					System.out.println("5.  View Savings Account Transactions");
					System.out.println("6.  Reset Password");
					System.out.println("7.  Sign Out");
					System.out.println("---------------------------------------------------------------");
					System.out.print("Please enter your selection: ");
					
					int choice = input.nextInt();
					
					if (choice == 7) {
						System.out.println("");
						System.out.println("You have been successfully logged out.");
						System.out.println("===============================================================");
						System.out.println("");
						System.out.println("");
						basicMember = null;
						loginScreen();
						break;
						}
					
					else if (choice == 1) {		
						System.out.println("How much would you like to deposit?: ");
						double depositAmount = input.nextDouble();
						System.out.println("1. Deposit to Checking Account");
						System.out.println("2. Deposit to Savings Account");
						System.out.println("Make a selection: ");
						//System.out.println(depositAmount);
						int subChoice = input.nextInt();
						
							if (subChoice == 1) {
								copyBasicChecking();
								System.out.println(BasicMember.makeDepositChecking(depositAmount, basicMember.checkingAccount.accountNumber, basicMember.checkingAccount.balance, basicMember.checkingAccount.transactions));
								basicMember.checkingAccount.transactions += 1;
								basicMember.checkingAccount.balance += depositAmount;
								
								Transaction tran = new Transaction(basicMember.userID, null, "Deposit", "Checking", basicMember.checkingAccount.accountNumber, depositAmount);
								transactions.add(tran);
								BasicMember.makeTransaction(tran);
								}
							
							if (subChoice == 2) {
								copyBasicSavings();
								System.out.println(BasicMember.makeDepositSavings(depositAmount, basicMember.savingsAccount.accountNumber, basicMember.savingsAccount.balance, basicMember.savingsAccount.transactions));
								basicMember.savingsAccount.transactions += 1;
								basicMember.savingsAccount.balance += depositAmount;
								
								Transaction tran = new Transaction(basicMember.userID, null, "Deposit", "Savings", basicMember.savingsAccount.accountNumber, depositAmount);
								transactions.add(tran);
								BasicMember.makeTransaction(tran);
								}
						}
					
					else if (choice == 2) {
						System.out.println("How much would you like to withdraw?: ");
						double withdrawAmount = input.nextDouble();
						System.out.println("1. Withdraw from Checking Account");
						System.out.println("2. Withdraw from Savings Account");
						System.out.println("Make a selection: ");
						
						int subChoice = input.nextInt();
						
						if (subChoice == 1) {
							copyBasicChecking();
							System.out.println(BasicMember.makeWithdrawalChecking(withdrawAmount, basicMember.checkingAccount.accountNumber, basicMember.checkingAccount.balance, basicMember.checkingAccount.transactions));
							basicMember.checkingAccount.transactions += 1;
							basicMember.checkingAccount.balance -= withdrawAmount;
							
							Transaction tran = new Transaction(basicMember.userID, null, "Withdrawal", "Checking", basicMember.checkingAccount.accountNumber, withdrawAmount);
							transactions.add(tran);
							BasicMember.makeTransaction(tran);
							}
						
						if (subChoice == 2) {
							copyBasicSavings();
							System.out.println(BasicMember.makeWithdrawalSavings(withdrawAmount, basicMember.savingsAccount.accountNumber, basicMember.savingsAccount.balance, basicMember.savingsAccount.transactions));
							basicMember.savingsAccount.transactions += 1;
							basicMember.savingsAccount.balance -= withdrawAmount;
							
							Transaction tran = new Transaction(basicMember.userID, null, "Withdrawal", "Savings", basicMember.savingsAccount.accountNumber, withdrawAmount);
							transactions.add(tran);
							BasicMember.makeTransaction(tran);
							}
						}
					
					else if (choice == 3) {
						System.out.println("How much would you like to transfer?: ");
						double transferAmount = input.nextDouble();
						System.out.println("1. Transfer from Checking to Savings Account");
						System.out.println("2. Transfer from Savings to Checking Account");
						System.out.println("Make a selection: ");
						
						int subChoice = input.nextInt();
						
						if (subChoice == 1) {	
							copyBasicChecking();		
							copyBasicSavings();
							System.out.println(BasicMember.transferFundsFromChecking(transferAmount, basicMember.checkingAccount.accountNumber, basicMember.savingsAccount.accountNumber, basicMember.checkingAccount.balance, basicMember.savingsAccount.balance, basicMember.checkingAccount.transactions));
							basicMember.checkingAccount.transactions += 1;
							basicMember.checkingAccount.balance -= transferAmount;
							basicMember.savingsAccount.balance += transferAmount;
							
							Transaction tran = new Transaction(basicMember.userID, null, "Transfer to Savings", "Checking", basicMember.checkingAccount.accountNumber, transferAmount);
							transactions.add(tran);
							BasicMember.makeTransaction(tran);
							}
						
						if (subChoice == 2) {
							copyBasicChecking();
							copyBasicSavings();
							System.out.println(BasicMember.transferFundsFromSavings(transferAmount, basicMember.checkingAccount.accountNumber, basicMember.savingsAccount.accountNumber, basicMember.checkingAccount.balance, basicMember.savingsAccount.balance, basicMember.savingsAccount.transactions));
							basicMember.savingsAccount.transactions += 1;
							basicMember.savingsAccount.balance -= transferAmount;
							basicMember.checkingAccount.balance += transferAmount;
							
							Transaction tran = new Transaction(basicMember.userID, null, "Transfer to Checking", "Savings", basicMember.savingsAccount.accountNumber, transferAmount);
							transactions.add(tran);
							BasicMember.makeTransaction(tran);
							}
						}
					
					else if (choice == 4) {
						viewChecking(basicMember.userID);
						}
					
					else if (choice == 5) {
						viewSavings(basicMember.userID);
						}
						
					else if (choice == 6) {
						System.out.println("Enter a new password: ");
						String newPass = input.next();
						
						System.out.println(BasicMember.resetPassword(newPass, basicMember.userID));
						System.out.println("");
						System.out.println("You have been successfully logged out.");
						System.out.println("===============================================================");
						System.out.println("");
						System.out.println("");
						loginScreen();
						break;
						}
					
					} // end of while(true) loop
				
				loginScreen(); // back to login after logging out
			
				} // end of basic member portal 
			
			
		//////////////////////////////////////////////////////////////////////////////
		// STANDARD MEMBER PORTAL
		//////////////////////////////////////////////////////////////////////////////
			
			else if (userType.equals("Standard")) {
				for (StandardMember member : standardMembers) {
					System.out.println(member.firstName);
					if (member.userID == userID) {
						standardMember = member;
						}
					}

				
				while(true) { 
					System.out.println("---------------------------------------------------------------");
					System.out.println("                  *  STANDARD MEMBER MENU  *");
					System.out.println("---------------------------------------------------------------");
					System.out.println("Name: " + standardMember.firstName + " " + standardMember.lastName + " | " + "User ID: " + standardMember.userID);
					System.out.println("Checking Balance: $" + standardMember.checkingAccount.balance + " | " + "Account Number: " + standardMember.checkingAccount.accountNumber);
					System.out.println("Savings Balance: $" + standardMember.savingsAccount.balance + " | " + "Account Number: " + standardMember.savingsAccount.accountNumber);
					System.out.println("Credit Card Balance: $" + standardMember.creditAccount.balance + " | " + "Account Number: " + standardMember.creditAccount.accountNumber);
					System.out.println("---------------------------------------------------------------");
					System.out.println("1.  Make a Deposit");
					System.out.println("2.  Make a Withdrawal");
					System.out.println("3.  Transfer Funds");
					System.out.println("4.  View Checking Account Transactions");
					System.out.println("5.  View Savings Account Transactions");
					System.out.println("6.  View Credit Card Account Transactions");
					System.out.println("7.  Make Credit Card Payment");
					System.out.println("8.  Reset Password");
					System.out.println("9.  Sign Out");
					System.out.println("---------------------------------------------------------------");
					System.out.print("Please enter your selection: ");
					
					int choice = input.nextInt();
					
					if (choice == 9) {
						System.out.println("");
						System.out.println("You have been successfully logged out.");
						System.out.println("===============================================================");
						System.out.println("");
						System.out.println("");
						standardMember = null;
						loginScreen();
						break;
						}
					
					else if (choice == 1) {		
						System.out.println("How much would you like to deposit?: ");
						double depositAmount = input.nextDouble();
						System.out.println("1. Deposit to Checking Account");
						System.out.println("2. Deposit to Savings Account");
						System.out.println("Make a selection: ");
						//System.out.println(depositAmount);
						int subChoice = input.nextInt();
						
							if (subChoice == 1) {
								
								copyStandardChecking();
								System.out.println(StandardMember.makeDepositChecking(depositAmount, standardMember.checkingAccount.accountNumber, standardMember.checkingAccount.balance, standardMember.checkingAccount.transactions));
								standardMember.checkingAccount.transactions += 1;
								standardMember.checkingAccount.balance += depositAmount;
								
								Transaction tran = new Transaction(standardMember.userID, null, "Deposit", "Checking", standardMember.checkingAccount.accountNumber, depositAmount);
								transactions.add(tran);
								StandardMember.makeTransaction(tran);
								}
							
							if (subChoice == 2) {
								copyStandardSavings();
								System.out.println(StandardMember.makeDepositSavings(depositAmount, standardMember.savingsAccount.accountNumber, standardMember.savingsAccount.balance, standardMember.savingsAccount.transactions));
								standardMember.savingsAccount.transactions += 1;
								standardMember.savingsAccount.balance += depositAmount;
								
								Transaction tran = new Transaction(standardMember.userID, null, "Deposit", "Savings", standardMember.savingsAccount.accountNumber, depositAmount);
								transactions.add(tran);
								StandardMember.makeTransaction(tran);
								}
						
						}
					
					else if (choice == 2) {
						System.out.println("How much would you like to withdraw?: ");
						double withdrawAmount = input.nextDouble();
						System.out.println("1. Withdraw from Checking Account");
						System.out.println("2. Withdraw from Savings Account");
						System.out.println("Make a selection: ");
						
						int subChoice = input.nextInt();
						
						if (subChoice == 1) {
							copyStandardChecking();
							System.out.println(StandardMember.makeWithdrawalChecking(withdrawAmount, standardMember.checkingAccount.accountNumber, standardMember.checkingAccount.balance, standardMember.checkingAccount.transactions));
							standardMember.checkingAccount.transactions += 1;
							standardMember.checkingAccount.balance -= withdrawAmount;
							
							Transaction tran = new Transaction(standardMember.userID, null, "Withdrawal", "Checking", standardMember.checkingAccount.accountNumber, withdrawAmount);
							transactions.add(tran);
							StandardMember.makeTransaction(tran);
							}
						
						if (subChoice == 2) {
							copyStandardSavings();
							System.out.println(StandardMember.makeWithdrawalSavings(withdrawAmount, standardMember.savingsAccount.accountNumber, standardMember.savingsAccount.balance, standardMember.savingsAccount.transactions));
							standardMember.savingsAccount.transactions += 1;
							standardMember.savingsAccount.balance -= withdrawAmount;
							
							Transaction tran = new Transaction(standardMember.userID, null, "Withdrawal", "Savings", standardMember.savingsAccount.accountNumber, withdrawAmount);
							transactions.add(tran);
							StandardMember.makeTransaction(tran);
							}
						}
					
					else if (choice == 3) {
						System.out.println("How much would you like to transfer?: ");
						double transferAmount = input.nextDouble();
						System.out.println("1. Transfer from Checking to Savings Account");
						System.out.println("2. Transfer from Savings to Checking Account");
						System.out.println("Make a selection: ");
						
						int subChoice = input.nextInt();
						
						if (subChoice == 1) {
							copyStandardChecking();	
							copyStandardSavings();
							System.out.println(StandardMember.transferFundsFromChecking(transferAmount, standardMember.checkingAccount.accountNumber, standardMember.savingsAccount.accountNumber, standardMember.checkingAccount.balance, standardMember.savingsAccount.balance, standardMember.checkingAccount.transactions));
							standardMember.checkingAccount.transactions += 1;
							standardMember.checkingAccount.balance -= transferAmount;
							standardMember.savingsAccount.balance += transferAmount;
							
							Transaction tran = new Transaction(standardMember.userID, null, "Transfer to Savings", "Checking", standardMember.checkingAccount.accountNumber, transferAmount);
							transactions.add(tran);
							StandardMember.makeTransaction(tran);
							}
						
						if (subChoice == 2) {
							copyStandardChecking();	
							copyStandardSavings();			
							System.out.println(StandardMember.transferFundsFromSavings(transferAmount, standardMember.checkingAccount.accountNumber, standardMember.savingsAccount.accountNumber, standardMember.checkingAccount.balance, standardMember.savingsAccount.balance, standardMember.savingsAccount.transactions));
							standardMember.savingsAccount.transactions += 1;
							standardMember.savingsAccount.balance -= transferAmount;
							standardMember.checkingAccount.balance += transferAmount;
							
							Transaction tran = new Transaction(standardMember.userID, null, "Transfer to Checking", "Savings", standardMember.savingsAccount.accountNumber, transferAmount);
							transactions.add(tran);
							StandardMember.makeTransaction(tran);
							}
						}
					
					else if (choice == 4) {
						viewChecking(standardMember.userID);	
						}
					
					else if (choice == 5) {
						viewSavings(standardMember.userID);
						}
					
					else if (choice == 6) {
						viewCC(standardMember.userID);
						}
					
					else if (choice == 7) {
						System.out.println("How much would you like to pay?: ");
						double ccPaymentAmount = input.nextDouble();
						StandardMember.makeCCPayment(ccPaymentAmount, standardMember.checkingAccount.accountNumber, standardMember.creditAccount.accountNumber, standardMember.checkingAccount.balance, standardMember.creditAccount.balance, standardMember.creditAccount.transactions);
						standardMember.checkingAccount.balance -= ccPaymentAmount;
						standardMember.creditAccount.balance -= ccPaymentAmount;
						standardMember.creditAccount.transactions ++;
						
						Transaction tran = new Transaction(standardMember.userID, null, "CC Payment", "Credit Card", standardMember.checkingAccount.accountNumber, ccPaymentAmount);
						transactions.add(tran);
						StandardMember.makeTransaction(tran);
						}
						
					else if (choice == 8) {
						System.out.println("Enter a new password: ");
						String newPass = input.next();
						System.out.println(StandardMember.resetPassword(newPass, standardMember.userID));
						System.out.println("");
						System.out.println("You have been successfully logged out.");
						System.out.println("======================================");
						System.out.println("");
						System.out.println("");
						loginScreen();
						break;
						}
					} // end of while(true) loop
				loginScreen(); // Back to login after logging out
				} // end of basic member portal 
			
			
		//////////////////////////////////////////////////////////////////////////////
		// PREMIUM MEMBER PORTAL
		//////////////////////////////////////////////////////////////////////////////
			
			else if (userType.equals("Premium")) {				
				while(true) {                                              								
					System.out.println("---------------------------------------------------------------");
					System.out.println("                  *  PREMIUM MEMBER MENU  *");
					System.out.println("---------------------------------------------------------------");
					System.out.println("Name: " + premiumMember.firstName + " " + premiumMember.lastName + " | " + "User ID: " + premiumMember.userID);
					System.out.println("Checking Balance: $" + premiumMember.checkingAccount.balance + " | " + "Account Number: " + premiumMember.checkingAccount.accountNumber);
					System.out.println("Savings Balance: $" + premiumMember.savingsAccount.balance + " | " + "Account Number: " + premiumMember.savingsAccount.accountNumber);
					System.out.println("Credit Card Balance: $" + premiumMember.creditAccount.balance + " | " + "Account Number: " + premiumMember.creditAccount.accountNumber);
					System.out.println("Mortgage Balance: $" + premiumMember.mortgageAccount.balance + " | " + "Account Number: " + premiumMember.mortgageAccount.accountNumber);
					System.out.println("---------------------------------------------------------------");
					System.out.println("1.  Make a Deposit");
					System.out.println("2.  Make a Withdrawal");
					System.out.println("3.  Transfer Funds");
					System.out.println("4.  View Checking Account Transactions");
					System.out.println("5.  View Savings Account Transactions");
					System.out.println("6.  View Credit Card Account Transactions");
					System.out.println("7.  Make Credit Card Payment");
					System.out.println("8.  View Mortgage Account Transactions");
					System.out.println("9.  Make Morgage Payment");
					System.out.println("10. Reset Password");
					System.out.println("11. Sign Out");
					System.out.println("---------------------------------------------------------------");
					System.out.print("Please enter your selection: ");
					
					int choice = input.nextInt();
					
					if (choice == 11) {
						System.out.println("");
						System.out.println("You have been successfully logged out.");
						System.out.println("===============================================================");
						System.out.println("");
						System.out.println("");
						premiumMember = null;
						loginScreen();
						break;
						}
					
					else if (choice == 1) {		
						System.out.println("How much would you like to deposit?: ");
						double depositAmount = input.nextDouble();
						System.out.println("1. Deposit to Checking Account: $" + premiumMember.checkingAccount.balance);
						System.out.println("2. Deposit to Savings Account: $" + premiumMember.savingsAccount.balance);
						System.out.println("Make a selection: ");
						//System.out.println(depositAmount);
						int subChoice = input.nextInt();
					
						if (subChoice == 1) {
							copyPremiumChecking();
							System.out.println(PremiumMember.makeDepositChecking(depositAmount, premiumMember.checkingAccount.accountNumber, premiumMember.checkingAccount.balance, premiumMember.checkingAccount.transactions));
							premiumMember.checkingAccount.transactions += 1;
							premiumMember.checkingAccount.balance += depositAmount;
							
							Transaction tran = new Transaction(premiumMember.userID, null, "Deposit", "Checking", premiumMember.checkingAccount.accountNumber, depositAmount);
							transactions.add(tran);
							PremiumMember.makeTransaction(tran);
							}
						
						if (subChoice == 2) {
							copyPremiumSavings();
							System.out.println(PremiumMember.makeDepositSavings(depositAmount, premiumMember.savingsAccount.accountNumber, premiumMember.savingsAccount.balance, premiumMember.savingsAccount.transactions));
							premiumMember.savingsAccount.transactions += 1;
							premiumMember.savingsAccount.balance += depositAmount;
							
							Transaction tran = new Transaction(premiumMember.userID, null, "Deposit", "Savings", premiumMember.savingsAccount.accountNumber, depositAmount);
							transactions.add(tran);
							PremiumMember.makeTransaction(tran);
							}
						}
					
					else if (choice == 2) {
						System.out.println("How much would you like to withdraw?: ");
						double withdrawAmount = input.nextDouble();
						System.out.println("1. Withdraw from Checking Account");
						System.out.println("2. Withdraw from Savings Account");
						System.out.println("Make a selection: ");
						
						int subChoice = input.nextInt();
						
						if (subChoice == 1) {
							copyPremiumChecking();
							System.out.println(PremiumMember.makeWithdrawalChecking(withdrawAmount, premiumMember.checkingAccount.accountNumber, premiumMember.checkingAccount.balance, premiumMember.checkingAccount.transactions));
							premiumMember.checkingAccount.transactions += 1;
							premiumMember.checkingAccount.balance -= withdrawAmount;
							
							Transaction tran = new Transaction(premiumMember.userID, null, "Withdraw", "Checking", premiumMember.checkingAccount.accountNumber, withdrawAmount);
							transactions.add(tran);
							PremiumMember.makeTransaction(tran);
							}
						
						if (subChoice == 2) {
							copyPremiumSavings();
							System.out.println(PremiumMember.makeWithdrawalSavings(withdrawAmount, premiumMember.savingsAccount.accountNumber, premiumMember.savingsAccount.balance, premiumMember.savingsAccount.transactions));
							premiumMember.savingsAccount.transactions += 1;
							premiumMember.savingsAccount.balance -= withdrawAmount;
							
							Transaction tran = new Transaction(premiumMember.userID, null, "Withdraw", "Savings", premiumMember.savingsAccount.accountNumber, withdrawAmount);
							transactions.add(tran);
							PremiumMember.makeTransaction(tran);
							}
						}
					
					else if (choice == 3) {
								System.out.println("How much would you like to transfer?: ");
								double transferAmount = input.nextDouble();
								System.out.println("1. Transfer from Checking to Savings Account");
								System.out.println("2. Transfer from Savings to Checking Account");
								System.out.println("Make a selection: ");
						
						int subChoice = input.nextInt();
						
						if (subChoice == 1) {
							copyPremiumChecking();
							copyPremiumSavings();
							System.out.println(PremiumMember.transferFundsFromChecking(transferAmount, premiumMember.checkingAccount.accountNumber, premiumMember.savingsAccount.accountNumber, premiumMember.checkingAccount.balance, premiumMember.savingsAccount.balance, premiumMember.checkingAccount.transactions));
							premiumMember.checkingAccount.transactions += 1;
							premiumMember.checkingAccount.balance -= transferAmount;
							premiumMember.savingsAccount.balance += transferAmount;
							
							Transaction tran = new Transaction(premiumMember.userID, null, "Transfer to Savings", "Checking", premiumMember.checkingAccount.accountNumber, transferAmount);
							transactions.add(tran);
							PremiumMember.makeTransaction(tran);
							}
						
						if (subChoice == 2) {
							copyPremiumChecking();
							copyPremiumSavings();
							System.out.println(PremiumMember.transferFundsFromSavings(transferAmount, premiumMember.checkingAccount.accountNumber, premiumMember.savingsAccount.accountNumber, premiumMember.checkingAccount.balance, premiumMember.savingsAccount.balance, premiumMember.savingsAccount.transactions));
							premiumMember.savingsAccount.transactions += 1;
							premiumMember.savingsAccount.balance -= transferAmount;
							premiumMember.checkingAccount.balance += transferAmount;
							
							Transaction tran = new Transaction(premiumMember.userID, null, "Transfer to Checking", "Savings", premiumMember.savingsAccount.accountNumber, transferAmount);
							transactions.add(tran);
							PremiumMember.makeTransaction(tran);
							}
						}
					
					else if (choice == 4) {
						viewChecking(premiumMember.userID);
						}
					
					else if (choice == 5) {
						viewSavings(premiumMember.userID);
						}
					
					else if (choice == 6) {
						viewCC(premiumMember.userID);
						}
					
					else if (choice == 7) {
						System.out.println("How much would you like to pay?: ");
						double ccPaymentAmount = input.nextDouble();
						PremiumMember.makeCCPayment(ccPaymentAmount, premiumMember.checkingAccount.accountNumber, premiumMember.creditAccount.accountNumber, premiumMember.checkingAccount.balance, premiumMember.creditAccount.balance, premiumMember.creditAccount.transactions);
						premiumMember.checkingAccount.balance -= ccPaymentAmount;
						premiumMember.creditAccount.balance -= ccPaymentAmount;
						premiumMember.creditAccount.transactions ++;
						
						Transaction tran = new Transaction(premiumMember.userID, null, "CC Payment", "Credit Card", premiumMember.checkingAccount.accountNumber, ccPaymentAmount);
						transactions.add(tran);
						PremiumMember.makeTransaction(tran);
						
						}
					
					else if (choice == 8) {
						viewMortgage(premiumMember.userID);
						}
					
					else if (choice == 9) {
						System.out.println("How much would you like to pay?: ");
						double mortgagePaymentAmount = input.nextDouble();
						PremiumMember.makeMortgagePayment(mortgagePaymentAmount, premiumMember.checkingAccount.accountNumber, premiumMember.mortgageAccount.accountNumber, premiumMember.checkingAccount.balance, premiumMember.mortgageAccount.balance, premiumMember.mortgageAccount.transactions);
						premiumMember.checkingAccount.balance -= mortgagePaymentAmount;
						premiumMember.mortgageAccount.balance -= mortgagePaymentAmount;
						premiumMember.mortgageAccount.transactions ++;
						
						Transaction tran = new Transaction(premiumMember.userID, null, "Mortgage Payment", "Mortgage", premiumMember.checkingAccount.accountNumber, mortgagePaymentAmount);
						transactions.add(tran);
						PremiumMember.makeTransaction(tran);
						}
						
					else if (choice == 10) {
						System.out.println("Enter a new password: ");
						String newPass = input.next();
						
						System.out.println(PremiumMember.resetPassword(newPass, premiumMember.userID));
						System.out.println("");
						System.out.println("You have successfully logged out.");
						System.out.println("===============================================================");
						System.out.println("");
						System.out.println("");
						loginScreen();
						break;	
						}
					
					} // end of while(true) loop
				loginScreen(); // Back to login after logging out
				} // end of premium member portal 
			
		//////////////////////////////////////////////////////////////////////////////
		// BANK CLERK PORTAL
		//////////////////////////////////////////////////////////////////////////////
			
			if (userType.equals("Clerk")) {
				while(true) {                                               
					System.out.println("---------------------------------------------------------------");
					System.out.println("                    *  BANK CLERK MENU  *");
					System.out.println("---------------------------------------------------------------");
					System.out.println("1.  View Member Information");
					System.out.println("2.  Provide Member Services");
					System.out.println("3.  Transfer Funds");
					System.out.println("4.  Create a Basic Member");
					System.out.println("5.  Create a Standard Member");
					System.out.println("6.  Create a Premium Member");
					System.out.println("7.  Reset Member Password");
					System.out.println("8.  Reset Your Password");
					System.out.println("9.  Log Out");
					System.out.println("---------------------------------------------------------------");
					System.out.print("Please enter your selection: ");
					
					int choice = input.nextInt();
					
					if (choice == 9) {
						System.out.println("");
						System.out.println("You have been successfully logged out.");
						System.out.println("===============================================================");
						System.out.println("");
						System.out.println("");
						bankClerk = null;
						loginScreen();
						break;
						}
					
					else if (choice == 1) {
						
						}
					
					else if (choice == 2) {
						System.out.println("Please enter Member Type (Basic, Standard, Premium): ");
						String type = input.next();
						System.out.println("Please enter UserID): ");
						String userID = input.next();
						
						if (type.equals("Basic")){
							for (BasicMember member : basicMembers) {
								if (member.userID.equals(userID)) {
									basicMember = member;
									
								}
							}
							}
						
						
						
						
						}
					
					else if (choice == 3) {
						
						}
					else if (choice == 4) {
						createBasicMember();
						}
					else if (choice == 5) {
						createStandardMember();
						}
					else if (choice == 6) {
						createPremiumMember();
						}
					else if (choice == 7) {
					
						}
					else if (choice == 8) {
					
						}
				}
			} 
					
		//////////////////////////////////////////////////////////////////////////////
		// BANK MANAGER PORTAL
		//////////////////////////////////////////////////////////////////////////////
			
			if (userType.equals("Manager")) {
				while(true) {                                               
					System.out.println("---------------------------------------------------------------");
					System.out.println("                  *  BANK MANAGER MENU  *");
					System.out.println("---------------------------------------------------------------");
					System.out.println("1.  View Member Information");
					System.out.println("2.  Provide Member Services");
					System.out.println("3.  Transfer Funds");
					System.out.println("4.  Create a Basic Member");
					System.out.println("5.  Create a Standard Member");
					System.out.println("6.  Create a Premium Member");
					System.out.println("7.  Create a Bank Clerk");
					System.out.println("8.  Modify Bank Clerk");
					System.out.println("9.  Remove Bank Clerk");
					System.out.println("10.  View Home Loan Applications");
					System.out.println("11.  Reset Member Password");
					System.out.println("12.  Reset Your Password");
					System.out.println("13.  Log Out");
					System.out.println("---------------------------------------------------------------");
					System.out.print("Please enter your selection: ");
					
					int choice = input.nextInt();
					
					if (choice == 13) {
						System.out.println("");
						System.out.println("You have been successfully logged out.");
						System.out.println("===============================================================");
						System.out.println("");
						System.out.println("");
						bankClerk = null;
						loginScreen();
						break;
						}
					
					else if (choice == 1) {

						}
					
					else if (choice == 2) {

						}
					
					else if (choice == 3) {

						}
					else if (choice == 4) {
						createBasicMember();
						}
					else if (choice == 5) {
						createStandardMember();
						}
					else if (choice == 6) {
						createPremiumMember();
						}
					else if (choice == 7) {
						createBankClerk();
						}
					else if (choice == 8) {
					
						}
					else if (choice == 9) {
						
					}
					else if (choice == 10) {
						
					}
					else if (choice == 11) {
						
					}
					else if (choice == 12) {
						
					}
				}
			}
			
			
			//////////////////////////////////////////////////////////////////////////////
			// ADMIN PORTAL
			//////////////////////////////////////////////////////////////////////////////
			
			if (userType.equals("Admin")) { 
				while(true) {                                               
					System.out.println("---------------------------------------------------------------");
					System.out.println("                          *  MENU  *");
					System.out.println("---------------------------------------------------------------");
					System.out.println("1. Create a Bank Manager");
					System.out.println("2. Create a Bank Clerk");
					System.out.println("3. Log Out");
					System.out.println("---------------------------------------------------------------");
					System.out.print("Please enter your selection: ");
					
					int choice = input.nextInt();
					
					if (choice == 3) {
						System.out.println("");
						System.out.println("You have been successfully logged out.");
						System.out.println("===============================================================");
						System.out.println("");
						System.out.println("");
						admin = null;
						loginScreen();
						break;
						}
					
					else if (choice == 1) {
							createBankManager();
						}
					
					else if (choice == 2) {
							createBankClerk();
						}
					

				} //while
			}//userType
		}//if statement
		
		else {
			System.out.println("");
			System.out.println("Incorrect credentials. Try again.");
			System.out.println("===============================================================");
			loginScreen();
			}
			
		
	}// END OF LOGIN SCREEN
	
	
	
//////////////////////////////////////////////////////////////////////////////////
//* GATHER METHODS - RECREATE OBJECTS *
//////////////////////////////////////////////////////////////////////////////////
		
	static void gatherBasicMembers() throws SQLException{
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM BasicMembers");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
		    // Retrieve values from result set and use them to create object
		    String username = rs.getString("Username");
		    String password = rs.getString("Password");
		    String firstName = rs.getString("FirstName");
		    String lastName = rs.getString("LastName");
		    String address = rs.getString("Address");
		    String checkingAccountNumber = rs.getString("CheckingAccountNumber");
		    String savingsAccountNumber = rs.getString("SavingsAccountNumber");
		    String userID = rs.getString("UserID");

		    // Create object using retrieved values, add to list
		    BasicMember member = new BasicMember(username, password, firstName, lastName, address, findCheckingAccount(checkingAccountNumber), findSavingsAccount(savingsAccountNumber), userID);
		    basicMembers.add(member);
			}

		rs.close();
		stmt.close();
		conn.close();
	}
	
	
	static void gatherStandardMembers() throws SQLException{
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM StandardMembers");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
			    String username = rs.getString("Username");
			    String password = rs.getString("Password");
			    String firstName = rs.getString("FirstName");
			    String lastName = rs.getString("LastName");
			    String address = rs.getString("Address");
			    String checkingAccountNumber = rs.getString("CheckingAccountNumber");
			    String savingsAccountNumber = rs.getString("SavingsAccountNumber");
			    String creditCardAccountNumber = rs.getString("CreditCardAccountNumber");
			    String userID = rs.getString("UserID");
	
			    StandardMember member = new StandardMember(username, password, firstName, lastName, address, findCheckingAccount(checkingAccountNumber), findSavingsAccount(savingsAccountNumber), findCreditCardAccount(creditCardAccountNumber), userID);
			    standardMembers.add(member);
				}
			
			rs.close();
			stmt.close();
			conn.close();
			}
	
	
	static void gatherPremiumMembers() throws SQLException{
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PremiumMembers");
			ResultSet rs = stmt.executeQuery();
		
			while (rs.next()) {
			    String username = rs.getString("Username");
			    String password = rs.getString("Password");
			    String firstName = rs.getString("FirstName");
			    String lastName = rs.getString("LastName");
			    String address = rs.getString("Address");
			    String checkingAccountNumber = rs.getString("CheckingAccountNumber");
			    String savingsAccountNumber = rs.getString("SavingsAccountNumber");
			    String creditCardAccountNumber = rs.getString("CreditCardAccountNumber");
			    String mortgageAccountNumber = rs.getString("MortgageAccountNumber");
			    String userID = rs.getString("UserID");
		
			    PremiumMember member = new PremiumMember(username, password, firstName, lastName, address, findCheckingAccount(checkingAccountNumber), findSavingsAccount(savingsAccountNumber), findCreditCardAccount(creditCardAccountNumber),findMortgageAccount(mortgageAccountNumber), userID);
			    premiumMembers.add(member);
				}
	
			rs.close();
			stmt.close();
			conn.close();
			}
	
	
	static void gatherAdmins() throws SQLException{
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Admins");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
			    String username = rs.getString("Username");
			    String password = rs.getString("Password");
			    String firstName = rs.getString("FirstName");
			    String lastName = rs.getString("LastName");
			    String address = rs.getString("EmployeeID");
			    String userID = rs.getString("UserID");
	
			    Admin member = new Admin(username, password, firstName, lastName, address, userID);
			    admins.add(member);
				}
			
			rs.close();
			stmt.close();
			conn.close();
			}
	
	
	static void gatherCheckingAccounts() throws SQLException{
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CheckingAccounts");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
			    String userID = rs.getString("UserID");
			    Double balance = rs.getDouble("CheckingBalance");
			    int transactions = rs.getInt("Transactions");
			    String accountNumber = rs.getString("AccountNumber");
			    
			    CheckingAccount acc = new CheckingAccount(accountNumber);
			    acc.memberUserID = userID;
			    acc.balance = balance;
			    acc.transactions = transactions;
			    checkingAccounts.add(acc); 
				}
	
			rs.close();
			stmt.close();
			conn.close();
			}

	
	static void gatherSavingsAccounts() throws SQLException{
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SavingsAccounts");
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
		    String userID = rs.getString("UserID");
		    Double balance = rs.getDouble("SavingsBalance");
		    int transactions = rs.getInt("Transactions");
		    String accountNumber = rs.getString("AccountNumber");
	
		    SavingsAccount acc = new SavingsAccount(accountNumber);
		    acc.memberUserID = userID;
		    acc.balance = balance;
		    acc.transactions = transactions;
		    savingsAccounts.add(acc);  
			}
		
		rs.close();
		stmt.close();
		conn.close();
		}
	
	
	static void gatherCreditCardAccounts() throws SQLException{
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CreditCardAccounts");
		ResultSet rs = stmt.executeQuery();
	
		while (rs.next()) {
		    String userID = rs.getString("UserID");
		    Double balance = rs.getDouble("CreditCardBalance");
		    Double limit = rs.getDouble("CreditLimit");
		    int transactions = rs.getInt("Transactions");
		    String accountNumber = rs.getString("AccountNumber");
	
		    CreditCardAccount acc = new CreditCardAccount(accountNumber, limit);
		    acc.memberUserID = userID;
		    acc.balance = balance;
		    acc.transactions = transactions;
		    creditCardAccounts.add(acc);
		    }
	
		rs.close();
		stmt.close();
		conn.close();
		}
	

	static void gatherMortgageAccounts() throws SQLException{
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM MortgageAccounts");
		ResultSet rs = stmt.executeQuery();
	
		while (rs.next()) {
		    String userID = rs.getString("UserID");
		    Double mortgageBalance = rs.getDouble("MortgageBalance");
		    Double loanAmount = rs.getDouble("LoanAmount");
		    int transactions = rs.getInt("Transactions");
		    String accountNumber = rs.getString("AccountNumber");
	
		    MortgageAccount acc = new MortgageAccount(accountNumber, loanAmount);
		    acc.memberUserID = userID;
		    acc.balance = mortgageBalance;
		    acc.transactions = transactions;
		    mortgageAccounts.add(acc);
			}
	
		rs.close();
		stmt.close();
		conn.close();
		}
	
	static void gatherTransactions() throws SQLException{
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Transactions");
		ResultSet rs = stmt.executeQuery();
	
		while (rs.next()) {
		    String userID = rs.getString("UserID");
		    String employeeID = rs.getString("EmployeeID");
		    String tranxType = rs.getString("TransactionType");
		    String accType = rs.getString("AccountType");
		    String accID = rs.getString("AccountID");
		    double amount = rs.getDouble("Amount");
	
		    Transaction tran = new Transaction(userID, employeeID, tranxType, accType, accID, amount);
		    transactions.add(tran);
			}
	
		rs.close();
		stmt.close();
		conn.close();
		}

	
//////////////////////////////////////////////////////////////////////////////////
//* CHECK LOGIN METHOD *
//////////////////////////////////////////////////////////////////////////////////

	static boolean checkLogin(String username, String password){
		boolean isValid = false;
		System.out.println("Attempting to log in... ");
		
		try {
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
			PreparedStatement stmt = myConnection.prepareStatement("SELECT * FROM Credentials WHERE Username = ? AND Password = ?");
		    stmt.setString(1, username);
		    stmt.setString(2, password);	
		    ResultSet rs = stmt.executeQuery();
		    
		    if (rs.next()) {
		    	isValid = true;
		    	userType = rs.getString("Type");
		    	userID = rs.getString("UserID");
		    	
		    	if (userType.equals("Basic")) {
		    		for (BasicMember member : basicMembers) {
		    			if (userID.equals(member.userID)){
		    				basicMember = member;
		    				}
		    			}
		    		}
		    	
		    	else if (userType.equals("Standard")) {
		    		for (StandardMember member : standardMembers) {
		    			if (userID.equals(member.userID)){
		    				standardMember = member;
		    				}
		    			}
		    		}
		    	
		    	else if (userType.equals("Premium")) {
		    		for (PremiumMember member : premiumMembers) {
		    			if (userID.equals(member.userID)){
		    				premiumMember = member;
		    				}
		    			}
		    		}
		    	}	
	
		    myConnection.close();
		    stmt.close();
			}
		
		catch (Exception exc) {
			exc.printStackTrace();
			}
		
		return isValid;
		} 
	
//////////////////////////////////////////////////////////////////////////////////
//* GENERATE RANDOM DATA  *
//////////////////////////////////////////////////////////////////////////////////
	
	static String generateRandom() {
		Random value =  new Random();
		int intValue = value.nextInt(99999 - 10000 + 1) + 10000;
		String intValueString = Integer.toString(intValue);
		
		return intValueString;
		}
	
	static Double generateRandomCreditCardBalance() {
		Random value =  new Random();
		int intValue = value.nextInt(1500 - 50 + 1) + 50;
		double newValue = intValue;

		return newValue;
		}
	
	static Double generateRandomMortgageLoanAmount() {
		Random value =  new Random();
		int intValue = value.nextInt(200000 - 80000 + 1) + 80000;
		double newValue = intValue;

		return newValue;
		}
	
//////////////////////////////////////////////////////////////////////////////////
//* RELATE MEMBER OBJECTS WITH ACCOUNTS  *
//////////////////////////////////////////////////////////////////////////////////
	
	static void copyBasicChecking(){
		for (BasicMember member : basicMembers) {
			for (CheckingAccount acc : checkingAccounts) {
				if (member.userID.equals(acc.memberUserID)){
					member.checkingAccount.balance = acc.balance;
					member.checkingAccount.transactions = acc.transactions;
					}
				}
			}
		}
	
	
	static void copyBasicSavings(){
		for (BasicMember member : basicMembers) {
			for (SavingsAccount acc : savingsAccounts) {
				if (member.userID.equals(acc.memberUserID)){
					member.savingsAccount.balance = acc.balance;
					member.savingsAccount.transactions = acc.transactions;
				}
			}
		}
	}
	
	
	static void copyStandardChecking() {
		for (StandardMember member : standardMembers) {
			for (CheckingAccount acc : checkingAccounts) {
				if (member.userID.equals(acc.memberUserID)){
					member.checkingAccount.balance = acc.balance;
					member.checkingAccount.transactions = acc.transactions;
				}
			}
		}
	}
	
	
	static void copyStandardSavings() {
		for (StandardMember member : standardMembers) {
			for (SavingsAccount acc : savingsAccounts) {
				if (member.userID.equals(acc.memberUserID)){
					member.savingsAccount.balance = acc.balance;
					member.savingsAccount.transactions = acc.transactions;
				}
			}
		}
	}
	
	static void copyStandardCC() {
		for (StandardMember member : standardMembers) {
			for (CreditCardAccount acc : creditCardAccounts) {
				if (member.userID.equals(acc.memberUserID)){
					member.creditAccount.balance = acc.balance;
				}
			}
		}
	}
	
	
	static void copyPremiumChecking() {
		for (PremiumMember member : premiumMembers) {
			for (CheckingAccount acc : checkingAccounts) {
				if (member.userID.equals(acc.memberUserID)){
					member.checkingAccount.balance = acc.balance;
					member.checkingAccount.transactions = acc.transactions;
				}
			}
		}
	}
	
	
	static void copyPremiumSavings() {
		for (PremiumMember member : premiumMembers) {
			for (SavingsAccount acc : savingsAccounts) {
				if (member.userID.equals(acc.memberUserID)){
					member.savingsAccount.balance = acc.balance;
					member.savingsAccount.transactions = acc.transactions;
				}
			}
		}
	}
	
	static void copyPremiumCC() {
		for (PremiumMember member : premiumMembers) {
			for (CreditCardAccount acc : creditCardAccounts) {
				if (member.userID.equals(acc.memberUserID)){
					member.creditAccount.balance = acc.balance;
				}
			}
		}
	}
	
	static void copyPremiumMortgage() {
		for (PremiumMember member : premiumMembers) {
			for (MortgageAccount acc : mortgageAccounts) {
				if (member.userID.equals(acc.memberUserID)){
					member.mortgageAccount.balance = acc.balance;
				}
			}
		}
	}
	
//////////////////////////////////////////////////////////////////////////////////
//* FIND SPECIFIC ACCOUNTS  *
//////////////////////////////////////////////////////////////////////////////////
	
	static CheckingAccount findCheckingAccount(String checkingAccNumber) {
		for (CheckingAccount acc : checkingAccounts) {
			if (acc.accountNumber.equals(checkingAccNumber)) {
				return acc;
				}
			}
		return null;	
		}
	
	static SavingsAccount findSavingsAccount(String savingsAccNumber) {
		for (SavingsAccount acc : savingsAccounts) {
			if (acc.accountNumber.equals(savingsAccNumber)) {
				return acc;
				}
			}
		return null;	
		}
	
	static CreditCardAccount findCreditCardAccount(String creditAccNumber) {
		for (CreditCardAccount acc : creditCardAccounts) {
			if (acc.accountNumber.equals(creditAccNumber)) {
				return acc;
				}
			}
		return null;
		}
	
	static MortgageAccount findMortgageAccount(String mortgageAccNumber) {
		for (MortgageAccount acc : mortgageAccounts) {
			if (acc.accountNumber.equals(mortgageAccNumber)) {
				return acc;
				}
			}
		return null;
		}
	
//////////////////////////////////////////////////////////////////////////////////
//* CREATE ADMIN  *
//////////////////////////////////////////////////////////////////////////////////
	
	public static String createAdmin(Admin newAdmin) {
		String info = "";
	    
	    try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Bank", "root", "root");
	        PreparedStatement stmt = myConnection.prepareStatement("INSERT INTO `Admins`(`Username`, `Password`, `FirstName`,`LastName`, `EmployeeID`, `UserID`) VALUES (?,?,?,?,?,?)");
	    	PreparedStatement stmt2 = myConnection.prepareStatement("INSERT INTO `Credentials`(`Username`, `Password`, `UserID`,`Type` ) VALUES (?,?,?,?)"))
	    	{
	        stmt.setString(1, newAdmin.username);
	        stmt.setString(2, newAdmin.password);    
	        stmt.setString(3, newAdmin.firstName); 
	        stmt.setString(4, newAdmin.lastName);
	        stmt.setString(5, newAdmin.employeeID);
	        stmt.setString(6, newAdmin.userID);        
	        int rowsAffected = stmt.executeUpdate();
	        
	        stmt2.setString(1, newAdmin.username);
	        stmt2.setString(2, newAdmin.password);    
	        stmt2.setString(3, newAdmin.userID); 
	        stmt2.setString(4, newAdmin.membershipType);
	        stmt2.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            info += "Result: Admin, " + newAdmin.firstName + " has been created."; 
	        	}  
	        
	        else {
	            info += "Result: Unsuccessful, try again later.";
	        	}
	    	} 
	    
	    catch (SQLException exc) {
	        info += "Something went wrong.";
	    	}
	    return info;
		}
	
//////////////////////////////////////////////////////////////////////////////////
//* CREATE MEMBERS  *
//////////////////////////////////////////////////////////////////////////////////
	
	static void createBasicMember(){
		System.out.println("Create a new Basic Member.");
		System.out.println("Enter a Username: ");
		String basicUsername = input.next();
		System.out.println("Enter a Password: ");
		String basicPassword = input.next();
		System.out.println("Enter First Name: ");
		String basicFirstName = input.next();
		System.out.println("Enter Last Name: ");
		String basicLastName = input.next();
		input.nextLine(); 
		System.out.println("Enter their address: ");
		String basicAddress = input.nextLine();
		
		BasicMember basicMember = new BasicMember(basicUsername, basicPassword, basicFirstName, basicLastName, basicAddress, new CheckingAccount(generateRandom()), new SavingsAccount(generateRandom()), generateRandom());
	    basicMember.checkingAccount.memberUserID = basicMember.userID;
	    basicMember.savingsAccount.memberUserID = basicMember.userID;
		basicMembers.add(basicMember);
		checkingAccounts.add(basicMember.checkingAccount);
		System.out.println(BankClerk.createBasicMember(basicMember));
		}
	
	static void createStandardMember(){
		System.out.println("Create a new Standard Member.");
		System.out.println("Enter a Username: ");
		String standardUsername = input.next();
		System.out.println("Enter a Password: ");
		String standardPassword = input.next();
		System.out.println("Enter First Name: ");
		String standardFirstName = input.next();
		System.out.println("Enter Last Name: ");
		String standardLastName = input.next();
		input.nextLine(); 
		System.out.println("Enter their address: ");
		String standardAddress = input.nextLine();
		
		StandardMember standardMember = new StandardMember(standardUsername, standardPassword, standardFirstName, standardLastName, standardAddress, new CheckingAccount(generateRandom()), new SavingsAccount(generateRandom()), new CreditCardAccount(generateRandom(), creditLimit), generateRandom());
	    standardMember.checkingAccount.memberUserID = standardMember.userID;
	    standardMember.savingsAccount.memberUserID = standardMember.userID;
	    standardMember.creditAccount.memberUserID = standardMember.userID;
	    standardMember.creditAccount.balance = generateRandomCreditCardBalance();
		standardMembers.add(standardMember);
		checkingAccounts.add(standardMember.checkingAccount);
		savingsAccounts.add(standardMember.savingsAccount);
		creditCardAccounts.add(standardMember.creditAccount);
		
		System.out.println(BankClerk.createStandardMember(standardMember));	
		}
	
	static void createPremiumMember(){
		System.out.println("Create a Premium Member.");
		System.out.println("Enter a Username: ");
		String premiumUsername = input.next();
		System.out.println("Enter a Password: ");
		String premiumPassword = input.next();
		System.out.println("Enter First Name: ");
		String premiumFirstName = input.next();
		System.out.println("Enter Last Name: ");
		String premiumLastName = input.next();
		input.nextLine(); 
		System.out.println("Enter their address: ");
		String premiumAddress = input.nextLine();
		
		PremiumMember premiumMember = new PremiumMember(premiumUsername, premiumPassword, premiumFirstName, premiumLastName, premiumAddress, new CheckingAccount(generateRandom()), new SavingsAccount(generateRandom()), new CreditCardAccount(generateRandom(), creditLimit), new MortgageAccount(generateRandom(),generateRandomMortgageLoanAmount()), generateRandom());
		premiumMember.checkingAccount.memberUserID = premiumMember.userID;
		premiumMember.savingsAccount.memberUserID = premiumMember.userID;
		premiumMember.creditAccount.memberUserID = premiumMember.userID;
		premiumMember.mortgageAccount.memberUserID = premiumMember.userID;
		premiumMember.mortgageAccount.balance = premiumMember.mortgageAccount.loanAmount;
		
		premiumMember.creditAccount.balance = generateRandomCreditCardBalance();
		
		premiumMembers.add(premiumMember);
		checkingAccounts.add(premiumMember.checkingAccount);
		savingsAccounts.add(premiumMember.savingsAccount);
		creditCardAccounts.add(premiumMember.creditAccount);
		mortgageAccounts.add(premiumMember.mortgageAccount);
		
		System.out.println(BankClerk.createPremiumMember(premiumMember));
		}
	
	
	static void createBankClerk(){
		System.out.println("Create a new Bank Clerk.");
		System.out.println("Enter a Username: ");
		String clerkUsername = input.next();
		System.out.println("Enter a Password: ");
		String clerkPassword = input.next();
		System.out.println("Enter First Name: ");
		String clerkFirstName = input.next();
		System.out.println("Enter Last Name: ");
		String clerkLastName = input.next();
		input.nextLine(); 
		
		BankClerk bankClerk = new BankClerk(clerkUsername, clerkPassword, clerkFirstName, clerkLastName, generateRandom(), generateRandom());
		bankClerks.add(bankClerk);
		
		System.out.println(BankManager.createBankClerk(bankClerk));
		}
	
	
	static void createBankManager(){
		System.out.println("Create a new Bank Manager.");
		System.out.println("Enter a Username: ");
		String mgrUsername = input.next();
		System.out.println("Enter a Password: ");
		String mgrPassword = input.next();
		System.out.println("Enter First Name: ");
		String mgrFirstName = input.next();
		System.out.println("Enter Last Name: ");
		String mgrLastName = input.next();
		input.nextLine(); 
		
		BankManager bankMgr = new BankManager(mgrUsername, mgrPassword, mgrFirstName, mgrLastName, generateRandom(), generateRandom());
		bankManagers.add(bankMgr);
		
		System.out.println(Admin.createBankManager(bankMgr));
		}
	
	
//////////////////////////////////////////////////////////////////////////////////
//* View Transactions  *
//////////////////////////////////////////////////////////////////////////////////
	
static void viewChecking(String UserID) {
	System.out.println("===============================================================");
	System.out.println("              *  Checking Account Transactions  *");
	System.out.println("---------------------------------------------------------------");
	for (Transaction tran : transactions) {
		if ((userID.equals(tran.userID) && tran.accountType.equals("Checking"))){
			System.out.println("Transaction Type: " + tran.tranxType +  " | Amount: " + tran.amount);
			
			}
		}
	System.out.println("===============================================================");
	}

static void viewSavings(String UserID) {
	System.out.println("===============================================================");
	System.out.println("              *  Savings Account Transactions  *");
	System.out.println("---------------------------------------------------------------");
	for (Transaction tran : transactions) {
		if ((userID.equals(tran.userID) && tran.accountType.equals("Savings"))){
			System.out.println("Transaction Type: " + tran.tranxType +  " | Amount: " + tran.amount);
			
			}
		}
	System.out.println("===============================================================");
	}

static void viewCC(String UserID) {
	System.out.println("===============================================================");
	System.out.println("              *  Credit Card Account Transactions  *");
	System.out.println("---------------------------------------------------------------");
	for (Transaction tran : transactions) {
		if ((userID.equals(tran.userID) && tran.accountType.equals("Credit Card"))){
			System.out.println("Transaction Type: " + tran.tranxType +  " | Amount: " + tran.amount);
			
			}
		}
	System.out.println("===============================================================");
	}

static void viewMortgage(String UserID) {
	System.out.println("===============================================================");
	System.out.println("              *  Mortgage Account Transactions  *");
	System.out.println("---------------------------------------------------------------");
	for (Transaction tran : transactions) {
		if ((userID.equals(tran.userID) && tran.accountType.equals("Mortgage"))){
			System.out.println("Transaction Type: " + tran.tranxType +  " | Amount: " + tran.amount);
			
			}
		
		}
	System.out.println("===============================================================");
	}
	
	
	
	
	
	
	
	
//////////////////////////////////////////////////////////////////////////////////
//* OTHER - FOR TESTING  *
//////////////////////////////////////////////////////////////////////////////////
	
	static void printCCAccounts() {
		for (CreditCardAccount acc : creditCardAccounts) {
			System.out.println(acc.accountNumber);
			}
		}
	
} // END OF CLASS: BRANCH


	
		    
	


