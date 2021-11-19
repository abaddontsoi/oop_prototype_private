import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class new_ATM extends JFrame {
    
    private LoginPanel screen = new LoginPanel();

    public new_ATM(){
    }
    public static void main(String[] args) {
        new_ATM atm = new new_ATM();
    }

    private class hlr implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            JOptionPane.showInputDialog(this, "logined");
        }

    }
}
