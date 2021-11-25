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

    private boolean userAuthenticated = false; // whether user is authenticated
    private int currentAccountNumber; // current user's account number
    private CashDispenser cashDispenser = new CashDispenser(); // ATM's cash dispenser
    private BankDatabase bankDatabase = new BankDatabase(); // account information database

    // action listeners 
    private bt01hlr _01hlr = new bt01hlr();
    private bt11hlr _11hlr = new bt11hlr();
    private bt21hlr _21hlr = new bt21hlr();
    private bt00hlr _00hlr = new bt00hlr();
    private bt10hlr _10hlr = new bt10hlr();
    private bt20hlr _20hlr = new bt20hlr();
    private bt30hlr _30hlr = new bt30hlr();
    private normalKeypadNumericLiseter KPhlr = new normalKeypadNumericLiseter();
    private normalKeypadOKListener OKhlr = new normalKeypadOKListener();
    private normalKeypadResetListener Resethlr = new normalKeypadResetListener();

    // for swaping
    private Account swap = null;
    private String type;

    // used as switch option
    // private final String GENERALTYPE = "General account";
    private final String SAVINGTYPE = "Saving account";
    private final String CHEQUEINGTYPE = "Cheque account";
    private final String BOTHTYPE = "Both";
    private final String GENERALTYPE = "General account";
    private int accountNumber =0;
    private int pin=0;

    private JTextField acID = new JTextField();
    private JPasswordField pwd = new JPasswordField();

    //for keypad opperation
    private boolean WDwaitingInput = false;
    private boolean TSwaitingInput = false;
    private boolean TSTargetWaitingInput = false;
    public String sbuffer = "";
    public int target;
    private boolean transferPerformed = false;

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

        for (int i = 0; i < inputButtonGP.length; i++) {
            if(i == 12){
                inputButtonGP[i].addActionListener(OKhlr);
            }
            if(i == 13){
                inputButtonGP[i].addActionListener(Resethlr);
            }
            inputButtonGP[i].addActionListener(KPhlr);
        }

        pack();
    }

    public void init(boolean b) {
        clean();
        if (!userAuthenticated) {
            screenWithButtons1.turnOn(!b);
            loginAndOut2.jButton1.setEnabled(b);
            loginAndOut2.jButton2.setEnabled(!b);
            inputField.setEnabled(!b);
            inputField.setVisible(!b);
            pwd.setVisible(b);
            acID.setVisible(b);
            screenWithButtons1.displayWelcome();
        }else{
            screenWithButtons1.turnOn(b);
            loginAndOut2.jButton1.setEnabled(!b);
            loginAndOut2.jButton2.setEnabled(b);
            inputField.setEnabled(!b);
            inputField.setVisible(b);
            pwd.setVisible(!b);
            acID.setVisible(!b);
            TSTargetWaitingInput = false;
            TSwaitingInput = false;
            WDwaitingInput = false;
            transferPerformed = false;
        }
        disableKeypad(b);
    }

    private void disableKeypad(boolean b) {
        for (int i = 0; i < inputButtonGP.length; i++) {
            inputButtonGP[i].setEnabled(!b);
        }
    }

    // reset buffers and input fields/areas
    private void clean() {
        acID.setText("");
        pwd.setText("");
        inputField.setText("");

        // should be removed if there is ant logic bug
        screenWithButtons1.jTextArea1.setText("");
    }

    //action listener of login button
    private class loginHLR implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            passToBE();
        }
    }
    
    // action listener of logout button
    // logout sequence will be activated after clicking action
    private class logoutHLR implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            logout();
        }
    }

    // pass to backend
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

    // validate inputs of login credential
    // no "." and blank inpur are allowed
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

    // to log out
    public void logout() {
        accountNumber = 0;
        pin = 0;
        userAuthenticated = false;
        swap = null;
        screenWithButtons1.Bt01.setText("");
        screenWithButtons1.Bt11.setText("");
        screenWithButtons1.Bt21.setText("");
        TSTargetWaitingInput = false;
        TSwaitingInput = false;
        WDwaitingInput = false;
        transferPerformed = false;
        resetAllScreenBT();
        this.init(true);
    }

    // authenticate login credential
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
            screenWithButtons1.displayFailedLogin();
        }
    } // end method authenticateUser

    // switch on the screen and connected buttons 
    private void turnOnScreenBT() {
        type = (swap instanceof SavingAccount) ? SAVINGTYPE : (swap instanceof ChequeAccount)? CHEQUEINGTYPE: 
        bankDatabase.getAccountTypeString(currentAccountNumber);

        // place this to button hlr (maybe)
        swap = (swap instanceof SavingAccount)? swap = (SavingAccount) swap : (swap instanceof ChequeAccount)?
            swap = (ChequeAccount) swap : swap;

        // System.out.println(type);
        switch (type) {
            case SAVINGTYPE:
            case GENERALTYPE:
            case CHEQUEINGTYPE:
                screenWithButtons1.Bt01.setText("Check Balance");
                screenWithButtons1.Bt01.setActionCommand(screenWithButtons1.Bt01.getText());
                screenWithButtons1.Bt01.removeActionListener(_01hlr);
                screenWithButtons1.Bt01.addActionListener(_01hlr);

                screenWithButtons1.Bt11.setText("Withdraw");
                screenWithButtons1.Bt11.setActionCommand(screenWithButtons1.Bt11.getText());
                screenWithButtons1.Bt11.removeActionListener(_11hlr);
                screenWithButtons1.Bt11.addActionListener(_11hlr);

                screenWithButtons1.Bt21.setText("Transfer");
                screenWithButtons1.Bt21.setActionCommand(screenWithButtons1.Bt21.getText());
                screenWithButtons1.Bt21.removeActionListener(_21hlr);
                screenWithButtons1.Bt21.addActionListener(_21hlr);
                break;

            case BOTHTYPE:
                screenWithButtons1.Bt01.setText("Swap saving");
                screenWithButtons1.Bt01.setActionCommand("Swap saving");
                screenWithButtons1.Bt01.removeActionListener(_01hlr);
                screenWithButtons1.Bt01.addActionListener(_01hlr);

                screenWithButtons1.Bt11.setText("Swap chequing");
                screenWithButtons1.Bt11.setActionCommand("Swap chequing");
                screenWithButtons1.Bt11.removeActionListener(_11hlr);
                screenWithButtons1.Bt11.addActionListener(_11hlr);
                break;
        }
    }
    
    // action listener of button in row 0, col 1
    private class bt01hlr implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            Transaction temp = null;
            String btCommand = screenWithButtons1.Bt01.getActionCommand();
            int amount = 1000;
            // System.out.println(btCommand);
            switch (btCommand) {
                case "Confirm":
                    // amount = Integer.valueOf(btCommand);
                    if (swap != null) {
                        temp = new Withdrawal(swap, screenWithButtons1, bankDatabase, cashDispenser, amount); 
                    }else{
                        temp = new Withdrawal(accountNumber, screenWithButtons1, bankDatabase, cashDispenser, amount); 
                    }
                    temp.execute();
                    resetAllScreenBT();
                    turnOnScreenBT();
                    // logout();
                    break;
                case "1000":
                    screenWithButtons1.jTextArea1.setText("Confirm?");
                    screenWithButtons1.Bt01.setText("Confirm");
                    screenWithButtons1.Bt01.setActionCommand("Confirm");
                    setOtherScreenBTDisable(screenWithButtons1.Bt01, true);
                    break;
                case "Check Balance":
                    temp = null;
                    if (swap != null) {
                        temp = new BalanceInquiry( 
                                swap,screenWithButtons1, bankDatabase );
                        System.out.print("has swap.\n");
                        temp.execute();
                    }else{
                        temp = new BalanceInquiry( 
                                currentAccountNumber,screenWithButtons1, bankDatabase );
                        System.out.print("no swap.\n");
                        temp.execute();
                    }
                    break;
                case "Swap saving":
                    swap = bankDatabase.swapToSaving(currentAccountNumber);
                    turnOnScreenBT();    
                    break;           
            }
        }
    }
    
    // action listener of button in row 0, col 0
    private class bt00hlr implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            Transaction temp = null;
            String btCommand = screenWithButtons1.Bt00.getActionCommand();
            int amount = 200;
            // System.out.println(btCommand);
            switch (btCommand) {
                case "Confirm":
                    // amount = Integer.valueOf(btCommand);
                    if (swap != null) {
                        temp = new Withdrawal(swap, screenWithButtons1, bankDatabase, cashDispenser, amount); 
                    }else{
                        temp = new Withdrawal(accountNumber, screenWithButtons1, bankDatabase, cashDispenser, amount); 
                    }
                    temp.execute();
                    resetAllScreenBT();
                    turnOnScreenBT();
                    // logout();
                    break;
                case "200":
                    screenWithButtons1.jTextArea1.setText("Confirm?");
                    screenWithButtons1.Bt00.setText("Confirm");
                    screenWithButtons1.Bt00.setActionCommand("Confirm");
                    setOtherScreenBTDisable(screenWithButtons1.Bt00, true);
                    break;
            }
        }
    }
    
    // action listener of button in row 1, col 0 
    private class bt10hlr implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            Transaction temp = null;
            String btCommand = screenWithButtons1.Bt10.getActionCommand();
            int amount = 400;
            // System.out.println(btCommand);
            switch (btCommand) {
                case "Confirm":
                    // amount = Integer.valueOf(btCommand);
                    if (swap != null) {
                        temp = new Withdrawal(swap, screenWithButtons1, bankDatabase, cashDispenser, amount); 
                    }else{
                        temp = new Withdrawal(accountNumber, screenWithButtons1, bankDatabase, cashDispenser, amount); 
                    }
                    temp.execute();
                    resetAllScreenBT();
                    turnOnScreenBT();
                    // logout();
                    break;
                case "400":
                    screenWithButtons1.jTextArea1.setText("Confirm?");
                    screenWithButtons1.Bt10.setText("Confirm");
                    screenWithButtons1.Bt10.setActionCommand("Confirm");
                    setOtherScreenBTDisable(screenWithButtons1.Bt10, true);
                    break;
            }
        }        
    }

    // action listener of button in row 2, col 0 
    private class bt20hlr implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            Transaction temp = null;
            String btCommand = screenWithButtons1.Bt20.getActionCommand();
            int amount = 600;
            // System.out.println(btCommand);
            switch (btCommand) {
                case "Confirm":
                    // amount = Integer.valueOf(btCommand);
                    if (swap != null) {
                        temp = new Withdrawal(swap, screenWithButtons1, bankDatabase, cashDispenser, amount); 
                    }else{
                        temp = new Withdrawal(accountNumber, screenWithButtons1, bankDatabase, cashDispenser, amount); 
                    }
                    temp.execute();
                    resetAllScreenBT();
                    turnOnScreenBT();
                    // logout();
                    break;
                case "600":
                    screenWithButtons1.jTextArea1.setText("Confirm?");
                    screenWithButtons1.Bt20.setText("Confirm");
                    screenWithButtons1.Bt20.setActionCommand("Confirm");
                    setOtherScreenBTDisable(screenWithButtons1.Bt20, true);
                    break;
            }

        }        
    }

    // action listener of button in row 3, col 0 
    private class bt30hlr implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            Transaction temp = null;
            String btCommand = screenWithButtons1.Bt30.getActionCommand();
            int amount = 800;
            // System.out.println(btCommand);
            switch (btCommand) {
                case "Confirm":
                    // amount = Integer.valueOf(btCommand);
                    if (swap != null) {
                        temp = new Withdrawal(swap, screenWithButtons1, bankDatabase, cashDispenser, amount); 
                    }else{
                        temp = new Withdrawal(accountNumber, screenWithButtons1, bankDatabase, cashDispenser, amount); 
                    }
                    temp.execute();
                    resetAllScreenBT();
                    turnOnScreenBT();
                    // logout();
                    break;
                case "800":
                    screenWithButtons1.jTextArea1.setText("Confirm?");
                    screenWithButtons1.Bt30.setText("Confirm");
                    screenWithButtons1.Bt30.setActionCommand("Confirm");
                    setOtherScreenBTDisable(screenWithButtons1.Bt30, true);
                    break;
            }

        }        
    }

    // action listener of button in row 1, col 1
    private class bt11hlr implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            String btCommand = screenWithButtons1.Bt11.getActionCommand();
            switch (btCommand) {
                case "Withdraw":
                
                    screenWithButtons1.jTextArea1.setText("");
        
                    screenWithButtons1.Bt00.setText("HKD200");
                    screenWithButtons1.Bt10.setText("HKD400");
                    screenWithButtons1.Bt20.setText("HKD600");
                    screenWithButtons1.Bt30.setText("HKD800");
                    screenWithButtons1.Bt01.setText("HKD1000");
                    screenWithButtons1.Bt11.setText("Custom amount");
                    screenWithButtons1.Bt21.setText("Back");
                    // System.out.println(btCommand);

                    screenWithButtons1.Bt00.setActionCommand("200");
                    screenWithButtons1.Bt00.removeActionListener(_00hlr);
                    screenWithButtons1.Bt00.addActionListener(_00hlr);

                    screenWithButtons1.Bt10.setActionCommand("400");
                    screenWithButtons1.Bt10.removeActionListener(_10hlr);
                    screenWithButtons1.Bt10.addActionListener(_10hlr);

                    screenWithButtons1.Bt20.setActionCommand("600");
                    screenWithButtons1.Bt20.removeActionListener(_20hlr);
                    screenWithButtons1.Bt20.addActionListener(_20hlr);

                    screenWithButtons1.Bt30.setActionCommand("800");
                    screenWithButtons1.Bt30.removeActionListener(_30hlr);
                    screenWithButtons1.Bt30.addActionListener(_30hlr);

                    screenWithButtons1.Bt01.setActionCommand("1000");
                    screenWithButtons1.Bt11.setActionCommand("Custom amount");
                    screenWithButtons1.Bt21.setActionCommand("Back");

                    disableKeypad(true);

                    break;
                case "Swap chequing":
                    System.out.println(btCommand);
                    swap = bankDatabase.swapToChequing(currentAccountNumber);
                    turnOnScreenBT();    
                    break;           
                case "Custom amount":
                    screenWithButtons1.jTextArea1.setText("\nPlease enter amount");
                    WDwaitingInput = true;
                    TSwaitingInput = false;
                    TSTargetWaitingInput = false;
                    setOtherScreenBTDisable(inputButtonGP[12], true);
                    disableKeypad(false);
                    inputButtonGP[10].setEnabled(false);
                    break;
            }
        }
    }

    // button listener for Transfer method
    private class bt21hlr implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            String btCommand = screenWithButtons1.Bt21.getActionCommand();
            switch (btCommand) {
                case "Transfer":
                    TSTargetWaitingInput = true;
                    setOtherScreenBTDisable(screenWithButtons1.Bt21, true);
                    screenWithButtons1.jTextArea1.setText("\nPlease enter target bank account number: ");
                    screenWithButtons1.Bt21.setText("Back");
                    screenWithButtons1.Bt21.setActionCommand("Back");
                    TSwaitingInput = false;
                    WDwaitingInput = false;
                    transferPerformed = false;
                    disableKeypad(false);
                    // disable dot inpu in target account number
                    inputButtonGP[10].setEnabled(false);
                    break;
                case "Back":
                    screenWithButtons1.jTextArea1.setText(
                        "\nReady for other opperation."
                        );
                    TSwaitingInput = false;
                    WDwaitingInput = false;
                    TSTargetWaitingInput = false;
                    resetAllScreenBT();
                    turnOnScreenBT();
                    disableKeypad(true);
                break;
            }
        }
    }
    
    // button listeners for keypad
    private class normalKeypadNumericLiseter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            JButton src = (JButton)e.getSource();
            String btCommand = src.getActionCommand();
            if (btCommand!="OK" && btCommand != "Reset") {
                inputField.setText(inputField.getText() + btCommand);
                System.out.println(btCommand);
            }
            sbuffer = inputField.getText();
            if (btCommand == ".") {
                inputButtonGP[10].setEnabled(false);
            }
        }
    }

    private class normalKeypadOKListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Transaction temp = null;
            sbuffer = inputField.getText();
            if (!validateData(sbuffer)) {
                if (TSTargetWaitingInput && !TSwaitingInput && !WDwaitingInput && !transferPerformed) {
                    target = Integer.valueOf(sbuffer);
                    TSTargetWaitingInput = !TSTargetWaitingInput;
                    screenWithButtons1.jTextArea1.setText("\nPlease enter amount: ");
                    inputButtonGP[10].setEnabled(true);
                    turnOnScreenBT();
                    screenWithButtons1.Bt21.setText("Back");
                    screenWithButtons1.Bt21.setActionCommand("Back");
                }
                if (TSwaitingInput && !WDwaitingInput && !TSTargetWaitingInput && !transferPerformed) {
                    if (swap != null) {
                        temp = new Transfer(swap, screenWithButtons1, 
                        bankDatabase, Double.valueOf(sbuffer), target);
                        TSwaitingInput = false;
                        temp.execute();
                        transferPerformed = true;
                        System.out.println(transferPerformed);
                        System.out.print(screenWithButtons1.jTextArea1.getText());
                        disableKeypad(true);
                    }
                    if (swap == null) {
                        temp = new Transfer(currentAccountNumber, screenWithButtons1, 
                        bankDatabase, Double.valueOf(sbuffer), target);
                        TSwaitingInput = false;
                        temp.execute();
                        transferPerformed = true;
                        System.out.print(screenWithButtons1.jTextArea1.getText());
                        disableKeypad(true);
                    }
                    resetAllScreenBT();
                    turnOnScreenBT();
                }
                if (WDwaitingInput && !TSwaitingInput) {
                    if (swap != null) {
                        temp = new Withdrawal(swap, screenWithButtons1, 
                            bankDatabase,cashDispenser,Integer.valueOf(sbuffer));
                        temp.execute();
                    }
                    if (swap == null) {
                        temp = new Withdrawal(currentAccountNumber, screenWithButtons1, 
                            bankDatabase, cashDispenser, Integer.valueOf(sbuffer));
                        temp.execute();
                    }
                    disableKeypad(true);
                    resetAllScreenBT();
                    turnOnScreenBT();
                }
                TSwaitingInput = true;
                WDwaitingInput = false;
                TSTargetWaitingInput = false;
                inputField.setText("");
            }else{
                init(true);
                turnOnScreenBT();
                screenWithButtons1.jTextArea1.setText("Invalid format of input\n"
                + "\nAborted...");
            }
        }
    }

    private class normalKeypadResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            inputField.setText("");
        }        
    }

    // disable all irrelevent screen button to avoid miss click
    public void setOtherScreenBTDisable(JButton bt, boolean b) {
        JButton[] screenBTArray = {
            screenWithButtons1.Bt00,
            screenWithButtons1.Bt01,
            screenWithButtons1.Bt10,
            screenWithButtons1.Bt11,
            screenWithButtons1.Bt20,
            //screenWithButtons1.Bt21,
            screenWithButtons1.Bt31,
            screenWithButtons1.Bt30
        };
        for (JButton jButton : screenBTArray) {
            if (jButton != bt) {
                jButton.setEnabled(!b);
            }
        }
    }
    
    // validate input type
    public boolean validateData(String inputString) {
        return inputString.startsWith(".")||inputString.isEmpty();
    }


    // enable all screen button for further opperation
    public void resetAllScreenBT() {
        JButton[] screenBTArray = {
            screenWithButtons1.Bt00,
            screenWithButtons1.Bt01,
            screenWithButtons1.Bt10,
            screenWithButtons1.Bt11,
            screenWithButtons1.Bt20,
            screenWithButtons1.Bt21,
            screenWithButtons1.Bt30,
            screenWithButtons1.Bt31,
        };
        for (JButton jButton : screenBTArray) {
            jButton.setEnabled(true);
            jButton.setText("");
        }

    }

}
