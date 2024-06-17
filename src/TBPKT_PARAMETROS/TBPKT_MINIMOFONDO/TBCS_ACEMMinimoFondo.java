package TBPKT_PARAMETROS.TBPKT_MINIMOFONDO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_ACEMMinimoFondo extends HttpServlet{
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

    //Toma la v_opcion del servlet
    String v_opcion = "Consulta";
    //Toma el código del producto
    String v_codigo = "";
    //Toma el fondo
    String fondos = "";
    //Toma el total de dicho v_codigo
    float total = 100;

    try{
       v_opcion = request.getParameter("v_opcion");
       if (v_opcion.equals("adiciona"))
         fondos = request.getParameter("fondosa");
       else
         fondos = request.getParameter("fondos");
       v_codigo = request.getParameter("v_codigo");
       total = 100 - Float.parseFloat(request.getParameter("total"));
       }
    catch (Exception e) { e.printStackTrace(); }


   if (v_opcion.equals("modifica"))
     {
      v_declaracion =  "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+fondos+"' AND ref_codigo LIKE 'UFO%' AND ref_codigo > 'UFO000'";
      String fondo = v_Consulta.TBFL_Consulta(v_declaracion).elementAt(0).toString();

      v_declaracion = "SELECT mif_pro_codigo, mif_ref_fondo, mif_porcentaje FROM tbminimos_fondo WHERE mif_pro_codigo = '"+v_codigo+"' AND mif_ref_fondo = '"+fondos+"'";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarModifica(fondo, v_resultadodeclaracion, total+Float.parseFloat(v_resultadodeclaracion.elementAt(2).toString()));
     }
  else if (v_opcion.equals("adiciona"))
     {
     v_declaracion =  "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+fondos+"' AND ref_codigo LIKE 'UFO%' AND ref_codigo > 'UFO000'";
     String fondo = v_Consulta.TBFL_Consulta(v_declaracion).elementAt(0).toString();

     //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
     TBPL_PublicarAdiciona(v_codigo, fondos, fondo, total);
     }
   else if (v_opcion.equals("elimina"))
     {
      v_declaracion =  "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+fondos+"' AND ref_codigo LIKE 'UFO%' AND ref_codigo > 'UFO000'";
      String fondo = v_Consulta.TBFL_Consulta(v_declaracion).elementAt(0).toString();

      v_declaracion = "SELECT mif_pro_codigo, mif_ref_fondo, mif_porcentaje FROM tbminimos_fondo WHERE mif_pro_codigo = '"+v_codigo+"' AND mif_ref_fondo = '"+fondos+"'";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarElimina(fondo, v_resultadodeclaracion);

     }

  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }



      private void TBPL_PublicarModifica(String fondo, Vector v_descripcion, float total)
      {
      v_descripcion.trimToSize();


      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de porcentajes mínimos por fondo", "Administración de porcentajes mínimos por fondo", "TBPKT_PARAMETROS.TBPKT_MINIMOFONDO.TBCS_ModificaMinimoFondo", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificar porcentaje mínimo por fondo</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codigo value='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fondo:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+fondo+"<INPUT TYPE=hidden NAME=fondo value='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Porcentaje:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"%<INPUT TYPE=hidden NAME=dporcentaje value='"+v_descripcion.elementAt(2).toString()+"'><BR><INPUT TYPE=TEXT MAXLENGTH=5  SIZE=4 NAME=obligatorioporcentaje VALUE='"+v_descripcion.elementAt(2).toString()+"' onblur='if (esNumero(this)==1) return false; noExcede(obligatorioporcentaje, "+total+")'>%</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER><BR>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      private void TBPL_PublicarAdiciona(String v_codigo, String cfondos, String fondo, float total)
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de porcentajes mínimo por fondo", "Administración de porcentajes mínimo por fondo","TBPKT_PARAMETROS.TBPKT_MINIMOFONDO.TBCS_AdicionaMinimoFondo", "", true, "moduloparametro", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adicionar porcentaje mínimo por fondo</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del producto:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codigo+"<INPUT TYPE=HIDDEN NAME=obligatoriocodigo VALUE='"+v_codigo+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Fondo:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+fondo+"<INPUT TYPE=HIDDEN NAME=obligatoriofondo VALUE='"+cfondos+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Porcentaje:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=TEXT MAXLENGTH=5  SIZE=4 NAME=obligatorioporcentaje onblur='if (esNumero(this)==1) return false; noExcede(obligatorioporcentaje, "+total+")'>%</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



      private void TBPL_PublicarElimina(String fondo, Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de porcentajes mínimos por fondo","Administración de porcentajes mínimos por fondo", "TBPKT_PARAMETROS.TBPKT_MINIMOFONDO.TBCS_EliminaMinimoFondo", "", true));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Eliminar porcentaje mínimo por fondo</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codigo value='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fondo:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+fondo+"<INPUT TYPE=hidden NAME=fondo value='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"%</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Eliminar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
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
    return "TBPKT_MINIMOFONDO.TBCS_ACEMMinimoFondo Information";
  }
}

