package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


/**
 * TBS_PASO10
 * Servlet que permite acceder al paso 10 en cual se insertan los traslados 
 * internos del mismo cliente 
 * @autor Marcela Ortiz Sandoval nortiz@skandia.com.co
 * @date 23/07/07
 * @return java.lang.String
 */
public class TBS_PASO10 extends HttpServlet {

  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter          out;
    out = new PrintWriter (response.getOutputStream());
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
      out.close();
     }
     catch(Exception e){
      System.out.println(" ");
     }
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_RETIROS.TBS_PASO10 Information";
  }
}