public class SetComFre extends Transaction {

    private Keypad keypad; // reference to keypad

    private BankDatabase bankDatabase; 
    private Screen screen;

    private static final int CANCEL = 0;

    private static final int ANNUALLY = 1;
    private static final int HALF = 2;
    private static final int SEASONAL = 3;
    private static final int DAILY = 4;

    public SetComFre(int userAccountNumber, Screen atmScreen, 
    BankDatabase atmBankDatabase, Keypad atmKeypad){
        super(userAccountNumber, atmScreen, atmBankDatabase);

        keypad = atmKeypad;
    }

    private int frequencySelection() {
        screen.displayMessageLine("Please select new compound frequency: ");
        screen.displayMessageLine("0 - Cancel ");
        screen.displayMessageLine("1 - Annually ");
        screen.displayMessageLine("2 - Half-a-year ");
        screen.displayMessageLine("3 - Seasonal");
        screen.displayMessageLine("4 - Daily");

        return keypad.getInput();

    }
    
    private void setMessage( double nextYearBalance){
        screen.displayMessageLine(
            "Compound frequency is set, below is predicted balance in next year: " );
        System.out.printf("$%.2f", nextYearBalance);
        //System.out.println("$" + nextYearBalance);
    }

    @Override
    public void execute() {
        bankDatabase = getBankDatabase();

        int currentAccountNumber = getAccountNumber();

        // get references to screen
        screen = getScreen();

        int setFrequency = frequencySelection();

        double currentBalance = bankDatabase.getTotalBalance(currentAccountNumber);
        double nextYearBalance;
        double exponent;
        double currentRate = bankDatabase.getCurrentRate(currentAccountNumber);

        switch (setFrequency) {
            case ANNUALLY:
            case HALF:

                bankDatabase.setFrequency(currentAccountNumber, setFrequency);              
                exponent = 1 + currentRate/setFrequency ;
                nextYearBalance = currentBalance * Math.pow(exponent, setFrequency);
                setMessage(nextYearBalance);
                break;

            case SEASONAL:

                bankDatabase.setFrequency(currentAccountNumber, setFrequency);
                exponent = 1 + currentRate/4;
                nextYearBalance = currentBalance * Math.pow(exponent, 4);
                setMessage(nextYearBalance);                
                break;

            case DAILY:
                bankDatabase.setFrequency(currentAccountNumber, setFrequency);  
                exponent = 1 + currentRate/365;
                nextYearBalance = currentBalance * Math.pow(exponent, 365);
                setMessage(nextYearBalance);
                break;
            case CANCEL:
                screen.displayMessageLine("Canceled.");
                break;
            default:
                screen.displayMessageLine("No such option.");
                break;
        }
    }

}
