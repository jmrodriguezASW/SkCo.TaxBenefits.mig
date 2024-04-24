package TBPKT_PARAMETROS.TBPKT_CONTABILIDAD;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.GTBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Nombre de la clase que se encarga de eliminar la contabilidad
public class TBCS_EliminaContabilidad extends HttpServlet{
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
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       out = new PrintWriter (response.getOutputStream());
       response.setContentType("text/html");

  v_Consulta = new TBCL_Consulta();
  String v_declaracion = new String();
  Vector v_resultadodeclaracion = new Vector();
  String declaracion1 = new String();
  Vector v_resultadodeclaracion1 = new Vector();
  v_Consulta.TBPL_RealizarConexion();

    String v_codpro = "";
    String linmov = "";

    //Toma de los parametros
    try{
       v_codpro = request.getParameter("v_codpro");
       linmov = request.getParameter("linmov");
       }
    catch (Exception e) { e.printStackTrace(); }



   //Elimina contabilidad
   //-----------------------
   if ((v_codpro!=null)&&(linmov!=null))
     {

    //Sentencias SQL
    v_declaracion = "DELETE tbcontabilidades WHERE cob_pro_codigo = '"+v_codpro+"' AND cob_linea_movimiento = "+linmov;

    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, false);
    //-----------------------------------
    }
    v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
    TBPL_Publicar(v_resultadodeclaracion);
  }

  private void TBPL_Publicar(Vector resultado)//Publica el resultado de la eliminación
  {
  resultado.trimToSize();

  out.println(GTBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de parametrización contable de conceptos de transacciones por producto","Administración de parametrización contable de conceptos de transacciones por producto","", "<BLOCKQUOTE><BR>"+resultado.elementAt(0).toString()+"</BLOCKQUOTE>", false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></CENTER>");
  out.println(GTBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONTABILIDAD.TBCS_EliminaContabilidad Information";
  }
}

