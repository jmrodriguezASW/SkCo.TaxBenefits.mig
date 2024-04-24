package TBPKT_PARAMETROS.TBPKT_PARAMETRIZACION_CONTRATOS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_CM_Contratos extends HttpServlet implements SingleThreadModel{
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
    String declaracion;
    //Vector donde se guardara el resultado de la declaracion
    Vector resultadodeclaracion, resultadodeclaracion1, resultadodeclaracion2, resultadodeclaracion3, resultadodeclaracion4, resultadodeclaracion5, resultadodeclaracion6;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    //Toma la opción de modificar, eliminar, adicionar o consultar
    String opcion = "v_dconsulta";
    //Toma los diferentes codigos de producto
    String v_codpro = "";
    String grupoempresa = "";
    String v_contrato = "";
    int v_contratolength = 0;

    try{
       opcion = request.getParameter("opcion");
       v_codpro = request.getParameter("v_codpro");
       v_contrato = request.getParameter("obligatoriocontrato").trim();
       /*Cambio para manejo de referencia unica 2009/04/01 MOS */
       v_contrato = v_Consulta.TBFL_ConsultaSinRefUnica(v_contrato);
       }
    catch (Exception e) { e.printStackTrace(); }

     v_contratolength = v_contrato.length();

     switch (v_contratolength)
           {
           case 1:
               v_contrato = "00000000"+v_contrato;
           break;
           case 2:
               v_contrato = "0000000"+v_contrato;
           break;
           case 3:
               v_contrato = "000000"+v_contrato;
           break;
           case 4:
               v_contrato = "00000"+v_contrato;
           break;
           case 5:
               v_contrato = "0000"+v_contrato;
           break;
           case 6:
               v_contrato = "000"+v_contrato;
           break;
           case 7:
               v_contrato = "00"+v_contrato;
           break;
           case 8:
               v_contrato = "0"+v_contrato;
           break;
           default:
               v_contrato = v_contrato;
           break;
           }


   if (!(v_contrato.indexOf("'")==-1))
     {
     TBPL_PublicarError();
     }
   else if (opcion.equals("v_dconsulta"))
     {
     declaracion = "SELECT CON_PRO_CODIGO, \n"+
                   " CON_NUMERO, \n"+
                   " a.ref_descripcion, \n"+
                   " CON_NUMERO_IDENTIFICACION, \n"+
                   " CON_NOMBRES, \n"+
                   " CON_APELLIDOS, \n"+
                   " to_char(CON_FECHA_APERTURA, 'rrrr-mm-dd'), \n"+
                   " to_char(CON_FECHA_CANCELACION, 'rrrr-mm-dd'), \n"+
                   " b.ref_descripcion, \n"+
                   " c.ref_descripcion, \n"+
                   " d.ref_descripcion, \n"+
                   " e.ref_descripcion, \n"+
                   " f.ref_descripcion, \n"+
                   " CON_RESPETAR_NATURALEZA, \n"+
                   " CON_DIGITO_VERIFICACION, \n"+
                   " to_char(CON_FECHA_CARGUE, 'rrrr-mm-dd'), \n"+
                   " CON_TRANSACCION \n"+
                   "FROM   TBCONTRATOS \n"+
                   "     , tbreferencias a \n"+
                   "     , tbreferencias b \n"+
                   "     , tbreferencias c \n"+
                   "     , tbreferencias d \n"+
                   "     , tbreferencias e \n"+
                   "     , tbreferencias f \n"+
                   "WHERE  CON_PRO_CODIGO = '"+v_codpro+"' \n"+
                   "AND    CON_NUMERO = '"+v_contrato+"' \n"+
"AND CON_REF_TIPO_IDENTIFICACION  = a.ref_codigo (+) \n"+
"AND CON_REF_METODO_ORDEN = b.ref_codigo (+) \n"+
"AND CON_REF_METODO_BENEFICIO = c.ref_codigo (+) \n"+
"AND CON_REF_METODO_PENALIZACION = d.ref_codigo (+) \n"+
"AND CON_REF_METODO_CUENTA = e.ref_codigo (+) \n"+
"AND CON_REF_NATURALEZA = f.ref_codigo (+)";
     resultadodeclaracion = v_Consulta.TBFL_Consulta(declaracion);

     //cierra la conexion con la base de datos
     //v_Consulta.TBPL_shutDown();
     //publicacion en pagina html de las consultas
     PublicarConsulta(resultadodeclaracion);
     }
   else if (opcion.equals("modifica"))
     {
     declaracion = "SELECT CON_PRO_CODIGO, \n"+
                   " CON_NUMERO, \n"+
                   " a.ref_descripcion, \n"+
                   " CON_NUMERO_IDENTIFICACION, \n"+
                   " CON_NOMBRES, \n"+
                   " CON_APELLIDOS, \n"+
                   " to_char(CON_FECHA_APERTURA, 'rrrr-mm-dd'), \n"+
                   " to_char(CON_FECHA_CANCELACION, 'rrrr-mm-dd'), \n"+
                   " b.ref_descripcion, \n"+
                   " c.ref_descripcion, \n"+
                   " d.ref_descripcion, \n"+
                   " e.ref_descripcion, \n"+
                   " f.ref_descripcion, \n"+
                   " CON_RESPETAR_NATURALEZA, \n"+
                   " CON_DIGITO_VERIFICACION, \n"+
                   " to_char(CON_FECHA_CARGUE, 'rrrr-mm-dd'), \n"+
                   " CON_TRANSACCION \n"+
                   "FROM   TBCONTRATOS \n"+
                   "     , tbreferencias a \n"+
                   "     , tbreferencias b \n"+
                   "     , tbreferencias c \n"+
                   "     , tbreferencias d \n"+
                   "     , tbreferencias e \n"+
                   "     , tbreferencias f \n"+
                   "WHERE  CON_PRO_CODIGO = '"+v_codpro+"' \n"+
                   "AND    CON_NUMERO = '"+v_contrato+"' \n"+
"AND CON_REF_TIPO_IDENTIFICACION  = a.ref_codigo (+) \n"+
"AND CON_REF_METODO_ORDEN = b.ref_codigo (+) \n"+
"AND CON_REF_METODO_BENEFICIO = c.ref_codigo (+) \n"+
"AND CON_REF_METODO_PENALIZACION = d.ref_codigo (+) \n"+
"AND CON_REF_METODO_CUENTA = e.ref_codigo (+) \n"+
"AND CON_REF_NATURALEZA = f.ref_codigo (+)";
     resultadodeclaracion = v_Consulta.TBFL_Consulta(declaracion);

      declaracion = " SELECT pro_metodo_orden, \n"+
                        " pro_metodo_beneficio, \n"+
                        " pro_metodo_penalizacion, \n"+
                        " pro_metodo_cuenta, \n"+
                        " pro_naturaleza_retiro \n"+
                    " FROM   tbproductos \n"+
                    " WHERE  pro_codigo = '"+v_codpro+"'";
      resultadodeclaracion6 = v_Consulta.TBFL_Consulta(declaracion);

      resultadodeclaracion1 = TBFL_ConsultaMetodoOrden(resultadodeclaracion6.elementAt(0).toString());

      resultadodeclaracion2 = TBFL_ConsultaMetodoBeneficio(resultadodeclaracion6.elementAt(1).toString());

      resultadodeclaracion3 = TBFL_ConsultaMetodoPenalizacion(resultadodeclaracion6.elementAt(2).toString());

      resultadodeclaracion4 = TBFL_ConsultaMetodoCuenta(resultadodeclaracion6.elementAt(3).toString());

      resultadodeclaracion5 = TBFL_ConsultaNaturalezaRetiro(resultadodeclaracion6.elementAt(4).toString());

     //cierra la conexion con la base de datos
     //v_Consulta.TBPL_shutDown();
     //publicacion en pagina html de las consultas
     PublicarModifica(resultadodeclaracion, resultadodeclaracion1, resultadodeclaracion2, resultadodeclaracion3, resultadodeclaracion4, resultadodeclaracion5);
     }
  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }

      private void PublicarConsulta(Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
         {
         for (int i = 0; i<v_descripcion.size(); i++)
            if (v_descripcion.elementAt(i).toString().equals("null"))
              v_descripcion.setElementAt("-", i);

         out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de contratos","Administración contratos", "", "", true));
         /*Cambio para manejo de referencia unica 2009/04/01 MOS */
         String v_contrato_unif = "";
        // v_Consulta.TBPL_RealizarConexion();
         v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_descripcion.elementAt(0).toString(), v_descripcion.elementAt(1).toString());
         //v_Consulta.TBPL_shutDown();
         
         out.println("<BR>&nbsp;&nbsp;<BR>");
         out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                     "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Consultar contrato</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
         out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Contrato numero</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_contrato_unif+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de identificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Numero de identificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Nombres</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Apellidos</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(5).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha apertura</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(6).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha cancelación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(7).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Metodo orden</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(8).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Metodo beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(9).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Metodo penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(10).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Metodo cuenta</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(11).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Naturaleza</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(12).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Respetar naturaleza</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+TBFL_ModificaNS(v_descripcion.elementAt(13).toString())+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Digito verificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(14).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha cargue</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(15).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Transaccion</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(16).toString()+"</font></TD></TR>");
         out.println("</TABLE>");
         out.println("<BR>&nbsp;&nbsp;<BR>");
         out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
         }
      else
         {
         out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de contratos","Administración contratos", "", "<BLOCKQUOTE>"+v_descripcion.elementAt(0).toString()+"</BLOCKQUOTE>", false));
         out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
         }
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }




      private void PublicarModifica(Vector v_descripcion, Vector v_opciones, Vector v_opciones1, Vector v_opciones2, Vector v_opciones3, Vector v_opciones4)
      {
      v_descripcion.trimToSize();
      v_opciones.trimToSize();
      v_opciones1.trimToSize();
      v_opciones2.trimToSize();
      v_opciones3.trimToSize();
      v_opciones4.trimToSize();


      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
         {
         for (int i = 0; i<v_descripcion.size(); i++)
            if (v_descripcion.elementAt(i).toString().equals("null"))
              v_descripcion.setElementAt("-", i);

         out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de contratos","Administración de contratos", "TBPKT_PARAMETROS.TBPKT_PARAMETRIZACION_CONTRATOS.TBCS_Modifica_Contrato", "", true));
         out.println("<BR>&nbsp;&nbsp;<BR>");
         out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                     "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificar contrato</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
         out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
         /*Cambio para manejo de referencia unica 2009/04/01 MOS */
         String v_contrato_unif = "";
         //v_Consulta.TBPL_RealizarConexion();
         v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_descripcion.elementAt(0).toString(), v_descripcion.elementAt(1).toString());
         //v_Consulta.TBPL_shutDown();
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Contrato numero</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_contrato_unif+"<INPUT TYPE=hidden NAME=v_contrato VALUE='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");

         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de identificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Numero de identificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Nombres</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Apellidos</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(5).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha apertura</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(6).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha cancelación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(7).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Metodo orden</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(8).toString()+"<BR><SELECT NAME=metord>");
         for (int i=0; i<v_opciones.size(); i+=2)
            {
            if (v_descripcion.elementAt(8).toString().equals(v_opciones.elementAt(i+1).toString()))
              out.println("<OPTION SELECTED VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
            else
              out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
            }
         if (v_descripcion.elementAt(8).toString().equals("-"))
           out.println("<OPTION SELECTED VALUE='null'>");
         else
           out.println("<OPTION VALUE='null'>");
         out.println("</SELECT></font></TD></TR>");

         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Metodo beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(9).toString()+"<BR><SELECT NAME=metben>");
         for (int i=0; i<v_opciones1.size(); i+=2)
            {
            if (v_descripcion.elementAt(9).toString().equals(v_opciones1.elementAt(i+1).toString()))
              out.println("<OPTION SELECTED VALUE='"+v_opciones1.elementAt(i).toString()+"'>"+v_opciones1.elementAt(i+1).toString());
            else
              out.println("<OPTION VALUE='"+v_opciones1.elementAt(i).toString()+"'>"+v_opciones1.elementAt(i+1).toString());
            }
         if (v_descripcion.elementAt(9).toString().equals("-"))
           out.println("<OPTION SELECTED VALUE='null'>");
         else
           out.println("<OPTION VALUE='null'>");
         out.println("</SELECT></font></TD></TR>");

         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Metodo penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(10).toString()+"<BR><SELECT NAME=metpen>");
         for (int i=0; i<v_opciones2.size(); i+=2)
            {
            if (v_descripcion.elementAt(10).toString().equals(v_opciones2.elementAt(i+1).toString()))
              out.println("<OPTION SELECTED VALUE='"+v_opciones2.elementAt(i).toString()+"'>"+v_opciones2.elementAt(i+1).toString());
            else
              out.println("<OPTION VALUE='"+v_opciones2.elementAt(i).toString()+"'>"+v_opciones2.elementAt(i+1).toString());
            }
         if (v_descripcion.elementAt(10).toString().equals("-"))
           out.println("<OPTION SELECTED VALUE='null'>");
         else
           out.println("<OPTION VALUE='null'>");
         out.println("</SELECT></font></TD></TR>");

         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Metodo cuenta</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(11).toString()+"<BR><SELECT NAME=metcue>");
         for (int i=0; i<v_opciones3.size(); i+=2)
            {
            if (v_descripcion.elementAt(11).toString().equals(v_opciones3.elementAt(i+1).toString()))
              out.println("<OPTION SELECTED VALUE='"+v_opciones3.elementAt(i).toString()+"'>"+v_opciones3.elementAt(i+1).toString());
            else
              out.println("<OPTION VALUE='"+v_opciones3.elementAt(i).toString()+"'>"+v_opciones3.elementAt(i+1).toString());
            }
         if (v_descripcion.elementAt(11).toString().equals("-"))
           out.println("<OPTION SELECTED VALUE='null'>");
         else
           out.println("<OPTION VALUE='null'>");
         out.println("</SELECT></font></TD></TR>");

         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Naturaleza</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(12).toString()+"<BR><SELECT NAME=naturaleza>");
         for (int i=0; i<v_opciones4.size(); i+=2)
            {
            if (v_descripcion.elementAt(12).toString().equals(v_opciones4.elementAt(i+1).toString()))
              out.println("<OPTION SELECTED VALUE='"+v_opciones4.elementAt(i).toString()+"'>"+v_opciones4.elementAt(i+1).toString());
            else
              out.println("<OPTION VALUE='"+v_opciones4.elementAt(i).toString()+"'>"+v_opciones4.elementAt(i+1).toString());
            }
         if (v_descripcion.elementAt(12).toString().equals("-"))
           out.println("<OPTION SELECTED VALUE='null'>");
         else
           out.println("<OPTION VALUE='null'>");
         out.println("</SELECT></font></TD></TR>");

         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Respetar naturaleza</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+TBFL_ModificaNS(v_descripcion.elementAt(13).toString())+"<BR><SELECT NAME=resnat>");
         if (v_opciones4.size()==2)
           if (v_descripcion.elementAt(13).toString().equals("-"))
             out.println("<OPTION SELECTED VALUE='null'><OPTION VALUE=S>SI</SELECT></font></TD></TR>");
           else
             out.println("<OPTION SELECTED VALUE=S>SI<OPTION VALUE='null'></SELECT></font></TD></TR>");
         else
           if (v_descripcion.elementAt(13).toString().equals("S"))
             out.println("<OPTION VALUE=N>NO<OPTION SELECTED VALUE=S>SI<OPTION VALUE='null'></SELECT></font></TD></TR>");
           else if (v_descripcion.elementAt(13).toString().equals("N"))
             out.println("<OPTION SELECTED VALUE=N>NO<OPTION VALUE=S>SI<OPTION VALUE='null'></SELECT></font></TD></TR>");
           else
             out.println("<OPTION VALUE=N>NO<OPTION VALUE=S>SI<OPTION SELECTED VALUE='null'></SELECT></font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Digito verificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(14).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha cargue</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(15).toString()+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Transaccion</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(16).toString()+"</font></TD></TR>");
         out.println("</TABLE>");
         out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
         }
      else
         {
         out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de contratos","Administración contratos", "", "<BLOCKQUOTE>"+v_descripcion.elementAt(0).toString()+"</BLOCKQUOTE>", false));
         out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
         }
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");         
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      private void TBPL_PublicarError()
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de contratos","Administración contratos", "", "<BLOCKQUOTE>Comilla sencilla no es un caractér valido</BLOCKQUOTE>", false));
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();      
      }



  private String TBFL_ModificaNS(String valor)
  {
  String s = new String("-");
  if (valor.equals("N"))
    s = "NO";
  else if (valor.equals("S"))
    s = "SI";
  return s;
  }

  private Vector TBFL_ConsultaMetodoOrden(String valor)
  {
  Vector retorno = new Vector();
  if (valor.equals("SMO002"))
    {
    retorno.addElement("SMO002");
    retorno.addElement("LIFO");
    }
  else if (valor.equals("SMO001"))
    {
    retorno.addElement("SMO001");
    retorno.addElement("FIFO");
    }
  else if (valor.equals("SMO003"))
    {
    retorno.addElement("SMO003");
    retorno.addElement("APORTES SELECCIONADOS");
    }
  else if (valor.equals("SMO002SMO001"))
    {
    retorno.addElement("SMO002");
    retorno.addElement("LIFO");
    retorno.addElement("SMO001");
    retorno.addElement("FIFO");
    }
  else if (valor.equals("SMO002SMO001SMO003"))
    {
    retorno.addElement("SMO002");
    retorno.addElement("LIFO");
    retorno.addElement("SMO001");
    retorno.addElement("FIFO");
    retorno.addElement("SMO003");
    retorno.addElement("APORTES SELECCIONADOS");
    }
  else if (valor.equals("SMO002SMO003"))
    {
    retorno.addElement("SMO002");
    retorno.addElement("LIFO");
    retorno.addElement("SMO003");
    retorno.addElement("APORTES SELECCIONADOS");
    }
  else if (valor.equals("SMO001SMO003"))
    {
    retorno.addElement("SMO001");
    retorno.addElement("FIFO");
    retorno.addElement("SMO003");
    retorno.addElement("APORTES SELECCIONADOS");
    }
  else
    retorno.addElement("-");
  return retorno;
  }

  private Vector TBFL_ConsultaMetodoBeneficio(String valor)
  {
  Vector retorno = new Vector();
  if (valor.equals("S"))
    {
    retorno.addElement("SMB001");
    retorno.addElement("APORTES CON BENEFICIO");
    retorno.addElement("SMB002");
    retorno.addElement("APORTES SIN BENEFICIO");
    retorno.addElement("SMB003");
    retorno.addElement("NO APLICA");
    }
  else if (valor.equals("N"))
    {
    retorno.addElement("SMB003");
    retorno.addElement("NO APLICA");
    }
  else
    retorno.addElement("-");
  return retorno;
  }

  private Vector TBFL_ConsultaMetodoPenalizacion(String valor)
  {
  Vector retorno = new Vector();
  if (valor.equals("S"))
    {
    retorno.addElement("SMP001");
    retorno.addElement("APORTE PENALIZADO");
    retorno.addElement("SMP002");
    retorno.addElement("APORTE NO PENALIZADO");
    retorno.addElement("SMP003");
    retorno.addElement("NO APLICA");
    }
  else if (valor.equals("N"))
    {
    retorno.addElement("SMP003");
    retorno.addElement("NO APLICA");
    }
  else
    retorno.addElement("-");
  return retorno;
  }

  private Vector TBFL_ConsultaMetodoCuenta(String valor)
  {
  Vector retorno = new Vector();
  if (valor.equals("S"))
    {
    retorno.addElement("SMC001");
    retorno.addElement("APORTE CON CUENTA CONTINGENTE");
    retorno.addElement("SMC002");
    retorno.addElement("APORTE SIN CUENTA CONTINGENTE");
    retorno.addElement("SMC003");
    retorno.addElement("NO APLICA");
    }
  else if (valor.equals("N"))
    {
    retorno.addElement("SMC003");
    retorno.addElement("NO APLICA");
    }
  else
    retorno.addElement("-");
  return retorno;
  }

  private Vector TBFL_ConsultaNaturalezaRetiro(String valor)
  {
  Vector retorno = new Vector();
  if (valor.equals("SNR002"))
    {
    retorno.addElement("SNR002");
    retorno.addElement("CAPITAL");
    }
  else if (valor.equals("SNR001"))
    {
    retorno.addElement("SNR001");
    retorno.addElement("RENDIMIENTOS");
    }
  else if (valor.equals("SNR003"))
    {
    retorno.addElement("SNR003");
    retorno.addElement("PROPORCIONAL");
    }
  else if (valor.equals("SNR002SNR001"))
    {
    retorno.addElement("SNR002");
    retorno.addElement("CAPITAL");
    retorno.addElement("SNR001");
    retorno.addElement("RENDIMIENTOS");
    }
  else if (valor.equals("SNR002SNR003"))
    {
    retorno.addElement("SNR002");
    retorno.addElement("CAPITAL");
    retorno.addElement("SNR003");
    retorno.addElement("PROPORCIONAL");
    }
  else if (valor.equals("SNR001SNR003"))
    {
    retorno.addElement("SNR001");
    retorno.addElement("RENDIMIENTOS");
    retorno.addElement("SNR003");
    retorno.addElement("PROPORCIONAL");
    }
  else if (valor.equals("SNR002SNR001SNR003"))
    {
    retorno.addElement("SNR002");
    retorno.addElement("CAPITAL");
    retorno.addElement("SNR001");
    retorno.addElement("RENDIMIENTOS");
    retorno.addElement("SNR003");
    retorno.addElement("PROPORCIONAL");
    }
  else
    retorno.addElement("-");
  return retorno;
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_PARAMETRIZACION_CONTRATOS.TBCS_CM_Contratos Information";
  }
}

