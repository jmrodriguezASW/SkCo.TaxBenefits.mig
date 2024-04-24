package TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
/*clase que maneja la opcion escogida por el usuario, para modificar solicitud de retiro*/
public class TBS_Penalizacion extends HttpServlet implements SingleThreadModel
{
 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
  PrintWriter out = new PrintWriter (response.getOutputStream());
  STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();
  try
  {
   //se toma session
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
   String parametros[] = new String[8];
   String  cadena = request.getParameter("cadena");
   String   nuevaCadena = cadena;
   String ip_tax = request.getRemoteAddr();
   TBCL_Seguridad Seguridad = new TBCL_Seguridad();
   parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
   v_contrato = parametros[0];
   v_producto = parametros[1];
   v_usuario  = parametros[2];
   v_unidad = parametros[3];
   v_tipousu = parametros[4];
   v_idworker = parametros[5];

   String v_conret = "";//consecutivo del retiro
   String v_modificar = "";//opcion tomada por el usuario
   //se toma consecutivo de la solicitud del retiro
   try { v_conret= request.getParameter("v_conret");}
   catch (Exception e){e.printStackTrace(); }
   //se coloca consecutivo de la solicitud de retiro como variable de session
   session.removeValue("s_conret");
   session.putValue("s_conret",(java.lang.Object)v_conret);
   //se toma opcion escogida por el cliente
   try {v_modificar= request.getParameter("v_modificar");}
   catch (Exception e){ e.printStackTrace();}
   //si la opción es modificar código de banco y numeroode cuanta
   if(!v_conret.equals("null"))
   {
    if(v_modificar.equals("0"))
    {
     TBCL_Banco i_banco = new TBCL_Banco();
     i_banco.TBPL_ModificarBanco(out,session,request,nuevaCadena);
    }
    else if(v_modificar.equals("1"))
         {
          TBCL_ActualizarDistribucion i_distribucion = new TBCL_ActualizarDistribucion();
          i_distribucion.TBPL_ActualizarDistribucion(out,session,request,nuevaCadena);
         }
         else if(v_modificar.equals("2"))//si al opcion es eliminar solicitud pendiente de enviar a multifund
              {
               TBCL_EliminarTax i_eliminar = new TBCL_EliminarTax();
               i_eliminar.TBPL_EliminarTax(out,session,request);
              }
              else
              {
               String v_pintarout=    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiro","Error al Modificar Solicitud de Retiro","","<center>Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.</center>",false);
               out.println(""+v_pintarout+"");
               String v_pieout = i_pagina.TBFL_PIE;
               out.println("<br>");
               out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'></center>");
               out.println(""+v_pieout+"");
               out.close();
              }
   }
  }
  catch(Exception ex)
  {//manejo de errores
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
   out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
   String v_pie = i_pagina.TBFL_PIE;
   out.println("<br>");
   out.println(""+v_pie+"");
   out.close();
  }
 }
}

