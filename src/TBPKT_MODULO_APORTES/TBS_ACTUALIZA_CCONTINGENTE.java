package TBPKT_MODULO_APORTES;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import javax.servlet.http.*;
import javax.servlet.*;
import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.*;
import java.text.*;
import java.sql.*;
import java.text.*;
import java.io.*;

import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;

import co.oldmutual.taxbenefit.util.DataSourceWrapper; // INT20131108


/**
 *Este servlet servirá para modificar aportes en su calidad de fecha aporte, capital,
 *rendimientos y cuenta contingente, generándose un ajuste para aquellos
 *aportes que se modifiquen y que tengan retiros asociados
 */


public class TBS_ACTUALIZA_CCONTINGENTE extends HttpServlet implements SingleThreadModel{
int v_historia  = -1;
  public void init(ServletConfig config) throws ServletException
    {super.init(config);}
//*******************************************************************************************************************
  public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
                    throws ServletException, IOException
  {
    //INT20131108
    String rutaFisica = TBCL_REFERENCIAS.TBCL_CONSULTA("SMM003");
    //rutaFisica  =  "c:\\Taxbenefits\\Taxbenefits\\public_html\\WEB-INF\\";
    String POLICY_FILE_LOCATION = rutaFisica+"antisamy-tinymce-1.4.4.xml"; // Path to policy file
    AntiSamy as = new AntiSamy(); // INT20131108
  PrintWriter salida       = new PrintWriter (respuesta.getOutputStream());
    Connection v_conexion_taxb    =   null;
  try
  {
   //seguridad
    HttpSession session          = peticion.getSession(true);
    if (session == null) session = peticion.getSession(true);
    respuesta.setContentType("text/html");
    /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
    //respuesta.setHeader("Pragma", "No-Cache");
    //respuesta.setDateHeader("Expires", 0);
//    respuesta.setHeader("Cache-Control","no-Cache");
    String v_contrato           = "", v_producto = "",
    v_usuario                   = "", v_unidad   = "";
    String v_tipousu            = "", v_idworker = "";
    String parametros[]         = new String[8];
    String cadena2              = peticion.getParameter("cadena");
    String nuevaCadena          = cadena2;
    String ip_tax               = peticion.getRemoteAddr();
    TBCL_Seguridad Seguridad    = new TBCL_Seguridad();
    //valido la veracidad del producto y contrato enviados por pipeline
    parametros                  = Seguridad.TBFL_Seguridad(cadena2, salida, ip_tax);
    v_contrato                  = parametros[0];
    v_producto                  = parametros[1];
    v_usuario                   = parametros[2];
    v_unidad                    = parametros[3];
    v_tipousu                   = parametros[4];
    v_idworker                  = parametros[5];
    final int MAX = 3000;    //Cambio hecho por APC 2005-03-03 para manejar los arreglos
   //seguridad

   
   boolean hay_datos            = false;
   v_conexion_taxb =   DataSourceWrapper.getInstance().getConnection();
   

   TBCL_HTML   hoja_base                 = new TBCL_HTML();
   TBCL_HTML   hoja_error                = new TBCL_HTML();
   TBCL_HTML   parametros_requeridos     = new TBCL_HTML();
   TBCL_HTML   datos_ccontingente        = new TBCL_HTML();
   TBCL_HTML   nombres                   = new TBCL_HTML();
   TBCL_HTML   sin_datos                 = new TBCL_HTML();
   TBCL_HTML   publica_elegido           = new TBCL_HTML();
   TBCL_HTML   date                      = new TBCL_HTML();
   TBCL_HTML   valor_long                = new TBCL_HTML();
   String  nombre_producto               = peticion.getParameter("nom_producto");

   String  numero_contrato               = peticion.getParameter("num_contrato");

   String  usuario                       = peticion.getParameter("usuario");

   if(nombre_producto != null)
   {
     v_producto = "x";
     v_contrato = "x";
   }
   //------------------------------------------------------------------pagina numero 1
    //si los parametros son validos, dibujo hojabase y devuelvo parametros x y x
    if(parametros_requeridos.TBPL_Parametros_Requeridos(v_producto,v_contrato))
    {

     v_historia  = -1;
     hoja_base.TBPL_Hoja_Base("ACTUALIZADOR DE APORTES",v_producto,v_contrato,salida,3,
                            "TBS_ACTUALIZA_CCONTINGENTE",v_conexion_taxb,v_usuario,nombres.TBPL_Nombres(v_producto,v_contrato),nuevaCadena);

     return;
    }
    else if ((!parametros_requeridos.TBPL_Parametros_Requeridos(nombre_producto,numero_contrato))&&
         ( nombre_producto == null)&&
         ( numero_contrato == null))
       {
         STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
         String msj = "EL CONTRATO "+v_contrato+" NO EXISTE EN EL SISTEMA.";
         salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE APORTES",
                                       "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_CCONTINGENTE","<font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><CENTER>"+msj+"</CEBTER></font></b>",false));
         salida.println("<BR><BR>");
         salida.println("<center><input type='button' value='Aceptar' Onclick='history.go(-1);' ></center>");
         salida.println(plantilla.TBFL_PIE);
         salida.flush();
       return;
        }
//------------------------------------------------------------------pagina numero 2
       else if
      ((!parametros_requeridos.TBPL_Parametros_Requeridos(nombre_producto,numero_contrato))&&
      (!nombre_producto.equals("x"))&&
      (!numero_contrato.equals("x"))&&
      (!numero_contrato.equals("y"))&&
      (!numero_contrato.equals("y"))&&
      (!numero_contrato.equals("z"))&&
      (!numero_contrato.equals("z")))
      { v_historia  = -2;

     hoja_error.TBPL_Hoja_Error(nombre_producto,numero_contrato,salida,0,"TBS_ACTUALIZA_CCONTINGENTE",
                          "ACTUALIZADOR DE APORTES");


     return;
   }
//------------------------------------------------------------------pagina numero 3
   else if(nombre_producto.equals("x")&&numero_contrato.equals("x"))
   {
     v_historia  = -2;
     String  f_producto    = peticion.getParameter("f_producto");
     String  f_contrato    = peticion.getParameter("f_contrato");
     String  f_usuario     = peticion.getParameter("usuario");
     String fecha_1        = peticion.getParameter("fecha_1");
     String fecha_2        = peticion.getParameter("fecha_2");
     String tipo_select    = peticion.getParameter("tipo_select");
     int consecutivo       = 0;
     int consecus[]        = new int[MAX];
     //defino vables para capturar posteriormente desde el select
     String certificado[]  = new String[MAX];
     String cc[]           = new String[MAX];
     String c[]            = new String[MAX];
     String rtos[]         = new String[MAX];
     String f_a[]          = new String[MAX];
     String f_e[]          = new String[MAX];
     double saldo_nu[]        = new double[MAX];
     int consecutivos[]    = new int[MAX];
     String apo_beneficio[]= new String[MAX];
     int total_aportes     = 0;
     String select8i_11    = "SELECT "+
                          "APO_APORTE_CERTIFICADO"+
                          ",TO_CHAR(APO_CUENTA_CONTINGENTE)"+
                          ",TO_CHAR(APO_CAPITAL)"+
                          ",TO_CHAR(APO_RENDIMIENTOS)"+
                          ",APO_SALDO_NUMERO_UNIDADES"+
                          ",TO_CHAR(APO_FECHA_APORTE,'RRRR-MM-DD')"+
                          ",TO_CHAR(APO_FECHA_EFECTIVA,'RRRR-MM-DD')"+
                          ",APO_CONSECUTIVO "+
                          ",APO_APORTE_BENEFICIO "+
                          "FROM "+
                          "TBAPORTES "+
                          "WHERE "+
                          "("+
                          "APO_CON_PRO_CODIGO = ? "+
                          "AND APO_CON_NUMERO = ? "+
                          "AND APO_REF_ESTADO <> 'SEA002' "+
                          "AND APO_SALDO_NUMERO_UNIDADES <> 0 "+
                          "AND APO_FECHA_EFECTIVA BETWEEN TO_DATE(?,'RRRR-MM-DD') AND TO_DATE(?,'RRRR-MM-DD') " +
                          "AND (APO_APORTE_TRASLADO = 'N' OR (APO_APORTE_TRASLADO = 'S' AND APO_INFORMACION_TRASLADO = 'N' ))"+
                           ") ORDER BY APO_FECHA_EFECTIVA DESC";
     String select8i_12   = "SELECT "+
                          "APO_APORTE_CERTIFICADO"+
                          ",TO_CHAR(APO_CUENTA_CONTINGENTE)"+
                          ",TO_CHAR(APO_CAPITAL)"+
                          ",TO_CHAR(APO_RENDIMIENTOS)"+
                          ",APO_SALDO_NUMERO_UNIDADES"+
                          ",TO_CHAR(APO_FECHA_APORTE,'RRRR-MM-DD')"+
                          ",TO_CHAR(APO_FECHA_EFECTIVA,'RRRR-MM-DD')"+
                          ",APO_CONSECUTIVO "+
                          ",APO_APORTE_BENEFICIO "+
                          ",TO_CHAR(CON_FECHA_CANCELACION,'RRRR-MM-DD') "+
                          "FROM "+
                          "TBAPORTES "+
                          ",TBCONTRATOS "+
                          "WHERE "+
                          "("+
                          "APO_CON_PRO_CODIGO     = CON_PRO_CODIGO "+
                          "AND APO_CON_NUMERO     = CON_NUMERO "+
                          "AND APO_CON_PRO_CODIGO = ? "+
                          "AND APO_CON_NUMERO     = ? "+
                          "AND APO_REF_ESTADO <> 'SEA002' "+
                          "AND APO_FECHA_EFECTIVA BETWEEN TO_DATE(?,'RRRR-MM-DD') AND TO_DATE(?,'RRRR-MM-DD') " +
                          "AND (APO_APORTE_TRASLADO = 'N' OR (APO_APORTE_TRASLADO = 'S' AND APO_INFORMACION_TRASLADO = 'N' ))"+
                           ") ORDER BY APO_FECHA_EFECTIVA DESC";
      //salida.println("Parametros: " +f_producto+","+f_contrato+","+fecha_1+","+fecha_2);
     PreparedStatement t_st8i    = null;
     String fecha_c              = new String(" ");
     boolean seleccionado_12     = false;
     fecha_c                     = null;
     try
      {
       //salida.println("<br>" +tipo_select.equals("1"));
       if(tipo_select.equals("1"))
        {

         t_st8i = v_conexion_taxb.prepareStatement(select8i_12);
         seleccionado_12 = true;
        }
      }
     catch(Exception ex){t_st8i = v_conexion_taxb.prepareStatement(select8i_11);}
     t_st8i.setString(1,f_producto);
     t_st8i.setString(2,f_contrato);
     t_st8i.setString(3,fecha_1);
     t_st8i.setString(4,fecha_2 );
     ResultSet t_rs8i = t_st8i.executeQuery();


     while(t_rs8i.next())
     {
     try
        {
        //salida.println("Paso...:" + t_rs8i.getString(6) + ".<BR>" );
        if(!t_rs8i.getString(6).equals(null))
         {
//           salida.println("Entro...<br>");
           hay_datos                   = true;
           certificado[total_aportes]  = t_rs8i.getString(1);
//           salida.println("<br>Entro...1" + t_rs8i.getString(1));
           cc[total_aportes]           = t_rs8i.getString(2);
//           salida.println("<br>Entro...2" + t_rs8i.getString(2));
           c[total_aportes]            = t_rs8i.getString(3);
//           salida.println("<br>Entro...3" + t_rs8i.getString(3));
           rtos[total_aportes]         = t_rs8i.getString(4);
//           salida.println("<br>Entro...4" + t_rs8i.getString(4));           
           saldo_nu[total_aportes]     = t_rs8i.getDouble(5);
//           salida.println("<br>Entro...5" + t_rs8i.getString(5));           
           f_a[total_aportes]          = t_rs8i.getString(6);
//           salida.println("<br>Entro...6" + t_rs8i.getString(6));           
           f_e[total_aportes]          = t_rs8i.getString(7);
//           salida.println("<br>Entro...7" + t_rs8i.getString(7));           
           consecus[total_aportes]     = t_rs8i.getInt(8);
//           salida.println("<br>Entro...8" + t_rs8i.getString(8));           
           apo_beneficio[total_aportes]= t_rs8i.getString(9);
/*           salida.println("<br>Entro...9" + t_rs8i.getString(9));           
           salida.println("<br>Ingreso:" + 
                          certificado[total_aportes] + "<br>," +
                          cc[total_aportes] +  "<br>," +
                          c[total_aportes] +  "<br>," +
                          rtos[total_aportes]  +  "<br>," +
                          saldo_nu[total_aportes]  +  "<br>," +
                          f_a[total_aportes] +  "<br>," +
                          f_e[total_aportes] +  "<br>," +
                          consecus[total_aportes] + "<br>," +
                          apo_beneficio[total_aportes] +  "<br>");*/
                          
                        
                      
           try
           {fecha_c = t_rs8i.getString(10);}
           catch(Exception ex){fecha_c = null;}
           total_aportes++;
         }
        else{System.out.println("  ");}
      }//try
      catch(Exception ex){System.out.println("  ");}
     }//while
     t_rs8i.close();
     t_st8i.close();
  //salida.println("total aportes :"+total_aportes);
     try
     {
        if(seleccionado_12 && !fecha_c.equals(null))
         {
           STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
           String msje = "EL CONTRATO ELEGIDO SE ENCUENTRA CANCELADO.";
           salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE APORTES",
                                           "PKT_ACTUALIZA_APORTES.TBS_ACTUALIZA_CCONTINGENTE"," ",true));
           salida.println("<font face='Verdana' size='1' color='#324395'>"+msje+"</font>");
           salida.println("<center><input type='button' value='Aceptar' Onclick='history.go(-1);'></center><BR>");
           salida.println(plantilla.TBFL_PIE);
           salida.flush();
         }
     }//try
     catch(Exception ex)
     {
       System.out.println("  ");
     }//catch
     //llamada a procedimiento que tome los datos en vectores y los publique
     if(hay_datos)
        datos_ccontingente.TBPL_Datos_Ccontingente(f_producto,f_contrato,f_usuario,certificado,cc,c,rtos,f_a,
                                                   total_aportes,salida,consecus,f_e,apo_beneficio,nuevaCadena);
     else if(!hay_datos)
      {
        STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
        String msje = "NO SE ENCONTRARON APORTES PARA EL CONTRATO ELEGIDO.";
        salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE APORTES",
                                       "PKT_ACTUALIZA_APORTES.TBS_ACTUALIZA_CCONTINGENTE"," ",true));
        salida.println("<font face='Verdana' size='1' color='#324395'>"+msje+"</font>");
        salida.println("<center><input type='button' value='Aceptar' Onclick= 'history.go(-1);'></center>");
        salida.println(plantilla.TBFL_PIE);
        salida.flush();
      }
     return;
   }
//------------------------------------------------------------------pagina numero 4
  else if(nombre_producto.equals("y")&&numero_contrato.equals("y"))
   {
    v_historia  = -3;
    int indice              = 0;
    String str              = " ";
    String  f_producto    = peticion.getParameter("f_producto");
    String  f_contrato    = peticion.getParameter("f_contrato");
    String  f_usuario     = peticion.getParameter("usuario");
    String cadena         = peticion.getParameter("fila");
    indice                = cadena.indexOf("$");
    String f_a            = cadena.substring(0,indice);
    str                   = cadena.substring(indice+1,cadena.length());
    indice                = str.indexOf("$");
    String certificado    = str.substring(0,indice);
    str                   = str.substring(indice+1,str.length());
    indice                = str.indexOf("$");
    String k              = str.substring(0,indice);
    str                   = str.substring(indice+1,str.length());
    indice                = str.indexOf("$");
    String rtos           = str.substring(0,indice);
    str                   = str.substring(indice+1,str.length());
    indice                = str.indexOf("*");
    String cc             = str.substring(0,indice);
    str                   = str.substring(indice+1,str.length());
    indice                = str.indexOf("@");
    String apo_beneficio  = str.substring(0,indice);
    String  consecutiv    = str.substring(indice+1,str.length());
    Integer i             =  new Integer(consecutiv);
    int consecutivo       = i.intValue();
    CallableStatement t_cst8i_13 = v_conexion_taxb.prepareCall("{ call TBPBD_Valida_Retiros_por_Apt(?,?,?,?) }");
    t_cst8i_13.registerOutParameter(4,Types.VARCHAR);
    t_cst8i_13.setString(1,f_producto);
    t_cst8i_13.setString(2,f_contrato);
    t_cst8i_13.setInt(3,consecutivo);
    t_cst8i_13.execute();
    String tiene_retiros = t_cst8i_13.getString(4);
    t_cst8i_13.close();
    publica_elegido.TBPL_Aporte_Elegido(f_producto,f_contrato,f_usuario,salida,f_a,certificado,k,cc,rtos,consecutiv,
                                   apo_beneficio,tiene_retiros,nuevaCadena);
   return;
   }//Y y Y
//------------------------------------------------------------------pagina numero 5
  else if(nombre_producto.equals("z")&&numero_contrato.equals("z"))
   {
    v_historia  = -4;
    String boton_salida          = new String("");
    String pintalo               = new String("0");
    String estado_cargos_ajustes = new String("");
    String v_msj_error           = new String("");
    java.sql.Date fecha_ret_p[]  = new java.sql.Date[150];
    java.sql.Date fecha_ret_e[]  = new java.sql.Date[150];
    String tipo_t[]          = new String[150];
    String clase_t[]         = new String[150];
    double valor[]           = new double[150];
    String tipo_v            = new String("STV001");
    String penalizado[]      = new String[150];
    String prorata[]         = new String[150];
    String banco[]           = new String[150];
    String cuenta[]          = new String[150];
    String transaccion[]     = new String[150];
    String ret_usuario[]     = new String[150];
    String udad_proceso[]    = new String[150];
    String afp[]             = new String[150];
    String tipo_i[]          = new String[150];
    String numero_i[]        = new String[150];
    String m_o[]             = new String[150];
    String m_b[]             = new String[150];
    String m_p[]             = new String[150];
    String m_c[]             = new String[150];
    String m_n[]             = new String[150];
    String r_n[]             = new String[150];
    String ret_con[]         = new String[150];
    double ret_vu[]          = new double[150];
    double ret_vneto[]       = new double[150];
    java.sql.Date ret_feDate = new java.sql.Date(1);
    java.sql.Date frb = new java.sql.Date(1);
    String estado_reversar = "NO";
    String estado_aplicar  = "NO";
    STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
    String msj ="<p><font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><font size='1'></font></b><font size='1' color='#000000'>Actualización de Aportes Realizada</font></font></p>";
    String  f_producto    = peticion.getParameter("f_producto");
    String  f_contrato    = peticion.getParameter("f_contrato");
    String  f_usuario     = peticion.getParameter("usuario");
    String  consecu       = peticion.getParameter("consecutivo");
    String  apo_beneficio = peticion.getParameter("apo_beneficio");
    //valores de cambio
    String  f_a           = peticion.getParameter("obligatoriofecha");
    String  certificado   = peticion.getParameter("certificado");
    String  k             = new String("0");
    String  cc            = new String("0");
    String  rtos          = new String("0");
    try
    {
      if(k!=null)
        k    =peticion.getParameter("capital");
    } catch(Exception ex){k="0";}
    try
    {
      if(cc!=null)
        cc   = peticion.getParameter("cc");
    } catch(Exception ex){cc="0";}
    try
    {
      if(rtos!=null)
        rtos = peticion.getParameter("rto");
    }catch(Exception ex){rtos="0";}

    //valores originales
    String  f_ao          = peticion.getParameter("f_ao");
    String  certificado_o = peticion.getParameter("certificado_o");
    String  k_o           = peticion.getParameter("capital_o");
    String  cc_o          = peticion.getParameter("cc_o");
    String  rtos_o        = peticion.getParameter("rto_o");
    //razon de cambio
    String  razon         = peticion.getParameter("obligatorio_razon");
       try
       {
           CleanResults cr = as.scan(peticion.getParameter("obligatorio_razon"), new File(POLICY_FILE_LOCATION));
           razon = cr.getCleanHTML();
       }
       catch (Exception e)
       {
         e.printStackTrace();
       } //INT20131108
    razon                += " USUARIO : ";
    razon                += f_usuario;
    Integer i             =  new Integer(consecu);
    int consecutivo       = i.intValue();
    String indicador_beneficio_actual   = "";
    //convierto Valores de Cambio a long
    double capital        = 0;
    double  cuenta_c       = 0;
    double  rto            = 0;
    boolean pintar_decision = true;
    int cutivo_ajuste   = 1;
    try
    {
     if(k.trim().equals(""))
     {
      capital            = 0;
      k = "0";
     }
     else
     {
      Double i_1         = new Double(k);
      capital            = i_1.doubleValue();
     }
    }
    catch(Exception ex){capital=0;}
    try
    {
     if(cc.trim().equals(""))
     {
      cuenta_c           = 0;
       cc =  "0";
     }
     else
     {
      Double i_2         = new Double(cc);
      cuenta_c           = i_2.doubleValue();

     }
    }
    catch(Exception ex){cuenta_c=0;}
    try
    {
      if(rtos.trim().equals(""))
      {
            rto                = 0;
            rtos = "0";
      }
      else
      {
        Double i_3         = new Double(rtos);
        rto                = i_3.doubleValue();

      }
    }
     catch(Exception ex){rto = 0;}
    //convierto Valores Originales a long
    double capital_o   = 0;
    double cuenta_co   = 0;
    double rto_o       = 0;
    if(k_o.trim().equals(""))
    {
     capital_o          = 0;
    }
    else
    {
     Double i_4         = new Double(k_o);
     capital_o          = i_4.doubleValue();
    }

    if(cc_o.trim().equals(""))
    {
      cuenta_co          = 0;
    }
    else
    {
      Double i_5         = new Double(cc_o);
      cuenta_co          = i_5.doubleValue();
    }


    if(rtos_o.trim().equals(""))
    {
      rto_o              = 0;
    }
    else
    {
      Double i_6         = new Double(rtos_o);
      rto_o              = i_6.doubleValue();
    }

    String tiene_beneficio = " ";
    boolean valores_krtos  = true;


    boolean valores_cc     = true;
    boolean  certificacion_correcta = false;
    double max_cc             = 0;
    boolean hoy_estabien      = false;
    boolean fechas_diferentes = false;
    String select8i_3           = "SELECT '*' FROM DUAL WHERE TO_DATE(?,'RRRR-MM-DD')<=SYSDATE ";
    PreparedStatement t_st8i_1  = v_conexion_taxb.prepareStatement(select8i_3);
    t_st8i_1.setString(1,f_a);
    ResultSet t_rs8i_1          = t_st8i_1.executeQuery();
    while(t_rs8i_1.next()){hoy_estabien = true;}
    t_rs8i_1.close();
    t_st8i_1.close();
    if(hoy_estabien)
    {
        //realizo validaciones antes de actualizar
       //Valido Si fecha tiene beneficio-->
      //Convierto f_a String a f_a Date
      java.sql.Date f_aDate = new java.sql.Date(1);
      f_aDate               = date.TBPL_date(f_a);
      //Convierto f_e String a f_AORIGINAL Date
      java.sql.Date f_aODate = new java.sql.Date(1);
      f_aODate               = date.TBPL_date(f_ao);
       //valido beneficio
try
{
      CallableStatement t_cst8i_3 = v_conexion_taxb.prepareCall("{ call TBPBD_Con_Beneficio(?,?,?,?,?) }");
      t_cst8i_3.registerOutParameter(4,Types.VARCHAR);
      t_cst8i_3.registerOutParameter(5,Types.DATE);
      t_cst8i_3.setString(1,f_producto);
      t_cst8i_3.setString(2,f_contrato);
      t_cst8i_3.setDate(3,f_aDate);
      t_cst8i_3.execute();
      tiene_beneficio = t_cst8i_3.getString(4);
      frb             = t_cst8i_3.getDate(5);
      t_cst8i_3.close();
}
catch(Exception ex){

                   }
      DecimalFormat dec  = new DecimalFormat("###,###,###,###,###,###.##");
      if(tiene_beneficio.equals("v"))indicador_beneficio_actual = "S";
      else indicador_beneficio_actual = "N";
      double vsuma_nueva = capital+rto;
      double vsuma_vieja = capital_o+rto_o;
      double vdiferencia = vsuma_vieja - vsuma_nueva;
      if( vdiferencia < 0 || vdiferencia >= 0.01)
      {
         valores_krtos = false;
      }
      //Valido igualdad en valores originales y nuevos en las variables capital y rendimientos
      //if(vsuma_nueva == vsuma_vieja)
      //{
        //valores_krtos = true;
      //}

      //Valido Cuenta Contingente Nueva con el Valor MAX
      CallableStatement t_cst8i_6 = v_conexion_taxb.prepareCall("{ call TBPBD_Cuenta_Contingente_Fecha(?,?,?,?) }");
      //CallableStatement t_cst8i_6 = v_conexion_taxb.prepareCall("{ call TBPBD_Cuenta_Contingente(?,?,?) }");
      t_cst8i_6.registerOutParameter(4,Types.NUMERIC);
      //t_cst8i_6.registerOutParameter(3,Types.NUMERIC);
      t_cst8i_6.setString(1,f_producto);
      t_cst8i_6.setDouble(2,capital);
      t_cst8i_6.setString(3,f_a);
      t_cst8i_6.execute();
      //max_cc = (t_cst8i_6.getDouble(3)/100);
      max_cc = (t_cst8i_6.getDouble(4)/100);
      t_cst8i_6.close();
      if(cuenta_c>(max_cc)) valores_cc = false;
      /*if(certificado_o.equals("N")&&certificado.equals("S")&&valores_cc)
        certificacion_correcta = true;
      if(certificado_o.equals("S")&&certificado.equals("S")&&valores_cc)
        certificacion_correcta = true;*/
      //Valido Retiros
     //verifico si el aporte tiene retiros asociados
      CallableStatement t_cst8i_13 = v_conexion_taxb.prepareCall("{ call TBPBD_Valida_Retiros_por_Apt(?,?,?,?) }");
      t_cst8i_13.registerOutParameter(4,Types.VARCHAR);
      t_cst8i_13.setString(1,f_producto);
      t_cst8i_13.setString(2,f_contrato);
      t_cst8i_13.setInt(3,consecutivo);
      t_cst8i_13.execute();
      String tiene_retiros = t_cst8i_13.getString(4);
      t_cst8i_13.close();
     //segun validaciones procedo a:
     if(valores_krtos&&valores_cc)
     {   //reviso el beneficio
         if(apo_beneficio.equals("S")&&tiene_beneficio.equals("f"))
         //debo anular datos de debenficio
          {
            CallableStatement t_cst8i_4 = v_conexion_taxb.prepareCall("{ call TBPBD_DesActualiza_B(?,?,?) }");
            t_cst8i_4.setString(1,f_producto);
            t_cst8i_4.setString(2,f_contrato);
            t_cst8i_4.setInt(3,consecutivo);
            t_cst8i_4.execute();
            t_cst8i_4.close();
          }//si anular
         else if(apo_beneficio.equals("N")&&tiene_beneficio.equals("v"))
         //debo update datos de beneficio
          {
            CallableStatement t_cst8i_4 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_B(?,?,?,?,?) }");
            t_cst8i_4.setString(1,f_producto);
            t_cst8i_4.setString(2,f_contrato);
            t_cst8i_4.setInt(3,consecutivo);
            t_cst8i_4.setString(4,razon);
            t_cst8i_4.setDate(5,frb);
            t_cst8i_4.execute();
            t_cst8i_4.close();
          }//sinupdate
          //debo ir a insertar alogs antes de realizar modificacion
         //para esto necesito calcular el valor actual del campo TRT_LINEA e increementarlo si es necesario
         CallableStatement t_cst8i_1 = v_conexion_taxb.prepareCall("{ call TBPBD_TRL_Linea(?,?,?) }");
         t_cst8i_1.registerOutParameter(3,Types.NUMERIC);
         t_cst8i_1.setInt(1,consecutivo);
         t_cst8i_1.setString(2,"A");
         t_cst8i_1.execute();
         int linea = t_cst8i_1.getInt(3);
         t_cst8i_1.close();
         //ahora realizo insert sobre la tabla de logs
         CallableStatement t_cst8i_2 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_Logs(?,?,?,?,?,?,?,?,?,?,?) }");
         t_cst8i_2.setString(1,"A");
         t_cst8i_2.setInt(2,consecutivo);
         t_cst8i_2.setInt(3,linea);
         t_cst8i_2.setString(4,"M");
         t_cst8i_2.setString(5,razon);
         t_cst8i_2.setString(6,f_usuario);
         t_cst8i_2.setDouble(7,cuenta_co);
         t_cst8i_2.setString(8,certificado_o);
         t_cst8i_2.setDouble(9,capital_o);
         t_cst8i_2.setDouble(10,rto_o);
         t_cst8i_2.setString(11,f_ao);
         t_cst8i_2.execute();
         t_cst8i_2.close();
         //PROCESO DE ACTUALIZACION DE SALDOS
         CallableStatement t_cst8i_98 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_Saldos(?,?,?,?,?,?) }");
         t_cst8i_98.setString(1,f_producto);
         t_cst8i_98.setString(2,f_contrato);
         t_cst8i_98.setInt(3,consecutivo);
         t_cst8i_98.setDouble(4,Double.parseDouble(k));
         t_cst8i_98.setDouble(5,Double.parseDouble(cc));
         t_cst8i_98.setDouble(6,Double.parseDouble(rtos));
         t_cst8i_98.executeUpdate();
         t_cst8i_98.close();
         //update datos
         CallableStatement t_cst8i_8 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_C(?,?,?,?,?,?,?,?) }");
         t_cst8i_8.setString(1,f_producto);
         t_cst8i_8.setString(2,f_contrato);
         t_cst8i_8.setInt(3,consecutivo);
         t_cst8i_8.setDouble(4,capital);
         t_cst8i_8.setDouble(5,cuenta_c);
         t_cst8i_8.setDouble(6,rto);
         t_cst8i_8.setString(7,certificado);
         t_cst8i_8.setString(8,f_a);
         t_cst8i_8.executeUpdate();
         t_cst8i_8.close();
         //mando a ajustes
         if(tiene_retiros.equals("f"))
          {
            pintalo               = "1";
            estado_reversar       = "YES";
            estado_cargos_ajustes = "BIEN";
            boton_salida          = "<center><input type='button' value='Aceptar' Onclick='history.go(-3);'></center>";
                      }
         else if(tiene_retiros.equals("v"))
         {
             boton_salida        = "<center><input type='button' value='Regresar' Onclick='history.go(-1);'></center>";
             String ret_fp       = "";
             String ret_fe       = "";
             String fa_tieneB    = "";
             String fao_tieneB   = "";
             int linea_ajuste    = 0;
             //calculo el consecutivo del ajuste a generar
             CallableStatement t_cst8i_12 = v_conexion_taxb.prepareCall("{ ? = call  TBFBD_nextConsecAjus() }");
             t_cst8i_12.registerOutParameter(1,Types.VARCHAR);
             t_cst8i_12.executeUpdate();
             String ctivo = t_cst8i_12.getString(1);
             t_cst8i_12.close();
             //convierto a Int
             Integer ct    = new Integer(ctivo);
             cutivo_ajuste = ct.intValue();
             msj = "<p><font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><font size='1'></font></b><font size='1' color='#000000'>SE REALIZO EL PROCESO DE AJUSTES(CONSECUTIVO #"+
                  cutivo_ajuste+") DE LOS RETIROS SATISFACTORIAMENTE</font></font></p>";
               //de acuerdo a los datos que se modificaron realizare validaciones y/o llamado a AJUSTES, asi
              //si la fecha del aporte es modificada se debe de realizar validaciones de beneficios y reversar y aplicar
             //si la fecha del aporte no es modificada se debe reversar y aplicar
             if(!f_a.equals(f_ao)&&!apo_beneficio.equals(indicador_beneficio_actual))
              {fechas_diferentes = true;}
                  //se eupone que por aquí ANULO RETIROS y AJUSTO RETIROS
                 //simulo pg de respuesta que diga lo anterior
                //Proceo 1-Reversar=> producto,contrato,estado_retrio,ret_consecutivo,valor_unidad,estado_actualizacion,estado_reversion)
               //Reversar(producto,contrato,SER008,ret_cutivo,valor_unidad,1,1)
              //Proceo 2-Aplicar=> cutivo_ajuste,linea,f_p,f_e,razon,user,$neto,valor_ud,cutivo_ret,estado
             String select8i_4 =  "SELECT "+
                                    //PRODUCTO
                                   //CONTRATO
                                  //NULL
                                  "TO_DATE(RET_FECHA_EFECTIVA,'DD-MON-RR') "+
                              	  ",TO_DATE(RET_FECHA_PROCESO,'DD-MON-RR') "+
                              	  ",RET_COT_REF_TIPO_TRANSACCION "+
                              	  ",RET_COT_REF_CLASE_TRANSACCION "+
                                  ",RET_VALOR_BRUTO "+
                              	  ",RET_REF_TIPO_VALOR "+
                                  //VALOR DE LA UNIDAD--CALCULADO
                            	    ",DECODE(RET_RETIRO_CARGUE,'N','S','N') "+
                              	  ",RET_RETIRO_PRORRATA "+
                                  ",RET_BANCO "+
                                  ",RET_CUENTA "+
                                  ",RET_TRANSACCION "+
                                  ",RET_USUARIO "+
                                  ",RET_REF_UNIDAD_PROCESO "+
                                  //NUEVO ESTADO --SER006
                                  ",RET_AFP_CODIGO "+
                                  ",RET_CON_PRO_CODIGO_TI "+
                                  ",RET_CON_NUMERO_TI "+
                                  ",RET_REF_METODO_ORDEN "+
                                  ",RET_REF_METODO_BENEFICIO "+
                                  ",RET_REF_METODO_PENALIZACION "+
                                  ",RET_REF_METODO_CUENTA "+
                                  ",RET_REF_NATURALEZA "+
                                  ",RET_RESPETAR_NATURALEZA "+
                                  ",RET_CONSECUTIVO "+
                                  ",RET_VALOR_UNIDAD "+
                                  ",RET_VALOR_NETO "+
                                   //INDICADOR --'S'
                                  //INDICADOR DE GIRO --'N'
                                  "FROM "+
                                  "TBAPORTES_RETIROS "+
                                  ",TBRETIROS "+
                                  "WHERE "+
                                  "APR_RET_CON_PRO_CODIGO      = ? "+
                                  "AND APR_RET_CON_NUMERO      = ? "+
                                  "AND APR_APO_CONSECUTIVO     = ? "+
                                  "AND APR_RET_CON_PRO_CODIGO  = RET_CON_PRO_CODIGO "+
                                  "AND APR_RET_CON_NUMERO      = RET_CON_NUMERO "+
                                  "AND APR_RET_CONSECUTIVO     = RET_CONSECUTIVO "+
                                  "AND RET_REF_ESTADO          = 'SER006' ";
             PreparedStatement t_st8i_2  = v_conexion_taxb.prepareStatement(select8i_4);
             t_st8i_2.setString(1,f_producto);
             t_st8i_2.setString(2,f_contrato);
             t_st8i_2.setInt(3,consecutivo);
             double v_valorUnidad  = 0;
             ResultSet t_rs8i_2    = t_st8i_2.executeQuery();
             int total_retiros     = 0;
             while(t_rs8i_2.next())
              {
                //capturo Ix de cada Retiro
                fecha_ret_p[total_retiros]  = t_rs8i_2.getDate(2);
                fecha_ret_e[total_retiros]  = t_rs8i_2.getDate(1);
                tipo_t[total_retiros]       = t_rs8i_2.getString(3);
                clase_t[total_retiros]      = t_rs8i_2.getString(4);
                valor[total_retiros]        = t_rs8i_2.getDouble(5);
                penalizado[total_retiros]   = t_rs8i_2.getString(7);
                prorata[total_retiros]      = t_rs8i_2.getString(8);
                banco[total_retiros]        = t_rs8i_2.getString(9);
                cuenta[total_retiros]       = t_rs8i_2.getString(10);
                transaccion[total_retiros]  = t_rs8i_2.getString(11);
                ret_usuario[total_retiros]  = t_rs8i_2.getString(12);
                udad_proceso[total_retiros] = t_rs8i_2.getString(13);
                afp[total_retiros]          = t_rs8i_2.getString(14);
                tipo_i[total_retiros]       = t_rs8i_2.getString(15);
                numero_i[total_retiros]     = t_rs8i_2.getString(16);
                m_o[total_retiros]          = t_rs8i_2.getString(17);
                m_b[total_retiros]          = t_rs8i_2.getString(18);
                m_p[total_retiros]          = t_rs8i_2.getString(19);
                m_c[total_retiros]          = t_rs8i_2.getString(20);
                m_n[total_retiros]          = t_rs8i_2.getString(21);
                r_n[total_retiros]          = t_rs8i_2.getString(22);
                ret_con[total_retiros]      = t_rs8i_2.getString(23);
                ret_vu[total_retiros]       = t_rs8i_2.getDouble(24);
                ret_vneto[total_retiros]    = t_rs8i_2.getDouble(25);
                //valido esuqema
                try
                {
                 if(m_o[total_retiros].equals(null)||m_b[total_retiros].equals(null)||
                    m_p[total_retiros].equals(null)||m_c[total_retiros].equals(null)||
                    m_n[total_retiros].equals(null)||r_n[total_retiros].equals(null))
                  {
                    m_o[total_retiros] = null;             m_b[total_retiros] = null;                 m_p[total_retiros] = null;
                    m_c[total_retiros] = null;             m_n[total_retiros] = null;                 r_n[total_retiros] = null;
                  }
                }//try
               catch(Exception ex)
                {
                  m_o[total_retiros] = null;               m_b[total_retiros] = null;                 m_p[total_retiros] = null;
                  m_c[total_retiros] = null;               m_n[total_retiros] = null;                 r_n[total_retiros] = null;
                }//catch
               total_retiros++;
              }//while de seleccion de retiros
             t_rs8i_2.close();
             t_st8i_2.close();
             //debo entonces validar beneficio de fechas
             if(fechas_diferentes)
             {
              //REVISO BENEFICIO DE FECHAS
              if(TBPL_Beneficio(f_producto,f_contrato,f_aDate,fecha_ret_e,f_aODate,total_retiros,v_conexion_taxb))
               {
                 //DEBO REVERSAR,ACTUALIZAR SALDOS,GENERAR RETIROS PRIMA
                //PROCESO DE REVERSIONES
                if(TBPL_Reversiones(f_producto,f_contrato,ret_con,ret_vu,total_retiros,v_conexion_taxb))
                 {
                  //PROCESO DE ACTUALIZACION DE SALDOS
                  CallableStatement t_cst8i_198 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_Saldos(?,?,?,?,?,?) }");
                  t_cst8i_198.setString(1,f_producto);
                  t_cst8i_198.setString(2,f_contrato);
                  t_cst8i_198.setInt(3,consecutivo);
                  t_cst8i_198.setDouble(4,Double.parseDouble(k));
                  t_cst8i_198.setDouble(5,Double.parseDouble(cc));
                  t_cst8i_198.setDouble(6,Double.parseDouble(rtos));
                  t_cst8i_198.executeUpdate();
                  t_cst8i_198.close();
                  //PROCESO DE GENERACION DE RETIROS PRIMA y AJUSTE
                  if(TBPL_Primas(fecha_ret_p                               ,fecha_ret_e
                               ,tipo_t                                   ,clase_t
                               ,valor                                    ,penalizado
                               ,prorata                                  ,banco
                               ,cuenta                                   ,transaccion
                               ,ret_usuario                              ,udad_proceso
                               ,afp                                      ,tipo_i
                               ,numero_i                                 ,m_o
                               ,m_b                                      ,m_p
                               ,m_c                                      ,m_n
                               ,r_n                                      ,ret_con
                               ,ret_vu                                   ,ret_vneto
                               ,total_retiros                            ,v_conexion_taxb
                               ,cutivo_ajuste                            ,razon
                               ,f_usuario                                ,f_producto
                               ,f_contrato
                                ))
                   {
                    estado_cargos_ajustes = "BIEN";
                    estado_reversar        = "YES";
                   }//si generacion de primas Ok
                  else
                   {
                    msj                   = "EN EL PROCESO DEL RETIRO PRIMA SE PRODUJO UN ERROR: NO SE PUDO ANULAR NI APLICAR EL RETIRO. NO SE PUDO GENERAR EL RETIRO AJUSTADO, NO EXISTE DISPONIBILIDAD EN APORTES PARA LAS OPCIONES ELEGIDAS.";
                    estado_cargos_ajustes = "MAL";
                    estado_reversar       = "NO";
                    pintar_decision       = false;
                   }//
                 }//si reversiones OK
                else
                 {
                  estado_cargos_ajustes = "MAL";
                  estado_reversar       = "NO";
                  pintar_decision       = false;
                  msj                   = "EN EL PROCESO DE REVERSAR RETIROS NO SE ENCONTRARON APORTES RETIROS ASOCIADOS ";
                 }//si reversiones con error
              }//if(PL_Beneficio())
              else
              {
               msj                   = "LA MODIFICACION EN LA FECHA DEL APORTE NO SOPORTA EL PROCESO DE AJUSTES.";
               estado_cargos_ajustes = "MAL";
               estado_reversar       = "NO";
               pintar_decision       = false;
              }//si fa_tieneB.equals(fao_tieneB)
             }//if(fechas_diferentes)
            else if(!fechas_diferentes)
             {
               //DEBO REVERSAR,ACTUALIZAR SALDOS,GENERAR RETIROS PRIMA
              //PROCESO DE REVERSIONES
              if(TBPL_Reversiones(f_producto,f_contrato,ret_con,ret_vu,total_retiros,v_conexion_taxb))
                {
                  //PROCESO DE ACTUALIZACION DE SALDOS
                  CallableStatement t_cst8i_298 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_Saldos(?,?,?,?,?,?) }");
                  t_cst8i_298.setString(1,f_producto);
                  t_cst8i_298.setString(2,f_contrato);
                  t_cst8i_298.setInt(3,consecutivo);
                  t_cst8i_298.setDouble(4,Double.parseDouble(k));
                  t_cst8i_298.setDouble(5,Double.parseDouble(cc));
                  t_cst8i_298.setDouble(6,Double.parseDouble(rtos));
                  t_cst8i_298.executeUpdate();
                  t_cst8i_298.close();
                  //PROCESO DE GENERACION DE RETIROS PRIMA y AJUSTE
                   if(TBPL_Primas(fecha_ret_p                               ,fecha_ret_e
                               ,tipo_t                                   ,clase_t
                               ,valor                                    ,penalizado
                               ,prorata                                  ,banco
                               ,cuenta                                   ,transaccion
                               ,ret_usuario                              ,udad_proceso
                               ,afp                                      ,tipo_i
                               ,numero_i                                 ,m_o
                               ,m_b                                      ,m_p
                               ,m_c                                      ,m_n
                               ,r_n                                      ,ret_con
                               ,ret_vu                                   ,ret_vneto
                               ,total_retiros                            ,v_conexion_taxb
                               ,cutivo_ajuste                            ,razon
                               ,f_usuario                                ,f_producto
                               ,f_contrato))
                   {
                    estado_cargos_ajustes = "BIEN";
                    estado_reversar       = "YES";
                   }//si generacion de primas Ok
                  else
                   {
                    msj                   = "EN EL PROCESO DEL RETIRO PRIMA SE PRODUJO UN ERROR: NO SE PUDO ANULAR NI APLICAR EL RETIRO. NO SE PUDO GENERAR EL RETIRO AJUSTADO, NO EXISTE DISPONIBILIDAD EN APORTES PARA LAS OPCIONES ELEGIDAS.";
                    estado_cargos_ajustes = "MAL";
                    estado_reversar       = "NO";
                    pintar_decision       = false;
                   }
                 }//si reversiones OK
                else
                 {
                  estado_cargos_ajustes = "MAL";
                  estado_reversar       = "NO";
                  pintar_decision       = false;
                  msj                   = "EN EL PROCESO DE REVERSAR RETIROS NO SE ENCONTRARON APORTES RETIROS ASOCIADOS ";
                 }//si reversiones con error
             }//else if(!fechas_diferentes)
         }//si hay retiros asociados al aporte
        //reviso estado procesos para realizar commit o rollback
        if(estado_reversar.equals("YES")&&estado_cargos_ajustes.equals("BIEN"))
         {
           if(pintalo.equalsIgnoreCase("0"))
              pintar_decision = true;
           else
              pintar_decision = false;
           v_conexion_taxb.commit();
         }
       else
        {
          pintar_decision = false;
          v_conexion_taxb.rollback();
        }
       salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE APORTES",
                                       "TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.TBS_DecisionRetiro"," ",true));
       salida.println("<font face='Verdana' size='1' color='#324395'><b>"+msj+"</font></b>");
       if(pintar_decision)
        {
          salida.println("<input type='hidden' name='cadena' value='"+nuevaCadena+"'>");
          salida.println("<center><input type='button' value='Aceptar' Onclick='history.go(-3);'>"+
                        "&nbsp;<input type=SUBMIT value='Decision' ></center>");
       }
       else
          salida.println("<BR><BR>"+boton_salida);
       salida.println("<input type='hidden' name='nom_producto' value='ABC'>");
       salida.println("<input type='hidden' name='num_contrato' value='ABC'>");
       salida.println(plantilla.TBFL_PIE);
       salida.flush();
       return;
       }//si valores_krtos&&valores_cc
     if(!valores_krtos||!valores_cc)
     {

      String ms = "";
      if(!valores_krtos)ms = "LA SUMA DEL CAPITAL Y DE LOS RENDIMIENTOS ORIGINALES NO CONCUERDAN CON LA SUMA DE LOS INGRESADOS.";
      if(!valores_cc)   ms = "EL VALOR DE LA CUENTA CONTINGENTE INGRESADO SOBREPASA AL MAXIMO.";
      salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE APORTES",
                                                "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_CCONTINGENTE"," ",true));
      salida.println("<p><font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><font size='1'>"+ms+"</font></b></p>");
      salida.println("<CENTER><input type='button' value='Aceptar' Onclick='history.go(-3);'>"+
      "<input type='button' value='Regresar' Onclick='history.go(-1);'></center>");
      salida.println(plantilla.TBFL_PIE);
      salida.flush();
     }
  }//SI F_A MENOR A SYSDATE
 else
  {
   String ms1 = "ERROR EN LA ACTUALIZACION,LA FECHA INGRESADA ES MAYOR A LA FECHA DEL DIA DE HOY";
   salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE APORTES",
                                                "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_CCONTINGENTE"," ",true));
   salida.println("<p><font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><font size='1'>"+ms1+"</font></b></p>");
   salida.println("<CENTER><input type='button' value='Aceptar' Onclick='history.go(-3);'>"+
   "<input type='button' value='Regresar' Onclick='history.go(-1);'></center>");
   salida.println(plantilla.TBFL_PIE);
   salida.flush();
  }//SI LA FECHA ES MAYOR A SYSDATE
     return;
   }//Z y Z
//------------------------------------------------------------------pagina numero X
 }//fin try
catch(Exception ex)
 {
  STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
  String msj = "SE PRODUJO UN ERROR INESPERADO, "+ex.toString() +" INTENTE DE NUEVO.";
  salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE APORTES",
                                       "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_CCONTINGENTE","<font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><CENTER>"+msj+"</CEBTER></font></b>",false));
  salida.println("<BR><BR>");
  salida.println("<center><input type='button' value='Aceptar' Onclick='history.go("+v_historia+");' ></center>");
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
//--------------------------------------------------------------------------------
public boolean TBPL_Primas(java.sql.Date[] fecha_ret_p
                         ,java.sql.Date[] fecha_ret_e
                         ,String[] tipo_t
                         ,String[] clase_t
                         ,double[] valor
                         ,String[] penalizado
                         ,String[] prorata
                         ,String[] banco
                         ,String[] cuenta
                         ,String[] transaccion
                         ,String[] ret_usuario
                         ,String[] udad_proceso
                         ,String[] afp
                         ,String[] tipo_i
                         ,String[] numero_i
                         ,String[] m_o
                         ,String[] m_b
                         ,String[] m_p
                         ,String[] m_c
                         ,String[] m_n
                         ,String[] r_n
                         ,String[] ret_con
                         ,double[] ret_vu
                         ,double[] ret_vneto
                         ,int total_retiros
                         ,Connection v_conexion_taxb
                         ,int cutivo_ajuste
                         ,String razon
                         ,String f_usuario
                         ,String f_producto
                         ,String f_contrato)
{

try
{
String indicador01       = new String("S");
String indicador02       = new String("N");
String v_msj_error1      = new String("");
String v_msj_error2      = new String("");
String v_msj_error3      = new String("");
int linea                = 0;
for(int i=0;i<total_retiros;i++)
 {
    //GENERO POR CADA RETIRO ORIGINAL UN RETIRO PRIMA Y A SU VEZ UN AJUSTE Y SUS CORRESPONDIENTES
   //CARGOS
   int v_conret = 0;
   boolean v_nuevoretiro = false;
   DateFormat currentDateFormat;
   java.util.Date currentDate = new java.util.Date();
   currentDateFormat          = DateFormat.getDateInstance(DateFormat.DEFAULT, java.util.Locale.PRC);
   String d                   = currentDateFormat.format(currentDate);
   int ano                    = Integer.parseInt(d.substring(0,4))-1900;
   int mes                    = Integer.parseInt(d.substring(5,d.lastIndexOf("-")))-1;
   int dia                    = Integer.parseInt(d.substring(d.lastIndexOf("-")+1,d.length()));
   java.sql.Date hoy          = new java.sql.Date(ano,mes,dia);
   linea++;
   //SI EL METODO DEL RETIRO ES SELECCIONADO SE CONSULTAN LOS APORTES PARA INSERTAR CON EL NUEVO RETIRO
   try
   {
    if(m_o[i].equalsIgnoreCase("SMO003"))
    {
    //insertar nuevo retiro

      CallableStatement l_stmt0 = v_conexion_taxb.prepareCall("{? = call TB_FINSERTAR_RETPARCIAL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
      l_stmt0.registerOutParameter(1,Types.INTEGER);
      l_stmt0.registerOutParameter(4,Types.INTEGER);
      l_stmt0.registerOutParameter(27,Types.VARCHAR);
      l_stmt0.setString(2,f_producto);
      l_stmt0.setString(3,f_contrato);
      l_stmt0.setInt(4,v_conret);
      l_stmt0.setString(5,hoy.toString());
      l_stmt0.setString(6,fecha_ret_e[i].toString());
      l_stmt0.setString(7,tipo_t[i]);
      l_stmt0.setString(8,clase_t[i]);
      l_stmt0.setDouble(9,valor[i]);
      l_stmt0.setString(10,"STV001");
      l_stmt0.setDouble(11,ret_vu[i]);
      l_stmt0.setString(12,penalizado[i]);
      l_stmt0.setString(13,prorata[i]);
      l_stmt0.setString(14,ret_usuario[i]);
      l_stmt0.setString(15,udad_proceso[i]);
      l_stmt0.setString(16,banco[i]);
      l_stmt0.setString(17,cuenta[i]);
      l_stmt0.setString(18,afp[i]);
      l_stmt0.setString(19,tipo_i[i]);
      l_stmt0.setString(20,numero_i[i]);
      l_stmt0.setString(21,m_o[i]);
      l_stmt0.setString(22,m_b[i]);
      l_stmt0.setString(23,m_p[i]);
      l_stmt0.setString(24,m_c[i]);
      l_stmt0.setString(25,m_n[i]);
      l_stmt0.setString(26,r_n[i]);
      l_stmt0.execute();
      int v_indicador  = l_stmt0.getInt(1);
      v_conret         = l_stmt0.getInt(4);
      String v_mensaje = l_stmt0.getString(27);
      l_stmt0.close();

      if( v_indicador == 0 )
      {
        String  v_aporteseleccion = " SELECT APR_APO_CONSECUTIVO  "+
                                    "         ,APR_ORDEN           "+
                                    "   FROM TBAPORTES_RETIROS  "+
                                    "  WHERE  APR_RET_CON_PRO_CODIGO  = ?  "+
                                    "    AND  APR_RET_CON_NUMERO      = ?  "+
                                    "    AND  APR_RET_CONSECUTIVO     = ?  ";

       PreparedStatement t_st8i_0    = v_conexion_taxb.prepareStatement(v_aporteseleccion);
       t_st8i_0.setString(1,f_producto);
       t_st8i_0.setString(2,f_contrato);
       t_st8i_0.setInt(3,Integer.parseInt(ret_con[i]));
       ResultSet rs8i_0              = t_st8i_0.executeQuery();
       //consulta nuevo consecutivo
       while(rs8i_0.next())
       {
         v_nuevoretiro = true;

         CallableStatement l_stmt3 = v_conexion_taxb.prepareCall("{? = call  TB_FINSERTAR_APORSEL(?,?,?,?,?,?)}");
         l_stmt3.registerOutParameter(1,Types.INTEGER);
         l_stmt3.registerOutParameter(7,Types.VARCHAR);
         l_stmt3.setString(2,f_producto);
         l_stmt3.setString(3,f_contrato);
         l_stmt3.setInt(4,v_conret);
         l_stmt3.setInt(5,rs8i_0.getInt(1));
         l_stmt3.setInt(6,rs8i_0.getInt(2));
         l_stmt3.execute();
         int  v_indicador2  = l_stmt3.getInt(1);
         String v_mensaje4 = l_stmt3.getString(7);
         l_stmt3.close();

       }
        rs8i_0.close();
        t_st8i_0.close();
      }
      else
      {
       return false;
      }
    }//si es seleccionado
   }
   catch(Exception ex)
   {
    System.out.println("   ");
   }

    /*Modificación hecha por APC para manejar el nuevo reglamento 2006-06-22*/          

    CallableStatement csP2 = v_conexion_taxb.prepareCall ( "{call TBPBD_Parametros_FuncionesAS(?,?,?,?)}");
    csP2.registerOutParameter(1,Types.VARCHAR);
    csP2.registerOutParameter(2,Types.VARCHAR);
    csP2.registerOutParameter(3,Types.VARCHAR);
    csP2.registerOutParameter(4,Types.VARCHAR);
    csP2.execute();

    
    String v_libreria= csP2.getString(1);
    String v_sistema = csP2.getString(2);
    String v_usumfund = csP2.getString(3);
    String v_passmfund = csP2.getString(4);
    csP2.close();

    String v_retorno_programa = new String("");
    String v_programa;
    CallableStatement cs2 = v_conexion_taxb.prepareCall ( "{? = call TBCL_FuncionesAs400.TBPL_ProgramaContrato(?,?,?,?,?)}");
    cs2.registerOutParameter(1,Types.CHAR);
    cs2.setString(2,f_contrato);
    cs2.setString(3,v_sistema);
    cs2.setString(4,v_usumfund);
    cs2.setString(5,v_passmfund);
    cs2.setString(6,v_libreria);
    cs2.executeUpdate();
    v_retorno_programa = cs2.getString(1);
    cs2.close();
    v_programa = v_retorno_programa.substring(0,v_retorno_programa.indexOf(";",0));
    /*FIN Modificación hecha por APC para manejar el nuevo reglamento 2006-06-22*/          


   CallableStatement l_stmt1 = v_conexion_taxb.prepareCall("{call TBPBD_RETIRO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
   l_stmt1.registerOutParameter(31,Types.VARCHAR);
   l_stmt1.registerOutParameter(32,Types.VARCHAR);
   l_stmt1.registerOutParameter(33,Types.INTEGER);
   l_stmt1.registerOutParameter(34,Types.DOUBLE);
   l_stmt1.registerOutParameter(35,Types.INTEGER);
   l_stmt1.registerOutParameter(36,Types.VARCHAR);
   l_stmt1.setString(1,f_producto);
   l_stmt1.setString(2,f_contrato);

   /*
    * MOS Se quita temporalmente :v_programa
    * Se agrego nuevamente
    */
   l_stmt1.setString(3,v_programa);
   
   if(v_nuevoretiro)
     l_stmt1.setInt(4,v_conret);
   else
    l_stmt1.setString(4,null);
   l_stmt1.setDate(5,hoy);
   l_stmt1.setDate(6,fecha_ret_e[i]);
   l_stmt1.setString(7,tipo_t[i]);
   l_stmt1.setString(8,clase_t[i]);
   l_stmt1.setDouble(9,valor[i]);
   l_stmt1.setString(10,"STV001");
   l_stmt1.setDouble(11,ret_vu[i]);
   l_stmt1.setString(12,penalizado[i]);
   l_stmt1.setString(13,prorata[i]);
   l_stmt1.setString(14,banco[i]);
   l_stmt1.setString(15,cuenta[i]);
   l_stmt1.setString(16,transaccion[i]);
   l_stmt1.setString(17,ret_usuario[i]);
   l_stmt1.setString(18,udad_proceso[i]);
   l_stmt1.setString(19,"SER006");
   l_stmt1.setString(20,afp[i]);
   l_stmt1.setString(21,tipo_i[i]);
   l_stmt1.setString(22,numero_i[i]);
   l_stmt1.setString(23,m_o[i]);
   l_stmt1.setString(24,m_b[i]);
   l_stmt1.setString(25,m_p[i]);
   l_stmt1.setString(26,m_c[i]);
   l_stmt1.setString(27,m_n[i]);
   l_stmt1.setString(28,r_n[i]);
   l_stmt1.setString(29,indicador01);
   l_stmt1.setString(30,indicador02);
   l_stmt1.execute();
   String v_pro        = l_stmt1.getString(31);
   String v_contra     = l_stmt1.getString(32);
   v_conret        = l_stmt1.getInt(33);
   double v_valnetret  = l_stmt1.getDouble(34);
   int v_cod_error     = l_stmt1.getInt(35);
   v_msj_error1        = l_stmt1.getString(36);
   l_stmt1.close();

   if(!v_msj_error1.equalsIgnoreCase("OK"))
     break;
   else if(v_msj_error1.equalsIgnoreCase("OK"))
    {
      //INSERTAR DISTRIBUCION DE FONDOS PARA EL NUEVO RETIRO
      CallableStatement t_cst8i_dis = v_conexion_taxb.prepareCall("{ ? = call TB_FDISFON_RETREV(?,?,?,?,?) }");
      t_cst8i_dis.registerOutParameter(1,Types.INTEGER);
      t_cst8i_dis.registerOutParameter(6,Types.VARCHAR);
      t_cst8i_dis.setString(2,f_producto);
      t_cst8i_dis.setString(3,f_contrato);
      t_cst8i_dis.setInt(4,v_conret);
      t_cst8i_dis.setInt(5,Integer.parseInt(ret_con[i]));
      t_cst8i_dis.execute();
      String v_msj_error_dis = t_cst8i_dis.getString(6);
      t_cst8i_dis.close();

      if(!v_msj_error_dis.equalsIgnoreCase("OK"))
       break;
      else if(v_msj_error_dis.equalsIgnoreCase("OK"))
      {
        //calcula la diferencia del valor neto entre los dos retiros
        double val_difNetos = ret_vneto[i] - v_valnetret;
        //TIPO DE TRANSACCION SEGUN SIGNO
        String v_tipTrans    = new String("");
        if((val_difNetos * -1 )<0)  v_tipTrans = "UTT009";
        else                v_tipTrans = "UTT008";

        //llamar procedimiento de registrar el ajuste
        CallableStatement t_cst8i_9 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Ajuste(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
        t_cst8i_9.registerOutParameter(16,Types.VARCHAR);
        t_cst8i_9.setString(1,f_producto);
        t_cst8i_9.setString(2,f_contrato);
        t_cst8i_9.setInt(3,cutivo_ajuste);
        t_cst8i_9.setInt(4,linea);
        t_cst8i_9.setString(5,hoy.toString());
        t_cst8i_9.setDate(6,fecha_ret_e[i]);
        t_cst8i_9.setString(7,"STR003");
        t_cst8i_9.setString(8,v_tipTrans);
        t_cst8i_9.setString(9,"UCT001");
        t_cst8i_9.setDouble(10,(val_difNetos*-1));
        t_cst8i_9.setString(11,razon);
        t_cst8i_9.setString(12,f_usuario);
        t_cst8i_9.setDouble(13,ret_vu[i]);
        t_cst8i_9.setInt(14,Integer.parseInt(ret_con[i]));
        t_cst8i_9.setInt(15,v_conret);
        t_cst8i_9.execute();
        v_msj_error2 = t_cst8i_9.getString(16);
        t_cst8i_9.close();
        if(!v_msj_error2.equalsIgnoreCase("BIEN"))
          break;
        else if(v_msj_error2.equalsIgnoreCase("BIEN"))
        {
         CallableStatement t_cst8i_10 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Cargos_Ajustes(?,?,?,?,?,?,?) }");
         t_cst8i_10.registerOutParameter(7,Types.VARCHAR);
         t_cst8i_10.setString(1,f_producto);
         t_cst8i_10.setString(2,f_contrato);
         t_cst8i_10.setInt(3,Integer.parseInt(ret_con[i]));
         t_cst8i_10.setInt(4,v_conret);
         t_cst8i_10.setInt(5,cutivo_ajuste);
         t_cst8i_10.setInt(6,linea);
         t_cst8i_10.execute();
         v_msj_error3 = t_cst8i_10.getString(7);
         t_cst8i_10.close();
         if(!v_msj_error3.equalsIgnoreCase("BIEN"))
          break;
        }//ajustei Ok
      }//distribucion ok
     }//RETIRO PRIMAi OK
 }//for
 if(v_msj_error1.equalsIgnoreCase("OK") && v_msj_error2.equalsIgnoreCase("BIEN") && v_msj_error3.equalsIgnoreCase("BIEN"))
   return true;
 else
   return false;
}//try
catch(Exception ex){
System.out.println("  ");
return false;

}
}//fin
//*******************************************************************************************************************
public boolean TBPL_Reversiones(String f_producto,String f_contrato,String [] ret_con,double[] ret_vu
                              ,int total_retiros,Connection v_conexion_taxb)
{
try
{
String estado_reversar = new String();
for(int indice=0;indice<total_retiros;indice++)
 {
   CallableStatement t_cst8i_11 = v_conexion_taxb.prepareCall("{ ? = call TBFBD_REVERSAR_RETIROS(?,?,?,?,?,?,?) }");
   t_cst8i_11.registerOutParameter(1,Types.VARCHAR);
   t_cst8i_11.setString(2,f_producto);
   t_cst8i_11.setString(3,f_contrato);
   t_cst8i_11.setString(4,"SER008");
   t_cst8i_11.setString(5,ret_con[indice]);
   t_cst8i_11.setDouble(6,ret_vu[indice]);
   t_cst8i_11.setInt(7,1);
   t_cst8i_11.setInt(8,1);
   t_cst8i_11.executeUpdate();
   estado_reversar = t_cst8i_11.getString(1);
   t_cst8i_11.close();
   if(!estado_reversar.equalsIgnoreCase("YES"))
     break;
 }//for
 if(estado_reversar.equalsIgnoreCase("YES"))
   return true;
 else
  return false;
}//try
catch(Exception ex){return false;}
}//fin
//*******************************************************************************************************************
public boolean TBPL_Beneficio(String f_producto,String f_contrato,java.sql.Date f_aDate
                            ,java.sql.Date[] fecha_ret_e,java.sql.Date f_aODate
                            ,int total_retiros,Connection v_conexion_taxb)
{
try
{
 String fa_tieneB  = new String("");
 String fao_tieneB = new String("");
 for(int indice=0;indice<total_retiros;indice++)
 {
    //valido beneficio de la F_A a la ret_fe
    CallableStatement t_cst8i_9 = v_conexion_taxb.prepareCall("{ call TBPBD_BENEFICIO_APORTE_RETIRO(?,?,?,?,?) }");
    t_cst8i_9.registerOutParameter(5,Types.VARCHAR);
    t_cst8i_9.setString(1,f_producto);
    t_cst8i_9.setString(2,f_contrato);
    t_cst8i_9.setDate(3,f_aDate);
    t_cst8i_9.setDate(4,fecha_ret_e[indice]);
    t_cst8i_9.executeUpdate();
    fa_tieneB = t_cst8i_9.getString(5);
    t_cst8i_9.close();
    //valido beneficio de la F_AOriginal a la ret_fe
    CallableStatement t_cst8i_10 = v_conexion_taxb.prepareCall("{ call TBPBD_BENEFICIO_APORTE_RETIRO(?,?,?,?,?) }");
    t_cst8i_10.registerOutParameter(5,Types.VARCHAR);
    t_cst8i_10.setString(1,f_producto);
    t_cst8i_10.setString(2,f_contrato);
    t_cst8i_10.setDate(3,f_aODate);
    t_cst8i_10.setDate(4,fecha_ret_e[indice]);
    t_cst8i_10.executeUpdate();
    fao_tieneB = t_cst8i_10.getString(5);
    t_cst8i_10.close();
    if(fa_tieneB.equals(fao_tieneB))
      break;
  }//for
  if(fa_tieneB.equals(fao_tieneB))
    return false;
  else
    return true;
}//try
catch(Exception ex){return false;}
}//fin PL_Beneficio
//*******************************************************************************************************************
  public String getServletInfo()
   {
    return "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_CCONTINGENTE Information";
   }
}
