package TBPKT_PARAMETROS.TBPKT_CONCEPTOTIPOCARGO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_ACEMConceptoTipoCargo extends HttpServlet{
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
        
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
       out = new PrintWriter (response.getOutputStream());
       parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

    v_Consulta = new TBCL_Consulta();
    String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion, v_resultadodeclaracion1;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    //Toma la opción de modificar, eliminar, adicionar o Consultar
    String v_opcion = "Consulta";
    //Toma los diferentes codigos de producto
    String v_codpro = "";
    //Toma el v_codigo de las transacciones
    String codigos = "";
    //Toma la transaccion de la contabilidad
    String transac = "";
    //Toma el tipo de transacción de contabilidad
    String tipotransac = "";
    //Toma la clase de transaccion
    String clasetransac = "";


    //Toma los parametros
    try{
       v_opcion = request.getParameter("v_opcion");
       v_codpro = request.getParameter("v_codpro");
       codigos = request.getParameter("transac");
       }
    catch (Exception e) { e.printStackTrace(); }

    transac = codigos.substring(0,6);
    tipotransac = codigos.substring(6,12);
    clasetransac = codigos.substring(12);


   //Si se seleciona Consultar realiza la respectiva Consulta de concepto tipo cargo
   if (v_opcion.equals("Consulta"))
     {

     v_declaracion = "SELECT a.ref_descripcion, b.ref_descripcion, c.ref_descripcion, d.ref_descripcion FROM \n"+
                   " tbconceptos_tipos_cargo, tbreferencias a, tbreferencias b, tbreferencias c, tbreferencias d \n"+
                   " WHERE ctc_prc_cot_ref_transaccion = a.ref_codigo \n"+
                   " AND ctc_prc_cot_ref_tipo_trans = b.ref_codigo \n"+
                   " AND ctc_prc_cot_ref_clase_trans = c.ref_codigo \n"+
                   " AND ctc_ref_cargo = d.ref_codigo \n"+
                   " AND ctc_prc_pro_codigo = '"+v_codpro+"' \n"+
                   " AND ctc_prc_cot_ref_transaccion = '"+transac+"' \n"+
                   " AND ctc_prc_cot_ref_tipo_trans = '"+tipotransac+"' \n"+
                   " AND ctc_prc_cot_ref_clase_trans = '"+clasetransac+"'";

      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      TBPL_PublicarConsulta(v_codpro, v_resultadodeclaracion);//Publica la pagina de Consultas
     }
   else if (v_opcion.equals("adiciona"))//Si selecciona adiciona realiza las siguientes Consultas
     {

      v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'STC%' AND ref_codigo != 'STC000'";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

     v_declaracion = "SELECT a.ref_descripcion, b.ref_descripcion, c.ref_descripcion, d.ref_descripcion FROM \n"+
                   " tbconceptos_tipos_cargo, tbreferencias a, tbreferencias b, tbreferencias c, tbreferencias d \n"+
                   " WHERE ctc_prc_cot_ref_transaccion = a.ref_codigo \n"+
                   " AND ctc_prc_cot_ref_tipo_trans = b.ref_codigo \n"+
                   " AND ctc_prc_cot_ref_clase_trans = c.ref_codigo \n"+
                   " AND ctc_ref_cargo = d.ref_codigo \n"+
                   " AND ctc_prc_pro_codigo = '"+v_codpro+"' \n"+
                   " AND ctc_prc_cot_ref_transaccion = '"+transac+"' \n"+
                   " AND ctc_prc_cot_ref_tipo_trans = '"+tipotransac+"' \n"+
                   " AND ctc_prc_cot_ref_clase_trans = '"+clasetransac+"'";

      v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);

      //Publica la pagina de adicionar
      TBPL_PublicarAdiciona(v_codpro, TBFL_ConsultaDescripcion(transac), TBFL_ConsultaDescripcion(tipotransac), TBFL_ConsultaDescripcion(clasetransac), v_resultadodeclaracion, v_resultadodeclaracion1, transac, tipotransac, clasetransac);
     }
   else if (v_opcion.equals("elimina"))//Si se selecciona eliminar realiza las Consultas para la pagina de eliminar
     {
     v_declaracion = "SELECT a.ref_descripcion, b.ref_descripcion, c.ref_descripcion, d.ref_descripcion FROM \n"+
                   " tbconceptos_tipos_cargo, tbreferencias a, tbreferencias b, tbreferencias c, tbreferencias d \n"+
                   " WHERE ctc_prc_cot_ref_transaccion = a.ref_codigo \n"+
                   " AND ctc_prc_cot_ref_tipo_trans = b.ref_codigo \n"+
                   " AND ctc_prc_cot_ref_clase_trans = c.ref_codigo \n"+
                   " AND ctc_ref_cargo = d.ref_codigo \n"+
                   " AND ctc_prc_pro_codigo = '"+v_codpro+"' \n"+
                   " AND ctc_prc_cot_ref_transaccion = '"+transac+"' \n"+
                   " AND ctc_prc_cot_ref_tipo_trans = '"+tipotransac+"' \n"+
                   " AND ctc_prc_cot_ref_clase_trans = '"+clasetransac+"'";

      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      TBPL_PublicarElimina(v_codpro, v_resultadodeclaracion, transac, tipotransac, clasetransac);//Publica pagina de eliminar
     }
  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }


      private void TBPL_PublicarConsulta(String v_codpro, Vector v_descripcion)//TBPL_Publicar Consulta
      {
      v_descripcion.trimToSize();

    if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cargos por conceptos de transacciones","Administración de cargos por conceptos de transacciones", "", "", true));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Código del producto:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_codpro+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(0).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Tipo de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Clase de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Tipo cargo:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      for (int i=3; i<v_descripcion.size(); i+=4)
         out.println(v_descripcion.elementAt(i).toString()+"<BR>");
      out.println("</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      }
      //Hasta aca van los campos
   else
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cargos por conceptos de transacciones","Administración de cargos por conceptos de transacciones", "", v_descripcion.elementAt(0).toString(), false));

     out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></FONT></CENTER>");
     out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
     out.close();
      }





      private void TBPL_PublicarAdiciona(String v_codpro, String transac, String tipotransac, String clasetransac, Vector v_opciones, Vector v_descripcion, String ctransac, String ctipotransac, String cclasetransac)//TBPL_Publicar adicionar
      {
      v_opciones.trimToSize();
      v_descripcion.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cargos por conceptos de transacciones","Administración de cargos por conceptos de transacciones","TBPKT_PARAMETROS.TBPKT_CONCEPTOTIPOCARGO.TBCS_AdicionaConceptoTipoCargo","",true, "moduloparametro.js", "return checkrequired(this)"));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Código del producto:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_codpro+"<INPUT TYPE=HIDDEN NAME=v_codpro VALUE='"+v_codpro+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+transac+"<INPUT TYPE=HIDDEN NAME=transac VALUE='"+ctransac+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Tipo de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+tipotransac+"<INPUT TYPE=HIDDEN NAME=tipotransac VALUE='"+ctipotransac+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Clase de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+clasetransac+"<INPUT TYPE=HIDDEN NAME=clasetransac VALUE='"+cclasetransac+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Tipo cargo:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      for (int i=3; i<v_descripcion.size(); i+=4)
         out.println(v_descripcion.elementAt(i).toString()+"<BR>");
//      out.println("<SELECT NAME=cargo>");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         if (!v_descripcion.contains(v_opciones.elementAt(i+1).toString()))
           out.println("<INPUT TYPE=checkbox NAME=cargo VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString()+"<BR>");
//         out.println("<OPTION>"+v_opciones.elementAt(i).toString());
         }
      out.println("</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos

      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



      private void TBPL_PublicarElimina(String v_codpro, Vector v_descripcion, String ctransac, String ctipotransac, String cclasetransac)//TBPL_Publicar eliminar
      {
      v_descripcion.trimToSize();

    if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cargos por conceptos de transacciones","Administración de cargos por conceptos de transacciones", "TBPKT_PARAMETROS.TBPKT_CONCEPTOTIPOCARGO.TBCS_EliminaConceptoTipoCargo", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Código del producto:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=HIDDEN NAME=v_codpro   VALUE='"+v_codpro+"'>"+v_codpro+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=HIDDEN NAME=transaccion   VALUE='"+ctransac+"'>"+v_descripcion.elementAt(0).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Tipo de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=HIDDEN NAME=tipotransaccion VALUE='"+ctipotransac+"'>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Clase de transacción:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=HIDDEN NAME=clasetransaccion VALUE='"+cclasetransac+"'>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Tipo cargo:</B></font></TD><TD><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      for (int i=3; i<v_descripcion.size(); i+=4)
         out.println(v_descripcion.elementAt(i).toString()+"<BR>");
      out.println("</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Eliminar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      }
      //Hasta aca van los campos
   else
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cargos por conceptos de transacciones","Administración de cargos por conceptos de transacciones", "", v_descripcion.elementAt(0).toString(), false));
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></FONT></CENTER>");
      }

     out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
     out.close();
      }


  //Consulta las descripciones de la tabla de referenias, no permite mostrar un v_codigo intendible
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

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONCEPTOTIPOCARGO.TBCS_ACEMConceptoTipoCargo Information";
  }
}

