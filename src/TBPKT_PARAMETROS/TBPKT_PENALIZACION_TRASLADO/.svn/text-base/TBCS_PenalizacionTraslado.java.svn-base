package TBPKT_PARAMETROS.TBPKT_PENALIZACION_TRASLADO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_PenalizacionTraslado extends HttpServlet implements SingleThreadModel
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
  String Consulta1;
  //Vector donde se guardara el resultado de la Consulta
  Vector v_resultadoConsulta = new Vector();
  Vector v_resultadoConsulta1 = new Vector();
  v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

  Consulta = "SELECT prg_codigo FROM tbprogramas_parametrizacion ORDER BY prg_codigo";
  v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta, "prg_codigo");
  Consulta1 = "SELECT pro_codigo FROM tbproductos ORDER BY pro_codigo";
  v_resultadoConsulta1 = v_Consulta.TBFL_Consulta(Consulta1, "pro_codigo");
 
  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  TBPL_Publicar(v_resultadoConsulta, v_resultadoConsulta1);
  
  }


      private void TBPL_Publicar(Vector v_opciones, Vector v_opciones1)
      {
       v_opciones.trimToSize();
       v_opciones1.trimToSize();

       out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de penalización de traslados","Administración de penalización de traslados", "TBPKT_PARAMETROS.TBPKT_PENALIZACION_TRASLADO.TBCS_ACEMPenalizacionTraslado", "", true));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<BLOCKQUOTE>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='consulta' CHECKED>Consultar penalización<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='modifica'>Modificar penalización<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='elimina'>Eliminar penalización<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adiciona'>Adicionar penalización<BR>&nbsp;&nbsp;<BR>");
      
      out.println("<B>Código del producto origen</B>&nbsp;&nbsp;<SELECT NAME=v_codpro1>");
      for (int i=0; i<v_opciones1.size(); i++)
         {
         out.println("<OPTION>"+v_opciones1.elementAt(i).toString());
         }
      out.println("</SELECT><BR>&nbsp;&nbsp;<BR>");
      
      out.println("<B>Código del programa origen</B>&nbsp;&nbsp;<SELECT NAME=v_codprg1>");
      for (int i=0; i<v_opciones.size(); i++)
         {
         out.println("<OPTION>"+v_opciones.elementAt(i).toString());
         }
      out.println("</SELECT><BR>&nbsp;&nbsp;<BR>");
      
      out.println("<B>Código del producto destino</B>&nbsp;&nbsp;<SELECT NAME=v_codpro2>");
      for (int i=0; i<v_opciones1.size(); i++)
         {
         out.println("<OPTION>"+v_opciones1.elementAt(i).toString());
         }
      out.println("</SELECT><BR>&nbsp;&nbsp;<BR>");
      out.println("<B>Código del programa destino</B>&nbsp;&nbsp;<SELECT NAME=v_codprg2>");
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
    return "TBPKT_PENALIZACION_TRASLADO.TBCS_PenalizazionTraslado Information";
  }
}

