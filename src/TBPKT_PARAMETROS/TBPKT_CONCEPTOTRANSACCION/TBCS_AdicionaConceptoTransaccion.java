package TBPKT_PARAMETROS.TBPKT_CONCEPTOTRANSACCION;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_AdicionaConceptoTransaccion extends HttpServlet{
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
       response.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
       // response.setHeader("Pragma", "No-Cache");
       // response.setDateHeader("Expires", 0);
       String parametros[] = new String[8];
       String  cadena = request.getParameter("cadena");
       v_nuevaCadena = cadena;
       String ip_tax = request.getRemoteAddr();
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       out = new PrintWriter (response.getOutputStream());
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

  v_Consulta = new TBCL_Consulta();
  String v_declaracion = new String();
  Vector v_resultadodeclaracion = new Vector();
  String declaracion1 = new String();
  Vector v_resultadodeclaracion1 = new Vector();
  v_Consulta.TBPL_RealizarConexion();


    String transac = "";
    String tipotransac = "";
    String clasetransac = "";
    String v_descripcion = "";


    //Toma los parametros de transacción, tipo de transaccion y clase de transaccion
    /*     try{
    transac = request.getParameter("transac");
    tipotransac = request.getParameter("tipotransac");
    clasetransac = request.getParameter("clasetransac");
    v_descripcion = request.getParameter("obligatoriodescripcion");
    }
    catch (Exception e) { e.printStackTrace(); }*///INT20131108
    try{
       transac = request.getParameter("transac");
       tipotransac = request.getParameter("tipotransac");
       clasetransac = request.getParameter("clasetransac");
       CleanResults cr1 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
       v_descripcion = cr1.getCleanHTML();
       }
    catch (Exception e) { e.printStackTrace(); }

  //Adiciona concepto transacción
  //------------------------------------------
  if ((transac!=null)&&(tipotransac!=null)&&(clasetransac!=null)&&(v_descripcion!=null))
    {
    //Sentencias SQL
    v_declaracion = "INSERT INTO tbconceptos_transaccion (cot_ref_transaccion, cot_ref_tipo_transaccion, cot_ref_clase_transaccion, cot_descripcion) VALUES (UPPER('"+transac+"'), UPPER('"+tipotransac+"'), UPPER('"+clasetransac+"'), UPPER('"+v_descripcion+"'))";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }//--------------------------------------------------
  //Si no adiciono con exxito muestra un mensaje
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }


  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos

  //publicacion en pagina html de las Consultas
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cargos por concepto de transacciones","Administración de cargos por concepto de transacciones", "", v_resultadodeclaracion.elementAt(0).toString(), false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></FONT></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONCEPTOTRANSACCION.TBCS_AdicionaConceptoTransaccion Information";
  }
}

