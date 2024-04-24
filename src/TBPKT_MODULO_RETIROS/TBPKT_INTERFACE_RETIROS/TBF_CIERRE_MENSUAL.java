
// Copyright (c) 2000 Sk
package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;


//import javax.swing.*;
//import java.awt.*;
import oracle.jdeveloper.layout.*;
import java.awt.event.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

import oracle.jdbc.driver.*;
import java.sql.*;
import oracle.sql.*;
import java.util.Calendar;
/**
 * A Swing-based top level window class.
 * <P>
 * @author Skandia
 */
public class TBF_CIERRE_MENSUAL extends Object {
/*  JPanel jPanel1 = new JPanel();
  JLabel l_fecha = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  JTextField t_fecha = new JTextField();
  JButton b_aceptar_f = new JButton();*/

  /**
   * Constructs a new instance.
   */
  public TBF_CIERRE_MENSUAL(String v_fecha) 
  {
    super();
    try  {
//      jbInit();
        Cierre_Mensual(v_fecha);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public TBF_CIERRE_MENSUAL(String inicio,String v_fecha) 
  {
    super();
    try  
    {
//      jbInit();
        if (inicio.toUpperCase().trim() == "INICIO")
        {
          inicio(v_fecha);
        }
        else if (inicio.toUpperCase().trim() == "EJECUCION")
        {
          Cierre_Mensual(v_fecha);
        }
        
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Initializes the state of this instance.
   */
/*  private void jbInit() throws Exception {
    this.getContentPane().setLayout(xYLayout1);
    this.setSize(new Dimension(278, 141));
    l_fecha.setText("Fecha de Cierre (yyyymmdd):");
    xYLayout1.setHeight(156);
    b_aceptar_f.setText("Aceptar");
    b_aceptar_f.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        b_aceptar_f_actionPerformed(e);
      }
    });
    xYLayout1.setWidth(291);
    this.setTitle("CIERRE MENSUAL");
    this.getContentPane().add(jPanel1, new XYConstraints(0, 0, 270, -1));
    this.getContentPane().add(l_fecha, new XYConstraints(9, 21, 169, 31));
    this.getContentPane().add(t_fecha, new XYConstraints(174, 25, 82, -1));
    this.getContentPane().add(b_aceptar_f, new XYConstraints(108, 71, 76, 26));
  }*/

  void inicio(String v_fecha ) {
    //Declaracion de variables

//    Date v_horainicio = new Date();
    Connection t_tax    =   null;
    try
    {
      /**Conectar Base de datos*/
      t_tax =   DataSourceWrapper.getInstance().getConnection();
      //v_cod_err = SQL_CIERRE_SALDOS.TB_CIERRE_SALDOS_CONTRATO(v_fecha);
      /*
      Modificacion:
      Se añade el procedimiento de invocacion a un procedimiento del AS400
      */
      
      CallableStatement cs = t_tax.prepareCall ( "{ call TBPBD_INICIO_CIERRE_MENSUAL(?)}");
      cs.setString(1,v_fecha);
      cs.setQueryTimeout(2700);
      cs.executeUpdate();

      cs.close();
    }
    catch(Exception ex)
    {
      System.out.println("Error al iniciar cierre mensual "+ex);
    }
      finally{
                    try{
                        DataSourceWrapper.closeConnection(t_tax);                  
                    }catch(SQLException sqlEx){
                        System.out.println(sqlEx.toString());
                    }
                }
  }


  void Cierre_Mensual(String v_fecha) {
    //Declaracion de variables
    String       v_men_err = " ";
    String       v_men_err2 = " ";
    int          v_cod_err = 0;
    int          v_cod_err2 = 0;
//    TBF_MENSAJES i_mensaje;

/*    v_fecha   = t_fecha.getText();
    System.out.println("Fecha en frame "+v_fecha);*/

//    Date v_horainicio = new Date();
    
    Connection t_tax    =   null;
    try
    {
      t_tax =   DataSourceWrapper.getInstance().getConnection();
      
      /**Conectar Base de datos*/
      //v_cod_err = SQL_CIERRE_SALDOS.TB_CIERRE_SALDOS_CONTRATO(v_fecha);
      /*
      Modificacion:
      Se añade el procedimiento de invocacion a un procedimiento del AS400
      */
      
      CallableStatement cs = t_tax.prepareCall ( "{? = call TBFBD_CIERRE_MENSUAL(?)}");
      cs.registerOutParameter(1,Types.VARCHAR);
      cs.setString(2,v_fecha);
      cs.setQueryTimeout(2700);
      cs.executeUpdate();
      v_men_err = cs.getString(1);

      if (v_men_err.compareTo("OK") == 0 )
      {


        
/*        Connection t_tax =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
        t_tax.setAutoCommit(false);*/
        //v_cod_err = SQL_CIERRE_SALDOS.TB_CIERRE_SALDOS_CONTRATO(v_fecha);
        /*
        Modificacion:
        Se añade el procedimiento de invocacion a un procedimiento del AS400
        */
      
        cs = t_tax.prepareCall ( "{? = call SQL_CIERRE_SALDOS.TB_CIERRE_SALDOS_CONTRATO(?)}");
        cs.registerOutParameter(1,Types.INTEGER);
        cs.setString(2,v_fecha);
        cs.setQueryTimeout(0);
        cs.executeUpdate();
        v_cod_err = cs.getInt(1);
        cs.close();
  /*Cambio heho por APC 2005-09-02 para dejar los cierres mensuales como una tarea mensual*/
  //      cs.close();
  //      t_tax.close();
  /*Fin cambio APC 2005-09-02*/    
        /* Final de la modificacion */

  /*Cambio heho por APC 2005-09-02 para dejar los cierres mensuales como una tarea mensual*/
        if (v_cod_err == 0)
        {

        System.out.println("Se va a procesar la interface de extractos.");
          System.out.println("Año "+v_fecha.substring(0,4));
          System.out.println("Mes "+new Integer(v_fecha.substring(4,6)).intValue());


          CallableStatement l_stmt1= t_tax.prepareCall("{ ?  = call  TB_FINTERFAZ_EXTRACTOS_AS400(?,?, ? ) }");
          l_stmt1.registerOutParameter(1,Types.INTEGER);
          l_stmt1.registerOutParameter(4,Types.VARCHAR);
          l_stmt1.setInt(2,new Integer (v_fecha.substring(0,4)).intValue());
          l_stmt1.setInt(3,new Integer (v_fecha.substring(4,6)).intValue());
          l_stmt1.setQueryTimeout(0);
          l_stmt1.execute();
          v_cod_err2  = l_stmt1.getInt(1);
          v_men_err2  = l_stmt1.getString(4);
          l_stmt1.close();
          System.out.println("Se realizo interface de extractos");          
          if(v_cod_err2 == 0 )
          {
            CallableStatement cco = t_tax.prepareCall ( "{ call TBPBD_CIERRE_CORPORATIVOS(?)}");
            cco.setString(1,v_fecha);
            cco.setQueryTimeout(0);
            cco.executeUpdate();
              cco.close();
            System.out.println("Se realizo saldos de corporativos");
          }
          else
          {
           System.out.println("Error en la  interface de extractos "+v_men_err2);
          t_tax.rollback();
          }

          System.out.println("Proceso de Cierre Mensual termino exitosamente");
          
/*          t_tax.commit();
          t_tax.close();*/
        
          v_men_err = "Proceso de Cierre Mensual termino exitosamente";
        }
        else  
        {
          v_men_err = "Error en Proceso de Cierre Mensual";
          System.out.println("Error en Proceso de Cierre Mensual");
        }

        CallableStatement l_stmt1= t_tax.prepareCall("{ call  TBPBD_FIN_CIERRE_MENSUAL(?,?) }");
        l_stmt1.setString(1,v_fecha);        
        l_stmt1.setString(2,v_men_err);
        l_stmt1.setQueryTimeout(0);
        l_stmt1.execute();
        l_stmt1.close();
      }
      t_tax.commit();
      /*Fin cambio APC 2005-09-02*/            
    }
    catch(Exception ex)
    {
      System.out.println("Error de conexion al procesar saldo contrato "+ex);
/*      t_tax.rollback();
      t_tax.close();*/
    }
    finally
    {
        try{
            DataSourceWrapper.closeConnection(t_tax);                  
        }catch(SQLException sqlEx){
            System.out.println(sqlEx.toString());
        }
    }

//    System.out.println("Estado despues de llamar cierre de saldos en frame: "+v_cod_err);

/*Cambio heho por APC 2005-09-02 para dejar los cierres mensuales como una tarea mensual*/
/*   if (v_cod_err == 0){
     try
     {
      System.out.println("Se va a procesar la interface de extractos.");
      System.out.println("Año "+v_fecha.substring(0,4));
      System.out.println("Mes "+new Integer(v_fecha.substring(4,6)).intValue());
      /**Conectar Base de datos
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection t_tax =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
      t_tax.setAutoCommit(false);
      CallableStatement l_stmt1= t_tax.prepareCall("{ ?  = call  TB_FINTERFAZ_EXTRACTOS_AS400(?,?, ? ) }");
      l_stmt1.registerOutParameter(1,Types.INTEGER);
      l_stmt1.registerOutParameter(4,Types.VARCHAR);
      l_stmt1.setInt(2,new Integer (v_fecha.substring(0,4)).intValue());
      l_stmt1.setInt(3,new Integer (v_fecha.substring(4,6)).intValue());
      l_stmt1.setQueryTimeout(2700);
      l_stmt1.execute();
      v_cod_err2  = l_stmt1.getInt(1);
      v_men_err2  = l_stmt1.getString(4);
      if(v_cod_err2 == 0 )
      {
       System.out.println("Se realizo interface de extractos");
      }
      else
      {
       System.out.println("Error en la  interface de extractos "+v_men_err2);
      }

      l_stmt1.close();
      t_tax.rollback();
      //t_tax.commit();
      t_tax.close();

      v_men_err = "Proceso de Cierre Mensual termino exitosamente";
      }
      catch(Exception ex){System.out.println("Error de conexion al procesar la interface de extractos "+ex);}
    }
    else {
      v_men_err = "Error en Proceso de Cierre Mensual";
      System.out.println("Error en Proceso de Cierre Mensual");
    }
    Date v_horafinal = new Date();
    System.out.println("Hora inicial: "+v_horainicio+" y hora final: "+v_horafinal);
    i_mensaje = new TBF_MENSAJES();
    i_mensaje.TBP_setModal(this);
    i_mensaje.mensaje(v_men_err);
    i_mensaje.show();*/
/*Fin cambio APC 2005-09-02*/    

  }



}


