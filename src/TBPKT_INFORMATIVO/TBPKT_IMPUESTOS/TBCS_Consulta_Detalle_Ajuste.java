package TBPKT_INFORMATIVO.TBPKT_IMPUESTOS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTMLII;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_Consulta_Detalle_Ajuste extends HttpServlet implements SingleThreadModel{
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
    Vector v_resultadoconsulta, v_resultadoconsulta1;

    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


    //Toma el v_consecutivo
    String v_codigo = "";
    String v_Nocontrato = "";
    String v_consecutivo = "";
    String v_Dato = "";
    String v_linea = "";
    String v_Fecha_Efe_Aju = "";
    String v_Des_Cli = "";
    int v_sini = 0;
    int v_sfin = 0;



    try{
       v_codigo = request.getParameter("v_codigo");
       v_Nocontrato = request.getParameter("v_Nocontrato");
       v_Dato = request.getParameter("v_consecutivo");
       }
    catch (Exception e) { e.printStackTrace(); }

      v_sini = v_Dato.indexOf("*");
      v_consecutivo = v_Dato.substring(0, v_sini);
      v_sfin = v_Dato.indexOf("*", v_sini+1);
      v_linea = v_Dato.substring(v_sini+1, v_sfin);
      v_sini = v_Dato.indexOf("*", v_sfin+1);
      v_Fecha_Efe_Aju = v_Dato.substring(v_sfin+1, v_sini);
      v_Des_Cli = v_Dato.substring(v_Dato.lastIndexOf("*")+1);


      v_dconsulta = "SELECT   APA_CAPITAL\n"+
"         ,APA_RENDIMIENTOS\n"+
"         ,APA_CUENTA_CONTINGENTE\n"+
"         ,APO_CONSECUTIVO         -- Transaccion TAX\n"+
"         ,APO_TRANSACCION         -- Transaccion Multifund\n"+
"FROM      tbaportes_ajustes\n"+
"         ,tbaportes\n"+
"         ,tbajustes\n"+
"WHERE     APA_APO_CON_PRO_CODIGO = APO_CON_PRO_CODIGO\n"+
"AND       APA_APO_CON_NUMERO = APO_CON_NUMERO\n"+
"AND       APA_APO_CONSECUTIVO = APO_CONSECUTIVO\n"+
"AND       APA_APO_CON_PRO_CODIGO = AJU_CON_PRO_CODIGO\n"+
"AND       APA_APO_CON_NUMERO = AJU_CON_NUMERO\n"+
"AND       APA_AJU_CONSECUTIVO = AJU_CONSECUTIVO\n"+
"AND       APA_AJU_LINEA = AJU_LINEA\n"+
"AND       AJU_CON_NUMERO = '"+v_Nocontrato+"'\n"+
"AND       AJU_CON_PRO_CODIGO = '"+v_codigo+"'\n"+
"AND       AJU_CONSECUTIVO = "+v_consecutivo+"\n"+
"AND       AJU_LINEA = "+v_linea;
      v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);
      
      TBPL_Publicar(v_resultadoconsulta, v_codigo, v_Nocontrato, v_Fecha_Efe_Aju, v_Des_Cli);
      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }


      private void TBPL_Publicar(Vector v_descripcion, String v_codigo, String v_Nocontrato, String v_Fecha_Efe_Aju, String v_Des_Cli)
      {
      v_descripcion.trimToSize();
      double capital = 0;
      double rendimientos = 0;
      double ctacontingente = 0;

      /*Cambio para manejo de referencia unica 2009/04/01 MOS */
      String v_contrato_unif = "";
     // v_Consulta.TBPL_RealizarConexion();
      v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_codigo,v_Nocontrato);
      //v_Consulta.TBPL_shutDown();
      
      out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Consulta de impuetos","Consulta de impuestos para el contrato&nbsp;"+v_contrato_unif+"", "TBPKT_INFORMATIVO.TBPKT_IMPUESTOS.TBCS_Consulta_Detalle_Ajuste", "", true));
      out.println("<BR>&nbsp;&nbsp;<BR>");      
      out.println("<TABLE bgColor=white cols=2 width=\"100%\"><TBODY>");
      out.println("<TR bgColor=white><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha efectiva</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_Fecha_Efe_Aju+"</FONT></TD></TR>");
      out.println("<TR bgColor=white><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Decisión del cliente</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_Des_Cli+"</FONT></TD></TR>");
      out.println("</TBODY></TABLE>");

      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        {
        for (int i = 0; i<v_descripcion.size(); i++)
           if (v_descripcion.elementAt(i).toString().equals("null"))
             v_descripcion.setElementAt(" ", i);

        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0><TBODY>");
        out.println("<TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Detalle de ajustes</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR class=\"td11\" borderColor=silver align=center><TD width=\"22%\"><FONT color=white face=verdana size=1><B>No transacción Tax Benefits</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>No transacción MULTIFUND</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Capital ajustado</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Rendimiento ajustado</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Cta contingente</B></FONT></TD></TR>");
        for (int i=0; i<v_descripcion.size(); i+=5)
           {
           try{capital = Double.parseDouble(v_descripcion.elementAt(i+0).toString());}
           catch (Exception e){capital = 0;}
           try{rendimientos = Double.parseDouble(v_descripcion.elementAt(i+1).toString());}
           catch (Exception e){rendimientos = 0;}
           try{ctacontingente = Double.parseDouble(v_descripcion.elementAt(i+2).toString());}
           catch (Exception e){ctacontingente = 0;}
           out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+3).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+4).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(capital)+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(rendimientos)+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(ctacontingente)+"</FONT></TD></TR>");
           }
        out.println("</TBODY></TABLE>");
        }
      else
        {
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0><TBODY>");
        out.println("<TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Detalle de ajustes</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR class=\"td11\" borderColor=silver align=center><TD width=\"22%\"><FONT color=white face=verdana size=1><B>No transacción Tax Benefits</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>No transacción MULTIFUND</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Capital ajustado</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Rendimiento ajustado</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Cta contingente</B></FONT></TD></TR>");
        out.println("</TBODY></TABLE>");
        }
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");      
      out.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
      out.close();
      }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_IMPUESTOS.TBCS_Consulta_Detalle_Ajuste Information";
  }
}

