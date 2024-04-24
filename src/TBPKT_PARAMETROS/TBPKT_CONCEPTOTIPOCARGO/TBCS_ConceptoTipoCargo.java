package TBPKT_PARAMETROS.TBPKT_CONCEPTOTIPOCARGO;//Paquete donde se guardan todas las vlases de concepto tipo cargo

//importa todos los paquetes que se utilizarán en esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Nomre de la clase
public class TBCS_ConceptoTipoCargo extends HttpServlet{
private PrintWriter out;//Clase que permite imprimir el código HTML
private TBCL_Consulta v_Consulta;//Clase que permite ejecutar las declaraciones
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
    Vector v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2, v_resultadodeclaracion3;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


//------------------------------------------

      v_declaracion = "SELECT pro_codigo  FROM tbproductos ORDER BY pro_codigo";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, "pro_codigo");


      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_Publicar(v_resultadodeclaracion);//TBPL_Publicar el resultado de la Consulta
  }

      private void TBPL_Publicar(Vector v_opciones)//TBPL_Publicar pagina en v_codigo HTML
      {
      v_opciones.trimToSize();
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cargos por concepto de transacciones","Administración de cargos por concepto de transacciones","TBPKT_PARAMETROS.TBPKT_CONCEPTOTIPOCARGO.TBCS_TransaccionesConceptoTipoCargo", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<BLOCKQUOTE>");
      out.println("<B>Código del producto:</B>&nbsp;&nbsp;<SELECT NAME=v_codpro>");
      for (int i=0; i<v_opciones.size(); i++)
         {
         out.println("<OPTION>"+v_opciones.elementAt(i).toString());
         }
      out.println("</SELECT>");
      out.println("</BLOCKQUOTE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
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
    return "TBPKT_CONCEPTOTIPOCARGO.TBCS_ConceptoTipoCargo Information";
  }
}

