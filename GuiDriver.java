   import javax.swing.*;
   import java.io.*;
   import java.util.*;
   import java.awt.*;
   public class GuiDriver extends JPanel{
      public static void main(String[] arg){
         JFrame frame = new JFrame("Suduko Solving App");
         RubixPanel panel = new RubixPanel();
         //frame.setExtendedState(Frame.MAXIMIZED_BOTH);
         frame.add(panel);
         frame.pack();
         frame.setVisible(true);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//System.out.println(System.nanoTime());
			}
		}