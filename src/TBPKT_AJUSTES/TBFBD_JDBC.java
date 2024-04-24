package TBPKT_AJUSTES;
import oracle.jdbc.driver.*;
import java.sql.*;


/**
*  El objetivo de esta clase es realizar operaciones con la Base de Datos, y
*  que además no se puedan realizar en SQLJ
*/

public class TBFBD_JDBC extends Object {
//////////////////////////////////////////constructor//////////////////////////////////////
  public TBFBD_JDBC(){
  }
/////////////////////////////devuelve el consecutivo de la tabla ajustes//////////////////  
  public static String TBFBD_nextConsecAjus(){
    String consec = new String("0");
    try
    {
      Connection c = new OracleDriver().defaultConnection();
      String v_qry = "SELECT AJU_CONSECUTIVO_SQ.NEXTVAL FROM DUAL";
      PreparedStatement prepConsec = c.prepareStatement(v_qry);
      ResultSet resConsec=prepConsec.executeQuery();
      if(resConsec.next())
      {
        consec = Integer.toString(resConsec.getInt(1));
      }
      resConsec.close();
      prepConsec.close();
    }
    catch(Exception e)
    {
      consec = "Exception en nextConsecAjus "+e;
    }
    return consec;
  }
  
    public static String TBFBD_nextConsecAjusConex(Connection c){
    String consec = new String("0");
    try
    {
      //Connection c = new OracleDriver().defaultConnection();
      String v_qry = "SELECT AJU_CONSECUTIVO_SQ.NEXTVAL FROM DUAL";
      PreparedStatement prepConsec = c.prepareStatement(v_qry);
      ResultSet resConsec=prepConsec.executeQuery();
      if(resConsec.next())
      {
        consec = Integer.toString(resConsec.getInt(1));
      }
      resConsec.close();
      prepConsec.close();
    }
    catch(Exception e)
    {
      consec = "Exception en nextConsecAjus "+e;
    }
    return consec;
  }
///////Me devuelve la fecha mínima y máxima de los retiros de un producto-contrato/////////
/*  public static String TBFBD_yearRetMinMax(String cod_producto,
                                           String num_contrato){
    String years=new String("");
    try{
      Connection c = new OracleDriver().defaultConnection();
      String v_qry="SELECT MIN(RET_FECHA_EFECTIVA),MAX(RET_FECHA_EFECTIVA) "+
                      "FROM TBRETIROS"+
                   " WHERE RET_CON_PRO_CODIGO = ? AND RET_CON_NUMERO=?";
      PreparedStatement prepYear=c.prepareStatement(v_qry);
      prepYear.setString(1,cod_producto);
      prepYear.setString(2,num_contrato);
      ResultSet resYear=prepYear.executeQuery();
      while(resYear.next()){
        years="yearmin='"+resYear.getDate(1).toString().substring(0,4)+"' yearmax='"+resYear.getDate(2).toString().substring(0,4)+"'";
      }
      resYear.close();
      prepYear.close();
    }catch(Exception e){
      years="Exception en TBPBD_yearRetMinMax "+e;
    }
    return years;
  }*/
}

