// Withdrawal.java
// Represents a withdrawal ATM transaction

public class Withdrawal extends Transaction
{
   private int amount; // amount to withdraw
   private CashDispenser cashDispenser; // reference to cash dispenser

   // Withdrawal constructor
   public Withdrawal( int userAccountNumber, screenWithButtons atmScreen, 
      BankDatabase atmBankDatabase, CashDispenser atmCashDispenser, int inputAmount )
   {
      // initialize superclass variables
      super( userAccountNumber, atmScreen, atmBankDatabase );
      
      // initialize references to keypad and cash dispenser
      cashDispenser = atmCashDispenser;
      amount = inputAmount;
   } // end Withdrawal constructor

   public Withdrawal( Account userAccount, screenWithButtons atmScreen, 
   BankDatabase atmBankDatabase,CashDispenser atmCashDispenser, int inputAmount )
   {
      super( userAccount, atmScreen, atmBankDatabase );
      cashDispenser = atmCashDispenser;
      amount = inputAmount;
   }


   // perform transaction
   public void execute()
   {
      boolean DisableDispenser = false; // cash was not dispensed yet

      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase();
      
      Account subAccount = super.getAccount();

      double availableBalance = (subAccount != null)? bankDatabase.getAvailableBalance( getAccount()):
      bankDatabase.getAvailableBalance( getAccountNumber() ); // amount available for withdrawal

      boolean flag = super.getFlag();

      int currentAccountNumber = getAccountNumber();
      // System.out.println(flag);
      if (!flag) {
         DisableDispenser = normalWithdraw(availableBalance, currentAccountNumber, screen, bankDatabase, DisableDispenser);
      }else{
         DisableDispenser = compoundWithdraw(availableBalance, subAccount, screen, bankDatabase, DisableDispenser);
      }

      // loop until cash is dispensed or the user cancels
   } // end method execute

   private boolean normalWithdraw(double availableBalance, int currentAccountNumber, screenWithButtons screen
   , BankDatabase database, boolean disabled) {

         // check whether the user has enough money in the account 
         if ( amount <= availableBalance && amount > 0 )
         {   
            // check whether the cash dispenser has enough money
            if ( cashDispenser.isSufficientCashAvailable( amount ) )
            {
               // update the account involved to reflect withdrawal
               database.debit( getAccountNumber(), amount );
               
               cashDispenser.dispenseCash( amount ); // dispense cash
               disabled = true; // cash was dispensed

               // instruct user to take cash
               screen.jTextArea1.setText(
                  "\nRequest successful." + 
                  "\nCash will be dispensed after log out."+
                  "\nPlease remeber to take them." +
                  "\n\nReady for other opperation."
                  );
            } 
            else{
               screen.jTextArea1.setText(
                  "\nInsufficient cash available in the ATM." +
                  "\n\nPlease choose a smaller amount."+
                  "\n\nAborted."
               );
            }
         } 
         else // not enough money available in user's account
         {
            screen.jTextArea1.setText(
               "\nInsufficient funds in your account." +
               "\n\nPlease choose a smaller amount." +
               "\n\nAborted."
               );
         } // end else

         // check whether user chose a withdrawal amount or canceled
         return disabled;
   }

   private boolean compoundWithdraw(double availableBalance, Account subAccount, screenWithButtons screen,  
      BankDatabase database, boolean disabled) {

      // check whether the user has enough money in the account 
      if ( amount <= availableBalance && amount > 0)
      {   
         // check whether the cash dispenser has enough money
         if ( cashDispenser.isSufficientCashAvailable( amount ) )
         {
            // update the account involved to reflect withdrawal
            database.debit( subAccount, amount );
            
            cashDispenser.dispenseCash( amount ); // dispense cash
            disabled = true; // cash was dispensed

            // instruct user to take the card and cash
            screen.jTextArea1.setText(
               "\nRequest successful." + 
               "\nCash will be dispensed after log out."+
               "\nPlease remeber to take them."+
               "\n\nReady for other opperation."
            );
         } 
         else{
            screen.jTextArea1.setText(
               "\nInsufficient cash available in the ATM." +
               "\n\nPlease choose a smaller amount." +
               "\n\nAborted."
               );   
         }
      } 
      else 
      {
         screen.jTextArea1.setText(
            "\nInsufficient funds in your account." +
            "\n\nPlease choose a smaller amount." +
            "\n\nAborted."
            );
      } 
      return disabled;
   }

   // to check whether user's input is a multiple of 100
   public boolean checkIsMultiple(int input, screenWithButtons screen){
      int mod = input % 100;
      if (mod == 0) {
         return true;
      }else{
         screen.jTextArea1.setText("Your input is NOT a multiple of 100");
         return false;   
      }
   }
} // end class Withdrawal



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