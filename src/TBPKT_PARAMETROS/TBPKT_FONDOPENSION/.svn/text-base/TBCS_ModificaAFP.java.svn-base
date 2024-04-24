package TBPKT_PARAMETROS.TBPKT_FONDOPENSION;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108


//Nombre de la clase que se encarga de modificar la AFP
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_ModificaAFP extends HttpServlet implements SingleThreadModel{
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

    //Toma el código de la AFP
    String codigoafp = "";
    //Toma la v_descripcion de la AFP
    String v_descripcion = "";
    //Toma la descripción por default de la AFP
    String v_ddescripcion = "";
    //Toma el nit de la AFP
    String nit = "";
    //Toma el nit por default de la AFP
    String dnit = "";
    //Toma el tipo de la AFP
    String tipoafp = "";
    //Toma la el tipo de la AFP por default
    String dtipoafp = "";

    //Toma de parametros
    /*     try{
       codigoafp = request.getParameter("codigoafp");
       v_descripcion = request.getParameter("obligatoriodescripcion");
       v_ddescripcion = request.getParameter("v_ddescripcion");
       nit = request.getParameter("obligatorionit");
       dnit = request.getParameter("dnit");
       tipoafp = request.getParameter("tipoafp");
       dtipoafp = request.getParameter("dtipoafp");
       }
    catch (Exception e) { e.printStackTrace(); }*///INT20131108
    
    try{
       codigoafp = request.getParameter("codigoafp");
       CleanResults cr1 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
       v_descripcion = cr1.getCleanHTML();
       v_ddescripcion = request.getParameter("v_ddescripcion");
       nit = request.getParameter("obligatorionit");
       dnit = request.getParameter("dnit");
       tipoafp = request.getParameter("tipoafp");
       dtipoafp = request.getParameter("dtipoafp");
       }
    catch (Exception e) { e.printStackTrace(); }///INT20131108




  if (v_descripcion.equals(""))
    v_descripcion = v_ddescripcion;
  if (nit.equals(""))
    nit = dnit;
  if (tipoafp.equals(""))
    tipoafp = dtipoafp;



  //Realiza la modificación
  //-----------------------------
  if ((codigoafp!=null)&&(v_descripcion!=null)&&(nit!=null)&&(tipoafp!=null))
    {
    //Sentencias SQL
    v_declaracion = "UPDATE tbadm_fondos_pensiones SET "+
                  "afp_descripcion = UPPER('"+v_descripcion+"')"+
                  " , afp_nit = UPPER('"+nit+"')"+
                  " , afp_ref_tipo = UPPER('"+tipoafp+"')"+
                  " WHERE afp_codigo = UPPER('"+codigoafp+"')";

    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }//--------------------------
  //Si ocurre un error retorna un mensaje
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }


  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //publicacion en pagina html de las Consultas
  TBPL_Publicar(v_resultadodeclaracion);
  }





  //Codigo html que se mostrara en el browser
  private void TBPL_Publicar(Vector v_resultadodeclaracion)
  {
  v_resultadodeclaracion.trimToSize();
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de administradoras de fondos de pensiones","Administración de administradoras de fondos de pensiones", "", v_resultadodeclaracion.elementAt(0).toString(), false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_FONDOPENSION.TBCS_ModificaAFP Information";
  }
}

