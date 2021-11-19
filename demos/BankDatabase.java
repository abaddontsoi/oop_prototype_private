
// BankDatabase.java
// Represents the bank account information database 

public class BankDatabase
{
	private Account accounts[]; // array of Accounts
	
	// no-argument BankDatabase constructor initializes accounts
	public BankDatabase()
	{
		accounts = new Account[ 4 ]; // just 2 accounts for default testing, and 2 for account types testing
		accounts[ 0 ] = new SavingAccount( 12345, 54321, 1000.0, 1200.0 );

		// for users having general account, (i.e. neither saving nor chequing)
		accounts[ 1 ] = new Account( 98765, 56789, 200.0, 200.0 );

		// new accounts of checque account and saving account
		accounts[ 2 ] = new ChequeAccount(2, 2, 500, 1000);

		// new account for users having both saving and chequing
		accounts[ 3 ] = new Account(3, 3, 900000, 1000000, 200000, 400000);

	} // end no-argument BankDatabase constructor
	
	// retrieve Account object containing specified account number
	private Account getAccount( int accountNumber )
	{
		// loop through accounts searching for matching account number
		for ( Account currentAccount : accounts )
		{
			// return current account if match found
			if ( currentAccount.getAccountNumber() == accountNumber )
				return currentAccount;
		} // end for

		return null; // if no matching account was found, return null
	} // end method getAccount

	// determine whether user-specified account number and PIN match
	// those of an account in the database
	public boolean authenticateUser( int userAccountNumber, int userPIN )
	{
		// attempt to retrieve the account with the account number
		Account userAccount = getAccount( userAccountNumber );

		// if account exists, return result of Account method validatePIN
		if ( userAccount != null )
			return userAccount.validatePIN( userPIN );
		else
			return false; // account number not found, so return false
	} // end method authenticateUser

	// to check whether the account is exists in the database
	public boolean accountExists(int ACnumber){

		int acFound = 0;

		// loop through accounts searching for matching account number
		for ( Account currentAccount : accounts )
		{
			if(currentAccount.getAccountNumber() == ACnumber)
				acFound += 1;
		} // end for

		// return true when only one account is matched with ACnumber
		return acFound==1;

	}

	// return available balance of Account with specified account number
	public double getAvailableBalance( int userAccountNumber )
	{
		return getAccount( userAccountNumber ).getAvailableBalance();
	} // end method getAvailableBalance
	public double getAvailableBalance( Account userAccount )
	{
		return userAccount.getAvailableBalance();
	} // end method getAvailableBalance


	// return total balance of Account with specified account number
	public double getTotalBalance( int userAccountNumber )
	{
		return getAccount( userAccountNumber ).getTotalBalance();
	} // end method getTotalBalance
	// return total balance of Account with specified account number
	public double getTotalBalance( Account userAccount )
	{
			return userAccount.getTotalBalance();
	} // end method getTotalBalance

	// return saving account total balance
	// return chequing account total balance
	public double getTotalBalance( int userAccountNumber, String ACType )
	{
		
		switch (ACType) {
			case "Cheque account":
			case "Saving account":
				
				break;
		
			default:
				break;
		}

		return getAccount( userAccountNumber ).getTotalBalance();
	} // end method getTotalBalance

	public void passBalance(int bothTypeACNumber){
		getAccount(bothTypeACNumber)._passBalance();
	}

	// credit an amount to Account with specified account number
	public void credit( int userAccountNumber, double amount )
	{
		getAccount( userAccountNumber ).credit( amount );
	} // end method credit

	// debit an amount from of Account with specified account
	public void credit( Account userAccount, double amount )
	{
		userAccount.credit( amount );
	} // end method debit

	// debit an amount from of Account with specified account number
	public void debit( int userAccountNumber, double amount )
	{
		getAccount( userAccountNumber ).debit( amount );
	} // end method debit

	// debit an amount from of Account with specified account
	public void debit( Account userAccount, double amount )
	{
		userAccount.debit( amount );
	} // end method debit

	// allows backend to set cheque limit for an account
   	public void setChequeLimit(int userAccountNumber, double amount) {

		// downcasting current account to ChequeAccount for setting limit
		ChequeAccount temp = (ChequeAccount) getAccount(userAccountNumber);
        temp.setLimit(amount);
   	}

	// return user's account type in string
	public String getAccountTypeString(int userAccountNumber) {
		return getAccount(userAccountNumber).getType();
	}

	// allows backend set the interest rate for an account
	public void setInterestRate(int currentAccountNumber, int rate) {
		SavingAccount temp = (SavingAccount) getAccount(currentAccountNumber);
		temp.setInterestRate(rate);
	}

	// returns current interest rate to backend
	public double getCurrentRate(int currentAccountNumber) {
		SavingAccount temp = (SavingAccount) getAccount(currentAccountNumber);
		double rate = temp.getInterestRate();
		return rate;
	}

	// swap to saving account
	public SavingAccount swapToSaving(int currentGeneralAC) {
		SavingAccount temp = getAccount(currentGeneralAC)._swapToSaving();
		return temp;
	}

	// swap to chequing account
	public ChequeAccount swapToChequing(int currentGeneralAC) {
		ChequeAccount temp = getAccount(currentGeneralAC)._swapToChequing();
		return temp;
	}

	// indicates whether the account is swapable
	public boolean isSwapable(int currentAccountNumber) {
		boolean flag = getAccount(currentAccountNumber)._isSwapable();
		return flag;
	}
} // end class BankDatabase



/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/