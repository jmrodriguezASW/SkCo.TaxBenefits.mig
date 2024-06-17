package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;

import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.TBCL_Seguridad;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

import java.io.IOException;

import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TBS_FinalizarRetiro extends HttpServlet{
            
    private String[]        v_valusu;
    private String[] resul = null;   
    String v_ruta_serv = "";
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = new PrintWriter (response.getOutputStream());        
        /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase STBCL_GenerarBaseHTML, no es necesaria la instancia nueva*/ 
 //STBCL_GenerarBaseHTML i_pagina = new STBCL_GenerarBaseHTML;        
        
        try{
            
            HttpSession session = request.getSession(true);
            if(session==null)
                session = request.getSession(true);
            
            session.setMaxInactiveInterval(3600);
            response.setContentType("text/html");

            String v_contrato = "", v_producto = "", v_usuario  = "", v_unidad = "";
            String v_tipousu = "", v_idworker = "";
            String   nuevaCadena = "";
            
            String parametros[] = new String[8];
            String  cadena = request.getParameter("cadena");
            nuevaCadena = cadena;
            String ip_tax = request.getRemoteAddr();
             
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
            parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
            v_contrato = parametros[0];
            v_producto = parametros[1];
            v_usuario  = parametros[2];
            v_unidad = parametros[3];
            v_tipousu = parametros[4];
            v_idworker = parametros[5];
            int v_error   = 0;
            String v_mensaje   = "";
            int bandera = 0;
                        
            String[] saldos = null;
            
            v_ruta_serv = (java.lang.String)session.getAttribute("s_ruta_serv");
            
            if((java.lang.String)session.getAttribute("s_contrato")!= null || (java.lang.String)session.getAttribute("s_producto")!= null) {
                String v_pro            = (java.lang.String)session.getAttribute("s_producto");
                String v_contra         = (java.lang.String)session.getAttribute("s_contrato");
                String v_fecpro         =(java.lang.String)session.getAttribute("s_fecpro");
                String v_fecefe         = (java.lang.String)session.getAttribute("s_fecefectiva");                
                String v_tipotran       = (java.lang.String)session.getAttribute("v_tipotran"); 
                String v_clasetran      = (java.lang.String)session.getAttribute("v_clasetran");
                String v_cuenta         = request.getParameter("v_cuenta");
                String v_tipodoc_ter    = request.getParameter("v_tipodoc_ter");
                String v_doc_ter        = request.getParameter("v_doc_ter"); 
                String v_nomb_terc      = request.getParameter("v_nomb_terc");
                String v_apell_terc     = request.getParameter("v_apell_terc");
                String v_concepto       = request.getParameter("v_concepto");
                String v_orden_apli     = request.getParameter("v_orden_apli");
                String s_usuario        = (java.lang.String)session.getAttribute("s_usuariopipe");
                String v_saldoTOblig    = (java.lang.String)session.getAttribute("v_saldoTOblig");
                double v_saldoAVA       = (java.lang.Double)session.getAttribute("v_saldoAVA");
                double v_saldoAVE       = (java.lang.Double)session.getAttribute("v_saldoAVE");
                String v_esTercero      = (java.lang.String)session.getAttribute("esTercero");
                String v_total          = (java.lang.String) session.getAttribute("s_total"); 
                String v_tipo_valor     = "";
                String v_banco          = ""; 
                String numerocuenta     = "";
                double monto_retiro     = 0.0;
                String s_comisionava    = (java.lang.String) session.getAttribute("s_comisionava"); 
                String s_comisionave    = (java.lang.String) session.getAttribute("s_comisionave");
                double v_comision_ava   = 0.0;
                double v_comision_ave   = 0.0; 
                double v_cc             = 0.0; 
                double v_ret_rend       = 0.0; 
                double v_prov_comi_rend = 0.0; 
                double v_prov_comi_cap  = 0.0; 
                double v_disponible_neto        = 0.0; 
                double v_disponible_neto_AVE    = 0.0; 
                double v_prov_comi_rend_AVE     = 0.0; 
                double v_prov_comi_cap_AVE      = 0.0; 
                double v_ret_rend_AVE   = 0.0; 
                double v_cc_AVE         = 0.0; 
                double soliAVA          = 0.0;
                    
                if (v_concepto.equals("2")){
                    v_disponible_neto = ((Double)session.getAttribute("v_disponible_neto_AVA")).doubleValue(); 
                    v_prov_comi_rend = ((Double)session.getAttribute("v_prov_comi_rend_AVA")).doubleValue();
                    v_prov_comi_cap = ((Double)session.getAttribute("v_prov_comi_cap_AVA")).doubleValue();
                    v_ret_rend = ((Double)session.getAttribute("v_ret_rend_AVA")).doubleValue();
                    v_cc = ((Double)session.getAttribute("v_cc_AVA")).doubleValue();
                    v_disponible_neto_AVE =((Double)session.getAttribute("v_disponible_neto_AVE")).doubleValue(); 
                    v_prov_comi_rend_AVE = ((Double)session.getAttribute("v_prov_comi_rend_AVE")).doubleValue();
                    v_prov_comi_cap_AVE = ((Double)session.getAttribute("v_prov_comi_cap_AVE")).doubleValue();
                    v_ret_rend_AVE = ((Double)session.getAttribute("v_ret_rend_AVE")).doubleValue();
                    v_cc_AVE = ((Double)session.getAttribute("v_cc_AVE")).doubleValue();
                }                
                else{
                    v_cc             = ((Double)session.getAttribute("v_cc")).doubleValue();
                    v_ret_rend       = ((Double)session.getAttribute("v_ret_rend")).doubleValue();
                    v_prov_comi_rend = ((Double)session.getAttribute("v_prov_comi_rend")).doubleValue();
                    v_prov_comi_cap  = ((Double)session.getAttribute("v_prov_comi_cap")).doubleValue();
                    v_disponible_neto = ((Double)session.getAttribute("v_disponible_neto")).doubleValue(); 
                }
                
                if (v_total.equals("N")) {
                    monto_retiro = Double.parseDouble(request.getParameter("obligatoriov_valor").toString());
                    }
                        
                if (v_concepto.equals("0")) {
                    v_comision_ava = Double.parseDouble(s_comisionava);
                    v_saldoAVE = 0.0;
                    }
                
                if (v_concepto.equals("1")) {
                    v_comision_ave = Double.parseDouble(s_comisionave);
                    v_saldoAVA = 0.0;
                    }
                
                if (v_concepto.equals("2")) {
                    v_comision_ava = Double.parseDouble(s_comisionava);
                    v_comision_ave = Double.parseDouble(s_comisionave);
                    }
                                
                if (v_tipotran.equals("UTT001"))
                    v_tipo_valor = "STV002";
                else
                    v_tipo_valor = "STV001";
                
                    if (v_cuenta.equals("1")) {
                        v_nomb_terc     = "";
                        v_apell_terc    = "";
                        v_tipodoc_ter   = "";
                        v_doc_ter       = "";                                
                        if (v_esTercero.equals("S")) {   
                            v_tipodoc_ter   = (java.lang.String)session.getAttribute("s_tipoidentificacion");
                            v_doc_ter       = (java.lang.String)session.getAttribute("s_identificacion");
                            v_nomb_terc     = (java.lang.String)session.getAttribute("s_nombres");
                            v_apell_terc    = (java.lang.String)session.getAttribute("s_apellidos");                       
                        }
                    }
                    else {
                        if (v_cuenta.equals("2")) {
                            v_nomb_terc     = "";
                            v_apell_terc    = "";
                            v_tipodoc_ter   = "";
                            v_doc_ter       = "";  
                            if (v_esTercero.equals("S")) {                                                            
                                v_tipodoc_ter = request.getParameter("v_tipodoc_ter");
                                v_doc_ter = request.getParameter("v_doc_ter");
                                v_nomb_terc = request.getParameter("v_nomb_terc");
                                v_apell_terc = request.getParameter("v_apell_terc");
                            }                            
                        }
                        else {
                            String[] vector_cuenta = v_cuenta.split("/");
                            v_banco         = vector_cuenta[0];
                            numerocuenta    = vector_cuenta[1];
                            v_nomb_terc     = "";
                            v_apell_terc    = "";
                            v_tipodoc_ter   = "";
                            v_doc_ter       = "";                                
                            if (v_esTercero.equals("S")) {                                                            
                                v_nomb_terc = vector_cuenta[5];
                                v_apell_terc = vector_cuenta[6];
                                v_tipodoc_ter = vector_cuenta[7];
                                v_doc_ter = vector_cuenta[8];                                
                            }
                        }                    
                    }                

                String aporte_actual = "";
                SQL_PTB_RETIROS_OBLIG objSQL_PTB_RETIROS_OBLIG =  new SQL_PTB_RETIROS_OBLIG();   
                
                if (v_orden_apli.equals("2"))
                {            
                    String seleccion = session.getAttribute("seleccion").toString();                
                    saldos = seleccion.split(";");                    
                     
                    for (int v_con=0;v_con < seleccion.split(";").length; v_con++)
                    {                        
                        aporte_actual = saldos[v_con];
                        bandera = 1;                                                              
                        
                        //Insertamos en la tabla de trabajo                                                                                        
                        resul = objSQL_PTB_RETIROS_OBLIG.INSERT_RETPERSSON_OBLIG(v_pro, ""+Integer.parseInt(v_contra), 0, Double.parseDouble(aporte_actual));

                        if(!resul[0].equals("0")) {
                            bandera = 0;
                            String v_pintar4=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error al procesar solicitud de Retiro. "+resul[1]+" .</center>",false);
                            out.println(""+v_pintar4+"");
                            out.println("<BR>");
                            out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
                            out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
                            out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                            out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                                        "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                                        "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                                        "</FORM></TD></TR>");
                            out.println("</table></center>");                                                    
                            String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
                            out.println("<br>");
                            out.println(""+v_pie+"");
                            out.close();
                            v_con = saldos.length;
                        }                    
                    }
                    if (bandera == 1) {
                        
                        resul = objSQL_PTB_RETIROS_OBLIG.GENERAR_RETIRO_OBLIG(v_pro, ""+Integer.parseInt(v_contra), monto_retiro,v_comision_ava, v_comision_ave, v_fecefe, v_fecpro, 
                                                                              v_fecpro, v_tipotran.trim(), v_clasetran.trim(), v_tipo_valor.trim(), v_banco.trim(), numerocuenta.trim(), 
                                                                              v_tipodoc_ter.trim(), v_doc_ter.trim(), v_nomb_terc, v_apell_terc, s_usuario, v_orden_apli, v_concepto, 
                                                                              v_saldoAVA, v_saldoAVE, v_disponible_neto, v_cc, v_ret_rend, v_prov_comi_rend, v_prov_comi_cap,0);                   
                        
                        if(!resul[0].equals("0")) {
                            bandera = 0;
                            String v_pintar4=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error al procesar solicitud de Retiro. "+resul[1]+" .</center>",false);
                            out.println(""+v_pintar4+"");
                            out.println("<BR>");
                            out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
                            out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
                            out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                            out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                                        "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                                        "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                                        "</FORM></TD></TR>");
                            out.println("</table></center>");                            
                            String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
                            out.println("<br>");
                            out.println(""+v_pie+"");
                            out.close();
                        }
                        else {
                            Double conseretiro = Double.parseDouble(resul[2]);                            
                            resul = objSQL_PTB_RETIROS_OBLIG.UPDATE_RETPERSSON_OBLIG(v_pro, ""+Integer.parseInt(v_contra),conseretiro,1);  
                            if(!resul[0].equals("0")) {
                                bandera = 0;
                                String v_pintar4=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error al procesar solicitud de Retiro. "+resul[1]+" .</center>",false);
                                out.println(""+v_pintar4+"");
                                out.println("<BR>");
                                out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
                                out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
                                out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                                out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                                            "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                                            "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                                            "</FORM></TD></TR>");
                                out.println("</table></center>");                                
                                String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
                                out.println("<br>");
                                out.println(""+v_pie+"");
                                out.close();
                            }
                            else {
                                String v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Solicitud de Retiro","Solicitud de Retiro","","<center>Solicitud de Retiro "+Long.toString(Math.round(Double.parseDouble(resul[2])))+" realizada con exito.</center>",false);
                                out.println(""+v_pintar+"");
                                out.println("<BR><BR>");
                                out.println("<center>");
                                out.println("<a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                                out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                                                    "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                                                    "<input type=submit name = 'Aceptar' value='Aceptar'>"+
                                                    "</FORM>");
                                out.println("</center>");
                                String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
                                out.println("<br>");
                                out.println(""+v_pie+"");
                                out.close();
                            }
                        }                        
                    }                                                                                                                
            }
                else {      
                    if (v_concepto.equals("2")) {
                        resul = objSQL_PTB_RETIROS_OBLIG.GENERAR_RETIRO_OBLIG(v_pro, ""+Integer.parseInt(v_contra), monto_retiro, v_comision_ava, 0.0, v_fecefe, v_fecpro, 
                                                                              v_fecpro, v_tipotran.trim(), v_clasetran.trim(), v_tipo_valor, v_banco.trim(), numerocuenta.trim(), 
                                                                              v_tipodoc_ter.trim(), v_doc_ter.trim(), v_nomb_terc, v_apell_terc, s_usuario, v_orden_apli, "0", 
                                                                              v_saldoAVA, 0.0, v_disponible_neto, v_cc, v_ret_rend, v_prov_comi_rend, v_prov_comi_cap,0); 
                        if(!resul[0].equals("0")) {
                            bandera = 0;
                            String v_pintar4=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error al procesar solicitud de Retiro. "+resul[1]+" .</center>",false);
                            out.println(""+v_pintar4+"");
                            out.println("<BR>");
                            out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
                            out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
                            out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                            out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                                        "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                                        "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                                        "</FORM></TD></TR>");
                            out.println("</table></center>");                        
                            String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
                            out.println("<br>");
                            out.println(""+v_pie+"");
                            out.close();
                        }
                        else {                        
                            soliAVA = Double.parseDouble(resul[2]);
                            resul = objSQL_PTB_RETIROS_OBLIG.GENERAR_RETIRO_OBLIG(v_pro, ""+Integer.parseInt(v_contra), monto_retiro, 0.0, v_comision_ave, v_fecefe, v_fecpro, 
                                                                              v_fecpro, v_tipotran.trim(), v_clasetran.trim(), v_tipo_valor, v_banco.trim(), numerocuenta.trim(), 
                                                                              v_tipodoc_ter.trim(), v_doc_ter.trim(), v_nomb_terc, v_apell_terc, s_usuario, v_orden_apli, "1", 
                                                                              0.0, v_saldoAVE, v_disponible_neto_AVE, v_cc_AVE, v_ret_rend_AVE, v_prov_comi_rend_AVE, v_prov_comi_cap_AVE,0); 
                            if(!resul[0].equals("0")) {
                                bandera = 0;
                                String v_pintar4=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error al procesar solicitud de Retiro. "+resul[1]+" .</center>",false);
                                out.println(""+v_pintar4+"");
                                out.println("<BR>");
                                out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
                                out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
                                out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                                out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                                            "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                                            "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                                            "</FORM></TD></TR>");
                                out.println("</table></center>");                        
                                String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
                                out.println("<br>");
                                out.println(""+v_pie+"");
                                out.close();
                            }                        
                            else {
                                String v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Solicitud de Retiro","Solicitud de Retiro","","<center>Solicitud de Retiro AVA: "+Math.round(soliAVA)+" y AVE: "+Math.round(Double.parseDouble(resul[2]))+" realizada con exito.</center>",false);
                                out.println(""+v_pintar+"");
                                out.println("<BR><BR>");
                                out.println("<center>");
                                out.println("<a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                                out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                                                    "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                                                    "<input type=submit name = 'Aceptar' value='Aceptar'>"+
                                                    "</FORM>");
                                out.println("</center>");
                                String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
                                out.println("<br>");
                                out.println(""+v_pie+"");
                                out.close();
                            }
                        }
                    }
                    else
                    {
                    resul = objSQL_PTB_RETIROS_OBLIG.GENERAR_RETIRO_OBLIG(v_pro, ""+Integer.parseInt(v_contra), monto_retiro, v_comision_ava, v_comision_ave, v_fecefe, v_fecpro, 
                                                                          v_fecpro, v_tipotran.trim(), v_clasetran.trim(), v_tipo_valor, v_banco.trim(), numerocuenta.trim(), 
                                                                          v_tipodoc_ter.trim(), v_doc_ter.trim(), v_nomb_terc, v_apell_terc, s_usuario, v_orden_apli, v_concepto, 
                                                                          v_saldoAVA, v_saldoAVE, v_disponible_neto, v_cc, v_ret_rend, v_prov_comi_rend, v_prov_comi_cap,0); 
                    }
                    if(!resul[0].equals("0")) {
                        bandera = 0;
                        String v_pintar4=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error al procesar solicitud de Retiro. "+resul[1]+" .</center>",false);
                        out.println(""+v_pintar4+"");
                        out.println("<BR>");
                        out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
                        out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
                        out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                        out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                                    "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                                    "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                                    "</FORM></TD></TR>");
                        out.println("</table></center>");                        
                        String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
                        out.println("<br>");
                        out.println(""+v_pie+"");
                        out.close();
                    }
                    else {
                        String v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Solicitud de Retiro","Solicitud de Retiro","","<center>Solicitud de Retiro "+Math.round(Double.parseDouble(resul[2]))+" realizada con exito.</center>",false);
                        out.println(""+v_pintar+"");
                        out.println("<BR><BR>");
                        out.println("<center>");
                        out.println("<a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                        out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                                            "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                                            "<input type=submit name = 'Aceptar' value='Aceptar'>"+
                                            "</FORM>");
                        out.println("</center>");
                        String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
                        out.println("<br>");
                        out.println(""+v_pie+"");
                        out.close();
                    }
                }
            }
            
        }catch (Exception e) {
            e.printStackTrace();
            String v_pintar4=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error al procesar solicitud de Retiro.</center>",false);
            out.println(""+v_pintar4+"");
            out.println("<BR>");
            out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
            out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
            out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
            out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                        "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+request.getParameter("cadena")+"'>" + 
                        "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                        "</FORM></TD></TR>");
            out.println("</table></center>");                             
            String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
            out.println("<br>");
            out.println(""+v_pie+"");
            out.close(); 
        }
    }    
}
