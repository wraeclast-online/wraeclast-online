package examples;

import javax.swing.JWindow;
import java.awt.Toolkit;
import java.awt.Dimension;

public class Notification extends JWindow {
   private final int WIDTH = 200;
   private final int HEIGHT = 30;

   public Notification() {
      positionWindow();
      setVisible(true);
   }

   // Place the window in the bottom right corner of the screen
   private void positionWindow() {
      Toolkit aToolkit = Toolkit.getDefaultToolkit();
      Dimension screen = aToolkit.getScreenSize();
      int xPosition = screen.width - (WIDTH + 10); // Right edge of the screen
      int yPosition = screen.height - (HEIGHT + 10); // Bottom edge of the screen
      setBounds(xPosition, yPosition, WIDTH, HEIGHT);
   }
}