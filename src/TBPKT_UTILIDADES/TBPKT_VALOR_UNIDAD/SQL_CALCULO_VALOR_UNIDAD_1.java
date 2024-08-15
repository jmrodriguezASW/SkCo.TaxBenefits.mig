package TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Date;

import oracle.jdbc.driver.OracleDriver;

import sqlj.runtime.ref.DefaultContext;

/**
 * A java class.
 * <P>
 * @author Asesoftware
 */
public class SQL_CALCULO_VALOR_UNIDAD_1 extends Object {


    public static double TBF_CALCULO_VALOR_UNIDAD_N(String v_fecha_contrato, String v_fecha_unidad, String v_contrato,
                                                    String v_producto, String v_update, int v_retiro, int v_index) {
        boolean v_bupdate = Boolean.valueOf(v_update).booleanValue();
        double v_vunidad[] = new double[3];
        v_vunidad =
            TBF_CALCULO_VALOR_UNIDAD(v_fecha_contrato, v_fecha_unidad, v_contrato, v_producto, v_bupdate, v_retiro);
        return v_vunidad[v_index];
    }

    /*
    Función que devuelve el valor de la unidad para una fecha,
    contrato y producto especifico. Ademas actualiza la tabla
    TBSALDOS si se desea (v_update=true)
    La fecha debe venir en el formato YYYYMMDD.
    El campo retiro debe ser enviado en 1 cuando se desea que se
    calcule el valor de la unidad pero que no se consulte en TBSALDOS
    */
    public static double[] TBF_CALCULO_VALOR_UNIDAD(String v_fecha_contrato, String v_fecha_unidad, String v_contrato,
                                                    String v_producto, boolean v_update, int v_retiro) {

        Date v_max_fecha;
        double v_saldo_contrato = 0;
        double v_saldo_unidades = 1;
        double v_valor_unidad = 0;
        double v_saldo_unidad_max_fec;
        double v_cuenta_conting_max_fec;
        int v_cod_err = 0;
        String v_men_err = " ";
        double v_salida[] = new double[3];
        //String v_log_datos;

        CallableStatement t_cst8i_1 = null;
        Connection con = null;

        try {

            con = new OracleDriver().defaultConnection();

            /*BLOQUE DE LOG*/
            /*
            v_log_datos =
                "Contrato: " + v_contrato + " Producto: " + v_producto + " Fecha Contrato: " + v_fecha_contrato +
                " Fecha Unidad: " + v_fecha_unidad;
            v_men_err = "ASW CONTROL LOG TBF_CALCULO_VALOR_UNIDAD INICIO JAVA VERSION";
            t_cst8i_1 = con.prepareCall("{ call TBPBD_INS_TBINTERFACE_LOGS('EG',SYSDATE,'VU',?,?,?, null)}");
            t_cst8i_1.setString(1, v_men_err);
            t_cst8i_1.setString(2, v_log_datos);
            t_cst8i_1.setString(3, v_producto);
            t_cst8i_1.execute();
            t_cst8i_1.close();
            */

            /*INICIO LLAMADO TBPBD_DEL_TBSALDOS*/
            t_cst8i_1 = con.prepareCall("{ call TBPBD_DEL_TBSALDOS(?,?,?,?,?)}");
            /*SETEO PARAMETROS DEL PROCEDIMIENTO ALMACENADO*/
            t_cst8i_1.setString(1, v_fecha_contrato);
            t_cst8i_1.setString(2, v_contrato);
            t_cst8i_1.setString(3, v_producto);
            t_cst8i_1.registerOutParameter(4, Types.NUMERIC);
            t_cst8i_1.registerOutParameter(5, Types.VARCHAR);
            /*EJECUCIÓN PROCEDIMIENTO ALMACENADO*/
            t_cst8i_1.execute();
            /*CAPTURA DE LOS PARAMETROS DE SALIDA DEL PROCEDIMIENTO ALMACENADO*/
            v_cod_err = t_cst8i_1.getInt(4);
            v_men_err = t_cst8i_1.getString(5);
            t_cst8i_1.close();

            if (v_cod_err != 0) {
                v_salida[0] = -111;
                v_salida[1] = 0;
                v_salida[2] = v_cod_err;
            } //Error al borrar los saldos existentes en tbsaldos
            else {
                //Llamado a un metodo que devuelve el saldo de contrato
                try {
                    v_saldo_contrato = TBCL_AS400.TBF_SALDO_CONTRATO(v_contrato, v_fecha_contrato);

                    if (v_saldo_contrato == -1.0)
                        v_cod_err = -1;
                    else if (v_saldo_contrato == -2.0)
                        v_cod_err = -2;
                } catch (Exception e) {
                    v_cod_err = -2;
                }
                //Si ocurre un error al calcular el saldo del contrato en el AS400
                //retornar -1 y no seguir proceso
                if (v_cod_err == 0) {
                    //Si el saldo del contrato calculado es negativo o cero retornar un error
                    //Sino calcular el saldo de unidades
                    if (v_saldo_contrato > 0) {
                        //Llamado a procedimiento que devuelve el saldo de unidades para
                        //la fecha, contrato y producto deseado calculando primero los
                        //ultimos saldos existentes en TBSALDOS
                        //Seleccionar los saldos de unidades y cuenta contingente a la maxima
                        //fecha en TBSALDOS

                        /*INICIO LLAMADO TBPBD_ULTIMOS_SALDOS*/
                        t_cst8i_1 = con.prepareCall("{ call TBPBD_ULTIMOS_SALDOS(?,?,?,?,?,?,?,?)}");
                        /*SETEO PARAMETROS DEL PROCEDIMIENTO ALMACENADO*/
                        t_cst8i_1.setString(1, v_fecha_contrato);
                        t_cst8i_1.setString(2, v_contrato);
                        t_cst8i_1.setString(3, v_producto);
                        t_cst8i_1.registerOutParameter(4, Types.DATE);
                        t_cst8i_1.registerOutParameter(5, Types.NUMERIC);
                        t_cst8i_1.registerOutParameter(6, Types.NUMERIC);
                        t_cst8i_1.registerOutParameter(7, Types.NUMERIC);
                        t_cst8i_1.registerOutParameter(8, Types.VARCHAR);
                        /*EJECUCIÓN PROCEDIMIENTO ALMACENADO*/
                        t_cst8i_1.execute();
                        /*CAPTURA DE LOS PARAMETROS DE SALIDA DEL PROCEDIMIENTO ALMACENADO*/
                        v_max_fecha = t_cst8i_1.getDate(4);
                        v_saldo_unidad_max_fec = t_cst8i_1.getDouble(5);
                        v_cuenta_conting_max_fec = t_cst8i_1.getDouble(6);
                        v_cod_err = t_cst8i_1.getInt(7);
                        v_men_err = t_cst8i_1.getString(8);

                        t_cst8i_1.close();

                        //Si hubo error al calcular los ultimos saldos no seguir proceso
                        //y retornar el codigo de error sino seguir con el calculo de unidades a la fecha deseada
                        if (v_cod_err == 0) {
                            /*INICIO LLAMADO TBFBD_CALC_SALDO_UNIDAD_P*/
                            t_cst8i_1 = con.prepareCall("{ ? = call TBFBD_CALC_SALDO_UNIDAD_P(?,?,?,?,?,?,?)}");
                            /*SETEO PARAMETROS DEL PROCEDIMIENTO ALMACENADO*/
                            t_cst8i_1.registerOutParameter(1, Types.NUMERIC);
                            t_cst8i_1.setString(2, v_fecha_contrato);
                            t_cst8i_1.setString(3, v_contrato);
                            t_cst8i_1.setString(4, v_producto);
                            t_cst8i_1.setDate(5, v_max_fecha != null ? new java.sql.Date(v_max_fecha.getTime()) : null);
                            t_cst8i_1.setDouble(6, v_saldo_unidad_max_fec);
                            t_cst8i_1.registerOutParameter(7, Types.NUMERIC);
                            t_cst8i_1.registerOutParameter(8, Types.VARCHAR);
                            /*EJECUCIÓN PROCEDIMIENTO ALMACENADO*/
                            t_cst8i_1.execute();
                            /*CAPTURA DE LOS PARAMETROS DE SALIDA DEL PROCEDIMIENTO ALMACENADO*/
                            v_saldo_unidades = t_cst8i_1.getDouble(1);

                            v_cod_err = t_cst8i_1.getInt(7);
                            v_men_err = t_cst8i_1.getString(8);
                            t_cst8i_1.close();

                            //Si no hubo error al calcular el saldo de unidades seguir proceso
                            //Sino retornar el codigo de error
                            if (v_cod_err == 0) {
                                //Si el saldo de unidades es menor o igual a cero retornar error
                                if (v_saldo_unidades > 0) {
                                    v_valor_unidad = v_saldo_contrato / v_saldo_unidades;
                                    v_salida[0] = v_valor_unidad; //Valor de la Unidad
                                    v_salida[1] = v_saldo_contrato; //Saldo del Contrato
                                    v_salida[2] = 0; //Codigo de error
                                    //Si v_update = true llamar al proc de insercion a TBSALDOS
                                    if (v_update) {
                                        //Llama al procedimiento que inserta en la tabla TBSALDOS

                                        /*INICIO LLAMADO TBPBD_INS_TBSALDOS*/
                                        t_cst8i_1 =
                                            con.prepareCall("{ call TBPBD_INS_TBSALDOS(?,?,?,?,null,null,?,?,?,?)}");
                                        /*SETEO PARAMETROS DEL PROCEDIMIENTO ALMACENADO*/
                                        t_cst8i_1.setString(1, v_fecha_contrato);
                                        t_cst8i_1.setString(2, v_contrato);
                                        t_cst8i_1.setString(3, v_producto);
                                        t_cst8i_1.setDouble(4, v_saldo_contrato);
                                        t_cst8i_1.setDouble(5, v_saldo_unidades);
                                        t_cst8i_1.setDouble(6, v_valor_unidad);
                                        t_cst8i_1.registerOutParameter(7, Types.NUMERIC);
                                        t_cst8i_1.registerOutParameter(8, Types.VARCHAR);
                                        /*EJECUCIÓN PROCEDIMIENTO ALMACENADO*/
                                        t_cst8i_1.execute();
                                        /*CAPTURA DE LOS PARAMETROS DE SALIDA DEL PROCEDIMIENTO ALMACENADO*/
                                        v_cod_err = t_cst8i_1.getInt(7);
                                        v_men_err = t_cst8i_1.getString(8);
                                        t_cst8i_1.close();

                                        //Si este saldo existe en la tabla TBSALDOS se actualiza la tabla
                                        if (v_cod_err == -0001) {

                                            String sql =
                                                "UPDATE tbsaldos " + "SET sal_saldo_contrato = ?, " +
                                                "    sal_saldo_contrato_disponible = null, " +
                                                "    sal_saldo_cuenta_contingente = null, " +
                                                "    sal_saldo_unidades = ?, " + "    sal_valor_unidad = ? " +
                                                "WHERE sal_con_pro_codigo = ? " + "  AND sal_con_numero = ? " +
                                                "  AND TRUNC(sal_fecha) = TO_DATE(?, 'YYYY-MM-DD')";

                                            PreparedStatement pstmt = null;

                                            pstmt = con.prepareStatement(sql);

                                            // Asigna los valores a los parámetros
                                            pstmt.setDouble(1, v_saldo_contrato);
                                            pstmt.setDouble(2, v_saldo_unidades);
                                            pstmt.setDouble(3, v_valor_unidad);
                                            pstmt.setString(4, v_producto);
                                            pstmt.setString(5, v_contrato);
                                            pstmt.setString(6, v_fecha_contrato);

                                            // Ejecuta la actualización
                                            pstmt.executeUpdate();
                                            v_cod_err = 0;
                                        } // fin si error de constraint
                                    } // fin si quiere actualizar
                                } //Saldo de unidades es mayor a cero
                                else {
                                    v_salida[0] = 0; //Valor de la Unidad
                                    v_salida[1] = v_saldo_contrato; //Saldo del Contrato
                                    v_salida[2] = -5; //Codigo de error
                                } //Saldo de unidades es menor a cero
                            } // fin si no hubo error al calcular saldo de unidades
                            else {
                                v_salida[0] = 0; //Valor de la Unidad
                                v_salida[1] = 0; //Saldo del Contrato
                                v_salida[2] = v_cod_err; //Codigo de error
                            } //hubo error al calcular saldos de unidades
                        } //No hubo error al calcular los saldos a maxima fecha
                        else {
                            v_salida[0] = 0; //Valor de la Unidad
                            v_salida[1] = 0; //Saldo del Contrato
                            v_salida[2] = v_cod_err; //Codigo de error
                        } //Hubo error al calcular los saldos a maxima fecha
                    } //Saldo del contrato es positivo
                    else {
                        v_salida[0] = 0; //Valor de la Unidad
                        v_salida[1] = 0; //Saldo del Contrato
                        v_salida[2] = -3; //Codigo de error
                    } //Saldo del contrato es negativo o igual a cero retornar error
                } //No hubo error al calcular saldo del contrato en el AS400
                else {
                    v_salida[0] = 0; //Valor de la Unidad
                    v_salida[1] = 0; //Saldo del Contrato
                    v_salida[2] = v_cod_err; //Codigo de error
                } //Hubo error al calcular saldo del contrato en el AS400
            } //No error al borrar los saldos existentes en tbsaldos

        } catch (Exception e) {
            try {
                PrintWriter out =
                    new PrintWriter(new BufferedWriter(new FileWriter("c:\\TaxBenefits\\Taxb\\Pasos_logs\\error.log",
                                                                      true)));
                DefaultContext.setDefaultContext(null);
                out.println("HP Error=" + e.toString());
                out.close();
            } catch (Exception ex) {

            }
            v_salida[0] = 0;
            v_salida[1] = 0;
            v_salida[2] = -4;
            return v_salida;
        }
        return v_salida;
    } //fin metodo
}//fin de la clase

