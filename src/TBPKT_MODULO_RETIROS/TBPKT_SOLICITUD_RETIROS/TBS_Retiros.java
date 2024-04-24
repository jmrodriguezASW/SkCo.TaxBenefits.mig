package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBS_Retiros extends HttpServlet
{

 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  {
      //INT20131108
      String rutaFisica = TBCL_REFERENCIAS.TBCL_CONSULTA("SMM003");
      //rutaFisica  =  "c:\\Taxbenefits\\Taxbenefits\\public_html\\WEB-INF\\";
      String POLICY_FILE_LOCATION = rutaFisica+"antisamy-tinymce-1.4.4.xml"; // Path to policy file
      AntiSamy as = new AntiSamy(); // INT20131108
    PrintWriter out = new PrintWriter (response.getOutputStream());
    STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();
    try
    {
      HttpSession session = request.getSession(true);
      if(session==null)
          session = request.getSession(true);

      session.setMaxInactiveInterval(3600);
      String v_fecefe ="";
      String v_fecpro ="";

      response.setContentType("text/html");
      /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
      // response.setHeader("Pragma", "No-Cache");
      // response.setDateHeader("Expires", 0);

      String v_contrato = "", v_producto = "", v_usuario  = "", v_unidad = "";
      String v_tipousu = "", v_idworker = "";

      String parametros[] = new String[8];
      String  cadena = request.getParameter("cadena");
      String   nuevaCadena = cadena;
      String ip_tax = request.getRemoteAddr();
      TBCL_Seguridad Seguridad = new TBCL_Seguridad();
      parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
      v_contrato = parametros[0];
      v_producto = parametros[1];
      v_usuario  = parametros[2];
      v_unidad = parametros[3];
      v_tipousu = parametros[4];
      v_idworker = parametros[5];

        /*try
        {
          v_fecefe  = request.getParameter("obligatoriov_fecefectiva");
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }*/ //INT20131108
          
        try
        {
            CleanResults cr = as.scan(request.getParameter("obligatoriov_fecefectiva"), new File(POLICY_FILE_LOCATION));
            v_fecefe = cr.getCleanHTML();
            if (!session.getAttribute("s_producto").toString().equals("FPOB") && !session.getAttribute("s_producto").toString().equals("FPAL"))
            {
                CleanResults crfp = as.scan(request.getParameter("obligatoriov_fecproceso"), new File(POLICY_FILE_LOCATION));
                v_fecpro = crfp.getCleanHTML();
            }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        } //INT20131108
      
      session.removeAttribute("s_fecefectiva");
      session.setAttribute("s_fecefectiva",(java.lang.Object)v_fecefe );        
 
      if (session.getAttribute("s_producto").toString().equals("FPOB") || session.getAttribute("s_producto").toString().equals("FPAL")) {
              TBCL_RetiroOperativo_Oblig i_solicitud = new TBCL_RetiroOperativo_Oblig ();
              i_solicitud.TBPL_SolicitudOperativo_Oblig(out,session,request,nuevaCadena);
          }else
          {
            session.setAttribute("s_fecpro",(java.lang.Object)v_fecpro );
            TBCL_RetiroOperativo i_solicitud = new TBCL_RetiroOperativo ();
            i_solicitud.TBPL_SolicitudOperativo(out,session,request,nuevaCadena);
          }
    }
    catch(Exception ex)
    {            
       String v_pintar="";
      String error = ex.toString();
       if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
      {
        v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.</center>",false);
      }
      else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
          {
            v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
          }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_pintar = i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.</center>",false);
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_pintar =  i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.</center>",false);
                     }
                  else
                  {
                   v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error Solicitud de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                  }
       out.println(""+v_pintar+"");
       out.println("<BR>");
       out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
       String v_pie = i_pagina.TBFL_PIE;
       out.println("<br>");
       out.println(""+v_pie+"");
       out.close();
    }

  }
 }
 
 ////////////////MARCELA
 

  /*public void doGet (HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException
 {
                PrintWriter out = new PrintWriter (response.getOutputStream());
                // Get the user's session and shopping cart
                //HttpSession session = request.getSession(true);
                //out = response.getWriter();
                //out.println("YYYYYYYYYY");
                 out.println("<html>"); 
                 out.println("<head><title>Servlet Presentacion</title></head>"); 
                 out.println("<body>"); 
                 out.println("<p><h1>Servlet Presentacion !!</h1></p>"); 
                 String temp = TBPL_SaldoLiqPorContrato("000000021", "MFUND", out);
                 out.println("</body></html>"); 
                 out.close(); 
                //out.println("<html><body>NADA</body></html>");
                
 }*/


  
  
   /*static String TBPL_SaldoLiqPorContrato(String v_contrato, String v_producto, PrintWriter out){
       String saldoLiq ="1125.65";
        try{
            out.println("Entro 1.1");
            BasicHttpBinding_IAvailableServiceClient myPort = new BasicHttpBinding_IAvailableServiceClient();
            out.println("Entro 1.2");
            System.out.print("calling " + myPort.getEndpoint());
            out.println("Entro 1.3");
            out.println("calling " + myPort.getEndpoint());
            out.println("Entro 1.4");
            Saldo saldo = myPort.getAvailableBalance(v_contrato,v_producto,"E","10000");
            out.println("Entro 1.5");
            System.out.print("Salida:"+saldo.getSaldoDisponible().toString());
            saldoLiq = saldo.getSaldoDisponible().toString();
        }catch (Exception e){
            saldoLiq = "12.34";
           out.println("Entro 1.5");
           out.println(e.toString());
           System.out.println("Error TBPL_ValorAportesFondosCerradosTest"+e);
        }finally {
         return saldoLiq;
        }
    }*/
}

