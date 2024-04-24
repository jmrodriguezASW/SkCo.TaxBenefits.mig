/*@lineinfo:filename=TBCL_ModificarFondos*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD;

import sqlj.runtime.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import sqlj.runtime.ref.DefaultContext ;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import java.util.Vector;
import java.text.NumberFormat;
import java.text.DecimalFormat;

/**Clase que muestra al usuario la distribución actual de saldos de los fondos@
*y el valor retirado de estos, permite modificar esta distribución.*/
public class TBCL_ModificarFondos extends HttpServlet
{
 /**Iterator de valor retirado de fondos de otras solicitudes de retiro.*/
 /*@lineinfo:generated-code*//*@lineinfo:20^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_distri
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_distri(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    DIF_VALORNdx = findColumn("DIF_VALOR");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double DIF_VALOR()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(DIF_VALORNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int DIF_VALORNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:20^55*/
 /**Iterator de valor y porcentaje retirado de fondos de la solicitudes de retiro a modificar.*/
 /*@lineinfo:generated-code*//*@lineinfo:22^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_distri2
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_distri2(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    DIF_VALORNdx = findColumn("DIF_VALOR");
    DIF_PORCENTAJENdx = findColumn("DIF_PORCENTAJE");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double DIF_VALOR()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(DIF_VALORNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int DIF_VALORNdx;
  public double DIF_PORCENTAJE()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(DIF_PORCENTAJENdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int DIF_PORCENTAJENdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:22^79*/
 /**Variable tipo iterator i_distri*/
 i_distri v_distri;
 /**Variable tipo iterator i_distri2*/
 i_distri2 v_distri2;
 /**Procedimiento que consulta valor y porcentaje retirado de los fondos y dibnuja página de respuesta*/
 public void TBPL_ModificarFondos (HttpSession session,HttpServletRequest request,PrintWriter out,String nuevaCadena )
 {
  /**Instancias de clase*/
  STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();/**Instancia de la clase TBCL_GenerarBaseHTML*/
  TBCL_Validacion     i_valusu = new     TBCL_Validacion ();/**Instancia de la clase TBCL_Validacion*/
  //TBCL_ConexionSqlj    i_conexion = new TBCL_ConexionSqlj();/**Instancia de la clase TBCL_ConexionSqlj*/
  try
  {
   DecimalFormat v_format = new  DecimalFormat("####,####.##");
   String[] v_valusu = new String[3];
   v_valusu=i_valusu.TBFL_ValidarUsuario();
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   DefaultContext ctx8 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
   DefaultContext.setDefaultContext(ctx8);
   /**Conectar al abase de datos*/
   //i_conexion.TBCL_ConexionBaseDatos();
   /**Definicion de variables*/
   /**Tipo Vector*/
   Vector  v_fon           = new Vector();/** Vector código fondo*/
   Vector  v_desc          = new Vector();/** Vector  descripción fondo*/
   Vector  v_val           = new Vector();/** Vector saldo fondo*/
   Vector  v_valret        = new Vector();/** Vector valor retirado del fondo*/
   Vector  v_porret        = new Vector();/** Vector porcentaje retirado del fondo*/
   Vector  v_minfon        = new Vector();/** Vector minimo a dejar en el fondo*/
   /**Tipo boolean*/
   boolean v_encontrofondo = false;/**Verificar existencia de otras solicitudes de retiro para el contrato*/
   boolean v_encontrofondo2= true;/**Verificar distribución de fondos del retiro a actualizar*/
   /**Tipo String*/
   String v_codigofondo    = "";/**Variable código fondo*/
   String v_valorfondo     = "";/**Variable saldo fondo string*/
   String v_codfonvalor    = "";/**Variable código fondo valor*/
   String v_descripcionfondo= "";/**Variable descripción fondos*/
   String v_minimofon      = "";/**Variable minimo fondo*/
   String v_fondo          = "";/**Variable tipo de distribución de fondo*/
   String v_contra         = "";/**Variable numero del contrato*/
   String v_pro            = "";/**Variable código del producto*/
   String v_nom            = "";/**Variable nombre del afiliado*/
   String v_ape            = "";/**Variable apellido del afiliado*/
   String v_fecefe         = "";/**Variable fecha efectiva del retiro*/
   String v_consecutivo    = "";/**Variable consecutivo del retiro*/
   String v_valre          = "";/**Variable valor retiro*/
   String v_maximo         = "";/**Variable maximo fondo*/
   String v_pintar         = "";/**Variable dibujar inicio página*/
   String v_pie            = "";/**Variable dibujar final página*/
   /**Tipo int*/
   int v_conret            = 0;
   /**Tipo double*/
   double v_valre2         = 0;/**Valor retiro numerico*/
   double v_valdiv         = 0;/**Valor retiro prorrata*/
   double v_valort         = 0;/**Valor saldo fondo númerico*/
   double v_sumatfondo     = 0;/**Sumatoria  saldos de fondos*/
   double v_sumatvalor     = 0;/**Sumatoria de valoresretirados de fondos*/
   double v_sumatporcentaje= 0;/**Sumatoria de porcentajes retirados*/
   double vsumvalor        = 0;/**sumatoria de valores retirados de fondos de otras  solicitudes de retiro*/
   /**Verificar que las variables de session no expiren*/
   if((java.lang.String)session.getAttribute("s_contrato") != null ||(java.lang.String)session.getAttribute("s_producto") != null)
   {
    /**Capturar variables de session*/
    v_contra         = (java.lang.String)session.getAttribute("s_contrato");/**Tomar contrato*/
    v_pro            = (java.lang.String)session.getAttribute("s_producto");/**Tomar  producto*/
    v_nom            = (java.lang.String)session.getAttribute("s_nombres");/**Tomar  nombres*/
    v_ape            = (java.lang.String)session.getAttribute("s_apellidos");/**Tomar apellidos*/
    v_fecefe         = (java.lang.String)session.getAttribute("s_fecefe");/**Tomar fehca efectiva*/
    //v_consecutivo    = (java.lang.String)session.getAttribute("s_conret");/**Tomar consecutivo del retiro a modificar fondos*/
    v_conret         = new Integer ((java.lang.String)session.getAttribute("s_conret")).intValue();/**Consecutivo retiro numerico*/
    //v_valre          = (java.lang.String)session.getAttribute("s_valoret");/**Tomar Valor retiro */
    v_valre2         = new Double((java.lang.String)session.getAttribute("s_valoret")).doubleValue();/**Valor retiro numerico*/
    v_maximo         = "";/**Maximo fondo*/
    /**Capturar maximos fondos*/
    try { v_maximo = request.getParameter("v_maximo"); } catch (Exception e) { e.printStackTrace(); }
    session.removeAttribute("s_maximo");
    session.setAttribute("s_maximo",v_maximo);

    /**Tomar la descripcion de los fondos y mostrar el valor de los fondos disponibles reales*/
    for ( int i=1;i< new Integer(v_maximo).intValue();i++)
    {
     v_encontrofondo2 = true;
     v_codigofondo    =(java.lang.String)session.getAttribute("s_codfon"+i+"");

     v_fon.addElement(v_codigofondo);
     //v_valorfondo     = (java.lang.String)session.getAttribute("s_valfon"+i+"");
     v_valort         = new Double((java.lang.String)session.getAttribute("s_valfon"+i+"")).doubleValue();

     /**Consulta valor de fondos retirados para esa fecha*/
     /*@lineinfo:generated-code*//*@lineinfo:112^6*/

//  ************************************************************
//  #sql { SELECT NVL(SUM(DIF_VALOR),0) 
//                FROM tbretiros,tbdistribucion_fondos
//               WHERE RET_CON_PRO_CODIGO  = :v_pro
//                 AND RET_CON_NUMERO      = :v_contra
//                 AND RET_CONSECUTIVO     <> :v_conret
//                 AND RET_CON_PRO_CODIGO  = DIF_RET_CON_PRO_CODIGO
//                 AND RET_CON_NUMERO      = DIF_RET_CON_NUMERO
//                 AND RET_CONSECUTIVO     = DIF_RET_CONSECUTIVO
//                 AND dif_ref_fondo       = :v_codigofondo
//                 AND (RET_REF_ESTADO     = 'SER001' or RET_REF_ESTADO = 'SER003')
//            ORDER BY dif_ref_fondo
//             };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT NVL(SUM(DIF_VALOR),0)  \n              FROM tbretiros,tbdistribucion_fondos\n             WHERE RET_CON_PRO_CODIGO  =  :1  \n               AND RET_CON_NUMERO      =  :2  \n               AND RET_CONSECUTIVO     <>  :3  \n               AND RET_CON_PRO_CODIGO  = DIF_RET_CON_PRO_CODIGO\n               AND RET_CON_NUMERO      = DIF_RET_CON_NUMERO\n               AND RET_CONSECUTIVO     = DIF_RET_CONSECUTIVO\n               AND dif_ref_fondo       =  :4  \n               AND (RET_REF_ESTADO     = 'SER001' or RET_REF_ESTADO = 'SER003')\n          ORDER BY dif_ref_fondo";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_ModificarFondos",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // set IN parameters
   __sJT_st.setString(1,v_pro);
   __sJT_st.setString(2,v_contra);
   __sJT_st.setInt(3,v_conret);
   __sJT_st.setString(4,v_codigofondo);
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   vsumvalor = __sJT_rs.getDouble(1); if (__sJT_rs.wasNull()) throw new sqlj.runtime.SQLNullException();
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:123^11*/
     if(vsumvalor != 0)
     {
      v_valort = v_valort - vsumvalor;
      v_val.addElement(new Double(v_valort));
      v_encontrofondo = true;
     }
     else
     {
      v_val.addElement(new Double(v_valort));
     }
     /**Consultar valores y porcentajes retirados*/
     /*@lineinfo:generated-code*//*@lineinfo:135^6*/

//  ************************************************************
//  #sql v_distri2 = { SELECT DIF_VALOR,DIF_PORCENTAJE
//                            FROM tbdistribucion_fondos
//                           WHERE DIF_RET_CON_PRO_CODIGO = :v_pro
//                             AND DIF_RET_CON_NUMERO= :v_contra
//                             AND DIF_RET_CONSECUTIVO = :v_conret
//                             AND dif_ref_fondo = :v_codigofondo
//                        ORDER BY dif_ref_fondo
//                         };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT DIF_VALOR,DIF_PORCENTAJE\n                          FROM tbdistribucion_fondos\n                         WHERE DIF_RET_CON_PRO_CODIGO =  :1  \n                           AND DIF_RET_CON_NUMERO=  :2  \n                           AND DIF_RET_CONSECUTIVO =  :3  \n                           AND dif_ref_fondo =  :4  \n                      ORDER BY dif_ref_fondo";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_ModificarFondos",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_pro);
   __sJT_st.setString(2,v_contra);
   __sJT_st.setInt(3,v_conret);
   __sJT_st.setString(4,v_codigofondo);
   // execute query
   v_distri2 = new TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_ModificarFondos.i_distri2(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"1TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_ModificarFondos",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:142^23*/

     while(v_distri2.next())
     {
      v_codfonvalor +=v_codigofondo;
      v_codfonvalor +=",";
      v_valret.addElement(new Double(v_distri2.DIF_VALOR()));
      v_codfonvalor += new Double(v_distri2.DIF_VALOR());
      v_codfonvalor +=",";
      v_porret.addElement(new Double(v_distri2.DIF_PORCENTAJE()));
      v_codfonvalor += new Double(v_distri2.DIF_PORCENTAJE());
      v_codfonvalor +=",";
      v_encontrofondo2 = false;
     }
     v_distri2.close();
     if(v_encontrofondo2)
     {
      double c = 0;
      v_valret.addElement(new Double(c));
      v_porret.addElement(new Double(c));
     }
     v_descripcionfondo    = (java.lang.String)session.getAttribute("s_desfon"+i+"");

     v_desc.addElement(v_descripcionfondo);
     v_minimofon =  (java.lang.String)session.getAttribute("s_minfon"+i+"");
     v_minfon.addElement(v_minimofon);
    }
    /**Capturar tipo de distribución de fondos*/
    try { v_fondo = request.getParameter("v_fondo"); } catch (Exception e) { e.printStackTrace(); }
    session.removeAttribute("s_fondo");
    session.removeAttribute("s_codfonvalor");
    session.setAttribute("s_fondo",v_fondo);
    session.setAttribute("s_codfonvalor",v_codfonvalor);
    /**Distribución prorrata*/
    if(v_fondo.equals("0"))
    {
     session.removeAttribute("s_prorrata");
     session.setAttribute("s_prorrata","S");
    }
    else
    {/**Distribución valor o porcentaje*/
     session.removeAttribute("s_prorrata");
     session.setAttribute("s_prorrata","N");
    }
    v_fon.trimToSize();
    v_desc.trimToSize();
    v_val.trimToSize();
    for (int con = 0; con<v_fon.size();con++)
    {
     v_sumatfondo = v_sumatfondo + new Double(v_val.elementAt(con).toString()).doubleValue();
    }

    /**Retiro distribución prorrata*/
    if(v_fondo.equals("0"))
    {
     /**Página de respuesta*/
     v_pintar=    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiro","Modificar Distribución de Fondos","TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBS_ActualizarDistrifon","",true,"modulo_retiros.js","");
     out.println(""+v_pintar+"");
     /*Cambio para manejo de referencia unica 2009/03/30 MOS */
     String v_contrato_unif = "";
     /*@lineinfo:generated-code*//*@lineinfo:202^6*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_ModificarFondos",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:202^79*/
     out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
     out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
     out.println("<br>");
     out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Valor a retirar</b>  "+NumberFormat.getCurrencyInstance().format(v_valre2)+" </cENTER></font>");
     out.println("<br>");
     out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
     out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Fondos que conforman el contrato</b></font></center></font></td>");
     out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Saldo Fondo</b></center></font></td>");
     out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Valor retirado</b></center></font></td>");
     out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>% retirado</b></center></font></td>");
     out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Valor actual</b></center></font></td></tr>");
     int x = 0;

     for (int g = 0; g<v_fon.size();g++)
     {
      /**Sumatoria de valores y porcentajes */
      //v_sumatfondo = v_sumatfondo + new Double(v_val.elementAt(g).toString()).doubleValue();
      v_sumatvalor = v_sumatvalor + new Double(v_valret.elementAt(g).toString()).doubleValue();

      v_sumatporcentaje = v_sumatporcentaje + new Double(v_porret.elementAt(g).toString()).doubleValue();

      v_valdiv = new Double(v_val.elementAt(g).toString()).doubleValue()/v_sumatfondo;

      double v_dou  = new Double(v_valre2).doubleValue()* v_valdiv;
      long v_valdiv2 = Math.round(v_dou);

      x++;
      out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='000000'>"+v_desc.elementAt(g).toString()+"</font></td>");

      out.println("<td align=right ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_val.elementAt(g))+"</font></td>");

      out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_valret.elementAt(g))+"</font></td>");
      out.println("<td align=right ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>%"+v_format.format(v_porret.elementAt(g))+"</font></td>");
      out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>$<input name= valor"+x+"  type=text value='"+v_valdiv2+"' size = '11' readonly> </font></td></tr>");
     }

     out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='000000'><b>Total </b></font></td>");
     out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_sumatfondo)+"</font></td>");
     out.println("<td align=right ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_sumatvalor)+"</font></td>");
     out.println("<td align=right ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>%"+v_format.format(v_sumatporcentaje)+"</font></td>");
     out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><input type=text name = totalesDesde value = '"+NumberFormat.getCurrencyInstance().format(new Double(v_valre2))+"'  size = '11' readonly></font></td></tr>");
     out.println("</table></center>");
     out.println("<input name= v_maximo2 type=HIDDEN value='"+x+"' >");
     out.println("<br>");
     /**Si no se encuentran datos*/
     if(v_encontrofondo)
     {
      out.println("<pre>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>        Nota:</b>Los valores por fondos pueden estar afectados por retiros del día.</font></center>");
      out.println("</pre>");
     }
     out.println("<br>");
     out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
     out.println("<center><input type=submit value='Aceptar'><input type=button value='Regresar' onclick=' history.go(-1)'><input type=button value='Cancelar' onclick=' history.go(-3)'></center>");
     v_pie = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println(""+v_pie+"");
     out.close();
    }
    else if(v_fondo.equals("1"))/**Retiro distribución por valor*/
         {
          v_pintar=    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiro","Modificar Distribución de Fondos","TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBS_ActualizarDistrifon","",true,"modulo_retiros.js","");
          out.println(""+v_pintar+"");
          /*Cambio para manejo de referencia unica 2009/03/30 MOS */
          String v_contrato_unif = "";
         /*@lineinfo:generated-code*//*@lineinfo:268^10*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_ModificarFondos",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:268^83*/
          out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
          out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
          out.println("<br>");
          out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Valor a retirar</b>  "+NumberFormat.getCurrencyInstance().format(v_valre2)+" </cENTER></font>");
          out.println("<br>");
          out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
          out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Fondos que conforman el contrato</b></font></center></font></td>");
          out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Saldo Fondo</b></center></font></td>");
          out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Valor retirado</b></center></font></td>");
          out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>% retirado</b></center></font></td>");
          out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Valor actual</b></center></font></td></tr>");
          int x = 0;
          for (int y = 0; y<v_fon.size();y++)
          {
           /**Sumatoria de valores y porcentajes */
           v_sumatvalor = v_sumatvalor + new Double(v_valret.elementAt(y).toString()).doubleValue();
           v_sumatporcentaje = v_sumatporcentaje + new Double(v_porret.elementAt(y).toString()).doubleValue();

           x++;
           out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='000000'>"+v_desc.elementAt(y).toString()+" </font></td>");
           out.println("<td align=right ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_val.elementAt(y))+"</font></td>");
           out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_valret.elementAt(y))+"</font></td>");
           out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>%"+v_format.format(v_porret.elementAt(y))+"</font></td>");
           out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>$<input name= valor"+x+"  type=text value='' size = '9' onChange='if (validar("+v_sumatfondo+","+v_val.elementAt(y).toString()+",valor"+x+".value, "+v_minfon.elementAt(y)+",v_codigo.valor"+x+" ,"+v_valre2+","+v_fon.size()+")==1) return false;'> </font></td></tr>");
          }
          out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='000000'><b>Total </b></font></td>");
          out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_sumatfondo)+"</font></td>");
          out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_sumatvalor)+"</font></td>");
          out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>%"+v_format.format(v_sumatporcentaje)+"</font></td>");
          out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>$<input type=text name = totalesDesde value = ''  size = '9' readonly></font></td></tr>");
          out.println("</table></center>");
          out.println("<input name= v_maximo2 type=HIDDEN value='"+x+"' size = '9'>");
          out.println("<br>");
         /**Si no se encuentran datos*/
          if(v_encontrofondo)
          {
           out.println("<pre>");
           out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>        Nota:</b>Los valores por fondos pueden estar afectados por retiros del día.</font></center>");
           out.println("</pre>");
           out.println("<br>");
          }
          out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
          out.println("<center><input type=submit value='Aceptar' onclick='if(maximo("+v_valre2+",v_codigo.totalesDesde)==1)return false;' ><input type=button value='Regresar' onclick=' history.go(-1)'><input type=button value='Cancelar' onclick=' history.go(-3)'></center>");
          v_pie = i_pagina.TBFL_PIE;
          out.println("<br>");
          out.println(""+v_pie+"");
          out.close();
         }
         else if(v_fondo.equals("2"))/**Retiro distribución por porcentaje*/
              {
               v_pintar=    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiros","Modificar Distribución de Fondos","TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBS_ActualizarDistrifon","",true,"modulo_retiros.js","");
               out.println(""+v_pintar+"");
               /*Cambio para manejo de referencia unica 2009/03/30 MOS */
               String v_contrato_unif = "";
              /*@lineinfo:generated-code*//*@lineinfo:323^15*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_ModificarFondos",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:323^88*/
               out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
               out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
               out.println("<br>");
               out.println("<center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Valor a retirar</b>  "+NumberFormat.getCurrencyInstance().format(v_valre2)+" </cENTER></font>");
               out.println("<br>");
               out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
               out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Fondos que conforman el contrato</b></font></center></font></td>");
               out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Saldo Fondo</b></center></font></td>");
               out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>% retirado</b></center></font></td>");
               out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>valor retirado</b></center></font></td>");
               out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>% actual</b></center></font></td></tr>");
               int x = 0;
               for (int y = 0; y<v_fon.size();y++)
               {
                /**Sumatoria de valores y porcentajes */
                v_sumatvalor = v_sumatvalor + new Double(v_valret.elementAt(y).toString()).doubleValue();
                v_sumatporcentaje = v_sumatporcentaje + new Double(v_porret.elementAt(y).toString()).doubleValue();

                x++;
                out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='000000'>"+v_desc.elementAt(y).toString()+"    </font></td>");
                out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_val.elementAt(y))+"</font></td>");
                out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>%"+v_format.format(v_porret.elementAt(y))+"</font></td>");
                out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_valret.elementAt(y))+"</font></td>");
                out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>%<input name= porcen"+x+"  type=text value='' size = '9' onChange='if (validar2("+v_sumatfondo+","+v_val.elementAt(y).toString()+",porcen"+x+".value, "+v_minfon.elementAt(y)+",v_codigo.porcen"+x+" ,"+v_fon.size()+","+v_valre2+")==1) return false;'> </font></td></tr>");
               }
               out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='000000'><b>Total </b></font></td>");
               out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_sumatfondo)+"</font></td>");
               out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>%"+v_format.format(v_sumatporcentaje)+"</font></td>");
               out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_sumatvalor)+"</font></td>");
               out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>%<input type=text name = totalesDesde value = ''  size = '9' readonly></font></td></tr>");
               out.println("</table></center>");
               out.println("<input name= v_maximo2 type=HIDDEN value='"+x+"' size = '9'>");
               out.println("<br>");
               /**Si no se encuentran datos*/
               if(v_encontrofondo)
               {
                out.println("<pre>");
                out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>        Nota:</b>Los valores por fondos pueden estar afectados por retiros del día.</font></center>");
                out.println("</pre>");
                out.println("<br>");
               }
               out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
               out.println("<center><input type=submit value='Aceptar' onclick='if(maximo2(v_codigo.totalesDesde)==1)return false;'><input type=button value='Regresar' onclick=' history.go(-1)'><input type=button value='Cancelar' onclick=' history.go(-3)'></center>");
               v_pie = i_pagina.TBFL_PIE;
               out.println("<br>");
               out.println(""+v_pie+"");
               out.close();
              }

   }
   else/**Termina session*/
   {
    v_pintar=    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiro","Error al Modificar Distribución de Fondos","","<center>Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.</center>",false);
    out.println(""+v_pintar+"");
    v_pie = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-3)'></center>");
    out.println(""+v_pie+"");
    out.close();
   }
   /**Desconectar base de datos*/
   //i_conexion.TBCL_DesconectarBaseDatos();
   ctx8.getConnection().close();
  }
  catch (Exception ex)
  {/**Manejo de error*/
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

   String v_pintar=    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiro","Error al Modificar Distribución de Fondos","","<center>"+v_menex+"<center>",false);
   out.println(""+v_pintar+"");
   out.println("<br>");
   out.println("<center><input type=button value='Cancelar' onclick=' history.go(-3)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
   String v_pie = i_pagina.TBFL_PIE;
   out.println(""+v_pie+"");
   out.close();
  }
 }
}/*@lineinfo:generated-code*/