
// Copyright (c) 2000 skandia
package TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS;

import java.util.*;
import java.io.*;

/**Clase para validar usuario y reemplazar comilla sencilla por comilla doble.*/
public class TBCL_Validacion extends Object
{
//**Función que consulta la url, usuario y password en el archivo connection.properties.*/
public static String[] TBFL_ValidarUsuario()
{
 try
 {
  byte v_parametros[] = new byte[500];/**Variable que toma cadena de conexión.*/
  String v_cadena;/**Variable que toma la variable parametros.*/
  String v_valusu[] = new String[3];/**Variable que se retorna con la información para la conexión.*/
  FileInputStream   v_leer = new FileInputStream( "E:/Taxbenefits/taxb/Conexion/Connection.properties" );/**Variable para leer archivo de connection.properties*/
  v_leer.read(v_parametros);
  
  /*[SO_396]Se realiza modificación para suprimir el uso del constructorSe new String( byte[],int ) ya que ha sido deprecado,
   *se cambia implementación por new String( byte[] )*/
  //v_cadena = new String( v_parametros ,0);
  v_cadena = new String( v_parametros );
     
  int index_s1,index_s2,index_s3,index_s4,index_s5,index_s6,index_s7,index_s8,index_s9,index_s10,index_s11,index_s12;/**Variable para tomar el tamaño de las cadenas*/
  index_s1=index_s2=index_s3=index_s4=0;

  index_s1=v_cadena.indexOf("*");
  index_s2=v_cadena.length();
  String s_s1 = v_cadena.substring(index_s1+1,index_s2);/**Variable que toma tamaño de la cadena que se leee de connection.properties*/

  index_s3=s_s1.indexOf("*");
  index_s4=s_s1.length();

  String v_conexion = s_s1.substring(0,index_s3);/**Variable que toma el url para la conexion.*/

  String s_s2 = s_s1.substring(index_s3+1,index_s4);
  index_s5=s_s2.indexOf("*");
  index_s6=s_s2.length();

  String s_s3 = s_s2.substring(index_s5+1,index_s6);

  String  v_usuario = s_s2.substring(0,index_s5);/**Variable que toma el usuario para la conexion.*/
  index_s7=s_s3.indexOf("*");
  index_s8=s_s3.length();

  String v_password = s_s3.substring(0,index_s7);/**Variable que toma el password para la conexion.*/

  v_valusu[0] = v_conexion;
  v_valusu[1] = v_usuario;
  v_valusu[2] = v_password;
  return v_valusu;
 }
 catch (IOException e)
 {
   System.err.println("FileStreamsTest: " + e);
   String v_valusu[] = new String[2];
   return v_valusu;
 }
}
}
