package TEST.TBPKT_MODULO_APORTES;

import TBPKT_MODULO_APORTES.TBCBD_STORED_PROCEDURES;
import TBPKT_MODULO_APORTES.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;

public class TestTBCBD_STORED_PROCEDURES 
{
  public static Connection con = null;
    
  public TestTBCBD_STORED_PROCEDURES()
  { 
  }

  public void  TBPBD_Cuenta_Contingente_Fecha_Test(String producto,double capital, String f_a,//in
                                      double[] c_c){

      try{
      
        TBCBD_STORED_PROCEDURES tras = new TBCBD_STORED_PROCEDURES();
        //tras.TBPBD_Cuenta_Contingente_Fecha(producto, capital, f_a, c_c);

      }catch (Exception e){
         System.out.println("Error TBPBD_Cuenta_Contingente_Fecha_Test: "+e);
      }
        
  }
  
    public void  TBPBD_Cuenta_Contingente_Test(String producto,double capital, //in
                                      double[] c_c){

      try{
      
        TBCBD_STORED_PROCEDURES tras = new TBCBD_STORED_PROCEDURES();
        tras.TBPBD_Cuenta_Contingente(producto, capital, c_c);

      }catch (Exception e){
         System.out.println("Error TBPBD_Cuenta_Contingente_Fecha_Test: "+e);
      }
    }  
      public void  TBPBD_Valores_Totales_AportesTest(int[] v_registros_mf,double[] v_ingresos_mf,double[] v_rtos_mf,double[] v_cc_mf
                                           ,String[] estado){
    
      try{
          estado[0]="BIEN";
          TBCL_Validacion  i_valusu  = new     TBCL_Validacion ();
          String[] v_valusu          = new String[3];
          v_valusu                   = i_valusu.TBFL_ValidarUsuario();
         Class.forName("oracle.jdbc.driver.OracleDriver");
         Connection c = DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
         
          String t_select8i_1        = "SELECT KLQENU,KLSGVA,KLV4VZ,KLV6VZ FROM AJKLCPP@MFUND WHERE KLVGSZ = '01' ";
          PreparedStatement t_st8i_1 = c.prepareStatement(t_select8i_1);
          ResultSet t_rs8i_1;
          t_rs8i_1 = t_st8i_1.executeQuery();
          while(t_rs8i_1.next())
             {
              v_registros_mf[0] = t_rs8i_1.getInt(1);
              v_ingresos_mf[0]  = t_rs8i_1.getDouble(2);
              v_rtos_mf[0]      = t_rs8i_1.getDouble(3);
              v_cc_mf[0]        = t_rs8i_1.getDouble(4);
             }
          t_rs8i_1.close();
          t_st8i_1.close();
          //acumulo los rec 2
          int v_registros_mfa    = 0;
          double v_ingresos_mfa  = 0;
          double v_rtos_mfa      = 0;
          double v_cc_mfa        = 0;
          String t_select8i_2        = "SELECT KLSGVA,KLV4VZ,KLV6VZ FROM AJKLCPP@MFUND WHERE KLVGSZ = '02' ";
          PreparedStatement t_st8i_2 = c.prepareStatement(t_select8i_2);
          ResultSet t_rs8i_2;
          t_rs8i_2  = t_st8i_2.executeQuery();
          while(t_rs8i_2.next())
             {
              v_registros_mfa++;
              v_ingresos_mfa  += t_rs8i_2.getDouble(1);
              v_rtos_mfa      += t_rs8i_2.getDouble(2);
              v_cc_mfa        += t_rs8i_2.getDouble(3);
             }
          t_rs8i_2.close();
          t_st8i_2.close();
          if(v_registros_mfa==v_registros_mf[0]&&v_ingresos_mfa==v_ingresos_mf[0]&&v_rtos_mfa==v_rtos_mf[0]&&v_cc_mfa==v_cc_mf[0])
          //{
            estado[0]="OK";
          /*}
          else
          {
            estado[0]="MAL";
          }*/
       }catch(Exception ex){estado[0] = "ERROR: "+ex;}



        
  }
  

  public static void main(String[] args)
  {

      /*Conexion con la base de datos*/
      //logger.info
      try {
      
        String producto="MFUND";
        double capital=15000;
        String f_a="2001-10-20";
        double[] c_c= new double[2];
        int[] v_registros_mf = new int[1000];
        double[] v_ingresos_mf = new double[1000];
        double[] v_rtos_mf = new double[1000];
        double[] v_cc_mf = new double[1000];
        String[] estado = new String[1000];
        String[] ruta_funcion =   new String[100];
          String[] nombre_sistema =   new String[100];
          String[] usuario_mfund =   new String[100];
          String[] password_mfund =   new String[100];
          String resp = "";
          
          
        TBCL_FuncionesAs400_Oblig obj2 = new TBCL_FuncionesAs400_Oblig();  
        TBCBD_STORED_PROCEDURES obj1 = new TBCBD_STORED_PROCEDURES();  
        
        //obj.TBPBD_Parametros_FuncionesAS(ruta_funcion, nombre_sistema, usuario_mfund, password_mfund);  
       // obj1.TBPBD_Param_FuncAS_OBLIG("FPOB", ruta_funcion, nombre_sistema, usuario_mfund, password_mfund);
        obj2.TBFL_COMISION_ADMIN_OBLIG( nombre_sistema[0], ruta_funcion[0], usuario_mfund[0], password_mfund[0],"FPOB","50");  
        //resp = obj2.TBFL_Reporte_CEI_OBLIG("20180907","/QSYS.LIB/PRBACS.LIB/", nombre_sistema[0], usuario_mfund[0], password_mfund[0]);
         //resp = obj2.TBFL_Procesar_Retiros_OBLIG("20180824",ruta_funcion[0], nombre_sistema[0], usuario_mfund[0], password_mfund[0]);
       //ruta_funcion[0] = "/QSYS.LIB/PRBACS.LIB/";
        //resp = obj2.TBFL_PruebaAdriana("20180824",ruta_funcion[0], nombre_sistema[0], usuario_mfund[0], password_mfund[0]);
       // TestTBCBD_STORED_PROCEDURES storedProc = new TestTBCBD_STORED_PROCEDURES();
        /*Prueba TBPBD_Cuenta_Contingente_Fecha_Test*/    
        //storedProc.TBPBD_Cuenta_Contingente_Fecha_Test(producto, capital, f_a, c_c);
        /*Prueba TBPBD_Cuenta_Contingente_Test*/    
        //storedProc.TBPBD_Cuenta_Contingente_Test(producto, capital, c_c);
        /*Prueba TBPBD_Valores_Totales_AportesTest*/    
        //storedProc.TBPBD_Valores_Totales_AportesTest(v_registros_mf,v_ingresos_mf,v_rtos_mf,v_cc_mf,estado);
        System.out.println("Estado:"+estado[0]);
        

      }catch (Exception e){
         System.out.println("Test TestTBCBD_STORED_PROCEDURES  "+e);
      }
    
  }
}