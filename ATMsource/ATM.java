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

    // fourth option of different account types are,
    // saving account: set compund frequency
    // chequing account: set cheque limit
    // general accout: exit
    private static final int FORTH_OPTION = 4;

    // exit option for cheque and saving account
    private static final int EXIT = 5;

    // used as switch option
    private final String GENERALTYPE = "General account";
    private final String SAVINGTYPE = "Saving account";
    private final String CHEQUEINGTYPE = "Cheque account";


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
        } // end if
        else
            screen.displayMessageLine( 
                "Invalid account number or PIN. Please try again." );
    } // end method authenticateUser

    private boolean transactions(int mainMenuSelection, String ACtype) {

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
                case FORTH_OPTION:

                    // initialize as new object of chosen type
                    currentTransaction = 
                        createTransaction( mainMenuSelection, ACtype );

                    if (currentTransaction != null) {
                        currentTransaction.execute(); // execute transaction when is not exit signal of general account    
                    }
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

    // display the main menu and perform transactions    
    private void performTransactions(){

        // Transaction currentTransaction = null;
      
        boolean userExited = false; // user has not chosen to exit

        //int mainMenuSelection = displayMainMenu();

        String type = bankDatabase.getAccountTypeString(currentAccountNumber);

        while (!userExited) {
            int mainMenuSelection = displayMainMenu();

            userExited = transactions(mainMenuSelection, type);
        }
    }
   
    // display the main menu and return an input selection
    private int displayMainMenu()
    {
        String type = bankDatabase.getAccountTypeString(currentAccountNumber);

        int selection = 0;

        // showing which type of current account
        screen.displayMessageLine("\nYour account type is: " + type);

        switch (type) {
            case SAVINGTYPE:
                displayMainMenuSaving();
                selection = keypad.getInput();// return user's selection
                break;
            case CHEQUEINGTYPE:
                displayMainMenuCheque();
                selection = keypad.getInput();// return user's selection
                break;
            case GENERALTYPE:
                displayMainMenuGeneral();
                selection = keypad.getInput();// return user's selection
                break;
        }
        
        return selection;
    } // end method displayMainMenu

    // menu for general account
    private void displayMainMenuGeneral() {
        screen.displayMessageLine( "\nMain menu:" );
        screen.displayMessageLine( "1 - View my balance" );
        screen.displayMessageLine( "2 - Withdraw cash" );
        screen.displayMessageLine( "3 - Transfer funds" );
        screen.displayMessageLine( "4 - Exit\n" );

        screen.displayMessageLine("Please enter your choice: ");
    }

    // menu for saving account
    private void displayMainMenuSaving() {
        screen.displayMessageLine( "\nMain menu:" );
        screen.displayMessageLine( "1 - View my balance" );
        screen.displayMessageLine( "2 - Withdraw cash" );
        screen.displayMessageLine( "3 - Transfer funds" );
        screen.displayMessageLine( "4 - Set Compound Frequency" );
        screen.displayMessageLine( "5 - Exit\n" );
        screen.displayMessageLine("Please enter your choice: ");

    }

    // menu for chequeing account
    private void displayMainMenuCheque() {
        screen.displayMessageLine( "\nMain menu:" );
        screen.displayMessageLine( "1 - View my balance" );
        screen.displayMessageLine( "2 - Withdraw cash" );
        screen.displayMessageLine( "3 - Transfer funds" );
        screen.displayMessageLine( "4 - Set cheque limit" );
        screen.displayMessageLine( "5 - Exit\n" );

        screen.displayMessageLine("Please enter your choice: ");
    }
         
   // return object of specified Transaction subclass
    private Transaction createTransaction( int type , String ACType)
    {
        Transaction temp = null; // temporary Transaction variable
        
        // determine which type of Transaction to create     
        switch ( type )
        {
            case BALANCE_INQUIRY: // create new BalanceInquiry transaction
                temp = new BalanceInquiry( 
                currentAccountNumber, screen, bankDatabase );
                break;
            case WITHDRAWAL: // create new Withdrawal transaction
                temp = new Withdrawal( currentAccountNumber, screen, 
                bankDatabase, keypad, cashDispenser );
                break; 
            case TRANSFER: // create new Deposit transaction
                temp = new Transfer( currentAccountNumber, screen, 
                bankDatabase, keypad);
                break;
            case FORTH_OPTION:
                if (ACType == SAVINGTYPE) {
                    temp = new SetComFre(currentAccountNumber, screen, bankDatabase, keypad);
                }
                if (ACType == CHEQUEINGTYPE) {
                    temp = new SetLimit(currentAccountNumber, screen, bankDatabase, keypad);
                }else{
                    break;
                }
        } // end switch

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