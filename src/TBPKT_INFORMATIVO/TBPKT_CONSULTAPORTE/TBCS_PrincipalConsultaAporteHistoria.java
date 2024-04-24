package TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_PrincipalConsultaAporteHistoria extends HttpServlet {
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
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

    TBPL_Publicar();
  }

  private void TBPL_Publicar()
  {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de historias pendientes","Consulta de historia de aportes", "TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE.TBCS_ConsultaHistoria", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=0 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"30%\"><FONT color=black face=verdana size=1>&nbsp;</font></TD><TD width=\"30%\"><FONT color=black face=verdana size=1><B>(YYYY-MM-DD)</B></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"30%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Fecha inicial del reporte</B></font></TD><TD width=\"30%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=fechaini MAXLENGTH=10 SIZE=10 ONCHANGE=\"fechavalida(this)\"></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"30%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Fecha final del reporte</B></font></TD><TD width=\"30%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=fechafin MAXLENGTH=10 SIZE=10 ONCHANGE=\"fechavalida(this)\"></font></TD></TR>");
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
    return "TBPKT_CONSULTARETIROS.TBCS_PrincipalConsultaAporteHistoria Information";
  }
}