// Screen.java
// Represents the screen of the ATM
import java.awt.*;
import javax.swing.*;

public class Screen extends JPanel
{
   // JFrame frame ;
   // String tittleString;
   JTextArea displayArea = new JTextArea();
   boolean inputdisabled = false;

   public Screen(String tittleString, boolean disabled){
      
      inputdisabled = disabled;
      displayArea.enableInputMethods(!inputdisabled);
      add(displayArea);
      setVisible(true);
   }

   public Screen(){
      displayArea.enableInputMethods(inputdisabled);
      add(displayArea);
      setVisible(true);
   }

   public void displayDialogMessage(String messageString) {
      displayArea.append(messageString);
      // displayArea.setText(messageString);
   }

   // displays a message without a carriage return
   public void displayMessage( String message ) 
   {
      System.out.print( message ); 
   } // end method displayMessage

   // display a message with a carriage return
   public void displayWindowsMessage( String message ) 
   {
      System.out.println( message );
      
      displayArea.append(message);
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