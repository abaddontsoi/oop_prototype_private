public class Transfer extends Transaction {

    private double amount; // amount to transfer

    // the target account of fund transfer
    private int target;

    private BankDatabase bankDatabase; 
    private screenWithButtons screen;

 
    // constant corresponding to menu option to cancel
    // private final static int CANCELED = 6;
 
    // Transfer constructor
    public Transfer( int userAccountNumber, screenWithButtons atmScreen, 
    BankDatabase atmBankDatabase, Double amount, int target){

        super(userAccountNumber, atmScreen, atmBankDatabase);
        this.amount = amount;
        this.target = target;
        this.screen = atmScreen;
    }
    
    public Transfer( Account userAccount, screenWithButtons atmScreen, 
    BankDatabase atmBankDatabase, double amount, int target )
    {
        super( userAccount, atmScreen, atmBankDatabase );
        this.amount = amount;
        this.target = target;
        this.screen = atmScreen;
    }
 

    public boolean isSufficientTransfer(double amount, double available){
        return amount<=available;
    }

    // transfer method for non-compounded account
    private void transferNormal(double availableBalance, int accountNumber, int target) {
        screen.jTextArea1.setText("\nPlease enter target bank account number: ");

        if (isSufficientTransfer(amount, availableBalance) && bankDatabase.accountExists(target) 
        && target != accountNumber && amount > 0 )
        {
            bankDatabase.debit(accountNumber, amount);
            bankDatabase.credit(target, amount);

            screen.jTextArea1.setText("Successful");
        }else{
            screen.jTextArea1.setText(
                "\nAvailavle balance is lower than transfer amount \nor target account unavailable."+
                "\nProgress aborted."
            ); 
        }
        System.out.println(availableBalance);
        System.out.println(accountNumber);
        System.out.println(target);
    }
 
    private void transferCompound(double availableBalance, Account subAccount, int target){
        int acNum = subAccount.getAccountNumber();

        if (isSufficientTransfer(amount, availableBalance) && bankDatabase.accountExists(target) 
            && target != acNum && amount > 0) {

            bankDatabase.debit(subAccount, amount);
            bankDatabase.credit(target, amount);

            screen.jTextArea1.setText("Successful");
        }else{
            screen.jTextArea1.setText(
                "\nAvailavle balance is lower than transfer amount \nor target account unavailable."+
                "\nProgress aborted."
            );     
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
 

        if(flag){
            transferCompound(availableBalance, subAccount, target);
        }else{
            transferNormal(availableBalance, currentAccountNumber,target);
        }

        
    } // end method execute

}
