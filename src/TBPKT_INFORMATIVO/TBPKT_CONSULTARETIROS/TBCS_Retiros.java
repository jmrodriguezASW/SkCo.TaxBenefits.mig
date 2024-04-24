package TBPKT_INFORMATIVO.TBPKT_CONSULTARETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTMLII;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_Retiros extends HttpServlet implements SingleThreadModel{
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
    String Consulta;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadoConsulta, resultadoConsulta1;

    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


    //Toma el c�digo del producto
    String v_codpro = "MFUND";
    //Toma el n�mero de contrato
    String nocon = "";
    //Toma el mes inicial para la Consulta
    String mesini = "";
    //Toma el ano inicial para la Consulta
    String anoini = "";
    //Toma el mes final para la Consulta
    String mesfin = "";
    //Toma el ano final para la Consulta
    String anofin = "";


    try{
       v_codpro = request.getParameter("v_codpro");
       nocon = request.getParameter("nocon");
       mesini = request.getParameter("mesini");
       anoini = request.getParameter("anoini");
       mesfin = request.getParameter("mesfin");
       anofin = request.getParameter("anofin");


       }
    catch (Exception e) { e.printStackTrace(); }


      Consulta = "SELECT ret_consecutivo, to_char(ret_fecha_efectiva, 'yyyy-mm-dd'), \n"+
                       " to_char(ret_fecha_proceso,'yyyy-mm-dd'), \n"+
                       " ret_valor_bruto, ret_valor_neto, \n"+
                       " ret_cot_ref_transaccion, \n"+
                       " ret_cot_ref_tipo_transaccion, \n"+
                       " ret_cot_ref_clase_transaccion, \n"+
                       " ret_banco, ret_cuenta, \n"+
                       " ref_descripcion \n"+
                 " FROM  tbretiros, tbreferencias  \n"+
                 " WHERE ret_con_pro_codigo = ? \n"+
                 " AND   ret_con_numero = ? \n"+
                  "AND  (RET_REF_ESTADO = 'SER006' OR RET_REF_ESTADO = 'SER001')\n"+                 
                 " AND   ret_fecha_efectiva BETWEEN \n"+
                 " to_date('"+mesini+" "+anoini+"', 'MM-YYYY','nls_date_language=ENGLISH') AND  LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-YYYY','nls_date_language=ENGLISH')) \n"+
                 " AND ref_codigo = ret_ref_estado ORDER BY ret_fecha_efectiva ";

      String v_parametros[] = new String[2];
      v_parametros[0] = v_codpro; 
      v_parametros[1] = nocon; 
      v_resultadoConsulta = v_Consulta.TBFL_ConsultaParametros(Consulta, v_parametros);

    TBPL_Publicar(v_resultadoConsulta, v_codpro, nocon);
    v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }

      private void TBPL_Publicar(Vector v_descripcion, String v_codpro, String nocon)
      {
      v_descripcion.trimToSize();

      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        {
        for (int i = 0; i<v_descripcion.size(); i++)
           if (v_descripcion.elementAt(i).toString().equals("null"))
             v_descripcion.setElementAt(" ", i);

        out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Consulta de retiros","Consulta de retiros", "TBPKT_INFORMATIVO.TBPKT_CONSULTARETIROS.TBCS_InformativoEgresos", "", true));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=6 rules=all>");
        out.println("<TR align=middle class=\"td11\" borderColor=silver><TD width=\"5%\"><FONT color=white face=verdana size=1>&nbsp;</font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha efectiva</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha proceso</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Valor bruto</B></font>");
        out.println("</TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Valor neto</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Tipo de egreso</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Banco</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Cuenta</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Estado</B></font></TD></TR>");
        for (int i=0; i<v_descripcion.size(); i+=11)
           {
           if (i<10)
             {
             out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=consecutivo VALUE='"+v_descripcion.elementAt(i).toString()+"' CHECKED></font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+1).toString()+"</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+2).toString());
             out.println("</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+3).toString()))+"</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+4).toString()))+"</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+ConsultaDescripcion(v_descripcion.elementAt(i+5).toString())+"<BR>"+ConsultaDescripcion(v_descripcion.elementAt(i+6).toString())+"<BR>"+ConsultaDescripcion(v_descripcion.elementAt(i+7).toString())+"</font></TD>");
             out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+8).toString()+"</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+9).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+10).toString()+"</font></TD></TR>");
             }
           else
             {
             out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=consecutivo VALUE='"+v_descripcion.elementAt(i).toString()+"'></font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+1).toString()+"</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+2).toString());
             out.println("</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+3).toString()))+"</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+4).toString()))+"</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+ConsultaDescripcion(v_descripcion.elementAt(i+5).toString())+"<BR>"+ConsultaDescripcion(v_descripcion.elementAt(i+6).toString())+"<BR>"+ConsultaDescripcion(v_descripcion.elementAt(i+7).toString())+"</font></TD>");
             out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+8).toString()+"</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+9).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+10).toString()+"</font></TD></TR>");
             }
           }
        out.println("</TABLE>");
        out.println("<INPUT TYPE=hidden NAME=v_codigo VALUE='"+v_codpro+"'>");
        out.println("<INPUT TYPE=hidden NAME=v_Nocontrato VALUE='"+nocon+"'>");
        out.println("<BLOCKQUOTE>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
        out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='modalidad' CHECKED>Informaci�n del retiro<BR>");
        out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='ingresos'>Ingresos que han sido afectados por el retiro<BR>");
        out.println("</font>");
        out.println("</BLOCKQUOTE>");
        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT TYPE=submit VALUE=Consultar><INPUT TYPE=button VALUE=Regresar  ONCLICK=history.go(-1);></font></CENTER>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        out.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
        }
      else
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de retiros","Consulta de retiros", "", "No hay Elementos", false));
        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT TYPE=button VALUE=Regresar  ONCLICK=history.go(-1);></font></CENTER>");
        out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
        }

       out.close();
      }


      private String ConsultaDescripcion (String cod)
      {
      String ret = "";
      //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
      ret = v_Consulta.TBFL_Consulta("SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo ='"+cod+"'", "ref_descripcion").elementAt(0).toString();
      //v_Consulta.TBPL_shutDown();
      return ret;
      }
  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONSULTARETIROS.TBCS_Retiros Information";
  }
}

