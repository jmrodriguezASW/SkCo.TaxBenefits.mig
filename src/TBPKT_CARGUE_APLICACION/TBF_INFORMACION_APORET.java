
// Copyright (c) 2000 skandia
package TBPKT_CARGUE_APLICACION;

import javax.swing.*;
import java.awt.*;
import oracle.jdeveloper.layout.*;
import java.awt.event.*;
/**Dibujar frame que muestra el resultado del cargue de aportes y retiros paso2*/
public class TBF_INFORMACION_APORET extends JFrame
{
 //Instancias de clase	 
 JPanel jPanel1     = new JPanel();
 XYLayout xYLayout1 = new XYLayout();
 XYLayout xYLayout2 = new XYLayout();
 JPanel p_aporte    = new JPanel();
 XYLayout xYLayout3 = new XYLayout();
 JButton b_aceptar  = new JButton();
 JLabel laporte     = new JLabel();
 JLabel lretiro     = new JLabel();
 JLabel laporet     = new JLabel();
 JLabel lpena       = new JLabel();
 JLabel lcargado    = new JLabel();
 JLabel lnocargado  = new JLabel();
 JLabel ltotal      = new JLabel();
 JLabel llog        = new JLabel();
 Frame invocar;
 /*Constructor*/
 public TBF_INFORMACION_APORET()
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
 /**Inicializar*/
 private void jbInit() throws Exception
 {//Dibujar Frame 
  this.getContentPane().setLayout(xYLayout1);
  this.setSize(new Dimension(550, 370));
  this.setTitle("Resultado del cargue de información de aportes y retiros");
  xYLayout1.setHeight(360);
  xYLayout1.setWidth(548);
  p_aporte.setBackground(Color.white);
  laporte.setText("laporte");
  lretiro.setText("lretiro");
  laporet.setText("laporet");
  lpena.setText("lpena");
  lcargado.setText("lcargado");
  lnocargado.setText("lnocargado");
  ltotal.setText("ltotal");
  b_aceptar.setText("Aceptar");
  b_aceptar.addActionListener(new java.awt.event.ActionListener() {
  //accion aceptar
  public void actionPerformed(ActionEvent e)
  {
   b_aceptar_actionPerformed(e);
  }
  });
  p_aporte.setLayout(xYLayout3);
  jPanel1.setLayout(xYLayout2);
  this.getContentPane().add(jPanel1, new XYConstraints(0, 0, -1, -1));
  this.getContentPane().add(p_aporte, new XYConstraints(33, 34, 483, 238));
  p_aporte.add(laporte, new XYConstraints(6, 9, 469, -1));
  p_aporte.add(lretiro, new XYConstraints(5, 36, 476, -1));
  p_aporte.add(laporet, new XYConstraints(4, 60, 477, 18));
  p_aporte.add(lpena, new XYConstraints(5, 90, 474, 15));
  p_aporte.add(lcargado, new XYConstraints(6, 128, 473, -1));
  p_aporte.add(lnocargado, new XYConstraints(3, 151, 480, -1));
  p_aporte.add(ltotal, new XYConstraints(5, 175, 480, -1));
  p_aporte.add(llog, new XYConstraints(7, 208, 477, 19));
  this.getContentPane().add(b_aceptar, new XYConstraints(224, 287, 87, 34));
 }
 //Ventana modal
 public void TBP_setModal4(Frame invocar)
 {
  this.invocar = invocar;
  this.invocar.setEnabled(false);
  this.setVisible(true);
 }
 //Mensajes de la ventana
 public void TBP_APORET (String v_aporte
                           ,String v_retiro
                           ,String v_aporet
                           ,String v_pena
                           ,String v_cargado
                           ,String v_nocargado
                           ,String v_total
                           ,String v_log  )
 {
  laporte.setText(v_aporte);
  lretiro.setText(v_retiro);
  laporet.setText(v_aporet);
  lpena.setText(v_pena);
  lcargado.setText(v_cargado);
  lnocargado.setText(v_nocargado);
  ltotal.setText(v_total);
  if(!v_log.trim().equals(""))
  {
   llog.setText(v_log);
  }
 }
 //Accion aceptar
 void b_aceptar_actionPerformed(ActionEvent e)
 {
  System.exit(0);
 }
}

