package test.TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD;

import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.TBCL_AS400;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_MODULO_APORTES.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import oracle.jdbc.OracleTypes.*;
import com.ibm.as400.access.*;

public class TestTBCL_AS400
{
  public static Connection con = null;
  public static TBCL_AS400   ias400;


  public static void TBF_CSAFTest(AS400 as400, String v_libreria){

      try{
        int temp = ias400.TBF_CSAF(as400,v_libreria);
        System.out.print("Salida:"+temp);
      }catch (Exception e){
         System.out.println("Error TBPL_ValorAportesFondosCerradosTest"+e);
      }
        
  }

  public static void TBF_PASO6Test(String v_fecha_control, AS400 as400, String v_libreria){

      try{
        int temp = ias400.TBF_PASO6(v_fecha_control, as400,v_libreria);
        System.out.print("Salida:"+temp);
      }catch (Exception e){
         System.out.println("Error TBPL_ValorAportesFondosCerradosTest"+e);
      }
        
  }
  
  public void TBF_PASO6_STest(String v_fecha_control, String v_sistema, String 
        v_usuario, String v_pass,String v_libreria){

      try{
        int temp =  ias400.TBF_PASO6_S(v_fecha_control,v_sistema, v_usuario, v_pass,v_libreria);
  
        System.out.print("Salida:"+temp);
      }catch (Exception e){
         System.out.println("Error TBPL_ValorAportesFondosCerradosTest"+e);
      }
        
  }

  public static void main(String[] args)
  {

      /*Conexion con la base de datos*/
      //logger.info
      try {
      TBCL_Validacion valuesUser = new TBCL_Validacion();
      String[] valuesUs = new String[3];
      valuesUs = valuesUser.TBFL_ValidarUsuario();
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      con = DriverManager.getConnection(valuesUs[0],valuesUs[1],valuesUs[2]);
      String sistema="";
      String usuario="";
      String password="";
      String libreria="";
      AS400 as400;
      
      System.out.println("Intentará establecer conexión con 400");
      CallableStatement csPdto =  con.prepareCall("BEGIN :1 := TB_FREFERENCIAS_MULTI( :2 ,\n :3,\n :4 ,\n :5)\n; END;");
      csPdto.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      csPdto.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
      /* setear parametro de entrada IN */
      csPdto.setString(2,sistema);
      csPdto.setString(3,usuario);
      csPdto.setString(4,password);
      csPdto.setString(5,libreria);
      csPdto.execute();
      /* recupera parametros de salida*/
      //logger.info("Se tienen los parametros de 400");
      sistema = csPdto.getString(2);
      usuario = csPdto.getString(3);
      password = csPdto.getString(4);
      libreria = csPdto.getString(5);
      csPdto.close();
      
          /* Pruebas TBFL_BloqueoEgresosTest*/
      //ias400.TBF_PASO6_S("20130211", sistema, usuario, password, libreria);
      
      //Conectarse al AS400
      as400 = new AS400(""+sistema+"");
      try{
          //se conecta con usuario y password
          as400.setUserId(""+usuario+"");
          as400.setPassword(""+password+"");
          try{
            ias400 = new TBCL_AS400();
            //TBF_CSAFTest(as400, libreria);
            TBF_PASO6Test("20130429",as400, libreria);
            //v_cod_err = ias400.TBF_CSAF(as400, v_libreria);
          }
          catch(Exception e){ 
            System.out.println("Error en la ejecución de la función"+e);
          }
      }//Hubo error al conectarse al AS400
      catch (Exception e){
        System.out.println("Error al conectarse al As400 "+e);
      }
      
      /*Prueba TBPL_ValorAportesFondosCerrados*/
      //TestFuncionesAs400 funcionesAs400 = new TestFuncionesAs400();
      //funcionesAs400.TBPL_ValorAportesFondosCerradosTest(contrato, sistema, usuario, password, libreria);
      
      }catch (Exception ex){
         System.out.println("Test TBCL AS400 "+ex);
      }
    
  }
}