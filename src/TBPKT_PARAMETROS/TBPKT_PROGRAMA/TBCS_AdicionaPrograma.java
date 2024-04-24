package TBPKT_PARAMETROS.TBPKT_PROGRAMA;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108




public class TBCS_AdicionaPrograma extends HttpServlet{
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
       String parametros[] = new String[8];
       String  cadena = request.getParameter("cadena");
       v_nuevaCadena = cadena;
       String ip_tax = request.getRemoteAddr();
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
    } catch(Exception ex){System.out.println("");}
      v_Consulta = new TBCL_Consulta();
      String v_declaracion = new String();
      Vector v_resultadodeclaracion = new Vector();
      String declaracion1 = new String();
      v_Consulta.TBPL_RealizarConexion();

      String v_codigo = "";
      String v_descripcion = "";
      String v_activo = "";
      String v_tipo_penalizacion = "";
    
    /*  try{
         v_codigo = request.getParameter("obligatoriocodigo");
         v_descripcion = request.getParameter("obligatoriodescripcion");
         v_activo = request.getParameter("obligatorioactivo");
         v_tipo_penalizacion = request.getParameter("obligatoriotippenal");
         }
      catch (Exception e) { e.printStackTrace(); }*/ //INT20131108
    
      try{
         CleanResults cr1 = as.scan(request.getParameter("obligatoriocodigo"), new File(POLICY_FILE_LOCATION));
         v_codigo = cr1.getCleanHTML();
         CleanResults cr2 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
         v_descripcion = cr2.getCleanHTML();
         CleanResults cr3 = as.scan(request.getParameter("obligatorioactivo"), new File(POLICY_FILE_LOCATION));
         v_activo = cr3.getCleanHTML();
         CleanResults cr4 = as.scan(request.getParameter("obligatoriotippenal"), new File(POLICY_FILE_LOCATION));
         v_tipo_penalizacion = cr4.getCleanHTML();
         }
      catch (Exception e) { e.printStackTrace(); }   //INT20131108 
    

    if ((v_codigo!=null)&&(v_descripcion!=null)&&(v_activo!=null)&&(v_tipo_penalizacion!=null)){
       v_declaracion = "insert into tbprogramas_parametrizacion (prg_codigo, prg_descripcion, prg_activo, prg_tipo_penalizacion) VALUES (UPPER('"+v_codigo+"'), UPPER('"+v_descripcion+"'), UPPER('"+v_activo+"'),UPPER('"+v_tipo_penalizacion+"'))";
       v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
       //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
    }
    else{
      v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }
    v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  
    //publicacion en pagina html de las Consultas
    out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de programas","Administración de programas", "", v_resultadodeclaracion.elementAt(0).toString(), false, "moduloparametro.js", "return checkrequired(this)"));
    out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></CENTER>");
    out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
    out.close();
  
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_PROGRAMA.TBCS_AdicionaPrograma Information";
  }
}

