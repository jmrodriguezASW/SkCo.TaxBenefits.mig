/*@lineinfo:filename=SQL_TB_FREFERENCIAS_FPOB*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE;

import java.sql.*;
import oracle.sqlj.runtime.*;
import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

public class SQL_TB_FREFERENCIAS_FPOB extends Object{
    
    private int             v_sw            = 1;
    private String          v_sistema       = " ";
    private String          v_usuario       = " ";
    private String          v_password      = " ";
    private String          v_libreria      = " ";  
    private String          v_libd          = " ";
    private String[]        v_valusu;    
    
    public Modelo_TB_Referencias GET_TB_FREFERENCIAS_FPOB (String producto){
        
        Modelo_TB_Referencias objModelo_TB_Referencias = new Modelo_TB_Referencias();
        TBCL_Validacion i_valusu = new TBCL_Validacion();
        
        try {            
            //Conexion con la base de datos
            v_valusu = new String[3];
            v_valusu = i_valusu.TBFL_ValidarUsuario();
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            DefaultContext.setDefaultContext(new DefaultContext( v_valusu[0],v_valusu[1],v_valusu[2],false));
            
            /*@lineinfo:generated-code*//*@lineinfo:31^13*/ /*//  *************************************************************//*//  #sql v_sw = { values(TB_FREFERENCIAS_FPOB(:producto,*//*//                                                          :v_sistema,*//*//                                                          :v_usuario,*//*//                                                          :v_password,*//*//                                                          :v_libreria,*//*//                                                          :v_libd)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TB_FREFERENCIAS_FPOB( :2  ,\n                                                         :3  ,\n                                                         :4  ,\n                                                         :5  ,\n                                                         :6  ,\n                                                         :7  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE.SQL_TB_FREFERENCIAS_FPOB",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,producto); __sJT_st.setString(3,v_sistema); __sJT_st.setString(4,v_usuario); __sJT_st.setString(5,v_password); __sJT_st.setString(6,v_libreria); __sJT_st.setString(7,v_libd); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_sw = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_sistema = (String)__sJT_st.getString(3); v_usuario = (String)__sJT_st.getString(4); v_password = (String)__sJT_st.getString(5); v_libreria = (String)__sJT_st.getString(6); v_libd = (String)__sJT_st.getString(7); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 




                                                                        /*@lineinfo:user-code*//*@lineinfo:36^72*/
           if (v_sw == 0){                
                objModelo_TB_Referencias.setSistema(""+v_sistema+"");
                objModelo_TB_Referencias.setUsuario(""+v_usuario+"");
                objModelo_TB_Referencias.setPassword(""+v_password+"");
                objModelo_TB_Referencias.setLibreria(""+v_libreria+"");                
                objModelo_TB_Referencias.setlibreriadatos(""+v_libd+"");
            }                        
        }catch( Exception e ) {
            System.out.println("ERROR SQL_CIERRE: "+e);            
        }    
        return (objModelo_TB_Referencias);
    }
    
}