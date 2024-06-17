package TBPKT_PARAMETROS.TBPKT_MINIMOFONDO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_Minimos_Fondos extends HttpServlet{
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
    Vector v_resultadodeclaracion, v_resultadodeclaracion1;

    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


//------------------------------------------

    //Toma el v_codigo del producto
    String v_codpro = "";

    try{
       v_codpro = request.getParameter("v_codigo");
       }
    catch (Exception e) { e.printStackTrace(); }

      //ESCOGO LOS FONDOS QUE HAY PARA DICHO PRODUCTO
      v_declaracion = "SELECT mif_ref_fondo,\n"+
                      "       ref_descripcion,\n"+
                      "       mif_porcentaje\n"+
                      "FROM   tbminimos_fondo,\n"+
                      "       tbreferencias\n"+
                      "WHERE  mif_pro_codigo = '"+v_codpro+"'\n"+
                      "AND    mif_ref_fondo = ref_codigo";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      v_resultadodeclaracion.trimToSize();

      //ESCOGO LOS FONDOS QUE NO ESTAN ASOCIADOS A DICHO PRODUCTO
      v_declaracion = "SELECT ref_codigo\n"+
                    " ,ref_descripcion \n"+
                    " FROM tbreferencias  \n"+
                    " where ref_codigo not in \n"+
                    " (SELECT mif_ref_fondo \n"+
                    " FROM tbminimos_fondo \n"+
                    " WHERE mif_pro_codigo = '"+v_codpro+"') \n"+
                    " AND ref_codigo LIKE 'UFO%' AND ref_codigo != 'UFO000'";
      v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);
      v_resultadodeclaracion1.trimToSize();


      if (!v_resultadodeclaracion.elementAt(0).toString().equals("No hay elementos"))
        {
        //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
        TBPL_Publicar(v_codpro, v_resultadodeclaracion, v_resultadodeclaracion1);
        }
      else
        {
        TBPL_Publicar(v_codpro, v_resultadodeclaracion1);
        }
      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }


      private void TBPL_Publicar(String v_codpro, Vector v_opciones1, Vector v_opciones2)
      {
      float Sumatoria=0;
      v_opciones1.trimToSize();
      v_opciones2.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de porcentajes mínimos por fondo", "Administración de porcentajes mínimos por fondo", "TBPKT_PARAMETROS.TBPKT_MINIMOFONDO.TBCS_ACEMMinimoFondo", "", true));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Porcentajes mínimos por fondo para&nbsp;"+v_codpro+"<INPUT TYPE=hidden NAME=v_codigo VALUE='"+v_codpro+"'></B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=3 rules=all width=\"100%\">");
      out.println("<TR class=\"td11\" borderColor=silver><TD width=\"5%\"><FONT color=white face=verdana size=1>&nbsp;</font></TD><TD align=middle width=\"22%\"><FONT color=white face=verdana size=1><B>Fondo</B></font></TD><TD align=middle width=\"22%\"><FONT color=white face=verdana size=1><B>Porcentaje</B></font></TD></TR>");
      for (int i=0; i<v_opciones1.size(); i+=3)
         {
         if (i<3)
           {
           out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=RADIO CHECKED NAME=fondos VALUE='"+v_opciones1.elementAt(i).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones1.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones1.elementAt(i+2).toString()+"</font></TD></TR>");
           Sumatoria += (float)(new Float(v_opciones1.elementAt(i+2).toString()).floatValue());
           }
         else
           {
           out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=RADIO NAME=fondos VALUE='"+v_opciones1.elementAt(i).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones1.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones1.elementAt(i+2).toString()+"</font></TD></TR>");
           Sumatoria += (float)(new Float(v_opciones1.elementAt(i+2).toString()).floatValue());
           }
         }
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1>&nbsp;</font></TD><TD align=middle width=\"22%\"><FONT color=black face=verdana size=1>TOTAL</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=total TYPE=hidden VALUE='"+Sumatoria+"'>"+Sumatoria+"</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BLOCKQUOTE>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='modifica' CHECKED>Modificar porcentaje mínimo por fondo<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='elimina'>Eliminar porcentaje mínimo por fondo<BR>");
      out.println("</font>");
      out.println("</BLOCKQUOTE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");


      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Porcentajes mínimos por fondo para adicionar</B></FONT></CENTER></TD></TR></TBODY></TABLE>");

      if (!v_opciones2.elementAt(0).toString().equals("No hay elementos"))
        {
        out.println("<BLOCKQUOTE>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<B>Fondos</B><BR><SELECT NAME=fondosa>");
        for (int i=0; i<v_opciones2.size(); i+=2)
           {
           out.println("<OPTION VALUE='"+v_opciones2.elementAt(i).toString()+"'>"+v_opciones2.elementAt(i+1).toString());
           }
        out.println("</SELECT>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adiciona'>Adicionar porcentaje mínimo por fondo<BR>");
        out.println("</font>");
        out.println("</BLOCKQUOTE>");
        }


      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos

      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      private void TBPL_Publicar(String v_codpro, Vector v_opciones1)
      {
      float Sumatoria=0;
      v_opciones1.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de porcentajes mínimos por fondo", "Administración de porcentajes mínimos por fondo", "TBPKT_PARAMETROS.TBPKT_MINIMOFONDO.TBCS_ACEMMinimoFondo", "", true));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Porcentajes mínimos por fondo para adicionar en&nbsp;"+v_codpro+"<INPUT TYPE=hidden NAME=v_codigo VALUE='"+v_codpro+"'></B></FONT></CENTER></TD></TR></TBODY></TABLE>");


      if (!v_opciones1.elementAt(0).toString().equals("No hay elementos"))
        {
        out.println("<BLOCKQUOTE>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<B>Fondos</B><BR><SELECT NAME=fondosa>");
        for (int i=0; i<v_opciones1.size(); i+=2)
           {
           out.println("<OPTION VALUE='"+v_opciones1.elementAt(i).toString()+"'>"+v_opciones1.elementAt(i+1).toString());
           }
        out.println("</SELECT>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<INPUT NAME=total TYPE=hidden VALUE='0'>");
        out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adiciona' CHECKED>Adicionar porcentaje mínimo por fondo<BR>");
        out.println("</font>");
        out.println("</BLOCKQUOTE>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
        }
      else
        {
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
        }
      //Hasta aca van los campos
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }
  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_MINIMOFONDO.TBCS_Minimos_Fondos Information";
  }
}

