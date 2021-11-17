import java.awt.*;
import javax.swing.*;

public class _tests extends JFrame {
    private JPanel screenPanel = new JPanel();
    private JPanel outputPanel = new JPanel();
    private JPanel inputPanel = new JPanel();
    private JTextArea opArea = new JTextArea();
    private JTextField ipField = new JTextField(100);

    private JPanel keyPanel = new JPanel();
    private JButton[] buttonGP = { new JButton("1"), new JButton("2"), new JButton("3"), new JButton("Enter"),
            new JButton("4"), new JButton("5"), new JButton("6"), new JButton("Cancel"), new JButton("7"),
            new JButton("8"), new JButton("9"), new JButton("."), new JButton("0"), new JButton("00") };

    public _tests() {
        setLayout(new GridLayout(1, 2));

        opArea.setEditable(false);
        ipField.setEditable(true);

        outputPanel.setLayout(new GridLayout(1,1));
        // outputPanel.setSize(250, 400);
        outputPanel.setBounds(0,0,400,340);
        outputPanel.add(opArea);
        
        inputPanel.setLayout(new GridLayout(1,1));
        //inputPanel.setSize(250, 20);
        inputPanel.setBounds(0, 340,400,20);
        inputPanel.add(ipField);

        screenPanel.setLayout(null);
        screenPanel.setSize(250, 400);
        screenPanel.add(outputPanel);
        screenPanel.add(inputPanel);

        keyPanel.setLayout(new GridLayout(4, 3));
        for (int i = 0; i < buttonGP.length; i++) {
            keyPanel.add(buttonGP[i]);
            // buttonGP[i].addActionListener(bthlr);
        }

        screenPanel.setVisible(true);
        keyPanel.setVisible(true);
        add(screenPanel);
        add(keyPanel);
    }

    public static void main(String[] args) {
        _tests ATM = new _tests();
        ATM.run();

    }

    public void run() {
        setSize(650, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
