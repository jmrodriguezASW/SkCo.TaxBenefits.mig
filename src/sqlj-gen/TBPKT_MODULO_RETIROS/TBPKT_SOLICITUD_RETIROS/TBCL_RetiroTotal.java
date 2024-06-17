/*@lineinfo:filename=TBCL_RetiroTotal*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.Connection;
import sqlj.runtime.ref.DefaultContext ;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.*;
import TBPKT_UTILIDADES.TBPKT_FECHAS.*;
import java.util.Date;
import java.text.NumberFormat;
import java.util.Vector;

/**Clase que  verifica si se puede hacer o no un retiro total */
public class TBCL_RetiroTotal extends HttpServlet
{

 /**Iterator de información  contrato traslado*/
 /*@lineinfo:generated-code*//*@lineinfo:29^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_res
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_res(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    con_numeroNdx = findColumn("con_numero");
    CON_REF_TIPO_IDENTIFICACIONNdx = findColumn("CON_REF_TIPO_IDENTIFICACION");
    CON_NUMERO_IDENTIFICACIONNdx = findColumn("CON_NUMERO_IDENTIFICACION");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String con_numero()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(con_numeroNdx);
  }
  private int con_numeroNdx;
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

/*@lineinfo:user-code*//*@lineinfo:30^68*/
 /**Iterator de información  penalización del contrato*/
 /*@lineinfo:generated-code*//*@lineinfo:32^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_pencon2
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_pencon2(sqlj.runtime.profile.RTResultSet resultSet)
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

/*@lineinfo:user-code*//*@lineinfo:32^61*/

 i_res v_resp;/**Variable tipo iterator i_res*/
 i_pencon2 v_pencon2;/**Variable tipo iterator i_pencon2*/

 /**Procedimiento que consulta si se puede realizar un retiro total, sino se dibuja una página
 *de respuesta que especifica que condiciones no se cumplen para el retiro total */
 public void TBPL_RetTotal(HttpSession session,HttpServletRequest request,PrintWriter out,String nuevaCadena)
 {
  /**Instancias de clase*/
  //STBCL_GenerarBaseHTML i_pagina = new STBCL_GenerarBaseHTML;/**Instancia de la clase TBCL_GenerarBaseHTML*/
  /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_ValidarUsuario de la clase TBCL_Validacion, no es necesaria la instancia nueva*/ 
 //TBCL_Validacion i_valusu = new TBCL_Validacion(); 
 //TBCL_Validacion  i_valusu = new TBCL_Validacion()/**Instancia de la clase TBCL_Validacion*/
  TBCL_ConsultaClienteLista i_consultaC = new TBCL_ConsultaClienteLista();/**Instancia de la clase TBCL_ConsultaClienteLista */
  //TBCL_ConexionSqlj   i_conexion = new TBCL_ConexionSqlj();/**Instancia de la clase TBCL_ConexionSqlj*/
  try
  {
   String[] v_valusu = new String[3];
   v_valusu=TBCL_Validacion.TBFL_ValidarUsuario();
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   Connection t_tax =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
   DefaultContext ctx13 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
   DefaultContext.setDefaultContext(ctx13);
   /**Conectar base de datos*/
   //i_conexion.TBCL_ConexionBaseDatos();
   /**Definicion de variables*/
   /**Tipo boolean*/
   boolean v_encontro        = false;/**Validar que el contrato a trasladar pertenesca al mismo afiliado*/
   boolean v_encontro2       = false;/**Validar que exista el contrato a trasladar*/
   boolean v_enconpenaliza   = false;/**Validar penalización del contrato*/
   boolean v_rettotal        = false;/**Validar condiciones para retiro total*/
   boolean v_tercero         = false;/**Validación de datos para giros a terceros */
   boolean v_terlista        = false;/**Validación del terceroen listas */
   boolean v_saro            = false;/**Validación del evento saro */
   boolean v_existeCuenta    = false;/**Validación del cuenta y banco*/
    /**Tipo String*/
   String v_tipotran = "";/**Variable tipo transacción*/
   String v_clasetran= "";/**Variable clase transacción*/
   String v_traslado = "";/**Variable concepto traslado*/
   String v_fecefe   = "";/**Variable fecha efectiva*/
   String v_intext   = "";/**Variable identificador afp externa o interna*/
   String v_afp      = "";/**Variable cadena afp*/
   String v_cuenta   = "";/**Variable cadena código banco y numero cuenta*/
   String v_proti    = "";/**Variable código producto traslado*/
   String v_conti    = "";/**Variable numero contrato traslado*/
   String v_total    = "";/**Variable concepto total*/
   String v_afp2     = "";/**Variable código afp*/
   String v_banco    = "";/**Variable código banco*/
   String v_numcuen  = "";/**Variable numero de cuenta*/
   String v_tipoconti  = "";/**Variable tipo contrato*/
   String v_mendias    = "";/**Variable indicador de días*/
   String v_fecpro     = "";/**Variable fecha proceso*/
   String v_sistema    = "";/**Variable nombre del sistema multifund*/
   String v_usumfund   = "";/**Variable usuario multifund*/
   String v_passmfund  = "";/**Variable password de usuario multifund*/
   String v_libreria   = "";/**Variable nombre de libreria multifund*/
   String v_cadena     = "";/**Variable cadena contrato*/
   String v_conti2     = "";/**Variable numero contrato*/
   String v_contra     = "";/**Variable numero contrato*/
   String v_pro        = "";/**Variable código producto*/
   String v_nom        = "";/**Variable nombre afiliado*/
   String v_ape        = "";/**Variable apellido afiliado*/
   String v_canje      = "";/**Variable días de canje*/
   String v_tipoiden   = "";/**Variable tipo de identificación*/
   String v_identificacion  = "";/**Variable número de identificación*/
   String v_diasmenor  = "";/**Variable tiempo minimo del contrato*/
   String v_diasmayor  = "";/**Variable tiempo maximo del contrato*/
   String v_pintar     = "";/**Variable dibujar inicio página*/
   String v_pie        = "";/**Variable dibujar final página*/
   String v_idsaro       = "";/**Variable para el id del evento de saro*/
   String v_cuentaLey1607     = "";/**Cadena de código banco y número cuenta para ley 1607 titulares y terceros*/
   /**Agregados para el manejo de datos de terceros 2009/09/14 */
   String v_doc_ter    = "";/**Variable con el documento del tercero*/
   String v_tipodoc_ter= "";/**Variable con el tipo de documento del tercero*/
   String v_nomb_terc  = "";/**Variable con el nombre del tercero*/
   String v_apell_terc = "";/**Variable con el apellido del tercero*/
   String v_esTercero  = "";/**Variable para saber si es una operación a tercero/
   /**Tipo int*/
   int v_retirovigente = 0;
   int v_certifi   = 0;/**Indicador de aportes sin certificar*/
   int v_canje2    = 0;/**Indicador de aportes que no cumplen con los días de canje*/
   int v_disponi   = 0;/**Indicador de aportes que no tienen 100% de disponibilidad*/
   int v_indtras   = 0;/**Indicador de aportes de traslado sin información*/
   int v_fecmay    = 0;/**Indicadir de aportes con fecha mayor a la fecha del retiro*/
   int v_sumtotal  = 0;/**Sumatoria de indicadores*/
   int v_aportes   = 0;/**Indicador de exito o fracaso de fucnión TB_FSELECCION_RETTOTAL*/
   int v_pasa      = 0;/**Indicador de días*/
   int v_pasa2     = 0;/**Indicador de días*/
   int v_conconti  = 0;/**Tamaño del String v_conti*/
   int v_tamañocuenta  = 0;/**Tamaño de la cadena de banco y cuenta*/
   /**Tipo double*/
   double v_diasmenor2 = 0;/**Indicador de días menores*/
   double v_diasmayor2 = 0;/**Indicador de días mayores*/
   double v_diascon    = 0;/**Indicador de días menores*/
   double v_cumes2     = 0;
   double v_saldodispo2  = 0;/**Saldo disponible bruto del contrato*/
   String v_mensLista  ="";
   /**Verificar que las variables de session no expiren*/
   if((java.lang.String)session.getAttribute("s_contrato") != null || (java.lang.String)session.getAttribute("s_producto") != null)
   {
    /**Capturar variables de session*/
    v_contra   = (java.lang.String)session.getAttribute("s_contrato");
    v_pro      = (java.lang.String)session.getAttribute("s_producto");
    v_nom      = (java.lang.String)session.getAttribute("s_nombres");
    v_ape      = (java.lang.String)session.getAttribute("s_apellidos");
    v_canje    = (java.lang.String)session.getAttribute("s_diascanje");
    v_fecefe   =(java.lang.String)session.getAttribute("s_fecefectiva");
     v_fecpro  = (java.lang.String)session.getAttribute("s_fecpro");
    //v_cumple  = (java.lang.String)session.getAttribute("s_cumes");
    v_cumes2   = new Double((java.lang.String)session.getAttribute("s_cumes")).doubleValue();
    v_tipoiden = (java.lang.String)session.getAttribute("s_tipoidentificacion");
    v_identificacion  = (java.lang.String)session.getAttribute("s_identificacion");
    //v_saldodispo    = (java.lang.String)session.getAttribute("s_saldispo");
    v_saldodispo2     = new Double((java.lang.String)session.getAttribute("s_saldispo")).doubleValue();
    v_sistema      = (java.lang.String)session.getAttribute("s_sistema");
    v_usumfund     = (java.lang.String)session.getAttribute("s_usumfund");
    v_passmfund    = (java.lang.String)session.getAttribute("s_passmfund");
    v_libreria     = (java.lang.String)session.getAttribute("s_libreria");
    
    /**Si saldo disponible mayor que cero*/
    if (v_saldodispo2 > 0)
    {
     /**Capturar variables de session*/
     v_tipotran   =  (java.lang.String)session.getAttribute("s_tipotran");
     if(v_tipotran.equals("UTT012")){
        v_tipotran =  "UTT003";
        session.setAttribute("s_tipotran",v_tipotran);
     }
     v_clasetran  =  (java.lang.String)session.getAttribute("s_clasetran");
     v_traslado   =  (java.lang.String)session.getAttribute("s_traslado");
     v_total      =  (java.lang.String)session.getAttribute("s_total");
     v_diasmenor  =  (java.lang.String)session.getAttribute("s_diasmenor");

     v_diasmayor  =  (java.lang.String)session.getAttribute("s_diasmayor");

     /* Agregado por Marcela Ortiz Sandoval 
      * 2009/10/27
      * Inclusión del id del evento de Saro
      */
     /**Capturar el id del evento de Saro */
     try { 
     v_idsaro = request.getParameter("v_idsaro"); 
     session.removeAttribute("v_idsaro");
     session.setAttribute("v_idsaro",v_idsaro);
     /**Validar el id del evento de Saro */
     if (!v_idsaro.trim().equals("")){
      CallableStatement cs1 = t_tax.prepareCall ( "{? = call TBCL_FUNCIONESAS400.TBPL_ValidarSaro(?,?,?,?,?)}");
      cs1.registerOutParameter(1,Types.CHAR);
      cs1.setString(2,v_idsaro);
      cs1.setString(3,v_sistema);
      cs1.setString(4,v_usumfund);
      cs1.setString(5,v_passmfund);
      cs1.setString(6,v_libreria);
      cs1.executeUpdate();
      String v_rta = cs1.getString(1);
      cs1.close();
      if (v_rta.equals("N"))
       {
         v_saro = true;
         v_encontro=false;
       }
     }else
       {
         v_saro = true;
         v_encontro=false;
       }
     } catch (Exception e) { e.printStackTrace(); }
     
     /**Validar el timpo del contrato con los dias permitidos para cada concepto de retiro*/
     if(!v_diasmayor.equals("null"))
     {
      if(!v_diasmayor.equals("0"))
      {
       v_diasmayor2 =  new Double(v_diasmayor).doubleValue();
       if(v_cumes2>v_diasmayor2)
       {
        v_pasa = 0;
       }
       else
       {
        v_pasa = -1;
       }
      }
     }
     if(!v_diasmenor.equals("null"))
     {
      if(!v_diasmenor.equals("0") )
      {
       v_diasmenor2 =  new Double(v_diasmenor).doubleValue();

/*Modificación hecha por APC 2002-06-11 no se tiene en cuenta contratos con exactamente
60 dias de creado el contrato*/
//         if(v_cumes2<v_diasmenor2)
       if(v_cumes2<=v_diasmenor2)
       {
        v_pasa2 = 0;
       }
       else
       {
        v_pasa2 = -1;
       }
      }
     }
      /**Si el concepto no cumple con la parametrización de días*/
     if(v_pasa == -1)
     {
      v_mendias ="mayores";
      v_diascon = v_diasmayor2;
     }
     else if(v_pasa2==-1)
          {
           v_mendias ="menores";
           v_diascon = v_diasmenor2;
          }
     /**Si el concepto cumple con la parametrización de días*/
     if(v_pasa == 0  && v_pasa2 == 0)
     {

      /**Si el concepto de retiro es traslado*/
      if(v_traslado.equals("S"))
      {
        /**Para todos los traslado el tercero siempre es N a menos que sea de Ley1111 */  
        v_esTercero ="N";
        session.removeAttribute("esTercero");
        session.setAttribute("esTercero",v_esTercero);
        
       /**Variable de session banco y cuentao*/
       session.removeAttribute("s_banco");
       session.removeAttribute("s_cuenta");
       session.setAttribute("s_banco"," ");
       session.setAttribute("s_cuenta"," ");
       /**Capturar código afp*/
       try { v_afp = request.getParameter("v_afp"); } catch (Exception e) { e.printStackTrace(); }
       if(!v_afp.substring(0,1).equals(" "))
       {
        v_intext = v_afp.substring(2,8);
        v_afp2   = v_afp.substring(0,2);
        session.removeAttribute("s_afp");
        session.setAttribute("s_afp",v_afp2);
        /** Fin agregado 20091118*/
       }
       else
       {
        session.removeAttribute("s_afp");
        session.setAttribute("s_afp",v_afp);
       }
       /**Capturar código producto traslado*/
       try { v_proti = request.getParameter("v_proti"); } catch (Exception e) { e.printStackTrace(); }
       session.removeAttribute("s_proti");
       session.setAttribute("s_proti",v_proti);
       /**Capturar numero de contrato traslado*/
       try { v_conti = request.getParameter("v_conti"); } catch (Exception e) { e.printStackTrace(); }
       v_conconti =  v_conti.trim().length();
       for(int p=0 ;p <9-v_conconti;p++)
       {
        v_cadena +="0";
       }
       v_conti2 = v_cadena.trim() +v_conti.trim();
       session.removeAttribute("s_conti");
       session.setAttribute("s_conti",v_conti2.trim());
       v_conti2 = v_conti2.trim();
       /**Si el concepto es traslado y el tipo de afp es interno*/
       if(v_traslado.equals("S") && v_intext.equals("STA001"))
       {
        try { v_tipoconti = request.getParameter("v_tipocontrato"); } catch (Exception e) { e.printStackTrace(); }
        /**Si el tipo de conntrato de traslado es existente, se verifica infoemación afiliado*/
        if(v_tipoconti.equals("E"))
        {
         if(!v_conti.trim().equals("") && !v_proti.substring(0,1).equals(" "))
         {
          /*@lineinfo:generated-code*//*@lineinfo:303^11*/

//  ************************************************************
//  #sql v_resp = { SELECT CON_NUMERO
//                                    ,CON_REF_TIPO_IDENTIFICACION
//                                    ,CON_NUMERO_IDENTIFICACION
//                                FROM tbcontratos
//                               WHERE con_pro_codigo = :v_proti
//                                 AND con_numero = :v_conti2
//                              };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT CON_NUMERO\n                                  ,CON_REF_TIPO_IDENTIFICACION\n                                  ,CON_NUMERO_IDENTIFICACION\n                              FROM tbcontratos\n                             WHERE con_pro_codigo =  :1  \n                               AND con_numero =  :2 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroTotal",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_proti);
   __sJT_st.setString(2,v_conti2);
   // execute query
   v_resp = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroTotal.i_res(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"0TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroTotal",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:309^28*/

          while(v_resp.next())
          {
           v_encontro2 = true;
           if(v_resp.CON_REF_TIPO_IDENTIFICACION().equals(v_tipoiden) && v_resp.CON_NUMERO_IDENTIFICACION().equals(v_identificacion))
           {
            v_encontro = true;
           }
          }
          v_resp.close();
         }
        }
        else
        {
            String v_retorno_programa = new String("");
            String hubo_insercion = " ";
            String v_efe        = "";/**Variable fecha efectiva del retiro formato as400*/
            
            /**Pasar fecha efectiva del retiro a formato YYYYMMDD*/
            TBCL_Fecha i_fecha = new TBCL_Fecha();/**Instancia de la clase TBCL_Fecha*/
            v_efe =i_fecha.TBFL_Fecha(v_fecefe);
            //out.println("contrato destino:" + v_conti2);
            
            /*@lineinfo:generated-code*//*@lineinfo:333^13*/

//  ************************************************************
//  #sql v_retorno_programa = { values (TBCL_FuncionesAs400.TBPL_ProgramaContrato(:v_conti2,
//                                                                                          :v_sistema,
//                                                                                          :v_usumfund,
//                                                                                          :v_passmfund,
//                                                                                          :v_libreria))
//                                                                                           };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBCL_FuncionesAs400.TBPL_ProgramaContrato( :2  ,\n                                                                                         :3  ,\n                                                                                         :4  ,\n                                                                                         :5  ,\n                                                                                         :6  ) \n                                                                                        \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroTotal",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_conti2);
   __sJT_st.setString(3,v_sistema);
   __sJT_st.setString(4,v_usumfund);
   __sJT_st.setString(5,v_passmfund);
   __sJT_st.setString(6,v_libreria);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_retorno_programa = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:338^89*/
            
            String v_programa = v_retorno_programa.substring(0,v_retorno_programa.indexOf(";",0));
            
           // out.println("programa :" + v_programa);
            
            CallableStatement t_cst8i_9 = t_tax.prepareCall("{ call TBPBD_INSERTA_CONTRATO_NUEVO(?,?,?,?,?,?,?,?,?,?) }");
            t_cst8i_9.registerOutParameter(10,Types.VARCHAR);
            t_cst8i_9.setString(1,v_tipoiden);
            t_cst8i_9.setString(2,v_identificacion);
            t_cst8i_9.setString(3,v_nom);
            t_cst8i_9.setString(4,v_ape);
            t_cst8i_9.setString(5, v_fecefe.toString());
            t_cst8i_9.setString(6,v_proti);
            t_cst8i_9.setString(7,v_conti2);
            t_cst8i_9.setString(8,v_programa);
            t_cst8i_9.setDouble(9, Double.valueOf(v_efe.toString()).doubleValue());
            t_cst8i_9.execute();
            hubo_insercion = t_cst8i_9.getString(10);
            t_cst8i_9.close();
            
            
            if(hubo_insercion.equals("S"))      
            {
               v_encontro = true;
               //out.println("inserto");
            }else
            {
               v_encontro = false;
               //out.println("no inserto");
            }
            //v_encontro = true;
        }
       }
       else
       {
        session.removeAttribute("s_conti");
        session.removeAttribute("s_proti");
        session.setAttribute("s_proti","  ");
        session.setAttribute("s_conti","  ");
        v_encontro = true;
       }
      }

      if(v_traslado.equals("N")||(v_traslado.equals("S") && v_esTercero.equals("S"))) //(v_traslado.equals("N")||v_esTercero.equals("S"))
           {
            if(v_traslado.equals("N")){
              v_encontro = true;
              /**Variable de session producto y contrato traslado*/
              session.removeAttribute("s_conti");
              session.removeAttribute("s_proti");
              session.setAttribute("s_proti","  ");
              session.setAttribute("s_conti","  ");
              /**Variable de session código afp*/
              session.removeAttribute("s_afp");
              session.setAttribute("s_afp","   ");
              /**Capturar cadena banco y cuenta*/
              try { v_cuenta = request.getParameter("v_cuenta"); } catch (Exception e) { e.printStackTrace(); }
              if(!v_cuenta.trim().equals(""))
              {
               int v_cadena2 = v_cuenta.indexOf(",");
               int u = v_cuenta.length();
               v_banco =  v_cuenta.substring(0,v_cadena2).trim();
               v_numcuen =  v_cuenta.substring(v_cadena2+1,u);
  
               /**Variable de session código banco y numero cuenta*/
               session.removeAttribute("s_banco");
               session.removeAttribute("s_cuenta");
               session.setAttribute("s_banco",v_banco);
               session.setAttribute("s_cuenta",v_numcuen);
               v_existeCuenta = true;
              }

            }
        }
        else
      {
           if(v_intext.equals("STA004")) /**Se realiza validación para cargar los datos de la cuenta, validando si el traslado es por la ley 1607 que permite retiros a cuentas de titulares y terceros*/
                 {    
                     /**Capturar cadena con código del banco y número de cuenta*/
                     try { v_cuentaLey1607 = request.getParameter("v_cuentaLey1607"); 
                       if(!v_cuentaLey1607.trim().equals(""))
                       {
                        int v_cadena2 = v_cuentaLey1607.indexOf(",");
                        v_tamañocuenta = v_cuentaLey1607.length();
                        v_banco        =  v_cuentaLey1607.substring(0,v_cadena2).trim();
                        v_numcuen      =  v_cuentaLey1607.substring(v_cadena2+1,v_tamañocuenta);
                        
                        /**Variable de session del código de banco y número de cuenta*/
                        session.removeAttribute("s_banco");
                        session.removeAttribute("s_cuenta");
                        session.setAttribute("s_banco",v_banco);
                        session.setAttribute("s_cuenta",v_numcuen);
                        v_existeCuenta = true;
                        
                        try {
                          v_esTercero =(java.lang.String)session.getAttribute("esTercero"); 
                          session.removeAttribute("esTercero");
                          session.setAttribute("esTercero",v_esTercero); 
                        }catch (Exception e)  { e.printStackTrace(); }
              
                       }
                       else
                       {
                           v_esTercero ="S"; 
                          session.removeAttribute("esTercero");
                          session.setAttribute("esTercero",v_esTercero); 
                        }
                     
                     } catch (Exception e) { e.printStackTrace(); }
                     
                }
             else
                {
               if(v_intext.equals("STA003")){ //Ley 1111
                     /**Capturar cadena con código del banco y número de cuenta*/
                     try { v_cuenta = request.getParameter("v_cuenta"); 
                       if(!v_cuenta.trim().equals(""))
                       {
                        int v_cadena2 = v_cuenta.indexOf(",");
                        v_tamañocuenta = v_cuenta.length();
                        v_banco        =  v_cuenta.substring(0,v_cadena2).trim();
                        v_numcuen      =  v_cuenta.substring(v_cadena2+1,v_tamañocuenta);
                        
                        /**Variable de session del código de banco y número de cuenta*/
                        session.removeAttribute("s_banco");
                        session.removeAttribute("s_cuenta");
                        session.setAttribute("s_banco",v_banco);
                        session.setAttribute("s_cuenta",v_numcuen);
                        v_existeCuenta = true;
                        
                        try {
                          v_esTercero =(java.lang.String)session.getAttribute("esTercero"); 
                          session.removeAttribute("esTercero");
                          session.setAttribute("esTercero",v_esTercero); 
                        }catch (Exception e)  { e.printStackTrace(); }
              
                       }
                        else
                       {
                           v_esTercero ="S"; 
                          session.removeAttribute("esTercero");
                          session.setAttribute("esTercero",v_esTercero); 
                        }
                     } catch (Exception e) { e.printStackTrace(); }
                    }
                 }   
          }
            //else
            //{
             try { 
              v_esTercero =(java.lang.String)session.getAttribute("esTercero"); 
              if (v_esTercero.equals("S") && v_cuenta.trim().equals("") && v_cuentaLey1607.trim().equals(""))   
              {
                try { v_doc_ter = request.getParameter("v_doc_ter"); } catch (Exception e) { e.printStackTrace(); }
                try { v_tipodoc_ter = request.getParameter("v_tipodoc_ter"); } catch (Exception e) { e.printStackTrace(); }
                try { v_nomb_terc = request.getParameter("v_nomb_terc"); } catch (Exception e) { e.printStackTrace(); }
                try { v_apell_terc = request.getParameter("v_apell_terc"); } catch (Exception e) { e.printStackTrace(); }
                 v_tercero=true;
                 v_encontro=false;
                 
                 if ((!v_doc_ter.trim().equals("")&&!v_tipodoc_ter.trim().equals("")&&
                      !v_apell_terc.trim().equals("")))
                 {
                   session.removeAttribute("v_doc_ter");
                   session.setAttribute("v_doc_ter",v_doc_ter);
                   session.removeAttribute("v_tipodoc_ter");
                   session.setAttribute("v_tipodoc_ter",v_tipodoc_ter);
                   session.removeAttribute("v_nomb_terc");
                   session.setAttribute("v_nomb_terc",v_nomb_terc);
                   session.removeAttribute("v_apell_terc");
                   session.setAttribute("v_apell_terc",v_apell_terc);
                   v_tercero=false;
                   v_encontro=true;
                   
                   String lista = i_consultaC.ConsultarCliente(v_doc_ter);
                   if (lista.equals("Y"))
                   {
                     v_terlista = true;
                     v_encontro=true;
                     v_mensLista ="<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#FF0000'><b>Favor Comunicarse con el área de Financial Crime Prevention a las EXT 4018-4269-4233 antes de continuar con la operación<center></center> </b></font>";

                   }
                 }
               } 
             /**Variable de session código banco y numero cuenta*/
             if(!v_existeCuenta){
               session.removeAttribute("s_banco");
               session.removeAttribute("s_cuenta");
               session.setAttribute("s_banco"," ");
               session.setAttribute("s_cuenta"," ");
             }
             }catch (Exception e)  { e.printStackTrace(); }
             

            //}
          

      session.removeAttribute("s_fondo");
      session.setAttribute("s_fondo","S");
      /**Si la validacipon de contrato traslado estuvo correcta*/

      if(v_encontro && !v_saro)
      {//1
       /**Dibujar pagina de respuesta*/
       v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Retiro Total","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_CalcularRetiro2","",true,"modulo_retiros.js","return validar_retiro(this)");
       out.println(""+v_pintar+"");
       out.println(""+v_mensLista+"");
       /*Cambio para manejo de referencia unica 2009/03/30 MOS */
       String v_contrato_unif = "";
       /*@lineinfo:generated-code*//*@lineinfo:548^8*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroTotal",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:548^81*/
       out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
       out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
       out.println("<br>");
       /**Consultar aportes del contrato, deben cumplir con las condiciones para retiro total*/
       /*@lineinfo:generated-code*//*@lineinfo:553^8*/

//  ************************************************************
//  #sql v_aportes = { values (TB_FSELECCION_RETTOTAL( :v_pro
//                                                           ,:v_contra
//                                                           ,to_number(:v_canje)
//                                                           ,:v_fecefe
//                                                           ,:v_fecpro
//                                                           ,:v_certifi
//                                                           ,:v_canje2
//                                                           ,:v_disponi
//                                                           ,:v_fecmay
//                                                           ,:v_indtras
//                                                          )) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FSELECCION_RETTOTAL(  :2  \n                                                         , :3  \n                                                         ,to_number( :4  )\n                                                         , :5  \n                                                         , :6  \n                                                         , :7  \n                                                         , :8  \n                                                         , :9  \n                                                         , :10  \n                                                         , :11  \n                                                        ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroTotal",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.INTEGER);
   }
   // set IN parameters
   __sJT_st.setString(2,v_pro);
   __sJT_st.setString(3,v_contra);
   __sJT_st.setString(4,v_canje);
   __sJT_st.setString(5,v_fecefe);
   __sJT_st.setString(6,v_fecpro);
   __sJT_st.setInt(7,v_certifi);
   __sJT_st.setInt(8,v_canje2);
   __sJT_st.setInt(9,v_disponi);
   __sJT_st.setInt(10,v_fecmay);
   __sJT_st.setInt(11,v_indtras);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_aportes = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_certifi = __sJT_st.getInt(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_canje2 = __sJT_st.getInt(8); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_disponi = __sJT_st.getInt(9); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_fecmay = __sJT_st.getInt(10); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_indtras = __sJT_st.getInt(11); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:563^59*/
       /**Función sin error*/
       

       if(v_aportes == 0)
       {//1
          /*@lineinfo:generated-code*//*@lineinfo:569^11*/

//  ************************************************************
//  #sql { SELECT  COUNT(1)
//                     
//                     FROM  TBRETIROS
//                     WHERE RET_CON_PRO_CODIGO = :v_pro
//                       AND RET_CON_NUMERO     = :v_contra
//                       AND RET_FECHA_EFECTIVA > TO_DATE(:v_fecefe,'RRRR-MM-DD')
//                       AND (RET_REF_ESTADO      = 'SER001' OR RET_REF_ESTADO      = 'SER006')
//                  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT  COUNT(1)\n                    \n                   FROM  TBRETIROS\n                   WHERE RET_CON_PRO_CODIGO =  :1  \n                     AND RET_CON_NUMERO     =  :2  \n                     AND RET_FECHA_EFECTIVA > TO_DATE( :3  ,'RRRR-MM-DD')\n                     AND (RET_REF_ESTADO      = 'SER001' OR RET_REF_ESTADO      = 'SER006')";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroTotal",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // set IN parameters
   __sJT_st.setString(1,v_pro);
   __sJT_st.setString(2,v_contra);
   __sJT_st.setString(3,v_fecefe);
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   v_retirovigente = __sJT_rs.getInt(1); if (__sJT_rs.wasNull()) throw new sqlj.runtime.SQLNullException();
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:576^16*/
        /**Dibujar que condiciones se estan violando  para hacer un retiro total*/
        v_sumtotal = v_certifi + v_canje2 + v_disponi + v_fecmay+v_retirovigente+v_indtras;
        if (v_sumtotal != 0)
        {//17
         out.println("<br>");
         out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b> La solicitud de retiro para el  contrato "+v_contra+" no se puede realizar, por que tiene:</b></font></center>");
         out.println("<br>");
         v_rettotal  = true;
        }
        out.println("<pre>");
        if (v_certifi != 0)
        {//17
         out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>         - "+v_certifi+" aporte(s) sin certificar.</b></font>");
        }
        if (v_disponi != 0)
        {//17
         out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>         - "+v_disponi+" aporte(s) no disponibles.</b></font>");
        }
        if (v_canje2 != 0)
        {//17
         out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>         - "+v_canje2+" aporte(s) que  no han cumplido los días de canje.</b></font>");
        }
        if (v_fecmay!= 0)
        {//17
         out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>         - "+v_fecmay+" aporte(s) que son posteriores a la fecha efectiva del retiro.</b></font>");
        }
        if (v_retirovigente != 0)
        {//17
         out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>         - "+v_retirovigente+" retiros(s) que son posteriores a la fecha efectiva del nuevo retiro.</b></font>");
        }
        if (v_indtras != 0)
        {//17
          out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>   -  " +v_indtras+" aporte(s) de traslado sin información. </b></font></center>");
        }
        
        
        
        out.println("</pre>");
        if (v_rettotal)
        {//17
         out.println("<center><input type='Button' value='Cancelar' onclick=' history.go(-3)'> <input type='Button' value='Regresar' onclick='history.go(-1)'></center>");
         v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
         out.println("<br>");
         out.println(""+v_pie+"");
         out.close();
        }
        
        else
        {/**Si la información es correcta, dibuja página de respuesta informando si se desea realizar el retiro*/
         out.println("<br>");
         
         out.println("<br>");
         out. println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>¿Desea realizar la solicitud de retiro ?</b></font></center>");
         out.println("<br>");
         out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
         out.println("<center>");
         out.println("<input type=submit value='Aceptar' name = 'aceptar'><input type='Button' value='Regresar' onclick='history.go(-1)' name = 'regresar'><input type='Button' value='Cancelar' onclick='history.go(-3)' name ='cancelar'></center>");
         v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
         out.println("<br>");
         out.println(""+v_pie+"");
         out.close();
        }
       }
       else
       {/**Erro al consultar aportes del contrato*/
        out.println("<br>");
        out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Error al consultar los aportes.</b></font></center>");
        out.println("<br>");
        out.println("<center><input type=button value='Cancelar' onclick=' history.go(-3)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
        v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
        out.println("<br>");
        out.println(""+v_pie+"");
        out.close();
       }
      }
      else
      {/**Si no es correcta la validación del contrato traslado*/
       v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Retiro Total","","",true,"","");
       out.println(""+v_pintar+"");
       /*Cambio para manejo de referencia unica 2009/03/30 MOS */
       String v_contrato_unif = "";
       /*@lineinfo:generated-code*//*@lineinfo:658^8*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroTotal",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:658^81*/
       out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
       out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
       out.println("<br>");
       
       /**Los datos para el giro a tercero no estan completos*/
       /** Agregado 20090914*/
       if(v_terlista)
       {
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b><center> El documento ingresado se encuentra en la lista Clinton.</center> </b></font>");
       }
       else{
         if(v_tercero)
         {
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b><center> Para giro a un tercero por favor complete la información requerida del tercero.</center> </b></font>");
         }
         else
         {
           if(v_saro)
           {
            out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b><center> El Evento Saro ingresado no es válido.</center> </b></font>");
           }
         else{
           if(v_encontro2)
           {
            out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b><center> El número de contrato "+v_conti+" no pertenece al cliente por lo tanto no es permitida esta operación.</center> </b></font>");
           }
           else
           {
            out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b><center> No esta creado el numero de contrato "+v_conti+" en el sistema.</center></b></font>");
           }
         }
       }
       }
       out.println("<br>");
       out.println("<center><input type='Button' value='Cancelar' onclick=' history.go(-3)'> <input type='Button' value='Regresar' onclick='history.go(-1)'></center>");
       v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
       out.println("<br>");
       out.println(""+v_pie+"");
       out.close();
      }
     }
     else
     {/**Si no se cumple con la parametrización de los dias permitidos para el concepto de retiro*/
      v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Retiro Total","","",true,"","");
      out.println(""+v_pintar+"");
      /*Cambio para manejo de referencia unica 2009/03/30 MOS */
      String v_contrato_unif = "";
      /*@lineinfo:generated-code*//*@lineinfo:706^7*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroTotal",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:706^80*/
      out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
      out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
      out.println("<br>");
      out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>No es posible realizar la solicitud de retiro, los días del contrato deben ser "+v_mendias+" a "+v_diascon+". </b></font></center>");
      out.println("<br>");
      out.println("<center><input type='Button' value='Cancelar' onclick=' history.go(-3)'> <input type='Button' value='Regresar' onclick='history.go(-1)'></center>");
      v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
      out.println("<br>");
      out.println(""+v_pie+"");
      out.close();
     }
    }//2
    else
    {/**Si el saldo del contrato es menor o igual a cero*/
     v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Solicitud de Retiro","Error Retiro Total","","<center>No es posible realizar la Solicitud de Retiro el saldo disponible es $"+NumberFormat.getCurrencyInstance().format(v_saldodispo2)+"</center>",false);
     out.println(""+v_pintar+"");
     out.println("<BR>");
     out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-3)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
     v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
     out.println("<br>");
     out.println("<br>");
     out.println(""+v_pie+"");
     out.close();
    }
   }
   else
   {/**Terminar session*/
    v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Retiro Total","","<center>Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.</center>",false);
    out.println(""+v_pintar+"");
    v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
    out.println("<br>");
    out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-3)'></center>");
    out.println(""+v_pie+"");
    out.close();
   }
   /**Desconectar base de datos*/
   //i_conexion.TBCL_DesconectarBaseDatos();
     ctx13.getConnection().close();
  
  }/**Manejo de errores*/
  catch(Exception ex)
  {
   String v_pintar="";
   String error = ex.toString();
   if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
   {
    v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Retiro Total","","<center>No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.</center>",false);
   }
   else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
        {
         v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Retiro Total","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
        }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_pintar = STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Retiro Total","","<center>Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.</center>",false);
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_pintar = STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Retiro Total","","<center>Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.</center>",false);
                     }
                  else
                  {
                   v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Retiro Total","","<center>Mensaje de Error :"+ex+".</center>",false);
                  }
   out.println(""+v_pintar+"");
   out.println("<BR>");
   out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-3)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
   String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
   out.println("<br>");
   out.println(""+v_pie+"");
   out.close();
  }
 }
}/*@lineinfo:generated-code*/