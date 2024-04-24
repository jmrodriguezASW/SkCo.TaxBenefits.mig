package TBPKT_PARAMETROS.TBPKT_FORMATOCERTIFICADO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108


public class TBCS_ModificaFormatoCertificadoItemAno extends HttpServlet{
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
  String declaracion1 = new String();
  Vector v_resultadodeclaracion1 = new Vector();

    //Toma el código del producto
    String v_codpro = "";
    //Toma el año del formato certificado
    String ano = "";
    //Toma la descripción del item certificado
    String v_descripcion = "";
    //Toma el titulo del año item
    String titulo = "";
    //Toma la posicion del año item
    String posicion = "";
    //Toma la posicion por default del año item
    String dposicion = "";

   /* try{
       v_codpro = request.getParameter("v_codigo");
       ano = request.getParameter("ano");
       v_descripcion = request.getParameter("itcodigo");
       titulo = request.getParameter("obligatoriotitulo");
       posicion = request.getParameter("obligatorioposicion");
       dposicion = request.getParameter("dposicion");
       }
    catch (Exception e) { e.printStackTrace(); }*///INT20131108
    try{
       v_codpro = request.getParameter("v_codigo");
       ano = request.getParameter("ano");
       v_descripcion = request.getParameter("itcodigo");
       CleanResults cr1 = as.scan(request.getParameter("obligatoriotitulo"), new File(POLICY_FILE_LOCATION));
       titulo = cr1.getCleanHTML();
       CleanResults cr2 = as.scan(request.getParameter("obligatorioposicion"), new File(POLICY_FILE_LOCATION));
       posicion = cr2.getCleanHTML();
       CleanResults cr3 = as.scan(request.getParameter("dposicion"), new File(POLICY_FILE_LOCATION));
       dposicion = cr3.getCleanHTML();
       }
    catch (Exception e) { e.printStackTrace(); }

    v_Consulta.TBPL_RealizarConexion();


  if (posicion.equals(" "))
    posicion = dposicion;



  if ((ano!=null)&&(v_codpro!=null)&&(v_descripcion!=null)&&(titulo!=null)&&(posicion!=null))
    {
    v_declaracion = "SELECT itc_codigo FROM tbitems_certificado WHERE itc_descripcion = '"+v_descripcion+"'";
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, "itc_codigo");
    v_descripcion = v_resultadodeclaracion.elementAt(0).toString();
    v_resultadodeclaracion.clear();
    //Sentencias SQL
    v_declaracion = "UPDATE tbanos_items SET "+
                  "ani_titulo = UPPER('"+titulo+"')"+
                  " , ani_posicion = UPPER('"+posicion+"')"+
                  " WHERE ani_foc_pro_codigo = '"+v_codpro+"' AND ani_foc_ano ='"+ano+"' AND ani_itc_codigo = '"+v_descripcion+"'";

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

  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administracion de certificados de retención en la fuente por producto","Administracion de certificados de retención en la fuente por producto", "", "<BLOCKQUOTE><BR>"+resultado.elementAt(0).toString()+"</BLOCKQUOTE>", false, "moduloparametro", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_FORMATOCERTIFICADO.TBCL_ModificaFormatoCertificadoItemAno Information";
  }
}

