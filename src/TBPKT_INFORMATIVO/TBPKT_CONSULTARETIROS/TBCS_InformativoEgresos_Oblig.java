package TBPKT_INFORMATIVO.TBPKT_CONSULTARETIROS;

import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;

import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;

import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.TBCL_Seguridad;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.NumberFormat;

import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TBCS_InformativoEgresos_Oblig extends HttpServlet{
    
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
      Vector v_resultadoConsulta;

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
            String Retencion = "";
            String v_parametros[] = new String[3];
            v_parametros[0] = v_codigo; 
            v_parametros[1] = v_Nocontrato; 
            v_parametros[2] = consecutivo;  
            
            Consulta = "SELECT NVL(SUM(APO_CUENTA_CONTINGENTE),0) " +
                "FROM TBAPORTES_RETIROS_OBLIG \n" + 
                "INNER JOIN TBAPORTES_OBLIG ON APR_RET_CON_PRO_CODIGO = APO_CON_PRO_CODIGO AND APR_RET_CON_NUMERO = APO_CON_NUMERO AND APR_APO_CONSECUTIVO = APO_CONSECUTIVO \n" + 
                "WHERE APR_RET_CON_PRO_CODIGO=? AND APR_RET_CON_NUMERO=? AND APR_RET_CONSECUTIVO = ?";    
            
            v_resultadoConsulta = v_Consulta.TBFL_ConsultaParametros(Consulta, v_parametros);
            Retencion = v_resultadoConsulta.elementAt(0).toString().trim();
            
            Consulta = "SELECT COUNT(DISTINCT(tbfbd_tipoaporteoblig(APO_CONSECUTIVO))) \n" + 
                        "from TBAPORTES_RETIROS_OBLIG \n" + 
                        "INNER JOIN TBAPORTES_OBLIG  ON APR_RET_CON_PRO_CODIGO = APO_CON_PRO_CODIGO AND APR_RET_CON_NUMERO = APO_CON_NUMERO AND APR_APO_CONSECUTIVO = APO_CONSECUTIVO \n" + 
                        "WHERE APR_RET_CON_PRO_CODIGO= ? AND  APR_RET_CON_NUMERO = ?  AND  APR_ret_consecutivo = ? ";
                        
            v_resultadoConsulta = v_Consulta.TBFL_ConsultaParametros(Consulta, v_parametros);
            
            if (v_resultadoConsulta.elementAt(0).toString().equals("No hay elementos"))
                PublicarModalidad(null,null);
            else {                                                
                if (v_resultadoConsulta.elementAt(0).toString().equals("2"))
                {
                    Consulta = "SELECT  ret_transaccion, \n"+
                           "        afp_descripcion, \n"+
                           "        a.ref_descripcion, \n"+                               
                           "        'AVA + AVE', \n"+  
                           "        "+Retencion+"\n"+
                           "FROM   tbretiros_oblig, \n"+
                           "       tbadm_fondos_pensiones, \n"+
                           "       tbreferencias a \n"+
                           "WHERE  RET_CON_PRO_CODIGO = ? \n"+
                           "  AND  RET_CON_NUMERO = ? \n"+
                           "  AND  ret_consecutivo = ? \n" +
                           "  AND  RET_REF_METODO_ORDEN = a.ref_codigo (+) \n"+                               
                           "  AND  ret_afp_codigo  =   afp_codigo (+)"; 
                    
                    v_resultadoConsulta = v_Consulta.TBFL_ConsultaParametros(Consulta, v_parametros);    
                    PublicarModalidad(v_resultadoConsulta, consecutivo);
                }
                else {
                    
                    Consulta = "SELECT CASE WHEN tbfbd_tipoaporteoblig(APO_CONSECUTIVO) = 'N' THEN 'AVA' ELSE 'AVE' END\n" + 
                                "from TBAPORTES_RETIROS_OBLIG \n" + 
                                "INNER JOIN TBAPORTES_OBLIG ON APR_RET_CON_PRO_CODIGO = APO_CON_PRO_CODIGO " +
                                "                           AND APR_RET_CON_NUMERO = APO_CON_NUMERO " +
                                "                           AND APR_APO_CONSECUTIVO = APO_CONSECUTIVO\n" + 
                                "WHERE APR_RET_CON_PRO_CODIGO=? AND  APR_RET_CON_NUMERO = ?  AND  APR_ret_consecutivo = ? \n" + 
                                "GROUP BY tbfbd_tipoaporteoblig(APO_CONSECUTIVO)";
                    
                    v_resultadoConsulta = v_Consulta.TBFL_ConsultaParametros(Consulta, v_parametros);
                    if (v_resultadoConsulta.elementAt(0).toString().equals("No hay elementos"))
                        PublicarModalidad(null,null);
                    else {
                        Consulta = "SELECT  ret_transaccion, \n"+
                               "        afp_descripcion, \n"+
                               "        a.ref_descripcion, \n"+                               
                               "        '"+v_resultadoConsulta.elementAt(0).toString()+"', \n"+  
                               "        "+Retencion+"\n"+
                               "FROM   tbretiros_oblig, \n"+
                               "       tbadm_fondos_pensiones, \n"+
                               "       tbreferencias a \n"+
                               "WHERE  RET_CON_PRO_CODIGO = ? \n"+
                               "  AND  RET_CON_NUMERO = ? \n"+
                               "  AND  ret_consecutivo = ? \n" +
                               "  AND  RET_REF_METODO_ORDEN = a.ref_codigo (+) \n"+                               
                               "  AND  ret_afp_codigo  =   afp_codigo (+)";                           
                    }
                                    
                    v_resultadoConsulta = v_Consulta.TBFL_ConsultaParametros(Consulta, v_parametros);    
                    PublicarModalidad(v_resultadoConsulta, consecutivo);
                }
            }
        }
       else if (v_opcion.equals("ingresos"))
        {
        Consulta = "SELECT ret_valor_neto \n"+
                   " FROM  tbretiros_oblig WHERE \n"+
                   " ret_con_pro_codigo = ? \n"+
                   " AND ret_con_numero = ? \n"+
                   " AND ret_consecutivo = ? ";
        
        String v_parametros[] = new String[3];
        v_parametros[0] = v_codigo; 
        v_parametros[1] = v_Nocontrato; 
        v_parametros[2] = consecutivo; 
        
        String ValorNeto = v_Consulta.TBFL_ConsultaParametros(Consulta, v_parametros, "ret_valor_neto").elementAt(0).toString();        

        Consulta =" SELECT  apr_apo_consecutivo,\n"+
                  "         to_char(apo_fecha_efectiva, 'RRRR-MM-DD'),\n"+
                  "         apr_capital,\n"+                  
                  "         NVL(SUM(TBFBD_CARGOS_RETIROS(apr_ret_con_pro_codigo , apr_ret_con_numero, apr_ret_consecutivo, apr_apo_consecutivo, 'STC002')),0), \n"+                  
                  "         apr_rendimientos, \n"+
                  "         NVL(SUM(TBFBD_CARGOS_RETIROS(apr_ret_con_pro_codigo , apr_ret_con_numero, apr_ret_consecutivo, apr_apo_consecutivo, 'STC001')),0), \n"+                                    
                  "         CASE WHEN tbfbd_tipoaporteoblig(APO_CONSECUTIVO) = 'S' THEN 'AVE' ELSE 'AVA' END \n" +             
                  "   FROM  tbaportes_retiros_oblig,\n"+
                  "         tbaportes_oblig\n"+
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
                 "          tbfbd_tipoaporteoblig(APO_CONSECUTIVO) ";    

        v_resultadoConsulta = v_Consulta.TBFL_ConsultaParametros(Consulta,v_parametros);        
        PublicarIngresos(v_resultadoConsulta, ValorNeto);
        }
        v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    }



        private void PublicarModalidad(Vector v_descripcion, String consecutivo)
        {
        v_descripcion.trimToSize();
        String penalizacion = "";
        String metodoReten = "";
        String beneficio = "";

        if (v_descripcion.elementAt(4).toString().trim().equals("0"))
            metodoReten = "APORTE SIN RETENCIÓN CONTINGENTE";
        else
            metodoReten = "APORTE CON RETENCIÓN CONTINGENTE";    

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
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de transacción AS400</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"</font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>AFP destino</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");        
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Método orden</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Método de retención contingente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+metodoReten+"</font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Concepto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
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
        double  Sumaretencontingente = 0;
        double  Sumarendimientos = 0;
        double  Sumaretenrendimientos = 0;
        
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
          out.println("<TR align=middle class=\"td11\" borderColor=silver><TD><FONT color=white face=verdana size=1><B>Fecha Efectiva<B></font></TD>");
          out.println("<TD><FONT color=white face=verdana size=1><B>Capital Retirado</B></font></TD>");
          out.println("<TD><FONT color=white face=verdana size=1><B>Retención Contingente</B></font></TD>");
          out.println("<TD><FONT color=white face=verdana size=1><B>Rendimientos Retirados</B></font>");
          out.println("</TD><TD><FONT color=white face=verdana size=1><B>Retención Rendimientos</B></font></TD>");
          out.println("<TD><FONT color=white face=verdana size=1><B>Concepto</B></font></TD></TR>");
          for (int i=0; i<v_descripcion.size(); i+=7)
             {
             Sumacapital += new Double(v_descripcion.elementAt(i+2).toString()).doubleValue();
             Sumaretencontingente += new Double(v_descripcion.elementAt(i+3).toString()).doubleValue();             
             Sumarendimientos += new Double(v_descripcion.elementAt(i+4).toString()).doubleValue();
             Sumaretenrendimientos += new Double(v_descripcion.elementAt(i+5).toString()).doubleValue();
             
             out.println("<TR bgColor=white borderColor=silver><TD><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+1).toString()+"</font>\n</TD>");
             out.println("<TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(Double.parseDouble(v_descripcion.elementAt(i+2).toString())))+"</font>\n</TD>");
             out.println("<TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+3).toString())));
             out.println("</font>\n</TD><TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(Double.parseDouble(v_descripcion.elementAt(i+4).toString()))+"</font>\n</TD>");
             out.println("<TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+5).toString()))+"</font>\n</TD>");
             out.println("<TD><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+6).toString()+"</font></TD></TR>");
             }
          out.println("<TR align=middle bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>TOTAL</B></font></TD>");
          out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(Sumacapital)+"</font></TD>");
          out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(Sumaretencontingente)+"</font></TD>");
          out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(Sumarendimientos)+"</B></font></TD>");
          out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(Sumaretenrendimientos)+"</B></font></TD>");
          out.println("<TD width=\"22%\"></TD></TR>");
          out.println("</TABLE>");
          ValorNeto = new Double(VN).doubleValue();
          double calculo = (Sumacapital)-(Sumaretencontingente+Sumaretenrendimientos);
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
    
    public TBCS_InformativoEgresos_Oblig() {
        super();
    }
}
