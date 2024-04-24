package TBPKT_AJUSTES;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import java.text.DecimalFormat;
import TBPKT_MODULO_APORTES.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;
import java.sql.*;
import java.io.*;

/**
* El objetivo de este servlet es visualizar todos los retiros en estado vigente dentro de
* una fecha escogida por el usuario.
*/
 

public class TBS_SelectRetiro extends HttpServlet{

  boolean botonAcep  = false;
  STBCL_GenerarBaseHTMLII codHtm;
  TBCL_LoadPage i_LP;
  SQL_AJUSTE i_sqlj;
  HttpServletRequest request;
  HttpServletResponse response;
  HttpSession sess;
  PrintWriter out;
  TBCL_FndCmp i_fnd   = new TBCL_FndCmp();
  String cod_producto = new String("");
  boolean contCancel  = false;
  boolean contNoExis   = false;
  String num_contrato = new String("");
  String v_nombApel   = new String("");
  String v_yearMin    = new String("");
  String v_yearMax    = new String("");
  String k_cabeza     = new String("");
  String esquema      = new String("");//opcional
  String cadena       = new String("");
/////////////////////////////////Llamado Principal/////////////////////////////////////////
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
    cod_producto  = num_contrato = v_nombApel = v_yearMin = v_yearMax="";
    contCancel    = false;
    contNoExis   = false;
    botonAcep     = false;
    this.request  = request;
    this.response = response;
    i_LP          = new TBCL_LoadPage();
    i_fnd         = new TBCL_FndCmp();
    i_sqlj        = new SQL_AJUSTE();
    esquema       = new String("");
    response.setContentType("text/html");
    out           = new PrintWriter (response.getOutputStream());
    sess          = request.getSession(true);
    sess.setMaxInactiveInterval(3600);
    cadena        = request.getParameter("cadena");
    k_cabeza      = codHtm.TBFL_CABEZA("Modificar Retiros","Modificar Retiros","TBPKT_AJUSTES.TBS_SelectRetiro","",true);
    TBPL_CleanSession();
    TBPL_takeParameter();//tomar parámetros de entrada
    if(!cod_producto.equals("") && !num_contrato.equals(""))
    {
      if(i_sqlj.TBPBD_ConexionBD())
       {
         //conexión con la BD
         if(sess.getAttribute("NOMBAPEL")==null)
          {
            //tomar nombres y apellidos
            TBPBD_ContrNomApel();
          }
         else
           v_nombApel = (String)sess.getAttribute("NOMBAPEL");
         if(!contCancel)
          {
            if(!contNoExis)
            {
             if(sess.getAttribute("ESQUEMA")==null)
             {

              TBPL_FindRef();
              //Encontrar descripciones de  las referencias
             }
             else{

              esquema = (String)sess.getAttribute("ESQUEMA");
             }

             TBPL_BuildPage();//Construccion pagina final*/
            }
            else
            {
              i_LP.TBPL_PrintMsgErr(out,"Lo sentimos pero el contrato no existe en el sistema.",true,k_cabeza,codHtm.TBFL_PIE);
            }
          }
         else
          {
            i_LP.TBPL_PrintMsgErr(out,"Lo sentimos pero el contrato se encuentra cancelado",true,k_cabeza,codHtm.TBFL_PIE);
          }
       }
      else
        //error conexion BD
        i_LP.TBPL_PrintMsgErr(out,"Error en la conexion con la Base de Datos",true,k_cabeza,codHtm.TBFL_PIE);
    }
    i_sqlj.TBPBD_CerrarConexionBD();
}
///////////////////////Limpia Variables de Session que hay en memoria///////////////////////////////////
  private void TBPL_CleanSession(){
    if(sess.getAttribute("NOMBAPEL")!=null)      sess.removeAttribute("NOMBAPEL");
    if(sess.getAttribute("VALRETANU")!=null)     sess.removeAttribute("VALRETANU");
    if(sess.getAttribute("MODIFICADOANU")!=null) sess.removeAttribute("MODIFICADOANU");
    if(sess.getAttribute("FECHA")!=null)         sess.removeAttribute("FECHA");
    if(sess.getAttribute("KEYS")!=null)          sess.removeAttribute("KEYS");
    if(sess.getAttribute("VALRETSEL")!=null)     sess.removeAttribute("VALRETSEL");
  }
///////////////Construcción de la página de salida con  los retiros vigentes/////////////////
private void TBPL_BuildPage()
  {
    boolean findBD = false;
    Connection v_conexion_taxb  =   null;
    //primer parte encabezado
    i_LP.TBPL_Encabezado(out,k_cabeza,cod_producto,num_contrato,v_nombApel,"");
    out.println("<input type='hidden' id='cadena' name='cadena' value='"+cadena+"'>");
    //Armar selección de fechas desde - hasta
    String v_fecha = "";
    /*if(sess.getAttribute("YEARMINMAX")==null)
      TBPBD_yearRetMinMax();
    else
     {
      v_yearMin=i_fnd.TBPL_getCmp((String)sess.getAttribute("YEARMINMAX"),"yearmin");
      v_yearMax=i_fnd.TBPL_getCmp((String)sess.getAttribute("YEARMINMAX"),"yearmax");
     }*/

   try
    {
      //CONEXION
      v_conexion_taxb   =   DataSourceWrapper.getInstance().getConnection();
      CallableStatement callYear = v_conexion_taxb.prepareCall("{call TBFBD_yearRetMinMax(?,?,?,?)}");
      callYear.registerOutParameter(3,Types.VARCHAR);
      callYear.registerOutParameter(4,Types.VARCHAR);
      callYear.setString(1,cod_producto);
      callYear.setString(2,num_contrato);
      callYear.execute();
      v_yearMin=callYear.getString(3);
      v_yearMax =callYear.getString(4);
      callYear.close();
    }
   catch(Exception e)
    {
      i_LP.TBPL_scriptMsgErr(out,"Ocurrio error en la base de datos "+e);
      out.close();
    }
    finally{
        try{
            DataSourceWrapper.closeConnection(v_conexion_taxb);            
        }catch(SQLException sqlEx){
            out.println(sqlEx.toString());
        }
    }

    if(request.getParameter("mesD")!=null && request.getParameter("anoD")!=null &&
       request.getParameter("mesH")!=null && request.getParameter("anoH")!=null   )
      {
         v_fecha="<diad='01' mesd='"+request.getParameter("mesD")+"' "+"anod='"+request.getParameter("anoD")+"' "+
                 "diah='"+TBPL_DayMonth(request.getParameter("mesH"))+"' mesh='"+request.getParameter("mesH")+"' "+
                 "anoH='"+request.getParameter("anoH")+"'> ";
         findBD=true;
         sess.removeAttribute("FECHA");
         sess.setAttribute("FECHA", v_fecha );
      }
    else

      if(sess.getAttribute("FECHA")!=null)
        v_fecha=(String)sess.getAttribute("FECHA");
      else
       {
          if(!v_yearMin.trim().equals("") && !v_yearMax.trim().equals(""))
          {
             v_fecha="<diad='01' mesd='JAN' anod='"+v_yearMin+"' "+
                     "diah='31' mesh='JAN' anoh='"+v_yearMin+"'>";
          }
       }

     if(!v_fecha.trim().equals(""))
      {

         i_LP.TBPL_AjusRet_Fecha(out,v_fecha,v_yearMin,v_yearMax);//llamar proceso de armar
      }                                                     //selección de fechas
     else
      {

         i_LP.TBPL_PrintMsgErr(out,"<font face='Verdana' size='2' color='#000000'><center><strong>NO EXISTEN RETIROS VIGENTES PARA EL CONTRATO ELEGIDO.</strong></center></font>",false,k_cabeza,codHtm.TBFL_PIE);
        return;
      }
    
      //mostrar resultado
    out.println("<FORM  METHOD=Post NAME=codigo ACTION=/Servlets/TBPKT_AJUSTES.TBS_Modificar_Retiros>");
    out.println("<input type='hidden' id='cadena' name='cadena' value='"+cadena+"'>");
    out.println("<center><table bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 align='center' width='100%'>");
    out.println("<tr bgColor=white borderColor=silver ><td align='center'  class=\"td11\" style='border: thin solid'></td><td align='center' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Transacción&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong></font></td>");
    out.println("<td align='center' class=\"td11\"  style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fecha&nbsp;&nbsp;Efectiva&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong></font></td>");
    out.println("<td align='center'  class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Valor&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong></font></td>");
    out.println("<td align='center' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Esquema&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Retiros&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong></font></td></tr>");
     //tomar la información de los retiros vigentes de la variable de session si existe o
    //va a la BD.
    if(findBD)
    {
     TBPBD_FindAnu();//traer los retiros vigentes de la Base de datos
    }
   else
     if(sess.getAttribute("VALRETANU")!=null && sess.getAttribute("MODIFICADOANU")==null)
        TBPL_showAnu((String)sess.getAttribute("VALRETANU"));//mostrar inf. de retiros vigentes
     else
     {
      if(sess.getAttribute("MODIFICADOANU")!=null)
      {
          sess.removeAttribute("MODIFICADOANU");
          TBPBD_FindAnu();
      }
    }
   out.println("</table></center>");
   //boton regreso
   out.println("<center><table border='0' width='34%'><tr>");
   if(botonAcep)
      out.println("<td width='17%' align='center'><input type='submit' value='Aceptar'></td>");
   out.println("<td align='center' width='17%'><input type='button' value='Regresar' ONCLICK=history.go(-1);></td>");
   out.println("</tr></table></center>");
   out.println(codHtm.TBFL_PIE);
   out.close();
}
////////////////////////////tomar parámetros de entrada de PipeLine//////////////////////////
private void TBPL_takeParameter()
{
    cod_producto  = num_contrato = "";
    String v_keys = new String("");
    /*if(request.getParameter("num_contrato")!=null)
      TBPL_CleanSession();*/
    TBCL_ParamEntr param = new TBCL_ParamEntr();
    v_keys               = param.TBCL_ParamEntr(request,sess,out);
    if(!v_keys.equals(""))
     {
      cod_producto       = i_fnd.TBPL_getCmp(v_keys,"producto");
      num_contrato       = i_fnd.TBPL_getCmp(v_keys,"contrato");
     }
    else
      i_LP.TBPL_ErrParamEntr(out,k_cabeza,codHtm.TBFL_PIE);
}
/////////////////////////////////////Traer Nombres y Apellidos/////////////////////////////
private void TBPBD_ContrNomApel()
  {
    String valCadena[]=new String[2];
    valCadena  = i_sqlj.TBPBD_ContratoNomApel(cod_producto,num_contrato);
    v_nombApel = valCadena[0];
    if(valCadena[1].equalsIgnoreCase("SI"))
      contCancel = true;//CONTRATO CANCELADO
    else if(valCadena[0].equalsIgnoreCase("No existe"))
         contNoExis = true;
       else
       {
        sess.removeAttribute("NOMBAPEL");
        sess.setAttribute("NOMBAPEL",v_nombApel);
        contCancel = false;
        contNoExis  = false;
       }
  }
///////////////////////////Calcular año Mínimo y máximo de los retiros//////////////////////
private void TBPBD_yearRetMinMax()
{
    v_yearMin      = new String("");
    v_yearMax      = new String("");
    String v_years = new String("");
    Connection v_conexion_taxb  =   null;
    try
    {
      //CONEXION
      v_conexion_taxb =   DataSourceWrapper.getInstance().getConnection();
      CallableStatement callYear = v_conexion_taxb.prepareCall("{? = call TBFBD_yearRetMinMax(?,?)}");
      callYear.registerOutParameter(1,Types.VARCHAR);
      callYear.setString(2,cod_producto);
      callYear.setString(3,num_contrato);
      callYear.execute();
      v_years=callYear.getString(1);
      callYear.close();
    }
   catch(Exception e)
    {
      i_LP.TBPL_scriptMsgErr(out,"Ocurrio error en la base de datos "+e);
      out.close();
    }
    finally{
        try{
            DataSourceWrapper.closeConnection(v_conexion_taxb);
            
        }catch(SQLException sqlEx){
            out.println(sqlEx.toString());
        }
    }
  v_yearMin = i_fnd.TBPL_getCmp(v_years,"yearmin");
  v_yearMax = i_fnd.TBPL_getCmp(v_years,"yearmax");
  sess.removeAttribute("YEARMINMAX");
  sess.setAttribute("YEARMINMAX","<"+v_years+">");
}
/////Encontrar todos los retiros en estado vigente dentro del rango de fecha escogido//////
private void TBPBD_FindAnu()
{
    String v_fecha = (String)sess.getAttribute("FECHA");
    String valores = new String("");
    String fecMin1 = i_fnd.TBPL_getCmp(v_fecha,"diad")+"-"+i_fnd.TBPL_getCmp(v_fecha,"mesd")+"-"+i_fnd.TBPL_getCmp(v_fecha,"anod");
    String fecMax1 = i_fnd.TBPL_getCmp(v_fecha,"diah")+"-"+i_fnd.TBPL_getCmp(v_fecha,"mesh")+"-"+i_fnd.TBPL_getCmp(v_fecha,"anoh");
    //llamar procedimiento que retorne toda la información necesaria de los retiros vigente
    valores        = i_sqlj.TBPBD_SelAllRetAnu(num_contrato,cod_producto,"SER006",fecMax1,fecMin1);
    if(valores.equals("<>"))
    {
      i_LP.TBPL_scriptMsgErr(out,"No existen retiros vigentes desde "+fecMin1+" Hasta "+fecMax1);
    }
   else
    {
      sess.removeAttribute("VALRETANU");
      sess.setAttribute("VALRETANU",valores);
      TBPL_showAnu(valores);
      //mostrar la información retornada
    }
}
////////////////Visualizar la información necesaria de los retiros vigentes///////////////
private void TBPL_showAnu(String valores)
{
     String v_const         = "";
     String v_fechafnd      = "";
     String v_valor         = "";
     String v_valor_esquema = "";
     int ii                 = 1;
     int hh                 = 0;
     while(true)
     {
        boolean check = false;
        String v_ii   = Integer.toString(ii);
        if(!i_fnd.TBPL_getCmp(valores,"fechae"+v_ii).equals(""))
        {
          v_const         = i_fnd.TBPL_getCmp(valores,"const"+v_ii);
          v_fechafnd      = i_fnd.TBPL_getCmp(valores,"fechae"+v_ii);
          v_valor         = i_fnd.TBPL_getCmp(valores,"valor"+v_ii);
          v_valor_esquema = i_fnd.TBPL_getCmp(valores,"esquema"+v_ii);
          if(hh==0)
            check = true;
          else
            check = false;
          i_LP.TBPL_ShowResultAnu(out,v_fechafnd,v_valor,esquema,v_valor_esquema,v_const,check);
          hh++;
        }
        else
          break;
        ii++;
     }
    botonAcep = true;
}
////////////////Construir Diccionario con el día máximo de los meses
private String TBPL_DayMonth(String k_mes)
{
    String v_day          = new String("01");
    Hashtable dicDayMonth = new Hashtable();
    dicDayMonth.put("JAN","31");dicDayMonth.put("FEB","28");dicDayMonth.put("MAR","31");
    dicDayMonth.put("APR","30");dicDayMonth.put("MAY","31");dicDayMonth.put("JUN","30");
    dicDayMonth.put("JUL","31");dicDayMonth.put("AUG","31");dicDayMonth.put("SEP","30");
    dicDayMonth.put("OCT","31");dicDayMonth.put("NOV","30");dicDayMonth.put("DEC","31");
    if(dicDayMonth.get(k_mes)!=null)
      v_day = (String)dicDayMonth.get(k_mes);
    return v_day;
}
/////////////Encontrar las descripciones de la tabla referencias del esquema de retiro////
private void TBPL_FindRef()
{
    String v_ref[] = new String[2];
    String v_sess  = new String("");
    String v_valor = new String("");
    v_ref          = i_sqlj.TBPBD_BuildRef("STV","1");
    v_sess         = v_ref[0];
    sess.removeAttribute("TIPO");
    sess.setAttribute("TIPO","<"+v_sess+">");
    v_ref          = i_sqlj.TBPBD_BuildRef("SER","1");
    v_sess         = v_ref[0];
    sess.removeAttribute("ESTADO");
    sess.setAttribute("ESTADO","<"+v_sess+">");
    v_ref          = i_sqlj.TBPBD_BuildRef("SMB","000");
    v_sess         = v_ref[0];
    v_valor        = v_ref[1];
    v_ref          = i_sqlj.TBPBD_BuildRef("SMO","000");
    v_sess        += v_ref[0];
    v_valor       += v_ref[1];
    v_ref          = i_sqlj.TBPBD_BuildRef("SMP","000");
    v_sess        += v_ref[0];
    v_valor       += v_ref[1];
    v_ref          = i_sqlj.TBPBD_BuildRef("SNR","000");
    v_sess        += v_ref[0];
    v_valor       += v_ref[1];
    v_ref          = i_sqlj.TBPBD_BuildRef("SMC","000");
    v_sess        += v_ref[0];
    v_valor       += v_ref[1];
    esquema="<"+v_sess+">";
    sess.removeAttribute("ESQUEMA");
    sess.setAttribute("ESQUEMA","<"+v_sess+">");
    sess.removeAttribute("VALORREF");
    sess.setAttribute("VALORREF",v_valor);
}
///////////////////////////////////////////////////////////////////////////////////////////
public String getServletInfo()
 {
    return "TBPKT_AJUSTES.TBCL_AjusteRetiro Information";
  }
///////////////////////////////////////////////////////////////////////////////////////////
}//fin

