public class Transfer extends Transaction {

    private double amount; // amount to transfer
    private Keypad keypad; // reference to keypad

    // the target account of fund transfer
    private int target;

    private BankDatabase bankDatabase; 
    private Screen screen;

 
    // constant corresponding to menu option to cancel
    // private final static int CANCELED = 6;
 
    // Transfer constructor
    public Transfer( int userAccountNumber, Screen atmScreen, 
    BankDatabase atmBankDatabase, Keypad atmKeypad){

        super(userAccountNumber, atmScreen, atmBankDatabase);

        // initialize references to keypad
        keypad = atmKeypad;

    }//end Transfer constructor

    public boolean isSufficientTransfer(double amount, double available){
        return amount<=available;
    }

 
    // perform transaction
    public void execute()
    {
        // get references to bank database
        bankDatabase = getBankDatabase();

        int currentAccountNumber = getAccountNumber();
        double availableBalance = bankDatabase.getAvailableBalance(currentAccountNumber); // amount available for transfer
 
        // get references to screen
        screen = getScreen();

        screen.displayMessage("Please enter target bank account number: ");
        target = keypad.getInput();

        screen.displayMessage("Please enter amount: ");
        try {
            amount = keypad.getInput();

            if (isSufficientTransfer(amount, availableBalance) && bankDatabase.accountExists(target) && target != currentAccountNumber) {
                bankDatabase.debit(currentAccountNumber, amount);
                bankDatabase.credit(target, amount);
            }else{
                screen.displayMessageLine("Availavle balance is lower than transfer amount or target account unavailable.");
                screen.displayMessageLine("Progress aborted."); 
            }

        } catch (Exception e) {

            // to maintain all inputs are integer, fund with cents are not considered
            screen.displayMessageLine("Input mismatch! No fund will be transfered.");
            amount = 0;
        }

        
    } // end method execute

}
