package TBPKT_PARAMETROS.TBPKT_PRODUCTOCONCEPTO; 

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_ProductosConceptos extends HttpServlet{
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
        
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
       parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

    v_Consulta = new TBCL_Consulta();
    String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion3;
    Vector v_resultadodeclaracion2 = new Vector();
    Vector v_resultadodeclaracion4 = new Vector();
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


//------------------------------------------

    //Toma el v_codigo del producto
    String v_codpro = "";

    try{
       v_codpro = request.getParameter("v_codpro");
       }
    catch (Exception e) { e.printStackTrace(); }


      v_declaracion = "SELECT \n"+
                    "        prc_cot_ref_transaccion, \n"+
                    "        prc_cot_ref_tipo_transaccion, \n"+
                    "        prc_cot_ref_clase_transaccion, \n"+
                    "        cot_descripcion \n"+
                    "FROM    tbproductos_conceptos, tbconceptos_transaccion \n"+
                    "WHERE   prc_pro_codigo = '"+v_codpro+"' \n"+
                    "AND     prc_cot_ref_transaccion       = cot_ref_transaccion \n"+
                    "AND     prc_cot_ref_tipo_transaccion  = cot_ref_tipo_transaccion \n"+
                    "AND     prc_cot_ref_clase_transaccion = cot_ref_clase_transaccion";
      v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);
      v_resultadodeclaracion1.trimToSize();

      v_declaracion = "select COT_REF_TRANSACCION \n"+
                    " ,COT_REF_TIPO_TRANSACCION \n"+
                    " ,COT_REF_CLASE_TRANSACCION \n"+
                    " ,COT_DESCRIPCION  \n"+
                    " from TBCONCEPTOS_TRANSACCION \n"+
                    " where COT_REF_TRANSACCION||COT_REF_TIPO_TRANSACCION||COT_REF_CLASE_TRANSACCION not in \n"+
                    " (select PRC_COT_REF_TRANSACCION||PRC_COT_REF_TIPO_TRANSACCION||PRC_COT_REF_CLASE_TRANSACCION \n"+
                    " from TBPRODUCTOS_CONCEPTOS \n"+
                    " where PRC_PRO_CODIGO = '"+v_codpro+"')";
      v_resultadodeclaracion3 = v_Consulta.TBFL_Consulta(v_declaracion);
      v_resultadodeclaracion3.trimToSize();

        for (int i=0; i<v_resultadodeclaracion3.size(); i++)
           {
           v_declaracion = "SELECT ref_descripcion  FROM tbreferencias WHERE ref_codigo ='"+v_resultadodeclaracion3.elementAt(i)+"'";
           v_resultadodeclaracion4.add(v_Consulta.TBFL_Consulta(v_declaracion, "ref_descripcion").elementAt(0).toString());
           }


      if (!v_resultadodeclaracion1.elementAt(0).toString().equals("No hay elementos"))
        {
        for (int i=0; i<v_resultadodeclaracion1.size(); i++)
           {
           v_declaracion = "SELECT ref_descripcion  FROM tbreferencias WHERE ref_codigo ='"+v_resultadodeclaracion1.elementAt(i)+"'";
           v_resultadodeclaracion2.add(v_Consulta.TBFL_Consulta(v_declaracion, "ref_descripcion").elementAt(0).toString());
           }
        //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
        TBPL_Publicar(v_codpro, v_resultadodeclaracion1, v_resultadodeclaracion2, v_resultadodeclaracion3, v_resultadodeclaracion4);
        }
      else
        {
        TBPL_Publicar(v_codpro, v_resultadodeclaracion3, v_resultadodeclaracion4);
        }
      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }


/* select COT_REF_TRANSACCION
,COT_REF_TIPO_TRANSACCION
,COT_REF_CLASE_TRANSACCION
,COT_DESCRIPCION
from TBCONCEPTOS_TRANSACCION
where COT_REF_TRANSACCION||COT_REF_TIPO_TRANSACCION||COT_REF_CLASE_TRANSACCION not in
(select PRC_COT_REF_TRANSACCION||PRC_COT_REF_TIPO_TRANSACCION||PRC_COT_REF_CLASE_TRANSACCION
from TBPRODUCTOS_CONCEPTOS
where PRC_PRO_CODIGO = 'MF2')*/



      private void TBPL_Publicar(String v_codpro, Vector v_opciones1, Vector v_opciones2, Vector v_opciones3, Vector v_opciones4)
      {
      v_opciones1.trimToSize();
      v_opciones2.trimToSize();
      v_opciones3.trimToSize();
      v_opciones4.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones por producto","Administración de conceptos de transacciones por producto","TBPKT_PARAMETROS.TBPKT_PRODUCTOCONCEPTO.TBCS_ACEMProductoConcepto", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Conceptos de transacción para&nbsp;"+v_codpro+"<INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_codpro+"'></B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=4 rules=all width=\"100%\">");
      out.println("<TR class=\"td11\" borderColor=silver><TD width=\"5%\"><FONT color=white face=verdana size=1>&nbsp;</font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Tipo de transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Clase de transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Descripción</B></font></TD></TR>");
      for (int i=0; i<v_opciones1.size(); i+=4)
         {
         if (i<3)
           out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=RADIO CHECKED NAME=transac VALUE='"+v_opciones1.elementAt(i).toString()+v_opciones1.elementAt(i+1).toString()+v_opciones1.elementAt(i+2).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones2.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones2.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones2.elementAt(i+2).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones1.elementAt(i+3).toString()+"</font></TD></TR>");
         else
           out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=RADIO NAME=transac VALUE='"+v_opciones1.elementAt(i).toString()+v_opciones1.elementAt(i+1).toString()+v_opciones1.elementAt(i+2).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones2.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones2.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones2.elementAt(i+2).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones1.elementAt(i+3).toString()+"</font></TD></TR>");
         }
      out.println("</TABLE>");
      out.println("<BLOCKQUOTE>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='Consulta' CHECKED>Consultar conceptos de transacciones por producto<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='modifica'>Modificar conceptos de transacciones por producto<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='elimina'>Eliminar conceptos de transacciones por producto<BR>");
      out.println("</font>");
      out.println("</BLOCKQUOTE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Conceptos de transacción para adicionar</B></FONT></CENTER></TD></TR></TBODY></TABLE>");


    if (!(v_opciones3.elementAt(0).toString().startsWith("Hubo algun  error al realizar su transacción, retorne y verifique")||v_opciones3.elementAt(0).toString().equals("No hay elementos")))
      {
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=5 rules=all width=\"100%\">");
      out.println("<TR class=\"td11\" borderColor=silver><TD width=\"5%\"><FONT color=white face=verdana size=1>&nbsp;</font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Tipo de transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Clase de transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Descripcion</B></font></TD></TR>");
      for (int i=0; i<v_opciones3.size(); i+=4)
         if (i<3)
           out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=RADIO NAME=transaca CHECKED VALUE='"+v_opciones3.elementAt(i).toString()+v_opciones3.elementAt(i+1).toString()+v_opciones3.elementAt(i+2).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones4.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones4.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones4.elementAt(i+2).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones3.elementAt(i+3).toString()+"</font></TD></TR>");
         else
           out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=RADIO NAME=transaca VALUE='"+v_opciones3.elementAt(i).toString()+v_opciones3.elementAt(i+1).toString()+v_opciones3.elementAt(i+2).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones4.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones4.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones4.elementAt(i+2).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones3.elementAt(i+3).toString()+"</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BLOCKQUOTE>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adiciona'>Adicionar conceptos de transacciones por producto<BR>");
      out.println("</font>");
      out.println("</BLOCKQUOTE>");
      }


      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      //Hasta aca van los campos
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      private void TBPL_Publicar(String v_codpro, Vector v_opciones3, Vector v_opciones4)
      {
      v_opciones3.trimToSize();
      v_opciones4.trimToSize();


      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones por producto","Administración de conceptos de transacciones por producto","TBPKT_PARAMETROS.TBPKT_PRODUCTOCONCEPTO.TBCS_ACEMProductoConcepto", "", true));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Conceptos de transacción para adicionar en&nbsp;"+v_codpro+"<INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_codpro+"'></B></FONT></CENTER></TD></TR></TBODY></TABLE>");

    if (!(v_opciones3.elementAt(0).toString().startsWith("Hubo algun  error al realizar su transacción, retorne y verifique")||v_opciones3.elementAt(0).toString().equals("No hay elementos")))
      {
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=5 rules=all width=\"100%\">");
      out.println("<TR class=\"td11\" borderColor=silver><TD width=\"5%\"><FONT color=white face=verdana size=1>&nbsp;</font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Tipo de transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Clase de transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Descripción</B></font></TD></TR>");
      for (int i=0; i<v_opciones3.size(); i+=4)
         if (i<3)
           out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=RADIO NAME=transaca CHECKED VALUE='"+v_opciones3.elementAt(i).toString()+v_opciones3.elementAt(i+1).toString()+v_opciones3.elementAt(i+2).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones4.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones4.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones4.elementAt(i+2).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones3.elementAt(i+3).toString()+"</font></TD></TR>");
         else
           out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=RADIO NAME=transaca VALUE='"+v_opciones3.elementAt(i).toString()+v_opciones3.elementAt(i+1).toString()+v_opciones3.elementAt(i+2).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones4.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones4.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones4.elementAt(i+2).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones3.elementAt(i+3).toString()+"</font></TD></TR>");
      out.println("</TABLE>");
      }

      out.println("<BLOCKQUOTE>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adiciona' CHECKED>Adicionar conceptos de transacciones por producto<BR>");
      out.println("</font>");
      out.println("</BLOCKQUOTE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
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
    return "TBPKT_PRODUCTOCONCEPTO.TBCS_ProductosConceptos Information";
  }
}

