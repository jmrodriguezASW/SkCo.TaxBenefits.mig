package TBPKT_PARAMETROS.TBPKT_PROGRAMA;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_ACEMPrograma extends HttpServlet{
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
    //Vector donde se guardara el resultado de la v_declaracion
    Vector v_resultadodeclaracion, v_resultadodeclaracion2, v_resultadodeclaracion3, v_resultadodeclaracion4, v_resultadodeclaracion5;
    Vector v_resultadodeclaracion1 = new Vector();
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    //Toma la opción de modificar, eliminar, adicionar o Consultar
    String v_opcion = "Consulta";
    //Toma los diferentes codigos de producto
    String v_codprg = "";

    try{
       v_opcion = request.getParameter("v_opcion");
       v_codprg = request.getParameter("v_codprg");
       }
    catch (Exception e) { e.printStackTrace(); }


   if (v_opcion.equals("consulta"))
   {
      //Sentencias SQL
      //selecciona todos los datos que hay en la tabla programas con la llave de v_codigo
      v_declaracion = "SELECT          prg_codigo,\n"+
                " prg_descripcion,\n"+
                " prg_activo,\n"+
                " prg_tipo_penalizacion\n"+
                " FROM tbprogramas_parametrizacion\n" +
                " WHERE prg_codigo  = '"+v_codprg+"'\n";
      //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
    
      //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
      //publicacion en pagina html de las Consultas
      TBPL_PublicarConsulta(v_codprg, v_resultadodeclaracion);
   }
   else if (v_opcion.equals("adiciona"))
   {
     //publicacion en pagina html de las Consultas
     TBPL_PublicarAdiciona();
   }
   else if (v_opcion.equals("elimina"))
   {
    //Sentencias SQL
      v_declaracion = "SELECT          prg_codigo,\n"+
                " prg_descripcion,\n"+
                " prg_activo,\n"+
                " prg_tipo_penalizacion\n"+
                " FROM tbprogramas_parametrizacion\n"+
                " WHERE prg_codigo  = ? \n";
     String v_parametros[] = new String[1];
     v_parametros[0] = v_codprg;
     v_resultadodeclaracion = v_Consulta.TBFL_ConsultaParametros(v_declaracion, v_parametros);

     //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos 
    //publicacion en pagina html de las Consultas
     TBPL_PublicarElimina(v_codprg, v_resultadodeclaracion);
   }
   else if (v_opcion.equals("modifica"))
   {
    //Sentencias SQL
      v_declaracion = "SELECT prg_codigo,\n"+
                " prg_descripcion,\n"+
                " prg_activo,\n"+
                " prg_tipo_penalizacion\n"+
                " FROM tbprogramas_parametrizacion\n"+
                " WHERE prg_codigo  = ? \n";
     String v_parametros[] = new String[1];
     v_parametros[0] = v_codprg;
     
     v_resultadodeclaracion = v_Consulta.TBFL_ConsultaParametros(v_declaracion, v_parametros);
     
     //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
     //publicacion en pagina html de las Consultas
     TBPL_PublicarModifica(v_resultadodeclaracion);
     }

  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }

//Código en HTML a mostrar en el navegador
      private void TBPL_PublicarConsulta(String v_codprg, Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de programas","Administración de programas", "", "", true));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Consultar Programa</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del programa</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codprg+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción del programa</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Activo</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

      //Código en HTML a mostrar en el navegador
      private void TBPL_PublicarAdiciona()
      {

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de programas", "Administración de programas","TBPKT_PARAMETROS.TBPKT_PROGRAMA.TBCS_AdicionaPrograma", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adicionar Programa</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del programa</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=obligatoriocodigo TYPE=text MAXLENGTH=5 SIZE=5></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Descripción del programa</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=obligatoriodescripcion TYPE=text MAXLENGTH=50 SIZE=30></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Activo</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=obligatorioactivo VALUE='S'CHECKED>Si<BR><INPUT TYPE=radio NAME=obligatorioactivo VALUE='N'>No</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=obligatoriotippenal TYPE=text MAXLENGTH=15 SIZE=10></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BLOCKQUOTE>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      
      out.println("</font>");
      out.println("</BLOCKQUOTE>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER><BR>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



//Código en HTML a mostrar en el navegador
      private void TBPL_PublicarElimina(String v_codprg, Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de programas","Administración de programas", "TBPKT_PARAMETROS.TBPKT_PROGRAMA.TBCS_EliminaPrograma", "", true));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Eliminar Programa</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del programa</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=v_codigo TYPE=hidden VALUE='"+v_codprg+"'>"+v_codprg+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción del programa</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Activo</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Eliminar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      //Código en HTML a mostrar en el navegador
      private void TBPL_PublicarModifica(Vector v_descripcion)
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de programas", "Administración de programas","TBPKT_PARAMETROS.TBPKT_PROGRAMA.TBCS_ModificaPrograma", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificar Programa</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del programa</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT NAME=obligatoriocodigo VALUE='"+v_descripcion.elementAt(0).toString()+"' TYPE=hidden ></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Descripción del prgrama</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"<BR><INPUT NAME=obligatoriodescripcion VALUE='"+v_descripcion.elementAt(1).toString()+"' TYPE=text MAXLENGTH=50 SIZE=30></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Activo</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>");
      if (v_descripcion.elementAt(2).toString().equals("S"))
      out.println("<INPUT TYPE=radio NAME=obligatorioactivo VALUE='S' CHECKED>Si<BR><INPUT TYPE=radio NAME=obligatorioactivo VALUE='N'>No");
      else
      out.println("<INPUT TYPE=radio NAME=obligatorioactivo VALUE='S'>Si<BR><INPUT TYPE=radio NAME=obligatorioactivo VALUE='N' CHECKED>No");
      out.println("</FONT></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Tipo de Penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"<BR><INPUT NAME=obligatoriotippenal VALUE='"+v_descripcion.elementAt(3).toString()+"' TYPE=text MAXLENGTH=13 SIZE=13></font></TD></TR>");      
      out.println("</TABLE><BR>");
      
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER><BR>");
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
    return "TBPKT_PROGRAMA.TBCS_ACEMPrograma Information";
  }
}

