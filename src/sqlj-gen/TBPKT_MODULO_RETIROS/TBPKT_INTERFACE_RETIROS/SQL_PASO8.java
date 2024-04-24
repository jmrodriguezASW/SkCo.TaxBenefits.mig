/*@lineinfo:filename=SQL_PASO8*//*@lineinfo:user-code*//*@lineinfo:1^1*/// Copyright (c) 2000 Sk
package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

import sqlj.runtime.ref.*;
import sqlj.runtime.*;
import java.sql.*;
import java.math.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_MODULO_APORTES.*;
import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.*;
import com.ibm.as400.access.*;
import java.io.*;
import oracle.jdbc.driver.*;
import oracle.jdbc.OracleTypes.*;

/**
 * clase: SQL_PASO8
 * Procedimientos:
 *    1. TBP_PASO8: Controla el paso 8 de las Interfaces de Retiros,
 *       el cual debe procesar las reversiones y confirmaciones de retiros.
 *       Cambiar el estado de las solicitudes de retiros no procesadas por
 *       Multifund. Finalmente se debe enviar a Multifund las solicitudes de
 *       retiros no procesadas por Multifund
 * @author: Ing. Dolly Marcela Padilla Baños
 * 
 * Modificado 2008-04-09 
 * Agregar llamado al CSAF
 * @author: Marcela Ortiz Sandoval
 */

public class SQL_PASO8 extends Object {
  //Interector o cursor de la tabla TBRETIROS con los campos seleccionados
  /*@lineinfo:generated-code*//*@lineinfo:33^3*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class vint_retiros
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public vint_retiros(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    ret_con_pro_codigoNdx = findColumn("ret_con_pro_codigo");
    ret_con_numeroNdx = findColumn("ret_con_numero");
    fecha_efectivaNdx = findColumn("fecha_efectiva");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String ret_con_pro_codigo()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(ret_con_pro_codigoNdx);
  }
  private int ret_con_pro_codigoNdx;
  public String ret_con_numero()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(ret_con_numeroNdx);
  }
  private int ret_con_numeroNdx;
  public String fecha_efectiva()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(fecha_efectivaNdx);
  }
  private int fecha_efectivaNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:33^116*/
  /*@lineinfo:generated-code*//*@lineinfo:34^3*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class vint_interface
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public vint_interface(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    KPGLDZNdx = findColumn("KPGLDZ");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String KPGLDZ()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(KPGLDZNdx);
  }
  private int KPGLDZNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:34^60*/

  public void TBP_PASO8(int v_veces, int v_proceso) {
    int               v_cod_err          = 0;
    int               v_cod_err_contable = 0;
    int               v_cod_err_aportes  = 0;
    int               v_cod_err_cierre   = 1;
    int               v_cod_err_extractos   = 1;
    int               v_consecutivo_log  = 0;
    String            v_men_err_contable = " ";
    String            v_men_err          = " ";
    String            v_men_err_aportes  = " ";
    String            v_men_err_extractos  = " ";
    Integer           v_fecha_control    = null;
    int               v_cont             = 0;
    String            v_valido           = null;
    String            v_ejecutar         = "N";
    String            v_proceso_exitoso  = "N";
    String            v_fecha_c          = " ";
    vint_retiros      vreg_retiro        = null;
    Date              v_fecha_d          = null;
    TBCL_Validacion   i_valusu           = new TBCL_Validacion();
    String            v_fecha_contrato;
    String            v_fecha_unidad;
    double[]          v_vector;
    String[]          v_valusu;
    vint_interface    i_interface;
    TBCL_AS400        ias400;
    AS400             as400;
    String            v_sistema         = " ";
    String            v_usuario         = " ";
    String            v_password        = " ";
    String            v_libreria        = " ";
    int               v_sw = 1;                

    try{

      
       //Conexion con la base de datos
      v_valusu = new String[3];
      v_valusu = i_valusu.TBFL_ValidarUsuario();
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      Connection t_tax =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
      DefaultContext.setDefaultContext(new DefaultContext( v_valusu[0],v_valusu[1],v_valusu[2],false));
      //****************************************************
      //Verificar si es valido que el Task Schedule ejecute
      //el paso 8. Si en el Log esta registrado el paso 8
      //como exitoso no volver a ejecutarlo, en caso contrario
      //reintentar nuevamente
      //****************************************************


      /*@lineinfo:generated-code*//*@lineinfo:86^7*/

//  ************************************************************
//  #sql { call TBPBD_CRONE_LOG('8',
//                                   :v_ejecutar,
//                                   :v_consecutivo_log,
//                                   :v_cod_err,
//                                   :v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_CRONE_LOG('8',\n                                  :1  ,\n                                  :2  ,\n                                  :3  ,\n                                  :4  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setInt(3,v_cod_err);
   __sJT_st.setString(4,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_ejecutar = (String)__sJT_st.getString(1);
   v_consecutivo_log = __sJT_st.getInt(2); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cod_err = __sJT_st.getInt(3); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(4);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:90^51*/
      System.out.println("v_ejecutar "+v_ejecutar);
      System.out.println("v_consecutivo "+v_consecutivo_log);
      if (v_cod_err == 0){

          //*************************************************
          //Ejecutar Paso 8
          //*************************************************
          while ((v_proceso_exitoso == "N") && (v_cont <= v_veces))
           {
            v_cod_err = 0;
            v_men_err = " ";
            /*@lineinfo:generated-code*//*@lineinfo:102^13*/

//  ************************************************************
//  #sql { call TBPBD_INTERF_RET_GRAL('8',
//                                               :v_fecha_control,
//                                               :v_valido,
//                                               :v_veces,
//                                               :v_cod_err,
//                                               :v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INTERF_RET_GRAL('8',\n                                              :1  ,\n                                              :2  ,\n                                              :3  ,\n                                              :4  ,\n                                              :5  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setInt(3,v_veces);
   __sJT_st.setInt(4,v_cod_err);
   __sJT_st.setString(5,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_fecha_control = new Integer(__sJT_st.getInt(1)); if (__sJT_st.wasNull()) v_fecha_control = null;
   v_valido = (String)__sJT_st.getString(2);
   v_cod_err = __sJT_st.getInt(4); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:107^63*/
            if (v_cod_err == 0){
              v_proceso_exitoso = "S";
            }
            else{
              v_proceso_exitoso = "N";
              v_cont            = v_cont + 1;
            }
          }//Mientras ejecucion de paso 8 no es exitosa
          System.out.println("Despues de llamar Interface Retiros General FUE VALIDO "+v_valido+" Mensaje de error "+v_men_err+" Proceso exitoso"+v_proceso_exitoso);
          System.out.println("Fecha de control "+v_fecha_control);
          //********************************************************************
          //Si el paso 8 fue valido ejecutarlo llamar los demas procedimientos.
          //En caso de no ser valido insertar en el Log el nuevo intento fallido
          //del Task Schedule
          //********************************************************************
          if (v_valido.equals("S"))
          {
              //*******************************************************************
              //Agregado 2008-04-09 MOS
              //Se conecta al 400 para buscar elementos de conexion y luego  
              // realizar llamado para ejecutar el CSAF. Se llama una funcion
              //que ejecuta el procedimiento de CSAF la cual retorna:
              // 0: Si corrio 
              // -1: Si hubo una excepcion
              // -2: Si no corrio 
              //*******************************************************************
              System.out.println("Inicia ejecución csaf");
              /*@lineinfo:generated-code*//*@lineinfo:135^15*/

//  ************************************************************
//  #sql v_sw = { values(TB_FREFERENCIAS_MULTI(:v_sistema,
//                                                           :v_usuario,
//                                                           :v_password,
//                                                           :v_libreria )) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FREFERENCIAS_MULTI( :2  ,\n                                                          :3  ,\n                                                          :4  ,\n                                                          :5   ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_sistema);
   __sJT_st.setString(3,v_usuario);
   __sJT_st.setString(4,v_password);
   __sJT_st.setString(5,v_libreria);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_sw = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_sistema = (String)__sJT_st.getString(2);
   v_usuario = (String)__sJT_st.getString(3);
   v_password = (String)__sJT_st.getString(4);
   v_libreria = (String)__sJT_st.getString(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:138^78*/
              if (v_sw == 0){
                //Conectarse al AS400
                as400 = new AS400(""+v_sistema+"");
                try{
                  //se conecta con usuario y password
                  as400.setUserId(""+v_usuario+"");
                  as400.setPassword(""+v_password+"");
                }//Hubo error al conectarse al AS400
                catch (Exception e){
                  System.out.println("Error al conectarse al As400 "+e);
                  v_cod_err = -1;
                }//Hubo error al conectarse al AS400
                System.out.println("v_cod_err despues de conexion as "+v_cod_err);
                if (v_cod_err == 0){
                  try{
                   ias400 = new TBCL_AS400();
                   v_cod_err = ias400.TBF_CSAF(as400, v_libreria);
                   System.out.println("Termina ejecución csaf");
                  }
                  catch(Exception e){ v_cod_err = -3;}
                
                }
              }
              /*Fin del bloque agregado*/
             
             String v_eje_interfaces = "";
             /*@lineinfo:generated-code*//*@lineinfo:165^14*/

//  ************************************************************
//  #sql i_interface = { SELECT  KPGLDZ
//                                      FROM  AJKPCPP@MFUND
//                                     WHERE KPVVSZ = 'RE'
//                                       AND KPQGNU = 2 };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT  KPGLDZ\n                                    FROM  AJKPCPP@MFUND\n                                   WHERE KPVVSZ = 'RE'\n                                     AND KPQGNU = 2";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",theSqlTS);
   // execute query
   i_interface = new TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8.vint_interface(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"3TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:168^52*/
             if(i_interface.next())
             {
                v_eje_interfaces = i_interface.KPGLDZ();
             }
             i_interface.close();

             if(v_eje_interfaces.equals(""))
             {
              //*************************************************
              //Ejecutar el Paso 2 de la interface de aportes
              //*************************************************
              v_proceso_exitoso = "N";
              v_cont            = 1;
              while ((v_proceso_exitoso.equals("N")) && (v_cont <= v_veces))
              {
                v_cod_err_aportes = 0;
                v_men_err_aportes = TBPKT_MODULO_APORTES.TBCBD_INGRESO_REVERSION_APORTES.TBFL_Inicio();
                System.out.println("Despues de llamar TBPKT_MODULO_APORTES.TBCBD_INGRESO_REVERSION_APORTES.TBFL_Inicio() "+v_men_err_aportes);
                System.out.println("Parametro de Interfece Log Fecha_control "+v_fecha_control);
                if (v_men_err_aportes.equals("ERROR"))
                {
                 v_cont            = v_cont + 1;
                 v_proceso_exitoso = "N";
                 v_cod_err_aportes = -1;
                 System.out.println("Antes de TBPBD_INS_TBINTERFACE_LOGS por error.");
                 System.out.println("Va a insertar a interface logs 'EG'," +v_fecha_control+",'8','Error en  el procedimiento de ingreso y reversión de aportes.','XXXXX','XXXXX',null");
                 /*@lineinfo:generated-code*//*@lineinfo:195^18*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG',
//                                                          TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
//                                                          '8',
//                                                          'Error en  el procedimiento de ingreso y reversión de aportes.',
//                                                          'XXXXX',
//                                                          'XXXXX',
//                                                          null ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG',\n                                                        TO_DATE( :1  ,'RRRR-MM-DD'),\n                                                        '8',\n                                                        'Error en  el procedimiento de ingreso y reversión de aportes.',\n                                                        'XXXXX',\n                                                        'XXXXX',\n                                                        null )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:201^63*/
                 /*@lineinfo:generated-code*//*@lineinfo:202^18*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:202^30*/
                 System.out.println("Insertó en TBPBD_INS_TBINTERFACE_LOGS por error");
                }
                else
                {
                 System.out.println("Antes de TBPBD_INS_TBINTERFACE_LOGS por exitoso");
                 System.out.println("Va a insertar a interface logs 'EG'," +v_fecha_control+"'8','Se realizo procedimiento de ingreso y reversión de aportes.','XXXXX','XXXXX',null");
                 v_proceso_exitoso = "S";
                 /*@lineinfo:generated-code*//*@lineinfo:210^18*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG',
//                                                         TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
//                                                         '8',
//                                                         'Se realizo procedimiento de ingreso y reversión de aportes.',
//                                                         'XXXXX',
//                                                         'XXXXX',
//                                                         null ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG',\n                                                       TO_DATE( :1  ,'RRRR-MM-DD'),\n                                                       '8',\n                                                       'Se realizo procedimiento de ingreso y reversión de aportes.',\n                                                       'XXXXX',\n                                                       'XXXXX',\n                                                       null )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"5TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:216^62*/
                 /*@lineinfo:generated-code*//*@lineinfo:217^18*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:217^30*/
                 System.out.println("Insertó en TBPBD_INS_TBINTERFACE_LOGS por exitoso");
                }
              }//Mientras paso 2 de interface de aportes no se realice exitosamente
              System.out.println("Despues de llamar Interface Aportes v_men_err_aportes "+v_men_err_aportes+" Proceso exitoso"+v_proceso_exitoso);
              //*************************************************
              //Ejecutar interface contable
              //*************************************************
              v_proceso_exitoso = "N";
              v_cont            = 1;
              while ((v_proceso_exitoso.equals("N")) && (v_cont <= v_veces))
              {
                v_cod_err = 0;
                // La variable v_usuario fue cambiada para enviar como usuario a PINEDALU
                /*@lineinfo:generated-code*//*@lineinfo:231^17*/

//  ************************************************************
//  #sql { call TBPBD_INTERFACECONTABLE(TO_DATE(TO_CHAR(:v_fecha_control),'RRRR-MM-DD'),
//                                                     'PINEDALU',
//                                                     :v_cod_err_contable,
//                                                     :v_men_err_contable) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INTERFACECONTABLE(TO_DATE(TO_CHAR( :1  ),'RRRR-MM-DD'),\n                                                   'PINEDALU',\n                                                    :2  ,\n                                                    :3  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
   __sJT_st.setInt(2,v_cod_err_contable);
   __sJT_st.setString(3,v_men_err_contable);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_cod_err_contable = __sJT_st.getInt(2); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err_contable = (String)__sJT_st.getString(3);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:234^78*/
                if (v_cod_err_contable != 0)
                {
                 v_cont            = v_cont + 1;
                 v_proceso_exitoso = "N";
                 String v_mensaje2 = "Error en la interface contable: "+v_men_err_contable+".";
                 /*@lineinfo:generated-code*//*@lineinfo:240^18*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG',
//                                                          TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
//                                                          '8',
//                                                          :v_mensaje2,
//                                                          'XXXXX',
//                                                          'XXXXX',
//                                                           null ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG',\n                                                        TO_DATE( :1  ,'RRRR-MM-DD'),\n                                                        '8',\n                                                         :2  ,\n                                                        'XXXXX',\n                                                        'XXXXX',\n                                                         null )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"7TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
   __sJT_st.setString(2,v_mensaje2);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:246^64*/
                 /*@lineinfo:generated-code*//*@lineinfo:247^18*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:247^30*/
                }
                else
                {
                 v_proceso_exitoso = "S";
                 /*@lineinfo:generated-code*//*@lineinfo:252^18*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG',
//                                                         TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
//                                                         '8',
//                                                         'Se realizo la interface contable.',
//                                                         'XXXXX',
//                                                         'XXXXX',
//                                                         null ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG',\n                                                       TO_DATE( :1  ,'RRRR-MM-DD'),\n                                                       '8',\n                                                       'Se realizo la interface contable.',\n                                                       'XXXXX',\n                                                       'XXXXX',\n                                                       null )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"8TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:258^62*/
                 /*@lineinfo:generated-code*//*@lineinfo:259^18*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:259^30*/
                }
              }//Mientras interface contable no se realice exitosamente
              System.out.println("Despues de llamar Interface Contable v_men_err_contable "+v_men_err_contable+" Proceso exitoso"+v_proceso_exitoso);
              //************************************************************
              //Ejecutar Cierre Mensual, Si paso 8 y aportes se ejecuto bien
              //************************************************************
/*              if ((v_cod_err == 0)&&(v_cod_err_aportes == 0))
              {
               v_proceso_exitoso = "N";
               v_cont            = 1;
               while ((v_proceso_exitoso.equals("N")) && (v_cont <= v_veces))
               {
                v_cod_err_cierre = 0;
                String v_ultimo_dia = v_fecha_control.toString();
                String v_men_err_cierre = "OK";
                #sql {call TBPBD_ULTIMO_DIA_MES (:INOUT v_ultimo_dia,
                                                 :INOUT v_cod_err_cierre,
                                                 :INOUT v_men_err)};
                if(v_cod_err_cierre == 0)
                {
                 if( v_fecha_control.toString().equals(v_ultimo_dia))
                 {
                   //v_cod_err_cierre = SQL_CIERRE_SALDOS.TB_CIERRE_SALDOS_CONTRATO(v_fecha_control.toString());
                   /*
                   Modificacion:
                   Se añade el procedimiento de invocacion a un procedimiento del AS400
                   */
/*                   CallableStatement cs = t_tax.prepareCall ( "{? = call SQL_CIERRE_SALDOS.TB_CIERRE_SALDOS_CONTRATO(?)}");
                   cs.registerOutParameter(1,Types.INTEGER);
                   cs.setString(2,v_fecha_control.toString());
                   cs.executeUpdate();
                   v_cod_err_cierre = cs.getInt(1);
                   cs.close();
                   /* Final de la modificacion */

/*                   if (v_cod_err_cierre != 0)
                   {
                    v_cont            = v_cont + 1;
                    v_proceso_exitoso   = "N";
                    String v_mensaje = "Error en el cierre mensual.Error "+v_cod_err+".";
                    #sql {call TBPBD_INS_TBINTERFACE_LOGS('EG',
                                                          TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
                                                          '8',
                                                          :v_mensaje,
                                                          'XXXXX',
                                                          'XXXXX',
                                                          null )};
                    #sql {COMMIT};
                   }
                   else
                   {
                    #sql {call TBPBD_INS_TBINTERFACE_LOGS('EG',
                                                          TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
                                                          '8',
                                                          'Se realizo el cierre mensual.',
                                                          'XXXXX',
                                                          'XXXXX',
                                                          null )};
                    #sql {COMMIT};
                    v_proceso_exitoso = "S";
                   }
                 }
                 else
                 {
                   v_cod_err_cierre = -1;
                   v_cont            = v_cont + 1;
                   v_proceso_exitoso   = "N";
                   #sql {call TBPBD_INS_TBINTERFACE_LOGS('EG',
                                                         TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
                                                        '8',
                                                        'El cierre mensual no se procesa, por no ser último día del mes.',
                                                        'XXXXX',
                                                        'XXXXX',
                                                        null )};
                   #sql {COMMIT};
                 }//fecha ultimo dia
                }
                else
                {
                  v_cont            = v_cont + 1;
                  v_proceso_exitoso   = "N";
                  String v_mensaje4 = "Error en el cierre mensual.Error en TBPBD_ULTIMO_DIA_MES  "+v_cod_err+".";
                  #sql {call TBPBD_INS_TBINTERFACE_LOGS('EG',
                                                        TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
                                                        '8',
                                                        :v_mensaje4,
                                                        'XXXXX',
                                                        'XXXXX',
                                                        null )};
                  #sql {COMMIT};
                }//cod error fecha
               }//Mientras cierre mensual no se realice exitosamente
              }//Si se puede llamar el cierre mensual
              System.out.println("Despues de llamar Cierre Mensual v_cod_err_cierre "+v_cod_err_cierre+" Proceso exitoso"+v_proceso_exitoso);
              //*************************************************
              //Ejecutar Extractos
              //*************************************************
              if (v_cod_err_cierre == 0)
              {
               System.out.println("Va a ejecutar interface de Extractos");
               #sql v_cod_err_extractos ={values(TB_FINTERFAZ_EXTRACTOS_AS400
                                                ( SUBSTR(:v_fecha_control,1,4),
                                                  SUBSTR(:v_fecha_control,5,2),
                                                  :INOUT v_men_err_extractos ) )
                                                };

               if(v_cod_err_extractos == 0)
               {
               System.out.println("Se ejecuto bien extractos.  Va a insertar en Interface Logs OK");
                #sql {call TBPBD_INS_TBINTERFACE_LOGS('EG',
                                                       TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
                                                       '8',
                                                       'Se realizo la interface de extractos exitosamente.',
                                                       'XXXXX',
                                                       'XXXXX',
                                                       null )};
               }
               else
               {
                System.out.println("Se ejecuto bien extractos.  Va a insertar en Interface Logs ERR");
                String v_mensaje3  ="Error en la interface de extractos. Mensaje: "+v_men_err_extractos+".";
                #sql {call TBPBD_INS_TBINTERFACE_LOGS('EG',
                                                       TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
                                                       '8',
                                                       :v_mensaje3,
                                                       'XXXXX',
                                                       'XXXXX',
                                                        null )};
               }
               System.out.println("Despues de llamar Interface Extractos ");
              }//Si se puede llamar extractos*/
              //**************************************************************************************
              //Si No hubo error al ejecutar el paso 8 informar en el LOg que el cron se ejecuto bien
              //Si hubo error al ejecutar paso 8 informar en el Log la falla y borrar de la tabla
              //de control el paso 8 con el fin de volver a interar nuevamente ejecutarlo
              //**************************************************************************************
              if (v_cod_err == 0)
              {
               if (v_consecutivo_log == 0)
               {
                /*@lineinfo:generated-code*//*@lineinfo:400^17*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG',
//                                                         TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
//                                                         '8',
//                                                         'TASK SCHEDULE: EXITOSO',
//                                                         'XXXXX',
//                                                         'XXXXX',
//                                                         null ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG',\n                                                       TO_DATE( :1  ,'RRRR-MM-DD'),\n                                                       '8',\n                                                       'TASK SCHEDULE: EXITOSO',\n                                                       'XXXXX',\n                                                       'XXXXX',\n                                                       null )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"9TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:406^62*/
                /*@lineinfo:generated-code*//*@lineinfo:407^17*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:407^29*/
               }//Primer intento del Task
               else
               {
                /*@lineinfo:generated-code*//*@lineinfo:411^17*/

//  ************************************************************
//  #sql { call TBPBD_UPDATE_TBINTERFACE_LOGS( :v_fecha_control,
//                                                            '8',
//                                                            :v_consecutivo_log,
//                                                            'TASK SCHEDULE: EXITOSO') };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_UPDATE_TBINTERFACE_LOGS(  :1  ,\n                                                          '8',\n                                                           :2  ,\n                                                          'TASK SCHEDULE: EXITOSO')\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"10TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
   __sJT_st.setInt(2,v_consecutivo_log);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:414^84*/
                /*@lineinfo:generated-code*//*@lineinfo:415^17*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:415^29*/
               }


              }//No hubo error al ejecutar paso8
              else
              {
               if (v_consecutivo_log == 0)
               {
                /*@lineinfo:generated-code*//*@lineinfo:424^17*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG',
//                                                         TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
//                                                         '8',
//                                                         'TASK SCHEDULE: FALLO',
//                                                         'XXXXX',
//                                                         'XXXXX',
//                                                         null ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG',\n                                                       TO_DATE( :1  ,'RRRR-MM-DD'),\n                                                       '8',\n                                                       'TASK SCHEDULE: FALLO',\n                                                       'XXXXX',\n                                                       'XXXXX',\n                                                       null )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"11TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:430^62*/
                /*@lineinfo:generated-code*//*@lineinfo:431^17*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:431^29*/

               }//Primer intento del Task
               else
               {
                /*@lineinfo:generated-code*//*@lineinfo:436^17*/

//  ************************************************************
//  #sql { call TBPBD_UPDATE_TBINTERFACE_LOGS( :v_fecha_control,
//                                                            '8',
//                                                            :v_consecutivo_log,
//                                                            'TASK SCHEDULE: FALLO') };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_UPDATE_TBINTERFACE_LOGS(  :1  ,\n                                                          '8',\n                                                           :2  ,\n                                                          'TASK SCHEDULE: FALLO')\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"12TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
   __sJT_st.setInt(2,v_consecutivo_log);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:439^82*/
                /*@lineinfo:generated-code*//*@lineinfo:440^17*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:440^29*/
               }
               //Borrar en la tabla de control el paso 8 para que la validacion en el proximo
               //intento sea correcta.
               /*@lineinfo:generated-code*//*@lineinfo:444^16*/

//  ************************************************************
//  #sql { DELETE ajkpcpp@mfund
//                         WHERE kpgldz = :v_fecha_control
//                           AND kpqgnu = 8
//                           AND kpvvsz = 'EG'  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "DELETE ajkpcpp@mfund\n                       WHERE kpgldz =  :1  \n                         AND kpqgnu = 8\n                         AND kpvvsz = 'EG'";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"13TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO8",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:447^44*/
               /*@lineinfo:generated-code*//*@lineinfo:448^16*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:448^28*/
              }//Hubo error al ejecutar paso8
             }
             else
             {
               System.out.println("No valido para ejecutar las interfaces");
             }
          }//Si fue valido realizar paso 8 ejecutar los demas procedimientos
          else
          {
           //*************************************************
           //informar en el Log que el Paso 8 no se ejecutó
           //*************************************************
           System.out.println("No valido para ejecutar");
           /* if (v_consecutivo_log == 0)
            {
             #sql {call TBPBD_INS_TBINTERFACE_LOGS('EG',
                                                    TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
                                                    '8',
                                                    'TASK SCHEDULE: FALLO',
                                                    'XXXXX',
                                                    'XXXXX',
                                                    null )};
              #sql {COMMIT};
            }
            else
            {
              #sql {call TBPBD_UPDATE_TBINTERFACE_LOGS( :v_fecha_control,
                                                        '8',
                                                        :v_consecutivo_log,
                                                        'TASK SCHEDULE: FALLO')};
              #sql {COMMIT};
            }*/
          }//No fue valido realizar paso 8
      }//No hubo error al verificar si se ejecutaba el crone
      t_tax.close();
    }
    catch (Exception e){ System.out.println("SQL_PASO8 "+e);
    }
  }//Procedimiento TBP_PASO5

}/*@lineinfo:generated-code*/