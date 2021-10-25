public class SavingAccount extends Account{

    private double interestRate;

    public SavingAccount(int theAccountNumber, int thePIN, 
    double theAvailableBalance, double theTotalBalance){

        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);

        interestRate = 0.001;
    }

    public void setInterestRate(double rate) {
        interestRate = rate;
    }

    public double getInterestRate() {
        return interestRate;
    }
}
