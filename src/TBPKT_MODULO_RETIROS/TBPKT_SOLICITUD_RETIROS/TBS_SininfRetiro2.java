package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBS_SininfRetiro2 extends HttpServlet
{

 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
  PrintWriter out = new PrintWriter (response.getOutputStream());
  STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();
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
   String parametros[] = new String[8];
   String  cadena = request.getParameter("cadena");
   String   nuevaCadena = cadena;
   String v_idsaro ="";

   String ip_tax = request.getRemoteAddr();
   TBCL_Seguridad Seguridad = new TBCL_Seguridad();
   parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
   v_contrato = parametros[0];
   v_producto = parametros[1];
   v_usuario  = parametros[2];
   v_unidad = parametros[3];
   v_tipousu = parametros[4];
   v_idworker = parametros[5];

  if((java.lang.String)session.getAttribute("s_contrato")!= null || (java.lang.String)session.getAttribute("s_producto")!= null)
     {
        String v_pro = (java.lang.String)session.getAttribute("s_producto");//"MFUND";
        String v_contra = (java.lang.String)session.getAttribute("s_contrato");//"000011425";
        String v_nom    = (java.lang.String)session.getAttribute("s_nombres");
        String v_ape    = (java.lang.String)session.getAttribute("s_apellidos");
        String v_nota = (java.lang.String)session.getAttribute("s_nota");
        String v_pintar=    i_pagina.TBFL_CABEZA("Calcular Solicitud de Retiro","Calcular Solicitud de Retiro","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_CalcularRetiro","",true,"modulo_retiros.js","return validar_retiro(this)");
        out.println(""+v_pintar+"");
        /**Modificado 2009/10/27 Variable Id Saro*/
        try{
          if((java.lang.String)session.getAttribute("v_idsaro")!= null){
            v_idsaro = (java.lang.String)session.getAttribute("v_idsaro");
            session.removeAttribute("v_idsaro");
            session.setAttribute("v_idsaro",v_idsaro);
          }
        }catch (Exception e)  { e.printStackTrace(); }
     
        /*Cambio para manejo de referencia unica 2009/03/30 MOS */
        String v_contrato_unif = (java.lang.String)session.getAttribute("s_contra_unif");
        out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
        out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
        out.println("<br>");
        out.println("<br>");
        if(v_nota.equals("S"))
        {
         session.removeAttribute("s_indinformacion");
         session.setAttribute("s_indinformacion","S");
         out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'>Se tienen aportes sin información de traslado.¿Desea realizar la solicitud de retiro?</font></center>");
         out.println("<br>");
         out.println("<PRE>");
         out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
         out.println("<center><input type=submit value='Aceptar' name ='aceptar'><input type=button value='Regresar' onclick=' history.go(-1)' name = 'regresar'><input type=button value='Cancelar' onclick='history.go(-6)' name ='cancelar'></center>");
         String v_piemin2 = i_pagina.TBFL_PIE;
         out.println("<br>");
         out.println("<br>");
         out.println(""+v_piemin2+"");
         out.close();
        }
       else
       {
         session.removeAttribute("s_indinformacion");
         session.setAttribute("s_indinformacion","N");
         out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'>¿Desea realizar la solicitud de retiro?</font></center>");
         out.println("<br>");
         out.println("<PRE>");
         out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
         out.println("<center><input type=submit value='Aceptar' name ='aceptar'><input type=button value='Regresar' onclick=' history.go(-1)' name = 'regresar'><input type=button value='Cancelar' onclick='history.go(-6)' name ='cancelar'></center>");

         String v_piemin2 = i_pagina.TBFL_PIE;
         out.println("<br>");
         out.println("<br>");
         out.println(""+v_piemin2+"");
         out.close();
       }
     }
     else
     {
        String v_pintarout=    i_pagina.TBFL_CABEZA("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.</center>",false);
        out.println(""+v_pintarout+"");
        String v_pieout = i_pagina.TBFL_PIE;
        out.println("<br>");
        out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-6)'></center>");
        out.println(""+v_pieout+"");
        out.close();
     }
  }
  catch(Exception ex)
  {
   String v_pintar="";
   String error = ex.toString();
   if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
   {
    v_pintar=    i_pagina.TBFL_CABEZA("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.</center>",false);
   }
   else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
        {
         v_pintar=    i_pagina.TBFL_CABEZA("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
        }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_pintar = i_pagina.TBFL_CABEZA("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.</center>",false);
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_pintar =  i_pagina.TBFL_CABEZA("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.</center>",false);
                     }
                  else
                  {
                   v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                  }
   out.println(""+v_pintar+"");
   out.println("<BR>");
   out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-6)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
   String v_pie = i_pagina.TBFL_PIE;
   out.println("<br>");
   out.println(""+v_pie+"");
   out.close();
  }
 }
}

