package TBPKT_AJUSTES;
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;

/**
*  El objetivo de esta clase es armar cada una de las páginas del módulo de
*  ajustes, para que los servlets no sean tan pesados en código
*/

public class TBCL_LoadPage extends Object {
  TBCL_FndCmp i_fnd = new TBCL_FndCmp();
  DecimalFormat dec=new DecimalFormat("###,###,###,###,###.##");
  String k_font=new String("<font face='Verdana' size='1'>");
  TBCL_Consulta v_Consulta;
  
///////////////////////////////////constructor////////////////////////////////
  public TBCL_LoadPage() {
  }
//////Armar y visualizar las fechas desde-hasta para seleccionar los retiros vigentes////////
  public void TBPL_AjusRet_Fecha(PrintWriter out,
                                 String  fecha,
                                 String k_yearMin,
                                 String k_yearMax){
    i_fnd=new TBCL_FndCmp();
    String cmpSel=new String("");
    out.println("<center><table border='0' align='center' width='100%'>");
    out.println("<tr><td align='center'><font face='Verdana' size='1' color='#000080'>");
    out.println("<strong>VER RETIROS VIGENTES</strong></font></td></tr>");
    out.println("<tr><td>");
    out.println("<font face='Verdana' size='1' color='#000080'>");
    out.println("<strong>De</strong></font>");
    ///mes desde
    out.println("<font face='Verdana' size='1'>Mes:");
    out.println("<select name='mesD'>");
    if(!fecha.equals(""))
      cmpSel=i_fnd.TBPL_getCmp(fecha,"mesd");
    out.println(TBPL_BuildDate("mes",cmpSel,k_yearMin,k_yearMax));
    out.println("</select>");
    //año desde
    out.println("Año:<select name='anoD'>");
    if(!fecha.equals(""))
      cmpSel=i_fnd.TBPL_getCmp(fecha,"anod");
    out.println(TBPL_BuildDate("ano",cmpSel,k_yearMin,k_yearMax));
    out.println("</select>");
    out.println("</font>");
    out.println("<font face='Verdana' size='1' color='#000080'><strong>Hasta</strong></font>");
    //mes hasta
    out.println("<font face='Verdana' size='1'>Mes:");
    out.println("<select name='mesH'>");
    if(!fecha.equals(""))
      cmpSel=i_fnd.TBPL_getCmp(fecha,"mesh");
    out.println(TBPL_BuildDate("mes",cmpSel,k_yearMin,k_yearMax));
    out.println("</select>");
    //año hasta
    out.println("Año:<select name='anoH'>");
    if(!fecha.equals(""))
      cmpSel=i_fnd.TBPL_getCmp(fecha,"anoh");
    out.println(TBPL_BuildDate("ano",cmpSel,k_yearMin,k_yearMax));
    out.println("</select>");
    out.println("</font></td>");
    out.println("<td valign='center' align='center'><input type='submit' value='Buscar'></td>");
    out.println("</tr></table></center></form>");
  }
///////////////////////Armar y visualizar el Retiro a Modificar//////////////////////////
  public void TBPL_ShowRet(PrintWriter out,
                           String v_esquema,
                           String v_valorEsquema,
                           String v_fechaAjuste,
                           String valRef,
                           String valCambio){
    i_fnd=new TBCL_FndCmp();
    //armar el esquema de retiro
    out.println(TBPL_BuildEsqRet(v_esquema,v_valorEsquema,valRef,valCambio));
    out.println("</center></table><br>");
    out.println("<center><table border='0' width='100%'>");
    //fecha del ajuste
    out.println("<tr align='left' width='100%'>");
    out.println(k_font+TBPL_BuildFechAju(v_fechaAjuste));
    out.println("</tr>");
    out.println("<tr align='left' width='100%'>");
    out.println("<font face='Verdana' size='1'><strong>NOTA: </strong>Si desea el default del contrato debe seleccionar NINGUNO para todo el esquema.</font>");
    out.println("</tr>");
    //textarea para la razon del ajuste
    out.println("<tr align='left' width='100%'>");
    out.println("<td width='40%' valign='top'>");
    out.println("<font face='Verdana' size='1'><strong>RAZON DEL AJUSTE:</strong></font></td>");
    out.println("<td width='60%' valign='top'>"+k_font);
    out.println("<textarea rows='4' name='razon' cols='30'></textarea></font></td>");

    out.println("</tr>");
  }
//////////Armar y visualizar todos los retiros que se encuentran en estado vigente////////  
  public void TBPL_ShowResultAnu(PrintWriter out,
                                 String v_fecha,
                                 String v_valor,
                                 String v_esquema,
                                 String v_valorEsquema,
                                 String cons,
                                 boolean check){
    i_fnd=new TBCL_FndCmp();
    out.println("<tr width='100%' bgColor=white borderColor=silver>");
    out.println("<td valign='top' align='center' width='5%'><input type='radio' name='cons' value='"+cons+"fec"+v_fecha+"val"+v_valor+"' ");
    if(check)
      out.println("checked>");
    else
      out.println(">");

    out.println("<td valign='top' align='center' width='12%'>"+k_font+cons+"</font></td><td valign='top' align='center' width='19%'>"+k_font+v_fecha+"</font></td><td valign='top' align='right' width='14%'>"+k_font+"$"+dec.format(new Double(v_valor)).toString()+"</font></td><td valign='top' align='left' width='50%'>"+k_font+TBPL_BuildEsqRes(v_esquema,v_valorEsquema)+"</font></td>");
    out.println("</td></tr>");
  }
////////////Armar y visualizar todos los retiros en estado reversado////////////////////////  
  public void TBPL_ShowResultRev(PrintWriter out,
                                 String v_valor,
                                 String v_fechap,
                                 String v_fechafnd,
                                 String v_fechaAct,
                                 String v_consec,
                                 String v_consecMultif,
                                 String v_ii){
    out.println("<tr bgColor='white' borderColor='silver'><td valign='top' align='center' >"+k_font+v_consec+"</font></td><td valign='top' align='center' >"+k_font+v_consecMultif+"</font></td><td valign='top' align='center' >"+k_font+v_fechafnd+"</font></td><td valign='top' align='center' >"+k_font+v_fechap+"</font></td><td valign='top' align='right' >"+k_font+"$"+dec.format(new Double(v_valor)).toString()+"</font></td><td valign='top' align='left' >"+k_font+buildFecRev(v_fechafnd,v_fechaAct,v_ii)+"</font></td><td valign='top' align='center' >"+TBPL_OptionAplicar(v_ii)+"</td></tr>");
  }
//////////////////////Arma las opciones para los retiros reversados///////////////////////  
  private String TBPL_OptionAplicar(String v_ii){
    String v_opcion="";
    v_opcion="<select name='apr"+v_ii+"'>";
    v_opcion+="<option value='sd'>Sin decidir</option>";
    v_opcion+="<option value='y'>Aplicar</option>";
    v_opcion+="<option value='n'>No Aplicar</option>";
    v_opcion+="</select>";
    return v_opcion;
  }
//////////Armar y visualizar el reporte de los ajustes con decisión///////////////////////
  public void TBPL_ShowResultRep(PrintWriter out,
                                 String v_nomb,
                                 String v_contrato,
                                 String v_fecAjus,
                                 String v_valor,
                                 String v_bco,
                                 String v_fecDec,
                                 String v_consecutivo,
                                 String v_decision){
    /*Cambio para manejo de referencia unica 2009/04/01 MOS */
    v_Consulta = new TBCL_Consulta();
    String v_contrato_unif = "";
    v_Consulta.TBPL_RealizarConexion();
    v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica("", v_contrato);
    v_Consulta.TBPL_shutDown();                                 
    out.println("<tr ><td valign='top' align='center' >"+k_font+v_consecutivo+"</font></td><td valign='top' align='center'>"+k_font+v_nomb+"</font></td><td valign='top' align='center'>"+k_font+v_contrato_unif+"</font></td><td valign='top' align='center'>"+k_font+v_fecAjus+"</font></td><td valign='top' align='right' >"+k_font+"$"+dec.format(new Double(v_valor)).toString()+"</font></td><td valign='top' align='center' >"+k_font+v_bco+"</font></td><td valign='top' align='center' >"+k_font+v_fecDec+"</font></td><td valign='top' align='center' >"+k_font+v_decision+"</font></td></tr>");
  }
//////////Armar y visualizar todos los ajustes que no tengan ninguna acción///////////////
  public void TBPL_ShowResultDec(PrintWriter out,
                                 String v_cons,
                                 String v_user,
                                 String v_fec,
                                 String v_tot,
                                 int v_ii)
 {
    out.println("<tr width='100% bgColor=white borderColor=silver'>");
    if(v_ii==1)
     out.println("<td valign='top' align='center' ><input type='checkbox' name='ejec"+v_cons+"' value='y' checked></td>");
    else
      out.println("<td valign='top' align='center' width='3%'><input type='checkbox' name='ejec"+v_cons+"' value='y'></td>");
    if(Double.parseDouble(v_tot) < 0)
    {
      double total = Double.parseDouble(v_tot)*(-1);
      out.println("<td valign='top' align='center' >"+k_font+"<a href='TBPKT_AJUSTES.TBS_AmpliarAjuste?cons="+v_cons+"' style='color:blue'>Detallar</a></font></td><td valign='top' align='center'>"+k_font+v_cons+"</font></td><td valign='top' align='center'>"+k_font+v_fec+"</font></td><td valign='top' align='left' >"+k_font+v_user+"</font></td><td valign='top' align='right' ><p>"+k_font+"$("+dec.format(total)+")</p></font></td><td valign='top' align='center' >"+k_font+TBPL_buildAction(v_cons,v_tot)+"</td></tr>");
    }
    else
      out.println("<td valign='top' align='center' >"+k_font+"<a href='TBPKT_AJUSTES.TBS_AmpliarAjuste?cons="+v_cons+"' style='color:blue'>Detallar</a></font></td><td valign='top' align='center'>"+k_font+v_cons+"</font></td><td valign='top' align='center'>"+k_font+v_fec+"</font></td><td valign='top' align='left' >"+k_font+v_user+"</font></td><td valign='top' align='right' ><p>"+k_font+"$"+dec.format(new Double(v_tot)).toString()+"</p></font></td><td valign='top' align='center' >"+k_font+TBPL_buildAction(v_cons,v_tot)+"</td></tr>");
  }
/////////Armar y visualizar el detalle de cada ajuste que no tenga ninguna decisión/////////////
  public void TBPL_ShowDetalleAjs(PrintWriter out,
                                  String v_linea,
                                  String v_valor,
                                  String v_orig,
                                  String v_actual){
    out.println("<tr width='100%' bgColor=white borderColor=silver><td valign='top' align='center' width='20%'>"+k_font+v_linea+"</font></td><td valign='top' align='right' width='20%'>"+k_font+"$"+dec.format(new Double(v_valor)).toString()+"</font></td><td valign='top' align='center' width='30%'>"+k_font+v_orig+"</font></td><td valign='top' align='center' width='30%'>"+k_font+v_actual+"</font></td></tr>");
  }
//////////Armar y visualizar las tres acciones a escoger para cada ajuste/////////////////
  private String TBPL_buildAction(String v_cons,String v_tot){
    String v_opcion="";
    v_opcion="<select name='dec"+v_cons+"'>";
    if(v_tot.indexOf("-")!=-1)//cuando el valor del ajuste es negativo
      v_opcion+="<option value='SAC003'>El Cliente Paga</option>";
    else//cuando el valor del ajuste es positivo
      v_opcion+="<option value='SAC001'>Girar al Cliente</option>";
    v_opcion+="<option value='SAC002'>Ajustar al Contrato</option>";
    v_opcion+="</select>";
    return v_opcion;
  }
/////////////Armar para visualizar en una sola linea el esquema del retiro/////////////////
  private String TBPL_BuildEsqRes(String v_esquema,
                                  String v_valorEsquema){
    String v_esq    = new String("");
    String v_opcion = new String("<select>");
    v_esq           = i_fnd.TBPL_getCmp(v_esquema,i_fnd.TBPL_getCmp(v_valorEsquema,"smo").toLowerCase());
    v_opcion       += "<option>MET.ORDEN: "+v_esq+"</option>";
    v_esq           = i_fnd.TBPL_getCmp(v_esquema,i_fnd.TBPL_getCmp(v_valorEsquema,"smb").toLowerCase());
    v_opcion       += "<option>MET.BENEFICIO: "+v_esq+"</option>";
    v_esq           = i_fnd.TBPL_getCmp(v_esquema,i_fnd.TBPL_getCmp(v_valorEsquema,"smc").toLowerCase());
    v_opcion       += "<option>MET.CUENTA: "+v_esq+"</option>";
    v_esq           = i_fnd.TBPL_getCmp(v_esquema,i_fnd.TBPL_getCmp(v_valorEsquema,"snr").toLowerCase());
    v_opcion       += "<option>NATURALEZA RETIRO: "+v_esq+"</option>";
    v_esq           = i_fnd.TBPL_getCmp(v_valorEsquema,"srn");
    if(v_esq.equals("null") || v_esq.equals(""))
       v_esq="ninguno";
    v_opcion += "<option>RESPETAR NATURALEZA: "+v_esq+"</option>";
    v_opcion += "<option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>";
    v_opcion += "</select>";
    return v_opcion;
  }
/////////Armar y visualizar el esquema del retiro a modificar/////////////////////////////
  private String TBPL_BuildEsqRet(String v_esquema,
                                  String v_valorEsquema,
                                  String vRef,String vCambio){
    String v_opcion=new String("");
    //ESQUEMA DE CAMBIO
    v_opcion="<tr bgColor=white borderColor=silver><td>"+k_font+"<strong>ORDEN:</strong></font></td>";
    v_opcion+="<td>"+k_font+"<select name='smo'>";
    v_opcion+=TBPL_BuildOptEsq(v_esquema,"smo",i_fnd.TBPL_getCmp(v_valorEsquema,"smo").substring(3),vRef,vCambio);
    v_opcion+="</select></font></td></tr>";


    v_opcion+="<tr bgColor=white borderColor=silver><td>"+k_font+"<strong>BENEFICIO:</strong></font></td>";
    v_opcion+="<td>"+k_font+"<select name='smb'>";
    v_opcion+=TBPL_BuildOptEsq(v_esquema,"smb",i_fnd.TBPL_getCmp(v_valorEsquema,"smb").substring(3),vRef,vCambio);
    v_opcion+="</select></font></td></tr>";

    v_opcion+="<tr bgColor=white borderColor=silver><td>"+k_font+"<strong>PENALIZACION:</strong></font></td>";
    v_opcion+="<td>"+k_font+"<select name='smp'>";
    v_opcion+=TBPL_BuildOptEsq(v_esquema,"smp",i_fnd.TBPL_getCmp(v_valorEsquema,"smp").substring(3),vRef,vCambio);
    v_opcion+="</select></font></td></tr>";

    v_opcion+="<tr bgColor=white borderColor=silver><td>"+k_font+"<strong>CUENTA:</strong></font></td>";
    v_opcion+="<td>"+k_font+"<select name='smc'>";
    v_opcion+=TBPL_BuildOptEsq(v_esquema,"smc",i_fnd.TBPL_getCmp(v_valorEsquema,"smc").substring(3),vRef,vCambio);
    v_opcion+="</select></font></td></tr>";

    v_opcion+="<tr bgColor=white borderColor=silver><td>"+k_font+"<strong>NATURALEZA:</strong></font></td>";
    v_opcion+="<td>"+k_font+"<select name='snr'>";
    v_opcion+=TBPL_BuildOptEsq(v_esquema,"snr",i_fnd.TBPL_getCmp(v_valorEsquema,"snr").substring(3),vRef,vCambio);
    v_opcion+="</select></font></td></tr>";

    v_opcion+="<tr bgColor=white borderColor=silver><td>"+k_font+"<strong>RESPETAR NATURALEZA:</strong></font></td>";
    v_opcion+="<td>"+k_font+"<select name='srn'>";
    String valNR=i_fnd.TBPL_getCmp(v_valorEsquema,"srn");
    if(valNR.equalsIgnoreCase("N")){
      v_opcion+="<option value='S'>SI</option>";
      v_opcion+="<option selected value='N'>NO</option>";
      v_opcion+="<option value=''>ninguno</option>";
    }else{
      if(valNR.equalsIgnoreCase("S")){
        v_opcion+="<option selected value='S'>SI</option>";
        v_opcion+="<option value='N'>NO</option>";
        v_opcion+="<option value=''>ninguno</option>";
      }else
        if(valNR.equals("") || valNR.equals("null")){
          v_opcion+="<option value='S'>SI</option>";
          v_opcion+="<option value='N'>NO</option>";
          v_opcion+="<option selected value=''>ninguno</option>";
        }
    }
    v_opcion+="</select></font></td></tr>";
    return v_opcion;
  }
/////////////////Armar las opciones de cada esquema del retiro////////////////////////  
  private String TBPL_BuildOptEsq(String v_esquema,
                                  String k_prf,
                                  String k_valor,
                                  String vRef,
                                  String vCambio){
    String v_opcion=new String("");
    int ii=0;
    String k_val="";
    while(true){
      String v_ii=new String(Integer.toString(ii));
      while(v_ii.length()<3)
        v_ii=new String("0"+v_ii);
      if(!i_fnd.TBPL_getCmp(v_esquema,k_prf+v_ii).equals("")){
        String v_valorC=i_fnd.TBPL_getCmp(v_esquema,k_prf+v_ii);
        if(k_prf.equalsIgnoreCase("smo"))// && (k_valor.equalsIgnoreCase("001") || v_valorC.equalsIgnoreCase("002")))
          k_val="aportes seleccionados";
        if(k_valor.equals(v_ii)){//El seleccionado por la tabla
          if(!k_val.equals(v_valorC)){
            v_opcion+="<option selected value='"+k_prf.toUpperCase()+v_ii+"'>"+v_valorC+"</option>";
          }
        }
        else{
          if(!k_val.equals(v_valorC)){
              //mirar las opciones de cambio en el esquema de retiro
             if(k_prf.equalsIgnoreCase("smb") || k_prf.equalsIgnoreCase("smp") || k_prf.equalsIgnoreCase("smc")){
                if(i_fnd.TBPL_getCmp(vCambio,k_prf).equalsIgnoreCase("S")){//genera todas las opociones
                  v_opcion+="<option value='"+k_prf.toUpperCase()+v_ii+"'>"+v_valorC+"</option>";
                }else{//genera solo no aplica
                   if(i_fnd.TBPL_getCmp(vRef,k_prf+v_ii).equals("null") || v_ii.equals("000"))
                     v_opcion+="<option value='"+k_prf.toUpperCase()+v_ii+"'>"+v_valorC+"</option>";
                }
             }else{
                if(i_fnd.TBPL_getCmp(vCambio,k_prf).indexOf(i_fnd.TBPL_getCmp(vRef,k_prf+v_ii))!=-1)
                  v_opcion+="<option value='"+k_prf.toUpperCase()+v_ii+"'>"+v_valorC+"</option>";
             }
          }
        }
      }else{
        break;
      }
      ii++;
    }
    return v_opcion;
  }
//////////////Armar y visualizar en letras(Fecha del Retiro-Fecha de Hoy) las fechas en que se desea ajustar//////
  private String buildFecRev(String fechafnd,
                             String fechaAct,
                             String v_ii){
    String v_optFec=new String("");
    v_optFec="<input type='radio' name='fecAp"+v_ii+"' checked value='"+fechafnd+"'>Del Retiro";
    v_optFec+="<br>";
    v_optFec+="<input type='radio' name='fecAp"+v_ii+"' value='"+fechaAct+"'>A Hoy";
    return v_optFec;
  }
///Armar y visualizar en formato fecha(aaaa-mm-dd / aaaa-mm-dd) las fechas del ajuste////////
  public String TBPL_BuildFechAju(String v_fechaAjuste){
    String v_optFec=new String("");
    v_optFec="<td width='40%'>"+k_font+"<strong>AJUSTAR A FECHA</strong></font></td>";
    v_optFec+="<td width='60%'>"+k_font;
    v_optFec+="<input type='radio' name='fechaAjs' checked value='"+i_fnd.TBPL_getCmp(v_fechaAjuste,"fecha1")+"'>"+i_fnd.TBPL_getCmp(v_fechaAjuste,"fecha1");
    v_optFec+="&nbsp;&nbsp;&nbsp;&nbsp;";
    v_optFec+="<input type='radio' name='fechaAjs' value='"+i_fnd.TBPL_getCmp(v_fechaAjuste,"fecha2")+"'>"+i_fnd.TBPL_getCmp(v_fechaAjuste,"fecha2");
    v_optFec+="<br>(Fecha del retiro)&nbsp;&nbsp;&nbsp;&nbsp;(Fecha a Hoy)";
    v_optFec+="</font></td>";
    return v_optFec;
  }
///////////////Armar las opciones de las fechas desde-hasta para los retiros vigentes//////
  private String TBPL_BuildDate(String par,
                                String sel,
                                String k_yearMin,
                                String k_yearMax){
    String opcion=new String("");
    if(par.equals("mes")){
      Hashtable dicMes=new Hashtable();
      dicMes.put("01","Ene_JAN");dicMes.put("02","Feb_FEB");dicMes.put("03","Mar_MAR");dicMes.put("04","Abr_APR");
      dicMes.put("05","May_MAY");dicMes.put("06","Jun_JUN");dicMes.put("07","Jul_JUL");dicMes.put("08","Ago_AUG");
      dicMes.put("09","Sep_SEP");dicMes.put("10","Oct_OCT");dicMes.put("11","Nov_NOV");dicMes.put("12","Dic_DEC");
      for(int ii=1;ii<13;ii++){
        String jj=Integer.toString(ii);
        if(ii<10)
          jj="0"+jj;
        String mes=(String)dicMes.get(jj);
        if(mes.substring(mes.indexOf("_")+1).equalsIgnoreCase(sel))
          opcion+="<option selected value='"+mes.substring(mes.indexOf("_")+1)+"'>"+mes.substring(0,mes.indexOf("_"))+"</option>";
         else
          opcion+="<option value='"+mes.substring(mes.indexOf("_")+1)+"'>"+mes.substring(0,mes.indexOf("_"))+"</option>";
      }
    }
    if(par.equals("ano")){
      int max=1990;
      int min=2000;
      try{
        min=Integer.parseInt(k_yearMin);
        max=Integer.parseInt(k_yearMax);
        for(int ii=min;ii<=max;ii++){
          String jj=Integer.toString(ii);
          if(jj.equals(sel))
            opcion+="<option selected value='"+jj+"'>"+jj+"</option>";
          else
            opcion+="<option value='"+jj+"'>"+jj+"</option>";
        }
     }catch(NumberFormatException e){}
     catch(StringIndexOutOfBoundsException e1){}

    }
    return opcion;
  }
////////////////Armar y visualizar el encabezado para cada página////////////////////////////  
  public void TBPL_Encabezado(PrintWriter out,
                              String cabeza,
                              String cod_producto,
                              String num_contrato,
                              String v_nombApel,
                              String v_tr){
    out.println(cabeza);
    String v_contrato_unif = "";
    
    v_Consulta = new TBCL_Consulta();
    v_Consulta.TBPL_RealizarConexion();
    v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica("", num_contrato);
    v_Consulta.TBPL_shutDown();     
    out.println("<center><table border='0' align='center' width='80%'>");
    out.println("<tr><td align='center'><font face='Verdana' size='1'><strong>Producto: </strong>"+cod_producto+"&nbsp;&nbsp;<strong>Contrato: </strong>"+v_contrato_unif+"</font></td></tr>");
    out.println("<tr><td align='center'><font face='Verdana' size='1'>"+v_nombApel.toUpperCase()+"</font></td></tr>");
    if(!v_tr.equals(""))
      out.println(v_tr);
    out.println("</table></center>");
    out.println("<hr width='100%'>");  
  }
///////////Armar y visualizar ERROR en la entrada de parámetros de PipeLine///////////////  
  public void TBPL_ErrParamEntr(PrintWriter out,
                                String k_cabeza,
                                String v_pie){
     out.println(k_cabeza);
     out.println("<hr width='100%'><center><table border='0' align='center' width='100%'>");
     out.println("<tr>");
     out.println("<td widht='100%' height='10' valign='center'>");
     out.println("<center><font face='Arial Black' size='2' color='#000080'>");
     out.println("ERROR EN LOS PARAMETROS SESSION CERRADA POR FAVOR VUELVA A INTENTAR</font></center>");
     out.println("</td></tr></table></center><hr width='100%'>");
     out.println(v_pie);
     out.close();
  }
//////Armar y visualizar cualquier error en los procesos del módulo ajustes//////////////  
  public void TBPL_PrintMsgErr(PrintWriter out,
                               String msg,
                               boolean cabeza,
                               String v_cabeza,
                               String pie){
    if(cabeza)
      out.println(v_cabeza);
    //TBPL_scriptMsgErr(out,msg);
    out.println("<center><table border='0' align='center' width='70%'>");
    out.println("<p><font face='Verdana' size='2' color='#324395'>"+msg.toUpperCase()+"</font></p>");
    out.println("</table></center>");
/*
    if(cabeza){
      out.println("<center><input type='button' value='Aceptar' Onclick=window.location='/taxbenefit/Ajuste.html';></center>");
    }
    */
    out.println(pie);
    out.close();
  }
//////Armar y visualizar la página de éxito de cualquier proceso del módulo ajustes
  public void TBPL_pageOk(PrintWriter out,
                          String msgOk,
                          String k_cabeza,
                          String k_pie,
                          String cod_producto,
                          String num_contrato,
                          String v_nombApel,
                          String otroBoton){
    out.println(k_cabeza);
    /*Cambio para manejo de referencia unica 2009/04/01 MOS */
    String v_contrato_unif = "";
    v_Consulta= new TBCL_Consulta();
    v_Consulta.TBPL_RealizarConexion();
    v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(cod_producto, num_contrato);
    v_Consulta.TBPL_shutDown();
        
    out.println("<center><table border='0' align='center' width='70%'>");
    out.println("<tr><td align='center'><font face='Verdana' size='1'><strong>Producto: "+cod_producto+"&nbsp;&nbsp;Contrato: "+v_contrato_unif+"</strong></font></td></tr>");
    out.println("<tr><td align='center'><font face='Verdana' size='1'><strong>"+v_nombApel.toUpperCase()+"</strong></font></td></tr>");
    out.println("</center></table>");
    out.println("<hr width='100%'>");
    out.println("<center><table border='0' align='center' width='100%'>");
    out.println("<p><font face='Verdana' size='2' color='#324395'><strong>"+msgOk+"</strong></font></p>");
    out.println("</table></center>");

    if(!otroBoton.equals(""))
//       out.println("<center><input type='button' value='Aceptar' Onclick=window.location='/taxbenefit/Ajuste.html';>"+
      out.println("<input type='button' value='Decision' Onclick=window.location='"+otroBoton+"';></center>");
//    else
//      out.println("<center><input type='button' value='Aceptar' Onclick=window.location='/taxbenefit/Ajuste.html';></center>");
    out.println(k_pie);
    //    out.close();
  }
//////Armar y sacar una ventana un mensaje de error
  public void TBPL_scriptMsgErr(PrintWriter out,
                                String msgErr){
    out.println("<SCRIPT LANGUAJE='JavaScript'>");
    out.println("<!-- HIDE FROM OTHER BROWSERS");
    out.println("alert('¡"+msgErr+"!');");
    out.println("// STOP HIDING FROM OTHER BROWSERS -->");
    out.println("</SCRIPT>");
  }

}//fin

