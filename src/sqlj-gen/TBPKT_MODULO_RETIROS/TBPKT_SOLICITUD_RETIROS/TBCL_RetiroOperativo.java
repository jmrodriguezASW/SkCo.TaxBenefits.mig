/*@lineinfo:filename=TBCL_RetiroOperativo*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;
import sqlj.runtime.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*

Modificacion:
Se elimina el import de TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*debido a que la
conexion al AS400 se hace desde la base de datos.
Se elimina el import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.* debido a que este componente
se invoca directamente en la base de datos.
Se importa el paquete TBPKT_IAS.

*/

import TBPKT_IAS.*;


import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_FECHAS.*;

import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.TBCL_FuncionesAs400;

import java.text.NumberFormat;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.*;

import java.util.Date;
import java.util.Vector;
import javax.servlet.http.*;
import sqlj.runtime.ref.DefaultContext ;

import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108


//import taxbenefits.proxy.BasicHttpBinding_IAvailableServiceClient;
//import taxbenefits.proxy.types.org.datacontract.schemas._2004._07.skco_available_fsl.Saldo;

/**Clase que muestra al usuario el saldo totaol y disponible, permite
 * capturar el valor a retirar y los diferentes párametros segun el tipo de retiro*/
public class TBCL_RetiroOperativo extends HttpServlet
{
 /**Iterator de tiempo del contrato*/
 /*@lineinfo:generated-code*//*@lineinfo:50^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_contrato
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_contrato(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    v_fechacontratoNdx = findColumn("v_fechacontrato");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double v_fechacontrato()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(v_fechacontratoNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int v_fechacontratoNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:50^63*/
 /**Iterator de código y descripción de afp*/
 /*@lineinfo:generated-code*//*@lineinfo:52^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_afp
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_afp(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    AFP_CODIGONdx = findColumn("AFP_CODIGO");
    AFP_DESCRIPCIONNdx = findColumn("AFP_DESCRIPCION");
    AFP_REF_TIPONdx = findColumn("AFP_REF_TIPO");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String AFP_CODIGO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(AFP_CODIGONdx);
  }
  private int AFP_CODIGONdx;
  public String AFP_DESCRIPCION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(AFP_DESCRIPCIONNdx);
  }
  private int AFP_DESCRIPCIONNdx;
  public String AFP_REF_TIPO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(AFP_REF_TIPONdx);
  }
  private int AFP_REF_TIPONdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:52^96*/
 /**Iterator de código de producto*/
 /*@lineinfo:generated-code*//*@lineinfo:54^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_producto
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_producto(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    PRO_CODIGONdx = findColumn("PRO_CODIGO");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String PRO_CODIGO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRO_CODIGONdx);
  }
  private int PRO_CODIGONdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:54^59*/
  /*@lineinfo:generated-code*//*@lineinfo:55^3*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_pencon
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_pencon(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    COO_COP_CODIGONdx = findColumn("COO_COP_CODIGO");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String COO_COP_CODIGO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(COO_COP_CODIGONdx);
  }
  private int COO_COP_CODIGONdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:55^61*/
 i_afp v_afp;/**Variable tipo iterator i_afp*/
 i_contrato v_contrato;/**Variable tipo iterator i_contrato*/
 i_producto v_productotraslado;/**Variable tipo iterator i_producto*/
 /**Variable tipo iterator código fondo*/
 i_pencon v_pencon;
 public void TBPL_SolicitudOperativo(PrintWriter out,HttpSession session,HttpServletRequest request,String nuevaCadena)
 {
  /**Instancias de clase*/
  //STBCL_GenerarBaseHTML i_pagina = new STBCL_GenerarBaseHTML;/**Instancia de la clase TBCL_GenerarBaseHTML*/
  /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_ValidarUsuario de la clase TBCL_Validacion, no es necesaria la instancia nueva*/ 
 //TBCL_Validacion i_valusu = new TBCL_Validacion(); 
 //TBCL_Validacion  i_valusu = new TBCL_Validacion()/**Instancia de la clase TBCL_Validacion*/
  TBCL_ConsultaClienteLista i_consultaC = new TBCL_ConsultaClienteLista(); /**Instancia de la clase TBCL_ConsultaClienteLista*/
  //TBCL_ConexionSqlj   i_conexion = new TBCL_ConexionSqlj();/**Instancia de la clase TBCL_ConexionSqlj*/

/*

Modificacion:
Se debe reemplazar la creacion del objeto
TBCL_FuncionesAs400 ya que se sustituye por un llamado a
procedimiento de la base de datos

  TBCL_FuncionesAs400   i_fondos = new TBCL_FuncionesAs400(); / **Instancia de la clase TBCL_FuncionesAs400

*/

  TBCL_Fecha i_fecha             = new TBCL_Fecha();/**Instancia de la clase TBCL_Fecha*/

/*

Modificacion:
Se debe reemplazar la creacion del objeto
TBCL_FuncionesAs400 ya que se sustituye por un llamado a
procedimiento de la base de datos

  SQL_CALCULO_VALOR_UNIDAD i_unidad =  new SQL_CALCULO_VALOR_UNIDAD(); / **Instancia de la clase TBCL_GenerarBaseHTML

*/

  try
  {
   String[] v_valusu = new String[3];
   v_valusu=TBCL_Validacion.TBFL_ValidarUsuario();
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   Connection t_tax =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
   DefaultContext ctx11 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
   DefaultContext.setDefaultContext(ctx11);
   /**Conectar base de datos*/
   //i_conexion.TBCL_ConexionBaseDatos();

   /**Definicion de variables*/
   /**Tipo double*/
   double v_saldispo      = 0;/**Saldo contrato bruto*/
   double v_saldisponeto  = 0;/**Saldo contrato neto*/
   double v_saldo         = 0;/**Saldo contrato Multifund*/
   double v_valmin        = 0;/**Valor minimo del retiro*/
   double v_salrenbruto   = 0;/**Saldo de rendimientos brutos*/
   double v_salrenneto    = 0;/**Saldo de rendimientos netos*/
   double[] v_valuni2     = new double[3];/**Valor unidad*/
   double v_salTotCap1    = 0;/**Saldo total de capital bruto*/
   double v_salTotConting = 0;/**Saldo total cuenta contingente*/
   double v_salTotRend1   = 0;/**Saldo total de rendimientos brutos*/
   double v_salTotRendN   = 0;/**Saldo total de rendimientos nuevos*/
   double v_salTotRendH   = 0;/**Saldo total de rendiminetos huerfanos*/
   double v_salTotCap2    = 0;/**Saldo total de capital neto*/
   double v_salTotRend2   = 0;/**Saldo total de rendimientos netos*/
   double v_valor_unidad  = 0;/**Valor unidad*/
   double v_fondos_cerrados=0;/**Saldo en fondos cerrados*/
   double v_garantias     = 0;/**Valor en garantiias por prestamos*/

   boolean  v_enconpenaliza = false;
   /**Tipo int*/
   int v_codcargo = 0;/**Indicador exito o fracaso de función TB_FDATOS_CARGOS*/
   int v_posicion = 0;/**Separador de cadena tipo de retiro*/
   int v_int1     = 0;/**Separador de cadena saldos disponibles*/
   int v_int2     = 0;/**Separador de cadena saldos disponibles*/
   int v_dim      = 0;/**Contador fondos*/
   int v_coderr   = 0;/**Codigo de error funcion TBPBD_SaldoContratoRet*/
   int v_calfec   = 0;
   int v_calproc  = 0;
   int v_calprocexpr = 0;
   int v_calfecexpr = 0;
   int v_calactexpr = 0;
   int v_aplicaRetTotExpr = 0; 
  
   /**Tipo boolean*/
   boolean v_infbanco  = true;/**Indicador de información de bancos*/
   boolean v_infbancoTercTitu = true; /**Indicador de información de bancos cuentas terceros y titulares*/
   boolean v_tipovUCT002  = false; /**Indicador tipo de valor para retiros UCT002*/ 
   /**Tipo String*/
   String v_penalizarretiro = "";
   String v_efe           = "";/**Fecha efectiva de retiro formato yyyymmdd*/
   String v_banco         = "";/**Cadena código banco y numero cuenta*/
   String v_msgErr        = "OK";/**Mensaje de error funcion TBPBD_SaldoContratoRet*/
   String v_retiro        = "";/**Variable tipo retiro*/
   String v_tipotran      = "";/**Variable tipo transacción*/
   String v_clasetran     = "";/**Variable Clase transacción*/
   String v_diasmenor     = "";/**Variable días menor*/
   String v_diasmayor     = "";/**Variable días mayor*/
   String v_penaliza      = "";/**Variable concepto de penalización*/
   String v_traslado      = "";/**Variable concepto retiro traslado*/
   String v_total         = "";/**Variable concepto retiro total*/
   String v_cargo1        = "";/**Variable tipo cargo 1*/
   String v_cargo2        = "";/**Variable tipo cargo 2*/
   String v_cargo3        = "";/**Variable tipo cargo 3*/
   String v_cargo4        = "";/**Variable tipo cargo 4*/
   String v_mensajecargo  = "";/**Mensaje de error funcion TB_FDATOS_CARGOS*/
   String v_nombre        = "";/**Variable nombre afiliado*/
   String v_apellidos     = "";/**Variable apellido afiliado*/
   String v_contra        = "";/**Variable numero contrato*/
   String v_pro           = "";/**Variable  código producto*/
   String v_fecefe        = "";/**Variable  fecha efectiva retiro*/
   String v_fecpro        = "";/**Variable  feha proceso retiro*/
   String v_sistema       = "";/**Variable nombre del sistema multifund.*/
   String v_usumfund      = "";/**Variable nombre Usuario multifund.*/
   String v_passmfund     = "";/**Variable nombre password de usuario multifund.*/
   String v_libreria      = "";/**Variable nombre libreria en multifund.*/
   String v_banconulo     = "";/**Variable código banco*/
   String v_cuentanulo    = "";/**Variable numero cuenta*/
   String v_nombannulo    = "";/**Variable nombre banco*/
   String v_ahocor        = "";/**Variable  indicador cuenta ahorro o corriente*/
   String v_banconuloTercTitu     = "";/**Variable código banco tercero y titular*/
   String v_cuentanuloTercTitu    = "";/**Variable numero cuenta tercero y titular*/
   String v_nombannuloTercTitu    = "";/**Variable nombre banco tercero y titular*/
   String v_ahocorTercTitu        = "";/**Variable  indicador cuenta ahorro o corriente tercero y titular*/
   String v_cum2          = "";/**Variable  tiempo contrato*/
   String v_pintar        = "";/**Variable  dibujar inicio pagina*/
   String v_pie           = "";/**Variable  dibujar final pagina*/
   String v_programa      = "";/**Variable  del programa del contrato*/
   String v_reformaTributaria = ""; /**Flag para identificar si debe mostrar las cuentas registradas en ley 1111 y 1607*/ 
   String v_reformaTributa = "MVR001"; /**Codigo para mostrar las secciones de Cuentas registradas en ley 1111 y 1607*/
   String v_fecha_actual = ""; /**Variable que almacena la fecha actual habil. */
   String v_fecha_retiroTotalExpress = "";

/******Cambio hecho por APC 2005-06-15 para manejar el nuevo retiro a terceros*/
   String v_tipocuentas   = "10";/**Variable que verifica si el retiro es a un tercero o no. 10 es el 
                                  *  código en el 400 para Primary Owner (default)*/
   String v_tipocuentasTitular   = "10"; 
   String v_nombreter     = ""; /**Variable que maneja el nombre del tercero en la cuenta*/
   String v_nombreterTercTitu     = ""; /**Variable que maneja el nombre del tercero en la cuenta tercero o titular*/
    String v_retExp     ="UTT014";
/***********Fin cambio APC 2005-06-15*****************/   

  /* Agregado por Marcela Ortiz Sandoval 
   * 30/08/2007
   * Incluye parametros para manejo de contratos corporativos */
   String montoMinRetiro  = "";/**Variable monto minimo para corporativos*/
   String saldoMinRetiro  = "";/**Variable saldo minimo para corporativos*/
   /*Fin codigo agregado primera parte*/  
   
   /* Agregado por Marcela Ortiz Sandoval 
   * 14/09/2009 */
   String esTercero  = "N";/**Variable para identificar operación a tercero*/
   //out.println("esTercero=" + esTercero);
   /*Fin codigo agregado*/  
   
   /**Verificar que las variables de session no expiren*/
   if((java.lang.String)session.getAttribute("s_contrato") != null || (java.lang.String)session.getAttribute("s_producto")!= null)
   {
    /**Capturar variables de session*/
    v_nombre    = (java.lang.String)session.getAttribute("s_nombres");
    v_apellidos = (java.lang.String)session.getAttribute("s_apellidos");
    v_contra    = (java.lang.String)session.getAttribute("s_contrato");
    v_pro       = (java.lang.String)session.getAttribute("s_producto");
    v_fecefe    = (java.lang.String)session.getAttribute("s_fecefectiva");
    v_fecpro    = (java.lang.String)session.getAttribute("s_fecpro");
    v_valmin    = new Double((java.lang.String)session.getAttribute("s_valmin")).doubleValue();
    v_sistema   = (java.lang.String)session.getAttribute("s_sistema");
    v_usumfund  = (java.lang.String)session.getAttribute("s_usumfund");
    v_passmfund = (java.lang.String)session.getAttribute("s_passmfund");
    v_libreria  = (java.lang.String)session.getAttribute("s_libreria");
    /* Agregado por Marcela Ortiz Sandoval 
     * 30/08/2007
     * Incluye parametros para manejo de contratos corporativos */
    montoMinRetiro = (java.lang.String)session.getAttribute("montoMinRetiro");
    saldoMinRetiro = (java.lang.String)session.getAttribute("saldoMinRetiro");
    double montoMinimo = 0;
    double saldoMinimo = 0;
    if(montoMinRetiro!=null && montoMinRetiro.length()>0){
      montoMinimo = Double.parseDouble(montoMinRetiro);
    }
    if(saldoMinRetiro!=null && saldoMinRetiro.length()>0){
      saldoMinimo = Double.parseDouble(saldoMinRetiro);
    }
    /*Fin codigo agregado segunda parte*/    
   
    /*@lineinfo:generated-code*//*@lineinfo:242^5*/

//  ************************************************************
//  #sql { SELECT TO_CHAR(to_date(tbf_getbusinessdate(0, SYSDATE), 'DD/MM/RR'), 'yyyy-mm-dd') DIA_HABIL
//      
//            FROM DUAL 
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT TO_CHAR(to_date(tbf_getbusinessdate(0, SYSDATE), 'DD/MM/RR'), 'yyyy-mm-dd') DIA_HABIL\n     \n          FROM DUAL";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   v_fecha_actual = (String)__sJT_rs.getString(1);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:245^5*/

    //***VALIDA QUE FECHA EFECTIVA INGRESADA SEA MAYOR O IGUAL A LA FECHA DEL DIA
    v_calfec       = v_fecha_actual.compareTo(v_fecefe); // v_fecefe.compareTo(v_fecpro);
    
    //***VALIDA QUE FECHA EFECTIVA SEA MENOR A LA FECHA DE PROCESO
    v_calproc      = v_fecefe.compareTo(v_fecpro);
    
    
    /**Validar fecha efectiva de retiro, deb de ser menor o igual a la fecha de proceso*/
    if(v_calproc <= 0)
    {
     if(v_calfec <= 0)/**Fechas efectiva y de proceso iguales*/
     {
      /*@lineinfo:generated-code*//*@lineinfo:259^7*/

//  ************************************************************
//  #sql { SELECT TO_CHAR(TO_DATE(:v_fecefe,'RRRR-MM-DD')-1,'RRRRMMDD')
//                
//                FROM DUAL
//              };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT TO_CHAR(TO_DATE( :1  ,'RRRR-MM-DD')-1,'RRRRMMDD')\n               \n              FROM DUAL";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // set IN parameters
   __sJT_st.setString(1,v_fecefe);
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   v_efe = (String)__sJT_rs.getString(1);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:262^12*/
      
           v_fecpro = v_fecefe;
           session.removeAttribute("s_fecpro");
           session.setAttribute("s_fecpro", v_fecefe);
     }
     else if(v_calfec > 0)/**Fecha efectiva backdate*/
          {
           v_efe=i_fecha.TBFL_Fecha(v_fecefe);
           v_fecpro = v_fecha_actual;
           session.removeAttribute("s_fecpro");
           session.setAttribute("s_fecpro", v_fecpro);
          }
     /**Consultar tiempo del contrato*/
     /*@lineinfo:generated-code*//*@lineinfo:276^6*/

//  ************************************************************
//  #sql v_contrato = { SELECT TO_DATE(:v_fecefe,'RRRR-MM-DD') - con_fecha_apertura  as v_fechacontrato
//                              FROM tbcontratos
//                              WHERE  con_pro_codigo = :v_pro
//                               AND con_numero      = :v_contra
//                           };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT TO_DATE( :1  ,'RRRR-MM-DD') - con_fecha_apertura  as v_fechacontrato\n                            FROM tbcontratos\n                            WHERE  con_pro_codigo =  :2  \n                             AND con_numero      =  :3 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_fecefe);
   __sJT_st.setString(2,v_pro);
   __sJT_st.setString(3,v_contra);
   // execute query
   v_contrato = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo.i_contrato(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:280^25*/
     while(v_contrato.next())
     {
      v_cum2 = new Double(v_contrato.v_fechacontrato()).toString();
      session.removeAttribute("s_cumes");
      session.setAttribute("s_cumes",(java.lang.Object)v_cum2);
     }
     v_contrato.close();

     //******************************************
     /**Capturar  concepto de transaccion*/
     try { v_retiro = request.getParameter("v_retiro"); } catch (Exception e) { e.printStackTrace(); }
     v_tipotran   = v_retiro.substring(0,6);
     v_clasetran  = v_retiro.substring(6,12);
     v_posicion   = v_retiro.indexOf("-");
     v_diasmenor  = v_retiro.substring(15,v_posicion);
     v_diasmayor  = v_retiro.substring(v_posicion+1,v_retiro.length()-11);
     v_penaliza =  v_retiro.substring(12,13);
     v_traslado =  v_retiro.substring(13,14);
     v_total    =  v_retiro.substring(14,15);

/******Cambio hecho por APC 2005-06-15 para manejar el nuevo retiro a terceros*/
/******Agregado UTT012 por MOS 2008-04-02 para manejar retiro total a terceros*/
     
/******Llave UTT002 para manejar la validación de Tipo de Valor en las transacciones UCT002 GAV 2015-01-26 retiro Parcial Ley 1111 y Ley 1607*/     
     
     if(v_clasetran.trim().toUpperCase().equals("UCT002")){
         v_tipovUCT002 = true;
     }
     
     /******Agregado UTT002 por GAV 2015-01-21 para manejar retiro Parcial Ley 1111 y Ley 1607*/

    if (v_clasetran.trim().toUpperCase().equals("UCT007")|| 
        v_tipotran.trim().toUpperCase().equals("UTT012") ||
        v_clasetran.trim().toUpperCase().equals("UCT002"))
    {
      esTercero ="S";
      v_tipocuentas = "70";/**Código en el 400 para Third Party*/     
    }
     
    if (v_clasetran.trim().toUpperCase().equals("UCT007")|| 
        v_tipotran.trim().toUpperCase().equals("UTT012"))
    {
       v_clasetran = "UCT001";      
    }
     
/***********Fin cambio APC 2005-06-15*****************/     

     /**Si el concepto es no penalizado el retiro es NO penalizado.*/
     if(v_penaliza.equals("N"))
     {
      /**Variable de session de penalización del retiro*/
      session.removeAttribute("s_penaliza");
      session.setAttribute("s_penaliza",v_penaliza);
      v_penalizarretiro = v_penaliza;
     }
     else
     {
      //Si es penalizado se consulta si el contrato es penalizado
     /******/
     v_enconpenaliza = true;
     /**********/      
      //v_pencon.close();
      /**Si el contrato es penalizado*/
      if(v_enconpenaliza)
      {
       /**Variable de session de penalización del retiro*/
       session.removeAttribute("s_penaliza");
       session.setAttribute("s_penaliza","S");
       v_penalizarretiro = "S";
      }
      else /**Si el contrato es NO penalizado*/
      {
       /**Variable de session de penalización del retiro*/
       session.removeAttribute("s_penaliza");
       session.setAttribute("s_penaliza","N");
       v_penalizarretiro = "N";
      }
     }
     /**Variable de session concepto de transaccion*/
     session.removeAttribute("s_tipotran");
     session.removeAttribute("s_clasetran");
     session.removeAttribute("s_diasmenor");
     session.removeAttribute("s_diasmayor");
     session.removeAttribute("s_conceptopenalizar");
     session.removeAttribute("s_traslado");
     session.removeAttribute("s_total");
     session.setAttribute("s_tipotran",v_tipotran);
     session.setAttribute("s_clasetran",v_clasetran);
     session.setAttribute("s_diasmenor",v_diasmenor);
     session.setAttribute("s_diasmayor",v_diasmayor);
     session.setAttribute("s_conceptopenalizar",v_penaliza);
     session.setAttribute("s_traslado",v_traslado);
     session.setAttribute("s_total",v_total);

     /**Consultar código del banco y número de cuenta*/


/*
Modificacion:
Se añade el procedimiento de invocacion a un procedimiento del AS400
*/

/******Cambio hecho por APC 2005-06-15 para manejar el nuevo retiro a terceros*/

  CallableStatement cs = t_tax.prepareCall ( "{? = call TBCL_FUNCIONESAS400.TBFL_CuentasBancarias1(?,?,?,?,?,?)}");
  cs.registerOutParameter(1,Types.CHAR);
  cs.setString(2,v_contra);
  cs.setString(3,v_sistema);
  cs.setString(4,v_usumfund);
  cs.setString(5,v_passmfund);
  cs.setString(6,v_libreria);
  cs.setString(7,v_tipocuentas);  

  cs.executeUpdate();
  v_banco = cs.getString(1);
  cs.close();

/***********Fin cambio APC 2005-06-15*****************/    
  
  
/******Cambio hecho por GAV 2015-01-22 para cargar las cuentas de titular y terceros para la ley de beneficio tributario 1607 para pago por ACH*/

  CallableStatement cs1 = t_tax.prepareCall ( "{? = call TBCL_FUNCIONESAS400.TBFL_CuentasBancarias1(?,?,?,?,?,?)}");
  cs1.registerOutParameter(1,Types.CHAR);
  cs1.setString(2,v_contra);
  cs1.setString(3,v_sistema);
  cs1.setString(4,v_usumfund);
  cs1.setString(5,v_passmfund);
  cs1.setString(6,v_libreria);
  cs1.setString(7,v_tipocuentasTitular);  

  cs1.executeUpdate();
  String v_banco_titu_terc = "";
  v_banco_titu_terc = v_banco + cs1.getString(1);
  cs1.close();

/***********Fin cambio APC 2005-06-15*****************/     


/* Final de la modificacion */
    
        
     /**Contador fondos*/
     /*@lineinfo:generated-code*//*@lineinfo:424^6*/

//  ************************************************************
//  #sql { SELECT count(1)
//               
//               FROM tbreferencias
//              WHERE ref_codigo like 'UFO%'
//             };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT count(1)\n              \n             FROM tbreferencias\n            WHERE ref_codigo like 'UFO%'";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   v_dim = __sJT_rs.getInt(1); if (__sJT_rs.wasNull()) throw new sqlj.runtime.SQLNullException();
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:428^11*/
     v_dim = (v_dim - 1) * 2;
     session.removeAttribute("s_dim");
     session.setAttribute("s_dim",(java.lang.Object)new Integer(v_dim).toString());
     if(v_banco.trim().equals(""))
     {
      v_infbanco = false;
     }
     
     /**GAV 22012015 Valida si las cuentas de terceros y titulaes no estan vacias*/
     if(v_banco_titu_terc.trim().equals(""))    
     {
      v_infbancoTercTitu = false;
     }
     
     /**Si no hay error al consultar código de banco y npumero de cuenta*/
     if(!v_banco.substring(0,5).equals("Error"))//banco
     {
      String[] v_bancuen= UtilitiesForTax.TBCL_CapturarCadena(v_banco,v_dim);   
      /**Consultar saldo del contrato y valor de unidad*/
      
      String[] v_bancuenTercTitu = UtilitiesForTax.TBCL_CapturarCadena(v_banco_titu_terc,v_dim); 
      
      String v_StrValuni2 = "";

/*
Modificacion:
Se añade el procedimiento de invocacion a un procedimiento del AS400
*/
      cs = t_tax.prepareCall ( "{? = call SQL_CALCULO_VALOR_UNIDAD.TBF_CALCULO_VALOR_UNIDAD_S(?,?,?,?,?,?,?)}");
      cs.registerOutParameter(1,Types.CHAR);
      cs.setString(2,v_efe);
      cs.setString(3,v_efe);
      cs.setString(4,v_contra);
      cs.setString(5,v_pro);
      cs.setString(6,"false");
      cs.setInt(7,0);
      cs.setInt(8,3);
      cs.executeUpdate();
      v_StrValuni2 = cs.getString(1);
      cs.close();
      v_valuni2 = UtilitiesForTax.StringTokenizedToArray(v_StrValuni2, 3, "\\");
      t_tax.close();

/* Final de la modificacion */


      /*@lineinfo:generated-code*//*@lineinfo:475^7*/

//  ************************************************************
//  #sql { commit };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:475^19*/
      if(v_valuni2[2] == 0.0)
      {
       // v_saldo  = v_valuni2[1];
       /**Variable de session saldo multifund*/
       session.removeAttribute("s_saldomfund");
       session.setAttribute("s_saldomfund",(java.lang.Object)new Double(v_valuni2[1]).toString());
       v_valor_unidad = v_valuni2[0];
       /**Variable de session valor de unidad*/
       session.removeAttribute("s_valuni");
       session.setAttribute("s_valuni",(java.lang.Object)new Double(v_valuni2[0]).toString());

       /**Consultar cargos para el tipo de retiro*/
       /*@lineinfo:generated-code*//*@lineinfo:488^8*/

//  ************************************************************
//  #sql v_codcargo = { values ( TB_FDATOS_CARGOS ( :v_pro
//                                                      ,:v_tipotran
//                                                      ,:v_clasetran
//                                                      ,:v_cargo1
//                                                      ,:v_cargo2
//                                                      ,:v_cargo3
//                                                      ,:v_cargo4
//                                                      ,:v_mensajecargo
//                                                      )) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FDATOS_CARGOS (  :2  \n                                                    , :3  \n                                                    , :4  \n                                                    , :5  \n                                                    , :6  \n                                                    , :7  \n                                                    , :8  \n                                                    , :9  \n                                                    ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_pro);
   __sJT_st.setString(3,v_tipotran);
   __sJT_st.setString(4,v_clasetran);
   __sJT_st.setString(9,v_mensajecargo);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_codcargo = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cargo1 = (String)__sJT_st.getString(5);
   v_cargo2 = (String)__sJT_st.getString(6);
   v_cargo3 = (String)__sJT_st.getString(7);
   v_cargo4 = (String)__sJT_st.getString(8);
   v_mensajecargo = (String)__sJT_st.getString(9);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:496^55*/
        /**Variables de session cargos*/
       session.removeAttribute("s_cargo1");
       session.setAttribute("s_cargo1",(java.lang.Object)v_cargo1);

       session.removeAttribute("s_cargo2");
       session.setAttribute("s_cargo2",(java.lang.Object)v_cargo2);

       session.removeAttribute("s_cargo3");
       session.setAttribute("s_cargo3",(java.lang.Object)v_cargo3);

       session.removeAttribute("s_cargo4");
       session.setAttribute("s_cargo4",(java.lang.Object)v_cargo4);


       /**Consultar saldo disponible neto y bruto del contrato*/

      /*Modificación hecha por MOS para disminuir los llamados a 400 2013-09-23*/          
      
      //String v_retorno_programa = new String("");
      String v_retorno_fondo = null;
      /*@lineinfo:generated-code*//*@lineinfo:517^7*/

//  ************************************************************
//  #sql v_retorno_fondo = { values (TBCL_FuncionesAs400.TBPL_DisponiblesPorContrato(:v_contra,
//                                                                                         :v_efe,
//                                                                                         'E',
//                                                                                         :v_sistema,
//                                                                                         :v_usumfund,
//                                                                                         :v_passmfund,
//                                                                                         :v_libreria))
//                                                                                          };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBCL_FuncionesAs400.TBPL_DisponiblesPorContrato( :2  ,\n                                                                                        :3  ,\n                                                                                       'E',\n                                                                                        :4  ,\n                                                                                        :5  ,\n                                                                                        :6  ,\n                                                                                        :7  ) \n                                                                                       \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_contra);
   __sJT_st.setString(3,v_efe);
   __sJT_st.setString(4,v_sistema);
   __sJT_st.setString(5,v_usumfund);
   __sJT_st.setString(6,v_passmfund);
   __sJT_st.setString(7,v_libreria);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_retorno_fondo = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:524^88*/
      
      
      //out.println("<b>Retorno</b> "+v_retorno_fondo+"    <b>;</b>");
      v_int1 = v_retorno_fondo.indexOf(";",0);
      v_int2 = v_retorno_fondo.indexOf(";",v_int1+1);
      v_saldispo = new Double(v_retorno_fondo.substring(0, v_int1));
      v_saldispo = v_saldispo/100;
      v_fondos_cerrados =  new Double(v_retorno_fondo.substring(v_int1+1, v_int2));
      v_fondos_cerrados = v_fondos_cerrados/100;
      v_int1 = v_int2;
      v_int2 = v_retorno_fondo.indexOf(";",v_int2+1);
      v_garantias =  new Double(v_retorno_fondo.substring(v_int1+1, v_int2));
      v_garantias =  v_garantias/100;
      v_int1 = v_int2;
      v_int2 = v_retorno_fondo.indexOf(";",v_int2+1);
      v_programa = v_retorno_fondo.substring(v_int1+1, v_int2);
      //out.println("<b>Saldo</b> "+v_saldispo+"    <b>;Cerrados:</b>"+v_fondos_cerrados +"Garantias;"+v_garantias);


      
      /*FIN Modificación hecha por APC para manejar el nuevo reglamento 2006-06-22*/          

      
      /*
       * MOS Se quita temporalmente :v_programa
       * Se agregó nuevamente
       */
       /*@lineinfo:generated-code*//*@lineinfo:552^8*/

//  ************************************************************
//  #sql { call TBPBD_SaldoContratoRet( :v_pro
//                                           ,:v_contra
//                                           ,:v_programa
//                                           ,:v_valor_unidad
//                                           ,:v_penalizarretiro
//                                           ,to_date(:v_fecefe,'RRRR-MM-DD')
//                                           ,:v_cargo1
//                                           ,:v_cargo2
//                                           ,:v_cargo3
//                                           ,:v_cargo4
//                                           ,:v_salTotCap1
//                                           ,:v_salTotConting
//                                           ,:v_salTotRend1
//                                           ,:v_salTotRendN
//                                           ,:v_salTotRendH
//                                           ,:v_salTotCap2
//                                           ,:v_salTotRend2
//                                           ,:v_coderr
//                                           ,:v_msgErr) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_SaldoContratoRet(  :1  \n                                         , :2  \n                                         , :3  \n                                         , :4  \n                                         , :5  \n                                         ,to_date( :6  ,'RRRR-MM-DD')\n                                         , :7  \n                                         , :8  \n                                         , :9  \n                                         , :10  \n                                         , :11  \n                                         , :12  \n                                         , :13  \n                                         , :14  \n                                         , :15  \n                                         , :16  \n                                         , :17  \n                                         , :18  \n                                         , :19  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(13,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(14,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(17,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(18,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(19,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,v_pro);
   __sJT_st.setString(2,v_contra);
   __sJT_st.setString(3,v_programa);
   __sJT_st.setDouble(4,v_valor_unidad);
   __sJT_st.setString(5,v_penalizarretiro);
   __sJT_st.setString(6,v_fecefe);
   __sJT_st.setString(7,v_cargo1);
   __sJT_st.setString(8,v_cargo2);
   __sJT_st.setString(9,v_cargo3);
   __sJT_st.setString(10,v_cargo4);
   __sJT_st.setInt(18,v_coderr);
   __sJT_st.setString(19,v_msgErr);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_salTotCap1 = __sJT_st.getDouble(11); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotConting = __sJT_st.getDouble(12); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotRend1 = __sJT_st.getDouble(13); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotRendN = __sJT_st.getDouble(14); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotRendH = __sJT_st.getDouble(15); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotCap2 = __sJT_st.getDouble(16); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotRend2 = __sJT_st.getDouble(17); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_coderr = __sJT_st.getInt(18); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_msgErr = (String)__sJT_st.getString(19);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:570^59*/
       /**Si no hay error al consultar el saldo disponible*/
       if(v_coderr == 0)
       {//1
        /**Saldo bruto del contrato */
        v_saldispo = v_salTotCap1 + v_salTotRend1;

        /**Variables de session Saldo bruto del contrato */
        session.removeAttribute("s_saldispo");
        session.setAttribute("s_saldispo",(java.lang.Object)new Double(v_saldispo).toString());

        /**Saldo de rendimientos brutos del contrato*/
        v_salrenbruto = v_salTotRend1;
        /**Variables de session Saldo de rendimientos brutos del contrato*/
        session.removeAttribute("s_salrenbruto");
        session.setAttribute("s_salrenbruto",(java.lang.Object)new Double(v_salrenbruto).toString());

        /**Saldo neto del contrato */
        v_saldisponeto = v_salTotCap2 + v_salTotRend2;//neto
        
        /**Valor en fondos cerrados */
         //Saldo Total 
         double saldoT =  v_valuni2[1];
         //Saldo en fondos cerrados
         /*String v_retorno_fondo = null;
         #sql v_retorno_fondo ={values (TBCL_FuncionesAs400.TBPL_SaldoFCerrPorContrato(:v_contra,
                                                                                       :v_efe,
                                                                                       'E',
                                                                                       :v_sistema,
                                                                                       :v_usumfund,
                                                                                       :v_passmfund,
                                                                                       :v_libreria))
                                                                                       };
         v_fondos_cerrados =  new Double(v_retorno_fondo).doubleValue()/100;
        */
        
        
        /**Variables de session Saldo neto del contrato */
        session.removeAttribute("s_saldisponeto");
        session.setAttribute("s_saldisponeto",(java.lang.Object)new Double(v_saldisponeto).toString());
        /**Saldo de rendimientos netos del contrato*/
        v_salrenneto = v_salTotRend2;
        /**Variables de session Saldo de rendimientos netos del contrato*/
        session.removeAttribute("s_salrenneto");
        session.setAttribute("s_salrenneto",(java.lang.Object)new Double(v_salrenneto).toString());
        /**Si saldo disponible mayor a cero*/
        if( v_saldispo > 0 && v_saldisponeto>0)
        {//2
         /**Dibujar página de respuesta*/
         v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Solicitud de Retiro","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_Distribucion","",true,"modulo_retiros.js","return checkrequired(this)");
         out.println(""+v_pintar+"");
         /*Cambio para manejo de referencia unica 2009/03/30 MOS */
         String v_contrato_unif = "";
         /*@lineinfo:generated-code*//*@lineinfo:623^10*/

//  ************************************************************
//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_pro,:v_contra)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_pro);
   __sJT_st.setString(3,v_contra);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_contrato_unif = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:623^83*/
         out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
         out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nombre+"  <b> Apellidos </b>"+v_apellidos+" </CENTER></font>");
         out.println("<br>");
         out.println("<input name= v_retotal type=HIDDEN value='x' size = '9'>");
         
         /**
         Calcular descuentos al disponible
         **/
         //Datos TAX Aportes en canje, Aportes sin certificar, Cuenta contingente, retiros sin procesar
         double v_canje=0;
         double v_sin_cert=0; 
         double v_cuenta_cont=0; 
         Double v_ret_rendi= new Double(0);
         double v_sin_cert_fc=0;
         double v_cuenta_cont_fc=0; 
         double v_ret_pendien=0;
         //HIA 2014-02-03  Se agrega variable para manejar el valor de los retiros express pendientes por procesar
         double v_ret_pendien_express=0;
         Double v_apo_no_dispon= new Double(0);
         double v_total_desc = 0;
         /*@lineinfo:generated-code*//*@lineinfo:644^10*/

//  ************************************************************
//  #sql { call tbpbd_calculo_val_disponible(:v_contra,
//                                                  :v_pro,
//                                                  :v_canje,
//                                                  :v_sin_cert,
//                                                  :v_cuenta_cont,
//                                                  :v_ret_rendi,
//                                                  :v_sin_cert_fc,
//                                                  :v_cuenta_cont_fc,
//                                                  :v_ret_pendien,
//                                                  :v_ret_pendien_express,
//                                                  :v_apo_no_dispon,
//                                                  :v_coderr,
//                                                  :v_msgErr) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN tbpbd_calculo_val_disponible( :1  ,\n                                                 :2  ,\n                                                 :3  ,\n                                                 :4  ,\n                                                 :5  ,\n                                                 :6  ,\n                                                 :7  ,\n                                                 :8  ,\n                                                 :9  ,\n                                                 :10  ,\n                                                 :11  ,\n                                                 :12  ,\n                                                 :13  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"8TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(13,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,v_contra);
   __sJT_st.setString(2,v_pro);
   __sJT_st.setDouble(3,v_canje);
   __sJT_st.setDouble(4,v_sin_cert);
   __sJT_st.setDouble(5,v_cuenta_cont);
   if (v_ret_rendi == null) __sJT_st.setNull(6,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(6,v_ret_rendi.doubleValue());
   __sJT_st.setDouble(7,v_sin_cert_fc);
   __sJT_st.setDouble(8,v_cuenta_cont_fc);
   __sJT_st.setDouble(9,v_ret_pendien);
   __sJT_st.setDouble(10,v_ret_pendien_express);
   if (v_apo_no_dispon == null) __sJT_st.setNull(11,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(11,v_apo_no_dispon.doubleValue());
   __sJT_st.setInt(12,v_coderr);
   __sJT_st.setString(13,v_msgErr);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_canje = __sJT_st.getDouble(3); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_sin_cert = __sJT_st.getDouble(4); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cuenta_cont = __sJT_st.getDouble(5); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_ret_rendi = new Double(__sJT_st.getDouble(6)); if (__sJT_st.wasNull()) v_ret_rendi = null;
   v_sin_cert_fc = __sJT_st.getDouble(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cuenta_cont_fc = __sJT_st.getDouble(8); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_ret_pendien = __sJT_st.getDouble(9); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_ret_pendien_express = __sJT_st.getDouble(10); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_apo_no_dispon = new Double(__sJT_st.getDouble(11)); if (__sJT_st.wasNull()) v_apo_no_dispon = null;
   v_coderr = __sJT_st.getInt(12); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_msgErr = (String)__sJT_st.getString(13);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:656^65*/
           if (v_coderr ==0){
               if (v_ret_rendi == null)
                   v_ret_rendi  =new Double(0);
               if (v_apo_no_dispon == null)
                   v_apo_no_dispon  =new Double(0);
               v_total_desc = v_canje + v_sin_cert +v_ret_pendien + v_ret_pendien_express +v_apo_no_dispon.doubleValue();    
               if (v_fondos_cerrados>0){
                   v_total_desc = v_total_desc + v_fondos_cerrados;
               }
               if (v_fondos_cerrados==0 || v_garantias>0){
                   v_total_desc = v_total_desc + v_cuenta_cont + v_ret_rendi.doubleValue();
               }
               if (v_fondos_cerrados>0 && v_garantias>0){
                  v_total_desc = v_total_desc +  Math.max(0,(v_garantias-v_fondos_cerrados));
               }else{
                   v_total_desc = v_total_desc +  v_garantias;
                   }

          }
         
         /**
          * Agregado por Marcela Ortiz Sandoval 
          * El saldo disponible cambia si es un retiro de liquidez
          * 20090424
          */
           
         //Saldo en fondos liquidos

          double v_fondos_liq=0;
          String v_retorno_liq = null;
          /*@lineinfo:generated-code*//*@lineinfo:687^11*/

//  ************************************************************
//  #sql v_retorno_liq = { values (TBCL_FuncionesAs400.TBPL_SaldoLiquidezContrato(:v_contra,
//                                                                                                 :v_efe,
//                                                                                                 'E',
//                                                                                                 '70000',
//                                                                                                 :v_sistema,
//                                                                                                 :v_usumfund,
//                                                                                                 :v_passmfund,
//                                                                                                 :v_libreria))
//                                                                                                  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBCL_FuncionesAs400.TBPL_SaldoLiquidezContrato( :2  ,\n                                                                                                :3  ,\n                                                                                               'E',\n                                                                                               '70000',\n                                                                                                :4  ,\n                                                                                                :5  ,\n                                                                                                :6  ,\n                                                                                                :7  ) \n                                                                                               \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_contra);
   __sJT_st.setString(3,v_efe);
   __sJT_st.setString(4,v_sistema);
   __sJT_st.setString(5,v_usumfund);
   __sJT_st.setString(6,v_passmfund);
   __sJT_st.setString(7,v_libreria);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_retorno_liq = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:695^96*/ 
            v_fondos_liq =  new Double(v_retorno_liq).doubleValue()/100;  
            
            if((v_fondos_liq==saldoT) && (v_pro.equals("MFUND")) && (v_tipotran.equals("UTT003")||v_tipotran.equals("UTT002")||v_tipotran.equals("UTT012")))
            {
                //Obtenemos Fecha Efectiva para retiro Express Total
                   /*@lineinfo:generated-code*//*@lineinfo:701^20*/

//  ************************************************************
//  #sql { SELECT tbf_obtener_fechas_x_tranx('MFUND', 'UTT015', 'UCT001') FECHA_EFECTIVA
//                                                                                                          
//                     FROM DUAL };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT tbf_obtener_fechas_x_tranx('MFUND', 'UTT015', 'UCT001') FECHA_EFECTIVA\n                                                                                                         \n                   FROM DUAL";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"10TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   v_fecha_retiroTotalExpress = (String)__sJT_rs.getString(1);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:703^29*/
                   
                   //si fecha efectiva es menor a la del dia (SARO) no se hace nada
                   
                    if(v_calfec <= 0)
                    {
                            //***VALIDA QUE FECHA EFECTIVA SEA MENOR A LA FECHA DE PROCESO
                            v_calfecexpr       = v_fecha_retiroTotalExpress.compareTo(v_fecefe);   
                            
                            if(v_calfecexpr<=0)
                            {
                                   v_fecefe = v_fecha_retiroTotalExpress;
                                   v_fecpro = v_fecha_retiroTotalExpress;
                                   session.removeAttribute("s_fecpro");
                                   session.setAttribute("s_fecpro", v_fecpro);
                                   session.removeAttribute("s_fecefe");
                                   session.setAttribute("s_fecefe", v_fecefe);
                                   session.removeAttribute("s_fecefectiva");
                                   session.setAttribute("s_fecefectiva", v_fecefe);
                                   v_aplicaRetTotExpr = 1;
                                
                            }
                    }                             
            }
            
            
                
         if (v_tipotran.equals(v_retExp)){
             try {
   
                 
                 //HIA 2014-02-03 Se restan los retiros express pendientes por procesar al disponible de retiro express.
                 v_fondos_liq = v_fondos_liq - v_ret_pendien_express;
                 v_saldisponeto = Math.min(v_fondos_liq, Math.max(0, saldoT-v_total_desc));
                 
             } catch (Exception ex) { 
                 out.println(ex.toString());
                 ex.printStackTrace();
             }
         }else{
              v_saldisponeto = Math.min(v_saldispo, Math.max(0, saldoT-v_total_desc));
              //out.println("<b>v_saldisponeto</b> "+v_saldisponeto+"<b>;saldoT:</b>"+saldoT +"v_total_desc;"+v_total_desc);
             }
             
         /*Agregado por Romel Escorcia
          * 11/07/2014
          * Al valor del disponible bruto el mayor valor de garantias o saldo en fondos cerrados */
         v_saldispo = saldoT - (v_canje + v_sin_cert +v_ret_pendien + v_ret_pendien_express +v_apo_no_dispon.doubleValue()) - Math.max(v_garantias, v_fondos_cerrados);
         v_saldispo = Math.max(0, v_saldispo);
           /*Fin codigo agregado*/
         
         /*Agregado por Marcela Ortiz Sandoval 
          * 30/08/2007
          * Incluye parametros para manejo de contratos corporativos*/
         out.println("<INPUT ID=montoMinimo NAME=montoMinimo TYPE=hidden VALUE='"+montoMinimo+"'>");
         out.println("<INPUT ID=saldoMinimo NAME=saldoMinimo TYPE=hidden VALUE='"+saldoMinimo+"'>");
         /*Fin codigo agregado*/
         
         /*Agregado por Marcela Ortiz Sandoval 
          * 14/09/2009 */
         out.println("<INPUT ID=esTercero NAME=esTercer TYPE=hidden VALUE='"+esTercero+"'>");
         session.removeAttribute("esTercero");
         session.setAttribute("esTercero",esTercero);
         /*Fin codigo agregado*/
         
         out.println("<pre>");
         out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Saldo Total</b>                       <input name=v_saltot type=text value='"+NumberFormat.getCurrencyInstance().format(v_valuni2[1])+"' size= '24' readonly ></font>");
         
         if(((v_clasetran.equals("UCT002"))&&(v_total.equals("N"))) || (v_tipovUCT002 &&(v_total.equals("N"))))
         {
             out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Saldo Disponible</b>               <input name=v_saldisponible type=text value='"+NumberFormat.getCurrencyInstance().format(v_saldispo)+"' size= '24'  readonly> </font>");
         }
         else
         {
            out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Saldo Disponible</b>               <input name=v_saldisponible type=text value='"+NumberFormat.getCurrencyInstance().format(v_saldisponeto)+"' size= '24'  readonly> </font>");
         }
         
         /**Si el concepto de retiro no es total*/
         if(v_total.equals("N"))
         {
         /* Agregado por Marcela Ortiz Sandoval 
          * 2007/08/30
          * Se incluyeron los parametros saldoMinimo y montominimo en los javascripts
          */
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Valor a Retirar</b>                  <input type=text  name=obligatoriov_valor value='' size= '15' maxlength = 15  onChange = 'minimo("+v_valmin+",obligatoriov_valor,"+v_saldispo+","+v_saldisponeto+",v_tipov,"+montoMinimo+","+saldoMinimo+")'>");
          
              /*Modificado por Romel Escorcia
              * 18/08/2014
              * Si el tipo de transacción es de traslado de deja por defecto el tipo de retiro BRUTO */
              if(v_clasetran.equals("UCT002") || v_tipovUCT002)
              {
                    out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Tipo valor</b>                         <SELECT name=v_tipov  onChange='minimo3("+v_valmin+",obligatoriov_valor,"+v_saldispo+",v_saldisponible,"+v_saldisponeto+",v_tipov,"+montoMinimo+","+saldoMinimo+")'> <OPTION selected value='STV001'>Bruto </OPTION></SELECT></FONT> </font>");
              }
              else
              {
                    out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Tipo valor</b>                         <SELECT name=v_tipov  onChange='minimo2("+v_valmin+",obligatoriov_valor,"+v_saldispo+","+v_saldisponeto+",v_tipov,"+montoMinimo+","+saldoMinimo+")'> <OPTION selected value='STV002'>Neto <OPTION value='STV001'>Bruto</OPTION></SELECT></FONT> </font>");
              }           
              //*FIN Modificación
         }
         /* Agregado por Marcela Ortiz Sandoval 
          * 2009/10/27
          * Inclusión del id del evento de Saro

          */
         if(v_calfec > 0 && v_pro.equals("MFUND")){
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Evento Saro</b>                     <input type=text  name=v_idsaro value='' size= '9' maxlength = 9  >");
         } 
          
         /**Si el concepto de retiro no es de traslado*/
         /** Agregado por MOS 2008-04-02 Si la transaccion es retiro total a tercero 
          ** no debe dar espacio en blanco */
         if(v_traslado.equals("N"))
         {
          if (esTercero.equals("S")){
            out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
            out.println("<tr><td bgcolor='#6f6f4a' colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Información de Tercero</b></font></center></font></td></tr>");
            out.println("<tr><td >");
            out.println("<br>");
            out.println("<pre>");
            out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Número de cuenta</b>              <select name=v_cuenta  onclick='fillWords3(v_cuenta.value)'> ");
          }          
          else{
            out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Numero de cuenta</b>            <select name=v_cuenta>");
          }
          //Si se seleccionó la opción de retiro parcial al dueño de las cuentas registradas
          /** Agregado por MOS 2008-04-02 Si la transaccion es retiro total a tercero 
           *  no debe dar espacio en blanco */ 
          //if (!v_tipotran.trim().toUpperCase().equals("UTT012")){
              out.println("               <option VALUE ='     '>                         ");
          //}*/
          /**Fin del bloque agregado 2008-04-02 */
          if(v_infbanco)
          {
           for(int i=0,f=0;i<v_bancuen.length;i=i+5)
           {
            if(v_bancuen[f] != null)
            {
              if(v_bancuen[i]== null || v_bancuen[i].trim().equals(""))
              {
                v_banconulo = "<b>Confirmar</b>";
              }
              else
              {
                v_banconulo = v_bancuen[i];
              }
              if(v_bancuen[i+1]== null || v_bancuen[i+1].trim().equals(""))
              {
                v_cuentanulo = "<b>Confirmar</b>";
              }
              else
              {
                v_cuentanulo = v_bancuen[i+1];
              }
              if(v_bancuen[i+2]== null || v_bancuen[i+2].trim().equals(""))
              {
                v_nombannulo = "<b>Confirmar</b>";
              }
              else
              {
                v_nombannulo = v_bancuen[i+2];
              }
              if(v_bancuen[i+3]== null || v_bancuen[i+3].trim().equals(""))
              {
                v_ahocor= "<b>Confirmar</b>";
              }
              else
              {
                v_ahocor = v_bancuen[i+3];
              }
              if(v_bancuen[i+4]== null || v_bancuen[i+4].trim().equals(""))
              {
                v_nombreter= "";
              }
              else
              {
                v_nombreter = "," + v_bancuen[i+4];
              }
              //out.println("<br>v_banconulo = " + v_banconulo + "<br>v_cuentanulo=" + v_cuentanulo + "<br>=" + v_nombannulo + "<br>v_ahocor= " + v_ahocor + "<br>v_nombreter="+v_nombreter);
              out.println("               <option VALUE ='"+v_banconulo+","+v_cuentanulo+"'>"+v_cuentanulo+","+ v_nombannulo+"("+v_banconulo+"),"+v_ahocor+v_nombreter);
              f=f+5;
            }
           }
          }
          out.println("</OPTION></SELECT></FONT>");
          out.println("<pre>");
          //Si se seleccionó la opción de retiro parcial al dueño de las cuentas registradas
          if (v_tipocuentas == "10")
          {
              out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>Para Giro en cheque deje en blanco cuenta bancaria.</font>");
          }  
          else
          {
            /** Agregado por MOS 2008-04-02 Si la transaccion es retiro total a tercero 
             *  no debe dar poner la nota para cheque */ 
            if (esTercero.equals("S") || v_traslado.equals("S")){
            /** Agregado por MOS 2009-09-09 Si la transaccion es retiro a tercero 
             *  se capturan los datos del tercero para el cheque */ 
             out.println("<DIV id=section6 style='DISPLAY: none'>");
             out.println("<SPAN  ID=plural6></SPAN>");
             out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Documento</b>                        <input type=text  name=v_doc_ter value='' size= '15' maxlength = 15>");
             out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Tipo de Documento</b>             <SELECT name=v_tipodoc_ter> <OPTION selected value='C'>Cedula Ciudadania</OPTION> <OPTION value='E'>Cedula Extranjeria</OPTION>"+
                         "<OPTION value='N'>Nit Persona Juridica</OPTION><OPTION value='M'>Nit Persona Natural</OPTION><OPTION value='P'>Pasaporte</OPTION><OPTION value='R'>Registro Civil</OPTION><OPTION value='T'>Tarjeta Identidad</OPTION><OPTION value='L'>Carnet Minist Relac Exter</OPTION></SELECT></FONT> </font>");
             out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Nombres</b>                           <input type=text  name=v_nomb_terc value='' size= '30' maxlength = 30>");
             out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Apellidos</b>                           <input type=text  name=v_apell_terc value='' size= '30' maxlength = 30>");
             out.println("</div>");
             out.println("</td></tr>");
             out.println("</pre>");
             out.println("</table></center>");
             
             /**/
              //out.println("<blink><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='RED'><b>Nota:</b>Para Giro en cheque a terceros se debe ingresar primero en el <br>AS400 la información correspondiente al cheque, a continuación <br>deje en blanco la cuenta bancaria.</font></blink>");            
            }
           /**Fin del bloque agregado 2008-04-02 */              
          }
          out.println("</pre>");
         }
         
         /**Consultar administradora de fondos*/
         /*@lineinfo:generated-code*//*@lineinfo:921^10*/

//  ************************************************************
//  #sql v_afp = { SELECT AFP_DESCRIPCION
//                               ,AFP_CODIGO
//                               ,AFP_REF_TIPO
//                           FROM tbadm_fondos_pensiones
//                       ORDER BY AFP_DESCRIPCION  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT AFP_DESCRIPCION\n                             ,AFP_CODIGO\n                             ,AFP_REF_TIPO\n                         FROM tbadm_fondos_pensiones\n                     ORDER BY AFP_DESCRIPCION";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"11TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",theSqlTS);
   // execute query
   v_afp = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo.i_afp(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"11TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:925^47*/

         /**Si el concepto de retiro es traslado*/
         if(v_traslado.equals("S"))
         {
                   
          out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
          out.println("<tr><td bgcolor='#6f6f4a' colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Información de Traslado</b></font></center></font></td></tr>");
          out.println("<tr><td >");
          out.println("<br>");
          out.println("<pre>");
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'>       <b>AFP Destino</b>              <select name=v_afp  onclick='fillWords2(v_afp.value)'> ");
          while (v_afp.next())
          {
           out.println("<option VALUE ='"+v_afp.AFP_CODIGO()+v_afp.AFP_REF_TIPO()+"'>"+ v_afp.AFP_DESCRIPCION());
          }
          v_afp.close();
          out.println("</OPTION></SELECT></FONT>");
          out.println("<DIV id=section5 style='DISPLAY: none'>");
          /**Consultar código producto*/
          /*@lineinfo:generated-code*//*@lineinfo:945^11*/

//  ************************************************************
//  #sql v_productotraslado = { SELECT  PRO_CODIGO
//                                        FROM  TBPRODUCTOS
//                                     };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT  PRO_CODIGO\n                                      FROM  TBPRODUCTOS";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"12TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",theSqlTS);
   // execute query
   v_productotraslado = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo.i_producto(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"12TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:947^35*/
          out.println("<SPAN  ID=plural5></SPAN>");
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>        Producto Destino</b>       <select name=v_proti> </font>");
          while (v_productotraslado.next())
          {
           out.println("<option VALUE ='"+v_productotraslado.PRO_CODIGO()+"'>"+ v_productotraslado.PRO_CODIGO());
          }
          v_productotraslado.close();
          out.println("</OPTION></SELECT></FONT>");
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>        Contrato Destino</b>       <input name=v_conti type=text value='' size= '14'  >  <SELECT name=v_tipocontrato > <OPTION selected value='E'>Existente <OPTION value='N'>Nuevo</OPTION></SELECT></font>");
          out.println("</div>");          
          
          /**GAV Mostrar cuentas registradas para Beneficio Tributario Ley 1111 y Beneficio tributario Condiciones Pensión*/
          

         /**GAV 20150218 Calculo Fecha efectiva Customer First*/
     
         /**GAV Hora de Corte para calculo de fecha efectiva Customer First*/
         
         v_reformaTributaria = TBCL_REFERENCIAS.TBCL_CONSULTA("MVR001");
         /**GAV Hora de Corte para calculo de fecha efectiva Customer First*/
         
         if(v_traslado.equals("S") && esTercero.equals("S") && v_tipocuentas.equals("70"))
         {
          out.println("<DIV id=sectionBenficioTributario style='DISPLAY: none; margin-left:35px;'>");
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Numero de cuenta</b>            <select name=v_cuenta   onclick='fillWords3(v_cuenta.value)'>");
          //Si se seleccionó la opción de retiro parcial al dueño de las cuentas registradas
          /** Agregado por MOS 2008-04-02 Si la transaccion es retiro total a tercero 
           *  no debe dar espacio en blanco */ 
          //if (!v_tipotran.trim().toUpperCase().equals("UTT012")){
              out.println("               <option VALUE ='     '>                         ");
          //}*/
          /**Fin del bloque agregado 2008-04-02 */

          if(v_reformaTributaria.equals("S"))
          {
              if(v_infbanco)

              {
               for(int i=0,f=0;i<v_bancuen.length;i=i+5)
               {
                if(v_bancuen[f] != null)
                {
                  if(v_bancuen[i]== null || v_bancuen[i].trim().equals(""))
                  {
                    v_banconulo = "<b>Confirmar</b>";
                  }
                  else
                  {
                    v_banconulo = v_bancuen[i];
                  }
                  if(v_bancuen[i+1]== null || v_bancuen[i+1].trim().equals(""))
                  {
                    v_cuentanulo = "<b>Confirmar</b>";
                  }
                  else
                  {
                    v_cuentanulo = v_bancuen[i+1];
                  }
                  if(v_bancuen[i+2]== null || v_bancuen[i+2].trim().equals(""))
                  {
                    v_nombannulo = "<b>Confirmar</b>";
                  }
                  else
                  {
                    v_nombannulo = v_bancuen[i+2];
                  }
                  if(v_bancuen[i+3]== null || v_bancuen[i+3].trim().equals(""))
                  {
                    v_ahocor= "<b>Confirmar</b>";
                  }
                  else
                  {
                    v_ahocor = v_bancuen[i+3];
                  }
                  if(v_bancuen[i+4]== null || v_bancuen[i+4].trim().equals(""))
                  {
                    v_nombreter= "";
                  }
                  else
                  {
                    v_nombreter = "," + v_bancuen[i+4];
                  }
                  //out.println("<br>v_banconulo = " + v_banconulo + "<br>v_cuentanulo=" + v_cuentanulo + "<br>=" + v_nombannulo + "<br>v_ahocor= " + v_ahocor + "<br>v_nombreter="+v_nombreter);
                  out.println("               <option VALUE ='"+v_banconulo+","+v_cuentanulo+"'>"+v_cuentanulo+","+ v_nombannulo+"("+v_banconulo+"),"+v_ahocor+v_nombreter);
                  f=f+5;
                }
               }
              }
          }
          out.println("</OPTION></SELECT></FONT>");
          out.println("</div>");
          /**Finalizacion del listado de cuentas terceros ley 1111*/
          
          /**---------------------------------------------------------------------------------*/
          
          /**Listado de cuentas terceros y titulares ley 1607*/
          
          out.println("<DIV id=sectionBeneTributCuentTercTitu style='DISPLAY: none; margin-left:35px;'>");
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Numero de cuenta</b>            <select name=v_cuentaLey1607   onclick='fillWords3(v_cuentaLey1607.value)'>");
          //Si se seleccionó la opción de retiro parcial al dueño de las cuentas registradas
          /** Agregado por MOS 2008-04-02 Si la transaccion es retiro total a tercero 
           *  no debe dar espacio en blanco */ 
          //if (!v_tipotran.trim().toUpperCase().equals("UTT012")){
              out.println("               <option VALUE ='     '>                         ");
          //}*/
          /**Fin del bloque agregado 2008-04-02 */

          if(v_reformaTributaria.equals("S"))
          { 
              if(v_infbancoTercTitu)
              {
               for(int i=0,f=0;i<v_bancuenTercTitu.length;i=i+5)
               {
                if(v_bancuenTercTitu[f] != null)
                {
                  if(v_bancuenTercTitu[i]== null || v_bancuenTercTitu[i].trim().equals(""))
                  {
                    v_banconuloTercTitu = "<b>Confirmar</b>";
                  }
                  else
                  {
                   v_banconuloTercTitu = v_bancuenTercTitu[i];
                  }
                  if(v_bancuenTercTitu[i+1]== null || v_bancuenTercTitu[i+1].trim().equals(""))
                  {
                    v_cuentanuloTercTitu = "<b>Confirmar</b>";
                  }
                  else
                  {
                    v_cuentanuloTercTitu = v_bancuenTercTitu[i+1];
                  }
                  if(v_bancuenTercTitu[i+2]== null || v_bancuenTercTitu[i+2].trim().equals(""))
                  {
                    v_nombannuloTercTitu = "<b>Confirmar</b>";
                  }
                  else
                  {
                    v_nombannuloTercTitu = v_bancuenTercTitu[i+2];
                  }
                  if(v_bancuenTercTitu[i+3]== null || v_bancuenTercTitu[i+3].trim().equals(""))
                  {
                    v_ahocorTercTitu= "<b>Confirmar</b>";
                  }
                  else
                  {
                    v_ahocorTercTitu = v_bancuenTercTitu[i+3];
                  }
                  if(v_bancuenTercTitu[i+4]== null || v_bancuenTercTitu[i+4].trim().equals(""))
                  {
                    v_nombreterTercTitu= " Titular"; //"";
                  }
                  else
                  {
                    v_nombreterTercTitu = "," + v_bancuenTercTitu[i+4];
                  }
                  //out.println("<br>v_banconulo = " + v_banconulo + "<br>v_cuentanulo=" + v_cuentanulo + "<br>=" + v_nombannulo + "<br>v_ahocor= " + v_ahocor + "<br>v_nombreter="+v_nombreter);
                  out.println("               <option VALUE ='"+v_banconuloTercTitu+","+v_cuentanuloTercTitu+"'>"+v_cuentanuloTercTitu+","+ v_nombannuloTercTitu+"("+v_banconuloTercTitu+"),"+v_ahocorTercTitu+v_nombreterTercTitu);
                  f=f+5;
                }
               }
              }
          }  
          out.println("</OPTION></SELECT></FONT>");
          out.println("</div>");
          
          /**Finalizacion Listado de cuentas terceros y titulares ley 1607*/
           
          }/*Fin*/
		  
		    /** Agregado por MOS 2009-11-17 Si la transaccion es de beneficio tributario
            *  se capturan los datos del tercero para el cheque */ 
          out.println("<DIV id=section6 style='DISPLAY: none; margin-left:35px;'>");
          //out.println("<SPAN  ID=plural6></SPAN>");
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Documento</b>                       <input type=text  name=v_doc_ter value='' size= '15' maxlength = 15>");
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Tipo de Documento</b>            <SELECT name=v_tipodoc_ter> <OPTION selected value='C'>Cedula Ciudadania</OPTION> <OPTION value='E'>Cedula Extranjeria</OPTION>"+
                      "<OPTION value='N'>Nit Persona Juridica</OPTION><OPTION value='M'>Nit Persona Natural</OPTION><OPTION value='P'>Pasaporte</OPTION><OPTION value='R'>Registro Civil</OPTION><OPTION value='T'>Tarjeta Identidad</OPTION><OPTION value='L'>Carnet Minist Relac Exter</OPTION></SELECT></FONT> </font>");
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Nombres</b>                           <input type=text  name=v_nomb_terc value='' size= '30' maxlength = 30>");
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Apellidos</b>                           <input type=text  name=v_apell_terc value='' size= '30' maxlength = 30>");
          out.println("</div>");
          /**Fin del bloque agregado 2008-11-17 */ 


          out.println("</td></tr>");
          out.println("</pre>");
          out.println("</table></center>");
         }//traslado
         
         /* Modificación
            Se adiciona mensaje de Saldo Mínimo según Requerimiento 31599 - Ajuste de Retiros en portal de cliente  
            Romel Escorcia 19 de Septiembre de 2014 */
         if(v_total.equals("S") && saldoMinimo>0)
         {
             out.println("<center><table border='0' cellspacing='0'><tr><td align='center'>");
             out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FF0000'>");
             out.println("<b>Este contrato requiere un saldo mínimo de $"+ Math.round(saldoMinimo) +". <br /> Por favor verifique que haya una autorización para el retiro.</b>");
             out.println("</font></td></tr></table><center>");
         }
         /*Fin Modificación */
         
         if(v_aplicaRetTotExpr==1)
         {
             out.println("<br><center><table border='0' cellspacing='0'><tr><td align='center'>");
             out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'>");
             out.println("<b>RETIRO TOTAL EXPRESS <br>Se ha cambiado la Fecha Efectiva de esta solicitud a: "+ v_fecha_retiroTotalExpress + "</b>");
             out.println("</font></td></tr></table><center>"); 
         }
         
         
         /**Si el concepto de retiro no es total*/
         if(v_total.equals("N"))
         {
          out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
          out.println("<tr><td bgcolor='#6f6f4a' colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Distribución  de Fondos para el Retiro</b></font></center></font></td></tr>");
          out.println("<tr><td >");
          out.println("<pre><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=2>                  <input type=radio NAME=v_fondo VALUE='0' checked>  A Prorrata  <input type=radio NAME=v_fondo VALUE='1'>  Por valor  <input type=radio NAME=v_fondo VALUE='2'>  Por Porcentaje</font><pre>");
          out.println("</td></tr>");
          out.println("</table></center>");
          out.println("</pre>");
         }
         out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
         out.println("<center><input type=submit value='Aceptar'  ><input type=button value='Regresar' onclick=' history.go(-1)'><input type=button value='Cancelar' onclick=' history.go(-2)'></center>");
         v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
         out.println(""+v_pie+"");
         out.println("<br>");
         out.close();
        }//2
        else
        {/**Saldo disponible menor que cero*/
         v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Solicitud de Retiro","Error Solicitud de Retiro","","<center>No es posible realizar la Solicitud de Retiro el saldo disponible es "+NumberFormat.getCurrencyInstance().format(v_saldisponeto)+"</center>",false);
         out.println(""+v_pintar+"");
         out.println("<BR>");
         out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
         v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
         out.println("<br>");
         out.println("<br>");
         out.println(""+v_pie+"");
         out.close();
        }
       }
       else
       {
        /*Cambio para manejo de referencia unica 2009/04/01 MOS */
        String v_contrato_unif = "";
        /*@lineinfo:generated-code*//*@lineinfo:1191^9*/

//  ************************************************************
//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_pro,:v_contra)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"13TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_pro);
   __sJT_st.setString(3,v_contra);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_contrato_unif = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1191^82*/
           
           
        /**Error al consultar saldo contrato*/
        v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Solicitud de Retiro","Error Solicitud de Retiro","","<center>No fue posible consultar el saldo disponible para el contrato "+v_contrato_unif+", Error "+v_msgErr+".</center>",false);
        out.println(""+v_pintar+"");
        out.println("<BR>");
        out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
        v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
        out.println("<br>");
        out.println("<br>");
        out.println(""+v_pie+"");
        out.close();
       }
      }
      else
      {/**Error al calcular valor unidad*/
       v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error consulta de saldo y valor de unidad. Mensaje de error:"+v_valuni2[2]+"</center>",false);
       out.println(""+v_pintar+"");
       out.println("<BR>");
       out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
       v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
       out.println("<br>");
       out.println("<br>");
       out.println(""+v_pie+"");
       out.close();
      }
     }
     else
     {/**Error  al consultar código de banco y número de cuenta*/
      v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Consulta de código banco y número cuenta. Mensaje de error:"+v_banco+"</center>",false);
      out.println(""+v_pintar+"");
      out.println("<BR>");
      out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
      v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
      out.println("<br>");
      out.println("<br>");
      out.println(""+v_pie+"");
      out.close();
     }
    }
    else
    {/**Fecha efectiva mayor que fecha de proceso*/
     v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Favor digitar fecha efectiva menor o igual a la fecha de proceso.</center>",false);
     out.println(""+v_pintar+"");
     out.println("<BR>");
     out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
     v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
     out.println("<br>");
     out.println("<br>");
     out.println(""+v_pie+"");
     out.close();
    }
   }
   else
   {/**Termina session*/
    v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.</center>",false);
    out.println(""+v_pintar+"");
    v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
    out.println("<br>");
    out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'></center>");
    out.println(""+v_pie+"");
    out.close();
   }
   /**Desconectar base de datos*/
   //i_conexion.TBCL_DesconectarBaseDatos();
   ctx11.getConnection().close();
  }
  catch(Exception ex)/**Manejo de errores*/
  {
   String v_pintar="";
   String error = ex.toString();
   if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
   {
    v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Solicitud de Retiro","","<center>No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.</center>",false);
   }
   else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
        {
         v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
        }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_pintar = STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.</center>",false);
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_pintar =  STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.</center>",false);
                     }
                  else
                  {
                   v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                  }
   out.println(""+v_pintar+"");
   out.println("<BR>");
   out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'></center>");
   String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
   out.println("<br>");
   out.println("<br>");
   out.println(""+v_pie+"");
   out.close();
  }
  
 }
 
}/*@lineinfo:generated-code*/