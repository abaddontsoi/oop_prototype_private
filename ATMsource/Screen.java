// Screen.java
// Represents the screen of the ATM
import java.awt.*;
import javax.swing.*;

public class Screen
{
   JFrame frame ;
   String tittleString;
   JTextArea displayArea = new JTextArea();
   boolean inputdisabled = false;

   public Screen(String tittleString, boolean disabled){
      inputdisabled = disabled;

      frame = new JFrame(tittleString);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(500, 500);
      displayArea.enableInputMethods(inputdisabled);
      frame.add(displayArea);

      frame.setVisible(true);
   }

   public Screen(){

   }

   public void displayWindowsyMessage(String messageString) {
      JOptionPane.showMessageDialog(frame, messageString, "ATM - Dialog", JOptionPane.PLAIN_MESSAGE);
      //JOptionPane.showMessageDialog(parentComponent, message, title, messageType, icon);
   }

   // displays a message without a carriage return
   public void displayMessage( String message ) 
   {
      System.out.print( message ); 
   } // end method displayMessage

   // display a message with a carriage return
   public void displayMessageLine( String message ) 
   {
      System.out.println( message );   
   } // end method displayMessageLine

   // display a dollar amount
   public void displayDollarAmount( double amount )
   {
      System.out.printf( "$%,.2f", amount );   
   } // end method displayDollarAmount 
} // end class Screen



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