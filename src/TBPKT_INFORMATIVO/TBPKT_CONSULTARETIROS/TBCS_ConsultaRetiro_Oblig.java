package TBPKT_INFORMATIVO.TBPKT_CONSULTARETIROS;

import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;

import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

import java.io.IOException;

import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TBCS_ConsultaRetiro_Oblig extends HttpServlet{
    
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
    
      String v_codproducto = "";      
      String v_Nocontrato = "";      

      try{
         v_codproducto = parametros[1];
         v_Nocontrato  = parametros[0];
         }
      catch (Exception e) { e.printStackTrace(); }

        Consulta = "SELECT con_pro_codigo, con_numero, con_nombres, con_numero_identificacion, con_apellidos " +
                   " FROM tbcontratos_oblig "+ 
                   " WHERE con_pro_codigo = ? AND con_numero = ? ";
        String v_parametros[] = new String[2];
        v_parametros[0] = v_codproducto; 
        v_parametros[1] = ""+Integer.parseInt(v_Nocontrato); 
        v_resultadoConsulta = v_Consulta.TBFL_ConsultaParametros(Consulta, v_parametros);

        Consulta = "SELECT distinct(to_char(ret_fecha_efectiva,'yyyy')) " + 
                   "FROM tbretiros_oblig "+ 
                   "WHERE ret_con_pro_codigo = ?  AND ret_con_numero = ? "+
                   "GROUP BY  ret_fecha_efectiva";
        v_parametros = new String[2];
        v_parametros[0] = v_codproducto; 
        v_parametros[1] = ""+Integer.parseInt(v_Nocontrato); 
        resultadoConsulta1 = v_Consulta.TBFL_ConsultaParametros(Consulta, v_parametros);

        TBPL_Publicar(v_resultadoConsulta, resultadoConsulta1);
        v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    }


        private void TBPL_Publicar(Vector v_descripcion, Vector anos)
        {
        v_descripcion.trimToSize();
        anos.trimToSize();
        
        if (anos.elementAt(0).toString().equals("No hay elementos")) {
            out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de retiros","Consulta de retiros", "", "<BLOCKQUOTE>"+anos.elementAt(0).toString()+"&nbsp;para este contrato</BLOCKQUOTE>", false));
            out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");
        }
        else {
        
            for (int i = 0; i<v_descripcion.size(); i++)
               if (v_descripcion.elementAt(i).toString().equals("null"))
                 v_descripcion.setElementAt(" ", i);
            
            if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
              {
              out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de retiros","Consulta de retiros", "TBPKT_INFORMATIVO.TBPKT_CONSULTARETIROS.TBCS_Retiros_Oblig", "", true));
              
              out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
              out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_descripcion.elementAt(0).toString()+"'></font></TD></TR>");          
              String v_contrato_unif = "";          
              v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_descripcion.elementAt(0).toString(), v_descripcion.elementAt(1).toString());          
              out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Número del contrato</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_contrato_unif +"<INPUT TYPE=hidden NAME=nocon VALUE='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
              out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Nombre del cliente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
              out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Apellidos del cliente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"</font></TD></TR>");
              out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cédula del cliente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
              out.println("<TR align=middle class=\"td11\" borderColor=silver><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha efectiva inicial</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha efectiva final</B></font></TD></TR>");
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
              out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de retiros","Consulta de retiros", "", "<BLOCKQUOTE>"+v_descripcion.elementAt(0).toString()+"&nbsp;para este contrato</BLOCKQUOTE>", false));
              out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");
              }
        }
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
        out.close();
        }
}
