package TBPKT_PARAMETROS.TBPKT_FORMATOCERTIFICADO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_FormatoCertificado extends HttpServlet{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
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
       out = new PrintWriter (response.getOutputStream());
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
    String Consulta;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadoConsulta, resultadoConsulta1;
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

//------------------------------------------



//SELECT DISTINCT (foc_ano) FROM tbformatos_certificado ORDER BY  foc_ano


      Consulta = "SELECT pro_codigo FROM tbproductos ORDER BY pro_codigo";
      v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta, "pro_codigo");

      Consulta = "SELECT DISTINCT (foc_ano) FROM tbformatos_certificado ORDER BY  foc_ano";
      resultadoConsulta1 = v_Consulta.TBFL_Consulta(Consulta, "foc_ano");


      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      PublicarConsultar(v_resultadoConsulta, resultadoConsulta1);
  }

      private void PublicarConsultar(Vector v_opciones, Vector v_opciones1)
      {
      currentDateFormat = DateFormat.getDateInstance(DateFormat.MONTH_FIELD, Locale.PRC);
      String d = currentDateFormat.format(currentDate);
      int ano = Integer.parseInt(d.substring(0,4));
      int ano1 = ano+1;
      int ano2 = ano+2;
      int ano3 = ano+3;
      int ano4 = ano+4;

      v_opciones.trimToSize();
      v_opciones1.trimToSize();
      //Adiciona años al vector
      if (v_opciones1.contains("No hay elementos"))
        v_opciones1.remove(0);
      if (!v_opciones1.contains(new Integer(ano).toString()))
        v_opciones1.addElement((new Integer(ano).toString()));
      if (!v_opciones1.contains(new Integer(ano1).toString()))
        v_opciones1.addElement((new Integer(ano1).toString()));
      if (!v_opciones1.contains(new Integer(ano2).toString()))
        v_opciones1.addElement((new Integer(ano2).toString()));
      if (!v_opciones1.contains(new Integer(ano3).toString()))
        v_opciones1.addElement((new Integer(ano3).toString()));
      if (!v_opciones1.contains(new Integer(ano4).toString()))
        v_opciones1.addElement((new Integer(ano4).toString()));


      //convierte el Vector en una arreglo entero
      int anos[] = new int[v_opciones1.size()];

      for (int i=0; i<anos.length; i++)
         anos[i]=Integer.parseInt(v_opciones1.elementAt(i).toString());

      anos = bubbleSort(anos);


      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de formatos de certificado de retención en la fuente", "Administración de formatos de certificados de retención en la fuente","TBPKT_PARAMETROS.TBPKT_FORMATOCERTIFICADO.TBCS_ConsultaFormatoCertificado", "", true));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD><font face=Verdana size='1'><font color='#000000'><B>Año del formato</B></TD><TD><font face=Verdana size='1'><font color='#000000'><SELECT NAME=obligatorioano>");
      for (int i=0; i<v_opciones1.size(); i++)
         {
         out.println("<OPTION>"+anos[i]);
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD><font face=Verdana size='1'><font color='#000000'><BR><B>Código del producto</B></TD><TD><font face=Verdana size='1'><font color='#000000'><SELECT NAME=v_codigo>");
      for (int i=0; i<v_opciones.size(); i++)
         {
         out.println("<OPTION>"+v_opciones.elementAt(i).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);><CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

   private int[] bubbleSort(int b[])
   {
   for (int pass = 1; pass < b.length; pass++)
      for (int i = 0; i < b.length - 1; i++)
         if (b[i]>b[i+1])
           swap(b, i, i+1);

    return b;       
   }

   private void swap(int c[], int first, int second)
   {
   int hold;
   hold = c[first];
   c[first] = c [second];
   c[second] = hold;
   }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_FORMATOCERTIFICADO.TBCL_FormatoCertificado Information";
  }
}

