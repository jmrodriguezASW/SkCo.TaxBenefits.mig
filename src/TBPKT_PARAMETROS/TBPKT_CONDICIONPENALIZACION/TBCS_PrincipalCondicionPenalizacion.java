package TBPKT_PARAMETROS.TBPKT_CONDICIONPENALIZACION;//Paque que guarda las clases de condición penalización

//paquetes que utilizaremos en esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Nombre  de la página principal de condiciones de penalización
public class TBCS_PrincipalCondicionPenalizacion extends HttpServlet{
private PrintWriter out;//Clase que me permite imprimir el código HTML
private TBCL_Consulta v_Consulta;//Clase que se encarga de ejecutar las declaraciones SQL
private String v_nuevaCadena ="";

  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException {
  }




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
    String Consulta;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadoConsulta = new Vector();

    //Consulta que se realiza para luego ser mostrada
    //---------------
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    Consulta = "SELECT cop_codigo FROM tbcondiciones_penalizacion ORDER BY cop_codigo";
    v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta, "cop_codigo");

    v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    TBPL_Publicar(v_resultadoConsulta);//TBPL_Publicar la Consulta
  }



      private void TBPL_Publicar(Vector v_opciones)//Publicación de la pagina en código HTML
      {
      v_opciones.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administracion de las condiciones de penalización","Administracion de las condiciones de penalización", "TBPKT_PARAMETROS.TBPKT_CONDICIONPENALIZACION.TBCS_CondicionPenalizacion", "", true));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<BLOCKQUOTE>");
      out.println("<INPUT TYPE=radio NAME=accion VALUE='Consultar' CHECKED>Consultar condiciones de penalización<BR>");
      out.println("<INPUT TYPE=radio NAME=accion VALUE='modificar'>Modificar condiciones de penalización<BR>");
      out.println("<INPUT TYPE=radio NAME=accion VALUE='eliminar'>Eliminar condiciones de penalización<BR>");
      out.println("<INPUT TYPE=radio NAME=accion VALUE='adicionar'>Adicionar condiciones de penalización");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<B>Código de las condiciones de penalización:</B>&nbsp;&nbsp;<SELECT NAME=v_codigo>");
      for (int i=0; i<v_opciones.size(); i++)
         {
         out.println("<OPTION>"+v_opciones.elementAt(i).toString());
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
    return "TBPKT_CONDICIONPENALIZACION.TBCS_PrincipalCondicionPenalizacion Information";
  }
}

 