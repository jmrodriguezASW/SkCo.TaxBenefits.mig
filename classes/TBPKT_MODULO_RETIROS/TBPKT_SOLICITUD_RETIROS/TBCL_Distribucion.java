/*@lineinfo:filename=TBCL_Distribucion*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

/*

Modificacion:
Se elimina el import debido a que la conexion
al AS400 se hace desde la base de datos

*/

// import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;

import TBPKT_IAS.*;

import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_FECHAS.*;
import java.text.NumberFormat;
import sqlj.runtime.ref.DefaultContext ;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.*;


import java.util.*;

//import taxbenefits.proxy.BasicHttpBinding_IAvailableServiceClient;
//import taxbenefits.proxy.types.org.datacontract.schemas._2004._07.skco_available_fsl.Saldo;

// Se elimina este import ya que no se utiliza.

//import TBPKT_AJUSTES.*;


/**Clase que muestra al usuario la distribución actual de saldos de los fondos@
*del contrato, y permite distribuir el valor del retiro en los diferentes fondos.*/
public class TBCL_Distribucion extends HttpServlet
{//1
 /**Iterator de nombre y número de identificación del afiliado.*/
 /*@lineinfo:generated-code*//*@lineinfo:45^2*/ /*//  *************************************************************//*//  SQLJ iterator declaration:*//*//  *************************************************************/ public static class i_res extends sqlj.runtime.ref.ResultSetIterImpl implements sqlj.runtime.NamedIterator { public i_res(sqlj.runtime.profile.RTResultSet resultSet) throws java.sql.SQLException { super(resultSet); con_numeroNdx = findColumn("con_numero"); CON_REF_TIPO_IDENTIFICACIONNdx = findColumn("CON_REF_TIPO_IDENTIFICACION"); CON_NUMERO_IDENTIFICACIONNdx = findColumn("CON_NUMERO_IDENTIFICACION"); m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet(); } private oracle.jdbc.OracleResultSet m_rs; public String con_numero() throws java.sql.SQLException { return (String)m_rs.getString(con_numeroNdx); } private int con_numeroNdx; public String CON_REF_TIPO_IDENTIFICACION() throws java.sql.SQLException { return (String)m_rs.getString(CON_REF_TIPO_IDENTIFICACIONNdx); } private int CON_REF_TIPO_IDENTIFICACIONNdx; public String CON_NUMERO_IDENTIFICACION() throws java.sql.SQLException { return (String)m_rs.getString(CON_NUMERO_IDENTIFICACIONNdx); } private int CON_NUMERO_IDENTIFICACIONNdx; } /*//  *************************************************************/ 


                                   /*@lineinfo:user-code*//*@lineinfo:48^35*/
 /**Iterator de penalizaciones del contrato.*/

 /**Iterator código del fondo.*/
 /*@lineinfo:generated-code*//*@lineinfo:52^2*/ /*//  *************************************************************//*//  SQLJ iterator declaration:*//*//  *************************************************************/ public static class i_res2 extends sqlj.runtime.ref.ResultSetIterImpl implements sqlj.runtime.NamedIterator { public i_res2(sqlj.runtime.profile.RTResultSet resultSet) throws java.sql.SQLException { super(resultSet); ref_codigoNdx = findColumn("ref_codigo"); ref_descripcionNdx = findColumn("ref_descripcion"); v_porcentajeNdx = findColumn("v_porcentaje"); m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet(); } private oracle.jdbc.OracleResultSet m_rs; public String ref_codigo() throws java.sql.SQLException { return (String)m_rs.getString(ref_codigoNdx); } private int ref_codigoNdx; public String ref_descripcion() throws java.sql.SQLException { return (String)m_rs.getString(ref_descripcionNdx); } private int ref_descripcionNdx; public String v_porcentaje() throws java.sql.SQLException { return (String)m_rs.getString(v_porcentajeNdx); } private int v_porcentajeNdx; } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:52^97*/
 /**Iterator minimos porcentaje a dejar en cada fondo*/




 /**Variable tipo iterator datos afiliado*/
 i_res v_resp;
 /**Variable tipo iterator código fondo*/
 i_res2 v_res2;

 /**Procedimiento local que consulta al as400 la disribución actual de los fondos y permite distribuir el valor a retirar.@
 * Puede ser a Prorrata, valor o porcentaje.
 */
 public void TBPL_DistribucionFondos (HttpSession session,HttpServletRequest request,PrintWriter out,String nuevaCadena )
 {//2
   /**Instancias de clase*/
  STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();/**Instancia de la clase TBCL_GenerarBaseHTML*/
  TBCL_Validacion  i_valusu      = new TBCL_Validacion ();/**Instancia de la clase TBCL_Validacion */
  TBCL_ConsultaClienteLista i_consultaC = new TBCL_ConsultaClienteLista();/**Instancia de la clase TBCL_ConsultaClienteLista */

/*

Modificacion:
Se debe reemplazar la creacion del objeto
TBCL_FuncionesAs400 ya que se sustituye por un llamado a
procedimiento de la base de datos

*/

//  TBCL_FuncionesAs400 i_fondos   = new TBCL_FuncionesAs400();/**Instancia de la clase TBCL_FuncionesAs400*/

  TBCL_Fecha i_fecha = new TBCL_Fecha();/**Instancia de la clase TBCL_Fecha*/
  //TBCL_ConexionSqlj    i_conexion = new TBCL_ConexionSqlj();
  try
  {//3
   /**Leer de archivo connection.properties url,usuario y paswword a la base de datos.*/
   String[] v_valusu = new String[3];
   v_valusu=i_valusu.TBFL_ValidarUsuario();
   /**Realizar conexion a la base de datos*/
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   Connection t_tax =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
   DefaultContext ctx3 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
   DefaultContext.setDefaultContext(ctx3);
   //i_conexion.TBCL_ConexionBaseDatos();

   /**Definicion de variables*/
   /**Tipo Vector*/
   Vector v_fon    = new Vector();/** Vector código fondo*/
   Vector v_desc   = new Vector();/** Vector descripción fondo*/
   Vector v_val    = new Vector();/** Vector saldo fondo*/
   Vector v_minfon = new Vector();/** Vector minimo fondo*/

   /**Tipo String*/
   String v_codigoas    = "";/**Variable código del fondo en el as400*/
   String v_codfon      = "";/**Variable código del fondo en Tax Benefits*/
   String v_tipotran    = "";/**Variable  tipo de transacción*/
   String v_clasetran   = "";/**Variable clase de transacción*/
   String v_traslado    = "";/**Variable concepto de retiro para traslado*/
   String v_valor       = "";/**Variable valor del retiro*/
   String v_tipov       = "";/**Variable tipo de valor*/
   String v_intext     = "";/**Variable indicador de afp interna o externa*/
   String v_afp        = " ";/**Variable cadena código afp e identificador de externa o externa*/
   String v_cuenta     = "";/**Cadena de código bango y número cuenta*/
   String v_cuentaLey1607     = "";/**Cadena de código banco y número cuenta para ley 1607 titulares y terceros*/
   String v_banco      = "";/**Variable código de banco*/
   //nuevo
   String v_cadblanco   = " ";
   String v_numcuen    = "";/**Variable numero de cuenta*/
   String v_distribucion = "";/**Variable distribución de fondos*/
   String v_proti      = "";/**Variable producto destino*/
   String v_conti      = "";/**Variable contrato destino*/
   String v_conti2     = "";/**Variable contrato destino */
   String v_fecefe     = "";/**Variable Fecha efectiva*/
   String v_fecpro        = "";/**Variable  feha proceso retiro*/
   String v_maximo     = "";/**Variable maximo de fondos*/
   String v_afp2       = "";/**Variable codigo afp*/
   String v_tipoconti  = "";/**Tipo contrato traslado nuevo o existente*/
   String v_cadena     = "";/**Cadena para reemplazr con ceros*/
   String v_fondos     = "";/**Cadena con información de fondos y saldos del as400*/
   String v_cumple     = "";/**Variable tiempo del contrato*/
   String v_diasmenor  = "";/**Variable tiempo minimo del contrato*/
   String v_diasmayor  = "";/**Variable tiempo maximo del contrato*/
   String v_mendias    = "";/**Variable que guarda mensaje de dias menores o mayores*/
   String v_efe        = "";/**Variable fecha efectiva del retiro formato as400*/
   String v_desfon     = "";/**Variable descripción del fondo*/
   String v_contra     = "";/**Variable numero del contrato*/
   String v_pro        = "";/**Variable Código del producto*/
   String v_nom        = "";/**Variable nombre del afiliado*/
   String v_ape        = "";/**Variable Apellido del afiliado*/
   String v_tipoiden   = "";/**Variable tipo de identificación afiliado*/
   String v_identificacion  ="";/**Variable número de identificación*/
   String v_sistema    = "";/**Variable nombre del sistema as400*/
   String v_usumfund   = "";/**Variable usuario del sistema as400*/
   String v_passmfund  = "";/**Variable password del usuario as400*/
   String v_libreria   = "";/**Variable nombre de libreria as400*/
   String v_dim2       = "";/**Variable contador*/
   String v_total      = "";/**Variable concepto retiro total*/
   String v_pinfon     = "";/**Variable dibujar cabeza de página de respuesta*/
   String v_piefon     = "";/**Variable dibujar pie de página de respuesta*/
   String v_contratotraslado="";/**Variable contrato traslado*/
   String v_pintar     = "";/**Variable dibujar cabeza de página de respuesta*/
   String v_pie        = "";/**Variable dibujar pie de página de respuesta*/
   /**Agregados para el manejo de datos de terceros 2009/09/14 */
   String v_doc_ter    = "";/**Variable con el documento del tercero*/
   String v_tipodoc_ter= "";/**Variable con el tipo de documento del tercero*/
   String v_nomb_terc  = "";/**Variable con el nombre del tercero*/
   String v_apell_terc = "";/**Variable con el apellido del tercero*/
   String v_esTercero  = "";/**Variable para saber si es una operación a tercero*/
   String v_idsaro     = "";/**Variable para el id del evento  de saro */
   String v_valor_neto2= "";/**Variable para almacenar el valor origal antes del 4xmil */
   /**Tipo int*/
   int v_conconti      = 0;/**Tamaño del contrato de traslado*/
   int v_pasa          = 0;/**Verificación diás del contrato*/
   int v_pasa2         = 0;/**Verificación diás del contrato*/
   int v_num           = 1;/**Numero de fondos que conforman el contrato*/
   int v_dim           = 0;/**Contador*/
   int v_tamañocuenta  = 0;/**Tamaño de la cadena de banco y cuenta*/
   int v_calfec   = 0;   
   /**Tipo boolean*/
   boolean v_validarfondo  = false;/**Validar parametrización de fondos*/
   boolean v_encontro      = false;/**Validar afiliado del contrato traslado*/
   boolean v_encontro2     = false;/**Encontrar datos afiliado del contrato traslado*/
   boolean v_enconpenaliza = false;/**Validar penalización contrato*/
   boolean v_encdisfon     = false;/**Verificación de retiros del mismo contrato*/
   boolean vtienedefault   = false;
   boolean v_tercero       = false;/**Validación de datos para giros a terceros */
   boolean v_terlista      = false;/**Validación del tercero en listas */
   boolean v_saro          = false;/**Validación del evento saro */
   boolean v_existeCuenta  = false;/**Validación del cuenta y banco*/
   /**Tipo double*/
   double v_valort     = 0;/**Variable  valor fondo*/
   double v_valdiv     = 0;/** Variable valor retiro fondo a prorrata*/
   double v_cumes2     = 0;/**Variable tiempo del contrato numerico*/
   double v_diasmenor2 = 0;/**Variable numerica tiempo minimo del contrato*/
   double v_diasmayor2 = 0;/**Variable tiempo maximo del contrato*/
   double v_diascon    = 0;/**Variable días validos para el retiro*/
   double v_sumatfondo = 0;/**Variable saldos total fondos*/
   double v_sumval     = 0;/**Variable sumatoria de saldos retirados de los fondos*/
   double v_cargomil   = 0;/**Variable para el porcentaje de4x1000*/
   String v_metodo_1   ="";
   String v_metodo_2   ="";
   String v_metodo_3   ="";
   String v_metodo_4   ="";
   String v_natcon_1   ="";
   String v_resnat_1   ="";
   String v_retExp     ="UTT014";
   String v_mensLista  ="";
   
      
   /**Verificar que las variables de session no expiren*/
   if((java.lang.String)session.getAttribute("s_contrato") != null || (java.lang.String)session.getAttribute("s_producto") != null)
   {//4
    /**Capturar variables de session*/

    v_contra         = (java.lang.String)session.getAttribute("s_contrato");
    v_pro            = (java.lang.String)session.getAttribute("s_producto");
    v_nom            = (java.lang.String)session.getAttribute("s_nombres");
    v_ape            =  (java.lang.String)session.getAttribute("s_apellidos");
    v_tipoiden       = (java.lang.String)session.getAttribute("s_tipoidentificacion");
    v_identificacion = (java.lang.String)session.getAttribute("s_identificacion");
    v_sistema        = (java.lang.String)session.getAttribute("s_sistema");
    v_usumfund       = (java.lang.String)session.getAttribute("s_usumfund");
    v_passmfund      = (java.lang.String)session.getAttribute("s_passmfund");
    v_libreria       = (java.lang.String)session.getAttribute("s_libreria");
    //v_dim2           = (java.lang.String)session.getAttribute("s_dim");
    v_dim            = new Integer((java.lang.String)session.getAttribute("s_dim")).intValue();
    v_fecefe         = (java.lang.String)session.getAttribute("s_fecefectiva");
    v_fecpro         = (java.lang.String)session.getAttribute("s_fecpro");
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
    }else{
         v_saro = true;
         v_encontro=false;
       }
    } catch (Exception e) { e.printStackTrace(); }

       
    //v_cumple         = (java.lang.String)session.getAttribute("s_cumes");
    v_cumes2         = new Double((java.lang.String)session.getAttribute("s_cumes")).doubleValue();
    /**Pasar fecha efectiva del retiro a formato YYYYMMDD*/

    v_efe            =i_fecha.TBFL_Fecha(v_fecefe);
    /**Consultar saldo de los fondos del contrato al as400*/

//    v_fondos         = i_fondos.TBPL_SaldosPorContrato(v_contra,v_efe,"E",v_sistema,v_usumfund, v_passmfund, v_libreria);

/*
Modificacion:
Se añade el procedimiento de invocacion a un procedimiento del AS400

*/
       /**Capturar el tipo de transacción*/
       v_tipotran =  (java.lang.String)session.getAttribute("s_tipotran");
       
//  out.println("Llamado al programa en as 400: <br>vcontra="+v_contra+"<br>vefe="+v_efe+"<br>E<br>vsistema="+v_sistema+"<br>vusumfund="+v_usumfund+"<br>vpassmfund="+v_passmfund+"<br>vlibreria="+v_libreria);

      /** Agregado por Marcela Ortiz Sandoval 
       * 2008/02/19
       * Modificado para que vea unicamente la distrubución de fondos liquidos
       * en caso de que sea un retiro express
       **/       
       String v_proced = null;
       if (v_tipotran.equals(v_retExp))
         v_proced = "call TBCL_FUNCIONESAS400.TBPL_SaldosLiqPorContrato(?,?,?,?,?,?,?)";
       else 
         v_proced = "call TBCL_FUNCIONESAS400.TBPL_SaldosPorContrato(?,?,?,?,?,?,?)";

  //out.println("Llamado al programa :"+v_proced);
  CallableStatement cs = t_tax.prepareCall ( "{? = "+ v_proced +"}");
  cs.registerOutParameter(1,Types.CHAR);
  cs.setString(2,v_contra);
  cs.setString(3,v_efe);
  cs.setString(4,"E");
  cs.setString(5,v_sistema);
  cs.setString(6,v_usumfund);
  cs.setString(7,v_passmfund);
  cs.setString(8,v_libreria);
  cs.executeUpdate();
  v_fondos = cs.getString(1);
  cs.close();

/* Final de la modificacion */
 
    /**Si se encuantran datos*/
    if( v_fondos!= null){
    if(!v_fondos.trim().equals("") )
    {//5
     /**Si no hubo error en consulta*/
     if(!v_fondos.substring(0,5).equals("Error"))//error saldos
     {//6
      /**Separar cadena de saldos y fondos*/
 //    out.println("Lo que entrego el 400  v_fondos="+v_fondos);
      String[] v_fonsal2 =  UtilitiesForTax.TBCL_CapturarCadena(v_fondos,v_dim);

      /**Capturar saldos fondos*/
      double[]  v_valor2 =  new double[v_fonsal2.length/2];
      /**Capturar códigos de fondos as400*/
      String[] v_fonsal = new String[v_fonsal2.length];

      if(!v_fonsal2[0].equals("Error"))
      {//7
       for ( int y = 0; y<v_fonsal2.length;y=y+2)
       {//8
        v_codigoas =v_fonsal2[y];
//        out.println("Codigo del fondo ["+y+"] es = "+v_codigoas+"<br>");
        if(v_codigoas != null)
        {//9
         /*@lineinfo:generated-code*//*@lineinfo:324^10*/ /*//  *************************************************************//*//  #sql v_res2 = { SELECT ref_codigo*//*//                                   ,ref_descripcion*//*//                                   ,to_char( nvl(MIF_PORCENTAJE,0)) v_porcentaje*//*//                               FROM tbminimos_fondo*//*//                                    ,tbreferencias*//*//                              WHERE MIF_PRO_CODIGO(+) = :v_pro*//*//                                and MIF_REF_FONDO (+) = ref_codigo*//*//                                and ref_valor = :v_codigoas*//*//                            };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "SELECT ref_codigo\n                                 ,ref_descripcion\n                                 ,to_char( nvl(MIF_PORCENTAJE,0)) v_porcentaje\n                             FROM tbminimos_fondo\n                                  ,tbreferencias\n                            WHERE MIF_PRO_CODIGO(+) =  :1  \n                              and MIF_REF_FONDO (+) = ref_codigo\n                              and ref_valor =  :2 "; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion",theSqlTS); /*// set IN parameters*/ __sJT_st.setString(1,v_pro); __sJT_st.setString(2,v_codigoas); /*// execute query*/ v_res2 = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion.i_res2(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"0TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion",null)); } finally { __sJT_ec.oracleCloseQuery(); } } /*//  *************************************************************/ 







                          /*@lineinfo:user-code*//*@lineinfo:332^26*/

         while(v_res2.next())
         {//10
          v_sumval        = 0;
          /**Código del fondo en Taxbenefits*/
          v_codfon = v_res2.ref_codigo();
          v_fon.addElement(v_codfon);
          /**Variable de session código del fondo */
          session.removeAttribute("s_codfon"+v_num+"");
          session.setAttribute("s_codfon"+v_num+"",(java.lang.Object)v_codfon);

          
          double  l      = new Double(v_fonsal2[y+1]).doubleValue();
          v_valort = l/100;
          /**Variable de session saldo del fondo */
          session.removeAttribute("s_valfon"+v_num+"");
          session.setAttribute("s_valfon"+v_num+"",(java.lang.Object)new Double(v_valort).toString());
          //out.print("Codigo del fondo [" + v_num + "] es = " + v_codfon + ", y el valor es ="+v_valort + "<br>");
          
          /**Consultar valores retirados en otras solicitudes de retiro para restar, y presentar@
          *el saldo del fondo diponible real*/
          /*@lineinfo:generated-code*//*@lineinfo:354^11*/ /*//  *************************************************************//*//  #sql { SELECT NVL(SUM(DIF_VALOR),0)*//*//*//*//                     FROM tbretiros,tbdistribucion_fondos*//*//                    WHERE RET_CON_PRO_CODIGO = :v_pro*//*//                      AND RET_CON_NUMERO= :v_contra*//*//                      AND RET_CONSECUTIVO = DIF_RET_CONSECUTIVO*//*//                      AND dif_ref_fondo = :v_codfon*//*//                      AND (ret_ref_estado= 'SER001' or ret_ref_estado= 'SER003')*//*//                 ORDER BY dif_ref_fondo*//*//                 };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); oracle.jdbc.OracleResultSet __sJT_rs = null; try { String theSqlTS = "SELECT NVL(SUM(DIF_VALOR),0)\n                    \n                   FROM tbretiros,tbdistribucion_fondos\n                  WHERE RET_CON_PRO_CODIGO =  :1  \n                    AND RET_CON_NUMERO=  :2  \n                    AND RET_CONSECUTIVO = DIF_RET_CONSECUTIVO\n                    AND dif_ref_fondo =  :3  \n                    AND (ret_ref_estado= 'SER001' or ret_ref_estado= 'SER003')\n               ORDER BY dif_ref_fondo"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.setFetchSize(2); } /*// set IN parameters*/ __sJT_st.setString(1,v_pro); __sJT_st.setString(2,v_contra); __sJT_st.setString(3,v_codfon); /*// execute query*/ __sJT_rs = __sJT_ec.oracleExecuteQuery(); if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount()); if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO(); /*// retrieve OUT parameters*/ v_sumval = __sJT_rs.getDouble(1); if (__sJT_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO(); } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 








               /*@lineinfo:user-code*//*@lineinfo:363^15*/
          /**Si se encuentran valores para esa misma fecha se va restando del saldo del fondo*/
          if(v_sumval != 0)
          {//11

           v_valort = v_valort - v_sumval;

           v_val.addElement(new Double(v_valort));
           v_encdisfon = true;
          }//11
          else
          {//11

           v_val.addElement(new Double(v_valort));
          }//11

          v_minfon.addElement(v_res2.v_porcentaje());
          /**Variable de session minimo porcentaje a dejar en el fondo */
          session.removeAttribute("s_minfon"+v_num+"");
          session.setAttribute("s_minfon"+v_num+"",(java.lang.Object)v_res2.v_porcentaje());


          v_desfon =  v_res2.ref_descripcion();
          v_desc.addElement(v_desfon);
          /**Variable de session descripción del fondo */
          session.removeAttribute("s_desfon"+v_num+"");
          session.setAttribute("s_desfon"+v_num+"",(java.lang.Object)v_desfon);
          v_validarfondo = true;
         }//termina while  10
         v_res2.close();
         v_num++;
         /**Si no se encuentra parametrización de fondos*/
         if(v_validarfondo == false)
         {//12
          v_pinfon=    i_pagina.TBFL_CABEZA ("Solicitud de Retiro","Error Solicitud de Retiro","","<center>El fondo "+v_codigoas+" no esta parametrizado en el sistema.</center>",false);
          out.println(""+v_pinfon+"");
          out.println("<BR>");
          out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-3)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
          v_piefon = i_pagina.TBFL_PIE;
          out.println("<br>");
          out.println("<br>");
          out.println(""+v_piefon+"");
          out.close();
         }//12
        }//9
       }//8
       

       /**Capturar tipo de transacción*/

       v_tipotran  =   (java.lang.String)session.getAttribute("s_tipotran");
       /**Capturar clase de transacción*/
       v_clasetran =  (java.lang.String)session.getAttribute("s_clasetran");
       /**Capturar parametrización de días menores*/
       v_diasmenor  =  (java.lang.String)session.getAttribute("s_diasmenor");

       /**Capturar parametrización de días mayores*/
       v_diasmayor  =  (java.lang.String)session.getAttribute("s_diasmayor");



       /**Validar el timpo del contrato con los dias permitidos para cada concepto de retiro*/
       if(!v_diasmayor.equals("null"))
       {//13
        if(!v_diasmayor.equals("0"))
        {//14
       v_diasmayor2 =  new Double(v_diasmayor).doubleValue();
         if(v_cumes2>v_diasmayor2)
         {//15
          v_pasa = 0;
         }//15
         else
         {//15
          v_pasa = -1;
         }//15
        }//14
       }//13

       if(!v_diasmenor.equals("null"))
       {
        if(!v_diasmenor.equals("0"))
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
         out.println("v_pasa2" + v_pasa2);
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
       {//15
        /**Capturar concepto de retiro traslado*/
        v_traslado    =  (java.lang.String)session.getAttribute("s_traslado");
        /**Capturar concepto de retiro total*/
        v_total       =  (java.lang.String)session.getAttribute("s_total");


        /**Si el retiro es para traslado*/
        if(v_traslado.equalsIgnoreCase("S"))
        {//16
         /**Variable de session código del banco y númeroi de cuenta*/
         session.removeAttribute("s_banco");
         session.removeAttribute("s_cuenta");
         session.setAttribute("s_banco",v_cadblanco);
         session.setAttribute("s_cuenta",v_cadblanco);

         /**Capturar cadena con código afp e identificador de afp externa o interna*/
         try { v_afp = request.getParameter("v_afp"); } catch (Exception e) { e.printStackTrace(); }
         if(!v_afp.substring(0,1).equals(" "))
         {//17
          v_intext = v_afp.substring(2,8);
          v_afp2 = v_afp.substring(0,2);
          /**Variable de session del código de la afp*/
          session.removeAttribute("s_afp");
          session.setAttribute("s_afp",v_afp2);
         }
         else
         {
          /**Variable de session del código de la afp*/
          session.removeAttribute("s_afp");
          session.setAttribute("s_afp",v_afp);
         }//17

         /**Capturar código producto traslado destino*/
         try { v_proti = request.getParameter("v_proti"); } catch (Exception e) { e.printStackTrace(); }
         /**Variable de session codigo de producto destino*/
         session.removeAttribute("s_proti");
         session.setAttribute("s_proti",v_proti);
         /**Capturar numero contrato traslado destino*/

         try { v_conti = request.getParameter("v_conti"); } catch (Exception e) { e.printStackTrace(); }
         v_conconti =  v_conti.trim().length();
         for(int k=0 ;k <9-v_conconti;k++)
         {
          v_cadena +="0";
         }
         v_conti2 = v_cadena.trim() +v_conti.trim();
         /**Variable de session numero contrato destino*/
         session.removeAttribute("s_conti");
         session.setAttribute("s_conti",v_conti2.trim());
         v_contratotraslado = v_conti2.trim();
         /**Capturar tipo de contrato traslado destino*/
         try { v_tipoconti = request.getParameter("v_tipocontrato"); } catch (Exception e) { e.printStackTrace(); }
         /**Si el concepto es de traslado y la afp es interna*/

        
         if(v_traslado.equals("S") && v_intext.equals("STA001"))
         {
            
          /**Si el contrato  es existente*/
          if(v_tipoconti.equals("E"))
          {
           if(!v_conti.trim().equals("") && !v_proti.substring(0,1).equals(" "))
           {
            /**Validar contrato destino, que pertenesca al mismo dueño del contrato al que se realiza el retiro*/
            /*@lineinfo:generated-code*//*@lineinfo:538^13*/ /*//  *************************************************************//*//  #sql v_resp = { SELECT  con_numero*//*//                                       ,CON_REF_TIPO_IDENTIFICACION*//*//                                       ,CON_NUMERO_IDENTIFICACION*//*//                                  FROM tbcontratos*//*//                                 WHERE con_pro_codigo = :v_proti*//*//                                   AND con_numero = :v_contratotraslado*//*//                               };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "SELECT  con_numero\n                                     ,CON_REF_TIPO_IDENTIFICACION\n                                     ,CON_NUMERO_IDENTIFICACION\n                                FROM tbcontratos\n                               WHERE con_pro_codigo =  :1  \n                                 AND con_numero =  :2 "; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion",theSqlTS); /*// set IN parameters*/ __sJT_st.setString(1,v_proti); __sJT_st.setString(2,v_contratotraslado); /*// execute query*/ v_resp = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion.i_res(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion",null)); } finally { __sJT_ec.oracleCloseQuery(); } } /*//  *************************************************************/ 





                             /*@lineinfo:user-code*//*@lineinfo:544^29*/
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
          else/**Contrato nuevo*/
          {
            String v_retorno_programa = new String("");
            String hubo_insercion = " ";
            
            /*@lineinfo:generated-code*//*@lineinfo:561^13*/ /*//  *************************************************************//*//  #sql v_retorno_programa = { values (TBCL_FuncionesAs400.TBPL_ProgramaContrato(:v_contratotraslado,*//*//                                                                                          :v_sistema,*//*//                                                                                          :v_usumfund,*//*//                                                                                          :v_passmfund,*//*//                                                                                          :v_libreria))*//*//                                                                                           };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBCL_FuncionesAs400.TBPL_ProgramaContrato( :2  ,\n                                                                                         :3  ,\n                                                                                         :4  ,\n                                                                                         :5  ,\n                                                                                         :6  ) \n                                                                                        \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_contratotraslado); __sJT_st.setString(3,v_sistema); __sJT_st.setString(4,v_usumfund); __sJT_st.setString(5,v_passmfund); __sJT_st.setString(6,v_libreria); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_retorno_programa = (String)__sJT_st.getString(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 




                                                                                         /*@lineinfo:user-code*//*@lineinfo:566^89*/
            
            String v_programa = v_retorno_programa.substring(0,v_retorno_programa.indexOf(";",0));
            
            CallableStatement t_cst8i_9 = t_tax.prepareCall("{ call TBPBD_INSERTA_CONTRATO_NUEVO(?,?,?,?,?,?,?,?,?,?) }");
            t_cst8i_9.registerOutParameter(10,Types.VARCHAR);
            t_cst8i_9.setString(1,v_tipoiden);
            t_cst8i_9.setString(2,v_identificacion);
            t_cst8i_9.setString(3,v_nom);
            t_cst8i_9.setString(4,v_ape);
            t_cst8i_9.setString(5, v_fecefe.toString());
            t_cst8i_9.setString(6,v_proti);
            t_cst8i_9.setString(7,v_contratotraslado);
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
            
            //out.println(Double.valueOf(v_efe.toString()).doubleValue());
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
         
         /** Agregado 20150917 REB 
          *  Todos los traslados no son terceros a excepción de ley 1111 y 1607*/
         if(v_traslado.equals("S"))
         {
             v_esTercero ="N";
             session.removeAttribute("esTercero");
             session.setAttribute("esTercero",v_esTercero);
         }         
         /** Agregado 20091118 MOS 
          *  Manejo de terceros en caso de ley 1111*/
         if(v_traslado.equals("S") && v_intext.equals("STA003")){
            v_esTercero ="S";
            session.removeAttribute("esTercero");
            session.setAttribute("esTercero",v_esTercero);
         }
         
        /** Agregado 20150107 GAV 
          *  Manejo de terceros en caso de ley 1607*/
         if(v_traslado.equals("S") && v_intext.equals("STA004")){
            v_esTercero ="S";
            session.removeAttribute("esTercero");
            session.setAttribute("esTercero",v_esTercero);
         }
         
         
         
         
         }
        
        if(v_traslado.equals("N")||(v_traslado.equals("S") && v_esTercero.equals("S")))
        {
         if(v_traslado.equals("N")){ 
           /**Variable de session del código de la afp*/
           session.removeAttribute("s_conti");
           session.removeAttribute("s_proti");
           session.setAttribute("s_proti","  ");
           session.setAttribute("s_conti","  ");
           v_encontro = true;
           session.removeAttribute("s_afp");
           session.setAttribute("s_afp","   ");

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
      
               }} catch (Exception e) { e.printStackTrace(); }
         }else
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
              
                       }} catch (Exception e) { e.printStackTrace(); }
                     
                }else
                {
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
              
                       }} catch (Exception e) { e.printStackTrace(); }
                 }   
            }    
         
        

          try {  
          v_esTercero =(java.lang.String)session.getAttribute("esTercero"); 
          session.setAttribute("esTercero",v_esTercero); 
          if (v_esTercero.equals("S") && v_cuenta.trim().equals("") && v_cuentaLey1607.trim().equals(""))  /**Se agrega la validacion para que la cuenta de titulares y terceros este vacia*/
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
                 v_encontro=true;
                 v_tercero=false;
                   
                 String lista = i_consultaC.ConsultarCliente(v_doc_ter);
                 if (lista.equals("Y"))
                 {
                   v_terlista = true;
                   v_encontro=true;
                   v_mensLista ="<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#FF0000'><b><center>Favor Comunicarse con el área de Financial Crime Prevention a las EXT 4018-4269-4233 antes de continuar con la operación.</center> </b></font>";
                  
                 }
                 
               }
              } 
          }catch (Exception e)  { e.printStackTrace(); }
               
          /**Variable de session del código de banco y número de cuenta*/
          if (!v_existeCuenta ){
          session.removeAttribute("s_banco");
          session.removeAttribute("s_cuenta");
          session.setAttribute("s_banco",v_cadblanco);
          session.setAttribute("s_cuenta",v_cadblanco);
          }

        }
        if(v_total.equalsIgnoreCase("N"))
        {
         /**Capturar valor del retiro*/
         /**Variable de session del tipo de valor a retirar*/
         try { v_valor = request.getParameter("obligatoriov_valor"); } catch (Exception e) { e.printStackTrace(); }
         
         /**Capturar tipo de valor del retiro*/
         try { v_tipov = request.getParameter("v_tipov"); } catch (Exception e) { e.printStackTrace(); }
         session.removeAttribute("s_tipov");
         session.setAttribute("s_tipov",v_tipov);


         /**Aumentar el cobro de 4x1000 s es un retiro parcial para tercero*/
         if (v_esTercero.equals("S") && v_tipov.equals("STV002"))
         {
           /*@lineinfo:generated-code*//*@lineinfo:794^12*/ /*//  *************************************************************//*//  #sql { SELECT NVL(REF_VALOR,0)*//*//*//*//                     FROM tbreferencias*//*//                    WHERE REF_CODIGO = 'STC006'*//*//                 };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); oracle.jdbc.OracleResultSet __sJT_rs = null; try { String theSqlTS = "SELECT NVL(REF_VALOR,0)\n                    \n                   FROM tbreferencias\n                  WHERE REF_CODIGO = 'STC006'"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.setFetchSize(2); } /*// execute query*/ __sJT_rs = __sJT_ec.oracleExecuteQuery(); if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount()); if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO(); /*// retrieve OUT parameters*/ v_cargomil = __sJT_rs.getDouble(1); if (__sJT_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO(); } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 



               /*@lineinfo:user-code*//*@lineinfo:798^15*/
           if (v_cargomil>0) {
             Double v_valor_neto = new Double(v_valor);
             v_valor_neto2 = v_valor;
             v_valor_neto = new Double(v_valor_neto.doubleValue()/v_cargomil);
             v_valor =  String.valueOf((double)Math.round(v_valor_neto.doubleValue()*100)/100);
             out.println("v_valor"+v_valor);
           }
         }
         session.removeAttribute("s_valor");
         session.setAttribute("s_valor",v_valor);

         /**Capturar tipo de distribución de fondo*/
         try { v_distribucion = request.getParameter("v_fondo"); } catch (Exception e) { e.printStackTrace(); }
         /**Variable de session del tipo de distribución de fondos*/
         session.removeAttribute("s_fondo");
         session.setAttribute("s_fondo",v_distribucion);
         /**Si es a prorrata*/
         if(v_distribucion.equals("0"))
         {
          /**Variable de session de tipo de distribución*/
          session.removeAttribute("s_prorrata");
          session.setAttribute("s_prorrata","S");
         }
         else/**Valor o porcentaje*/
         {
          /**Variable de session de tipo de distribución*/
          session.removeAttribute("s_prorrata");
          session.setAttribute("s_prorrata","N");
         }
        }

        v_fon.trimToSize();
        v_desc.trimToSize();
        v_val.trimToSize();

        for (int con = 0; con<v_fon.size();con++)
        {
         v_sumatfondo = v_sumatfondo + new Double(v_val.elementAt(con).toString()).doubleValue();
        }
        /**Agregado 2009/10/28 Para manejo de SARO */
        /**Si el producto y contrato son validos*/
        if(v_encontro && !v_saro)
        {
         /**Si la distribución de fondos es a prorrata*/
         if(v_distribucion.equals("0"))
         {
          v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Distribución de Fondos","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_Esquema","",true,"modulo_retiros.js","");
          out.println(""+v_pintar+"");
          out.println(""+v_mensLista+"");

          /*Cambio para manejo de referencia unica 2009/03/30 MOS */
          String v_contrato_unif = "";
          /*@lineinfo:generated-code*//*@lineinfo:851^11*/ /*//  *************************************************************//*//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_pro,:v_contra)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_pro); __sJT_st.setString(3,v_contra); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_contrato_unif = (String)__sJT_st.getString(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:851^84*/
          out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
          out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");

          out.println("<br>");
          out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
          out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Fondos que conforman el contrato</b></font></center></font></td>");
          out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Saldo Fondo</b></center></font></td>");
          out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Valor a Retirar</b></center></font></td></tr>");
          int x = 0;
          for (int g =0; g<v_fon.size();g++)
          {
           //Calcular prorrata del valor en los fondos
           v_valdiv = new Double(v_val.elementAt(g).toString()).doubleValue()/v_sumatfondo;
           double v_dou  = new Double(v_valor).doubleValue()* v_valdiv;
           long v_valdiv2 = Math.round(v_dou);
           //v_sumatfondo = v_sumatfondo + new Double(v_val.elementAt(g).toString()).doubleValue();
           x++;

           out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='000000'>"+v_desc.elementAt(g).toString()+"</font></td>");
           out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_val.elementAt(g))+"</font></td>");
           out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>$<input name= valor"+x+"  type=text value='"+v_valdiv2+"' size = '11' readonly> </font></td></tr>");
          }
          out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='000000'><b>Total </b></font></td>");
          out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_sumatfondo)+"</font></td>");
          out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><input type=text name = totalesDesde value = '"+NumberFormat.getCurrencyInstance().format(new Double(v_valor))+"'  size = '11' readonly></font></td></tr>");
          out.println("</table></center>");
          out.println("<input name= v_maximo2 type=HIDDEN value='"+x+"' >");
          out.println("<br>");
          /**Dibujar si los saldos han sido modificados por más retiros*/
          if(v_encdisfon)
          {
           out.println("<pre>");
           out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>        Nota:</b>Los valores por fondos pueden estar afectados por retiros del día.</font></center>");
           out.println("</pre>");
          }
          out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
          //consular si el contrato tiene defaul
           /*@lineinfo:generated-code*//*@lineinfo:889^12*/ /*//  *************************************************************//*//  #sql { SELECT nvl(CON_REF_METODO_ORDEN,'X')*//*//                                     ,nvl(CON_REF_METODO_BENEFICIO,'X')*//*//                                     ,nvl(CON_REF_METODO_PENALIZACION,'X')*//*//                                     ,nvl(CON_REF_METODO_CUENTA,'X')*//*//                                     ,nvl(CON_REF_NATURALEZA,'X')*//*//                                     ,nvl(CON_RESPETAR_NATURALEZA,'X')*//*//*//*//                                 FROM tbcontratos*//*//                                WHERE CON_NUMERO = :v_contra*//*//                                  AND CON_PRO_CODIGO = :v_pro*//*//                                };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); oracle.jdbc.OracleResultSet __sJT_rs = null; try { String theSqlTS = "SELECT nvl(CON_REF_METODO_ORDEN,'X')\n                                   ,nvl(CON_REF_METODO_BENEFICIO,'X')\n                                   ,nvl(CON_REF_METODO_PENALIZACION,'X')\n                                   ,nvl(CON_REF_METODO_CUENTA,'X')\n                                   ,nvl(CON_REF_NATURALEZA,'X')\n                                   ,nvl(CON_RESPETAR_NATURALEZA,'X')\n                                \n                               FROM tbcontratos\n                              WHERE CON_NUMERO =  :1  \n                                AND CON_PRO_CODIGO =  :2 "; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"6TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.setFetchSize(2); } /*// set IN parameters*/ __sJT_st.setString(1,v_contra); __sJT_st.setString(2,v_pro); /*// execute query*/ __sJT_rs = __sJT_ec.oracleExecuteQuery(); if (__sJT_rs.getMetaData().getColumnCount() != 6) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(6,__sJT_rs.getMetaData().getColumnCount()); if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO(); /*// retrieve OUT parameters*/ v_metodo_1 = (String)__sJT_rs.getString(1); v_metodo_2 = (String)__sJT_rs.getString(2); v_metodo_3 = (String)__sJT_rs.getString(3); v_metodo_4 = (String)__sJT_rs.getString(4); v_natcon_1 = (String)__sJT_rs.getString(5); v_resnat_1 = (String)__sJT_rs.getString(6); if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO(); } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 















                              /*@lineinfo:user-code*//*@lineinfo:905^30*/
           if(v_metodo_1.equals("X") && v_metodo_2.equals("X") && v_metodo_3.equals("X") &&
              v_metodo_4.equals("X") && v_natcon_1.equals("X") && v_resnat_1.equals("X"))
                 vtienedefault = false;
           else
               vtienedefault = true;





          out.println("<tr><td class=\"td11\" colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Esquema del Retiro</b></font></center></font></td></tr>");
          out.println("<tr><td >");
          out.println("<pre><center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=2>");
          if(vtienedefault)
          {
             out.println("<input type=radio NAME=v_esquema VALUE='0' > Default Skandia <input type=radio NAME=v_esquema VALUE='1' checked> Default Contrato<input type=radio NAME=v_esquema VALUE='2'> Personalizado</font></center><pre>");
             out.println("<SCRIPT LANGUAJE='JavaScript'>");
             out.println("<!-- HIDE FROM OTHER BROWSERS");
             out.println("alert('El contrato presenta esquema de retiro default ');");
             out.println("// STOP HIDING FROM OTHER BROWSERS -->");
             out.println("</SCRIPT>");

          }
          else
          {
             out.println("<input type=radio NAME=v_esquema VALUE='0' checked> Default Skandia <input type=radio NAME=v_esquema VALUE='1'> Default Contrato<input type=radio NAME=v_esquema VALUE='2'> Personalizado</font></center><pre>");          }


          out.println("</td></tr>");
          out.println("</table></center>");
          out.println("</pre>");
          out.println("<br>");
          out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
          out.println("<center><input type=submit value='Aceptar'><input type=button value='Regresar' onclick=' history.go(-1)'><input type=button value='Cancelar' onclick=' history.go(-3)'></center>");
          v_pie = i_pagina.TBFL_PIE;
          out.println("<br>");
          out.println(""+v_pie+"");
          out.close();
         }
         else if(v_distribucion.equals("1"))/**Si distribucion de fondos es por valor*/
              {
               v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Distribución de Fondos","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_Esquema","",true,"modulo_retiros.js","");
               out.println(""+v_pintar+"");
               out.println(""+v_mensLista+"");
               
               /*Cambio para manejo de referencia unica 2009/03/30 MOS */
               String v_contrato_unif = "";
               /*@lineinfo:generated-code*//*@lineinfo:953^16*/ /*//  *************************************************************//*//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_pro,:v_contra)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_pro); __sJT_st.setString(3,v_contra); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_contrato_unif = (String)__sJT_st.getString(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:953^89*/
               out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
               out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
               out.println("<br>");
               out.println("<br>");
               out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
               
               
               
               out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Fondos que conforman el contrato</b></font></center></font></td>");
               out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Saldo Fondo</b></center></font></td>");
               out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Valor actual</b></center></font></td></tr>");
               int x = 0;
               for (int y = 0; y<v_fon.size();y++)
               {
                //v_sumatfondo = v_sumatfondo + new Double(v_val.elementAt(y).toString()).doubleValue();
                x++;
                out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='000000'>"+v_desc.elementAt(y).toString()+" </font></td>");
                out.println("<td align=right ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_val.elementAt(y))+"</font></td>");
                out.println("<td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>$<input name= valor"+x+"  type=text value='' size = '11' onChange='if (validar("+v_sumatfondo+","+v_val.elementAt(y).toString()+",valor"+x+".value, "+v_minfon.elementAt(y).toString()+",v_codigo.valor"+x+" ,"+v_valor+","+v_fon.size()+")==1) return false;'> </font></td></tr>");
               }
               out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='000000'><b>Total </b></font></td>");
               out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_sumatfondo)+"</font></td>");
               out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><input type=text name = totalesDesde value = '0'  size = '11' readonly></font></td></tr>");
               out.println("</table></center>");
               out.println("<input name= v_maximo2 type=HIDDEN value='"+x+"' >");
               out.println("<br>");
               /**Dibujar si los saldos han sido modificados por más retiros*/
               if(v_encdisfon)
               {
                out.println("<pre>");
                out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>        Nota:</b>Los valores por fondos pueden estar afectados por retiros del día.</font></center>");
                out.println("</pre>");
               }
               out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
               out.println("<tr><td class=\"td11\" colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Esquema del Retiro</b></font></center></font></td></tr>");
               out.println("<tr><td >");
               out.println("<pre><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=2>                  <input type=radio NAME=v_esquema VALUE='0' checked> Default Skandia  <input type=radio NAME=v_esquema VALUE='1'> Default Contrato <input type=radio NAME=v_esquema VALUE='2'> Personalizado</font><pre>");
               out.println("</td></tr>");
               out.println("</table></center>");
               out.println("</pre>");
               out.println("<input name= v_maximo2 type=HIDDEN value='"+x+"' size = '9'>");
               out.println("<br>");
               out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
         
               if (v_esTercero.equals("S") && v_tipov.equals("STV002")){
                     out.println("<center><input type=submit value='Aceptar' onclick='if(maximo("+v_valor_neto2+",v_codigo.totalesDesde)==1)return false;' ><input type=button value='Regresar' onclick=' history.go(-1)'><input type=button value='Cancelar' onclick=' history.go(-3)'></center>");
               }
               else 
               {
                     out.println("<center><input type=submit value='Aceptar' onclick='if(maximo("+v_valor+",v_codigo.totalesDesde)==1)return false;' ><input type=button value='Regresar' onclick=' history.go(-1)'><input type=button value='Cancelar' onclick=' history.go(-3)'></center>");
               }
               
               v_pie = i_pagina.TBFL_PIE;
               out.println("<br>");
               out.println(""+v_pie+"");
               out.close();
              }
              else if(v_distribucion.equals("2"))//si 3es por porcentaje
                   {
                    v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiros","Distribución de Fondos","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_Esquema","",true,"modulo_retiros.js","");
                    out.println(""+v_pintar+"");
                    out.println(""+v_mensLista+"");
                    /*Cambio para manejo de referencia unica 2009/03/30 MOS */
                    String v_contrato_unif = "";
                    /*@lineinfo:generated-code*//*@lineinfo:1018^21*/ /*//  *************************************************************//*//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_pro,:v_contra)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"8TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_pro); __sJT_st.setString(3,v_contra); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_contrato_unif = (String)__sJT_st.getString(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:1018^94*/
                    out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
                    out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
                    out.println("<br>");
                    out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
                    out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Fondos que conforman el contrato</b></font></center></font></td>");
                    out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Saldo Fondo</b></center></font></td>");
                    out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>% actual</b></center></font></td></tr>");
                    int x = 0;
                    for (int y = 0; y<v_fon.size();y++)
                    {
                     x++;
                     //v_sumatfondo = v_sumatfondo + new Double(v_val.elementAt(y).toString()).doubleValue();
                     out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='000000'>"+v_desc.elementAt(y).toString()+"    </font></td>");
                     out.println("<td align=right ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_val.elementAt(y))+"</font></td>");
                     out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>%<input name= porcen"+x+"  type=text value='' size = '11' onChange='if (validar2("+v_sumatfondo+","+v_val.elementAt(y).toString()+",porcen"+x+".value, "+v_minfon.elementAt(y)+",v_codigo.porcen"+x+" ,"+v_fon.size()+","+v_valor+")==1) return false;'> </font></td></tr>");
                    }
                    out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='000000'><b>Total </b></font></td>");
                    out.println("<td align=right ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_sumatfondo)+"</font></td>");
                    out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>%<input type=text name = totalesDesde value = '0'  size = '11' readonly></font></td></tr>");
                    out.println("</table></center>");
                    out.println("<input name= v_maximo2 type=HIDDEN value='"+x+"' >");
                    out.println("<br>");
                    /**Dibujar si los saldos han sido modificados por más retiros*/
                    if(v_encdisfon)
                    {
                     out.println("<pre>");
                     out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>        Nota:</b>Los valores por fondos pueden estar afectados por retiros del día.</font></center>");
                     out.println("</pre>");
                    }
                    out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
                    out.println("<tr><td class=\"td11\" colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Esquema del Retiro</b></font></center></font></td></tr>");
                    out.println("<tr><td >");
                    out.println("<pre><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=2>                  <input type=radio NAME=v_esquema VALUE='0' checked> Default Skandia  <input type=radio NAME=v_esquema VALUE='1'> Default Contrato <input type=radio NAME=v_esquema VALUE='2'> Personalizado</font><pre>");
                    out.println("</td></tr>");
                    out.println("</table></center>");
                    out.println("</pre>");
                    out.println("<br>");
                    out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
                    out.println("<center><input type=submit value='Aceptar' onclick='if(maximo2(v_codigo.totalesDesde)==1)return false;'><input type=button value='Regresar' onclick=' history.go(-1)'><input type=button value='Cancelar' onclick=' history.go(-3)'></center>");
                    v_pie = i_pagina.TBFL_PIE;
                    out.println("<br>");
                    out.println(""+v_pie+"");
                    out.close();
                   }
        }
        else
        {/**Si el producto y contrato destino no exiten*/
         v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Distribución de Fondos","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_Esquema","",true,"modulo_retiros.js","");
         out.println(""+v_pintar+"");
         /*Cambio para manejo de referencia unica 2009/03/30 MOS */
         String v_contrato_unif = "";
         /*@lineinfo:generated-code*//*@lineinfo:1070^10*/ /*//  *************************************************************//*//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_pro,:v_contra)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_pro); __sJT_st.setString(3,v_contra); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_contrato_unif = (String)__sJT_st.getString(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:1070^83*/
         out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
         out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
         out.println("<br>");
         
         /**Los datos para el giro a tercero no estan completos*/
         /** Agregado 20090914*/
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
             /**Si el contrato destino no pertenece al mismo afiliado*/
             if(v_encontro2)
             {
              out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b><center> El número de contrato "+v_conti+" no pertenece al cliente por lo tanto no es permitida esta operación.</center> </b></font>");
             }
             else/**Si el contrato destino no existe*/
             {
              out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b><center> No esta creado el numero de contrato "+v_conti+" en el sistema.</center></b></font>");
             }
            }
          }
         out.println("<br>");
         out.println("<center><input type='Button' value='Cancelar' onclick=' history.go(-3)'> <input type='Button' value='Regresar' onclick='history.go(-1)'></center>");
         v_pie = i_pagina.TBFL_PIE;
         out.println("<br>");
         out.println(""+v_pie+"");
         out.close();
        }
       }
       else/**Si el tipo de retiro no cumple con la parametrización de los días*/
       {
        v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Distribución de Fondos","","",true,"","");
        out.println(""+v_pintar+"");
        /*Cambio para manejo de referencia unica 2009/03/30 MOS */
        String v_contrato_unif = "";
        /*@lineinfo:generated-code*//*@lineinfo:1113^9*/ /*//  *************************************************************//*//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_pro,:v_contra)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"10TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Distribucion",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_pro); __sJT_st.setString(3,v_contra); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_contrato_unif = (String)__sJT_st.getString(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:1113^82*/
        out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
        out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
        out.println("<br>");
        out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>No es posible realizar la solicitud de retiro, los días del contrato deben ser "+v_mendias+" a "+v_diascon+". </b></font></center>");
        out.println("<br>");
        out.println("<center><input type='Button' value='Cancelar' onclick=' history.go(-3)'> <input type='Button' value='Regresar' onclick='history.go(-1)'></center>");
        v_pie = i_pagina.TBFL_PIE;
        out.println("<br>");
        out.println(""+v_pie+"");
        out.close();
       }
      }
      else
      {//error saldo
       v_pintar=    i_pagina.TBFL_CABEZA ("Solicitud de Retiro","Error Distribución de Fondos","","<center>Consulta de saldo para el  contrato  "+v_contra+" en el AS400 no devuelve datos.</center>",false);
       out.println(""+v_pintar+"");
       out.println("<BR>");
       out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-3)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
       v_pie = i_pagina.TBFL_PIE;
       out.println("<br>");
       out.println("<br>");
       out.println(""+v_pie+"");
       out.close();
      }
     }
     else
     {//error saldo
      v_pintar=    i_pagina.TBFL_CABEZA ("Solicitud de Retiro","Error Distribución de Fondos","","<center>Consulta de saldo del contrato. Mensaje de error:"+v_fondos+"</center>",false);
      out.println(""+v_pintar+"");
      out.println("<BR>");
      out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-3)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
      v_pie = i_pagina.TBFL_PIE;
      out.println("<br>");
      out.println("<br>");
      out.println(""+v_pie+"");
      out.close();
     }
    }
   }
    else
    {//error saldo
     v_pintar=    i_pagina.TBFL_CABEZA ("Solicitud de Retiro","Error Distribución de Fondos","","<center>Consulta de saldo para el  contrato  "+v_contra+" en el AS400 no devuelve datos.</center>",false);
     out.println(""+v_pintar+"");
     out.println("<BR>");
     out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-3)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
     v_pie = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println("<br>");
     out.println(""+v_pie+"");
     out.close();
    }
   }
   else
   {
    v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Distribución de Fondos","","<center>Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.</center>",false);
    out.println(""+v_pintar+"");
    v_pie = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-3)'></center>");
    out.println(""+v_pie+"");
    out.close();
   }
    t_tax.close();
   /**Cerrar conexión*/
    ctx3.getConnection().close();
  
   //i_conexion.TBCL_DesconectarBaseDatos();//.getConnection().close();
  }
  catch (Exception ex)
  {//manejo de error
   String v_pintar="";
   String error = ex.toString();
   if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
   {
    v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Distribución de Fondos","","<center>No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.</center>",false);
   }
   else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
        {
         v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Distribución de Fondos","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
        }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_pintar =i_pagina.TBFL_CABEZA("Solicitud de Retiro","Distribución de Fondos","","<center>Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.</center>",false);
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_pintar =  i_pagina.TBFL_CABEZA("Solicitud de Retiro","Distribución de Fondos","","<center>Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.</center>",false);
                     }
                  else
                  {
                   v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Distribución de Fondos","","<center>Mensaje de Error :"+ex+".</center>",false);
                  }
   out.println(""+v_pintar+"");
   out.println("<br>");
   out.println("<center><input type=button value='Cancelar' onclick=' history.go(-3)'></center>");
   String v_pie = i_pagina.TBFL_PIE;
   out.println(""+v_pie+"");
   out.close();
  }
 }
 

}