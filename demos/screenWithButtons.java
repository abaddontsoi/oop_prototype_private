/**
 *
 * @author abaddon
 */

import javax.swing.*;
import java.awt.Font;

public class screenWithButtons extends JPanel {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public JButton Bt21;
    public JButton Bt01;
    public JButton Bt31;
    public JButton Bt00;
    public JButton Bt10;
    public JButton Bt11;
    public JButton Bt30;
    public JButton Bt20;
    public JScrollPane jScrollPane1;
    public JTextArea jTextArea1;
    private Font f;

    public screenWithButtons() {
        initComponents();
    }

    public void initComponents() {

        jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextArea();
        Bt21 = new JButton();
        Bt01 = new JButton();
        Bt31 = new JButton();
        Bt10 = new JButton();
        Bt00 = new JButton();
        Bt11 = new JButton();
        Bt30 = new JButton();
        Bt20 = new JButton();

        setLayout(null);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        add(jScrollPane1);
        jScrollPane1.setBounds(100, 10, 514, 323);

        Bt21.setText("");
        add(Bt21);
        Bt21.setBounds(620, 170, 170, 76);

        Bt01.setText("");
        add(Bt01);
        Bt01.setBounds(620, 10, 170, 72);

        Bt31.setText("");
        add(Bt31);
        Bt31.setBounds(620, 250, 170, 84);

        Bt00.setText("");
        add(Bt00);
        Bt00.setBounds(10, 10, 81, 76);

        Bt10.setText("");
        add(Bt10);
        Bt10.setBounds(10, 90, 81, 76);

        Bt11.setText("");
        add(Bt11);
        Bt11.setBounds(620, 90, 170, 76);

        Bt30.setText("");
        add(Bt30);
        Bt30.setBounds(10, 250, 81, 80);

        Bt20.setText("");
        add(Bt20);
        Bt20.setBounds(10, 170, 81, 76);
    }

    public void turnOn(boolean b) {
        jScrollPane1.setEnabled(b);
        jTextArea1.setEditable(false);
        Bt21.setEnabled(b);
        Bt01.setEnabled(b);
        Bt31.setEnabled(b);
        Bt10.setEnabled(b);
        Bt00.setEnabled(b);
        Bt11.setEnabled(b);
        Bt30.setEnabled(b);
        Bt20.setEnabled(b);
    }

    public void displayWelcome() {
        f = new Font("Times new roman", Font.BOLD, 20);
        jTextArea1.setFont(f);
        jTextArea1.setText(
            "\nWelcome!"+
            "\nPlease login with the field on right."+
            "\nEnter your user ID on top and password in the bottom."
        );
    }

    public String displayMoney(double amount) {
        String returnValue;
        returnValue = "$" + amount;  
        return returnValue;
    }
}
