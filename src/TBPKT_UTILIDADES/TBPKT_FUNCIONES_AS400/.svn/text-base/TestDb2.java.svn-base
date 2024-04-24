package TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



 /**Función que consulta al as400 si el contrato tiene bloqueo de egresos. Procedimiento AJKGXFR.*/
 
public class TestDb2 extends Object {

    public static String  TestAS400() {
    Connection connection = null;
    String rta = "";
      
      try{	
        DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver());	
        connection = DriverManager.getConnection ("jdbc:as400://10.81.250.86/","MFTAXBEN","taxb2k");
        System.out.println("step 1");	
        Statement st = connection.createStatement();	
        System.out.println("step 2");
        ResultSet rs = st.executeQuery("SELECT * FROM LAAMINTER.ajkpcpp");
        while(rs.next()){ 
          System.out.println("empid "+rs.getString(1));
        }	
      } catch ( Exception e ) {
        System.out.println("step 3"+e);
        e.printStackTrace();
        return e.toString();
      }
      finally {
        if(connection!=null){
          try {
            connection.close();
          } catch (SQLException e1) {
              e1.printStackTrace();
              return e1.toString();
          }
        }
        return rta;
      }
    }
}
