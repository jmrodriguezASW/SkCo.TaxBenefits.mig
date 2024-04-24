
// Copyright (c) 2000 Sk
package TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD;

import java.io.*;
import java.util.*;
import java.math.*;
import java.sql.*;
import java.text.*;
import java.lang.Thread.*;
import java.lang.String.*;
import com.ibm.as400.access.*;


/**
 * A Class class.
 * <P>
 * @author Skandia
 */
public class TBCL_FUNCION_AS400 extends Object {

public static String Saldos_por_Contrato(String numero_contrato, String fecha_consulta, String tipo_fecha, AS400 sadc2, String ruta){
  //Esta función devuelve desde el as/400 los saldos por contrato en una cadena
  try{
    //declaro y defino la lista de parametros, aquí configuro tanto los de entrada como los de salida
    //los parametros de entrada serán dummy(1),numero de contrato(9),fecha de consulta(8) y fecha autilizar en la busqueda(1),
    //los parametros de salida serán fecha consulta,cadena1,cadena2,cadena3,cadena4 y cadena5.
    //en estas ultimas vendrán los saldos por fondo separados por punto y coma
    ProgramParameter[] parametros = new ProgramParameter[10];
    //parametros de entrada
    AS400Text t1         = new AS400Text(1);
    byte[] by1           = t1.toBytes(" ");
    parametros[0]        = new ProgramParameter(by1);
    AS400Text t2         = new AS400Text(9);
    byte[] by2           = t2.toBytes(numero_contrato);
    parametros[1]        = new ProgramParameter(by2);
    AS400Text t3         = new AS400Text(8);
    byte[] by3           = t3.toBytes(fecha_consulta);
    parametros[2]        = new ProgramParameter(by3);
    AS400Text t4         = new AS400Text(8);
    byte[] by4           = t4.toBytes(tipo_fecha);
    parametros[3]        = new ProgramParameter(by4);
    //parametros de salida
    parametros[4]        = new ProgramParameter(8);
    parametros[5]        = new ProgramParameter(200);
    parametros[6]        = new ProgramParameter(200);
    parametros[7]        = new ProgramParameter(200);
    parametros[8]        = new ProgramParameter(200);
    parametros[9]        = new ProgramParameter(200);
    //realizo llamado al programa as400
    ProgramCall programa = new ProgramCall(sadc2,ruta+"AJIIXFR.PGM",parametros);
    //capturo valores, elimino espacios Y CONVIERTO A MAYUSCULAS
    if (programa.run()){
      //capturo fecha de consulta
      AS400Text tx1 = new AS400Text(8);
      byte[] b1     = parametros[4].getOutputData();
      String fecha  = (String)tx1.toObject(b1);
      //capturo cadena1
      AS400Text tx2   = new AS400Text(200);
      byte[] b2       = parametros[5].getOutputData();
      String cadena1  = (String)tx2.toObject(b2);
      cadena1=cadena1.trim();
      //capturo cadena2
      AS400Text tx3   = new AS400Text(200);
      byte[] b3       = parametros[6].getOutputData();
      String cadena2  = (String)tx3.toObject(b3);
      cadena2=cadena2.trim();
      //capturo cadena3
      AS400Text tx4   = new AS400Text(200);
      byte[] b4       = parametros[7].getOutputData();
      String cadena3  =(String)tx4.toObject(b4);
      cadena3=cadena3.trim();
      //capturo cadena4
      AS400Text tx5   = new AS400Text(200);
      byte[] b5       = parametros[8].getOutputData();
      String cadena4  =(String)tx5.toObject(b5);
      cadena4=cadena4.trim();
      //capturo cadena5
      AS400Text tx6   = new AS400Text(200);
      byte[] b6       = parametros[9].getOutputData();
      String cadena5  =(String)tx5.toObject(b5);
      cadena5=cadena5.trim();
      //por cada array de char mueva hasta al final, insertando todo aquello que sea diferente de punto y coma  en un array de int
      return cadena1+cadena2+cadena3+cadena4+cadena5;
    }
    else{
      return "ERROR";
    }
  }
  catch(Exception ex){System.out.println("");return "ERROR";}//"EN FUNCIONES AS400(Saldos por Contrato): "+ex
 }//Metodo


}//Clase

 