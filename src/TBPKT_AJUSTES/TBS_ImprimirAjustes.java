
package TBPKT_AJUSTES;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;
import java.io.*;


/**
*  El objetivo de este servlet es mostrar en una ventana nueva en modo de impresión la
*  informacion de todos los ajustes que tengan alguna acción
*/


public class TBS_ImprimirAjustes extends HttpServlet{

  TBCL_FndCmp i_fnd = new TBCL_FndCmp();
  String cadena     = new String("");
  HttpServletRequest request;
  HttpServletResponse response;
  TBCL_LoadPage i_LP;
  SQL_AJUSTE i_sqlj;
  HttpSession sess;
  PrintWriter out;
/////////////////////////////////////inicialización del servlet/////////////////////////////
public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }
/////////////////////////////////////////llamado principal////////////////////////////////
public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
    this.request                = request;
    this.response               = response;
    i_LP                        = new TBCL_LoadPage();
    i_fnd                       = new TBCL_FndCmp();
    i_sqlj                      = new SQL_AJUSTE();
    response.setContentType("text/html");
    out                         = new PrintWriter (response.getOutputStream());
    sess                        = request.getSession(true);
    sess.setMaxInactiveInterval(3600);
    String cadena               = (String)sess.getAttribute("cadena");
    //INICIO seguridad
    String parametros[]         = new String[8];
    String ip_tax               = request.getRemoteAddr();
     
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
    parametros                  = TBCL_Seguridad.TBFL_Seguridad(cadena,out,ip_tax);
     //FIN seguridad
    //construcción de la página de salida
    buildPage();
    i_sqlj.TBPBD_CerrarConexionBD();
}
//////////////////////Página de salida en formato para impresión(blanco y negro)////////////
private void buildPage()
{
    String v_fechaHoy = new String("");
    if(SQL_AJUSTE.TBPBD_ConexionBD())
    {
      v_fechaHoy = SQL_AJUSTE.TBPBD_FechaActual();
    }
    out.println("<html><head><title>Reporte de Ajustes</title></head><body>");
    //out.println("<FORM METHOD=Post NAME=codigo ACTION=/Servlets/TBPKT_AJUSTES.TBS_ReporteAjustes>");
    out.println("<center><table border='0' align='center' width='100%'>");//TABLA PRINCIPAL
    //segunda tabla encabezado
    out.println("<center><table border='0' align='center' width='100%'>");
    out.println("<tr align='center'><font face='Verdana' size='3'><strong>REPORTE DE AJUSTES</strong></font></tr><br><br>");
    out.println("<tr align='left'><font face='Verdana' size='1'><strong>FECHA DEL REPORTE: "+v_fechaHoy+"</strong></font></tr>");
    out.println("</table></center>");
    out.println("<hr width='100%'>");
    //tercer tabla de resultados
    out.println("<center><table bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 align='center' width='100%'>");
    out.println("<tr bgColor=white borderColor=silver width='100%'>");
    out.println("<td align='center' BGCOLOR='#DCDCDC' width='20%' style='border: thin solid'><font face='Verdana' size='1'><strong>Consecutivo</strong></font></td>");
    out.println("<td align='center' BGCOLOR='#DCDCDC' width='20%' style='border: thin solid'><font face='Verdana' size='1'><strong>Nombres Apellidos</strong></font></td><td align='center' width='12%' BGCOLOR='#DCDCDC' style='border: thin solid'><font face='Verdana' size='1'><strong>Contrato</strong></font></td><td align='center' width='12%' BGCOLOR='#DCDCDC' style='border: thin solid'><font face='Verdana' size='1'><strong>Fecha Ajuste</strong></font></td><td align='center' width='10%' BGCOLOR='#DCDCDC' style='border: thin solid'><font face='Verdana' size='1'><strong>Monto</strong></font></td><td align='center' width='20%' BGCOLOR='#DCDCDC' style='border: thin solid'><font face='Verdana' size='1'><strong>Banco-Cuenta Egreso Original</strong></font></td><td align='center' width='12%' BGCOLOR='#DCDCDC' style='border: thin solid'><font face='Verdana' size='1'><strong>Fecha de la Decisión</strong></font></td>");
    out.println("<td align='center' width='14%' BGCOLOR='#DCDCDC' style='border: thin solid'><font face='Verdana' size='1'><strong>Decisión</strong></font></td></tr>");
    //tomar la información de los ajustes con acción
    if(sess.getAttribute("VALREPORTE")!=null)
     {
      TBPL_showReporte((String)sess.getAttribute("VALREPORTE"));//mostrar el reporte
     }
    out.println("</table></center>");
    out.println("</table></center>");
    //out.println("</form>");
    out.println("</body></html>");
    out.close();
}
//////////////////Armar y mostrar en la página la información por cada ajuste////////////////
private void TBPL_showReporte(String valores)
{
     int ii              =1 ;
     Hashtable dicAccion = new Hashtable();
     dicAccion.put("SAC001","Girar al Cliente");dicAccion.put("SAC002","Ajustar Contrato");
     dicAccion.put("SAC003","Cliente Paga");
     while(true)
     {
      String v_ii = Integer.toString(ii++);
      if(!i_fnd.TBPL_getCmp(valores,"nom"+v_ii).equals(""))
      {
        String v_decision = i_fnd.TBPL_getCmp(valores,"accion"+v_ii).toUpperCase();
        String v_acc      = new String("");
        if(dicAccion.get(v_decision)!=null)
          v_acc           = (String)dicAccion.get(v_decision);
          i_LP.TBPL_ShowResultRep(out,i_fnd.TBPL_getCmp(valores,"nom"+v_ii),i_fnd.TBPL_getCmp(valores,"cont"+v_ii),
                                i_fnd.TBPL_getCmp(valores,"fajs"+v_ii),i_fnd.TBPL_getCmp(valores,"val"+v_ii),
                                i_fnd.TBPL_getCmp(valores,"bco"+v_ii),i_fnd.TBPL_getCmp(valores,"fdec"+v_ii),
                                i_fnd.TBPL_getCmp(valores,"conse"+v_ii),v_acc);
      }else
        break;
     }
}
//--
}

 