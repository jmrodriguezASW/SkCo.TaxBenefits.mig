package TBPKT_CARGUE_APLICACION;

import java.util.Date;
import javax.swing.*;
import java.awt.*;
import oracle.jdeveloper.layout.*;
import java.awt.event.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_FECHAS.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

import java.sql.*;
import oracle.sql.*;
import oracle.jdbc.driver.*;
/**Dibujar Frame que permite al usuario iniciar el proceso de cargue*/
public class TBF_CARGUE extends JFrame
{
 Connection t_tax  = null;
 BorderLayout borderLayout1 = new BorderLayout();
 //instancias de clase
 JPanel  p_panel    = new JPanel();
 JButton b_aceptar  = new JButton();
 XYLayout xYLayout1 = new XYLayout();
 JButton b_cancelar = new JButton();
 JLabel l_mensaje   = new JLabel();
 //Constructor
 public TBF_CARGUE()
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
 
 private void jbInit() throws Exception
 {
  this.getContentPane().setLayout(borderLayout1);
  this.setSize(new Dimension(550, 200));
  this.setTitle("Cargue de información");
  p_panel.setLayout(xYLayout1);
  b_aceptar.setText("Aceptar");
  b_cancelar.setText("Cancelar");
  l_mensaje.setText("¿Desea realizar el cargue de información de contratos, aportes y " +
  "retiros?");
  l_mensaje.setFont(new Font("SansSerif", 0, 14));
  b_cancelar.addActionListener(new java.awt.event.ActionListener() {
  // Accion de cancelar
  public void actionPerformed(ActionEvent e)
  {
    b_cancelar_actionPerformed(e);
  }
  });
  b_aceptar.addActionListener(new java.awt.event.ActionListener() {
  //acccion de aceptar
  public void actionPerformed(ActionEvent e)
  {
   b_aceptar_actionPerformed(e);
  }
  });
  this.getContentPane().add(p_panel, BorderLayout.CENTER);
  p_panel.add(b_aceptar, new XYConstraints(198, 104, -1, -1));
  p_panel.add(b_cancelar, new XYConstraints(272, 104, 87, 28));
  p_panel.add(l_mensaje, new XYConstraints(33, 38, 504, 31));
 }
 

 void b_aceptar_actionPerformed(ActionEvent e)
 {
  //llamado al métoso TBP_CARGUE_INI
  TBF_CARGUE i_cargue  = new TBF_CARGUE();
  i_cargue.TBP_CARGUE_INI();
 }
 
 
 void b_cancelar_actionPerformed(ActionEvent e)
 {
  try
  {
   System.exit(0);
  }
  catch(Exception ex){}
 }
 //Método que verifica si se encuentra lista la información para el cargue e inicia el proceso
 void TBP_CARGUE_INI()
 {
  try
  {
   String v_control2 = "";
   String v_fecar = "";
   TBF_RES_CARGUE f = new TBF_RES_CARGUE();
   TBF_MENSAJE m = new TBF_MENSAJE();
   TBCL_Validacion  i_valusu = new TBCL_Validacion();
   TBCL_Fecha        i_fecha  =  new    TBCL_Fecha();
   int v_contador   = 0;
   //Consultar uurl,usuario y password
   
   //se hace conexion a taxbenefit
   //Conexion a base de datos
   Statement t_st = null;
 try
   {
   t_tax    =   DataSourceWrapper.getInstance().getConnection();
   t_st = t_tax.createStatement();
   System.out.println("Se Realiza conexión con base de datos.");
   }
catch (Exception E)
   {
   System.out.println("Error "+E);
   }
    Date p = new Date();
    System.out.println("hora  "+p.toLocaleString());
   //Consultar en  la tabla de control si la informacion  esta cargada
   int v_ensayo = 0;
   while (v_ensayo < 2)
   {
    try
    {
     String t_control2 = "SELECT cifecgen,ciestado FROM cicontrol@mfund";
     ResultSet t_res2 = t_st.executeQuery(t_control2);
     while( t_res2.next())
     {
      v_control2 = t_res2.getString(2);
      v_fecar = t_res2.getString(1);
      v_contador++;

     }
     v_ensayo = 2;
     t_res2.close();
     t_tax.commit();
    }
    catch(Exception e)
    {
      v_ensayo++;
    }
   }
    //String v_cerrar = " alter session close DATABASE LINK mfund";
    //t_st.execute(v_cerrar);
   if(v_contador ==0)
   {//si no hay datos se muestra mensaje
    System.out.println("No se encontro registro de control en tabla del as400 cicontrol.");
    t_st.close();
    m.setModal2(this);
    m.mensaje("No se encuentra disponible la información para el cargue.");
    m.setVisible(true);
   }
   else if(v_contador > 1)
        {//si hay mas de un registro en la tabla de control, no se procede al cargue
         System.out.println("Se encontro mas de un registro de control en tabla del as400 cicontrol.");
         t_st.close();
         m.setModal2(this);
         m.mensaje("Se encuentr más de un registro de control para el cargue.");
         m.setVisible(true);
        }
   //si esta cargada la información se procede a insertar
   if(v_control2.equals("Y"))
   {
    //Insertar en tabla de control que se esta realizando el paso 2 que aun no ha finalizado	
    String insert_01 = "INSERT INTO cicontrol@mfund(cifecgen,cicodinter,cilevel,ciestado) VALUES ("+v_fecar+",'CI',2,'N')";
    t_st.executeUpdate(insert_01);
    t_tax.commit();
    //String v_cerrar2 = " alter session close DATABASE LINK mfund";
    //t_st.execute(v_cerrar2);
    System.out.println("La información para el cargue se encuentra lista.");
    int v_conlog = 0;
    //Consultar consecutivo
    ResultSet t_rs    = t_st.executeQuery("SELECT MAX(CIL_CONSECUTIVO)"+
                                          "FROM TBCI_LOGS");
    //mientras se encutren datos
    while(t_rs.next())
    {
     v_conlog = t_rs.getInt(1);
    }
    t_rs.close();
    v_conlog=v_conlog++;
    String v_menlog= "";
    int v_concon  = 0;
    int v_apocon  = 0;
    int v_retcon  = 0;
    int v_axpcon  = 0;
    int v_pencon  = 0;
    int suma    = 0;
    System.out.println("Se llama función almacenada en la base de datos.");
    Date d = new Date();
    System.out.println("hora inicio ."+d.toLocaleString());
    //Llamado a la funcion que consulta la información en el as400 y la inserta en tablas temporales de Tax Benefits
    CallableStatement l_stmt0 = t_tax.prepareCall("{? = call TB_FINS_REGISTROS(?,?,?,?,?,?,?,?,?,?,?,?)}");
    l_stmt0.registerOutParameter(1,Types.INTEGER);
    l_stmt0.registerOutParameter(3,Types.INTEGER);
    l_stmt0.registerOutParameter(4,Types.INTEGER);
    l_stmt0.registerOutParameter(5,Types.INTEGER);
    l_stmt0.registerOutParameter(6,Types.INTEGER);
    l_stmt0.registerOutParameter(7,Types.INTEGER);
    l_stmt0.registerOutParameter(8,Types.INTEGER);
    l_stmt0.registerOutParameter(9,Types.INTEGER);
    l_stmt0.registerOutParameter(10,Types.INTEGER);
    l_stmt0.registerOutParameter(11,Types.INTEGER);
    l_stmt0.registerOutParameter(12,Types.INTEGER);
    l_stmt0.registerOutParameter(13,Types.INTEGER);
    l_stmt0.setString(2,v_fecar);
    l_stmt0.setInt(3,v_conlog);
    l_stmt0.setInt(4,v_concon);
    l_stmt0.setInt(6,v_apocon);
    l_stmt0.setInt(8,v_retcon);
    l_stmt0.setInt(10,v_axpcon);
    l_stmt0.setInt(12,v_pencon);
    l_stmt0.execute();
    int v_indicador = l_stmt0.getInt(1);
    v_conlog = l_stmt0.getInt(3);
    v_concon = l_stmt0.getInt(4);
    int v_confal = l_stmt0.getInt(5);
    v_apocon = l_stmt0.getInt(6);
    int v_apofal = l_stmt0.getInt(7);
    v_retcon = l_stmt0.getInt(8);
    int v_retfal = l_stmt0.getInt(9);
    v_axpcon = l_stmt0.getInt(10);
    int v_axpfal = l_stmt0.getInt(11);
    v_pencon = l_stmt0.getInt(12);
    int v_penfal = l_stmt0.getInt(13);
    l_stmt0.close();
     Date h = new Date();
    System.out.println("hora final ."+h.toLocaleString());
    String delete_01 = "delete cicontrol@mfund";
    t_st.execute(delete_01);
    t_tax.commit();
    //si se produjo error cuando se realizaba la lectura y escritura
    if(v_indicador == -1  )
    {
     System.out.println("Se produjo error al procesar información");
     t_st.close();
     t_tax.commit();
     suma = v_confal + v_apofal + v_retfal +v_axpfal +v_penfal;
     if(suma >0)
     {
      v_menlog = "Nota: Favor consultar los registros no procesados en el log de transacciones.";
     }
     f.setModal(this);
     f.ejemplo("Total de registros de contratos procesados "+v_concon+" y total de registros no procesados "+v_confal+"",
     "Total de registros de aportes procesados "+v_apocon+" y total de registros no procesados "+v_apofal+"",
     "Total de registros de retiros procesados "+v_retcon+" y total de registros no procesados "+v_retfal+"",
     "Total de registros de aportes-retiros procesados "+v_axpcon+" y total de registros no procesados "+v_axpfal+"",
     "Total de registros de penalización-retiro procesados "+v_pencon+" y total de registros no procesados "+v_penfal+"",v_menlog,"Borrado de información de la librería intermedia no realizado.");
     f.setVisible(true);
    }
    else
    {//si no pasa nada se procede a borrar la información
     /*  CallableStatement l_stmt1 = t_tax.prepareCall("{? = call TB_FBORR_REGISTROS(?,?,?)}");
     l_stmt1.registerOutParameter(1,Types.INTEGER);
     l_stmt1.registerOutParameter(3,Types.INTEGER);
     l_stmt1.registerOutParameter(4,Types.INTEGER);
     l_stmt1.setString(2,v_fecar);
     l_stmt1.setInt(3,v_conlog);
     l_stmt1.execute();
     int v_indicador2 = l_stmt1.getInt(1);
     String v_men     =  l_stmt1.getString(4);
     v_conlog = l_stmt1.getInt(3);
     l_stmt1.close();
     if(v_indicador2 == -1)
     {//si falla el borraado
      t_st.close();
      t_tax.commit();
      t_tax.close();
      suma = v_confal + v_apofal + v_retfal +v_axpfal +v_penfal;
      if(suma >0)
      {
       v_menlog = "Favor consultar los registros no cargados en el log.";
      }
      f.ejemplo("Total de registros de contratos procesados "+v_concon+" y total de registros no procesados "+v_confal+"",
      "Total de registros de aportes procesados "+v_apocon+" y total de registros no procesados "+v_apofal+"",
      "Total de registros de retiros procesados "+v_retcon+" y total de registros no procesados "+v_retfal+"",
      "Total de registros de aportes-retiros procesados "+v_axpcon+" y total de registros no procesados "+v_axpfal+"",
      "Total de registros de penalización-retiro procesados "+v_pencon+" y total de registros no procesados "+v_penfal+"",v_menlog,"Nota: Favor consultar el error del borrado en el log");
     }
     else
     {*/
      t_st.close();
      t_tax.commit();
      System.out.println("Información procesada.");
      suma = v_confal + v_apofal + v_retfal +v_axpfal +v_penfal;
      if(suma >0)
      {
       v_menlog = "Nota: Favor consultar los registros no procesados en el log de transacciones.";
      }
      f.setModal(this);
      f.ejemplo("Total de registros de contratos procesados "+v_concon+" y total de registros no procesados "+v_confal+"",
      "Total de registros de aportes procesados "+v_apocon+" y total de registros no procesados "+v_apofal+"",
      "Total de registros de retiros procesados "+v_retcon+" y total de registros no procesados "+v_retfal+"",
      "Total de registros de aportes-retiros procesados "+v_axpcon+" y total de registros no procesados "+v_axpfal+"",
      "Total de registros de penalización-retiro procesados "+v_pencon+" y total de registros no procesados "+v_penfal+"",v_menlog,"Borrado de información de la libreria intermedia realizado.");
      f.setVisible(true);
     }
    //}
   }
   else
   {
    t_st.close();
    t_tax.rollback();
    System.out.println("No se encuentra disponible la información para el cargue");
    m.setModal2(this);
    m.mensaje("No se encuentra disponible la información para el cargue.");
    m.setVisible(true);
   }
  }
  catch(Exception ex)
  {//se captuera errorr
   try
   {
    t_tax.rollback();
    TBF_MENSAJE i_men = new TBF_MENSAJE();
    System.out.println("Error en el proceso "+ex);
    i_men.setModal2(this);
    i_men.mensaje("Mensaje de error "+ex);
    i_men.setVisible(true);
   }
   catch(Exception e) {}

  }
 finally{
         try{
                 DataSourceWrapper.closeConnection(t_tax);                  
         }catch(SQLException sqlEx){
                 System.out.println(sqlEx.toString());
         }
 }
 }
}
