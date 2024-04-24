package TBPKT_UTILIDADES.TBPKT_AS400_APORTES;

import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import com.ibm.as400.access.*;
import java.sql.*;

public class TBCL_FUNCIONES_AS400_APORTES extends Object
{
//*************************************************************************************************************************

    public static String TBFL_Grupo_Empresa_S(String grupoid, int v_index)
    {
        String v_unidadTokenized = "";
        String informacion_empresa[] = new String[v_index];
        informacion_empresa = TBFL_Grupo_Empresa(grupoid);
        int i = 0;
        v_unidadTokenized = informacion_empresa[i];
        do
            v_unidadTokenized = v_unidadTokenized + "\\" + informacion_empresa[++i];
        while(i < informacion_empresa.length - 1);
        return v_unidadTokenized;
    }

public static String[] TBFL_Grupo_Empresa(String grupoid)
{
String informacion_empresa[] = new String[5];
try
{
//conexion para ejecucion de parametros de funciones
 TBCL_Validacion  i_valusu   = new TBCL_Validacion();
 String[] v_valusu            = new String[3];
 v_valusu                     = i_valusu.TBFL_ValidarUsuario();
 Class.forName("oracle.jdbc.driver.OracleDriver");
 Connection v_conexion_taxb   = DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
 CallableStatement t_cst8i_1 = v_conexion_taxb.prepareCall("{ call TBPBD_Parametros_FuncionesAS(?,?,?,?) }");
 t_cst8i_1.registerOutParameter(1,Types.VARCHAR);
 t_cst8i_1.registerOutParameter(2,Types.VARCHAR);
 t_cst8i_1.registerOutParameter(3,Types.VARCHAR);
 t_cst8i_1.registerOutParameter(4,Types.VARCHAR);
 t_cst8i_1.execute();
 String ruta     = t_cst8i_1.getString(1);
 String sistema  = t_cst8i_1.getString(2);
 String usuario  = t_cst8i_1.getString(3);
 String password = t_cst8i_1.getString(4);
 t_cst8i_1.close();
 //realizo la conexión al sistema sadc2 con un userid y un password
//estos parametros serán leidos desde tbrefencias des sistema taxb
AS400 sadc2 = new AS400(sistema);sadc2.setUserId(usuario);sadc2.setPassword(password);
   //declaro y defino la lista de parametros, aquí configuro tanto los de entrada como los de salida
  //los parametros de entrada serán dummy(1),Número del Grupo(9)
 //los parametros de salida serán nombre del grupo,NIT del grupo
ProgramParameter[] parametros = new ProgramParameter[4];
//parametros de entrada
AS400Text t1         = new AS400Text(1);
byte[] by1           = t1.toBytes(" ");
parametros[0]        = new ProgramParameter(by1);
AS400Text t2         = new AS400Text(9);
byte[] by2           = t2.toBytes(grupoid);
parametros[1]        = new ProgramParameter(by2);
//parametros de salida
parametros[2]        = new ProgramParameter(9);
parametros[3]        = new ProgramParameter(13);
//realizo llamado al programa as400
ProgramCall programa = new ProgramCall(sadc2,ruta+"AJLGXFR.PGM",parametros);
if(programa.run())
{
 //capturo nombre del grupo
 AS400Text tx1           = new AS400Text(9);
 byte[] b1               = parametros[2].getOutputData();
 informacion_empresa[0]  = (String)tx1.toObject(b1);
 //capturo NIT del Grupo
 AS400Text tx2           = new AS400Text(13);
 byte[] b2               = parametros[3].getOutputData();
 informacion_empresa[1]  = (String)tx2.toObject(b2);
 sadc2.disconnectAllServices();
 return informacion_empresa;
}
else
 {
 sadc2.disconnectAllServices();
 return informacion_empresa;
 }
}
catch(Exception ex){System.out.println("");return informacion_empresa;}//EN FUNCIONES AS400(GRUPO EMPRESAS) SE PRESENTO EL SIGUIENTE ERROR: "+ex
}
//**************************************************************************************************************************************
public static String TBFL_Saldos_por_Contrato(String numero_contrato,String fecha_consulta,String tipo_fecha)
 //Esta función devuelve desde el as/400 los saldos por contrato en una cadena
  {
   try
   {
     //conexion para ejecucion de parametros de funciones
     TBCL_Validacion  i_valusu   = new TBCL_Validacion();
     String[] v_valusu            = new String[3];
     v_valusu                     = i_valusu.TBFL_ValidarUsuario();
     Class.forName("oracle.jdbc.driver.OracleDriver");
     Connection v_conexion_taxb   = DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
     String ruta      = new String();
     String sistema   = new String();
     String usuario   = new String();
     String password  = new String();
     CallableStatement t_cst8i_1 = v_conexion_taxb.prepareCall("{ call TBPBD_Parametros_FuncionesAS(?,?,?,?) }");
     t_cst8i_1.registerOutParameter(1,Types.VARCHAR);
     t_cst8i_1.registerOutParameter(2,Types.VARCHAR);
     t_cst8i_1.registerOutParameter(3,Types.VARCHAR);
     t_cst8i_1.registerOutParameter(4,Types.VARCHAR);
     t_cst8i_1.execute();
     ruta     = t_cst8i_1.getString(1);
     sistema  = t_cst8i_1.getString(2);
     usuario  = t_cst8i_1.getString(3);
     password = t_cst8i_1.getString(4);
     t_cst8i_1.close();
     v_conexion_taxb.close();
      //realizo la conexión al sistema sadc2 con un userid y un password
     //estos parametros serán leidos desde tbrefencias des sistema taxb
     AS400 sadc2 = new AS400(sistema);sadc2.setUserId(usuario);sadc2.setPassword(password);
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
     if(programa.run())
      {
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
       String cadena5  =(String)tx6.toObject(b6);
       cadena5=cadena5.trim();
       //por cada array de char mueva hasta al final, insertando todo aquello que sea diferente de punto y coma  en un array de int
       sadc2.disconnectAllServices();
       return cadena1+cadena2+cadena3+cadena4+cadena5;
      }
     else
      {
      sadc2.disconnectAllServices();
      return "ERROR";
      }
   }
   catch(Exception ex){System.out.println("");return "ERROR";}//EN FUNCIONES AS400(Saldos por Contrato) SE PRESENTO EL SIGUIENTE ERROR: "+ex
  }
//**************************************************************************************************************************************
public static String TBFL_Saldos_por_Contrato(String numero_contrato,String fecha_consulta,String tipo_fecha,AS400 sadc2,String ruta)
 //Esta función devuelve desde el as/400 los saldos por contrato en una cadena
  {
   try
   {
            //conexion para ejecucion de parametros de funciones
           //realizo la conexión al sistema sadc2 con un userid y un password
          //estos parametros serán leidos desde tbrefencias des sistema taxb
         //AS400 sadc2 = new AS400(sistema);sadc2.setUserId(usuario);sadc2.setPassword(password);
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
     if(programa.run())
      {
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
       String cadena5  =(String)tx6.toObject(b6);
       cadena5=cadena5.trim();
       //por cada array de char mueva hasta al final, insertando todo aquello que sea diferente de punto y coma  en un array de int
       sadc2.disconnectAllServices();
       return cadena1+cadena2+cadena3+cadena4+cadena5;             
      }
     else
      {
      sadc2.disconnectAllServices();
      return "ERROR";
      }
   }
   catch(Exception ex){System.out.println("");return "ERROR";}//EN FUNCIONES AS400(Saldos por Contrato) SE PRESENTO EL SIGUIENTE ERROR: "+ex
  }
//**************************************************************************************************************************************
public static long TBFL_Saldo_Entero(String cadenaT)
  {
  try
  {
   long saldoT = 0; int indice1 = 0;   int indice2 = 0; String str1 = "";  String str2 = "";
   do{
      indice1      = cadenaT.indexOf(";");
      str1         = cadenaT.substring(0,indice1);
      indice2      = str1.indexOf(",");
      str2         = str1.substring(indice2+1,indice1);
      cadenaT      = cadenaT.substring(indice1+1,cadenaT.length());
      Long saldoP  = new Long(str2);
      saldoT      += saldoP.longValue();
     }while(indice1<=cadenaT.length());
   return saldoT;
  }
  catch(Exception ex)
  {
   String s = ex.toString();
   if(s.trim().equals("java.lang.StringIndexOutOfBoundsException: String index out of range: -1"))
   {
     System.out.println("");//EN FUNCIONES AS400(Saldo Entero): el contenido de la cadena para el saldo del contrato en Multifund es vacia.
     return -1;
   }
   else
   {
     System.out.println("");return -2;//EN FUNCIONES AS400(Saldo Entero): "+ex
   }

  }
 }
//*************************************************************************************************************************
    
}

 