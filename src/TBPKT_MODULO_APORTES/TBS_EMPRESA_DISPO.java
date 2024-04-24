
package TBPKT_MODULO_APORTES;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import javax.servlet.http.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import javax.servlet.*;
import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.*;
import java.sql.*;
import java.io.*;
 
/**
*Este servlet servirá para actualizar la disponibilidad de contratos asociados a una empresa
*/

public class TBS_EMPRESA_DISPO extends HttpServlet
{
   int v_historia = -1;
   //---------------------------------------------------------------------------------------------------------------
   public void init(ServletConfig config) throws ServletException
    {super.init(config);}
   //----------------------------------------------------------------------------------------------------------------
   public void doPost(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException
   {
      PrintWriter out = new PrintWriter (response.getOutputStream());
      STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();
      try
      {
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

         String v_pintar = "";
         /**Dibbujar pagina de respuesta*/
         v_pintar=    i_pagina.TBFL_CABEZA("Actualizar Disponibilidad Aporte por Empresa","Actualizar Disponibilidad Aporte por Empresa","TBPKT_MODULO_APORTES.TBS_FECEMP_DISPO","",true,"","");
         out.println(""+v_pintar+"");
         out.println("<br>");
         out.println("<pre>");
         out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><center><b>Nombre Empresa        </b>  <input name=obligatoriov_empresa type=text  value='' size = '30' maxlength = '100' > </center></font>");
         out.println("</pre>");
         out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
         out.println("<center><input type=submit value='Aceptar'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
         String v_pie = i_pagina.TBFL_PIE;
         out.println(""+v_pie+"");
         out.println("<br>");
         out.close();

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
