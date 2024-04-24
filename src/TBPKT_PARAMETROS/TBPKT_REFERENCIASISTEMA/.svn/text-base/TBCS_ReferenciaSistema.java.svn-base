package TBPKT_PARAMETROS.TBPKT_REFERENCIASISTEMA;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_ReferenciaSistema extends HttpServlet implements SingleThreadModel{
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
    String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion = new Vector();
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


//------------------------------------------

      v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'S%000' ORDER BY ref_descripcion";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      PublicarConsultar(v_resultadodeclaracion);
  }

      private void PublicarConsultar(Vector v_opciones)
      {
      v_opciones.trimToSize();
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de referencias de sistema","Administración de referencias de Sistema", "TBPKT_PARAMETROS.TBPKT_REFERENCIASISTEMA.TBCS_ACEMReferenciasistema", "", true, "moduloparametro.js", "return checkrequired(this)"));
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
      //Aqui van los campo que se quieren mostrar
      out.println("<BLOCKQUOTE>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='Consulta' CHECKED>Consultar referencia de sistema<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='modifica'>Modificar referencias de sistema<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='elimina'>Eliminar referencias de sistema<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adiciona'>Adicionar referencias de sistema<BR>");
      out.println("&nbsp;&nbsp;<BR><B>Tipo de Referencia:</B>&nbsp;&nbsp;<SELECT NAME=v_codigo>");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("</BLOCKQUOTE>");
      //Hasta aca van los campos
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
    return "TBPKT_REFERENCIASISTEMA.TBCS_REFERENCIASISTEMA Information";
  }
}

