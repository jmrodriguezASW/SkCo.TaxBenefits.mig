package TBPKT_MODULO_RETIROS.TBPKT_MODIFICAR_SOLICITUD;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import co.oldmutual.taxbenefit.util.DataSourceWrapper;


/*Clase que hace llamado al procedimiento almacenado que actualiza la informaci�n de banco*/

public class TBS_ActualizarBanco extends HttpServlet implements SingleThreadModel
{

 public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
  PrintWriter out = new PrintWriter (response.getOutputStream());
  STBCL_GenerarBaseHTML i_pagina  = new STBCL_GenerarBaseHTML ();
  HttpSession session = request.getSession(true);
  Connection t_tax  =   null;
 try
 {
  // se toma session
  if(session==null)
   session = request.getSession(true);

  session = request.getSession(true);
  session.setMaxInactiveInterval(3600);

  response.setContentType("text/html");
  /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
  // response.setHeader("Pragma", "No-Cache");
  // response.setDateHeader("Expires", 0);

  String v_contrato = "", v_producto = "", v_usuario2  = "", v_unidad = "";
  String v_tipousu = "", v_idworker = "";
  String parametros[] = new String[8];
  String  cadena = request.getParameter("cadena");
  String  nuevaCadena = cadena;
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
  if((java.lang.String)session.getValue("s_producto") != null ||(java.lang.String)session.getValue("s_contrato") != null)
  {
   String v_pro    = (java.lang.String)session.getValue("s_producto");//se toma producto
   String v_contra =(java.lang.String)session.getValue("s_contrato");//se toma contrato
   String v_usuario =(java.lang.String)session.getValue("s_usuariopipe");//se toma contrato
   String v_sele="";//se toma el dato para actualizar
   try {  v_sele= request.getParameter("v_seleccion");  }
   catch (Exception e) { e.printStackTrace();  }
   String v_conret = (java.lang.String)session.getValue("s_conret"); //se toma consecutivo del retiro
   int v_conse = new Integer(v_conret).intValue();
   String  v_banco = "   ";//codigo del banco
   String  v_numcuen ="    ";//numero de cuenta
   String  v_bancoviejo = (java.lang.String)session.getValue("s_bancoviejo");
   String  v_cuentaviejo = (java.lang.String)session.getValue("s_cuentaviejo");
   if(!v_sele.substring(0,1).equals(" "))
   {
    v_banco =  v_sele.substring(0,2);
    v_numcuen =  v_sele.substring(2,v_sele.length());
   }
   //se hace llamado al procecdimiento almacenado que actualiza infromaci�n bancos
   CallableStatement l_stmt0 = t_tax.prepareCall("{? = call TB_FACTUALIZAR_BANCO(?,?,?,?,?,?,?,?,?)}");
   l_stmt0.registerOutParameter(1,Types.INTEGER);
   l_stmt0.registerOutParameter(10,Types.VARCHAR);
   l_stmt0.setString(2,v_pro);
   l_stmt0.setString(3,v_contra);
   l_stmt0.setString(4,v_banco);
   l_stmt0.setString(5,v_numcuen);
   l_stmt0.setString(6,v_bancoviejo);
   l_stmt0.setString(7,v_cuentaviejo);
   l_stmt0.setString(8,v_usuario);
   l_stmt0.setInt(9,v_conse);
   l_stmt0.execute();
   int v_indicador  = l_stmt0.getInt(1);
   String v_mensaje = l_stmt0.getString(10);
   l_stmt0.close();
   // si se actualiza bien
   if(v_indicador == 0)
   {
    t_tax.commit();
    String v_pintar=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Modificar C�digo del Banco y N�mero Cuenta","","<center>"+v_mensaje+"</center>",false);
    out.println(""+v_pintar+"");
    out.println("<br>");
    out.println("<center><input type=button value='Aceptar'  onclick=' history.go(-2)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
    String v_pie = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println(""+v_pie+"");
    out.close();
   }
   else
   {//si nmo tuvo exito la actualizacion
    t_tax.rollback();
    String v_pintar=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Error al Modificar C�digo del Banco y N�mero Cuenta","","<center>"+v_mensaje+"</center>",false);
    out.println(""+v_pintar+"");
    out.println("<BR>");
    out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-3)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
    String v_pie = i_pagina.TBFL_PIE;
    out.println("<br>");
    out.println(""+v_pie+"");
    out.close();
   }
  }
  else
  {
   t_tax.rollback();
   String v_pintarout=    i_pagina.TBFL_CABEZA("Modificar Solicitud de Retiro","Error en Modificar C�digo de Banco y N�mero de cuenta","","<center>Error de comunicaci�n no se tiene conexi�n con el servidor web,por favor intente de nuevo.</center>",false);
   out.println(""+v_pintarout+"");
   String v_pieout = i_pagina.TBFL_PIE;
   out.println("<br>");
   out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-3)'></center>");
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
    v_menex = "Error de comunicaci�n no se tiene conexi�n con el servidor web,por favor intente de nuevo.";
   }
   else if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
      {
        v_menex = "No se tiene comunicaci�n con el servidor de datos  por favor ingrese nuevamente.";
      }
      else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
           {
             v_menex = "Se reinicio la base de datos por favor ingrese nuevamente.";
           }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_menex = "Error momentaneo de comunicaci�n con el servidor de datos, por favor intente entrar de nuevo a la opci�n.";
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_menex =  "Error momentaneo de comunicaci�n con el servidor Web, por favor intente entrar de nuevo a la opci�n.";
                     }

                     else
                     {
                       v_menex = "Mensaje de error: "+ex;
                     }
  String v_pintar=    i_pagina.TBFL_CABEZA ("Modificar Solicitud de Retiro","Error Modificar C�digo del Banco y N�mero Cuenta","","<center>"+v_menex+"<center>",false);
  out.println(""+v_pintar+"");
  out.println("<BR>");
  out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-3)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
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
