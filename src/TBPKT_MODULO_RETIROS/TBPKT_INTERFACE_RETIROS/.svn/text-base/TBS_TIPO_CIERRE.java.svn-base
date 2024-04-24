package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import java.sql.*;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.Connection;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;

public class TBS_TIPO_CIERRE extends HttpServlet implements SingleThreadModel{
  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      PrintWriter     out   = new PrintWriter (response.getOutputStream());
    try{
      SQL_TIPO_CIERRE itipocierre;

      String          vec;
      String          v_coduni = "";
      String          v_codpro = "";
      String          v_fecha  = "";

      HttpSession session = request.getSession(true);
      if(session==null)
        session = request.getSession(true);
      response.setContentType("text/html");
     // Capturar los parámetros seleccionados por el usuario en la página de cierre

      vec      = request.getParameter("tipoCierre");
      v_coduni = request.getParameter("s_unidad");
      v_codpro = request.getParameter("s_producto");
      v_fecha  = request.getParameter("obligatoriov_fecefectiva");
      // Llamada a la clase SQL_TIPO_CIERRE que realiza todo el proceso de cierre
      itipocierre = new SQL_TIPO_CIERRE();
      itipocierre.TBP_TIPO_CIERRE(out, session, v_coduni, v_codpro, vec, v_fecha);
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
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Saldos", "Cierre Saldos","","Error Proc TBS_TIPO_CIERRE: "+v_menex ,false));
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
     }
  }

 public String getServletInfo() {
    return "TBPKT_CIERRE.TBS_TIPO_CIERRE Information";
  }
}

