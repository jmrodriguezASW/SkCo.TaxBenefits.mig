package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBS_RetirosUniFecha extends HttpServlet implements SingleThreadModel
{
 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
  PrintWriter out = new PrintWriter (response.getOutputStream());
  STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();
  try
  {
   TBCL_RetirosUniFecha i_retunifec = new TBCL_RetirosUniFecha();
   HttpSession session              = request.getSession(true);
   if(session==null)
    session = request.getSession(true);
   session.setMaxInactiveInterval(3600);
   response.setContentType("text/html");
   /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
   // response.setHeader("Pragma", "No-Cache");
   // response.setDateHeader("Expires", 0);
   String v_contrato = "", v_producto = "", v_usuario  = "", v_unidad = "";
   String v_tipousu = "", v_idworker = "";
   String parametros[] = new String[8];
   String  cadena = request.getParameter("cadena");
   /* Agregado por Marcela Ortiz Sandoval 
   * 30/08/2007
   * Incluye parametros para manejo de contratos corporativos */
   String permiteRetiros = request.getParameter("PermiteRetiros");
   String montoMinRetiro = request.getParameter("MontoMinRetiro");
   String saldoMinRetiro = request.getParameter("SaldoMinRetiro");
   String permiteCerrados = request.getParameter("PermiteCerrados");
   /* Fin del codigo agregado Primera Parte */
   
   /* Agregado por Marcela Ortiz Sandoval 
   * 18/08/2009
   * Incluye parametro para manejo de retiro express */
   String permiteExpress = request.getParameter("RetiroExpress");
  
   
   String nuevaCadena = cadena;
   String ip_tax = request.getRemoteAddr();
   TBCL_Seguridad Seguridad = new TBCL_Seguridad();
   parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
   v_contrato = parametros[0];
   v_producto = parametros[1];
   v_usuario  = parametros[2];
   v_unidad = parametros[3];
   v_tipousu = parametros[4];
   v_idworker = parametros[5];

   session.removeValue("s_producto");
   try
   {
    session.putValue("s_producto",(java.lang.Object)v_producto);//parametros[1]);
   }
   catch (Exception e)
   {
    e.printStackTrace();
   }
   session.removeValue("s_contrato");
   try
   {
    session.putValue("s_contrato",(java.lang.Object)v_contrato);//parametros[0]);
   }
   catch (Exception e)
   {
    e.printStackTrace();
   }
   session.removeValue("s_usuario");
   try
   {
    session.putValue("s_usuario",(java.lang.Object)v_tipousu);//parametros[4]);
   }
   catch (Exception e)
   {
    e.printStackTrace();
   }
   session.removeValue("s_unidad");
   try
   {
    session.putValue("s_unidad",(java.lang.Object)v_unidad);//parametros[3]);
   }
   catch (Exception e)
   {
    e.printStackTrace();
   }
   session.removeValue("s_usuariopipe");
   try
   {
    session.putValue("s_usuariopipe",(java.lang.Object)v_usuario);//parametros[2]);
   }
   catch (Exception e)
   {
    e.printStackTrace();
   }
   /* Agregado por Marcela Ortiz Sandoval 
    * 30/08/2007
    * Incluye parametros para manejo de contratos corporativos */
   try
   {
    session.putValue("permiteRetiros",(java.lang.Object)permiteRetiros);
   }
   catch (Exception e)
   {
    e.printStackTrace();
   }
   try
   {
    session.putValue("montoMinRetiro",(java.lang.Object)montoMinRetiro);
   }
   catch (Exception e)
   {
    e.printStackTrace();
   }
   try
   {
    session.putValue("saldoMinRetiro",(java.lang.Object)saldoMinRetiro);
   }
   catch (Exception e)
   {
    e.printStackTrace();
   }

   try
   {
    session.putValue("permiteCerrados",(java.lang.Object)permiteCerrados);
   }
   catch (Exception e)
   {
    e.printStackTrace();
   }
   /* Fin codigo agregado segunda parte */
   /* Agregado por Marcela Ortiz Sandoval 
    * 18/08/2009
    * Incluye parametro para manejo de retiro express  */
   try
   {
    session.putValue("permiteExpress",(java.lang.Object)permiteExpress);
   }
   catch (Exception e)
   {
    e.printStackTrace();
   }
   i_retunifec.TBPL_FechaRetiro(out,session,request,nuevaCadena,parametros[0],parametros[1],parametros[3],parametros[4]);

  }
  catch(Exception ex)
  {
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
                       v_pintar=  i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicaci�n con el servidor Web, por favor intente entrar de nuevo a la opci�n.</center>",false);
                     }
                  else
                  {
                   v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                  }
     out.println(""+v_pintar+"");
     out.println("<BR>");
     out.println("<br>");
     out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-1)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
     String v_pie = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println(""+v_pie+"");
     out.close();
 }

}

}

