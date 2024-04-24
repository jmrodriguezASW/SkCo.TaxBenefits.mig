package TBPKT_PARAMETROS.TBPKT_FESTIVOS;//Paquete donde estan todas las clases de administracicón de festivos

//Paquetes que importan todas las clases que utilizaremos en esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Nombre de la clase principal de festivos
public class TBCS_PrincipalFestivos extends HttpServlet{
private PrintWriter out;//Clase que nos sirve para imprimir el código HTLML
private TBCL_Consulta v_Consulta;//Clase que se encarga de ejecutar todas las Consultas
private Date currentDate = new Date();
private DateFormat currentDateFormat;
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


    //Realización de las Consultas de los años
    //-----------------------------
    v_declaracion = "SELECT DISTINCT (to_char(fes_fecha,'yyyy')) FROM tbfestivos GROUP BY  fes_fecha";
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
    v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    //----------------------------------
    TBPL_Publicar(v_resultadodeclaracion);
  }



      private void TBPL_Publicar(Vector v_opciones)//TBPL_Publicar la pagina en código HTML
      {
/*      currentDateFormat = DateFormat.getDateInstance(DateFormat.MONTH_FIELD, Locale.PRC);
      String d = currentDateFormat.format(currentDate);
      int ano = Integer.parseInt(d.substring(0,4));
      int ano1 = ano+1;
      int ano2 = ano+2;*/
      v_opciones.trimToSize();

/*      if (v_opciones.contains("No hay elementos"))
        v_opciones.remove(0);
      if (!v_opciones.contains(new Integer(ano).toString()))
        v_opciones.addElement((new Integer(ano).toString()));
      if (!v_opciones.contains(new Integer(ano1).toString()))
        v_opciones.addElement((new Integer(ano1).toString()));
      if (!v_opciones.contains(new Integer(ano2).toString()))
        v_opciones.addElement((new Integer(ano2).toString()));

      int anos[] = new int[v_opciones.size()];

      for (int i=0; i<anos.length; i++)
         anos[i]=Integer.parseInt(v_opciones.elementAt(i).toString());

      bubbleSort(anos);*/

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de festivos","Administración de festivos","TBPKT_PARAMETROS.TBPKT_FESTIVOS.TBCS_Festivos","",true));
      //Aqui van los campo que se quieren mostrar
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
      out.println("<BLOCKQUOTE>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adiciona'>Adicionar festivo<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='Consulta' CHECKED>Consultar festivos por año");
      out.println("&nbsp;&nbsp;<SELECT NAME=ano>");
      for (int i=0; i<v_opciones.size(); i++)
         {
         out.println("<OPTION>"+v_opciones.elementAt(i).toString());
         }

      out.println("</SELECT>");
      out.println("<BR>&nbsp;&nbsp;</BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE='Aceptar'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK='history.go(-1)'></CENTER>");
      out.println("</BLOCKQUOTE>");
      out.println("</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos

      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);

      out.close();
      }

/*   private void bubbleSort(int b[])
   {
   for (int pass = 1; pass < b.length; pass++)
      for (int i = 0; i < b.length - 1; i++)
         if (b[i]>b[i+1])
           swap(b, i, i+1);
   }

   private void swap(int c[], int first, int second)
   {
   int hold;
   hold = c[first];
   c[first] = c [second];
   c[second] = hold;
   }*/

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_FESTIVOS.TBCS_PrincipalFestivos Information";
  }
}

