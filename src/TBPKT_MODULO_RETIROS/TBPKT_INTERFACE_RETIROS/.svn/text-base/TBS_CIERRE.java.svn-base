package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBS_CIERRE extends HttpServlet implements SingleThreadModel{
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //***********************************************
    //   CIERRE CON SEGURIDAD
    //***********************************************
    PrintWriter    out          = new PrintWriter (response.getOutputStream());
    String         parametros[] = new String [6];
    TBCL_Seguridad FB           = new TBCL_Seguridad();
    SQL_CIERRE     icierre      = new SQL_CIERRE();
    String         encriptada;
    String         ip_tax;
    String         sid;
    //Llamar funcion de Seguridad para validar si el usuario esta autorizado a ver la
    //pagina de cierres. Esta funcion devuelve el control al servlet de cierre en caso
    //de que el usuario sea autorizado. En caso contrario Pipeline toma el control.
    try{
      encriptada = request.getParameter("cadena");
      ip_tax = request.getRemoteAddr();
      sid = request.getRequestedSessionId();
      parametros = FB.TBFL_Seguridad(encriptada, out, ip_tax);
      response.setContentType("text/html");
    }
    catch(Exception ex){
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Error Procedimiento TBS_CIERRE: "+ex ,false));
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
   }
   //Si el usuario esta autorizado llamar procedimiento que dibuja la pagina de cierres
   try{
      HttpSession session = request.getSession(true);
      if( session == null)
        session = request.getSession(true);
      session.putValue("s_cierre","2");
      icierre.TBP_CIERRE_T(out, session);
      out.close();
    }
   catch(Exception ex){
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

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Unidades", "Cierre Unidades","","Error Proc TBS_CIERRE: "+v_menex ,false));
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
    }
}

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CIERRE.TBS_CIERRE Information";
  }
}

