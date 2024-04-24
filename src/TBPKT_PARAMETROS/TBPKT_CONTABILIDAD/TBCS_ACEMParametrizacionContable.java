package TBPKT_PARAMETROS.TBPKT_CONTABILIDAD;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTMLII;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_ACEMParametrizacionContable extends HttpServlet{
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
       // response.setHeader("Pragma", "No-Cache");
       // response.setDateHeader("Expires", 0);
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
    Vector v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2, v_resultadodeclaracion3, v_resultadodeclaracion4, v_resultadodeclaracion5;

    //Toma la opción de modificar, eliminar, adicionar o Consultar
    String v_opcion = "Consulta";
    //Toma los diferentes codigos de producto
    String v_codpro = "";


    //Toma los parametros
    try{
       v_opcion = request.getParameter("v_opcion");
       v_codpro = request.getParameter("v_codpro");
       }
    catch (Exception e) { e.printStackTrace(); }





    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


   if (v_opcion.equals("Consulta"))//Si selecciona Consulta realiza las siguientes Consultas
     {

      v_declaracion = "SELECT cob_linea_movimiento, \n"+
                    "       cob_pro_codigo, \n"+
                    "       cob_tipo_movimiento, \n"+
                    "       cob_compania, \n"+
                    "       cob_cuenta_uno, \n"+
                    "       cob_cuenta_dos, \n"+
                    "       cob_subcuenta_uno, \n"+
                    "       cob_subcuenta_dos, \n"+
                    "       cob_unidad_negocio, \n"+
                    "       cob_signo, \n"+
                    "       cob_descripcion, \n"+
                    "       cob_tipo_identificacion, \n"+
                    "       cob_numero_identificacion, \n"+
                    "       ref_descripcion \n"+
                    "FROM   tbcontabilidades, tbreferencias WHERE "+
                    "       cob_pro_codigo = '"+v_codpro+"' "+
                    "AND    cob_ref_parametro_contable = ref_codigo (+)"+
                    "ORDER BY COB_LINEA_MOVIMIENTO";

      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      TBPL_PublicarConsulta(v_resultadodeclaracion);//Publica la página de Consulta
     }
   else if (v_opcion.equals("modifica"))//Si selecciona modifica realiza las Consultas para mostrar la pagina de modificación
     {
      v_declaracion = "SELECT cob_linea_movimiento, \n"+
                    "cob_pro_codigo, \n"+
                    "cob_tipo_movimiento, \n"+
                    "cob_compania, \n"+
                    "cob_cuenta_uno, \n"+
                    "cob_cuenta_dos, \n"+
                    "cob_subcuenta_uno, \n"+
                    "cob_subcuenta_dos, \n"+
                    "cob_unidad_negocio, \n"+
                    "cob_signo, \n"+
                    "cob_descripcion, \n"+
                    "cob_tipo_identificacion, \n"+
                    "cob_numero_identificacion, \n"+
                    "       ref_descripcion \n"+
                    "FROM   tbcontabilidades, tbreferencias WHERE "+
                    "       cob_pro_codigo = '"+v_codpro+"' "+
                    "AND    cob_ref_parametro_contable = ref_codigo (+)"+
                    "ORDER BY COB_LINEA_MOVIMIENTO";

      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      

      TBPL_PublicarModifica(v_resultadodeclaracion);//Publica la página de modificar
     }
   else if (v_opcion.equals("adiciona"))//Si selecciona adicionar realiza las Consultas para adicionar
     {
     v_declaracion =  "SELECT ref_valor, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'UTI%' AND ref_codigo != 'UTI000' ORDER BY ref_descripcion";
     v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);

     v_declaracion =  "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'SCO%' AND ref_codigo != 'SCO000' ORDER BY ref_descripcion";
     v_resultadodeclaracion2 = v_Consulta.TBFL_Consulta(v_declaracion);

     
     //Publica la pagina de adicionar
     TBPL_PublicarAdiciona(v_codpro, v_resultadodeclaracion1, v_resultadodeclaracion2);
     }
   else if (v_opcion.equals("elimina"))//Si la opción es elimonar realiza las Consultas para poder mostrar la pagina de eliminar
     {

      v_declaracion = "SELECT cob_linea_movimiento, \n"+
                    "cob_pro_codigo, \n"+
                    "cob_tipo_movimiento, \n"+
                    "cob_compania, \n"+
                    "cob_cuenta_uno, \n"+
                    "cob_cuenta_dos, \n"+
                    "cob_subcuenta_uno, \n"+
                    "cob_subcuenta_dos, \n"+
                    "cob_unidad_negocio, \n"+
                    "cob_signo, \n"+
                    "cob_descripcion, \n"+
                    "cob_tipo_identificacion, \n"+
                    "cob_numero_identificacion, \n"+
                    "       ref_descripcion \n"+
                    "FROM   tbcontabilidades, tbreferencias WHERE "+
                    "       cob_pro_codigo = '"+v_codpro+"' "+
                    "AND    cob_ref_parametro_contable = ref_codigo (+)"+
                    "ORDER BY COB_LINEA_MOVIMIENTO";

      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      
      TBPL_PublicarElimina(v_resultadodeclaracion);//Publica pagina de eliminar
     }
  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }


      private void TBPL_PublicarConsulta(Vector v_descripcion)//Publica en v_codigo HTML la pagina de Consulta
      {
      v_descripcion.trimToSize();

    if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
      {
      out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Administración de parametrización contable de conceptos de transacciones por producto","Administración de parametrización contable de conceptos de transacciones por producto","TBPKT_PARAMETROS.TBPKT_CONTABILIDAD.TBCS_ConfirmaEliminaContabilidad", "", true));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Consulta parametrización contable</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 rules=all width=\"100%\">");
      out.println("<TR class=\"td11\" borderColor=silver><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Parametrización contable</B></font></TD>");
      for (int i=0; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Linea de movimiento&nbsp;"+v_descripcion.elementAt(i).toString()+"</B></font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto</B></font></TD>");
      for (int i=1; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de movimiento</B></font></TD>");
      for (int i=2; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Compañía</B></font></TD>");
      for (int i=3; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta uno</B></font></TD>");
      for (int i=4; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta dos</B></font></TD>");
      for (int i=5; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Subcuenta uno</B></font></TD>");
      for (int i=6; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Subcuenta dos</B></font></TD>");
      for (int i=7; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Unidad negocio</B></font></TD>");
      for (int i=8; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Signo</B></font></TD>");
      for (int i=9; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción</B></font></TD>");
      for (int i=10; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de identificación</B></font></TD>");
      for (int i=11; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+TBCL_Consultatipoidentificacion(v_descripcion.elementAt(i).toString())+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de identificación</B></font></TD>");
      for (int i=12; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Parametrización contable</B></font></TD>");
      for (int i=13; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");

      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      }
    else
      {
      out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Administración de parametrización contable de conceptos de transacciones por producto","Administración de parametrización contable de conceptos de transacciones por producto","", "<BLOCKQUOTE>"+v_descripcion.elementAt(0).toString()+"</BLOCKQUOTE>", false));
      out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      }
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
      out.close();
      }

      private void TBPL_PublicarModifica(Vector v_descripcion)
      {
      v_descripcion.trimToSize();

    if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
      {
      out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Administración de parametrización contable de conceptos de transacciones por producto","Administración de parametrización contable de conceptos de transacciones por producto","TBPKT_PARAMETROS.TBPKT_CONTABILIDAD.TBCS_ConfirmaModificaContabilidad", "", true));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificar parametrización contable</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 rules=all width=\"100%\">");
      out.println("<TR class=\"td11\" borderColor=silver><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Parametrización contable</B></font></TD>");
      for (int i=0; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Linea de movimiento&nbsp;"+v_descripcion.elementAt(i).toString()+"</B></font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto</B></font></TD>");
      for (int i=1; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de movimiento</B></font></TD>");
      for (int i=2; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Compañía</B></font></TD>");
      for (int i=3; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta uno</B></font></TD>");
      for (int i=4; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta dos</B></font></TD>");
      for (int i=5; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Subcuenta uno</B></font></TD>");
      for (int i=6; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Subcuenta dos</B></font></TD>");
      for (int i=7; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Unidad negocio</B></font></TD>");
      for (int i=8; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Signo</B></font></TD>");
      for (int i=9; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción</B></font></TD>");
      for (int i=10; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de identificación</B></font></TD>");
      for (int i=11; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+TBCL_Consultatipoidentificacion(v_descripcion.elementAt(i).toString())+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de identificación</B></font></TD>");
      for (int i=12; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Parametrización contable</B></font></TD>");
      for (int i=13; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver align=center><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</font></TD>");
      for (int i=0; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1><A HREF='TBPKT_PARAMETROS.TBPKT_CONTABILIDAD.TBCS_ConfirmaModificaContabilidad?lineamov="+v_descripcion.elementAt(i).toString()+"&v_codpro="+v_descripcion.elementAt(i+1).toString()+"'>Modificar</A></font></TD>");
      out.println("</TR>");
      out.println("</TABLE>");
/*      out.println("<TABLE BORDER COLS=16>");
      out.println("<TR><TH bgColor=whitesmoke><font face=Verdana size='1'><font color='#000000'>&nbsp;</TH><TH><font face=Verdana size='1'><font color='#000000'>Línea de movimiento</TH><TH><font face=Verdana size='1'><font color='#000000'>Código producto</TH><TH><font face=Verdana size='1'><font color='#000000'>Transacción</TH><TH><font face=Verdana size='1'><font color='#000000'>Tipo de transacción</TH><TH><font face=Verdana size='1'><font color='#000000'>Clase de transacción</TH><TH><font face=Verdana size='1'><font color='#000000'>Tipo de movimiento</TH><TH><font face=Verdana size='1'><font color='#000000'>Compañía</TH><TH>"+
                  "<font face=Verdana size='1'><font color='#000000'>Cuenta 1</TH><TH><font face=Verdana size='1'><font color='#000000'>Cuenta 2</TH><TH><font face=Verdana size='1'><font color='#000000'>Subcuenta 1</TH><TH><font face=Verdana size='1'><font color='#000000'>Subcuenta 2</TH><TH><font face=Verdana size='1'><font color='#000000'>Unidad negocio</TH><TH><font face=Verdana size='1'><font color='#000000'>Signo</TH><TH><font face=Verdana size='1'><font color='#000000'>Descripción</TH><TH><font face=Verdana size='1'><font color='#000000'>Tipo identificación</TH><TH><font face=Verdana size='1'><font color='#000000'>No. de identificación</TH></TR>");
      int i= -1;
      while (i+16<v_descripcion.size())
           if (i<15)
             out.println("<TR><TD><font face=Verdana size='1'><font color='#000000'><INPUT TYPE=radio NAME=lineamov CHECKED VALUE='"+v_descripcion.elementAt(++i).toString()+"'></TD><TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(i).toString()+"</TD><TD><font face=Verdana size='1'><font color='#000000'><INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_descripcion.elementAt(i).toString()+"</TD><TD><font face=Verdana size='1'><font color='#000000'><INPUT TYPE=hidden NAME=trans VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+transac+"</TD>"+
                         "<TD><font face=Verdana size='1'><font color='#000000'><INPUT TYPE=hidden NAME=tipotrans VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+tipotransac+"</TD><TD><font face=Verdana size='1'><font color='#000000'><INPUT TYPE=hidden NAME=clasetrans VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+clasetransac+"</TD>"+
                         "<TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD>"+
                         "<TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD>"+
                         "<TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face=Verdana size='1'><font color='#000000'>"+TBCL_Consultatipoidentificacion(v_descripcion.elementAt(++i).toString())+"</TD><TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD></TR>");
           else
             out.println("<TR><TD><font face=Verdana size='1'><font color='#000000'><INPUT TYPE=radio NAME=lineamov VALUE='"+v_descripcion.elementAt(++i).toString()+"'></TD><TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(i).toString()+"</TD><TD><font face=Verdana size='1'><font color='#000000'><INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_descripcion.elementAt(i).toString()+"</TD><TD><font face=Verdana size='1'><font color='#000000'><INPUT TYPE=hidden NAME=trans VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+transac+"</TD>"+
                         "<TD><font face=Verdana size='1'><font color='#000000'><INPUT TYPE=hidden NAME=tipotrans VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+tipotransac+"</TD><TD><font face=Verdana size='1'><font color='#000000'><INPUT TYPE=hidden NAME=clasetrans VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+clasetransac+"</TD>"+
                         "<TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD>"+
                         "<TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD>"+
                         "<TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face=Verdana size='1'><font color='#000000'>"+TBCL_Consultatipoidentificacion(v_descripcion.elementAt(++i).toString())+"</TD><TD><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD></TR>");
      out.println("</TABLE>");*/
      out.println("<BR>&nbsp;&nbsp;<BR>");
//      out.println("<CENTER><INPUT TYPE=submit VALUE='Confirma Modifica'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      }
    else
      {
      out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Administración de parametrización contable de conceptos de transacciones por producto","Administración de parametrización contable de conceptos de transacciones por producto","", "<BLOCKQUOTE>"+v_descripcion.elementAt(0).toString()+"</BLOCKQUOTE>", false));
      out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      }


      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
      out.close();
      }


      //TBPL_Publicar en código HTML la pagina de adicionar
      private void TBPL_PublicarAdiciona(String v_codpro, Vector v_opciones, Vector v_opciones1)
      {
      v_opciones.trimToSize();
      v_opciones1.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de parametrización contable de conceptos de transacciones por producto", "Administración de parametrización contable de conceptos de transacciones por producto","TBPKT_PARAMETROS.TBPKT_CONTABILIDAD.TBCS_AdicionaContabilidad", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adicionar parametrización contable de transacciones por producto</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del producto:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=HIDDEN NAME=v_codpro VALUE='"+v_codpro+"'>"+v_codpro+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Línea de movimiento:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=obligatoriolinmov MAXLENGTH=2 SIZE=2 onblur='if (esNumero(obligatoriolinmov)==1) return false;'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Tipo de movimiento:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=tipmov><OPTION>D<OPTION>H</SELECT></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Compañía:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=obligatoriocompania MAXLENGTH=5 SIZE=5></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Cuenta 1:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=obligatoriocuentauno MAXLENGTH=2 SIZE=2></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Cuenta 2:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=obligatoriocuentados MAXLENGTH=2 SIZE=2></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Subcuenta 1:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=obligatoriosubcuentauno MAXLENGTH=2 SIZE=2></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Subcuenta 2:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=subcuentados MAXLENGTH=2 SIZE=2></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Unidad de negocio:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=obligatoriounineg MAXLENGTH=5 SIZE=5></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Signo:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=signo><OPTION>+<OPTION>-</SELECT></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Descripción:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=obligatoriodescripcion MAXLENGTH=30 SIZE=20></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de identificación:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=tipoiden>");
      out.println("               <option VALUE ='   '>                         ");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No. de identificación:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=text NAME=numiden MAXLENGTH=13 SIZE=10></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Parametrización contable:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=parcont>");
      for (int i=0; i<v_opciones1.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones1.elementAt(i).toString()+"'>"+v_opciones1.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      //TBPL_Publicar en código HTML la pagina de eliminar
      private void TBPL_PublicarElimina(Vector v_descripcion)
      {
      v_descripcion.trimToSize();

    if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
      {
      out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Administración de parametrización contable de conceptos de transacciones por producto","Administración de parametrización contable de conceptos de transacciones por producto","TBPKT_PARAMETROS.TBPKT_CONTABILIDAD.TBCS_ConfirmaEliminaContabilidad", "", true));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Eliminar parametrización contable</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 rules=all width=\"100%\">");
      out.println("<TR class=\"td11\" borderColor=silver><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Parametrización contable</B></font></TD>");
      for (int i=0; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Linea de movimiento&nbsp;"+v_descripcion.elementAt(i).toString()+"</B></font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto</B></font></TD>");
      for (int i=1; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de movimiento</B></font></TD>");
      for (int i=2; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Compañía</B></font></TD>");
      for (int i=3; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta uno</B></font></TD>");
      for (int i=4; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cuenta dos</B></font></TD>");
      for (int i=5; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Subcuenta uno</B></font></TD>");
      for (int i=6; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Subcuenta dos</B></font></TD>");
      for (int i=7; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Unidad negocio</B></font></TD>");
      for (int i=8; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Signo</B></font></TD>");
      for (int i=9; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción</B></font></TD>");
      for (int i=10; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de identificación</B></font></TD>");
      for (int i=11; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+TBCL_Consultatipoidentificacion(v_descripcion.elementAt(i).toString())+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de identificación</B></font></TD>");
      for (int i=12; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Parametrización contable</B></font></TD>");
      for (int i=13; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
      out.println("</TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver align=center><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;</font></TD>");
      for (int i=0; i<v_descripcion.size(); i+=14)
         out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1><A HREF='TBPKT_PARAMETROS.TBPKT_CONTABILIDAD.TBCS_ConfirmaEliminaContabilidad?lineamov="+v_descripcion.elementAt(i).toString()+"&v_codpro="+v_descripcion.elementAt(i+1).toString()+"'>Eliminar</A></font></TD>");
      out.println("</TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      }
    else
      {
      out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Administración de parametrización contable de conceptos de transacciones por producto","Administración de parametrización contable de conceptos de transacciones por producto","", "<BLOCKQUOTE>"+v_descripcion.elementAt(0).toString()+"</BLOCKQUOTE>", false));
      out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      }
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
      out.close();

      }



  //Consulta de la tabla de referencias los codigos del tipo de identificación(Muestra la v_descripcion)
  private String TBCL_Consultatipoidentificacion(String v_valor)
  {
 // v_Consulta = new TBCL_Consulta();
  String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion;
   // v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
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
    return "TBPKT_CONTABILIDAD.TBCS_ACEMParametrizacionContable Information";
  }
}

