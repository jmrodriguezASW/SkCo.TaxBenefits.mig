package TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;


/*clase que actualiza la informacion de la distribución de los fondos*/
public class TBS_ActualizarDistrifon extends HttpServlet
{
 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
  PrintWriter out = new PrintWriter (response.getOutputStream());
  STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();
  Connection t_tax  =   null;
 try
 {
  //toma session
  HttpSession session = request.getSession(true);
  if(session==null)
   session = request.getSession(true);
  session.setMaxInactiveInterval(3600);
  response.setContentType("text/html");
  /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
  // response.setHeader("Pragma", "No-Cache");
  // response.setDateHeader("Expires", 0);
   String v_contrato = "", v_producto = "", v_usuario2  = "", v_unidad = "";
  String v_tipousu = "", v_idworker = "";
  String   nuevaCadena = "";

  String parametros[] = new String[8];
  String  cadena = request.getParameter("cadena");
  nuevaCadena = cadena;
  String ip_tax = request.getRemoteAddr();
  TBCL_Seguridad Seguridad = new TBCL_Seguridad();
  parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
  v_contrato = parametros[0];
  v_producto = parametros[1];
  v_usuario2  = parametros[2];
  v_unidad = parametros[3];
  v_tipousu = parametros[4];
  v_idworker = parametros[5];

  //se hace conexion a taxbenefit
  t_tax =   DataSourceWrapper.getInstance().getConnection();
  if((java.lang.String)session.getAttribute("s_producto") != null ||(java.lang.String)session.getAttribute("s_contrato") != null)
  {
   String v_pro         = (java.lang.String)session.getAttribute("s_producto");//"MFUND";
   String v_contra      = (java.lang.String)session.getAttribute("s_contrato");//"000011425";
   String v_consecutivo = (java.lang.String)session.getAttribute("s_conret");
   int v_conret         = new Integer (v_consecutivo).intValue();
   String v_confonvalor = (java.lang.String)session.getAttribute("s_codfonvalor");
   String v_prorrata    = (java.lang.String)session.getAttribute("s_prorrata");
   String v_usuario     = (java.lang.String)session.getAttribute("s_usuariopipe");
   String v_maximo      = (java.lang.String)session.getAttribute("s_maximo");
   String v_fondo       = (java.lang.String)session.getAttribute("s_fondo");
   String v_valre       = (java.lang.String)session.getAttribute("s_valoret");
   double    v_valre2   = new Double(v_valre).doubleValue();
   String v_maximo2     = "";
   try { v_maximo2 = request.getParameter("v_maximo2"); } catch (Exception e) { e.printStackTrace(); }
   if(v_fondo.equals("1")||v_fondo.equals("0"))
   {
    for ( int i=1;i<= new Integer(v_maximo2).intValue();i++)
    {
     String v_valorcli = "";
     try { v_valorcli = request.getParameter("valor"+i); } catch (Exception e) { e.printStackTrace(); }
     session.removeAttribute("s_valorcli"+i+"");
     session.setAttribute("s_valorcli"+i+"",v_valorcli);
    }
   }
   else
   {
    if(v_fondo.equals("2"))
    {
     for ( int i=1;i<= new Integer(v_maximo2).intValue();i++)
     {
      String v_porcencli = "";
      try { v_porcencli = request.getParameter("porcen"+i); } catch (Exception e) { e.printStackTrace(); }
     session.removeAttribute("s_porcencli"+i+"");
      session.setAttribute("s_porcencli"+i+"",v_porcencli);
     }
    }
   }
   String  v_codfon   = "";
   String  v_valorfon = "";
   int v_indicador    = 0;
   int v_indicador2   = 0;
   int v_indicador3   = 0;
   int v_indicador4   = 0;
   String v_men       = "";
   String v_men2      = "";
   String v_men3      = "";
   String v_men4      = "";
   if(v_fondo.equals("1") || v_fondo.equals("0"))
   {
    CallableStatement l_stmt3 = t_tax.prepareCall("{? = call TB_FBORRAR_DISTRIBUCION(?,?,?,?)}");
    l_stmt3.registerOutParameter(1,Types.INTEGER);
    l_stmt3.registerOutParameter(5,Types.VARCHAR);
    l_stmt3.setString(2,v_pro);
    l_stmt3.setString(3,v_contra);
    l_stmt3.setInt(4,v_conret);
    l_stmt3.execute();
    v_indicador3  = l_stmt3.getInt(1);
    v_men3 = l_stmt3.getString(5);
    l_stmt3.close();
    
    if(v_indicador3 ==0)
    {
     double v_sumporcentaje = 0;
     for ( int i=1;i<= new Integer(v_maximo2).intValue();i++)
     {
      v_codfon   = (java.lang.String)session.getAttribute("s_codfon"+i+"");
      v_valorfon = (java.lang.String)session.getAttribute("s_valorcli"+i+"");
      if(!v_valorfon.trim().equals(""))
      {
       double v_valorfon2 = new Double(v_valorfon).doubleValue();
       double v_porcentaje = (v_valorfon2*100)/v_valre2;
       // SE AGREGÓ ESTA PARTE DEL CODIGO (ABRIL 23/2002) PARA AJUSTAR EL PORCENTAJE TOTAL       
          v_porcentaje =java.lang.Math.round(v_porcentaje*100);
          v_porcentaje = v_porcentaje /100;
          v_sumporcentaje = v_sumporcentaje + v_porcentaje;
          double v_dif = 100 - v_sumporcentaje;
          if( Math.abs(v_dif) > 0 && Math.abs(v_dif)<0.1)
          {
           if(v_sumporcentaje > 100)
           {
            v_porcentaje = v_porcentaje - Math.abs(v_dif);
           }
           else if(v_sumporcentaje <   100)
           {
            v_porcentaje = v_porcentaje + Math.abs(v_dif) ;
           }
          }
       //
       CallableStatement l_stmt1 = t_tax.prepareCall("{? = call TB_FINSERTAR_DISTRIBUCION(?,?,?,?,?,?,?)}");
       l_stmt1.registerOutParameter(1,Types.INTEGER);
       l_stmt1.registerOutParameter(8,Types.VARCHAR);
       l_stmt1.setString(2,v_pro);
       l_stmt1.setString(3,v_contra);
       l_stmt1.setInt(4,v_conret);
       l_stmt1.setString(5,v_codfon);
       l_stmt1.setDouble(6,v_valorfon2);
       l_stmt1.setDouble(7,v_porcentaje);
       l_stmt1.execute();
       v_indicador  =  l_stmt1.getInt(1);
       v_men = l_stmt1.getString(8);
       l_stmt1.close();
      }
     }
    }
    else
    {
     v_indicador++;
    }
   }
   else
   {
    if(v_fondo.equals("2"))
    {
     CallableStatement l_stmt4 = t_tax.prepareCall("{? = call TB_FBORRAR_DISTRIBUCION(?,?,?,?)}");
     l_stmt4.registerOutParameter(1,Types.INTEGER);
     l_stmt4.registerOutParameter(5,Types.VARCHAR);
     l_stmt4.setString(2,v_pro);
     l_stmt4.setString(3,v_contra);
     l_stmt4.setInt(4,v_conret);
     l_stmt4.execute();
     v_indicador3  = l_stmt4.getInt(1);
     v_men4 = l_stmt4.getString(5);
     l_stmt4.close();
     if(v_indicador3 ==0)
     {
      for ( int i=1;i<= new Integer(v_maximo2).intValue();i++)
      {
       v_codfon = (java.lang.String)session.getAttribute("s_codfon"+i+"");
       String v_porcenfon = (java.lang.String)session.getAttribute("s_porcencli"+i+"");
       if(!v_porcenfon.trim().equals(""))
       {
        double v_porcenfon2     = new Double(v_porcenfon).doubleValue();
        double v_valorporcentaje = v_valre2 * (v_porcenfon2/100);
        CallableStatement l_stmt2 = t_tax.prepareCall("{? = call TB_FINSERTAR_DISTRIBUCION(?,?,?,?,?,?,?)}");
        l_stmt2.registerOutParameter(1,Types.INTEGER);
        l_stmt2.registerOutParameter(8,Types.VARCHAR);
        l_stmt2.setString(2,v_pro);
        l_stmt2.setString(3,v_contra);
        l_stmt2.setInt(4,v_conret);
        l_stmt2.setString(5,v_codfon);
        l_stmt2.setDouble(6,v_valorporcentaje);
        l_stmt2.setDouble(7,v_porcenfon2);
        l_stmt2.execute();
        v_indicador  =  l_stmt2.getInt(1);
        v_men = l_stmt2.getString(8);
        l_stmt2.close();
        //inserto en tbdistribucion fondos
       }
      }
     }
     else
     {
      v_indicador++;
     }
    }
   }
   if(v_indicador == 0)
   {
    CallableStatement l_stmtb = t_tax.prepareCall("{call TB_PINSERT_TRANSACCIONLOGS(?,?,?)}");
    l_stmtb.setInt(1,v_conret);
    l_stmtb.setString(2,v_confonvalor);
    l_stmtb.setString(3,v_usuario);
    l_stmtb.execute();
    l_stmtb.close();
    CallableStatement l_stmt3 = t_tax.prepareCall("{? = call TB_FUPDATE_PRORRATA(?,?,?,?,?)}");
    l_stmt3.registerOutParameter(1,Types.INTEGER);
    l_stmt3.registerOutParameter(6,Types.VARCHAR);
    l_stmt3.setString(2,v_pro);
    l_stmt3.setString(3,v_contra);
    l_stmt3.setInt(4,v_conret);
    l_stmt3.setString(5,v_prorrata);
    l_stmt3.execute();
    v_indicador3  =  l_stmt3.getInt(1);
    v_men3 = l_stmt3.getString(6);
    l_stmt3.close();
    if(v_indicador3 == 0)
    {
     t_tax.commit();
     String v_pintar=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Modificar Distribución de Fondos","","<center>Actualización de fondos realizada con exito.</center>",false);
     out.println(""+v_pintar+"");
     out.println("<BR>");
     out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-3)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
     String v_pie = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println(""+v_pie+"");
     out.close();
    }
    else
    {
     t_tax.rollback();
     String v_pintar=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Modificar Distribución de Fondos","","<center>Error al Modificar Distribución de Fondos de la Solicitud de Retiro.Mensaje de Error : "+v_men3+"</center>",false);
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
    t_tax.rollback();
    String v_pintar=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Modificar Distribución de Fondos","","<center>Error al Modificar Distribución de Fondos de la Solicitud de Retiro.Mensaje de error:"+v_men2+", "+v_men+"</center>",false);
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
   String v_pintarout=    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiro","Error al Modificar Distribución de Fondos","","<center>Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.</center>",false);
   out.println(""+v_pintarout+"");
   String v_pieout = i_pagina.TBFL_PIE;
   out.println("<br>");
   out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-4)'></center>");
   out.println(""+v_pieout+"");
   out.close();
  }
 }
 catch(Exception ex)
 {
 String v_menex = "";
    String error = ex.toString();
   if(ex.equals("java.sql.SQLException: found null connection context"))
   {
    v_menex = "Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.";
   }
   else if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
      {
        v_menex = "No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.";
      }
      else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
           {
             v_menex = "Se reinicio la base de datos por favor ingrese nuevamente.";
           }
            else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_menex = "Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.";
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_menex =  "Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.";
                     }
                     else
                     {
                       v_menex = "Mensaje de error: "+ex;
                     }
  String v_pintar=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Error al Modificar Distribución de Fondos","","<center>"+v_menex+"</center>",false);
  out.println(""+v_pintar+"");
  out.println("<BR>");
  out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-4)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
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

