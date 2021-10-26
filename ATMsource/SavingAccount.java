public class SavingAccount extends Account{

    private double interestRate;

    private static final String TYPE = "Saving account";
    private int FREQUENCY;


    public SavingAccount(int theAccountNumber, int thePIN, 
    double theAvailableBalance, double theTotalBalance){

        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);

        interestRate = 0.001;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getFre() {
        return FREQUENCY;
    }

    // returns account type
    @Override
    public String getType() {
        return TYPE;
    }

    public void setFrequency(int freSelection) {
            FREQUENCY = freSelection;
    }
}
