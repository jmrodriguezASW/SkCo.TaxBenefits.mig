/*@lineinfo:filename=TBC_CALCULAR_UNIRETIRADAS*//*@lineinfo:user-code*//*@lineinfo:1^1*/
package TBPKT_CARGUE_APLICACION;

import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import java.sql.*;
//import funciones_as400.*;

/*Modificado porque no se utilizan
//import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;
//import com.ibm.as400.access.*;
*/
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOSSQLJ.*;
import TBPKT_UTILIDADES.TBPKT_FECHAS.*;
import  TBPKT_UTILIDADES.TBPKT_AS400_APORTES.*;

/**Clase que calcula el numero de unidades retiradas*/

public class TBC_CALCULAR_UNIRETIRADAS extends Object
{
 //cursor contratos
 /*@lineinfo:generated-code*//*@lineinfo:22^2*/ /*//  *************************************************************//*//  SQLJ iterator declaration:*//*//  *************************************************************/ public static class i_contrato extends sqlj.runtime.ref.ResultSetIterImpl implements sqlj.runtime.NamedIterator { public i_contrato(sqlj.runtime.profile.RTResultSet resultSet) throws java.sql.SQLException { super(resultSet); CIC_PRODUCTONdx = findColumn("CIC_PRODUCTO"); CIC_CONTRATONdx = findColumn("CIC_CONTRATO"); m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet(); } private oracle.jdbc.OracleResultSet m_rs; public String CIC_PRODUCTO() throws java.sql.SQLException { return (String)m_rs.getString(CIC_PRODUCTONdx); } private int CIC_PRODUCTONdx; public String CIC_CONTRATO() throws java.sql.SQLException { return (String)m_rs.getString(CIC_CONTRATONdx); } private int CIC_CONTRATONdx; } /*//  *************************************************************/ 
                                                            /*@lineinfo:user-code*//*@lineinfo:23^60*/


 i_contrato v_contrato ;
 public int TBP_CALCULAR_UNIDADES_RETIRADAS(String v_fecha,int v_log)
 {
  int v_contadornumuni = 0;
  try
  {
   /**Instancias de clase*/
   //TBCL_FUNCIONES_AS400_APORTES i_as400 = new  TBCL_FUNCIONES_AS400_APORTES();
   //TBCL_FuncionesAs400 i_as400_2 = new TBCL_FuncionesAs400();
   TBCL_ConexionSqlj    i_conexion = new TBCL_ConexionSqlj();/**Instancia de la clase TBCL_ConexionSqlj*/
   //Conexion base de datos
   i_conexion.TBCL_ConexionBaseDatos();
   String vconviejo = "X";
   String vconnuevo = "X";
   String v_producto  = "";
   int contador = 0;
   //consulta as400 para lea fecha de cargue
   String   v_csaldoas400 = "";
   long v_csaldo     = 0;
   Long  v_ctmp   = null;
   double v_csaldo2     = 0;
   double v_calculo = 0;

   //referencias multi
   String v_sistema = "";
   String v_usumfund= "";
   String v_passmfund="";
   String v_libreria ="";
   int v_resmulti     = 0;

   int v_indicador = 0;
   int v_indicador2 = 0;
   String v_menretiro = "";
   String v_menaporte = "";
   //Consultar referencias del sistema Multifund
   //#sql v_resmulti ={values(TB_FREFERENCIAS_MULTI(:INOUT v_sistema,:INOUT v_usumfund,:INOUT v_passmfund, :INOUT v_libreria))};
   //AS400 sadc2 = new AS400(""+v_sistema+"");
   //sadc2.setUserId(""+v_usumfund+"");
   //sadc2.setPassword(""+v_passmfund+"");
   //Consultar  contratos cargados para la fecha
   /*@lineinfo:generated-code*//*@lineinfo:66^4*/ /*//  *************************************************************//*//  #sql v_contrato = { SELECT DISTINCT(CIC_CONTRATO)*//*//                               ,CIC_PRODUCTO*//*//                           FROM TBCI_CONTRATOS*//*//                          WHERE CIC_FECHA = TO_DATE(:v_fecha,'RRRR-MM-DD')*//*//*//*//                        };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "SELECT DISTINCT(CIC_CONTRATO)\n                             ,CIC_PRODUCTO\n                         FROM TBCI_CONTRATOS\n                        WHERE CIC_FECHA = TO_DATE( :1  ,'RRRR-MM-DD')"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_CARGUE_APLICACION.TBC_CALCULAR_UNIRETIRADAS",theSqlTS); /*// set IN parameters*/ __sJT_st.setString(1,v_fecha); /*// execute query*/ v_contrato = new TBPKT_CARGUE_APLICACION.TBC_CALCULAR_UNIRETIRADAS.i_contrato(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"0TBPKT_CARGUE_APLICACION.TBC_CALCULAR_UNIRETIRADAS",null)); } finally { __sJT_ec.oracleCloseQuery(); } } /*//  *************************************************************/ 




                      /*@lineinfo:user-code*//*@lineinfo:71^22*/

   while(v_contrato.next())
   {
     contador++;
     System.out.println("contrato "+contador+" numero "+ v_contrato.CIC_CONTRATO());
     vconnuevo = v_contrato.CIC_CONTRATO();
     v_producto = v_contrato.CIC_PRODUCTO();
     //Llamado a función que actualiza unidades retiradas
     /*@lineinfo:generated-code*//*@lineinfo:80^6*/ /*//  *************************************************************//*//  #sql v_indicador = { values(TB_FCALNUMUNIRET( :v_producto*//*//                                                     ,:vconnuevo*//*//                                                     ,:v_menretiro*//*//                                                     ,:v_log )) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TB_FCALNUMUNIRET(  :2  \n                                                   , :3  \n                                                   , :4  \n                                                   , :5   ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_CARGUE_APLICACION.TBC_CALCULAR_UNIRETIRADAS",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.INTEGER); } /*// set IN parameters*/ __sJT_st.setString(2,v_producto); __sJT_st.setString(3,vconnuevo); __sJT_st.setString(4,v_menretiro); __sJT_st.setInt(5,v_log); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_indicador = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_menretiro = (String)__sJT_st.getString(4); v_log = __sJT_st.getInt(5); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 


                                                                    /*@lineinfo:user-code*//*@lineinfo:83^68*/

     if(v_indicador != 0)
     {//si hay error se inserta en el log
      v_contadornumuni++;
      String vmen2 = "Error en el proceso de actualizar valor de unidad y numero de unidades retiradas, contrato "+vconnuevo+". Función TB_FCALNUMUNIRET";
      /*#sql {call TB_PLOG( 'CNUTAX'
                          ,:vmen2
                          ,:v_menretiro
                          ,0
                          ,:v_log)};
       v_log++;*/
     }

     //Consulta del saldo del contrato con fecha DE CARGUE
     /*v_csaldoas400 = i_as400_2.TBPL_SaldosPorContrato2(vconnuevo,v_fecha,"E",sadc2,v_libreria);
     if(!v_csaldoas400.trim().equals(""))
     {
      if(!v_csaldoas400.substring(0,5).equals("Error"))//error saldos
      {
       v_csaldo      = i_as400.TBFL_Saldo_Entero(v_csaldoas400);
       v_ctmp   = new Long(v_csaldo);
       v_csaldo2     = v_ctmp.doubleValue();
       v_csaldo2/=100.00;
       //Llamado a función que procesa información de aportes*/
       /*@lineinfo:generated-code*//*@lineinfo:108^8*/ /*//  *************************************************************//*//  #sql v_indicador2 = { values(TB_FCALNUMAPO( :v_producto*//*//                                                  ,:vconnuevo*//*//                                                  ,:v_fecha*//*//                                                  ,:v_csaldo2*//*//                                                  ,:v_menaporte*//*//                                                  ,:v_log*//*//                                                  ,:v_contadornumuni)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TB_FCALNUMAPO(  :2  \n                                                , :3  \n                                                , :4  \n                                                , :5  \n                                                , :6  \n                                                , :7  \n                                                , :8  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_CARGUE_APLICACION.TBC_CALCULAR_UNIRETIRADAS",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.INTEGER); } /*// set IN parameters*/ __sJT_st.setString(2,v_producto); __sJT_st.setString(3,vconnuevo); __sJT_st.setString(4,v_fecha); __sJT_st.setDouble(5,v_csaldo2); __sJT_st.setString(6,v_menaporte); __sJT_st.setInt(7,v_log); __sJT_st.setInt(8,v_contadornumuni); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_indicador2 = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_menaporte = (String)__sJT_st.getString(6); v_log = __sJT_st.getInt(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_contadornumuni = __sJT_st.getInt(8); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 





                                                                           /*@lineinfo:user-code*//*@lineinfo:114^75*/

      if(v_indicador2 != 0)
       {
        v_contadornumuni++;
        String vmen3 ="Error en el proceso de actualizar aportes, contrato "+vconnuevo+". Función TB_FCALNUMAPO";
        /*#sql {call TB_PLOG( 'CNUTAX'
                           ,:vmen3
                           ,:v_menaporte
                           ,0
                           ,:v_log)};
        v_log++;*/
       }
      /* }
      else
      {
       v_contadornumuni++;
       String v_men4 = "Error en el proceso de consultar saldo para el contrato "+vconnuevo+". Función TB_FCALNUMAPO";
       #sql {call TB_PLOG( 'CNUTAX'
                          ,:v_men4
                          ,:v_csaldoas400
                          ,0
                          ,:v_log)};
      }
     }
     else
     {
      v_contadornumuni++;
      String v_men2 = "Error en el proceso de consultar saldo para el contrato "+vconnuevo+", no devuelve datos. Función TB_FCALNUMAPO";
      #sql {call TB_PLOG( 'CNUTAX'
                         ,:v_men2
                         ,:v_csaldoas400
                         ,0
                         ,:v_log)};
     }*/
    }//contrato

    v_contrato.close();
    /*@lineinfo:generated-code*//*@lineinfo:152^5*/ /*//  *************************************************************//*//  #sql { commit };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:152^17*/
    //#sql {ROLLBACK};
    //sadc2.disconnectAllServices();
    i_conexion.TBCL_DesconectarBaseDatos();
    java.util.Date g = new java.util.Date();
    System.out.println("Hora final: "+g);

    return  v_contadornumuni;
   }
   catch(Exception ex)
   {
   try
   {
    System.out.println("error "+ex);
    String v_men ="Mensaje de error "+ex;
    v_contadornumuni++;
    /*@lineinfo:generated-code*//*@lineinfo:168^5*/ /*//  *************************************************************//*//  #sql { call TB_PLOG( 'CNUTAX'*//*//                         ,'Error en el proceso de calcular el numero de unidades retiradas'*//*//                         ,:v_men*//*//                         ,0*//*//                         ,:v_log) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TB_PLOG( 'CNUTAX'\n                       ,'Error en el proceso de calcular el numero de unidades retiradas'\n                       , :1  \n                       ,0\n                       , :2  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"3TBPKT_CARGUE_APLICACION.TBC_CALCULAR_UNIRETIRADAS",theSqlTS); /*// set IN parameters*/ __sJT_st.setString(1,v_men); __sJT_st.setInt(2,v_log); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 



                                /*@lineinfo:user-code*//*@lineinfo:172^32*/
   }
   catch(Exception ex2)
   {
   }
   return  v_contadornumuni;
  }
 }
}