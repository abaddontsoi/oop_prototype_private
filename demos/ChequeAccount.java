
public class ChequeAccount extends Account {

    private double LimitPerCheque;
    
    private static final String TYPE = "Cheque account";


    public ChequeAccount(int theAccountNumber, int thePIN, 
    double theAvailableBalance, double theTotalBalance) {
        
        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
        LimitPerCheque = 10000;
    }

    // get method of this class
    public void setLimit(double limit) {
        LimitPerCheque = limit;
    }

    // returns current cheque limit
    public double getLimit(){
        return LimitPerCheque;
    }

    // returns account type
    @Override
    public String getType() {
        return TYPE;
    }

}
