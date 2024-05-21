package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

import java.sql.*;
import java.util.*;
import java.math.*;
import oracle.jdbc.OracleTypes.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;
import TBPKT_MODULO_APORTES.*;
import oracle.jdbc.*;
import TBPKT_EXCEPCIONES.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

import java.text.SimpleDateFormat;
//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;



/**
 * SQL_PASO10
 * TBP_PASO10: Controla el paso 10 de las Interfaces de Retiros,
 * el cual debe enviar la información de traslados internos de
 * Multifund. 
 * @date 23/07/07      
 * @author: Marcela Ortiz Sandoval nortiz@skandia.com.co
 */
public class SQL_PASO10 extends Object{

  public static String PROCEDIMIENTO_REP = SQLTools.buildProcedureCall("CMD_AFT1REP", "insert_aft1rep", 9);
  public static String PROCEDIMIENTO_FECHA = SQLTools.buildProcedureCall(null, "TBPBD_INTERF_RET_GRAL", 6);
  public static String PROCEDIMIENTO_PASO10 = SQLTools.buildProcedureCall(null, "TBPBD_PASO10",10);
  public static String PROCEDIMIENTO_APORTES_COMP =  SQLTools.buildProcedureCall("CMD_AFTLCPP", "insert_aftlcpp", 13);
  public static String PROCEDIMIENTO_REGISTRO_CONTROL =  SQLTools.buildProcedureCall(null, "TBPBD_INS_AJKPCPP", 7);
  public static String PROCEDIMIENTO_MODIFICAR_REGISTRO =  SQLTools.buildProcedureCall(null, "TBPBD_UPDATE_AJKPCPP", 5);
  public static String PROCEDIMIENTO_DESHACER =  SQLTools.buildProcedureCall("CMD_PASO10", "BORRAR_TABLAS_INTERMEDIAS", 3);
  public static String PROCEDIMIENTO_FECHA_SIGUIENTE_HABIL =  SQLTools.buildProcedureCall(null, "TBPBD_FECHA_CIERRE_VALIDA", 1);
  public static String PROCEDIMIENTO_CAJAS_PRIMAS =  SQLTools.buildProcedureCall("tbcl_funcionesas400", "tbpl_generarcajasprimas", 9);
  public static Connection con = null;
  
  /**
   * TBP_PASO10 
   * Controla el paso 10 de las Interfaces de Retiros, el cual debe enviar 
   * la información de traslados internos de Multifund. 
   * Realiza el llamado a los métodos implicados
   **/
  public void TBP_PASO10() {

      System.out.println("Inicia la ejecución del paso 10");
      TBCL_Validacion valuesUser = new TBCL_Validacion();
      Boolean resul1 = Boolean.FALSE;
      Boolean resul2 = Boolean.FALSE;
      Boolean resul3 = Boolean.FALSE;
      Boolean resul4 = Boolean.FALSE;
      Boolean resul5 = Boolean.FALSE;
      String codAgente = "000001551";/*valor por defecto del agente general Skandia*/
      String tipo = "I";
      String equipo = "000008";
      String sistema ="";
      String usuario ="";
      String password ="";
      String libreria ="";
      int consec = 1;
    try{
      /*Conexion con la base de datos*/
      //logger.info
      con =   DataSourceWrapper.getInstance().getConnection();
      
      /* Cargue de los parametros */
      //logger.info
      int pos_i = 0;
      Integer fechaControl =  obtenerFechaControl();
      //fechaControl =  new Integer(20100602);
      if(fechaControl != null){
      reportarEstadoPaso("I",fechaControl,"N");
      CallableStatement csFechaHabil = con.prepareCall(PROCEDIMIENTO_FECHA_SIGUIENTE_HABIL);
      csFechaHabil.registerOutParameter(1, OracleTypes.VARCHAR);
      csFechaHabil.execute();
      String fechaSigHabil = csFechaHabil.getString(1);
      String fechaSigHabilTrunc = "";
      for (int i=0; i < fechaSigHabil.length(); i++) {
        if (fechaSigHabil.charAt(i) != '-')
          fechaSigHabilTrunc += fechaSigHabil.charAt(i);
      }
      //logger.info
      fechaSigHabil =  fechaSigHabilTrunc;
      csFechaHabil.close();
      String hora = obtenerHora();

      /*Establecer los datos de conexion a AS400 */
      //logger.info
      System.out.println("Intentará establecer conexión con 400");
      CallableStatement csPdto =  con.prepareCall("BEGIN :1 := TB_FREFERENCIAS_MULTI( :2 ,\n :3,\n :4 ,\n :5)\n; END;");
      csPdto.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      csPdto.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
      /* setear parametro de entrada IN */
      csPdto.setString(2,sistema);
      csPdto.setString(3,usuario);
      csPdto.setString(4,password);
      csPdto.setString(5,libreria);
      csPdto.execute();

      /* recupera parametros de salida*/
      //logger.info("Se tienen los parametros de 400");
      sistema = csPdto.getString(2);
      usuario = csPdto.getString(3);
      password = csPdto.getString(4);
      libreria = csPdto.getString(5);
      csPdto.close();

      /*Registrar el lote de traslados*/
      resul1 = registrarLote(fechaSigHabil, hora, codAgente, tipo, equipo);
      if (resul1 == Boolean.TRUE){
        resul5 = registrarApsCompanyHeader (codAgente,  fechaSigHabil, hora, 
         "",  "", "", "", "", "", "", "",tipo, equipo);
        if( resul5 == Boolean.TRUE){
        resul2 = registrarApsContract
                  (codAgente, fechaControl, fechaSigHabil, "MFUND", hora,  "Y", 
                   tipo, equipo);
                   
        System.out.print("Termino los registros Multifund:\n");
        con.commit();
        System.out.println("Entra a crear caja Mfund");
        String contratoCajas = cajasPrimasAS400(fechaSigHabil, equipo, tipo, codAgente, hora, sistema, usuario, password, libreria);
        con.commit();
        System.out.println("Termina de crear caja Mfund");
        
        hora = obtenerHora();
        resul1 = registrarLote(fechaSigHabil, hora, codAgente, tipo, equipo);
        if (resul1 == Boolean.TRUE){
        resul5 = registrarApsCompanyHeader (codAgente,  fechaSigHabil, hora, 
         "",  "", "", "", "", "", "", "",tipo, equipo);
         
            if( resul5 == Boolean.TRUE){
            resul2 = registrarApsContract(codAgente, fechaControl, fechaSigHabil,  
                              "SIPEN", hora,  "Y", tipo, equipo);
                       
            System.out.print("Termino los registros SIPEN:\n");
            con.commit();
            System.out.println("Entra a crear caja SIPEN");
            contratoCajas = cajasPrimasAS400(fechaSigHabil, equipo, tipo, codAgente, hora, sistema, usuario, password, libreria);
            con.commit();
            System.out.println("Termina de crear caja SIPEN");
            }
        } 
        reportarEstadoPaso("M",fechaControl,"Y");
        System.out.println("Termina de crear cajas");
      } else{
        throw new Paso10Exception( "Error al ingresar registro en APS COMPANY HEADER", consec);
        }
      } else{
        throw new Paso10Exception( "Error al registrar el lote de traslados", consec);
        }
      }
      
      
    }  catch (Exception e){
         Boolean rta = deshacerInsercciones();
         //logger.error
         System.out.println("SQL_PASO10 "+e);
    } finally{
        try { 
             if( con != null ) { 
                con.commit();
                DataSourceWrapper.closeConnection(con);
             }
         }
         catch( SQLException ignored ){
           } 
    }
  }

  /**
   * registrarLote
   * Inserta un registro en la tabla intermedia AFT1REP
   * @param String fechaControl Fecha de cierre
   * @param String hora           Hora de Cierre
   * @param String codAgente     Agente general 
   * @param String tipo           Tipo de operación
   * @param String equipo         Equipo
   * @return Boolean Retorna true si la operación fue exitosa y false en 
   *         caso contrario
   */
  public Boolean registrarLote(String fechaControl, String hora, 
      String codAgente, String tipo, String equipo){

      int pos = 0;
      int retorna;
      Boolean resultado = Boolean.FALSE; 
      
    try{

      CallableStatement cs = con.prepareCall(PROCEDIMIENTO_REP);
      String estado = "";
      String codError = "";
      String mensError = "";
      
      /* Cargue de los parametros de entrada IN */
      cs.setString(++pos, codAgente);
      cs.setString(++pos, fechaControl);
      cs.setString(++pos, hora);
      cs.setString(++pos, estado);
      cs.setString(++pos, tipo);
      cs.setString(++pos, equipo);
      cs.setString(++pos, codError);
      cs.setString(++pos, mensError);
        
      /* Registro de los parametro de salida OUT */
      cs.registerOutParameter(++pos, OracleTypes.NUMBER);
      cs.execute();
      retorna = (int) cs.getInt(9);
      if(retorna == 1){
          resultado = Boolean.TRUE;
      }
      cs.close();

    } catch (Exception e){
      //logger.error
      System.out.println("SQL_PASO10 en el registro del lote de aportes:"+e);
    } finally  {
      return resultado;
    }


  }

  /**
   * registrarApsContract
   * Inserta un registro en la tabla intermedia AFTMCPP APS CONTRACT
   * @param String agente Código agente ajuste con ceros
   * @param String fechaControl Fecha AAAA MM  DD
   * @param String fecha Fecha AAAA MM  DD
   * @param String hora Hora HH MM SS
   * @param String taxable Taxable
   * @param String tipo
   * @param String equipo
   * @return Boolean Retorna true si la operación fue exitosa y false en 
   *         caso contrario
   */

  public Boolean registrarApsContract(String agente, Integer fechaControl, 
      String fecha, String producto, String hora, String taxable, 
      String tipo, String equipo) {
      
    int pos = 0;
    int retorna;
    Boolean resultado = Boolean.FALSE; 

    try{

      CallableStatement csCont = con.prepareCall(PROCEDIMIENTO_PASO10);
      /* Cargue de los parametros de entrada IN */
      csCont.setInt(++pos, fechaControl.intValue());
      csCont.setString(++pos, fecha);
      csCont.setString(++pos, producto);
      csCont.setString(++pos, agente);
      csCont.setString(++pos, taxable);
      csCont.setString(++pos, hora);
      csCont.setString(++pos, tipo);
      csCont.setString(++pos, equipo);  
      /* Registro de los parametro de salida OUT */
      csCont.registerOutParameter(++pos, OracleTypes.NUMBER);
      csCont.registerOutParameter(++pos, OracleTypes.VARCHAR);
      csCont.execute();
      retorna = (int) csCont.getInt(9);
      if(retorna == 0){
          resultado = Boolean.TRUE;
      }
      csCont.close();
    } catch (Exception e){
      //logger.error
      System.out.println("SQL_PASO10 en la inserción de retiros : "+e);
    } finally  {
    
      return resultado;
    }
  }

  
  /**
   * registrarApsCompanyHeader
   * Inserta un registro en la tabla intermedia AFTLCPP APS COMPANY HEADER
   * @param String jpfvcd Código agente ajuste con ceros
   * @param String jpscdx Fecha AAAA MM  DD
   * @param String jpdetm Hora HH MM SS
   * @param String jplecx Consecutivo = por contrato
   * @param String jpwbvz Cuenta contigente
   * @param String jpqesz Pti Tax Certificate
   * @param String jpwcvz Rendimiento
   * @param String jpwdvz Capital (Valor Ingreso 2 dec)
   * @param String consecutivo
   * @param String codigo
   * @param String contrato
   * @param String tipo
   * @param String equipo
   * @return Boolean Retorna true si la operación fue exitosa y false en 
   *         caso contrario
   */
  public Boolean registrarApsCompanyHeader(String jpfvcd, String jpscdx, 
      String jpdetm, String jplecx, String jpwbvz, String jpqesz, 
      String jpwcvz, String jpwdvz, String consecutivo, String codigo,  
      String contrato, String tipo, String equipo) {
      int pos = 0;
      int retorna;
      Boolean resultado = Boolean.FALSE; 

    try{
      
      CallableStatement csPremium = con.prepareCall(PROCEDIMIENTO_APORTES_COMP);
      /* Cargue de los parametros de entrada IN */
      csPremium.setString(++pos, jpfvcd);
      csPremium.setString(++pos, jpscdx);
      csPremium.setString(++pos, jpdetm);
      csPremium.setString(++pos, jplecx);
      csPremium.setString(++pos, jpwbvz);
      csPremium.setString(++pos, jpqesz);
      csPremium.setString(++pos, jpwcvz);
      csPremium.setString(++pos, jpwdvz);
      csPremium.setString(++pos, tipo);
      csPremium.setString(++pos, equipo);
      /* Registro de los parametro de salida OUT */
      csPremium.registerOutParameter(++pos, OracleTypes.NUMBER);
      csPremium.registerOutParameter(++pos, OracleTypes.VARCHAR);
      csPremium.registerOutParameter(++pos, OracleTypes.NUMBER);
      csPremium.execute();
      retorna = (int) csPremium.getInt(13);
      if(retorna == 1){
          resultado = Boolean.TRUE;
      }
      csPremium.close();

    } catch (Exception e){
      //logger.error
      System.out.println("SQL_PASO10 en la inserción a la tabla AFTLCPP APS COMPANY HEADER del aporte:"+e);
    } finally  {
      return resultado;
    }
  }



  /**
   * deshacerInsercciones
   * Borra todos los registros ingresados en el paso 10 en las tablas intermedias
   * @return Boolean Retorna true si la operación fue exitosa y false en 
   *         caso contrario
   */
  public Boolean deshacerInsercciones() {
      int pos = 0;
      int retorna;
      String codError = "";
      String mensError = "";
      Boolean resultado = Boolean.FALSE; 

    try{
        CallableStatement csUndo = con.prepareCall(PROCEDIMIENTO_DESHACER);
        /* Cargue de los parametros de entrada IN */
        csUndo.setString(++pos, codError);
        csUndo.setString(++pos, mensError);
        /* Registro de los parametro de salida OUT */
        csUndo.registerOutParameter(++pos, OracleTypes.NUMBER);
        csUndo.execute();
        retorna = (int) csUndo.getInt(3);
        if(retorna == 1){
            resultado = Boolean.TRUE;
        }
        csUndo.close();
    } catch (Exception e){
        //logger.error
        System.out.println("SQL_PASO10 deshaciendo las acciones del paso:"+e);
    } finally  {
      return resultado;
    }
  }

  /**
   * reportarEstadoPaso
   * Inserta un registro en la tabla de control sobre el paso 10 
   * @param String operacion Indica si es inserción o modificación del registro
   * @param  Integer fechaControl de la ejecución del paso
   * @param  String resultado  de la ejecución del paso
   */
  public void reportarEstadoPaso(String operacion, Integer fechaControl, 
          String resultado) {
      int pos = 0;
      String codError = "";
      String mensError = "";
      String valorUnidad = "000099";
      String codUnidad = "UUP099";

    try{
      /*Calcular valor unidad*/
      CallableStatement csControl = null;
      

      if (operacion =="M"){
        /* Cargue de los parametros de entrada IN */      
        csControl = con.prepareCall(PROCEDIMIENTO_MODIFICAR_REGISTRO);
        csControl.setInt(++pos, fechaControl.intValue());
        csControl.setString(++pos, codUnidad);
        csControl.setInt(++pos,10);
        
        /* Registro de los parametro de salida OUT */
        csControl.registerOutParameter(++pos, OracleTypes.NUMBER);
        csControl.setInt(pos, 0);
        csControl.registerOutParameter(++pos, OracleTypes.VARCHAR);
        csControl.executeUpdate();
        csControl.close();
        con.commit();
        
      }  
      else{ 
         
          
          csControl = con.prepareCall(PROCEDIMIENTO_REGISTRO_CONTROL);
          csControl.setInt(++pos, fechaControl.intValue());
          csControl.setString(++pos, "EG");
          csControl.setString(++pos, valorUnidad);
          csControl.setInt(++pos,10);
          csControl.setString(++pos, resultado);

          /* Registro de los parametro de salida OUT */
          csControl.registerOutParameter(++pos, OracleTypes.NUMBER);
          csControl.setInt(pos, 0);
          csControl.registerOutParameter(++pos, OracleTypes.VARCHAR);
          csControl.executeUpdate();
          csControl.close();
          con.commit();
      } 
        
      


      
    } catch (Exception e){
        //logger.error
        System.out.println("SQL_PASO10 guardando registro de control:"+e);
    } 
  }

  /**
   * obtenerFechaControl
   * Obtener la fecha de control con la cual se jecuta el cierre 
   * @param  Integer fecha de cierre
   */
  public Integer obtenerFechaControl() {
      int pos = 0;
      Integer fecha = null;

    try{
      /* Consultar fecha de salida */
      CallableStatement csInicial =  con.prepareCall(PROCEDIMIENTO_FECHA);
      csInicial.setString(1,"10");
      csInicial.registerOutParameter(2,oracle.jdbc.OracleTypes.INTEGER);
      csInicial.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR);
      csInicial.setInt(4,1);
      csInicial.setInt(5,0);
      csInicial.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
      csInicial.execute();
      String valido = csInicial.getString(3);
      if (valido.equals("S")){
          fecha  = new Integer(csInicial.getInt(2));
      }
      csInicial.close();
    } catch (Exception e){
        //logger.error
        System.out.println("SQL_PASO10 guardando registro de control:"+e);
    } finally{
            return fecha;
    }
  }


    /**
   * cajasPrimasAS400
   * Obtener la fecha de control con la cual se jecuta el cierre 
   * @param  Integer fecha de cierre
   */
  public String cajasPrimasAS400(String fechaSigHabil, String equipo, String tipo, String codAgente, String hora, String sistema, String usuario, String password, String libreria) {
      String v_retorno_programa = new String("");
      String v_programa;
    try{
      /* Consultar fecha de salida */
      //CallableStatement cs2 =  con.prepareCall(PROCEDIMIENTO_CAJAS_PRIMAS);
      CallableStatement cs2 = con.prepareCall ( "{? = call TBCL_FuncionesAs400.tbpl_generarcajasprimas(?,?,?,?,?,?,?,?,?)}");
      cs2.registerOutParameter(1,Types.CHAR);
      cs2.setString(2,fechaSigHabil);
      cs2.setString(3,equipo);
      cs2.setString(4,tipo);
      cs2.setString(5,codAgente);
      cs2.setString(6,hora);
      cs2.setString(7,sistema);
      cs2.setString(8,usuario);
      cs2.setString(9,password);
      cs2.setString(10,libreria);
      
      cs2.execute();
      v_retorno_programa = cs2.getString(1);
      cs2.close();
      con.commit();
    } catch (Exception e){
        //logger.error
        System.out.println("SQL_PASO10 guardando registro de control:"+e);
    } finally{
            return v_retorno_programa;
    }
  }
  
  /**
   * obtenerHora
   * Obtener la hora completa para registrar la operación
   * @param  Integer hora de cierre
   */
  /*[SO_396]Se cambia implementación del método para realizar la misma acción sin usar los métodos getHours, getMinutes y getSeconds ya que han sido deprecados
   *
  public String obtenerHora() {
      int pos = 0;
      String hora = null;

    try{
      java.util.Date fecha = new java.util.Date(System.currentTimeMillis());
      String horas = String.valueOf(fecha.getHours());
      String mins = String.valueOf(fecha.getMinutes());
      String segs = String.valueOf(fecha.getSeconds());
      if(horas.length()<2){
        horas = "0" + horas;
        if(horas.length()<2){
          horas = "0" + horas;
        }
      }
      if(mins.length()<2){
        mins = "0" + mins;
        if(mins.length()<2){
          mins = "0" + mins;
        }
      }
      if(segs.length()<2){
        segs = "0" + segs;
        if(segs.length()<2){
          segs = "0" + segs;
        }
      }
      hora = horas + mins +segs;

    } catch (Exception e){
        //logger.error
        System.out.println("SQL_PASO10 hora 12:"+e);
    } finally{
            return hora;
    }
  }*/

    public String obtenerHora() {
        java.util.Date fHoy= new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        return sdf.format(fHoy);
    }

}
