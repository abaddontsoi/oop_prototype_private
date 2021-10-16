public class savingAccount extends Account{

    protected double interestRate;

    public savingAccount(int theAccountNumber, int thePIN, 
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
