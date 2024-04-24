package TBPKT_PARAMETROS.TBPKT_CIERREUNIDAD;//Paquete donde se guardarán las clases de cierre de unidad

//Paquetes que utilizaremos en esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Nobre de la clase
public class TBCS_EliminaCierreUnidad extends HttpServlet implements SingleThreadModel{
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
       response.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
       // response.setHeader("Pragma", "No-Cache");
       // response.setDateHeader("Expires", 0);
       String parametros[] = new String[8];
       String  cadena = request.getParameter("cadena");
       v_nuevaCadena = cadena;
       String ip_tax = request.getRemoteAddr();
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       out = new PrintWriter (response.getOutputStream());
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

  v_Consulta = new TBCL_Consulta();
  String v_declaracion = new String();
  Vector v_resultadodeclaracion = new Vector();
  String declaracion1 = new String();
  Vector v_resultadodeclaracion1 = new Vector();
  v_Consulta.TBPL_RealizarConexion();


    String v_codpro = "";
    String v_unipro = "";
    String tipusu = "";
    String hora = "";

    //Toma los parametros que me manda la pagina anterior
    try{
       v_codpro = request.getParameter("v_codpro");
       v_unipro = request.getParameter("v_unipro");
       tipusu = request.getParameter("tipusu");
       hora = request.getParameter("hora");
       }
    catch (Exception e) { e.printStackTrace(); }



   //Borrra cierre de unidad
   //-------------------------------------------------
   if ((v_codpro!=null)&&(v_unipro!=null)&&(tipusu!=null)&&(hora!=null))
     {
  
    //Sentencias SQL
    v_declaracion = "DELETE tbcierres_unidades WHERE ciu_pro_codigo = '"+v_codpro+"' AND ciu_ref_unidad_proceso = '"+v_unipro+"' AND ciu_ref_tipo_usuario = '"+tipusu+"' AND ciu_hora = '"+hora+"'";

    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, false);
    }


    v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
    //-------------------------------------------
    TBPL_Publicar(v_resultadodeclaracion);//Publica pagina HTML
  }

  private void TBPL_Publicar(Vector resultado)//Publica página HTML
  {
  resultado.trimToSize();
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cierre de transacción por producto", "Administración de cierre de transacción por producto", "", "<BLOCKQUOTE><BR>"+resultado.elementAt(0).toString()+"</BLOCKQUOTE>", false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></FONT></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CIERREUNIDAD.TBCS_EliminaCierreUnidad Information";
  }
}

