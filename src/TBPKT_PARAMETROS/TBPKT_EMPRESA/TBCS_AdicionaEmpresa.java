package TBPKT_PARAMETROS.TBPKT_EMPRESA;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108

//Nombre de la clase que se encarga de adicionar empresa
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_AdicionaEmpresa extends HttpServlet{
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
        
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
       parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}


  v_Consulta = new TBCL_Consulta();
  String v_declaracion = new String();
  Vector v_resultadodeclaracion = new Vector();
  String declaracion1 = new String();
  Vector v_resultadodeclaracion1 = new Vector();
  v_Consulta.TBPL_RealizarConexion();

    //Toma el grupo de la empresa
    String grupemp = "";
    //Toma la v_descripcion del item certificado
    String v_descripcion = "";
    //Toma el nit de la empresa
    String nit = "";
    //Toma la condición
    String condicion = "";
    //Toma el detalle condición
    String detcon = "";


    //Toma de los parametros
    /*    try{
       grupemp = request.getParameter("obligatoriogrupemp");
       v_descripcion = request.getParameter("obligatoriodescripcion");
       nit = request.getParameter("obligatorionit");
       condicion = request.getParameter("condicion");
       detcon = request.getParameter("detcon");
       }
    catch (Exception e) { e.printStackTrace(); }*///INT20131108
    try{
       grupemp = request.getParameter("obligatoriogrupemp");
       CleanResults cr1 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
       v_descripcion = cr1.getCleanHTML();
       nit = request.getParameter("obligatorionit");
       condicion = request.getParameter("condicion");
       CleanResults cr2 = as.scan(request.getParameter("detcon"), new File(POLICY_FILE_LOCATION));
       detcon = cr2.getCleanHTML();
       }
    catch (Exception e) { e.printStackTrace(); }




  //Adiciona la empresa
  //---------------------------------------------------
  if ((grupemp!=null)&&(v_descripcion!=null)&&(nit!=null)&&(condicion!=null)&&(detcon!=null))
    {
    //Sentencias SQL
    v_declaracion = "INSERT INTO tbempresas\n"+
"    (emp_grupo,\n"+
"     emp_descripcion,\n"+
"     emp_nit,\n"+
"     emp_ref_condicion,\n"+
"     emp_detalle_condicion)\n"+
"VALUES\n"+
"    (UPPER('"+grupemp+"'),\n"+
"     UPPER('"+v_descripcion+"'),\n"+
"     UPPER('"+nit+"'),\n"+
"     decode ('"+condicion+"', 'NULL', null, UPPER('"+condicion+"')),\n"+
"     UPPER('"+detcon+"'))";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }//---------------------------------------------
  //Si no adiciona con exito muestra un mensaje
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
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas","Administración de empresas", "", v_resultadodeclaracion.elementAt(0).toString(), false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_EMPRESA.TBCS_AdicionaEmpresa Information";
  }
}

