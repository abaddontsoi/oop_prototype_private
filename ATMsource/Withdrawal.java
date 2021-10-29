// Withdrawal.java
// Represents a withdrawal ATM transaction

public class Withdrawal extends Transaction
{
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser

   // constant corresponding to menu option to customize amount
   private final static int CUSTOMIZE = 6;

   // constant corresponding to menu option to cancel
   private final static int CANCELED = 7;

   // Withdrawal constructor
   public Withdrawal( int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      CashDispenser atmCashDispenser )
   {
      // initialize superclass variables
      super( userAccountNumber, atmScreen, atmBankDatabase );
      
      // initialize references to keypad and cash dispenser
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
   } // end Withdrawal constructor

   public Withdrawal( Account userAccount, Screen atmScreen, 
   BankDatabase atmBankDatabase,Keypad atmKeypad, 
   CashDispenser atmCashDispenser )
   {
      super( userAccount, atmScreen, atmBankDatabase );
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
   }


   // perform transaction
   public void execute()
   {
      boolean cashDispensed = false; // cash was not dispensed yet

      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase();
      Screen screen = getScreen();

      Account subAccount = super.getAccount();

      double availableBalance = (subAccount != null)? bankDatabase.getAvailableBalance( getAccount()):
      bankDatabase.getAvailableBalance( getAccountNumber() ); // amount available for withdrawal

      boolean flag = super.getFlag();

      int currentAccountNumber = getAccountNumber();

      do{
         if (flag) {
            cashDispensed = compoundWithdraw(availableBalance, subAccount, screen, keypad, bankDatabase, cashDispensed);
         }else{
            cashDispensed = normalWithdraw(availableBalance, currentAccountNumber, screen, keypad, bankDatabase, cashDispensed);
         }
      }while(!cashDispensed);

      // loop until cash is dispensed or the user cancels
   } // end method execute

   private boolean normalWithdraw(double availableBalance, int currentAccountNumber, Screen screen,
       Keypad keypad_Keypad, BankDatabase database, boolean Dispensed) {

         // obtain a chosen withdrawal amount from the user 
         amount = displayMenuOfAmounts();
         
         // check whether user chose a withdrawal amount or canceled
         if ( amount != CANCELED )
         {
            // check whether the user has enough money in the account 
            if ( amount <= availableBalance )
            {   
               // check whether the cash dispenser has enough money
               if ( cashDispenser.isSufficientCashAvailable( amount ) )
               {
                  // update the account involved to reflect withdrawal
                  database.debit( getAccountNumber(), amount );
                  
                  cashDispenser.dispenseCash( amount ); // dispense cash
                  Dispensed = true; // cash was dispensed

                  // instruct user to take cash
                  screen.displayMessageLine( 
                     "\nPlease take your cash now." );
               } // end if
               else // cash dispenser does not have enough cash
                  screen.displayMessageLine( 
                     "\nInsufficient cash available in the ATM." +
                     "\n\nPlease choose a smaller amount." );
            } // end if
            else // not enough money available in user's account
            {
               screen.displayMessageLine( 
                  "\nInsufficient funds in your account." +
                  "\n\nPlease choose a smaller amount." );
            } // end else
         } // end if
         else // user chose cancel menu option 
         {
            screen.displayMessageLine( "\nCanceling transaction..." );
            //return; // return to main menu because user canceled
         } // end else
      return Dispensed;
   }

   private boolean compoundWithdraw(double availableBalance, Account subAccount, Screen screen, Keypad keypad_Keypad, 
      BankDatabase database, boolean Dispensed) {
         // obtain a chosen withdrawal amount from the user 
         amount = displayMenuOfAmounts();
         
         // check whether user chose a withdrawal amount or canceled
         if ( amount != CANCELED )
         {
            // check whether the user has enough money in the account 
            if ( amount <= availableBalance )
            {   
               // check whether the cash dispenser has enough money
               if ( cashDispenser.isSufficientCashAvailable( amount ) )
               {
                  // update the account involved to reflect withdrawal
                  database.debit( subAccount, amount );
                  
                  cashDispenser.dispenseCash( amount ); // dispense cash
                  Dispensed = true; // cash was dispensed

                  // instruct user to take cash
                  screen.displayMessageLine( 
                     "\nPlease take your cash now." );
               } // end if
               else // cash dispenser does not have enough cash
                  screen.displayMessageLine( 
                     "\nInsufficient cash available in the ATM." +
                     "\n\nPlease choose a smaller amount." );
            } // end if
            else // not enough money available in user's account
            {
               screen.displayMessageLine( 
                  "\nInsufficient funds in your account." +
                  "\n\nPlease choose a smaller amount." );
            } // end else
         } // end if
         else // user chose cancel menu option 
         {
            screen.displayMessageLine( "\nCanceling transaction..." );
            //return; // return to main menu because user canceled
         } // end else
      return Dispensed;
   }

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private int displayMenuOfAmounts()
   {
      int userChoice = 0; // local variable to store return value

      Screen screen = getScreen(); // get screen reference
      
      // array of amounts to correspond to menu numbers
      int amounts[] = { 0, 200, 400, 600, 800, 1000};

      // loop while no valid choice has been made
      while ( userChoice == 0 )
      {
         // display the menu
         screen.displayMessageLine( "\nWithdrawal Menu:" );
         screen.displayMessageLine( "1 - $200" );
         screen.displayMessageLine( "2 - $400" );
         screen.displayMessageLine( "3 - $600" );
         screen.displayMessageLine( "4 - $800" );
         screen.displayMessageLine( "5 - $1000" );
         screen.displayMessageLine( "6 - Custom amount" );
         screen.displayMessageLine( "7 - Cancel transaction" );
         screen.displayMessage( "\nChoose a withdrawal amount: " );

         int input = keypad.getInput(); // get user input through keypad

         // determine how to proceed based on the input value
         switch ( input )
         {
            case 1: // if the user chose a withdrawal amount 
            case 2: // (i.e., chose option 1, 2, 3, 4 or 5), return the
            case 3: // corresponding amount from amounts array
            case 4:
            case 5:
               userChoice = amounts[ input ]; // save user's choice
               break;

            case CUSTOMIZE: // get user input
               screen.displayMessageLine("Please enter withdrawl option: ");
               int temp = keypad.getInput();
               if (checkIsMultiple(temp, screen)) {
                  userChoice = temp;
               }
               break;
               
            case CANCELED: // the user chose to cancel
               userChoice = CANCELED; // save user's choice
               break;
            default: // the user did not enter a value from 1-6
               screen.displayMessageLine( 
                  "\nIvalid selection. Try again." );
         } // end switch
      } // end while

      return userChoice; // return withdrawal amount or CANCELED
   } // end method displayMenuOfAmounts

   // to check whether user's input is a multiple of 100
   public boolean checkIsMultiple(int input, Screen screen){
      int mod = input % 100;
      if (mod == 0) {
         return true;
      }else{
         screen.displayMessageLine("Input is not a multiple of $100, aborting...");
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