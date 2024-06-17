package TBPKT_PARAMETROS.TBPKT_ITEMCERTIFICADO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108


public class TBCS_ModificaItemCertificado extends HttpServlet{
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
       //response.setHeader("Pragma", "No-Cache");
       //response.setDateHeader("Expires", 0);
       String parametros[] = new String[8];
       String  cadena = request.getParameter("cadena");
       v_nuevaCadena = cadena;
       String ip_tax = request.getRemoteAddr();
        
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
       out = new PrintWriter (response.getOutputStream());
       parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}


  v_Consulta = new TBCL_Consulta();
  String v_declaracion = new String();
  Vector v_resultadodeclaracion = new Vector();
  String declaracion1 = new String();
  Vector v_resultadodeclaracion1 = new Vector();

    //Toma eel código del item certificado
    String coditemcert = "";
    //Toma la v_descripcion del item certificado
    String v_descripcion = "";
    //Toma la descripción por default del item certificado
    String v_ddescripcion = "";
    //Toma el tipo de dato del item certificado
    String tipodato = "";
    //Toma el tipo de dato por default del item certificado
    String dtipodato = "";
      /*    try{
       coditemcert = request.getParameter("coditemcert");
       v_descripcion = request.getParameter("obligatoriodescripcion")
       v_ddescripcion = request.getParameter("v_ddescripcion");
       CleanResults cr2 = as.scan(, new File(POLICY_FILE_LOCATION));
       tipodato = request.getParameter("tipodato");
       dtipodato = request.getParameter("dtipodato");
       }
      catch (Exception e) { e.printStackTrace(); }*///INT20131108
    try{
       coditemcert = request.getParameter("coditemcert");
       CleanResults cr1 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
       v_descripcion = cr1.getCleanHTML();
       CleanResults cr2 = as.scan(request.getParameter("v_ddescripcion"), new File(POLICY_FILE_LOCATION));
       v_ddescripcion = cr2.getCleanHTML();
       tipodato = request.getParameter("tipodato");
       dtipodato = request.getParameter("dtipodato");
       }
    catch (Exception e) { e.printStackTrace(); }

    v_Consulta.TBPL_RealizarConexion();


  if (v_descripcion.equals(""))
    v_descripcion = v_ddescripcion;
  if (tipodato.equals("")||tipodato.equals("TIPO DE DATO"))
    tipodato = dtipodato;



  if ((coditemcert!=null)&&(v_descripcion!=null)&&(tipodato!=null))
    {
    v_declaracion = "UPDATE tbitems_certificado SET "+
                  "itc_descripcion = UPPER('"+v_descripcion+"')"+
                  " , itc_ref_tipo_dato = UPPER('"+tipodato+"')"+
                  " WHERE itc_codigo = '"+coditemcert+"'";

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
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administracion de items de certificado de retención en la fuente por producto","Administracion de items de certificado de retención en la fuente por producto", "", "<BLOCKQUOTE><BR>"+resultado.elementAt(0).toString()+"</BLOCKQUOTE>", false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_ITEMCERTIFICADO.TBCS_ModificaItemCertificado Information";
  }
}

