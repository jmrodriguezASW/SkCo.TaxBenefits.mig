package TBPKT_PARAMETROS.TBPKT_REFERENCIASUSUARIO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_ACEMReferenciasUsuario extends HttpServlet{
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
       //response.setDateHeader("Expires", 0);       String parametros[] = new String[8];
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
     v_declaracion = "SELECT "+
                   "trim(to_char(max(to_number(substr(ref_codigo,4,3))) + 1,'000'))"+
                   "FROM tbreferencias WHERE ref_codigo LIKE '"+v_codigo+"%' AND ref_codigo > '"+v_codigo+"000'";

     v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
     v_resultadodeclaracion.trimToSize();
//     TBPL_PublicarAdiciona(v_codigo,v_resultadodeclaracion.size()+1);
     TBPL_PublicarAdiciona(v_codigo,v_resultadodeclaracion.elementAt(0).toString());
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

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de referencias de usuario","Administración de referencias de usuario", "", "", true, "moduloparametro.js", "return checkrequired(this)"));

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

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de referencias de usuario","Administración de referencias de usuario","TBPKT_PARAMETROS.TBPKT_REFERENCIASUSUARIO.TBCS_ConfirmaModificaReferenciaUsuario","",true, "moduloparametro.js", "return checkrequired(this)"));

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
     out.println("<INPUT NAME=titulo TYPE=hidden VALUE='"+titulo+"'>");
     out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
     out.println("<CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
     //Hasta aca van los campos
     out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
     out.close();
     }


      private void TBPL_PublicarAdiciona(String v_codigo, String num)
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de referencias de usuario", "Administración de referencias de usuario","TBPKT_PARAMETROS.TBPKT_REFERENCIASUSUARIO.TBCS_AdicionaReferenciaUsuario", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      v_codigo = v_codigo + num;
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adición de referencias de&nbsp;"+titulo+"</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codigo+"<INPUT TYPE=hidden NAME=obligatoriocodigo MAXLENGTH=6 SIZE=6 VALUE='"+v_codigo+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Descripción:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=obligatoriodescripcion MAXLENGTH=50 SIZE=50></font></TD></TR>");
      if (v_codigo.startsWith("UDE")||v_codigo.startsWith("UDS"))
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=v_valor MAXLENGTH=50 SIZE=50><BR>Introducir valores entre 0 y 100</font></TD></TR>");
      else
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=v_valor MAXLENGTH=50 SIZE=50></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      //Hasta aca van los campos

      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



      private void TBPL_PublicarElimina(Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de referencias de usuario","Administración de referencias de usuario", "TBPKT_PARAMETROS.TBPKT_REFERENCIASUSUARIO.TBCS_EliminaReferenciaUsuario", "", true, "moduloparametro.js", "return checkrequired(this)"));
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

/*      private void TBPL_PublicarConsulta(Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      out.println(TBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de referencias de usuario","Administración de referencias de usuario", "", "", true, "moduloparametro.js", "return checkrequired(this)"));

      //Aqui van los campo que se quieren mostrar
      out.println("<TABLE BORDER COLS=3>");
      out.println("<TR><TH><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>Codigo</TH><TH><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>Descripción</TH><TH><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>Valor</TH></TR>");
      int i = -1;
      while (i+3<v_descripcion.size())
        out.println("<TR><TD><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD>"+
                    "<TD><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD>"+
                    "<TD><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-2);></CENTER>");
      //Hasta aca van los campos
      out.println(TBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }*/



  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_REFERENCIASUSUARIO.TBCS_ACEMReferenciasUsuario Information";
  }
}

