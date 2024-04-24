package TBPKT_MODULO_APORTES;

import java.sql.*;
import java.util.*;
import TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS.SQLTools;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

import oracle.jdbc.*;

/**
 * TBS_CARGAR_HISTORIA
 * TBS_CARGAR_PENDIENTES: Carga la historia de los aportes que tienen pendiente
 * su historia y que son originados por traslados internos.
 * @date 01/08/07      
 * @author: Marcela Ortiz Sandoval nortiz@skandia.com.co
 */
 
public class TBS_CARGAR_HISTORIA 
{

 //public static String CONSULTA_PENDIENTES = "SELECT APO_CON_PRO_CODIGO, APO_CON_NUMERO, APO_CONSECUTIVO, APO_USUARIO FROM TBAPORTES WHERE APO_CON_PRO_CODIGO = 'MFUND' AND APO_AFP_CODIGO='22' AND APO_REF_ESTADO='SEA001' AND APO_APORTE_TRASLADO='S' AND APO_INFORMACION_TRASLADO='N' AND APO_APORTE_CARGUE='N' AND APO_NUMERO_UNIDADES = APO_SALDO_NUMERO_UNIDADES";
 public static String PROCEDIMIENTO_FECHA_SIGUIENTE_HABIL =  SQLTools.buildProcedureCall(null, "TBPBD_FECHA_CIERRE_VALIDA", 1);
 public PreparedStatement preStam;
 public ResultSet resultSet, aportes, vresconsec;
 public CallableStatement cstat1;
 //private static Connection conn = null;
 
  public void TBS_CARGAR_PENDIENTES() {
      Boolean resul1 = Boolean.FALSE;
      Boolean resul2 = Boolean.FALSE;
      Boolean resul3 = Boolean.FALSE;
      Boolean resul4 = Boolean.FALSE;
      Boolean resul5 = Boolean.FALSE;
      int consec = 1;
      String fechaSigHabil;
      int v_cod_err;
      String v_men_err;
      Connection conn = null;
      
    try{
      //Conexion con la base de datos
      conn =   DataSourceWrapper.getInstance().getConnection();

      cstat1 = conn.prepareCall(PROCEDIMIENTO_FECHA_SIGUIENTE_HABIL);
      cstat1.registerOutParameter(1, OracleTypes.VARCHAR);
      cstat1.execute();
      fechaSigHabil = cstat1.getString(1);
      cstat1.close();

      System.out.println("Iniciaa cargue de historia para aportes pendientes ");
      
      cstat1 = conn.prepareCall("{ call TBPBD_CARGAR_HISTORIA(?,?,?,?,?,?) }");
      cstat1.registerOutParameter(5,Types.NUMERIC);
      cstat1.registerOutParameter(6,Types.VARCHAR);
      cstat1.setString(1,fechaSigHabil);
      cstat1.setString(2,"MFUND");
      cstat1.setString(3,"22");
      cstat1.setDouble(4,0.025);
      cstat1.execute();
      v_cod_err = cstat1.getInt(5);
      v_men_err = cstat1.getString(6);
      cstat1.close();
      
      if (v_cod_err==0){
        System.out.println("Termina cargue historia MFUND");
        cstat1 = conn.prepareCall("{ call TBPBD_CARGAR_HISTORIA(?,?,?,?,?,?) }");
        cstat1.registerOutParameter(5,Types.NUMERIC);
        cstat1.registerOutParameter(6,Types.VARCHAR);
        cstat1.setString(1,fechaSigHabil);
        cstat1.setString(2,"SIPEN");
        cstat1.setString(3,"29");
        cstat1.setDouble(4,0.025);
        cstat1.execute();
        v_cod_err = cstat1.getInt(5);
        v_men_err = cstat1.getString(6);
        cstat1.close();
         if (v_cod_err==0){
          System.out.println("Termina cargue historia SIPEN");
         }else
      {
        System.out.println("Error en el cargue de historia SIPEN" + v_men_err);
      } 
          
      }else
      {
        System.out.println("Error en el cargue de historia MFUND" + v_men_err);
      }
     

    }  catch (Exception e){
         System.out.println("Cargue de historia para aportes pendientes "+e);
    }
      finally{
              try{
                      DataSourceWrapper.closeConnection(conn);                  
              }catch(SQLException sqlEx){
                      System.out.println(sqlEx.toString());
              }
      }
  }




}