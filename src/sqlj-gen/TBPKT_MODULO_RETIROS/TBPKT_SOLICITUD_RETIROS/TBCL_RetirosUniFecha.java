/*@lineinfo:filename=TBCL_RetirosUniFecha*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import sqlj.runtime.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

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

//import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.*;

/**Clase que muestra al usuario la fecha efectiva y  de proceso del retiro,tambien
*permite escojer el tipo de retiro a realizar.*/
public class TBCL_RetirosUniFecha extends HttpServlet
{
 /**Iterator de la información del contrato.*/
 /*@lineinfo:generated-code*//*@lineinfo:36^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_cumple
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_cumple(sqlj.runtime.profile.RTResultSet resultSet)
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

/*@lineinfo:user-code*//*@lineinfo:42^74*/

 /**Iterator de la información del tipo de usuarion.*/
 /*@lineinfo:generated-code*//*@lineinfo:45^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_tipo
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_tipo(sqlj.runtime.profile.RTResultSet resultSet)
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
    FECHA_EFECTIVANdx = findColumn("FECHA_EFECTIVA");
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
  public String FECHA_EFECTIVA()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(FECHA_EFECTIVANdx);
  }
  private int FECHA_EFECTIVANdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:45^261*/
 i_cumple v_cum;/**Variable tipo iterator i_cumple*/
 i_tipo v_tipo;/**Variable tipo iterator i_tipo*/

 /** Cambio agregado por Marcela Ortiz Sandoval 
  * Iterator de la información de los dias de canje.*/
 /*@lineinfo:generated-code*//*@lineinfo:51^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_dias_c
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_dias_c(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    dias_canjeNdx = findColumn("dias_canje");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public int dias_canje()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(dias_canjeNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int dias_canjeNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:51^53*/
 i_dias_c v_pac;
 
 /**Procedimiento muestra al usuario las fechas del retiro y consulta los tipod de retiro */
 public void TBPL_FechaRetiro(PrintWriter out,HttpSession session,HttpServletRequest request,String nuevaCadena,String v_contrato,String v_producto,String v_unidad ,String v_tipousu)
 {
  /**Instancias de clase*/
  STBCL_GenerarBaseHTML i_pagina = new STBCL_GenerarBaseHTML(); /**Instancia de la clase TBCL_GenerarBaseHTML*/
  //TBCL_Validacion      i_valusu = new     TBCL_Validacion ();/**Instancia de la clase TBCL_Validacion */

/*
Modificacion:
Se debe reemplazar la creacion del objeto
TBCL_FuncionesAs400 ya que se sustituye por un llamado a
procedimiento de la base de datos

  TBCL_FuncionesAs400  i_fondos   = new TBCL_FuncionesAs400(); /* Instancia de la clase TBCL_FuncionesAs400()
*/

  TBCL_Fecha           i_fecha    = new TBCL_Fecha();/**Instancia de la clase TBCL_Fecha*/
  //TBCL_ConexionSqlj    i_conexion = new TBCL_ConexionSqlj();/**Instancia de la clase TBCL_ConexionSqlj*/
  TBCL_Validacion       i_valusu = new TBCL_Validacion();/**Instancia de la clase TBCL_Validacion*/
  try
  {
   String[] v_valusu = new String[3];
   v_valusu=i_valusu.TBFL_ValidarUsuario();
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   Connection t_tax =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);   
   DefaultContext ctx12 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
   DefaultContext.setDefaultContext(ctx12);
   /**Conextar bas de datos*/
   //i_conexion.TBCL_ConexionBaseDatos();

   /**Definicion de variables*/
   /**Tipo Date*/
   java.sql.Date v_fecha = new java.sql.Date(4);/**Fecha de proceso del retiro*/
   /**Tipo String*/
   String v_valmin    = "";/**Valor minimo del retiro*/
   String v_codusu    = "";/**Variable código tipo de usuario*/
   String v_coduni    = "";/**Variable código unodad*/
   String v_feccan    = "";/**Fecha de cancelación del contrato*/
   String v_hora      = "";/**Hora actual*/
   String v_nombre    = " ";/**Nombre del afiliado*/
   String v_apellidos = "";/**Apellido del afiliado*/
   String v_sistema   = "";/**Variable nombre del sistema multifund*/
   String v_usumfund  = "";/**Variable usuario multifund*/
   String v_passmfund = "";/**Variable password de usuario multifund*/
   String v_libreria  = "";/**Variable nombre de libreria multifund*/
   String v_tipoci    = "";/**Tipo de cierre de la unidad*/
   String v_horacie   = "";/**Hora cierre para la unidad y tipo de usuario*/
   String v_bloqueo   = "";/**Bloqueo de egresos*/
   String v_canje2    = "";/**Días de canje de los aportes*/
   String v_tipoiden  = "";/**tipo de identificación*/
   String v_identificacion = "";/**Número de identificación*/
   String v_pintar    = "";/**Dibujar inicio página*/
   String v_pie       = "";/**Dibujar final de página*/
   //String v_contra    = (java.lang.String)session.getAttribute("s_contrato");/**Número de contrato*/
   //String v_pro       = (java.lang.String)session.getAttribute("s_producto");/**Código de producto*/
   //String v_tusu      = (java.lang.String)session.getAttribute("s_usuario");/**Código Tipo de usuario*/
   String v_tuni      = (java.lang.String)session.getAttribute("s_unidad");/**Código unidad de proceso*/
      
   String v_coderr = "";
   String v_msgErr = "";
      
      
   String v_retiro        = "";/**Variable tipo retiro*/   
   String v_tipotranNuevoDisp  = "";/**Variable tipo de transacción Nuevo Disponible*/
   String v_fechaProceso = "";
   String v_fechaEfect = "";
   String v_fechaE = "";
   String v_horaCorte = "HCR001"; /**Codigo de tbreferencias para hora de Corte*/
      
      
   /*Agregado por Marcela Ortiz Sandoval 
    * 30/08/2007
    * Incluye parametros para manejo de contratos corporativos*/
   String permiteRetiros = (java.lang.String)session.getAttribute("permiteRetiros");
   String montoMinRetiro = (java.lang.String)session.getAttribute("montoMinRetiro");
   String saldoMinRetiro = (java.lang.String)session.getAttribute("saldoMinRetiro");
   String permiteCerrados = (java.lang.String)session.getAttribute("permiteCerrados");
   
   String fechaE1 = "";
   String fechaE2 = "";
   String fechaE3 = "";
   String fechaE4 = "";
   String fechaE5 = "";
   String fechaE6 = "";
   String fechaE7 = "";
   
   /* Agregado por Marcela Ortiz Sandoval 
    * 18/08/2009
    * Incluye parametro para manejo de retiro express */
   String permiteExpress = (java.lang.String)session.getAttribute("permiteExpress");
   
   if (permiteRetiros!=null){
     if (permiteRetiros.equals("N")){
       throw new Exception("El contrato NO esta autorizado para realizar retiros"); 
     }
   }
   /*Fin codigo agregado*/

   /*Agregado por Marcela Ortiz Sandoval 
    * 25/10/2007
    * Incluye parametro para manejo de fondos cerrados*/
    String bloqueoRetiroTotal = permiteCerrados;
 
    /*Fin codigo agregado*/
   
    /* Agregado por Marcela Ortiz Sandoval 
    * 18/08/2009
    * Incluye parametro para manejo de retiro express */
    String bloqueoRetiroExpress = permiteExpress;
    
    
   /**Tipo int*/
   int v_resmulti     = 0;/**Indicador función TB_FREFERENCIAS_MULTI*/
   /**Tipo boolean*/
   boolean v_validafecha    = false;/**Validar fecha*/
   boolean v_validacontrato = false;/**Validar contrato*/

   /**Validar tipo de usuario y unidad de proceso*/
   /*@lineinfo:generated-code*//*@lineinfo:172^4*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:172^63*/
   /*@lineinfo:generated-code*//*@lineinfo:173^4*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:173^62*/
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
    {//if unidad
     /**Consultar tipo de cierr para la unidad deproceso*/
     /*@lineinfo:generated-code*//*@lineinfo:186^6*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:186^57*/
     if(v_tipoci.equals("T"))
     { //if cierre
      /**Es cierre total entonces la fecha efectiva del retiro es proxima fecha habil*/
      /*@lineinfo:generated-code*//*@lineinfo:190^7*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:190^53*/
      v_validafecha = true;
     }//cierre
     else
     {
      /**Si el cierre es parcial o aun no se ha hecho ningun tipo de cierre para el dia de hoy
      * se averigua que hora de cierre hay para la unidad y tipo de usuario*/
      /*@lineinfo:generated-code*//*@lineinfo:197^7*/

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
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:197^67*/
      /*@lineinfo:generated-code*//*@lineinfo:198^7*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:198^81*/
      if(!v_horacie.equals("ERROR"))
      {
       if( new Integer(v_hora).intValue() > new Integer(v_horacie).intValue())
       {
        /**Si se ha pasado la hora de cierre la fecha efectiva del retiro es proxima fecha habil*/
        /*@lineinfo:generated-code*//*@lineinfo:204^9*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:204^55*/
        v_validafecha = true;
       }
       else
       {
        /**Queda el dia habil*/
        /*@lineinfo:generated-code*//*@lineinfo:210^9*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:210^55*/
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
      /**Variable de session fecha proceso */
      session.removeAttribute("s_fecpro");
      session.setAttribute("s_fecpro",(java.lang.Object)v_fecha.toString());
      /**Consultar datos del contrato*/
      /*@lineinfo:generated-code*//*@lineinfo:234^7*/

//  ************************************************************
//  #sql v_cum = { SELECT  CON_NOMBRES
//                            ,CON_APELLIDOS
//                            ,TO_CHAR(CON_FECHA_CANCELACION,'YYYY-MM-DD') as v_fecha_cancelacion
//                            ,pro_retiro_minimo
//                            ,PRO_DIAS_CANJE
//                            ,CON_REF_TIPO_IDENTIFICACION
//                            ,CON_NUMERO_IDENTIFICACION
//                        FROM tbproductos,tbcontratos
//                       WHERE PRO_CODIGO     = CON_PRO_CODIGO
//                         AND CON_PRO_CODIGO = :v_producto
//                         AND CON_NUMERO     = :v_contrato
//                      };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT  CON_NOMBRES\n                          ,CON_APELLIDOS\n                          ,TO_CHAR(CON_FECHA_CANCELACION,'YYYY-MM-DD') as v_fecha_cancelacion\n                          ,pro_retiro_minimo\n                          ,PRO_DIAS_CANJE\n                          ,CON_REF_TIPO_IDENTIFICACION\n                          ,CON_NUMERO_IDENTIFICACION\n                      FROM tbproductos,tbcontratos\n                     WHERE PRO_CODIGO     = CON_PRO_CODIGO\n                       AND CON_PRO_CODIGO =  :1  \n                       AND CON_NUMERO     =  :2 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"8TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_producto);
   __sJT_st.setString(2,v_contrato);
   // execute query
   v_cum = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha.i_cumple(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"8TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:245^20*/
                   
      //MIENTRAS SE ENCUENTREN DATOS
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
       v_validacontrato = true;
       v_tipoiden = v_cum.CON_REF_TIPO_IDENTIFICACION();
       session.removeAttribute("s_tipoidentificacion");
       session.setAttribute("s_tipoidentificacion",(java.lang.Object)v_tipoiden);
       v_identificacion = v_cum.CON_NUMERO_IDENTIFICACION();
       session.removeAttribute("s_identificacion");
       session.setAttribute("s_identificacion",(java.lang.Object)v_identificacion);
      }
      v_cum.close();
      
     /**Cambio agregado por Marcela Ortiz Sandoval 
       * Si el contrato es corporativo y existe algun acuerdo de confidencialidad se
       * se modifican los días de canje
       * */
      /*@lineinfo:generated-code*//*@lineinfo:279^7*/

//  ************************************************************
//  #sql v_pac = { select dias_canje from tbempresas_contratos, tbempresa_canje
//                     where emc_emp_grupo = emp_grupo 
//                     and emc_con_pro_codigo = :v_producto
//                     and emc_con_numero = :v_contrato
//                      };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "select dias_canje from tbempresas_contratos, tbempresa_canje\n                   where emc_emp_grupo = emp_grupo \n                   and emc_con_pro_codigo =  :1  \n                   and emc_con_numero =  :2 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"9TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_producto);
   __sJT_st.setString(2,v_contrato);
   // execute query
   v_pac = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha.i_dias_c(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"9TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:283^20*/
      while (v_pac.next())
      {
        v_canje2 =  new Integer(v_pac.dias_canje()).toString();
        session.removeAttribute("s_diascanje");
        session.setAttribute("s_diascanje",(java.lang.Object)v_canje2);
      }
      
      v_pac.close();
      
      /**Si no hay error al consultar contrato*/
      if (v_validacontrato)
      {//1
       /**Si el contrato no esta cancelado*/
       if(v_feccan == null)
       {
        /**Consultar  variables de conexion del as400*/
        /*@lineinfo:generated-code*//*@lineinfo:300^9*/

//  ************************************************************
//  #sql v_resmulti = { values(TB_FREFERENCIAS_MULTI(:v_sistema,:v_usumfund,:v_passmfund, :v_libreria)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FREFERENCIAS_MULTI( :2  , :3  , :4  ,  :5  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"10TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:300^130*/
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
        
   
    /**GAV 20150218 Se procede a agregar clase de validación de fecha efectiva*/   
    String rutaFisica = TBCL_REFERENCIAS.TBCL_CONSULTA("SMM003");
    String POLICY_FILE_LOCATION = rutaFisica+"antisamy-tinymce-1.4.4.xml"; // Path to policy file
    //String POLICY_FILE_LOCATION = "D://Oracle//Middlewaredev//utils//antisamy-tinymce-1.4.4.xml"; // Path to policy file
    AntiSamy as = new AntiSamy(); // INT20131108
    
    
    if(request.getParameter("obligatoriov_fecefectiva") != null ){
        try
            {
                CleanResults cr = as.scan(request.getParameter("obligatoriov_fecefectiva"), new File(POLICY_FILE_LOCATION));
                v_fechaEfect = cr.getCleanHTML();
                
                CleanResults cr1 = as.scan(request.getParameter("obligatoriov_fecproceso"), new File(POLICY_FILE_LOCATION));
                v_fechaProceso = cr1.getCleanHTML();
            }
            catch (Exception e)
            {
              e.printStackTrace();
            } //INT20131108
    }else
    {
        v_fechaEfect = "";
        v_fechaProceso = "";
    }
      session.removeAttribute("s_fecefectiva");
      session.setAttribute("s_fecefectiva",(java.lang.Object)v_fechaEfect );
      session.setAttribute("s_fecpro",(java.lang.Object)v_fechaProceso );
      
    /**Fin fecha efectiva*/  
        
        

CallableStatement cs = t_tax.prepareCall ( "{? = call TBCL_FUNCIONESAS400.TBFL_BLOQUEOEGRESOS(?,?,?,?,?)}");
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
         String v_contrato_unif = "";
         /*@lineinfo:generated-code*//*@lineinfo:368^10*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"11TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:368^90*/
         out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_producto+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
         out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nombre+"  <b> Apellidos </b>"+v_apellidos+" </CENTER></font>");
         out.println("<br>");
         out.println("<pre>");
         

         out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Fecha de Proceso de la Solicitud de Retiro                  </b><input name='obligatoriov_fecproceso' type=text  size = '10' maxlength = '10' OnFocus='this.blur()'>  </font>");
         out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Fecha Efectiva de la Solicitud de Retiro                     </b>  <input name='obligatoriov_fecefectiva' type=text  size = '10' maxlength = '10' readonly> </font>");
         out.println("</pre>");
         out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Tipo de Retiro</b> <select name=v_retiro onChange='fechaEfectiva(v_retiro.value, obligatoriov_fecproceso, obligatoriov_fecefectiva)'></font>");   

 
         /*@lineinfo:generated-code*//*@lineinfo:381^10*/

//  ************************************************************
//  #sql v_tipo = { SELECT COT_DESCRIPCION
//                               ,COT_REF_TIPO_TRANSACCION
//                               ,COT_REF_CLASE_TRANSACCION
//                               ,PRC_RETIRO_PENALIZADO
//                               ,PRC_TRASLADO
//                               ,PRC_RETIRO_TOTAL
//                               ,TO_CHAR(PRC_DIAS_MENOR) v_diasmenor
//                               ,TO_CHAR(PRC_DIAS_MAYOR) v_diasmayor
//                               ,tbf_obtener_fechas_x_tranx(PRC_PRO_CODIGO, COT_REF_TIPO_TRANSACCION, COT_REF_CLASE_TRANSACCION) FECHA_EFECTIVA
//                           FROM TBPRODUCTOS_CONCEPTOS,TBCONCEPTOS_TRANSACCION
//                          WHERE PRC_PRO_CODIGO                = :v_producto
//                            AND PRC_COT_REF_TRANSACCION       = 'STR001'
//                            AND PRC_COT_REF_TRANSACCION       = COT_REF_TRANSACCION
//                            AND PRC_COT_REF_TIPO_TRANSACCION  = COT_REF_TIPO_TRANSACCION
//                            AND PRC_COT_REF_CLASE_TRANSACCION = COT_REF_CLASE_TRANSACCION
//                            AND PRC_ORIGEN_TAXB               = 'S'
//                       ORDER BY COT_DESCRIPCION };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT COT_DESCRIPCION\n                             ,COT_REF_TIPO_TRANSACCION\n                             ,COT_REF_CLASE_TRANSACCION\n                             ,PRC_RETIRO_PENALIZADO\n                             ,PRC_TRASLADO\n                             ,PRC_RETIRO_TOTAL\n                             ,TO_CHAR(PRC_DIAS_MENOR) v_diasmenor\n                             ,TO_CHAR(PRC_DIAS_MAYOR) v_diasmayor\n                             ,tbf_obtener_fechas_x_tranx(PRC_PRO_CODIGO, COT_REF_TIPO_TRANSACCION, COT_REF_CLASE_TRANSACCION) FECHA_EFECTIVA\n                         FROM TBPRODUCTOS_CONCEPTOS,TBCONCEPTOS_TRANSACCION\n                        WHERE PRC_PRO_CODIGO                =  :1  \n                          AND PRC_COT_REF_TRANSACCION       = 'STR001'\n                          AND PRC_COT_REF_TRANSACCION       = COT_REF_TRANSACCION\n                          AND PRC_COT_REF_TIPO_TRANSACCION  = COT_REF_TIPO_TRANSACCION\n                          AND PRC_COT_REF_CLASE_TRANSACCION = COT_REF_CLASE_TRANSACCION\n                          AND PRC_ORIGEN_TAXB               = 'S'\n                     ORDER BY COT_DESCRIPCION";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"12TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_producto);
   // execute query
   v_tipo = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha.i_tipo(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"12TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosUniFecha",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:397^46*/
         out.println("               <option VALUE ='  '>                         ");
         
         while (v_tipo.next())
         {
              /* Agregado por Marcela Ortiz Sandoval 
               * 18/08/2009
               * Elimina el retiro express si no lo tiene permitido */
              if (bloqueoRetiroExpress.equals("N") && (v_tipo.COT_REF_TIPO_TRANSACCION().equals("UTT014")) ){
                  out.println("");
              }
              else{
                /*Agregado por Marcela Ortiz Sandoval 
                 * 25/10/2007
                 * Elimina opciones de retiro total si tiene fondos cerrados*/
                 if (bloqueoRetiroTotal!=null){
                   /*Agregar UTT012 Retiro total a tercero */
                   if (bloqueoRetiroTotal.equals("S") && (v_tipo.COT_REF_TIPO_TRANSACCION().equals("UTT002") || v_tipo.COT_REF_TIPO_TRANSACCION().equals("UTT003")|| v_tipo.COT_REF_TIPO_TRANSACCION().equals("UTT012"))){
                      out.println("");
                   }
                   else{
                      out.println("<option value='"+v_tipo.COT_REF_TIPO_TRANSACCION()+v_tipo.COT_REF_CLASE_TRANSACCION()+v_tipo.PRC_RETIRO_PENALIZADO()+v_tipo.PRC_TRASLADO()+v_tipo.PRC_RETIRO_TOTAL()+v_tipo.v_diasmenor()+"-"+v_tipo.v_diasmayor()+"-"+v_tipo.FECHA_EFECTIVA()+"'>"+ v_tipo.COT_DESCRIPCION());
                   }
                 }
                 else{
                    out.println("<option value='"+v_tipo.COT_REF_TIPO_TRANSACCION()+v_tipo.COT_REF_CLASE_TRANSACCION()+v_tipo.PRC_RETIRO_PENALIZADO()+v_tipo.PRC_TRASLADO()+v_tipo.PRC_RETIRO_TOTAL()+v_tipo.v_diasmenor()+"-"+v_tipo.v_diasmayor()+"-"+v_tipo.FECHA_EFECTIVA()+"'>"+ v_tipo.COT_DESCRIPCION());
                 }
                 /*Fin codigo agregado*/    
              }
         }
         v_tipo.close();
         out.println("</OPTION></SELECT></FONT>");
         out.println("<br>");
         out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
         out.println("<br>");
         out.println("<br>");
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
          v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>No se establecio comunicación con el AS/400.</center>",false);
          out.println(""+v_pintar+"");
          v_pie = i_pagina.TBFL_PIE;
          out.println("<br>");
          out.println("<br>");
          out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-1)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
          out.println(""+v_pie+"");
          out.close();
         }
        }
       }/**Si el contrato tiene fecha de cancelación*/
       else
       {
        v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>El contrato "+v_contrato+" ,tiene fecha de cancelación "+v_feccan+".</center>",false);
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
    {/**Código de unidad de proceso invalido*/
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
   {/**Código tipo de usuario invalido*/
    v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>El tipo de usuario "+v_tipousu+" es desconocido por el sistema.</center>",false);
    out.println(""+v_pintar+"");
    v_pie = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-1)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
    out.println(""+v_pie+"");
    out.close();
   }
   /**Desconectar base de datos*/
   ctx12.getConnection().close();
   
   //i_conexion.TBCL_DesconectarBaseDatos();
  }/**Manejo de errores*/
  catch(Exception ex)
  {
   String v_pintar="";
   String error = ex.toString();
   if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
   {
    v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.</center>",false);
   }
   else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
        {
         v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
        }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_pintar = i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.</center>",false);
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_pintar =  i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.</center>",false);
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