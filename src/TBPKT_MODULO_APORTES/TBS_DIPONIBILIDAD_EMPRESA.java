
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

public class TBS_DIPONIBILIDAD_EMPRESA extends HttpServlet
{
   public void doPost(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException
   {
       //INT20131108
       String rutaFisica = TBCL_REFERENCIAS.TBCL_CONSULTA("SMM003");
       //rutaFisica  =  "c:\\Taxbenefits\\Taxbenefits\\public_html\\WEB-INF\\";
       String POLICY_FILE_LOCATION = rutaFisica+"antisamy-tinymce-1.4.4.xml"; // Path to policy file
       AntiSamy as = new AntiSamy(); // INT20131108
      PrintWriter salida = new PrintWriter (response.getOutputStream());
      STBCL_GenerarBaseHTMLII i_pagina = new STBCL_GenerarBaseHTMLII();
      TBS_ACTUALIZA_DISPONIBILIDAD  iapo = new  TBS_ACTUALIZA_DISPONIBILIDAD();
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
         parametros = Seguridad.TBFL_Seguridad(cadena, salida, ip_tax);
         conn =   DataSourceWrapper.getInstance().getConnection();
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
          
         String vconsulta = " SELECT  TO_CHAR(APO_FECHA_EFECTIVA,'RRRR-MM-DD') "+
                            "        ,APO_CONSECUTIVO  "+
                            "        ,APO_CON_PRO_CODIGO     "+
                            "        ,APO_CON_NUMERO  "+
                            "  FROM   TBAPORTES    ,TBDISPONIBILIDADES_APORTES    ,TBREFERENCIAS "+
                            "         ,TBCONTRATOS  "+
                            " WHERE  APO_CON_PRO_CODIGO        = DIA_APO_CON_PRO_CODIGO  "+
                            " AND APO_CON_NUMERO        = DIA_APO_CON_NUMERO "+
                            " AND APO_CONSECUTIVO       = DIA_APO_CONSECUTIVO "+
                            " AND APO_CON_PRO_CODIGO    = CON_PRO_CODIGO "+
                            " AND APO_CON_NUMERO        = CON_NUMERO "+
		                        "AND DIA_FECHA             = (SELECT MAX(DIA_FECHA) "+
                                                         "FROM TBDISPONIBILIDADES_APORTES "+
                                                         "WHERE  DIA_APO_CON_PRO_CODIGO = APO_CON_PRO_CODIGO  "+
                                                         "AND DIA_APO_CON_NUMERO        = APO_CON_NUMERO  "+
				                                                 "AND DIA_APO_CONSECUTIVO       = APO_CONSECUTIVO) "+

                            " AND APO_REF_CONDICION_SKA = REF_CODIGO (+)  "+
                            " AND CON_FECHA_CANCELACION IS NULL  "+
                            " AND (( APO_APORTE_TRASLADO ='S' AND APO_INFORMACION_TRASLADO = 'N') OR ( APO_APORTE_TRASLADO ='N')) "+
                            " AND APO_EMP_GRUPO = '"+v_empresa+"' "+
                            " ORDER BY APO_FECHA_EFECTIVA DESC  ";
         //System.out.println(vconsulta);
         Statement t_st = conn.createStatement();
         ResultSet t_rs8i_2;
         t_rs8i_2 = t_st.executeQuery(vconsulta);

         double consecutivo = 0;
         String   f_e   = "";
         String    vproducto = "";
         String    vcontrato = "";
         String   dia_fecha         = request.getParameter("obligatorio_fecha_x");
         String   portmp1           = request.getParameter("obligatorio_porcentaje_x");
         Double   portmp2           = new Double(portmp1);
         double   porcentaje        = portmp2.doubleValue();
         String  msj  = "No se encontraron aportes asociadoa a la empresa.";
         String select8i_fecha1 = "SELECT '*' FROM DUAL WHERE TO_DATE(?,'RRRR-MM-DD')>=TO_DATE(?,'RRRR-MM-DD')";
         String select8i_fecha2 = "SELECT '*' FROM DUAL WHERE TO_DATE(?,'RRRR-MM-DD')<=SYSDATE ";

         while (t_rs8i_2.next())
         {

           //capturo datos sobre vables mias , enviados por el proc.
           //Problema con el getInt, cuando el consecutivo es muy grande el getInt() no aguanta
//           consecutivo      = t_rs8i_2.getInt(2);
           consecutivo      = t_rs8i_2.getDouble(2);
           f_e              = t_rs8i_2.getString(1);                 //fecha_efectiva
           v_producto       = t_rs8i_2.getString(3);
           v_contrato       = t_rs8i_2.getString(4);
           msj = iapo.TBCL_MANEJAR_APORTES(dia_fecha, f_e,v_producto  ,v_contrato ,
                                      consecutivo  ,porcentaje,portmp1,
                                      select8i_fecha1 , select8i_fecha2,
                                     conn, salida);
           if(!msj.trim().equals("LA ACTUALIZACION FUE EXITOSA"))
           {
              conn.rollback();
             salida.println(i_pagina.TBFL_CABEZA("Actualizar disponibilidad aporte por empresa","Actualizar disponibilidad aportes por empresa",
             "",msj,false));
             salida.println("<BR><BR>");
             salida.println("<center><input type='button' value='Aceptar'Onclick=history.go(-3);><input type='button' value='Regresar' Onclick=history.go(-2);></center>");
             salida.println(i_pagina.TBFL_PIE);
             salida.flush();
             return;
           }
         }


         t_rs8i_2.close();
         t_st.close();
         conn.commit();
         salida.println(i_pagina.TBFL_CABEZA("Actualizar disponibilidad aporte por empresa","Actualizar disponibilidad aportes por empresa",
         "",msj,false));
         salida.println("<BR><BR>");
         salida.println("<center><input type='button' value='Aceptar'Onclick=history.go(-3);><input type='button' value='Regresar' Onclick=history.go(-2);></center>");
         salida.println(i_pagina.TBFL_PIE);
         salida.flush();
         return;


      }
      catch(Exception ex)
      {
         String v_pintar="";
         String error = ex.toString();
         if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
         {
           v_pintar=    i_pagina.TBFL_CABEZA("Actualizar disponibilidad aportes por empresa","Error Actualizar disponibilidad aportes por empresa","","<center>No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.</center>",false);
         }
         else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
              {
                 v_pintar=    i_pagina.TBFL_CABEZA("Actualizar disponibilidad aportes por empresa","Error Actualizar disponibilidad aportes por empresa","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
              }
              else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                   {
                     v_pintar = i_pagina.TBFL_CABEZA("Actualizar disponibilidad aportes por empresa","Error Actualizar disponibilidad aportes por empresa","","<center>Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.</center>",false);
                   }
                   else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                        {
                           v_pintar =  i_pagina.TBFL_CABEZA("Actualizar disponibilidad aportes por empresa","Error Actualizar disponibilidad aportes por empresa","","<center>Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.</center>",false);
                        }
                        else
                        {
                           v_pintar=    i_pagina.TBFL_CABEZA("Actualizar disponibilidad aportes por empresa","Error Actualizar disponibilidad aportes por empresa","","<center>Mensaje de Error :"+ex+".</center>",false);
                        }
         salida.println(""+v_pintar+"");
         salida.println("<BR>");
         salida.println("<center><input type=button value='Cancelar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
         String v_pie = i_pagina.TBFL_PIE;
         salida.println("<br>");
         salida.println(""+v_pie+"");
         salida.close();      
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




