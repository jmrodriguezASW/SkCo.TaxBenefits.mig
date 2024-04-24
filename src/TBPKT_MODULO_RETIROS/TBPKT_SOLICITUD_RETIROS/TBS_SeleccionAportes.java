package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBS_SeleccionAportes extends HttpServlet implements SingleThreadModel {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = new PrintWriter (response.getOutputStream());
        STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();
        String v_orden_apli = "";/**Variable del orden de aplicacion escogido por el usuario*/
        String v_concepto = "";/**Variable del concepto escogido por el usuario*/
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

         v_orden_apli = request.getParameter("v_orden_apli").toString();         
             
         if (v_orden_apli.equals("0") || v_orden_apli.equals("1")) {
                System.out.println("0 1");
            }
         else {
             if (v_orden_apli.equals("2")) {
                    TBCL_SeleccionAportes objSeleccionAportes= new TBCL_SeleccionAportes();
                    objSeleccionAportes.TBPL_SeleccionAportes(session,request,out,nuevaCadena);                                 
                 }
             else
                 System.out.println("Error en el Orden");
             }            
        }
        catch(Exception ex)
        {
         String v_pintar="";
         String error = ex.toString();
         if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
         {
          v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>No se tiene comunicaci�n con el servidor de datos  por favor ingrese nuevamente.</center>",false);
         }
         else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
              {
               v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
              }
                 else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                      {
                       v_pintar = i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Error momentaneo de comunicaci�n con el servidor de datos, por favor intente entrar de nuevo a la opci�n.</center>",false);
                      }
                      else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                           {
                             v_pintar = i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Error momentaneo de comunicaci�n con el servidor Web, por favor intente entrar de nuevo a la opci�n.</center>",false);
                           }
                        else
                        {
                         v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                        }
         out.println(""+v_pintar+"");
         out.println("<BR>");
         out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-4)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
         String v_pie = i_pagina.TBFL_PIE;
         out.println("<br>");
         out.println(""+v_pie+"");
         out.close();
         }
        }
    }
