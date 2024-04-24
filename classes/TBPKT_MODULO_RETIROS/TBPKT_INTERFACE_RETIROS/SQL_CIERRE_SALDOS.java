/*@lineinfo:filename=SQL_CIERRE_SALDOS*//*@lineinfo:user-code*//*@lineinfo:1^1*/// Copyright (c) 2000 Sk
package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import java.sql.*;
import java.io.*;
import java.sql.Date;
import java.lang.String.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.*;
import com.ibm.as400.access.*;

public class SQL_CIERRE_SALDOS extends Object {

 /*
  Metodo que realiza el cierre mensual de saldos de contratos
  insertando en la tabla TBSALDOS
 */

 //Interector o cursor de la tabla TBCONTRATOS con los canpos seleccionados
 /*@lineinfo:generated-code*//*@lineinfo:22^2*/ /*//  *************************************************************//*//  SQLJ iterator declaration:*//*//  *************************************************************/ public static class vint_contrato extends sqlj.runtime.ref.ResultSetIterImpl implements sqlj.runtime.NamedIterator { public vint_contrato(sqlj.runtime.profile.RTResultSet resultSet) throws java.sql.SQLException { super(resultSet); con_pro_codigoNdx = findColumn("con_pro_codigo"); con_numeroNdx = findColumn("con_numero"); m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet(); } private oracle.jdbc.OracleResultSet m_rs; public String con_pro_codigo() throws java.sql.SQLException { return (String)m_rs.getString(con_pro_codigoNdx); } private int con_pro_codigoNdx; public String con_numero() throws java.sql.SQLException { return (String)m_rs.getString(con_numeroNdx); } private int con_numeroNdx; } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:22^85*/

 public static int TB_CIERRE_SALDOS_CONTRATO(String v_fecha)
 {
   Date              v_max_fecha;
   vint_contrato     vreg_contrato         = null;
   int               v_cod_err             = 0;
   String            v_cadena_t;
   String            v_contrato;
   String            v_producto;
   String            v_men_err = " ";
   String            v_log_mensaje = " ";
   String            v_log_datos = " ";
   String            v_cierre = "N";
   double            v_saldo_unidades;
   Double            v_saldo_cuenta_conting_d;
   Double            v_saldo_unidades_d;
   double            v_saldo_cuenta_conting = 0;
   double            v_valor_unidad;
   Double            v_saldo_contrato_d;
   double            v_saldo_contrato = 0;
   Double            v_saldo_unidad_max_fec;
   Double            v_cuenta_conting_max_fec;
   String[]          v_valusu;
   TBCL_Validacion   i_valusu = new TBCL_Validacion();
   Date              v_fecha_d;
   int               sw = 0;
   int               v_sw = 1;
   Integer           v_cta_contingente = null;
   String            v_existe;
   Double           v_saldo_disponible;
   String            v_sistema         = " ";
   String            v_usuario         = " ";
   String            v_password        = " ";
   String            v_libreria        = " ";

   try{
     //Conexion con la base de datos
     v_valusu = new String[3];
     v_valusu = i_valusu.TBFL_ValidarUsuario();
     DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
     DefaultContext.setDefaultContext(new DefaultContext( v_valusu[0],v_valusu[1],v_valusu[2],false));
     //Validar si es fin de mes para realizar el proceso de cierre
     //Si no es fin de mes realizar el proceso solo si para el mes anterior no se
     //realizo el cierre de saldos
     System.out.println("Fecha en  cierre saldo "+v_fecha);
     /*@lineinfo:generated-code*//*@lineinfo:68^6*/ /*//  *************************************************************//*//  #sql v_cierre = { values ( TBFBD_VALIDAR_CIERRE_SALDO (:v_fecha, :v_cod_err,:v_men_err)) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TBFBD_VALIDAR_CIERRE_SALDO ( :2  ,  :3  , :4  ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE_SALDOS",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_fecha); __sJT_st.setInt(3,v_cod_err); __sJT_st.setString(4,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_cierre = (String)__sJT_st.getString(1); v_fecha = (String)__sJT_st.getString(2); v_cod_err = __sJT_st.getInt(3); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(4); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:68^112*/
     System.out.println("Paso validar cierre saldo "+v_cierre+" Fecha nueva"+v_fecha+" v_cod_err"+v_cod_err);
     if (v_cod_err == 0){
       if (v_cierre.charAt(0) == 'S'){
         //Conexion al AS400
         //Buscar elementos de conexion al As400 en referncias
         /*@lineinfo:generated-code*//*@lineinfo:74^10*/ /*//  *************************************************************//*//  #sql v_sw = { values(TB_FREFERENCIAS_MULTI(:v_sistema,*//*//                                                      :v_usuario,*//*//                                                      :v_password,*//*//                                                      :v_libreria )) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN :1 := TB_FREFERENCIAS_MULTI( :2  ,\n                                                     :3  ,\n                                                     :4  ,\n                                                     :5   ) \n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE_SALDOS",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR); __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(2,v_sistema); __sJT_st.setString(3,v_usuario); __sJT_st.setString(4,v_password); __sJT_st.setString(5,v_libreria); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_sw = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_sistema = (String)__sJT_st.getString(2); v_usuario = (String)__sJT_st.getString(3); v_password = (String)__sJT_st.getString(4); v_libreria = (String)__sJT_st.getString(5); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 


                                                                         /*@lineinfo:user-code*//*@lineinfo:77^73*/
         if (v_sw == 0){
           //Conectarse al AS400
           AS400 as400 = new AS400(""+v_sistema+"");
           try{
            //se conecta con usuario y password
            as400.setUserId(""+v_usuario+"");
            as400.setPassword(""+v_password+"");
            System.out.println("Se conectó al As400 ");
           }//No Hubo error al conectarse al AS400
           catch (Exception e){
             System.out.println("Error al conectarse al As400 "+e);
             v_cod_err = -1;
           }//Hubo error al conectarse al AS400
           if (v_cod_err == 0){
             System.out.println("Se conectó al As400 ");
             //Para cada contrato vigente o existente los saldos para el producto-contrato en caso
             //de que existan y la cuenta contingente sea Null procesar los saldos y actualizar TBSALDOS
             //En caso de no existir los saldos procesar e insertar en TBSALDOS
             //Si existen los saldos pero la cuenta contigente es distinta de Null significa
             //que un cierre mensual anterior proceso este producto-contrato y no se
             //debe procesar. Calcular saldo_contrato = AS400, saldo_unidades y valor_unidad
             /*@lineinfo:generated-code*//*@lineinfo:99^14*/ /*//  *************************************************************//*//  #sql vreg_contrato = { SELECT con_pro_codigo, con_numero*//*//                                       FROM tbcontratos, tbsaldos*//*//                                       WHERE sal_con_pro_codigo (+) = con_pro_codigo*//*//                                         AND sal_con_numero     (+) = con_numero*//*//                                         AND sal_fecha          (+) = to_date(:v_fecha,'RRRR-MM-DD')*//*//                                         AND sal_saldo_cuenta_contingente  IS NULL*//*//                                         AND ( con_fecha_cancelacion       IS NULL OR*//*//                                              con_fecha_cancelacion > to_date(:v_fecha,'RRRR-MM-DD')*//*//                                              )*//*//                                           };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "SELECT con_pro_codigo, con_numero\n                                     FROM tbcontratos, tbsaldos\n                                     WHERE sal_con_pro_codigo (+) = con_pro_codigo\n                                       AND sal_con_numero     (+) = con_numero\n                                       AND sal_fecha          (+) = to_date( :1  ,'RRRR-MM-DD')\n                                       AND sal_saldo_cuenta_contingente  IS NULL\n                                       AND ( con_fecha_cancelacion       IS NULL OR\n                                            con_fecha_cancelacion > to_date( :2  ,'RRRR-MM-DD')\n                                            )"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE_SALDOS",theSqlTS); /*// set IN parameters*/ __sJT_st.setString(1,v_fecha); __sJT_st.setString(2,v_fecha); /*// execute query*/ vreg_contrato = new TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE_SALDOS.vint_contrato(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"2TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE_SALDOS",null)); } finally { __sJT_ec.oracleCloseQuery(); } } /*//  *************************************************************/ 








                                         /*@lineinfo:user-code*//*@lineinfo:108^41*/
             while (vreg_contrato.next() ){
               //Seguir proceso de cierre aunque ocurra un error al calcular los
               //saldos para cualquier contrato. Si ocurre un error insertar en el LOG
               v_cod_err = 0;
               v_men_err = " ";
               sw = 0;
               v_contrato = vreg_contrato.con_numero();
               v_producto = vreg_contrato.con_pro_codigo();
               System.out.println("Contrato número "+v_contrato);
               //Llamado a funcion que devuelve el saldo de contrato
               //OJO EL SALDO DEL CONTRATO DEVULVE -1 SI HAY ERROR
               try {
                 TBCL_AS400 ias400 = new TBCL_AS400();
                 v_saldo_contrato  = ias400.TBF_SALDO_CONTRATO_P(v_contrato, v_fecha, as400, v_libreria);
                 if (v_saldo_contrato == -1){
                   v_cod_err = 0; // para que siga con el siguiente contrato.
                   v_men_err = "Error en la funcion del AS400";
                   System.out.println("Error en la funcion del AS400. Contrato número "+v_contrato);
                 }
               }catch(Exception e){v_cod_err = 1;}
               //Si hubo error al calcular el saldo de contrato no realizar proceso
               //para el contrato
               if (v_cod_err == 0){
                 if (v_saldo_contrato > 0){
                   //Seleccionar los saldos de unidades y cuenta contingente de los saldos
                   //mas recientes en TBSALDOS o del aporte vigente mas antiguo en caso
                   //de no existir saldos anteriores en TBSALDOS
                   /*@lineinfo:generated-code*//*@lineinfo:136^20*/ /*//  *************************************************************//*//  #sql { call TBPBD_ULTIMOS_SALDOS(:v_fecha,*//*//                                                     :v_contrato,*//*//                                                     :v_producto,*//*//                                                     :v_max_fecha,*//*//                                                     :v_saldo_unidad_max_fec,*//*//                                                     :v_cuenta_conting_max_fec,*//*//                                                     :v_cod_err,*//*//                                                     :v_men_err) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_ULTIMOS_SALDOS( :1  ,\n                                                    :2  ,\n                                                    :3  ,\n                                                    :4  ,\n                                                    :5  ,\n                                                    :6  ,\n                                                    :7  ,\n                                                    :8  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE_SALDOS",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.DATE); __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.DOUBLE); __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.DOUBLE); __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(1,v_fecha); __sJT_st.setString(2,v_contrato); __sJT_st.setString(3,v_producto); __sJT_st.setInt(7,v_cod_err); __sJT_st.setString(8,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_max_fecha = (java.sql.Date)__sJT_st.getDate(4); v_saldo_unidad_max_fec = new Double(__sJT_st.getDouble(5)); if (__sJT_st.wasNull()) v_saldo_unidad_max_fec = null; v_cuenta_conting_max_fec = new Double(__sJT_st.getDouble(6)); if (__sJT_st.wasNull()) v_cuenta_conting_max_fec = null; v_cod_err = __sJT_st.getInt(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(8); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 






                                                                     /*@lineinfo:user-code*//*@lineinfo:143^69*/
                   if (v_cod_err == 0){
                     v_saldo_unidades = 0;
                     //Calcular el saldo_unidades y el saldo de cuenta contingente
                     /*@lineinfo:generated-code*//*@lineinfo:147^22*/ /*//  *************************************************************//*//  #sql { call TBPBD_SALUNI_SALCTACONT(:v_fecha,*//*//                                                          :v_contrato,*//*//                                                          :v_producto,*//*//                                                          :v_max_fecha,*//*//                                                          :v_cuenta_conting_max_fec,*//*//                                                          :v_saldo_unidad_max_fec,*//*//                                                          :v_saldo_cuenta_conting,*//*//                                                          :v_saldo_unidades,*//*//                                                          :v_cod_err,*//*//                                                          :v_men_err) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_SALUNI_SALCTACONT( :1  ,\n                                                         :2  ,\n                                                         :3  ,\n                                                         :4  ,\n                                                         :5  ,\n                                                         :6  ,\n                                                         :7  ,\n                                                         :8  ,\n                                                         :9  ,\n                                                         :10  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE_SALDOS",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.DOUBLE); __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.DOUBLE); __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(1,v_fecha); __sJT_st.setString(2,v_contrato); __sJT_st.setString(3,v_producto); __sJT_st.setDate(4,v_max_fecha); if (v_cuenta_conting_max_fec == null) __sJT_st.setNull(5,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(5,v_cuenta_conting_max_fec.doubleValue()); if (v_saldo_unidad_max_fec == null) __sJT_st.setNull(6,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(6,v_saldo_unidad_max_fec.doubleValue()); __sJT_st.setInt(9,v_cod_err); __sJT_st.setString(10,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_saldo_cuenta_conting = __sJT_st.getDouble(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_saldo_unidades = __sJT_st.getDouble(8); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_cod_err = __sJT_st.getInt(9); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(10); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 








                                                                          /*@lineinfo:user-code*//*@lineinfo:156^74*/
                     if (v_cod_err == 0 ){
                       if ((v_saldo_unidades > 0) && (v_saldo_cuenta_conting >= 0)) {
                         //calculo el valor de la unidad
                         v_valor_unidad     = v_saldo_contrato / v_saldo_unidades;
                         //Calcular el saldo disponible del contrato
                         /*@lineinfo:generated-code*//*@lineinfo:162^26*/ /*//  *************************************************************//*//  #sql { call TBPBD_CONTRATO_DISPONIBLE(:v_fecha,*//*//                                                                :v_contrato,*//*//                                                                :v_producto,*//*//                                                                :v_saldo_contrato,*//*//                                                                :v_valor_unidad,*//*//                                                                :v_saldo_disponible,*//*//                                                                :v_cod_err,*//*//                                                                :v_men_err) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_CONTRATO_DISPONIBLE( :1  ,\n                                                               :2  ,\n                                                               :3  ,\n                                                               :4  ,\n                                                               :5  ,\n                                                               :6  ,\n                                                               :7  ,\n                                                               :8  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE_SALDOS",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.DOUBLE); __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(1,v_fecha); __sJT_st.setString(2,v_contrato); __sJT_st.setString(3,v_producto); __sJT_st.setDouble(4,v_saldo_contrato); __sJT_st.setDouble(5,v_valor_unidad); __sJT_st.setInt(7,v_cod_err); __sJT_st.setString(8,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_saldo_disponible = new Double(__sJT_st.getDouble(6)); if (__sJT_st.wasNull()) v_saldo_disponible = null; v_cod_err = __sJT_st.getInt(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(8); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 






                                                                                /*@lineinfo:user-code*//*@lineinfo:169^80*/
                         if (v_cod_err == 0 ){
                           //Llama al procedimiento que inserta en la tabla TBSALDOS
                           /*@lineinfo:generated-code*//*@lineinfo:172^28*/ /*//  *************************************************************//*//  #sql { call TBPBD_INS_TBSALDOS(:v_producto,*//*//                                                           :v_contrato,*//*//                                                           :v_fecha,*//*//                                                           :v_saldo_contrato,*//*//                                                           :v_saldo_disponible,*//*//                                                           :v_saldo_cuenta_conting,*//*//                                                           :v_saldo_unidades,*//*//                                                           :v_valor_unidad,*//*//                                                           :v_cod_err,*//*//                                                           :v_men_err) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_INS_TBSALDOS( :1  ,\n                                                          :2  ,\n                                                          :3  ,\n                                                          :4  ,\n                                                          :5  ,\n                                                          :6  ,\n                                                          :7  ,\n                                                          :8  ,\n                                                          :9  ,\n                                                          :10  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE_SALDOS",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(1,v_producto); __sJT_st.setString(2,v_contrato); __sJT_st.setString(3,v_fecha); __sJT_st.setDouble(4,v_saldo_contrato); if (v_saldo_disponible == null) __sJT_st.setNull(5,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(5,v_saldo_disponible.doubleValue()); __sJT_st.setDouble(6,v_saldo_cuenta_conting); __sJT_st.setDouble(7,v_saldo_unidades); __sJT_st.setDouble(8,v_valor_unidad); __sJT_st.setInt(9,v_cod_err); __sJT_st.setString(10,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_cod_err = __sJT_st.getInt(9); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(10); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 








                                                                           /*@lineinfo:user-code*//*@lineinfo:181^75*/
                           //Si este saldo existe en la tabla TBSALDOS se actualiza la tabla
                           if (v_cod_err == -0001){
                             /*@lineinfo:generated-code*//*@lineinfo:184^30*/ /*//  *************************************************************//*//  #sql { call TBPBD_UPDATE_SALDOS(to_date(:v_fecha,'RRRR-MM-DD'),*//*//                                                              :v_producto,*//*//                                                              :v_contrato,*//*//                                                              :v_saldo_contrato,*//*//                                                              :v_saldo_disponible,*//*//                                                              :v_saldo_cuenta_conting,*//*//                                                              :v_saldo_unidades,*//*//                                                              :v_valor_unidad,*//*//                                                              :v_cod_err,*//*//                                                              :v_men_err) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OracleCallableStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_UPDATE_SALDOS(to_date( :1  ,'RRRR-MM-DD'),\n                                                             :2  ,\n                                                             :3  ,\n                                                             :4  ,\n                                                             :5  ,\n                                                             :6  ,\n                                                             :7  ,\n                                                             :8  ,\n                                                             :9  ,\n                                                             :10  )\n; END;"; __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE_SALDOS",theSqlTS); if (__sJT_ec.isNew()) { __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.INTEGER); __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR); } /*// set IN parameters*/ __sJT_st.setString(1,v_fecha); __sJT_st.setString(2,v_producto); __sJT_st.setString(3,v_contrato); __sJT_st.setDouble(4,v_saldo_contrato); if (v_saldo_disponible == null) __sJT_st.setNull(5,oracle.jdbc.OracleTypes.DOUBLE); else __sJT_st.setDouble(5,v_saldo_disponible.doubleValue()); __sJT_st.setDouble(6,v_saldo_cuenta_conting); __sJT_st.setDouble(7,v_saldo_unidades); __sJT_st.setDouble(8,v_valor_unidad); __sJT_st.setInt(9,v_cod_err); __sJT_st.setString(10,v_men_err); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); /*// retrieve OUT parameters*/ v_cod_err = __sJT_st.getInt(9); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException(); v_men_err = (String)__sJT_st.getString(10); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 








                                                                              /*@lineinfo:user-code*//*@lineinfo:193^78*/
                             if (v_cod_err != 0 ){
                               sw = 1;
                               v_log_mensaje = v_men_err;
                               v_log_datos   = "Fecha: "+v_fecha+" Contrato: "+v_contrato;
                               /*@lineinfo:generated-code*//*@lineinfo:198^32*/ /*//  *************************************************************//*//  #sql { ROLLBACK  };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:198^48*///De la actualizacion
                             }//Hubo error al actualizar en TBSALDOS
                             else{
                               /*@lineinfo:generated-code*//*@lineinfo:201^32*/ /*//  *************************************************************//*//  #sql { COMMIT  };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:201^46*///De la actualizacion
                             }//No hubo error al actualizar
                           }//Existia saldos en TBSALDOS actualizar
                           else{
                             if (v_cod_err != 0 ){
                               sw = 1;
                               v_log_mensaje = v_men_err;
                               v_log_datos   = "Fecha: "+v_fecha+" Contrato: "+v_contrato;
                               System.out.println("Error al insertar en tbsaldos "+v_men_err);
                               System.out.println("Contrato "+v_contrato);
                               /*@lineinfo:generated-code*//*@lineinfo:211^32*/ /*//  *************************************************************//*//  #sql { ROLLBACK  };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:211^48*///De la insercion
                             }//Hubo error al insertar en TBSALDOS
                             else{
                               /*@lineinfo:generated-code*//*@lineinfo:214^32*/ /*//  *************************************************************//*//  #sql { COMMIT  };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:214^46*///De la insercion
                             }//No hubo error al insertar
                           }//No existian saldos en TBSALDOS insertó o error
                         }//No hubo error al calcular el saldo disponible de contrato
                         else{
                           sw = 1;
                           v_log_mensaje = v_men_err;
                           v_log_datos   = "Fecha: "+v_fecha+" Contrato: "+v_contrato;
                           System.out.println("Error al calcular saldo de contrato disponible "+v_men_err);
                           System.out.println("Contrato "+v_contrato);
                         }//Error al calcular saldo disponible del contrato
                       }//Saldos son positivos
                       else{
                         sw = 1;
                         v_log_mensaje = "Saldo de cuenta contingente "+v_saldo_cuenta_conting+
                                         "o Saldo de Unidades "+v_saldo_unidades+
                                         "es negativo o igual a cero";
                         v_log_datos   = "Fecha: "+v_fecha+" Contrato: "+v_contrato;
                         System.out.println("Saldos de unidades y cuenta contigente negativos o igual a cero "+v_saldo_unidades+" Cuenta Cont"+v_saldo_cuenta_conting);
                         System.out.println("Contrato "+v_contrato);
                       }//Saldos son negativos o cero
                     }//No hubo error al calcular los saldos
                     else{
                       sw = 1;
                       v_log_mensaje = v_men_err;
                       v_log_datos   = "Fecha: "+v_fecha+" Contrato: "+v_contrato;
                       System.out.println("Error al calcular los saldos de unidades y cuenta contigente "+v_men_err);
                       System.out.println("Contrato "+v_contrato);
                     }//Hubo error al calcular los saldos
                   }//No hubo error al selecionar los ultimos saldos en TBSALDOS
                   else{
                     sw = 1;
                     v_log_mensaje = v_men_err;
                     v_log_datos   = "Fecha: "+v_fecha+" Contrato: "+v_contrato;
                     System.out.println("Error al selecionar los ultimos saldos en TBSALDOS"+v_men_err);
                     System.out.println("Contrato "+v_contrato);
                   }//Hubo error al selecionar los ultimos saldos en TBSALDOS
                 }//Saldo de contrato es positivo
                 else{
                   sw = 1;
                   v_log_mensaje = "Saldo del contrato es negativo o igual a cero";
                   v_log_datos   = "Fecha: "+v_fecha+" Contrato: "+v_contrato;
                   System.out.println("Saldo del contrato es negativo o cero"+v_saldo_contrato);
                   System.out.println("Contrato "+v_contrato);
                 }//Saldo de contrato es negativo o cero
               }//No hubo error al calcular el saldo de contrato en el AS400
               else{
                 sw = 1;
                 v_log_mensaje = "Error al calcular el saldo del contrato en el AS400";
                 v_log_datos   = "Fecha: "+v_fecha+" Contrato: "+v_contrato;
                 System.out.println("Saldo del contrato as400 error"+v_men_err);
                 System.out.println("Contrato "+v_contrato);
               }//Hubo error al calcular el saldo de contrato en el AS400
               if (sw == 1){
                 //Procedimiento que inserta en la tabla de Log
                 /*@lineinfo:generated-code*//*@lineinfo:269^18*/ /*//  *************************************************************//*//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG', to_date(:v_fecha,'RRRR-MM-DD'), 'CM', :v_log_mensaje,*//*//                                                          :v_log_datos, :v_producto, 0) };*//*//  *************************************************************/ { /*// declare temps*/ oracle.jdbc.OraclePreparedStatement __sJT_st = null; sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX(); sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext()); try { String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG', to_date( :1  ,'RRRR-MM-DD'), 'CM',  :2  ,\n                                                         :3  ,  :4  , 0)\n; END;"; __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"8TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE_SALDOS",theSqlTS); /*// set IN parameters*/ __sJT_st.setString(1,v_fecha); __sJT_st.setString(2,v_log_mensaje); __sJT_st.setString(3,v_log_datos); __sJT_st.setString(4,v_producto); /*// execute statement*/ __sJT_ec.oracleExecuteUpdate(); } finally { __sJT_ec.oracleClose(); } } /*//  *************************************************************/ 
                                                                                      /*@lineinfo:user-code*//*@lineinfo:270^86*/
                 /*@lineinfo:generated-code*//*@lineinfo:271^18*/ /*//  *************************************************************//*//  #sql { COMMIT };*//*//  *************************************************************/ ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext()); /*//  *************************************************************/ /*@lineinfo:user-code*//*@lineinfo:271^30*/
               }//Insertar en el log
             }//While
             //Desconectarse del AS400
             as400.disconnectService(AS400.COMMAND);
           }//No Hubo error al conectarse al AS400
           System.out.println("Cierre Terminado.");
         }//No hubo error al buscar las referencias de conexion a MF
       }//Se puede realizar el cierre
     }//No hubo error al validar el proceso de cierre
      return v_cod_err;
  }
   catch( Exception e ) {
     System.out.println("ERROR SQL_CIERRE: "+e);
     return -1;
  }
 }//Metodo TB_CIERRE_SALDOS_CONTRATO
}