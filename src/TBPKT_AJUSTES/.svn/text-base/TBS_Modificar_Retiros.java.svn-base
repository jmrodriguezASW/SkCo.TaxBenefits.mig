package TBPKT_AJUSTES;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;

import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import javax.servlet.http.*;
import javax.servlet.*;

//import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.*;

import java.text.*;
import java.sql.*;
import java.io.*;

import java.util.Vector;

import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108



/**
 *<font face='Verdana' size='2' color='#324395'>
 * El submódulo de Modificación de Retiros tiene como finalidad:
 * ·	Modificar el estado del retiro a 'Anulado'.
 * ·	Reversar los valores a los aportes afectados.
 * ·	Generar un nuevo Retiro (llamado 'Retiro Prima') con Estado 'Vigente'.
 * ·	Registrar el Ajuste entre el Retiro Original y el Retiro Prima.
 * ·	Posibilidad de tomar la decisión de una vez frente al ajuste generado.
 * <p>
 * @param    cadena  = CADENA ENCRIPTADA CON LA INFORMACION DEL APORTE <BR>
 * @return   ninguno
 */

public class TBS_Modificar_Retiros extends HttpServlet implements SingleThreadModel
{
  //DEFINICIONES INICIALES
  TBCL_LoadPage i_LP;
  STBCL_GenerarBaseHTML codHtm;
  STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
  boolean pintar_decision= false;
  String msje_final      = new String("");
  String fecharetiro     = new String("");
  String fechahoy        = new String("");
  TBCL_FndCmp i_fnd      = new TBCL_FndCmp();
  TBCL_LoadPage i_01     = new TBCL_LoadPage();
  String cod_producto    = new String("");
  String k_font          = new String("<font face='Verdana' size='1'>");
  int consecAjus         = 0;
  String num_contrato    = new String("");
  String usuario         = new String("");
  String valores         = new String("");
  String v_cons          = new String("");
  String v_fecha         = new String("");//fecha efectiva que trae como parametro
  java.sql.Date vfefec   = new java.sql.Date(4);
  String v_valor         = new String("");
  double v_valorUnidad   = 0;
  String v_nombApel      = new String("No existe");
  String cadena          = new String("");
  String v_tipo          = new String("");
  String v_estado        = new String("");
  String v_esquema       = new String("");
  String v_error         = new String("");
  String v_fecha_choose  = new String("");
  String v_fecha_choose1 = new String("");
  String v_fecha_proc    = new String();
  String v_razon         = new String("");
  String vorden          = new String("");
  String vbenef          = new String("");
  String vpenal          = new String("");
  String vcuenta         = new String("");
  String vnatural        = new String("");
  String vrespetarn      = new String("");
  String k_cabeza        = new String("");
  String str_esquema     = new String("");
  HttpServletRequest request;
  HttpServletResponse response;
  HttpSession sess;
  PrintWriter out;
  private TBCL_Consulta v_Consulta; // INT20131108
/////////////////////inicialización del servlet//////////////////////////////
public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }
/////////////////////////////llamado principal//////////////////////////////////
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
 try
 {
    //INICIO seguridad
    out                 = new PrintWriter (response.getOutputStream());
    TBCL_Seguridad Seguridad    = new TBCL_Seguridad();
    String v_contrato   = "", v_producto = "",
    v_usuario           = "", v_unidad   = "";
    String v_tipousu    = "", v_idworker = "";
    String parametros[] = new String[8];
    cadena              = request.getParameter("cadena");
    String ip_tax       = request.getRemoteAddr();
    parametros          = Seguridad.TBFL_Seguridad(cadena,out,ip_tax);
    v_contrato          = parametros[0];
    v_producto          = parametros[1];
    v_usuario           = parametros[2];
    v_unidad            = parametros[3];
    v_tipousu           = parametros[4];
    v_idworker          = parametros[5];
    //FIN seguridad
    msje_final          = " ";
    pintar_decision     = false;
    this.request        = request;
    this.response       = response;
    i_LP                = new TBCL_LoadPage();
    i_fnd               = new TBCL_FndCmp();
    response.setContentType("text/html");
    sess                = request.getSession(true);
    sess.setMaxInactiveInterval(3600);
    valores             = new String("");
    v_error             = new String("");
    k_cabeza            = codHtm.TBFL_CABEZA("Modificar Retiros","Modificar Retiros","TBPKT_AJUSTES.TBS_Modificar_Retiros",
                                             " ",true,"moduloparametro.js","return checkrequired(this)");
    //SEGUIMIENTO DEL PROCESO DE MODIFICACION DE RETIROS
    if(request.getParameter("cons")!=null)
    {
        //PROCESO PARA PRIMER PETICION SOBRE EL SERVLET
       //tomar consecutivo del retiro a modificar
       if(TBPL_getParams_Sess1())
       {
          //tomar parametros de entrada
          TBPL_BuildPage1("","1");
        }
       else
       {
         Dibujo_Error(out); //para Rparametros de entrada
       }
    }
    else
    {
      //PROCESO PARA LA SEGUNDA PETICION SOBRE EL SERVLET
      TBCL_Validacion   i_valusu = new TBCL_Validacion();
      String[] v_valusu          = new String[3];
      v_valusu                   = i_valusu.TBFL_ValidarUsuario();
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection v_conexion_taxb = DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
      v_conexion_taxb.setAutoCommit(false);
      if(TBPL_getParams_Sess2(v_conexion_taxb))
       {
         //VALIDACION DEL ESUQEMA ACTUAL CON RESPECTO AL ESQUEMA ORIGINAL
         if(TBPL_validar())
          {
            //REALIZACION DEL PROCESO DE REVERSAR EL RETIRO ORIGINAL
            boolean v_AnularModificar = false;
            if(TBPBD_AnularModificar(v_conexion_taxb)&&sess.getValue("refresh")!=null)
             {
               //REALIZACION DEL PROCESO DE GENERACION DEL RETIRO PRIMA, DEL AJUSTE Y DE LOS CARGOS AJUSTES CORRESPONDIENTES
               v_AnularModificar = true;
               if(TBPL_Aplicar(v_conexion_taxb))
                {
                  //REALIZACION DE LA INSERCIION DEL PROCESO EN TBTRANSACCION_LOGS
                  if(TBPL_TransaccionLog(v_conexion_taxb))
                   {
                     pintar_decision = true;
                     sess.removeValue("refresh");
                     v_conexion_taxb.commit();
                     v_conexion_taxb.close();
                     Dibujo_Error(out);
                   }//if(TBPL_TransaccionLog(v_conexion_taxb))
                  else
                   {
                     v_conexion_taxb.rollback();v_conexion_taxb.close();
                     sess.removeValue("refresh");
                     Dibujo_Error(out); //para transaccion logs
                   }//if(!TBPL_TransaccionLog(v_conexion_taxb))
                }//if(TBPL_Aplicar(v_conexion_taxb))
               else
                {
                  v_conexion_taxb.rollback();v_conexion_taxb.close();
                  Dibujo_Error(out); //para Retiro Prima
                }//if(!TBPL_Aplicar(v_conexion_taxb))
             }//if(TBPBD_AnularModificar(v_conexion_taxb)&&sess.getValue("refresh")!=null)
            else
             {
               v_conexion_taxb.rollback();v_conexion_taxb.close();
               if(!v_AnularModificar)
                  Dibujo_Error(out); //para Reversar
               else
               {
                out.println(plantilla.TBFL_CABEZA("Modificar Retiros","Modificar Retiros"," ","<CENTER>Su Transacción Ya ha sido Realizada</CENTER>",false));
                out.println("<BR><BR>");
                out.println("<center><input type='button' value='Aceptar' Onclick=history.go(-2);></center>");
                out.println(plantilla.TBFL_PIE);
                out.close();
               }
             }//if(!TBPBD_AnularModificar(v_conexion_taxb)&&sess.getValue("refresh")==null)
          }//if(TBPL_validar())
         else
          {
            out.println(plantilla.TBFL_CABEZA("Modificar Retiros","Modificar Retiros"," ","<left>"+msje_final+"</left>",false));
            out.println("<BR><BR>");
            out.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
            out.println(plantilla.TBFL_PIE);
            out.close();
          }//if(!TBPL_validar())
       }//if(TBPL_getParams_Sess2())
     else
       {
         v_conexion_taxb.rollback();v_conexion_taxb.close();
         Dibujo_Error(out); //para captura de parametros por segunda vez
       }//if(!TBPL_getParams_Sess2())
    }// if (request.getParameter("cons")==null)
 }//del try
 catch(Exception ex)
  {
    TBCL_LoadPage i_LP = new TBCL_LoadPage();
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
    i_LP.TBPL_PrintMsgErr(out,"Se produjo un error inesperado durante el proceso, Intente de Nuevo, "+ v_menex,true,k_cabeza,codHtm.TBFL_PIE);
  }
}


//////////////////////////////////////////////////////////////////////////////////////////////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA DESPLEGAR LA INFORMACION FINAL DEL PROCESO
* DE GENERACION DEL AJUSTE.
* <p>
* @param   salida      = OBJETO PRINTWRITER<BR>
* @return  NINGUNO
*/
private void Dibujo_Error(PrintWriter salida)
{
try
 {
   salida.println(plantilla.TBFL_CABEZA("Modificar Retiros","Modificar Retiros"," ","<left>"+msje_final+"</left>",false));
   salida.println("<BR><BR>");
   if(pintar_decision)
    {
      salida.println("</form>");
      salida.println("<body >");
      salida.println("<table border='0' width='100%'>");
			salida.println("<tr>");
			salida.println("<form action='/Servlets/TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.TBS_DecisionRetiro' method='post' id='link23' name='link23'>");
			salida.println("<input type='hidden' id='cadena' name='cadena' value='"+cadena+"'>");
			salida.println("<input type='hidden' id='only' name='only' value='"+consecAjus+"'>");
			salida.println("<td width='400'>");
			salida.println("<center>");
			salida.println("<input type='button' value='Aceptar' Onclick=history.go(-2);>");
			salida.println("<INPUT TYPE=SUBMIT VALUE='Decision'>");
			salida.println("<center>");
			salida.println("</td>");
			salida.println("</form>");
			salida.println("</tr>");
    	salida.println("</table>");
      salida.println("</body>");
      salida.println("</center>");
    }
   else
     salida.println("<center><input type='button' value='Regresar' Onclick=history.go(-1);></center>");
   salida.println(plantilla.TBFL_PIE);
   salida.close();
 }
catch(Exception ex){}
}
///////////////////////////////////construcción de la página de salida///////////////////////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA construIR la página de salida
* <p>
* @param   msgErr1  = MENSAJE A DESPLEGAR<BR>
*          num      = INDICADOR DE DIBUJP<BR>
* @return  NINGUNO
*/
private void TBPL_BuildPage1(String msgErr1,String num)
  {
  try
  {
    TBCL_Validacion   i_valusu = new TBCL_Validacion();
    String[] v_valusu          = new String[3];
    v_valusu                   = i_valusu.TBFL_ValidarUsuario();
    Class.forName("oracle.jdbc.driver.OracleDriver");
    Connection v_conexion_tb   = DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
    v_conexion_tb.setAutoCommit(false);
    DecimalFormat dec  = new DecimalFormat("###,###,###,###,###.##");
    i_LP.TBPL_Encabezado(out,k_cabeza,cod_producto,num_contrato,v_nombApel,"<tr><td align='center'><font face='Verdana' size='1'><strong>MODIFICAR RETIRO -> Transacción: </strong>"+v_cons+"&nbsp;&nbsp;<strong> con Valor:$</strong>"+dec.format(new Double(v_valor)).toString()+"</font></td></tr>");
    if(!msgErr1.equals(""))
     {
      out.println("<font face='Verdana' size='1' align='left' color='000000'>"+msgErr1+"</FONT>");
      out.println("<BR><BR>");
      out.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
      out.println(codHtm.TBFL_PIE);
      out.close();
     }
    if(sess.getValue("VALRETSEL")!=null && num.equals("2"))
     {
      valores=(String)sess.getValue("VALRETSEL");//información del retiro
     }
    else
    {
      valores = TBPBD_RetSel();
      sess.removeValue("VALRETSEL");
      sess.putValue("VALRETSEL",valores);
    }
    if(!valores.equals(""))
    {
      //BUSCO ESQUEMA ACTUAL--DEFAULT
      CallableStatement t_cst8i_1 = v_conexion_tb.prepareCall("{ call TBPBD_ESQUEMA_ACTUAL(?,?,?,?) }");
      t_cst8i_1.registerOutParameter(4,Types.VARCHAR);
      t_cst8i_1.setString(1,cod_producto);
      t_cst8i_1.setString(2,num_contrato);
      t_cst8i_1.setString(3,v_cons);
      t_cst8i_1.execute();
      str_esquema = t_cst8i_1.getString(4);
      t_cst8i_1.close();
      //encuentro esquema de respetar naturaleza actual
      String r_n     = new String("");
      String rn_tmp  = new String("");
      try{r_n        = str_esquema.substring(30,31);}
      catch(Exception ex){r_n = "X";}
      //--respetar naturaleza
      if(r_n.equalsIgnoreCase("S"))
         rn_tmp  = "<SELECT name=srn><OPTION VALUE=''>NINGUNO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option><OPTION selected VALUE=S>SI</OPTION><OPTION VALUE=N>NO</OPTION></SELECT>";
      else if(r_n.equalsIgnoreCase("N"))
         rn_tmp  = "<SELECT name=srn><OPTION VALUE=''>NINGUNO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option><OPTION VALUE=S>SI</OPTION><OPTION selected VALUE=N>NO</OPTION></SELECT>";
      else
         rn_tmp  = "<SELECT name=srn><OPTION selected VALUE=''>NINGUNO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option><OPTION VALUE=S>SI</OPTION><OPTION VALUE=N>NO</OPTION></SELECT>";
      String fechaAct   = new String(" ");
      java.sql.Date fec = new java.sql.Date(4);
      CallableStatement t_cst8i_11 = v_conexion_tb.prepareCall("{ ? = call TB_FFECHA_SIGUIENTE(-1) }");
      t_cst8i_11.registerOutParameter(1,Types.DATE);
      t_cst8i_11.executeUpdate();
      fec = t_cst8i_11.getDate(1);
      t_cst8i_11.close();
      fechaAct          = fec.toString();
      String metodo     = new String("ORDEN:");
      String referencia = new String(" ");
      out.println("<input type='hidden' id='cadena' name='cadena' value='"+cadena+"'>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width='100%'>"+
                  "<TR class=\"td11\" borderColor=silver>"+
                  "<TH width='200'><FONT color=white face=verdana size=1>Esquema del Retiro</font></TH>"+
                  "<TH width='200'><FONT color=white face=verdana size=1>Valor del Esquema</font></TH>"+
                  "</TR>");
      for(int i=0;i<6;i++)
      {
        CallableStatement t_cst8i_2 = v_conexion_tb.prepareCall("{ call TBPBD_ESQUEMA_TOTAL(?,?,?) }");
        t_cst8i_2.registerOutParameter(3,Types.VARCHAR);
        t_cst8i_2.setString(1,metodo);
        t_cst8i_2.setString(2,str_esquema);
        t_cst8i_2.execute();
        referencia = t_cst8i_2.getString(3);
        t_cst8i_2.close();
        if(metodo.equals("RESPETAR NATURALEZA:"))
          referencia = rn_tmp;
        out.println(
        "<TR><TD width='200'><FONT color=black face=verdana size=1>"+metodo+"</font></TD>"+
        "<TD width='200'><FONT color=black face=verdana size=1>"+referencia+"</font></TD></TR>");
        if(i==0)metodo="BENEFICIO:";  if(i==1)metodo="PENALIZACION:"; if(i==2)metodo="CUENTA:";
        if(i==3)metodo="NATURALEZA:"; if(i==4)metodo="RESPETAR NATURALEZA:";
      }
      out.println("</TABLE>");
      out.println("<left><FONT color=black face=verdana size=1><strong>NOTA:</strong> Si desea el default del contrato debe seleccionar NINGUNO para todo el esquema. </font></left>");
      //fechas
      out.println("<input type='hidden' name='fecharetiro' value='"+v_fecha+"'>"); //OCULTO FECHA DEL RETIRO
      out.println("<input type='hidden' name='fechahoy' value='"+fechaAct+"'>");  //OCULTO FECHA DE HOY
      out.println("<BR><font face='Verdana' size='1'><strong>AJUSTAR A FECHA</strong></font>");
      out.println("<input type='radio' name='fechaAjs' checked value='"+v_fecha+"'>"+v_fecha+"&nbsp;&nbsp;&nbsp;&nbsp;");
      out.println("<input type='radio' name='fechaAjs'         value='"+fechaAct+"'>"+fechaAct);
      out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(Fecha del retiro)&nbsp;&nbsp;&nbsp;&nbsp;(Fecha a Hoy)");
      //textarea para la razon del ajuste
      out.println("<BR><font face='Verdana' size='1'><strong>RAZON DEL AJUSTE:</strong></font>");
      out.println("<BR><textarea rows='2' name='obligatorio_razon' cols='58'></textarea></font>");
     }
    else
     {
      i_LP.TBPL_scriptMsgErr(out,"Error de Session, No se encontró la información del retiro "+v_cons);
     }
      //boton aceptar y cancelar
      out.println("<center><table border='0' width='34%'>");
      out.println("<tr><td align='center' width='17%'><input type='submit' value='Aceptar'></td>");
      out.println("<td align='center' width='17%'><input type='button' value='Regresar' ONCLICK=history.go(-1);></td>");
      out.println("</tr></table></center>");
      out.println(codHtm.TBFL_PIE);
      out.close();
      v_conexion_tb.close();
   }
   catch(Exception ex)
     {
      TBCL_LoadPage i_LP = new TBCL_LoadPage();
      i_LP.TBPL_PrintMsgErr(out,"Se producjo un error inesperado durante el proceso, Intente de Nuevo, Error: "+ex,true,k_cabeza,codHtm.TBFL_PIE);
     }
  }
///////////////////buscar la información del consecutivo del retiro escogido//////////////////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA buscar la información del consecutivo del retiro escogido
* <p>
* @param   NINGUNO<BR>
* @return  NINGUNO
*/
   private String TBPBD_RetSel(){
    String v_rta=new String("");
    if(sess.getValue("VALRETANU")!=null){
      v_rta=(String)sess.getValue("VALRETANU");
      String v_ii=new String("0");
      int ii=0;
      while(true){//buscar datos del consecutivo del retiro
        ii++;v_ii=Integer.toString(ii);
        if(i_fnd.TBPL_getCmp(v_rta,"const"+v_ii).equals(v_cons))
          break;
        if(ii>10000){v_ii="0";break;}
      }
      //armar la información con sólo el consecutivo del retiro escogido
      if(!v_ii.equals("0"))
        v_rta="<neto='"+i_fnd.TBPL_getCmp(v_rta,"neto"+v_ii)+
              "' bruto='"+i_fnd.TBPL_getCmp(v_rta,"bruto"+v_ii)+
              "' fechae='"+i_fnd.TBPL_getCmp(v_rta,"fechae"+v_ii)+
              "' fechap='"+i_fnd.TBPL_getCmp(v_rta,"fechap"+v_ii)+
              "' tipo='"+i_fnd.TBPL_getCmp(v_rta,"tipo"+v_ii)+
              "' esquema='"+i_fnd.TBPL_getCmp(v_rta,"esquema"+v_ii)+
              "' unidad='"+i_fnd.TBPL_getCmp(v_rta,"unidad"+v_ii)+
              "' feccargue='"+i_fnd.TBPL_getCmp(v_rta,"feccargue"+v_ii)+
              "' >";
    }
    return v_rta;
  }
///////////////////////////////tomar parametros de entrada a la página/////////////////////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA tomar parametros de entrada a la página
* <p>
* @param   NINGUNO<BR>
* @return  BOOLEANO
*/
  private boolean TBPL_getParams_Sess1(){
    String refresh   = new String("X");
    sess.putValue("refresh",refresh);
    boolean v_exist = false;
    v_cons          = v_fecha  = v_valor = v_nombApel = num_contrato = cod_producto = v_tipo = v_estado = v_esquema = "";
    if(sess.getValue("NOMBAPEL")!=null &&    sess.getValue("KEYS")!=null && sess.getValue("TIPO")!=null &&
         sess.getValue("ESTADO")!=null && sess.getValue("ESQUEMA")!=null)
       {
        String v_keys  = (String)sess.getValue("KEYS");
        cod_producto   = i_fnd.TBPL_getCmp(v_keys,"producto");
        num_contrato   = i_fnd.TBPL_getCmp(v_keys,"contrato");
        //tomar el consecutivo del retiro escogido
        String v_cons1 = request.getParameter("cons");
        v_cons         = v_cons1.substring(0,v_cons1.indexOf("fec"));
        v_fecha        = v_cons1.substring(v_cons1.indexOf("fec")+3,v_cons1.indexOf("val"));
        v_valor        = v_cons1.substring(v_cons1.indexOf("val")+3);
        sess.removeValue("BUSQUEDA");
        sess.putValue("BUSQUEDA","<cons='"+v_cons+"' fec='"+v_fecha+"' val='"+v_valor+"'>");
        v_nombApel     = (String)sess.getValue("NOMBAPEL");//nombres y apellidos
        v_tipo         = (String)sess.getValue("TIPO");// ref.tipo de valor
        v_estado       = (String)sess.getValue("ESTADO");//ref.estado del retiro
        v_esquema      = (String)sess.getValue("ESQUEMA");//ref. del esquema
        v_exist        = true;
      }
      else
      {
        msje_final = "Se produjo un Error con los parámetros de Session";
        //i_LP.TBPL_PrintMsgErr(out,"Error con los parámetros",true,k_cabeza,codHtm.PIE);
        v_exist        = false;
      }
    return v_exist;
  }
//////////////////////////////tomar los nuevos valores del retiro modificado//////////////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA tomar los nuevos valores del retiro modificado
* <p>
* @param   v_conexion_taxb  = OBJETO CONEXION<BR>
* @return  BOOLEANO
*/
private boolean TBPL_getParams_Sess2(Connection v_conexion_taxb)
 {
  try
  {
    //INT20131108
    String rutaFisica = TBCL_REFERENCIAS.TBCL_CONSULTA("SMM003");
    //rutaFisica  =  "c:\\Taxbenefits\\Taxbenefits\\public_html\\WEB-INF\\";
    String POLICY_FILE_LOCATION = rutaFisica+"antisamy-tinymce-1.4.4.xml"; // Path to policy file
    AntiSamy as = new AntiSamy(); // INT20131108
      
    boolean v_exist      = false;
    v_error              ="";
    java.sql.Date vfproc = new java.sql.Date(4);
    vorden = "";vbenef = "";vpenal = "";vcuenta = "";vnatural = "";vrespetarn = "";
    v_cons = v_fecha = v_valor=v_razon = v_nombApel = num_contrato = cod_producto = v_tipo = v_estado = v_esquema = v_fecha_choose = v_fecha_proc="";
    if(request.getParameter("smb")!=null && request.getParameter("smo")!=null && request.getParameter("snr")!=null &&
      request.getParameter("smp")!=null && request.getParameter("smc")!=null &&
      request.getParameter("srn")!=null && request.getParameter("fechaAjs")!=null && request.getParameter("obligatorio_razon")!=null)
      {
         vorden            = request.getParameter("smo").toUpperCase();
         vbenef            = request.getParameter("smb").toUpperCase();
         vnatural          = request.getParameter("snr").toUpperCase();
         vpenal            = request.getParameter("smp").toUpperCase();
         vcuenta           = request.getParameter("smc").toUpperCase();
         vrespetarn        = request.getParameter("srn").toUpperCase();
         v_fecha_choose    = request.getParameter("fechaAjs");
         fecharetiro       = request.getParameter("fecharetiro");
         fechahoy          = request.getParameter("fechahoy");
         java.sql.Date fec = new java.sql.Date(4);
         CallableStatement t_cst8i_11 = v_conexion_taxb.prepareCall("{ ? = call TB_FFECHA_SIGUIENTE(-1) }");
         t_cst8i_11.registerOutParameter(1,Types.DATE);
         t_cst8i_11.executeUpdate();
         fec = t_cst8i_11.getDate(1);
         t_cst8i_11.close();
         v_fecha_proc = fec.toString();
         //validar que la fecha de proceso sea mayor o igual a la efectiva
         vfproc         = java.sql.Date.valueOf(v_fecha_proc);
         vfefec         = java.sql.Date.valueOf(v_fecha_choose);
         if(vfproc.before(vfefec))
         {
          msje_final = "No se puede anular el retiro No."+v_cons+" por que la fecha efectiva no puede ser mayor a la del proceso";
          //i_LP.TBPL_PrintMsgErr(out,"No se puede anular el retiro No."+v_cons+" por que la fecha efectiva no puede ser mayor a la del proceso",true,k_cabeza,codHtm.PIE);
          return false;
         }
        v_razon = new String(request.getParameter("obligatorio_razon"));
        try
         {
           CleanResults cr = as.scan(request.getParameter("obligatorio_razon"), new File(POLICY_FILE_LOCATION));
           v_razon = cr.getCleanHTML();
         }
        catch (Exception e)
         {
         e.printStackTrace();
         } //INT20131108
        if(v_razon.length()>100)
          v_razon = v_razon.substring(0,100);
        if(sess.getValue("BUSQUEDA")!=null && sess.getValue("KEYS")!=null &&
         sess.getValue("NOMBAPEL")!=null && sess.getValue("VALRETSEL")!=null)
         {
          String busq  = (String)sess.getValue("BUSQUEDA");
          v_cons       = i_fnd.TBPL_getCmp(busq,"cons");
          //v_fecha=i_fnd.TBPL_formatDate(i_fnd.TBPL_getCmp(busq,"fec"));
          v_fecha       = i_fnd.TBPL_getCmp(busq,"fec");
          v_valor       = i_fnd.TBPL_getCmp(busq,"val");
          String v_keys = (String)sess.getValue("KEYS");
          cod_producto  = i_fnd.TBPL_getCmp(v_keys,"producto");
          num_contrato  = i_fnd.TBPL_getCmp(v_keys,"contrato");
          usuario       = i_fnd.TBPL_getCmp(v_keys,"usuario");
          v_nombApel    = (String)sess.getValue("NOMBAPEL");
          v_esquema     = (String)sess.getValue("ESQUEMA");
          valores       = (String)sess.getValue("VALRETSEL");
          v_exist       = true;
        }
     }
    return v_exist;
   }//try
  catch(Exception ex){msje_final = "SE PRODUJO UN ERROR EN EL PROCEDIMIENTO TBPL_getParams_Sess2: "+ex.toString(); return false;}
  }
//////////////////////////validar los nuevos valores del esquema del retiro/////////////////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA validar los nuevos valores del esquema del retiro
* <p>
* @param   NINGUNO<BR>
* @return  BOOLEANO
*/
  private boolean TBPL_validar()
  {
    v_error          = new String("");
    boolean v_ok     = false;
    //esquema anterior del retiro
    String v_esq_old = i_fnd.TBPL_getCmp((String)sess.getValue("VALRETSEL"),"esquema");
    //VALIDAR QUE ESTEN VACIOS O LLENOS
    if((vrespetarn.equals("") && vorden.equalsIgnoreCase("SMO000") &&
        vbenef.equalsIgnoreCase("SMB000") && vpenal.equalsIgnoreCase("SMP000") &&
        vnatural.equalsIgnoreCase("SNR000") && vcuenta.equalsIgnoreCase("SMC000"))
      ||
        (!(vorden).equalsIgnoreCase("SMO000") && !(vbenef).equalsIgnoreCase("SMB000") &&
        !(vpenal).equalsIgnoreCase("SMP000") && !(vnatural).equalsIgnoreCase("SNR000") &&
        !(vcuenta).equalsIgnoreCase("SMC000") && !(vrespetarn).equals("")))
    {
        //VALIDA QUE AL MENOS 1 HAYA CAMBIADO
        if(!vorden.equals(i_fnd.TBPL_getCmp(v_esq_old,"smo"))  ||
          !vbenef.equals(i_fnd.TBPL_getCmp(v_esq_old,"smb"))   ||
          !vpenal.equals(i_fnd.TBPL_getCmp(v_esq_old,"smp"))   ||
          !vnatural.equals(i_fnd.TBPL_getCmp(v_esq_old,"snr")) ||
          !vcuenta.equals(i_fnd.TBPL_getCmp(v_esq_old,"smc"))  ||
          !vrespetarn.equals(i_fnd.TBPL_getCmp(v_esq_old,"srn")))
        {
          v_ok = true;
        }
        else
        {
         msje_final = "Dentro del esquema de retiro debe haber al menos un cambio";
        }
     }
     else
     {
      msje_final = "El esquema de retiro debe quedar todos con opción de ninguno o todos con algún valor";
     }
    return v_ok;
  }
/////////////////////////////proceso de Anular y reversar el retiro///////////////////////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA REVERSAR Y ANULAR EL RETIRO ORIGINAL
* <p>
* @param   v_conexion_taxb  = OBJETO CONEXION<BR>
* @return  BOOLEANO
*/
 private boolean TBPBD_AnularModificar(Connection v_conexion_taxb){
  try
  {
    boolean confir    = false;
    double valUnidRet = 0;
    String VU  = i_fnd.TBPL_getCmp(valores,"unidad");
    Double vu  = new Double(VU);
    valUnidRet = vu.doubleValue();
    String busq           = (String)sess.getValue("BUSQUEDA");
    String ret_c          = i_fnd.TBPL_getCmp(busq,"cons");
     String v_retestado = "";
   try
   {

   String v_estadoretiro = "SELECT RET_REF_ESTADO  "+
                              "   FROM TBRETIROS  "+
                              "  WHERE RET_CON_PRO_CODIGO  = '"+cod_producto+"'  "+
                              "    AND RET_CON_NUMERO     = '"+num_contrato+"'  "+
                              "    AND RET_CONSECUTIVO    = '"+ret_c+"' ";
   Statement t_stestado = v_conexion_taxb.createStatement();


   ResultSet  t_rsestado = t_stestado.executeQuery(v_estadoretiro);
   if(t_rsestado.next())
   {
     v_retestado = t_rsestado.getString(1);

    }
   t_rsestado.close();
   t_stestado.close();

    if(v_retestado.equals("SER008"))
    {
      msje_final = "EL RETIRO YA FUE MODIFICADO.";
     return false;
    }
   }
   catch(Exception ex)
     {
       msje_final = "ERROR AAL CONSULTAR RETIRO "+ex.toString();
       return false;
     }
    //actualizar y reversar  retiros;
    try
    {
      v_fecha_choose1   = v_fecha_choose;
      v_fecha_choose    = i_fnd.TBPL_formatDate(request.getParameter("fechaAjs"),"");
      v_fecha_proc      = i_fnd.TBPL_formatDate(v_fecha_proc,"");
      String str_confir = new String("");
      CallableStatement t_cst8i_11 = v_conexion_taxb.prepareCall("{ ? = call TBFBD_REVERSAR_RETIROS(?,?,?,?,?,?,?) }");
      t_cst8i_11.registerOutParameter(1,Types.VARCHAR);
      t_cst8i_11.setString(2,cod_producto);
      t_cst8i_11.setString(3,num_contrato);
      t_cst8i_11.setString(4,"SER008");
      t_cst8i_11.setString(5,ret_c);
      t_cst8i_11.setDouble(6,valUnidRet);
      t_cst8i_11.setInt(7,1);
      t_cst8i_11.setInt(8,1);
      t_cst8i_11.executeUpdate();
      str_confir = t_cst8i_11.getString(1);
      t_cst8i_11.close();
      if(str_confir.equalsIgnoreCase("YES"))
      {
        confir = true;
      }
      else if(str_confir.equalsIgnoreCase("NO"))
      {
        msje_final = "No se pudo anular el retiro No."+v_cons+" No se encontraron Aportes Retiros";
        confir = false;
      }
     }//try
   catch(Exception ex){msje_final = "SE PRODUJO UN ERROR EN EL PROCEDIMIENTO DE REVERSION DEL RETIRO: "+ex.toString();return false;}
   return confir;
   }//try
   catch(Exception ex){msje_final = "SE PRODUJO UN ERROR EN EL PROCEDIMIENTO DE REVERSION DEL RETIRO: "+ex.toString();return false;}
 }
///////////////////////////////registrar la transacción de cada retiro/////////////////////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA registrar la transacción de cada retiro
* <p>
* @param   v_conexion_taxb  = OBJETO CONEXION<BR>
* @return  BOOLEANO
*/
  private boolean TBPL_TransaccionLog(Connection v_conexion_taxb)
  {
   try
   {
    int v_linea=-1;
    CallableStatement callLinea = v_conexion_taxb.prepareCall("{call TBPBD_TRL_Linea(?,?,?)}");
    callLinea.registerOutParameter(3,Types.INTEGER);
    callLinea.setInt(1,Integer.parseInt(v_cons));
    callLinea.setString(2,"R");
    callLinea.execute();
    v_linea = callLinea.getInt(3);
    callLinea.close();
    CallableStatement t_cst8i_2 = v_conexion_taxb.prepareCall("{ call TBPBD_TransaccionLog(?,?,?,?,?,?,?) }");
    t_cst8i_2.registerOutParameter(7,Types.VARCHAR);
    t_cst8i_2.setString(1,"R");
    t_cst8i_2.setInt(2,Integer.parseInt(v_cons));
    t_cst8i_2.setInt(3,v_linea);
    t_cst8i_2.setString(4,"A");
    t_cst8i_2.setString(5,"Anulado Por TaxBenefits");
    t_cst8i_2.setString(6,usuario);
    t_cst8i_2.execute();
    String str_confir  = t_cst8i_2.getString(7);
    t_cst8i_2.close();
    //String str_confir = i_sqlj.TBPBD_TransaccionLog("R",v_cons,v_linea,"A","Modificado por TaxBenefit",usuario);
    if(!str_confir.equals("YES"))
    {
      msje_final = "No se pudo insertar en Transacción Log la informacion del retiro No"+v_cons+"  debido al Error: "+str_confir ;
      //i_LP.TBPL_PrintMsgErr(out,"(Anular Retiro)No se pudo insertar en Transacción Log la informacion del retiro aanulado "+v_cons,true,k_cabeza,codHtm.PIE);
      return false;
    }
    else
      return true;
   }//try
   catch(Exception ex){msje_final = "SE PRODUJO UN ERROR EN EL PROCEDIMIENTO DE INSERCCION EN LA TABLA DE LOGS: "+ex.toString();return false;}
  }
//////////////////////////////////proceso de Aplicar el retiro y retiro Prima////////////////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA GENERAR EL RETIRO PRIMA A PARTIR DEL RETIRO
* ORIGINAL
* <p>
* @param   v_conexion_taxb  = OBJETO CONEXION<BR>
* @return  BOOLEANO
*/
  private boolean TBPL_Aplicar(Connection v_conexion_taxb){
   double valor_unidad_final = 0;

   if(v_fecha_choose1.equalsIgnoreCase(fechahoy))
   //Se debe invocar la función del Cálculo de la Unidad, la fecha del ajuste es la misma que hoy
   {
     try
     {
       //calcular valor unidad

/* Modificacion: Se elimina la linea que instancia a
   SQL_CALCULO_VALOR_UNIDAD ya que se va a invocar el metodo en
   la base de datos */

//       SQL_CALCULO_VALOR_UNIDAD i_valUnid = new SQL_CALCULO_VALOR_UNIDAD();

       double matUnidad                 = 0;

       //       matUnidad                          = i_valUnid.TBF_CALCULO_VALOR_UNIDAD(Fecha_as400(v_fecha_choose1),Fecha_as400(v_fecha_choose1),num_contrato,cod_producto,false,0);

       try
       {
       CallableStatement cs = v_conexion_taxb.prepareCall("{? = call SQL_CALCULO_VALOR_UNIDAD.TBF_CALCULO_VALOR_UNIDAD_N(?,?,?,?,?,?,?)}");
       cs.registerOutParameter(1,Types.DOUBLE);
       cs.setString(2,Fecha_as400(v_fecha_choose1));
       cs.setString(3,Fecha_as400(v_fecha_choose1));
       cs.setString(4,num_contrato);
       cs.setString(5,cod_producto);
       cs.setString(6,"false");
       cs.setInt(7,0);
       cs.setInt(8,0);
       cs.executeUpdate();
       matUnidad = cs.getDouble(1);
       cs.close();
       }
       catch(Exception E)
       {
        System.out.println("Error llamando a SQL_CALCULO_VALOR_UNIDAD.TBF_CALCULO_VALOR_UNIDAD_N");
       }
       valor_unidad_final                 = matUnidad;
     }

/* Final de Modificacion */

     catch(Exception ex)
     {
       msje_final = "ERROR EN EL LLAMADO A LA FUNCION DEL CALCULO DEL VALOR DE LA UNIDAD, DEBIDO AL ERROR: "+ex.toString();
       return false;
     }
   }//si fecha del ajuste ==  a hoy
   double val_neto = 0;
   String p        = i_fnd.TBPL_getCmp(valores,"neto");
   Double Val_neto = new Double(p);
   val_neto        = Val_neto.doubleValue();
   consecAjus      = 0;
   try
   {
   //calculo consecutivo del ajuste
   CallableStatement callNext = v_conexion_taxb.prepareCall("{? = call TBFBD_nextConsecAjus()}");
   callNext.registerOutParameter(1,Types.VARCHAR);
   callNext.execute();
   consecAjus = callNext.getInt(1);
   callNext.close();
   }
   catch(Exception ex){ }
   //declaro variables String() para mantener esquema
   String m_o = new String(" ");    String m_b = new String(" ");
   String m_p = new String(" ");    String m_c = new String(" ");
   String nat = new String(" ");    String r_n = new String(" ");
   //declaro variables String() para mantener esquema
   m_o = vorden;      m_b = vbenef;      m_p = vpenal;
   m_c = vcuenta;     nat = vnatural;    r_n = vrespetarn;
   try
   {
    if(m_o.equalsIgnoreCase("SMO000")||m_b.equalsIgnoreCase("SMB000")||m_p.equalsIgnoreCase("SMP000")||
     m_c.equalsIgnoreCase("SMC000")||nat.equalsIgnoreCase("SNR000")||r_n.equals(""))
     { m_o = null;   m_b = null;  m_p = null;  m_c = null;  nat = null;  r_n = null;}
    //valido esuqema
    if(m_o.equals(null)||m_b.equals(null)||m_p.equals(null)||m_c.equals(null)||nat.equals(null)||r_n.equals(null))
     { m_o = null;   m_b = null; m_p = null;  m_c = null;  nat = null; r_n = null;}
   }//try
   catch(Exception ex)
   { m_o = null;   m_b = null; m_p = null;  m_c = null;  nat = null; r_n = null;}//catch
   try
   {
     //Declaro variables para ix del retiro prima
     java.sql.Date vfefec  = new java.sql.Date(4);   java.sql.Date vfecproc= new java.sql.Date(4);
     String vtiptran       = "";                     String vclastran      = "";String vtipval="";
     String vretpen        = "";String vretpro="";   String vuser          = "";
     String vunidproc      = "";                     String vbanco         = "";
     String vcuenta        = "";                     String vtransaccion   = "";
     String vcodigoafp     = "";                     String vcodti         = "";
     String vnumti         = "";                     String vretcargue     = "";
     String msgErr         = "";                     int v_codErr          = 0;
     String vretestado     = "";
     double vvalor = 0,vvalunid = 0;
     //Capturo Ix del Retiro Original
     CallableStatement callInfRet = v_conexion_taxb.prepareCall("{call TBPBD_SelInfRetiro(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
     callInfRet.setString(1,cod_producto);   callInfRet.setString(2,num_contrato);
     callInfRet.setInt(3,Integer.parseInt(v_cons));
     callInfRet.registerOutParameter(4,Types.DATE);     callInfRet.registerOutParameter(5,Types.DATE);
     callInfRet.registerOutParameter(6,Types.VARCHAR);  callInfRet.registerOutParameter(7,Types.VARCHAR);
     callInfRet.registerOutParameter(8,Types.NUMERIC);  callInfRet.registerOutParameter(9,Types.VARCHAR);
     callInfRet.registerOutParameter(10,Types.NUMERIC); callInfRet.registerOutParameter(11,Types.VARCHAR);
     callInfRet.registerOutParameter(12,Types.VARCHAR); callInfRet.registerOutParameter(13,Types.VARCHAR);
     callInfRet.registerOutParameter(14,Types.VARCHAR); callInfRet.registerOutParameter(15,Types.VARCHAR);
     callInfRet.registerOutParameter(16,Types.VARCHAR); callInfRet.registerOutParameter(17,Types.VARCHAR);
     callInfRet.registerOutParameter(18,Types.VARCHAR); callInfRet.registerOutParameter(19,Types.VARCHAR);
     callInfRet.registerOutParameter(20,Types.VARCHAR); callInfRet.registerOutParameter(21,Types.VARCHAR);
     callInfRet.registerOutParameter(22,Types.VARCHAR); callInfRet.registerOutParameter(23,Types.VARCHAR);
     callInfRet.registerOutParameter(24,Types.VARCHAR); callInfRet.registerOutParameter(25,Types.VARCHAR);
     callInfRet.registerOutParameter(26,Types.VARCHAR); callInfRet.registerOutParameter(27,Types.VARCHAR);
     callInfRet.registerOutParameter(28,Types.NUMERIC); callInfRet.registerOutParameter(29,Types.VARCHAR);
     callInfRet.execute();
     vfefec     = callInfRet.getDate(4);     vfecproc       = callInfRet.getDate(5);
     vtiptran   = callInfRet.getString(6);   vclastran      = callInfRet.getString(7);
     vvalor     = callInfRet.getDouble(8);   vtipval        = "STV001";
     vvalunid   = callInfRet.getDouble(10);  vretpen        = callInfRet.getString(11);
     vretpro    = callInfRet.getString(12);  vuser          = callInfRet.getString(13);
     vunidproc  = callInfRet.getString(14);  vbanco         = callInfRet.getString(15);
     vcuenta    = callInfRet.getString(16);  vtransaccion   = callInfRet.getString(17);
     vcodigoafp = callInfRet.getString(18);  vcodti         = callInfRet.getString(19);
     vnumti     = callInfRet.getString(20);  vretcargue     = callInfRet.getString(27);
     v_codErr   = callInfRet.getInt(28);     msgErr         = callInfRet.getString(29);
     callInfRet.close();
     if(!msgErr.equalsIgnoreCase("OK"))
      {
       msje_final= "SE PRODUJO UN ERORR EN LA CAPTURA DE LA INFORMACION DEL RETIRO ORIGINAL DEBIDO AL ERROR: "+msgErr;
       return false;
      }
     //reviso si el valor de la unidad a aplicar es la del retiro o la calculada previamente
     if(v_fecha_choose1.equalsIgnoreCase(fecharetiro))
     //no se invoca la función del calculo de la unidad, sino que se trabaja con el valor de udad del retiro original
     {
       valor_unidad_final = vvalunid;
     }
     //Chequeo Penalización del Retiro
     if(vretcargue.equalsIgnoreCase("S"))  vretpen = "N";
     if(vretcargue.equalsIgnoreCase("N"))  vretpen = "S";
     if(vretestado.equals("SER011"))
     {


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
      cs2.setString(2,num_contrato);
      cs2.setString(3,v_sistema);
      cs2.setString(4,v_usumfund);
      cs2.setString(5,v_passmfund);
      cs2.setString(6,v_libreria);
      cs2.executeUpdate();
      v_retorno_programa = cs2.getString(1);
      cs2.close();
      v_programa = v_retorno_programa.substring(0,v_retorno_programa.indexOf(";",0));
      /*FIN Modificación hecha por APC para manejar el nuevo reglamento 2006-06-22*/          


     //calculo fecha de proceso
     DateFormat currentDateFormat;
     java.util.Date currentDate = new java.util.Date();
     currentDateFormat          = DateFormat.getDateInstance(DateFormat.DEFAULT, java.util.Locale.PRC);
     String d                   = currentDateFormat.format(currentDate);
     int ano                    = Integer.parseInt(d.substring(0,4))-1900;
     int mes                    = Integer.parseInt(d.substring(5,d.lastIndexOf("-")))-1;
     int dia                    = Integer.parseInt(d.substring(d.lastIndexOf("-")+1,d.length()));
     java.sql.Date hoy          = new java.sql.Date(ano,mes,dia);
     CallableStatement l_stmt1 = v_conexion_taxb.prepareCall("{call TBPBD_RETIRO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
     l_stmt1.registerOutParameter(31,Types.VARCHAR);   
     l_stmt1.registerOutParameter(32,Types.VARCHAR);
     l_stmt1.registerOutParameter(33,Types.INTEGER);   
     l_stmt1.registerOutParameter(34,Types.DOUBLE);
     l_stmt1.registerOutParameter(35,Types.INTEGER);   
     l_stmt1.registerOutParameter(36,Types.VARCHAR);

     /*
      * MOS Se quita temporalmente :v_programa
      * Se agrego nuevamente
      */
     l_stmt1.setString(1,cod_producto);   
     l_stmt1.setString(2,num_contrato);
     l_stmt1.setString(3,v_programa);
     l_stmt1.setString(4,null);           
     l_stmt1.setDate(5,hoy);
     l_stmt1.setDate(6,vfefec);           
     l_stmt1.setString(7,vtiptran);
     l_stmt1.setString(8,vclastran);      
     l_stmt1.setDouble(9,vvalor);
     l_stmt1.setString(10,vtipval);        
     l_stmt1.setDouble(11,valor_unidad_final);
     l_stmt1.setString(12,vretpen);       
     l_stmt1.setString(13,vretpro);
     l_stmt1.setString(14,vbanco);        
     l_stmt1.setString(15,vcuenta);
     l_stmt1.setString(16,vtransaccion);  
     l_stmt1.setString(17,vuser);
     l_stmt1.setString(18,vunidproc);     
     l_stmt1.setString(19,"SER006");
     l_stmt1.setString(20,vcodigoafp);    
     l_stmt1.setString(21,vcodti);
     l_stmt1.setString(22,vnumti);        
     l_stmt1.setString(23,m_o);
     l_stmt1.setString(24,m_b);           
     l_stmt1.setString(25,m_p);
     l_stmt1.setString(26,m_c);           
     l_stmt1.setString(27,nat);
     l_stmt1.setString(28,r_n);           
     l_stmt1.setString(29,"S");
     l_stmt1.setString(30,"N");
     l_stmt1.execute();
     String v_msj_error  = new String(" ");
     String v_pro        = l_stmt1.getString(31);   
     String v_contra     = l_stmt1.getString(32);
     int v_conret        = l_stmt1.getInt(33);     
     double v_valnetret  = l_stmt1.getDouble(34);
     int v_cod_error     = l_stmt1.getInt(35);      
     v_msj_error  = l_stmt1.getString(36);
     l_stmt1.close();
     if(!v_msj_error.equalsIgnoreCase("OK"))
      {
       msje_final= " NO PUDO SER REAPLICADO, EN EL PROCESO DEL RETIRO PRIMA SE PRODUJO UN ERROR, NO SE PUDO APLICAR EL RETIRO. NO SE PUDO GENERAR EL RETIRO AJUSTADO, NO EXISTE DISPONIBILIDAD EN APORTES PARA LAS OPCIONES ELEGIDAS. <BR>";
       return false;
      }
     //INSERTAR DISTRIBUCION DE FONDOS PARA EL NUEVO RETIRO
     CallableStatement t_cst8i_dis = v_conexion_taxb.prepareCall("{ ? = call TB_FDISFON_RETREV(?,?,?,?,?) }");
     t_cst8i_dis.registerOutParameter(1,Types.INTEGER);
     t_cst8i_dis.registerOutParameter(6,Types.VARCHAR);
     t_cst8i_dis.setString(2,cod_producto);
     t_cst8i_dis.setString(3,num_contrato);
     t_cst8i_dis.setInt(4,v_conret);
     t_cst8i_dis.setInt(5,Integer.parseInt(v_cons));
     t_cst8i_dis.execute();
     String v_msj_error_dis = t_cst8i_dis.getString(6);
     t_cst8i_dis.close();
     if(!v_msj_error_dis.equalsIgnoreCase("OK"))
     {
        msje_final= " NO PUDO SER REAPLICADO, EN EL PROCESO DEL RETIRO PRIMA SE PRODUJO UN ERROR, NO SE PUDO APLICAR EL RETIRO. NO SE PUDO GENERAR EL RETIRO AJUSTADO, NO SE INSERTO LA DISTRIBUCION DE FONDOS. <BR>";
       return false;
     }
     else if(v_msj_error_dis.equalsIgnoreCase("OK"))
     {
      //CALCULO AJUSTE
      double val_difNetos = val_neto - v_valnetret;
      //CHEQUEO TRANSACCION
      String v_tipTrans=new String("");
      if((val_difNetos * -1)< 0)  v_tipTrans = "UTT009";
      else                 v_tipTrans = "UTT008";
      //INSERTO AJUSTE
      CallableStatement t_cst8i_91   = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Ajuste(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
      t_cst8i_91.registerOutParameter(16,Types.VARCHAR);
      t_cst8i_91.setString(1,cod_producto);          t_cst8i_91.setString(2,num_contrato);
      t_cst8i_91.setInt(3,consecAjus);               t_cst8i_91.setInt(4,1);
      t_cst8i_91.setString(5,hoy.toString());        t_cst8i_91.setDate(6,vfecproc);
      t_cst8i_91.setString(7,"STR003");              t_cst8i_91.setString(8,v_tipTrans);
      t_cst8i_91.setString(9,"UCT001");              t_cst8i_91.setDouble(10,(val_difNetos*-1));
      t_cst8i_91.setString(11,v_razon);              t_cst8i_91.setString(12,usuario);
      t_cst8i_91.setDouble(13,valor_unidad_final);   t_cst8i_91.setInt(14,Integer.parseInt(v_cons));
      t_cst8i_91.setInt(15,v_conret);                t_cst8i_91.execute();
      String estado_aplicar = t_cst8i_91.getString(16);
      t_cst8i_91.close();
      if(!estado_aplicar.equalsIgnoreCase("BIEN"))
      {
        msje_final= "SE PRODUJO UN ERORR EN LA INSERCION DEL AJUSTE. DEBIDO AL ERROR: "+estado_aplicar;
        return false;
      }
      CallableStatement t_cst8i_10 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Cargos_Ajustes(?,?,?,?,?,?,?) }");
      t_cst8i_10.registerOutParameter(7,Types.VARCHAR);
      t_cst8i_10.setString(1,cod_producto);                       t_cst8i_10.setString(2,num_contrato);
      t_cst8i_10.setInt(3,Integer.parseInt(v_cons));              t_cst8i_10.setInt(4,v_conret);
      t_cst8i_10.setInt(5,consecAjus);                            t_cst8i_10.setInt(6,1);
      t_cst8i_10.execute();
      String estado_cargos_ajustes = t_cst8i_10.getString(7);
      t_cst8i_10.close();
      if(!estado_cargos_ajustes.equalsIgnoreCase("BIEN"))
      {
        msje_final= "SE PRODUJO UN ERORR EN LA INSERCION DEL LOS CARGOS DEL AJUSTE. DEBIDO AL ERROR: "+estado_cargos_ajustes;
        return false;
      }
      msje_final = "SE HA GENERADO EL AJUSTE No"+consecAjus+"  SATISFACTORIAMENTE";
     }
     v_conexion_taxb.commit();
     return true;
    }//TRY
    catch(Exception ex){msje_final = "SE PRODUJO UN ERROR EN EL PROCEDIMIENTO QUE REAPLICA EL RETIRO: "+ex.toString();return false;}
    }
//---------------------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE DARLE FORMATO AS/400 A UNA FECHA DETERMINADA
* <p>
* @param   f_e          = FECHA A FORMATEAR<BR>
* @return  fecha_as400  = FECHA FORMATEADA
*/
public static String Fecha_as400(String f_e)
{
 String año = " ";
 String mes = " ";
 String dia = " ";
 int indice = 0;
 String str = " ";
 indice     = f_e.indexOf("-");
 año        = f_e.substring(0,indice);
 str        = f_e.substring(indice+1,f_e.length());
 indice     = str.indexOf("-");
 mes        = str.substring(0,indice);
 dia        = str.substring(indice+1,str.length());
 String fecha_as400 = año+mes+dia;
 return fecha_as400;
}
//--
}


