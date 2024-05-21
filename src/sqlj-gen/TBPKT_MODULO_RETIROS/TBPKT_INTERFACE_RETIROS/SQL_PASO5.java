/*@lineinfo:filename=SQL_PASO5*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import java.sql.*;
import java.io.*;

//import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.*;
import com.ibm.as400.access.*;

/**
 * clase: SQL_PASO5
 * Procedimientos:
 *    1. TBP_PASO5: Controla el paso 5 de las Interfaces de Retiros,
 *       el cual debe generar todas las solicitudes de retiros
 *       correspondientes al proceso del dia con el valor de unidad
 *       correspondiente
 * @author: Ing. Dolly Marcela Padilla Baños
 */
public class SQL_PASO5 extends Object {

  //Interector o cursor de la tabla TBRETIROS con los campos seleccionados
  /*@lineinfo:generated-code*//*@lineinfo:25^3*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class vint_retiro
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public vint_retiro(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    ret_con_pro_codigoNdx = findColumn("ret_con_pro_codigo");
    ret_con_numeroNdx = findColumn("ret_con_numero");
    fecha_contratoNdx = findColumn("fecha_contrato");
    fecha_unidadNdx = findColumn("fecha_unidad");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String ret_con_pro_codigo()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(ret_con_pro_codigoNdx);
  }
  private int ret_con_pro_codigoNdx;
  public String ret_con_numero()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(ret_con_numeroNdx);
  }
  private int ret_con_numeroNdx;
  public String fecha_contrato()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(fecha_contratoNdx);
  }
  private int fecha_contratoNdx;
  public String fecha_unidad()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(fecha_unidadNdx);
  }
  private int fecha_unidadNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:25^136*/

  public static void TBP_PASO5(int v_veces, int v_proceso) {
    int               v_cod_err         = 0;
    String            v_men_err         = " ";
    String            v_sistema         = " ";
    String            v_usuario         = " ";
    String            v_password        = " ";
    String            v_libreria        = " ";
    String            fe;
    String            v_ejecutar         = "N";
    String            v_menvu           = "";
    int               v_codvu            = 0;
    Integer           v_fecha_control   = null;
    int               v_cont            = 1;
    int               v_consecutivo_log  = 0;
    long              y;
    String            v_valido          = "N";
    String            v_proceso_exitoso = "N";
    String            v_fecha_c = " ";
    String            v_fecha_contrato;
    String            v_fecha_unidad;
    vint_retiro       vreg_retiro       = null;
    double[]          v_vector;
    String[]          v_valusu;

    //TBCL_Validacion   i_valusu = new TBCL_Validacion(); //Quitar MOS

    TBCL_AS400        ias400;
    AS400             as400;
    int               v_sw = 1;
    int               v_existe_paso = 0;
    String v_unidad_total = "";
    String v_estado_paso  = "N";
    String            v_log_datos = " ";
    String v_ruta = "c:\\TaxBenefits\\Taxb\\Pasos_logs\\paso5.log";

    try{

/* Modificacion:

Se deja en comentarios la seccion de conexion a la base de datos, ya que esta clase
se envia al esquema tbenefit en la base de datos.

Conexion con la base de datos Oracle*
    v_valusu = new String[3];
    v_valusu = i_valusu.TBFL_ValidarUsuario();
    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    Connection t_tax =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
      DefaultContext ctx3 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
      DefaultContext.setDefaultContext(ctx3);
      t_tax.setAutoCommit(false);
    
      
//Quitar MOS
//Final de modificacion */
      
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(v_ruta, true)));
      DefaultContext.setDefaultContext(null);

      //*********************************************************
      //El paso 5 se debe ejecutar las veces que sean necesarios
      //debido a que los valores brutos del paso 4 pueden cambiar
      //en tal caso se debe volver a correr el paso 5
      //**********************************************************
      /*@lineinfo:generated-code*//*@lineinfo:90^7*/

//  ************************************************************
//  #sql { call TBPBD_CRONE_LOG('5',
//                                   :v_ejecutar,
//                                   :v_consecutivo_log,
//                                   :v_cod_err,
//                                   :v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_CRONE_LOG('5',\n                                  :1  ,\n                                  :2  ,\n                                  :3  ,\n                                  :4  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setInt(3,v_cod_err);
   __sJT_st.setString(4,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_ejecutar = (String)__sJT_st.getString(1);
   v_consecutivo_log = __sJT_st.getInt(2); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cod_err = __sJT_st.getInt(3); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(4);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:94^51*/
      out.println("v_ejecutar "+v_ejecutar);
      out.println("v_consecutivo "+v_consecutivo_log);
      if (v_cod_err == 0){
        //*************************************************
        //Verificar si paso 5 se puede ejecutar
        //*************************************************
        while ((v_proceso_exitoso == "N") && (v_cont <= v_veces)) {
          v_cod_err = 0;
          v_men_err = " ";
          /*@lineinfo:generated-code*//*@lineinfo:104^11*/

//  ************************************************************
//  #sql { call TBPBD_INTERF_RET_GRAL('5',
//                                             :v_fecha_control,
//                                             :v_valido,
//                                             :v_veces,
//                                             :v_cod_err,
//                                             :v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INTERF_RET_GRAL('5',\n                                            :1  ,\n                                            :2  ,\n                                            :3  ,\n                                            :4  ,\n                                            :5  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setInt(3,v_veces);
   __sJT_st.setInt(4,v_cod_err);
   __sJT_st.setString(5,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_fecha_control = new Integer(__sJT_st.getInt(1)); if (__sJT_st.wasNull()) v_fecha_control = null;
   v_valido = (String)__sJT_st.getString(2);
   v_cod_err = __sJT_st.getInt(4); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:109^61*/
          out.println("v_cod_err  de TBPBD_INTERF_RET_GRAL "+v_cod_err);
          if (v_cod_err == 0){
            v_fecha_c = v_fecha_control.toString();
            v_proceso_exitoso = "S";
          }
          else{
            v_proceso_exitoso = "N";
            v_cont            = v_cont + 1;
          }
        }//Mientras verificacion de paso 5 no es exitosa
        //*************************************************
        //Si paso 5 se puede ejecutar
        //*************************************************
        out.println("v_valido "+v_valido);
        if (v_valido.equals("S")){
          //**************************************************************
          //Buscar en la tabla de control en que proceso se debe iniciar
          //el paso 5
          //**************************************************************
          /*@lineinfo:generated-code*//*@lineinfo:129^11*/

//  ************************************************************
//  #sql v_proceso = { values (TBFBD_BUSCAR_PROCESO( '5',
//                                                            :v_fecha_control,
//                                                            :v_cod_err,
//                                                            :v_men_err)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_BUSCAR_PROCESO( '5',\n                                                           :2  ,\n                                                           :3  ,\n                                                           :4  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(2,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(2,v_fecha_control.intValue());
   __sJT_st.setInt(3,v_cod_err);
   __sJT_st.setString(4,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_proceso = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cod_err = __sJT_st.getInt(3); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(4);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:132^77*/
          out.println("Proceso "+v_proceso);
          out.println(" v_cod_err en TBFBD_BUSCAR_PROCESO "+ v_cod_err);
          if ( v_cod_err == 0 ){
            //*****************************************************************
            //Si el proceso a ejecutar es el 0 significa que en una ejecucion
            //anterior se corrio todo el paso 5 y se debe correr nuevamente
            //debido a que el valor de la unidad en el paso 4 cambió
            //Se debe borrar la tabla del Log para correr nuevamente el paso 5
            //Ademas se debe borrar la tabla temporal de retiros de Tax
            //******************************************************************
            if (v_proceso == 0){//proceso 0
              v_proceso         = 1;
              v_consecutivo_log = 0;//se coloca consecutivo en 0 por que se borraran todas las tablas
              /*@lineinfo:generated-code*//*@lineinfo:146^15*/

//  ************************************************************
//  #sql { DELETE tbinterface_logs
//                           WHERE inl_interface = 'EG'
//                             AND inl_fecha     = TO_DATE(TO_CHAR(:v_fecha_control),'RRRR-MM-DD')
//                             AND (inl_paso      LIKE '5%' OR  inl_paso      = 'VU' ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "DELETE tbinterface_logs\n                         WHERE inl_interface = 'EG'\n                           AND inl_fecha     = TO_DATE(TO_CHAR( :1  ),'RRRR-MM-DD')\n                           AND (inl_paso      LIKE '5%' OR  inl_paso      = 'VU' )";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:149^83*/
              out.println("Borrado tbinterface_logs");
              /*@lineinfo:generated-code*//*@lineinfo:151^15*/

//  ************************************************************
//  #sql { DELETE tbinterface_retiros
//                          WHERE inr_fecha     = TO_DATE(TO_CHAR(:v_fecha_control),'RRRR-MM-DD')
//                            AND inr_paso      = '5'  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "DELETE tbinterface_retiros\n                        WHERE inr_fecha     = TO_DATE(TO_CHAR( :1  ),'RRRR-MM-DD')\n                          AND inr_paso      = '5'";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"4TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:153^51*/
              out.println("Borrado tbinterface_retiros");


              /*@lineinfo:generated-code*//*@lineinfo:157^15*/

//  ************************************************************
//  #sql { call TBPBD_BORRAR_VALOR_UNIDAD( :v_fecha_control
//                                                       ,:v_codvu
//                                                       ,:v_menvu )
//  
//                       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_BORRAR_VALOR_UNIDAD(  :1  \n                                                     , :2  \n                                                     , :3   )\n\n                    \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"5TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
   __sJT_st.setInt(2,v_codvu);
   __sJT_st.setString(3,v_menvu);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:161^21*/

                    
              /*@lineinfo:generated-code*//*@lineinfo:164^15*/

//  ************************************************************
//  #sql { DELETE tbinterface_aportes
//                         WHERE INA_FECHA  = TO_DATE(TO_CHAR(:v_fecha_control),'RRRR-MM-DD')
//                          AND  INA_PASO   = '5'  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "DELETE tbinterface_aportes\n                       WHERE INA_FECHA  = TO_DATE(TO_CHAR( :1  ),'RRRR-MM-DD')\n                        AND  INA_PASO   = '5'";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"6TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:166^47*/
              out.println("Borrado tbinterface_aportes");
              /*@lineinfo:generated-code*//*@lineinfo:168^15*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:168^28*/
              out.println("Borrado TBPBD_BORRAR_VALOR_UNIDAD");
              /*@lineinfo:generated-code*//*@lineinfo:170^15*/

//  ************************************************************
//  #sql { DELETE ajkmcpp@mfund  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "DELETE ajkmcpp@mfund";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"7TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:170^44*/
              /*@lineinfo:generated-code*//*@lineinfo:171^15*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:171^28*/
              out.println("Borrado ajkmcpp");
              /*@lineinfo:generated-code*//*@lineinfo:173^15*/

//  ************************************************************
//  #sql { DELETE ajklcpp@mfund  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "DELETE ajklcpp@mfund";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"8TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:173^44*/
              /*@lineinfo:generated-code*//*@lineinfo:174^15*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:174^28*/
             // #sql  {alter session close DATABASE LINK mfund};
              out.println("Borrado ajklcpp");
            }//Si el proceso a ejecutar es 0


            /************  proceso 1 insertar en tabla de control********************/
             if(v_proceso == 1)
             {//proceso 1
               v_proceso_exitoso = "N";
               v_cont            = 1;
               while ((v_proceso_exitoso.equals("N")) && (v_cont <= v_veces))
               {
                v_cod_err = 0;
                v_men_err = " ";
                /*@lineinfo:generated-code*//*@lineinfo:189^17*/

//  ************************************************************
//  #sql { call TBPBD_PASO5(:v_fecha_control,
//                                         :v_proceso,
//                                         :v_cod_err,
//                                         :v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_PASO5( :1  ,\n                                        :2  ,\n                                        :3  ,\n                                        :4  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
   __sJT_st.setInt(2,v_proceso);
   __sJT_st.setInt(3,v_cod_err);
   __sJT_st.setString(4,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_proceso = __sJT_st.getInt(2); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cod_err = __sJT_st.getInt(3); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(4);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:192^57*/
                out.println("v_cod_err en  TBPBD_PASO5"+v_cod_err);

                if (v_cod_err == 0)
                  v_proceso_exitoso = "S";
                else
                 v_cont = v_cont + 1;
              }//Mientras proceso no se realice exitosamente
             }// proceso 1


            /**************************fin proceso1 *********************************************/
            //*************************************************
            //Conexion al AS400. Buscar elementos de conexion
            // al As400 en referencias
            //*************************************************
            /*@lineinfo:generated-code*//*@lineinfo:208^13*/

//  ************************************************************
//  #sql v_sw = { values(TB_FREFERENCIAS_MULTI(:v_sistema,
//                                                         :v_usuario,
//                                                         :v_password,
//                                                         :v_libreria )) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FREFERENCIAS_MULTI( :2  ,\n                                                        :3  ,\n                                                        :4  ,\n                                                        :5   ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"10TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_sistema);
   __sJT_st.setString(3,v_usuario);
   __sJT_st.setString(4,v_password);
   __sJT_st.setString(5,v_libreria);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_sw = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_sistema = (String)__sJT_st.getString(2);
   v_usuario = (String)__sJT_st.getString(3);
   v_password = (String)__sJT_st.getString(4);
   v_libreria = (String)__sJT_st.getString(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:211^76*/

            if (v_sw == 0){
              //Conectarse al AS400
              as400 = new AS400(""+v_sistema+"");
              try{
                //se conecta con usuario y password
                as400.setUserId(""+v_usuario+"");
                as400.setPassword(""+v_password+"");
              }//Hubo error al conectarse al AS400
              catch (Exception e){
                out.println("Error al conectarse al As400 "+e);
                v_cod_err = -1;
              }//Hubo error al conectarse al AS400
              out.println("v_cod_err despues de conexion as "+v_cod_err);
              if (v_cod_err == 0){
                //*************************************************
                //Proceso 2: Calcular el valor de unidad de los
                //retiros a procesar en este paso
                //*************************************************

                if (v_proceso == 2 ){
                  v_proceso_exitoso = "N";
                  v_cont            = 1;
                  while ((v_proceso_exitoso.equals("N")) && (v_cont <= v_veces)) {
                    v_cod_err = 0;
                    v_men_err = " ";
                    //Calcular el valor de unidad para los retiros a procesar e insertarlos en TBSALDOS
                    /*#sql vreg_retiro = {SELECT DISTINCT ret_con_pro_codigo,
                                                        ret_con_numero,
                                                        TO_CHAR(ret_fecha_efectiva,'RRRRMMDD') AS fecha_contrato,
                                                        TO_CHAR(ret_fecha_efectiva-1,'RRRRMMDD') AS fecha_unidad
                                          FROM  tbretiros
                                          WHERE ret_fecha_proceso = TO_DATE(TO_CHAR(:v_fecha_control),'RRRR-MM-DD')
                                            AND ret_ref_estado IN ( 'SER014', 'SER015', 'SER016', 'SER017')
                                          ORDER BY ret_con_pro_codigo, ret_con_numero};
                    while (vreg_retiro.next() ){
                      //Si la fecha efectiva del retiro es menor a la fecha de proceso (Back Date)
                      //Calcular el valor de unidad con fecha de contrato y unidad iguales a la
                      //fecha efectiva. En caso contrario calcular el valor de unidad con fecha
                      //de contrato igual a la fecha efectiva y fecha de unidad con fecha efectiva
                      //menos 1 para no tomar las transacciones del dia
                      fe = vreg_retiro.fecha_contrato();
                      if ( fe.compareTo(v_fecha_c) < 0 ){
                        v_fecha_contrato = vreg_retiro.fecha_contrato();
                        v_fecha_unidad   = vreg_retiro.fecha_contrato();
                      }
                      else{
                        v_fecha_contrato = vreg_retiro.fecha_contrato();
                        v_fecha_unidad   = vreg_retiro.fecha_unidad();
                      }
                      out.println("caso 1017 sin P v_fecha_contrato "+v_fecha_contrato);
                      out.println("caso 1017 sin P v_fecha_unidad "+v_fecha_unidad);
                     v_vector = SQL_CALCULO_VALOR_UNIDAD.TBF_CALCULO_VALOR_UNIDAD_P(v_fecha_contrato,
                                                                                v_fecha_unidad,
                                                                                     vreg_retiro.ret_con_numero(),
                                                                                     vreg_retiro.ret_con_pro_codigo(),
                                                                                     true,
                                                                                     as400,
                                                                                     v_libreria);
                      y = Math.round(v_vector[2]);
                      if ( y == -4.0 ){
                        v_log_datos = "Contrato: "+vreg_retiro.ret_con_numero()+"Producto: "+vreg_retiro.ret_con_pro_codigo()+
                                      "Fecha Contrato: "+v_fecha_contrato+
                                      "Fecha Unidad: "+v_fecha_unidad;
                        //MODIFICACION HECHA POR APC 2004/03/09 SE CREA UNA VARIABLE STRING PARA MANDARLA COMO PARAMETRO 
                        String v_prod_codigo = vreg_retiro.ret_con_pro_codigo();
                        #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG'
                                                               ,TO_DATE(:v_fecha_contrato,'RRRR-MM-DD')
                                                               ,'VU'
                                                               ,'Excepcion en TBF_CALCULO_VALOR_UNIDAD_P paila'
                                                               ,:v_log_datos
                                                               ,:v_prod_codigo
                                                               ,null)};
                        #sql {COMMIT};
                      }
                      v_cod_err = 0;
                      v_men_err = " ";
                    }//Mientras se calcula el valor de la unidad de los retiros a procesar en paso 5
                    --*/
                    /*@lineinfo:generated-code*//*@lineinfo:291^21*/

//  ************************************************************
//  #sql { call TBPBD_INSERTARSALDOSRETIROS(TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
//                                             :v_cod_err,
//                                             :v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INSERTARSALDOSRETIROS(TO_DATE( :1  ,'RRRR-MM-DD'),\n                                            :2  ,\n                                            :3  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"11TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
   __sJT_st.setInt(2,v_cod_err);
   __sJT_st.setString(3,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_cod_err = __sJT_st.getInt(2); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(3);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:293^61*/
                    //vreg_retiro.close();

                    if (v_cod_err != 0 ){
                      /*@lineinfo:generated-code*//*@lineinfo:297^23*/

//  ************************************************************
//  #sql { ROLLBACK };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:297^37*/
                      v_cont            = v_cont + 1;
                      v_proceso_exitoso = "N";
                    }
                    else{
                      /*@lineinfo:generated-code*//*@lineinfo:302^23*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG',
//                                                               TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
//                                                               '53',
//                                                               'Proceso 2: Calculo de Valor de Unidad para retiros a procesar exitoso',
//                                                               'XXXXX',
//                                                               'XXXXX',
//                                                                null ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG',\n                                                             TO_DATE( :1  ,'RRRR-MM-DD'),\n                                                             '53',\n                                                             'Proceso 2: Calculo de Valor de Unidad para retiros a procesar exitoso',\n                                                             'XXXXX',\n                                                             'XXXXX',\n                                                              null )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"12TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:308^69*/
                      v_proceso_exitoso = "S";
                      v_proceso          = 3;
                      /*@lineinfo:generated-code*//*@lineinfo:311^23*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:311^35*/
                    }
                  }//Mientras calculo del valor de unidad no se realice exitosamente

                }//Proceso 2

                //****************************************************************************
                //Proceso 3: Informar a MultifunD que el paso 5 esta en proceso.
                //Además realiza los procesos siguientes del paso 5 si proceso 2 fue exitoso
                //****************************************************************************
                out.println("v_proceso "+v_proceso);
                if ((v_proceso >= 3) && (v_proceso < 9))  {
                  v_proceso_exitoso = "N";
                  v_cont            = 1;
                  while ((v_proceso_exitoso.equals("N")) && (v_cont <= v_veces)) {
                    v_cod_err = 0;
                    v_men_err = " ";
                    out.println("va a llamar paso5");
                    out.println("v_fecha_control "+v_fecha_control);
                    out.println("v_proceso "+v_proceso);
                    out.println("v_codigo "+v_cod_err);
                    out.println("v_men_err "+v_men_err);
                    /*@lineinfo:generated-code*//*@lineinfo:333^21*/

//  ************************************************************
//  #sql { call TBPBD_PASO5(:v_fecha_control,
//                                             :v_proceso,
//                                             :v_cod_err,
//                                             :v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_PASO5( :1  ,\n                                            :2  ,\n                                            :3  ,\n                                            :4  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"13TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
   __sJT_st.setInt(2,v_proceso);
   __sJT_st.setInt(3,v_cod_err);
   __sJT_st.setString(4,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_proceso = __sJT_st.getInt(2); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cod_err = __sJT_st.getInt(3); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(4);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:336^61*/
                    out.println("llamo paso5");
                    out.println("Mensaje de error en paso5 " + v_men_err + ", codigo de error " + v_cod_err);
                    if (v_cod_err == 0){
                      v_proceso_exitoso = "S";
                      v_proceso          = 9;
                    }
                    else{
                      v_cont = v_cont + 1;
                      v_proceso_exitoso = "N";
                    }
                  }//Mientras proceso no se realice exitosamente
                }//Proceso =3
                //*************************************************
                //Proceso 9: Llamar Procedimiento del AS400
                //*************************************************
                if (v_proceso == 9)
                 {
                  // Se debe determinar si el proceso 9 se esta ejecutando como parte de un reintento o a
                  // continuacion del proceso 8.  En el primer caso se debe insertar un registro en la
                  // tabla de control (Paso 5 en YES) ya que la terminación anormal anterior lo borro.
                  // En el segundo caso se debe seguir con la ejecucion normal.

                  // Se consulta el codigo de la unidad Total en la Tabla de Referencias
                  /*@lineinfo:generated-code*//*@lineinfo:360^19*/

//  ************************************************************
//  #sql v_unidad_total = { values(TBFBD_TBREFERENCIAS( 'UUP099',
//                                                                        :v_cod_err,
//                                                                        :v_men_err )) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_TBREFERENCIAS( 'UUP099',\n                                                                       :2  ,\n                                                                       :3   ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"14TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setInt(2,v_cod_err);
   __sJT_st.setString(3,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_unidad_total = (String)__sJT_st.getString(1);
   v_cod_err = __sJT_st.getInt(2); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(3);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:362^90*/


                  if( v_cod_err == 0 ) // si no hubo error
                   // Se consulta si existe el paso 5 en la tabla de control.
                   { /*@lineinfo:generated-code*//*@lineinfo:367^22*/

//  ************************************************************
//  #sql v_existe_paso = { values(TBFBD_EXISTE_PASO( 5,
//                                                                      :v_fecha_control,
//                                                                      :v_unidad_total,
//                                                                      :v_cod_err,
//                                                                      :v_men_err )) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_EXISTE_PASO( 5,\n                                                                     :2  ,\n                                                                     :3  ,\n                                                                     :4  ,\n                                                                     :5   ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"15TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(2,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(2,v_fecha_control.intValue());
   __sJT_st.setString(3,v_unidad_total);
   __sJT_st.setInt(4,v_cod_err);
   __sJT_st.setString(5,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_existe_paso = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cod_err = __sJT_st.getInt(4); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:371^88*/

                   if (v_cod_err == 0) // si no hubo error
                   {
                     if (v_existe_paso == 0) // El paso 5 no existe
                     {
                       // Inserta en la tabla de control paso 5 en YES
                       /*@lineinfo:generated-code*//*@lineinfo:378^24*/

//  ************************************************************
//  #sql { call TBPBD_INS_AJKPCPP(:v_fecha_control,
//                                                      'EG',
//                                                      :v_unidad_total,
//                                                      5,
//                                                      'Y',
//                                                      :v_cod_err,
//                                                      :v_men_err ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_AJKPCPP( :1  ,\n                                                    'EG',\n                                                     :2  ,\n                                                    5,\n                                                    'Y',\n                                                     :3  ,\n                                                     :4   )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"16TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
   __sJT_st.setString(2,v_unidad_total);
   __sJT_st.setInt(3,v_cod_err);
   __sJT_st.setString(4,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_cod_err = __sJT_st.getInt(3); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(4);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:384^71*/

                      if(v_cod_err == 0 ) // si no hubo error
                      {
                        /*@lineinfo:generated-code*//*@lineinfo:388^25*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:388^37*/
                        v_existe_paso = 5;
                        v_estado_paso = "Y";
                      }
                     }
                     else  // El paso 5 ya existe
                     {
                        // Se verifica que el paso 5 este es estado YES.
                        /*@lineinfo:generated-code*//*@lineinfo:396^25*/

//  ************************************************************
//  #sql v_estado_paso = { values(TBFBD_ESTADO_PASO( 5,
//                                                                          :v_fecha_control,
//                                                                          :v_unidad_total,
//                                                                          :v_cod_err,
//                                                                          :v_men_err )) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_ESTADO_PASO( 5,\n                                                                         :2  ,\n                                                                         :3  ,\n                                                                         :4  ,\n                                                                         :5   ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"17TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(2,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(2,v_fecha_control.intValue());
   __sJT_st.setString(3,v_unidad_total);
   __sJT_st.setInt(4,v_cod_err);
   __sJT_st.setString(5,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_estado_paso = (String)__sJT_st.getString(1);
   v_cod_err = __sJT_st.getInt(4); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:400^92*/
                     }//existe paso
                   }//codigo de error
                  }//codigo erro uni
                  if(v_existe_paso == 5 && v_estado_paso.equals("Y")) // El paso 5 existe en YES
                  {
                   if (v_sw == 0)
                   {//0
                    //*******************************************************************
                    //Llamado al paso 6 de la interfaz de retiros. Se llama una funcion
                    //que ejecuta el paso 6 del AS400 la cual retorna:
                    // 0: Si corrio el paso 6
                    // -1: Si hubo una excepcion
                    // -2: Si no corrio el paso 6
                    //*******************************************************************
                    try
                    {
                     ias400 = new TBCL_AS400();
                     v_cod_err = ias400.TBF_PASO6(v_fecha_c, as400, v_libreria);
                    }
                    catch(Exception e){ v_cod_err = -3;}
                    if (v_cod_err == 0 )
                    {//1
                     /*@lineinfo:generated-code*//*@lineinfo:423^22*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:423^34*/
                     /*@lineinfo:generated-code*//*@lineinfo:424^22*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG',
//                                                             TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
//                                                             '50',
//                                                             'Proceso 9: Paso 6 se pudo llamar',
//                                                             'XXXXX',
//                                                             'XXXXX',
//                                                             null ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG',\n                                                           TO_DATE( :1  ,'RRRR-MM-DD'),\n                                                           '50',\n                                                           'Proceso 9: Paso 6 se pudo llamar',\n                                                           'XXXXX',\n                                                           'XXXXX',\n                                                           null )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"18TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:430^66*/
                    }//1
                    else
                    {//1
                     if (v_cod_err == -1){v_men_err = "Excepcion en el Paso 6 TBF_PASO6";}
                     if (v_cod_err == -2){v_men_err = "No corrio el Paso 6";}
                     if (v_cod_err == -3){v_men_err = "Excepcion en el llamado del Paso 6 TBP_PASO5";}
                     String v_men_paso6 = "Error Proceso 9: Paso 6 "+v_men_err;
                     /*@lineinfo:generated-code*//*@lineinfo:438^22*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:438^34*/
                     /*@lineinfo:generated-code*//*@lineinfo:439^22*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG',
//                                                             TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
//                                                             '5',
//                                                             :v_men_paso6,
//                                                             'XXXXX',
//                                                             'XXXXX',
//                                                             null ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG',\n                                                           TO_DATE( :1  ,'RRRR-MM-DD'),\n                                                           '5',\n                                                            :2  ,\n                                                           'XXXXX',\n                                                           'XXXXX',\n                                                           null )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"19TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
   __sJT_st.setString(2,v_men_paso6);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:445^66*/
                     }//1
                   }//No hubo error al Seleccionar de parametros de Multifund 0
                  }//
                  else
                  {
                      /*@lineinfo:generated-code*//*@lineinfo:451^23*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:451^35*/
                     /*@lineinfo:generated-code*//*@lineinfo:452^22*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG',
//                                                             TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
//                                                             '5',
//                                                             'Error proceso 9: El Paso 5 del registro de control esta en estado N.',
//                                                             'XXXXX',
//                                                             'XXXXX',
//                                                             null ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG',\n                                                           TO_DATE( :1  ,'RRRR-MM-DD'),\n                                                           '5',\n                                                           'Error proceso 9: El Paso 5 del registro de control esta en estado N.',\n                                                           'XXXXX',\n                                                           'XXXXX',\n                                                           null )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"20TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:458^66*/

                  }
                }//Proceso = 9
                //*************************************************
                //Desconectarse del AS400
                //*************************************************
                as400.disconnectService(AS400.COMMAND);
              }//No hubo error al conectarse al AS400
            }//No hubo error al buscar los elementos de conexion en TBREFERENCIAS
          }//No hubo error al buscar proceso del paso 5
          //*************************************************
          //informar en el Log que el Paso 5 se ejecutó
          //*************************************************
          if (v_cod_err == 0)
          {
            //Si el paso 5 fue valido y no hubo error en el proceso informarlo como
            //finalizado exitosamente para que el crone no lo vuelva a ejecutar
            out.println("Valido y no error exitoso");
            if (v_consecutivo_log == 0)
            {
              /*@lineinfo:generated-code*//*@lineinfo:479^15*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG',
//                                                      TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
//                                                      '5',
//                                                      'TASK SCHEDULE: EXITOSO',
//                                                      'XXXXX',
//                                                      'XXXXX',
//                                                      null ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG',\n                                                    TO_DATE( :1  ,'RRRR-MM-DD'),\n                                                    '5',\n                                                    'TASK SCHEDULE: EXITOSO',\n                                                    'XXXXX',\n                                                    'XXXXX',\n                                                    null )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"21TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:485^59*/
              /*@lineinfo:generated-code*//*@lineinfo:486^15*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:486^27*/
            }
            else
            {
              /*@lineinfo:generated-code*//*@lineinfo:490^15*/

//  ************************************************************
//  #sql { call TBPBD_UPDATE_TBINTERFACE_LOGS( :v_fecha_control,
//                                                          '5',
//                                                          :v_consecutivo_log,
//                                                          'TASK SCHEDULE: EXITOSO') };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_UPDATE_TBINTERFACE_LOGS(  :1  ,\n                                                        '5',\n                                                         :2  ,\n                                                        'TASK SCHEDULE: EXITOSO')\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"22TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
   __sJT_st.setInt(2,v_consecutivo_log);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:493^82*/
              /*@lineinfo:generated-code*//*@lineinfo:494^15*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:494^27*/
            }
          }//No hubo error en la ejecucion del paso 5
          else
          {
            //Si el paso 5 fue valido y hubo error en el proceso informarlo como
            //error para que el crone lo vuelva a ejecutar
            if (v_consecutivo_log == 0)
            {
              /*@lineinfo:generated-code*//*@lineinfo:503^15*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG',
//                                                      TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
//                                                      '5',
//                                                      'TASK SCHEDULE: FALLO',
//                                                      'XXXXX',
//                                                      'XXXXX',
//                                                      null ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG',\n                                                    TO_DATE( :1  ,'RRRR-MM-DD'),\n                                                    '5',\n                                                    'TASK SCHEDULE: FALLO',\n                                                    'XXXXX',\n                                                    'XXXXX',\n                                                    null )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"23TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:509^59*/
              /*@lineinfo:generated-code*//*@lineinfo:510^15*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:510^27*/
            }
            else
            {
              /*@lineinfo:generated-code*//*@lineinfo:514^15*/

//  ************************************************************
//  #sql { call TBPBD_UPDATE_TBINTERFACE_LOGS( :v_fecha_control,
//                                                          '5',
//                                                          :v_consecutivo_log,
//                                                          'TASK SCHEDULE: FALLO') };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_UPDATE_TBINTERFACE_LOGS(  :1  ,\n                                                        '5',\n                                                         :2  ,\n                                                        'TASK SCHEDULE: FALLO')\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"24TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
   __sJT_st.setInt(2,v_consecutivo_log);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:517^80*/
              /*@lineinfo:generated-code*//*@lineinfo:518^15*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:518^27*/
            }
            //Borrar en la tabla de control el paso 5 para que la validacion en el proximo
            //intento sea correcta.
            /*@lineinfo:generated-code*//*@lineinfo:522^13*/

//  ************************************************************
//  #sql { DELETE ajkpcpp@mfund
//                       WHERE kpgldz = :v_fecha_control
//                         AND kpqgnu = 5
//                         AND kpvvsz = 'EG'  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "DELETE ajkpcpp@mfund\n                     WHERE kpgldz =  :1  \n                       AND kpqgnu = 5\n                       AND kpvvsz = 'EG'";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"25TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:525^42*/
            /*@lineinfo:generated-code*//*@lineinfo:526^13*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:526^25*/
            out.println("Borro");
          }//Hubo error en la ejecucion del paso 5
        }//Fue valido ejecutar paso 5
        else{
          //*************************************************
          //informar en el Log que el Paso 5 no se ejecutó
          //*************************************************
          out.println("No Valido ejecutar");

          /*if (v_consecutivo_log == 0){
            #sql {call TBPBD_INS_TBINTERFACE_LOGS('EG',
                                                  TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
                                                  '5',
                                                  'TASK SCHEDULE: FALLO',
                                                  'XXXXX',
                                                  'XXXXX',
                                                  null )};
            #sql {COMMIT};
          }
          else{
            #sql {call TBPBD_UPDATE_TBINTERFACE_LOGS( :v_fecha_control,
                                                      '5',
                                                      :v_consecutivo_log,
                                                      'TASK SCHEDULE: FALLO')};
            #sql {COMMIT};
          }*/
        }//Si no fue valido ejecutar paso 5
      }//No hubo error al verificar si se puede ejecutar el crone
      out.close();
    }
    catch (Exception e)
    {
     String v_error = e.toString();
     try
     {
       PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("c:\\TaxBenefits\\Taxb\\Pasos_logs\\paso5.log", true)));
       out.println("Error Paso 5 "+e );
       /*@lineinfo:generated-code*//*@lineinfo:564^8*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS('EG',
//                                                    TO_DATE(:v_fecha_control,'RRRR-MM-DD'),
//                                                    '5',
//                                                    'TASK SCHEDULE: FALLO '||:v_error,
//                                                    'XXXXX',
//                                                    'XXXXX',
//                                                    null ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS('EG',\n                                                  TO_DATE( :1  ,'RRRR-MM-DD'),\n                                                  '5',\n                                                  'TASK SCHEDULE: FALLO '|| :2  ,\n                                                  'XXXXX',\n                                                  'XXXXX',\n                                                  null )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"26TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_PASO5",theSqlTS);
   // set IN parameters
   if (v_fecha_control == null) __sJT_st.setNull(1,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(1,v_fecha_control.intValue());
   __sJT_st.setString(2,v_error);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:570^57*/
       /*@lineinfo:generated-code*//*@lineinfo:571^8*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:571^20*/
       out.close();
       }
      catch (Exception e1)
      {
        try
        {
          PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("c:\\TaxBenefits\\Taxb\\Pasos_logs\\paso5.log", true)));
          out.println("Execption  en paso5 - Se necesita recuperacion manual "+ e1);
          out.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
    }
  }//Procedimiento TBP_PASO5
}/*@lineinfo:generated-code*/