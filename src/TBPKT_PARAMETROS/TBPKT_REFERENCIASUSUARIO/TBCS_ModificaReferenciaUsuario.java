package TBPKT_PARAMETROS.TBPKT_REFERENCIASUSUARIO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_ModificaReferenciaUsuario extends HttpServlet{
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
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  v_Consulta = new TBCL_Consulta();
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

  String v_declaracion = new String();
  //Vector donde se guardara el resultado de la Consulta
  Vector v_resultadodeclaracion = new Vector();
  v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos



    String v_codigo = "";
    String v_descripcion = "";
    String v_ddescripcion = "";
    String v_valor = "";
    String v_dvalor = "";

    /*  try{
         v_codigo = request.getParameter("v_codigo");
         v_descripcion = request.getParameter("obligatoriodescripcion");
         v_ddescripcion = request.getParameter("v_ddescripcion");
         v_valor = request.getParameter("v_valor");
         v_dvalor = request.getParameter("v_dvalor");
         }
      catch (Exception e) { e.printStackTrace(); }*/ // INT20131108

    try{
       v_codigo = request.getParameter("v_codigo");
       CleanResults cr1 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
       v_descripcion = cr1.getCleanHTML();
       CleanResults cr2 = as.scan(request.getParameter("v_ddescripcion"), new File(POLICY_FILE_LOCATION));
       v_ddescripcion = cr2.getCleanHTML();
       CleanResults cr3 = as.scan(request.getParameter("v_valor"), new File(POLICY_FILE_LOCATION));
       v_valor = cr3.getCleanHTML(); 
       CleanResults cr4 = as.scan(request.getParameter("v_dvalor"), new File(POLICY_FILE_LOCATION));
       v_dvalor = cr4.getCleanHTML();
       }
    catch (Exception e) { e.printStackTrace(); }  // INT20131108

  if (v_descripcion.equals(" "))
    v_descripcion = v_ddescripcion;
  if (v_valor.equals(" "))
    v_valor = v_dvalor;



 if ((v_codigo!=null)&&(v_descripcion!=null))
  {

    //Sentencias SQL
    v_declaracion = "UPDATE tbreferencias SET "+
                  "ref_descripcion = UPPER('"+v_descripcion+"')"+
                  " , ref_valor = UPPER('"+v_valor+"')"+
                  " WHERE ref_codigo = '"+v_codigo+"'";


    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }


  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //publicacion en pagina html de las Consultas
  TBPL_Publicar(v_resultadodeclaracion);
  }

  //Codigo html que se mostrara en el browser
  private void TBPL_Publicar(Vector resultado)
  {
  resultado.trimToSize();
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de referencias de usuario","Administración de referencias de usuario","", "<BLOCKQUOTE><BR>"+resultado.elementAt(0).toString()+"</BLOCKQUOTE>", false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_REFERENCIASUSUARIO.TBCS_ModificaReferenciaUsuario Information";
  }
}

 