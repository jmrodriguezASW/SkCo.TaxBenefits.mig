package TBPKT_INFORMATIVO.TBPKT_REPORTESCONTROL;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_Principal_Reportes_Control extends HttpServlet implements SingleThreadModel{
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
    v_Consulta = new TBCL_Consulta();
    String v_dconsulta;
    //Vector donde se guardara el resultado de la v_dconsulta
    Vector v_resultadoconsulta, v_resultadoconsulta1, v_resultadoconsulta2;

    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


       v_dconsulta = "SELECT ref_codigo,\n"+
                 "         ref_descripcion\n"+
                 " FROM    tbreferencias\n"+
                 " WHERE   ref_codigo > 'UUP000'\n"+
                 " AND     ref_codigo LIKE 'UUP%'";
      v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);

      v_dconsulta = "SELECT ref_codigo,\n"+
                 "         ref_descripcion\n"+
                 " FROM    tbreferencias\n"+
                 " WHERE   ref_codigo > 'STR000'\n"+
                 " AND     ref_codigo LIKE 'STR%'";
      v_resultadoconsulta1 = v_Consulta.TBFL_Consulta(v_dconsulta);      

      TBPL_Publicar(v_resultadoconsulta, v_resultadoconsulta1);
      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }

      private void TBPL_Publicar(Vector v_opciones, Vector v_opciones1)
      {
      v_opciones.trimToSize();
      v_opciones1.trimToSize();

        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Reportes de control","Reportes de control", "TBPKT_INFORMATIVO.TBPKT_REPORTESCONTROL.TBCS_Reportes_Control", "", true, "moduloparametro.js", "return checkrequired(this)"));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE bgColor=white border=0 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"30%\"><FONT color=black face=verdana size=1>&nbsp;</font></TD><TD width=\"30%\"><FONT color=black face=verdana size=1><B>(YYYY-MM-DD)</B></font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"30%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Fecha inicial del reporte</B></font></TD><TD width=\"30%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=obligatoriofechaini MAXLENGTH=10 SIZE=10 ONCHANGE=\"fechavalida(this)\"></font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"30%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Fecha final del reporte</B></font></TD><TD width=\"30%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=obligatoriofechafin MAXLENGTH=10 SIZE=10 ONCHANGE=\"fechavalida(this)\"></font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"30%\"><FONT color=black face=verdana size=1><B>Seleccionar Unidad de proceso</B><BR>(Aplica solo para retiros)</font></TD><TD width=\"30%\"><FONT color=black face=verdana size=1><SELECT NAME=unipro>");
        for (int i=0; i<v_opciones.size(); i+=2)
           {
           out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
           }
        out.println("</SELECT></font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"30%\"><FONT color=black face=verdana size=1><B>Usuario</B></font></TD><TD width=\"30%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=vusuario MAXLENGTH=15 SIZE=15 ></font></TD></TR>");            
        
        out.println("<TR bgColor=white borderColor=silver><TD width=\"30%\"><FONT color=black face=verdana size=1><B>Producto</B></font></TD><TD width=\"30%\"><FONT color=black face=verdana size=1><SELECT NAME=producto>");
            out.println("<OPTION VALUE='VOLUNTARIOS'>VOLUNTARIOS");  
            out.println("<OPTION VALUE='FPOB'>FPOB");  
        out.println("</SELECT></font></TD></TR>");        
        
        out.println("</TABLE>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
        out.println("<BLOCKQUOTE>");
        out.println("<INPUT TYPE=radio NAME=opcion VALUE='siproceso' CHECKED>Reporte para retiros PROCESADOS<BR>");
        out.println("<INPUT TYPE=radio NAME=opcion VALUE='noproceso'>Reporte para transacciones NO PROCESADAS de&nbsp;<SELECT NAME=tran><OPTION VALUE='STR099'>TODAS");
        for (int i=0; i<v_opciones1.size(); i+=2)
           {
           out.println("<OPTION VALUE='"+v_opciones1.elementAt(i).toString()+"'>"+v_opciones1.elementAt(i+1).toString());
           }
        out.println("</SELECT>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=submit VALUE=Consultar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");
        out.println("</BLOCKQUOTE>");
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
    return "TBPKT_REPORTESCONTROL.TBCS_Principal_Reportes_Control Information";
  }
}

