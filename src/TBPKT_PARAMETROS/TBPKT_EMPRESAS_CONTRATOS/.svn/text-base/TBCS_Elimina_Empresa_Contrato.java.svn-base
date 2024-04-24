package TBPKT_PARAMETROS.TBPKT_EMPRESAS_CONTRATOS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_Elimina_Empresa_Contrato extends HttpServlet implements SingleThreadModel{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
private String v_nuevaCadena ="";

  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   * Process the HTTP Get request
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try{
       out = new PrintWriter (response.getOutputStream());
       response.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
       //response.setHeader("Pragma", "No-Cache");
       //response.setDateHeader("Expires", 0);
       String parametros[] = new String[8];
       String  cadena = request.getParameter("cadena");
       v_nuevaCadena = cadena;
       String ip_tax = request.getRemoteAddr();
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

  v_Consulta = new TBCL_Consulta();
  String v_declaracion = new String();
  Vector v_resultadodeclaracion = new Vector();
  String declaracion1 = new String();
  v_Consulta.TBPL_RealizarConexion();

    //Toma el código del producto
    String v_codpro = "";
    String grupo = "";
    String contrato = "";

    try{
       v_codpro = request.getParameter("v_codpro");
       grupo = request.getParameter("grupo");
       contrato = request.getParameter("contrato");
       }
    catch (Exception e) { e.printStackTrace(); }

    v_declaracion = "DELETE tbempresas_contratos \n"+
                    " WHERE emc_emp_grupo = '"+grupo+"' \n"+
                   " AND emc_con_pro_codigo = '"+v_codpro+"' \n"+
                   " AND emc_con_numero = '"+contrato+"'";
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, false);
    v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos

  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas contratos","Administración de empresas contratos", "", "<BLOCKQUOTE>"+v_resultadodeclaracion.elementAt(0).toString()+"</BLOCKQUOTE>", false));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_EMPRESAS_CONTRATOS.TBCS_Elimina_Empresa_Contrato Information";
  }
}

