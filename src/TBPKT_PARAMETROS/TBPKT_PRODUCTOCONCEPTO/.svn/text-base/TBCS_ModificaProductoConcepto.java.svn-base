package TBPKT_PARAMETROS.TBPKT_PRODUCTOCONCEPTO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_ModificaProductoConcepto extends HttpServlet implements SingleThreadModel{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
private String v_nuevaCadena ="";

  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   * Process the HTTP Get request
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try{
       out = new PrintWriter (response.getOutputStream());
       response.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
       //response.setHeader("Pragma", "No-Cache");
       //response.setDateHeader("Expires", 0);
       String parametros[] = new String[8];
       String  cadena = request.getParameter("cadena");
       v_nuevaCadena = cadena;
       String ip_tax = request.getRemoteAddr();
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

  v_Consulta = new TBCL_Consulta();
  String v_declaracion = new String();
  Vector v_resultadodeclaracion = new Vector();
  String declaracion1 = new String();
  Vector v_resultadodeclaracion1 = new Vector();
  v_Consulta.TBPL_RealizarConexion();

    //Toma el código del producto
    String v_codpro = "";
    String transaccion = "";
    String tipotransaccion = "";
    String clasetransaccion = "";
    String metord = "";
    String metben = "";
    String metpen = "";
    String metcue = "";
    String naturaleza = "";
    String resnat = "";
    String priesq = "";
    String cont = "";
    String pricon = "";
    String tras = "";
    String retot = "";
    String retpen = "";
    String retaxb = "";
    String pmax = "";
    String pmin = "";

    try{
       v_codpro = request.getParameter("v_codpro");
       transaccion = request.getParameter("transaccion");
       tipotransaccion = request.getParameter("tipotransaccion");
       clasetransaccion = request.getParameter("clasetransaccion");
       metord = request.getParameter("metord");
       metben = request.getParameter("metben");
       metpen = request.getParameter("metpen");
       metcue = request.getParameter("metcue");
       naturaleza = request.getParameter("naturaleza");
       resnat = request.getParameter("resnat");
       priesq = request.getParameter("priesq");
       cont = request.getParameter("cont");
       pricon = request.getParameter("obligatoriopricon");
       tras = request.getParameter("tras");
       retot = request.getParameter("retot");
       retpen = request.getParameter("retpen");
       retaxb = request.getParameter("retaxb");
       pmax = request.getParameter("pmax").trim();
       pmin = request.getParameter("pmin").trim();
       }
    catch (Exception e) { e.printStackTrace(); }

  if ((retaxb.equals("N")&&retot.equals("S"))||(retaxb.equals("N")&&metord.equals("SMO003"))||(retaxb.equals("N")&&tras.equals("S")))
    TBPL_PublicarError();
  else
    {
      v_declaracion = "SELECT ref_codigo FROM tbreferencias WHERE ref_descripcion = '"+metben+"' AND ref_codigo LIKE 'SMB%'";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, "ref_codigo");
      metben = v_resultadodeclaracion.elementAt(0).toString();

      v_declaracion = "SELECT ref_codigo FROM tbreferencias WHERE ref_descripcion = '"+metpen+"' AND ref_codigo LIKE 'SMP%'";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, "ref_codigo");
      metpen = v_resultadodeclaracion.elementAt(0).toString();

      v_declaracion = "SELECT ref_codigo FROM tbreferencias WHERE ref_descripcion = '"+metcue+"' AND ref_codigo LIKE 'SMC%'";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, "ref_codigo");
      metcue = v_resultadodeclaracion.elementAt(0).toString();
    if (pmax.length()==0||pmax==null)
      pmax = "null";
    else
      pmax = pmax;
    if (pmin.length()==0||pmin==null)
      pmin = "null";
    else
      pmin = pmin;



      v_declaracion = "UPDATE tbproductos_conceptos SET "+
                    "   prc_ref_metodo_orden = UPPER('"+metord+"')"+
                    " , prc_ref_metodo_beneficio = UPPER('"+metben+"')"+
                    " , prc_ref_metodo_penalizacion = UPPER('"+metpen+"')"+
                    " , prc_ref_metodo_cuenta = UPPER('"+metcue+"')"+
                    " , prc_ref_naturaleza = UPPER('"+naturaleza+"')"+
                    " , prc_respetar_naturaleza = UPPER('"+resnat+"')"+
                    " , prc_ref_prioridad_esquema = UPPER('"+priesq+"')"+
                    " , prc_contabilizar = UPPER('"+cont+"')"+
                    " , prc_prioridad_concepto = "+pricon+""+
                    " , prc_traslado = UPPER('"+tras+"')"+
                    " , prc_retiro_total = UPPER('"+retot+"')"+
                    " , prc_retiro_penalizado = UPPER('"+retpen+"')"+
                    " , prc_origen_taxb = UPPER('"+retaxb+"')"+
                    " , prc_dias_mayor = "+pmin+
                    " , prc_dias_menor = "+pmax+
                    " WHERE prc_pro_codigo = '"+v_codpro+"' AND prc_cot_ref_transaccion = '"+transaccion+"' AND prc_cot_ref_tipo_transaccion = '"+tipotransaccion+"' AND prc_cot_ref_clase_transaccion = '"+clasetransaccion+"'";

    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);

    v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos

    //publicacion en pagina html de las Consultas
    out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones por producto","Administración de conceptos de transacciones por producto", "", v_resultadodeclaracion.elementAt(0).toString(), false));
    out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></CENTER>");
    out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
    out.close();
    }
  }


  private void TBPL_PublicarError()
  {
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones por producto","Administración de conceptos de transacciones por producto", "", "No puede seleccionar Origen TaxBenefit \"NO\" con:<BR><MENU><LI>Metodo orden = APORTES SELECCIONADOS<LI>Retiro total = SI<LI>Traslado = SI</MENU>", false));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_PRODUCTOCONCEPTO.TBCS_ModificaProductoConcepto Information";
  }
}

