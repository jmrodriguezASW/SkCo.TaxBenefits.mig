
// Copyright (c) 2000 skandia
package TBPKT_MODULO_APORTES;
import TBPKT_MODULO_APORTES.*;



public class TBC_APORTES_IR extends Object {


  public static void main(String[] args) {
   /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Inicio de la clase TBCBD_INGRESO_REVERSION_APORTES, no es necesaria la instancia nueva*/ 
   //TBCBD_INGRESO_REVERSION_APORTES i_aporte = new TBCBD_INGRESO_REVERSION_APORTES ();
   //String v_mensaje = i_aporte.TBFL_Inicio();
   String v_mensaje = TBCBD_INGRESO_REVERSION_APORTES.TBFL_Inicio();
   System.out.println("Mensaje: "+v_mensaje);
  }
}