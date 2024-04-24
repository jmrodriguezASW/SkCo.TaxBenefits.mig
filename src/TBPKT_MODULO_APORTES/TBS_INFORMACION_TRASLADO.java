package TBPKT_MODULO_APORTES;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.*;

/**
*Este servlet hace se encraga de cargar loa aportes de traslado interno
*hacia tax
*/

public class TBS_INFORMACION_TRASLADO extends HttpServlet
{

  public void init(ServletConfig config) throws ServletException
  {super.init(config);}
  //*************************************************************************************************************************
  public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
    throws ServletException, IOException
  {
    PrintWriter salida  = new PrintWriter (respuesta.getOutputStream());
    /*Variable agregada por APC para manejar el tamaño de los arreglos de una manera centralizada*/
    //static final int MAX = 900;
    int MAX = 900;
      Connection v_conexion_taxb    =   null;
    /****************************************************************************************/
    try
    {
      //seguridad
      HttpSession session = peticion.getSession(true);
      if (session == null)
        session         = peticion.getSession(true);
      respuesta.setContentType("text/html");
      /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
      // respuesta.setHeader("Pragma", "No-Cache");
      // respuesta.setDateHeader("Expires", 0);
      //   respuesta.setHeader("Cache-Control","no-Cache");
      String v_contrato                      = "", v_producto = "", v_usuario  = "", v_unidad2 = "";
      String v_tipousu                       = "", v_idworker = "";
      String parametros[]                    = new String[8];
      String cadena2                         = peticion.getParameter("cadena");
      String nuevaCadena                     = cadena2;
      String ip_tax                          = peticion.getRemoteAddr();
      TBCL_Seguridad Seguridad               = new TBCL_Seguridad();
      //valido la veracidad del producto y contrato enviados por pipeline
      parametros                             = Seguridad.TBFL_Seguridad(cadena2, salida, ip_tax);
      v_contrato                             = parametros[0];
      v_producto                             = parametros[1];
      v_usuario                              = parametros[2];
      v_unidad2                              = parametros[3];
      v_tipousu                              = parametros[4];
      v_idworker                             = parametros[5];
      Class.forName("oracle.jdbc.driver.OracleDriver");
      TBCL_HTML   parametros_requeridos      = new TBCL_HTML();
      TBCL_HTML   hoja_error                 = new TBCL_HTML();
      TBCL_HTML   publica_traslado           = new TBCL_HTML();
      TBCL_HTML   compara_totales            = new TBCL_HTML();
      TBCL_HTML   informacion_final          = new TBCL_HTML();
      TBCL_HTML   error_carga                = new TBCL_HTML();
      TBCL_HTML   nombres                    = new TBCL_HTML();
      TBCL_Validacion i_valusu               = new TBCL_Validacion ();
      String[] v_valusu                      = new String[5];
      v_valusu                               = i_valusu.TBFL_ValidarUsuario();
        v_conexion_taxb =   DataSourceWrapper.getInstance().getConnection();
      String msj                             = " ";
      //valido la veracidad del producto y contrato enviados por pipeline
      String  nombre_producto                = peticion.getParameter("nom_producto");
      String  numero_contrato                = peticion.getParameter("num_contrato");
      String  usuario                        = peticion.getParameter("usuario");
      if(nombre_producto != null)
      {
        v_producto = "x";
        v_contrato = "x";
      }
      //------------------------------------------------------------------pagina numero 1
      //si los parametros son validos, dibujo hojabase y devuelvo parametros x y x
      if(parametros_requeridos.TBPL_Parametros_Requeridos(v_producto,v_contrato))
      {
        //declaro vectores que me mantendran la informacion para luego publicarla
        //con todos los datos a mostrar, los paso a un void que me los pu lique
        String fecha_e[]     = new String[MAX];
        double c[]           = new double[MAX];
        double cc[]          = new double[MAX];
        double rtos[]        = new double[MAX];
        String nomb_afp[]    = new String[MAX];
        int[] consecutivos   = new int[MAX];
        int total_aportes    = 0;
        String select8i_0 = "SELECT "+
                      "TO_CHAR(APO_FECHA_EFECTIVA,'RRRR-MM-DD'),"+
                      "APO_CAPITAL,"+
                      "APO_RENDIMIENTOS,"+
                      "APO_CUENTA_CONTINGENTE,"+
                      "APO_CONSECUTIVO, "+
                      "AFP_DESCRIPCION "+
                      "FROM TBAPORTES,TBADM_FONDOS_PENSIONES "+
                      "WHERE "+
                      "APO_CON_PRO_CODIGO           = ? "+
                      "AND APO_CON_NUMERO           = ? "+
                      "AND APO_APORTE_TRASLADO      = 'S' "+
                      "AND APO_INFORMACION_TRASLADO = 'N' "+
                      "AND APO_AFP_CODIGO           = AFP_CODIGO "+
                      "AND AFP_REF_TIPO             = 'STA001' "+
                      "ORDER BY APO_FECHA_EFECTIVA DESC";
        PreparedStatement t_st8i = v_conexion_taxb.prepareStatement(select8i_0);
        t_st8i.setString(1,v_producto);
        t_st8i.setString(2,v_contrato);
        ResultSet t_rs9i              = t_st8i.executeQuery();
        while(t_rs9i.next())
        {
          fecha_e[total_aportes]      = t_rs9i.getString(1);
          c[total_aportes]            = t_rs9i.getDouble(2);
          rtos[total_aportes]         = t_rs9i.getDouble(3);
          cc[total_aportes]           = t_rs9i.getDouble(4);
          consecutivos[total_aportes] = t_rs9i.getInt(5);
          nomb_afp[total_aportes]     = t_rs9i.getString(6);
          total_aportes++;
        }
        publica_traslado.TBPL_Datos_Informacion_Traslado(v_producto, v_contrato, fecha_e,
                                                         cc,c,rtos,total_aportes,salida,
                                                         v_usuario,nomb_afp,consecutivos,
                                                         nombres.TBPL_Nombres(v_producto,v_contrato),
                                                         nuevaCadena);
        t_rs9i.close();
        t_st8i.close();
        return;
      }
      else if ((!parametros_requeridos.TBPL_Parametros_Requeridos(nombre_producto,numero_contrato))&&
               ( nombre_producto == null)&&
               ( numero_contrato == null))
      {
        STBCL_GenerarBaseHTML plantilla2 = new STBCL_GenerarBaseHTML();
        String msj2 = "EL CONTRATO "+v_contrato+" NO EXISTE EN EL SISTEMA.";
       salida.println(plantilla2.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS INTERNOS",
                                         "TBPKT_MODULO_APORTES.TBS_INFORMACION_TRASLADO","<font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><CENTER>"+msj2+"</CEBTER></font></b>",false));
        salida.println("<BR><BR>");
        salida.println("<center><input type='button' value='Aceptar' Onclick='history.go(-1);' ></center>");
        salida.println(plantilla2.TBFL_PIE);
        salida.flush();
        return;
      }
      //---------------pagina numero 2--manejo de parametros errados y enviados por pipeline
      else if ((!parametros_requeridos.TBPL_Parametros_Requeridos(nombre_producto,numero_contrato))&&
               (!nombre_producto.equals("x"))&&
               (!numero_contrato.equals("x")))
      {
        hoja_error.TBPL_Hoja_Error(nombre_producto,numero_contrato,salida,0,"TBS_INFORMACION_TRASLADO",
                          "INFORMACION DE TRASLADOS INTERNOS");
        return;
      }
      //---------------------------------------------------------------pagina numero 3
      else if((nombre_producto.equals("x"))&&(numero_contrato.equals("x")))
        //por esta parte del doget me dispongo a buscar la informacion del aporte que
        //haga match con el retiro, y de igual forma buscarle los hijos e insertarlos
        //teniendo en cuenta lo necesario a la hora de insertar
      {
        //variable que indica si se debe realizar carga, en el caso que el aporte tenga
        //retiros asociados
        boolean carga_imposible=false;
        //variable que indica si la carga no se debe realizar debido a incongruencia
        //en los datos totales de los hijos con la del padre
        boolean totales_invalidos=false;
        //variable que indica que para el aporte seleccionado existe o no un retiro
        //asociado a el(MATCH de informacion)
        boolean no_existe_retiro=false;
        //variable que indica que para un aporte que tiene un retiro asociado,
        //se encontraron o no hijos, para publicacion
        boolean aporte_con_hijos=false;
        //variable que indica la posible falla del aporte antes seleccionado por el usuario
        boolean aporte_hallado=false;
        //realizar captura de información para el aporte elegido en pantalla
        String  f_producto    = peticion.getParameter("f_producto");
        String  f_contrato    = peticion.getParameter("f_contrato");
        String  user          = peticion.getParameter("usuario");
        String  conse         = peticion.getParameter("posicion");
        Integer c_temp        = new Integer(conse);
        int consecutivo       = c_temp.intValue();
        String select8i_1 = "SELECT "+
                      "TO_CHAR(APO_FECHA_EFECTIVA,'RRRR-MM-DD'),"+
                      "APO_CAPITAL,"+
                      "APO_RENDIMIENTOS,"+
                      "APO_CUENTA_CONTINGENTE,"+
                      "APO_VALOR_UNIDAD,"+
                      "TO_CHAR(APO_FECHA_PROCESO,'RRRR-MM-DD'),"+
                      "APO_COT_REF_TRANSACCION,"+
                      "APO_COT_REF_TIPO_TRANSACCION,"+
                      "APO_COT_REF_CLASE_TRANSACCION,"+
                      "APO_APORTE_CERTIFICADO,"+
                      "APO_REF_CONDICION_SKA,"+
                      "APO_DETALLE_CONDICION_SKA,"+
                      "APO_TRANSACCION,"+
                      "APO_EMP_GRUPO,"+
                      "APO_AFP_CODIGO,"+
                      "AFP_DESCRIPCION,"+
                      "APO_INFORMACION_TRASLADO,"+
                      "APO_APORTE_TRASLADO "+
                      "FROM TBAPORTES,TBADM_FONDOS_PENSIONES "+
                      "WHERE APO_CON_PRO_CODIGO = ? "+
                      "AND APO_CON_NUMERO     = ? "+
                      "AND APO_CONSECUTIVO    = ? "+
                      "AND APO_AFP_CODIGO         = AFP_CODIGO ";
        String producto_ret_c = new String();
        String contrato_ret_c = new String();
        int ret_consecutivo= 0;
        String estado_ret_c= " ";
        String fecha_e     = " ";
        double k           = 0;
        double cc          = 0;
        double rtos        = 0;
        double v_u         = 0;
        String fecha_p     = " ";
        String transaccion = " ";
        String t_t         = " ";
        String c_t         = " ";
        String certificado = " ";
        String condicion   = " ";
        String detalle     = " ";
        String trs         = " ";
        String empresa     = " ";
        String afp         = " ";
        String nomb_afp    = " ";
        String i_traslado  = " ";
        String a_traslado  = " ";
        //
        PreparedStatement t_st8i = v_conexion_taxb.prepareStatement(select8i_1);
        t_st8i.setString(1,f_producto);
        t_st8i.setString(2,f_contrato);
        t_st8i.setInt(3,consecutivo);
        //salida.println("Query para el aporte elegido en pantalla es: "+select8i_1+", con los parametros:"+f_producto+","+f_contrato+","+consecutivo+"<br>");
        ResultSet t_rs8i = t_st8i.executeQuery();
        //capturar informaciond del aporte padre
        while(t_rs8i.next())
        {
          aporte_hallado = true;
          fecha_e        = t_rs8i.getString(1);
          k              = t_rs8i.getDouble(2);
          rtos           = t_rs8i.getDouble(3);
          cc             = t_rs8i.getDouble(4);
          v_u            = t_rs8i.getDouble(5);
          fecha_p        = t_rs8i.getString(6);
          transaccion    = t_rs8i.getString(7);
          t_t            = t_rs8i.getString(8);
          c_t            = t_rs8i.getString(9);
          certificado    = t_rs8i.getString(10);
          condicion      = t_rs8i.getString(11);
          detalle        = t_rs8i.getString(12);
          trs            = t_rs8i.getString(13);
          empresa        = t_rs8i.getString(14);
          afp            = t_rs8i.getString(15);
          nomb_afp       = t_rs8i.getString(16);
          i_traslado     = t_rs8i.getString(17);
          a_traslado     = t_rs8i.getString(18);
        } //while
        //
        t_rs8i.close();
        t_st8i.close();
        //si el aporte seleccionado fue hallado se sigue con procedimiento,
        //en caso contrario pagina de error
        //calcular el porcentaje máximo para actualizar disponibilidad de cada hijo
        CallableStatement t_cst8i_12 = v_conexion_taxb.prepareCall("{ call TBPBD_Maximo_Porcentaje_Papa(?,?,?,?) }");
        t_cst8i_12.registerOutParameter(4,Types.NUMERIC);
        t_cst8i_12.setString(1,f_producto);
        t_cst8i_12.setString(2,f_contrato);
        t_cst8i_12.setInt (3,consecutivo);
        t_cst8i_12.execute();
        int p_maximo = t_cst8i_12.getInt(4);
        /*salida.println ("{ call TBPBD_Maximo_Porcentaje_Papa(?,?,?,?) },"+f_producto+","+f_contrato+","+consecutivo+" retorno:"+p_maximo+"<br>");*/
        t_cst8i_12.close();
        if(aporte_hallado)
        {
          //continuar con la definicion de variables necesarias para insertar los hijos
          //variables que mantendrán los valores totales de los hijos con el fin de
          //enviarlos a un void que haga la validacion de ellos contra los del retiro padre
          double capital_hijo       = 0;
          double c_chijo            = 0;
          double rtos_hijo          = 0;
          //DEFINO VABLE QUE MANTENGA LA INFORMACION DE LOS CONSECUTIVOS HIJOS DEL APORTE TO, para mostrar posteriormente
          String apo_afp[]          = new String[MAX];
          String apo_traslado[]     = new String[MAX];
          String apo_i_traslado[]   = new String[MAX];
          String apo_producto[]     = new String[MAX];
          String apo_contrato[]     = new String[MAX];
          int apr_apo_consecutivo[] = new int[MAX];
          double apr_capital[]      = new double[MAX];
          double apr_rto_1[]        = new double[MAX];
          double apr_rto_2[]        = new double[MAX];
          double apr_rto[]          = new double[MAX];
          double saldo_c_hijo[]     = new double[MAX];
          double saldo_rtos_hijo[]  = new double[MAX];
          double c_ch[]             = new double[MAX];
          double saldo_c_ch[]       = new double[MAX];
          double saldo_num_u_hijo[] = new double[MAX];
          double num_unidades_hijo[]= new double[MAX];
          int porcentaje_rto_hijo[] = new int[MAX];
          String fa_hijo[]          = new String[MAX];
          String tiene_beneficio    = " ";
          String a_beneficio_hijo   = "N";
          String a_razon_ben_hijo   = " ";
          //HOY tipo .util
          java.util.Date fb_hijo_1  = new java.util.Date();
          //HOY tipo .sql
          java.sql.Date fb_hijo_2   = new java.sql.Date(1);
          //convierto valor de unidad a double
          Double v_unidad     = new Double(v_u);
          double valor_u_papa = v_unidad.doubleValue();
          //valido si el consecutivo tiene retiros asociados y vigentes
          //verifico si el aporte elegido tiene retiros asociados, si es asi significa página de Error
          CallableStatement t_cst8i_13 = v_conexion_taxb.prepareCall("{ call TBPBD_Valida_Retiros_por_Apt(?,?,?,?) }");
//          salida.println("{ call TBPBD_Valida_Retiros_por_Apt(?,?,?,?) },"+f_producto+","+f_contrato+","+consecutivo+"<br>");
          t_cst8i_13.registerOutParameter(4,Types.VARCHAR);
          t_cst8i_13.setString(1,f_producto);
          t_cst8i_13.setString(2,f_contrato);
          t_cst8i_13.setInt(3,consecutivo);
          t_cst8i_13.execute();
          String tieneretiros = t_cst8i_13.getString(4);
          t_cst8i_13.close();
          if(tieneretiros.equals("v"))
            carga_imposible=true;
          int total_hijos = 0;
          //convertir capital, rtos y c_c papa a longs
          Double c_cp         = new Double(cc);
          double c_cpapa      = c_cp.doubleValue();
          Double ca           = new Double(k);
          double capital_papa = ca.doubleValue();
          Double rendis       = new Double(rtos);
          double rtos_papa    = rendis.doubleValue();
          //definir variable que contendra el valor real del aporte, capital del papa
          //mas rtos del papa
          double valor_aporte = 0;
          valor_aporte = capital_papa + rtos_papa;
          if(!carga_imposible)
          {
            //con estos datos se realiza busqueda del ret_consecutivo, en caso de existir
            //buscar los aportes afectados por el retiro padre(en caso de no existir
            //significa pagina de error) y se pasan como hijos del nuevo aporte que
            //entró como TO, teniendo en cuenta ....
            CallableStatement t_cst8i_1 = v_conexion_taxb.prepareCall("{ call TBPBD_RETIRO_TO11(?,?,?,?,?,?,?,?,?,?) }");
            t_cst8i_1.registerOutParameter(7,Types.NUMERIC);
            t_cst8i_1.registerOutParameter(8,Types.VARCHAR);
            t_cst8i_1.registerOutParameter(9,Types.VARCHAR);
            t_cst8i_1.registerOutParameter(10,Types.VARCHAR);
            t_cst8i_1.setString(1,fecha_p);
            t_cst8i_1.setDouble(2,capital_papa);
            t_cst8i_1.setDouble(3,rtos_papa);
            t_cst8i_1.setDouble(4,c_cpapa);
            t_cst8i_1.setString(5,f_producto);
            t_cst8i_1.setString(6,f_contrato);
            t_cst8i_1.execute();
            ret_consecutivo    = t_cst8i_1.getInt(7);
            estado_ret_c       = t_cst8i_1.getString(8);
            producto_ret_c     = t_cst8i_1.getString(9);
            contrato_ret_c     = t_cst8i_1.getString(10);
            /*salida.println("{ call TBPBD_RETIRO_TO11(?,?,?,?,?,?,?,?,?,?) },"+fecha_p+","+capital_papa+","+rtos_papa+","+c_cpapa+","+f_producto+","+f_contrato+",retornos:"+ret_consecutivo +","+estado_ret_c+","+producto_ret_c+","+contrato_ret_c+"<br>");*/
            t_cst8i_1.close();
            //
            if(estado_ret_c.equalsIgnoreCase("0"))
              no_existe_retiro = true;
            //calcular el porcentaje máximo para actualizar disponibilidad de cada hijo
            /*
             CallableStatement t_cst8i_12 = v_conexion_taxb.prepareCall("{ call TBPBD_Maximo_Porcentaje(?,?,?,?) }");
             t_cst8i_12.registerOutParameter(3,Types.NUMERIC);
             t_cst8i_12.setString(1,f_producto);
             t_cst8i_12.setString(2,f_contrato);
             t_cst8i_12.setInt (2,consecutivo)
             t_cst8i_12.execute();
             int p_maximo = t_cst8i_12.getInt(3);
             t_cst8i_12.close();
            */
            //
            if(!estado_ret_c.equalsIgnoreCase("0"))
            {
              //quiere decir que existe un retiro para ese aporte TO interno
              //buscar los Aportes internos y pasarlos como hijos sobre el nuevo
              //aporte de TO interno
              String select8i_2="SELECT "+
                            "APO_CON_PRO_CODIGO "+
                            ",APO_CON_NUMERO "+
                            ",APO_AFP_CODIGO "+
                            ",APO_APORTE_TRASLADO "+
                            ",APO_INFORMACION_TRASLADO "+
                            ",APR_APO_CONSECUTIVO "+
                            ",APR_CAPITAL "+
                            ",TB_FCALCULAR_RENDIMIENTOS( APR_RET_CON_PRO_CODIGO,APR_RET_CON_NUMERO ,APR_RET_CONSECUTIVO,APR_APO_CONSECUTIVO) APR_RENDIMIENTOS "+
              //            ",APR_RENDIMIENTOS "+
                            ",APR_RENDIMIENTOS_P "+
                            ",TO_CHAR(APO_FECHA_APORTE,'RRRR-MM-DD') "+
                            "FROM "+
                            "TBAPORTES "+
                            ",TBAPORTES_RETIROS "+
                            "WHERE "+
                            "APR_RET_CON_PRO_CODIGO   	  = APO_CON_PRO_CODIGO "+
                            "AND APR_RET_CON_NUMERO       = APO_CON_NUMERO "+
                  			    "AND APR_APO_CONSECUTIVO      = APO_CONSECUTIVO "+
                            "AND APR_RET_CONSECUTIVO      = ? "+
                            "ORDER BY APR_APO_CONSECUTIVO ";
              PreparedStatement t_pst8i = v_conexion_taxb.prepareStatement(select8i_2);
              t_pst8i.setInt(1,ret_consecutivo);
              t_rs8i = t_pst8i.executeQuery();
              salida.println("Query para Aportes internos y pasarlos como hijos:"+ select8i_2+"<br>");             
              String afp_hijo = " ";
              
              String vconsulta = "SELECT APO_CONSECUTIVO_SQ.NEXTVAL FROM DUAL";
              while(t_rs8i.next())
              {
                salida.println("Total hijos="+total_hijos+"<br>");
                a_beneficio_hijo = "N";
                aporte_con_hijos=true;
                //aqui se almacenan los hijos del aporte TO
                apo_producto[total_hijos]        = t_rs8i.getString(1);
                apo_contrato[total_hijos]        = t_rs8i.getString(2);
                apo_afp[total_hijos]             = t_rs8i.getString(3);
                apo_traslado[total_hijos]        = t_rs8i.getString(4);
                apo_i_traslado[total_hijos]      = t_rs8i.getString(5);
                apr_apo_consecutivo[total_hijos] = t_rs8i.getInt(6);
                apr_capital[total_hijos]         = t_rs8i.getDouble(7);
                apr_rto_1[total_hijos]           = t_rs8i.getDouble(8);
                apr_rto_2[total_hijos]           = t_rs8i.getDouble(9);
                fa_hijo[total_hijos]             = t_rs8i.getString(10);
                String a_informacion_to_hijo     = " ";
                String a_traslado_hijo           = " ";
                //relizar validación sobre informacion de traslado, aporte de traslado
                //y afp de cada hijo dependiendo de dichos indicadores de cada hijo
                if(apo_traslado[total_hijos].equals("S")&&apo_i_traslado[total_hijos].equals("N"))
                {
                  a_informacion_to_hijo = "S"; //apo_i_traslado[total_hijos];
                  a_traslado_hijo       = apo_traslado[total_hijos];
                  afp_hijo              = apo_afp[total_hijos];
                }
                else
                {
                  a_informacion_to_hijo = "N";
                  a_traslado_hijo       = "N";
                  afp_hijo              = afp;
                }
                //calcular rtos de cada hijo -- 2002/05/09 Se quitó apr_rto_[total_hijos] (rendimientos penalizados), esto
                //solo es informativo y no se debe usar para el cálculo de los rendimientos del hijo
                apr_rto[total_hijos]             = apr_rto_1[total_hijos]; // + apr_rto_2[total_hijos];
                //calculo número de unidades de cada hijo
                //definir variable para nuevo número de unidades del hijo
                if(valor_u_papa==0)
                  valor_u_papa=1;
                num_unidades_hijo[total_hijos]  = (apr_capital[total_hijos]+apr_rto[total_hijos])/valor_u_papa;
                String a_certificado            = "S";
                String a_cargue_hijo            = "N";
                String a_estado_hijo            = "SEA001";
                saldo_c_hijo[total_hijos]       = apr_capital[total_hijos];
                saldo_rtos_hijo[total_hijos]    = apr_rto[total_hijos];
                saldo_num_u_hijo[total_hijos]   = num_unidades_hijo[total_hijos];
                //calculo el valor de la cuenta contingente de cada hijo
                CallableStatement t_cst8i_2 = v_conexion_taxb.prepareCall("{ call TBPBD_Cuenta_CHijo(?,?,?,?,?) }");
                t_cst8i_2.registerOutParameter(5,Types.NUMERIC);
                t_cst8i_2.setString(1,apo_producto[total_hijos]);
                t_cst8i_2.setString(2,apo_contrato[total_hijos]);
                t_cst8i_2.setInt(3,ret_consecutivo);
                t_cst8i_2.setInt(4,apr_apo_consecutivo[total_hijos]);
                t_cst8i_2.execute();
                c_ch[total_hijos]        = t_cst8i_2.getDouble(5);
                saldo_c_ch[total_hijos]  = c_ch[total_hijos];
                //calcular porcentaje de rtos de cada hijo
                porcentaje_rto_hijo[total_hijos]  = p_maximo;
                /*
                if(apr_capital[total_hijos]!=0)
                porcentaje_rto_hijo[total_hijos]=0;
                */
                t_cst8i_2.close();
                //calcular variables involucradas con el beneficio tributario de cada hijo:
                //aporte_beneficio, fecha_aporte, razon_beneficio, fecha_beneficio
                CallableStatement t_cst8i_3 = v_conexion_taxb.prepareCall("{ call TBPBD_Tiene_Beneficio_Externos(?,?,?,?,?) }");
                t_cst8i_3.registerOutParameter(4,Types.VARCHAR);
                t_cst8i_3.registerOutParameter(5,Types.VARCHAR);
                t_cst8i_3.setString(1,apo_producto[total_hijos]);
                t_cst8i_3.setString(2,apo_contrato[total_hijos]);
                t_cst8i_3.setInt(3,apr_apo_consecutivo[total_hijos]);
                t_cst8i_3.execute();
                tiene_beneficio = t_cst8i_3.getString(4);
                String estado_b = t_cst8i_3.getString(5);
                t_cst8i_3.close();
                if(tiene_beneficio.equalsIgnoreCase("v"))
                  a_beneficio_hijo = "S";
                //llamo procedimiento que seleccione la inofrmacion de dicho aporte
                //necesaria para que combinando esta informacion con la del papa pueda insertar el nuevo hijo
                //los valores para el nuevo aporte hijo a insertar son
                //llamado a sp
                double vconapo = 0;
                  Statement t_st = v_conexion_taxb.createStatement();
                  ResultSet vresconsec = t_st.executeQuery(vconsulta);
                while( vresconsec.next())
                {
                 vconapo = vresconsec.getDouble(1);
                }
                vresconsec.close();
                  t_st.close(); 
                CallableStatement t_cst8i_6 = v_conexion_taxb.prepareCall("{ call TBPBD_Aporte_TO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
                t_cst8i_6.registerOutParameter(37,Types.VARCHAR);
                t_cst8i_6.setString(1,f_producto);
                //System.out.println("producto "+f_producto);
                t_cst8i_6.setString(2,f_contrato);
                //System.out.println("contre"+f_contrato);
                t_cst8i_6.setDouble(3,vconapo);
                //t_cst8i_6.setInt(3,apr_apo_consecutivo[total_hijos]);
                // System.out.println("apr_apo_consecutivo "+apr_apo_consecutivo[total_hijos]);
                t_cst8i_6.setString(4,fecha_p);
                // System.out.println("fecha_p "+fecha_p);
                t_cst8i_6.setString(5,fecha_e);
                //System.out.println("fecha_e "+fecha_e);
                t_cst8i_6.setString(6,transaccion);
                //System.out.println("transaccion "+transaccion);
                t_cst8i_6.setString(7,t_t);
                // System.out.println("t_t "+t_t);
                t_cst8i_6.setString(8,c_t);
                // System.out.println("C_t "+c_t);
                t_cst8i_6.setDouble(9,apr_capital[total_hijos]);
                // System.out.println("apr_capital[total_hijos] "+apr_capital[total_hijos]);
                t_cst8i_6.setDouble(10,apr_rto[total_hijos]);
                // System.out.println("apr_rto[total_hijos] "+apr_rto[total_hijos]);
                t_cst8i_6.setDouble(11,c_ch[total_hijos]);
                // System.out.println("c_ch[total_hijos] "+c_ch[total_hijos]);
                t_cst8i_6.setDouble(12,valor_u_papa);
                // System.out.println("valor_u_papa "+valor_u_papa);
                t_cst8i_6.setDouble(13,num_unidades_hijo[total_hijos]);
                // System.out.println("num_unidades_hijo[total_hijos]"+num_unidades_hijo[total_hijos]);
                t_cst8i_6.setString(14,a_certificado);
                // System.out.println("a_certificado "+a_certificado);
                t_cst8i_6.setString(15,a_traslado_hijo);
                // System.out.println("a_traslado_hijo "+a_traslado_hijo);
                t_cst8i_6.setString(16,a_beneficio_hijo);
                // System.out.println("a_traslado_hijo "+a_traslado_hijo);
                t_cst8i_6.setString(17,a_cargue_hijo);
                // System.out.println("a_cargue_hijo"+a_cargue_hijo);
                t_cst8i_6.setString(18,a_informacion_to_hijo);
                // System.out.println("a_informacion_to_hijo"+a_informacion_to_hijo);
                t_cst8i_6.setString(19,a_estado_hijo);
                // System.out.println("a_estado_hijo"+a_estado_hijo);
                t_cst8i_6.setDouble(20,apr_capital[total_hijos]);
                //System.out.println("apr_capital[total_hijos]"+apr_capital[total_hijos]);
                t_cst8i_6.setDouble(21,0);
                //System.out.println("ES 0");
                t_cst8i_6.setInt(22,0);
                //System.out.println("ES 0");
                t_cst8i_6.setDouble(23,c_ch[total_hijos]);
                //System.out.println("c_ch[total_hijos] "+c_ch[total_hijos] );
                t_cst8i_6.setDouble(24,num_unidades_hijo[total_hijos]);
                //System.out.println("num_unidades_hijo[total_hijos] "+num_unidades_hijo[total_hijos] );
                t_cst8i_6.setString(25,user);
                //System.out.println("user "+user);
                t_cst8i_6.setString(26,fa_hijo[total_hijos]);
                //System.out.println("fa_hijo[total_hijos] "+fa_hijo[total_hijos]);
                t_cst8i_6.setString(27,a_razon_ben_hijo);
                //System.out.println("a_razon_ben_hijo "+a_razon_ben_hijo);
                /*
                if(a_beneficio_hijo.equals("S"))
                    t_cst8i_6.setDate(28,(java.sql.Date)fb_hijo_1);
                else if(a_beneficio_hijo.equals("N"))
                   t_cst8i_6.setDate(28,null);
                */
                //sino la fecha es nulll
                t_cst8i_6.setString(28,a_beneficio_hijo);
                //System.out.println("a_beneficio_hijo "+a_beneficio_hijo);
                t_cst8i_6.setString(29,condicion);
                //System.out.println("condicion "+condicion);
                t_cst8i_6.setString(30,detalle);
                //System.out.println("detalle "+detalle);
                t_cst8i_6.setString(31,trs);
                //System.out.println("trs "+trs);
                t_cst8i_6.setString(32,empresa);
                //System.out.println("empresa "+empresa);
                t_cst8i_6.setString(33,afp_hijo);
                //System.out.println("afp_hijoa "+afp_hijo);
                t_cst8i_6.setInt(34,consecutivo);
                //System.out.println("consecutivo "+consecutivo);
                t_cst8i_6.setString(35,apo_contrato[total_hijos]);
                //System.out.println("apo_contrato[total_hijos] "+apo_contrato[total_hijos]);
                t_cst8i_6.setString(36,apo_producto[total_hijos]);
                //System.out.println("apo_producto[total_hijos] "+apo_producto[total_hijos]);
                t_cst8i_6.executeUpdate();
                String estado = t_cst8i_6.getString(37);
                t_cst8i_6.close();
                salida.println("{ call TBPBD_Aporte_TO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }, parametros:"+f_producto+","+f_contrato+","+vconapo+","+fecha_p+","+fecha_e+","+transaccion+","+t_t+","+c_t+","+apr_capital[total_hijos]+","+apr_rto[total_hijos]+","+c_ch[total_hijos]+","+valor_u_papa+","+num_unidades_hijo[total_hijos]+","+user+","+fa_hijo[total_hijos]+","+
                a_razon_ben_hijo+","+condicion+","+detalle+","+trs+","+empresa+","+afp_hijo+","+consecutivo+","+apo_contrato[total_hijos]+","+apo_producto[total_hijos]+"RETORNO:"+estado+"<br>");
                //ACTUALIZO DISPONIBILIDADES DE CADA HIJO
                double valor_d = (apr_capital[total_hijos]*p_maximo)/100;
                CallableStatement t_cst8i_8 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Disponibilidad_E(?,?,?,?,?,?,?) }");
                t_cst8i_8.registerOutParameter(7,Types.VARCHAR);
                t_cst8i_8.setString(1,f_producto);//apo_producto[total_hijos]
                // System.out.println("f_product"+f_producto);
                t_cst8i_8.setString(2,f_contrato);//apo_contrato[total_hijos]
                // System.out.println("f_contrato"+f_contrato);
                t_cst8i_8.setDouble(3,vconapo);
                //t_cst8i_8.setInt(3,apr_apo_consecutivo[total_hijos]);
                // System.out.println("apr_apo_consecutivo[total_hijos] "+apr_apo_consecutivo[total_hijos]);
                t_cst8i_8.setString(4,fa_hijo[total_hijos]);
                // System.out.println("fa_hijo[total_hijos] "+fa_hijo[total_hijos]);
                t_cst8i_8.setInt(5,porcentaje_rto_hijo[total_hijos]);
                //System.out.println("porcentaje_rto_hijo[total_hijos] "+porcentaje_rto_hijo[total_hijos]);
                t_cst8i_8.setDouble(6,valor_d);
                //System.out.println("valor_d "+valor_d);
                t_cst8i_8.execute();
                String estado_d = t_cst8i_8.getString(7);
                t_cst8i_8.close();
                //
                capital_hijo += apr_capital[total_hijos];
                c_chijo      += c_ch[total_hijos];
                rtos_hijo    += apr_rto[total_hijos];
                total_hijos++;
              } //while de hijos hallados
              //definir variable que indican si la carga no se debe realizar debido a
              //incongruencia en los datos totales de los hijos con la del padre,
              //validar informacion del padre con los totales de los hijos
              t_rs8i.close();
              t_pst8i.close();
                
              totales_invalidos = compara_totales.TBPL_Compara_Totales(capital_hijo,rtos_hijo,capital_papa,rtos_papa);
            } // si (!estado_ret_c.equalsIgnoreCase("0"))
          } //si la carga es posible
          //preparar pag de respuesta en caso positivo o negativo
          if(carga_imposible||!totales_invalidos||no_existe_retiro||!aporte_con_hijos)
          {
            v_conexion_taxb.rollback();
            //preparo mensajes
            if(carga_imposible)    msj = " CARGA IMPOSIBLE, EL APORTE PRESENTA RETIROS ASOCIADOS A EL.";
            if(!totales_invalidos) msj = " CARGA IMPOSIBE, LOS TOTALES DE LOS APORTES HIJOS NO CONCUERDAN CON LOS TOTALES DEL APORTE PAPA.";
            if(no_existe_retiro)   msj = " CARGA IMPOSIBLE, NO EXISTE RETIRO CON LAS CARACTERISTICAS REQUERIDAS PARA EL CARGUE.";
            if(!aporte_con_hijos)  msj = " CARGA IMPOSIBLE, EL RETIRO ASOCIADO AL APORTE NO AFECTA NINGUN APORTE.";
            //debo publicar pagina de error

            error_carga.TBPL_Aporte_Invalido(salida,msj+ " fecha_p "+fecha_p+ "capital_papa " +capital_papa + " rtos_papa "+rtos_papa+" c_cpapa "+c_cpapa +" f_producto "+f_producto+" f_contrato "+f_contrato);
          }
          else if(!carga_imposible||totales_invalidos||aporte_con_hijos)
          {
            double vdif = c_cpapa - c_chijo;
            if (vdif >= 0.01)
            {
              // if(c_chijo!=c_cpapa)
              //{
              v_conexion_taxb.rollback();
              msj="LA SUMATORIA DE CUENTA CONTINGENTE DE LOS HIJOS ES DIFERENTE AL TOTAL DEL PAPA";
              v_conexion_taxb.rollback();
            }
            else
            {
              //actualizar el aporte papa con informacion de traslado = "S"
              CallableStatement t_cst8i_7 = v_conexion_taxb.prepareCall("{ call TBPBD_Estado_Aporte_TO(?,?,?,?,?,?) }");
              t_cst8i_7.registerOutParameter(6,Types.VARCHAR);
              t_cst8i_7.setString(1,f_producto);
              t_cst8i_7.setString(2,f_contrato);
              t_cst8i_7.setInt(3,consecutivo);
              t_cst8i_7.setString(4,producto_ret_c);
              t_cst8i_7.setString(5,contrato_ret_c);
              t_cst8i_7.execute();
              String estado = t_cst8i_7.getString(6);
              t_cst8i_7.close();
              v_conexion_taxb.commit();
            }
            //Llamar Procedimiento QUE MUSTRE INFORMACION DEL PAPA Y LA DE LOS HIJOS
            informacion_final.TBPL_Papa_Hijos(apr_capital,c_ch,apr_rto,capital_papa,c_cpapa,rtos_papa,total_hijos,salida,msj," ","INFORMACION DE TRASLADOS INTERNOS DE APORTES");
          }
        }  //si el aporte fue hallado
        //si el aporte no fue hallado en tax se produce un error
        else if (!aporte_hallado)
        {
          v_conexion_taxb.rollback();
          msj = "CARGA IMPOSIBLE, EL APORTE SELECCIONADO POR EL USUARIO NO ES VALIDO.";
          error_carga.TBPL_Aporte_Invalido(salida,msj);
        }
        //}
      } //si vamos por X y X
      salida.flush();
    }
    catch(Exception ex)
    {
      STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
      String msj = "SE PRODUJO UN ERROR INESPERADO, "+ex.toString() +" INTENTE DE NUEVO.";
      salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS INTERNOS",
                                         "TBPKT_MODULO_APORTES.TBS_INFORMACION_TRASLADO","<CENTER>"+msj+"</CEBTER>",false));
      salida.println("<BR><BR>");
      salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
      salida.println(plantilla.TBFL_PIE);
      salida.flush();
      return;
    }
      finally{
                 try{
                              DataSourceWrapper.closeConnection(v_conexion_taxb);                  
                 }catch(SQLException sqlEx){
                              System.out.println(sqlEx.toString());
                 }
         } 
  }
  //************************************************************************************
  public String getServletInfo()
  {
    return "PKT_ACTUALIZA_APORTES.TBS_INFORMACION_TRASLADO Information";
  }
  //************************************************************************************
}
