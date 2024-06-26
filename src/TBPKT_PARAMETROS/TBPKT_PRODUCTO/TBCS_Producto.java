package TBPKT_PARAMETROS.TBPKT_PRODUCTO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_Producto extends HttpServlet
{
 private PrintWriter out;
 private TBCL_Consulta v_Consulta;
 private String v_nuevaCadena ="";

  /**
   * Initialize global variables
   */
 public void init(ServletConfig config) throws ServletException
 {
  super.init(config);
 }

  /**
   * Process the HTTP Get request
   */
 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
  try
  {
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

  out = new PrintWriter (response.getOutputStream());
  response.setContentType("text/html");

  v_Consulta = new TBCL_Consulta();
  String Consulta;
  //Vector donde se guardara el resultado de la Consulta
  Vector v_resultadoConsulta = new Vector();
  v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

   Consulta = "SELECT pro_codigo FROM tbproductos ORDER BY pro_codigo";
   v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta, "pro_codigo");

    v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    TBPL_Publicar(v_resultadoConsulta);
  }


      private void TBPL_Publicar(Vector v_opciones)
      {
       v_opciones.trimToSize();

       out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de productos","Administración de Productos", "TBPKT_PARAMETROS.TBPKT_PRODUCTO.TBCS_ACEMProducto", "", true));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<BLOCKQUOTE>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='consulta' CHECKED>Consultar producto<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='modifica'>Modificar producto<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='elimina'>Eliminar producto<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adiciona'>Adicionar producto<BR>&nbsp;&nbsp;<BR>");
      out.println("<B>Código del producto</B>&nbsp;&nbsp;<SELECT NAME=v_codpro>");
      for (int i=0; i<v_opciones.size(); i++)
         {
         out.println("<OPTION>"+v_opciones.elementAt(i).toString());
         }
      out.println("</SELECT><BR>&nbsp;&nbsp;<BR>");
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
    return "TBPKT_PRODUCTO.TBCS_Producto Information";
  }
}

