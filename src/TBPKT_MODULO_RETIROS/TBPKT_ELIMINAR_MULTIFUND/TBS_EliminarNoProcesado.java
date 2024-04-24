package TBPKT_MODULO_RETIROS.TBPKT_ELIMINAR_MULTIFUND;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

/**Clase que hace llamado a una funci�n almacenada  que se encarga de eliminar la solicitud de retiro no porcesada*/
public class TBS_EliminarNoProcesado extends HttpServlet
{

 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
  PrintWriter out = new PrintWriter (response.getOutputStream());
  STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();
  Connection t_tax = null;
  try
  {
   HttpSession session = request.getSession(true);
   if(session==null)
    session = request.getSession(true);
   session.setMaxInactiveInterval(3600);
   response.setContentType("text/html");
   /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
   // response.setHeader("Pragma", "No-Cache");
   // response.setDateHeader("Expires", 0);
   String v_contrato = "", v_producto = "", v_usuario  = "", v_unidad = "";
   String v_tipousu = "", v_idworker = "";
   String   nuevaCadena = "";
   String parametros[] = new String[8];
   String  cadena = request.getParameter("cadena");
   nuevaCadena = cadena;
   String ip_tax = request.getRemoteAddr();
   TBCL_Seguridad Seguridad = new TBCL_Seguridad();
   parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
   v_contrato = parametros[0];
   v_producto = parametros[1];
   v_usuario  = parametros[2];
   v_unidad = parametros[3];
   v_tipousu = parametros[4];
   v_idworker = parametros[5];


   t_tax =   DataSourceWrapper.getInstance().getConnection();
   

   if((java.lang.String)session.getAttribute("s_producto") != null || (java.lang.String)session.getAttribute("s_contrato")!= null)
   {
    String v_pro = (java.lang.String)session.getAttribute("s_producto");
    String v_contra =(java.lang.String)session.getAttribute("s_contrato");
    String v_usuario2 = (java.lang.String)session.getAttribute("s_usuariopipe");
    String v_fecha    = (java.lang.String)session.getAttribute("s_fechaeliminacion");
    String v_unidad_pro  = (java.lang.String)session.getAttribute("s_unidad_proceso");


    String v_fecefe = "";
    String v_conret = "";
    try {  v_conret= request.getParameter("v_conret");  }
    catch (Exception e) { e.printStackTrace();  }
    int v_conse = new Integer(v_conret).intValue();
    CallableStatement l_stmt0 = t_tax.prepareCall("{? = call TB_FELIMINAR_NO_PROCESADO(?,?,?,?,?,?,?)}");
    l_stmt0.registerOutParameter(1,Types.INTEGER);
    l_stmt0.registerOutParameter(8,Types.VARCHAR);
    l_stmt0.setString(2,v_pro);
    l_stmt0.setString(3,v_contra);
    l_stmt0.setInt(4,v_conse);
    l_stmt0.setString(5,v_usuario2);
    l_stmt0.setString(6,v_fecha);
    l_stmt0.setString(7,v_unidad_pro);
    l_stmt0.execute();
    int v_indicador  = l_stmt0.getInt(1);
    String v_mensaje = l_stmt0.getString(8);
    l_stmt0.close();
    if(v_indicador == 0)
    {
     t_tax.commit();
     String v_pintar=    i_pagina.TBFL_CABEZA("Eliminar Solicitud de Retiro No Procesada","Eliminar Solicitud de Retiro No procesada","","<center>"+v_mensaje+"</center>",false);
     out.println(""+v_pintar+"");
     out.println("<BR>");
     out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick='history.go(-1)' ></center>");
     String v_pie = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println(""+v_pie+"");
     out.close();
    }
    else
    {
     String v_pintar=    i_pagina.TBFL_CABEZA("Eliminar Solicitud de Retiro No Procesada","Eliminar Solicitud de Retiro No procesada","","<center>"+v_mensaje+"</center>",false);
     out.println(""+v_pintar+"");
     out.println("<BR>");
     out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
     String v_pie = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println(""+v_pie+"");
     out.close();
     t_tax.rollback();
    }
   }
   else
   {
    String v_pintarout=    i_pagina.TBFL_CABEZA("Eliminar Solicitud de Retiro No Procesada","Eliminar Solicitud de Retiro No procesada","","<center>Error de comunicaci�n no se tiene conexi�n con el servidor web,por favor intente de nuevo.</center>",false);
    out.println(""+v_pintarout+"");
    String v_pieout = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println("<center><input type=button value='Cancelar' onclick=' history.go(-2)'></center>");
    out.println(""+v_pieout+"");
    out.close();
   }
  }
  catch(Exception ex)
  {
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
   String v_pintar=    i_pagina.TBFL_CABEZA ("Eliminar Solicitud de Retiro No Procesada","Error al Eliminar Solicitud de Retiro No procesada","","<center>"+v_menex+"</center>",false);
   out.println(""+v_pintar+"");
   out.println("<BR>");
   out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
   String v_pie = i_pagina.TBFL_PIE;
   out.println("<br>");
   out.println(""+v_pie+"");
   out.close();
  }
     finally{
         try{
             DataSourceWrapper.closeConnection(t_tax);            
         }catch(SQLException sqlEx){
             out.println(sqlEx.toString());
         }
     }
 }
}

