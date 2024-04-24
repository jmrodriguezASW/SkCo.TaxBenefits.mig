package TBPKT_PARAMETROS.TBPKT_CONTABILIDAD;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.GTBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


//Nombre de la clase que v_confirma la eliminación de contabilidad
public class TBCS_ConfirmaEliminaContabilidad extends HttpServlet{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   * Process the HTTP Get request
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       out = new PrintWriter (response.getOutputStream());
       response.setContentType("text/html");


    v_Consulta = new TBCL_Consulta();
    String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion, v_resultadodeclaracion1;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


    //Toma el v_codigo del producto
    String v_codpro = "";
    //Toma la linea de movimiento de contabilidad
    String lineamov = "";


    //Toma de los parametros
    try{
       v_codpro = request.getParameter("v_codpro");
       lineamov = request.getParameter("lineamov");
       }
    catch (Exception e) { e.printStackTrace(); }


    //Consulta para mostrar la página de v_confirma elimina
    //-----------------------------------------------------
      v_declaracion = "SELECT \n"+
                    " COB_PRO_CODIGO, \n"+
                    " COB_LINEA_MOVIMIENTO, \n"+
                    " COB_TIPO_MOVIMIENTO, \n"+
                    " COB_COMPANIA, \n"+
                    " COB_CUENTA_UNO, \n"+
                    " COB_CUENTA_DOS, \n"+
                    " COB_SUBCUENTA_UNO, \n"+
                    " COB_UNIDAD_NEGOCIO, \n"+
                    " COB_SIGNO, \n"+
                    " COB_DESCRIPCION, \n"+
                    " COB_TIPO_IDENTIFICACION, \n"+
                    " COB_NUMERO_IDENTIFICACION, \n"+
                    " COB_SUBCUENTA_DOS,  \n"+
                    "       ref_descripcion \n"+
                    "FROM   tbcontabilidades, tbreferencias WHERE "+
                    "       cob_pro_codigo = '"+v_codpro+"' "+
                    "AND    cob_ref_parametro_contable = ref_codigo (+)"+
                    "AND    cob_linea_movimiento = "+lineamov;


    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
    v_declaracion =  "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'UTI%'";
    v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);
    //-----------------------------------------------------

      TBPL_Publicar(v_resultadodeclaracion, v_resultadodeclaracion1);//TBPL_Publicar la página
      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
   }



      private void TBPL_Publicar(Vector v_descripcion, Vector v_opciones)//Publica página HTML
      {
      v_descripcion.trimToSize();
      v_opciones.trimToSize();

      out.println(GTBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de parametrización contable de conceptos de transacciones por producto","Administración de parametrización contable de conceptos de transacciones por producto","TBPKT_PARAMETROS.TBPKT_CONTABILIDAD.TBCS_EliminaContabilidad", "", true));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Eliminar parametrización contable de transacciones por producto</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codpro value='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Línea de movimiento:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"<INPUT TYPE=hidden NAME=linmov value='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de movimiento:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Compañía:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta 1:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta 2:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(5).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Subcuenta 1:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(6).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Subcuenta 2:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(12).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Unidad de negocio:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(7).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Signo:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(8).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(9).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de identificación:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+TBCL_Consultatipoidentificacion(v_descripcion.elementAt(10).toString())+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Número de identificación:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(11).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Parametrización contable:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(13).toString()+"</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Eliminar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      //Hasta aca van los campos
      out.println(GTBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



  //Consulta el tipo de identificación de la tabla de referencias(Este metodo me perimte mostrar la descripción y no el código)
  private String TBCL_Consultatipoidentificacion(String v_valor)
  {
  //v_Consulta = new TBCL_Consulta();
  String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion;
    //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
    v_declaracion =  "SELECT ref_descripcion FROM tbreferencias WHERE ref_valor = '"+v_valor+"' AND ref_codigo LIKE 'UTI%'";
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
    //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    return  v_resultadodeclaracion.elementAt(0).toString();
  }




  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONTABILIDAD.TBCS_ConfirmaEliminaContabilidad Information";
  }
}

