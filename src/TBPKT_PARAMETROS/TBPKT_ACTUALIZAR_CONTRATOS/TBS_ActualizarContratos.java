
// Copyright (c) 2000 skandia
package TBPKT_PARAMETROS.TBPKT_ACTUALIZAR_CONTRATOS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import oracle.jdbc.*;
import java.sql.*;
import oracle.sql.*;
import oracle.jdbc.driver.*;
import java.lang.String.*;
import java.lang.Integer.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

/**Clase que actualiza información de contratos;
 *  1- Consultar si la información de contratos esta lista
 *  2- Verificar registros. Fucnión TB_FVERINF_CONTRATO
 *  3- COnsultar informacion en tabla temporal del as400
 *  4- Cargar a tablas tempoprales de taxbenefits
 *  5- Actualizar en tbcontratos y tbcontratos_condicion
 *  6- Dibukjar página de respuesta     */

public class TBS_ActualizarContratos extends HttpServlet
{
 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
   /**Instancias de clase*/
  /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase STBCL_GenerarBaseHTML, no es necesaria la instancia nueva*/ 
 //STBCL_GenerarBaseHTML i_pagina = new STBCL_GenerarBaseHTML;/**Instancia de la clase STBCL_GenerarBaseHTML*/
   
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;/**Instancia de la clase TBC_Seguridad*/
  
 
 //TBCL_Validacion TBCL_Validacion.= new TBCL_Validacion1();   

/**Instancia de la clase TBCL_Validaciond*/
  /**Variable tipo PrintWriter*/
  PrintWriter out = new PrintWriter (response.getOutputStream());/**Instancia de la clase STBCL_GenerarBaseHTML*/
  Connection t_tax =    null;
  try
  {
   /**Tomar session*/
   HttpSession session = request.getSession(true);
   if (session == null) session = request.getSession(true);
   response.setContentType("text/html");
   /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
   // response.setHeader("Pragma", "No-Cache");
   // response.setDateHeader("Expires", 0);
   /**Declaración de datos*/
   /**Tipo String*/
   String v_contrato = "";
   String v_producto = "";
   String v_usuario  = "";
   String v_unidad   = "";
   String v_tipousu  = "";
   String v_idworker = "";
   String v_control2 = "";
   String v_fecact = "";
   String v_indcon = "";
   String v_menerr = "";
   String v_menerr2 = "";
   String v_inscontrol = "";
   String parametros[] = new String[8];
   String  cadena = request.getParameter("cadena");
   String   nuevaCadena = cadena;
   String ip_tax = request.getRemoteAddr();
   /**Validar seguridad*/
   parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
   v_contrato = parametros[0];
   v_producto = parametros[1];
   v_usuario  = parametros[2];
   v_unidad = parametros[3];
   v_tipousu = parametros[4];
   v_idworker = parametros[5];
   /**Tipo int*/
   int v_contador =0;
   int v_conreg  = 0;
   int v_sumreg  = 0;
   int v_indicador = 0;
   int v_indicador2 = 0;
   /**Tipo boolean*/
   boolean v_encontro = true;
   boolean v_encontro2 = true;
   boolean v_encontro3 = true;
   /**Se averigua por el usuario de taxbenefits en el archivo conection.properties*/
   String[] v_valusu = new String[3];
   v_valusu=TBCL_Validacion.TBFL_ValidarUsuario();

   /**Conectar Base de datos*/
   Class.forName("oracle.jdbc.driver.OracleDriver");
   t_tax    =   DataSourceWrapper.getInstance().getConnection();
   Statement t_st = t_tax.createStatement();
   /**Consultar en tabla de control si la informacion  esta cargada*/
   String t_control2 = "SELECT KPGLDZ,KPVWSZ,KPVXSZ FROM AJKPCPP@mfund WHERE KPVVSZ='CT'";
   ResultSet t_res2 = t_st.executeQuery(t_control2);
   while( t_res2.next())
   {
    v_contador++;
    v_fecact = t_res2.getString(1);
    v_control2 = t_res2.getString(2);
    v_indcon =t_res2.getString(3);
   }
   t_res2.close();
   t_tax.commit();
  // String v_cerrar2 = " alter session close DATABASE LINK mfund";
  // t_st.execute(v_cerrar2);
   if(v_contador==0)
   {
    t_st.close();
    /**Si la tabla de control no tiene datos*/
    String v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Error al actualizar contratos","Error al actualizar contratos","","<center>No se encuentra disponible la información para la actualización de contratos.</center>",false);
    out.println(""+v_pintar+"");
    out.println("<br>");
    out.println("<br>");
    out.println("<center><input type=button value='Aceptar'  onclick='history.go(-2)' name ='aceptar'>");
    out.println("<input type=button value='Regresar' onclick='history.go(-1)' name='cancelar'></center>");
    String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
    out.println("<br>");
    out.println(""+v_pie+"");
    out.close();
   }
   /**Se tiene mas de un registro en la tabla de control*/
   if(v_contador == 1)
   {
    /**Si esta cargada la información se procede a insertar*/
    if(v_control2.equals("Y"))
    {
     int v_fecconv= new Integer (v_fecact).intValue();
     /**Insertar registro en tabl de control*/
     v_inscontrol = "INSERT INTO  AJKPCPP@mfund (KPGLDZ,KPVVSZ,KPMECX,KPQGNU,KPVWSZ,KPVXSZ)VALUES("+v_fecconv+",'CT','00099',2,'N',' ')";
     t_st.executeUpdate(v_inscontrol);
     t_tax.commit();
     String v_cerrar = " alter session close DATABASE LINK mfund";
     t_st.execute(v_cerrar);
     /**Verificar registro de control*/
     CallableStatement l_stmt0 = t_tax.prepareCall("{? = call TB_FVERINF_CONTRATO(?,?,?,?)}");
     l_stmt0.registerOutParameter(1,Types.INTEGER);
     l_stmt0.registerOutParameter(3,Types.INTEGER);
     l_stmt0.registerOutParameter(4,Types.INTEGER);
     l_stmt0.registerOutParameter(5,Types.VARCHAR);
     l_stmt0.setString(2,v_fecact);
     l_stmt0.setInt(3,v_conreg);
     l_stmt0.setInt(4,v_sumreg);
     l_stmt0.setString(5,v_menerr);
     l_stmt0.execute();
     v_indicador = l_stmt0.getInt(1);
     v_conreg = l_stmt0.getInt(3);
     v_sumreg = l_stmt0.getInt(4);
     v_menerr = l_stmt0.getString(5);
     l_stmt0.close();
     /**Si hubo error cuando se verificaban registros de control*/
     if(v_indicador == -1  )
     {
      t_st.close();
      t_tax.commit();
      String v_pintar2=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Error al actualizar contratos","Error al actualizar contratos","","<center>"+v_menerr+"</center>",false);
      out.println(""+v_pintar2+"");
      out.println("<br>");
      out.println("<br>");
      out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
      String v_pie2 = STBCL_GenerarBaseHTML.TBFL_PIE;
      out.println("<br>");
      out.println(""+v_pie2+"");
      out.close();
     }
     else
     {
      /**Llamar función de actualizar contratos*/
      CallableStatement l_stmt1 = t_tax.prepareCall("{? = call TB_FACTUALIZAR_CONTRATOS(?,?,?,?)}");
      l_stmt1.registerOutParameter(1,Types.INTEGER);
      l_stmt1.registerOutParameter(5,Types.VARCHAR);
      l_stmt1.setString(2,v_fecact);
      l_stmt1.setString(3,v_indcon);
      l_stmt1.setInt(4,v_sumreg);
      l_stmt1.setString(5,v_menerr);
      l_stmt1.execute();
      v_indicador2 = l_stmt1.getInt(1);
      v_menerr2 = l_stmt1.getString(5);
      l_stmt1.close();
      if(v_indicador2 == -1)
      {
       t_st.close();
       t_tax.commit();
       String v_pintar6=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Error al actualizar contratos","Error al actualizar contratos","","<center>"+v_menerr2+"</center>",false);
       out.println(""+v_pintar6+"");
       out.println("<br>");
       out.println("<br>");
       out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
       String v_pie6 = STBCL_GenerarBaseHTML.TBFL_PIE;
       out.println("<br>");
       out.println(""+v_pie6+"");
       out.close();
      }
      else
      {
       if(v_indcon.equals("Y"))
       {
        String v_pintar4=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Actualización de Contratos","Actualización de Contratos","","",false);
        out.println(""+v_pintar4+"");
        out.println("<br>");
        ResultSet t_rs2    = t_st.executeQuery(" SELECT  INL_CONSECUTIVO,INL_MENSAJE  FROM  TBINTERFACE_LOGS WHERE INL_INTERFACE ='CONTAX' AND INL_FECHA = TO_DATE("+v_fecact+",'RRRR-MM-DD') AND INL_PASO = 2 ");
        out.println("<center><table width='100%' border='1' cellspacing='2' cellpadding='2'>");
        out.println("<tr><td class=\"td11\" colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Contratos en Multifund  que no encontrados en Tax Benefits</b></font></center></font></td></tr>");
        out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Consecutivo</b></font></center></td>");
        out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>   Mensaje   </b></font></center></td></tr>");
        while(t_rs2.next())
        {
         v_encontro = false;
         out.println("<tr><td ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+t_rs2.getInt(1)+"</font></center></td>");
         out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><center>"+t_rs2.getString(2)+"</center></font></td></tr>");
        }
        if(v_encontro)
        {
         out.println("<tr><td ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></center></td>");
         out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><center>--</center></font></td></tr>");
        }
        out.println("</table></center>");
        out.println("<br>");
        t_rs2.close();
        ResultSet t_rs3   = t_st.executeQuery(" SELECT  INL_CONSECUTIVO,INL_MENSAJE  FROM  TBINTERFACE_LOGS WHERE  INL_INTERFACE ='CTTAX' AND INL_FECHA = TO_DATE("+v_fecact+",'RRRR-MM-DD') AND INL_PASO = 2 ");
        //mientras se encutren datos
        out.println("<center><table width='100%' border='1' cellspacing='2' cellpadding='2'>");
        out.println("<tr><td class=\"td11\" colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Contratos en  Tax Benefits que no encontrados en Multifund</b></font></center></font></td></tr>");
        out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Consecutivo</b></font></center></td>");
        out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>   Mensaje   </b></font></center></td></tr>");
        while(t_rs3.next())
        {
         v_encontro2=false;
         out.println("<tr><td ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+t_rs3.getInt(1)+"</font></center></td>");
         out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><center>"+t_rs3.getString(2)+"</center></font></td></tr>");
        }
        if(v_encontro2)
        {
         out.println("<tr><td ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></center></td>");
         out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><center>--</center></font></td></tr>");
        }
        t_rs3.close();
        t_st.close();
        out.println("</table></center>");
        out.println("<br>");
        out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
        out.println("<br>");
        String v_pie4 = STBCL_GenerarBaseHTML.TBFL_PIE;
        out.println("<br>");
        out.println(""+v_pie4+"");
        out.close();
        t_tax.commit();
       }
       else
       {
        String v_pintar5=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Actualización de Contratos","Actualización de Contratos","","",false);
        out.println(""+v_pintar5+"");
        out.println("<br>");
        ResultSet t_rs5    = t_st.executeQuery(" SELECT  INL_CONSECUTIVO,INL_MENSAJE  FROM  TBINTERFACE_LOGS WHERE   INL_INTERFACE ='CONTAX' AND INL_FECHA = TO_DATE("+v_fecact+",'RRRR-MM-DD') AND INL_PASO = 2 ");
        //mientras se encutren datos
        out.println("<center><table width='100%' border='1' cellspacing='2' cellpadding='2'>");
        out.println("<tr><td class=\"td11\" colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Contratos enviados en la interface que no se encontraron en Tax Benefots</b></font></center></font></td></tr>");
        out.println("<tr><td class=\"td11\" ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Consecutivo</b></font></center></td>");
        out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>   Mensaje   </b></font></center></td></tr>");
        while(t_rs5.next())
        {
         v_encontro3 = false;
         out.println("<tr><td ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>"+t_rs5.getInt(1)+"</font></center></td>");
         out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><center>"+t_rs5.getString(2)+"</center></font></td></tr>");
        }
        if(v_encontro3)
        {
         out.println("<tr><td ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>--</font></center></td>");
         out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><center>--</center></font></td></tr>");
        }
        out.println("</table></center>");
        out.println("<br>");
        t_rs5.close();
        t_st.close();
        out.println("<br>");
        out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
        out.println("<br>");
        String v_pie5 = STBCL_GenerarBaseHTML.TBFL_PIE;
        out.println("<br>");
        out.println(""+v_pie5+"");
        out.close();
        t_tax.commit();
       }
      }
     }
    }
    else
    {
     t_st.close();
     t_tax.rollback();

     String v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Error al actualizar contratos","Error al actualizar contratos","","<center>No se encuentra disponible la información para la actualización de contratos.</center>",false);
     out.println(""+v_pintar+"");
     out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'><input type=button  value='Regresar' onclick=' history.go(-1)'></center>");
     String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
     out.println("<br>");
     out.println("<br>");
     out.println(""+v_pie+"");
     out.close();
    }
   }
   else
   {
    /**Página de respuesta - la información aun no esta lista*/
    t_st.close();
    t_tax.rollback();
    String v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Error al actualizar contratos","Error al actualizar contratos","","<center>La información de contratos presenta problemas.</center>",false);
    out.println(""+v_pintar+"");
    out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'><input type=button  value='Regresar' onclick=' history.go(-1)'></center>");
    String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
    out.println("<br>");
    out.println("<br>");
    out.println(""+v_pie+"");
    out.close();

   }
  }
  catch(Exception ex)
  {/**Capturar errorr*/
   String v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Error al actualizar contratos","Error al actualizar contratos",""," Mensaje de error:  '"+ex+"'", false);
   out.println(""+v_pintar+"");
   out.println("<br>");
   out.println("<br>");
   out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
   String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
   out.println("<br>");
   out.println(""+v_pie+"");
   out.close();
  }
     finally{
             try{
                     DataSourceWrapper.closeConnection(t_tax);                  
             }catch(SQLException sqlEx){
                     System.out.println(sqlEx.toString());
             }
     }  
 }
}

