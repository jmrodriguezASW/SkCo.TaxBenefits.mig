package TBPKT_PARAMETROS.TBPKT_EMPRESA;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108


//Nombre de la clase que se encarga de mostrar las paginas de adicionar, Consultar, eliminar y modificar
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_ACEMEmpresa extends HttpServlet implements SingleThreadModel{
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
      //INT20131108
      String rutaFisica = TBCL_REFERENCIAS.TBCL_CONSULTA("SMM003");
      //rutaFisica  =  "c:\\Taxbenefits\\Taxbenefits\\public_html\\WEB-INF\\";
      String POLICY_FILE_LOCATION = rutaFisica+"antisamy-tinymce-1.4.4.xml"; // Path to policy file
      AntiSamy as = new AntiSamy(); // INT20131108
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
    Vector v_resultadodeclaracion, v_resultadodeclaracion1;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    //Toma la v_opcion del servlet
    String v_opcion = "Consulta";
    //Toma la v_descripcion de la empresa
    String v_descripcion = "";


    //Toma de los parametros
    /*      try{
       v_opcion = request.getParameter("v_opcion");
       v_descripcion = request.getParameter("v_descripcion");
       }
    catch (Exception e) { e.printStackTrace(); }*///INT20131108
    try{
       v_opcion = request.getParameter("v_opcion");
       CleanResults cr1 = as.scan(request.getParameter("v_descripcion"), new File(POLICY_FILE_LOCATION));
       v_descripcion = cr1.getCleanHTML();
       }
    catch (Exception e) { e.printStackTrace(); }

   if (v_opcion.equals("Consulta"))//Si selecciona Consulta realiza la Consulta para mostrar la pagina
     {
      v_declaracion = "SELECT emp_grupo,\n"+
                      "       emp_descripcion,\n"+
                      "       emp_nit,\n"+
                      "       ref_descripcion,\n"+
                      "       emp_detalle_condicion\n"+
                      "FROM   tbempresas,\n"+
                      "       tbreferencias\n"+
                      "WHERE  emp_grupo = '"+v_descripcion+"'\n"+
                      "AND    emp_ref_condicion  = ref_codigo (+)";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      TBPL_PublicarConsulta(v_resultadodeclaracion);//Publica la pagina de Consulta
     }
   else if (v_opcion.equals("modifica"))//Si escoge modificar publica la página de modificar
     {
      v_declaracion = "SELECT emp_grupo,\n"+
                      "       emp_descripcion,\n"+
                      "       emp_nit,\n"+
                      "       ref_descripcion,\n"+
                      "       emp_detalle_condicion\n"+
                      "FROM   tbempresas,\n"+
                      "       tbreferencias\n"+
                      "WHERE  emp_grupo = '"+v_descripcion+"'\n"+
                      "AND    emp_ref_condicion  = ref_codigo (+)";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'UDE%' AND ref_codigo > 'UDE000'";
      v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);
      v_resultadodeclaracion1.insertElementAt("NULL",0);
      v_resultadodeclaracion1.insertElementAt("", 1);


      TBPL_PublicarModifica(v_resultadodeclaracion, v_resultadodeclaracion1);//Publica la pagina de modifica
     }
  else if (v_opcion.equals("adiciona"))//Si slecciona adicionar reliza la Consulta correspondiente para mostrar
     {
      v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'UDE%' AND ref_codigo > 'UDE000'";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      v_resultadodeclaracion.insertElementAt("NULL",0);
      v_resultadodeclaracion.insertElementAt("", 1);

      
      TBPL_PublicarAdiciona(v_resultadodeclaracion);//Publica la pagina de adicionar
     }
   else if (v_opcion.equals("elimina"))//Si escoge eliminar reliza la Consulta correspondiente para mostrar la pagina de eliminar
     {
      v_declaracion = "SELECT emp_grupo,\n"+
                      "       emp_descripcion,\n"+
                      "       emp_nit,\n"+
                      "       ref_descripcion,\n"+
                      "       emp_detalle_condicion\n"+
                      "FROM   tbempresas,\n"+
                      "       tbreferencias\n"+
                      "WHERE  emp_grupo = '"+v_descripcion+"'\n"+
                      "AND    emp_ref_condicion  = ref_codigo (+)";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      TBPL_PublicarElimina(v_resultadodeclaracion);//Publica la pagina de eliminar
     }

  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }



      private void TBPL_PublicarConsulta(Vector v_descripcion)//Publica la pagina de Consulta
      {
      v_descripcion.trimToSize();

      for (int i = 0; i<v_descripcion.size(); i++)
         if (v_descripcion.elementAt(i).toString().equals("null")||v_descripcion.elementAt(i).toString().equals("No hay elementos"))
            v_descripcion.setElementAt(" ", i);

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas", "Administración de empresas", "", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Consultar empresa</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Grupo de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Nit de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Condición de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Detalle de la condición:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");


      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

      private void TBPL_PublicarModifica(Vector v_descripcion, Vector v_opciones)//Publica la pagina de modificar
      {
      v_descripcion.trimToSize();
      v_opciones.trimToSize();

      for (int i = 0; i<v_descripcion.size(); i++)
         if (v_descripcion.elementAt(i).toString().equals("null")||v_descripcion.elementAt(i).toString().equals("No hay elementos"))
            v_descripcion.setElementAt(" ", i);

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas", "Administración de empresas", "TBPKT_PARAMETROS.TBPKT_EMPRESA.TBCS_ModificaEmpresa", "", true, "moduloparametro.js", "return checkrequired(this)"));
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificar empresa</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Grupo de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=grupemp value='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Descripción de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"<INPUT TYPE=hidden NAME=v_ddescripcion value='"+v_descripcion.elementAt(1).toString()+"'><BR><INPUT TYPE=TEXT MAXLENGTH=100  SIZE=50 NAME=obligatoriodescripcion value='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Nit de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"<INPUT TYPE=hidden NAME=dnit value='"+v_descripcion.elementAt(2).toString()+"'><BR><INPUT TYPE=TEXT MAXLENGTH=13  SIZE=10 NAME=obligatorionit value='"+v_descripcion.elementAt(2).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Condición de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"<INPUT TYPE=hidden NAME=dcondicion value='"+v_descripcion.elementAt(3).toString()+"'><BR><SELECT NAME=condicion>");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         if (v_descripcion.elementAt(3).equals(v_opciones.elementAt(i+1).toString()))
           out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"' SELECTED>"+v_opciones.elementAt(i+1).toString());
         else
           out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Detalle de la condición:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=hidden NAME=ddetcon value='"+v_descripcion.elementAt(4).toString()+"'><TEXTAREA COLS=40 ROWS=4 NAME=detcon WRAP=HARD>"+v_descripcion.elementAt(4).toString()+"</TEXTAREA></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      private void TBPL_PublicarAdiciona(Vector v_opciones)//Publica la página de adicionar
      {
      v_opciones.trimToSize();
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas", "Administración de empresas","TBPKT_PARAMETROS.TBPKT_EMPRESA.TBCS_AdicionaEmpresa", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adicionar empresa</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Grupo de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=TEXT MAXLENGTH=9  SIZE=9 NAME=obligatoriogrupemp></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Descripción de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=TEXT MAXLENGTH=100  SIZE=50 NAME=obligatoriodescripcion>&nbsp;&nbsp;</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Nit de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=TEXT MAXLENGTH=13  SIZE=10 NAME=obligatorionit></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Condición de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=condicion>");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Detalle de la condición:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><TEXTAREA COLS=40 ROWS=4 NAME=detcon WRAP=HARD></TEXTAREA></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



      private void TBPL_PublicarElimina(Vector v_descripcion)//Publica la pagina de eliminar
      {
      v_descripcion.trimToSize();

      for (int i = 0; i<v_descripcion.size(); i++)
         if (v_descripcion.elementAt(i).toString().equals("null")||v_descripcion.elementAt(i).toString().equals("No hay elementos"))
            v_descripcion.setElementAt(" ", i);

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas", "Administración de empresas", "TBPKT_PARAMETROS.TBPKT_EMPRESA.TBCS_EliminaEmpresa", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Eliminar empresa</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Grupo de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=grupemp value='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Nit de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Condición de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Detalle de la condición:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"</font></TD></TR>");
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
    return "TBPKT_EMPRESA.TBCS_ACEMEmpresa Information";
  }
}

