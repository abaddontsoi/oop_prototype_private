// Account.java
// Represents a bank account

public class Account 
{
	private int accountNumber; // account number
	private int pin; // PIN for authentication
	private double availableBalance; // funds available for withdrawal
	private double totalBalance; // funds available + pending deposits

	// these attributes are only for accounts having saving and chequeing purpose
	private SavingAccount saving = null;
	private ChequeAccount chequing = null;

	// we assume existing accounts remain it original type, until the owner requests for changing
	private static String TYPE = "General account";

	// Account constructor initializes attributes
	public Account( int theAccountNumber, int thePIN, 
		double theAvailableBalance, double theTotalBalance )
	{
		accountNumber = theAccountNumber;
		pin = thePIN;
		availableBalance = theAvailableBalance;
		totalBalance = theTotalBalance;
	} // end Account constructor

	// constructor initializes attributes for both saving and chequeing purpose 
	public Account(int theAccountNumber, int thePIN, 
	double savingAvailableBalance, double savingTotalBalance, double chequingAvailableBalance, double chequingTotalBalance)
	{
		accountNumber = theAccountNumber;
		pin = thePIN;

		saving = new SavingAccount(theAccountNumber, thePIN, savingAvailableBalance, savingTotalBalance);
		chequing = new ChequeAccount(theAccountNumber, thePIN, chequingAvailableBalance, chequingTotalBalance);

		TYPE = "Both";
	}

	public void _passBalance() {
		if(_isSwapable()){
			saving.credit(totalBalance);
			totalBalance = 0;
		}
	}

	// return true when the user owns both saving and chequing accounts
	public boolean _isSwapable() {
		return (saving != null) && (chequing != null);
	}

	// swap to saving account
	public SavingAccount _swapToSaving(){
		return saving;
	}

	// swap to chequing accout
	public ChequeAccount _swapToChequing(){
		return chequing;
	}

	// determines whether a user-specified PIN matches PIN in Account
	public boolean validatePIN( int userPIN )
	{
		if ( userPIN == pin )
			return true;
		else
			return false;
	} // end method validatePIN
	
	// returns available balance
	public double getAvailableBalance()
	{
		return availableBalance;
	} // end getAvailableBalance

	// returns the total balance
	public double getTotalBalance()
	{
		return totalBalance;
	} // end method getTotalBalance

	// credits an amount to the account
	public void credit( double amount )
	{
		totalBalance += amount; // add to total balance
	} // end method credit

	// debits an amount from the account
	public void debit( double amount )
	{
		availableBalance -= amount; // subtract from available balance
		totalBalance -= amount; // subtract from total balance
	} // end method debit

	// returns account number
	public int getAccountNumber()
	{
		return accountNumber;  
	} // end method getAccountNumber

	// returns account type
	public String getType() {
		return TYPE; 
	}
} // end class Account


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