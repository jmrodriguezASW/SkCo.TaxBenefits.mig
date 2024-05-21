/*@lineinfo:filename=SQL_PTB_RETIROS_OBLIG*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.TBCL_Validacion;

import java.sql.DriverManager;

import java.text.DecimalFormat;

import sqlj.runtime.ref.DefaultContext;
import java.sql.Connection;

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



public class SQL_PTB_RETIROS_OBLIG extends Object{
        
    private String[] v_valusu;            
    private int v_cod_err = 0;  
    private String v_men_err;
    
    /*@lineinfo:generated-code*//*@lineinfo:33^5*/

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
    apo_rendimientosNdx = findColumn("apo_rendimientos");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double apo_rendimientos()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(apo_rendimientosNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int apo_rendimientosNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:33^66*/
    
    i_aportes v_aportes;     
    
    public String[] INSERT_RETPERSSON_OBLIG (String v_codproducto, String v_Nocontrato, double vretconsec, double aporte_actual){                
        TBCL_Validacion i_valusu = new TBCL_Validacion();
        String[] resul = new String[2];
        resul[0] = "0"; 
        resul[1] = "0";        
        try {            
            //Conexion con la base de datos
            v_valusu = new String[3];
            v_valusu = i_valusu.TBFL_ValidarUsuario();
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            DefaultContext.setDefaultContext(new DefaultContext( v_valusu[0],v_valusu[1],v_valusu[2],false));
            /*@lineinfo:generated-code*//*@lineinfo:48^13*/

//  ************************************************************
//  #sql { call tbpbd_insretperson_oblig(:vretconsec
//                                       ,:v_codproducto
//                                       ,:v_Nocontrato
//                                       ,:aporte_actual                                                             
//                                       ,:v_cod_err
//                                       ,:v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN tbpbd_insretperson_oblig( :1  \n                                     , :2  \n                                     , :3  \n                                     , :4                                                               \n                                     , :5  \n                                     , :6  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.SQL_PTB_RETIROS_OBLIG",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setDouble(1,vretconsec);
   __sJT_st.setString(2,v_codproducto);
   __sJT_st.setString(3,v_Nocontrato);
   __sJT_st.setDouble(4,aporte_actual);
   __sJT_st.setInt(5,v_cod_err);
   __sJT_st.setString(6,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_cod_err = __sJT_st.getInt(5); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(6);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:53^56*/            
        if(v_cod_err == 0) 
        {            
            /*@lineinfo:generated-code*//*@lineinfo:56^13*/

//  ************************************************************
//  #sql { Commit };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:56^25*/
            return resul;
        }
        else
        {
            /*@lineinfo:generated-code*//*@lineinfo:61^13*/

//  ************************************************************
//  #sql { Rollback };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:61^27*/
            resul[0] = ""+v_cod_err; 
            resul[1] = v_men_err;
            return resul;
        } 
                                                        
        }catch( Exception e ) {            
            resul[1] = ""+e;
            System.out.println("ERROR SQL_CIERRE: "+e);            
        }    
        return resul;
    }
    
     public String[] GENERAR_RETIRO_OBLIG (String v_codproducto, String v_contrato, Double v_valor_r, Double v_comision_pendiente_ava, Double v_comision_pendiente_ave, String v_fecha_efectiva, String v_fecha_proceso, String v_fecha_solicitud,
                                           String v_tipo_trans,String v_clase_trans, String v_tipo_valor,String v_banco, String v_cuenta, String v_tipodocumentoter, String v_documentoter,
                                           String v_nombrester, String v_apellidoster, String v_usuario, String v_orden, String v_concepto, Double v_saldoAVA, Double v_saldoAVE,
                                           Double v_disponible_neto, Double v_cc, Double v_ret_rend, Double v_prov_comi_rend, Double v_prov_comi_cap, int commit){                        
    
        TBCL_Validacion i_valusu = new TBCL_Validacion();
        String v_ret_consec = "0";
        String[] resul = new String[3];
        resul[0] = "0"; 
        resul[1] = "0";        
        resul[2] = "0";              
        
        try {                 
            //Conexion con la base de datos
            v_valusu = new String[3];
            v_valusu = i_valusu.TBFL_ValidarUsuario();
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            DefaultContext.setDefaultContext(new DefaultContext( v_valusu[0],v_valusu[1],v_valusu[2],false)); 
            
            /*@lineinfo:generated-code*//*@lineinfo:93^13*/

//  ************************************************************
//  #sql { call TBPBD_GENERAR_RETIRO_OBLIG(:v_codproducto
//                                       ,:v_contrato
//                                       ,:v_valor_r
//                                       ,:v_comision_pendiente_ava
//                                       ,:v_comision_pendiente_ave
//                                       ,:v_fecha_efectiva                                                             
//                                       ,:v_fecha_proceso
//                                       ,:v_fecha_solicitud
//                                       ,:v_tipo_trans
//                                       ,:v_clase_trans
//                                       ,:v_tipo_valor
//                                       ,:v_banco
//                                       ,:v_cuenta
//                                       ,:v_tipodocumentoter
//                                       ,:v_documentoter
//                                       ,:v_nombrester
//                                       ,:v_apellidoster
//                                       ,:v_usuario
//                                       ,:v_orden
//                                       ,:v_concepto
//                                       ,:v_saldoAVA
//                                       ,:v_saldoAVE 
//                                       ,:v_disponible_neto
//                                       ,:v_cc
//                                       ,:v_ret_rend
//                                       ,:v_prov_comi_rend
//                                       ,:v_prov_comi_cap
//                                       ,:v_ret_consec
//                                       ,:v_cod_err
//                                       ,:v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_GENERAR_RETIRO_OBLIG( :1  \n                                     , :2  \n                                     , :3  \n                                     , :4  \n                                     , :5  \n                                     , :6                                                               \n                                     , :7  \n                                     , :8  \n                                     , :9  \n                                     , :10  \n                                     , :11  \n                                     , :12  \n                                     , :13  \n                                     , :14  \n                                     , :15  \n                                     , :16  \n                                     , :17  \n                                     , :18  \n                                     , :19  \n                                     , :20  \n                                     , :21  \n                                     , :22   \n                                     , :23  \n                                     , :24  \n                                     , :25  \n                                     , :26  \n                                     , :27  \n                                     , :28  \n                                     , :29  \n                                     , :30  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.SQL_PTB_RETIROS_OBLIG",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(28,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(29,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(30,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,v_codproducto);
   __sJT_st.setString(2,v_contrato);
   if (v_valor_r == null) __sJT_st.setNull(3,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(3,v_valor_r.doubleValue());
   if (v_comision_pendiente_ava == null) __sJT_st.setNull(4,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(4,v_comision_pendiente_ava.doubleValue());
   if (v_comision_pendiente_ave == null) __sJT_st.setNull(5,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(5,v_comision_pendiente_ave.doubleValue());
   __sJT_st.setString(6,v_fecha_efectiva);
   __sJT_st.setString(7,v_fecha_proceso);
   __sJT_st.setString(8,v_fecha_solicitud);
   __sJT_st.setString(9,v_tipo_trans);
   __sJT_st.setString(10,v_clase_trans);
   __sJT_st.setString(11,v_tipo_valor);
   __sJT_st.setString(12,v_banco);
   __sJT_st.setString(13,v_cuenta);
   __sJT_st.setString(14,v_tipodocumentoter);
   __sJT_st.setString(15,v_documentoter);
   __sJT_st.setString(16,v_nombrester);
   __sJT_st.setString(17,v_apellidoster);
   __sJT_st.setString(18,v_usuario);
   __sJT_st.setString(19,v_orden);
   __sJT_st.setString(20,v_concepto);
   if (v_saldoAVA == null) __sJT_st.setNull(21,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(21,v_saldoAVA.doubleValue());
   if (v_saldoAVE == null) __sJT_st.setNull(22,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(22,v_saldoAVE.doubleValue());
   if (v_disponible_neto == null) __sJT_st.setNull(23,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(23,v_disponible_neto.doubleValue());
   if (v_cc == null) __sJT_st.setNull(24,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(24,v_cc.doubleValue());
   if (v_ret_rend == null) __sJT_st.setNull(25,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(25,v_ret_rend.doubleValue());
   if (v_prov_comi_rend == null) __sJT_st.setNull(26,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(26,v_prov_comi_rend.doubleValue());
   if (v_prov_comi_cap == null) __sJT_st.setNull(27,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(27,v_prov_comi_cap.doubleValue());
   __sJT_st.setString(28,v_ret_consec);
   __sJT_st.setInt(29,v_cod_err);
   __sJT_st.setString(30,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_ret_consec = (String)__sJT_st.getString(28);
   v_cod_err = __sJT_st.getInt(29); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(30);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:122^56*/            
        if(v_cod_err == 0) 
        {
            if (commit==0)            
                /*@lineinfo:generated-code*//*@lineinfo:126^17*/

//  ************************************************************
//  #sql { Commit };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:126^29*/
            resul[2] = v_ret_consec; 
            return resul;
        }
        else
        {            
            /*@lineinfo:generated-code*//*@lineinfo:132^13*/

//  ************************************************************
//  #sql { Rollback };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:132^27*/
            resul[0] = ""+v_cod_err; 
            resul[1] = v_men_err;
            resul[2] = "0";
            return resul;
        } 
                                                        
        }catch( Exception e ) { 
            resul[0] = "1"; 
            resul[1] = ""+e;
            System.out.println("ERROR SQL_CIERRE: "+e);            
        }    
        return resul;
    }
     
     public String[] UPDATE_RETPERSSON_OBLIG (String v_codproducto, String v_contrato, double v_ret_consec, int parametro){
           
        TBCL_Validacion i_valusu = new TBCL_Validacion();
        String[] resul = new String[3];
        resul[0] = "0"; 
        resul[1] = "0";    
        resul[2] = "" + v_ret_consec;  
        
        try {            
            //Conexion con la base de datos
            v_valusu = new String[3];
            v_valusu = i_valusu.TBFL_ValidarUsuario();
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            DefaultContext.setDefaultContext(new DefaultContext( v_valusu[0],v_valusu[1],v_valusu[2],false));            
            /*@lineinfo:generated-code*//*@lineinfo:161^13*/

//  ************************************************************
//  #sql { call TBPBD_UPDRETIRO_PERS_OBLIG(:v_ret_consec
//                                       ,:v_codproducto           
//                                       ,:v_contrato
//                                       ,:parametro
//                                       ,:v_cod_err
//                                       ,:v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_UPDRETIRO_PERS_OBLIG( :1  \n                                     , :2             \n                                     , :3  \n                                     , :4  \n                                     , :5  \n                                     , :6  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.SQL_PTB_RETIROS_OBLIG",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setDouble(1,v_ret_consec);
   __sJT_st.setString(2,v_codproducto);
   __sJT_st.setString(3,v_contrato);
   __sJT_st.setInt(4,parametro);
   __sJT_st.setInt(5,v_cod_err);
   __sJT_st.setString(6,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_cod_err = __sJT_st.getInt(5); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(6);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:166^56*/            
        if(v_cod_err == 0) 
        {
            /*@lineinfo:generated-code*//*@lineinfo:169^13*/

//  ************************************************************
//  #sql { Commit };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:169^25*/
            return resul;
        }
        else
        {            
            resul[0] = ""+v_cod_err; 
            resul[1] = v_men_err;
            resul[2] = "0";
            return resul;
        } 
                                                        
        }catch( Exception e ) { 
            resul[0] = "1"; 
            resul[1] = ""+e;
            System.out.println("ERROR SQL_CIERRE: "+e);            
        }    
        return resul;
         }     
     
    public String[] ELIMINAR_RETIRO_OBLIG (String producto, String contrato, String conret, String usuario){                
        
        TBCL_Validacion i_valusu = new TBCL_Validacion();
        String[] resul = new String[2];
        resul[0] = "0"; 
        resul[1] = "0";   
        
        try {                                
            //Conexion con la base de datos
            v_valusu = new String[3];
            v_valusu = i_valusu.TBFL_ValidarUsuario();
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            DefaultContext.setDefaultContext(new DefaultContext( v_valusu[0],v_valusu[1],v_valusu[2],false));                        
            
            /*@lineinfo:generated-code*//*@lineinfo:202^13*/

//  ************************************************************
//  #sql { call tbpbd_reversar_retiro_oblig(:producto
//                                       ,:contrato 
//                                       ,:conret
//                                       ,:usuario
//                                       ,'SER002'
//                                       ,SYSDATE
//                                       ,0
//                                       ,:v_cod_err
//                                       ,:v_men_err
//                                       ,''
//                                       ,0) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN tbpbd_reversar_retiro_oblig( :1  \n                                     , :2   \n                                     , :3  \n                                     , :4  \n                                     ,'SER002'\n                                     ,SYSDATE\n                                     ,0\n                                     , :5  \n                                     , :6  \n                                     ,''\n                                     ,0)\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.SQL_PTB_RETIROS_OBLIG",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,producto);
   __sJT_st.setString(2,contrato);
   __sJT_st.setString(3,conret);
   __sJT_st.setString(4,usuario);
   __sJT_st.setInt(5,v_cod_err);
   __sJT_st.setString(6,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_cod_err = __sJT_st.getInt(5); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(6);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:212^41*/ 
                                                                
        if(v_cod_err == 0) 
        {                    
            return resul;
        }
        else
        {                        
            resul[0] = ""+v_cod_err; 
            resul[1] = v_men_err;            
            return resul;
        } 
                                                        
        }catch( Exception e ) { 
            resul[0] = "1"; 
            resul[1] = ""+e;
            System.out.println("ERROR SQL_CIERRE: "+e);            
        }    
        return resul;    
    }
    
    public double[] CONSULTA_REND_TRAS_OBLIG (String producto, String contrato, int padre_consecutivo){                
        
        TBCL_Validacion i_valusu = new TBCL_Validacion();
        double[] resul = new double[3000];                
        int i = 0;                                
        
        try {                                
            //Conexion con la base de datos
            v_valusu = new String[3];
            v_valusu = i_valusu.TBFL_ValidarUsuario();
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            DefaultContext.setDefaultContext(new DefaultContext( v_valusu[0],v_valusu[1],v_valusu[2],false));                                                                
            
           /*@lineinfo:generated-code*//*@lineinfo:246^12*/

//  ************************************************************
//  #sql v_aportes = { select apo_rendimientos
//                                  from tbaportes
//                                  where apo_con_pro_codigo =:producto
//                                          and apo_con_numero = :contrato
//                                          and apo_apo_consecutivo =:padre_consecutivo
//                                  order by apo_consecutivo };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "select apo_rendimientos\n                                from tbaportes\n                                where apo_con_pro_codigo = :1  \n                                        and apo_con_numero =  :2  \n                                        and apo_apo_consecutivo = :3  \n                                order by apo_consecutivo";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.SQL_PTB_RETIROS_OBLIG",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,producto);
   __sJT_st.setString(2,contrato);
   __sJT_st.setInt(3,padre_consecutivo);
   // execute query
   v_aportes = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.SQL_PTB_RETIROS_OBLIG.i_aportes(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"4TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.SQL_PTB_RETIROS_OBLIG",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:251^57*/                                       
          
          while (v_aportes.next())
          {
            resul[i] = v_aportes.apo_rendimientos();
            i = i+1;
          }
          v_aportes.close();
                                                                                                                                
        }catch( Exception e ) { 
            System.out.println("ERROR SQL_CIERRE: "+e);            
        }    
        return resul;    
    }
     
}/*@lineinfo:generated-code*/