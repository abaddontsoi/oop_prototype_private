/**
 *
 * @author abaddon
 */

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ATM extends JFrame {
    // Variables declaration
    private JButton inputButtonGP[] =
    {
        new JButton(),
        new JButton(),
        new JButton(),
        new JButton(),
        new JButton(),
        new JButton(),
        new JButton(),
        new JButton(),
        new JButton(),
        new JButton(),
        new JButton(),
        new JButton(),
        new JButton(),
        new JButton(),
    };
    private JTextField inputField;
    private LoginAndOut loginAndOut2;
    private screenWithButtons screenWithButtons1;

    private boolean userAuthenticated =false; // whether user is authenticated
    private int currentAccountNumber; // current user's account number
    private CashDispenser cashDispenser; // ATM's cash dispenser
    //private DepositSlot depositSlot; // ATM's deposit slot
    private BankDatabase bankDatabase = new BankDatabase(); // account information database

    // constants corresponding to main menu options
    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    // private static final int DEPOSIT = 3;
    
    // added new main menu option
    private static final int TRANSFER = 3;

    // for exit
    private static final int EXIT = 0;

    // action listeners 
    private CB cb = new CB(); // check balance
    private WD wd = new WD(); // withdraw
    private TS ts = new TS(); // transfer
    private SWS sws = new SWS(); // swap to saving (for compound account)
    private SWC swc = new SWC(); // swap to chequing (for compound account)


    // for swaping
    private Account swap = null;
    private static final int SWAPTOSAVING = 8;
    private static final int SWAPTOCHEQUING = 9;
    private static final int NOSWAPPING = 0;
    private String type;

    // used as switch option
    // private final String GENERALTYPE = "General account";
    private final String SAVINGTYPE = "Saving account";
    private final String CHEQUEINGTYPE = "Cheque account";
    private final String BOTHTYPE = "Both";
    private int accountNumber =0;
    private int pin=0;

    private JTextField acID = new JTextField();
    private JPasswordField pwd = new JPasswordField();

    public ATM() {
        initComponents();
    }

    private void initComponents() {

        screenWithButtons1 = new screenWithButtons();
        loginAndOut2 = new LoginAndOut();
        inputField = new JTextField();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ATM");
        getContentPane().setLayout(null);
        getContentPane().add(screenWithButtons1);

        screenWithButtons1.setBounds(0, 0, 800, 356);
        getContentPane().add(loginAndOut2);
        loginAndOut2.setBounds(10, 365, 692, 94);

        inputButtonGP[0].setText("1");
        getContentPane().add(inputButtonGP[0]);
        inputButtonGP[0].setBounds(810, 130, 121, 73);

        inputButtonGP[1].setText("2");
        getContentPane().add(inputButtonGP[1]);
        inputButtonGP[1].setBounds(940, 130, 126, 73);

        inputButtonGP[2].setText("3");
        getContentPane().add(inputButtonGP[2]);
        inputButtonGP[2].setBounds(1070, 130, 121, 73);

        inputButtonGP[3].setText("4");
        getContentPane().add(inputButtonGP[3]);
        inputButtonGP[3].setBounds(810, 210, 121, 75);

        inputButtonGP[4].setText("5");
        getContentPane().add(inputButtonGP[4]);
        inputButtonGP[4].setBounds(940, 210, 126, 75);

        inputButtonGP[5].setText("6");
        getContentPane().add( inputButtonGP[5]);
        inputButtonGP[5].setBounds(1070, 210, 121, 73);

        inputButtonGP[6].setText("7");
        getContentPane().add(inputButtonGP[6] );
        inputButtonGP[6].setBounds(810, 290, 121, 75);

        inputButtonGP[7].setText("8");
        getContentPane().add(inputButtonGP[7]);
        inputButtonGP[7].setBounds(940, 290, 126, 73);

        inputButtonGP[8].setText("9");
        getContentPane().add(inputButtonGP[8]);
        inputButtonGP[8].setBounds(1070, 290, 121, 73);

        inputButtonGP[9].setText("0");
        getContentPane().add(inputButtonGP[9]);
        inputButtonGP[9].setBounds(940, 370, 126, 79);

        inputButtonGP[10].setText(".");
        getContentPane().add(inputButtonGP[10]);
        inputButtonGP[10].setBounds(810, 370, 121, 79);

        inputButtonGP[11].setText("00");
        getContentPane().add(inputButtonGP[11]);
        inputButtonGP[11].setBounds(1070, 370, 121, 79);

        inputButtonGP[12].setText("OK");
        getContentPane().add(inputButtonGP[12]);
        inputButtonGP[12].setBounds(1200, 130, 95, 153);

        inputButtonGP[13].setText("Reset");
        getContentPane().add(inputButtonGP[13]);
        inputButtonGP[13].setBounds(1200, 290, 95, 157);

        getContentPane().add(inputField);
        inputField.setBounds(810, 10, 480, 110);
        
        getContentPane().add(acID);
        acID.setBounds(810, 10, 480, 80);

        getContentPane().add(pwd);
        pwd.setBounds(810, 90, 480, 30);


        loginAndOut2.jButton1.addActionListener(new loginHLR());
        loginAndOut2.jButton2.addActionListener(new logoutHLR());

        pack();
    }
    public static void main(String args[]) {
        ATM atm = new ATM();
        atm.init(true);
        atm.setVisible(true);
        atm.setSize(1400, 500);
    }

    private void init(boolean b) {
        clean();
        if (!userAuthenticated) {
            screenWithButtons1.turnOn(!b);
            loginAndOut2.jButton1.setEnabled(b);
            loginAndOut2.jButton2.setEnabled(!b);
            inputField.setEnabled(!b);
            inputField.setVisible(!b);
            pwd.setVisible(b);
            acID.setVisible(b);
            for (int i = 0; i < inputButtonGP.length; i++) {
                inputButtonGP[i].setEnabled(!b);
            }
            screenWithButtons1.displayWelcome();
        }else{
            screenWithButtons1.turnOn(b);
            loginAndOut2.jButton1.setEnabled(!b);
            loginAndOut2.jButton2.setEnabled(b);
            inputField.setEnabled(!b);
            inputField.setVisible(b);
            pwd.setVisible(!b);
            acID.setVisible(!b);
            for (int i = 0; i < inputButtonGP.length; i++) {
                inputButtonGP[i].setEnabled(b);
            }
        }
    }

    private void clean() {
        acID.setText("");
        pwd.setText("");
        inputField.setText("");

        // should be removed if there is ant logic bug
        screenWithButtons1.jTextArea1.setText("");
    }

    private class loginHLR implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            passToBE();
        }
    }
    
    private class logoutHLR implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            logout();
        }
    }

    private void passToBE() {
        if (validateData(pwd.getPassword(), acID.getText())) {
            accountNumber = Integer.valueOf(acID.getText());
            char[] tempPIN = pwd.getPassword();
            pin = Integer.valueOf(String.valueOf(tempPIN));
            System.out.println(accountNumber);
            System.out.println(pin);
            authenticateUser();
        }else{
            clean();
            this.init(true);
        }
    }

    private boolean validateData(char pwdTemp[], String IDtemp) {
        for (char c : pwdTemp) {
            if (c == '.'|| pwdTemp.length == 0) {
                System.out.print("Bad input");
                return false;
            }
        }
        if (IDtemp.contains(".") || IDtemp.length()==0) {
            System.out.print("Bad input");
            return false;
        }
        return true;
    }

    public void logout() {
        accountNumber = 0;
        pin = 0;
        userAuthenticated = false;
        swap = null;
        this.init(true);
    }

    private void authenticateUser() 
    {
        // bankDatabase
        userAuthenticated = 
            bankDatabase.authenticateUser( accountNumber, pin );
        
        // check whether authentication succeeded
        if ( userAuthenticated )
        {
            currentAccountNumber = accountNumber; // save user's account #
            if (bankDatabase.getAccountTypeString(currentAccountNumber)==BOTHTYPE) {
                bankDatabase.passBalance(currentAccountNumber);
            }
            this.init(true);
            turnOnScreenBT();
        } // end if
        else{
            System.out.println("failed" + accountNumber);
            System.out.println("failed" + pin);
            clean();
        }
    } // end method authenticateUser

    private void turnOnScreenBT() {
        type = (swap instanceof SavingAccount) ? SAVINGTYPE : (swap instanceof ChequeAccount)? CHEQUEINGTYPE: 
        bankDatabase.getAccountTypeString(currentAccountNumber);

        // place this to button hlr (maybe)
        swap = (swap instanceof SavingAccount)? swap = (SavingAccount) swap : (swap instanceof ChequeAccount)?
            swap = (ChequeAccount) swap : swap;

        switch (type) {
            case SAVINGTYPE:
            case CHEQUEINGTYPE:
                screenWithButtons1.Bt01.setText("Check Balance");
                screenWithButtons1.Bt01.removeActionListener(cb);
                screenWithButtons1.Bt01.addActionListener(cb);

                screenWithButtons1.Bt11.setText("Withdraw");
                screenWithButtons1.Bt11.removeActionListener(wd);
                screenWithButtons1.Bt11.addActionListener(wd);

                screenWithButtons1.Bt21.setText("Transfer");
                screenWithButtons1.Bt21.removeActionListener(ts);
                screenWithButtons1.Bt21.addActionListener(ts);
                break;
            case BOTHTYPE:
                screenWithButtons1.Bt01.setText("Swap saving");
                screenWithButtons1.Bt01.removeActionListener(sws);
                screenWithButtons1.Bt01.addActionListener(sws);

                screenWithButtons1.Bt11.setText("Swap chequing");
                screenWithButtons1.Bt11.removeActionListener(swc);
                screenWithButtons1.Bt11.addActionListener(swc);
                break;
        }


        // showing which type of current account
        // screen.displayWindowsMessage("\nYour current account type is: " + type);
    }
    private class CB implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            Transaction temp = null;
            if (swap != null) {
                temp = new BalanceInquiry( 
                        swap,screenWithButtons1, bankDatabase );
                System.out.print("has swap.\n");
            }else{
                temp = new BalanceInquiry( 
                        currentAccountNumber,screenWithButtons1, bankDatabase );
                System.out.print("no swap.\n");
            }
        }
    }
    private class WD implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
        }        
    }
    private class TS implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
        }        
    }
    private class SWS implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            swap = bankDatabase.swapToSaving(currentAccountNumber);
            turnOnScreenBT();
        }
    }
    private class SWC implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            swap = bankDatabase.swapToChequing(currentAccountNumber);
            turnOnScreenBT();
        }
    }

}
