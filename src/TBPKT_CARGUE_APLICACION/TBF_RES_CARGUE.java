package TBPKT_CARGUE_APLICACION;

import javax.swing.*;
import java.awt.*;
//import oracle.dacf.control.swing.*;
import oracle.jdeveloper.layout.*;
import java.awt.event.*;
/**Calse que dibuja el resultado del cargue inicial paso1*/
public class TBF_RES_CARGUE extends JFrame {
 //instancias de clase	
 TBF_CARGUE f       = new TBF_CARGUE();
 JPanel jPanel1     = new JPanel();
 XYLayout xYLayout1 = new XYLayout();
 Frame invocar;
 JButton baceptar  = new JButton();
 JLabel lcontratos = new JLabel();
 JLabel laportes   = new JLabel();
 JLabel lretiros   = new JLabel();
 JLabel laporet    = new JLabel();
 JLabel lpenaliza  = new JLabel();
 JLabel llog       = new JLabel();
 JLabel lborrado   = new JLabel();
 //constructor
 public TBF_RES_CARGUE()
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
 public void setModal(Frame invocar)
 {
   this.invocar = invocar;
   this.invocar.setEnabled(false);
   this.show();
 }
 //Mensajes de la ventana
 public void ejemplo (String v_contrato,String v_aporte, String v_retiro, String v_aporet,String v_penaliza, String v_log ,String v_borrado)
 {
  lcontratos.setText(v_contrato);
  if(!v_aporte.trim().equals(""))
  {
   laportes.setText(v_aporte);
  }
  if(!v_retiro.trim().equals(""))
  {
   lretiros.setText(v_retiro);
  }
  if(!v_aporet.trim().equals(""))
  {
   laporet.setText(v_aporet);
  }
  if(!v_penaliza.trim().equals(""))
  {
   lpenaliza.setText(v_penaliza);
  }
  if(!v_log.trim().equals(""))
  {
   llog.setText(v_log);
  }
  if(!v_borrado.trim().equals(""))
  {
   lborrado.setText(v_borrado);
   }
  }
  //inicializador
  private void jbInit() throws Exception
  {
   this.getContentPane().setLayout(xYLayout1);
   this.setSize(new Dimension(600, 400));
   //dibujar frame
   this.setTitle("Resumen del cargue de información");
   xYLayout1.setHeight(400);
   xYLayout1.setWidth(600);
   baceptar.setText("Aceptar");
   lcontratos.setText("1");
   laportes.setText("2");
   lretiros.setText("3");
   laporet.setText("4");
   lpenaliza.setText("5");
   llog.setText("");
   lborrado.setText("");
   baceptar.addActionListener(new java.awt.event.ActionListener() {
   //accion aceptar
   public void actionPerformed(ActionEvent e)
   {
    baceptar_actionPerformed(e);
   }
   });
   this.addWindowListener(new java.awt.event.WindowAdapter() {
   //accion cerrar ventana
   public void windowClosed(WindowEvent e)
   {
    this_windowClosed(e);
   }
   });
   this.getContentPane().add(jPanel1, BorderLayout.CENTER);
   this.getContentPane().add(baceptar, new XYConstraints(251, 287, 85, 35));
   this.getContentPane().add(lcontratos, new XYConstraints(38, 36, 527, 23));
   this.getContentPane().add(laportes, new XYConstraints(38, 68, 527, 25));
   this.getContentPane().add(lretiros, new XYConstraints(38, 101, 532, 27));
   this.getContentPane().add(laporet, new XYConstraints(38, 133, 530, 24));
   this.getContentPane().add(lpenaliza, new XYConstraints(40, 166, 527, 21));
   this.getContentPane().add(llog, new XYConstraints(40, 207, 523, 25));
   this.getContentPane().add(lborrado, new XYConstraints(42, 234, 523, 23));
  }
  void this_windowClosed(WindowEvent e)
  {
   if(invocar !=  null)
    invocar.setEnabled(true);
   this.dispose();
  }
  void baceptar_actionPerformed(ActionEvent e)
  {
   if(invocar !=  null)
    invocar.setEnabled(true);
   System.exit(0);
  }
 }


