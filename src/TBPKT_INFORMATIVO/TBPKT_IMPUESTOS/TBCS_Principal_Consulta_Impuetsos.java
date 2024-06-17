package TBPKT_INFORMATIVO.TBPKT_IMPUESTOS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_Principal_Consulta_Impuetsos extends HttpServlet{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
private String v_nuevaCadena ="";

  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

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
    String v_dconsulta;
    //Vector donde se guardara el resultado de la v_dconsulta
    Vector v_resultadoconsulta, v_resultadoconsulta1;

    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


      v_dconsulta = "SELECT pro_codigo FROM tbproductos ORDER BY pro_codigo";
      v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta, "pro_codigo");


    v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    TBPL_Publicar(v_resultadoconsulta);
  }

        private void TBPL_Publicar(Vector v_opciones)
      {
      v_opciones.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de impuestos","Consulta de impuestos", "TBPKT_INFORMATIVO.TBPKT_IMPUESTOS.TBCS_Consulta_Impuestos", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=0 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=v_codpro>");
      for (int i=0; i<v_opciones.size(); i++)
         {
         out.println("<OPTION>"+v_opciones.elementAt(i).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      /*Cambio para manejo de referencia unica 2009/04/01 MOS */
      out.println("<TR><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Número del contrato:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=obligatoriocontrato SIZE=12 MAXLENGTH=12></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");      
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }
  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_IMPUESTOS.TBCS_Principal_Consulta_Impuetsos Information";
  }
}

