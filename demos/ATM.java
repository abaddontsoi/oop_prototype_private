/**
 *
 * @author abaddon
 */

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ATM extends JFrame {
    // Variables declaration
    private JButton rightButtonGP[] =
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

    // for swaping
    private Account swap = null;
    private static final int SWAPTOSAVING = 8;
    private static final int SWAPTOCHEQUING = 9;
    private static final int NOSWAPPING = 0;

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

        screenWithButtons1.setBounds(0, 0, 702, 356);
        getContentPane().add(loginAndOut2);
        loginAndOut2.setBounds(10, 365, 692, 94);

        rightButtonGP[0].setText("1");
        getContentPane().add(rightButtonGP[0]);
        rightButtonGP[0].setBounds(708, 135, 121, 73);

        rightButtonGP[1].setText("2");
        getContentPane().add(rightButtonGP[1]);
        rightButtonGP[1].setBounds(835, 135, 126, 73);

        rightButtonGP[2].setText("3");
        getContentPane().add(rightButtonGP[2]);
        rightButtonGP[2].setBounds(967, 135, 121, 73);

        rightButtonGP[3].setText("4");
        getContentPane().add(rightButtonGP[3]);
        rightButtonGP[3].setBounds(708, 213, 121, 75);

        rightButtonGP[4].setText("5");
        getContentPane().add(rightButtonGP[4]);
        rightButtonGP[4].setBounds(835, 213, 126, 75);

        rightButtonGP[5].setText("6");
        getContentPane().add( rightButtonGP[5]);
        rightButtonGP[5].setBounds(967, 213, 121, 75);

        rightButtonGP[6].setText("7");
        getContentPane().add(rightButtonGP[6] );
        rightButtonGP[6].setBounds(708, 293, 121, 73);

        rightButtonGP[7].setText("8");
        getContentPane().add(rightButtonGP[7]);
        rightButtonGP[7].setBounds(835, 293, 126, 73);

        rightButtonGP[8].setText("9");
        getContentPane().add(rightButtonGP[8]);
        rightButtonGP[8].setBounds(967, 293, 121, 73);

        rightButtonGP[9].setText("0");
        getContentPane().add(rightButtonGP[9]);
        rightButtonGP[9].setBounds(835, 371, 126, 79);

        rightButtonGP[10].setText(".");
        getContentPane().add(rightButtonGP[10]);
        rightButtonGP[10].setBounds(708, 371, 121, 79);

        rightButtonGP[11].setText("00");
        getContentPane().add(rightButtonGP[11]);
        rightButtonGP[11].setBounds(967, 371, 121, 79);

        rightButtonGP[12].setText("OK");
        getContentPane().add(rightButtonGP[12]);
        rightButtonGP[12].setBounds(1094, 135, 95, 153);

        rightButtonGP[13].setText("Reset");
        getContentPane().add(rightButtonGP[13]);
        rightButtonGP[13].setBounds(1094, 293, 95, 157);

        getContentPane().add(inputField);
        inputField.setBounds(710, 10, 480, 110);
        
        getContentPane().add(acID);
        acID.setBounds(710, 11, 480, 80);

        getContentPane().add(pwd);
        pwd.setBounds(710, 90, 480, 30);


        loginAndOut2.jButton1.addActionListener(new loginHLR());
        loginAndOut2.jButton2.addActionListener(new logoutHLR());

        pack();
    }
    public static void main(String args[]) {
        ATM atm = new ATM();
        atm.init(true);
        atm.setVisible(true);
        atm.setSize(1250, 500);
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
            for (int i = 0; i < rightButtonGP.length; i++) {
                rightButtonGP[i].setEnabled(!b);
            }
        }else{
            screenWithButtons1.turnOn(b);
            loginAndOut2.jButton1.setEnabled(!b);
            loginAndOut2.jButton2.setEnabled(b);
            inputField.setEnabled(!b);
            inputField.setVisible(b);
            pwd.setVisible(!b);
            acID.setVisible(!b);
            for (int i = 0; i < rightButtonGP.length; i++) {
                rightButtonGP[i].setEnabled(b);
            }
            
        }
    }

    private void clean() {
        acID.setText("");
        pwd.setText("");
        inputField.setText("");
    }

 private class loginHLR implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            passToBE();
            authenticateUser();
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
        accountNumber = Integer.valueOf(acID.getText());
        char[] tempPIN = pwd.getPassword();
        pin = Integer.valueOf(String.valueOf(tempPIN));
        System.out.println(accountNumber);
        System.out.println(pin);
    }

    public void logout() {
        accountNumber = 0;
        pin = 0;
        userAuthenticated = false;
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
        } // end if
        else{
            System.out.println("failed" + accountNumber);
            System.out.println("failed" + pin);
        }
    } // end method authenticateUser
}
