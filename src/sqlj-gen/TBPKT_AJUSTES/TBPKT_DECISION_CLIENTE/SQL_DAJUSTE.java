/*@lineinfo:filename=SQL_DAJUSTE*//*@lineinfo:user-code*//*@lineinfo:1^1*//*
El objetivo de esta clase es manejar todo lo que tiene que ver con la Base de
Datos TaxBenefit, según lo que se desee realizar, como consultas, modificaciones
o insertar información.
*/
package TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE;

import oracle.jdbc.*;
import oracle.sql.*;
import oracle.jdbc.driver.*;
import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import sqlj.runtime.ref.DefaultContext;
import java.sql.*;
import java.sql.Date;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;


public class SQL_DAJUSTE extends Object{

/*@lineinfo:generated-code*//*@lineinfo:23^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONTOTRETCARGO
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONTOTRETCARGO(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    CAPRENNdx = findColumn("CAPREN");
    LONNdx = findColumn("LON");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double CAPREN()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(CAPRENNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int CAPRENNdx;
  public int LON()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(LONNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int LONNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:23^66*/
/*@lineinfo:generated-code*//*@lineinfo:24^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONRETCARGO
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONRETCARGO(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    APR_APO_CONSECUTIVONdx = findColumn("APR_APO_CONSECUTIVO");
    APR_CAPITALNdx = findColumn("APR_CAPITAL");
    APR_RENDIMIENTOSNdx = findColumn("APR_RENDIMIENTOS");
    APR_PORCENTAJE_PENALIZACIONNdx = findColumn("APR_PORCENTAJE_PENALIZACION");
    CAE_VALORNdx = findColumn("CAE_VALOR");
    CAE_REF_CARGONdx = findColumn("CAE_REF_CARGO");
    APO_SALDO_NUMERO_UNIDADESNdx = findColumn("APO_SALDO_NUMERO_UNIDADES");
    APO_SALDO_CUENTA_CONTINGENTENdx = findColumn("APO_SALDO_CUENTA_CONTINGENTE");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double APR_APO_CONSECUTIVO()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APR_APO_CONSECUTIVONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APR_APO_CONSECUTIVONdx;
  public double APR_CAPITAL()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APR_CAPITALNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APR_CAPITALNdx;
  public double APR_RENDIMIENTOS()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APR_RENDIMIENTOSNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APR_RENDIMIENTOSNdx;
  public double APR_PORCENTAJE_PENALIZACION()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APR_PORCENTAJE_PENALIZACIONNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APR_PORCENTAJE_PENALIZACIONNdx;
  public double CAE_VALOR()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(CAE_VALORNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int CAE_VALORNdx;
  public String CAE_REF_CARGO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CAE_REF_CARGONdx);
  }
  private int CAE_REF_CARGONdx;
  public double APO_SALDO_NUMERO_UNIDADES()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_SALDO_NUMERO_UNIDADESNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_SALDO_NUMERO_UNIDADESNdx;
  public double APO_SALDO_CUENTA_CONTINGENTE()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_SALDO_CUENTA_CONTINGENTENdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_SALDO_CUENTA_CONTINGENTENdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:24^260*/
/*@lineinfo:generated-code*//*@lineinfo:25^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONCARGORET
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONCARGORET(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    SUM_CAE_VALORNdx = findColumn("SUM_CAE_VALOR");
    CAE_REF_CARGONdx = findColumn("CAE_REF_CARGO");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double SUM_CAE_VALOR()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(SUM_CAE_VALORNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int SUM_CAE_VALORNdx;
  public String CAE_REF_CARGO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CAE_REF_CARGONdx);
  }
  private int CAE_REF_CARGONdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:25^83*/
/*@lineinfo:generated-code*//*@lineinfo:26^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONAPORTE2
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONAPORTE2(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    APO_SALDO_CUENTA_CONTINGENTENdx = findColumn("APO_SALDO_CUENTA_CONTINGENTE");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double APO_SALDO_CUENTA_CONTINGENTE()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_SALDO_CUENTA_CONTINGENTENdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_SALDO_CUENTA_CONTINGENTENdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:26^75*/
/*@lineinfo:generated-code*//*@lineinfo:27^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class ITVALORDB
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public ITVALORDB(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    VALORDBNdx = findColumn("VALORDB");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double VALORDB()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(VALORDBNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int VALORDBNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:27^53*/
/*@lineinfo:generated-code*//*@lineinfo:28^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class ITVALORINT
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public ITVALORINT(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    VALORINTNdx = findColumn("VALORINT");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public int VALORINT()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(VALORINTNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int VALORINTNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:28^52*/
/*@lineinfo:generated-code*//*@lineinfo:29^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class ITAPORTE
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public ITAPORTE(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    APO_SALDO_CAPITALNdx = findColumn("APO_SALDO_CAPITAL");
    APO_SALDO_CUENTA_CONTINGENTENdx = findColumn("APO_SALDO_CUENTA_CONTINGENTE");
    APO_SALDO_NUMERO_UNIDADESNdx = findColumn("APO_SALDO_NUMERO_UNIDADES");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
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
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:29^131*/
/*@lineinfo:generated-code*//*@lineinfo:30^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class ITAPORET
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public ITAPORET(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    APR_APO_CONSECUTIVONdx = findColumn("APR_APO_CONSECUTIVO");
    APR_CAPITALNdx = findColumn("APR_CAPITAL");
    APR_RENDIMIENTOSNdx = findColumn("APR_RENDIMIENTOS");
    APR_PORCENTAJE_PENALIZACIONNdx = findColumn("APR_PORCENTAJE_PENALIZACION");
    APO_SALDO_NUMERO_UNIDADESNdx = findColumn("APO_SALDO_NUMERO_UNIDADES");
    APO_SALDO_CUENTA_CONTINGENTENdx = findColumn("APO_SALDO_CUENTA_CONTINGENTE");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public int APR_APO_CONSECUTIVO()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(APR_APO_CONSECUTIVONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APR_APO_CONSECUTIVONdx;
  public double APR_CAPITAL()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APR_CAPITALNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APR_CAPITALNdx;
  public double APR_RENDIMIENTOS()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APR_RENDIMIENTOSNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APR_RENDIMIENTOSNdx;
  public int APR_PORCENTAJE_PENALIZACION()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(APR_PORCENTAJE_PENALIZACIONNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APR_PORCENTAJE_PENALIZACIONNdx;
  public double APO_SALDO_NUMERO_UNIDADES()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_SALDO_NUMERO_UNIDADESNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_SALDO_NUMERO_UNIDADESNdx;
  public double APO_SALDO_CUENTA_CONTINGENTE()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_SALDO_CUENTA_CONTINGENTENdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_SALDO_CUENTA_CONTINGENTENdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:30^205*/
/*@lineinfo:generated-code*//*@lineinfo:31^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class ITAPOCAR
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public ITAPOCAR(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    CAE_APR_APO_CONSECUTIVONdx = findColumn("CAE_APR_APO_CONSECUTIVO");
    CAE_VALORNdx = findColumn("CAE_VALOR");
    CAE_REF_CARGONdx = findColumn("CAE_REF_CARGO");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public int CAE_APR_APO_CONSECUTIVO()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(CAE_APR_APO_CONSECUTIVONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int CAE_APR_APO_CONSECUTIVONdx;
  public double CAE_VALOR()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(CAE_VALORNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int CAE_VALORNdx;
  public String CAE_REF_CARGO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CAE_REF_CARGONdx);
  }
  private int CAE_REF_CARGONdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:31^103*/
/*@lineinfo:generated-code*//*@lineinfo:32^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class ITAPORTE1
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public ITAPORTE1(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    APO_CONSECUTIVONdx = findColumn("APO_CONSECUTIVO");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public int APO_CONSECUTIVO()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(APO_CONSECUTIVONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_CONSECUTIVONdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:32^58*/

/*@lineinfo:generated-code*//*@lineinfo:34^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONAJUSTES
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONAJUSTES(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    AJU_CONSECUTIVONdx = findColumn("AJU_CONSECUTIVO");
    AJU_LINEANdx = findColumn("AJU_LINEA");
    AJU_FECHA_PROCESONdx = findColumn("AJU_FECHA_PROCESO");
    AJU_VALORNdx = findColumn("AJU_VALOR");
    AJU_RAZON_AJUSTENdx = findColumn("AJU_RAZON_AJUSTE");
    AJU_USUARIONdx = findColumn("AJU_USUARIO");
    AJU_RETIRO_ORIGINALNdx = findColumn("AJU_RETIRO_ORIGINAL");
    AJU_RETIRO_ACTUALNdx = findColumn("AJU_RETIRO_ACTUAL");
    AJU_VALOR_UNIDADNdx = findColumn("AJU_VALOR_UNIDAD");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public int AJU_CONSECUTIVO()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(AJU_CONSECUTIVONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int AJU_CONSECUTIVONdx;
  public int AJU_LINEA()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(AJU_LINEANdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int AJU_LINEANdx;
  public java.sql.Date AJU_FECHA_PROCESO()
    throws java.sql.SQLException
  {
    return (java.sql.Date)m_rs.getDate(AJU_FECHA_PROCESONdx);
  }
  private int AJU_FECHA_PROCESONdx;
  public double AJU_VALOR()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(AJU_VALORNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int AJU_VALORNdx;
  public String AJU_RAZON_AJUSTE()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(AJU_RAZON_AJUSTENdx);
  }
  private int AJU_RAZON_AJUSTENdx;
  public String AJU_USUARIO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(AJU_USUARIONdx);
  }
  private int AJU_USUARIONdx;
  public int AJU_RETIRO_ORIGINAL()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(AJU_RETIRO_ORIGINALNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int AJU_RETIRO_ORIGINALNdx;
  public int AJU_RETIRO_ACTUAL()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(AJU_RETIRO_ACTUALNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int AJU_RETIRO_ACTUALNdx;
  public double AJU_VALOR_UNIDAD()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(AJU_VALOR_UNIDADNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int AJU_VALOR_UNIDADNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:34^226*/
/*@lineinfo:generated-code*//*@lineinfo:35^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONCARGOAJU
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONCARGOAJU(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    CAA_VALORNdx = findColumn("CAA_VALOR");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double CAA_VALOR()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(CAA_VALORNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int CAA_VALORNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:35^57*/
/*@lineinfo:generated-code*//*@lineinfo:36^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONAPORAJUS
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONAPORAJUS(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    APA_CAPITALNdx = findColumn("APA_CAPITAL");
    APA_RENDIMIENTOSNdx = findColumn("APA_RENDIMIENTOS");
    APA_CUENTA_CONTINGENTENdx = findColumn("APA_CUENTA_CONTINGENTE");
    APA_NUMERO_UNIDADESNdx = findColumn("APA_NUMERO_UNIDADES");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double APA_CAPITAL()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APA_CAPITALNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APA_CAPITALNdx;
  public double APA_RENDIMIENTOS()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APA_RENDIMIENTOSNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APA_RENDIMIENTOSNdx;
  public double APA_CUENTA_CONTINGENTE()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APA_CUENTA_CONTINGENTENdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APA_CUENTA_CONTINGENTENdx;
  public double APA_NUMERO_UNIDADES()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APA_NUMERO_UNIDADESNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APA_NUMERO_UNIDADESNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:36^140*/
/*@lineinfo:generated-code*//*@lineinfo:37^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONTRATONOMBAPEL
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONTRATONOMBAPEL(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    CON_NOMBRESNdx = findColumn("CON_NOMBRES");
    CON_APELLIDOSNdx = findColumn("CON_APELLIDOS");
    CON_FECHA_CANCELACIONNdx = findColumn("CON_FECHA_CANCELACION");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String CON_NOMBRES()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_NOMBRESNdx);
  }
  private int CON_NOMBRESNdx;
  public String CON_APELLIDOS()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_APELLIDOSNdx);
  }
  private int CON_APELLIDOSNdx;
  public java.sql.Date CON_FECHA_CANCELACION()
    throws java.sql.SQLException
  {
    return (java.sql.Date)m_rs.getDate(CON_FECHA_CANCELACIONNdx);
  }
  private int CON_FECHA_CANCELACIONNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:37^112*/
/*@lineinfo:generated-code*//*@lineinfo:38^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class I_DECISIONAJUSTE
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public I_DECISIONAJUSTE(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    DECISIONNdx = findColumn("DECISION");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String DECISION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(DECISIONNdx);
  }
  private int DECISIONNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:38^61*/
/*@lineinfo:generated-code*//*@lineinfo:39^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class I_INTERFACERET
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public I_INTERFACERET(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    INL_PASONdx = findColumn("INL_PASO");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String INL_PASO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(INL_PASONdx);
  }
  private int INL_PASONdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:39^59*/



public static String err=new String();

//////////////////Llamado principal en la Decision de Ajustar contrato///////////////////////
/*
  modificado Noviembre 27 de 2001
  por Diego V
*/
public static String TBPBD_AjustarContrato(String cod_producto,
                                           String num_contrato,
                                           int consecRet,
                                           int consecRetP,
                                           int consecAjus,
                                           int linea,
                                           double valUnidad){
    String strProp = "NO";
    if (valUnidad == 0) return "EL VALOR DE LA UNIDAD ES CERO";
    try{
      CONCARGORET cargoret;
      CONTOTRETCARGO  tot_aportes_retiros;
      ITVALORINT  por_retencion;
      ITAPORET aportes_retiros;
      ITAPOCAR aportes_cargos;
      int ii = 1,jj = 0;
      String v_ii    = "";
      String v_cargo = "";
      double sumCargos[][] = new double [2][4];     //guarda sumatoria de cargos para r y r'
      double matrizAjustes[][]  = null;               //guardar c/u de cargos r' y proporcionalidad
      double ajustes[][]  = null;               //guardar c/u de los ajustes


      // Porcentaje de beneficio para el cálculo de los ajustes
      /*@lineinfo:generated-code*//*@lineinfo:74^7*/

//  ************************************************************
//  #sql por_retencion = { SELECT PRO_RETENCION_FUENTE VALORINT
//                      FROM TBPRODUCTOS
//                      WHERE PRO_CODIGO = :cod_producto };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT PRO_RETENCION_FUENTE VALORINT\n                    FROM TBPRODUCTOS\n                    WHERE PRO_CODIGO =  :1 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   // execute query
   por_retencion = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.ITVALORINT(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"0TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:77^53*/
      double porBeneficio = 0;
      if (por_retencion.next()){
        porBeneficio = por_retencion.VALORINT();
        porBeneficio /= 100;
      }
      por_retencion.close();

      //sumatoria de r de los cargos retiros para calcular proporcionalidad
      /*@lineinfo:generated-code*//*@lineinfo:86^7*/

//  ************************************************************
//  #sql cargoret = { SELECT NVL(SUM(CAE_VALOR),0) SUM_CAE_VALOR
//       	                    , CAE_REF_CARGO
//                         FROM
//                          TBCARGOS_RETIROS
//                         WHERE
//                           CAE_APR_RET_CON_PRO_CODIGO  =:cod_producto
//                           AND CAE_APR_RET_CON_NUMERO  =:num_contrato
//                           AND CAE_APR_RET_CONSECUTIVO =:consecRet
//                         GROUP BY CAE_REF_CARGO };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT NVL(SUM(CAE_VALOR),0) SUM_CAE_VALOR\n     \t                    , CAE_REF_CARGO\n                       FROM\n                        TBCARGOS_RETIROS\n                       WHERE\n                         CAE_APR_RET_CON_PRO_CODIGO  = :1  \n                         AND CAE_APR_RET_CON_NUMERO  = :2  \n                         AND CAE_APR_RET_CONSECUTIVO = :3  \n                       GROUP BY CAE_REF_CARGO";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"1TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecRet);
   // execute query
   cargoret = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.CONCARGORET(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"1TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:94^46*/

      while(cargoret.next())
        sumCargos[0][Integer.parseInt(cargoret.CAE_REF_CARGO().substring(cargoret.CAE_REF_CARGO().length() - 1))-1] = cargoret.SUM_CAE_VALOR();
      cargoret.close();

      //sumatoria de r' de los cargos retiros para calcular proporcionalidad
      /*@lineinfo:generated-code*//*@lineinfo:101^7*/

//  ************************************************************
//  #sql cargoret = { SELECT NVL(SUM(CAE_VALOR),0) SUM_CAE_VALOR
//       	                    , CAE_REF_CARGO
//                         FROM
//                          TBCARGOS_RETIROS
//                         WHERE
//                           CAE_APR_RET_CON_PRO_CODIGO  =:cod_producto
//                           AND CAE_APR_RET_CON_NUMERO  =:num_contrato
//                           AND CAE_APR_RET_CONSECUTIVO =:consecRetP
//                         GROUP BY CAE_REF_CARGO };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT NVL(SUM(CAE_VALOR),0) SUM_CAE_VALOR\n     \t                    , CAE_REF_CARGO\n                       FROM\n                        TBCARGOS_RETIROS\n                       WHERE\n                         CAE_APR_RET_CON_PRO_CODIGO  = :1  \n                         AND CAE_APR_RET_CON_NUMERO  = :2  \n                         AND CAE_APR_RET_CONSECUTIVO = :3  \n                       GROUP BY CAE_REF_CARGO";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"2TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecRetP);
   // execute query
   cargoret = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.CONCARGORET(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"2TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:109^46*/

      while(cargoret.next())
        sumCargos[1][Integer.parseInt(cargoret.CAE_REF_CARGO().substring(cargoret.CAE_REF_CARGO().length() - 1))-1] = cargoret.SUM_CAE_VALOR();
      cargoret.close();

      // retiros por aporte
      /*@lineinfo:generated-code*//*@lineinfo:116^7*/

//  ************************************************************
//  #sql aportes_retiros = { SELECT	APR_APO_CONSECUTIVO
//              			, APR_CAPITAL
//              			, APR_RENDIMIENTOS
//                    , APR_PORCENTAJE_PENALIZACION
//                    , APO_SALDO_NUMERO_UNIDADES
//                    , APO_SALDO_CUENTA_CONTINGENTE
//              FROM 		TBAPORTES_RETIROS
//                    , TBAPORTES
//              WHERE 	APR_RET_CON_PRO_CODIGO = :cod_producto
//              		AND APR_RET_CON_NUMERO     = :num_contrato
//              		AND APR_RET_CONSECUTIVO    = :consecRetP
//                  AND APO_CON_PRO_CODIGO = APR_RET_CON_PRO_CODIGO
//  		            AND APO_CON_NUMERO     = APR_RET_CON_NUMERO
//              		AND APO_CONSECUTIVO    = APR_APO_CONSECUTIVO
//              ORDER BY APR_APO_CONSECUTIVO };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT\tAPR_APO_CONSECUTIVO\n            \t\t\t, APR_CAPITAL\n            \t\t\t, APR_RENDIMIENTOS\n                  , APR_PORCENTAJE_PENALIZACION\n                  , APO_SALDO_NUMERO_UNIDADES\n                  , APO_SALDO_CUENTA_CONTINGENTE\n            FROM \t\tTBAPORTES_RETIROS\n                  , TBAPORTES\n            WHERE \tAPR_RET_CON_PRO_CODIGO =  :1  \n            \t\tAND APR_RET_CON_NUMERO     =  :2  \n            \t\tAND APR_RET_CONSECUTIVO    =  :3  \n                AND APO_CON_PRO_CODIGO = APR_RET_CON_PRO_CODIGO\n\t\t            AND APO_CON_NUMERO     = APR_RET_CON_NUMERO\n            \t\tAND APO_CONSECUTIVO    = APR_APO_CONSECUTIVO\n            ORDER BY APR_APO_CONSECUTIVO";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"3TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecRetP);
   // execute query
   aportes_retiros = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.ITAPORET(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"3TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:131^41*/

      // total de capital + rendimientos para retiros por aporte
      /*@lineinfo:generated-code*//*@lineinfo:134^7*/

//  ************************************************************
//  #sql tot_aportes_retiros = { SELECT NVL(SUM(APR_CAPITAL +APR_RENDIMIENTOS), 0)  CAPREN
//   						          , COUNT(APR_CAPITAL) LON
//                  FROM 		TBAPORTES_RETIROS
//                      , TBAPORTES
//                  WHERE  APR_RET_CON_PRO_CODIGO =  :cod_producto
//                            AND APR_RET_CON_NUMERO     = :num_contrato
//                            AND APR_RET_CONSECUTIVO    = :consecRetP
//                            AND APO_CON_PRO_CODIGO = APR_RET_CON_PRO_CODIGO
//                            AND APO_CON_NUMERO     = APR_RET_CON_NUMERO
//                            AND APO_CONSECUTIVO    = APR_APO_CONSECUTIVO };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT NVL(SUM(APR_CAPITAL +APR_RENDIMIENTOS), 0)  CAPREN\n \t\t\t\t\t\t          , COUNT(APR_CAPITAL) LON\n                FROM \t\tTBAPORTES_RETIROS\n                    , TBAPORTES\n                WHERE  APR_RET_CON_PRO_CODIGO =   :1  \n                          AND APR_RET_CON_NUMERO     =  :2  \n                          AND APR_RET_CONSECUTIVO    =  :3  \n                          AND APO_CON_PRO_CODIGO = APR_RET_CON_PRO_CODIGO\n                          AND APO_CON_NUMERO     = APR_RET_CON_NUMERO\n                          AND APO_CONSECUTIVO    = APR_APO_CONSECUTIVO";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"4TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecRetP);
   // execute query
   tot_aportes_retiros = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.CONTOTRETCARGO(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"4TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:144^71*/
      double sumKR = 1;
      int lonmatrizAjustes = 0;
      if(tot_aportes_retiros.next()){
        sumKR = tot_aportes_retiros.CAPREN();
        lonmatrizAjustes = tot_aportes_retiros.LON();
      }
      tot_aportes_retiros.close();
      if (sumKR == 0) sumKR = 1;

      matrizAjustes = new double[lonmatrizAjustes][13];

      int filaAp = 0;
      int conPro, posCargo;
      double proporcion = 0.0;

//  Se llena la matriz para calcular los ajustes
      // Datos del aporte
      while(aportes_retiros.next()){
        matrizAjustes[filaAp][0] = aportes_retiros.APR_APO_CONSECUTIVO(); // Columna 0 aporte
        matrizAjustes[filaAp][1] = aportes_retiros.APR_CAPITAL();         // Columna 1 capital
        matrizAjustes[filaAp][2] = aportes_retiros.APR_RENDIMIENTOS();    //  Columna 2 rendimientos
        // Columna 11 Porcentaje de Penalización
        matrizAjustes[filaAp][11] = aportes_retiros.APR_PORCENTAJE_PENALIZACION();
        matrizAjustes[filaAp][11] /= 100;
        // Columna 12 Saldo Cuenta contingente
        matrizAjustes[filaAp][12] = aportes_retiros.APO_SALDO_CUENTA_CONTINGENTE();
        filaAp++;
      }
      aportes_retiros.close();

      // cargos por aporte
      /*@lineinfo:generated-code*//*@lineinfo:176^7*/

//  ************************************************************
//  #sql aportes_cargos = { SELECT	CAE_APR_APO_CONSECUTIVO
//                    , CAE_VALOR
//              			, CAE_REF_CARGO
//              FROM 		TBCARGOS_RETIROS
//              WHERE 	CAE_APR_RET_CON_PRO_CODIGO  = :cod_producto
//                  AND CAE_APR_RET_CON_NUMERO      = :num_contrato
//                  AND CAE_APR_RET_CONSECUTIVO     = :consecRetP
//              ORDER BY CAE_APR_APO_CONSECUTIVO };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT\tCAE_APR_APO_CONSECUTIVO\n                  , CAE_VALOR\n            \t\t\t, CAE_REF_CARGO\n            FROM \t\tTBCARGOS_RETIROS\n            WHERE \tCAE_APR_RET_CON_PRO_CODIGO  =  :1  \n                AND CAE_APR_RET_CON_NUMERO      =  :2  \n                AND CAE_APR_RET_CONSECUTIVO     =  :3  \n            ORDER BY CAE_APR_APO_CONSECUTIVO";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"5TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecRetP);
   // execute query
   aportes_cargos = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.ITAPOCAR(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"5TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:184^45*/
      // Datos de cargos
      while (aportes_cargos.next()){
        filaAp = 0;
        while(matrizAjustes[filaAp][0] != aportes_cargos.CAE_APR_APO_CONSECUTIVO()) filaAp++;
        posCargo = Integer.parseInt(aportes_cargos.CAE_REF_CARGO().substring(aportes_cargos.CAE_REF_CARGO().length() - 1))+2;
        matrizAjustes[filaAp][posCargo] = aportes_cargos.CAE_VALOR(); // Columnas 3-6 Valor cargo
      }
      aportes_cargos.close();

      // Datos preajustes calculados
      for (filaAp=0; filaAp<lonmatrizAjustes; filaAp++){
        // Fila 7-10 Proporcionalidad de cargos ajustes
        for (int colAp=7; colAp<11; colAp++){
          if (sumCargos[1][colAp-7] != 0.0){
            proporcion = (sumCargos[0][colAp-7] - sumCargos[1][colAp-7]) * matrizAjustes[filaAp][colAp-4]/sumCargos[1][colAp-7];
          }else{
            proporcion = sumCargos[0][colAp-7] * (matrizAjustes[filaAp][1]+matrizAjustes[filaAp][2])/sumKR;
            }
          matrizAjustes[filaAp][colAp] = proporcion;
        }
      }

      aportes_cargos.close();
      ajustes = new double [lonmatrizAjustes][8];
      double division;
      // Calculo de los ajustes
      for (filaAp=0; filaAp<lonmatrizAjustes; filaAp++){
        // 0 Aporte
        ajustes[filaAp][0] = matrizAjustes[filaAp][0];
        // 1 Ajuste a capital
        if (matrizAjustes[filaAp][1] != 0.0){
          division = matrizAjustes[filaAp][4];
          division /= matrizAjustes[filaAp][1];
        }else{
          division = 0.0;
        };
        ajustes[filaAp][1] = (matrizAjustes[filaAp][8]+matrizAjustes[filaAp][10]);
        ajustes[filaAp][1] /=(1-(division+matrizAjustes[filaAp][11]));
        // 2 Ajuste a rendimientos
        ajustes[filaAp][2] = (matrizAjustes[filaAp][7]+matrizAjustes[filaAp][9]);
        if(matrizAjustes[filaAp][5] != 0)
          ajustes[filaAp][2] /=(1-(porBeneficio+matrizAjustes[filaAp][11]));
        else
          ajustes[filaAp][2] /=(1-(porBeneficio));
        // 3 Ajuste a STC001
        ajustes[filaAp][3] = ajustes[filaAp][2]* porBeneficio;
        // 4 Ajuste a STC002
        ajustes[filaAp][4] = division * ajustes[filaAp][1];
        // 5 Ajuste a STC003
        if(matrizAjustes[filaAp][5] != 0)
          ajustes[filaAp][5] = ajustes[filaAp][2]*matrizAjustes[filaAp][11];
        // 6 Ajuste a STC004
        ajustes[filaAp][6] = ajustes[filaAp][1]*matrizAjustes[filaAp][11];
        // 7 Cuenta Contingente del aporte
        ajustes[filaAp][7] = matrizAjustes[filaAp][12];
      }

//  Ajuste para cada aporte
      String v_rta  = "";
      int consecApo;
      double deltaC, deltaR, deltaRetCap;
      double[] salApor = new double[2];
      double  retRend = 0, retCap = 0, penRend = 0, penCap = 0;


      for (filaAp=0; filaAp<lonmatrizAjustes; filaAp++){
        consecApo = (int) ajustes[filaAp][0];
        salApor = TBPBD_CalSalAporte(num_contrato,cod_producto,consecApo,valUnidad);
//  Cargosn ajustes
        retRend += ajustes[filaAp][3];
        retCap += ajustes[filaAp][4];
        penRend += ajustes[filaAp][5];
        penCap += ajustes[filaAp][6];

        if(ajustes[filaAp][1] >= 0 && ajustes[filaAp][2] >= 0){ // Capital>=0 & Rendimientos>=0
          v_rta = TBPBD_ActualizarBD(cod_producto, num_contrato, consecApo, consecAjus, linea
                          , ajustes[filaAp][1]  // Capital
                          , ajustes[filaAp][2]  // rendimientos
                          , ajustes[filaAp][4]  // retCap
                          , valUnidad );        // Valor de la unidad
          if(!v_rta.equalsIgnoreCase("YES"))
                      return v_rta;
        }
        else
          if(ajustes[filaAp][1] >= 0 && ajustes[filaAp][2] < 0){ // Capital>=0 & Rendimientos < 0
            deltaR = ajustes[filaAp][2]+ salApor[1];
            if (deltaR >= 0){ // Rendimientos a retirar + Rendimientos del aporte >= 0
                v_rta = TBPBD_ActualizarBD(cod_producto, num_contrato, consecApo, consecAjus, linea
                          , ajustes[filaAp][1]  // Capital
                          , ajustes[filaAp][2]  // rendimientos
                          , ajustes[filaAp][4]  // retCap
                          , valUnidad );        // Valor de la unidad
                if(!v_rta.equalsIgnoreCase("YES"))
                      return v_rta;
            }
            else{
              if (salApor[1] > 0)
                v_rta = TBPBD_ActualizarBD(cod_producto, num_contrato, consecApo, consecAjus, linea
                          , ajustes[filaAp][1]  // Capital
                          , salApor[1]*(-1)  // rendimientos
                          , ajustes[filaAp][4]  // retCap
                          , valUnidad  );       // Valor de la unidad
              else {
                v_rta = "YES";
                deltaR = ajustes[filaAp][2];
              }
              // Busca otros aportes para sacar lo que falta de RendimientosS
              if(v_rta.equalsIgnoreCase("YES"))
                v_rta = TBPBD_BuscarOtroAporte( cod_producto, num_contrato, consecApo, consecAjus, linea
                          , 0  // Capital
                          , deltaR  // rendimientos
                          , ajustes[filaAp][4]  // retCap
                          , valUnidad           // Valor de la unidad
                          , false);              // Respetar cuenta contingente
              if(!v_rta.equalsIgnoreCase("YES"))
                      return v_rta;
            }
          }else
            if(ajustes[filaAp][1] < 0 && ajustes[filaAp][2] >= 0){ //  Capital<0 & Rendimientos >= 0
              deltaC = ajustes[filaAp][1]+ salApor[0];
              deltaRetCap = ajustes[filaAp][7] + ajustes[filaAp][4];
              if (deltaC >= 0){ // Capital a retirar + Capital del aporte >= 0
                v_rta = TBPBD_ActualizarBD(cod_producto, num_contrato, consecApo, consecAjus, linea
                          , ajustes[filaAp][1]  // Capital
                          , ajustes[filaAp][2]  // rendimientos
                          , ajustes[filaAp][4]  // retCap
                          , valUnidad );        // Valor de la unidad
                if(!v_rta.equalsIgnoreCase("YES"))
                      return v_rta;
              }
              else{
                v_rta = TBPBD_ActualizarBD(cod_producto, num_contrato, consecApo, consecAjus, linea
                          , salApor[0]*(-1)  // Capital
                          , ajustes[filaAp][2]  // rendimientos
                          , ajustes[filaAp][7]*(-1)  // retCap
                          , valUnidad );        // Valor de la unidad
              if(v_rta.equalsIgnoreCase("YES"))
                // Busca otros aportes para sacar lo que falta de Capital
                v_rta = TBPBD_BuscarOtroAporte( cod_producto, num_contrato, consecApo, consecAjus, linea
                          , deltaC  // Capital
                          , 0  // rendimientos
                          , deltaRetCap         // retCap
                          , valUnidad           // Valor de la unidad
                          , true);              // Respetar cuenta contingente
              if(!v_rta.equalsIgnoreCase("YES"))
                      return v_rta;
              }
            }else
              if(ajustes[filaAp][1] < 0 && ajustes[filaAp][2] < 0){ // Capital < 0 & Rendimientos < 0
                deltaC = ajustes[filaAp][1]+ salApor[0];
                deltaR = ajustes[filaAp][2]+ salApor[1];
                deltaRetCap = ajustes[filaAp][7] + ajustes[filaAp][4];
                if (deltaC >= 0){ // Capital a retirar + Capital del aporte >= 0
                  if (deltaR >= 0){ // Rendimientos a retirar + Rendimientos del aporte >= 0
                    v_rta = TBPBD_ActualizarBD(cod_producto, num_contrato, consecApo, consecAjus, linea
                          , ajustes[filaAp][1]  // Capital
                          , ajustes[filaAp][2]  // rendimientos
                          , ajustes[filaAp][4]  // retCap
                          , valUnidad );        // Valor de la unidad
                    if(!v_rta.equalsIgnoreCase("YES"))
                      return v_rta;
                  }
                  else{
                  //***************
                    if (salApor[1] > 0)
                      v_rta = TBPBD_ActualizarBD(cod_producto, num_contrato, consecApo, consecAjus, linea
                          , ajustes[filaAp][1]  // Capital
                          , salApor[1]*(-1)  // rendimientos
                          , ajustes[filaAp][4]  // retCap
                          , valUnidad );        // Valor de la unidad
                    else {
                      v_rta = "YES";
                      deltaR = ajustes[filaAp][2];
                    }
                    // Busca otros aportes para sacar lo que falta de RendimientosS
                    if(v_rta.equalsIgnoreCase("YES"))
                      v_rta = TBPBD_BuscarOtroAporte( cod_producto, num_contrato, consecApo, consecAjus, linea
                          , 0  // Capital
                          , deltaR  // rendimientos
                          , ajustes[filaAp][4]  // retCap
                          , valUnidad           // Valor de la unidad
                          , true);              // Respetar cuenta contingente
                    if(!v_rta.equalsIgnoreCase("YES"))
                      return v_rta;
                  }
                }else{ // Capital a retirar + Capital del aporte < 0
                  if (deltaR >= 0){ // Rendimientos a retirar + Rendimientos del aporte >= 0
                    v_rta = TBPBD_ActualizarBD(cod_producto, num_contrato, consecApo, consecAjus, linea
                          , salApor[0]*(-1)  // Capital
                          , ajustes[filaAp][2]  // rendimientos
                          , ajustes[filaAp][7]*(-1)  // retCap
                          , valUnidad );        // Valor de la unidad
                    // Busca otros aportes para sacar lo que falta de capital
                    if(v_rta.equalsIgnoreCase("YES"))
                        v_rta = TBPBD_BuscarOtroAporte( cod_producto, num_contrato, consecApo, consecAjus, linea
                          , deltaC  // Capital
                          , 0  // rendimientos
                          , deltaRetCap         // retCap
                          , valUnidad           // Valor de la unidad
                          , true);              // Respetar cuenta contingente
                    if(!v_rta.equalsIgnoreCase("YES"))
                      return v_rta;
                  }
                  else{
                                      //***************
                    if (salApor[1] > 0)
                      v_rta = TBPBD_ActualizarBD(cod_producto, num_contrato, consecApo, consecAjus, linea
                          , salApor[0]*(-1)  // Capital
                          , salApor[1]*(-1)  // rendimientos
                          , ajustes[filaAp][7]*(-1)  // retCap
                          , valUnidad );        // Valor de la unidad
                    else {
                      v_rta = "YES";
                      deltaR = ajustes[filaAp][2];
                    }
                    // Busca otros aportes para sacar lo que falta de Capital y RendimientosS
                    if(v_rta.equalsIgnoreCase("YES"))
                        v_rta = TBPBD_BuscarOtroAporte( cod_producto, num_contrato, consecApo, consecAjus, linea
                          , deltaC  // Capital
                          , deltaR  // rendimientos
                          , deltaRetCap         // retCap
                          , valUnidad           // Valor de la unidad
                          , true);              // Respetar cuenta contingente
                    if(!v_rta.equalsIgnoreCase("YES"))
                      return v_rta;
                    }
                  }
              }
      } // for
      //actualizar cargos ajustes del aporte
      v_rta = TBPBD_UpdateCargosAjustes(cod_producto, num_contrato, consecAjus, linea
                            , retRend
                            , retCap
                            , penRend
                            , penCap);
      if(!v_rta.equalsIgnoreCase("YES"))
        return v_rta;
 strProp="YES";
}
catch(Exception ex)
{
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

return "Exception en TBPBD_AjustarContrato por "+v_menex;}
return strProp;
}

/*
  Actualiza:  aportes ajustes
              y saldos del aporte
*/
 public static String TBPBD_ActualizarBD( String cod_producto
                                        , String num_contrato
                                        , int consecApo
                                        , int consecAjus
                                        , int linea
                                        , double capital
                                        , double rendimientos
                                        , double retCap
                                        , double valUnidad){
    String v_rta  = "YES";
    double numUnidades = (capital+rendimientos);
    numUnidades /= valUnidad;

    if (capital != 0.0 || rendimientos != 0.0){
      v_rta = TBPBD_AportesAjustes(cod_producto,num_contrato,consecApo,consecAjus,linea
                      , capital, rendimientos, numUnidades, retCap);
      if(v_rta.equalsIgnoreCase("YES")){//Actualizar saldos del aporte
        v_rta = TBPBD_SaldosApor(cod_producto,num_contrato,consecApo,capital,numUnidades, retCap);
        if(!v_rta.equalsIgnoreCase("YES"))
          return "Exception en CalcAjustes actualizando saldosAportes por "+v_rta;
      }else
          return "Exception en CalcAjustes insertando en aportesajustes por "+v_rta;
    }
    return "YES";
  }

  
  public static boolean TBPBD_Condicionado( String cod_producto
                                        , String num_contrato
                                        , int consecApo){
    boolean retCon = false;
    try{
        CONAPORTE2 condicion;
        /*@lineinfo:generated-code*//*@lineinfo:494^9*/

//  ************************************************************
//  #sql condicion = { SELECT APO_SALDO_CUENTA_CONTINGENTE
//                          FROM TBAPORTES
//                          WHERE APO_CON_PRO_CODIGO = :cod_producto
//                            AND APO_CON_NUMERO = :num_contrato
//  		                      AND APO_CONSECUTIVO    = :consecApo };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT APO_SALDO_CUENTA_CONTINGENTE\n                        FROM TBAPORTES\n                        WHERE APO_CON_PRO_CODIGO =  :1  \n                          AND APO_CON_NUMERO =  :2  \n\t\t                      AND APO_CONSECUTIVO    =  :3 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"6TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecApo);
   // execute query
   condicion = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.CONAPORTE2(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"6TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:498^60*/
        condicion.next();
        if (condicion.APO_SALDO_CUENTA_CONTINGENTE() > 0)
          retCon = true;
        else
          retCon = false;
    }catch(Exception e){return false;}
    return retCon;
  }

/*
    Busca otro aporte y actualiza
*/
  public static String TBPBD_BuscarOtroAporte( String cod_producto
                                        , String num_contrato
                                        , int consecApo
                                        , int consecAjus
                                        , int linea
                                        , double capital
                                        , double rendimientos
                                        , double retCap
                                        , double valUnidad
                                        , boolean resCondicion){

    double deltaC, deltaR;
    int consecApoO;
    String v_rta = "";

    try{
        ITAPORTE1 aportes;
        //Selecciona otros aportes del contrato que no sean aportes padre 
        if (resCondicion && TBPBD_Condicionado(cod_producto, num_contrato, consecApo))
          /*@lineinfo:generated-code*//*@lineinfo:530^11*/

//  ************************************************************
//  #sql aportes = { SELECT APO_CONSECUTIVO
//                           FROM TBAPORTES
//                           WHERE APO_CON_PRO_CODIGO=:cod_producto
//                             AND APO_CON_NUMERO=:num_contrato
//                             AND APO_SALDO_CUENTA_CONTINGENTE > 0
//                             AND APO_REF_ESTADO = 'SEA001'
//                             AND APO_INFORMACION_TRASLADO = 'N'
//                           ORDER BY APO_FECHA_EFECTIVA DESC };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT APO_CONSECUTIVO\n                         FROM TBAPORTES\n                         WHERE APO_CON_PRO_CODIGO= :1  \n                           AND APO_CON_NUMERO= :2  \n                           AND APO_SALDO_CUENTA_CONTINGENTE > 0\n                           AND APO_REF_ESTADO = 'SEA001'\n                           AND APO_INFORMACION_TRASLADO = 'N'\n                         ORDER BY APO_FECHA_EFECTIVA DESC";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"7TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   // execute query
   aportes = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.ITAPORTE1(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"7TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:537^58*/
        else
          /*@lineinfo:generated-code*//*@lineinfo:539^11*/

//  ************************************************************
//  #sql aportes = { SELECT APO_CONSECUTIVO
//                           FROM TBAPORTES
//                           WHERE APO_CON_PRO_CODIGO=:cod_producto
//                             AND APO_CON_NUMERO=:num_contrato
//                             AND  APO_REF_ESTADO = 'SEA001'
//                             AND APO_INFORMACION_TRASLADO = 'N'                           
//                           ORDER BY APO_FECHA_EFECTIVA DESC };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT APO_CONSECUTIVO\n                         FROM TBAPORTES\n                         WHERE APO_CON_PRO_CODIGO= :1  \n                           AND APO_CON_NUMERO= :2  \n                           AND  APO_REF_ESTADO = 'SEA001'\n                           AND APO_INFORMACION_TRASLADO = 'N'                           \n                         ORDER BY APO_FECHA_EFECTIVA DESC";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"8TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   // execute query
   aportes = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.ITAPORTE1(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"8TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:545^58*/
        deltaC = capital;
        deltaR = rendimientos;
        double[] salApor = new double[2];
        while(aportes.next() && (deltaC < 0 || deltaR < 0)){//quitar  capital y/o rendimientos hasta que
          consecApoO = aportes.APO_CONSECUTIVO();
          salApor = TBPBD_CalSalAporte(num_contrato,cod_producto,consecApoO,valUnidad);
          // Sacar del capital
          if(deltaC != 0)
            if(deltaC + salApor[0] >= 0 ){
              v_rta = TBPBD_ActualizarBD(cod_producto, num_contrato, consecApoO, consecAjus, linea
                          , deltaC     // Capital
                          , 0           // rendimientos
                          , retCap      // retCap
                          , valUnidad );// Valor de la unidad
              deltaC = 0;
            }
            else{
              if (salApor[0] >0){
              deltaC += salApor[0];
              v_rta = TBPBD_ActualizarBD(cod_producto, num_contrato, consecApoO, consecAjus, linea
                          , salApor[0]*(-1)  // Capital
                          , 0               // rendimientos
                          , retCap          // retCap
                          , valUnidad );    // Valor de la unidad
              }
            }
          // Sacar de los rendimientos
          if(deltaR != 0)
            if(deltaR + salApor[1] >= 0){
              v_rta = TBPBD_ActualizarBD(cod_producto, num_contrato, consecApoO, consecAjus, linea
                          , 0  // Capital
                          , deltaR  // rendimientos
                          , retCap          // retCap
                          , valUnidad );    // Valor de la unidad
             deltaR = 0;
            }
            else{
              if(salApor[1] > 0){
                deltaR += salApor[1];
                v_rta = TBPBD_ActualizarBD(cod_producto, num_contrato, consecApoO, consecAjus, linea
                          , 0 // Capital
                          , salApor[1]*(-1) // rendimientos
                          , retCap          // retCap
                          , valUnidad );    // Valor de la unidad
              }
            }
          }
        aportes.close();
      }catch(Exception ex)
      {
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

      return "Exception en TBPBD_BuscarOtroAporte por "+v_menex;
      }
    if (deltaC < 0 || deltaR < 0)
      return "NO ALCANZA EL CAPITAL O LOS RENDIMIENTOS DE LOS APORTES DEL CONTRATO";
    return "YES";
  }

////////////constructor//////////////////
  public SQL_DAJUSTE(){
  }
  //////////////////Abrir conexión con la Base de Datos//////////////////////
  public static boolean TBPBD_ConexionBD(){
    try{
       /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_ValidarUsuario de la clase TBCL_Validacion, no es necesaria la instancia nueva*/ 
 //TBCL_Validacion i_valusu = new TBCL_Validacion(); 
 //TBCL_Validacion  i_valusu = new TBCL_Validacion()
       String[] v_valusu = new String[3];
       v_valusu=TBCL_Validacion.TBFL_ValidarUsuario();

       DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
       DefaultContext.setDefaultContext(new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false));
       return true;
     }
    catch(Exception e){
      System.out.print("");
      return false;
    }
  }

////Actualiza valores en cargos_ajustes despues de calcular el ajuste de cada retiro-aporte
 public static String TBPBD_UpdateCargosAjustes(String cod_producto,
                                                String num_contrato,
                                                int consecAjus,
                                                int linea,
                                                double retRend,
                                                double retCap,
                                                double penRend,
                                                double penCap){
    int ii=0;
    int colAj=0;
    String v_ii= "";
    String v_cargo= "";
    double cargos[]=new double[4];
    cargos[0]=retRend;cargos[1]=retCap;cargos[2]=penRend;cargos[3]=penCap;
    for(ii=0; ii<4; ii++){
      colAj=ii;
      v_ii="000"+(ii+1);
      v_ii = v_ii.substring(v_ii.length()-3);
      v_cargo="STC"+v_ii;
      try{
        boolean exist=false;
        double valor=0;
        CONCARGOAJU cargoAju;
        /*@lineinfo:generated-code*//*@lineinfo:676^9*/

//  ************************************************************
//  #sql cargoAju = { SELECT CAA_VALOR
//                            FROM TBCARGOS_AJUSTES
//                         WHERE CAA_AJU_CON_PRO_CODIGO=:cod_producto
//                           AND CAA_AJU_CON_NUMERO=:num_contrato
//                           AND CAA_REF_CARGO=:v_cargo
//                           AND CAA_AJU_CONSECUTIVO=:consecAjus
//                           AND CAA_AJU_LINEA=:linea };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT CAA_VALOR\n                          FROM TBCARGOS_AJUSTES\n                       WHERE CAA_AJU_CON_PRO_CODIGO= :1  \n                         AND CAA_AJU_CON_NUMERO= :2  \n                         AND CAA_REF_CARGO= :3  \n                         AND CAA_AJU_CONSECUTIVO= :4  \n                         AND CAA_AJU_LINEA= :5 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"9TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setString(3,v_cargo);
   __sJT_st.setInt(4,consecAjus);
   __sJT_st.setInt(5,linea);
   // execute query
   cargoAju = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.CONCARGOAJU(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"9TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:682^50*/
        if(cargoAju.next()){
          valor=cargoAju.CAA_VALOR();
          valor+=cargos[colAj];
          exist=true;
        }else
          exist=false;
        cargoAju.close();
        if(exist){//si existe el cargo  a actualizar
          /*@lineinfo:generated-code*//*@lineinfo:691^11*/

//  ************************************************************
//  #sql { UPDATE TBCARGOS_AJUSTES
//                      SET CAA_VALOR=:valor
//                 WHERE CAA_AJU_CON_PRO_CODIGO=:cod_producto
//                    AND CAA_AJU_CON_NUMERO=:num_contrato
//                    AND CAA_REF_CARGO=:v_cargo
//                    AND CAA_AJU_CONSECUTIVO=:consecAjus
//                    AND CAA_AJU_LINEA=:linea };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "UPDATE TBCARGOS_AJUSTES\n                    SET CAA_VALOR= :1  \n               WHERE CAA_AJU_CON_PRO_CODIGO= :2  \n                  AND CAA_AJU_CON_NUMERO= :3  \n                  AND CAA_REF_CARGO= :4  \n                  AND CAA_AJU_CONSECUTIVO= :5  \n                  AND CAA_AJU_LINEA= :6 ";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"10TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setDouble(1,valor);
   __sJT_st.setString(2,cod_producto);
   __sJT_st.setString(3,num_contrato);
   __sJT_st.setString(4,v_cargo);
   __sJT_st.setInt(5,consecAjus);
   __sJT_st.setInt(6,linea);
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:697^43*/
       }
      }catch(Exception ex){
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

        return "Exception en TBPBD_UPDATECARGOS_AJUSTES por "+ v_menex;
      }
    }
    return "YES";
  }
 ////////////////////////registrar el ajuste en aportes_ajustes /////////////////
  public static String TBPBD_AportesAjustes(String cod_producto,
                                            String num_contrato,
                                            int consecApo,
                                            int consecAjus,
                                            int linea,
                                            double capital,
                                            double rendimientos,
                                            double nUnid,
                                            double conting){
    //realizar el insert en aportes_ajustes
    double apo_cap=0,apo_conting=0;
    boolean v_exist=false;
    try{
      double v_cap=0,v_rend=0,v_conting=0,v_unid=0;
      CONAPORAJUS AporAjus;
      /*@lineinfo:generated-code*//*@lineinfo:748^7*/

//  ************************************************************
//  #sql AporAjus = { SELECT APA_CAPITAL,            APA_RENDIMIENTOS,
//                              APA_CUENTA_CONTINGENTE, APA_NUMERO_UNIDADES
//                          FROM TBAPORTES_AJUSTES
//                       WHERE APA_APO_CON_PRO_CODIGO=:cod_producto
//                         AND APA_APO_CON_NUMERO=:num_contrato
//                         AND APA_APO_CONSECUTIVO=:consecApo
//                         AND APA_AJU_CONSECUTIVO=:consecAjus
//                         AND APA_AJU_LINEA=:linea };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT APA_CAPITAL,            APA_RENDIMIENTOS,\n                            APA_CUENTA_CONTINGENTE, APA_NUMERO_UNIDADES\n                        FROM TBAPORTES_AJUSTES\n                     WHERE APA_APO_CON_PRO_CODIGO= :1  \n                       AND APA_APO_CON_NUMERO= :2  \n                       AND APA_APO_CONSECUTIVO= :3  \n                       AND APA_AJU_CONSECUTIVO= :4  \n                       AND APA_AJU_LINEA= :5 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"11TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecApo);
   __sJT_st.setInt(4,consecAjus);
   __sJT_st.setInt(5,linea);
   // execute query
   AporAjus = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.CONAPORAJUS(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"11TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:755^48*/
      if(AporAjus.next()){//si existe guarda valores anteriores y suma con lo que venga
        v_cap=capital+AporAjus.APA_CAPITAL();
        v_rend=rendimientos+AporAjus.APA_RENDIMIENTOS();
        v_unid=nUnid+AporAjus.APA_NUMERO_UNIDADES();
        v_conting=conting+AporAjus.APA_CUENTA_CONTINGENTE();
        v_exist=true;
      }
      AporAjus.close();
      if(v_exist){//si existe actualiza valores
       /*@lineinfo:generated-code*//*@lineinfo:765^8*/

//  ************************************************************
//  #sql { UPDATE TBAPORTES_AJUSTES
//                  SET APA_CAPITAL=:v_cap,
//                      APA_RENDIMIENTOS=:v_rend,
//                      APA_NUMERO_UNIDADES=:v_unid,
//                      APA_CUENTA_CONTINGENTE=:v_conting
//                  WHERE APA_APO_CON_PRO_CODIGO=:cod_producto
//                    AND APA_APO_CON_NUMERO=:num_contrato
//                    AND APA_APO_CONSECUTIVO=:consecApo
//                    AND APA_AJU_CONSECUTIVO=:consecAjus
//                    AND APA_AJU_LINEA=:linea };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "UPDATE TBAPORTES_AJUSTES\n                SET APA_CAPITAL= :1  ,\n                    APA_RENDIMIENTOS= :2  ,\n                    APA_NUMERO_UNIDADES= :3  ,\n                    APA_CUENTA_CONTINGENTE= :4  \n                WHERE APA_APO_CON_PRO_CODIGO= :5  \n                  AND APA_APO_CON_NUMERO= :6  \n                  AND APA_APO_CONSECUTIVO= :7  \n                  AND APA_AJU_CONSECUTIVO= :8  \n                  AND APA_AJU_LINEA= :9 ";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"12TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setDouble(1,v_cap);
   __sJT_st.setDouble(2,v_rend);
   __sJT_st.setDouble(3,v_unid);
   __sJT_st.setDouble(4,v_conting);
   __sJT_st.setString(5,cod_producto);
   __sJT_st.setString(6,num_contrato);
   __sJT_st.setInt(7,consecApo);
   __sJT_st.setInt(8,consecAjus);
   __sJT_st.setInt(9,linea);
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:774^43*/
                  
      }else{//si no existe inserta
        /*@lineinfo:generated-code*//*@lineinfo:777^9*/

//  ************************************************************
//  #sql { INSERT INTO TBAPORTES_AJUSTES
//                        (APA_APO_CON_PRO_CODIGO,        APA_APO_CON_NUMERO,       APA_APO_CONSECUTIVO,
//                         APA_AJU_CONSECUTIVO,           APA_AJU_LINEA,            APA_CAPITAL,
//                         APA_RENDIMIENTOS,              APA_NUMERO_UNIDADES,      APA_CUENTA_CONTINGENTE)
//                   VALUES
//                        (:cod_producto,                 :num_contrato,            :consecApo,
//                         :consecAjus,                   :linea,                   :capital,
//                         :rendimientos,                 :nUnid,                   :conting) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "INSERT INTO TBAPORTES_AJUSTES\n                      (APA_APO_CON_PRO_CODIGO,        APA_APO_CON_NUMERO,       APA_APO_CONSECUTIVO,\n                       APA_AJU_CONSECUTIVO,           APA_AJU_LINEA,            APA_CAPITAL,\n                       APA_RENDIMIENTOS,              APA_NUMERO_UNIDADES,      APA_CUENTA_CONTINGENTE)\n                 VALUES\n                      ( :1  ,                  :2  ,             :3  ,\n                        :4  ,                    :5  ,                    :6  ,\n                        :7  ,                  :8  ,                    :9  )";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"13TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecApo);
   __sJT_st.setInt(4,consecAjus);
   __sJT_st.setInt(5,linea);
   __sJT_st.setDouble(6,capital);
   __sJT_st.setDouble(7,rendimientos);
   __sJT_st.setDouble(8,nUnid);
   __sJT_st.setDouble(9,conting);
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:784^90*/
      }
     }catch(Exception ex)
     {
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
      return "Exception en TBPBD_AportesAjustes por "+v_menex;
    }
    return "YES";
  }

//////////////////////////Actualizar saldos en  aportes//////////////////////////////////
  public static String TBPBD_SaldosApor(String cod_producto,
                                        String num_contrato,
                                        int consecApo,
                                        double capital,
                                        double nUnid,
                                        double conting){
    try{
      double sal_cap = 0.0, sal_conting = 0.0, sal_nUnid = 0.0;
      ITAPORTE aporte;
      /*@lineinfo:generated-code*//*@lineinfo:829^7*/

//  ************************************************************
//  #sql aporte = { SELECT APO_SALDO_CAPITAL
//                        , APO_SALDO_CUENTA_CONTINGENTE
//                        , APO_SALDO_NUMERO_UNIDADES
//                      FROM TBAPORTES
//                     WHERE APO_CON_PRO_CODIGO=:cod_producto
//                       AND APO_CON_NUMERO=:num_contrato
//                       AND APO_CONSECUTIVO=:consecApo };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT APO_SALDO_CAPITAL\n                      , APO_SALDO_CUENTA_CONTINGENTE\n                      , APO_SALDO_NUMERO_UNIDADES\n                    FROM TBAPORTES\n                   WHERE APO_CON_PRO_CODIGO= :1  \n                     AND APO_CON_NUMERO= :2  \n                     AND APO_CONSECUTIVO= :3 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"14TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecApo);
   // execute query
   aporte = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.ITAPORTE(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"14TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:835^52*/
       //sacar saldos anteriores para sumar con los valores que venga
       if(aporte.next()){
        sal_cap=aporte.APO_SALDO_CAPITAL()+capital;
        sal_conting=aporte.APO_SALDO_CUENTA_CONTINGENTE()+conting;
        sal_nUnid=aporte.APO_SALDO_NUMERO_UNIDADES()+nUnid;
       }else{
          return "NO";
       }
       //actualizar saldos
       aporte.close();
       if (sal_conting < 0) sal_conting = 0;
       /*@lineinfo:generated-code*//*@lineinfo:847^8*/

//  ************************************************************
//  #sql { UPDATE TBAPORTES
//                  SET APO_SALDO_CAPITAL=:sal_cap,
//                      APO_SALDO_CUENTA_CONTINGENTE=:sal_conting,
//                      APO_SALDO_NUMERO_UNIDADES=:sal_nUnid
//               WHERE APO_CON_NUMERO=:num_contrato
//                 AND APO_CON_PRO_CODIGO=:cod_producto
//                 AND APO_CONSECUTIVO=:consecApo };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "UPDATE TBAPORTES\n                SET APO_SALDO_CAPITAL= :1  ,\n                    APO_SALDO_CUENTA_CONTINGENTE= :2  ,\n                    APO_SALDO_NUMERO_UNIDADES= :3  \n             WHERE APO_CON_NUMERO= :4  \n               AND APO_CON_PRO_CODIGO= :5  \n               AND APO_CONSECUTIVO= :6 ";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"15TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setDouble(1,sal_cap);
   __sJT_st.setDouble(2,sal_conting);
   __sJT_st.setDouble(3,sal_nUnid);
   __sJT_st.setString(4,num_contrato);
   __sJT_st.setString(5,cod_producto);
   __sJT_st.setInt(6,consecApo);
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:853^46*/
    }catch(Exception ex)
    {
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
      return "Exception en TBPBD_SaldosApor por "+v_menex ;
    }
    return "YES";
  }
//////////Actualiza la acción y fecha de la acción del ajuste según decisión del cliente////
  public static String TBPBD_ActAccionAjustes(String cod_producto,
                                              String num_contrato,
                                              int consecAjus,
                                              int linea,
                                              String accion){
    String strUpdate = "NO";
    try{
      ITVALORDB ajuste;
      I_INTERFACERET v_interface;
      boolean        v_paso    = false;
      String         v_paso_52  = "";
      java.sql.Date  v_fecha   = new  java.sql.Date(4);
      //consultar fecha de proceso

      /*@lineinfo:generated-code*//*@lineinfo:901^7*/

//  ************************************************************
//  #sql v_interface = { select  INL_PASO
//                                  from tbinterface_logs
//                                 where trunc(INL_FECHA) = trunc(sysdate)
//                                   and INL_PASO like '52%'
//                                 };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "select  INL_PASO\n                                from tbinterface_logs\n                               where trunc(INL_FECHA) = trunc(sysdate)\n                                 and INL_PASO like '52%'";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"16TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // execute query
   v_interface = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.I_INTERFACERET(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"16TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:905^31*/

      if(v_interface.next())
      {
        v_paso_52 = v_interface.INL_PASO();
        v_paso    = true;
      }

      if(v_paso)
      {
        /*@lineinfo:generated-code*//*@lineinfo:915^9*/

//  ************************************************************
//  #sql v_fecha = { values(TB_FFECHA_SIGUIENTE(1)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FFECHA_SIGUIENTE(1) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"17TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.DATE);
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_fecha = (java.sql.Date)__sJT_st.getDate(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:915^55*/
      }
      else
      {
        /*@lineinfo:generated-code*//*@lineinfo:919^9*/

//  ************************************************************
//  #sql { SELECT  TRUNC(SYSDATE)   FROM DUAL  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT  TRUNC(SYSDATE)    FROM DUAL";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"18TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   v_fecha = (java.sql.Date)__sJT_rs.getDate(1);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:919^64*/
      }

       double val_ajuste = 0;
       if(accion.equals("SAC002"))
       {
          /*@lineinfo:generated-code*//*@lineinfo:925^11*/

//  ************************************************************
//  #sql ajuste = { SELECT NVL(SUM(CAA_VALOR),0) VALORDB
//                             FROM TBCARGOS_AJUSTES
//                            WHERE CAA_AJU_CON_PRO_CODIGO = :cod_producto
//                               AND CAA_AJU_CON_NUMERO = :num_contrato
//                               AND CAA_AJU_CONSECUTIVO = :consecAjus
//                               AND CAA_AJU_LINEA = :linea };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT NVL(SUM(CAA_VALOR),0) VALORDB\n                           FROM TBCARGOS_AJUSTES\n                          WHERE CAA_AJU_CON_PRO_CODIGO =  :1  \n                             AND CAA_AJU_CON_NUMERO =  :2  \n                             AND CAA_AJU_CONSECUTIVO =  :3  \n                             AND CAA_AJU_LINEA =  :4 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"19TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecAjus);
   __sJT_st.setInt(4,linea);
   // execute query
   ajuste = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.ITVALORDB(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"19TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:930^56*/
          if(ajuste.next())
            val_ajuste = ajuste.VALORDB();

         /*@lineinfo:generated-code*//*@lineinfo:934^10*/

//  ************************************************************
//  #sql { UPDATE
//               TBAJUSTES
//              SET AJU_FECHA_ACCION  = trunc(Sysdate)
//                , AJU_REF_ACCION    = :accion
//                , AJU_VALOR         = :val_ajuste
//                , AJU_FECHA_PROCESO = :v_fecha
//              WHERE
//                AJU_CON_NUMERO         = :num_contrato
//                AND AJU_CON_PRO_CODIGO = :cod_producto
//                AND AJU_CONSECUTIVO    = :consecAjus
//                AND AJU_LINEA          = :linea };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "UPDATE\n             TBAJUSTES\n            SET AJU_FECHA_ACCION  = trunc(Sysdate)\n              , AJU_REF_ACCION    =  :1  \n              , AJU_VALOR         =  :2  \n              , AJU_FECHA_PROCESO =  :3  \n            WHERE\n              AJU_CON_NUMERO         =  :4  \n              AND AJU_CON_PRO_CODIGO =  :5  \n              AND AJU_CONSECUTIVO    =  :6  \n              AND AJU_LINEA          =  :7 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"20TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,accion);
   __sJT_st.setDouble(2,val_ajuste);
   __sJT_st.setDate(3,v_fecha);
   __sJT_st.setString(4,num_contrato);
   __sJT_st.setString(5,cod_producto);
   __sJT_st.setInt(6,consecAjus);
   __sJT_st.setInt(7,linea);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:944^46*/

       }
       else
       {
         /*@lineinfo:generated-code*//*@lineinfo:949^10*/

//  ************************************************************
//  #sql { UPDATE
//               TBAJUSTES
//              SET AJU_FECHA_ACCION  = trunc(sysdate)
//                , AJU_REF_ACCION    = :accion
//                , AJU_FECHA_PROCESO = :v_fecha
//              WHERE
//                AJU_CON_NUMERO         = :num_contrato
//                AND AJU_CON_PRO_CODIGO = :cod_producto
//                AND AJU_CONSECUTIVO    = :consecAjus
//                AND AJU_LINEA          = :linea };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "UPDATE\n             TBAJUSTES\n            SET AJU_FECHA_ACCION  = trunc(sysdate)\n              , AJU_REF_ACCION    =  :1  \n              , AJU_FECHA_PROCESO =  :2  \n            WHERE\n              AJU_CON_NUMERO         =  :3  \n              AND AJU_CON_PRO_CODIGO =  :4  \n              AND AJU_CONSECUTIVO    =  :5  \n              AND AJU_LINEA          =  :6 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"21TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,accion);
   __sJT_st.setDate(2,v_fecha);
   __sJT_st.setString(3,num_contrato);
   __sJT_st.setString(4,cod_producto);
   __sJT_st.setInt(5,consecAjus);
   __sJT_st.setInt(6,linea);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:958^46*/

       }
      strUpdate="YES";
    }catch(Exception ex)
    {
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
      return "Exception en TBPBD_ActAccionAjustes por "+v_menex;
    }
    return strUpdate;
  }

/////////////////////////Traer nombres y apellidos del contrato-producto//////////////////
  public static String[] TBPBD_ContratoNomApel(String cod_producto,
                                             String num_contrato){
    String strNombApel=new String("NO EXISTE ESTA PERSONA");
    String fechaCancel=new String("");
    String cadena[]=new String[2];
    try{
      CONTRATONOMBAPEL nombApel;
      /*@lineinfo:generated-code*//*@lineinfo:1003^7*/

//  ************************************************************
//  #sql nombApel = { SELECT CON_NOMBRES,    CON_APELLIDOS,CON_FECHA_CANCELACION
//                          FROM TBCONTRATOS
//                       WHERE CON_PRO_CODIGO=:cod_producto
//                         AND CON_NUMERO=:num_contrato };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT CON_NOMBRES,    CON_APELLIDOS,CON_FECHA_CANCELACION\n                        FROM TBCONTRATOS\n                     WHERE CON_PRO_CODIGO= :1  \n                       AND CON_NUMERO= :2 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"22TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   // execute query
   nombApel = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.CONTRATONOMBAPEL(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"22TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1006^52*/
      if(nombApel.next()){
         strNombApel=nombApel.CON_NOMBRES()+" "+nombApel.CON_APELLIDOS();
         if(nombApel.CON_FECHA_CANCELACION()==null)
          fechaCancel="NO";
         else
          fechaCancel="SI";
      }
      nombApel.close();
    }catch(Exception ex)
    {
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



      cadena[0]="Excepcion en TBPBD_ContratoNomApel "+v_menex;
      return cadena;
    }
    cadena[0]=strNombApel;
    cadena[1]=fechaCancel;
    return cadena;
  }
  
////////////////////selecciona todos los ajustes sin acción///////////////////////////////
  public static String[] TBPBD_SelAllRetDec(String num_contrato,
                                            String cod_producto,
                                            String consecAjusOnly1){
    String v_retNDec=new String("");
    String v_consec=new String("");
    String v_NDec[]=new String[2];
    try{
      int ii=1;
      String v_cons=new String("");
      String v_cons1=new String("-1");
      String v_lin=new String("");
      double val_TC=0;
      CONAJUSTES ajusNoDec;
      if(!consecAjusOnly1.equals("")){
      int consecAjusOnly=Integer.parseInt(consecAjusOnly1);
         /*@lineinfo:generated-code*//*@lineinfo:1070^10*/

//  ************************************************************
//  #sql ajusNoDec = { SELECT AJU_CONSECUTIVO,AJU_LINEA,AJU_FECHA_PROCESO,AJU_VALOR,AJU_RAZON_AJUSTE,AJU_USUARIO,AJU_RETIRO_ORIGINAL, AJU_RETIRO_ACTUAL,AJU_VALOR_UNIDAD FROM TBAJUSTES WHERE AJU_CON_PRO_CODIGO=:cod_producto AND AJU_CON_NUMERO=:num_contrato AND AJU_CONSECUTIVO=:consecAjusOnly AND AJU_REF_ACCION IS NULL AND AJU_FECHA_ACCION IS NULL };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT AJU_CONSECUTIVO,AJU_LINEA,AJU_FECHA_PROCESO,AJU_VALOR,AJU_RAZON_AJUSTE,AJU_USUARIO,AJU_RETIRO_ORIGINAL, AJU_RETIRO_ACTUAL,AJU_VALOR_UNIDAD FROM TBAJUSTES WHERE AJU_CON_PRO_CODIGO= :1   AND AJU_CON_NUMERO= :2   AND AJU_CONSECUTIVO= :3   AND AJU_REF_ACCION IS NULL AND AJU_FECHA_ACCION IS NULL";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"23TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecAjusOnly);
   // execute query
   ajusNoDec = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.CONAJUSTES(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"23TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1070^350*/
        while(ajusNoDec.next()){
          v_cons=Integer.toString(ajusNoDec.AJU_CONSECUTIVO());
          v_lin=Integer.toString(ajusNoDec.AJU_LINEA());
          v_retNDec+="val"+v_cons+v_lin+"='"+ajusNoDec.AJU_VALOR()+"' "+
                    "orig"+v_cons+v_lin+"='"+ajusNoDec.AJU_RETIRO_ORIGINAL()+"' "+
                   "act"+v_cons+v_lin+"='"+ajusNoDec.AJU_RETIRO_ACTUAL()+"' "+
                   "und"+v_cons+v_lin+"='"+ajusNoDec.AJU_VALOR_UNIDAD()+"' ";
          if(!v_cons.equals(v_cons1)){//cuando es primer vez el consecutivo primer linea
            val_TC=0;
            v_retNDec+="raz"+v_cons+"='"+ajusNoDec.AJU_RAZON_AJUSTE()+"' user"+v_cons+"='"+ajusNoDec.AJU_USUARIO()+"' "+
                       "fec"+v_cons+"='"+ajusNoDec.AJU_FECHA_PROCESO()+"' ";
            v_consec+="cons"+Integer.toString(ii++)+"='"+v_cons+"' ";
            val_TC=ajusNoDec.AJU_VALOR();
            v_retNDec+=" vtot"+v_cons+"='"+val_TC+"' max"+v_cons+"='"+v_lin+"' ";
          }else{//cuando hay dos ó más líneas por consecutivo
            val_TC+=ajusNoDec.AJU_VALOR();
            if(v_retNDec.indexOf("vtot"+v_cons)!=-1){//valor total del consecutivo
              int vt=v_retNDec.indexOf("vtot"+v_cons);int vt1=v_retNDec.indexOf(" ",vt);
              v_retNDec=v_retNDec.substring(0,vt-1)+v_retNDec.substring(vt1)+" vtot"+v_cons+"='"+val_TC+"' ";
            }
            if(v_retNDec.indexOf("max"+v_cons)!=-1){//guardar el max de linea del consecutivo
              int vt=v_retNDec.indexOf("max"+v_cons);int vt1=v_retNDec.indexOf(" ",vt);
              v_retNDec=v_retNDec.substring(0,vt-1)+v_retNDec.substring(vt1)+" max"+v_cons+"='"+v_lin+"' ";
            }
          }
          v_cons1=v_cons;
        }//while
        ajusNoDec.close();
      }else{
        /*@lineinfo:generated-code*//*@lineinfo:1100^9*/

//  ************************************************************
//  #sql ajusNoDec = { SELECT AJU_CONSECUTIVO,     AJU_LINEA,        AJU_FECHA_PROCESO,
//                               AJU_VALOR,           AJU_RAZON_AJUSTE, AJU_USUARIO,
//                               AJU_RETIRO_ORIGINAL, AJU_RETIRO_ACTUAL,AJU_VALOR_UNIDAD
//                           FROM TBAJUSTES
//                         WHERE AJU_CON_PRO_CODIGO=:cod_producto
//                           AND AJU_CON_NUMERO=:num_contrato
//                           AND AJU_REF_ACCION IS NULL
//                           AND AJU_FECHA_ACCION IS NULL
//                         ORDER BY AJU_CONSECUTIVO };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT AJU_CONSECUTIVO,     AJU_LINEA,        AJU_FECHA_PROCESO,\n                             AJU_VALOR,           AJU_RAZON_AJUSTE, AJU_USUARIO,\n                             AJU_RETIRO_ORIGINAL, AJU_RETIRO_ACTUAL,AJU_VALOR_UNIDAD\n                         FROM TBAJUSTES\n                       WHERE AJU_CON_PRO_CODIGO= :1  \n                         AND AJU_CON_NUMERO= :2  \n                         AND AJU_REF_ACCION IS NULL\n                         AND AJU_FECHA_ACCION IS NULL\n                       ORDER BY AJU_CONSECUTIVO";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"24TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   // execute query
   ajusNoDec = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.CONAJUSTES(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"24TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1108^48*/
        while(ajusNoDec.next()){
          v_cons=Integer.toString(ajusNoDec.AJU_CONSECUTIVO());
          v_lin=Integer.toString(ajusNoDec.AJU_LINEA());
          v_retNDec+="val"+v_cons+v_lin+"='"+ajusNoDec.AJU_VALOR()+"' "+
                    "orig"+v_cons+v_lin+"='"+ajusNoDec.AJU_RETIRO_ORIGINAL()+"' "+
                   "act"+v_cons+v_lin+"='"+ajusNoDec.AJU_RETIRO_ACTUAL()+"' "+
                   "und"+v_cons+v_lin+"='"+ajusNoDec.AJU_VALOR_UNIDAD()+"' ";
          if(!v_cons.equals(v_cons1)){//cuando es primer vez el consecutivo primer linea
            val_TC=0;
            v_retNDec+="raz"+v_cons+"='"+ajusNoDec.AJU_RAZON_AJUSTE()+"' user"+v_cons+"='"+ajusNoDec.AJU_USUARIO()+"' "+
                       "fec"+v_cons+"='"+ajusNoDec.AJU_FECHA_PROCESO()+"' ";
            v_consec+="cons"+Integer.toString(ii++)+"='"+v_cons+"' ";
            val_TC=ajusNoDec.AJU_VALOR();
            v_retNDec+=" vtot"+v_cons+"='"+val_TC+"' max"+v_cons+"='"+v_lin+"' ";
          }else{//cuando hay dos ó más líneas por consecutivo
            val_TC+=ajusNoDec.AJU_VALOR();
            if(v_retNDec.indexOf("vtot"+v_cons)!=-1){//valor total del consecutivo
              int vt=v_retNDec.indexOf("vtot"+v_cons);int vt1=v_retNDec.indexOf(" ",vt);
              v_retNDec=v_retNDec.substring(0,vt-1)+v_retNDec.substring(vt1)+" vtot"+v_cons+"='"+val_TC+"' ";
            }
            if(v_retNDec.indexOf("max"+v_cons)!=-1){//guardar el max de linea del consecutivo
              int vt=v_retNDec.indexOf("max"+v_cons);int vt1=v_retNDec.indexOf(" ",vt);
              v_retNDec=v_retNDec.substring(0,vt-1)+v_retNDec.substring(vt1)+" max"+v_cons+"='"+v_lin+"' ";
            }
          }
          v_cons1=v_cons;
        }//while
        ajusNoDec.close();
      }
    }catch(Exception ex){
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
      v_NDec[0]="Exception en SellAllRetDec por "+v_menex;
      return v_NDec;
    }
    v_NDec[0]="<"+v_retNDec+">";v_NDec[1]="<"+v_consec+">";
    return v_NDec;
  }

  //////////devuelve saldo capital y saldo rendimientos de un aporte//////////
  public static double[] TBPBD_CalSalAporte(String num_contrato,
                                           String cod_producto,
                                           int consecApo,
                                           double valUnidad){
    double salApor[] = new double[2];
    double cap,rend;
    String msgErr = "";
    int codErr=0;
    try{
        /*@lineinfo:generated-code*//*@lineinfo:1182^9*/

//  ************************************************************
//  #sql { call TBPBD_SaldoBrutoAporte(:cod_producto,:num_contrato,:consecApo,:valUnidad, 'S',:cap,:rend,:codErr,:msgErr) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_SaldoBrutoAporte( :1  , :2  , :3  , :4  , 'S', :5  , :6  , :7  , :8  )\n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"25TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecApo);
   __sJT_st.setDouble(4,valUnidad);
   __sJT_st.setInt(7,codErr);
   __sJT_st.setString(8,msgErr);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   cap = __sJT_st.getDouble(5); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   rend = __sJT_st.getDouble(6); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   codErr = __sJT_st.getInt(7); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   msgErr = (String)__sJT_st.getString(8);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1182^145*/
        salApor[0] = cap;
        salApor[1] = rend;
      }catch(Exception e){
        salApor[0] = 0;
        salApor[1] = 0;
      }
    return salApor;
  }
///////////////////////////////////////////
  //////////devuelve saldo capital y saldo rendimientos de un aporte//////////
  public static boolean TBPL_Verdecision
  (String cod_producto
   ,String num_contrato
   ,int consecAjus
   ,int linea)
   {
    I_DECISIONAJUSTE  DECISIONAJUSTE;
    try{
        /*@lineinfo:generated-code*//*@lineinfo:1201^9*/

//  ************************************************************
//  #sql DECISIONAJUSTE = { SELECT DECODE(AJU_REF_ACCION,NULL,'1','0') DECISION
//                                 FROM   TBAJUSTES
//                                WHERE    AJU_CON_PRO_CODIGO  = :cod_producto
//                                  AND    AJU_CON_NUMERO      = :num_contrato
//                                  AND    AJU_CONSECUTIVO     = :consecAjus
//                                  AND    AJU_LINEA           = :linea  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT DECODE(AJU_REF_ACCION,NULL,'1','0') DECISION\n                               FROM   TBAJUSTES\n                              WHERE    AJU_CON_PRO_CODIGO  =  :1  \n                                AND    AJU_CON_NUMERO      =  :2  \n                                AND    AJU_CONSECUTIVO     =  :3  \n                                AND    AJU_LINEA           =  :4 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"26TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecAjus);
   __sJT_st.setInt(4,linea);
   // execute query
   DECISIONAJUSTE = new TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE.I_DECISIONAJUSTE(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"26TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.SQL_DAJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1206^69*/
        if(DECISIONAJUSTE.next())
        {
          if(DECISIONAJUSTE.DECISION().equals("1"))
             return false;
           else
             return true;
       }
       else
       {
         return false;
       }


      }catch(Exception e){

         return false;
      }

  }

  /////////////////////Realizar todos los cambios con la Base de Datos////////////////////////
  public static boolean TBPBD_Commit(){
    try{
      /*@lineinfo:generated-code*//*@lineinfo:1230^7*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1230^19*/
      return true;
    }catch(Exception e){
      return false;
    }
  }
/////////////////////No Realizar cambios con la Base de Datos////////////////////////~
  public static boolean TBPBD_RollBack(){
    try{
      /*@lineinfo:generated-code*//*@lineinfo:1239^7*/

//  ************************************************************
//  #sql { ROLLBACK };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1239^21*/
      return true;
    }catch(Exception e){
      return false;
    }
  }
  
  public static boolean TBPBD_CerrarConexionBD(){
    try{
       DefaultContext.getDefaultContext().close();
       return true;
     }
    catch(Exception e){
      return false;
    }
  }

}/*@lineinfo:generated-code*/