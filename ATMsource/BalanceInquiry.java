// BalanceInquiry.java
// Represents a balance inquiry ATM transaction

public class BalanceInquiry extends Transaction
{
   // BalanceInquiry constructor
   public BalanceInquiry( int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase )
   {
      super( userAccountNumber, atmScreen, atmBankDatabase );
   } // end BalanceInquiry constructor

   public BalanceInquiry( Account userAccount, Screen atmScreen, 
      BankDatabase atmBankDatabase )
   {
      super( userAccount, atmScreen, atmBankDatabase );
   }

   // performs the transaction
   public void execute()
   {
      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase();
      Screen screen = getScreen();
      double availableBalance,totalBalance;
      //double totalBalance;

      // if it is not a compound bank account, use account number on transaction 
      if (super.getAccount() != null) {
         // get the available balance for the account involved
         availableBalance = 
            bankDatabase.getAvailableBalance( getAccount() );

         // get the total balance for the account involved
         totalBalance = 
            bankDatabase.getTotalBalance( getAccount() );
         
      }else{
         // get the available balance for the account involved
         availableBalance = 
            bankDatabase.getAvailableBalance( getAccountNumber() );

         // get the total balance for the account involved
         totalBalance = 
            bankDatabase.getTotalBalance( getAccountNumber() );
      }
      // display the balance information on the screen
      screen.displayWindowsMessage( "\nBalance Information:" );
      screen.displayMessage( " - Available balance: " ); 
      screen.displayDollarAmount( availableBalance );
      screen.displayMessage( "\n - Total balance:     " );
      screen.displayDollarAmount( totalBalance );
      screen.displayWindowsMessage( "" );
   } // end method execute
} // end class BalanceInquiry



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