package TBPKT_PARAMETROS.TBPKT_FESTIVOS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Clase que se encarga de mostrar las paginas de modificar o eliminar festivos
public class TBCS_AEMFestivos extends HttpServlet implements SingleThreadModel{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
private String v_nuevaCadena;

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
       //response.setHeader("Pragma", "No-Cache");
       //response.setDateHeader("Expires", 0);
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
    //Vector donde se guardara el resultado de la v_declaracion
    Vector v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    //Toma la opción de modificar, eliminar, adicionar o Consultar
    String v_opcion = "modifica";
    String fecha = "";

    //Toma de los parametros
    try{
       v_opcion = request.getParameter("v_opcion");
       fecha = request.getParameter("fecha");
       }
    catch (Exception e) { e.printStackTrace(); }


   if (v_opcion.equals("modifica"))//Si escoge modificar reliza las Consultas respectivas para mostrar
     {
     //Sentencias SQL
     v_declaracion = "SELECT to_char(fes_fecha,'yyyy-mm-dd'), fes_descripcion FROM tbfestivos WHERE fes_fecha = to_date('"+fecha+"', 'YYYY-MM-DD')";
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
//     v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
     //publicacion en pagina html de las Consultas
     TBPL_PublicarModifica(v_resultadodeclaracion);//Publica pagina de modificar
     }
   else if (v_opcion.equals("elimina"))//Si escoge elimina realiza la Consulta correspondiente para mostrar la pagina de eliminar
     {
     //Sentencias SQL
     v_declaracion = "SELECT to_char(fes_fecha,'yyyy-mm-dd'), fes_descripcion FROM tbfestivos WHERE fes_fecha = to_date('"+fecha+"', 'yyyy-mm-dd')";
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
//     v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
     //publicacion en pagina html de las Consultas
     TBPL_PublicarElimina(v_resultadodeclaracion);//TBPL_Publicar pagina de eliminar
     }
      v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  }



      private void TBPL_PublicarModifica(Vector v_descripcion)//TBPL_Publicar pagina de modificar
      {
      v_descripcion.trimToSize();

      if (!v_descripcion.elementAt(0).toString().startsWith("Hubo algun  error al realizar su transacción"))
        {
        for (int i = 0; i<v_descripcion.size(); i++)
           if (v_descripcion.elementAt(i).toString().equals("null")||v_descripcion.elementAt(i).toString().equals("No hay elementos"))
             v_descripcion.setElementAt(" ", i);

        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de festivos", "Administración de festivos", "TBPKT_PARAMETROS.TBPKT_FESTIVOS.TBCS_ModificaFestivos", "", true, "moduloparametro.js", "return checkrequired(this)"));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                    "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificación de festivos</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Fecha:</B></font></TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(0).toString().length()<10?v_descripcion.elementAt(0).toString():v_descripcion.elementAt(0).toString().substring(0,10))+"<INPUT NAME=fecha VALUE='"+(v_descripcion.elementAt(0).toString().length()<10?v_descripcion.elementAt(0).toString():v_descripcion.elementAt(0).toString().substring(0,10))+"' TYPE=hidden>&nbsp;(YYYY-MM-DD)</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><SUP>*</SUP><B>Descripción:</B></font></TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(1).toString()+"<INPUT NAME=v_ddescripcion VALUE='"+v_descripcion.elementAt(1).toString()+"' TYPE=hidden ><BR><INPUT NAME=obligatoriodescripcion VALUE='"+v_descripcion.elementAt(1).toString()+"' TYPE=text MAXLENGTH=50 SIZE=30></font></TD></TR>");
        out.println("</TABLE>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK='history.go(-1)'></CENTER><BR>");
        out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        }
      else
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de festivos", "Administración de festivos", "", "No existen registros para modificar", false));
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<CENTER><INPUT TYPE=button VALUE=Aceptar ONCLICK='history.go(-1)'></CENTER>");
        }


      //Hasta aca van los campos

      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



      private void TBPL_PublicarElimina(Vector v_descripcion)//Publica pagina de eliminar
      {
      v_descripcion.trimToSize();

      if (!v_descripcion.elementAt(0).toString().startsWith("Hubo algun  error al realizar su transacción"))
        {
        for (int i = 0; i<v_descripcion.size(); i++)
           if (v_descripcion.elementAt(i).toString().equals("null")||v_descripcion.elementAt(i).toString().equals("No hay elementos"))
             v_descripcion.setElementAt(" ", i);

        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de festivos", "Administración de festivos","TBPKT_PARAMETROS.TBPKT_FESTIVOS.TBCS_EliminaFestivos", "", true, "moduloparametro.js", "return checkrequired(this)"));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                    "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Eliminación de festivos</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Fecha:</B></font></TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+(v_descripcion.elementAt(0).toString().length()<10?v_descripcion.elementAt(0).toString():v_descripcion.elementAt(0).toString().substring(0,10))+"<INPUT NAME=fecha VALUE='"+(v_descripcion.elementAt(0).toString().length()<10?v_descripcion.elementAt(0).toString():v_descripcion.elementAt(0).toString().substring(0,10))+"' TYPE=hidden>&nbsp;(YYYY-MM-DD)</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B>Descripción:</B></font></TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
        out.println("</TABLE>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<CENTER><font size='1'><INPUT TYPE=submit VALUE=Eliminar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK='history.go(-1)'></CENTER>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");        
        }
      else
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de porcentajes mínimos por fondo","Administración de porcentajes mínimos por fondo", "", "No existen registros para eliminar", false));
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
        }

      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_FESTIVOS.TBCS_AEMFestivos Information";
  }
}

