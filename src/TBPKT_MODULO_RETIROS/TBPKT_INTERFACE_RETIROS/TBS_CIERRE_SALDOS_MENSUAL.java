package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import java.util.*;
import java.sql.*;

/*
Modificacion:
Se agrega el siguiente import para obtener conexion a la base de datos
e invocar a SQL_CIERRE_SALDOS
*/

import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;


public class TBS_CIERRE_SALDOS_MENSUAL extends HttpServlet {

  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   * Process the HTTP Get request
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter          out;
    out = new PrintWriter (response.getOutputStream());
    try
     {
      String  v_fecha_p = "";
      v_fecha_p = request.getParameter("vfecha");

/*
Modificacion: se elimina la creacion del objeto de tipo SQL_CIERRE_SALDOS
y la invocacion del metodo que se comunica con el AS/400 TB_CIERRE_SALDOS_CONTRATO
      SQL_CIERRE_SALDOS isql_saldos = new SQL_CIERRE_SALDOS();
      isql_saldos.TB_CIERRE_SALDOS_CONTRATO(v_fecha_p);
*/

/*
Modificacion: Se adicionan las siguientes lineas:
*/

  Connection conn = null;
  CallableStatement cs = null;

      try
      {
          conn =   DataSourceWrapper.getInstance().getConnection();
          cs = conn.prepareCall("{? = call SQL_CIERRE_SALDOS.TB_CIERRE_SALDOS_CONTRATO(?)}");
          cs.registerOutParameter(1,Types.INTEGER);
          cs.setString(2,v_fecha_p);
          cs.executeUpdate();
          int v_resultado = cs.getInt(1);
          cs.close();
      }
      catch(SQLException e)
      {
          System.out.println("Error llamando a SQL_CIERRE_SALDOS.TB_CIERRE_SALDOS_CONTRATO");
          System.out.println(e);
      }
      finally
      {
          DataSourceWrapper.closeConnection(conn);
      }

/* final de la modificacion */

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
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Cierre Saldos", "Cierre Saldos","","Error Proc TBS_CIERRE_SALDOS: "+v_menex ,false));
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
     }
  }

  /**
   * Process the HTTP Post request
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = new PrintWriter (response.getOutputStream());
    out.println("<html>");
    out.println("<head><title>TBS_CIERRE_SALDOS_MENSUAL</title></head>");
    out.println("<body>");
    out.println("</body></html>");
    out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_RETIROS.TBS_CIERRE_SALDOS_MENSUAL Information";
  }
}

 