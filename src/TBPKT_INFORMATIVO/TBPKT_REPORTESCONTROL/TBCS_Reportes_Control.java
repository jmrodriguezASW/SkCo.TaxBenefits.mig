package TBPKT_INFORMATIVO.TBPKT_REPORTESCONTROL;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;

import java.util.*;

import java.text.NumberFormat;

import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;

import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTMLII;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;

import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_Reportes_Control extends HttpServlet implements SingleThreadModel {
    private PrintWriter out;
    private TBCL_Consulta v_Consulta;
    private String v_nuevaCadena = "";

    /**
     * Initialize global variables
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**
     * Process the HTTP Get request
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (session == null)
            session = request.getSession(true);

        session.setMaxInactiveInterval(3600);
        try {
            out = new PrintWriter(response.getOutputStream());
            response.setContentType("text/html");
            String parametros[] = new String[8];
            String cadena = request.getParameter("cadena");
            v_nuevaCadena = cadena;
            String ip_tax = request.getRemoteAddr();
            TBCL_Seguridad Seguridad = new TBCL_Seguridad();
            parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
        } catch (Exception ex) {
            System.out.println("");
        }
        v_Consulta = new TBCL_Consulta();
        String v_dconsulta = "";
        //Vector donde se guardara el resultado de la v_dconsulta
        Vector v_resultadoconsulta = new Vector();
        Vector v_resultadoconsulta2 = new Vector();
        Vector resultadoconsulta01 = new Vector();
        Vector resultadoconsulta11 = new Vector();

        v_Consulta.TBPL_RealizarConexion(); //Realiza la conexion con la base de datos

        //Toma la opcion
        String opcion = "";
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

        try {
            opcion = request.getParameter("opcion");
            fechaini = request.getParameter("obligatoriofechaini").trim();
            fechafin = request.getParameter("obligatoriofechafin").trim();
            unipro = request.getParameter("unipro");
            tran = request.getParameter("tran");
            vusuario = request.getParameter("vusuario");
            producto = request.getParameter("producto");

            session.removeValue("s_fechaini");
            session.putValue("s_fechaini", (java.lang.Object)fechaini);
            session.removeValue("s_fechafin");
            session.putValue("s_fechafin", (java.lang.Object)fechafin);
            session.removeValue("s_unipro");
            session.putValue("s_unipro", (java.lang.Object)unipro);
            session.removeValue("s_tran");
            session.putValue("s_tran", (java.lang.Object)tran);
            session.removeValue("s_usuario");
            session.putValue("s_usuario", (java.lang.Object)vusuario);
            session.removeValue("s_producto");
            session.putValue("s_producto", (java.lang.Object)producto);


        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (producto.equals("VOLUNTARIOS")) 
        {
        /*transacciones procesadas*/
        if (opcion.equals("siproceso")) {
            //todas las unidades
            if (unipro.equals("UUP099")) {
                v_dconsulta = "SELECT COUNT(distinct(ret_consecutivo)),\n" +
                        "       SUM(RET_VALOR_NETO),  --Valor Neto\n" +
                        "       SUM(RET_VALOR_BRUTO),                   --Valor Bruto\n" +
                        "       SUM(APR_CAPITAL),                  --Capital\n" +
                        "       SUM(APR_RENDIMIENTOS),             --Rendimientos\n" +
                        "       NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC002')),0),\n" +
                        "       NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC001')),0),\n" +
                        "       NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC004')),0) + NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC003')),0)\n" +
                        " FROM  tbaportes_retiros,\n" +
                        "       tbretiros \n" +
                        "WHERE  RET_CON_PRO_CODIGO in ('MFUND','OMPEV','SIPEN')" +
                        "  AND  APR_RET_CON_PRO_CODIGO    = RET_CON_PRO_CODIGO\n" +
                        "  AND  APR_RET_CON_NUMERO        = RET_CON_NUMERO\n" +
                        "  AND  APR_RET_CONSECUTIVO       = RET_CONSECUTIVO\n" +
                        "  AND (RET_REF_ESTADO = 'SER006' OR RET_REF_ESTADO = 'SER001')\n" +
                        "  AND  RET_FECHA_PROCESO BETWEEN TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" +
                        fechafin + "','RRRR-MM-DD') ";
                try {
                    if (!vusuario.trim().equals("")) {
                        v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('" + vusuario + "')  ";
                    }
                } catch (Exception e) {
                }
                v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);

                v_dconsulta = "SELECT               SUM(RET_VALOR_NETO),\n" +
                        "                      SUM(RET_VALOR_BRUTO)\n" +
                        " FROM                 tbretiros\n" +
                        " WHERE                RET_CON_PRO_CODIGO in ('MFUND','OMPEV','SIPEN') " +
                        " AND                  (RET_REF_ESTADO = 'SER006' OR RET_REF_ESTADO = 'SER001')\n" +
                        " AND                  RET_FECHA_PROCESO\n" +
                        " BETWEEN              TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" + fechafin +
                        "','RRRR-MM-DD')";
                try {
                    if (!vusuario.trim().equals("")) {
                        v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('" + vusuario + "')  ";
                    }
                } catch (Exception e) {
                }
                resultadoconsulta01 = v_Consulta.TBFL_Consulta(v_dconsulta);

                v_dconsulta = " SELECT               SUM(MAX(RET_VALOR_NETO))\n" +
                        "                     ,SUM(MAX(RET_VALOR_BRUTO))\n" +
                        "                     ,SUM(APR_CAPITAL)\n" +
                        "                     ,SUM(APR_RENDIMIENTOS)\n" +
                        " FROM                 TBAPORTES_RETIROS,\n" +
                        "                      TBRETIROS\n" +
                        " WHERE\n" +
                        "                      RET_CON_PRO_CODIGO in ('MFUND','OMPEV','SIPEN') " +
                        " AND                  APR_RET_CON_PRO_CODIGO = RET_CON_PRO_CODIGO\n" +
                        " AND                  APR_RET_CON_NUMERO = RET_CON_NUMERO\n" +
                        " AND                  APR_RET_CONSECUTIVO = RET_CONSECUTIVO\n" +
                        " AND                  (RET_REF_ESTADO = 'SER006' OR RET_REF_ESTADO = 'SER001')\n" +
                        " AND                  RET_FECHA_PROCESO\n" +
                        " BETWEEN              TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" + fechafin +
                        "','RRRR-MM-DD') ";
                try {
                    if (!vusuario.trim().equals("")) {
                        v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('" + vusuario + "')  ";
                    }
                } catch (Exception e) {
                }
                v_dconsulta += " GROUP BY             APR_CAPITAL, APR_RENDIMIENTOS";
                v_resultadoconsulta2 = v_Consulta.TBFL_Consulta(v_dconsulta);

            } else //unidad especifica
            {
                v_dconsulta =
                        "SELECT COUNT(distinct(ret_consecutivo)), SUM(RET_VALOR_NETO)/COUNT(apr_apo_consecutivo),                    --Valor Neto\n" +
                        "       SUM(RET_VALOR_BRUTO)/COUNT(apr_apo_consecutivo),                   --Valor Bruto\n" +
                        "       SUM(APR_CAPITAL),                  --Capital\n" +
                        "       SUM(APR_RENDIMIENTOS),             --Rendimientos\n" +
                        "       NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC002')),0),\n" +
                        "       NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC001')),0),\n" +
                        "       NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC004')),0) + NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC003')),0)\n" +
                        "FROM   tbaportes_retiros,\n" +
                        "       tbretiros\n" +
                        "WHERE  RET_CON_PRO_CODIGO in ('MFUND','OMPEV','SIPEN') " +
                        "AND    APR_RET_CON_PRO_CODIGO = RET_CON_PRO_CODIGO\n" +
                        "AND    APR_RET_CON_NUMERO = RET_CON_NUMERO\n" +
                        "AND    APR_RET_CONSECUTIVO = RET_CONSECUTIVO \n" +
                        "AND   (RET_REF_ESTADO = 'SER006' OR RET_REF_ESTADO = 'SER001') \t" +
                        "AND   RET_REF_UNIDAD_PROCESO = '" + unipro + "'\n" +
                        "AND   RET_FECHA_PROCESO \t" + "BETWEEN    TO_DATE('" + fechaini +
                        "','RRRR-MM-DD') AND TO_DATE('" + fechafin + "','RRRR-MM-DD')";
                try {
                    if (!vusuario.trim().equals("")) {
                        v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('" + vusuario + "')  ";
                    }
                } catch (Exception e) {
                }
                v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);
                v_dconsulta = "SELECT               SUM(RET_VALOR_NETO),\n" +
                        "                     SUM(RET_VALOR_BRUTO)\n" +
                        " FROM                 tbretiros\n" +
                        " WHERE                RET_CON_PRO_CODIGO in ('MFUND','OMPEV','SIPEN')" +
                        " AND                  (RET_REF_ESTADO = 'SER006' OR RET_REF_ESTADO = 'SER001')\n" +
                        " AND                  RET_REF_UNIDAD_PROCESO = '" + unipro + "'\n" +
                        " AND                  RET_FECHA_PROCESO\n" +
                        " BETWEEN              TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" + fechafin +
                        "','RRRR-MM-DD')";
                try {
                    if (!vusuario.trim().equals("")) {
                        v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('" + vusuario + "')  ";
                    }
                } catch (Exception e) {
                }
                resultadoconsulta01 = v_Consulta.TBFL_Consulta(v_dconsulta);
                v_dconsulta = " SELECT               SUM(MAX(RET_VALOR_NETO))\n" +
                        "                     ,SUM(MAX(RET_VALOR_BRUTO))\n" +
                        "                     ,SUM(APR_CAPITAL)\n" +
                        "                     ,SUM(APR_RENDIMIENTOS)\n" +
                        " FROM                 TBRETIROS,\n" +
                        "                      TBAPORTES_RETIROS\n" +
                        " WHERE\n" +
                        "                     RET_CON_PRO_CODIGO in ('MFUND','OMPEV','SIPEN')" +
                        " AND                 (RET_REF_ESTADO = 'SER006' OR RET_REF_ESTADO = 'SER001')\n" +
                        " AND                  RET_REF_UNIDAD_PROCESO = '" + unipro + "'\n" +
                        " AND                  RET_FECHA_PROCESO\n" +
                        " BETWEEN              TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" + fechafin +
                        "','RRRR-MM-DD')\n" +
                        " AND                  APR_RET_CON_PRO_CODIGO = RET_CON_PRO_CODIGO\n" +
                        " AND                  APR_RET_CON_NUMERO = RET_CON_NUMERO\n" +
                        " AND                  APR_RET_CONSECUTIVO = RET_CONSECUTIVO  ";
                try {
                    if (!vusuario.trim().equals("")) {
                        v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('" + vusuario + "')  ";
                    }
                } catch (Exception e) {
                }

                v_dconsulta += " GROUP BY             APR_CAPITAL, APR_RENDIMIENTOS";
                v_resultadoconsulta2 = v_Consulta.TBFL_Consulta(v_dconsulta);
            }
            PublicarSI(v_resultadoconsulta, resultadoconsulta01, v_resultadoconsulta2, fechaini, fechafin, unipro,
                       session);
        } else if (opcion.equals("noproceso")) //transacciones no procesadas
        { //1
            if (tran.equals("STR001")) //retiros
            { //2
                if (unipro.equals("UUP099")) //todas la unidades
                { //3
                    v_dconsulta = "SELECT          RET_CON_PRO_CODIGO,    --Producto\n" +
                            "                RET_CON_NUMERO,        --Contrato\n" +
                            "                TO_CHAR(RET_FECHA_PROCESO,'RRRR-MM-DD'),     --Fecha proceso\n" +
                            "                TO_CHAR(RET_FECHA_EFECTIVA,'RRRR-MM-DD'),    --Fecha efectiva\n" +
                            "                RET_CONSECUTIVO,       --No transacción Tax\n" +
                            "                RET_TRANSACCION,       --No transacción MF\n" +
                            "                tran.ref_descripcion,  --transaccion\n" +
                            "                tptran.ref_descripcion,--Tipo transacción\n" +
                            "                unidad.ref_descripcion,--Unidad\n" +
                            "                RET_VALOR_NETO,        --Valor neto\n" +
                            "                RET_VALOR_BRUTO,       --Valor bruto\n" +
                            "                estado.ref_descripcion --Estado\n" +
                            "FROM            tbretiros,\n" +
                            "                tbreferencias tran,\n" +
                            "                tbreferencias tptran,\n" +
                            "                tbreferencias unidad,\n" +
                            "                tbreferencias estado\n" +
                            "WHERE           RET_CON_PRO_CODIGO in ('MFUND','OMPEV','SIPEN') " +
                            "AND             (RET_REF_ESTADO = 'SER005' OR RET_REF_ESTADO = 'SER009' OR RET_REF_ESTADO = 'SER013' OR RET_REF_ESTADO = 'SER014')\n" +
                            "AND             tran.ref_codigo = RET_COT_REF_TRANSACCION\n" +
                            "AND             tptran.ref_codigo = RET_COT_REF_TIPO_TRANSACCION\n" +
                            "AND             unidad.ref_codigo = RET_REF_UNIDAD_PROCESO\n" +
                            "AND             estado.ref_codigo = RET_REF_ESTADO\n" +
                            "AND             ret_fecha_proceso\n" +
                            "BETWEEN         TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" + fechafin +
                            "','RRRR-MM-DD')";
                    try {
                        if (!vusuario.trim().equals("")) {
                            v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('" + vusuario + "')  ";
                        }
                    } catch (Exception e) {
                    }
                    v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);

                    v_dconsulta = "SELECT          inl_producto,    --Producto\n" +
                            "                inl_transaccion,        --Contrato\n" +
                            "                inl_mensaje,   \n" +
                            "                to_char(inl_fecha, 'RRRR-MM-DD'), \n" +
                            "                INL_DATOS,  --Mensaje de Error\n" +
                            "                INL_PASO   --PASO de Error\n" +
                            "FROM            tbinterface_logs\n" +
                            "WHERE           inl_interface = 'EG'\n" +
                            " AND             inl_producto NOT in ('FPOB','FPAL') \n" +         
                            "AND             inl_fecha\n" +
                            "BETWEEN         TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" + fechafin +
                            "','RRRR-MM-DD')";
                    resultadoconsulta01 = v_Consulta.TBFL_Consulta(v_dconsulta);
                } //3
                else //unidad especifica
                { //3
                    v_dconsulta = "SELECT          RET_CON_PRO_CODIGO,    --Producto\n" +
                            "                RET_CON_NUMERO,        --Contrato\n" +
                            "                TO_CHAR(RET_FECHA_PROCESO,'RRRR-MM-DD'),     --Fecha proceso\n" +
                            "                TO_CHAR(RET_FECHA_EFECTIVA,'RRRR-MM-DD'),    --Fecha efectiva\n" +
                            "                RET_CONSECUTIVO,       --No transacción Tax\n" +
                            "                RET_TRANSACCION,       --No transacción MF\n" +
                            "                tran.ref_descripcion,  --transaccion\n" +
                            "                tptran.ref_descripcion,--Tipo transacción\n" +
                            "                unidad.ref_descripcion,--Unidad\n" +
                            "                RET_VALOR_NETO,        --Valor neto\n" +
                            "                RET_VALOR_BRUTO,       --Valor bruto\n" +
                            "                estado.ref_descripcion --Estado\n" +
                            "FROM            tbretiros,\n" +
                            "                tbreferencias tran,\n" +
                            "                tbreferencias tptran,\n" +
                            "                tbreferencias unidad,\n" +
                            "                tbreferencias estado\n" +
                            "WHERE           RET_CON_PRO_CODIGO in ('MFUND','OMPEV','SIPEN') " +
                            "AND            (RET_REF_ESTADO = 'SER005' OR RET_REF_ESTADO = 'SER009' OR RET_REF_ESTADO = 'SER013' OR RET_REF_ESTADO = 'SER014')\n" +
                            "AND             RET_REF_UNIDAD_PROCESO = '" + unipro + "'\n" +
                            "AND             tran.ref_codigo = RET_COT_REF_TRANSACCION\n" +
                            "AND             tptran.ref_codigo = RET_COT_REF_TIPO_TRANSACCION\n" +
                            "AND             unidad.ref_codigo = RET_REF_UNIDAD_PROCESO\n" +
                            "AND             estado.ref_codigo = RET_REF_ESTADO\n" +
                            "AND             ret_fecha_proceso\n" +
                            "BETWEEN         TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" + fechafin +
                            "','RRRR-MM-DD')";
                    try {
                        if (!vusuario.trim().equals("")) {
                            v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('" + vusuario + "')  ";
                        }
                    } catch (Exception e) {
                    }
                    v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);

                    v_dconsulta = "SELECT          inl_producto,    --Producto\n" +
                            "                inl_transaccion,        --Contrato\n" +
                            "                inl_mensaje, \n" +
                            "                to_char(inl_fecha, 'RRRR-MM-DD'),  \n" +
                            "                INL_DATOS,  --Mensaje de Error\n" +
                            "                INL_PASO   --PASO de Error\n" +
                            "FROM            tbinterface_logs\n" +
                            "WHERE           inl_interface = 'EG'\n" +
                            " AND             inl_producto NOT in ('FPOB','FPAL') \n" +   
                            "AND             inl_fecha\n" +
                            "BETWEEN         TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" + fechafin +
                            "','RRRR-MM-DD')";
                    resultadoconsulta01 = v_Consulta.TBFL_Consulta(v_dconsulta);
                } //3
            } //2
            else if (tran.equals("STR002")) //aportes
            { //4
                v_dconsulta = "SELECT          inl_producto,    --Producto\n" +
                        "                inl_transaccion,        --Contrato\n" +
                        "                inl_mensaje, \n" +
                        "                to_char(inl_fecha, 'RRRR-MM-DD'), \n" +
                        "                INL_DATOS,   --Mensaje de Error\n" +
                        "                INL_PASO,   --PASO de Error\n" +
                        "                 ina_contrato  \n" +
                        "FROM           tbinterface_logs \n" +
                        "              ,tbinterface_aportes  \n" +
                        "WHERE           ina_producto in ('MFUND','OMPEV','SIPEN')" +
                        " and            ina_fecha (+) = inl_fecha  \n" +
                        " and            ina_paso (+) = inl_paso  \n" +
                        " and            ina_producto (+) = inl_producto  \n" +
                        " and            ina_transaccion (+) = inl_transaccion  \n" +
                        " and            ina_tipo_registro (+) = '02'  \n" +
                        " and            inl_fecha BETWEEN TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" +
                        fechafin + "','RRRR-MM-DD') \n" +
                        " and            inl_paso = '1'  \n" +
                        " and            inl_interface = 'RE'  ";


                resultadoconsulta11 = v_Consulta.TBFL_Consulta(v_dconsulta);

            } //4
            else if (tran.equals("STR003")) //ajustes
            { //5
                v_dconsulta = "SELECT          inl_producto,    --Producto\n" +
                        "                inl_transaccion,        --Contrato\n" +
                        "                inl_mensaje,   \n" +
                        "                to_char(inl_fecha, 'RRRR-MM-DD'), \n" +
                        "                INL_DATOS, --Mensaje de Error\n" +
                        "                INL_PASO   --PASO de Error\n" +
                        "FROM            tbinterface_logs\n" +
                        "WHERE           inl_interface = 'EG'\n" +
                        " AND             inl_producto NOT in ('FPOB','FPAL') \n" +   
                        "AND             inl_fecha\n" +
                        "BETWEEN         TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" + fechafin +
                        "','RRRR-MM-DD') " + " AND            INL_MENSAJE LIKE '%Ajuste negativo:%' ";
                v_resultadoconsulta2 = v_Consulta.TBFL_Consulta(v_dconsulta);
            } //5
            else //todas las transacciones
            { //5
                if (unipro.equals("UUP099")) //todas las unidades
                { //6
                    v_dconsulta = "SELECT          RET_CON_PRO_CODIGO,    --Producto\n" +
                            "                RET_CON_NUMERO,        --Contrato\n" +
                            "                TO_CHAR(RET_FECHA_PROCESO,'RRRR-MM-DD'),     --Fecha proceso\n" +
                            "                TO_CHAR(RET_FECHA_EFECTIVA,'RRRR-MM-DD'),    --Fecha efectiva\n" +
                            "                RET_CONSECUTIVO,       --No transacción Tax\n" +
                            "                RET_TRANSACCION,       --No transacción MF\n" +
                            "                tran.ref_descripcion,  --transaccion\n" +
                            "                tptran.ref_descripcion,--Tipo transacción\n" +
                            "                unidad.ref_descripcion,--Unidad\n" +
                            "                RET_VALOR_NETO,        --Valor neto\n" +
                            "                RET_VALOR_BRUTO,       --Valor bruto\n" +
                            "                estado.ref_descripcion --Estado\n" +
                            "FROM            tbretiros,\n" +
                            "                tbreferencias tran,\n" +
                            "                tbreferencias tptran,\n" +
                            "                tbreferencias unidad,\n" +
                            "                tbreferencias estado\n" +
                            "WHERE           RET_CON_PRO_CODIGO in ('MFUND','OMPEV','SIPEN') " +
                            "AND             (RET_REF_ESTADO = 'SER005' OR RET_REF_ESTADO = 'SER009' OR RET_REF_ESTADO = 'SER013' OR RET_REF_ESTADO = 'SER014')\n" +
                            "AND             tran.ref_codigo = RET_COT_REF_TRANSACCION\n" +
                            "AND             tptran.ref_codigo = RET_COT_REF_TIPO_TRANSACCION\n" +
                            "AND             unidad.ref_codigo = RET_REF_UNIDAD_PROCESO\n" +
                            "AND             estado.ref_codigo = RET_REF_ESTADO\n" +
                            "AND             ret_fecha_proceso\n" +
                            "BETWEEN         TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" + fechafin +
                            "','RRRR-MM-DD')";
                    try {
                        if (!vusuario.trim().equals("")) {
                            v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('" + vusuario + "')  ";
                        }
                    } catch (Exception e) {
                    }
                    v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);
                    v_dconsulta = "SELECT          inl_producto,    --Producto\n" +
                            "                inl_transaccion,        --Contrato\n" +
                            "                inl_mensaje,   \n" +
                            "                to_char(inl_fecha, 'RRRR-MM-DD'), \n" +
                            "                INL_DATOS, --Mensaje de Error\n" +
                            "                INL_PASO   --PASO de Error\n" +
                            "FROM            tbinterface_logs\n" +
                            "WHERE           inl_interface = 'EG'\n" +
                            " AND             inl_producto NOT in ('FPOB','FPAL') \n" +   
                            "AND             inl_fecha\n" +
                            "BETWEEN         TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" + fechafin +
                            "','RRRR-MM-DD')";
                    resultadoconsulta01 = v_Consulta.TBFL_Consulta(v_dconsulta);
                } //6
                else //unidad especifica
                { //6
                    v_dconsulta = "SELECT                                               tbre.RET_CON_PRO_CODIGO,\n" +
                            "                 tbre.RET_CON_NUMERO,\n" +
                            "                 TO_CHAR(tbre.RET_FECHA_PROCESO,'RRRR-MM-DD'),\n" +
                            "                 TO_CHAR(tbre.RET_FECHA_EFECTIVA,'RRRR-MM-DD'),\n" +
                            "                 tbre.RET_CONSECUTIVO,\n" +
                            "                 tbre.RET_TRANSACCION,\n" +
                            "                 tran.ref_descripcion,\n" +
                            "                 tptran.ref_descripcion,\n" +
                            "                 unidad.ref_descripcion,\n" +
                            "                 tbre.RET_VALOR_NETO,\n" +
                            "                 tbre.RET_VALOR_BRUTO,\n" +
                            "                 estado.ref_descripcion\n" +
                            " FROM            tbretiros tbre,\n" +
                            "                 tbreferencias tran,\n" +
                            "                 tbreferencias tptran,\n" +
                            "                 tbreferencias unidad,\n" +
                            "                 tbreferencias estado\n" +
                            " WHERE           RET_CON_PRO_CODIGO in ('MFUND','OMPEV','SIPEN') " +
                            " AND             (tbre.RET_REF_ESTADO = 'SER005' OR tbre.RET_REF_ESTADO = 'SER009' OR tbre.RET_REF_ESTADO = 'SER013' OR tbre.RET_REF_ESTADO = 'SER014')\n" +
                            " AND             tbre.RET_REF_UNIDAD_PROCESO = '" + unipro + "'\n" +
                            " AND             tran.ref_codigo = tbre.RET_COT_REF_TRANSACCION\n" +
                            " AND             tptran.ref_codigo = tbre.RET_COT_REF_TIPO_TRANSACCION\n" +
                            " AND             unidad.ref_codigo = tbre.RET_REF_UNIDAD_PROCESO\n" +
                            " AND             estado.ref_codigo = tbre.RET_REF_ESTADO\n" +
                            " AND             tbre.ret_fecha_proceso\n" +
                            " BETWEEN         TO_DATE('" + fechaini + "','RRRR-MM-DD')AND TO_DATE('" + fechafin +
                            "','RRRR-MM-DD')\n" +
                            " AND exists\n" +
                            " (select tbr.* from tbcierres_retiros tbr\n" +
                            " inner join\n" +
                            " (select tbre2.CIE_RET_FECHA_CIERRE,MAX(tbre2.CIE_RET_NUMERO_CIERRE) as cie_ret_numero_cierre from tbcierres_retiros tbre2\n" +
                            " group by tbre2.CIE_RET_FECHA_CIERRE) tbx\n" +
                            " on tbr.cie_ret_numero_cierre = tbx.cie_ret_numero_cierre and tbr.cie_ret_fecha_cierre = tbx.cie_ret_fecha_cierre\n" +
                            " where  tbr.CIE_RET_FECHA_CIERRE BETWEEN  TO_DATE('" + fechaini +
                            "','RRRR-MM-DD') AND TO_DATE('" + fechafin + "','RRRR-MM-DD')\n" +
                            " and tbr.CIE_RET_CONSECUTIVO = tbre.RET_CONSECUTIVO)";

                    try {
                        if (!vusuario.trim().equals("")) {
                            v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('" + vusuario + "')  ";
                        }
                    } catch (Exception e) {
                    }
                    v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);
                    v_dconsulta = "SELECT          inl_producto,    --Producto\n" +
                            "                inl_transaccion,        --Contrato\n" +
                            "                inl_mensaje, \n" +
                            "                 to_char(inl_fecha, 'RRRR-MM-DD'), \n" +
                            "                 INL_DATOS,  --Mensaje de Error\n" +
                            "                 INL_PASO   --PASO de Error\n" +
                            "FROM            tbinterface_logs\n" +
                            "WHERE           inl_interface = 'EG'\n" +
                            " AND             inl_producto NOT in ('FPOB','FPAL') \n" +   
                            "AND             inl_fecha\n" +
                            "BETWEEN         TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" + fechafin +
                            "','RRRR-MM-DD')";
                    resultadoconsulta01 = v_Consulta.TBFL_Consulta(v_dconsulta);
                } //6
                v_dconsulta = "SELECT          inl_producto,    --Producto\n" +
                        "                inl_transaccion,        --Contrato\n" +
                        "                inl_mensaje,\n" +
                        "                to_char(inl_fecha, 'RRRR-MM-DD'),\n" +
                        "                INL_DATOS,  --Mensaje de Error\n" +
                        "                 INL_PASO,   --PASO de Error\n" +
                        "                 ina_contrato  \n" +
                        "FROM           tbinterface_logs \n" +
                        "              ,tbinterface_aportes  \n" +
                        "WHERE           ina_producto in ('MFUND','OMPEV','SIPEN') " +
                        " and            ina_fecha (+) = inl_fecha  \n" +
                        " and            ina_paso (+) = inl_paso  \n" +
                        " and            ina_producto (+) = inl_producto  \n" +
                        " and            ina_transaccion (+) = inl_transaccion  \n" +
                        " and            ina_tipo_registro (+) = '02'  \n" +
                        " and            inl_fecha BETWEEN TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" +
                        fechafin + "','RRRR-MM-DD') \n" +
                        " and            inl_paso = '1'  \n" +
                        " and            inl_interface = 'RE'  ";

                resultadoconsulta11 = v_Consulta.TBFL_Consulta(v_dconsulta);

                v_dconsulta = "SELECT          inl_producto,    --Producto\n" +
                        "                inl_transaccion,        --Contrato\n" +
                        "                inl_mensaje,\n" +
                        "                to_char(inl_fecha, 'RRRR-MM-DD'),\n" +
                        "                INL_DATOS,  --Mensaje de Error\n" +
                        "                 INL_PASO   --PASO de Error\n" +
                        "FROM            tbinterface_logs\n" +
                        "WHERE           inl_interface = 'EG' \n" +
                        " AND             inl_producto NOT in ('FPOB','FPAL') \n" +        
                        "AND             inl_fecha\n" +
                        "BETWEEN         TO_DATE('" + fechaini + "','RRRR-MM-DD') AND TO_DATE('" + fechafin +
                        "','RRRR-MM-DD') " + " AND            INL_MENSAJE LIKE '%Ajuste negativo:%' ";
                v_resultadoconsulta2 = v_Consulta.TBFL_Consulta(v_dconsulta);
            } //5
            PublicarNO(v_resultadoconsulta, v_resultadoconsulta2, resultadoconsulta01, resultadoconsulta11, fechaini,
                       fechafin, unipro, tran, session);
        }
        }//fin proceso actual parar voluntarios
        else {
            if (opcion.equals("siproceso")) {
                v_dconsulta = "SELECT COUNT(*)," +
                            "         SUM(NETO),\n" + 
                            "         SUM(BRUTO),\n" + 
                            "         SUM(CAPITAL),\n" + 
                            "         SUM(RENDIMIENTOS),\n" + 
                            "         SUM(CC),\n" + 
                            "         SUM(RET_REND),\n" + 
                            "         SUM(PENALI) \n" + 
                            "         FROM (\n" + 
                            " SELECT  SUM(RET_VALOR_NETO)/COUNT(apr_apo_consecutivo) NETO,  \n" + 
                            "        SUM(RET_VALOR_BRUTO)/COUNT(apr_apo_consecutivo) BRUTO,  \n" + 
                            "        SUM(APR_CAPITAL) CAPITAL,  \n" + 
                            "        SUM(APR_RENDIMIENTOS) RENDIMIENTOS,  \n" + 
                            "        NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC002')),0) CC,  \n" + 
                            "        NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC001')),0) RET_REND,  \n" + 
                            "        NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC004')),0) +   \n" + 
                            "        NVL(SUM(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, apr_ret_consecutivo, apr_apo_consecutivo, 'STC003')),0) PENALI \n"+    
                            " FROM   TBRETIROS_OBLIG  \n"+ 
                            " INNER  JOIN TBAPORTES_RETIROS_OBLIG ON APR_RET_CON_PRO_CODIGO      = RET_CON_PRO_CODIGO  \n"+ 
                            "                                   AND APR_RET_CON_NUMERO         = RET_CON_NUMERO  \n"+ 
                            "                                   AND APR_RET_CONSECUTIVO        = RET_CONSECUTIVO  \n"+ 
                            "WHERE APR_RET_CON_PRO_CODIGO  = '"+producto.trim()+"' \n" + 
                            "      AND  RET_REF_ESTADO     = 'SER006' \n" + 
                            "      AND  RET_FECHA_PROCESO BETWEEN TO_DATE('"+ fechaini +"','YYYY-MM-DD') AND TO_DATE('"+ fechafin +"','YYYY-MM-DD') \n";
                if (!vusuario.trim().equals("")) {
                    v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('" + vusuario + "')  ";
                }
                v_dconsulta += "group by ret_con_numero,ret_consecutivo)";
                v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);
                PublicarSI(v_resultadoconsulta, session);
            }
            if (opcion.equals("noproceso")) {
                if (tran.equals("STR001") || tran.equals("STR099")) //retiros - TODAS
                {
                    v_dconsulta = "SELECT  RET_CON_PRO_CODIGO, \n" +
                                           "RET_CON_NUMERO, \n" +
                                           "RET_FECHA_PROCESO, \n" +
                                           "RET_FECHA_EFECTIVA, \n" +
                                           "RET_CONSECUTIVO, \n" +  
                                           "RET_TRANSACCION, \n" +
                                           "'Retiro' TRANSACCION, \n" +
                                           "RET_VALOR_BRUTO, \n" +           
                                           "RET_VALOR_NETO, \n" +       
                                           "INL_MENSAJE, \n" +
                                           "REF_DESCRIPCION \n" +
                                    "FROM   TBRETIROS_OBLIG\n" +
                                    "INNER JOIN tbinterface_logs_oblig  ON  INL_PRODUCTO  = RET_CON_PRO_CODIGO \n" +
                                    "                                   AND to_char(INL_FECHA,'dd-mm-yyyy') = to_char(RET_FECHA_PROCESO,'dd-mm-yyyy') \n" +
                                    "                                   AND INL_DATOS     = TO_NUMBER(RET_CON_NUMERO) ||'-'|| RET_CONSECUTIVO \n" +
                                    "INNER JOIN TBREFERENCIAS       ON REF_CODIGO     = RET_REF_ESTADO " +
                                    "WHERE  RET_CON_PRO_CODIGO  = '"+producto.trim()+"' \n" +
                                    "       AND RET_REF_ESTADO  NOT IN ('SER006','SER001') \n" +
                                    "       AND RET_FECHA_PROCESO BETWEEN TO_DATE('" + fechaini + "','YYYY-MM-DD') AND TO_DATE('"+ fechafin +"','YYYY-MM-DD') " +
                                    "       AND INL_FECHA BETWEEN TO_DATE('"+ fechaini +"','YYYY-MM-DD') AND TO_DATE('"+ fechafin +"','YYYY-MM-DD') \n" + 
                                    "       AND INL_INTERFACE='RE' \n";
                    
                    if (!vusuario.trim().equals("")) {
                        v_dconsulta += "  AND  UPPER(RET_USUARIO) = UPPER('" + vusuario + "')  ";
                    }
                    
                    v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta);
                    PublicarNO(v_resultadoconsulta, session);
                }
                else
                    PublicarNO(null,session);
            }
        }                
        v_Consulta.TBPL_shutDown();

    }


    private void PublicarNO(Vector valor, Vector valor2, Vector valor01, Vector valor11, String fechaini,
                            String fechafin, String unipro, String tran, HttpSession session) {
        valor.trimToSize();
        valor01.trimToSize(); //retiros
        valor11.trimToSize(); //aportes
        valor2.trimToSize(); //ajustes

        out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Reportes de control", "Reportes de control", "", "", true));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR class=\"td11\" borderColor=silver align=center><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Producto</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Contrato</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha proceso</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha efectiva</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>No transacción Tax</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>No de Transacción MFUND</B></FONT></TD>");
        out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Transacción</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Tipo de retiro</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>UNIDAD</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Valor Neto</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Valor Bruto</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Mensaje de Error</B></FONT></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Estado</B></FONT></TD></TR>");
        if (valor.size() != 0)
            if (!valor.elementAt(0).toString().equals("No hay elementos"))
                for (int i = 0; i < valor.size(); i += 12) {
                    out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 1).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 2).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 3).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 4).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 5).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 6).toString() + "</FONT></TD>");
                    out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 7).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 8).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i +
                                                                                                             9).toString())) +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i +
                                                                                                             10).toString())) +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 11).toString() + "</FONT></TD></TR>");
                }
        if (valor01.size() != 0)
            if (!valor01.elementAt(0).toString().equals("No hay elementos"))
                for (int k = 0; k < valor01.size(); k += 6) {
                    out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor01.elementAt(k).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor01.elementAt(k + 3).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor01.elementAt(k + 1).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>RETIROS</FONT></TD>");
                    out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor01.elementAt(k + 2).toString() + "<BR>DATOS: " +
                                valor01.elementAt(k + 4).toString() + "<BR>PASO: " +
                                valor01.elementAt(k + 5).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD></TR>");
                }
        if (valor11.size() != 0)
            if (!valor11.elementAt(0).toString().equals("No hay elementos"))
                for (int k = 0; k < valor11.size(); k += 7) {
                    out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor11.elementAt(k).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor11.elementAt(k + 6).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor11.elementAt(k + 3).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor11.elementAt(k + 1).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>APORTES</FONT></TD>");
                    out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor11.elementAt(k + 2).toString() + "<BR>DATOS: " +
                                valor11.elementAt(k + 4).toString() + "<BR>PASO: " +
                                valor11.elementAt(k + 5).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD></TR>");
                }
        if (valor2.size() != 0)
            if (!valor2.elementAt(0).toString().equals("No hay elementos"))
                for (int k = 0; k < valor2.size(); k += 6) {
                    out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor2.elementAt(k).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor2.elementAt(k + 3).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor2.elementAt(k + 1).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>AJUSTES</FONT></TD>");
                    out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                                valor2.elementAt(k + 2).toString() + "<BR>DATOS: " +
                                valor2.elementAt(k + 4).toString() + "<BR>PASO: " +
                                valor2.elementAt(k + 5).toString() +
                                "</FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</FONT></TD></TR>");
                }
        out.println("</TABLE>");
        out.println("<input type='hidden' id='cadena' name='cadena' value='" + v_nuevaCadena + "'>");
        session.removeValue("s_cadena");
        session.putValue("s_cadena", (java.lang.Object)v_nuevaCadena);
        out.println("<a href='TBPKT_INFORMATIVO.TBPKT_REPORTESCONTROL.TBCS_Reporte_NO_Impresion' target='impresion' style='font-style: normal; text-decoration:none'><font face='Verdana' size='1'><img src='imagenes/PRINTER.gif' alt='Version para Imprimir' border='0'></font></a>");
        //        out.println("<A HREF=\"TBPKT_INFORMATIVO.TBPKT_REPORTESCONTROL.TBCS_Reporte_NO_Impresion?fechaini="+fechaini+"&fechafin="+fechafin+"&unipro="+unipro+"&tran="+tran+"\" target=\"VentanaNueva\"><IMG SRC=\"imagenes/PRINTER.gif\" ALT=\"Versión de impresión\" BORDER=\"0\" align=\"right\"></A>");
        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='" + v_nuevaCadena + "'>");
        out.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
        out.close();
    }

    private void PublicarSI(Vector valor, Vector valor01, Vector valor2, String fechaini, String fechafin,
                            String unipro, HttpSession session) {
        valor.trimToSize();
        valor01.trimToSize();
        valor2.trimToSize();

        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Reportes de control", "Reportes de control", "", "", true));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0><TBODY>");
        out.println("<TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Reporte de retiros procesados</B></FONT></CENTER></TD></TR></TBODY></TABLE>");

        if (!valor.elementAt(0).toString().equals("No hay elementos"))
            if (!valor.elementAt(0).toString().equals("0")) {
                out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de registros procesados</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            valor.elementAt(0).toString() + "</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Neto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor01.elementAt(0).toString())) +
                            "</FONT></TD></TR>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Vlr Bruto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor01.elementAt(1).toString())) +
                            "</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Capital</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor2.elementAt(2).toString())) +
                            "</FONT></TD></TR>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor2.elementAt(3).toString())) +
                            "</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta Contingente</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(5).toString())) +
                            "</FONT></TD></TR>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Retención rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(6).toString())) +
                            "</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penalización</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(7).toString())) +
                            "</FONT></TD></TR>");
                out.println("</TBODY></TABLE>");
                out.println("<input type='hidden' id='cadena' name='cadena' value='" + v_nuevaCadena + "'>");
                session.removeValue("s_cadena");
                session.putValue("s_cadena", (java.lang.Object)v_nuevaCadena);
                out.println("<a href='TBPKT_INFORMATIVO.TBPKT_REPORTESCONTROL.TBCS_Reporte_Retiros_Impresion' target='impresion' style='font-style: normal; text-decoration:none'><font face='Verdana' size='1'><img src='imagenes/PRINTER.gif' alt='Version para Imprimir' border='0'></font></a>");
                //out.println("<A HREF=\"TBPKT_INFORMATIVO.TBPKT_REPORTESCONTROL.TBCS_Reporte_Retiros_Impresion?fechaini="+fechaini+"&fechafin="+fechafin+"&unipro="+unipro+"&
                String vlrneto =
                    NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor01.elementAt(0).toString()));
                String vlrbrto =
                    NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor01.elementAt(1).toString()));
                String vlrcap =
                    NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor2.elementAt(2).toString()));
                String vlrren =
                    NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor2.elementAt(3).toString()));
                session.removeValue("s_vlrneto");
                session.putValue("s_vlrneto", (java.lang.Object)vlrneto);
                session.removeValue("s_vlrbrto");
                session.putValue("s_vlrbrto", (java.lang.Object)vlrbrto);
                session.removeValue("s_vlrcap");
                session.putValue("s_vlrcap", (java.lang.Object)vlrcap);
                session.removeValue("s_vlrren");
                session.putValue("s_vlrren", (java.lang.Object)vlrren);

            } else {
                out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de registros procesados</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Neto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Vlr Bruto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Capital</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta Contingente</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Retención rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penalización</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("</TBODY></TABLE>");
            }
        else {
            out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
            out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de registros procesados</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Neto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Vlr Bruto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Capital</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta Contingente</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Retención rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penalización</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("</TBODY></TABLE>");
        }

        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='" + v_nuevaCadena + "'>");
        out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
        out.close();
    }
    
    private void PublicarSI(Vector valor, HttpSession session) {
        valor.trimToSize();

        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Reportes de control", "Reportes de control", "", "", true));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0><TBODY>");
        out.println("<TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Reporte de retiros procesados</B></FONT></CENTER></TD></TR></TBODY></TABLE>");

        if (!valor.elementAt(0).toString().equals("No hay elementos"))
            if (!valor.elementAt(0).toString().equals("0")) {
                out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de registros procesados</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            valor.elementAt(0).toString() + "</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Neto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(1).toString())) +
                            "</FONT></TD></TR>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Vlr Bruto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(2).toString())) +
                            "</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Capital</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(3).toString())) +
                            "</FONT></TD></TR>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(4).toString())) +
                            "</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta Contingente</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(5).toString())) +
                            "</FONT></TD></TR>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Retención rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(6).toString())) +
                            "</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penalización</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>" +
                            NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(7).toString())) +
                            "</FONT></TD></TR>");
                out.println("</TBODY></TABLE>");
                out.println("<input type='hidden' id='cadena' name='cadena' value='" + v_nuevaCadena + "'>");
                session.removeValue("s_cadena");
                session.putValue("s_cadena", (java.lang.Object)v_nuevaCadena);
                out.println("<a href='TBPKT_INFORMATIVO.TBPKT_REPORTESCONTROL.TBCS_Reporte_Retiros_Impresion' target='impresion' style='font-style: normal; text-decoration:none'><font face='Verdana' size='1'><img src='imagenes/PRINTER.gif' alt='Version para Imprimir' border='0'></font></a>");
                //out.println("<A HREF=\"TBPKT_INFORMATIVO.TBPKT_REPORTESCONTROL.TBCS_Reporte_Retiros_Impresion?fechaini="+fechaini+"&fechafin="+fechafin+"&unipro="+unipro+"&
                String vlrneto =
                    NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(1).toString()));
                String vlrbrto =
                    NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(2).toString()));
                String vlrcap =
                    NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(3).toString()));
                String vlrren =
                    NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(4).toString()));
                session.removeValue("s_vlrneto");
                session.putValue("s_vlrneto", (java.lang.Object)vlrneto);
                session.removeValue("s_vlrbrto");
                session.putValue("s_vlrbrto", (java.lang.Object)vlrbrto);
                session.removeValue("s_vlrcap");
                session.putValue("s_vlrcap", (java.lang.Object)vlrcap);
                session.removeValue("s_vlrren");
                session.putValue("s_vlrren", (java.lang.Object)vlrren);

            } else {
                out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de registros procesados</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Neto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Vlr Bruto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Capital</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta Contingente</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Retención rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penalización</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
                out.println("</TBODY></TABLE>");
            }
        else {
            out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
            out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de registros procesados</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor Neto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Vlr Bruto</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Capital</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta Contingente</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Retención rendimientos</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Penalización</B></FONT></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>0</FONT></TD></TR>");
            out.println("</TBODY></TABLE>");
        }

        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='" + v_nuevaCadena + "'>");
        out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
        out.close();
    }
    
    private void PublicarNO(Vector valor, HttpSession session) {

        out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Reportes de control", "Reportes de control", "", "", true));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\"><TBODY>");
        out.println("<TR class=\"td11\" borderColor=silver align=center><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Producto</B></FONT></TD>" +
                    "<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Contrato</B></FONT></TD>" +
                    "<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha proceso</B></FONT></TD>" +
                    "<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha efectiva</B></FONT></TD>" +
                    "<TD width=\"22%\"><FONT color=white face=verdana size=1><B>No transacción Tax</B></FONT></TD>" +
                    "<TD width=\"22%\"><FONT color=white face=verdana size=1><B>No de Transacción AS400</B></FONT></TD>");
        out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Transacción</B></FONT></TD>" +
                    "<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Valor Bruto</B></FONT></TD>" +
                    "<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Valor Neto</B></FONT></TD>" +
                    "<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Mensaje de Error</B></FONT></TD>" +
                    "<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Estado</B></FONT></TD></TR>");      
        if (valor!=null && valor.size() != 0)
        {
            valor.trimToSize();
            if (!valor.elementAt(0).toString().equals("No hay elementos"))
                for (int i = 0; i < valor.size(); i +=11) {
                    out.println("<TR bgColor=white borderColor=silver><TD><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i).toString() +
                                "</FONT></TD><TD><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 1).toString() +
                                "</FONT></TD><TD nowrap><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 2).toString().substring(0, 10) +
                                "</FONT></TD><TD nowrap><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 3).toString().substring(0, 10) +
                                "</FONT></TD><TD><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 4).toString() +
                                "</FONT></TD><TD><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 5).toString() +
                                "</FONT></TD><TD><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 6).toString() + "</FONT></TD>");
                    out.println("<TD><FONT color=black face=verdana size=1>" +
                                NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i + 7).toString())) +
                                "</FONT></TD><TD><FONT color=black face=verdana size=1>" +
                                NumberFormat.getCurrencyInstance().format(Double.parseDouble(valor.elementAt(i + 8).toString())) +
                                "</FONT></TD><TD nowrap><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 9).toString() +
                                "</FONT></TD><TD nowrap><FONT color=black face=verdana size=1>" +
                                valor.elementAt(i + 10).toString() +"</FONT></TD></TR>");

                }
        }
        out.println("</TABLE>");
        out.println("<input type='hidden' id='cadena' name='cadena' value='" + v_nuevaCadena + "'>");
        session.removeValue("s_cadena");
        session.putValue("s_cadena", (java.lang.Object)v_nuevaCadena);
        out.println("<a href='TBPKT_INFORMATIVO.TBPKT_REPORTESCONTROL.TBCS_Reporte_NO_Impresion' target='impresion' style='font-style: normal; text-decoration:none'><font face='Verdana' size='1'><img src='imagenes/PRINTER.gif' alt='Version para Imprimir' border='0'></font></a>");
        //        out.println("<A HREF=\"TBPKT_INFORMATIVO.TBPKT_REPORTESCONTROL.TBCS_Reporte_NO_Impresion?fechaini="+fechaini+"&fechafin="+fechafin+"&unipro="+unipro+"&tran="+tran+"\" target=\"VentanaNueva\"><IMG SRC=\"imagenes/PRINTER.gif\" ALT=\"Versión de impresión\" BORDER=\"0\" align=\"right\"></A>");
        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='" + v_nuevaCadena + "'>");
        out.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
        out.close();
    }

    /**
     * Get Servlet information
     * @return java.lang.String
     */
    public String getServletInfo() {
        return "TBPKT_REPORTESCONTROL.TBCS_Reportes_Control Information";
    }
} 

