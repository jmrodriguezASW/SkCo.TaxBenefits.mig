
package TBPKT_MODULO_APORTES;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.*;

/**
*Este servlet hace parte de la carga de aportes externos hacia tax, y tiene como
*función cargar temporalmente los aportes desde un plano hacia tax
*/

public class TBS_CARGA_APORTES_EXTERNOS extends HttpServlet{
public void init(ServletConfig config)
       throws ServletException
       { super.init(config);}
//----------------------------------------------------------------------------------------------------
public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
                  throws ServletException, IOException
{
PrintWriter salida  = new PrintWriter (respuesta.getOutputStream());
    Connection v_conexion_taxb    =   null;
try
{
 //seguridad
 HttpSession session = peticion.getSession(true);
 if (session == null) session = peticion.getSession(true);
 respuesta.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
 //respuesta.setHeader("Pragma", "No-Cache");
 //respuesta.setDateHeader("Expires", 0);
// respuesta.setHeader("Cache-Control","no-Cache");
 String v_contrato    = "", v_producto = "",
 v_usuario            = "", v_unidad   = "";
 String v_tipousu     = "", v_idworker = "";
 String parametros[]  = new String[8];
 String  cadena2      = peticion.getParameter("cadena");
 String   nuevaCadena = cadena2;
 String ip_tax        = peticion.getRemoteAddr();
  
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
 //valido la veracidad del producto y contrato enviados por pipeline
 parametros = TBCL_Seguridad.TBFL_Seguridad(cadena2, salida, ip_tax);
 v_contrato = parametros[0];
 v_producto = parametros[1];
 v_usuario  = parametros[2];
 v_unidad   = parametros[3];
 v_tipousu  = parametros[4];
 v_idworker = parametros[5];
 //seguridad
 
    v_conexion_taxb =   DataSourceWrapper.getInstance().getConnection();
 //carga_archivo("negativos.txt",salida,v_conexion_taxb);
 String  pagina            = peticion.getParameter("pagina");
 if(pagina == null)
 {
  pagina = "1";
 }

 if(pagina.equals("1"))
  {//dibujo boton inicial, pido archivo a CARGAR
   TBPL_pagina_1(salida,nuevaCadena);
   return;
  }
//-----------------------------------------PAG1
 else if(pagina.equals("2"))
  {
   String  archivo = peticion.getParameter("obligatorio_archivo");
   if(TBPL_existe_afp(archivo,v_conexion_taxb,salida))
   {
     TBPL_realizar_pregunta(salida,archivo,nuevaCadena);
   }
   else
   {//La AFP no es Valida en TAX, PUEDO INICIAR CARGA
    TBPL_carga_archivo(archivo,salida,v_conexion_taxb);
   }
  }
//-----------------------------------------PAG2
 else if(pagina.equals("3"))
 {
  String  archivo = peticion.getParameter("archivo");
  TBPL_carga_archivo(archivo,salida,v_conexion_taxb);
  return;
 }
//-----------------------------------------PAG3
 salida.flush();
}
catch(Exception ex)
  {
   //STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML;
   String msj = "SE PRODUJO UN ERROR INESPERADO, "+ex.toString() +" INTENTE DE NUEVO.";
   salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                         "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS","<CENTER>"+msj+"</CEBTER>",false));
   salida.println("<BR><BR>");
   salida.println("<center><input type='button' value='Aceptar' Onclick=window.location='/Servlets/TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS';></center>");
   salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
   salida.flush();
   return;
  }
    finally{
       try{
            DataSourceWrapper.closeConnection(v_conexion_taxb);                  
       }catch(SQLException sqlEx){
            System.out.println(sqlEx.toString());
       }
   }
}
//--------------------ANEXOS-------------------------------------------------------------------------
public static void TBPL_pagina_1(PrintWriter salida,String nuevaCadena)
{
//STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML;
salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                      "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS",
                                                " ",true,"moduloparametro.js","return checkrequired(this)"));
salida.println("<BR><BR>");
salida.println("<p><B><FONT color=black face=verdana size=1>DIGITE EL NOMBRE DEL ARCHIVO QUE CONTIENE LA INFORMACION "+
               "DE LOS APORTES DE TRASLADOS EXTERNOS QUE DESEA CARGAR </font></B></p>");
salida.println("<p><FONT color=black face=verdana size=1> *ARCHIVO :</font>"+
               "<INPUT NAME='obligatorio_archivo' TYPE='text' MAXLENGTH='45' SIZE='45'></p>");
salida.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
salida.println("<center><input type='submit' value='Aceptar'>"+
               "<input type='button' value='Cancelar' Onclick=history.go(-1);></center>");
salida.println("<input type='hidden' name='pagina' value='2'>");
salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
salida.close();
}
//----------------------------------------------------------------------------------------------------
public static boolean TBPL_existe_afp(String archivo,Connection c,PrintWriter salida)
{
//STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML;
try
{
 CallableStatement t_cst8i_0 = c.prepareCall("{ call TBPBD_Ruta_File(?) }");
 t_cst8i_0.registerOutParameter(1,Types.VARCHAR);
 t_cst8i_0.execute();
 String ruta = t_cst8i_0.getString(1);
 t_cst8i_0.close();
 
 if(!ruta.equals("ERROR"))
  {
   archivo                     = archivo.toUpperCase();
   byte registro_bytes[]       = new byte[100000];
   File archivoentrada         = new File(ruta+archivo);
   FileInputStream lector      = new FileInputStream(archivoentrada);
   lector.read(registro_bytes,0,100000);
   String registro_str         = new String(registro_bytes);
   //Capturo AFP_ORIGEN
   String afp_o                = registro_str.substring(31,42);
   CallableStatement t_cst8i_1 = c.prepareCall("{ call TBPBD_Afp_Existe(?,?) }");
   t_cst8i_1.registerOutParameter(2,Types.VARCHAR);
   t_cst8i_1.setString(1,afp_o);
   t_cst8i_1.execute();
   String existe = t_cst8i_1.getString(2);
   t_cst8i_1.close();
   if(existe.equals("V"))
     return true;
   else if(existe.equals("F"))
     return false;
   else
   salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                               " ","SE PRODUJO UN ERROR EN LA BASE DATOS, POR FAVOR INTENTE DE NUEVO"
                                               ,false));
   salida.println("<BR><BR>");
   salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
   salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
   salida.close();
   return false;

  }
 else
  {
   salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                               " ","SE PRODUJO UN ERROR EN LA BASE DATOS, RUTA DE ARCHIVO INEXISTENTE, POR FAVOR INTENTE DE NUEVO"
                                               ,false));
   salida.println("<BR><BR>");
   salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
   salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
   salida.close();
   return false;
  }
 }//try
catch(FileNotFoundException fe)
{
 salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                             " ","NOMBRE DE ARCHIVO INEXISTENTE EN EL SERVIDOR, VERIFIQUE LA UBICACION Y EL NOMBRE DEL ARCHIVO,"+
                                              "EL NOMBRE ES DE LA FORMA NOMBREARCHIVO.TXT",false));
 salida.println("<BR><BR>");
 salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
 salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
 salida.close();
 /*
Aqui
 */
 return false;
}
catch(Exception ex)
{
System.out.println("");
return false;
}
}
//----------------------------------------------------------------------------------------------------
public static void TBPL_realizar_pregunta(PrintWriter salida,String archivo,String nuevaCadena)
{
  //STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML;
  salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                                "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS",
                                                " ",true));
  salida.println("<BR><BR>");
  salida.println("<p><B><FONT color=black face=verdana size=1>LA INFORMACION QUE DESEA CARGAR  TIENE ASOCIADA UNA AFP YA EXISTENTE,"+
               "¿ DESEA BORRARLA Y CREAR UN NUEVO CARGUE ?</font></B></p>");
  salida.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
  salida.println("<center><input type='submit' value='Aceptar'>"+
               "<input type='button' value='Cancelar' Onclick=history.go(-1);></center>");
  salida.println("<input type='hidden' name='pagina' value='3'>");
  salida.println("<input type='hidden' name='archivo' value='"+archivo+"'>");
  salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  salida.close();
}

public static void TBPL_carga_archivoNoWeb(String archivo,Connection c)
{
String url = "Onclick=window.location='/Servlets/TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS';";

//STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML;
try
{
 CallableStatement t_cst8i_0 = c.prepareCall("{ call TBPBD_Ruta_File(?) }");
 t_cst8i_0.registerOutParameter(1,Types.VARCHAR);
 t_cst8i_0.execute();
 String ruta = t_cst8i_0.getString(1);
 t_cst8i_0.close();
 //String ruta =  "C:\\Taxbenefits\\";
 if(ruta.equals("ERROR"))
  {
   c.close();
  }
 String registro_str;
 String str0            = " ";
 String fecha_g         = " ";
 String total_a         = " ";
 String valor_t         = " ";
 String afp_o           = " ";
 String afp_d           = " ";
 archivo                = archivo.toUpperCase();
 byte registro_bytes[]  = new byte[10000000];
 File archivoentrada    = new File(ruta+archivo);
 FileInputStream lector = new FileInputStream(archivoentrada);
 lector.read(registro_bytes,0,10000000);
 registro_str          = new String(registro_bytes);

 //Capturo información del registro tipo 1
 fecha_g               = registro_str.substring(1,9);
 total_a               = registro_str.substring(9,14);
 valor_t               = registro_str.substring(14,31);
 afp_o                 = registro_str.substring(31,42);
 afp_d                 = registro_str.substring(42,53);
 //convierto tipos
 Integer total_afi     = new Integer(total_a);
 int afiliados         = total_afi.intValue();
 int afiliados_reales  = 0;
 int total_rec3        = 0;
 Double valor_total    = new Double(valor_t);
 double monto_total1   = valor_total.doubleValue();              //ENVIADO EN EL REGISTRO1
 double monto_total2   = 0;                       //CALCULADO EN LOS REGISTROS2
 double monto_total3   = 0;                      //CALCULADO EN LOS REGISTROS3
 double total_afiliado = 0;                     //APORTE TOTALES A FAVOR DEL AFILIADO
 double total_empleador= 0;                    //APORTES TOTALES A FAVOR DEL EMPLEADOR
 registro_str          = registro_str.trim();
 registro_str          = registro_str.substring(55,registro_str.length());

 boolean era2          = true;
 boolean era3          = false;
 String porcion_x      = " ";
 int valor_x           = 206;
 String registros2     = " ";
 String registros3     = " ";
 double k1             = 0;
 double k2             = 0;
 double rtos1          = 0;
 double rtos2          = 0;
 double beneficio_t1   = 0;
 double beneficio_t2   = 0;
 int indice1           = 0;
 int indice2           = 0;
 String str1           = " ";
 String str2           = " ";
 double kh_a           = 0;
 double kh_e           = 0;
 double rtosh          = 0;
 String strtmp1        = " ";
 int consecutivo       = 0;
 //divido string por tipo de registro(rec2 y rec3)
 do
 {
  porcion_x    = registro_str.substring(0,valor_x);
  if(valor_x+2<registro_str.length()){
  registro_str = registro_str.substring(valor_x+2,registro_str.length());}
  else{registro_str = " ";}
  //valido estado de booleanos y acumulativos
  if(era2)
  {
   era2        = false;
   registros2 += porcion_x;
   registros3 += "@";
  }
  else if(era3)
  {
   total_rec3++;
   era3        = false;
   registros3 += porcion_x;
  }
  //defino valor X
  if(registro_str.substring(0,1).equals("3"))
  {
   era3    = true;
   valor_x = 75;
  }
  else if(registro_str.substring(0,1).equals("2"))
  {
   era2    = true;
   valor_x = 206;
  }
  else
  {
   System.out.println("Registro no igual a 2");
  }
 }while (registro_str.length()>=75);
 afiliados_reales = registros2.length()/206;
  //acumulo totales por afiliado(REGISTROS TIPO 2)
   for(int i=0;i<afiliados_reales;i++)
   {
     //registros2 = encode(registros2);
     registros2 =  new String(registros2.getBytes("ASCII"), "ASCII");
     str0         = registros2.substring(1,206);

     registros2   = registros2.substring(206,registros2.length());

     str1  = str0.substring(102,119);

     Double vlr1  = new Double(str1);
     k1          += vlr1.doubleValue();//capital de afiliado
     //signo
     String signo = str0.substring(136,137);
     String str3  = str0.substring(119,136);
     Double vlr3  = new Double(str3);
     if(signo.equals("+"))     rtos1 += vlr3.doubleValue();//rto de afiliado
     else if(signo.equals("-"))rtos1 -= vlr3.doubleValue();//rto de afiliado
     str2  = str0.substring(154,171);
     Double vlr2  = new Double(str2);
     k2          += vlr2.doubleValue();//capital de empleador
     //signo
     signo        = str0.substring(188,189);
     String str4  = str0.substring(171,188);
     Double vlr4  = new Double(str4);
     if(signo.equals("+"))     rtos2 += vlr4.doubleValue();//rto de empleador
     else if(signo.equals("-"))rtos2 -= vlr4.doubleValue();//rto de empleador
   }
   monto_total2    = k1+k2+rtos1+rtos2;
   total_afiliado  = k1+rtos1;
   total_empleador = k2+rtos2;
   //si el monto del registro 1 y el de los registros 2 son iguales entonces calculo monto de los tres
   str0  = registros3.substring(2,registros3.length());
   str0 += "@";

   if(monto_total1==monto_total2)
    {
     for(int j=0;j<afiliados_reales;j++)
      {
       indice1 = str0.indexOf("@");
       str1    = str0.substring(0,indice1);
       str0    = str0.substring(indice1+1,str0.length());

       do
       {

        str2           = str1.substring(0,75);//(0,indice2+1);
        str1           = str1.substring(75,str1.length());//(indice2+1,str1.length());

          //acumulo totales tanto para afiliado como para empleador
         //signo
        String señal   = str2.substring(74,75);
        //rtos
        String strtmp2 = str2.substring(57,74);
        Double vlrtmp2 = new Double(strtmp2);
        //valor historico
        strtmp1        = str2.substring(23,40);
        Double vlrtmp1 = new Double(strtmp1);
        if(str2.substring(22,23).equals("A"))
         {
          kh_a += vlrtmp1.doubleValue();//valor historico PARA AFILIADOS
          if(señal.equals("+"))
             kh_a += vlrtmp2.doubleValue();//rto
          else if(señal.equals("-"))
             kh_a -= vlrtmp2.doubleValue();//rto
         }
        else if(str2.substring(22,23).equals("E"))
         {
           kh_e += vlrtmp1.doubleValue();//valor historico PARA EMPLEADOR
           if(señal.equals("+"))
             kh_e += vlrtmp2.doubleValue();//rto
          else if(señal.equals("-"))
             kh_e -= vlrtmp2.doubleValue();//rto
         }
       }while(str1.length()>=75);
      }//fin for afiliados_reales



      monto_total3 = kh_a + kh_e;
      //si el monto del registro 1 y el de los registros 2 son iguales entonces calculo monto de los tres
       str0  = registros3.substring(2,registros3.length());
       str0 += "@";
      if(total_afiliado==kh_a&&total_empleador==kh_e)
      {//DEBO PARTIR Y CALCULAR LA INFORMACION PARA INSERTARLA EN TBINTERFACE_TRASLADOS
        for(int j=0;j<afiliados_reales;j++)
         {
          indice1 = str0.indexOf("@");
          str1    = str0.substring(0,indice1);
          str0    = str0.substring(indice1+1,str0.length());
          do
           {
            str2           = str1.substring(0,75);//(0,indice2+1);//CONTIENE INFORMACION DE CADA APORTE
            str1           = str1.substring(75,str1.length());//(indice2+1,str1.length());
            consecutivo++;
            String t_i     = str2.substring(1,3);
            String num_i   = str2.substring(3,14);
            //Double int1    = new Double (num_i);
            //Integer int1   = new Integer(num_i);
            //double n_i        = int1.doubleValue();
            String f_c     = str2.substring(14,22);//AAAAMMDD
            String t_a     = str2.substring(22,23);
            String valor   = str2.substring(23,40);
            Double db1     = new Double(valor);
            double v_h     = db1.doubleValue();
            String reten   = str2.substring(40,57);
            Double db2     = new Double(reten);
            double r_c     = db2.doubleValue();
            String rto     = str2.substring(57,74);
            Double db3     = new Double(rto);
            double rtos    = db3.doubleValue();
            String signo   = str2.substring(74,75);
            if(signo.equals("-")) rtos *= -1;
            CallableStatement t_cst8i_1 = c.prepareCall("{ call TBPBD_Inserta_Externo(?,?,?,?,?,?,?,?,?,?) }");
            t_cst8i_1.registerOutParameter(10,Types.VARCHAR);
            t_cst8i_1.setInt(1,consecutivo);
            t_cst8i_1.setString(2,t_i);
            //t_cst8i_1.setInt(3,n_i);
            t_cst8i_1.setString(3,num_i);
            t_cst8i_1.setString(4,f_c);
            t_cst8i_1.setString(5,t_a);
            t_cst8i_1.setDouble(6,v_h/100);
            t_cst8i_1.setDouble(7,r_c/100);
            t_cst8i_1.setDouble(8,rtos/100);
            t_cst8i_1.setString(9,afp_o);
            t_cst8i_1.execute();
            String estado_inserccion = t_cst8i_1.getString(10);
            t_cst8i_1.close();
//            salida.println("llamado a TBPBD_Inserta_Externo(?,?,?,?,?,?,?,?,?,?) } parametros:"+consecutivo+","+t_i+","+num_i+","+f_c+","+t_a+","+v_h/100+","+r_c/100+","+rtos/100+","+afp_o+", retorno:"+estado_inserccion);
            if(estado_inserccion.equals("ERROR"))
             {
               c.rollback();
               c.close();
             }
           }while(str1.length()>=75);
         }//for
        c.commit();
        c.close();
       }//fin si hay debo hacer inserccion
      else if(total_afiliado!=kh_a||total_empleador!=kh_e)
      {
        String msj = " ";
        if(total_afiliado!=kh_a)
          msj="LOS TOTALES DE AFILIADOS ENVIADOS EN EL REGISTRO DOS NO CONCUERDA CON EL TOTAL CALCULADO EN LOS REGISTROS TRES";
        else if(total_empleador!=kh_e)
          msj="LOS TOTALES DE EMPLEADOR ENVIADOS EN EL REGISTRO DOS NO CONCUERDA CON EL TOTAL CALCULADO EN LOS REGISTROS TRES";
        c.close();
        System.out.println("Error fin si hay error en los totales tres-dos ");
      }//fin si hay error en los totales tres-dos
    }//fin si montos iguales
   else
    {
     c.close();
     System.out.println("Error fin si hay error en los totales dos-uno ");
    }//fin si hay error en los totales dos-uno
}
catch(FileNotFoundException fe)
{
 System.out.println("Error archivo no existe ");
 String a="NOMBRE DE ARCHIVO INEXISTENTE ";
}
catch(Exception ex)
{
System.out.println("Error noidentificado");
String b="NOMBRE DE ARCHIVO INEXISTENTE ";
}
}
//----------------------------------------------------------------------------------------------------
public static void TBPL_carga_archivo(String archivo,PrintWriter salida,Connection c)
{
String url = "Onclick=window.location='/Servlets/TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS';";

//STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML;
try
{
 CallableStatement t_cst8i_0 = c.prepareCall("{ call TBPBD_Ruta_File(?) }");
 t_cst8i_0.registerOutParameter(1,Types.VARCHAR);
 t_cst8i_0.execute();
 String ruta = t_cst8i_0.getString(1);
 t_cst8i_0.close();
 if(ruta.equals("ERROR"))
  {
    salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                               " ","SE PRODUJO UN ERROR EN LA BASE DATOS, RUTA DE ARCHIVO INEXISTENTE, POR FAVOR INTENTE DE NUEVO"
                                               ,false));
   salida.println("<BR><BR>");
   salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
   salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
   salida.close();
   c.close();
  }
 String registro_str;
 String str0            = " ";
 String fecha_g         = " ";
 String total_a         = " ";
 String valor_t         = " ";
 String afp_o           = " ";
 String afp_d           = " ";
 archivo                = archivo.toUpperCase();
 byte registro_bytes[]  = new byte[10000000];
 File archivoentrada    = new File(ruta+archivo);
 FileInputStream lector = new FileInputStream(archivoentrada);
 lector.read(registro_bytes,0,10000000);
 registro_str          = new String(registro_bytes);

  //Capturo información del registro tipo 1
 fecha_g               = registro_str.substring(1,9);
 total_a               = registro_str.substring(9,14);
 valor_t               = registro_str.substring(14,31);
 afp_o                 = registro_str.substring(31,42);
 afp_d                 = registro_str.substring(42,53);
 //convierto tipos
 Integer total_afi     = new Integer(total_a);
 int afiliados         = total_afi.intValue();
 int afiliados_reales  = 0;
 int total_rec3        = 0;
 Double valor_total    = new Double(valor_t);
 double monto_total1   = valor_total.doubleValue();              //ENVIADO EN EL REGISTRO1
 double monto_total2   = 0;                       //CALCULADO EN LOS REGISTROS2
 double monto_total3   = 0;                      //CALCULADO EN LOS REGISTROS3
 double total_afiliado = 0;                     //APORTE TOTALES A FAVOR DEL AFILIADO
 double total_empleador= 0;                    //APORTES TOTALES A FAVOR DEL EMPLEADOR
 registro_str          = registro_str.trim();
 registro_str          = registro_str.substring(55,registro_str.length());

 boolean era2          = true;
 boolean era3          = false;
 String porcion_x      = " ";
 int valor_x           = 206;
 String registros2     = " ";
 String registros3     = " ";
 double k1             = 0;
 double k2             = 0;
 double rtos1          = 0;
 double rtos2          = 0;
 double beneficio_t1   = 0;
 double beneficio_t2   = 0;
 int indice1           = 0;
 int indice2           = 0;
 String str1           = " ";
 String str2           = " ";
 double kh_a           = 0;
 double kh_e           = 0;
 double rtosh          = 0;
 String strtmp1        = " ";
 int consecutivo       = 0;
 //divido string por tipo de registro(rec2 y rec3)
 do
 {
  porcion_x    = registro_str.substring(0,valor_x);
  if(valor_x+2<registro_str.length()){
  registro_str = registro_str.substring(valor_x+2,registro_str.length());}
  else{registro_str = " ";}
  //valido estado de booleanos y acumulativos
  if(era2)
  {
   era2        = false;
   registros2 += porcion_x;
   registros3 += "@";
  }
  else if(era3)
  {
   total_rec3++;
   era3        = false;
   registros3 += porcion_x;
  }
  //defino valor X
  if(registro_str.substring(0,1).equals("3"))
  {
   era3    = true;
   valor_x = 75;
  }
  else if(registro_str.substring(0,1).equals("2"))
  {
   era2    = true;
   valor_x = 206;
  }
  else
  {
   System.out.println(" ");
  }
 }
 while (registro_str.length()>=75);
 afiliados_reales = registros2.length()/206;
  //acumulo totales por afiliado(REGISTROS TIPO 2)
   for(int i=0;i<afiliados_reales;i++)
   {
     //registros2 =  new String(registros2.getBytes("ASCII"), "ASCII");
          
     str0         = registros2.substring(1,206);

     registros2   = registros2.substring(206,registros2.length());

     str1  = str0.substring(102,119);

     Double vlr1  = new Double(str1);
     k1          += vlr1.doubleValue();//capital de afiliado
     //signo
     String signo = str0.substring(136,137);
     String str3  = str0.substring(119,136);
     Double vlr3  = new Double(str3);
     if(signo.equals("+"))     rtos1 += vlr3.doubleValue();//rto de afiliado
     else if(signo.equals("-"))rtos1 -= vlr3.doubleValue();//rto de afiliado
     str2  = str0.substring(154,171);
     Double vlr2  = new Double(str2);
     k2          += vlr2.doubleValue();//capital de empleador
     //signo
     signo        = str0.substring(188,189);
     String str4  = str0.substring(171,188);
     Double vlr4  = new Double(str4);
     if(signo.equals("+"))     rtos2 += vlr4.doubleValue();//rto de empleador
     else if(signo.equals("-"))rtos2 -= vlr4.doubleValue();//rto de empleador
   }
   monto_total2    = k1+k2+rtos1+rtos2;
   total_afiliado  = k1+rtos1;
   total_empleador = k2+rtos2;
   //si el monto del registro 1 y el de los registros 2 son iguales entonces calculo monto de los tres
   str0  = registros3.substring(2,registros3.length());
   str0 += "@";

   if(monto_total1==monto_total2)
    {
     for(int j=0;j<afiliados_reales;j++)
      {
       indice1 = str0.indexOf("@");
       str1    = str0.substring(0,indice1);
       str0    = str0.substring(indice1+1,str0.length());

       do
       {
        /*indice2        = str1.trim().indexOf("+");

        if(indice2==-1)
         indice2      = str1.trim().indexOf("-");
        if(indice2==-1)
          System.out.println(" ");//signo de rendimientos desconocido, ni + ni -*/


        str2           = str1.substring(0,75);//(0,indice2+1);
        str1           = str1.substring(75,str1.length());//(indice2+1,str1.length());

          //acumulo totales tanto para afiliado como para empleador
         //signo
        String señal   = str2.substring(74,75);
        //rtos
        String strtmp2 = str2.substring(57,74);
        Double vlrtmp2 = new Double(strtmp2);
        //valor historico
        strtmp1        = str2.substring(23,40);
        Double vlrtmp1 = new Double(strtmp1);
        if(str2.substring(22,23).equals("A"))
         {
          kh_a += vlrtmp1.doubleValue();//valor historico PARA AFILIADOS
          if(señal.equals("+"))
             kh_a += vlrtmp2.doubleValue();//rto
          else if(señal.equals("-"))
             kh_a -= vlrtmp2.doubleValue();//rto
         }
        else if(str2.substring(22,23).equals("E"))
         {
           kh_e += vlrtmp1.doubleValue();//valor historico PARA EMPLEADOR
           if(señal.equals("+"))
             kh_e += vlrtmp2.doubleValue();//rto
          else if(señal.equals("-"))
             kh_e -= vlrtmp2.doubleValue();//rto
         }
       }while(str1.length()>=75);
      }//fin for afiliados_reales



      monto_total3 = kh_a + kh_e;
      //si el monto del registro 1 y el de los registros 2 son iguales entonces calculo monto de los tres
       str0  = registros3.substring(2,registros3.length());
       str0 += "@";
      if(total_afiliado==kh_a&&total_empleador==kh_e)
      {//DEBO PARTIR Y CALCULAR LA INFORMACION PARA INSERTARLA EN TBINTERFACE_TRASLADOS
        for(int j=0;j<afiliados_reales;j++)
         {
          indice1 = str0.indexOf("@");
          str1    = str0.substring(0,indice1);
          str0    = str0.substring(indice1+1,str0.length());
          do
           {
            /*indice2        = str1.indexOf("+");
            if(indice2==-1)
              indice2      = str1.indexOf("-");
            if(indice2==-1)
              System.out.println(" ");//signo de rendimientos desconocido, ni + ni -*/
            str2           = str1.substring(0,75);//(0,indice2+1);//CONTIENE INFORMACION DE CADA APORTE
            str1           = str1.substring(75,str1.length());//(indice2+1,str1.length());
            consecutivo++;
            String t_i     = str2.substring(1,3);
            String num_i   = str2.substring(3,14);
            //Double int1    = new Double (num_i);
            //Integer int1   = new Integer(num_i);
            //double n_i        = int1.doubleValue();
            String f_c     = str2.substring(14,22);//AAAAMMDD
            String t_a     = str2.substring(22,23);
            String valor   = str2.substring(23,40);
            Double db1     = new Double(valor);
            double v_h     = db1.doubleValue();
            String reten   = str2.substring(40,57);
            Double db2     = new Double(reten);
            double r_c     = db2.doubleValue();
            String rto     = str2.substring(57,74);
            Double db3     = new Double(rto);
            double rtos    = db3.doubleValue();
            String signo   = str2.substring(74,75);
            if(signo.equals("-")) rtos *= -1;
            CallableStatement t_cst8i_1 = c.prepareCall("{ call TBPBD_Inserta_Externo(?,?,?,?,?,?,?,?,?,?) }");
            t_cst8i_1.registerOutParameter(10,Types.VARCHAR);
            t_cst8i_1.setInt(1,consecutivo);
            t_cst8i_1.setString(2,t_i);
            //t_cst8i_1.setInt(3,n_i);
            t_cst8i_1.setString(3,num_i);
            t_cst8i_1.setString(4,f_c);
            t_cst8i_1.setString(5,t_a);
            t_cst8i_1.setDouble(6,v_h/100);
            t_cst8i_1.setDouble(7,r_c/100);
            t_cst8i_1.setDouble(8,rtos/100);
            t_cst8i_1.setString(9,afp_o);
            t_cst8i_1.execute();
            String estado_inserccion = t_cst8i_1.getString(10);
            t_cst8i_1.close();
//            salida.println("llamado a TBPBD_Inserta_Externo(?,?,?,?,?,?,?,?,?,?) } parametros:"+consecutivo+","+t_i+","+num_i+","+f_c+","+t_a+","+v_h/100+","+r_c/100+","+rtos/100+","+afp_o+", retorno:"+estado_inserccion);
            if(estado_inserccion.equals("ERROR"))
             {
               c.rollback();
               c.close();
               salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                               " ","SE PRODUJO UN ERROR EN LA BASE DATOS, INSERCCION NO REALIZADA, INTENTE DE NUEVO"
                                               ,false));
               salida.println("<BR><BR>");
               salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1)></center>");
               salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
               salida.close();
             }
           }while(str1.length()>=75);
         }//for
        c.commit();
        c.close();
       salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                             " ","CARGA TEMPORAL REALIZADA SATISFACTORIAMENTE"
                                             ,false));
       salida.println("<BR><BR>");
       salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-2)></center>");
       salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
       salida.close();
       }//fin si hay debo hacer inserccion
      else if(total_afiliado!=kh_a||total_empleador!=kh_e)
      {
        String msj = " ";
        if(total_afiliado!=kh_a)
          msj="LOS TOTALES DE AFILIADOS ENVIADOS EN EL REGISTRO DOS NO CONCUERDA CON EL TOTAL CALCULADO EN LOS REGISTROS TRES";
        else if(total_empleador!=kh_e)
          msj="LOS TOTALES DE EMPLEADOR ENVIADOS EN EL REGISTRO DOS NO CONCUERDA CON EL TOTAL CALCULADO EN LOS REGISTROS TRES";
        salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                             " ","INFORMACION ERRADA,"+msj
                                             ,false));
        salida.println("<BR><BR>");
        salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1)></center>");
        salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
        salida.close();
        c.close();
      }//fin si hay error en los totales tres-dos
    }//fin si montos iguales
   else
    {
     salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                               " ","INFORMACION ERRADA,LOS TOTALES ENVIADOS EN EL REGISTRO UNO NO CONCUERDAN CON LOS TOTALES CALCULADOS EN LOS REGISTROS DOS "
                                               ,false));
     salida.println("<BR><BR>");
     salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1)></center>");
     salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
     salida.close();
     c.close();
    }//fin si hay error en los totales dos-uno
}
catch(FileNotFoundException fe)
{

 salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                             " ","NOMBRE DE ARCHIVO INEXISTENTE EN EL SERVIDOR, VERIFIQUE LA UBICACION Y EL NOMBRE DEL ARCHIVO,"+
                                              "EL NOMBRE ES DE LA FORMA NOMBREARCHIVO.TXT",false));
 salida.println("<BR><BR>");
 salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1)></center>");
 salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
 salida.close();
}
catch(Exception ex)
{

salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                               " ","SE HA PRODUCIDO UN ERROR EN LA CARGA DEL PLANO, POR FAVOR INTENTE DE NUEVO"
                                               ,false));
salida.println("<BR><BR>");
salida.println("<span class=\"error\">La estructura del archivo plano no es la correcta, porfavor revisar el archivo</span>");
salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
salida.close();
}
}
//----------------------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------------------
public String getServletInfo()
 {return "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS Information";}
//----------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------


//----------------------------------------------------------------------------------------------------
}
