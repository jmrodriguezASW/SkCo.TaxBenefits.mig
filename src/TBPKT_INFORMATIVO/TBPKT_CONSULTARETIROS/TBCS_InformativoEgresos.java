package TBPKT_INFORMATIVO.TBPKT_CONSULTARETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_InformativoEgresos extends HttpServlet{
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
    String Consulta;

    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadoConsulta, resultadoConsulta1;

    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


    //Toma el consecutivo
    String consecutivo = "";
    String v_opcion = "";
    String v_codigo = "";
    String v_Nocontrato = "";

    try{
       consecutivo = request.getParameter("consecutivo");
       v_codigo = request.getParameter("v_codigo");
       v_Nocontrato = request.getParameter("v_Nocontrato");
       v_opcion = request.getParameter("v_opcion");
       }
    catch (Exception e) { e.printStackTrace(); }


    if (v_opcion.equals("modalidad"))
      {
      Consulta = "SELECT  ret_transaccion, \n"+
                 "        ret_con_pro_codigo_ti, \n"+
                 "        ret_con_numero_ti, \n"+
                 "        afp_descripcion, \n"+
                 "        a.ref_descripcion, \n"+
                 "        b.ref_descripcion, \n"+
                 "        c.ref_descripcion, \n"+
                 "        d.ref_descripcion, \n"+
                 "        e.ref_descripcion \n"+
                 "FROM   tbretiros, \n"+
                 "       tbadm_fondos_pensiones, \n"+
                 "       tbreferencias a, \n"+
                 "       tbreferencias b, \n"+
                 "       tbreferencias c, \n"+
                 "       tbreferencias d, \n"+
                 "       tbreferencias e \n"+
                 "WHERE  RET_CON_PRO_CODIGO = ? \n"+
                 "  AND  RET_CON_NUMERO = ? \n"+
                 "  AND  ret_consecutivo = ? \n"+
                 "  AND  RET_REF_METODO_ORDEN = a.ref_codigo (+) \n"+
                 "  AND  RET_REF_METODO_BENEFICIO = b.ref_codigo (+) \n"+
                 "  AND  RET_REF_METODO_PENALIZACION = c.ref_codigo (+) \n"+
                 "  AND  RET_REF_METODO_CUENTA = d.ref_codigo (+) \n"+
                 "  AND  RET_REF_NATURALEZA = e.ref_codigo (+)\n"+
                 "  AND  ret_afp_codigo  =   afp_codigo (+) ";

      String v_parametros[] = new String[3];
      v_parametros[0] = v_codigo; 
      v_parametros[1] = v_Nocontrato; 
      v_parametros[2] = consecutivo; 

      v_resultadoConsulta = v_Consulta.TBFL_ConsultaParametros(Consulta, v_parametros);
//      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      PublicarModalidad(v_resultadoConsulta, consecutivo);
      }
     else if (v_opcion.equals("ingresos"))
      {
      Consulta = "SELECT ret_valor_neto \n"+
                 " FROM  tbretiros WHERE \n"+
                 " ret_con_pro_codigo = ? \n"+
                 " AND ret_con_numero = ? \n"+
                 " AND ret_consecutivo = ? ";

      String v_parametros[] = new String[3];
      v_parametros[0] = v_codigo; 
      v_parametros[1] = v_Nocontrato; 
      v_parametros[2] = consecutivo; 

      String ValorNeto = v_Consulta.TBFL_ConsultaParametros(Consulta, v_parametros, "ret_valor_neto").elementAt(0).toString();
      //String ValorNeto = v_Consulta.TBFL_Consulta(Consulta, "ret_valor_neto").elementAt(0).toString();

      Consulta =" SELECT  apr_apo_consecutivo,\n"+
                "         to_char(apo_fecha_efectiva, 'RRRR-MM-DD'),\n"+
                "         apr_capital,\n"+
                "         apr_rendimientos,\n"+
                "         NVL(SUM(TBFBD_CARGOS_RETIROS(apr_ret_con_pro_codigo , apr_ret_con_numero, apr_ret_consecutivo, apr_apo_consecutivo, 'STC001')),0), \n"+
                "         NVL(SUM(TBFBD_CARGOS_RETIROS(apr_ret_con_pro_codigo , apr_ret_con_numero, apr_ret_consecutivo, apr_apo_consecutivo, 'STC002')),0), \n"+
                "         (NVL(SUM(TBFBD_CARGOS_RETIROS(apr_ret_con_pro_codigo , apr_ret_con_numero, apr_ret_consecutivo, apr_apo_consecutivo, 'STC003')),0)+ NVL(SUM(TBFBD_CARGOS_RETIROS(apr_ret_con_pro_codigo , apr_ret_con_numero, apr_ret_consecutivo, apr_apo_consecutivo, 'STC004')),0)) \n"+
                "   FROM  tbaportes_retiros,\n"+
                "         tbaportes\n"+
                "  WHERE  apr_ret_con_pro_codigo = ? \n"+
                "    AND  apr_ret_con_numero =  ? \n"+
                "    AND  apr_ret_consecutivo = ? \n"+
                "    AND  apr_apo_consecutivo  = apo_consecutivo   \n"+
                "    AND  apr_ret_con_pro_codigo = apo_con_pro_codigo   \n"+
                "    AND  apr_ret_con_numero   = apo_con_numero   \n"+
               " GROUP BY apr_apo_consecutivo,\n"+
               "          apo_fecha_efectiva,\n"+
               "          apr_capital,\n"+
               "          apr_rendimientos ,\n"+
               "          apr_rendimientos_p";

      v_parametros = new String[3];
      v_parametros[0] = v_codigo; 
      v_parametros[1] = v_Nocontrato; 
      v_parametros[2] = consecutivo; 

      v_resultadoConsulta = v_Consulta.TBFL_ConsultaParametros(Consulta,v_parametros);
      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      PublicarIngresos(v_resultadoConsulta, ValorNeto);
      }
      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }



      private void PublicarModalidad(Vector v_descripcion, String consecutivo)
      {
      v_descripcion.trimToSize();

      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        {
        for (int i = 0; i<v_descripcion.size(); i++)
           if (v_descripcion.elementAt(i).toString().equals("null"))
             v_descripcion.setElementAt(" ", i);

        //Aqui van los campo que se quieren mostrar
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de retiros","Consulta de retiros", "", "", true));
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                    "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Información del retiro</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de transacción Tax</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+consecutivo+"</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de transacción MULTIFUND</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>AFP destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Contrato destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"&nbsp;"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Método orden</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Método beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(5).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Método penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(6).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Método de cuenta contingente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(7).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Naturaleza del retiro</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(8).toString()+"</font></TD></TR>");
        out.println("</TABLE>");
        }
      else
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de retiros","Consulta de retiros", "", v_descripcion.elementAt(0).toString(), false));
        }

      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }




      private void PublicarIngresos(Vector v_descripcion, String VN)
      {
      double  Sumacapital = 0;
      double  Sumactacontingente = 0;
      double  Sumapenalizacion = 0;
      double  Sumarendimientos = 0;
      double  Sumaimpuestos = 0;
      double  ValorNeto = 0;//VN

      v_descripcion.trimToSize();

      //Aqui van los campo que se quieren mostrar
      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        {
        for (int i = 0; i<v_descripcion.size(); i++)
           if (v_descripcion.elementAt(i).toString().equals("null"))
             v_descripcion.setElementAt(" ", i);

        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de retiros","Consulta de retiros", "", "", true));
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                   "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Ingresos que han sido afectados por el retiro</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=6 rules=all>");
        out.println("<TR align=middle class=\"td11\" borderColor=silver><TD><FONT color=white face=verdana size=1><B>Fecha efectiva<B></font></TD><TD><FONT color=white face=verdana size=1><B>Capital retirado</B></font></TD><TD><FONT color=white face=verdana size=1><B>Cuenta contingente</B></font></TD><TD><FONT color=white face=verdana size=1><B>Penalizacion</B></font>");
        out.println("</TD><TD><FONT color=white face=verdana size=1><B>rendimientos retirados</B></font></TD><TD><FONT color=white face=verdana size=1><B>Impuesto por rendimiento</B></font></TD></TR>");
        for (int i=0; i<v_descripcion.size(); i+=7)
           {
           Sumacapital += new Double(v_descripcion.elementAt(i+2).toString()).doubleValue();
           Sumactacontingente += new Double(v_descripcion.elementAt(i+5).toString()).doubleValue();
           Sumapenalizacion += new Double(v_descripcion.elementAt(i+6).toString()).doubleValue();
           Sumarendimientos += new Double(v_descripcion.elementAt(i+3).toString()).doubleValue();
           Sumaimpuestos += new Double(v_descripcion.elementAt(i+4).toString()).doubleValue();
           out.println("<TR bgColor=white borderColor=silver><TD><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+1).toString()+"</font>\n</TD><TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+2).toString()))+"</font>\n</TD><TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+5).toString())));
           out.println("</font>\n</TD><TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+6).toString()))+"</font>\n</TD><TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+3).toString()))+"</font>\n</TD><TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+4).toString()))+"</font></TD></TR>");
           }
        out.println("<TR align=middle bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>TOTAL</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(Sumacapital)+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(Sumactacontingente)+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(Sumapenalizacion)+"</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(Sumarendimientos)+"</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(Sumaimpuestos)+"</B></font></TD></TR>");
        out.println("</TABLE>");
        ValorNeto = new Double(VN).doubleValue();
        double calculo = (Sumacapital+Sumarendimientos)-(Sumactacontingente+Sumaimpuestos+Sumapenalizacion);
        double diferencia = ValorNeto - calculo;

        if (diferencia < 0 )
           out.println("<BR>&nbsp;&nbsp;<BR><FONT color=black face=verdana size=1>*Este retiro incluyo "+NumberFormat.getCurrencyInstance().format(Math.abs(diferencia))+"&nbsp;causados como perdida(rendimientos Negativos)</font>");
        }
      else
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de retiros","Consulta de retiros", "", v_descripcion.elementAt(0).toString(), false));
        }

        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
        out.close();
      }



  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONSULTARETIROS.TBCS_InformativoEgresos Information";
  }
}

