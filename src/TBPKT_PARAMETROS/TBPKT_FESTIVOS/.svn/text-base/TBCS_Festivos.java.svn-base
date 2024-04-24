package TBPKT_PARAMETROS.TBPKT_FESTIVOS;//Paquete donde se guardan todas las clases referentes a festivos

//paquetes que utilizaremos en esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


//Nombre de la clase principal de festivos
public class TBCS_Festivos extends HttpServlet implements SingleThreadModel{
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
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion = new Vector();
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


    String ano = "";
    String v_opcion = "";

    //Toma de los parametros
    try{
       ano = request.getParameter("ano");
       v_opcion = request.getParameter("v_opcion");
       }
    catch (Exception e) { e.printStackTrace(); }

//    ano = ano.substring(2,4);


      if (v_opcion.equals("Consulta"))
        {
        //Consulta de los años
        v_declaracion = "SELECT to_char(fes_fecha,'yyyy-mm-dd'), fes_descripcion FROM tbfestivos WHERE to_char(fes_fecha,'yyyy') =  '"+ano+"' ORDER BY fes_fecha";
        v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
        TBPL_Publicar(v_resultadodeclaracion, ano);
        }
      else if (v_opcion.equals("adiciona"))
        TBPL_PublicarAdiciona();
      
      v_Consulta.TBPL_shutDown();//Desconexión a la base de datos
  }


      private void TBPL_Publicar(Vector v_descripcion, String ano)//Publica la pagina que da la opción de modificar o eliminar
      {
      v_descripcion.trimToSize();

      for (int i = 0; i<v_descripcion.size(); i++)
         if (v_descripcion.elementAt(i).toString().equals("null")||v_descripcion.elementAt(i).toString().equals("No hay elementos"))
            v_descripcion.setElementAt(" ", i);

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de festivos","Administración de festivos","TBPKT_PARAMETROS.TBPKT_FESTIVOS.TBCS_AEMFestivos","",true));
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Consulta de festivos para el año&nbsp;"+ano+"</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=3 rules=all width=\"100%\">");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TH width=\"5%\"><font face=Verdana size='1'><font color='#000000'>&nbsp;</TH><TH width=\"22%\"><font face=Verdana size='1'><font color='#000000'>Fecha (YYYY-MM-DD)</TH><TH width=\"22%\"><font face=Verdana size='1'><font color='#000000'>Descripción</TH></TR>");
      int i = -1;
      while (i+2<v_descripcion.size())
        {
        if (i<1)
          out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><font face=Verdana size='1'><font color='#000000'><INPUT TYPE=radio NAME=fecha VALUE='"+v_descripcion.elementAt(++i).toString()+"' CHECKED></TD><TD width=\"22%\"><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(i).toString()+"</TD><TD width=\"22%\"><font face=Verdana size='1'><font color='#000000'><INPUT TYPE=hidden NAME=v_descripcion VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_descripcion.elementAt(i).toString()+"</TD></TR>");
        else
          out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><font face=Verdana size='1'><font color='#000000'><INPUT TYPE=radio NAME=fecha VALUE='"+v_descripcion.elementAt(++i).toString()+"'></TD><TD width=\"22%\"><font face=Verdana size='1'><font color='#000000'>"+v_descripcion.elementAt(i).toString()+"</TD><TD width=\"22%\"><font face=Verdana size='1'><font color='#000000'><INPUT TYPE=hidden NAME=v_descripcion VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_descripcion.elementAt(i).toString()+"</TD></TR>");
        }
      out.println("</TABLE>");
      out.println("<BLOCKQUOTE>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='modifica' CHECKED>Modificar festivo<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='elimina'>Eliminar festivo<BR>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("</font>");
      out.println("</BLOCKQUOTE>");
      out.println("<BR>&nbsp;&nbsp;</BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE='Aceptar'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK='history.go(-1)'></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos

      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);

      out.close();
      }


      private void TBPL_PublicarAdiciona()//Publica la pagina de adicionar festivos
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de festivos", "Administración de festivos", "TBPKT_PARAMETROS.TBPKT_FESTIVOS.TBCS_AdicionaFestivos", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adición de festivos</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Fecha:</B></font></TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT NAME=obligatoriofecha  TYPE=text MAXLENGTH=10 SIZE=10 >&nbsp;(YYYY-MM-DD)</font></TD></TR>");//onClick=\"fechavalida(this)\"
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><B><SUP>*</SUP>Descripción:</B></font></TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT NAME=obligatoriodescripcion TYPE=text MAXLENGTH=50 SIZE=30></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK='history.go(-1)'></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
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
    return "TBPKT_FESTIVOS.TBCS_Festivos Information";
  }
}

