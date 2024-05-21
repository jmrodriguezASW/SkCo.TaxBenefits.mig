/*@lineinfo:filename=TBCL_RetirosOblig*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import java.sql.*;

import oracle.sqlj.runtime.*;

import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import javax.servlet.http.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import java.io.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.*;
import TBPKT_AJUSTES.*;

public class TBCL_RetirosOblig extends HttpServlet{
    
    /**Iterator de la información del contrato.*/
    /*@lineinfo:generated-code*//*@lineinfo:19^5*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_cumple
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_cumple(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    CON_NOMBRESNdx = findColumn("CON_NOMBRES");
    CON_APELLIDOSNdx = findColumn("CON_APELLIDOS");
    REF_VALORNdx = findColumn("REF_VALOR");
    CON_NUMERO_IDENTIFICACIONNdx = findColumn("CON_NUMERO_IDENTIFICACION");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String CON_NOMBRES()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_NOMBRESNdx);
  }
  private int CON_NOMBRESNdx;
  public String CON_APELLIDOS()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_APELLIDOSNdx);
  }
  private int CON_APELLIDOSNdx;
  public String REF_VALOR()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(REF_VALORNdx);
  }
  private int REF_VALORNdx;
  public String CON_NUMERO_IDENTIFICACION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_NUMERO_IDENTIFICACIONNdx);
  }
  private int CON_NUMERO_IDENTIFICACIONNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:22^74*/

    /**Iterator de la información del tipo de usuarion.*/
    /*@lineinfo:generated-code*//*@lineinfo:25^5*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_tipo
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_tipo(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    COT_DESCRIPCIONNdx = findColumn("COT_DESCRIPCION");
    COT_REF_TIPO_TRANSACCIONNdx = findColumn("COT_REF_TIPO_TRANSACCION");
    COT_REF_CLASE_TRANSACCIONNdx = findColumn("COT_REF_CLASE_TRANSACCION");
    PRC_RETIRO_TOTALNdx = findColumn("PRC_RETIRO_TOTAL");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String COT_DESCRIPCION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(COT_DESCRIPCIONNdx);
  }
  private int COT_DESCRIPCIONNdx;
  public String COT_REF_TIPO_TRANSACCION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(COT_REF_TIPO_TRANSACCIONNdx);
  }
  private int COT_REF_TIPO_TRANSACCIONNdx;
  public String COT_REF_CLASE_TRANSACCION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(COT_REF_CLASE_TRANSACCIONNdx);
  }
  private int COT_REF_CLASE_TRANSACCIONNdx;
  public String PRC_RETIRO_TOTAL()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRC_RETIRO_TOTALNdx);
  }
  private int PRC_RETIRO_TOTALNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:28^66*/
    
    
    i_cumple v_cum;/**Variable tipo iterator i_cumple*/    
    i_tipo v_tipo;/**Variable tipo iterator i_tipo*/    
    
    public void TBPL_FechaRetiro(PrintWriter out,HttpSession session,HttpServletRequest request,String nuevaCadena)
    {
        /**Instancias de clase*/
        STBCL_GenerarBaseHTML i_pagina = new STBCL_GenerarBaseHTML(); /**Instancia de la clase TBCL_GenerarBaseHTML*/        
        TBCL_Validacion       i_valusu = new TBCL_Validacion();/**Instancia de la clase TBCL_Validacion*/
        String s_ruta_serv = "";
        try
        {
           String[] v_valusu = new String[3];
           v_valusu=i_valusu.TBFL_ValidarUsuario();
           DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
           Connection t_tax =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);   
           DefaultContext ctx12 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
           DefaultContext.setDefaultContext(ctx12);        
           /**Definicion de variables*/
           /**Tipo Date*/
           java.sql.Date v_fecha = new java.sql.Date(4);/**Fecha de proceso del retiro*/
           /**Tipo String*/           
           String v_pintar    = "";/**Dibujar inicio página*/
           String v_pie       = "";/**Dibujar final de página*/
           String v_nombre    = " ";/**Nombre del afiliado*/
           String v_apellidos = "";/**Apellido del afiliado*/ 
           String v_tipoiden = "";/**Tipo de Identificacion Afiliiado*/ 
           String v_identificacion = "";/**Numero de Identificacion Afiliado*/ 
           String v_contra    = (java.lang.String)session.getAttribute("s_contrato");/**Número de contrato*/
           String v_pro       = (java.lang.String)session.getAttribute("s_producto");/**Código de producto*/
              
           String v_coderr = "";
           String v_msgErr = "";                    
                          
           String v_fechaActual;
              
           String permiteRetiros = (java.lang.String)session.getAttribute("permiteRetiros");
            
           String bloqueoRetiroTotal = "";                    
           String v_contrato_unif = "";                    
           
           if (permiteRetiros!=null){
             if (permiteRetiros.equals("N")){
               throw new Exception("El contrato NO esta autorizado para realizar retiros"); 
             }
           }
          //Obtener direccion servidor TAX  
          /*@lineinfo:generated-code*//*@lineinfo:77^11*/

//  ************************************************************
//  #sql { select ref_descripcion  from tbreferencias WHERE REF_CODIGO='DSR001' };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "select ref_descripcion   from tbreferencias WHERE REF_CODIGO='DSR001'";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosOblig",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   s_ruta_serv = (String)__sJT_rs.getString(1);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:77^102*/  
          session.removeAttribute("s_ruta_serv");
          session.setAttribute("s_ruta_serv",(java.lang.Object)s_ruta_serv);  
            
          /**Consultar datos del contrato*/
          /*@lineinfo:generated-code*//*@lineinfo:82^11*/

//  ************************************************************
//  #sql v_cum = { select   CON_NOMBRES
//                                  ,CON_APELLIDOS
//                                  ,REF_VALOR
//                                  ,CON_NUMERO_IDENTIFICACION 
//                         from     tbcontratos_oblig    
//                                  inner join TBREFERENCIAS on ref_codigo = CON_REF_TIPO_IDENTIFICACION
//                         where    CON_PRO_CODIGO =:v_pro 
//                                  and LPAD(CON_NUMERO,9, '0')=:v_contra  
//                          };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "select   CON_NOMBRES\n                                ,CON_APELLIDOS\n                                ,REF_VALOR\n                                ,CON_NUMERO_IDENTIFICACION \n                       from     tbcontratos_oblig    \n                                inner join TBREFERENCIAS on ref_codigo = CON_REF_TIPO_IDENTIFICACION\n                       where    CON_PRO_CODIGO = :1   \n                                and LPAD(CON_NUMERO,9, '0')= :2 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosOblig",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_pro);
   __sJT_st.setString(2,v_contra);
   // execute query
   v_cum = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosOblig.i_cumple(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"1TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosOblig",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:90^24*/
                       
          while (v_cum.next())
          {
            v_nombre= v_cum.CON_NOMBRES().trim();
            session.removeAttribute("s_nombres");
            session.setAttribute("s_nombres",(java.lang.Object)v_nombre);
            v_apellidos = v_cum.CON_APELLIDOS().trim();
            session.removeAttribute("s_apellidos");
            session.setAttribute("s_apellidos",(java.lang.Object)v_apellidos);
            v_tipoiden = v_cum.REF_VALOR();
            session.removeAttribute("s_tipoidentificacion");
            session.setAttribute("s_tipoidentificacion",(java.lang.Object)v_tipoiden);
            v_identificacion = v_cum.CON_NUMERO_IDENTIFICACION();
            session.removeAttribute("s_identificacion");
            session.setAttribute("s_identificacion",(java.lang.Object)v_identificacion);
           }
           v_cum.close();
                        
           v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Solicitud de Retiro","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_Retiros","","modulo_retiros_oblig.js","return validarFecha_tiporetiro(obligatoriov_fecefectiva,v_retiro)");
           out.println(""+v_pintar+"");
            
           /*@lineinfo:generated-code*//*@lineinfo:112^12*/

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
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosOblig",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:112^85*/
           out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
           out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nombre+"  <b> Apellidos </b>"+v_apellidos+" </CENTER></font>");
           out.println("<br>");                             
           /*@lineinfo:generated-code*//*@lineinfo:116^12*/

//  ************************************************************
//  #sql { select TO_CHAR(to_date(sysdate, 'DD/MM/RR'), 'yyyy-mm-dd') fecha_actual  from dual };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "select TO_CHAR(to_date(sysdate, 'DD/MM/RR'), 'yyyy-mm-dd') fecha_actual   from dual";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosOblig",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   v_fechaActual = (String)__sJT_rs.getString(1);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:116^119*/           
           out.println("<input type='hidden' name='txtfechaActual' value='"+v_fechaActual+"'>");            
           
           /*@lineinfo:generated-code*//*@lineinfo:119^12*/

//  ************************************************************
//  #sql { call TBPBD_APLICA_RET_TOTAL_OBLIG( :v_pro
//                                              ,:v_contra
//                                              ,:bloqueoRetiroTotal
//                                              ,:v_coderr
//                                              ,:v_msgErr) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_APLICA_RET_TOTAL_OBLIG(  :1  \n                                            , :2  \n                                            , :3  \n                                            , :4  \n                                            , :5  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosOblig",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,v_pro);
   __sJT_st.setString(2,v_contra);
   __sJT_st.setString(3,bloqueoRetiroTotal);
   __sJT_st.setString(4,v_coderr);
   __sJT_st.setString(5,v_msgErr);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   bloqueoRetiroTotal = (String)__sJT_st.getString(3);
   v_coderr = (String)__sJT_st.getString(4);
   v_msgErr = (String)__sJT_st.getString(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:123^66*/
           out.println("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");                                                        
           out.println("<tr><td><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Tipo de Retiro</b></font></td><td align=right> <select name=v_retiro>");   
           
           /*@lineinfo:generated-code*//*@lineinfo:127^12*/

//  ************************************************************
//  #sql v_tipo = { SELECT COT_DESCRIPCION
//                                  ,COT_REF_TIPO_TRANSACCION
//                                  ,COT_REF_CLASE_TRANSACCION                                                                
//                                  ,PRC_RETIRO_TOTAL                               
//                          FROM    TBPRODUCTOS_CONCEPTOS
//                          INNER JOIN TBCONCEPTOS_TRANSACCION ON PRC_COT_REF_TRANSACCION           = COT_REF_TRANSACCION
//                                                                AND PRC_COT_REF_TIPO_TRANSACCION  = COT_REF_TIPO_TRANSACCION
//                                                                AND PRC_COT_REF_CLASE_TRANSACCION = COT_REF_CLASE_TRANSACCION 
//                          WHERE   PRC_PRO_CODIGO                = :v_pro 
//                                  AND PRC_COT_REF_TRANSACCION   = 'STR001'                                
//                                  AND PRC_ORIGEN_TAXB           = 'S'
//                          ORDER BY COT_DESCRIPCION };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT COT_DESCRIPCION\n                                ,COT_REF_TIPO_TRANSACCION\n                                ,COT_REF_CLASE_TRANSACCION                                                                \n                                ,PRC_RETIRO_TOTAL                               \n                        FROM    TBPRODUCTOS_CONCEPTOS\n                        INNER JOIN TBCONCEPTOS_TRANSACCION ON PRC_COT_REF_TRANSACCION           = COT_REF_TRANSACCION\n                                                              AND PRC_COT_REF_TIPO_TRANSACCION  = COT_REF_TIPO_TRANSACCION\n                                                              AND PRC_COT_REF_CLASE_TRANSACCION = COT_REF_CLASE_TRANSACCION \n                        WHERE   PRC_PRO_CODIGO                =  :1   \n                                AND PRC_COT_REF_TRANSACCION   = 'STR001'                                \n                                AND PRC_ORIGEN_TAXB           = 'S'\n                        ORDER BY COT_DESCRIPCION";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"5TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosOblig",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_pro);
   // execute query
   v_tipo = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosOblig.i_tipo(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"5TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetirosOblig",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:138^49*/
           out.println("               <option VALUE ='  '>                         ");
         
         while (v_tipo.next())
         {
             if ((bloqueoRetiroTotal!=null && bloqueoRetiroTotal.equals("S")) && (v_tipo.COT_REF_TIPO_TRANSACCION().equals("UTT002") || v_tipo.COT_REF_TIPO_TRANSACCION().equals("UTT003")|| v_tipo.COT_REF_TIPO_TRANSACCION().equals("UTT012"))){                   
                out.println("");
             } else {
                 out.println("<option value='"+v_tipo.COT_REF_TIPO_TRANSACCION()+v_tipo.COT_REF_CLASE_TRANSACCION()+v_tipo.PRC_RETIRO_TOTAL()+v_tipo.COT_DESCRIPCION()+"'>"+v_tipo.COT_DESCRIPCION());                                  
                 }
         }            
         v_tipo.close();
         out.println("</OPTION></SELECT></td></tr>");  
         out.println("</table>");
         out.println("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
         out.println("<pre>");
         out.println("<tr><td><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Fecha Solicitud del Retiro</b></font></td><td align=right><input type=\"text\" id=\"datepicker\" placeholder=\"aaaa-mm-dd\" name='obligatoriov_fecefectiva'></td></tr>");                                               
         out.println("</pre>");
         out.println("</table>");
         out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
         out.println("<br>");         
         out.println("<center><input type=submit value='Aceptar'></center>");
         v_pie = i_pagina.TBFL_PIE;
         out.println(""+v_pie+"");
         out.println("<br>");
         out.close();              
            
        } catch(Exception ex) {
            
            /*try {        
            SQL_PTB_RETIROS_OBLIG objSQL_PTB_RETIROS_OBLIG = new SQL_PTB_RETIROS_OBLIG();
            objSQL_PTB_RETIROS_OBLIG.INSERT_EVENTS_OBLIG("0",""+Integer.parseInt(session.getAttribute("s_contrato").toString()),session.getAttribute("s_producto").toString(),"E",session.getAttribute("s_usuariopipe").toString(),ex.toString());
            } catch(Exception ex1) {
                System.out.println("ERROR SQL_CIERRE: "+ex1);  
            }  */   
                    
            String v_pintar="";
            String error = ex.toString();
            if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
            {
                v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.</center>",false);
            } else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
                {
                    v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
                } else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection")) {
                        v_pintar = i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.</center>",false);
                    } else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                        {
                            v_pintar =  i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.</center>",false);
                        } else {
                            v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                        }
   out.println(""+v_pintar+"");
   out.println("<BR>");
   out.println("<center>");
   out.println("<a href=\"javascript:LinkSmit(link5)\" class=\"lmenu0\"></a>");
   out.println("<FORM name=link5 id=link5 action=http://"+s_ruta_serv.trim()+"/Servlets/TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_RetirosOblig method=post target=content>"+                     
                     "<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>" + 
                     "<input type=submit name = 'Cancelar' value='Cancelar'>"+
                     "</FORM>");
   out.println("<input type=button value='Regresar' onclick=' history.go(-1)'></center>");
   String v_pie = i_pagina.TBFL_PIE;
   out.println("<br>");
   out.println("<br>");
   out.println(""+v_pie+"");
   out.close();
  }
 }
}/*@lineinfo:generated-code*/