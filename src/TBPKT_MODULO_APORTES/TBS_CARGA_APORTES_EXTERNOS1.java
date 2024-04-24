package TBPKT_MODULO_APORTES;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.*;
import java.lang.Math;

/**
*Este servlet hace parte de la carga de aportes externos hacia tax, y tiene como
*función cargar los aportes que estan la tabla temporal de TAX hacia la tabla
*definitiva de APORTES EN TAX
*/

public class TBS_CARGA_APORTES_EXTERNOS1 extends HttpServlet implements SingleThreadModel
{
//----------------------------------------------------------------------------------------------------
public void init(ServletConfig config) throws ServletException
 {super.init(config);}
//----------------------------------------------------------------------------------------------------
public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
                  throws ServletException, IOException
{
    PrintWriter salida  = new PrintWriter (respuesta.getOutputStream());
    Connection v_conexion_taxb    =   null;
try
{
    /*Variable agregada por APC para manejar el tamaño de los arreglos de una manera centralizada*/
    int MAX = 3000;

  //salida.println("<BR>Inicio.<BR>");
  STBCL_GenerarBaseHTMLII plantilla = new STBCL_GenerarBaseHTMLII();
  //seguridad
  HttpSession session = peticion.getSession(true);
  if (session == null) session = peticion.getSession(true);
  respuesta.setContentType("text/html");
  /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
  // respuesta.setHeader("Pragma", "No-Cache");
  // respuesta.setDateHeader("Expires", 0);
  // respuesta.setHeader("Cache-Control","no-Cache");
  String v_contrato   = "", v_producto = "", v_usuario  = "", v_unidad = "";
  String v_tipousu    = "", v_idworker = "";
  String parametros[] = new String[8];
  String cadena2      = peticion.getParameter("cadena");
  String nuevaCadena  = cadena2;
  String ip_tax = peticion.getRemoteAddr();
  
  // PHTM I&T 2016-02-09 Variables añadidas para el proceso de TBPBD_Tiene_Beneficio_Externos
  String[] tiene_beneficioStr = new String[1];  
  String[] estadoStr = new String[10]; 
    
  TBCL_Seguridad Seguridad = new TBCL_Seguridad();
  //valido la veracidad del producto y contrato enviados por pipeline
  parametros = Seguridad.TBFL_Seguridad(cadena2, salida, ip_tax);
  v_contrato = parametros[0];
  v_producto = parametros[1];
  v_usuario  = parametros[2];
  v_unidad   = parametros[3];
  v_tipousu  = parametros[4];
  v_idworker = parametros[5];
  //seguridad
  v_conexion_taxb =   DataSourceWrapper.getInstance().getConnection();
  
  TBCL_HTML   parametros_requeridos     = new TBCL_HTML();
  TBCL_HTML   hoja_error                = new TBCL_HTML();
  TBCL_HTML   publica_afp               = new TBCL_HTML();
  TBCL_HTML   publica_aportes           = new TBCL_HTML();
  TBCL_HTML   afp_existe                = new TBCL_HTML();
  TBCL_HTML   nombres                   = new TBCL_HTML();
  TBCL_HTML   compara_totales           = new TBCL_HTML();
  TBCL_HTML   informacion_final         = new TBCL_HTML();
  TBCL_HTML   usuario_invalido          = new TBCL_HTML();
  //valido la veracidad del producto y contrato enviados por pipeline
  String  producto    = peticion.getParameter("nom_producto");
  String  contrato    = peticion.getParameter("num_contrato");
  String  usuario     = peticion.getParameter("usuario");

  if(producto != null)
  {
    v_producto = "X";
    v_contrato = "X";
  }
  //------------------------------------------------------------------pagina numero 1
  if(parametros_requeridos.TBPL_Parametros_Requeridos(v_producto,v_contrato))
  {
    
    
    String codigo_afp[] = new String[500];
    String nombre_afp[] = new String[500];
    String nit_afp[]    = new String[500];
    int total_afp       = 0;
    String select8i_0   ="SELECT "+
                        "AFP_CODIGO "+
                        ",AFP_DESCRIPCION "+
                        ",AFP_NIT "+
                        "FROM "+
                        "TBADM_FONDOS_PENSIONES "+
                        "WHERE "+
                        "AFP_REF_TIPO = 'STA002' "+
                        "ORDER BY AFP_DESCRIPCION ";
    PreparedStatement t_st8i = v_conexion_taxb.prepareStatement(select8i_0);
    ResultSet t_rs8i         = t_st8i.executeQuery();
    while(t_rs8i.next())
    {
      codigo_afp[total_afp] = t_rs8i.getString(1);
      nombre_afp[total_afp] = t_rs8i.getString(2);
      nit_afp[total_afp]    = t_rs8i.getString(3);
      total_afp++;
    }
    t_rs8i.close();
    t_st8i.close();
    publica_afp.TBPL_Publica_AFP(nombre_afp,nit_afp,total_afp,v_producto,v_contrato,v_usuario,salida,codigo_afp,nuevaCadena);
    return;
  }
  else if ((!parametros_requeridos.TBPL_Parametros_Requeridos(producto,contrato))&&
          ( producto == null)&&
          ( contrato == null))
  {
    STBCL_GenerarBaseHTML plantilla2 = new STBCL_GenerarBaseHTML();
    String msj = "EL CONTRATO "+v_contrato+" NO EXISTE EN EL SISTEMA.";
    salida.println(plantilla2.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                       "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS","<font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><CENTER>"+msj+"</CEBTER></font></b>",false));
    salida.println("<BR><BR>");
    salida.println("<center><input type='button' value='Aceptar' Onclick='history.go(-1);' ></center>");
    salida.println(plantilla2.TBFL_PIE);
    salida.flush();
    return;
  }
    //------------------------------------------------------------------pagina numero 2
  else if
       ((!parametros_requeridos.TBPL_Parametros_Requeridos(producto,contrato))&&
       (!producto.equals("A"))&&
       (!contrato.equals("A"))&&
       (!producto.equals("B"))&&
       (!contrato.equals("B")))
  {
    hoja_error.TBPL_Hoja_Error(producto,contrato,salida,0,"TBS_CARGA_APORTES_EXTERNOS1",
                          "INFORMACION DE TRASLADOS EXTERNOS");
    return;
  }
  //------------------------------------------------------------------pagina numero 2
  else if((producto.equals("A"))&&(contrato.equals("A")))
  {
    String  f_producto    = peticion.getParameter("f_producto");
    String  f_contrato    = peticion.getParameter("f_contrato");
    String  nombre_afp    = " ";
    String  nit_afp       = " ";
    String  codigo_afp    = " ";
    String  cadena        = peticion.getParameter("fila");
    int     indice        = 0;
    indice                = cadena.indexOf("$");
    codigo_afp            = cadena.substring(0,indice);
    cadena                = cadena.substring(indice+1,cadena.length());
    indice                = cadena.indexOf("$");
    nombre_afp            = cadena.substring(0,indice);
    nit_afp               = cadena.substring(indice+1,cadena.length());
    String fecha_e[]      = new String[MAX];
    double c[]            = new double[MAX];
    double cc[]           = new double[MAX];
    double rtos[]         = new double[MAX];
    String nomb_afp[]     = new String[MAX];
    int[] consecutivos    = new int[MAX];
    int total_aportes     = 0;
    if(nit_afp.length()==13)
    {
      nit_afp = nit_afp.substring(2,13);
    }
    else
    {
      nit_afp = nit_afp.substring(0,nit_afp.length());
    }
    if(afp_existe.TBPL_AFP(nit_afp,v_conexion_taxb))
    {
      String select8i_0     = "SELECT "+
                              "TO_CHAR(APO_FECHA_EFECTIVA,'RRRR-MM-DD'), "+
                              "APO_CAPITAL, "+
                              "APO_RENDIMIENTOS, "+
                              "APO_CUENTA_CONTINGENTE, "+
                              "APO_CONSECUTIVO, "+
                              "AFP_DESCRIPCION "+
                              "FROM TBAPORTES,TBADM_FONDOS_PENSIONES "+
                              "WHERE "+
                              "APO_CON_PRO_CODIGO           = ? "+
                              "AND APO_CON_NUMERO           = ? "+
                              "AND APO_AFP_CODIGO           = ? "+
                              "AND APO_APORTE_TRASLADO      = 'S' "+
                              "AND APO_INFORMACION_TRASLADO = 'N' "+
                              "AND APO_AFP_CODIGO           = AFP_CODIGO "+
                              "AND AFP_REF_TIPO             = 'STA002' "+
                              "ORDER BY APO_FECHA_EFECTIVA DESC";
//      salida.println("<BR>Despues de select de afp existe.<BR>");
      PreparedStatement t_st8i = v_conexion_taxb.prepareStatement(select8i_0);
      t_st8i.setString(1,f_producto);
      t_st8i.setString(2,f_contrato);
      t_st8i.setString(3,codigo_afp);
//      salida.println("<BR>Antes de publica aportes.<BR>");
//      salida.println("<BR>Query : "+ t_st8i + "<BR>");
      ResultSet t_rs8i       = t_st8i.executeQuery();
      while(t_rs8i.next())
      {
        fecha_e[total_aportes]      = t_rs8i.getString(1);
        c[total_aportes]            = t_rs8i.getDouble(2);
        rtos[total_aportes]         = t_rs8i.getDouble(3);
        cc[total_aportes]           = t_rs8i.getDouble(4);
        consecutivos[total_aportes] = t_rs8i.getInt(5);
        nomb_afp[total_aportes]     = t_rs8i.getString(6);
        total_aportes++;
//        salida.println("<BR>aporte " + total_aportes + ".<BR>");
      }
      t_rs8i.close();
      t_st8i.close();
      publica_aportes.TBPL_Publica_Aportes(f_producto,f_contrato,fecha_e,cc,c,rtos,total_aportes,salida,usuario,nomb_afp,consecutivos
                                          ,nombres.TBPL_Nombres(f_producto,f_contrato),codigo_afp,nit_afp,nuevaCadena);
    }//si afp se encuentra cargada en TBINTERFACE_TRASLADOS
    else
    {
      STBCL_GenerarBaseHTML plantilla1 = new STBCL_GenerarBaseHTML();
      salida.println(plantilla1.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                            "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS1"," ",true));
      salida.println("<BR><BR>");
      salida.println("<p><B><FONT color=black face=verdana size=2> NO HAY INFORMACION CARGADA PARA LA AFP SELECCIONADA </font></B></p>");
      salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
      salida.println(plantilla1.TBFL_PIE);
      salida.close();
    }//si la afp no se encuentra cargada en TBINTERFACE_TRASLADOS
    return;
  }
  //------------------------------------------------------------------pagina numero 3
  else if((producto.equals("B"))&&(contrato.equals("B")))
  {
    //Inicio 
    //salida.println("<BR>Inicio pagina 3.<BR>");
    boolean datos_cargados= false;
    String  estado_user   = "BIEN";
    String  estado        = " ";
    String  f_producto    = peticion.getParameter("f_producto");
    String  f_contrato    = peticion.getParameter("f_contrato");
    String  codigo_afp    = peticion.getParameter("codigo_afp");
    String  nit_afp       = peticion.getParameter("nit_afp");
    String  conse         = peticion.getParameter("consecutivo");
    Integer c_temp        = new Integer(conse);
    String tieneretiros   = " ";
    int consecutivo       = c_temp.intValue();
    boolean aporte_hallado    = false;
    int totales_validos   = 1;
    String  fecha_e       = " ";
    double  k             = 0;
    double cc             = 0;
    double rtos           = 0;
    double v_u            = 0;
    double numUnid        = 0;
    boolean vvalccta      = true;
    String fecha_p        = " ";
    String transaccion    = " ";
    String t_t            = " ";
    String c_t            = " ";
    String certificado    = " ";
    String condicion      = " ";
    String detalle        = " ";
    String trs            = " ";
    String empresa        = new String(" ");
    String afp            = " ";
    String nomb_afp       = " ";
    String i_traslado     = " ";
    String a_traslado     = " ";
    String f_i            = " ";
    String t_i            = " ";
    long    n_i            = 0;
    String f_c            = " ";
    String t_a            = " ";
    double c[]            = new double[MAX];
    double rts[]          = new double[MAX];
    double c_c[]          = new double[MAX];
    double k_total        = 0;
    double k_total1       = 0;
    double k_total12      = 0;
    double rto_total      = 0;
    double rto_total1     = 0;
    double rto_total12    = 0;
    double v_vr_comision_total = 0;
    double v_vr_comision   = 0;
    double v_vr_comision_hijo = 0;
 
    double cc_total       = 0;
    String msj            = " ";
    String msj_cc         = " ";
    String select8i_1     = " ";
    int total_hijos       = 0;
    //INICIO CALCULO DE CAMPOS PARA INSERTARLE AL HIJO
    String estado_aporte         = "SEA001";
    String certificacion_aporte  = " ";
    String trasladado_aporte     = "N";
    String cargado_aporte        = "N";
    String con_i_traslado        = "N";
    double numero_unidades       = 0;
    double saldo_numero_unidades = 0;
    double num_uni_total         = 0;
    int    consecutivo_hijo      = 0;
    int    porcentaje_r_h        = 0;
    int    p_maximo              = 0;
    double valor_d               = 0;
    String beneficiado_aporte    = "N";
    String razon_beneficio       = " ";
    java.sql.Date fecha_beneficio= new java.sql.Date(1,1,1);
    String detalle_condicion     = new String(" ");
    String ref_condicion         = new String(" ");
    String empresa_x             = new String(" ");
    String select8i_2            = new String(" ");
    String tiene_beneficio       = " ";
    String usuario_valido        = " ";
    String ref_valor             = new String(" ");
    String t_id                  = "";
    long n_id                    = 0;
    double v_comision            = 0;
        
    //FIN CALCULO DE CAMPOS PARA INSERTARLE AL HIJO
    
    String select8i_0     = "SELECT "+
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
                          "NVL(APO_EMP_GRUPO,'#$%$'),"+
                          "APO_AFP_CODIGO,"+
                          "AFP_DESCRIPCION,"+
                          "APO_INFORMACION_TRASLADO,"+
                          "APO_APORTE_TRASLADO,"+
                          "CON_REF_TIPO_IDENTIFICACION,"+
                          "CON_NUMERO_IDENTIFICACION, "+
                          "APO_NUMERO_UNIDADES "+
                          "FROM TBAPORTES,TBADM_FONDOS_PENSIONES,TBCONTRATOS,TBREFERENCIAS "+
                          "WHERE "+
                          "APO_AFP_CODIGO         = AFP_CODIGO "+
                          "AND APO_CON_PRO_CODIGO = CON_PRO_CODIGO "+
                          "AND APO_CON_NUMERO     = CON_NUMERO "+
                          "AND CON_REF_TIPO_IDENTIFICACION = REF_CODIGO "+
                          "AND APO_CON_PRO_CODIGO = ? "+
                          "AND APO_CON_NUMERO     = ? "+
                          "AND APO_CONSECUTIVO    = ? ";
    PreparedStatement t_st8i_1 = v_conexion_taxb.prepareStatement(select8i_0);
    t_st8i_1.setString(1,f_producto);
    t_st8i_1.setString(2,f_contrato);
    t_st8i_1.setInt(3,consecutivo);
    ResultSet t_rs8i = t_st8i_1.executeQuery();
//    salida.println("<BR>Antes de asignacion de campos para el aporte hijo<BR>");    
    while(t_rs8i.next())
    {
      aporte_hallado = true;
//      salida.println("<BR>asignacion campo1<BR>");
      fecha_e        = t_rs8i.getString(1);
//      salida.println("<BR>asignacion campo2<BR>");      
      k              = t_rs8i.getDouble(2);
//      salida.println("<BR>asignacion campo3<BR>");      
      cc             = t_rs8i.getDouble(4);
//      salida.println("<BR>asignacion campo4<BR>");      
      rtos           = t_rs8i.getDouble(3);
//      salida.println("<BR>asignacion campo5<BR>");      
      v_u            = t_rs8i.getDouble(5);
//      salida.println("<BR>asignacion campo6<BR>");      
      fecha_p        = t_rs8i.getString(6);
//      salida.println("<BR>asignacion campo7<BR>");      
      transaccion    = t_rs8i.getString(7);
//      salida.println("<BR>asignacion campo8<BR>");      
      t_t            = t_rs8i.getString(8);
//      salida.println("<BR>asignacion campo9<BR>");      
      c_t            = t_rs8i.getString(9);
//      salida.println("<BR>asignacion campo10<BR>");      
      certificado    = t_rs8i.getString(10);
//      salida.println("<BR>asignacion campo11<BR>");      
      condicion      = t_rs8i.getString(11);
//      salida.println("<BR>asignacion campo12<BR>");      
      detalle        = t_rs8i.getString(12);
//      salida.println("<BR>asignacion campo13<BR>");      
      trs            = t_rs8i.getString(13);
//      salida.println("<BR>asignacion campo14<BR>");      
      empresa        = t_rs8i.getString(14);
//      salida.println("<BR>asignacion campo15<BR>");      
      afp            = t_rs8i.getString(15);
//      salida.println("<BR>asignacion campo16<BR>");      
      nomb_afp       = t_rs8i.getString(16);
//      salida.println("<BR>asignacion campo17<BR>");      
      i_traslado     = t_rs8i.getString(17);
//      salida.println("<BR>asignacion campo18<BR>");      
      a_traslado     = t_rs8i.getString(18);
//      salida.println("<BR>asignacion campo19<BR>");      
      t_id           = t_rs8i.getString(19);
//      salida.println("<BR>asignacion campo20:="+ t_rs8i.getLong(20) +".<BR>");      
      n_id           = t_rs8i.getLong(20);
//      salida.println("<BR>asignacion campo20:="+ t_rs8i.getLong(21) +".<BR>");      
      numUnid        = t_rs8i.getDouble(21);
      
    }//while
    t_rs8i.close();
    t_st8i_1.close();
//    salida.println("<BR>Despues de asignacion de campos para el aporte hijo<BR>");
    if(aporte_hallado)
    {
      //VALIDO RETIROS ASOCIADOS AL APORTE HALLADO
      CallableStatement t_cst8i_0 = v_conexion_taxb.prepareCall("{ call TBPBD_Valida_Retiros_Externos(?,?,?,?,?) }");
      t_cst8i_0.registerOutParameter(4,Types.VARCHAR);
      t_cst8i_0.registerOutParameter(5,Types.VARCHAR);
      t_cst8i_0.setString(1,f_producto);
      t_cst8i_0.setString(2,f_contrato);
      t_cst8i_0.setInt(3,consecutivo);
      t_cst8i_0.execute();
      tieneretiros = t_cst8i_0.getString(4);
      estado       = t_cst8i_0.getString(5);
      t_cst8i_0.close();
//      salida.println("<BR>Despues de valida retiros externos.<BR>");
      if(!estado.equals("BIEN"))
      {
        STBCL_GenerarBaseHTML plantilla2 = new STBCL_GenerarBaseHTML();
        salida.println(plantilla2.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                         " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, TBPBD_Valida_Retiros_Externos("+f_producto+","+f_contrato+","+consecutivo+","+tieneretiros+","+estado+")",false));
        salida.println("<BR><BR>");
        salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
        salida.println(plantilla2.TBFL_PIE);
        salida.close();
      }
      if(tieneretiros.equals("f")&&estado.equals("BIEN"))
      {
        //BUSCO LOS HIJOS
        select8i_1 = "SELECT "+
                     "INT_FECHA "+
                     ",INT_NIT_AFP_ORIGEN "+
                     ",DECODE(INT_TIPO_IDENTIFICACION,'CC','UTI001','TI','UTI003','CE','UTI002','UTI000')"+
                     ",INT_NUMERO_IDENTIFICACION "+
                     ",TO_CHAR(INT_FECHA_CONSIGNACION,'RRRR-MM-DD') "+
                     ",INT_TIPO_APORTE "+
                     ",INT_VALOR_HISTORICO "+
                     ",INT_RETENCION_CONTINGENTE "+
                     ",INT_RENDIMIENTOS "+
                     "FROM "+
                     "TBINTERFACE_TRASLADOS "+
                     "WHERE "+
                     "INT_NIT_AFP_ORIGEN            = ? "+
                     "AND DECODE(INT_TIPO_IDENTIFICACION,'CC','UTI001','TI','UTI003','CE','UTI002','UTI000') = ? "+
                     "AND INT_NUMERO_IDENTIFICACION = ? ";
        PreparedStatement t_st8i_2 = v_conexion_taxb.prepareStatement(select8i_1);
        t_st8i_2.setString(1,nit_afp);
        t_st8i_2.setString(2,t_id);
        t_st8i_2.setLong(3,n_id);
        t_rs8i = t_st8i_2.executeQuery();
        //salida.println("<BR>Despues de busqueda de hijos.<BR>");
        //salida.println("<BR>Query:<BR>"+t_st8i_2+"<BR>");
        datos_cargados = true;
        
        //HIA 2012-05-23 buscar valor de comision para los aportes de capital + seguro
        CallableStatement t_comi = v_conexion_taxb.prepareCall("{ call TBPBD_OBTENER_COMISIONAPORTE(?,?) }");
        t_comi.registerOutParameter(2,Types.NUMERIC);
        t_comi.setInt(1,consecutivo);
        t_comi.execute();
        v_comision = t_comi.getDouble(2);        
            t_comi.close();
  
        //salida.println("<BR>Query:<BR>comisión: "+v_comision+"<BR>");
      
      
        //salida.println("<BR>Query:<BR>nit afp: "+nit_afp + " nit: " + n_id + " tipo id: " + t_id + " capital: " + k + " rendimientos: " + rtos + " cuenta conti:" + cc +"<BR>");
      
        //HIA 2012-05-23 Validacion de totales devuelve 0 si estan correctos los totales, 
        //              si la diferencia es mayor a la tolerancia permitida que es de un peso devuelve 1
        CallableStatement t_total = v_conexion_taxb.prepareCall("{ call TBPBD_COMPARA_TOTALES(?,?,?,?,?,?,?,?) }");
        t_total.registerOutParameter(8,Types.INTEGER);
        t_total.setString(1,nit_afp);
        t_total.setString(2,t_id);
        t_total.setDouble(3,n_id);
        t_total.setDouble(4,k);
        t_total.setDouble(5,rtos);
        t_total.setDouble(6,cc);
        t_total.setDouble(7,v_comision);
        t_total.execute();
        totales_validos = t_total.getInt(8);
            t_total.close();
            
        //salida.println("<BR>Query:<BR>totales validos: " + totales_validos +"<BR>");    
            
        //Permite realizar la carga de los aportes hijos 
        if (totales_validos == 1)
        {
            //HIA 2012-05-23 Validar si existe valor de comisión, si es igual a cero se carga la historia como siempre se ha hecho, de lo contrario 
            //                se carga disminuyendo el valor de la comision proporcionalmente a los aportes hijos. 
            if( v_comision == 0 )
            {
                while(t_rs8i.next())
                {
                  datos_cargados = true;
                  f_i = t_rs8i.getString(1);
                  t_i = t_rs8i.getString(3);
                  n_i = t_rs8i.getLong(4);
                  f_c = t_rs8i.getString(5);
                  t_a = t_rs8i.getString(6);
                  
                  // PHTM I&T 2016-09-02 Se retira instruccion de restar el capital de los aportes por el requerimieto 28366
                  // cuando los rendimientos son negativos
                  /*if(t_rs8i.getDouble(9)<0)
                  {
                    c[total_hijos]    = t_rs8i.getDouble(7)+t_rs8i.getDouble(9);
                    rts[total_hijos]  = 0;
                    k_total1          = t_rs8i.getDouble(7);
                    rto_total1        = t_rs8i.getDouble(9);
                  }
                  else
                  {*/
                    c[total_hijos]    = t_rs8i.getDouble(7);
                    rts[total_hijos]  = t_rs8i.getDouble(9);
                    k_total1          = t_rs8i.getDouble(7);
                    rto_total1        = t_rs8i.getDouble(9);
                  //}
                  c_c[total_hijos]    = t_rs8i.getDouble(8);
                  k_total            += c[total_hijos];
                  k_total12          += k_total1;
                  rto_total          += rts[total_hijos];
                  rto_total12        += rto_total1;
                  cc_total           += c_c[total_hijos];
                  //INICIO BUSQUEDA DEL REF_VALOR DE CADA HIJO(DE ACUERDO A T_A)
        //          salida.println("<BR>Inicio de busqueda de ref valor.<BR>");
                  if(t_a.equals("A"))
                  {
                    empresa   = null;
                    ref_valor = "100";
                  }//si tipo aporte es A
                  else if(t_a.equals("E"))
                  {
                    if (empresa == null || empresa.equals("")) empresa = " ";
                    if(!empresa.substring(0,1).equals(" "))
                    {
                      CallableStatement t_cst8i_5 = v_conexion_taxb.prepareCall("{ call TBPBD_CONDICION_EXTERNOS(?,?,?,?,?,?,?,?) }");
                      t_cst8i_5.registerOutParameter(4,Types.VARCHAR);
                      t_cst8i_5.registerOutParameter(5,Types.VARCHAR);
                      t_cst8i_5.registerOutParameter(6,Types.VARCHAR);
                      t_cst8i_5.registerOutParameter(7,Types.VARCHAR);
                      t_cst8i_5.registerOutParameter(8,Types.VARCHAR);
                      t_cst8i_5.setString(1,empresa);
                      t_cst8i_5.setString(2,f_contrato);
                      t_cst8i_5.setString(3,f_producto);
                      t_cst8i_5.execute();
                      estado = t_cst8i_5.getString(8);
                      try
                      {
                        if(!t_cst8i_5.getString(6).equals(null))
                          ref_valor = t_cst8i_5.getString(6);
                      }
                      catch(Exception ex){ref_valor = "100";}
                      t_cst8i_5.close();
        //              salida.println("<BR>Despues de condicion externos<BR>");
                      if(!estado.equals("BIEN"))
                      {
                        STBCL_GenerarBaseHTML plantilla3 = new STBCL_GenerarBaseHTML();
                        salida.println(plantilla3.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                 " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO,TBPBD_CONDICION_EXTERNOS( "+empresa+","+f_contrato+","+f_producto+","+estado+")",false));
                        salida.println("<BR><BR>");
                        salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                        salida.println(plantilla3.TBFL_PIE);
                        salida.close();
                      }
                    }//si empresa papa no es nula
                    else if(empresa.substring(0,1).equals(" "))
                    {
                      CallableStatement t_cst8i_6 = v_conexion_taxb.prepareCall("{ call TBPBD_Empresa_Externos(?,?,?,?) }");
                      t_cst8i_6.registerOutParameter(3,Types.VARCHAR);
                      t_cst8i_6.registerOutParameter(4,Types.VARCHAR);
                      t_cst8i_6.setString(1,f_producto);
                      t_cst8i_6.setString(2,f_contrato);
                      t_cst8i_6.execute();
                      empresa_x = t_cst8i_6.getString(3);
                      estado    = t_cst8i_6.getString(4);
                      t_cst8i_6.close();
        //              salida.println("<BR>Despues de empresa externos<BR>");
                      if(!estado.equals("BIEN"))
                      {
                        STBCL_GenerarBaseHTML plantilla4 = new STBCL_GenerarBaseHTML();
                        salida.println(plantilla4.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                         " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO,TBPBD_Empresa_Externos( "+f_producto+","+f_contrato+","+empresa_x+","+estado+")",false));
                        salida.println("<BR><BR>");
                        salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                        salida.println(plantilla4.TBFL_PIE);
                        salida.close();
                      }
                      if(!empresa_x.equals("nula"))
                      {
                        CallableStatement t_cst8i_5 = v_conexion_taxb.prepareCall("{ call TBPBD_CONDICION_EXTERNOS(?,?,?,?,?,?,?,?) }");
                        t_cst8i_5.registerOutParameter(4,Types.VARCHAR);
                        t_cst8i_5.registerOutParameter(5,Types.VARCHAR);
                        t_cst8i_5.registerOutParameter(6,Types.VARCHAR);
                        t_cst8i_5.registerOutParameter(7,Types.VARCHAR);
                        t_cst8i_5.registerOutParameter(8,Types.VARCHAR);
                        t_cst8i_5.setString(1,empresa);
                        t_cst8i_5.setString(2,f_contrato);
                        t_cst8i_5.setString(3,f_producto);
                        t_cst8i_5.execute();
                        estado = t_cst8i_5.getString(8);
                    //                salida.println("<BR>Despues de condicion externos 2.<BR>");
                        try
                        {
                          if(!t_cst8i_5.getString(6).equals(null))
                             ref_valor = t_cst8i_5.getString(6);
                        }
                        catch(Exception ex){ref_valor = "100";}
                        t_cst8i_5.close();
                        empresa      = empresa_x;
                      }//si empresa_x no es nula
                      else if(empresa_x.equals("nula"))
                      {
                        empresa   = null;
                        ref_valor = "100";
                      }//si empresa _x es nula
                    }//si empresa papa es nula
                  }//si tipo aporte es E
                  //Realizo conversion del ref valor
                  Integer re_valor = new Integer(ref_valor);
                  int r_valor      = re_valor.intValue();
                  //FIN BUSQUEDA DE LOS CAMPOS REF_CONDICION Y DETALLE_CONDICION DE CADA HIJO(DE ACUERDO A T_A)
                  //INICIO CALCULO DE CAMPOS PARA INSERTARLE AL HIJO
                  numero_unidades       = (c[total_hijos]+rts[total_hijos])/v_u;
                  saldo_numero_unidades = numero_unidades;
                  num_uni_total += numero_unidades;
                  if(c[total_hijos]!=0)
                    porcentaje_r_h  = 0;
                  else
                    porcentaje_r_h  = 100;
                  select8i_2 = "SELECT APO_CONSECUTIVO_SQ.NEXTVAL FROM DUAL ";
                  PreparedStatement t_st8i_3= v_conexion_taxb.prepareStatement(select8i_2);
                  ResultSet t_rs8i_3 = t_st8i_3.executeQuery();
                  while(t_rs8i_3.next())
                    consecutivo_hijo = t_rs8i_3.getInt(1);
                    t_rs8i_3.close();
                    t_st8i_3.close();
                    //FIN CALCULO DE CAMPOS PARA INSERTARLE AL HIJO
                    //FIN ACTUALIZACION DE DISPONIBILIDADES DE CADA HIJO
                    //INICIO INSERT DE LOS HIJOS
                    CallableStatement t_cst8i_3 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Aporte_Hijo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
                    t_cst8i_3.registerOutParameter(35,Types.VARCHAR);
                    t_cst8i_3.setString(1,f_producto);
                    t_cst8i_3.setString(2,f_contrato);
                    t_cst8i_3.setInt(3,consecutivo_hijo);
                    t_cst8i_3.setString(4,fecha_p);
                    t_cst8i_3.setString(5,fecha_e);
                    t_cst8i_3.setString(6,transaccion);
                    t_cst8i_3.setString(7,t_t);
                    t_cst8i_3.setString(8,c_t);
                    t_cst8i_3.setDouble(9,c[total_hijos]);
                    t_cst8i_3.setDouble(10,rts[total_hijos]);
                    t_cst8i_3.setDouble(11,c_c[total_hijos]);
                    t_cst8i_3.setDouble(12,v_u);
                    t_cst8i_3.setDouble(13,numero_unidades);
                    t_cst8i_3.setString(14,certificado);
                    t_cst8i_3.setString(15,trasladado_aporte);
                    t_cst8i_3.setString(16,beneficiado_aporte);
                    t_cst8i_3.setString(17,cargado_aporte);
                    t_cst8i_3.setString(18,con_i_traslado);
                    t_cst8i_3.setString(19,estado_aporte);
                    t_cst8i_3.setDouble(20,c[total_hijos]);
                    t_cst8i_3.setDouble(21,rts[total_hijos]);
                    t_cst8i_3.setInt(22,porcentaje_r_h);
                    t_cst8i_3.setDouble(23,c_c[total_hijos]);
                    t_cst8i_3.setDouble(24,saldo_numero_unidades);
                    t_cst8i_3.setString(25,usuario);
                    t_cst8i_3.setString(26,f_c);
                    t_cst8i_3.setString(27,null);                   //RAZON DE BENEFICIO
                    t_cst8i_3.setDate(28,null);                    //FECHA DE BENEFICIO
                    t_cst8i_3.setString(29,null);                 //REF_CONDICION
                    t_cst8i_3.setString(30,null);                //DETALLE_CONDICION
                    t_cst8i_3.setString(31,null);
                    t_cst8i_3.setString(32,empresa);
                    t_cst8i_3.setString(33,afp);
                    t_cst8i_3.setInt(34,consecutivo);
                    t_cst8i_3.executeUpdate();
                    estado = t_cst8i_3.getString(35);
                    t_cst8i_3.close();
        //            salida.println("<BR>Despues de inserta aporte hijo.<BR>");
                    if(!estado.equals("BIEN"))
                    {
                      STBCL_GenerarBaseHTML plantilla5 = new STBCL_GenerarBaseHTML();
                      salida.println(plantilla5.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                  " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, TBPBD_Inserta_Aporte_Hijo"+estado,false));
                      salida.println("<BR><BR>");
                      salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                      salida.println(plantilla5.TBFL_PIE);
                      salida.close();
                    }
                    //FIN INSERT DE LOS HIJOS
                    //INICIO ACTUALIZACION DE DISPONIBILIDADES DE CADA HIJO
                    //ACTUALIZO DISPONIBILIDADES DE CADA HIJO
                    valor_d = ((c[total_hijos]+rts[total_hijos])*r_valor)/100;
                    CallableStatement t_cst8i_2 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Disponibilidad_E(?,?,?,?,?,?,?) }");
                    t_cst8i_2.registerOutParameter(7,Types.VARCHAR);
                    t_cst8i_2.setString(1,f_producto);
                    t_cst8i_2.setString(2,f_contrato);
                    t_cst8i_2.setInt(3,consecutivo_hijo);
                    t_cst8i_2.setString(4,fecha_e);
                    t_cst8i_2.setInt(5,r_valor);
                    t_cst8i_2.setDouble(6,valor_d);
                    t_cst8i_2.execute();
                    estado = t_cst8i_2.getString(7);
                    t_cst8i_2.close();
        //            salida.println("<BR>Despues de inserta disponibilidad_E<BR>");
                    //INICIO CALCULO BENEFICIO PARA EL APO_CONSECUTIVO ACABADO DE INGRESAR, DESPUES DE CALCULARLO LO DEBO ACTUALIZAR
                    //calculo variables involucradas con el beneficio trin¿butario: aporte_beneficio,fecha_aporte,razon_beneficio,fecha_beneficio
                    
                    // PHTM I&T 2016-02-08 Instruccion añadida para evitar la ejecucion del metodo TBPBD_Tiene_Beneficio_Externos desde base de datos 
                    TBCBD_STORED_PROCEDURES procedimientos_internos = new TBCBD_STORED_PROCEDURES();  
                    procedimientos_internos.TBPBD_Tiene_Beneficio_Externos(producto, contrato, consecutivo, tiene_beneficioStr, estadoStr);                    
                    
                    tiene_beneficio =  tiene_beneficioStr[0].toString();
                    estado = estadoStr[0].toString();
                    
                    /*
                    CallableStatement t_cst8i_7 = v_conexion_taxb.prepareCall("{ call TBPBD_Tiene_Beneficio_Externos(?,?,?,?,?) }");
                    t_cst8i_7.registerOutParameter(4,Types.VARCHAR);
                    t_cst8i_7.registerOutParameter(5,Types.VARCHAR);
                    t_cst8i_7.setString(1,f_producto);
                    t_cst8i_7.setString(2,f_contrato);
                    t_cst8i_7.setInt(3,consecutivo_hijo);
                    t_cst8i_7.execute();
        //            salida.println("<BR>Despues de beneficio externos <BR>");
                    tiene_beneficio = t_cst8i_7.getString(4);
                    estado          = t_cst8i_7.getString(5);
                    t_cst8i_7.close();
                    */
                    
                    if(!estado.equals("BIEN"))
                    {
                      STBCL_GenerarBaseHTML plantilla5 = new STBCL_GenerarBaseHTML();
                      salida.println(plantilla5.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                    " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, TBPBD_Tiene_Beneficio_Externos("+f_producto+","+f_contrato+","+consecutivo_hijo+","+tiene_beneficio+","+estado+")",false));
                      salida.println("<BR><BR>");
                      salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                      salida.println(plantilla5.TBFL_PIE);
                      salida.close();
                    }
                    if(tiene_beneficio.equals("v"))
                    {
                      //ACTUALIZO APORTE
                      CallableStatement t_cst8i_8 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_Externos(?,?,?,?,?) }");
                      t_cst8i_8.registerOutParameter(5,Types.VARCHAR);
                      t_cst8i_8.setString(1,f_producto);
                      t_cst8i_8.setString(2,f_contrato);
                      t_cst8i_8.setInt(3,consecutivo_hijo);
                      t_cst8i_8.setString(4,f_c);
                      t_cst8i_8.execute();
                      estado = t_cst8i_8.getString(5);
                      t_cst8i_8.close();
        //              salida.println("<BR>Despues de actualiza externos<BR>");
                      if(!estado.equals("BIEN"))
                      {
                        STBCL_GenerarBaseHTML plantilla6 = new STBCL_GenerarBaseHTML();
                        salida.println(plantilla6.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                  " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, TBPBD_Actualiza_Externos("+f_producto+","+f_contrato+","+consecutivo_hijo+","+f_c+","+estado+")",false));
                        salida.println("<BR><BR>");
                        salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                        salida.println(plantilla6.TBFL_PIE);
                        salida.close();
                      }
                    }//si tiene beenficio
                    //FIN CALCULO BENEFICIO PARA EL APO_CONSECUTIVO ACABADO DE INGRESAR, DESPUES DE CALCULARLO LO DEBO ACTUALIZAR
                    total_hijos++;
                  }//while encuentro hijos
              }
              else //HIA - 2012-05-23 Cargar aportes hijos del programa capital + seguro disminuyendo el valor de la comision en los aportes hijos
              {
              
                //salida.println("<BR> Inicio comisión <BR>");
                while(t_rs8i.next())
                {
                  datos_cargados = true;
                  f_i = t_rs8i.getString(1);
                  t_i = t_rs8i.getString(3);
                  n_i = t_rs8i.getLong(4);
                  f_c = t_rs8i.getString(5);
                  t_a = t_rs8i.getString(6);
                 
                  // PHTM I&T 2016-09-02 Se retira instruccion de restar el capital de los aportes por el requerimieto 28366
                  // cuando los rendimientos son negativos
                  /*if(t_rs8i.getDouble(9)<0)
                  {
                    c[total_hijos]    = t_rs8i.getDouble(7)+t_rs8i.getDouble(9);
                    rts[total_hijos]  = 0;
                    k_total1          = t_rs8i.getDouble(7);
                    rto_total1        = t_rs8i.getDouble(9);
                  }
                  else
                  {*/
                    c[total_hijos]    = t_rs8i.getDouble(7);
                    rts[total_hijos]  = t_rs8i.getDouble(9);
                    k_total1          = t_rs8i.getDouble(7);
                    rto_total1        = t_rs8i.getDouble(9);
                  //}
                  
                  //HIA 2012-05-23 CALCULAR EL VALOR DE LA COMISION 
                  v_vr_comision = (double)(Math.round((((c[total_hijos]+rts[total_hijos])* 0.025))*100))/100;;
                  v_vr_comision_hijo = v_vr_comision;
                  v_vr_comision_total = v_vr_comision_total + v_vr_comision_hijo;
                  
                  //salida.println("<BR>Query:<BR> ANTES: vr comision1: "+v_vr_comision+" Capital " + c[total_hijos] + " Rendimientos " + rts[total_hijos]  + " <BR>");
                  
                  if (v_vr_comision <=  c[total_hijos])
                  {
                      c[total_hijos] =  (double)(Math.round((c[total_hijos] - v_vr_comision)*100))/100;
                      v_vr_comision = 0;
                  } else
                  {
                      //2012-06-21 HIA se puso  c[total_hijos]  =  0; despues de que se descuenta el valor del capital a la comision
                      v_vr_comision = (double)(Math.round((v_vr_comision - c[total_hijos])*100))/100;
                      c[total_hijos]  =  0;
                  }
                                   
                  if (v_vr_comision != 0)
                  {
                      rts[total_hijos] = (double)(Math.round((rts[total_hijos] - v_vr_comision)*100))/100;
                  } else
                  {
                      rts[total_hijos] = rts[total_hijos];
                  }
                  
                  //salida.println("<BR>Query:<BR>DESPUES: vr comision1: "+v_vr_comision+" Capital " + c[total_hijos] + " Rendimientos " + rts[total_hijos]  + " vr comision2: " + v_vr_comision_hijo  + " <BR>");
                  
                  c_c[total_hijos]    = t_rs8i.getDouble(8);
                  k_total            += c[total_hijos];
                  k_total12          += k_total1;
                  rto_total          += rts[total_hijos];
                  rto_total12        += rto_total1;
                  cc_total           += c_c[total_hijos];
                  
                  //INICIO BUSQUEDA DEL REF_VALOR DE CADA HIJO(DE ACUERDO A T_A)
        //          salida.println("<BR>Inicio de busqueda de ref valor.<BR>");
                  if(t_a.equals("A"))
                  {
                    empresa   = null;
                    ref_valor = "100";
                  }//si tipo aporte es A
                  else if(t_a.equals("E"))
                  {
                    if (empresa == null || empresa.equals("")) empresa = " ";
                    if(!empresa.substring(0,1).equals(" "))
                    {
                      CallableStatement t_cst8i_5 = v_conexion_taxb.prepareCall("{ call TBPBD_CONDICION_EXTERNOS(?,?,?,?,?,?,?,?) }");
                      t_cst8i_5.registerOutParameter(4,Types.VARCHAR);
                      t_cst8i_5.registerOutParameter(5,Types.VARCHAR);
                      t_cst8i_5.registerOutParameter(6,Types.VARCHAR);
                      t_cst8i_5.registerOutParameter(7,Types.VARCHAR);
                      t_cst8i_5.registerOutParameter(8,Types.VARCHAR);
                      t_cst8i_5.setString(1,empresa);
                      t_cst8i_5.setString(2,f_contrato);
                      t_cst8i_5.setString(3,f_producto);
                      t_cst8i_5.execute();
                      estado = t_cst8i_5.getString(8);
                      try
                      {
                        if(!t_cst8i_5.getString(6).equals(null))
                          ref_valor = t_cst8i_5.getString(6);
                      }
                      catch(Exception ex){ref_valor = "100";}
                      t_cst8i_5.close();
        //              salida.println("<BR>Despues de condicion externos<BR>");
                      if(!estado.equals("BIEN"))
                      {
                        STBCL_GenerarBaseHTML plantilla3 = new STBCL_GenerarBaseHTML();
                        salida.println(plantilla3.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                 " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO,TBPBD_CONDICION_EXTERNOS( "+empresa+","+f_contrato+","+f_producto+","+estado+")",false));
                        salida.println("<BR><BR>");
                        salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                        salida.println(plantilla3.TBFL_PIE);
                        salida.close();
                      }
                    }//si empresa papa no es nula
                    else if(empresa.substring(0,1).equals(" "))
                    {
                      CallableStatement t_cst8i_6 = v_conexion_taxb.prepareCall("{ call TBPBD_Empresa_Externos(?,?,?,?) }");
                      t_cst8i_6.registerOutParameter(3,Types.VARCHAR);
                      t_cst8i_6.registerOutParameter(4,Types.VARCHAR);
                      t_cst8i_6.setString(1,f_producto);
                      t_cst8i_6.setString(2,f_contrato);
                      t_cst8i_6.execute();
                      empresa_x = t_cst8i_6.getString(3);
                      estado    = t_cst8i_6.getString(4);
                      t_cst8i_6.close();
        //              salida.println("<BR>Despues de empresa externos<BR>");
                      if(!estado.equals("BIEN"))
                      {
                        STBCL_GenerarBaseHTML plantilla4 = new STBCL_GenerarBaseHTML();
                        salida.println(plantilla4.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                         " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO,TBPBD_Empresa_Externos( "+f_producto+","+f_contrato+","+empresa_x+","+estado+")",false));
                        salida.println("<BR><BR>");
                        salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                        salida.println(plantilla4.TBFL_PIE);
                        salida.close();
                      }
                      if(!empresa_x.equals("nula"))
                      {
                        CallableStatement t_cst8i_5 = v_conexion_taxb.prepareCall("{ call TBPBD_CONDICION_EXTERNOS(?,?,?,?,?,?,?,?) }");
                        t_cst8i_5.registerOutParameter(4,Types.VARCHAR);
                        t_cst8i_5.registerOutParameter(5,Types.VARCHAR);
                        t_cst8i_5.registerOutParameter(6,Types.VARCHAR);
                        t_cst8i_5.registerOutParameter(7,Types.VARCHAR);
                        t_cst8i_5.registerOutParameter(8,Types.VARCHAR);
                        t_cst8i_5.setString(1,empresa);
                        t_cst8i_5.setString(2,f_contrato);
                        t_cst8i_5.setString(3,f_producto);
                        t_cst8i_5.execute();
                        estado = t_cst8i_5.getString(8);
                    //                salida.println("<BR>Despues de condicion externos 2.<BR>");
                        try
                        {
                          if(!t_cst8i_5.getString(6).equals(null))
                             ref_valor = t_cst8i_5.getString(6);
                        }
                        catch(Exception ex){ref_valor = "100";}
                        t_cst8i_5.close();
                        empresa      = empresa_x;
                      }//si empresa_x no es nula
                      else if(empresa_x.equals("nula"))
                      {
                        empresa   = null;
                        ref_valor = "100";
                      }//si empresa _x es nula
                    }//si empresa papa es nula
                  }//si tipo aporte es E
                  //Realizo conversion del ref valor
                  Integer re_valor = new Integer(ref_valor);
                  int r_valor      = re_valor.intValue();
                  //FIN BUSQUEDA DE LOS CAMPOS REF_CONDICION Y DETALLE_CONDICION DE CADA HIJO(DE ACUERDO A T_A)
                  //INICIO CALCULO DE CAMPOS PARA INSERTARLE AL HIJO
                  numero_unidades       = (c[total_hijos]+rts[total_hijos])/v_u;
                  saldo_numero_unidades = numero_unidades;
                  num_uni_total += numero_unidades;
                  if(c[total_hijos]!=0)
                    porcentaje_r_h  = 0;
                  else
                    porcentaje_r_h  = 100;
                  select8i_2 = "SELECT APO_CONSECUTIVO_SQ.NEXTVAL FROM DUAL ";
                  PreparedStatement t_st8i_3= v_conexion_taxb.prepareStatement(select8i_2);
                  ResultSet t_rs8i_3 = t_st8i_3.executeQuery();
                  while(t_rs8i_3.next())
                    consecutivo_hijo = t_rs8i_3.getInt(1);
                    t_rs8i_3.close();
                    t_st8i_3.close();
                    //FIN CALCULO DE CAMPOS PARA INSERTARLE AL HIJO
                    //FIN ACTUALIZACION DE DISPONIBILIDADES DE CADA HIJO
                    //INICIO INSERT DE LOS HIJOS
                    CallableStatement t_cst8i_3 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Aporte_Hijo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
                    t_cst8i_3.registerOutParameter(35,Types.VARCHAR);
                    t_cst8i_3.setString(1,f_producto);
                    t_cst8i_3.setString(2,f_contrato);
                    t_cst8i_3.setInt(3,consecutivo_hijo);
                    t_cst8i_3.setString(4,fecha_p);
                    t_cst8i_3.setString(5,fecha_e);
                    t_cst8i_3.setString(6,transaccion);
                    t_cst8i_3.setString(7,t_t);
                    t_cst8i_3.setString(8,c_t);
                    t_cst8i_3.setDouble(9,c[total_hijos]);
                    t_cst8i_3.setDouble(10,rts[total_hijos]);
                    t_cst8i_3.setDouble(11,c_c[total_hijos]);
                    t_cst8i_3.setDouble(12,v_u);
                    t_cst8i_3.setDouble(13,numero_unidades);
                    t_cst8i_3.setString(14,certificado);
                    t_cst8i_3.setString(15,trasladado_aporte);
                    t_cst8i_3.setString(16,beneficiado_aporte);
                    t_cst8i_3.setString(17,cargado_aporte);
                    t_cst8i_3.setString(18,con_i_traslado);
                    t_cst8i_3.setString(19,estado_aporte);
                    t_cst8i_3.setDouble(20,c[total_hijos]);
                    t_cst8i_3.setDouble(21,rts[total_hijos]);
                    t_cst8i_3.setInt(22,porcentaje_r_h);
                    t_cst8i_3.setDouble(23,c_c[total_hijos]);
                    t_cst8i_3.setDouble(24,saldo_numero_unidades);
                    t_cst8i_3.setString(25,usuario);
                    t_cst8i_3.setString(26,f_c);
                    t_cst8i_3.setString(27,null);                   //RAZON DE BENEFICIO
                    t_cst8i_3.setDate(28,null);                    //FECHA DE BENEFICIO
                    t_cst8i_3.setString(29,null);                 //REF_CONDICION
                    t_cst8i_3.setString(30,null);                //DETALLE_CONDICION
                    t_cst8i_3.setString(31,null);
                    t_cst8i_3.setString(32,empresa);
                    t_cst8i_3.setString(33,afp);
                    t_cst8i_3.setInt(34,consecutivo);
                    t_cst8i_3.executeUpdate();
                    estado = t_cst8i_3.getString(35);
                    t_cst8i_3.close();
                   // salida.println("<BR>Despues de inserta aporte hijo.<BR>");
                   // salida.println("<BR>comision hijo: " + v_vr_comision_hijo + "<BR>");
                   // salida.println("<BR>comision hijo: " + v_vr_comision_hijo + "<BR>");
                    
                    
                    if(!estado.equals("BIEN"))
                    {
                      STBCL_GenerarBaseHTML plantilla5 = new STBCL_GenerarBaseHTML();
                      salida.println(plantilla5.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                  " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, TBPBD_Inserta_Aporte_Hijo"+estado,false));
                      salida.println("<BR><BR>");
                      salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                      salida.println(plantilla5.TBFL_PIE);
                      salida.close();
                    }
                    //FIN INSERT DE LOS HIJOS
                    //INICIO ACTUALIZACION DE DISPONIBILIDADES DE CADA HIJO
                    //ACTUALIZO DISPONIBILIDADES DE CADA HIJO
                    valor_d = ((c[total_hijos]+rts[total_hijos])*r_valor)/100;
                    CallableStatement t_cst8i_2 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Disponibilidad_E(?,?,?,?,?,?,?) }");
                    t_cst8i_2.registerOutParameter(7,Types.VARCHAR);
                    t_cst8i_2.setString(1,f_producto);
                    t_cst8i_2.setString(2,f_contrato);
                    t_cst8i_2.setInt(3,consecutivo_hijo);
                    t_cst8i_2.setString(4,fecha_e);
                    t_cst8i_2.setInt(5,r_valor);
                    t_cst8i_2.setDouble(6,valor_d);
                    t_cst8i_2.execute();
                    estado = t_cst8i_2.getString(7);
                    t_cst8i_2.close();
                    
                    //HIA 2012-05-24 Inserta valor correspondiente a la comision de los aportes hijos
                    CallableStatement t_comi_h = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Comision_Aporte(?,?,?,?) }");
                    t_comi_h.registerOutParameter(4,Types.VARCHAR);
                    t_comi_h.setLong(1,consecutivo_hijo);
                    t_comi_h.setString(2,transaccion);
                    t_comi_h.setDouble(3,v_vr_comision_hijo*-1);
                    t_comi_h.execute();
                    estado = t_comi_h.getString(4);
                    t_comi_h.close();
                    
        //            salida.println("<BR>Despues de inserta disponibilidad_E<BR>");
                    //INICIO CALCULO BENEFICIO PARA EL APO_CONSECUTIVO ACABADO DE INGRESAR, DESPUES DE CALCULARLO LO DEBO ACTUALIZAR
                    //calculo variables involucradas con el beneficio trin¿butario: aporte_beneficio,fecha_aporte,razon_beneficio,fecha_beneficio
                    CallableStatement t_cst8i_7 = v_conexion_taxb.prepareCall("{ call TBPBD_Tiene_Beneficio_Externos(?,?,?,?,?) }");
                    t_cst8i_7.registerOutParameter(4,Types.VARCHAR);
                    t_cst8i_7.registerOutParameter(5,Types.VARCHAR);
                    t_cst8i_7.setString(1,f_producto);
                    t_cst8i_7.setString(2,f_contrato);
                    t_cst8i_7.setInt(3,consecutivo_hijo);
                    t_cst8i_7.execute();
        //            salida.println("<BR>Despues de beneficio externos <BR>");
                    tiene_beneficio = t_cst8i_7.getString(4);
                    estado          = t_cst8i_7.getString(5);
                    t_cst8i_7.close();
                    if(!estado.equals("BIEN"))
                    {
                      STBCL_GenerarBaseHTML plantilla5 = new STBCL_GenerarBaseHTML();
                      salida.println(plantilla5.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                    " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, TBPBD_Tiene_Beneficio_Externos("+f_producto+","+f_contrato+","+consecutivo_hijo+","+tiene_beneficio+","+estado+")",false));
                      salida.println("<BR><BR>");
                      salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                      salida.println(plantilla5.TBFL_PIE);
                      salida.close();
                    }
                    if(tiene_beneficio.equals("v"))
                    {
                      //ACTUALIZO APORTE
                      CallableStatement t_cst8i_8 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_Externos(?,?,?,?,?) }");
                      t_cst8i_8.registerOutParameter(5,Types.VARCHAR);
                      t_cst8i_8.setString(1,f_producto);
                      t_cst8i_8.setString(2,f_contrato);
                      t_cst8i_8.setInt(3,consecutivo_hijo);
                      t_cst8i_8.setString(4,f_c);
                      t_cst8i_8.execute();
                      estado = t_cst8i_8.getString(5);
                      t_cst8i_8.close();
        //              salida.println("<BR>Despues de actualiza externos<BR>");
                      if(!estado.equals("BIEN"))
                      {
                        STBCL_GenerarBaseHTML plantilla6 = new STBCL_GenerarBaseHTML();
                        salida.println(plantilla6.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                  " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, TBPBD_Actualiza_Externos("+f_producto+","+f_contrato+","+consecutivo_hijo+","+f_c+","+estado+")",false));
                        salida.println("<BR><BR>");
                        salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                        salida.println(plantilla6.TBFL_PIE);
                        salida.close();
                      }
                    }//si tiene beenficio
                    //FIN CALCULO BENEFICIO PARA EL APO_CONSECUTIVO ACABADO DE INGRESAR, DESPUES DE CALCULARLO LO DEBO ACTUALIZAR
                    total_hijos++;
                  }//while encuentro hijos            
              }
              
               //salida.println("<BR>Capital: " + c[total_hijos-1] + " Rendimientos:" + rts[total_hijos-1]  +  "<BR>");
               //salida.println("<BR>Unidades: " + numero_unidades + " Rendimientos:" + rts[total_hijos-1]  +  "<BR>");
               //salida.println("<BR>capital papa:" + k + " Rendimientos papa:"  + rtos +  "<BR>");
               //salida.println("<BR>capital hijos:" + k_total + " rtos hijos:" + rto_total + "<BR>");
              
              //HIA 2012-05-24 Actualizar el ultimo aporte hijo en caso de presentarse alguna diferencia en los totales
              CallableStatement t_apoHij = v_conexion_taxb.prepareCall("{ call TBPBD_ACTUALIZAR_ULT_APO_HIJ(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
              t_apoHij.registerOutParameter(14,Types.NUMERIC);
              t_apoHij.registerOutParameter(15,Types.NUMERIC);
              t_apoHij.registerOutParameter(16,Types.VARCHAR);
              t_apoHij.setLong(1,consecutivo_hijo); // consecutivo ultimo aporte hijo
              t_apoHij.setDouble(2,c[total_hijos-1]); // capital ultimo aporte hijo
              t_apoHij.setDouble(3,rts[total_hijos-1]); // rendimientos ultimo aporte hijo
              t_apoHij.setDouble(4,numero_unidades); //numero de unidades ultimo aporte hijo
              t_apoHij.setDouble(5,v_vr_comision_hijo); // comision ultimo aporte hijo
              t_apoHij.setDouble(6,k); //capital aporte padre
              t_apoHij.setDouble(7,rtos); // rendimientos aporte padre
              t_apoHij.setDouble(8,numUnid); // numero unidades aporte padre 
              t_apoHij.setDouble(9,v_comision); //comision aportes padre
              t_apoHij.setDouble(10,k_total); //total capital hijos
              t_apoHij.setDouble(11,rto_total); //total rendimientos hijos
              t_apoHij.setDouble(12,num_uni_total); // total saldo unidades hijos
              t_apoHij.setDouble(13,v_vr_comision_total); //total comision calculada hijos
              t_apoHij.execute();
             // salida.println("<BR>Capital: " + t_apoHij.getDouble(14) + " Rendimientos:" + t_apoHij.getDouble(15)  +  "<BR>");
              c[total_hijos-1] = t_apoHij.getDouble(14);
              rts[total_hijos-1] = t_apoHij.getDouble(15);
              estado = t_apoHij.getString(16);
              
              t_apoHij.close();
          } 
          else
          {
             v_conexion_taxb.rollback();
             msj = " CARGA IMPOSIBlE, LOS TOTALES DE CAPITAL Y RENDIMIENTO DE LOS APORTES HIJOS NO CONCUERDAN CON LOS TOTALES DEL APORTE PAPA";
          
          }//FIN VALIDACION DE TOTALES          
          
          if(!datos_cargados)
          {
            usuario_invalido.TBPL_Usuario_Invalido(salida,f_contrato,n_id);
            estado_user = "MAL";
          }
          t_rs8i.close();
          t_st8i_2.close();
          //FIN ACTUALIZACION DEL CAMPO APO_INFORMACION_TRASLADO DEL PAPA("S")
          //VALIDO TOTALES DE LOS HIJOS CON LOS DEL PAPA
         /* totales_validos = compara_totales.TBPL_Compara_Totales(k_total12,rto_total12,k,rtos);
          if(!totales_validos)
          {
            v_conexion_taxb.rollback();
            msj = " CARGA IMPOSIBlE, LOS TOTALES DE CAPITAL Y RENDIMIENTO DE LOS APORTES HIJOS NO CONCUERDAN CON LOS TOTALES DEL APORTE PAPA";
          }*/
          double vdif = cc - cc_total;
          if (vdif >= 0.01)
          {
            //if(cc!=cc_total)
            vvalccta = false;
            v_conexion_taxb.rollback();
            msj_cc = ", LA SUMATORIA DE CUENTA CONTINGENTE DE LOS HIJOS ES DIFERENTE AL TOTAL DEL PAPA";
          }
        }//SI EL APORTE HALLADO NO TIENE RETIROS ASOCIADOS
        else
        {
          v_conexion_taxb.rollback();
          msj = "EL APORTE ELEGIDO PRESENTA RETIROS ASOCIADOS, IMPOSIBLE CONTINUAR CON LA CARGA";
        }//SI EL APORTE HALLADO TIENE RETIROS ASOCIADOS
      }//SI EL PRIMER SELECT ENCUENTRA EL APORTE SELECCIONADO
      else
      {
        v_conexion_taxb.rollback();
        msj = "EL APORTE ELEGIDO, NO SE ENCUENTRA DISPONIBLE, IMPOSIBLE CONTINUAR CON LA CARGA";
      }//SI EL PRIMER SELECT NO ENCUENTRA EL APORTE SELECCIONADO
      //VALIDO CAUSA DE ERROR PARA RELAIZAR O NO EL COMMIT
      if(aporte_hallado&&tieneretiros.equals("f")&&totales_validos==1&&estado_user.equals("BIEN"))
      {
        //PUEDO REALIZAR COMMIT FINAL
        //LLAMAR PROCEDIMIENTO QUE MUESTRE HIJOS Y PAPA
        //INICIO ACTUALIZACION DEL CAMPO APO_INFORMACION_TRASLADO DEL PAPA("S")
        //debo actualizar el aporte papa con informacion de traslado = "S"
        CallableStatement t_cst8i_4 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_Aporte_Papa(?,?,?,?) }");
        t_cst8i_4.registerOutParameter(4,Types.VARCHAR);
        t_cst8i_4.setString(1,f_producto);
        t_cst8i_4.setString(2,f_contrato);
        t_cst8i_4.setInt(3,consecutivo);
        t_cst8i_4.execute();
        estado = t_cst8i_4.getString(4);
        t_cst8i_4.close();
        if(!estado.equals("BIEN"))
        {
          STBCL_GenerarBaseHTML plantilla7 = new STBCL_GenerarBaseHTML();
          salida.println(plantilla7.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                          " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, TBPBD_Actualiza_Aporte_Papa("+f_producto+","+f_contrato+","+consecutivo+","+estado+")",false));
          salida.println("<BR><BR>");
          salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
          salida.println(plantilla7.TBFL_PIE);
          salida.close();
        }
        if(vvalccta)
        {
          v_conexion_taxb.commit();
        }
        else
        {
          v_conexion_taxb.rollback();
        }
        informacion_final.TBPL_Papa_Hijos(c,c_c,rts,k,cc,rtos,total_hijos,salida,msj,msj_cc,"INFORMACION DE TRASLADOS EXTERNOS DE APORTES");
      }//si todo ok
      else
      {
        v_conexion_taxb.rollback();
        //LLAMAR PROCEDIMIENTO QUE MUESTRE HIJOS Y PAPA, EXPLICANDO LA CAUSA DE ERROR
        informacion_final.TBPL_Papa_Hijos(c,c_c,rts,k,cc,rtos,total_hijos,salida,msj,msj_cc,"INFORMACION DE TRASLADOS EXTERNOS DE APORTES");
      }
      return;
    }
    //------------------------------------------------------------------pagina numero X
    salida.flush();
  }
  catch(Exception ex)
  {
    STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
    String msj = "SE PRODUJO UN ERROR INESPERADO, "+ex.toString() + " - " + ex.getMessage() +". INTENTE DE NUEVO.";
    salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                         "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS","<CENTER>"+msj+"</CEBTER>",false));
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

public void cargarHistoriaExterna(String v_contrato, String v_producto, String v_usuario, 
  String v_f_producto, String v_f_contrato, String v_codigo_afp, String v_nit_afp, String v_conse)
throws IOException
{
    Connection v_conexion_taxb    =   null;
try
{
  /*Variable agregada por APC para manejar el tamaño de los arreglos de una manera centralizada*/
  int MAX = 3000;
  //valido la veracidad del producto y contrato enviados por pipeline
  //seguridad
      v_conexion_taxb =   DataSourceWrapper.getInstance().getConnection();
  TBCL_HTML   parametros_requeridos     = new TBCL_HTML();
  TBCL_HTML   hoja_error                = new TBCL_HTML();
  TBCL_HTML   publica_afp               = new TBCL_HTML();
  TBCL_HTML   publica_aportes           = new TBCL_HTML();
  TBCL_HTML   afp_existe                = new TBCL_HTML();
  TBCL_HTML   nombres                   = new TBCL_HTML();
  TBCL_HTML   compara_totales           = new TBCL_HTML();
  TBCL_HTML   informacion_final         = new TBCL_HTML();
  TBCL_HTML   usuario_invalido          = new TBCL_HTML();
  //valido la veracidad del producto y contrato enviados por pipeline
  String  producto    = v_producto;//peticion.getParameter("nom_producto");
  String  contrato    = v_contrato;//peticion.getParameter("num_contrato");
  String  usuario     = v_usuario;//peticion.getParameter("usuario");

  if(producto != null)
  {
    v_producto = "X";
    v_contrato = "X";
  }
  //------------------------------------------------------------------pagina numero 1
  if(parametros_requeridos.TBPL_Parametros_Requeridos(v_producto,v_contrato))
  {
    String codigo_afp[] = new String[500];
    String nombre_afp[] = new String[500];
    String nit_afp[]    = new String[500];
    int total_afp       = 0;
    String select8i_0   ="SELECT "+
                        "AFP_CODIGO "+
                        ",AFP_DESCRIPCION "+
                        ",AFP_NIT "+
                        "FROM "+
                        "TBADM_FONDOS_PENSIONES "+
                        "WHERE "+
                        "AFP_REF_TIPO = 'STA002' "+
                        "ORDER BY AFP_DESCRIPCION ";
    PreparedStatement t_st8i = v_conexion_taxb.prepareStatement(select8i_0);
    ResultSet t_rs8i         = t_st8i.executeQuery();
    while(t_rs8i.next())
    {
      codigo_afp[total_afp] = t_rs8i.getString(1);
      nombre_afp[total_afp] = t_rs8i.getString(2);
      nit_afp[total_afp]    = t_rs8i.getString(3);
      total_afp++;
    }
    t_rs8i.close();
    t_st8i.close();
    return;
  }
  else if ((!parametros_requeridos.TBPL_Parametros_Requeridos(producto,contrato))&&
          ( producto == null)&&
          ( contrato == null))
  {
    STBCL_GenerarBaseHTML plantilla2 = new STBCL_GenerarBaseHTML();
    String msj = "EL CONTRATO "+v_contrato+" NO EXISTE EN EL SISTEMA.";
    System.out.println(plantilla2.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                       "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS","<font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><CENTER>"+msj+"</CEBTER></font></b>",false));
    System.out.println("<BR><BR>");
    System.out.println("<center><input type='button' value='Aceptar' Onclick='history.go(-1);' ></center>");
    System.out.println(plantilla2.TBFL_PIE);
    return;
  }
    //------------------------------------------------------------------pagina numero 2
  else if
       ((!parametros_requeridos.TBPL_Parametros_Requeridos(producto,contrato))&&
       (!producto.equals("A"))&&
       (!contrato.equals("A"))&&
       (!producto.equals("B"))&&
       (!contrato.equals("B")))
  {
    /////hoja_error.TBPL_Hoja_Error(producto,contrato,salida,0,"TBS_CARGA_APORTES_EXTERNOS1",
    /////                      "INFORMACION DE TRASLADOS EXTERNOS");
    return;
  }
  //------------------------------------------------------------------pagina numero 2
  else if((producto.equals("A"))&&(contrato.equals("A")))
  {
    String  f_producto    = v_producto;////peticion.getParameter("f_producto");
    String  f_contrato    = v_contrato;////peticion.getParameter("f_contrato");
    String  nombre_afp    = " ";
    String  nit_afp       = " ";
    String  codigo_afp    = " ";
    String  cadena        = "";/////peticion.getParameter("fila");
    int     indice        = 0;
    indice                = cadena.indexOf("$");
    codigo_afp            = cadena.substring(0,indice);
    cadena                = cadena.substring(indice+1,cadena.length());
    indice                = cadena.indexOf("$");
    nombre_afp            = cadena.substring(0,indice);
    nit_afp               = cadena.substring(indice+1,cadena.length());
    String fecha_e[]      = new String[MAX];
    double c[]            = new double[MAX];
    double cc[]           = new double[MAX];
    double rtos[]         = new double[MAX];
    String nomb_afp[]     = new String[MAX];
    int[] consecutivos    = new int[MAX];
    int total_aportes     = 0;
    if(nit_afp.length()==13)
    {
      nit_afp = nit_afp.substring(2,13);
    }
    else
    {
      nit_afp = nit_afp.substring(0,nit_afp.length());
    }
    if(afp_existe.TBPL_AFP(nit_afp,v_conexion_taxb))
    {
      String select8i_0     = "SELECT "+
                              "TO_CHAR(APO_FECHA_EFECTIVA,'RRRR-MM-DD'), "+
                              "APO_CAPITAL, "+
                              "APO_RENDIMIENTOS, "+
                              "APO_CUENTA_CONTINGENTE, "+
                              "APO_CONSECUTIVO, "+
                              "AFP_DESCRIPCION "+
                              "FROM TBAPORTES,TBADM_FONDOS_PENSIONES "+
                              "WHERE "+
                              "APO_CON_PRO_CODIGO           = ? "+
                              "AND APO_CON_NUMERO           = ? "+
                              "AND APO_AFP_CODIGO           = ? "+
                              "AND APO_APORTE_TRASLADO      = 'S' "+
                              "AND APO_INFORMACION_TRASLADO = 'N' "+
                              "AND APO_AFP_CODIGO           = AFP_CODIGO "+
                              "AND AFP_REF_TIPO             = 'STA002' "+
                              "ORDER BY APO_FECHA_EFECTIVA DESC";
//      salida.println("<BR>Despues de select de afp existe.<BR>");
      PreparedStatement t_st8i = v_conexion_taxb.prepareStatement(select8i_0);
      t_st8i.setString(1,f_producto);
      t_st8i.setString(2,f_contrato);
      t_st8i.setString(3,codigo_afp);
//      salida.println("<BR>Antes de publica aportes.<BR>");
//      salida.println("<BR>Query : "+ t_st8i + "<BR>");
      ResultSet t_rs8i       = t_st8i.executeQuery();
      while(t_rs8i.next())
      {
        fecha_e[total_aportes]      = t_rs8i.getString(1);
        c[total_aportes]            = t_rs8i.getDouble(2);
        rtos[total_aportes]         = t_rs8i.getDouble(3);
        cc[total_aportes]           = t_rs8i.getDouble(4);
        consecutivos[total_aportes] = t_rs8i.getInt(5);
        nomb_afp[total_aportes]     = t_rs8i.getString(6);
        total_aportes++;
//        salida.println("<BR>aporte " + total_aportes + ".<BR>");
      }
      t_rs8i.close();
      t_st8i.close();
      ////publica_aportes.TBPL_Publica_Aportes(f_producto,f_contrato,fecha_e,cc,c,rtos,total_aportes,salida,usuario,nomb_afp,consecutivos
      ////                                    ,nombres.TBPL_Nombres(f_producto,f_contrato),codigo_afp,nit_afp,nuevaCadena);
      
    }//si afp se encuentra cargada en TBINTERFACE_TRASLADOS
    else
    {
      STBCL_GenerarBaseHTML plantilla1 = new STBCL_GenerarBaseHTML();
      System.out.println(plantilla1.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                            "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS1"," ",true));
      System.out.println("<BR><BR>");
      System.out.println("<p><B><FONT color=black face=verdana size=2> NO HAY INFORMACION CARGADA PARA LA AFP SELECCIONADA </font></B></p>");
      System.out.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
      System.out.println(plantilla1.TBFL_PIE);
      //salida.close();
    }//si la afp no se encuentra cargada en TBINTERFACE_TRASLADOS
    return;
  }
  //------------------------------------------------------------------pagina numero 3
  else if((producto.equals("B"))&&(contrato.equals("B")))
  {
    boolean datos_cargados= false;
    String  estado_user   = "BIEN";
    String  estado        = " ";
    String  f_producto    = v_f_producto;////peticion.getParameter("f_producto");
    String  f_contrato    = v_f_contrato;////peticion.getParameter("f_contrato");
    String  codigo_afp    = v_codigo_afp;////peticion.getParameter("codigo_afp");
    String  nit_afp       = v_nit_afp;////peticion.getParameter("nit_afp");
    String  conse         = v_conse;////peticion.getParameter("consecutivo");
    Integer c_temp        = new Integer(conse);
    String tieneretiros   = " ";
    int consecutivo       = c_temp.intValue();
    boolean aporte_hallado    = false;
    boolean totales_validos   = true;
    String  fecha_e       = " ";
    double  k             = 0;
    double cc             = 0;
    double rtos           = 0;
    double v_u            = 0;
    boolean vvalccta      = true;
    String fecha_p        = " ";
    String transaccion    = " ";
    String t_t            = " ";
    String c_t            = " ";
    String certificado    = " ";
    String condicion      = " ";
    String detalle        = " ";
    String trs            = " ";
    String empresa        = new String(" ");
    String afp            = " ";
    String nomb_afp       = " ";
    String i_traslado     = " ";
    String a_traslado     = " ";
    String f_i            = " ";
    String t_i            = " ";
    long    n_i            = 0;
    String f_c            = " ";
    String t_a            = " ";
    double c[]            = new double[MAX];
    double rts[]          = new double[MAX];
    double c_c[]          = new double[MAX];
    double k_total        = 0;
    double k_total1       = 0;
    double k_total12      = 0;
    double rto_total      = 0;
    double rto_total1     = 0;
    double rto_total12    = 0;
    double cc_total       = 0;
    String msj            = " ";
    String msj_cc         = " ";
    String select8i_1     = " ";
    int total_hijos       = 0;
    //INICIO CALCULO DE CAMPOS PARA INSERTARLE AL HIJO
    String estado_aporte         = "SEA001";
    String certificacion_aporte  = " ";
    String trasladado_aporte     = "N";
    String cargado_aporte        = "N";
    String con_i_traslado        = "N";
    double numero_unidades       = 0;
    double saldo_numero_unidades = 0;
    int    consecutivo_hijo      = 0;
    int    porcentaje_r_h        = 0;
    int    p_maximo              = 0;
    double valor_d               = 0;
    String beneficiado_aporte    = "N";
    String razon_beneficio       = " ";
    java.sql.Date fecha_beneficio= new java.sql.Date(1,1,1);
    String detalle_condicion     = new String(" ");
    String ref_condicion         = new String(" ");
    String empresa_x             = new String(" ");
    String select8i_2            = new String(" ");
    String tiene_beneficio       = " ";
    String usuario_valido        = " ";
    String ref_valor             = new String(" ");
    String t_id                  = "";
    long n_id                     = 0;
    //FIN CALCULO DE CAMPOS PARA INSERTARLE AL HIJO
    
    String select8i_0     = "SELECT "+
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
                          "NVL(APO_EMP_GRUPO,'#$%$'),"+
                          "APO_AFP_CODIGO,"+
                          "AFP_DESCRIPCION,"+
                          "APO_INFORMACION_TRASLADO,"+
                          "APO_APORTE_TRASLADO,"+
                          "CON_REF_TIPO_IDENTIFICACION,"+
                          "CON_NUMERO_IDENTIFICACION "+
                          "FROM TBAPORTES,TBADM_FONDOS_PENSIONES,TBCONTRATOS,TBREFERENCIAS "+
                          "WHERE "+
                          "APO_AFP_CODIGO         = AFP_CODIGO "+
                          "AND APO_CON_PRO_CODIGO = CON_PRO_CODIGO "+
                          "AND APO_CON_NUMERO     = CON_NUMERO "+
                          "AND CON_REF_TIPO_IDENTIFICACION = REF_CODIGO "+
                          "AND APO_CON_PRO_CODIGO = ? "+
                          "AND APO_CON_NUMERO     = ? "+
                          "AND APO_CONSECUTIVO    = ? ";
    PreparedStatement t_st8i_1 = v_conexion_taxb.prepareStatement(select8i_0);
    t_st8i_1.setString(1,f_producto);
    t_st8i_1.setString(2,f_contrato);
    t_st8i_1.setInt(3,consecutivo);
    ResultSet t_rs8i = t_st8i_1.executeQuery();
    while(t_rs8i.next())
    {
      aporte_hallado = true;
      fecha_e        = t_rs8i.getString(1);
      k              = t_rs8i.getDouble(2);
      cc             = t_rs8i.getDouble(4);
      rtos           = t_rs8i.getDouble(3);
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
      t_id           = t_rs8i.getString(19);
      n_id           = t_rs8i.getLong(20);
    }//while
    t_rs8i.close();
    t_st8i_1.close();
    if(aporte_hallado)
    {
      //VALIDO RETIROS ASOCIADOS AL APORTE HALLADO
      CallableStatement t_cst8i_0 = v_conexion_taxb.prepareCall("{ call TBPBD_Valida_Retiros_Externos(?,?,?,?,?) }");
      t_cst8i_0.registerOutParameter(4,Types.VARCHAR);
      t_cst8i_0.registerOutParameter(5,Types.VARCHAR);
      t_cst8i_0.setString(1,f_producto);
      t_cst8i_0.setString(2,f_contrato);
      t_cst8i_0.setInt(3,consecutivo);
      t_cst8i_0.execute();
      tieneretiros = t_cst8i_0.getString(4);
      estado       = t_cst8i_0.getString(5);
      t_cst8i_0.close();
      if(!estado.equals("BIEN"))
      {
        STBCL_GenerarBaseHTML plantilla2 = new STBCL_GenerarBaseHTML();
        System.out.println(plantilla2.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                         " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, TBPBD_Valida_Retiros_Externos("+f_producto+","+f_contrato+","+consecutivo+","+tieneretiros+","+estado+")",false));
        System.out.println("<BR><BR>");
        System.out.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
        System.out.println(plantilla2.TBFL_PIE);
      }
      if(tieneretiros.equals("f")&&estado.equals("BIEN"))
      {
        //BUSCO LOS HIJOS
        select8i_1 = "SELECT "+
                     "INT_FECHA "+
                     ",INT_NIT_AFP_ORIGEN "+
                     ",DECODE(INT_TIPO_IDENTIFICACION,'CC','UTI001','TI','UTI003','CE','UTI002','UTI000')"+
                     ",INT_NUMERO_IDENTIFICACION "+
                     ",TO_CHAR(INT_FECHA_CONSIGNACION,'RRRR-MM-DD') "+
                     ",INT_TIPO_APORTE "+
                     ",INT_VALOR_HISTORICO "+
                     ",INT_RETENCION_CONTINGENTE "+
                     ",INT_RENDIMIENTOS "+
                     "FROM "+
                     "TBINTERFACE_TRASLADOS "+
                     "WHERE "+
                     "INT_NIT_AFP_ORIGEN            = ? "+
                     "AND DECODE(INT_TIPO_IDENTIFICACION,'CC','UTI001','TI','UTI003','CE','UTI002','UTI000') = ? "+
                     "AND INT_NUMERO_IDENTIFICACION = ? ";
        PreparedStatement t_st8i_2 = v_conexion_taxb.prepareStatement(select8i_1);
        t_st8i_2.setString(1,nit_afp);
        t_st8i_2.setString(2,t_id);
        t_st8i_2.setLong(3,n_id);
        t_rs8i = t_st8i_2.executeQuery();
        System.out.println("<BR>Despues de busqueda de hijos.<BR>");
        System.out.println("<BR>Query:<BR>"+t_st8i_2+"<BR>");
        datos_cargados = true;
        while(t_rs8i.next())
        {
          datos_cargados = true;
          f_i = t_rs8i.getString(1);
          t_i = t_rs8i.getString(3);
          n_i = t_rs8i.getLong(4);
          f_c = t_rs8i.getString(5);
          t_a = t_rs8i.getString(6);
            
          // PHTM I&T 2016-09-02 Se retira instruccion de restar el capital de los aportes por el requerimieto 28366
          // cuando los rendimientos son negativos            
          /*if(t_rs8i.getDouble(9)<0)
          {
            c[total_hijos]    = t_rs8i.getDouble(7)+t_rs8i.getDouble(9);
            rts[total_hijos]  = 0;
            k_total1          = t_rs8i.getDouble(7);
            rto_total1        = t_rs8i.getDouble(9);
          }
          else
          {*/
            c[total_hijos]    = t_rs8i.getDouble(7);
            rts[total_hijos]  = t_rs8i.getDouble(9);
            k_total1          = t_rs8i.getDouble(7);
            rto_total1        = t_rs8i.getDouble(9);
          //}
          c_c[total_hijos]    = t_rs8i.getDouble(8);
          k_total            += c[total_hijos];
          k_total12          += k_total1;
          rto_total          += rts[total_hijos];
          rto_total12        += rto_total1;
          cc_total           += c_c[total_hijos];
          //INICIO BUSQUEDA DEL REF_VALOR DE CADA HIJO(DE ACUERDO A T_A)
//          salida.println("<BR>Inicio de busqueda de ref valor.<BR>");
          if(t_a.equals("A"))
          {
            empresa   = null;
            ref_valor = "100";
          }//si tipo aporte es A
          else if(t_a.equals("E"))
          {
            if (empresa == null || empresa.equals("")) empresa = " ";
            if(!empresa.substring(0,1).equals(" "))
            {
              CallableStatement t_cst8i_5 = v_conexion_taxb.prepareCall("{ call TBPBD_CONDICION_EXTERNOS(?,?,?,?,?,?,?,?) }");
              t_cst8i_5.registerOutParameter(4,Types.VARCHAR);
              t_cst8i_5.registerOutParameter(5,Types.VARCHAR);
              t_cst8i_5.registerOutParameter(6,Types.VARCHAR);
              t_cst8i_5.registerOutParameter(7,Types.VARCHAR);
              t_cst8i_5.registerOutParameter(8,Types.VARCHAR);
              t_cst8i_5.setString(1,empresa);
              t_cst8i_5.setString(2,f_contrato);
              t_cst8i_5.setString(3,f_producto);
              t_cst8i_5.execute();
              estado = t_cst8i_5.getString(8);
              try
              {
                if(!t_cst8i_5.getString(6).equals(null))
                  ref_valor = t_cst8i_5.getString(6);
              }
              catch(Exception ex){ref_valor = "100";}
              t_cst8i_5.close();
//              salida.println("<BR>Despues de condicion externos<BR>");
              if(!estado.equals("BIEN"))
              {
                STBCL_GenerarBaseHTML plantilla3 = new STBCL_GenerarBaseHTML();
                System.out.println(plantilla3.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                         " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO,TBPBD_CONDICION_EXTERNOS( "+empresa+","+f_contrato+","+f_producto+","+estado+")",false));
                System.out.println("<BR><BR>");
                System.out.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                System.out.println(plantilla3.TBFL_PIE);
              }
            }//si empresa papa no es nula
            else if(empresa.substring(0,1).equals(" "))
            {
              CallableStatement t_cst8i_6 = v_conexion_taxb.prepareCall("{ call TBPBD_Empresa_Externos(?,?,?,?) }");
              t_cst8i_6.registerOutParameter(3,Types.VARCHAR);
              t_cst8i_6.registerOutParameter(4,Types.VARCHAR);
              t_cst8i_6.setString(1,f_producto);
              t_cst8i_6.setString(2,f_contrato);
              t_cst8i_6.execute();
              empresa_x = t_cst8i_6.getString(3);
              estado    = t_cst8i_6.getString(4);
              t_cst8i_6.close();
//              salida.println("<BR>Despues de empresa externos<BR>");
              if(!estado.equals("BIEN"))
              {
                STBCL_GenerarBaseHTML plantilla4 = new STBCL_GenerarBaseHTML();
                System.out.println(plantilla4.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                 " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO,TBPBD_Empresa_Externos( "+f_producto+","+f_contrato+","+empresa_x+","+estado+")",false));
                System.out.println("<BR><BR>");
                System.out.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                System.out.println(plantilla4.TBFL_PIE);
              }
              if(!empresa_x.equals("nula"))
              {
                CallableStatement t_cst8i_5 = v_conexion_taxb.prepareCall("{ call TBPBD_CONDICION_EXTERNOS(?,?,?,?,?,?,?,?) }");
                t_cst8i_5.registerOutParameter(4,Types.VARCHAR);
                t_cst8i_5.registerOutParameter(5,Types.VARCHAR);
                t_cst8i_5.registerOutParameter(6,Types.VARCHAR);
                t_cst8i_5.registerOutParameter(7,Types.VARCHAR);
                t_cst8i_5.registerOutParameter(8,Types.VARCHAR);
                t_cst8i_5.setString(1,empresa);
                t_cst8i_5.setString(2,f_contrato);
                t_cst8i_5.setString(3,f_producto);
                t_cst8i_5.execute();
                estado = t_cst8i_5.getString(8);
//                salida.println("<BR>Despues de condicion externos 2.<BR>");
                try
                {
                  if(!t_cst8i_5.getString(6).equals(null))
                     ref_valor = t_cst8i_5.getString(6);
                }
                catch(Exception ex){ref_valor = "100";}
                t_cst8i_5.close();
                empresa      = empresa_x;
              }//si empresa_x no es nula
              else if(empresa_x.equals("nula"))
              {
                empresa   = null;
                ref_valor = "100";
              }//si empresa _x es nula
            }//si empresa papa es nula
          }//si tipo aporte es E
          //Realizo conversion del ref valor
          Integer re_valor = new Integer(ref_valor);
          int r_valor      = re_valor.intValue();
          //FIN BUSQUEDA DE LOS CAMPOS REF_CONDICION Y DETALLE_CONDICION DE CADA HIJO(DE ACUERDO A T_A)
          //INICIO CALCULO DE CAMPOS PARA INSERTARLE AL HIJO
          numero_unidades       = (c[total_hijos]+rts[total_hijos])/v_u;
          saldo_numero_unidades = numero_unidades;
          if(c[total_hijos]!=0)
            porcentaje_r_h  = 0;
          else
            porcentaje_r_h  = 100;
          select8i_2 = "SELECT APO_CONSECUTIVO_SQ.NEXTVAL FROM DUAL ";
          PreparedStatement t_st8i_3= v_conexion_taxb.prepareStatement(select8i_2);
          ResultSet t_rs8i_3 = t_st8i_3.executeQuery();
          while(t_rs8i_3.next())
            consecutivo_hijo = t_rs8i_3.getInt(1);
            t_rs8i_3.close();
            t_st8i_3.close();
            //FIN CALCULO DE CAMPOS PARA INSERTARLE AL HIJO
            //FIN ACTUALIZACION DE DISPONIBILIDADES DE CADA HIJO
            //INICIO INSERT DE LOS HIJOS
            CallableStatement t_cst8i_3 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Aporte_Hijo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
            t_cst8i_3.registerOutParameter(35,Types.VARCHAR);
            t_cst8i_3.setString(1,f_producto);
            t_cst8i_3.setString(2,f_contrato);
            t_cst8i_3.setInt(3,consecutivo_hijo);
            t_cst8i_3.setString(4,fecha_p);
            t_cst8i_3.setString(5,fecha_e);
            t_cst8i_3.setString(6,transaccion);
            t_cst8i_3.setString(7,t_t);
            t_cst8i_3.setString(8,c_t);
            t_cst8i_3.setDouble(9,c[total_hijos]);
            t_cst8i_3.setDouble(10,rts[total_hijos]);
            t_cst8i_3.setDouble(11,c_c[total_hijos]);
            t_cst8i_3.setDouble(12,v_u);
            t_cst8i_3.setDouble(13,numero_unidades);
            t_cst8i_3.setString(14,certificado);
            t_cst8i_3.setString(15,trasladado_aporte);
            t_cst8i_3.setString(16,beneficiado_aporte);
            t_cst8i_3.setString(17,cargado_aporte);
            t_cst8i_3.setString(18,con_i_traslado);
            t_cst8i_3.setString(19,estado_aporte);
            t_cst8i_3.setDouble(20,c[total_hijos]);
            t_cst8i_3.setDouble(21,rts[total_hijos]);
            t_cst8i_3.setInt(22,porcentaje_r_h);
            t_cst8i_3.setDouble(23,c_c[total_hijos]);
            t_cst8i_3.setDouble(24,saldo_numero_unidades);
            t_cst8i_3.setString(25,usuario);
            t_cst8i_3.setString(26,f_c);
            t_cst8i_3.setString(27,null);                   //RAZON DE BENEFICIO
            t_cst8i_3.setDate(28,null);                    //FECHA DE BENEFICIO
            t_cst8i_3.setString(29,null);                 //REF_CONDICION
            t_cst8i_3.setString(30,null);                //DETALLE_CONDICION
            t_cst8i_3.setString(31,null);
            t_cst8i_3.setString(32,empresa);
            t_cst8i_3.setString(33,afp);
            t_cst8i_3.setInt(34,consecutivo);
            t_cst8i_3.executeUpdate();
            estado = t_cst8i_3.getString(35);
            t_cst8i_3.close();
//            salida.println("<BR>Despues de inserta aporte hijo.<BR>");
            if(!estado.equals("BIEN"))
            {
              STBCL_GenerarBaseHTML plantilla5 = new STBCL_GenerarBaseHTML();
              System.out.println(plantilla5.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                          " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, TBPBD_Inserta_Aporte_Hijo"+estado,false));
              System.out.println("<BR><BR>");
              System.out.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
              System.out.println(plantilla5.TBFL_PIE);
            }
            //FIN INSERT DE LOS HIJOS
            //INICIO ACTUALIZACION DE DISPONIBILIDADES DE CADA HIJO
            //ACTUALIZO DISPONIBILIDADES DE CADA HIJO
            valor_d = ((c[total_hijos]+rts[total_hijos])*r_valor)/100;
            CallableStatement t_cst8i_2 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Disponibilidad_E(?,?,?,?,?,?,?) }");
            t_cst8i_2.registerOutParameter(7,Types.VARCHAR);
            t_cst8i_2.setString(1,f_producto);
            t_cst8i_2.setString(2,f_contrato);
            t_cst8i_2.setInt(3,consecutivo_hijo);
            t_cst8i_2.setString(4,fecha_e);
            t_cst8i_2.setInt(5,r_valor);
            t_cst8i_2.setDouble(6,valor_d);
            t_cst8i_2.execute();
            estado = t_cst8i_2.getString(7);
            t_cst8i_2.close();
//            salida.println("<BR>Despues de inserta disponibilidad_E<BR>");
            //INICIO CALCULO BENEFICIO PARA EL APO_CONSECUTIVO ACABADO DE INGRESAR, DESPUES DE CALCULARLO LO DEBO ACTUALIZAR
            //calculo variables involucradas con el beneficio trin¿butario: aporte_beneficio,fecha_aporte,razon_beneficio,fecha_beneficio
            CallableStatement t_cst8i_7 = v_conexion_taxb.prepareCall("{ call TBPBD_Tiene_Beneficio_Externos(?,?,?,?,?) }");
            t_cst8i_7.registerOutParameter(4,Types.VARCHAR);
            t_cst8i_7.registerOutParameter(5,Types.VARCHAR);
            t_cst8i_7.setString(1,f_producto);
            t_cst8i_7.setString(2,f_contrato);
            t_cst8i_7.setInt(3,consecutivo_hijo);
            t_cst8i_7.execute();
//            salida.println("<BR>Despues de beneficio externos <BR>");
            tiene_beneficio = t_cst8i_7.getString(4);
            estado          = t_cst8i_7.getString(5);
            t_cst8i_7.close();
            if(!estado.equals("BIEN"))
            {
              STBCL_GenerarBaseHTML plantilla5 = new STBCL_GenerarBaseHTML();
              System.out.println(plantilla5.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                            " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, TBPBD_Tiene_Beneficio_Externos("+f_producto+","+f_contrato+","+consecutivo_hijo+","+tiene_beneficio+","+estado+")",false));
              System.out.println("<BR><BR>");
              System.out.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
              System.out.println(plantilla5.TBFL_PIE);
            }
            if(tiene_beneficio.equals("v"))
            {
              //ACTUALIZO APORTE
              CallableStatement t_cst8i_8 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_Externos(?,?,?,?,?) }");
              t_cst8i_8.registerOutParameter(5,Types.VARCHAR);
              t_cst8i_8.setString(1,f_producto);
              t_cst8i_8.setString(2,f_contrato);
              t_cst8i_8.setInt(3,consecutivo_hijo);
              t_cst8i_8.setString(4,f_c);
              t_cst8i_8.execute();
              estado = t_cst8i_8.getString(5);
              t_cst8i_8.close();
//              salida.println("<BR>Despues de actualiza externos<BR>");
              if(!estado.equals("BIEN"))
              {
                STBCL_GenerarBaseHTML plantilla6 = new STBCL_GenerarBaseHTML();
                System.out.println(plantilla6.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                          " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, TBPBD_Actualiza_Externos("+f_producto+","+f_contrato+","+consecutivo_hijo+","+f_c+","+estado+")",false));
                System.out.println("<BR><BR>");
                System.out.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                System.out.println(plantilla6.TBFL_PIE);
              }
            }//si tiene beenficio
            //FIN CALCULO BENEFICIO PARA EL APO_CONSECUTIVO ACABADO DE INGRESAR, DESPUES DE CALCULARLO LO DEBO ACTUALIZAR
            total_hijos++;
          }//while encuentro hijos
          if(!datos_cargados)
          {
            ////usuario_invalido.TBPL_Usuario_Invalido(salida,f_contrato,n_id);
            estado_user = "MAL";
          }
          t_rs8i.close();
          t_st8i_2.close();
          //FIN ACTUALIZACION DEL CAMPO APO_INFORMACION_TRASLADO DEL PAPA("S")
          //VALIDO TOTALES DE LOS HIJOS CON LOS DEL PAPA
          totales_validos = compara_totales.TBPL_Compara_Totales(k_total12,rto_total12,k,rtos);
          System.out.println("k_total12"+k_total12+"rto_total12"+rto_total12+"k"+k+"rtos"+rtos);
          if(!totales_validos)
          {
            v_conexion_taxb.rollback();
            msj = " CARGA IMPOSIBlE, LOS TOTALES DE CAPITAL Y RENDIMIENTO DE LOS APORTES HIJOS NO CONCUERDAN CON LOS TOTALES DEL APORTE PAPA";
            System.out.println(msj);
          }
          double vdif = cc - cc_total;
          if (vdif >= 0.01)
          {
            //if(cc!=cc_total)
            vvalccta = false;
            v_conexion_taxb.rollback();
            msj_cc = ", LA SUMATORIA DE CUENTA CONTINGENTE DE LOS HIJOS ES DIFERENTE AL TOTAL DEL PAPA";
          }
        }//SI EL APORTE HALLADO NO TIENE RETIROS ASOCIADOS
        else
        {
          v_conexion_taxb.rollback();
          msj = "EL APORTE ELEGIDO PRESENTA RETIROS ASOCIADOS, IMPOSIBLE CONTINUAR CON LA CARGA";
        }//SI EL APORTE HALLADO TIENE RETIROS ASOCIADOS
      }//SI EL PRIMER SELECT ENCUENTRA EL APORTE SELECCIONADO
      else
      {
        v_conexion_taxb.rollback();
        msj = "EL APORTE ELEGIDO, NO SE ENCUENTRA DISPONIBLE, IMPOSIBLE CONTINUAR CON LA CARGA";
      }//SI EL PRIMER SELECT NO ENCUENTRA EL APORTE SELECCIONADO
      //VALIDO CAUSA DE ERROR PARA RELAIZAR O NO EL COMMIT
      if(aporte_hallado&&tieneretiros.equals("f")&&totales_validos&&estado_user.equals("BIEN"))
      {
        //PUEDO REALIZAR COMMIT FINAL
        //LLAMAR PROCEDIMIENTO QUE MUESTRE HIJOS Y PAPA
        //INICIO ACTUALIZACION DEL CAMPO APO_INFORMACION_TRASLADO DEL PAPA("S")
        //debo actualizar el aporte papa con informacion de traslado = "S"
        CallableStatement t_cst8i_4 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_Aporte_Papa(?,?,?,?) }");
        t_cst8i_4.registerOutParameter(4,Types.VARCHAR);
        t_cst8i_4.setString(1,f_producto);
        t_cst8i_4.setString(2,f_contrato);
        t_cst8i_4.setInt(3,consecutivo);
        t_cst8i_4.execute();
        estado = t_cst8i_4.getString(4);
        t_cst8i_4.close();
        if(!estado.equals("BIEN"))
        {
          STBCL_GenerarBaseHTML plantilla7 = new STBCL_GenerarBaseHTML();
          System.out.println(plantilla7.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                          " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, TBPBD_Actualiza_Aporte_Papa("+f_producto+","+f_contrato+","+consecutivo+","+estado+")",false));
          System.out.println("<BR><BR>");
          System.out.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
          System.out.println(plantilla7.TBFL_PIE);
        }
        if(vvalccta)
        {
          v_conexion_taxb.commit();
        }
        else
        {
          v_conexion_taxb.rollback();
        }
        ////informacion_final.TBPL_Papa_Hijos(c,c_c,rts,k,cc,rtos,total_hijos,salida,msj,msj_cc,"INFORMACION DE TRASLADOS EXTERNOS DE APORTES");
      }//si todo ok
      else
      {
        v_conexion_taxb.rollback();
        //LLAMAR PROCEDIMIENTO QUE MUESTRE HIJOS Y PAPA, EXPLICANDO LA CAUSA DE ERROR
        ////informacion_final.TBPL_Papa_Hijos(c,c_c,rts,k,cc,rtos,total_hijos,salida,msj,msj_cc,"INFORMACION DE TRASLADOS EXTERNOS DE APORTES");
      }
      return;
    }
    //------------------------------------------------------------------pagina numero X
    //salida.flush();
  }
  catch(Exception ex)
  {
    STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
    String msj = "SE PRODUJO UN ERROR INESPERADO, "+ex.toString() + " - " + ex.getMessage() +". INTENTE DE NUEVO.";
    System.out.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS",
                                         "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS","<CENTER>"+msj+"</CEBTER>",false));
    System.out.println("<BR><BR>");
    System.out.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
    System.out.println(plantilla.TBFL_PIE);
    //salida.flush();
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
//----------------------------------------------------------------------------------------------------
public String getServletInfo()
{return "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS1 Information";}
//----------------------------------------------------------------------------------------------------
}
