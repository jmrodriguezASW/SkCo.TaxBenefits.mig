/*@lineinfo:filename=TBCL_RetiroOperativo_Oblig*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import java.sql.*;

import oracle.sqlj.runtime.*;

import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import javax.servlet.http.*;
import java.io.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_AS400_APORTES.TBCL_FUNCIONES_AS400_APORTES;
import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;
import TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.math.*;


public class TBCL_RetiroOperativo_Oblig extends HttpServlet{

    public void TBPL_SolicitudOperativo_Oblig(PrintWriter out,HttpSession session,HttpServletRequest request,String nuevaCadena) {
        
        STBCL_GenerarBaseHTML  i_pagina = new STBCL_GenerarBaseHTML();/**Instancia de la clase TBCL_GenerarBaseHTML*/
        String v_pintar        = "";/**Variable  dibujar inicio pagina*/
        String v_pie           = "";/**Variable  dibujar final pagina*/
        String v_retiro        = "";/**Variable tipo retiro*/
        String v_tipotran      = "";/**Variable tipo transacci�n*/
        String v_clasetran     = "";/**Variable Clase transacci�n*/
        String v_diasmenor     = "";/**Variable d�as menor*/
        String v_diasmayor     = "";/**Variable d�as mayor*/
        String v_penaliza      = "";/**Variable concepto de penalizaci�n*/
        String v_traslado      = "";/**Variable concepto retiro traslado*/
        String v_total         = "";/**Variable concepto retiro total*/
        String esTercero       = "N";/**Variable para identificar operaci�n a tercero*/
        String v_contra        = "";/**Variable numero contrato*/
        String v_pro           = "";/**Variable  c�digo producto*/
        String v_nombre        = "";/**Variable nombre afiliado*/
        String v_apellidos     = "";/**Variable apellido afiliado*/
        String v_fecefe        = "";/**Variable fecha efectiva retiro*/
        String v_fecpro        = "";/**Variable feha proceso retiro*/   
        String v_tiporetiro    = "";/**Variable Descripcion tipo retiro*/
        String v_ruta_serv     = ""; 
        
        try
        { 
        if((java.lang.String)session.getAttribute("s_contrato") != null || (java.lang.String)session.getAttribute("s_producto")!= null)
        {        
           v_contra    = (java.lang.String)session.getAttribute("s_contrato");
           v_pro       = (java.lang.String)session.getAttribute("s_producto");
           v_nombre    = (java.lang.String)session.getAttribute("s_nombres");
           v_apellidos = (java.lang.String)session.getAttribute("s_apellidos");
           v_ruta_serv = (java.lang.String)session.getAttribute("s_ruta_serv");
           String s_contra = String.valueOf(Integer.parseInt(v_contra)); 
                  
           double v_saldoAVA      = 0;/**Saldo de Aporte Voluntario del Afiliado para productos de FPOB y FPAL*/
           double v_saldoAVE      = 0;/**Saldo de Aporte Voluntario de la empresa para productos de FPOB y FPAL*/
           double v_saldoTOblig   = 0;/**Saldo Total Voluntario de la empresa para productos de FPOB y FPAL*/ 
           double saldoT = 0;
                   
           /**Capturar  concepto de transaccion*/
           try { v_retiro = request.getParameter("v_retiro"); } catch (Exception e) { e.printStackTrace(); }
           v_tipotran   = v_retiro.substring(0,6);
           v_clasetran  = v_retiro.substring(6,12);
           v_total      =  v_retiro.substring(12,13);
           v_tiporetiro =  v_retiro.substring(13,v_retiro.length());           
           
           v_fecefe = request.getParameter("obligatoriov_fecefectiva");
              
           /*@lineinfo:generated-code*//*@lineinfo:72^12*/

//  ************************************************************
//  #sql { SELECT TBF_OBTENER_FECHAS_X_TRANX(:v_pro, :v_tipotran, :v_clasetran, :v_fecefe) FECHA_EFECTIVA
//                                                                                                          
//                     FROM DUAL };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT TBF_OBTENER_FECHAS_X_TRANX( :1  ,  :2  ,  :3  ,  :4  ) FECHA_EFECTIVA\n                                                                                                         \n                   FROM DUAL";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo_Oblig",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // set IN parameters
   __sJT_st.setString(1,v_pro);
   __sJT_st.setString(2,v_tipotran);
   __sJT_st.setString(3,v_clasetran);
   __sJT_st.setString(4,v_fecefe);
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   v_fecpro = (String)__sJT_rs.getString(1);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:74^29*/
                   
           session.removeAttribute("v_tipotran");
           session.setAttribute("v_tipotran", v_tipotran);                    
                
           session.removeAttribute("s_fecpro");
           session.setAttribute("s_fecpro", v_fecpro);
            
           session.removeAttribute("s_fecefectiva");
           session.setAttribute("s_fecefectiva", v_fecefe); 
           
           session.removeAttribute("s_total");
           session.setAttribute("s_total", v_total); 
           
           session.removeAttribute("s_tiporetiro");
            session.setAttribute("s_tiporetiro", v_tiporetiro);            
        
            if (v_clasetran.trim().toUpperCase().equals("UCT007")|| 
                v_tipotran.trim().toUpperCase().equals("UTT012") ||
                v_clasetran.trim().toUpperCase().equals("UCT002"))
            {
              esTercero ="S";  
            }             
            if (v_clasetran.trim().toUpperCase().equals("UCT007")|| 
                v_tipotran.trim().toUpperCase().equals("UTT012"))
            {
               v_clasetran = "UCT001";      
            }
            session.removeAttribute("esTercero");
            session.setAttribute("esTercero",esTercero);   
            
            session.removeAttribute("v_clasetran");
            session.setAttribute("v_clasetran", v_clasetran);
        
            double v_aportes_sin_cert_AVA = 0;    
            double v_aportes_sin_cert_AVE = 0;   
            double v_aportes_hist_pend_AVA = 0;   
            double v_aportes_hist_pend_AVE = 0;   
            double v_disponibleTOblig = 0;
            double v_valunidad_AVA = 0;
            double v_valunidad_AVE = 0;
            double v_prov_comi_total_AVA = 0;
            double v_prov_comi_total_AVE = 0;
            double Credito = 0;
            String[] SaldosFinales, comisiones;
            String v_libd = "";
            int v_cod_err = 0;               
            String v_men_err = "";  

        String vfecha_act, vtipoid, vnumid, v_sistema_oblig="", v_usuario_oblig="", v_password_oblig = "", v_libreria_oblig = "";
        String resultsaldo = "";
        
        int    v_sw  = 1;
        /*@lineinfo:generated-code*//*@lineinfo:127^9*/

//  ************************************************************
//  #sql { SELECT ref_valor, con_numero_identificacion,  to_char(to_date(:v_fecefe,'YYYY/MM/DD'),'YYYYMMDD') 
//                      
//                    FROM tbcontratos_oblig
//                    INNER JOIN TBREFERENCIAS on CON_REF_TIPO_IDENTIFICACION = REF_CODIGO 
//                    WHERE con_pro_codigo = :v_pro
//                    AND lpad(con_numero, 9, '0') = :v_contra };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT ref_valor, con_numero_identificacion,  to_char(to_date( :1  ,'YYYY/MM/DD'),'YYYYMMDD') \n                     \n                  FROM tbcontratos_oblig\n                  INNER JOIN TBREFERENCIAS on CON_REF_TIPO_IDENTIFICACION = REF_CODIGO \n                  WHERE con_pro_codigo =  :2  \n                  AND lpad(con_numero, 9, '0') =  :3 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo_Oblig",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // set IN parameters
   __sJT_st.setString(1,v_fecefe);
   __sJT_st.setString(2,v_pro);
   __sJT_st.setString(3,v_contra);
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 3) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(3,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   vtipoid = (String)__sJT_rs.getString(1);
   vnumid = (String)__sJT_rs.getString(2);
   vfecha_act = (String)__sJT_rs.getString(3);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:132^59*/
        
        /*@lineinfo:generated-code*//*@lineinfo:134^9*/

//  ************************************************************
//  #sql v_sw = { values(TB_FREFERENCIAS_FPOB(:v_pro,
//                                                    :v_sistema_oblig,
//                                                    :v_usuario_oblig,
//                                                    :v_password_oblig,
//                                                    :v_libreria_oblig,
//                                                    :v_libd)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FREFERENCIAS_FPOB( :2  ,\n                                                   :3  ,\n                                                   :4  ,\n                                                   :5  ,\n                                                   :6  ,\n                                                   :7  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo_Oblig",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_pro);
   __sJT_st.setString(3,v_sistema_oblig);
   __sJT_st.setString(4,v_usuario_oblig);
   __sJT_st.setString(5,v_password_oblig);
   __sJT_st.setString(6,v_libreria_oblig);
   __sJT_st.setString(7,v_libd);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_sw = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_sistema_oblig = (String)__sJT_st.getString(3);
   v_usuario_oblig = (String)__sJT_st.getString(4);
   v_password_oblig = (String)__sJT_st.getString(5);
   v_libreria_oblig = (String)__sJT_st.getString(6);
   v_libd = (String)__sJT_st.getString(7);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:139^66*/        
        
         TBCL_FuncionesAs400_Oblig Saldo_Contrato = new TBCL_FuncionesAs400_Oblig();
         resultsaldo = Saldo_Contrato.TBFL_Saldo_Contrato_OBLIG(v_pro,"E", vfecha_act, vtipoid, vnumid, v_libreria_oblig, v_sistema_oblig, v_usuario_oblig, v_password_oblig);           
         
         if(resultsaldo.substring(0,4).equals("ERROR"))
         {
            String v_pintar4=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error al procesar solicitud de Retiro. No se pudo obtener el saldo del contrato.</center>",false);
            out.println(""+v_pintar4+"");
            out.println("<BR>");
            
            v_pie = i_pagina.TBFL_PIE;
            out.println("<br>");
            out.println(""+v_pie+"");
            out.close();
         }
         else
         {
             String[] saldos = resultsaldo.split(";");
             v_saldoAVA = Double.parseDouble(saldos[0]); 
             v_saldoAVE = Double.parseDouble(saldos[1]);
             v_saldoTOblig = v_saldoAVA+v_saldoAVE;
             
             session.removeAttribute("v_saldoTOblig");
             session.setAttribute("v_saldoTOblig", String.valueOf(v_saldoTOblig));
             session.removeAttribute("v_saldoAVA");
             session.setAttribute("v_saldoAVA", v_saldoAVA);
             session.removeAttribute("v_saldoAVE");
             session.setAttribute("v_saldoAVE", v_saldoAVE);
             
             //Obtenemos de AS400 si hay credito pendiente del afiliado
             String resultCredito = Saldo_Contrato.TBFL_Saldo_Credito( v_pro, vtipoid, vnumid, v_libreria_oblig, v_sistema_oblig, v_usuario_oblig, v_password_oblig);
            
             if(!resultCredito.substring(0,3).toUpperCase().equals("ERR")) 
                Credito = Double.parseDouble(resultCredito);
            
            Modelo_TB_Referencias objModelo_TB_Referencias = new Modelo_TB_Referencias();                         
            SQL_TB_FREFERENCIAS_FPOB objSQL_TB_FREFERENCIAS_FPOB = new SQL_TB_FREFERENCIAS_FPOB();
            objModelo_TB_Referencias = objSQL_TB_FREFERENCIAS_FPOB.GET_TB_FREFERENCIAS_FPOB(v_pro);                       
            
            /*@lineinfo:generated-code*//*@lineinfo:179^13*/

//  ************************************************************
//  #sql { SELECT  to_char(LAST_DAY(to_date(:v_fecefe,'YYYY/MM/DD')),'YYYYMMDD')
//                                                                                                          
//                     FROM DUAL };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT  to_char(LAST_DAY(to_date( :1  ,'YYYY/MM/DD')),'YYYYMMDD')\n                                                                                                         \n                   FROM DUAL";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo_Oblig",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // set IN parameters
   __sJT_st.setString(1,v_fecefe);
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   vfecha_act = (String)__sJT_rs.getString(1);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:181^29*/
             
            Consultas_As400_OBLIG objConsultas_As400_OBLIG = new Consultas_As400_OBLIG();            
            comisiones = objConsultas_As400_OBLIG.GET_COMISION_ADMIN_AS400_OBLIG(v_sistema_oblig, v_libd, v_usuario_oblig, v_password_oblig, v_pro, v_contra, vfecha_act).split(";");             
            
            SQL_PTB_DISPONIBLE_OBLIG objSQL_PTB_DISPONIBLE_OBLIG = new SQL_PTB_DISPONIBLE_OBLIG();
            SaldosFinales = objSQL_PTB_DISPONIBLE_OBLIG.GET_DISPONIBLE_OBLIG(v_pro, s_contra, v_saldoAVA, v_saldoAVE, v_fecefe, comisiones, Credito);
                                     
            /*double v_comision_AVA = Double.parseDouble(comisiones[0]);
            double v_comision_AVE = Double.parseDouble(comisiones[1]);*/
                          
         if (SaldosFinales != null) 
         {                     
             if(Double.parseDouble(SaldosFinales[12].toString().replace(",","").replace("$",""))<0.1)
             {
                 SaldosFinales[12]="$0";
             }
             if(Double.parseDouble(SaldosFinales[13].toString().replace(",","").replace("$",""))<0.1)
             {
                 SaldosFinales[13]="$0";
             }
             v_disponibleTOblig =  Double.parseDouble(SaldosFinales[12].toString().replace(",","").replace("$","")) +  Double.parseDouble(SaldosFinales[13].toString().replace(",","").replace("$",""));
             
             session.removeAttribute("s_saldoava");
             session.setAttribute("s_saldoava", SaldosFinales[0].toString().replace(",","").replace("$",""));               
             session.removeAttribute("s_saldoave");
             session.setAttribute("s_saldoave", SaldosFinales[1].toString().replace(",","").replace("$",""));
             session.removeAttribute("s_comisionava");
             session.setAttribute("s_comisionava", comisiones[0]);
             session.removeAttribute("s_comisionave");
             session.setAttribute("s_comisionave", comisiones[1]);             
             session.removeAttribute("s_prov_comi_total_AVE");
             session.setAttribute("s_prov_comi_total_AVE", String.valueOf(SaldosFinales[19].toString().replace(",","").replace("$","")));
             session.removeAttribute("s_prov_comi_total_AVA");
             session.setAttribute("s_prov_comi_total_AVA", String.valueOf(SaldosFinales[18].toString().replace(",","").replace("$","")));
             
             session.removeAttribute("v_prov_comi_rend_AVA");
             session.setAttribute("v_prov_comi_rend_AVA", SaldosFinales[14].toString().replace(",","").replace("$",""));
             session.removeAttribute("v_prov_comi_capital_AVA");
             session.setAttribute("v_prov_comi_capital_AVA", SaldosFinales[15].toString().replace(",","").replace("$",""));
             
             session.removeAttribute("v_prov_comi_rend_AVE");
             session.setAttribute("v_prov_comi_rend_AVE", SaldosFinales[16].toString().replace(",","").replace("$",""));
             session.removeAttribute("v_prov_comi_capital_AVE");
             session.setAttribute("v_prov_comi_capital_AVE", SaldosFinales[17].toString().replace(",","").replace("$",""));
             
             session.removeAttribute("v_retencion_cont_AVA");
             session.setAttribute("v_retencion_cont_AVA", SaldosFinales[8].toString().replace(",","").replace("$",""));
             session.removeAttribute("v_retencion_rend_AVA");
             session.setAttribute("v_retencion_rend_AVA", SaldosFinales[10].toString().replace(",","").replace("$",""));
             
             session.removeAttribute("v_retencion_cont_AVE");
             session.setAttribute("v_retencion_cont_AVE", SaldosFinales[9].toString().replace(",","").replace("$",""));
             session.removeAttribute("v_retencion_rend_AVE");
             session.setAttribute("v_retencion_rend_AVE", SaldosFinales[11].toString().replace(",","").replace("$",""));
             
             session.removeAttribute("v_valorunidad_AVA");
             session.setAttribute("v_valorunidad_AVA", SaldosFinales[21].toString().replace(",","").replace("$",""));
             
             session.removeAttribute("v_valorunidad_AVE");
             session.setAttribute("v_valorunidad_AVE", SaldosFinales[22].toString().replace(",","").replace("$",""));
         }
         else
         {
             v_saldoTOblig = 0; 
         }

        if( v_disponibleTOblig > 0 && v_saldoTOblig>0)
         {
                 /**Dibujar p�gina de respuesta*/                
                 v_pintar=    i_pagina.TBFL_CABEZA("Detalle del Retiro","Detalle del Retiro","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_ConfirmarRetiro","","modulo_retiros_oblig.js","");                 
                 out.println(""+v_pintar+"");                                
         }
        else{
                v_pintar=    i_pagina.TBFL_CABEZA ("Detalle del Retiro","Detalle del Retiro","","<center>El Contrato no Cuenta con Saldo Disponible.</center>",false);
                out.println(""+v_pintar+"");
                out.println("<BR>");
                out.println("<center>");
                out.println("<input type=button value='Regresar' onclick=' history.go(-1)'>");
                out.println("<a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                                     "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                                     "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                                     "</FORM> </center>");                
                v_pie = i_pagina.TBFL_PIE;
                out.println("<br>");
                out.println(""+v_pie+"");
                out.close();                
            }

         String v_contrato_unif = "";
         /*@lineinfo:generated-code*//*@lineinfo:272^10*/

//  ************************************************************
//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_pro,:v_contra)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo_Oblig",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_pro);
   __sJT_st.setString(3,v_contra);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_contrato_unif = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:272^83*/
         session.removeAttribute("s_contrato_unif");
         session.setAttribute("s_contrato_unif",(java.lang.Object)v_contrato_unif);
         out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
         out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nombre+"  <b> Apellidos </b>"+v_apellidos+" </CENTER></font>");
         
         out.println("<input name= v_retotal type=HIDDEN value='x' size = '9'>");
         out.println("<INPUT ID=esTercero NAME=esTercer TYPE=hidden VALUE='"+esTercero+"'>");
         
         out.println("<BR>");
         out.println("<table width='100%' border='0' cellspacing='0' cellpadding='0' columns='2'>");         
         out.println("<TR><TD align=\"left\"><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Saldo Total:</b></TD><TD align=\"left\"><input name=v_saltot type=text value='"+NumberFormat.getCurrencyInstance().format(v_saldoTOblig)+"' size= '24' readonly ></font></TD></TR>");
         out.println("<BR>");
         out.println("<TR><TD align=\"left\"><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Saldo Disponible</b></TD><TD align=\"left\">  <input name=v_saldisponible type=text value='"+NumberFormat.getCurrencyInstance().format(v_disponibleTOblig)+"' size= '24'  readonly> </font></TD></TR>");
         out.println("</table>");
         out.println("<BR>");
         out.println("<BR>");
         out.println("<input type=hidden name=objdispo value="+ String.valueOf(v_disponibleTOblig) +">");
         out.println("<input type=hidden name=obDispAVA value="+ String.valueOf(SaldosFinales[12].toString().replace(",","").replace("$","")) +">");
         out.println("<input type=hidden name=obDispAVE value="+ String.valueOf(SaldosFinales[13].toString().replace(",","").replace("$","")) +">");
         DecimalFormat v_format = new  DecimalFormat("�###,###,###,###,###,###.##");
              String s_saldoAVA = String.valueOf(v_format.format(v_saldoAVA));
              String s_saldoAVE = String.valueOf(v_format.format(v_saldoAVE));
             /*String s_aportes_sin_cert_AVA = String.valueOf(v_format.format(SaldosFinales[2]));
              String s_aportes_sin_cert_AVE = String.valueOf(v_format.format(SaldosFinales[3]));
              String s_aportes_hist_pend_AVA = String.valueOf(v_format.format(SaldosFinales[4]));
              String s_aportes_hist_pend_AVE = String.valueOf(v_format.format(SaldosFinales[5]));
              String s_ret_pend_procesar_AVA = String.valueOf(v_format.format(SaldosFinales[6]));
              String s_ret_pend_procesar_AVE = String.valueOf(v_format.format(SaldosFinales[7]));
              String s_retencion_cont_AVA = String.valueOf(v_format.format(SaldosFinales[8]));
              String s_retencion_cont_AVE = String.valueOf(v_format.format(SaldosFinales[9]));
              String s_retencion_rend_AVA = String.valueOf(v_format.format(SaldosFinales[10]));
              String s_retencion_rend_AVE = String.valueOf(v_format.format(SaldosFinales[11]));
              String s_disponibleAVA = String.valueOf(v_format.format(SaldosFinales[12]));
              String s_disponibleAVE = String.valueOf(v_format.format(SaldosFinales[13]));
              String s_prov_credito = String.valueOf(v_format.format(SaldosFinales[20]));*/
              String s_credito = String.valueOf(v_format.format(Credito));
             
             v_pintar = i_pagina.TBFL_DISPONIBLE_OBLIG(s_saldoAVA, s_saldoAVE, 
                                                       SaldosFinales[2], SaldosFinales[3], 
                                                       SaldosFinales[4], SaldosFinales[5], 
                                                       SaldosFinales[6], SaldosFinales[7], 
                                                       SaldosFinales[8], SaldosFinales[9], 
                                                       SaldosFinales[10], SaldosFinales[11], 
                                                       String.valueOf(v_format.format(Double.parseDouble(comisiones[0]))), String.valueOf(v_format.format(Double.parseDouble(comisiones[1]))), 
                                                       SaldosFinales[12], SaldosFinales[13],
                                                       String.valueOf(SaldosFinales[18]),String.valueOf(SaldosFinales[19]),SaldosFinales[20], s_credito); 
             
               out.println(""+v_pintar+"");
        
         if(v_total.equals("N"))
         {            
            out.println("<table border=0><tr><td><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Valor a Retirar</b>                  <input type=text  name=obligatoriov_valor value='0' size= '15' maxlength = 15  onkeypress='return validaNumericos(event)'></td></tr></table>");                      
            out.println("<br>");
            out.println("<br>");
         }
        
             out.println("<table width='100%' BORDER=2 BORDERCOLOR=SILVER style='border-collapse: collapse;'>");
             out.println("<tr><td width = '20%' class=\"td11\" colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF'><b><center>M�todo de Giro</center></b></font></td><td width = '80%'><select style='font-size: 11px; width:100%;' name=v_cuenta onchange='fillWords(v_cuenta.value)'>");
             out.println("<option VALUE ='-1' selected>  </option>");
             if (esTercero.equals("N")){
                 out.println("<option VALUE ='1'>EN CHEQUE</option>");
             }
             else
             {
                 out.println("<option VALUE ='2'>EN CHEQUE</option>");  
             }
             String vsistemaoblig, vusuariooblig, vpasswordoblig, vlibreriaoblig = "";
                //SISTEMA
                /*@lineinfo:generated-code*//*@lineinfo:341^17*/

//  ************************************************************
//  #sql { SELECT REF_DESCRIPCION
//                      
//                      FROM TBREFERENCIAS
//                      WHERE REF_CODIGO = 'SNM002'
//                   };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT REF_DESCRIPCION\n                     \n                    FROM TBREFERENCIAS\n                    WHERE REF_CODIGO = 'SNM002'";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"5TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo_Oblig",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   vsistemaoblig = (String)__sJT_rs.getString(1);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:346^17*/
                //USUARIO
                /*@lineinfo:generated-code*//*@lineinfo:348^17*/

//  ************************************************************
//  #sql { SELECT REF_DESCRIPCION
//                      
//                      FROM TBREFERENCIAS
//                      WHERE REF_CODIGO = 'SUM002'
//                   };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT REF_DESCRIPCION\n                     \n                    FROM TBREFERENCIAS\n                    WHERE REF_CODIGO = 'SUM002'";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"6TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo_Oblig",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   vusuariooblig = (String)__sJT_rs.getString(1);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:353^17*/
               //PASSWORD
                /*@lineinfo:generated-code*//*@lineinfo:355^17*/

//  ************************************************************
//  #sql { SELECT REF_DESCRIPCION
//                      
//                      FROM TBREFERENCIAS
//                      WHERE REF_CODIGO = 'SPM002'
//                   };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT REF_DESCRIPCION\n                     \n                    FROM TBREFERENCIAS\n                    WHERE REF_CODIGO = 'SPM002'";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"7TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo_Oblig",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   vpasswordoblig = (String)__sJT_rs.getString(1);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:360^17*/
                
                //LIBRERIA
                /*@lineinfo:generated-code*//*@lineinfo:363^17*/

//  ************************************************************
//  #sql { SELECT REF_DESCRIPCION
//                      
//                      FROM TBREFERENCIAS
//                      WHERE REF_CODIGO = 'SLO001'
//                   };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT REF_DESCRIPCION\n                     \n                    FROM TBREFERENCIAS\n                    WHERE REF_CODIGO = 'SLO001'";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"8TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroOperativo_Oblig",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   vlibreriaoblig = (String)__sJT_rs.getString(1);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:368^17*/
               DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver());       
               Connection connection = DriverManager.getConnection ("jdbc:as400://"+ vsistemaoblig +"/",vusuariooblig,vpasswordoblig);
               Statement st = connection.createStatement();    
               ResultSet rs = st.executeQuery("SELECT CSBWCD, CSBLCD, CSCVST, CSARCD, B3CETX, AJBPTX, AJAYTX, AJVEST, AJABNB   " + 
                                                  " FROM "+vlibreriaoblig+".AACSREP CT" + 
                                                  " INNER JOIN "+vlibreriaoblig+".ULB3REP BC ON CT.CSBWCD = BC.B3BWCD" + 
                                                  " LEFT JOIN "+vlibreriaoblig+".ULAJREP TR ON CT.CSARCD = TR.AJARCD" + 
                                                  " WHERE CSX3ST='A' AND CSAMCD = '"+v_pro+"' AND CSADNB = '"+ String.valueOf(Integer.parseInt(v_contra)) +"'");
                while(rs.next()){ 
                       String codigoBanco, cuentaBanco, tipoCuenta, rol, nombreBanco, nombreTercero, apellidoTercero, textocombo, textocombo1, tipodocumentoter, numerodocter  = "";
                       codigoBanco = rs.getString(1);
                       cuentaBanco = rs.getString(2);
                       tipoCuenta = rs.getString(3);
                       rol = rs.getString(4);
                       nombreBanco = rs.getString(5);
                       nombreTercero = rs.getString(6);
                       apellidoTercero = rs.getString(7);
                       tipodocumentoter = rs.getString(8);
                       numerodocter = rs.getString(9);
                                           
                       if(rs.getString(3).trim().equals("32"))
                       {
                            tipoCuenta = "AHORROS No.";
                       }
                       if(rs.getString(3).trim().equals("22"))
                       {
                            tipoCuenta = "CORRIENTE No.";
                       }
                    
                       textocombo = nombreBanco.trim() + " " + tipoCuenta + " " + cuentaBanco.trim(); 
                       textocombo1 = nombreBanco.trim() + " /" + tipoCuenta + " /" + cuentaBanco.trim(); 
                       if (esTercero.equals("S"))
                       {
                          if(!rol.equals("0"))
                          {
                             textocombo = textocombo +" "+ nombreTercero.trim() + " " + apellidoTercero.trim() + " " + tipodocumentoter +" "+ numerodocter;
                             textocombo1 = textocombo1 +" /"+ nombreTercero.trim() + " /" + apellidoTercero.trim() + " /" + tipodocumentoter +" /"+ numerodocter;
                             out.println("<option value='"+ codigoBanco.trim()+" /"+ cuentaBanco.trim()+ "/"+ textocombo1 + "'>"+ textocombo.trim() +"</option>");
                          }
                       }
                       else
                       {
                           if(rol.equals("0"))
                           {
                               out.println("<option value='"+ codigoBanco.trim()+" /"+ cuentaBanco.trim()+ "/"+ textocombo1 +"'>"+ textocombo.trim() +"</option>");
                           }                        
                        }                         
                }  
                out.println("</SELECT></td></tr></table><br>");                  
                st.close();
        
               //DIV CAPTURA CHEQUE TERCERO
                    out.println("<DIV id=section6 style='display:none;'>");
                    out.println("<center>");
                    out.println("<table width='100%' BORDER=2 BORDERCOLOR=SILVER style='border-collapse: collapse;'>");
                    out.println("<tr><td class='td11' colspan= 4><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF'>");
                    out.println("<center><b>Informaci�n de Tercero</b></center></font></font>");
                    out.println("</td></tr>");
                    out.println("<tr><td>");
                    out.println("<table width='100%'>");
                    out.println("<tr>");
                    out.println("<td width='30%'>");
                    out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><b>Tipo de Documento:</b></font>");
                    out.println("</td>");
                    out.println("<td width='70%'>");
                    out.println("<SELECT name=v_tipodoc_ter>");
                      out.println("<OPTION selected value='C'>Cedula Ciudadania</OPTION>");
                      out.println("<OPTION value='E'>Cedula Extranjeria</OPTION>");
                      out.println("<OPTION value='N'>Nit Persona Juridica</OPTION>");
                      out.println("<OPTION value='M'>Nit Persona Natural</OPTION>");
                      out.println("<OPTION value='P'>Pasaporte</OPTION>");
                      out.println("<OPTION value='R'>Registro Civil</OPTION>");
                      out.println("<OPTION value='T'>Tarjeta Identidad</OPTION>");
                      out.println("<OPTION value='L'>Carnet Minist Relac Exter</OPTION>");
                    out.println("</SELECT>");
                    out.println("</td></tr><tr><td width='30%'><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><b>Documento:</b></font></td>");
                    out.println("<td width='70%'><input type=text  name=v_doc_ter value='' size= '15' maxlength = 15></td></tr>");
                    out.println("<tr><td><font face='Ver  dana, Arial, Helvetica, sans-serif' size='1'><b>Nombres:</b></font></td><td>");
                    out.println("<input type=text  name=v_nomb_terc value='' size= '30' maxlength = 30></td></tr>");
                    out.println("<tr><td><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><b>Apellidos:</b></font></td>");
                    out.println("<td><input type=text  name=v_apell_terc value='' size= '30' maxlength = 30></td></tr>");
                    out.println("</table></td></tr></table></center></div>");                                                             
              if(v_total.equals("N"))
              {
                  out.println("<table width=100% border=0><tr><td>");
                  //TABLA 1
                      out.println("<table width=100%  BORDER=2 BORDERCOLOR=SILVER style='border-collapse: collapse;'><tr><td class=\"td11\" colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Concepto</b></font></center></font></td></tr>");
                      out.println("<tr><td >");
                      out.println("<pre><center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=2>");
                      
                      
                      if((Double.parseDouble(SaldosFinales[12].toString().replace(",","").replace("$",""))>0)&&(Double.parseDouble(SaldosFinales[13].toString().replace(",","").replace("$",""))>0))
                      {
                           out.println("AVA <input type=radio NAME=v_concepto VALUE='0' onclick = 'minimo(obligatoriov_valor, obDispAVA)' > AVE <input type=radio NAME=v_concepto VALUE='1' onclick = 'minimo(obligatoriov_valor, obDispAVE);MensajeAVE()'>  </font></center><pre>");
                      }
                      if((Double.parseDouble(SaldosFinales[12].toString().replace(",","").replace("$",""))>0)&&(Double.parseDouble(SaldosFinales[13].toString().replace(",","").replace("$",""))==0))
                      {
                           out.println("AVA <input type=radio NAME=v_concepto VALUE='0' onclick  = 'minimo(obligatoriov_valor,obDispAVA)' checked> AVE <input type=radio NAME=v_concepto VALUE='1' disabled> </font></center><pre>");
                      }
                      if((Double.parseDouble(SaldosFinales[12].toString().replace(",","").replace("$",""))==0)&&(Double.parseDouble(SaldosFinales[13].toString().replace(",","").replace("$",""))>0))
                      {
                           out.println("AVA <input type=radio NAME=v_concepto VALUE='0' disabled> AVE <input type=radio NAME=v_concepto VALUE='1' onclick  = 'minimo(obligatoriov_valor, obDispAVE);MensajeAVE()'> </font></center><pre>");
                      }
                      if((Double.parseDouble(SaldosFinales[12].toString().replace(",","").replace("$",""))==0)&&(Double.parseDouble(SaldosFinales[13].toString().replace(",","").replace("$",""))==0))
                      {
                           out.println("AVA <input type=radio NAME=v_concepto VALUE='0' disabled> AVE <input type=radio NAME=v_concepto VALUE='1'  disabled> </font></center><pre>");
                      }                      
                      out.println("<input type='hidden' name='v_fondo' value='0'>");
                      out.println("</tr></td></table>");
                  
                  out.println("</td><td>");
                  //TABLA 2
                      out.println("<table width=100% BORDER=2 BORDERCOLOR=SILVER style='border-collapse: collapse;'><tr><td class=\"td11\" colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Orden de Aplicaci�n</b></font></center></font></td></tr>");
                      out.println("<tr ><td >");
                      out.println("<pre><center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=2>");
                      out.println("FIFO <input type=radio NAME=v_orden_apli VALUE='0' checked> LIFO <input type=radio NAME=v_orden_apli VALUE='1'> APORTE PUNTUAL <input type=radio NAME=v_orden_apli VALUE='2'></font></center><pre>");
                      out.println("<input type='hidden' name='v_fondo' value='0'>");
                      out.println("</tr></td></table>");
                  out.println("</td></tr></table>");  
                  out.println("<input type='hidden' name='v_fondo' value='0'>");
                  out.println("<input type='hidden' name='v_esquema' value='0'>"); 
              }
              else
              {
                    int retTotalAVA = 0;
                    int retTotalAVE = 0;                    
                    if(Double.parseDouble(SaldosFinales[12].toString().replace(",","").replace("$",""))>0&&Double.parseDouble(SaldosFinales[2].toString().replace(",","").replace("$",""))==0&&Double.parseDouble(SaldosFinales[4].toString().replace(",","").replace("$",""))==0&&Credito==0)
                    {
                        retTotalAVA = 1;
                    }
                    if(Double.parseDouble(SaldosFinales[13].toString().replace(",","").replace("$",""))>0&&Double.parseDouble(SaldosFinales[3].toString().replace(",","").replace("$",""))==0&&Double.parseDouble(SaldosFinales[5].toString().replace(",","").replace("$",""))==0)
                    {
                        retTotalAVE = 1;
                    }
                    out.println("<table width=100%  BORDER=2 BORDERCOLOR=SILVER style='border-collapse: collapse;'><tr><td class=\"td11\" colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Concepto</b></font></center></font></td></tr>");
                      out.println("<tr><td >");
                      out.println("<pre><center><FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=2>");
                      if((retTotalAVA==1)&&(retTotalAVE==1))
                      {
                           out.println("AVA <input type=radio NAME=v_concepto VALUE='0'> AVE <input type=radio NAME=v_concepto VALUE='1' onclick  = 'MensajeAVE()'> Saldo Total (AVA + AVE) <input type=radio NAME=v_concepto VALUE='2' onclick  = 'MensajeAVE()'> </font></center><pre>");
                      }
                      if((retTotalAVA==1)&&(retTotalAVE==0))
                      {
                           out.println("AVA <input type=radio NAME=v_concepto VALUE='0' checked> AVE <input type=radio NAME=v_concepto VALUE='1' disabled> Saldo Total (AVA + AVE) <input type=radio NAME=v_concepto VALUE='2' disabled> </font></center><pre>");
                      }
                      if((retTotalAVA==0)&&(retTotalAVE==1))
                      {
                           out.println("AVA <input type=radio NAME=v_concepto VALUE='0' disabled> AVE <input type=radio NAME=v_concepto VALUE='1' onclick  = 'MensajeAVE()'> Saldo Total (AVA + AVE) <input type=radio NAME=v_concepto VALUE='2' disabled> </font></center><pre>");
                      }
                      if((retTotalAVA==0)&&(retTotalAVE==0))
                      {
                           out.println("AVA <input type=radio NAME=v_concepto VALUE='0' disabled> AVE <input type=radio NAME=v_concepto VALUE='1'  disabled> Saldo Total (AVA + AVE) <input type=radio NAME=v_concepto VALUE='2' disabled> </font></center><pre>");
                      }                      
                      out.println("<input type='hidden' name='v_fondo' value='0'>");
                      out.println("</tr></td></table>");
              
              }
         out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
         out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
         out.println("<INPUT ID=cadena NAME=v_valorunidad_ava TYPE=hidden VALUE='"+String.valueOf(SaldosFinales[21].toString().replace(",","").replace("$",""))+"'>");
         out.println("<INPUT ID=cadena NAME=v_valorunidad_ave TYPE=hidden VALUE='"+String.valueOf(SaldosFinales[22].toString().replace(",","").replace("$",""))+"'>");
         out.println("<INPUT ID=cadena NAME=finalizar TYPE=hidden VALUE='0'>");
         out.println("<INPUT ID=cadena NAME=v_disponibleAVA TYPE=hidden VALUE='"+String.valueOf(SaldosFinales[12].toString().replace(",","").replace("$",""))+"'>");
         out.println("<INPUT ID=cadena NAME=v_disponibleAVE TYPE=hidden VALUE='"+String.valueOf(SaldosFinales[13].toString().replace(",","").replace("$",""))+"'>");               
         out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
         out.println("<br>");
         out.println("<tr><td valign=TOP><input type=submit name = 'Aceptar' value='Aceptar'  ></td><td valign=TOP><input type=button name = 'Regresar' value='Regresar' onclick=' history.go(-1)'></td></form>");                                
         out.println("</tr>");
         out.println("</table></center>");         
         v_pie = i_pagina.TBFL_PIE;
         out.println(""+v_pie+"");
         out.println("<br>");         
         out.close();
         }
         }
    } catch(Exception ex)/**Manejo de errores*/
        {
        v_pintar="";
        String error = ex.toString();
        if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
        {
        v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Solicitud de Retiro","","<center>No se tiene comunicaci�n con el servidor de datos  por favor ingrese nuevamente.</center>",false);
        }
        else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
            {
             v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
            }
               else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                    {
                     v_pintar = i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicaci�n con el servidor de datos, por favor intente entrar de nuevo a la opci�n.</center>",false);
                    }
                    else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                         {
                           v_pintar =  i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicaci�n con el servidor Web, por favor intente entrar de nuevo a la opci�n.</center>",false);
                         }
                      else
                      {
                       v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                      }
        out.println(""+v_pintar+"");
        out.println("<BR>");
        out.println("<center>");
        out.println("<input type=button value='Regresar' onclick=' history.go(-1)'>");
        out.println("<a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
        out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_EliminarSolicitud_Oblig method=post target=content>"+                     
                         "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                         "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                         "</FORM> </center>");        
        v_pie = i_pagina.TBFL_PIE;
        out.println("<br>");
        out.println("<br>");
        out.println(""+v_pie+"");
        out.close();
        }
      
    }
}/*@lineinfo:generated-code*/