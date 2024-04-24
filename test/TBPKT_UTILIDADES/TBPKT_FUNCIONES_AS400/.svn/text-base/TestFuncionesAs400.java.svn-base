package TEST.TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400;

import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.TBCL_FuncionesAs400;
import TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQL_CIERRE_SALDOS;
import TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.TBCL_APLICACION_CIERRE;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_MODULO_APORTES.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import oracle.jdbc.OracleTypes.*;

//import taxbenefits.proxy.BasicHttpBinding_IAvailableServiceClient;
//import taxbenefits.proxy.types.org.datacontract.schemas._2004._07.skco_available_fsl.Saldo;

public class TestFuncionesAs400 
{
  public static Connection con = null;
    
  public TestFuncionesAs400()
  { 
  }

  public void TBPL_ValorAportesFondosCerradosTest(String v_contrato, String v_sistema, String 
        v_usuario, String v_pass,String v_libreria){

      try{
        String temp =  TBCL_FuncionesAs400.TBPL_ValorAportesFCerrados(v_contrato, v_sistema, v_usuario, v_pass,v_libreria);
        System.out.print("Salida:"+temp);
      }catch (Exception e){
         System.out.println("Error TBPL_ValorAportesFondosCerradosTest"+e);
      }
        
  }
  
  public void TBPL_SaldosPorContratoTest(String v_contrato, String v_fecha, String v_sistema, String 
        v_usuario, String v_pass,String v_libreria){

      try{
        String temp =  TBCL_FuncionesAs400.TBPL_SaldosPorContrato(v_contrato, v_fecha, "E", v_sistema, v_usuario, v_pass,v_libreria);
  
        System.out.print("Salida:"+temp);
      }catch (Exception e){
         System.out.println("Error TBPL_ValorAportesFondosCerradosTest"+e);
      }
        
  }
  
    public void TBFL_BloqueoEgresosTest(String v_contrato, String v_sistema, String 
        v_usuario, String v_pass,String v_libreria){

      try{
        String temp =  TBCL_FuncionesAs400.TBFL_BloqueoEgresos(v_contrato,v_sistema, v_usuario, v_pass,v_libreria);
  
        System.out.print("Salida:"+temp);
      }catch (Exception e){
         System.out.println("Error TBPL_ValorAportesFondosCerradosTest"+e);
      }
        
  }
  
    public void TBPL_SaldosContratoFonCerLTest(String v_contrato, String v_fecha, String v_sistema, String 
          v_usuario, String v_pass,String v_libreria){

        try{
          String temp =  TBCL_FuncionesAs400.TBPL_SaldoFCerrPorContrato(v_contrato, v_fecha, "E", v_sistema, v_usuario, v_pass,v_libreria);
    
          System.out.print("TBPL_SaldoFCerrPorContrato Salida:"+temp);
        }catch (Exception e){
           System.out.println("Error TBPL_ValorAportesFondosCerradosTest"+e);
        }
          
    }
    
    public void TBPL_SaldosDisponiblesPorContratoTest(String v_contrato, String v_fecha, String v_sistema, String 
          v_usuario, String v_pass,String v_libreria){

        try{
          String temp =  TBCL_FuncionesAs400.TBPL_DisponiblesPorContrato(v_contrato, v_fecha, "E", v_sistema, v_usuario, v_pass,v_libreria);
    
          //System.out.print("TBPL_SaldosDisponiblesPorContratoTest Salida:"+temp);
          int v_int1 = temp.indexOf(";",0);
          int v_int2 = temp.indexOf(";",v_int1+1);
          System.out.print("indices:"+v_int1 +","+v_int2+"\n");
          System.out.print("Saldo disponible:"+new Double(temp.substring(0, v_int1))+"\n");
          System.out.print("indices:"+v_int1 +","+v_int2+"\n");
          System.out.print("Saldo cerrados:"+new Double(temp.substring(v_int1+1, v_int2))+"\n");
          v_int1 = v_int2;
          v_int2 = temp.indexOf(";",v_int1+1);
          System.out.print("indices:"+v_int1 +","+v_int2+"\n");
            System.out.print("Saldo garantias:"+ temp.substring(v_int1+1, v_int2)+"\n");
          System.out.print("Saldo garantias:"+ new Double(temp.substring(v_int1+1, v_int2))+"\n");
          v_int1 = v_int2;
          v_int2 = temp.indexOf(";",v_int1+1);
          System.out.print("indices:"+v_int1 +","+v_int2+"\n");
          System.out.print("Programa:"+ temp.substring(v_int1+1, v_int2));  
            
        }catch (Exception e){
           System.out.println("Error TBPL_SaldosDisponiblesPorContratoTest"+e);
        }
          
    }

    public void TBPL_SaldoLiquidezContratoTest(String v_contrato, String v_fecha, String v_sistema, String 
          v_usuario, String v_pass,String v_libreria){

        try{
          String temp =  TBCL_FuncionesAs400.TBPL_SaldoLiquidezContrato(v_contrato, v_fecha, "E", "10000", v_sistema, v_usuario, v_pass,v_libreria);
    
          System.out.print("TBPL_SaldoLiquidezContrato Salida:"+temp);
        }catch (Exception e){
           System.out.println("Error TBPL_SaldoLiquidezContrato"+e);
        }
          
    }
  public void TBPL_SaldosLiqPorContratoTest(String v_contrato, String v_fecha, String v_sistema, String 
        v_usuario, String v_pass,String v_libreria){

      try{
        String temp =  TBCL_FuncionesAs400.TBPL_SaldosLiqPorContrato(v_contrato, v_fecha, "E", v_sistema, v_usuario, v_pass,v_libreria);
  
        System.out.print("Salida:"+temp);
      }catch (Exception e){
         System.out.println("Error TBPL_ValorAportesFondosCerradosTest"+e);
      }
        
  }
  
  public void TBPL_ValidarSaroTest(String v_saro, String v_sistema, String 
        v_usuario, String v_pass,String v_libreria){

      try{
        String temp =  TBCL_FuncionesAs400.TBPL_ValidarSaro(v_saro,v_sistema, v_usuario, v_pass,v_libreria);
  
        System.out.print("Salida:"+temp);
      }catch (Exception e){
         System.out.println("Error TBPL_ValidarSaro"+e);
      }
        
  }
  
  public void TBPL_TipoOperacionTrasladoTest(String v_contrato, String v_transaccion, String v_sistema, String 
        v_usuario, String v_pass,String v_libreria){

      try{
        String temp =  TBCL_FuncionesAs400.TBPL_TipoOperacionTraslado(v_contrato, v_transaccion,v_sistema, v_usuario, v_pass,v_libreria);
  
        System.out.print("Salida:"+temp);
      }catch (Exception e){
         System.out.println("Error TBPL_ValidarSaro"+e);
      }
        
  }
  
   /* public void TBPL_SaldoDISPorContrato(String v_contrato, String v_producto){

        try{
            BasicHttpBinding_IAvailableServiceClient myPort = new BasicHttpBinding_IAvailableServiceClient();
            System.out.print("calling " + myPort.getEndpoint());
            Saldo saldo = myPort.getAvailableBalance(v_contrato,v_producto,"E","10000");
            System.out.print("Salida:"+saldo.getSaldoDisponible().toString());
        }catch (Exception e){
           System.out.println("Error TBPL_ValorAportesFondosCerradosTest"+e);
        }
          
    }*/

  public static void main(String[] args)
  {

      /*Conexion con la base de datos*/
      //logger.info
      //args = new String[]{"20151231"};
      try {
          SQL_CIERRE_SALDOS.TB_CIERRE_SALDOS_CONTRATO("20151231");
          
          
      /*TBCL_Validacion valuesUser = new TBCL_Validacion();
      String[] valuesUs = new String[3];
      valuesUs = valuesUser.TBFL_ValidarUsuario();
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      con = DriverManager.getConnection(valuesUs[0],valuesUs[1],valuesUs[2]);
      String sistema  ="";
      String usuario  ="";
      String password ="";
      String libreria ="";
      String contrato ="000126484"; //"000016593";
      String producto ="MFUND";
      String fecha    = "20131029";
      String saro     = "000002066";
      String transaccion ="990922031761";
      //"000079470";
      
      System.out.println("Intentará establecer conexión con 400");
      CallableStatement csPdto =  con.prepareCall("BEGIN :1 := TB_FREFERENCIAS_MULTI( :2 ,\n :3,\n :4 ,\n :5)\n; END;");
      csPdto.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      csPdto.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR); */
      /* setear parametro de entrada IN */
   /*   csPdto.setString(2,sistema);
      csPdto.setString(3,usuario);
      csPdto.setString(4,password);
      csPdto.setString(5,libreria);
      csPdto.execute(); */
      /* recupera parametros de salida*/
  /*    sistema = csPdto.getString(2);
      usuario = csPdto.getString(3);
      password = csPdto.getString(4);
      libreria = csPdto.getString(5);
      csPdto.close(); */
  
    //  TestFuncionesAs400 funcionesAs400 = new TestFuncionesAs400();
      /*Prueba TBPL_ValorAportesFondosCerrados*/    
      //funcionesAs400.TBPL_ValorAportesFondosCerradosTest(contrato, sistema, usuario, password, libreria);
      
      /*Prueba TBPL_SaldosPorContrato*/
      //funcionesAs400.TBPL_SaldosPorContratoTest(contrato, fecha, sistema, usuario, password, libreria);
      
      /* Prueba TBPL_SaldosLiqPorContrato */
      //funcionesAs400.TBPL_SaldosLiqPorContratoTest(contrato, fecha, sistema, usuario, password, libreria);
      
      /* Prueba TBPL_SaldoLiqPorContrato */
      //funcionesAs400.TBPL_SaldoLiqPorContrato(contrato,producto);
      
      /* Pruebas TBPL_SaldosContratoFonLTest */
      //funcionesAs400.TBPL_SaldosContratoFonCerLTest(contrato, fecha, sistema, usuario, password, libreria);
      
      /* Pruebas TBPL_SaldoLiquidezContratoTest*/
      //funcionesAs400.TBPL_SaldoLiquidezContratoTest(contrato, fecha, sistema, usuario, password, libreria);

      /* Pruebas TBFL_BloqueoEgresosTest*/
      //funcionesAs400.TBFL_BloqueoEgresosTest(contrato, sistema, usuario, password, libreria);
      
      /* Pruebas TBFL_BloqueoEgresosTest*/
      //funcionesAs400.TBPL_ValidarSaroTest(saro, sistema, usuario, password, libreria);
      
      /* Pruebas TBPL_TipoOperacionTrasladoTest*/
      //funcionesAs400.TBPL_TipoOperacionTrasladoTest(contrato, transaccion, sistema, usuario, password, libreria);

      /* Prueba TBPL_SaldosLiqPorContrato */
    //  funcionesAs400.TBPL_SaldosDisponiblesPorContratoTest(contrato, fecha, sistema, usuario, password, libreria);
      
      }catch (Exception e){
         System.out.println("Test TBCL AS400 "+e);
      }
    
  }
}