package TBPKT_INFORMATIVO.TBPKT_REPORTESCONTROL;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.NumberFormat;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_Reporte_NO_Impresion extends HttpServlet{
private PrintWriter out;
private TBCL_Consulta v_Consulta;

  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   * Process the HTTP Get request
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String v_nuevaCadena = "";
    String cadena  = "";
    HttpSession session = request.getSession(true);
    if(session==null)
       session = request.getSession(true);
    session.setMaxInactiveInterval(3600);
    out = new PrintWriter (response.getOutputStream());
    response.setContentType("text/html");
    /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
    //response.setHeader("Pragma", "No-Cache");
    //response.setDateHeader("Expires", 0);

    try
    {
     String parametros[] = new String[8];
     try
     {
      cadena               = (String)session.getAttribute("s_cadena");
     }
     catch (Exception e)
     {
      cadena = "";
     }
     v_nuevaCadena = cadena;
     String ip_tax = request.getRemoteAddr();
     TBCL_Seguridad Seguridad = new TBCL_Seguridad();
     parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
    }
    catch(Exception ex)
    {
     System.out.println("");
    }

    v_Consulta = new TBCL_Consulta();
    String v_dconsulta ="";
    //Vector donde se guardara el resultado de la v_dconsulta
    Vector v_resultadoconsulta = new Vector();
    Vector resultadoconsulta01 = new Vector();
    Vector resultadoconsulta11 = new Vector();
    Vector v_resultadoconsulta2 = new Vector();

    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
   
    //Toma la fecha inicial a consultar
    String fechaini = "";
    //Toma la fecha final a consultar
    String fechafin = "";
    //Toma el código de la unidad de proceso
    String unipro = "";
    //Toma la transaccion
    String tran = "";
    String vusuario = "";
    String producto = "";

    try{
       fechaini = (java.lang.String)session.getAttribute("s_fechaini");//request.getParameter("fechaini").trim();
       }
    catch (Exception e) { fechaini = ""; }
    try{
       fechafin = (java.lang.String)session.getAttribute("s_fechafin");//request.getParameter("fechafin").trim();
       }
    catch (Exception e) { fechafin = ""; }
    try{
       unipro = (java.lang.String)session.getAttribute("s_unipro");//quest.getParameter("unipro");
       }
    catch (Exception e) { unipro = ""; }
    try{
       tran = (java.lang.String)session.getAttribute("s_tran");request.getParameter("s_tran");
       }
    catch (Exception e) { tran= ""; }
    try{
       vusuario = (java.lang.String)session.getAttribute("s_usuario");request.getParameter("s_tran");
       }
    catch (Exception e) { vusuario= ""; }
    
    try{
        producto = (java.lang.String)session.getAttribute("s_producto");
        }
    catch (Exception e) { vusuario= ""; }
    
    if(tran != null)
    {
     if (producto.equals("VOLUNTARIOS")) 
     {
      if (tran.equals("STR001"))
        {
        if (unipro.equals("UUP099"))
          {
          v_dconsulta = "SELECT          RET_CON_PRO_CODIGO,    --Producto\n"+
                        "                RET_CON_NUMERO,        --Contrato\n"+
                        "                TO_CHAR(RET_FECHA_PROCESO,'RRRR-MM-DD'),     --Fecha proceso\n"+
                        "                TO_CHAR(RET_FECHA_EFECTIVA,'RRRR-MM-DD'),    --Fecha efectiva\n"+
                        "                RET_CONSECUTIVO,       --No transacción Tax\n"+
                        "                RET_TRANSACCION,       --No transacción MF\n"+
                        "                tran.ref_descripcion,  --transaccion\n"+
                        "                tptran.ref_descripcion,--Tipo transacción\n"+
                        "                unidad.ref_descripcion,--Unidad\n"+
                        "                RET_VALOR_NETO,        --Valor neto\n"+
                        "                RET_VALOR_BRUTO,       --Valor bruto\n"+
                        "                estado.ref_descripcion --Estado\n"+
                        "FROM            tbretiros,\n"+
                        "                tbreferencias tran,\n"+
                        "                tbreferencias tptran,\n"+
                        "                tbreferencias unidad,\n"+
                        "                tbreferencias estado\n"+
                        "WHERE           (RET_REF_ESTADO = 'SER005' OR RET_REF_ESTADO = 'SER009' OR RET_REF_ESTADO = 'SER013' OR RET_REF_ESTADO = 'SER014') \n"+
                        "AND             tran.ref_codigo = RET_COT_REF_TRANSACCION \n"+
                        "AND             tptran.ref_codigo = RET_COT_REF_TIPO_TRANSACCION \n"+
                        "AND             unidad.ref_codigo = RET_REF_UNIDAD_PROCESO \n"+
                        "AND             estado.ref_codigo = RET_REF_ESTADO \n"+
                        "AND             RET_CON_PRO_CODIGO  IN ('MFUND','OMPEV','SIPEN') \n" +
                        "AND             ret_fecha_proceso \n"+
                        "BETWEEN         TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD') ";
           try
           {
            if(!vusuario.trim().equals(""))
           {
              v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('"+vusuario+"')  ";
           }
          }
          catch(Exception e)
          {}
          v_dconsulta +=   "ORDER BY 1,2,9  ";

          v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);
          v_dconsulta = "SELECT          inl_producto,    --Producto\n"+
                        "                inl_transaccion,        --Contrato\n"+
                        "                inl_mensaje, \n"+
                        "                to_char(inl_fecha, 'RRRR-MM-DD'), \n"+
                        "                INL_DATOS,  --Mensaje de Error\n"+
                        "                 INL_PASO   --PASO de Error\n"+
                        "FROM            tbinterface_logs \n"+
                        "WHERE           inl_interface = 'EG' \n"+
                        "AND             inl_producto NOT in ('FPOB','FPAL') \n" +  
                        "AND             inl_fecha \n"+
                        "BETWEEN         TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD')";
         resultadoconsulta01 = v_Consulta.TBFL_Consulta(v_dconsulta);
          }
        else
          {
          v_dconsulta = "SELECT          RET_CON_PRO_CODIGO,    --Producto\n"+
                        "                RET_CON_NUMERO,        --Contrato\n"+
                        "                TO_CHAR(RET_FECHA_PROCESO,'RRRR-MM-DD'),     --Fecha proceso\n"+
                        "                TO_CHAR(RET_FECHA_EFECTIVA,'RRRR-MM-DD'),    --Fecha efectiva\n"+
                        "                RET_CONSECUTIVO,       --No transacción Tax\n"+
                        "                RET_TRANSACCION,       --No transacción MF\n"+
                        "                tran.ref_descripcion,  --transaccion\n"+
                        "                tptran.ref_descripcion,--Tipo transacción\n"+
                        "                unidad.ref_descripcion,--Unidad\n"+
                        "                RET_VALOR_NETO,        --Valor neto\n"+
                        "                RET_VALOR_BRUTO,       --Valor bruto\n"+
                        "                estado.ref_descripcion --Estado\n"+
                        "FROM            tbretiros,\n"+
                        "                tbreferencias tran,\n"+
                        "                tbreferencias tptran,\n"+
                        "                tbreferencias unidad,\n"+
                        "                tbreferencias estado\n"+
                        "WHERE           (RET_REF_ESTADO = 'SER005' OR RET_REF_ESTADO = 'SER009' OR RET_REF_ESTADO = 'SER013' OR RET_REF_ESTADO = 'SER014')\n"+
                        "AND             RET_REF_UNIDAD_PROCESO = '"+unipro+"' \n"+
                        "AND             tran.ref_codigo = RET_COT_REF_TRANSACCION \n"+
                        "AND             tptran.ref_codigo = RET_COT_REF_TIPO_TRANSACCION \n"+
                        "AND             unidad.ref_codigo = RET_REF_UNIDAD_PROCESO \n"+
                        "AND             estado.ref_codigo = RET_REF_ESTADO \n"+
                        "AND             RET_CON_PRO_CODIGO  IN ('MFUND','OMPEV','SIPEN') \n" +
                        "AND             ret_fecha_proceso \n"+
                        "BETWEEN         TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD') ";
            try
            {
             if(!vusuario.trim().equals(""))
             {
              v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('"+vusuario+"')  ";
             }
            }
            catch(Exception e)
            {}
            v_dconsulta +=       "ORDER BY 1,2,9  ";
          v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);
          v_dconsulta = "SELECT          inl_producto,    --Producto\n"+
                        "                inl_transaccion,        --Contrato\n"+
                        "                inl_mensaje, \n"+
                        "                to_char(inl_fecha, 'RRRR-MM-DD'),\n"+
                        "                INL_DATOS,  --Mensaje de Error\n"+
                        "                 INL_PASO   --PASO de Error\n"+
                        "FROM            tbinterface_logs \n"+
                        "WHERE           inl_interface = 'EG' \n"+
                        "AND             inl_producto NOT in ('FPOB','FPAL') \n" +  
                        "AND             inl_fecha \n"+
                        "BETWEEN         TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD')";
        resultadoconsulta01 = v_Consulta.TBFL_Consulta(v_dconsulta);
          }
        }
      else if (tran.equals("STR002"))
        {
         v_dconsulta = "SELECT          inl_producto,    --Producto\n"+
                       "                inl_transaccion,        --Contrato\n"+
                       "                inl_mensaje, \n"+
                       "                to_char(inl_fecha, 'RRRR-MM-DD'), \n"+
                       "                INL_DATOS,   --Mensaje de Error\n"+
                       "                INL_PASO,   --PASO de Error\n"+
                       "                 ina_contrato  \n"+
                       "FROM           tbinterface_logs \n"+
                       "              ,tbinterface_aportes  \n"+
                       "WHERE          ina_fecha (+) = inl_fecha  \n"+
                       " and            ina_paso (+) = inl_paso  \n"+
                       " and            ina_producto (+) = inl_producto  \n"+
                       " and            ina_transaccion (+) = inl_transaccion  \n"+
                       " and            ina_tipo_registro (+) = '02'  \n"+
                       " and            inl_fecha BETWEEN TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD') \n"+
                       " and            inl_paso = '1'  \n"+
                       " and            ina_producto in ('MFUND','OMPEV','SIPEN') \n" +
                       " and            inl_interface = 'RE'  ";


        resultadoconsulta11 = v_Consulta.TBFL_Consulta(v_dconsulta);
        }
      else if (tran.equals("STR003"))
        {
        v_dconsulta = "SELECT          inl_producto,    --Producto\n"+
                      "                inl_transaccion,        --Contrato\n"+
                      "                inl_mensaje, \n"+
                      "                to_char(inl_fecha, 'RRRR-MM-DD'), \n"+
                      "                INL_DATOS, --Mensaje de Error\n"+
                      "                INL_PASO   --PASO de Error\n"+
                      "FROM            tbinterface_logs \n"+
                      "WHERE           inl_interface = 'EG' \n"+
                      "AND             inl_producto NOT in ('FPOB','FPAL') \n" +  
                      "AND             inl_fecha \n"+
                      "BETWEEN         TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD') "+
                      " AND            INL_MENSAJE LIKE '%Ajuste negativo:%' ";
        v_resultadoconsulta2 = v_Consulta.TBFL_Consulta(v_dconsulta);
        }
      else if(tran.equals("STR099"))
        {
        if (unipro.equals("UUP099"))
          {
          v_dconsulta = "SELECT          RET_CON_PRO_CODIGO,    --Producto\n"+
                        "                RET_CON_NUMERO,        --Contrato\n"+
                        "                TO_CHAR(RET_FECHA_PROCESO,'RRRR-MM-DD'),     --Fecha proceso\n"+
                        "                TO_CHAR(RET_FECHA_EFECTIVA,'RRRR-MM-DD'),    --Fecha efectiva\n"+
                        "                RET_CONSECUTIVO,       --No transacción Tax\n"+
                        "                RET_TRANSACCION,       --No transacción MF\n"+
                        "                tran.ref_descripcion,  --transaccion\n"+
                        "                tptran.ref_descripcion,--Tipo transacción\n"+
                        "                unidad.ref_descripcion,--Unidad\n"+
                        "                RET_VALOR_NETO,        --Valor neto\n"+
                        "                RET_VALOR_BRUTO,       --Valor bruto\n"+
                        "                estado.ref_descripcion --Estado\n"+
                        "FROM            tbretiros,\n"+
                        "                tbreferencias tran,\n"+
                        "                tbreferencias tptran,\n"+
                        "                tbreferencias unidad,\n"+
                        "                tbreferencias estado\n"+
                        "WHERE           (RET_REF_ESTADO = 'SER005' OR RET_REF_ESTADO = 'SER009' OR RET_REF_ESTADO = 'SER013' OR RET_REF_ESTADO = 'SER014')\n"+
                        "AND             tran.ref_codigo = RET_COT_REF_TRANSACCION \n"+
                        "AND             tptran.ref_codigo = RET_COT_REF_TIPO_TRANSACCION \n"+
                        "AND             unidad.ref_codigo = RET_REF_UNIDAD_PROCESO \n"+
                        "AND             estado.ref_codigo = RET_REF_ESTADO \n"+
                        "AND             RET_CON_PRO_CODIGO  IN ('MFUND','OMPEV','SIPEN') \n" +
                        "AND             ret_fecha_proceso \n"+
                        "BETWEEN         TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD') ";
           try
            {
             if(!vusuario.trim().equals(""))
             {
              v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('"+vusuario+"')  ";
             }
            }
            catch(Exception e)
            {}
            v_dconsulta +=   "ORDER BY 1,2,9  ";
         v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);
          }
        else
          {
          v_dconsulta = "SELECT          RET_CON_PRO_CODIGO,    --Producto\n"+
                        "                RET_CON_NUMERO,        --Contrato\n"+
                        "                TO_CHAR(RET_FECHA_PROCESO,'RRRR-MM-DD'),     --Fecha proceso\n"+
                        "                TO_CHAR(RET_FECHA_EFECTIVA,'RRRR-MM-DD'),    --Fecha efectiva\n"+
                        "                RET_CONSECUTIVO,       --No transacción Tax\n"+
                        "                RET_TRANSACCION,       --No transacción MF\n"+
                        "                tran.ref_descripcion,  --transaccion\n"+
                        "                tptran.ref_descripcion,--Tipo transacción\n"+
                        "                unidad.ref_descripcion,--Unidad\n"+
                        "                RET_VALOR_NETO,        --Valor neto\n"+
                        "                RET_VALOR_BRUTO,       --Valor bruto\n"+
                        "                estado.ref_descripcion --Estado\n"+
                        "FROM            tbretiros,\n"+
                        "                tbreferencias tran,\n"+
                        "                tbreferencias tptran,\n"+
                        "                tbreferencias unidad,\n"+
                        "                tbreferencias estado\n"+
                        "WHERE           (RET_REF_ESTADO = 'SER005' OR RET_REF_ESTADO = 'SER009' OR RET_REF_ESTADO = 'SER013' OR RET_REF_ESTADO = 'SER014')\n"+
                        "AND             RET_REF_UNIDAD_PROCESO = '"+unipro+"' \n"+
                        "AND             tran.ref_codigo = RET_COT_REF_TRANSACCION \n"+
                        "AND             tptran.ref_codigo = RET_COT_REF_TIPO_TRANSACCION \n"+
                        "AND             unidad.ref_codigo = RET_REF_UNIDAD_PROCESO \n"+
                        "AND             estado.ref_codigo = RET_REF_ESTADO \n"+
                        "AND             RET_CON_PRO_CODIGO  IN ('MFUND','OMPEV','SIPEN') \n" +
                        "AND             ret_fecha_proceso\n"+
                        "BETWEEN         TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD') ";
            try
            {
             if(!vusuario.trim().equals(""))
             {
              v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('"+vusuario+"')  ";
             }
            }
            catch(Exception e)
            {}

            v_dconsulta +=   "ORDER BY 1,9  ";
          v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);
          }
          v_dconsulta = "SELECT          inl_producto,    --Producto\n"+
                        "                inl_transaccion,        --Contrato\n"+
                        "                inl_mensaje,  \n"+
                        "                to_char(inl_fecha, 'RRRR-MM-DD'),  \n"+
                        "                INL_DATOS, --Mensaje de Error\n"+
                        "                INL_PASO   --PASO de Error\n"+
                        "FROM            tbinterface_logs\n"+
                        "WHERE           inl_interface = 'EG' \n"+
                        "AND             inl_producto NOT in ('FPOB','FPAL') \n" +  
                        "AND             inl_fecha\n"+
                        "BETWEEN         TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD')";
         resultadoconsulta01 = v_Consulta.TBFL_Consulta(v_dconsulta);

         v_dconsulta = "SELECT          inl_producto,    --Producto\n"+
                       "                inl_transaccion,        --Contrato\n"+
                       "                inl_mensaje,  \n"+
                       "                to_char(inl_fecha, 'RRRR-MM-DD'), \n"+
                       "                INL_DATOS,  --Mensaje de Error\n"+
                       "                INL_PASO,   --PASO de Error\n"+
                       "                ina_contrato  \n"+
                       "FROM           tbinterface_logs \n"+
                       "              ,tbinterface_aportes  \n"+
                       "WHERE          ina_fecha (+) = inl_fecha  \n"+
                       " and            ina_paso (+) = inl_paso  \n"+
                       " and            ina_producto (+) = inl_producto  \n"+
                       " and            ina_transaccion (+) = inl_transaccion  \n"+
                       " and            ina_tipo_registro (+) = '02'  \n"+
                       " and            inl_fecha BETWEEN TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD') \n"+
                       " and            inl_paso = '1'  \n"+
                       " and            ina_producto in ('MFUND','OMPEV','SIPEN') \n" +
                       " and            inl_interface = 'RE'  ";
         resultadoconsulta11 = v_Consulta.TBFL_Consulta(v_dconsulta);

         v_dconsulta = "SELECT          inl_producto,    --Producto\n"+
                       "                inl_transaccion,        --Contrato\n"+
                       "                inl_mensaje,  \n"+
                       "                to_char(inl_fecha, 'RRRR-MM-DD'), \n"+
                       "                INL_DATOS,  --Mensaje de Error\n"+
                       "                INL_PASO   --PASO de Error\n"+
                       "FROM            tbinterface_logs \n"+
                       "WHERE           RET_CON_PRO_CODIGO  IN ('MFUND','OMPEV','SIPEN') \n" +
                       "AND             inl_interface = 'EG' \n"+                       
                       "AND             inl_fecha \n"+
                       "BETWEEN         TO_DATE('"+fechaini+"','RRRR-MM-DD') AND TO_DATE('"+fechafin+"','RRRR-MM-DD') "+
                       " AND            INL_MENSAJE LIKE '%Ajuste negativo:%' ";
         v_resultadoconsulta2 = v_Consulta.TBFL_Consulta(v_dconsulta);
        }
      TBPL_Publicar(v_resultadoconsulta, v_resultadoconsulta2, resultadoconsulta01, resultadoconsulta11, fechaini, fechafin, tran);
      }//FIN PRODUCTO VOLUNTARIOS
    else {
       if (tran.equals("STR001") || tran.equals("STR099")) //retiros - TODAS
       {
           v_dconsulta = "SELECT  RET_CON_PRO_CODIGO, \n" +
                                  "RET_CON_NUMERO, \n" +
                                  "RET_FECHA_PROCESO, \n" +
                                  "RET_FECHA_EFECTIVA, \n" +
                                  "RET_CONSECUTIVO, \n" +  
                                  "RET_TRANSACCION, \n" +
                                  "'RETIRO' TRANSACCION, \n" +
                                  "RET_VALOR_BRUTO, \n" +           
                                  "RET_VALOR_NETO, \n" +       
                                  "INL_MENSAJE, \n" +
                                  "REF_DESCRIPCION \n" +
                           "FROM   TBRETIROS_OBLIG\n" +
                           "INNER JOIN tbinterface_logs_oblig ON  INL_PRODUCTO  = RET_CON_PRO_CODIGO \n" +
                           "                                  AND to_char(INL_FECHA,'dd-mm-yyyy') = to_char(RET_FECHA_PROCESO,'dd-mm-yyyy') \n" +
                           "                                  AND INL_DATOS     = TO_NUMBER(RET_CON_NUMERO) ||'-'|| RET_CONSECUTIVO \n" +
                           "INNER JOIN TBREFERENCIAS       ON REF_CODIGO     = RET_REF_ESTADO " +
                           "WHERE  RET_CON_PRO_CODIGO  = '"+producto.trim()+"' \n" +
                           "       AND RET_REF_ESTADO  NOT IN ('SER006','SER001') \n" +
                           "       AND RET_FECHA_PROCESO BETWEEN TO_DATE('" + fechaini + "','YYYY-MM-DD') AND TO_DATE('"+ fechafin +"','YYYY-MM-DD') \n" +
                           "       AND INL_FECHA BETWEEN TO_DATE('" + fechaini +"','YYYY-MM-DD') AND TO_DATE('"+ fechafin +"','YYYY-MM-DD') \n" + 
                           "       AND INL_INTERFACE='RE' \n";
           if (!vusuario.trim().equals("")) {
               v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('" + vusuario + "')  ";
           }
           v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);
           TBPL_Publicar(v_resultadoconsulta,fechaini, fechafin, tran);
       }
       else
         TBPL_Publicar(null,fechaini, fechafin, tran);
     }
    }        
      v_Consulta.TBPL_shutDown();
  }

      private void TBPL_Publicar(Vector valor, Vector valor2, Vector valor01, Vector valor11, String fechaini, String fechafin, String tran)
      {
      valor.trimToSize();
      valor01.trimToSize();
      valor11.trimToSize();
      valor2.trimToSize();


      out.println("<html>");
      out.println("<head>");
      out.println("<title>Reportes de Control</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<p>&nbsp;</p>");
      out.println("<p align=\"center\"><big><big><strong>Reportes de control</strong></big></big></p>");
      out.println("<p align=\"center\"><big><strong>Transacciones NO procesadas para&nbsp;"+TBFL_ConsultaDescripcion(tran)+"</strong></big></p>");
      out.println("<div align=\"center\"><center>");
      out.println("<TABLE bgColor=#ffffff border=0 cellPadding=0 cellSpacing=0 width=890><TBODY>");
      out.println("<TR><!-- row 1 --><TD>&nbsp;</TD></TR>");
      out.println("<TR><!-- row 2 -->");
      out.println("<TD align=left vAlign=top><!-- Empieza TD1 -->");



        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR bgColor=white borderColor=black align=center><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Producto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Contrato</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha proceso</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha efectiva</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No transacción Tax</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de Transacción MFUND</B></FONT></TD>");
        out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Transacción</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de retiro</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>UNIDAD</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Neto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Bruto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Mensaje de Error</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Estado</B></FONT></TD></TR>");
        if (valor.size()!=0)
          if (!valor.elementAt(0).toString().equals("No hay elementos"))
            for (int i = 0; i<valor.size(); i+=12)
               {
               out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor.elementAt(i).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor.elementAt(i+1).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor.elementAt(i+2).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor.elementAt(i+3).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor.elementAt(i+4).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor.elementAt(i+5).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor.elementAt(i+6).toString()+"</FONT></TD>");
               out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor.elementAt(i+7).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor.elementAt(i+8).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+9).toString()))+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+10).toString()))+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor.elementAt(i+11).toString()+"</FONT></TD></TR>");
               }
        if (valor01.size()!=0)
          if (!valor01.elementAt(0).toString().equals("No hay elementos"))
            for (int k = 0; k<valor01.size(); k+=6)
               {
               out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor01.elementAt(k).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor01.elementAt(k+3).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor01.elementAt(k+1).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>RETIROS</FONT></TD>");
               out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor01.elementAt(k+2).toString()+"<BR>DATOS:"+valor01.elementAt(k+4).toString()+"<BR>PASO :"+valor01.elementAt(k+5).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD></TR>");
               }
        if (valor11.size()!=0)
          if (!valor11.elementAt(0).toString().equals("No hay elementos"))
            for (int k = 0; k<valor11.size(); k+=7)
               {
               out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor11.elementAt(k).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor11.elementAt(k+6).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor11.elementAt(k+3).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor11.elementAt(k+1).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>APORTES</FONT></TD>");
               out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor11.elementAt(k+2).toString()+"<BR>DATOS:"+valor11.elementAt(k+4).toString()+"<BR>PASO :"+valor11.elementAt(k+5).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD></TR>");
               }
        if (valor2.size()!=0)
          if (!valor2.elementAt(0).toString().equals("No hay elementos"))
            for (int k = 0; k<valor2.size(); k+=6)
               {
               out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor2.elementAt(k).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor2.elementAt(k+3).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor2.elementAt(k+1).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>AJUSTES</FONT></TD>");
               out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor2.elementAt(k+2).toString()+"<BR>DATOS: "+valor2.elementAt(k+4).toString()+"<BR>PASO: "+valor2.elementAt(k+5).toString()+"</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD></TR>");
               }
        out.println("</TABLE>");


      out.println("</TD><!-- Termina TD1 -->");
      out.println("</TR><!-- row 2 -->");
      out.println("<TR><!-- row 1 --><TD>&nbsp;</TD></TR>");

      out.println("</TBODY></TABLE>");
      out.println("</center></div>");
      out.println("<BLOCKQUOTE><BLOCKQUOTE><BLOCKQUOTE>");
      out.println("<p align=\"right\"><strong>Fechainicial:&nbsp;"+fechaini+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fechafinal:&nbsp;"+fechafin+"<BR>YYYY-MM-DD</strong></p>");
      out.println("</BLOCKQUOTE></BLOCKQUOTE></BLOCKQUOTE>");
      out.println("</body></html>");
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.close();
      }
      
    private void TBPL_Publicar(Vector valor, String fechaini, String fechafin, String tran)
    {    

    out.println("<html>");
    out.println("<head>");
    out.println("<title>Reportes de Control</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<p>&nbsp;</p>");
    out.println("<p align=\"center\"><big><big><strong>Reportes de control</strong></big></big></p>");
    out.println("<p align=\"center\"><big><strong>Transacciones NO procesadas para&nbsp;"+TBFL_ConsultaDescripcion(tran)+"</strong></big></p>");
    out.println("<div align=\"center\"><center>");
    out.println("<TABLE bgColor=#ffffff border=0 cellPadding=0 cellSpacing=0 width=890><TBODY>");
    out.println("<TR><!-- row 1 --><TD>&nbsp;</TD></TR>");
    out.println("<TR><!-- row 2 -->");
    out.println("<TD align=left vAlign=top><!-- Empieza TD1 -->");

      out.println("<TABLE bgColor=white bor der=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
      out.println("<TR bgColor=white borderColor=black align=center><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Producto</B></FONT></TD>" +
                  "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Contrato</B></FONT></TD>" +
                  "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha proceso</B></FONT></TD>" +
                  "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha efectiva</B></FONT></TD>" +
                  "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>No transacción Tax</B></FONT></TD>" +
                  "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de Transacción AS400</B></FONT></TD>");
      out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Transacción</B></FONT></TD>" +                                    
                  "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Bruto</B></FONT></TD>" +
                  "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Neto</B></FONT></TD>" +
                  "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Mensaje de Error</B></FONT></TD>" +
                  "<TD width=\"22%\"><FONT color=black face=verdana size=1><B>Estado</B></FONT></TD></TR>");
      if (valor != null && valor.size()!=0)
      {
        valor.trimToSize();
        if (!valor.elementAt(0).toString().equals("No hay elementos"))
          for (int i = 0; i<valor.size(); i+=11)
             {
             out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+valor.elementAt(i).toString()+"</FONT></TD>" +
                         "<TD nowrap><FONT color=black face=verdana size=1>"+valor.elementAt(i+1).toString()+"</FONT></TD>" +
                         "<TD nowrap><FONT color=black face=verdana size=1>"+valor.elementAt(i+2).toString().substring(0, 10)+"</FONT></TD>" +
                         "<TD nowrap><FONT color=black face=verdana size=1>"+valor.elementAt(i+3).toString().substring(0, 10)+"</FONT></TD>" +
                         "<TD nowrap><FONT color=black face=verdana size=1>"+valor.elementAt(i+4).toString()+"</FONT></TD>" +
                         "<TD nowrap><FONT color=black face=verdana size=1>"+valor.elementAt(i+5).toString()+"</FONT></TD>" +
                         "<TD nowrap><FONT color=black face=verdana size=1>"+valor.elementAt(i+6).toString()+"</FONT></TD>");
             out.println("<TD nowrap><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+7).toString()))+"</FONT></TD>" +                                                                                    
                         "<TD nowrap><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i+8).toString()))+"</FONT></TD>" +                                                                            
                         "<TD nowrap><FONT color=black face=verdana size=1>"+valor.elementAt(i+9).toString()+"</FONT></TD>" +
                         "<TD nowrap><FONT color=black face=verdana size=1>"+valor.elementAt(i+10).toString()+"</FONT></TD></TR>");
             }      
      }
    out.println("</TABLE>");

    out.println("</TD><!-- Termina TD1 -->");
    out.println("</TR><!-- row 2 -->");
    out.println("<TR><!-- row 1 --><TD>&nbsp;</TD></TR>");

    out.println("</TBODY></TABLE>");
    out.println("</center></div>");
    out.println("<BLOCKQUOTE><BLOCKQUOTE><BLOCKQUOTE>");
    out.println("<p align=\"right\"><strong>Fechainicial:&nbsp;"+fechaini+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fechafinal:&nbsp;"+fechafin+"<BR>YYYY-MM-DD</strong></p>");
    out.println("</BLOCKQUOTE></BLOCKQUOTE></BLOCKQUOTE>");
    out.println("</body></html>");
    //Aqui van los campo que se quieren mostrar
    out.println("<BR>&nbsp;&nbsp;<BR>");
    out.close();
    }

  private String TBFL_ConsultaDescripcion(String cod)
  {
  //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
  String declaracion = "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+cod+"'";
  Vector resultadodeclaracion = v_Consulta.TBFL_Consulta(declaracion, "ref_descripcion");
  //v_Consulta.TBPL_shutDown();
  if (resultadodeclaracion.elementAt(0).toString().equals("No hay elementos"))
    return ("Todas");
  else
    return (resultadodeclaracion.elementAt(0).toString());
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_REPORTESCONTROL.TBCS_Reporte_NO_Impresion Information";
  }
}

