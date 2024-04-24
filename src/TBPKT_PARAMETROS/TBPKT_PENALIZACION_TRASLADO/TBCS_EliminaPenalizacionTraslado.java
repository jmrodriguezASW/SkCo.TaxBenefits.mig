package TBPKT_PARAMETROS.TBPKT_PENALIZACION_TRASLADO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_EliminaPenalizacionTraslado extends HttpServlet{
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

    String v_prod_orig = "";
    String v_prod_dest = "";
    String v_prog_orig = "";
    String v_prog_dest = "";

    try{
       v_prod_orig = request.getParameter("obligatorioprodorig");
       v_prod_dest = request.getParameter("obligatorioproddest");
       v_prog_orig = request.getParameter("obligatorioprogorig");
       v_prog_dest = request.getParameter("obligatorioprogdest");
       }
    catch (Exception e) { e.printStackTrace(); }

    if ((v_prod_orig!=null)&&(v_prog_orig!=null)&&(v_prod_dest!=null)&&(v_prog_dest!=null))
      {
  
      //Sentencias SQL
      v_declaracion = "DELETE TBPENALIZACIONTRASLADO WHERE PTR_PRODUCTO_ORIGEN ='"+ v_prod_orig + 
                      "' AND PTR_PROGRAMA_ORIGEN ='"+v_prog_orig+"' AND PTR_PRODUCTO_DESTINO ='"+v_prod_dest + 
                      "' AND PTR_PROGRAMA_DESTINO = '"+v_prog_dest+"'";
      //out.println(v_declaracion);
      //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, false);
      }
    else
      {
      v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
      }

    v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  
    //publicacion en pagina html de las Consultas
    out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de penalización de traslados","Administración de penalizacion de traslados", "", v_resultadodeclaracion.elementAt(0).toString(), false, "moduloparametro.js", "return checkrequired(this)"));
    out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
    out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
    out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_PRODUCTO.TBCS_PenalizacionTraslado Information";
  }
}

 