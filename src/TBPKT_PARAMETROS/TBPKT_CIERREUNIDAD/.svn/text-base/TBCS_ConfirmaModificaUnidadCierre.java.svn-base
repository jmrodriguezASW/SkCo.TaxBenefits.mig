package TBPKT_PARAMETROS.TBPKT_CIERREUNIDAD;//Paquete de cierres de unidad


//importación de paquetes que se utilizarán es esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_ConfirmaModificaUnidadCierre extends HttpServlet implements SingleThreadModel{
private PrintWriter out;//Clase que se encarga de imprimir el código HTML
private TBCL_Consulta v_Consulta;//Clase que se encarga de manejar las declaraciones
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
    String v_declaracion;//Cadena donde se guardarán las declaraciones
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion, v_resultadodeclaracion1;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


    //Toma el v_codigo del producto
    String v_codpro = "";
    //Toma la unidad de proceso
    String v_unipro = "";
    //Toma el tipo de usuario
    String tipusu = "";
    //Toma el código de unidad de proceso
    String v_cunipro = "";
    //Toma el código del tipo de usuario
    String ctipusu = "";

    try{
       v_codpro = request.getParameter("v_codpro");
       v_cunipro = request.getParameter("v_unipro");
       ctipusu = request.getParameter("tipusu");
       }
    catch (Exception e) { e.printStackTrace(); }

    tipusu =  ConsultaDescripcion(ctipusu);
    v_unipro =  ConsultaDescripcion(v_cunipro);

    //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
     //Se encarga de las Consultas para poder modificar cierre de unidad
     //------------------------------------------

      v_declaracion = "SELECT \n"+
                    " CIU_PRO_CODIGO, \n"+
                    " CIU_REF_UNIDAD_PROCESO, \n"+
                    " CIU_REF_TIPO_USUARIO, \n"+
                    " CIU_HORA \n"+
                    "FROM tbcierres_unidades WHERE "+
                    "ciu_pro_codigo = '"+v_codpro+"' AND "+
                    "ciu_ref_unidad_proceso = '"+v_cunipro+"' AND "+
                    "ciu_ref_tipo_usuario = '"+ctipusu+"'";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

     //-------------------------------------
     TBPL_Publicar(v_resultadodeclaracion, tipusu, v_unipro);//TBPL_Publicar pagina html
     
     v_Consulta.TBPL_shutDown();//Desconexion a la base de datos

  }


      private void TBPL_Publicar(Vector v_descripcion, String tipusu, String v_unipro)//TBPL_Publicar pagina HTML
      {
      v_descripcion.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cierre de transacción por producto", "Administración de cierre de transacción por producto", "TBPKT_PARAMETROS.TBPKT_CIERREUNIDAD.TBCS_ModificaCierreUnidad", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificar cierre de transacción por producto</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Unidad de proceso:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_unipro+"<INPUT TYPE=hidden NAME=v_unipro VALUE='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de usuario:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+tipusu+"<INPUT TYPE=hidden NAME=tipusu VALUE='"+v_descripcion.elementAt(2).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Hora de cierre:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"<INPUT TYPE=hidden NAME=dhora VALUE='"+v_descripcion.elementAt(3).toString()+"'><BR>");
      String h = v_descripcion.elementAt(3).toString().substring(0,2);
      String m = v_descripcion.elementAt(3).toString().substring(2);
      out.println("<SELECT NAME=hora><OPTION>00<OPTION>01<OPTION>02<OPTION>03<OPTION>04<OPTION>05<OPTION>06<OPTION>07<OPTION>08<OPTION>09<OPTION>10<OPTION>11<OPTION>12<OPTION>13<OPTION>14<OPTION>15<OPTION>16<OPTION>17<OPTION>18<OPTION>19<OPTION>20<OPTION>21<OPTION>22<OPTION>23<OPTION SELECTED>"+h+"</SELECT>");
      out.println(":<SELECT NAME=min><OPTION>00<OPTION>01<OPTION>02<OPTION>03<OPTION>04<OPTION>05<OPTION>06<OPTION>07<OPTION>08<OPTION>09<OPTION>10<OPTION>11<OPTION>12<OPTION>13<OPTION>14<OPTION>15<OPTION>16<OPTION>17<OPTION>18<OPTION>19<OPTION>20<OPTION>21<OPTION>22<OPTION>23<OPTION>24<OPTION>25<OPTION>26<OPTION>27<OPTION>28<OPTION>29<OPTION>30");
      out.println("<OPTION>31<OPTION>32<OPTION>33<OPTION>34<OPTION>35<OPTION>36<OPTION>37<OPTION>38<OPTION>39<OPTION>40<OPTION>41<OPTION>42<OPTION>43<OPTION>44<OPTION>45<OPTION>46<OPTION>47<OPTION>48<OPTION>49<OPTION>50<OPTION>51<OPTION>52<OPTION>53<OPTION>54<OPTION>55<OPTION>56<OPTION>57<OPTION>58<OPTION>59<OPTION SELECTED>"+m+"</SELECT>(HH:MM)Militar</font></TD></TR>");
      //<INPUT TYPE=text MAXLENGTH=5 SIZE=5 NAME=hora VALUE='"+v_descripcion.elementAt(3).toString()+"' ONBLUR=\"return IsValidTime(document.v_codigo.hora.value);\">
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");      
      out.println("<CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

      //Consulta el tipo de usuario en la tabla de referencias de la base de datos
  private String ConsultaDescripcion(String v_valor)
  {
  //v_Consulta = new TBCL_Consulta();
  String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion;
    //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
    v_declaracion =  "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_valor+"'";
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
    //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    return  v_resultadodeclaracion.elementAt(0).toString();
  }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CIERREUNIDAD.TBCS_ConfirmaModificaUnidadCierre Information";
  }
}

