/*@lineinfo:filename=TBCL_Banco*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD;

import sqlj.runtime.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/*
Modificacion:
Se elimina el import debido a que la conexion
al AS400 se hace desde la base de datos
*/
//import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;

import TBPKT_IAS.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_FECHAS.*;
import sqlj.runtime.ref.DefaultContext ;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;


/*Clase para modificar codigo de banco y numero de cuenta,
*presenta al usuario la informaci�n actual y da lo opcion de modificarlo.*/
//public class TBCL_Banco extends Object
public class TBCL_Banco extends HttpServlet
{
 /**Iterator c�digo del banco y numero de cuenta del retiro a modificar.*/
 /*@lineinfo:generated-code*//*@lineinfo:29^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

class i_banco
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_banco(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    RET_BANCONdx = findColumn("RET_BANCO");
    RET_CUENTANdx = findColumn("RET_CUENTA");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String RET_BANCO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(RET_BANCONdx);
  }
  private int RET_BANCONdx;
  public String RET_CUENTA()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(RET_CUENTANdx);
  }
  private int RET_CUENTANdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:29^61*/
 /**Variable tipo iterator i_banco*/
 i_banco v_bancocon;

 /**Procedimiento local que muestra al usuario el codigo  del banco y numero de cuenta que tiene el retiro@
 * y dar la opci�n de modificarlos segun las nuevas cuentas registradas en el AS400. */
 public void TBPL_ModificarBanco(PrintWriter out,HttpSession session,HttpServletRequest request,String nuevaCadena )
 {
  /**Instancias de clase*/
  STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();/**Instancia de la clase TBCL_GenerarBaseHTML*/

  //TBCL_FuncionesAs400   i_fondos = new TBCL_FuncionesAs400();/**Instancia de la clase TBCL_FuncionesAs400*/


  TBCL_Validacion       i_valusu = new TBCL_Validacion ();/**Instancia de la clase TBCL_Validacion*/
  //TBCL_ConexionSqlj    i_conexion = new TBCL_ConexionSqlj();
  try
  {
   /**Leer de archivo connection.properties url,usuario y paswword a la base de datos.*/
   String[] v_valusu = new String[3];
   v_valusu=i_valusu.TBFL_ValidarUsuario();
   /**Realizar conexion a la base de datos*/
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   Connection t_tax =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
   DefaultContext ctx2 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
   DefaultContext.setDefaultContext(ctx2);
   //i_conexion.TBCL_ConexionBaseDatos();
   /**Definicion de variables*/
   /**Tipo boolean*/
   boolean v_encontro       = false;/**Variable indicador cuando se encuentran datos*/
   boolean v_infbanco       = true;/**Variable indicador si se encuentran cuentas bancarias*/

   /**Tipo String*/
   String v_banco            = "";/**Variable c�digo del banco*/
   String v_cuenta           = "";/**Variable n�mero de cuenta*/
   String v_cuenta_bancaria  = "";/**Variable que toma cadena con codigos y numeros de cuenta para el contrato*/
   String v_sistema          = "";/**Variable nombre del sistema multifund*/
   String v_usumfund         = "";/**Variable usuario multifund*/
   String v_passmfund        = "";/**Variable password de usuario multifund*/
   String v_libreria         = "";/**Variable nombre de libreria multifund*/
   String v_banconulo        = " ";/**Variable c�digo del banco*/
   String v_cuentanulo       = " ";/**Variable cuenta del banco*/
   String v_nombannulo       = " ";/**Variable nombre banco*/
   String v_ahocor           = " ";/**Variable indicador de ahorro y corriente*/
   String v_contra           = "";/**Variable numero del contrato*/
   String v_pro              = "";/**Variable c�digo del producto*/
   String v_nombre           = "";/**Variable nombre del afiliado*/
   String v_apellidos        = "";/**Variable apellidos del afiliado*/
   String v_consecutivo      = "";/**Variable consecutivo del retiro a modificar*/
   String v_pintar           = "";/**Variable dibujar inicio pagina*/
   String v_pie              = "";/**Variable dibujar final pagina */
   /**Tipo int*/
   int v_dim                 = 0;/**Variable contador*/
   int v_resmulti            = 0;/**Variable indicador de exito o fracaso de funcion TB_FREFERENCIAS_MULTI*/
   int v_consecutivo2        = 0;/**Variable consecutivo  n�merico*/

   /**Verificar que las variables de session no expiren*/
   if((java.lang.String)session.getAttribute("s_contrato")!= null ||(java.lang.String)session.getAttribute("s_producto") != null)
   {
    /**Capturar variables de session*/
    v_contra          = (java.lang.String)session.getAttribute("s_contrato"); //se toma contrato
    v_pro             = (java.lang.String)session.getAttribute("s_producto");//se toma producto
    v_nombre          =(java.lang.String)session.getAttribute("s_nombres");//se toma nombre del afiliado
    v_apellidos       =(java.lang.String)session.getAttribute("s_apellidos");//apellidos del afiliado
    v_consecutivo     = (java.lang.String)session.getAttribute("s_conret");//consecutivo del retiro
    v_consecutivo2    = new Integer(v_consecutivo).intValue();//se pasa consecutivo a n�merico

    /**Capturar variables de session*/
    /*@lineinfo:generated-code*//*@lineinfo:97^5*/

//  ************************************************************
//  #sql v_bancocon = { SELECT RET_BANCO,RET_CUENTA
//                            FROM TBRETIROS
//                           WHERE RET_CON_PRO_CODIGO = :v_pro
//                             AND RET_CON_NUMERO     = :v_contra
//                             AND RET_CONSECUTIVO    = :v_consecutivo2
//                         };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT RET_BANCO,RET_CUENTA\n                          FROM TBRETIROS\n                         WHERE RET_CON_PRO_CODIGO =  :1  \n                           AND RET_CON_NUMERO     =  :2  \n                           AND RET_CONSECUTIVO    =  :3 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_Banco",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_pro);
   __sJT_st.setString(2,v_contra);
   __sJT_st.setInt(3,v_consecutivo2);
   // execute query
   v_bancocon = new TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_Banco.i_banco(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"0TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_Banco",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:102^23*/

    /** Si no hubo error al consultar*/
    while (v_bancocon.next())
    {
     v_banco= v_bancocon.RET_BANCO();
     v_cuenta= v_bancocon.RET_CUENTA();
     v_encontro = true;
    }
    v_bancocon.close();

    /**Si el dato de c�digo del banco es null se asigna blanco*/
    try
    {
     if(v_banco == null)
     {
       v_banco = "       ";
     }
    }
    catch(Exception ex)
    {
     v_banco = "       ";
    }
    /**Si el dato de numero de cuenta es null se asigna blanco*/
    try
    {
     if(v_cuenta == null)
     {
      v_cuenta = "        ";
     }
    }
    catch(Exception ex)
    {
     v_cuenta = "        ";
    }
    /**Variables de session cuenta y banco anteriores.*/
    session.removeAttribute("s_bancoviejo");
    session.setAttribute("s_bancoviejo",(java.lang.Object)v_banco);
    session.removeAttribute("s_cuentaviejo");
    session.setAttribute("s_cuentaviejo",(java.lang.Object)v_cuenta);
    /**Si se encuentran datos*/
    if (v_encontro)
    {
     /** Consultar los parametros de conexion para el AS400*/
     /*@lineinfo:generated-code*//*@lineinfo:146^6*/

//  ************************************************************
//  #sql v_resmulti = { values(TB_FREFERENCIAS_MULTI(:v_sistema,:v_usumfund,:v_passmfund, :v_libreria)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FREFERENCIAS_MULTI( :2  , :3  , :4  ,  :5  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_Banco",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_sistema);
   __sJT_st.setString(3,v_usumfund);
   __sJT_st.setString(4,v_passmfund);
   __sJT_st.setString(5,v_libreria);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_resmulti = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_sistema = (String)__sJT_st.getString(2);
   v_usumfund = (String)__sJT_st.getString(3);
   v_passmfund = (String)__sJT_st.getString(4);
   v_libreria = (String)__sJT_st.getString(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:146^127*/
     /**Consultar al as400 los c�digos de banco y n�meros de cuenta, devuelve una cadena*/
     //v_cuenta_bancaria = i_fondos.TBFL_CuentasBancarias(v_contra,v_sistema,v_usumfund,v_passmfund,v_libreria);

     /*
     Modificacion:
     Se a�ade el procedimiento de invocacion a un procedimiento del AS400
     */

     CallableStatement cs = t_tax.prepareCall ( "{? = call TBCL_FUNCIONESAS400.TBFL_CuentasBancarias(?,?,?,?,?)}");
     cs.registerOutParameter(1,Types.CHAR);
     cs.setString(2,v_contra);
     cs.setString(3,v_sistema);
     cs.setString(4,v_usumfund);
     cs.setString(5,v_passmfund);
     cs.setString(6,v_libreria);
     cs.executeUpdate();
     v_cuenta_bancaria = cs.getString(1);
     cs.close();
     t_tax.close();

     /* Final de la modificacion */

     /**Si el contrato no tiene cuentas registradas*/
     if(v_cuenta_bancaria.trim().equals(""))
     {
      v_infbanco = false;
     }
     /**Si no hay al consultar en el as400*/
     if(!v_cuenta_bancaria.substring(0,5).equals("Error"))
     {//
      v_dim = 20;
      String[] v_cuenta_bancaria2= UtilitiesForTax.TBCL_CapturarCadena(v_cuenta_bancaria,v_dim);
      /**Dibujar p�gina de respuesta*/
      v_pintar=    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiro","Modificar C�digo de Banco y N�mero de cuenta","TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBS_ActualizarBanco","",true,"","");
      out.println(""+v_pintar+"");
      String v_contrato_unif = "";
      /*@lineinfo:generated-code*//*@lineinfo:183^7*/

//  ************************************************************
//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_pro,:v_contra)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD.TBCL_Banco",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_pro);
   __sJT_st.setString(3,v_contra);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_contrato_unif = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:183^80*/
      out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
      out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nombre+"  <b> Apellidos </b>"+v_apellidos+" </CENTER></font>");
      out.println("<br>");
      out.println("<pre>");
      out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=2><b>C�digo del banco  "+v_banco+" y n�mero de cuenta "+v_cuenta+" .<b></font>");
      out.println("<br>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Modificar banco y cuenta</b>     <select name=v_seleccion>");
      out.println("               <option VALUE ='     '>                    ");
      if(v_infbanco)
      {
       for(int i=0,f=0;i<v_cuenta_bancaria2.length;i=i+4)
       {
        if(v_cuenta_bancaria2[f] != null)
        {
         //Si consulta no devuelve datos se coloca en pagina confirmar
         if(v_cuenta_bancaria2[i]== null || v_cuenta_bancaria2[i].trim().equals(""))
         {
          v_banconulo = "<b>Confirmar</b>";
         }
         else
         {
          v_banconulo = v_cuenta_bancaria2[i];
         }
         if(v_cuenta_bancaria2[i+1]== null || v_cuenta_bancaria2[i+1].trim().equals(""))
         {
          v_cuentanulo = "<b>Confirmar</b>";
         }
         else
         {
          v_cuentanulo = v_cuenta_bancaria2[i+1];
         }
         if(v_cuenta_bancaria2[i+2]== null || v_cuenta_bancaria2[i+2].trim().equals(""))
         {
          v_nombannulo = "<b>Confirmar</b>";
         }
         else
         {
          v_nombannulo = v_cuenta_bancaria2[i+2];
         }
         if(v_cuenta_bancaria2[i+3]== null || v_cuenta_bancaria2[i+3].trim().equals(""))
         {
          v_ahocor = "<b>Confirmar</b>";
         }
         else
         {
          v_ahocor =  v_cuenta_bancaria2[i+3];
         }
         out.println("               <option VALUE ='"+v_banconulo+v_cuentanulo +"'>"+v_cuentanulo+","+v_nombannulo+"("+v_banconulo+"),"+v_ahocor+"");
         f=f+4;
        }
       }
      } 
      out.println("</OPTION></SELECT></FONT>");
      out.println("<br>");
      out.println("</pre>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
      out.println("<center><input type='submit' value='Aceptar'> <input type='Button' value='Regresar' onclick='history.go(-1)'><input type='Button' value='Cancelar' onclick='history.go(-2)'></center>");
      v_pie = i_pagina.TBFL_PIE;
      out.println("<br>");
      out.println(""+v_pie+"");
      out.close();
     }
     else
     {/**Error banco*/
      v_pintar=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Error en Modificar C�digo de Banco y N�mero de cuenta","","<center>Consulta de c�digo banco y n�mero cuenta. Mensaje de error:"+v_banco+"</center>",false);
      out.println(""+v_pintar+"");
      out.println("<BR>");
      out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
      v_pie = i_pagina.TBFL_PIE;
      out.println("<br>");
      out.println("<br>");
      out.println(""+v_pie+"");
      out.close();
     }
    }
    else
    {/**Si no se encontraron datos*/
     v_pintar=    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiro","Error en Modificar C�digo de Banco y N�mero de cuenta","","<center>El n�mero de solicitud de retiro "+v_consecutivo+" no existe.</center>",false);
     out.println(""+v_pintar+"");
     v_pie = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
     out.println(""+v_pie+"");
     out.close();
    }
   }
   else
   {/**Termina session*/
    v_pintar=    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiro","Error en Modificar C�digo de Banco y N�mero de cuenta","","<center>Error de comunicaci�n no se tiene conexi�n con el servidor web,por favor intente de nuevo.</center>",false);
    out.println(""+v_pintar+"");
    v_pie = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'></center>");
    out.println(""+v_pie+"");
    out.close();
   }
   /**Cerrar conexi�n*/
   ctx2.getConnection().close();
   //i_conexion.TBCL_DesconectarBaseDatos();
  }
  catch(Exception ex)
  {/**Manejo de error*/
   String v_menex = "";
      String error = ex.toString();
   if(ex.equals("java.sql.SQLException: found null connection context"))
   {
    v_menex = "Error de comunicaci�n no se tiene conexi�n con el servidor web,por favor intente de nuevo.";
   }
   else if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
      {
        v_menex = "No se tiene comunicaci�n con el servidor de datos  por favor ingrese nuevamente.";
      }
      else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
           {
             v_menex = "Se reinicio la base de datos por favor ingrese nuevamente.";
           }
            else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_menex = "Error momentaneo de comunicaci�n con el servidor de datos, por favor intente entrar de nuevo a la opci�n.";
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_menex =  "Error momentaneo de comunicaci�n con el servidor Web, por favor intente entrar de nuevo a la opci�n.";
                     }
                     else
                     {
                       v_menex = "Mensaje de error: "+ex;
                     }
   String v_pintar=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Error en Modificar C�digo de Banco y N�mero de cuenta","","<center>"+v_menex+"</center>",false);
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