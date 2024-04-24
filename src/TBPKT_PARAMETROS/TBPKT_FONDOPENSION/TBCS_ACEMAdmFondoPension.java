package TBPKT_PARAMETROS.TBPKT_FONDOPENSION;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108


//Nobre de la clase que se encarga de mostrar las paginas de modificar, Consultar, eliminar y adicionar AFP
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_ACEMAdmFondoPension extends HttpServlet{
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
    //Toma la v_descripcion del item certificado
    String v_descripcion = "";

    //Toma de los parametros
    /*        try{
       v_opcion = request.getParameter("v_opcion");
       v_descripcion = request.getParameter("v_descripcion");
       }
    catch (Exception e) { e.printStackTrace(); }*///INT20131108
    try{
       v_opcion = request.getParameter("v_opcion");
       CleanResults cr1 = as.scan(request.getParameter("v_descripcion"), new File(POLICY_FILE_LOCATION));
       v_descripcion = cr1.getCleanHTML();
       }
    catch (Exception e) { e.printStackTrace(); }//INT20131108

   if (v_opcion.equals("Consulta"))//Si escoge Consulta reliza las respectivas Consultas
     {
      v_declaracion = "SELECT afp_codigo,\n"+
                      "       afp_descripcion,\n"+
                      "       afp_nit,\n"+
                      "       ref_descripcion\n"+
                      "  FROM tbadm_fondos_pensiones,\n"+
                      "       tbreferencias\n"+
                      " WHERE afp_codigo = '"+v_descripcion+"'\n"+
                      "   AND afp_ref_tipo = ref_codigo ";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      TBPL_PublicarConsulta(v_resultadodeclaracion);//TBPL_Publicar pagina de Consulta
     }
   else if (v_opcion.equals("modifica"))
     {
      v_declaracion = "SELECT afp_codigo,\n"+
                      "       afp_descripcion,\n"+
                      "       afp_nit,\n"+
                      "       ref_descripcion\n"+
                      "  FROM tbadm_fondos_pensiones,\n"+
                      "       tbreferencias\n"+
                      " WHERE afp_codigo = '"+v_descripcion+"'\n"+
                      "   AND afp_ref_tipo = ref_codigo ";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'STA%' AND ref_codigo > 'STA000'";
      v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);

      TBPL_PublicarModifica(v_resultadodeclaracion, v_resultadodeclaracion1);
     }
  else if (v_opcion.equals("adiciona"))
     {
      v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'STA%' AND ref_codigo > 'STA000'";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      TBPL_PublicarAdiciona(v_resultadodeclaracion);
     }
   else if (v_opcion.equals("elimina"))
     {
      v_declaracion = "SELECT afp_codigo,\n"+
                      "       afp_descripcion,\n"+
                      "       afp_nit,\n"+
                      "       ref_descripcion\n"+
                      "FROM   tbadm_fondos_pensiones,\n"+
                      "       tbreferencias\n"+
                      "WHERE  afp_codigo = '"+v_descripcion+"'\n"+
                      "AND    afp_ref_tipo = ref_codigo ";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      TBPL_PublicarElimina(v_resultadodeclaracion);
     }

  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }



      private void TBPL_PublicarConsulta(Vector v_descripcion)
      {
      v_descripcion.trimToSize();
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de Administradoras de Fondos de Pensiones","Administración de Administradoras de Fondos de Pensiones", "", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Consulta de Administradoras de Fondos de Pensiones</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Nit de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
      //Hasta aca van los campos
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

      private void TBPL_PublicarModifica(Vector v_descripcion, Vector v_opciones)
      {
      v_descripcion.trimToSize();
      v_opciones.trimToSize();
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de Administradoras de Fondos de Pensiones","Administración de Administradoras de Fondos de Pensiones", "TBPKT_PARAMETROS.TBPKT_FONDOPENSION.TBCS_ModificaAFP", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificación de Administradoras de Fondos de Pensiones</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=codigoafp value='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Descripción de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"<INPUT TYPE=hidden NAME=v_ddescripcion value='"+v_descripcion.elementAt(1).toString()+"'><BR><INPUT TYPE=TEXT MAXLENGTH=100  SIZE=50 NAME=obligatoriodescripcion value='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Nit de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"<INPUT TYPE=hidden NAME=dnit value='"+v_descripcion.elementAt(2).toString()+"'><BR><INPUT TYPE=TEXT MAXLENGTH=13  SIZE=10 NAME=obligatorionit value='"+v_descripcion.elementAt(2).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"<INPUT TYPE=hidden NAME=dtipoafp value='"+v_descripcion.elementAt(3).toString()+"'><BR><SELECT NAME=tipoafp>");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         if (v_descripcion.elementAt(3).equals(v_opciones.elementAt(i+1).toString()))
           out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"' SELECTED>"+v_opciones.elementAt(i+1).toString());
         else
           out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER><BR>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      private void TBPL_PublicarAdiciona(Vector v_opciones)
      {
      v_opciones.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de Administradoras de Fondos de Pensiones", "Administración de Administradoras de Fondos de Pensiones", "TBPKT_PARAMETROS.TBPKT_FONDOPENSION.TBCS_AdicionaAFP", "", true, "moduloparametro", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adición de Administradoras de Fondos de Pensiones</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=TEXT MAXLENGTH=2  SIZE=2 NAME=obligatoriocodigoafp></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Descripción de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=TEXT MAXLENGTH=100  SIZE=50 NAME=obligatoriodescripcion></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Nit de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=TEXT MAXLENGTH=13  SIZE=10 NAME=obligatorionit></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Tipo de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=obligatoriotipoafp>");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



      private void TBPL_PublicarElimina(Vector v_descripcion)
      {
      v_descripcion.trimToSize();
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de Administradoras de Fondos de Pensiones","Administración de Administradoras de Fondos de Pensiones", "TBPKT_PARAMETROS.TBPKT_FONDOPENSION.TBCS_EliminaAFP", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Eliminación de Administradoras de Fondos de Pensiones</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=codigoafp value='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Nit de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de la AFP:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
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
    return "TBPKT_FONDOPENSION.TBCS_ACEMAdmFondoPension Information";
  }
}

