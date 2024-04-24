
package TBPKT_AJUSTES;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;


/**
*  El objetivo de este servlet es visualizar en detalle la información de cada uno de los
*  ajustes.
*/

public class TBS_AmpliarAjuste extends HttpServlet{
  STBCL_GenerarBaseHTML codHtm;
  TBCL_FndCmp i_fnd=new TBCL_FndCmp();
  TBCL_LoadPage i_LP;
  String cod_producto=new String("");
  String num_contrato=new String("");
  String v_nombApel=new String("");
  String v_cons=new String("");
  String v_detalle=new String("");
  String k_cabeza=new String("");
  HttpServletRequest request;
  HttpServletResponse response;
  HttpSession sess;
  PrintWriter out;
//////////////////////////////////inicialización del servlet////////////////
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
/////////////////////////////////Llamado Principal//////////////////////////////////
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    v_nombApel=v_cons=v_detalle="";
    this.request=request;
    this.response=response;
    i_LP=new TBCL_LoadPage();
    i_fnd=new TBCL_FndCmp();
    response.setContentType("text/html");
    out = new PrintWriter (response.getOutputStream());
    sess=request.getSession(true);
    sess.setMaxInactiveInterval(3600);

    k_cabeza=codHtm.TBFL_CABEZA("Detalle del Ajuste","Detalle del Ajuste","TBPKT_AJUSTES.TBS_AmpliarAjuste","",true);
    if(takeParameter()){//toma la información del ajuste a mostrar
      buildPage();//armar página de salida al usuario
    }
  }
/////////////////////////////////construcción de la página de salida//////////////////////
  private void buildPage(){
    //primer parte encabezado
    i_LP.TBPL_Encabezado(out,k_cabeza,cod_producto,num_contrato,v_nombApel,"<tr><td align='center'><font face='Verdana' size='1'><strong>AJUSTE No.: </strong>"+v_cons+"</font></td></tr>");
    //mostrar resultado
    out.println("<center><table bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 align='center' width='100%'>");
    out.println("<tr bgColor=white borderColor=silver width='100%'><td align='center' class=\"td11\" width='20%' style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Item</strong></font></td><td align='center' width='20%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Valor Ajustado</strong></font></td><td align='center' width='30%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Transacción Retiro Original</strong></font></td><td align='center' width='30%' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Transacción Retiro Actual</strong></font></td></tr>");
    showDetalle();//armar mostrar el detalle del ajuste
    out.println("</table></center>");
    //boton regreso
    out.println("<center><table border='0' width='20%'>");
    out.println("<tr><td align='center'><input type='button' value='Regresar' ONCLICK=history.go(-1);></td></tr>");
    out.println("</table></center>");

    out.println(codHtm.TBFL_PIE);
    out.close();

  }
/////////////////////////////tomar parametros de entrada a la página///////////////////
  private boolean takeParameter(){
    if(request.getParameter("cons")!=null && sess.getAttribute("NOMBAPEL")!=null &&
       sess.getAttribute("VALRETDEC")!=null
       ){
       v_cons=request.getParameter("cons");//consecutivo del ajuste
       v_nombApel=(String)sess.getAttribute("NOMBAPEL");//nombres y apellidos
       sess.removeAttribute("CONTRATO");
       cod_producto = (String)sess.getAttribute("PRODUCTO");//producto
       num_contrato = (String)sess.getAttribute("CONTRATO");//contrato
       v_detalle=(String)sess.getAttribute("VALRETDEC");
       return true;
    }else{
       i_LP.TBPL_PrintMsgErr(out,"Problemas con las variables de session y parámetros de entrada",true,k_cabeza,codHtm.TBFL_PIE);
       return false;
    }
  }
/////////////////////////armar y mostrar el detalle del ajuste/////////////////////
  private void showDetalle(){
    String v_razon=new String(i_fnd.TBPL_getCmp(v_detalle,"raz"+v_cons));
    int maxLin=Integer.parseInt(i_fnd.TBPL_getCmp(v_detalle,"max"+v_cons));//max linea del
                                                                           //consecutivo
    int ii=1;String v_ii="0";
    while(ii<=maxLin){
      v_ii=Integer.toString(ii++);
      if(!i_fnd.TBPL_getCmp(v_detalle,"val"+v_cons+v_ii).equals("")){
        //armar el detalle
        i_LP.TBPL_ShowDetalleAjs(out,v_ii,i_fnd.TBPL_getCmp(v_detalle,"val"+v_cons+v_ii),i_fnd.TBPL_getCmp(v_detalle,"orig"+v_cons+v_ii),i_fnd.TBPL_getCmp(v_detalle,"act"+v_cons+v_ii));
      }
    }
    //mostrar la razón del ajuste al final del detalle
    out.println("</table></center><br>");
    out.println("<font face='Verdana' size='1'><strong>RAZÓN DEL AJUSTE : </strong></font>");
    out.println("<center><table border='1' width='100%'>");
    out.println("<tr><td>");
    out.println("<font face='Verdana' size='1'>"+v_razon);
    out.println("</font></td></tr>");
  }
//  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  public String getServletInfo() {
    return "TBPKT_AJUSTES.TBS_AmpliarAjuste Information";
  }
}

