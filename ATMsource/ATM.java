// ATM.java
// Represents an automated teller machine

public class ATM 
{
    private boolean userAuthenticated; // whether user is authenticated
    private int currentAccountNumber; // current user's account number
    private Screen screen; // ATM's screen
    private Keypad keypad; // ATM's keypad
    private CashDispenser cashDispenser; // ATM's cash dispenser
    //private DepositSlot depositSlot; // ATM's deposit slot
    private BankDatabase bankDatabase; // account information database

    // constants corresponding to main menu options
    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    // private static final int DEPOSIT = 3;
    
    // added new main menu option
    private static final int TRANSFER = 3;

    // for exit
    private static final int EXIT = 0;

    // for swaping
    private Account swap = null;
    private static final int SWAPTOSAVING = 8;
    private static final int SWAPTOCHEQUING = 9;
    private static final int NOSWAPPING = 0;

    // used as switch option
    private final String GENERALTYPE = "General account";
    private final String SAVINGTYPE = "Saving account";
    private final String CHEQUEINGTYPE = "Cheque account";
    private final String BOTHTYPE = "Both";


   // no-argument ATM constructor initializes instance variables
    public ATM() 
    {
        userAuthenticated = false; // user is not authenticated to start
        currentAccountNumber = 0; // no current account number to start
        screen = new Screen(); // create screen
        keypad = new Keypad(); // create keypad 
        cashDispenser = new CashDispenser(); // create cash dispenser
        //depositSlot = new DepositSlot(); // create deposit slot
        bankDatabase = new BankDatabase(); // create acct info database
    } // end no-argument ATM constructor

    // start ATM 
    public void run()
    {
        // welcome and authenticate user; perform transactions
        while ( true )
        {
            // loop while user is not yet authenticated
            while ( !userAuthenticated ) 
            {
                screen.displayMessageLine( "\nWelcome!" );       
                authenticateUser(); // authenticate user
            } // end while
         
            performTransactions(); // user is now authenticated 
            userAuthenticated = false; // reset before next ATM session
            currentAccountNumber = 0; // reset before next ATM session 
            screen.displayMessageLine( "\nThank you! Goodbye!" );
        } // end while   
    } // end method run

    // attempts to authenticate user against database
    private void authenticateUser() 
    {
        screen.displayMessage( "\nPlease enter your account number: " );
        int accountNumber = keypad.getInput(); // input account number
        screen.displayMessage( "\nEnter your PIN: " ); // prompt for PIN
        int pin = keypad.getInput(); // input PIN
        
        // set userAuthenticated to boolean value returned by database
        userAuthenticated = 
            bankDatabase.authenticateUser( accountNumber, pin );
        
        // check whether authentication succeeded
        if ( userAuthenticated )
        {
            currentAccountNumber = accountNumber; // save user's account #
            if (bankDatabase.getAccountTypeString(currentAccountNumber)==BOTHTYPE) {
                bankDatabase.passBalance(currentAccountNumber);
            }
        } // end if
        else
            screen.displayMessageLine( 
                "Invalid account number or PIN. Please try again." );

    } // end method authenticateUser

    // display the main menu and perform transactions    
    private void performTransactions(){

        // Transaction currentTransaction = null;
      
        boolean userExited = false; // user has not chosen to exit

        //int mainMenuSelection = displayMainMenu();

        String type = bankDatabase.getAccountTypeString(currentAccountNumber);

        //int swapSelection ;

        while (!userExited) {
            int mainMenuSelection = displayMainMenu();

            switch (mainMenuSelection) {
                case SWAPTOCHEQUING:
                    if (swap == null) {
                        swap = bankDatabase.swapToChequing(currentAccountNumber);
                        performTransactions();
                        break;
                    }
                    // userExited = true;
                case SWAPTOSAVING:
                    if(swap == null){
                        swap = bankDatabase.swapToSaving(currentAccountNumber);
                        performTransactions();
                        break;
                    }
                    // userExited = true;
                case NOSWAPPING:
                    if(swap == null){
                        userExited = true;
                        break;
                    }
                default:
                    // if swapped, change type to swapped account and perform related transactions.
                    if (swap != null) {
                        type = bankDatabase.getAccountTypeString(swap.getAccountNumber());
                    }
                    userExited = transactionsControl(mainMenuSelection, type);
            }
            mainMenuSelection = 0;
        }
        swap = null;
    }

    private boolean transactionsControl(int mainMenuSelection, String ACtype) {

        Transaction currentTransaction = null;

        boolean exitSignal = false;

        while (!exitSignal ) {

            switch ( mainMenuSelection )
            {
                // user chose to perform one of three transaction types
                case BALANCE_INQUIRY: 
                case WITHDRAWAL: 
                //case DEPOSIT:
                case TRANSFER:

                    // initialize as new object of chosen type
                    currentTransaction = 
                        createTransaction( mainMenuSelection, ACtype );

                    currentTransaction.execute(); // execute transaction when is not exit signal of general account    
                    return exitSignal;
                    //break;
                case EXIT: // user chose to terminate session
                    screen.displayMessageLine( "\nExiting the system..." );
                    exitSignal = true; // this ATM session should end
                    return exitSignal;

                    //break;
                default: // user did not enter an integer from 1-4
                    screen.displayMessageLine( 
                    "\nYou did not enter a valid selection. Try again." );
                    return exitSignal;
                    //break;
            }   // end switch
        }
        return exitSignal;
    }

    // display the main menu and return an input selection
    private int displayMainMenu()
    {
        // String type = bankDatabase.getAccountTypeString(currentAccountNumber);
        String type = (swap instanceof SavingAccount) ? SAVINGTYPE : (swap instanceof ChequeAccount)? CHEQUEINGTYPE: 
            bankDatabase.getAccountTypeString(currentAccountNumber);

        swap = (swap instanceof SavingAccount)? swap = (SavingAccount) swap : (swap instanceof ChequeAccount)?
            swap = (ChequeAccount) swap : swap;
        int selection = 0;

        // showing which type of current account
        screen.displayMessageLine("\nYour current account type is: " + type);

        if (bankDatabase.isSwapable(currentAccountNumber) && swap == null) {
            selection = swapMenu();
        }else{
            selection = _displayMainMenu();
        }

        return selection;
    } // end method displayMainMenu

    private int swapMenu() {
        screen.displayMessageLine( "\nSwap menu:" );
        screen.displayMessageLine( "8 - Swap to saving account" );
        screen.displayMessageLine( "9 - Swap to chequing account" );
        screen.displayMessageLine( "0 - Exit" );

        screen.displayMessageLine("Please enter your choice: ");
        return keypad.getInput();
    }

    // menu for every account
    private int _displayMainMenu() {
        screen.displayMessageLine( "\nMain menu:" );
        screen.displayMessageLine( "1 - View my balance" );
        screen.displayMessageLine( "2 - Withdraw cash" );
        screen.displayMessageLine( "3 - Transfer funds" );
        screen.displayMessageLine( "0 - Exit\n" );

        screen.displayMessageLine("Please enter your choice: ");
        return keypad.getInput();
    }
         
    // return object of specified Transaction subclass
    private Transaction createTransaction( int input , String ACType)
    {
        Transaction temp = null; // temporary Transaction variable
        
        // determine which type of Transaction to create     
        if (swap != null) {
            switch ( input )
            {
                case BALANCE_INQUIRY: // create new BalanceInquiry transaction
                    temp = new BalanceInquiry( 
                    swap, screen, bankDatabase );
                    break;
                case WITHDRAWAL: // create new Withdrawal transaction
                    temp = new Withdrawal( swap, screen, 
                    bankDatabase, keypad, cashDispenser );
                    break; 
                case TRANSFER: // create new Transfer transaction
                    temp = new Transfer( swap, screen, 
                    bankDatabase, keypad);
                    break;
                case EXIT:
                        break;
            } // end switch
        } else {
            switch ( input )
            {
                case BALANCE_INQUIRY: // create new BalanceInquiry transaction
                    temp = new BalanceInquiry( 
                    currentAccountNumber, screen, bankDatabase );
                    break;
                case WITHDRAWAL: // create new Withdrawal transaction
                    temp = new Withdrawal( currentAccountNumber, screen, 
                    bankDatabase, keypad, cashDispenser );
                    break; 
                case TRANSFER: // create new Transfer transaction
                    temp = new Transfer( currentAccountNumber, screen, 
                    bankDatabase, keypad);
                    break;
                case EXIT:
                        break;
            } // end switch        
        }
        return temp; // return the newly created object
    } // end method createTransaction
} // end class ATM



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