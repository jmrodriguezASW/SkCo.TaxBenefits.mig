
package TBPKT_MODULO_APORTES;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.*;

import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;

import co.oldmutual.taxbenefit.util.DataSourceWrapper; // INT20131108

/**
 *Este servlet sirve para actualizar un aportes en su calidad de disponibilidad
 */

public class TBS_ACTUALIZA_DISPONIBILIDAD extends HttpServlet
{
   int v_historia = -1;
   public void init(ServletConfig config) throws ServletException
   { super.init(config);}
   //------------------------------------------------------------------------------------------------------------------------
   public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
                    throws ServletException, IOException
   {
       Connection v_conexion_taxb    =   null;
       //INT20131108
       String rutaFisica = TBCL_REFERENCIAS.TBCL_CONSULTA("SMM003");
       //rutaFisica  =  "c:\\Taxbenefits\\Taxbenefits\\public_html\\WEB-INF\\";
       String POLICY_FILE_LOCATION = rutaFisica+"antisamy-tinymce-1.4.4.xml"; // Path to policy file
       AntiSamy as = new AntiSamy(); // INT20131108      
      //STBCL_GenerarBaseHTMLII plantilla = new STBCL_GenerarBaseHTMLII;
      PrintWriter salida                = new PrintWriter (respuesta.getOutputStream());
      //seguridad
      HttpSession session               = peticion.getSession(true);
      if (session == null) session      = peticion.getSession(true);
      respuesta.setContentType("text/html");
      /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
      //respuesta.setHeader("Pragma", "No-Cache");
      //respuesta.setDateHeader("Expires", 0);
      String v_contrato       = "", v_producto = "", v_usuario  = "", v_unidad = "";
      String v_tipousu        = "", v_idworker = "";
      String parametros[]     = new String[8];
      String cadena2          = peticion.getParameter("cadena");
      String nuevaCadena      = cadena2;
      String ip_tax           = peticion.getRemoteAddr();
       
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
      //valido la veracidad del producto y contrato enviados por pipeline
      parametros              = TBCL_Seguridad.TBFL_Seguridad(cadena2, salida, ip_tax);
      v_contrato              = parametros[0];
      v_producto              = parametros[1];
      v_usuario               = parametros[2];
      v_unidad                = parametros[3];
      v_tipousu               = parametros[4];
      v_idworker              = parametros[5];

      int MAX = 900;
      
      //seguridad
      try
      {
         v_conexion_taxb =   DataSourceWrapper.getInstance().getConnection();
         String select8i_1 ="SELECT "+
                            "TO_CHAR(APO_FECHA_EFECTIVA,'RRRR-MM-DD') "+
                            ",APO_NUMERO_UNIDADES "+
                            ",APO_CAPITAL "+
                            ",APO_CUENTA_CONTINGENTE "+
                            ",APO_EMP_GRUPO "+
                            ",DIA_FECHA "+
                            ",DIA_PORCENTAJE_DISPONIBILIDAD "+
                            ",APO_CONSECUTIVO "+
                            ",APO_DETALLE_CONDICION_SKA"+
                            ",APO_REF_CONDICION_SKA "+
                            ",REF_VALOR "+
                            ",REF_DESCRIPCION "+
                            ",APO_RENDIMIENTOS "+
                            "FROM "+
                            "TBAPORTES "+
                            ",TBDISPONIBILIDADES_APORTES "+
                            ",TBREFERENCIAS "+
                            ",TBCONTRATOS "+
                            "WHERE "+
                            "APO_CON_PRO_CODIGO        = DIA_APO_CON_PRO_CODIGO "+
                            "AND APO_CON_NUMERO        = DIA_APO_CON_NUMERO "+
                            "AND APO_CONSECUTIVO       = DIA_APO_CONSECUTIVO "+
                            "AND APO_CON_PRO_CODIGO    = CON_PRO_CODIGO "+
                   		      "AND APO_CON_NUMERO        = CON_NUMERO "+
     	  	                  "AND DIA_FECHA             = (SELECT MAX(DIA_FECHA) "+
                                                         "FROM TBDISPONIBILIDADES_APORTES "+
                                                         "WHERE  DIA_APO_CON_PRO_CODIGO = ? "+
                                                         "AND DIA_APO_CON_NUMERO        = ? "+
				                                                 "AND DIA_APO_CONSECUTIVO       = APO_CONSECUTIVO) "+
                            "AND APO_REF_CONDICION_SKA = REF_CODIGO (+) "+
                            "AND APO_FECHA_EFECTIVA BETWEEN TO_DATE(?,'RRRR-MM-DD') AND TO_DATE(?,'RRRR-MM-DD') "+
                            "AND APO_CON_PRO_CODIGO  = ? "+
                            "AND APO_CON_NUMERO      = ? "+
                            "AND CON_FECHA_CANCELACION IS NULL "+
                            "AND (( APO_APORTE_TRASLADO ='S' AND APO_INFORMACION_TRASLADO = 'N') OR ( APO_APORTE_TRASLADO ='N')) "+
                            "ORDER BY APO_FECHA_EFECTIVA DESC ";
         //actualiza todos los aportes del contrato si tienen condicion
         String select8i_2 ="SELECT "+
                            "TO_CHAR(APO_FECHA_EFECTIVA,'RRRR-MM-DD') "+
                            ",APO_CONSECUTIVO "+
                            "FROM "+
                            "TBAPORTES "+
                            ",TBDISPONIBILIDADES_APORTES "+
                            ",TBREFERENCIAS "+
                            ",TBCONTRATOS "+
                            "WHERE "+
                            "APO_CON_PRO_CODIGO        = DIA_APO_CON_PRO_CODIGO "+
                            "AND APO_CON_NUMERO        = DIA_APO_CON_NUMERO "+
                            "AND APO_CONSECUTIVO       = DIA_APO_CONSECUTIVO "+
                            "AND APO_CON_PRO_CODIGO    = CON_PRO_CODIGO "+
                            "AND APO_CON_NUMERO        = CON_NUMERO "+
                            "AND DIA_FECHA             = (SELECT MAX(DIA_FECHA) "+
                                                           "FROM TBDISPONIBILIDADES_APORTES "+
                                                           "WHERE  DIA_APO_CON_PRO_CODIGO = ? "+
                                                           "AND DIA_APO_CON_NUMERO        = ? "+
				                                                   "AND DIA_APO_CONSECUTIVO       = APO_CONSECUTIVO) "+
                            "AND APO_REF_CONDICION_SKA = REF_CODIGO (+) "+
                            "AND APO_CON_PRO_CODIGO  = ? "+
                            "AND APO_CON_NUMERO      = ? "+
                            "AND CON_FECHA_CANCELACION IS NULL "+
                            "AND (( APO_APORTE_TRASLADO ='S' AND APO_INFORMACION_TRASLADO = 'N') OR ( APO_APORTE_TRASLADO ='N')) "+
                            "AND APO_EMP_GRUPO IS NOT NULL "+
                            "ORDER BY APO_FECHA_EFECTIVA DESC ";

         //SELECCIONA LOS APORTES SEGUN FECHA Y SI TIENEN CONDICION EEMPRESA
         String select8i_3 ="SELECT "+
                            "TO_CHAR(APO_FECHA_EFECTIVA,'RRRR-MM-DD') "+
                            ",APO_CONSECUTIVO "+
                            "FROM "+
                            "TBAPORTES "+
                            ",TBDISPONIBILIDADES_APORTES "+
                            ",TBREFERENCIAS "+
                            ",TBCONTRATOS "+
                            "WHERE "+
                            "APO_CON_PRO_CODIGO        = DIA_APO_CON_PRO_CODIGO "+
                            "AND APO_CON_NUMERO        = DIA_APO_CON_NUMERO "+
                            "AND APO_CONSECUTIVO       = DIA_APO_CONSECUTIVO "+
                            "AND APO_CON_PRO_CODIGO    = CON_PRO_CODIGO "+
                  		      "AND APO_CON_NUMERO        = CON_NUMERO "+
		                        "AND DIA_FECHA             = (SELECT MAX(DIA_FECHA) "+
                                                         "FROM TBDISPONIBILIDADES_APORTES "+
                                                         "WHERE  DIA_APO_CON_PRO_CODIGO = ? "+
                                                         "AND DIA_APO_CON_NUMERO        = ? "+
				                                                 "AND DIA_APO_CONSECUTIVO       = APO_CONSECUTIVO) "+
                            "AND APO_REF_CONDICION_SKA = REF_CODIGO (+) "+
                            "AND APO_FECHA_EFECTIVA BETWEEN TO_DATE(?,'RRRR-MM-DD') AND TO_DATE(?,'RRRR-MM-DD') "+
                            "AND APO_CON_PRO_CODIGO  = ? "+
                            "AND APO_CON_NUMERO      = ? "+
                            "AND CON_FECHA_CANCELACION IS NULL "+
                            "AND (( APO_APORTE_TRASLADO ='S' AND APO_INFORMACION_TRASLADO = 'N') OR ( APO_APORTE_TRASLADO ='N')) "+
                            "AND APO_EMP_GRUPO IS NOT NULL "+
                            "ORDER BY APO_FECHA_EFECTIVA DESC ";



         /*TBCL_HTML   hoja_base                 = new TBCL_HTML();
         TBCL_HTML   hoja_error                = new TBCL_HTML();
         TBCL_HTML   parametros_requeridos     = new TBCL_HTML();
         TBCL_HTML   pagina3                   = new TBCL_HTML();
         TBCL_HTML   fechas_aportes            = new TBCL_HTML();
         TBCL_HTML   nombres                   = new TBCL_HTML();*/
         String v_condicion[]                  = new String[MAX];
         double v_Consecutivos[]               = new double[MAX];
         String v_Fecha[]                      = new String[MAX];        //vector de fechas efectivas
         String v_E[]                          = new String[MAX];       //vector de indicadores de s o n para aportes de empresas
         String v_e[]                          = new String[MAX];      //vector de indicadores de s o n para aportes de apleados
         String v_Porcentaje[]                 = new String[MAX];     //vector de porcentajes
         int v_Capital[]                       = new int[MAX];       //vector de valores de capital
         int v_Cuenta_C[]                      = new int[MAX];      //vector de valores de c_c
         double v_Rtos[]                       = new double[MAX];  //vector de rtos
         String v_Detalle[]                    = new String[MAX]; //vector de detalles de condicion por aporte
         String v_Condicon_ska[]               = new String[MAX];
         String grupo_id[]                     = new String[MAX];
         String v_codigo[]                     = new String[MAX];
         int v_valor[]                         = new int[MAX];
         String v_descripcion[]                = new String[MAX];
         //valido la veracidad del producto y contrato enviados por pipeline
         String  nombre_producto    = peticion.getParameter("nom_producto");
         String  numero_contrato    = peticion.getParameter("num_contrato");
         String  usuario            = peticion.getParameter("usuario");
         if(nombre_producto != null)
         {
            v_producto = "x";
           v_contrato = "x";
         }

        //------------------------------------------------------------------pagina numero 1
        //si los parametros son validos, dibujo hojabase y devuelvo parametros x y x
        if(TBCL_HTML.TBPL_Parametros_Requeridos(v_producto,v_contrato))
        {
          v_historia = -1;
          TBCL_HTML.TBPL_Hoja_Base("ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",v_producto,v_contrato,salida,0,
                                  "TBS_ACTUALIZA_DISPONIBILIDAD",v_conexion_taxb,"",TBCL_HTML.TBPL_Nombres(v_producto,v_contrato),nuevaCadena);
          return;
        }
        else if ((!TBCL_HTML.TBPL_Parametros_Requeridos(nombre_producto,numero_contrato))&&
                 ( nombre_producto == null)&&
                 ( numero_contrato == null))
             {
                 //STBCL_GenerarBaseHTML plantilla2 = new STBCL_GenerarBaseHTML();
                 String msj = "EL CONTRATO "+v_contrato+" NO EXISTE EN EL SISTEMA.";
                 salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                           "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_DISPONIBILIDAD","<font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><CENTER>"+msj+"</CEBTER></font></b>",false));
                 salida.println("<BR><BR>");
                 salida.println("<center><input type='button' value='Aceptar' Onclick='history.go(-1);' ></center>");
                 salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                 salida.flush();
                 return;
             }
            //------------------------------------------------------------------pagina numero 2
            else if  ((!TBCL_HTML.TBPL_Parametros_Requeridos(nombre_producto,numero_contrato))&&
                      (!nombre_producto.equals("x"))&&
                      (!numero_contrato.equals("x"))&&
                      (!nombre_producto.equals("y"))&&
                      (!numero_contrato.equals("y"))&&
                      (!nombre_producto.equals("z"))&&
                      (!numero_contrato.equals("z")))
                 {
                    v_historia = -2;
                    TBCL_HTML.TBPL_Hoja_Error(nombre_producto,numero_contrato,salida,0,"TBS_ACTUALIZA_DISPONIBILIDAD",
                                              "ACTUALIZADOR DE DISPONIBILIDAD DE APORTES");
                    return;
                 }
                 //------------------------------------------------------------------pagina numero 3
                 //si los parametros son x y x, debo generar hoja 2
                 else if(nombre_producto.equals("x")&&numero_contrato.equals("x"))
                      {
                          String  tipo_select     = "";
                          tipo_select     = peticion.getParameter("tipo_select");
                          v_historia = -2;
                          //x me identifica producto desconocido y un contrato desconocido, para que solo entre a la llamada de pipline
                          //cuando vuelva a llamar esta misma le enviaré al servlet los valores respectivos de x y x.
                          //estoy haciendo un nuevo llamado a la misma forma, solo que aquí no se llamará de nuevo a la hoja base
                          //debo validar las fechas, las validaré por medio de JavaScript en la hoja base
                          //debo generar la hoja de r/ta # 2
                          String estado           = "";
                          int total_aportes       = 0;                       //total aportes por contrato
                          String  f_producto      = peticion.getParameter("f_producto");
                          String  f_contrato      = peticion.getParameter("f_contrato");
                          String fecha_1          = peticion.getParameter("fecha_1");
                          String fecha_2          = peticion.getParameter("fecha_2");
                          double rtos             = 0;
                          double apo_consecutivo     = 0;                               //consecutivo de la tabla tbaportes
                          String f_e              = "";                             //fecha_efectiva
                          int num_u               = 0;                             //numero de udds
                          int s_c                 = 0;                            //saldo capital
                          int s_c_c               = 0;                           //saldo cuenta contingente
                          String g_id             = "";                         //grupo id
                          String f_m              = "";                        //fecha maxima
                          String p_d              = "";                       //porcentaje de disponibilidad
                          String Condicion_s      = " ";
                          String ref_codigo_a     = " ";
                          String ref_ds_a         = " ";
                          int ref_valor_a         = 0;
                          int i=0;
                          String detalle          = " ";
                          String descripcion      = " ";
                          String codigo           = " ";
                          int valor               = 0;
                          if(tipo_select != null)
                          {
                             //jag se adiciona para que dibuje entradas de fecha y porcentaje para aportes de empresa
                             //dibujo para todos loa aportes
                             //dibujo pagina que pide disponibilidad,fecha y porcentaje
                             salida.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                            "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_DISPONIBILIDAD",
                                            " ",true,"moduloparametro.js","return checkrequired(this)"));
                             //consecucuion de datos de cabecera para el contrato, el producto y el consecutivo
                             salida.println("<p><FONT color=black face=verdana size=1>*Fecha:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>");
                             salida.println("<INPUT NAME='obligatorio_fecha_x' TYPE=text MAXLENGTH=10 SIZE=12' onchange='checkdate(this)'> <FONT color=black face=verdana size=1> YYYY-MM-DD</font></p>");
                             salida.println("<p><FONT color=black face=verdana size=1>*Porcentaje: </font>");
                             salida.println("<input type='text' name='obligatorio_porcentaje_x'  MAXLENGTH=6 SIZE=6 onchange='if (esNumero(this)==1) return false;checkDecimals(this,this.value,2),esMayor(this)'></p>");
                             //campos ocultos de producto y contrato que me dicen cual hoja dibujar
                             salida.println("<input type='hidden' name='nom_producto' value='z'>");
                             salida.println("<input type='hidden' name='num_contrato' value='z'>");
                             //campos ocultos necesarios par relizar update final
                             salida.println("<input type='hidden' name='f_producto' value='"+f_producto+"'>");
                             salida.println("<input type='hidden' name='f_contrato' value='"+f_contrato+"'>");
                             salida.println("<input type='hidden' name='tipo_select' value='1'>");
                             salida.println("<input type='hidden' name='tipo_opcion' value='1'>");
                             salida.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
                             salida.println("<center><input type='submit' value='Aceptar' >"+
                                            "<input type='button' value='Cancelar' Onclick=history.go(-2);></center>");
                             salida.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
                             salida.close();
                          }
                          else
                          {
                             //se dibujan los aportes segun fecha
                             PreparedStatement t_st8i = v_conexion_taxb.prepareStatement(select8i_1);
                             ResultSet t_rs8i;
                             t_st8i.setString(1,f_producto);
                             t_st8i.setString(2,f_contrato);
                             t_st8i.setString(3,fecha_1);
                             t_st8i.setString(4,fecha_2);
                             t_st8i.setString(5,f_producto);
                             t_st8i.setString(6,f_contrato);
                             t_rs8i = t_st8i.executeQuery();
                             //datos de salida sobre pag 3
                             while(t_rs8i.next())
                             {
                                //capturo datos sobre vables mias , enviados por el proc.
                                apo_consecutivo  = t_rs8i.getDouble(8);
                                f_e              = t_rs8i.getString(1);                 //fecha_efectiva
                                num_u            = t_rs8i.getInt(2);                   //numero de udds
                                s_c              = t_rs8i.getInt(3);                  //saldo capital
                                s_c_c            = t_rs8i.getInt(4);                 //saldo cuenta contingente
                                g_id             = t_rs8i.getString(5);             //grupo id
                                f_m              = t_rs8i.getString(6);            //fecha maxima
                                p_d              = t_rs8i.getString(7);           //porcentaje de disponibilidad
                                Condicion_s      = t_rs8i.getString(9);
                                ref_codigo_a     = t_rs8i.getString(10);
                                valor            = t_rs8i.getInt(11);
                                ref_ds_a         = t_rs8i.getString(12);
                                rtos             = t_rs8i.getDouble(13);
                                try
                                {
                                   if(!g_id.equals(null))
                                   {
                                      //llamado sobre la funcion que me devuelvo la condicion de disponibilidad siempre y cuando el gruppoid sea <> NULL
                                      CallableStatement t_cst8i_2 = v_conexion_taxb.prepareCall("{ call TBPBD_CONDICION(?,?,?,?,?,?,?,?) }");
                                      t_cst8i_2.registerOutParameter(4,Types.VARCHAR);
                                      t_cst8i_2.registerOutParameter(5,Types.VARCHAR);
                                      t_cst8i_2.registerOutParameter(6,Types.NUMERIC);
                                      t_cst8i_2.registerOutParameter(7,Types.VARCHAR);
                                      t_cst8i_2.registerOutParameter(8,Types.VARCHAR);
                                      t_cst8i_2.setString(1,g_id);
                                      t_cst8i_2.setString(2,f_contrato);
                                      t_cst8i_2.setString(3,f_producto);
                                      t_cst8i_2.execute();
                                      detalle      = t_cst8i_2.getString(4);
                                      codigo       = t_cst8i_2.getString(5);
                                      valor        = t_cst8i_2.getInt(6);
                                      descripcion  = t_cst8i_2.getString(7);
                                      estado       = t_cst8i_2.getString(8);
                                       t_cst8i_2.close();
                                   }
                                }
                                catch(Exception ex){detalle=Condicion_s;codigo=ref_codigo_a;valor=ref_valor_a;descripcion=ref_ds_a;}
                                //independientemente debo generar la pagina,sea de E o de e
                                //defino vectpres para mantener la informacion y luego dibujarla sobre la proxima pagina de respuesta,
                                //mantengo todos los valores por aporte
                                v_Fecha[total_aportes]        = f_e;
                                v_Porcentaje[total_aportes]   = p_d;
                                v_Capital[total_aportes]      = s_c;
                                v_Cuenta_C[total_aportes]     = s_c_c;
                                v_Rtos[total_aportes]         = rtos;
                                v_Consecutivos[total_aportes] = apo_consecutivo;
                                v_condicion[total_aportes]    = detalle;
                                v_codigo[total_aportes]       = codigo;
                                v_valor[total_aportes]        = valor;
                                v_descripcion[total_aportes]  = descripcion;
                                grupo_id[total_aportes]       = g_id;
                                total_aportes++;
                             }//While
                             //llama pagina de respuesta numero tres y proceso la resouesta 4
                             TBCL_HTML.TBPL_Encabezados_Disponibles(v_condicion,v_Fecha,v_Porcentaje,v_Capital,v_Cuenta_C,v_Rtos,
                                                                  "ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",f_producto,f_contrato,salida,
                                                                  total_aportes,v_Consecutivos,grupo_id,v_codigo,v_descripcion,v_valor,nuevaCadena,fecha_1,fecha_2);
                              
                              t_rs8i.close();
                             t_st8i.close();
                             return;
                          }
                      }
                      //------------------------------------------------------------------pagina numero 4
                      else if(nombre_producto.equals("y")&&numero_contrato.equals("y"))
                           {
                              String tipo_select       = peticion.getParameter("tipo_select");
                              v_historia = -3;
                              String f_producto       = peticion.getParameter("f_producto");
                              String f_contrato       = peticion.getParameter("f_contrato");

                              if (tipo_select != null)
                              {
                                 //aportes segun  rango y de empresa
                                 //jag se adiciona para que dibuje entradas de fecha y porcentaje para aportes de empresa
                                 //dibujo para todos loa aportes
                                 //dibujo pagina que pide disponibilidad,fecha y porcentaje
                                 String v_fecha1       = peticion.getParameter("v_fecha1");
                                 String v_fecha2       = peticion.getParameter("v_fecha2");
                                 salida.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_DISPONIBILIDAD",
                                                " ",true,"moduloparametro.js","return checkrequired(this)"));
                                //consecucuion de datos de cabecera para el contrato, el producto y el consecutivo
                                //dibujo encabezado
                                //dibujo campos de entrada
                                salida.println("<p><FONT color=black face=verdana size=1>*Fecha:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>");
                                salida.println("<INPUT NAME='obligatorio_fecha_x' TYPE=text MAXLENGTH=10 SIZE=12' onchange='checkdate(this)'> <FONT color=black face=verdana size=1> YYYY-MM-DD</font></p>");
                                salida.println("<p><FONT color=black face=verdana size=1>*Porcentaje: </font>");
                                salida.println("<input type='text' name='obligatorio_porcentaje_x'  MAXLENGTH=6 SIZE=6 onchange='if (esNumero(this)==1) return false;checkDecimals(this,this.value,2),esMayor(this)'></p>");
                                //campos ocultos de producto y contrato que me dicen cual hoja dibujar
                                salida.println("<input type='hidden' name='nom_producto' value='z'>");
                                salida.println("<input type='hidden' name='num_contrato' value='z'>");
                                //campos ocultos necesarios par relizar update final
                                salida.println("<input type='hidden' name='f_producto' value='"+f_producto+"'>");
                                salida.println("<input type='hidden' name='f_contrato' value='"+f_contrato+"'>");
                                salida.println("<input type='hidden' name='tipo_select' value='1'>");
                                salida.println("<input type='hidden' name='tipo_opcion' value='2'>");
                                salida.println("<input type='hidden' name='v_fecha1' value='"+v_fecha1+"'>");
                                salida.println("<input type='hidden' name='v_fecha2' value='"+v_fecha2+"'>");
                                salida.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
                                salida.println("<center><input type='submit' value='Aceptar' >"+
                                               "<input type='button' value='Cancelar' Onclick=history.go(-2);></center>");
                                salida.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
                                salida.close();
                                return;

                              }
                              else
                              {
                                 //aporte seleccionado
                                 int indice              = 0;
                                 String str              = " ";
                                 int total_aportes       = 0;                                 //total aportes por contrato
                                 int total_dispo         = 0;                                //total  datos captirados disponibles por aporte
                                 String fechas_dispo[]   = new String[100];                 //vector de fechas de dsiponibilidad por aporte
                                 String p_dispo[]        = new String[100];                   //vector de % disponibles por aportes
                                 int valores_dispo[]     = new int[100];                  //vector de valores disponibles por aportes
                                 Integer t_a             = new Integer(peticion.getParameter("total_aportes"));
                                 total_aportes           = t_a.intValue();
                                 String cadena           = peticion.getParameter("fila");
                                 indice                  = cadena.indexOf("$");
                                 String fecha_e          = cadena.substring(0,indice);
                                 str                     = cadena.substring(indice+1,cadena.length());
                                 indice                  = str.indexOf("$");
                                 String capital          = str.substring(0,indice);
                                 str                     = str.substring(indice+1,str.length());
                                 indice                  = str.indexOf("$");
                                 String rtos             = str.substring(0,indice);
                                 str                     = str.substring(indice+1,str.length());
                                 indice                  = str.indexOf("%");
                                 String cc               = str.substring(0,indice);
                                 str                     = str.substring(indice+1,str.length());
                                 indice                  = str.indexOf("*");
                                 String porc             = str.substring(0,indice);
                                 str                     = str.substring(indice+1,str.length());
                                 indice                  = str.indexOf("@");
                                 String condi            = str.substring(0,indice);
                                 str                     = str.substring(indice+1,str.length());
                                 indice                  = str.indexOf("|");
                                 String cutivo           = str.substring(0,indice);
                                 str                     = str.substring(indice+1,str.length());
                                 indice                  = str.indexOf("&");
                                 String g_id             = str.substring(0,indice);
                                 str                     = str.substring(indice+1,str.length());
                                 indice                  = str.indexOf("=");
                                 String ref_descripcion  = str.substring(0,indice);
                                 str                     = str.substring(indice+1,str.length());
                                 indice                  = str.indexOf("#");
                                 String ref_codigo       = str.substring(0,indice);
                                 String ref_valor        = str.substring(indice+1,str.length());
                                 Double cons            = new Double(cutivo);
                                 double consecutivo         = cons.doubleValue();
                                 String select8i_2a = "SELECT TO_CHAR(DIA_FECHA,'RRRR-MM-DD'),DIA_PORCENTAJE_DISPONIBILIDAD,DIA_VALOR_DISPONIBLE "+
                                                      "FROM tbdisponibilidades_aportes " +
                                                      "WHERE DIA_APO_CON_PRO_CODIGO = ? "+
                                                      "AND DIA_APO_CON_NUMERO       = ? "+
                                                      "AND DIA_APO_CONSECUTIVO      = ?  "+
                                                      "ORDER BY DIA_FECHA DESC";
                                 PreparedStatement t_st8i = v_conexion_taxb.prepareStatement(select8i_2a);
                                 ResultSet t_rs8i;
                                 t_st8i.setString(1,f_producto);
                                 t_st8i.setString(2,f_contrato);
                                 t_st8i.setDouble(3,consecutivo);
                                 t_rs8i = t_st8i.executeQuery();
                                 while(t_rs8i.next())
                                 {
                                    fechas_dispo[total_dispo]   = t_rs8i.getString(1);
                                    p_dispo[total_dispo]        = t_rs8i.getString(2);
                                    valores_dispo[total_dispo]  = t_rs8i.getInt(3);
                                    total_dispo++;
                                 }
                                 t_rs8i.close();
                                 t_st8i.close();
                                 TBCL_HTML.TBPL_Fechas_Aportes(total_dispo,fechas_dispo,p_dispo,valores_dispo,salida,"ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                                    f_producto,f_contrato,v_conexion_taxb,consecutivo,condi,g_id,fecha_e,capital,cc,rtos,porc,ref_descripcion,
                                                                    ref_codigo,ref_valor,nuevaCadena);
                                 return;
                              }
                           }
                           //------------------------------------------------------------------pagina numero 5
                           else if(nombre_producto.equals("z")&&numero_contrato.equals("z"))
                                {
                                   try
                                   {
                                      v_historia = -4;
                                      /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase STBCL_GenerarBaseHTML, no es necesaria la instancia nueva*/  
 //STBCL_GenerarBaseHTML plantilla1 = new STBCL_GenerarBaseHTML;
                                      String tipo_opcion       = peticion.getParameter("tipo_opcion");
                                      if(tipo_opcion != null)
                                         System.out.println(" ");
                                      else
                                         tipo_opcion  = "3";
                                      String f_producto        = peticion.getParameter("f_producto");
                                      String f_contrato        = peticion.getParameter("f_contrato");
                                      String portmp1           = peticion.getParameter("obligatorio_porcentaje_x");
                                      Double portmp2           = new Double(portmp1);
                                      double porcentaje        = portmp2.doubleValue();
                                      String dia_fecha         = peticion.getParameter("obligatorio_fecha_x");

                                      String msj     = "ZZZZZZZZZZZZZZZ";
                                      String botones = " ";
                                      String select8i_fecha1 = "SELECT '*' FROM DUAL WHERE TO_DATE(?,'RRRR-MM-DD')>=TO_DATE(?,'RRRR-MM-DD')";
                                      String select8i_fecha2 = "SELECT '*' FROM DUAL WHERE TO_DATE(?,'RRRR-MM-DD')<=SYSDATE ";
                                      boolean fecha_xcorrecta  = false;
                                      boolean hoy_estabien     = false;
                                      double consecutivo  = 0;
                                      String f_e   ="";

                                      if(tipo_opcion.equals("1"))
                                      {
                                         //toma del select

                                         PreparedStatement t_st8i_2 = v_conexion_taxb.prepareStatement(select8i_2);
                                         ResultSet t_rs8i_2;
                                         t_st8i_2.setString(1,f_producto);
                                         t_st8i_2.setString(2,f_contrato);
                                         t_st8i_2.setString(3,f_producto);
                                         t_st8i_2.setString(4,f_contrato);
                                         t_rs8i_2 = t_st8i_2.executeQuery();
                                         msj     = "No se encontraron aportes con condicion de empresa para ser actualizados.";
                                         while(t_rs8i_2.next())
                                         {

                                            //capturo datos sobre vables mias , enviados por el proc.
                                            consecutivo      = t_rs8i_2.getDouble(2);
                                            f_e              = t_rs8i_2.getString(1);                 //fecha_efectiva

                                            msj = TBCL_MANEJAR_APORTES(dia_fecha, f_e,f_producto  ,f_contrato ,
                                                                       consecutivo  ,porcentaje,portmp1,
                                                                       select8i_fecha1 , select8i_fecha2,
                                                                        v_conexion_taxb, salida);

                                            if(!msj.trim().equals("LA ACTUALIZACION FUE EXITOSA"))
                                            {
                                               v_conexion_taxb.rollback();
                                               salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                               "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_DISPONIBILIDAD",msj,false));
                                               salida.println("<BR><BR>");
                                               salida.println("<center><input type='button' value='Aceptar'Onclick=history.go(-3);><input type='button' value='Regresar' Onclick=history.go(-2);></center>");
                                               salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                                               salida.flush();
                                               return;
                                            }

                                         }
                                         t_rs8i_2.close();
                                         t_st8i_2.close();
                                         v_conexion_taxb.commit();
                                         salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                         "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_DISPONIBILIDAD",msj,false));
                                         salida.println("<BR><BR>");
                                         salida.println("<center><input type='button' value='Aceptar'Onclick=history.go(-3);><input type='button' value='Regresar' Onclick=history.go(-2);></center>");
                                         salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                                         salida.flush();
                                         return;
                                      }
                                      else if (tipo_opcion.equals("2"))
                                           {
                                              //aportes empresa segn fecha

                                              String v_fecha1       = peticion.getParameter("v_fecha1");

                                              String v_fecha2       = peticion.getParameter("v_fecha2");

                                              //toma del select

                                              PreparedStatement t_st8i_3 = v_conexion_taxb.prepareStatement(select8i_3);
                                              ResultSet t_rs8i_3;
                                              t_st8i_3.setString(1,f_producto);
                                              t_st8i_3.setString(2,f_contrato);
                                              t_st8i_3.setString(3,v_fecha1);
                                              t_st8i_3.setString(4,v_fecha2);
                                              t_st8i_3.setString(5,f_producto);
                                              t_st8i_3.setString(6,f_contrato);
                                              t_rs8i_3 = t_st8i_3.executeQuery();
                                              msj     = "No se encontraron aportes con condicion de empresa para ser actualizados.";
                                              while(t_rs8i_3.next())
                                              {

                                                 //capturo datos sobre vables mias , enviados por el proc.
                                                 consecutivo      = t_rs8i_3.getDouble(2);
                                                 f_e              = t_rs8i_3.getString(1);                 //fecha_efectiva

                                                 msj = TBCL_MANEJAR_APORTES(dia_fecha, f_e,f_producto  ,f_contrato ,
                                                                            consecutivo  ,porcentaje,portmp1,
                                                                            select8i_fecha1 , select8i_fecha2,
                                                                            v_conexion_taxb, salida);

                                                if(!msj.trim().equals("LA ACTUALIZACION FUE EXITOSA"))
                                                {
                                                   v_conexion_taxb.rollback();
                                                   salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                   "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_DISPONIBILIDAD",msj,false));
                                                   salida.println("<BR><BR>");
                                                   salida.println("<center><input type='button' value='Aceptar'Onclick=history.go(-3);><input type='button' value='Regresar' Onclick=history.go(-2);></center>");
                                                   salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                                                   salida.flush();
                                                   return;
                                                }

                                              }

                                              t_rs8i_3.close();
                                              t_st8i_3.close();
                                              v_conexion_taxb.commit();

                                              salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                              "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_DISPONIBILIDAD",msj,false));
                                              salida.println("<BR><BR>");
                                              salida.println("<center><input type='button' value='Aceptar'Onclick=history.go(-3);><input type='button' value='Regresar' Onclick=history.go(-2);></center>");
                                              salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                                              salida.flush();
                                              return;
                                           }
                                           else if(tipo_opcion.equals("3"))
                                           {
                                              //aporte seleccionado
                                              String estado            = " ";
                                              int indice               = 0;
                                              String str               = " ";
                                              f_e               = peticion.getParameter("f_e");
                                              //de cambio
                                              String cadena            = peticion.getParameter("cadena2");
                                              indice                   = cadena.indexOf("$");
                                              String ref_valor         = cadena.substring(0,indice);
                                              str                      = cadena.substring(indice+1,cadena.length());
                                              indice                   = str.indexOf("|");
                                              String ref_codigo        = str.substring(0,indice);
                                              String ref_descripcion   = str.substring(indice+1,str.length());
                                              //Originales
                                              String ref_valor_o       = peticion.getParameter("ref_valor_o");
                                              String ref_codigo_o      = peticion.getParameter("ref_codigo_o");
                                              String ref_descripcion_o = peticion.getParameter("ref_descripcion_o");
                                              String conse             = peticion.getParameter("conse");
                                              Double cons             = new Double(conse);
                                              consecutivo          = cons.doubleValue();
                                              String g_id              = peticion.getParameter("g_id");
                                              String detalle1          = " ";
                                              String detalle2          = " ";
                                              //esta variable me revisa lo de Ref_codigo nuevo es diferente dell anterior,
                                              //el porcentaje debe ser igual al ref_valor de la lista
                                              boolean buen_porcentaje  = false;
                                              detalle1                 = peticion.getParameter("condicion");
                                                try
                                                {
                                                    CleanResults cr = as.scan(peticion.getParameter("condicion"), new File(POLICY_FILE_LOCATION));
                                                    detalle1 = cr.getCleanHTML();
                                                }
                                                catch (Exception e)
                                                {
                                                  e.printStackTrace();
                                                } //INT20131108
                                              double capital           = 0;
                                              double rtos              = 0;
                                              double valor_disponible  = 0;
                                              double sumatoria_valores = 0;
                                              double valor             = 0;
                                              boolean g_idnull         = false;
                                              boolean ref_codigonull   = false;
                                              String next_valido       = " ";
                                              //con esta variable validar la fecha_x contra la fecha_e
                                              //si la fecha_x es menor que la efectiva, el proceso no puede continuar
                                              //si la fecha_x es mayor que la efectiva debo eliminar, buscar en sumatoria y si no hay algo allí, inserto con f_e y %_x
                                              //si la fecha_x es mayor que la efectiva debo eliminar, buscar en sumatoria y si  hay algo allí, inserto con f_x y %_x
                                              //el siguiente select será temporal y me ayuda a comparar las f_x y la f_e, si hay .next f_1 menor que f_2
                                              PreparedStatement t_st8i = v_conexion_taxb.prepareStatement(select8i_fecha1);
                                              t_st8i.setString(1,dia_fecha);
                                              t_st8i.setString(2,f_e);
                                              ResultSet t_rs8i         = t_st8i.executeQuery();
                                              while(t_rs8i.next()){fecha_xcorrecta = true;}
                                              t_rs8i.close();
                                              t_st8i.close();

                                              if(fecha_xcorrecta)
                                              {
                                                 PreparedStatement t_st8i_1  = v_conexion_taxb.prepareStatement(select8i_fecha2);
                                                 t_st8i_1.setString(1,dia_fecha);
                                                 ResultSet t_rs8i_1          = t_st8i_1.executeQuery();
                                                 while(t_rs8i_1.next()){hoy_estabien = true;}
                                                 t_rs8i_1.close();
                                                 t_st8i_1.close();
                                              }

                                              if(hoy_estabien)
                                              {
                                                 //Calculo el valor disponible
                                                 CallableStatement t_cst8i_6 = v_conexion_taxb.prepareCall("{ call TBPBD_Capital_Tbaportes_D(?,?,?,?,?) }");
                                                 t_cst8i_6.registerOutParameter(4,Types.NUMERIC);
                                                 t_cst8i_6.registerOutParameter(5,Types.NUMERIC);
                                                 t_cst8i_6.setString(1,f_producto);
                                                 t_cst8i_6.setString(2,f_contrato);
                                                 t_cst8i_6.setDouble(3,consecutivo);
                                                 t_cst8i_6.execute();
                                                 capital          = t_cst8i_6.getDouble(4);
                                                 rtos             = t_cst8i_6.getDouble(5);
                                                 t_cst8i_6.close();
                                                 //Reviso el detalle original con el nuevo detalle
                                                 if(!detalle1.equals(detalle2))
                                                 {
                                                    //debo actualizar el aporte con detalle2
                                                    CallableStatement t_cst8i_17 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_Detalle_D(?,?,?,?) }");
                                                    t_cst8i_17.setString(1,f_producto);
                                                    t_cst8i_17.setString(2,f_contrato);
                                                    t_cst8i_17.setDouble(3,consecutivo);
                                                    t_cst8i_17.setString(4,detalle1);
                                                    t_cst8i_17.execute();
                                                    t_cst8i_17.close();
                                                                                                     
                                                 }
                                                 //En cualquier caso Borro y calculo Sumatoria
                                                 CallableStatement t_cst8i_10 = v_conexion_taxb.prepareCall("{ call TBPBD_Elimina_D_D(?,?,?,?,?) }");
                                                 t_cst8i_10.setString(1,f_producto);
                                                 t_cst8i_10.setString(2,f_contrato);
                                                 t_cst8i_10.setDouble(3,consecutivo);
                                                 t_cst8i_10.setString(4,dia_fecha);
                                                 t_cst8i_10.setDouble(5,porcentaje);
                                                 t_cst8i_10.execute();
                                                 t_cst8i_10.close();

                                                 //calculo sumatoria
                                                 CallableStatement t_cst8i_27 = v_conexion_taxb.prepareCall("{ call TBPBD_Sumatoria_Valores_D(?,?,?,?,?) }");
                                                 t_cst8i_27.registerOutParameter(4,Types.NUMERIC);
                                                 t_cst8i_27.registerOutParameter(5,Types.VARCHAR);
                                                 t_cst8i_27.setString(1,f_producto);
                                                 t_cst8i_27.setString(2,f_contrato);
                                                 t_cst8i_27.setDouble(3,consecutivo);
                                                 t_cst8i_27.execute();
                                                 sumatoria_valores  = t_cst8i_27.getDouble(4);
                                                 next_valido        = t_cst8i_27.getString(5);
                                                 t_cst8i_27.close();

                                                 //calculo sumatoria
                                                 valor_disponible = (porcentaje*(capital+rtos))/100;
                                                 valor            = valor_disponible-sumatoria_valores;

                                                 //valido grupoid y refcodigo
                                                 if(g_id.equals("null"))
                                                      g_idnull = true;
                                                 if(ref_codigo_o.equals("null"))
                                                     ref_codigonull = true;
                                                 if(g_idnull&&ref_codigonull)
                                                 {
                                                    //actualizo la condicion



                                                    CallableStatement t_cst8i_127 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_Condicion_D(?,?,?,?) }");
                                                    t_cst8i_127.setString(1,f_producto);
                                                    t_cst8i_127.setString(2,f_contrato);
                                                    t_cst8i_127.setDouble(3,consecutivo);
                                                    t_cst8i_127.setString(4,ref_codigo);
                                                    t_cst8i_127.execute();
                                                    t_cst8i_127.close();


                                                    //inserto disponibilidad con fecha_X y Porcentaje_X
                                                    //2001-03-01 EL %_X será el que traiga la disponibilidad entonces portmp1 = ref_valor_de_disponibilidad_elegido
                                                    portmp1          = ref_valor;
                                                    Double portmp3   = new Double(portmp1);
                                                    porcentaje       = portmp3.doubleValue();
                                                    valor_disponible = (porcentaje*(capital+rtos))/100;
                                                    valor            = valor_disponible-sumatoria_valores;
                                                    CallableStatement t_cst8i_9 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Disponibilidad_D(?,?,?,?,?,?,?) }");
                                                    t_cst8i_9.registerOutParameter(7,Types.VARCHAR);
                                                    t_cst8i_9.setString(1,f_producto);
                                                    t_cst8i_9.setString(2,f_contrato);
                                                    t_cst8i_9.setDouble(3,consecutivo);
                                                    t_cst8i_9.setString(4,dia_fecha);
                                                    t_cst8i_9.setString(5,portmp1);
                                                    t_cst8i_9.setDouble(6,valor);
                                                    t_cst8i_9.execute();
                                                    estado = t_cst8i_9.getString(7);
                                                    t_cst8i_9.close();


                                                    if(!estado.equals("BIEN"))
                                                    {
                                                        salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                                       " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, "+estado,false));
                                                        salida.println("<BR><BR>");
                                                        salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                                                        salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                                                        salida.close();
                                                    }
                                                    msj = "LA ACTUALIZACION FUE EXITOSA";
                                                    botones  = "<center><input type='button' value='Aceptar' Onclick=history.go(-3);>&nbsp;<input type='button' value='Regresar' Onclick=history.go(-2);></center>";
                                                 }//si grupo y codigo NULL
                                                 else
                                                 {
                                                    //inserto disponibilidad con fecha_E y Porcentaje_X
                                                    if(next_valido.equals("v"))
                                                    {

                                                       CallableStatement t_cst8i_9 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Disponibilidad_D(?,?,?,?,?,?,?) }");
                                                       t_cst8i_9.registerOutParameter(7,Types.VARCHAR);
                                                       t_cst8i_9.setString(1,f_producto);
                                                       t_cst8i_9.setString(2,f_contrato);
                                                       t_cst8i_9.setDouble(3,consecutivo);
                                                       t_cst8i_9.setString(4,dia_fecha);
                                                       t_cst8i_9.setString(5,portmp1);
                                                       t_cst8i_9.setDouble(6,valor);
                                                       t_cst8i_9.execute();
                                                       estado = t_cst8i_9.getString(7);
                                                       t_cst8i_9.close();

                                                       if(!estado.equals("BIEN"))
                                                       {
                                                           salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                                          " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, "+estado,false));
                                                           salida.println("<BR><BR>");
                                                           salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                                                           salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                                                           salida.close();
                                                       }
                                                       msj = "LA ACTUALIZACION FUE EXITOSA";
                                                       botones  = "<center><input type='button' value='Aceptar' Onclick=history.go(-3);>&nbsp;<input type='button' value='Regresar' Onclick=history.go(-2);></center>";
                                                    }//si valor_sumatoria igual a cero
                                                    //inserto disponibilidad con fecha_X y Porcentaje_X
                                                    else if(next_valido.equals("f"))
                                                         {

                                                            CallableStatement t_cst8i_9 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Disponibilidad_D(?,?,?,?,?,?,?) }");
                                                            t_cst8i_9.registerOutParameter(7,Types.VARCHAR);
                                                            t_cst8i_9.setString(1,f_producto);
                                                            t_cst8i_9.setString(2,f_contrato);
                                                            t_cst8i_9.setDouble(3,consecutivo);
                                                            t_cst8i_9.setString(4,f_e);
                                                            t_cst8i_9.setString(5,portmp1);
                                                            t_cst8i_9.setDouble(6,valor);
                                                            t_cst8i_9.execute();
                                                            estado = t_cst8i_9.getString(7);
                                                            t_cst8i_9.close();

                                                            if(!estado.equals("BIEN"))
                                                            {
                                                               salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                                              " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, "+estado,false));
                                                               salida.println("<BR><BR>");
                                                               salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                                                               salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                                                               salida.close();
                                                            }
                                                            msj = "LA ACTUALIZACION FUE EXITOSA";
                                                            botones  = "<center><input type='button' value='Aceptar' Onclick=history.go(-3);>&nbsp;<input type='button' value='Regresar' Onclick=history.go(-2);></center>";
                                                         }//si valor de la sumatoria diferente de cero
                                                 }//si grupo y codigo no NULL
                                                 v_conexion_taxb.commit();

                                              }//si hoy_estabien
                                              else
                                              {
                                                  botones  = "<center><input type='button' value='Aceptar'Onclick=history.go(-3);><input type='button' value='Regresar' Onclick=history.go(-2);></center>";
                                                  if(!hoy_estabien)
                                                      msj = "ERROR EN LA ACTUALIZACION, LA FECHA INGRESADA ES MAYOR A LA FECHA DEL DIA DE HOY";
                                                  if(!fecha_xcorrecta)
                                                  msj = "ERROR EN LA ACTUALIZACION, LA FECHA INGRESADA ES MENOR A LA FECHA EFECTIVA DEL APORTE";

                                               }//si fecha no Correcta
                                               salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                               "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_DISPONIBILIDAD",msj,false));
                                               salida.println("<BR><BR>");
                                               salida.println(botones);
                                               salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                                               salida.flush();
                                               return;
                                            }
                                   }//try
                                   catch(Exception ex)
                                   {
                                       String msj = "SE PRODUJO UN ERROR INESPERADO, "+ex.toString() +" INTENTE DE NUEVO.";
                                       salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                      "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_DISPONIBILIDAD","<CENTER>"+msj+"</CEBTER>",false));
                                       salida.println("<BR><BR>");
                                       salida.println("<center><input type='button' value='Aceptar' Onclick=window.location='/taxbenefit/actualizar_aportes.html';></center>");
                                       salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
                                       salida.flush();
                                   }//catch
                                }//is Z Y Z
       }//del try
       catch(Exception ex)
       {
          String msj = "SE PRODUJO UN ERROR INESPERADO, "+ex.toString() +" INTENTE DE NUEVO.";
          salida.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                               "","<CENTER>"+msj+"</CEBTER>",false));
          salida.println("<BR><BR>");
          salida.println("<center><input type='button' value='Aceptar' Onclick=history.go("+v_historia+");></center>");
          salida.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
          salida.flush();
      }
       finally{
          try{
                  DataSourceWrapper.closeConnection(v_conexion_taxb);                  
          }catch(SQLException sqlEx){
                  System.out.println(sqlEx.toString());
          }
      }      
   }//del doget

   //metodo para el manejo de aportes
   public String TBCL_MANEJAR_APORTES(String dia_fecha, String f_e,
                                      String f_producto  , String f_contrato ,
                                      double consecutivo  ,double porcentaje,
                                      String  portmp1,
                                      String  select8i_2 , String  select8i_3,
                                      Connection v_conexion_taxb,PrintWriter salida)
   {
       //STBCL_GenerarBaseHTMLII plantilla = new STBCL_GenerarBaseHTMLII;
      boolean fecha_xcorrecta = false;
      boolean hoy_estabien = false;
      double  capital          = 0;
      double  rtos             = 0;
      double  sumatoria_valores  = 0;
      String  next_valido        = "";
      double  valor_disponible = 0;
      double  valor            = 0;
      String msj ="";
      String estado = "";

      try
      {
         PreparedStatement t_st8i = v_conexion_taxb.prepareStatement(select8i_2);
         t_st8i.setString(1,dia_fecha);
         t_st8i.setString(2,f_e);
         ResultSet t_rs8i         = t_st8i.executeQuery();
         while(t_rs8i.next())
         {
            fecha_xcorrecta = true;
         }
         t_rs8i.close();
         t_st8i.close();
         if(fecha_xcorrecta)
         {
            PreparedStatement t_st8i_1  = v_conexion_taxb.prepareStatement(select8i_3);
            t_st8i_1.setString(1,dia_fecha);
            ResultSet t_rs8i_1          = t_st8i_1.executeQuery();
            while(t_rs8i_1.next())
            {
               hoy_estabien = true;
            }
            t_rs8i_1.close();
            t_st8i_1.close();
         }
         if(hoy_estabien)
         {
            //Calculo el valor disponible
            CallableStatement t_cst8i_6 = v_conexion_taxb.prepareCall("{ call TBPBD_Capital_Tbaportes_D(?,?,?,?,?) }");
            t_cst8i_6.registerOutParameter(4,Types.NUMERIC);
            t_cst8i_6.registerOutParameter(5,Types.NUMERIC);
            t_cst8i_6.setString(1,f_producto);
            t_cst8i_6.setString(2,f_contrato);
            t_cst8i_6.setDouble(3,consecutivo);
            t_cst8i_6.execute();
            capital          = t_cst8i_6.getDouble(4);
            rtos             = t_cst8i_6.getDouble(5);
            t_cst8i_6.close();
            //esto no se hace por que esto es solo para skandia
            //Reviso el detalle original con el nuevo detalle
            /*if(!detalle1.equals(detalle2))
            {
               //debo actualizar el aporte con detalle2
               CallableStatement t_cst8i_17 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_Detalle_D(?,?,?,?) }");
               t_cst8i_17.setString(1,f_producto);
               t_cst8i_17.setString(2,f_contrato);
               t_cst8i_17.setInt(3,consecutivo);
               t_cst8i_17.setString(4,detalle1);
               t_cst8i_17.execute();
               t_cst8i_17.close();
            }*/
            //En cualquier caso Borro y calculo Sumatoria
            CallableStatement t_cst8i_10 = v_conexion_taxb.prepareCall("{ call TBPBD_Elimina_D_D(?,?,?,?,?) }");
            t_cst8i_10.setString(1,f_producto);
            t_cst8i_10.setString(2,f_contrato);
            t_cst8i_10.setDouble(3,consecutivo);
            t_cst8i_10.setString(4,dia_fecha);
            t_cst8i_10.setDouble(5,porcentaje);
            t_cst8i_10.execute();
            t_cst8i_10.close();
            //calculo sumatoria
            CallableStatement t_cst8i_27 = v_conexion_taxb.prepareCall("{ call TBPBD_Sumatoria_Valores_D(?,?,?,?,?) }");
            t_cst8i_27.registerOutParameter(4,Types.NUMERIC);
            t_cst8i_27.registerOutParameter(5,Types.VARCHAR);
            t_cst8i_27.setString(1,f_producto);
            t_cst8i_27.setString(2,f_contrato);
            t_cst8i_27.setDouble(3,consecutivo);
            t_cst8i_27.execute();
            sumatoria_valores  = t_cst8i_27.getDouble(4);
            next_valido        = t_cst8i_27.getString(5);
            t_cst8i_27.close();

            valor_disponible = (porcentaje*(capital+rtos))/100;
            valor            = valor_disponible-sumatoria_valores;
            //esto no se valida por que simpre hay empresa
            //valido grupoid y refcodigo
            /*if(g_id.equals("null"))
                g_idnull = true;
            if(ref_codigo_o.equals("null"))
                ref_codigonull = true;
            /*if(g_idnull&&ref_codigonull)
            {
              //actualizo la condicion
              CallableStatement t_cst8i_127 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_Condicion_D(?,?,?,?) }");
              t_cst8i_127.setString(1,f_producto);
              t_cst8i_127.setString(2,f_contrato);
              t_cst8i_127.setInt(3,consecutivo);
              t_cst8i_127.setString(4,ref_codigo);
              t_cst8i_127.execute();
              t_cst8i_127.close();
              //inserto disponibilidad con fecha_X y Porcentaje_X
              //2001-03-01 EL %_X será el que traiga la disponibilidad entonces portmp1 = ref_valor_de_disponibilidad_elegido
              portmp1          = ref_valor;
              Double portmp3   = new Double(portmp1);
              porcentaje       = portmp3.doubleValue();
              valor_disponible = (porcentaje*(capital+rtos))/100;
              valor            = valor_disponible-sumatoria_valores;
              CallableStatement t_cst8i_9 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Disponibilidad_D(?,?,?,?,?,?,?) }");
              t_cst8i_9.registerOutParameter(7,Types.VARCHAR);
              t_cst8i_9.setString(1,f_producto);
              t_cst8i_9.setString(2,f_contrato);
              t_cst8i_9.setInt(3,consecutivo);
              t_cst8i_9.setString(4,dia_fecha);
              t_cst8i_9.setString(5,portmp1);
              t_cst8i_9.setDouble(6,valor);
              t_cst8i_9.execute();
              estado = t_cst8i_9.getString(7);
              t_cst8i_9.close();
              if(!estado.equals("BIEN"))
              {
                 salida.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                               " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, "+estado,false));
                salida.println("<BR><BR>");
                salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                 salida.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
                salida.close();
              }
              msj = "LA ACTUALIZACION FUE EXITOSA";
              botones  = "<center><input type='button' value='Aceptar' Onclick=history.go(-3);>&nbsp;<input type='button' value='Regresar' Onclick=history.go(-2);></center>";
            }//si grupo y codigo NULL
            else
            {*/
               //inserto disponibilidad con fecha_E y Porcentaje_X
            if(next_valido.equals("v"))
            {
               CallableStatement t_cst8i_9 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Disponibilidad_D(?,?,?,?,?,?,?) }");
               t_cst8i_9.registerOutParameter(7,Types.VARCHAR);
               t_cst8i_9.setString(1,f_producto);
               t_cst8i_9.setString(2,f_contrato);
               t_cst8i_9.setDouble(3,consecutivo);
               t_cst8i_9.setString(4,dia_fecha);
               t_cst8i_9.setString(5,portmp1);
               t_cst8i_9.setDouble(6,valor);
               t_cst8i_9.execute();
               estado = t_cst8i_9.getString(7);
               t_cst8i_9.close();
               if(!estado.equals("BIEN"))
               {
                 v_conexion_taxb.rollback();
                 v_conexion_taxb.close();
                 salida.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, "+estado,false));
                 salida.println("<BR><BR>");
                 salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                 salida.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
                 salida.close();
               }
               msj = "LA ACTUALIZACION FUE EXITOSA";

            }//si valor_sumatoria igual a cero
            //inserto disponibilidad con fecha_X y Porcentaje_X
            else if(next_valido.equals("f"))
                 {
                    CallableStatement t_cst8i_9 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Disponibilidad_D(?,?,?,?,?,?,?) }");
                    t_cst8i_9.registerOutParameter(7,Types.VARCHAR);
                    t_cst8i_9.setString(1,f_producto);
                    t_cst8i_9.setString(2,f_contrato);
                    t_cst8i_9.setDouble(3,consecutivo);
                    t_cst8i_9.setString(4,f_e);
                    t_cst8i_9.setString(5,portmp1);
                    t_cst8i_9.setDouble(6,valor);
                    t_cst8i_9.execute();
                    estado = t_cst8i_9.getString(7);
                    t_cst8i_9.close();
                    if(!estado.equals("BIEN"))
                    {
                       salida.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                       " ","SE HA PRODUCIDO UN ERROR CON LA BASE DE DATOS, POR FAVOR INTENTELO DE NUEVO, "+estado,false));
                       salida.println("<BR><BR>");
                       salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
                       salida.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
                       salida.close();
                    }
                    msj = "LA ACTUALIZACION FUE EXITOSA";

                 }//si valor de la sumatoria diferente de cero
             //  }//si grupo y codigo no NULL
             //v_conexion_taxb.commit();
         }//si hoy_estabien
         else
         {
             
             if(!hoy_estabien)
                msj = "ERROR EN LA ACTUALIZACION, LA FECHA INGRESADA ES MAYOR A LA FECHA DEL DIA DE HOY";
             if(!fecha_xcorrecta)
               msj = "ERROR EN LA ACTUALIZACION, LA FECHA INGRESADA ES MENOR A LA FECHA EFECTIVA DEL APORTE";
          }//si fecha no Correcta
       }
       catch(Exception ex)
       {

          try
          {
             v_conexion_taxb.rollback();
             v_conexion_taxb.close();
          }
          catch(Exception ex2)
          {}

          msj = "SE PRODUJO UN ERROR INESPERADO, "+ex.toString() +" INTENTE DE NUEVO.";
          salida.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                         "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_DISPONIBILIDAD","<CENTER>"+msj+"</CEBTER>",false));
          salida.println("<BR><BR>");
          salida.println("<center><input type='button' value='Aceptar' Onclick=window.location='/taxbenefit/actualizar_aportes.html';></center>");
          salida.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
          salida.flush();
       }//catch

       return msj;
    }
}



