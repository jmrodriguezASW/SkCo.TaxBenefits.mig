package TBPKT_PARAMETROS.TBPKT_PRODUCTOCONCEPTO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_ACEMProductoConcepto extends HttpServlet{
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
    String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion, v_resultadodeclaracion2, v_resultadodeclaracion3, v_resultadodeclaracion4, v_resultadodeclaracion6, v_resultadodeclaracion7, v_resultadodeclaracion8, v_resultadodeclaracion9, v_resultadodeclaracion10;
    Vector v_resultadodeclaracion1 = new Vector();
    Vector v_resultadodeclaracion5 = new Vector();
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    //Toma la opción de modificar, eliminar, adicionar o Consultar
    String v_opcion = "Consulta";
    //Toma los diferentes codigos de producto
    String v_codpro = "";
    //Toma los codigos de la transaccion
    String codigos = "";
    //Toma la transaccion de la contabilidad
    String transac = "";
    //Toma el tipo de transacción de contabilidad
    String tipotransac = "";
    //Toma la clase de transaccion
    String clasetransac = "";

    try{
       v_opcion = request.getParameter("v_opcion");
       v_codpro = request.getParameter("v_codpro");
       if (v_opcion.equals("adiciona"))
         codigos = request.getParameter("transaca");
       else
         codigos = request.getParameter("transac");
       }
    catch (Exception e) { e.printStackTrace(); }

    transac = codigos.substring(0,6);
    tipotransac = codigos.substring(6,12);
    clasetransac = codigos.substring(12);


   if (v_opcion.equals("Consulta"))
     {
     v_declaracion = "SELECT\n"+
"                   PRC_PRO_CODIGO,\n"+
"                   a.ref_descripcion, --PRC_COT_REF_TRANSACCION,\n"+
"                   b.ref_descripcion, --PRC_COT_REF_TIPO_TRANSACCION,\n"+
"                   c.ref_descripcion, --PRC_COT_REF_CLASE_TRANSACCION,\n"+
"                   d.ref_descripcion, --PRC_REF_METODO_ORDEN,\n"+
"                   e.ref_descripcion, --PRC_REF_METODO_BENEFICIO,\n"+
"                   f.ref_descripcion, --PRC_REF_METODO_PENALIZACION,\n"+
"                   g.ref_descripcion, --PRC_REF_METODO_CUENTA,\n"+
"                   h.ref_descripcion, --PRC_REF_NATURALEZA,\n"+
"                   PRC_RESPETAR_NATURALEZA,\n"+
"                   i.ref_descripcion, --PRC_REF_PRIORIDAD_ESQUEMA,\n"+
"                   PRC_CONTABILIZAR,\n"+
"                   PRC_PRIORIDAD_CONCEPTO,\n"+
"                   PRC_TRASLADO,\n"+
"                   PRC_RETIRO_TOTAL,\n"+
"                   PRC_RETIRO_PENALIZADO,\n"+
"                   PRC_ORIGEN_TAXB,\n"+
"                   PRC_DIAS_MAYOR,\n"+
"                   PRC_DIAS_MENOR\n"+
"FROM               tbproductos_conceptos,\n"+
"                   tbreferencias a,\n"+
"                   tbreferencias b,\n"+
"                   tbreferencias c,\n"+
"                   tbreferencias d,\n"+
"                   tbreferencias e,\n"+
"                   tbreferencias f,\n"+
"                   tbreferencias g,\n"+
"                   tbreferencias h,\n"+
"                   tbreferencias i\n"+
"WHERE prc_pro_codigo = '"+v_codpro+"'\n"+
"AND prc_cot_ref_transaccion = '"+transac+"'\n"+
"AND prc_cot_ref_tipo_transaccion = '"+tipotransac+"'\n"+
"AND prc_cot_ref_clase_transaccion = '"+clasetransac+"'\n"+
"AND                a.ref_codigo = PRC_COT_REF_TRANSACCION\n"+
"AND                b.ref_codigo = PRC_COT_REF_TIPO_TRANSACCION\n"+
"AND                c.ref_codigo = PRC_COT_REF_CLASE_TRANSACCION\n"+
"AND                d.ref_codigo = PRC_REF_METODO_ORDEN\n"+
"AND                e.ref_codigo = PRC_REF_METODO_BENEFICIO\n"+
"AND                f.ref_codigo = PRC_REF_METODO_PENALIZACION\n"+
"AND                g.ref_codigo = PRC_REF_METODO_CUENTA\n"+
"AND                h.ref_codigo = PRC_REF_NATURALEZA\n"+
"AND                i.ref_codigo = PRC_REF_PRIORIDAD_ESQUEMA";



      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarConsulta(v_resultadodeclaracion);
     }
   else if (v_opcion.equals("modifica"))
     {
     v_declaracion = "SELECT\n"+
"                   PRC_PRO_CODIGO,\n"+
"                   a.ref_descripcion, --PRC_COT_REF_TRANSACCION,\n"+
"                   b.ref_descripcion, --PRC_COT_REF_TIPO_TRANSACCION,\n"+
"                   c.ref_descripcion, --PRC_COT_REF_CLASE_TRANSACCION,\n"+
"                   d.ref_descripcion, --PRC_REF_METODO_ORDEN,\n"+
"                   e.ref_descripcion, --PRC_REF_METODO_BENEFICIO,\n"+
"                   f.ref_descripcion, --PRC_REF_METODO_PENALIZACION,\n"+
"                   g.ref_descripcion, --PRC_REF_METODO_CUENTA,\n"+
"                   h.ref_descripcion, --PRC_REF_NATURALEZA,\n"+
"                   PRC_RESPETAR_NATURALEZA,\n"+
"                   i.ref_descripcion, --PRC_REF_PRIORIDAD_ESQUEMA,\n"+
"                   PRC_CONTABILIZAR,\n"+
"                   PRC_PRIORIDAD_CONCEPTO,\n"+
"                   PRC_TRASLADO,\n"+
"                   PRC_RETIRO_TOTAL,\n"+
"                   PRC_RETIRO_PENALIZADO,\n"+
"                   PRC_ORIGEN_TAXB,\n"+
"                   PRC_DIAS_MAYOR,\n"+
"                   PRC_DIAS_MENOR\n"+
"FROM               tbproductos_conceptos,\n"+
"                   tbreferencias a,\n"+
"                   tbreferencias b,\n"+
"                   tbreferencias c,\n"+
"                   tbreferencias d,\n"+
"                   tbreferencias e,\n"+
"                   tbreferencias f,\n"+
"                   tbreferencias g,\n"+
"                   tbreferencias h,\n"+
"                   tbreferencias i\n"+
"WHERE prc_pro_codigo = '"+v_codpro+"'\n"+
"AND prc_cot_ref_transaccion = '"+transac+"'\n"+
"AND prc_cot_ref_tipo_transaccion = '"+tipotransac+"'\n"+
"AND prc_cot_ref_clase_transaccion = '"+clasetransac+"'\n"+
"AND                a.ref_codigo = PRC_COT_REF_TRANSACCION\n"+
"AND                b.ref_codigo = PRC_COT_REF_TIPO_TRANSACCION\n"+
"AND                c.ref_codigo = PRC_COT_REF_CLASE_TRANSACCION\n"+
"AND                d.ref_codigo = PRC_REF_METODO_ORDEN\n"+
"AND                e.ref_codigo = PRC_REF_METODO_BENEFICIO\n"+
"AND                f.ref_codigo = PRC_REF_METODO_PENALIZACION\n"+
"AND                g.ref_codigo = PRC_REF_METODO_CUENTA\n"+
"AND                h.ref_codigo = PRC_REF_NATURALEZA\n"+
"AND                i.ref_codigo = PRC_REF_PRIORIDAD_ESQUEMA";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      /*Remplaza metodo orden
        ------------------*/

      v_declaracion ="SELECT pro_metodo_orden FROM tbproductos WHERE pro_codigo = '"+v_codpro+"'";
      String v_Scodmetord = v_Consulta.TBFL_Consulta(v_declaracion, "pro_metodo_orden").elementAt(0).toString();

      String v_codmetord[] = new String[v_Scodmetord.length()/6];

      for (int i=0; i<v_Scodmetord.length()/6; i++)
         v_codmetord[i] = v_Scodmetord.substring(i*6,i*6+6);

      for (int i=0; i<v_codmetord.length; i++)
         {
         //Sentencias SQL
         v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_codmetord[i]+"' and ref_codigo LIKE 'SMO%'";
         //Limpia el vector resultado v_declaracion para ingresar un nuevo dato

         //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
         v_resultadodeclaracion1.addElement(v_Consulta.TBFL_Consulta(v_declaracion).elementAt(0).toString());
         v_resultadodeclaracion1.addElement(v_Consulta.TBFL_Consulta(v_declaracion).elementAt(1).toString());
         }

      //-------------------------------------------------------------------------------


      v_declaracion ="SELECT pro_metodo_beneficio FROM tbproductos WHERE pro_codigo = '"+v_codpro+"'";
      v_resultadodeclaracion2 = TBFL_ConsultaMetodoBeneficio(v_Consulta.TBFL_Consulta(v_declaracion, "pro_metodo_beneficio").elementAt(0).toString());

      v_declaracion ="SELECT pro_metodo_penalizacion FROM tbproductos WHERE pro_codigo = '"+v_codpro+"'";
      v_resultadodeclaracion3 = TBFL_ConsultaMetodoPenalizacion(v_Consulta.TBFL_Consulta(v_declaracion, "pro_metodo_penalizacion").elementAt(0).toString());

      v_declaracion ="SELECT pro_metodo_cuenta FROM tbproductos WHERE pro_codigo = '"+v_codpro+"'";
      v_resultadodeclaracion4 = TBFL_ConsultaMetodoCuenta(v_Consulta.TBFL_Consulta(v_declaracion, "pro_metodo_cuenta").elementAt(0).toString());

      /*Remplaza naturalezaretiro
         ------------------*/
      v_declaracion ="SELECT pro_naturaleza_retiro FROM tbproductos WHERE pro_codigo = '"+v_codpro+"'";
      String v_Snatret = v_Consulta.TBFL_Consulta(v_declaracion, "pro_naturaleza_retiro").elementAt(0).toString();

      String v_codnatret[] = new String[v_Snatret.length()/6];

      for (int i=0; i<v_Snatret.length()/6; i++)
         v_codnatret[i] = v_Snatret.substring(i*6,i*6+6);

      for (int i=0; i<v_codnatret.length; i++)
         {
         //Sentencias SQL
         v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_codnatret[i]+"' and ref_codigo LIKE 'SNR%'";
         //Limpia el vector resultado v_declaracion para ingresar un nuevo dato

         //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
         v_resultadodeclaracion5.addElement(v_Consulta.TBFL_Consulta(v_declaracion).elementAt(0).toString());
         v_resultadodeclaracion5.addElement(v_Consulta.TBFL_Consulta(v_declaracion).elementAt(1).toString());
         }

      //---------------------------------------------------------------------------------

      v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'SPE%' AND ref_codigo != 'SPE000'";
      v_resultadodeclaracion6 = v_Consulta.TBFL_Consulta(v_declaracion);


      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarModifica(v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2, v_resultadodeclaracion3, v_resultadodeclaracion4, v_resultadodeclaracion5, v_resultadodeclaracion6, transac, tipotransac, clasetransac);
     }
   else if (v_opcion.equals("adiciona"))
     {
      /*Remplaza metodo orden
        ------------------*/

      v_declaracion ="SELECT pro_metodo_orden FROM tbproductos WHERE pro_codigo = '"+v_codpro+"'";
      String v_Scodmetord = v_Consulta.TBFL_Consulta(v_declaracion, "pro_metodo_orden").elementAt(0).toString();

      String v_codmetord[] = new String[v_Scodmetord.length()/6];

      for (int i=0; i<v_Scodmetord.length()/6; i++)
         v_codmetord[i] = v_Scodmetord.substring(i*6,i*6+6);

      for (int i=0; i<v_codmetord.length; i++)
         {
         //Sentencias SQL
         v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_codmetord[i]+"' and ref_codigo LIKE 'SMO%'";
         //Limpia el vector resultado v_declaracion para ingresar un nuevo dato

         //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
         v_resultadodeclaracion1.addElement(v_Consulta.TBFL_Consulta(v_declaracion).elementAt(0).toString());
         v_resultadodeclaracion1.addElement(v_Consulta.TBFL_Consulta(v_declaracion).elementAt(1).toString());
         }

      //-------------------------------------------------------------------------------

      v_declaracion ="SELECT pro_metodo_beneficio FROM tbproductos WHERE pro_codigo = '"+v_codpro+"'";
      v_resultadodeclaracion2 = TBFL_ConsultaMetodoBeneficio(v_Consulta.TBFL_Consulta(v_declaracion, "pro_metodo_beneficio").elementAt(0).toString());

      v_declaracion ="SELECT pro_metodo_penalizacion FROM tbproductos WHERE pro_codigo = '"+v_codpro+"'";
      v_resultadodeclaracion3 = TBFL_ConsultaMetodoPenalizacion(v_Consulta.TBFL_Consulta(v_declaracion, "pro_metodo_penalizacion").elementAt(0).toString());

      v_declaracion ="SELECT pro_metodo_cuenta FROM tbproductos WHERE pro_codigo = '"+v_codpro+"'";
      v_resultadodeclaracion4 = TBFL_ConsultaMetodoCuenta(v_Consulta.TBFL_Consulta(v_declaracion, "pro_metodo_cuenta").elementAt(0).toString());

      /*Remplaza naturalezaretiro
         ------------------*/
      v_declaracion ="SELECT pro_naturaleza_retiro FROM tbproductos WHERE pro_codigo = '"+v_codpro+"'";
      String v_Snatret = v_Consulta.TBFL_Consulta(v_declaracion, "pro_naturaleza_retiro").elementAt(0).toString();

      String v_codnatret[] = new String[v_Snatret.length()/6];

      for (int i=0; i<v_Snatret.length()/6; i++)
         v_codnatret[i] = v_Snatret.substring(i*6,i*6+6);

      for (int i=0; i<v_codnatret.length; i++)
         {
         //Sentencias SQL
         v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_codnatret[i]+"' and ref_codigo LIKE 'SNR%'";
         //Limpia el vector resultado v_declaracion para ingresar un nuevo dato

         //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
         v_resultadodeclaracion5.addElement(v_Consulta.TBFL_Consulta(v_declaracion).elementAt(0).toString());
         v_resultadodeclaracion5.addElement(v_Consulta.TBFL_Consulta(v_declaracion).elementAt(1).toString());

         }

      //---------------------------------------------------------------------------------

      v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'SPE%' AND ref_codigo != 'SPE000'";
      v_resultadodeclaracion6 = v_Consulta.TBFL_Consulta(v_declaracion);


      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarAdiciona(v_codpro, TBFL_ConsultaDescripcion(transac), TBFL_ConsultaDescripcion(tipotransac), TBFL_ConsultaDescripcion(clasetransac), v_resultadodeclaracion1, v_resultadodeclaracion2, v_resultadodeclaracion3, v_resultadodeclaracion4, v_resultadodeclaracion5, v_resultadodeclaracion6, transac, tipotransac, clasetransac);
     }
   else if (v_opcion.equals("elimina"))
     {
     v_declaracion = "SELECT\n"+
"                   PRC_PRO_CODIGO,\n"+
"                   a.ref_descripcion, --PRC_COT_REF_TRANSACCION,\n"+
"                   b.ref_descripcion, --PRC_COT_REF_TIPO_TRANSACCION,\n"+
"                   c.ref_descripcion, --PRC_COT_REF_CLASE_TRANSACCION,\n"+
"                   d.ref_descripcion, --PRC_REF_METODO_ORDEN,\n"+
"                   e.ref_descripcion, --PRC_REF_METODO_BENEFICIO,\n"+
"                   f.ref_descripcion, --PRC_REF_METODO_PENALIZACION,\n"+
"                   g.ref_descripcion, --PRC_REF_METODO_CUENTA,\n"+
"                   h.ref_descripcion, --PRC_REF_NATURALEZA,\n"+
"                   PRC_RESPETAR_NATURALEZA,\n"+
"                   i.ref_descripcion, --PRC_REF_PRIORIDAD_ESQUEMA,\n"+
"                   PRC_CONTABILIZAR,\n"+
"                   PRC_PRIORIDAD_CONCEPTO,\n"+
"                   PRC_TRASLADO,\n"+
"                   PRC_RETIRO_TOTAL,\n"+
"                   PRC_RETIRO_PENALIZADO,\n"+
"                   PRC_ORIGEN_TAXB,\n"+
"                   PRC_DIAS_MAYOR,\n"+
"                   PRC_DIAS_MENOR\n"+
"FROM               tbproductos_conceptos,\n"+
"                   tbreferencias a,\n"+
"                   tbreferencias b,\n"+
"                   tbreferencias c,\n"+
"                   tbreferencias d,\n"+
"                   tbreferencias e,\n"+
"                   tbreferencias f,\n"+
"                   tbreferencias g,\n"+
"                   tbreferencias h,\n"+
"                   tbreferencias i\n"+
"WHERE prc_pro_codigo = '"+v_codpro+"'\n"+
"AND prc_cot_ref_transaccion = '"+transac+"'\n"+
"AND prc_cot_ref_tipo_transaccion = '"+tipotransac+"'\n"+
"AND prc_cot_ref_clase_transaccion = '"+clasetransac+"'\n"+
"AND                a.ref_codigo = PRC_COT_REF_TRANSACCION\n"+
"AND                b.ref_codigo = PRC_COT_REF_TIPO_TRANSACCION\n"+
"AND                c.ref_codigo = PRC_COT_REF_CLASE_TRANSACCION\n"+
"AND                d.ref_codigo = PRC_REF_METODO_ORDEN\n"+
"AND                e.ref_codigo = PRC_REF_METODO_BENEFICIO\n"+
"AND                f.ref_codigo = PRC_REF_METODO_PENALIZACION\n"+
"AND                g.ref_codigo = PRC_REF_METODO_CUENTA\n"+
"AND                h.ref_codigo = PRC_REF_NATURALEZA\n"+
"AND                i.ref_codigo = PRC_REF_PRIORIDAD_ESQUEMA";



      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);



      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarElimina(v_resultadodeclaracion, v_codpro, transac, tipotransac, clasetransac);
     }
  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }


      private void TBPL_PublicarConsulta(Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones por producto","Administración de conceptos de transacciones por producto", "", "", true, "moduloparametro.js", "return checkrequired(this)"));

      //Aqui van los campo que se quieren mostrar

/*  <TR><TH> English </TH><TH> Spanish </TH><TH> German </TH></TR>
  <TR><TD> one     </TD><TD> uno     </TD><TD> ein    </TD></TR>
  <TR><TD> two     </TD><TD> dos     </TD><TD> zwei   </TD></TR>
  <TR><TD> three   </TD><TD> tres    </TD><TD> drei   </TD></TR>*/

      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Consulta conceptos de transacciones por producto</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Código del producto</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(0).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Transaccioón</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(1).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Tipo de transacción</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(2).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Clase de transacción</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(3).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Método orden</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(4).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Método beneficio</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(5).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Método penalización</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(6).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Método cuenta</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(7).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Naturaleza</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(8).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Respetar naturaleza</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(9).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Prioridad Esquema</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(10).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Contabilizar</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(11).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Prioridad concepto</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(12).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Traslado</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(13).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Retiro total</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(14).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Retiro Penalizado</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(15).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Origen TaxBenefit</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(16).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Plazo máximo sin cargo</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(18).toString()+"&nbsp;días</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Plazo mínimo para validez de transacción</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(17).toString()+"&nbsp;días</font></TD></TR>");
      out.println("</TABLE>");
      //Hasta aca van los campos
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);

      out.close();
      }




      private void TBPL_PublicarModifica(Vector v_descripcion, Vector v_opciones, Vector v_opciones1, Vector v_opciones2, Vector v_opciones3, Vector v_opciones4, Vector v_opciones5, String transac, String tipotransac, String clasetransac)
      {
      v_descripcion.trimToSize();
      v_opciones.trimToSize();
      v_opciones1.trimToSize();
      v_opciones2.trimToSize();
      v_opciones3.trimToSize();
      v_opciones4.trimToSize();
      v_opciones5.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones por producto","Administración de conceptos de transacciones por producto","TBPKT_PARAMETROS.TBPKT_PRODUCTOCONCEPTO.TBCS_ModificaProductoConcepto","",true, "moduloparametro.js", "return checkrequired(this)"));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modifica conceptos de transacciones por producto</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Código del producto:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codpro value='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Transaccioón:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(1).toString())+"<INPUT TYPE=hidden NAME=transaccion value='"+transac+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Tipo de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(2).toString())+"<INPUT TYPE=hidden NAME=tipotransaccion value='"+tipotransac+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Clase de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(3).toString())+"<INPUT TYPE=hidden NAME=clasetransaccion value='"+clasetransac+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Método orden:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(4).toString())+"<BR><SELECT NAME=metord>");
      for (int i=0; i<v_opciones.size(); i=i+2)
         {
         if ((v_descripcion.elementAt(4).toString()).equals(v_opciones.elementAt(i+1).toString()))
           out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"' SELECTED>"+v_opciones.elementAt(i+1).toString());
         else
           out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Método beneficio:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(5).toString())+"<BR><SELECT NAME=metben>");
      for (int i=0; i<v_opciones1.size(); i++)
         {
         if (v_descripcion.elementAt(5).toString().equals(v_opciones1.elementAt(i).toString()))
           out.println("<OPTION SELECTED>"+v_opciones1.elementAt(i).toString());
         else
           out.println("<OPTION>"+v_opciones1.elementAt(i).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Método penalización:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(6).toString())+"<BR><SELECT NAME=metpen>");
      for (int i=0; i<v_opciones2.size(); i++)
         {
         if ((v_descripcion.elementAt(6).toString()).equals(v_opciones2.elementAt(i).toString()))
           out.println("<OPTION SELECTED>"+v_opciones2.elementAt(i).toString());
         else
           out.println("<OPTION>"+v_opciones2.elementAt(i).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Método cuenta:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(7).toString())+"<BR><SELECT NAME=metcue>");
      for (int i=0; i<v_opciones3.size(); i++)
         {
         if ((v_descripcion.elementAt(7).toString()).equals(v_opciones3.elementAt(i).toString()))
           out.println("<OPTION SELECTED>"+v_opciones3.elementAt(i).toString());
         else
           out.println("<OPTION>"+v_opciones3.elementAt(i).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Naturaleza:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(8).toString())+"<BR><SELECT NAME=naturaleza>");
      for (int i=0; i<v_opciones4.size(); i=i+2)
         {
         if (v_descripcion.elementAt(8).toString().equals(v_opciones4.elementAt(i+1).toString()))
           out.println("<OPTION VALUE='"+v_opciones4.elementAt(i).toString()+"' SELECTED>"+v_opciones4.elementAt(i+1).toString());
         else
           out.println("<OPTION VALUE='"+v_opciones4.elementAt(i).toString()+"'>"+v_opciones4.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Respetar naturaleza:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(9).toString())+"<BR><SELECT NAME=resnat>");
      if (v_opciones4.size()==2)
        out.println("<OPTION VALUE=S>SI</SELECT></font></TD></TR>");
      else
        {
        if (v_descripcion.elementAt(9).toString().equals("S"))
          out.println("<OPTION VALUE=N>NO<OPTION SELECTED VALUE=S>SI</SELECT></font></TD></TR>");
        else
          out.println("<OPTION SELECTED VALUE=N>NO<OPTION VALUE=S>SI</SELECT></font></TD></TR>");
        }

      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Prioridad Esquema:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(10).toString())+"<BR><SELECT NAME=priesq>");
      for (int i=0; i<v_opciones5.size(); i+=2)
         {
         if (v_descripcion.elementAt(10).toString().equals(v_opciones5.elementAt(i+1).toString()))
           out.println("<OPTION SELECTED VALUE='"+v_opciones5.elementAt(i).toString()+"'>"+v_opciones5.elementAt(i+1).toString());
         else
           out.println("<OPTION VALUE='"+v_opciones5.elementAt(i).toString()+"'>"+v_opciones5.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Contabilizar:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(11).toString())+"<BR><SELECT NAME=cont>");
      if (v_descripcion.elementAt(11).toString().equals("S"))
        out.println("<OPTION VALUE=N>NO<OPTION SELECTED VALUE=S>SI</SELECT></font></TD></TR>");
      else
        out.println("<OPTION SELECTED VALUE=N>NO<OPTION VALUE=S>SI</SELECT></font></TD></TR>");


      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Prioridad concepto:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(12).toString()+"<INPUT TYPE=hidden NAME=dpricon value='"+v_descripcion.elementAt(12).toString()+"'><BR><INPUT TYPE=text NAME=obligatoriopricon MAXLENGTH=5 SIZE=5 value='"+v_descripcion.elementAt(12).toString()+"' onblur='if (esNumero(obligatoriopricon)==1) return false;'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Traslado:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(13).toString())+"<BR><SELECT NAME=tras>");
      if (v_descripcion.elementAt(13).toString().equals("S"))
        out.println("<OPTION VALUE=N>NO<OPTION SELECTED VALUE=S>SI</SELECT></font></TD></TR>");
      else
        out.println("<OPTION SELECTED VALUE=N>NO<OPTION VALUE=S>SI</SELECT></font></TD></TR>");

      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Retiro total:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(14).toString())+"<BR><SELECT NAME=retot>");
       if (v_descripcion.elementAt(14).toString().equals("S"))
          out.println("<OPTION VALUE=N>NO<OPTION SELECTED VALUE=S>SI</SELECT></font></TD></TR>");
        else
          out.println("<OPTION SELECTED VALUE=N>NO<OPTION VALUE=S>SI</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Retiro Penalizado:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(15).toString())+"<BR><SELECT NAME=retpen>");
       if (v_descripcion.elementAt(15).toString().equals("S"))
          out.println("<OPTION VALUE=N>NO<OPTION SELECTED VALUE=S>SI</SELECT></font></TD></TR>");
        else
          out.println("<OPTION SELECTED VALUE=N>NO<OPTION VALUE=S>SI</SELECT></font></TD></TR>");

      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Origen TaxBenefit:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(16).toString())+"<BR><SELECT NAME=retaxb>");
       if (v_descripcion.elementAt(16).toString().equals("S"))
          out.println("<OPTION VALUE=N>NO<OPTION SELECTED VALUE=S>SI</SELECT></font></TD></TR>");
        else
          out.println("<OPTION SELECTED VALUE=N>NO<OPTION VALUE=S>SI</SELECT></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Plazo máximo sin cargo:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(18).toString()+"<BR><INPUT TYPE=text NAME=pmax MAXLENGTH=3 SIZE=3 value='"+v_descripcion.elementAt(17).toString()+"' onblur='if (esNumero(pmax)==1) return false;'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Plazo mínimo para validez de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(17).toString()+"<BR><INPUT TYPE=text NAME=pmin MAXLENGTH=3 SIZE=3 value='"+v_descripcion.elementAt(18).toString()+"' onblur='if (esNumero(pmin)==1) return false;'></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER><BR>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
    out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos

     out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
     out.close();
     }


      private void TBPL_PublicarAdiciona(String v_codpro, String transac,String tipotransac, String clasetransac, Vector v_opciones, Vector v_opciones1, Vector v_opciones2, Vector v_opciones3, Vector v_opciones4, Vector v_opciones5, String ctransac, String ctipotransac, String cclasetransac)
      {
      v_opciones.trimToSize();
      v_opciones1.trimToSize();
      v_opciones2.trimToSize();
      v_opciones3.trimToSize();
      v_opciones4.trimToSize();
      v_opciones5.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones por producto", "Administración de conceptos de transacciones por producto","TBPKT_PARAMETROS.TBPKT_PRODUCTOCONCEPTO.TBCS_AdicionaProductoConcepto", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Consulta conceptos de transacciones por producto</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Código del producto:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_codpro+"<INPUT TYPE=hidden NAME=v_codpro value='"+v_codpro+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Transaccioón:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+transac+"<INPUT TYPE=hidden NAME=transaccion value='"+ctransac+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Tipo de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+tipotransac+"<INPUT TYPE=hidden NAME=tipotransaccion value='"+ctipotransac+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Clase de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+clasetransac+"<INPUT TYPE=hidden NAME=clasetransaccion value='"+cclasetransac+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Método orden:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SELECT NAME=metord>");
      for (int i=0; i<v_opciones.size(); i=i+2)
         {
         out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Método beneficio:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SELECT NAME=metben>");
      for (int i=0; i<v_opciones1.size(); i++)
         {
         out.println("<OPTION>"+v_opciones1.elementAt(i).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Método penalización:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SELECT NAME=metpen>");
      for (int i=0; i<v_opciones2.size(); i++)
         {
         out.println("<OPTION>"+v_opciones2.elementAt(i).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Método cuenta:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SELECT NAME=metcue>");
      for (int i=0; i<v_opciones3.size(); i++)
         {
         out.println("<OPTION>"+v_opciones3.elementAt(i).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Naturaleza:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SELECT NAME=naturaleza>");
      for (int i=0; i<v_opciones4.size(); i=i+2)
         {
         out.println("<OPTION VALUE='"+v_opciones4.elementAt(i).toString()+"'>"+v_opciones4.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Respetar naturaleza:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SELECT NAME=resnat>");
      if (v_opciones4.size()==2)
        out.println("<OPTION VALUE=S>SI</SELECT></font></TD></TR>");
      else
        out.println("<OPTION VALUE=N>NO<OPTION VALUE=S>SI</SELECT></font></TD></TR>");

      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Prioridad Esquema:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SELECT NAME=priesq>");
      for (int i=0; i<v_opciones5.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones5.elementAt(i).toString()+"'>"+v_opciones5.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Contabilizar:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SELECT NAME=cont><OPTION VALUE=N>NO<OPTION VALUE=S>SI</SELECT></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Prioridad concepto:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=text NAME=obligatoriopricon MAXLENGTH=5 SIZE=5 onblur='if (esNumero(obligatoriopricon)==1) return false;'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Traslado:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SELECT NAME=tras><OPTION VALUE=N>NO<OPTION VALUE=S>SI</SELECT></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Retiro total:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SELECT NAME=retot><OPTION VALUE=N>NO<OPTION VALUE=S>SI</SELECT></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Retiro Penalizado:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SELECT NAME=retpen><OPTION VALUE=N>NO<OPTION VALUE=S>SI</SELECT></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Origen TaxBenefit:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SELECT NAME=retaxb><OPTION VALUE=N>NO<OPTION VALUE=S>SI</SELECT></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Plazo máximo sin cargo:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=text NAME=pmax MAXLENGTH=3 SIZE=3 value='null' onblur='if (esNumero(pmax)==1) return false;'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Plazo mínimo para validez de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=text NAME=pmin MAXLENGTH=3 SIZE=3 value='null' onblur='if (esNumero(pmin)==1) return false;'></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
     out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
     out.close();
      }



      private void TBPL_PublicarElimina(Vector v_descripcion, String v_codpro, String transac, String tipotransac, String clasetransac)
      {
      v_descripcion.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones por producto","Administración de conceptos de transacciones por producto", "TBPKT_PARAMETROS.TBPKT_PRODUCTOCONCEPTO.TBCS_EliminaProductoConcepto", "", true, "moduloparametro.js", "return checkrequired(this)"));

      //Aqui van los campo que se quieren mostrar

/*  <TR><TH> English </TH><TH> Spanish </TH><TH> German </TH></TR>
  <TR><TD> one     </TD><TD> uno     </TD><TD> ein    </TD></TR>
  <TR><TD> two     </TD><TD> dos     </TD><TD> zwei   </TD></TR>
  <TR><TD> three   </TD><TD> tres    </TD><TD> drei   </TD></TR>*/



      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Elimina conceptos de transacciones por producto</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Código del producto:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codpro value='"+v_codpro+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Transaccioón:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(1).toString())+"<INPUT TYPE=hidden NAME=transaccion value='"+transac+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Tipo de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(2).toString())+"<INPUT TYPE=hidden NAME=tipotransaccion value='"+tipotransac+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Clase de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(3).toString())+"<INPUT TYPE=hidden NAME=clasetransaccion value='"+clasetransac+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Método orden:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(4).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Método beneficio:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(5).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Método penalización:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(6).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Método cuenta:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(7).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Naturaleza:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(8).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Respetar naturaleza:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(9).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Prioridad Esquema:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(10).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Contabilizar:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(11).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Prioridad concepto:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(12).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Traslado:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(13).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Retiro total:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(14).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Retiro Penalizado:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(15).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Origen TaxBenefits:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+TBFL_ModificaNS(v_descripcion.elementAt(16).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Plazo máximo sin cargo</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(18).toString()+"&nbsp;días</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Plazo mínimo para validez de transacción</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(17).toString()+"&nbsp;días</font></TD></TR>");
      out.println("</font>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Eliminar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos

      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


  private String TBFL_ConsultaDescripcion(String v_valor)
  {
  String v_declaracion;
   //TBCL_Consulta v_Consulta = new TBCL_Consulta();
    //v_Consulta.TBPL_RealizarConexion();
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion;
    v_declaracion =  "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_valor+"'";
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
    //v_Consulta.TBPL_shutDown();
    return  v_resultadodeclaracion.elementAt(0).toString();
  }

  private String TBFL_ModificaNS(String v_valor)
  {
  String s = new String("No hay valor");
  if (v_valor.equals("N"))
    s = "NO";
  else if (v_valor.equals("S"))
    s = "SI";
  return s;
  }



  private Vector TBFL_ConsultaMetodoBeneficio(String v_valor)
  {
  Vector retorno = new Vector();
  if (v_valor.equals("S"))
    {
    retorno.addElement("APORTES CON BENEFICIO");
    retorno.addElement("APORTES SIN BENEFICIO");
    retorno.addElement("NO APLICA");
    }
  else if (v_valor.equals("N"))
    retorno.addElement("NO APLICA");
  return retorno;
  }

  private Vector TBFL_ConsultaMetodoPenalizacion(String v_valor)
  {
  Vector retorno = new Vector();
  if (v_valor.equals("S"))
    {
    retorno.addElement("APORTE PENALIZADO");
    retorno.addElement("APORTE NO PENALIZADO");
    retorno.addElement("NO APLICA");
    }
  else if (v_valor.equals("N"))
    retorno.addElement("NO APLICA");
  return retorno;
  }

  private Vector TBFL_ConsultaMetodoCuenta(String v_valor)
  {
  Vector retorno = new Vector();
  if (v_valor.equals("S"))
    {
    retorno.addElement("APORTE CON CUENTA CONTINGENTE");
    retorno.addElement("APORTE SIN CUENTA CONTINGENTE");
    retorno.addElement("NO APLICA");
    }
  else if (v_valor.equals("N"))
    retorno.addElement("NO APLICA");
  return retorno;
  }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_PRODUCTOCONCEPTO.TBCS_ACEMProductoConcepto Information";
  }
}

