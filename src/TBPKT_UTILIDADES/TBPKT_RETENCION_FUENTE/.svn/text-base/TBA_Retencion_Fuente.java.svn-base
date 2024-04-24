
// Copyright (c) 2001 skandia
package TBPKT_RETENCION_FUENTE;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TBA_Retencion_Fuente {

  
  /**
   * Constructor
   */
  public TBA_Retencion_Fuente() {
    Frame frame = new TBF_Retencion_Fuente();
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
    frame.addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { System.exit(0); } });
    frame.setVisible(true);
  }

  /**
   * main
   * @param args
   */
  public static void main(String[] args) {
    try  {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    new TBA_Retencion_Fuente();
  }
  public static void process(){};

}

