package TBPKT_PARAMETROS.TBPKT_REFERENCIASISTEMA;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_ConfirmaModificaReferenciaSistema extends HttpServlet{
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
    String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    //Toma el v_codigo del producto
    String v_codigo = "";


    try{
       v_codigo = request.getParameter("v_codigo");
       }
    catch (Exception e) { e.printStackTrace(); }




    v_declaracion =  "SELECT ref_codigo, ref_descripcion, ref_valor FROM tbreferencias WHERE ref_codigo = '"+v_codigo+"'";
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

    v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    TBPL_Publicar(v_resultadodeclaracion);
  }


      private void TBPL_Publicar(Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      for (int i = 0; i<v_descripcion.size(); i++)
         if (v_descripcion.elementAt(i).toString().equals("null")||v_descripcion.elementAt(i).toString().equals("No hay elementos"))
            v_descripcion.setElementAt(" ", i);

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de referencias de sistema","Administración de referencias de sistema", "TBPKT_PARAMETROS.TBPKT_REFERENCIASISTEMA.TBCS_ModificaReferenciaSistema", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificar referencia de sistema</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codigo VALUE='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Descripción:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"<INPUT TYPE=hidden NAME=v_ddescripcion VALUE='"+v_descripcion.elementAt(1).toString()+"'><BR><INPUT TYPE=text NAME=obligatoriodescripcion MAXLENGTH=50 SIZE=50 VALUE='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"<INPUT TYPE=hidden NAME=v_dvalor VALUE='"+v_descripcion.elementAt(2).toString()+"'><BR><INPUT TYPE=text NAME=v_valor MAXLENGTH=50 SIZE=50 VALUE='"+v_descripcion.elementAt(2).toString()+"'></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER><BR>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
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
    return "TBPKT_REFERENCIASISTEMA.TBCS_ConfirmaModificaReferenciaSistema Information";
  }
}

