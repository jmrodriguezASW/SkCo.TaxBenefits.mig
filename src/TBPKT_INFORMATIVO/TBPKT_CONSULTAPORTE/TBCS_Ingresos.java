package TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE;//Nombre del paquete

//importa los paquetes que utilizaremos en esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTMLII;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
													  


public class TBCS_Ingresos extends HttpServlet{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
private String v_nuevaCadena ="";
HttpSession session  = null;


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
       out = new PrintWriter (response.getOutputStream());
       session = request.getSession(true);
       if(session==null)
        session = request.getSession(true);
       session.setMaxInactiveInterval(3600);
       response.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
       //response.setHeader("Pragma", "No-Cache");
       //response.setDateHeader("Expires", 0);
       String parametros[] = new String[8];
       String  cadena = request.getParameter("cadena");
       v_nuevaCadena = cadena;
       String ip_tax = request.getRemoteAddr();
        
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
       parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}
    v_Consulta = new TBCL_Consulta();
    String Consulta;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadoConsulta, resultadoConsulta1;

    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


    //Toma el código del producto
    String v_codpro = "MFUND";
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
       v_codpro = request.getParameter("v_codpro");
       nocon = request.getParameter("nocon");
       mesini = request.getParameter("mesini");
       anoini = request.getParameter("anoini");
       mesfin = request.getParameter("mesfin");
       anofin = request.getParameter("anofin");


       }
    catch (Exception e) { e.printStackTrace(); }

    /* HIA 2012-03-13 Se agrega la tabla tbcomision_aporte al select para obtener el valor de la comisión sobre el aporte(cma_valor)*/
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
													  
														  
          "             ,nvl(cma_valor,0) cma_valor \n"+
          "          FROM tbaportes,tbcomision_aporte\n"+
          "         WHERE apo_consecutivo = cma_apo_consecutivo(+)\n"+
          "           AND apo_fecha_efectiva BETWEEN to_date('"+mesini+" "+anoini+"', 'MM-YYYY','nls_date_language=ENGLISH') AND  LAST_DAY(to_date('01-"+mesfin+"-"+anofin+"', 'DD-MM-YYYY','nls_date_language=ENGLISH')) \n"+
          "           AND apo_con_pro_codigo = '"+v_codpro+"' \n"+
          "           AND apo_con_numero = '"+nocon+"'"+
          "           AND APO_REF_ESTADO = 'SEA001' "+
          "      ORDER BY apo_fecha_efectiva";

    v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);    
	   
												  
	
    v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    TBPL_Publicar(v_resultadoConsulta, v_codpro, nocon);
  }



      private void TBPL_Publicar(Vector v_descripcion, String v_codpro, String nocon)
      {
      v_descripcion.trimToSize();
      String aporte = "-";
						   
      /* HIA 2012-03-13 Se muetra el valor de la comisión sobre el aporte */
      double vrComision =0;
      boolean existeVrComision= false;
      int indiceComision =1;

      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        {
        for (int i = 0; i<v_descripcion.size(); i++)
        {    if (v_descripcion.elementAt(i).toString().equals("null"))
                v_descripcion.setElementAt(" ", i);
             if ((indiceComision*10 + indiceComision - 1) == i)
             {
                //out.println(v_descripcion.elementAt(i).toString());
                indiceComision ++;
                if (Double.valueOf(v_descripcion.elementAt(i).toString()).doubleValue() != 0)
                    existeVrComision = true;
             }
        }    

        out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE.TBCS_InformativoAporte", "", true));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=8 rules=all>");
        out.println("<TR align=middle class=\"td11\" borderColor=silver><TD width=\"5%\"><FONT color=white face=verdana size=1>&nbsp;</font></TD>");
        out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha efectiva</B></font></TD>");
        out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha proceso</B></font></TD>");
        out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Capital</B></font>");
        out.println("</TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Rendimientos</B></font></TD>");
        out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Cuenta contingente</B></font></TD>");
        /* HIA 2012-03-13 Se muetra el valor de la comisión sobre el aporte */
        if (existeVrComision)
        { 
          out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Comisión sobre aportes</B></font></TD>");
        }
        out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Aporte</B></font></TD>");
        out.println("<TD width=\"22%\"><FONT color=white face=verdana size=1><B>Certificado</B></font></TD></TR>");
		
																 
																														 
																											   
		 
							 

        for (int i=0; i<v_descripcion.size(); i+=11)
           {
            /* HIA 2012-03-13 Se muestra el valor de la comisión sobre el aporte */
             vrComision = Double.valueOf(v_descripcion.elementAt(i+10).toString()).doubleValue();
             if (vrComision < 0)
             {
                vrComision = vrComision * -1;
             }
           
           if (i<11)
             {
             //out.println("hola" + v_descripcion.elementAt(i+6).toString() + " - " +  v_descripcion.elementAt(i+7).toString() + " - " + v_descripcion.elementAt(i+9).toString());
             out.println("<TR bgColor=white borderColor=silver>");
             out.println("<TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=consecutivo VALUE='"+v_descripcion.elementAt(i).toString()+"' CHECKED></font>\n</TD>");
             out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+1).toString()+"<INPUT TYPE=hidden NAME=fechaefectiva VALUE='"+v_descripcion.elementAt(i+1).toString()+"'></font>\n</TD>");
             out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+2).toString());
             out.println("</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+3).toString()))+"</font>\n</TD>");
             out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+4).toString()))+"</font>\n</TD>");
             out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+5).toString()))+"</font></TD>");
             /* HIA 2012-03-13 Se muetra el valor de la comisión sobre el aporte */
             if (existeVrComision)
             {
                  out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(vrComision)+"</font></TD>");
             }
             if (v_descripcion.elementAt(i+6).toString().equalsIgnoreCase("S")&&v_descripcion.elementAt(i+7).toString().equalsIgnoreCase("S"))
               aporte="P";
             else if(v_descripcion.elementAt(i+6).toString().equalsIgnoreCase("N") && !v_descripcion.elementAt(i+9).toString().trim().equals(""))
               aporte="H";
             else if(v_descripcion.elementAt(i+6).toString().equalsIgnoreCase("N")&&v_descripcion.elementAt(i+7).toString().equalsIgnoreCase("S") && v_descripcion.elementAt(i+9).toString().trim().equals("") )
               aporte="PDTE";
             else if(v_descripcion.elementAt(i+6).toString().equalsIgnoreCase("N")&&v_descripcion.elementAt(i+7).toString().equalsIgnoreCase("N") && v_descripcion.elementAt(i+9).toString().trim().equals(""))
               aporte="-";
             out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+aporte+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+8).toString()+"</font></TD></TR>");
             }
           else
             {
             //out.println("bye" + v_descripcion.elementAt(i+6).toString() + " - " +  v_descripcion.elementAt(i+7).toString() + " - " + v_descripcion.elementAt(i+9).toString());
             out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=consecutivo VALUE='"+v_descripcion.elementAt(i).toString()+"'></font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+1).toString()+"</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+2).toString());
             /* HIA 2012-03-13 Se muetra el valor de la comisión sobre el aporte */
             
             out.println("</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+3).toString()))+"</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+4).toString()))+"</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+5).toString()))+"</font></TD>");
             if (existeVrComision)
             { 
                out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(vrComision)+"</font></TD>");
             }
             if (v_descripcion.elementAt(i+6).toString().equalsIgnoreCase("S")&&v_descripcion.elementAt(i+7).toString().equalsIgnoreCase("S"))
               aporte="P";
             else if(v_descripcion.elementAt(i+6).toString().equalsIgnoreCase("N") && !v_descripcion.elementAt(i+9).toString().trim().equals(""))
               aporte="H";
             else if(v_descripcion.elementAt(i+6).toString().equalsIgnoreCase("N")&&v_descripcion.elementAt(i+7).toString().equalsIgnoreCase("S") && v_descripcion.elementAt(i+9).toString().trim().equals("") )
               aporte="PDTE";
             else if(v_descripcion.elementAt(i+6).toString().equalsIgnoreCase("N")&&v_descripcion.elementAt(i+7).toString().equalsIgnoreCase("N") && v_descripcion.elementAt(i+9).toString().trim().equals(""))
               aporte="-";
             out.println("<TD width=\"22%\"><FONT color=black face=verdana size=1>"+aporte+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+8).toString()+"</font></TD></TR>");
             }
			
																	
																				
																																			   
																											   
           }
								   
			
					   
		
        out.println("</TABLE>");
        out.println("<INPUT TYPE=hidden NAME=v_codigo VALUE='"+v_codpro+"'>");
        out.println("<INPUT TYPE=hidden NAME=v_Nocontrato VALUE='"+nocon+"'>");
        out.println("<BLOCKQUOTE>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
        out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='composicion' CHECKED>Composición actual del aporte<BR>");
        out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='retiros'>Retiros que han afectado al aporte<BR>");
        out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='historia'>Historia del aporte<BR>");
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





  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONSULTAPORTE.TBCS_Ingresos Information";
  }
}

