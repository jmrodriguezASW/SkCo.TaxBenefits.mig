
// Copyright (c) 2000 Sk
package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * TBPKT_RETIROS.TBCL_APLICACION_CIERRE
 * <P>
 * @author Skandia
 */
public class TBCL_APLICACION_CIERRE {

  /**
   * Constructor
   */
  public TBCL_APLICACION_CIERRE() {
//    TBF_CIERRE_MENSUAL cierre = new TBF_CIERRE_MENSUAL();
    //Center the window
/*Cambio heho por APC 2005-09-02 para dejar los cierres mensuales como una tarea mensual*/
/*    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
    frame.addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { System.exit(0); } });
    frame.setVisible(true);*/
/*Fin cambio APC 2005-09-02*/
  }

  /**
   * main
   * @param args
   */
  public static void main(String[] args) 
  {
/*Cambio heho por APC 2005-09-02 para dejar los cierres mensuales como una tarea mensual*/
/*    try  {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
*/
/*Fin cambio APC 2005-09-02*/
/*    args = new String[1];
    args[0] = "INICIO";*/
    if (args.length >0)
    {
      TBF_CIERRE_MENSUAL cierre = new TBF_CIERRE_MENSUAL("INICIO",args[0]);      
      TBF_CIERRE_MENSUAL cierre2 = new TBF_CIERRE_MENSUAL("EJECUCION",args[0]);          
    }
    else
    {
        System.out.println("No se ha dado una fecha para cerrar como parametro");
    }
  }
}

 