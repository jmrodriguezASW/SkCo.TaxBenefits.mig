package TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE;

import javax.servlet.*;
import java.io.*;
import java.text.DecimalFormat;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTMLII;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_AS400_APORTES.TBCL_FUNCIONES_AS400_APORTES;
import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;
import java.util.Vector;
import javax.servlet.http.*;

import javax.swing.text.html.HTML;

public class TBCS_ConsultaDisponible extends HttpServlet{

private PrintWriter out;
private TBCL_Consulta v_Consulta;
HttpSession session  = null;
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
      String ip_tax = "";
      try{
          out = new PrintWriter (response.getOutputStream());
          session = request.getSession(true);
          if(session==null)
              session = request.getSession(true);
          session.setMaxInactiveInterval(3600);
          response.setContentType("text/html");
          String  cadena = request.getParameter("cadena");
          session.removeAttribute("s_consultaas");
          v_nuevaCadena = cadena;
          ip_tax = request.getRemoteAddr();
           
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
          parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }catch(Exception ex) {   
            System.out.println("");
        }
      v_Consulta = new TBCL_Consulta();
      Modelo_TB_Referencias objModelo_TB_Referencias = new Modelo_TB_Referencias();
      SQL_TB_FREFERENCIAS_FPOB objSQL_TB_FREFERENCIAS_FPOB = new SQL_TB_FREFERENCIAS_FPOB();
      
      v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
      
      //Toma el código del producto
      String v_codproducto = "";
      //Toma el número de contrato
      String v_Nocontrato = "";          
      String Consulta;
      Vector v_resultadoConsulta;
      String[] Saldos400,  SaldosFinales, comisiones;
      //Saldos de Aporte Voluntario para productos de FPOB y FPAL        
      double v_saldoAVA = 0, v_saldoAVE = 0, Credito = 0;
     
      try{
            v_codproducto = parametros[1];
            v_Nocontrato = parametros[0];            
      }
      catch (Exception e) { e.printStackTrace(); }
      
      //Obtenemos la identificacion del afiliado
      Consulta ="SELECT ref_valor, \n"+
        "   con_numero_identificacion \n"+
        "   FROM tbcontratos_oblig \n"+
        "   INNER JOIN TBREFERENCIAS on CON_REF_TIPO_IDENTIFICACION = REF_CODIGO \n"+
        "   WHERE con_pro_codigo = '"+v_codproducto+"'\n"+
        "     AND lpad(con_numero, 9, '0') = '"+v_Nocontrato+"'";
      v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);
      
      for (int i = 0; i<v_resultadoConsulta.size(); i++)
        if (v_resultadoConsulta.elementAt(i).toString().equals("null"))
          v_resultadoConsulta.setElementAt(" ", i);
      
      if (v_resultadoConsulta.elementAt(0).toString().equals("No hay elementos") || v_resultadoConsulta.elementAt(0) == null)
            TBPL_Publicar(null,null, null, Credito);
      else {
      
        objModelo_TB_Referencias = objSQL_TB_FREFERENCIAS_FPOB.GET_TB_FREFERENCIAS_FPOB(v_codproducto);                       
          /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Saldo_Contrato_OBLIG de la clase TBCL_FuncionesAs400_Oblig, no es necesaria la instancia nueva*/    
        //TBCL_FuncionesAs400_Oblig Saldo_Contrato = new TBCL_FuncionesAs400_Oblig();

        String resultsaldo = TBCL_FuncionesAs400_Oblig.TBFL_Saldo_Contrato_OBLIG(v_codproducto,"E",v_Consulta.TBFL_Consulta("SELECT TO_CHAR(SYSDATE, 'YYYYMMDD') FROM DUAL").elementAt(0).toString(),
                                                                    v_resultadoConsulta.elementAt(0).toString(),
                                                                    v_resultadoConsulta.elementAt(1).toString(),                                                                    
                                                                    objModelo_TB_Referencias.getLibreria(),
                                                                    objModelo_TB_Referencias.getSistema(), 
                                                                    objModelo_TB_Referencias.getUsuario(),
                                                                    objModelo_TB_Referencias.getPassword());         
        if(!resultsaldo.substring(0,5).toUpperCase().equals("ERROR")) {    
            Saldos400 = resultsaldo.split(";"); 
            v_saldoAVA = Double.parseDouble(Saldos400[0]); 
            v_saldoAVE = Double.parseDouble(Saldos400[1]); 
            
            String resultCredito = TBCL_FuncionesAs400_Oblig.TBFL_Saldo_Credito( v_codproducto,
                                                v_resultadoConsulta.elementAt(0).toString(),
                                                v_resultadoConsulta.elementAt(1).toString(), 
                                                objModelo_TB_Referencias.getLibreria(),
                                                objModelo_TB_Referencias.getSistema(), 
                                                objModelo_TB_Referencias.getUsuario(),
                                                objModelo_TB_Referencias.getPassword());
            
            if(!resultCredito.substring(0,3).toUpperCase().equals("ERR")) 
                Credito = Double.parseDouble(resultCredito);     
            
            Consultas_As400_OBLIG objConsultas_As400_OBLIG = new Consultas_As400_OBLIG();
            comisiones = objConsultas_As400_OBLIG.GET_COMISION_ADMIN_AS400_OBLIG(objModelo_TB_Referencias.getSistema(), 
                                                                   objModelo_TB_Referencias.getlibreriadatos(), 
                                                                   objModelo_TB_Referencias.getUsuario(), 
                                                                   objModelo_TB_Referencias.getPassword(), 
                                                                   v_codproducto, v_Nocontrato).split(";");
            
            SQL_PTB_DISPONIBLE_OBLIG objSQL_PTB_DISPONIBLE_OBLIG = new SQL_PTB_DISPONIBLE_OBLIG();
            SaldosFinales = objSQL_PTB_DISPONIBLE_OBLIG.GET_DISPONIBLE_OBLIG(v_codproducto, v_Nocontrato, v_saldoAVA, v_saldoAVE, 
                                                                             v_Consulta.TBFL_Consulta("SELECT TO_CHAR(SYSDATE, 'YYYY-MM-DD') FROM DUAL").elementAt(0).toString(), 
                                                                             comisiones, Credito);

            if (SaldosFinales != null) 
                TBPL_Publicar(SaldosFinales, Saldos400, comisiones, Credito);    
            else
                TBPL_Publicar(null, null, null, Credito);
                    
        }
        else {
            TBPL_Publicar(null,null, null, Credito);  
        }                      
      }    
    }
  
    private void TBPL_Publicar(String[] SaldosDisponible, String[] Saldos400, String[] comisiones, double Credito)
      {                        
        DecimalFormat v_format = new  DecimalFormat("¤###,###,###,###,###,###.##");            
        if (SaldosDisponible!=null && Saldos400!=null) { 
          //saldos maestros
          out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA_NEW("SALDO APORTES OBLIGATORIOS","SALDO APORTES OBLIGATORIOS", "", "", true));          
          out.println("<BR>");            
          //CABECERA 1
          out.println("<TABLE class=table2 bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=3 rules=all style=\"table-layout:fixed\">");            
          out.println("<TR align=middle class=\"td11\" borderColor=silver>");
          out.println("<TD width=\"39%\"><FONT color=white face=verdana size=1><B>Concepto</B></font></TD>");                      
          out.println("<TD width=\"31%\"><FONT color=white face=verdana size=1><B>Aportes Obligatorios</B></font></TD></TR>");
          //Detalle 1
          out.println("<TR bgColor=white borderColor=silver><TD><FONT color=black face=verdana size=1>Saldo Obligatorio</font></TD><TD align=right><FONT color=black face=verdana size=1>"+v_format.format(Double.parseDouble(Saldos400[3])).toString()+"</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD><FONT color=black face=verdana size=1>Valor Recibido por Mora</font></TD><TD align=right><FONT color=black face=verdana size=1>"+v_format.format(Double.parseDouble(Saldos400[4])).toString()+"</font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD><FONT color=black face=verdana size=1><B>Saldo Total</font></TD><TD align=right><FONT color=black face=verdana size=1><B>"+v_format.format(Double.parseDouble(Saldos400[3])+Double.parseDouble(Saldos400[4])).toString()+"</B></font></TD></TR>");
          out.println("</TABLE>");           
          out.println("</table></form></body></html>");
          
          out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA_NEW("SALDO APORTES VOLUNTARIOS","SALDO APORTES VOLUNTARIOS", "", "", true));          
          out.println("<BR>"); 
          //CABECERA 2
          out.println("<TABLE class=table2 bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=3 rules=all style=\"table-layout:fixed\">");            
          out.println("<TR align=middle class=\"td11\" borderColor=silver>");
          out.println("<TD width=\"39%\"><FONT color=white face=verdana size=1><B>Concepto</B></font></TD>");            
          out.println("<TD width=\"30%\"><FONT color=white face=verdana size=1><B>Aportes Voluntario Afiliado</B></font></TD>");
          out.println("<TD width=\"31%\"><FONT color=white face=verdana size=1><B>Aportes Voluntario Empresa</B></font></TD></TR>");
          //Detalle 2                                                                                                                   
          out.println("<TR bgColor=white borderColor=silver><TD><FONT color=black face=verdana size=1><B>(+) Saldo Aportes Voluntarios</B></font></TD><TD align=right><FONT color=black face=verdana size=1>"+v_format.format(Double.parseDouble(Saldos400[0])).toString()+"</font></TD><TD align=right><FONT color=black face=verdana size=1>"+v_format.format(Double.parseDouble(Saldos400[1])).toString()+"</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD><FONT color=black face=verdana size=1>(-) Aportes sin Certificar</font></TD><TD align=right><FONT color=black face=verdana size=1>"+SaldosDisponible[2].toString()+"</font></TD><TD align=right><FONT color=black face=verdana size=1>"+SaldosDisponible[3].toString()+"</font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD><FONT color=black face=verdana size=1>(-) Aportes con Historia Pendiente</font></TD><TD align=right><FONT color=black face=verdana size=1>"+SaldosDisponible[4].toString()+"</font></TD><TD align=right><FONT color=black face=verdana size=1>"+SaldosDisponible[5].toString()+"</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD><FONT color=black face=verdana size=1>(-) Retiros Pendientes por Procesar</font></TD><TD align=right><FONT color=black face=verdana size=1>"+SaldosDisponible[6].toString()+"</font></TD><TD align=right><FONT color=black face=verdana size=1>"+SaldosDisponible[7].toString()+"</font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD><FONT color=black face=verdana size=1>(-) Retención Contingente</font></TD><TD align=right><FONT color=black face=verdana size=1>"+SaldosDisponible[8].toString()+"</font></TD><TD align=right><FONT color=black face=verdana size=1>"+SaldosDisponible[9].toString()+"</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD><FONT color=black face=verdana size=1>(-) Retención Sobre Rendimientos</font></TD><TD align=right><FONT color=black face=verdana size=1>"+SaldosDisponible[10].toString()+"</font></TD><TD align=right><FONT color=black face=verdana size=1>"+SaldosDisponible[11].toString()+"</font></TD></TR>");                                      
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD><FONT color=black face=verdana size=1>(-) Provisión crédito saldo disponible</font></TD><TD align=right><FONT color=black face=verdana size=1>"+SaldosDisponible[20].toString()+"</font></TD><TD align=right><FONT color=black face=verdana size=1></font></TD></TR>");                                      
          out.println("<TR bgColor=white borderColor=silver><TD><FONT color=black face=verdana size=1>(-) Provisión comisión saldo disponible</font></TD><TD align=right><FONT color=black face=verdana size=1>"+SaldosDisponible[18].toString()+"</font></TD><TD align=right><FONT color=black face=verdana size=1>"+SaldosDisponible[19].toString()+"</font></TD></TR>");                                        
          out.println("<TR bgColor=white borderColor=silver><TD><FONT color=gray face=verdana size=1>Créditos Vigentes</font></TD><TD align=right><FONT color=gray face=verdana size=1>"+v_format.format(Credito)+"</font></TD><TD align=right><FONT color=gray face=verdana size=1></font></TD></TR>");
          out.println("<TR bgColor=white borderColor=silver><TD><FONT color=gray face=verdana size=1>Comisión de Administración</font></TD><TD align=right><FONT color=gray face=verdana size=1>"+v_format.format(Double.parseDouble(comisiones[0])).toString()+"</font></TD><TD align=right><FONT color=gray face=verdana size=1>"+v_format.format(Double.parseDouble(comisiones[1])).toString()+"</font></TD></TR>");
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD><FONT color=black face=verdana size=1><B>(=) Saldo Disponible Para Retiro</B></font></TD><TD align=right><FONT color=black face=verdana size=1><B>"+SaldosDisponible[12]+"</B></font></TD><TD align=right><FONT color=black face=verdana size=1><B>"+SaldosDisponible[13]+"</B></font></TD></TR>");         
          out.println("</TABLE>"); 
          out.println("<BR>"); 
          out.println("<TABLE class=\"table3\" cellPadding=0 cellSpacing=0 border=0>");
          out.println("<TBODY><TR><TD align=left><span class=\"subtitulo\" ><FONT color=black face=verdana size=2><B>SALDO TOTAL DEL CONTRATO</B></FONT></span></TD><TD align=right><span class=\"subtitulo\"><FONT color=black face=verdana size=2><B>"+v_format.format(Double.parseDouble(Saldos400[3])+Double.parseDouble(Saldos400[4])+Double.parseDouble(Saldos400[0])+Double.parseDouble(Saldos400[1])).toString()+"</B></FONT></span></TD></TR></TBODY></TABLE>");                  
          out.println("</form></body></html>");
      }
      else {        
                out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA_NEW("Saldo Disponible Aportes Voluntarios","Saldo Disponible Aportes Voluntarios", "", "No hay Elementos", false));                
            }
         
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");  
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");            
      out.close();
      }      
}
