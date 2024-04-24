
// Copyright (c) 2000 skandia
package TBPKT_UTILIDADES.TBPKT_FECHAS;

import java.io.*;
import java.util.*;
/**Clase que cambia las fecha de formato YYYY-MM-DD al formato YYYYMMDD para enviar al AS400.*/
public class TBCL_Fecha extends Object
{
 /**Procedimiento publico que cambia formato de fechas.*/
 public  String TBFL_Fecha(String v_fecha_mfund)
 {
  String v_ano="";//Variable año de la fecha.
  String v_mes="";//Variable mes de la fecha.
  String v_dia="";//Variable día de la Fecha
  String v_fecha="";//Variable con formato fecha a retornar.
  v_ano=v_fecha_mfund.substring(0,4);
  v_mes=v_fecha_mfund.substring(5,7);
  v_dia=v_fecha_mfund.substring(8,10);
  v_fecha+=v_ano;v_fecha+=v_mes;
  v_fecha+=v_dia;
  return v_fecha;
                                                         }

}

 