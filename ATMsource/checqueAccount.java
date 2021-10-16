public class checqueAccount extends Account {

    private double LimitPerCheque;

    public checqueAccount(int theAccountNumber, int thePIN, 
    double theAvailableBalance, double theTotalBalance) {
        
        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
        LimitPerCheque = 10000;
    }

    public void setLimit(double limit) {
        LimitPerCheque = limit;
    }

    public double getLimit(){
        return LimitPerCheque;
    }
}