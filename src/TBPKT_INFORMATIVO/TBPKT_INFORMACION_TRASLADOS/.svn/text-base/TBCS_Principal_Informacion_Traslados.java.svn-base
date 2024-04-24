package TBPKT_INFORMATIVO.TBPKT_INFORMACION_TRASLADOS;

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

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

/**
 * Servlet para procesar la llamada inicial al M&oacute;dulo de Informaci&oacute;n de Traslados
 * <P>
 * @author ALFA GL Ltda
 */
public class TBCS_Principal_Informacion_Traslados extends HttpServlet {

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
    String[] afp, producto;

    try {
      Iterator afps= getDatosAFP().iterator();
      Iterator productos= getDatosProductos().iterator();

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

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Informaci&oacute;n de Traslados","Generaci&oacute;n de Informaci&oacute;n de Traslados", "TBPKT_INFORMATIVO.TBPKT_INFORMACION_TRASLADOS.TBCS_Informacion_Traslados", "", true, "moduloInformativo.js", "return validaGIT(this);"));
      out.println("<TABLE BGCOLOR=\"WHITE\" BORDER=0 CELLPADDING=2 CELLSPACING=0 COLS=2 RULES=\"ALL\" WIDTH=100%>");
      out.println("<TR><TD COLSPAN=2><INPUT TYPE=\"HIDDEN\" NAME=\"tipo\" VALUE=1>&nbsp;</TD></TR>");

      out.print("<TR><TD WIDTH=25%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.println("<B><SUP>*</SUP>AFP de destino :</B></FONT></TD>");
      out.println("<TD WIDTH=75%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.println("<SELECT NAME=\"afp\">");

      while(afps.hasNext()) {
        afp= (String[])afps.next();
        out.println("<OPTION VALUE=\""+afp[0]+"_"+afp[2]+"\">"+afp[1]);
      }

      out.println("</SELECT>");
      out.println("</FONT></TD></TR>");

      out.print("<TR><TD WIDTH=25%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.println("<B><SUP>*</SUP>Fecha inicial :</B></FONT></TD>");
      out.print("<TD WIDTH=75%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.print("<INPUT NAME=fechaInicial TYPE=\"TEXT\" MAXLENGTH=10 SIZE=15>&nbsp;&nbsp;(AAAA-MM-DD)");
      out.println("</FONT></TD></TR>");

      out.print("<TR><TD WIDTH=25%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.println("<B><SUP>*</SUP>Fecha final :</B></FONT></TD>");
      out.print("<TD WIDTH=75%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.print("<INPUT NAME=fechaFinal TYPE=\"TEXT\" MAXLENGTH=10 SIZE=15>&nbsp;&nbsp;(AAAA-MM-DD)");
      out.println("</FONT></TD></TR>");

      out.println("<TR><TD COLSPAN=2>&nbsp;</TD></TR>");

      out.println("<TR><TD WIDTH=25%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>Producto :</FONT></TD><TD WIDTH=75%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE='1'>");
      out.println("<SELECT NAME=\"producto\">");
      out.println("<OPTION SELECTED VALUE=\"TODOS\">");

      while(productos.hasNext()) {
        producto= (String[])productos.next();
        out.println("<OPTION VALUE=\""+producto[0]+"\">"+producto[0]);
      }

      out.println("</SELECT>");
      out.println("</FONT></TD></TR>");

      out.print("<TR><TD WIDTH=25%><FONT FACE=\"Verdana, sans-serif\" SIZE=1>Contrato :</FONT></TD>");
      out.print("<TD WIDTH=75%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.println("<INPUT NAME=\"contrato\" TYPE=\"TEXT\" MAXLENGTH=50 SIZE=30></FONT></TD></TR>");

      out.print("<TR><TD WIDTH=25%><FONT FACE=\"Verdana, sans-serif\" SIZE=1>C&eacute;dula :</FONT></TD>");
      out.print("<TD WIDTH=75%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.println("<INPUT NAME=\"cedula\" TYPE=\"TEXT\" MAXLENGTH=50 SIZE=30></FONT></TD></TR>");

      out.print("<TR><TD WIDTH=25%><FONT FACE=\"Verdana, sans-serif\" SIZE=1>NIT Compa&ntilde;&iacute;a :</FONT></TD>");
      out.print("<TD WIDTH=75%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.println("<INPUT NAME=\"nit\" TYPE=\"TEXT\" MAXLENGTH=50 SIZE=30></FONT></TD></TR>");
      out.println("<TR><TD><INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+ nuevaCadena +"'></TD></TR>");
      out.println("<TR><TD COLSPAN=2>&nbsp;</TD></TR>");

      out.println("<TR><TD COLSPAN=2><CENTER><INPUT TYPE=\"SUBMIT\" VALUE=\"Aceptar\"><INPUT TYPE=\"BUTTON\" VALUE=\"Regresar\" OnClick='history.go(-1);'></CENTER></TD></TR>");

      out.println("</TABLE>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
    }
    catch(SQLException sqle) {
    }
  }

  /**
   * Regresa el C&oacute;digo y la Descripci&oacute;n de todos los Productos registrados en el sistema
   * @return java.util.LinkedList
   */
  private LinkedList getDatosProductos() throws SQLException {
    LinkedList retList= new LinkedList();

    Connection con= null;
    PreparedStatement ps= null;
    ResultSet rs= null;

    String query= "SELECT PRO_CODIGO "+
                        ",PRO_DESCRIPCION "+
                        ",PRO_NUMERO_IDENTIFICACION "+
                  "FROM   TBPRODUCTOS";

    try {
      con   =   DataSourceWrapper.getInstance().getConnection();
      ps= con.prepareStatement(query);

      rs= ps.executeQuery();
      while(rs.next())
        retList.add(new String[]{rs.getString(1),rs.getString(2),rs.getString(3)});
    }
    finally{
      try {
          DataSourceWrapper.closeStatement(rs);
          DataSourceWrapper.closeStatement(ps);
          DataSourceWrapper.closeConnection(con);        
      }
      catch(SQLException sqle) {
      }
    }
    return retList;
  }

  /**
   * Regresa el C&oacute;digo, la Descripci&oacute;n y el NIT de todas las AFP registradas en el sistema
   * @return java.util.LinkedList
   */
  private LinkedList getDatosAFP() throws SQLException {
    LinkedList retList= new LinkedList();

    Connection con= null;
    PreparedStatement ps= null;
    ResultSet rs= null;

    String query= "SELECT AFP_CODIGO "+
                  "      ,AFP_DESCRIPCION "+
                  "      ,SUBSTR(LPAD(AFP_NIT,13,'0'),-11) "+
                  "FROM   TBADM_FONDOS_PENSIONES";

    try {
      con   =   DataSourceWrapper.getInstance().getConnection();
      ps= con.prepareStatement(query);

      rs= ps.executeQuery();
      while(rs.next())
        retList.add(new String[]{rs.getString(1),rs.getString(2),rs.getString(3)});

    }
      finally{
        try {
            DataSourceWrapper.closeStatement(rs);
            DataSourceWrapper.closeStatement(ps);
            DataSourceWrapper.closeConnection(con);        
        }
        catch(SQLException sqle) {
        }
      }
    return retList;
  }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_INFORMATIVO.TBPKT_INFORMACION_TRASLADOS.TBCS_Principal_Informacion_Traslados Information";
  }
}

