/*@lineinfo:filename=SQL_CIERRE*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.Connection;
import java.sql.Date;

/**
 * A Sqlj class.
 * <P>
 * @author
 */
public class SQL_CIERRE extends Object
{
  /*@lineinfo:generated-code*//*@lineinfo:27^3*/ /*//  *************************************************************//*//  SQLJ iterator declaration:*//*//  *************************************************************/ public static class vinter_tbcierre extends sqlj.runtime.ref.ResultSetIterImpl implements sqlj.runtime.NamedIterator { public vinter_tbcierre(sqlj.runtime.profile.RTResultSet resultSet) throws java.sql.SQLException { super(resultSet); cie_ref_unidad_procesoNdx = findColumn("cie_ref_unidad_proceso"); cie_fechaNdx = findColumn("cie_fecha"); cie_tipoNdx = findColumn("cie_tipo"); m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet(); } private oracle.jdbc.OracleResultSet m_rs; public String cie_ref_unidad_proceso() throws java.sql.SQLException { return (String)m_rs.getString(cie_ref_unidad_procesoNdx); } private int cie_ref_unidad_procesoNdx; public java.sql.Date cie_fecha() throws java.sql.SQLException { return (java.sql.Date)m_rs.getDate(cie_fechaNdx); } private int cie_fechaNdx; public String cie_tipo() throws java.sql.SQLException { return (String)m_rs.getString(cie_tipoNdx); } private int cie_tipoNdx; } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:27^112*/
  /*@lineinfo:generated-code*//*@lineinfo:28^3*/ /*//  *************************************************************//*//  SQLJ iterator declaration:*//*//  *************************************************************/ public static class vinter_unidades extends sqlj.runtime.ref.ResultSetIterImpl implements sqlj.runtime.NamedIterator { public vinter_unidades(sqlj.runtime.profile.RTResultSet resultSet) throws java.sql.SQLException { super(resultSet); ref_descripcionNdx = findColumn("ref_descripcion"); ref_codigoNdx = findColumn("ref_codigo"); m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet(); } private oracle.jdbc.OracleResultSet m_rs; public String ref_descripcion() throws java.sql.SQLException { return (String)m_rs.getString(ref_descripcionNdx); } private int ref_descripcionNdx; public String ref_codigo() throws java.sql.SQLException { return (String)m_rs.getString(ref_codigoNdx); } private int ref_codigoNdx; } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:28^91*/
  /*@lineinfo:generated-code*//*@lineinfo:29^3*/ /*//  *************************************************************//*//  SQLJ iterator declaration:*//*//  *************************************************************/ public static class vinter_productos extends sqlj.runtime.ref.ResultSetIterImpl implements sqlj.runtime.NamedIterator { public vinter_productos(sqlj.runtime.profile.RTResultSet resultSet) throws java.sql.SQLException { super(resultSet); pro_descripcionNdx = findColumn("pro_descripcion"); pro_codigoNdx = findColumn("pro_codigo"); m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet(); } private oracle.jdbc.OracleResultSet m_rs; public String pro_descripcion() throws java.sql.SQLException { return (String)m_rs.getString(pro_descripcionNdx); } private int pro_descripcionNdx; public String pro_codigo() throws java.sql.SQLException { return (String)m_rs.getString(pro_codigoNdx); } private int pro_codigoNdx; } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:29^91*/

  public void TBP_CIERRE_T(PrintWriter out, HttpSession session )
  {
     String                 v_pagina;
     String                 v_codusu    = "";
     String                 v_coduni    = "";
     String                 v_desuni    = "";
     String                 v_pie       = "";
     String                 v_fecha     = "";
     String                 v_estado_p9 = "";
     String                 v_estado_p1 = "";
     java.sql.Date          v_max_fecha = null;
     STBCL_GenerarBaseHTML  i_pagina    = new STBCL_GenerarBaseHTML();
     TBCL_Validacion        i_valusu    = new TBCL_Validacion();
     String[]               v_valusu;
     vinter_tbcierre        v_tbcierres;
     vinter_unidades        v_unidades;
     vinter_productos       v_productos;
     try
     {
       //Conexion con la base de datos Oracle
       v_valusu = new String[3];
       v_valusu = i_valusu.TBFL_ValidarUsuario();
       DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
       DefaultContext.setDefaultContext(new DefaultContext( v_valusu[0],v_valusu[1],v_valusu[2],false));
       // Imprime el encabezado de la pagina de entrada a Cierres de Unidades
       v_pagina = i_pagina.TBFL_CABEZA("Cierre de solicitudes de retiro", "Cierre de solicitudes de retiro","TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.TBS_TIPO_CIERRE","",true,"modulo_retiros.js","return validar_fecha2(this)");
       out.print(""+v_pagina+"");
       //**********************************************************************************
       //Seleciona de TBCIERRES los ultimos cierres realizados por cada unidad
       //**********************************************************************************
       /*@lineinfo:generated-code*//*@lineinfo:61^8*/ /*//  *************************************************************//*//  #sql v_tbcierres = { SELECT tc.cie_fecha, tc.cie_ref_unidad_proceso, tc.cie_tipo*//*//                              FROM tbreferencias, tbcierres tc*//*//                              WHERE tc.cie_fecha = ( SELECT MAX(cie_fecha)*//*//                                                    FROM tbcierres*//*//                                                    WHERE cie_ref_unidad_proceso = tc.cie_ref_unidad_proceso AND CIE_TIPO='T')*//*//                                AND ref_codigo   = cie_ref_unidad_proceso*//*//                                AND ref_codigo LIKE 'UUP%'*//*//                                AND ref_codigo NOT LIKE 'UUP000'*//*//                                AND ref_codigo NOT LIKE 'UUP099'*//*//                                AND ref_valor IS NOT NULL*//*//                              ORDER BY  tc.cie_ref_unidad_proceso, tc.cie_fecha  };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "SELECT tc.cie_fecha, tc.cie_ref_unidad_proceso, tc.cie_tipo\n                            FROM tbreferencias, tbcierres tc\n                            WHERE tc.cie_fecha = ( SELECT MAX(cie_fecha)\n                                                  FROM tbcierres\n                                                  WHERE cie_ref_unidad_proceso = tc.cie_ref_unidad_proceso AND CIE_TIPO='T')\n                              AND ref_codigo   = cie_ref_unidad_proceso\n                              AND ref_codigo LIKE 'UUP%'\n                              AND ref_codigo NOT LIKE 'UUP000'\n                              AND ref_codigo NOT LIKE 'UUP099'\n                              AND ref_valor IS NOT NULL\n                            ORDER BY  tc.cie_ref_unidad_proceso, tc.cie_fecha"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE",theSqlTS); /*// execute query*/ v_tbcierres = new TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE.vinter_tbcierre(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"0TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE",null)); } finally { __sJT_ec.oracleCloseQuery(); } } /*//  *************************************************************/ 









                                                                               /*@lineinfo:user-code*//*@lineinfo:71^79*/
      //**********************************************************************************
      // Imprime la tabla con los ultimos cierres realizado por cada unidad
      //**********************************************************************************
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=1><B>Ultimos cierres por unidad</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=3 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TH width=\"22%\"><FONT color=black face=verdana size=1>Unidad</TH><TH width=\"22%\"><FONT color=black face=verdana size=1>Fecha de cierre</TH><TH width=\"22%\"><FONT color=black face=verdana size=1>Tipo de cierre</font></TH></TR>");
      while (v_tbcierres.next())
      {
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_tbcierres.cie_ref_unidad_proceso()+
                    "</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_tbcierres.cie_fecha()+
                    "</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_tbcierres.cie_tipo()+"</font></TD></TR>");
      }
      v_tbcierres.close();
      out.println("</TABLE>");
      //**********************************************************************************
      // Consulta de unidades y productos
      //**********************************************************************************
       /*@lineinfo:generated-code*//*@lineinfo:91^8*/ /*//  *************************************************************//*//  #sql v_unidades = { SELECT ref_descripcion, ref_codigo*//*//                              FROM tbreferencias*//*//                              WHERE ref_codigo LIKE 'UUP%'*//*//                                AND ref_codigo NOT LIKE 'UUP000'*//*//                                AND ref_codigo NOT LIKE 'UUP099'*//*//                                AND ref_valor  IS NOT NULL };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "SELECT ref_descripcion, ref_codigo\n                            FROM tbreferencias\n                            WHERE ref_codigo LIKE 'UUP%'\n                              AND ref_codigo NOT LIKE 'UUP000'\n                              AND ref_codigo NOT LIKE 'UUP099'\n                              AND ref_valor  IS NOT NULL"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE",theSqlTS); /*// execute query*/ v_unidades = new TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE.vinter_unidades(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"1TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE",null)); } finally { __sJT_ec.oracleCloseQuery(); } } /*//  *************************************************************/ 




                                                         /*@lineinfo:user-code*//*@lineinfo:96^57*/
       /*@lineinfo:generated-code*//*@lineinfo:97^8*/ /*//  *************************************************************//*//  #sql v_productos = { SELECT pro_descripcion, pro_codigo*//*//                              FROM tbproductos  };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "SELECT pro_descripcion, pro_codigo\n                            FROM tbproductos"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE",theSqlTS); /*// execute query*/ v_productos = new TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE.vinter_productos(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"2TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE",null)); } finally { __sJT_ec.oracleCloseQuery(); } } /*//  *************************************************************/ 
                                              /*@lineinfo:user-code*//*@lineinfo:98^46*/
      //**********************************************************************************
      // Imprime la tabla con las Unidades de proceso a cerrar
      //**********************************************************************************
       out.println("<TABLE bgColor=white border=0 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
       out.println("<TR><TD><B>Unidad de Proceso:</B></TD>");
       out.println("<TD><SELECT NAME=s_unidad>");
       while (v_unidades.next() )
       {
         out.println("<OPTION value = '"+v_unidades.ref_codigo()+"' >"+v_unidades.ref_descripcion());
       }
       v_unidades.close();
       out.println("</SELECT></TD></TR>");
      //**********************************************************************************
      // Imprime la tabla con los Productos a cerrar
      //**********************************************************************************
       out.println("<br>");
       //MODIFICACION HECHA POR APC 2004/02/03 
       //SE QUITO EL COMBO DEL PRODUCTO DADO QUE SE QUIERE QUE EL CIERRE LO HAGA PARA TODOS LOS PRODUCTOS Y NO PARA
       //UN SÓLO PRODUCTO
/*       out.println("<TR><TD><B>Producto:</B></TD>");
       out.println("<TD><SELECT NAME=s_producto>");
       while (v_productos.next() )
       {
         out.println("<OPTION value = '"+v_productos.pro_codigo()+"'>"+v_productos.pro_codigo());
       }
       v_productos.close();
       out.println("</SELECT></TD></TR>");*/
//    FIN DEL CAMBIO APC 2004/02/03        
       //**********************************************************************************
       // Consulta fecha de cierre valida, LLama procedimiento que retorna la fecha de
       // Cierre o nulo si ocurre un error al consultar en la Base de Datos.
       //**********************************************************************************
       /*@lineinfo:generated-code*//*@lineinfo:131^8*/ /*//  *************************************************************//*//  #sql { call TBPBD_FECHA_CIERRE_VALIDA(:v_fecha) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_FECHA_CIERRE_VALIDA( :1  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(1,v_fecha); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_fecha = (String)__sJT_st.getString(1); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:131^60*/
       //**********************************************************************************
       // Imprime la Fecha de cierre válida
       //**********************************************************************************
       out.println("<br>");
       out.println("<TR><TD><B>Fecha de Cierre (yyyy-mm-dd):</B></TD>");
       out.println("<TD><INPUT NAME= obligatoriov_fecefectiva VALUE='"+v_fecha+"' TYPE=text MAXLENGTH=10 SIZE=10>");
       out.println("</TD></TR></TABLE>");
      //**********************************************************************************
      // Imprime la tabla con los Tipos de Cierre que se pueden realizar T: Total P: Parcial
      //**********************************************************************************
       out.println("<br>");      
       out.println("<center><table width=400 border='1' ");
       out.println("<tr><td class=\"td11\" colspan= 0><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Tipo de Cierre</b></font></center></font></td></tr>");
       out.println("</table></center>");
       out.println("<center><table width=400 border='1' ");
       out.println("<tr><td ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Parcial&nbsp;&nbsp;<input type=radio name=tipoCierre value='P' checked >");
       out.println("<td><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Total&nbsp;&nbsp;<input type=radio name=tipoCierre value='T'  ></font></center></td>");
       out.println("</table></center>");
       out.println("<pre>");
//       out.println("<center> <input type='submit' value='Aceptar' ><input type='button' value='Cancelar' ONCLICK=history.go(-1);></center>");
       out.println("<center> <input type='submit' value='Aceptar' name='aceptar'><input type='button' value='Cancelar' ONCLICK='history.go(-1)' name='cancelar' ></center>");
       v_pie = i_pagina.TBFL_PIE;
       out.print(""+v_pie+"");
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
       out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Error Proc sql_cierre: "+v_menex  ,false));
       out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
       out.close();
      }
  }

}