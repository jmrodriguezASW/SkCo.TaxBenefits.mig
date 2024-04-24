package TBPKT_INFORMATIVO.TBPKT_CONSULTARETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_ConsultaTraslado extends HttpServlet{
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
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  String parametros[] = new String[8];
    try{
       out = new PrintWriter (response.getOutputStream());
       response.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
       //response.setHeader("Pragma", "No-Cache");
       //response.setDateHeader("Expires", 0);
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
    Vector v_resultadoConsulta;

    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    String fechaini = null; 
    String fechafin = null; 
    String v_result = null;

    try{
           fechaini = request.getParameter("fechaini").trim();
           fechafin = request.getParameter("fechafin").trim();
           v_result = request.getParameter("v_result");

           String fechainiTrunc = "";
           for (int i=0; i < fechaini.length(); i++) {
             if (fechaini.charAt(i) != '-')
               fechainiTrunc += fechaini.charAt(i);
           }
           fechaini =  fechainiTrunc;

           String fechafinTrunc = "";
           for (int i=0; i < fechafin.length(); i++) {
             if (fechafin.charAt(i) != '-')
               fechafinTrunc += fechafin.charAt(i);
           }
           fechafin =  fechafinTrunc;
     }
    catch (Exception e) { e.printStackTrace(); }

    Consulta = "SELECT CONTRATOORIG, TMSCDX, TMLFCX, TMD6CZ, "+
               "TMADTN, TMBVNZ, RESULTADO " +
               "FROM tbinterface_aftmcpp "+
               "WHERE  to_date(TMSCDX,'RRRRMMDD') between "+
               "to_date('"+ fechaini +"','RRRRMMDD') AND " + 
               "to_date('"+ fechafin +"','RRRRMMDD')";
     if (!v_result.equals("Todos"))
     {
       Consulta = Consulta + " AND RESULTADO='"+v_result+"'";
     }

     v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);
     v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
     TBPL_Publicar(v_resultadoConsulta);

  }


      private void TBPL_Publicar(Vector v_descripcion)
      {

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de retiros traslado","Consulta de retiros traslado", "", "", true));
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      v_descripcion.trimToSize();

      for (int i = 0; i<v_descripcion.size(); i=i+7){
         if (v_descripcion.elementAt(i).toString().equals("null"))
           v_descripcion.setElementAt(" ", i);
            if (!v_descripcion.elementAt(i).toString().equals("No hay elementos")){
                 if (i == 0){
                    out.println("<TR bgColor=white borderColor=silver>");
                    out.println("<TD width=\"12%\"><FONT color=black face=verdana size=1><B>Contrato Origen</B></font></TD>");
                    out.println("<TD width=\"12%\"><FONT color=black face=verdana size=1><B>Fecha</B></font></TD>");
                    out.println("<TD width=\"12%\"><FONT color=black face=verdana size=1><B>Contracto</B></font></TD>");
                    out.println("<TD width=\"12%\"><FONT color=black face=verdana size=1><B>Producto</B></font></TD>");
                    out.println("<TD width=\"12%\"><FONT color=black face=verdana size=1><B>Documento</B></font></TD>");
                    out.println("<TD width=\"12%\"><FONT color=black face=verdana size=1><B>Usuario</B></font></TD>");
                    out.println("<TD width=\"12%\"><FONT color=black face=verdana size=1><B>Resultado</B></font></TD>");
                    out.println("</TR>");
                  }
        
                  out.println("<TR bgColor=white borderColor=silver>");
                  out.println("<TD width=\"12%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD>");
                  out.println("<TD width=\"12%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+1).toString()+"</font></TD>");
                  out.println("<TD width=\"12%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+2).toString()+"</font></TD>");
                  out.println("<TD width=\"12%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+3).toString()+"</font></TD>");
                  out.println("<TD width=\"12%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+4).toString()+"</font></TD>");
                  out.println("<TD width=\"12%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+5).toString()+"</font></TD>");
                  out.println("<TD width=\"12%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+6).toString()+"</font></TD>");
                  out.println("</TR>");
            }
            else{
                out.println("<TR bgColor=white borderColor=silver>");
                out.println("<TD width=\"100%\"><FONT color=black face=verdana size=1>No hay elementos</font></TD>");
                out.println("</TR>");
            }
      }
      
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=submit VALUE=Consultar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONSULTARETIROS.TBCS_ConsultaTraslado Information";
  }
}

