package TBPKT_PARAMETROS.TBPKT_PENALIZACION_TRASLADO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_AdicionaPenalizacionTraslado extends HttpServlet implements SingleThreadModel{
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
    } catch(Exception ex){System.out.println("");}
      v_Consulta = new TBCL_Consulta();
      String v_declaracion = new String();
      Vector v_resultadodeclaracion = new Vector();
      String declaracion1 = new String();
      v_Consulta.TBPL_RealizarConexion();

      String v_prod_orig = "";
      String v_prod_dest = "";
      String v_prog_orig = "";
      String v_prog_dest = "";
      String v_tercero = "";
      String v_titular = "";
          
    try{
       v_prod_orig = request.getParameter("obligatorioprodorig");
       v_prod_dest = request.getParameter("obligatorioproddest");
       v_prog_orig = request.getParameter("obligatorioprogorig");
       v_prog_dest = request.getParameter("obligatorioprogdest");
       v_tercero = request.getParameter("obligatoriotercero");
       v_titular = request.getParameter("obligatoriotitular");
       }
    catch (Exception e) { e.printStackTrace(); }

    if ((v_prod_orig!=null)&&(v_prod_dest!=null)&&(v_prog_orig!=null)&&(v_prog_dest!=null)){    
       v_declaracion = "insert into TBPENALIZACIONTRASLADO (ptr_producto_origen, ptr_programa_origen, ptr_producto_destino, ptr_programa_destino, ptr_penaliza_titular, ptr_penaliza_tercero) VALUES " +
                       " ('"+v_prod_orig+"', '"+v_prog_orig+"', '"+v_prod_dest+"', '"+v_prog_dest+"', '"+v_titular+"', '"+v_tercero+"')";
       v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
       //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
    }
    else{
      v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }
    v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  
    //publicacion en pagina html de las Consultas
    out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de penalizacion traslados","Administración de penalizacion traslados", "", v_resultadodeclaracion.elementAt(0).toString(), false, "moduloparametro.js", "return checkrequired(this)"));
    out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></CENTER>");
    out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
    out.close();
  
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_PENALIZACION_TRASLADO.TBCS_AdicionaPenalizacionTraslado Information";
  }
}

