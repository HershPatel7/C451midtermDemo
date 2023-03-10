package MidtermDemo;

public class MortgageAccount extends Account {
	double loanAmount;
	MortgageAccount(String accountNumber, double loanAmount) {
		super(accountNumber);
		this.loanAmount = loanAmount;
	}

}
