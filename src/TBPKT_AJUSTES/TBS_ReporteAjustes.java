
package TBPKT_AJUSTES;
import javax.servlet.http.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import javax.servlet.*;
import java.util.*;
import java.io.*;

/**
*  El objetivo de este servlet es visualizar toda la información con los ajustes que tengan
*  alguna acción.
*/


public class TBS_ReporteAjustes extends HttpServlet{
  STBCL_GenerarBaseHTMLII codHtm;
  HttpServletRequest request;
  HttpServletResponse response;
  HttpSession sess;
  TBCL_LoadPage i_LP;
  SQL_AJUSTE i_sqlj;
  TBCL_FndCmp i_fnd   = new TBCL_FndCmp();
  String k_cabeza     = new String("");
  boolean v_buscar    = false;
  String cadena       = new String("");
  String v_opcion     = new String("");
  String v_fecDesde   = new String("");
  String v_fecHasta   = new String("");
  String v_contDesde  = new String("");
  String v_contHasta  = new String("");
  Hashtable dicOpcion = new Hashtable();
  PrintWriter out;
/////////////////////////////////////inicialización del servlet//////////////////////////////
public void init(ServletConfig config) throws ServletException
   {
    super.init(config);
   }
////////////////////////////////////Llamado Principal//////////////////////////////////////
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
    //INICIO seguridad
    String parametros[]         = new String[8];
    cadena                      = request.getParameter("cadena");
    String ip_tax               = request.getRemoteAddr();
    TBCL_Seguridad Seguridad    = new TBCL_Seguridad();
    parametros                  = Seguridad.TBFL_Seguridad(cadena,out,ip_tax);
    //FIN seguridad
    v_buscar      = false;
    v_opcion      = new String("");
    v_fecDesde    = new String("");
    v_fecHasta    = new String("");
    v_contDesde   = new String("");
    v_contHasta   = new String("");
    this.request  = request;
    this.response = response;
    i_LP          = new TBCL_LoadPage();
    i_fnd         = new TBCL_FndCmp();
    i_sqlj        = new SQL_AJUSTE();
    response.setContentType("text/html");
    out           = new PrintWriter (response.getOutputStream());
    sess          = request.getSession(true);
    k_cabeza      = codHtm.TBFL_CABEZA("Reporte de Ajustes","Reporte de Ajustes","TBPKT_AJUSTES.TBS_ReporteAjustes","",true,"moduloparametro.js","return checkrequired(this)");
    //armar diccionario con posibles decisiones
    buildOpcion();
    //contruir página final
    buildPage();
    i_sqlj.TBPBD_CerrarConexionBD();
}
//construcción de un diccionario con las posibles opciones de lo que desea reportar el usuario
private void buildOpcion()
{
    dicOpcion.put("1","1");        //reportar sólo Girar al cliente
    dicOpcion.put("2","2");       //reportar sólo Ajustar Contrato
    dicOpcion.put("3","3");      //reportar sólo El Cliente Paga
    dicOpcion.put("123","4");   //reportar todas las acciones
    dicOpcion.put("12","5");   //reportar Girar al Cliente y Ajustar Contrato
    dicOpcion.put("13","6");  //reportar Girar al Cliente y El Cliente Paga
    dicOpcion.put("23","7"); //reportar Ajustar Contrato y El Cliente Paga
}
//////////////////////////////Construir página de salida/////////////////////////////////////
private void buildPage()
{
    out.println(k_cabeza);
    out.println("<input type='hidden' id='cadena' name='cadena' value='"+cadena+"'>");
    out.println("<center><table border='0' align='center' width='100%'>");
    out.println("<tr align='center'><font face='Verdana' size='1'><strong>VER AJUSTES CON:</strong></font></tr>");
    //tomar parámetro de la opción del reporte
    takeParameter();
    out.println("</table></center>");
    out.println("<center><table border='0' align='center' width='100%'>");
    out.println("<tr align='center'><font face='Verdana' size='1'><strong>Nota: </strong>Si quiere seleccionar todos los contratos deje en blanco la información del contrato Inicial y Final.</font></tr>");
    out.println("</table></center>");
    out.println("<br>");
    //mostrar información
    out.println("<center><table bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 align='center' width='100%'>");
    out.println("<tr bgColor=white borderColor=silver width='100%'><td align='center' class=\"td11\" width='20%' style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Consecutivo</strong></font></td><td align='center' class=\"td11\" width='20%' style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Nombres Apellidos</strong></font></td><td align='center' width='12%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Contrato</strong></font></td><td align='center' width='12%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Fecha Ajuste</strong>");
    out.println("</font></td><td align='center' width='10%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Monto&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong></font></td><td align='center' width='20%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Banco&nbsp;y&nbsp;Cuenta&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Egreso&nbsp;Original&nbsp;&nbsp;&nbsp;&nbsp;</B></font></td><td align='center' width='12%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Fecha de la Decisión</strong></font></td>");
    out.println("<td align='center' width='14%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Decisión</strong></font></td></tr>");
    if(v_buscar)
    {
     //buscar la información de los ajustes según opción
     findAjustes();
    }
    out.println("</table></center>");
    //boton regreso y aceptar
    out.println("<center><table border='0' align='right' width='65%'><tr>");
    out.println("<td width='50%' align='left'><input type='button' value='Regresar' ONCLICK=history.go(-1);></td>");
    if(v_buscar)
     {
       out.println("<td width='15%' align='right' BGCOLOR='#DCDCDC' style='border: thin solid'><a href='TBPKT_AJUSTES.TBS_ImprimirAjustes' target='impresion' style='font-style: normal; text-decoration:none'><font face='Verdana' size='1'><img src='imagenes/PRINTER.gif' alt='Version para Imprimir' border='0'></font></a></td>");
     }
    out.println("</tr></table></center>");
    out.println(codHtm.TBFL_PIE);
    out.close();
}
////////////////tomar parametros de la decisión de lo que quiere reportar /////////////////
private void takeParameter()
  {
    v_opcion = new String("");
    if(request.getParameter("traer")!=null || request.getParameter("ajus")!=null ||
       request.getParameter("girar")!=null)
    {
       out.println("<tr align='center' width='100%'>");
       out.println("<td align='left' width='11%'><font face='Verdana' size='1' color='black'><strong>Decisión </strong></font></td>");
       if(request.getParameter("girar")!=null)
       {
          v_opcion = request.getParameter("girar");
          out.println("<td align='center' width='33%'><font face='Verdana' size='1'><input type='checkbox' checked name='girar' value='1'>Girar Cliente</font></td>");
       }
       else
       {
          out.println("<td align='center' width='33%'><font face='Verdana' size='1'><input type='checkbox' name='girar' value='1'>Girar Cliente</font></td>");
       }
       if(request.getParameter("ajus")!=null)
       {
          v_opcion += request.getParameter("ajus");
          out.println("<td align='center' width='33%'><font face='Verdana' size='1'><input type='checkbox' checked name='ajus' value='2'>Ajustar Contrato</font></td>");
       }
       else
       {
          out.println("<td align='center' width='33%'><font face='Verdana' size='1'><input type='checkbox' name='ajus' value='2'>Ajustar Contrato</font></td>");
       }
       if(request.getParameter("traer")!=null)
       {
          v_opcion += request.getParameter("traer");
          out.println("<td align='center' width='33%'><font face='Verdana' size='1'><input type='checkbox' checked name='traer' value='3'>Cliente Paga</font></td>");
       }
       else
       {
          out.println("<td align='center' width='33%'><font face='Verdana' size='1'><input type='checkbox' name='traer' value='3'>Cliente Paga</font></td>");
       }
       if(request.getParameter("obligatoriofd")!=null && request.getParameter("obligatoriofh")!=null )
       {
          v_fecDesde  = new String(request.getParameter("obligatoriofd"));
          v_fecHasta  = new String(request.getParameter("obligatoriofh"));
          v_contDesde = new String(request.getParameter("cd"));
          v_contHasta = new String(request.getParameter("ch"));
          //si es blanco no hacer
          if(!v_contDesde.trim().equals(""))
          {
            if(v_contDesde.length()<9)
            {
              while(v_contDesde.length()<9)
                v_contDesde = new String("0"+v_contDesde);
            }
          }
          if(!v_contHasta.trim().equals(""))
          {
           if(v_contHasta.length()<9)
           {
            while(v_contHasta.length()<9)
                v_contHasta = new String("0"+v_contHasta);
           }
          }
       }
       out.println("<tr>");
       out.println("<tr align='center' width='100%'>");
       out.println("<td align='left' width='11%'><font face='Verdana' size='1' color='black'><strong>Fecha Decisión</strong></font></td>");
       out.println("<td align='left' width='33%'><font face='Verdana' size='1' color='black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(aaaa-mm-dd)<br>*Desde <input type='text' MAXLENGTH='10' SIZE=13 name='obligatoriofd' value='"+v_fecDesde+"' onChange='fechavalida(this)'></font></td>");
       out.println("<td align='left' width='33%'><font face='Verdana' size='1' color='black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(aaaa-mm-dd)<br>*Hasta <input type='text' MAXLENGTH='10' SIZE=13 name='obligatoriofh' value='"+v_fecHasta+"' onChange='fechavalida2(this)'></font></td>");
       out.println("<td align='left' width='13%'><font color='white'>l</font></td>");
       out.println("</tr>");
       out.println("<tr>");
       out.println("<tr align='center' width='100%'>");
       out.println("<td align='left' width='11%'><font face='Verdana' size='1' color='black'><strong>Contrato </strong></font></td>");
       out.println("<td align='left' width='33%'><font face='Verdana' size='1' color='black'>*Desde <input type='text' MAXLENGTH=9 SIZE=13 name='cd' value='"+v_contDesde+"' ></font></td>");
       out.println("<td align='left' width='33%'><font face='Verdana' size='1' color='black'>*Hasta <input type='text' MAXLENGTH=9 SIZE=13 name='ch' value='"+v_contHasta+"' ></font></td>");
       out.println("<td align='left' width='23%'><input type='submit' value='Buscar'></td></tr>");
       out.println("</tr>");
       v_buscar=true;
    }
    else
    {
      out.println("<tr align='center' width='100%'>");
      out.println("<td align='left' width='11%'><font face='Verdana' size='1' color='black'><strong>Decisión </strong></font></td>");
      out.println("<td align='left' width='33%'><font face='Verdana' size='1' color='black'><input type='checkbox' checked name='girar' value='1'>Girar Cliente</font></td>");
      out.println("<td align='left' width='33%'><font face='Verdana' size='1' color='black'><input type='checkbox' checked name='ajus' value='2'>Ajustar Contrato</font></td>");
      out.println("<td align='left' width='23%'><font face='Verdana' size='1' color='black'><input type='checkbox' checked name='traer' value='3'>Cliente Paga</font></td>");
      out.println("</tr>");
      out.println("<tr>");
      out.println("<tr align='center' width='100%'>");
      out.println("<td align='left' width='11%'><font face='Verdana' size='1' color='black'><strong>Fecha Decisión</strong></font></td>");
      out.println("<td align='left' width='33%'><font face='Verdana' size='1' color='black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(aaaa-mm-dd)<br>*Desde <input type='text' MAXLENGTH='10' SIZE=13 name='obligatoriofd' onChange='fechavalida(this)'></font></td>");
      out.println("<td align='left' width='33%'><font face='Verdana' size='1' color='black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(aaaa-mm-dd)<br>*Hasta <input type='text' MAXLENGTH='10' SIZE=13 name='obligatoriofh' onChange='fechavalida2(this)'></font></td>");
      out.println("<td align='left' width='13%'><font color='white'>l</font></td>");
      out.println("</tr>");
      out.println("<tr>");
      out.println("<tr align='center' width='100%'>");
      out.println("<td align='left' width='11%'><font face='Verdana' size='1' color='black'><strong>Contrato </strong></font></td>");
      out.println("<td align='left' width='33%'><font face='Verdana' size='1' color='black'>*Desde <input type='text' MAXLENGTH=9 SIZE=13 name='cd'  ></font></td>");
      out.println("<td align='left' width='33%'><font face='Verdana' size='1' color='black'>*Hasta <input type='text' MAXLENGTH=9 SIZE=13 name='ch' ></font></td>");
      out.println("<td align='left' width='23%'><input type='submit' value='Buscar'></td></tr>");
      out.println("</tr>");
      v_buscar=false;
    }
}
///////////////////Buscar la información necesaria de los ajustes según las opciones de lo
              ////////////que desea reportar el usuario/////////////////
private void findAjustes()
{
    String v_mostrar=(String)dicOpcion.get(v_opcion);
    if(i_sqlj.TBPBD_ConexionBD())
    {
      v_fecDesde     = i_fnd.TBPL_formatDate(v_fecDesde,"aaaa-mm-dd");
      v_fecHasta     = i_fnd.TBPL_formatDate(v_fecHasta,"aaaa-mm-dd");
      //llamar procedimiento para traer la información de los ajustes de la BD
      String valores = i_sqlj.TBPBD_AllRepAjustes(v_mostrar,v_fecDesde,v_fecHasta,v_contDesde,v_contHasta);
      if(!valores.equals("") && valores.indexOf("Exception")==-1)
      {
        sess.setAttribute ("VALREPORTE",valores);
        sess.setAttribute ("cadena",cadena);
        //mostrar la información
        TBPL_showReporte(valores);
        v_buscar = true;
      }
      else
      {
       v_buscar = false;
      }
    }
}
//Visualizar la información para cada uno de los ajustes según opciones del cliente////////
private void TBPL_showReporte(String valores)
{
     int ii              = 1 ;
     Hashtable dicAccion = new Hashtable();
     dicAccion.put("SAC001","Girar al Cliente");
     dicAccion.put("SAC002","Ajustar Contrato");
     dicAccion.put("SAC003","Cliente Paga");
     while(true)
     {
      String v_ii = Integer.toString(ii++);
      if(!i_fnd.TBPL_getCmp(valores,"nom"+v_ii).equals(""))
       {
        String v_decision=i_fnd.TBPL_getCmp(valores,"accion"+v_ii).toUpperCase();
        String v_acc=new String("");
        if(dicAccion.get(v_decision)!=null)
          v_acc=(String)dicAccion.get(v_decision);
        i_LP.TBPL_ShowResultRep(out,i_fnd.TBPL_getCmp(valores,"nom"+v_ii),i_fnd.TBPL_getCmp(valores,"cont"+v_ii),
                                i_fnd.TBPL_getCmp(valores,"fajs"+v_ii),i_fnd.TBPL_getCmp(valores,"val"+v_ii),
                                i_fnd.TBPL_getCmp(valores,"bco"+v_ii),i_fnd.TBPL_getCmp(valores,"fdec"+v_ii),
                                i_fnd.TBPL_getCmp(valores,"conse"+v_ii),v_acc);
      }
      else
        break;
     }
}
//-------
}
