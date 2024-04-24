package TBPKT_PARAMETROS.TBPKT_CONTABILIDAD;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.GTBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Nombre de la clase que v_confirma la modificación de administración contable
public class TBCS_ConfirmaModificaContabilidad extends HttpServlet implements SingleThreadModel{
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
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       out = new PrintWriter (response.getOutputStream());
       response.setContentType("text/html");


    v_Consulta = new TBCL_Consulta();
    String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    //Toma el v_codigo del producto
    String v_codpro = "";
    String lineamov = "";


    //Toma de los parametros
    try{
       v_codpro = request.getParameter("v_codpro");
       lineamov = request.getParameter("lineamov");
       }
    catch (Exception e) { e.printStackTrace(); }



    //Realización de las Consultas para mostrar en la pagina
    //----------------------------------------------------------

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
                    " COB_SUBCUENTA_DOS, \n"+
                    "       ref_descripcion \n"+
                    "FROM   tbcontabilidades, tbreferencias WHERE "+
                    "       cob_pro_codigo = '"+v_codpro+"' "+
                    "AND    cob_ref_parametro_contable = ref_codigo (+)"+
                    "AND cob_linea_movimiento = "+lineamov;


    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

    v_declaracion =  "SELECT  ref_valor, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'UTI%' AND ref_codigo != 'UTI000'";
    v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);
    //----------------------------------------------------------
    v_declaracion =  "SELECT  ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'SCO%' AND ref_codigo != 'SCO000'";
    v_resultadodeclaracion2 = v_Consulta.TBFL_Consulta(v_declaracion);

      TBPL_Publicar(v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2);//TBPL_Publicar página HTML
      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }


      //Publicacion de la pagina de confirmación modifica contabilidad
      private void TBPL_Publicar(Vector v_descripcion, Vector v_opciones, Vector v_opciones1)
      {
      v_descripcion.trimToSize();
      v_opciones.trimToSize();
      v_opciones1.trimToSize();

      out.println(GTBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de parametrización contable de conceptos de transacciones por producto","Administración de parametrización contable de conceptos de transacciones por producto","TBPKT_PARAMETROS.TBPKT_CONTABILIDAD.TBCS_ModificaContabilidad", "", true));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificar parametrización contable de transacciones por producto</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Línea de movimiento:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"<INPUT TYPE=hidden NAME=obligatoriolinmov VALUE='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de movimiento:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"<INPUT TYPE=hidden NAME=dtipmov VALUE='"+v_descripcion.elementAt(2).toString()+"'><BR><SELECT NAME=tipomov>");
      if (v_descripcion.elementAt(2).toString().equals("D"))
         out.println("<OPTION SELECTED>D<OPTION>H</SELECT></font></TD></TR>");
       else
         out.println("<OPTION>D<OPTION SELECTED>H</SELECT></font></TD></TR>");

      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Compañía:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"<INPUT TYPE=hidden NAME=dcompania VALUE='"+v_descripcion.elementAt(3).toString()+"'><BR><INPUT TYPE=text NAME=obligatoriocompania MAXLENGTH=5 SIZE=5 VALUE='"+v_descripcion.elementAt(3).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Cuenta 1:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"<INPUT TYPE=hidden NAME=dcuentauno VALUE='"+v_descripcion.elementAt(4).toString()+"'><BR><INPUT TYPE=text NAME=obligatoriocuentauno MAXLENGTH=2 SIZE=2 VALUE='"+v_descripcion.elementAt(4).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Cuenta 2:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(5).toString()+"<INPUT TYPE=hidden NAME=dcuentados VALUE='"+v_descripcion.elementAt(5).toString()+"'><BR><INPUT TYPE=text NAME=obligatoriocuentados MAXLENGTH=2 SIZE=2 VALUE='"+v_descripcion.elementAt(5).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Subcuenta 1:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(6).toString()+"<INPUT TYPE=hidden NAME=dsubcuentauno VALUE='"+v_descripcion.elementAt(6).toString()+"'><BR><INPUT TYPE=text NAME=obligatoriosubcuentauno MAXLENGTH=2 SIZE=2 VALUE='"+v_descripcion.elementAt(6).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Subcuenta 2:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(12).toString()+"<INPUT TYPE=hidden NAME=dsubcuentados VALUE='"+v_descripcion.elementAt(12).toString()+"'><BR><INPUT TYPE=text NAME=subcuentados MAXLENGTH=2 SIZE=2 VALUE='"+v_descripcion.elementAt(12).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Unidad de negocio:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(7).toString()+"<INPUT TYPE=hidden NAME=dunineg VALUE='"+v_descripcion.elementAt(7).toString()+"'><BR><INPUT TYPE=text NAME=obligatoriounineg MAXLENGTH=5 SIZE=5 VALUE='"+v_descripcion.elementAt(7).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Signo:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(8).toString()+"<INPUT TYPE=hidden NAME=dsigno VALUE='"+v_descripcion.elementAt(8).toString()+"'><BR><SELECT NAME=signo>");
      if (v_descripcion.elementAt(8).toString().equals("+"))
         out.println("<OPTION SELECTED>+<OPTION>-</SELECT></font></TD></TR>");
       else
         out.println("<OPTION>+<OPTION SELECTED>-</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Descripción:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(9).toString()+"<INPUT TYPE=hidden NAME=v_ddescripcion VALUE='"+v_descripcion.elementAt(9).toString()+"'><BR><INPUT TYPE=text NAME=obligatoriodescripcion MAXLENGTH=30 SIZE=20 VALUE='"+v_descripcion.elementAt(9).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de identificación:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+TBCL_Consultatipoidentificacion(v_descripcion.elementAt(10).toString())+"<INPUT TYPE=hidden NAME=dtipoiden VALUE='"+v_descripcion.elementAt(10).toString()+"'><BR><SELECT NAME=tipoiden>");
      out.println("               <option VALUE ='   '>                         ");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
           if (v_descripcion.elementAt(10).toString().equals(v_opciones.elementAt(i).toString()))
             out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"' SELECTED>"+v_opciones.elementAt(i+1).toString());
           else
             out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"' >"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Número de identificación:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(11).toString()+"<INPUT TYPE=hidden NAME=dnumiden VALUE='"+v_descripcion.elementAt(11).toString()+"'><BR><INPUT TYPE=text NAME=numiden MAXLENGTH=13 SIZE=10 VALUE='"+v_descripcion.elementAt(11).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Parametrización contable:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(13).toString()+"<INPUT TYPE=hidden NAME=dparcont VALUE='"+v_descripcion.elementAt(13).toString()+"'><BR><SELECT NAME=parcont>");
      for (int i=0; i<v_opciones1.size(); i+=2)
         {
           if (v_descripcion.elementAt(13).toString().equals(v_opciones1.elementAt(i+1).toString()))
             out.println("<OPTION VALUE='"+v_opciones1.elementAt(i).toString()+"' SELECTED>"+v_opciones1.elementAt(i+1).toString());
           else
             out.println("<OPTION VALUE='"+v_opciones1.elementAt(i).toString()+"' >"+v_opciones1.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");      
      out.println("<CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      //Hasta aca van los campos
      out.println(GTBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


  //Consulta tipo de identificación de la tabla de referencias
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


  //Consulta de la tabla de referencias los codigos del tipo de identificación(Muestra la descripción)
  private String ConsultaDescripcion(String v_codigo)
  {
  //v_Consulta = new TBCL_Consulta();
  String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion;
    //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
    v_declaracion =  "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_codigo+"'";
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, "ref_descripcion");
    //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    return  v_resultadodeclaracion.elementAt(0).toString();
  }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONTABILIDAD.TBCS_ConfirmaModificaContabilidad Information";
  }
}

