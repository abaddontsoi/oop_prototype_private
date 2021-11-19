// Keypad.java
// Represents the keypad of the ATM
import java.util.Scanner; // program uses Scanner to obtain user input
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class Keypad extends JPanel
{
	// private	JFrame keypadFrame = new JFrame(); 
	private Scanner input; // reads data from the command line
	private JButton[] buttonGP = 
	{
		new JButton("1"),
		new JButton("2"),
		new JButton("3"),
		new JButton("Enter"),
		new JButton("4"),
		new JButton("5"),
		new JButton("6"),
		new JButton("Cancel"),
		new JButton("7"),
		new JButton("8"),
		new JButton("9"),
		new JButton("."),
		new JButton("0")
	};

	// private JFrame displayObject = new JFrame();
	private JPanel screenPanel = new JPanel();

	public int intBuffer;
	public double douBuffer;
	public String sBuffer = "";
	public boolean confirmSignal = false; 

	// no-argument constructor initializes the Scanner
	public Keypad()
	{
		screenPanel.setVisible(true);
		input = new Scanner( System.in ); // for command line input, will be removed    
	} // end no-argument Keypad constructor

	public void showKeypad(){
		placeComponent();
	}

	public void placeComponent(){
		ButtonHandler bthlr = new ButtonHandler();
		EnterHandler ethlr = new EnterHandler();
		setLayout(new GridLayout(4,3));
		for (int i = 0; i < buttonGP.length; i++) {
			add(buttonGP[i]);
			if (i != 3) {
				buttonGP[i].addActionListener(bthlr);
			}else{
				buttonGP[i].addActionListener(ethlr);
			}
		}
	}

	// return an integer value entered by user 
	public int getInput()
	{
		return input.nextInt(); // we assume that user enters an integer  
	} // end method getInput

	public int getGUIinput(){
		return intBuffer;
	}

	public int waitForReturn(){
		while (!confirmSignal) {
			continue;
		}
		return intBuffer;
	}

	public double getDoubleInput() {
		return input.nextInt();
	}

	//private inner class
	private class ButtonHandler implements ActionListener{
		public void actionPerformed( ActionEvent event )
        {
			// waitForReturn();
            JButton src = (JButton)event.getSource();
			if (!sBuffer.contains(".")  
				&& src.getActionCommand() != "." && src.getActionCommand() != "Cancel") {
				sBuffer += src.getActionCommand();
				intBuffer = Integer.parseInt(sBuffer);
				// System.out.print(sBuffer);
			}
			if ((src.getActionCommand() == "." || sBuffer.contains("."))
				&& src.getActionCommand() != "Cancel" ) {
				disableDot(true);
				sBuffer += src.getActionCommand();
				douBuffer = Double.parseDouble(sBuffer);
				// System.out.print(sBuffer);
			}
			if (src.getActionCommand() == "Cancel") {
				cancel();
			}
        } // end method actionPerformed
	}
	private class EnterHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			confirm();
			cancel();
		}
	}
	public void confirm() {
		confirmSignal = true;
	}

	public void cancel() {
		sBuffer = "";
		intBuffer = 0;
		douBuffer = 0;
		// disableDot(false);
	}

	public void disableDot(boolean b) {
		buttonGP[11].setEnabled(!b);
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