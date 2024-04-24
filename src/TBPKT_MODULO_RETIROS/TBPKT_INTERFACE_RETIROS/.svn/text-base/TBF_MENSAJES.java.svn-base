
// Copyright (c) 2000 Sk
package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

import javax.swing.*;
import java.awt.*;
import oracle.jdeveloper.layout.*;
import java.awt.event.*;

/**
 * A Swing-based top level window class.
 * <P>
 * @author Skandia
 */
public class TBF_MENSAJES extends JFrame {
  JPanel jPanel1             = new JPanel();
  XYLayout xYLayout1         = new XYLayout();
  JButton b_aceptar_m        = new JButton();
  JLabel jLabel2             = new JLabel();
  JTextField t_mensaje_error = new JTextField();
  Frame invocar;

  /**
   * Constructs a new instance.
   */
  public TBF_MENSAJES() {
    super();
    try  {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  //Dibuja el contenido de los mensajes en el frame
  public void TBP_setModal(Frame invocar){
    this.invocar = invocar;
    this.invocar.setEnabled(false);
    this.show();
  }
  //Inicializa los textos del frmae con las variables enviadas
  public void mensaje (String v_mensaje){
    t_mensaje_error.setText(v_mensaje);
  }
  /**
   * Initializes the state of this instance.
   */
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(xYLayout1);
    this.setSize(new Dimension(340, 137));
    xYLayout1.setHeight(253);
    xYLayout1.setWidth(340);
    b_aceptar_m.setText("Aceptar");
    b_aceptar_m.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        b_aceptar_m_actionPerformed(e);
      }
    });
    jLabel2.setText("Mensaje: ");
    this.setTitle("MENSAJES");
    this.getContentPane().add(jPanel1, new XYConstraints(0, 0, -1, -1));
    this.getContentPane().add(b_aceptar_m, new XYConstraints(115, 79, 83, 26));
    this.getContentPane().add(jLabel2, new XYConstraints(13, 14, 78, 22));
    this.getContentPane().add(t_mensaje_error, new XYConstraints(15, 41, 277, -1));
  }

  void b_aceptar_m_actionPerformed(ActionEvent e) {
    System.exit(0);
  }
}

                                             
