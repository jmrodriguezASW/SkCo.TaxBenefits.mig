package TBPKT_INFORMATIVO.TBPKT_MEDIOS_MAGNETICOS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.math.*;
import java.text.*;
import oracle.jdbc.driver.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_RTF.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

/**
 * Servlet para procesar las llamadas al M&oacute;dulo de Medios Magn&eacute;neticos
 * <P>
 * @author ALFA GL Ltda
 */
public class TBCS_Medios_Magneticos extends HttpServlet {

  /**
   * Initialize global variables
   */
  String cadena = "";
  String nuevaCadena = "";
  String rutaVirtual = "";
  String rutaFisica = "";
   
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   * Process the HTTP Post request
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
    //response.setHeader("Pragma", "No-Cache");
    //response.setDateHeader("Expires", 0);
    String parametros[] = new String[8];
    String  cadena = request.getParameter("cadena");
    nuevaCadena = cadena;
    String ip_tax = request.getRemoteAddr();
    TBCL_Seguridad Seguridad = new TBCL_Seguridad();
    PrintWriter out = new PrintWriter (response.getOutputStream());
    parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);

    String strTipo= request.getParameter("tipo");
    if(strTipo == null) strTipo= "3";
    int tipo= Integer.parseInt(strTipo);

    String archivo= request.getParameter("archivo");
    if(archivo == null) archivo= "DIAN";
    archivo+= ".txt";


    String medio= request.getParameter("medio");
    if(medio == null) medio= "ABCD";
    String[] params= TBFL_GeneraParams(tipo, request);

    out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Reporte de Medios Magn&eacute;ticos","Confirmaci&oacute;n de Reportes de Medios Magn&eacute;ticos", "", "", false));
    out.println("<TABLE BGCOLOR=\"WHITE\" BORDER=0 CELLPADDING=2 CELLSPACING=0 COLS=2 RULES=\"ALL\" WIDTH=100%>");

    params= TBFL_GeneraDocumento(tipo, medio, archivo, params);
    if(params != null)
    {
      out.print("<TR><TD WIDTH=35%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.print("Informaci&oacute;n del medio :</FONT></TD>");
      out.println("<TD WIDTH=65%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>"+params[0]+"</FONT></TD></TR>");

      out.print("<TR><TD WIDTH=35%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.print("A&ntilde;o gravable :</FONT></TD>");
      out.println("<TD WIDTH=65%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>"+params[1]+"</FONT></TD></TR>");

      out.println("<TR><TD COLSPAN=2>&nbsp;</TD></TR>");

      out.print("<TR><TD WIDTH=35%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.print("Archivo generado :</FONT></TD>");
      out.print("<TD WIDTH=65%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.print("<A HREF=\""+params[2]+"\">"+params[3]+"</A>");
      out.println("</FONT></TD></TR>");

      out.println("<TR><TD COLSPAN=2>&nbsp;</TD></TR>");

      out.println("<TR><TD COLSPAN=2><CENTER><INPUT TYPE=\"BUTTON\" VALUE=\"Aceptar\" OnClick='history.go(-1);'></CENTER></TD></TR>");
    }
    else {
      out.print("<TR><TD COLSPAN=2><CENTER><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.print("No se encontraron registros</FONT></CENTER></TD></TR>");

      out.println("<TR><TD COLSPAN=2>&nbsp;</TD></TR>");
      out.println("<TR><TD COLSPAN=2><CENTER><INPUT TYPE=\"BUTTON\" VALUE=\"Aceptar\" OnClick='history.go(-1);'></CENTER></TD></TR>");
    }

    out.println("</TABLE>");
    out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
    out.close();
  }

  /**
   * Genera el arreglo de par&aacute;metros a partir del tipo
   * @param tipo Tipo de consulta.
   * @param request Objeto que recibe el servlet.
   * @return java.lang.String[].
   */
  private String[] TBFL_GeneraParams(int tipo, HttpServletRequest request) {
    String anio_gravable, monto_referencia;

    anio_gravable= request.getParameter("anio_gravable");
    if(anio_gravable == null) anio_gravable= "2000";
    monto_referencia= request.getParameter("monto_referencia");
    if(monto_referencia == null) monto_referencia= "120000000";


    if(tipo == 1)
      return new String[]{anio_gravable,anio_gravable,anio_gravable,anio_gravable,anio_gravable,monto_referencia};
    else if(tipo == 2)
      return new String[]{anio_gravable,anio_gravable,monto_referencia};
    else
      return new String[]{anio_gravable,anio_gravable,anio_gravable,anio_gravable,anio_gravable,anio_gravable,anio_gravable,monto_referencia};
  }

  /**
   * Genera el archivo de Medios Magneticos
   * @return boolean True si el archivo pudo generarse. False en otro caso.
   */
  private String[] TBFL_GeneraDocumento(int tipo, String idMedio, String strArchivo, String[] params) {
    if(tipo == 1 || tipo == 2) {
      return TBFL_GeneraDebitosCreditos(tipo, idMedio, strArchivo, params);
    }
    else {
      return TBFL_GeneraContratos(idMedio, strArchivo, params);
    }
  }

  /**
   * Genera el archivo de D&eacute;bitos y Cr&eacute;ditos
   * @return boolean True si el archivo pudo generarse. False en otro caso.
   */
  private String[] TBFL_GeneraDebitosCreditos(int tipo, String idMedio, String strArchivo, String[] params) {
    String[] retArray= null;

    String nit_afpOrigen, digito_verificador, razon_social;
    String documento, numero_cuenta, ubicacion_contrato, nombre;
    String oldNit_afpOrigen= "", oldDigito_verificador= "", oldRazon_social= "";
    String registro2= "", registro1= "";
    String valor_total, hoy;
    String anio_gravable= params[0];

    double nValor_total= 0.0, tValor= 0.0;
    int tRegistros= 0;
    boolean printRegistro1, hayRegistros= false;

    String[] datosRegistro;
    Iterator registros;
    File archivo= null;

    try {
      TBPL_GetRutas();
      java.util.Date fHoy= new java.util.Date();
      String anio= ""+ (fHoy.getYear()+1900);
      String mes= "00"+ (fHoy.getMonth()+1);
      String dia= "00"+ fHoy.getDate();
      hoy= anio+ mes.substring(mes.length()-2)+ dia.substring(dia.length()-2);

      String arTmp= rutaFisica+ strArchivo.substring(0, strArchivo.length()-1);

      archivo= new File(arTmp);
      PrintWriter fileOut= new PrintWriter(new BufferedWriter(new FileWriter(archivo)));

      registros= TBFL_GetRegistros(tipo, params).iterator();

      while(registros.hasNext()) {
        hayRegistros= true;
        datosRegistro= (String[])registros.next();

        nit_afpOrigen= datosRegistro[0];
        digito_verificador= datosRegistro[1];
        razon_social= datosRegistro[2];

        printRegistro1= !(oldNit_afpOrigen.equals(nit_afpOrigen) || oldNit_afpOrigen.equals(""));

        if(printRegistro1) {
          registro1= "1"+ anio_gravable+ "10"+ oldNit_afpOrigen+ oldDigito_verificador+ " "+ oldRazon_social+
                     fill(""+tRegistros,'0',10,true)+ formatNumber(tValor,20)+ hoy+ idMedio+ "     ";
          fileOut.println(registro1);
          tRegistros= 0;
          tValor= 0.0;
        }

        oldNit_afpOrigen= nit_afpOrigen;
        oldDigito_verificador= digito_verificador;
        oldRazon_social= razon_social;

        documento= datosRegistro[3];
        nombre= datosRegistro[4];
        numero_cuenta= datosRegistro[5];
        valor_total= datosRegistro[6];

        nValor_total= Double.parseDouble(valor_total);
        tValor+= nValor_total;
        tRegistros++;

        valor_total= formatNumber(nValor_total, 20);

        registro2= "2"+ "10"+ "00"+ documento+ "  "+ nombre+ valor_total+ numero_cuenta+ " "+ "11001"+ "   ";
        fileOut.println(registro2);
      }
      if(hayRegistros) {

        registro1= "1"+ anio_gravable+ "10"+ oldNit_afpOrigen+ oldDigito_verificador+ " "+ oldRazon_social+
                   fill(""+tRegistros,'0',10,true)+ formatNumber(tValor,20)+ hoy+ idMedio+ "     ";
        fileOut.print(registro1);

        String link= strArchivo;
        String href= rutaVirtual + link;

        retArray= new String[]{idMedio, anio_gravable, href, link};

      }

      fileOut.close();
      (new TBCL_ManageFile()).TBPL_inv(arTmp, rutaFisica + strArchivo);
      archivo.delete();
    }
    catch(SQLException sqle) {
      sqle.printStackTrace(System.out);
    }
    catch(IOException ioe) {
      ioe.printStackTrace(System.out);
    }

    return retArray;
  }

  /**
   * Genera el archivo de Contratos
   * @return boolean True si el archivo pudo generarse. False en otro caso.
   */
  private String[] TBFL_GeneraContratos(String idMedio, String strArchivo, String[] params) {
    String[] retArray= null;

    String nit_afpOrigen, digito_verificador, razon_social;
    String documento, numero_cuenta, ubicacion_contrato, nombre;
    String oldNit_afpOrigen= "", oldDigito_verificador= "", oldRazon_social= "";
    String registro2= "", registro1= "";
    String estado_contrato, valor_acumulado, hoy;
    String anio_gravable= params[0];

    double nValor_acumulado= 0.0, tValor= 0.0;
    int tRegistros= 0;
    boolean printRegistro1, hayRegistros= false;

    LinkedList lRegs;
    String[] datosRegistro;
    Iterator registros;
    File archivo= null;
    PrintWriter fileOut= null;

    try {
      TBPL_GetRutas();
      java.util.Date fHoy= new java.util.Date();
      String anio= ""+ (fHoy.getYear()+1900);
      String mes= "00"+ (fHoy.getMonth()+1);
      String dia= "00"+ fHoy.getDate();
      hoy= anio+ mes.substring(mes.length()-2)+ dia.substring(dia.length()-2);

      String arTmp= rutaFisica + strArchivo.substring(0, strArchivo.length()-1);

      archivo= new File(arTmp);
      fileOut= new PrintWriter(new BufferedWriter(new FileWriter(archivo)));

      lRegs= TBFL_GetRegistros(3, params);
      String[] params2= new String[]{params[0],params[0],params[0],params[0],params[params.length-1]};
      lRegs.addAll(TBFL_GetRegistros(4, params2));

      registros= lRegs.iterator();

      while(registros.hasNext()) {
        hayRegistros= true;
        datosRegistro= (String[])registros.next();

        nit_afpOrigen= datosRegistro[0];
        digito_verificador= datosRegistro[1];
        razon_social= datosRegistro[2];

        printRegistro1= !(oldNit_afpOrigen.equals(nit_afpOrigen) || oldNit_afpOrigen.equals(""));

        if(printRegistro1) {
          registro1= "1"+ anio_gravable+ "10"+ oldNit_afpOrigen+ oldDigito_verificador+ " "+ oldRazon_social+
                     fill(""+tRegistros,'0',10,true)+ formatNumber(tValor,20)+ hoy+ idMedio+ "     ";
          fileOut.println(registro1);
          tRegistros= 0;
          tValor= 0.0;
        }

        oldNit_afpOrigen= nit_afpOrigen;
        oldDigito_verificador= digito_verificador;
        oldRazon_social= razon_social;

        documento= datosRegistro[3];
        nombre= datosRegistro[4];
        numero_cuenta= datosRegistro[5];
        estado_contrato= datosRegistro[6];
        valor_acumulado= datosRegistro[7];

        nValor_acumulado= Double.parseDouble(valor_acumulado);
        tValor+= nValor_acumulado;
        tRegistros++;

        valor_acumulado= formatNumber(nValor_acumulado, 20);

        registro2= "2"+ "10"+ estado_contrato+ documento+ "  "+ nombre+ valor_acumulado+ numero_cuenta+ "11001"+ "    ";
        fileOut.println(registro2);
      }
      if(hayRegistros) {

        registro1= "1"+ anio_gravable+ "10"+ oldNit_afpOrigen+ oldDigito_verificador+ " "+ oldRazon_social+
                   fill(""+tRegistros,'0',10,true)+ formatNumber(tValor,20)+ hoy+ idMedio+ "     ";
        fileOut.print(registro1);

        String link= strArchivo;
        String href= rutaVirtual + link;

        retArray= new String[]{idMedio, anio_gravable, href, link};

      }

      fileOut.close();
      fileOut= null;
      (new TBCL_ManageFile()).TBPL_inv(arTmp, rutaFisica + strArchivo);
    }
    catch(SQLException sqle) {
      sqle.printStackTrace(System.out);
    }
    catch(IOException ioe) {
      ioe.printStackTrace(System.out);
    }
    finally {
      if(archivo!= null) archivo.delete();
      if(fileOut!= null) fileOut.close();
    }

    return retArray;
  }

  /**
   * Regresa una tabla de los datos definidos por los par&aacute;metros
   * @param tipo Tipo de consulta que quiere realizarse.
   * @param params par&aacute;metros de la consulta.
   * @return java.util.LinkedList
   */
  private LinkedList TBFL_GetRegistros(int tipo, String[] params) throws SQLException {
    String query= "";

    switch(tipo) {
      case 1: //Débitos.
        query= "SELECT * "+
               "FROM   ( "+
               "SELECT LPAD(REPLACE(pro_numero_identificacion,'.'),14,'0') NIT "+
               "      ,SUBSTR(pro_digito_verificacion,1,2) V "+
               "      ,RPAD(pro_descripcion,60) RAZON_SOCIAL "+
               "      ,LPAD(REPLACE(con_numero_identificacion,'.'),14,'0') DOCUMENTO "+
               "      ,RPAD((RTRIM(con_apellidos) || ' ' || RTRIM(con_nombres)),60) APELLIDOS_NOMBRES "+
               "      ,LPAD(con_numero,20,'0') NO_CUENTA "+
               "      ,ROUND(NVL(SUM(K),0),-3) VALOR_TOTAL "+
               "FROM  ( "+
               "       SELECT pro_numero_identificacion "+
               "             ,pro_digito_verificacion "+
               "             ,pro_descripcion "+
               "             ,con_numero "+
               "             ,con_numero_identificacion "+
               "             ,con_apellidos "+
               "             ,con_nombres "+
               "             ,GREATEST((NVL(dAct.dia_porcentaje_disponibilidad,0) - NVL(dAnt.dia_porcentaje_disponibilidad,0)),0)/100*apo_capital K "+
               "       FROM   tbdisponibilidades_aportes dAct "+
               "             ,tbdisponibilidades_aportes dAnt "+
               "             ,tbaportes "+
               "             ,tbcontratos "+
               "             ,tbproductos "+
               "       WHERE  dAct.dia_apo_con_pro_codigo (+) = apo_con_pro_codigo "+
               "         AND  dAct.dia_apo_con_numero     (+) = apo_con_numero "+
               "         AND  dAct.dia_apo_consecutivo    (+) = apo_consecutivo "+
               "         AND  dAnt.dia_apo_con_pro_codigo (+) = apo_con_pro_codigo "+
               "         AND  dAnt.dia_apo_con_numero     (+) = apo_con_numero "+
               "         AND  dAnt.dia_apo_consecutivo    (+) = apo_consecutivo "+
               "         AND  apo_con_pro_codigo              = con_pro_codigo "+
               "         AND  apo_con_numero                  = con_numero "+
               "         AND  con_pro_codigo                  = pro_codigo "+
               "         AND  apo_ref_estado                  = 'SEA001' "+
               "         AND  apo_apo_consecutivo            IS NULL "+
               "         AND  apo_fecha_efectiva BETWEEN TO_DATE(? || '-01-01', 'rrrr-mm-dd') AND TO_DATE(? || '-12-31', 'rrrr-mm-dd') "+
               "         AND  dAct.dia_fecha (+) BETWEEN TO_DATE(? || '-01-01', 'rrrr-mm-dd') AND TO_DATE(? || '-12-31', 'rrrr-mm-dd') "+
               "         AND  dAnt.dia_fecha (+) < TO_DATE(? || '-01-01',  'rrrr-mm-dd') "+
               "       ORDER BY dAct.dia_fecha desc "+
               "               ,dAnt.dia_fecha desc "+
               "      ) "+
               "GROUP BY pro_numero_identificacion "+
               "        ,pro_digito_verificacion "+
               "        ,pro_descripcion "+
               "        ,con_numero "+
               "        ,con_numero_identificacion "+
               "        ,con_apellidos "+
               "        ,con_nombres "+
               ") "+
               "WHERE  VALOR_TOTAL > ?";
        break;
      case 2: //Créditos.
        query= "SELECT * "+
               "FROM   ( "+
               "        SELECT LPAD(REPLACE(pro_numero_identificacion,'.'),14,'0') NIT "+
               "              ,SUBSTR(pro_digito_verificacion,1,2) V "+
               "              ,RPAD(pro_descripcion,60) RAZON_SOCIAL "+
               "              ,LPAD(REPLACE(con_numero_identificacion,'.'),14,'0') DOCUMENTO "+
               "              ,RPAD((RTRIM(con_apellidos) || ' ' || RTRIM(con_nombres)),60) APELLIDOS_NOMBRES "+
               "              ,LPAD(con_numero,20,'0') NO_CUENTA "+
               "              ,ROUND(NVL(SUM(ret_valor_bruto),0),-3) VALOR_TOTAL "+
               "        FROM   tbproductos "+
               "              ,tbcontratos "+
               "              ,tbretiros "+
               "        WHERE  pro_codigo                        = con_pro_codigo "+
               "          AND  con_pro_codigo                    = ret_con_pro_codigo "+
               "          AND  con_numero                        = ret_con_numero "+
               "          AND  ret_ref_estado                    = 'SER006' "+
               "          AND  ret_fecha_efectiva BETWEEN TO_DATE(? || '-01-01',  'rrrr-mm-dd') AND TO_DATE(? || '-12-31',  'rrrr-mm-dd') "+
               "        GROUP BY  pro_numero_identificacion "+
               "                 ,pro_digito_verificacion "+
               "                 ,pro_descripcion "+
               "                 ,con_numero_identificacion "+
               "                 ,con_apellidos "+
               "                 ,con_nombres "+
               "                 ,con_numero "+
               "        ORDER BY  pro_numero_identificacion "+
               ") "+
               "WHERE  VALOR_TOTAL > ?";
        break;
      case 3: //Contratos Abiertos
        query= "SELECT * "+
               "FROM   ( "+
               "SELECT LPAD(REPLACE(pro_numero_identificacion,'.'),14,'0') NIT "+
               "      ,SUBSTR(pro_digito_verificacion,1,2) V "+
               "      ,RPAD(pro_descripcion,60) RAZON_SOCIAL "+
               "      ,LPAD(REPLACE(con_numero_identificacion,'.'),14,'0') DOCUMENTO "+
               "      ,RPAD((RTRIM(con_apellidos) || ' ' || RTRIM(con_nombres)),60) APELLIDOS_NOMBRES "+
               "      ,LPAD(con_numero,20,'0') NO_CUENTA "+
               "      ,'11' "+
               "      ,ROUND(NVL(SUM(K),0),-3) VALOR_TOTAL "+
               "FROM  ( "+
               "       SELECT pro_numero_identificacion "+
               "             ,pro_digito_verificacion "+
               "             ,pro_descripcion "+
               "             ,con_numero "+
               "             ,con_numero_identificacion "+
               "             ,con_apellidos "+
               "             ,con_nombres "+
               "             ,GREATEST((NVL(dAct.dia_porcentaje_disponibilidad,0) - NVL(dAnt.dia_porcentaje_disponibilidad,0)),0)/100*apo_capital K "+
               "       FROM   tbdisponibilidades_aportes dAct "+
               "             ,tbdisponibilidades_aportes dAnt "+
               "             ,tbaportes "+
               "             ,tbcontratos "+
               "             ,tbproductos "+
               "       WHERE  dAct.dia_apo_con_pro_codigo (+) = apo_con_pro_codigo "+
               "         AND  dAct.dia_apo_con_numero     (+) = apo_con_numero "+
               "         AND  dAct.dia_apo_consecutivo    (+) = apo_consecutivo "+
               "         AND  dAnt.dia_apo_con_pro_codigo (+) = apo_con_pro_codigo "+
               "         AND  dAnt.dia_apo_con_numero     (+) = apo_con_numero "+
               "         AND  dAnt.dia_apo_consecutivo    (+) = apo_consecutivo "+
               "         AND  apo_con_pro_codigo              = con_pro_codigo "+
               "         AND  apo_con_numero                  = con_numero "+
               "         AND  con_pro_codigo                  = pro_codigo "+
               "         AND  apo_ref_estado                  = 'SEA001' "+
               "         AND  apo_apo_consecutivo            IS NULL "+
               "         AND  apo_fecha_efectiva BETWEEN TO_DATE(? || '-01-01', 'rrrr-mm-dd') AND TO_DATE(? || '-12-31', 'rrrr-mm-dd') "+
               "         AND  con_fecha_apertura BETWEEN TO_DATE(? || '-01-01',  'rrrr-mm-dd') AND TO_DATE(? || '-12-31',  'rrrr-mm-dd') "+
               "         AND  dAct.dia_fecha (+) BETWEEN TO_DATE(? || '-01-01', 'rrrr-mm-dd') AND TO_DATE(? || '-12-31', 'rrrr-mm-dd') "+
               "         AND  dAnt.dia_fecha (+) < TO_DATE(? || '-01-01',  'rrrr-mm-dd') "+
               "       ORDER BY dAct.dia_fecha desc "+
               "               ,dAnt.dia_fecha desc "+
               "      ) "+
               "GROUP BY pro_numero_identificacion "+
               "        ,pro_digito_verificacion "+
               "        ,pro_descripcion "+
               "        ,con_numero "+
               "        ,con_numero_identificacion "+
               "        ,con_apellidos "+
               "        ,con_nombres "+
               ") "+
               "WHERE  VALOR_TOTAL > ?";
        break;
      case 4: //Contratos Cancelados
        query= "SELECT * "+
               "FROM   ( "+
               "       SELECT LPAD(REPLACE(pro_numero_identificacion,'.'),14,'0') NIT "+
               "             ,SUBSTR(pro_digito_verificacion,1,2) V "+
               "             ,RPAD(pro_descripcion,60) RAZON_SOCIAL "+
               "             ,LPAD(REPLACE(con_numero_identificacion,'.'),14,'0') DOCUMENTO "+
               "             ,RPAD((RTRIM(con_apellidos) || ' ' || RTRIM(con_nombres)),60) APELLIDOS_NOMBRES "+
               "             ,LPAD(con_numero,20,'0') NO_CUENTA "+
               "             ,'13' "+
               "             ,ROUND(NVL(SUM(ret_valor_bruto),0),-3) VALOR_TOTAL "+
               "       FROM   tbproductos "+
               "             ,tbcontratos "+
               "             ,tbretiros "+
               "       WHERE  pro_codigo                        = con_pro_codigo "+
               "         AND  con_pro_codigo                    = ret_con_pro_codigo "+
               "         AND  con_numero                        = ret_con_numero "+
               "         AND  ret_ref_estado                    = 'SER006'  "+
               "         AND  con_fecha_cancelacion BETWEEN TO_DATE(? || '-01-01','rrrr-mm-dd') AND TO_DATE(? || '-12-31','rrrr-mm-dd') "+
               "         AND  ret_fecha_efectiva BETWEEN TO_DATE(? || '-01-01',  'rrrr-mm-dd') AND TO_DATE(? || '-12-31',  'rrrr-mm-dd') "+
               "        GROUP BY  pro_numero_identificacion "+
               "                 ,pro_digito_verificacion "+
               "                 ,pro_descripcion "+
               "                 ,con_numero_identificacion "+
               "                 ,con_apellidos "+
               "                 ,con_nombres "+
               "                 ,con_numero "+
               "       ORDER BY  pro_numero_identificacion "+
               ") "+
               "WHERE  VALOR_TOTAL > ?";
        break;
      default:
        break;
    }
    return TBFL_Consulta(query, params);
  }

  /**
   * Ejecuta una consulta con los par&aacute;metros definidos
   * @param query Consulta que desea hacerse.
   * @param params Par&aacute;metros de la consulta.
   * @return java.util.LinkedList
   */
  private LinkedList TBFL_Consulta(String query, String[] params) throws SQLException {
    LinkedList retList= new LinkedList();

    Connection con= null;
    PreparedStatement ps= null;
    ResultSet rs= null;

    try {
          con   =   DataSourceWrapper.getInstance().getConnection();
          ps= con.prepareStatement(query);
          for(int i= 0; i< params.length; i++) {
            ps.setString(i+1,params[i]);
          }
    
          rs= ps.executeQuery();
          while(rs.next()) {
            int count= rs.getMetaData().getColumnCount();
            String[] record= new String[count];
            for(int i= 0; i< count; i++) {
              record[i]= rs.getString(i+1);
            }
            retList.add(record);
          }
    }
    finally{
      try {
            DataSourceWrapper.closeStatement(rs);
            DataSourceWrapper.closeStatement(ps);
            DataSourceWrapper.closeConnection(con); 
      }
      catch(SQLException sqle) {
      }
    }
    return retList;
  }

  /**
   * Regresa la Descripci&oacute;n de la AFP seleccionada
   * @return java.lang.String
   */
  private String getDescAFP(String afp_codigo) throws SQLException {
    String retString= "";

    Connection con= null;
    PreparedStatement ps= null;
    ResultSet rs= null;

    String query= "SELECT AFP_DESCRIPCION "+
                  "FROM TBADM_FONDOS_PENSIONES "+
                  "WHERE AFP_CODIGO = ?";

    try {
      con   =   DataSourceWrapper.getInstance().getConnection();
      ps= con.prepareStatement(query);
      ps.setString(1, afp_codigo);

      rs= ps.executeQuery();
      if(rs.next()) {
        retString = rs.getString(1);
      }

    }
    finally{
      try {
            DataSourceWrapper.closeStatement(rs);
            DataSourceWrapper.closeStatement(ps);
            DataSourceWrapper.closeConnection(con); 
      }
      catch(SQLException sqle) {
      }
    }
    return (retString==null)?"0":retString;
  }

  /**
   * Regresa la ruta para generar el archivo
   * @return java.lang.String
   */
  private void TBPL_GetRutas() throws SQLException {
    Connection con= null;
    PreparedStatement ps= null;
    ResultSet rs= null;

    try {
     con   =   DataSourceWrapper.getInstance().getConnection();
      String query= "SELECT REF_VALOR "+
                "FROM   TBREFERENCIAS "+
                "WHERE  REF_CODIGO = 'SMM001'";

      ps= con.prepareStatement(query);

      rs= ps.executeQuery();
      if(rs.next()) {
        rutaFisica = rs.getString(1);
      }
      query= "SELECT REF_VALOR "+
                "FROM   TBREFERENCIAS "+
                "WHERE  REF_CODIGO = 'SMM002'";

      ps= con.prepareStatement(query);

      rs= ps.executeQuery();
      if(rs.next()) {
        rutaVirtual = rs.getString(1);
      }

    }
    finally{
      try {
            DataSourceWrapper.closeStatement(rs);
            DataSourceWrapper.closeStatement(ps);
            DataSourceWrapper.closeConnection(con); 
      }
      catch(SQLException sqle) {
      }
    }
  }

  

  /**
   * Inserta el caracter especificado en la cadena dada, hasta llenar las
   * posiciones indicadas.
   * @param cadena La cadena de entrada.
   * @param caracter El caracter con el cual se quiere llenar la cadena.
   * @param longitud La longitud deseada de la cadena de regreso.
   * @param derecha true para justificar a la derecha, false para justificar a la izquierda.
   * @return java.lang.String
   */
  private String fill(String cadena, char caracter, int longitud, boolean derecha) {
    if(cadena == null || cadena.length() >= longitud) return cadena;

    StringBuffer sb= new StringBuffer(cadena);
    for(int i= cadena.length(); i< longitud; i++ ) {
      if(derecha) sb.insert(0,caracter);
      else sb.append(caracter);
    }

    return sb.toString();
  }

  /**
   * Da formato al n&uacute;mero pasado como par&aacute;metro para su inserci&oacute;n en los
   * registros del archivo de salida.
   * @param numero El n&uacute;mero de entrada.
   * @param longitud La longitud deseada de la cadena de regreso.
   * @return java.lang.String
   */
  private String formatNumber(double numero, int longitud) {

    BigDecimal big= new BigDecimal(numero);
    big= big.setScale(0, BigDecimal.ROUND_HALF_UP);

    StringBuffer sb= new StringBuffer();
    StringTokenizer st= new StringTokenizer(big.toString(), ".-");

    while(st.hasMoreTokens()) sb.append(st.nextToken());

    for(int i= sb.length(); i< longitud; i++ )
      sb.insert(0,'0');

    return sb.toString();
  }

    /**
   * Da formato a la cadena pasada como par&aacute;metro para su inserci&oacute;n en los
   * registros del archivo de salida.
   * @param cadeda La cadena de entrada.
   * @param longitud La longitud deseada de la cadena de regreso.
   * @return java.lang.String
   */
  private String formatString(String cadena, int longitud) {
    if(cadena == null) return "                    ";
    if(cadena.length()> longitud) return cadena.substring(0,longitud);

    StringBuffer sb= new StringBuffer(cadena);

    for(int i= sb.length(); i< longitud; i++ )
      sb.append(' ');

    return sb.toString();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_INFORMATIVO.TBPKT_MEDIOS_MAGNETICOS.TBCS_Medios_Magneticos Information";
  }
}

