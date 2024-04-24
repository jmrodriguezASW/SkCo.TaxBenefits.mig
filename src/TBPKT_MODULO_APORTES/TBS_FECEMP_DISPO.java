
package TBPKT_MODULO_APORTES;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import javax.servlet.http.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import javax.servlet.*;
import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.*;
import java.sql.*;
import java.io.*;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;

import co.oldmutual.taxbenefit.util.DataSourceWrapper; // INT20131108

/** 
*Este servlet servirá para actualizar la disponibilidad de contratos asociados a una empresa
*/

public class TBS_FECEMP_DISPO extends HttpServlet
{
   int v_historia = -1;
   //---------------------------------------------------------------------------------------------------------------
   public void init(ServletConfig config) throws ServletException
    {super.init(config);}
   //----------------------------------------------------------------------------------------------------------------
   public void doPost(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException
   {
       //INT20131108
       String rutaFisica = TBCL_REFERENCIAS.TBCL_CONSULTA("SMM003");
       //rutaFisica  =  "c:\\Taxbenefits\\Taxbenefits\\public_html\\WEB-INF\\";
       String POLICY_FILE_LOCATION = rutaFisica+"antisamy-tinymce-1.4.4.xml"; // Path to policy file
       AntiSamy as = new AntiSamy(); // INT20131108
      PrintWriter out = new PrintWriter (response.getOutputStream());
      STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();
       Connection conn    =   null;
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
         conn   =   DataSourceWrapper.getInstance().getConnection();
         
         String v_empresa = request.getParameter("obligatoriov_empresa");
          try
          {
              CleanResults cr = as.scan(request.getParameter("obligatoriov_empresa"), new File(POLICY_FILE_LOCATION));
              v_empresa = cr.getCleanHTML();
          }
          catch (Exception e)
          {
            e.printStackTrace();
          } //INT20131108
         String vconsulta = " SELECT  EMP_GRUPO, EMP_DESCRIPCION  "+
                            "   FROM tbempresas  ";
         if(!v_empresa.trim().equals("") || v_empresa != null)
                vconsulta +=  "  WHERE upper(EMP_DESCRIPCION) like UPPER('%"+v_empresa+"%')";//
         Statement t_st = conn.createStatement();
         ResultSet t_rs8i_2;
         t_rs8i_2 = t_st.executeQuery(vconsulta);
         String vdesemp = "";
         boolean vencontro = false;
         while (t_rs8i_2.next())
         {
            vencontro = true;
            vdesemp += " <option value='"+t_rs8i_2.getString(1)+"'>"+ t_rs8i_2.getString(2)+" ";
         }
         if(vencontro)
         {
            /**Dibbujar pagina de respuesta*/
            v_pintar=    i_pagina.TBFL_CABEZA("Actualizar Disponibilidad Aporte por Empresa","Actualizar Disponibilidad Aporte por Empresa","TBPKT_MODULO_APORTES.TBS_DIPONIBILIDAD_EMPRESA","",true,"moduloparametro.js","return checkrequired(this)");
            out.println(""+v_pintar+"");
            out.println("<br>");
            out.println("<pre>");
            out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><b>Empresa</b>       <select name=obligatoriov_empresa></font>");
            //out.println("               <option VALUE =''>                         ");
            out.println(""+vdesemp+"");
            out.println("</OPTION></SELECT></FONT>");
            out.println("<p><FONT color=black face=verdana size=1>*Fecha:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>");
            out.println("<INPUT NAME='obligatorio_fecha_x' TYPE=text MAXLENGTH=10 SIZE=12' onchange='checkdate(this)'> <FONT color=black face=verdana size=1> YYYY-MM-DD</font></p>");
            out.println("<p><FONT color=black face=verdana size=1>*Porcentaje: </font>");
            out.println("<input type='text' name='obligatorio_porcentaje_x'  MAXLENGTH=6 SIZE=6 onchange='if (esNumero(this)==1) return false;checkDecimals(this,this.value,2),esMayor(this)'></p>");
            out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
            out.println("<center><input type=submit value='Aceptar'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
            String v_pie = i_pagina.TBFL_PIE;
            out.println(""+v_pie+"");
            out.println("<br>");
            out.close();
         }
         else
         {
            out.println(i_pagina.TBFL_CABEZA("Actualizar Disponibilidad Aporte por Empresa","Actualizar Disponibilidad Aportes por Empresa",
            "","No se encontraron empresas con esa condición de busqueda.",false));
            out.println("<BR><BR>");
            out.println("<center><input type='button' value='Aceptar'Onclick=history.go(-2);><input type='button' value='Regresar' Onclick=history.go(-1);></center>");
            out.println(i_pagina.TBFL_PIE);
            out.close();

         }
          t_rs8i_2.close();
          t_st.close();

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
       finally{
               try{
                       DataSourceWrapper.closeConnection(conn);                  
               }catch(SQLException sqlEx){
                       System.out.println(sqlEx.toString());
               }
       }
   }
}
