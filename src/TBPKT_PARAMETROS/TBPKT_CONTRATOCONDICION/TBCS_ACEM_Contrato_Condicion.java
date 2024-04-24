package TBPKT_PARAMETROS.TBPKT_CONTRATOCONDICION;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_ACEM_Contrato_Condicion extends HttpServlet{
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
    String v_dconsulta;
    Vector v_resultadoconsulta ,v_resultadoconsulta1, v_resultadoconsulta2;
    v_Consulta.TBPL_RealizarConexion();


    //Toma los diferentes codigos de producto
    String v_codpro = "";
    String v_contrato = "";
    int v_contratolength = 0;

    try{
       v_codpro = request.getParameter("v_codpro");
       v_contrato = request.getParameter("obligatoriocontrato").trim();
       /*Cambio para manejo de referencia unica 2009/04/01 MOS */
       v_contrato = v_Consulta.TBFL_ConsultaSinRefUnica(v_contrato);
       }
    catch (Exception e) { e.printStackTrace(); }

    v_contratolength = v_contrato.length();

 switch (v_contratolength)
       {
       case 1:
           v_contrato = "00000000"+v_contrato;
       break;
       case 2:
           v_contrato = "0000000"+v_contrato;
       break;
       case 3:
           v_contrato = "000000"+v_contrato;
       break;
       case 4:
           v_contrato = "00000"+v_contrato;
       break;
       case 5:
           v_contrato = "0000"+v_contrato;
       break;
       case 6:
           v_contrato = "000"+v_contrato;
       break;
       case 7:
           v_contrato = "00"+v_contrato;
       break;
       case 8:
           v_contrato = "0"+v_contrato;
       break;
       default:
           v_contrato = v_contrato;
       break;
       }


       if ((v_contrato.indexOf("'")==-1))
         {
         v_dconsulta = "SELECT CON_NOMBRES, CON_APELLIDOS \n"+
                    " FROM   tbcontratos \n"+
                    " WHERE CON_PRO_CODIGO = '"+v_codpro+"' \n"+
                    " AND CON_NUMERO = '"+v_contrato+"'";
         v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);

/*SELECT   COP_CODIGO, COP_DESCRIPCION, COP_CANTIDAD_TIEMPO, COP_REF_UNIDAD_TIEMPO, COP_PORCENTAJE
FROM tbcondiciones_penalizacion
WHERE COP_CODIGO = 'CON001';*/


/*select coo_con_pro_codigo
      ,coo_con_numero
      ,cop_codigo
      ,cop_descripcion
      ,COP_CANTIDAD_TIEMPO
      ,COP_REF_UNIDAD_TIEMPO
      ,REF_DESCRIPCION
      ,COP_PORCENTAJE
from  tbcontratos_condiciones
     ,tbcondiciones_penalizacion
     ,tbreferencias
where cop_ref_unidad_tiempo = ref_codigo
and coo_cop_codigo (+)  = cop_codigo
and coo_con_numero (+) = '000003285'
*/



         v_dconsulta = "SELECT COO_COP_CODIGO, COP_DESCRIPCION, COP_CANTIDAD_TIEMPO, COP_REF_UNIDAD_TIEMPO, COP_PORCENTAJE \n"+
                    " FROM tbcontratos_condiciones, tbcondiciones_penalizacion \n"+
                    " WHERE COO_COP_CODIGO = COP_CODIGO \n"+
                    " AND COO_CON_NUMERO = '"+v_contrato+"' \n"+
                    " AND COO_CON_PRO_CODIGO = '"+v_codpro+"'";
         v_resultadoconsulta1 = v_Consulta.TBFL_Consulta(v_dconsulta);

         v_dconsulta = "SELECT COP_CODIGO, COP_DESCRIPCION, COP_CANTIDAD_TIEMPO, COP_REF_UNIDAD_TIEMPO, COP_PORCENTAJE \n"+
                    " FROM tbcondiciones_penalizacion \n"+
                    " WHERE COP_CODIGO \n"+
                    " NOT IN \n"+
                    " (SELECT COO_COP_CODIGO \n"+
                    " FROM    tbcontratos_condiciones \n"+
                    " WHERE   COO_CON_NUMERO = '"+v_contrato+"' \n"+
                    " AND     COO_CON_PRO_CODIGO = '"+v_codpro+"') ";
         v_resultadoconsulta2 = v_Consulta.TBFL_Consulta(v_dconsulta);
         TBPL_Publicar(v_codpro, v_contrato, v_resultadoconsulta, v_resultadoconsulta1, v_resultadoconsulta2);
         }
       else
         {
         TBPL_PublicarError();
         }
      v_Consulta.TBPL_shutDown();
  }


      private void TBPL_Publicar(String v_codpro, String v_contrato, Vector v_descripcion, Vector v_codcondpen, Vector v_codcondpena)
      {
      v_descripcion.trimToSize();
      v_codcondpen.trimToSize();
      v_codcondpena.trimToSize();

      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de condiciones de penalización para contratos","Administración de condiciones de penalización para contratos", "TBPKT_PARAMETROS.TBPKT_CONTRATOCONDICION.TBCS_AE_Contrato_condicion", "", true));
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
        out.println("<BLOCKQUOTE>");
        /*Cambio para manejo de referencia unica 2009/04/01 MOS */
        String v_contrato_unif = "";
        v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_codpro, v_contrato);
        
        out.println("<B>Código del producto:</B>&nbsp;&nbsp;"+v_codpro+"<INPUT TYPE=hidden NAME=v_codpro value='"+v_codpro+"'><BR>");
        out.println("<B>Número del contrato:</B>&nbsp;&nbsp;"+v_contrato_unif+"<INPUT TYPE=hidden NAME=v_contrato value='"+v_contrato+"'><BR>");
        out.println("</BLOCKQUOTE>");
        out.println("</font>");

        if (!v_codcondpen.elementAt(0).toString().equals("No hay elementos"))
          {
          out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                      "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Condiciones de penalización para:<BR>"+v_descripcion.elementAt(0).toString()+"&nbsp;"+v_descripcion.elementAt(1).toString()+"</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
          out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=4 rules=all width=\"100%\">");
          out.println("<TR class=\"td11\" borderColor=silver><TH width=\"5%\">&nbsp;</TH><TH width=\"22%\"><FONT color=white face=verdana size=1><B>Descripción</B></font></TH><TH width=\"22%\"><FONT color=white face=verdana size=1><B>Cantidad de tiempo</B></font></TH><TH width=\"22%\"><FONT color=white face=verdana size=1><B>Porcentaje</B></font></TH></TR>");
          for (int i=0; i<v_codcondpen.size(); i+=5)
             out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=checkbox NAME=v_codcondpen VALUE='"+v_codcondpen.elementAt(i).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codcondpen.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codcondpen.elementAt(i+2).toString()+"&nbsp;"+TBFL_ConsultaDescripcion(v_codcondpen.elementAt(i+3).toString())+"</font><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codcondpen.elementAt(i+4).toString()+"&nbsp;%</font></TD></TD></TR>");
          out.println("</TABLE>");
          out.println("<BLOCKQUOTE>");
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
          out.println("<INPUT TYPE=radio NAME=opcion VALUE=elimina>Eliminar condición de penalización");
          out.println("</font>");
          out.println("</BLOCKQUOTE>");
          }

         out.println("<BR>&nbsp;&nbsp;<BR>");

        if (!v_codcondpena.elementAt(0).toString().equals("No hay elementos"))
          {
          out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                      "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Condiciones de penalizacíón para adicionar</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
          out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=4 rules=all width=\"100%\">");
          out.println("<TR class=\"td11\" borderColor=silver><TH width=\"5%\">&nbsp;</TH><TH width=\"22%\"><FONT color=white face=verdana size=1><B>Descripción</B></font></TH><TH width=\"22%\"><FONT color=white face=verdana size=1><B>Cantidad de tiempo</B></font></TH><TH width=\"22%\"><FONT color=white face=verdana size=1><B>Porcentaje</B></font></TH></TR>");
          for (int i=0; i<v_codcondpena.size(); i+=5)
             out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=checkbox NAME=v_codcondpena VALUE='"+v_codcondpena.elementAt(i).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codcondpena.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codcondpena.elementAt(i+2).toString()+"&nbsp;"+TBFL_ConsultaDescripcion(v_codcondpena.elementAt(i+3).toString())+"</font><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codcondpena.elementAt(i+4).toString()+"&nbsp;%</font></TD></TD></TR>");
          out.println("</TABLE>");
          out.println("<BLOCKQUOTE>");
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
          out.println("<INPUT TYPE=radio NAME=opcion VALUE=adiciona>Adicionar condición de penalización");
          out.println("</font>");
          out.println("</BLOCKQUOTE>");
          }
        out.println("<INPUT TYPE=hidden NAME=opcion VALUE=seleccion>");
        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
        }
      else
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de condiciones de penalización para contratos","Administración de condiciones de penalización para contratos", "", "</BLOCKQUOTE>No existe este contrato</BLOCKQUOTE>", false));
        out.println("<BR>&nbsp;&nbsp;</BR>");
        out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
        }
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      private void TBPL_PublicarError()
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de condiciones de penalización para contratos","Administración de condiciones de penalización para contratos", "", "<BLOCKQUOTE>Comilla sencilla no es un caractér valido</BLOCKQUOTE>", false));
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

  private String TBFL_ConsultaDescripcion(String cod)
  {
  String declaracion = "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+cod+"'";
  Vector resultadodeclaracion = v_Consulta.TBFL_Consulta(declaracion, "ref_descripcion");
  if (resultadodeclaracion.elementAt(0).toString().equals("No hay elementos"))
    resultadodeclaracion.setElementAt(" ", 0);
  return (resultadodeclaracion.elementAt(0).toString());
  }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONTRATOCONDICION.TBCS_ACEM_Contrato_Condicion Information";
  }
}

