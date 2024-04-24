/*@lineinfo:filename=SQL_CALCULO_VALOR_UNIDAD*//*@lineinfo:user-code*//*@lineinfo:1^1*/  // Copyright (c) 2000 Sk
package TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD;

import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import java.sql.*;
import com.ibm.as400.access.*;
import java.io.*;


/**
 * A Sqlj class.
 * <P>
 * @author Skandia
 */
public class SQL_CALCULO_VALOR_UNIDAD extends Object {


    public static double TBF_CALCULO_VALOR_UNIDAD_N(String v_fecha_contrato, String v_fecha_unidad, String v_contrato, String v_producto, String v_update, int v_retiro, int v_index)
    {
        boolean v_bupdate = Boolean.valueOf(v_update).booleanValue();
        double v_vunidad[] = new double[3];
        v_vunidad = TBF_CALCULO_VALOR_UNIDAD(v_fecha_contrato, v_fecha_unidad, v_contrato, v_producto, v_bupdate, v_retiro);
        return v_vunidad[v_index];
    }

    public static String TBF_CALCULO_VALOR_UNIDAD_S(String v_fecha_contrato, String v_fecha_unidad, String v_contrato, String v_producto, String v_update, int v_retiro, int v_index)
    {
        String v_unidadTokenized = "";
        boolean v_bupdate = Boolean.valueOf(v_update).booleanValue();
        double v_vunidad[] = new double[v_index];
        v_vunidad = TBF_CALCULO_VALOR_UNIDAD(v_fecha_contrato, v_fecha_unidad, v_contrato, v_producto, v_bupdate, v_retiro);
        int i = 0;
        v_unidadTokenized = String.valueOf(v_vunidad[i]);
        do
            v_unidadTokenized = v_unidadTokenized + "\\" + String.valueOf(v_vunidad[++i]);
        while(i < v_vunidad.length - 1);
        return v_unidadTokenized;
    }

  /*
  Función que devuelve el valor de la unidad para una fecha,
  contrato y producto especifico. Ademas actualiza la tabla
  TBSALDOS si se desea (v_update=true)
  La fecha debe venir en el formato YYYYMMDD.
  El campo retiro debe ser enviado en 1 cuando se desea que se
  calcule el valor de la unidad pero que no se consulte en TBSALDOS
  */
  public static double[] TBF_CALCULO_VALOR_UNIDAD
    ( String v_fecha_contrato, String v_fecha_unidad, String v_contrato, String v_producto, boolean v_update, int v_retiro)
  {
    Date              v_max_fecha;
    double            v_saldo_contrato = 0;
    double            v_saldo_unidades = 1;
    double            v_valor_unidad   = 0;
    double            v_saldo_unidad_max_fec;
    double            v_cuenta_conting_max_fec;
    int               v_cod_err        = 0;
    String            v_men_err        = " ";
    String            v_fecha;
    String[]          v_valusu;
    double            v_salida[] = new double[3];
    //Connection c = null;

    try{
    
/*      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      c = DriverManager.getConnection("jdbc:oracle:thin:@10.49.28.34:1521:orc1", "tbenefit", "produccion");
      DefaultContext.setDefaultContext( new DefaultContext(c));*/
    
      /* Borrar los saldos mayores a la fecha de contrato enviada existentes en
         TBSALDOS para evitar los caLculos erroneos originados por los BACK DATE */
      /*@lineinfo:generated-code*//*@lineinfo:73^7*/

//  ************************************************************
//  #sql { call TBPBD_DEL_TBSALDOS(:v_fecha_contrato,:v_contrato,:v_producto, :v_cod_err, :v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_DEL_TBSALDOS( :1  , :2  , :3  ,  :4  ,  :5  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_CALCULO_VALOR_UNIDAD",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,v_fecha_contrato);
   __sJT_st.setString(2,v_contrato);
   __sJT_st.setString(3,v_producto);
   __sJT_st.setInt(4,v_cod_err);
   __sJT_st.setString(5,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_cod_err = __sJT_st.getInt(4); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:73^115*/
      if (v_cod_err != 0){
        v_salida[0] = 0;
        v_salida[1] = 0;
        v_salida[2] = v_cod_err;
      }//Error al borrar los saldos existentes en tbsaldos
      else{
        //Llamado a un metodo que devuelve el saldo de contrato
        try{
          TBCL_AS400 ias400 = new TBCL_AS400();

          System.out.println("caso 1017 sin P v_contrato "+v_contrato);
          System.out.println("caso 1017 sin P v_fecha_contrato "+v_fecha_contrato);

          v_saldo_contrato = ias400.TBF_SALDO_CONTRATO(v_contrato, v_fecha_contrato);

          /*
          Modificacion:
          Se añade el procedimiento de invocacion a un procedimiento del AS400
          */
          
//          #sql v_saldo_contrato ={values (TBCL_AS400.TBF_SALDO_CONTRATO(:v_contrato,:v_fecha_contrato))};

          /* Final de la modificacion */



          System.out.println("caso 1017 sin P v_saldo_contrato "+v_saldo_contrato);

          if (v_saldo_contrato == -1.0)
            v_cod_err = -1;
          else if(v_saldo_contrato == -2.0)
            v_cod_err = -2;
          }catch(Exception e){ v_cod_err = -2;}
        //Si ocurre un error al calcular el saldo del contrato en el AS400
        //retornar -1 y no seguir proceso
        if (v_cod_err == 0){
          //Si el saldo del contrato calculado es negativo o cero retornar un error
          //Sino calcular el saldo de unidades
          if (v_saldo_contrato > 0 ){
            //Llamado a procedimiento que devuelve el saldo de unidades para
            //la fecha, contrato y producto deseado calculando primero los
            //ultimos saldos existentes en TBSALDOS
            //Seleccionar los saldos de unidades y cuenta contingente a la maxima
            //fecha en TBSALDOS
            /*@lineinfo:generated-code*//*@lineinfo:118^13*/

//  ************************************************************
//  #sql { call TBPBD_ULTIMOS_SALDOS(:v_fecha_contrato,             :v_contrato,
//                                              :v_producto,                   :v_max_fecha,
//                                              :v_saldo_unidad_max_fec,   :v_cuenta_conting_max_fec,
//                                              :v_cod_err,              :v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_ULTIMOS_SALDOS( :1  ,              :2  ,\n                                             :3  ,                    :4  ,\n                                             :5  ,    :6  ,\n                                             :7  ,               :8  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_CALCULO_VALOR_UNIDAD",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.DATE);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,v_fecha_contrato);
   __sJT_st.setString(2,v_contrato);
   __sJT_st.setString(3,v_producto);
   __sJT_st.setInt(7,v_cod_err);
   __sJT_st.setString(8,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_max_fecha = (java.sql.Date)__sJT_st.getDate(4);
   v_saldo_unidad_max_fec = __sJT_st.getDouble(5); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cuenta_conting_max_fec = __sJT_st.getDouble(6); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cod_err = __sJT_st.getInt(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(8);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:121^93*/

            //Si hubo error al calcular los ultimos saldos no seguir proceso
            //y retornar el codigo de error sino seguir con el calculo de unidades a la fecha deseada
            if (v_cod_err == 0){
              /*@lineinfo:generated-code*//*@lineinfo:126^15*/

//  ************************************************************
//  #sql v_saldo_unidades = { values (TBFBD_CALC_SALDO_UNIDAD_P(:v_fecha_unidad,
//                                                                           :v_contrato,
//                                                                           :v_producto,
//                                                                           :v_max_fecha,
//                                                                           :v_saldo_unidad_max_fec,
//                                                                           :v_cod_err,
//                                                                           :v_men_err)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_CALC_SALDO_UNIDAD_P( :2  ,\n                                                                          :3  ,\n                                                                          :4  ,\n                                                                          :5  ,\n                                                                          :6  ,\n                                                                          :7  ,\n                                                                          :8  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_CALCULO_VALOR_UNIDAD",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_fecha_unidad);
   __sJT_st.setString(3,v_contrato);
   __sJT_st.setString(4,v_producto);
   __sJT_st.setDate(5,v_max_fecha);
   __sJT_st.setDouble(6,v_saldo_unidad_max_fec);
   __sJT_st.setInt(7,v_cod_err);
   __sJT_st.setString(8,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_saldo_unidades = __sJT_st.getDouble(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cod_err = __sJT_st.getInt(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(8);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:132^92*/
              System.out.println("v_saldo_unidades "+v_saldo_unidades);

              //Si no hubo error al calcular el saldo de unidades seguir proceso
              //Sino retornar el codigo de error
              if (v_cod_err == 0){
                //Si el saldo de unidades es menor o igual a cero retornar error
                if (v_saldo_unidades > 0 ){
                  v_valor_unidad = v_saldo_contrato/v_saldo_unidades;
                  v_salida[0] = v_valor_unidad;//Valor de la Unidad
                  v_salida[1] = v_saldo_contrato;//Saldo del Contrato
                  v_salida[2] = 0;//Codigo de error
                  //Si v_update = true llamar al proc de insercion a TBSALDOS
                  if (v_update){
                    //Llama al procedimiento que inserta en la tabla TBSALDOS
                    /*@lineinfo:generated-code*//*@lineinfo:147^21*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBSALDOS(:v_producto,       :v_contrato,
//                                                    :v_fecha_contrato, :v_saldo_contrato,
//                                                    null,              null,
//                                                    :v_saldo_unidades, :v_valor_unidad,
//                                                    :v_cod_err,  :v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBSALDOS( :1  ,        :2  ,\n                                                   :3  ,  :4  ,\n                                                  null,              null,\n                                                   :5  ,  :6  ,\n                                                   :7  ,   :8  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_CALCULO_VALOR_UNIDAD",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,v_producto);
   __sJT_st.setString(2,v_contrato);
   __sJT_st.setString(3,v_fecha_contrato);
   __sJT_st.setDouble(4,v_saldo_contrato);
   __sJT_st.setDouble(5,v_saldo_unidades);
   __sJT_st.setDouble(6,v_valor_unidad);
   __sJT_st.setInt(7,v_cod_err);
   __sJT_st.setString(8,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_cod_err = __sJT_st.getInt(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(8);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:151^87*/

                    //Si este saldo existe en la tabla TBSALDOS se actualiza la tabla
                    if (v_cod_err == -0001){
                      /*@lineinfo:generated-code*//*@lineinfo:155^23*/

//  ************************************************************
//  #sql { UPDATE  tbsaldos
//                                 SET   sal_saldo_contrato            = :v_saldo_contrato,
//                                       sal_saldo_contrato_disponible = null,
//                                       sal_saldo_cuenta_contingente  = null ,
//                                       sal_saldo_unidades            = :v_saldo_unidades,
//                                       sal_valor_unidad              = :v_valor_unidad
//                                 WHERE sal_con_pro_codigo            = :v_producto
//                                   AND sal_con_numero                = :v_contrato
//                                   AND TRUNC(sal_fecha)              = TO_DATE(:v_fecha_contrato) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "UPDATE  tbsaldos\n                               SET   sal_saldo_contrato            =  :1  ,\n                                     sal_saldo_contrato_disponible = null,\n                                     sal_saldo_cuenta_contingente  = null ,\n                                     sal_saldo_unidades            =  :2  ,\n                                     sal_valor_unidad              =  :3  \n                               WHERE sal_con_pro_codigo            =  :4  \n                                 AND sal_con_numero                =  :5  \n                                 AND TRUNC(sal_fecha)              = TO_DATE( :6  )";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"4TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_CALCULO_VALOR_UNIDAD",theSqlTS);
   // set IN parameters
   __sJT_st.setDouble(1,v_saldo_contrato);
   __sJT_st.setDouble(2,v_saldo_unidades);
   __sJT_st.setDouble(3,v_valor_unidad);
   __sJT_st.setString(4,v_producto);
   __sJT_st.setString(5,v_contrato);
   __sJT_st.setString(6,v_fecha_contrato);
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:163^96*/

                      v_cod_err = 0;
                    }// fin si error de constraint
                  }// fin si quiere actualizar
                }//Saldo de unidades es mayor a cero
                else{
                  v_salida[0] = 0;//Valor de la Unidad
                  v_salida[1] = v_saldo_contrato;//Saldo del Contrato
                  v_salida[2] = -5;//Codigo de error
                }//Saldo de unidades es menor a cero
              }// fin si no hubo error al calcular saldo de unidades
              else{
                v_salida[0] = 0;//Valor de la Unidad
                v_salida[1] = 0;//Saldo del Contrato
                v_salida[2] = v_cod_err;//Codigo de error
              }//hubo error al calcular saldos de unidades
            }//No hubo error al calcular los saldos a maxima fecha
            else{
              v_salida[0] = 0;//Valor de la Unidad
              v_salida[1] = 0;//Saldo del Contrato
              v_salida[2] = v_cod_err;//Codigo de error
            }//Hubo error al calcular los saldos a maxima fecha
          }//Saldo del contrato es positivo
          else{
            v_salida[0] = 0;//Valor de la Unidad
            v_salida[1] = 0;//Saldo del Contrato
            v_salida[2] = -3;//Codigo de error
          }//Saldo del contrato es negativo o igual a cero retornar error
        }//No hubo error al calcular saldo del contrato en el AS400
        else{
          v_salida[0] = 0;//Valor de la Unidad
          v_salida[1] = 0;//Saldo del Contrato
          v_salida[2] = v_cod_err;//Codigo de error
        }//Hubo error al calcular saldo del contrato en el AS400
      }//No error al borrar los saldos existentes en tbsaldos
    }catch(Exception e)
    {
      try{
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("c:\\TaxBenefits\\Taxb\\Pasos_logs\\error.log", true)));
      DefaultContext.setDefaultContext(null);                                                
      out.println("HP Error=" + e.toString());
      out.close();
      }
      catch(Exception ex)
      {
        
      }
       v_salida[0] = 0;       
       v_salida[1] = 0;
       v_salida[2] = -4;
      return v_salida;
   }

    return v_salida;
  }//fin metodo

    /*
     Valor Unidad con parametros de conexion
    */
  public static double[] TBF_CALCULO_VALOR_UNIDAD_P
    ( String v_fecha_contrato, String v_fecha_unidad, String v_contrato, String v_producto, boolean v_update,  AS400 as400, String v_libreria)
  {
    Date              v_max_fecha;
    double            v_saldo_contrato = 0;
    double            v_saldo_unidades = 1;
    double            v_valor_unidad   = 0;
    double            v_saldo_unidad_max_fec;
    double            v_cuenta_conting_max_fec;
    int               v_cod_err        = 0;
    String            v_men_err        = " ";
    String            v_fecha;
    String[]          v_valusu;
    String            v_log_datos;
    double            v_salida[] = new double[3];
    try{
      v_log_datos = "Contrato: "+v_contrato+"Producto: "+v_producto+"Fecha Contrato: "+v_fecha_contrato+
                    "Fecha Unidad: "+v_fecha_unidad;
      /* Borrar los saldos mayores a la fecha de contrato enviada existentes en
         TBSALDOS para evitar los caLculos erroneos originados por los BACK DATE */
      /*@lineinfo:generated-code*//*@lineinfo:243^7*/

//  ************************************************************
//  #sql { call TBPBD_DEL_TBSALDOS(:v_fecha_contrato,:v_contrato,:v_producto, :v_cod_err, :v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_DEL_TBSALDOS( :1  , :2  , :3  ,  :4  ,  :5  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_CALCULO_VALOR_UNIDAD",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,v_fecha_contrato);
   __sJT_st.setString(2,v_contrato);
   __sJT_st.setString(3,v_producto);
   __sJT_st.setInt(4,v_cod_err);
   __sJT_st.setString(5,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_cod_err = __sJT_st.getInt(4); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:243^115*/
      if (v_cod_err != 0){
        v_salida[0] = 0;
        v_salida[1] = 0;
        v_salida[2] = v_cod_err;

      }//Error al borrar los saldos existentes en tbsaldos
      else{
        //Llamado a un metodo que devuelve el saldo de contrato
        try{
          TBCL_AS400 ias400 = new TBCL_AS400();
          System.out.println("caso 1017 con P v_contrato "+v_contrato);
          System.out.println("caso 1017 con P v_fecha_contrato "+v_fecha_contrato);
          v_saldo_contrato = ias400.TBF_SALDO_CONTRATO_P(v_contrato, v_fecha_contrato, as400, v_libreria);
          System.out.println("caso 1017 con P v_saldo_contrato "+v_saldo_contrato);
          if (v_saldo_contrato == -1.0)
             v_cod_err = -1;
          else if(v_saldo_contrato == -2.0)
                v_cod_err = -2;
          /*if (v_saldo_contrato == -1){
            v_cod_err = -1;
            v_men_err = "Error en Calculo de saldo del Contrato en el AS400";*/

         // }
        }catch(Exception e){
           v_cod_err = -2;
           v_men_err = e.toString();

        }
        //Si ocurre un error al calcular el saldo del contrato en el AS400
        //retornar -1 y no seguir proceso
        if (v_cod_err == 0){
          //Si el saldo del contrato calculado es negativo o cero retornar un error
          //Sino calcular el saldo de unidades
          if (v_saldo_contrato > 0 ){
            //Llamado a procedimiento que devuelve el saldo de unidades para
            //la fecha, contrato y producto deseado calculando primero los
            //ultimos saldos existentes en TBSALDOS
            //Seleccionar los saldos de unidades y cuenta contingente a la maxima
            //fecha en TBSALDOS
            /*@lineinfo:generated-code*//*@lineinfo:283^13*/

//  ************************************************************
//  #sql { call TBPBD_ULTIMOS_SALDOS(:v_fecha_contrato,             :v_contrato,
//                                              :v_producto,                   :v_max_fecha,
//                                              :v_saldo_unidad_max_fec,   :v_cuenta_conting_max_fec,
//                                              :v_cod_err,              :v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_ULTIMOS_SALDOS( :1  ,              :2  ,\n                                             :3  ,                    :4  ,\n                                             :5  ,    :6  ,\n                                             :7  ,               :8  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_CALCULO_VALOR_UNIDAD",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.DATE);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,v_fecha_contrato);
   __sJT_st.setString(2,v_contrato);
   __sJT_st.setString(3,v_producto);
   __sJT_st.setInt(7,v_cod_err);
   __sJT_st.setString(8,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_max_fecha = (java.sql.Date)__sJT_st.getDate(4);
   v_saldo_unidad_max_fec = __sJT_st.getDouble(5); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cuenta_conting_max_fec = __sJT_st.getDouble(6); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cod_err = __sJT_st.getInt(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(8);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:286^93*/
            //Si hubo error al calcular los ultimos saldos no seguir proceso
            //y retornar el codigo de error sino seguir con el calculo de unidades a la fecha deseada
            if (v_cod_err == 0){
              /*@lineinfo:generated-code*//*@lineinfo:290^15*/

//  ************************************************************
//  #sql v_saldo_unidades = { values (TBFBD_CALC_SALDO_UNIDAD_P(:v_fecha_unidad,
//                                                                           :v_contrato,
//                                                                           :v_producto,
//                                                                           :v_max_fecha,
//                                                                           :v_saldo_unidad_max_fec,
//                                                                           :v_cod_err,
//                                                                           :v_men_err)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_CALC_SALDO_UNIDAD_P( :2  ,\n                                                                          :3  ,\n                                                                          :4  ,\n                                                                          :5  ,\n                                                                          :6  ,\n                                                                          :7  ,\n                                                                          :8  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_CALCULO_VALOR_UNIDAD",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_fecha_unidad);
   __sJT_st.setString(3,v_contrato);
   __sJT_st.setString(4,v_producto);
   __sJT_st.setDate(5,v_max_fecha);
   __sJT_st.setDouble(6,v_saldo_unidad_max_fec);
   __sJT_st.setInt(7,v_cod_err);
   __sJT_st.setString(8,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_saldo_unidades = __sJT_st.getDouble(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_cod_err = __sJT_st.getInt(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(8);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:296^92*/
           System.out.println("v_saldo_unidades "+v_saldo_unidades);
              //Si no hubo error al calcular el saldo de unidades seguir proceso
              //Sino retornar el codigo de error
              if (v_cod_err == 0){
                //Si el saldo de unidades es menor o igual a cero retornar error
                if (v_saldo_unidades > 0 ){
                  v_valor_unidad = v_saldo_contrato/v_saldo_unidades;
                  v_salida[0] = v_valor_unidad;//Valor de la Unidad
                  v_salida[1] = v_saldo_contrato;//Saldo del Contrato
                  v_salida[2] = 0;//Codigo de error
                  //Si v_update = true llamar al proc de insercion a TBSALDOS
                  if (v_update){
                    //Llama al procedimiento que inserta en la tabla TBSALDOS
                    /*@lineinfo:generated-code*//*@lineinfo:310^21*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBSALDOS(:v_producto,       :v_contrato,
//                                                    :v_fecha_contrato, :v_saldo_contrato,
//                                                    null,              null,
//                                                    :v_saldo_unidades, :v_valor_unidad,
//                                                    :v_cod_err,  :v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBSALDOS( :1  ,        :2  ,\n                                                   :3  ,  :4  ,\n                                                  null,              null,\n                                                   :5  ,  :6  ,\n                                                   :7  ,   :8  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"8TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_CALCULO_VALOR_UNIDAD",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,v_producto);
   __sJT_st.setString(2,v_contrato);
   __sJT_st.setString(3,v_fecha_contrato);
   __sJT_st.setDouble(4,v_saldo_contrato);
   __sJT_st.setDouble(5,v_saldo_unidades);
   __sJT_st.setDouble(6,v_valor_unidad);
   __sJT_st.setInt(7,v_cod_err);
   __sJT_st.setString(8,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_cod_err = __sJT_st.getInt(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(8);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:314^87*/
                    //Si este saldo existe en la tabla TBSALDOS se actualiza la tabla
                    if (v_cod_err == -0001){
                      v_cod_err = 0;
                      /*@lineinfo:generated-code*//*@lineinfo:318^23*/

//  ************************************************************
//  #sql { call TBPBD_UPDATE_SALDOS(to_date(:v_fecha_contrato,'RRRR-MM-DD'),
//                                                       :v_producto,
//                                                       :v_contrato,
//                                                       :v_saldo_contrato,
//                                                       null,
//                                                       null,
//                                                       :v_saldo_unidades,
//                                                       :v_valor_unidad,
//                                                       :v_cod_err,
//                                                       :v_men_err) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_UPDATE_SALDOS(to_date( :1  ,'RRRR-MM-DD'),\n                                                      :2  ,\n                                                      :3  ,\n                                                      :4  ,\n                                                     null,\n                                                     null,\n                                                      :5  ,\n                                                      :6  ,\n                                                      :7  ,\n                                                      :8  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_CALCULO_VALOR_UNIDAD",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,v_fecha_contrato);
   __sJT_st.setString(2,v_producto);
   __sJT_st.setString(3,v_contrato);
   __sJT_st.setDouble(4,v_saldo_contrato);
   __sJT_st.setDouble(5,v_saldo_unidades);
   __sJT_st.setDouble(6,v_valor_unidad);
   __sJT_st.setInt(7,v_cod_err);
   __sJT_st.setString(8,v_men_err);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_cod_err = __sJT_st.getInt(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_men_err = (String)__sJT_st.getString(8);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:327^71*/
                      if (v_cod_err == 0){
                        /*@lineinfo:generated-code*//*@lineinfo:329^25*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:329^37*/
                      }
                      else{
                        /*@lineinfo:generated-code*//*@lineinfo:332^25*/

//  ************************************************************
//  #sql { ROLLBACK };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:332^39*/
                        /*@lineinfo:generated-code*//*@lineinfo:333^25*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS( 'EG'
//                                 ,TO_DATE(:v_fecha_contrato,'RRRR-MM-DD')
//                                 , 'VU'
//                                 , :v_men_err
//                                 , :v_log_datos
//                                 , :v_producto
//                                 , null) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS( 'EG'\n                               ,TO_DATE( :1  ,'RRRR-MM-DD')\n                               , 'VU'\n                               ,  :2  \n                               ,  :3  \n                               ,  :4  \n                               , null)\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"10TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_CALCULO_VALOR_UNIDAD",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_fecha_contrato);
   __sJT_st.setString(2,v_men_err);
   __sJT_st.setString(3,v_log_datos);
   __sJT_st.setString(4,v_producto);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:339^39*/
                        /*@lineinfo:generated-code*//*@lineinfo:340^25*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:340^37*/
                      }

                    }// fin si error de constraint
                    else{
                      if (v_cod_err == 0){
                        /*@lineinfo:generated-code*//*@lineinfo:346^25*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:346^37*/
                      }
                      else{
                        /*@lineinfo:generated-code*//*@lineinfo:349^25*/

//  ************************************************************
//  #sql { ROLLBACK };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:349^39*/
                        /*@lineinfo:generated-code*//*@lineinfo:350^25*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS( 'EG'
//                                                                  ,TO_DATE(:v_fecha_contrato,'RRRR-MM-DD')
//                                                                  , 'VU'
//                                                                  , :v_men_err
//                                                                  , :v_log_datos
//                                                                  , :v_producto
//                                                                  , null) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS( 'EG'\n                                                                ,TO_DATE( :1  ,'RRRR-MM-DD')\n                                                                , 'VU'\n                                                                ,  :2  \n                                                                ,  :3  \n                                                                ,  :4  \n                                                                , null)\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"11TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_CALCULO_VALOR_UNIDAD",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_fecha_contrato);
   __sJT_st.setString(2,v_men_err);
   __sJT_st.setString(3,v_log_datos);
   __sJT_st.setString(4,v_producto);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:356^72*/
                        /*@lineinfo:generated-code*//*@lineinfo:357^25*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:357^37*/
                      }
                    }
                  }// fin si quiere actualizar
                }//Saldo de unidades es mayor a cero
                else{
                  v_salida[0] = 0;//Valor de la Unidad
                  v_salida[1] = v_saldo_contrato;//Saldo del Contrato
                  v_salida[2] = -5;//Codigo de error
                  v_cod_err   = -5;
                  v_men_err   = "Saldo de unidades negativa o igual a cero "+v_saldo_unidades;

                }//Saldo de unidades es menor a cero
              }// fin si no hubo error al calcular saldo de unidades
              else{
                v_salida[0] = 0;//Valor de la Unidad
                v_salida[1] = 0;//Saldo del Contrato
                v_salida[2] = v_cod_err;//Codigo de error

              }//hubo error al calcular saldos de unidades
            }//No hubo error al calcular los saldos a maxima fecha
            else{
              v_salida[0] = 0;//Valor de la Unidad
              v_salida[1] = 0;//Saldo del Contrato
              v_salida[2] = v_cod_err;//Codigo de error

            }//Hubo error al calcular los saldos a maxima fecha
          }//Saldo del contrato es positivo
          else{
            v_salida[0] = 0;//Valor de la Unidad
            v_salida[1] = 0;//Saldo del Contrato
            v_salida[2] = -3;//Codigo de error
            v_cod_err   = -3;
            v_men_err   = "Saldo contrato es negativo o igual a cero "+v_saldo_contrato;

          }//Saldo del contrato es negativo o igual a cero retornar error
        }//No hubo error al calcular saldo del contrato en el AS400
        else{
          v_salida[0] = 0;//Valor de la Unidad
          v_salida[1] = 0;//Saldo del Contrato
          v_salida[2] = v_cod_err;//Codigo de error
        }//Hubo error al calcular saldo del contrato en el AS400
      }//No error al borrar los saldos existentes en tbsaldos
      if (v_cod_err != 0){
        //Procedimiento que inserta en la tabla de Log
        /*@lineinfo:generated-code*//*@lineinfo:402^9*/

//  ************************************************************
//  #sql { call TBPBD_INS_TBINTERFACE_LOGS( 'EG'
//                                                  ,TO_DATE(:v_fecha_contrato,'RRRR-MM-DD')
//                                                  , 'VU'
//                                                  , :v_men_err
//                                                  , :v_log_datos
//                                                  , :v_producto
//                                                  , null) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_INS_TBINTERFACE_LOGS( 'EG'\n                                                ,TO_DATE( :1  ,'RRRR-MM-DD')\n                                                , 'VU'\n                                                ,  :2  \n                                                ,  :3  \n                                                ,  :4  \n                                                , null)\n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"12TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_CALCULO_VALOR_UNIDAD",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_fecha_contrato);
   __sJT_st.setString(2,v_men_err);
   __sJT_st.setString(3,v_log_datos);
   __sJT_st.setString(4,v_producto);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:408^56*/
        /*@lineinfo:generated-code*//*@lineinfo:409^9*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:409^21*/
      }
    }catch(Exception e){

      try{
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("c:\\TaxBenefits\\Taxb\\Pasos_logs\\errorpaso5.log", true)));
      DefaultContext.setDefaultContext(null);                                                
      out.println("HP Error=" + e.toString());
      out.close();
      }
      catch(Exception ex)
      {
        
      }

         v_salida[0] = 0;
         v_salida[1] = 0;
         v_salida[2] = -4;
      return v_salida;
   }
    return v_salida;
  }//fin metodo

}/*@lineinfo:generated-code*/