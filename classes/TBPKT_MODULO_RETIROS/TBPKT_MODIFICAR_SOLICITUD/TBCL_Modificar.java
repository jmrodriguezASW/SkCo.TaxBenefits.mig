/*@lineinfo:filename=TBCL_Modificar*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD;

import sqlj.runtime.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import sqlj.runtime.ref.DefaultContext ;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import java.text.NumberFormat;


/*clase que muestra las solicitudes de retiro pendientes de enviar a multifund  y
las opciones de modificación*/
public class TBCL_Modificar extends HttpServlet
{

 /**Iterator datos del retiro a modificar*/
 /*@lineinfo:generated-code*//*@lineinfo:20^2*/ /*//  *************************************************************//*//  SQLJ iterator declaration:*//*//  *************************************************************/ class i_retiro extends sqlj.runtime.ref.ResultSetIterImpl implements sqlj.runtime.NamedIterator { public i_retiro(sqlj.runtime.profile.RTResultSet resultSet) throws java.sql.SQLException { super(resultSet); RET_CONSECUTIVONdx = findColumn("RET_CONSECUTIVO"); RET_FECHA_EFECTIVANdx = findColumn("RET_FECHA_EFECTIVA"); RET_VALORNdx = findColumn("RET_VALOR"); ret_valor_brutoNdx = findColumn("ret_valor_bruto"); prc_retiro_totalNdx = findColumn("prc_retiro_total"); CON_NOMBRESNdx = findColumn("CON_NOMBRES"); CON_APELLIDOSNdx = findColumn("CON_APELLIDOS"); m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet(); } private oracle.jdbc.OracleResultSet m_rs; public int RET_CONSECUTIVO() throws java.sql.SQLException { int __sJtmp = m_rs.getInt(RET_CONSECUTIVONdx); if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp; } private int RET_CONSECUTIVONdx; public java.sql.Date RET_FECHA_EFECTIVA() throws java.sql.SQLException { return (java.sql.Date)m_rs.getDate(RET_FECHA_EFECTIVANdx); } private int RET_FECHA_EFECTIVANdx; public double RET_VALOR() throws java.sql.SQLException { double __sJtmp = m_rs.getDouble(RET_VALORNdx); if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp; } private int RET_VALORNdx; public double ret_valor_bruto() throws java.sql.SQLException { double __sJtmp = m_rs.getDouble(ret_valor_brutoNdx); if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp; } private int ret_valor_brutoNdx; public String prc_retiro_total() throws java.sql.SQLException { return (String)m_rs.getString(prc_retiro_totalNdx); } private int prc_retiro_totalNdx; public String CON_NOMBRES() throws java.sql.SQLException { return (String)m_rs.getString(CON_NOMBRESNdx); } private int CON_NOMBRESNdx; public String CON_APELLIDOS() throws java.sql.SQLException { return (String)m_rs.getString(CON_APELLIDOSNdx); } private int CON_APELLIDOSNdx; } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:20^183*/
 /**Variable tipo iterator v_retiro*/
 i_retiro v_retiro;



 public void TBPL_ModificarSol(PrintWriter out,HttpSession session,HttpServletRequest request,String nuevaCadena )
 {
  STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();/**Instancia de la clase TBCL_GenerarBaseHTML*/
  TBCL_Validacion        i_valusu = new TBCL_Validacion();/**Instancia de la clase TBCL_GenerarBaseHTML*/
  //TBCL_ConexionSqlj    i_conexion = new TBCL_ConexionSqlj();/**Instancia de la clase TBCL_ConexionSqlj*/
  try
  {
   String[] v_valusu = new String[3];
   v_valusu=i_valusu.TBFL_ValidarUsuario();
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   DefaultContext ctx7 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
   DefaultContext.setDefaultContext(ctx7);
   /**Realizar conexion a la base de datos*/
   //i_conexion.TBCL_ConexionBaseDatos();

   /**Definicion de variables*/
   /**Tipo String*/
   String v_coduni     = "";/**Variable código unidad as400*/
   String v_codusu     = "";/**Variable código tipo usuario as400*/
   String v_nombre     = "";/**Variable nombre afiliado*/
   String v_apellidos  = "";/**Variable apellido afiliado*/
   double v_valoret    = 0;/**Variable valor retirado*/
   String v_contra     = "";/**Variable número del contrato*/
   String v_pro        = "";/**Variable código del producto*/
   String v_tusupipe   = "";/**Variable nombre usuari*/
   String v_fecha      = "";/**Variable fecha retiro*/
   String v_pie        = "";/**Variable dibujar inicio página*/
   String v_pintar     = "";/**Variable dibujar final página*/
   /**Tipo boolean*/
   boolean v_encontro2 = false;/**Verificar que se encontraron datos*/
   boolean v_check     = false;/**Verificar que el primer dato quede chequeado*/
   boolean v_nopintar  = true;/**Verificar que en el primer dato se captura datos contrato*/

   /**Verificar que las variables de session no expiren*/
   if((java.lang.String)session.getAttribute("s_contrato")!= null ||(java.lang.String)session.getAttribute("s_producto")!= null)
   {
    /**Capturar variables de session*/
    v_contra     = (java.lang.String)session.getAttribute("s_contrato");//toam contrato
    v_pro        = (java.lang.String)session.getAttribute("s_producto");//toma producto
    v_tusupipe   = (java.lang.String)session.getAttribute("s_usuariopipe");//toma unidad

    /**Seleccionar información del retiro pendiente a enviar a multifund*/
    /*@lineinfo:generated-code*//*@lineinfo:68^5*/ /*//  *************************************************************//*//  #sql v_retiro = { SELECT RET_CONSECUTIVO*//*//                                 ,RET_FECHA_EFECTIVA*//*//                                 ,RET_VALOR*//*//                                 ,ret_valor_bruto*//*//                                 ,prc_retiro_total*//*//                                 ,CON_NOMBRES*//*//                                 ,CON_APELLIDOS*//*//                            FROM TBRETIROS*//*//                                ,Tbproductos_conceptos*//*//                                ,TBCONTRATOS*//*//                           WHERE RET_CON_PRO_CODIGO  = :v_pro*//*//                             AND RET_CON_NUMERO      = :v_contra*//*//                             AND (RET_REf_ESTADO      ='SER001' OR  (RET_REf_ESTADO ='SER003' AND RET_CONSECUTIVO IN (SELECT A2QFNU FROM C3A2CPP@MFUND WHERE A2GLDZ=( SELECT TO_CHAR(MAX(CIE_FECHA),'RRRRMMDD') FROM TBCIERRES WHERE CIE_TIPO='P') AND A2AMCD=RET_CON_PRO_CODIGO AND A2ADNB=RET_CON_NUMERO)))*//*//                             AND RET_CON_PRO_CODIGO            = PRC_PRO_CODIGO*//*//                             AND RET_COT_REF_TIPO_TRANSACCION  = PRC_COT_REF_TIPO_TRANSACCION*//*//                             AND RET_COT_REF_CLASE_TRANSACCION = PRC_COT_REF_CLASE_TRANSACCION*//*//                             AND RET_CON_PRO_CODIGO            = CON_PRO_CODIGO*//*//                             AND RET_CON_NUMERO                = CON_NUMERO*//*//                          UNION*//*//                           SELECT RET_CONSECUTIVO*//*//                                 ,RET_FECHA_EFECTIVA*//*//                                 ,RET_VALOR*//*//                                 ,ret_valor_bruto*//*//                                 ,'N' prc_retiro_total*//*//                                 ,CON_NOMBRES*//*//                                 ,CON_APELLIDOS*//*//                            FROM TBRETIROS*//*//                                ,TBCONTRATOS*//*//                           WHERE RET_CON_PRO_CODIGO  = :v_pro*//*//                             AND RET_CON_NUMERO      = :v_contra*//*//                             AND (RET_REf_ESTADO      ='SER001' OR  (RET_REf_ESTADO ='SER003' AND RET_CONSECUTIVO IN (SELECT A2QFNU FROM C3A2CPP@MFUND WHERE A2GLDZ=( SELECT TO_CHAR(MAX(CIE_FECHA),'RRRRMMDD') FROM TBCIERRES WHERE CIE_TIPO='P') AND A2AMCD=RET_CON_PRO_CODIGO AND A2ADNB=RET_CON_NUMERO)))*//*//                             AND RET_CON_PRO_CODIGO            = 'MFUND'*//*//                             AND RET_COT_REF_TIPO_TRANSACCION  = 'UTT016'*//*//                             AND RET_COT_REF_CLASE_TRANSACCION = 'UCT001'*//*//                             AND RET_CON_PRO_CODIGO            = CON_PRO_CODIGO*//*//                             AND RET_CON_NUMERO                = CON_NUMERO*//*//                         };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "SELECT RET_CONSECUTIVO\n                               ,RET_FECHA_EFECTIVA\n                               ,RET_VALOR\n                               ,ret_valor_bruto\n                               ,prc_retiro_total\n                               ,CON_NOMBRES\n                               ,CON_APELLIDOS\n                          FROM TBRETIROS\n                              ,Tbproductos_conceptos\n                              ,TBCONTRATOS\n                         WHERE RET_CON_PRO_CODIGO  =  :1  \n                           AND RET_CON_NUMERO      =  :2  \n                           AND (RET_REf_ESTADO      ='SER001' OR  (RET_REf_ESTADO ='SER003' AND RET_CONSECUTIVO IN (SELECT A2QFNU FROM C3A2CPP@MFUND WHERE A2GLDZ=( SELECT TO_CHAR(MAX(CIE_FECHA),'RRRRMMDD') FROM TBCIERRES WHERE CIE_TIPO='P') AND A2AMCD=RET_CON_PRO_CODIGO AND A2ADNB=RET_CON_NUMERO)))\n                           AND RET_CON_PRO_CODIGO            = PRC_PRO_CODIGO\n                           AND RET_COT_REF_TIPO_TRANSACCION  = PRC_COT_REF_TIPO_TRANSACCION\n                           AND RET_COT_REF_CLASE_TRANSACCION = PRC_COT_REF_CLASE_TRANSACCION\n                           AND RET_CON_PRO_CODIGO            = CON_PRO_CODIGO\n                           AND RET_CON_NUMERO                = CON_NUMERO\n                        UNION\n                         SELECT RET_CONSECUTIVO\n                               ,RET_FECHA_EFECTIVA\n                               ,RET_VALOR\n                               ,ret_valor_bruto\n                               ,'N' prc_retiro_total\n                               ,CON_NOMBRES\n                               ,CON_APELLIDOS\n                          FROM TBRETIROS\n                              ,TBCONTRATOS\n                         WHERE RET_CON_PRO_CODIGO  =  :3  \n                           AND RET_CON_NUMERO      =  :4  \n                           AND (RET_REf_ESTADO      ='SER001' OR  (RET_REf_ESTADO ='SER003' AND RET_CONSECUTIVO IN (SELECT A2QFNU FROM C3A2CPP@MFUND WHERE A2GLDZ=( SELECT TO_CHAR(MAX(CIE_FECHA),'RRRRMMDD') FROM TBCIERRES WHERE CIE_TIPO='P') AND A2AMCD=RET_CON_PRO_CODIGO AND A2ADNB=RET_CON_NUMERO)))\n                           AND RET_CON_PRO_CODIGO            = 'MFUND'\n                           AND RET_COT_REF_TIPO_TRANSACCION  = 'UTT016'\n                           AND RET_COT_REF_CLASE_TRANSACCION = 'UCT001'\n                           AND RET_CON_PRO_CODIGO            = CON_PRO_CODIGO\n                           AND RET_CON_NUMERO                = CON_NUMERO"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_Modificar",theSqlTS); /*// set IN parameters*/ __sJT_st.setString(1,v_pro); __sJT_st.setString(2,v_contra); __sJT_st.setString(3,v_pro); __sJT_st.setString(4,v_contra); /*// execute query*/ v_retiro = new TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_Modificar.i_retiro(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"0TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_Modificar",null)); } finally { __sJT_ec.oracleCloseQuery(); } } /*//  *************************************************************/ 




































                       /*@lineinfo:user-code*//*@lineinfo:105^23*/

    /**Dibujar página de respuesta*/
    v_pintar=    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiro","Modificar Solicitud de Retiro","TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBS_Penalizacion","",true,"","");
    out.println(""+v_pintar+"");
    while(v_retiro.next())
    {
     /**Tomar el primer dato y capturar datos contrato*/
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
      /*@lineinfo:generated-code*//*@lineinfo:123^7*/ /*//  *************************************************************//*//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_pro,:v_contra)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_Modificar",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_pro); __sJT_st.setString(3,v_contra); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_contrato_unif = (String)__sJT_st.getString(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:123^80*/
      out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
      out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nombre+"  <b> Apellidos </b>"+v_apellidos+" </CENTER></font>");
      out.println("<br>");
      out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
      out.println("<tr><td class=\"td11\" colspan= 7 ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Solicitudes de Retiros</b></font></center></font></td></tr>");
      out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Fecha Efectiva</b></center></font></td>");
      out.println("<td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Valor del Retiro</b></center></font></td></TR>");
      //se muestran las solicitudes de retiro pendientes a enviar a multifund
      v_nopintar = false;
     }
     if(v_retiro.prc_retiro_total().equals("S"))
     {
       v_valoret = v_retiro.ret_valor_bruto();
     }
     else
     {
       v_valoret = v_retiro.RET_VALOR();
     }
     /**Chequear el primer registro por default*/
     if(v_check == false)
     {

      out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><input type =radio name=v_conret value= "+v_retiro.RET_CONSECUTIVO()+"  checked>"+v_retiro.RET_FECHA_EFECTIVA()+"</font></td>");
      out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'> "+NumberFormat.getCurrencyInstance().format(v_valoret)+"</font></td></tr>");
      v_check = true;
      v_encontro2 =true;
     }
     else
     {
      out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><input type =radio name=v_conret value= "+v_retiro.RET_CONSECUTIVO()+"  >"+v_retiro.RET_FECHA_EFECTIVA()+"</font></td>");
      out.println("<td align=right ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'> "+NumberFormat.getCurrencyInstance().format(v_valoret)+"</font></td></tr>");
      v_encontro2 =true;
     }
    }
    v_retiro.close();
    /**Cuando no se encuentran datos*/
    if(v_encontro2 == false)
    {
     out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
     out.println("<tr><td class=\"td11\" colspan= 7 ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Solicitudes de Retiros</b></font></center></font></td></tr>");
     out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Fecha Efectiva</b></center></font></td>");
     out.println("<td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Valor del Retiro</b></center></font></td></TR>");
     out.println("<tr><td  ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></center></td>");
     out.println("<td><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></td></center></tr>");
     out.println("<tr><td colspan= 5><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>No se encontraron Solicitudes de retiro para modificar</font></center></td><tr>");
     out.println("</table></center>");
     out.println("<br>");
     out.println("<center><input type=button value='Aceptar' onclick=' history.go(-1)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
     v_pie = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println(""+v_pie+"");
     out.close();
    }
    out.println("</table></center>");
    out.println("<pre><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=2><input type=radio NAME=v_modificar VALUE='0' checked>  Modificar Código de Banco y Número de Cuenta </font>");
    out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=2><input type=radio NAME=v_modificar VALUE='1'>  Modificar Distribución de Fondos  </font>");
    out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=2><input type=radio NAME=v_modificar VALUE='2'>  Eliminar Solicitud de Retiro Pendiente de enviar </font></pre>");
    out.println("<br>");
    out.println("<PRE>");
    out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
    out.println("<center><input type=submit value='Aceptar'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
    v_pie = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println(""+v_pie+"");
    out.close();
   }
   else
   {/**Termino session*/
    v_pintar=    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiro","Error al Modificar  Solicitud de Retiro","","<center>Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.</center>",false);
    out.println(""+v_pintar+"");
    v_pie = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'></center>");
    out.println(""+v_pie+"");
    out.close();
   }
  /**Terminar session base de datos*/
  //i_conexion.TBCL_DesconectarBaseDatos();
  ctx7.getConnection().close();
  }
  catch(Exception ex)
  {/**manejo de errores*/
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
   String v_pintar=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Error al Modificar Solicitud de Retiro","","<center>"+v_menex+"</center>",false);
   out.println(""+v_pintar+"");
   out.println("<BR>");
   out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
   String v_pie = i_pagina.TBFL_PIE;
   out.println("<br>");
   out.println("<br>");
   out.println(""+v_pie+"");
   out.close();
  }
 }
}