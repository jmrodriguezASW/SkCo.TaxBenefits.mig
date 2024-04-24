package TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE;

import TBPKT_UTILIDADES.TBPKT_AS400_APORTES.TBCL_FUNCIONES_AS400_APORTES;

import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_FECHAS.TBCL_Fecha;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTMLII;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.TBCL_Seguridad;
import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_VALOR_UNIDAD_CC;
import co.oldmutual.taxbenefit.util.DataSourceWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.NumberFormat;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE.*;

import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.TBCL_FuncionesAs400_Oblig;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServlet;

public class TBCS_InformativoAporte_Oblig extends HttpServlet{
    private PrintWriter out;
    private TBCL_Consulta v_Consulta;
    private String v_nuevaCadena ="";
    HttpSession session  = null;
    SQL_VALOR_UNIDAD_CC i_unidad =  new SQL_VALOR_UNIDAD_CC();/**Instancia de la clase TBCL_GenerarBaseHTML*/
    double[] v_valuni2     = new double[3];/**Valor unidad*/
                                                              
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try{
         out = new PrintWriter (response.getOutputStream());
         session = request.getSession(true);
         if(session==null)
          session = request.getSession(true);
         session.setMaxInactiveInterval(3600);
         response.setContentType("text/html");         
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
      Vector v_resultadoConsulta = null, resultadoSaldoUnidades = null, v_resultadoDetalle = null, v_resultadoidentificacion = null, SaldoUniCargApo = null , 
          SaldoUniCargContr = null, SaldoCuenContApo = null;
      TBCL_Fecha i_fecha             = new TBCL_Fecha();/**Instancia de la clase TBCL_Fecha*/
      v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

      //Toma el consecutivo      
      double  consecutivo = 0;
      String v_opcion = "";
      String v_codigo = "";
      String v_Nocontrato = "";
      String fechaefectiva = "";
      String fechaefectiva2 = "";
      String UfechaAct = "";
      String concepto = "";
      double SaldoContrato_AVA = 0.0;
      double SaldoContrato_AVE = 0.0;
      double SaldoUnidades_AVA = 0.0;
      double SaldoUnidades_AVE = 0.0;

      try{
         consecutivo = Double.parseDouble(request.getParameter("consecutivo").split(";")[0].toString().trim());
         concepto = request.getParameter("consecutivo").split(";")[1].toString().trim();                              
         v_codigo = request.getParameter("v_codigo");
         v_Nocontrato = request.getParameter("v_Nocontrato");
         v_opcion = request.getParameter("v_opcion").toString();
         fechaefectiva = request.getParameter("fechaefectiva");
         fechaefectiva2 = i_fecha.TBFL_Fecha(fechaefectiva);
          }
      catch (Exception e) { e.printStackTrace(); }

      if (v_opcion.equals("historia"))
        {

        Consulta = "SELECT  to_char(trl_fecha, 'rrrr/mm/dd'), \n"+
                   "        to_char(trl_fecha_aporte, 'rrrr/mm/dd'), trl_cuenta_contingente, \n"+
                   "        trl_aporte_certificado, trl_capital, trl_rendimientos, \n"+
                   "        trl_ajuste, trl_razon_modificacion, trl_usuario \n"+
                   " FROM   tbtransaccion_logs \n"+
                   " WHERE  trl_tipo_transaccion ='A' \n"+
                   " AND    trl_aporte_retiro_producto = '"+consecutivo+"' \n"+
                   " ORDER BY trl_fecha, trl_linea ";
        v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);        
        PublicarHistoria(v_resultadoConsulta);
        }
       else if (v_opcion.equals("composicion"))
        {/*
        Consulta ="SELECT  to_char(MAX(trl_fecha), 'rrrr-mm-dd')\n"+
                  "  FROM  tbtransaccion_logs\n"+
                  " WHERE  trl_tipo_transaccion = 'A'\n"+
                  "   AND  trl_aporte_retiro_producto = '"+consecutivo+"'\n"+
                  " ORDER BY   trl_fecha";
        UfechaAct = v_Consulta.TBFL_Consulta(Consulta).elementAt(0).toString();  
        */
        //ESTO NO APLICA PARA OBLIG POR LO TANTO SE DEJA VACIO  
        UfechaAct = "";
        
        Consulta ="SELECT ref_valor, \n"+
                  "   con_numero_identificacion \n"+
                  "   FROM tbcontratos_oblig \n"+
                  "   INNER JOIN TBREFERENCIAS on CON_REF_TIPO_IDENTIFICACION = REF_CODIGO \n"+
                  "   WHERE con_pro_codigo = '"+v_codigo+"'\n"+
                  "     AND con_numero = '"+v_Nocontrato+"' \n";
        v_resultadoidentificacion = v_Consulta.TBFL_Consulta(Consulta);
       
        Consulta ="SELECT SUM(APO_SALDO_NUMERO_UNIDADES)\n"+
                  "    FROM tbaportes_oblig\n"+
                  "   WHERE APO_CON_PRO_CODIGO = '"+v_codigo+"'\n"+
                  "     AND apo_con_numero = '"+v_Nocontrato+"' \n" +
                  "     AND (( APO_APORTE_TRASLADO ='S' AND APO_INFORMACION_TRASLADO = 'N') OR ( APO_APORTE_TRASLADO ='N')) \n" +
                  "     AND TBFBD_TipoAporteOblig(APO_CONSECUTIVO) = '"+concepto+"' \n" +
                  "     AND APO_REF_ESTADO = 'SEA001' \n";
        resultadoSaldoUnidades = v_Consulta.TBFL_Consulta(Consulta);        
        
        for (int i = 0; i<resultadoSaldoUnidades.size(); i++)
            if (resultadoSaldoUnidades.elementAt(i).toString().equals("null"))
                resultadoSaldoUnidades.setElementAt("No hay elementos", i);   

            Consulta =" SELECT  apo_transaccion,\n" + 
            "                   afp_descripcion,\n" + 
            "                   CASE WHEN apo_saldo_numero_unidades = 0 THEN 0 ELSE apo_saldo_capital END apo_saldo_capital ,\n" + 
            "                   CASE WHEN apo_saldo_numero_unidades = 0 THEN 0 ELSE apo_saldo_cuenta_contingente END apo_saldo_cuenta_contingente,\n" + 
            "                   apo_aporte_certificado,\n" + 
            "                   apo_aporte_beneficio,\n" + 
            "                   to_char(apo_fecha_beneficio, 'rrrr-mm-dd'),\n" + 
            "                   apo_razon_beneficio,\n" + 
            "                   tbfbd_tipoaporteoblig(APO_CONSECUTIVO) Concepto,\n" + 
            "                   '' emp_descripcion\n" + 
            "           FROM    tbaportes_oblig tb\n" + 
            "           LEFT JOIN   tbadm_fondos_pensiones tfp on CASE WHEN apo_con_pro_codigo='FPOB' THEN tfp.afp_codigo_fpob ELSE tfp.afp_codigo_fpal END = tb.apo_afp_codigo \n" + 
           // "           LEFT JOIN   tbempresas te on te.emp_grupo = tb.apo_emp_grupo\n" + 
            "           WHERE       apo_con_pro_codigo = '"+v_codigo+"'   \n" + 
            "                       AND     apo_con_numero= "+v_Nocontrato+"     \n" + 
            "                       AND     apo_consecutivo = "+consecutivo;
                
            v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta); 

        if (resultadoSaldoUnidades.elementAt(0).toString().equals("No hay elementos") || resultadoSaldoUnidades.elementAt(0) == null || resultadoSaldoUnidades.elementAt(0).toString().equals("0"))
                PublicarComposicion(v_resultadoConsulta, consecutivo, UfechaAct, "0");
        else {
            Modelo_TB_Referencias objModelo_TB_Referencias = new Modelo_TB_Referencias();
            SQL_TB_FREFERENCIAS_FPOB objSQL_TB_FREFERENCIAS_FPOB = new SQL_TB_FREFERENCIAS_FPOB();
            objModelo_TB_Referencias = objSQL_TB_FREFERENCIAS_FPOB.GET_TB_FREFERENCIAS_FPOB(v_codigo);                       
            
            TBCL_FuncionesAs400_Oblig Saldo_Contrato = new TBCL_FuncionesAs400_Oblig();

            String resultsaldo = Saldo_Contrato.TBFL_Saldo_Contrato_OBLIG(v_codigo,"P",v_Consulta.TBFL_Consulta("SELECT TO_CHAR(SYSDATE, 'YYYYMMDD') FROM DUAL").elementAt(0).toString(),
                                                                    v_resultadoidentificacion.elementAt(0).toString(),
                                                                    v_resultadoidentificacion.elementAt(1).toString(),                                                                    
                                                                    objModelo_TB_Referencias.getLibreria(),
                                                                    objModelo_TB_Referencias.getSistema(), 
                                                                    objModelo_TB_Referencias.getUsuario(),
                                                                    objModelo_TB_Referencias.getPassword());                        
            
            if(!resultsaldo.substring(0,5).toUpperCase().equals("ERROR")) {                            
                SaldoContrato_AVA = Double.parseDouble( resultsaldo.split(";")[0]);  
                SaldoContrato_AVE = Double.parseDouble( resultsaldo.split(";")[1]);  
            
                    if (concepto.equals("N") && SaldoContrato_AVA>0) {
                
                        SaldoUnidades_AVA = Double.parseDouble(resultadoSaldoUnidades.elementAt(0).toString());  
                        
                        Consulta = "SELECT CASE WHEN apo_saldo_numero_unidades = 0 THEN 0 ELSE" +
                                "   ((APO_SALDO_NUMERO_UNIDADES * " + SaldoContrato_AVA + " / " + SaldoUnidades_AVA +
                                "           ) -  APO_SALDO_CAPITAL) END  Saldo_Rendimientos \n" +
                                "   FROM    TBAPORTES_OBLIG\n" +
                                "   WHERE   APO_CON_PRO_CODIGO = '" + v_codigo + "' \n" +
                                "           AND apo_con_numero = '" + v_Nocontrato + "' \n" +                    
                                "           AND APO_REF_ESTADO = 'SEA001' \n" +
                                "           AND apo_informacion_traslado ='N' \n" +
                                "           AND apo_saldo_numero_unidades >= 0 \n" +
                                "           AND apo_consecutivo = "+consecutivo+" \n" +
                                "           AND TBFBD_TipoAporteOblig(APO_CONSECUTIVO) = '"+concepto+"' \n" +
                                "           ORDER BY apo_fecha_aporte ASC";
                        v_resultadoDetalle = v_Consulta.TBFL_Consulta(Consulta);
                    }
                    
                    if (concepto.equals("S") && SaldoContrato_AVE>0) {
                        
                        SaldoUnidades_AVE = Double.parseDouble(resultadoSaldoUnidades.elementAt(0).toString());  
                        
                        Consulta = "SELECT ((APO_SALDO_NUMERO_UNIDADES * "+ SaldoContrato_AVE + " / " + SaldoUnidades_AVE +
                                "           ) -  APO_SALDO_CAPITAL)  Saldo_Rendimientos \n" +
                                "   FROM    TBAPORTES_OBLIG\n" +
                                "   WHERE   APO_CON_PRO_CODIGO = '" + v_codigo + "' \n" +
                                "           AND apo_con_numero = '" + v_Nocontrato + "' \n" +                    
                                "           AND APO_REF_ESTADO = 'SEA001' \n" +
                                "           AND apo_informacion_traslado ='N' \n" +
                                "           AND apo_saldo_numero_unidades >= 0 \n" +
                                "           AND apo_consecutivo = "+consecutivo+" \n" +
                                "           AND TBFBD_TipoAporteOblig(APO_CONSECUTIVO) = '"+concepto+"' \n" +   
                                "           ORDER BY apo_fecha_aporte ASC";
                        v_resultadoDetalle = v_Consulta.TBFL_Consulta(Consulta);                    
                    }
                                                            
                PublicarComposicion(v_resultadoConsulta, consecutivo, UfechaAct, v_resultadoDetalle.elementAt(0).toString());
            }
            else {
                PublicarComposicion(v_resultadoConsulta, consecutivo, UfechaAct, "0");
            }
        }
        }
      else if (v_opcion.equals("retiros"))
        {
        Consulta = " SELECT to_char(ret_fecha_efectiva, 'RRRR-MM-DD'),\n"+
                   " apr_capital,\n"+
                   " apr_rendimientos,\n"+
                   " NVL(TBFBD_CARGOS_RETIROS(RET_CON_PRO_CODIGO,  RET_CON_NUMERO, ret_consecutivo, apr_apo_consecutivo, 'STC002'),0), \n" +
                   " tbfbd_tipoaporteoblig(APO_CONSECUTIVO) Concepto"+
                   " FROM TBRETIROS_OBLIG\n" + 
                   " INNER JOIN TBAPORTES_RETIROS_OBLIG  ON APR_RET_CON_PRO_CODIGO  = RET_CON_PRO_CODIGO\n" + 
                   "                                        AND APR_RET_CON_NUMERO  = RET_CON_NUMERO\n" + 
                   "                                        AND APR_RET_CONSECUTIVO = RET_CONSECUTIVO\n" + 
                   " INNER JOIN TBAPORTES_OBLIG          ON APR_RET_CON_PRO_CODIGO  = APO_CON_PRO_CODIGO\n" + 
                   "                                        AND APR_APO_CONSECUTIVO = APO_CONSECUTIVO\n" + 
                   "                                        AND APR_RET_CON_NUMERO  = APO_CON_NUMERO " +
                   " WHERE  ret_con_pro_codigo    = '"+v_codigo+"' \n"+
                   " AND ret_con_numero           = "+v_Nocontrato+" \n"+
                   " AND ret_con_pro_codigo       = apr_ret_con_pro_codigo \n"+
                   " AND ret_con_numero           = apr_ret_con_numero \n"+
                   " AND ret_consecutivo          = apr_ret_consecutivo \n"+
                   " AND ret_ref_estado          in ('SER001','SER006') \n"+
                   " AND APR_APO_CONSECUTIVO      = "+consecutivo+" \n" +
                   " AND APO_CON_PRO_CODIGO       = ret_con_pro_codigo \n" + 
                   " AND APO_CON_NUMERO           = ret_con_numero \n" + 
                   " AND APO_CONSECUTIVO          = APR_APO_CONSECUTIVO ";

        v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);        
        PublicarRetiros(v_resultadoConsulta);
        }
        v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    }


        private void PublicarHistoria(Vector v_descripcion)
        {
        v_descripcion.trimToSize();

        //Aqui van los campo que se quieren mostrar
        if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
          {
          for (int i = 0; i<v_descripcion.size(); i++)
             if (v_descripcion.elementAt(i).toString().equals("null"))
                v_descripcion.setElementAt(" ", i);

          out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE.TBCS_HistoriaAporte", "", true));
          out.println("<BR>&nbsp;&nbsp;<BR>");
          out.println("<TABLE align=center class=\"td11\" border=0 cellPadding=0 cellSpacing=0 width=\"100%\">\n"+
                    "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Historia del aporte</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
          out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=6 rules=all>");
          out.println("<TR align=middle class=\"td11\" borderColor=silver><TD><FONT color=white face=verdana size=1><B>Fecha Cambio<B></font></TD><TD><FONT color=white face=verdana size=1><B>Fecha aporte</B></font></TD><TD><FONT color=white face=verdana size=1><B>Cuenta contingente</B></font></TD><TD><FONT color=white face=verdana size=1><B>Aporte<BR>certificado</B></font>");
          out.println("</TD><TD><FONT color=white face=verdana size=1><B>Capital</B></font></TD><TD><FONT color=white face=verdana size=1><B>Rendimiento</B></font></TD><TD><FONT color=white face=verdana size=1><B>Ajuste</B></font></TD><TD><FONT color=white face=verdana size=1><B>Razón modificación</B></font></TD><TD><FONT color=white face=verdana size=1><B>Usuario</B></font></TD></TR>");
          for (int i=0; i<v_descripcion.size(); i+=9)
             {
             out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+1).toString()+"</font>\n</TD><TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+2).toString())));
             out.println("</font>\n</TD><TD><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+3).toString()+"</font>\n</TD><TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+4).toString()))+"</font>\n</TD><TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+5).toString()))+"</font></TD>");
             out.println("</font>\n</TD><TD><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+6).toString()+"</font>\n</TD><TD><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+7).toString()+"</font>\n</TD><TD><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+8).toString()+"</font></TD></TR>");
             }
          out.println("</TABLE>");
          }
        else
          {
          out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "", v_descripcion.elementAt(0).toString(), false));
          }

          out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
          out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
          out.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
          out.close();
        }


    private void PublicarComposicion(Vector v_descripcion, double consecutivo, String UfechaAct, String Rendimientos)
    {                
        v_descripcion.trimToSize();
        DecimalFormat v_format = new  DecimalFormat("¤###,###,###,###,###,###.##");
        if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
          {
          
          for (int i = 0; i<v_descripcion.size(); i++)
             if (v_descripcion.elementAt(i).toString().equals("null"))
               v_descripcion.setElementAt(" ", i);
        
        if (Rendimientos.equals("No hay elementos"))
            Rendimientos = "0";
        
          String concepto = v_descripcion.elementAt(8).equals("S")?"AVE":"AVA";
          out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de Aportes","Consulta de Aportes", "", "", true));
          //Aqui van los campo que se quieren mostrar
          out.println("<BR>&nbsp;&nbsp;<BR>");
          
          out.println("<TABLE align=center class=\"td11\" border=0 cellPadding=0 cellSpacing=0 width=\"100%\">\n"+
                      "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Composición Actual del Aporte</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
          out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de transacción Tax</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+Math.round(consecutivo)+"</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de transacción Sistema Origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"</font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>AFP origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Saldo Capital</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_format.format(Double.parseDouble(v_descripcion.elementAt(2).toString()))+"</font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Saldo rendimientos</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_format.format(Double.parseDouble(Rendimientos))+"</font></TD></TR>");                
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Saldo Cuenta Contingente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_format.format(Double.parseDouble(v_descripcion.elementAt(3).toString()))+"</font></TD></TR>");  
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Certificado</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(5).toString()+"</font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha Beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(6).toString()+"&nbsp;</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Razón Beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(7).toString()+"</font></TD></TR>");          
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Concepto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+concepto+"</font></TD></TR>");                      
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Empresa que hace el aporte</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(9).toString()+"</font></TD></TR>");
          if (UfechaAct.equals("null"))
              UfechaAct = "";
          out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Última fecha de actualización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+UfechaAct+"</font></TD></TR>");
          out.println("</TABLE>");
          }
        else
          {
          out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "", v_descripcion.elementAt(0).toString(), false));
          }

        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
        out.close();
        }



        private void PublicarRetiros(Vector v_descripcion)
        {
        double  Sumacapital = 0;
        double  Sumarendimientos = 0;
        double  Sumactacontingente = 0;
        String concepto = "";

        v_descripcion.trimToSize();
        if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
          {

           for (int i = 0; i<v_descripcion.size(); i++)
              if (v_descripcion.elementAt(i).toString().equals("null"))
                v_descripcion.setElementAt(" ", i);

          out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "", "", true));
          //Aqui van los campo que se quieren mostrar
          out.println("<BR>&nbsp;&nbsp;<BR>");
          out.println("<TABLE align=center class=\"td11\" border=0 cellPadding=0 cellSpacing=0 width=\"100%\">\n"+
                    "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Retiros que han afectado el aporte</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
          out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=6 rules=all width=\"100%\">");
          out.println("<TR align=middle class=\"td11\" borderColor=silver><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha</B></font></TD>");
          out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Capital</B></font></TD>");
          out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Rendimientos</B></font></TD>");
          out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Cuenta Contingente</B></font></TD>");
          out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Concepto</B></font></TD>");
          out.println("</TR>");
          for (int i=0; i<v_descripcion.size(); i+=5)
             {
             Sumacapital += (double)(new Double(v_descripcion.elementAt(i+1).toString())).doubleValue();
             Sumarendimientos += (double)(new Double(v_descripcion.elementAt(i+2).toString())).doubleValue();
             Sumactacontingente += (double)(new Double(v_descripcion.elementAt(i+3).toString())).doubleValue();
             out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font>\n</TD>");
             out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;"+NumberFormat.getCurrencyInstance().format(new Double(v_descripcion.elementAt(i+1).toString()))+"</font>\n</TD>");
             out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;"+NumberFormat.getCurrencyInstance().format(new Double(v_descripcion.elementAt(i+2).toString()))+"</font>\n</TD>");
             out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;"+NumberFormat.getCurrencyInstance().format(new Double(v_descripcion.elementAt(i+3).toString()))+"</font>\n</TD>");
             concepto = v_descripcion.elementAt(i+4).equals("S")?"AVE":"AVA";
             out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;"+concepto+"</font></TD></TR>");
             }
          out.println("<TR align=middle bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>TOTAL</B></font></TD>");
          out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;"+NumberFormat.getCurrencyInstance().format(Sumacapital)+"</font></TD>");
          out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;"+NumberFormat.getCurrencyInstance().format(Sumarendimientos)+"</font></TD>");
          out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;"+NumberFormat.getCurrencyInstance().format(Sumactacontingente)+"</font></TD><TD></TD></TR>");
          out.println("</TABLE>");
          }
        else
          {
          out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "", v_descripcion.elementAt(0).toString(), false));
          }
        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
        out.close();
        }                                                                                                                    
}
