package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

/**
 * TBC_PASO10
 * Clase para ejecutar .
 * <P>
 * @author skandia
 */
public class TBC_PASO10  extends Object {

  public static void main(String[] args) {
  
    SQL_PASO10 isql_saldos = new SQL_PASO10();
     isql_saldos.TBP_PASO10();
    //isql_saldos.reportarEstadoPaso("M", 20131109, "Y");
    
  }
}