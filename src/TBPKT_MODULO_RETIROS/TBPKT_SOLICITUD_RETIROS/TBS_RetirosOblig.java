package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;

import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.TBCL_Seguridad;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TBS_RetirosOblig extends HttpServlet {
     
     public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
      PrintWriter out = new PrintWriter (response.getOutputStream());
      STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();
      try
      {
       TBCL_RetirosOblig i_retoblig = new TBCL_RetirosOblig();
       HttpSession session              = request.getSession(true);
       if(session==null)
        session = request.getSession(true);
       session.setMaxInactiveInterval(3600);
       response.setContentType("text/html");
       String v_contrato = "", v_producto = "", v_usuario  = "", v_unidad = "";
       String v_tipousu = "", v_idworker = "";
       String parametros[] = new String[8];
       String permiteRetiros = request.getParameter("PermiteRetiros");
       String montoMinRetiro = request.getParameter("MonMinRetiro");
       String saldoMinRetiro = request.getParameter("SaldoMinRetiro");
       String permiteCerrados = request.getParameter("PermiteCerrados");
       String  cadena = request.getParameter("cadena");       
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
    
       session.removeAttribute("s_producto");
       try
       {
        session.setAttribute("s_producto",(java.lang.Object)v_producto);//parametros[1]);
       }
       catch (Exception e)
       {
        e.printStackTrace();
       }
       session.removeAttribute("s_contrato");
      
       try
       {
        session.setAttribute("s_contrato",(java.lang.Object)v_contrato);//parametros[0]);
       }
       catch (Exception e)
       {
        e.printStackTrace();
       }
       session.removeAttribute("s_usuario");
      
       try
       {
        session.setAttribute("s_usuariopipe",(java.lang.Object)v_usuario);//parametros[4]);
       }
       catch (Exception e)
       {
        e.printStackTrace();
       }
    
       try
       {
        session.setAttribute("permiteCerrados",(java.lang.Object)permiteCerrados);
       }
       catch (Exception e)
       {
        e.printStackTrace();
       }
          
       i_retoblig.TBPL_FechaRetiro(out,session,request,nuevaCadena);
    
      }
      catch(Exception ex)
      {
        String v_pintar="";
        String error = ex.toString();
        if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
         {
            v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.</center>",false);
         }
         else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
             {
                v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
             }
               else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                    {
                     v_pintar = i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.</center>",false);
                    }
                    else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                         {
                           v_pintar=  i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.</center>",false);
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
