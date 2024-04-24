/*@lineinfo:filename=TBCL_RetiroFinal*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

/*
Modificacion:
Se elimina el import debido a que la conexion
al AS400 se hace desde la base de datos
import com.ibm.as400.acces.*;

import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;
*/

import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import java.text.NumberFormat;
import sqlj.runtime.ref.DefaultContext ;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import java.util.Date;



/**Clase que muestra al usuario la informaci�n de los aportes que fueron escogidos
*en el esquema de retiro.*/
public class TBCL_RetiroFinal extends HttpServlet
{//1
/**Iterator de la informaci�n de aportes.*/
/*@lineinfo:generated-code*//*@lineinfo:33^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_ejemplo3
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_ejemplo3(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    APO_CONSECUTIVONdx = findColumn("APO_CONSECUTIVO");
    APO_FECHA_APORTENdx = findColumn("APO_FECHA_APORTE");
    APO_APORTE_TRASLADONdx = findColumn("APO_APORTE_TRASLADO");
    APO_INFORMACION_TRASLADONdx = findColumn("APO_INFORMACION_TRASLADO");
    APO_SALDO_CAPITALNdx = findColumn("APO_SALDO_CAPITAL");
    APO_SALDO_CUENTA_CONTINGENTENdx = findColumn("APO_SALDO_CUENTA_CONTINGENTE");
    APO_SALDO_NUMERO_UNIDADESNdx = findColumn("APO_SALDO_NUMERO_UNIDADES");
    APO_RENDIMIENTOS_PENALIZADOSNdx = findColumn("APO_RENDIMIENTOS_PENALIZADOS");
    APO_PORCENTAJE_RENDIMIENTOS_HNdx = findColumn("APO_PORCENTAJE_RENDIMIENTOS_H");
    APO_CAPITALNdx = findColumn("APO_CAPITAL");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double APO_CONSECUTIVO()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_CONSECUTIVONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_CONSECUTIVONdx;
  public java.sql.Date APO_FECHA_APORTE()
    throws java.sql.SQLException
  {
    return (java.sql.Date)m_rs.getDate(APO_FECHA_APORTENdx);
  }
  private int APO_FECHA_APORTENdx;
  public String APO_APORTE_TRASLADO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(APO_APORTE_TRASLADONdx);
  }
  private int APO_APORTE_TRASLADONdx;
  public String APO_INFORMACION_TRASLADO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(APO_INFORMACION_TRASLADONdx);
  }
  private int APO_INFORMACION_TRASLADONdx;
  public double APO_SALDO_CAPITAL()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_SALDO_CAPITALNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_SALDO_CAPITALNdx;
  public double APO_SALDO_CUENTA_CONTINGENTE()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_SALDO_CUENTA_CONTINGENTENdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_SALDO_CUENTA_CONTINGENTENdx;
  public double APO_SALDO_NUMERO_UNIDADES()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_SALDO_NUMERO_UNIDADESNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_SALDO_NUMERO_UNIDADESNdx;
  public double APO_RENDIMIENTOS_PENALIZADOS()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_RENDIMIENTOS_PENALIZADOSNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_RENDIMIENTOS_PENALIZADOSNdx;
  public double APO_PORCENTAJE_RENDIMIENTOS_H()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_PORCENTAJE_RENDIMIENTOS_HNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_PORCENTAJE_RENDIMIENTOS_HNdx;
  public double APO_CAPITAL()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_CAPITALNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_CAPITALNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:42^59*/
                                       

  
 /**Iterator de la informaci�n de aportes.*/
/*@lineinfo:generated-code*//*@lineinfo:47^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class i_ejemplo4
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_ejemplo4(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    APO_CONSECUTIVONdx = findColumn("APO_CONSECUTIVO");
    APO_FECHA_APORTENdx = findColumn("APO_FECHA_APORTE");
    APO_APORTE_TRASLADONdx = findColumn("APO_APORTE_TRASLADO");
    APO_INFORMACION_TRASLADONdx = findColumn("APO_INFORMACION_TRASLADO");
    APO_SALDO_CAPITALNdx = findColumn("APO_SALDO_CAPITAL");
    APO_SALDO_CUENTA_CONTINGENTENdx = findColumn("APO_SALDO_CUENTA_CONTINGENTE");
    APO_SALDO_NUMERO_UNIDADESNdx = findColumn("APO_SALDO_NUMERO_UNIDADES");
    APO_RENDIMIENTOS_PENALIZADOSNdx = findColumn("APO_RENDIMIENTOS_PENALIZADOS");
    APO_PORCENTAJE_RENDIMIENTOS_HNdx = findColumn("APO_PORCENTAJE_RENDIMIENTOS_H");
    APO_CAPITALNdx = findColumn("APO_CAPITAL");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double APO_CONSECUTIVO()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_CONSECUTIVONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_CONSECUTIVONdx;
  public java.sql.Date APO_FECHA_APORTE()
    throws java.sql.SQLException
  {
    return (java.sql.Date)m_rs.getDate(APO_FECHA_APORTENdx);
  }
  private int APO_FECHA_APORTENdx;
  public String APO_APORTE_TRASLADO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(APO_APORTE_TRASLADONdx);
  }
  private int APO_APORTE_TRASLADONdx;
  public String APO_INFORMACION_TRASLADO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(APO_INFORMACION_TRASLADONdx);
  }
  private int APO_INFORMACION_TRASLADONdx;
  public double APO_SALDO_CAPITAL()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_SALDO_CAPITALNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_SALDO_CAPITALNdx;
  public double APO_SALDO_CUENTA_CONTINGENTE()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_SALDO_CUENTA_CONTINGENTENdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_SALDO_CUENTA_CONTINGENTENdx;
  public double APO_SALDO_NUMERO_UNIDADES()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_SALDO_NUMERO_UNIDADESNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_SALDO_NUMERO_UNIDADESNdx;
  public double APO_RENDIMIENTOS_PENALIZADOS()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_RENDIMIENTOS_PENALIZADOSNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_RENDIMIENTOS_PENALIZADOSNdx;
  public double APO_PORCENTAJE_RENDIMIENTOS_H()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_PORCENTAJE_RENDIMIENTOS_HNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_PORCENTAJE_RENDIMIENTOS_HNdx;
  public double APO_CAPITAL()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_CAPITALNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_CAPITALNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:56^59*/
/**Variable tipo iterator i_ejemplo3*/
i_ejemplo3 v_aportes3;
/**Variable tipo iterator i_ejemplo3*/
i_ejemplo4 v_aportes4;
/**Procedimeinto que consulta los aportes del contrato dado un esquema de retiro*/
public void TBPL_Final(HttpSession session,HttpServletRequest request,PrintWriter out,String nuevaCadena)
{//2
   /**Instancias de clase*/
   TBCL_Validacion      i_valusu = new TBCL_Validacion ();/**Instancia de la clase TBCL_Validacion */
   STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();/**Instancia de la clase  TBCL_GenerarBaseHTML*/
   //TBCL_ConexionSqlj    i_conexion = new TBCL_ConexionSqlj();/**Instancia de la clase TBCL_ConexionSqlj*/

//   TBCL_FuncionesAs400  i_fondos = new TBCL_FuncionesAs400();/**Instancia de la clase TBCL_FuncionesAs400*/

  try
  {//3
   String[] v_valusu = new String[3];
   v_valusu=i_valusu.TBFL_ValidarUsuario();
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   Connection t_tax =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
   DefaultContext ctx10 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
   DefaultContext.setDefaultContext(ctx10);
   /**Conectar a la base de datos*/
   //i_conexion.TBCL_ConexionBaseDatos();

   /**Definicion de variables*/
   /**Tipo boolean*/
   boolean  v_filapo    =true;/**Verificaci�n de que se encuentran datos*/
   boolean  v_nota      = false;/**Indicador de aportes de traslado sin informaci�n*/
   boolean  v_saro      = false;/**Validaci�n del evento saro */

   /**Tipo Stirng*/
   String v_ordenselect  = "";/**Variable que indica orden del select*/
   String v_naturaleza2  = "";/**Variable  m�todo naturaleza de retiro*/
   String v_respetar     = "";/**Variable respetar naturaleza*/
   String v_orden2       = "";/**Variable m�todo orden*/
   String v_beneficio    = "";/**Variable m�todo beneficio*/
   String v_metben       = "";/**Variable  c�digo m�todo beneficio*/
   String v_ben          = "";/**Variable  indicador de beneficio*/
   String v_penalizacion = "";/**Variable m�todo penalizaci�n*/
   String v_pen          = "";/**Variable indicador de penalizaci�n*/
   String v_metpen       = "";/**Variable c�digo m�todo penalizaci�n*/
   String v_cuenta       = "";/**Variable m�todo cuenta*/
   String v_metcue       = "";/**Variable c�digo metodo cuenta contingente*/
   String v_cue          = "";/**Variable indicador cuenta contingente*/
   String v_valor        = "";/**Variable valor del retiro*/
   String v_valuni       = "";/**Variable valor unidad*/
   String v_actualizar   = "";/**Variable indicadicador de actualizaci�n  del esquema retiro default contrato*/
   String v_tipov        = "";/**Variable tipo del valor a retirar*/
   String v_menesquema   = "";/**Variable  mensaje de error de validad esquema*/
   String v_mincon       = "";/**Variable minimo saldo a dejar en el contraot*/
   String          v_msgErr          = "";/**Variable  mensaje de error de saldo aporte*/
   String v_servlet      = "TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_SininfRetiro2";/**Nombre del servlet a llamar*/
   String v_contrato     = "";/**Variable  numero de contrato*/
   String v_producto     = "";/**Variable  c�digo del producto*/
   String v_canje        = "";/**Variable d�as de canje*/
   String v_nom          = "";/**Variable  nombre afiliado*/
   String v_ape          = "";/**Variable  apellido afiliado*/
   String v_fecha        = "";/**Variable  fecha efectiva del retiro*/
   String v_fechapro     = "";/**Variable  fecha proceso retiro*/
   String v_sistema      = "";/**Variable nombre del sistema multifund*/
   String v_usumfund     = "";/**Variable usuario multifund*/
   String v_passmfund    = "";/**Variable password de usuario multifund*/
   String v_libreria     = "";/**Variable nombre de libreria multifund*/
   String v_tipopenaliza = "";/**Variable penalizaci�n retiro*/
   String v_pintar       = "";/**Variable inicio pagina*/
   String v_pie          = "";/**Variable final pagina*/
   String v_idsaro       = "";/**Variable para el id del evento de saro*/
   /**Tipos cargos retiro*/
   String v_cargo1       = "";
   String v_cargo2       = "";
   String v_cargo3       = "";
   String v_cargo4       = "";  
   String v_salrenneto   = "";/**Saldo total de rendimientos netos del contrato */
   String v_salrenbruto  = "";/**Saldo total de rendimientos brutos del contrato */
   String v_saldoneto    = "";/**Saldo total  neto del contrato */
   String v_saldobruto   = "";/**Saldo total bruto del contrato */
   String v_saldomfund   = "";/**Saldo multifund*/

   /**Tipo Date*/
   Date v_fechaefectivapl  = new Date(1);/**Fecha efectiva del aporte*/
   Date v_fechaefectivapl2 = new Date(1);/**Fecha efectiva del aporte*/
   /**Tipo int*/
   /**Validar que todo el esquema del retiro este seleccionado*/
   int numnat = 0;
  int numres  =0;
   int numord = 0;
   int numben = 0;
   int numpen = 0;
   int numcue = 0;
   int v_coderr           = 0;/**C�digo de error funci�n saldo aporte*/
   int v_indvalesq    = 0;/**Indicador de exito o fracaso validaci�n esquema*/
   double v_conpl        = 0;/**Consecutivo aporte*/
   double v_conpl2       = 0;/**Consecutivo aporte*/
   int v_con          = 0;/**Contador*/
   /**Tipo double*/
   /**Formulaci�n saldo minimo contrato*/
   double v_formula       = 0;
   double v_formulapor    = 0;
   double v_formula2      = 0;
   double v_valor2        = 0;/**Valor a retirar numerico*/
   double v_salTotCap1    = 0;/**Saldo capital aporte bruto*/
   double v_salTotConting = 0;/**Saldo cuenta contingente aporte bruto*/
   double v_salTotRend1     = 0;/**Saldo de rendiminetos aporte bruto*/
   double        v_salTotRendN                   = 0;/**Saldo de rendiminetos nuevos*/
            double          v_salTotRendH                   = 0;/**Saldo de rendiminetos huerfanos*/
            double          v_salTotCap2                      = 0;/**Saldo capital aporte neto*/
   double v_valunidad = 0; /**Variable que captura el valor de la unidad*/ 
   double v_salTotRend2               = 0;/**Saldo de rendiminetos aporte neto*/
   double v_sumcap        = 0;/**Sumatoria de capital del contrato*/
   double v_sumren        = 0;/**Sumatoria de rendimientos del contrato*/
   double v_sumcapren     = 0;/**Sumatoria de rendimientos mas capital del contrato*/
   double v_capnetbru     = 0;/**Saldo Capital bruto*/
   double v_rennetbru     = 0;/**Saldo de rendimiento bruto*/
   double v_minimo_contrato = 0;/**Minimo del contrato*/
   double v_saldo_bruto   = 0;/**Saldo bruto del aporte*/
   double v_saldo_neto    = 0;/**Saldo neto del aporte*/
   double v_sumsaldo_bruto= 0;/**Sumatoria Saldo bruto del aporte*/
   double v_sumsaldo_neto = 0;/**Sumatoria Saldo neto del aporte*/
  double v_porret        = 0;/**Porcentaje de retenci�n*/
   double v_porpen        = 0;/**Porcentaje de penalizaci�n*/
   double v_porpencap     = 0;/**Porcentaje de penalizaci�n capital*/
   double v_porben        = 0;/**Porcentaje de beneficio*/
   double v_pordis        = 0;/**Porcentaje de disponibilidad*/
   double v_saldonetocontrato  = 0;
   double v_saldobrutocontrato = 0;
   double v_salrenpositivos    = 0;
   String v_decimal ="N";


   /**Verificar que las variables de session no expiren*/
   if((java.lang.String)session.getValue("s_contrato") !=null || (java.lang.String)session.getValue("s_producto") != null)
   {/**Capturar variables de session  4*/
    v_contrato     = (java.lang.String)session.getValue("s_contrato");//toma contrato
    v_producto     = (java.lang.String)session.getValue("s_producto");//toma producto
    v_canje        = (java.lang.String)session.getValue("s_diascanje");//toma numero de dias canje
    v_nom          = (java.lang.String)session.getValue("s_nombres");//tomo nombres del afiliado
    v_ape          = (java.lang.String)session.getValue("s_apellidos");//tomo apellidos del afiliado
    v_fecha        = (java.lang.String)session.getValue("s_fecefectiva");//tomo fecha efectiva del retirpo
    v_fechapro     = (java.lang.String)session.getValue("s_fecpro");//tomo fecha efectiva del retirpo
    v_sistema      = (java.lang.String)session.getValue("s_sistema");
    v_usumfund     = (java.lang.String)session.getValue("s_usumfund");
    v_passmfund    = (java.lang.String)session.getValue("s_passmfund");
    v_libreria     = (java.lang.String)session.getValue("s_libreria");
    v_tipopenaliza = (java.lang.String)session.getValue("s_penaliza");
    v_cargo1       = (java.lang.String)session.getValue("s_cargo1");
    v_cargo2       = (java.lang.String)session.getValue("s_cargo2");
    v_cargo3       = (java.lang.String)session.getValue("s_cargo3");
    v_cargo4       = (java.lang.String)session.getValue("s_cargo4");
    //v_salrenneto   = (java.lang.String)session.getValue("s_salrenneto");
    //v_salrenbruto  = (java.lang.String)session.getValue("s_salrenbruto");
    double v_salrenneto2    = new Double((java.lang.String)session.getValue("s_salrenneto")).doubleValue();
    double v_salrenbruto2   = new Double((java.lang.String)session.getValue("s_salrenbruto")).doubleValue();
    //v_saldoneto    =(java.lang.String)session.getValue("s_saldisponeto");
    //v_saldobruto   =(java.lang.String)session.getValue("s_saldispo");
    //v_saldomfund   =(java.lang.String)session.getValue("s_saldomfund");
    double v_saldoneto2     = new Double((java.lang.String)session.getValue("s_saldisponeto")).doubleValue();
    double v_saldobruto2    = new Double((java.lang.String)session.getValue("s_saldispo")).doubleValue();
    double v_saldomfund2    = new Double((java.lang.String)session.getValue("s_saldomfund")).doubleValue();
    /* Agregado por Marcela Ortiz Sandoval 
     * 2009/10/27
     * Inclusi�n del id del evento de Saro
     */
    /**Capturar el id del evento de Saro */
    try { 
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        v_idsaro = request.getParameter("v_idsaro"); 
    session.removeValue("v_idsaro");
    session.putValue("v_idsaro",v_idsaro);
    
    /**Validar el id del evento de Saro */
    if (!v_idsaro.trim().equals("")){
      CallableStatement cs1 = t_tax.prepareCall ( "{? = call TBCL_FUNCIONESAS400.TBPL_ValidarSaro(?,?,?,?,?)}");
      cs1.registerOutParameter(1,Types.CHAR);
      cs1.setString(2,v_idsaro);
      cs1.setString(3,v_sistema);
      cs1.setString(4,v_usumfund);
      cs1.setString(5,v_passmfund);
      cs1.setString(6,v_libreria);
      cs1.executeUpdate();
      String v_rta = cs1.getString(1);
      cs1.close();
      if (v_rta.equals("N"))
       {
         v_saro = true;
         throw new Exception("El Evento Saro ingresado no es v�lido."); 
       }
    }
    } 
    catch (Exception e) { e.printStackTrace(); }
    
    session.removeValue("s_contador");
    session.putValue("s_contador",(java.lang.Object)new Integer(v_con).toString());
   /**Tomar naturaleza de retiro y declarar como variable de session*/
    try { v_naturaleza2 = request.getParameter("naturaleza"); } catch (Exception e) { e.printStackTrace(); }
    if(v_naturaleza2.equals("sin"))
    {//5
     numnat++;
    }//5
    else
    {//5
     session.removeValue("s_naturaleza");
     session.putValue("s_naturaleza",(java.lang.Object)v_naturaleza2);
    }//5
    /**Tomar respetar naturaleza y declarar como variable de session*/
    try { v_respetar = request.getParameter("respetar"); } catch (Exception e) { e.printStackTrace(); }
    if(v_respetar.equals("sin"))
    {//6
     numres++;
    }//6
    else
    {//6
     session.removeValue("s_respetar");
     session.putValue("s_respetar",(java.lang.Object)v_respetar);
     }//6
     /**Tomar rm�todo orden y declarar como variable de session*/
     try { v_orden2 = request.getParameter("orden"); } catch (Exception e) { e.printStackTrace(); }
     if(v_orden2.equals("sin"))
     {//7
      numord++;
     }//7
     else
     {//7
      session.removeValue("s_orden");
      session.putValue("s_orden",(java.lang.Object)v_orden2);
     }//7
     /**Indicador de actualizar esquema default*/
     try { v_actualizar = request.getParameter("v_actualizar"); } catch (Exception e) { e.printStackTrace(); }
     /**Declarar como variable de session*/
     session.removeValue("s_actualizar");
     session.putValue("s_actualizar",(java.lang.Object)v_actualizar);
     /**Toma m�todo beneficio y se declarar como variable de session*/
     try { v_beneficio = request.getParameter("beneficio"); } catch (Exception e) { e.printStackTrace(); }
     if(!v_beneficio.equals("sin"))
     {//8
      v_metben = v_beneficio.substring(1,7);
      v_ben    = v_beneficio.substring(0,1);
     }//8
     session.removeValue("s_beneficio");
     session.putValue("s_beneficio",(java.lang.Object)v_metben);
     /**Toma m�todo penalizaci�n y declarar como variable de session*/
     try { v_penalizacion = request.getParameter("penalizacion"); } catch (Exception e) { e.printStackTrace(); }
     if(!v_penalizacion.equals("sin"))
     {//9
      v_pen  =  v_penalizacion.substring(0,1);
      v_metpen =v_penalizacion.substring(1,7);
     }//9
     session.removeValue("s_metpen");
     session.putValue("s_metpen",(java.lang.Object)v_metpen);
     /**Tomar m�todo cuenta y se declara como variable de session*/
     try { v_cuenta = request.getParameter("cuenta"); } catch (Exception e) { e.printStackTrace(); }
     if(!v_cuenta.equals("sin"))
     {
      v_metcue =v_cuenta.substring(1,7);
      v_cue  =  v_cuenta.substring(0,1);
     }
     session.removeValue("s_cuencon");
     session.putValue("s_cuencon",(java.lang.Object)v_metcue);
     /**Verificar seleccion esquema retiro*/
     if(v_beneficio.equals("sin"))
     {
      numben++;
     }
     if(v_cuenta.equals("sin"))
     {
      numcue++;
     }
     if(v_penalizacion.equals("sin"))
     {
      numpen++;
     }
     /**Falta seleccion del esquema retiro*/
     if(numnat !=0 || numres !=0 || numord !=0 || numben!=0 || numcue != 0 || numpen != 0)
     {//10
      v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","error esquema del Retiro","","",true,"","");
     out.println(""+v_pintar+"");
      out.println("<pre>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'>            Favor Digitar</font>");
      out.println("<br>");
      if(numnat !=0)
      {
       out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'>            Naturaleza del Retiro</font>");
       out.println("<br>");
      }
      if(numres !=0)
      {
       out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'>            Respetar Naturaleza</font>");
       out.println("<br>");
      }
      if(numord !=0)
      {
       out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'>            M�todo Orden</font>");
       out.println("<br>");
      }
      if(numben !=0)
      {
       out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'>            M�todo de Beneficio</font>");
       out.println("<br>");
      }
      if(numcue !=0)
      {
       out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'>            M�todo Cuenta Contingente</font>");
       out.println("<br>");
      }
      if(numpen !=0)
      {
       out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'>            M�todo de Penalizaci�n</font>");
       out.println("<br>");
      }
      out.println("<br>");
      out.println("<PRE>");
      out.println("<center><input type=button value='Cancelar' onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
      v_pie = i_pagina.TBFL_PIE;
      out.println("<br>");
      out.println(""+v_pie+"");
      out.close();
     }//10
     else
     {//10
      /**Validar esquema de retiro*/

      /*@lineinfo:generated-code*//*@lineinfo:377^7*/

//  ************************************************************
//  #sql v_indvalesq = { values (TB_FVALIDAR_ESQUEMA(:v_producto ,:v_orden2,:v_ben,:v_pen,:v_cue,:v_naturaleza2,:v_respetar,:v_menesquema)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FVALIDAR_ESQUEMA( :2   , :3  , :4  , :5  , :6  , :7  , :8  , :9  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFinal",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_producto);
   __sJT_st.setString(3,v_orden2);
   __sJT_st.setString(4,v_ben);
   __sJT_st.setString(5,v_pen);
   __sJT_st.setString(6,v_cue);
   __sJT_st.setString(7,v_naturaleza2);
   __sJT_st.setString(8,v_respetar);
   __sJT_st.setString(9,v_menesquema);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_indvalesq = __sJT_st.getInt(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_menesquema = (String)__sJT_st.getString(9);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:377^146*/
      if(v_indvalesq == 0)
      {//11
       //v_valor  =( java.lang.String)session.getValue("s_valor");
       String v_valorret = ( java.lang.String)session.getValue("s_valor");
       v_valor2 = new Double (v_valorret).doubleValue();
       v_valuni = (java.lang.String)session.getValue("s_valuni");
       v_tipov  =(java.lang.String)session.getValue("s_tipov");
       
       v_valunidad = new Double(v_valuni).doubleValue();
        
       if(v_orden2.equals("SMO003"))
       {
        v_servlet="TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_SininfRetiro";
       }
       /**Dibujar pagina de respuesta*/
       v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Detalle del Retiro",""+v_servlet+"","",true,"modulo_retiros.js","");
       out.println(""+v_pintar+"");
       /*Cambio para manejo de referencia unica 2009/03/30 MOS */
       String v_contrato_unif = "";
       /*@lineinfo:generated-code*//*@lineinfo:397^8*/

//  ************************************************************
//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_producto,:v_contrato)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFinal",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_producto);
   __sJT_st.setString(3,v_contrato);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_contrato_unif = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:397^88*/
       session.putValue("s_contra_unif",v_contrato_unif);
       out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_producto+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
       out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
       out.println("<br>");
       out.println("<br>");
       out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Valor a Retirar                     "+NumberFormat.getCurrencyInstance().format(v_valor2)+"</b></font></center>");
       out.println("<br>");
       /**Segun el m�todo orden se muestran aportes ascendente o descendente*/
       if(v_orden2.equals("SMO002") )
       {
        v_ordenselect = "D";
       }
       else if(v_orden2.equals("SMO003") )
            {
             v_ordenselect = "D";
            }
            else
            {
             v_ordenselect = "A";
            }

        /**Consulta segun el esquema escogido*/
        //*************************************************************************************//
        if(!v_orden2.equals("SMO003"))
        {//si es lifo o fifo 12

//          out.println("Antes de TBCL_FuncionesAs400.TBPL_ProgramaContrato2<br>");
            /*Modificaci�n hecha por APC para manejar el nuevo reglamento 2006-06-22*/          
            String v_retorno_programa = new String("");
            String v_programa;
            CallableStatement cs2 = t_tax.prepareCall ( "{? = call TBCL_FuncionesAs400.TBPL_ProgramaContrato(?,?,?,?,?)}");
            cs2.registerOutParameter(1,Types.CHAR);
            cs2.setString(2,v_contrato);
            cs2.setString(3,v_sistema);
            cs2.setString(4,v_usumfund);
            cs2.setString(5,v_passmfund);
            cs2.setString(6,v_libreria);
            cs2.executeUpdate();
            v_retorno_programa = cs2.getString(1);
            cs2.close();
            v_programa = v_retorno_programa.substring(0,v_retorno_programa.indexOf(";",0));
            /*FIN Modificaci�n hecha por APC para manejar el nuevo reglamento 2006-06-22*/          
//            out.println("v_retorno_programa.indexOf(';',1)=" + v_retorno_programa.indexOf(";",0));
//            out.println("Antes de TB_PKGSELECCION_APORTES.TB_FFILTRO_APORTE: v_retorno_programa="+v_retorno_programa);


        
         /**Consultar aportes*/
//         out.println("Antes de TB_PKGSELECCION_APORTES.TB_FFILTRO_APORTE:"+v_producto+","+v_contrato+","+v_ben+","+v_cue+","+v_pen+","+v_canje+","+v_fecha+","+v_fechapro+","+v_ordenselect);
         /*Modificaci�n hecha por APC para manejar el nuevo reglamento 2006-06-22*/          
//         #sql v_aportes3 = { values (TB_PKGSELECCION_APORTES.TB_FFILTRO_APORTE(:v_producto,:v_contrato ,:v_ben,:v_cue,:v_pen,:v_canje,:v_fecha,:v_fechapro,:v_ordenselect ))};

         //#sql v_aportes3 = { values (TB_PKGSELECCION_APORTES.TB_FFILTRO_APORTE(:v_producto,:v_contrato ,:v_ben,:v_cue,:v_pen,:v_canje,:v_fecha,:v_fechapro,:v_ordenselect,:v_programa))};
         
         //{
  // declare temps
  oracle.jdbc.OracleCallableStatement sJT_stTem = null;
  sqlj.runtime.ref.DefaultContext sJT_ccTem = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (sJT_ccTem==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext sJT_ecTe = ((sJT_ccTem.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sJT_ccTem.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_PKGSELECCION_APORTES.TB_FFILTRO_APORTE( :2 , :3  , :4 , :5 , :6 , :7 , :8 , :9 , :10 , :11 ) \n; END;";
   sJT_stTem = sJT_ecTe.prepareOracleCall(sJT_ccTem,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFinal",theSqlTS);
   if (sJT_ecTe.isNew())
   {
      sJT_stTem.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);
   }
   // set IN parameters
   sJT_stTem.setString(2,v_producto);
   sJT_stTem.setString(3,v_contrato);
   sJT_stTem.setString(4,v_ben);
   sJT_stTem.setString(5,v_cue);
   sJT_stTem.setString(6,v_pen);
   sJT_stTem.setString(7,v_canje);
   sJT_stTem.setString(8,v_fecha);
   sJT_stTem.setString(9,v_fechapro);
   sJT_stTem.setString(10,v_ordenselect);
   sJT_stTem.setString(11,v_programa);
// execute statement
   sJT_ecTe.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_aportes3 = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFinal.i_ejemplo3(new sqlj.runtime.ref.OraRTResultSet(sJT_stTem.getCursor(1)));
  } finally {sJT_ecTe.oracleCloseQuery(); }

         /*FIN Modificaci�n hecha por APC para manejar el nuevo reglamento 2006-06-22*/   
         //out.println("Despues de TB_PKGSELECCION_APORTES.TB_FFILTRO_APORTE");
         out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
         out.println("<tr><td class=\"td11\" colspan= 5 ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Saldos Disponibles de los aportes</b></font></center></font></td></tr>");
         out.println("<tr><td class=\"td11\"  ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Fecha Aporte</b></font></center></font></td>");
         out.println("<td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Saldo Capital</b></center></font></td>");
         out.println("<td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Saldo Rendimientos</b></center></font></td>");
         out.println("<td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Saldo Cuenta Contingente</b></center></font></td></TR>");
         while(v_aportes3.next())
         {//while 13
          v_conpl           = v_aportes3.APO_CONSECUTIVO();
          v_fechaefectivapl = v_aportes3.APO_FECHA_APORTE();
             
          //out.println("Despues de asignacion");   
          /**Consultar saldo aporte*/
          /*@lineinfo:generated-code*//*@lineinfo:496^11*/

//  ************************************************************
//  #sql { call  TBPBD_SaldoAporteRet( :v_producto
//                                             ,:v_contrato
//                                             ,:v_conpl
//                                             ,:v_valunidad
//                                             ,:v_tipopenaliza  
//                                             ,to_date(:v_fecha,'RRRR-MM-DD')
//                                             ,:v_cargo1
//                                             ,:v_cargo2
//                                             ,:v_cargo3
//                                             ,:v_cargo4
//                                             ,:v_programa       
//                                                                ,:v_salTotCap1
//                                                                                     ,:v_salTotConting
//                                                                                                 ,:v_salTotRend1
//                                                                                                 ,:v_salTotRendN
//                                                                                     ,:v_salTotRendH
//                                                                                     ,:v_salTotCap2
//                                                                                     ,:v_salTotRend2
//                                             ,:v_porret
//                                             ,:v_porpen
//                                             ,:v_porpencap
//                                             ,:v_porben
//                                             ,:v_pordis
//                                             ,:v_coderr
//                                                                          ,:v_msgErr
//                                             ,:v_decimal
//                                             ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_SaldoAporteRet(  :1  \n                                           , :2  \n                                           , :3  \n                                           , :4  \n                                           , :5    \n                                           ,to_date( :6  ,'RRRR-MM-DD')\n                                           , :7  \n                                           , :8  \n                                           , :9  \n                                           , :10  \n                                           , :11         \n                                                              , :12  \n                                                                                   , :13  \n                                                                                               , :14  \n                                                                                               , :15  \n                                                                                   , :16  \n                                                                                   , :17  \n                                                                                   , :18  \n                                           , :19  \n                                           , :20  \n                                           , :21  \n                                           , :22  \n                                           , :23  \n                                           , :24  \n                                                                        , :25  \n                                           , :26  \n                                           )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFinal",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(13,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(14,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(17,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(18,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(19,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(20,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(21,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(22,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(23,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(24,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(25,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,v_producto);
   __sJT_st.setString(2,v_contrato);
   __sJT_st.setDouble(3,v_conpl);
   __sJT_st.setDouble(4,v_valunidad);
   __sJT_st.setString(5,v_tipopenaliza);
   __sJT_st.setString(6,v_fecha);
   __sJT_st.setString(7,v_cargo1);
   __sJT_st.setString(8,v_cargo2);
   __sJT_st.setString(9,v_cargo3);
   __sJT_st.setString(10,v_cargo4);
   __sJT_st.setString(11,v_programa);
   __sJT_st.setInt(24,v_coderr);
   __sJT_st.setString(25,v_msgErr);
   __sJT_st.setString(26,v_decimal);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_salTotCap1 = __sJT_st.getDouble(12); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotConting = __sJT_st.getDouble(13); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotRend1 = __sJT_st.getDouble(14); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotRendN = __sJT_st.getDouble(15); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotRendH = __sJT_st.getDouble(16); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotCap2 = __sJT_st.getDouble(17); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotRend2 = __sJT_st.getDouble(18); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_porret = __sJT_st.getDouble(19); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_porpen = __sJT_st.getDouble(20); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_porpencap = __sJT_st.getDouble(21); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_porben = __sJT_st.getDouble(22); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_pordis = __sJT_st.getDouble(23); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_coderr = __sJT_st.getInt(24); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_msgErr = (String)__sJT_st.getString(25);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:522^45*/
          //out.println("<br>Llamado a TBPBD_SaldoAporteRet("+v_producto+","+v_contrato+","+v_conpl+","+v_valuni+","+v_tipopenaliza+","+v_fecha+","+v_cargo1+","+v_cargo2+","+v_cargo3+","+v_cargo4+",* "+v_programa+",* "+v_salTotCap1+",* "+v_salTotConting+",* "+v_salTotRend1 +",* " +v_salTotRendN+",* "+v_salTotRendH+",* "+v_salTotCap2+",* "+v_salTotRend2+",* " +v_porret+",* "+v_porpen+", * "+ v_porpencap + ",* "+v_porben+ ",* "+ v_pordis + ",* " +v_coderr+ ",* "+v_msgErr+")");                                           
          v_saldo_bruto = v_salTotCap1 + v_salTotRend1;
          v_saldo_neto  = v_salTotCap2 + v_salTotRend2;
//          out.println(v_tipov+","+v_naturaleza2+","+v_respetar);
          if((v_saldo_bruto > 0 && v_tipov.equals("STV001")) || (v_saldo_neto > 0 && v_tipov.equals("STV002")))
          {//1
           v_sumsaldo_bruto = v_sumsaldo_bruto + v_salTotCap1 + v_salTotRend1;
           v_sumsaldo_neto  =  v_sumsaldo_neto + v_salTotCap2 + v_salTotRend2;
           //rendimiento bruto
           if(v_naturaleza2.equals("SNR001")&& v_tipov.equals("STV001") && v_respetar.equals("S"))//rendimientos
           {//1
               v_sumren = v_sumren + v_salTotRend1;
           }//1
           else if(v_naturaleza2.equals("SNR001")&& v_tipov.equals("STV001") && v_respetar.equals("N"))//rendimientos
                {//2
                 if(v_salrenbruto2 < 0)
                 {//3
                  if(v_salTotRend1 < 0)
                  {//4
                   v_sumren = v_sumren + v_salTotRend1 + v_salTotCap1 ;
                  }//4
                  else
                  {//4
                   v_sumren = v_sumren +  v_salTotCap1 ;
                  }//4
                 }//3
                 else
                 {//3
                   if(v_salTotRend1 <0 )
                   {
                     v_sumren = v_sumren + v_salTotRend1 + v_salTotCap1 ;
                   }
                   else
                   {
                     v_sumren = v_sumren +  v_salTotCap1 ;
                     v_salrenpositivos += v_salTotRend1;
                   }
                  }//3
                 }//2

           //rendimiento neto
           if(v_naturaleza2.equals("SNR001")&& v_tipov.equals("STV002") && v_respetar.equals("S"))//rendimientos
           {//1
             v_sumren = v_sumren + v_salTotRend2;
           }//1
           else  if(v_naturaleza2.equals("SNR001")&& v_tipov.equals("STV002") && v_respetar.equals("N"))//rendimientos
                 {//2
                   if(v_salrenneto2 < 0)
                   {//3
                    if(v_salTotRend2 < 0)
                    {//4
                      v_sumren = v_sumren + v_salTotRend2 + v_salTotCap2 ;
                    }//4
                    else
                    {//4
                     v_sumren = v_sumren  + v_salTotCap2 ;
                    }//4
                   }//3
                   else
                   {//3
                    if(v_salTotRend2 <0 )
                    {
                      v_sumren = v_sumren + v_salTotRend2 + v_salTotCap2 ;
                    }
                    else
                    {
                     v_sumren = v_sumren +  v_salTotCap2 ;
                     v_salrenpositivos += v_salTotRend2;
                    }
                   }//3
                  }//2

           //proporcional
           if(v_naturaleza2.equals("SNR003") && v_tipov.equals("STV001"))//proporcional
           {
            /*if(v_salTotRend1 < 0)
            {
              v_sumcapren =  v_sumcapren + v_salTotCap1 + v_salTotRend1;
            }
            else
             {
                 v_sumcapren = v_sumcapren +  v_salTotCap1 ;
                 v_salrenpositivos += v_salTotRend1;
             }*/
              if(v_salrenbruto2 < 0)
                 {//3
                  if(v_salTotRend1 < 0)
                  {//4
                   v_sumcapren = v_sumcapren + v_salTotRend1 + v_salTotCap1 ;
                  }//4
                  else
                  {//4
                   v_sumcapren = v_sumcapren +  v_salTotCap1 ;
                  }//4
                 }//3
                 else
                 {//3
                   if(v_salTotRend1 <0 )
                   {
                     v_sumcapren = v_sumcapren + v_salTotRend1 + v_salTotCap1 ;
                   }
                   else
                   {
                     v_sumcapren = v_sumcapren +  v_salTotCap1 ;
                     v_salrenpositivos += v_salTotRend1;
                   }
                  }//3
           }//aqui voy
           else  if(v_naturaleza2.equals("SNR003") && v_tipov.equals("STV002"))//proporcional
                 {
                   /*if( v_salTotRend2 < 0)
                   {
                     v_sumcapren =  v_sumcapren + v_salTotCap2 + v_salTotRend2;
                   }
                   else
                   {
                     v_sumcapren = v_sumcapren +  v_salTotCap2 ;
                     v_salrenpositivos += v_salTotRend2;
                   }*/
                      if(v_salrenneto2 < 0)
                      {//3
                        if(v_salTotRend2 < 0)
                        {//4
                          v_sumcapren = v_sumcapren + v_salTotRend2 + v_salTotCap2 ;
                        }//4
                        else
                        {//4
                          v_sumcapren = v_sumcapren  + v_salTotCap2 ;
                        }//4
                      }//3
                      else
                      {//3
                        if(v_salTotRend2 <0 )
                        {
                          v_sumcapren = v_sumcapren + v_salTotRend2 + v_salTotCap2 ;
                        }
                        else
                        {
                          v_sumcapren = v_sumcapren +  v_salTotCap2 ;
                          v_salrenpositivos += v_salTotRend2;
                        }
                      }//
                 }
           //capital bruto
           if(v_naturaleza2.equals("SNR002") && v_tipov.equals("STV001") && v_respetar.equals("S"))//capital
           {
             if(v_salTotRend1<0)
             {
              v_sumcap =  v_sumcap + v_salTotCap1 + v_salTotRend1;
             }
             else
             {
             v_sumcap =  v_sumcap + v_salTotCap1;
             }
           }
           else if(v_naturaleza2.equals("SNR002") && v_tipov.equals("STV001") && v_respetar.equals("N"))//capital
                {
                 if(v_salrenbruto2 < 0)
                 {
                  if(v_salTotRend1<0)
                  {
                    v_sumcap =  v_sumcap + v_salTotCap1 + v_salTotRend1;
                  }
                  else
                  {
                   v_sumcap =  v_sumcap + v_salTotCap1;
                  }
                 }
                 else
                 {
                   if(v_salTotRend1 < 0)
                   {
                     v_sumcap =  v_sumcap + v_salTotCap1 + v_salTotRend1;
                   }
                   else
                   {
                     v_sumcap = v_sumcap +  v_salTotCap1 ;
                     v_salrenpositivos += v_salTotRend1;
                   }
                 }
                }

           //capital neto
           if(v_naturaleza2.equals("SNR002") && v_tipov.equals("STV002") && v_respetar.equals("S"))//capital
           {
             if(v_salTotRend2<0)
             {
              v_sumcap =  v_sumcap + v_salTotCap2 + v_salTotRend2;
             }
             else
             {
             v_sumcap =  v_sumcap + v_salTotCap2;
             }
           }
           else if(v_naturaleza2.equals("SNR002") && v_tipov.equals("STV002") && v_respetar.equals("N"))//capital
                {//2
                 if(v_salrenneto2 < 0)
                 {//3
                  if(v_salTotRend2<0)
                  {//4
                    v_sumcap =  v_sumcap + v_salTotCap2 + v_salTotRend2;
                  }//4
                  else
                  {//4
                   v_sumcap =  v_sumcap + v_salTotCap2;
                  } //4
                 }//3
                 else
                 {//3
                   if(v_salTotRend2 < 0)
                   {
                    v_sumcap =  v_sumcap + v_salTotCap2 + v_salTotRend2;
                   }
                   else
                   {
                     v_sumcap = v_sumcap +  v_salTotCap2 ;
                     v_salrenpositivos += v_salTotRend2;
                  }
                  }//3
                 }//2

           if(v_tipov.equals("STV001"))
           {
            v_capnetbru =  v_salTotCap1;
            v_rennetbru = v_salTotRend1;
           }
           else if(v_tipov.equals("STV002"))
           {
            v_capnetbru =  v_salTotCap2;
            v_rennetbru = v_salTotRend2;
           }
           /**Dibujar informaci�n aportes*/
           if(v_aportes3.APO_APORTE_TRASLADO().equals("S") && v_aportes3.APO_INFORMACION_TRASLADO().equals("N"))
           {
            out.println("<tr><td class=\"td12\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+v_fechaefectivapl+"</font></center></td>");
            out.println("<td class=\"td12\" align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_capnetbru)+"</font></td>");
            out.println("<td class=\"td12\" align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_rennetbru)+"</font></td>");
            out.println("<td class=\"td12\" align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_salTotConting)+"</font></td></tr>");
            v_nota = true;
           }
           else
           {
            out.println("<tr><td ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+v_fechaefectivapl+"</font></td>");
            out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_capnetbru)+"</font></td>");
            out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_rennetbru)+"</font></td>");
            out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_salTotConting)+"</font></td></tr>");
           }
           v_filapo = false;
          }
         }//while
         /**Si no se encuentran aportes*/
         if(v_filapo)
         {
          out.println("<tr><td  ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></center></td>");
          out.println("<td><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></center></td>");
          out.println("<td><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></center></td>");
          out.println("<td><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></td></center></tr>");
          out.println("<tr><td colspan= 5><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>No se encontraron aportes para la definici�n del retiro.</font></center></td><tr>");
          out.println("</table></center>");
          out.println("<br>");
          out.println("<center><input type=button value='Cancelar' onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
          v_pie = i_pagina.TBFL_PIE;
          out.println("<br>");
          out.println(""+v_pie+"");
          out.close();
         }
         /**Si de los aportes seleccionados se encuentran  aportes sin informaci�n de traslado*/
         if(v_nota)
         {
          out.println("<br>");
          out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b> Los aportes de color gris, son aportes de traslado sin informaci�n.</font></center>");
          out.println("<br>");
         }

         //validar saldo capital
         if(v_naturaleza2.equals("SNR002"))//capital
         {//1
          if(v_respetar.equals("N"))
          {//2

            if( v_tipov.equals("STV002"))//neto
            {
             if(v_salrenneto2 >= 0)
             {
               if(v_salrenneto2 < v_salrenpositivos)
                 v_sumcap += v_salrenneto2 ;
               else
                 v_sumcap += v_salrenpositivos ;
             }
            }
            else if( v_tipov.equals("STV001"))//BRUTO
            {
              if (v_salrenbruto2 >= 0)
              {
               if(v_salrenbruto2 < v_salrenpositivos)
                 v_sumcap += v_salrenbruto2 ;
               else
                 v_sumcap += v_salrenpositivos ;

              }
            }


            if(v_valor2> v_sumcap)
            {//3
             String v_men = "";
             String v_men2 = "";
             v_men = "sin respetar naturaleza";
             if( v_tipov.equals("STV002"))//neto
             {//4
              if(v_salrenneto2< 0)
              {//5
               v_men2 = "Se tienen rendimientos totales negativos "+NumberFormat.getCurrencyInstance().format(v_salrenneto2)+". Por lo tanto solo es posible retirar "+NumberFormat.getCurrencyInstance().format(v_sumcap)+".";
              }//5
             }//4
             else if( v_tipov.equals("STV001"))//BRUTO
             {//4
              if(v_salrenbruto2< 0)
              {
               v_men2 = "Se tienen rendimientos totales negativos "+NumberFormat.getCurrencyInstance().format(v_salrenbruto2)+". Por lo tanto solo es posible retirar "+NumberFormat.getCurrencyInstance().format(v_sumcap)+".";
              }
             }//4
             out.println("</table></center>");
             out.println("<br>");
             out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza capital sin respetar naturaleza, porque el saldo disponible  es "+NumberFormat.getCurrencyInstance().format(v_sumcap)+" y no alcanza para el retiro."+v_men2+"</font></center>");
             out.println("<br>");
             out.println("<PRE>");
             out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
             v_pie = i_pagina.TBFL_PIE;
             out.println("<br>");
             out.println(""+v_pie+"");
              out.close();
            }//3
          }
          else
          {
             if(v_valor2>v_sumcap)
             {//5
              out.println("</table></center>");
              out.println("<br>");
              out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza capital, porque el saldo disponible  es "+NumberFormat.getCurrencyInstance().format(v_sumcap)+" y no alcanza para el retiro.</font></center>");
              out.println("<br>");
              out.println("<PRE>");
              out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
              v_pie = i_pagina.TBFL_PIE;
              out.println("<br>");
              out.println(""+v_pie+"");
              out.close();
             }//5
           }
         }   //1
         //validar saldo rendimientos
         if(v_naturaleza2.equals("SNR001"))//rendimiento
         {//1
          if(v_respetar.equals("S"))
          {//2
           if( v_tipov.equals("STV002"))//neto
           {//3
            if(v_salrenneto2< 0)
            {//4
             out.println("</table></center>");
             out.println("<br>");
             out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza rendimientos , porque el saldo disponible de rendimientos totales  es "+NumberFormat.getCurrencyInstance().format(v_salrenneto2)+" y no alcanza para el retiro.</font></center>");
             out.println("<br>");
             out.println("<PRE>");
             out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
             v_pie = i_pagina.TBFL_PIE;
             out.println("<br>");
             out.println(""+v_pie+"");
             out.close();
            }//4
            else
            {//4
             if(v_valor2> v_sumren)
             {//5
              out.println("</table></center>");
              out.println("<br>");
              out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza rendimiento, porque el saldo disponible es "+NumberFormat.getCurrencyInstance().format(v_sumren)+" y no alcanza para el retiro.</font></center>");
              out.println("<br>");
              out.println("<PRE>");
              out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
              v_pie = i_pagina.TBFL_PIE;
              out.println("<br>");
              out.println(""+v_pie+"");
              out.close();
             }//5
            }//4
           }//3
           else if( v_tipov.equals("STV001"))//bruto
            {//4
             if(v_salrenbruto2< 0)
             {//5
              out.println("</table></center>");
              out.println("<br>");
              out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza rendimientos , porque el saldo disponible de rendimientos totales  es "+NumberFormat.getCurrencyInstance().format(v_salrenbruto2)+" y no alcanza para el retiro.</font></center>");
              out.println("<br>");
              out.println("<PRE>");
              out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
              v_pie = i_pagina.TBFL_PIE;
              out.println("<br>");
              out.println(""+v_pie+"");
              out.close();
             }//5
             else
             {//5
              if(v_valor2> v_sumren)
              {//6
               out.println("</table></center>");
               out.println("<br>");
               out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza rendimiento, porque el saldo disponible es "+NumberFormat.getCurrencyInstance().format(v_sumren)+" y no alcanza para el retiro.</font></center>");
               out.println("<br>");
               out.println("<PRE>");
               out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
               v_pie = i_pagina.TBFL_PIE;
               out.println("<br>");
               out.println(""+v_pie+"");
               out.close();
              }//6
             }//5
            }//4
          }//2
          else
          {//2
            if( v_tipov.equals("STV002"))//neto
            {
             if (v_salrenneto2 >= 0)
             {
               if(v_salrenneto2 < v_salrenpositivos)
                 v_sumren += v_salrenneto2 ;
               else
                 v_sumren += v_salrenpositivos ;
             }
            }
            else if( v_tipov.equals("STV001"))//BRUTO
            {
             if (v_salrenbruto2 > 0)
             {
               if(v_salrenbruto2 < v_salrenpositivos)
                 v_sumren += v_salrenbruto2 ;
               else
                 v_sumren += v_salrenpositivos ;
             }
            }
           if(v_valor2> v_sumren)
           {//5
            String v_men3 = "";
            if( v_tipov.equals("STV002"))//neto
            {//3
             if(v_salrenneto2 <0)
             {
              v_men3 = "Se tienen rendimientos totales negativos "+NumberFormat.getCurrencyInstance().format(v_salrenneto2)+".Por lo tanto solo es posible retirar "+NumberFormat.getCurrencyInstance().format(v_sumren)+".";
             }
            }
            else  if( v_tipov.equals("STV001"))//neto
            {
             if(v_salrenbruto2 <0)
             {
              v_men3 = "Se tienen rendimientos totales negativos "+NumberFormat.getCurrencyInstance().format(v_salrenbruto2)+".Por lo tanto solo es posible retirar "+NumberFormat.getCurrencyInstance().format(v_sumren)+".";
             }
            }
            out.println("</table></center>");
            out.println("<br>");
            out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza rendimiento sin respetar naturaleza, porque el saldo disponible es "+NumberFormat.getCurrencyInstance().format(v_sumren)+" y no alcanza para el retiro."+v_men3+"</font></center>");
            out.println("<br>");
            out.println("<PRE>");
            out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
            v_pie = i_pagina.TBFL_PIE;
            out.println("<br>");
            out.println(""+v_pie+"");
            out.close();
           }//5
          }//2
         }//1
         //naturaleza proporcional
         if(v_naturaleza2.equals("SNR003"))
         {
            if( v_tipov.equals("STV002"))//neto
            {
              if (v_salrenneto2 >= 0)
              {
               if(v_salrenneto2 < v_salrenpositivos)
                  v_sumcapren  += v_salrenneto2 ;
               else
                 v_sumcapren  += v_salrenpositivos ;
              }
            }
            else if( v_tipov.equals("STV001"))//BRUTO
            {

              if (v_salrenbruto2 >= 0)
              {
               if(v_salrenbruto2 < v_salrenpositivos)
                  v_sumcapren  += v_salrenbruto2 ;
               else
                 v_sumcapren  += v_salrenpositivos ;

              }
            }


          if(v_valor2> v_sumcapren)
          {
           out.println("</table></center>");
           out.println("<br>");
           String v_men4 = "";
           if( v_tipov.equals("STV002"))//neto
           {//3
             if(v_salrenneto2 <0)
             {
              v_men4 = "Se tienen rendimientos totales negativos "+NumberFormat.getCurrencyInstance().format(v_salrenneto2)+".Por lo tanto solo es posible retirar "+NumberFormat.getCurrencyInstance().format(v_sumcapren)+".";
             }
            }
            else  if( v_tipov.equals("STV001"))//BRUTO
            {
             if(v_salrenbruto2 <0)
             {
              v_men4 = "Se tienen rendimientos totales negativos "+NumberFormat.getCurrencyInstance().format(v_salrenbruto2)+".Por lo tanto solo es posible retirar "+NumberFormat.getCurrencyInstance().format(v_sumcapren)+".";
             }
            }
           out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza proporcional, porque el saldo disponible es "+NumberFormat.getCurrencyInstance().format(v_sumcapren)+" y no alcanza para el retiro. "+v_men4+" </font></center>");
           out.println("<br>");
           out.println("<PRE>");
           out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
           v_pie = i_pagina.TBFL_PIE;
           out.println("<br>");
           out.println(""+v_pie+"");
           out.close();
          }
         }
         //validar  saldo minimo contrato
         if(v_tipov.equals("STV001"))//bruto
         {

          //v_mincon =i_fondos.TBFL_MinimoContrato(v_contrato,v_sistema,v_usumfund, v_passmfund, v_libreria);
          /*
           Modificacion:
           Se a�ade el procedimiento de invocacion a un procedimiento del AS400
          */

          CallableStatement cs = t_tax.prepareCall ( "{? = call TBCL_FUNCIONESAS400.TBFL_MinimoContrato(?,?,?,?,?)}");
          cs.registerOutParameter(1,Types.CHAR);
          cs.setString(2,v_contrato);
          cs.setString(3,v_sistema);
          cs.setString(4,v_usumfund);
          cs.setString(5,v_passmfund);
          cs.setString(6,v_libreria);
          cs.executeUpdate();
          v_mincon = cs.getString(1);
         cs.close();
          //t_tax.close();

          /* Final de la modificacion */


          if(!v_mincon.substring(0,5).equals("Error"))//minimo contrato
          {
           v_minimo_contrato = new Double(v_mincon).doubleValue();
           double v_mincon2   = v_minimo_contrato/100;

           v_formula2 = v_saldomfund2 - v_valor2;

           if(v_formula2 <=v_mincon2)
           {
            out.println("</table></center>");
            out.println("<br>");
            out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No se puede realizar la solicitud de retiro por que debe dejar minimo "+NumberFormat.getCurrencyInstance().format(v_mincon2)+" en el contrato.</font></center>");
            out.println("<br>");
            out.println("<PRE>");
            out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
            v_pie = i_pagina.TBFL_PIE;
            out.println("<br>");
            out.println(""+v_pie+"");
            out.close();
           }
          }
          else
          {//error minimo contrato
           out.println("</table></center>");
           out.println("<br>");
           out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>Consulta de minimo por contrato :"+v_mincon+"</font></center>");
           out.println("<br>");
           out.println("<PRE>");
           out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
           v_pie = i_pagina.TBFL_PIE;
           out.println("<br>");
           out.println("<br>");
           out.println(""+v_pie+"");
           out.close();
          }
         }
         else
         {//neto

          //v_mincon =i_fondos.TBFL_MinimoContrato(v_contrato,v_sistema,v_usumfund, v_passmfund, v_libreria);
          /*
           Modificacion:
           Se a�ade el procedimiento de invocacion a un procedimiento del AS400
          */

          CallableStatement cs = t_tax.prepareCall ( "{? = call TBCL_FUNCIONESAS400.TBFL_MinimoContrato(?,?,?,?,?)}");
          cs.registerOutParameter(1,Types.CHAR);
          cs.setString(2,v_contrato);
          cs.setString(3,v_sistema);
          cs.setString(4,v_usumfund);
          cs.setString(5,v_passmfund);
          cs.setString(6,v_libreria);
          cs.executeUpdate();
          v_mincon = cs.getString(1);
          cs.close();
          //t_tax.close();

          /* Final de la modificacion */

          if(!v_mincon.substring(0,5).equals("Error"))//minimo contrato
          {
           v_minimo_contrato = new Double(v_mincon).doubleValue();
           double v_mincon2   = v_minimo_contrato/100;

           v_formulapor = v_saldoneto2  / v_saldobruto2;

           v_formula    = v_saldomfund2 - (v_valor2/v_formulapor);


           if( v_formula <= v_mincon2)
           {
            out.println("</table></center>");
            out.println("<br>");
            out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No se puede realizar la solicitud de retiro por que debe dejar minimo "+NumberFormat.getCurrencyInstance().format(v_mincon2)+" en el contrato.</font></center>");
            out.println("<br>");
            out.println("<PRE>");
            out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
            v_pie= i_pagina.TBFL_PIE;
            out.println("<br>");
            out.println(""+v_pie+"");
            out.close();
           }
          }
          else
          {//error minimo contrato
           out.println("</table></center>");
           out.println("<br>");
           out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>Consulta de minimo por contrato :"+v_mincon+"</font></center>");
           out.println("<br>");
           out.println("<PRE>");
           out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
           v_pie = i_pagina.TBFL_PIE;
           out.println("<br>");
           out.println("<br>");
           out.println(""+v_pie+"");
           out.close();
          }
         }
         out.println("</table></center>");
         /**Verificar que de los aportes sin informaci�pn se toque o no los saldos*/
         if(v_nota)
         {
          session.removeValue("s_nota");
          session.putValue("s_nota","S");
          out.println("<br>");
          out.println("<PRE>");
          out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
          out.println("<center><input type=submit value='Aceptar' ><input type=button value='Regresar'  onclick=' history.go(-1)'><input type=button value = 'Cancelar' name='cancelar2' onclick=' history.go(-5)'></center>");
          v_pie = i_pagina.TBFL_PIE;
          out.println("<br>");
          out.println(""+v_pie+"");
          out.close();
         }
         else
         {
          session.removeValue("s_nota");
          session.putValue("s_nota","N");
          out.println("<br>");
          out.println("<PRE>");
          out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
          out.println("<center><input type=submit value='Aceptar' ><input type=button value='Regresar' onclick=' history.go(-1)'><input type=button value='Cancelar' name='cancelar2' onclick=' history.go(-5)'></center>");
          v_pie = i_pagina.TBFL_PIE;
          out.println("<br>");
          out.println(""+v_pie+"");
          out.close();
         }//se cierra cursor
         v_aportes3.close();
        }
        else if(v_orden2.equals("SMO003"))
             {//si es seleccionado
              //Consultar informaci�n de aportes
//                out.println("Antes de TBCL_FuncionesAs400.TBPL_ProgramaContrato1");
                String v_retorno_programa =new String("");
                String v_programa;
                CallableStatement cs3 = t_tax.prepareCall ( "{? = call TBCL_FuncionesAs400.TBPL_ProgramaContrato(?,?,?,?,?)}");
                cs3.registerOutParameter(1,Types.CHAR);
                cs3.setString(2,v_contrato);
                cs3.setString(3,v_sistema);
                cs3.setString(4,v_usumfund);
                cs3.setString(5,v_passmfund);
                cs3.setString(6,v_libreria);
                cs3.executeUpdate();
                v_retorno_programa = cs3.getString(1);
                cs3.close();
                v_programa = v_retorno_programa.substring(0,v_retorno_programa.indexOf(";",0));

//              #sql v_aportes4 = { values (TB_PKGSELECCION_APORTES.TB_FFILTRO_APORTE(:v_producto,:v_contrato ,:v_ben,:v_cue,:v_pen,:v_canje,:v_fecha,:v_fechapro,:v_ordenselect ))};
//              #sql v_aportes4 = { values (TB_PKGSELECCION_APORTES.TB_FFILTRO_APORTE(:v_producto,:v_contrato ,:v_ben,:v_cue,:v_pen,:v_canje,:v_fecha,:v_fechapro,:v_ordenselect,:v_programa))};
  oracle.jdbc.OracleCallableStatement sJT_stTem = null;
  sqlj.runtime.ref.DefaultContext sJT_ccTem = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (sJT_ccTem==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext sJT_ecTe = ((sJT_ccTem.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sJT_ccTem.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_PKGSELECCION_APORTES.TB_FFILTRO_APORTE( :2 , :3  , :4 , :5 , :6 , :7 , :8 , :9 , :10 , :11 ) \n; END;";
   sJT_stTem = sJT_ecTe.prepareOracleCall(sJT_ccTem,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFinal",theSqlTS);
   if (sJT_ecTe.isNew())
   {
      sJT_stTem.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);
   }
   // set IN parameters
   sJT_stTem.setString(2,v_producto);
   sJT_stTem.setString(3,v_contrato);
   sJT_stTem.setString(4,v_ben);
   sJT_stTem.setString(5,v_cue);
   sJT_stTem.setString(6,v_pen);
   sJT_stTem.setString(7,v_canje);
   sJT_stTem.setString(8,v_fecha);
   sJT_stTem.setString(9,v_fechapro);
   sJT_stTem.setString(10,v_ordenselect);
   sJT_stTem.setString(11,v_programa);
// execute statement
   sJT_ecTe.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_aportes4 = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFinal.i_ejemplo4(new sqlj.runtime.ref.OraRTResultSet(sJT_stTem.getCursor(1)));
  } finally {sJT_ecTe.oracleCloseQuery(); }
              /*FIN Modificaci�n hecha por APC para manejar el nuevo reglamento 2006-06-22*/          
              out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
              out.println("<tr><td class=\"td11\" colspan= 5 ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Saldos Disponibles de los aportes</b></font></center></font></td></tr>");
              out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Fecha del Aporte</b></font></center></font></td>");
              out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Saldo Capital</b></center></font></td>");
              out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Saldo Rendimientos</b></center></font></td>");
              out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Saldo Cuenta Contingente</b></center></font></td></TR>");
              while(v_aportes4.next())
              {
        
               v_conpl2 =v_aportes4.APO_CONSECUTIVO();

               v_fechaefectivapl2 = v_aportes4.APO_FECHA_APORTE();
               //Consultar saldo aporte   
               /*@lineinfo:generated-code*//*@lineinfo:1265^16*/

//  ************************************************************
//  #sql { call  TBPBD_SaldoAporteRet( :v_producto
//                                                  ,:v_contrato
//                                                  ,:v_conpl2
//                                                  ,:v_valunidad
//                                                  ,:v_tipopenaliza
//                                                  ,to_date(:v_fecha,'RRRR-MM-DD')
//                                                  ,:v_cargo1
//                                                  ,:v_cargo2
//                                                  ,:v_cargo3
//                                                  ,:v_cargo4
//                                                  ,:v_programa          
//                                                                           ,:v_salTotCap1
//                                                                                      ,:v_salTotConting
//                                                                                                              ,:v_salTotRend1
//                                                                                                  ,:v_salTotRendN
//                                                                                                  ,:v_salTotRendH
//                                                                                                  ,:v_salTotCap2
//                                                                                                  ,:v_salTotRend2
//                                                  ,:v_porret
//                                                  ,:v_porpen
//                                                  ,:v_porpencap
//                                                  ,:v_porben
//                                                  ,:v_pordis
//                                                 ,:v_coderr
//                                                                                      ,:v_msgErr
//                                                  ,:v_decimal
//                                                 ) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_SaldoAporteRet(  :1  \n                                                , :2  \n                                                , :3  \n                                                , :4  \n                                                , :5  \n                                                ,to_date( :6  ,'RRRR-MM-DD')\n                                                , :7  \n                                                , :8  \n                                                , :9  \n                                                , :10  \n                                                , :11            \n                                                                         , :12  \n                                                                                    , :13  \n                                                                                                            , :14  \n                                                                                                , :15  \n                                                                                                , :16  \n                                                                                                , :17  \n                                                                                                , :18  \n                                                , :19  \n                                                , :20  \n                                                , :21  \n                                                , :22  \n                                                , :23  \n                                               , :24  \n                                                                                    , :25  \n                                                , :26  \n                                               )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_RetiroFinal",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(13,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(14,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(17,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(18,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(19,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(20,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(21,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(22,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(23,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(24,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(25,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,v_producto);
   __sJT_st.setString(2,v_contrato);
   __sJT_st.setDouble(3,v_conpl2);
   __sJT_st.setDouble(4,v_valunidad);
   __sJT_st.setString(5,v_tipopenaliza);
   __sJT_st.setString(6,v_fecha);
   __sJT_st.setString(7,v_cargo1);
   __sJT_st.setString(8,v_cargo2);
   __sJT_st.setString(9,v_cargo3);
   __sJT_st.setString(10,v_cargo4);
   __sJT_st.setString(11,v_programa);
   __sJT_st.setInt(24,v_coderr);
   __sJT_st.setString(25,v_msgErr);
   __sJT_st.setString(26,v_decimal);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_salTotCap1 = __sJT_st.getDouble(12); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotConting = __sJT_st.getDouble(13); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotRend1 = __sJT_st.getDouble(14); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotRendN = __sJT_st.getDouble(15); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotRendH = __sJT_st.getDouble(16); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotCap2 = __sJT_st.getDouble(17); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_salTotRend2 = __sJT_st.getDouble(18); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_porret = __sJT_st.getDouble(19); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_porpen = __sJT_st.getDouble(20); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_porpencap = __sJT_st.getDouble(21); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_porben = __sJT_st.getDouble(22); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_pordis = __sJT_st.getDouble(23); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_coderr = __sJT_st.getInt(24); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   v_msgErr = (String)__sJT_st.getString(25);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1291^49*/

               v_saldo_bruto = v_salTotCap1 + v_salTotRend1;
               v_saldo_neto  = v_salTotCap2 + v_salTotRend2;
               if((v_saldo_bruto > 0 && v_tipov.equals("STV001")) || (v_saldo_neto > 0 && v_tipov.equals("STV002")))
               {
                 v_sumsaldo_bruto = v_sumsaldo_bruto + v_salTotCap1 + v_salTotRend1;
                 v_sumsaldo_neto  =  v_sumsaldo_neto + v_salTotCap2 + v_salTotRend2;
                 //rendimiento bruto
           if(v_naturaleza2.equals("SNR001")&& v_tipov.equals("STV001") && v_respetar.equals("S"))//rendimientos
           {//1
               v_sumren = v_sumren + v_salTotRend1;
           }//1
           else if(v_naturaleza2.equals("SNR001")&& v_tipov.equals("STV001") && v_respetar.equals("N"))//rendimientos
                {//2
                 if(v_salrenbruto2 < 0)
                 {//3
                  if(v_salTotRend1 < 0)
                  {//4
                   v_sumren = v_sumren + v_salTotRend1 + v_salTotCap1 ;
                  }//4
                  else
                  {//4
                   v_sumren = v_sumren +  v_salTotCap1 ;
                  }//4
                 }//3
                 else
                 {//3
                   if(v_salTotRend1 <0 )
                   {
                     v_sumren = v_sumren + v_salTotRend1 + v_salTotCap1 ;
                   }
                   else
                   {
                     v_sumren = v_sumren +  v_salTotCap1 ;
                     v_salrenpositivos += v_salTotRend1;
                   }
                  }//3
                 }//2

           //rendimiento neto
           if(v_naturaleza2.equals("SNR001")&& v_tipov.equals("STV002") && v_respetar.equals("S"))//rendimientos
           {//1
             v_sumren = v_sumren + v_salTotRend2;
           }//1
           else  if(v_naturaleza2.equals("SNR001")&& v_tipov.equals("STV002") && v_respetar.equals("N"))//rendimientos
                 {//2
                   if(v_salrenneto2 < 0)
                   {//3
                    if(v_salTotRend2 < 0)
                    {//4
                      v_sumren = v_sumren + v_salTotRend2 + v_salTotCap2 ;
                    }//4
                    else
                    {//4
                     v_sumren = v_sumren  + v_salTotCap2 ;
                    }//4
                   }//3
                   else
                   {//3
                    if(v_salTotRend2 <0 )
                    {
                      v_sumren = v_sumren + v_salTotRend2 + v_salTotCap2 ;
                    }
                    else
                    {
                     v_sumren = v_sumren +  v_salTotCap2 ;
                     v_salrenpositivos += v_salTotRend2;
                    }
                   }//3
                  }//2

           //proporcional
           if(v_naturaleza2.equals("SNR003") && v_tipov.equals("STV001"))//proporcional
           {
            /*if(v_salTotRend1 < 0)
            {
              v_sumcapren =  v_sumcapren + v_salTotCap1 + v_salTotRend1;
            }
            else
             {
                 v_sumcapren = v_sumcapren +  v_salTotCap1 ;
                 v_salrenpositivos += v_salTotRend1;
             }*/
              if(v_salrenbruto2 < 0)
                 {//3
                  if(v_salTotRend1 < 0)
                  {//4
                   v_sumcapren = v_sumcapren + v_salTotRend1 + v_salTotCap1 ;
                  }//4
                  else
                  {//4
                   v_sumcapren = v_sumcapren +  v_salTotCap1 ;
                  }//4
                 }//3
                 else
                 {//3
                   if(v_salTotRend1 <0 )
                   {
                     v_sumcapren = v_sumcapren + v_salTotRend1 + v_salTotCap1 ;
                   }
                   else
                   {
                     v_sumcapren = v_sumcapren +  v_salTotCap1 ;
                     v_salrenpositivos += v_salTotRend1;
                   }
                  }//3
           }
           else  if(v_naturaleza2.equals("SNR003") && v_tipov.equals("STV002"))//proporcional
                 {
                   /*if( v_salTotRend2 < 0)
                   {
                     v_sumcapren =  v_sumcapren + v_salTotCap2 + v_salTotRend2;
                   }
                   else
                   {
                     v_sumcapren = v_sumcapren +  v_salTotCap2 ;
                     v_salrenpositivos += v_salTotRend2;
                   }*/
                     if(v_salrenneto2 < 0)
                   {//3
                    if(v_salTotRend2 < 0)
                    {//4
                      v_sumcapren = v_sumcapren + v_salTotRend2 + v_salTotCap2 ;
                    }//4
                    else
                    {//4
                     v_sumcapren = v_sumcapren  + v_salTotCap2 ;
                    }//4
                   }//3
                   else
                   {//3
                    if(v_salTotRend2 <0 )
                    {
                      v_sumcapren = v_sumcapren + v_salTotRend2 + v_salTotCap2 ;
                    }
                    else
                    {
                     v_sumcapren = v_sumcapren +  v_salTotCap2 ;
                     v_salrenpositivos += v_salTotRend2;
                    }
                   }//
                 }
           //capital bruto
           if(v_naturaleza2.equals("SNR002") && v_tipov.equals("STV001") && v_respetar.equals("S"))//capital
           {
             if(v_salTotRend1<0)
             {
              v_sumcap =  v_sumcap + v_salTotCap1 + v_salTotRend1;
             }
             else
             {
             v_sumcap =  v_sumcap + v_salTotCap1;
             }
           }
           else if(v_naturaleza2.equals("SNR002") && v_tipov.equals("STV001") && v_respetar.equals("N"))//capital
                {
                 if(v_salrenbruto2 < 0)
                 {
                  if(v_salTotRend1<0)
                  {
                    v_sumcap =  v_sumcap + v_salTotCap1 + v_salTotRend1;
                  }
                  else
                  {
                   v_sumcap =  v_sumcap + v_salTotCap1;
                  }
                 }
                 else
                 {
                   if(v_salTotRend1 < 0)
                   {
                     v_sumcap =  v_sumcap + v_salTotCap1 + v_salTotRend1;
                   }
                   else
                   {
                     v_sumcap = v_sumcap +  v_salTotCap1 ;
                     v_salrenpositivos += v_salTotRend1;
                   }
                 }
                }

           //capital neto
           if(v_naturaleza2.equals("SNR002") && v_tipov.equals("STV002") && v_respetar.equals("S"))//capital
           {
             if(v_salTotRend2<0)
             {
             v_sumcap =  v_sumcap + v_salTotCap2 + v_salTotRend2;
             }
             else
             {
             v_sumcap =  v_sumcap + v_salTotCap2;
             }
           }
           else if(v_naturaleza2.equals("SNR002") && v_tipov.equals("STV002") && v_respetar.equals("N"))//capital
                {//2
                 if(v_salrenneto2 < 0)
                 {//3
                  if(v_salTotRend2<0)
                  {//4
                    v_sumcap =  v_sumcap + v_salTotCap2 + v_salTotRend2;
                  }//4
                  else
                  {//4
                   v_sumcap =  v_sumcap + v_salTotCap2;
                  } //4
                 }//3
                 else
                 {//3
                   if(v_salTotRend2 < 0)
                   {
                    v_sumcap =  v_sumcap + v_salTotCap2 + v_salTotRend2;
                   }
                   else
                   {
                     v_sumcap = v_sumcap +  v_salTotCap2 ;
                     v_salrenpositivos += v_salTotRend2;
                   }
                  }//3
                 }//2

               if(v_tipov.equals("STV001"))
                {
                 v_capnetbru =  v_salTotCap1;
                 v_rennetbru = v_salTotRend1;
                }
                else if(v_tipov.equals("STV002"))
                {
                 v_capnetbru =  v_salTotCap2;
                 v_rennetbru = v_salTotRend2;

                }

                /**Si el aporte es de traslado y noi tiene informaci�n */
                if(v_aportes4.APO_APORTE_TRASLADO().equals("S") && v_aportes4.APO_INFORMACION_TRASLADO().equals("N"))
                {

                 out.println("<tr><td class=\"td12\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><input type=checkbox name= seleccion value = 'T"+v_conpl2+"'>"+v_fechaefectivapl2+"</font></center></td>");
                 out.println("<td class=\"td12\" align=right ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_capnetbru)+"</font></td>");
                 out.println("<td class=\"td12\" align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_rennetbru)+" </font></td>");
                 out.println("<td class=\"td12\" align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_salTotConting)+"</font></td></tr>");
                 v_nota = true;
                }
                else
                {

                 out.println("<tr><td ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><input type=checkbox name= seleccion value = 'A"+v_conpl2+"'>"+v_fechaefectivapl2+"</font></center></td>");
                 out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_capnetbru)+"</font></center></td>");
                 out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_rennetbru)+"</font></center></td>");
                 out.println("<td align=right><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+NumberFormat.getCurrencyInstance().format(v_salTotConting)+"</font></center></td></tr>");
                }

               }
               v_filapo =false;
              }//WHILE
              //si no se encuentran aportes
              if(v_filapo)
              {
               out.println("<tr><td  ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></center></td>");
               out.println("<td><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></center></td>");
               out.println("<td><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></center></td>");
               out.println("<td><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></td></center></tr>");
               out.println("<tr><td colspan= 5><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>No se encontraron aportes para la definici�n del retiro.</font></center></td><tr>");
               out.println("</table></center>");
               out.println("<br>");
               out.println("<center><input type=button value='Cancelar' onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
               v_pie = i_pagina.TBFL_PIE;
               out.println("<br>");
               out.println(""+v_pie+"");
               out.close();
              }
              //si se tienen aportes sin informacion de traslado
              if(v_nota)
              {
               out.println("<br>");
               out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b> Los aportes de color gris, son aportes de traslado sin informaci�n.</font></center>");
               out.println("<br>");
              }
         //validar saldo capital
         if(v_naturaleza2.equals("SNR002"))//capital
         {//1
          if(v_respetar.equals("N"))
          {//2

            if( v_tipov.equals("STV002"))//neto
            {
             if(v_salrenneto2 >= 0)
             {
               if(v_salrenneto2 < v_salrenpositivos)
                 v_sumcap += v_salrenneto2 ;
               else
                 v_sumcap += v_salrenpositivos ;
             }
            }
            else if( v_tipov.equals("STV001"))//BRUTO
            {
              if (v_salrenbruto2 >= 0)
              {
               if(v_salrenbruto2 < v_salrenpositivos)
                 v_sumcap += v_salrenbruto2 ;
               else
                 v_sumcap += v_salrenpositivos ;

              }
            }


            if(v_valor2> v_sumcap)
            {//3
             String v_men = "";
             String v_men2 = "";
             v_men = "sin respetar naturaleza";
             if( v_tipov.equals("STV002"))//neto
             {//4
              if(v_salrenneto2< 0)
              {//5
               v_men2 = "Se tienen rendimientos totales negativos "+NumberFormat.getCurrencyInstance().format(v_salrenneto2)+". Por lo tanto solo es posible retirar "+NumberFormat.getCurrencyInstance().format(v_sumcap)+".";
              }//5
             }//4
             else if( v_tipov.equals("STV001"))//BRUTO
             {//4
              if(v_salrenbruto2< 0)
              {
               v_men2 = "Se tienen rendimientos totales negativos "+NumberFormat.getCurrencyInstance().format(v_salrenbruto2)+". Por lo tanto solo es posible retirar "+NumberFormat.getCurrencyInstance().format(v_sumcap)+".";
              }
             }//4
             out.println("</table></center>");
             out.println("<br>");
             out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza capital sin respetar naturaleza, porque el saldo disponible  es "+NumberFormat.getCurrencyInstance().format(v_sumcap)+" y no alcanza para el retiro."+v_men2+"</font></center>");
             out.println("<br>");
             out.println("<PRE>");
             out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
             v_pie = i_pagina.TBFL_PIE;
             out.println("<br>");
             out.println(""+v_pie+"");
              out.close();
            }//3
          }
          else
          {
             if(v_valor2>v_sumcap)
             {//5
              out.println("</table></center>");
              out.println("<br>");
              out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza capital, porque el saldo disponible  es "+NumberFormat.getCurrencyInstance().format(v_sumcap)+" y no alcanza para el retiro.</font></center>");
              out.println("<br>");
              out.println("<PRE>");
              out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
              v_pie = i_pagina.TBFL_PIE;
              out.println("<br>");
              out.println(""+v_pie+"");
              out.close();
             }//5
           }
         }   //1
         //validar saldo rendimientos
         if(v_naturaleza2.equals("SNR001"))//rendimiento
         {//1
          if(v_respetar.equals("S"))
          {//2
           if( v_tipov.equals("STV002"))//neto
           {//3
            if(v_salrenneto2< 0)
            {//4
             out.println("</table></center>");
             out.println("<br>");
             out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza rendimientos , porque el saldo disponible de rendimientos totales  es "+NumberFormat.getCurrencyInstance().format(v_salrenneto2)+" y no alcanza para el retiro.</font></center>");
             out.println("<br>");
             out.println("<PRE>");
             out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
             v_pie = i_pagina.TBFL_PIE;
             out.println("<br>");
             out.println(""+v_pie+"");
             out.close();
            }//4
            else
            {//4
             if(v_valor2> v_sumren)
             {//5
              out.println("</table></center>");
              out.println("<br>");
              out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza rendimiento, porque el saldo disponible es "+NumberFormat.getCurrencyInstance().format(v_sumren)+" y no alcanza para el retiro.</font></center>");
              out.println("<br>");
              out.println("<PRE>");
              out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
              v_pie = i_pagina.TBFL_PIE;
              out.println("<br>");
              out.println(""+v_pie+"");
              out.close();
             }//5
            }//4
           }//3
           else if( v_tipov.equals("STV001"))//bruto
            {//4
             if(v_salrenbruto2< 0)
             {//5
              out.println("</table></center>");
              out.println("<br>");
              out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza rendimientos , porque el saldo disponible de rendimientos totales  es "+NumberFormat.getCurrencyInstance().format(v_salrenbruto2)+" y no alcanza para el retiro.</font></center>");
              out.println("<br>");
              out.println("<PRE>");
              out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
              v_pie = i_pagina.TBFL_PIE;
              out.println("<br>");
              out.println(""+v_pie+"");
              out.close();
             }//5
             else
             {//5
              if(v_valor2> v_sumren)
              {//6
               out.println("</table></center>");
               out.println("<br>");
               out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza rendimiento, porque el saldo disponible es "+NumberFormat.getCurrencyInstance().format(v_sumren)+" y no alcanza para el retiro.</font></center>");
               out.println("<br>");
               out.println("<PRE>");
               out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
               v_pie = i_pagina.TBFL_PIE;
               out.println("<br>");
               out.println(""+v_pie+"");
               out.close();
              }//6
             }//5
            }//4
          }//2
          else
          {//2
            if( v_tipov.equals("STV002"))//neto
            {
             if (v_salrenneto2 >= 0)
             {
               if(v_salrenneto2 < v_salrenpositivos)
                 v_sumren += v_salrenneto2 ;
               else
                 v_sumren += v_salrenpositivos ;
             }
            }
            else if( v_tipov.equals("STV001"))//BRUTO
            {
             if (v_salrenbruto2 > 0)
             {
               if(v_salrenbruto2 < v_salrenpositivos)
                 v_sumren += v_salrenbruto2 ;
               else
                 v_sumren += v_salrenpositivos ;
             }
            }
           if(v_valor2> v_sumren)
           {//5
            String v_men3 = "";
            if( v_tipov.equals("STV002"))//neto
            {//3
             if(v_salrenneto2 <0)
             {
              v_men3 = "Se tienen rendimientos totales negativos "+NumberFormat.getCurrencyInstance().format(v_salrenneto2)+".Por lo tanto solo es posible retirar "+NumberFormat.getCurrencyInstance().format(v_sumren)+".";
             }
            }
            else  if( v_tipov.equals("STV001"))//neto
            {
             if(v_salrenbruto2 <0)
             {
              v_men3 = "Se tienen rendimientos totales negativos "+NumberFormat.getCurrencyInstance().format(v_salrenbruto2)+".Por lo tanto solo es posible retirar "+NumberFormat.getCurrencyInstance().format(v_sumren)+".";
             }
            }
            out.println("</table></center>");
            out.println("<br>");
            out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza rendimiento sin respetar naturaleza, porque el saldo disponible es "+NumberFormat.getCurrencyInstance().format(v_sumren)+" y no alcanza para el retiro."+v_men3+"</font></center>");
            out.println("<br>");
            out.println("<PRE>");
            out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
            v_pie = i_pagina.TBFL_PIE;
            out.println("<br>");
            out.println(""+v_pie+"");
            out.close();
           }//5
          }//2
         }//1
                //naturaleza proporcional
         if(v_naturaleza2.equals("SNR003"))
         {
            if( v_tipov.equals("STV002"))//neto
            {
              if (v_salrenneto2 >= 0)
              {
               if(v_salrenneto2 < v_salrenpositivos)
                  v_sumcapren  += v_salrenneto2 ;
               else
                 v_sumcapren  += v_salrenpositivos ;
              }
            }
            else if( v_tipov.equals("STV001"))//BRUTO
            {

              if (v_salrenbruto2 >= 0)
              {
               if(v_salrenbruto2 < v_salrenpositivos)
                  v_sumcapren  += v_salrenbruto2 ;
               else
                 v_sumcapren  += v_salrenpositivos ;

              }
            }


          if(v_valor2> v_sumcapren)
          {
           out.println("</table></center>");
           out.println("<br>");
           String v_men4 = "";
           if( v_tipov.equals("STV002"))//neto
           {//3
             if(v_salrenneto2 <0)
             {
              v_men4 = "Se tienen rendimientos totales negativos "+NumberFormat.getCurrencyInstance().format(v_salrenneto2)+".Por lo tanto solo es posible retirar "+NumberFormat.getCurrencyInstance().format(v_sumcapren)+".";
             }
            }
            else  if( v_tipov.equals("STV001"))//BRUTO
            {
             if(v_salrenbruto2 <0)
             {
              v_men4 = "Se tienen rendimientos totales negativos "+NumberFormat.getCurrencyInstance().format(v_salrenbruto2)+".Por lo tanto solo es posible retirar "+NumberFormat.getCurrencyInstance().format(v_sumcapren)+".";
             }
            }
           out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No es posible realizar el retiro con naturaleza proporcional, porque el saldo disponible es "+NumberFormat.getCurrencyInstance().format(v_sumcapren)+" y no alcanza para el retiro. "+v_men4+" </font></center>");
           out.println("<br>");
           out.println("<PRE>");
           out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
           v_pie = i_pagina.TBFL_PIE;
           out.println("<br>");
           out.println(""+v_pie+"");
           out.close();
          }
         }
              //minimo contrato
              if(v_tipov.equals("STV001"))//bruto
              {
               // v_mincon =i_fondos.TBFL_MinimoContrato(v_contrato,v_sistema,v_usumfund, v_passmfund, v_libreria);

               /*
               Modificacion:
               Se a�ade el procedimiento de invocacion a un procedimiento del AS400
               */

               CallableStatement cs = t_tax.prepareCall ( "{? = call TBCL_FUNCIONESAS400.TBFL_MinimoContrato(?,?,?,?,?)}");
               cs.registerOutParameter(1,Types.CHAR);
               cs.setString(2,v_contrato);
               cs.setString(3,v_sistema);
               cs.setString(4,v_usumfund);
               cs.setString(5,v_passmfund);
               cs.setString(6,v_libreria);
               cs.executeUpdate();
               v_mincon = cs.getString(1);
               cs.close();

               /* Final de la modificacion */


               if(!v_mincon.substring(0,5).equals("Error"))//minimo contrato
               {
                v_minimo_contrato = new Double(v_mincon).doubleValue();
                double v_mincon2   = v_minimo_contrato/100;

                v_formula2 = v_saldomfund2 - v_valor2;

                if(v_formula2 <=v_mincon2)
                {
                 out.println("</table></center>");
                 out.println("<br>");
                 out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No se puede realizar la solicitud de retiro por que debe dejar minimo "+NumberFormat.getCurrencyInstance().format(v_mincon2)+" en el contrato.</font></center>");
                 out.println("<br>");
                 out.println("<PRE>");
                 out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
                 v_pie = i_pagina.TBFL_PIE;
                 out.println("<br>");
                 out.println(""+v_pie+"");
                 out.close();
                }
               }
               else
               {//error minimo contrato
                out.println("</table></center>");
                out.println("<br>");
                out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>Consulta de minimo por contrato :"+v_mincon+"</font></center>");
                out.println("<br>");
                out.println("<PRE>");
                out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
                v_pie = i_pagina.TBFL_PIE;
                out.println("<br>");
                out.println("<br>");
                out.println(""+v_pie+"");
                out.close();
               }
              }
              else
              {//neto


               // v_mincon =i_fondos.TBFL_MinimoContrato(v_contrato,v_sistema,v_usumfund, v_passmfund, v_libreria);
               /*
               Modificacion:
               Se a�ade el procedimiento de invocacion a un procedimiento del AS400
               */

               CallableStatement cs = t_tax.prepareCall ( "{? = call TBCL_FUNCIONESAS400.TBFL_MinimoContrato(?,?,?,?,?)}");
               cs.registerOutParameter(1,Types.CHAR);
               cs.setString(2,v_contrato);
               cs.setString(3,v_sistema);
               cs.setString(4,v_usumfund);
               cs.setString(5,v_passmfund);
               cs.setString(6,v_libreria);
               cs.executeUpdate();
               v_mincon = cs.getString(1);
               cs.close();

               /* Final de la modificacion */


               if(!v_mincon.substring(0,5).equals("Error"))//minimo contrato
               {
                v_minimo_contrato = new Double(v_mincon).doubleValue();
                double v_mincon2   = v_minimo_contrato/100;

                v_formulapor = v_saldoneto2  / v_saldobruto2;

                v_formula    = v_saldobruto2 - (v_valor2/v_formulapor);

                if( v_formula <= v_mincon2)
                {
                 out.println("</table></center>");
                 out.println("<br>");
                 out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>No se puede realizar la solicitud de retiro por que debe dejar minimo "+NumberFormat.getCurrencyInstance().format(v_mincon2)+" en el contrato.</font></center>");
                 out.println("<br>");
                 out.println("<PRE>");
                 out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
                 v_pie = i_pagina.TBFL_PIE;
                 out.println("<br>");
                 out.println(""+v_pie+"");
                 out.close();
                }
               }
               else
               {//error minimo contrato
                out.println("</table></center>");
                out.println("<br>");
                out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><b>Nota:</b>Consulta de minimo por contrato :"+v_mincon+"</font></center>");
                out.println("<br>");
                out.println("<PRE>");
                out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
                v_pie = i_pagina.TBFL_PIE;
                out.println("<br>");
                out.println("<br>");
                out.println(""+v_pie+"");
                out.close();
               }
              }
              out.println("</table></center>");

              if(v_nota)
              {
               out.println("<br>");
               out.println("<PRE>");
               out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
               out.println("<center><input type=submit value='Aceptar' ><input type=button value='Regresar' onclick=' history.go(-1)'><input type=button value='Cancelar' name = 'cancelar' onclick=' history.go(-5)'></center>");
               v_pie = i_pagina.TBFL_PIE;
               out.println("<br>");
               out.println(""+v_pie+"");
               out.close();
              }
              else
              {
               out.println("<br>");
               out.println("<PRE>");
               out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
               out.println("<center><input type=submit value='Aceptar' ><input type=button value='Regresar' onclick=' history.go(-1)'><input type=button value='Cancelar' onclick=' history.go(-5)'></center>");
               v_pie = i_pagina.TBFL_PIE;
               out.println("<br>");
               out.println(""+v_pie+"");
               out.close();
              }
              v_aportes4.close();
             }
      }
      else
      {
       v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Detalle del Retiro","","<center>"+v_menesquema+"</center>",false);
       out.println(""+v_pintar+"");
       v_pie = i_pagina.TBFL_PIE;
       out.println("<br>");
       out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-5)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
       out.println(""+v_pie+"");
       out.close();
      }
     }
    }
    else
    {
    v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Detalle del Retiro","","<center>Error de comunicaci�n no se tiene conexi�n con el servidor web,por favor intente de nuevo.</center>",false);
     out.println(""+v_pintar+"");
     v_pie = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-7)'></center>");
     out.println(""+v_pie+"");
     out.close();
    }
   t_tax.close();
   // i_conexion.TBCL_DesconectarBaseDatos();
   ctx10.getConnection().close();
  }
  catch(Exception ex)
  {//manejo de errores
   String v_pintar="";
   String error = ex.toString();
   if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
   {
    v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Detalle del Retiro","","<center>No se tiene comunicaci�n con el servidor de datos  por favor ingrese nuevamente.</center>",false);
   }
   else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
        {
         v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Detalle del Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
        }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_pintar = i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Detalle del Retiro","","<center>Error momentaneo de comunicaci�n con el servidor de datos, por favor intente entrar de nuevo a la opci�n.</center>",false);
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_pintar =  i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Detalle del Retiro","","<center>Error momentaneo de comunicaci�n con el servidor Web, por favor intente entrar de nuevo a la opci�n.</center>",false);
                     }
                  else
                  {
                   v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Detalle del Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                   
                  }
   out.println(""+v_pintar+"");
   ex.printStackTrace(out);
   out.println("<BR>");
   out.println("<center><input type=button value='cancelar'  onclick=' history.go(-5)'></center>");
   String v_pie = i_pagina.TBFL_PIE;
   out.println("<br>");
   out.println(""+v_pie+"");
   out.close();
  }
}
}/*@lineinfo:generated-code*/