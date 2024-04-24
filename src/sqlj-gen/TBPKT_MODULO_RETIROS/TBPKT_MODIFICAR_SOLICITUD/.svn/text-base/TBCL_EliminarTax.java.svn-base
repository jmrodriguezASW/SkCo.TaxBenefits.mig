/*@lineinfo:filename=TBCL_EliminarTax*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD;

import sqlj.runtime.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import sqlj.runtime.ref.DefaultContext ;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;


/**Clase que hace llamado la función almacenada TB_FACTUALIZAR_ESTADO, que se encarga de borra@
* la relación aportes_retiros, cargos y devuelve los saldos a los aportes afectados. */
public class TBCL_EliminarTax extends HttpServlet
{
 /**Itrator de valor de unidad del retiro*/
 /*@lineinfo:generated-code*//*@lineinfo:18^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

class i_valor
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_valor(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    RET_VALOR_UNIDADNdx = findColumn("RET_VALOR_UNIDAD");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double RET_VALOR_UNIDAD()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(RET_VALOR_UNIDADNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int RET_VALOR_UNIDADNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:18^48*/
 /**Variable tipo iterator i_valor*/
 i_valor v_valor;
 /**Procedimineto local que relimina solicitud de retiro antes de ser procesado en la interfaz nocturna.*/
 public void TBPL_EliminarTax(PrintWriter out,HttpSession session,HttpServletRequest request)
 {
  /**Instancias de clase*/
  STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();/**Instancia de la clase TBCL_GenerarBaseHTML*/
  //TBCL_ConexionSqlj    i_conexion = new TBCL_ConexionSqlj();/**Instancia de la clase TBCL_ConexionSqlj*/
  TBCL_Validacion  i_valusu = new     TBCL_Validacion ();

  try
  {
   String[] v_valusu = new String[3];
   v_valusu=i_valusu.TBFL_ValidarUsuario();
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   DefaultContext ctx5 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
   DefaultContext.setDefaultContext(ctx5);
   /**Realizar conexión a la base de datos*/
   //i_conexion.TBCL_ConexionBaseDatos();

   /**Definicion de variables*/
   /**Tipo boolean*/
   boolean v_encontro   = false;
   /**Tipo double*/
   double v_valuni      = 0;
   /**Tipo String*/
   String v_mensaje     = "";
   String v_contra      = "";
   String v_pro         = "";
   String v_consecutivo = "";
   String v_usuario     = "";
   String v_pintar      = "";
   String v_pie         = "";
   /**Tipo int*/
   int v_consecutivo2   = 0;
   int indicador        = 0;
   /**Verificar que las variables de session no expiren*/
   if((java.lang.String)session.getValue("s_contrato")!= null ||(java.lang.String)session.getValue("s_producto") != null)
   {
    /**Capturar variables de session*/
    v_contra =(java.lang.String)session.getValue("s_contrato");/**Tomar contrato*/
    v_pro = (java.lang.String)session.getValue("s_producto");/**Tomar producto*/
    v_consecutivo = (java.lang.String)session.getValue("s_conret");/**Tomar consecutivo del retiro a eliminar*/
    v_usuario  = (java.lang.String)session.getValue("s_usuariopipe");/**Tomar nombre de usuario*/
    v_consecutivo2 = new Integer(v_consecutivo).intValue();/**Consecutivo numerico*/

    /**Consultar valor de unidad del retiro*/
    /*@lineinfo:generated-code*//*@lineinfo:66^5*/

//  ************************************************************
//  #sql v_valor = { SELECT RET_VALOR_UNIDAD
//                        FROM tbretiros
//                       WHERE RET_CON_PRO_CODIGO = :v_pro
//                         AND RET_CON_NUMERO     = :v_contra
//                         AND RET_CONSECUTIVO    = :v_consecutivo2
//  
//                       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT RET_VALOR_UNIDAD\n                      FROM tbretiros\n                     WHERE RET_CON_PRO_CODIGO =  :1  \n                       AND RET_CON_NUMERO     =  :2  \n                       AND RET_CONSECUTIVO    =  :3 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_EliminarTax",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_pro);
   __sJT_st.setString(2,v_contra);
   __sJT_st.setInt(3,v_consecutivo2);
   // execute query
   v_valor = new TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_EliminarTax.i_valor(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"0TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_EliminarTax",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:72^21*/

    /**Mientras se encuentren datos*/
    while (v_valor.next())
    {
     v_valuni = v_valor.RET_VALOR_UNIDAD();
     v_encontro = true;
    }
    v_valor.close();

    if (v_encontro)
    {//1
     /**Llamar función que reversa, elimina relacion aportexretiro y actualiza estado*/
     /*@lineinfo:generated-code*//*@lineinfo:85^6*/

//  ************************************************************
//  #sql indicador = { values (TB_FACTUALIZAR_ESTADO(:v_pro, :v_contra,:v_consecutivo2,:v_valuni,'SER002' ,'Se elimino la Solicitud de Retiro antes de ser enviada a Multifund',:v_usuario,:v_mensaje)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FACTUALIZAR_ESTADO( :2  ,  :3  , :4  , :5  ,'SER002' ,'Se elimino la Solicitud de Retiro antes de ser enviada a Multifund', :6  , :7  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_EliminarTax",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_pro);
   __sJT_st.setString(3,v_contra);
   __sJT_st.setInt(4,v_consecutivo2);
   __sJT_st.setDouble(5,v_valuni);
   __sJT_st.setString(6,v_usuario);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   indicador = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_mensaje = (String)__sJT_st.getString(7);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:85^205*/
     /**Si no hubo error*/
     if (indicador == 0)
     {//1
      /*@lineinfo:generated-code*//*@lineinfo:89^7*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:89^19*/
      /**Dibujar página de respuesta*/
      v_pintar=    i_pagina.TBFL_CABEZA("Eliminar Solicitud de Retiro","Eliminar Solicitud de Retiro","","<center>"+v_mensaje+"</center>",false);
      out.println(""+v_pintar+"");
      v_pie = i_pagina.TBFL_PIE;
      out.println("<br>");
      out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
      out.println(""+v_pie+"");
      out.close();
     }
     else
     {/**Si hubo error*/
      /*@lineinfo:generated-code*//*@lineinfo:101^7*/

//  ************************************************************
//  #sql { ROLLBACK };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:101^21*/
      v_pintar=    i_pagina.TBFL_CABEZA("Eliminar Solicitud de Retiro","Error al Eliminar Solicitud de Retiro","","<center>"+v_mensaje+"</center>",false);
      out.println(""+v_pintar+"");
      v_pie = i_pagina.TBFL_PIE;
      out.println("<br>");
      out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
      out.println(""+v_pie+"");
      out.close();
     }
    }
    else
    {/**No se encontraron datos para el número del retiro*/
     /*@lineinfo:generated-code*//*@lineinfo:113^6*/

//  ************************************************************
//  #sql { ROLLBACK };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:113^20*/
     v_pintar=    i_pagina.TBFL_CABEZA("Eliminar Solicitud de Retiro","Error al Eliminar Solicitud de Retiro","","<center>Solicitud de Retiro número "+v_consecutivo+" no se encontro en el sistema.</center>",false);
     out.println(""+v_pintar+"");
     v_pie = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
     out.println(""+v_pie+"");
     out.close();
    }
   }
   else
   {/**Se termino session*/
    v_pintar=    i_pagina.TBFL_CABEZA("Eliminar Solicitud de Retiro","Error al Eliminar Solicitud de Retiro","","<center>Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.</center>",false);
    out.println(""+v_pintar+"");
    v_pie = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'></center>");
    out.println(""+v_pie+"");
    out.close();
   }
   /**Desconectar base de datos*/
   //i_conexion.TBCL_DesconectarBaseDatos();
   ctx5.getConnection().close();
  }
  catch(Exception ex)
  {/**Mnejo de error*/
   String v_menex = "";
   String error = ex.toString();
   if(ex.equals("java.sql.SQLException: found null connection context"))
   {
    v_menex = "Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.";
   }
   else if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
      {
        v_menex = "No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.";
      }
      else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
           {
             v_menex = "Se reinicio la base de datos por favor ingrese nuevamente.";
           }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_menex = "Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.";
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_menex =  "Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.";
                     }

                     else
                     {
                       v_menex = "Mensaje de error: "+ex;
                     }
   String v_pintar=    i_pagina.TBFL_CABEZA ("Eliminar Solicitud de Retiro","Error al Eliminar Solicitud de retiro","","<center>"+v_menex+"</center>",false);
   out.println(""+v_pintar+"");
   out.println("<BR>");
   out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
   String v_pie = i_pagina.TBFL_PIE;
   out.println("<br>");
   out.println("<br>");
   out.println(""+v_pie+"");
   out.close();
  }
 }
}/*@lineinfo:generated-code*/