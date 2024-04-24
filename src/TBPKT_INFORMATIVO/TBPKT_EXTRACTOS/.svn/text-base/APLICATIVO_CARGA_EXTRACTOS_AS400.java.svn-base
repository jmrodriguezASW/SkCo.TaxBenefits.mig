package TBPKT_INFORMATIVO.TBPKT_EXTRACTOS;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import java.util.*;
import java.text.DateFormat;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

/**
 * TBPKT_EXTRACTOS.APLICATIVO_CARGA_EXTRACTOS_AS400
 * <P>
 * @author Herbert
 */
public class APLICATIVO_CARGA_EXTRACTOS_AS400 {
private Date currentDate = new Date();
private Date currentDate1;
private DateFormat currentDateFormat;



  /**
   * main
   * @param args
   */


    public APLICATIVO_CARGA_EXTRACTOS_AS400()
    {
    int ano=2999;
    int mes=12;
    String p = currentDate.toString();
    currentDateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.PRC);
    String d = currentDateFormat.format(currentDate);
    ano = Integer.parseInt(d.substring(0,4));
    mes = Integer.parseInt(d.substring(5,d.lastIndexOf("-")))-1;
    if (mes == 0)
      ano = Integer.parseInt(d.substring(0,4))-1;
    System.out.println("Procesando....\t"+p);
    Cargar_AS400(ano, mes);
    currentDate1 = new Date();
    p = currentDate1.toString();
    shutDown();
    System.out.println("Proceso finalizado\t"+p);
    }

  public static void main(String[] args) {
   new APLICATIVO_CARGA_EXTRACTOS_AS400();
  }

  //Conexión a la base de datos
  public  void RealizarConexion()
  {
  
  }

   public void Cargar_AS400(int ano, int mes)
   {
       Connection t_tax  =   null;
       CallableStatement clsm = null;
       try
          {
          t_tax   =   DataSourceWrapper.getInstance().getConnection();
          clsm = t_tax.prepareCall("{call TB_INTERFAZ_EXTRACTOS_AS400(?,?)}");
          clsm.setInt(1,ano);
          clsm.setInt(2,mes);
          clsm.execute();
          clsm.close();
          }
       catch ( SQLException sqlex )
         {
              System.err.println( "Conexion no se realizo con exito" );
              sqlex.printStackTrace();
         }
       catch (Exception e){System.out.println("Error "+e);}
       finally{
           try{
               DataSourceWrapper.closeStatement(clsm);
               DataSourceWrapper.closeConnection(t_tax);               
           }catch(SQLException sqlEx){
               System.err.println(sqlEx.toString());
           }
       }
   }



   //Desconexión a la base de datos
   public void shutDown()
   {
   }

}

