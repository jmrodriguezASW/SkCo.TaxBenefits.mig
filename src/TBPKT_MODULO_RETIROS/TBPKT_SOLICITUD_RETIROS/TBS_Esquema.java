package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBS_Esquema extends HttpServlet
{
 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
  PrintWriter out = new PrintWriter (response.getOutputStream());
  /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase STBCL_GenerarBaseHTML, no es necesaria la instancia nueva*/ 
 //STBCL_GenerarBaseHTML i_pagina = new STBCL_GenerarBaseHTML;
  String v_esquema = "";/**Variable Esquema de retiro escogido por el usuario*/
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
    
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
   parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
   v_contrato = parametros[0];
   v_producto = parametros[1];
   v_usuario  = parametros[2];
   v_unidad = parametros[3];
   v_tipousu = parametros[4];
   v_idworker = parametros[5];


   try { v_esquema = request.getParameter("v_esquema"); } catch (Exception e) { e.printStackTrace(); }
   session.removeAttribute("s_esquema");
   session.setAttribute("s_esquema",v_esquema);

   TBCL_Esquema i_esquema= new TBCL_Esquema();
   i_esquema.TBPL_Esquema(session,request,out,nuevaCadena);
      
  }
  catch(Exception ex)
  {
   String v_pintar="";
   String error = ex.toString();
   if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
   {
    v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.</center>",false);
   }
   else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
        {
         v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
        }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_pintar = STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.</center>",false);
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_pintar = STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.</center>",false);
                     }
                  else
                  {
                   v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                  }
   out.println(""+v_pintar+"");
   out.println("<BR>");
   out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-4)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
   String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
   out.println("<br>");
   out.println(""+v_pie+"");
   out.close();
  }
 }
}

