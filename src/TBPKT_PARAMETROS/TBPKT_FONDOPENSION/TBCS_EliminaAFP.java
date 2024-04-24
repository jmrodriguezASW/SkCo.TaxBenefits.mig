package TBPKT_PARAMETROS.TBPKT_FONDOPENSION;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Nombre de la clase que se encarga de eliminar AFP
public class TBCS_EliminaAFP extends HttpServlet implements SingleThreadModel{
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
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

  v_Consulta = new TBCL_Consulta();
  String v_declaracion = new String();
  Vector v_resultadodeclaracion = new Vector();
  String declaracion1 = new String();
  Vector v_resultadodeclaracion1 = new Vector();

    //Toma el c�digo de la AFP
    String codigoafp = "";
    //Toma de los parametros
    try{
       codigoafp = request.getParameter("codigoafp");
       }
    catch (Exception e) { e.printStackTrace(); }

  v_Consulta.TBPL_RealizarConexion();

  //Elimina AFP
  //----------------------------------------------------
  //Sentencias SQL
  v_declaracion = "DELETE tbadm_fondos_pensiones WHERE afp_codigo = '"+codigoafp+"'";
  //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
  v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, false);
  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //-----------------------------------------------
  TBPL_Publicar(v_resultadodeclaracion);
  }

  private void TBPL_Publicar(Vector v_resultadodeclaracion)
  {
  v_resultadodeclaracion.trimToSize();
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administraci�n de administradoras de fondos de pensiones","Administraci�n de administradoras de fondos de pensiones", "", v_resultadodeclaracion.elementAt(0).toString(), false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_FONDOPENSION.TBCS_EliminaAFP Information";
  }
}

 