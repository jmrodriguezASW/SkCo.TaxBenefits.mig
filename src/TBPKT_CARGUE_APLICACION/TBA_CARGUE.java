package TBPKT_CARGUE_APLICACION;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**Clase que permite hacer el cargue inicial de información desde la libreria
*intermedia de Multifund a las tablas temporales de Tax Benefits*/
public class TBA_CARGUE
{
 public TBA_CARGUE()
 {
  Frame frame = new TBF_CARGUE();//Lalmado al frame 
  //Centrar la Ventana
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
  new TBA_CARGUE();
 }
}

