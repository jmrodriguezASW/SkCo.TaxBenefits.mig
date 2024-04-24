/*@lineinfo:filename=TBCL_EliminarMulti*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND;

import sqlj.runtime.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.NumberFormat;

import sqlj.runtime.ref.DefaultContext ;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

/**Clase que muestra las solicitudes de retiro no procesadss por multifund y permite eliminarlas.*/
public class TBCL_EliminarMulti extends Object
{
 /**Iterator datos retiros.*/
 /*@lineinfo:generated-code*//*@lineinfo:18^2*/ /*//  *************************************************************//*//  SQLJ iterator declaration:*//*//  *************************************************************/ public class i_retiro extends sqlj.runtime.ref.ResultSetIterImpl implements sqlj.runtime.NamedIterator { public i_retiro(sqlj.runtime.profile.RTResultSet resultSet) throws java.sql.SQLException { super(resultSet); RET_CONSECUTIVONdx = findColumn("RET_CONSECUTIVO"); RET_FECHA_PROCESONdx = findColumn("RET_FECHA_PROCESO"); RET_FECHA_EFECTIVANdx = findColumn("RET_FECHA_EFECTIVA"); RET_VALORNdx = findColumn("RET_VALOR"); RET_VALOR_BRUTONdx = findColumn("RET_VALOR_BRUTO"); PRC_RETIRO_TOTALNdx = findColumn("PRC_RETIRO_TOTAL"); CON_NOMBRESNdx = findColumn("CON_NOMBRES"); CON_APELLIDOSNdx = findColumn("CON_APELLIDOS"); m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet(); } private oracle.jdbc.OracleResultSet m_rs; public int RET_CONSECUTIVO() throws java.sql.SQLException { int __sJtmp = m_rs.getInt(RET_CONSECUTIVONdx); if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp; } private int RET_CONSECUTIVONdx; public java.sql.Date RET_FECHA_PROCESO() throws java.sql.SQLException { return (java.sql.Date)m_rs.getDate(RET_FECHA_PROCESONdx); } private int RET_FECHA_PROCESONdx; public java.sql.Date RET_FECHA_EFECTIVA() throws java.sql.SQLException { return (java.sql.Date)m_rs.getDate(RET_FECHA_EFECTIVANdx); } private int RET_FECHA_EFECTIVANdx; public double RET_VALOR() throws java.sql.SQLException { double __sJtmp = m_rs.getDouble(RET_VALORNdx); if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp; } private int RET_VALORNdx; public double RET_VALOR_BRUTO() throws java.sql.SQLException { double __sJtmp = m_rs.getDouble(RET_VALOR_BRUTONdx); if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp; } private int RET_VALOR_BRUTONdx; public String PRC_RETIRO_TOTAL() throws java.sql.SQLException { return (String)m_rs.getString(PRC_RETIRO_TOTALNdx); } private int PRC_RETIRO_TOTALNdx; public String CON_NOMBRES() throws java.sql.SQLException { return (String)m_rs.getString(CON_NOMBRESNdx); } private int CON_NOMBRESNdx; public String CON_APELLIDOS() throws java.sql.SQLException { return (String)m_rs.getString(CON_APELLIDOSNdx); } private int CON_APELLIDOSNdx; } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:18^221*/

 /**Variable tipo iterator datos retiro*/
 i_retiro v_retiro;

 /**Procedimiento local que consulta a la base de datos la informaci�n del retiro a eliminar.*/
 public void TBPL_Eliminar(PrintWriter out,HttpSession session,HttpServletRequest request,String nuevaCadena )
 {
  /**Instancias de clase*/
  STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();/**Instancia de la clase TBCL_GenerarBaseHTML*/
  TBCL_Validacion       i_valusu = new TBCL_Validacion ();/**Instancia de la clase TBCL_Validacion*/
  //TBCL_ConexionSqlj    i_conexion = new TBCL_ConexionSqlj();
  try
  {
   /**Leer de archivo connection.properties url,usuario y paswword a la base de datos.*/
   String[] v_valusu = new String[3];
   v_valusu=i_valusu.TBFL_ValidarUsuario();
   /**Realizar conexion a la base de datos*/
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   DefaultContext ctx4 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
   DefaultContext.setDefaultContext(ctx4);
   //i_conexion.TBCL_ConexionBaseDatos();
   /**Definicion de variables*/
   /**Tipo boolean*/
   boolean v_encontro2 = false;/**Validar si se encuantran datos*/
   boolean v_check     = true;/**Validar que el primer dato quede por default chequeado*/
   boolean v_nopintar  = true;/**Validar que al tomar el primer dato se coja informaci�n del contrato*/

   /**Tipo String*/
   String v_coduni     = "";/** Variable c�digo unidad*/
   String v_codusu     = "";/** Variable codigo tipo de usuario*/
   String v_nombre     = "";/** Variable nombre del afiliado*/
   String v_apellidos  = "";/** Variable apellidos del afiliado*/
   String v_contra     = "";/** Variable numero del contrato*/
   String v_pro        = "";/** Variable c�digo del producto*/
   String v_tusu       = "";/** Variable tipo usuario as400*/
   String v_tuni       = "";/** Variable c�digo unidad as400*/
   String v_pintar     = "";/** Dibujar cabeza p�gina de respuesta*/
   String v_pie        = "";/** Dibujar pie de p�gibna de respueata*/
   String  v_tipoci    = "";
   String  v_hora      = "";
   String  v_horacierre = "";
   java.sql.Date v_fecha = new java.sql.Date(4);
    /**Tipo double*/
   double v_valorretiro= 0;
   /**Verificar que las variables de session no expiren*/


   if((java.lang.String)session.getAttribute("s_contrato") != null ||(java.lang.String)session.getAttribute("s_producto")!= null)
   {
    /**Capturar variables de session*/
    v_contra     = (java.lang.String)session.getAttribute("s_contrato");/**Tomar contrato*/
    v_pro        = (java.lang.String)session.getAttribute("s_producto");/**Tomar producto*/
    v_tusu       =(java.lang.String)session.getAttribute("s_tipousu");/**Tomar Tipo de usuario*/
    v_tuni       =(java.lang.String)session.getAttribute("s_unidad");/**Tomar unidad de proceso*/

    /*@lineinfo:generated-code*//*@lineinfo:74^5*/ /*//  *************************************************************//*//  #sql v_codusu = { values(TBFBD_REFERENCIAS(:v_tusu,'UTU')) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBFBD_REFERENCIAS( :2  ,'UTU') \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND.TBCL_EliminarMulti",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_tusu); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_codusu = (String)__sJT_st.getString(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:74^61*/
    /*@lineinfo:generated-code*//*@lineinfo:75^5*/ /*//  *************************************************************//*//  #sql v_coduni = { values(TBFBD_REFERENCIAS(:v_tuni,'UUP')) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBFBD_REFERENCIAS( :2  ,'UUP') \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND.TBCL_EliminarMulti",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_tuni); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_coduni = (String)__sJT_st.getString(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:75^61*/
    session.removeAttribute("s_unidad_proceso");
    session.setAttribute("s_unidad_proceso",(java.lang.Object)v_coduni);

    if(!v_codusu.equals("XXXXXX"))
    {//if usuario
     /**Si la unidad de proceso es valida*/
     if(!v_coduni.equals("XXXXXX"))
     {//if unidad
      /**Consultar tipo de cierr para la unidad deproceso*/
      /*@lineinfo:generated-code*//*@lineinfo:85^7*/ /*//  *************************************************************//*//  #sql v_tipoci = { values(TB_FTIPO_CIERRE(:v_coduni)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TB_FTIPO_CIERRE( :2  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND.TBCL_EliminarMulti",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_coduni); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_tipoci = (String)__sJT_st.getString(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:85^58*/
      if(v_tipoci.equals("T"))
      { //if cierre
       /**Es cierre total entonces la fecha efectiva del retiro es proxima fecha habil*/
       /*@lineinfo:generated-code*//*@lineinfo:89^8*/ /*//  *************************************************************//*//  #sql v_fecha = { values(TB_FFECHA_SIGUIENTE(1)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TB_FFECHA_SIGUIENTE(1) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND.TBCL_EliminarMulti",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.DATE); } /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_fecha = (java.sql.Date)__sJT_st.getDate(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:89^54*/
      }//cierre
      else
      {
       /**Si el cierre es parcial o aun no se ha hecho ningun tipo de cierre para el dia de hoy
       * se averigua que hora de cierre hay para la unidad y tipo de usuario*/
       /*@lineinfo:generated-code*//*@lineinfo:95^8*/ /*//  *************************************************************//*//  #sql { select to_char(sysdate,'HH24MI') FROM DUAL };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); oracle.jdbc.OracleResultSet __sJT_rs = null; try { String theSqlTS = "select to_char(sysdate,'HH24MI')  FROM DUAL"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND.TBCL_EliminarMulti",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.setFetchSize(2); } /*// execute query*/ __sJT_rs = __sJT_ec.oracleExecuteQuery(); if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount()); if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO(); /*// retrieve OUT parameters*/ v_hora = (String)__sJT_rs.getString(1); if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO(); } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:95^68*/
       /*@lineinfo:generated-code*//*@lineinfo:96^8*/ /*//  *************************************************************//*//  #sql v_horacierre = { values(TB_FHORA_CIERRE(:v_pro,:v_coduni,:v_codusu)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TB_FHORA_CIERRE( :2  , :3  , :4  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND.TBCL_EliminarMulti",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_pro); __sJT_st.setString(3,v_coduni); __sJT_st.setString(4,v_codusu); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_horacierre = (String)__sJT_st.getString(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:96^80*/
       if(!v_horacierre.equals("ERROR"))
       {
       if( new Integer(v_hora).intValue() > new Integer(v_horacierre).intValue())
       {
        /**Si se ha pasado la hora de cierre la fecha efectiva del retiro es proxima fecha habil*/
        /*@lineinfo:generated-code*//*@lineinfo:102^9*/ /*//  *************************************************************//*//  #sql v_fecha = { values(TB_FFECHA_SIGUIENTE(1)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TB_FFECHA_SIGUIENTE(1) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND.TBCL_EliminarMulti",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.DATE); } /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_fecha = (java.sql.Date)__sJT_st.getDate(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:102^55*/

       }
       else
       {
        /**Queda el dia habil*/
        /*@lineinfo:generated-code*//*@lineinfo:108^9*/ /*//  *************************************************************//*//  #sql v_fecha = { values(TB_FFECHA_SIGUIENTE(0)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TB_FFECHA_SIGUIENTE(0) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND.TBCL_EliminarMulti",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.DATE); } /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_fecha = (java.sql.Date)__sJT_st.getDate(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:108^55*/

       }
      }
      else
      {
       /**Si no esta parametrizada la hora de cierre para la unidad de proceso*/
       v_pintar=    i_pagina.TBFL_CABEZA("Eliminar Solicitud de Retiro No Procesada","Eliminar Solicitud de Retiro No Procesada","","<center>No esta parametrizada la hora de cierre para el tipo de usuario "+v_tusu+"</center>",false);
       out.println(""+v_pintar+"");
       v_pie = i_pagina.TBFL_PIE;
       out.println("<br>");
       out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-1)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
       out.println(""+v_pie+"");
       out.close();
      }
     }
     }
    else
    {/**C�digo de unidad de proceso invalido*/
     v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro" ,"","<center>La Unidad de Proceso "+v_tuni+" es desconocido por el sistema.</center>",false);
     out.println(""+v_pintar+"");
     v_pie = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println("<br>");
     out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-1)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
     out.println(""+v_pie+"");
     out.close();
    }
   }
   else
   {/**C�digo tipo de usuario invalido*/
     v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>El tipo de usuariop "+v_tusu+" es desconocido por el sistema.</center>",false);
     out.println(""+v_pintar+"");
     v_pie = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-1)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
     out.println(""+v_pie+"");
     out.close();
   }

    String v_fecha_eliminacion = v_fecha.toString();
    session.setAttribute("s_fechaeliminacion", (java.lang.Object)v_fecha_eliminacion);

    /**Seleccionar informaci�n de retiros no procesadas por multifund*/
    /*@lineinfo:generated-code*//*@lineinfo:152^5*/ /*//  *************************************************************//*//  #sql v_retiro = { SELECT RET_CONSECUTIVO*//*//                              ,RET_FECHA_PROCESO*//*//                              ,RET_FECHA_EFECTIVA*//*//                              ,RET_VALOR*//*//                              ,RET_VALOR_BRUTO*//*//                              ,PRC_RETIRO_TOTAL*//*//                              ,CON_NOMBRES*//*//                              ,CON_APELLIDOS*//*//                          FROM tbretiros*//*//                               ,tbproductos_conceptos*//*//                               ,tbcontratos*//*//                         WHERE RET_CON_PRO_CODIGO            = :v_pro*//*//                           AND RET_CON_NUMERO                = :v_contra*//*//                           AND RET_REF_ESTADO                = 'SER005'*//*//                           AND RET_CON_PRO_CODIGO            = PRC_PRO_CODIGO*//*//                           AND RET_COT_REF_TIPO_TRANSACCION  = PRC_COT_REF_TIPO_TRANSACCION*//*//                           AND RET_COT_REF_CLASE_TRANSACCION = PRC_COT_REF_CLASE_TRANSACCION*//*//                           AND RET_CON_NUMERO                = CON_NUMERO*//*//                       };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "SELECT RET_CONSECUTIVO\n                            ,RET_FECHA_PROCESO\n                            ,RET_FECHA_EFECTIVA\n                            ,RET_VALOR\n                            ,RET_VALOR_BRUTO\n                            ,PRC_RETIRO_TOTAL\n                            ,CON_NOMBRES\n                            ,CON_APELLIDOS\n                        FROM tbretiros\n                             ,tbproductos_conceptos\n                             ,tbcontratos\n                       WHERE RET_CON_PRO_CODIGO            =  :1  \n                         AND RET_CON_NUMERO                =  :2  \n                         AND RET_REF_ESTADO                = 'SER005'\n                         AND RET_CON_PRO_CODIGO            = PRC_PRO_CODIGO\n                         AND RET_COT_REF_TIPO_TRANSACCION  = PRC_COT_REF_TIPO_TRANSACCION\n                         AND RET_COT_REF_CLASE_TRANSACCION = PRC_COT_REF_CLASE_TRANSACCION\n                         AND RET_CON_NUMERO                = CON_NUMERO"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"8TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND.TBCL_EliminarMulti",theSqlTS); /*// set IN parameters*/ __sJT_st.setString(1,v_pro); __sJT_st.setString(2,v_contra); /*// execute query*/ v_retiro = new TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND.TBCL_EliminarMulti.i_retiro(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"8TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND.TBCL_EliminarMulti",null)); } finally { __sJT_ec.oracleCloseQuery(); } } /*//  *************************************************************/ 

















                     /*@lineinfo:user-code*//*@lineinfo:170^21*/

    /**P�gina de respuesta*/
    v_pintar=    i_pagina.TBFL_CABEZA("Eliminar Solicitud de Retiro No Procesada","Eliminar Solicitud de Retiro No Procesada","TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND.TBS_EliminarNoProcesado","",true,"","");
                                                                                                                               
    out.println(""+v_pintar+"");

    while(v_retiro.next())
    {
     /**Tomar por primera vez los datos del afiliado*/
     if(v_nopintar)
     {
      v_nombre= v_retiro.CON_NOMBRES().trim();

      session.removeAttribute("s_nombres");
      session.setAttribute("s_nombres",(java.lang.Object)v_nombre);
      v_apellidos = v_retiro.CON_APELLIDOS().trim();
      session.removeAttribute("s_apellidos");
      session.setAttribute("s_apellidos",(java.lang.Object)v_apellidos);
      /*Cambio para manejo de referencia unica 2009/03/30 MOS */
      String v_contrato_unif = "";
      /*@lineinfo:generated-code*//*@lineinfo:191^7*/ /*//  *************************************************************//*//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_pro,:v_contra)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND.TBCL_EliminarMulti",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_pro); __sJT_st.setString(3,v_contra); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_contrato_unif = (String)__sJT_st.getString(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:191^80*/
      out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
      out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nombre+"  <b> Apellidos </b>"+v_apellidos+" </CENTER></font>");
      out.println("<br>");
      out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
      out.println("<tr><td class=\"td11\" colspan= 7 ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Solicitudes de Retiros </b></font></center></font></td></tr>");
      out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Fecha Proceso</b></center></font></td>");
      out.println("<td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Fecha Efectiva</b></center></font></td>");
      out.println("<td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Valor del Retiro</b></center></font></td></tr>");
      v_nopintar = false;
     }
     /**Si se tiene un retiro total se toma el valor bruto del retiro*/
     if(v_retiro.PRC_RETIRO_TOTAL().equals("S"))
     {
      v_valorretiro = v_retiro.RET_VALOR_BRUTO();
     }
     else/**Si se tiene un retiro parcial se toma el valor del retiro*/
     {
      v_valorretiro = v_retiro.RET_VALOR();
     }
     /**Chequear el primero por default*/
     if(v_check)
     {
      out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><input type =radio name=v_conret value= "+v_retiro.RET_CONSECUTIVO()+"  checked>"+v_retiro.RET_FECHA_PROCESO()+"</font></td>");
      out.println("<td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+v_retiro.RET_FECHA_EFECTIVA()+"</font></td>");
      out.println("<td align=right ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_valorretiro)+"</font></td></tr>");
      v_encontro2 =true;
      v_check = false;
     }
     else
     {
      out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><input type =radio name=v_conret value= "+v_retiro.RET_CONSECUTIVO()+"  >"+v_retiro.RET_FECHA_PROCESO()+"</font></td>");
      out.println("<td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+v_retiro.RET_FECHA_EFECTIVA()+"</font></td>");
      out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_valorretiro)+"</font></td></tr>");
      v_encontro2 =true;
     }
    }
    v_retiro.close();
    /**No se encuantran solicitudes en estado SER005*/
    if(v_encontro2==false)
    {
     out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
     out.println("<tr><td class=\"td11\" colspan= 7 ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Solicitudes de Retiros </b></font></center></font></td></tr>");
     out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Fecha Proceso</b></center></font></td>");
     out.println("<td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Fecha Efectiva</b></center></font></td>");
     out.println("<td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Valor del Retiro</b></center></font></td></tr>");
     out.println("<tr><td  ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></center></td>");
     out.println("<td><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></td></center></tr>");
     out.println("<tr><td colspan= 5><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>No se encontraron Solicitudes de retiro para eliminar</font></center></td><tr>");
     out.println("</table></center>");
     out.println("<br>");
     out.println("<center><input type=button value='Cancelar' onclick=' history.go(-1)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
     v_pie = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println(""+v_pie+"");
     out.close();
    }
    out.println("</table></center>");
    out.println("<PRE>");
    out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
    out.println("<center><input type=submit value='Aceptar'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
    v_pie = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println(""+v_pie+"");
    out.close();
   }
   else
   {/**Se termina session*/
    v_pintar=    i_pagina.TBFL_CABEZA(" Eliminar Solicitud de Retiro No Procesada","Error al Eliminar Solicitud de Retiro No Procesada","","<center>Error de comunicaci�n no se tiene conexi�n con el servidor web,por favor intente de nuevo.</center>",false);
    out.println(""+v_pintar+"");
    v_pie = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-1)'></center>");
    out.println(""+v_pie+"");
    out.close();
   }
   /**Cierra conexion*/
  //i_conexion.TBCL_DesconectarBaseDatos();
  ctx4.getConnection().close();
  }
  catch(Exception ex)
  {/**Manejo de errores*/
  String v_menex = "";
    String error = ex.toString();
   if(ex.equals("java.sql.SQLException: found null connection context"))
   {
    v_menex = "Error de comunicaci�n no se tiene conexi�n con el servidor web,por favor intente de nuevo.";
   }
   else if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
      {
        v_menex = "No se tiene comunicaci�n con el servidor de datos  por favor ingrese nuevamente.";
      }
      else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
           {
             v_menex = "Se reinicio la base de datos por favor ingrese nuevamente.";
           }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_menex = "Error momentaneo de comunicaci�n con el servidor de datos, por favor intente entrar de nuevo a la opci�n.";
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_menex =  "Error momentaneo de comunicaci�n con el servidor Web, por favor intente entrar de nuevo a la opci�n.";
                     }
                     else
                     {
                       v_menex = "Mensaje de error: "+ex;
                     }
   String v_pintar=    i_pagina.TBFL_CABEZA (" Eliminar Solicitud de Retiro No Procesada","Error al Eliminar Solicitud de Retiro No Procesada","","<center>"+v_menex+"</center>",false);
   out.println(""+v_pintar+"");
   out.println("<BR>");
   out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
   String v_pie = i_pagina.TBFL_PIE;
   out.println("<br>");
   out.println("<br>");
   out.println(""+v_pie+"");
   out.close();
  }
 }
}