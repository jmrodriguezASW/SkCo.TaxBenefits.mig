/*@lineinfo:filename=SQL_TIPO_CIERRE*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import java.sql.*;
import java.io.*;
import java.sql.Date;
import java.lang.String.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
* A Sqlj class.
* <P>
* @author: DOLLY MARCELA PADILLA BAÑOS
*/
public class SQL_TIPO_CIERRE extends Object {//clase
  //*************************************************************
  //Metodo que realiza el cierre de las unidad de proceso y
  //Producto a la fecha de cierre seleccionada por el usuario
  //*************************************************************
  /*@lineinfo:generated-code*//*@lineinfo:25^3*/ /*//  *************************************************************//*//  SQLJ iterator declaration:*//*//  *************************************************************/ public static class vinter_fecha extends sqlj.runtime.ref.ResultSetIterImpl implements sqlj.runtime.NamedIterator { public vinter_fecha(sqlj.runtime.profile.RTResultSet resultSet) throws java.sql.SQLException { super(resultSet); fecha_cierreNdx = findColumn("fecha_cierre"); m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet(); } private oracle.jdbc.OracleResultSet m_rs; public Integer fecha_cierre() throws java.sql.SQLException { Integer __sJtmp = new Integer(m_rs.getInt(fecha_cierreNdx)); if (m_rs.wasNull()) __sJtmp = null; return __sJtmp; } private int fecha_cierreNdx; } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:25^64*/
  public void TBP_TIPO_CIERRE(PrintWriter out, HttpSession session, String v_coduni, String v_producto, String v_tipcierre, String v_fechac){
    //metodox
    Date            v_fecha_sys    = null;
    int             v_cod_err      = 0;
    int             v_proceso      = 0;
    int             v_retiros      = 0;
    int             v_distribucion = 0;
    int             sw             = 0;
    int             v_cont         = 0;
    int             v_total_reg    = 0;
    int             ct;
    String          v_valTotal     = "S";
    String          v_men_err      = " ";
    String          v_val_cierre   = "N";
    String          v_cod_unidad   = " ";
    String[]        v_valusu;
    TBCL_Validacion i_valusu       = new TBCL_Validacion();
    vinter_fecha    v_fecha_cierre = null;
    Integer         v_fecha        = null;
    String          v_mensajeLog     = "";
    try{
      //************************************************************
      //Conexion a la base de datos
      //************************************************************
      v_valusu = new String[3];
      v_valusu = i_valusu.TBFL_ValidarUsuario();
     DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      DefaultContext.setDefaultContext(new DefaultContext( v_valusu[0],v_valusu[1],v_valusu[2],false));
      String v_varsession = (java.lang.String)session.getAttribute("s_cierre");
      //************************************************************
      //Verificar si hubo error en algun proceso de cierre anterior
      //************************************************************

      /*@lineinfo:generated-code*//*@lineinfo:59^7*/ /*//  *************************************************************//*//  #sql v_fecha_cierre = { SELECT TO_NUMBER(SUBSTR(:v_fechac,1,4)||SUBSTR(:v_fechac,6,2)||SUBSTR(:v_fechac,9,2))*//*//                                  AS   fecha_cierre*//*//                                  FROM DUAL };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "SELECT TO_NUMBER(SUBSTR( :1  ,1,4)||SUBSTR( :2  ,6,2)||SUBSTR( :3  ,9,2))\n                                AS   fecha_cierre\n                                FROM DUAL"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); /*// set IN parameters*/ __sJT_st.setString(1,v_fechac); __sJT_st.setString(2,v_fechac); __sJT_st.setString(3,v_fechac); /*// execute query*/ v_fecha_cierre = new TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE.vinter_fecha(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"0TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",null)); } finally { __sJT_ec.oracleCloseQuery(); } } /*//  *************************************************************/ 

                                          /*@lineinfo:user-code*//*@lineinfo:61^42*/
      while (v_fecha_cierre.next()){
        v_fecha = v_fecha_cierre.fecha_cierre();
      }
      /*@lineinfo:generated-code*//*@lineinfo:65^7*/ /*//  *************************************************************//*//  #sql v_val_cierre = { values (TBFBD_VALIDAR_CIERRE_ANT (:v_retiros,*//*//                                                               :v_distribucion,*//*//                                                               :v_cod_unidad,*//*//                                                               :v_fecha,*//*//                                                               :v_cod_err)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBFBD_VALIDAR_CIERRE_ANT ( :2  ,\n                                                              :3  ,\n                                                              :4  ,\n                                                              :5  ,\n                                                              :6  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.INTEGER); } /*// set IN parameters*/ __sJT_st.setInt(2,v_retiros); __sJT_st.setInt(3,v_distribucion); __sJT_st.setString(4,v_cod_unidad); if (v_fecha == null) __sJT_st.setNull(5,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(5,v_fecha.intValue()); __sJT_st.setInt(6,v_cod_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_val_cierre = (String)__sJT_st.getString(1); v_retiros = __sJT_st.getInt(2); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_distribucion = __sJT_st.getInt(3); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_cod_unidad = (String)__sJT_st.getString(4); v_fecha = new Integer(__sJT_st.getInt(5)); if (__sJT_st.wasNull()) v_fecha = null; v_cod_err = __sJT_st.getInt(6); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 



                                                                                /*@lineinfo:user-code*//*@lineinfo:69^80*/

      //********************************************************************
      //Si el cierre anterior no fue exitoso: tablas temporales de
      //retiros y distribucion de fondos tienen el indicador de
      //cargue exitoso en N, realizar el cargue de las temporales de
      //Tax a las librerias intermedias de Multifund.
      //Si el cierre anterior fue exitoso: Validar si se puede realizar
      //el cierre actual
      //*********************************************************************
      if (v_cod_err == 0){
        if (v_val_cierre.charAt(0)== 'Y') {
           v_val_cierre = " ";
           //**************************************************************************
           //Llamado a procedimiento que verifica si se puede realizar cierre
           //realizando la validacion en la tabla de control del AS400
           //Devolviendo Y si la validacion fue exitosa, junto con la fecha de control
           //N: Si no es posible realizar el cierre
           //***************************************************************************
            /*@lineinfo:generated-code*//*@lineinfo:88^13*/ /*//  *************************************************************//*//  #sql v_val_cierre = { values(TBFBD_VALIDAR_CIERRE(:v_fecha, :v_coduni, :v_producto, 1, :v_tipcierre, :v_cod_err, :v_men_err))  };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBFBD_VALIDAR_CIERRE( :2  ,  :3  ,  :4  , 1,  :5  ,  :6  ,  :7  )  \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ if (v_fecha == null) __sJT_st.setNull(2,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(2,v_fecha.intValue()); __sJT_st.setString(3,v_coduni); __sJT_st.setString(4,v_producto); __sJT_st.setString(5,v_tipcierre); __sJT_st.setInt(6,v_cod_err); __sJT_st.setString(7,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_val_cierre = (String)__sJT_st.getString(1); v_cod_err = __sJT_st.getInt(6); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(7); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:88^152*/
            if (v_cod_err == 0) {
              if ( v_val_cierre.charAt(0)== 'Y'){
                v_proceso      = 1;
                v_retiros      = 1;
                v_distribucion = 1;
                v_total_reg = 0;
                
                //**************************************************************************
                //Cambia temporalmente el codigo de transacción de retiro express a retiro express T0 de aquellas
                //solicitudes de retiros con fecha efectiva igual a la del cierre, estado SER001 y usuarios que son de Portal de clientes e IVR
                //Customer First - Romel Escorcia - 19/Mayo/2015 
                //**************************************************************************                
                 /*@lineinfo:generated-code*//*@lineinfo:101^18*/ /*//  *************************************************************//*//  #sql { call TBPBD_UPDATE_RETIROST0 (:v_fecha, :v_cod_err, :v_men_err)  };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_UPDATE_RETIROST0 ( :1  ,  :2  ,  :3  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ if (v_fecha == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha.intValue()); __sJT_st.setInt(2,v_cod_err); __sJT_st.setString(3,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_cod_err = __sJT_st.getInt(2); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(3); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:101^99*/
                if (v_cod_err != 0){
                          out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA ("Cierre Unidades", "Cierre Unidades","","Error en el proceso de cierre: "+v_men_err+"ajkm"+v_retiros+"ajkn"+v_distribucion,false));
                          out.println("<pre>");
                          //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
                          out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
                          out.println("</pre>");
                          out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                          out.close();
                }
                
                if (v_cod_err == 0){
                    //**************************************************************************
                    //Borrado de C3AICPP (LAAPINTER) antes de cada cierre parcial o total
                    //**************************************************************************             
                   // #sql {call TBPBD_DEL_C3AICPP (:INOUT v_cod_err, :INOUT v_men_err) };                
                     /*@lineinfo:generated-code*//*@lineinfo:117^22*/ /*//  *************************************************************//*//  #sql { DELETE FROM c3aicpp@mfund };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); String theSqlTS = "DELETE FROM c3aicpp@mfund"; __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); /*// execute statement*/ __sJT_ec.oracleExecuteBatchableUpdate(); } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:117^56*/
                     /*@lineinfo:generated-code*//*@lineinfo:118^22*/ /*//  *************************************************************//*//  #sql { DELETE FROM ajkmcpp@mfund };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); String theSqlTS = "DELETE FROM ajkmcpp@mfund"; __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"5TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); /*// execute statement*/ __sJT_ec.oracleExecuteBatchableUpdate(); } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:118^56*/
                     /*@lineinfo:generated-code*//*@lineinfo:119^22*/ /*//  *************************************************************//*//  #sql { DELETE FROM ajkncpp@mfund where kngldz<>:v_fecha };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); String theSqlTS = "DELETE FROM ajkncpp@mfund where kngldz<> :1 "; __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"6TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); /*// set IN parameters*/ if (v_fecha == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha.intValue()); /*// execute statement*/ __sJT_ec.oracleExecuteBatchableUpdate(); } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:119^79*/
                     /*@lineinfo:generated-code*//*@lineinfo:120^22*/ /*//  *************************************************************//*//  #sql { DELETE FROM c3ajcpp@mfund };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); String theSqlTS = "DELETE FROM c3ajcpp@mfund"; __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"7TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); /*// execute statement*/ __sJT_ec.oracleExecuteBatchableUpdate(); } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:120^56*/
                     /*@lineinfo:generated-code*//*@lineinfo:121^22*/ /*//  *************************************************************//*//  #sql { COMMIT };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:121^34*/
                                     
                          if (v_cod_err != 0){
                          out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA ("Cierre Unidades", "Cierre Unidades","","Error en el proceso de cierre: "+v_men_err+"ajkm"+v_retiros+"ajkn"+v_distribucion,false));
                          out.println("<pre>");
                          //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
                          out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
                          out.println("</pre>");
                          out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                          out.close();
                        }
                    
                }
                //**************************************************************************
                //Borrado de cierres parciales 
                //La información de cierres parciales queda en TBLOGS
               //**************************************************************************  
                if(v_tipcierre.equals("T"))
                {
                    /*@lineinfo:generated-code*//*@lineinfo:140^21*/ /*//  *************************************************************//*//  #sql { DELETE FROM AJKPCPP@MFUND WHERE KPQGNU = 1 AND KPMECX = '000001' };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); String theSqlTS = "DELETE FROM AJKPCPP@MFUND WHERE KPQGNU = 1 AND KPMECX = '000001'"; __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"8TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); /*// execute statement*/ __sJT_ec.oracleExecuteBatchableUpdate(); } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:140^91*/
                    /*@lineinfo:generated-code*//*@lineinfo:141^21*/ /*//  *************************************************************//*//  #sql { COMMIT };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:141^33*/
                }
                else
                {
                    //**************************************************************************
                    //Si hay cierres parciales, garantizamos que estos solo sean para el producto MFUND
                    //ROMEL ESCORCIA - 04 DE AGOSTO DE 2015
                    //**************************************************************************
                    v_producto = "MFUND";
                }
                             
                //**************************************************************************  
                //Cargar en Temporal de as400
                //**************************************************************************
                /*@lineinfo:generated-code*//*@lineinfo:155^17*/ /*//  *************************************************************//*//  #sql { call TBPBD_CARGAR (:v_coduni, :v_producto, :v_fecha, '1', :v_proceso, :v_retiros, :v_distribucion, :v_total_reg, :v_cod_err, :v_men_err) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_CARGAR ( :1  ,  :2  ,  :3  , '1',  :4  ,  :5  ,  :6  ,  :7  ,  :8  ,  :9  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(1,v_coduni); __sJT_st.setString(2,v_producto); if (v_fecha == null) __sJT_st.setNull(3,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(3,v_fecha.intValue()); __sJT_st.setInt(4,v_proceso); __sJT_st.setInt(5,v_retiros); __sJT_st.setInt(6,v_distribucion); __sJT_st.setInt(7,v_total_reg); __sJT_st.setInt(8,v_cod_err); __sJT_st.setString(9,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_proceso = __sJT_st.getInt(4); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_retiros = __sJT_st.getInt(5); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_distribucion = __sJT_st.getInt(6); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_total_reg = __sJT_st.getInt(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_cod_err = __sJT_st.getInt(8); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(9); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:155^195*/
                if (v_cod_err == 0){
                  //Realizar las actualizaciones de las tablas de control de Tax y MF
                  sw = 1;
                }//No hubo error al realizar el cargue de las tablas para el cierre actual
                else{
                  //session.setAttribute("s_cierre","3");
                  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA ("Cierre Unidades", "Cierre Unidades","","Error en el proceso de cierre: "+v_men_err+"ajkm"+v_retiros+"ajkn"+v_distribucion,false));
                  out.println("<pre>");
                  //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
                  out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
                  out.println("</pre>");
                  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                  out.close();
                }//Hubo error al realizar el cargue de las tablas para el cierre actual
              }//Si no hubo error al validar en las tablas de control v_val_cierre = Y
              else{
                //session.setAttribute("s_cierre","3");
                out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","No se puede realizar el cierre. Error: "+v_men_err,false));
                out.println("<pre>");
                //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE?cadena=89789655437996452890077676654434579041356';></center>");
                out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
                out.println("</pre>");
                out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                out.close();
              } //Si hubo error al validar en las tablas de control  v_val_cierre = N
            }//No hubo error al validar cierre
            else{
              //session.setAttribute("s_cierre","3");
              out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Error al validar tablas de control: "+v_men_err,false));
              out.println("<pre>");
              //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
             out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
              //out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
              out.println("</pre>");
              out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
              out.close();
            }//Hubo error al validar el cierre
          }//Cierre anterior exitoso realizar cierre actual
          else{
            //Llamar proceso que recarga las tablas temporales que no se cargaron en cierre anterior
            v_proceso   = 3;
            v_coduni    = v_cod_unidad;
            v_total_reg = 0;
            /*@lineinfo:generated-code*//*@lineinfo:199^13*/ /*//  *************************************************************//*//  #sql { call TBPBD_CARGAR (:v_coduni, :v_producto, :v_fecha, '1', :v_proceso, :v_retiros, :v_distribucion, :v_total_reg, :v_cod_err, :v_men_err) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_CARGAR ( :1  ,  :2  ,  :3  , '1',  :4  ,  :5  ,  :6  ,  :7  ,  :8  ,  :9  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"10TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(1,v_coduni); __sJT_st.setString(2,v_producto); if (v_fecha == null) __sJT_st.setNull(3,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(3,v_fecha.intValue()); __sJT_st.setInt(4,v_proceso); __sJT_st.setInt(5,v_retiros); __sJT_st.setInt(6,v_distribucion); __sJT_st.setInt(7,v_total_reg); __sJT_st.setInt(8,v_cod_err); __sJT_st.setString(9,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_proceso = __sJT_st.getInt(4); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_retiros = __sJT_st.getInt(5); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_distribucion = __sJT_st.getInt(6); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_total_reg = __sJT_st.getInt(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_cod_err = __sJT_st.getInt(8); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(9); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:199^191*/
            if (v_cod_err == 0){
              //Realizar las actualizaciones de las tablas de control de Tax y MF
              sw = 1;
              v_tipcierre = "P"; //Para proceso de cierre anterior el tipo de cierre sera parcial
            }//No hubo error al realizar el recargue de las tablas para el cierre anterior
            else{
              //session.setAttribute("s_cierre","3");
              out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Advertencia: No se efectuó su cierre actual pues el último proceso de cierre realizado no fue exitoso. Se realizó nuevamente este cierre con tipo Parcial.Hubo error al recargar tablas de proceso de cierre anterior: "+v_men_err,false));
              out.println("<pre>");
              out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go();></center>");
              out.println("</pre>");
              out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
              out.close();
            }//Hubo error al realizar el recargue de las tablas para el cierre anterior
          }//Cierre anterior no exitoso recargar
        }//No hubo error al validar proceso de cierre anterior
        else{
          //session.setAttribute("s_cierre","3");
          out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Error al validar proceso de cierre anterior: "+v_men_err,false));
          out.println("<pre>");
          //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
          out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
          out.println("</pre>");
          out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
          out.close();
        }//Hubo error al validar proceso de cierre anterior
        //Realizar las actualizaciones de las tablas de control de MF
        if ( sw == 1) {
          sw = 0;
          //Si no hubo registros que insertar borrar la unidad insertada en la
          //tabla de control de MF
          if (v_total_reg>0){
            //Actualizo a Y la tabla de control de MF si se pudo cerrar la unidad
            /*@lineinfo:generated-code*//*@lineinfo:233^13*/ /*//  *************************************************************//*//  #sql { call TBPBD_UPDATE_AJKPCPP(:v_fecha, :v_coduni, 1, :v_cod_err, :v_men_err) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_UPDATE_AJKPCPP( :1  ,  :2  , 1,  :3  ,  :4  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"11TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ if (v_fecha == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha.intValue()); __sJT_st.setString(2,v_coduni); __sJT_st.setInt(3,v_cod_err); __sJT_st.setString(4,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_cod_err = __sJT_st.getInt(3); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(4); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:233^104*/
          }//Si se insertaron retiros actualizar en Y tabla de control para informarles que puede MF tomar datos
          else{
            //borrar la unidad insertada en la validacion del cierre debido a que no habian retiros
            //a enviar para el proceso de cierre solicitado
            /*@lineinfo:generated-code*//*@lineinfo:238^13*/ /*//  *************************************************************//*//  #sql { call TBPBD_DEL_AJKPCPP_E(:v_coduni, :v_fecha, 1, :v_cod_err, :v_men_err) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_DEL_AJKPCPP_E( :1  ,  :2  , 1,  :3  ,  :4  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"12TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(1,v_coduni); if (v_fecha == null) __sJT_st.setNull(2,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(2,v_fecha.intValue()); __sJT_st.setInt(3,v_cod_err); __sJT_st.setString(4,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_cod_err = __sJT_st.getInt(3); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(4); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:238^103*/
          }//Si no habian retiros borrar la unidad insertada en la tabla de control para que MF no realice proceso de tomar datos
          if (v_cod_err == 0){
            //Si se el tipo de cierre realizado para la unidad de proceso fue total verificar si el resto
            //de unidades ya cerraron para informar a Multifund que paso 1 ha finalizado
            if (v_tipcierre.equals("T")){
              /*@lineinfo:generated-code*//*@lineinfo:244^15*/ /*//  *************************************************************//*//  #sql ct = { values ( TBFBD_VERIFICAR_PASO1(:v_fecha, :v_coduni, :v_cod_err) ) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBFBD_VERIFICAR_PASO1( :2  ,  :3  ,  :4  )  \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"13TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.INTEGER); } /*// set IN parameters*/ if (v_fecha == null) __sJT_st.setNull(2,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(2,v_fecha.intValue()); __sJT_st.setString(3,v_coduni); __sJT_st.setInt(4,v_cod_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ ct = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_cod_err = __sJT_st.getInt(4); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:244^97*/
              if (v_cod_err == 0){
                if ( ct == 0){
                  /*@lineinfo:generated-code*//*@lineinfo:247^19*/ /*//  *************************************************************//*//  #sql { call TBPBD_UPDATE_AJKPCPP(:v_fecha, 'UUP099', 1, :v_cod_err, :v_men_err) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_UPDATE_AJKPCPP( :1  , 'UUP099', 1,  :2  ,  :3  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"14TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ if (v_fecha == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha.intValue()); __sJT_st.setInt(2,v_cod_err); __sJT_st.setString(3,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_cod_err = __sJT_st.getInt(2); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(3); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:247^109*/
                  if (v_cod_err != 0){
                    /*@lineinfo:generated-code*//*@lineinfo:249^21*/ /*//  *************************************************************//*//  #sql { ROLLBACK };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:249^35*///De actualizacion e insercion de tabla de control de MF
                    //session.setAttribute("s_cierre","3");
                    out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Error al insertar en la tabla de control de Multifund "+v_men_err,false));
                    out.println("<pre>");
                    //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
                    out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
                    out.println("</pre>");
                    out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                    out.close();
                  }//Hubo error al insertar en ajkpcpp
                  else{
                    /*@lineinfo:generated-code*//*@lineinfo:260^21*/ /*//  *************************************************************//*//  #sql { COMMIT };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:260^33*///De actualizacion o borrado de MF
                    sw = 1; //Proceso de cierre exitoso, actualizar estado de cargue de tablas temporales de tax
                  }//No hubo error al insertar en ajkpcpp
                }//Se cierra paso 1 ct==0
                else{
                  /*@lineinfo:generated-code*//*@lineinfo:265^19*/ /*//  *************************************************************//*//  #sql { COMMIT };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:265^31*///De actualizacion o borrado de MF
                  sw = 1; //Proceso de cierre exitoso, actualizar estado de cargue de tablas temporales de tax
                }//No se cierra paso 1 ct!=0
              }//No hubo error en verificar paso 1
              else{
                //session.setAttribute("s_cierre","3");
                /*@lineinfo:generated-code*//*@lineinfo:271^17*/ /*//  *************************************************************//*//  #sql { ROLLBACK };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:271^31*///De actualizacion o borrado de tabla de control de MF
                out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Error al verificar si se cierra paso 1",false));
                out.println("<pre>");
                //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
                out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
                out.println("</pre>");
                out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                out.close();
              }//Hubo error en verificar paso 1
            }//Tipo de cierre es total
            else{
              /*@lineinfo:generated-code*//*@lineinfo:282^15*/ /*//  *************************************************************//*//  #sql { COMMIT };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:282^27*///De actualizacion de tabla de control MF
              sw = 1; //Proceso de cierre exitoso, actualizar estado de cargue de tablas temporales de tax
            }//Tipo de cierre es parcial
          }//No hubo error al actualizar tabla de control ajkpcpp
          else{
            //session.setAttribute("s_cierre","3");
            /*@lineinfo:generated-code*//*@lineinfo:288^13*/ /*//  *************************************************************//*//  #sql { ROLLBACK };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:288^27*///De actualizacion o borrado de tabla de control de MF
            out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Error al actualizar la tabla de control de Multifund"+v_men_err,false));
            out.println("<pre>");
            //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
            out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
            out.println("</pre>");
            out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
            out.close();
          }//Hubo error al actualizar ajkpcpp
        }//Realizar actualizaciones de tablas de control de MF sw=1
       //Realizar actualizaciones de las tablas de control y estados de cargue de tablas temporales de TAX
      if (sw == 1){
        //Actualizar tipo de cierre realizado en la tabla de control de Tax
        sw = 0;
        v_cont = 1;
        while ((sw==0) && (v_cont<=3)) {
          /*@lineinfo:generated-code*//*@lineinfo:304^11*/ /*//  *************************************************************//*//  #sql { call TBPBD_UPDATE_TBCIERRES (:v_coduni, :v_fecha, :v_tipcierre, :v_cod_err, :v_men_err) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_UPDATE_TBCIERRES ( :1  ,  :2  ,  :3  ,  :4  ,  :5  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"15TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(1,v_coduni); if (v_fecha == null) __sJT_st.setNull(2,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(2,v_fecha.intValue()); __sJT_st.setString(3,v_tipcierre); __sJT_st.setInt(4,v_cod_err); __sJT_st.setString(5,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_cod_err = __sJT_st.getInt(4); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(5); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:304^116*/

          //Si actualizacion de tabla de control de Tax se realiza bien seguir
          //en las actualizaciones del estado de cargue de las temporales
          if (v_cod_err == 0){
            sw = 1;
          }//No hubo error al actualizar tbcierres
          else{
            if(v_cont<3){
             //Intentar nuevamente actualizar
              v_cod_err = 0;
            }
          }
          v_cont++;
        }//Mientras la actualizacion de la tabla de control de tax no sea exitosa seguir
        if (v_cod_err==0){
          //Actualizar estado de cargue en la tabla temporal de retiros de Tax
          sw = 0;
          v_cont = 1;
          while ((sw==0) && (v_cont<=3)) {
             /*@lineinfo:generated-code*//*@lineinfo:324^14*/ /*//  *************************************************************//*//  #sql { call TBPBD_UPDATE_INTERF_RETIROS(:v_fecha, '1', :v_coduni, :v_cod_err, :v_men_err) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_UPDATE_INTERF_RETIROS( :1  , '1',  :2  ,  :3  ,  :4  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"16TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ if (v_fecha == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha.intValue()); __sJT_st.setString(2,v_coduni); __sJT_st.setInt(3,v_cod_err); __sJT_st.setString(4,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_cod_err = __sJT_st.getInt(3); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(4); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:324^114*/

            if (v_cod_err == 0) {
              sw = 1;
            }
            else{
              if(v_cont<3){
               //Intentar nuevamente actualizar
                v_cod_err = 0;
              }
            }
            v_cont++;
          }//Mientras la actualizacion del estado de cargue de retiros no sea exitosa seguir
          if (v_cod_err==0){
            //Actualizar estado de cargue en la tabla Temporal de Distribuccion de Tax
            sw = 0;
            v_cont = 1;
            while ((sw==0) && (v_cont<=3)) {
               /*@lineinfo:generated-code*//*@lineinfo:342^16*/ /*//  *************************************************************//*//  #sql { call TBPBD_UPDATE_INTERF_DISTR(:v_fecha, '1', :v_coduni, :v_cod_err, :v_men_err) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_UPDATE_INTERF_DISTR( :1  , '1',  :2  ,  :3  ,  :4  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"17TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ if (v_fecha == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha.intValue()); __sJT_st.setString(2,v_coduni); __sJT_st.setInt(3,v_cod_err); __sJT_st.setString(4,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_cod_err = __sJT_st.getInt(3); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(4); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:342^114*/

              if (v_cod_err == 0) {
                sw = 1;
              }
              else{
                if(v_cont<3){
                  //Intentar nuevamente actualizar
                  v_cod_err = 0;
                }
              }
              v_cont++;
            }//Mientras la actualizacion del estado de cargue de distribucion no sea exitosa seguir
            if (v_cod_err == 0){
              /*@lineinfo:generated-code*//*@lineinfo:356^15*/ /*//  *************************************************************//*//  #sql { call TBPBD_COMMIT(:v_cod_err, :v_men_err ) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_COMMIT( :1  ,  :2   )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"18TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setInt(1,v_cod_err); __sJT_st.setString(2,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_cod_err = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(2); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:356^75*/
              if (v_cod_err == 0){
                if (v_proceso == 3){
                  //session.setAttribute("s_cierre","3");
                
                    //Se almacena información de resultado en LOG de TAX
                    v_mensajeLog = "Advertencia: No se efectuó su cierre actual pues el último proceso de cierre realizado no fue exitoso. Se realizó nuevamente este cierre con tipo Parcial. Proceso de Cierre Anterior Exitoso. Numero total de registros cargados: " +v_total_reg;
                    /*@lineinfo:generated-code*//*@lineinfo:363^21*/ /*//  *************************************************************//*//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG', to_date(:v_fecha,'RRRR-MM-DD'), 'P1', :v_mensajeLog, to_char(:v_total_reg), 'MFUND', 0) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG', to_date( :1  ,'RRRR-MM-DD'), 'P1',  :2  , to_char( :3  ), 'MFUND', 0)\n; END;"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"19TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); /*// set IN parameters*/ if (v_fecha == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha.intValue()); __sJT_st.setString(2,v_mensajeLog); __sJT_st.setInt(3,v_total_reg); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:363^152*/
                    /*@lineinfo:generated-code*//*@lineinfo:364^21*/ /*//  *************************************************************//*//  #sql { COMMIT };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:364^33*/
                
                  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Advertencia: No se efectuó su cierre actual pues el último proceso de cierre realizado no fue exitoso. Se realizó nuevamente este cierre con tipo Parcial. Proceso de Cierre Anterior Exitoso. Numero total de registros cargados: "+v_total_reg,false));
                  out.println("<pre>");
                  //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
                  out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
                  out.println("</pre>");
                  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                  out.close();
                }//Si proceso fue anterior v_proceso = 3
                else{
                     
                     //Se almacena información de resultado en LOG de TAX
                      if(v_tipcierre.equals("P"))
                      {
                             v_mensajeLog = "Cierre Parcial Exitoso: Numero total de registros cargados: "+v_total_reg;
                      }
                      if(v_tipcierre.equals("T"))
                      {
                             v_mensajeLog = "Cierre Total Exitoso: Numero total de registros cargados: "+v_total_reg;
                      }    
                      /*@lineinfo:generated-code*//*@lineinfo:385^23*/ /*//  *************************************************************//*//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG', to_date(:v_fecha,'RRRR-MM-DD'), 'P1', :v_mensajeLog, to_char(:v_total_reg), 'MFUND', 0) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG', to_date( :1  ,'RRRR-MM-DD'), 'P1',  :2  , to_char( :3  ), 'MFUND', 0)\n; END;"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"20TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); /*// set IN parameters*/ if (v_fecha == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha.intValue()); __sJT_st.setString(2,v_mensajeLog); __sJT_st.setInt(3,v_total_reg); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:385^154*/
                      /*@lineinfo:generated-code*//*@lineinfo:386^23*/ /*//  *************************************************************//*//  #sql { COMMIT };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:386^35*/
                    
                  //session.setAttribute("s_cierre","3");
                  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Proceso de Cierre Exitoso. Numero total de registros cargados: "+v_total_reg,false));
                  out.println("<pre>");
                  //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
                  out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
                  out.println("</pre>");
                  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                  out.close();
                }//Si proceso no fue anterior v_proceso != 3
              }//No hubo error en el commit;
              else{
                //session.setAttribute("s_cierre","3");
                  
                //Se almacena información de resultado en LOG de TAX
                v_mensajeLog = "Error al hacer commit de la actualizacion de tablas de control "+v_men_err;
                /*@lineinfo:generated-code*//*@lineinfo:403^17*/ /*//  *************************************************************//*//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG', to_date(:v_fecha,'RRRR-MM-DD'), 'P1', :v_mensajeLog, to_char(:v_total_reg), 'MFUND', 0) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG', to_date( :1  ,'RRRR-MM-DD'), 'P1',  :2  , to_char( :3  ), 'MFUND', 0)\n; END;"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"21TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); /*// set IN parameters*/ if (v_fecha == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha.intValue()); __sJT_st.setString(2,v_mensajeLog); __sJT_st.setInt(3,v_total_reg); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:403^148*/
                /*@lineinfo:generated-code*//*@lineinfo:404^17*/ /*//  *************************************************************//*//  #sql { COMMIT };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:404^29*/  
                  
                out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Error al hacer commit de la actualizacion de tablas de control "+v_men_err,false));
                out.println("<pre>");
                //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
                out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
                out.println("</pre>");
                out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                out.close();
              }//hubo error en el commit;
            }//No hubo error al actualizar estado de cargue en distrib
            else{
              /*@lineinfo:generated-code*//*@lineinfo:416^15*/ /*//  *************************************************************//*//  #sql { ROLLBACK  };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:416^31*/
              //session.setAttribute("s_cierre","3");
                
                //Se almacena información de resultado en LOG de TAX
                v_mensajeLog = "Error al actualizar el estado de cargue de Distribucción de Fondos "+v_men_err;
                /*@lineinfo:generated-code*//*@lineinfo:421^17*/ /*//  *************************************************************//*//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG', to_date(:v_fecha,'RRRR-MM-DD'), 'P1', :v_mensajeLog, to_char(:v_total_reg), 'MFUND', 0) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG', to_date( :1  ,'RRRR-MM-DD'), 'P1',  :2  , to_char( :3  ), 'MFUND', 0)\n; END;"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"22TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); /*// set IN parameters*/ if (v_fecha == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha.intValue()); __sJT_st.setString(2,v_mensajeLog); __sJT_st.setInt(3,v_total_reg); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:421^148*/
                /*@lineinfo:generated-code*//*@lineinfo:422^17*/ /*//  *************************************************************//*//  #sql { COMMIT };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:422^29*/    
                
              out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Error al actualizar el estado de cargue de Distribucción de Fondos "+v_men_err,false));
              out.println("<pre>");
              //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
              out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
              out.println("</pre>");
              out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
              out.close();
            }
          }//No hubo error al actualizar el estado de cargue de retiros
          else{
            /*@lineinfo:generated-code*//*@lineinfo:434^13*/ /*//  *************************************************************//*//  #sql { ROLLBACK  };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:434^29*/
            //session.setAttribute("s_cierre","3");
            
            //Se almacena información de resultado en LOG de TAX
            v_mensajeLog = "Error al actualizar el estado de cargue de retiros "+v_men_err;
            /*@lineinfo:generated-code*//*@lineinfo:439^13*/ /*//  *************************************************************//*//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG', to_date(:v_fecha,'RRRR-MM-DD'), 'P1', :v_mensajeLog, to_char(:v_total_reg), 'MFUND', 0) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG', to_date( :1  ,'RRRR-MM-DD'), 'P1',  :2  , to_char( :3  ), 'MFUND', 0)\n; END;"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"23TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); /*// set IN parameters*/ if (v_fecha == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha.intValue()); __sJT_st.setString(2,v_mensajeLog); __sJT_st.setInt(3,v_total_reg); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:439^144*/
            /*@lineinfo:generated-code*//*@lineinfo:440^13*/ /*//  *************************************************************//*//  #sql { COMMIT };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:440^25*/    
                  
            out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Error al actualizar el estado de cargue de retiros "+v_men_err,false));
            out.println("<pre>");
            //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
            out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
            out.println("</pre>");
            out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
            out.close();
          }//Hubo error al actualizar el estado de cargue de retiros
        }//No hubo error al actualizar tbcierres
        else{
          /*@lineinfo:generated-code*//*@lineinfo:452^11*/ /*//  *************************************************************//*//  #sql { ROLLBACK  };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:452^27*/
          //session.setAttribute("s_cierre","3");
          
        //Se almacena información de resultado en LOG de TAX
        v_mensajeLog = "Error al actualizar el estado de cargue de Temporal de Retiros de Taxbenefit "+v_men_err;
        /*@lineinfo:generated-code*//*@lineinfo:457^9*/ /*//  *************************************************************//*//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG', to_date(:v_fecha,'RRRR-MM-DD'), 'P1', :v_mensajeLog, to_char(:v_total_reg), 'MFUND', 0) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG', to_date( :1  ,'RRRR-MM-DD'), 'P1',  :2  , to_char( :3  ), 'MFUND', 0)\n; END;"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"24TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_TIPO_CIERRE",theSqlTS); /*// set IN parameters*/ if (v_fecha == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha.intValue()); __sJT_st.setString(2,v_mensajeLog); __sJT_st.setInt(3,v_total_reg); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:457^140*/
        /*@lineinfo:generated-code*//*@lineinfo:458^9*/ /*//  *************************************************************//*//  #sql { COMMIT };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:458^21*/    
            
          out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Error al actualizar el estado de cargue de Temporal de Retiros de Taxbenefit "+v_men_err,false));
          out.println("<pre>");
          //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
          out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
          out.println("</pre>");
          out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
          out.close();
        }//Hubo error al actualizar tbcierres
      }//Realizar actualizaciones de tablas de control de Tax sw=1

   }
   catch(Exception ex){
     String v_menex = "";
   String error = ex.toString();
   if(ex.equals("java.sql.SQLException: found null connection context"))
   {
    v_menex = "Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.";
   }
   else if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
      {
        v_menex = "No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.";
      }
      else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
           {
             v_menex = "Se reinicio la base de datos por favor ingrese nuevamente.";
           }
            else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_menex = "Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.";
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                      v_menex =  "Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.";
                     }

                     else
                     {
                       v_menex = "Mensaje de error: "+ex;
                
                     }
     //session = null;// .putValue("s_cierre","3");
     out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Error sql_tipo_cierre: "+v_menex,false));
     out.println("<pre>");
     //out.println("<center> <input type='button' value='Regresar' Onclick=window.location='TBPKT_CIERRE.TBS_CIERRE';></center>");
     out.println("<center> <input type='button' value='Regresar' ONCLICK=history.go(-1);></center>");
     out.println("</pre>");
     out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
     out.close();
   }
}

}