package TBPKT_PARAMETROS.TBPKT_FESTIVOS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

import org.owasp.validator.html.CleanResults;

//Nombre de la clase que se encarga de modificar festivos
public class TBCS_ModificaFestivos extends HttpServlet implements SingleThreadModel{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
private String v_nuevaCadena;

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
       //response.setHeader("Pragma", "No-Cache");
       //response.setDateHeader("Expires", 0);
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

    //Toma el código del producto
    String fecha = "";
    String v_descripcion = "";
    String v_ddescripcion = "";

      //Toma de los parametros
      /* try{
       fecha = request.getParameter("fecha");
       v_ddescripcion = request.getParameter("v_ddescripcion");
       v_descripcion = request.getParameter("obligatoriodescripcion");
       }
    catch (Exception e) { e.printStackTrace(); }*///INT20131108
      try{
         fecha = request.getParameter("obligatoriofecha");
         CleanResults cr1 = as.scan(request.getParameter("v_ddescripcion"), new File(POLICY_FILE_LOCATION));
         v_ddescripcion = cr1.getCleanHTML();
         CleanResults cr2 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
         v_descripcion = cr2.getCleanHTML();
         }
      catch (Exception e) { e.printStackTrace(); }



  if (v_descripcion.equals(" "))
    v_descripcion = v_ddescripcion;


  //Modifica festivos
  //-------------------------------------
  if ((fecha!=null)&&(v_descripcion!=null))
    {
      v_declaracion = "UPDATE tbfestivos SET "+
                  "fes_descripcion = UPPER('"+v_descripcion+"')"+
                  " WHERE fes_fecha = to_date('"+fecha+"', 'yyyy-mm-dd')";

    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }//--------------------------
  //Si ocurre un error muestra un mensaje
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }


  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //publicacion en pagina html de las Consultas
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de festivos","Administración de festivos", "", v_resultadodeclaracion.elementAt(0).toString(), false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_FESTIVOS.TBCS_ModificaFestivos Information";
  }
}

