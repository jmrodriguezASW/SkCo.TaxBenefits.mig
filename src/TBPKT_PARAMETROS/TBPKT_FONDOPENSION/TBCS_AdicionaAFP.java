package TBPKT_PARAMETROS.TBPKT_FONDOPENSION;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108

//Nombre de la clase que se encarga de adicionar AFP
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_AdicionaAFP extends HttpServlet implements SingleThreadModel{
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
  Vector v_resultadodeclaracion1 = new Vector();
  v_Consulta.TBPL_RealizarConexion();

    //Toma el v_codigo de la afp
    String codigoafp = "";
    //Toma la v_descripcion de la afp
    String v_descripcion = "";
    //Toma el nit de la afp
    String nit = "";
    //Toma el tipo de afp
    String tipoafp = "";


    //Toma de los parametros
    
    /*    try{
       codigoafp = request.getParameter("obligatoriocodigoafp");
       v_descripcion = request.getParameter("obligatoriodescripcion");
       nit = request.getParameter("obligatorionit");
       tipoafp = request.getParameter("obligatoriotipoafp");
       }
    catch (Exception e) { e.printStackTrace(); }*///INT20131108
    try{
       codigoafp = request.getParameter("obligatoriocodigoafp");
       CleanResults cr1 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
       v_descripcion = cr1.getCleanHTML();
       nit = request.getParameter("obligatorionit");
       tipoafp = request.getParameter("obligatoriotipoafp");
       }
    catch (Exception e) { e.printStackTrace(); }//INT20131108


  //Adicionar AFP
  //-------------------------------------------
  if ((codigoafp!=null)&&(v_descripcion!=null)&&(nit!=null)&&(tipoafp!=null))
    {
    //Sentencias SQL
    v_declaracion = "INSERT INTO tbadm_fondos_pensiones (afp_codigo, afp_descripcion, afp_nit, afp_ref_tipo) VALUES (UPPER('"+codigoafp+"'), UPPER('"+v_descripcion+"'), UPPER('"+nit+"'), UPPER('"+tipoafp+"'))";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }//------------------------------------------
  //Si ocurre un error muestra un mensaje
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo alg�n error al actualizar los datos, verifique todos los datos"));
    }


  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //publicacion en pagina html de las Consultas
  TBPL_Publicar(v_resultadodeclaracion);
  }




  //Codigo html que se mostrara en el browser
  private void TBPL_Publicar(Vector v_resultadodeclaracion)
  {
  v_resultadodeclaracion.trimToSize();
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administraci�n de administradoras de fondos de pensiones","Administraci�n de administradoras de fondos de pensiones", "", v_resultadodeclaracion.elementAt(0).toString(), false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_FONDOPENSION.TBCS_AdicionaAFP Information";
  }
}
