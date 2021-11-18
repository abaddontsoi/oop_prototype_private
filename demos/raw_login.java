import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class raw_login extends JFrame{
    private JButton blogin;
    private JPanel loginpanel;
    private JTextField txuser;
    private JTextField pass;
    private JLabel username;
    private JLabel password;
    
    public raw_login(){
        super("Login Authentification");

        blogin = new JButton("Login");
        loginpanel = new JPanel();
        txuser = new JTextField(15);
        pass = new JPasswordField(15);
        username = new JLabel("User - ");
        password = new JLabel("Pass - ");

        setSize(300,200);
        setLocation(500,280);
        loginpanel.setLayout(null); 


        txuser.setBounds(70,30,150,20);
        pass.setBounds(70,65,150,20);
        blogin.setBounds(110,100,80,20);
        username.setBounds(20,28,80,20);
        password.setBounds(20,63,80,20);

        loginpanel.add(blogin);
        loginpanel.add(txuser);
        loginpanel.add(pass);
        loginpanel.add(username);
        loginpanel.add(password);

        getContentPane().add(loginpanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
    public static void main(String[] args) {
        raw_login atm = new raw_login();
    }
}
