package TBPKT_CARGUE_APLICACION;

import javax.swing.*;
import java.awt.*;
import oracle.jdeveloper.layout.*;
import java.awt.event.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_FECHAS.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

import oracle.jdbc.*;
import java.sql.*;

import java.text.SimpleDateFormat;

import oracle.sql.*;
import oracle.jdbc.driver.*;
import java.util.Date;
import java.util.Locale;

/**Clase que inicia el porcso de cargue inicial paso2*/

public class TBF_CARGUE_PASO2 extends JFrame {
 //Instancias de clase 
 BorderLayout borderLayout1 = new BorderLayout();
 JPanel P_cargue    = new JPanel();
 XYLayout xYLayout1 = new XYLayout();
 JButton B_aceptar  = new JButton();
 JButton B_cancelar = new JButton();
 Panel panel1       = new Panel();
 GridLayout gridLayout1 = new GridLayout();
 CheckboxGroup cbg  = new CheckboxGroup();

 JLabel lfecha = new JLabel();
 String fecha = new String("");
 String fecha2 = new String("");
 Statement t_st = null;
 Connection t_tax =  null;
 JComboBox cb_fechas = new JComboBox();
 TBF_INFORMACION_CONTRATOS men = new TBF_INFORMACION_CONTRATOS();
 TBF_INFORMACION_APORET i_aporet = new TBF_INFORMACION_APORET();
 TBF_MENSAJE i_mensaje = new TBF_MENSAJE();
 TBCL_Validacion  i_valusu = new TBCL_Validacion();
 TBC_CALCULAR_UNIRETIRADAS i_numuni = new TBC_CALCULAR_UNIRETIRADAS();
 TBCL_Fecha i_fecha = new TBCL_Fecha();
 //Constructor
 public TBF_CARGUE_PASO2()
 {
  super();
  try
  {//Consultar url,usuario y password
   
   t_tax    =   DataSourceWrapper.getInstance().getConnection();
   t_st = t_tax.createStatement();
   System.out.println("Se realiza conexión con la base de datos.");
   jbInit();
  }
  catch (Exception e)
  {
   e.printStackTrace();
  }
 }
 //Inicializador de clase
 private void jbInit() throws Exception
 {
  try
  {
   this.getContentPane().setLayout(borderLayout1);
   this.setTitle("Cargue de información");
   this.setSize(new Dimension(400, 260));
   P_cargue.setLayout(xYLayout1);
   B_aceptar.setText("Aceptar");
   B_aceptar.setActionCommand("B_aceptar");
   B_aceptar.addActionListener(new java.awt.event.ActionListener() {
   //Accion aceptar 
   public void actionPerformed(ActionEvent e)
   {
    B_aceptar_actionPerformed(e);
   }
   });
   B_cancelar.setText("Cancelar");
   B_cancelar.setActionCommand("B_cancelar");
   lfecha.setText("Escoja fecha de cargue: ");
   //Consultar fechas en tablas temporales
   ResultSet t_rs2    = t_st.executeQuery("SELECT DISTINCT TO_CHAR(CIC_FECHA,'RRRR-MM-DD') "+
                                          "FROM TBCI_CONTRATOS");
   //mientras se encutren datos
   while(t_rs2.next())
   {
    cb_fechas.addItem(t_rs2.getString(1));
   }
   t_rs2.close();
   System.out.println("Se realiza consulta de las fechas de cargue.");
   B_cancelar.addActionListener(new java.awt.event.ActionListener() {
   //accion cancelar
   public void actionPerformed(ActionEvent e)
   {
    B_cancelar_actionPerformed(e);
   }
   });
   panel1.setLayout(new GridLayout(3, 1));
   panel1.add(new Checkbox("Información de Contratos",cbg, true));
   panel1.add(new Checkbox("Información de Aportes y Retiros", cbg, false));
   panel1.add(new Checkbox("Calcular Número de Unidades", cbg, false));
   this.getContentPane().add(P_cargue, BorderLayout.WEST);
   P_cargue.add(B_aceptar, new XYConstraints(122, 181, 77, 28));
   P_cargue.add(B_cancelar, new XYConstraints(195, 180, -1, 29));
   P_cargue.add(panel1, new XYConstraints(92, 40, -1, 52));
   //  P_cargue.add(panel1, new XYConstraints(88, 62, -1, 52));
   P_cargue.add(lfecha, new XYConstraints(61, 135, 138, 24));
   P_cargue.add(cb_fechas, new XYConstraints(220, 131, 101, 23));
  }
  catch (Exception e)
  {
   try
   {
    t_tax.rollback();
    TBF_MENSAJE i_men = new TBF_MENSAJE();
    System.out.println("Error en el proceso "+e);
    i_men.setModal2(this);
    i_men.mensaje("Mensaje de error "+e);
    i_men.setVisible(true);
   }
   catch(Exception ex) {}
  }
    finally{
             try{
                     DataSourceWrapper.closeConnection(t_tax);                  
             }catch(SQLException sqlEx){
                     System.out.println(sqlEx.toString());
             }
     }
 }

  //Método que consulta en tablas de control si la información esta lista e inicia proceso 
  void B_aceptar_actionPerformed(ActionEvent e)
  {
   String v_grupobox= cbg.getSelectedCheckbox().toString();
   int v_label = v_grupobox.indexOf("label");
   int v_state = v_grupobox.indexOf("state");
   String v_escoje = v_grupobox.substring(v_label+6,v_state-1);
   int v_concon    = 0;
   int v_fallo     = 0;
   int v_sumcon    = 0;
   int v_empresa   = 0;
   int v_falempresa= 0;
   int v_empcon    = 0;
   int v_falempcon = 0;
   int vregistro   = 0;
   int vregfal     = 0;
   int suma        = 0;
   int v_apocon    = 0;
   int v_apofal    = 0;
   int v_sumapo    = 0;
   int v_retcon    = 0;
   int v_retfal    = 0;
   int v_sumret    = 0;
   int v_axpcon    = 0;
   int v_axpfal    = 0;
   int v_sumaxp    = 0;
   int v_pencon    = 0;
   int v_penfal    = 0;
   int v_sumpen    = 0;
   int registro    = 0;
   int cargado     = 0;
   int nocargado   = 0;
   int v_connumuni =0;
   String v_menconnumuni ="";
   int v_conlog = 0;
   String v_menlog ="";
   
   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
  try
  {
   // Consultar conscutivo log
   ResultSet t_rs    = t_st.executeQuery("SELECT MAX(CIL_CONSECUTIVO)"+
                                         "FROM TBCI_LOGS");
   //Mientras se encutren datos
   while(t_rs.next())
   {
    v_conlog = t_rs.getInt(1);
   }
   t_rs.close();
   v_conlog++;
   fecha = cb_fechas.getSelectedItem().toString();
   //Fecha es vacia
   if(fecha.trim().equals(""))
   {
    i_mensaje.setModal2(this);
    i_mensaje.mensaje("Favor digitar la fecha en la que desea hacer el cargue de información.");
    i_mensaje.setVisible(true);
   }
   else
   {
    //Cargar contratos
    if(v_escoje.trim().equals("Información de Contratos"))
    {
     System.out.println("Se escogió información de contratos para cargar.");
     String vval4 = "X";
     //Verificar que la información de contratos no haya sido cargada anteriormente
     ResultSet t_rs4    = t_st.executeQuery("SELECT CIO_PASO  FROM TBCI_CONTROLES  WHERE CIO_INTERFACE = 'CTTAX' AND  CIO_FECHA = TO_DATE('"+fecha+"','RRRR-MM-DD') AND  CIO_PASO ='2'  AND  CIO_PASO_FINALIZADO = 'S' ");
     while(t_rs4.next())
     {
      vval4 = t_rs4.getString(1);
     }
     t_rs4.close();
     if (vval4.equals("X"))
     {
      System.out.println("Se llama función para procesar contratos.");
      Date p = new Date();
           
      /*[SO_396]Se realiza modificación para suprimir el uso del método toLocaleString ya que ha sido deprecado
       *se cambia implementación por el uso de la clase SimpleDateFormat y su método format*/
      
      //System.out.println("Hora inicio: "+p.toLocaleString());      
      System.out.println("Hora inicio: "+sdf.format(p));
      
      //Llamar función que verifica la información de los contratos e inserta en tbcontratos, tbempresas,tbempresas_contratos y tbcondiciones_contratos
      CallableStatement l_stmt0 = t_tax.prepareCall("{? = call TB_FVAL_CON(?,?,?,?,?,?,?,?,?,?,?)}");
      l_stmt0.registerOutParameter(1,Types.INTEGER);
      l_stmt0.registerOutParameter(3,Types.INTEGER);
      l_stmt0.registerOutParameter(4,Types.VARCHAR);
      l_stmt0.registerOutParameter(5,Types.INTEGER);
      l_stmt0.registerOutParameter(6,Types.INTEGER);
      l_stmt0.registerOutParameter(7,Types.INTEGER);
      l_stmt0.registerOutParameter(8,Types.INTEGER);
      l_stmt0.registerOutParameter(9,Types.INTEGER);
      l_stmt0.registerOutParameter(10,Types.INTEGER);
      l_stmt0.registerOutParameter(11,Types.INTEGER);
      l_stmt0.registerOutParameter(12,Types.INTEGER);
      l_stmt0.setString(2,fecha);
      l_stmt0.setInt(3,v_conlog);
      l_stmt0.setInt(5,vregistro);
      l_stmt0.setInt(6,vregistro);
      l_stmt0.setInt(7,v_concon);
      l_stmt0.setInt(8,v_fallo);
      l_stmt0.setInt(9,v_empresa);
      l_stmt0.setInt(10,v_empcon);
      l_stmt0.setInt(11,v_falempresa);
      l_stmt0.setInt(12,v_falempcon);
      l_stmt0.execute();
      int v_indicador = l_stmt0.getInt(1);
      v_conlog        = l_stmt0.getInt(3);
      vregistro       = l_stmt0.getInt(5);
      vregfal         = l_stmt0.getInt(6);
      v_concon        = l_stmt0.getInt(7);
      v_fallo         = l_stmt0.getInt(8);
      v_empresa       = l_stmt0.getInt(9);
      v_empcon        = l_stmt0.getInt(10);
      v_falempresa    = l_stmt0.getInt(11);
      v_falempcon     = l_stmt0.getInt(12);
      l_stmt0.close();
      Date p2 = new Date();
      
      /*[SO_396]Se realiza modificación para suprimir el uso del método toLocaleString ya que ha sido deprecado
      *se cambia implementación por el uso de la clase SimpleDateFormat y su método format*/
         
      //System.out.println("Hora final: "+p2.toLocaleString());
      System.out.println("Hora final: "+sdf.format(p2));
      
      v_sumcon    =  v_concon + v_fallo ;
      //si se produjo error cuando se realizaba la lectura y escritura
      if(v_indicador == -1  )
      {
       t_tax.commit();
       System.out.println("Se produjo error al procesar información.");
       suma = v_fallo + v_falempresa + v_falempcon;
       if(suma > 0)
       {
        v_menlog = "Nota: Favor consultar los elementos no procesados.";
       }
       men.TBP_setModal3(this);
       men.TBP_contrato("Total de Contratos procesados "+v_concon+" y no procesados "+v_fallo+".",
                        "Total de Empresas procesadas  "+v_empresa+" y no procesadas "+v_falempresa+".",
                        "Total de Relación empresa-contrato procesadas "+v_empcon+" y no procesadas "+v_falempcon+"",
                        "Total de elementos unicos:     "+v_sumcon+"",
                        "Total de elementos duplicados: "+vregfal+"",
                        "Total de registros procesados: "+vregistro+"",
                        v_menlog);
       men.setVisible(true);
      }
      else
      {
       t_tax.commit();
       System.out.println("Información procesada.");
       suma = v_fallo + v_falempresa + v_falempcon;
       if(suma > 0)
       {
        v_menlog = "Nota: Favor consultar los elementos no procesados.";
       }
       men.TBP_setModal3(this);
       men.TBP_contrato("Total de Contratos porcesados "+v_concon+" y no procesados "+v_fallo+".",
                        "Total de Empresas procesadas  "+v_empresa+" y no procesadas "+v_falempresa+".",
                        "Total de Relación empresa-contrato procesadas "+v_empcon+" y no procesadas "+v_falempcon+"",
                        "Total de elementos unicos:     "+v_sumcon+"",
                        "Total de elementos duplicados: "+vregfal+"",
                        "Total de registros procesados: "+vregistro+"",
                        v_menlog);
       men.setVisible(true);
      }
     }
     else
     {//Se ha hecho anteriomente el cargue de contratos
      t_st.close();
      t_tax.commit();
      i_mensaje.setModal2(this);
      i_mensaje.mensaje("La información de contratos para la fecha "+fecha+" ya ha sido cargada al sistema. ");
      i_mensaje.setVisible(true);
     }
    }//Si se escoje cargar aportes y retiros
    else if(v_escoje.trim().equals("Información de Aportes y Retiros"))
         {
          System.out.println("Se escogió información de aportes y retiros para cargar.");
          String vval = "X";
          //Verificar en control si se tienen contratos cargados para la fecha de proceso
          ResultSet t_rs3    = t_st.executeQuery("SELECT CIO_PASO  FROM TBCI_CONTROLES  WHERE CIO_INTERFACE = 'CTTAX' AND  CIO_FECHA = TO_DATE('"+fecha+"','RRRR-MM-DD') AND  CIO_PASO ='2'  AND  CIO_PASO_FINALIZADO = 'S' ");
          while(t_rs3.next())
          {
           vval = t_rs3.getString(1);
          }
          t_rs3.close();
          if (vval.equals("X"))
          {//No se han cargado contratos para la fecha
           t_st.close();
           t_tax.commit();
           i_mensaje.setModal2(this);
           i_mensaje.mensaje("Error en el proceso. La información de contratos para la fecha "+fecha+" no ha sido cargada.");
           i_mensaje.setVisible(true);
          }//2
          else
          {//2
           String vval2 = "X";
           //Verificar que el cargue de aportes y retiros no se haya realizsdo anteriormente
           ResultSet t_rs4    = t_st.executeQuery("SELECT CIO_PASO  FROM TBCI_CONTROLES  WHERE CIO_INTERFACE = 'APOTAX' AND  CIO_FECHA = TO_DATE('"+fecha+"','RRRR-MM-DD') AND  CIO_PASO ='2'  AND  CIO_PASO_FINALIZADO = 'S' ");
           while(t_rs4.next())
           {
            vval2 = t_rs4.getString(1);
           }
           t_rs4.close();
           if (vval2.equals("X"))
           {//3
            System.out.println("Se llama función para procesar aportes y retiros.");
            Date p3 = new Date();
            
            /*[SO_396]Se realiza modificación para suprimir el uso del método toLocaleString ya que ha sido deprecado
            *se cambia implementación por el uso de la clase SimpleDateFormat y su método format*/
           
            //System.out.println("Hora inicio: "+p3.toLocaleString());
            System.out.println("Hora inicio: "+sdf.format(p3));
            
            //Llamado a función que inserta en tbaportes, tbretiros, tbeportes_retiros y tbdisponibilidades_aportes
            CallableStatement l_stmt0 = t_tax.prepareCall("{? = call  TB_FINSTAX_REGISTROS(?,?,?,?,?,?,?,?,?,?)}");
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
            l_stmt0.setString(2,fecha);
            l_stmt0.setInt(3,v_conlog);
            l_stmt0.setInt(4,v_apocon);
            l_stmt0.setInt(5,v_apofal);
            l_stmt0.setInt(6,v_retcon);
            l_stmt0.setInt(7,v_retfal);
            l_stmt0.setInt(8,v_axpcon);
            l_stmt0.setInt(9,v_axpfal);
            l_stmt0.setInt(10,v_pencon);
            l_stmt0.setInt(11,v_penfal);
            l_stmt0.execute();
            int v_indicador = l_stmt0.getInt(1);
            v_conlog = l_stmt0.getInt(3);
            v_apocon = l_stmt0.getInt(4);
            v_apofal = l_stmt0.getInt(5);
            v_retcon = l_stmt0.getInt(6);
            v_retfal = l_stmt0.getInt(7);
            v_axpcon = l_stmt0.getInt(8);
            v_axpfal = l_stmt0.getInt(9);
            v_pencon = l_stmt0.getInt(10);
            v_penfal = l_stmt0.getInt(11);
            l_stmt0.close();
            v_sumapo = v_apocon + v_apofal;
            v_sumret = v_retcon + v_retfal;
            v_sumaxp = v_axpcon + v_axpfal;
            v_sumpen = v_pencon + v_penfal;
            Date p4 = new Date();
            
            /*[SO_396]Se realiza modificación para suprimir el uso del método toLocaleString ya que ha sido deprecado
            *se cambia implementación por el uso de la clase SimpleDateFormat y su método format*/
           
            //System.out.println("Hora final: "+p4.toLocaleString());
            System.out.println("Hora inicio: "+sdf.format(p4));          

            if(v_indicador == -1  )
            {//4
             t_st.close();
             t_tax.commit();
             System.out.println("Se produjo error al procesar información.");
             registro = v_sumapo+ v_sumret +v_sumaxp+v_sumpen;
             cargado = v_apocon +v_retcon + v_axpcon +v_pencon;
             nocargado = v_apofal +v_retfal + v_axpfal +v_penfal;
             suma = v_retfal + v_axpfal + v_penfal + v_apofal;
             if(suma > 0)
             {
              v_menlog = "Nota: Favor consultar los elementos no procesados.";
             }
             i_aporet.TBP_setModal4(this);
             i_aporet.TBP_APORET("Total de Aportes procesados "+v_apocon+" y no procesados "+v_apofal+".",
                                 "Total de Retiros procesados  "+v_retcon+" y no procesados "+v_retfal+".",
                                 "Total de Aportes por Retiro procesados "+v_axpcon+" y no procesados "+v_axpfal+"",
                                 "Total de Penalización por Retiro procesados "+v_pencon+" y no procesados "+v_penfal+"",
                                 "Total de elementos cargados:    "+cargado+"",
                                 "Total de elementos no cargados: "+nocargado+"",
                                 "Total de registros procesados: "+registro+"",
                                 v_menlog);
             i_aporet.setVisible(true);
            }//4
            else
            {//4
             t_st.close();
             t_tax.commit();
             System.out.println("Información procesada.");
             registro = v_sumapo+ v_sumret +v_sumaxp+v_sumpen;
             cargado = v_apocon +v_retcon + v_axpcon +v_pencon;
             nocargado = v_apofal +v_retfal + v_axpfal +v_penfal;
             suma = v_retfal + v_axpfal + v_penfal + v_apofal;
             if(suma > 0)
             {
              v_menlog = "Nota: Favor consultar los elementos no procesados.";
             }
             i_aporet.TBP_setModal4(this);
             i_aporet.TBP_APORET("Total de Aportes procesados "+v_apocon+" y no procesados "+v_apofal+".",
                                 "Total de Retiros procesados  "+v_retcon+" y no procesados "+v_retfal+".",
                                 "Total de Aportes por Retiro procesados "+v_axpcon+" y no procesados "+v_axpfal+"",
                                 "Total de Penalización por Retiro procesados "+v_pencon+" y no procesados "+v_penfal+"",
                                 "Total de elementos cargados:    "+cargado+"",
                                 "Total de elementos no cargados: "+nocargado+"",
                                 "Total de registros procesados: "+registro+"",
                                 v_menlog);
             i_aporet.setVisible(true);
            }//4
           }
           else
           {//Hay información de aportes y retiros ya cargada para la fecha
            t_st.close();
            t_tax.commit();
            i_mensaje.setModal2(this);
            i_mensaje.mensaje("La información de aportes y retiros para la fecha "+fecha+" ya ha sido cargada.");
            i_mensaje.setVisible(true);
           }//3
          }//2
         }//Se escoje ralizar el proceso de calculo de numero de unidades retiradas
         else if(v_escoje.trim().equals("Calcular Número de Unidades"))
              {
               t_st.close();
               t_tax.commit();
               fecha2=i_fecha.TBFL_Fecha(fecha);
               System.out.println("Llamado al la fucnión que calcula el numero de unidades.");
               java.util.Date g = new java.util.Date();
               System.out.println("Hora inicio: "+g);
               v_connumuni = i_numuni.TBP_CALCULAR_UNIDADES_RETIRADAS(fecha2,v_conlog);
               java.util.Date g2 = new java.util.Date();
               System.out.println("Hora final: "+g2);

               //Si hay error en el proceso
               if(v_connumuni != 0)
               {
                v_menconnumuni = "         Proceso de calculo de número de unidades realizado. Favor revisar el log, código CURTAX.";
               }
               else
               {
                v_menconnumuni = "            Proceso de calculo de número de unidades realizado. Número de unidades Actualizadas.";
               }
               //t_st.close();
               //t_tax.commit();
               //t_tax.close();
               i_mensaje.setModal2(this);
               i_mensaje.mensaje(v_menconnumuni);
               i_mensaje.setVisible(true);
              }//tercero
   }
  }//manejo de errores
  catch(Exception ex)
  {
    try
   {
    t_tax.rollback();
    i_mensaje.setModal2(this);
    i_mensaje.mensaje("Mensaje de error "+ex);
    i_mensaje.setVisible(true);
   }
   catch(Exception ex2)
   {
    try
   {
    t_tax.rollback();
    TBF_MENSAJE i_men = new TBF_MENSAJE();
    System.out.println("Error en el proceso "+ex);
    i_men.setModal2(this);
    i_men.mensaje("Mensaje de error "+ex);
    i_men.setVisible(true);
   }
   catch(Exception exc) {}

   }
  }
    finally
  {
         try{
                 DataSourceWrapper.closeConnection(t_tax);                  
         }catch(SQLException sqlEx){
                 System.out.println(sqlEx.toString());
         }
    }
 }
 void B_cancelar_actionPerformed(ActionEvent e)
 {
  System.exit(0);
 }
}


