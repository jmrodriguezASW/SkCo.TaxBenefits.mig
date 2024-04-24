package TBPKT_PARAMETROS.TBPKT_EMPRESAS_CONTRATOS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_ACEM_Empresas_Contratos extends HttpServlet implements SingleThreadModel{
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
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}
    v_Consulta = new TBCL_Consulta();
    String v_declaracion;
    //Vector donde se guardara el resultado de la v_declaracion
    Vector v_resultadodeclaracion, v_resultadodeclaracion1;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    //Toma la opción de modificar, eliminar, adicionar o Consultar
    String v_opcion = "";
    //Toma los diferentes codigos de producto
    String v_codpro = "";
    String grupoempresa = "";
    String empresaa = "";
    String contrato = "";

    try{
       v_opcion = request.getParameter("v_opcion");
       v_codpro = request.getParameter("v_codpro");
       grupoempresa = request.getParameter("grupo");
       empresaa = request.getParameter("empresa");//Solo lo utilizo para adicionar
       contrato = request.getParameter("contrato");
       }
    catch (Exception e) { e.printStackTrace(); }

   if (v_opcion.equals("adiciona"))
     {
     v_declaracion = "SELECT ref_codigo, ref_descripcion \n"+
                   " FROM tbreferencias \n"+
                   " WHERE ref_codigo LIKE 'UDE%' AND ref_codigo != 'UDE000'";
     v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

     v_declaracion = "SELECT   emp_grupo, emp_descripcion \n"+
                   "FROM     tbempresas \n"+
                   "WHERE    emp_grupo \n"+
                   "NOT IN   ( \n"+
                   "   SELECT   emc_emp_grupo \n"+
                   "   FROM     tbempresas_contratos \n"+
                   "   WHERE    emc_con_numero = '"+contrato+"' \n"+
                   "   AND      emc_con_pro_codigo = '"+v_codpro+"') \n"+
                   "AND      emp_descripcion \n"+
                   "LIKE     UPPER('%"+empresaa+"%') \n"+
                   "ORDER BY emp_descripcion";
     v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);
     
     //publicacion en pagina html de las Consultas
     TBPL_PublicarAdiciona(v_codpro, contrato, v_resultadodeclaracion, v_resultadodeclaracion1);
     }
   else if (v_opcion.equals("elimina"))
     {
     v_declaracion = "SELECT emp_descripcion FROM tbempresas WHERE emp_grupo = '"+grupoempresa+"'";
     String empresa = v_Consulta.TBFL_Consulta(v_declaracion, "emp_descripcion").elementAt(0).toString();

     v_declaracion = "SELECT  \n"+
                   " ref_descripcion, \n"+
                   " emc_detalle_condicion \n"+
                   " FROM tbreferencias, tbempresas_contratos \n"+
                   " WHERE emc_emp_grupo = '"+grupoempresa+"' \n"+
                   " AND emc_con_pro_codigo = '"+v_codpro+"' \n"+
                   " AND emc_con_numero = '"+contrato+"' \n"+
                   " AND emc_ref_condicion = ref_codigo";
     v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

     //publicacion en pagina html de las Consultas
     TBPL_PublicarElimina(empresa, grupoempresa, v_codpro, contrato, v_resultadodeclaracion);
     }
   else if (v_opcion.equals("modifica"))
     {
     v_declaracion = "SELECT emp_descripcion FROM tbempresas WHERE emp_grupo = '"+grupoempresa+"'";
     String empresa = v_Consulta.TBFL_Consulta(v_declaracion, "emp_descripcion").elementAt(0).toString();

     v_declaracion = "SELECT  \n"+
                   " ref_descripcion, \n"+
                   " emc_detalle_condicion \n"+
                   " FROM tbreferencias, tbempresas_contratos \n"+
                   " WHERE emc_emp_grupo = '"+grupoempresa+"' \n"+
                   " AND emc_con_pro_codigo = '"+v_codpro+"' \n"+
                   " AND emc_con_numero = '"+contrato+"' \n"+
                   " AND emc_ref_condicion = ref_codigo";
     v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

     v_declaracion = "SELECT ref_codigo, ref_descripcion \n"+
                   " FROM tbreferencias \n"+
                   " WHERE ref_codigo LIKE 'UDE%' AND ref_codigo != 'UDE000'";
     v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);

     //publicacion en pagina html de las Consultas
     TBPL_PublicarModifica(empresa, grupoempresa, v_codpro, contrato, v_resultadodeclaracion, v_resultadodeclaracion1);
     }
    v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }



      private void TBPL_PublicarAdiciona(String v_codpro, String contrato, Vector v_opciones, Vector v_opciones1)
      {
      v_opciones.trimToSize();

      if (!v_opciones1.elementAt(0).equals("No hay elementos"))
         {
         out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas contratos","Administración de empresas contratos", "TBPKT_PARAMETROS.TBPKT_EMPRESAS_CONTRATOS.TBCS_Adiciona_Empresa_Contrato", "", true));
         out.println("<BR>&nbsp;&nbsp;<BR>");
         out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                     "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adicionar empresas contratos</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
         out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro+"<INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_codpro+"'></font></TD></TR>");
         /*Cambio para manejo de referencia unica 2009/04/01 MOS */
        String v_contrato_unif = "";
        //v_Consulta.TBPL_RealizarConexion();
        v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_codpro, contrato);
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Número de contrato</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_contrato_unif+"<INPUT TYPE=hidden NAME=contrato VALUE='"+contrato+"'></font></TD></TR>");
         //v_Consulta.TBPL_shutDown();
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Nombre de la empresa:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=grupo>");
         for (int i=0; i<v_opciones1.size(); i+=2)
            {
            out.println("<OPTION VALUE='"+v_opciones1.elementAt(i).toString()+"'>"+v_opciones1.elementAt(i+1).toString());
            }
         out.println("</SELECT></font></TD></TR>");

         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Condición empresa contrato</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=condempcont>");
         out.println("<OPTION VALUE='null'>");
         for (int i=0; i<v_opciones.size(); i+=2)
            {
            out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
            }
         out.println("</SELECT></font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Detalle condición empresa contrato</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><TEXTAREA NAME=detcond ROWS=6 COLS=30></TEXTAREA></font></TD></TR>");
         out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
         }
      else
         {
         out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas contratos","Administración de empresas contratos", "", "No hay empresas para adicionar", false));
         out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
         }
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      private void TBPL_PublicarElimina(String empresa, String grupoempresa, String v_codpro, String contrato, Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas contratos","Administración de empresas contratos", "TBPKT_PARAMETROS.TBPKT_EMPRESAS_CONTRATOS.TBCS_Elimina_Empresa_Contrato", "", true));
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Eliminar empresas contratos</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Nombre de empresa</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+empresa+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Grupo de empresa</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+grupoempresa+"<INPUT TYPE=hidden NAME=grupo VALUE='"+grupoempresa+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro+"<INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_codpro+"'></font></TD></TR>");
      /*Cambio para manejo de referencia unica 2009/04/01 MOS */
      //v_Consulta.TBPL_RealizarConexion();
      String v_contrato_unif = "";
      v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_codpro, contrato);
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Número de contrato</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_contrato_unif+"<INPUT TYPE=hidden NAME=contrato VALUE='"+contrato+"'></font></TD></TR>");
      //v_Consulta.TBPL_shutDown();
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR class=\"td11\" borderColor=silver><TH width=\"22%\"><FONT color=white face=verdana size=1>Condición empresa contrato</font></TH><TH width=\"22%\"><FONT color=white face=verdana size=1>Detalle condición empresa contrato</font></TH></TR>");
      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        for (int i=0; i<v_descripcion.size(); i+=2)
           out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+1)+"</font></TD></TR>");
      out.println("</TABLE>");

      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Eliminar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      private void TBPL_PublicarModifica(String empresa, String grupoempresa, String v_codpro, String contrato, Vector v_descripcion, Vector v_opciones)
      {
      v_descripcion.trimToSize();
      v_opciones.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas contratos","Administración de empresas contratos", "TBPKT_PARAMETROS.TBPKT_EMPRESAS_CONTRATOS.TBCS_Modifica_Empresa_Contrato", "", true));
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificar empresas contratos</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Nombre de empresa</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+empresa+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Grupo de empresa</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+grupoempresa+"<INPUT TYPE=hidden NAME=grupo VALUE='"+grupoempresa+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro+"<INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_codpro+"'></font></TD></TR>");
      /*Cambio para manejo de referencia unica 2009/04/01 MOS */
      //v_Consulta.TBPL_RealizarConexion();
      String v_contrato_unif = "";
      v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_codpro, contrato);
      //v_Consulta.TBPL_shutDown();
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Número de contrato</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_contrato_unif+"<INPUT TYPE=hidden NAME=contrato VALUE='"+contrato+"'></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR class=\"td11\" borderColor=silver><TH width=\"22%\"><FONT color=white face=verdana size=1>Condición empresa contrato</font></TH><TH width=\"22%\"><FONT color=white face=verdana size=1>Detalle condición empresa contrato</font></TH></TR>");
      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        for (int i=0; i<v_descripcion.size(); i+=2)
           {
           out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"<BR>");
           out.println("<SELECT NAME=condempcont>");
           out.println("<OPTION VALUE='null'>");           
           for (int j=0; j<v_opciones.size(); j+=2)
              {
              if (v_descripcion.elementAt(i).toString().equals(v_opciones.elementAt(j+1).toString()))
                out.println("<OPTION SELECTED VALUE='"+v_opciones.elementAt(j).toString()+"'>"+v_opciones.elementAt(j+1).toString());
              else
                out.println("<OPTION VALUE='"+v_opciones.elementAt(j).toString()+"'>"+v_opciones.elementAt(j+1).toString());
              }
           out.println("</SELECT>");
           out.println("</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+1)+"<BR><TEXTAREA NAME=detcond ROWS=6 COLS=30>"+v_descripcion.elementAt(i+1)+"</TEXTAREA>");
           }
      else
        {
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>No hay elementos<BR>");
        out.println("<SELECT NAME=condempcont>");
        out.println("<OPTION VALUE='null'>");
        for (int j=0; j<v_opciones.size(); j+=2)
           out.println("<OPTION VALUE='"+v_opciones.elementAt(j).toString()+"'>"+v_opciones.elementAt(j+1).toString());
        out.println("</SELECT>");
        out.println("</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><BR><TEXTAREA NAME=detcond ROWS=6 COLS=30></TEXTAREA>");
        }
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");      
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }




  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_EMPRESAS_CONTRATOS.TBCS_ACEM_Empresas_Contratos Information";
  }
}

