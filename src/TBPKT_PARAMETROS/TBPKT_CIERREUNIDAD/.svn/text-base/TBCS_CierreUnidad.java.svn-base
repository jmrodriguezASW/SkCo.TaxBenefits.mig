package TBPKT_PARAMETROS.TBPKT_CIERREUNIDAD;//Paquete de el manejo de cierres de unidad

//paquetes que utilizaremos en esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


//Nombre de la clase
public class TBCS_CierreUnidad extends HttpServlet implements SingleThreadModel{
private PrintWriter out;//Se enjcarga de imprimir el v_codigo html
//Clase que se encarga de manejar las declaraciones que se harán con la base de datos
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

    v_Consulta = new TBCL_Consulta();//Clase que se encarga de manejar las sentencias SQL
    String v_declaracion;//Cadena donde se guardaran las declaraciones
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion, v_resultadodeclaracion1;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

//------------------------------------------

      v_declaracion = "SELECT pro_codigo FROM tbproductos ORDER BY pro_codigo";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, "pro_codigo");
      v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'UUP%' AND ref_codigo != 'UUP000' ORDER BY ref_descripcion";
      v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);
      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      PublicarConsultar(v_resultadodeclaracion, v_resultadodeclaracion1);//TBPL_Publicar pagina HTML
  }



      private void PublicarConsultar(Vector v_opciones, Vector v_opciones1)//TBPL_Publicar pagina HTML
      {
      v_opciones.trimToSize();
      v_opciones1.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cierre de transacción por producto", "Administración de cierre de transacción por producto", "TBPKT_PARAMETROS.TBPKT_CIERREUNIDAD.TBCS_ACEMUnidadCierre", "", true));

      out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
      //Aqui van los campo que se quieren mostrar
      out.println("<BLOCKQUOTE>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='Consulta' CHECKED>Consultar cierre de transacción<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='modifica'>Modificar cierre de transacción<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='elimina'>Eliminar cierre de transacción<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adiciona'>Adicionar cierre de transacción<BR>&nbsp;&nbsp;<BR>");
      out.println("</BLOCKQUOTE>");
      out.println("</font>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=v_codpro>");
      for (int i=0; i<v_opciones.size(); i++)
         {
         out.println("<OPTION>"+v_opciones.elementAt(i).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Unidad de Proceso:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=v_unipro>");
      for (int i=0; i<v_opciones1.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones1.elementAt(i).toString()+"'>"+v_opciones1.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");      
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      //Hasta aca van los campos


      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CIERREUNIDAD.TBCS_CierreUnidad Information";
  }
}

