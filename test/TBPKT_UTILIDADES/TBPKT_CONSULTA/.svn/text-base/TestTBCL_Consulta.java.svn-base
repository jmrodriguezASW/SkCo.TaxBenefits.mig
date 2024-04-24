package TEST.TBPKT_UTILIDADES.TBPKT_CONSULTA;

import TBPKT_UTILIDADES.TBPKT_CONSULTA.*;
import java.io.*;
import java.util.*;
import java.text.*;


public class TestTBCL_Consulta 
{
  public TestTBCL_Consulta()
  {
  }
  
  public void TBFL_ConsultaRefUnicaTest(String v_producto, String v_contrato){
      try{
      
        TBCL_Consulta v_TBCL_Consulta = new TBCL_Consulta();
        v_TBCL_Consulta.TBPL_RealizarConexion();
        String temp = v_TBCL_Consulta.TBFL_ConsultaRefUnica(v_producto, v_contrato);
        System.out.print("Salida:"+temp);
      }catch (Exception e){
         System.out.println("Error TBFL_ConsultaRefUnica"+e);
      }
  
  }
  
    public void TBFL_ConsultaParametrosTest(String v_consulta, String[] v_parametros){
      try{
      
        TBCL_Consulta v_TBCL_Consulta = new TBCL_Consulta();
        v_TBCL_Consulta.TBPL_RealizarConexion();
        Vector temp = new Vector();

        temp = v_TBCL_Consulta.TBFL_ConsultaParametros(v_consulta, v_parametros);
        System.out.print("Salida:"+temp);
      }catch (Exception e){
         System.out.println("Error TBFL_ConsultaRefUnica"+e);
      }
  
  }
    
  public static void main(String[] args)
  {

      /*Conexion con la base de datos*/
      //logger.info
      try {
      System.out.println("Inicia la ejecución del test");
  
      TestTBCL_Consulta testTBCL_Consulta = new TestTBCL_Consulta();
      
      /*Prueba TBFL_ConsultaRefUnicaTest*/
      String producto ="MFUND";
      String contrato = "000001722";
      //testTBCL_Consulta.TBFL_ConsultaRefUnicaTest(producto, contrato);
      /*prueba TBFL_ConsultaParametrosTest*/
       String v_declaracion = "SELECT prg_codigo,\n"+
                  " prg_descripcion,\n"+
                  " prg_activo,\n"+
                  " prg_tipo_penalizacion\n"+
                  " FROM tbprogramas_parametrizacion\n"+
                  //" WHERE prg_codigo  = 'FPIN' \n";
                  " WHERE prg_codigo  = ? \n";
                  

       String v_parametros[] = new String[1];
       v_parametros[0] = "FPIN";
       testTBCL_Consulta.TBFL_ConsultaParametrosTest(v_declaracion, v_parametros);
      
      }catch (Exception e){
         System.out.println("Test TBCL Consulta"+e);
      }
    
  }
}

