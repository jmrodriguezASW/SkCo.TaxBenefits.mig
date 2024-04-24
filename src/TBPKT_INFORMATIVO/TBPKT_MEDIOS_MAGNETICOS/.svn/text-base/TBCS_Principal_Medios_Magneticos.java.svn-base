package TBPKT_INFORMATIVO.TBPKT_MEDIOS_MAGNETICOS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.math.*;
import oracle.jdbc.driver.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

/**
 * Servlet para procesar la llamada inicial al M&oacute;dulo de Informaci&oacute;n de Traslados
 * <P>
 * @author ALFA GL Ltda
 */
public class TBCS_Principal_Medios_Magneticos  extends HttpServlet {

  /**
   * Initialize global variables
   */
  String cadena = "";
  String nuevaCadena = "";
     
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   * Process the HTTP Post request
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
    //response.setHeader("Pragma", "No-Cache");
    //response.setDateHeader("Expires", 0);
    String parametros[] = new String[8];
    String  cadena = request.getParameter("cadena");
    nuevaCadena = cadena;
    String ip_tax = request.getRemoteAddr();
    TBCL_Seguridad Seguridad = new TBCL_Seguridad();
    PrintWriter out = new PrintWriter (response.getOutputStream());
    parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);

    String stropcion = "";
		stropcion= request.getParameter("opcion");
    if(stropcion == null) stropcion= "1";
    int opcion= Integer.parseInt(stropcion);

    switch(opcion)
    {
    	case 2:
    		out.println(TBFL_Opcion2());
    	break;
    	case 3:
    		out.println(TBFL_Opcion3());
    	break;
      default:
    		out.println(TBFL_Opcion1());
    	break;

    }
    out.close();
  }

  private String TBFL_Opcion1(){
    String html = "";
    html += STBCL_GenerarBaseHTML.TBFL_CABEZA("Medios Magneticos","Generaci&oacute;n de Reportes de Medios Magnéticos para la DIAN", "TBPKT_INFORMATIVO.TBPKT_MEDIOS_MAGNETICOS.TBCS_Principal_Medios_Magneticos", "", true, "", "");
    html += "<TABLE BGCOLOR=\"WHITE\" BORDER=0 CELLPADDING=2 CELLSPACING=0 COLS=2 RULES=\"ALL\" WIDTH=100%>";
    html += "<TR borderColor=silver><TD COLSPAN=2>&nbsp;</TD></TR>";
    html += "<TR borderColor=silver><TD COLSPAN=2>&nbsp;</TD></TR>";
    html += "<TR borderColor=silver><TD WIDTH=15%>&nbsp;</TD><TD WIDTH=85%><FONT FACE=\"Verdana, sans-serif\" SIZE=1>";
    html += "<INPUT CHECKED TYPE='RADIO' NAME='opcion' VALUE='2'>Generar archivo de <B>D&eacute;bitos o Cr&eacute;ditos</B><BR>";
    html += "<INPUT TYPE='RADIO' NAME='opcion' VALUE='3'>Generar archivo de <B>Contratos Abiertos, Saldados y Cancelados</B>";
    html += "</FONT></TD></TR>";
    html += "<TR><TD><INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+ nuevaCadena +"'></TD></TR>";
    html += "<TR><TD COLSPAN=2>&nbsp;</TD></TR>";
    html += "<TR><TD COLSPAN=2>&nbsp;</TD></TR>";
    html += "<TR><TD COLSPAN=2><CENTER><INPUT TYPE=\"SUBMIT\" VALUE=\"Aceptar\"><INPUT TYPE=\"BUTTON\" VALUE=\"Regresar\" OnClick='history.go(-1);'></CENTER></TD></TR>";
    html += "</TABLE>";
    html += STBCL_GenerarBaseHTML.TBFL_PIE;
    return html;
  }

  private String TBFL_Opcion2(){
    String html = "";
    html += STBCL_GenerarBaseHTML.TBFL_CABEZA("Medios Magneticos","Generaci&oacute;n de Reportes de Medios Magnéticos para la DIAN", "TBPKT_INFORMATIVO.TBPKT_MEDIOS_MAGNETICOS.TBCS_Medios_Magneticos", "", true, "moduloInformativo.js", "return validaGRMM(this);");

    html += "<TABLE BGCOLOR=\"WHITE\" BORDER=0 CELLPADDING=2 CELLSPACING=0 COLS=2 RULES=\"ALL\" WIDTH=100%>";
    html += "<TR borderColor=silver><TD COLSPAN=2>&nbsp;</TD></TR>";
    html += "<TR borderColor=silver><TD COLSPAN=2>&nbsp;</TD></TR>";

    html += "<TR>";
    html += "<TD width='33%'><FONT FACE='Verdana, sans-serif' SIZE=1><B><SUP>*</SUP>Informaci&oacute;n del medio :</B></FONT></TD>";
    html += "<TD width='67%'><FONT FACE='Verdana, sans-serif' SIZE=1><INPUT NAME='medio' TYPE='TEXT' MAXLENGTH=4 SIZE=20> (R&oacute;tulo del archivo)</FONT></TD>";
    html += "</TR>";

    html += "<TR>";
    html += "<TD width='33%'><FONT FACE='Verdana, sans-serif' SIZE=1><B><SUP>*</SUP>A&ntilde;o gravable :</B></FONT></TD>";
    html += "<TD width='67%'><FONT FACE='Verdana, sans-serif' SIZE=1><INPUT NAME='anio_gravable' TYPE='TEXT' MAXLENGTH=4 SIZE=20> (AAAA)</FONT></TD>";
    html += "</TR>";

    html += "<TR>";
    html += "<TD width='33%'><FONT FACE='Verdana, sans-serif' SIZE=1><B><SUP>*</SUP>Monto de referencia :</B></FONT></TD>";
    html += "<TD width='67%'><FONT FACE='Verdana, sans-serif' SIZE=1><INPUT NAME='monto_referencia' TYPE='TEXT' MAXLENGTH=22 SIZE=20></FONT></TD>";
    html += "</TR>";

    html += "<TR><TD width='33%'><FONT FACE='Verdana, sans-serif' size=1><B><SUP>*</SUP>Tipo de transacci&oacute;n :</B></font></TD>";
    html += "<TD width='67%'><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>";
    html += "<SELECT NAME='tipo'>";
    html += "<OPTION SELECTED VALUE=1>D&eacute;bito";
    html += "<OPTION VALUE=2>Cr&eacute;dito";
    html += "</SELECT>";
    html += "</FONT></TD></TR>";

    html += "<TR>";
    html += "<TD WIDTH='33%'><FONT FACE='Verdana, sans-serif' SIZE=1><B><SUP>*</SUP>Nombre de archivo :</B></FONT></TD>";
    html += "<TD WIDTH='67%'><FONT FACE='Verdana, sans-serif' SIZE=1><INPUT NAME='archivo' TYPE='TEXT' MAXLENGTH=20 SIZE=20> (Por ej. dian2000)</FONT></TD>";
    html += "</TR>";

    html += "<TR><TD><INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+ nuevaCadena +"'></TD></TR>";
    html += "<TR><TD COLSPAN=2>&nbsp;</TD></TR>";
    html += "<TR><TD COLSPAN=2>&nbsp;</TD></TR>";
    html += "<TR><TD COLSPAN=2><CENTER><INPUT TYPE=\"SUBMIT\" VALUE=\"Aceptar\"><INPUT TYPE=\"BUTTON\" VALUE=\"Regresar\" OnClick='history.go(-1);'></CENTER></TD></TR>";
    html += "</TABLE>";
    html += STBCL_GenerarBaseHTML.TBFL_PIE;

    return html;
  }

  private String TBFL_Opcion3(){
    String html = "";
    html += STBCL_GenerarBaseHTML.TBFL_CABEZA("Medios Magneticos","Generaci&oacute;n de Reportes de Medios Magnéticos para la DIAN", "TBPKT_INFORMATIVO.TBPKT_MEDIOS_MAGNETICOS.TBCS_Medios_Magneticos", "", true, "moduloInformativo.js", "return validaGRMM(this);");

    html += "<TABLE BGCOLOR=\"WHITE\" BORDER=0 CELLPADDING=2 CELLSPACING=0 COLS=2 RULES=\"ALL\" WIDTH=100%>";
    html += "<TR borderColor=silver><TD COLSPAN=2>&nbsp;</TD></TR>";
    html += "<TR borderColor=silver><TD COLSPAN=2>&nbsp;</TD></TR>";

    html += "<TR><TD colspan=2><INPUT TYPE='HIDDEN' NAME='tipo' VALUE=3>&nbsp;</TD></TR>";

    html += "<TR>";
    html += "<TD width='33%'><FONT FACE='Verdana, sans-serif' SIZE=1><B><SUP>*</SUP>Informaci&oacute;n del medio :</B></FONT></TD>";
    html += "<TD width='67%'><FONT FACE='Verdana, sans-serif' SIZE=1><INPUT NAME='medio' TYPE='TEXT' MAXLENGTH=4 SIZE=20> (R&oacute;tulo del archivo)</FONT></TD>";
    html += "</TR>";

    html += "<TR>";
    html += "<TD width='33%'><FONT FACE='Verdana, sans-serif' SIZE=1><B><SUP>*</SUP>A&ntilde;o gravable :</B></FONT></TD>";
    html += "<TD width='67%'><FONT FACE='Verdana, sans-serif' SIZE=1><INPUT NAME='anio_gravable' TYPE='TEXT' MAXLENGTH=4 SIZE=20> (AAAA)</FONT></TD>";
    html += "</TR>";

    html += "<TR>";
    html += "<TD width='33%'><FONT FACE='Verdana, sans-serif' SIZE=1><B><SUP>*</SUP>Monto de referencia :</B></FONT></TD>";
    html += "<TD width='67%'><FONT FACE='Verdana, sans-serif' SIZE=1><INPUT NAME='monto_referencia' TYPE='TEXT' MAXLENGTH=22 SIZE=20></FONT></TD>";
    html += "</TR>";

    html += "<TR>";
    html += "<TD WIDTH='33%'><FONT FACE='Verdana, sans-serif' SIZE=1><B><SUP>*</SUP>Nombre de archivo :</B></FONT></TD>";
    html += "<TD WIDTH='67%'><FONT FACE='Verdana, sans-serif' SIZE=1><INPUT NAME='archivo' TYPE='TEXT' MAXLENGTH=20 SIZE=20> (Por ej. dian2000)</FONT></TD>";
    html += "</TR>";

    html += "<TR><TD><INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+ nuevaCadena +"'></TD></TR>";
    html += "<TR><TD COLSPAN=2>&nbsp;</TD></TR>";
    html += "<TR><TD COLSPAN=2>&nbsp;</TD></TR>";
    html += "<TR><TD COLSPAN=2><CENTER><INPUT TYPE=\"SUBMIT\" VALUE=\"Aceptar\"><INPUT TYPE=\"BUTTON\" VALUE=\"Regresar\" OnClick='history.go(-1);'></CENTER></TD></TR>";
    html += "</TABLE>";
    html += STBCL_GenerarBaseHTML.TBFL_PIE;
    
    return html;
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_INFORMATIVO.TBPKT_MEDIOS_MAGNETICOS.TBCS_Principal_Medios_Magneticos Information";
  }
}

