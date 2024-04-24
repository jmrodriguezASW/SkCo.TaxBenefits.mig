package TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD;

import TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.SQL_PTB_RETIROS_OBLIG;
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



public class TBS_FinalizarEliminar_Oblig extends HttpServlet implements SingleThreadModel
{
  private String[] v_valusu;
  private String[] resul = null;
  String v_ruta_serv = ""; 
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { PrintWriter out = new PrintWriter(response.getOutputStream());
    STBCL_GenerarBaseHTML i_pagina = new STBCL_GenerarBaseHTML();
    
    try
    {
      HttpSession session = request.getSession(true);
      if (session == null) {
        session = request.getSession(true);
      }
      session.setMaxInactiveInterval(3600);
      response.setContentType("text/html");
      
      String v_contrato = "";String v_producto = "";String v_usuario = "";
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
      String v_pintar = "";
      String v_pie = "";
      
      if (((String)session.getValue("s_contrato") != null) || ((String)session.getValue("s_producto") != null)) {
        String v_pro = (String)session.getValue("s_producto");
        String v_contra = (String)session.getValue("s_contrato");
        String s_conret = request.getParameter("v_conret");
        v_ruta_serv = ((String)session.getValue("s_ruta_serv"));
        
        SQL_PTB_RETIROS_OBLIG objSQL_PTB_RETIROS_OBLIG = new SQL_PTB_RETIROS_OBLIG();
        resul = objSQL_PTB_RETIROS_OBLIG.ELIMINAR_RETIRO_OBLIG(v_pro, v_contra, s_conret, v_usuario);
        
        if (!resul[0].trim().equals("0")) {
          v_pintar = STBCL_GenerarBaseHTML.TBFL_CABEZA("Eliminar Solicitud de Retiro", "Error al Eliminar Solicitud de Retiro", "", "<center>" + resul[1] + "</center>", false);
          out.println("" + v_pintar + "");
          v_pie = "<td class=\"td6\">&nbsp;</td> \n<td class=\"td4\">&nbsp;</td></tr> \n<tr class=\"tr1\"><!-- row 3 --> \n<td class=\"td9\">&nbsp;</td> \n<td class=\"td10\">&nbsp;</td> \n<td class=\"td9\">&nbsp;</td> \n<td class=\"td4\">&nbsp;</td></tr> \n<br></td></tr> \n</table> \n</table></form></body></html> \n";
          out.println("<br>");
          out.println("<center><input type=button value='Regresar' onclick=' history.go(-1)'><input type=button value='Cancelar'  onclick=' history.go(-2)'></center>");
          out.println("" + v_pie + "");
          out.close();
        }
        else {
          v_pintar = STBCL_GenerarBaseHTML.TBFL_CABEZA("Eliminar Solicitud de Retiro", "Eliminar Solicitud de Retiro", "", "<center>Solicitud de retiro eliminada del Sistema</center>", false);
          out.println("" + v_pintar + "");
          v_pie = "<td class=\"td6\">&nbsp;</td> \n<td class=\"td4\">&nbsp;</td></tr> \n<tr class=\"tr1\"><!-- row 3 --> \n<td class=\"td9\">&nbsp;</td> \n<td class=\"td10\">&nbsp;</td> \n<td class=\"td9\">&nbsp;</td> \n<td class=\"td4\">&nbsp;</td></tr> \n<br></td></tr> \n</table> \n</table></form></body></html> \n";
          out.println("<br>");
          
          out.println("<center>");
          out.println("</form><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
          out.println("<FORM name=link5 id=link5 action=http://" + v_ruta_serv.trim() + "/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_EliminarSolicitud_Oblig method=post target=content>" + "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='" + nuevaCadena + "'>" + "<input type=submit name = 'Aceptar' value='Aceptar'>" + "</FORM> </center>");
          


          out.println("" + v_pie + "");
          out.close();
        }
      }
    }
    catch (Exception ex)
    {
      String v_menex = "";
      String error = ex.toString();
      if (ex.equals("java.sql.SQLException: found null connection context"))
      {
        v_menex = "Error de comunicaci�n no se tiene conexi�n con el servidor web,por favor intente de nuevo.";
      }
      else if ((error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel")) || (error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available")))
      {
        v_menex = "No se tiene comunicaci�n con el servidor de datos  por favor ingrese nuevamente.";
      }
      else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
      {
        v_menex = "Se reinicio la base de datos por favor ingrese nuevamente.";
      }
      else if (error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
      {
        v_menex = "Error momentaneo de comunicaci�n con el servidor de datos, por favor intente entrar de nuevo a la opci�n.";
      }
      else if (error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
      {
        v_menex = "Error momentaneo de comunicaci�n con el servidor Web, por favor intente entrar de nuevo a la opci�n.";
      }
      else
      {
        v_menex = "Mensaje de error: " + ex;
      }
      String v_pintar = STBCL_GenerarBaseHTML.TBFL_CABEZA("Modificar Solicitud de Retiro", "Error al Modificar Solicitud de Retiro", "", "<center>" + v_menex + "</center>", false);
      out.println("" + v_pintar + "");
      out.println("<BR>");
      out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
      String v_pie = "<td class=\"td6\">&nbsp;</td> \n<td class=\"td4\">&nbsp;</td></tr> \n<tr class=\"tr1\"><!-- row 3 --> \n<td class=\"td9\">&nbsp;</td> \n<td class=\"td10\">&nbsp;</td> \n<td class=\"td9\">&nbsp;</td> \n<td class=\"td4\">&nbsp;</td></tr> \n<br></td></tr> \n</table> \n</table></form></body></html> \n";
      out.println("<br>");
      out.println("" + v_pie + "");
      out.close();
    }
  }
}
