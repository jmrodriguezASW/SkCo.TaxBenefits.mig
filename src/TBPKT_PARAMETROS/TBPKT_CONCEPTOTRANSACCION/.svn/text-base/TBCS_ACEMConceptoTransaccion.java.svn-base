package TBPKT_PARAMETROS.TBPKT_CONCEPTOTRANSACCION;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_ACEMConceptoTransaccion extends HttpServlet implements SingleThreadModel{
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
       response.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
       // response.setHeader("Pragma", "No-Cache");
       // response.setDateHeader("Expires", 0);
       String parametros[] = new String[8];
       String  cadena = request.getParameter("cadena");
       v_nuevaCadena = cadena;
       String ip_tax = request.getRemoteAddr();
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       out = new PrintWriter (response.getOutputStream());
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

    v_Consulta = new TBCL_Consulta();
    String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    //Toma la opción de modificar, eliminar, adicionar o Consultar
    String v_opcion = "Consulta";
    //Toma los diferentes codigos de producto
    String transac = "";
    //Toma el tipo de transacción de contabilidad
    String tipotransac = "";
    //Toma la clase de transaccion
    String clasetransac = "";
    //Toma los diferentes codigos de producto
    String v_ctransac = "";
    //Toma el tipo de transacción de contabilidad
    String v_ctipotransac = "";
    //Toma la clase de transaccion
    String v_cclasetransac = "";
    String codigos = "";    


    //Toma los parametros
    try{
       v_opcion = request.getParameter("v_opcion");
       codigos = request.getParameter("codigos");
       transac = request.getParameter("transac");
       tipotransac = request.getParameter("tipotransac");
       clasetransac = request.getParameter("clasetransac");
       }
    catch (Exception e) { e.printStackTrace(); }

    v_ctransac = codigos.substring(0,6);
    v_ctipotransac = codigos.substring(6,12);
    v_cclasetransac = codigos.substring(12);


 if (v_opcion.equals("adiciona"))//Si la opción es adicionar
     {
     v_declaracion = "SELECT\n"+
"                   a.ref_descripcion,\n"+
"                   b.ref_descripcion,\n"+
"                   c.ref_descripcion\n"+
"FROM\n"+
"                   tbreferencias a,\n"+
"                   tbreferencias b,\n"+
"                   tbreferencias c\n"+
"WHERE              a.ref_codigo   = '"+transac+"'\n"+
"AND                b.ref_codigo   = '"+tipotransac+"'\n"+
"AND                c.ref_codigo   = '"+clasetransac+"'";

      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      
      TBPL_PublicarAdiciona(v_resultadodeclaracion, transac, tipotransac, clasetransac);//Publica adiciona
     }
   else if (v_opcion.equals("elimina"))//si la opción es elimina Consulta las v_opciones a mostrar
     {
     v_declaracion = "SELECT\n"+
"                   a.ref_descripcion,\n"+
"                   b.ref_descripcion,\n"+
"                   c.ref_descripcion,\n"+
"                   cot_descripcion\n"+
"FROM               tbconceptos_transaccion,\n"+
"                   tbreferencias a,\n"+
"                   tbreferencias b,\n"+
"                   tbreferencias c\n"+
"WHERE              cot_ref_transaccion  = '"+v_ctransac+"'\n"+
"AND                cot_ref_tipo_transaccion  = '"+v_ctipotransac+"'\n"+
"AND                cot_ref_clase_transaccion  = '"+v_cclasetransac+"'\n"+
"AND                cot_ref_transaccion = a.ref_codigo\n"+
"AND                cot_ref_tipo_transaccion = b.ref_codigo\n"+
"AND                cot_ref_clase_transaccion = c.ref_codigo";

      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);


      
      TBPL_PublicarElimina(v_resultadodeclaracion, v_ctransac, v_ctipotransac, v_cclasetransac); //TBPL_Publicar pagina de eliminar
        }
   else if (v_opcion.equals("modifica"))//Si es modifica Consulta para mostrar la pagina de modificar
     {
     v_declaracion = "SELECT\n"+
"                   a.ref_descripcion,\n"+
"                   b.ref_descripcion,\n"+
"                   c.ref_descripcion,\n"+
"                   cot_descripcion\n"+
"FROM               tbconceptos_transaccion,\n"+
"                   tbreferencias a,\n"+
"                   tbreferencias b,\n"+
"                   tbreferencias c\n"+
"WHERE              cot_ref_transaccion  = '"+v_ctransac+"'\n"+
"AND                cot_ref_tipo_transaccion  = '"+v_ctipotransac+"'\n"+
"AND                cot_ref_clase_transaccion  = '"+v_cclasetransac+"'\n"+
"AND                cot_ref_transaccion = a.ref_codigo\n"+
"AND                cot_ref_tipo_transaccion = b.ref_codigo\n"+
"AND                cot_ref_clase_transaccion = c.ref_codigo";

      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      
      TBPL_PublicarModifica(v_resultadodeclaracion, v_ctransac, v_ctipotransac, v_cclasetransac);//TBPL_Publicar  la pagina de modificar
     }
  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }






      private void TBPL_PublicarAdiciona(Vector v_descripcion, String transac, String tipotransac, String clasetransac)//Publica la pagina de adicionar
      {

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones", "Administración de conceptos de transacciones","TBPKT_PARAMETROS.TBPKT_CONCEPTOTRANSACCION.TBCS_AdicionaConceptoTransaccion", "", true, "moduloparametro.js", "return checkrequired(this)"));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=transac VALUE='"+transac+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Tipo de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(1).toString()+"<INPUT TYPE=hidden NAME=tipotransac VALUE='"+tipotransac+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Clase de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(2).toString()+"<INPUT TYPE=hidden NAME=clasetransac VALUE='"+clasetransac+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Descripción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=text NAME=obligatoriodescripcion MAXLENGTH=100 SIZE=50></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



      private void TBPL_PublicarElimina(Vector v_descripcion, String v_ctransac, String v_ctipotransac, String v_cclasetransac)//TBPL_Publicar la pagina de elimina
      {
      v_descripcion.trimToSize();

    if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones","Administración de conceptos de transacciones","TBPKT_PARAMETROS.TBPKT_CONCEPTOTRANSACCION.TBCS_EliminaConceptoTransaccion","",true, "moduloparametro.js", "return checkrequired(this)"));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=transac VALUE='"+v_ctransac+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Tipo de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(1).toString()+"<INPUT TYPE=hidden NAME=tipotransac VALUE='"+v_ctipotransac+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Clase de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(2).toString()+"<INPUT TYPE=hidden NAME=clasetransac VALUE='"+v_cclasetransac+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Descripción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Eliminar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      }
      //Hasta aca van los campos
   else
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones","Administración de conceptos de transacciones", "", v_descripcion.elementAt(0).toString(), false, "moduloparametro.js", "return checkrequired(this)"));

     out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
     out.close();
      }

      private void TBPL_PublicarModifica(Vector v_descripcion, String v_ctransac, String v_ctipotransac, String v_cclasetransac)//TBPL_Publicar la pagina de modificar
      {
     if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones","Administración de conceptos de transacciones","TBPKT_PARAMETROS.TBPKT_CONCEPTOTRANSACCION.TBCS_ModificaConceptoTransaccion","",true, "moduloparametro.js", "return checkrequired(this)"));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=transac VALUE='"+v_ctransac+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Tipo de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(1).toString()+"<INPUT TYPE=hidden NAME=tipotransac VALUE='"+v_ctipotransac+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Clase de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(2).toString()+"<INPUT TYPE=hidden NAME=clasetransac VALUE='"+v_cclasetransac+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Descripción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(3).toString()+"<INPUT TYPE=hidden NAME=v_ddescripcion VALUE='"+v_descripcion.elementAt(3).toString()+"'><BR><INPUT TYPE=text NAME=obligatoriodescripcion MAXLENGTH=100 SIZE=50 VALUE='"+v_descripcion.elementAt(3).toString()+"'></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      }
   else
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones","Administración de conceptos de transacciones", "", v_descripcion.elementAt(0).toString(), false));

     out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
     out.close();
      }



  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONCEPTOTRANSACCION.TBCS_ACEMConceptoTransaccion Information";
  }
}

