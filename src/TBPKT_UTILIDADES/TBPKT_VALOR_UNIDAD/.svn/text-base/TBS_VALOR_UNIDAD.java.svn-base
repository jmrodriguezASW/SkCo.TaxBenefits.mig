package TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class TBS_VALOR_UNIDAD extends HttpServlet {

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
    try{

    
      String  v_fecha_c  = "";
      v_fecha_c          = request.getParameter("vfechac");
      String  v_fecha_u  = "";
      v_fecha_u          = request.getParameter("vfechau");
      String  v_contrato = "";
      v_contrato         = request.getParameter("vcontrato");
      double v[]         = new double[3];
      v                   = SQL_VALOR_UNIDAD_CC.TBF_CALCULO_VALOR_UNIDAD(v_fecha_c,
                                                                         v_fecha_u,
                                                                         v_contrato,
                                                                         "MFUND",
                                                                         true,
                                                                         1);
      out.close();
     }
     catch(Exception e){
      System.out.println("");
     }
  }

  /**
   * Process the HTTP Post request
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = new PrintWriter (response.getOutputStream());
    out.println("<html>");
    out.println("<head><title>TBS_VALOR_UNIDAD</title></head>");
    out.println("<body>");
    out.println("</body></html>");
    out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_RETIROS.TBS_VALOR_UNIDAD Information";
  }
}

 