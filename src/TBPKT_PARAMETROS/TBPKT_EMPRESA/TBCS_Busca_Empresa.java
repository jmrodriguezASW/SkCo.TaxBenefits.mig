package TBPKT_PARAMETROS.TBPKT_EMPRESA;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_Busca_Empresa extends HttpServlet implements SingleThreadModel{
private PrintWriter out;//Clase que se encarga de imprimir el código HTML
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

    PublicarConsultar();//TBPL_Publicar la pagina
  }


      private void PublicarConsultar()//Publicación de la pagina HTML
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas","Administración de empresas", "TBPKT_PARAMETROS.TBPKT_EMPRESA.TBCS_Empresa", "", true));
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
      //Aqui van los campo que se quieren mostrar
      out.println("<BLOCKQUOTE>");
      out.println("<BR>&nbsp;&nbsp<BR>");
      out.println("<B>Digite las iniciales de la empresa:</B>&nbsp;&nbsp<INPUT TYPE=text NAME=empresa SIZE=15>");
      out.println("<BR>&nbsp;&nbsp<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Buscar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR>&nbsp;&nbsp<BR>");
      out.println("Espacio en blanco para mostrar todas las empresas");
      out.println("</BLOCKQUOTE>");
      out.println("</font>");
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
    return "TBPKT_EMPRESA.TBCS_Busca_Empresa Information";
  }
}

