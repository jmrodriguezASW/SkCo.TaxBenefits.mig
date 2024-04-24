package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import sqlj.runtime.ref.DefaultContext ;

import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

public class TBS_PASO5 extends HttpServlet {

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
    Connection t_tax    =   null;
    try
     {
      int v_veces = 1;
      String  v_veces_s = "";
      v_veces_s = request.getParameter("vveces");
      Integer x = new Integer(v_veces_s);
      v_veces = x.intValue();
      int v_proceso = 1;
      String  v_proceso_s = "";
      v_proceso_s = request.getParameter("vproceso");
      Integer y = new Integer(v_proceso_s);
      v_proceso = y.intValue();

      /**Realizar conexion a la base de datos*/
      t_tax =   DataSourceWrapper.getInstance().getConnection();
      //SQL_PASO5 isql_saldos = new SQL_PASO5();
      //isql_saldos.TBP_PASO5(v_veces, v_proceso);
      /*
      Modificacion:
      Se añade el procedimiento de invocacion a un procedimiento del AS400
      */
      CallableStatement cs = t_tax.prepareCall ( "{call SQL_PASO5.TBP_PASO5(?,?)}");
      cs.setInt(1,v_veces);
      cs.setInt(2,v_proceso);
      cs.executeUpdate();
      cs.close();
      /* Final de la modificacion */
      out.close();
     }
     catch(Exception e){
      System.out.println(" ");
     }
      finally{
        try{
            DataSourceWrapper.closeConnection(t_tax);                  
        }catch(SQLException sqlEx){
            System.out.println(sqlEx.toString());
        }
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

 