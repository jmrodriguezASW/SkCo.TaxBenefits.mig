package TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE;

import TBPKT_UTILIDADES.TBPKT_AS400_APORTES.TBCL_FUNCIONES_AS400_APORTES;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;

import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.TBCL_FuncionesAs400_Oblig;

import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTMLII;
import com.ibm.as400.access.*;

import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

import com.ibm.as400.access.AS400;

import java.text.NumberFormat;


/**
 */
public class TBCS_ConsultaDetalleAportes extends HttpServlet{
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
       }
    catch(Exception ex){System.out.println("");}
    v_Consulta = new TBCL_Consulta();
    String Consulta,libreria, ruta, usr, pass;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadoDetalle_AVA = null; 
    Vector v_resultadoDetalle_AVE = null;
    Vector resultadoSaldoUnidades_AVA = null;
    Vector resultadoSaldoUnidades_AVE = null;
    Vector v_resultadoConsulta = null;
    double SaldoUnidades_AVA, SaldoUnidades_AVE, SaldoContrato_AVA, SaldoContrato_AVE = 0.0;
    Modelo_TB_Referencias objModelo_TB_Referencias = new Modelo_TB_Referencias();
    SQL_TB_FREFERENCIAS_FPOB objSQL_TB_FREFERENCIAS_FPOB = new SQL_TB_FREFERENCIAS_FPOB();

    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
    
    String[] Saldos400;

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
    
      v_Nocontrato = ""+Integer.parseInt(v_Nocontrato);
    
      Consulta ="SELECT ref_valor, \n"+
                "   con_numero_identificacion \n"+
                "   FROM tbcontratos_oblig \n"+
                "   INNER JOIN TBREFERENCIAS on CON_REF_TIPO_IDENTIFICACION = REF_CODIGO \n"+
                "   WHERE con_pro_codigo = '"+v_codproducto+"'\n"+
                "     AND con_numero = '"+v_Nocontrato+"' \n";
      v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);
      
      for (int i = 0; i<v_resultadoConsulta.size(); i++)
         if (v_resultadoConsulta.elementAt(i).toString().equals("null"))
           v_resultadoConsulta.setElementAt(" ", i);
              
       Consulta ="SELECT SUM(APO_SALDO_NUMERO_UNIDADES)\n"+
                 "    FROM tbaportes_oblig\n"+
                 "   WHERE APO_CON_PRO_CODIGO = '"+v_codproducto+"'\n"+
                 "     AND apo_con_numero = '"+v_Nocontrato+"' \n" +
                 "     AND (( APO_APORTE_TRASLADO ='S' AND APO_INFORMACION_TRASLADO = 'N') OR ( APO_APORTE_TRASLADO ='N')) \n" +
                 "     AND TBFBD_TipoAporteOblig(APO_CONSECUTIVO) = 'N' \n" +
                 "     AND APO_REF_ESTADO = 'SEA001' \n";
       resultadoSaldoUnidades_AVA = v_Consulta.TBFL_Consulta(Consulta);
       
      Consulta ="SELECT SUM(APO_SALDO_NUMERO_UNIDADES)\n"+
                "    FROM tbaportes_oblig\n"+
                "   WHERE APO_CON_PRO_CODIGO = '"+v_codproducto+"'\n"+
                "     AND apo_con_numero = '"+v_Nocontrato+"' \n" +
                "     AND (( APO_APORTE_TRASLADO ='S' AND APO_INFORMACION_TRASLADO = 'N') OR ( APO_APORTE_TRASLADO ='N')) \n" +
                "     AND TBFBD_TipoAporteOblig(APO_CONSECUTIVO) = 'S' \n" +
                "     AND APO_REF_ESTADO = 'SEA001' \n";
      resultadoSaldoUnidades_AVE = v_Consulta.TBFL_Consulta(Consulta);
           
      for (int i = 0; i<resultadoSaldoUnidades_AVA.size(); i++)
         if (resultadoSaldoUnidades_AVA.elementAt(i).toString().equals("null"))
           resultadoSaldoUnidades_AVA.setElementAt(" ", i);
      
      for (int i = 0; i<resultadoSaldoUnidades_AVE.size(); i++)
         if (resultadoSaldoUnidades_AVE.elementAt(i).toString().equals("null"))
           resultadoSaldoUnidades_AVE.setElementAt(" ", i);

        if (((resultadoSaldoUnidades_AVA.elementAt(0).toString().equals("No hay elementos") || resultadoSaldoUnidades_AVA.elementAt(0) == null)
            && (resultadoSaldoUnidades_AVE.elementAt(0).toString().equals("No hay elementos") || resultadoSaldoUnidades_AVE.elementAt(0) == null))
            || v_resultadoConsulta.elementAt(0).toString().equals("No hay elementos") || v_resultadoConsulta.elementAt(0) == null )
            TBPL_Publicar(null,null);
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
                SaldoContrato_AVA = Double.parseDouble(Saldos400[0]);
                SaldoContrato_AVE = Double.parseDouble(Saldos400[1]);
            
                if (!resultadoSaldoUnidades_AVA.elementAt(0).toString().equals("No hay elementos") && resultadoSaldoUnidades_AVA.elementAt(0) != null 
                    && !resultadoSaldoUnidades_AVA.elementAt(0).toString().equals(" ") && SaldoContrato_AVA>0) {
                    SaldoUnidades_AVA = Double.parseDouble(resultadoSaldoUnidades_AVA.elementAt(0).toString());             
                    
                    Consulta = "SELECT to_char(APO_FECHA_EFECTIVA, 'yyyy-mm-dd'), \n" +
                            "APO_SALDO_CAPITAL Saldo_Capital, \n" +
                            "ROUND(((APO_SALDO_NUMERO_UNIDADES * " + SaldoContrato_AVA + " / " + SaldoUnidades_AVA +                            
                            ") -  APO_SALDO_CAPITAL),2)  Saldo_Rendimientos, \n" +
                            "APO_SALDO_CUENTA_CONTINGENTE Cuenta_Contingente, \n" +
                            "APO_APORTE_CERTIFICADO Certificado, \n" +
                            "APO_APORTE_BENEFICIO Exento, \n" +                    
                            "DECODE(NVL(APO_APO_CONSECUTIVO,0)||APO_APORTE_TRASLADO||APO_INFORMACION_TRASLADO,'0NN','N','S') Aporte_Traslado, \n" +
                            "tbfbd_tipoaporteoblig(APO_CONSECUTIVO) Concepto \n" +
                            "From TBAPORTES_OBLIG\n" +
                            "WHERE  APO_CON_PRO_CODIGO = '" + v_codproducto + "' \n" +
                            "       AND apo_con_numero = '" + v_Nocontrato + "' \n" +                    
                            "       AND APO_REF_ESTADO = 'SEA001' \n" +
                            "       AND apo_informacion_traslado ='N' \n" +
                            "       AND apo_saldo_numero_unidades > 0 \n" +
                            "       AND TBFBD_TipoAporteOblig(APO_CONSECUTIVO) = 'N' \n" +
                            "ORDER BY apo_fecha_aporte ASC";                
                    
                    v_resultadoDetalle_AVA = v_Consulta.TBFL_Consulta(Consulta);
                }
                
                if (!resultadoSaldoUnidades_AVE.elementAt(0).toString().equals("No hay elementos") && resultadoSaldoUnidades_AVE.elementAt(0) != null
                    && !resultadoSaldoUnidades_AVE.elementAt(0).toString().equals(" ") && SaldoContrato_AVE>0) {
                    SaldoUnidades_AVE = Double.parseDouble(resultadoSaldoUnidades_AVE.elementAt(0).toString());             
                    
                    Consulta = "SELECT to_char(APO_FECHA_EFECTIVA, 'yyyy-mm-dd'), \n" +
                            "APO_SALDO_CAPITAL Saldo_Capital, \n" +
                            "ROUND(((APO_SALDO_NUMERO_UNIDADES * " + SaldoContrato_AVE + " / " + SaldoUnidades_AVE +
                            ") -  APO_SALDO_CAPITAL),2)  Saldo_Rendimientos, \n" +
                            "APO_SALDO_CUENTA_CONTINGENTE Cuenta_Contingente, \n" +
                            "APO_APORTE_CERTIFICADO Certificado, \n" +
                            "APO_APORTE_BENEFICIO Exento, \n" +                    
                            "DECODE(NVL(APO_APO_CONSECUTIVO,0)||APO_APORTE_TRASLADO||APO_INFORMACION_TRASLADO,'0NN','N','S') Aporte_Traslado, \n" +
                            "tbfbd_tipoaporteoblig(APO_CONSECUTIVO) Concepto \n" +
                            "From TBAPORTES_OBLIG\n" +
                            "WHERE  APO_CON_PRO_CODIGO = '" + v_codproducto + "' \n" +
                            "       AND apo_con_numero = '" + v_Nocontrato + "' \n" +                    
                            "       AND APO_REF_ESTADO = 'SEA001' \n" +
                            "       AND apo_informacion_traslado ='N' \n" +
                            "       AND apo_saldo_numero_unidades > 0 \n" +
                            "       AND TBFBD_TipoAporteOblig(APO_CONSECUTIVO) = 'S' \n" +
                            "ORDER BY apo_fecha_aporte ASC";                
                    
                    v_resultadoDetalle_AVE = v_Consulta.TBFL_Consulta(Consulta);
                }
                

            TBPL_Publicar(v_resultadoDetalle_AVA,v_resultadoDetalle_AVE); //Publicación de la pagina en código HTML
            }
            else {
                TBPL_Publicar(null,null);
            }
                
        }
        v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }

      private void TBPL_Publicar(Vector v_descripcion_AVA, Vector v_descripcion_AVE)
      {                
        String concepto = "";
        if (v_descripcion_AVA!=null || v_descripcion_AVE!=null)
          {
          if ((v_descripcion_AVA!=null && v_descripcion_AVA.elementAt(0) != null && !v_descripcion_AVA.elementAt(0).toString().equals("No hay elementos"))
              || (v_descripcion_AVE!=null && v_descripcion_AVE.elementAt(0) != null && !v_descripcion_AVE.elementAt(0).toString().equals("No hay elementos")))
            {
            if (v_descripcion_AVA!=null) {
                v_descripcion_AVA.trimToSize();
                for (int i = 0; i<v_descripcion_AVA.size(); i++)
                   if (v_descripcion_AVA.elementAt(i).toString().equals("null"))
                     v_descripcion_AVA.setElementAt(" ", i);
            }
            if (v_descripcion_AVE!=null) {
                v_descripcion_AVE.trimToSize();
                for (int i = 0; i<v_descripcion_AVE.size(); i++)
                   if (v_descripcion_AVE.elementAt(i).toString().equals("null"))
                     v_descripcion_AVE.setElementAt(" ", i);
            }
                out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Consulta Detalle de Aportes","Consulta Detalle de Aportes", "", "", true));
                out.println("<BR>&nbsp;&nbsp;<BR>");            
                out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=8 rules=all width=580>");            
                out.println("<TR align=middle class=\"td11\" borderColor=silver><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha</B></font></TD>");            
                out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Saldo Capital</B></font></TD>");
                out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Saldo Rendimientos</B></font></TD>");
                out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Cuenta contingente</B></font></TD>");                        
                out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Certificado</B></font></TD>");
                out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Exento</B></font></TD>");
                out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Aporte Traslado</B></font></TD>");
                out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Concepto</B></font></TD></TR>");
    
                if (v_descripcion_AVA!=null && !v_descripcion_AVA.elementAt(0).toString().equals("No hay elementos"))
                for (int i=0; i<v_descripcion_AVA.size(); i+=8)
                   {   
                     concepto = v_descripcion_AVA.elementAt(i+7).equals("S")?"AVE":"AVA";
                     out.println("<TR bgColor=white borderColor=silver>");                 
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion_AVA.elementAt(i).toString()+"</font>\n</TD>");                 
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion_AVA.elementAt(i+1).toString()))+"</font>\n</TD>");
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion_AVA.elementAt(i+2).toString()))+"</font>\n</TD>");
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion_AVA.elementAt(i+3).toString()))+"</font></TD>");                                
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion_AVA.elementAt(i+4).toString()+"</font></TD>");
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion_AVA.elementAt(i+5).toString()+"</font></TD>");
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion_AVA.elementAt(i+6).toString()+"</font></TD>");                 
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>AVA</font></TD></TR>");                
                   }
                if (v_descripcion_AVE!=null && !v_descripcion_AVE.elementAt(0).toString().equals("No hay elementos"))
                for (int i=0; i<v_descripcion_AVE.size(); i+=8)
                   {   
                     concepto = v_descripcion_AVE.elementAt(i+7).equals("S")?"AVE":"AVA";  
                     out.println("<TR bgColor=white borderColor=silver>"); 
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion_AVE.elementAt(i).toString()+"</font>\n</TD>");                 
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion_AVE.elementAt(i+1).toString()))+"</font>\n</TD>");
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion_AVE.elementAt(i+2).toString()))+"</font>\n</TD>");
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion_AVE.elementAt(i+3).toString()))+"</font></TD>");                                
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion_AVE.elementAt(i+4).toString()+"</font></TD>");
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion_AVE.elementAt(i+5).toString()+"</font></TD>");
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion_AVE.elementAt(i+6).toString()+"</font></TD>");                 
                     out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>AVE</font></TD></TR>");                
                   }
            out.println("</TABLE>");        
            }      
          }
          else
            {        
                out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA_NEW("Consulta de aportes","Consulta de Aportes", "", "No hay Elementos", false));                
            }
        
          out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");  
          out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");            
          out.close();
      }        
}

