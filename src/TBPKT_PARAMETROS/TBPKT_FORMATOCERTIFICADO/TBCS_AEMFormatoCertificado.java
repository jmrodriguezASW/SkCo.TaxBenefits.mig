package TBPKT_PARAMETROS.TBPKT_FORMATOCERTIFICADO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108

//Nombre de la clase que se encarga de mostrar las paginas de adición, eliminación y modificación
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_AEMFormatoCertificado extends HttpServlet{
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
  String v_declaracion = new String();
  Vector v_resultadodeclaracion = new Vector();
  String declaracion1 = new String();
  Vector v_resultadodeclaracion1 = new Vector();

    //Depenfdiendo de lo que se devuelve reliza una acción
    String accion = "";
    //Toma el año del certificado de retención en la fuente
    String ano = "";
    //Toma el código del producto
    String v_codpro = "";
    //Toma el titulo del año item
    String titulo = "";

    //Toma de los parametros
    
    /*    try{
          accion = request.getParameter("v_opcion");
          ano = request.getParameter("ano");
          v_codpro = request.getParameter("v_codpro");
          titulo = request.getParameter("titulo");
          }
       catch (Exception e) { e.printStackTrace(); }*///INT20131108
    
    try{
       accion = request.getParameter("v_opcion");
       ano = request.getParameter("ano");
       v_codpro = request.getParameter("v_codpro");
       CleanResults cr3 = as.scan(request.getParameter("titulo"), new File(POLICY_FILE_LOCATION));
       titulo = cr3.getCleanHTML();
       }
    catch (Exception e) { e.printStackTrace(); }//INT20131108

  v_Consulta.TBPL_RealizarConexion();

  if (accion.equals("eliminaFC"))//Si se escoge eliminar formato certificado elimina el formato certificado
    {
    if (v_Consulta.TBFL_Consulta("SELECT cer_ani_foc_ano FROM tbcertificados WHERE cer_con_pro_codigo = '"+v_codpro+"' AND cer_ani_foc_ano = '"+ano+"'").elementAt(0).toString().equals("No hay elementos"))
      {
      //Sentencias SQL
      v_declaracion = "DELETE tbanos_items WHERE ani_foc_pro_codigo = '"+v_codpro+"' AND ani_foc_ano = '"+ano+"'";
      //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, false);

      //Sentencias SQL
      v_declaracion = "DELETE tbformatos_certificado WHERE foc_pro_codigo = '"+v_codpro+"' AND foc_ano = '"+ano+"'";
      //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, false);
      TBPL_Publicar(v_resultadodeclaracion);//publica el resultasdo de la eliminación
      }
    else
      PublicarERROR();

    }
  else if (accion.equals("modificaFC"))//Si escoge modificar formato certificado realiza las Consultas correspondientes
    {
    if (v_Consulta.TBFL_Consulta("SELECT cer_ani_foc_ano FROM tbcertificados WHERE cer_con_pro_codigo = '"+v_codpro+"' AND cer_ani_foc_ano = '"+ano+"'").elementAt(0).toString().equals("No hay elementos"))
      {
      v_declaracion = "SELECT   foc_pro_codigo, \n"+
"         foc_ano,\n"+
"         foc_descripcion,\n"+
"         foc_header,\n"+
"         foc_footer,\n"+
"         ref_descripcion\n"+
"FROM     tbformatos_certificado,\n"+
"         tbreferencias\n"+
"WHERE    foc_ano = '"+ano+"'\n"+
"AND      foc_pro_codigo = '"+v_codpro+"'\n"+
"AND      ref_codigo = foc_ref_codigo";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'SEC%' AND ref_codigo > 'SEC000'";
      v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);
      //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
      PublicarModificarFC(v_resultadodeclaracion, v_resultadodeclaracion1);//Publica el resultado de la Consulta
      }
    else
      PublicarERROR();
    }
  else if (accion.equals("elimina")&&(titulo!=null))//Si escoge eliminar item año lo elimina
    {
    //Realiza la eliminación de item año
    //------------------------------
    if (v_Consulta.TBFL_Consulta("SELECT cer_ani_foc_ano FROM tbcertificados WHERE cer_con_pro_codigo = '"+v_codpro+"' AND cer_ani_foc_ano = '"+ano+"'").elementAt(0).toString().equals("No hay elementos"))
      {
      if (!titulo.equals("No hay elementos"))
        {
        //Sentencias SQL
        v_declaracion = "DELETE tbanos_items WHERE ani_foc_pro_codigo = '"+v_codpro+"' AND ani_foc_ano = '"+ano+"' AND ani_itc_codigo = '"+titulo+"'";
        //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
        v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, false);
        //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
        TBPL_Publicar(v_resultadodeclaracion);
        }//------------------------------
      else
        {
        v_resultadodeclaracion.addElement("No puede eliminar este elemento");
        TBPL_Publicar(v_resultadodeclaracion);
        }
      }
    else
      PublicarERROR();
    }
  else if (accion.equals("modifica")&&(titulo!=null))//Si escoge modifica item año realiza la siguiente Consulta
    {
    if (v_Consulta.TBFL_Consulta("SELECT cer_ani_foc_ano FROM tbcertificados WHERE cer_con_pro_codigo = '"+v_codpro+"' AND cer_ani_foc_ano = '"+ano+"'").elementAt(0).toString().equals("No hay elementos"))
      {
      //Realiza una Consulta para verificar si puede modificar o no el item de ano
      //Modifica el item ano
      //----------------------------------
      if (!titulo.equals("No hay elementos"))
        {
        v_declaracion = "SELECT \n"+
                    " ANI_FOC_PRO_CODIGO, \n"+
                    " ANI_FOC_ANO, \n"+
                    " ANI_ITC_CODIGO, \n"+
                    " ANI_TITULO, \n"+
                    " ANI_POSICION \n"+
                    " FROM tbanos_items WHERE ani_foc_ano = '"+ano+"' AND ani_foc_pro_codigo = '"+v_codpro+"' AND ani_itc_codigo = '"+titulo+"'";
        v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
        v_declaracion = "SELECT itc_descripcion FROM tbitems_certificado WHERE itc_codigo ='"+v_resultadodeclaracion.elementAt(2).toString()+"'";
        v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);
        v_resultadodeclaracion.setElementAt(v_resultadodeclaracion1.elementAt(0).toString(),2);
        //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
        PublicarModificar(v_resultadodeclaracion);
        }//-----------------------------------
      else
        {
        v_resultadodeclaracion.addElement("No puede modificar este elemento");
        PublicarModificar(v_resultadodeclaracion);
        }
      }
    else
      PublicarERROR();
    }
  else if (accion.equals("adiciona"))
    {
    if (v_Consulta.TBFL_Consulta("SELECT cer_ani_foc_ano FROM tbcertificados WHERE cer_con_pro_codigo = '"+v_codpro+"' AND cer_ani_foc_ano = '"+ano+"'").elementAt(0).toString().equals("No hay elementos"))
      {
      v_declaracion = "SELECT  itc_codigo, itc_descripcion FROM tbitems_certificado ORDER BY itc_descripcion";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
      PublicarAdicionar(ano, v_codpro, v_resultadodeclaracion);
      }
    else
      PublicarERROR();
    }

  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //publicacion en pagina html de las Consultas
  }

  private void TBPL_Publicar(Vector resultado)
  {
  resultado.trimToSize();
  if (!resultado.elementAt(0).equals("No puede eliminar este elemento"))
    {
    out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de formatos de certificados de retención en la fuente","Administración de formatos de certificados de retención en la fuente", "", "<BLOCKQUOTE>"+resultado.elementAt(0).toString()+"</BLOCKQUOTE>", false));
    out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
    out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
    }
  else
    {
    out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administracion de certificados de retención en la fuente por producto","Administracion de certificados de retención en la fuente por producto", "", "<BLOCKQUOTE>"+resultado.elementAt(0).toString()+"</BLOCKQUOTE>", false));
    out.println("<BR>&nbsp;&nbsp;<BR>");
    out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-2);></CENTER>");
    out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
    }
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }



      private void PublicarModificar(Vector v_descripcion)
      {
      v_descripcion.trimToSize();

    if (!v_descripcion.elementAt(0).equals("No puede modificar este elemento"))
      {

      for (int i = 0; i<v_descripcion.size(); i++)
         if (v_descripcion.elementAt(i).toString().equals("null"))
            v_descripcion.setElementAt(" ", i);

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de formatos de certificados de retención en la fuente","Administración de formatos de certificados de retención en la fuente", "TBPKT_PARAMETROS.TBPKT_FORMATOCERTIFICADO.TBCS_ModificaFormatoCertificadoItemAno", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificar item del formato</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Año del formato:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"<INPUT TYPE=hidden NAME=ano VALUE='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codigo VALUE='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción del item:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"<INPUT TYPE=hidden NAME=itcodigo VALUE='"+v_descripcion.elementAt(2).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Título del item:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><TEXTAREA NAME=obligatoriotitulo ROWS=5 COLS=20>"+v_descripcion.elementAt(3).toString()+"</TEXTAREA></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Posición del item:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"<INPUT TYPE=hidden NAME=dposicion VALUE='"+v_descripcion.elementAt(4).toString()+"'><BR><INPUT TYPE=TEXT MAXLENGTH=30  SIZE=20 NAME=obligatorioposicion VALUE='"+v_descripcion.elementAt(4).toString()+"'></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER><BR>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      }
    else
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administracion de certificados de retención en la fuente por producto","Administracion de certificados de retención en la fuente por producto", "", "<BLOCKQUOTE><BR>"+v_descripcion.elementAt(0).toString()+"</BLOCKQUOTE>", false));
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-2);></CENTER>");
      }
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      private void PublicarAdicionar(String ano, String v_codpro, Vector v_opciones)
      {
      v_opciones.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de formatos de certificados de retención en la fuente", "Administración de formatos de certificados de retención en la fuente","TBPKT_PARAMETROS.TBPKT_FORMATOCERTIFICADO.TBCS_AdicionaFormatoCertificadoItemAno", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adicionar item al formato</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Año del formato:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+ano+"<INPUT TYPE=hidden NAME=ano VALUE='"+ano+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro+"<INPUT TYPE=hidden NAME=v_codigo VALUE='"+v_codpro+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción del item:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=desitemcer>");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Título del item:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><TEXTAREA NAME=obligatoriotitulo ROWS=5 COLS=20></TEXTAREA></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Posición del item:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=TEXT MAXLENGTH=30  SIZE=20 NAME=obligatorioposicion></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      private void PublicarModificarFC(Vector v_descripcion, Vector v_opciones)
      {
      v_descripcion.trimToSize();
      v_opciones.trimToSize();

      for (int i = 0; i<v_descripcion.size(); i++)
         if (v_descripcion.elementAt(i).toString().equals("null"))
            v_descripcion.setElementAt(" ", i);

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de formatos de certificados de retención en la fuente","Administración de formatos de certificados de retención en la fuente", "TBPKT_PARAMETROS.TBPKT_FORMATOCERTIFICADO.TBCS_ModificaFormatoCertificado", "", true, "moduloparametro.js", "return checkrequired(this)"));
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
      //Aqui van los campo que se quieren mostrar
      out.println("<BLOCKQUOTE>");
      out.println("<B>Año del formato:</B>&nbsp;&nbsp;"+v_descripcion.elementAt(1).toString()+"<INPUT TYPE=hidden NAME=ano VALUE='"+v_descripcion.elementAt(1).toString()+"'>"+"<BR>&nbsp;&nbsp;<BR>");
      out.println("<B>Código del producto:</B>&nbsp;&nbsp;"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codigo VALUE='"+v_descripcion.elementAt(0).toString()+"'>"+"<BR>&nbsp;&nbsp;<BR>");
      out.println("<B>Descripción del formato:</B>&nbsp;&nbsp;"+v_descripcion.elementAt(2).toString()+"<INPUT TYPE=hidden NAME=v_ddescripcion VALUE='"+v_descripcion.elementAt(2).toString()+"'><BR><INPUT TYPE=TEXT MAXLENGTH=50  SIZE=40 NAME=v_descripcion VALUE='"+v_descripcion.elementAt(2).toString()+"'>&nbsp;&nbsp;<BR>&nbsp;&nbsp;<BR>");
      out.println("<B>CABEZA del formato:</B>&nbsp;&nbsp;<BR><TEXTAREA NAME=head ROWS=10 COLS=40>"+v_descripcion.elementAt(3).toString()+"</TEXTAREA><BR>&nbsp;&nbsp;<BR>");
      out.println("<B>PIE del formato:</B>&nbsp;&nbsp;<BR><TEXTAREA NAME=foot ROWS=10 COLS=40>"+v_descripcion.elementAt(4).toString()+"</TEXTAREA><BR>&nbsp;&nbsp;<BR>");
      out.println("<B>Estado del formato:</B>&nbsp;&nbsp;"+v_descripcion.elementAt(5).toString()+"&nbsp;&nbsp;<SELECT NAME=estfor>");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         if (v_descripcion.elementAt(5).toString().equals(v_opciones.elementAt(i+1).toString()))
           out.println("<OPTION SELECTED VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         else
           out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT><BR>&nbsp;&nbsp;<BR>");

      out.println("<CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("</BLOCKQUOTE>");
      //Hasta aca van los campos
      out.println("</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

  private void PublicarERROR()
  {
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administracion de certificados de retención en la fuente por producto","Administracion de certificados de retención en la fuente por producto", "", "<BLOCKQUOTE><BR>No puede Modificar ni Eliminar este Formato certificado porque ya han sido generados a partir de el.</BLOCKQUOTE>", false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-2);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_FORMATOCERTIFICADO.TBCL_AEMFormatoCertificado Information";
  }
}

