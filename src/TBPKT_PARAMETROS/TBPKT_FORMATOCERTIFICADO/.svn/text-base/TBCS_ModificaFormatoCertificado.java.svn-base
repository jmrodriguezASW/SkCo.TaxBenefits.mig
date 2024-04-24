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


public class TBCS_ModificaFormatoCertificado extends HttpServlet implements SingleThreadModel{
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
    //Toma la descripción del formato certificado por default
    String v_ddescripcion = "";
    //Toma la descripción del formato certificado
    String v_descripcion = "";
    //Toma la TBFL_CABEZA del formato certificado
    String head = "";
    //Toma el TBFL_PIE del formato certificado
    String foot = "";
    String estfor = "";
   /* try{
       v_codpro = request.getParameter("v_codigo");
       ano = request.getParameter("ano");
       v_ddescripcion = request.getParameter("v_ddescripcion");
       v_descripcion = request.getParameter("v_descripcion");
       head = request.getParameter("head");
       foot = request.getParameter("foot");
       estfor = request.getParameter("estfor");
       }
    catch (Exception e) { e.printStackTrace(); }*///int20131108
    
      try{
         v_codpro = request.getParameter("v_codigo");
         ano = request.getParameter("ano");
         CleanResults cr1 = as.scan(request.getParameter("v_ddescripcion"), new File(POLICY_FILE_LOCATION));
         v_ddescripcion = cr1.getCleanHTML();
         CleanResults cr2 = as.scan(request.getParameter("v_descripcion"), new File(POLICY_FILE_LOCATION));
         v_descripcion = cr2.getCleanHTML();
         CleanResults cr3 = as.scan(request.getParameter("head"), new File(POLICY_FILE_LOCATION));
         head = cr3.getCleanHTML();
         CleanResults cr4 = as.scan(request.getParameter("foot"), new File(POLICY_FILE_LOCATION));
         foot = cr4.getCleanHTML();
         estfor = request.getParameter("estfor");
         }
      catch (Exception e) { e.printStackTrace(); }

    v_Consulta.TBPL_RealizarConexion();


  if (v_descripcion.equals(""))
    v_descripcion = v_ddescripcion;


/*    v_declaracion = "UPDATE tbformatos_certificado SET "+
                  "   foc_descripcion = '"+v_descripcion+"')"+
                  " , foc_header = '"+head+"'"+
                  " , foc_footer = '"+foot+"'"+
                  " , foc_ref_codigo = '"+estfor+"'"+
                  " WHERE foc_pro_codigo = '"+v_codpro+"' AND foc_ano ='"+ano+"'";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);*/


/*  if ((ano!=null)&&(v_codpro!=null)&&(v_descripcion!=null)&&(head!=null)&&(foot!=null))
    {*/
    //Sentencias SQL
    v_declaracion = "UPDATE tbformatos_certificado SET "+
                  "foc_descripcion = decode('"+v_descripcion.trim()+"','',null,'"+v_descripcion+"')  "+
                  " , foc_header =decode('"+head.trim()+"','',null,'"+head+"')  "+
                  " , foc_footer = decode('"+foot.trim()+"','',null,'"+foot+"')  "+
                  " , foc_ref_codigo = decode('"+estfor.trim()+"','',null,'"+estfor+"')  "+
                  " WHERE foc_pro_codigo = '"+v_codpro.trim()+"' AND foc_ano ='"+ano+"'";


    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
/*    }
  else if ((ano!=null)&&(v_codpro!=null)&&(v_descripcion!=null)&&(foot!=null))
    {
    //Sentencias SQL
    v_declaracion = "UPDATE tbformatos_certificado SET "+
                  "foc_descripcion = UPPER('"+v_descripcion+"')"+
                  " , foc_footer = UPPER('"+foot+"')"+
                  " , foc_ref_codigo = '"+estfor+"'"+
                  " WHERE foc_pro_codigo = '"+v_codpro+"' AND foc_ano ='"+ano+"'";


    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }
  else if ((ano!=null)&&(v_codpro!=null)&&(v_descripcion!=null)&&(head!=null))
    {
    //Sentencias SQL
    v_declaracion = "UPDATE tbformatos_certificado SET "+
                  "foc_descripcion = '"+v_descripcion+"'"+
                  " , foc_header = '"+head+"'"+
                  " , foc_ref_codigo = '"+estfor+"'"+
                  " WHERE foc_pro_codigo = '"+v_codpro+"' AND foc_ano ='"+ano+"'";


    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }
  else if ((ano!=null)&&(v_codpro!=null)&&(v_descripcion!=null))
    {
    //Sentencias SQL
    v_declaracion = "UPDATE tbformatos_certificado SET "+
                  "foc_descripcion = '"+v_descripcion+"'"+
                  " , foc_ref_codigo = '"+estfor+"'"+
                  " WHERE foc_pro_codigo = '"+v_codpro+"' AND foc_ano ='"+ano+"'";


    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }*/


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
    return "TBPKT_FORMATOCERTIFICADO.TBCL_ModificaFormatoCertificado Information";
  }
}

