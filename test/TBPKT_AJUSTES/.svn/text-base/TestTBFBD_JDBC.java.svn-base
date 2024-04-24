package TEST.TBPKT_AJUSTES;

import TBPKT_AJUSTES.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import oracle.jdbc.OracleTypes.*;


public class TestTBFBD_JDBC 
{
  public static Connection con = null;
    
  public TestTBFBD_JDBC()
  { 
  }

  public void TBFBD_nextConsecAjusTest(){

      try{
      
        TBFBD_JDBC tbjdbc = new TBFBD_JDBC();
        String Resultado  = tbjdbc.TBFBD_nextConsecAjus();
        System.out.println(Resultado);

      }catch (Exception e){
         System.out.println("Error TBFBD_nextConsecAjusTest: "+e);
      }
        
  }
  

  public void TBFBD_nextConsecAjusTestConex(){

      try{
      
        TBFBD_JDBC tbjdbc = new TBFBD_JDBC();
        String Resultado  = tbjdbc.TBFBD_nextConsecAjusConex(con);
        System.out.println(Resultado);

      }catch (Exception e){
         System.out.println("Error TBFBD_nextConsecAjusTest: "+e);
      }
        
  }
  

  public static void main(String[] args)
  {

      /*Conexion con la base de datos*/
      
      try {
      TBCL_Validacion valuesUser = new TBCL_Validacion();
      String[] valuesUs = new String[3];
      valuesUs = valuesUser.TBFL_ValidarUsuario();
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      con = DriverManager.getConnection(valuesUs[0],valuesUs[1],valuesUs[2]);
      
      TestTBFBD_JDBC infortbjdbc = new TestTBFBD_JDBC();
      /*Prueba TBFBD_nextConsecAjusTest()*/    
      //infortbjdbc.TBFBD_nextConsecAjusTest();
      /*Prueba TBFBD_nextConsecAjusTestConex()*/    
      infortbjdbc.TBFBD_nextConsecAjusTestConex();

      }catch (Exception e){
         System.out.println("Test TBCL AS400 "+e);
      }
    
  }
}