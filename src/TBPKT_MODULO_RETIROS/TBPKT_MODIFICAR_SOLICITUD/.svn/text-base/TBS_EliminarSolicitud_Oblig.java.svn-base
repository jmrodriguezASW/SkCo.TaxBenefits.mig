package TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD;

import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.TBCL_Seguridad;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TBS_EliminarSolicitud_Oblig extends HttpServlet implements SingleThreadModel
{  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    PrintWriter out = new PrintWriter(response.getOutputStream());
    STBCL_GenerarBaseHTML i_pagina = new STBCL_GenerarBaseHTML();
    
    try
    {
      HttpSession session = request.getSession(true);
      if (session == null)
        session = request.getSession(true);
      session.setMaxInactiveInterval(3600);
      response.setContentType("text/html");
      String v_contrato = "";String v_producto = "";String v_usuario = "";String v_unidad = "";
      String v_tipousu = "";String v_idworker = "";
      String nuevaCadena = "";
      String[] parametros = new String[8];
      String cadena = request.getParameter("cadena");
      nuevaCadena = cadena;
      String ip_tax = request.getRemoteAddr();
      TBCL_Seguridad Seguridad = new TBCL_Seguridad();
      parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
      v_contrato = parametros[0];
      v_producto = parametros[1];
      v_usuario = parametros[2];
      v_unidad = parametros[3];
      v_tipousu = parametros[4];
      v_idworker = parametros[5];
      
      session.removeValue("s_producto");
      try
      {
        session.putValue("s_producto", v_producto);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      session.removeValue("s_contrato");
      
      try
      {
        session.putValue("s_contrato", "" + Integer.parseInt(v_contrato));
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      session.removeValue("s_usuario");
      try
      {
        session.putValue("s_usuario", v_tipousu);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      session.removeValue("s_unidad");
      
      try
      {
        session.putValue("s_unidad", v_unidad);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      session.removeValue("s_usuariopipe");
      try
      {
        session.putValue("s_usuariopipe", v_usuario);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      TBCL_EliminarSol_Oblig i_modificar = new TBCL_EliminarSol_Oblig();
      i_modificar.TBPL_EliminarSol_Oblig(out, session, request, nuevaCadena);
    }
    catch (Exception ex)
    {
      String v_menex = "";
      String error = ex.toString();
      if (ex.equals("java.sql.SQLException: found null connection context"))
      {
        v_menex = "Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.";
      }
      else if ((error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel")) || (error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available")))
      {
        v_menex = "No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.";
      }
      else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
      {
        v_menex = "Se reinicio la base de datos por favor ingrese nuevamente.";
      }
      else if (error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
      {
        v_menex = "Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.";
      }
      else if (error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
      {
        v_menex = "Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.";
      }
      else
      {
        v_menex = "Mensaje de error: " + ex;
      }
      String v_pintar = STBCL_GenerarBaseHTML.TBFL_CABEZA("Modificar Solicitud de Retiro", "Error al Modificación  Solicitud de Retiro", "", "<center>" + v_menex + "</center>", false);
      out.println("" + v_pintar + "");
      out.println("<BR>");
      out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
      String v_pie = "<td class=\"td6\">&nbsp;</td> \n<td class=\"td4\">&nbsp;</td></tr> \n<tr class=\"tr1\"><!-- row 3 --> \n<td class=\"td9\">&nbsp;</td> \n<td class=\"td10\">&nbsp;</td> \n<td class=\"td9\">&nbsp;</td> \n<td class=\"td4\">&nbsp;</td></tr> \n<br></td></tr> \n</table> \n</table></form></body></html> \n";
      out.println("<br>");
      out.println("" + v_pie + "");
      out.close();
    }
  }
}
