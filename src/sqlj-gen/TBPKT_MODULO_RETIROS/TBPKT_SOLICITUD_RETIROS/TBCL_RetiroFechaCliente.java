/*@lineinfo:filename=TBCL_RetiroFechaCliente*//*@lineinfo:user-code*//*@lineinfo:1^1*/
package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import sqlj.runtime.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

/*
 Modificacion:
 Se elimina el import debido a que la conexion
 al AS400 se hace desde la base de datos
 import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;
*/

import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_FECHAS.*;
import sqlj.runtime.ref.DefaultContext ;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import java.util.Date;
import java.util.Vector;
import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.*;
/**Clase que muestra al usuario la fecha efectiva y  de proceso del retio,tambien
*permite escojer el tipo de retiro a realizar.*/
public class TBCL_RetiroFechaCliente extends HttpServlet
{
 /**Iterator de la informaci�n del contrato.*/
 /*@lineinfo:generated-code*//*@lineinfo:29^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_cumple2
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_cumple2(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    CON_NOMBRESNdx = findColumn("CON_NOMBRES");
    CON_APELLIDOSNdx = findColumn("CON_APELLIDOS");
    v_fecha_cancelacionNdx = findColumn("v_fecha_cancelacion");
    pro_retiro_minimoNdx = findColumn("pro_retiro_minimo");
    PRO_DIAS_CANJENdx = findColumn("PRO_DIAS_CANJE");
    CON_REF_TIPO_IDENTIFICACIONNdx = findColumn("CON_REF_TIPO_IDENTIFICACION");
    CON_NUMERO_IDENTIFICACIONNdx = findColumn("CON_NUMERO_IDENTIFICACION");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String CON_NOMBRES()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_NOMBRESNdx);
  }
  private int CON_NOMBRESNdx;
  public String CON_APELLIDOS()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_APELLIDOSNdx);
  }
  private int CON_APELLIDOSNdx;
  public String v_fecha_cancelacion()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(v_fecha_cancelacionNdx);
  }
  private int v_fecha_cancelacionNdx;
  public double pro_retiro_minimo()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(pro_retiro_minimoNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int pro_retiro_minimoNdx;
  public int PRO_DIAS_CANJE()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(PRO_DIAS_CANJENdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int PRO_DIAS_CANJENdx;
  public String CON_REF_TIPO_IDENTIFICACION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_REF_TIPO_IDENTIFICACIONNdx);
  }
  private int CON_REF_TIPO_IDENTIFICACIONNdx;
  public String CON_NUMERO_IDENTIFICACION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_NUMERO_IDENTIFICACIONNdx);
  }
  private int CON_NUMERO_IDENTIFICACIONNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:35^74*/
 /**Iterator de los tipos de retiro.*/
 /*@lineinfo:generated-code*//*@lineinfo:37^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_tipo2
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_tipo2(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    COT_DESCRIPCIONNdx = findColumn("COT_DESCRIPCION");
    COT_REF_TIPO_TRANSACCIONNdx = findColumn("COT_REF_TIPO_TRANSACCION");
    COT_REF_CLASE_TRANSACCIONNdx = findColumn("COT_REF_CLASE_TRANSACCION");
    PRC_RETIRO_PENALIZADONdx = findColumn("PRC_RETIRO_PENALIZADO");
    PRC_TRASLADONdx = findColumn("PRC_TRASLADO");
    PRC_RETIRO_TOTALNdx = findColumn("PRC_RETIRO_TOTAL");
    v_diasmenorNdx = findColumn("v_diasmenor");
    v_diasmayorNdx = findColumn("v_diasmayor");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String COT_DESCRIPCION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(COT_DESCRIPCIONNdx);
  }
  private int COT_DESCRIPCIONNdx;
  public String COT_REF_TIPO_TRANSACCION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(COT_REF_TIPO_TRANSACCIONNdx);
  }
  private int COT_REF_TIPO_TRANSACCIONNdx;
  public String COT_REF_CLASE_TRANSACCION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(COT_REF_CLASE_TRANSACCIONNdx);
  }
  private int COT_REF_CLASE_TRANSACCIONNdx;
  public String PRC_RETIRO_PENALIZADO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRC_RETIRO_PENALIZADONdx);
  }
  private int PRC_RETIRO_PENALIZADONdx;
  public String PRC_TRASLADO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRC_TRASLADONdx);
  }
  private int PRC_TRASLADONdx;
  public String PRC_RETIRO_TOTAL()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRC_RETIRO_TOTALNdx);
  }
  private int PRC_RETIRO_TOTALNdx;
  public String v_diasmenor()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(v_diasmenorNdx);
  }
  private int v_diasmenorNdx;
  public String v_diasmayor()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(v_diasmayorNdx);
  }
  private int v_diasmayorNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:37^239*/

 /**Variable tipo iterator i_cumple2*/
 i_cumple2 v_cum;
 /**Variable tipo iterator i_tipo2*/
 i_tipo2 v_tipo;

 /**Procedimiento muestra al usuario las fechas del retiro y consulta los tipos de retiro para el cliente*/
 public void TBPL_FechaCliente(PrintWriter out,HttpSession session,HttpServletRequest request,String nuevaCadena,String v_contrato,String v_producto,String v_unidad ,String v_tipousu)
 {
  /**Instancias de clase*/
  STBCL_GenerarBaseHTML   i_pagina = new STBCL_GenerarBaseHTML();/**Instancia de la clase TBCL_GenerarBaseHTML*/
  TBCL_Validacion     i_valusu = new  TBCL_Validacion ();/**Instancia de la clase TBCL_Validacion*/
  //TBCL_ConexionSqlj    i_conexion = new TBCL_ConexionSqlj();/**Instancia de la clase TBCL_ConexionSqlj*/

  //TBCL_FuncionesAs400    i_fondos = new TBCL_FuncionesAs400 ();/**Instancia de la clase TBCL_FuncionesAs400*/

  TBCL_Fecha              i_fecha = new TBCL_Fecha();/**Instancia de la clase TBCL_Fecha*/
  try
  {
   String[] v_valusu = new String[3];
   v_valusu=i_valusu.TBFL_ValidarUsuario();
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   Connection t_tax =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
   DefaultContext ctx9 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
   DefaultContext.setDefaultContext(ctx9);
   /**Conectar base de datos*/


   /**Definicion de variables*/
   /**Tipo Date*/
   java.sql.Date v_fecha = new java.sql.Date(4);/**Fecha de proceso del retiro*/

   /**Tipo String */
   String v_valmin    = "";/**Variable valor minimo del retiro*/
   String v_codusu    = "";/**Variable c�digo tipo de usuario Taxbenefits*/
   String v_coduni    = "";/**Variable c�digo unidad Taxbenefits*/
   String v_feccan    = "";/**Variable fecha cancelaci�n del contrato*/
   String v_hora      = "";/**Variable hora actual*/
   String v_nombre    = "";/**Variable nombre del afiliado*/
   String v_apellidos = "";/**Variable apellido del afiliado*/
   String v_sistema   = "";/**Variable nombre del sistema multifund*/
   String v_usumfund  = "";/**Variable usuario multifund*/
   String v_passmfund = "";/**Variable password de usuario multifund*/
   String v_libreria  = "";/**Variable nombre de libreria multifund*/
   String v_tipoci    = "";/**Variable tipo cierre de unidad*/
   String v_horacie   = "";/**Variable hora cierre de unidad*/
   String v_bloqueo   = "";/**Variable bloqueo de egresos del contrato*/
   String v_canje2    = "";/**Variable d�as de canje de un aporte*/
   String v_tipoiden  = "";/**Variable tipo de identificaci�n afiliado*/
   String v_identificacion = "";/**Variable n�mero de identificaci�n*/
   String v_pie       = "";/**Variable dibujar final p�gina*/
   String v_pintar    = "";   /**Variable dibujar inicio p�gina*/
   String v_fechasol  = "";/**Fecha de proceso del retiro en string*/
   //String v_contra    = (java.lang.String)session.getAttribute("s_contrato");/**Variable numero del contrato*/
  // String v_pro       = (java.lang.String)session.getAttribute("s_producto");/**Variable c�digo del producto*/
  // String v_tusu      = (java.lang.String)session.getAttribute("s_usuario");/**Variable c�digo tipo de usuario As400*/
  //String v_tuni      = (java.lang.String)session.getAttribute("s_unidad");/**Variable c�digo unidad As400*/
   /**Tipo int */
   int v_resmulti     = 0;/**Indicador de exito o fracaso */
   /**Tipo boolean */
   boolean v_validafecha = false;/**Indicador de que se encuentra fecha de proceso*/
   boolean v_validacontrato = false;/**Indicador de que se encuntran datos para el contrato*/

   /**Validar tipo de uisuario y unidad de proceso*/
   /*@lineinfo:generated-code*//*@lineinfo:102^4*/

//  ************************************************************
//  #sql v_codusu = { values(TBFBD_REFERENCIAS(:v_tipousu,'UTU')) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_REFERENCIAS( :2  ,'UTU') \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_tipousu);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_codusu = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:102^63*/
   /*@lineinfo:generated-code*//*@lineinfo:103^4*/

//  ************************************************************
//  #sql v_coduni = { values(TBFBD_REFERENCIAS(:v_unidad,'UUP')) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_REFERENCIAS( :2  ,'UUP') \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_unidad);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_coduni = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:103^62*/
   /**Variable de session tipo de uisuario y unidad de proceso */
   session.removeAttribute("s_usuariotax");
   session.removeAttribute("s_unidadtax");
   session.setAttribute("s_usuariotax",(java.lang.Object)v_codusu);
   session.setAttribute("s_unidadtax",(java.lang.Object)v_coduni);
   /**Si el tipo de usuario es valido*/
   if(!v_codusu.equals("XXXXXX"))
   {//if usuario
    /**Si la unidad de proceso es valida*/
    if(!v_coduni.equals("XXXXXX"))
    {
     /**Consultar el tipo de cierre para la unidad*/
     /*@lineinfo:generated-code*//*@lineinfo:116^6*/

//  ************************************************************
//  #sql v_tipoci = { values(TB_FTIPO_CIERRE(:v_coduni)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FTIPO_CIERRE( :2  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_coduni);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_tipoci = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:116^57*/
     if(v_tipoci.equals("T"))
     {
      /**Es cierre total entonces la fecha efectiva del retiro es proxima fecha habil*/
      /*@lineinfo:generated-code*//*@lineinfo:120^7*/

//  ************************************************************
//  #sql v_fecha = { values(TB_FFECHA_SIGUIENTE(1)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FFECHA_SIGUIENTE(1) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.DATE);
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_fecha = (java.sql.Date)__sJT_st.getDate(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:120^53*/
      v_validafecha = true;
     }
     else
     {
      /**Si el cierre es parcial o aun no se ha hecho ningun tipo de cierre para el dia de hoy
      * se averigua que hora de cierre hay para la unidad y tipo de usuario*/
      /*@lineinfo:generated-code*//*@lineinfo:127^7*/

//  ************************************************************
//  #sql { select to_char(sysdate,'HH24MI') FROM DUAL };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "select to_char(sysdate,'HH24MI')  FROM DUAL";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   v_hora = (String)__sJT_rs.getString(1);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:127^67*/
      /*@lineinfo:generated-code*//*@lineinfo:128^7*/

//  ************************************************************
//  #sql v_horacie = { values(TB_FHORA_CIERRE(:v_producto,:v_coduni,:v_codusu)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FHORA_CIERRE( :2  , :3  , :4  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_producto);
   __sJT_st.setString(3,v_coduni);
   __sJT_st.setString(4,v_codusu);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_horacie = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:128^81*/
      if(!v_horacie.equals("ERROR"))
      {
       if( new Integer(v_hora).intValue() > new Integer(v_horacie).intValue())
       {
        /**Si se ha pasado la hora de cierre la fecha efectiva del retiro es proxima fecha habil*/
        /*@lineinfo:generated-code*//*@lineinfo:134^9*/

//  ************************************************************
//  #sql v_fecha = { values(TB_FFECHA_SIGUIENTE(1)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FFECHA_SIGUIENTE(1) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.DATE);
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_fecha = (java.sql.Date)__sJT_st.getDate(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:134^55*/
        v_validafecha = true;
       }
       else
       {
        /**Queda el dia habil*/
        /*@lineinfo:generated-code*//*@lineinfo:140^9*/

//  ************************************************************
//  #sql v_fecha = { values(TB_FFECHA_SIGUIENTE(0)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FFECHA_SIGUIENTE(0) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.DATE);
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_fecha = (java.sql.Date)__sJT_st.getDate(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:140^55*/
        v_validafecha = true;
       }
      }
      else
      {
       /**Si no esta parametrizada la hora de cierre para la unidad de proceso*/
       v_validafecha = false;
       v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>No esta parametrizada la hora de cierre para el tipo de usuario "+v_tipousu+"</center>",false);
       out.println(""+v_pintar+"");
       v_pie = i_pagina.TBFL_PIE;
       out.println("<br>");
       out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-1)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
       out.println(""+v_pie+"");
       out.close();
      }
     }
     /**Se encontro fecha de proceso*/
     if(v_validafecha)
     {
      v_fechasol = v_fecha.toString();
      /**Variable de session fecha proceso y efectiva */
      session.removeAttribute("s_fecpro");
      session.removeAttribute("s_fecefectiva");
      session.setAttribute("s_fecpro",(java.lang.Object)v_fechasol);
      session.setAttribute("s_fecefectiva",(java.lang.Object)v_fechasol);
      /**Consultar datos del contrato*/
      /*@lineinfo:generated-code*//*@lineinfo:167^7*/

//  ************************************************************
//  #sql v_cum = { SELECT  CON_NOMBRES
//                             ,CON_APELLIDOS
//                             ,to_char(CON_FECHA_CANCELACION,'YYYY-MM-DD') as v_fecha_cancelacion
//                             ,pro_retiro_minimo
//                             ,PRO_DIAS_CANJE
//                             ,CON_REF_TIPO_IDENTIFICACION
//                             ,CON_NUMERO_IDENTIFICACION
//                        FROM tbproductos,tbcontratos
//                       WHERE PRO_CODIGO=CON_PRO_CODIGO
//                         AND CON_PRO_CODIGO = :v_producto
//                         AND CON_NUMERO = :v_contrato
//                       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT  CON_NOMBRES\n                           ,CON_APELLIDOS\n                           ,to_char(CON_FECHA_CANCELACION,'YYYY-MM-DD') as v_fecha_cancelacion\n                           ,pro_retiro_minimo\n                           ,PRO_DIAS_CANJE\n                           ,CON_REF_TIPO_IDENTIFICACION\n                           ,CON_NUMERO_IDENTIFICACION\n                      FROM tbproductos,tbcontratos\n                     WHERE PRO_CODIGO=CON_PRO_CODIGO\n                       AND CON_PRO_CODIGO =  :1  \n                       AND CON_NUMERO =  :2 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"8TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_producto);
   __sJT_st.setString(2,v_contrato);
   // execute query
   v_cum = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente.i_cumple2(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"8TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:178^21*/


      while (v_cum.next())
      {
       v_nombre= v_cum.CON_NOMBRES().trim();
       session.removeAttribute("s_nombres");
       session.setAttribute("s_nombres",(java.lang.Object)v_nombre);
       v_apellidos = v_cum.CON_APELLIDOS().trim();
       session.removeAttribute("s_apellidos");
       session.setAttribute("s_apellidos",(java.lang.Object)v_apellidos);
       v_feccan = v_cum.v_fecha_cancelacion();
       session.removeAttribute("s_feccan");
       session.setAttribute("s_feccan",(java.lang.Object)v_feccan);
       v_valmin = new Double(v_cum.pro_retiro_minimo()).toString();
       session.removeAttribute("s_valmin");
       session.setAttribute("s_valmin",(java.lang.Object)v_valmin);
       v_canje2 =  new Integer(v_cum.PRO_DIAS_CANJE()).toString();
       session.removeAttribute("s_diascanje");
       session.setAttribute("s_diascanje",(java.lang.Object)v_canje2);
       v_tipoiden = v_cum.CON_REF_TIPO_IDENTIFICACION();
       session.removeAttribute("s_tipoidentificacion");
       session.setAttribute("s_tipoidentificacion",(java.lang.Object)v_tipoiden);
       v_identificacion = v_cum.CON_NUMERO_IDENTIFICACION();
       session.removeAttribute("s_identificacion");
       session.setAttribute("s_identificacion",(java.lang.Object)v_identificacion);
       v_validacontrato = true;
      }
      v_cum.close();
      /**Si no hay error al consultar contrato*/
      if (v_validacontrato)
      {//1
       /**Si el contrato no esta cancelado*/
       if(v_feccan == null)
       {
        /**Consultar  variables de conexion del as400*/
        /*@lineinfo:generated-code*//*@lineinfo:214^9*/

//  ************************************************************
//  #sql v_resmulti = { values(TB_FREFERENCIAS_MULTI( :v_sistema
//                                                         ,:v_usumfund
//                                                         ,:v_passmfund
//                                                         ,:v_libreria
//                                                         )
//                                   )
//                             };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FREFERENCIAS_MULTI(  :2  \n                                                       , :3  \n                                                       , :4  \n                                                       , :5  \n                                                       )\n                                  \n                          \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente",theSqlTS);
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
   __sJT_st.setString(3,v_usumfund);
   __sJT_st.setString(4,v_passmfund);
   __sJT_st.setString(5,v_libreria);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_resmulti = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_sistema = (String)__sJT_st.getString(2);
   v_usumfund = (String)__sJT_st.getString(3);
   v_passmfund = (String)__sJT_st.getString(4);
   v_libreria = (String)__sJT_st.getString(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:220^27*/
         /**Variable de session de conexion del as400*/
        session.removeAttribute("s_sistema");
        session.removeAttribute("s_usumfund");
        session.removeAttribute("s_passmfund");
        session.removeAttribute("s_libreria");
        session.setAttribute("s_sistema",(java.lang.Object)v_sistema);
        session.setAttribute("s_usumfund",(java.lang.Object)v_usumfund);
        session.setAttribute("s_passmfund",(java.lang.Object)v_passmfund);
        session.setAttribute("s_libreria",(java.lang.Object)v_libreria);
        /**Consultar bloqueo de egresos para el contrato*/
        //v_bloqueo = i_fondos.TBFL_BloqueoEgresos(v_contrato,v_sistema,v_usumfund,v_passmfund,v_libreria);
        /*
        Modificacion:
        Se a�ade el procedimiento de invocacion a un procedimiento del AS400
        */

        CallableStatement cs = t_tax.prepareCall ( "{? = call TBCL_FUNCIONESAS400.TBFL_BloqueoEgresos(?,?,?,?,?)}");
        cs.registerOutParameter(1,Types.CHAR);
        cs.setString(2,v_contrato);
        cs.setString(3,v_sistema);
        cs.setString(4,v_usumfund);
        cs.setString(5,v_passmfund);
        cs.setString(6,v_libreria);
        cs.executeUpdate();
        v_bloqueo = cs.getString(1);
        cs.close();
        t_tax.close();

        /* Final de la modificacion */

        /**Si el contrano no tiene bloqueo de egresos*/
        if(v_bloqueo.equals("N"))
        {//3
         /**Dibbujar pagina de respuesta*/
         v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Solicitud de Retiro","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_Retiros","",true,"modulo_retiros.js","return validar_fecha(this)");
         out.println(""+v_pintar+"");
         /*Cambio para manejo de referencia unica 2009/03/30 MOS */
         String v_contrato_unif = "";
         /*@lineinfo:generated-code*//*@lineinfo:259^10*/

//  ************************************************************
//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_producto,:v_contrato)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"10TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_producto);
   __sJT_st.setString(3,v_contrato);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_contrato_unif = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:259^90*/
         out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_producto+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
         out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nombre+"  <b> Apellidos </b>"+v_apellidos+" </CENTER></font>");
         out.println("<br>");
         out.println("<pre>");
         out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Fecha de Proceso de la Solicitud de Retiro </b>              "+v_fechasol+"        </font>");
         out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Fecha Efectiva de la Solicitud de Retiro                     </b> "+v_fechasol+"  <input name=obligatoriov_fecefectiva type=hidden  value='"+v_fechasol+"'></font>");
         out.println("</pre>");
         out.println("</pre>");
         out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Tipo de Retiro</b>                   <select name=v_retiro ></font>");
         /*@lineinfo:generated-code*//*@lineinfo:269^10*/

//  ************************************************************
//  #sql v_tipo = { SELECT COT_DESCRIPCION
//                               ,COT_REF_TIPO_TRANSACCION
//                               ,COT_REF_CLASE_TRANSACCION
//                               ,PRC_RETIRO_PENALIZADO
//                               ,PRC_TRASLADO
//                               ,PRC_RETIRO_TOTAL
//                               ,TO_CHAR(PRC_DIAS_MENOR) v_diasmenor
//                               ,TO_CHAR(PRC_DIAS_MAYOR) v_diasmayor
//                           FROM TBPRODUCTOS_CONCEPTOS,TBCONCEPTOS_TRANSACCION
//                          WHERE PRC_PRO_CODIGO                = :v_producto
//                            AND PRC_COT_REF_TRANSACCION       = 'STR001'
//                            AND PRC_COT_REF_TRANSACCION       = COT_REF_TRANSACCION
//                            AND PRC_COT_REF_TIPO_TRANSACCION  = COT_REF_TIPO_TRANSACCION
//                            AND PRC_COT_REF_CLASE_TRANSACCION = COT_REF_CLASE_TRANSACCION
//                            AND TBPRODUCTOS_CONCEPTOS.PRC_ORIGEN_TAXB ='S'
//                            AND TBPRODUCTOS_CONCEPTOS.PRC_TRASLADO    = 'N'
//                       ORDER BY COT_DESCRIPCION };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT COT_DESCRIPCION\n                             ,COT_REF_TIPO_TRANSACCION\n                             ,COT_REF_CLASE_TRANSACCION\n                             ,PRC_RETIRO_PENALIZADO\n                             ,PRC_TRASLADO\n                             ,PRC_RETIRO_TOTAL\n                             ,TO_CHAR(PRC_DIAS_MENOR) v_diasmenor\n                             ,TO_CHAR(PRC_DIAS_MAYOR) v_diasmayor\n                         FROM TBPRODUCTOS_CONCEPTOS,TBCONCEPTOS_TRANSACCION\n                        WHERE PRC_PRO_CODIGO                =  :1  \n                          AND PRC_COT_REF_TRANSACCION       = 'STR001'\n                          AND PRC_COT_REF_TRANSACCION       = COT_REF_TRANSACCION\n                          AND PRC_COT_REF_TIPO_TRANSACCION  = COT_REF_TIPO_TRANSACCION\n                          AND PRC_COT_REF_CLASE_TRANSACCION = COT_REF_CLASE_TRANSACCION\n                          AND TBPRODUCTOS_CONCEPTOS.PRC_ORIGEN_TAXB ='S'\n                          AND TBPRODUCTOS_CONCEPTOS.PRC_TRASLADO    = 'N'\n                     ORDER BY COT_DESCRIPCION";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"11TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_producto);
   // execute query
   v_tipo = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente.i_tipo2(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"11TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFechaCliente",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:285^46*/
                 out.println("               <option VALUE ='  '>                         ");
         while (v_tipo.next())
         {
          out.println("     <option value='"+v_tipo.COT_REF_TIPO_TRANSACCION()+v_tipo.COT_REF_CLASE_TRANSACCION()+v_tipo.PRC_RETIRO_PENALIZADO()+v_tipo.PRC_TRASLADO()+v_tipo.PRC_RETIRO_TOTAL()+v_tipo.v_diasmenor()+"-"+v_tipo.v_diasmayor()+"'>"+ v_tipo.COT_DESCRIPCION());
         }
         v_tipo.close();
         out.println("</OPTION></SELECT></FONT>");
         out.println("<br>");
         out.println("<br>");
         out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
         out.println("<center><input type=submit value='Aceptar'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
         v_pie = i_pagina.TBFL_PIE;
         out.println(""+v_pie+"");
         out.println("<br>");
         out.close();
        }
        else
        {/**El contrato tiene bloqueo de egresos o hubo error al consultar en el as400*/
         if(v_bloqueo.equals("Y"))
         {
          v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>El contrato "+v_contrato+" tiene bloqueo de egresos, no es posible realizar la solicitud.</center>",false);
          out.println(""+v_pintar+"");
          v_pie = i_pagina.TBFL_PIE;
          out.println("<br>");
          out.println("<br>");
          out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-1)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
          out.println(""+v_pie+"");
          out.close();
         }
         else
         {
          v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>No se establecio comunicaci�n con el AS/400.</center>",false);
          out.println(""+v_pintar+"");
          v_pie = i_pagina.TBFL_PIE;
          out.println("<br>");
          out.println("<br>");
          out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-1)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
          out.println(""+v_pie+"");
          out.close();
         }
        }
       }
       else
       {/**Si el contrato tiene fecha de cancelaci�n*/
        v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>El contrato "+v_contrato+" ,tiene fecha de cancelaci�n "+v_feccan+".</center>",false);
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
      {/**Si no se encuantran datos para el contrato*/
       v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>El contrato "+v_contrato+" ,no existe en el sistema.</center>",false);
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
     {/**Error al consultar fecha de proceso del retiro*/
      v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro" ,"","<center>Error al consultar fecha de proceso para la solicitud de retiro.</center>",false);
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
    {/**C�digo de unidad de proceso invalido*/
     v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro" ,"","<center>La Unidad de Proceso "+v_unidad+" es desconocido por el sistema.</center>",false);
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
     v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>El tipo de usuariop "+v_tipousu+" es desconocido por el sistema.</center>",false);
     out.println(""+v_pintar+"");
     v_pie = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-1)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
     out.println(""+v_pie+"");
     out.close();
   }
   /**Desconectar base de datos*/
   //i_conexion.TBCL_DesconectarBaseDatos();

  ctx9.getConnection().close();
  }
  catch(Exception ex)
  {/**Manejo de errores*/
   String v_pintar="";
   String error = ex.toString();
   if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
   {
    v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>No se tiene comunicaci�n con el servidor de datos  por favor ingrese nuevamente.</center>",false);
   }
   else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
        {
         v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
        }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_pintar = i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicaci�n con el servidor de datos, por favor intente entrar de nuevo a la opci�n.</center>",false);
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_pintar =  i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicaci�n con el servidor Web, por favor intente entrar de nuevo a la opci�n.</center>",false);
                     }
                  else
                  {
                   v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                  }
   out.println(""+v_pintar+"");
   out.println("<BR>");
   out.println("<center><input type=button value='Cancelar' onclick=' history.go(-1)'></center>");
   String v_pie = i_pagina.TBFL_PIE;
   out.println("<br>");
   out.println("<br>");
   out.println(""+v_pie+"");
   out.close();
  }
 }
}/*@lineinfo:generated-code*/