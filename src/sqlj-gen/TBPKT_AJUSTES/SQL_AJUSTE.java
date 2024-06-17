/*@lineinfo:filename=SQL_AJUSTE*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_AJUSTES;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import sqlj.runtime.ref.DefaultContext;
import oracle.jdbc.driver.*;
import sqlj.runtime.ref.*;
import java.lang.*;
import sqlj.runtime.*;
import javax.servlet.http.*;
import java.sql.Date;
import oracle.sql.*;
import java.util.*;
import java.sql.*;
import oracle.jdbc.*;
import java.io.*;

/**
*El objetivo de esta clase es manejar todo lo que tiene que ver con la Base de
*Datos TaxBenefit, según lo que se desee realizar, como consultas, modificaciones
*o insertar información.
*/



public class SQL_AJUSTE extends HttpServlet{


/*@lineinfo:generated-code*//*@lineinfo:27^1*/

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

/*@lineinfo:user-code*//*@lineinfo:27^112*/
/*@lineinfo:generated-code*//*@lineinfo:28^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class TBREFERENCIAS
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public TBREFERENCIAS(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    REF_DESCRIPCIONNdx = findColumn("REF_DESCRIPCION");
    REF_VALORNdx = findColumn("REF_VALOR");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String REF_DESCRIPCION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(REF_DESCRIPCIONNdx);
  }
  private int REF_DESCRIPCIONNdx;
  public String REF_VALOR()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(REF_VALORNdx);
  }
  private int REF_VALORNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:28^82*/
/*@lineinfo:generated-code*//*@lineinfo:29^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class TBFINDRETANU
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public TBFINDRETANU(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    RET_CONSECUTIVONdx = findColumn("RET_CONSECUTIVO");
    RET_VALOR_UNIDADNdx = findColumn("RET_VALOR_UNIDAD");
    RET_FECHA_EFECTIVANdx = findColumn("RET_FECHA_EFECTIVA");
    RET_FECHA_PROCESONdx = findColumn("RET_FECHA_PROCESO");
    RET_REF_TIPO_VALORNdx = findColumn("RET_REF_TIPO_VALOR");
    RET_VALOR_BRUTONdx = findColumn("RET_VALOR_BRUTO");
    RET_VALOR_NETONdx = findColumn("RET_VALOR_NETO");
    RET_REF_METODO_ORDENNdx = findColumn("RET_REF_METODO_ORDEN");
    RET_REF_METODO_BENEFICIONdx = findColumn("RET_REF_METODO_BENEFICIO");
    RET_REF_METODO_PENALIZACIONNdx = findColumn("RET_REF_METODO_PENALIZACION");
    RET_REF_METODO_CUENTANdx = findColumn("RET_REF_METODO_CUENTA");
    RET_REF_NATURALEZANdx = findColumn("RET_REF_NATURALEZA");
    RET_RESPETAR_NATURALEZANdx = findColumn("RET_RESPETAR_NATURALEZA");
    RET_REF_UNIDAD_PROCESONdx = findColumn("RET_REF_UNIDAD_PROCESO");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public int RET_CONSECUTIVO()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(RET_CONSECUTIVONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int RET_CONSECUTIVONdx;
  public double RET_VALOR_UNIDAD()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(RET_VALOR_UNIDADNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int RET_VALOR_UNIDADNdx;
  public java.sql.Date RET_FECHA_EFECTIVA()
    throws java.sql.SQLException
  {
    return (java.sql.Date)m_rs.getDate(RET_FECHA_EFECTIVANdx);
  }
  private int RET_FECHA_EFECTIVANdx;
  public java.sql.Date RET_FECHA_PROCESO()
    throws java.sql.SQLException
  {
    return (java.sql.Date)m_rs.getDate(RET_FECHA_PROCESONdx);
  }
  private int RET_FECHA_PROCESONdx;
  public String RET_REF_TIPO_VALOR()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(RET_REF_TIPO_VALORNdx);
  }
  private int RET_REF_TIPO_VALORNdx;
  public double RET_VALOR_BRUTO()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(RET_VALOR_BRUTONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int RET_VALOR_BRUTONdx;
  public double RET_VALOR_NETO()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(RET_VALOR_NETONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int RET_VALOR_NETONdx;
  public String RET_REF_METODO_ORDEN()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(RET_REF_METODO_ORDENNdx);
  }
  private int RET_REF_METODO_ORDENNdx;
  public String RET_REF_METODO_BENEFICIO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(RET_REF_METODO_BENEFICIONdx);
  }
  private int RET_REF_METODO_BENEFICIONdx;
  public String RET_REF_METODO_PENALIZACION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(RET_REF_METODO_PENALIZACIONNdx);
  }
  private int RET_REF_METODO_PENALIZACIONNdx;
  public String RET_REF_METODO_CUENTA()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(RET_REF_METODO_CUENTANdx);
  }
  private int RET_REF_METODO_CUENTANdx;
  public String RET_REF_NATURALEZA()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(RET_REF_NATURALEZANdx);
  }
  private int RET_REF_NATURALEZANdx;
  public String RET_RESPETAR_NATURALEZA()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(RET_RESPETAR_NATURALEZANdx);
  }
  private int RET_RESPETAR_NATURALEZANdx;
  public String RET_REF_UNIDAD_PROCESO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(RET_REF_UNIDAD_PROCESONdx);
  }
  private int RET_REF_UNIDAD_PROCESONdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:29^414*/
/*@lineinfo:generated-code*//*@lineinfo:30^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class TBFINDRETREV
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public TBFINDRETREV(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    RET_CONSECUTIVONdx = findColumn("RET_CONSECUTIVO");
    RET_TRANSACCIONNdx = findColumn("RET_TRANSACCION");
    RET_FECHA_EFECTIVANdx = findColumn("RET_FECHA_EFECTIVA");
    RET_FECHA_PROCESONdx = findColumn("RET_FECHA_PROCESO");
    RET_REF_TIPO_VALORNdx = findColumn("RET_REF_TIPO_VALOR");
    RET_VALOR_BRUTONdx = findColumn("RET_VALOR_BRUTO");
    RET_VALOR_NETONdx = findColumn("RET_VALOR_NETO");
    RET_REF_UNIDAD_PROCESONdx = findColumn("RET_REF_UNIDAD_PROCESO");
    RET_VALOR_UNIDADNdx = findColumn("RET_VALOR_UNIDAD");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public int RET_CONSECUTIVO()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(RET_CONSECUTIVONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int RET_CONSECUTIVONdx;
  public String RET_TRANSACCION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(RET_TRANSACCIONNdx);
  }
  private int RET_TRANSACCIONNdx;
  public java.sql.Date RET_FECHA_EFECTIVA()
    throws java.sql.SQLException
  {
    return (java.sql.Date)m_rs.getDate(RET_FECHA_EFECTIVANdx);
  }
  private int RET_FECHA_EFECTIVANdx;
  public java.sql.Date RET_FECHA_PROCESO()
    throws java.sql.SQLException
  {
    return (java.sql.Date)m_rs.getDate(RET_FECHA_PROCESONdx);
  }
  private int RET_FECHA_PROCESONdx;
  public String RET_REF_TIPO_VALOR()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(RET_REF_TIPO_VALORNdx);
  }
  private int RET_REF_TIPO_VALORNdx;
  public double RET_VALOR_BRUTO()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(RET_VALOR_BRUTONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int RET_VALOR_BRUTONdx;
  public double RET_VALOR_NETO()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(RET_VALOR_NETONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int RET_VALOR_NETONdx;
  public String RET_REF_UNIDAD_PROCESO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(RET_REF_UNIDAD_PROCESONdx);
  }
  private int RET_REF_UNIDAD_PROCESONdx;
  public double RET_VALOR_UNIDAD()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(RET_VALOR_UNIDADNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int RET_VALOR_UNIDADNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:30^256*/
/*@lineinfo:generated-code*//*@lineinfo:31^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONAPORET
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONAPORET(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    APR_APO_CONSECUTIVONdx = findColumn("APR_APO_CONSECUTIVO");
    APR_CAPITALNdx = findColumn("APR_CAPITAL");
    APR_RENDIMIENTOS_HNdx = findColumn("APR_RENDIMIENTOS_H");
    APR_NUMERO_UNIDADESNdx = findColumn("APR_NUMERO_UNIDADES");
    APR_RENDIMIENTOS_PNdx = findColumn("APR_RENDIMIENTOS_P");
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
  public double APR_RENDIMIENTOS_H()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APR_RENDIMIENTOS_HNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APR_RENDIMIENTOS_HNdx;
  public double APR_NUMERO_UNIDADES()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APR_NUMERO_UNIDADESNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APR_NUMERO_UNIDADESNdx;
  public double APR_RENDIMIENTOS_P()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APR_RENDIMIENTOS_PNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APR_RENDIMIENTOS_PNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:31^160*/
/*@lineinfo:generated-code*//*@lineinfo:32^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONAPORET1
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONAPORET1(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    APR_CAPITALNdx = findColumn("APR_CAPITAL");
    APR_RENDIMIENTOSNdx = findColumn("APR_RENDIMIENTOS");
    APR_RENDIMIENTOS_PNdx = findColumn("APR_RENDIMIENTOS_P");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
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
  public double APR_RENDIMIENTOS_P()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APR_RENDIMIENTOS_PNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APR_RENDIMIENTOS_PNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:32^108*/
/*@lineinfo:generated-code*//*@lineinfo:33^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class ACTAPORTE
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public ACTAPORTE(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    APO_SALDO_CAPITALNdx = findColumn("APO_SALDO_CAPITAL");
    APO_PORCENTAJE_RENDIMIENTOS_HNdx = findColumn("APO_PORCENTAJE_RENDIMIENTOS_H");
    APO_RENDIMIENTOS_PENALIZADOSNdx = findColumn("APO_RENDIMIENTOS_PENALIZADOS");
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
  public double APO_PORCENTAJE_RENDIMIENTOS_H()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_PORCENTAJE_RENDIMIENTOS_HNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_PORCENTAJE_RENDIMIENTOS_HNdx;
  public double APO_RENDIMIENTOS_PENALIZADOS()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(APO_RENDIMIENTOS_PENALIZADOSNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int APO_RENDIMIENTOS_PENALIZADOSNdx;
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

/*@lineinfo:user-code*//*@lineinfo:33^205*/
/*@lineinfo:generated-code*//*@lineinfo:34^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONAPORTE1
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONAPORTE1(sqlj.runtime.profile.RTResultSet resultSet)
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

/*@lineinfo:user-code*//*@lineinfo:34^59*/
/*@lineinfo:generated-code*//*@lineinfo:35^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONAPORTE
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONAPORTE(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    APO_APORTE_BENEFICIONdx = findColumn("APO_APORTE_BENEFICIO");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String APO_APORTE_BENEFICIO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(APO_APORTE_BENEFICIONdx);
  }
  private int APO_APORTE_BENEFICIONdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:35^66*/
/*@lineinfo:generated-code*//*@lineinfo:36^1*/

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

/*@lineinfo:user-code*//*@lineinfo:36^226*/
/*@lineinfo:generated-code*//*@lineinfo:37^1*/

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
    CAE_VALORNdx = findColumn("CAE_VALOR");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double CAE_VALOR()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(CAE_VALORNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int CAE_VALORNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:37^57*/
/*@lineinfo:generated-code*//*@lineinfo:38^1*/

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

/*@lineinfo:user-code*//*@lineinfo:38^57*/
/*@lineinfo:generated-code*//*@lineinfo:39^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONCARGORET1
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONCARGORET1(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    CAE_VALORNdx = findColumn("CAE_VALOR");
    CAE_APR_APO_CONSECUTIVONdx = findColumn("CAE_APR_APO_CONSECUTIVO");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public double CAE_VALOR()
    throws java.sql.SQLException
  {
    double __sJtmp = m_rs.getDouble(CAE_VALORNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int CAE_VALORNdx;
  public int CAE_APR_APO_CONSECUTIVO()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(CAE_APR_APO_CONSECUTIVONdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int CAE_APR_APO_CONSECUTIVONdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:39^86*/
/*@lineinfo:generated-code*//*@lineinfo:40^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONPRODUCTO
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONPRODUCTO(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    PRO_RETENCION_FUENTENdx = findColumn("PRO_RETENCION_FUENTE");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public int PRO_RETENCION_FUENTE()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(PRO_RETENCION_FUENTENdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int PRO_RETENCION_FUENTENdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:40^65*/
/*@lineinfo:generated-code*//*@lineinfo:41^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONPRODUCTO1
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONPRODUCTO1(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    PRO_METODO_ORDENNdx = findColumn("PRO_METODO_ORDEN");
    PRO_METODO_BENEFICIONdx = findColumn("PRO_METODO_BENEFICIO");
    PRO_METODO_PENALIZACIONNdx = findColumn("PRO_METODO_PENALIZACION");
    PRO_METODO_CUENTANdx = findColumn("PRO_METODO_CUENTA");
    PRO_NATURALEZA_RETIRONdx = findColumn("PRO_NATURALEZA_RETIRO");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String PRO_METODO_ORDEN()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRO_METODO_ORDENNdx);
  }
  private int PRO_METODO_ORDENNdx;
  public String PRO_METODO_BENEFICIO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRO_METODO_BENEFICIONdx);
  }
  private int PRO_METODO_BENEFICIONdx;
  public String PRO_METODO_PENALIZACION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRO_METODO_PENALIZACIONNdx);
  }
  private int PRO_METODO_PENALIZACIONNdx;
  public String PRO_METODO_CUENTA()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRO_METODO_CUENTANdx);
  }
  private int PRO_METODO_CUENTANdx;
  public String PRO_NATURALEZA_RETIRO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRO_NATURALEZA_RETIRONdx);
  }
  private int PRO_NATURALEZA_RETIRONdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:41^178*/
/*@lineinfo:generated-code*//*@lineinfo:42^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONRETIROS
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONRETIROS(sqlj.runtime.profile.RTResultSet resultSet)
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

/*@lineinfo:user-code*//*@lineinfo:42^74*/
/*@lineinfo:generated-code*//*@lineinfo:43^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class CONAJUSTES1
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public CONAJUSTES1(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    AJU_CONSECUTIVONdx = findColumn("AJU_CONSECUTIVO");
    AJU_CON_PRO_CODIGONdx = findColumn("AJU_CON_PRO_CODIGO");
    AJU_CON_NUMERONdx = findColumn("AJU_CON_NUMERO");
    AJU_RETIRO_ORIGINALNdx = findColumn("AJU_RETIRO_ORIGINAL");
    fecefeNdx = findColumn("fecefe");
    fecproNdx = findColumn("fecpro");
    AJU_VALORNdx = findColumn("AJU_VALOR");
    AJU_REF_ACCIONNdx = findColumn("AJU_REF_ACCION");
    fecajuNdx = findColumn("fecaju");
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
  public String AJU_CON_PRO_CODIGO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(AJU_CON_PRO_CODIGONdx);
  }
  private int AJU_CON_PRO_CODIGONdx;
  public String AJU_CON_NUMERO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(AJU_CON_NUMERONdx);
  }
  private int AJU_CON_NUMERONdx;
  public int AJU_RETIRO_ORIGINAL()
    throws java.sql.SQLException
  {
    int __sJtmp = m_rs.getInt(AJU_RETIRO_ORIGINALNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int AJU_RETIRO_ORIGINALNdx;
  public String fecefe()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(fecefeNdx);
  }
  private int fecefeNdx;
  public String fecpro()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(fecproNdx);
  }
  private int fecproNdx;
  public float AJU_VALOR()
    throws java.sql.SQLException
  {
    float __sJtmp = m_rs.getFloat(AJU_VALORNdx);
    if (m_rs.wasNull()) throw new sqlj.runtime.SQLNullException(); else return __sJtmp;
  }
  private int AJU_VALORNdx;
  public String AJU_REF_ACCION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(AJU_REF_ACCIONNdx);
  }
  private int AJU_REF_ACCIONNdx;
  public String fecaju()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(fecajuNdx);
  }
  private int fecajuNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:43^212*/
/*@lineinfo:generated-code*//*@lineinfo:44^1*/

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

/*@lineinfo:user-code*//*@lineinfo:44^140*/
/*@lineinfo:generated-code*//*@lineinfo:45^1*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

public static class FECHAACTUAL
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public FECHAACTUAL(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    FECACTUALNdx = findColumn("FECACTUAL");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public java.sql.Date FECACTUAL()
    throws java.sql.SQLException
  {
    return (java.sql.Date)m_rs.getDate(FECACTUALNdx);
  }
  private int FECACTUALNdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:45^64*/

public static String err=new String();
////////////constructor//////////////////
public SQL_AJUSTE()
{
}
//////////////////Abrir conexión con la Base de Datos//////////////////////
public static boolean TBPBD_ConexionBD()
{
try
  {
   /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_ValidarUsuario de la clase TBCL_Validacion, no es necesaria la instancia nueva*/ 
 //TBCL_Validacion i_valusu = new TBCL_Validacion(); 
 //TBCL_Validacion  i_valusu = new TBCL_Validacion()
   String[] v_valusu          = new String[3];
   v_valusu                   = TBCL_Validacion.TBFL_ValidarUsuario();
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   DefaultContext.setDefaultContext(new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false));
   return true;
  }
catch(Exception e)
 {
  return false;
 }
}
/////////////////////Devuelve la fecha de Hoy//////////////////////////////////
public static String TBPBD_FechaHoy()
{
 String v_fechaHoy=new String("");
 try
 {
  FECHAACTUAL fecAct1;
  /*@lineinfo:generated-code*//*@lineinfo:78^3*/

//  ************************************************************
//  #sql fecAct1 = { SELECT SYSDATE AS FECACTUAL FROM DUAL };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT SYSDATE AS FECACTUAL FROM DUAL";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // execute query
   fecAct1 = new TBPKT_AJUSTES.SQL_AJUSTE.FECHAACTUAL(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"0TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:78^54*/
  if(fecAct1.next())
  {
    v_fechaHoy=fecAct1.FECACTUAL().toString();
  }
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
  return "Exception en TBPBD_FechaHoy por "+v_menex;
 }
 return v_fechaHoy;
}

////////////////Llamar procedimiento en pl/sql que me genere el nuevo retiro/No se utiliza/////////////
 public static String[] TBPBD_GenerarRetiroPrima(String cod_producto,
                                                  String num_contrato,
                                                  java.sql.Date vfproc,
                                                  java.sql.Date vfefec,
                                                  String vtiptran,
                                                  String vclastran,
                                                  double vvalor,
                                                  String vtipval,
                                                  double vvalunid,
                                                  String vretpen,
                                                  String vretpro,
                                                  String vbanco,
                                                  String vcuenta,
                                                  String vtransaccion,
                                                  String vusuario,
                                                  String vunidproc,
                                                  String cambiarEstado,
                                                  String vcodigoafp,
                                                  String vcodti,
                                                  String vnumti,
                                                  String vmetorden,
                                                  String vmetbenef,
                                                  String vmetpenal,
                                                  String vmetcuenta,
                                                  String vmetnatural,
                                                  String vmetrespetarn,
                                                  String vindtrans)
{
    String valores[]     = new String[4];
    String msgErr        = new String("null");
    Integer codErr       = new Integer(0);
    String cod_producto1 = new String("");
    String num_contrato1 = new String("");
    Integer consecRet    = new Integer(0);
    Double valneto       = new Double("0");
    //declaro variables String() para mantener esquema
    String m_o = new String(" ");    String m_b = new String(" ");
    String m_p = new String(" ");    String m_c = new String(" ");
    String nat = new String(" ");    String r_n = new String(" ");
    //declaro variables String() para mantener esquema
    try
     {
      m_o = vmetorden;
      m_b = vmetbenef;
      m_p = vmetpenal;
      m_c = vmetcuenta;
      nat = vmetnatural;
      r_n = vmetrespetarn;
     }
    catch(Exception s){}
    try
    {
     if(m_o.equalsIgnoreCase("SMO000")||m_b.equalsIgnoreCase("SMB000")||m_p.equalsIgnoreCase("SMP000")||
        m_c.equalsIgnoreCase("SMC000")||nat.equalsIgnoreCase("SNR000")||r_n.equals(""))
        {
          m_o = null;
          m_b = null;
          m_p = null;
          m_c = null;
          nat = null;
          r_n = null;
        }
       //valido esuqema
      if(m_o.equals(null)||m_b.equals(null)||m_p.equals(null)||m_c.equals(null)||nat.equals(null)||r_n.equals(null))
        {
          m_o = null;
          m_b = null;
          m_p = null;
          m_c = null;
          m_o = null;
          nat = null;
        }
    }//try
   catch(Exception ex)
   {
       m_o = null;
       m_b = null;
       m_p = null;
       m_c = null;
       nat = null;
       r_n = null;
   }//catch
  try{
  
  
        /*Modificación hecha por APC para manejar el nuevo reglamento 2006-06-22*/          
        String v_libreria= new String();
        String v_sistema = new String();
        String v_usumfund = new String();
        String v_passmfund = new String();


        /*@lineinfo:generated-code*//*@lineinfo:210^9*/

//  ************************************************************
//  #sql { call TBPBD_Parametros_FuncionesAS(:v_libreria,
//                                                  :v_sistema,
//                                                  :v_usumfund,
//                                                  :v_passmfund)
//                                                   };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_Parametros_FuncionesAS( :1  ,\n                                                 :2  ,\n                                                 :3  ,\n                                                 :4  )\n                                                \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"1TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_libreria);
   __sJT_st.setString(2,v_sistema);
   __sJT_st.setString(3,v_usumfund);
   __sJT_st.setString(4,v_passmfund);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:214^49*/
        
        String v_retorno_programa = new String("");
        
        /*@lineinfo:generated-code*//*@lineinfo:218^9*/

//  ************************************************************
//  #sql v_retorno_programa = { values (TBCL_FuncionesAs400.TBPL_ProgramaContrato(:num_contrato,
//                                                                                      :v_sistema,
//                                                                                      :v_usumfund,
//                                                                                      :v_passmfund,
//                                                                                      :v_libreria))
//                                                                                       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBCL_FuncionesAs400.TBPL_ProgramaContrato( :2  ,\n                                                                                     :3  ,\n                                                                                     :4  ,\n                                                                                     :5  ,\n                                                                                     :6  ) \n                                                                                    \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setString(3,v_sistema);
   __sJT_st.setString(4,v_usumfund);
   __sJT_st.setString(5,v_passmfund);
   __sJT_st.setString(6,v_libreria);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_retorno_programa = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:223^85*/
        
        String v_programa = v_retorno_programa.substring(0,v_retorno_programa.indexOf(";",0));
        /*FIN Modificación hecha por APC para manejar el nuevo reglamento 2006-06-22*/          
  
  
  
     /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_ValidarUsuario de la clase TBCL_Validacion, no es necesaria la instancia nueva*/ 
 //TBCL_Validacion i_valusu = new TBCL_Validacion(); 
 //TBCL_Validacion  i_valusu = new TBCL_Validacion()
     String[] v_valusu          = new String[3];
     v_valusu                   = TBCL_Validacion.TBFL_ValidarUsuario();
     DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
     DefaultContext.setDefaultContext(new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false));
     String indicador_ng = new String("N");
      /*
       * MOS Se quita temporalmente :v_programa
       * Se vuelve a poner
       */
          /*@lineinfo:generated-code*//*@lineinfo:242^11*/

//  ************************************************************
//  #sql { call TBPBD_RETIRO(:cod_producto
//                               ,:num_contrato
//                               ,:v_programa
//                               ,NULL
//                               ,:vfproc
//                               ,:vfefec
//                               ,:vtiptran
//                               ,:vclastran
//                               ,:vvalor
//                               ,:vtipval
//                               ,:vvalunid
//                               ,:vretpen
//                               ,:vretpro
//                               ,:vbanco
//                               ,:vcuenta
//                               ,:vtransaccion
//                               ,:vusuario
//                               ,:vunidproc
//                               ,:cambiarEstado
//                               ,:vcodigoafp
//                               ,:vcodti
//                               ,:vnumti
//                               ,:m_o
//                               ,:m_b
//                               ,:m_p
//                               ,:m_c
//                               ,:nat
//                               ,:r_n
//                               ,:vindtrans
//                               ,:indicador_ng
//                               ,:cod_producto1
//                               ,:num_contrato1
//                               ,:consecRet
//                               ,:valneto
//                               ,:codErr
//                               ,:msgErr)
//                                };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN TBPBD_RETIRO( :1  \n                             , :2  \n                             , :3  \n                             ,NULL\n                             , :4  \n                             , :5  \n                             , :6  \n                             , :7  \n                             , :8  \n                             , :9  \n                             , :10  \n                             , :11  \n                             , :12  \n                             , :13  \n                             , :14  \n                             , :15  \n                             , :16  \n                             , :17  \n                             , :18  \n                             , :19  \n                             , :20  \n                             , :21  \n                             , :22  \n                             , :23  \n                             , :24  \n                             , :25  \n                             , :26  \n                             , :27  \n                             , :28  \n                             , :29  \n                             , :30  \n                             , :31  \n                             , :32  \n                             , :33  \n                             , :34  \n                             , :35  )\n                             \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(30,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(31,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(32,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(33,oracle.jdbc.OracleTypes.DOUBLE);
      __sJT_st.registerOutParameter(34,oracle.jdbc.OracleTypes.INTEGER);
      __sJT_st.registerOutParameter(35,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setString(3,v_programa);
   __sJT_st.setDate(4,vfproc);
   __sJT_st.setDate(5,vfefec);
   __sJT_st.setString(6,vtiptran);
   __sJT_st.setString(7,vclastran);
   __sJT_st.setDouble(8,vvalor);
   __sJT_st.setString(9,vtipval);
   __sJT_st.setDouble(10,vvalunid);
   __sJT_st.setString(11,vretpen);
   __sJT_st.setString(12,vretpro);
   __sJT_st.setString(13,vbanco);
   __sJT_st.setString(14,vcuenta);
   __sJT_st.setString(15,vtransaccion);
   __sJT_st.setString(16,vusuario);
   __sJT_st.setString(17,vunidproc);
   __sJT_st.setString(18,cambiarEstado);
   __sJT_st.setString(19,vcodigoafp);
   __sJT_st.setString(20,vcodti);
   __sJT_st.setString(21,vnumti);
   __sJT_st.setString(22,m_o);
   __sJT_st.setString(23,m_b);
   __sJT_st.setString(24,m_p);
   __sJT_st.setString(25,m_c);
   __sJT_st.setString(26,nat);
   __sJT_st.setString(27,r_n);
   __sJT_st.setString(28,vindtrans);
   __sJT_st.setString(29,indicador_ng);
   if (codErr == null) __sJT_st.setNull(34,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(34,codErr.intValue());
   __sJT_st.setString(35,msgErr);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   cod_producto1 = (String)__sJT_st.getString(30);
   num_contrato1 = (String)__sJT_st.getString(31);
   consecRet = new Integer(__sJT_st.getInt(32)); if (__sJT_st.wasNull()) consecRet = null;
   valneto = new Double(__sJT_st.getDouble(33)); if (__sJT_st.wasNull()) valneto = null;
   codErr = new Integer(__sJT_st.getInt(34)); if (__sJT_st.wasNull()) codErr = null;
   msgErr = (String)__sJT_st.getString(35);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:278^30*/

      try
      {if(valneto!=null)valores[0]=valneto.toString();}
      catch(Exception ex){}
      try
      {if(consecRet!=null)valores[1]=consecRet.toString();}
      catch(Exception ex){}
      try
      {if(codErr!=null)valores[2]=codErr.toString();}
      catch(Exception ex){}
      try
      {if(msgErr != null) valores[3]=msgErr;}
      catch(Exception ex){}
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
                 v_menex = "Error de comunicación no se tiene conexión con el servidor de datos, por favor intente de nuevo.";
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_menex =  "Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.";
                     }
                     else
                     {
                       v_menex = "Mensaje de error: "+ex;
                     }
      valores[3]="Ocurrio la exception="+v_menex;
      return valores;
    }
    return valores;
  }
///////////////////////Calcula Fecha efectiva hacia atras////////////////////
public static String TBPBD_FechaActual()
{
  String v_fecha    = new String();
  java.sql.Date fec = new java.sql.Date(4);
  try
  {
    /*@lineinfo:generated-code*//*@lineinfo:333^5*/

//  ************************************************************
//  #sql fec = { VALUES(TB_FFECHA_SIGUIENTE(-1)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TB_FFECHA_SIGUIENTE(-1) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.DATE);
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   fec = (java.sql.Date)__sJT_st.getDate(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:333^48*/
    v_fecha  = fec.toString();
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
   return "Exception en TBPBD_FechaActual "+v_menex;
  }
 return v_fecha;
}
///////Inserta informacion en Ajustes y Cargos Ajustes cuando aplicamos retiros/no se utiliza/////
public static String TBPBD_Aplicar(String cod_producto,
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
                                     int consecRetP)
{
 String strInsert=new String("NO");
 try
  {
   val_difNetos *= (-1);
   /*@lineinfo:generated-code*//*@lineinfo:389^4*/

//  ************************************************************
//  #sql { INSERT INTO
//                       TBAJUSTES
//                       (AJU_CON_PRO_CODIGO
//                       ,AJU_CON_NUMERO
//                       ,AJU_CONSECUTIVO
//                       ,AJU_LINEA
//                       ,AJU_FECHA_PROCESO
//                       ,AJU_FECHA_EFECTIVA
//                       ,AJU_COT_REF_TRANSACCION
//                       ,AJU_COT_REF_TIPO_TRANSACCION
//                       ,AJU_COT_REF_CLASE_TRANSACCION
//                       ,AJU_VALOR
//                       ,AJU_RAZON_AJUSTE
//                       ,AJU_USUARIO
//                       ,AJU_RETIRO_ORIGINAL
//                       ,AJU_RETIRO_ACTUAL
//                       ,AJU_VALOR_UNIDAD)
//                VALUES
//                    (:cod_producto
//                    ,:num_contrato
//                    ,:consecAjus
//                    ,:linea
//                    ,TO_DATE(:v_fecha_proc,'RRRR-MM-DD')
//                    ,:v_fecha_choose
//                    ,:v_trans
//                    ,:v_tipTrans
//                    ,:v_clasTrans
//                    ,:val_difNetos
//                    ,:v_razon
//                    ,:usuario
//                    ,:consecRet
//                    ,:consecRetP
//                    ,:val_unidad) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "INSERT INTO\n                     TBAJUSTES\n                     (AJU_CON_PRO_CODIGO\n                     ,AJU_CON_NUMERO\n                     ,AJU_CONSECUTIVO\n                     ,AJU_LINEA\n                     ,AJU_FECHA_PROCESO\n                     ,AJU_FECHA_EFECTIVA\n                     ,AJU_COT_REF_TRANSACCION\n                     ,AJU_COT_REF_TIPO_TRANSACCION\n                     ,AJU_COT_REF_CLASE_TRANSACCION\n                     ,AJU_VALOR\n                     ,AJU_RAZON_AJUSTE\n                     ,AJU_USUARIO\n                     ,AJU_RETIRO_ORIGINAL\n                     ,AJU_RETIRO_ACTUAL\n                     ,AJU_VALOR_UNIDAD)\n              VALUES\n                  ( :1  \n                  , :2  \n                  , :3  \n                  , :4  \n                  ,TO_DATE( :5  ,'RRRR-MM-DD')\n                  , :6  \n                  , :7  \n                  , :8  \n                  , :9  \n                  , :10  \n                  , :11  \n                  , :12  \n                  , :13  \n                  , :14  \n                  , :15  )";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"5TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecAjus);
   __sJT_st.setInt(4,linea);
   __sJT_st.setString(5,v_fecha_proc);
   __sJT_st.setDate(6,v_fecha_choose);
   __sJT_st.setString(7,v_trans);
   __sJT_st.setString(8,v_tipTrans);
   __sJT_st.setString(9,v_clasTrans);
   __sJT_st.setDouble(10,val_difNetos);
   __sJT_st.setString(11,v_razon);
   __sJT_st.setString(12,usuario);
   __sJT_st.setInt(13,consecRet);
   __sJT_st.setInt(14,consecRetP);
   __sJT_st.setDouble(15,val_unidad);
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:421^32*/
      strInsert=TBPBD_CargoAjuste(cod_producto,num_contrato,consecRet,consecRetP,consecAjus,linea);
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
                 v_menex = "Error de comunicación no se tiene conexión con el servidor de datos, por favor intente de nuevo.";
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_menex =  "Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.";
                     }
                     else
                     {
                       v_menex = "Mensaje de error: "+ex;
                     }
 return "Exception al insertar en Aplicar "+v_menex;
 }
return strInsert;
}
////////////no se utiliza////Resta valores de R y R' de cargos retiros para insertar en cargos_ajustes //
public static String TBPBD_CargoAjuste(String cod_producto,
                                         String num_contrato,
                                         int consecRet,
                                         int consecRetP,
                                         int consecAjus,
                                         int linea){
    String strInsert = new String("NO");
    try
    {
      CONCARGORET cargoret;
      CONCARGORET cargoretP;
      int ii         = 0;
      String v_ii    = new String("");
      String v_cargo = new String("");
      double v_valor  = 0.0,v_valorP=0.0;
      while(ii<4)
      {
        ii++;
        v_ii = Integer.toString(ii);
        while(v_ii.length()<3)
        {v_ii = new String("0"+v_ii);}
        v_cargo  = "STC"+v_ii;
        v_valor  = 0.0;
        v_valorP = 0.0;
        /*@lineinfo:generated-code*//*@lineinfo:482^9*/

//  ************************************************************
//  #sql cargoret = { SELECT CAE_VALOR
//                         FROM TBCARGOS_RETIROS
//                         WHERE CAE_APR_RET_CON_PRO_CODIGO =:cod_producto
//                           AND CAE_APR_RET_CON_NUMERO     =:num_contrato
//                           AND CAE_APR_RET_CONSECUTIVO    =:consecRet
//                           AND CAE_REF_CARGO              =:v_cargo };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT CAE_VALOR\n                       FROM TBCARGOS_RETIROS\n                       WHERE CAE_APR_RET_CON_PRO_CODIGO = :1  \n                         AND CAE_APR_RET_CON_NUMERO     = :2  \n                         AND CAE_APR_RET_CONSECUTIVO    = :3  \n                         AND CAE_REF_CARGO              = :4 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"6TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecRet);
   __sJT_st.setString(4,v_cargo);
   // execute query
   cargoret = new TBPKT_AJUSTES.SQL_AJUSTE.CONCARGORET(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"6TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:487^67*/
        while(cargoret.next())
        {
          v_valor+=cargoret.CAE_VALOR();
        }
        cargoret.close();
        /*@lineinfo:generated-code*//*@lineinfo:493^9*/

//  ************************************************************
//  #sql cargoretP = { SELECT CAE_VALOR
//                          FROM TBCARGOS_RETIROS
//                          WHERE CAE_APR_RET_CON_PRO_CODIGO  =:cod_producto
//                            AND CAE_APR_RET_CON_NUMERO      =:num_contrato
//                            AND CAE_APR_RET_CONSECUTIVO     =:consecRetP
//                            AND CAE_REF_CARGO               =:v_cargo };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT CAE_VALOR\n                        FROM TBCARGOS_RETIROS\n                        WHERE CAE_APR_RET_CON_PRO_CODIGO  = :1  \n                          AND CAE_APR_RET_CON_NUMERO      = :2  \n                          AND CAE_APR_RET_CONSECUTIVO     = :3  \n                          AND CAE_REF_CARGO               = :4 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"7TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecRetP);
   __sJT_st.setString(4,v_cargo);
   // execute query
   cargoretP = new TBPKT_AJUSTES.SQL_AJUSTE.CONCARGORET(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"7TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:498^69*/
        while(cargoretP.next())
        {
          v_valorP+=cargoretP.CAE_VALOR();
        }
        cargoretP.close();
        //proceso de insertar en cargo_ajustes
        double v_valorDif=v_valor-v_valorP;
        /*@lineinfo:generated-code*//*@lineinfo:506^9*/

//  ************************************************************
//  #sql { INSERT INTO TBCARGOS_AJUSTES
//                      (
//                      CAA_AJU_CON_PRO_CODIGO
//                      ,CAA_AJU_CON_NUMERO
//                      ,CAA_AJU_CONSECUTIVO
//                      ,CAA_AJU_LINEA
//                      ,CAA_REF_CARGO
//                      ,CAA_VALOR
//                      )
//                  VALUES
//                      (:cod_producto
//                       ,:num_contrato
//                       ,:consecAjus
//                       ,:linea
//                       ,:v_cargo
//                       ,:v_valorDif) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "INSERT INTO TBCARGOS_AJUSTES\n                    (\n                    CAA_AJU_CON_PRO_CODIGO\n                    ,CAA_AJU_CON_NUMERO\n                    ,CAA_AJU_CONSECUTIVO\n                    ,CAA_AJU_LINEA\n                    ,CAA_REF_CARGO\n                    ,CAA_VALOR\n                    )\n                VALUES\n                    ( :1  \n                     , :2  \n                     , :3  \n                     , :4  \n                     , :5  \n                     , :6  )";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"8TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecAjus);
   __sJT_st.setInt(4,linea);
   __sJT_st.setString(5,v_cargo);
   __sJT_st.setDouble(6,v_valorDif);
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:521^35*/
     }
      strInsert="YES";
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
    return "Exception en TBPBD_CargoAjuste por "+v_menex;}
    return strInsert;
  }
//////Selecciona la información de los métodos de referencia de la tabla productos//////////
//////no se utiliza/////////para indicar los posibles cambios en el esquema de retiro/////////////////
public static String TBPBD_SelMetCambio(String cod_producto)
  {
    String esqCambio=new String("");
    try
    {
      CONPRODUCTO1 prodEsq;
      /*@lineinfo:generated-code*//*@lineinfo:564^7*/

//  ************************************************************
//  #sql prodEsq = { SELECT
//                       PRO_METODO_ORDEN
//                       ,PRO_METODO_BENEFICIO
//                       ,PRO_METODO_PENALIZACION
//                       ,PRO_METODO_CUENTA
//                       ,PRO_NATURALEZA_RETIRO
//                     FROM
//                       TBPRODUCTOS
//                     WHERE
//                       PRO_CODIGO=:cod_producto };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT\n                     PRO_METODO_ORDEN\n                     ,PRO_METODO_BENEFICIO\n                     ,PRO_METODO_PENALIZACION\n                     ,PRO_METODO_CUENTA\n                     ,PRO_NATURALEZA_RETIRO\n                   FROM\n                     TBPRODUCTOS\n                   WHERE\n                     PRO_CODIGO= :1 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"9TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   // execute query
   prodEsq = new TBPKT_AJUSTES.SQL_AJUSTE.CONPRODUCTO1(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"9TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:573^46*/
      if(prodEsq.next())
      {
        esqCambio="smo='"+prodEsq.PRO_METODO_ORDEN()+"' ";
        esqCambio+="smb='"+prodEsq.PRO_METODO_BENEFICIO()+"' ";
        esqCambio+="smp='"+prodEsq.PRO_METODO_PENALIZACION()+"' ";
        esqCambio+="smc='"+prodEsq.PRO_METODO_CUENTA()+"' ";
        esqCambio+="snr='"+prodEsq.PRO_NATURALEZA_RETIRO()+"'";
      }
      prodEsq.close();
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
    return "Exception en TBPBD_SelMetCambio por "+v_menex;}
    return esqCambio;
}
////////no se utiliza//Actualiza la acción y fecha de la acción del ajuste según decisión del cliente////
  public static String TBPBD_ActAccionAjustes(String cod_producto,
                                              String num_contrato,
                                              int consecAjus,
                                              int linea,
                                              String accion){
    String strUpdate=new String("NO");
    try{
      /*@lineinfo:generated-code*//*@lineinfo:623^7*/

//  ************************************************************
//  #sql { UPDATE
//               TBAJUSTES
//              SET
//                AJU_FECHA_ACCION  =  SYSDATE,
//                AJU_REF_ACCION    =:accion
//              WHERE
//                AJU_CON_NUMERO         =:num_contrato
//                AND AJU_CON_PRO_CODIGO =:cod_producto
//                AND AJU_CONSECUTIVO    =:consecAjus
//                AND AJU_LINEA          =:linea };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "UPDATE\n             TBAJUSTES\n            SET\n              AJU_FECHA_ACCION  =  SYSDATE,\n              AJU_REF_ACCION    = :1  \n            WHERE\n              AJU_CON_NUMERO         = :2  \n              AND AJU_CON_PRO_CODIGO = :3  \n              AND AJU_CONSECUTIVO    = :4  \n              AND AJU_LINEA          = :5 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"10TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,accion);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setString(3,cod_producto);
   __sJT_st.setInt(4,consecAjus);
   __sJT_st.setInt(5,linea);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:632^46*/
      strUpdate="YES";
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
      return "Exception en TBPBD_ActAccionAjustes por "+v_menex;
    }
    return strUpdate;
  }
////no se utiliza///Proceso de Actualización del estado del Retiro y si se reversa el retiro/////////
  public static String TBPBD_AnulRevRetiro(String cod_producto,
                                           String num_contrato,
                                           String estado,
                                           String ret_consec,
                                           String fecha_efec,
                                           double val_unidad,
                                           int v_update,
                                           int devValApor){
    String strUpdate=new String("NO");
    try{
      /*@lineinfo:generated-code*//*@lineinfo:676^7*/

//  ************************************************************
//  #sql strUpdate = { VALUES(TBFBD_REVERSAR_RETIROS(:cod_producto,:num_contrato,:estado,:ret_consec,:val_unidad,:v_update,:devValApor)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_REVERSAR_RETIROS( :2  , :3  , :4  , :5  , :6  , :7  , :8  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"11TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,cod_producto);
   __sJT_st.setString(3,num_contrato);
   __sJT_st.setString(4,estado);
   __sJT_st.setString(5,ret_consec);
   __sJT_st.setDouble(6,val_unidad);
   __sJT_st.setInt(7,v_update);
   __sJT_st.setInt(8,devValApor);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   strUpdate = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:676^136*/
      //#sql {COMMIT;};
      if(strUpdate.equalsIgnoreCase("NO"))
        strUpdate = " No se encontraron Aportes Retiros asociados a su elección. ";
      else if(!strUpdate.equalsIgnoreCase("YES"))
        strUpdate = " La producción de un error en el procedimiento de ANULAR("+strUpdate+")";
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
      return "Exception en TBPBD_ANULAR_REVERSAR "+v_menex;
    }
    return strUpdate;
  }
//////////no se utiliza////Registrar transacción por cada retiro modificado///////////////////////////////
  public static String  TBPBD_TransaccionLog(String v_tipTran,
                                             String consecRet,
                                             int linea,
                                             String v_tipMod,
                                             String v_razMod,
                                             String v_usuario){
    String strInsert="NO";
    try{
      /*@lineinfo:generated-code*//*@lineinfo:723^7*/

//  ************************************************************
//  #sql { INSERT INTO TBTRANSACCION_LOGS
//                    (TRL_TIPO_TRANSACCION,        TRL_APORTE_RETIRO_PRODUCTO,     TRL_FECHA,
//                     TRL_LINEA,                   TRL_TIPO_MODIFICACION,          TRL_RAZON_MODIFICACION,
//                     TRL_USUARIO)
//                VALUES
//                    (:v_tipTran,                  :consecRet,                     SYSDATE,
//                     :linea,                      :v_tipMod,                      :v_razMod,
//                     :v_usuario) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
   String theSqlTS = "INSERT INTO TBTRANSACCION_LOGS\n                  (TRL_TIPO_TRANSACCION,        TRL_APORTE_RETIRO_PRODUCTO,     TRL_FECHA,\n                   TRL_LINEA,                   TRL_TIPO_MODIFICACION,          TRL_RAZON_MODIFICACION,\n                   TRL_USUARIO)\n              VALUES\n                  ( :1  ,                   :2  ,                     SYSDATE,\n                    :3  ,                       :4  ,                       :5  ,\n                    :6  )";
   __sJT_st = __sJT_ec.prepareOracleBatchableStatement(__sJT_cc,"12TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_tipTran);
   __sJT_st.setString(2,consecRet);
   __sJT_st.setInt(3,linea);
   __sJT_st.setString(4,v_tipMod);
   __sJT_st.setString(5,v_razMod);
   __sJT_st.setString(6,v_usuario);
  // execute statement
   __sJT_ec.oracleExecuteBatchableUpdate();
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:730^31*/
      strInsert="YES";
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
      return "Exception en TBPBD_TransaccionLog "+v_menex;
    }
    return strInsert;
  }
/////////////////////Realizar todos los cambios con la Base de Datos////////////////////////
  public static boolean TBPBD_Commit(){
    try{
      /*@lineinfo:generated-code*//*@lineinfo:767^7*/

//  ************************************************************
//  #sql { COMMIT };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleCommit(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:767^19*/
      return true;
    }catch(Exception e){
      return false;
    }
  }
/////////////////////No Realizar cambios con la Base de Datos////////////////////////
  public static boolean TBPBD_RollBack(){
    try{
      /*@lineinfo:generated-code*//*@lineinfo:776^7*/

//  ************************************************************
//  #sql { ROLLBACK };
//  ************************************************************

  ((sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : sqlj.runtime.ref.DefaultContext.getDefaultContext().getExecutionContext().getOracleContext()).oracleRollback(sqlj.runtime.ref.DefaultContext.getDefaultContext());


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:776^21*/
      return true;
    }catch(Exception e){
      return false;
    }
  }

/////////////////////////Traer nombres y apellidos del contrato-producto//////////////////
  public static String[] TBPBD_ContratoNomApel(String cod_producto,String num_contrato)
  {
    String strNombApel = new String("No existe");
    String fechaCancel = new String("");
    String cadena[]    = new String[2];
    try
    {
      CONTRATONOMBAPEL nombApel;
      /*@lineinfo:generated-code*//*@lineinfo:792^7*/

//  ************************************************************
//  #sql nombApel = { SELECT
//                       CON_NOMBRES
//                       ,CON_APELLIDOS
//                       ,CON_FECHA_CANCELACION
//                       FROM TBCONTRATOS
//                       WHERE CON_PRO_CODIGO =:cod_producto
//                       AND CON_NUMERO       =:num_contrato };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT\n                     CON_NOMBRES\n                     ,CON_APELLIDOS\n                     ,CON_FECHA_CANCELACION\n                     FROM TBCONTRATOS\n                     WHERE CON_PRO_CODIGO = :1  \n                     AND CON_NUMERO       = :2 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"13TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   // execute query
   nombApel = new TBPKT_AJUSTES.SQL_AJUSTE.CONTRATONOMBAPEL(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"13TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:798^58*/
      if(nombApel.next())
       {
         strNombApel = nombApel.CON_NOMBRES()+" "+nombApel.CON_APELLIDOS();
         if(nombApel.CON_FECHA_CANCELACION()==null)
          fechaCancel = "NO";
         else
          fechaCancel = "SI";
       }

      nombApel.close();
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
      cadena[0]="Exception en TBPBD_ContratoNomApel "+v_menex;
      return cadena;
     }
    cadena[0] = strNombApel;
    cadena[1] = fechaCancel;
    return cadena;
  }
/////////////////////traer valores de referencia del esquema retiro////////////////////
  public static String[] TBPBD_BuildRef(String v_prf,
                                        String k_ini){
    int ii=1;
    String v_ref[]=new String[2];
    String v_desc=new String("");
    String v_val=new String("");
    String v_ii=new String("");
    if(!k_ini.equals("1"))
      v_desc+=v_prf.toLowerCase()+k_ini+"='ninguno' ";
    try{
     TBREFERENCIAS tbref;
      while(true){
        v_ii=Integer.toString(ii);
        while(v_ii.length()<3)
          v_ii=new String("0"+v_ii);

        String key=new String(v_prf+v_ii);
        /*@lineinfo:generated-code*//*@lineinfo:863^9*/

//  ************************************************************
//  #sql tbref = { SELECT REF_DESCRIPCION,     REF_VALOR
//                        FROM TBREFERENCIAS
//                      WHERE REF_CODIGO=:key };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT REF_DESCRIPCION,     REF_VALOR\n                      FROM TBREFERENCIAS\n                    WHERE REF_CODIGO= :1 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"14TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,key);
   // execute query
   tbref = new TBPKT_AJUSTES.SQL_AJUSTE.TBREFERENCIAS(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"14TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:865^42*/
        if(tbref.next()){
          v_desc+=v_prf.toLowerCase()+v_ii+"='"+tbref.REF_DESCRIPCION().toLowerCase()+"' ";
          v_val+=v_prf.toLowerCase()+v_ii+"='"+tbref.REF_VALOR()+"' ";
        }else{
          break;
        }
        ii++;
      }
      tbref.close();
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

      v_ref[0]="Exception en TBPBD_BuildRef "+v_menex;
      return v_ref;
    }
    v_ref[0]=v_desc;v_ref[1]=v_val;
    return v_ref;
  }
/////////////////////selecciona todos los retiros reversados///////////////////////////////
  public static String TBPBD_SelAllRetRev(String num_contrato,
                                          String cod_producto,
                                          String estado){
    String v_retiros=new String("");
    int ii=1;
    try{
      TBFINDRETREV findRetRev;
      /*@lineinfo:generated-code*//*@lineinfo:918^7*/

//  ************************************************************
//  #sql findRetRev = { SELECT
//                         RET_CONSECUTIVO
//                         ,RET_TRANSACCION
//                         ,RET_FECHA_EFECTIVA
//                         ,RET_FECHA_PROCESO
//                         ,RET_REF_TIPO_VALOR
//                         ,RET_VALOR_BRUTO
//                         ,RET_VALOR_NETO
//                         ,RET_REF_UNIDAD_PROCESO
//                         ,RET_VALOR_UNIDAD
//                         FROM
//                         TBRETIROS
//                         WHERE
//                         RET_CON_NUMERO          =:num_contrato
//                         AND RET_CON_PRO_CODIGO  =:cod_producto
//                         AND RET_REF_ESTADO      =:estado
//                         ORDER BY RET_FECHA_EFECTIVA
//                          };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT\n                       RET_CONSECUTIVO\n                       ,RET_TRANSACCION\n                       ,RET_FECHA_EFECTIVA\n                       ,RET_FECHA_PROCESO\n                       ,RET_REF_TIPO_VALOR\n                       ,RET_VALOR_BRUTO\n                       ,RET_VALOR_NETO\n                       ,RET_REF_UNIDAD_PROCESO\n                       ,RET_VALOR_UNIDAD\n                       FROM\n                       TBRETIROS\n                       WHERE\n                       RET_CON_NUMERO          = :1  \n                       AND RET_CON_PRO_CODIGO  = :2  \n                       AND RET_REF_ESTADO      = :3  \n                       ORDER BY RET_FECHA_EFECTIVA";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"15TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,num_contrato);
   __sJT_st.setString(2,cod_producto);
   __sJT_st.setString(3,estado);
   // execute query
   findRetRev = new TBPKT_AJUSTES.SQL_AJUSTE.TBFINDRETREV(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"15TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:936^24*/
      while(findRetRev.next())
      {
        String v_const      = Integer.toString(findRetRev.RET_CONSECUTIVO());
        String v_transa     = findRetRev.RET_TRANSACCION();
        String v_fechafnd   = findRetRev.RET_FECHA_EFECTIVA().toString();
        String v_fechap     = findRetRev.RET_FECHA_PROCESO().toString();
        String v_tip_unidad = findRetRev.RET_REF_UNIDAD_PROCESO();
        String v_fecCargue  = new String("");
        //llamar lo de johanna
        String v_valorNeto     = Double.toString(findRetRev.RET_VALOR_NETO());
        String v_valor         = new String("0");
        String v_vu            = new String("0");
        v_valor = Double.toString(findRetRev.RET_VALOR_BRUTO());
        v_vu    = Double.toString(findRetRev.RET_VALOR_UNIDAD());
        String v_valor_esquema = new String("");
        /*if(findRetRev.RET_REF_TIPO_VALOR().equalsIgnoreCase("STV001"))
          v_valor = Double.toString(findRetRev.RET_VALOR_BRUTO());
        else
        {
         if(findRetRev.RET_REF_TIPO_VALOR().equalsIgnoreCase("STV002"))
            v_valor = Double.toString(findRetRev.RET_VALOR_NETO());
        }*/
        String v_ii = Integer.toString(ii);
        //guardar en una cadena consecutiva todos los valores por cada retiro reversado
        v_retiros += "v_vu"+v_ii+"='"+v_vu+"' const"+v_ii+"='"+v_const+"' fechae"+v_ii+"='"+v_fechafnd+"' fechap"+v_ii+"='"+v_fechap+"' valor"+v_ii+"='"+v_valor+"' valneto"+v_ii+"='"+v_valorNeto+"' multif"+v_ii+"='"+v_transa+"' feccargue"+v_ii+"='"+v_fecCargue+"' ";
        ii++;
      }
    findRetRev.close();
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

      return "Exception tomando los retiros reversados por "+v_menex;
   }
  return "<"+v_retiros+">";
}
////////////////////selecciona y devuelve todos los retiros en estado vigente//////////////////////////
  public static String TBPBD_SelAllRetAnu(String num_contrato,
                                          String cod_producto,
                                          String estado,
                                          String fechaMax,
                                          String fechaMin){
    String v_retiros=new String();
    int ii=1;
    try{
      TBFINDRETANU findRet;
      /*@lineinfo:generated-code*//*@lineinfo:1010^7*/

//  ************************************************************
//  #sql findRet = { SELECT RET_CONSECUTIVO,       RET_VALOR_UNIDAD,         RET_FECHA_EFECTIVA,       RET_FECHA_PROCESO,
//                             RET_REF_TIPO_VALOR,    RET_VALOR_BRUTO,          RET_VALOR_NETO,
//                             RET_REF_METODO_ORDEN,  RET_REF_METODO_BENEFICIO, RET_REF_METODO_PENALIZACION,
//                             RET_REF_METODO_CUENTA, RET_REF_NATURALEZA,       RET_RESPETAR_NATURALEZA,RET_REF_UNIDAD_PROCESO
//                       FROM TBRETIROS
//                       WHERE RET_CON_NUMERO       =:num_contrato
//                         AND RET_CON_PRO_CODIGO   =:cod_producto
//                         AND RET_REF_ESTADO       =:estado
//                         AND RET_FECHA_EFECTIVA>  =:fechaMin
//                         AND RET_FECHA_EFECTIVA  <=:fechaMax
//                      ORDER BY RET_FECHA_EFECTIVA };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT RET_CONSECUTIVO,       RET_VALOR_UNIDAD,         RET_FECHA_EFECTIVA,       RET_FECHA_PROCESO,\n                           RET_REF_TIPO_VALOR,    RET_VALOR_BRUTO,          RET_VALOR_NETO,\n                           RET_REF_METODO_ORDEN,  RET_REF_METODO_BENEFICIO, RET_REF_METODO_PENALIZACION,\n                           RET_REF_METODO_CUENTA, RET_REF_NATURALEZA,       RET_RESPETAR_NATURALEZA,RET_REF_UNIDAD_PROCESO\n                     FROM TBRETIROS\n                     WHERE RET_CON_NUMERO       = :1  \n                       AND RET_CON_PRO_CODIGO   = :2  \n                       AND RET_REF_ESTADO       = :3  \n                       AND RET_FECHA_EFECTIVA>  = :4  \n                       AND RET_FECHA_EFECTIVA  <= :5  \n                    ORDER BY RET_FECHA_EFECTIVA";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"16TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,num_contrato);
   __sJT_st.setString(2,cod_producto);
   __sJT_st.setString(3,estado);
   __sJT_st.setString(4,fechaMin);
   __sJT_st.setString(5,fechaMax);
   // execute query
   findRet = new TBPKT_AJUSTES.SQL_AJUSTE.TBFINDRETANU(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"16TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1020^48*/
      while(findRet.next())
      {
        String v_const        = Integer.toString(findRet.RET_CONSECUTIVO());
        String v_valorUnidad1 = Double.toString(findRet.RET_VALOR_UNIDAD());
        String v_fechafnd     = findRet.RET_FECHA_EFECTIVA().toString();
        String v_fechaP       = findRet.RET_FECHA_PROCESO().toString();
        String v_tipo         = findRet.RET_REF_TIPO_VALOR();
        String tip_unidad     = findRet.RET_REF_UNIDAD_PROCESO();
        String v_fecCargue    = new String("");
        //llamar lo de johanna
        String v_neto  = Double.toString(findRet.RET_VALOR_NETO());
        String v_bruto = Double.toString(findRet.RET_VALOR_BRUTO());
        String v_valor = new String("0");
        String v_valor_esquema = new String("");
        v_valor = v_bruto;
        /*if(v_tipo.equalsIgnoreCase("STV001"))
          v_valor = v_bruto;
        else
         {
          if(v_tipo.equalsIgnoreCase("STV002"))
            v_valor = v_neto;
         }*/
        //verificar si el esquema de retiro es null o con valor
        if(findRet.RET_REF_METODO_ORDEN()==null) v_valor_esquema+="smo=%SMO000% ";
          else v_valor_esquema+="smo=%"+findRet.RET_REF_METODO_ORDEN()+"% ";
        if(findRet.RET_REF_METODO_BENEFICIO()==null) v_valor_esquema+="smb=%SMB000% ";
          else v_valor_esquema+="smb=%"+findRet.RET_REF_METODO_BENEFICIO()+"% ";
        if(findRet.RET_REF_METODO_PENALIZACION()==null) v_valor_esquema+="smp=%SMP000% ";
          else v_valor_esquema+="smp=%"+findRet.RET_REF_METODO_PENALIZACION()+"% ";
        if(findRet.RET_REF_METODO_CUENTA()==null) v_valor_esquema+="smc=%SMC000% ";
          else v_valor_esquema+="smc=%"+findRet.RET_REF_METODO_CUENTA()+"% ";
        if(findRet.RET_REF_NATURALEZA()==null) v_valor_esquema+="snr=%SNR000% ";
          else v_valor_esquema+="snr=%"+findRet.RET_REF_NATURALEZA()+"% ";
        if(findRet.RET_RESPETAR_NATURALEZA()==null) v_valor_esquema+="srn=%%";
          else  v_valor_esquema+="srn=%"+findRet.RET_RESPETAR_NATURALEZA()+"%";
        String v_ii=Integer.toString(ii);
        //guardar en una cadena consecutiva todos los valores por cada retiro
        v_retiros+="const"+v_ii+"='"+v_const+"' fechae"+v_ii+"='"+v_fechafnd+"' fechap"+v_ii+"='"+v_fechaP+"' valor"+v_ii+"='"+v_valor+"' esquema"+v_ii+"='"+v_valor_esquema+"' neto"+v_ii+"='"+v_neto+"' tipo"+v_ii+"='"+v_tipo+"' bruto"+v_ii+"='"+v_bruto+"' unidad"+v_ii+"='"+v_valorUnidad1+"' feccargue"+v_ii+"='"+v_fecCargue+"' ";
        ii++;
      }
      findRet.close();
    }
    catch(Exception e)
    {
     System.out.println(" ");
    }
    return "<"+v_retiros+">";
  }
/////////////Selecciona de la tabla ajustes las acciones que desea ver el cliente////////////
  public static String TBPBD_AllRepAjustes(String v_mostrar,String v_fecDesde,String v_fecHasta,String v_contDesde,String v_contHasta){
    String valores=new String("");
    String v_accion=new String("");
    String v_accion1=new String("");
    String v_cadena[]=new String[2];
    CONAJUSTES1 ajustes;
    int ii=1;String v_ii=new String("");
    try{
      ///seleccionar la información de una de las tres acciones
      if(v_mostrar.equals("1") || v_mostrar.equals("2") || v_mostrar.equals("3"))
      {//1
        if(v_mostrar.equals("1"))
           v_accion="SAC001";
        if(v_mostrar.equals("2"))
           v_accion="SAC002";
        if(v_mostrar.equals("3"))
          v_accion="SAC003";
        //si los dos son en blanco
        if(v_contDesde.trim().equals("") && v_contHasta.trim().equals(""))
        {
           /*@lineinfo:generated-code*//*@lineinfo:1090^12*/

//  ************************************************************
//  #sql ajustes = { SELECT  AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,
//                                   AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,
//                                   TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,
//                                   TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,
//                                   AJU_VALOR,
//                                   AJU_REF_ACCION,
//                                   TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju
//                            FROM TBAJUSTES
//                           WHERE AJU_REF_ACCION= :v_accion AND
//                                TRUNC(AJU_FECHA_ACCION) >= :v_fecDesde AND
//                                TRUNC(AJU_FECHA_ACCION) <= :v_fecHasta };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT  AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,\n                                 AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,\n                                 TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,\n                                 TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,\n                                 AJU_VALOR,\n                                 AJU_REF_ACCION,\n                                 TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju\n                          FROM TBAJUSTES\n                         WHERE AJU_REF_ACCION=  :1   AND\n                              TRUNC(AJU_FECHA_ACCION) >=  :2   AND\n                              TRUNC(AJU_FECHA_ACCION) <=  :3 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"17TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_accion);
   __sJT_st.setString(2,v_fecDesde);
   __sJT_st.setString(3,v_fecHasta);
   // execute query
   ajustes = new TBPKT_AJUSTES.SQL_AJUSTE.CONAJUSTES1(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"17TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1100^69*/
           ii=1;
           while(ajustes.next())
           {
              v_ii=Integer.toString(ii++);
              v_cadena=TBPBD_ContratoNomApel(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO());
              valores+="nom"+v_ii+"='"+v_cadena[0]+"' ";
              valores+="cont"+v_ii+"='"+ajustes.AJU_CON_NUMERO()+"' ";
              valores+="conse"+v_ii+"='"+ajustes.AJU_CONSECUTIVO()+"' ";
              valores+="fajs"+v_ii+"='"+ajustes.fecefe()+"' ";
              valores+="fpro"+v_ii+"='"+ajustes.fecpro()+"' ";
              valores+="val"+v_ii+"='"+Float.toString(ajustes.AJU_VALOR())+"' ";
              valores+="fdec"+v_ii+"='"+ajustes.fecaju()+"' ";
              valores+="accion"+v_ii+"='"+ajustes.AJU_REF_ACCION()+"' ";
              valores+="bco"+v_ii+"='"+TBPBD_BancoCuenta(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO(),ajustes.AJU_RETIRO_ORIGINAL())+"' ";
           }
           ajustes.close();
        }//si hasta esta vacio solo un contrato
        else if(v_contHasta.trim().equals(""))
            {
               /*@lineinfo:generated-code*//*@lineinfo:1120^16*/

//  ************************************************************
//  #sql ajustes = { SELECT  AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,
//                                       AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,
//                                       TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,
//                                       TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,
//                                       AJU_VALOR,
//                                       AJU_REF_ACCION,
//                                       TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju
//                                  FROM TBAJUSTES
//                                 WHERE AJU_REF_ACCION= :v_accion AND
//                                       AJU_CON_NUMERO = :v_contDesde AND
//                                TRUNC(AJU_FECHA_ACCION) >= :v_fecDesde AND
//                                TRUNC(AJU_FECHA_ACCION) <= :v_fecHasta };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT  AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,\n                                     AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,\n                                     TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,\n                                     TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,\n                                     AJU_VALOR,\n                                     AJU_REF_ACCION,\n                                     TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju\n                                FROM TBAJUSTES\n                               WHERE AJU_REF_ACCION=  :1   AND\n                                     AJU_CON_NUMERO =  :2   AND\n                              TRUNC(AJU_FECHA_ACCION) >=  :3   AND\n                              TRUNC(AJU_FECHA_ACCION) <=  :4 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"18TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_accion);
   __sJT_st.setString(2,v_contDesde);
   __sJT_st.setString(3,v_fecDesde);
   __sJT_st.setString(4,v_fecHasta);
   // execute query
   ajustes = new TBPKT_AJUSTES.SQL_AJUSTE.CONAJUSTES1(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"18TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1131^69*/
               ii=1;
               while(ajustes.next())
               {
                  v_ii=Integer.toString(ii++);
                  v_cadena=TBPBD_ContratoNomApel(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO());
                  valores+="nom"+v_ii+"='"+v_cadena[0]+"' ";
                  valores+="cont"+v_ii+"='"+ajustes.AJU_CON_NUMERO()+"' ";
                  valores+="conse"+v_ii+"='"+ajustes.AJU_CONSECUTIVO()+"' ";
                  valores+="fajs"+v_ii+"='"+ajustes.fecefe()+"' ";
                  valores+="fpro"+v_ii+"='"+ajustes.fecpro()+"' ";
                  valores+="val"+v_ii+"='"+Float.toString(ajustes.AJU_VALOR())+"' ";
                  valores+="fdec"+v_ii+"='"+ajustes.fecaju()+"' ";
                  valores+="accion"+v_ii+"='"+ajustes.AJU_REF_ACCION()+"' ";
                  valores+="bco"+v_ii+"='"+TBPBD_BancoCuenta(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO(),ajustes.AJU_RETIRO_ORIGINAL())+"' ";
               }
               ajustes.close();
            }
            else//desde hasta
            {
               /*@lineinfo:generated-code*//*@lineinfo:1151^16*/

//  ************************************************************
//  #sql ajustes = { SELECT  AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,
//                                       AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,
//                                       TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,
//                                       TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,
//                                       AJU_VALOR,
//                                       AJU_REF_ACCION,
//                                       TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju
//                                  FROM TBAJUSTES
//                                 WHERE AJU_REF_ACCION= :v_accion AND
//                                       AJU_CON_NUMERO >= :v_contDesde AND
//                                      AJU_CON_NUMERO <= :v_contHasta AND
//                                TRUNC(AJU_FECHA_ACCION) >= :v_fecDesde AND
//                                TRUNC(AJU_FECHA_ACCION) <= :v_fecHasta };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT  AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,\n                                     AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,\n                                     TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,\n                                     TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,\n                                     AJU_VALOR,\n                                     AJU_REF_ACCION,\n                                     TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju\n                                FROM TBAJUSTES\n                               WHERE AJU_REF_ACCION=  :1   AND\n                                     AJU_CON_NUMERO >=  :2   AND\n                                    AJU_CON_NUMERO <=  :3   AND\n                              TRUNC(AJU_FECHA_ACCION) >=  :4   AND\n                              TRUNC(AJU_FECHA_ACCION) <=  :5 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"19TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_accion);
   __sJT_st.setString(2,v_contDesde);
   __sJT_st.setString(3,v_contHasta);
   __sJT_st.setString(4,v_fecDesde);
   __sJT_st.setString(5,v_fecHasta);
   // execute query
   ajustes = new TBPKT_AJUSTES.SQL_AJUSTE.CONAJUSTES1(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"19TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1163^69*/
               ii=1;
               while(ajustes.next())
               {
                  v_ii=Integer.toString(ii++);
                  v_cadena=TBPBD_ContratoNomApel(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO());
                  valores+="nom"+v_ii+"='"+v_cadena[0]+"' ";
                  valores+="cont"+v_ii+"='"+ajustes.AJU_CON_NUMERO()+"' ";
                  valores+="conse"+v_ii+"='"+ajustes.AJU_CONSECUTIVO()+"' ";
                  valores+="fajs"+v_ii+"='"+ajustes.fecefe()+"' ";
                  valores+="fpro"+v_ii+"='"+ajustes.fecpro()+"' ";
                  valores+="val"+v_ii+"='"+Float.toString(ajustes.AJU_VALOR())+"' ";
                  valores+="fdec"+v_ii+"='"+ajustes.fecaju()+"' ";
                  valores+="accion"+v_ii+"='"+ajustes.AJU_REF_ACCION()+"' ";
                  valores+="bco"+v_ii+"='"+TBPBD_BancoCuenta(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO(),ajustes.AJU_RETIRO_ORIGINAL())+"' ";
               }
               ajustes.close();
            }
      }//1
      //seleccionar la información de todas las acciones
      if(v_mostrar.equals("4"))
      {//2

        if(v_contDesde.trim().equals("") && v_contHasta.trim().equals(""))
        {
           /*@lineinfo:generated-code*//*@lineinfo:1188^12*/

//  ************************************************************
//  #sql ajustes = { SELECT AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,
//                                  AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,
//                                  TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,
//                                  TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,
//                                  AJU_VALOR,
//                                  AJU_REF_ACCION,
//                                  TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju
//                             FROM TBAJUSTES
//                            WHERE AJU_REF_ACCION IS NOT NULL AND
//                                 TRUNC(AJU_FECHA_ACCION) >= :v_fecDesde AND
//                                 TRUNC(AJU_FECHA_ACCION) <= :v_fecHasta };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,\n                                AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,\n                                TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,\n                                TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,\n                                AJU_VALOR,\n                                AJU_REF_ACCION,\n                                TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju\n                           FROM TBAJUSTES\n                          WHERE AJU_REF_ACCION IS NOT NULL AND\n                               TRUNC(AJU_FECHA_ACCION) >=  :1   AND\n                               TRUNC(AJU_FECHA_ACCION) <=  :2 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"20TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_fecDesde);
   __sJT_st.setString(2,v_fecHasta);
   // execute query
   ajustes = new TBPKT_AJUSTES.SQL_AJUSTE.CONAJUSTES1(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"20TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1198^70*/

           ii=1;
           while(ajustes.next()){
             v_ii=Integer.toString(ii++);
             v_cadena=TBPBD_ContratoNomApel(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO());
             valores+="nom"+v_ii+"='"+v_cadena[0]+"' ";
             valores+="cont"+v_ii+"='"+ajustes.AJU_CON_NUMERO()+"' ";
             valores+="conse"+v_ii+"='"+ajustes.AJU_CONSECUTIVO()+"' ";
             valores+="fajs"+v_ii+"='"+ajustes.fecefe()+"' ";
             valores+="fpro"+v_ii+"='"+ajustes.fecpro()+"' ";
             valores+="val"+v_ii+"='"+Float.toString(ajustes.AJU_VALOR())+"' ";
             valores+="fdec"+v_ii+"='"+ajustes.fecaju()+"' ";
             valores+="accion"+v_ii+"='"+ajustes.AJU_REF_ACCION()+"' ";
            valores+="bco"+v_ii+"='"+TBPBD_BancoCuenta(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO(),ajustes.AJU_RETIRO_ORIGINAL())+"' ";;
           }
           ajustes.close();
        }
        else  if(v_contHasta.trim().equals(""))
              {
                 /*@lineinfo:generated-code*//*@lineinfo:1218^18*/

//  ************************************************************
//  #sql ajustes = { SELECT AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,
//                                        AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,
//                                        TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,
//                                        TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,
//                                        AJU_VALOR,
//                                        AJU_REF_ACCION,
//                                        TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju
//                                        FROM TBAJUSTES
//                                        WHERE AJU_REF_ACCION IS NOT NULL AND
//                                        AJU_CON_NUMERO = :v_contDesde AND
//                                        TRUNC(AJU_FECHA_ACCION) >= :v_fecDesde AND
//                                        TRUNC(AJU_FECHA_ACCION) <= :v_fecHasta };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,\n                                      AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,\n                                      TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,\n                                      TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,\n                                      AJU_VALOR,\n                                      AJU_REF_ACCION,\n                                      TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju\n                                      FROM TBAJUSTES\n                                      WHERE AJU_REF_ACCION IS NOT NULL AND\n                                      AJU_CON_NUMERO =  :1   AND\n                                      TRUNC(AJU_FECHA_ACCION) >=  :2   AND\n                                      TRUNC(AJU_FECHA_ACCION) <=  :3 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"21TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_contDesde);
   __sJT_st.setString(2,v_fecDesde);
   __sJT_st.setString(3,v_fecHasta);
   // execute query
   ajustes = new TBPKT_AJUSTES.SQL_AJUSTE.CONAJUSTES1(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"21TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1229^77*/

                 ii=1;
                 while(ajustes.next()){
                   v_ii=Integer.toString(ii++);
                   v_cadena=TBPBD_ContratoNomApel(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO());
                   valores+="nom"+v_ii+"='"+v_cadena[0]+"' ";
                   valores+="cont"+v_ii+"='"+ajustes.AJU_CON_NUMERO()+"' ";
                   valores+="conse"+v_ii+"='"+ajustes.AJU_CONSECUTIVO()+"' ";
                   valores+="fajs"+v_ii+"='"+ajustes.fecefe()+"' ";
                   valores+="fpro"+v_ii+"='"+ajustes.fecpro()+"' ";
                   valores+="val"+v_ii+"='"+Float.toString(ajustes.AJU_VALOR())+"' ";
                   valores+="fdec"+v_ii+"='"+ajustes.fecaju()+"' ";
                   valores+="accion"+v_ii+"='"+ajustes.AJU_REF_ACCION()+"' ";
                   valores+="bco"+v_ii+"='"+TBPBD_BancoCuenta(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO(),ajustes.AJU_RETIRO_ORIGINAL())+"' ";;
                 }
                 ajustes.close();
              }
              else
              {
                /*@lineinfo:generated-code*//*@lineinfo:1249^17*/

//  ************************************************************
//  #sql ajustes = { SELECT AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,
//                                        AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,
//                                        TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,
//                                        TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,
//                                        AJU_VALOR,
//                                        AJU_REF_ACCION,
//                                        TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju
//                                        FROM TBAJUSTES
//                                        WHERE AJU_REF_ACCION IS NOT NULL AND
//                                        AJU_CON_NUMERO >= :v_contDesde AND
//                                        AJU_CON_NUMERO <= :v_contHasta AND
//                                        TRUNC(AJU_FECHA_ACCION) >= :v_fecDesde AND
//                                        TRUNC(AJU_FECHA_ACCION) <= :v_fecHasta };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,\n                                      AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,\n                                      TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,\n                                      TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,\n                                      AJU_VALOR,\n                                      AJU_REF_ACCION,\n                                      TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju\n                                      FROM TBAJUSTES\n                                      WHERE AJU_REF_ACCION IS NOT NULL AND\n                                      AJU_CON_NUMERO >=  :1   AND\n                                      AJU_CON_NUMERO <=  :2   AND\n                                      TRUNC(AJU_FECHA_ACCION) >=  :3   AND\n                                      TRUNC(AJU_FECHA_ACCION) <=  :4 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"22TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_contDesde);
   __sJT_st.setString(2,v_contHasta);
   __sJT_st.setString(3,v_fecDesde);
   __sJT_st.setString(4,v_fecHasta);
   // execute query
   ajustes = new TBPKT_AJUSTES.SQL_AJUSTE.CONAJUSTES1(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"22TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1261^77*/

                  ii=1;
                  while(ajustes.next()){
                   v_ii=Integer.toString(ii++);
                   v_cadena=TBPBD_ContratoNomApel(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO());
                   valores+="nom"+v_ii+"='"+v_cadena[0]+"' ";
                   valores+="cont"+v_ii+"='"+ajustes.AJU_CON_NUMERO()+"' ";
                   valores+="conse"+v_ii+"='"+ajustes.AJU_CONSECUTIVO()+"' ";
                   valores+="fajs"+v_ii+"='"+ajustes.fecefe()+"' ";
                   valores+="fpro"+v_ii+"='"+ajustes.fecpro()+"' ";
                   valores+="val"+v_ii+"='"+Float.toString(ajustes.AJU_VALOR())+"' ";
                   valores+="fdec"+v_ii+"='"+ajustes.fecaju()+"' ";
                   valores+="accion"+v_ii+"='"+ajustes.AJU_REF_ACCION()+"' ";
                   valores+="bco"+v_ii+"='"+TBPBD_BancoCuenta(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO(),ajustes.AJU_RETIRO_ORIGINAL())+"' ";;
                 }
                 ajustes.close();
              }
      }//2

      //seleccionar la información en combinación de las tres acciones
      if(v_mostrar.equals("5") || v_mostrar.equals("6") || v_mostrar.equals("7")){
        if(v_mostrar.equals("5")){v_accion="SAC001"; v_accion1="SAC002";}
        if(v_mostrar.equals("6")){v_accion="SAC001"; v_accion1="SAC003";}
        if(v_mostrar.equals("7")){v_accion="SAC002";v_accion1="SAC003";}

        if(v_contDesde.trim().equals("") && v_contHasta.trim().equals(""))
        {
           /*@lineinfo:generated-code*//*@lineinfo:1289^12*/

//  ************************************************************
//  #sql ajustes = { SELECT AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,
//                                  AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,
//                                  TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,
//                                  TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,
//                                  AJU_VALOR,
//                                  AJU_REF_ACCION,
//                                  TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju
//                             FROM TBAJUSTES
//                            WHERE AJU_REF_ACCION=:v_accion
//                               OR AJU_REF_ACCION=:v_accion1 AND
//                                  TRUNC(AJU_FECHA_ACCION) >= :v_fecDesde AND
//                                 TRUNC(AJU_FECHA_ACCION) <= :v_fecHasta };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,\n                                AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,\n                                TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,\n                                TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,\n                                AJU_VALOR,\n                                AJU_REF_ACCION,\n                                TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju\n                           FROM TBAJUSTES\n                          WHERE AJU_REF_ACCION= :1  \n                             OR AJU_REF_ACCION= :2   AND\n                                TRUNC(AJU_FECHA_ACCION) >=  :3   AND\n                               TRUNC(AJU_FECHA_ACCION) <=  :4 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"23TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_accion);
   __sJT_st.setString(2,v_accion1);
   __sJT_st.setString(3,v_fecDesde);
   __sJT_st.setString(4,v_fecHasta);
   // execute query
   ajustes = new TBPKT_AJUSTES.SQL_AJUSTE.CONAJUSTES1(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"23TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1300^70*/
           ii=1;
           while(ajustes.next()){
            v_ii=Integer.toString(ii++);
            v_cadena=TBPBD_ContratoNomApel(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO());
            valores+="nom"+v_ii+"='"+v_cadena[0]+"' ";
            valores+="cont"+v_ii+"='"+ajustes.AJU_CON_NUMERO()+"' ";
            valores+="conse"+v_ii+"='"+ajustes.AJU_CONSECUTIVO()+"' ";
            valores+="fajs"+v_ii+"='"+ajustes.fecefe()+"' ";
            valores+="fpro"+v_ii+"='"+ajustes.fecpro()+"' ";
            valores+="val"+v_ii+"='"+Float.toString(ajustes.AJU_VALOR())+"' ";
            valores+="fdec"+v_ii+"='"+ajustes.fecaju()+"' ";
            valores+="accion"+v_ii+"='"+ajustes.AJU_REF_ACCION()+"' ";
            valores+="bco"+v_ii+"='"+TBPBD_BancoCuenta(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO(),ajustes.AJU_RETIRO_ORIGINAL())+"' ";;
           }
           ajustes.close();
        }
        else if(v_contHasta.trim().equals(""))
             {
                /*@lineinfo:generated-code*//*@lineinfo:1319^17*/

//  ************************************************************
//  #sql ajustes = { SELECT AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,
//                                       AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,
//                                       TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,
//                                       TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,
//                                       AJU_VALOR,
//                                       AJU_REF_ACCION,
//                                       TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju
//                                  FROM TBAJUSTES
//                                 WHERE AJU_REF_ACCION=:v_accion
//                                    OR AJU_REF_ACCION=:v_accion1 AND
//                                       AJU_CON_NUMERO = :v_contDesde AND
//                                       TRUNC(AJU_FECHA_ACCION) >= :v_fecDesde AND
//                                       TRUNC(AJU_FECHA_ACCION) <= :v_fecHasta };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,\n                                     AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,\n                                     TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,\n                                     TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,\n                                     AJU_VALOR,\n                                     AJU_REF_ACCION,\n                                     TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju\n                                FROM TBAJUSTES\n                               WHERE AJU_REF_ACCION= :1  \n                                  OR AJU_REF_ACCION= :2   AND\n                                     AJU_CON_NUMERO =  :3   AND\n                                     TRUNC(AJU_FECHA_ACCION) >=  :4   AND\n                                     TRUNC(AJU_FECHA_ACCION) <=  :5 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"24TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_accion);
   __sJT_st.setString(2,v_accion1);
   __sJT_st.setString(3,v_contDesde);
   __sJT_st.setString(4,v_fecDesde);
   __sJT_st.setString(5,v_fecHasta);
   // execute query
   ajustes = new TBPKT_AJUSTES.SQL_AJUSTE.CONAJUSTES1(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"24TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1331^76*/
                ii=1;
                while(ajustes.next()){
                  v_ii=Integer.toString(ii++);
                  v_cadena=TBPBD_ContratoNomApel(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO());
                  valores+="nom"+v_ii+"='"+v_cadena[0]+"' ";
                  valores+="cont"+v_ii+"='"+ajustes.AJU_CON_NUMERO()+"' ";
                  valores+="conse"+v_ii+"='"+ajustes.AJU_CONSECUTIVO()+"' ";
                  valores+="fajs"+v_ii+"='"+ajustes.fecefe()+"' ";
                  valores+="fpro"+v_ii+"='"+ajustes.fecpro()+"' ";
                  valores+="val"+v_ii+"='"+Float.toString(ajustes.AJU_VALOR())+"' ";
                  valores+="fdec"+v_ii+"='"+ajustes.fecaju()+"' ";
                  valores+="accion"+v_ii+"='"+ajustes.AJU_REF_ACCION()+"' ";
                  valores+="bco"+v_ii+"='"+TBPBD_BancoCuenta(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO(),ajustes.AJU_RETIRO_ORIGINAL())+"' ";;
                }
                ajustes.close();
             }
             else
             {
                /*@lineinfo:generated-code*//*@lineinfo:1350^17*/

//  ************************************************************
//  #sql ajustes = { SELECT AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,
//                                       AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,
//                                       TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,
//                                       TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,
//                                       AJU_VALOR,
//                                       AJU_REF_ACCION,
//                                       TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju
//                                  FROM TBAJUSTES
//                                 WHERE AJU_REF_ACCION=:v_accion
//                                    OR AJU_REF_ACCION=:v_accion1 AND
//                                       AJU_CON_NUMERO >= :v_contDesde AND
//                                       AJU_CON_NUMERO <= :v_contHasta AND
//                                       TRUNC(AJU_FECHA_ACCION) >= :v_fecDesde AND
//                                       TRUNC(AJU_FECHA_ACCION) <= :v_fecHasta };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT AJU_CONSECUTIVO   ,AJU_CON_PRO_CODIGO,\n                                     AJU_CON_NUMERO,     AJU_RETIRO_ORIGINAL,\n                                     TO_CHAR(AJU_FECHA_EFECTIVA,'RRRR/MM/DD')  fecefe,\n                                     TO_CHAR(AJU_FECHA_PROCESO,'RRRR/MM/DD') fecpro,\n                                     AJU_VALOR,\n                                     AJU_REF_ACCION,\n                                     TO_CHAR(AJU_FECHA_ACCION,'RRRR/MM/DD') fecaju\n                                FROM TBAJUSTES\n                               WHERE AJU_REF_ACCION= :1  \n                                  OR AJU_REF_ACCION= :2   AND\n                                     AJU_CON_NUMERO >=  :3   AND\n                                     AJU_CON_NUMERO <=  :4   AND\n                                     TRUNC(AJU_FECHA_ACCION) >=  :5   AND\n                                     TRUNC(AJU_FECHA_ACCION) <=  :6 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"25TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_accion);
   __sJT_st.setString(2,v_accion1);
   __sJT_st.setString(3,v_contDesde);
   __sJT_st.setString(4,v_contHasta);
   __sJT_st.setString(5,v_fecDesde);
   __sJT_st.setString(6,v_fecHasta);
   // execute query
   ajustes = new TBPKT_AJUSTES.SQL_AJUSTE.CONAJUSTES1(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"25TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1363^76*/
                ii=1;
                while(ajustes.next()){
                  v_ii=Integer.toString(ii++);
                  v_cadena=TBPBD_ContratoNomApel(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO());
                  valores+="nom"+v_ii+"='"+v_cadena[0]+"' ";
                  valores+="cont"+v_ii+"='"+ajustes.AJU_CON_NUMERO()+"' ";
                  valores+="conse"+v_ii+"='"+ajustes.AJU_CONSECUTIVO()+"' ";
                  valores+="fajs"+v_ii+"='"+ajustes.fecefe()+"' ";
                  valores+="fpro"+v_ii+"='"+ajustes.fecpro()+"' ";
                  valores+="val"+v_ii+"='"+Float.toString(ajustes.AJU_VALOR())+"' ";
                  valores+="fdec"+v_ii+"='"+ajustes.fecaju()+"' ";
                  valores+="accion"+v_ii+"='"+ajustes.AJU_REF_ACCION()+"' ";
                  valores+="bco"+v_ii+"='"+TBPBD_BancoCuenta(ajustes.AJU_CON_PRO_CODIGO(),ajustes.AJU_CON_NUMERO(),ajustes.AJU_RETIRO_ORIGINAL())+"' ";;
                }
                ajustes.close();
             }
      }//3

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
      return "Exception en TBPBD_AllRepAjustes por "+v_menex;
    }
    return valores;
  }
/////////////////selecciona banco y cuenta del egreso original de un retiro/////////////////
  public static String TBPBD_BancoCuenta(String cod_producto,
                                         String num_contrato,
                                         int consecRet){
    String v_cadena=new String("");
    try{
      CONRETIROS bcoRet;
      /*@lineinfo:generated-code*//*@lineinfo:1421^7*/

//  ************************************************************
//  #sql bcoRet = { SELECT
//                    DECODE(RET_BANCO,NULL,' ',RET_BANCO)    RET_BANCO
//                    ,DECODE(RET_CUENTA,NULL,' ',RET_CUENTA) RET_CUENTA
//                    FROM
//                    TBRETIROS
//                    WHERE
//                    RET_CON_PRO_CODIGO  =:cod_producto
//                    AND RET_CON_NUMERO  =:num_contrato
//                    AND RET_CONSECUTIVO =:consecRet };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT\n                  DECODE(RET_BANCO,NULL,' ',RET_BANCO)    RET_BANCO\n                  ,DECODE(RET_CUENTA,NULL,' ',RET_CUENTA) RET_CUENTA\n                  FROM\n                  TBRETIROS\n                  WHERE\n                  RET_CON_PRO_CODIGO  = :1  \n                  AND RET_CON_NUMERO  = :2  \n                  AND RET_CONSECUTIVO = :3 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"26TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecRet);
   // execute query
   bcoRet = new TBPKT_AJUSTES.SQL_AJUSTE.CONRETIROS(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"26TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1429^51*/
      if(bcoRet.next()){
        v_cadena="("+bcoRet.RET_BANCO()+")"+bcoRet.RET_CUENTA();
      }
      bcoRet.close();
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
      return "Exception en TBPBD_BancoCuenta por "+v_menex;
    }
    return v_cadena;
  }
////////////////////fecha -1 //////////////////////////////////

public static String TBPBD_FECHA_VALUNI(String v_fecha_choose)
{
 try
 {
     /*@lineinfo:generated-code*//*@lineinfo:1473^6*/

//  ************************************************************
//  #sql { SELECT TO_CHAR(TO_DATE(:v_fecha_choose,'RRRR-MM-DD') - 1,'RRRRMMDD')  FROM DUAL  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT TO_CHAR(TO_DATE( :1  ,'RRRR-MM-DD') - 1,'RRRRMMDD')   FROM DUAL";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"27TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // set IN parameters
   __sJT_st.setString(1,v_fecha_choose);
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   v_fecha_choose = (String)__sJT_rs.getString(1);
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1473^115*/
    return  v_fecha_choose;

 }
 catch(Exception ex){
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
   return "Mensaje de error "+v_menex;
    }



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
         /*@lineinfo:generated-code*//*@lineinfo:1529^10*/

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
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"28TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   __sJT_st.setInt(3,consecAjusOnly);
   // execute query
   ajusNoDec = new TBPKT_AJUSTES.SQL_AJUSTE.CONAJUSTES(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"28TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1529^350*/
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
        /*@lineinfo:generated-code*//*@lineinfo:1559^9*/

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
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"29TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,cod_producto);
   __sJT_st.setString(2,num_contrato);
   // execute query
   ajusNoDec = new TBPKT_AJUSTES.SQL_AJUSTE.CONAJUSTES(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"29TBPKT_AJUSTES.SQL_AJUSTE",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1567^48*/
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


      v_NDec[0]="Exception en SellAllRetDec por "+v_menex;
      return v_NDec;
    }
    v_NDec[0]="<"+v_retNDec+">";v_NDec[1]="<"+v_consec+">";
    return v_NDec;
  }

/*******************************************/
//CONSULTAR UNIDAD

public static String TBPL_BuscarUnidad(String v_unidad)
{
 String v_coduni = "";
 try
 {
 /*@lineinfo:generated-code*//*@lineinfo:1642^2*/

//  ************************************************************
//  #sql v_coduni = { values(TBFBD_REFERENCIAS(:v_unidad,'UUP')) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_REFERENCIAS( :2  ,'UUP') \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"30TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_unidad);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_coduni = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1642^60*/
 return v_coduni;
 }
 catch(Exception  ex)
 {
  return "XXXXXX";
 }
}
/*******************************************/
//CONSULTAR tipo de usuario

public static String TBPL_BuscarTipoUsuario(String v_tipousu)
{
 String v_codusu = "";
 try
 {
  /*@lineinfo:generated-code*//*@lineinfo:1658^3*/

//  ************************************************************
//  #sql v_codusu = { values(TBFBD_REFERENCIAS(:v_tipousu,'UTU')) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_REFERENCIAS( :2  ,'UTU') \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"31TBPKT_AJUSTES.SQL_AJUSTE",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_tipousu);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_codusu = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1658^62*/
 return v_codusu;
 }
 catch(Exception  ex)
 {
  return "XXXXXX";
 }
}

    public void TBPBD_CerrarConexionBD(){
        try
          {
           DefaultContext.getDefaultContext().close();
          }
        catch(Exception e)
         {
          e.printStackTrace();
         }
    }
}/*@lineinfo:generated-code*/