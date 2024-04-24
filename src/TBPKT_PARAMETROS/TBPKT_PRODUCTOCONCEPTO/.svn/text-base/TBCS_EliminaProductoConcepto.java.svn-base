package TBPKT_PARAMETROS.TBPKT_PRODUCTOCONCEPTO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_EliminaProductoConcepto extends HttpServlet implements SingleThreadModel{
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
  v_Consulta.TBPL_RealizarConexion();

    //Toma el código del producto
    String v_codpro = "";
    String transaccion = "";
    String tipotransaccion = "";
    String clasetransaccion = "";
    try{
       v_codpro = request.getParameter("v_codpro");
       transaccion = request.getParameter("transaccion");
       tipotransaccion = request.getParameter("tipotransaccion");
       clasetransaccion = request.getParameter("clasetransaccion");
       }
    catch (Exception e) { e.printStackTrace(); }

  if ((v_codpro!=null)&&(transaccion!=null)&&(tipotransaccion!=null)&&(clasetransaccion!=null))
    {
    //Sentencias SQL
    v_declaracion = "DELETE tbproductos_conceptos WHERE prc_pro_codigo = '"+v_codpro+"' AND prc_cot_ref_transaccion = '"+transaccion+"' AND prc_cot_ref_tipo_transaccion = '"+tipotransaccion+"' AND prc_cot_ref_clase_transaccion = '"+clasetransaccion+"'";

    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, false);
    }
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }


  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos

  //publicacion en pagina html de las Consultas
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones por producto","Administración de conceptos de transacciones por producto", "", v_resultadodeclaracion.elementAt(0).toString(), false , "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_PRODUCTOCONCEPTO.TBCS_EliminaProductoConcepto Information";
  }
}

