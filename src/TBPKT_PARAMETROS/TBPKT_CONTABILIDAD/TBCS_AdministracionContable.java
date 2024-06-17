package TBPKT_PARAMETROS.TBPKT_CONTABILIDAD;//Nombre del paquete donde estarán guardadas todas las clases de contabilidad

//importación de todos los paquetes que se utilizarán en esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Nombre de la clase principal de administración contable
public class TBCS_AdministracionContable extends HttpServlet{
private PrintWriter out;//Clase que me permite imprimir el código HTML
private TBCL_Consulta v_Consulta;//Clase que se encarga de ejecutar las declaraciones SQL
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


    //v_Consulta = new TBCL_Consulta();
    String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion3;
    Vector v_resultadodeclaracion2 = new Vector();
    //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


//------------------------------------------

    //Toma el v_codigo del producto
    String v_codpro = "";

    //Toma de los parametros
    try{
       v_codpro = request.getParameter("v_codpro");
       }
    catch (Exception e) { e.printStackTrace(); }

            TBPL_Publicar(v_codpro);//TBPL_Publicar v_codigo HTML para adicionar

     }

      private void TBPL_Publicar(String v_codpro)//Publica v_codigo HTML para adicionar, modificar o eliminar
      {


      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de parametrización contable de conceptos de transacciones por producto","Administración de parametrización contable de conceptos de transacciones por producto","TBPKT_PARAMETROS.TBPKT_CONTABILIDAD.TBCS_ACEMParametrizacionContable", "", true, "moduloparametro.js", "return checkrequired(this)"));

      //Aqui van los campo que se quieren mostrar
      out.println("<BLOCKQUOTE>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='Consulta' CHECKED>Consultar parametrización contable<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='modifica'>Modificar parametrización contable<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adiciona'>Adicionar parametrización contable<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='elimina'>Eliminar parametrización contable<BR>");
      out.println("<INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_codpro+"'>");
      out.println("</font>");
      out.println("</BLOCKQUOTE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos

     out.println(STBCL_GenerarBaseHTML.TBFL_PIE);

      out.close();
      }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONTABILIDAD.TBCS_AdministracionContable Information";
  }
}

