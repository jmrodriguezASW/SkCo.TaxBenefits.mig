package TBPKT_INFORMATIVO.TBPKT_CONSULTARETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_PrincipalConsultaTrasladoInt extends HttpServlet {
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
    String Consulta;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadoConsulta, resultadoConsulta1;

    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


      Consulta = "SELECT distinct(resultado) FROM tbinterface_aftmcpp where resultado is not null";
      v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta, "resultado");


    v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    TBPL_Publicar(v_resultadoConsulta);
  }

      private void TBPL_Publicar(Vector v_opciones)
      {
      v_opciones.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de retiros para traslados","Consulta de retiros para traslados", "TBPKT_INFORMATIVO.TBPKT_CONSULTARETIROS.TBCS_ConsultaTraslado", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=0 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"30%\"><FONT color=black face=verdana size=1>&nbsp;</font></TD><TD width=\"30%\"><FONT color=black face=verdana size=1><B>(YYYY-MM-DD)</B></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"30%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Fecha inicial del reporte</B></font></TD><TD width=\"30%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=fechaini MAXLENGTH=10 SIZE=10 ONCHANGE=\"fechavalida(this)\"></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"30%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Fecha final del reporte</B></font></TD><TD width=\"30%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=fechafin MAXLENGTH=10 SIZE=10 ONCHANGE=\"fechavalida(this)\"></font></TD></TR>");
      out.println("<TR><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Resultado del retiro</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=v_result>");
      out.println("<OPTION>Todos");
      for (int i=0; i<v_opciones.size(); i++)
         {
         out.println("<OPTION>"+v_opciones.elementAt(i).toString());
         }
      out.println("</SELECT></font></TD></TR>");
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
    return "TBPKT_CONSULTARETIROS.TBCS_PrincipalConsultaTrasladoInt Information";
  }
}

