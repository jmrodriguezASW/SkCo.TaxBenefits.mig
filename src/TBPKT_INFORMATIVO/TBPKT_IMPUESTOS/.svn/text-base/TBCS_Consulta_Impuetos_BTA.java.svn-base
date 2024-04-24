package TBPKT_INFORMATIVO.TBPKT_IMPUESTOS;
 
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTMLII;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_Consulta_Impuetos_BTA extends HttpServlet implements SingleThreadModel
{
   private PrintWriter out;
   private TBCL_Consulta v_Consulta;
   private String v_nuevaCadena ="";


   /**
   * Initialize global variables
   */
   public void init(ServletConfig config) throws ServletException
   {
     super.init(config);
   }

   /**
   * Process the HTTP Get request
   */
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      try
      {
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
      //Vector donde se guardara el resultado de la v_dconsulta
      Vector v_resultadoconsulta, v_resultadoconsulta1, resultadoconsulta11, resultadoconsulta12, resultadoconsulta21, resultadoconsulta22,
             resultadoconsulta31, resultadoconsulta32, resultadoconsulta41, resultadoconsulta42, resultadoconsulta43, resultadoconsulta44;

      v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


      //Toma el v_consecutivo
      String opcion = "";
      String v_codigo = "";
      String v_Nocontrato = "";
      String mesini = "";
      //Toma el ano inicial para la v_dconsulta
      String anoini = "";
      //Toma el mes final para la v_dconsulta
      String mesfin = "";
      //Toma el ano final para la v_dconsulta
      String anofin = "";
      String Cta_Con_Cobrada = "";
      String Cta_Con_Ahorrada = "";


      try
      {
         v_codigo = request.getParameter("v_codigo");
         v_Nocontrato = request.getParameter("v_Nocontrato");
         opcion = request.getParameter("opcion");
         mesini = request.getParameter("mesini");
         anoini = request.getParameter("anoini");
         mesfin = request.getParameter("mesfin");
         anofin = request.getParameter("anofin");
      }
      catch (Exception e) { e.printStackTrace(); }



      if (opcion.equals("beneficio"))
      {
         v_dconsulta = "SELECT     SUM(decode(apr_aporte_beneficio,'S',apr_capital,0))\n"+
                       "          ,SUM(decode(apr_aporte_beneficio,'S',apr_rendimientos,0))\n"+
                       "          ,SUM(decode(apr_aporte_beneficio,'N',apr_capital,0))\n"+
                       "          ,SUM(decode(apr_aporte_beneficio,'N',apr_rendimientos,0))\n"+
                       "FROM       tbaportes_retiros\n"+
                       "          ,tbretiros\n"+
                       "WHERE      apr_ret_con_pro_codigo = ret_con_pro_codigo\n"+
                       "AND        apr_ret_con_numero = ret_con_numero\n"+
                       "AND        apr_ret_consecutivo = ret_consecutivo\n"+
                       "AND        ret_fecha_efectiva BETWEEN to_date('"+mesini+" "+anoini+"', 'MM-RRRR','nls_date_language=ENGLISH') AND LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-YYYY','nls_date_language=ENGLISH')) \n"+
                       "AND        ret_con_pro_codigo = '"+v_codigo+"'\n"+
                       "AND        ret_con_numero = '"+v_Nocontrato+"'";
         v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);

         v_dconsulta = "SELECT     SUM(decode(apr_aporte_beneficio,'N',NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC002'),0),0))\n"+
                       "FROM       tbaportes_retiros\n"+
                       "          ,tbretiros\n"+
                       "WHERE      apr_ret_con_pro_codigo = ret_con_pro_codigo\n"+
                       "AND        apr_ret_con_numero = ret_con_numero\n"+
                       "AND        apr_ret_consecutivo = ret_consecutivo\n"+
                       "AND        ret_fecha_efectiva BETWEEN to_date('"+mesini+" "+anoini+"', 'MM-RRRR','nls_date_language=ENGLISH') AND LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-RRRR','nls_date_language=ENGLISH'))\n"+
                       "AND        ret_con_pro_codigo = '"+v_codigo+"'\n"+
                       "AND        ret_con_numero = '"+v_Nocontrato+"'";
         Cta_Con_Cobrada = v_Consulta.TBFL_Consulta(v_dconsulta).elementAt(0).toString();


         v_dconsulta = "SELECT           SUM(apr_capital)*(SUM(APO_CUENTA_CONTINGENTE)/SUM(APO_CAPITAL))\n"+
                       "FROM             tbaportes_retiros,\n"+
                       "                 tbretiros,\n"+
                       "                 tbaportes\n"+
                       "WHERE            apo_con_pro_codigo = apr_ret_con_pro_codigo\n"+
                       "AND              apo_con_numero = apr_ret_con_numero\n"+
                       "AND              apo_consecutivo = apr_apo_consecutivo\n"+
                       "AND              apr_ret_con_pro_codigo = ret_con_pro_codigo\n"+
                       "AND              apr_ret_con_numero = ret_con_numero\n"+
                       "AND              apr_ret_consecutivo = ret_consecutivo\n"+
                       "AND              ret_fecha_efectiva\n"+
                       "BETWEEN          TO_DATE('"+mesini+" "+anoini+"', 'MM-RRRR','nls_date_language=ENGLISH') AND LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-YYYY','nls_date_language=ENGLISH')) \n"+
                       "AND              ret_con_pro_codigo = '"+v_codigo+"'\n"+
                       "AND              ret_con_numero = '"+v_Nocontrato+"'\n"+
                       "AND              apr_aporte_beneficio='S'";
         Cta_Con_Ahorrada = v_Consulta.TBFL_Consulta(v_dconsulta).elementAt(0).toString();
         v_dconsulta = "SELECT to_char ("+Cta_Con_Ahorrada+", '999,999,999,999.99') from dual";
         Cta_Con_Ahorrada = v_Consulta.TBFL_Consulta(v_dconsulta).elementAt(0).toString().trim();

         //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
         PublicarBeneficio(v_resultadoconsulta, Cta_Con_Ahorrada, Cta_Con_Cobrada, v_Nocontrato,v_codigo);
      }
      else if (opcion.equals("traslado"))
           {
              v_dconsulta = "SELECT      to_char(apo_fecha_aporte, 'RRRR/MM/DD'),\n"+
                            "            apo_capital,\n"+
                            "            apo_rendimientos,\n"+
                            "            apo_cuenta_contingente,\n"+
                            "            afp_descripcion,\n"+
                            "            APO_CON_PRO_CODIGO_TI,\n"+
                            "            APO_CON_NUMERO_TI\n"+
                            "FROM        tbaportes,\n"+
                            "            tbadm_fondos_pensiones\n"+
                            "WHERE       apo_fecha_efectiva\n"+
                            "BETWEEN     to_date('"+mesini+" "+anoini+"', 'MM-RRRR','nls_date_language=ENGLISH') AND  LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-YYYY','nls_date_language=ENGLISH')) \n"+
                            "AND         apo_con_numero = '"+v_Nocontrato+"'\n"+
                            "AND         apo_con_pro_codigo = '"+v_codigo+"'\n"+
                            "AND         apo_aporte_traslado = 'S'\n"+
                            "AND         AFP_CODIGO (+) = APO_AFP_CODIGO\n"+
                            "ORDER BY    apo_fecha_aporte";
              v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);

              v_dconsulta = "SELECT to_char(ret_fecha_proceso,'RRRR/MM/DD')\n"+
                            "      ,to_char(ret_fecha_efectiva,'RRRR/MM/DD')\n"+
                            "      ,ret_con_pro_codigo_ti\n"+
                            "      ,ret_con_numero_ti\n"+
                            "      ,cot_descripcion\n"+
                            "      ,afp_descripcion\n"+
                            "      ,nvl(sum(apr_capital),0)\n"+
                            "      ,nvl(SUM(apr_rendimientos),0)\n"+
                            "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC002'),0)),0)\n"+
                            "FROM  tbaportes_retiros\n"+
                            "     ,tbretiros\n"+
                            "     ,tbadm_fondos_pensiones\n"+
                            "     ,tbconceptos_transaccion\n"+
                            "     ,tbproductos_conceptos\n"+
                            "where     apr_ret_con_pro_codigo (+)      = ret_con_pro_codigo          -- Outer Join\n"+
                            "    and   apr_ret_con_numero (+)          = ret_con_numero              -- por si el retiro\n"+
                            "    and   apr_ret_consecutivo (+)         = ret_consecutivo             -- no tiene aportes_retiro\n"+
                            "    AND   RET_COT_REF_TRANSACCION         = COT_REF_TRANSACCION\n"+
                            "    AND   RET_COT_REF_TIPO_TRANSACCION    = COT_REF_TIPO_TRANSACCION\n"+
                            "    AND   RET_COT_REF_CLASE_TRANSACCION   = COT_REF_CLASE_TRANSACCION\n"+
                            "    and   ret_con_pro_codigo              = prc_pro_codigo\n"+
                            "    and   ret_afp_codigo                  = afp_codigo (+)              -- Outer Join por si el retiro no tiene AFP\n"+
                            "    and   ret_con_numero                  = '"+v_Nocontrato+"'\n"+
                            "    and   cot_ref_transaccion             = prc_cot_ref_transaccion\n"+
                            "    and   cot_ref_tipo_transaccion        = prc_cot_ref_tipo_transaccion\n"+
                            "    and   cot_ref_clase_transaccion       = prc_cot_ref_clase_transaccion\n"+
                            "    and   prc_pro_codigo                  = '"+v_codigo+"'\n"+
                            "    and   prc_traslado                    = 'S'\n"+
                            "    and   ret_fecha_efectiva\n"+
                            "          BETWEEN     to_date('"+mesini+" "+anoini+"', 'MM-RRRR','nls_date_language=ENGLISH') AND  LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-YYYY','nls_date_language=ENGLISH'))\n"+
                            "group by  to_char(ret_fecha_proceso, 'RRRR/MM/DD')\n"+
                            "         ,to_char(ret_fecha_efectiva, 'RRRR/MM/DD')\n"+
                            "         ,ret_con_pro_codigo_ti\n"+
                            "         ,ret_con_numero_ti\n"+
                            "         ,cot_descripcion\n"+
                            "         ,afp_descripcion\n"+
                            "         ,ret_consecutivo";
              v_resultadoconsulta1 = v_Consulta.TBFL_Consulta(v_dconsulta);
              //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
              PublicarTraslado(v_resultadoconsulta, v_resultadoconsulta1, v_Nocontrato, v_codigo);
           }
           else if (opcion.equals("ajuste"))
                {
                   v_dconsulta = "SELECT         aju_consecutivo\n"+
                                 "              ,aju_linea\n"+
                                 "              ,aju_retiro_original\n"+
                                 "              ,aju_retiro_actual\n"+
                                 "              ,to_char(aju_fecha_efectiva, 'RRRR/MM/DD')\n"+
                                 "              ,to_char(aju_fecha_proceso, 'RRRR/MM/DD')\n"+
                                 "              ,aju_valor\n"+
                                 "              ,ref_descripcion\n"+
                                 " FROM          tbajustes\n"+
                                 "              ,tbreferencias\n"+
                                 "WHERE          ref_codigo (+) = aju_ref_accion\n"+
                                 " AND           aju_fecha_efectiva\n"+
                                 " BETWEEN       TO_DATE('"+mesini+" "+anoini+"', 'MM-RRRR','nls_date_language=ENGLISH') AND LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-YYYY','nls_date_language=ENGLISH')) \n"+
                                 " AND           aju_con_pro_codigo = '"+v_codigo+"'\n"+
                                 " AND           aju_con_numero = '"+v_Nocontrato+"'\n"+
                                 "ORDER BY       aju_consecutivo\n"+
                                 "              ,aju_linea";
                   v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);

                   v_dconsulta = "SELECT  SUM(apr_capital)\n"+
                                 "       ,SUM(apr_rendimientos )\n"+
                                 " FROM          tbaportes_retiros\n"+
                                 "              ,tbajustes\n"+
                                 "WHERE          apr_ret_consecutivo (+) = aju_retiro_original\n"+
                                 " AND           apr_ret_con_pro_codigo (+) = aju_con_pro_codigo\n"+
                                 " AND           apr_ret_con_numero (+) = aju_con_numero\n"+
                                 " AND           aju_fecha_efectiva\n"+
                                 " BETWEEN       TO_DATE('"+mesini+" "+anoini+"', 'MM-RRRR','nls_date_language=ENGLISH') AND LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-YYYY','nls_date_language=ENGLISH')) \n"+
                                 " AND           aju_con_pro_codigo = '"+v_codigo+"'\n"+
                                 " AND           aju_con_numero = '"+v_Nocontrato+"'\n"+
                                 " GROUP BY      aju_consecutivo\n"+
                                 "              ,aju_linea\n"+
                                 "ORDER BY       aju_consecutivo\n"+
                                 "              ,aju_linea";
                   resultadoconsulta11 = v_Consulta.TBFL_Consulta(v_dconsulta);

                   v_dconsulta = "SELECT  SUM(apr_capital)\n"+
                                 "              ,SUM(apr_rendimientos)\n"+
                                 " FROM          tbaportes_retiros\n"+
                                 "              ,tbajustes\n"+
                                 "WHERE          apr_ret_consecutivo (+) = aju_retiro_actual\n"+
                                 " AND           apr_ret_con_pro_codigo (+) = aju_con_pro_codigo\n"+
                                 " AND           apr_ret_con_numero (+) = aju_con_numero\n"+
                                 " AND           aju_fecha_efectiva\n"+
                                 " BETWEEN       TO_DATE('"+mesini+" "+anoini+"', 'MM-RRRR','nls_date_language=ENGLISH') AND LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-YYYY','nls_date_language=ENGLISH')) \n"+
                                 " AND           aju_con_pro_codigo = '"+v_codigo+"'\n"+
                                 " AND           aju_con_numero = '"+v_Nocontrato+"'\n"+
                                 " GROUP BY      aju_consecutivo\n"+
                                 "              ,aju_linea\n"+
                                 "ORDER BY       aju_consecutivo\n"+
                                 "              ,aju_linea";
                   resultadoconsulta12 = v_Consulta.TBFL_Consulta(v_dconsulta);


                   v_dconsulta = "SELECT       NVL(SUM(caa_valor),0)\n"+
                                 " FROM          tbcargos_ajustes\n"+
                                 "              ,tbajustes\n"+
                                 "WHERE          CAA_AJU_CON_PRO_CODIGO (+) = aju_con_pro_codigo\n"+
                                 " AND           CAA_AJU_CON_NUMERO     (+) = aju_con_numero\n"+
                                 " AND           CAA_AJU_CONSECUTIVO    (+) = aju_CONSECUTIVO\n"+
                                 " AND           CAA_AJU_LINEA          (+) = aju_linea  \n"+
                                 " AND           CAA_REF_CARGO          (+) = 'STC002'\n"+
                                 " AND           aju_fecha_efectiva\n"+
                                 " BETWEEN       TO_DATE('"+mesini+" "+anoini+"', 'MM-RRRR','nls_date_language=ENGLISH') AND LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-YYYY','nls_date_language=ENGLISH')) \n"+
                                 " AND           aju_con_pro_codigo = '"+v_codigo+"'\n"+
                                 " AND           aju_con_numero = '"+v_Nocontrato+"'\n"+
                                 " GROUP BY      aju_consecutivo\n"+
                                 "              ,aju_linea\n"+
                                 "ORDER BY       aju_consecutivo\n"+
                                 "              ,aju_linea";

                   resultadoconsulta22 = v_Consulta.TBFL_Consulta(v_dconsulta);

                   v_dconsulta = "SELECT         SUM(caa_valor)\n"+
                                 " FROM          tbcargos_ajustes\n"+
                                 "              ,tbajustes\n"+
                                 "WHERE          CAA_AJU_CON_PRO_CODIGO (+) = aju_con_pro_codigo\n"+
                                 " AND           CAA_AJU_CON_NUMERO     (+) = aju_con_numero\n"+
                                 " AND           CAA_AJU_CONSECUTIVO    (+) = aju_CONSECUTIVO\n"+
                                 " AND           CAA_AJU_LINEA          (+) = aju_linea \n"+
                                 " AND           CAA_REF_CARGO (+) = 'STC001'\n"+
                                 " AND           aju_fecha_efectiva\n"+
                                 " BETWEEN       TO_DATE('"+mesini+" "+anoini+"', 'MM-RRRR','nls_date_language=ENGLISH') AND LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-YYYY','nls_date_language=ENGLISH')) \n"+
                                 " AND           aju_con_pro_codigo = '"+v_codigo+"'\n"+
                                 " AND           aju_con_numero = '"+v_Nocontrato+"'\n"+
                                 " GROUP BY      aju_consecutivo\n"+
                                 "              ,aju_linea\n"+
                                 "ORDER BY       aju_consecutivo\n"+
                                 "              ,aju_linea";

                   resultadoconsulta32 = v_Consulta.TBFL_Consulta(v_dconsulta);



                   v_dconsulta = "SELECT         SUM(caa_valor)\n"+
                                 " FROM          tbcargos_ajustes\n"+
                                 "              ,tbajustes\n"+
                                 "WHERE          CAA_AJU_CON_PRO_CODIGO (+) = aju_con_pro_codigo\n"+
                                 " AND           CAA_AJU_CON_NUMERO     (+) = aju_con_numero\n"+
                                 " AND           CAA_AJU_CONSECUTIVO    (+) = aju_CONSECUTIVO\n"+
                                 " AND           CAA_AJU_LINEA          (+) = aju_linea \n"+
                                 " AND           CAA_REF_CARGO (+) = 'STC003'\n"+
                                 " AND           aju_fecha_efectiva\n"+
                                 " BETWEEN       TO_DATE('"+mesini+" "+anoini+"', 'MM-RRRR','nls_date_language=ENGLISH') AND LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-YYYY','nls_date_language=ENGLISH')) \n"+
                                 " AND           aju_con_pro_codigo = '"+v_codigo+"'\n"+
                                 " AND           aju_con_numero = '"+v_Nocontrato+"'\n"+
                                 " GROUP BY      aju_consecutivo\n"+
                                 "              ,aju_linea\n"+
                                 "ORDER BY       aju_consecutivo\n"+
                                 "              ,aju_linea";

                   resultadoconsulta42 = v_Consulta.TBFL_Consulta(v_dconsulta);



                   v_dconsulta = "SELECT         SUM(caa_valor)\n"+
                                 " FROM          tbcargos_ajustes\n"+
                                 "              ,tbajustes\n"+
                                 "WHERE          CAA_AJU_CON_PRO_CODIGO (+) = aju_con_pro_codigo\n"+
                                 " AND           CAA_AJU_CON_NUMERO     (+) = aju_con_numero\n"+
                                 " AND           CAA_AJU_CONSECUTIVO    (+) = aju_CONSECUTIVO\n"+
                                 " AND           CAA_AJU_LINEA          (+) = aju_linea  \n"+
                                 " AND           CAA_REF_CARGO (+) = 'STC004'\n"+
                                 " AND           aju_fecha_efectiva\n"+
                                 " BETWEEN       TO_DATE('"+mesini+" "+anoini+"', 'MM-RRRR','nls_date_language=ENGLISH') AND LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-YYYY','nls_date_language=ENGLISH')) \n"+
                                 " AND           aju_con_pro_codigo = '"+v_codigo+"'\n"+
                                 " AND           aju_con_numero = '"+v_Nocontrato+"'\n"+
                                 " GROUP BY      aju_consecutivo\n"+
                                 "              ,aju_linea\n"+
                                 "ORDER BY       aju_consecutivo\n"+
                                 "              ,aju_linea";

                   resultadoconsulta44 = v_Consulta.TBFL_Consulta(v_dconsulta);

                   //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
                   PublicarAjuste(v_resultadoconsulta, resultadoconsulta11, resultadoconsulta12, resultadoconsulta22,
                                  resultadoconsulta32, resultadoconsulta42, resultadoconsulta44,
                                  v_Nocontrato, v_codigo);
                }
                else if (opcion.equals("conimp"))
                     {
                        String vconfecha = " select to_char(sysdate,'rrrr')"+
                                           "       , to_char(sysdate,'mm') "+
                                           "      , to_char(sysdate,'dd')  "+
                                           "  from dual  ";
                        Vector v_resfecha =v_Consulta.TBFL_Consulta(vconfecha);
                        //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
                        String feccon = request.getParameter("feccon");
                        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta Impuestos","Consulta de Impuestos", "TBPKT_INFORMATIVO.TBPKT_IMPUESTOS.TBCS_Impuestos_fecha_efepro", "", true, "validation.js", "return formValidation(this)"));
                        out.println("<TABLE align=center bgColor=white border=0 borderColor=black cellPadding=1 cellSpacing=0 cols=1 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY>");
                        out.println("<TR bgColor=white borderColor=black align=left>");
                        out.println("<TD width=\"5%\">");
                        out.println("<FONT color=black face=verdana size=1><B><CENTER>Fecha de inicio del contrato: "+feccon+"</CENTER></B></FONT></TD>");
                        String vfeccon2 = feccon.replace('-','/');
                        out.println("</TR></TABLE>");
                        out.println("<center>");
                        Date vano=new Date();
                        out.println("Año: <input name=ANNO type=text value='"+v_resfecha.elementAt(0).toString()+"' class=bttntext size=4 maxlength=4 id='RN           Y Año'>");
                        Date vmes=new Date();
                        out.println("Mes: <SELECT name='MES' class=bttntext id='             M Mes'>");
                        if(v_resfecha.elementAt(1).toString().equals("01"))
                           out.println("<option selected value=01>01</option>");
                        else
                           out.println("<option  value=01>01</option>");
                        if(v_resfecha.elementAt(1).toString().equals("02"))
                           out.println("<option selected value=02>02</option>");
                        else
                           out.println("<option  value=02>02</option>");
                        if(v_resfecha.elementAt(1).toString().equals("03"))
                           out.println("<option selected value=03>03</option>");
                        else
                           out.println("<option  value=03>03</option>");

                        if(v_resfecha.elementAt(1).toString().equals("04"))
                           out.println("<option selected value=04>04</option>");
                        else
                           out.println("<option  value=04>04</option>");

                        if(v_resfecha.elementAt(1).toString().equals("05"))
                           out.println("<option selected value=05>05</option>");
                        else
                           out.println("<option  value=05>05</option>");

                        if(v_resfecha.elementAt(1).toString().equals("06"))
                           out.println("<option selected value=06>06</option>");
                        else
                           out.println("<option  value=06>06</option>");

                        if(v_resfecha.elementAt(1).toString().equals("07"))
                           out.println("<option selected value=07>07</option>");
                        else
                           out.println("<option  value=07>07</option>");

                        if(v_resfecha.elementAt(1).toString().equals("08"))
                           out.println("<option selected value=08>08</option>");
                        else
                           out.println("<option  value=08>08</option>");

                        if(v_resfecha.elementAt(1).toString().equals("09"))
                           out.println("<option selected value=09>09</option>");
                        else
                           out.println("<option  value=09>09</option>");

                        if(v_resfecha.elementAt(1).toString().equals("10"))
                           out.println("<option selected value=10>10</option>");
                        else
                           out.println("<option  value=10>10</option>");

                        if(v_resfecha.elementAt(1).toString().equals("11"))
                           out.println("<option selected value=11>11</option>");
                        else
                           out.println("<option  value=11>11</option>");

                        if(v_resfecha.elementAt(1).toString().equals("12"))
                           out.println("<option selected value=12>12</option>");
                        else
                           out.println("<option  value=12>12</option>");
                        out.println("</SELECT>");
                        Date dia = new Date();
                        out.println("Día: <input name=DIA  type=text value='"+v_resfecha.elementAt(2).toString()+"' class=bttntext size=2 maxlength=2 id='RN           D Día'>");
                        out.println("<br>Fecha <SELECT name='FP' class=bttntext>");
                        out.println("<option selected value=P>de Proceso</option>");
                        out.println("<option  value=E>Efectiva</option>");
                        out.println("</SELECT>");
                         out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
                        out.println("<br><input name=Send type=submit value='Buscar' class=sbttn  >");//onClick='return chkDate(form,'"+vfeccon2+"')' >");
                        out.println("<input name=clear type=reset value='Limpiar' class=sbttn>");
                        out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                        out.close();
                     }
       v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
   }


   private void PublicarBeneficio(Vector v_descripcion, String Cta_Con_Ahorrada, String Cta_Con_Cobrada , String v_Nocontrato, String v_codigo)
   {
      v_descripcion.trimToSize();
      double TCRCON = 0;
      double TRRCON = 0;
      double TCRSIN = 0;
      double TRRSIN = 0;
      double CCC = 0;

      /*Cambio para manejo de referencia unica 2009/04/01 MOS */
      String v_contrato_unif = "";
      //v_Consulta.TBPL_RealizarConexion();
      v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_codigo,v_Nocontrato);
      //v_Consulta.TBPL_shutDown();
          
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de impuestos","Consulta de impuestos para el contrato&nbsp"+v_contrato_unif+"", "", "", true));

      if (!v_descripcion.elementAt(0).toString().equals("null"))
        {
        for (int i = 0; i<v_descripcion.size(); i++)
           if (v_descripcion.elementAt(i).toString().equals("null"))
             v_descripcion.setElementAt("0", i);

        try{TCRCON = Double.parseDouble(v_descripcion.elementAt(0).toString());}
        catch (Exception e){TCRCON = 0;}
        try{TRRCON = Double.parseDouble(v_descripcion.elementAt(1).toString());}
        catch (Exception e){TRRCON = 0;}
        try{TCRSIN = Double.parseDouble(v_descripcion.elementAt(2).toString());}
        catch (Exception e){TCRSIN = 0;}
        try{TRRSIN = Double.parseDouble(v_descripcion.elementAt(3).toString());}
        catch (Exception e){TRRSIN = 0;}
        try{CCC = Double.parseDouble(Cta_Con_Cobrada);}
        catch (Exception e){CCC = 0;}

        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0><TBODY>");
        out.println("<TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Retiros CON beneficio tributario</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Total capital retirado</B></FONT></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>");
        out.println(NumberFormat.getCurrencyInstance().format(TCRCON)+"</FONT></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Total rendimientos retirados</B></FONT></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(TRRCON)+"</FONT></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta contingente ahorrada</B></FONT></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>$"+Cta_Con_Ahorrada+"</FONT></TD></TR>");
        out.println("</TBODY></TABLE>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0><TBODY>");
        out.println("<TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Retiros SIN beneficio tributario</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Total capital retirado</B></FONT></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(TCRSIN)+"</FONT></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Total rendimientos retirados</B></FONT></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(TRRSIN)+"</FONT></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta contingente cobrada</B></FONT></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(CCC)+"</FONT></TD></TR>");
        out.println("</TBODY></TABLE>");
        }
      else
        {
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");

        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0><TBODY>");
        out.println("<TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Retiros CON beneficio tributario</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Total capital retirado</B></FONT></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>No hay elementos</FONT></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Total rendimientos retirados</B></FONT></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>No hay elementos</FONT></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta contingente ahorrada</B></FONT></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>No hay elementos</FONT></TD></TR>");
        out.println("</TBODY></TABLE>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0><TBODY>");
        out.println("<TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Retiros SIN beneficio tributario</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Total capital retirado</B></FONT></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>No hay elementos</FONT></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Total rendimientos retirados</B></FONT></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>No hay elementos</FONT></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta contingente cobrada</B></FONT></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>No hay elementos</FONT></TD></TR>");
        out.println("</TBODY></TABLE>");
        }



        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
        out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
        out.println("</font>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
        out.close();
      }


      private void PublicarTraslado(Vector v_descripcion, Vector v_descripcion1, String v_Nocontrato, String v_codigo)
      {
      v_descripcion.trimToSize();
      v_descripcion1.trimToSize();

      /*Cambio para manejo de referencia unica 2009/04/01 MOS */
      String v_contrato_unif = "";
      //v_Consulta.TBPL_RealizarConexion();
      v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_codigo,v_Nocontrato);
      //v_Consulta.TBPL_shutDown();
              
      out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Consulta de impuestos","Consulta de impuestos para el contrato&nbsp"+v_contrato_unif+"", "", "", true));

      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        {
        for (int i = 0; i<v_descripcion.size(); i++)
           if (v_descripcion.elementAt(i).toString().equals("null"))
             v_descripcion.setElementAt(" ", i);

        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0><TBODY>");
        out.println("<TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Aportes trasladados</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR class=\"td11\" borderColor=silver align=center><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha del aporte</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Capital</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Cta contingente</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>AFP origen</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Contrato origen</B></FONT></TD></TR>");
        for (int i=0; i<v_descripcion.size(); i+=7)
           out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_descripcion.elementAt(i+1).toString()))+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_descripcion.elementAt(i+2).toString()))+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_descripcion.elementAt(i+3).toString()))+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+4).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+5).toString()+"&nbsp;"+v_descripcion.elementAt(i+6).toString()+"</FONT></TD></TR>");
        out.println("</TBODY></TABLE>");
        }
      else
        {
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");

        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0><TBODY>");
        out.println("<TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Aportes trasladados</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR class=\"td11\" borderColor=silver align=center><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha del aporte</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Capital</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Cta contingente</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>AFP origen</B></FONT></TD></TR>");
        out.println("</TBODY></TABLE>");
        }

      if (!v_descripcion1.elementAt(0).toString().equals("No hay elementos"))
        {
        for (int i = 0; i<v_descripcion1.size(); i++)
           if (v_descripcion1.elementAt(i).toString().equals("null"))
             v_descripcion1.setElementAt(" ", i);

        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0><TBODY>");
        out.println("<TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Retiros trasladados</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR class=\"td11\" borderColor=silver align=center><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha proceso</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha efectiva</B></FONT></TD><TD width=\"35%\"><FONT color=white face=verdana size=1><B>Tipo de retiro</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Capital</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Cta contingente</B></FONT></TD><TD width=\"35%\"><FONT color=white face=verdana size=1><B>AFP destino</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Contrato destino</B></FONT></TD></TR>");
        for (int i=0; i<v_descripcion1.size(); i+=9)
           {
           out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion1.elementAt(i).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion1.elementAt(i+1).toString()+"</FONT></TD><TD width=\"35%\"><FONT color=black face=verdana size=1>"+v_descripcion1.elementAt(i+4).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_descripcion1.elementAt(i+6).toString()))+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_descripcion1.elementAt(i+7).toString()))+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_descripcion1.elementAt(i+8).toString()))+"</FONT></TD>");
           out.println("<TD width=\"35%\"><FONT color=black face=verdana size=1>"+v_descripcion1.elementAt(i+5).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion1.elementAt(i+2).toString()+"&nbsp;"+v_descripcion1.elementAt(i+3).toString()+"</FONT></TD></TR>");
           }
        out.println("</TBODY></TABLE>");
        }
      else
        {
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0><TBODY>");
        out.println("<TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Retiros trasladados</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR class=\"td11\" borderColor=silver align=center><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha proceso</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha efectiva</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Tipo de retiro</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Capital</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Cta contingente</B></FONT></TD><TD width=\"35%\"><FONT color=white face=verdana size=1><B>AFP destino</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Contrato destino</B></FONT></TD></TR>");
        out.println("</TBODY></TABLE>");
        }

        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
        out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
        out.println("</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        out.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
        out.close();
      }



      private void PublicarAjuste(Vector v_descripcion, Vector descripcion11, Vector descripcion12, Vector descripcion22,
                            Vector descripcion32,  Vector descripcion42, Vector descripcion44,
                            String v_Nocontrato, String v_codigo)
      {
      v_descripcion.trimToSize();
      descripcion11.trimToSize();
      descripcion12.trimToSize();
      descripcion22.trimToSize();
      descripcion32.trimToSize();
      descripcion42.trimToSize();
      descripcion44.trimToSize();

      double Val_Aju = 0;
      double CapitalO = 0;
      double RendO = 0;
      double Cta_ConO = 0;
      double Ret_RenO = 0;
      double PenOK = 0;
      double PenOR = 0;
      double CapitalA = 0;
      double RendA = 0;
      double Cta_ConA = 0;
      double Ret_RenA = 0;
      double PenAK = 0;
      double PenAR = 0;
      int cont = 0;
      int cont1 = 0;

      /*Cambio para manejo de referencia unica 2009/04/01 MOS */
      String v_contrato_unif = "";
      //v_Consulta.TBPL_RealizarConexion();
      v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_codigo,v_Nocontrato);
      //v_Consulta.TBPL_shutDown();
          
      out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Consulta de impuestos","Consulta de impuestos para el contrato&nbsp;"+v_contrato_unif+"", "TBPKT_INFORMATIVO.TBPKT_IMPUESTOS.TBCS_Consulta_Detalle_Ajuste", "", true));

      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        {
        for (int i = 0; i<v_descripcion.size(); i++)
           if (v_descripcion.elementAt(i).toString().equals("null"))
             v_descripcion.setElementAt(" ", i);

        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR class=\"td11\" borderColor=silver align=center><TD width=\"22%\"><FONT color=white face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>No ajuste</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Linea</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>No transacción retiro reversado</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>No transacción retiro reaplicado</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha efectiva</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha proceso</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Valor ajustado</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Diferencia Capital</B></FONT></TD>");
        out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Diferencia rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Diferencia Cta contingente</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Diferencia de retención rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Diferencia Penalización</B></FONT></TD></TR>");
        for (int i=0; i<v_descripcion.size(); i+=8)
           {
           if (i<7)
             {
             try{Val_Aju = Double.parseDouble(v_descripcion.elementAt(i+6).toString());}
             catch (Exception e){Val_Aju = 0;}

             try{PenOK = Double.parseDouble(descripcion42.elementAt(cont).toString());}//41
             catch (Exception e){PenOK = 0;}
             try{PenAK = Double.parseDouble(descripcion44.elementAt(cont).toString());}//43
             catch (Exception e){PenAK = 0;}

             try{Ret_RenO = Double.parseDouble(descripcion32.elementAt(cont).toString());}//31
             catch (Exception e){Ret_RenO = 0;}
             try{Cta_ConO = Double.parseDouble(descripcion22.elementAt(cont).toString());}//21
             catch (Exception e){Cta_ConO = 0;}

             try{RendO = Double.parseDouble(descripcion11.elementAt(cont1+1).toString());}
             catch (Exception e){RendO = 0;}
             try{RendA = Double.parseDouble(descripcion12.elementAt(cont1+1).toString());}
             catch (Exception e){RendO = 0;}

             try{CapitalO = Double.parseDouble(descripcion11.elementAt(cont1).toString());}
             catch (Exception e){CapitalO = 0;}
             try{CapitalA = Double.parseDouble(descripcion12.elementAt(cont1).toString());}
             catch (Exception e){CapitalA = 0;}
             out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=v_consecutivo VALUE='"+v_descripcion.elementAt(i).toString()+"*"+v_descripcion.elementAt(i+1).toString()+"*"+v_descripcion.elementAt(i+4).toString()+"*"+v_descripcion.elementAt(i+7).toString()+"' CHECKED></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+1).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+2).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+3).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+4).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+5).toString()+"</FONT></TD>")
;
             out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Val_Aju)+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(CapitalO-CapitalA)+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(RendO-RendA)+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Cta_ConO)+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Ret_RenO)+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(PenOK+PenAK)+"</FONT></TD></TR>");
             cont++;
             cont1+=2;
             }
           else
             {
               try{Val_Aju = Double.parseDouble(v_descripcion.elementAt(i+6).toString());}
             catch (Exception e){Val_Aju = 0;}

             try{PenOK = Double.parseDouble(descripcion42.elementAt(cont).toString());}//41
             catch (Exception e){PenOK = 0;}
             try{PenAK = Double.parseDouble(descripcion44.elementAt(cont).toString());}//43
             catch (Exception e){PenAK = 0;}

             try{Ret_RenO = Double.parseDouble(descripcion32.elementAt(cont).toString());}//31
             catch (Exception e){Ret_RenO = 0;}
             try{Cta_ConO = Double.parseDouble(descripcion22.elementAt(cont).toString());}//21
             catch (Exception e){Cta_ConO = 0;}
             try{RendO = Double.parseDouble(descripcion11.elementAt(cont1+1).toString());}
             catch (Exception e){RendO = 0;}
             try{RendA = Double.parseDouble(descripcion12.elementAt(cont1+1).toString());}
             catch (Exception e){RendO = 0;}

             try{CapitalO = Double.parseDouble(descripcion11.elementAt(cont1).toString());}
             catch (Exception e){CapitalO = 0;}
             try{CapitalA = Double.parseDouble(descripcion12.elementAt(cont1).toString());}
             catch (Exception e){CapitalA = 0;}
             out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=v_consecutivo VALUE='"+v_descripcion.elementAt(i).toString()+"*"+v_descripcion.elementAt(i+1).toString()+"*"+v_descripcion.elementAt(i+4).toString()+"*"+v_descripcion.elementAt(i+7).toString()+"' CHECKED></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+1).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+2).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+3).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+4).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+5).toString()+"</FONT></TD>")
;
             out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Val_Aju)+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(CapitalO-CapitalA)+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(RendO-RendA)+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Cta_ConO)+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Ret_RenO)+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(PenOK+PenAK)+"</FONT></TD></TR>");
             cont++;
             cont1+=2;
              }
           }
        out.println("</TBODY></TABLE>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<INPUT TYPE=hidden name='v_codigo' VALUE='"+v_codigo+"'>");
        out.println("<INPUT TYPE=hidden name='v_Nocontrato' VALUE='"+v_Nocontrato+"'>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
        out.println("<CENTER><INPUT TYPE=submit VALUE='Detallar ajuste'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
        out.println("</font>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        }
      else
        {
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR class=\"td11\" borderColor=silver align=center><TD width=\"22%\"><FONT color=white face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>No ajuste</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>No transacción retiro reversado</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>No transacción retiro reaplicado</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha efectiva</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha proceso</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Valor ajustado</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Diferencia Capital</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Diferencia rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Diferencia Cta contingente</B></FONT></TD>");
        out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Diferencia de retención rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Diferencia Penalización</B></FONT></TD></TR>");
        out.println("</TBODY></TABLE>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<INPUT TYPE=hidden name='v_codigo' VALUE='"+v_codigo+"'>");
        out.println("<INPUT TYPE=hidden name='v_Nocontrato' VALUE='"+v_Nocontrato+"'>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
        out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
        out.println("</font>");
        }
      out.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
      out.close();
      }

 
  public String getServletInfo() {
    return "TBPKT_IMPUESTOS.TBCS_Consulta_Impuetos_BTA Information";
  }
}

