package TBPKT_PARAMETROS.TBPKT_FORMATOCERTIFICADO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108

//Nombre de la clase que se encarga de adicionar item de año al formato certificado
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_AdicionaFormatoCertificadoItemAno extends HttpServlet implements SingleThreadModel{
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

    //Toma el año del formato certificado
    String ano = "";
    //Toma el v_codigo del producto
    String v_codpro = "";
    //Toma la v_descripcion del item certificado
    String desitemcer = "";
    //toma el titulo del certificado año item
    String titulo = "";
    //Toma la posición  del formato certificado año item
    String posicion = "";

    //Toma de parametros
    /*try{
       ano = request.getParameter("ano");
       v_codpro = request.getParameter("v_codigo");
       desitemcer = request.getParameter("desitemcer");
       titulo = request.getParameter("obligatoriotitulo");
       posicion = request.getParameter("obligatorioposicion");
       }
    catch (Exception e) { e.printStackTrace(); }*///INT20131108
    
    try{
       ano = request.getParameter("ano");
       v_codpro = request.getParameter("v_codigo");
       CleanResults cr1 = as.scan(request.getParameter("desitemcer"), new File(POLICY_FILE_LOCATION));
       desitemcer = cr1.getCleanHTML();
       CleanResults cr2 = as.scan(request.getParameter("obligatoriotitulo"), new File(POLICY_FILE_LOCATION));
       titulo = cr2.getCleanHTML();
       CleanResults cr3 = as.scan(request.getParameter("obligatorioposicion"), new File(POLICY_FILE_LOCATION));
       posicion = cr3.getCleanHTML();
       }
    catch (Exception e) { e.printStackTrace(); }//INT20131108

  v_Consulta.TBPL_RealizarConexion();//Reliza conexion con la base de datos

  //Inserta el item de año
  //---------------------------------
  if ((v_codpro!=null)&&(ano!=null)&&(desitemcer!=null)&&(titulo!=null)&&(posicion!=null))
    {
    //Sentencias SQL
    v_declaracion = "INSERT INTO tbanos_items (ani_foc_pro_codigo, ani_foc_ano, ani_itc_codigo, ani_titulo, ani_posicion) VALUES (UPPER('"+v_codpro+"'), UPPER('"+ano+"'), UPPER('"+desitemcer+"'), UPPER('"+titulo+"'), UPPER('"+posicion+"'))";
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    }//------------------------------------
  //Si hubo algun error muestra un mensaje
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
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_FORMATOCERTIFICADO.TBCL_AdicionaFormatoCertificadoItemAno Information";
  }
}

