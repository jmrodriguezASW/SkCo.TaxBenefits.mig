package TBPKT_PARAMETROS.TBPKT_MINIMOFONDO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_AdicionaMinimoFondo extends HttpServlet{
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
  v_Consulta.TBPL_RealizarConexion();

    //Toma el v_codigo del producto
    String v_codigo = "";
    //Toma el fondo
    String fondo = "";
    //Toma el porcentaje
    String porcentaje = "";

    try{
       v_codigo = request.getParameter("obligatoriocodigo");
       fondo = request.getParameter("obligatoriofondo");
       porcentaje = request.getParameter("obligatorioporcentaje");
       }
    catch (Exception e) { e.printStackTrace(); }


    Float fporcentaje = new Float(porcentaje);
    if (fporcentaje.floatValue()<0||fporcentaje.floatValue()>100)
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de porcentajes mínimos por fondo", "Administración de porcentajes mínimos por fondo", "", "No debe ingresar porcentajes mayores a 100 o menosres a 0", false));
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></CENTER>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }
    else
      {


  if ((v_codigo!=null)&&(fondo!=null)&&(porcentaje!=null))
    {
    //Sentencias SQL
    v_declaracion = "INSERT INTO tbminimos_fondo (mif_pro_codigo, mif_ref_fondo, mif_porcentaje) VALUES (UPPER('"+v_codigo+"'), UPPER('"+fondo+"'), UPPER('"+porcentaje+"'))";

    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }


//  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //publicacion en pagina html de las Consultas
  TBPL_Publicar(v_resultadodeclaracion);

      }//No deja ingresar porcentajes mayores a 100 o menores a 0
    
      v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos

  }



  //Codigo html que se mostrara en el browser
  private void TBPL_Publicar(Vector resultado)
  {
  resultado.trimToSize();
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de porcentajes mínimos por fondo", "Administración de porcentajes mínimos por fondo", "", "<BLOCKQUOTE><BR>"+resultado.elementAt(0).toString()+"</BLOCKQUOTE>", false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_MINIMOFONDO.TBCS_AdicionaMinimoFondo Information";
  }
}

