
package TBPKT_MODULO_APORTES;

import TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE.Modelo_TB_Referencias;

import TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE.SQL_TB_FREFERENCIAS_FPOB;

import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.TBCL_Validacion;

import oracle.jdbc.driver.*;
import java.sql.Date;
import java.sql.*;

/**
* <font face='Verdana' size='2' color='#324395'>
* CLASE DE PROCEDIMIENTOS ALMACENADOS DEL MÓDULO DE APORTES <br>
* Esta clase será la encargada de mantener todos los stored procedures utilizados
* en el módulo de APORTES, los procedimiento son los siguientes:
*@author      AlfaGL
*@version     1.0 junio/11/2001
*/


public class TBCBD_STORED_PROCEDURES extends Object
{
/**
*<font face='Verdana' size='2' color='#324395'>
*PROCEDIMIENTO ALMACENADO PRINCIPAL QUE ACTUALIZA EL BENEFICIO TRIBUTARIO
*DE TODOS LOS APORTES QUE A LA FECHA DE HOY YA HAYA CUMPLIDO
*CON DICHO BENEFICIO <br>
*Esta clase se encarga de LLAMAR los stored procedures que conforman el submódulo de
*beneficio tributario, el cual a su vez es un stored procedure
*Este procedimiento será el encargado de seleccionar por cada producto los aportes que cumplan ya
*con el beneficio tributario y de actualizarles a cada uno de ellos su benrficio tributario
*@param     ninguno
*@return    ninguno
*/



//-----------------------------------------------------------------------------------------
/*public static void TBPBD_Aporte_TO
(String f_producto,String f_contrato,int consecutivo_hijo,String fecha_p,String fecha_e,String transaccion,String t_t,String c_t,
double apr_capital,double apr_rto,double c_ch,int valor_u_papa,int num_unidades_hijo,String a_certificado,String a_traslado_hijo,
String a_beneficio_hijo,String a_cargue_hijo,String a_informacion_to_hijo,String a_estado_hijo,double saldo_apr_capital,
double saldo_apr_rto,int porcentaje_rto_hijo,double saldo_c_ch,int saldo_num_unidades_hijo,String user,String fa_hijo,
String a_razon_ben_hijo,java.sql.Date fb_hijo,String condicion,String detalle,String trs,String empresa,String afp,
int consecutivo,String contrato_to,String producto_to,String[] estado)
{
try
  {
  estado[0]        = "BIEN";
  Connection c     = new OracleDriver().defaultConnection();
  String t_insert8i_1          =    "INSERT INTO tbaportes"+
                                    "(APO_CON_PRO_CODIGO,"+
                                    "APO_CON_NUMERO,"+
                                    "APO_CONSECUTIVO,"+
                                    "APO_FECHA_PROCESO,"+
                                    "APO_FECHA_EFECTIVA,"+
                                    "APO_COT_REF_TRANSACCION,"+
                                    "APO_COT_REF_TIPO_TRANSACCION,"+
                                    "APO_COT_REF_CLASE_TRANSACCION,"+
                                    "APO_CAPITAL,"+
                                    "APO_RENDIMIENTOS,"+
                                    "APO_CUENTA_CONTINGENTE,"+
                                    "APO_VALOR_UNIDAD,"+
                                    "APO_NUMERO_UNIDADES,"+
                                    "APO_APORTE_CERTIFICADO,"+
                                    "APO_APORTE_TRASLADO,"+
                                    "APO_APORTE_BENEFICIO,"+
                                    "APO_APORTE_CARGUE,"+
                                    "APO_INFORMACION_TRASLADO,"+
                                    "APO_REF_ESTADO,"+
                                    "APO_SALDO_CAPITAL,"+
                                    "APO_RENDIMIENTOS_PENALIZADOS,"+
                                    "APO_PORCENTAJE_RENDIMIENTOS_H,"+
                                    "APO_SALDO_CUENTA_CONTINGENTE,"+
                                    "APO_SALDO_NUMERO_UNIDADES,"+
                                    "APO_USUARIO,"+
                                    "APO_FECHA_APORTE,"+
                                    "APO_RAZON_BENEFICIO,"+
                                    "APO_FECHA_BENEFICIO,"+
                                    "APO_REF_CONDICION_SKA,"+
                                    "APO_DETALLE_CONDICION_SKA,"+
                                    "APO_TRANSACCION,"+
                                    "APO_EMP_GRUPO,"+
                                    "APO_AFP_CODIGO,"+
                                    "APO_APO_CONSECUTIVO,"+
                                    "APO_CON_PRO_CODIGO_TI,"+
                                    "APO_CON_NUMERO_TI)"+
                                    "VALUES("+
                                    "?,?,?,TO_DATE(?,'DD-MON-RRRR'),TO_DATE(?,'RRRR-MM-DD'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,TO_DATE(?,'RRRR-MM-DD'),?,?,?,?,?,?,?,?,?,?)";
  PreparedStatement t_st8i = c.prepareStatement(t_insert8i_1);
  t_st8i.setString(1,f_producto);
  t_st8i.setString(2,f_contrato);
  t_st8i.setInt(3,consecutivo_hijo);
  t_st8i.setString(4,fecha_p);
  t_st8i.setString(5,fecha_e);
  t_st8i.setString(6,transaccion);
  t_st8i.setString(7,t_t);
  t_st8i.setString(8,c_t);
  t_st8i.setDouble(9,apr_capital);
  t_st8i.setDouble(10,apr_rto);
  t_st8i.setDouble(11,c_ch);
  t_st8i.setDouble(12,valor_u_papa);
  t_st8i.setDouble(13,num_unidades_hijo);
  t_st8i.setString(14,a_certificado);
  t_st8i.setString(15,a_traslado_hijo);
  t_st8i.setString(16,a_beneficio_hijo);
  t_st8i.setString(17,a_cargue_hijo);
  t_st8i.setString(18,a_informacion_to_hijo);
  t_st8i.setString(19,a_estado_hijo);
  t_st8i.setDouble(20,apr_capital);
  t_st8i.setDouble(21,apr_rto);
  t_st8i.setInt(22,porcentaje_rto_hijo);
  t_st8i.setDouble(23,c_ch);
  t_st8i.setDouble(24,num_unidades_hijo);
  t_st8i.setString(25,user);
  t_st8i.setString(26,fa_hijo);
  t_st8i.setString(27,a_razon_ben_hijo);
  t_st8i.setDate(28,fb_hijo);
  t_st8i.setString(29,condicion);
  t_st8i.setString(30,detalle);
  t_st8i.setString(31,trs);
  t_st8i.setString(32,empresa);
  t_st8i.setString(33,afp);
  t_st8i.setInt(34,consecutivo);
  t_st8i.setString(35,producto_to);
  t_st8i.setString(36,contrato_to);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){estado[0] =" ERROR1: "+ex;}
}*/
//-----------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------
/**
*<font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO SIRVE PARA ACTUALIZAR EL INDICADOR DE INFORMACION DE TRASLADO
*DE UN APORTE PADRE , CAMBIADO DICHO INDICADOR DE 'N' A 'S'
*@author      AlfaGL
*@version     1.0 junio/11/2001
* <p>
* @param         producto    = PRODUCTO AL QUE PERTENECE EL APORTE PADRE<BR>
*                contrato    = CONTRATO AL QUE PERTENECE EL APORTE PADRE<BR>
*                consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE PADRE<BR>
* @return        estado      = ESTADO FINAL DEL PROCESO DE ACTUALIZACION<BR>
*/
public static void TBPBD_Estado_Aporte_TO(String producto,String contrato,int consecutivo
                                          ,String producto_to,String contrato_to,String[] estado)//IN
{
try
  {
   estado[0]            = "BIEN";
   Connection c      = new OracleDriver().defaultConnection();
   String update8i_1 = "UPDATE TBAPORTES "+
                       "SET "+
                       "APO_INFORMACION_TRASLADO = 'S' "+
                       ",APO_CON_PRO_CODIGO_TI   = ? "+
                       ",APO_CON_NUMERO_TI       = ? "+
                       "WHERE "+
                       "APO_CON_PRO_CODIGO       = ? "+
                       "AND APO_CON_NUMERO       = ? "+
                       "AND APO_CONSECUTIVO      = ? ";
  PreparedStatement t_st8i = c.prepareStatement(update8i_1);
  t_st8i.setString(1,producto_to);
  t_st8i.setString(2,contrato_to);
  t_st8i.setString(3,producto);
  t_st8i.setString(4,contrato);
  t_st8i.setInt(5,consecutivo);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){estado[0] = " ERROR: "+ex;}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO RETORNA LA INFORMACION TOTALIZADA DE
*LOS APORTES A CARGAR ENVIADOS POR MFUN
@param   v_registros_mf = TOTAL DE REGISTROS ENVIADOS POR MFUND <BR>
*        v_ingresos_mf  = TOTAL DE INGRESOS ENVIADOS POR MFUND<BR>
*        v_rtos_mf      = TOTAL DE RENDINIENTOS ENVIADOS POR MFUND<BR>
*        v_cc_mf        = TOTAL DE CUENTA CONTINGENTE ENVIADOS POR MFUND<BR>
*@return estado         = ESTADO FINAL DEL PROCESO DE ACTUALIZACION
*/
public static void TBPBD_Valores_Totales_Aportes(int[] v_registros_mf,double[] v_ingresos_mf,double[] v_rtos_mf,double[] v_cc_mf
                                           ,String[] estado)//OUT
{
  String temp ="";
  int v_registros_mfa    = 0;
try
  {
  estado[0]="BIEN";

  Connection c               = new OracleDriver().defaultConnection();
  //busco los rec 1
  String t_select8i_1        = "SELECT KLQENU,KLSGVA,KLV4VZ,KLV6VZ FROM AJKLCPP@MFUND WHERE KLVGSZ = '01' ";
  PreparedStatement t_st8i_1 = c.prepareStatement(t_select8i_1);
  ResultSet t_rs8i_1;
  t_rs8i_1 = t_st8i_1.executeQuery();
  while(t_rs8i_1.next())
     {
      v_registros_mf[0] = t_rs8i_1.getInt(1);
      v_ingresos_mf[0]  = t_rs8i_1.getDouble(2);
      v_rtos_mf[0]      = t_rs8i_1.getDouble(3);
      v_cc_mf[0]        = t_rs8i_1.getDouble(4);
     }
  temp = temp+"RegT:"+v_registros_mf[0];
  t_rs8i_1.close();
  t_st8i_1.close();
  //acumulo los rec 2
  
  double v_ingresos_mfa  = 0;
  double v_rtos_mfa      = 0;
  double v_cc_mfa        = 0;
  String t_select8i_2        = "SELECT KLSGVA,KLV4VZ,KLV6VZ FROM AJKLCPP@MFUND WHERE KLVGSZ = '02' ";
  //PreparedStatement t_st8i_2 = c.prepareStatement(t_select8i_2);
  //ResultSet t_rs8i_2;
  //t_rs8i_2  = t_st8i_2.executeQuery();
  
  java.sql.Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE,
                           ResultSet.HOLD_CURSORS_OVER_COMMIT);
  ResultSet t_rs8i_2 = stmt.executeQuery("SELECT KLSGVA,KLV4VZ,KLV6VZ FROM AJKLCPP@MFUND WHERE KLVGSZ = '02'");
  t_rs8i_2.absolute(1);
    while (t_rs8i_2.next()) {
      // retrieve and print the values for the current row
      double i = t_rs8i_2.getDouble(1);
      v_ingresos_mfa  += i;
      double s = t_rs8i_2.getDouble(2);
      v_rtos_mfa      += s;
      double f = t_rs8i_2.getDouble(3);
      v_cc_mfa        += f;
      //System.out.println("ROW = " + i + " " + s + " " + f);
    }

  /*while(t_rs8i_2.next())
     {
      v_registros_mfa++;
      v_ingresos_mfa  += t_rs8i_2.getDouble(1);
      temp = temp+"d:"+t_rs8i_2.getDouble(1);
      v_rtos_mfa      += t_rs8i_2.getDouble(2);
      temp = temp+"f:"+t_rs8i_2.getDouble(2);
      v_cc_mfa        += t_rs8i_2.getDouble(3);
      temp = temp+"g:"+t_rs8i_2.getDouble(3)+" ";
     }*/
  
  temp = temp+"5";
  t_rs8i_2.close();
  //t_st8i_2.close();
  temp = temp+"6";
  if(v_registros_mfa==v_registros_mf[0]&&v_ingresos_mfa==v_ingresos_mf[0]&&v_rtos_mfa==v_rtos_mf[0]&&v_cc_mfa==v_cc_mf[0])//{
    estado[0]="OK";
  /*}
          else
          {
            //estado[0]="MAL";
            estado[0]="ERROR2: "+v_registros_mfa+":"+temp+":";
          }*/
  }
catch(Exception ex){estado[0] = "ERROR1: "+v_registros_mfa+":"+temp+":"+ex.getMessage()+ex.toString();}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA VERIFICAR SI UN APORTE TIENE O NO
*RETIROS ASOCIADOS A EL
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
* @return   retiros     = INDICADOR FINAL DEL PROCESO
*/
/*public static void TBPBD_Valida_Retiros_por_Apt(String producto,String contrato,double consecutivo,
                                             String[] retiros)//in
{
try
  {
  Connection c      = new OracleDriver().defaultConnection();
  String select8i_1 = "SELECT "+
                      "APR_RET_CONSECUTIVO "+
                      "FROM "+
                      "TBAPORTES_RETIROS "+
                      ",TBRETIROS "+
                      "WHERE "+
                      "APR_RET_CON_PRO_CODIGO      = RET_CON_PRO_CODIGO "+
                      "AND APR_RET_CON_NUMERO      = RET_CON_NUMERO "+
                      "AND APR_RET_CONSECUTIVO     = RET_CONSECUTIVO "+
                      "AND RET_REF_ESTADO          = 'SER006' "+
                      "AND APR_RET_CON_PRO_CODIGO  = ? "+
                      "AND APR_RET_CON_NUMERO      = ? "+
                      "AND APR_APO_CONSECUTIVO     = ? ";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  t_st8i.setDouble(3,consecutivo);
  ResultSet t_rs8i;
  t_rs8i = t_st8i.executeQuery();
  retiros[0] = "f";
  while(t_rs8i.next())
     {
      retiros[0]   = "v";
     }
  t_rs8i.close();
  t_st8i.close();
  }
catch(Exception ex){retiros[0] = "f";}
}*/
//-----------------------------------------------------------------------------------------
/**
*ESTE PROCEDIMIENTO ALMACENADO ACTUALIZARÁ LA TABLA TBAPORTES RESPECTO A LA PARTE DE BENEFICIO TRIBUTARIO
*@author      AlfaGL
*@version     1.0 junio/11/2001
@param           producto      = PRODUCTO AL QUE PERTENECE EL APORTE PADRE<BR>
*                contrato      = CONTRATO AL QUE PERTENECE EL APORTE PADRE<BR>
*                consecutivo   = CONSECUTIVO PERTENECIENTE AL APORTE PADRE<BR>
*                razon         = RAZON POR LA CUAL SE ESTA REALIZANDO DICHA ACTUALIZACION<BR>
* @return        NIMGUNO
*/
public static void TBPBD_Actualiza_Beneficio(String producto,String contrato,int consecutivo,String razon)
{
try
  {
  Connection c      = new OracleDriver().defaultConnection();
  String update8i_1 = "UPDATE "+
                      "TBAPORTES SET "+
                      "APO_APORTE_BENEFICIO         = 'S' ,"+
                      "APO_SALDO_CUENTA_CONTINGENTE = 0 ,"+
                      "APO_RAZON_BENEFICIO          = ? ,"+
                      "APO_FECHA_BENEFICIO          = TRUNC(SYSDATE) "+
                      "WHERE "+
                      "APO_CON_PRO_CODIGO           = ? "+
                      "AND APO_CON_NUMERO           = ? "+
                      "AND APO_CONSECUTIVO          = ? ";
  PreparedStatement t_st8i = c.prepareStatement(update8i_1);
  t_st8i.setString(1,razon);
  t_st8i.setString(2,producto);
  t_st8i.setString(3,contrato);
  t_st8i.setInt(4,consecutivo);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA DETERMINAR SI
*UNA FECHA DE APORTE PRESENTA O NO BENEFICIO TRIBUTARIO
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           fa          = FECHA DE APORTE A LA CUAL SE LE REALIZARÁ LA VALIDEZ<BR>
* @return   tiene       = INDICADOR DE TENENCIA O NO DE BENEFICIO<BR>
*           fecha_rb    = FECHA REAL DE BENEFICIO CUANDO LA FECHA DEL APORTE PRESENTE BENEFICIO
*/
public static void TBPBD_Con_Beneficio(String producto,String contrato,java.sql.Date fa,//IN
                                       String[] tiene,java.sql.Date[] fecha_rb)
{
try
  {
  Connection c       = new OracleDriver().defaultConnection();
  tiene[0]           = "f";
  String select8i_1  = "SELECT PRO_FECHA_BENEFICIO "+
                       ",PRO_CANTIDAD_TIEMPO "+
                       ",PRO_REF_UNIDAD_TIEMPO "+
                       "FROM TBPRODUCTOS "+
                       "WHERE PRO_CODIGO = ? ";
  java.sql.Date fb   = new java.sql.Date(1);
  int tb             = 0;
  String ref_tb      = " ";
  String descripcion = " ";
  String fc          = " ";
  String estado_fc   = " ";
  PreparedStatement t_st8i_1 = c.prepareStatement(select8i_1);
  t_st8i_1.setString(1,producto);
  ResultSet t_rs8i;
  t_rs8i = t_st8i_1.executeQuery();
  //CAPTURO IX DESEDE PRODUCTOS
  while(t_rs8i.next())
    {
      fb     = t_rs8i.getDate(1);
      tb     = t_rs8i.getInt(2);
      ref_tb = t_rs8i.getString(3);
    }
  //paso tb  a meses siempre y cuando este se represente en años
  if(ref_tb.equals("SUT002"))tb *= 12;
  //realizo calculo de la fecha real de beneficio
  java.sql.Date fecharb = new java.sql.Date(1);
  if(fa.compareTo(fb)<0)
   //quiere decir que la fecha del aporte(fa) es menor que la fecha de beneficio
  //e implica una fecha de beneficio real(fbr) es igual a fb+tb
   {
    //debo llamar un sp que me devuelva el FBR pasandalo como argumento de entrada el TB y la FA
     CallableStatement t_cst8i_3 = c.prepareCall("{ call TBPBD_FRB(?,?,?) }");
     t_cst8i_3.registerOutParameter(3,Types.DATE);
     t_cst8i_3.setDate(1,fb);
     t_cst8i_3.setInt(2,tb);
     t_cst8i_3.execute();
     fecharb = t_cst8i_3.getDate(3);
     t_cst8i_3.close();
  }
  else if(fb.compareTo(fa)<0)
   //quiere decir que la fecha del beneficio(fb) es menor que la fecha de aporte
  //e implica una fecha de beneficio real(fbr) es igual a fa+tb
  {
   //debo llamar un sp que me devuelva el FBR pasandalo como argumento de entrada el TB y la FB
   CallableStatement t_cst8i_3 = c.prepareCall("{ call TBPBD_FRB(?,?,?) }");
   t_cst8i_3.registerOutParameter(3,Types.DATE);
   t_cst8i_3.setDate(1,fa);
   t_cst8i_3.setInt(2,tb);
   t_cst8i_3.execute();
   fecharb = t_cst8i_3.getDate(3);
   t_cst8i_3.close();
  }
  //comparo la fecha real de beneficio con la fecha de hoy y retuno V o F
    java.util.Date hoy = new java.util.Date();
    fecha_rb[0]        = fecharb;
  //si FBR.compareTo(hoy)<0 (FBR mayor que hoy) no debo actualizar el beneficio tributario
 //si  FBR.compareTo(hoy)>0 (FBR menor que hoy) debo actualizar el beneficio tributario(llamada a un sp, IN pro,cont,cons)
//si   FBR.compareTo(hoy)==0 (FBR es igual a hoy) no debo actualizar el beneficio tributario
 if(fecharb.compareTo(hoy)<0)
   {
     tiene[0]    = "v";
   }
}
catch(Exception ex){tiene[0] = " error: "+ex;}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIEMINETO DEVUELVE EL VALOR DE LA CIENTA CONTINGENTE DE UN
*PRODUCTO; DESDE LA TABLA TBPRODUCTOS Y REALIZANDO EL CALCULO CON LA
*CUENTA CONTINGENTE DE UN APORTE ESPECIFICO
* <p>
* @param    producto        = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           capital         = VALOR CAPITAL QUE TIENE EL APORTE   <BR>
* @return   c_c             = VALOR DE LA CUENTA CONTINGENTE      <BR>
*/
public static void TBPBD_Cuenta_Contingente(String producto,double capital,//in
                                      double[] c_c)//ot
{
try
  {
  Connection c      = new OracleDriver().defaultConnection();
  String select8i_1   = "SELECT "+
                        "PRO_MAX_CTA_CONTINGENTE "+
                        "FROM "+
                        "TBPRODUCTOS "+
                        "WHERE "+
                        "PRO_CODIGO = ? ";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  t_st8i.setString(1,producto);
  ResultSet t_rs8i;
  t_rs8i = t_st8i.executeQuery();
  while(t_rs8i.next())
     {
      int porcentaje = t_rs8i.getInt(1);
      c_c[0]         = (capital*porcentaje);
     }
  t_rs8i.close();
  t_st8i.close();
  }
catch(Exception ex){c_c[0] = 0;}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIEMINETO DEVUELVE EL VALOR DE LA CUENTA CONTINGENTE DE UN
*PRODUCTO PARA UNA FECHA;  REALIZANDO EL CALCULO CON LA
*CUENTA CONTINGENTE DE UN APORTE ESPECIFICO
* <p>
* @param    producto        = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           capital         = VALOR CAPITAL QUE TIENE EL APORTE   <BR>
*           f_a             = FECHA DEL APORTE                    <BR>
* @return   c_c             = VALOR DE LA CUENTA CONTINGENTE      <BR>
*/
public static void TBPBD_Cuenta_Contingente_Fecha(String producto,double capital, String f_a,//in
                                      double[] c_c)//ot
{
try
  {
  Connection c      = new OracleDriver().defaultConnection();
  String select8i_1   = "SELECT "+
                        "valor_maximo "+
                        "FROM "+
                        "tbcuenta_contingente "+
                        "WHERE "+
                        "cod_producto = ? AND "+
                        "TO_DATE(?,'RRRR-MM-DD') between fechainicial and nvl(fechafinal,sysdate)";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,f_a);
  ResultSet t_rs8i;
  t_rs8i = t_st8i.executeQuery();
  while(t_rs8i.next())
     {
      int porcentaje = t_rs8i.getInt(1);
      c_c[0]         = (capital*porcentaje);
     }
  t_rs8i.close();
  t_st8i.close();
  }
catch(Exception ex){c_c[0] = 0;}
}

//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA DESACTUALIZAR UN APORTE ESPECIFICO
*EN SU CALIDAD DE BENEFICIO TRIBUTARIO
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
* @return  NINGUNO
*/
public static void TBPBD_DesActualiza_B(String producto,String contrato,int consecutivo)
{
try
  {
   Connection c      = new OracleDriver().defaultConnection();
   String update8i_1 ="UPDATE TBAPORTES SET "+
                      "APO_RAZON_BENEFICIO          = NULL, "+
                      "APO_FECHA_BENEFICIO          = NULL,"+
                      "APO_APORTE_BENEFICIO         = 'N' "+
                      "WHERE APO_CON_PRO_CODIGO     = ? "+
                      "AND APO_CON_NUMERO           = ? "+
                      "AND APO_CONSECUTIVO          = ? ";
  PreparedStatement t_st8i = c.prepareStatement(update8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  t_st8i.setInt(3,consecutivo);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){}
}
//-----------------------------------------------------------------------------------------
/**
*ESTE PROCEDIMIENTO ALMACENADO ACTUALIZARÁ LA TABLA TBAPORTES RESPECTO A LA PARTE DE BENEFICIO TRIBUTARIO
*@author      AlfaGL
*@version     1.0 junio/11/2001
<p>
* @param         producto    = PRODUCTO AL QUE PERTENECE EL APORTE PADRE<BR>
*                contrato    = CONTRATO AL QUE PERTENECE EL APORTE PADRE<BR>
*                consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE PADRE<BR>
*                razon       = RAZON POR LA CUAL SE ESTA REALIZANDO DICHA ACTUALIZACION<BR>
*                frb         = FECHA REAL DE BENEFICIO DEL APORTE EN CUESTION<BR>
* @return        NIMGUNO
*/
public static void TBPBD_Actualiza_B(String producto,String contrato,int consecutivo,String razon,java.sql.Date frb)
{
try
  {
   Connection c      = new OracleDriver().defaultConnection();
   String update8i_1 ="UPDATE TBAPORTES SET "+
                      "APO_SALDO_CUENTA_CONTINGENTE = 0,"+
                      "APO_RAZON_BENEFICIO          = ?, "+
                      "APO_FECHA_BENEFICIO          = TRUNC(SYSDATE),"+
                      "APO_APORTE_BENEFICIO         = 'S' "+
                      "WHERE APO_CON_PRO_CODIGO     = ? "+
                      "AND APO_CON_NUMERO           = ? "+
                      "AND APO_CONSECUTIVO          = ? ";
  PreparedStatement t_st8i = c.prepareStatement(update8i_1);
  t_st8i.setString(1,razon+" FECHA que PASO: "+frb.toString());
  //t_st8i.setDate(2,frb);
  t_st8i.setString(2,producto);
  t_st8i.setString(3,contrato);
  t_st8i.setInt(4,consecutivo);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){}
}
//-----------------------------------------------------------------------------------------
/**
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA ACTUALIZAR LOS VALORES DE
*UN APORTE
*@author      AlfaGL
*@version     1.0 junio/11/2001
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
*           c           = VALOR ACTUAL DEL CAPITAL<BR>
*           cc          = VALOR ACTUAL DE LA CUENTA CONTINGENTE<BR>
*           rto         = VALOR ACTUAL DE LOS RENDIMIENTOS<BR>
*           certificado = VALOR ACTUAL DEL INDICADOR DE CERTIFICACION<BR>
*           f_a         = VALOR ACTUAL DE FECHA DEL APORTE<BR>
* @return  NINGUNO
*/
public static void TBPBD_Actualiza_C(String producto,String contrato,int consecutivo,double c,
                                     double cc,double rto,String certificado,String f_a)//in
{
try
  {
   Connection co     = new OracleDriver().defaultConnection();
   String update8i_1 = "UPDATE TBAPORTES SET "+
                       "APO_CAPITAL              = ? "+
                       ",APO_RENDIMIENTOS        = ? "+
                       ",APO_CUENTA_CONTINGENTE  = ? "+
                       ",APO_APORTE_CERTIFICADO  = ? "+
                       ",APO_FECHA_APORTE        = TO_DATE(?,'RRRR-MM-DD') "+
                       "WHERE APO_CON_PRO_CODIGO = ? "+
                       "AND APO_CON_NUMERO       = ? "+
                       "AND APO_CONSECUTIVO      = ? ";
  PreparedStatement t_st8i = co.prepareStatement(update8i_1);
  t_st8i.setDouble(1,c);
  t_st8i.setDouble(2,rto);
  t_st8i.setDouble(3,cc);
  t_st8i.setString(4,certificado);
  t_st8i.setString(5,f_a);
  t_st8i.setString(6,producto);
  t_st8i.setString(7,contrato);
  t_st8i.setInt(8,consecutivo);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SE ENCARGARÁ DE DECIR SI LA FECHA DE UN APORTE
*ESPECIFICO TIENE O NO BENEFICIO TRIBUTARIO
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           fa          = FECHA DE APORTE A LA CUAL SE LE REALIZARÁ LA VALIDEZ<BR>
*           hoy         = FECHA CON LA CUAL SE BUSCARÁ EL BENEFICIO TRIBUTARIO DE LA ANTERIROR<BR>
* @return   tiene       = INDICADOR DE BENEFICIO O NO DE LA FECHA
*/
public static void TBPBD_BENEFICIO_APORTE_RETIRO(String producto,String contrato,java.sql.Date fa,java.sql.Date hoy,//IN
                                   String[] tiene)
{
try
  {
  Connection c       = new OracleDriver().defaultConnection();
  tiene[0]           = "f";
  String select8i_1  = "SELECT PRO_FECHA_BENEFICIO "+
                       ",PRO_CANTIDAD_TIEMPO "+
                       ",PRO_REF_UNIDAD_TIEMPO "+
                       "FROM TBPRODUCTOS "+
                       "WHERE PRO_CODIGO = ? ";
  java.sql.Date fb   = new java.sql.Date(1);
  int tb             = 0;
  String ref_tb      = " ";
  String descripcion = " ";
  String fc          = " ";
  PreparedStatement t_st8i_1 = c.prepareStatement(select8i_1);
  t_st8i_1.setString(1,producto);
  ResultSet t_rs8i;
  t_rs8i = t_st8i_1.executeQuery();
  //CAPTURO IX DESEDE PRODUCTOS
  while(t_rs8i.next()){fb=t_rs8i.getDate(1);tb=t_rs8i.getInt(2);ref_tb=t_rs8i.getString(3);}
  t_rs8i.close();
  t_st8i_1.close();
  //paso tb  a meses siempre y cuando este se represente en años
  if(ref_tb.equals("SUT002"))tb *= 12;
  //realizo calculo de la fecha real de beneficio
  java.sql.Date fecharb = new java.sql.Date(1);
  if(fa.compareTo(fb)<0)//quiere decir que la fecha del aporte(fa) es menor que la fecha de beneficio
                         //e implica una fecha de beneficio real(fbr) es igual a fb+tb
   {
    //debo llamar un sp que me devuelva el FBR pasandalo como argumento de entrada el TB y la FA
     CallableStatement t_cst8i_3 = c.prepareCall("{ call TBPBD_FRB(?,?,?) }");
     t_cst8i_3.registerOutParameter(3,Types.DATE);
     t_cst8i_3.setDate(1,fb);
     t_cst8i_3.setInt(2,tb);
     t_cst8i_3.execute();
     fecharb = t_cst8i_3.getDate(3);
     t_cst8i_3.close();
      }
  else if(fb.compareTo(fa)<0)//quiere decir que la fecha del beneficio(fb) es menor que la fecha de aporte
                         //e implica una fecha de beneficio real(fbr) es igual a fa+tb
  {
   //debo llamar un sp que me devuelva el FBR pasandalo como argumento de entrada el TB y la FB
   CallableStatement t_cst8i_3 = c.prepareCall("{ call TBPBD_FRB(?,?,?) }");
   t_cst8i_3.registerOutParameter(3,Types.DATE);
   t_cst8i_3.setDate(1,fa);
   t_cst8i_3.setInt(2,tb);
   t_cst8i_3.execute();
   fecharb = t_cst8i_3.getDate(3);
   t_cst8i_3.close();
  }
   //comparo la fecha real de beneficio con la fecha de hoy y retuno V o F
  //si FBR.compareTo(hoy)<0 (FBR mayor que hoy) no debo actualizar el beneficio tributario
 //si  FBR.compareTo(hoy)>0 (FBR menor que hoy) debo actualizar el beneficio tributario(llamada a un sp, IN pro,cont,cons)
//si   FBR.compareTo(hoy)==0 (FBR es igual a hoy) no debo actualizar el beneficio tributario
 if(fecharb.compareTo(hoy)<0){tiene[0] = "v";}}
catch(Exception ex){tiene[0] = "X";}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA  INSERTAR SOBRE
*LA TABLA DE AJUSTES LOS NUEVOS AJUSTES GENERADOS
* <p>
* @param
* @return  estado = ESTADO FINAL DEL PROCESO
*/
public static void TBPBD_Inserta_Ajuste(
                                     String cod_producto,
                                     String num_contrato,
                                     int consecAjus,
                                     int linea,
                                     String v_fecha_proc,
                                     Date v_fecha_choose,
                                     String v_trans,
                                     String v_tipTrans,
                                     String v_clasTrans,
                                     double val_difNetos,
                                     String v_razon,
                                     String usuario,
                                     double val_unidad,
                                     int consecRet,
                                     int consecRetP,
                                     String[] estado
                                  )

{
try
  {
  estado[0]         = "BIEN";
  Connection c      = new OracleDriver().defaultConnection();
  String insert8i_1 = "INSERT INTO "+
                      "TBAJUSTES "+
                      "(AJU_CON_PRO_CODIGO"+
                      ",AJU_CON_NUMERO"+
                      ",AJU_CONSECUTIVO"+
                      ",AJU_LINEA"+
                      ",AJU_FECHA_PROCESO"+
                      ",AJU_FECHA_EFECTIVA"+
                      ",AJU_COT_REF_TRANSACCION"+
                      ",AJU_COT_REF_TIPO_TRANSACCION"+
                      ",AJU_COT_REF_CLASE_TRANSACCION"+
                      ",AJU_VALOR"+
                      ",AJU_RAZON_AJUSTE"+
                      ",AJU_USUARIO"+
                      ",AJU_RETIRO_ORIGINAL"+
                      ",AJU_RETIRO_ACTUAL"+
                      ",AJU_VALOR_UNIDAD) "+
                      "VALUES "+
                      "(?,?,?,?,TO_DATE(?,'RRRR-MM-DD'),?,?,?,?,?,?,?,?,?,?) ";
  PreparedStatement t_st8i = c.prepareStatement(insert8i_1);
  t_st8i.setString(1,cod_producto);
  t_st8i.setString(2,num_contrato);
  t_st8i.setInt(3,consecAjus);
  t_st8i.setInt(4,linea);
  t_st8i.setString(5,v_fecha_proc);
  t_st8i.setDate(6,v_fecha_choose);
  t_st8i.setString(7,v_trans);
  t_st8i.setString(8,v_tipTrans);
  t_st8i.setString(9,v_clasTrans);
  t_st8i.setDouble(10,val_difNetos);
  t_st8i.setString(11,v_razon);
  t_st8i.setString(12,usuario);
  t_st8i.setInt(13,consecRet);
  t_st8i.setInt(14,consecRetP);
  t_st8i.setDouble(15,val_unidad);
  t_st8i.executeUpdate();
  t_st8i.close();
  estado[0] = "BIEN";
  }
catch(Exception ex){estado[0] = ex.toString();}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA  INSERTAR SOBRE
*LA TABLA DE CARGOS AJUSTES LOS NUEVOS AJUSTES GENERADOS
* <p>
* @param
* @return  estado = ESTADO FINAL DEL PROCESO
*/
public static void TBPBD_Inserta_Cargos_Ajustes(String producto,String contrato,int ret_o,int ret_p,int ajuste,int linea
                                                ,String[] estado)
{
try
 {
  estado[0]                = "BIEN";
  Connection c             = new OracleDriver().defaultConnection();
  String select_01         = " SELECT REF_CODIGO FROM TBREFERENCIAS WHERE REF_CODIGO LIKE 'STC%' AND REF_CODIGO<>'STC000' ";
  PreparedStatement t_st01 = c.prepareStatement(select_01);
  ResultSet t_rs01         = t_st01.executeQuery();
  String cargo             = new String("");
  double valor_o           = 0.0;
  double valor_p           = 0.0;
  double valor_t           = 0.0;
  while(t_rs01.next())
  {
    cargo            = t_rs01.getString(1);
    String select_02 = "SELECT CAE_VALOR "+
                       "FROM TBCARGOS_RETIROS "+
                       "WHERE CAE_APR_RET_CON_PRO_CODIGO = ? "+
                       "AND CAE_APR_RET_CON_NUMERO       = ? "+
                       "AND CAE_APR_RET_CONSECUTIVO      = ? "+
                       "AND CAE_REF_CARGO                = ? ";
    PreparedStatement t_st02 = c.prepareStatement(select_02);
    t_st02.setString(1,producto);
    t_st02.setString(2,contrato);
    t_st02.setInt(3,ret_o);
    t_st02.setString(4,cargo);
    ResultSet t_rs02  = t_st02.executeQuery();
    while(t_rs02.next())
     {
       valor_o += t_rs02.getDouble(1);
     }//while t_rs02
    t_rs02.close();
    t_st02.close();
    String select_03 = "SELECT CAE_VALOR "+
                       "FROM TBCARGOS_RETIROS "+
                       "WHERE CAE_APR_RET_CON_PRO_CODIGO = ? "+
                       "AND CAE_APR_RET_CON_NUMERO       = ? "+
                       "AND CAE_APR_RET_CONSECUTIVO      = ? "+
                       "AND CAE_REF_CARGO                = ? ";
    PreparedStatement t_st03 = c.prepareStatement(select_03);
    t_st03.setString(1,producto);
    t_st03.setString(2,contrato);
    t_st03.setInt(3,ret_p);
    t_st03.setString(4,cargo);
    ResultSet t_rs03  = t_st03.executeQuery();
    while(t_rs03.next())
     {
       valor_p += t_rs03.getDouble(1);
     }//while t_rs03
   t_rs03.close();
   t_st03.close();
   valor_t = valor_o - valor_p;
   if(valor_t!=0.0)
   {
     String insert_01 = "INSERT "+
                        "INTO TBCARGOS_AJUSTES( "+
                        "CAA_AJU_CON_PRO_CODIGO "+
                        ",CAA_AJU_CON_NUMERO "+
                        ",CAA_AJU_CONSECUTIVO "+
                        ",CAA_AJU_LINEA "+
                        ",CAA_REF_CARGO "+
                        ",CAA_VALOR) "+
                        "VALUES(?,?,?,?,?,?)";
     PreparedStatement t_st04 = c.prepareStatement(insert_01);
     t_st04.setString(1,producto);
     t_st04.setString(2,contrato);
     t_st04.setInt(3,ajuste);
     t_st04.setInt(4,linea);
     t_st04.setString(5,cargo);
     t_st04.setDouble(6,valor_t);
     t_st04.executeUpdate();
     t_st04.close();
   }
     valor_o = 0;
     valor_p = 0;
     valor_t = 0;
  }//while t_rs01
  t_rs01.close();
  t_st01.close();
  estado[0] = "BIEN";
 }
catch(Exception ex){estado[0] = ex.toString();}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA BUSCAR LA CONDICION
*DE DSIPONIBILIDAD DE UN APORTE ESPECIFICO,
*SE MIRA PRIMERO EN TBEMPRESA_CONTRATO, SE AVERIGUA LA CONDICION Y CON ESTA SE
*CONSULTA EL VALOR EN TBREFERENCIAS SI ES NULL SE AVERIGUAL LA CONDICION DE LA
*EMPRESA Y CON ESTA EL VALOR EN TBREFERENCIAS
* <p>
* @param    grupoid     = GRUPO EMPRESA AL CUAL PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
* @return   condicion_e = CONDICION DE DISPONIBILIDAD DE EPMRESA
*           ref_cod_e   = CODIGO DE LA CONDICION DE DISPONIBILIDAD
*           ref_valor_e = VALOR DE LA CONDICION DE DISPONIBILIDAD
*           ref_ds_e    = DESCRIPCION DE LA CONDICION DE DISPONIBILIDAD
*           estado      = INDICADOR FINAL DEL PROCESO
*/
public static void TBPBD_CONDICION(String grupoid, String contrato, String producto,
                                  String[] condicion_e,String[] ref_cod_e,
                                  int[] ref_valor_e,String[] ref_ds_e,String[] estado)

{
try
{
 estado[0]      = "BIEN";
 ref_ds_e[0]    = " ";
 ref_valor_e[0] = 0;
 condicion_e[0]   = " ";
 ref_cod_e[0]   = " ";
 Connection c = (new oracle.jdbc.driver.OracleDriver()).defaultConnection();
 String select8i_1 = "select "+
                    "REF_CODIGO"+
                    ",EMC_DETALLE_CONDICION"+
                    ",REF_DESCRIPCION"+
                    ",TO_NUMBER(REF_VALOR) "+
                    "FROM "+
                    "TBEMPRESAS_CONTRATOS "+
                    ",tbreferencias "+
                    "where "+
                    "EMC_REF_CONDICION      = REF_CODIGO "+
                    "and EMC_EMP_GRUPO      = ? "+
                    "and EMC_CON_PRO_CODIGO = ? "+
                    "and EMC_CON_NUMERO     = ? ";
 PreparedStatement t_st8i_1 = c.prepareStatement(select8i_1);
 t_st8i_1.setString(1,grupoid);
 t_st8i_1.setString(2,producto);
 t_st8i_1.setString(3,contrato);
 ResultSet t_rs8i_1;
 t_rs8i_1 = t_st8i_1.executeQuery();
 if(t_rs8i_1.next())
  {
   ref_cod_e[0]   = t_rs8i_1.getString(1);
   condicion_e[0] = t_rs8i_1.getString(2);
   ref_ds_e[0]    = t_rs8i_1.getString(3);
   ref_valor_e[0] = t_rs8i_1.getInt(4);
  }
 else
 {
  String select8i_2 = "SELECT "+
                      "REF_CODIGO"+
                      ",EMP_DETALLE_CONDICION"+
                      ",REF_DESCRIPCION"+
                      ",TO_NUMBER(REF_VALOR)"+
                      "FROM "+
                      "TBEMPRESAS"+
                      ",TBREFERENCIAS "+
                      "WHERE "+
                      "EMP_REF_CONDICION=REF_CODIGO "+
                      "AND EMP_GRUPO = ? ";
  PreparedStatement t_st8i_2 = c.prepareStatement(select8i_2);
  t_st8i_2.setString(1,grupoid);
  ResultSet t_rs8i_2;
  t_rs8i_2 = t_st8i_2.executeQuery();
  if(t_rs8i_2.next())
   {
   ref_cod_e[0]   = t_rs8i_2.getString(1);
   condicion_e[0] = t_rs8i_2.getString(2);
   ref_ds_e[0]    = t_rs8i_2.getString(3);
   ref_valor_e[0] = t_rs8i_2.getInt(4);
   }
  t_rs8i_2.close();
  t_st8i_2.close();
 }
 t_rs8i_1.close();
 t_st8i_1.close();
}
catch(Exception exe){estado[0] = "ERROR";}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SELECCIONA EL CAPITAL DE UN APORTE CON
*CONTRATO,PRODUCTO Y CONSECUTIVO
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
* @return   c           = VALOR ACTUAL DEL CAPITAL<BR>
*           cc          = VALOR ACTUAL DE LA CUENTA CONTINGENTE<BR>
*/
/*public static void TBPBD_Capital_Tbaportes_D(String producto,String contrato,double consecutivo,//IN
                                             double[] capital,double[] rtos)//OUT
{
try
  {
  Connection c      = new OracleDriver().defaultConnection();
  String select8i_1 = "SELECT "+
                      "APO_CAPITAL"+
                      ",APO_RENDIMIENTOS "+
                      "FROM TBAPORTES "+
                      "WHERE "+
                      "APO_CON_PRO_CODIGO = ? "+
                      "AND APO_CON_NUMERO = ? "+
                      "AND APO_CONSECUTIVO = ? ";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  t_st8i.setInt(3,consecutivo);
  ResultSet t_rs8i;
  t_rs8i = t_st8i.executeQuery();
  while(t_rs8i.next())
     {
      capital[0]   = t_rs8i.getDouble(1);
      rtos[0]      = t_rs8i.getDouble(2);
     }
  t_rs8i.close();
  t_st8i.close();
  }
catch(Exception ex){capital[0] = 0.0; rtos[0] = 0.0;}
}*/
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA ACTUALIZAR EL DETALLE DE
*DISPONIBILIDAD DE UN APORTE
*<p>
* @param    producto        = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato        = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo     = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
*           detalle         = VALOR ACTUAL DEL DETALLE DE DISPONIBILIDAD DEL  APORTE<BR>
* @return  NINGUNO
*/
/*public static void TBPBD_Actualiza_Detalle_D(String producto,String contrato,int consecutivo,String detalle)//IN
{
try
  {
   Connection c     = new OracleDriver().defaultConnection();
   String update8i_1 = "UPDATE TBAPORTES "+
                       "SET "+
                       "APO_DETALLE_CONDICION_SKA = ? "+
                       "WHERE "+
                       "APO_CON_PRO_CODIGO        = ? "+
                       "AND APO_CON_NUMERO        = ? "+
                       "AND APO_CONSECUTIVO       = ? ";
  PreparedStatement t_st8i = c.prepareStatement(update8i_1);
  t_st8i.setString(1,detalle);
  t_st8i.setString(2,producto);
  t_st8i.setString(3,contrato);
  t_st8i.setInt(4,consecutivo);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){}
}*/
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO BORRA LOS REGISTROS DE DISPONIBILIDAD DE
*UN APORTE ESPECIFICO PARA LAS FECHAS DE DIPONIBILIDAD POSTERIORES A LA
*ESPECIFICADA POR EL CLIENTE O PARA LOS VALORES MAYORES AL NUEVO VALOR DEL
*PORCENTAJE SOBRE  LA TABLA TBDISPONIBILIDADES
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
*           fecha       = FECHA DE DISPONIBILIDAD ACTUAL<BR>
*           porcentaje  = PORCENTAJE DE DISPONIBILIDAD ACTUAL<BR>
* @return   NINGUNO
*/
/*public static void TBPBD_Elimina_D_D(String producto,String contrato,int consecutivo,String fecha,double porcentaje) //IN

{
try
  {
  Connection c      = new OracleDriver().defaultConnection();
  String delete8i_1 = "DELETE FROM TBDISPONIBILIDADES_APORTES "+
                      "WHERE DIA_APO_CON_PRO_CODIGO = ? "+
                      "AND DIA_APO_CON_NUMERO       = ? "+
                      "AND DIA_APO_CONSECUTIVO      = ? "+
                      "AND (DIA_FECHA >= TO_DATE(?,'RRRR-MM-DD') "+
                      "OR DIA_PORCENTAJE_DISPONIBILIDAD > ?)";
  PreparedStatement t_st8i = c.prepareStatement(delete8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  t_st8i.setInt(3,consecutivo);
  t_st8i.setString(4,fecha);
  t_st8i.setDouble(5,porcentaje);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){}
}*/
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA  CALCULAR
*EL VALOR ACUMULADO DE DISPONIBILIDAD DE UN APORTE EN PARTICULAR
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
* @return   sumatoria   = VALOR ACUMUALDO DE DISPONILIBILIDAD DEL APORTES<BR>
*           next_valido = INDICADOR FINAL DE PROCESO
*/
/*public static void TBPBD_Sumatoria_Valores_D(String producto,String contrato,int consecutivo,//IN
                                     double[] sumatoria,String[] next_valido)//OUT
{
try
  {
   Connection c      = new OracleDriver().defaultConnection();
   String select8i_1 = "SELECT "+
                       "SUM(DIA_VALOR_DISPONIBLE) "+
                       "FROM "+
                       "tbdisponibilidades_aportes "+
                       "WHERE "+
                       "DIA_APO_CON_PRO_CODIGO  = ? "+
                       "AND DIA_APO_CON_NUMERO  = ? "+
                       "AND DIA_APO_CONSECUTIVO = ? ";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  ResultSet t_rs8i;
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  t_st8i.setInt(3,consecutivo);
  t_rs8i = t_st8i.executeQuery();
  sumatoria[0]    = 0;
  next_valido[0]  = "f";
  while(t_rs8i.next())
     {
      sumatoria[0]    = t_rs8i.getDouble(1);
      next_valido[0]  = "v";
     }
  t_rs8i.close();
  t_st8i.close();
  }
catch(Exception ex){sumatoria[0] = 0; next_valido[0] = "f";}
}*/
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA ACTUALIZAR LA CONDICION DE
*DISPONIBILIDAD DE UN APORTE
*<p>
* @param    producto        = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato        = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo     = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
*           ref_descripcion = VALOR ACTUAL DE LA CONDICION DE DISPONIBILIDAD DEL APORTE<BR>
* @return  NINGUNO
*/
/*public static void TBPBD_Actualiza_Condicion_D(String producto,String contrato,int consecutivo,String ref_descripcion)//IN
{
try
  {
   Connection c     = new OracleDriver().defaultConnection();
   String update8i_1 = "UPDATE TBAPORTES "+
                       "SET "+
                       "APO_REF_CONDICION_SKA     = ? "+
                       "WHERE "+
                       "APO_CON_PRO_CODIGO        = ? "+
                       "AND APO_CON_NUMERO        = ? "+
                       "AND APO_CONSECUTIVO       = ? ";
  PreparedStatement t_st8i = c.prepareStatement(update8i_1);
  t_st8i.setString(1,ref_descripcion);
  t_st8i.setString(2,producto);
  t_st8i.setString(3,contrato);
  t_st8i.setInt(4,consecutivo);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){}
}*/
//-----------------------------------------------------------------------------------------
/**
*ESTE PROCEDIMIENTO ALMACENADO INSERTA SOBRE NUEVAS
*CONDICIONES DE DISPONIBILIDAD
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
*           fecha       = VALOR ACTUAL DE LA FECHA DE DISPONIBILIDADL<BR>
*           porcentaje  = VALOR ACTUAL DEL PORCENTAJE DE DISPONIBILIDAD<BR>
*           valor       = VALOR ACTUAL DE LA DISPONIBILIDAD<BR>
* @return   estado      = ESTADO FINAL DEL PROCESO
*/
/*public static void TBPBD_Inserta_Disponibilidad_D(String producto,String contrato,int consecutivo,String fecha,String porcentaje,
                                                  double valor,String[] estado) //IN

{
try
  {
  estado[0]         = "BIEN";
  Connection c      = new OracleDriver().defaultConnection();
  String insert8i_1 = "INSERT INTO TBDISPONIBILIDADES_APORTES "+
                      "(DIA_APO_CON_PRO_CODIGO,"+
                      "DIA_APO_CON_NUMERO,"+
                      "DIA_APO_CONSECUTIVO,"+
                      "DIA_FECHA,"+
                      "DIA_PORCENTAJE_DISPONIBILIDAD,"+
                      "DIA_VALOR_DISPONIBLE) "+
                      "VALUES (?,?,?,TO_DATE(?,'RRRR-MM-DD'),?,?)";
  PreparedStatement t_st8i = c.prepareStatement(insert8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  t_st8i.setInt(3,consecutivo);
  t_st8i.setString(4,fecha);
  t_st8i.setString(5,porcentaje);
  t_st8i.setDouble(6,valor);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){estado[0] = "ERORR "+ex;}
}*/
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA HALLAE DESDE REFERENCIAS
*LA RUTA EN DONDE SE HALLARAN LOS ARCHIVOS PLANOS QUE CONTENDRAN LOS
*APORTES DE TRASLADO EXTERNO
* <p>
* @param   NINGUNO
* @return  ruta    = UBICACION FISICA DE LOS ARCHIVOS PLANOS DE TRASLADO EXTERNO
*/
public static void TBPBD_Ruta_File(String[] ruta)
{
try
{
 Connection c      = new OracleDriver().defaultConnection();
 String select8i_1 = "SELECT "+
                     "REF_DESCRIPCION "+
                     "FROM TBREFERENCIAS "+
                     "WHERE REF_CODIGO = 'STE001' ";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  ResultSet t_rs8i;
  t_rs8i     = t_st8i.executeQuery();
  ruta[0]  = "ERROR";
  while(t_rs8i.next())
   {
    ruta[0]  = t_rs8i.getString(1);
   }
   t_rs8i.close();
   t_st8i.close();
}
catch(Exception ex){ruta[0]  = "ERROR";}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA REALIZAR LA VALIDEZ DE UNA
*AFP EN EL SISTEMA TAXB
<p>
* @param   afp       = NIT DE LA AFP A LA CUAL SE LE REALIZARA LA VALIDEZ<br>
* @return  existe    = INDICADOR DE EXISTENCIA O NO
*/
public static void TBPBD_Afp_Existe(String afp,String[] existe)
{
try
{
 Connection c      = new OracleDriver().defaultConnection();
 String select8i_1 = "SELECT "+
                     "INT_NIT_AFP_ORIGEN "+
                     "FROM TBINTERFACE_TRASLADOS "+
                     "WHERE INT_NIT_AFP_ORIGEN = ?";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  t_st8i.setString(1,afp);
  ResultSet t_rs8i;
  t_rs8i     = t_st8i.executeQuery();
  existe[0]  = "F";
  while(t_rs8i.next())
   {
    existe[0]  = "V";
    break;
   }
   t_rs8i.close();
   t_st8i.close();
}
catch(Exception ex){existe[0] = "O";}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA  INSERTAR LOS APORTES
*QUE LLEGAN A TAX COMO DE TRASLADO EXTERNO SOBRE DICHA INTERFACE
* <p>
* @param   consecutivo = CONSECUTIVO DEL APORTE EXTERNO A CARGAR
*          t_i         = TIPO DE IDENTIFICACION DEL CLIENTE DEL APORTE EXTERNO A CARGAR
*          n_i         = NUMERO DE IDENTIFICACION DEL CLIENTE DEL APORTE EXTERNO A CARGAR
*          f_c         = FECHA DE CONSIGNACION DEL APORTE EXTERNO A CARGAR
*          t_a         = TIPO DE APORTE A CARGAR
*          v_h         = VALOR HISTORICO DEL APORTE EXTERNO A CARGAR
*          r_c         = RETENCION CONTINGENTE DEL APORTE EXTERNO A CARGAR
*          rtos        = RENDIMIENTOS DEL APORTE EXTERNO A CARGAR
*          afp_o       = CODIGO AFP ORIGEN DEL APORTE EXTERNO A CARGAR
* @return  msj         = INDICADOR ESTADO FINAL PROCESO
*/
/*public static void TBPBD_Inserta_Externo(int consecutivo,String t_i,int n_i,String f_c,String t_a,
                                         double v_h,double r_c,double rtos,String afp_o,String[] msj)

{
try
  {
  Connection c      = new OracleDriver().defaultConnection();
  msj[0] = "BIEN";
  if(consecutivo==1)
   {
     String delete8i_1 = "DELETE TBINTERFACE_TRASLADOS WHERE INT_NIT_AFP_ORIGEN = ? ";
     PreparedStatement t_st8i_0 = c.prepareStatement(delete8i_1);
     t_st8i_0.setString(1,afp_o);
     t_st8i_0.executeUpdate();
     t_st8i_0.close();
   }
  String insert8i_1 = "INSERT INTO TBINTERFACE_TRASLADOS "+
                      "(INT_FECHA,"+
                      "INT_NIT_AFP_ORIGEN,"+
                      "INT_CONSECUTIVO,"+
                      "INT_TIPO_IDENTIFICACION,"+
                      "INT_NUMERO_IDENTIFICACION,"+
                      "INT_FECHA_CONSIGNACION,"+
                      "INT_TIPO_APORTE,"+
                      "INT_VALOR_HISTORICO,"+
                      "INT_RETENCION_CONTINGENTE,"+
                      "INT_RENDIMIENTOS)"+
                      "VALUES (TRUNC(SYSDATE),?,?,?,?,TO_DATE(?,'RRRRMMDD'),?,?,?,?)";
  PreparedStatement t_st8i = c.prepareStatement(insert8i_1);
  t_st8i.setString(1,afp_o);
  t_st8i.setInt(2,consecutivo);
  t_st8i.setString(3,t_i);
  t_st8i.setInt(4,n_i);
  t_st8i.setString(5,f_c);
  t_st8i.setString(6,t_a);
  t_st8i.setDouble(7,v_h);
  t_st8i.setDouble(8,r_c);
  t_st8i.setDouble(9,rtos);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){msj[0] = "ERROR";}
}
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA BUSCAR LA CONDICION
*DE DSIPONIBILIDAD DE UN APORTE ESPECIFICO,
*SE MIRA PRIMERO EN TBEMPRESA_CONTRATO, SE AVERIGUA LA CONDICION Y CON ESTA SE
*CONSULTA EL VALOR EN TBREFERENCIAS SI ES NULL SE AVERIGUAL LA CONDICION DE LA
*EMPRESA Y CON ESTA EL VALOR EN TBREFERENCIAS
* <p>
* @param    grupoid     = GRUPO EMPRESA AL CUAL PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
* @return   condicion_e = CONDICION DE DISPONIBILIDAD DE EPMRESA
*           ref_cod_e   = CODIGO DE LA CONDICION DE DISPONIBILIDAD
*           ref_valor_e = VALOR DE LA CONDICION DE DISPONIBILIDAD
*           ref_ds_e    = DESCRIPCION DE LA CONDICION DE DISPONIBILIDAD
*           estado      = INDICADOR FINAL DEL PROCESO
*/

public static void TBPBD_CONDICION_EXTERNOS(String grupoid, String contrato, String producto,
                                            String[] condicion_e,String[] ref_cod_e,String[] ref_valor_e,
                                            String[] ref_ds_e,String[] estado)

{
try
{
 estado[0]       = "BIEN";
 ref_ds_e[0]    = " ";
 ref_valor_e[0] = "";
 condicion_e[0]   = " ";
 ref_cod_e[0]   = " ";
 Connection c = (new oracle.jdbc.driver.OracleDriver()).defaultConnection();
 String select8i_1 = "select "+
                    "REF_CODIGO"+
                    ",EMC_DETALLE_CONDICION"+
                    ",REF_DESCRIPCION"+
                    ",TO_NUMBER(REF_VALOR) "+
                    "FROM "+
                    "TBEMPRESAS_CONTRATOS "+
                    ",tbreferencias "+
                    "where "+
                    "EMC_REF_CONDICION      = REF_CODIGO "+
                    "and EMC_EMP_GRUPO      = ? "+
                    "and EMC_CON_PRO_CODIGO = ? "+
                    "and EMC_CON_NUMERO     = ? ";
 PreparedStatement t_st8i_1 = c.prepareStatement(select8i_1);
 t_st8i_1.setString(1,grupoid);
 t_st8i_1.setString(2,producto);
 t_st8i_1.setString(3,contrato);
 ResultSet t_rs8i_1;
 t_rs8i_1 = t_st8i_1.executeQuery();
 if(t_rs8i_1.next())
  {
   ref_cod_e[0]   = t_rs8i_1.getString(1);
   condicion_e[0] = t_rs8i_1.getString(2);
   ref_ds_e[0]    = t_rs8i_1.getString(3);
   ref_valor_e[0] = t_rs8i_1.getString(4);
  }
 else
 {
  String select8i_2 = "SELECT "+
                      "REF_CODIGO"+
                      ",EMP_DETALLE_CONDICION"+
                      ",REF_DESCRIPCION"+
                      ",TO_NUMBER(REF_VALOR)"+
                      "FROM "+
                      "TBEMPRESAS"+
                      ",TBREFERENCIAS "+
                      "WHERE "+
                      "EMP_REF_CONDICION=REF_CODIGO "+
                      "AND EMP_GRUPO = ? ";
  PreparedStatement t_st8i_2 = c.prepareStatement(select8i_2);
  t_st8i_2.setString(1,grupoid);
  ResultSet t_rs8i_2;
  t_rs8i_2 = t_st8i_2.executeQuery();
  if(t_rs8i_2.next())
   {
   ref_cod_e[0]   = t_rs8i_2.getString(1);
   condicion_e[0] = t_rs8i_2.getString(2);
   ref_ds_e[0]    = t_rs8i_2.getString(3);
   ref_valor_e[0] = t_rs8i_2.getString(4);
   }
  t_rs8i_2.close();
  t_st8i_2.close();
 }
 t_rs8i_1.close();
 t_st8i_1.close();
}
catch(Exception ex){estado[0] = " ERROR: "+ex;}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA VERIFICAR QUE UN PRODUCTO-CONTRATO
*TENGA ASIGNADO UN SOLO GRUPO EMPRESA
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
* @return   empresax    = GRUPO EMPRESA CUANDO EL NUMERO DE ASIGNACIONES A UN CONTRATO
*                         Y UN PRODCUTO ES SOLO UNO, EN CASO CONTRARIO LA PALABRA nula
*           estado      = INDICADOR FINAL DEL PROCESO
*/
public static void TBPBD_Empresa_Externos(String producto,String contrato,String[] empresax,String[] estado)
{
try
{
 estado[0]         = "BIEN";
 Connection c      = new OracleDriver().defaultConnection();
 String select8i_1 = "SELECT "+
                    "COUNT(EMC_EMP_GRUPO) "+
                    ",EMC_EMP_GRUPO "+
                    "FROM "+
                    "TBEMPRESAS_CONTRATOS "+
                    "WHERE "+
                    "EMC_CON_PRO_CODIGO = ? "+
                    "AND EMC_CON_NUMERO = ? "+
                    "GROUP BY EMC_EMP_GRUPO ";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  ResultSet t_rs8i;
  t_rs8i       = t_st8i.executeQuery();
  int contador = 0;
  while(t_rs8i.next())
   {
    contador    = t_rs8i.getInt(1);
    empresax[0] = t_rs8i.getString(2);
   }
  if(contador!=1)
  empresax[0]  = "nula";
  t_rs8i.close();
  t_st8i.close();
}
catch(Exception ex){estado[0] = " ERROR: "+ex;}
}
//-----------------------------------------------------------------------------------------
/**
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA INSERTAR LOS APORTES
* <p>
* @param 
* @return  estado = ESTADO FINAL DEL PROCESO
*/
/*public static void TBPBD_Inserta_Aporte_Hijo
(String f_producto,String f_contrato,int consecutivo_hijo,String fecha_p,String fecha_e,String transaccion,String t_t,String c_t,
double apr_capital,double apr_rto,double c_ch,int valor_u_papa,int num_unidades_hijo,String a_certificado,String a_traslado_hijo,
String a_beneficio_hijo,String a_cargue_hijo,String a_informacion_to_hijo,String a_estado_hijo,double saldo_apr_capital,double saldo_apr_rto,
int porcentaje_rto_hijo,double saldo_c_ch,int saldo_num_unidades_hijo,String user,String fa_hijo,String a_razon_ben_hijo,java.sql.Date fb_hijo,
String condicion,String detalle,String trs,String empresa,String afp,int consecutivo,String[] estado)
{
try
  {
  estado[0]        = "BIEN";
  Connection c     = new OracleDriver().defaultConnection();
  String t_insert8i_1          =    "INSERT INTO tbaportes"+
                                    "(APO_CON_PRO_CODIGO,"+
                                    "APO_CON_NUMERO,"+
                                    "APO_CONSECUTIVO,"+
                                    "APO_FECHA_PROCESO,"+
                                    "APO_FECHA_EFECTIVA,"+
                                    "APO_COT_REF_TRANSACCION,"+
                                    "APO_COT_REF_TIPO_TRANSACCION,"+
                                    "APO_COT_REF_CLASE_TRANSACCION,"+
                                    "APO_CAPITAL,"+
                                    "APO_RENDIMIENTOS,"+
                                    "APO_CUENTA_CONTINGENTE,"+
                                    "APO_VALOR_UNIDAD,"+
                                    "APO_NUMERO_UNIDADES,"+
                                    "APO_APORTE_CERTIFICADO,"+
                                    "APO_APORTE_TRASLADO,"+
                                    "APO_APORTE_BENEFICIO,"+
                                    "APO_APORTE_CARGUE,"+
                                    "APO_INFORMACION_TRASLADO,"+
                                    "APO_REF_ESTADO,"+
                                    "APO_SALDO_CAPITAL,"+
                                    "APO_RENDIMIENTOS_PENALIZADOS,"+
                                    "APO_PORCENTAJE_RENDIMIENTOS_H,"+
                                    "APO_SALDO_CUENTA_CONTINGENTE,"+
                                    "APO_SALDO_NUMERO_UNIDADES,"+
                                    "APO_USUARIO,"+
                                    "APO_FECHA_APORTE,"+
                                    "APO_RAZON_BENEFICIO,"+
                                    "APO_FECHA_BENEFICIO,"+
                                    "APO_REF_CONDICION_SKA,"+
                                    "APO_DETALLE_CONDICION_SKA,"+
                                    "APO_TRANSACCION,"+
                                    "APO_EMP_GRUPO,"+
                                    "APO_AFP_CODIGO,"+
                                    "APO_APO_CONSECUTIVO)"+
                                    "VALUES("+
                                    "?,?,?,TO_DATE(?,'DD-MON-YY'),TO_DATE(?,'DD-MON-YY'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  PreparedStatement t_st8i = c.prepareStatement(t_insert8i_1);
  t_st8i.setString(1,f_producto);
  t_st8i.setString(2,f_contrato);
  t_st8i.setInt(3,consecutivo_hijo);
  t_st8i.setString(4,fecha_p);
  t_st8i.setString(5,fecha_e);
  t_st8i.setString(6,transaccion);
  t_st8i.setString(7,t_t);
  t_st8i.setString(8,c_t);
  t_st8i.setDouble(9,apr_capital);
  t_st8i.setDouble(10,apr_rto);
  t_st8i.setDouble(11,c_ch);
  t_st8i.setDouble(12,valor_u_papa);
  t_st8i.setDouble(13,num_unidades_hijo);
  t_st8i.setString(14,a_certificado);
  t_st8i.setString(15,a_traslado_hijo);
  t_st8i.setString(16,a_beneficio_hijo);
  t_st8i.setString(17,a_cargue_hijo);
  t_st8i.setString(18,a_informacion_to_hijo);
  t_st8i.setString(19,a_estado_hijo);
  t_st8i.setDouble(20,apr_capital);
  t_st8i.setDouble(21,apr_rto);
  t_st8i.setInt(22,porcentaje_rto_hijo);
  t_st8i.setDouble(23,c_ch);
  t_st8i.setDouble(24,num_unidades_hijo);
  t_st8i.setString(25,user);
  t_st8i.setString(26,fa_hijo);
  t_st8i.setString(27,a_razon_ben_hijo);
  t_st8i.setDate(28,fb_hijo);
  t_st8i.setString(29,condicion);
  t_st8i.setString(30,detalle);
  t_st8i.setString(31,trs);
  t_st8i.setString(32,empresa);
  t_st8i.setString(33,afp);
  t_st8i.setInt(34,consecutivo);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){estado[0] =" ERROR1: "+ex;}
}
//-----------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------
/**
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA INSERTAR LOS APORTES
* <p>
* @param
* @return  estado = ESTADO FINAL DEL PROCESO
*/
public static void TBPBD_Inserta_Aporte_Hijo_TO
(String f_producto,String f_contrato,int consecutivo_hijo,String fecha_p,String fecha_e,String transaccion,String t_t,String c_t,
double apr_capital,double apr_rto,double c_ch,int valor_u_papa,int num_unidades_hijo,String a_certificado,String a_traslado_hijo,
String a_beneficio_hijo,String a_cargue_hijo,String a_informacion_to_hijo,String a_estado_hijo,double saldo_apr_capital,
double saldo_apr_rto,int porcentaje_rto_hijo,double saldo_c_ch,int saldo_num_unidades_hijo,String user,String fa_hijo,
String a_razon_ben_hijo,java.sql.Date fb_hijo,String condicion,String detalle,String trs,String empresa,String afp,
int consecutivo,String contrato_to,String producto_to,String[] estado)
{
try
  {
  estado[0]        = "BIEN";
  Connection c     = new OracleDriver().defaultConnection();
  String t_insert8i_1          =    "INSERT INTO tbaportes"+
                                    "(APO_CON_PRO_CODIGO,"+
                                    "APO_CON_NUMERO,"+
                                    "APO_CONSECUTIVO,"+
                                    "APO_FECHA_PROCESO,"+
                                    "APO_FECHA_EFECTIVA,"+
                                    "APO_COT_REF_TRANSACCION,"+
                                    "APO_COT_REF_TIPO_TRANSACCION,"+
                                    "APO_COT_REF_CLASE_TRANSACCION,"+
                                    "APO_CAPITAL,"+
                                    "APO_RENDIMIENTOS,"+
                                    "APO_CUENTA_CONTINGENTE,"+
                                    "APO_VALOR_UNIDAD,"+
                                    "APO_NUMERO_UNIDADES,"+
                                    "APO_APORTE_CERTIFICADO,"+
                                    "APO_APORTE_TRASLADO,"+
                                    "APO_APORTE_BENEFICIO,"+
                                    "APO_APORTE_CARGUE,"+
                                    "APO_INFORMACION_TRASLADO,"+
                                    "APO_REF_ESTADO,"+
                                    "APO_SALDO_CAPITAL,"+
                                    "APO_RENDIMIENTOS_PENALIZADOS,"+
                                    "APO_PORCENTAJE_RENDIMIENTOS_H,"+
                                    "APO_SALDO_CUENTA_CONTINGENTE,"+
                                    "APO_SALDO_NUMERO_UNIDADES,"+
                                    "APO_USUARIO,"+
                                    "APO_FECHA_APORTE,"+
                                    "APO_RAZON_BENEFICIO,"+
                                    "APO_FECHA_BENEFICIO,"+
                                    "APO_REF_CONDICION_SKA,"+
                                    "APO_DETALLE_CONDICION_SKA,"+
                                    "APO_TRANSACCION,"+
                                    "APO_EMP_GRUPO,"+
                                    "APO_AFP_CODIGO,"+
                                    "APO_APO_CONSECUTIVO,"+
                                    "APO_CON_PRO_CODIGO_TI,"+
                                    "APO_CON_NUMERO_TI)"+
                                    "VALUES("+
                                    "?,?,?,TO_DATE(?,'DD-MON-YY'),TO_DATE(?,'DD-MON-YY'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  PreparedStatement t_st8i = c.prepareStatement(t_insert8i_1);
  t_st8i.setString(1,f_producto);
  t_st8i.setString(2,f_contrato);
  t_st8i.setInt(3,consecutivo_hijo);
  t_st8i.setString(4,fecha_p);
  t_st8i.setString(5,fecha_e);
  t_st8i.setString(6,transaccion);
  t_st8i.setString(7,t_t);
  t_st8i.setString(8,c_t);
  t_st8i.setDouble(9,apr_capital);
  t_st8i.setDouble(10,apr_rto);
  t_st8i.setDouble(11,c_ch);
  t_st8i.setDouble(12,valor_u_papa);
  t_st8i.setDouble(13,num_unidades_hijo);
  t_st8i.setString(14,a_certificado);
  t_st8i.setString(15,a_traslado_hijo);
  t_st8i.setString(16,a_beneficio_hijo);
  t_st8i.setString(17,a_cargue_hijo);
  t_st8i.setString(18,a_informacion_to_hijo);
  t_st8i.setString(19,a_estado_hijo);
  t_st8i.setDouble(20,apr_capital);
  t_st8i.setDouble(21,apr_rto);
  t_st8i.setInt(22,porcentaje_rto_hijo);
  t_st8i.setDouble(23,c_ch);
  t_st8i.setDouble(24,num_unidades_hijo);
  t_st8i.setString(25,user);
  t_st8i.setString(26,fa_hijo);
  t_st8i.setString(27,a_razon_ben_hijo);
  t_st8i.setDate(28,fb_hijo);
  t_st8i.setString(29,condicion);
  t_st8i.setString(30,detalle);
  t_st8i.setString(31,trs);
  t_st8i.setString(32,empresa);
  t_st8i.setString(33,afp);
  t_st8i.setInt(34,consecutivo);
  t_st8i.setString(35,producto_to);
  t_st8i.setString(36,contrato_to);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){estado[0] =" ERROR1: "+ex;}
}
//-----------------------------------------------------------------------------------------
/**
*ESTE PROCEDIMIENTO ALMACENADO INSERTA SOBRE NUEVAS
*CONDICIONES DE DISPONIBILIDAD
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
*           fecha       = VALOR ACTUAL DE LA FECHA DE DISPONIBILIDADL<BR>
*           porcentaje  = VALOR ACTUAL DEL PORCENTAJE DE DISPONIBILIDAD<BR>
*           valor       = VALOR ACTUAL DE LA DISPONIBILIDAD<BR>
* @return   estado      = ESTADO FINAL DEL PROCESO
*/
/*public static void TBPBD_Inserta_Disponibilidad_E(String producto,String contrato,int consecutivo,String fecha,String porcentaje,
                                              double valor,String[] estado) //IN

{
try
  {
  estado[0]         = "BIEN";
  Connection c      = new OracleDriver().defaultConnection();
  String insert8i_1 = "INSERT INTO TBDISPONIBILIDADES_APORTES "+
                      "(DIA_APO_CON_PRO_CODIGO,"+
                      "DIA_APO_CON_NUMERO,"+
                      "DIA_APO_CONSECUTIVO,"+
                      "DIA_FECHA,"+
                      "DIA_PORCENTAJE_DISPONIBILIDAD,"+
                      "DIA_VALOR_DISPONIBLE) "+
                      "VALUES (?,?,?,TO_DATE(?,'DD-MON-YY'),?,?)";
  PreparedStatement t_st8i = c.prepareStatement(insert8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  t_st8i.setInt(3,consecutivo);
  t_st8i.setString(4,fecha);
  t_st8i.setString(5,porcentaje);
  t_st8i.setDouble(6,valor);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){estado[0] = " ERROR0: "+ex;}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA DETERMINAR SI
*UN APORTE PRESENTA O NO BENEFICIO TRIBUTARIO
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
* @return   tiene       = INDICADOR DE TENENCIA O NO DE BENEFICIO<BR>
*           estado      = INDICADOR FINAL DE PROCESO
*/
public static void TBPBD_Tiene_Beneficio_Externos(String producto,String contrato,int consecutivo,//IN
                                                  String[] tiene,String[] estado)
{
try
  {
     //PHTM 2016-02-09 Se cambia metodo de la conexion c por la de DriverManager que utiliza el archivo Connection.Properties
     TBCL_Validacion       i_valusu = new TBCL_Validacion();/**Instancia de la clase TBCL_Validacion*/
     String[] v_valusu = new String[3];
     v_valusu=i_valusu.TBFL_ValidarUsuario();
     DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
     Connection c =DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]); 
     
     //Connection c       = new OracleDriver().defaultConnection();
    
  estado[0]          = "BIEN";
  tiene[0]           = "f";
  String select8i_1  = "SELECT PRO_FECHA_BENEFICIO "+
                       ",PRO_CANTIDAD_TIEMPO "+
                       ",PRO_REF_UNIDAD_TIEMPO "+
                       "FROM TBPRODUCTOS "+
                       "WHERE PRO_CODIGO = ? ";
  java.sql.Date fb   = new java.sql.Date(1);
  int tb             = 0;
  String ref_tb      = " ";
  String descripcion = " ";
  String fc          = " ";
  PreparedStatement t_st8i_1 = c.prepareStatement(select8i_1);
  t_st8i_1.setString(1,producto);
  ResultSet t_rs8i;
  t_rs8i = t_st8i_1.executeQuery();
  //CAPTURO IX DESEDE PRODUCTOS
  while(t_rs8i.next()){fb=t_rs8i.getDate(1);tb=t_rs8i.getInt(2);ref_tb=t_rs8i.getString(3);}
  //paso tb  a meses siempre y cuando este se represente en años
  if(ref_tb.equals("SUT002"))tb *= 12;
    //llamado a un sp que me devuelva la fecha de corte(FC) de un producto X
   //IN tiempo de beneficio en meses
  //OUT fecha de corte EN FORMATO(RRRR-DD-MM)
  CallableStatement t_cst8i_2 = c.prepareCall("{ call TBPBD_Fecha_Corte_Btributario(?,?,?) }");
  t_cst8i_2.registerOutParameter(2,Types.VARCHAR);
  t_cst8i_2.registerOutParameter(3,Types.VARCHAR);
  t_cst8i_2.setInt(1,tb);
  t_cst8i_2.execute();
  fc       = t_cst8i_2.getString(2);
  estado[0] = t_cst8i_2.getString(3);
  t_cst8i_2.close();
  if(estado[0].equals("E0"))
     estado[0] = "BIEN";
  //SELECCIONO LA FECHA DEL APORTE A VALIDAR, GENERO STATEMENT QUE LA DEVUELVA EN FORMATO JAVA.SQL.DATE
  String select8i_2 = "SELECT APO_FECHA_APORTE "+
                      "FROM TBAPORTES "+
                      "WHERE "+
                      "APO_CON_PRO_CODIGO = ? "+
                      "AND APO_CON_NUMERO = ? "+
                      "AND APO_CONSECUTIVO = ? ";
  java.sql.Date fa           = new java.sql.Date(1);
  PreparedStatement t_st8i_2 = c.prepareStatement(select8i_2);
  t_st8i_2.setString(1,producto);
  t_st8i_2.setString(2,contrato);
  t_st8i_2.setInt(3,consecutivo);
  t_rs8i = t_st8i_2.executeQuery();
  //CAPTURO IX DESEDE tbaportes
  while(t_rs8i.next()){fa=t_rs8i.getDate(1);}
  //realizo calculo de la fecha real de beneficio
  java.sql.Date fecharb = new java.sql.Date(1);
  if(fa.compareTo(fb)<0)//quiere decir que la fecha del aporte(fa) es menor que la fecha de beneficio
                         //e implica una fecha de beneficio real(fbr) es igual a fb+tb
   {
    //debo llamar un sp que me devuelva el FBR pasandalo como argumento de entrada el TB y la FA
     CallableStatement t_cst8i_3 = c.prepareCall("{ call TBPBD_FRB(?,?,?) }");
     t_cst8i_3.registerOutParameter(3,Types.DATE);
     t_cst8i_3.setDate(1,fb);
     t_cst8i_3.setInt(2,tb);
     t_cst8i_3.execute();
     fecharb = t_cst8i_3.getDate(3);
     t_cst8i_3.close();
      }
  else if(fb.compareTo(fa)<0)//quiere decir que la fecha del beneficio(fb) es menor que la fecha de aporte
                         //e implica una fecha de beneficio real(fbr) es igual a fa+tb
  {
   //debo llamar un sp que me devuelva el FBR pasandalo como argumento de entrada el TB y la FB
   CallableStatement t_cst8i_3 = c.prepareCall("{ call TBPBD_FRB(?,?,?) }");
   t_cst8i_3.registerOutParameter(3,Types.DATE);
   t_cst8i_3.setDate(1,fa);
   t_cst8i_3.setInt(2,tb);
   t_cst8i_3.execute();
   fecharb = t_cst8i_3.getDate(3);
   t_cst8i_3.close();
  }
  //comparo la fecha real de beneficio con la fecha de hoy y retuno V o F
    java.util.Date hoy = new java.util.Date();
  //si FBR.compareTo(hoy)<0 (FBR mayor que hoy) no debo actualizar el beneficio tributario
 //si  FBR.compareTo(hoy)>0 (FBR menor que hoy) debo actualizar el beneficio tributario(llamada a un sp, IN pro,cont,cons)
//si   FBR.compareTo(hoy)==0 (FBR es igual a hoy) no debo actualizar el beneficio tributario
 if(fecharb.compareTo(hoy)<0){tiene[0] = "v";}}
catch(Exception ex){estado[0] = " ERROR: "+ex;}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA ACTUALIZAR LOS INDICADORES
*VINCULADOS DE UN APORTE CON RESPECTO A SU BENEFICIO TRIBUTARIO CUANDO
*ESTE ES CATALOGADO COMO UN APORTE TRASLADADO 
*<p>
* @param    producto        = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato        = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo     = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
* @return   estado          = ESTADO FINAL DEL PROCESO DE ACTUALIZACION
*/
/*public static void TBPBD_Actualiza_Externos(String producto,String contrato,int consecutivo,String[] estado)
{
try
  {
   estado[0]         = "BIEN";
   Connection c      = new OracleDriver().defaultConnection();
   String update8i_1 ="UPDATE TBAPORTES SET "+
                      "APO_RAZON_BENEFICIO          = 'CUMPLMIENTO DEL TIEMPO TRIBUTARIO,APORTE TRASLADADO CON BENEFICIO.', "+
                      "APO_FECHA_BENEFICIO          = TRUNC(SYSDATE),"+
                      "APO_APORTE_BENEFICIO         = 'S' "+
                      "WHERE APO_CON_PRO_CODIGO     = ? "+
                      "AND APO_CON_NUMERO           = ? "+
                      "AND APO_CONSECUTIVO          = ? ";
  PreparedStatement t_st8i = c.prepareStatement(update8i_1);
   t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  t_st8i.setInt(3,consecutivo);
  t_st8i.executeUpdate();
  t_st8i.close();
  }

  catch(Exception ex){estado[0]=" ERROR: "+ex;}
}
//-----------------------------------------------------------------------------------------
/**
*<font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO SIRVE PARA ACTUALIZAR EL INDICADOR DE INFORMACION DE TRASLADO
*DE UN APORTE PADRE , CAMBIADO DICHO INDICADOR DE 'N' A 'S'
*@author      AlfaGL
*@version     1.0 junio/11/2001
* <p>
* @param         producto    = PRODUCTO AL QUE PERTENECE EL APORTE PADRE<BR>
*                contrato    = CONTRATO AL QUE PERTENECE EL APORTE PADRE<BR>
*                consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE PADRE<BR>
* @return        estado      = ESTADO FINAL DEL PROCESO DE ACTUALIZACION<BR>
*/
public static void TBPBD_Actualiza_Aporte_Papa(String producto,String contrato,int consecutivo,String[] estado)//IN
{
try
  {
   estado[0]            = "BIEN";
   Connection c      = new OracleDriver().defaultConnection();
   String update8i_1 = "UPDATE TBAPORTES "+
                       "SET "+
                       "APO_INFORMACION_TRASLADO = 'S' "+
                       "WHERE "+
                       "APO_CON_PRO_CODIGO       = ? "+
                       "AND APO_CON_NUMERO       = ? "+
                       "AND APO_CONSECUTIVO      = ? ";
  PreparedStatement t_st8i = c.prepareStatement(update8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  t_st8i.setInt(3,consecutivo);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){estado[0] = " ERROR: "+ex;}
}
//-----------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------
/**
*<font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO SIRVE PARA ACTUALIZAR EL INDICADOR DE INFORMACION DE TRASLADO
*DE UN APORTE PADRE , CAMBIADO DICHO INDICADOR DE 'N' A 'S'
*@author      AlfaGL
*@version     1.0 junio/11/2001
* <p>
* @param         producto    = PRODUCTO AL QUE PERTENECE EL APORTE PADRE<BR>
*                contrato    = CONTRATO AL QUE PERTENECE EL APORTE PADRE<BR>
*                consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE PADRE<BR>
* @return        estado      = ESTADO FINAL DEL PROCESO DE ACTUALIZACION<BR>
*/
public static void TBPBD_Actualiza_Aporte_TO(String producto,String contrato,int consecutivo
                                             ,String producto_to,String contrato_to,String[] estado)//IN
{
try
  {
   estado[0]            = "BIEN";
   Connection c      = new OracleDriver().defaultConnection();
   String update8i_1 = "UPDATE TBAPORTES "+
                       "SET "+
                       "APO_INFORMACION_TRASLADO = 'S' "+
                       ",APO_CON_PRO_CODIGO_TI   = ? "+
                       ",APO_CON_NUMERO_TI       = ? "+
                       "WHERE "+
                       "APO_CON_PRO_CODIGO       = ? "+
                       "AND APO_CON_NUMERO       = ? "+
                       "AND APO_CONSECUTIVO      = ? ";
  PreparedStatement t_st8i = c.prepareStatement(update8i_1);
  t_st8i.setString(1,producto_to);
  t_st8i.setString(2,contrato_to);
  t_st8i.setString(3,producto);
  t_st8i.setString(4,contrato);
  t_st8i.setInt(5,consecutivo);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){estado[0] = " ERROR: "+ex;}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA  HALLAR RETIROS
*QUE SE HAYAN CONVERTIDO EN TRASLADOS INTERNOS CON RESPECTO
*A UN APORTE EN PARTICULAR CORRESPONDIENTE AL PRODUCTO AL CONTRATO
*Y DONDE EL VALORBRUTO DEL RETIRO ES IGUAL A LA SUMA DEL CAPITAL Y DE
*LOS RTOS
* <p>
* @param    fecha_p         = FECHA DE PROCESO PERTENECIENTE AL APORTE DE TRASLADO INTERNO<BR>
*           capital_papa    = CAPITAL DEL APORTE DE TRASLADO<BR>
*           vrtos_papa      = RENDIMIENTOS DEL APORTE DE TRASLADO<BR>
*           c_cpapa         = CUENTA CONTINGENTE DEL APORTE DE TRASLADO<BR>
*@RETURN    ret_consecutivo = CONSECUTIVO DEL RETIRO HALLADO<br>
*           estado          = INDICADOR FINAL DEL PROCESO
*/
public static void TBPBD_Ret_Consecutivo(String fecha_p,double capital_papa,double rtos_papa,double c_cpapa,//IN
                                         int[] ret_consecutivo, int[] estado,String[] producto,String[] contrato)
{
try
  {
  Connection c      = new OracleDriver().defaultConnection();
  String select8i_1 = "SELECT "+
                      "RET_CONSECUTIVO "+
                      ",SUM(APR_CAPITAL) "+
                      ",SUM(APR_RENDIMIENTOS)+sum(APR_RENDIMIENTOS_P) "+
                      ",SUM(DECODE(CAE_REF_CARGO,'STC003',CAE_VALOR)) "+
                      ",RET_CON_PRO_CODIGO "+
                      ",RET_CON_NUMERO "+
                      "FROM "+
                      "TBRETIROS "+
                      ",TBAPORTES_RETIROS "+
                      ",TBCARGOS_RETIROS "+
                      "WHERE "+
                      "RET_FECHA_PROCESO      BETWEEN (TO_DATE(?,'DD-MON-RRRR')-6) AND TO_DATE(?,'DD-MON-RRRR') "+
                      "AND RET_CON_PRO_CODIGO       = APR_RET_CON_PRO_CODIGO "+
                      "AND RET_CON_NUMERO           = APR_RET_CON_NUMERO "+
                      "AND RET_CONSECUTIVO          = APR_RET_CONSECUTIVO "+
                      "AND APR_RET_CON_PRO_CODIGO   = CAE_APR_RET_CON_PRO_CODIGO(+) "+
                      "AND APR_RET_CON_NUMERO       = CAE_APR_RET_CON_NUMERO(+) "+
                      "AND APR_RET_CONSECUTIVO      = CAE_APR_RET_CONSECUTIVO(+) "+
                      "AND APR_APO_CONSECUTIVO      = CAE_APR_APO_CONSECUTIVO(+) "+
                      "GROUP BY "+
                      "RET_CONSECUTIVO,RET_CON_PRO_CODIGO,RET_CON_NUMERO "+
                      "ORDER BY RET_CONSECUTIVO ";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  t_st8i.setString(1,fecha_p);
  t_st8i.setString(2,fecha_p);
  ResultSet t_rs8i;
  //retorna cero sino encuentra,1 si encuentra y 2 si encuentra y cc_h<>cc_p
  estado[0] = 0;
  t_rs8i    = t_st8i.executeQuery();
  while(t_rs8i.next())
     {
        //capturo consecutivo del retiro
        ret_consecutivo[0] = t_rs8i.getInt(1);
        producto[0]        = t_rs8i.getString(5);
        contrato[0]        = t_rs8i.getString(6);
        //aproximaxión de la suma de capitales
        double sum_c1    = t_rs8i.getDouble(2);
        long   sum_c2    = java.lang.Math.round(sum_c1);
        //aproximación de la suma de rendimientos
        double sum_rtos1 = t_rs8i.getDouble(3);
        long   sum_rtos2 = java.lang.Math.round(sum_rtos1);
        //aproximación de la suma de cuenta contingente
        double sum_cc1   = t_rs8i.getDouble(4);
        long   sum_cc2   = java.lang.Math.round(sum_cc1);
        //aproximación del capital del aporte
        long   apo_c2    = java.lang.Math.round(capital_papa);
        //aproximación de los rendimientos del aporte
        long   apo_rtos2 = java.lang.Math.round(rtos_papa);
        //aproximación de la cuenta continegnte del aporte
        long   apo_cc2   = java.lang.Math.round(c_cpapa);
        if(sum_c2==apo_c2 && sum_rtos2==apo_rtos2)
        {
          estado[0] = 1;
          if(sum_cc2==apo_cc2)
            estado[0] = 2;
          break;
        }
     }
  t_rs8i.close();
  t_st8i.close();
  }
catch(Exception ex){estado[0] = 0;}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA  HALLAR RETIROS
*QUE SE HAYAN CONVERTIDO EN TRASLADOS INTERNOS CON RESPECTO
*A UN APORTE EN PARTICULAR CORRESPONDIENTE AL PRODUCTO AL CONTRATO
*Y DONDE EL VALORBRUTO DEL RETIRO ES IGUAL A LA SUMA DEL CAPITAL Y DE
*LOS RTOS
* <p>
* @param    fecha_p         = FECHA DE PROCESO PERTENECIENTE AL APORTE DE TRASLADO INTERNO<BR>
*           capital_papa    = CAPITAL DEL APORTE DE TRASLADO<BR>
*           vrtos_papa      = RENDIMIENTOS DEL APORTE DE TRASLADO<BR>
*           c_cpapa         = CUENTA CONTINGENTE DEL APORTE DE TRASLADO<BR>
*@RETURN    ret_consecutivo = CONSECUTIVO DEL RETIRO HALLADO<br>
*           estado          = INDICADOR FINAL DEL PROCESO
*/
/*public static void TBPBD_RETIRO_TO1(String fecha_p,double capital_papa,double rtos_papa,double c_cpapa,
                                   int[] ret_consecutivo, String[] estado,String[] producto,String[] contrato)
{
try
  {
  Connection co     = new OracleDriver().defaultConnection();
  String select8i_1 = "SELECT "+
                      "RET_CONSECUTIVO "+
                      ",SUM(APR_CAPITAL) "+
                      ",SUM(APR_RENDIMIENTOS)+sum(APR_RENDIMIENTOS_P) "+
                      ",SUM(DECODE(CAE_REF_CARGO,'STC003',CAE_VALOR)) "+
                      ",RET_CON_PRO_CODIGO "+
                      ",RET_CON_NUMERO "+
                      "FROM "+
                      "TBRETIROS "+
                      ",TBAPORTES_RETIROS "+
                      ",TBCARGOS_RETIROS "+
                      "WHERE "+
                      "RET_FECHA_PROCESO      BETWEEN (TO_DATE(?,'DD-MON-RRRR')-6) AND TO_DATE(?,'DD-MON-RRRR') "+
                      "AND RET_CON_PRO_CODIGO       = APR_RET_CON_PRO_CODIGO "+
                      "AND RET_CON_NUMERO           = APR_RET_CON_NUMERO "+
                      "AND RET_CONSECUTIVO          = APR_RET_CONSECUTIVO "+
                      "AND APR_RET_CON_PRO_CODIGO   = CAE_APR_RET_CON_PRO_CODIGO(+) "+
                      "AND APR_RET_CON_NUMERO       = CAE_APR_RET_CON_NUMERO(+) "+
                      "AND APR_RET_CONSECUTIVO      = CAE_APR_RET_CONSECUTIVO(+) "+
                      "AND APR_APO_CONSECUTIVO      = CAE_APR_APO_CONSECUTIVO(+) "+
                      "GROUP BY "+
                      "RET_CONSECUTIVO,RET_CON_PRO_CODIGO,RET_CON_NUMERO "+
                      "ORDER BY RET_CONSECUTIVO ";
  PreparedStatement t_st8i = co.prepareStatement(select8i_1);
  t_st8i.setString(1,fecha_p);
  t_st8i.setString(2,fecha_p);
  ResultSet t_rs8i = t_st8i.executeQuery();
  //retorna cero sino encuentra,1 si encuentra y 2 si encuentra y cc_h<>cc_p
  estado[0]    = "0";

  long k       = 0;
  long rtos    = 0;
  long cc      = 0;
  long k_apt   = java.lang.Math.round(capital_papa);
  long r_apt   = java.lang.Math.round(rtos_papa);
  long c_apt   = java.lang.Math.round(c_cpapa);
  /*
  estado[0] = "0"; ==> no existe retiro equivalente
  estado[0] = "1"  ==> k y r son iguales y cc diferentes
  estado[0] = "2"; ==> k, r y cc iguales
  estado[0] = "ex" ==> EXCEPTION
  */
/*  while(t_rs8i.next())
  {
    ret_consecutivo[0] = t_rs8i.getInt(1);
    k                  = java.lang.Math.round(t_rs8i.getDouble(2));
    rtos               = java.lang.Math.round(t_rs8i.getDouble(3));
    cc                 = java.lang.Math.round(t_rs8i.getDouble(4));
    producto[0]        = t_rs8i.getString(5);
    contrato[0]        = t_rs8i.getString(6);
    if(k==k_apt && rtos==r_apt)
     {
       estado[0] = "1";
       if(cc==c_apt)
         estado[0] = "2";
       break;
     }
  }
  t_rs8i.close();
  t_st8i.close();
  }
catch(Exception ex){estado[0] = ex.toString();}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA SELECCIONAR EL MAXIMO PORCENTAJE DE
*DISPONIBILIDAD DE UN APORTE EN PARTICULAR
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
* @return   maximo_p    = MAXIMO PORCENTAJE DE DISPONIBILIDAD DEL APORTE
*/
/*public static void TBPBD_Maximo_Porcentaje(String producto,String contrato,int[] maximo_p)
{
try
{
 Connection c      = new OracleDriver().defaultConnection();
 String select8i_1 =   "SELECT "+
                      "MAX(DIA_FECHA)"+
                      ",DIA_PORCENTAJE_DISPONIBILIDAD "+
                      "FROM "+
                      "TBAPORTES, "+
                      "TBDISPONIBILIDADES_APORTES "+
                      "WHERE "+
                      "APO_CON_PRO_CODIGO      = DIA_APO_CON_PRO_CODIGO "+
                      "AND APO_CON_NUMERO      = DIA_APO_CON_NUMERO "+
                      "AND APO_CONSECUTIVO     = DIA_APO_CONSECUTIVO "+
                      "AND APO_CON_PRO_CODIGO  = ? "+
                      "AND APO_CON_NUMERO      = ? "+
                      "AND APO_NUMERO_UNIDADES > 0 "+
                      "GROUP BY DIA_PORCENTAJE_DISPONIBILIDAD ";
 PreparedStatement t_st8i = c.prepareStatement(select8i_1);
 t_st8i.setString(1,producto);
 t_st8i.setString(2,contrato);
 ResultSet t_rs8i;
 t_rs8i = t_st8i.executeQuery();
 while(t_rs8i.next())
 {
  maximo_p[0] = t_rs8i.getInt(2);
 }
 t_st8i.close();
}
catch (Exception ex){maximo_p[0] = 0;}
}*/
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIEMINETO DEVUELVE EL VALOR DE LA CUENTA CONTINGENTE DE UN
*APORTE HIJO; DESDE LA TABLA TBCARGOS_RETIROS.
* <p>
* @param    producto        = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato        = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           ret_consecutivo = CONSECUTIVO PERTENECIENTE AL RETIRO <BR>
*           consecutivo     = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
* @return   c_ch            = VALOR DE LA CUENTA CONTINGENTE      <BR>
*/
public static void TBPBD_Cuenta_CHijo(String producto,String contrato,int ret_consecutivo,int apo_consecutivo,//IN
                                      double[] c_ch)
{
try
  {
  Connection c      = new OracleDriver().defaultConnection();
  String select8i_1 = "SELECT "+
                      "CAE_VALOR "+
                      "FROM TBCARGOS_RETIROS "+
                      "WHERE "+
                      "CAE_APR_RET_CON_PRO_CODIGO  = ? "+
                      "AND CAE_APR_RET_CON_NUMERO  = ? "+
                      "AND CAE_APR_RET_CONSECUTIVO = ? "+
                      "AND CAE_APR_APO_CONSECUTIVO = ? "+
                      "AND CAE_REF_CARGO = 'STC002'";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  t_st8i.setInt(3,ret_consecutivo);
  t_st8i.setInt(4,apo_consecutivo);
  ResultSet t_rs8i;
  t_rs8i = t_st8i.executeQuery();
  while(t_rs8i.next())
     {
      c_ch[0]  = t_rs8i.getDouble(1);
     }
  t_rs8i.close();
  t_st8i.close();
  }
catch(Exception ex){c_ch[0]  = 0;}

}
//-----------------------------------------------------------------------------------------
/**
*ESTE PROCEDIMIENTO SELECCIONARA DESDE EL PORTE HIJO LA FECHA DEL APORTE HIJO QUE
*SERÁ HEREDADO POR EL MISMO CUANDO PASE A SER HIJO DEL APORTE DE TO
*parametros de entrada producto,contrato,consecutivohijo
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
* @return   fa_hijo     = FECHA DEL APORTE HIJO<BR>
*/
public static void TBPBD_F_A_Hijo(String producto,String contrato,int consecutivo,//IN
                               String[] fa_hijo)
{
try
  {
  Connection c      = new OracleDriver().defaultConnection();
  String select8i_1 = "SELECT "+
                      "TO_CHAR(APO_FECHA_APORTE,'RRRR-MM-DD') "+
                      "FROM "+
                      "TBAPORTES "+
                      "WHERE "+
                      "APO_CON_PRO_CODIGO  = ? "+
                      "AND APO_CON_NUMERO  = ? "+
                      "AND APO_CONSECUTIVO = ? ";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  t_st8i.setInt(3,consecutivo);
  ResultSet t_rs8i;
  t_rs8i = t_st8i.executeQuery();
  while(t_rs8i.next())
     {
      fa_hijo[0]  = t_rs8i.getString(1);
     }
  t_rs8i.close();
  t_st8i.close();
  }
catch(Exception ex){fa_hijo[0] = null;}
}
//-----------------------------------------------------------------------------------------
/**
*ESTE PROCEDIMIENTO ALMACENADO INSERTA SOBRE NUEVAS
*CONDICIONES DE DISPONIBILIDAD
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
*           fecha       = VALOR ACTUAL DE LA FECHA DE DISPONIBILIDADL<BR>
*           porcentaje  = VALOR ACTUAL DEL PORCENTAJE DE DISPONIBILIDAD<BR>
*           valor       = VALOR ACTUAL DE LA DISPONIBILIDAD<BR>
* @return   estado      = ESTADO FINAL DEL PROCESO
*/
/*public static void TBPBD_Inserta_Disponibilidad(String producto,String contrato,int consecutivo,String fecha,String porcentaje,
                                              double valor,String[] estado) //IN

{
try
  {
  estado[0]         = "BIEN";
  Connection c      = new OracleDriver().defaultConnection();
  String insert8i_1 = "INSERT INTO TBDISPONIBILIDADES_APORTES "+
                      "(DIA_APO_CON_PRO_CODIGO,"+
                      "DIA_APO_CON_NUMERO,"+
                      "DIA_APO_CONSECUTIVO,"+
                      "DIA_FECHA,"+
                      "DIA_PORCENTAJE_DISPONIBILIDAD,"+
                      "DIA_VALOR_DISPONIBLE) "+
                      "VALUES (?,?,?,TO_DATE(?,'RRRR-MM-DD'),?,?)";
  PreparedStatement t_st8i = c.prepareStatement(insert8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  t_st8i.setInt(3,consecutivo);
  t_st8i.setString(4,fecha);
  t_st8i.setString(5,porcentaje);
  t_st8i.setDouble(6,valor);
  t_st8i.executeUpdate();
  t_st8i.close();
  }
catch(Exception ex){estado[0] = "ERORR "+ex;}
}*/
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA CALCULAR LA FECHA
*DE LLAMADO A LA FUNCION DEL VALOR DE LA UNIDAD, FECHA - 1
* <p>
* @param    fecha    = FECHA A LA CUAL SE LE REALIZARA EL CALCULO<BR>
* @return   fecha_ud = FECHA CALCULADA
*/
public static void TBPBD_FECHA_VU(String fecha,String[] fecha_ud)
{
try
  {
  Connection c      = new OracleDriver().defaultConnection();
  String select8i_1 = "SELECT TO_CHAR(TO_DATE(?,'RRRR-MM-DD') - 1,'RRRRMMDD') FROM DUAL";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  t_st8i.setString(1,fecha);
  ResultSet t_rs8i;
  t_rs8i = t_st8i.executeQuery();
  while(t_rs8i.next())
     {
      fecha_ud[0] = t_rs8i.getString(1);
     }
  t_rs8i.close();
  t_st8i.close();
  }
catch(Exception ex){fecha_ud[0]="ERROR   "+ex.toString(); }
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA  REGISTRAR LOS VALORES
*ORIGINALES DE UN APORTES CUANDO SE LE VAYA A REALIZAR UN CAMBIO
*POSTERIROR
* <p>
* @param    tipo_t      = TIPO DE TRANSACCION ENCARGADA DE REALIZAR LA MODIFICACION<BR>
*           consecutivo = CONSECUTIVO DEL APORTE A MODIFICAR<BR>
*           linea       = INDICADOR DE VARIACIONES DIARIAS PARA EL MISMO CONSECUTIVO<BR>
*           tipo_m      = TIPO DE MODIFICACION A REALIZAR<BR>
*           razon       = RAZON DE LA MODIFICACION<BR>
*           user        = USUARIO ENCARGADO DE LA MODIFICACION<BR>
* @return   estado      = INDICADOR FINAL DEL PROCESO
*/
public static void TBPBD_TransaccionLog(String tipo_t,int consecutivo,int linea,String tipo_m,
                                        String razon,String user,String[] estado)
  //VALORES PERMITIDOS POR
 //TRL_TIPO_TRANSACCION IN ('R', 'P', 'A')
//TRL_TIPO_MODIFICACION IN ('A', 'R', 'M')
{
try
  {
  estado[0] = "YES";
  Connection co     = new OracleDriver().defaultConnection();
  String insert8i_1 = "INSERT INTO TBTRANSACCION_LOGS "+
                      "(TRL_TIPO_TRANSACCION,"+
                      "TRL_APORTE_RETIRO_PRODUCTO,"+
                      "TRL_FECHA,"+
                      "TRL_LINEA,"+
                      "TRL_TIPO_MODIFICACION,"+
                      "TRL_RAZON_MODIFICACION,"+
                      "TRL_USUARIO) "+
                      "VALUES( "+
                      "?,TO_CHAR(?),TRUNC(SYSDATE),?,?,?,?)";
  PreparedStatement t_st8i = co.prepareStatement(insert8i_1);
  t_st8i.setString(1,tipo_t);
  t_st8i.setInt(2,consecutivo);
  t_st8i.setInt(3,linea);
  t_st8i.setString(4,tipo_m);
  t_st8i.setString(5,razon);
  t_st8i.setString(6,user);
  t_st8i.executeUpdate();
  t_st8i.close();
  estado[0] = "YES";
  }
catch(Exception ex){estado[0] = "NO";}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA  ACTUALIZARLE EL
*ESTADO DE UN RETIRO EN PARTICULAR CUANDO ESTE ES MODIFICADO
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL RETIRO <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL RETIRO <BR>
*           consecutivo = CONSECUTIVO PERTENECIENTE AL RETIRO <BR>
*           n_estado    = VALOR ACTUAL DEL ESTADO DEL RETIRO<BR>
* @return   estado      = ESTADO FINAL DEL PROCESO
*/
public static void TBPBD_Update_EstadoR(String producto,String contrato,int consecutivo,String n_estado,String[] estado)//in
{
try
  {
   estado[0] = "BIEN";
   Connection co     = new OracleDriver().defaultConnection();
   String update8i_1 = "UPDATE TBRETIROS SET "+
                       "RET_REF_ESTADO           = ? "+
                       "WHERE RET_CON_PRO_CODIGO = ? "+
                       "AND RET_CON_NUMERO       = ? "+
                       "AND RET_CONSECUTIVO      = ? ";
  PreparedStatement t_st8i = co.prepareStatement(update8i_1);
  t_st8i.setString(1,n_estado);
  t_st8i.setString(2,producto);
  t_st8i.setString(3,contrato);
  t_st8i.setInt(4,consecutivo);
  t_st8i.executeUpdate();
  t_st8i.close();
  estado[0] = "BIEN";
  }
catch(Exception ex){estado[0] = "ERROR:  "+ex.toString();}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL RETIRO <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL RETIRO <BR>
*           cutivo      = CONSECUTIVO PERTENECIENTE AL RETIRO <BR>
* @return   esquema     = CADENA QUE CONTIENE EL ESQUEMA DE CONTRATO DE UN RETIRO EN ESPECIFICO<BR>
*/
public static void TBPBD_ESQUEMA_ACTUAL(String producto,String contrato,String cutivo,String[] esquema)
{
try
  {
    Connection c   = new OracleDriver().defaultConnection();
    String select8i_1 ="select "+
                       "DECODE(RET_REF_METODO_ORDEN,NULL,'SMO000',RET_REF_METODO_ORDEN) "+
                       ",DECODE(RET_REF_METODO_BENEFICIO,NULL,'SMB000',RET_REF_METODO_BENEFICIO) "+
                       ",DECODE(RET_REF_METODO_PENALIZACION,NULL,'SMP000',RET_REF_METODO_PENALIZACION) "+
                       ",DECODE(RET_REF_METODO_CUENTA,NULL,'SMC000',RET_REF_METODO_CUENTA) "+
                       ",DECODE(RET_REF_NATURALEZA,NULL,'SNR000',RET_REF_NATURALEZA) "+
                       ",DECODE(RET_RESPETAR_NATURALEZA,NULL,'X',RET_RESPETAR_NATURALEZA) "+
                       "FROM "+
                       "TBRETIROS "+
                       "WHERE "+
                       "RET_CON_PRO_CODIGO  = ? "+
                       "AND RET_CON_NUMERO  = ? "+
                       "AND RET_CONSECUTIVO = ? ";
    PreparedStatement t_st8i = c.prepareStatement(select8i_1);
    t_st8i.setString(1,producto);
    t_st8i.setString(2,contrato);
    t_st8i.setString(3,cutivo);
    ResultSet t_rs8i         = t_st8i.executeQuery();
    while(t_rs8i.next())
    {
      esquema[0] = t_rs8i.getString(1)+t_rs8i.getString(2)+t_rs8i.getString(3)+t_rs8i.getString(4)+t_rs8i.getString(5)+t_rs8i.getString(6);
    }
    t_rs8i.close();
    t_st8i.close();
  }
catch(Exception ex){esquema[0] = ex.toString();}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA GENERAR POR CADA INDICADOR DE
*UN ESQUEMA ACTUAL DE CONTRATO UN TAG SELECT HTML QUE CONTENGA LAS POSIBLES
*OPCIONES DE DICHO INDICADOR, DEJANDO COMO PRIORITARIO EL INDICADOR ORIGINAL
* <p>
* @param   metodo     = INDICADOR DE ESQUEMA ACTUAL A PROCESAR<BR>
*          str_refs   = CADENA QUE CONTIENE EL ESQUEMA ACTUAL DE UN RETIRO ESPECIFICO<BR>
* @return  referencia = TAG HTML QUE CONTIENE LA INFORMACION DEL ESQUEMA ACTUAL DE UN CONTRATO
*                       POR INDICADOR DE ESQUEMA<BR>
*/
public static void TBPBD_ESQUEMA_TOTAL(String metodo,String str_refs,String[] referencia)
{
try
  {
   //encuentro esquema actual
   String m_o     = new String("");
   String m_b     = new String("");
   String m_p     = new String("");
   String m_c     = new String("");
   String m_n     = new String("");
   String ref_d   = new String("");
   String ref_c   = new String("");
   try
   {
     m_o     = str_refs.substring(0,6);
     m_b     = str_refs.substring(6,12);
     m_p     = str_refs.substring(12,18);
     m_c     = str_refs.substring(18,24);
     m_n     = str_refs.substring(24,30);
   }
   catch(Exception ex){m_o = "SMO000"; m_b = "SMB000"; m_p = "SMP000"; m_c = "SMC000"; m_n = "SNR000";}
   Connection c   = new OracleDriver().defaultConnection();
   String dominio = new String(" ");
//--METODO ORDEN
   if(metodo.equalsIgnoreCase("ORDEN:"))
    {
     dominio = "SMO";
     referencia[0]  = "<select name="+dominio.toLowerCase()+">";
     CallableStatement t_cst8i_1 = c.prepareCall("{ call TBPBD_ref_descripcion(?,?) }");
     t_cst8i_1.registerOutParameter(2,Types.VARCHAR);
     t_cst8i_1.setString(1,m_o);
     t_cst8i_1.execute();
     ref_d = t_cst8i_1.getString(2);
     t_cst8i_1.close();
     ref_c = m_o;
     if(ref_c.substring(3,6).equalsIgnoreCase("000"))
        ref_d = "NINGUNO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
     referencia[0] +="<option selected value="+m_o+">"+ref_d+"</option>";
    }
//--METODO BENEFICIO
   if(metodo.equalsIgnoreCase("BENEFICIO:"))
    {
     dominio = "SMB";
     referencia[0]  = "<select name="+dominio.toLowerCase()+">";
     CallableStatement t_cst8i_1 = c.prepareCall("{ call TBPBD_ref_descripcion(?,?) }");
     t_cst8i_1.registerOutParameter(2,Types.VARCHAR);
     t_cst8i_1.setString(1,m_b);
     t_cst8i_1.execute();
     ref_d = t_cst8i_1.getString(2);
     t_cst8i_1.close();
     ref_c = m_b;
     if(ref_c.substring(3,6).equalsIgnoreCase("000"))
        ref_d = "NINGUNO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
     referencia[0] +="<option selected value="+m_b+">"+ref_d+"</option>";
    }
//--METODO PENALIZACION
   if(metodo.equalsIgnoreCase("PENALIZACION:"))
    {
     dominio = "SMP";
     referencia[0]  = "<select name="+dominio.toLowerCase()+">";
     CallableStatement t_cst8i_1 = c.prepareCall("{ call TBPBD_ref_descripcion(?,?) }");
     t_cst8i_1.registerOutParameter(2,Types.VARCHAR);
     t_cst8i_1.setString(1,m_p);
     t_cst8i_1.execute();
     ref_d = t_cst8i_1.getString(2);
     t_cst8i_1.close();
     ref_c = m_p;
     if(ref_c.substring(3,6).equalsIgnoreCase("000"))
        ref_d = "NINGUNO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
     referencia[0] +="<option selected value="+m_p+">"+ref_d+"</option>";
    }
//--METODO CUENTA
   if(metodo.equalsIgnoreCase("CUENTA:"))
    {
     dominio = "SMC";
     referencia[0]  = "<select name="+dominio.toLowerCase()+">";
     CallableStatement t_cst8i_1 = c.prepareCall("{ call TBPBD_ref_descripcion(?,?) }");
     t_cst8i_1.registerOutParameter(2,Types.VARCHAR);
     t_cst8i_1.setString(1,m_c);
     t_cst8i_1.execute();
     ref_d = t_cst8i_1.getString(2);
     t_cst8i_1.close();
     ref_c = m_c;
     if(ref_c.substring(3,6).equalsIgnoreCase("000"))
        ref_d = "NINGUNO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
     referencia[0] +="<option selected value="+m_c+">"+ref_d+"</option>";
    }
//METODO NATURALEZA
   if(metodo.equalsIgnoreCase("NATURALEZA:"))
    {
     dominio = "SNR";
     referencia[0]  = "<select name="+dominio.toLowerCase()+">";
     CallableStatement t_cst8i_1 = c.prepareCall("{ call TBPBD_ref_descripcion(?,?) }");
     t_cst8i_1.registerOutParameter(2,Types.VARCHAR);
     t_cst8i_1.setString(1,m_n);
     t_cst8i_1.execute();
     ref_d = t_cst8i_1.getString(2);
     t_cst8i_1.close();
     ref_c = m_n;
     if(ref_c.substring(3,6).equalsIgnoreCase("000"))
        ref_d = "NINGUNO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
     referencia[0] +="<option selected value="+m_n+">"+ref_d+"</option>";
    }
//BUSQUEDA DE ESQUEMA TOTAL
   String select8i_1 ="SELECT "+
                      "REF_CODIGO "+
                      ",REF_DESCRIPCION "+
                      "FROM "+
                      "TBREFERENCIAS "+
                      "WHERE "+
                      "REF_CODIGO LIKE '"+dominio+"%'";
   PreparedStatement t_st8i = c.prepareStatement(select8i_1);
   ResultSet t_rs8i         = t_st8i.executeQuery();
   while(t_rs8i.next())
   {
     if(!t_rs8i.getString(1).equalsIgnoreCase(ref_c))
      {
        if(!t_rs8i.getString(1).equalsIgnoreCase("SMO003"))
         {
           if(!t_rs8i.getString(1).substring(3,6).equalsIgnoreCase("000"))
            {
              referencia[0] += "<option value="+t_rs8i.getString(1)+">"+t_rs8i.getString(2)+"</option>";
            }
           else
           {
             referencia[0] += "<option value="+t_rs8i.getString(1)+">NINGUNO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>";
           }
         }
      }
    }
   referencia[0] += "</select>";
   t_rs8i.close();
   t_st8i.close();
  }
catch(Exception ex){referencia[0] = ex.toString();}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA  SELECCIONAR UNA DESCRIPCION DE
*REFERENCIAS CON RESPECTO A UN CODIGO DE REFERENCIAS
* <p>
* @param    ref_c    = CODIGO DE REFERENCIA <BR>
* @return   ref_d    = DESCRIPCION DEL CODIGO DE REFERENCIA
*/
public static void TBPBD_ref_descripcion(String ref_c,String[] ref_d)
{
try
  {
    Connection c   = new OracleDriver().defaultConnection();
    String select8i_1 ="select "+
                       "ref_descripcion "+
                       "from "+
                       "tbreferencias "+
                       "where "+
                       "ref_codigo = ? ";
    PreparedStatement t_st8i = c.prepareStatement(select8i_1);
    t_st8i.setString(1,ref_c);
    ResultSet t_rs8i         = t_st8i.executeQuery();
    while(t_rs8i.next())
    {
      ref_d[0] = t_rs8i.getString(1);
    }
    t_rs8i.close();
    t_st8i.close();
  }
catch(Exception ex){ref_d[0] = ex.toString();}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA VERIFICAR SI UN APORTE TIENE O NO
*RETIROS ASOCIADOS A EL
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
* @return   retiros     = INDICADOR FINAL DEL PROCESO<BR>
*           estado      = ESTADO FINAL DEL PROCESO
*/
public static void TBPBD_Valida_Retiros_Externos(String producto,String contrato,int consecutivo,
                                                 String[] retiros,String[] estado)//in
{
try
  {
  estado[0]         = "BIEN";
  Connection c      = new OracleDriver().defaultConnection();
  String select8i_1 = "SELECT "+
                      "APR_RET_CONSECUTIVO "+
                      "FROM "+
                      "TBAPORTES_RETIROS "+
                      ",TBRETIROS "+
                      "WHERE "+
                      "APR_RET_CON_PRO_CODIGO      = RET_CON_PRO_CODIGO "+
                      "AND APR_RET_CON_NUMERO      = RET_CON_NUMERO "+
                      "AND APR_RET_CONSECUTIVO     = RET_CONSECUTIVO "+
                      "AND RET_REF_ESTADO          = 'SER006' "+
                      "AND APR_RET_CON_PRO_CODIGO  = ? "+
                      "AND APR_RET_CON_NUMERO      = ? "+
                      "AND APR_APO_CONSECUTIVO     = ? ";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  t_st8i.setInt(3,consecutivo);
  ResultSet t_rs8i;
  t_rs8i = t_st8i.executeQuery();
  retiros[0] = "f";
  while(t_rs8i.next())
     {
      retiros[0]   = "v";
     }
  t_rs8i.close();
  t_st8i.close();
  }
catch(Exception ex){estado[0] = " ERROR: "+ex;}
}
//-----------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*ESTE PROCEDIMIENTO ALMACENADO SIRVE PARA ACTUALIZAR LOS
*SALDOS DE UN APORTE ESPECIFICO
<p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           consecutivo = CONSECUTIVO PERTENECIENTE AL APORTE <BR>
*           c           = VALOR ACTUAL DEL SALDO DE CAPITAL<BR>
*           cc          = VALOR ACTUAL DEL SALDO DE CUENTA CONTINGENTE<BR>
*           rto         = VALOR ACTUAL DEL SALDO DE RENDIMIENTOS<BR>*
* @return  NINGUNO
*/
public static void TBPBD_Actualiza_Saldos(String producto,String contrato,int consecutivo,double c,double cc,double rto)

{
try
  {
   Connection co     = new OracleDriver().defaultConnection();
   String select8i_1 = "SELECT "+
                       "APO_RENDIMIENTOS_PENALIZADOS "+
                       "FROM TBAPORTES "+
                       "WHERE APO_CON_PRO_CODIGO = ? "+
                       "AND APO_CON_NUMERO       = ? "+
                       "AND APO_CONSECUTIVO      = ? ";
   String update8i_1 = "UPDATE TBAPORTES SET "+
                       "APO_SALDO_CAPITAL              = ? "+
                       ",APO_RENDIMIENTOS_PENALIZADOS  = ? "+
                       ",APO_SALDO_CUENTA_CONTINGENTE  = ? "+
                       "WHERE APO_CON_PRO_CODIGO       = ? "+
                       "AND APO_CON_NUMERO             = ? "+
                       "AND APO_CONSECUTIVO            = ? ";
  PreparedStatement t_st8i = co.prepareStatement(select8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  t_st8i.setInt(3,consecutivo);
  ResultSet t_rs8i = t_st8i.executeQuery();
  double rtos_p    = rto;
  while(t_rs8i.next())
   {
     if(t_rs8i.getDouble(1) <= 0)
       rtos_p = t_rs8i.getDouble(1);
   }//while
  t_rs8i.close();
  t_st8i.close();
  PreparedStatement t_st8i_2 = co.prepareStatement(update8i_1);
  t_st8i_2.setDouble(1,c);
  t_st8i_2.setDouble(2,rtos_p);
  t_st8i_2.setDouble(3,cc);
  t_st8i_2.setString(4,producto);
  t_st8i_2.setString(5,contrato);
  t_st8i_2.setInt(6,consecutivo);
  t_st8i_2.executeUpdate();
  t_st8i_2.close();
  }
catch(Exception ex){}
}

public static void TBPBD_Parametros_FuncionesAS(String[] ruta_funcion,String[] nombre_sistema,String[] usuario_mfund,String[] password_mfund)
{
try
{
 Connection c      = new OracleDriver().defaultConnection();
 //--RUTA FUNCIONES AS/400
 String select8i_1 =  "SELECT REF_DESCRIPCION FROM TBREFERENCIAS WHERE REF_CODIGO = 'SFM001' ";
 PreparedStatement t_st8i_1 = c.prepareStatement(select8i_1);
 ResultSet t_rs8i_1= t_st8i_1.executeQuery();
 ruta_funcion[0]   = "N";
 while(t_rs8i_1.next())
 {ruta_funcion[0] = t_rs8i_1.getString(1);}
 t_rs8i_1.close();t_st8i_1.close();
 //--NOMBRE SISTEMA AS/400
 String select8i_2 =  "SELECT REF_DESCRIPCION FROM TBREFERENCIAS WHERE REF_CODIGO = 'SNM001'";
 PreparedStatement t_st8i_2 = c.prepareStatement(select8i_2);
 ResultSet t_rs8i_2  = t_st8i_2.executeQuery();
 nombre_sistema[0]   = "N";
 while(t_rs8i_2.next())
 {nombre_sistema[0] = t_rs8i_2.getString(1);}
 t_rs8i_2.close();t_st8i_2.close();
 //--USUARIO MFUND DUEÑO DE FUNCIONES
 String select8i_3 =  "SELECT REF_DESCRIPCION FROM TBREFERENCIAS WHERE REF_CODIGO = 'SUM001'";
 PreparedStatement t_st8i_3 = c.prepareStatement(select8i_3);
 ResultSet t_rs8i_3  = t_st8i_3.executeQuery();
 usuario_mfund[0]   = "N";
 while(t_rs8i_3.next())
 {usuario_mfund[0] = t_rs8i_3.getString(1);}
 t_rs8i_3.close();t_st8i_3.close();
 //--PASSWORD MFUND DUEÑO DE FUNCIONES
 String select8i_4 =  "SELECT REF_DESCRIPCION FROM TBREFERENCIAS WHERE REF_CODIGO = 'SPM001'";
 PreparedStatement t_st8i_4 = c.prepareStatement(select8i_4);
 ResultSet t_rs8i_4  = t_st8i_4.executeQuery();
 password_mfund[0]   = "N";
 while(t_rs8i_4.next())
 {password_mfund[0] = t_rs8i_4.getString(1);}
 t_rs8i_4.close();t_st8i_4.close();
}
catch (Exception ex){System.out.println(ex);}
}
//-----------------------------------------------------------------------------------------

public static void TBPBD_Param_FuncAS_OBLIG(String producto, String[] ruta_funcion,String[] nombre_sistema,String[] usuario_oblig,String[] password_oblig)
{
try
{
    Modelo_TB_Referencias objModelo_TB_Referencias = new Modelo_TB_Referencias();
    SQL_TB_FREFERENCIAS_FPOB objSQL_TB_FREFERENCIAS_FPOB = new SQL_TB_FREFERENCIAS_FPOB();
    objModelo_TB_Referencias = objSQL_TB_FREFERENCIAS_FPOB.GET_TB_FREFERENCIAS_FPOB(producto); 
    ruta_funcion[0]     = objModelo_TB_Referencias.getLibreria();
    nombre_sistema[0]   = objModelo_TB_Referencias.getSistema();
    usuario_oblig[0]    = objModelo_TB_Referencias.getUsuario();
    password_oblig[0]   = objModelo_TB_Referencias.getPassword();   
    
}
catch (Exception ex){System.out.println(ex);}
}
//-----------------------------------------------------------------------------------------
}

