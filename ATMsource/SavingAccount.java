public class SavingAccount extends Account{

    private double interestRate;

    private static final String TYPE = "Saving account";


    public SavingAccount(int theAccountNumber, int thePIN, 
    double theAvailableBalance, double theTotalBalance){

        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);

        interestRate = 0.001;
    }

    // set method of this class
    public void setInterestRate(double rate ) {
        interestRate = rate;
    }

    // get method of this class
    public double getInterestRate() {
        return interestRate;
    }

    // returns account type
    @Override
    public String getType() {
        return TYPE;
    }

}
