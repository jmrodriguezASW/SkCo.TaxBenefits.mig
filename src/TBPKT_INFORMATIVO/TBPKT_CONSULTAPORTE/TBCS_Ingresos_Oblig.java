package TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE;

import TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.SQL_PTB_RETIROS_OBLIG;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTMLII;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_Ingresos_Oblig extends HttpServlet{
    
    private PrintWriter out;
    private TBCL_Consulta v_Consulta;
    private String v_nuevaCadena ="";
    HttpSession session  = null;
    
    public void init(ServletConfig config) throws ServletException {
      super.init(config);
    }
    
    /**
     * Process the HTTP Get request
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        String Consulta;          
        Vector v_resultadoConsulta = null;                
            
        //Toma el código del producto
        String v_codpro = "";
        //Toma el número de contrato
        String nocon = "";
        //Toma el mes inicial para la Consulta
        String mesini = "";
        //Toma el ano inicial para la Consulta
        String anoini = "";
        //Toma el mes final para la Consulta
        String mesfin = "";
        //Toma el ano final para la Consulta
        String anofin = "";
      
      try{
            out = new PrintWriter (response.getOutputStream());
            session = request.getSession(true);
            if(session==null)
                session = request.getSession(true);
            session.setMaxInactiveInterval(3600);
            response.setContentType("text/html");            
            String parametros[] = new String[8];
            String  cadena = request.getParameter("cadena");
            v_nuevaCadena = cadena;
            String ip_tax = request.getRemoteAddr();
            TBCL_Seguridad Seguridad = new TBCL_Seguridad();
            parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
                              
            v_Consulta = new TBCL_Consulta();            
            v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
        
            v_codpro = request.getParameter("v_codpro");
            nocon = request.getParameter("nocon");
            mesini = request.getParameter("mesini");
            anoini = request.getParameter("anoini");
            mesfin = request.getParameter("mesfin");
            anofin = request.getParameter("anofin");    

            Consulta = "SELECT apo_consecutivo\n"+
            "             , to_char(apo_fecha_efectiva, 'yyyy-mm-dd')\n"+
            "             , to_char(apo_fecha_proceso,'yyyy-mm-dd')\n"+
            "             , apo_capital\n"+
            "             , apo_rendimientos\n"+
            "             , apo_cuenta_contingente \n"+
            "             , APO_INFORMACION_TRASLADO\n"+
            "             , APO_APORTE_TRASLADO \n"+
            "             , apo_aporte_certificado   \n"+
            "             , apo_apo_consecutivo   \n"+
            "             , 'NA' Historia_pendiente \n"+
            "             , tbfbd_tipoaporteoblig(APO_CONSECUTIVO) Concepto \n" +            
            "          FROM tbaportes_oblig\n"+
            "         WHERE apo_fecha_efectiva BETWEEN to_date('"+mesini+" "+anoini+"', 'MM-YYYY','nls_date_language=ENGLISH') " +
            "           AND LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-YYYY','nls_date_language=ENGLISH')) \n"+
            "           AND apo_con_pro_codigo = '"+v_codpro+"' \n"+
            "           AND apo_con_numero = '"+nocon+"'"+
            "           AND APO_REF_ESTADO = 'SEA001' "+
            "      ORDER BY apo_fecha_efectiva";

            v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);                     
            
            v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      }
      catch (Exception e) { e.printStackTrace(); }
      TBPL_Publicar(v_resultadoConsulta, v_codpro, nocon);
    }



        private void TBPL_Publicar(Vector v_descripcion, String v_codpro, String nocon)
        {
        v_descripcion.trimToSize();
        String aporte = "N/A";
        String HistoriaPendiente = "N/A";
        String Certificado = "N/A";
        String concepto = "";

        if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
          {
          for (int i = 0; i<v_descripcion.size(); i++)
          {    if (v_descripcion.elementAt(i).toString().equals("null"))
                  v_descripcion.setElementAt(" ", i);
          }    

          out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE.TBCS_InformativoAporte_Oblig", "", true));
          //Aqui van los campo que se quieren mostrar
          out.println("<BR>&nbsp;&nbsp;<BR>");
          out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=8 rules=all width=650>");
          out.println("<TR align=middle class=\"td11\" borderColor=silver><TD width=\"5%\"><FONT color=white face=verdana size=1>&nbsp;</font></TD>");
          out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha Efectiva</B></font></TD>");
          out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha Proceso</B></font></TD>");
          out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Capital</B></font>");
          out.println("</TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Rendimientos</B></font></TD>");
          out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Cuenta Contingente</B></font></TD>");
          out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Aporte</B></font></TD>");
          out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Certificado</B></font></TD>");
          out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Historia Pendiente</B></font></TD>");
          out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Concepto</B></font></TD></TR>");          

          for (int i=0; i<v_descripcion.size(); i+=12)
             {
                concepto = v_descripcion.elementAt(i+11).equals("S")?"AVE":"AVA";
                out.println("<TR bgColor=white borderColor=silver>");
                out.println("<TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=consecutivo VALUE='"+v_descripcion.elementAt(i).toString()+";"+v_descripcion.elementAt(i+11).toString().trim()+"' CHECKED></font>\n</TD>");
                out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+1).toString()+"<INPUT TYPE=hidden NAME=fechaefectiva VALUE='"+v_descripcion.elementAt(i+1).toString()+"'></font>\n</TD>");
                out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+2).toString());
                out.println("</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+3).toString()))+"</font>\n</TD>");
                out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+4).toString()))+"</font>\n</TD>");
                out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+5).toString()))+"</font></TD>");
                
                 
                  if (!v_descripcion.elementAt(i+9).toString().trim().equals("")) {
                      aporte="H";
                      HistoriaPendiente = "N";
                      Certificado = "S";
                  } else {
                      aporte = "P";
                      Certificado = v_descripcion.elementAt(i+8).toString();
                      if (v_descripcion.elementAt(i+7).toString().equalsIgnoreCase("S")) {
                          HistoriaPendiente = "S";                                                
                          if (v_descripcion.elementAt(i+6).toString().equalsIgnoreCase("S"))
                              HistoriaPendiente = "N";                                                
                      } else {
                          aporte = "-";
                          HistoriaPendiente = "N/A"; 
                      }                                        
                  }    
                out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+aporte+"</font></TD>");
                out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+Certificado+"</font></TD>");
                out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+HistoriaPendiente+"</font></TD>");                
                out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+concepto+"</font></TD>");    
                out.println("</TR>");     
              }                      
          out.println("</TABLE>");
          out.println("<INPUT TYPE=hidden NAME=v_codigo VALUE='"+v_codpro+"'>");
          out.println("<INPUT TYPE=hidden NAME=v_Nocontrato VALUE='"+nocon+"'>");
          out.println("<BLOCKQUOTE>");
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
          out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='composicion' CHECKED>Composición actual del aporte<BR>");
          out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='retiros'>Retiros que han afectado al aporte<BR>");
          //out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='historia'>Historia del aporte<BR>");
          out.println("</font>");
          out.println("</BLOCKQUOTE>");
          out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=submit VALUE=Consultar><INPUT TYPE=button VALUE=Regresar  ONCLICK=history.go(-1);></font></CENTER>");
          }
        else
          {
          out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "", "No hay Elementos", false));
          out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=button VALUE=Regresar  ONCLICK=history.go(-1);></font></CENTER>");
          }
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        out.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
        out.close();
        }
}     
