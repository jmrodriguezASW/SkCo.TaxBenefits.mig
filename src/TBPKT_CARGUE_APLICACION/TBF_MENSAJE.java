package TBPKT_CARGUE_APLICACION;

import javax.swing.*;
import java.awt.*;
//import oracle.dacf.control.swing.*;
import oracle.jdeveloper.layout.*;
import java.awt.event.*;
/**Clase que dibuja mensajes de error*/
public class TBF_MENSAJE extends JFrame {
 //Instancias de clase 
 TBF_CARGUE f       = new TBF_CARGUE();
 JPanel jPanel1     = new JPanel();
 XYLayout xYLayout1 = new XYLayout();
 Frame invocar;
 JButton baceptar   = new JButton();
 JLabel lmensaje    = new JLabel();
 //constructor
 public TBF_MENSAJE()
 {
  super();
  try
  {
   jbInit();
  }
  catch (Exception e)
  {
   e.printStackTrace();
  }
 }
 //Ventana modal
 public void setModal2(Frame invocar)
 {
  this.invocar = invocar;
  this.invocar.setEnabled(false);
  this.show();
 }
 //Mensaje 
 public void mensaje (String v_mensaje)
 {
  lmensaje.setText(v_mensaje);
 }
 //inicializador
 private void jbInit() throws Exception
 {
  this.getContentPane().setLayout(xYLayout1);
  this.setSize(new Dimension(600, 180));
  //Dibujar frame
  this.setTitle("Mensaje de error");
  xYLayout1.setHeight(182);
  xYLayout1.setWidth(597);
  baceptar.setText("Aceptar");
  baceptar.addActionListener(new java.awt.event.ActionListener() {
  //accion aceptar
  public void actionPerformed(ActionEvent e)
  {
   baceptar(e);
  }
  });
  this.addWindowListener(new java.awt.event.WindowAdapter() {
  //accion cerrar
  public void windowClosed(WindowEvent e)
  {
   this_windowClosed(e);
  }
  });
  this.getContentPane().add(jPanel1, BorderLayout.CENTER);
  this.getContentPane().add(baceptar, new XYConstraints(255, 101, 81, 29));
  this.getContentPane().add(lmensaje, new XYConstraints(23, 42, 557, 23));
 }
 void this_windowClosed(WindowEvent e)
 {
  System.exit(0);
 }
 void baceptar(ActionEvent e)
 {
  System.exit(0);
 }
}


