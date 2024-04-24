package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

/*
Modificacion:
Se elimina el import debido a que la conexion
al AS400 se hace desde la base de datos
import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;
*/

import java.math.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

public class TBS_CalcularRetiro extends HttpServlet
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
   String   nuevaCadena = "";
   String v_idsaro  = "";/**Variable id del evento de saro*/

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
   
   t_tax =   DataSourceWrapper.getInstance().getConnection();
   //Statement t_st = t_tax.createStatement();
   int v_indbeneficio = 0;
   double v_sumcap    = 0;
   double v_sumren    = 0;
   double v_sumcapren = 0;
   boolean v_cumple   = false;
   String v_codfon    = "  ";
   String v_valorfon  = "  ";
   String v_mensaje2  = "";
   String v_mensaje3  = "";
   String v_mensaje4  = "";
   String v_porcenfon = "";
   int v_indicador    = 0;
   String v_mensaje   = "";
   int v_indicador2   = 0;
   int v_indicador3   = 0;
   int v_indicador4   = 0;
   double v_valorfon2 = 0;
   double v_porcentaje= 0;
   double v_porcenfon2= 0;
   double v_valorporcentaje = 0;
   double v_salTotConting = 0;
   double v_salTotRendN   = 0;
   double v_salTotRendH   = 0;
   double v_salTotCap2    = 0;
   double v_salTotRend2   = 0;
   double v_salTotCap1    = 0;
   double v_salTotRend1   = 0;
   double v_sumrenneg     = 0;
   double v_sumsaldo_bruto   = 0 ;
   double v_sumsaldo_neto    = 0;
   String v_msgErr   = "";
   int    v_coderr    = 0;
   String v_mincon    = "";
   double v_minimo_contrato = 0;
   double v_formula      = 0;
   double v_formula2      = 0;
   double v_formulapor   = 0;
   double v_sumporcentaje = 0;
   String v_esquema = (java.lang.String)session.getAttribute("s_esquema");
     
   /**Modificado 2009/10/27 Variable Id Saro*/
   try {
   if((java.lang.String)session.getAttribute("v_idsaro")!= null){
       v_idsaro = (java.lang.String)session.getAttribute("v_idsaro");
       session.removeAttribute("v_idsaro");
       session.setAttribute("v_idsaro",v_idsaro);
     }
   }
   catch (Exception e) { e.printStackTrace(); }
  
   if((java.lang.String)session.getAttribute("s_naturaleza")!= null || v_esquema.equals("0"))
   {
    if((java.lang.String)session.getAttribute("s_contrato")!= null || (java.lang.String)session.getAttribute("s_producto")!= null)
    {

     if(!v_esquema.equals("0"))
     {
          String v_pro = (java.lang.String)session.getAttribute("s_producto");//"MFUND";
          String v_contra = (java.lang.String)session.getAttribute("s_contrato");//"000011425";
          String v_fecpro =(java.lang.String)session.getAttribute("s_fecpro");//"2001-01-23";


     
          //Date v_fecpro2 = v_fecpro2.valueOf(v_fecpro);
          java.sql.Date v_fecpro2 = new java.sql.Date(4);
          v_fecpro2 = java.sql.Date.valueOf(v_fecpro);
          String v_fecefe = (java.lang.String)session.getAttribute("s_fecefectiva");// "2001-01-23";
          //Date v_fecefe2 = v_fecefe2.valueOf(v_fecefe);
          java.sql.Date v_fecefe2 = new java.sql.Date(4);
          v_fecefe2 = java.sql.Date.valueOf(v_fecefe);
     
          String v_tipo = (java.lang.String)session.getAttribute("s_tipotran");//"UTT001";
          String v_clase = (java.lang.String)session.getAttribute("s_clasetran");//"UCT001";
          String v_valor =(java.lang.String)session.getAttribute("s_valor");//"100000";//
          double v_valor2 = new Double(v_valor).doubleValue();
          String v_tranmulti = null;
          String v_tipov =(java.lang.String)session.getAttribute("s_tipov");//"STV001";
          String v_valoruni2 =(java.lang.String)session.getAttribute("s_valuni");//"1000";
          double  v_valoruni = new Double(v_valoruni2).doubleValue();
          String v_penalizar =(java.lang.String)session.getAttribute("s_penaliza");
          String v_prorrata = (java.lang.String)session.getAttribute("s_prorrata");
          String v_fondo = (java.lang.String)session.getAttribute("s_fondo");
          String v_usuariopipe =(java.lang.String)session.getAttribute("s_usuariopipe");
          String v_tuni =(java.lang.String)session.getAttribute("s_unidadtax");
          String  v_banco =(java.lang.String)session.getAttribute("s_banco");
          String  v_numcuen =(java.lang.String)session.getAttribute("s_cuenta");
          String v_afp =(java.lang.String)session.getAttribute("s_afp");
          String v_proti =(java.lang.String)session.getAttribute("s_proti");
          String v_conti =(java.lang.String)session.getAttribute("s_conti");
          String v_maximo =(java.lang.String)session.getAttribute("s_maximo2");
          String v_sistema = (java.lang.String)session.getAttribute("s_sistema");
          String v_usumfund= (java.lang.String)session.getAttribute("s_usumfund");
          String v_passmfund= (java.lang.String)session.getAttribute("s_passmfund");
          String v_libreria = (java.lang.String)session.getAttribute("s_libreria");
          String v_bloqueo = "";
          String v_naturaleza =(java.lang.String)session.getAttribute("s_naturaleza");//"SNR001";//
          String v_respetar =(java.lang.String)session.getAttribute("s_respetar");
          String v_orden =(java.lang.String)session.getAttribute("s_orden");//"SMO001";//
          String v_indinformacion =(java.lang.String)session.getAttribute("s_indinformacion");//"SMO001";//
          String v_beneficio =(java.lang.String)session.getAttribute("s_beneficio");//"SMB003";//
          String v_metpen =(java.lang.String)session.getAttribute("s_metpen");//"SMP003";//
          String v_cuencon=(java.lang.String)session.getAttribute("s_cuencon");//"SMC003";//
          String v_actualizar =(java.lang.String)session.getAttribute("s_actualizar");//"SMC003";//
          String v_contador =(java.lang.String)session.getAttribute("s_contador");
          int v_con = new Integer (v_contador).intValue();

          if(v_actualizar.equals("S"))
         {
          PreparedStatement p_actualiza = t_tax.prepareStatement(" UPDATE TBCONTRATOS SET  CON_REF_METODO_ORDEN = '"+v_orden+"',CON_REF_METODO_BENEFICIO = '"+v_beneficio+"',CON_REF_METODO_PENALIZACION  = '"+v_metpen+"',CON_REF_METODO_CUENTA = '"+v_cuencon+"',CON_REF_NATURALEZA = '"+v_naturaleza+"',CON_RESPETAR_NATURALEZA = '"+v_respetar+"'  WHERE  CON_PRO_CODIGO = '"+v_pro+"' AND  CON_NUMERO  = '"+v_contra+"'");
          p_actualiza.executeUpdate();
          p_actualiza.close();
         }

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
    Se a�ade el procedimiento de invocacion a un procedimiento del AS400

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
          double v_conret = 0;
          CallableStatement l_stmt0 = t_tax.prepareCall("{? = call TB_FINSERTAR_RETPARCIAL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
          l_stmt0.registerOutParameter(1,Types.INTEGER);
          l_stmt0.registerOutParameter(4,Types.INTEGER);
          l_stmt0.registerOutParameter(27,Types.VARCHAR);
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
          l_stmt0.setString(21,v_orden);
          l_stmt0.setString(22,v_beneficio);
          l_stmt0.setString(23,v_metpen);
          l_stmt0.setString(24,v_cuencon);
          l_stmt0.setString(25,v_naturaleza);
          l_stmt0.setString(26,v_respetar);
          l_stmt0.execute();
          v_indicador  = l_stmt0.getInt(1);
          v_conret         = l_stmt0.getDouble(4);
          v_mensaje = l_stmt0.getString(27);
          l_stmt0.close();
          /*out.println("TB_FINSERTAR_RETPARCIAL("  + "->" + v_indicador + ",<br>"
                                              + v_pro + ",<br>"  
                                              + v_contra + ",<br>"
                                              + "->" + v_conret + ",<br>"  
                                              + v_fecpro + ",<br>"  
                                              + v_fecefe + ",<br>"  
                                              + v_tipo + ",<br>"  
                                              + v_clase + ",<br>"  
                                              + v_valor2 + ",<br>"  
                                              + v_tipov + ",<br>"  
                                              + v_valoruni + ",<br>"  
                                              + v_penalizar + ",<br>"  
                                              + v_prorrata + ",<br>"  
                                              + v_usuariopipe + ",<br>"  
                                              + v_tuni + ",<br>"  
                                              + v_banco + ",<br>"  
                                              + v_numcuen + ",<br>"  
                                              + v_afp + ",<br>"  
                                              + v_proti +  ",<br>"  
                                              + v_conti + ",<br>"  
                                              + v_orden + ",<br>"  
                                              + v_beneficio + ",<br>"  
                                              + v_metpen + ",<br>"  
                                              + v_cuencon +  ",<br>"  
                                              + v_naturaleza + ",<br>"  
                                              + v_respetar + ",<br>"  
                                              + " -> " + v_mensaje);*/
                                              

          if(v_indicador == 0)
          {
           if(v_fondo.equals("1") || v_fondo.equals("0"))
           {
            for ( int i=1;i<= new Integer(v_maximo).intValue();i++)
            {
            out.println("Llega aca 4");
             v_codfon = (java.lang.String)session.getAttribute("s_codfon"+i+"");
             v_valorfon = (java.lang.String)session.getAttribute("s_valorcli"+i+"");
             if(!v_valorfon.trim().equals("0") && !v_valorfon.trim().equals("")  )
             {
             out.println("Llega aca 5");
              v_valorfon2 = new Double(v_valorfon).doubleValue();
              v_porcentaje = (v_valorfon2*100)/v_valor2;
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
              out.println("Va a insertar por valor codigo="+v_codfon);
              CallableStatement l_stmt1 = t_tax.prepareCall("{? = call TB_FINSERTAR_DISTRIBUCION(?,?,?,?,?,?,?)}");
              l_stmt1.registerOutParameter(1,Types.INTEGER);
              l_stmt1.registerOutParameter(8,Types.VARCHAR);
              l_stmt1.setString(2,v_pro);
              l_stmt1.setString(3,v_contra);
              l_stmt1.setDouble(4,v_conret);
              l_stmt1.setString(5,v_codfon);
              l_stmt1.setDouble(6,v_valorfon2);
              l_stmt1.setDouble(7,v_porcentaje);
              l_stmt1.execute();
              v_indicador2  = l_stmt1.getInt(1);
              v_mensaje2 = l_stmt1.getString(8);
              l_stmt1.close();
/*              out.println("TB_FINSERTAR_DISTRIBUCION("  + "->" + v_indicador2 + ",<br>"
                                                  + v_pro + ",<br>"  
                                                  + v_contra + ",<br>"
                                                  + v_conret + ",<br>"  
                                                  + v_codfon + ",<br>"  
                                                  + v_valorfon2 + ",<br>"  
                                                  + v_porcentaje + ",<br>"  
                                                  + " -> " + v_mensaje2); */


              v_indicador2  = v_indicador2 + v_indicador;
              //inserto en tbdistribucion fondos
             }
            }
           }
           else
           {
            if(v_fondo.equals("2"))
            {
             for ( int i=1;i<= new Integer(v_maximo).intValue();i++)
             {
              v_codfon = (java.lang.String)session.getAttribute("s_codfon"+i+"");
              v_porcenfon = (java.lang.String)session.getAttribute("s_porcencli"+i+"");
              if( !v_porcenfon.trim().equals("0") && !v_porcenfon.trim().equals("") )
              {
               v_porcenfon2     = new Double(v_porcenfon).doubleValue();
               out.println("Va a insertar por porcentaje codigo="+v_codfon);
               v_valorporcentaje = v_valor2 * (v_porcenfon2/100);
               CallableStatement l_stmt2 = t_tax.prepareCall("{? = call TB_FINSERTAR_DISTRIBUCION(?,?,?,?,?,?,?)}");
               l_stmt2.registerOutParameter(1,Types.INTEGER);
               l_stmt2.registerOutParameter(8,Types.VARCHAR);
               l_stmt2.setString(2,v_pro);
               l_stmt2.setString(3,v_contra);
               l_stmt2.setDouble(4,v_conret);
               l_stmt2.setString(5,v_codfon);
               l_stmt2.setDouble(6,v_valorporcentaje);
               l_stmt2.setDouble(7,v_porcenfon2);
               l_stmt2.execute();
               v_indicador  = l_stmt2.getInt(1);
               v_mensaje2 = l_stmt2.getString(8);
               l_stmt2.close();
/*                out.println("TB_FINSERTAR_DISTRIBUCION("  + "->" + v_indicador + ",<br>"
                                                    + v_pro + ",<br>"  
                                                    + v_contra + ",<br>"
                                                    + v_conret + ",<br>"  
                                                    + v_codfon + ",<br>"  
                                                    + v_valorporcentaje + ",<br>"  
                                                    + v_porcenfon2 + ",<br>"  
                                                    + " -> " + v_mensaje2); */
           
               v_indicador3  = v_indicador3 + v_indicador;
               //inserto en tbdistribucion fondos
              }
             }
            }
           }
           if(v_indicador2 == 0 && v_indicador3 == 0 )
           {
            if(v_orden.equals("SMO003"))
            {
             int z = 0;
             for (int y=0;y < v_con; y++)
             {
              String vec = (java.lang.String)session.getAttribute("s_sel"+y+"");//"MFUND";
              double v_aporte = new Double(vec).doubleValue();
              z++;
              CallableStatement l_stmt3 = t_tax.prepareCall("{? = call  TB_FINSERTAR_APORSEL(?,?,?,?,?,?)}");
              l_stmt3.registerOutParameter(1,Types.INTEGER);
              l_stmt3.registerOutParameter(7,Types.VARCHAR);
              l_stmt3.setString(2,v_pro);
              l_stmt3.setString(3,v_contra);
              l_stmt3.setDouble(4,v_conret);
              l_stmt3.setDouble(5,v_aporte);
              l_stmt3.setInt(6,z);
              l_stmt3.execute();
              v_indicador  = l_stmt3.getInt(1);
              v_mensaje4 = l_stmt3.getString(7);
              l_stmt3.close();
              v_indicador4  = v_indicador4 + v_indicador;
              //inserto en tbdistribucion fondos
             }
            }
            if( v_indicador4 == 0)
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


          
            /*Modificaci�n hecha por APC para manejar el nuevo reglamento 2006-06-22*/          
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
            /*FIN Modificaci�n hecha por APC para manejar el nuevo reglamento 2006-06-22*/          




             CallableStatement l_stmt1 = t_tax.prepareCall("{call TBPBD_RETIRO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
             l_stmt1.registerOutParameter(31,Types.VARCHAR);
             l_stmt1.registerOutParameter(32,Types.VARCHAR);
             l_stmt1.registerOutParameter(33,Types.INTEGER);
             l_stmt1.registerOutParameter(34,Types.DOUBLE);
             l_stmt1.registerOutParameter(35,Types.INTEGER);
             l_stmt1.registerOutParameter(36,Types.VARCHAR);
             l_stmt1.setString(1,v_pro);
             l_stmt1.setString(2,v_contra);
             /*Se modifico porque se enviaba el contrato y no el programa 20090915 */
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
             l_stmt1.setString(23,v_orden);
             l_stmt1.setString(24,v_beneficio);
             l_stmt1.setString(25,v_metpen);
             l_stmt1.setString(26,v_cuencon);
             l_stmt1.setString(27,v_naturaleza);
             l_stmt1.setString(28,v_respetar);
             l_stmt1.setString(29,v_indinformacion);
             l_stmt1.setString(30,"S");
             l_stmt1.execute();
             v_pro            = l_stmt1.getString(31);
             v_contra         = l_stmt1.getString(32);
             v_conret         = l_stmt1.getDouble(33);
             double v_valnetret = l_stmt1.getDouble(34);
             int v_indiretiro = l_stmt1.getInt(35);
             String v_mensajeret =l_stmt1.getString(36);
             l_stmt1.close();
             
              try {  
                    String v_esTercero =(java.lang.String)session.getAttribute("esTercero"); 
                    String v_doc_ter="", v_tipodoc_ter="", v_nomb_terc="", v_apell_terc ="";
                    if (v_esTercero.equals("S"))
                    {
                        try { v_doc_ter = (java.lang.String)session.getAttribute("v_doc_ter"); } catch (Exception e) { e.printStackTrace(); }
                        try { v_tipodoc_ter = (java.lang.String)session.getAttribute("v_tipodoc_ter"); } catch (Exception e) { e.printStackTrace(); }
                        try { v_nomb_terc = (java.lang.String)session.getAttribute("v_nomb_terc"); } catch (Exception e) { e.printStackTrace(); }
                        try { v_apell_terc = (java.lang.String)session.getAttribute("v_apell_terc"); } catch (Exception e) { e.printStackTrace(); }
                        if ((!v_doc_ter.trim().equals("") &&
                             !v_tipodoc_ter.trim().equals("")&&
                             !v_apell_terc.trim().equals(""))&& 
                             (v_numcuen== null || v_numcuen.trim().equals("")))
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
                           /*eSTO ESTABA DE LA SIGUIENTE MANERA
                            *                            int v_error = l_stmt0.getInt(8);
                           String v_mensajeretTer =l_stmt0.getString(9);
                           l_stmt0.close();
                            * 
                            * */
                           int v_error = l_stmt1.getInt(8);
                           String v_mensajeretTer =l_stmt1.getString(9);
                           l_stmt1.close();
                         }
                     } 
                }catch (Exception e)  { e.printStackTrace(); }             

               /**Modificado 2009/10/27 Variable Id Saro*/
               try{
                 if(!v_idsaro.trim().equals("")) {
                       l_stmt1 = t_tax.prepareCall("{call TBPBD_InsRetirosReaplicados(?,?,?,?,?,?)}");
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
               }   catch (Exception e) { e.printStackTrace(); }
              
             out.println("call TBPBD_RETIRO(" + v_pro + ",<br>"  
                                              + v_contra + ",<br>"
                                              + v_conret + ",<br>"  
                                              + v_fecpro2 + ",<br>"  
                                              + v_fecefe2 + ",<br>"  
                                              + v_tipo + ",<br>"  
                                              + v_clase + ",<br>"  
                                              + v_valor2 + ",<br>"  
                                              + v_tipov + ",<br>"  
                                              + v_valoruni + ",<br>"  
                                              + v_penalizar + ",<br>"  
                                              + v_prorrata + ",<br>"  
                                              + v_banco + ",<br>"  
                                              + v_numcuen + ",<br>"  
                                              + v_tranmulti + ",<br>"  
                                              + v_usuariopipe + ",<br>"  
                                              + v_tuni + ",<br>"  
                                              + "SER001" +  ",<br>"  
                                              + v_afp + ",<br>"  
                                              + v_proti + ",<br>"  
                                              + v_conti + ",<br>"  
                                              + v_orden + ",<br>"  
                                              + v_beneficio +  ",<br>"  
                                              + v_metpen + ",<br>"  
                                              + v_cuencon + ",<br>"  
                                              + v_naturaleza + ",<br>"  
                                              + v_respetar + ",<br>"  
                                              + v_indinformacion + ",<br>"  
                                              + "S" + ",<br>"  
                                              + " -> " + v_pro + ",<br>"
                                              + " -> " + v_contra + ",<br>"
                                              + " -> " + v_conret + ",<br>"
                                              + " -> " + v_valnetret + ",<br>"
                                              + " -> " + v_indiretiro + ",<br>"
                                              + " -> " + v_mensajeret);

             if(v_indiretiro == 0)
             {
              session.removeAttribute("s_naturaleza");
              
              t_tax.commit();
              String v_pintar=    i_pagina.TBFL_CABEZA ("Solicitud de Retiro","Solicitud de Retiro","","<center>Solicitud de Retiro "+Math.round(v_conret)+" realizada con exito.</center>",false);
              out.println(""+v_pintar+"");
              //out.print("Va a insertar codigo="+v_codfon);
              out.println("<BR><BR>");
              out.println("<center><input type=button value='Aceptar'  onclick='history.go(-7)'></center>");
              String v_pie = i_pagina.TBFL_PIE;
              out.println("<br>");
              out.println(""+v_pie+"");
              out.close();
             }
             else
             {
              if(v_indiretiro == -5)
              {
               t_tax.rollback();
               String v_pintar=    i_pagina.TBFL_CABEZA ("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","En este momento se esta procesando una solicitud de retiro para el contrato, imposible realizar la transaccion",false);
               out.println(""+v_pintar+"");
               out.println("<BR>");
               out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-7)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
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
            }
            else
            {
             t_tax.rollback();
             String v_pintar=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error Calcular Solicitud de Retiro","","<center>Error al calcular solicitud de retiros. Mensaje :"+v_mensaje4+" ,funci�n TB_FINSERTAR_APORSEL.</center>",false);//
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
            String v_pintar=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error al procesar Solicitud de Retiro. Mensaje :"+v_mensaje2+" ,Funci�n TB_FINSERTAR_DISTRIBUCION </center>",false);//Mensaje :"+v_mensaje2+" ,f
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
           String v_pintar=    i_pagina.TBFL_CABEZA ("Calcular Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error al procesar Solicitud de Retiro.Mensaje :"+v_mensaje+" , Funci�n TB_FINSERTAR_RETPARCIAL.</center>",false);//
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
          if(v_bloqueo.equals("Y"))
          {
           t_tax.rollback();
           String v_pintar2=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>El contrato "+v_contra+" tiene bloqueo de egresos, no es posible realizar la solicitud</center>",false);
           out.println(""+v_pintar2+"");
           String v_pie2 = i_pagina.TBFL_PIE;
           out.println("<br>");
           out.println("<br>");
           out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-7)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
           out.println(""+v_pie2+"");
           out.close();
          }
          else
          {
           t_tax.rollback();
           String v_pintar2=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>No se establecio comunicaci�n con el AS/400.</center>",false);
           out.println(""+v_pintar2+"");
           String v_pie2 = i_pagina.TBFL_PIE;
           out.println("<br>");
           out.println("<br>");
           out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-7)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
           out.println(""+v_pie2+"");
           out.close();
          }
         }
     }
     else
     {
        String v_pro = (java.lang.String)session.getAttribute("s_producto");//"MFUND";
        String v_contra = (java.lang.String)session.getAttribute("s_contrato");//"000011425";
        String v_fecpro =(java.lang.String)session.getAttribute("s_fecpro");//"2001-01-23";
        java.sql.Date v_fecpro2 = new java.sql.Date(4);
        v_fecpro2 = java.sql.Date.valueOf(v_fecpro);
        String v_fecefe = (java.lang.String)session.getAttribute("s_fecefectiva");// "2001-01-23";      
        java.sql.Date v_fecefe2 = new java.sql.Date(4);
        v_fecefe2 = java.sql.Date.valueOf(v_fecefe);

        String v_tipov =(java.lang.String)session.getAttribute("s_tipov");//"STV001";
        String v_valor =(java.lang.String)session.getAttribute("s_valor");//"100000";//
        String v_tipo = (java.lang.String)session.getAttribute("s_tipotran");//"UTT001";
        String v_clase = (java.lang.String)session.getAttribute("s_clasetran");//"UCT001";
        String  v_banco =(java.lang.String)session.getAttribute("s_banco");
        String  v_numcuen =(java.lang.String)session.getAttribute("s_cuenta");
        String v_afp =(java.lang.String)session.getAttribute("s_afp");
        String v_proti =(java.lang.String)session.getAttribute("s_proti");
        String v_conti =(java.lang.String)session.getAttribute("s_conti");
        String v_usuariopipe =(java.lang.String)session.getAttribute("s_usuariopipe");





        double v_conret = 0;
        CallableStatement l_stmt0 = t_tax.prepareCall("{call TBPBD_GENERAR_NU_DEFAULT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
        l_stmt0.registerOutParameter(18,Types.INTEGER);
        l_stmt0.registerOutParameter(22,Types.INTEGER);
        l_stmt0.registerOutParameter(23,Types.VARCHAR);
        l_stmt0.setString(1,v_pro);
        l_stmt0.setString(2,v_contra);
        l_stmt0.setDouble(3,new Double(v_valor).doubleValue());  
        l_stmt0.setDate(4,v_fecefe2);
        l_stmt0.setDate(5,v_fecpro2);
        l_stmt0.setString(6,v_tipo);
        l_stmt0.setString(7,v_clase);
        l_stmt0.setString(8,v_tipov);            
        l_stmt0.setString(9,v_banco);
        l_stmt0.setString(10,v_numcuen);
        l_stmt0.setString(11,v_proti);
        l_stmt0.setString(12,v_conti);
        l_stmt0.setString(13,v_usuariopipe);
        l_stmt0.setString(14,v_afp);
        l_stmt0.setString(15,null);
        l_stmt0.setString(16,null);            
        l_stmt0.setString(17,null);                        
        l_stmt0.setString(19,null);
        l_stmt0.setString(20,null);
        l_stmt0.setString(21,null);            
        l_stmt0.execute();





        v_conret            = l_stmt0.getDouble(18);
        int v_indiretiro    = l_stmt0.getInt(22);
        String v_mensajeret = l_stmt0.getString(23);
        l_stmt0.close();
//        out.println( "longitud="+ v_afp.length() );
        
        try {  
              String v_esTercero =(java.lang.String)session.getAttribute("esTercero"); 
              String v_doc_ter="", v_tipodoc_ter="", v_nomb_terc="", v_apell_terc ="";
              if (v_esTercero.equals("S"))
              {
                  try { v_doc_ter = (java.lang.String)session.getAttribute("v_doc_ter"); } catch (Exception e) { e.printStackTrace(); }
                  try { v_tipodoc_ter = (java.lang.String)session.getAttribute("v_tipodoc_ter"); } catch (Exception e) { e.printStackTrace(); }
                  try { v_nomb_terc = (java.lang.String)session.getAttribute("v_nomb_terc"); } catch (Exception e) { e.printStackTrace(); }
                  try { v_apell_terc = (java.lang.String)session.getAttribute("v_apell_terc"); } catch (Exception e) { e.printStackTrace(); }
                  if ((!v_doc_ter.trim().equals("")&&!v_tipodoc_ter.trim().equals("")&&
                        !v_apell_terc.trim().equals(""))&& 
                             (v_numcuen== null || v_numcuen.trim().equals(""))
                        )
                   {
                     l_stmt0 = t_tax.prepareCall("{call TBPBD_InsRetirosTerceros(?,?,?,?,?,?,?,?,?)}");
                     l_stmt0.registerOutParameter(8,Types.INTEGER);
                     l_stmt0.registerOutParameter(9,Types.VARCHAR);
                     l_stmt0.setString(1,v_pro);
                     l_stmt0.setString(2,v_contra);
                     l_stmt0.setDouble(3,v_conret);
                     l_stmt0.setString(4,v_tipodoc_ter);
                     l_stmt0.setString(5,v_doc_ter);
                     l_stmt0.setString(6,v_nomb_terc);
                     l_stmt0.setString(7,v_apell_terc);
                     l_stmt0.execute();
                     int v_error = l_stmt0.getInt(8);
                     String v_mensajeretTer =l_stmt0.getString(9);
                     l_stmt0.close();
                   }
               } 
          }catch (Exception e)  { e.printStackTrace(); }
         
          /**Modificado 2009/10/27 Variable Id Saro*/
          try{
            if(!v_idsaro.trim().equals("")) {
                l_stmt0 = t_tax.prepareCall("{call TBPBD_InsRETIROSREAPLICADOS(?,?,?,?,?,?)}");
                l_stmt0.registerOutParameter(5,Types.INTEGER);
                l_stmt0.registerOutParameter(6,Types.VARCHAR);
                l_stmt0.setString(1,v_pro);
                l_stmt0.setString(2,v_contra);
                l_stmt0.setDouble(3,v_conret);
                l_stmt0.setString(4,v_idsaro);
                l_stmt0.execute();
                int v_error = l_stmt0.getInt(5);
                String v_mensajeretTer =l_stmt0.getString(6);
                l_stmt0.close();
            } 
          }catch (Exception e) { e.printStackTrace(); }
          
          /*out.println("TBPBD_GENERAR_NU_DEFAULT(" + v_pro + ",<br>"  
                                              + v_contra + ",<br>"
                                              + v_valor + ",<br>"  
                                              + v_fecefe2 + ",<br>"  
                                              + v_fecpro2 + ",<br>"  
                                              + v_tipo + ",<br>"  
                                              + v_clase + ",<br>"  
                                              + v_tipov + ",<br>"  
                                              + v_banco + ",<br>"  
                                              + v_numcuen + ",<br>"  
                                              + v_proti + ",<br>"  
                                              + v_conti + ",<br>"  
                                              + v_usuariopipe + ",<br>"  
                                              + v_afp + ",<br>"  
                                              + " -> " + v_indiretiro + ",<br>"  
                                              + " -> " + v_mensajeret); */


        if(v_conret!=0)
        {
          t_tax.commit();

          String v_pintar2=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Solicitud de Retiro Default","","<center>Solicitud de Retiro default"+Math.round(v_conret)+" realizada con exito.</center>",false);
          out.println(""+v_pintar2+"");
          String v_pie2 = i_pagina.TBFL_PIE;
          out.println("<br>");
          out.println("<br>");
          out.println("<center><input type=button value='Aceptar'  onclick='history.go(-7)'></center>");
          out.println(""+v_pie2+"");
          out.close();
              
        }
        else
        {
          t_tax.rollback();
          String v_pintar2=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>El siguiente error se presento en el calculo del retiro default: " + v_mensajeret  + "</center>",false);
          out.println(""+v_pintar2+"");
          String v_pie2 = i_pagina.TBFL_PIE;
          out.println("<br>");
          out.println("<br>");
          out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-7)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
          out.println(""+v_pie2+"");
          out.close();
        }
     }
    }
    else
    {
     t_tax.rollback();
     String v_pintarout=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro.","","<center>Error de comunicaci�n no se tiene conexi�n con el servidor web,por favor intente de nuevo.</center>",false);
     out.println(""+v_pintarout+"");
     String v_pieout = i_pagina.TBFL_PIE;
     out.println("<br>");
     out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-7)'></center>");
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
    v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>No se tiene comunicaci�n con el servidor de datos  por favor ingrese nuevamente.</center>",false);
   }
   else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
        {
         v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
        }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_pintar = i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error momentaneo de comunicaci�n con el servidor de datos, por favor intente entrar de nuevo a la opci�n.</center>",false);
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                       v_pintar = i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Error momentaneo de comunicaci�n con el servidor Web, por favor intente entrar de nuevo a la opci�n.</center>",false);
                     }
                 else
                  {
                   v_pintar=    i_pagina.TBFL_CABEZA("Solicitud de Retiro","Error al Calcular Solicitud de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                  }
   out.println(""+v_pintar+"");
   out.println("<BR>");
   out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-7)'><input type=button value='Regresar' onclick=' history.go(-1)'></center>");
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

