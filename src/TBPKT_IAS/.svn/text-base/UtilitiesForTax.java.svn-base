
// Copyright (c) 2000 skandia
package TBPKT_IAS;

import java.util.*;

public class UtilitiesForTax extends Object
{

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

 public static double[] StringTokenizedToArray(String v_StringTokenized, int v_dim, String v_token)
   {
      int i = 0;
      double v_doubleArray[] = new double[v_dim];
      StringTokenizer st = new StringTokenizer(v_StringTokenized, v_token);
      while (st.hasMoreTokens())
       {
         v_doubleArray[i++] = Double.valueOf(st.nextToken()).doubleValue();
       }
      return v_doubleArray;
   }

  public static String[] StringTokenizedToArrayString(String v_StringTokenized, int v_dim, String v_token)
   {
      int i = 0;
      String v_stringArray[] = new String[v_dim];
      StringTokenizer st = new StringTokenizer(v_StringTokenized, v_token);
      while (st.hasMoreTokens())
       {
         v_stringArray[i++] = st.nextToken();
       }
      return v_stringArray;
   }

}

 