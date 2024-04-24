package TBPKT_CARGUE_APLICACION;

import javax.swing.*;
import java.awt.*;

/**
 * A Swing-based top level window class.
 * <P>
 * @author 
 */
public class TB_RES_PASO2 extends JFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();

  /**
   * Constructs a new instance.
   */
  public TB_RES_PASO2() {
    super();
    try  {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Initializes the state of this instance.
   */
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(400, 300));
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
  }
}

 