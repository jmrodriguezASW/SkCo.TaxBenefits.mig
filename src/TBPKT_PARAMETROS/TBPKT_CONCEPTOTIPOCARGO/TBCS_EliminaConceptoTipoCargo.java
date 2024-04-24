package TBPKT_PARAMETROS.TBPKT_CONCEPTOTIPOCARGO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_EliminaConceptoTipoCargo extends HttpServlet{
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

    //Toma el código del producto
    String v_codpro = "";
    String transac = "";
    String tipotransac = "";
    String clasetransac = "";

    //Toma los parametros
    try{
       v_codpro = request.getParameter("v_codpro");
       transac = request.getParameter("transaccion");
       tipotransac = request.getParameter("tipotransaccion");
       clasetransac = request.getParameter("clasetransaccion");
       }
    catch (Exception e) { e.printStackTrace(); }

  //Elimina concepto tipo cargo
  //-------------------------------------
  if ((v_codpro!=null)&&(transac!=null)&&(tipotransac!=null)&&(clasetransac!=null))
    {
    //Sentencias SQL
    v_declaracion = "DELETE tbconceptos_tipos_cargo WHERE ctc_prc_pro_codigo = '"+v_codpro+"' AND ctc_prc_cot_ref_transaccion = '"+transac+"' AND ctc_prc_cot_ref_tipo_trans = '"+tipotransac+"' AND ctc_prc_cot_ref_clase_trans = '"+clasetransac+"'";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, false);
    //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
    }//-----------------------------
  //Si no se elimino con exito muestra un mensaje
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }


  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos

  //publicacion en pagina html de las Consultas
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones por producto","Administración de conceptos de transacciones por producto", "", v_resultadodeclaracion.elementAt(0).toString(), false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></FONT></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONCEPTOTIPOCARGO.TBCS_EliminaConceptoTipoCargo Information";
  }
}

 