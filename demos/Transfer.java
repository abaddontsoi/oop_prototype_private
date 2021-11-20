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
    public Transfer( int userAccountNumber, screenWithButtons atmScreen, 
    BankDatabase atmBankDatabase, Keypad atmKeypad){

        super(userAccountNumber, atmScreen, atmBankDatabase);

        // initialize references to keypad
        keypad = atmKeypad;
    }
    //end Transfer constructor
    public Transfer( Account userAccount, screenWithButtons atmScreen, 
    BankDatabase atmBankDatabase, Keypad atmKeypad )
    {
        super( userAccount, atmScreen, atmBankDatabase );

        keypad = atmKeypad;
    }
 

    public boolean isSufficientTransfer(double amount, double available){
        return amount<=available;
    }

    // transfer method for non-compounded account
    private void transferNormal(double availableBalance, int accountNumber, Screen screen,Keypad keypad) {
        //this.keypad = keypad; 
        
        screen.displayMessage("Please enter target bank account number: ");
        target = keypad.getInput();

        screen.displayMessage("Please enter amount: ");
        try {
            amount = keypad.getDoubleInput();

            if (isSufficientTransfer(amount, availableBalance) && bankDatabase.accountExists(target) 
            && target != accountNumber && amount > 0 ) {
                bankDatabase.debit(accountNumber, amount);
                bankDatabase.credit(target, amount);

                screen.displayWindowsMessage("Successful.");
            }else{
                screen.displayWindowsMessage("Availavle balance is lower than transfer amount or target account unavailable.");
                screen.displayWindowsMessage("Progress aborted."); 
            }

        } catch (Exception e) {

            // to maintain all inputs are integer, fund with cents are not considered
            screen.displayWindowsMessage("Input mismatch! In normal mode.");
            amount = 0;
        }
    }
 
    private void transferCompound(double availableBalance, Account subAccount, Screen screen, Keypad keypad){
        int acNum = subAccount.getAccountNumber();

        // this.keypad = keypad; 
        screen.displayMessage("Please enter target bank account number: ");
        target = keypad.getInput();

        screen.displayMessage("Please enter amount: ");
        try {
            amount = keypad.getDoubleInput();

            if (isSufficientTransfer(amount, availableBalance) && bankDatabase.accountExists(target) 
                && target != acNum && amount > 0) {

                bankDatabase.debit(subAccount, amount);
                bankDatabase.credit(target, amount);

                screen.displayWindowsMessage("Successful.");
            }else{
                screen.displayWindowsMessage("Availavle balance is lower than transfer amount or target account unavailable.");
                screen.displayWindowsMessage("Progress aborted."); 
            }

        } catch (Exception e) {

            screen.displayWindowsMessage("Input mismatch! In compoud mode.");
            amount = 0;
        }

    }
    // perform transaction
    public void execute()
    {
        // get references to bank database
        bankDatabase = getBankDatabase();

        boolean flag = super.getFlag();
        Account subAccount = super.getAccount();

        int currentAccountNumber = getAccountNumber();
        double availableBalance = (subAccount != null)? bankDatabase.getAvailableBalance( getAccount()):
            bankDatabase.getAvailableBalance( getAccountNumber() );
 

        //transferCompound(availableBalance, subAccount, screen, keypad);

        if(flag){
            transferCompound(availableBalance, subAccount, screen, keypad);
        }else{
            transferNormal(availableBalance, currentAccountNumber, screen, keypad);
        }

        
    } // end method execute

}
