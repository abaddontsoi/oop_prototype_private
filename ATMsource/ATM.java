import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
// ATM.java
// Represents an automated teller machine

public class ATM extends JFrame 
{
    private boolean userAuthenticated; // whether user is authenticated
    private int currentAccountNumber; // current user's account number
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
    // private final String GENERALTYPE = "General account";
    private final String SAVINGTYPE = "Saving account";
    private final String CHEQUEINGTYPE = "Cheque account";
    private final String BOTHTYPE = "Both";

    //components of ATM
    //private JFrame ATMFrame = new JFrame();
    private Keypad keypad = new Keypad(); // ATM's keypad
    private Screen screen = new Screen(true); // ATM's screen
    private JPanel keypadPanel = new JPanel();
    private JPanel screenPanel = new JPanel();
    private int accountNumber = 0;
    private int pin = 0;
    private JButton[] buttonGP = 
	{
		new JButton("1"),
		new JButton("2"),
		new JButton("3"),
		new JButton("Enter"),
		new JButton("4"),
		new JButton("5"),
		new JButton("6"),
		new JButton("Cancel"),
		new JButton("7"),
		new JButton("8"),
		new JButton("9"),
		new JButton("."),
		new JButton("0")
	};
    public int intBuffer;
	public double douBuffer;
	public String sBuffer = "";
	public boolean confirmSignal = false; 



   // no-argument ATM constructor initializes instance variables
    public ATM() 
    {
        ButtonHlr bthlr = new ButtonHlr(); 
        EnterHlr ethlr = new EnterHlr(); 
        keypadPanel.setLayout(new GridLayout(4,3));
		for (int i = 0; i < buttonGP.length; i++) {
			keypadPanel.add(buttonGP[i]);
			if (i != 3) {
                buttonGP[i].addActionListener(bthlr);
			}else{
                buttonGP[i].addActionListener(ethlr);
			}
		}
        screenPanel.setLayout(new GridLayout(1,1));
        screenPanel.setSize(400, 400);
        screenPanel.add(new JTextArea());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,800);
        setLayout(new GridLayout(1,2));
        //add(screen);
        //add(keypad);
        add(screenPanel);
        add(keypadPanel);

        userAuthenticated = false; // user is not authenticated to start
        currentAccountNumber = 0; // no current account number to startcashDispenser = new CashDispenser(); // create cash dispenser
        //depositSlot = new DepositSlot(); // create deposit slot
        bankDatabase = new BankDatabase(); // create acct info database
        cashDispenser = new CashDispenser(); // create new dispenser
        setVisible(true);
    } // end no-argument ATM constructor

    private class ButtonHlr implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            JButton src = (JButton)e.getSource();
			if (!sBuffer.contains(".")  
				&& src.getActionCommand() != "." && src.getActionCommand() != "Cancel") {
				sBuffer += src.getActionCommand();
				intBuffer = Integer.parseInt(sBuffer);
				// System.out.print(sBuffer);
			}
			if ((src.getActionCommand() == "." || sBuffer.contains("."))
				&& src.getActionCommand() != "Cancel" ) {
				disableDot(true);
				sBuffer += src.getActionCommand();
				douBuffer = Double.parseDouble(sBuffer);
				// System.out.print(sBuffer);
			}
			if (src.getActionCommand() == "Cancel") {
				cancel();
			}

        }
    }
    private class EnterHlr implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            confirmSignal = true;
			cancel();
        }
    }

    public void cancel() {
		sBuffer = "";
		intBuffer = 0;
		douBuffer = 0;
		// disableDot(false);
	}
	public void disableDot(boolean b) {
		buttonGP[11].setEnabled(!b);
	}

    // start ATM 
    public void run()
    {
        keypad.showKeypad();
        // welcome and authenticate user; perform transactions
        while ( true )
        {
            // loop while user is not yet authenticated
            while ( !userAuthenticated ) 
            {
                screen.displayDialogMessage("Please login.");
                keypad.disableDot(true);
                authenticateUser(); // authenticate user
            } // end while

            performTransactions(); 
            userAuthenticated = false; // reset before next ATM session
            currentAccountNumber = 0; // reset before next ATM session 
            screen.displayDialogMessage( "\nThank you! Goodbye!" );
        } // end while   
    } // end method run

    // attempts to authenticate user against database
    private void authenticateUser() 
    {
        screen.displayDialogMessage( "\nPlease enter your account number: " );
        accountNumber = keypad.getInput();
        screen.displayDialogMessage( "\nEnter your PIN: " ); // prompt for PIN
        pin = keypad.getInput(); // set userAuthenticated to boolean value returned by database

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
            screen.displayDialogMessage( 
                "Invalid account number or PIN. Please try again." );
            System.out.println(accountNumber);
            System.out.println(pin);
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
                    screen.displayDialogMessage( "\nExiting the system..." );
                    exitSignal = true; // this ATM session should end
                    return exitSignal;

                    //break;
                default: // user did not enter an integer from 1-4
                    screen.displayDialogMessage( 
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
        screen.displayDialogMessage("\nYour current account type is: " + type);

        if (bankDatabase.isSwapable(currentAccountNumber) && swap == null) {
            selection = swapMenu();
        }else{
            selection = _displayMainMenu();
        }

        return selection;
    } // end method displayMainMenu

    private int swapMenu() {
        screen.displayDialogMessage( "\nSwap menu:" );
        screen.displayDialogMessage( "8 - Swap to saving account" );
        screen.displayDialogMessage( "9 - Swap to chequing account" );
        screen.displayDialogMessage( "0 - Exit" );

        screen.displayDialogMessage("Please enter your choice: ");
        return keypad.getInput();
    }

    // menu for every account
    private int _displayMainMenu() {
        screen.displayDialogMessage( "\nMain menu:" );
        screen.displayDialogMessage( "\n1 - View my balance" );
        screen.displayDialogMessage( "\n2 - Withdraw cash" );
        screen.displayDialogMessage( "\n3 - Transfer funds" );
        screen.displayDialogMessage( "\n0 - Exit\n" );

        screen.displayDialogMessage("Please enter your choice: ");
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