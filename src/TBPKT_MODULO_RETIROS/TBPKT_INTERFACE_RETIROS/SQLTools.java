package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

/**
 * SQLTools
 * Clase con los metodos para manejo de procedimientos almacenados.
 * @date 23/07/07      
 * @author: Marcela Ortiz Sandoval nortiz@skandia.com.co
 */
public class SQLTools 
{
   /**
   * buildProcedureCall
   * Construye un llamado un prodedimiento almacenado 
   * @param String packageName Conexión a la base de datos
   * @param String procedureName Conexión a la base de datos
   * @param int paramCount Conexión a la base de datos
   * @return String 
   */
  public static String buildProcedureCall(String packageName, String procedureName, int paramCount) {
      StringBuffer sb = null;
      if (packageName == null){
          sb = new StringBuffer("{call "+procedureName+"(");        
      }
      else {
        sb = new StringBuffer("{call "+packageName+"."+procedureName+"(");
      }
      for (int n = 1; n <= paramCount; n++) {
          sb.append("?");
          if (n < paramCount) sb.append(",");
      }
      return sb.append(")}").toString();
  }

}