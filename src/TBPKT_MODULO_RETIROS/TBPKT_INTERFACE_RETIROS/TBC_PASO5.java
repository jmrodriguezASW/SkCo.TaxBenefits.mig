
// Copyright (c) 2000 skandia
package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

  import sqlj.runtime.*;
  import sqlj.runtime.ref.*;
  import java.io.*;
  import java.sql.*;
  import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import co.oldmutual.taxbenefit.util.DataSourceWrapper;

/**
 * A Class class.
 * <P>
 * @author skandia
 */
public class TBC_PASO5 extends Object {
  public static void main(String[] args)
  {
    Connection t_tax    =   null;
    try
    {      
      t_tax =   DataSourceWrapper.getInstance().getConnection();
      
      //SQL_PASO5 isql_saldos = new SQL_PASO5();
      //isql_saldos.TBP_PASO5(1, 1);

      /*
      Modificacion:
      Se añade el procedimiento de invocacion a un procedimiento del AS400
      */
      CallableStatement cs = t_tax.prepareCall ( "{call SQL_PASO5.TBP_PASO5(?,?)}");
      cs.setInt(1,1);
      cs.setInt(2,1);
      cs.executeUpdate();
      cs.close();
      /* Final de la modificacion */
    }
    catch (Exception e)
    {
        System.out.println("Error en TBC_PASO5 ");
        try
        {

          PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("c:\\TaxBenefits\\Taxb\\Pasos_logs\\HP_error.log", true)));
          out.println("Execption  en paso5"+ e);
          e.printStackTrace(out);
          out.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
    }
      finally{
              try{
                  DataSourceWrapper.closeConnection(t_tax);                  
              }catch(SQLException sqlEx){
                  System.out.println(sqlEx.toString());
              }
          }
  }
}

