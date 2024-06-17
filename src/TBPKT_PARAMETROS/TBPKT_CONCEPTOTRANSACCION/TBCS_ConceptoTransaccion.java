package TBPKT_PARAMETROS.TBPKT_CONCEPTOTRANSACCION;//Paquete de clases de conceptos tipo cargo


//Paquetes que utilizaremos en esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;



//Nombre de la clase
public class TBCS_ConceptoTransaccion extends HttpServlet{
private PrintWriter out;//clase que nos sirve para mostrar el código HTML
private TBCL_Consulta v_Consulta;//Clase que ejecuta las Consultas y me devulve un vector de respuesta
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
    String v_declaracion;//Cadena donde se guardará la declaración SQL
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2 , v_resultadodeclaracion3, v_resultadodeclaracion4;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


//------------------------------------------


      v_declaracion = "SELECT ref_codigo, ref_descripcion  FROM tbreferencias WHERE ref_codigo like 'STR%' AND ref_codigo > 'STR000'";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

      v_declaracion = "SELECT ref_codigo, ref_descripcion  FROM tbreferencias WHERE ref_codigo like 'UTT%' AND ref_codigo > 'UTT000'";
      v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);

      v_declaracion = "SELECT ref_codigo, ref_descripcion  FROM tbreferencias WHERE ref_codigo like 'UCT%' AND ref_codigo > 'UCT000'";
      v_resultadodeclaracion2 = v_Consulta.TBFL_Consulta(v_declaracion);

      v_declaracion = "SELECT a.ref_descripcion, b.ref_descripcion, c.ref_descripcion, cot_descripcion FROM \n"+
                    " tbconceptos_transaccion, tbreferencias a, tbreferencias b, tbreferencias c \n"+
                    " WHERE cot_ref_transaccion = a.ref_codigo \n"+
                    " AND cot_ref_tipo_transaccion = b.ref_codigo \n"+
                    " AND cot_ref_clase_transaccion = c.ref_codigo \n"+
                    " ORDER BY cot_ref_transaccion, cot_ref_tipo_transaccion, cot_ref_tipo_transaccion";
      v_resultadodeclaracion3 = v_Consulta.TBFL_Consulta(v_declaracion);

      v_declaracion = "SELECT cot_ref_transaccion, cot_ref_tipo_transaccion, cot_ref_clase_transaccion, cot_descripcion from tbconceptos_transaccion \n"+
                    " ORDER BY cot_ref_transaccion, cot_ref_tipo_transaccion, cot_ref_tipo_transaccion";
      v_resultadodeclaracion4 = v_Consulta.TBFL_Consulta(v_declaracion);

      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      //Publicación del resultado de la Consulta en HTML
      TBPL_Publicar(v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2, v_resultadodeclaracion3, v_resultadodeclaracion4);
  }

      //Publicación en HTML de las v_opciones
      private void TBPL_Publicar(Vector v_opciones, Vector v_opciones1, Vector v_opciones2, Vector v_opciones3,  Vector v_opciones4)
      {
      v_opciones.trimToSize();
      v_opciones1.trimToSize();
      v_opciones2.trimToSize();
      v_opciones3.trimToSize();
      v_opciones4.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones","Administración de conceptos de transacciones", "TBPKT_PARAMETROS.TBPKT_CONCEPTOTRANSACCION.TBCS_ACEMConceptoTransaccion", "", true));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Consulta de conceptos transacción</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=5 rules=all width=\"100%\">");
      out.println("<TR class=\"td11\" borderColor=silver><TD width=\"5%\"><FONT color=white face=verdana size=1>&nbsp;</font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Tipo de transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Clase de transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Descripción</B></font></TD></TR>");
      for (int i=0; i<v_opciones3.size(); i+=4)
         {
         if (i<4)
           out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=RADIO CHECKED NAME=codigos VALUE='"+v_opciones4.elementAt(i).toString()+v_opciones4.elementAt(i+1).toString()+v_opciones4.elementAt(i+2).toString()+"'><!-- "+v_opciones4.elementAt(i+3).toString()+" --></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones3.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones3.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones3.elementAt(i+2).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones3.elementAt(i+3).toString()+"</font></TD></TR>");
         else
           out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=RADIO NAME=codigos VALUE='"+v_opciones4.elementAt(i).toString()+v_opciones4.elementAt(i+1).toString()+v_opciones4.elementAt(i+2).toString()+"'><!-- "+v_opciones4.elementAt(i+3).toString()+" --></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones3.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones3.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones3.elementAt(i+2).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones3.elementAt(i+3).toString()+"</font></TD></TR>");
         }
      out.println("</TABLE>");
      out.println("<BLOCKQUOTE>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='modifica' CHECKED>Modificar conceptos de transacciones<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='elimina'>Eliminar conceptos de transacciones<BR>");
      out.println("</font>");
      out.println("</BLOCKQUOTE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Transacción:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=transac>");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de transacción:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=tipotransac>");
      for (int i=0; i<v_opciones1.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones1.elementAt(i).toString()+"'>"+v_opciones1.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Clase de transación:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=clasetransac>");
      for (int i=0; i<v_opciones2.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones2.elementAt(i).toString()+"'>"+v_opciones2.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BLOCKQUOTE>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adiciona'>Adicionar conceptos de transacciones<BR>&nbsp;&nbsp;<BR>");
      out.println("</font>");
      out.println("</BLOCKQUOTE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONCEPTOTRANSACCION.TBCS_ConceptoTransaccion Information";
  }
}

