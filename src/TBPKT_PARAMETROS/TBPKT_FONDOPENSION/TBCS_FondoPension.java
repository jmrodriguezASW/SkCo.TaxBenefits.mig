package TBPKT_PARAMETROS.TBPKT_FONDOPENSION;//Paquete que contiene todas las clases de administración de fondos pensiones

//importa todos los paquetes que utilizaremos en esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Nombre de la clase principal de administradoras de fondo de pensiones
public class TBCS_FondoPension extends HttpServlet{
private PrintWriter out;//Clase que me permite mostrar el v_codigo HTML
private TBCL_Consulta v_Consulta;//Clase que se encarga de ejecutar las sentenvias SQL
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
    String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion = new Vector();
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


      //Consulta de las administradoras de fondos
      //------------------------------------------
      v_declaracion = "SELECT afp_codigo, afp_descripcion FROM tbadm_fondos_pensiones ORDER BY afp_descripcion";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      //---------------------------------------------
      PublicarConsultar(v_resultadodeclaracion);//TBPL_Publicar código HTML
  }


      private void PublicarConsultar(Vector v_opciones)//Publicación de la pagina
      {
      v_opciones.trimToSize();
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de Administradoras de Fondos de Pensiones","Administración de Administradoras de Fondos de Pensiones", "TBPKT_PARAMETROS.TBPKT_FONDOPENSION.TBCS_ACEMAdmFondoPension", "", true));
      //Aqui van los campo que se quieren mostrar
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
      out.println("<BLOCKQUOTE>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='Consulta' CHECKED>Consultar Administradoras de Fondos de Pensiones<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='modifica'>Modificar Administradoras de Fondos de Pensiones<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='elimina'>Eliminar Administradoras de Fondos de Pensiones<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adiciona'>Adicionar Administradoras de Fondos de Pensiones");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<B>Descripción de las administradoras de fondos y pensiones:</B>&nbsp;&nbsp;<SELECT NAME=v_descripcion>");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("</BLOCKQUOTE>");
      out.println("</font>");
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
    return "TBPKT_FONDOPENSION.TBCL_FondoPension Information";
  }
}

