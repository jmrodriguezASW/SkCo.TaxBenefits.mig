package TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD;

import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.Thread.*;
import java.sql.*;
import java.text.DecimalFormat;
import com.ibm.as400.access.*;
import TBPKT_UTILIDADES.TBPKT_AS400_APORTES.*;
/*
 * Modificado 09/04/2008
 * Marcela Ortiz Sandoval
 * Agregar funcion TBF_CSAF
 */

public class TBCL_AS400 extends Object {

  //Metodo que calcula el saldo del contrato del AS400
  public static double TBF_SALDO_CONTRATO(String v_contrato, String v_fecha){
    /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Saldos_por_Contrato de la clase TBCL_FUNCIONES_AS400_APORTES, no es necesaria la instancia nueva*/
    //TBCL_FUNCIONES_AS400_APORTES Saldos_por_Contrato = new TBCL_FUNCIONES_AS400_APORTES();
    //TBCL_FUNCIONES_AS400_APORTES saldo_entero        = new TBCL_FUNCIONES_AS400_APORTES();
    String cadenaT;
    long saldo_as400 = 0;
    try{
      //Llamado a la funcion del As400
      cadenaT = TBCL_FUNCIONES_AS400_APORTES.TBFL_Saldos_por_Contrato(v_contrato, v_fecha, "E");
      //Si ocurre un error al retornar el saldo del contrato en el AS400
      //retornar -1
      if (cadenaT != "ERROR"){
        //Se captura el valor retornado por la funcion del As400
        saldo_as400 = TBCL_FUNCIONES_AS400_APORTES.TBFL_Saldo_Entero(cadenaT);

      }
      else{
        saldo_as400 = -2;
      }

      if(saldo_as400 != -1 && saldo_as400 != -2)
      {
       Long    l1 = new Long(saldo_as400);
       double  d1 = l1.doubleValue();
       d1 = d1/100;
       return d1;
      }
      else
       return saldo_as400;
    }
    catch(Exception e)
    {
      return -2;
    }
  }//METODO TBF_SALDO_CONTRATO

  //Metodo que calcula el saldo del contrato del AS400 sin conexion
  public static double TBF_SALDO_CONTRATO_P(String v_contrato, String v_fecha, AS400 as400, String v_libreria){
    /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Saldos_por_Contrato de la clase TBCL_FUNCIONES_AS400_APORTES, no es necesaria la instancia nueva*/
    //TBCL_FUNCIONES_AS400_APORTES Saldos_por_Contrato = new TBCL_FUNCIONES_AS400_APORTES();
    //TBCL_FUNCIONES_AS400_APORTES saldo_entero        = new TBCL_FUNCIONES_AS400_APORTES();
    String cadenaT;
    long saldo_as400 = 0;
    try{
      //Llamado a la funcion del As400
      cadenaT = TBCL_FUNCIONES_AS400_APORTES.TBFL_Saldos_por_Contrato(v_contrato, v_fecha, "E", as400, v_libreria);
      //Si ocurre un error al retornar el saldo del contrato en el AS400
      //retornar -1
      if (cadenaT != "ERROR"){
        //Se captura el valor retornado por la funcion del As400
        saldo_as400 = TBCL_FUNCIONES_AS400_APORTES.TBFL_Saldo_Entero(cadenaT);

      }
      else{
        saldo_as400 = -2;
      }
      if(saldo_as400 != -1 && saldo_as400 != -2 )
      {
       Long    l1 = new Long(saldo_as400);
       double  d1 = l1.doubleValue();
       d1 = d1/100;
       return d1;
      }
     else
        return saldo_as400;

    }
    catch(Exception e)
    {
      return -2;
    }
  }//METODO TBF_SALDO_CONTRATO sin conexion


  //Procedimiento que llama al Paso 6 de Interfaces de retiros
 public static int TBF_PASO6_S(String v_fecha_control, String v_sistema,String v_usuario,String v_pass,String v_libreria)
 {
  AS400 as400 = new AS400(""+v_sistema+""); /**Definir variable tipo sistema as400*/
  int v_cod_err = 0;
  try
  {
   /**Conectar al sistema as400 con usuario y password*/
   as400.setUserId(""+v_usuario+"");
   as400.setPassword(""+v_pass+"");
   /**Declar y definir la lista de parametros, configurar tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1),numero de contrato(9),*/
   /**el parametro de salida será un campo Y o N que identifica si el contrato tiene bloqueo de egresos o no.*/

   ProgramParameter[] v_parametros = new ProgramParameter[2];/**Parametros del procedimiento del as400*/

   /**Parametros de entrada*/
   AS400Text text1 = new AS400Text(2);/**Variable que toma parametro dummy.*/
   byte[] v_dummy = text1.toBytes(" ");/**Definir parametro dummy*/
   v_parametros[0] = new ProgramParameter( v_dummy );

   AS400Text text2 = new AS400Text(10);/**Variable que toma parametro  contrato*/
   byte[] p_fecha = text2.toBytes(v_fecha_control);/**Definir parametro contrato.*/
   v_parametros[1] = new ProgramParameter(p_fecha );

   /**Realizar llamado al programa del as400.*/
   ProgramCall p_ajkgxfr = new ProgramCall(as400,""+v_libreria+"AJMXXFR.PGM",v_parametros);

   /**Si el programa corre*/
   if(p_ajkgxfr.run())
   {
    /**Tomar parametro de salida*/
      System.out.println("corrio el procedimiento del AS400");
      v_cod_err = 0;
   }
   /** Si no se corre el programa*/
   else
   {
    as400.disconnectService(AS400.COMMAND);
    return -4;
   }
    return v_cod_err;
  }
  //si hay algun error
  catch (Exception e)
  {
   as400.disconnectService(AS400.COMMAND);
   return -5;
  }
 }
 
  //Procedimiento que llama al Paso 6 de Interfaces de retiros
  //Este metodo esta en la base de datos como un store procedure, en caso de requerir el 
  //llamado unicamente al paso 6.
  public int TBF_PASO6(String v_fecha_control, AS400 as400, String v_libreria){
    int v_cod_err = 0;
    try{
      //declaro y defino la lista de parametros, aquí configuro tanto los de entrada como los de salida
      //los parametros de entrada serán dummy(1),fecha de proceso AAAAMMDD
      ProgramParameter[] v_parametros = new ProgramParameter[2];
      //parametros de entrada
      AS400Text text1 = new AS400Text(2);
      byte[] v_dummy = text1.toBytes(" ");
      v_parametros[0] = new ProgramParameter( v_dummy );
      AS400Text text2 = new AS400Text(10);
      byte[] p_fecha = text2.toBytes(v_fecha_control);
      v_parametros[1] = new ProgramParameter(p_fecha );
      //realizo llamado al programa del as400
      ProgramCall p_ajmxxfr = new ProgramCall(as400,""+v_libreria+"AJMXXFR.PGM",v_parametros);
      //si el programa corre
      if(p_ajmxxfr.run()){
        System.out.println("corrio el procedimiento del AS400");
        v_cod_err = 0;
      }
      // si no se corre el programa
      else{
        System.out.println("No corrio el procedimiento del AS400");
        v_cod_err = -2;
      }
      return v_cod_err;
    }
    //si hay algun error
    catch (Exception e){
      System.out.println("Error as400 "+e);
      return -1;
    }
  }//METODO TBP_PASO6

  /* 
   * TBF_CSAF
   * Ejecuta el proceso de CSAF
   * Returna 0 si se ejecuto correctamente, -2 si no se ejecuta, -1 si retorna 
   * error en el 400.
   */
  public int TBF_CSAF(AS400 as400, String v_libreria){
    int v_cod_err = 0;
    try{
      //declaro y defino la lista de parametros, aquí configuro tanto los de entrada como los de salida
      //los parametros de entrada serán dummy(1)
      ProgramParameter[] v_parametros = new ProgramParameter[1];
      //parametros de entrada
      AS400Text text1 = new AS400Text(2);
      byte[] v_dummy = text1.toBytes(" ");
      v_parametros[0] = new ProgramParameter( v_dummy );
      //realizo llamado al programa del as400
      ProgramCall p_ajmxxfr = new ProgramCall(as400,""+v_libreria+"C2IVXFR.PGM",v_parametros);
      //si el programa corre
      if(p_ajmxxfr.run()){
        System.out.println("corrio el procedimiento del AS400");
        v_cod_err = 0;
      }
      // si no se corre el programa
      else{
        System.out.println("No corrio el procedimiento del AS400");
        v_cod_err = -2;
      }
      return v_cod_err;
    }
    //si hay algun error
    catch (Exception e){
      System.out.println("Error as400 "+e);
      return -1;
    }
  }//METODO TBF_CSAF

}//CLASE


