
// Copyright (c) 2000 skandia
package TBPKT_CARGUE_APLICACION;

import javax.swing.*;
import java.awt.*;
import oracle.jdeveloper.layout.*;
import java.awt.event.*;

/**Dibujar Respuesta del cargue de contratos paso 2*/
public class TBF_INFORMACION_CONTRATOS extends JFrame
{
//instancias de clase	
 JPanel jPanel1     = new JPanel();
 XYLayout xYLayout1 = new XYLayout();
 XYLayout xYLayout2 = new XYLayout();
 JPanel p_aporte    = new JPanel();
 XYLayout xYLayout3 = new XYLayout();
 JButton b_aceptar  = new JButton();
 JLabel lcontrato   = new JLabel();
 JLabel lempresa    = new JLabel();
 JLabel lexc        = new JLabel();
 JLabel lunico      = new JLabel();
 JLabel ldupli      = new JLabel();
 JLabel lprocesado  = new JLabel();
 JLabel llog        = new JLabel();
 Frame invocar;
 //Constructor
 public TBF_INFORMACION_CONTRATOS()
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
 //inicializador
 private void jbInit() throws Exception
 {
  //Dibujar frame
  this.getContentPane().setLayout(xYLayout1);
  this.setSize(new Dimension(550, 370));
  this.setTitle("Resultado del cargue de información de contratos");
  xYLayout1.setHeight(360);
  xYLayout1.setWidth(548);
  p_aporte.setBackground(Color.white);
  lcontrato.setText("lcontratos");
  lempresa.setText("lempresas");
  lexc.setText("lexc");
  lunico.setText("lunico");
  ldupli.setText("ldupli");
  lprocesado.setText("lprocesado");
  llog.setText("llog");
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
  p_aporte.add(lcontrato, new XYConstraints(6, 9, 469, -1));
  p_aporte.add(lempresa, new XYConstraints(5, 36, 476, -1));
  p_aporte.add(lexc, new XYConstraints(4, 60, 477, 18));
  p_aporte.add(lunico, new XYConstraints(5, 90, 474, 15));
  p_aporte.add(ldupli, new XYConstraints(6, 128, 473, -1));
  p_aporte.add(lprocesado, new XYConstraints(3, 151, 480, -1));
  p_aporte.add(llog, new XYConstraints(7, 208, 477, 19));
  this.getContentPane().add(b_aceptar, new XYConstraints(224, 287, 87, 34));
 }
 //Ventana Modal
 public void TBP_setModal3(Frame invocar)
 {
  this.invocar = invocar;
  this.invocar.setEnabled(false);
  this.show();
 }
 /*Mansajes de la ventana*/
 public void TBP_contrato (String v_contrato
                           ,String v_empresa
                           ,String v_exc
                           ,String v_unico
                           ,String v_dupli
                           ,String v_procesado
                           ,String v_log  )
 {
  lcontrato.setText(v_contrato);
  lempresa.setText(v_empresa);
  lexc.setText(v_exc);
  lunico.setText(v_unico);
  ldupli.setText(v_dupli);
  lprocesado.setText(v_procesado);
  if(!v_log.trim().equals(""))
  {
   llog.setText(v_log);
  }
 }
 void b_aceptar_actionPerformed(ActionEvent e)
 {
  System.exit(0);
 }
}


