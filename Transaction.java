package MidtermDemo;

public class Transaction {
	String userID;
	String employeeID;
	String tranxType;
	String accountType;
	String accountID;
	Double amount;
	
	
	Transaction(String userID, String employeeID, String tranxType, String accountType, String accountID, Double amount){
		this.userID = userID;
		this.employeeID = employeeID;
		this.tranxType = tranxType;
		this.accountType = accountType;
		this.accountID = accountID;
		this.amount = amount;
	}
	

}
