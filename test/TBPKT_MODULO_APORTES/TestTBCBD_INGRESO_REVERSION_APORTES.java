package TEST.TBPKT_MODULO_APORTES;

import TBPKT_MODULO_APORTES.TBCBD_INGRESO_REVERSION_APORTES;
import TBPKT_MODULO_APORTES.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import java.sql.*;
import java.util.*;
import java.io.*;

public class TestTBCBD_INGRESO_REVERSION_APORTES 
{
  public static Connection con = null;
    
  public TestTBCBD_INGRESO_REVERSION_APORTES()
  { 
  }

  public void  TBPL_ValidacionesTest(Connection c,int fecha_cargue){

      try{
        /*[SO_396] Se realiza modificación de llamado por ser método estático TBPL_Validaciones de la clase TBCBD_INGRESO_REVERSION_APORTES, no es necesaria la instancia nueva*/
        //TBCBD_INGRESO_REVERSION_APORTES tras = new TBCBD_INGRESO_REVERSION_APORTES();
        TBCBD_INGRESO_REVERSION_APORTES.TBPL_Validaciones(c, fecha_cargue);

      }catch (Exception e){
         System.out.println("Error TBPL_ValidacionesTest: "+e);
      }
        
  }
  
    public void  TBFL_IncioTest(){

      try{
        /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Inicio de la clase TBCBD_INGRESO_REVERSION_APORTES, no es necesaria la instancia nueva*/
        //TBCBD_INGRESO_REVERSION_APORTES tras = new TBCBD_INGRESO_REVERSION_APORTES();
        TBCBD_INGRESO_REVERSION_APORTES.TBFL_Inicio();

      }catch (Exception e){
         System.out.println("Error TBPL_ValidacionesTest: "+e);
      }
        
  }
  

  public static void main(String[] args)
  {

      /*Conexion con la base de datos*/
      //logger.info
      try {
      
          String estado = " ";
         int contador_repeticiones = 0;
         //Realizo Conexión Sobre Tax
         
 
 //TBCL_Validacion TBCL_Validacion.= new TBCL_Validacion1();   


         String[] v_valusu          = new String[3];
         v_valusu                   = TBCL_Validacion.TBFL_ValidarUsuario();
         Class.forName("oracle.jdbc.driver.OracleDriver");
         Connection v_conexion_taxb = DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
                  System.out.println("maquina "+v_valusu[0]);
                  System.out.println("usu "+v_valusu[1]);
                  System.out.println("pass "+v_valusu[2]);
         v_conexion_taxb.setAutoCommit(false);
         System.out.println("Realiza conexion");
      
        TestTBCBD_INGRESO_REVERSION_APORTES ingresoRev = new TestTBCBD_INGRESO_REVERSION_APORTES();
        /*Prueba TBPL_ValidacionesTest*/    
        //ingresoRev.TBPL_ValidacionesTest(v_conexion_taxb,20130123);
        ingresoRev.TBFL_IncioTest();

      }catch (Exception e){
         System.out.println("Test TestTBS_CARGA_APORTES_EXTERNOS  "+e);
      }
    
  }
}