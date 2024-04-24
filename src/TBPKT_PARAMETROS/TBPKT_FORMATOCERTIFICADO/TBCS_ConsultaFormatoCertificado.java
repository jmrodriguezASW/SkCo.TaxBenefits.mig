package TBPKT_PARAMETROS.TBPKT_FORMATOCERTIFICADO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_ConsultaFormatoCertificado extends HttpServlet{
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
       response.setContentType("text/html");
       out = new PrintWriter (response.getOutputStream());
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
    String Consulta;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadoConsulta, resultadoConsulta1, resultadoConsulta2, resultadoConsulta3, resultadoConsulta4;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


    //Toma el año del formato de certificado
    String ano = "";
    //Toma el código del producto
    String v_codigo = "";

    try{
       ano = request.getParameter("obligatorioano");
       v_codigo = request.getParameter("v_codigo");
       }
    catch (Exception e) { e.printStackTrace(); }

      Consulta = "SELECT   foc_pro_codigo, \n"+
"         foc_ano,\n"+
"         foc_descripcion,\n"+
"         foc_header,\n"+
"         foc_footer,\n"+
"         ref_descripcion\n"+
"FROM     tbformatos_certificado,\n"+
"         tbreferencias\n"+
"WHERE    foc_ano = '"+ano+"'\n"+
"AND      foc_pro_codigo = '"+v_codigo+"'\n"+
"AND      ref_codigo = foc_ref_codigo";

      v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);

      Consulta = "SELECT ani_titulo, ani_posicion, ani_itc_codigo FROM tbanos_items WHERE ani_foc_ano = '"+ano+"' and ani_foc_pro_codigo = '"+v_codigo+"' ORDER BY ani_posicion";
      resultadoConsulta1 = v_Consulta.TBFL_Consulta(Consulta);


      if (v_resultadoConsulta.elementAt(0).toString().equals("No hay elementos"))
        {
        Consulta = "SELECT itc_codigo, itc_descripcion FROM tbitems_certificado ORDER BY itc_descripcion";
        resultadoConsulta3 = v_Consulta.TBFL_Consulta(Consulta);
        Consulta = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'SEC%' AND ref_codigo > 'SEC000'";
        resultadoConsulta4 = v_Consulta.TBFL_Consulta(Consulta);
        //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
        PublicarAdicionar(ano, v_codigo, resultadoConsulta3, resultadoConsulta4);
        }
      else
        {
        //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
        PublicarConsultar(v_resultadoConsulta, resultadoConsulta1);
        }
   v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }



      private void PublicarConsultar(Vector v_descripcion, Vector titulo)
      {
      v_descripcion.trimToSize();
      titulo.trimToSize();

      for (int i = 0; i<v_descripcion.size(); i++)
         if (v_descripcion.elementAt(i).toString().equals("null")||v_descripcion.elementAt(i).toString().equals("No hay elementos"))
            v_descripcion.setElementAt(" ", i);

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de formatos de certificados de retención en la fuente","Administración de formatos de certificados de retención en la fuente", "TBPKT_PARAMETROS.TBPKT_FORMATOCERTIFICADO.TBCS_AEMFormatoCertificado", "", true,"moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Formato del certificado</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Año del formato:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"<INPUT TYPE=hidden NAME=ano VALUE='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><BR><B>Código del producto:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><BR><B>Descripción del formato:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"&nbsp;&nbsp;&nbsp;&nbsp;</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><BR><B>Estado del formato:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(5).toString()+"&nbsp;&nbsp;&nbsp;&nbsp;</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<BR><FONT color=black face=verdana size=1><B>CABEZA del formato:</B><BR><TEXTAREA NAME=head ROWS=8 COLS=50 READONLY>"+v_descripcion.elementAt(3).toString()+"</TEXTAREA></FONT><BR>&nbsp;&nbsp;<BR>");
      out.println("<BR><FONT color=black face=verdana size=1><B>PIE del formato:</B><BR><TEXTAREA NAME=foot ROWS=8 COLS=50 READONLY>"+v_descripcion.elementAt(4).toString()+"</TEXTAREA></FONT><BR>&nbsp;&nbsp;<BR>");
      out.println("<FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=v_opcion VALUE='eliminaFC'>Eliminar el formato</FONT><BR>");
      out.println("<FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=v_opcion VALUE='modificaFC'>Modificar el formato</FONT><BR>&nbsp;&nbsp<BR>");

      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Items del formato</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=4 rules=all width=\"100%\">");
      out.println("<TR align=middle class=\"td11\"><TH undefined><FONT color=white face=verdana size=1>&nbsp;</FONT></TH><TH undefined><FONT color=white face=verdana size=1><B>Descripción</B></FONT></TH><TH undefined><FONT color=white face=verdana size=1><B>Título</B></FONT></TH><TH undefined><FONT color=white face=verdana size=1><B>Posición</B></FONT></TH></TR>");
      if (!titulo.elementAt(0).toString().equals("No hay elementos"))
        for (int i=0; i<titulo.size(); i=i+3)
         {
         if (i<2)
           out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=titulo VALUE='"+titulo.elementAt(i+2).toString()+"' CHECKED></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+TBFL_ConsultaDescripcion(titulo.elementAt(i+2).toString())+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+titulo.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+titulo.elementAt(i+1).toString()+"</font></TD></TR>");
         else
           out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=titulo VALUE='"+titulo.elementAt(i+2).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+TBFL_ConsultaDescripcion(titulo.elementAt(i+2).toString())+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+titulo.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+titulo.elementAt(i+1).toString()+"</font></TD></TR>");
         }
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      if (!titulo.elementAt(0).toString().equals("No hay elementos"))
        {
        out.println("<FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=v_opcion VALUE='elimina'>Eliminar item</FONT><BR>");
        out.println("<FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=v_opcion VALUE='modifica' CHECKED>Modificar item</FONT><BR>");
        out.println("<FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=v_opcion VALUE='adiciona'>Adicionar item</FONT>");
        }
      else
        out.println("<FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=v_opcion VALUE='adiciona' CHECKED>Adicionar item</FONT>");

      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }




      private void PublicarAdicionar(String ano, String v_codigo, Vector v_opciones, Vector v_opciones1)
      {
      v_opciones.trimToSize();
      v_opciones1.trimToSize();


      if (!v_opciones.elementAt(0).toString().equals(""))
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de formatos de certificados de retención en la fuente", "Administración de formatos de certificados de retención en la fuente", "TBPKT_PARAMETROS.TBPKT_FORMATOCERTIFICADO.TBCS_AdicionaFormatoCertificado", "", true, "moduloparametro.js", "return checkrequired(this)"));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                    "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Formato del certificado</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Año del formato:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+ano+"<INPUT TYPE=hidden NAME=ano value='"+ano+"'></font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del producto:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codigo+"<INPUT TYPE=hidden NAME=v_codigo value='"+v_codigo+"'></font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Descripción del formato:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=TEXT MAXLENGTH=50  SIZE=25 NAME=obligatoriodescripcion></font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Estado del formato:</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=estfor>");
        for (int i=0; i<v_opciones1.size(); i+=2)
           {
           out.println("<OPTION VALUE='"+v_opciones1.elementAt(i).toString()+"'>"+v_opciones1.elementAt(i+1).toString());
           }
        out.println("</SELECT></FONT></TD></TR>");
        out.println("</TABLE>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<FONT color=black face=verdana size=1><B>CABEZA del formato:</B>&nbsp;&nbsp;<BR><TEXTAREA NAME=head ROWS=8 COLS=50></TEXTAREA></FONT><BR>&nbsp;&nbsp;<BR>");
        out.println("<FONT color=black face=verdana size=1><B>PIE del formato:</B>&nbsp;&nbsp;<BR><TEXTAREA NAME=foot ROWS=8 COLS=50></TEXTAREA></FONT><BR>&nbsp;&nbsp;<BR>");

        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                    "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Items del formato</B></FONT></CENTER></TD></TR></TBODY></TABLE>");

        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción del item del formato:</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=desitemcer>");
        for (int i=0; i<v_opciones.size(); i+=2)
           {
           out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
           }
        out.println("</SELECT></FONT></TD></TR>");

        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Título del tem del formato:</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><TEXTAREA NAME=obligatoriotitulo ROWS=5 COLS=20></TEXTAREA></FONT></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Posición de item del formato:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=TEXT MAXLENGTH=30  SIZE=20 NAME=obligatorioposicion></font></TD></TR>");
        out.println("</TABLE>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER><BR>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        }
      else
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de certificados de retención en la fuente por producto", "Administración de certificados de retención en la fuente por producto", "", "<BLOCKQUOTE>No puede adicionar sin ingresas datos en condiciones penalización</BLOCKQUOTE>", false));
        out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER><BR>");
        }
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
        }



   private String TBFL_ConsultaDescripcion(String titulo)
   {
   //v_Consulta = new TBCL_Consulta();
   //v_Consulta.TBPL_RealizarConexion();
   String Consulta = "SELECT itc_descripcion FROM tbitems_certificado WHERE itc_codigo = '"+titulo+"'";
   String ret = v_Consulta.TBFL_Consulta(Consulta).elementAt(0).toString();
   //v_Consulta.TBPL_shutDown();
   return ret;
   }
  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_FORMATOCERTIFICADO.TBCL_ConsultaFormatoCertificado Information";
  }
}

