package TBPKT_PARAMETROS.TBPKT_EMPRESAS_CONTRATOS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108



public class TBCS_Adiciona_Empresa_Contrato extends HttpServlet{
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
      //INT20131108
      String rutaFisica = TBCL_REFERENCIAS.TBCL_CONSULTA("SMM003");
      //rutaFisica  =  "c:\\Taxbenefits\\Taxbenefits\\public_html\\WEB-INF\\";
      String POLICY_FILE_LOCATION = rutaFisica+"antisamy-tinymce-1.4.4.xml"; // Path to policy file
      AntiSamy as = new AntiSamy(); // INT20131108
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
    String condempcont = "";
    String detcond = "";
      /*        try{
         v_codpro = request.getParameter("v_codpro");
         grupo = request.getParameter("grupo");
         contrato = request.getParameter("contrato");
         condempcont = request.getParameter("condempcont");
        detcond = (request.getParameter("detcond");
         }
      catch (Exception e) { e.printStackTrace(); }*///INT20131108

    try{
       v_codpro = request.getParameter("v_codpro");
       grupo = request.getParameter("grupo");
       contrato = request.getParameter("contrato");
       condempcont = request.getParameter("condempcont");
       CleanResults cr1 = as.scan(request.getParameter("detcond"), new File(POLICY_FILE_LOCATION));
       detcond = cr1.getCleanHTML();
       }
    catch (Exception e) { e.printStackTrace(); }

      v_declaracion = "INSERT INTO tbempresas_contratos (emc_emp_grupo, emc_con_pro_codigo, \n"+
                    " emc_con_numero, emc_ref_condicion, emc_detalle_condicion) \n"+
                    " VALUES (UPPER('"+grupo+"'), UPPER('"+v_codpro+"'), \n"+
                    " UPPER('"+contrato+"'), DECODE('"+condempcont+"','null',NULL,UPPER('"+condempcont+"')), \n"+
                    " DECODE('"+detcond.trim()+"','',NULL,UPPER('"+detcond+"')))";
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos

  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración empresas contratos","Administración de empresas contratos", "", "<BLOCKQUOTE>"+v_resultadodeclaracion.elementAt(0).toString()+"</BLOCKQUOTE>", false));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_EMPRESAS_CONTRATOS.TBCS_Adiciona_Empresa_Contrato Information";
  }
}

