
package TBPKT_MODULO_APORTES;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import javax.servlet.http.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import javax.servlet.*;
import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.*;
import java.sql.*;
import java.io.*;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;

import co.oldmutual.taxbenefit.util.DataSourceWrapper; // INT20131108

/**
*Este servlet servirá para actualizar aportes en su calidad de exentos, especificando una
*razón valida
*/

public class TBS_ACTUALIZA_BENEFICIO extends HttpServlet
{
  int v_historia = -1;
  //---------------------------------------------------------------------------------------------------------------
  public void init(ServletConfig config) throws ServletException
    {super.init(config);}
  //----------------------------------------------------------------------------------------------------------------
  public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
                   throws ServletException, IOException
 {
     //INT20131108
     String rutaFisica = TBCL_REFERENCIAS.TBCL_CONSULTA("SMM003");
     //rutaFisica  =  "c:\\Taxbenefits\\Taxbenefits\\public_html\\WEB-INF\\";
     String POLICY_FILE_LOCATION = rutaFisica+"antisamy-tinymce-1.4.4.xml"; // Path to policy file
     AntiSamy as = new AntiSamy(); // INT20131108
 PrintWriter salida = new PrintWriter (respuesta.getOutputStream());
     Connection v_conexion_taxb    =   null;
 try
 {

  //seguridad
  HttpSession session = peticion.getSession(true);
  if (session == null) session = peticion.getSession(true);
  respuesta.setContentType("text/html");
  /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
  //respuesta.setHeader("Pragma", "No-Cache");
  //respuesta.setDateHeader("Expires", 0);
//  respuesta.setHeader("Cache-Control","no-Cache");
  String v_contrato = "", v_producto = "", v_usuario  = "", v_unidad = "";
  String v_tipousu  = "", v_idworker = "";
  String parametros[]      = new String[8];
  String  cadena2          = peticion.getParameter("cadena");
  String  nuevaCadena      = cadena2;
  String ip_tax            = peticion.getRemoteAddr();
   
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
  //valido la veracidad del producto y contrato enviados por pipeline
  parametros = TBCL_Seguridad.TBFL_Seguridad(cadena2, salida, ip_tax);
  v_contrato = parametros[0];
  v_producto = parametros[1];
  v_usuario  = parametros[2];
  v_unidad   = parametros[3];
  v_tipousu  = parametros[4];
  v_idworker = parametros[5];
  
    v_conexion_taxb =   DataSourceWrapper.getInstance().getConnection();
  /*[SO_396] Se realiza modificación de llamado por ser método estático ****** de la clase TBCL_HTML, no es necesaria la instancia nueva*/
  /*TBCL_HTML   hoja_base                 = new TBCL_HTML();
  TBCL_HTML   hoja_error                = new TBCL_HTML();
  TBCL_HTML   parametros_requeridos     = new TBCL_HTML();
  TBCL_HTML   nombres                   = new TBCL_HTML();
  TBCL_HTML   publica                   = new TBCL_HTML();*/
  //valido la veracidad del producto y contrato enviados por pipeline
  String  nombre_producto               = peticion.getParameter("nom_producto");
  String  numero_contrato               = peticion.getParameter("num_contrato");
  String  usuario                       = peticion.getParameter("usuario");
   if(nombre_producto != null)
   {
     v_producto = "x";
     v_contrato = "x";
   }
  //STBCL_GenerarBaseHTMLII plantilla = new STBCL_GenerarBaseHTMLII;
  //------------------------------------------------------------------pagina numero 1
 //si los parametros son validos, dibujo hojabase y devuelvo parametros x y x
 if(TBCL_HTML.TBPL_Parametros_Requeridos(v_producto,v_contrato))
  {
  v_historia = -1;
 TBCL_HTML.TBPL_Hoja_Base("ACTUALIZA EXENTOS",v_producto,v_contrato,salida,1,
                          "TBS_ACTUALIZA_BENEFICIO",v_conexion_taxb,v_usuario,
                          TBCL_HTML.TBPL_Nombres(v_producto,v_contrato),nuevaCadena);
return;
  }
  else if ((!TBCL_HTML.TBPL_Parametros_Requeridos(nombre_producto,numero_contrato))&&
         ( nombre_producto == null)&&
         ( numero_contrato == null))
       {
         //STBCL_GenerarBaseHTML plantilla2 = new STBCL_GenerarBaseHTML();
         String msj = "EL CONTRATO "+v_contrato+" NO EXISTE EN EL SISTEMA.";
         salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE EXENCION DE APORTES",
                                         "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_BENEFICIO","<font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><CENTER>"+msj+"</CEBTER></font></b>",false));
         salida.println("<BR><BR>");
         salida.println("<center><input type='button' value='Aceptar' Onclick='history.go(-1);' ></center>");
         salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
         salida.flush();
       return;
        }
  //------------------------------------------------------------------pagina numero 2
 //si los parametros son invalidos y diferentes de x hay error
else if
 ((!TBCL_HTML.TBPL_Parametros_Requeridos(nombre_producto,numero_contrato))&&
 (!nombre_producto.equals("x"))&&
 (!numero_contrato.equals("x"))&&
 (!nombre_producto.equals("y"))&&
 (!numero_contrato.equals("y"))){
 v_historia = -2;
 TBCL_HTML.TBPL_Hoja_Error(nombre_producto,numero_contrato,salida,0,"TBS_ACTUALIZA_BENEFICIO","ACTUALIZA EXENTOS");
  return;
                                }
//valido valor de productos para generar pagina3
else if((nombre_producto.equals("x"))&&(numero_contrato.equals("x")))
  {
   double  v_valorUnidad = 0;
   v_historia = -2;
   String  f_producto    = peticion.getParameter("f_producto");
   String  f_contrato    = peticion.getParameter("f_contrato");
   String  fecha_1       = peticion.getParameter("fecha_1");
   String  fecha_2       = peticion.getParameter("fecha_2");
   String  f_user        = peticion.getParameter("usuario");
   //defino vectores contenedores de datos a dibujar en la pagina de rta numero 3 por contrato
   String v_fechas[]   = new String[500];     //vector de fechas efectiva por contrato
   double v_saldoc[]   = new double[500];      //vector de saldos de capital por contrato
   double v_saldocc[]  = new double[500];     //vector de saldos de cuenta contingente por contrato
   double v_numerou[]  = new double[500];  //vector de numero de unidades por contrato
   double v_rtos[]     = new double[500]; //vector de saldos de rendimientos por contrato
   double v_conses[]      = new double[500];   //vector que mantendrá activos los consecutivos para pasarlo al doget
   int indice          = 0;
   String select8i_1   = "SELECT "+
                      "TO_CHAR(APO_FECHA_EFECTIVA,'RRRR-MM-DD'),"+
                      "APO_SALDO_CAPITAL,"+
                      "APO_SALDO_CUENTA_CONTINGENTE,"+
                      "APO_CONSECUTIVO,"+
                      "APO_SALDO_NUMERO_UNIDADES "+
                      "FROM TBAPORTES "+
                      "WHERE "+
                      "APO_CON_PRO_CODIGO = ? "+
                      "AND APO_CON_NUMERO = ? "+
                      "AND APO_FECHA_EFECTIVA BETWEEN TO_DATE(?,'RRRR-MM-DD') AND TO_DATE(?,'RRRR-MM-DD') " +
                      "AND APO_SALDO_NUMERO_UNIDADES <> 0 "+
                      "AND APO_APORTE_CERTIFICADO   = 'S' "+
                      "AND APO_APORTE_BENEFICIO     = 'N' "+
                      "AND APO_REF_ESTADO     <> 'SEA002' "+
                      "AND (APO_APORTE_TRASLADO     = 'N' OR (APO_APORTE_TRASLADO = 'S' AND APO_INFORMACION_TRASLADO = 'N' )) "+
                      "AND (APO_APO_CONSECUTIVO IS NULL OR (APO_APO_CONSECUTIVO IS NOT NULL AND APO_INFORMACION_TRASLADO = 'N')) "+
                      "ORDER BY APO_FECHA_EFECTIVA";
//--
   double matUnidad[] = new double[3];
   try
       {
         /*
         vector[0] valor_unidad
         vector[1] saldo contrato
         vector[2] codigo_error (sii cod_error == 0 tomar datos sino error intente despuesito)
         */
         //obtengo fecha de contrato y de unidad(SYSDATE-1)
         String v_fecha_valuni              = new String("");
         String select_2                    = "SELECT TO_CHAR(SYSDATE-1,'RRRRMMDD') FROM DUAL";
         PreparedStatement t_st8i_2         = v_conexion_taxb.prepareStatement(select_2);
         ResultSet t_rs8i_2                 = t_st8i_2.executeQuery();
         while(t_rs8i_2.next())
           v_fecha_valuni                   = t_rs8i_2.getString(1);
        t_rs8i_2.close();
        t_st8i_2.close();
         //calculo el valor de la unidad
         /*[SO_396] Se realiza modificación de llamado por ser método estático TBF_CALCULO_VALOR_UNIDAD de la clase SQL_VALOR_UNIDAD_CC, no es necesaria la instancia nueva*/
         //SQL_VALOR_UNIDAD_CC i_valUnid = new SQL_VALOR_UNIDAD_CC();
         matUnidad                          = SQL_VALOR_UNIDAD_CC.TBF_CALCULO_VALOR_UNIDAD(v_fecha_valuni,v_fecha_valuni,f_contrato,f_producto,false,0);
         v_valorUnidad                      = matUnidad[0];
         if(matUnidad[2]!=0.0)
           {
           String msj = new String("");
           if(matUnidad[2]==-1.0) msj = "ERROR menos 1";
           if(matUnidad[2]==-2.0) msj = "ERROR menos 2";
           if(matUnidad[2]==-3.0) msj = "ERROR menos 3";
           if(matUnidad[2]==-4.0) msj = "ERROR menos 4";
           if(matUnidad[2]==-5.0) msj = "ERROR menos 5";
           //STBCL_GenerarBaseHTML plantilla2 = new STBCL_GenerarBaseHTML();
           salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZA EXENTOS",
                                          " ","SE PRODUJO UN ERROR EN EL CALCULO DEL VALOR DE LA UNIDAD, "+msj,false));
           salida.println("<BR><BR>");
           salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
           salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
           salida.close();
         }
       }//try
      catch(Exception ex)
       {
           String msj = new String("");
           if(matUnidad[2]==-1.0) msj = "ERROR menos 1";
           if(matUnidad[2]==-2.0) msj = "ERROR menos 2";
           if(matUnidad[2]==-3.0) msj = "ERROR menos 3";
           if(matUnidad[2]==-4.0) msj = "ERROR menos 4";
           if(matUnidad[2]==-5.0) msj = "ERROR menos 5";
           /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase STBCL_GenerarBaseHTML, no es necesaria la instancia nueva*/  
 //STBCL_GenerarBaseHTML plantilla1 = new STBCL_GenerarBaseHTML;
           salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZA EXENTOS",
                                          " ","SE PRODUJO UN ERROR EN EL CALCULO DEL VALOR DE LA UNIDAD, "+msj,false));
           salida.println("<BR><BR>");
           salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
           salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
           salida.close();
       }
//--
   PreparedStatement t_st8i = v_conexion_taxb.prepareStatement(select8i_1);
   ResultSet t_rs8i;
   t_st8i.setString(3,fecha_1);
   t_st8i.setString(4,fecha_2);
   t_st8i.setString(1,f_producto);
   t_st8i.setString(2,f_contrato);
   t_rs8i = t_st8i.executeQuery();
   int i=0;
   while(t_rs8i.next())
   {
      //capturo saldo capital para hallar el saldo de rtos
      v_saldoc[indice]  = t_rs8i.getDouble(2);
      v_numerou[indice] = t_rs8i.getDouble(5);
      //capturo resto de información
      v_fechas[indice]  = t_rs8i.getString(1);
      v_saldocc[indice] = t_rs8i.getDouble(3);
      v_conses[indice]  = t_rs8i.getDouble(4);
      //calculo saldo de rtos
      double rtos = (v_numerou[indice]*v_valorUnidad)-v_saldoc[indice];
      v_rtos[indice]    = rtos;
      indice++;
   }
      t_rs8i.close();
      t_st8i.close();
      
   TBCL_HTML.TBPL_Publica_E(indice,v_fechas,v_saldoc,v_rtos,v_saldocc,v_conses,f_user,f_producto,f_contrato,salida,nuevaCadena);
   return;
  }
//------------------------------------------------------------------------------------------------------------------------
else if((nombre_producto.equals("y"))&&(numero_contrato.equals("y")))
  {
    v_historia = -3;
 /*  String url = "Onclick=history.go('http://afscotb1.skandia.com.co/taxbenefit/ACTUALIZAR_APORTES.HTML');"+
                 "history.go('http://afscotb1.skandia.com.co/taxbenefit/actualizar_aportes.html');";*/

   String  f_user           = peticion.getParameter("usuario");
   String razon             = peticion.getParameter("obligatorio_razon");
      try
      {
          CleanResults cr = as.scan(peticion.getParameter("obligatorio_razon"), new File(POLICY_FILE_LOCATION));
          razon = cr.getCleanHTML();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      } //INT20131108
   //concateno razon con userid
   razon                   += " USUARIO : ";
   razon                   += f_user;
   String producto          = peticion.getParameter("f_producto");
   String contrato          = peticion.getParameter("f_contrato");
   String  t                = peticion.getParameter("total_aportes");
   int total_aportes        = peticion.getParameterValues("checks").length;
   String elegidas[]        = new String[total_aportes+1];
   String  consecutivos[]   = new String[total_aportes+1];
   //capturo los consecutivos a cargar y junto con  contrato producto realizaré el update correspondiente
   for(int i=0;i<peticion.getParameterValues("checks").length;i++)
   {
     //elegidas[i]       = peticion.getParameterValues("checks")[i];
     consecutivos[i]   = peticion.getParameterValues("checks")[i];
     //consecutivos[i]   = peticion.getParameter("consecutivo"+elegidas[i]);
     Double inte      = new Double(consecutivos[i]);
     double consecutivo   = inte.doubleValue();
     CallableStatement t_cst8i_1 = v_conexion_taxb.prepareCall("{ call TBPBD_Actualiza_Beneficio(?,?,?,?) }");
     //configuro variables de entrada sobre el proc
     t_cst8i_1.setString(1,producto);
     t_cst8i_1.setString(2,contrato);
     t_cst8i_1.setDouble(3,consecutivo);
     t_cst8i_1.setString(4,razon);
     //ejecuto procedimiento
     t_cst8i_1.execute();
        t_cst8i_1.close();
    }
   /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase STBCL_GenerarBaseHTML, no es necesaria la instancia nueva*/  
 //STBCL_GenerarBaseHTML plantilla1 = new STBCL_GenerarBaseHTML;
   salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZA EXENTOS",
                                                "","ACTUALIZACION DE BENEFICIO DE APORTES REALIZADA",false));
   salida.println("<BR><BR>");
   salida.println("<center><input type='button' value='Aceptar' Onclick = 'history.go(-2);'></center>");
   salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
   salida.flush();
   v_conexion_taxb.commit();
   return;
  }
 }
 catch(Exception ex)
   {
    //STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML;
    String msj = "SE PRODUJO UN ERROR INESPERADO, "+ex.toString() +" INTENTE DE NUEVO.";
    salida.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE EXENCION DE APORTES",
                                         "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_BENEFICIO","<CENTER>"+msj+"</CEBTER>",false));
    salida.println("<BR><BR>");
    salida.println("<center><input type='button' value='Aceptar' Onclick=history.go("+v_historia+");></center>");
    salida.println(STBCL_GenerarBaseHTML.TBFL_PIE);
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
//------------------------------------------------------------------------------------------------------------------
  public String getServletInfo()
   {
    return "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_BENEFICIO Information";
   }
//--------------------------------------------------------------------------------------------------------------------
}
