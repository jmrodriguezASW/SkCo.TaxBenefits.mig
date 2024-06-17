package TEST.TBPKT_MODULO_APORTES;

import TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS;
import TBPKT_MODULO_APORTES.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import java.sql.*;
import java.util.*;
import java.io.*;

public class TestTBS_CARGA_APORTES_EXTERNOS 
{
  public static Connection con = null;
    
  public TestTBS_CARGA_APORTES_EXTERNOS()
  { 
  }

  public void TBPL_carga_archivoTest(String archivo,Connection c){

      try{
          /*[SO_396] Se realiza modificación de llamado por ser método estático TBPL_carga_archivoNoWeb de la clase TBS_CARGA_APORTES_EXTERNOS, no es necesaria la instancia nueva*/
        //TBS_CARGA_APORTES_EXTERNOS tras = new TBS_CARGA_APORTES_EXTERNOS();
        TBS_CARGA_APORTES_EXTERNOS.TBPL_carga_archivoNoWeb(archivo, c);

      }catch (Exception e){
         System.out.println("Error TBPL_carga_archivoTest: "+e);
      }
        
  }
  

  public static void main(String[] args)
  {

      /*Conexion con la base de datos*/
      //logger.info
      try {
       
 
 //TBCL_Validacion TBCL_Validacion.= new TBCL_Validacion1();   


       String[] v_valusu          = new String[3];
       v_valusu                   = TBCL_Validacion.TBFL_ValidarUsuario();
      String  archivo = "000232149.txt";
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection v_conexion_taxb = DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
 
      TestTBS_CARGA_APORTES_EXTERNOS inforTraslados = new TestTBS_CARGA_APORTES_EXTERNOS();
      /*Prueba TBPL_carga_archivo*/    
      inforTraslados.TBPL_carga_archivoTest(archivo, v_conexion_taxb);
      
      /*String cadena = "NIÑO PAGÜE DÍAZ";
      String cad = cadena;
      System.out.println(cad.length());
      System.out.println(cad.getBytes("UTF-8").length);
      System.out.println(cad.getBytes("UTF-16").length);
      System.out.println(cad.getBytes("LATIN1").length);
      System.out.println(cad.getBytes("ISO-8859-1").length);
      System.out.println(cad.getBytes("ASCII").length);
      cad =  new String(cad.getBytes("ASCII"), "ASCII");
      System.out.println(cad);
      System.out.println(cad.getBytes("UTF-8").length);*/
      
      //byte[] sha1hash = null;
      //md.update(text.getBytes("UTF-8"));
      
      /*System.out.println("Cadena 1:"+cadena);
      cadena =  new String(cadena.getBytes("UTF-8"), "UTF-8");
      System.out.println("Cadena 2:"+cadena);
      cadena =  new String(cadena.getBytes("UTF-16"), "UTF-16");
      System.out.println("Cadena 3:"+cadena);
      cadena =  new String(cadena.getBytes("LATIN1"), "LATIN1");
      System.out.println("Cadena 4:"+cadena);
      cadena =  new String(cadena.getBytes("ISO-8859-1"), "ISO-8859-1");
      System.out.println("Cadena 6:"+cadena);
      cadena =  new String(cadena.getBytes("ASCII"), "ASCII");
      System.out.println("Cadena 5:"+cadena);
      cadena =  new String(cadena.getBytes("UTF-8"), "UTF-8");
      System.out.println("Cadena 7:"+cadena);*/
      
      
      }catch (Exception e){
         System.out.println("Test TestTBS_CARGA_APORTES_EXTERNOS  "+e);
      }
    
  }
}