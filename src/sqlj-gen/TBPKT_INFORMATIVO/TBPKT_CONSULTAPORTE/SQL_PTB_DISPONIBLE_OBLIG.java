/*@lineinfo:filename=SQL_PTB_DISPONIBLE_OBLIG*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE;


import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.TBCL_Validacion;

import java.sql.DriverManager;

import java.text.DecimalFormat;

import sqlj.runtime.ref.DefaultContext;


public class SQL_PTB_DISPONIBLE_OBLIG extends Object{
        
    private String[]        v_valusu;        
    private double v_aportes_sin_cert_AVA = 0; 
    private double v_aportes_sin_cert_AVE = 0;   
    private double v_aportes_hist_pend_AVA = 0;   
    private double v_aportes_hist_pend_AVE = 0;   
    private double v_ret_pend_procesar_AVA = 0;   
    private double v_ret_pend_procesar_AVE = 0;   
    private double v_retencion_cont_AVA = 0;      
    private double v_retencion_cont_AVE = 0;     
    private double v_retencion_rend_AVA = 0;     
    private double v_retencion_rend_AVE = 0;  
    private double v_disponibleTOblig = 0;
    private double v_disponibleAVA = 0;
    private double v_disponibleAVE = 0;  
    private double v_valor_unidad_AVA = 0;
    private double v_valor_unidad_AVE = 0;
    private double v_prov_comi_rend_AVA = 0;
    private double v_prov_comi_capital_AVA = 0;
    private double v_prov_comi_rend_AVE = 0;
    private double v_prov_comi_capital_AVE = 0;
    private double v_prov_comi_total_AVA = 0;
    private double v_prov_comi_total_AVE = 0;
    private int v_cod_err = 0;  
    private String v_men_err;
    
    public String[] GET_DISPONIBLE_OBLIG (String v_codproducto, String v_Nocontrato, double v_saldoAVA, double v_saldoAVE, String v_fecha, String[] comisiones, double Credito){
        String[] SaldosFinales = new String[23];
        Modelo_TB_Referencias objModelo_TB_Referencias = new Modelo_TB_Referencias();
        /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_ValidarUsuario de la clase TBCL_Validacion, no es necesaria la instancia nueva*/ 
 //TBCL_Validacion i_valusu = new TBCL_Validacion(); 
 //TBCL_Validacion  i_valusu = new TBCL_Validacion()
        double v_comision_AVA = Double.parseDouble(comisiones[0]) + Credito;
        double v_comision_AVE = Double.parseDouble(comisiones[1]);
        double prov_credito = 0;        
        
        try {            
            //Conexion con la base de datos
            v_valusu = new String[3];
            v_valusu = TBCL_Validacion.TBFL_ValidarUsuario();
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            DefaultContext.setDefaultContext(new DefaultContext( v_valusu[0],v_valusu[1],v_valusu[2],false));
            v_Nocontrato = ""+Integer.parseInt(v_Nocontrato);
                        
            /*@lineinfo:generated-code*//*@lineinfo:58^13*/

//  ************************************************************
//  #sql { call TBPBD_DISPONIBLE_OBLIG(:v_codproducto
//                                       ,:v_Nocontrato
//                                       ,:v_saldoAVA
//                                       ,:v_saldoAVE
//                                       ,:v_comision_AVA  
//                                       ,:v_comision_AVE 
//                                       ,:v_fecha
//                                       ,:v_aportes_sin_cert_AVA
//                                       ,:v_aportes_sin_cert_AVE
//                                       ,:v_aportes_hist_pend_AVA
//                                       ,:v_aportes_hist_pend_AVE
//                                       ,:v_ret_pend_procesar_AVA
//                                       ,:v_ret_pend_procesar_AVE
//                                       ,:v_retencion_cont_AVA
//                                       ,:v_retencion_cont_AVE
//                                       ,:v_retencion_rend_AVA
//                                       ,:v_retencion_rend_AVE
//                                       ,:v_valor_unidad_AVA
//                                       ,:v_valor_unidad_AVE
//                                       ,:v_prov_comi_rend_AVA
//                                       ,:v_prov_comi_capital_AVA
//                                       ,:v_prov_comi_rend_AVE
//                                       ,:v_prov_comi_capital_AVE
//                                       ,:v_cod_err
//                                       ,:v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_DISPONIBLE_OBLIG( :1  \n                                     , :2  \n                                     , :3  \n                                     , :4  \n                                     , :5    \n                                     , :6   \n                                     , :7  \n                                     , :8  \n                                     , :9  \n                                     , :10  \n                                     , :11  \n                                     , :12  \n                                     , :13  \n                                     , :14  \n                                     , :15  \n                                     , :16  \n                                     , :17  \n                                     , :18  \n                                     , :19  \n                                     , :20  \n                                     , :21  \n                                     , :22  \n                                     , :23  \n                                     , :24  \n                                     , :25  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE.SQL_PTB_DISPONIBLE_OBLIG",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(13,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(14,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(17,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(18,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(19,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(20,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(21,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(22,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(23,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(24,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(25,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,v_codproducto);
   __sJT_st.setString(2,v_Nocontrato);
   __sJT_st.setDouble(3,v_saldoAVA);
   __sJT_st.setDouble(4,v_saldoAVE);
   __sJT_st.setDouble(5,v_comision_AVA);
   __sJT_st.setDouble(6,v_comision_AVE);
   __sJT_st.setString(7,v_fecha);
   __sJT_st.setDouble(8,v_aportes_sin_cert_AVA);
   __sJT_st.setDouble(9,v_aportes_sin_cert_AVE);
   __sJT_st.setDouble(10,v_aportes_hist_pend_AVA);
   __sJT_st.setDouble(11,v_aportes_hist_pend_AVE);
   __sJT_st.setDouble(12,v_ret_pend_procesar_AVA);
   __sJT_st.setDouble(13,v_ret_pend_procesar_AVE);
   __sJT_st.setDouble(14,v_retencion_cont_AVA);
   __sJT_st.setDouble(15,v_retencion_cont_AVE);
   __sJT_st.setDouble(16,v_retencion_rend_AVA);
   __sJT_st.setDouble(17,v_retencion_rend_AVE);
   __sJT_st.setDouble(18,v_valor_unidad_AVA);
   __sJT_st.setDouble(19,v_valor_unidad_AVE);
   __sJT_st.setDouble(20,v_prov_comi_rend_AVA);
   __sJT_st.setDouble(21,v_prov_comi_capital_AVA);
   __sJT_st.setDouble(22,v_prov_comi_rend_AVE);
   __sJT_st.setDouble(23,v_prov_comi_capital_AVE);
   __sJT_st.setInt(24,v_cod_err);
   __sJT_st.setString(25,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_aportes_sin_cert_AVA = __sJT_st.getDouble(8); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_aportes_sin_cert_AVE = __sJT_st.getDouble(9); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_aportes_hist_pend_AVA = __sJT_st.getDouble(10); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_aportes_hist_pend_AVE = __sJT_st.getDouble(11); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_ret_pend_procesar_AVA = __sJT_st.getDouble(12); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_ret_pend_procesar_AVE = __sJT_st.getDouble(13); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_retencion_cont_AVA = __sJT_st.getDouble(14); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_retencion_cont_AVE = __sJT_st.getDouble(15); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_retencion_rend_AVA = __sJT_st.getDouble(16); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_retencion_rend_AVE = __sJT_st.getDouble(17); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_valor_unidad_AVA = __sJT_st.getDouble(18); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_valor_unidad_AVE = __sJT_st.getDouble(19); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_prov_comi_rend_AVA = __sJT_st.getDouble(20); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_prov_comi_capital_AVA = __sJT_st.getDouble(21); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_prov_comi_rend_AVE = __sJT_st.getDouble(22); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_prov_comi_capital_AVE = __sJT_st.getDouble(23); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cod_err = __sJT_st.getInt(24); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(25);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:82^56*/
            
        if(v_cod_err == 0) 
        {            
            v_disponibleAVA = v_saldoAVA - v_aportes_sin_cert_AVA - v_aportes_hist_pend_AVA - v_ret_pend_procesar_AVA - v_retencion_cont_AVA - 
                              v_retencion_rend_AVA - v_prov_comi_rend_AVA - v_prov_comi_capital_AVA;
            
            v_disponibleAVE = v_saldoAVE - v_aportes_sin_cert_AVE - v_aportes_hist_pend_AVE - v_ret_pend_procesar_AVE - v_retencion_cont_AVE - 
                              v_retencion_rend_AVE - v_prov_comi_rend_AVE - v_prov_comi_capital_AVE;     
            
            if (v_prov_comi_rend_AVA + v_prov_comi_capital_AVA>=-0.01 && v_prov_comi_rend_AVA + v_prov_comi_capital_AVA<=0.01) {
                v_prov_comi_total_AVA = 0;
                prov_credito = 0;
            }
            else {
                if (v_prov_comi_rend_AVA + v_prov_comi_capital_AVA>=Credito-0.01)
                   v_prov_comi_total_AVA =  v_prov_comi_rend_AVA + v_prov_comi_capital_AVA - Credito;
                else
                   v_prov_comi_total_AVA = 0; 
                
                if (v_prov_comi_total_AVA > 0)
                   prov_credito =  Credito;
                else
                   prov_credito = v_prov_comi_rend_AVA + v_prov_comi_capital_AVA; 
                }
            
            v_prov_comi_total_AVE = v_prov_comi_rend_AVE + v_prov_comi_capital_AVE;   
            
            if(v_disponibleAVA<0)
             {
                 v_disponibleAVA=0;
             }
            if(v_disponibleAVE<0)
            {
                v_disponibleAVE=0;
            }
            v_disponibleTOblig = v_disponibleAVA + v_disponibleAVE;
         
            DecimalFormat v_format = new  DecimalFormat("¤###,###,###,###,###,###.##");            
            SaldosFinales[0] = String.valueOf(v_format.format(v_saldoAVA));
            SaldosFinales[1] = String.valueOf(v_format.format(v_saldoAVE));
            SaldosFinales[2] = String.valueOf(v_format.format(v_aportes_sin_cert_AVA));
            SaldosFinales[3] = String.valueOf(v_format.format(v_aportes_sin_cert_AVE));
            SaldosFinales[4] = String.valueOf(v_format.format(v_aportes_hist_pend_AVA));
            SaldosFinales[5] = String.valueOf(v_format.format(v_aportes_hist_pend_AVE));
            SaldosFinales[6] = String.valueOf(v_format.format(v_ret_pend_procesar_AVA));
            SaldosFinales[7] = String.valueOf(v_format.format(v_ret_pend_procesar_AVE));
            SaldosFinales[8] = String.valueOf(v_format.format(v_retencion_cont_AVA));
            SaldosFinales[9] = String.valueOf(v_format.format(v_retencion_cont_AVE));
            SaldosFinales[10] = String.valueOf(v_format.format(v_retencion_rend_AVA));
            SaldosFinales[11] = String.valueOf(v_format.format(v_retencion_rend_AVE));
            SaldosFinales[12] = String.valueOf(v_format.format(v_disponibleAVA));
            SaldosFinales[13] = String.valueOf(v_format.format(v_disponibleAVE));  
            SaldosFinales[14] = String.valueOf(v_format.format(v_prov_comi_rend_AVA));
            SaldosFinales[15] = String.valueOf(v_format.format(v_prov_comi_capital_AVA));
            SaldosFinales[16] = String.valueOf(v_format.format(v_prov_comi_rend_AVE));
            SaldosFinales[17] = String.valueOf(v_format.format(v_prov_comi_capital_AVE));
            SaldosFinales[18] = String.valueOf(v_format.format(v_prov_comi_total_AVA));
            SaldosFinales[19] = String.valueOf(v_format.format(v_prov_comi_total_AVE));
            SaldosFinales[20] = String.valueOf(v_format.format(prov_credito));
            SaldosFinales[21] = String.valueOf(v_format.format(v_valor_unidad_AVA));
            SaldosFinales[22] = String.valueOf(v_format.format(v_valor_unidad_AVE));                        
        }
        else
        {
            return null;
        } 
                                                        
        }catch( Exception e ) {
            System.out.println("ERROR SQL_CIERRE: "+e);            
        }    
        return (SaldosFinales);
    }
    
}/*@lineinfo:generated-code*/