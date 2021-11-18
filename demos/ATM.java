import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ATM extends JFrame {
    private Screen screenPanel = new Screen();

    private JPanel keyPanel = new JPanel();
    private JButton[] buttonGP = 
    {   new JButton("1"), new JButton("2"), new JButton("3"), new JButton("0"),
        new JButton("4"), new JButton("5"), new JButton("6"), new JButton("Cancel"), 
        new JButton("7"), new JButton("8"), new JButton("9"), new JButton(".")  
    };
    private JPanel rightPanel = new JPanel(null);
    private JPanel enterPanel = new JPanel();
    private JButton EnterButton = new JButton("Enter");

    public String sBuffer = "";
    public int intBuffer = 0 ;
    public double douBuffer = 0;
    private int acIDbuffer = 0;
    private int pwdBuffer = 0;

    private BankDatabase bankDatabase;
    private CashDispenser cashDispenser;
    protected boolean userAuthenticated = false; // whether user is authenticated

    private int currentAccountNumber;

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

    // constants corresponding to main menu options
    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    // private static final int DEPOSIT = 3;
    
    // added new main menu option
    private static final int TRANSFER = 3;

    // for exit
    private static final int EXIT = 0;

    public ATM() {
        bankDatabase = new BankDatabase(); // create acct info database
        cashDispenser = new CashDispenser(); // create new dispenser

        setLayout(new GridLayout(1, 2));

        keyPanel.setLayout(new GridLayout(3, 4));
        keyPanel.setBounds(0,0,400,340);
        for (int i = 0; i < buttonGP.length; i++) {
            keyPanel.add(buttonGP[i]);
            buttonGP[i].addActionListener(new normalbthlr());
        }
        EnterButton.setVisible(true);
        EnterButton.addActionListener(new enterbthlr());
        enterPanel.add(EnterButton);
        enterPanel.setVisible(true);
        enterPanel.setBounds(0,340,400,50);
        rightPanel.add(keyPanel);
        rightPanel.add(enterPanel);

        screenPanel.setVisible(true);
        rightPanel.setVisible(true);
        add(screenPanel);
        add(rightPanel);
    }

    public static void main(String[] args) {
        ATM ATM = new ATM();
        ATM.run();
    }

    private class normalbthlr implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            JButton src = (JButton)e.getSource();
			if (!sBuffer.contains(".")  
				&& src.getActionCommand() != "." && src.getActionCommand() != "Cancel") {
				sBuffer += src.getActionCommand();
				intBuffer = Integer.parseInt(sBuffer);
				//System.out.print(sBuffer);
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
                disableDot(false);
			}
        }
    }

    private class enterbthlr implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!userAuthenticated && acIDbuffer == 0) {
                acIDbuffer = intBuffer;
            }
            if (!userAuthenticated && pwdBuffer == 0 ) {
                pwdBuffer = intBuffer;
            }
            if (!userAuthenticated && acIDbuffer != 0 && pwdBuffer != 0) {
                authenticateUser();
            }
            cancel();
        }
    }

    public void run() {
        setSize(820, 420);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        screenPanel.displayWindowsMessage( "\nWelcome.");
        screenPanel.displayDialogMessage( "\nPlease enter your account number: " );
    }

    public void performTransactions() {

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
                    screenPanel.displayWindowsMessage( "\nExiting the system..." );
                    exitSignal = true; // this ATM session should end
                    return exitSignal;

                    //break;
                default: // user did not enter an integer from 1-4
                    screenPanel.displayWindowsMessage( 
                    "\nYou did not enter a valid selection. Try again." );
                    return exitSignal;
                    //break;
            }   // end switch
        }
        return exitSignal;
    }

    private Transaction createTransaction(int input, String aCtype) {
        Transaction temp = null; // temporary Transaction variable
        
        // determine which type of Transaction to create     
        if (swap != null) {
            switch ( input )
            {
                case BALANCE_INQUIRY: // create new BalanceInquiry transaction
                    temp = new BalanceInquiry( 
                    swap, screenPanel, bankDatabase );
                    break;
                case WITHDRAWAL: // create new Withdrawal transaction
                    temp = new Withdrawal( swap, screenPanel, 
                    bankDatabase, null, cashDispenser );
                    break; 
                case TRANSFER: // create new Transfer transaction
                    temp = new Transfer( swap, screenPanel, 
                    bankDatabase, null);
                    break;
                case EXIT:
                        break;
            } // end switch
        } else {
            switch ( input )
            {
                case BALANCE_INQUIRY: // create new BalanceInquiry transaction
                    temp = new BalanceInquiry( 
                    currentAccountNumber, screenPanel, bankDatabase );
                    break;
                case WITHDRAWAL: // create new Withdrawal transaction
                    temp = new Withdrawal( currentAccountNumber, screenPanel, 
                    bankDatabase, null, cashDispenser );
                    break; 
                case TRANSFER: // create new Transfer transaction
                    temp = new Transfer( currentAccountNumber, screenPanel  , 
                    bankDatabase, null);
                    break;
                case EXIT:
                        break;
            } // end switch        
        }
        return temp; // return the newly created object
    }

    private int displayMainMenu() {
        // String type = bankDatabase.getAccountTypeString(currentAccountNumber);
        String type = (swap instanceof SavingAccount) ? SAVINGTYPE : (swap instanceof ChequeAccount)? CHEQUEINGTYPE: 
            bankDatabase.getAccountTypeString(currentAccountNumber);

        swap = (swap instanceof SavingAccount)? swap = (SavingAccount) swap : (swap instanceof ChequeAccount)?
            swap = (ChequeAccount) swap : swap;
        int selection = 0;

        // showing which type of current account
        screenPanel.displayWindowsMessage("\nYour current account type is: " + type);

        if (bankDatabase.isSwapable(currentAccountNumber) && swap == null) {
            selection = swapMenu();
        }else{
            selection = _displayMainMenu();
        }

        return selection;
    }

    private int _displayMainMenu() {
        screenPanel.displayWindowsMessage( "\nMain menu:" );
        screenPanel.displayWindowsMessage( "\n1 - View my balance" );
        screenPanel.displayWindowsMessage( "\n2 - Withdraw cash" );
        screenPanel.displayWindowsMessage( "\n3 - Transfer funds" );
        screenPanel.displayWindowsMessage( "\n0 - Exit\n" );

        screenPanel.displayWindowsMessage("Please enter your choice: ");
        return intBuffer;
    }

    private int swapMenu() {
        screenPanel.displayWindowsMessage( "\nSwap menu:" );
        screenPanel.displayWindowsMessage( "8 - Swap to saving account" );
        screenPanel.displayWindowsMessage( "9 - Swap to chequing account" );
        screenPanel.displayWindowsMessage( "0 - Exit" );

        screenPanel.displayWindowsMessage("Please enter your choice: ");
        return intBuffer;
    }

    public void authenticateUser() {
        int accountNumber = 0;
        int pin = 0;
        if (accountNumber == 0) {
            accountNumber = acIDbuffer;
        }
        if (accountNumber > 0) {
            screenPanel.displayWindowsMessage( "\nEnter your PIN: \n" ); // prompt for PIN
            pin = pwdBuffer; // set userAuthenticated to boolean value returned by database
        }
        userAuthenticated = 
            bankDatabase.authenticateUser( accountNumber, pin );
        
        // check whether authentication succeeded
        if ( userAuthenticated )
        {
            currentAccountNumber = accountNumber; // save user's account #
            if (bankDatabase.getAccountTypeString(currentAccountNumber)==this.BOTHTYPE) {
                bankDatabase.passBalance(currentAccountNumber);
            }
        } // end if
        else{
            screenPanel.displayWindowsMessage( 
                "Invalid account number or PIN. Please try again." );
        }    
        System.out.println(accountNumber);
        System.out.println(pin);
    }

    public void cancel() {
        sBuffer = "";
		intBuffer = 0;
		douBuffer = 0;
    }

    public void disableDot(boolean b) {
        buttonGP[11].setEnabled(!b);
    }
}
