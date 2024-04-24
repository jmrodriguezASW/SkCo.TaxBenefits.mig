package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;

// import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;

import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

//import PKT_ACTUALIZA_APORTES.*;

public class TBS_SininfRetiro extends HttpServlet implements SingleThreadModel
{

 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
  PrintWriter out = new PrintWriter (response.getOutputStream());
  STBCL_GenerarBaseHTML i_pagina  = new  STBCL_GenerarBaseHTML ();
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
   String v_idsaro= "";
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

   //se hace conexion a taxbenefit
   t_tax =   DataSourceWrapper.getInstance().getConnection();
   
   //Statement t_st = t_tax.createStatement();
   double v_sumcap    = 0;
   double v_sumren    = 0;
   double v_sumcapren = 0;
   double v_sumrenneg = 0;
   double v_salTotConting = 0;
   double v_salTotRendN   = 0;
   double v_salTotRendH   = 0;
   double v_salTotCap2    = 0;
   double v_salTotRend2   = 0;
   double v_salTotCap1    = 0;
   double v_salTotRend1   = 0;
   double v_porret        = 0;
   double v_porpen        = 0;
   double v_porpencap     = 0;/**Porcentaje de penalización capital*/
   double v_porben        = 0;
   double v_pordis        = 0;
   double v_suma          =0;
   double v_formula       =0;
   double v_formula2      = 0;
   double v_sumsaldo_bruto= 0 ;
   double v_sumsaldo_neto = 0;
   double v_formulapor    = 0;
   double v_pencap        = 0;
   String v_msgErr        = "";
   String v_cargo1       = "";
   String v_cargo2       = "";
   String v_cargo3       = "";
   String v_cargo4       = "";

   int    v_coderr        = 0;
   String v_mincon        = "";
   boolean  v_cumple     = false;
   double v_minimo_contrato = 0;
   boolean v_indtraslado = false;
   int v_con  = 0;
   
   if((java.lang.String)session.getValue("s_contrato")!= null || (java.lang.String)session.getValue("s_producto")!= null)
     {
        String v_pro = (java.lang.String)session.getValue("s_producto");//"MFUND";
        String v_contra = (java.lang.String)session.getValue("s_contrato");//"000011425";
        String v_tipo = (java.lang.String)session.getValue("s_tipotran");//"UTT001";
        String v_clase = (java.lang.String)session.getValue("s_clasetran");//"UCT001";
        String v_valor =(java.lang.String)session.getValue("s_valor");//"100000";//
        double v_valor2 = new Double(v_valor).doubleValue();
        String v_tipov =(java.lang.String)session.getValue("s_tipov");//"STV001";
        String v_valoruni2 =(java.lang.String)session.getValue("s_valuni");//"1000";
        double  v_valoruni = new Double(v_valoruni2).doubleValue();
        String v_sistema = (java.lang.String)session.getValue("s_sistema");
        String v_usumfund= (java.lang.String)session.getValue("s_usumfund");
        String v_passmfund= (java.lang.String)session.getValue("s_passmfund");
        String v_libreria = (java.lang.String)session.getValue("s_libreria");
        String v_orden =(java.lang.String)session.getValue("s_orden");//"SMO001";//
        String v_respetar =(java.lang.String)session.getValue("s_respetar");//"SMO001";//
        String v_naturaleza =(java.lang.String)session.getValue("s_naturaleza");//"SNR001";//
        String v_nom    = (java.lang.String)session.getValue("s_nombres");
        String v_ape    = (java.lang.String)session.getValue("s_apellidos");
        String v_tipopenaliza  = (java.lang.String)session.getValue("s_penaliza");
        String v_fecha      =(java.lang.String)session.getValue("s_fecefectiva");//tomo fecha efectiva del retirpo
        java.sql.Date v_fecharetiro = new java.sql.Date(4);
        v_fecharetiro = java.sql.Date.valueOf(v_fecha);
        v_cargo1       = (java.lang.String)session.getValue("s_cargo1");
        v_cargo2       = (java.lang.String)session.getValue("s_cargo2");
        v_cargo3       = (java.lang.String)session.getValue("s_cargo3");
        v_cargo4       = (java.lang.String)session.getValue("s_cargo4");

        /**Modificado 2009/10/27 Variable Id Saro*/
        try{
          if((java.lang.String)session.getValue("v_idsaro")!= null){
            v_idsaro = (java.lang.String)session.getValue("v_idsaro");
            session.removeValue("v_idsaro");
            session.putValue("v_idsaro",v_idsaro);
          }
        }catch (Exception e)  { e.printStackTrace(); }
     
        boolean v_esnull = false;
        try
        {
          String[] vec3 = new String[request.getParameterValues("seleccion").length];
        }
        catch (Exception ex)
        {
          v_esnull = true;
        }

        int vcon2 = 0;
        if (v_esnull==false)
        {
          String[] vec2 = new String[request.getParameterValues("seleccion").length];
          if(v_orden.equals("SMO003"))
          {
            String v_retorno_programa = new String("");
            String v_programa = new String("");
            //out.println("<br>paso1<br>");
            CallableStatement cs2 = t_tax.prepareCall ( "{? = call TBCL_FuncionesAs400.TBPL_ProgramaContrato(?,?,?,?,?)}");
            cs2.registerOutParameter(1,Types.CHAR);
            cs2.setString(2,v_contrato);
            cs2.setString(3,v_sistema);
            cs2.setString(4,v_usumfund);
            cs2.setString(5,v_passmfund);
            cs2.setString(6,v_libreria);
            cs2.executeUpdate();
            v_retorno_programa = cs2.getString(1);
            cs2.close();
            v_programa = v_retorno_programa.substring(0,v_retorno_programa.indexOf(";",0));

            for (v_con=0;v_con < request.getParameterValues("seleccion").length; v_con++)
            {
              vec2[v_con] = request.getParameterValues("seleccion")[v_con];
              int v =vec2[v_con].length();
              String v_indicador = vec2[v_con].substring(0,1);
              double v_aporte2 = new Double(vec2[v_con].substring(1,v)).doubleValue();
              vcon2++;
              //out.println("<br>paso2<br>");
              CallableStatement l_stmt2 = t_tax.prepareCall("{call  TBPBD_SaldoAporteRet(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
              l_stmt2.registerOutParameter(12,Types.DOUBLE);
              l_stmt2.registerOutParameter(13,Types.DOUBLE);
              l_stmt2.registerOutParameter(14,Types.DOUBLE);
              l_stmt2.registerOutParameter(15,Types.DOUBLE);
              l_stmt2.registerOutParameter(16,Types.DOUBLE);
              l_stmt2.registerOutParameter(17,Types.DOUBLE);
              l_stmt2.registerOutParameter(18,Types.DOUBLE);
              l_stmt2.registerOutParameter(19,Types.DOUBLE);
              l_stmt2.registerOutParameter(20,Types.DOUBLE);
              l_stmt2.registerOutParameter(21,Types.DOUBLE);
              l_stmt2.registerOutParameter(22,Types.DOUBLE);
              l_stmt2.registerOutParameter(23,Types.DOUBLE);
              l_stmt2.registerOutParameter(24,Types.INTEGER);
              l_stmt2.registerOutParameter(25,Types.VARCHAR);

              l_stmt2.setString(1,v_pro);
              l_stmt2.setString(2,v_contra);
              l_stmt2.setDouble(3, v_aporte2);
              l_stmt2.setDouble(4,v_valoruni);
              l_stmt2.setString(5,v_tipopenaliza);
              l_stmt2.setDate(6,v_fecharetiro);
              l_stmt2.setString(7,v_cargo1);
              l_stmt2.setString(8,v_cargo2);
              l_stmt2.setString(9,v_cargo3);
              l_stmt2.setString(10,v_cargo4);
              l_stmt2.setString(11,v_programa);
              l_stmt2.setInt(24, v_coderr);
              l_stmt2.setString(25,v_msgErr);
              l_stmt2.execute();

              //out.println("<br>paso3<br>");
              v_salTotCap1       = l_stmt2.getDouble(12);
              v_salTotConting = l_stmt2.getDouble(13);
              v_salTotRend1       = l_stmt2.getDouble(14);
              v_salTotRendN   = l_stmt2.getDouble(15);
              v_salTotRendH   = l_stmt2.getDouble(16);
              v_salTotCap2    = l_stmt2.getDouble(17);
              v_salTotRend2   = l_stmt2.getDouble(18);
              v_porret        = l_stmt2.getDouble(19);
              v_porpen        = l_stmt2.getDouble(20);
              v_porpencap     = l_stmt2.getDouble(21);
              v_porben        = l_stmt2.getDouble(22);
              v_pordis        = l_stmt2.getDouble(23);
              v_coderr        = l_stmt2.getInt(24);
              v_msgErr        = l_stmt2.getString(25);

              l_stmt2.close();

              //out.println("<br>paso4<br>");
              if(v_coderr == 0)
              {
                 v_sumsaldo_bruto = v_sumsaldo_bruto + v_salTotCap1 + v_salTotRend1;
                 v_sumsaldo_neto  =  v_sumsaldo_neto + v_salTotCap2 + v_salTotRend2;

                  if(v_naturaleza.equals("SNR001")&& v_tipov.equals("STV001") && v_respetar.equals("S"))//rendimientos
                  {
                   v_sumren = v_sumren + v_salTotRend1;
                   if(v_salTotRend1 < 0)
                   {
                    v_sumrenneg = v_sumrenneg + v_salTotRend1;//rendimientos negativos
                   }
                  }
                  else
                  {
                   if(v_naturaleza.equals("SNR001")&& v_tipov.equals("STV001") && v_respetar.equals("N"))//rendimientos
                   {

                     v_sumren = v_sumren + v_salTotRend1 + v_salTotCap1 ;

                    if(v_salTotRend1 < 0)
                    {
                      v_sumrenneg = v_sumrenneg + v_salTotRend1;
                    }
                   }
                  }
                  //rendimiento neto
                  if(v_naturaleza.equals("SNR001")&& v_tipov.equals("STV002") && v_respetar.equals("S"))//rendimientos
                  {
                   v_sumren = v_sumren + v_salTotRend2;
                   if(v_salTotRend1 < 0)
                   {
                    v_sumrenneg = v_sumrenneg + v_salTotRend2;
                   }
                  }
                  else
                  {
                   if(v_naturaleza.equals("SNR001")&& v_tipov.equals("STV002") && v_respetar.equals("N"))//rendimientos
                   {
                     v_sumren = v_sumren + v_salTotRend2 + v_salTotCap2 ;

                    if(v_salTotRend2 < 0)
                    {
                     v_sumrenneg = v_sumrenneg + v_salTotRend2;
                    }
                   }
                  }
                  //proporcional
                  if(v_naturaleza.equals("SNR003") && v_tipov.equals("STV001"))//proporcional
                  {
                   v_sumcapren =  v_sumcapren + v_salTotCap1 + v_salTotRend1;
                  }
                  else
                  {
                   v_sumcapren =  v_sumcapren + v_salTotCap2 + v_salTotRend2;
                  }
                  //capital bruto
                  if(v_naturaleza.equals("SNR002") && v_tipov.equals("STV001") && v_respetar.equals("S"))//capital
                  {
                   v_sumcap =  v_sumcap + v_salTotCap1;
                  }
                  else
                  {
                   if(v_naturaleza.equals("SNR002") && v_tipov.equals("STV001") && v_respetar.equals("N"))//capital
                   {
                     v_sumcap =  v_sumcap + v_salTotCap1+v_salTotRend1;
                   }
                  }
                  //capital neto
                  if(v_naturaleza.equals("SNR002") && v_tipov.equals("STV002") && v_respetar.equals("S"))//capital
                  {
                   v_sumcap =  v_sumcap + v_salTotCap2;
                  }
                  else
                  {
                   if(v_naturaleza.equals("SNR002") && v_tipov.equals("STV002") && v_respetar.equals("N"))//capital
                   {
                     v_sumcap =  v_sumcap + v_salTotCap2+v_salTotRend2;

                   }
                  }

                 if(v_indicador.equals("T"))
                 {
                    v_indtraslado = true;

                 }

              }
              else
              {
                t_tax.rollback();
                String v_pintar=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error al procesar Solicitud de Retiro. Mensaje :"+ v_msgErr+" ,función  TBPBD_SaldoAporteRetiro.</center>",false);
                out.println(""+v_pintar+"");
                out.println("<BR>");
                out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-6)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
                String v_pie = i_pagina.TBFL_PIE;
                out.println("<br>");
                out.println(""+v_pie+"");
               out.close();


              }
               session.removeValue("s_sel"+v_con);
               session.putValue("s_sel"+v_con,(java.lang.Object)vec2[v_con].substring(1,v));


           }


           if(v_naturaleza.equals("SNR002"))
           {
            // v_sumcap = v_sumcap + v_sumrenneg;
            if(v_valor2< v_sumcap)
            {
              v_cumple =  true;
            }

           }


           if(v_naturaleza.equals("SNR001"))
           {
            if(v_valor2< v_sumren)
            {
              v_cumple =  true;
            }



           }
           if(v_naturaleza.equals("SNR003"))
           {
            if(v_valor2< v_sumcapren)
            {
              v_cumple =  true;
            }
           }
         //inserto en tbseleccion
        }



        if(v_cumple)
        {
            t_tax.rollback();
            session.removeValue("s_contador");
            session.putValue("s_contador",(java.lang.Object)new Integer(v_con).toString());
            String v_pintar=    i_pagina.TBFL_CABEZA("Calcular Solicitud de Retiro","Calcular Solicitud de Retiro","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_CalcularRetiro","",true,"modulo_retiros.js","return validar_retiro(this)");
            out.println(""+v_pintar+"");
            /*Cambio para manejo de referencia unica 2009/03/30 MOS */
            String v_contrato_unif = "";
            CallableStatement v_clsm = t_tax.prepareCall("{? = call TBFBD_obtener_ref_unica(?,?)}");
            v_clsm.registerOutParameter(1,Types.VARCHAR);
            v_clsm.setString(2,v_producto);
            v_clsm.setString(3,v_contrato);
            v_clsm.execute();
            v_contrato_unif = v_clsm.getString(1);
            v_clsm.close();

            
            out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
            out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
            out.println("<br>");
            out.println("<br>");
            if(v_indtraslado)
            {
              session.removeValue("s_indinformacion");
              session.putValue("s_indinformacion","S");
              out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'>De los aportes seleccionados hay  aportes sin información de traslado, ¿Desea realizar la solicitud de retiro?</font></center>");
              out.println("<br>");
              out.println("<PRE>");
              out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
              out.println("<center><input type=submit value='Aceptar' name = 'aceptar'><input type=button value='Regresar' onclick=' history.go(-1)' name = 'regresar'><input type=button value='Cancelar' onclick='history.go(-6)' name = 'cancelar'></center>");

              String v_piemin2 = i_pagina.TBFL_PIE;
              out.println("<br>");
              out.println("<br>");
              out.println(""+v_piemin2+"");
              out.close();
            }
            else
            {
              session.removeValue("s_indinformacion");
              session.putValue("s_indinformacion","N");
              out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'>¿Desea realizar la solicitud de retiro?</font></center>");
              out.println("<br>");
              out.println("<PRE>");
              out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
              out.println("<center><input type=submit value='Aceptar' name = 'aceptar'><input type=button value='Regresar' onclick=' history.go(-1)' name = 'regresar'><input type=button value='Cancelar' onclick='history.go(-6)' name = 'cancelar'></center>");
              String v_piemin2 = i_pagina.TBFL_PIE;
              out.println("<br>");
              out.println("<br>");
              out.println(""+v_piemin2+"");
              out.close();
            }
         }
         else
         {
             t_tax.rollback();
            String v_pintar4=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error al procesar solicitud de Retiro. Los aportes seleccionados no cumplen con el valor a retirar.</center>",false);
            out.println(""+v_pintar4+"");
            out.println("<BR>");
            out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-6)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
            String v_pie4 = i_pagina.TBFL_PIE;
            out.println("<br>");
            out.println(""+v_pie4+"");
            out.close();

         }
       }
       else
       {
         t_tax.rollback();
         String v_pintar4=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Favor seleccionar el/los aportes a retirar.</center>",false);
         out.println(""+v_pintar4+"");
         out.println("<BR>");
         out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-6)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
         String v_pie4 = i_pagina.TBFL_PIE;
         out.println("<br>");
         out.println(""+v_pie4+"");
         out.close();
       }
      }
      else
      {
          t_tax.rollback();
          String v_pintarout=    i_pagina.TBFL_CABEZA("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.</center>",false);
          out.println(""+v_pintarout+"");
          String v_pieout = i_pagina.TBFL_PIE;
          out.println("<br>");
          out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-6)'></center>");
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
    v_pintar=    i_pagina.TBFL_CABEZA("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.</center>",false);
   }
   else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
   {
    v_pintar=    i_pagina.TBFL_CABEZA("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
   }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                v_pintar = i_pagina.TBFL_CABEZA("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.</center>",false);
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_pintar =  i_pagina.TBFL_CABEZA("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.</center>",false);
                     }
                  else
                  {
                   v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                  }
   out.println(""+v_pintar+"");
   out.println("<BR>");
   out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-6)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
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

