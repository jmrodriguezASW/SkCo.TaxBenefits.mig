package TBPKT_PARAMETROS.TBPKT_PENALIZACION_TRASLADO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_ACEMPenalizacionTraslado extends HttpServlet{
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
    String v_codpro1 = "";
    String v_codpro2 = "";
    String v_codprg1 = "";
    String v_codprg2 = "";

    try{
       v_opcion = request.getParameter("v_opcion");
       v_codpro1 = request.getParameter("v_codpro1");
       v_codprg1 = request.getParameter("v_codprg1");
       v_codpro2 = request.getParameter("v_codpro2");
       v_codprg2 = request.getParameter("v_codprg2");
       }
    catch (Exception e) { e.printStackTrace(); }


   if (v_opcion.equals("consulta"))
   {
      //Sentencias SQL
      //selecciona todos los datos que hay en la tabla 
      v_declaracion = "SELECT  PTR_PENALIZA_TITULAR,\n"+
                " PTR_PENALIZA_TERCERO\n"+
                " FROM TBPENALIZACIONTRASLADO\n" +
                " WHERE PTR_PRODUCTO_ORIGEN  = '"+v_codpro1+"'\n" +
                " AND PTR_PROGRAMA_ORIGEN  = '"+v_codprg1+"'\n" +
                " AND PTR_PRODUCTO_DESTINO  = '"+v_codpro2+"'\n" +
                " AND PTR_PROGRAMA_DESTINO  = '"+v_codprg2+"'\n";
      //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
    
      //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
      //publicacion en pagina html de las Consultas
      TBPL_PublicarConsulta(v_codpro1, v_codprg1, v_codpro2, v_codprg2, v_resultadodeclaracion);
   }
   else if (v_opcion.equals("adiciona"))
   {
     //publicacion en pagina html de las Consultas
     TBPL_PublicarAdiciona(v_codpro1, v_codprg1, v_codpro2, v_codprg2);
   }
   else if (v_opcion.equals("elimina"))
   {
    //Sentencias SQL
      v_declaracion =  "SELECT PTR_PENALIZA_TITULAR,\n"+
                " PTR_PENALIZA_TERCERO\n"+
                " FROM TBPENALIZACIONTRASLADO\n" +
                " WHERE PTR_PRODUCTO_ORIGEN  = '"+v_codpro1+"'\n" +
                " AND PTR_PROGRAMA_ORIGEN  = '"+v_codprg1+"'\n" +
                " AND PTR_PRODUCTO_DESTINO  = '"+v_codpro2+"'\n" +
                " AND PTR_PROGRAMA_DESTINO  = '"+v_codprg2+"'\n";
     v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

     //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos 
    //publicacion en pagina html de las Consultas
     //TBPL_PublicarElimina(v_codprg, v_resultadodeclaracion);
     TBPL_PublicarElimina(v_codpro1, v_codprg1, v_codpro2, v_codprg2, v_resultadodeclaracion);
   }
   else if (v_opcion.equals("modifica"))
   {
    //Sentencias SQL
      v_declaracion = "SELECT PTR_PENALIZA_TITULAR,\n"+
                " PTR_PENALIZA_TERCERO\n"+
                " FROM TBPENALIZACIONTRASLADO\n" +
                " WHERE PTR_PRODUCTO_ORIGEN = '"+v_codpro1+"'\n" +
                " AND PTR_PROGRAMA_ORIGEN   = '"+v_codprg1+"'\n" +
                " AND PTR_PRODUCTO_DESTINO  = '"+v_codpro2+"'\n" +
                " AND PTR_PROGRAMA_DESTINO  = '"+v_codprg2+"'\n";
     v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
     
     //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
     //publicacion en pagina html de las Consultas
     TBPL_PublicarModifica(v_codpro1, v_codprg1, v_codpro2, v_codprg2, v_resultadodeclaracion);
     }

  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }

//Código en HTML a mostrar en el navegador
      private void TBPL_PublicarConsulta(String v_codpro1, String v_codprg1, String v_codpro2, String v_codprg2, Vector v_descripcion)
      {
      
      v_descripcion.trimToSize();
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de penalización de traslados","Administración de penalización de traslados", "", "", true));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Consultar penalización de traslados</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      
      
      if (v_descripcion.elementAt(0).toString().equals("No hay elementos")){
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No se han definido condiciones de penalización para esto datos.</B></font></TD></TR>");
      }
      else {
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro1+"</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del programa origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codprg1+"</font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro2+"</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del programa destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codprg2+"</font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penaliza a titular</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penaliza a tercero</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      }
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

      //Código en HTML a mostrar en el navegador
      private void TBPL_PublicarAdiciona(String v_codpro1, String v_codprg1, String v_codpro2, String v_codprg2)
      {

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de penalización de traslados", "Administración de penalización de traslados","TBPKT_PARAMETROS.TBPKT_PENALIZACION_TRASLADO.TBCS_AdicionaPenalizacionTraslado", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adicionar Programa</B></FONT></CENTER></TD></TR></TBODY></TABLE>");

      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del producto origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=obligatorioprodorig TYPE=text MAXLENGTH=10 SIZE=5 value="+v_codpro1+"></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del programa origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=obligatorioprogorig TYPE=text MAXLENGTH=10 SIZE=5 value="+v_codprg1+"></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del producto destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=obligatorioproddest TYPE=text MAXLENGTH=10 SIZE=5 value="+v_codpro2+"></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del programa destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=obligatorioprogdest TYPE=text MAXLENGTH=10 SIZE=5 value="+v_codprg2+"></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Penaliza a titular</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=obligatoriotitular VALUE='S'CHECKED>Si<BR><INPUT TYPE=radio NAME=obligatoriotitular VALUE='N'>No</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Penaliza a tercero</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=obligatoriotercero VALUE='S'CHECKED>Si<BR><INPUT TYPE=radio NAME=obligatoriotercero VALUE='N'>No</font></TD></TR>");
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
      private void TBPL_PublicarElimina(String v_codpro1, String v_codprg1, String v_codpro2, String v_codprg2, Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de penalización de traslados","Administración de penalización de traslados", "TBPKT_PARAMETROS.TBPKT_PENALIZACION_TRASLADO.TBCS_EliminaPenalizacionTraslado", "", true));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Eliminar Penalización de traslados</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      
      if (v_descripcion.elementAt(0).toString().equals("No hay elementos")){
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No se han definido condiciones de penalización para esto datos.</B></font></TD></TR>");
      }
      else {
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro1+"<INPUT NAME=obligatorioprodorig VALUE='"+v_codpro1+"' TYPE=hidden></font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del programa origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codprg1+"<INPUT NAME=obligatorioprogorig VALUE='"+v_codprg1+"' TYPE=hidden></font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro2+"<INPUT NAME=obligatorioproddest VALUE='"+v_codpro2+"' TYPE=hidden></font></font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del programa destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codprg2+"<INPUT NAME=obligatorioprogdest VALUE='"+v_codprg2+"' TYPE=hidden></font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penaliza a titular</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penaliza a tercero</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      }
     
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Eliminar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      //Código en HTML a mostrar en el navegador
      private void TBPL_PublicarModifica(String v_codpro1, String v_codprg1, String v_codpro2, String v_codprg2, Vector v_descripcion)
      {
      
            v_descripcion.trimToSize();

      //out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de penalización de traslados","Administración de penalización de traslados", "TBPKT_PARAMETROS.TBPKT_PENALIZACION_TRASLADO.TBCS_ModificaPenalizacionTraslado", "", true));
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de penalización de traslados", "Administración de penalización de traslados","TBPKT_PARAMETROS.TBPKT_PENALIZACION_TRASLADO.TBCS_ModificaPenalizacionTraslado", "", true, "moduloparametro.js", "return checkrequired(this)"));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificar Penalización de traslados</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      
      if (v_descripcion.elementAt(0).toString().equals("No hay elementos")){
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No se han definido condiciones de penalización para esto datos.</B></font></TD></TR>");
      }
      else {
          /*out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro1+"<INPUT NAME=obligatorioprodorig VALUE='"+v_codpro1+"' TYPE=hidden></font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del programa origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codprg1+"<INPUT NAME=obligatorioprogorig VALUE='"+v_codprg1+"' TYPE=hidden></font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro2+"<INPUT NAME=obligatorioproddest VALUE='"+v_codpro2+"' TYPE=hidden></font></font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del programa destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codprg2+"<INPUT NAME=obligatorioprogdest VALUE='"+v_codprg2+"' TYPE=hidden></font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penaliza a titular</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penaliza a tercero</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");*/
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del producto origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro1+"<INPUT NAME=obligatorioprodorig VALUE='"+v_codpro1+"' TYPE=hidden ></font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del programa origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codprg1+"<INPUT NAME=obligatorioprogorig VALUE='"+v_codprg1+"' TYPE=hidden ></font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del producto destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro2+"<INPUT NAME=obligatorioproddest VALUE='"+v_codpro2+"' TYPE=hidden ></font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del programa destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codprg2+"<INPUT NAME=obligatorioprogdest VALUE='"+v_codprg2+"' TYPE=hidden ></font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Penaliza a titular</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>");
        if (v_descripcion.elementAt(0).toString().equals("S"))
            out.println("<INPUT TYPE=radio NAME=obligatoriotitular VALUE='S' CHECKED>Si<BR><INPUT TYPE=radio NAME=obligatoriotitular VALUE='N'>No");
        else
            out.println("<INPUT TYPE=radio NAME=obligatoriotitular VALUE='S'>Si<BR><INPUT TYPE=radio NAME=obligatoriotitular VALUE='N' CHECKED>No");
            out.println("</FONT></TD></TR>");
            out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Penaliza a tercero</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>");
  
        if (v_descripcion.elementAt(1).toString().equals("S"))
            out.println("<INPUT TYPE=radio NAME=obligatoriotercero VALUE='S' CHECKED>Si<BR><INPUT TYPE=radio NAME=obligatoriotercero VALUE='N'>No");
        else
            out.println("<INPUT TYPE=radio NAME=obligatoriotercero VALUE='S'>Si<BR><INPUT TYPE=radio NAME=obligatoriotercero VALUE='N' CHECKED>No");
            out.println("</FONT></TD></TR>");
          
      }
     
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      /*v_descripcion.trimToSize();
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de penalización de traslados", "Administración de penalización de traslados","TBPKT_PARAMETROS.TBPKT_PENALIZACION_TRASLADO.TBCS_ModificaPenalizacionTraslado", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificar penalización de traslados</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      if (v_descripcion.elementAt(0).toString().equals("No hay elementos")){
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No se han definido condiciones de penalización para esto datos.</B></font></TD></TR>");
      }
      else {
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del producto origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro1+"<INPUT NAME=obligatorioprodorig VALUE='"+v_codpro1+"' TYPE=hidden ></font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del programa origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codprg1+"<INPUT NAME=obligatorioprogorig VALUE='"+v_codprg1+"' TYPE=hidden ></font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del producto destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro2+"<INPUT NAME=obligatorioproddest VALUE='"+v_codpro2+"' TYPE=hidden ></font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del programa destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codprg2+"<INPUT NAME=obligatorioprogdest VALUE='"+v_codprg2+"' TYPE=hidden ></font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Penaliza a titular</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>");
        if (v_descripcion.elementAt(0).toString().equals("S"))
            out.println("<INPUT TYPE=radio NAME=obligatoriotitular VALUE='S' CHECKED>Si<BR><INPUT TYPE=radio NAME=obligatoriotitular VALUE='N'>No");
        else
            out.println("<INPUT TYPE=radio NAME=obligatoriotitular VALUE='S'>Si<BR><INPUT TYPE=radio NAME=obligatoriotitular VALUE='N' CHECKED>No");
            out.println("</FONT></TD></TR>");
            out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Penaliza a tercero</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>");
  
        if (v_descripcion.elementAt(1).toString().equals("S"))
            out.println("<INPUT TYPE=radio NAME=obligatoriotercero VALUE='S' CHECKED>Si<BR><INPUT TYPE=radio NAME=obligatoriotercero VALUE='N'>No");
        else
            out.println("<INPUT TYPE=radio NAME=obligatoriotercero VALUE='S'>Si<BR><INPUT TYPE=radio NAME=obligatoriotercero VALUE='N' CHECKED>No");
            out.println("</FONT></TD></TR>");
      }
      out.println("</TABLE><BR>");    
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER><BR>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();*/
      }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_PENALIZACION_TRASLADO.TBCS_ACEMPenalizacion Information";
  }
}

