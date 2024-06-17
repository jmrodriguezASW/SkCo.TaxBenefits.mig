package TBPKT_PARAMETROS.TBPKT_ACTUALIZAR_CONTRATOS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
/**Servlet que muestra al usuario la opción de cargar la informacion de contratos*/

public class TBS_PaginaActualizar extends HttpServlet{

 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
  /**Instancias de clase*/
  /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase STBCL_GenerarBaseHTML, no es necesaria la instancia nueva*/ 
 //STBCL_GenerarBaseHTML i_pagina = new STBCL_GenerarBaseHTML;/**Instancia de la clase STBCL_GenerarBaseHTML*/
   
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;/**Instancia de la clase TBC_Seguridad*/
  /**Variable tipo PrintWriter*/
  PrintWriter out = new PrintWriter (response.getOutputStream());/**Instancia de la clase STBCL_GenerarBaseHTML*/
  try
  {
   /**Tomar session*/
   HttpSession session = request.getSession(false);
   if (session == null) session = request.getSession(true);

   response.setContentType("text/html");
   /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
   // response.setHeader("Pragma", "No-Cache");
   // response.setDateHeader("Expires", 0);
   /**Declaracion de Variables*/
   /**Tipo String*/
   String v_contrato = "";
   String v_producto = "";
   String v_usuario  = "";
   String v_unidad   = "";
   String v_tipousu  = "";
   String v_idworker = "";
   String parametros[] = new String[8];
   String cadena = request.getParameter("cadena");
   String nuevaCadena = cadena;
   String ip_tax = request.getRemoteAddr();
   /**Validar seguridad*/
   parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
   v_contrato = parametros[0];
   v_producto = parametros[1];
   v_usuario  = parametros[2];
   v_unidad = parametros[3];
   v_tipousu = parametros[4];
   v_idworker = parametros[5];
   /**dibujar página de respuesta*/
   String v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Actualización de Contratos","Actualización de Contratos","TBPKT_PARAMETROS.TBPKT_ACTUALIZAR_CONTRATOS.TBS_ActualizarContratos","",true,"modulo_retiros.js","return validar_retiro(this)");
   out.println(""+v_pintar+"");
   out.println("<br>");
   out.println("<pre>");
   out.println("<br>");
   out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'><center>      ¿Desea actualiza la información de los contratos del sistema?</center></font>");
   out.println("</pre>");
   out.println("<br>");
   out.println("<PRE>");
   out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
   out.println("<center>  <input type='submit'  value='Aceptar' name='aceptar'><!input type='Button'  value='Regresar' onclick= 'history.go(-1)'> </center>");
   String v_piemin2 = STBCL_GenerarBaseHTML.TBFL_PIE;
   out.println("<br>");
   out.println("<br>");
   out.println(""+v_piemin2+"");
   out.close();
 }/**Manejo de errores*/
 catch(Exception e)
 {
  String v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA ("Actualización de Contratos","Error Actualizar Contratos","","<center>Mensaje de error: "+e+"</center>",false);
  out.println(""+v_pintar+"");
  out.println("<BR>");
  out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-1)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
  String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
  out.println("<br>");
  out.println(""+v_pie+"");
  out.close();
 }
}
}

