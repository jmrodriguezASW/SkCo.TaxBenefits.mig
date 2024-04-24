
// Copyright (c) 2000 SKANDIA
package TBPKT_UTILIDADES.TBPKT_CONSULTA;/**Paquete de la clase que se encarga de ejecutar las declaraciones*/

import java.sql.*;
import java.net.URL;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;
/*
 * Modificado 2009/04/01 Por Marcela Ortiz Sandoval
 * Para agregar función que retorna la referencia unica de un contrato
 */



/** 
*Nombre de la clase que se encarga de realizar la conexión con la  basededatos,
*las consultas, los UPDATE, INSERT y la desconexión con la base de  datos
*/
public class TBCL_Consulta extends Object
{
 //Clase que se encarga de conectarme a la base de datos
 private Connection v_conexion;


 /**Realiza la conexión con la base de datos.*/
 public void TBPL_RealizarConexion()
 {
  try {
            if(v_conexion == null || v_conexion.isClosed()){
                try
                {
                 v_conexion         =   DataSourceWrapper.getInstance().getConnection();
                 //  conexion=DriverManager.getConnection("jdbc:oracle:thin:@10.42.1.200:1521:orc1","tbenefit","taxb00");
                }
                catch ( SQLException sqlex )
                {
                 System.err.println("");// "Conexion no se realizo con exito"
                 sqlex.printStackTrace();
                }    
            }   
        } 
  catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
  
 }

 /**Retorna el resultado de la consulta
 * Metodo que se encarga de guardar las consultas en un vector
 * consultaSQL es el statement en SQL de la consulta
 * campo Es el campo de donde se va a sacar la informacion */
 public Vector TBFL_Consulta(String v_consultaSQL, String v_campo)
 {
  Statement v_ordenSQL;
  ResultSet v_resultadoconsulta;
  Vector v_filas = new Vector();
  try
  {
   v_ordenSQL            = v_conexion.createStatement();
   v_resultadoconsulta   = v_ordenSQL.executeQuery(v_consultaSQL);
   boolean v_moreRecords = v_resultadoconsulta.next();
   // Si no hay datos se deviuelve un mensaje
   if ( ! v_moreRecords )
   {
    v_filas.addElement(new String("No hay elementos"));
    return v_filas;
   }
   do
   {
    if (v_resultadoconsulta.getString(v_campo)==null)
     v_filas.addElement( new String("null") );
    else
     v_filas.addElement(v_resultadoconsulta.getString(v_campo));
   } while ( v_resultadoconsulta.next() );
   v_resultadoconsulta.close();
   v_ordenSQL.close();
  }
  catch (SQLException e)
  {
   v_filas.clear();
   if (e.toString().startsWith("ORA-01756",e.toString().indexOf("ORA")))
    v_filas.addElement("Caracter invalido");
   else if (e.toString().startsWith("ORA-01841",e.toString().indexOf("ORA")))
         v_filas.addElement("Fecha Incorrecta");
        else
         v_filas.addElement("Hubo algun  error al realizar su transacción, contacte a su administrador ORACLE<BR>&nbsp;&nbsp;<BR>"+e);
  }
  return v_filas;
 }
 
  /**Retorna el resultado de la consulta
 * Metodo que se encarga de guardar las consultas en un vector
 * consultaSQL es el statement en SQL de la consulta
 * campo Es el campo de donde se va a sacar la informacion */
 public Vector TBFL_ConsultaParametros(String v_consultaSQL, String[]  parametros, String v_campo)
 {
  //Statement v_ordenSQL;
  PreparedStatement pstmt;
  ResultSet v_resultadoconsulta;
  Vector v_filas = new Vector();

  try
  {
   //v_ordenSQL            = v_conexion.createStatement();
   pstmt = v_conexion.prepareStatement( v_consultaSQL );  
   for (int i=0; i< parametros.length; i++){
        pstmt.setString( i+1, parametros[i]);

   }
   v_resultadoconsulta   = pstmt.executeQuery();
   boolean v_moreRecords = v_resultadoconsulta.next();
   // Si no hay datos se deviuelve un mensaje
   if ( ! v_moreRecords )
   {
    v_filas.addElement(new String("No hay elementos"));
    return v_filas;
   }
   do
   {
    if (v_resultadoconsulta.getString(v_campo)==null)
     v_filas.addElement( new String("null") );
    else
     v_filas.addElement(v_resultadoconsulta.getString(v_campo));
   } while ( v_resultadoconsulta.next() );
   v_resultadoconsulta.close();
   pstmt.close();
  }
  catch (SQLException e)
  {
   v_filas.clear();
   if (e.toString().startsWith("ORA-01756",e.toString().indexOf("ORA")))
    v_filas.addElement("Caracter invalido");
   else if (e.toString().startsWith("ORA-01841",e.toString().indexOf("ORA")))
         v_filas.addElement("Fecha Incorrecta");
        else
         v_filas.addElement("Hubo algun  error al realizar su transacción, contacte a su administrador ORACLE<BR>&nbsp;&nbsp;<BR>"+e);
  }
  return v_filas;
 }

 /**
 *Metodo que se encarga de confirmar la eliminacion o adicion de@
 * una condición de penalizacion de un producto*/
 public Vector TBFL_Consulta(String v_consultaSQL, boolean v_adel)
 {
  PreparedStatement v_ordenSQL;
  Vector v_filas = new Vector();

  try
  {
   v_ordenSQL = v_conexion.prepareStatement(v_consultaSQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
   v_ordenSQL.clearParameters();
   v_ordenSQL.executeUpdate();
   v_ordenSQL.close();

   if (v_adel)
    v_filas.addElement(new String("La adición o modificación se ha realizado con éxito"));
   else
    v_filas.addElement(new String("La eliminación se ha realizado con éxito"));

   }
   catch (SQLException e)
       {
       v_filas.clear();
       if (e.toString().startsWith("ORA-00001",e.toString().indexOf("ORA")))
         v_filas.addElement("No puede adicionar un registro ya existente");
       else if (e.toString().startsWith("ORA-02292",e.toString().indexOf("ORA")))
         v_filas.addElement("No puede eliminar un registro con información asociada");
       else if (e.toString().startsWith("ORA-01401",e.toString().indexOf("ORA")))
         v_filas.addElement("No puede insertar un registro demasiado largo");
       else if (e.toString().startsWith("ORA-00907",e.toString().indexOf("ORA")))
         v_filas.addElement("El caracter <B>'</B> (comilla sencilla) no es valido");
       else if (e.toString().startsWith("ORA-00911",e.toString().indexOf("ORA")))
         v_filas.addElement("Esta digitando un caracter no valido");
       else if (e.toString().startsWith("ORA-01843",e.toString().indexOf("ORA")))
         v_filas.addElement("Fecha Incorrecta");
       else if (e.toString().startsWith("Non supported SQL92",e.toString().indexOf("Non")))
         v_filas.addElement("Esta digitando un caracter no valido");
       else if (e.toString().startsWith("ORA-01756",e.toString().indexOf("ORA")))
         v_filas.addElement("El caracter <B>'</B> (comilla sencilla) no es valido");
       else if (e.toString().startsWith("ORA-01841",e.toString().indexOf("ORA")))
         v_filas.addElement("Fecha Incorrecta");
       else
         v_filas.addElement("Hubo algun  error al realizar su transacción, contacte a su administrador ORACLE<BR>&nbsp;&nbsp;<BR>"+e);
       }
  return v_filas;
  }



  /*
  Metodo que se encarga de guardar las consultas en un vector
  consultaSQL es el statement en SQL de la consulta
  campo Es el campo de donde se va a sacar la informacion
  */
 public Vector TBFL_Consulta(String v_consultaSQL)
 {
  Statement v_ordenSQL;
  ResultSet v_resultadoconsulta;
  ResultSetMetaData v_rsmd;
  Vector v_filas = new Vector();
  try
  {
   v_ordenSQL = v_conexion.createStatement();
   v_resultadoconsulta = v_ordenSQL.executeQuery(v_consultaSQL);
   v_rsmd = v_resultadoconsulta.getMetaData();
   boolean v_moreRecords = v_resultadoconsulta.next();

   // Si no hay datos se deviuelve un mensaje
   if ( ! v_moreRecords )
   {

    v_filas.addElement(new String("No hay elementos"));
    return v_filas;
   }

   do{
     for ( int i = 1; i <= v_rsmd.getColumnCount(); ++i )
          {
            if (v_resultadoconsulta.getString( i )==null)
            {
              v_filas.addElement( new String("null") );
            }
            else
            {
              v_filas.addElement( v_resultadoconsulta.getString( i ) );
            }
          }
         }while ( v_resultadoconsulta.next() );
       v_resultadoconsulta.close();
       v_ordenSQL.close();
       }
    catch (SQLException e)
       {
       v_filas.clear();
       if (e.toString().startsWith("ORA-01756",e.toString().indexOf("ORA")))
         v_filas.addElement("Caracter invalido");
       else if (e.toString().startsWith("ORA-01841",e.toString().indexOf("ORA")))
         v_filas.addElement("Fecha Incorrecta");
       else
         v_filas.addElement("Hubo algun  error al realizar su transacción, contacte a su administrador ORACLE<BR>&nbsp;&nbsp;<BR>"+e);
       }
  return v_filas;
  }

/*
  Metodo que se encarga de guardar las consultas en un vector
  consultaSQL es el statement en SQL de la consulta
  campo Es el campo de donde se va a sacar la informacion
  */
 public Vector TBFL_ConsultaParametros(String v_consultaSQL, String[]  parametros)
 {
  //Statement v_ordenSQL;
  ResultSet v_resultadoconsulta;
  ResultSetMetaData v_rsmd;
  Vector v_filas = new Vector();
  try
  {
   PreparedStatement pstmt = v_conexion.prepareStatement( v_consultaSQL );  
   for (int i=0; i< parametros.length; i++){
        pstmt.setString( i+1, parametros[i]);

   }
   
   
   v_resultadoconsulta = pstmt.executeQuery();
   v_rsmd = v_resultadoconsulta.getMetaData();
   boolean v_moreRecords = v_resultadoconsulta.next();

   // Si no hay datos se deviuelve un mensaje
   if ( ! v_moreRecords )
   {

    v_filas.addElement(new String("No hay elementos"));
    return v_filas;
   }

   do{
     for ( int i = 1; i <= v_rsmd.getColumnCount(); ++i )
          {
            if (v_resultadoconsulta.getString( i )==null)
            {
              v_filas.addElement( new String("null") );
            }
            else
            {
              v_filas.addElement( v_resultadoconsulta.getString( i ) );
            }
          }
         }while ( v_resultadoconsulta.next() );
       v_resultadoconsulta.close();
       pstmt.close();
       }
    catch (SQLException e)
       {
       v_filas.clear();
       if (e.toString().startsWith("ORA-01756",e.toString().indexOf("ORA")))
         v_filas.addElement("Caracter invalido");
       else if (e.toString().startsWith("ORA-01841",e.toString().indexOf("ORA")))
         v_filas.addElement("Fecha Incorrecta");
       else
         v_filas.addElement("Hubo algun  error al realizar su transacción, contacte a su administrador ORACLE<BR>&nbsp;&nbsp;<BR>"+e);
       }
  return v_filas;
  }



  //Metodo que me retorna si un contrato esta TBFL_Penalizado o No esta TBFL_Penalizado
  public int TBFL_Penalizado(String v_contrato, String v_producto, int v_consecutivo)
  {
  int v_porcentaje = 0;
  try
     {
     CallableStatement v_clsm = v_conexion.prepareCall("{? = call TB_FFILTRO_PENALIZACION(?,?,?)}");
     v_clsm.registerOutParameter(1,Types.INTEGER);
     v_clsm.setString(2,v_contrato);
     v_clsm.setString(3,v_producto);
     v_clsm.setInt(4,v_consecutivo);
     v_clsm.execute();
     v_porcentaje = v_clsm.getInt(1);
     v_clsm.close();
     }
  catch (Exception e)
     {
     System.out.println("");
     }
  return v_porcentaje;
  }


  //Metodo que me retorna el rendimiento total del contrato
  public double[] TBFL_Rendimientos(String v_producto, String v_contrato, double v_ValorUnidad)
  {
  double v_Rendimientos[]  = new double [3];
  try
     {
     CallableStatement v_clsm = v_conexion.prepareCall("{call TBPBD_SALDOContrato(?,?,?,?,?,?,?,?,?,?,?,?)}");
     v_clsm.setString(1,v_producto);
     v_clsm.setString(2,v_contrato);
     v_clsm.setDouble(3,v_ValorUnidad);
     v_clsm.registerOutParameter(4,Types.DOUBLE);
     v_clsm.registerOutParameter(5,Types.DOUBLE);
     v_clsm.registerOutParameter(6,Types.DOUBLE);
     v_clsm.registerOutParameter(7,Types.DOUBLE);
     v_clsm.registerOutParameter(8,Types.DOUBLE);
     v_clsm.registerOutParameter(9,Types.DOUBLE);
     v_clsm.registerOutParameter(10,Types.DOUBLE);
     v_clsm.registerOutParameter(11,Types.DOUBLE);
     v_clsm.registerOutParameter(12,Types.VARCHAR);
     v_clsm.execute();
     v_Rendimientos[0] = v_clsm.getDouble(4);
     v_Rendimientos[1] = v_clsm.getDouble(11);
     v_Rendimientos[2] = v_clsm.getDouble(6);
     v_clsm.close();

     }
  catch (Exception e)
     {
     System.out.println("");
     }
  return v_Rendimientos;
  }

  public double[] TBFL_ConsultaSaldos(String v_producto, String v_contrato, double v_ValorUnidad,String v_indicador,String v_fecha)
  {
  double v_Rendimientos[]  = new double [9];
  try
     {
     CallableStatement v_clsm = v_conexion.prepareCall("{? = call TBF_ConsultaImpuestos(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
     v_clsm.setString(2,v_producto);
     v_clsm.setString(3,v_contrato);
     v_clsm.setString(4,v_fecha);
     v_clsm.setString(5,v_indicador);
     v_clsm.setDouble(6,v_ValorUnidad);
     v_clsm.registerOutParameter(1,Types.DOUBLE);
     v_clsm.registerOutParameter(7,Types.DOUBLE);
     v_clsm.registerOutParameter(8,Types.DOUBLE);
     v_clsm.registerOutParameter(9,Types.DOUBLE);
     v_clsm.registerOutParameter(10,Types.DOUBLE);
     v_clsm.registerOutParameter(11,Types.DOUBLE);
     v_clsm.registerOutParameter(12,Types.DOUBLE);
     v_clsm.registerOutParameter(13,Types.DOUBLE);
     v_clsm.registerOutParameter(14,Types.VARCHAR);
     v_clsm.execute();
     v_Rendimientos[0] = v_clsm.getDouble(1);
     v_Rendimientos[1] = v_clsm.getDouble(7);//v_salcap_contrato
     v_Rendimientos[2] = v_clsm.getDouble(8);//v_salrencontrato
     v_Rendimientos[3] = v_clsm.getDouble(9);//v_salcc_contrato
     v_Rendimientos[4] = v_clsm.getDouble(10);//vsalcapori
     v_Rendimientos[5] = v_clsm.getDouble(11);//v_capret
     v_Rendimientos[6] = v_clsm.getDouble(12);//vv_renret
     v_Rendimientos[7] = v_clsm.getDouble(13);//v_cod_err
     v_clsm.close();

     }
  catch (Exception e)
     {
     System.out.println("");
     }
  return v_Rendimientos;
  }

  /*
   * TBFL_ConsultaRefUnica
   * Retorna la referencia unica de un contrato compuesta por 12 digitos 
   * @Parameters
   * v_producto String Producto al que pertenece el contrato 
   * v_contrato String Contrato de 9 digitos
   */
  public String  TBFL_ConsultaRefUnica(String v_producto, String v_contrato)
  {
  String contra_ref_unica  = "";
  try
     {
     CallableStatement v_clsm = v_conexion.prepareCall("{? = call TBFBD_obtener_ref_unica(?,?)}");
     v_clsm.registerOutParameter(1,Types.VARCHAR);
     v_clsm.setString(2,v_producto);
     v_clsm.setString(3,v_contrato);
     v_clsm.execute();
     contra_ref_unica = v_clsm.getString(1);
     v_clsm.close();
          
     }
  catch (Exception e)
     {
     System.out.println("");
     }
  return contra_ref_unica;
  }
  
    /*
   * TBFL_ConsultaSinRefUnica
   * Retorna el número del contrato compuesto por 9 digitos 
   * @Parameters
   * v_contrato String Contrato de 9 digitos
   */
  public String  TBFL_ConsultaSinRefUnica(String v_contrato)
  {
  String v_contra = "";
  try
     {
       if (v_contrato.length() ==9) v_contra= v_contrato;
       if (v_contrato.length()==12)  v_contra = v_contrato.substring(3,11);
     }
  catch (Exception e)
     {
     System.out.println("");
     }
  return v_contra;
  }

  
   //Desconexion a la base de datos
   public void TBPL_shutDown()
   { 
   try {
          DataSourceWrapper.closeConnection(v_conexion);
      }
      catch ( SQLException sqlex ) {
	 System.err.println( " " );//No se puede desconectar
	 sqlex.printStackTrace();
      }
   }
   
   protected void finalize(){
       TBPL_shutDown();
   }
   
   

}

