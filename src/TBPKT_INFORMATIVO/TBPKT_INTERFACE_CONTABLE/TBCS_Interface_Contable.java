package TBPKT_INFORMATIVO.TBPKT_INTERFACE_CONTABLE;

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
 * Servlet para procesar las llamadas al M&oacute;dulo de Interface Contable
 * <P>
 * @author ALFA GL Ltda
 */
public class TBCS_Interface_Contable extends HttpServlet {
 String nuevaCadena = "";
  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   * Process the HTTP Get request
   */
 // public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   // doPost(request, response);
 // }

  /**
   * Process the HTTP Post request
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

 /**Tomar session*/
  PrintWriter out = new PrintWriter (response.getOutputStream());
   
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;/**Instancia de la clase TBC_Seguridad*/

   HttpSession session = request.getSession(false);
   if (session == null) session = request.getSession(true);

   response.setContentType("text/html");
   /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
   //response.setHeader("Pragma", "No-Cache");
   //response.setDateHeader("Expires", 0);
   /**Declaracion de Variables*/
   /**Tipo String*/
   String usuario, producto, fecha_contabilizacion;
   String parametros[] = new String[8];
   String cadena = request.getParameter("cadena");
   nuevaCadena = cadena;
   String ip_tax = request.getRemoteAddr();
   /**Validar seguridad*/
   parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);

   String strOpcion= request.getParameter("opcion");

   if(strOpcion == null) strOpcion= "1";
   int opcion= Integer.parseInt(strOpcion);



   switch(opcion) {
      case 1:

        out.println(opcion1());
    	break;
      case 2:

        producto= request.getParameter("producto");
        if(producto == null || producto.equals("TODOS")) producto= "";
        fecha_contabilizacion= request.getParameter("fecha_contabilizacion");
        if(fecha_contabilizacion == null) fecha_contabilizacion= "";

        out.println(opcion2(producto, fecha_contabilizacion));
    	break;
      case 10:

        fecha_contabilizacion= request.getParameter("fecha_contabilizacion");
        if(fecha_contabilizacion == null) fecha_contabilizacion= "";
        out.println(opcion10(fecha_contabilizacion));
    	break;

      default:
        break;
    }

    out.close();
  }

  private String opcion1() {
    String[] datosProducto;
    StringBuffer html= new StringBuffer();
    try {
      Iterator productos= getDatosProductos().iterator();

      html.append(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta De Interface Contable","Consulta De Interface Contable", "TBPKT_INFORMATIVO.TBPKT_INTERFACE_CONTABLE.TBCS_Interface_Contable", "", true, "moduloInformativo.js", "return validaIC(this);"));

      html.append("<TABLE BGCOLOR=white BORDER=0 CELLPADDING=2 CELLSPACING=0 COLS=2 RULES=all WIDTH=100%>\n");
      html.append("<TR><TD COLSPAN=2>&nbsp;</TD></TR>\n");

      html.append("<TR><TD WIDTH=35%><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1><B><SUP>*</SUP>Producto :</B></FONT></TD><TD WIDTH=65%><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>\n");
      html.append("<SELECT NAME=producto>\n");
      html.append("<OPTION SELECTED VALUE=TODOS>\n");

      while(productos.hasNext()) {
        datosProducto= (String[])productos.next();
        html.append("<OPTION VALUE="+datosProducto[0]+">"+datosProducto[0]+ "\n");
      }

      html.append("</SELECT>\n");
      html.append("</FONT></TD></TR>\n");

      html.append("<TR><TD WIDTH=35%><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>\n");
      html.append("<B><SUP>*</SUP>Fecha de Contabilizaci&oacute;n :</B></FONT></TD>\n");
      html.append("<TD WIDTH=65%><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>\n");
      html.append("<INPUT NAME=fecha_contabilizacion TYPE=TEXT MAXLENGTH=10 SIZE=15>&nbsp;&nbsp;(AAAA-MM-DD)\n");
      html.append("</FONT></TD></TR>\n");

      html.append("<TR><TD WIDTH=35%><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>&nbsp;</FONT></TD>\n");
      html.append("<TD WIDTH=65%><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>\n");
      html.append("<SMALL>* Esta NO es la fecha de los ajustes, <BR>\n");
      html.append("<SMALL>&nbsp;&nbsp;sino la fecha en que se realiz&oacute; la contabilizaci&oacute;n</SMALL>\n");
      html.append("</FONT></TD></TR>\n");

      html.append("<TR><TD COLSPAN=2><INPUT TYPE=HIDDEN NAME=opcion VALUE=2>&nbsp;</TD></TR>\n");

      html.append("<TR><TD colspan=2><INPUT TYPE='HIDDEN' NAME='cadena' VALUE='"+nuevaCadena+"'>&nbsp;</TD></TR>");
      html.append("<TR><TD COLSPAN=2><CENTER><INPUT TYPE=SUBMIT VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar OnClick='history.go(-1);'></CENTER></TD></TR>\n");

      html.append("</TABLE>\n");

      html.append(STBCL_GenerarBaseHTML.TBFL_PIE);

    }
    catch(SQLException sqle) {
    }
    return html.toString();
  }


  private String opcion2(String producto, String fecha_contabilizacion) {
    String[] interfaceContable;
    String bgColor;
    boolean addBGColor= false;
    StringBuffer html= new StringBuffer();

    try {
      Iterator datosInterfaceContable= getDatosInterfaceContable(producto, fecha_contabilizacion).iterator();

      html.append(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Consulta De Interface Contable","Resultado de Consulta De Interface Contable", "", "", false));

      if(datosInterfaceContable.hasNext()) {
        html.append("<TABLE BGCOLOR=white BORDER=2 BORDERCOLOR=silver CELLPADDING=2 CELLSPACING=0 COLS=12 RULES=ALL WIDTH=100%>\n");

        html.append("<TR><TD BGCOLOR=whitesmoke NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1><B>Reg</B></FONT></TD>\n");
        html.append("<TD ALIGN=center BGCOLOR=whitesmoke NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1><B>Fecha</B></TD>\n");
        html.append("<TD ALIGN=center BGCOLOR=whitesmoke NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1><B>Usuario</B></FONT></TD>\n");
        html.append("<TD ALIGN=center BGCOLOR=whitesmoke NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1><B>Lote</B></FONT></TD>\n");
        html.append("<TD ALIGN=center BGCOLOR=whitesmoke NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1><B>Compa&ntilde;&iacute;a</B></FONT></TD>\n");
        html.append("<TD ALIGN=center BGCOLOR=whitesmoke NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1><B>U Negocio</B></FONT></TD>\n");
        html.append("<TD ALIGN=center BGCOLOR=whitesmoke NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1><B>Cuenta</B></FONT></TD>\n");
        html.append("<TD ALIGN=center BGCOLOR=whitesmoke NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1><B>Aux</B></FONT></TD>\n");
        html.append("<TD ALIGN=center BGCOLOR=whitesmoke NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1><B>Tipo</B></FONT></TD>\n");
        html.append("<TD ALIGN=center BGCOLOR=whitesmoke NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1><B>Documento</B></FONT></TD>\n");
        html.append("<TD ALIGN=center BGCOLOR=whitesmoke NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1><B>Valor</B></FONT></TD>\n");
        html.append("<TD ALIGN=center BGCOLOR=whitesmoke NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1><B>Descripci&oacute;n</B></FONT></TD></TR>\n");


        while(datosInterfaceContable.hasNext()) {
          interfaceContable= (String[])datosInterfaceContable.next();

          if(addBGColor) bgColor= "BGCOLOR=whitesmoke ";
          else bgColor= "";

          html.append("<TR><TD "+bgColor+"ALIGN=center NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>");
          html.append(interfaceContable[0]);
          html.append("</FONT></TD>\n<TD "+bgColor+"ALIGN=center NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>");
          html.append(interfaceContable[1]);
          html.append("</FONT></TD>\n<TD "+bgColor+"NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>");
          html.append(interfaceContable[2]);
          html.append("</FONT></TD>\n<TD "+bgColor+"ALIGN=center NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>");
          html.append(interfaceContable[3]);
          html.append("</FONT></TD>\n<TD "+bgColor+"ALIGN=right NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>");
          html.append(interfaceContable[4]);
          html.append("</FONT></TD>\n<TD "+bgColor+"ALIGN=right NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>");
          html.append(interfaceContable[5]);
          html.append("</FONT></TD>\n<TD "+bgColor+"ALIGN=right NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>");
          html.append(interfaceContable[6]);
          html.append("</FONT></TD>\n<TD "+bgColor+"ALIGN=right NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>");
          html.append(interfaceContable[7]);
          html.append("</FONT></TD>\n<TD "+bgColor+"ALIGN=center NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>");
          html.append(interfaceContable[8]);
          html.append("</FONT></TD>\n<TD "+bgColor+"ALIGN=right NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>");
          html.append(interfaceContable[9]);
          html.append("</FONT></TD>\n<TD "+bgColor+"ALIGN=right NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>");
          html.append(interfaceContable[10]);
          html.append("</FONT></TD>\n<TD "+bgColor+"NOWRAP><FONT FACE='Verdana, Arial, Helvetica, sans-serif' SIZE=1>");
          html.append(interfaceContable[11]);
          html.append("</FONT></TD></TR>\n");

          addBGColor= !addBGColor;
        }
        html.append("</TABLE>\n");
      }
      else {
        html.append("<CENTER><FONT FACE='Verdana, Arial, Helvetica, sans-serif' COLOR='#000000' SIZE=1>No se encontraron registros</FONT></CENTER>\n");
      }

      html.append("<BR>\n");
      html.append("<TR><TD colspan=2><INPUT TYPE='HIDDEN' NAME='cadena' VALUE='"+nuevaCadena+"'>&nbsp;</TD></TR>");
      html.append("<CENTER><INPUT TYPE=BUTTON VALUE=Aceptar OnClick='history.go(-1);'></CENTER>\n");
      html.append(STBCL_GenerarBaseHTMLII.TBFL_PIE);
    }
    catch(SQLException e) {
      e.printStackTrace(System.out);
    }
    return html.toString();
  }

  private String opcion10(String fecha) {
    StringBuffer html= new StringBuffer();
    String mensaje;
    int codigo;

    Connection con= null;
    CallableStatement cs= null;

    try {
     con   =   DataSourceWrapper.getInstance().getConnection();

      cs = con.prepareCall("{ call TBPBD_InsJDEdwards(?, ?, ?) }");
      cs.setString(1, fecha);
      cs.registerOutParameter(2, Types.INTEGER);
      cs.registerOutParameter(3, Types.VARCHAR);

      cs.execute();

      codigo= cs.getInt(2);
      if(codigo== 0) mensaje= "Inserci&oacute;n exitosa";
      else mensaje= cs.getString(3);

      html.append(STBCL_GenerarBaseHTML.TBFL_CABEZA("Inserci&oacute;n De Interface Contable En El AS/400","Resultado de Inserci&oacute;n De Interface Contable En El AS/400", "", "", false));
      html.append("<CENTER><FONT FACE='Verdana, Arial, Helvetica, sans-serif' COLOR='#000000' SIZE=1>"+mensaje+"</FONT></CENTER>\n<BR>\n");
      html.append("<CENTER><INPUT TYPE=BUTTON VALUE=Aceptar OnClick='history.go(-1);'></CENTER>\n");
      html.append(STBCL_GenerarBaseHTML.TBFL_PIE);

    }
    catch(SQLException sqle) {
      sqle.printStackTrace(System.out);
    }
    finally{
      try {
            DataSourceWrapper.closeStatement(cs);
            DataSourceWrapper.closeConnection(con); 
      }
      catch(SQLException sqle) {
      }
    }

    return html.toString();
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
                  "      ,PRO_DESCRIPCION "+
                  "      ,PRO_NUMERO_IDENTIFICACION "+
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
   * Regresa el C&oacute;digo y la Descripci&oacute;n de todos los Productos registrados en el sistema
   * @return java.util.LinkedList
   */
  private LinkedList getDatosInterfaceContable(String producto, String fecha_contabilizacion) throws SQLException {
    LinkedList retList= new LinkedList();
    String query;

    Connection con= null;
    PreparedStatement ps= null;
    ResultSet rs= null;


    query=    "SELECT inc_numero_registro "+
              "      ,TO_CHAR(inc_fecha, 'yyyy-mm-dd') "+
              "      ,DECODE(inc_usuario, ' ', '&nbsp;', NULL, '&nbsp;', inc_usuario) "+
              "      ,DECODE(inc_lote, ' ', '&nbsp;', NULL, '&nbsp;', inc_lote) "+
              "      ,DECODE(inc_compania, ' ', '&nbsp;', NULL, '&nbsp;', inc_compania) "+
              "      ,DECODE(inc_unidad_negocio, ' ', '&nbsp;', NULL, '&nbsp;', inc_unidad_negocio) "+
              "      ,DECODE(inc_cuenta, ' ', '&nbsp;', NULL, '&nbsp;', inc_cuenta) "+
              "      ,DECODE(inc_auxiliar, ' ', '&nbsp;', NULL, '&nbsp;', inc_auxiliar) "+
              "      ,DECODE(inc_tipo_identificacion, ' ', '&nbsp;', NULL, '&nbsp;', inc_tipo_identificacion) "+
              "      ,DECODE(inc_numero_identificacion, ' ', '&nbsp;', NULL, '&nbsp;', inc_numero_identificacion) "+
              "      ,NVL(inc_valor, '0') "+
              "      ,DECODE(inc_descripcion, ' ', '&nbsp;', NULL, '&nbsp;', inc_descripcion) "+
              "  FROM tbinterface_contables "+
              " WHERE inc_fecha_contabilizacion = TO_DATE(?,'rrrr-mm-dd')";
    if(!producto.equals("")) {
      query+= "   AND inc_producto = ?";
    }
    query+= " ORDER BY inc_fecha, inc_numero_registro";

    try {
      con   =   DataSourceWrapper.getInstance().getConnection();
      ps= con.prepareStatement(query);

      ps.setString(1, fecha_contabilizacion);
      if(!producto.equals("")) {
        ps.setString(2, producto);
      }

      rs= ps.executeQuery();
      while(rs.next()) {
        int count= rs.getMetaData().getColumnCount();
        String[] record= new String[count];
        for(int i= 0; i< count; i++) {
          record[i]= rs.getString(i+1);
        }

        retList.add(record);
      }

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
  
}

