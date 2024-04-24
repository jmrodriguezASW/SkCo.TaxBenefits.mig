package TBPKT_PARAMETROS.TBPKT_EMPRESA;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108


//Nombre de la clase que modifica empresa
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_ModificaEmpresa extends HttpServlet{
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

    //Toma el grupo de la empresa
    String grupemp = "";
    //Toma la v_descripcion del item certificado
    String v_descripcion = "";
    //Toma la descripción por default del item certificado
    String v_ddescripcion = "";
    //Toma el nit de la empresa
    String nit = "";
    //Toma el nit por default de la empresa
    String dnit = "";
    //Toma la condición
    String condicion = "";
    //Toma la condición por default
    String dcondicion = "";
    //Toma el detalle condición
    String detcon = "";
    //Toma el detalle condición por default
    String ddetcon = "";

    //Toma de los parametros
    /*       try{
         grupemp = request.getParameter("grupemp");
         v_descripcion = request.getParameter("obligatoriodescripcion");
         v_ddescripcion = request.getParameter("v_ddescripcion");
         nit = request.getParameter("obligatorionit");
         dnit = request.getParameter("dnit");
         condicion = request.getParameter("condicion");
         dcondicion = request.getParameter("dcondicion");
         detcon = request.getParameter("detcon");
         ddetcon = request.getParameter("ddetcon");

         }
      catch (Exception e) { e.printStackTrace(); }*///INT20131108
    try{
       grupemp = request.getParameter("grupemp");
       CleanResults cr1 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
       v_descripcion = cr1.getCleanHTML(); 
       CleanResults cr2 = as.scan(request.getParameter("v_ddescripcion"), new File(POLICY_FILE_LOCATION));
       v_ddescripcion = cr2.getCleanHTML(); 
       nit = request.getParameter("obligatorionit");
       dnit = request.getParameter("dnit");
       condicion = request.getParameter("condicion");
       dcondicion = request.getParameter("dcondicion");
       CleanResults cr3 = as.scan(request.getParameter("detcon"), new File(POLICY_FILE_LOCATION));
       detcon = cr3.getCleanHTML();
       CleanResults cr4 = as.scan(request.getParameter("ddetcon"), new File(POLICY_FILE_LOCATION));
       ddetcon = cr4.getCleanHTML();
       }
    catch (Exception e) { e.printStackTrace(); }




  if (v_descripcion.equals(""))
    v_descripcion = v_ddescripcion;
  if (nit.equals(""))
    nit = dnit;
  if (condicion.equals(""))
    condicion = dcondicion;
  if (detcon.equals(""))
    detcon = ddetcon;



  //Modifica empresa
  //------------------------------------------------
  if ((grupemp!=null)&&(v_descripcion!=null)&&(nit!=null)&&(condicion!=null)&&(detcon!=null))
    {
    //Sentencias SQL
      v_declaracion = "UPDATE tbempresas SET "+
                  "emp_descripcion = UPPER('"+v_descripcion+"')"+
                  " , emp_nit = UPPER('"+nit+"')"+
                  " , emp_ref_condicion = decode ('"+condicion+"', 'NULL', null, UPPER('"+condicion+"'))"+
                  " , emp_detalle_condicion = UPPER('"+detcon+"')"+
                  " WHERE emp_grupo = '"+grupemp+"'";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }//----------------------------------------------------
  //Si no modifica con exito retorna un error
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
    return "TBPKT_EMPRESA.TBCS_ModificaEmpresa Information";
  }
}

