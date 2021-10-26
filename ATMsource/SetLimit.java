public class SetLimit extends Transaction {

    private Keypad keypad; // reference to keypad

    private BankDatabase bankDatabase; 
    private Screen screen;

    public SetLimit(int userAccountNumber, Screen atmScreen, 
    BankDatabase atmBankDatabase, Keypad atmKeypad){
        super(userAccountNumber, atmScreen, atmBankDatabase);

        keypad = atmKeypad;
    }

    @Override
    public void execute() {
        bankDatabase = getBankDatabase();

        int currentAccountNumber = getAccountNumber();

        // get references to screen
        screen = getScreen();

        screen.displayMessage("Please enter limit amount: ");
        int amount = keypad.getInput();

        if (amount <= 0) {
            screen.displayMessageLine("Amount entered is out of range.");
        } else {
            bankDatabase.setChequeLimit(currentAccountNumber, amount);
        }

    }
}
