package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_ConsultaClienteLista;

import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;

import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTMLII;

import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.TBCL_Seguridad;

import java.io.IOException;
import java.io.PrintWriter;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.RoundingMode;

public class TBS_ConfirmarRetiro extends HttpServlet implements SingleThreadModel {
    
    PrintWriter out; 
    String v_ruta_serv = "";
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {                
        out = new PrintWriter (response.getOutputStream());
        STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();
        try
        {
         HttpSession session = request.getSession(true);
         if(session==null)
          session = request.getSession(true);
         session.setMaxInactiveInterval(3600);
         response.setContentType("text/html");
         String v_contrato = "", v_producto = "", v_usuario  = "", v_unidad = "";
         String v_tipousu = "";
         String   nuevaCadena = "";
         String parametros[] = new String[8];
         String  cadena = request.getParameter("cadena");   
         nuevaCadena = cadena;
         String ip_tax = request.getRemoteAddr();
         TBCL_Seguridad Seguridad = new TBCL_Seguridad();
         parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
         v_contrato = parametros[0];
         v_producto = parametros[1];
         v_usuario  = parametros[2];
         v_unidad = parametros[3];
         v_tipousu = parametros[4];
         String v_pintar        = "";
         String v_orden_apli    = "0";/**Variable del orden de aplicacion escogido por el usuario*/
         String monto_retiro    = "0";   
         String metodo_giro     = "";                 
         String v_tipodoc_ter   = "";
         String v_doc_ter       = "";
         String v_nomb_terc     = "";
         String v_apell_terc    = "";
         String v_mensLista     = "";  
         String num_aportes     = "";
         String s_concepto      = "";        
         String banco           = "";
         String tipocuenta      = "";   
         String numero_cuenta   = "";   
         String[] saldos = null;
         double monto_selec = 0.0;
         double v_ret_rend = 0.0;
         double v_cc = 0.0;
            
         v_ruta_serv     = (java.lang.String)session.getValue("s_ruta_serv");
        
         //Se obtienen los datos de session y request para hacer las validaciones necesarias para confirmar el retiro
         if (request.getParameter("v_orden_apli") != null)
            v_orden_apli = request.getParameter("v_orden_apli");
         
         if (request.getParameter("obligatoriov_valor")!=null)
             monto_retiro = request.getParameter("obligatoriov_valor");   
         
         String v_cuenta        = request.getParameter("v_cuenta");            
         String v_concepto      = request.getParameter("v_concepto");                           
         String finalizar       = request.getParameter("finalizar");
         String v_total         = (java.lang.String)session.getValue("s_total");
         String v_contrato_unif = (java.lang.String)session.getValue("s_contrato_unif");
         String v_nombre        = (java.lang.String)session.getValue("s_nombres");
         String v_apellidos     = (java.lang.String)session.getValue("s_apellidos"); 
         String v_fecefe        = (java.lang.String)session.getValue("s_fecefectiva"); 
         String v_fecpro        = (java.lang.String)session.getValue("s_fecpro"); 
         String v_tiporetiro    = (java.lang.String)session.getValue("s_tiporetiro");
         String v_esTercero     = (java.lang.String)session.getValue("esTercero");                    
            
        DecimalFormat v_format = new  DecimalFormat("¤###,###,###,###,###,###.##");
        
        if (request.getParameter("obligatoriov_valor")!= null) {
            if (Double.parseDouble(request.getParameter("obligatoriov_valor").toString())==0) {
                v_pintar=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Favor Digitar el Valor a Retirar.</center>",false);
                out.println(""+v_pintar+"");
                out.println("<BR>");
                out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
                out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
                out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                            "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                            "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                            "</FORM></TD></TR>");
                out.println("</table></center>");               
                String v_pie = i_pagina.TBFL_PIE;
                out.println("<br>");
                out.println(""+v_pie+"");
                out.close();
            }
            if (Double.parseDouble(request.getParameter("obligatoriov_valor").toString())<0) {
                v_pintar=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>El valor no puede ser negativo.</center>",false);
                out.println(""+v_pintar+"");
                out.println("<BR>");
                out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
                out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
                out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                            "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                            "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                            "</FORM></TD></TR>");
                out.println("</table></center>");                 
                String v_pie = i_pagina.TBFL_PIE;
                out.println("<br>");
                out.println(""+v_pie+"");
                out.close();
            }            
            
        }
                                                                         
        if (v_cuenta.equals("-1")) {
            v_pintar=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Debe Seleccionar un Metodo de Giro.</center>",false);
            out.println(""+v_pintar+"");
            out.println("<BR>");
            out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
            out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
            out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
            out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                        "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                        "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                        "</FORM></TD></TR>");
            out.println("</table></center>");             
            String v_pie = i_pagina.TBFL_PIE;
            out.println("<br>");
            out.println(""+v_pie+"");
            out.close();
        } else {   
            if (v_cuenta.equals("1")) {
                    metodo_giro     = "Cheque";
                    v_nomb_terc     = "";
                    v_apell_terc    = "";
                    v_tipodoc_ter   = "";
                    v_doc_ter       = "";                     
                } else {
                    if (v_cuenta.equals("2")) {
                        metodo_giro = "Cheque";
                        v_tipodoc_ter = request.getParameter("v_tipodoc_ter");
                        v_doc_ter = request.getParameter("v_doc_ter");
                        v_nomb_terc = request.getParameter("v_nomb_terc");
                        v_apell_terc = request.getParameter("v_apell_terc");
                        if (v_tipodoc_ter.equals("") || v_doc_ter.equals("") || v_nomb_terc.equals("") || v_apell_terc.equals("")) {
                            String v_pintar4 = i_pagina.TBFL_CABEZA("Calcular Solicitud de Retiro", "Error al Calcular Solicitud de Retiro",
                                                 "",
                                                 "<center>Error al procesar solicitud de Retiro. Debe Diligenciar los Datos del Tercero .</center>",
                                                 false);
                            out.println("" + v_pintar4 + "");
                            out.println("<BR>");
                            out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
                            out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
                            out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                            out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                                        "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                                        "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                                        "</FORM></TD></TR>");
                            out.println("</table></center>");                             
                            String v_pie = i_pagina.TBFL_PIE;
                            out.println("<br>");
                            out.println("" + v_pie + "");
                            out.close();
                        }
                        else {
                            TBCL_ConsultaClienteLista i_consultaC = new TBCL_ConsultaClienteLista();
                            String lista = i_consultaC.ConsultarCliente(v_doc_ter.trim());
                        
                            if (lista.equals("Y"))
                            {
                                v_mensLista ="<TR><TD width=\"40%\"><FONT color=black face=verdana size=1><B>Observaciones:</B></FONT></TD><TD width=\"60%\">: <FONT face=verdana size=1 color='#FF0000'><b>Favor Comunicarse con el área de Financial Crime   Prevention a las EXT 4018-4269-4233 antes de   continuar con la operación. </b></font></TR></TD>";                              
                            }
                        }                        
                    } else {
                        metodo_giro = "Cuenta Bancaria";
                        String[] vector_cuenta = v_cuenta.split("/");
                        banco            = vector_cuenta[2];
                        tipocuenta       = vector_cuenta[3].split(" ")[0];   
                        numero_cuenta    = vector_cuenta[4]; 
                        if (v_esTercero.equals("S")) {                            
                            v_nomb_terc      = vector_cuenta[5];
                            v_apell_terc     = vector_cuenta[6];
                            v_tipodoc_ter    = vector_cuenta[7];
                            v_doc_ter        = vector_cuenta[8];                                                                                    
                            
                            TBCL_ConsultaClienteLista i_consultaC = new TBCL_ConsultaClienteLista();
                            String lista = i_consultaC.ConsultarCliente(v_doc_ter.trim());
                            
                            if (lista.equals("Y"))
                            {
                                v_mensLista ="<TR><TD width=\"40%\"><FONT color=black face=verdana size=1><B>Observaciones:</B></FONT></TD><TD width=\"60%\">: <FONT face=verdana size=1 color='#FF0000'><b>Favor Comunicarse con el área de Financial Crime   Prevention a las EXT 4018-4269-4233 antes de   continuar con la operación. </b></font></TR></TD>";                              
                            }
                        }
                    }
                }
            }
            
        if (v_concepto==null) {
            v_pintar=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Seleccione Concepto a Retirar.</center>",false);
            out.println(""+v_pintar+"");
            out.println("<BR>");
            out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
            out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
            out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
            out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                        "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                        "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                        "</FORM></TD></TR>");
            out.println("</table></center>"); 
            String v_pie = i_pagina.TBFL_PIE;
            out.println("<br>");
            out.println(""+v_pie+"");
            out.close();
        }
        else {
            
            if (v_total.equals("S") && v_concepto.equals("0") ) {
                monto_retiro = ""+Double.parseDouble(request.getParameter("obDispAVA"));
            }
            if (v_total.equals("S") && v_concepto.equals("1") ) {
                monto_retiro = ""+Double.parseDouble(request.getParameter("obDispAVE"));
            }
            if (v_total.equals("S") && v_concepto.equals("2") ) {
                
                monto_retiro = ""+(Double.parseDouble(request.getParameter("obDispAVA")) + Double.parseDouble(request.getParameter("obDispAVE")));
            }                
    
            if (v_concepto.equals("0") && v_total.equals("N") && (Double.parseDouble(request.getParameter("obligatoriov_valor").toString())>Double.parseDouble(request.getParameter("obDispAVA").toString()))) {
                v_pintar=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Retiro no permitido, el saldo disponible para el contrato son "+String.valueOf(v_format.format(Double.parseDouble(request.getParameter("v_disponibleAVA").toString())))+" y usted desea retirar "+String.valueOf(v_format.format(Double.parseDouble(request.getParameter("obligatoriov_valor").toString())))+"</center>",false);
                out.println(""+v_pintar+"");
                out.println("<BR>");
                out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
                out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
                out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                            "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                            "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                            "</FORM></TD></TR>");
                out.println("</table></center>");                 
                String v_pie = i_pagina.TBFL_PIE;
                out.println("<br>");
                out.println(""+v_pie+"");
                out.close();
            } else {
                if (v_concepto.equals("1") && v_total.equals("N") && Double.parseDouble(request.getParameter("obligatoriov_valor").toString())>Double.parseDouble(request.getParameter("obDispAVE").toString())) {
                    v_pintar=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Retiro no permitido, el saldo disponible para el contrato son "+String.valueOf(v_format.format(Double.parseDouble(request.getParameter("v_disponibleAVE").toString())))+" y usted desea retirar "+String.valueOf(v_format.format(Double.parseDouble(request.getParameter("obligatoriov_valor").toString())))+"</center>",false);
                    out.println(""+v_pintar+"");
                    out.println("<BR>");
                    out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
                    out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
                    out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                    out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                                "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                                "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                                "</FORM></TD></TR>");
                    out.println("</table></center>");                     
                    String v_pie = i_pagina.TBFL_PIE;
                    out.println("<br>");
                    out.println(""+v_pie+"");
                    out.close();
                }   
            }            
        }                
                                            
         if (v_orden_apli.equals("0") || v_orden_apli.equals("1")) {  
                v_pintar=    i_pagina.TBFL_CABEZA("Resumen de la Solicitud","Resumen de la Solicitud","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_FinalizarRetiro","",true,"modulo_retiros_oblig.js","return validar_retiro(this)");                
                out.println(""+v_pintar+"");       
                
                session.removeValue("v_disponible_neto");
                session.removeValue("v_prov_comi_rend");
                session.removeValue("v_prov_comi_cap");
                session.removeValue("v_prov_comi_rend");
                session.removeValue("v_prov_comi_cap");
                  
                if (v_concepto.toString().trim().equals("0")) {             
                    s_concepto = "Aportes Voluntarios Afiliado";
                    session.putValue("v_disponible_neto", Double.valueOf(Double.parseDouble(request.getParameter("obDispAVA"))));
                    session.putValue("v_prov_comi_rend", Double.parseDouble(session.getValue("v_prov_comi_rend_AVA").toString()));
                    session.putValue("v_prov_comi_cap", Double.parseDouble(session.getValue("v_prov_comi_capital_AVA").toString()));
                    session.putValue("v_ret_rend", Double.parseDouble(session.getValue("v_retencion_rend_AVA").toString()));
                    session.putValue("v_cc", Double.parseDouble(session.getValue("v_retencion_cont_AVA").toString()));
                    }
                
                if (v_concepto.toString().trim().equals("1")) {
                    s_concepto = "Aportes Voluntarios Empleador";
                    session.putValue("v_disponible_neto", Double.valueOf(Double.parseDouble(request.getParameter("obDispAVE"))));
                    session.putValue("v_prov_comi_rend", Double.parseDouble(session.getValue("v_prov_comi_rend_AVE").toString()));
                    session.putValue("v_prov_comi_cap", Double.parseDouble(session.getValue("v_prov_comi_capital_AVE").toString()));
                    session.putValue("v_ret_rend", Double.parseDouble(session.getValue("v_retencion_rend_AVE").toString()));
                    session.putValue("v_cc", Double.parseDouble(session.getValue("v_retencion_cont_AVE").toString()));
                    }
                
                if (v_concepto.toString().trim().equals("2")){
                    s_concepto = "AVA + AVE";    
                    session.putValue("v_disponible_neto_AVA", Double.valueOf(Double.parseDouble(request.getParameter("obDispAVA"))));
                    session.putValue("v_prov_comi_rend_AVA", Double.parseDouble(session.getValue("v_prov_comi_rend_AVA").toString()));
                    session.putValue("v_prov_comi_cap_AVA", Double.parseDouble(session.getValue("v_prov_comi_capital_AVA").toString()));
                    session.putValue("v_ret_rend_AVA", Double.parseDouble(session.getValue("v_retencion_rend_AVA").toString()));
                    session.putValue("v_cc_AVA", Double.parseDouble(session.getValue("v_retencion_cont_AVA").toString()));
                    session.putValue("v_disponible_neto_AVE", Double.valueOf(Double.parseDouble(request.getParameter("obDispAVE"))));
                    session.putValue("v_prov_comi_rend_AVE", Double.parseDouble(session.getValue("v_prov_comi_rend_AVE").toString()));
                    session.putValue("v_prov_comi_cap_AVE", Double.parseDouble(session.getValue("v_prov_comi_capital_AVE").toString()));
                    session.putValue("v_ret_rend_AVE", Double.parseDouble(session.getValue("v_retencion_rend_AVE").toString()));
                    session.putValue("v_cc_AVE", Double.parseDouble(session.getValue("v_retencion_cont_AVE").toString()));                    
                }
                
                monto_retiro = v_format.format(Double.parseDouble(monto_retiro));
                TBPL_Publicar(v_producto,v_contrato_unif,v_nombre,v_apellidos,monto_retiro, s_concepto, v_fecefe, v_fecpro, v_orden_apli, v_tiporetiro, metodo_giro,
                              v_nomb_terc, v_apell_terc, v_tipodoc_ter, v_doc_ter, banco, tipocuenta, numero_cuenta, v_cuenta, v_mensLista);                
                
                
                //   String banco, String tipocuenta, String numero_cuenta, String v_cuenta, String v_mensLista
                out.println("<br>");
                out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
                out.println("<INPUT ID=cadena NAME=obligatoriov_valor TYPE=hidden VALUE='"+request.getParameter("obligatoriov_valor")+"'>");
                out.println("<INPUT ID=cadena NAME=v_cuenta TYPE=hidden VALUE='"+request.getParameter("v_cuenta")+"'>");
                out.println("<INPUT ID=cadena NAME=v_tipodoc_ter TYPE=hidden VALUE='"+request.getParameter("v_tipodoc_ter")+"'>");
                out.println("<INPUT ID=cadena NAME=v_doc_ter TYPE=hidden VALUE='"+request.getParameter("v_doc_ter")+"'>");
                out.println("<INPUT ID=cadena NAME=v_nomb_terc TYPE=hidden VALUE='"+request.getParameter("v_nomb_terc")+"'>");        
                out.println("<INPUT ID=cadena NAME=v_apell_terc TYPE=hidden VALUE='"+request.getParameter("v_apell_terc")+"'>");
                out.println("<INPUT ID=cadena NAME=v_concepto TYPE=hidden VALUE='"+request.getParameter("v_concepto")+"'>");
                out.println("<INPUT ID=cadena NAME=v_orden_apli TYPE=hidden VALUE='"+v_orden_apli+"'>");
                out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
                out.println("<TR><TD valign=TOP><center><input type=submit value='Aceptar' name ='aceptar'></TD><TD valign=TOP><input type=button value='Regresar' name = 'regresar' onclick=' history.go(-1)'></form></TD></TR>");                
                out.println("</table></center>");                 
                String v_pie = i_pagina.TBFL_PIE;
                out.println("<br>");
                out.println(""+v_pie+"");
                out.println("<br>");
                out.close();
            }
         else {
             if (v_orden_apli.equals("2") && (finalizar.equals("0"))) {                        
                    TBCL_SeleccionAportes objSeleccionAportes= new TBCL_SeleccionAportes();   
                    objSeleccionAportes.TBPL_SeleccionAportes(session,request,out,nuevaCadena);                                 
                 }
             else {
                 if (v_orden_apli.equals("2") && (finalizar.equals("1"))) {
                     
                     String[] NumApor = new String[request.getParameterValues("seleccion").length];                     
                     
                     for (int v_con=0;v_con < NumApor.length; v_con++)
                     {
                         NumApor[v_con] = request.getParameterValues("seleccion")[v_con];
                         saldos = NumApor[v_con].split(";");
                         monto_selec = Double.parseDouble(saldos[2]) + monto_selec;
                         num_aportes = saldos[1] + ";" + num_aportes;
                         v_cc += Double.parseDouble(saldos[3]);
                         v_ret_rend += Double.parseDouble(saldos[4]);
                     }
                     
                     session.removeValue("seleccion");
                     session.putValue("seleccion", num_aportes);
                     
                     session.removeValue("v_disponible_neto");
                     session.putValue("v_disponible_neto", Double.valueOf(monto_selec));
                     
                     session.removeValue("v_cc");
                     session.putValue("v_cc", Double.valueOf(v_cc));
                     
                     session.removeValue("v_ret_rend");
                     session.putValue("v_ret_rend", Double.valueOf(v_ret_rend));                     
                     
                     if (monto_selec > Double.parseDouble(monto_retiro)) {                     
                         v_pintar=    i_pagina.TBFL_CABEZA("Resumen de la Solicitud","Resumen de la Solicitud","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_FinalizarRetiro","",true,"modulo_retiros_oblig.js","return validar_retiro(this)");                
                         out.println(""+v_pintar+"");                                                           
                         s_concepto = v_concepto.equals("0")?"Aportes Voluntarios Afiliado":"Aportes Voluntarios Empleador";            
                         monto_retiro = v_format.format(Double.parseDouble(monto_retiro));                         
                         TBPL_Publicar(v_producto,v_contrato_unif,v_nombre,v_apellidos,monto_retiro, s_concepto, v_fecefe, v_fecpro, v_orden_apli, v_tiporetiro, metodo_giro,
                                       v_nomb_terc, v_apell_terc, v_tipodoc_ter, v_doc_ter, banco, tipocuenta, numero_cuenta, v_cuenta, v_mensLista);
                         out.println("<br>");
                         out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
                         out.println("<INPUT ID=cadena NAME=obligatoriov_valor TYPE=hidden VALUE='"+request.getParameter("obligatoriov_valor")+"'>");
                         out.println("<INPUT ID=cadena NAME=v_cuenta TYPE=hidden VALUE='"+request.getParameter("v_cuenta")+"'>");
                         out.println("<INPUT ID=cadena NAME=v_tipodoc_ter TYPE=hidden VALUE='"+request.getParameter("v_tipodoc_ter")+"'>");
                         out.println("<INPUT ID=cadena NAME=v_doc_ter TYPE=hidden VALUE='"+request.getParameter("v_doc_ter")+"'>");
                         out.println("<INPUT ID=cadena NAME=v_nomb_terc TYPE=hidden VALUE='"+request.getParameter("v_nomb_terc")+"'>");        
                         out.println("<INPUT ID=cadena NAME=v_apell_terc TYPE=hidden VALUE='"+request.getParameter("v_apell_terc")+"'>");
                         out.println("<INPUT ID=cadena NAME=v_concepto TYPE=hidden VALUE='"+request.getParameter("v_concepto")+"'>");
                         out.println("<INPUT ID=cadena NAME=v_orden_apli TYPE=hidden VALUE='"+v_orden_apli+"'>");                         
                         out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");                         
                         out.println("<TR><TD valign=TOP><center><input type=submit value='Aceptar' name ='aceptar'></TD><TD valign=TOP><input type=button value='Regresar' name = 'regresar' onclick=' history.go(-1)'></form></TD></TR>");     
                         out.println("</table></center>");                         
                         String v_pie = i_pagina.TBFL_PIE;
                         out.println("<br>");
                         out.println(""+v_pie+"");
                         out.println("<br>");
                         out.close();
                     }
                     else {
                         v_pintar=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Saldo de aportes seleccionados es "+v_format.format(monto_selec).toString()+" y el valor total de la solicitud es "+v_format.format(Double.parseDouble(monto_retiro)).toString()+"</center>",false);
                         out.println(""+v_pintar+"");
                         out.println("<BR>");                         
                         out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
                         out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
                         out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                         out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                                     "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                                     "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                                     "</FORM></TD></TR>");
                         out.println("</table></center>");                          
                         String v_pie = i_pagina.TBFL_PIE;
                         out.println("<br>");
                         out.println(""+v_pie+"");
                         out.close(); 
                     }
                 }             
             else {
                 v_pintar=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error con el valor de seleccion de ordenamiento.</center>",false);
                 out.println(""+v_pintar+"");
                 out.println("<BR>");
                 out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
                 out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
                 out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
                 out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+" VALUE='"+nuevaCadena+"'>" + 
                             "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                             "</FORM></TD></TR>");
                 out.println("</table></center>");                  
                 String v_pie = i_pagina.TBFL_PIE;
                 out.println("<br>");
                 out.println(""+v_pie+"");
                 out.close();
             } 
             }
         }        
            
        }        
        catch(Exception ex)
        {
         String v_pintar="";
         String error = ex.toString();
         if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
         {
          v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.</center>",false);
         }
         else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
              {
               v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
              }
                 else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                      {
                       v_pintar = i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.</center>",false);
                      }
                      else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                           {
                             v_pintar = i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.</center>",false);
                           }
                        else
                        {
                         v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                        }
         out.println(""+v_pintar+"");
         out.println("<BR>");
         out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
         out.println("<TR><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></TD>");
         out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
         out.println("<FORM name=link5 id=link5 action=http://"+v_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                     "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+request.getParameter("cadena")+"'>" + 
                     "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                     "</FORM></TD></TR>");
         out.println("</table></center>");                         
         String v_pie = i_pagina.TBFL_PIE;
         out.println("<br>");
         out.println(""+v_pie+"");
         out.close();
         }
        }
    
    private void TBPL_Publicar(String v_producto,String s_contrato_unif, String v_nombre, String v_apellidos, String monto_retiro, String s_concepto, String v_fecefe, 
                               String v_fecpro, String v_orden_apli, String v_tiporetiro, String metodo_giro, String nombres, String apellidos, String tipoid, String id,
                               String banco, String tipocuenta, String numero_cuenta, String v_cuenta, String v_mensLista)
      {                        
        String txt_ordenapli = "";  
        if (v_orden_apli.toString().trim().equals("0"))         
            txt_ordenapli = "FIFO";
        if (v_orden_apli.toString().trim().equals("1"))         
            txt_ordenapli = "LIFO";
        if (v_orden_apli.toString().trim().equals("2"))         
            txt_ordenapli = "Puntual";  
        
        if (tipocuenta.contains("AHORROS"))
            tipocuenta = "Cuenta de Ahorros";
        else
            tipocuenta = "Cuenta Corriente"; 
            
          //CABECERA
          out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_producto+"    <b>Contrato</b> "+s_contrato_unif+" </center></font>");
          out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nombre+"  <b> Apellidos </b>"+v_apellidos+" </CENTER></font>");          
          out.println("<BR>");                      
          out.println("<TABLE border=0 cellPadding=2 cellSpacing=0 cols=2 width=490>");              
          //Datalle del Retiro                             
          out.println("<TR><TD width=\"40%\"><FONT color=black face=verdana size=1><B>Valor Total del Retiro</B></font></TD><TD align=left width=\"60%\"><FONT color=black face=verdana size=1>: "+monto_retiro+"</font></TD></font></TR>");
          out.println("<TR><TD width=\"40%\"><FONT color=black face=verdana size=1><B>Concepto</B></font></TD><TD align=left width=\"60%\"><FONT color=black face=verdana size=1>: "+s_concepto+"</font></TD></font></TR>");
          out.println("<TR><TD width=\"40%\"><FONT color=black face=verdana size=1><B>Fecha de Solicitud Retiro</B></font></TD><TD align=left width=\"60%\"><FONT color=black face=verdana size=1>: "+v_fecefe+"</font></TD></font></TR>");
          out.println("<TR><TD width=\"40%\"><FONT color=black face=verdana size=1><B>Fecha Proceso Solicitud de Retiro</B></font></TD><TD align=left width=\"60%\"><FONT color=black face=verdana size=1>: "+v_fecpro+"</font></TD></font></TR>");
          out.println("<TR><TD width=\"40%\"><FONT color=black face=verdana size=1><B>Orden de Aplicación</B></font></TD><TD align=left width=\"60%\"><FONT color=black face=verdana size=1>: "+txt_ordenapli+"</font></TD></font></TR>");
          out.println("<TR><TD width=\"40%\"><FONT color=black face=verdana size=1><B>Tipo de Retiro</B></font></TD><TD align=left width=\"60%\"><FONT color=black face=verdana size=1>: "+v_tiporetiro+"</font></TD></font></TR>");
          out.println("<TR><TD width=\"40%\"><FONT color=black face=verdana size=1><B>Metodo de Giro</B></font></TD><TD align=left width=\"60%\"><FONT color=black face=verdana size=1>: "+metodo_giro+"</font></TD></font></TR>");                    
          
          if (v_cuenta.trim().equals("2")) {
              out.println("<TR><TD width=\"40%\"><FONT color=black face=verdana size=1><B>Datos Tercero</B></font></TD><TD align=left width=\"60%\"><FONT color=black face=verdana size=1>: "+nombres+ " " +apellidos + " " + tipoid + " " + id +"</font></TD></font></TR>");                    
          }
          else {
              if (!v_cuenta.trim().equals("1")) {
                  if (!id.equals("")) {
                      out.println("<TR><TD width=\"40%\"><FONT color=black face=verdana size=1><B>Datos Tercero</B></font></TD><TD align=left width=\"60%\"><FONT color=black face=verdana size=1>: "+nombres+ " " +apellidos + " " + tipoid + " " + id +"</font></TD></font></TR>");                                            
                  }
                  out.println("<TR><TD width=\"40%\"><FONT color=black face=verdana size=1><B>Datos Giro</B></font></TD><TD align=left width=\"60%\"><FONT color=black face=verdana size=1>: "+banco+"</font></TD></font></TR>");                     
                  out.println("<TR><TD width=\"40%\"><FONT color=black face=verdana size=1><B> </B></font></TD><TD align=left width=\"60%\"><FONT color=black face=verdana size=1>: "+tipocuenta+"</font></TD></font></TR>");                     
                  out.println("<TR><TD width=\"40%\"><FONT color=black face=verdana size=1><B> </B></font></TD><TD align=left width=\"60%\"><FONT color=black face=verdana size=1>: "+numero_cuenta+"</font></TD></font></TR>");                                           
              } 
           }
              
          if (!v_mensLista.equals(""))
              out.println(v_mensLista);                 
          
          out.println("</TABLE>"); 
          out.println("<BR>");          
          out.println("<center><FONT color=black face=verdana size=1><B>¿Desea Realizar la Solicitud de Retiro?</B></font></center>");                    
      } 
}
