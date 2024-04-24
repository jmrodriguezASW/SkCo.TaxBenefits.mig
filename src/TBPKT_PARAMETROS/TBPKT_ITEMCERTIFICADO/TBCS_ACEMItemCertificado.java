package TBPKT_PARAMETROS.TBPKT_ITEMCERTIFICADO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108


public class TBCS_ACEMItemCertificado extends HttpServlet implements SingleThreadModel{
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
       response.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
       //response.setHeader("Pragma", "No-Cache");
       //response.setDateHeader("Expires", 0);
       String parametros[] = new String[8];
       String  cadena = request.getParameter("cadena");
       v_nuevaCadena = cadena;
       String ip_tax = request.getRemoteAddr();
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       out = new PrintWriter (response.getOutputStream());
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

    v_Consulta = new TBCL_Consulta();
    String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    //Toma la v_opcion del servlet
    String v_opcion = "";
    //Toma la v_descripcion del item certificado
    String v_descripcion = "";

      /*       try{
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

   if (v_opcion.equals("consulta"))
     {
      v_declaracion = "SELECT itc_codigo,\n"+
                      "       itc_descripcion,\n"+
                      "       ref_descripcion\n"+
                      "FROM   tbitems_certificado,\n"+
                      "       tbreferencias\n"+
                      "WHERE itc_codigo = '"+v_descripcion+"'\n"+
                      "AND    itc_ref_tipo_dato = ref_codigo";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarConsulta(v_resultadodeclaracion);
     }
   else if (v_opcion.equals("modifica"))
     {
      //Verifica que no halla información asociada
      v_declaracion = "SELECT ani_itc_codigo FROM tbanos_items WHERE ani_itc_codigo = '"+v_descripcion+"'";
      v_resultadodeclaracion2 = v_Consulta.TBFL_Consulta(v_declaracion);

      v_declaracion = "SELECT itc_codigo,\n"+
                      "       itc_descripcion,\n"+
                      "       ref_descripcion\n"+
                      "FROM   tbitems_certificado,\n"+
                      "       tbreferencias\n"+
                      "WHERE itc_codigo = '"+v_descripcion+"'\n"+
                      "AND    itc_ref_tipo_dato = ref_codigo";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'STD%' AND ref_codigo != 'STD000'";
      v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);

      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarModifica(v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2);
     }
   else if (v_opcion.equals("adiciona"))
     {
      v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'STD%' AND ref_codigo != 'STD000'";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarAdiciona(v_resultadodeclaracion);
     }
   else if (v_opcion.equals("elimina"))
     {
      v_declaracion = "SELECT itc_codigo,\n"+
                      "       itc_descripcion,\n"+
                      "       ref_descripcion\n"+
                      "FROM   tbitems_certificado,\n"+
                      "       tbreferencias\n"+
                      "WHERE itc_codigo = '"+v_descripcion+"'\n"+
                      "AND    itc_ref_tipo_dato = ref_codigo";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarElimina(v_resultadodeclaracion);
     }

  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }


      private void TBPL_PublicarConsulta(Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de items de certificados de retención en la fuente por producto","Administración de items de certificados de retención en la fuente por producto", "", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Consulta de Item de certificado de retención en la fuente</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del Item de certificado</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción del Item de certificado</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de dato del Item de certificado</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
      out.println("</TABLE>");
      //Hasta aca van los campos
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

      private void TBPL_PublicarModifica(Vector v_descripcion, Vector v_opciones, Vector v_confirma)
      {
      v_descripcion.trimToSize();
      v_opciones.trimToSize();
      v_confirma.trimToSize();

      if (v_confirma.elementAt(0).toString().equals("No hay elementos"))
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de items de certificados de retención en la fuente por producto","Administración de items de certificados de retención en la fuente por producto", "TBPKT_PARAMETROS.TBPKT_ITEMCERTIFICADO.TBCS_ModificaItemCertificado", "", true, "moduloparametro.js", "return checkrequired(this)"));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                    "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificación de Item de certificado de retención en la fuente</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del Item de certificado</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=coditemcert value='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Descripción del Item de certificado</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"<INPUT TYPE=hidden NAME=v_ddescripcion value='"+v_descripcion.elementAt(1).toString()+"'><BR><INPUT TYPE=TEXT MAXLENGTH=50  SIZE=50 NAME=obligatoriodescripcion VALUE='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de dato del Item de certificado</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"<INPUT TYPE=hidden NAME=dtipodato value='"+v_descripcion.elementAt(2).toString()+"'><BR><SELECT NAME=tipodato>");
        for (int i=0; i<v_opciones.size(); i+=2)
           {
           if (v_descripcion.elementAt(2).equals(v_opciones.elementAt(i+1).toString()))
             out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"' SELECTED>"+v_opciones.elementAt(i+1).toString());
           else
             out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"' >"+v_opciones.elementAt(i+1).toString());
           }
        out.println("</SELECT></font></TD></TR>");
        out.println("</TABLE>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER><BR>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        }
      else
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de items de certificados de retención en la fuente por producto","Administración de items de certificados de retención en la fuente por producto", "", "<BLOCKQUOTE>No puede modificar un item de certificado asociado a uno o varios formatos de certificado</BLOCKQUOTE>", false));
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
        }
        //Hasta aca van los campos
        out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
        out.close();
      }


      private void TBPL_PublicarAdiciona(Vector v_opciones)
      {
      v_opciones.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de items de certificados de retención en la fuente por producto", "Administración de items de certificados de retención en la fuente por producto","TBPKT_PARAMETROS.TBPKT_ITEMCERTIFICADO.TBCS_AdicionaItemCertificado", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adición de Item de certificado de retención en la fuente</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del Item de certificado:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>ITEM<SELECT NAME=cod1><OPTION>0<OPTION>1<OPTION>2<OPTION>3<OPTION>4<OPTION>5<OPTION>6<OPTION>7<OPTION>8<OPTION>9</SELECT><SELECT NAME=cod2><OPTION>0<OPTION>1<OPTION>2<OPTION>3<OPTION>4<OPTION>5<OPTION>6<OPTION>7<OPTION>8<OPTION>9</SELECT></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Descripción del Item de certificado:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=TEXT MAXLENGTH=40  SIZE=50 NAME=obligatoriodescripcion></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Tipo de dato del Item de certificado:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=tipodato>");
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

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de items de certificados de retención en la fuente por producto","Administración de items de certificados de retención en la fuente por producto", "TBPKT_PARAMETROS.TBPKT_ITEMCERTIFICADO.TBCS_EliminaItemCertificado", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Eliminación de Item de certificado de retención en la fuente</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del Item de certificado</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"&nbsp;&nbsp;<INPUT TYPE=hidden NAME=coditemcert value='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción del Item de certificado</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de dato del Item de certificado</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
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
    return "TBPKT_ITEMCERTIFICADO.TBCS_ACEMItemCertificado Information";
  }
}

