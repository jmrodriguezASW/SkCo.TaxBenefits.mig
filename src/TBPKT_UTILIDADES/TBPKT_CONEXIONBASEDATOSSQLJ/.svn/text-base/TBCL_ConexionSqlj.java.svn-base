
// Copyright (c) 2000 skandia
package TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOSSQLJ;


import sqlj.runtime.*;
import javax.servlet.*;
import java.io.*;
import javax.servlet.http.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import java.sql.*;
import sqlj.runtime.ref.DefaultContext ;


public class TBCL_ConexionSqlj extends Object {
 DefaultContext ctx = null;
 public void TBCL_ConexionBaseDatos()
 {
  try
  {
   TBCL_Validacion      i_valusu = new TBCL_Validacion ();/**Instancia de la clase TBCL_Validacion*/
   /**Leer de archivo connection.properties url,usuario y paswword a la base de datos.*/
   String[] v_valusu = new String[3];
   v_valusu          =i_valusu.TBFL_ValidarUsuario();
   /**Realizar conexion a la base de datos*/
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   DefaultContext ctx = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
   DefaultContext.setDefaultContext(ctx);
  }
  catch(Exception ex)
  { //manejo de error
  }
 }
 public void TBCL_DesconectarBaseDatos()
 {
  try
  {
   ctx.getConnection().close();
  }
  catch(Exception ex)
  { //manejo de error
  }
 }
}

