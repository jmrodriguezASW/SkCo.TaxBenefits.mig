/*@lineinfo:filename=TBCL_SeleccionAportes*//*@lineinfo:user-code*//*@lineinfo:1^1*//*@lineinfo:filename=TBCL_SeleccionAportes*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import sqlj.runtime.ref.DefaultContext ;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TBCL_SeleccionAportes extends HttpServlet {
    
    /**Iterator de aportes*/
     /*@lineinfo:generated-code*//*@lineinfo:20^6*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_aportes
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_aportes(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    APO_CONSECUTIVONdx = findColumn("APO_CONSECUTIVO");
    APO_FECHA_EFECTIVANdx = findColumn("APO_FECHA_EFECTIVA");
    SALDO_CAPITALNdx = findColumn("SALDO_CAPITAL");
    SALDO_RENDIMIENTOSNdx = findColumn("SALDO_RENDIMIENTOS");
    SALDO_CUENTA_CONTINGENTENdx = findColumn("SALDO_CUENTA_CONTINGENTE");
    SALDO_RETENCION_RENDNdx = findColumn("SALDO_RETENCION_REND");
    SALDO_UNIDADESNdx = findColumn("SALDO_UNIDADES");
    APO_APORTE_TRASLADONdx = findColumn("APO_APORTE_TRASLADO");
    APO_INFORMACION_TRASLADONdx = findColumn("APO_INFORMACION_TRASLADO");
    APO_APORTE_EMPRESANdx = findColumn("APO_APORTE_EMPRESA");
    POR_CCNdx = findColumn("POR_CC");
    POR_RET_RENDNdx = findColumn("POR_RET_REND");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double APO_CONSECUTIVO()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_CONSECUTIVONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_CONSECUTIVONdx;
  public java.sql.Date APO_FECHA_EFECTIVA()
    throws java.sql.SQLException
  {
    return (java.sql.Date)m_rs.getDate(APO_FECHA_EFECTIVANdx);
  }
  private int APO_FECHA_EFECTIVANdx;
  public double SALDO_CAPITAL()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(SALDO_CAPITALNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int SALDO_CAPITALNdx;
  public double SALDO_RENDIMIENTOS()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(SALDO_RENDIMIENTOSNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int SALDO_RENDIMIENTOSNdx;
  public double SALDO_CUENTA_CONTINGENTE()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(SALDO_CUENTA_CONTINGENTENdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int SALDO_CUENTA_CONTINGENTENdx;
  public double SALDO_RETENCION_REND()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(SALDO_RETENCION_RENDNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int SALDO_RETENCION_RENDNdx;
  public double SALDO_UNIDADES()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(SALDO_UNIDADESNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int SALDO_UNIDADESNdx;
  public String APO_APORTE_TRASLADO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(APO_APORTE_TRASLADONdx);
  }
  private int APO_APORTE_TRASLADONdx;
  public String APO_INFORMACION_TRASLADO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(APO_INFORMACION_TRASLADONdx);
  }
  private int APO_INFORMACION_TRASLADONdx;
  public String APO_APORTE_EMPRESA()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(APO_APORTE_EMPRESANdx);
  }
  private int APO_APORTE_EMPRESANdx;
  public double POR_CC()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(POR_CCNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int POR_CCNdx;
  public double POR_RET_REND()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(POR_RET_RENDNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int POR_RET_RENDNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:31^61*/
     
    i_aportes v_aportes;/**Variable tipo iterator i_contrato*/
                         
    String v_pintar = "";    
    String v_fechaefectiva = "";

    public void TBPL_SeleccionAportes(HttpSession session,HttpServletRequest request,PrintWriter out,String nuevaCadena )
    {           
       String v_contra      = "";/**Variable número del contrato*/
       String v_pro         = "";/**Variable código producto*/         
       String v_nombre      = "";/**Variable nombre afiliado*/
       String v_apellidos   = "";/**Variable apellido afiliado*/
       String v_concepto    = "S";
       double v_capital_tot = 0;
       double v_rendimientos_tot    = 0;
       double saldo_disponible = 0;
       double cap_prorrata = 0;
       double rend_prorrata = 0;
       double v_prov_comi_total_AVA = 0;
       double v_prov_comi_total_AVE = 0;
       String monto_retiro = "";
       
       TBCL_Validacion       i_valusu = new TBCL_Validacion();
       
       DecimalFormat v_format = new  DecimalFormat("¤###,###,###,###,###,###.##");            
                   
       STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();/**Instancia de la clase TBCL_GenerarBaseHTML*/
       
       SimpleDateFormat FechaPantalla = new SimpleDateFormat("yyyy-MM-dd");
       SimpleDateFormat Fecha400 = new SimpleDateFormat("yyyyMMdd");            
       
       try
       {
           String[] v_valusu = new String[3];
           v_valusu=i_valusu.TBFL_ValidarUsuario();
           DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
           Connection t_tax =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
           DefaultContext ctx11 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
           DefaultContext.setDefaultContext(ctx11);
           
       /**Verificar que las variables de session no expiren*/
       if((java.lang.String)session.getAttribute("s_contrato") != null ||(java.lang.String)session.getAttribute("s_producto") != null)
       {                              
            /**Capturar variables de session*/
            v_contra            = (java.lang.String)session.getAttribute("s_contrato");/**Tomar contrato*/
            v_pro               = (java.lang.String)session.getAttribute("s_producto");/**Tomar producto*/  
            v_nombre            = (java.lang.String)session.getAttribute("s_nombres");
            v_apellidos         = (java.lang.String)session.getAttribute("s_apellidos");                
            String v_fecefe     = (java.lang.String)session.getAttribute("s_fecefectiva");
            String v_fecpro     = (java.lang.String)session.getAttribute("s_fecpro");
            monto_retiro = request.getParameter("obligatoriov_valor");   
            v_prov_comi_total_AVA = Double.parseDouble(session.getAttribute("v_prov_comi_rend_AVA").toString()) + Double.parseDouble(session.getAttribute("v_prov_comi_capital_AVA").toString());    
            v_prov_comi_total_AVE = Double.parseDouble(session.getAttribute("s_prov_comi_total_AVE").toString());   
            
            v_contra = String.valueOf(Integer.parseInt(v_contra));
            
            if (request.getParameter("v_concepto").toString().equals("0"))
                v_concepto = "N";
        
     oracle.jdbc.OracleCallableStatement sJT_stTem = null;
     sqlj.runtime.ref.DefaultContext sJT_ccTem = sqlj.runtime.ref.DefaultContext.getDefaultContext(); 
     if (sJT_ccTem==null) 
         sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
     sqlj.runtime.ExecutionContext.OracleContext sJT_ecTe = ((sJT_ccTem.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sJT_ccTem.getExecutionContext().getOracleContext());                
                try {
   String theSqlTS = "BEGIN :1 := TB_PKGSELECCION_APORTES_OBLIG.TB_FFILTRO_APORTE( :2 , :3  , :4 , :5 , :6 , :7 , :8 ) \n; END;";
   sJT_stTem = sJT_ecTe.prepareOracleCall(sJT_ccTem,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_SeleccionAportes",theSqlTS);
   if (sJT_ecTe.isNew())
   {
      sJT_stTem.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);
   }   
                    
   sJT_stTem.setString(2,v_pro);    
   sJT_stTem.setString(3,v_contra);
   sJT_stTem.setString(4,"A");
   sJT_stTem.setDouble(5,Double.parseDouble(request.getParameter("v_concepto").toString()));
   sJT_stTem.setString(6,Fecha400.format(FechaPantalla.parse(v_fecefe)).toString());
   //sJT_stTem.setString(7,Fecha400.format(FechaPantalla.parse(v_fecpro)).toString());
   //ACA DEBEMOS ENVIAR EL DE AVA O AVE
   if (v_concepto.equals("N"))                 
       sJT_stTem.setDouble(7,calculodecimal(Double.parseDouble(session.getAttribute("v_valorunidad_AVA").toString()),6));
   else
       sJT_stTem.setDouble(7,calculodecimal(Double.parseDouble(session.getAttribute("v_valorunidad_AVE").toString()),6));       
   sJT_stTem.setDouble(8,1);
   sJT_ecTe.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_aportes = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_SeleccionAportes.i_aportes(new sqlj.runtime.ref.OraRTResultSet(sJT_stTem.getCursor(1)));
  } finally {sJT_ecTe.oracleCloseQuery(); }           

      v_pintar = i_pagina.TBFL_CABEZA("Detalle del Retiro","Detalle del Retiro", "TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_ConfirmarRetiro", "", true,"","");      
      out.println(""+v_pintar+"");
      out.println("<BR>");                         
      out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contra+" </center></font>");
      out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nombre+"  <b> Apellidos </b>"+v_apellidos+" </CENTER></font>");
      out.println("<br>");
      out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Valor a Retirar</b>  "+v_format.format(Double.parseDouble(monto_retiro)).toString()+" </CENTER></font>");
     
      out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
      out.println("<tr><td class=\"td11\" colspan= 5 ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Saldos Disponibles de los aportes</b></font></center></font></td></tr>");
      out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Fecha Aporte</b></center></font></td>");
      out.println("<td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Saldo Disponible</b></center></font></td>");
      out.println("<td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Retención Contingente</b></center></font></td>");
      out.println("<td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Retención Sobre Rendimientos</b></center></font></td>");
      out.println("<td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Concepto</b></center></font></td></TR>");
      v_concepto = v_concepto.equals("N")?"AVA":"AVE";
      
      ArrayList<Modelo_Disponible_OBLIG> objModelo_disponible_OBLIG = new ArrayList<Modelo_Disponible_OBLIG>();      
      
      while(v_aportes.next()) {
          
          Modelo_Disponible_OBLIG objDisponible = new Modelo_Disponible_OBLIG();
          
          if (v_aportes.SALDO_RENDIMIENTOS()<0) {
              v_capital_tot = v_capital_tot + v_aportes.SALDO_CAPITAL()+v_aportes.SALDO_RENDIMIENTOS(); 
          }
          else {
              v_capital_tot = v_capital_tot + v_aportes.SALDO_CAPITAL();
              v_rendimientos_tot = v_rendimientos_tot + v_aportes.SALDO_RENDIMIENTOS();
          }
                              
          objDisponible.setAPO_CONSECUTIVO(v_aportes.APO_CONSECUTIVO());
          objDisponible.setAPO_FECHA_EFECTIVA(v_aportes.APO_FECHA_EFECTIVA().toString()); 

          objDisponible.setSALDO_CAPITAL(v_aportes.SALDO_CAPITAL());
          objDisponible.setSALDO_RENDIMIENTOS(v_aportes.SALDO_RENDIMIENTOS());
          
          objDisponible.setSALDO_CUENTA_CONTINGENTE(v_aportes.SALDO_CUENTA_CONTINGENTE());
          objDisponible.setSALDO_RETENCION_REND(v_aportes.SALDO_RETENCION_REND());
          objDisponible.setSALDO_UNIDADES(v_aportes.SALDO_UNIDADES());
          
          objDisponible.setAPO_APORTE_TRASLADO(v_aportes.APO_APORTE_TRASLADO());
          objDisponible.setAPO_INFORMACION_TRASLADO(v_aportes.APO_INFORMACION_TRASLADO());
          objDisponible.setAPO_INFORMACION_TRASLADO(v_aportes.APO_APORTE_EMPRESA());
          
          objDisponible.setPOR_CC(v_aportes.POR_CC());
          objDisponible.setPOR_RET_REND(v_aportes.POR_RET_REND());
          
          objModelo_disponible_OBLIG.add(objDisponible);                          
          }             
      v_aportes.close();
      
      double v_prov_comi_cap = 0;
      double v_prov_comi_rend = 0;
      
      if (v_concepto.equals("AVA")) {  
          v_prov_comi_total_AVE = 0;   
          rend_prorrata = 1;              
          cap_prorrata = 0;
          session.removeAttribute("v_prov_comi_rend");
          session.setAttribute("v_prov_comi_rend",v_prov_comi_total_AVA);
          session.removeAttribute("v_prov_comi_cap");
          session.setAttribute("v_prov_comi_cap",0.0);
          v_prov_comi_cap = 0;
          v_prov_comi_rend = v_prov_comi_total_AVA;
          if (v_prov_comi_total_AVA>v_rendimientos_tot) {
               v_prov_comi_total_AVA = v_prov_comi_total_AVA-v_rendimientos_tot;               
               rend_prorrata = 0;               
               cap_prorrata = 1;               
               session.removeAttribute("v_prov_comi_rend");
               session.setAttribute("v_prov_comi_rend",0.0);
               session.removeAttribute("v_prov_comi_cap");
               session.setAttribute("v_prov_comi_cap",v_prov_comi_total_AVA);
               v_prov_comi_cap = v_prov_comi_total_AVA;
               v_prov_comi_rend = 0;
            }                      
        }
      
      if (v_concepto.equals("AVE")) {
          v_prov_comi_total_AVA=0;
          rend_prorrata = 1;              
          cap_prorrata = 0;
          session.removeAttribute("v_prov_comi_rend");
          session.setAttribute("v_prov_comi_rend",v_prov_comi_total_AVE);
          session.removeAttribute("v_prov_comi_cap");
          session.setAttribute("v_prov_comi_cap",0.0);
          v_prov_comi_cap = 0;
          v_prov_comi_rend = v_prov_comi_total_AVE;
          if (v_prov_comi_total_AVE>v_rendimientos_tot) {
              v_prov_comi_total_AVE = v_prov_comi_total_AVE-v_rendimientos_tot;
              rend_prorrata = 0;              
              cap_prorrata = 1;
              session.removeAttribute("v_prov_comi_rend");
              session.setAttribute("v_prov_comi_rend",0.0);
              session.removeAttribute("v_prov_comi_cap");
              session.setAttribute("v_prov_comi_cap",v_prov_comi_total_AVE);
              v_prov_comi_cap = v_prov_comi_total_AVE;
              v_prov_comi_rend = 0;
            }          
        }
     
     double ret_rend = 0; 
     double ret_cap = 0;
     double rendimientos = 0;    
     double capital = 0;
     double procap = 0;
     double proren = 0;
     double CUR_SALDO_CAPITAL = 0;
     double CUR_SALDO_RENDIMIENTOS = 0;
     double CUR_SALDO_CUENTA_CONTINGENTE = 0;
     double CUR_SALDO_RETENCION_REND = 0;
     double CUR_POR_RET_REND = 0;
     double CUR_POR_CC = 0;
     
     for(Modelo_Disponible_OBLIG item :objModelo_disponible_OBLIG) {               
         v_fechaefectiva = item.getAPO_FECHA_EFECTIVA();
         
         CUR_SALDO_CAPITAL = item.getSALDO_CAPITAL();
         CUR_SALDO_RENDIMIENTOS = item.getSALDO_RENDIMIENTOS();
         CUR_SALDO_CUENTA_CONTINGENTE = item.getSALDO_CUENTA_CONTINGENTE();
         CUR_SALDO_RETENCION_REND = item.getSALDO_RETENCION_REND();
         CUR_POR_RET_REND = item.getPOR_RET_REND();
         CUR_POR_CC = item.getPOR_CC();    
         
         if (CUR_SALDO_CAPITAL>0) {                                    
             if(CUR_SALDO_RENDIMIENTOS<0) {
                 CUR_SALDO_CUENTA_CONTINGENTE = calculodecimal((CUR_SALDO_CAPITAL + CUR_SALDO_RENDIMIENTOS) * (CUR_SALDO_CUENTA_CONTINGENTE/CUR_SALDO_CAPITAL),6);
                 CUR_SALDO_CAPITAL = CUR_SALDO_CAPITAL + CUR_SALDO_RENDIMIENTOS;                                        
                 CUR_SALDO_RENDIMIENTOS = 0;
                 CUR_SALDO_RETENCION_REND = 0;
                 }
             if (v_prov_comi_cap==0) {
                 if (v_prov_comi_rend>0) {
                     CUR_SALDO_RENDIMIENTOS = calculodecimal((CUR_SALDO_RENDIMIENTOS - ((CUR_SALDO_RENDIMIENTOS/v_rendimientos_tot)*v_prov_comi_rend)),6);
                     CUR_SALDO_RETENCION_REND = CUR_SALDO_RENDIMIENTOS * CUR_POR_RET_REND;
                     }                                      
                 }  
             if (v_prov_comi_cap>0) {
                 CUR_SALDO_RENDIMIENTOS = 0;
                 CUR_SALDO_RETENCION_REND = 0;
                 CUR_SALDO_CAPITAL = calculodecimal((CUR_SALDO_CAPITAL - ((CUR_SALDO_CAPITAL/v_capital_tot)*v_prov_comi_cap)),6);
                 CUR_SALDO_CUENTA_CONTINGENTE = CUR_SALDO_CAPITAL * CUR_POR_CC;                                   
                 }    
             
             saldo_disponible = CUR_SALDO_CAPITAL - CUR_SALDO_CUENTA_CONTINGENTE + CUR_SALDO_RENDIMIENTOS - CUR_SALDO_RETENCION_REND;
             if (saldo_disponible>0) {
                out.println("<tr><td ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><input type=checkbox name=seleccion value = 'A;"+item.getAPO_CONSECUTIVO()+";"+saldo_disponible+";"+CUR_SALDO_CUENTA_CONTINGENTE+";"+CUR_SALDO_RETENCION_REND+"')>"+v_fechaefectiva+"</font></center></td>");
                out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+
                            v_format.format(saldo_disponible)+
                            "</font></center></td>");
                out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+v_format.format(CUR_SALDO_CUENTA_CONTINGENTE)+"</font></center></td>");
                out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+v_format.format(CUR_SALDO_RETENCION_REND)+"</font></center></td>");
                out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+v_concepto+"</font></center></td></tr>");
                }
             }
         }
         
         out.println("</table></center>");
         out.println("<br>");
         out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden style=visibility:hidden VALUE='"+nuevaCadena+"'>");
         out.println("<INPUT ID=cadena NAME=obligatoriov_valor TYPE=hidden style=visibility:hidden VALUE='"+monto_retiro+"'>");
         out.println("<INPUT ID=cadena NAME=v_cuenta TYPE=hidden style=visibility:hidden VALUE='"+request.getParameter("v_cuenta")+"'>");
         out.println("<INPUT ID=cadena NAME=v_tipodoc_ter TYPE=hidden style=visibility:hidden VALUE='"+request.getParameter("v_tipodoc_ter")+"'>");
         out.println("<INPUT ID=cadena NAME=v_doc_ter TYPE=hidden style=visibility:hidden VALUE='"+request.getParameter("v_doc_ter")+"'>");
         out.println("<INPUT ID=cadena NAME=v_nomb_terc TYPE=hidden style=visibility:hidden VALUE='"+request.getParameter("v_nomb_terc")+"'>");        
         out.println("<INPUT ID=cadena NAME=v_apell_terc TYPE=hidden style=visibility:hidden VALUE='"+request.getParameter("v_apell_terc")+"'>");
         out.println("<INPUT ID=cadena NAME=v_concepto TYPE=hidden style=visibility:hidden VALUE='"+request.getParameter("v_concepto")+"'>");
         out.println("<INPUT ID=cadena NAME=v_orden_apli TYPE=hidden style=visibility:hidden VALUE='"+request.getParameter("v_orden_apli")+"'>"); 
         if (request.getParameter("v_concepto").equals("0"))
             out.println("<INPUT ID=cadena NAME=obDispAVA TYPE=hidden style=visibility:hidden VALUE='"+request.getParameter("obDispAVA")+"'>"); 
         else
             out.println("<INPUT ID=cadena NAME=obDispAVE TYPE=hidden style=visibility:hidden VALUE='"+request.getParameter("obDispAVE")+"'>");                 
         out.println("<INPUT ID=cadena NAME=finalizar TYPE=hidden VALUE='1'>");
         out.println("<center><table border='0' cellspacing='0' cellpadding='0'>");
         out.println("<TR><TD valign=TOP><center><input type=submit value='Aceptar' ></TD><TD valign=TOP><input type=button value='Regresar' onclick=' history.go(-1)'></form></TD>");
         out.println("<TD valign=TOP><a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
         out.println("<FORM name=link5 id=link5 action=http://localhost:7101/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                     "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                     "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                     "</FORM></TD></TR>");
         out.println("</table></center>"); 
         String v_pie = i_pagina.TBFL_PIE;
         out.println("<br>");
         out.println(""+v_pie+"");
         out.println("<br>");
         out.close();  
         }
    } catch(Exception ex) {         
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
         out.println("<center>");
         out.println("<input type=button value='Regresar' onclick=' history.go(-1)'>");
         out.println("<a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
         out.println("<FORM name=link5 id=link5 action=http://localhost:7101/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                             "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                             "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                             "</FORM> </center>");         
         String v_pie = i_pagina.TBFL_PIE;
         out.println("<br>");
         out.println(""+v_pie+"");
         out.close();
        }        
    }    
    
     public static double calculodecimal(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
    
}/*@lineinfo:generated-code*/