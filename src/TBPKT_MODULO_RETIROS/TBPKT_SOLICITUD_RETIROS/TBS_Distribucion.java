package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBS_Distribucion extends HttpServlet
{
 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
  PrintWriter out = new PrintWriter (response.getOutputStream());
  STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML();
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


   String v_retotal = "";
   try { v_retotal = request.getParameter("v_retotal"); }
   catch (Exception e) { e.printStackTrace(); }

   String v_total = (java.lang.String)session.getAttribute("s_total");
    if((java.lang.String)session.getAttribute("s_total") != null)
   {
    if( v_total.equals("N"))
    {
     if(v_retotal.equals("x"))
     {
      TBCL_Distribucion i_distribucion= new TBCL_Distribucion();
      i_distribucion.TBPL_DistribucionFondos(session,request,out,nuevaCadena);
     }
     else if(v_retotal.equals("z")) 
     {
      TBCL_RetiroFinal i_final = new TBCL_RetiroFinal();
      i_final.TBPL_Final(session,request,out,nuevaCadena);
     }
    }
    if( v_total.equals("S"))
    {
     TBCL_RetiroTotal i_total = new TBCL_RetiroTotal();
     i_total.TBPL_RetTotal(session,request,out,nuevaCadena);
    }
   }
   else
   {
    String  v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error de comunicaci�n no se tiene conexi�n con el servidor web,por favor intente de nuevo.</center>",false);
    out.println(""+v_pintar+"");
    String v_pie = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'></center>");
    out.println(""+v_pie+"");
    out.close();
   }
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
                 v_pintar =  i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicaci�n con el servidor de datos, por favor intente entrar de nuevo a la opci�n.</center>",false);
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_pintar =  i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicaci�n con el servidor Web, por favor intente entrar de nuevo a la opci�n.</center>",false);
                     }
                  else
                  {
                   v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                  }
    out.println(""+v_pintar+"");
    out.println("<BR>");
    out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
    String v_pie = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println(""+v_pie+"");
    out.close();
  }
 }
}

