package TBPKT_PARAMETROS.TBPKT_CONCEPTOTIPOCARGO;//Paque donde estan las clases de concepto tiopo cargo

//Paquetes que se utilizaán en esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Nonmbre de la clae
public class TBCS_TransaccionesConceptoTipoCargo extends HttpServlet{
private PrintWriter out;//Clase que permite imprimiir código HTML
private TBCL_Consulta v_Consulta;//Clase que se encarga de ejecutar las declaraciones
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
    Vector v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion3;
    Vector v_resultadodeclaracion2 = new Vector();
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


//------------------------------------------

    //Toma el v_codigo del producto
    String v_codpro = "";

    //Toma de los parametros
    try{
       v_codpro = request.getParameter("v_codpro");
       }
    catch (Exception e) { e.printStackTrace(); }


      v_declaracion = "SELECT \n"+
                    "        prc_cot_ref_transaccion, \n"+
                    "        prc_cot_ref_tipo_transaccion, \n"+
                    "        prc_cot_ref_clase_transaccion, \n"+
                    "        cot_descripcion \n"+
                    "FROM    tbproductos_conceptos, tbconceptos_transaccion \n"+
                    "WHERE   prc_pro_codigo = '"+v_codpro+"' \n"+
                    "AND     prc_cot_ref_transaccion       = cot_ref_transaccion \n"+
                    "AND     prc_cot_ref_tipo_transaccion  = cot_ref_tipo_transaccion \n"+
                    "AND     prc_cot_ref_clase_transaccion = cot_ref_clase_transaccion";
      v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);
      v_resultadodeclaracion1.trimToSize();

      if (!v_resultadodeclaracion1.elementAt(0).toString().equals("No hay elementos"))
        {
        for (int i=0; i<v_resultadodeclaracion1.size(); i++)
           {
           v_declaracion = "SELECT ref_descripcion  FROM tbreferencias WHERE ref_codigo ='"+v_resultadodeclaracion1.elementAt(i)+"'";
           v_resultadodeclaracion2.add(v_Consulta.TBFL_Consulta(v_declaracion, "ref_descripcion").elementAt(0).toString());
           }
        TBPL_Publicar(v_codpro, v_resultadodeclaracion1, v_resultadodeclaracion2);//TBPL_Publicar v_codigo HTML para adicionar
        }
      else
        TBPL_Publicar(v_resultadodeclaracion1.elementAt(0).toString());//TBPL_Publicar código HTML si no hay nada para adicionar
      
        v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }

      private void TBPL_Publicar(String mensaje)//Publica código HTML si no hay elementos
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cargos por concepto de transacciones","Administración de cargos por concepto de transacciones","TBPKT_PARAMETROS.TBPKT_CONCEPTOTIPOCARGO.TBCS_ACEMConceptoTipoCargo", mensaje, false));
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-2);></FONT></CENTER>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

      private void TBPL_Publicar(String v_codpro, Vector v_opciones1, Vector v_opciones2)//Publica v_codigo HTML para adicionar, modificar o eliminar
      {
      v_opciones1.trimToSize();
      v_opciones2.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cargos por concepto de transacciones","Administración de cargos por concepto de transacciones","TBPKT_PARAMETROS.TBPKT_CONCEPTOTIPOCARGO.TBCS_ACEMConceptoTipoCargo", "", true, "moduloparametro.js", "return checkrequired(this)"));

      //Aqui van los campo que se quieren mostrar
      out.println("<BLOCKQUOTE>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='Consulta' CHECKED>Consultar cargos por conceptos de transacciones<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='elimina'>Eliminar cargos por conceptos de transacciones<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adiciona'>Adicionar cargos por conceptos de transacciones<BR>");
      out.println("</font>");
      out.println("</BLOCKQUOTE>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Conceptos de transacción para&nbsp;"+v_codpro+"<INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_codpro+"'></B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=4 rules=all width=\"100%\">");
      out.println("<TR class=\"td11\" borderColor=silver><TD width=\"5%\"><FONT color=white face=verdana size=1>&nbsp;</font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Tipo de transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Clase de transacción</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Descripción</B></font></TD></TR>");
      for (int i=0; i<v_opciones1.size(); i+=4)
         {
         if (i<3)
           out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=RADIO CHECKED NAME=transac VALUE='"+v_opciones1.elementAt(i).toString()+v_opciones1.elementAt(i+1).toString()+v_opciones1.elementAt(i+2).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones2.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones2.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones2.elementAt(i+2).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones1.elementAt(i+3).toString()+"</font></TD></TR>");
         else
           out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=RADIO NAME=transac VALUE='"+v_opciones1.elementAt(i).toString()+v_opciones1.elementAt(i+1).toString()+v_opciones1.elementAt(i+2).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones2.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones2.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones2.elementAt(i+2).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_opciones1.elementAt(i+3).toString()+"</font></TD></TR>");
         }
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
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
    return "TBPKT_CONCEPTOTIPOCARGO.TBCS_TransaccionesConceptoTipoCargo Information";
  }
}

