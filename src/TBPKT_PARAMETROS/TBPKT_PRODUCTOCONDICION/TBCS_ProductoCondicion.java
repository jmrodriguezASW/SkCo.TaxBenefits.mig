package TBPKT_PARAMETROS.TBPKT_PRODUCTOCONDICION;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Servlet que carga todos los posibles productos
public class TBCS_ProductoCondicion extends HttpServlet{
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
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
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
        
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
       parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

  v_Consulta = new TBCL_Consulta();
  String Consulta;

  v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

  //String de la Consulta SQL de los diferentes productos
  Consulta = "SELECT pro_codigo FROM tbproductos ORDER BY pro_codigo";

  //Vector donde se guardara el resultado de la Consulta
  Vector v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta, "pro_codigo");

  /*Agregado po Marcela Ortiz 2008-08-26
   String de la Consulta SQL de los diferentes programas*/
  String Consulta2 = "SELECT prg_codigo FROM tbprogramas_parametrizacion ORDER BY prg_codigo";
  //Vector donde se guardara el resultado de la Consulta
  Vector v_resultadoConsulta2 = v_Consulta.TBFL_Consulta(Consulta2, "prg_codigo");
  /*Fin del segmento agregado*/
  
  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  TBPL_Publicar(v_resultadoConsulta, v_resultadoConsulta2);//Codigo html que se publicara en la pagina
  }




  //Publicacion de la pagina en el browser (v_codigo html)
  private void TBPL_Publicar(Vector v_opciones, Vector v_opciones2)
  {
  v_opciones.trimToSize();
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de las condiciones de penalización por productos","Administración de las condiciones de penalización por productos", "TBPKT_PARAMETROS.TBPKT_PRODUCTOCONDICION.TBCS_CargaProductoCondicion", "", true));
  //Aqui van los campo que se quieren mostrar
  out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
  out.println("<BLOCKQUOTE>");
  out.println("<BR>&nbsp;&nbsp;<BR>");
  out.println("<B>Código del producto:</b>&nbsp;&nbsp;<SELECT NAME=v_codigo>");
  for (int i=0; i<v_opciones.size(); i++)
     {
     out.println("<OPTION>"+v_opciones.elementAt(i).toString());
     }
  out.println("</SELECT>");
  out.println("<BR>&nbsp;&nbsp;<BR>");
  /*Agregado po Marcela Ortiz 2008-08-26
   Recoge el programa para que pueda insertar la asociación */
  out.println("<B>Programa:</B>&nbsp;&nbsp<SELECT NAME=programa>");
  for (int i=0; i<v_opciones2.size(); i++)
  {
     out.println("<OPTION>"+v_opciones2.elementAt(i).toString());
  }
  out.println("</SELECT>");
  out.println("<BR>&nbsp;&nbsp<BR>");
  /*Fin del segmento agregado*/
  out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
  out.println("</BLOCKQUOTE>");
  out.println("</font>");
  out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
  //Hasta aca van los campos
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }



}

