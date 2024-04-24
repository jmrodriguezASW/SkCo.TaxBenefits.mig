/*
  El objetivo de este servlet es visualizar todos los ajustes por consecutivo que no
  tengan ninguna acción , para que el usuario tome alguna decisión sobre el.
  Solamente para la acción de ajustar al contrato se realiza el proceso de ajuste,
  en cambio cuando es girar o traer el dinero simplemente actualizamos la decisión y
  la fecha de la acción.
  Modificado Noviembre 27 de 2001
  Diego V
*/
package TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE;

//import sun.jdbc.*;
import java.sql.*;
import oracle.sql.*;
import oracle.jdbc.driver.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_AJUSTES.*;

public class TBS_DecisionRetiro extends HttpServlet {

  /**
   * Initialize global variables
   */
  STBCL_GenerarBaseHTML codHtm;
  TBCL_FndCmp i_fnd=new TBCL_FndCmp();
  TBCL_LoadPage i_LP;
  SQL_DAJUSTE i_sqlj;
  String consecAjusOnly= "";
  String cod_producto = "";
  String num_contrato = "";
  String v_nombApel="";
  String v_msg="";
  String v_err="";
  String k_TBFL_CABEZA="";
  String v_Ajustados="";
  boolean v_ejecutar=false;
  HttpServletRequest request;
  HttpServletResponse response;
  HttpSession sess;
  PrintWriter out;
  String nuevaCadena = "";

////////////////////////////////////inicialización del servlet////////////////////////////
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
    String contrato = "", producto = "", usuario  = "", unidad_o = "";
    String tipo_usuario = "", idworker = "";
    String parametros[] = new String[8];
    String  cadena = request.getParameter("cadena");
    nuevaCadena = cadena;
    String ip_tax = request.getRemoteAddr();
    TBCL_Seguridad Seguridad = new TBCL_Seguridad();
    out = new PrintWriter (response.getOutputStream());
    parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
    contrato = parametros[0];
    producto = parametros[1];
    usuario  = parametros[2];
    unidad_o = parametros[3];
    tipo_usuario = parametros[4];
    idworker = parametros[5];
    v_nombApel = v_Ajustados = consecAjusOnly = "";
    this.request = request;
    this.response = response;
    v_ejecutar = false;
    i_LP=new TBCL_LoadPage();
    i_fnd=new TBCL_FndCmp();
    i_sqlj=new SQL_DAJUSTE();

    sess=request.getSession(true);
    cod_producto = (String) sess.getAttribute("PRODUCTO");
    num_contrato = (String) sess.getAttribute("CONTRATO");
    if(!producto.equals(cod_producto) || !contrato.equals(num_contrato)){
      TBPL_CleanSession();
      sess.setAttribute("PRODUCTO", producto);
      sess.setAttribute("CONTRATO", contrato);
      cod_producto = producto;
      num_contrato = contrato;
    }

    k_TBFL_CABEZA = codHtm.TBFL_CABEZA("Decisión Respecto a los Ajustes","Decisión Respecto a los Ajustes","TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.TBS_DecisionRetiro","",true);
    //tomar parametros de entrada
    TBPL_takeParameter1();
    if(!cod_producto.equals("") && !num_contrato.equals(""))
    {
      if(i_sqlj.TBPBD_ConexionBD())
      {//conexión con la BD

        if(sess.getAttribute("NOMBAPEL")==null)
        {//tomar nombres y apellidos

            TBPBD_ContrNomApel();
        }else
          v_nombApel=(String)sess.getAttribute("NOMBAPEL");

        //tomar parametros de las decisones del cliente por cada consecutivo del ajuste
        if(TBPL_takeParameter2() && request.getParameter("only")==null)
        {

          String v_msg1= "";

          if(!v_ejecutar)
            v_msg1 = "No se decidió por ningún ajuste, si lo desea, por favor chequee la casilla de verificación y luego escoja la decisión";
          else
            v_msg1 = "La decision sobre los Ajustes se realizó con éxito";

          i_LP.TBPL_pageOk(out,v_msg1,k_TBFL_CABEZA,codHtm.TBFL_PIE,cod_producto,num_contrato,v_nombApel,"");
        }else{
          if(v_err.equals(""))
            TBPL_BuildPage1();//construir página de salida
          else
            i_LP.TBPL_PrintMsgErr(out,"Error en el proceso de decisión del cliente por razon de "+v_err+" / "+v_Ajustados,true,k_TBFL_CABEZA,codHtm.TBFL_PIE);
        }
        i_sqlj.TBPBD_CerrarConexionBD();

      }else//error conexion BD
         i_LP.TBPL_PrintMsgErr(out,"Error en la conexion con la Base de Datos",true,k_TBFL_CABEZA,codHtm.TBFL_PIE);

    }
    i_sqlj.TBPBD_CerrarConexionBD();
    out.close();
  }

///////////////////////Limpia Variables de Session que hay en memoria///////////////////////////////////
  private void TBPL_CleanSession(){
    if(sess.getAttribute("NOMBAPEL")!=null)
      sess.removeAttribute("NOMBAPEL");
    if(sess.getAttribute("KEYS")!=null)
      sess.removeAttribute("KEYS");
    if(sess.getAttribute("VALRETDEC")!=null)
      sess.removeAttribute("VALRETDEC");
    if(sess.getAttribute("MODIFICADODEC")!=null)
      sess.removeAttribute("MODIFICADODEC");
    if(sess.getAttribute("CONSECU")!=null)
      sess.removeAttribute("CONSECU");
    if(sess.getAttribute("PRODUCTO")!=null)
      sess.removeAttribute("PRODUCTO");
    if(sess.getAttribute("CONTRATO")!=null)
      sess.removeAttribute("CONTRATO");
  }

////////////construccion de la página de salida para la toma de decisiones de cada uno//////
            ///////// de los consecutivos del ajuste o ampliación del  mismo/////////
  private void TBPL_BuildPage1(){
    //primer parte enTBFL_CABEZAdo
    i_LP.TBPL_Encabezado(out,k_TBFL_CABEZA,cod_producto,num_contrato,v_nombApel,"");
    //mostrar resultado
    out.println("<center><table bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 align='center' width='100%'>");
    out.println("<tr bgColor=white borderColor=silver width='100%'>");

    out.println("<td align='center' width='3%' class=\"td11\" style='border: thin solid'></td>");
    out.println("<td align='center' width='14%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Detallar Ajuste</strong></font></td>");
    out.println("<td align='center' width='13%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Ajuste No.</strong></font></td>");
    out.println("<td align='center' width='30%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Fecha Proceso</strong></font></td>");
    out.println("<td align='center' width='16%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Usuario</strong></font></td>");
    out.println("<td align='center' width='26%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Valor</strong></font></td>");
    out.println("<td align='center' width='18%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Decisión</strong></font></td>");
    out.println("</tr>");
    boolean botonExe=false;
    //tomar la información de los ajustes sin acción de la variable de session si existe o
    //va a la BD.

    if(sess.getAttribute("VALRETDEC")!=null && sess.getAttribute("MODIFICADODEC")==null){
      TBPL_showDec((String)sess.getAttribute("VALRETDEC"),(String)sess.getAttribute("CONSECU"));
      botonExe=true;
    }
    else{
     if(sess.getAttribute("MODIFICADODEC")!=null)
       sess.removeAttribute("MODIFICADODEC");
     if(TBPBD_findDec())
       botonExe=true;
    }
    out.println("</table></center>");
    //boton regreso y aceptar

    out.println("<center><table border='0' align='center' width='34%'><tr>");
    if(botonExe){
      //en el llamado a cualquier servlet
      out.println("<TR><TD><INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+ nuevaCadena +"'></TD></TR>");
      out.println("<td width='17%' align='center'><input type='submit' value='Aceptar'></td>");

    }
    out.println("<td width='17%' align='center'><input type='button' value='Regresar' ONCLICK=history.go(-1);></td>");
    out.println("</tr></table></center>");

    out.println(codHtm.TBFL_PIE);
    out.close();
  }
/////procedimiento que va a la base de datos para traer de la tabla ajustes//////////////
        /////todos aquellos que no tengan accion para ser visualizados////////
  private boolean TBPBD_findDec(){
    String valores[]=new String[2];
    valores=i_sqlj.TBPBD_SelAllRetDec(num_contrato,cod_producto,consecAjusOnly);
    if(valores[0].equals("<>") || valores[0].indexOf("Exception")!=-1){
      i_LP.TBPL_scriptMsgErr(out,"No existen retiros a ajustar "+valores[0]);
      return false;
    }else{
      sess.setAttribute("VALRETDEC",valores[0]);//información por cada consecutivo con sus lineas
      sess.setAttribute("CONSECU",valores[1]);//valores de los consecutivos sin acción

      TBPL_showDec(valores[0],valores[1]);//mostrar la información
      return true;
    }
  }
////////muestra el resultado de cada uno de los consecutivos del ajuste sin acción/////////
  private void TBPL_showDec(String valores,String consec){
     String v_consec="";
     String v_valor="";
     int ii=1 ;
     int first=1;
     while(true){
      String v_ii=Integer.toString(ii++);
      if(!i_fnd.TBPL_getCmp(consec,"cons"+v_ii).equals("")){
        v_consec=i_fnd.TBPL_getCmp(consec,"cons"+v_ii);
        i_LP.TBPL_ShowResultDec(out,v_consec,i_fnd.TBPL_getCmp(valores,"user"+v_consec).toLowerCase(),i_fnd.TBPL_getCmp(valores,"fec"+v_consec),i_fnd.TBPL_getCmp(valores,"vtot"+v_consec),first);
        first++;
      }else{
        break;
      }
     }
  }
////Tomar los parámetros con las decisiones escogidas por el usuario para cada ajuste///////
  private boolean TBPL_takeParameter2(){
    v_err = "";
    v_msg = "";
    v_Ajustados = "";
    v_ejecutar = false;
    if(sess.getAttribute("VALRETDEC")==null && sess.getAttribute("CONSECU")==null)
      return false;
    String valores = (String) sess.getAttribute("VALRETDEC");
    String consec = (String) sess.getAttribute("CONSECU");
    int ii = 1, linea;
    String consecAjus = "";
    String v_ii = "";
    String v_rta = "";
    boolean save =true;
    String v_execute = "";
    String v_param = "";
    String accion = "";


    while(true){//recorrido para tomar cada una de las acciones por cada ajuste
      linea = 1;
      v_ii = "" + ii++;
      consecAjus = i_fnd.TBPL_getCmp(consec, "cons"+v_ii);
      v_param = "dec"+consecAjus;
      if(request.getParameter(v_param)!=null){
        v_execute = "ejec"+consecAjus;  //si tiene o no decisión
        accion = request.getParameter(v_param);
        if(request.getParameter(v_execute)!=null){
           v_ejecutar = true;
           //verificar si ya se tomo decision
           int linea2=1;
           String v_linea2=Integer.toString(linea);
           boolean v_resultado =false;
           while(linea2<=Integer.parseInt(i_fnd.TBPL_getCmp(valores,"max"+consecAjus)))
           {
             if(!i_fnd.TBPL_getCmp(valores,"val"+consecAjus+v_linea2).equals(""))
             {
               v_resultado =i_sqlj.TBPL_Verdecision(cod_producto,num_contrato,Integer.parseInt(consecAjus),Integer.parseInt(v_linea2));
               if(v_resultado)
               {
                i_LP.TBPL_PrintMsgErr(out,"El se ha tomado la decision para el ajuste "+Integer.parseInt(consecAjus),true,k_TBFL_CABEZA,codHtm.TBFL_PIE);
                break;
               }
             }
             linea2++;
             v_linea2=Integer.toString(linea2);
           }

          //si es ajustar al contrato
          if(accion.equalsIgnoreCase("SAC002")){
            //tomar la máxima linea para cada consecutivo del ajuste
            int maxLin = Integer.parseInt(i_fnd.TBPL_getCmp(valores,"max"+consecAjus));
            String v_linea = "" +linea;

            while(linea <= maxLin){
            v_rta = "";
            out.write("<BR>Valor de !i_fnd.TBPL_getCmp=( "+valores.toString() +"," + "val" +consecAjus+v_linea +") = "+ !i_fnd.TBPL_getCmp(valores,"val"+consecAjus+v_linea).equals("") + " valor retorno=" + i_fnd.TBPL_getCmp(valores,"val"+consecAjus+v_linea));
              if(!i_fnd.TBPL_getCmp(valores,"val"+consecAjus+v_linea).equals("")){
                //llamado al procedimiento para el ajuste respectivo
                out.write ("<br>Se va a llamar TBPBD_AjustarContrato ("+cod_producto+","+num_contrato+","+
                Integer.parseInt(i_fnd.TBPL_getCmp(valores,"orig"+consecAjus+linea))+","+
                Integer.parseInt(i_fnd.TBPL_getCmp(valores,"act"+consecAjus+linea))+","+
                Integer.parseInt(consecAjus)+","+
                linea+","+
                Float.parseFloat(i_fnd.TBPL_getCmp(valores,"und"+consecAjus+linea)));
                v_rta = i_sqlj.TBPBD_AjustarContrato(cod_producto,num_contrato,
                Integer.parseInt(i_fnd.TBPL_getCmp(valores,"orig"+consecAjus+linea)),
                Integer.parseInt(i_fnd.TBPL_getCmp(valores,"act"+consecAjus+linea)),
                Integer.parseInt(consecAjus),
                linea,
                Float.parseFloat(i_fnd.TBPL_getCmp(valores,"und"+consecAjus+linea)));

                out.write("La respuesta para el ajueste es:"+v_rta);
              }
              if(v_rta.indexOf("Exception")!=-1){//cuando existe Excepciones
                save = false;
                i_LP.TBPL_PrintMsgErr(out,"No actualiza Ajuste "+consecAjus+" por "+v_rta,true,k_TBFL_CABEZA,codHtm.TBFL_PIE);
                out.write("<br>Le puso false en el exception");                
                break;
              }else save = true;
            out.write("<BR><BR>ANTES DEL IF LA RESPUESTA ES " + v_rta + ", Y EL EQUALSIGNORECASE =" +!v_rta.equalsIgnoreCase("YES") );
             if(!(v_rta.trim()).equalsIgnoreCase("YES")){//cuando al ajuste no alcaza saldos
                i_LP.TBPL_PrintMsgErr(out,"No actualiza Ajuste "+consecAjus+" por "+v_rta,true,k_TBFL_CABEZA,codHtm.TBFL_PIE);
                out.write("<br>Le puso False en el if del equals al save");
                save = false;
                break;                         //deja de recorrer los otros
              }else{
              out.write("<br>Le puso True en el if del equals al save");
              	save = true;
                //va registrando los ajustes realizados
                if(linea == 1){
                  v_Ajustados += " - Consecutivo "+consecAjus+" Lineas "+linea+", ";
                }else{
                  v_Ajustados += linea+ ", ";
                }
              }
              linea++;
              v_linea = ""+linea;
            }//while interno
          }
          if (save){
            //actualizar la decisión del cliente en la tabla de ajustes
            v_rta = TBPL_ActualAccion(cod_producto,num_contrato,valores,consecAjus,Integer.parseInt(i_fnd.TBPL_getCmp(valores,"max"+consecAjus)),accion);
            if(!v_rta.equals("YES")){
              i_LP.TBPL_PrintMsgErr(out,"No actualizó accion del ajuste por "+v_rta,true,k_TBFL_CABEZA,codHtm.TBFL_PIE);
              break;
            }
            else
            {
          	  save = true;
              v_ejecutar = true;
            }
          }// if save
        }
      }else//if
        break;
    }//while

    if(save){
      if(i_sqlj.TBPBD_Commit())
      {//realizar cambios
        //if(sess.getAttribute("VALRETDEC")!=null)
        sess.removeAttribute("CONSECU");
        sess.removeAttribute("VALRETDEC");
        sess.setAttribute("MODIFICADODEC","YES");
        return true;
      }
      else
        return false;
    }else{
      i_sqlj.TBPBD_RollBack();
      return false;
     }
  }
//////////////Actualizar las decisiones por cada ajuste con sus respectivas líneas////////
  private String TBPL_ActualAccion(String cod_producto,String num_contrato,String valores,String consecAjus,int maxLin,String accion){
    int linea=1;
    String v_rta="";
    String v_linea=Integer.toString(linea);
    while(linea<=maxLin){
      if(!i_fnd.TBPL_getCmp(valores,"val"+consecAjus+v_linea).equals("")){
        v_rta=i_sqlj.TBPBD_ActAccionAjustes(cod_producto,num_contrato,Integer.parseInt(consecAjus),linea,accion);
      }
      linea++;
      v_linea=Integer.toString(linea);
    }
    return v_rta;
  }

///////////////////////////Tomar los parametros de entrada de Pipeline////////////////////////
  private void TBPL_takeParameter1(){
    String v_keys=new String("");
    if(request.getParameter("only")!=null){
      consecAjusOnly=request.getParameter("only");
    }
  }
///////////////////////////Traer  nombres y apellidos////////////////////////////////
  private void TBPBD_ContrNomApel(){
    String valCadena[]=new String[2];
    valCadena=i_sqlj.TBPBD_ContratoNomApel(cod_producto,num_contrato);
    v_nombApel=valCadena[0];
    sess.setAttribute("NOMBAPEL",v_nombApel);
  }

  /**
   * Get Servlet information
   * @return java.lang.String ~
   */
  public String getServletInfo() {
    return "TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.TBS_Decision Information";
  }
}

