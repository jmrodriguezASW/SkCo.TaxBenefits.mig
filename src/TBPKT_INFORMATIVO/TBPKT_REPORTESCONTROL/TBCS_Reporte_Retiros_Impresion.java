package TBPKT_INFORMATIVO.TBPKT_REPORTESCONTROL;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.NumberFormat;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_Reporte_Retiros_Impresion extends HttpServlet
{
   private PrintWriter out;
   private TBCL_Consulta v_Consulta = new TBCL_Consulta();


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
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String v_nuevaCadena = "";
      HttpSession session = request.getSession(true);
      if(session==null)
          session = request.getSession(true);

      session.setMaxInactiveInterval(3600);
      try
      {
         out = new PrintWriter (response.getOutputStream());
         response.setContentType("text/html");
         /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
         //response.setHeader("Pragma", "No-Cache");
         //response.setDateHeader("Expires", 0);
         String parametros[] = new String[8];
         String cadena  = "";
         try
         {
            cadena               = (String)session.getAttribute("s_cadena");
         }
         catch (Exception e)
         {
            cadena = "";
            e.printStackTrace();
         }
         v_nuevaCadena = cadena;
         String ip_tax = request.getRemoteAddr();
         TBCL_Seguridad Seguridad = new TBCL_Seguridad();
         parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
      }
      catch(Exception ex){System.out.println("");}

      //v_Consulta = new TBCL_Consulta();
      String v_dconsulta ="";
      //Vector donde se guardara el resultado de la v_dconsulta
      Vector v_resultadoconsulta = new Vector();
      Vector v_resultadoconsulta1 = new Vector();
      Vector v_resultadoconsulta2 = new Vector();

      v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

      //Toma la fecha inicial a consultar
      String fechaini = "";
      //Toma la fecha final a consultar
      String fechafin = "";
      //Toma el código de la unidad de proceso
      String unipro = "";
      String VlrNeto = "";
      String VlrBrto = "";
      String VlrCap = "";
      String VlrRen = "";
      String vusuario = "";
      String producto = "";
      
      try
      {
         fechaini = (java.lang.String)session.getAttribute("s_fechaini");//request.getParameter("fechaini").trim();
      }
      catch (Exception e) { e.printStackTrace(); }
      try
      {
         fechafin = (java.lang.String)session.getAttribute("s_fechafin");//request.getParameter("fechafin").trim();
      }
      catch (Exception e) { e.printStackTrace(); }
      try
      {
         VlrNeto = (java.lang.String)session.getAttribute("s_vlrneto");//request.getParameter("vlrneto").trim();
      }
      catch (Exception e) { e.printStackTrace(); }
      try
      {
         VlrBrto = (java.lang.String)session.getAttribute("s_vlrbrto");//request.getParameter("vlrbrto").trim();
      }
      catch (Exception e) { e.printStackTrace(); }
      try
      {
         VlrCap = (java.lang.String)session.getAttribute("s_vlrcap");//request.getParameter("vlrcap").trim();
      }
      catch (Exception e) { e.printStackTrace(); }
      try
      {
         VlrRen = (java.lang.String)session.getAttribute("s_vlrren");//request.getParameter("vlrren").trim();
      }
      catch (Exception e) { e.printStackTrace(); }
      try
      {
         unipro = (java.lang.String)session.getAttribute("s_unipro");//request.getParameter("unipro");
      }
      catch (Exception e) { e.printStackTrace(); }
      try
      {
         vusuario = (java.lang.String)session.getAttribute("s_usuario");request.getParameter("s_tran");
      }
      catch (Exception e) { vusuario= ""; }
      
      try{
          producto = (java.lang.String)session.getAttribute("s_producto");
          }
      catch (Exception e) { vusuario= ""; }
       

      if (unipro != null)
      {
       if (producto.equals("VOLUNTARIOS")) 
        {
         if (unipro.equals("UUP099"))
         {
            v_dconsulta = "SELECT               RET_CON_PRO_CODIGO,                --Producto\n"+
                          "                     RET_CON_NUMERO,                    --Contrato\n"+
                          "                     CON_NOMBRES, CON_APELLIDOS,         --Cliente\n"+
                          "                     tr.ref_valor,                      --Tipo de retiro\n"+
                          "                     RET_CONSECUTIVO,                   --Transacción Tax\n"+
                          "                     RET_TRANSACCION,                   --Transacción MF\n"+
                          "                     unidad.ref_descripcion,            --Unidad\n"+
                          "                     RET_BANCO,                         --Cuenta de retiro\n"+
                          "                     RET_INDICADOR_NO_GIRO,             --Indicador No Giro\n"+
                          "                     SUM(RET_VALOR_NETO)/COUNT(apr_apo_consecutivo),                    --Valor Neto\n"+
                          "                     SUM(RET_VALOR_BRUTO)/COUNT(apr_apo_consecutivo),                   --Valor Bruto\n"+
                          "                     SUM(APR_CAPITAL),                  --Capital\n"+
                          "                     SUM(APR_RENDIMIENTOS),             --Rendimientos\n"+
                          "                     NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC002')),0),\n"+
                          "                     NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC001')),0),\n"+
                          "                     NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC004')),0) + NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC003')),0),\n"+
                          //JAG 06-Feb-2002: Se adicionan campos al select
                          "                     decode(trim(RET_CUENTA) ,null,'CHECK',RET_CUENTA),  "+
                          "                     PRC_TRASLADO,   "+
                          "                     RET_AFP_CODIGO,  "+
                          "                     RET_RETIRO_PRORRATA,     "+
                          "                     PRC_RETIRO_TOTAL  "+
                          "FROM                 tbreferencias tr,\n"+
                          "                     tbreferencias unidad,\n"+
                          "                     tbaportes_retiros,\n"+
                          "                     tbretiros,\n"+
                          "                     tbcontratos,\n"+
                          "                     tbproductos_conceptos \n"+
                          "WHERE                APR_RET_CON_PRO_CODIGO = RET_CON_PRO_CODIGO\n"+
                          "AND                  APR_RET_CON_PRO_CODIGO  IN ('MFUND','OMPEV','SIPEN') \n" +
                          "AND                  APR_RET_CON_NUMERO = RET_CON_NUMERO\n"+
                          "AND                  APR_RET_CONSECUTIVO = RET_CONSECUTIVO\n"+
                          "AND                  (RET_REF_ESTADO = 'SER006' OR RET_REF_ESTADO = 'SER001')\n"+
                          "AND                  RET_FECHA_PROCESO\n"+
                          "BETWEEN              TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD')\n"+
                          "AND                  tr.ref_codigo = RET_COT_REF_TIPO_TRANSACCION\n"+
                          "AND                  CON_PRO_CODIGO = RET_CON_PRO_CODIGO\n"+
                          "AND                  CON_NUMERO = RET_CON_NUMERO\n"+
                          "AND                  unidad.ref_codigo = RET_REF_UNIDAD_PROCESO  "+
                          "AND                  RET_CON_PRO_CODIGO = PRC_PRO_CODIGO  "+
                          "AND                  RET_COT_REF_TRANSACCION    = PRC_COT_REF_TRANSACCION "+
                          "AND                  RET_COT_REF_TIPO_TRANSACCION  = PRC_COT_REF_TIPO_TRANSACCION  "+
                          "AND                  RET_COT_REF_CLASE_TRANSACCION = PRC_COT_REF_CLASE_TRANSACCION  ";


            try
            {
               if(!vusuario.trim().equals(""))
               {
                  v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('"+vusuario+"')  ";
               }
            }
            catch(Exception e)
            {}

            v_dconsulta += "GROUP BY             RET_CON_PRO_CODIGO,                --Producto\n"+
                           "                     RET_CON_NUMERO,                    --Contrato\n"+
                           "                     CON_NOMBRES, CON_APELLIDOS,         --Cliente\n"+
                           "                     tr.ref_valor,                      --Tipo de retiro\n"+
                           "                     RET_CONSECUTIVO,                   --Transacción Tax\n"+
                           "                     RET_TRANSACCION,                   --Transacción MF\n"+
                           "                     unidad.ref_descripcion,            --Unidad\n"+
                           "                     RET_BANCO,                         --Cuenta de retiro\n"+
                           "                     RET_INDICADOR_NO_GIRO,             --Indicador No Giro\n"+
                           "                     RET_CUENTA,                         --Cuenta de retirodesc\n"+
                           //JAG 06-Feb-2002: Se adicionan campos en el group by
                           "                     PRC_TRASLADO,   "+
                           "                     RET_AFP_CODIGO,  "+
                           "                     RET_RETIRO_PRORRATA,  "+
                           "                     PRC_RETIRO_TOTAL  "+
                           "   ORDER BY 1,2,5,8";

            v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);

            v_dconsulta = "SELECT               COUNT(distinct(ret_consecutivo)), SUM(RET_VALOR_NETO),                    --Valor Neto\n"+
                          "                     SUM(RET_VALOR_BRUTO),                   --Valor Bruto\n"+
                          "                     SUM(APR_CAPITAL),                  --Capital\n"+
                          "                     SUM(APR_RENDIMIENTOS),             --Rendimientos\n"+
                          "                     NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC002')),0),\n"+
                          "                     NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC001')),0),\n"+
                          "                     NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC004')),0) + NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC003')),0)\n"+
                          "FROM                 tbaportes_retiros,\n"+
                          "                     tbretiros\n"+
                          "WHERE                APR_RET_CON_PRO_CODIGO = RET_CON_PRO_CODIGO\n"+
                          "AND                  APR_RET_CON_PRO_CODIGO  IN ('MFUND','OMPEV','SIPEN') \n" +
                          "AND                  APR_RET_CON_NUMERO = RET_CON_NUMERO\n"+
                          "AND                  APR_RET_CONSECUTIVO = RET_CONSECUTIVO\n"+
                          "AND                  (RET_REF_ESTADO = 'SER006' OR RET_REF_ESTADO = 'SER001')\n"+
                          "AND                  RET_FECHA_PROCESO\n"+
                          "BETWEEN              TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD')";
            try
            {
               if(!vusuario.trim().equals(""))
               {
                 v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('"+vusuario+"')  ";
               }
            }
            catch(Exception e)
            {}
            v_resultadoconsulta1 = v_Consulta.TBFL_Consulta(v_dconsulta);
            v_dconsulta = " SELECT                SUM(APR_CAPITAL)        --Valor Capital\n"+
                          "                      ,SUM(APR_RENDIMIENTOS)   --Valor Rendimientos\n"+
                          " FROM                  TBRETIROS\n"+
                          "                      ,TBAPORTES_RETIROS\n"+
                          " WHERE                 (RET_REF_ESTADO = 'SER006' OR RET_REF_ESTADO = 'SER001')\n"+
                          " AND                   RET_CON_PRO_CODIGO  IN ('MFUND','OMPEV','SIPEN') \n" +
                          " AND                   RET_FECHA_PROCESO\n"+
                          " BETWEEN               TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD')\n"+
                          " AND                   APR_RET_CON_PRO_CODIGO = RET_CON_PRO_CODIGO\n"+
                          " AND                   APR_RET_CON_NUMERO = RET_CON_NUMERO\n"+
                          " AND                   APR_RET_CONSECUTIVO = RET_CONSECUTIVO ";

            try
            {
               if(!vusuario.trim().equals(""))
               {
                 v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('"+vusuario+"')  ";
               }
            }
            catch(Exception e)
            {}


             v_dconsulta += " GROUP BY              APR_RET_CON_PRO_CODIGO --Producto\n"+
                            "                      ,APR_RET_CON_NUMERO     --Num del Contrato\n"+
                            "                      ,APR_RET_CONSECUTIVO  --Tr Tax";
             v_resultadoconsulta2 = v_Consulta.TBFL_Consulta(v_dconsulta);


         }
         else if(! unipro.equals(null))
              {
                 v_dconsulta = "SELECT               RET_CON_PRO_CODIGO,                --Producto\n"+
                               "                     RET_CON_NUMERO,                    --Contrato\n"+
                               "                     CON_NOMBRES, CON_APELLIDOS,         --Cliente\n"+
                               "                     tr.ref_valor,                      --Tipo de retiro\n"+
                               "                     RET_CONSECUTIVO,                   --Transacción Tax\n"+
                               "                     RET_TRANSACCION,                   --Transacción MF\n"+
                               "                     unidad.ref_descripcion,            --Unidad\n"+
                               "                     RET_BANCO,                         --Cuenta de retiro\n"+
                               "                     RET_INDICADOR_NO_GIRO,             --Indicador No Giro\n"+
                               "                     SUM(RET_VALOR_NETO)/COUNT(apr_apo_consecutivo),                    --Valor Neto\n"+
                               "                     SUM(RET_VALOR_BRUTO)/COUNT(apr_apo_consecutivo),                   --Valor Bruto\n"+
                               "                     SUM(APR_CAPITAL),                  --Capital\n"+
                               "                     SUM(APR_RENDIMIENTOS),             --Rendimientos\n"+
                               "                     NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC002')),0),\n"+
                               "                     NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC001')),0),\n"+
                               "                     NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC004')),0) + NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC003')),0),\n"+
                               //JAG 06-Feb-2002: Se adicionan campos al select
                               "                     decode(trim(RET_CUENTA) ,null,'CHECK',RET_CUENTA),  "+
                               "                     PRC_TRASLADO,   "+
                               "                     RET_AFP_CODIGO , "+
                               "                     RET_RETIRO_PRORRATA,      "+
                               "                     PRC_RETIRO_TOTAL  "+
                               "FROM                 tbreferencias tr,\n"+
                               "                     tbreferencias unidad,\n"+
                               "                     tbaportes_retiros,\n"+
                               "                     tbretiros,\n"+
                               "                     tbcontratos,\n"+
                               "                     tbproductos_conceptos \n"+
                               "WHERE                APR_RET_CON_PRO_CODIGO = RET_CON_PRO_CODIGO\n"+
                               "AND                  APR_RET_CON_PRO_CODIGO  IN ('MFUND','OMPEV','SIPEN') \n" +
                               "AND                  APR_RET_CON_NUMERO = RET_CON_NUMERO\n"+
                               "AND                  APR_RET_CONSECUTIVO = RET_CONSECUTIVO\n"+
                               "AND                  (RET_REF_ESTADO = 'SER006' OR RET_REF_ESTADO = 'SER001')\n"+
                               "AND                  RET_REF_UNIDAD_PROCESO = '"+unipro+"'\n"+
                               "AND                  RET_FECHA_PROCESO\n"+
                               "BETWEEN              TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD')\n"+
                               "AND                  tr.ref_codigo = RET_COT_REF_TIPO_TRANSACCION\n"+
                               "AND                  CON_PRO_CODIGO = RET_CON_PRO_CODIGO\n"+
                               "AND                  CON_NUMERO = RET_CON_NUMERO\n"+
                               "AND                  unidad.ref_codigo = RET_REF_UNIDAD_PROCESO "+
                               "AND                  RET_CON_PRO_CODIGO = PRC_PRO_CODIGO  "+
                               "AND                  RET_COT_REF_TRANSACCION    = PRC_COT_REF_TRANSACCION "+
                               "AND                  RET_COT_REF_TIPO_TRANSACCION  = PRC_COT_REF_TIPO_TRANSACCION  "+
                               "AND                  RET_COT_REF_CLASE_TRANSACCION = PRC_COT_REF_CLASE_TRANSACCION  ";

                 try
                 {
                    if(!vusuario.trim().equals(""))
                    {
                       v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('"+vusuario+"')  ";
                    }
                 }
                 catch(Exception e)
                 {}

                 v_dconsulta += "GROUP BY             RET_CON_PRO_CODIGO,                --Producto\n"+
                                "                     RET_CON_NUMERO,                    --Contrato\n"+
                                "                     CON_NOMBRES, CON_APELLIDOS,         --Cliente\n"+
                                "                     tr.ref_valor,                      --Tipo de retiro\n"+
                                "                     RET_CONSECUTIVO,                   --Transacción Tax\n"+
                                "                     RET_TRANSACCION,                   --Transacción MF\n"+
                                "                     unidad.ref_descripcion,            --Unidad\n"+
                                "                     RET_BANCO,                         --Cuenta de retiro\n"+
                                "                     RET_INDICADOR_NO_GIRO,             --Indicador No Giro\n"+
                                "                     RET_CUENTA,                         --Cuenta de retiro\n"+
                               //JAG 06-Feb-2002: Se adicionan campos al group by
                                "                     PRC_TRASLADO,   "+
                                "                     RET_AFP_CODIGO, "+
                                "                     RET_RETIRO_PRORRATA,  "+
                                "                     PRC_RETIRO_TOTAL  "+
                                "ORDER BY 1,2,5,8";
                 v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);
                 v_dconsulta = "SELECT COUNT(distinct(ret_consecutivo)), SUM(RET_VALOR_NETO),                    --Valor Neto\n"+
                               "       SUM(RET_VALOR_BRUTO),                   --Valor Bruto\n"+
                               "       SUM(APR_CAPITAL),                  --Capital\n"+
                               "       SUM(APR_RENDIMIENTOS),             --Rendimientos\n"+
                               "       NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC002')),0),\n"+
                               "       NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC001')),0),\n"+
                               "       NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC004')),0) + NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC003')),0)\n"+
                               "FROM   tbaportes_retiros,\n"+
                               "       tbretiros\n"+
                               "WHERE  APR_RET_CON_PRO_CODIGO = RET_CON_PRO_CODIGO\n"+
                               "AND    APR_RET_CON_PRO_CODIGO  IN ('MFUND','OMPEV','SIPEN') \n" +                                
                               "AND    APR_RET_CON_NUMERO = RET_CON_NUMERO\n"+
                               "AND    APR_RET_CONSECUTIVO = RET_CONSECUTIVO \n"+
                               "AND    (RET_REF_ESTADO = 'SER006' OR RET_REF_ESTADO = 'SER001') \t"+
                               "AND    RET_REF_UNIDAD_PROCESO = '"+unipro+"'\n"+
                               "AND    RET_FECHA_PROCESO \t"+
                               "BETWEEN   TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD') ";
                 try
                 {
                    if(!vusuario.trim().equals(""))
                    {
                       v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('"+vusuario+"')  ";
                    }
                 }
                 catch(Exception e)
                 {}
                 v_resultadoconsulta1 = v_Consulta.TBFL_Consulta(v_dconsulta);
                 v_dconsulta = " SELECT                SUM(APR_CAPITAL)        --Valor Capital\n"+
                               "                      ,SUM(APR_RENDIMIENTOS)   --Valor Rendimientos\n"+
                               " FROM                  TBRETIROS\n"+
                               "                      ,TBAPORTES_RETIROS\n"+
                               " WHERE                 (RET_REF_ESTADO = 'SER006' OR RET_REF_ESTADO = 'SER001')\n"+
                               " AND                   RET_REF_UNIDAD_PROCESO = '"+unipro+"'\n"+
                               "AND                    RET_CON_PRO_CODIGO  IN ('MFUND','OMPEV','SIPEN') \n" +
                               " AND                   RET_FECHA_PROCESO\n"+
                               " BETWEEN               TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD')\n"+
                               " AND                   APR_RET_CON_PRO_CODIGO = RET_CON_PRO_CODIGO\n"+
                               " AND                   APR_RET_CON_NUMERO = RET_CON_NUMERO\n"+
                               " AND                   APR_RET_CONSECUTIVO = RET_CONSECUTIVO ";
                 try
                 {
                    if(!vusuario.trim().equals(""))
                    {
                       v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('"+vusuario+"')  ";
                    }
                 }
                 catch(Exception e)
                 {}

                 v_dconsulta += " GROUP BY              APR_RET_CON_PRO_CODIGO --Producto\n"+
                                "                      ,APR_RET_CON_NUMERO     --Num del Contrato\n"+
                                "                      ,APR_RET_CONSECUTIVO  --Tr Tax";
                 v_resultadoconsulta2 = v_Consulta.TBFL_Consulta(v_dconsulta);
         }
         PublicarSI(v_resultadoconsulta, v_resultadoconsulta1, v_resultadoconsulta2, fechaini, fechafin, unipro, VlrNeto, VlrBrto, VlrCap, VlrRen);
        }//fin voluntarios
       else{
           v_dconsulta = "SELECT  CON_NOMBRES,\n" + 
           "        CON_APELLIDOS,        \n" + 
           "        RET_CON_PRO_CODIGO,\n" + 
           "        RET_CON_NUMERO,\n" + 
           "        REF_VALOR,            \n" + 
           "        RET_CONSECUTIVO,      \n" + 
           "        RET_TRANSACCION END,      \n" + 
           "        RET_BANCO END,           \n" + 
           "        RET_CUENTA END,         \n" + 
           "        SUM(RET_VALOR_NETO)/COUNT(apr_apo_consecutivo),       \n" + 
           "        SUM(RET_VALOR_BRUTO)/COUNT(apr_apo_consecutivo),     \n" + 
           "        SUM(APR_CAPITAL),                 \n" + 
           "        SUM(APR_RENDIMIENTOS),            \n" + 
           "        NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC002')),0) CC,  \n" + 
           "        NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC001')),0) RET_REND, \n" + 
           "        NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC004')),0) + \n" + 
           "        NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC003')),0) PENALI  \n" + 
           "FROM       TBRETIROS_OBLIG\n" + 
           "INNER JOIN TBREFERENCIAS           ON REF_CODIGO             = RET_COT_REF_TIPO_TRANSACCION \n" + 
           "INNER JOIN TBAPORTES_RETIROS_OBLIG ON APR_RET_CON_PRO_CODIGO = RET_CON_PRO_CODIGO \n" + 
           "                                   AND APR_RET_CON_NUMERO     = RET_CON_NUMERO \n" + 
           "                                   AND APR_RET_CONSECUTIVO    = RET_CONSECUTIVO \n" + 
           "INNER JOIN TBCONTRATOS_OBLIG       ON CON_PRO_CODIGO         = RET_CON_PRO_CODIGO \n" + 
           "                                   AND CON_NUMERO             = RET_CON_NUMERO \n" + 
           "WHERE APR_RET_CON_PRO_CODIGO  = '"+producto.trim()+"' \n" + 
           "        AND RET_REF_ESTADO = 'SER006' \n" + 
           "        AND RET_FECHA_PROCESO BETWEEN TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD') \n";

           if (!vusuario.trim().equals("")) {
               v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('" + vusuario + "')  ";
           }
           
           v_dconsulta += "GROUP BY CON_NOMBRES,\n" + 
                           "         CON_APELLIDOS,  \n" + 
                           "         RET_CON_PRO_CODIGO, \n" + 
                           "         RET_CON_NUMERO, \n" + 
                           "         REF_VALOR, \n" + 
                           "         RET_CONSECUTIVO, \n" + 
                           "         RET_TRANSACCION, \n" + 
                           "         RET_BANCO, \n" + 
                           "         RET_CUENTA \n" + 
                           "ORDER BY 3,4,1,2,6,8";  
           v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);
           PublicarSI(v_resultadoconsulta, fechaini, fechafin, unipro, VlrNeto, VlrBrto, VlrCap, VlrRen);
       }
      }
      
       v_Consulta.TBPL_shutDown();
   }
   private void PublicarSI(Vector valor, Vector valor1, Vector valor2, String fechaini, String fechafin, String unipro, String VlrNeto, String VlrBrto, String VlrCap, String VlrRen)
   {
      int contador = 0;
      boolean vexifon = false;
      double vfilas =0;
      double v_val = 0;
      String vpintar = "";
      //JAG 06-Feb-2002: Vector para tener resultado de los fondos
      Vector v_resultadofondos = new Vector();
      valor.trimToSize();
      valor1.trimToSize();
      valor2.trimToSize();
      //JAG 06-Feb-2002: nuevas variables para el manejo de totales de PS y FS
      int vtotretps = 0;
      int vtotretfs = 0;
      double v_valorneto_ps = 0;
      double v_valorbruto_ps = 0;
      double v_capital_ps = 0;
      double v_rendimiento_ps = 0;
      double v_ctacon_ps = 0;
      double v_retren_ps = 0;
      double v_pena_ps = 0;
      double v_valorneto_fs = 0;
      double v_valorbruto_fs = 0;
      double v_capital_fs = 0;
      double v_rendimiento_fs = 0;
      double v_ctacon_fs = 0;
      double v_retren_fs = 0;
      double v_pena_fs = 0;
      //Aqui van los campo que se quieren mostrar
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Reportes de Control</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<p align=\"center\"><big><big><strong>Reportes de control</strong></big></big></p>");
      out.println("<p align=\"center\"><big><strong>Transacciones procesadas para RETIROS de:&nbsp;"+TBFL_ConsultaDescripcion(unipro)+"</strong></big></p>");
      out.println("<div align=\"center\"><center>");
      out.println("<TABLE bgColor=#ffffff border=0 cellPadding=0 cellSpacing=0 width=650><TBODY>");
      out.println("<TR><!-- row 2 -->");
      out.println("<TD align=left vAlign=top><!-- Empieza TD1 -->");
      int i = 0;
      for (i=0; i<valor.size(); i+=22)
      {
         contador ++;
         if (contador%7==0)
         {
           contador = 1;
           //2002-May-28 Se quita código que deja espacio entre tablas.
           vpintar = "<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=1 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY> "+
                      "<TR bgColor=white borderColor=black align=left><TD width=\"5%\"><FONT color=black face=verdana size=1><B>Cliente</B>&nbsp;&nbsp;&nbsp;"+valor.elementAt(i+2).toString()+"&nbsp;&nbsp;"+valor.elementAt(i+3).toString()+"</FONT></TD> "+
                      "</TR></TABLE>";
           vfilas = vfilas + 2;
           vpintar += "<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=8 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY>"+
                       "<TR bgColor=white borderColor=black align=left>"+
                       "<TD width=\"5%\"><FONT color=black face=verdana size=1><B>Producto y # de Contrato</B><BR>"+valor.elementAt(i).toString()+"&nbsp;"+valor.elementAt(i+1).toString()+"</FONT></TD>"+
                       "<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Tipo de retiro</B><BR>"+valor.elementAt(i+4).toString()+"</FONT></TD>"+
                       "<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Tr. TAX</B><BR>"+valor.elementAt(i+5).toString()+"</FONT></TD>"+
                       "<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Tr. MF</B><BR>"+valor.elementAt(i+6).toString()+"</FONT></TD>"+
                       "<TD width=\"15%\"><FONT color=black face=verdana size=1><B>UNIDAD</B><BR>"+valor.elementAt(i+7).toString()+"</FONT></TD>";
           //JAG 06-Feb-2002: En el campo Cta retiro para un egreso por cheque debe aparecer CHECK y si el retiri es traslado sale TO y codigo fondo
           if(valor.elementAt(i+18).toString().equals("S"))
           {
             vpintar += "<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Cta. de Retiro</B><BR>TO ("+valor.elementAt(i+19).toString()+")</FONT></TD>";
           }
           else if (valor.elementAt(i+17).toString().equals("CHECK"))
                 {
                    vpintar +="<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Cta. de Retiro</B><BR>"+valor.elementAt(i+17).toString()+"</FONT></TD>";
                 }
                 else
                 {
                     vpintar +="<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Cta. de Retiro</B><BR>"+valor.elementAt(i+17).toString()+"(Banco&nbsp;"+valor.elementAt(i+8).toString()+")</FONT></TD>";
                 }
           vpintar +="<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Indicador de NO GIRO</B><BR>"+valor.elementAt(i+9).toString()+"</FONT></TD></TR>"+
                     "</TABLE>";
           vfilas+=3;
           vpintar +="<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=7 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY>"+
                     "<TR bgColor=white borderColor=black align=left><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Neto</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+10).toString()))+"</FONT></TD>"+
                      "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Bruto</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+11).toString()))+"</FONT></TD>"+
                      "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Capital</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+12).toString()))+"</FONT></TD>"+
                      "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Rendimientos</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+13).toString()))+"</FONT></TD>"+
                      "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cta. Contingente</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+14).toString()))+"</FONT></TD>"+
                      "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Ret. Rendimientos</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+15).toString()))+"</FONT></TD>"+
                      "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penalización</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+16).toString()))+"</FONT></TD></TR>";
           //JAG 06-Feb-2002: Consulta de distribucion de fondos si retiro no es prorrata
           if(valor.elementAt(i+20).toString().equals("N"))
           {
             vpintar +="</TABLE>";
             vfilas+=3;
             String vconsulta = " SELECT  rpad(ref_descripcion,50,'     '),rpad(dif_valor,15,'     '),rpad(dif_porcentaje,6,'     ')"+
                                 "   FROM tbdistribucion_fondos  "+
                                  "        ,tbreferencias  "+
                                  "  WHERE dif_ret_con_pro_codigo =   '"+valor.elementAt(i).toString()+"'  "+
                                  "    AND dif_ret_con_numero     =   '"+valor.elementAt(i+1).toString()+"'  "+
                                  "    AND dif_ret_consecutivo    =   "+valor.elementAt(i+5).toString()+"  "+
                                  "    and dif_ref_fondo          = ref_codigo  ";
             //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
             v_resultadofondos  =  v_Consulta.TBFL_Consulta(vconsulta);
             //v_Consulta.TBPL_shutDown();
             v_resultadofondos.trimToSize();
             vpintar +="<TABLE bgColor=white border=1 borderColor=black cellPadding=3 cellSpacing=0 cols=1 HSPACE=0 VSPACE=0 width=\"100%\"> "+
                         " <TBODY> "+
                         " <TR bgColor=white borderColor=black align=left> ";
             //se recorre para llenar fondos
             for (int y = 0; y<v_resultadofondos.size(); y+=3)
             {
               vfilas= vfilas + 0.8;
               vexifon = true;
               //pinta fondos
               if(y==0)
               {
                 vpintar +="<TD width=\"22%\"><PRE><FONT color=black face=verdana size=1> "+
                                "<B>"+v_resultadofondos.elementAt(y).toString()+"</B></FONT> ";
               }
               else
               {
                 vpintar +="<br><br><FONT color=black face=verdana size=1> "+
                                "<B>"+v_resultadofondos.elementAt(y).toString()+"</B></FONT>";
               }
             }
             vpintar +="</Td>";
             //recorre para llenar valores
             for (int y = 1; y<v_resultadofondos.size(); y+=3)
             {
               //pinta fondos
               if(y==1)
               {
                 vpintar +="<TD width=\"22%\"><PRE><FONT color=black face=verdana size=1> "+
                              ""+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_resultadofondos.elementAt(y).toString()))+"</FONT> ";
               }
               else
               {
                 vpintar +="<br><br><FONT color=black face=verdana size=1> "+
                                 " "+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_resultadofondos.elementAt(y).toString()))+"</FONT>";
               }
             }
             vpintar +="</Td>";
             //recorre para llenar porcentajes
             for (int y = 2; y<v_resultadofondos.size(); y+=3)
             {
               //pinta fondos
               if(y==2)
               {
                 vpintar +="<TD width=\"22%\"><PRE><FONT color=black face=verdana size=1> "+
                             "%"+v_resultadofondos.elementAt(y).toString()+"</FONT> ";
               }
               else
               {
                 vpintar +="<br><br><FONT color=black face=verdana size=1> "+
                                 " %"+v_resultadofondos.elementAt(y).toString()+"</FONT>";
               }
             }
             vpintar +="</Td>";
             vpintar +="</TR></pre></TABLE>&nbsp;";
           }
           else
           {
             vfilas+=3;
             vpintar +="</TABLE>&nbsp;";
           }
         }
         else
         {
            vpintar = "<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=1 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY> "+
                      "<TR bgColor=white borderColor=black align=left><TD width=\"5%\"><FONT color=black face=verdana size=1><B>Cliente:</B>&nbsp;&nbsp;&nbsp;"+valor.elementAt(i+2).toString()+"&nbsp;&nbsp;"+valor.elementAt(i+3).toString()+"</FONT></TD> "+
                      "</TR></TABLE>";
            vfilas= vfilas + 2;
            vpintar += "<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=8 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY>"+
                       "<TR bgColor=white borderColor=black align=left>"+
                       "<TD width=\"5%\"><FONT color=black face=verdana size=1>"+
                       "<B>Producto y # de Contrato</B><BR>"+valor.elementAt(i).toString()+"&nbsp;"+valor.elementAt(i+1).toString()+"</FONT></TD>"+
                       "<TD width=\"15%\"><FONT color=black face=verdana size=1>"+
                       "<B>Tipo de retiro</B><BR>"+valor.elementAt(i+4).toString()+"</FONT></TD>"+
                       "<TD width=\"15%\"><FONT color=black face=verdana size=1>"+
                       "<B>Tr. TAX</B><BR>"+valor.elementAt(i+5).toString()+"</FONT></TD>"+
                       "<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Tr. MF</B><BR>"+valor.elementAt(i+6).toString()+"</FONT></TD>"+
                       "<TD width=\"15%\"><FONT color=black face=verdana size=1><B>UNIDAD</B><BR>"+valor.elementAt(i+7).toString()+"</FONT></TD>";
            //JAG 06-Feb-2002: En el campo Cta retiro para un egreso por cheque debe aparecer CHECK y si el retiri es traslado sale TO y codigo fondo
            if(valor.elementAt(i+18).toString().equals("S"))
            {
               vpintar +="<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Cta. de Retiro</B><BR>TO ("+valor.elementAt(i+19).toString()+")</FONT></TD>";
            }
            else if (valor.elementAt(i+17).toString().equals("CHECK"))
                 {
                    vpintar +="<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Cta. de Retiro</B><BR>"+valor.elementAt(i+17).toString()+"</FONT></TD>";
                 }
                 else
                 {
                     vpintar +="<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Cta. de Retiro</B><BR>"+valor.elementAt(i+17).toString()+"(Banco&nbsp;"+valor.elementAt(i+8).toString()+")</FONT></TD>";
                 }
            vpintar +="<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Indicador de NO GIRO</B><BR>"+valor.elementAt(i+9).toString()+"</FONT></TD></TR>"+
                      " </TABLE>";
            vfilas+=3;
            vpintar +="<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=7 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY>"+
                      "<TR bgColor=white borderColor=black align=left><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Neto</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+10).toString()))+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Bruto</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+11).toString()))+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Capital</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+12).toString()))+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Rendimientos</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+13).toString()))+"</FONT></TD>"+
                      "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cta. Contingente</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+14).toString()))+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Ret. Rendimientos</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+15).toString()))+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penalización</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+16).toString()))+"</FONT></TD></TR>";
            //JAG 06-Feb-2002: Consulta de distribucion de fondos si retiro no es prorrata
            if(valor.elementAt(i+20).toString().equals("N"))
            {
              vpintar +="</TABLE>";
              vfilas+=3;
              String vconsulta = " SELECT  rpad(ref_descripcion,50,'     '),rpad(dif_valor,15,'     '),rpad(dif_porcentaje,6,'     ')"+
                                  "   FROM tbdistribucion_fondos  "+
                                  "        ,tbreferencias  "+
                                  "  WHERE dif_ret_con_pro_codigo =   '"+valor.elementAt(i).toString()+"'  "+
                                  "    AND dif_ret_con_numero     =   '"+valor.elementAt(i+1).toString()+"'  "+
                                  "    AND dif_ret_consecutivo    =   "+valor.elementAt(i+5).toString()+"  "+
                                  "    and dif_ref_fondo          = ref_codigo  ";

               //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
               v_resultadofondos  =  v_Consulta.TBFL_Consulta(vconsulta);
               //v_Consulta.TBPL_shutDown();
               v_resultadofondos.trimToSize();
               vpintar +="<TABLE bgColor=white border=1 borderColor=black cellPadding=3 cellSpacing=0 cols=1 HSPACE=0 VSPACE=0 width=\"100%\"> "+
                         " <TBODY> "+
                         " <TR bgColor=white borderColor=black align=left> ";
               //se recorre para llenar fondos
               for (int y = 0; y<v_resultadofondos.size(); y+=3)
               {
                    vfilas= vfilas + 0.8;
                    vexifon = true;
                    //pinta fondos
                    if(y==0)
                    {
                      vpintar +="<TD width=\"22%\"><PRE><FONT color=black face=verdana size=1> "+
                                "<B>"+v_resultadofondos.elementAt(y).toString()+"</B></FONT> ";
                    }
                    else
                    {
                      vpintar +="<br><br><FONT color=black face=verdana size=1> "+
                                 "<B>"+v_resultadofondos.elementAt(y).toString()+"</B></FONT>";
                    }
               }
               vpintar +="</Td>";
               //recorre para llenar valores
               for (int y = 1; y<v_resultadofondos.size(); y+=3)
               {
                    //pinta fondos
                    if(y==1)
                    {
                      vpintar +="<TD width=\"22%\"><PRE><FONT color=black face=verdana size=1> "+
                                ""+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_resultadofondos.elementAt(y).toString()))+"</FONT> ";
                    }
                    else
                    {
                      vpintar +="<br><br><FONT color=black face=verdana size=1> "+
                                 " "+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_resultadofondos.elementAt(y).toString()))+"</FONT>";
                    }
               }
               vpintar +="</Td>";
               //recorre para llenar porcentajes
               for (int y = 2; y<v_resultadofondos.size(); y+=3)
               {
                    //pinta fondos
                    if(y==2)
                    {
                      vpintar +="<TD width=\"22%\"><PRE><FONT color=black face=verdana size=1> "+
                                "%"+v_resultadofondos.elementAt(y).toString()+"</FONT> ";
                    }
                    else
                    {
                      vpintar +="<br><br><FONT color=black face=verdana size=1> "+
                                 " %"+v_resultadofondos.elementAt(y).toString()+"</FONT>";
                    }
               }
               vpintar +="</Td>";

               vpintar +="</TR></pre></TABLE>&nbsp;";
            }
            else
            {
              vfilas+=3;
              vpintar +="</TABLE>&nbsp;";
            }
         }
         if ( vfilas < 58 )
         {
            //JAG 06-Feb-2002: Sumatorias de FS y PS
            if(valor.elementAt(i+21).toString().equals("N"))
            {
               //tomar valores retiro parcial
               vtotretps += 1;
               v_valorneto_ps  = v_valorneto_ps + new Double(valor.elementAt(i+10).toString()).doubleValue();
               v_valorbruto_ps = v_valorbruto_ps + new Double(valor.elementAt(i+11).toString()).doubleValue();
               v_capital_ps = v_capital_ps + new Double(valor.elementAt(i+12).toString()).doubleValue();
               v_rendimiento_ps = v_rendimiento_ps + new Double(valor.elementAt(i+13).toString()).doubleValue();
               v_ctacon_ps =  v_ctacon_ps + new Double(valor.elementAt(i+14).toString()).doubleValue();
               v_retren_ps = v_retren_ps + new Double(valor.elementAt(i+15).toString()).doubleValue();
               v_pena_ps = v_pena_ps + new Double(valor.elementAt(i+16).toString()).doubleValue();
            }
            else
            {
               vtotretfs += 1;
               //tomar valores retiro total
               v_valorneto_fs  = v_valorneto_fs + new Double(valor.elementAt(i+10).toString()).doubleValue();
               v_valorbruto_fs = v_valorbruto_fs + new Double(valor.elementAt(i+11).toString()).doubleValue();
               v_capital_fs = v_capital_fs + new Double(valor.elementAt(i+12).toString()).doubleValue();
               v_rendimiento_fs = v_rendimiento_fs + new Double(valor.elementAt(i+13).toString()).doubleValue();
               v_ctacon_fs =  v_ctacon_fs + new Double(valor.elementAt(i+14).toString()).doubleValue();
               v_retren_fs = v_retren_fs + new Double(valor.elementAt(i+15).toString()).doubleValue();
               v_pena_fs = v_pena_fs + new Double(valor.elementAt(i+16).toString()).doubleValue();
            }
            out.println(vpintar);
         }
         else
         {
            //pagina nueva
            double  f = 51- v_val;
            if(v_val >56)
               System.out.println(" ");
               //2002-May-28 Se elimina validación del tamaño de la hoja y se
               //quita el código que generaba lineas de espacio al final de la pág.
            vfilas = 0;
            i = i-22;
            vexifon = false;
            contador = 7;
         }
        v_val  = vfilas;
      }
      double vvalfin = 56- v_val;
      for(int p=0;p<=vvalfin/2;p++)
      out.println("<BR>&nbsp;&nbsp;</BR>");
      out.println("<p align=\"center\"><big><big><strong>&nbsp;&nbsp;</strong></big></big></p>");
      out.println("<p align=\"center\"><big><strong>&nbsp;&nbsp;</strong></big></p>");
      out.println("<BR>&nbsp;&nbsp;</BR>");
      //JAG 06-Feb-2002: Total de retiros FS
      out.println("<p align=\"right\"><big><strong>TOTAL FS:&nbsp;"+vtotretfs+"</strong></big></p>");
      out.println("<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=7 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY>");
      out.println("<TR bgColor=white borderColor=black align=left><TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Valor Neto(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_valorneto_fs)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Valor Bruto(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_valorbruto_fs)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1> ");
      out.println("<B>Capital(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_capital_fs)+"</FONT></TD> ");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1> ");
      out.println("<B>Rendimientos(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_rendimiento_fs)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Cta. Contingente(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_ctacon_fs)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Ret. Rendimientos(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_retren_fs)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Penalización(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_pena_fs)+"</FONT></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;</BR>");
      //JAG 06-Feb-2002: Total de retiros PS
      out.println("<p align=\"right\"><big><strong>TOTAL PS:&nbsp;"+vtotretps+"</strong></big></p>");
      out.println("<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=7 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY>");
      out.println("<TR bgColor=white borderColor=black align=left><TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Valor Neto(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_valorneto_ps)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Valor Bruto(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_valorbruto_ps)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1> ");
      out.println("<B>Capital(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_capital_ps)+"</FONT></TD> ");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1> ");
      out.println("<B>Rendimientos(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_rendimiento_ps)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Cta. Contingente(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_ctacon_ps)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Ret. Rendimientos(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_retren_ps)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Penalización(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_pena_ps)+"</FONT></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;</BR>");
      //TOTAL
      out.println("<p align=\"right\"><big><strong>TOTAL:&nbsp;"+valor1.elementAt(0)+"</strong></big></p>");
      out.println("<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=7 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY>");
      out.println("<TR bgColor=white borderColor=black align=left><TD width=\"22%\">");
      out.println("<FONT color=black face=verdana size=1>");
      out.println("<B>Valor Neto(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_valorneto_ps+v_valorneto_fs)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Valor Bruto(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_valorbruto_ps+v_valorbruto_fs)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Capital(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_capital_ps+v_capital_fs)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Rendimientos(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_rendimiento_ps+v_rendimiento_fs)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Cta. Contingente(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_ctacon_ps+v_ctacon_fs)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Ret. Rendimientos(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_retren_ps+v_retren_fs)+"</FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<B>Penalización(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_pena_ps+v_pena_fs)+"</FONT></TD>");
      out.println("</TR>");
      out.println("</TABLE>");
      out.println("</TD><!-- Termina TD1 -->");
      out.println("</TR><!-- row 2 -->");
      out.println("<TR><!-- row 1 --><TD>&nbsp;</TD></TR>");
      out.println("</TBODY></TABLE>");
      out.println("</center></div>");
      out.println("<BLOCKQUOTE><BLOCKQUOTE><BLOCKQUOTE>");
      out.println("<p align=\"right\"><strong>Fechainicial:&nbsp;"+fechaini+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fechafinal:&nbsp;"+fechafin+"<BR>YYYY-MM-DD</strong></p>");
      out.println("</BLOCKQUOTE></BLOCKQUOTE></BLOCKQUOTE>");
      out.println("</body></html>");
      out.close();
   }

    private void PublicarSI(Vector valor, String fechaini, String fechafin, String unipro, String VlrNeto, String VlrBrto, String VlrCap, String VlrRen)
    {
       String vpintar = "";
       valor.trimToSize();
       int vtotretps = 0;
       int vtotretfs = 0;
       double v_valorneto_ps = 0;
       double v_valorbruto_ps = 0;
       double v_capital_ps = 0;
       double v_rendimiento_ps = 0;
       double v_ctacon_ps = 0;
       double v_retren_ps = 0;
       double v_pena_ps = 0;
       double v_valorneto_fs = 0;
       double v_valorbruto_fs = 0;
       double v_capital_fs = 0;
       double v_rendimiento_fs = 0;
       double v_ctacon_fs = 0;
       double v_retren_fs = 0;
       double v_pena_fs = 0;
       //Aqui van los campo que se quieren mostrar
       out.println("<html>");
       out.println("<head>");
       out.println("<title>Reportes de Control</title>");
       out.println("</head>");
       out.println("<body>");
       out.println("<p align=\"center\"><big><big><strong>Reportes de control</strong></big></big></p>");
       out.println("<p align=\"center\"><big><strong>Transacciones procesadas para RETIROS de:&nbsp;"+TBFL_ConsultaDescripcion(unipro)+"</strong></big></p>");
       out.println("<div align=\"center\"><center>");
       out.println("<TABLE bgColor=#ffffff border=0 cellPadding=0 cellSpacing=0 width=650><TBODY>");
       out.println("<TR><!-- row 2 -->");
       out.println("<TD align=left vAlign=top><!-- Empieza TD1 -->");
       int i = 0;
       int i2 = 1;
       for (i=0; i<valor.size(); i+=16)
       {          
           if (valor.elementAt(i+6).toString().trim().equals("null"))
                valor.setElementAt(" ", i+6);
           if (valor.elementAt(i+7).toString().trim().equals("null"))
                valor.setElementAt(" ", i+7);
           if (valor.elementAt(i+8).toString().trim().equals("null"))
                valor.setElementAt(" ", i+8);
           vpintar += "<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=1 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY> "+
                    "<TR bgColor=white borderColor=black align=left><TD width=\"5%\"><FONT color=black face=verdana size=1><B>Cliente&nbsp;"+i2+":</B>&nbsp;&nbsp;&nbsp;"+valor.elementAt(i).toString()+"&nbsp;&nbsp;"+valor.elementAt(i+1).toString()+"</FONT></TD> "+
               "</TR></TABLE>";
             vpintar += "<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=8 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY>"+
                        "<TR bgColor=white borderColor=black align=left>"+
                        "<TD width=\"5%\"><FONT color=black face=verdana size=1>"+
                        "<B>Producto y # de Contrato</B><BR>"+valor.elementAt(i+2).toString()+"&nbsp;"+valor.elementAt(i+3).toString()+"</FONT></TD>"+
                        "<TD width=\"15%\"><FONT color=black face=verdana size=1>"+
                        "<B>Tipo de retiro</B><BR>"+valor.elementAt(i+4).toString()+"</FONT></TD>"+
                        "<TD width=\"15%\"><FONT color=black face=verdana size=1>"+
                        "<B>Tr. TAX</B><BR>"+valor.elementAt(i+5).toString()+"</FONT></TD>"+
                        "<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Tr. AS400</B><BR>"+valor.elementAt(i+6).toString()+"</FONT></TD>" +
                        //"<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Cta. de Retiro</B><BR>TO ("+valor.elementAt(i+7).toString()+")</FONT></TD></TR>"+
                        "<TD width=\"15%\"><FONT color=black face=verdana size=1><B>Cta. de Retiro</B><BR>"+valor.elementAt(i+8).toString()+"(Banco&nbsp;"+valor.elementAt(i+7).toString()+")</FONT></TD>" +
                        " </TABLE>";
             vpintar +="<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=7 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY>"+
                       "<TR bgColor=white borderColor=black align=left><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Neto</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+9).toString()))+"</FONT></TD>" +
                       "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Bruto</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+10).toString()))+"</FONT></TD>" +
                       "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Capital</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+11).toString()))+"</FONT></TD>" +
                       "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Rendimientos</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+12).toString()))+"</FONT></TD>"+
                       "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cta. Contingente</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+13).toString()))+"</FONT></TD>" +
                       "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Ret. Rendimientos</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+14).toString()))+"</FONT></TD>" +
                       "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penalización</B><BR>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+15).toString()))+"</FONT></TD></TR></TABLE><BR><BR>";                        
               if (valor.elementAt(i+4).toString().trim().equals("PS"))
               {
                   vtotretps += 1;
                   v_valorneto_ps  = v_valorneto_ps + new Double(valor.elementAt(i+9).toString()).doubleValue();
                   v_valorbruto_ps = v_valorbruto_ps + new Double(valor.elementAt(i+10).toString()).doubleValue();
                   v_capital_ps = v_capital_ps + new Double(valor.elementAt(i+11).toString()).doubleValue();
                   v_rendimiento_ps = v_rendimiento_ps + new Double(valor.elementAt(i+12).toString()).doubleValue();
                   v_ctacon_ps =  v_ctacon_ps + new Double(valor.elementAt(i+13).toString()).doubleValue();
                   v_retren_ps = v_retren_ps + new Double(valor.elementAt(i+14).toString()).doubleValue();
                   v_pena_ps = v_pena_ps + new Double(valor.elementAt(i+15).toString()).doubleValue();
                }
               else {               
                   vtotretfs += 1;
                   v_valorneto_fs  = v_valorneto_fs + new Double(valor.elementAt(i+9).toString()).doubleValue();
                   v_valorbruto_fs = v_valorbruto_fs + new Double(valor.elementAt(i+10).toString()).doubleValue();
                   v_capital_fs = v_capital_fs + new Double(valor.elementAt(i+11).toString()).doubleValue();
                   v_rendimiento_fs = v_rendimiento_fs + new Double(valor.elementAt(i+12).toString()).doubleValue();
                   v_ctacon_fs =  v_ctacon_fs + new Double(valor.elementAt(i+13).toString()).doubleValue();
                   v_retren_fs = v_retren_fs + new Double(valor.elementAt(i+14).toString()).doubleValue();
                   v_pena_fs = v_pena_fs + new Double(valor.elementAt(i+15).toString()).doubleValue(); 
               }
           i2++;
       }
       out.println(vpintar);
       out.println("<P><BR>");
       out.println("<p align=\"center\"><big><big><strong>&nbsp;&nbsp;</strong></big></big></p>");
       out.println("<p align=\"center\"><big><strong>&nbsp;&nbsp;</strong></big></p>");
       out.println("<BR>&nbsp;&nbsp;</BR>");
       //Total de retiros FS
       out.println("<p align=\"right\"><big><strong>TOTAL FS:&nbsp;"+vtotretfs+"</strong></big></p>");
       out.println("<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=7 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY>");
       out.println("<TR bgColor=white borderColor=black align=left><TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Valor Neto(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_valorneto_fs)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Valor Bruto(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_valorbruto_fs)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1> ");
       out.println("<B>Capital(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_capital_fs)+"</FONT></TD> ");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1> ");
       out.println("<B>Rendimientos(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_rendimiento_fs)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Cta. Contingente(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_ctacon_fs)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Ret. Rendimientos(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_retren_fs)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Penalización(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_pena_fs)+"</FONT></TD></TR>");
       out.println("</TABLE>");
       out.println("<BR>&nbsp;&nbsp;</BR>");
       //Total de retiros PS
       out.println("<p align=\"right\"><big><strong>TOTAL PS:&nbsp;"+vtotretps+"</strong></big></p>");
       out.println("<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=7 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY>");
       out.println("<TR bgColor=white borderColor=black align=left><TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Valor Neto(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_valorneto_ps)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Valor Bruto(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_valorbruto_ps)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1> ");
       out.println("<B>Capital(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_capital_ps)+"</FONT></TD> ");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1> ");
       out.println("<B>Rendimientos(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_rendimiento_ps)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Cta. Contingente(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_ctacon_ps)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Ret. Rendimientos(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_retren_ps)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Penalización(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_pena_ps)+"</FONT></TD></TR>");
       out.println("</TABLE>");
       out.println("<BR>&nbsp;&nbsp;</BR>");
       //TOTAL
       out.println("<TABLE bgColor=white border=1 borderColor=black cellPadding=1 cellSpacing=0 cols=7 HSPACE=0 VSPACE=0 width=\"100%\"><TBODY>");
       out.println("<TR bgColor=white borderColor=black align=left><TD width=\"22%\">");
       out.println("<FONT color=black face=verdana size=1>");
       out.println("<B>Valor Neto(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_valorneto_ps+v_valorneto_fs)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Valor Bruto(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_valorbruto_ps+v_valorbruto_fs)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Capital(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_capital_ps+v_capital_fs)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Rendimientos(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_rendimiento_ps+v_rendimiento_fs)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Cta. Contingente(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_ctacon_ps+v_ctacon_fs)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Ret. Rendimientos(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_retren_ps+v_retren_fs)+"</FONT></TD>");
       out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>");
       out.println("<B>Penalización(total)</B><BR>"+NumberFormat.getCurrencyInstance().format(v_pena_ps+v_pena_fs)+"</FONT></TD>");
       out.println("</TR>");
       out.println("</TABLE>");
       out.println("</TD><!-- Termina TD1 -->");
       out.println("</TR><!-- row 2 -->");
       out.println("<TR><!-- row 1 --><TD>&nbsp;</TD></TR>");
       out.println("</TBODY></TABLE>");
       out.println("</center></div>");
       out.println("<BLOCKQUOTE><BLOCKQUOTE><BLOCKQUOTE>");
       out.println("<p align=\"right\"><strong>Fechainicial:&nbsp;"+fechaini+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fechafinal:&nbsp;"+fechafin+"<BR>YYYY-MM-DD</strong></p>");
       out.println("</BLOCKQUOTE></BLOCKQUOTE></BLOCKQUOTE>");
       out.println("</body></html>");
       out.close();
    }   
    
   
   private String TBFL_ConsultaDescripcion(String cod)
   {
      //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
      String declaracion = "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+cod+"'";
      Vector resultadodeclaracion = v_Consulta.TBFL_Consulta(declaracion, "ref_descripcion");
      //v_Consulta.TBPL_shutDown();
      return (resultadodeclaracion.elementAt(0).toString());
   }
   /**
   * Get Servlet information
   * @return java.lang.String
   */
   public String getServletInfo()
   {
      return "TBPKT_REPORTESCONTROL.TBCS_Reporte_Retiros_Impresion Information";
   }
}

