package TEST.TBPKT_INFORMATIVO.TBPKT_INFORMACION_TRASLADOS;

import TBPKT_INFORMATIVO.TBPKT_INFORMACION_TRASLADOS.TBCS_Informacion_Traslados;
import TBPKT_MODULO_APORTES.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import oracle.jdbc.OracleTypes.*;


public class TestInformacion_Traslados 
{
  public static Connection con = null;
    
  public TestInformacion_Traslados()
  { 
  }

  public void TBPL_TrasladosTest(int tipo, String nit_afpDestino, String[] params){

      try{
      
        TBCS_Informacion_Traslados tras = new TBCS_Informacion_Traslados();
        String[] Resultado  =  tras.TBFL_GenerarDocumento(tipo,nit_afpDestino,params);
        System.out.println(Resultado.length);

      }catch (Exception e){
         System.out.println("Error TBPL_TrasladosTest: "+e);
      }
        
  }
  

  public static void main(String[] args)
  {

      /*Conexion con la base de datos*/
      //logger.info
      try {
      
      /*String fecha = "20090511";
      int tipo =1;
      String afp ="34";
      String[] params;
      String fechaInicial ="2009-12-02";
      String fechaFinal  = "2009-12-02";
      String nit_afp = "0000800148959";//"00800224827";//"0008909039388";*/
      
      String fecha = "20100105";
      int tipo =3;
      String afp ="56"; 
      String[] params;
      String fechaInicial ="2013-04-04";
      String fechaFinal  = "2013-04-04";
      String nit_afp = "00830038085";
      String producto ="MFUND";
      String contrato= "000138151"; 
      //DAVI"0000860034313"//BANCOLOM"0000890903938";//"0008909039388";*/
      
      //Tipo 1
      //params = new String[]{afp,fechaInicial,fechaFinal};
      
      //Tipo 3
      //contrato,producto,codigo_afp,fechaInicial,fechaFinal,nit_afpDestino
      params = new String[]{contrato,producto,afp,fechaInicial,fechaFinal};
      TestInformacion_Traslados inforTraslados = new TestInformacion_Traslados();
      /*Prueba TBFL_GenerarDocumento;*/    
      inforTraslados.TBPL_TrasladosTest(tipo, nit_afp, params);
      //funcionesAs400.TBPL_ValorAportesFondosCerradosTest(contrato, sistema, usuario, password, libreria);
      
      /*Prueba TBPL_SaldosPorContrato*/
      //funcionesAs400.TBPL_SaldosPorContratoTest(contrato, fecha, sistema, usuario, password, libreria);
      
      }catch (Exception e){
         System.out.println("Test TBCL AS400 "+e);
      }
    
  }
}