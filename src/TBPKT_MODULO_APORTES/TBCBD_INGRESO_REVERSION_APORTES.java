package TBPKT_MODULO_APORTES;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

/*
Modificacion:
Se elimina el import debido a que la conexion
al AS400 se hace desde la base de datos
*/
//import TBPKT_UTILIDADES.TBPKT_AS400_APORTES.*;
import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.*;
import TBPKT_IAS.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

import java.sql.*;
import org.apache.log4j.Logger;

/**
*<font face='Verdana' size='2' color='#324395'>
*INTERFACE DE APORTES COMPRENDIDA POR UN PROCEDIMIENTO AUTOMATICO
*QUE SIRVE PARA CARGAR Y/O REVERSAR APORTES
*DESDE MFUND HACIA TAXB <BR>
*Esta clase se encargará de realizar el proceso de Carga de aportes desde
*mfund hacia Tax y la reversión de aportes en Tax
*@author      AlfaGL
*@version     1.0 junio/11/2001
*
*/
public class TBCBD_INGRESO_REVERSION_APORTES extends Object
{

	static Logger myLogger = Logger.getLogger(TBCBD_INGRESO_REVERSION_APORTES.class.getName());

   //--
   public static String carga_final = new String(" ");
   /**
   *<font face='Verdana' size='2' color='#324395'>
   * PROCEDIMIENTO PRINCIPAL DE LA INTERFACE QUE SIRVE PARA
   * GENERAR LA SECUENCIA DEL PROCESO DE INGRESO Y DE
   *  REVERSION DE APORTES, SE ENCARGA VERIFICAR INICIO DEL
   * PROCESO, DE VALIDAR TOTALES ENVIADOS POR MFUND, DE
   * REALIZAR LA CARGA TEMPORAL SOBRE TAX, DE REALIZAR BORRADO SOBRE
   * LA TABLA DE APORTES DEL AS/400, DE ACTUALIZAR EL PASO DOS DE ESTA
   * INTERFACE SOBRE LA TABLA DE CONTROL DEL AS/400, DE INSERTAR APORTES
   * SOBRE LA TABLA DEFINITIVA DE APORTES Y DE REALIZAR REVERSIONES
   * @param          ninguno
    * @return         ninguno
   *</font>
   */
   public static String TBFL_Inicio()
   {
       Connection v_conexion_taxb   =   null;
      try
      {
         String estado = " ";
         int contador_repeticiones = 0;
         //Realizo Conexión Sobre Tax
         v_conexion_taxb =   DataSourceWrapper.getInstance().getConnection();
         
         //INICIO DEFINICION DE VARIABLES
         int total_registros_tax    = 0;
         double total_ingresos_tax  = 0;
         double total_rtos_tax      = 0;
         double total_cc_tax        = 0;
         int total_registros_mf     = 0;
         double total_ingresos_mf   = 0;
         double total_rtos_mf       = 0;
         double total_cc_mf         = 0;
         String datos_hoy           = " ";
         int fecha_cargue           = 20010816;
         //FIN DEFINICION DE VARIABLES
         //REALIZO VALIDACION SOBRE DATOS A CARGAR PARA EL DIA DE HOY
         /**
         * LLAMADO A UN STORED PROCEDURE QUE VERIFICA SOBRE
         * LA TABLA DE CONTROL DEL AS/400 QUE EL PASO UNO
         * DE LA INTERFACE SE ENCUANTRE FINALIZADO, PARA ASI
         * PODER INICIAR EL PROCESO EN TAXB.
         * IN NINGUNO
         * OUT INDICADOR DE INICIO DE PROCESO(v O f), FECHA PARA
         *      LA CUAL SE REALIZARÁ EL CARGUE(RRRRMMDD)
          */
         CallableStatement t_cst8i_0 = v_conexion_taxb.prepareCall("{ call TBPBD_empezar_cargue(?,?) }");
         t_cst8i_0.registerOutParameter(1,Types.VARCHAR);
         t_cst8i_0.registerOutParameter(2,Types.NUMERIC);
         t_cst8i_0.execute();
         datos_hoy     = t_cst8i_0.getString(1);
         fecha_cargue  = t_cst8i_0.getInt(2);
         t_cst8i_0.close();
         myLogger.debug("Validación para iniciar cargue "+datos_hoy);
        if(datos_hoy.equals("V"))
        {

          //calculo la información de totales enviados por Mfund
          /**
          * LLAMADO A UN STORED PROCEDURE QUE SE ENCARGA DE VERIFICAR
          * LA INFORMACION ENVIADA EN EL REGISTRO DE TOTALES CON
          * RESPECTO A LA ENVIADA EN LOS REGISTROS DE DETALLE SOBRE
          * LA TABLA DE APORTES DEL AS/400.
          * IN NINGUNO
          * OUT TOTAL DE REGISTROS A CARGAR Y A REVERSAR, VALOR TOTAL EN
          *     INGRESOS, VALOR TOTAL EN RENDIMIENTOS, VALOR TOTAL EN
           *     CUENTA CONTINGENTE Y ESTADO DE VALIDACION Y/O PROCESO(BIEN, OK 0 ERROR)
          */
          CallableStatement t_cst8i_1 = v_conexion_taxb.prepareCall("{ call TBPBD_Valores_Totales_Aportes(?,?,?,?,?) }");
          t_cst8i_1.registerOutParameter(1,Types.NUMERIC);
          t_cst8i_1.registerOutParameter(2,Types.NUMERIC);
          t_cst8i_1.registerOutParameter(3,Types.NUMERIC);
          t_cst8i_1.registerOutParameter(4,Types.NUMERIC);
          t_cst8i_1.registerOutParameter(5,Types.VARCHAR);
          t_cst8i_1.execute();
          total_registros_mf      = t_cst8i_1.getInt(1);
          total_ingresos_mf       = t_cst8i_1.getDouble(2);
          total_rtos_mf           = t_cst8i_1.getDouble(3);
          total_cc_mf             = t_cst8i_1.getDouble(4);
          estado                  = t_cst8i_1.getString(5);
          t_cst8i_1.close();
          myLogger.debug("Validación  de totales TBPBD_Valores_Totales_Aportes "+estado);
          if(estado.equals("BIEN")||estado.equals("OK"))
          {
             /**
             *do while infinito que sirve para relaizar los diferentes
             *intentos de realización de la carga y/o reversión
             */
             do
             {
                /**
                * reliazo la carga Temporal Desde Mfund hacia la tabla temporal de aportes sobre Tax.
                * Parametros de Entrada El número de Registros Enviados Por Mfund
                *             y la fecha de cargue
                * Parametros de Salida Total de Registros Cargados,Total de Ingresos
                *            enviados, Total de Rtos enviados, Total de C C enviado Y
                *            ESTADO FINAL DE PROCESO
                 *
                */
                CallableStatement t_cst8i_2 = v_conexion_taxb.prepareCall("{ call TBPBD_carga_temporal(?,?,?,?,?,?,?) }");
                t_cst8i_2.registerOutParameter(3,Types.NUMERIC);
                t_cst8i_2.registerOutParameter(4,Types.NUMERIC);
                t_cst8i_2.registerOutParameter(5,Types.NUMERIC);
                t_cst8i_2.registerOutParameter(6,Types.NUMERIC);
                t_cst8i_2.registerOutParameter(7,Types.VARCHAR);
                t_cst8i_2.setInt(1,fecha_cargue);
                t_cst8i_2.setInt(2,total_registros_mf);
                t_cst8i_2.execute();
                total_registros_tax  = t_cst8i_2.getInt(3);
                total_ingresos_tax   = t_cst8i_2.getDouble(4);
                total_rtos_tax       = t_cst8i_2.getDouble(5);
                total_cc_tax         = t_cst8i_2.getDouble(6);
                estado               = t_cst8i_2.getString(7);
                t_cst8i_2.close();
                myLogger.debug("Carga temporal TBPBD_carga_temporal "+estado);
                if(estado.equals("ERROR"))
                {
                   /**
                  *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
                  *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
                  *DURANTE EL PROCESO
                  *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
                  *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
                  *OUT NINGUNO
                  */
                  myLogger.debug("va al log carga temporal invalida");
                  String trs = new String(" ");
                  trs        = null;
                  CallableStatement t_cst8i_33 = v_conexion_taxb.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
                  t_cst8i_33.setString(1,"IMPOSIBLE INICIAR LA CARGA/REVERSION DE APORTES, LOS TOTALES DE MFUND NO SON VALIDOS");
                  t_cst8i_33.setString(2,"REGISTROS DE INICO DE CARGA INVALIDO");
                  t_cst8i_33.setString(3," ");
                  t_cst8i_33.setString(4,trs);
                  t_cst8i_33.setInt(5,fecha_cargue);
                  t_cst8i_33.execute();
                  t_cst8i_33.close();
                  v_conexion_taxb.commit();
                  myLogger.debug("realiza commit");
                  return carga_final = "ERROR ggfgf";
                }
                else if(estado.equals("BIEN"))
                     {
                       /**
                       * LLAMADO A UN STORED PROCEDURE QUE SE ENCARGA DE ACTUALIZAR EL
                       * PASO 2 DE ESTA INTERFACE EN ESTADO OK SOBRE LA TABLA DE CONTROL
                       * Y DE BORRAR LA TABLA DE APORTES DEL AS/400
                       *IN FECHA EN LA QUE SE REALIZA EL CARGUE
                       *OUT ESTADO FINAL DEL PROCESO DE ACTAULIZACION Y DE BORRADO
                        */
                       v_conexion_taxb.commit();
                       CallableStatement t_cst8i_16 = v_conexion_taxb.prepareCall("{ call TBPBD_ACTUALIZABORRA_MFUND(?,?) }");
                       t_cst8i_16.registerOutParameter(2,Types.VARCHAR);
                       t_cst8i_16.setInt(1,fecha_cargue);
                       t_cst8i_16.execute();
                       String TBPBD_ACTUALIZABORRA_MFUND = t_cst8i_16.getString(2);
                       t_cst8i_16.close();
                       myLogger.debug("Actualizar y borrar as400 TBPBD_ACTUALIZABORRA_MFUND "+TBPBD_ACTUALIZABORRA_MFUND);
                       /*v_conexion_taxb.commit();
                       String TBPBD_ACTUALIZABORRA_MFUND = "BIEN";*/
                       if(!TBPBD_ACTUALIZABORRA_MFUND.equalsIgnoreCase("BIEN"))
                       {
                          /**
                         *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
                         *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
                         *DURANTE EL PROCESO
                         *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
                         *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
                         *OUT NINGUNO
                         */
                         String trs = new String(" ");
                         trs        = null;
                         CallableStatement t_cst8i_333 = v_conexion_taxb.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
                         t_cst8i_333.setString(1,"NO SE PUDO REALIZAR EL BORRADO DE LA TABLA AJKLCPCP");
                         t_cst8i_333.setString(2,"REGISTROS DE BORRADO INVALIDO");
                         t_cst8i_333.setString(3," ");
                         t_cst8i_333.setString(4,trs);
                         t_cst8i_333.setInt(5,fecha_cargue);
                         t_cst8i_333.execute();
                         t_cst8i_333.close();
                         v_conexion_taxb.commit();
                         return carga_final = "ERROR";
                       }
                       /**
                       *En esta parte ya he relaizado la carga de la tabla tmp del
                       *AS400 sobre la tabla tmp de tax, siguen validaciones
                       *Ahora realizare las validaciones necesarias para cargar definitivamente
                       *los aportes sobre TAX
                       *PRIMERO REGISTRO EN EL LOG AQUELLOS TOTALES QUE SE ENCUENTRAN INVALIDO
                       *SEGUNDO VALIDO INFORMACION DE CADA APORTE ANTES DE CARGARLO DEFINITIVAMENTE
                       */
                       TBPL_VALIDA_TOTALES(total_registros_mf,total_registros_tax,total_ingresos_mf,total_ingresos_tax,total_rtos_mf,total_rtos_tax,
                            total_cc_mf,total_cc_tax,v_conexion_taxb,fecha_cargue);
                       myLogger.debug("Se validaron totales TBPL_VALIDA_TOTALES ");
                       TBPL_Validaciones(v_conexion_taxb,fecha_cargue);
                       myLogger.debug("Realizo TBPL_Validaciones");
                       contador_repeticiones=3;
                     }
                if(contador_repeticiones==2)
                {
                   /**
                   *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
                   *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
                   *DURANTE EL PROCESO
                   *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
                   *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
                   *OUT NINGUNO
                   */
                   String trs = new String(" ");
                   trs        = null;
                   CallableStatement t_cst8i_193 = v_conexion_taxb.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
                   t_cst8i_193.setString(1,"IMPOSIBLE INICIAR CON EL PROCESO DE INGRESO Y REVERSION");
                   t_cst8i_193.setString(2,"PROCESO AUTOMATICO NO REALIZADO");
                   t_cst8i_193.setString(3," ");
                   t_cst8i_193.setString(4,trs);
                   t_cst8i_193.setInt(5,fecha_cargue);
                   t_cst8i_193.execute();
                   t_cst8i_193.close();
                   v_conexion_taxb.commit();
                   return carga_final = "ERROR";
                 }
                 contador_repeticiones++;
             }while(!estado.equals("BIEN") && contador_repeticiones<3);
          }//si estado.
          else if(estado.substring(0,5).equals("ERROR"))
                {
                   /**
                   *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
                   *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
                   *DURANTE EL PROCESO
                   *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
                   *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
                   *OUT NINGUNO
                   */
                   String trs = new String(" ");
                   trs        = null;
                   CallableStatement t_cst8i_3 = v_conexion_taxb.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
                   t_cst8i_3.setString(1,"IMPOSIBLE INICIAR LA CARGA/REVERSION DE APORTES, LOS TOTALES DE MFUND NO SON VALIDOS");
                   t_cst8i_3.setString(2,"REGISTROS DE INICO DE CARGA INVALIDO");
                   t_cst8i_3.setString(3," ");
                   t_cst8i_3.setString(4,trs);
                   t_cst8i_3.setInt(5,fecha_cargue);
                   t_cst8i_3.execute();
                   t_cst8i_3.close();
                   v_conexion_taxb.commit();
                   return carga_final = "ERROR";
                }
        }//fin datos_hoy
        else if(datos_hoy.equals("E0"))
             {
                /**
                *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
                *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
                *DURANTE EL PROCESO
                *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
                *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
                *OUT NINGUNO
                */
                String trs = new String(" ");
                trs        = null;
                CallableStatement t_cst8i_3 = v_conexion_taxb.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
                t_cst8i_3.setString(1,"IMPOSIBLE INICIAR LA CARGA/REVERSION DE APORTES, EXISTE MAS DE UN REGISTRO EN LA TABLA DE CONTROL PARA INICIAR CARGA");
                t_cst8i_3.setString(2,"REGISTROS DE INICO DE CARGA INVALIDO");
                t_cst8i_3.setString(3," ");
                t_cst8i_3.setString(4,trs);
                t_cst8i_3.setInt(5,fecha_cargue);
                t_cst8i_3.execute();
                t_cst8i_3.close();
             }
             else if(datos_hoy.equals("E2"))
                  {
                     /**
                     *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
                     *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
                     *DURANTE EL PROCESO
                     *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
                     *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
                     *OUT NINGUNO
                     */
                     String trs = new String(" ");
                     trs        = null;
                     CallableStatement t_cst8i_3 = v_conexion_taxb.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
                     t_cst8i_3.setString(1,"IMPOSIBLE INICIAR LA CARGA/REVERSION DE APORTES, LA CARGA YA FUE REALIZADA");
                     t_cst8i_3.setString(2,"REGISTROS DE INICO DE CARGA INVALIDO");
                     t_cst8i_3.setString(3," ");
                     t_cst8i_3.setString(4,trs);
                     t_cst8i_3.setInt(5,fecha_cargue);
                     t_cst8i_3.execute();
                     t_cst8i_3.close();
                     v_conexion_taxb.commit();
                     return carga_final = "ERROR";
                  }
                  else if(datos_hoy.equals("E1"))
                  {
                     /**
                    *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
                    *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
                    *DURANTE EL PROCESO
                    *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
                    *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
                    *OUT NINGUNO
                    */
                    String trs = new String(" ");
                    trs        = null;
                    int v_fecha = 0;
                    Statement t_st = v_conexion_taxb.createStatement();
                    ResultSet t_rs    = t_st.executeQuery("SELECT TO_NUMBER(TO_CHAR(TRUNC(SYSDATE),'RRRRMMDD')) FROM DUAL ");
                    //mientras se encutren datos
                    while(t_rs.next())
                    {
                       v_fecha= t_rs.getInt(1);
                    }
                    t_rs.close();
                    t_st.close();
                   CallableStatement t_cst8i_3 = v_conexion_taxb.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
                   t_cst8i_3.setString(1,"ERROR NO SE ENCUENTRA REGISTRO DEL PASO 1 DE APORTES EN Y.");
                   t_cst8i_3.setString(2,"REGISTROS DE INICO DE CARGA INVALIDO");
                   t_cst8i_3.setString(3," ");
                   t_cst8i_3.setString(4,trs);
                   t_cst8i_3.setInt(5,v_fecha);
                   t_cst8i_3.execute();
                   t_cst8i_3.close();
                   v_conexion_taxb.commit();
                   return carga_final = "ERROR";
                }
                v_conexion_taxb.commit();
                return carga_final = "BIEN";
      }//fin try
      catch(Exception ex)
      {
          myLogger.error("Exception en TBCBD_INGRESO_REVERSION_APORTES "+ ex);
          return carga_final = "ERROR";
      }
       finally{
           try{
                   DataSourceWrapper.closeConnection(v_conexion_taxb);                  
           }catch(SQLException sqlEx){
                   System.out.println(sqlEx.toString());
           }
       }      
   }//fin Principal
//--
/**
*<font face='Verdana' size='2' color='#324395'>
* PROCEDIMIENTO PRINCIPAL DE LA INTERFACE QUE SIRVE PARA
* GENERAR LA SECUENCIA DEL PROCESO DE INGRESO Y DE
* REVERSION DE APORTES, SE ENCARGA VERIFICAR INICIO DEL
* PROCESO, DE VALIDAR TOTALES ENVIADOS POR MFUND, DE
* REALIZAR LA CARGA TEMPORAL SOBRE TAX, DE REALIZAR BORRADO SOBRE
* LA TABLA DE APORTES DEL AS/400, DE ACTUALIZAR EL PASO DOS DE ESTA
* INTERFACE SOBRE LA TABLA DE CONTROL DEL AS/400, DE INSERTAR APORTES
* SOBRE LA TABLA DEFINITIVA DE APORTES Y DE REALIZAR REVERSIONES
* @param          ninguno
* @return         ninguno
*</font>
*/
public static void TBPL_Inicio()
{
    Connection v_conexion_taxb  =   null;
try
{
  String estado = " ";
  int contador_repeticiones = 0;
  //Realizo Conexión Sobre Tax
  v_conexion_taxb   =   DataSourceWrapper.getInstance().getConnection();
  //INICIO DEFINICION DE VARIABLES
  int total_registros_tax    = 0;
  double total_ingresos_tax  = 0;
  double total_rtos_tax      = 0;
  double total_cc_tax        = 0;
  int total_registros_mf     = 0;
  double total_ingresos_mf   = 0;
  double total_rtos_mf       = 0;
  double total_cc_mf         = 0;
  String datos_hoy           = " ";
  int fecha_cargue           = 0;
   //FIN DEFINICION DE VARIABLES
  //REALIZO VALIDACION SOBRE DATOS A CARGAR PARA EL DIA DE HOY
  /**
  * LLAMADO A UN STORED PROCEDURE QUE VERIFICA SOBRE
  * LA TABLA DE CONTROL DEL AS/400 QUE EL PASO UNO
  * DE LA INTERFACE SE ENCUANTRE FINALIZADO, PARA ASI
  * PODER INICIAR EL PROCESO EN TAXB.
  * IN NINGUNO
  * OUT INDICADOR DE INICIO DE PROCESO(v O f), FECHA PARA
  *      LA CUAL SE REALIZARÁ EL CARGUE(RRRRMMDD)
  */
  CallableStatement t_cst8i_0 = v_conexion_taxb.prepareCall("{ call TBPBD_empezar_cargue(?,?) }");
  t_cst8i_0.registerOutParameter(1,Types.VARCHAR);
  t_cst8i_0.registerOutParameter(2,Types.NUMERIC);
  t_cst8i_0.execute();
  datos_hoy     = t_cst8i_0.getString(1);
  fecha_cargue  = t_cst8i_0.getInt(2);
  t_cst8i_0.close();
  if(datos_hoy.equals("V"))
  {
    //calculo la información de totales enviados por Mfund
    /**
    * LLAMADO A UN STORED PROCEDURE QUE SE ENCARGA DE VERIFICAR
    * LA INFORMACION ENVIADA EN EL REGISTRO DE TOTALES CON
    * RESPECTO A LA ENVIADA EN LOS REGISTROS DE DETALLE SOBRE
    * LA TABLA DE APORTES DEL AS/400.
    * IN NINGUNO
    * OUT TOTAL DE REGISTROS A CARGAR Y A REVERSAR, VALOR TOTAL EN
    *     INGRESOS, VALOR TOTAL EN RENDIMIENTOS, VALOR TOTAL EN
    *     CUENTA CONTINGENTE Y ESTADO DE VALIDACION Y/O PROCESO(BIEN, OK 0 ERROR)
    */
    CallableStatement t_cst8i_1 = v_conexion_taxb.prepareCall("{ call TBPBD_Valores_Totales_Aportes(?,?,?,?,?) }");
    t_cst8i_1.registerOutParameter(1,Types.NUMERIC);
    t_cst8i_1.registerOutParameter(2,Types.NUMERIC);
    t_cst8i_1.registerOutParameter(3,Types.NUMERIC);
    t_cst8i_1.registerOutParameter(4,Types.NUMERIC);
    t_cst8i_1.registerOutParameter(5,Types.VARCHAR);
    t_cst8i_1.execute();
    total_registros_mf      = t_cst8i_1.getInt(1);
    total_ingresos_mf       = t_cst8i_1.getDouble(2);
    total_rtos_mf           = t_cst8i_1.getDouble(3);
    total_cc_mf             = t_cst8i_1.getDouble(4);
    estado                  = t_cst8i_1.getString(5);
    t_cst8i_1.close();
    if(estado.equals("BIEN")||estado.equals("OK"))
    //SI LOS TATALES CONCUERDAN
     {
     /**
     *do while infinito que sirve para relaizar los diferentes
     *intentos de realización de la carga y/o reversión
     */
       do{
       /**
       * reliazo la carga Temporal Desde Mfund hacia la tabla temporal de aportes sobre Tax.
       * Parametros de Entrada El número de Registros Enviados Por Mfund
       *             y la fecha de cargue
       * Parametros de Salida Total de Registros Cargados,Total de Ingresos
       *            enviados, Total de Rtos enviados, Total de C C enviado Y
       *            ESTADO FINAL DE PROCESO
       *
       */
        CallableStatement t_cst8i_2 = v_conexion_taxb.prepareCall("{ call TBPBD_carga_temporal(?,?,?,?,?,?,?) }");
        t_cst8i_2.registerOutParameter(3,Types.NUMERIC);
        t_cst8i_2.registerOutParameter(4,Types.NUMERIC);
        t_cst8i_2.registerOutParameter(5,Types.NUMERIC);
        t_cst8i_2.registerOutParameter(6,Types.NUMERIC);
        t_cst8i_2.registerOutParameter(7,Types.VARCHAR);
        t_cst8i_2.setInt(1,fecha_cargue);
        t_cst8i_2.setInt(2,total_registros_mf);
        t_cst8i_2.execute();
        total_registros_tax  = t_cst8i_2.getInt(3);
        total_ingresos_tax   = t_cst8i_2.getDouble(4);
        total_rtos_tax       = t_cst8i_2.getDouble(5);
        total_cc_tax         = t_cst8i_2.getDouble(6);
        estado               = t_cst8i_2.getString(7);
        t_cst8i_2.close();
        if(estado.equals("ERROR"))
         {
           /**
           *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
           *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
           *DURANTE EL PROCESO
           *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
           *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
           *OUT NINGUNO
           */
           String trs = new String(" ");
           trs        = null;
           CallableStatement t_cst8i_33 = v_conexion_taxb.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
           t_cst8i_33.setString(1,"IMPOSIBLE INICIAR LA CARGA/REVERSION DE APORTES, LOS TOTALES DE MFUND NO SON VALIDOS");
           t_cst8i_33.setString(2,"REGISTROS DE INICO DE CARGA INVALIDO");
           t_cst8i_33.setString(3," ");
           t_cst8i_33.setString(4,trs);
           t_cst8i_33.setInt(5,fecha_cargue);
           t_cst8i_33.execute();
           t_cst8i_33.close();
         }
        else if(estado.equals("BIEN"))
         {
          /**
          * LLAMADO A UN STORED PROCEDURE QUE SE ENCARGA DE ACTUALIZAR EL
          * PASO 2 DE ESTA INTERFACE EN ESTADO OK SOBRE LA TABLA DE CONTROL
          * Y DE BORRAR LA TABLA DE APORTES DEL AS/400
          *IN FECHA EN LA QUE SE REALIZA EL CARGUE
          *OUT ESTADO FINAL DEL PROCESO DE ACTAULIZACION Y DE BORRADO
          */
          v_conexion_taxb.commit();
          CallableStatement t_cst8i_16 = v_conexion_taxb.prepareCall("{ call TBPBD_ACTUALIZABORRA_MFUND(?,?) }");
          t_cst8i_16.registerOutParameter(2,Types.VARCHAR);
          t_cst8i_16.setInt(1,fecha_cargue);
          t_cst8i_16.execute();
          String TBPBD_ACTUALIZABORRA_MFUND = t_cst8i_16.getString(2);
          t_cst8i_16.close();
          if(!TBPBD_ACTUALIZABORRA_MFUND.equalsIgnoreCase("BIEN"))
           {
             /**
             *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
             *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
             *DURANTE EL PROCESO
             *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
             *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
             *OUT NINGUNO
             */
             String trs = new String(" ");
             trs        = null;
             CallableStatement t_cst8i_333 = v_conexion_taxb.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
             t_cst8i_333.setString(1,"NO SE PUDO REALIZAR EL BORRADO DE LA TABLA AJKLCPCP");
             t_cst8i_333.setString(2,"REGISTROS DE BORRADO INVALIDO");
             t_cst8i_333.setString(3," ");
             t_cst8i_333.setString(4,trs);
             t_cst8i_333.setInt(5,fecha_cargue);
             t_cst8i_333.execute();
             t_cst8i_333.close();
           }
           /**
           *En esta parte ya he relaizado la carga de la tabla tmp del
           *AS400 sobre la tabla tmp de tax, siguen validaciones
           *Ahora realizare las validaciones necesarias para cargar definitivamente
           *los aportes sobre TAX
           *PRIMERO REGISTRO EN EL LOG AQUELLOS TOTALES QUE SE ENCUENTRAN INVALIDO
           *SEGUNDO VALIDO INFORMACION DE CADA APORTE ANTES DE CARGARLO DEFINITIVAMENTE
           */
          TBPL_VALIDA_TOTALES(total_registros_mf,total_registros_tax,total_ingresos_mf,total_ingresos_tax,total_rtos_mf,total_rtos_tax,
                              total_cc_mf,total_cc_tax,v_conexion_taxb,fecha_cargue);
          TBPL_Validaciones(v_conexion_taxb,fecha_cargue);
          contador_repeticiones=3;
         }
        if(contador_repeticiones==2)
         {
           /**
           *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
           *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
           *DURANTE EL PROCESO
           *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
           *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
           *OUT NINGUNO
           */
           String trs = new String(" ");
           trs        = null;
           CallableStatement t_cst8i_193 = v_conexion_taxb.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
           t_cst8i_193.setString(1,"IMPOSIBLE INICIAR CON EL PROCESO DE INGRESO Y REVERSION");
           t_cst8i_193.setString(2,"PROCESO AUTOMATICO NO REALIZADO");
           t_cst8i_193.setString(3," ");
           t_cst8i_193.setString(4,trs);
           t_cst8i_193.setInt(5,fecha_cargue);
           t_cst8i_193.execute();
           t_cst8i_193.close();
           break;
         }
        contador_repeticiones++;
      }while(!estado.equals("BIEN") && contador_repeticiones<3);
    }//si estado.
   else if(estado.substring(0,5).equals("ERROR"))
    {
      /**
      *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
      *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
      *DURANTE EL PROCESO
      *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
      *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
      *OUT NINGUNO
      */
      String trs = new String(" ");
      trs        = null;
      CallableStatement t_cst8i_3 = v_conexion_taxb.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
      t_cst8i_3.setString(1,"IMPOSIBLE INICIAR LA CARGA/REVERSION DE APORTES, LOS TOTALES DE MFUND NO SON VALIDOS");
      t_cst8i_3.setString(2,"REGISTROS DE INICO DE CARGA INVALIDO");
      t_cst8i_3.setString(3," ");
      t_cst8i_3.setString(4,trs);
      t_cst8i_3.setInt(5,fecha_cargue);
      t_cst8i_3.execute();
      t_cst8i_3.close();
    }
 }//fin datos_hoy
 else if(datos_hoy.equals("E0"))
  {
   /**
   *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
   *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
   *DURANTE EL PROCESO
   *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
   *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
   *OUT NINGUNO
   */
   String trs = new String(" ");
   trs        = null;
   CallableStatement t_cst8i_3 = v_conexion_taxb.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
   t_cst8i_3.setString(1,"IMPOSIBLE INICIAR LA CARGA/REVERSION DE APORTES, EXISTE MAS DE UN REGISTRO EN LA TABLA DE CONTROL PARA INICIAR CARGA");
   t_cst8i_3.setString(2,"REGISTROS DE INICO DE CARGA INVALIDO");
   t_cst8i_3.setString(3," ");
   t_cst8i_3.setString(4,trs);
   t_cst8i_3.setInt(5,fecha_cargue);
   t_cst8i_3.execute();
   t_cst8i_3.close();
  }
 else if(datos_hoy.equals("E2"))
  {
   /**
   *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
   *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
   *DURANTE EL PROCESO
   *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
   *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
   *OUT NINGUNO
   */
   String trs = new String(" ");
   trs        = null;
   CallableStatement t_cst8i_3 = v_conexion_taxb.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
   t_cst8i_3.setString(1,"IMPOSIBLE INICIAR LA CARGA/REVERSION DE APORTES, LA CARGA YA FUE REALIZADA");
   t_cst8i_3.setString(2,"REGISTROS DE INICO DE CARGA INVALIDO");
   t_cst8i_3.setString(3," ");
   t_cst8i_3.setString(4,trs);
   t_cst8i_3.setInt(5,fecha_cargue);
   t_cst8i_3.execute();
   t_cst8i_3.close();
  }
 v_conexion_taxb.commit();
}//fin try
catch(Exception ex){myLogger.error("ERROR EN EL PROCEDIMIENTO PRINCIPAL DE INGRESO Y REVERSION DE APORTES "+ex.toString());}
    finally{
        try{
                DataSourceWrapper.closeConnection(v_conexion_taxb);                  
        }catch(SQLException sqlEx){
                System.out.println(sqlEx.toString());
        }
    } 
}//fin Principal
//---------------------------------------------------------------------------------------------------
/**
*<font face='Verdana' size='2' color='#324395'>
*PROCEDIMIENTO QUE SIRVE PARA REGISTRAR EN EL LOG
*AQUELLOS TOTALES QUE FUERON ENVIADO EN EL REGISTRO
*DE TOTALES DE LA TABLA DE APORTES QUE NO CONCUERDAN
*CON LOS REGISTROS DE DETALLE DE LA MISMA TABLA
*</font>
* @param
*<p>
*<font face='Verdana' size='2' color='#324395'>
*              v_registros_mf  =  Número de registros enviados por MFUND                <br>
*              v_total_aportes =  Número de registros calculdos por TAX                 <br>
*              v_ingresos_mf   =  Valor Total en ingresos enviados por MFUND            <br>
*              v_ingresos_tax  =  Valor Total en ingresos calculdos por TAX             <br>
*              v_rtos_mf       =  Valor Total en rendimientos enviados por MFUND        <br>
*              v_rtos_tax      =  Valor Total en rendimientos calculdos por TAX         <br>
*              v_cc_mf         =  Valor Total en cuenta contingente enviados por MFUND  <br>
*              v_cc_tax        =  Valor Total en cuenta contingente calculdos por TAX   <br>
*              c               =  Objeto Conexión                                       <br>
*              fecha_cargue    =  Fecha en la que se ejecuta la Interface               <br>
*</font>
* @return      ninguno
*
*/
public static void TBPL_VALIDA_TOTALES(int v_registros_mf,int v_total_aportes,double v_ingresos_mf,double v_ingresos_tax,
                                        double v_rtos_mf,double v_rtos_tax,double v_cc_mf,double v_cc_tax,Connection c,
                                        int fecha_cargue)
{
 try
 {
  //defino variables del log
  String trs = new String(" ");
  trs        = null;
  String interfaz   = "RE";
  String paso       = "1";
  String datos      = " ";
  String mensaje    = " ";
  String producto   = " ";
  java.util.Date hoy = new java.util.Date();
  //reviso concordancia entre totales enviados por mfun y totales calculados por tax en el cargue
  if(v_registros_mf!=v_total_aportes){
    datos    = "FALLO TOTAL REGISTROS";
    mensaje  = "FALLO TOTAL REGISTROS";
    CallableStatement t_cst8i_3 = c.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
    t_cst8i_3.setString(1,mensaje);
    t_cst8i_3.setString(2,datos);
    t_cst8i_3.setString(3,producto);
    t_cst8i_3.setString(4,trs);
    t_cst8i_3.setInt(5,fecha_cargue);
    t_cst8i_3.execute();
    t_cst8i_3.close();
                                    }
  if(v_ingresos_mf!=v_ingresos_tax){
    datos    = "FALLO TOTAL INGRESOS";
    mensaje  = "FALLO TOTAL INGRESOS";
    CallableStatement t_cst8i_3 = c.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
    t_cst8i_3.setString(1,mensaje);
    t_cst8i_3.setString(2,datos);
    t_cst8i_3.setString(3,producto);
    t_cst8i_3.setString(4,trs);
    t_cst8i_3.setInt(5,fecha_cargue);
    t_cst8i_3.execute();
    t_cst8i_3.close();
                                   }
  if(v_rtos_mf!=v_rtos_tax){
    datos    = "FALLO TOTAL RENDIMIENTOS";
    mensaje  = "FALLO TOTAL RENDIMIENTOS";
    CallableStatement t_cst8i_3 = c.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
    t_cst8i_3.setString(1,mensaje);
    t_cst8i_3.setString(2,datos);
    t_cst8i_3.setString(3,producto);
    t_cst8i_3.setString(4,trs);
    t_cst8i_3.setInt(5,fecha_cargue);
    t_cst8i_3.execute();
    t_cst8i_3.close();
                           }
  if(v_cc_mf!=v_cc_tax){
    datos    = "FALLO TOTAL CUENTA CONTINGENTE";
    mensaje  = "FALLO TOTAL CUENTA CONTINGENTE";
    CallableStatement t_cst8i_3 = c.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
    t_cst8i_3.setString(1,mensaje);
    t_cst8i_3.setString(2,datos);
    t_cst8i_3.setString(3,producto);
    t_cst8i_3.setString(4,trs);
    t_cst8i_3.setInt(5,fecha_cargue);
    t_cst8i_3.execute();
    t_cst8i_3.close();
                      }
 }//fin try
 catch(Exception ex){myLogger.error("ERORR EN EL PROCEDIMIENTO DE VALIDACION DE TOTALES EN INGRESO Y REVERSION DE APORTES "+ex);}
}//fin Valida_Totales
//---------------------------------------------------------------------------------------------------
/**
*<p>
*<font face='Verdana' size='2' color='#324395'>
*PROCEDIMIENTO QUE SE ENCARGA DE REALIZAR LAS LAS VALIDACIONES NECESEARIAS
*ANTES DE REGISTRAR CADA APORTES DEFINITIVAMENETE SOBRE TAXB
*<font face='Verdana' size='2' color='#324395'>
* @param
*              c               =  Objeto Conexión                           <br>
*              fecha_cargue    =  Fecha en la que se ejecuta la Interface   <br>
* @return      ninguno
*</font>
*/
public static void TBPL_Validaciones(Connection c,int fecha_cargue)
{
 double v_valorUnidad = 0;
 String v_inf_empresas = " ";
 try
 {
  String hubo_insercion = " ";
  myLogger.debug("Validaciones generales TBPL_Validaciones ");
  /**
  *REALIZO LA SELECCION DE LAS EMPRESAS DE LA INTERFACE
  *Y LA INSERCCION DE AQUELLAS QUE NO SE ENCUENTREN REGISTRADAS
  *EN TAXB
  */
  //TBCL_FUNCIONES_AS400_APORTES Grupoid  = new TBCL_FUNCIONES_AS400_APORTES();
  String informacion_empresas[]         = new String[5];
  String select8i_0             = "SELECT DECODE(INA_GRUPO_EMPRESA,'?',NULL,INA_GRUPO_EMPRESA),"+
                                  "INA_PRODUCTO,"+
                                  "INA_TRANSACCION "+
                                  "FROM "+
                                  "TBINTERFACE_APORTES "+
                                  "WHERE INA_FECHA = TO_DATE(?,'RRRRMMDD') "+
                                  "AND INA_PASO    = '1' "+
                                  "AND INA_TIPO_REGISTRO  = '02' "+
                                  "AND INA_TRANSACCION_REVERSAR IS  NULL "+
                                  "ORDER BY INA_GRUPO_EMPRESA";
  PreparedStatement t_st8i_0    = c.prepareStatement(select8i_0);
  t_st8i_0.setInt(1,fecha_cargue);
  ResultSet rs8i_0              = t_st8i_0.executeQuery();
  String empresatmp             = "0X";
  myLogger.debug("Realiza Consulta de empresas,registros=" +rs8i_0.getFetchSize());
  myLogger.debug("select8i_0" +select8i_0);
  while(rs8i_0.next())
   {
    try {if(!empresatmp.equals(rs8i_0.getString(1)))
    {
     empresatmp           = rs8i_0.getString(1);
      myLogger.debug("empresatmp='" + empresatmp + "'");
     try
      {
      if(!empresatmp.equals(null))
      {
        /**
        *LLAMADA A UN STORED PROCEDURE ENCARGADO DE INSERTAR LAS EMPRESAS
        *NUEVAS SOBRE EL SISTEMA TAXB, PARA ESTO BUSCO LA INFORMACION DE CADA
        *UNA DE ELLAS SOBRE UNA FUNCION DEL AS/400
        *IN GRUPO EMPRESA, DESCRIPCION EMPRESA, NIT EMPRESA, PRODUCTO, TRANSACCION
        *   Y FECHA DE CARGUE
        *OUT NINGUNO
        */
        //informacion_empresas = Grupoid.TBFL_Grupo_Empresa(rs8i_0.getString(1));
        /*
        Modificacion:
        Se añade el procedimiento de invocacion a un procedimiento del AS400
        */
        CallableStatement cs = c.prepareCall ( "{? = call TBCL_FUNCIONES_AS400_APORTES.TBFL_Grupo_Empresa_S(?,?)}");
        cs.registerOutParameter(1,Types.CHAR);
        cs.setString(2,rs8i_0.getString(1));
        cs.setInt(3,5);
        cs.executeUpdate();
        v_inf_empresas = cs.getString(1);
        cs.close();
        /* Final de la modificacion */

        informacion_empresas = UtilitiesForTax.StringTokenizedToArrayString(v_inf_empresas, 5, "\\");

        String descripcion   = informacion_empresas[0];
        String nit           = informacion_empresas[1];
        //CAMBIO HECHO POR APC 2005/07/13 DADO QUE ESTA VALIDACIÓN ESTA FUERA DE LUGAR
        //        if(!descripcion.substring(0,1).equals(" ")&&!nit.substring(0,1).equals(" "))
        if(descripcion.trim().length()!=0 &&nit.trim().length()!=0)
        //FIN CAMBIO 2005/07/13        
           {
            CallableStatement t_cst8i_8 = c.prepareCall("{ call TBPBD_crea_empresa(?,?,?,?,?,?) }");
            t_cst8i_8.setString(1,rs8i_0.getString(1));
            t_cst8i_8.setString(2,descripcion);
            t_cst8i_8.setString(3,nit);
            t_cst8i_8.setString(4,rs8i_0.getString(2));
            t_cst8i_8.setString(5,rs8i_0.getString(3));
            t_cst8i_8.setInt(6,fecha_cargue);
            t_cst8i_8.execute();
            myLogger.debug("Se creo empresa "+rs8i_0.getString(1)+","+descripcion);
            t_cst8i_8.close();

           }//if ix del as/400 no es null
        else
         {
           /**
           *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
           *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
           *DURANTE EL PROCESO
           *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
           *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
           *OUT NINGUNO
           */
           //String trs = new String(" ");
           //trs        = null;
           CallableStatement t_cst8i_193 = c.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
           t_cst8i_193.setString(1,"IMPOSIBLE CREAR LA EMPRESA "+empresatmp);
           t_cst8i_193.setString(2,"DESCRIPCION AS/400: "+descripcion+"  NIT AS/400: "+nit);
           t_cst8i_193.setString(3,rs8i_0.getString(2));
           t_cst8i_193.setString(4,rs8i_0.getString(3));
           t_cst8i_193.setInt(5,fecha_cargue);
           t_cst8i_193.execute();
           t_cst8i_193.close();
           myLogger.debug("No se creo empresa ");
         }
      }// if(!empresatmp.equals(null))
     }//TRY
     catch(Exception ex)
     {
      myLogger.error(ex.toString());
     }
    }//if empresaid es diferente a las ya procesadas
   }
    catch(Exception exe)
    {
      myLogger.error(exe.toString());
    }
   }//while .next
      rs8i_0.close();
      t_st8i_0.close();
  /**
  *SELECCION DE LOS CONTRATOS DE LA INTERFACE Y LA CREACION
  *SOBRE TAXB DE AQUELLOS QUE SEAN NUEVOS
  */
  String select8i_1             = "SELECT INA_CONTRATO "+
                                  ",TO_CHAR(INA_FECHA_EFECTIVA,'RRRRMMDD')"+
                                  ",INA_PRODUCTO "+
                                  ",INA_GRUPO_EMPRESA "+
                                  ",INA_TIPO_IDENTIFICACION "+
                                  ",INA_NUMERO_IDENTIFICACION "+
                                  ",INA_NOMBRES "+
                                  ",INA_APELLIDOS "+
                                  ",TO_CHAR(INA_FECHA_APERTURA,'RRRR-MM-DD') "+
                                  ",DECODE(INA_METODO_ORDEN,NULL,'?',INA_METODO_ORDEN) "+
                                  ",DECODE(INA_METODO_BENEFICIO,NULL,'?',INA_METODO_BENEFICIO) "+
                                  ",DECODE(INA_METODO_PENALIZACION,NULL,'?',INA_METODO_PENALIZACION) "+
                                  ",DECODE(INA_METODO_CUENTA,NULL,'?',INA_METODO_CUENTA)"+
                                  ",DECODE(INA_NATURALEZA,NULL,'?',INA_NATURALEZA) "+
                                  ",DECODE(INA_RESPETAR_NATURALEZA,NULL,'?',INA_RESPETAR_NATURALEZA) "+
                                  ",INA_TRANSACCION "+
                                  ",INA_CONTRATO_PENALIZADO "+
                                  ",INA_APORTE_EMPRESA "+
                                  "FROM "+
                                  "TBINTERFACE_APORTES "+
                                  "WHERE INA_FECHA        = TO_DATE(?,'RRRRMMDD') "+
                                  "AND INA_PASO           = '1' "+
                                  "AND INA_TIPO_REGISTRO  = '02' "+
                                  "AND INA_TRANSACCION_REVERSAR IS  NULL "+
                                  "ORDER BY INA_CONTRATO ";
  PreparedStatement t_st8i_1    = c.prepareStatement(select8i_1);
  t_st8i_1.setInt(1,fecha_cargue);
  ResultSet rs8i_1              = t_st8i_1.executeQuery();
  String productotmp            = "0X";
  String contratotmp            = "0X";
  String fecha_vu               = new String("");
  myLogger.debug("Realizar consulta para contrato ");
  while(rs8i_1.next())
  { //CREO CONTRATOS QUE NO EXISTAN
   if(!contratotmp.equals(rs8i_1.getString(1)))
   {
      /**
      *LLAMADO A UN STORED PROCEDURE ENCARGADO DE INSERTAR
      *SOBRE LA TABLA TBCONTRATOS DE TAXB AQUELLOS CONTRATOS
      *QUE NO SE ENCUENTREN REGISTRADOS
      *IN GRUPO_EMPRESA, TIPO IDENTIFICACION, NUMERO IDENTIFICACION,
      *   NOMBRES, APELLIDOS, FECHA APERTURA, METODO ORDEN, METODO
      *   BEENFICIO, METODO PENALIZACION, METODO CUENTA, NATURALEZA
      *   RESPETAR NATURALEZA, TRANSACCION MFUND, PRODUCTO, CONTRATO,
      *   FECHA DE CARGUE, INDICADOR DE PENALIZACION, INDICADOR DE EMPRESA
      *OUT INDICADOR DE INSERCION(S O N)
      */
      contratotmp = rs8i_1.getString(1);
      fecha_vu    = rs8i_1.getString(2);
      productotmp = rs8i_1.getString(3);
      CallableStatement t_cst8i_9 = c.prepareCall("{ call TBPBD_INSERTA_CONTRATO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
      t_cst8i_9.registerOutParameter(19,Types.VARCHAR);
      t_cst8i_9.setString(1,rs8i_1.getString(4));
      t_cst8i_9.setString(2,rs8i_1.getString(5));
      t_cst8i_9.setString(3,rs8i_1.getString(6));
      t_cst8i_9.setString(4,rs8i_1.getString(7));
      t_cst8i_9.setString(5,rs8i_1.getString(8));
      t_cst8i_9.setString(6,rs8i_1.getString(9));
      t_cst8i_9.setString(7,rs8i_1.getString(10));
      t_cst8i_9.setString(8,rs8i_1.getString(11));
      t_cst8i_9.setString(9,rs8i_1.getString(12));
      t_cst8i_9.setString(10,rs8i_1.getString(13));
      t_cst8i_9.setString(11,rs8i_1.getString(14));
      t_cst8i_9.setString(12,rs8i_1.getString(15));
      t_cst8i_9.setString(13,rs8i_1.getString(16));
      t_cst8i_9.setString(14,rs8i_1.getString(3));
      t_cst8i_9.setString(15,rs8i_1.getString(1));
      t_cst8i_9.setInt(16,fecha_cargue);
      t_cst8i_9.setString(17,rs8i_1.getString(17));
      t_cst8i_9.setString(18,rs8i_1.getString(18));
      t_cst8i_9.execute();
      hubo_insercion = t_cst8i_9.getString(19);
      t_cst8i_9.close();
      /**
      *POR CADA CONTRATO DE LA INTERFACE PERTENECIENTE EN TAXB SE
      *DEBE DE REALIZAR EL LLAMADO A LA FUNCION DEL VALOR DE LA
      *UNIDAD, PARA LOS CONTRATOS NUEVOS EL VALOR DE LA UNIDAD
      *SERA ASUMIDO COMO 1000
      */
      myLogger.debug("Inserto contrato ,Mensaje "+ hubo_insercion);

      contratotmp  = "0X";
    } //si contrato diferente
   } //while
  rs8i_1.close();
  t_st8i_1.close();
  myLogger.debug("Calculo valor de unidad");
  /**
  *LLAMADO A UN STORED PROCEDURE ENCARGADO DE REALIZAR
  *LAS VALIDACIONES DE CAMPOS POR APORTE A CARGAR Y LA CARGA
  *DEFINITIVA DE APORTES VALIDOS PARA EL SISTEMA
  *IN FECHA DE CARGUE
  *OUT INDICADOR FINAL DE PROCESO
  */
  CallableStatement t_cst8i_6 = c.prepareCall("{ call TBPBD_carga_final(?,?) }");
  t_cst8i_6.registerOutParameter(2,Types.VARCHAR);
  t_cst8i_6.setInt(1,fecha_cargue);
  t_cst8i_6.execute();
  carga_final = t_cst8i_6.getString(2);
  t_cst8i_6.close();
  myLogger.debug("Realizo carga final");
  /**
  *SELECCION DE AQUELLOS APORTES DE LA INTERFACE A REVERSAR
  *SOBRE EL SISTEMA TAXB, PARA ELLO SE SELLECIONA LA TRANSACCION
  *DE REVERSION A LA CUAL PERTENECERAN LOS APORTES A REVERSAR PERTENECIENTES
  *A TAXB INCLUYENDOLO A EL Y ASUS ANCESTROS
  */
  String select8i_2             = "SELECT INA_PRODUCTO  "+
                                  "      ,INA_CONTRATO  "+
                                  "      ,INA_TRANSACCION_REVERSAR  "+
                                  "      ,TO_CHAR(INA_FECHA_EFECTIVA,'RRRRMMDD') "+
                                  "      ,INA_TRANSACCION   "+
                                  "  FROM TBINTERFACE_APORTES  "+
                                  " WHERE INA_FECHA = TO_DATE(?,'RRRRMMDD') "+
                                  "   AND INA_TRANSACCION_REVERSAR IS NOT NULL";
  PreparedStatement t_st8i_2    = c.prepareStatement(select8i_2);
  t_st8i_2.setInt(1,fecha_cargue);
  ResultSet rs8i_2              = t_st8i_2.executeQuery();

  String pr                     = new String(" ");
  String co                     = new String(" ");
  String tr                     = new String(" ");
  String trapo                 = new String(" ");
  boolean movio_cursor          = false;
  myLogger.debug("Realiza consulta de aportes a reversar, fecha de cargue "+fecha_cargue);
  while(rs8i_2.next())
    {//rs8i_2

     pr = rs8i_2.getString(1);
     co = rs8i_2.getString(2);
     tr = rs8i_2.getString(3);
     trapo  = rs8i_2.getString(5);
     String fecha = rs8i_2.getString(4);
     /**
      *POR CADA REVER PERTENECIENTE EN TAXB SE
      *DEBE DE REALIZAR EL LLAMADO A LA FUNCION DEL VALOR DE LA
      *UNIDAD
      */
     //INICIO calculo al valor de la unidad
     try
       {
         //calculo el valor de la unidad
         /*[SO_396] Se realiza modificación de llamado por ser método estático TBF_CALCULO_VALOR_UNIDAD de la clase SQL_VALOR_UNIDAD_CC, no es necesaria la instancia nueva*/
         //SQL_VALOR_UNIDAD_CC i_valUnid      = new SQL_VALOR_UNIDAD_CC();
         double matUnidad[]                 = new double[3];
         matUnidad                          = SQL_VALOR_UNIDAD_CC.TBF_CALCULO_VALOR_UNIDAD(fecha,fecha,pr,co,false,0);
//         matUnidad                          = i_valUnid.TBF_CALCULO_VALOR_UNIDAD(fecha,fecha,rs8i_2.getString(1),rs8i_2.getString(2),true,0);
         v_valorUnidad                      = matUnidad[0];
         if(matUnidad[2]!=0.0)
           {
             /**
             *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
             *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
             *DURANTE EL PROCESO
             *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
             *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
             *OUT NINGUNO
             */
             //llamar al LOG
             myLogger.error("ERROR EN EL LLAMADO A LA FUNCION DEL CALCULO DEL VALOR DE LA UNIDAD EN INGRESO Y REVERSION DE APORTES.ERROR "+matUnidad[2]);
             v_valorUnidad=1000.00;
           }
        }//try
       catch(Exception ex)
       {
         /**
         *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
         *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
         *DURANTE EL PROCESO
         *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
         *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
         *OUT NINGUNO
         */
         //llamar al LOG
         myLogger.error("ERROR EN EL LLAMADO A LA FUNCION DEL CALCULO DEL VALOR DE LA UNIDAD EN INGRESO Y REVERSION DE APORTES. ERROR "+ex);
         v_valorUnidad=1000.00;
       }//catch
      //fin llamado al valor de la unidad
      /**
      *SELECCION DEL APORTE A REVERSAR INCLUYENDO
      *TODOS LOS ANCESTROS, A CADA UNO DE ELLOS SE LE
      *DEBE DE ACTUALIZAR SU ESTADO Y SU FECHA DE PROCESO      *
      */

     String super_select ="select " +
                           "apo_consecutivo "+
                           "from tbaportes "+
                           "connect by prior apo_consecutivo = apo_apo_consecutivo "+
                           "start with apo_apo_consecutivo is null "+
                           "and apo_con_pro_codigo = ? "+
                           "and apo_con_numero     = ? "+
                           "and apo_transaccion    = ? ";
     PreparedStatement t_st8i_3    = c.prepareStatement(super_select);
     t_st8i_3.setString(1,pr);
     t_st8i_3.setString(2,co);
     t_st8i_3.setString(3,tr);
     ResultSet rs8i_3              = t_st8i_3.executeQuery();

     while(rs8i_3.next())
       {//rs8i_3
         /**
         *LLAMADO A UN STORED PROCEDURE ENCARGADO DE VERIFICAR
         *POR CADA APORTE A REVERSAR SI ESTE PRESENTA O NO RETIROS
         *ASOCIADOS A EL, EN CASO DE TENER RETIROS ASOCIADOS
         *SE PRODUCIRA UNA IDA AL LOG DE INTERFACES Y LA REVERSION NO
         *PODRA EFECTUARSE, EN CASO CONTRARIO EL PROCESO CONTINUARA
         *NORMALMENTE.
         *IN PRODUCTO, CONTRATO Y APORTE A REVERSAR
         */
         movio_cursor = true;
         //int n = rs8i_3.getInt(1);
         double n = rs8i_3.getDouble(1);
         CallableStatement t_cst8i_11 = c.prepareCall("{ call TBPBD_Valida_Retiros_por_Apt(?,?,?,?) }");
         t_cst8i_11.registerOutParameter(4,Types.VARCHAR);
         t_cst8i_11.setString(1,pr);
         t_cst8i_11.setString(2,co);
         t_cst8i_11.setDouble(3,n);
         t_cst8i_11.execute();
         String tieneretiros = t_cst8i_11.getString(4);
         t_cst8i_11.close();
         myLogger.debug("llamo a TBPBD_Valida_Retiros_por_Apt "+tieneretiros);
         if(tieneretiros.equals("v"))
         {
           /**
           *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
           *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
           *DURANTE EL PROCESO
           *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
           *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
           *OUT NINGUNO
           */
           String trs = new String(" ");
           trs        = null;
           CallableStatement t_cst8i_12 = c.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
           t_cst8i_12.setString(1,"NO SE PUDO REALIZAR LA REVERSION DEL APORTE, ESTE PRESENTA RETIROS ASOCIADOS.");
           t_cst8i_12.setString(2,"APORTES No: "+n+"  CONTRATO: "+co+"  PRODUCTO:  "+pr+"  TRANSACCION A REVERSAR: "+tr);
           t_cst8i_12.setString(3,pr);
           t_cst8i_12.setString(4,trapo);
           t_cst8i_12.setInt(5,fecha_cargue);
           t_cst8i_12.execute();
           t_cst8i_12.close();
         }
         else if(tieneretiros.equals("f"))
         {
           /**
           *LLAMADO A UN STORED PROCEDURE ENCARGADO ACTUALIZAR
           *EL ESTADO DE CADA APORTE A REVERSAR DE SE001 A SEA002
           *IN PRODUCTO, CONTRATO, APORTE A REVERSAR, FECHA DE CARGUE
           *OUT ESTADO FINAL DE PROCESO
           */
           CallableStatement t_cst8i_15 = c.prepareCall("{ call TBPBD_ACTUALIZA_ESTADO(?,?,?,?,?) }");
           t_cst8i_15.registerOutParameter(5,Types.VARCHAR);
           t_cst8i_15.setString(1,pr);
           t_cst8i_15.setString(2,co);
           t_cst8i_15.setDouble(3,n);
           t_cst8i_15.setInt(4,fecha_cargue);
           t_cst8i_15.execute();
           String a = t_cst8i_15.getString(5);
           t_cst8i_15.close();

         }
       }//super select
       rs8i_3.close();
       t_st8i_3.close();
    if(!movio_cursor)
      {

       /**
       *LLAMADO A UN STORED PROCEDURE QUE SE ERNCARGA DE REGISTRAR
       *EN LA TABLA DE LOGS DE LAS INTERFACES TODO ERROR QUE OCURRA
       *DURANTE EL PROCESO
       *IN MENSAJE DE ERROR, CONTENIDO DE LOS DATOS ERRADOS, UN PRODUCTO
       *   UN NUMERO DE TRANSACCION MFUND Y UNA FECHA DE CARGUE
       *OUT NINGUNO
       */
       String trs = new String(" ");
       trs        = null;
       CallableStatement t_cst8i_293 = c.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
       t_cst8i_293.setString(1,"NO SE ENCONTRARON APORTES PERTENECIENTES A LA TRANSACCIÓN No"+tr);
       t_cst8i_293.setString(2,"TRANSACCION A REVERSAR: "+tr+"  CONTRATO: "+co+"  PRODUCTO:  "+pr);
       t_cst8i_293.setString(3,pr);
       t_cst8i_293.setString(4,trapo);
       t_cst8i_293.setInt(5,fecha_cargue);
       t_cst8i_293.execute();
       t_cst8i_293.close();
      }
    }//INA_TRANSACCION
    rs8i_2.close();
    t_st8i_2.close();
 }
 catch(Exception ex)
 {
  try
  {
   myLogger.error("ERROR EN EL PROCEDIMIENTO DE INSERCCION DE INGRESO Y REVERSION DE APORTES "+ ex.toString());
   String trs = new String(" ");
   trs        = null;
   CallableStatement t_cst_error = c.prepareCall("{ call TBPBD_INSERTA_LOG(?,?,?,?,?) }");
   t_cst_error.setString(1,"ERROR EN EL PROCEDIMIENTO DE INSERCCION DE INGRESO Y REVERSION DE APORTES "+ ex.toString());
   t_cst_error.setString(2,"XXXXX");
   t_cst_error.setString(3," ");
   t_cst_error.setString(4,trs);
   t_cst_error.setInt(5,fecha_cargue);
   t_cst_error.execute();
   t_cst_error.close();
  }
  catch(Exception ex2)
  {
   myLogger.error("ERROR EN EL PROCEDIMIENTO DE INSERCCION DE INGRESO Y REVERSION DE APORTES "+ ex2.toString());
  }
 }
 }
}

