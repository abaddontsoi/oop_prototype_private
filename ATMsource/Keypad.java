// Keypad.java
// Represents the keypad of the ATM
import java.util.Scanner; // program uses Scanner to obtain user input
import java.util.concurrent.Flow;
import java.util.logging.Handler;
import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.Buffer;
import java.awt.event.ActionEvent;

import javax.security.auth.Subject;
import javax.swing.*;

public class Keypad 
{
	private	JFrame keypadFrame = new JFrame(); 
	private JPanel panel = new JPanel();
	private Scanner input; // reads data from the command line
	private JButton[] buttonGP = 
	{
		new JButton("1"),
		new JButton("2"),
		new JButton("3"),
		new JButton("4"),
		new JButton("5"),
		new JButton("6"),
		new JButton("7"),
		new JButton("8"),
		new JButton("9"),
		new JButton("Enter"),
		new JButton("0"),
		new JButton(".")

	};

	JFrame displayObject = new JFrame();
	JPanel screenPanel = new JPanel();
	public JTextArea TA = new JTextArea("100");

	public int intBuffer;
	public String sBuffer; 

	// no-argument constructor initializes the Scanner
	public Keypad()
	{
		TA.setSize(250,250);
		screenPanel.setVisible(true);
		screenPanel.add(TA);

		keypadFrame.setLayout(new FlowLayout());
		keypadFrame.add(panel);
		keypadFrame.add(screenPanel);
		input = new Scanner( System.in ); // for command line input, will be removed    
	} // end no-argument Keypad constructor

	public void showKeypad(){
		keypadFrame.setSize(500,500);
		keypadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		placeComponent();
		keypadFrame.setVisible(true);
	}

	public void placeComponent(){
		ButtonHandler bthlr = new ButtonHandler();
		panel.setLayout(new GridLayout(4,3));
		for (int i = 0; i < buttonGP.length; i++) {
			panel.add(buttonGP[i]);
			buttonGP[i].addActionListener(bthlr);
		}
	}

	// return an integer value entered by user 
	public int getInput()
	{
		return input.nextInt(); // we assume that user enters an integer  
	} // end method getInput

	public double getDoubleInput() {
		return input.nextInt();
	}

	private class ButtonHandler implements ActionListener{
		public void actionPerformed( ActionEvent event )
        {
            JButton src = (JButton)event.getSource();
			sBuffer = src.getActionCommand();
			System.out.println(sBuffer);
			intBuffer = Integer.parseInt(sBuffer);
			TA.setText(sBuffer);
        } // end method actionPerformed
	}
	
} // end class Keypad  



/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/