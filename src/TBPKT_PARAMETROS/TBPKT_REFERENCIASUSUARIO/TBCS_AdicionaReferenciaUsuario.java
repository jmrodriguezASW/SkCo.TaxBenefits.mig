package TBPKT_PARAMETROS.TBPKT_REFERENCIASUSUARIO;

import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.TBCL_Validacion;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.owasp.validator.html.*; // INT20131108


public class TBCS_AdicionaReferenciaUsuario extends HttpServlet {
private PrintWriter out;
private TBCL_Consulta v_Consulta;
private String v_nuevaCadena ="";
String cadena = "";
String nuevaCadena = "";
String rutaVirtual = "";
String rutaFisica = "";

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
  Vector v_resultadodeclaracion1 = new Vector();
  v_Consulta.TBPL_RealizarConexion();

  String rutaFisica = TBCL_REFERENCIAS.TBCL_CONSULTA("SMM003");
  //rutaFisica  =  "c:\\Taxbenefits\\Taxbenefits\\public_html\\WEB-INF\\";
  String POLICY_FILE_LOCATION = rutaFisica+"antisamy-tinymce-1.4.4.xml"; // Path to policy file
  AntiSamy as = new AntiSamy(); // INT20131108
  
    //Toma el código del producto
    String v_codigo = request.getParameter("obligatoriocodigo");
    String v_descripcion = request.getParameter("obligatoriodescripcion");
    String v_valor = request.getParameter("v_valor").toUpperCase().replaceAll("&LT;", "<").replaceAll("&GT;", ">");
    //out.println("<FONT color=blue face=Verdana size=2><B>"+v_valor+"</FONT>");
    try{
        CleanResults cr1 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
        v_descripcion = cr1.getCleanHTML();
        CleanResults cr2 = as.scan(v_valor, new File(POLICY_FILE_LOCATION));
        v_valor = cr2.getCleanHTML();
       }
    catch (Exception e) { e.printStackTrace(); }

  if ((v_codigo!=null)&&(v_descripcion!=null))
    {
    v_declaracion = "INSERT INTO tbreferencias (ref_codigo, ref_descripcion, ref_valor) VALUES (UPPER('"+v_codigo+"'), UPPER('"+v_descripcion+"'), UPPER('"+v_valor+"'))";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
    }
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }


  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos

  //publicacion en pagina html de las Consultas
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de referencias de usuario xx","Administración de referencias de usuario", "", v_resultadodeclaracion.elementAt(0).toString(), false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }
  
    

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_REFERENCIASUSUARIO.TBCS_AdicionaReferenciaUsuario Information";
  }
}

 