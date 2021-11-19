/**
 *
 * @author abaddon
 */
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class LoginAndOut extends JPanel {
    public JButton jButton1;
    public JButton jButton2;

    public LoginAndOut() {
        initComponents();
    }

    public void initComponents() {

        jButton1 = new JButton();
        jButton2 = new JButton();

        setLayout(null);

        jButton1.setText("Log in");
        add(jButton1);
        jButton1.setBounds(10, 10, 313, 70);

        jButton2.setText("Log Out");
        add(jButton2);
        jButton2.setBounds(329, 10, 328, 70);
    }
}
