package TBPKT_PARAMETROS.TBPKT_CIERREUNIDAD;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_ModificaCierreUnidad extends HttpServlet{
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
        
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
       out = new PrintWriter (response.getOutputStream());
       parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

  v_Consulta = new TBCL_Consulta();
  String v_declaracion = new String();
  //Vector donde se guardara el resultado de la Consulta
  Vector v_resultadodeclaracion = new Vector();
  Vector v_resultadodeclaracion1 = new Vector();
  v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    String v_codpro = "";
    String v_unipro = "";
    String tipusu = "";
    String dhora = "";
    String hora = "";
    String min = "";

    //Toma los parametros
    try{
       v_codpro = request.getParameter("v_codpro");
       v_unipro = request.getParameter("v_unipro");
       tipusu = request.getParameter("tipusu");
       dhora= request.getParameter("dhora");
       hora= request.getParameter("hora");
       min = request.getParameter("min");
       }
    catch (Exception e) { e.printStackTrace(); }


 //Modifica cierre de unidad
 //---------------------------------------
 if ((v_codpro!=null)&&(v_unipro!=null)&&(tipusu!=null)&&(hora!=null))
  {

    //Sentencias SQL
    v_declaracion = "UPDATE tbcierres_unidades SET "+
                  "ciu_hora = UPPER('"+hora+min+"')"+
                  " WHERE ciu_pro_codigo = '"+v_codpro+"' AND ciu_ref_unidad_proceso ='"+v_unipro+"' AND ciu_ref_tipo_usuario ='"+tipusu+"'";

    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }


  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //----------------------------------------------------
  //publicacion en pagina html de las Consultas
  TBPL_Publicar(v_resultadodeclaracion);
  }



  //Codigo html que se mostrara en el browser
  private void TBPL_Publicar(Vector resultado)
  {
  resultado.trimToSize();
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cierre de transacción por producto","Administración de cierre de transacción por producto", "", "<BLOCKQUOTE><BR>"+resultado.elementAt(0).toString()+"</BLOCKQUOTE>", false,"moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></FONT></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CIERREUNIDAD.TBCS_ModificaCierreUnidad Information";
  }
}

