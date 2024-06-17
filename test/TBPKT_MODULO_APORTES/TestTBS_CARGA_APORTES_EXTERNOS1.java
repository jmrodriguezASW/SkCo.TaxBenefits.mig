package TEST.TBPKT_MODULO_APORTES;

import TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS1;
import TBPKT_MODULO_APORTES.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import java.sql.*;
import java.util.*;
import java.io.*;

public class TestTBS_CARGA_APORTES_EXTERNOS1 
{
  public static Connection con = null;
    
  public TestTBS_CARGA_APORTES_EXTERNOS1()
  { 
  }

  public void TBPL_cargarHistoriaExternaTest(String v_contrato, String v_producto, String v_usuario, 
  String v_f_producto, String v_f_contrato, String v_codigo_afp, String v_nit_afp, String v_conse){

      try{
        TBS_CARGA_APORTES_EXTERNOS1 tras = new TBS_CARGA_APORTES_EXTERNOS1();
        tras.cargarHistoriaExterna(v_contrato, v_producto, v_usuario, v_f_producto, v_f_contrato, v_codigo_afp, v_nit_afp, v_conse);

      }catch (Exception e){
         System.out.println("Error TBPL_cargarHistoriaExternaTest: "+e);
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
      String v_contrato="B"; 
      String v_producto="B"; 
      String v_usuario="TEST";
      String v_f_producto="MFUND";
      String v_f_contrato="000232149";
      String v_codigo_afp="10";
      String v_nit_afp="00830070784";
      String v_conse="7177614";

      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection v_conexion_taxb = DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
 
      TestTBS_CARGA_APORTES_EXTERNOS1 inforTraslados = new TestTBS_CARGA_APORTES_EXTERNOS1();
      /*Prueba TBPL_carga_archivo*/  
      inforTraslados.TBPL_cargarHistoriaExternaTest(v_contrato, v_producto, v_usuario, v_f_producto, v_f_contrato, v_codigo_afp, v_nit_afp, v_conse);
      
      }catch (Exception e){
         System.out.println("Test TestTBS_CARGA_APORTES_EXTERNOS  "+e);
      }
    
  }
}