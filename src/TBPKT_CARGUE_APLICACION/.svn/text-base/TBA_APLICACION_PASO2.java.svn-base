package TBPKT_CARGUE_APLICACION;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**Clase que permite hacer el cargue inicial de información desde  las tablas
* temporales de Tax Benefits a las definitivas*/
public class TBA_APLICACION_PASO2
{
 public TBA_APLICACION_PASO2()
 {
  Frame frame = new TBF_CARGUE_PASO2();//Frame
  //Centrar ventana
  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  Dimension frameSize = frame.getSize();
  if (frameSize.height > screenSize.height)
  {
   frameSize.height = screenSize.height;
  }
  if (frameSize.width > screenSize.width)
  {
   frameSize.width = screenSize.width;
  }
  frame.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
  frame.addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { System.exit(0); } });
  frame.setVisible(true);
 }
 public static void main(String[] args)
 {
  try
  {
   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  }
  catch (Exception e)
  {
   e.printStackTrace();
  }
  new TBA_APLICACION_PASO2();
 }
}

 