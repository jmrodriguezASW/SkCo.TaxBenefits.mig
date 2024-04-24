package TBPKT_PARAMETROS.TBPKT_REFERENCIASISTEMA;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_ACEMReferenciasistema extends HttpServlet{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
private String titulo;
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
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

    v_Consulta = new TBCL_Consulta();
    String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2, v_resultadodeclaracion3, v_resultadodeclaracion4, v_resultadodeclaracion5, v_resultadodeclaracion6, v_resultadodeclaracion7, v_resultadodeclaracion8, v_resultadodeclaracion9, v_resultadodeclaracion10;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    //Toma la opción de modificar, eliminar, adicionar o Consultar
    String v_opcion = "Consulta";
    //Toma los diferentes codigos de producto
    String v_codigo = "";

    try{
       v_opcion = request.getParameter("v_opcion");
       v_codigo = request.getParameter("v_codigo");
       }
    catch (Exception e) { e.printStackTrace(); }

    v_codigo = v_codigo.substring(0,3);

    v_declaracion = "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE '"+v_codigo+"%' AND ref_codigo = '"+v_codigo+"000'";
    titulo=v_Consulta.TBFL_Consulta(v_declaracion).elementAt(0).toString();


   if (v_opcion.equals("Consulta"))
     {
     v_declaracion = "SELECT "+
                   "ref_codigo, "+
                   "ref_descripcion, "+
                   "ref_valor "+
                   "FROM tbreferencias WHERE ref_codigo LIKE '"+v_codigo+"%' AND ref_codigo > '"+v_codigo+"000'";


      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarConsulta(v_resultadodeclaracion);
     }
   else if (v_opcion.equals("modifica"))
     {
     v_declaracion = "SELECT "+
                   "ref_codigo,"+
                   "ref_descripcion, "+
                   "ref_valor "+
                   "FROM tbreferencias WHERE ref_codigo LIKE '"+v_codigo+"%' AND ref_codigo > '"+v_codigo+"000'";


      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarModifica(v_resultadodeclaracion);
     }
   else if (v_opcion.equals("adiciona"))
     {
     TBPL_PublicarAdiciona(v_codigo);
     }
   else if (v_opcion.equals("elimina"))
     {
     v_declaracion = "SELECT "+
                   "ref_codigo,"+
                   "ref_descripcion, "+
                   "ref_valor "+
                   "FROM tbreferencias WHERE ref_codigo LIKE '"+v_codigo+"%' AND ref_codigo > '"+v_codigo+"000'";


      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarElimina(v_resultadodeclaracion);
     }
  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }


      private void TBPL_PublicarConsulta(Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      for (int i = 0; i<v_descripcion.size(); i++)
         if (v_descripcion.elementAt(i).toString().equals("null")||v_descripcion.elementAt(i).toString().equals("No hay elementos"))
            v_descripcion.setElementAt(" ", i);

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de referencias de sistema","Administración de referencias de sistema", "", "", true, "moduloparametro.js", "return checkrequired(this)"));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Consulta de referencias de&nbsp;"+titulo+"</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=3 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TH width=\"22%\"><FONT color=black face=verdana size=1>Código</TH><TH width=\"22%\"><FONT color=black face=verdana size=1>Descripción</TH><TH width=\"22%\"><FONT color=black face=verdana size=1>Valor</font></TH></TR>");
      int i = -1;
      while (i+3<v_descripcion.size())
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(++i).toString()+
                    "</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(++i).toString()+
                    "</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(++i).toString()+"</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }




      private void TBPL_PublicarModifica(Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      for (int i = 0; i<v_descripcion.size(); i++)
         if (v_descripcion.elementAt(i).toString().equals("null")||v_descripcion.elementAt(i).toString().equals("No hay elementos"))
            v_descripcion.setElementAt(" ", i);

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de referencias de sistema","Administración de referencias sistema","TBPKT_PARAMETROS.TBPKT_REFERENCIASISTEMA.TBCS_ConfirmaModificaReferenciaSistema","",true, "moduloparametro.js", "return checkrequired(this)"));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificación de referencias de&nbsp;"+titulo+"</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=4 rules=all width=\"100%\">");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TH width=\"5%\"><FONT color=black face=verdana size=1>&nbsp;</TH><TH width=\"22%\"><FONT color=black face=verdana size=1>Código</TH><TH width=\"22%\"><FONT color=black face=verdana size=1>Descripción</TH><TH width=\"22%\"><FONT color=black face=verdana size=1>Valor</font></TH></TR>");
      int i = -1;
      while (i+3<v_descripcion.size())
        if (i<2)
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=v_codigo VALUE='"+v_descripcion.elementAt(++i).toString()+"' CHECKED>"+
                    "</font></TD><TD width=\"5%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+
                    "</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(++i).toString()+
                    "</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(++i).toString()+"</font></TD></TR>");
        else
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=v_codigo VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+
                    "</font></TD><TD width=\"5%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+
                    "</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(++i).toString()+
                    "</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(++i).toString()+"</font></TD></TR>");
     out.println("</TABLE>");
     out.println("<BR>&nbsp;&nbsp;<BR>");
     out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
     out.println("<CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
     //Hasta aca van los campos
     out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
     out.close();
     }


      private void TBPL_PublicarAdiciona(String v_codigo)
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de referencias de sistema", "Administración de referencias de sistema","TBPKT_PARAMETROS.TBPKT_REFERENCIASISTEMA.TBCS_AdicionaReferenciaSistema", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adición de referencias de&nbsp;"+titulo+"</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=obligatoriocodigo MAXLENGTH=6 SIZE=6>&nbsp;Valores entre:&nbsp;"+v_codigo+"001-"+v_codigo+"999</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Descripción:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=obligatoriodescripcion MAXLENGTH=50 SIZE=50></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=v_valor MAXLENGTH=50 SIZE=50></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



      private void TBPL_PublicarElimina(Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      for (int i = 0; i<v_descripcion.size(); i++)
         if (v_descripcion.elementAt(i).toString().equals("null")||v_descripcion.elementAt(i).toString().equals("No hay elementos"))
            v_descripcion.setElementAt(" ", i);

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de referencias de sistema","Administración de referencias de sistema", "TBPKT_PARAMETROS.TBPKT_REFERENCIASISTEMA.TBCS_EliminaReferenciaSistema", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");

      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Eliminación de referencias de&nbsp;"+titulo+"</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=4 rules=all width=\"100%\">");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TH width=\"5%\"><FONT color=black face=verdana size=1>&nbsp;</TH><TH width=\"22%\"><FONT color=black face=verdana size=1>Código</TH><TH width=\"22%\"><FONT color=black face=verdana size=1>Descripción</TH><TH width=\"22%\"><FONT color=black face=verdana size=1>Valor</font></TH></TR>");
      int i = -1;
      while (i+3<v_descripcion.size())
        if (i<2)
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=v_codigo VALUE='"+v_descripcion.elementAt(++i).toString()+"' CHECKED>"+
                    "</font></TD><TD width=\"5%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+
                    "</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(++i).toString()+
                    "</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(++i).toString()+"</font></TD></TR>");
         else
           out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=v_codigo VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+
                    "</font></TD><TD width=\"5%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+
                    "</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(++i).toString()+
                    "</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(++i).toString()+"</font></TD></TR>");
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
    return "TBPKT_REFERENCIASISTEMA.TBCS_ACEMReferenciasistema Information";
  }
}

