package TBPKT_PARAMETROS.TBPKT_FORMATOCERTIFICADO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108

//Nombre de clase que se encarga de adicionar formatos certificados
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_AdicionaFormatoCertificado extends HttpServlet{
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
       out = new PrintWriter (response.getOutputStream());
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


    //Codigo del producto
    String cod = "";
    //Año del formato certificado
    String ano = "";
    //Descripción del formato certificado
    String v_descripcion = "";
    //Toma el enTBFL_CABEZAdo del formato certificado
    String head = "";
    //Toma el TBFL_PIE del formato certificado
    String foot = "";
    //Toma la descripción del item certificado
    String descod = "";
    //Toma el titulo del año item
    String titulo = "";
    //Toma la posición del año item
    String posicion = "";
    String estfor = "";

    //Toma de los parametros
    
   /*
   try{
      cod = request.getParameter("v_codigo");
      ano = request.getParameter("ano");
      v_descripcion = request.getParameter("obligatoriodescripcion");
      head = request.getParameter("head");
      foot = request.getParameter("foot");
      descod = request.getParameter("desitemcer");
      titulo = request.getParameter("obligatoriotitulo");
      posicion = request.getParameter("obligatorioposicion");
      estfor = request.getParameter("estfor");
      }
    *///INT20131108
    
    try{
       cod = request.getParameter("v_codigo");
       ano = request.getParameter("ano");
       CleanResults cr1 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
       v_descripcion = cr1.getCleanHTML();
       CleanResults cr2 = as.scan(request.getParameter("head"), new File(POLICY_FILE_LOCATION));
       head = cr2.getCleanHTML();
       CleanResults cr3 = as.scan(request.getParameter("foot"), new File(POLICY_FILE_LOCATION));
       foot = cr3.getCleanHTML();
       descod = request.getParameter("desitemcer");
       CleanResults cr4 = as.scan(request.getParameter("obligatoriotitulo"), new File(POLICY_FILE_LOCATION));
       titulo = cr4.getCleanHTML();
       CleanResults cr5 = as.scan(request.getParameter("obligatorioposicion"), new File(POLICY_FILE_LOCATION));
       posicion = cr5.getCleanHTML();
       estfor = request.getParameter("estfor");
       }
       catch (Exception e) { e.printStackTrace(); }//INT20131108


  v_Consulta.TBPL_RealizarConexion();//Reliza conexion con la base de datos
  //Adición de formato certificado
  //---------------------------------------------
  if ((cod!=null)&&(ano!=null)&&(v_descripcion!=null)&&(head!=null)&&(foot!=null)&&(descod!=null)&&(titulo!=null)&&(posicion!=null))
    {
    //Sentencias SQL
    v_declaracion = "INSERT INTO tbformatos_certificado (foc_pro_codigo, foc_ano, foc_descripcion, foc_header, foc_footer, foc_ref_codigo) VALUES (UPPER('"+cod+"'), UPPER('"+ano+"'), UPPER('"+v_descripcion+"'), UPPER('"+head+"'), UPPER('"+foot+"'), UPPER('"+estfor+"'))";

    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);

    //Sentencias SQL
    v_declaracion = "INSERT INTO tbanos_items (ani_foc_pro_codigo, ani_foc_ano, ani_itc_codigo, ani_titulo, ani_posicion) VALUES ('"+cod+"', '"+ano+"', '"+descod+"', '"+titulo+"', '"+posicion+"')";

    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }
  else if ((cod!=null)&&(ano!=null)&&(v_descripcion!=null)&&(foot!=null)&&(descod!=null)&&(titulo!=null)&&(posicion!=null))
    {
    //Sentencias SQL
    v_declaracion = "INSERT INTO tbformatos_certificado (foc_pro_codigo, foc_ano, foc_descripcion,  foc_footer, foc_ref_codigo) VALUES ('"+cod+"', '"+ano+"', '"+v_descripcion+"', '"+foot+"', UPPER('"+estfor+"'))";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);

    //Sentencias SQL
    v_declaracion = "INSERT INTO tbanos_items (ani_foc_pro_codigo, ani_foc_ano, ani_itc_codigo, ani_titulo, ani_posicion) VALUES ('"+cod+"', '"+ano+"', '"+descod+"', '"+titulo+"', '"+posicion+"')";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }
  else if ((cod!=null)&&(ano!=null)&&(v_descripcion!=null)&&(head!=null)&&(descod!=null)&&(titulo!=null)&&(posicion!=null))
    {
    //Sentencias SQL
    v_declaracion = "INSERT INTO tbformatos_certificado (foc_pro_codigo, foc_ano, foc_descripcion, foc_header, foc_ref_codigo) VALUES ('"+cod+"', '"+ano+"', '"+v_descripcion+"', '"+head+"', UPPER('"+estfor+"'))";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);

    //Sentencias SQL
    v_declaracion = "INSERT INTO tbanos_items (ani_foc_pro_codigo, ani_foc_ano, ani_itc_codigo, ani_titulo, ani_posicion) VALUES ('"+cod+"', '"+ano+"', '"+descod+"', '"+titulo+"', '"+posicion+"')";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }
  else if ((cod!=null)&&(ano!=null)&&(v_descripcion!=null)&&(descod!=null)&&(titulo!=null)&&(posicion!=null))
    {
    //Sentencias SQL
    v_declaracion = "INSERT INTO tbformatos_certificado (foc_pro_codigo, foc_ano, foc_descripcion, foc_ref_codigo) VALUES ('"+cod+"', '"+ano+"', '"+v_descripcion+"', UPPER('"+estfor+"'))";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);

    //Sentencias SQL
    v_declaracion = "INSERT INTO tbanos_items (ani_foc_pro_codigo, ani_foc_ano, ani_itc_codigo, ani_titulo, ani_posicion) VALUES ('"+cod+"', '"+ano+"', '"+descod+"', '"+titulo+"', '"+posicion+"')";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }//---------------------------------------------
  //Si no adiciona con exito muestra un mensaje
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algun error al adicionar los datos, verifique si todos los datos con el asterisco tienen datos"));
    }

  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //publicacion en pagina html de las Consultas
  TBPL_Publicar(v_resultadodeclaracion);
  }


  //Codigo html que se mostrara en el browser
  private void TBPL_Publicar(Vector resultado)
  {
  resultado.trimToSize();
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administracion de certificados de retención en la fuente por producto","Administracion de certificados de retención en la fuente por producto", "", "<BLOCKQUOTE><BR>"+resultado.elementAt(0).toString()+"</BLOCKQUOTE>", false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_FORMATOCERTIFICADO.TBCL_AdicionaFormatoCertificado Information";
  }
}

