package TBPKT_PARAMETROS.TBPKT_CIERREUNIDAD;//Paquete donde estan las clases

//Paquetres que utilizaremos en esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


//Nombre  de la clase
public class TBCS_ConfirmaEliminaUnidadCierre extends HttpServlet implements SingleThreadModel{
private PrintWriter out;//Clase que se encarga de imprimir el código HTML
private TBCL_Consulta v_Consulta;//Clase que se encarga de las Consultas
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
    Vector v_resultadodeclaracion, v_resultadodeclaracion1;

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



    //Toma los parametros del la pagina anterior
    try{
       v_codpro = request.getParameter("v_codpro");
       v_cunipro = request.getParameter("v_unipro");
       ctipusu = request.getParameter("tipusu");
       }
    catch (Exception e) { e.printStackTrace(); }

     //realiza las Consultas para verificar la eliminacion de un cierre unidad
     //---------------------------------
     tipusu = ConsultaDescripcion(ctipusu);
     v_unipro = ConsultaDescripcion(v_cunipro);


     v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

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
     TBPL_Publicar(v_resultadodeclaracion, tipusu, v_unipro);//TBPL_Publicar página
     
     v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }



      private void TBPL_Publicar(Vector v_descripcion, String tipusu, String v_unipro)//Publica pagina
      {
      v_descripcion.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cierre de transacción por producto", "Administración de cierre de transacción por producto", "TBPKT_PARAMETROS.TBPKT_CIERREUNIDAD.TBCS_EliminaCierreUnidad", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Eliminar cierre de transacción por producto</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codpro value='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Unidad de proceso:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_unipro+"<INPUT TYPE=hidden NAME=v_unipro value='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de usuario:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+tipusu+"<INPUT TYPE=hidden NAME=tipusu value='"+v_descripcion.elementAt(2).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Hora de cierre:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"<INPUT TYPE=hidden NAME=hora value='"+v_descripcion.elementAt(3).toString()+"'></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Eliminar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
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
   // v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    return  v_resultadodeclaracion.elementAt(0).toString();
  }
  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CIERREUNIDAD.TBCS_ConfirmaEliminaUnidadCierre Information";
  }
}

