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
          /*[SO_396] Se realiza modificación de llamado por ser método estático TBFBD_nextConsecAjus de la clase TBFBD_JDBC, no es necesaria la instancia nueva*/
        //TBFBD_JDBC tbjdbc = new TBFBD_JDBC();
        String Resultado  = TBFBD_JDBC.TBFBD_nextConsecAjus();
        System.out.println(Resultado);

      }catch (Exception e){
         System.out.println("Error TBFBD_nextConsecAjusTest: "+e);
      }
        
  }
  

  public void TBFBD_nextConsecAjusTestConex(){

      try{
          /*[SO_396] Se realiza modificación de llamado por ser método estático TBFBD_nextConsecAjusConex de la clase TBFBD_JDBC, no es necesaria la instancia nueva*/
        TBFBD_JDBC tbjdbc = new TBFBD_JDBC();
        String Resultado  = TBFBD_JDBC.TBFBD_nextConsecAjusConex(con);
        System.out.println(Resultado);

      }catch (Exception e){
         System.out.println("Error TBFBD_nextConsecAjusTest: "+e);
      }
        
  }
  

  public static void main(String[] args)
  {

      /*Conexion con la base de datos*/
      
      try {
          /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_ValidarUsuario de la clase TBCL_Validacion, no es necesaria la instancia nueva*/
      //TBCL_Validacion valuesUser = new TBCL_Validacion();
      String[] valuesUs = new String[3];
      valuesUs = TBCL_Validacion.TBFL_ValidarUsuario();
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