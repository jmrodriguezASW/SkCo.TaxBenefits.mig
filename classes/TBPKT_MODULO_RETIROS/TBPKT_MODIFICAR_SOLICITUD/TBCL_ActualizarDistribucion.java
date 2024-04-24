/*@lineinfo:filename=TBCL_ActualizarDistribucion*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD;

import sqlj.runtime.*;
import javax.servlet.*;
import java.io.*;
import javax.servlet.http.*;
import java.sql.*;
import sqlj.runtime.ref.DefaultContext ;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;

/*
Modificacion:
Se elimina el import debido a que la conexion
al AS400 se hace desde la base de datos
*/
//import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;
import TBPKT_IAS.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_FECHAS.*;



/**Clase que muestra al usuario el estado actual del tipo de distribución
* y le presenta las nuevas opciones de modificación al usuario.*/
//public class TBCL_ActualizarDistribucion extends Object
public class TBCL_ActualizarDistribucion extends HttpServlet
{
 /**Iterator de la descripción y código de los fondos que conforman el contrato.*/
 /*@lineinfo:generated-code*//*@lineinfo:29^2*/ /*//  *************************************************************//*//  SQLJ iterator declaration:*//*//  *************************************************************/ public static class i_res extends sqlj.runtime.ref.ResultSetIterImpl implements sqlj.runtime.NamedIterator { public i_res(sqlj.runtime.profile.RTResultSet resultSet) throws java.sql.SQLException { super(resultSet); ref_codigoNdx = findColumn("ref_codigo"); ref_descripcionNdx = findColumn("ref_descripcion"); v_porcentajeNdx = findColumn("v_porcentaje"); m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet(); } private oracle.jdbc.OracleResultSet m_rs; public String ref_codigo() throws java.sql.SQLException { return (String)m_rs.getString(ref_codigoNdx); } private int ref_codigoNdx; public String ref_descripcion() throws java.sql.SQLException { return (String)m_rs.getString(ref_descripcionNdx); } private int ref_descripcionNdx; public String v_porcentaje() throws java.sql.SQLException { return (String)m_rs.getString(v_porcentajeNdx); } private int v_porcentajeNdx; } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:29^96*/


 i_res v_res;/**Variable tipo iterator i_res*/


 /**Procedimiento local que consulta a la base de datos la información del retiro a modificar.*/
 public void TBPL_ActualizarDistribucion(PrintWriter out,HttpSession session,HttpServletRequest request,String nuevaCadena)
 {
  /**Instancias de clase*/
  STBCL_GenerarBaseHTML i_pagina = new STBCL_GenerarBaseHTML();/**Instancia de la clase TBCL_GenerarBaseHTML*/
  TBCL_Validacion      i_valusu = new TBCL_Validacion ();/**Instancia de la clase TBCL_Validacion*/

  //TBCL_FuncionesAs400  i_fondos = new TBCL_FuncionesAs400();/**Instancia de la clase TBCL_FuncionesAs400*/

  TBCL_Fecha           i_fecha  = new TBCL_Fecha();/**Instancia de la clase TBCL_Fecha*/
  //TBCL_ConexionSqlj    i_conexion = new TBCL_ConexionSqlj();
  try
  {
   /**Leer de archivo connection.properties url,usuario y paswword a la base de datos.*/
   String[] v_valusu = new String[3];
   v_valusu          =i_valusu.TBFL_ValidarUsuario();
   /**Realizar conexion a la base de datos*/
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   Connection t_tax =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
   DefaultContext ctx = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
   DefaultContext.setDefaultContext(ctx);
   //i_conexion.TBCL_ConexionBaseDatos();


   /**Definicion de variables*/
   /**Tipo String*/
   String v_fechaefec        = "";/**Variable Fecha efectiva del retiro.*/
   String v_prorrata         = "";/**Variable Indicador de distribucion de fondos.*/
   String v_cod              = "";/**Variable Código del fondo en TaxBenefits*/
   String v_descri           = "";/**Variable Descripción de fondos*/
   String v_fonsaldo         = "";/**Variable Código del fondo en el AS400.*/
   String v_valorfondo       = "";/**Variable Saldo en el fondo.*/
   String v_sistema          = "";/**Variable nombre del sistema multifund.*/
   String v_usumfund         = "";/**Variable nombre Usuario multifund.*/
   String v_passmfund        = "";/**Variable nombre password de usuario multifund.*/
   String v_libreria         = "";/**Variable nombre libreria en multifund.*/
   String vefe               = "";/**Variable fecha efectiva del retiro en String*/
   String v_efe              = "";/**Variable fecha formato para IBM*/
   String v_fondos           = "";/**Variable cadena de los saldo en los fondos que conforman el contrato*/
   String v_valoret          = "";/**Variable valor del retiro en String*/
   String v_nombre           = "";/**Variable nombre del afiliado*/
   String v_apellidos        = "";/**Variable apellidos del afiliado*/
   String v_contra           = "";/**Variable número del contrato*/
   String v_pro              = "";/**Variable código del producto*/
   String v_consecutivo      = "";/**Variable consecutivo del retiro a modificar en String*/
   String v_pintar           = "";/**Variable dibujar pagina*/
   String v_piefon           = "";/**Variable dibujar pie de pagina error fondos*/
   String v_pie              = "";/**Variable dibujar pie de pagina*/
   String v_retirototal      = "";/**Variable indicador de retiro total*/
   //Tipo double
   double v_valorretiro      = 0;/**Variable valor del retiro*/
   double v_saldo            = 0;/**Variable saldo en fondo*/

   //Tipo int
   int v_resmulti            = 0;/**Variable indicador de exito o fracaso de funcion TB_FREFERENCIAS_MULTI*/

   int v_conret              = 0;/**Variable consecutivo del retiro a modificar en int*/
   int v_dim                 = 0;/**Variable dim*/
   int p                     = 0;/**Variable contador*/
   //Tipo boolean
   boolean v_encfon         = false;/**Variable Control de existencia del fondo*/


   /**Verificar que las variables de session no expiren*/
   if((java.lang.String)session.getAttribute("s_contrato") != null ||(java.lang.String)session.getAttribute("s_producto")!= null)
   {
    /**Capturar variables de session*/
    v_nombre           = (java.lang.String)session.getAttribute("s_nombres");
    v_apellidos        = (java.lang.String)session.getAttribute("s_apellidos");
    v_contra           = (java.lang.String)session.getAttribute("s_contrato");
    v_pro              = (java.lang.String)session.getAttribute("s_producto");
    v_consecutivo      = (java.lang.String)session.getAttribute("s_conret");
    v_conret           = new Integer(v_consecutivo).intValue();


    /**Selecciona fecha efectiva,valor del retiro y retiro prorrata segun el consecutivo del retiro*/
    /*@lineinfo:generated-code*//*@lineinfo:111^5*/ /*//  *************************************************************//*//  #sql { SELECT TO_CHAR(RET_FECHA_EFECTIVA,'YYYYMMDD')*//*//                    ,RET_VALOR*//*//                    ,RET_RETIRO_PRORRATA*//*//                    ,PRC_RETIRO_TOTAL*//*//*//*//                FROM TBRETIROS*//*//                     ,TBPRODUCTOS_CONCEPTOS*//*//               WHERE RET_CON_PRO_CODIGO            = :v_pro*//*//                 AND RET_CON_NUMERO                = :v_contra*//*//                 AND  RET_CONSECUTIVO              = :v_conret*//*//                 AND RET_COT_REF_TRANSACCION      = PRC_COT_REF_TRANSACCION*//*//                 AND RET_COT_REF_TIPO_TRANSACCION = PRC_COT_REF_TIPO_TRANSACCION*//*//                 AND RET_COT_REF_CLASE_TRANSACCION= PRC_COT_REF_CLASE_TRANSACCION*//*//*//*//              };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); oracle.jdbc.OracleResultSet __sJT_rs = null; try { String theSqlTS = "SELECT TO_CHAR(RET_FECHA_EFECTIVA,'YYYYMMDD')\n                  ,RET_VALOR\n                  ,RET_RETIRO_PRORRATA\n                  ,PRC_RETIRO_TOTAL\n               \n              FROM TBRETIROS\n                   ,TBPRODUCTOS_CONCEPTOS\n             WHERE RET_CON_PRO_CODIGO            =  :1  \n               AND RET_CON_NUMERO                =  :2  \n               AND  RET_CONSECUTIVO              =  :3  \n               AND RET_COT_REF_TRANSACCION      = PRC_COT_REF_TRANSACCION\n               AND RET_COT_REF_TIPO_TRANSACCION = PRC_COT_REF_TIPO_TRANSACCION\n               AND RET_COT_REF_CLASE_TRANSACCION= PRC_COT_REF_CLASE_TRANSACCION"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_ActualizarDistribucion",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.setFetchSize(2); } /*// set IN parameters*/ __sJT_st.setString(1,v_pro); __sJT_st.setString(2,v_contra); __sJT_st.setInt(3,v_conret); /*// execute query*/ __sJT_rs = __sJT_ec.oracleExecuteQuery(); if (__sJT_rs.getMetaData().getColumnCount() != 4) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(4,__sJT_rs.getMetaData().getColumnCount()); if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO(); /*// retrieve OUT parameters*/ v_fechaefec = (String)__sJT_rs.getString(1); v_valorretiro = __sJT_rs.getDouble(2); if (__sJT_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); v_prorrata = (String)__sJT_rs.getString(3); v_retirototal = (String)__sJT_rs.getString(4); if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO(); } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 
















            /*@lineinfo:user-code*//*@lineinfo:128^12*/

    /** Si no hubo error al consultar*/
    if(!v_fechaefec.equals("null"))
    {
     v_valoret = new Double(v_valorretiro).toString();
     session.removeAttribute("s_valoret");
     session.setAttribute("s_valoret",(java.lang.Object)v_valoret);

     /**Si no es retiro total*/
     if(!v_retirototal.equalsIgnoreCase("S"))
     {
      /** Consultar los parametros de conexion para el AS400*/
      /*@lineinfo:generated-code*//*@lineinfo:141^7*/ /*//  *************************************************************//*//  #sql v_resmulti = { values(TB_FREFERENCIAS_MULTI(:v_sistema,:v_usumfund,:v_passmfund, :v_libreria)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TB_FREFERENCIAS_MULTI( :2  , :3  , :4  ,  :5  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_ActualizarDistribucion",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_sistema); __sJT_st.setString(3,v_usumfund); __sJT_st.setString(4,v_passmfund); __sJT_st.setString(5,v_libreria); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_resmulti = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_sistema = (String)__sJT_st.getString(2); v_usumfund = (String)__sJT_st.getString(3); v_passmfund = (String)__sJT_st.getString(4); v_libreria = (String)__sJT_st.getString(5); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:141^128*/
      /**Llamar función para consultar saldos en AS400*/
      //v_fondos = i_fondos.TBPL_SaldosPorContrato(v_contra,v_fechaefec,"E",v_sistema,v_usumfund,v_passmfund,v_libreria);
      /*
      Modificacion:
      Se añade el procedimiento de invocacion a un procedimiento del AS400
      */

      CallableStatement cs = t_tax.prepareCall ( "{? = call TBCL_FUNCIONESAS400.TBPL_SaldosPorContrato(?,?,?,?,?,?,?)}");
      cs.registerOutParameter(1,Types.CHAR);
      cs.setString(2,v_contra);
      cs.setString(3,v_fechaefec);
      cs.setString(4,"E");
      cs.setString(5,v_sistema);
      cs.setString(6,v_usumfund);
      cs.setString(7,v_passmfund);
      cs.setString(8,v_libreria);
      cs.executeUpdate();
      v_fondos = cs.getString(1);
      cs.close();
      t_tax.close();



      /**Si se encuentran datos*/
      if(!v_fondos.trim().equals(""))
      {
       /**Si no hubo ningun error*/
       if(!v_fondos.substring(0,5).equals("Error"))//error saldos
       {
        /**Consultar numero de fondos*/
        /*@lineinfo:generated-code*//*@lineinfo:172^9*/ /*//  *************************************************************//*//  #sql { SELECT count(*)*//*//*//*//                  FROM tbreferencias*//*//                 WHERE ref_codigo like 'UFO%'*//*//                 };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); oracle.jdbc.OracleResultSet __sJT_rs = null; try { String theSqlTS = "SELECT count(*)\n                 \n                FROM tbreferencias\n               WHERE ref_codigo like 'UFO%'"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_ActualizarDistribucion",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.setFetchSize(2); } /*// execute query*/ __sJT_rs = __sJT_ec.oracleExecuteQuery(); if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount()); if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO(); /*// retrieve OUT parameters*/ v_dim = __sJT_rs.getInt(1); if (__sJT_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO(); } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 



               /*@lineinfo:user-code*//*@lineinfo:176^15*/
        v_dim = (v_dim - 1) * 2;
        /**Declarar string segun el numero de fondos*/

        String[] v_fonsal2 =  UtilitiesForTax.TBCL_CapturarCadena(v_fondos,v_dim);
        /**Saldo fondos*/
        double[]  v_valor =  new double[v_fonsal2.length/2];
        /**Código del fondo en al AS400 */
        String[] v_fonsal = new String[v_fonsal2.length];

        if(!v_fonsal2[0].equals("Error"))
        {
         for(int o=0; o<v_fonsal2.length;o++  )
         { //for
          if(v_fonsal2[p] != null)
          {//if1
           double  l      = new Double(v_fonsal2[p+1]).doubleValue();
           v_fonsal[o]= v_fonsal2[p];

           v_saldo = v_saldo+(l/100);
           v_valor[o] = (l/100);
           p=p+2;
          }//end if1
         }//end for
         //se dibuja pagina de respuesta
         v_pintar  =    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiro","Modificar Distribución de Fondos","TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBS_ActualizarDistribucion","",true,"","");
         out.println(""+v_pintar+"");
         String v_contrato_unif = "";
         /*@lineinfo:generated-code*//*@lineinfo:204^10*/ /*//  *************************************************************//*//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_pro,:v_contra)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_ActualizarDistribucion",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_pro); __sJT_st.setString(3,v_contra); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_contrato_unif = (String)__sJT_st.getString(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:204^83*/
         out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
         out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nombre+"  <b> Apellidos </b>"+v_apellidos+" </CENTER></font>");
         out.println("<br>");
         out.println("<br>");
         out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
         out.println("<tr><td class=\"td11\" colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='2'><font color='#FFFFFF' ><b>Distribución  de Fondos para el Retiro</b></font></center></font></td></tr>");
         out.println("<tr><td >");
         //si retiro es a prorrata
         if(v_prorrata.equals("S"))
         {
          out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=2><input type=radio NAME=v_fondo VALUE='1' checked>  Por valor  <input type=radio NAME=v_fondo VALUE='2'>  Por Porcentaje</font></center>");
         }
         else//por valor o porcentaje
         {
          out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=2><input type=radio NAME=v_fondo VALUE='0' checked>  A Prorrata  <input type=radio NAME=v_fondo VALUE='1'>  Por valor  <input type=radio NAME=v_fondo VALUE='2'>  Por Porcentaje</font></center>");
         }
         out.println("</td></tr>");
         out.println("</table></center>");
         out.println("<pre>");
         //se consultan los codigos,descripción y valor minimo de los fondos que conforman el contrato
         int v_nom = 1;
         for ( int y = 0; y<v_fonsal.length;y++)
         {
          v_fonsaldo =v_fonsal[y];
          if(v_fonsaldo != null)
          {
           /*@lineinfo:generated-code*//*@lineinfo:231^12*/ /*//  *************************************************************//*//  #sql v_res = { SELECT ref_codigo*//*//                                   ,ref_descripcion*//*//                                   ,to_char( nvl(MIF_PORCENTAJE,0)) v_porcentaje*//*//                               FROM tbminimos_fondo*//*//                                    ,tbreferencias*//*//                              WHERE MIF_PRO_CODIGO(+) = :v_pro*//*//                                and MIF_REF_FONDO (+) = ref_codigo*//*//                                and ref_valor = :v_fonsaldo*//*//                             };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "SELECT ref_codigo\n                                 ,ref_descripcion\n                                 ,to_char( nvl(MIF_PORCENTAJE,0)) v_porcentaje\n                             FROM tbminimos_fondo\n                                  ,tbreferencias\n                            WHERE MIF_PRO_CODIGO(+) =  :1  \n                              and MIF_REF_FONDO (+) = ref_codigo\n                              and ref_valor =  :2 "; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_ActualizarDistribucion",theSqlTS); /*// set IN parameters*/ __sJT_st.setString(1,v_pro); __sJT_st.setString(2,v_fonsaldo); /*// execute query*/ v_res = new TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_ActualizarDistribucion.i_res(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"4TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_ActualizarDistribucion",null)); } finally { __sJT_ec.oracleCloseQuery(); } } /*//  *************************************************************/ 







                           /*@lineinfo:user-code*//*@lineinfo:239^27*/
           //minetras encuentre datos
           while(v_res.next())
           {
            v_encfon = true;
            v_fonsal[y] = v_res.ref_descripcion();
            v_cod = v_res.ref_codigo();
            /**Variable de session el código del fondo*/
            session.removeAttribute("s_codfon"+v_nom+"");
            session.setAttribute("s_codfon"+v_nom+"",(java.lang.Object)v_cod);
            /**Variable minimo porcentaje a dejar por fondo*/
            session.removeAttribute("s_minfon"+v_nom+"");
            session.setAttribute("s_minfon"+v_nom+"",(java.lang.Object)v_res.v_porcentaje());
            /**Variable descripción del fondo*/
            v_descri = v_fonsal[y];

            /**Variable de session descripción del fondo*/
            session.removeAttribute("s_desfon"+v_nom+"");
            session.setAttribute("s_desfon"+v_nom+"",(java.lang.Object)v_descri);
            v_valorfondo = new Double(v_valor[y]).toString();
            /**Variable de session  saldo del fondo*/
            session.removeAttribute("s_valfon"+v_nom+"");
            session.setAttribute("s_valfon"+v_nom+"",(java.lang.Object)v_valorfondo);
           }
           v_res.close();
           v_nom++;
           //Si no se encuentra fondo se dibuja mensaje de error
           if(v_encfon==false)
           {
            out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=2><center>El fondo "+v_fonsaldo+" no esta parametrizado en el sistema.</center></font></center>");
            out.println("<BR>");
            out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
            v_piefon = i_pagina.TBFL_PIE;
            out.println("<br>");
            out.println("<br>");
            out.println(""+v_piefon+"");
            out.close();
           }
          }
          v_encfon = false;
         }
         out.println("<input name= v_maximo type=HIDDEN value='"+v_nom+"' size = '9'>");
         out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
         out.println("<center><input type=submit value='Aceptar'  ><input type=button value='Regresar' onclick=' history.go(-1)'><input type=button value='Cancelar' onclick=' history.go(-2)'></center>");
         v_pie = i_pagina.TBFL_PIE;
         out.println(""+v_pie+"");
         out.println("<br>");
         out.close();
        }
        else
        {//error al consultar saldos
         v_pintar =   i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Error al Modificar Distribución de Fondos","","<center>Consulta de saldo para el  contrato  "+v_contra+" en el AS400 no devuelve datos.</center>",false);
         out.println(""+v_pintar+"");
         out.println("<BR>");
         out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
         v_pie = i_pagina.TBFL_PIE;
         out.println("<br>");
         out.println("<br>");
         out.println(""+v_pie+"");
         out.close();
        }
       }
       else
       {//error al consultar saldos
        v_pintar=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Error al Modificar Distribución de Fondos","","<center>Consulta de saldo del contrato. Mensaje de error:"+v_fondos+"</center>",false);
        out.println(""+v_pintar+"");
        out.println("<BR>");
        out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
        v_pie = i_pagina.TBFL_PIE;
        out.println("<br>");
        out.println("<br>");
        out.println(""+v_pie+"");
        out.close();
       }
      }
      else
      {//error al consultar saldos
       v_pintar=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Error al Modificar Distribución de Fondos","","<center>Consulta de saldo para el  contrato  "+v_contra+" en el AS400 no devuelve datos.</center>",false);
       out.println(""+v_pintar+"");
       out.println("<BR>");
       out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
       v_pie = i_pagina.TBFL_PIE;
       out.println("<br>");
       out.println("<br>");
       out.println(""+v_pie+"");
       out.close();
      }
     }
     else
     {//si el retiro es total no se permite hacer distribucion de fondos
      v_pintar=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Error al Modificar Distribución de Fondos","","<center>No existe distribución de fondos para la solicitud de retiro.</center>",false);
      out.println(""+v_pintar+"");
      out.println("<BR>");
      out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
      v_pie= i_pagina.TBFL_PIE;
      out.println("<br>");
      out.println("<br>");
      out.println(""+v_pie+"");
      out.close();
     }
    }
    else
    {//si se da un error al consultar información del retiro
     v_pintar=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Error al Modificar Distribución de Fondos","","<center>Error en el proceso de modificación de distribución de fondos</center>",false);
     out.println(""+v_pintar+"");
     out.println("<BR>");
     out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
     v_pie= i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println("<br>");
     out.println(""+v_pie+"");
     out.close();
    }
   }
   else
   {//si la session a terminado
    v_pintar =    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiro","Error al Modificar Distribución de Fondos","","<center>Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.</center>",false);
    out.println(""+v_pintar+"");
    v_pie = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'></center>");
    out.println(""+v_pie+"");
    out.close();
   }
   /**Cerrar conexión*/
   ctx.getConnection().close();
   //i_conexion.TBCL_DesconectarBaseDatos();
  }
  catch(Exception ex)
  { //manejo de error
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
   String v_pintara=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Error en Modificar Solicitud de Retiro","","<center>"+v_menex+"<center>",false);
   out.println(""+v_pintara+"");
   out.println("<BR>");
   out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
   String v_piea = i_pagina.TBFL_PIE;
   out.println("<br>");
   out.println("<br>");
   out.println(""+v_piea+"");
   out.close();
  }
 }
}