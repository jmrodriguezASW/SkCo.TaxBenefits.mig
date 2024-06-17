package TBPKT_PARAMETROS.TBPKT_EMPRESA;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Nombre de la clase que se encarga de eliminar la empresa
public class TBCS_EliminaEmpresa extends HttpServlet{
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

    //Toma la el grupo de la empresa
    String grupemp = "";
    try{
       grupemp = request.getParameter("grupemp");
       }
    catch (Exception e) { e.printStackTrace(); }

  //Eliminación de la empresas
  //-----------------------
  v_Consulta.TBPL_RealizarConexion();

    //Sentencias SQL
    v_declaracion = "DELETE tbempresas WHERE emp_grupo = '"+grupemp+"'";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, false);


    v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //-------------------------------------------
    TBPL_Publicar(v_resultadodeclaracion);//Publica el resultado de la eliminación
  }

  private void TBPL_Publicar(Vector v_resultadodeclaracion)//Publicación en código HTML
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
    return "TBPKT_EMPRESA.TBCS_EliminaEmpresa Information";
  }
}

