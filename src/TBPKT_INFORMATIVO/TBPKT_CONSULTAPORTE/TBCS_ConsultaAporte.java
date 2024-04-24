package TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE;

import TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.SQL_PTB_RETIROS_OBLIG;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

/**
*/
public class TBCS_ConsultaAporte extends HttpServlet implements SingleThreadModel{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
HttpSession session  = null;
private String v_v_nuevaCadena ="";

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
       session = request.getSession(true);
       if(session==null)
        session = request.getSession(true);
       session.setMaxInactiveInterval(3600);
       response.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
       //response.setHeader("Pragma", "No-Cache");
       //response.setDateHeader("Expires", 0);
       String  cadena = request.getParameter("cadena");
       session.removeValue("s_consultaas");
       v_v_nuevaCadena = cadena;
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


    //Toma el código del producto
    String v_codproducto = "";
    //Toma el número de contrato
    String v_Nocontrato = "";
    //Permite saber cual es el tamaño del contrato introducido por el cliente(debe ser nueve)
    int v_contratolength = 0;

    try{
       v_codproducto = parametros[1];
       v_Nocontrato = parametros[0];
       }
    catch (Exception e) { e.printStackTrace(); }

     v_contratolength = v_Nocontrato.length();

     if (v_codproducto.toString().trim().equals("FPOB") || v_codproducto.toString().trim().equals("FPAL")) {
        
        v_Nocontrato = ""+Integer.parseInt(v_Nocontrato);
        
        Consulta ="SELECT con_pro_codigo\n"+
                "      , con_numero\n"+
                "       , con_nombres\n"+
                "       , con_numero_identificacion\n"+
                "       , con_apellidos\n"+
                "    FROM tbcontratos_oblig\n"+
                "   WHERE con_pro_codigo = '"+v_codproducto+"'\n"+
                "     AND con_numero = '"+v_Nocontrato+"'";
        
        v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);      
        
        Consulta = "SELECT distinct(to_char(apo_fecha_efectiva,'yyyy'))\n"+
         "           FROM tbaportes_oblig\n"+
         "          WHERE apo_con_pro_codigo = '"+v_codproducto+"'\n"+
         "            AND apo_con_numero = '"+v_Nocontrato+"'\n"+
         "       GROUP BY  apo_fecha_efectiva";
        
        resultadoConsulta1 = v_Consulta.TBFL_Consulta(Consulta);
     }
     else {         
         switch (v_contratolength)
               {
               case 1:
                   v_Nocontrato = "00000000"+v_Nocontrato;
               break;
               case 2:
                   v_Nocontrato = "0000000"+v_Nocontrato;
               break;
               case 3:
                   v_Nocontrato = "000000"+v_Nocontrato;
               break;
               case 4:
                   v_Nocontrato = "00000"+v_Nocontrato;
               break;
               case 5:
                   v_Nocontrato = "0000"+v_Nocontrato;
               break;
               case 6:
                   v_Nocontrato = "000"+v_Nocontrato;
               break;
               case 7:
                   v_Nocontrato = "00"+v_Nocontrato;
               break;
               case 8:
                   v_Nocontrato = "0"+v_Nocontrato;
               break;
               default:
                   v_Nocontrato = v_Nocontrato;
               break;
               }
         
         Consulta ="SELECT con_pro_codigo\n"+
                   "      , con_numero\n"+
                   "       , con_nombres\n"+
                   "       , con_numero_identificacion\n"+
                   "       , con_apellidos\n"+
                   "    FROM tbcontratos\n"+
                   "   WHERE con_pro_codigo = '"+v_codproducto+"'\n"+
                   "     AND con_numero = '"+v_Nocontrato+"'";
         v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);      

         Consulta = "SELECT distinct(to_char(apo_fecha_efectiva,'yyyy'))\n"+
            "           FROM tbaportes\n"+
            "          WHERE apo_con_pro_codigo = '"+v_codproducto+"'\n"+
            "            AND apo_con_numero = '"+v_Nocontrato+"'\n"+
            "       GROUP BY  apo_fecha_efectiva";
         resultadoConsulta1 = v_Consulta.TBFL_Consulta(Consulta);
         
     }

           

    TBPL_Publicar(v_resultadoConsulta, resultadoConsulta1); //Publicación de la pagina en código HTML    
    v_Consulta.TBPL_shutDown();//Desconexion a la base de datos    
  }



      private void TBPL_Publicar(Vector v_descripcion, Vector anos)
      {
      v_descripcion.trimToSize();
      anos.trimToSize();
      if (anos.elementAt(0).toString().equals("No hay elementos"))
        anos.setElementAt(" ", 0);

      for (int i = 0; i<v_descripcion.size(); i++)
         if (v_descripcion.elementAt(i).toString().equals("null"))
           v_descripcion.setElementAt(" ", i);

      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        {
        if (v_descripcion.elementAt(0).toString().trim().equals("FPOB") || v_descripcion.elementAt(0).toString().trim().equals("FPAL"))
            out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE.TBCS_Ingresos_Oblig", "", true));    
        else
            out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE.TBCS_Ingresos", "", true));
        //Aqui van los campo que se quieren mostrar
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");
        /*Cambio para manejo de referencia unica 2009/04/01 MOS */
        String v_contrato_unif = "";
        //v_Consulta.TBPL_RealizarConexion();
        v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_descripcion.elementAt(0).toString(), v_descripcion.elementAt(1).toString());
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Número del contrato</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_contrato_unif +"<INPUT TYPE=hidden NAME=nocon VALUE='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
        //v_Consulta.TBPL_shutDown();
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Nombre del cliente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Apellidos del cliente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cédula del cliente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
        out.println("<TR align=middle class=\"td11\" borderColor=silver><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha inicial</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha final</B></font></TD></TR>");
        out.println("<TR align=middle bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><select name=\"mesini\" size=1><option VALUE=Jan>Enero</option><option VALUE=Feb>Febrero</option><option VALUE=Mar>Marzo</option><option VALUE=apr>Abril</option><option VALUE=May>Mayo</option><option VALUE=Jun>Junio</option><option VALUE=Jul>Julio</option><option VALUE=Aug>Agosto</option><option VALUE=Sep>Septiembre</option><option VALUE=Oct>Octubre</option><option VALUE=Nov>Noviembre</option><option VALUE=Dec>Diciembre</option></select><select name=\"anoini\" size=1>");
        for (int i=0; i<anos.size(); i++)
           {
           out.println("<OPTION>"+anos.elementAt(i).toString());
           }
        out.println("</SELECT></font></TD><TD width=\"22%\">");
        out.println("<FONT color=black face=verdana size=1><select name=\"mesfin\" size=1><option VALUE=Jan>Enero</option><option VALUE=Feb>Febrero</option><option VALUE=Mar>Marzo</option><option VALUE=apr>Abril</option><option VALUE=May>Mayo</option><option VALUE=Jun>Junio</option><option VALUE=Jul>Julio</option><option VALUE=Aug>Agosto</option><option VALUE=Sep>Septiembre</option><option VALUE=Oct>Octubre</option><option VALUE=Nov>Noviembre</option><option VALUE=Dec>Diciembre</option></select><select name=\"anofin\" size=1>");
        for (int i=0; i<anos.size(); i++)
           {
           out.println("<OPTION>"+anos.elementAt(i).toString());
           }
        out.println("</SELECT></font></TD></TR>");
        out.println("</TABLE>");
        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=submit VALUE=Consultar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");
        }
      else
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "", "<BLOCKQUOTE>"+v_descripcion.elementAt(0).toString()+"&nbsp;para este contrato</BLOCKQUOTE>", false));
        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");
        }
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONSULTAPORTE.TBCS_ConsultaAporte Information";
  }
}

