package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;

/*
Modificacion:
Se elimina el import debido a que la conexion
al AS400 se hace desde la base de datos

import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;
*/

import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;
//import PKT_ACTUALIZA_APORTES.*;


public class TBS_CalcularRetiro2 extends HttpServlet implements SingleThreadModel
{
 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
  PrintWriter out = new PrintWriter (response.getOutputStream());
  STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();
  Connection t_tax    =   null;
  
  try
  {
   HttpSession session = request.getSession(true);
   if(session==null)
    session = request.getSession(true);
   session.setMaxInactiveInterval(3600);
   response.setContentType("text/html");
   /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
   // response.setHeader("Pragma", "No-Cache");
   // response.setDateHeader("Expires", 0);

   String v_contrato = "", v_producto = "", v_usuario  = "", v_unidad = "";
   String v_tipousu = "", v_idworker = "";
   String v_idsaro  = "";/**Variable id del evento de saro*/
   String   nuevaCadena = "";
   String parametros[] = new String[8];
   String  cadena = request.getParameter("cadena");
   nuevaCadena = cadena;
   String ip_tax = request.getRemoteAddr();
   TBCL_Seguridad Seguridad = new TBCL_Seguridad();
   parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
   v_contrato = parametros[0];
   v_producto = parametros[1];
   v_usuario  = parametros[2];
   v_unidad = parametros[3];
   v_tipousu = parametros[4];
   v_idworker = parametros[5];

/*
Modificacion:
Se debe reemplazar la creacion del objeto
TBCL_FuncionesAs400 ya que se sustituye por un llamado a
procedimiento de la base de datos

   TBCL_FuncionesAs400 i_fondos = new TBCL_FuncionesAs400();
*/

   //se hace conexion a taxbenefit
   t_tax    =   DataSourceWrapper.getInstance().getConnection();
   
   if((java.lang.String)session.getValue("s_valuni") != null)
   {
    if((java.lang.String)session.getValue("s_contrato") != null || (java.lang.String)session.getValue("s_producto") != null)
    {
     String v_pro = (java.lang.String)session.getValue("s_producto");
     String v_contra = (java.lang.String)session.getValue("s_contrato");
     
     /**Modificado 2009/10/27 Variable Id Saro*/
     try{
       if((java.lang.String)session.getValue("v_idsaro")!= null){
         v_idsaro = (java.lang.String)session.getValue("v_idsaro");
         session.removeValue("v_idsaro");
         session.putValue("v_idsaro",v_idsaro);
       }
     }catch (Exception e)  { e.printStackTrace(); }
     
     double v_conret = 0;
     String v_fecpro =(java.lang.String)session.getValue("s_fecpro");
     //Date v_fecpro2 = v_fecpro2.valueOf(v_fecpro);
     java.sql.Date v_fecpro2 = new java.sql.Date(4);
     v_fecpro2 = java.sql.Date.valueOf(v_fecpro);
     String v_fecefe = (java.lang.String)session.getValue("s_fecefectiva");
     //Date v_fecefe2 = v_fecefe2.valueOf(v_fecefe);
     java.sql.Date v_fecefe2 = new java.sql.Date(4);
     v_fecefe2 = java.sql.Date.valueOf(v_fecefe);
     String v_tranmulti = null;
     String v_tipo = (java.lang.String)session.getValue("s_tipotran");
     String v_clase =  (java.lang.String)session.getValue("s_clasetran");
     double v_valor2 = 0;
     String v_tipov ="STV002";
     String v_valoruni2 =(java.lang.String)session.getValue("s_valuni");
     double  v_valoruni = new Double(v_valoruni2).doubleValue();
     String v_penalizar =(java.lang.String)session.getValue("s_penaliza");
     String v_prorrata = (java.lang.String)session.getValue("s_fondo");
     String v_usuariopipe =(java.lang.String)session.getValue("s_usuariopipe");
     String v_tuni =(java.lang.String)session.getValue("s_unidadtax");
     String  v_banco =(java.lang.String)session.getValue("s_banco");

     String  v_numcuen =(java.lang.String)session.getValue("s_cuenta");

     String v_afp =(java.lang.String)session.getValue("s_afp");
     String v_proti =(java.lang.String)session.getValue("s_proti");
     String v_conti =(java.lang.String)session.getValue("s_conti");
     String v_sistema = (java.lang.String)session.getValue("s_sistema");
     String v_usumfund= (java.lang.String)session.getValue("s_usumfund");
     String v_passmfund= (java.lang.String)session.getValue("s_passmfund");
     String v_libreria = (java.lang.String)session.getValue("s_libreria");
     String v_bloqueo = "";
     //out.println("s_afp = "+ v_afp + "s_proti=" + v_proti + ",s_conti=" + v_conti + ",s_sistema=" + v_sistema + ",s_usumfund=" +v_usumfund+"s_passmfund="+v_passmfund+"s_libreria="+v_libreria);

/*
Modificacion:
No se realiza la conexion al AS 400 con la clase utilitaria
TBCL_FuncionesAs400, sino que se realiza invocando un procedimiento
almacenado en la base de datos, por lo cual no se requiere la siguiente
linea

     v_bloqueo = i_fondos.TBFL_BloqueoEgresos(v_contra,v_sistema,v_usumfund,v_passmfund,v_libreria);

*/

/*
Modificacion:
Se añade el procedimiento de invocacion a un procedimiento del AS400

*/

CallableStatement cs = t_tax.prepareCall ( "{? = call TBCL_FUNCIONESAS400.TBFL_BLOQUEOEGRESOS(?,?,?,?,?)}");
  cs.registerOutParameter(1,Types.CHAR);
  cs.setString(2,v_contra);
  cs.setString(3,v_sistema);
  cs.setString(4,v_usumfund);
  cs.setString(5,v_passmfund);
  cs.setString(6,v_libreria);
  cs.executeUpdate();
  v_bloqueo = cs.getString(1);
  cs.close();

/* Final de la modificacion */

     //SI EL CONTRATO NO TIENE BLOQUEO
     if(v_bloqueo.equals("N"))
     {//3

/*

Modificacion: Inicio del procedimiento de invocacion a un procedimiento del
AS 400

*/

      CallableStatement l_stmt0 = t_tax.prepareCall("{? = call TB_FINSERTAR_RETTOTAL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

      l_stmt0.registerOutParameter(1,Types.INTEGER);
      l_stmt0.registerOutParameter(4,Types.INTEGER);
      l_stmt0.registerOutParameter(21,Types.VARCHAR);
      l_stmt0.setString(2,v_pro);
      l_stmt0.setString(3,v_contra);
      l_stmt0.setDouble(4,v_conret);
      l_stmt0.setString(5,v_fecpro);
      l_stmt0.setString(6,v_fecefe);
      l_stmt0.setString(7,v_tipo);
      l_stmt0.setString(8,v_clase);
      l_stmt0.setDouble(9,v_valor2);
      l_stmt0.setString(10,v_tipov);
      l_stmt0.setDouble(11,v_valoruni);
      l_stmt0.setString(12,v_penalizar);
      l_stmt0.setString(13,v_prorrata);
      l_stmt0.setString(14,v_usuariopipe);
      l_stmt0.setString(15,v_tuni);
      l_stmt0.setString(16,v_banco);
      l_stmt0.setString(17,v_numcuen);
      l_stmt0.setString(18,v_afp);
      l_stmt0.setString(19,v_proti);
      l_stmt0.setString(20,v_conti);
      l_stmt0.execute();
      int v_indicador  = l_stmt0.getInt(1);
      v_conret         = l_stmt0.getDouble(4);
      String v_mensaje = l_stmt0.getString(21);
      l_stmt0.close();
      if(v_indicador == 0)
      {
       if(v_banco.trim().equals(""))
       {
        v_banco = null;
       }
       if(v_numcuen.trim().equals(""))
       {
        v_numcuen = null;
       }
       if(v_afp.trim().equals(""))
       {
        v_afp = null;
       }
       if(v_proti.trim().equals(""))
       {
        v_proti = null;
       }
       if(v_conti.trim().equals(""))
       {
        v_conti = null;
       }
       
        /*Modificación hecha por APC para manejar el nuevo reglamento 2006-06-22*/          
        String v_retorno_programa = new String("");
        String v_programa;
        CallableStatement cs2 = t_tax.prepareCall ( "{? = call TBCL_FuncionesAs400.TBPL_ProgramaContrato(?,?,?,?,?)}");
        cs2.registerOutParameter(1,Types.CHAR);
        cs2.setString(2,v_contra);
        cs2.setString(3,v_sistema);
        cs2.setString(4,v_usumfund);
        cs2.setString(5,v_passmfund);
        cs2.setString(6,v_libreria);
        cs2.executeUpdate();
        v_retorno_programa = cs2.getString(1);
        cs2.close();
        v_programa = v_retorno_programa.substring(0,v_retorno_programa.indexOf(";",0));
        /*FIN Modificación hecha por APC para manejar el nuevo reglamento 2006-06-22*/          

       


       /*out.println("<BR>"+v_pro);
       out.println("<BR>"+v_contra);
       out.println("<BR>"+v_programa);
       out.println("<BR>"+v_conret);
       out.println("<BR>"+v_fecpro2);
       out.println("<BR>"+v_fecefe2);
       out.println("<BR>"+v_tipo);
       out.println("<BR>"+v_clase);
       out.println("<BR>"+v_valor2);
       out.println("<BR>"+v_tipov);
       out.println("<BR>"+v_valoruni);
       out.println("<BR>"+v_penalizar);
       out.println("<BR>"+v_prorrata);
       out.println("<BR>"+v_banco);
       out.println("<BR>"+v_numcuen);
       out.println("<BR>"+v_tranmulti);
       out.println("<BR>"+v_usuariopipe);
       out.println("<BR>"+v_tuni);
       out.println("<BR>"+"SER001");
       out.println("<BR>"+v_afp);
       out.println("<BR>"+v_proti);
       out.println("<BR>"+v_conti);
       out.println("<BR>"+v_tranmulti);
       out.println("<BR>"+v_tranmulti);
       out.println("<BR>"+v_tranmulti);
       out.println("<BR>"+v_tranmulti);
       out.println("<BR>"+v_tranmulti);
       out.println("<BR>"+v_tranmulti);
       out.println("<BR>"+"N");
       out.println("<BR>"+"S");*/

       

       CallableStatement l_stmt1 = t_tax.prepareCall("{call TBPBD_RETIRO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
       l_stmt1.registerOutParameter(31,Types.VARCHAR);
       l_stmt1.registerOutParameter(32,Types.VARCHAR);
       l_stmt1.registerOutParameter(33,Types.INTEGER);
       l_stmt1.registerOutParameter(34,Types.DOUBLE);
       l_stmt1.registerOutParameter(35,Types.INTEGER);
       l_stmt1.registerOutParameter(36,Types.VARCHAR);
       l_stmt1.setString(1,v_pro);
       l_stmt1.setString(2,v_contra);
       l_stmt1.setString(3,v_programa);
       l_stmt1.setDouble(4,v_conret);
       l_stmt1.setDate(5,v_fecpro2);
       l_stmt1.setDate(6,v_fecefe2);
       l_stmt1.setString(7,v_tipo);
       l_stmt1.setString(8,v_clase);
       l_stmt1.setDouble(9,v_valor2);
       l_stmt1.setString(10,v_tipov);
       l_stmt1.setDouble(11,v_valoruni);
       l_stmt1.setString(12,v_penalizar);
       l_stmt1.setString(13,v_prorrata);
       l_stmt1.setString(14,v_banco);
       l_stmt1.setString(15,v_numcuen);
       l_stmt1.setString(16,v_tranmulti);
       l_stmt1.setString(17,v_usuariopipe);
       l_stmt1.setString(18,v_tuni);
       l_stmt1.setString(19,"SER001");
       l_stmt1.setString(20,v_afp);
       l_stmt1.setString(21,v_proti);
       l_stmt1.setString(22,v_conti);
       l_stmt1.setString(23,v_tranmulti);
       l_stmt1.setString(24,v_tranmulti);
       l_stmt1.setString(25,v_tranmulti);
       l_stmt1.setString(26,v_tranmulti);
       l_stmt1.setString(27,v_tranmulti);
       l_stmt1.setString(28,v_tranmulti);
       l_stmt1.setString(29,"N");
       l_stmt1.setString(30,"S");
       l_stmt1.execute();
       v_pro            = l_stmt1.getString(31);
       v_contra         = l_stmt1.getString(32);
       v_conret         = l_stmt1.getDouble(33);
       double v_valnetret = l_stmt1.getDouble(34);
       int v_indiretiro = l_stmt1.getInt(35);
       String v_mensajeret =  l_stmt1.getString(36);
       l_stmt1.close();
        try {  
              String v_esTercero =(java.lang.String)session.getValue("esTercero"); 
              String v_doc_ter="", v_tipodoc_ter="", v_nomb_terc="", v_apell_terc ="";
              if (v_esTercero.equals("S"))
              {
                  try { v_doc_ter = (java.lang.String)session.getValue("v_doc_ter"); } catch (Exception e) { e.printStackTrace(); }
                  try { v_tipodoc_ter = (java.lang.String)session.getValue("v_tipodoc_ter"); } catch (Exception e) { e.printStackTrace(); }
                  try { v_nomb_terc = (java.lang.String)session.getValue("v_nomb_terc"); } catch (Exception e) { e.printStackTrace(); }
                  try { v_apell_terc = (java.lang.String)session.getValue("v_apell_terc"); } catch (Exception e) { e.printStackTrace(); }
                  if ((!v_doc_ter.trim().equals("")&&
                       !v_tipodoc_ter.trim().equals("")&&
                       !v_apell_terc.trim().equals("")) &&
                       (v_numcuen== null || v_numcuen.trim().equals("")) )
                   {
                     l_stmt1 = t_tax.prepareCall("{call TBPBD_InsRetirosTerceros(?,?,?,?,?,?,?,?,?)}");
                     l_stmt1.registerOutParameter(8,Types.INTEGER);
                     l_stmt1.registerOutParameter(9,Types.VARCHAR);
                     l_stmt1.setString(1,v_pro);
                     l_stmt1.setString(2,v_contra);
                     l_stmt1.setDouble(3,v_conret);
                     l_stmt1.setString(4,v_tipodoc_ter);
                     l_stmt1.setString(5,v_doc_ter);
                     l_stmt1.setString(6,v_nomb_terc);
                     l_stmt1.setString(7,v_apell_terc);
                     l_stmt1.execute();
                     int v_error = l_stmt1.getInt(8);
                     String v_mensajeretTer =l_stmt1.getString(9);
                     l_stmt1.close();
                   }
               } 
          }catch (Exception e)  { e.printStackTrace(); }
        
      /**Modificado 2009/10/27 Variable Id Saro*/  
      try{
        if(!v_idsaro.trim().equals("")) {
               l_stmt1 = t_tax.prepareCall("{call TBPBD_InsRETIROSREAPLICADOS(?,?,?,?,?,?)}");
               l_stmt1.registerOutParameter(5,Types.INTEGER);
               l_stmt1.registerOutParameter(6,Types.VARCHAR);
               l_stmt1.setString(1,v_pro);
               l_stmt1.setString(2,v_contra);
               l_stmt1.setDouble(3,v_conret);
               l_stmt1.setString(4,v_idsaro);
               l_stmt1.execute();
               int v_error = l_stmt1.getInt(5);
               String v_mensajeretTer =l_stmt1.getString(6);
               l_stmt1.close();
        } 
      }catch (Exception e)  { e.printStackTrace(); } 
           
       if(v_indiretiro == 0)
       {
        session.removeValue("s_valuni");
        t_tax.commit();
        String v_pintar=    i_pagina.TBFL_CABEZA ("Solicitud de Retiro","Solicitud de Retiro","","<center>Solicitud de Retiro "+v_conret+" realizada con exito.</center>",false);
        out.println(""+v_pintar+"");
        out.println("<BR><BR>");
        out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-4)'></center>");
        String v_pie = i_pagina.TBFL_PIE;
        out.println("<br>");
        out.println(""+v_pie+"");
        out.close();
       }
       else
       {

         t_tax.rollback();
         String v_pintar=    i_pagina.TBFL_CABEZA ("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","Error en el proceso TBPBD_RETIRO: "+v_mensajeret,false);
         out.println(""+v_pintar+"");
         out.println("<BR>");
         out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-7)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
         String v_pie = i_pagina.TBFL_PIE;
         out.println("<br>");
         out.println(""+v_pie+"");
         out.close();
       
       }
      }
      else
      {
       t_tax.rollback();
       String v_pintar=    i_pagina.TBFL_CABEZA ("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","Error en el proceso TB_FINSERTAR_RETTOTAL:: "+v_mensaje ,false);
       out.println(""+v_pintar+"");
       out.println("<BR>");
       out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-4)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
       String v_pie = i_pagina.TBFL_PIE;
       out.println("<br>");
       out.println(""+v_pie+"");
       out.close();
      }
     }
     else
     {
      if(v_bloqueo.equals("Y"))
      {
       String v_pintar2=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>El contrato "+v_contra+" tiene bloqueo de egresos, no es posible realizar la solicitud de retiro.</center>",false);
       out.println(""+v_pintar2+"");
       String v_pie2 = i_pagina.TBFL_PIE;
       out.println("<br>");
       out.println("<br>");
       out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-4)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
       out.println(""+v_pie2+"");
       out.close();
      }
      else
      {
       String v_pintar2=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>No se establecio comunicación con el AS/400.</center>",false);
       out.println(""+v_pintar2+"");
       String v_pie2 = i_pagina.TBFL_PIE;
       out.println("<br>");
       out.println("<br>");
       out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-4)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
       out.println(""+v_pie2+"");
       out.close();
      }
     }
    }
    else
    {
     String v_pintarout=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.</center>",false);
     out.println(""+v_pintarout+"");
     String v_pieout = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-4)'></center>");
     out.println(""+v_pieout+"");
     out.close();
    }
   }
   else
   {
    t_tax.rollback();
    String v_pintarout=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro.","","<center>Su solicitud de retiro ya ha sido enviada.</center>",false);
    out.println(""+v_pintarout+"");
    String v_pieout = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-7)'></center>");
    out.println(""+v_pieout+"");
    out.close();
   }
  }
  catch(Exception ex)
  {
   String v_pintar="";
   String error = ex.toString();
   if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
   {
    v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.</center>",false);
   }
   else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
        {
         v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
        }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_pintar = i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.</center>",false);
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_pintar =  i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.</center>",false);
                     }
                  else
                  {
                   v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                  }
   out.println(""+v_pintar+"");
   out.println("<BR>");
   out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-4)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
   String v_pie = i_pagina.TBFL_PIE;
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

