package TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_MODULO_APORTES.*;
import java.io.*;
import java.util.*;
import oracle.jdbc.OracleTypes.*;
import com.ibm.as400.access.*;
import java.sql.*;
import oracle.jdbc.*;
import java.math.*;


/***********************************************************************
 * TBCL_FuncionesAs400
 * Modificaciones
 * 2007-07-23  Agregar Función TBPL_InfoAgente
 * 2007-08-01  Agregar Función TBPL_GenerarCajasPrimas
 * 2009-10-27  Agregar Función TBPL_ValidarSaro
 * 2009-11-03  Agregar Función TBPL_TipoOperacionTraslado
 * ********************************************************************/

/**Clase que tiene las funciones que llaman los procedimientos del as400.*/
public class TBCL_FuncionesAs400 extends Object
{
 /**Función que consulta al as400 si el contrato tiene bloqueo de egresos. Procedimiento AJKGXFR.*/
 public static String TBFL_BloqueoEgresos(String v_contrato,String v_sistema,String v_usuario,String v_pass,String v_libreria)
 {
  AS400 as400 = new AS400(""+v_sistema+""); /**Definir variable tipo sistema as400*/
  try
  {
   /**Conectar al sistema as400 con usuario y password*/
   as400.setUserId(""+v_usuario+"");
   as400.setPassword(""+v_pass+"");
   /**Declar y definir la lista de parametros, configurar tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1),numero de contrato(9),*/
   /**el parametro de salida será un campo Y o N que identifica si el contrato tiene bloqueo de egresos o no.*/

   ProgramParameter[] v_parametros = new ProgramParameter[3];/**Parametros del procedimiento del as400*/

   /**Parametros de entrada*/
   AS400Text text1 = new AS400Text(2);/**Variable que toma parametro dummy.*/
   byte[] v_dummy = text1.toBytes(" ");/**Definir parametro dummy*/
   v_parametros[0] = new ProgramParameter( v_dummy );

   AS400Text text2 = new AS400Text(10);/**Variable que toma parametro  contrato*/
   byte[] p_contrato = text2.toBytes(v_contrato);/**Definir parametro contrato.*/
   v_parametros[1] = new ProgramParameter(p_contrato );

   /**Parametros de salida*/
   v_parametros[2] = new ProgramParameter( 1 );

   /**Realizar llamado al programa del as400.*/
   ProgramCall p_ajkgxfr = new ProgramCall(as400,""+v_libreria+"AJKGXFR.PGM",v_parametros);

   /**Si el programa corre*/
   if(p_ajkgxfr.run())
   {
    /**Tomar parametro de salida*/
    AS400Text text4 = new AS400Text(1);
    byte[] v_bloqueo = v_parametros[2].getOutputData();
    String  v_bloqegre =  (String)text4.toObject(v_bloqueo);
    as400.disconnectService(AS400.COMMAND);
    return v_bloqegre;
   }
   /** Si no se corre el programa*/
   else
   {
    as400.disconnectService(AS400.COMMAND);
    return "No se corrio el programa";
   }
  }
  //si hay algun error
  catch (Exception e)
  {
   as400.disconnectService(AS400.COMMAND);
   return "error"+e;
  }
 }

/**Función que consulta al as400 los codigos de banco y numero de cuanta para un contrato. Procedimiento AJKHXFR.*/
 public static String TBFL_CuentasBancarias(String v_contrato,String v_sistema,String v_usuario,String v_pass,String v_libreria)
 {
  /**Definir variable tipo sistema as400*/
  AS400 as400 = new AS400(""+v_sistema+"");
  try
  {
   /**Conectar al sistema as400 con usuario y password*/
   as400.setUserId(""+v_usuario+"");
   as400.setPassword(""+v_pass+"");
   /**Declarar y definir la lista de parametros, se configura tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1),numero de contrato(9),*/
   /**el parametros de salida serán cadena1,cadena2,cadena3,cadena4,cadena5*/

   ProgramParameter[] v_parametros = new ProgramParameter[7];/**Parametros del procedimiento del as400*/

   /**Parametros de entrada*/
   AS400Text text1 = new AS400Text(2);/**Variable que toma parametro dummy.*/
   byte[] v_dummy = text1.toBytes(" ");/**Definir parametro dummy*/
   v_parametros[0] = new ProgramParameter( v_dummy );

   AS400Text text2 = new AS400Text(10);/**Variable que toma parametro  contrato*/
   byte[] p_contrato = text2.toBytes(v_contrato);/**Definir parametro contrato.*/
   v_parametros[1] = new ProgramParameter(p_contrato );

   v_parametros[2] = new ProgramParameter(200);
   v_parametros[3] = new ProgramParameter(200);
   v_parametros[4] = new ProgramParameter(200);
   v_parametros[5] = new ProgramParameter(200);
   v_parametros[6] = new ProgramParameter(200);

   /**Realizar llamado al programa del as400.*/
   ProgramCall p_ajkhxfr = new ProgramCall(as400,""+v_libreria+"AJKHXFR.PGM",v_parametros);
   //si el programa corre
   if(p_ajkhxfr.run())
   {
    String cadena = new String();

    //capturo cadena1
    AS400Text tx  = new AS400Text(200);
    byte[] b       = v_parametros[2].getOutputData();
    cadena += (String)tx.toObject(b);
    //capturo cadena2
    AS400Text tx2   = new AS400Text(200);
    byte[] b2       = v_parametros[3].getOutputData();
    cadena += (String)tx2.toObject(b2);
    //capturo cadena3
    AS400Text tx3   = new AS400Text(200);
    byte[] b3       = v_parametros[4].getOutputData();
    cadena +=(String)tx3.toObject(b3);
    //capturo cadena4
    AS400Text tx4   = new AS400Text(200);
    byte[] b4       = v_parametros[5].getOutputData();
    cadena  +=(String)tx4.toObject(b4);
    //capturo cadena5
    AS400Text tx5   = new AS400Text(200);
    byte[] b5       = v_parametros[6].getOutputData();
    cadena +=(String)tx5.toObject(b5);
    as400.disconnectService(AS400.COMMAND);
    return cadena;
   }
   // si no se corre el programa
   else
   {
    as400.disconnectService(AS400.COMMAND);
    return "Error el programa del AS400 para consultar cuentas bancarias no corrio.";
   }
  }
  //si hay algun error
  catch (Exception e)
  {
   as400.disconnectService(AS400.COMMAND);
   return  "Error "+e;
   }
 }

/**Función que consulta al as400 los codigos de banco y numero de cuanta para un contrato. Procedimiento AJKHXFR.*/
 public static String TBFL_CuentasBancarias1(String v_contrato,String v_sistema,String v_usuario,String v_pass,String v_libreria,String v_tipocuentas)
 {
  /**Definir variable tipo sistema as400*/
  AS400 as400 = new AS400(""+v_sistema+"");
  try
  {
   /**Conectar al sistema as400 con usuario y password*/
   as400.setUserId(""+v_usuario+"");
   as400.setPassword(""+v_pass+"");
   /**Declarar y definir la lista de parametros, se configura tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1),numero de contrato(9),*/
   /**el parametros de salida serán cadena1,cadena2,cadena3,cadena4,cadena5*/

   ProgramParameter[] v_parametros = new ProgramParameter[8];/**Parametros del procedimiento del as400*/


   /**Parametros de entrada*/
   AS400Text text1 = new AS400Text(2);/**Variable que toma parametro dummy.*/
   byte[] v_dummy = text1.toBytes(" ");/**Definir parametro dummy*/
   v_parametros[0] = new ProgramParameter( v_dummy );

   AS400Text text2 = new AS400Text(10);/**Variable que toma parametro  contrato*/
   byte[] p_contrato = text2.toBytes(v_contrato);/**Definir parametro contrato.*/
   v_parametros[1] = new ProgramParameter(p_contrato );

   AS400Text text3 = new AS400Text(2);/**Variable que toma el tipo de cuentas a consultar*/
   byte[] p_tipo_cuenta = text3.toBytes(v_tipocuentas);/**Definir parametro tipo cuentas*/
   v_parametros[2] = new ProgramParameter(p_tipo_cuenta);


   /**Parametros de salida*/

   v_parametros[3] = new ProgramParameter(200);
   v_parametros[4] = new ProgramParameter(200);
   v_parametros[5] = new ProgramParameter(200);
   v_parametros[6] = new ProgramParameter(200);
   v_parametros[7] = new ProgramParameter(200);


   /**Realizar llamado al programa del as400.*/
   ProgramCall p_ajkhxfr = new ProgramCall(as400,""+v_libreria+"AJKHXFR.PGM",v_parametros);
   //si el programa corre
   if(p_ajkhxfr.run())
   {
    String cadena = new String();

    AS400Text tx  = new AS400Text(200);
    byte[] b       = v_parametros[3].getOutputData();
    cadena += (String)tx.toObject(b);
    //capturo cadena2
    AS400Text tx2   = new AS400Text(200);
    byte[] b2       = v_parametros[4].getOutputData();
    cadena += (String)tx2.toObject(b2);
    //capturo cadena3
    AS400Text tx3   = new AS400Text(200);
    byte[] b3       = v_parametros[5].getOutputData();
    cadena +=(String)tx3.toObject(b3);
    //capturo cadena4
    AS400Text tx4   = new AS400Text(200);
    byte[] b4       = v_parametros[6].getOutputData();
    cadena  +=(String)tx4.toObject(b4);
    //capturo cadena5
    AS400Text tx5   = new AS400Text(200);
    byte[] b5       = v_parametros[7].getOutputData();
    cadena +=(String)tx5.toObject(b5);


    as400.disconnectService(AS400.COMMAND);
    return cadena;
   }
   // si no se corre el programa
   else
   {
    as400.disconnectService(AS400.COMMAND);
    return "Error el programa del AS400 para consultar cuentas bancarias no corrio.";
   }
  }
  //si hay algun error
  catch (Exception e)
  {
   as400.disconnectService(AS400.COMMAND);
   return  "Error "+e;
   }
 }


 /**Función que consulta al as400 los saldos por fondo para un contrato. Procedimiento AJIIXFR.*/
 public static String TBPL_SaldosPorContrato(String v_contrato, String v_fecha,String v_proefe,String v_sistema,String v_usuario,String v_pass,String v_libreria)
 {
  try
  {
   /**Definir variable tipo sistema as400*/
   AS400 sadc2 = new AS400(""+v_sistema+"");
   /**Conectar al sistema as400 con usuario y password*/
   sadc2.setUserId(""+v_usuario+"");
   sadc2.setPassword(""+v_pass+"");

   /**Declar y definir la lista de parametros, aquí se configura tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1),numero de contrato(9),fecha de consulta(8) y fecha autilizar en la busqueda(1),*/
   /**los parametros de salida serán fecha consulta,cadena1,cadena2,cadena3,cadena4 y cadena5.*/
   /**en estas ultimas vendrán los saldos por fondo separados por punto y coma*/
   ProgramParameter[] parametros = new ProgramParameter[10];/**Parametros del procedimiento del as400*/
   /**Parametros de entrada*/
   AS400Text t1         = new AS400Text(1);/**Variable que toma parametro dummy*/
   byte[] by1           = t1.toBytes(" ");/**Definir parametro dummy.*/
   parametros[0]        = new ProgramParameter(by1);

   AS400Text t2         = new AS400Text(9);/**Variable que toma parametro contrato*/
   byte[] by2           = t2.toBytes(v_contrato);/**Definir parametro contrato.*/
   parametros[1]        = new ProgramParameter(by2);

   AS400Text t3         = new AS400Text(8);/**Variable que toma parametro fecha consulta*/
   byte[] by3           = t3.toBytes(v_fecha);/**Definir parametro fecha consulta.*/
   parametros[2]        = new ProgramParameter(by3);

   AS400Text t4         = new AS400Text(8);/**Variable que toma indicador de efectiva o proceso*/
   byte[] by4           = t4.toBytes(v_proefe);/**Definir indicador de fecha.*/
   parametros[3]        = new ProgramParameter(by4);

   /**Parametros de salida*/
   parametros[4]        = new ProgramParameter(8);
   parametros[5]        = new ProgramParameter(200);
   parametros[6]        = new ProgramParameter(200);
   parametros[7]        = new ProgramParameter(200);
   parametros[8]        = new ProgramParameter(200);
   parametros[9]        = new ProgramParameter(200);

   /**Realizar llamado al programa del as400.*/
   ProgramCall programa = new ProgramCall(sadc2,""+v_libreria+"AJIIXFR.PGM",parametros);
   //capturar valores, eliminar espacios Y convewrtir a mayuscula
   if(programa.run())
   {//capturar fecha de consulta
    AS400Text tx1 = new AS400Text(8);
    byte[] b1     = parametros[4].getOutputData();
    String fecha  = (String)tx1.toObject(b1);
    //capturar cadena1
    AS400Text tx2   = new AS400Text(200);
    byte[] b2       = parametros[5].getOutputData();
    String cadena1  = (String)tx2.toObject(b2);
    cadena1=cadena1.trim();cadena1=cadena1.toUpperCase();
    //capturar cadena2
    AS400Text tx3   = new AS400Text(200);
    byte[] b3       = parametros[6].getOutputData();
    String cadena2  = (String)tx3.toObject(b3);
    cadena2=cadena2.trim();cadena2=cadena2.toUpperCase();
    //capturar cadena3
    AS400Text tx4   = new AS400Text(200);
    byte[] b4       = parametros[7].getOutputData();
    String cadena3  =(String)tx4.toObject(b4);
    cadena3=cadena3.trim();cadena3=cadena3.toUpperCase();
    //capturar cadena4
    AS400Text tx5   = new AS400Text(200);
    byte[] b5       = parametros[8].getOutputData();
    String cadena4  =(String)tx5.toObject(b5);
    cadena4=cadena4.trim();cadena4=cadena4.toUpperCase();
    //capturar cadena5
    AS400Text tx6   = new AS400Text(200);
    byte[] b6       = parametros[9].getOutputData();
    String cadena5  =(String)tx6.toObject(b6);
    cadena5=cadena5.trim();cadena5=cadena5.toUpperCase();
    //por cada array de char mueva hasta al final, insertando todo aquello que sea diferente de punto y coma  en un array de int
    sadc2.disconnectAllServices();
    return cadena1+cadena2+cadena3+cadena4+cadena5;
   }
   else
   {
    sadc2.disconnectAllServices();
    return "Error el programa del AS400 para consultar saldos del contrato no corrio.";
   }
  }
  catch(Exception ex){return "Error "+ex;}
 }
 

 /**Función que consulta al as400 el saldo en fondos cerrados para un contrato. Procedimiento C2B0XFR.*/
 public static String TBPL_SaldoFCerrPorContrato(String v_contrato, String v_fecha,String v_proefe,String v_sistema,String v_usuario,String v_pass,String v_libreria)
     {
      try
      {
       /**Definir variable tipo sistema as400*/
       AS400 sadc2 = new AS400(""+v_sistema+"");
       /**Conectar al sistema as400 con usuario y password*/
       sadc2.setUserId(""+v_usuario+"");
       sadc2.setPassword(""+v_pass+"");
    
       /**Declar y definir la lista de parametros, aquí se configura tanto los de entrada como los de salida*/
       /**los parametros de entrada serán dummy(1),numero de contrato(9),fecha de consulta(8) y fecha autilizar en la busqueda(1),*/
       /**los parametros de salida serán fecha consulta,cadena1,cadena2,cadena3,cadena4 y cadena5.*/
       /**en estas ultimas vendrán los saldos por fondo separados por punto y coma*/
       ProgramParameter[] parametros = new ProgramParameter[6];/**Parametros del procedimiento del as400*/
       /**Parametros de entrada*/
       AS400Text t1         = new AS400Text(1);/**Variable que toma parametro dummy*/
       byte[] by1           = t1.toBytes(" ");/**Definir parametro dummy.*/
       parametros[0]        = new ProgramParameter(by1);
    
       AS400Text t2         = new AS400Text(9);/**Variable que toma parametro contrato*/
       byte[] by2           = t2.toBytes(v_contrato);/**Definir parametro contrato.*/
       parametros[1]        = new ProgramParameter(by2);
    
       AS400Text t3         = new AS400Text(8);/**Variable que toma parametro fecha consulta*/
       byte[] by3           = t3.toBytes(v_fecha);/**Definir parametro fecha consulta.*/
       parametros[2]        = new ProgramParameter(by3);
    
       AS400Text t4         = new AS400Text(8);/**Variable que toma indicador de efectiva o proceso*/
       byte[] by4           = t4.toBytes(v_proefe);/**Definir indicador de fecha.*/
       parametros[3]        = new ProgramParameter(by4);
    
       /**Parametros de salida*/
       parametros[4]        = new ProgramParameter(8);
       parametros[5]        = new ProgramParameter(200);

    
       /**Realizar llamado al programa del as400.*/
       ProgramCall programa = new ProgramCall(sadc2,""+v_libreria+"C2B0XFR.PGM",parametros);
       //capturar valores, eliminar espacios Y convewrtir a mayuscula
       if(programa.run())
       {//capturar fecha de consulta
        AS400Text tx1 = new AS400Text(8);
        byte[] b1     = parametros[4].getOutputData();
        String fecha  = (String)tx1.toObject(b1);
        //capturar cadena1
        AS400Text tx2   = new AS400Text(200);
        byte[] b2       = parametros[5].getOutputData();
        String cadena1  = (String)tx2.toObject(b2);
        cadena1=cadena1.trim();cadena1=cadena1.toUpperCase();
        
        //por cada array de char mueva hasta al final, insertando todo aquello que sea diferente de punto y coma  en un array de int
        sadc2.disconnectAllServices();
        return cadena1;
       }
       else
       {
        sadc2.disconnectAllServices();
        return "Error el programa del AS400 para consultar saldos del contrato no corrio.";
       }
      }
      catch(Exception ex){return "Error "+ex;}
     }
     
 
 /* Función que consulta al as400 los saldos por fondo para un contrato, en fondos de liquidez.
  * Procedimiento C3BKXFR.
  * Agregado por Marcela Ortiz Sandoval
  * 2009/02/19 */
 public static String TBPL_SaldosLiqPorContrato(String v_contrato, String v_fecha,String v_proefe,String v_sistema,String v_usuario,String v_pass,String v_libreria)
 {
  try
  {
   /**Definir variable tipo sistema as400*/
   AS400 sadc2 = new AS400(""+v_sistema+"");
   /**Conectar al sistema as400 con usuario y password*/
   sadc2.setUserId(""+v_usuario+"");
   sadc2.setPassword(""+v_pass+"");

   /**Declar y definir la lista de parametros, aquí se configura tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1),numero de contrato(9),fecha de consulta(8) y fecha autilizar en la busqueda(1),*/
   /**los parametros de salida serán fecha consulta,cadena1,cadena2,cadena3,cadena4 y cadena5.*/
   /**en estas ultimas vendrán los saldos por fondo separados por punto y coma*/
   ProgramParameter[] parametros = new ProgramParameter[10];/**Parametros del procedimiento del as400*/
   /**Parametros de entrada*/
   AS400Text t1         = new AS400Text(1);/**Variable que toma parametro dummy*/
   byte[] by1           = t1.toBytes(" ");/**Definir parametro dummy.*/
   parametros[0]        = new ProgramParameter(by1);

   AS400Text t2         = new AS400Text(9);/**Variable que toma parametro contrato*/
   byte[] by2           = t2.toBytes(v_contrato);/**Definir parametro contrato.*/
   parametros[1]        = new ProgramParameter(by2);

   AS400Text t3         = new AS400Text(8);/**Variable que toma parametro fecha consulta*/
   byte[] by3           = t3.toBytes(v_fecha);/**Definir parametro fecha consulta.*/
   parametros[2]        = new ProgramParameter(by3);

   AS400Text t4         = new AS400Text(8);/**Variable que toma indicador de efectiva o proceso*/
   byte[] by4           = t4.toBytes(v_proefe);/**Definir indicador de fecha.*/
   parametros[3]        = new ProgramParameter(by4);

   /**Parametros de salida*/
   parametros[4]        = new ProgramParameter(8);
   parametros[5]        = new ProgramParameter(200);
   parametros[6]        = new ProgramParameter(200);
   parametros[7]        = new ProgramParameter(200);
   parametros[8]        = new ProgramParameter(200);
   parametros[9]        = new ProgramParameter(200);

   /**Realizar llamado al programa del as400.*/
   ProgramCall programa = new ProgramCall(sadc2,""+v_libreria+"C3BKXFR.PGM",parametros);
   //capturar valores, eliminar espacios Y convewrtir a mayuscula
   if(programa.run())
   {//capturar fecha de consulta
    AS400Text tx1 = new AS400Text(8);
    byte[] b1     = parametros[4].getOutputData();
    String fecha  = (String)tx1.toObject(b1);
    //capturar cadena1
    AS400Text tx2   = new AS400Text(200);
    byte[] b2       = parametros[5].getOutputData();
    String cadena1  = (String)tx2.toObject(b2);
    cadena1=cadena1.trim();cadena1=cadena1.toUpperCase();
    //capturar cadena2
    AS400Text tx3   = new AS400Text(200);
    byte[] b3       = parametros[6].getOutputData();
    String cadena2  = (String)tx3.toObject(b3);
    cadena2=cadena2.trim();cadena2=cadena2.toUpperCase();
    //capturar cadena3
    AS400Text tx4   = new AS400Text(200);
    byte[] b4       = parametros[7].getOutputData();
    String cadena3  =(String)tx4.toObject(b4);
    cadena3=cadena3.trim();cadena3=cadena3.toUpperCase();
    //capturar cadena4
    AS400Text tx5   = new AS400Text(200);
    byte[] b5       = parametros[8].getOutputData();
    String cadena4  =(String)tx5.toObject(b5);
    cadena4=cadena4.trim();cadena4=cadena4.toUpperCase();
    //capturar cadena5
    AS400Text tx6   = new AS400Text(200);
    byte[] b6       = parametros[9].getOutputData();
    String cadena5  =(String)tx6.toObject(b6);
    cadena5=cadena5.trim();cadena5=cadena5.toUpperCase();
    //por cada array de char mueva hasta al final, insertando todo aquello que sea diferente de punto y coma  en un array de int
    sadc2.disconnectAllServices();
    return cadena1+cadena2+cadena3+cadena4+cadena5;
   }
   else
   {
    sadc2.disconnectAllServices();
    return "Error el programa del AS400 para consultar saldos del contrato no corrio.";
   }
  }
  catch(Exception ex){return "Error "+ex;}
 }
 
    /* Función que consulta al as400 los saldos por fondo para un contrato, en fondos de liquidez.
     * Procedimiento C3PMXFR.
     * Agregado por Marcela Ortiz Sandoval
     * 2013/09/17 */
    public static String TBPL_DisponiblesPorContrato(String v_contrato, String v_fecha,String v_proefe,String v_sistema,String v_usuario,String v_pass,String v_libreria)
    {
     try
     {
      /**Definir variable tipo sistema as400*/
      AS400 sadc2 = new AS400(""+v_sistema+"");
      /**Conectar al sistema as400 con usuario y password*/
      sadc2.setUserId(""+v_usuario+"");
      sadc2.setPassword(""+v_pass+"");

      /**Declar y definir la lista de parametros, aquí se configura tanto los de entrada como los de salida*/
      /**los parametros de entrada serán dummy(1),numero de contrato(9),fecha de consulta(8) y fecha autilizar en la busqueda(1),*/
      /**los parametros de salida serán fecha consulta,cadena1,cadena2,cadena3,cadena4 y cadena5.*/
      /**en estas ultimas vendrán los saldos por fondo separados por punto y coma*/
      ProgramParameter[] parametros = new ProgramParameter[9];/**Parametros del procedimiento del as400*/
      /**Parametros de entrada*/
      AS400Text t1         = new AS400Text(1);/**Variable que toma parametro dummy*/
      byte[] by1           = t1.toBytes(" ");/**Definir parametro dummy.*/
      parametros[0]        = new ProgramParameter(by1);

      AS400Text t2         = new AS400Text(9);/**Variable que toma parametro contrato*/
      byte[] by2           = t2.toBytes(v_contrato);/**Definir parametro contrato.*/
      parametros[1]        = new ProgramParameter(by2);

      AS400Text t3         = new AS400Text(8);/**Variable que toma parametro fecha consulta*/
      byte[] by3           = t3.toBytes(v_fecha);/**Definir parametro fecha consulta.*/
      parametros[2]        = new ProgramParameter(by3);

      AS400Text t4         = new AS400Text(8);/**Variable que toma indicador de efectiva o proceso*/
      byte[] by4           = t4.toBytes(v_proefe);/**Definir indicador de fecha.*/
      parametros[3]        = new ProgramParameter(by4);

      /**Parametros de salida*/
      parametros[4]        = new ProgramParameter(8);
      parametros[5]        = new ProgramParameter(17);
      parametros[6]        = new ProgramParameter(17);
      parametros[7]        = new ProgramParameter(17);
      parametros[8]        = new ProgramParameter(4);
      //parametros[9]        = new ProgramParameter(200);

      /**Realizar llamado al programa del as400.*/
      ProgramCall programa = new ProgramCall(sadc2,""+v_libreria+"C3PMXFR.PGM",parametros);
      //capturar valores, eliminar espacios Y convewrtir a mayuscula
      if(programa.run())
      {//capturar fecha de consulta
       AS400Text tx1 = new AS400Text(8);
       byte[] b1     = parametros[4].getOutputData();
       String fecha  = (String)tx1.toObject(b1);
       //capturar cadena1
       AS400Text tx2   = new AS400Text(17);
       byte[] b2       = parametros[5].getOutputData();
       String cadena1  = (String)tx2.toObject(b2);
       cadena1=cadena1.trim();cadena1=cadena1.toUpperCase();
       //capturar cadena2
       AS400Text tx3   = new AS400Text(17);
       byte[] b3       = parametros[6].getOutputData();
       String cadena2  = (String)tx3.toObject(b3);
       cadena2=cadena2.trim();cadena2=cadena2.toUpperCase();
       //capturar cadena3
       AS400Text tx4   = new AS400Text(17);
       byte[] b4       = parametros[7].getOutputData();
       String cadena3  =(String)tx4.toObject(b4);
       cadena3=cadena3.trim();cadena3=cadena3.toUpperCase();
       //capturar cadena4
       AS400Text tx5   = new AS400Text(4);
       byte[] b5       = parametros[8].getOutputData();
       String cadena4  =(String)tx5.toObject(b5);
       cadena4=cadena4.trim();cadena4=cadena4.toUpperCase();
       //por cada array de char mueva hasta al final, insertando todo aquello que sea diferente de punto y coma  en un array de int
       sadc2.disconnectAllServices();
       return cadena1+";"+cadena2+";"+cadena3+";"+cadena4+";";
      }
      else
      {
       sadc2.disconnectAllServices();
       return "Error el programa del AS400 para consultar saldos del contrato no corrio.";
      }
     }
     catch(Exception ex){return "Error "+ex;}
    }

    /* Función que consulta al as400 el saldo para fondos cerrados de un contrato, en fondos de liquidez.
     * Procedimiento C3C4XFR.
     * Agregado por Marcela Ortiz Sandoval
     * 2009/05/04 */
    public static String TBPL_SaldoLiquidezContrato(String v_contrato, String v_fecha,String v_proefe, String v_catFondo, String v_sistema,String v_usuario,String v_pass,String v_libreria)
    {
     try
     {
      /**Definir variable tipo sistema as400*/
      AS400 sadc2 = new AS400(""+v_sistema+"");
      /**Conectar al sistema as400 con usuario y password*/
      sadc2.setUserId(""+v_usuario+"");
      sadc2.setPassword(""+v_pass+"");

      /**Declar y definir la lista de parametros, aquí se configura tanto los de entrada como los de salida*/
      /**los parametros de entrada serán dummy(1),numero de contrato(9),fecha de consulta(8) y fecha autilizar en la busqueda(1),*/
      /**los parametros de salida serán fecha consulta,cadena1,cadena2,cadena3,cadena4 y cadena5.*/
      /**en estas ultimas vendrán los saldos por fondo separados por punto y coma*/
      ProgramParameter[] parametros = new ProgramParameter[7];/**Parametros del procedimiento del as400*/
      /**Parametros de entrada*/
      AS400Text t1         = new AS400Text(1);/**Variable que toma parametro dummy*/
      byte[] by1           = t1.toBytes(" ");/**Definir parametro dummy.*/
      parametros[0]        = new ProgramParameter(by1);

      AS400Text t2         = new AS400Text(9);/**Variable que toma parametro contrato*/
      byte[] by2           = t2.toBytes(v_contrato);/**Definir parametro contrato.*/
      parametros[1]        = new ProgramParameter(by2);

      AS400Text t3         = new AS400Text(8);/**Variable que toma parametro fecha consulta*/
      byte[] by3           = t3.toBytes(v_fecha);/**Definir parametro fecha consulta.*/
      parametros[2]        = new ProgramParameter(by3);

      AS400Text t4         = new AS400Text(8);/**Variable que toma indicador de efectiva o proceso*/
      byte[] by4           = t4.toBytes(v_proefe);/**Definir indicador de fecha.*/
      parametros[3]        = new ProgramParameter(by4);
      
      AS400Text t5         = new AS400Text(8);/**Variable que indica la categoria del fondo */
      byte[] by5           = t5.toBytes(v_catFondo);/**Definir indicador de categoria.*/
      parametros[4]        = new ProgramParameter(by5);

      /**Parametros de salida*/
      parametros[5]        = new ProgramParameter(8);
      parametros[6]        = new ProgramParameter(200);


      /**Realizar llamado al programa del as400.*/
      ProgramCall programa = new ProgramCall(sadc2,""+v_libreria+"C3C4XFR.PGM",parametros);
      //capturar valores, eliminar espacios Y convewrtir a mayuscula
      if(programa.run())
      {//capturar fecha de consulta
       AS400Text tx1 = new AS400Text(8);
       byte[] b1     = parametros[5].getOutputData();
       String fecha  = (String)tx1.toObject(b1);
       
       
       //capturar cadena1
       AS400Text tx2   = new AS400Text(200);
       byte[] b2       = parametros[6].getOutputData();
       String cadena1  = (String)tx2.toObject(b2);
       cadena1=cadena1.trim();cadena1=cadena1.toUpperCase();

       //por cada array de char mueva hasta al final, insertando todo aquello que sea diferente de punto y coma  en un array de int
       sadc2.disconnectAllServices();
       return cadena1;
      }
      else
      {
       sadc2.disconnectAllServices();
       return "Error el programa del AS400 para consultar saldos del contrato no corrio.";
      }
     }
     catch(Exception ex){return "Error "+ex;}
    }
//*************************************************************************************************************************
 /**Función que consulta al as400 los saldos por fondo para un contrato. Procedimiento AJIIXFR.*/
 public static String TBPL_SaldosPorContrato2(String v_contrato, String v_fecha,String v_proefe,AS400 sadc2,String v_libreria)
 //Esta función devuelve desde el as/400 los saldos por contrato en una cadena
  {
    try
  {
   /**Declar y definir la lista de parametros, aquí se configura tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1),numero de contrato(9),fecha de consulta(8) y fecha autilizar en la busqueda(1),*/
   /**los parametros de salida serán fecha consulta,cadena1,cadena2,cadena3,cadena4 y cadena5.*/
   /**en estas ultimas vendrán los saldos por fondo separados por punto y coma*/
   ProgramParameter[] parametros = new ProgramParameter[10];/**Parametros del procedimiento del as400*/
   /**Parametros de entrada*/
   AS400Text t1         = new AS400Text(1);/**Variable que toma parametro dummy*/
   byte[] by1           = t1.toBytes(" ");/**Definir parametro dummy.*/
   parametros[0]        = new ProgramParameter(by1);

   AS400Text t2         = new AS400Text(9);/**Variable que toma parametro contrato*/
   byte[] by2           = t2.toBytes(v_contrato);/**Definir parametro contrato.*/
   parametros[1]        = new ProgramParameter(by2);

   AS400Text t3         = new AS400Text(8);/**Variable que toma parametro fecha consulta*/
   byte[] by3           = t3.toBytes(v_fecha);/**Definir parametro fecha consulta.*/
   parametros[2]        = new ProgramParameter(by3);

   AS400Text t4         = new AS400Text(8);/**Variable que toma indicador de efectiva o proceso*/
   byte[] by4           = t4.toBytes(v_proefe);/**Definir indicador de fecha.*/
   parametros[3]        = new ProgramParameter(by4);

   /**Parametros de salida*/
   parametros[4]        = new ProgramParameter(8);
   parametros[5]        = new ProgramParameter(200);
   parametros[6]        = new ProgramParameter(200);
   parametros[7]        = new ProgramParameter(200);
   parametros[8]        = new ProgramParameter(200);
   parametros[9]        = new ProgramParameter(200);

   /**Realizar llamado al programa del as400.*/
   ProgramCall programa = new ProgramCall(sadc2,""+v_libreria+"AJIIXFR.PGM",parametros);
   //capturar valores, eliminar espacios Y convewrtir a mayuscula
   if(programa.run())
   {//capturar fecha de consulta
    AS400Text tx1 = new AS400Text(8);
    byte[] b1     = parametros[4].getOutputData();
    String fecha  = (String)tx1.toObject(b1);
    //capturar cadena1
    AS400Text tx2   = new AS400Text(200);
    byte[] b2       = parametros[5].getOutputData();
    String cadena1  = (String)tx2.toObject(b2);
    cadena1=cadena1.trim();cadena1=cadena1.toUpperCase();
    //capturar cadena2
    AS400Text tx3   = new AS400Text(200);
    byte[] b3       = parametros[6].getOutputData();
    String cadena2  = (String)tx3.toObject(b3);
    cadena2=cadena2.trim();cadena2=cadena2.toUpperCase();
    //capturar cadena3
    AS400Text tx4   = new AS400Text(200);
    byte[] b4       = parametros[7].getOutputData();
    String cadena3  =(String)tx4.toObject(b4);
    cadena3=cadena3.trim();cadena3=cadena3.toUpperCase();
    //capturar cadena4
    AS400Text tx5   = new AS400Text(200);
    byte[] b5       = parametros[8].getOutputData();
    String cadena4  =(String)tx5.toObject(b5);
    cadena4=cadena4.trim();cadena4=cadena4.toUpperCase();
    //capturar cadena5
    AS400Text tx6   = new AS400Text(200);
    byte[] b6       = parametros[9].getOutputData();
    String cadena5  =(String)tx6.toObject(b6);
    cadena5=cadena5.trim();cadena5=cadena5.toUpperCase();
    //por cada array de char mueva hasta al final, insertando todo aquello que sea diferente de punto y coma  en un array de int
    sadc2.disconnectAllServices();
    return cadena1+cadena2+cadena3+cadena4+cadena5;
   }
   else
   {
    sadc2.disconnectAllServices();
    return "Error el programa del AS400 para consultar saldos del contrato no corrio.";
   }
  }
  catch(Exception ex){return "Error "+ex;}
  }

//*************************************************************************************************************************
 /**Función que parte una cadena y devuelve un string con información.*/
 public static String[] TBCL_CapturarCadena(String v_cadena,int v_dim)
 {
  try
  {
   String [] v_res =  new String[v_dim];/**Variable que retorna información de la cadena.*/
   if(!v_cadena.trim().equals(""))
   {
    int i = v_cadena.length();
    int v_cadena2 = v_cadena.indexOf(";");
    int c = 0;
    int t =0;
    while (v_cadena2!=-1)
    {
     int b=0;
     String v_cadena3 = v_cadena.substring(c,v_cadena2);
     c = v_cadena2+1;
     v_cadena2 = v_cadena.indexOf(";",c);
     int f =v_cadena3.length();
     v_cadena3+=",";
     int g =   v_cadena3.indexOf(",");
     while (g!=-1)
     {
      String h = v_cadena3.substring(b,g);
      v_res[t] = h;
      b=g+1;
      g=v_cadena3.indexOf(",",b);
      t++;
     }
    }
    return v_res;
  }
  v_res[0] = "Error";
  return v_res;
  }
  catch(Exception ex){
  String[] error = new String[1];
  error[0]= ""+ex;
  return error;}
 }

 /**Función que consulta al as400el minimo de saldo a dejar en el contrato. Procedimiento AJLYXFR.*/
 public static String TBFL_MinimoContrato(String v_contrato,String v_sistema,String v_usuario,String v_pass,String v_libreria)
 {
   AS400 as400 = new AS400(""+v_sistema+"");
  try
  {
   /**Definir variable tipo sistema as400*/

   /**Conectar al sistema as400 con usuario y password*/
   as400.setUserId(""+v_usuario+"");
   as400.setPassword(""+v_pass+"");
   /**Declarar y definir la lista de parametros, configurar tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1),numero de contrato(9),*/
   /**el parametro de salida será un campo Y o N que identifica si el contratotiene bloqueo de egresos o no*/
   ProgramParameter[] v_parametros = new ProgramParameter[3];/**Parametros del procedimiento del as400*/
   /**Parametros de entrada*/
   AS400Text text1 = new AS400Text(2);/**Variable que toma parametro dummy*/
   byte[] v_dummy = text1.toBytes(" ");/**Definir parametro dummy.*/
   v_parametros[0] = new ProgramParameter( v_dummy );

   AS400Text text2 = new AS400Text(10);/**Variable que toma parametro contrato*/
   byte[] p_contrato = text2.toBytes(v_contrato);/**Definir parametro contrato.*/
   v_parametros[1] = new ProgramParameter(p_contrato );
   int g = 0;
   /**Parametros de salida*/
   v_parametros[2] = new ProgramParameter(12);

   /**Realizar llamado al programa del as400.*/
   ProgramCall p_ajlyxfr = new ProgramCall(as400,""+v_libreria+"AJLYXFR.PGM",v_parametros);
   //si el programa corre
   if(p_ajlyxfr.run())
   {
    //se toma parametro de salida
    AS400Text text4 = new AS400Text(12);
    byte[] v_valor = v_parametros[2].getOutputData();
    String  v_valor_contrato = (String)text4.toObject(v_valor);
    as400.disconnectService(AS400.COMMAND);
    return v_valor_contrato;
   }
   // si no se corre el programa
   else
   {
    as400.disconnectService(AS400.COMMAND);
    return "Error el programa del AS400 para consultar minimo del  contrato no corrio.";
   }
  }
  //si hay algun error
  catch (Exception e)
  {
   as400.disconnectService(AS400.COMMAND);
   return "Error"+e;
  }
 }

 /**Función que consulta al as400el programa dado un contrato. Procedimiento ???????.*/
 public static String TBPL_ProgramaContrato(String v_contrato,String v_sistema,String v_usuario,String v_pass,String v_libreria)
 {
   AS400 as400 = new AS400(""+v_sistema+"");
   String ret;
  try
  {
   /**Definir variable tipo sistema as400*/

   /**Conectar al sistema as400 con usuario y password*/
   as400.setUserId(""+v_usuario+"");
   as400.setPassword(""+v_pass+"");
   /**Declarar y definir la lista de parametros, configurar tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1),numero de contrato(9),*/
   /**el parametro de salida será un campo Y o N que identifica si el contratotiene bloqueo de egresos o no*/
   ProgramParameter[] v_parametros = new ProgramParameter[4];/**Parametros del procedimiento del as400*/
   /**Parametros de entrada*/
   AS400Text text1 = new AS400Text(2);/**Variable que toma parametro dummy*/
   byte[] v_dummy = text1.toBytes(" ");/**Definir parametro dummy.*/
   v_parametros[0] = new ProgramParameter( v_dummy );

   AS400Text text2 = new AS400Text(9);/**Variable que toma parametro contrato*/
   byte[] p_contrato = text2.toBytes(v_contrato);/**Definir parametro contrato.*/
   v_parametros[1] = new ProgramParameter(p_contrato );

   /**Parametros de salida*/
   v_parametros[2] = new ProgramParameter(5);
   v_parametros[3] = new ProgramParameter(14);

   /**Realizar llamado al programa del as400.*/
   ProgramCall p_ajlyxfr = new ProgramCall(as400,""+v_libreria+"B1EKXFR.PGM",v_parametros);
   //si el programa corre
   if(p_ajlyxfr.run())
   {
    //se toma parametro de salida
    AS400Text text4 = new AS400Text(4);
    byte[] v_valor = v_parametros[2].getOutputData();
    String  v_valor_programa = (String)text4.toObject(v_valor);

    AS400Text text5 = new AS400Text(14);
    byte[] v_valor2 = v_parametros[3].getOutputData();
    String v_trm_contrato = (String)text5.toObject(v_valor2);

    v_trm_contrato = v_trm_contrato.substring(0,8) + "." + v_trm_contrato.substring(8);

    ret = v_valor_programa + ";" + v_trm_contrato;

    as400.disconnectService(AS400.COMMAND);

    return ret;    
   }
   // si no se corre el programa
   else
   {
    as400.disconnectService(AS400.COMMAND);
    return "Error el programa del AS400 para consultar el programa del contrato no corrio.";    
   }
  }
  //si hay algun error
  catch (Exception e)
  {
   as400.disconnectService(AS400.COMMAND);
   return "Error"+e;   
  }
 }

  /**
   * TBPL_InfoAgente
   * Obtiene la información de agente de un contrato
   * @param  String  v_contrato  Código del contrato 
   * @param  String  v_sistema   Sistema a usar en as400
   * @param  String  v_usuario   Usuario para acceder a AS400
   * @param  String  v_pass      Clave para acceder a AS400
   * @param  String  v_libreria  Libreria a usar en as400
   * @return String  Retorna la información del agente separando los atributos por ;
   */
 public static String TBPL_InfoAgente(String v_contrato, String v_sistema, String v_usuario,String v_pass,String v_libreria)
 {
   AS400 as400 = new AS400(""+v_sistema+"");
   String ret;
  try
  {
   /**Definir variable tipo sistema as400*/

   /**Conectar al sistema as400 con usuario y password*/
   as400.setUserId(""+v_usuario+"");
   as400.setPassword(""+v_pass+"");
   /**Declarar y definir la lista de parametros, configurar tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1),numero de contrato(9),*/
   /**los parametros de salida serán 6, Apsp Agent Last Name(20), Apsp Agent First Name (20), Apsp 
    * External Agent Id, (12) Apsp Agent Tin (13), Apsp Agent Firm Name (30), Apsp External Firm Id (12)*/
   ProgramParameter[] v_parametros = new ProgramParameter[9];/**Parametros del procedimiento del as400*/
   /**Parametros de entrada*/
   AS400Text text1 = new AS400Text(2);/**Variable que toma parametro dummy*/
   byte[] v_dummy = text1.toBytes(" ");/**Definir parametro dummy.*/
   v_parametros[0] = new ProgramParameter( v_dummy );

   AS400Text text2 = new AS400Text(9);/**Variable que toma parametro contrato*/
   byte[] p_contrato = text2.toBytes(v_contrato);/**Definir parametro contrato.*/
   v_parametros[1] = new ProgramParameter(p_contrato );

   /**Parametros de salida*/
   v_parametros[2] = new ProgramParameter(20);
   v_parametros[3] = new ProgramParameter(20);
   v_parametros[4] = new ProgramParameter(12);
   v_parametros[5] = new ProgramParameter(13);
   v_parametros[6] = new ProgramParameter(30);
   v_parametros[7] = new ProgramParameter(12);
   v_parametros[8] = new ProgramParameter(13);

   /**Realizar llamado al programa del as400.*/
   ProgramCall p_ajlyxfr = new ProgramCall(as400,""+v_libreria+"C1BAXFR.PGM",v_parametros);
   //si el programa corre
   if(p_ajlyxfr.run())
   {
    //se toma parametro de salida
    AS400Text text4 = new AS400Text(20);
    byte[] v_valor = v_parametros[2].getOutputData();
    String  v_apellido_agente = (String)text4.toObject(v_valor);

    AS400Text text5 = new AS400Text(20);
    byte[] v_valor2 = v_parametros[3].getOutputData();
    String v_nombre_agente = (String)text5.toObject(v_valor2);

    AS400Text text6 = new AS400Text(12);
    byte[] v_valor3 = v_parametros[4].getOutputData();
    String v_id_agente = (String)text6.toObject(v_valor3);
    
    AS400Text text7 = new AS400Text(13);
    byte[] v_valor4 = v_parametros[5].getOutputData();
    String v_tin_agente = (String)text7.toObject(v_valor4);

    AS400Text text8 = new AS400Text(30);
    byte[] v_valor5 = v_parametros[6].getOutputData();
    String v_firma_agente = (String)text8.toObject(v_valor5);
    
    AS400Text text9 = new AS400Text(12);
    byte[] v_valor6 = v_parametros[7].getOutputData();
    String v_id_firma_agente = (String)text9.toObject(v_valor6);

    AS400Text text10 = new AS400Text(13);
    byte[] v_valor7 = v_parametros[8].getOutputData();
    String v_nit_contrato = (String)text10.toObject(v_valor7);
    
    ret = v_apellido_agente + ";" + v_nombre_agente + ";" + v_id_agente + ";" + v_tin_agente + ";" + v_firma_agente + ";" + v_id_firma_agente+ ";" + v_nit_contrato;
    
    as400.disconnectService(AS400.COMMAND);

    return ret;    
   }
   // si no se corre el programa
   else
   {
    as400.disconnectService(AS400.COMMAND);
    return "Error el programa del AS400 para consultar el programa del contrato no corrio.";    
   }
  }
  //si hay algun error
  catch (Exception e)
  {
   as400.disconnectService(AS400.COMMAND);
   return "Error"+e;   
  }
 }

  /**
   * TBPL_GenerarCajasPrimas
   * Obtiene la información de agente de un contrato
   * @param  String  v_contrato  Código del contrato 
   * @param  String  v_sistema   Sistema a usar en as400
   * @param  String  v_usuario   Usuario para acceder a AS400
   * @param  String  v_pass      Clave para acceder a AS400
   * @param  String  v_libreria  Libreria a usar en as400
   * @return String  Retorna la información del agente separando los atributos por ;
   */
 public static String TBPL_GenerarCajasPrimas(String v_fecha, String v_team, 
        String v_tipo, String v_agente, String v_hora, String v_sistema, String 
        v_usuario, String v_pass,String v_libreria)
 {
   AS400 as400 = new AS400(""+v_sistema+"");
  try
  {
   /**Definir variable tipo sistema as400*/

   /**Conectar al sistema as400 con usuario y password*/
   as400.setUserId(""+v_usuario+"");
   as400.setPassword(""+v_pass+"");
   /**Declarar y definir la lista de parametros, configurar tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1), Fecha(8), Te Team Id(6), Sq Type(1), Sale(9), 
    * Time(6) */
   /**el parametro de salida será Contrato(9)*/
   ProgramParameter[] v_parametros = new ProgramParameter[7];/**Parametros del procedimiento del as400*/

   /**Parametros de entrada*/
   AS400Text text1 = new AS400Text(2);/**Variable que toma parametro dummy*/
   byte[] v_dummy = text1.toBytes(" ");/**Definir parametro dummy.*/
   v_parametros[0] = new ProgramParameter( v_dummy );
   AS400Text text2 = new AS400Text(8);/**Variable que toma parametro fecha*/
   byte[] p_fecha = text2.toBytes(v_fecha);/**Definir parametro fecha.*/
   v_parametros[1] = new ProgramParameter(p_fecha );
   AS400Text text3 = new AS400Text(6);/**Variable que toma parametro team*/
   byte[] p_team = text3.toBytes(v_team);/**Definir parametro team.*/
   v_parametros[2] = new ProgramParameter(p_team);

   AS400Text text4 = new AS400Text(1);/**Variable que toma parametro tipo*/
   byte[] p_tipo = text4.toBytes(v_tipo);/**Definir parametro team.*/
   v_parametros[3] = new ProgramParameter(p_tipo);

   AS400Text text5 = new AS400Text(9);/**Variable que toma parametro agente*/
   byte[] p_agente = text5.toBytes(v_agente);/**Definir parametro agente.*/
   v_parametros[4] = new ProgramParameter(p_agente);

   AS400Text text6 = new AS400Text(6);/**Variable que toma parametro hora*/
   byte[] p_hora = text6.toBytes(v_hora);/**Definir parametro hora.*/
   v_parametros[5] = new ProgramParameter(p_hora);

   /**Parametros de salida*/
   v_parametros[6] = new ProgramParameter(30);


   /**Realizar llamado al programa del as400.*/
   ProgramCall p_ajlyxfr = new ProgramCall(as400,""+v_libreria+"C1B5XFR.PGM",v_parametros);
   //si el programa corre
   if(p_ajlyxfr.run())
   {
    //se toma parametro de salida
    AS400Text text7 = new AS400Text(9);
    byte[] v_valor = v_parametros[6].getOutputData();
    String  v_contrato = (String)text6.toObject(v_valor);

    as400.disconnectService(AS400.COMMAND);

    return v_contrato;    
   }
   // si no se corre el programa
   else
   {
    as400.disconnectService(AS400.COMMAND);
    return "Error el programa del AS400 para consultar el programa del contrato no corrio.";    
   }
  }
  //si hay algun error
  catch (Exception e)
  {
   as400.disconnectService(AS400.COMMAND);
   return "Error"+e;   
  }
 }


  /**
   * TBPL_ValorAportesFondosCerrados
   * Obtiene la información de agente de un contrato
   * @param  String  v_contrato  Código del contrato 
   * @param  String  v_sistema   Sistema a usar en as400
   * @param  String  v_usuario   Usuario para acceder a AS400
   * @param  String  v_pass      Clave para acceder a AS400
   * @param  String  v_libreria  Libreria a usar en as400
   * @return String  Retorna el valor que tiene cada aporte en fondos cerrados;
   */
 /*public static String TBPL_ValorAportesFCerrados(String v_contrato, String v_sistema, String 
        v_usuario, String v_pass,String v_libreria )
 {
   AS400 as400 = new AS400(""+v_sistema+"");
   ResultSet aportes =null;
   String valores = "";
   Connection conn = null;
   ResultSet rs=null;

  try
  {
   /**Conectar al sistema as400 con usuario y password*/
   /*as400.setUserId(""+v_usuario+"");
   as400.setPassword(""+v_pass+"");
   /**Declarar y definir la lista de parametros, configurar tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1), contrato(9)*/
   /*ProgramParameter[] v_parametros = new ProgramParameter[2];/**Parametros del procedimiento del as400*/

   /**Parametros de entrada*/
   /*AS400Text text1 = new AS400Text(2);/**Variable que toma parametro dummy*/
   /*byte[] v_dummy = text1.toBytes(" ");/**Definir parametro dummy.*/
   /*v_parametros[0] = new ProgramParameter( v_dummy );

   AS400Text text2 = new AS400Text(9);/**Variable que toma parametro contrato*/
   /*byte[] p_contrato = text2.toBytes(v_contrato);/**Definir parametro contrato.*/
   /*v_parametros[1] = new ProgramParameter(p_contrato );

   v_libreria = v_libreria.substring(10, v_libreria.length());
   int pos1 = v_libreria.indexOf(".LIB/");
   v_libreria = v_libreria.substring(0, pos1);
   String sUrl = "jdbc:as400://"+v_sistema+"/"+v_libreria;
   DriverManager.registerDriver(new AS400JDBCDriver());
   Connection conAs400 = DriverManager.getConnection(sUrl,v_usuario, v_pass);
   Statement stmtAportes = conAs400.createStatement(); 
   aportes = stmtAportes.executeQuery("CALL "+v_libreria+".C1YZXFR('       ',"+"'"+v_contrato+"')");

   TBCL_Validacion valuesUser = new TBCL_Validacion();
   String[] valuesUs = new String[3];
   valuesUs = valuesUser.TBFL_ValidarUsuario();
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   conn = DriverManager.getConnection(valuesUs[0],valuesUs[1],valuesUs[2]);
   Statement stmt= conn.createStatement();
   BigDecimal certif = new BigDecimal(0);

   while(aportes.next()) {
         /* 
          * Para cada aporte se identifica si esta certificado y en ese caso se suma
          * al valor de no certificados
          */
         /*String consCertif = " select * from tbaportes "+
                             " where apo_transaccion = '"+ aportes.getString(4) +  "'" +
                             " and apo_con_numero = '"+ v_contrato + "'" +
                             " and apo_aporte_certificado ='N' ";
         rs=stmt.executeQuery(consCertif);
         if(rs.next()){
            certif = certif.add(aportes.getBigDecimal(3));  
         }
         rs.close();                           
         
         valores = certif.toString() ;

   }
   stmt.close();

  }
  //si hay algun error
  catch (Exception e)
  {
   as400.disconnectService(AS400.COMMAND);
   valores=  "Error"+e.getMessage();
  }
  finally 
  {
    try { 
      if( conn != null ) { 
                  conn.commit();
                  conn.close();  
      }
    }
    catch( SQLException ignored ){
    }
       return valores;
  }
  
}*/

 public static String TBPL_ValorAportesFCerrados(String v_contrato, String v_sistema, String 
        v_usuario, String v_pass,String v_libreria )
 {
   AS400 as400 = new AS400(""+v_sistema+"");
   String valores = "";
   ResultSet aportes =null;

  try
  {
   /**Conectar al sistema as400 con usuario y password*/
   as400.setUserId(""+v_usuario+"");
   as400.setPassword(""+v_pass+"");
   /**Declarar y definir la lista de parametros, configurar tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1), contrato(9)*/
   ProgramParameter[] v_parametros = new ProgramParameter[2];/**Parametros del procedimiento del as400*/

   /**Parametros de entrada*/
   AS400Text text1 = new AS400Text(2);/**Variable que toma parametro dummy*/
   byte[] v_dummy = text1.toBytes(" ");/**Definir parametro dummy.*/
   v_parametros[0] = new ProgramParameter( v_dummy );

   AS400Text text2 = new AS400Text(9);/**Variable que toma parametro contrato*/
   byte[] p_contrato = text2.toBytes(v_contrato);/**Definir parametro contrato.*/
   v_parametros[1] = new ProgramParameter(p_contrato );

   v_libreria = v_libreria.substring(10, v_libreria.length());
   int pos1 = v_libreria.indexOf(".LIB/");
   v_libreria = v_libreria.substring(0, pos1);
   String sUrl = "jdbc:as400://"+v_sistema+"/"+v_libreria;
   DriverManager.registerDriver(new AS400JDBCDriver());
   Connection conAs400 = DriverManager.getConnection(sUrl,v_usuario, v_pass);
   Statement stmtAportes = conAs400.createStatement(); 
   aportes = stmtAportes.executeQuery("CALL "+v_libreria+".C1YZXFR('       ',"+"'"+v_contrato+"')");
  
   while(aportes.next()) {
         /* 
          * Para cada aporte se identifica si esta certificado y en ese caso se suma
          * al valor de no certificados
          */
         valores = valores  + aportes.getString(4)+ ";";                   

   }
   //stmt.close();

  }
  //si hay algun error
  catch (Exception e)
  {
   as400.disconnectService(AS400.COMMAND);
   valores=  "Error"+e.getMessage();
  }
   return valores;
}

/**Función que consulta al as400 si el evento de saro es valido y esta activo. Procedimiento C3H2XFR.*/
 public static String TBPL_ValidarSaro(String v_id_saro ,String v_sistema,String v_usuario,String v_pass,String v_libreria)
 {
  AS400 as400 = new AS400(""+v_sistema+""); /**Definir variable tipo sistema as400*/
  try
  {
   /**Conectar al sistema as400 con usuario y password*/
   as400.setUserId(""+v_usuario+"");
   as400.setPassword(""+v_pass+"");
   /**Declar y definir la lista de parametros, configurar tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1),numero de contrato(9),*/
   /**el parametro de salida será un campo Y o N que identifica si el saro es valido o no.*/

   ProgramParameter[] v_parametros = new ProgramParameter[3];/**Parametros del procedimiento del as400*/

   /**Parametros de entrada*/
   AS400Text text1 = new AS400Text(2);/**Variable que toma parametro dummy.*/
   byte[] v_dummy = text1.toBytes(" ");/**Definir parametro dummy*/
   v_parametros[0] = new ProgramParameter( v_dummy );

   AS400Text text2 = new AS400Text(10);/**Variable que toma parametro  contrato*/
   byte[] p_saro = text2.toBytes(v_id_saro);/**Definir parametro contrato.*/
   v_parametros[1] = new ProgramParameter(p_saro );

   /**Parametros de salida*/
   v_parametros[2] = new ProgramParameter( 1 );

   /**Realizar llamado al programa del as400.*/
   ProgramCall p_ajkgxfr = new ProgramCall(as400,""+v_libreria+"C3H2XFR.PGM",v_parametros);

   /**Si el programa corre*/
   if(p_ajkgxfr.run())
   {
    /**Tomar parametro de salida*/
    AS400Text text4 = new AS400Text(1);
    byte[] v_vsaro = v_parametros[2].getOutputData();
    String  v_valsaro =  (String)text4.toObject(v_vsaro);
    as400.disconnectService(AS400.COMMAND);
    return v_valsaro;
   }
   /** Si no se corre el programa*/
   else
   {
    as400.disconnectService(AS400.COMMAND);
    return "No se corrio el programa";
   }
  }
  //si hay algun error
  catch (Exception e)
  {
   as400.disconnectService(AS400.COMMAND);
   return "error"+e;
  }
 }
 
 /**Función que consulta al as400 el tipo de pago que genero una transacción dada. Procedimiento C3HIXFR.*/
 public static String TBPL_TipoOperacionTraslado(String v_contrato, String v_transaccion,String v_sistema,String v_usuario,String v_pass,String v_libreria)
 {
  AS400 as400 = new AS400(""+v_sistema+""); /**Definir variable tipo sistema as400*/
  try
  {
   /**Conectar al sistema as400 con usuario y password*/
   as400.setUserId(""+v_usuario+"");
   as400.setPassword(""+v_pass+"");
   /**Declar y definir la lista de parametros, configurar tanto los de entrada como los de salida*/
   /**los parametros de entrada serán dummy(1),numero de contrato(9),*/
   /**el parametro de salida será un campo Y o N que identifica si el contrato tiene bloqueo de egresos o no.*/

   ProgramParameter[] v_parametros = new ProgramParameter[4];/**Parametros del procedimiento del as400*/

   /**Parametros de entrada*/
   AS400Text text1 = new AS400Text(2);/**Variable que toma parametro dummy.*/
   byte[] v_dummy = text1.toBytes(" ");/**Definir parametro dummy*/
   v_parametros[0] = new ProgramParameter( v_dummy );

   AS400Text text2 = new AS400Text(10);/**Variable que toma parametro contrato*/
   byte[] p_contrato = text2.toBytes(v_contrato);/**Definir parametro contrato.*/
   v_parametros[1] = new ProgramParameter(p_contrato );

   AS400Text text3 = new AS400Text(13);/**Variable que toma parametro transacciones*/
   byte[] p_transaccion = text3.toBytes(v_transaccion);/**Definir parametro contrato.*/
   v_parametros[2] = new ProgramParameter(p_transaccion );

   /**Parametros de salida*/
   v_parametros[3] = new ProgramParameter( 3 );

   /**Realizar llamado al programa del as400.*/
   ProgramCall p_c3hixfr = new ProgramCall(as400,""+v_libreria+"C3IHXFR.PGM",v_parametros);

   /**Si el programa corre*/
   if(p_c3hixfr.run())
   {
    /**Tomar parametro de salida*/
    AS400Text text4 = new AS400Text(3);
    byte[] v_tipoTraslado = v_parametros[3].getOutputData();
    String  v_tipoOpTras =  (String)text4.toObject(v_tipoTraslado);
    as400.disconnectService(AS400.COMMAND);
    return v_tipoOpTras;
   }
   /** Si no se corre el programa*/
   else
   {
    as400.disconnectService(AS400.COMMAND);
    return "No se corrio el programa";
   }
  }
  //si hay algun error
  catch (Exception e)
  {
   as400.disconnectService(AS400.COMMAND);
   return "error"+e;
  }
 }
 /***************************************************************************************************/
 public static String TBFL_Reporte_CEI_OBLIG(String fecha_consulta, String ruta, String sistema, String usuario, String password)
 //Esta función Genera el Reporte del CEI de los retiros que se van a procesar
  {
   try
   {    
     //realizo la conexión al sistema sadc2 con un userid y un password
     //estos parametros serán leidos desde tbrefencias desde taxb
     //AS400 sadc2 = new AS400(sistema);sadc2.setUserId(usuario);sadc2.setPassword(password);      
     AS400 sadc2 = new AS400(sistema,usuario,password);      
     //declaro y defino la lista de parametros, aquí configuro tanto los de entrada como los de salida
     //los parametros de entrada serán dummy(1),producto(8),tipo_fecha(1), fecha_consulta(8),tipo_id_afiliado(1), id_afiliado(12),     
     //Parametro Entrada salida Errores_proceso(2)  
     //los parametros de salida serán cadena1,cadena2,cadena3,cadena4 y cadena5.     
     ProgramParameter[] parametros = new ProgramParameter[3];
                     
     //parametros de entrada
     AS400Text t1         = new AS400Text(1);
     byte[] by1           = t1.toBytes(" ");
     parametros[0]        = new ProgramParameter(by1);
     AS400Text t2         = new AS400Text(8);
     byte[] by2           = t2.toBytes(fecha_consulta);
     parametros[1]        = new ProgramParameter(by2);     
     
     //parametros de salida
     parametros[2]        = new ProgramParameter(1);
     String resul="ERROR";
     //realizo llamado al programa as400
     ProgramCall programa = new ProgramCall(sadc2,ruta+"ACUHXFR.PGM",parametros);     
     if(programa.run())
     {
       //capturo Respuesta
       AS400Text tx1   = new AS400Text(1);
       byte[] b1       = parametros[2].getOutputData();
       String cadena1  = (String)tx1.toObject(b1);
       cadena1=cadena1.trim();       
       
       sadc2.disconnectAllServices();       
       
       return cadena1;    
      }
       return "1";
   }
   catch(Exception ex){
      return "ERROR" + ex.toString();
    }//EN FUNCIONES AS400(REPORTE RETIROS cei) SE PRESENTO EL SIGUIENTE ERROR: "+ex
  }

}//fin
