package TBPKT_UTILIDADES.TBPKT_SEGURIDAD;
import oracle.jdbc.driver.*;
import oracle.sql.*;
import java.sql.*;
import java.io.*;
import oracle.jdbc.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

/**
 * TBCL_Seguridad.
 * <P>
 * @author ALFAGL
 */

public class TBCL_Seguridad extends Object {

  /**
   * Constructor
   */
  public TBCL_Seguridad() {
  }

  /**
   * Chequea que la seguridad en la conexi&oacute;n desde Pipeline a Tax
   * @param encriptada Cadena encriptada que viene de Pipeline.
   * @param salida Variable de salida por pantalla.
   * @param ip_taxtipo Dirección IP del equipo que hace la solicitud.
   * @return parametros: Arreglo de cadenas con: "contrato"-"producto"-"usuario"-"unidad_o"-"tipo_usuario"-"idworker".
   */
	public static String[] TBFL_Seguridad(String encriptada,PrintWriter salida,String ip_tax){
		String v_parametros[] =  new String[8];
		//STBCL_GenerarBaseHTMLII plantilla = new STBCL_GenerarBaseHTMLII;
                
		String ip_pipeline          = " ";
		String url = "Onclick=history.go(-1);";
    String cadena = "";
    String carStr;
      Connection c  =   null;

    if (encriptada != null && !encriptada.equals("")){
			try
			{
                             for (int con= 0; con < encriptada.length(); con+=3){
                                carStr = encriptada.substring(con,con+3);
                                char car = (char) Integer.parseInt(carStr);
                                cadena +=  car;
                              }
			 c                =   DataSourceWrapper.getInstance().getConnection();
			 String cadena_traducida     = " ";
			 String contrato             = " ";
			 String producto             = " ";
			 String usuario              = " ";
			 String unidad_o             = " ";
			 String tipo_usuario         = " ";
			 String idworker             = " ";
			 CallableStatement t_cst8i_0 = c.prepareCall("{ call TBPDB_DESENCRIPTA(?,?,?,?,?,?,?,?,?) }");
			 t_cst8i_0.registerOutParameter(2,Types.VARCHAR);
			 t_cst8i_0.registerOutParameter(3,Types.VARCHAR);
			 t_cst8i_0.registerOutParameter(4,Types.VARCHAR);
			 t_cst8i_0.registerOutParameter(5,Types.VARCHAR);
			 t_cst8i_0.registerOutParameter(6,Types.VARCHAR);
			 t_cst8i_0.registerOutParameter(7,Types.VARCHAR);
			 t_cst8i_0.registerOutParameter(8,Types.VARCHAR);
			 t_cst8i_0.registerOutParameter(9,Types.VARCHAR);
			 t_cst8i_0.setString(1,cadena);
			 t_cst8i_0.execute();
			 cadena_traducida  = t_cst8i_0.getString(2);
			 ip_pipeline       = t_cst8i_0.getString(3);
			 contrato          = t_cst8i_0.getString(4);
			 producto          = t_cst8i_0.getString(5);
			 usuario           = t_cst8i_0.getString(6);
			 unidad_o          = t_cst8i_0.getString(7);
			 tipo_usuario      = t_cst8i_0.getString(8);
			 idworker          = t_cst8i_0.getString(9);
			 t_cst8i_0.close();
			 /*if(!ip_pipeline.equals(ip_tax))
			 {
			  v_parametros[0] = "contrato";          v_parametros[1] = "producto";
			  v_parametros[2] = "usuario";           v_parametros[3] = "unidad_o";
			  v_parametros[4] = "tipo_usuario";      v_parametros[5] = "idworker";
			  salida.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("MODULO DE SEGURIDAD","PAGINA DE RESPUESTA","","",false));
	      salida.println("<b> Cadena: "+ cadena_traducida+"</b>");
			  salida.println("<CENTER><b><FONT color=blue face=verdana size=1>ACCESO DENEGADO</font></b></CENTER>");
			  salida.println("<br><br>");
			  salida.println("<CENTER><b><FONT color=blue face=verdana size=1>IP PIPELINE:  "+ip_pipeline+"</font></b></CENTER>");
			  salida.println("<CENTER><b><FONT color=blue face=verdana size=1>IP TAX:  "+ip_tax+"</font></b></CENTER>");
			  salida.println("<center><input type='button' value='Aceptar' "+url+" ></center>");
			  salida.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
			  salida.close();
			  return v_parametros;
			 }*/
			v_parametros[0] = contrato;          v_parametros[1] = producto;
			v_parametros[2] = usuario;           v_parametros[3] = unidad_o;
			v_parametros[4] = tipo_usuario;      v_parametros[5] = idworker;
	    v_parametros[6] = ip_pipeline;
			return v_parametros;
			}
			catch(Exception ex)
			{v_parametros[0] = "contrato";          v_parametros[1] = "producto";
			v_parametros[2] = "usuario";           v_parametros[3] = "unidad_o";
			v_parametros[4] = "tipo_usuario";      v_parametros[5] = "idworker";
	    v_parametros[6] = "fallo";
			salida.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("MODULO DE SEGURIDAD","PAGINA DE RESPUESTA","","",false));
	    salida.println("<b> Excepcion: "+ ex +"</b>");
                        salida.println("<span class=\"error\">EXCEPCION: "+ex+"</span>");
			salida.println("<br><br>");
			salida.println("<center><input type='button' value='Aceptar' "+url+" ></center>");
			salida.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
			salida.close();
			return v_parametros;}
		    finally{
		                    try{
		                            DataSourceWrapper.closeConnection(c);                  
		                    }catch(SQLException sqlEx){
		                            System.out.println(sqlEx.toString());
		                    }
		            }
		}
    else {
			v_parametros[0] = "contrato";          v_parametros[1] = "producto";
			v_parametros[2] = "usuario";           v_parametros[3] = "unidad_o";
			v_parametros[4] = "tipo_usuario";      v_parametros[5] = "idworker";
	    v_parametros[6] = "fallo";
			salida.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("MODULO DE SEGURIDAD","PAGINA DE RESPUESTA","","",false));
        salida.println("<span class=\"error\">Error en el proceso que llama la función de seguridad (Verifique que se este enviando la cadena encriptada)</span>");
			salida.println("<br><br>");
			salida.println("<center><input type='button' value='Aceptar' "+url+" ></center>");
			salida.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
			salida.close();
			return v_parametros;
    }
  }

}

