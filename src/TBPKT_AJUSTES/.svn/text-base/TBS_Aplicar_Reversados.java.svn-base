package TBPKT_AJUSTES;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_MODULO_APORTES.*;

import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

import javax.servlet.http.*;
import javax.servlet.*;

//import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.*;

import java.text.*;
import java.sql.*;
import java.io.*;
import oracle.sql.*;


/**
*<font face='Verdana' size='2' color='#324395'>
* El submódulo de Aplicar  Reversados se debe tener en cuenta cuáles retiros desea
* el usuario que sean aplicados, no aplicados  ó sin decisión, para realizar su
* proceso correspondiente.
* En el caso de que el retiro sea aplicado:
* ·	Actualizar el estado del retiro.
* ·	Generar un nuevo Retiro (llamado 'Retiro Prima' )con Estado 'Pendiente por Tax'.
* ·	Registrar el Ajuste entre el Retiro Original y el Retiro Prima.
* En el caso de que el retiro no sea aplicado:
* ·	Actualizar simplemente el estado del retiro.
* En el caso de que el retiro no tenga decisión:
* ·	No se realiza ningún cambio.
* Si se ha generado ajuste habrá la posibilidad de tomar la decisión de una vez
* frente a ese ajuste generado.
* <p>
* @param    cadena  = CADENA ENCRIPTADA CON LA INFORMACION DEL APORTE <BR>
* @return   ninguno
*/

public class TBS_Aplicar_Reversados extends HttpServlet implements SingleThreadModel
{
  //DEFINICIONES INICIALES
  TBCL_LoadPage i_LP;
  SQL_AJUSTE i_sqlj;
  STBCL_GenerarBaseHTMLII codHtm;
  String msj_retiros_reversados_no_aplicados = new String("");
  STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
  boolean pintar_decision         = false;
  String msj_retiros_fallidos     = new String("");
  String msj_ajustes_realizados   = new String("");
  String msj_consecutivo_ajustes  = new String("");
  String msje_final               = new String("");
  TBCL_FndCmp i_fnd               = new TBCL_FndCmp();
  int consecAjus                  = 0;
  String cod_producto             = new String("");
  String cadena                   = new String("");
  String num_contrato             = new String("");
  String usuario                  = new String();
  String v_nombApel               = new String("");
  java.sql.Date vfefec            = new java.sql.Date(4);
  String k_cabeza                 = new String("");
  boolean v_showAjus              = false;
  String v_noGenerados            = new String("");
  HttpServletRequest request;
  HttpServletResponse response;
  HttpSession sess;
  PrintWriter out;
  String v_ajustes = "";
  //TBCL_Consulta v_Consulta;
////////////////////////////////inicialización del servlet//////////////////////////////////
public void init(ServletConfig config) throws ServletException
 {
   super.init(config);
 }
/////////////////////////////////////llamado principal//////////////////////////////////////
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    //INICIO DEL PROCESO DE APLICAR REVERSADOS PARA UN CONTRATO*PRODCUTO
    pintar_decision        = false;
    msj_retiros_reversados_no_aplicados = " ";
    msj_retiros_fallidos   = " ";
    msj_ajustes_realizados = " ";
    msje_final             = " ";
    cod_producto  = new String("");
    v_showAjus    = false;
    num_contrato  = new String("");
    usuario       = new String("");
    v_nombApel    = new String("");
    v_noGenerados = new String("");
    this.request  = request;
    this.response = response;
    i_LP          = new TBCL_LoadPage();
    i_fnd         = new TBCL_FndCmp();
    i_sqlj        = new SQL_AJUSTE();
    response.setContentType("text/html");
    out           = new PrintWriter (response.getOutputStream());
    sess          = request.getSession(true);
    sess.setMaxInactiveInterval(3600);
    cadena        = request.getParameter("cadena");
    k_cabeza      = codHtm.TBFL_CABEZA("Aplicar Reversados","Aplicar Reversados","TBPKT_AJUSTES.TBS_Aplicar_Reversados",
                                       " ",true,"moduloparametro.js","return checkrequired(this)");
    //tomar parámetros de entrada a la página

    TBPL_takeParameter1();
    TBPL_CleanSession();
    if(!cod_producto.equals("") && !num_contrato.equals(""))
    {
         if(sess.getValue("NOMBAPEL")==null)
          {
            //SI loS NOMBRES SON NULOS tomarlos de la base de datos
            TBPBD_ContrNomApel();
          }
         else
          {
            //si estan validos solo tomarlos
            v_nombApel = (String)sess.getValue("NOMBAPEL");
          }
         if(request.getParameter("obligatorio_razon")==null)
          {

            //si la razon es nula dibujo la página de salida #1
            sess.removeValue("VALRETREV");

            TBPL_BuildPage1();
          }
         else
          {

            //si la razon no es nula toma parametros del segundo doPost()
            if(TBPL_takeParameter2())
             {
               if(msj_ajustes_realizados.substring(0,1).equals(" "))
                 msj_ajustes_realizados = "TRANSACCION REALIZADA ";
               out.println(plantilla.TBFL_CABEZA("Aplicar Reversados","Aplicar Reversados"," ",msj_ajustes_realizados+msj_consecutivo_ajustes+msj_retiros_fallidos+msj_retiros_reversados_no_aplicados,false));
               out.println("<BR><BR>");

                  if(pintar_decision)
                    {
                      out.println("</form>");
                      out.println("<body >");
                      out.println("<table border='0' width='100%'>");
             		      out.println("<tr>");
             		      out.println("<form action='/Servlets/TBPKT_AJUSTES.TBPKT_DECISION_CLIENTE.TBS_DecisionRetiro' method='post' id='link23' name='link23'>");
          			      out.println("<input type='hidden' id='cadena' name='cadena' value='"+cadena+"'>");
          			      out.println("<td width='400'>");
           			      out.println("<center>");
              	      out.println("<input type='button' value='Aceptar' Onclick=history.go(-2);>");
            		      out.println("<INPUT TYPE=SUBMIT VALUE='Decision'>");
           			      out.println("<center>");
          			      out.println("</td>");
			                out.println("</form>");
			                out.println("</tr>");
    	                out.println("</table>");
                      out.println("</body>");
                      out.println("</center>");
                    }
               else
                 out.println("<center><input type='button' value='Aceptar' Onclick=history.go(-3);></center>");
               out.println(plantilla.TBFL_PIE);
               out.close();
             }//IF TBPL_takeParameter2
            else
             {
               out.println(plantilla.TBFL_CABEZA("Aplicar Reversados","Aplicar Reversados"," ","<CENTER><P>"+msje_final+"</P></CENTER>",false));
               out.println("<BR><BR>");
               out.println("<center><input type='button' value='Regresar' Onclick=history.go(-1);></center>");
               out.println(plantilla.TBFL_PIE);
               out.close();
             }//SINO TBPL_takeParameter2
        }//SI RAZON DIFERENTE DE NULL
    }
    
    i_sqlj.TBPBD_CerrarConexionBD();
}
///////////////////////Limpia Variables de Session que hay en memoria///////////////////////////////////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA Limpiar Variables de Session que hay en memoria
* <p>
* @param   NINGUNO<BR>
* @return  NINGUNO
*/
  private void TBPL_CleanSession()
  {
    if(sess.getValue("NOMBAPEL")!=null)      sess.removeValue("NOMBAPEL");
    if(sess.getValue("KEYS")!=null)          sess.removeValue("KEYS");
    //if(sess.getValue("VALRETREV")!=null)     sess.removeValue("VALRETREV");
    if(sess.getValue("MODIFICADOREV")!=null) sess.removeValue("MODIFICADOREV");
  

  }

//////////////////////////////Buscar todos los retiros con estado reversado////////////////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA Buscar todos los retiros con estado reversado
* <p>
* @param   NINGUNO<BR>
* @return  BOOLENAO
*/
private boolean TBPBD_findRev()
{
 if(i_sqlj.TBPBD_ConexionBD())
 {
   String valores = new String("");
   //llamado al procedimiento que me trae la información de los retiros reversados
   valores       = i_sqlj.TBPBD_SelAllRetRev(num_contrato,cod_producto,"SER007");
   if(valores.equals("<>") || valores.indexOf("Exception")!=-1)
    {
    /*
     String msje = "<p><font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><font size='1'>Para el contrato No"+num_contrato+" NO existen retiros reversados</b></font></p>";
     out.println(msje);
     out.println("<BR><BR>");
     out.println("<center><input type='button' value='Aceptar' Onclick=window.location='/taxbenefit/ajuste.html';></center>");
    */
     return false;
    }
   else
    {
     sess.removeValue("VALRETREV");
     sess.putValue("VALRETREV",valores);//colocar la información en una variable de sessión
     TBPL_showRev(valores);
     return true;
    }
  }
 else
  {
    return false;
  }
}
///////////////////Mostrar la información de cada uno de los retiros reversados/////////////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA Mostrar la información de cada uno de los retiros reversados
* <p>
* @param   valores  = STRING CON LA INFORMACION DE LOS RETIROS A REVERSAR<BR>
* @return  NINGUNO
*/
  private void TBPL_showRev(String valores)
  {
     String v_consec       = "";
     String v_fechafnd     = "";
     String v_consecMultif = new String("");
     String v_fechap       = "";
     String v_valor        = "";
     String v_fechaAct     = i_sqlj.TBPBD_FechaActual();
     int ii                = 1 ;
     while(true)
     {
      String v_ii = Integer.toString(ii);
      if(!i_fnd.TBPL_getCmp(valores,"fechae"+v_ii).equals(""))
      {
        v_consec       = i_fnd.TBPL_getCmp(valores,"const"+v_ii);
        v_consecMultif = i_fnd.TBPL_getCmp(valores,"multif"+v_ii);
        v_fechafnd     = i_fnd.TBPL_getCmp(valores,"fechae"+v_ii);
        v_fechap       = i_fnd.TBPL_getCmp(valores,"fechap"+v_ii);
        v_valor        = i_fnd.TBPL_getCmp(valores,"valor"+v_ii);
        i_LP.TBPL_ShowResultRev(out,v_valor,v_fechap,v_fechafnd,v_fechaAct,v_consec,v_consecMultif,v_ii);
      }
      else
      {
        break;
      }
     ii++;
   }
 }
//construcción de la página de salida para la toma de decisión para cada retiro reversado//
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA construiR la página de salida para la toma
*de decisión DE cada retiro reversado
* <p>
* @param   NINGUNO<BR>
* @return  NINGUNO
*/
  private void TBPL_BuildPage1()
  {
    boolean botonExe = false;
    boolean no_entro = true;
    i_LP.TBPL_Encabezado(out,k_cabeza,cod_producto,num_contrato,v_nombApel,"");
    //mostrar resultado
    out.println("<input type='hidden' id='cadena' name='cadena' value='"+cadena+"'>");
    out.println("<center><table bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 align='center' width='100%'>");
    out.println("<tr bgColor=white borderColor=silver ><td align='center' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Trans. Tax.</strong></font></td><td align='center' class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Trans.<br>MF</strong></font></td><td align='center' class=\"td11\"  style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong><center>&nbsp;&nbsp;&nbsp;&nbsp;Fecha&nbsp;&nbsp;&nbsp;&nbsp;Efectiva&nbsp;&nbsp;&nbsp;&nbsp;</center></strong></font></td><td align='center' class=\"td11\"  style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong><center>&nbsp;&nbsp;&nbsp;&nbsp;Fecha&nbsp;&nbsp;&nbsp;&nbsp;Proceso&nbsp;&nbsp;&nbsp;&nbsp;</center></strong></font></td>");
    out.println("<td align='center'  class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Valor</strong></font></td><td align='center'  class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong><center>&nbsp;&nbsp;&nbsp;&nbsp;Ajustar&nbsp;&nbsp;&nbsp;&nbsp;a&nbsp;&nbsp;&nbsp;&nbsp;Fecha&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</center></strong></font></td><td align='center'  class=\"td11\" style='border: thin solid'><font face='Verdana' size='1' color='FFFFFF'><strong>Aplicar</strong></font></td></tr>");
    //tomar la información de los retiros reversados de la variable de session si existe o va a la BD.
    if(sess.getValue("VALRETREV")!=null && sess.getValue("MODIFICADOREV")==null)
    {

      TBPL_showRev((String)sess.getValue("VALRETREV"));//mostrar los retiros reversados
      botonExe = true;
      no_entro = false;
    }
    else
    {

     //if(sess.getValue("MODIFICADOREV")!=null)
     sess.removeValue("MODIFICADOREV");
     sess.removeValue("VALRETREV");
     if(TBPBD_findRev())//buscar en la BD.
      {
       no_entro = false;
       botonExe = true;
      }
    }
    out.println("</table></center>");
      /*Cambio para manejo de referencia unica 2009/04/01 MOS */
      String v_contrato_unif = "";
      TBCL_Consulta v_Consulta = new TBCL_Consulta();
      v_Consulta.TBPL_RealizarConexion();
      v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(cod_producto,num_contrato);
      v_Consulta.TBPL_shutDown();
    if(no_entro)
      {
       //SI ERROR BUSCANDO RETIROS REVERSADOS
       String msje = "<FONT color='#324395' face=verdana size=1><strong>Para el contrato No "+v_contrato_unif+" NO existen retiros Reversados</strong></font>";
       out.println(msje);
       out.println("<BR><BR>");
       out.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
      }//SI ERROR BUSCANDO RETIROS REVERSADOS
    if(botonExe)
    {
      //mostrar razon
      out.println("<table border='1' width='100%'>");
      out.println("<tr ><td  align='left' valign='top'><font face='Verdana' size='1' color='#6F6F44'><strong>RAZON DEL AJUSTE:</strong><br><textarea rows='2' name='obligatorio_razon' cols='84'></textarea></font></td></tr>");
      out.println("</table>");
    }
    //boton regreso y aceptar
    out.println("<center><table border='0' align='center' ><tr>");
    if(botonExe)
     out.println("<center><input type='submit' value='Aceptar' ></center>");
    //out.println("<td  align='center'><input type='button' value='Regresar' ONCLICK=history.go(-1);></td>");
    out.println("</tr></table></center>");
    out.println(codHtm.TBFL_PIE);
    out.close();
  }
//////////////////////////////Tomar parámetros de entrada de PipeLine///////////////////////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA Tomar parámetros de entrada de PipeLine
* <p>
* @param   NINGUNO<BR>
* @return  NINGUNO
*/
  private void TBPL_takeParameter1()
  {
    cod_producto   = num_contrato = "";
    String v_keys  = new String("");



    TBCL_ParamEntr param = new TBCL_ParamEntr();
    v_keys               = param.TBCL_ParamEntr(request,sess,out);
    if(!v_keys.equals(""))
    {
      //TOMAR PARAMETROS, PRODUCTO;CONTRATO;USUARIO
      cod_producto = i_fnd.TBPL_getCmp(v_keys,"producto");
      num_contrato = i_fnd.TBPL_getCmp(v_keys,"contrato");
      usuario      = i_fnd.TBPL_getCmp(v_keys,"usuario");
    }
    else
      i_LP.TBPL_ErrParamEntr(out,k_cabeza,codHtm.TBFL_PIE);
 }
/////////////////////Tomar parámetros de las decisiones por cada retiro reversado///////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA TTomar parámetros de las decisiones por cada retiro reversado
* <p>
* @param   NINGUNO<BR>
* @return  BOOLEANO
*/
 private boolean TBPL_takeParameter2()
{
    Connection v_conexion_taxb = null;
try
 {
    String estado_cargos_ajustes = new String();
    String estado_aplicar      = new String();
    String v_fecharetiro       = new String();
    String estado_update       = new String();
    String accion              = new String();
    String v_retiro            = new String();
    String v_fecha_ajuste      = new String();
    int v_linea                = 0;
    v_conexion_taxb   =   DataSourceWrapper.getInstance().getConnection();
    String v_parametros  = new String("");
    String v_razon       = new String();
    String v_ii          = new String("1");
    String v_estado      = new String("SER011");
    boolean aplicar      = false;
    int ii = 0,linea     = 0;
    String v_ajustes = "";
    consecAjus           = 0;
    //calculo consecutivo del ajuste
    CallableStatement callNext = v_conexion_taxb.prepareCall("{? = call TBFBD_nextConsecAjus()}");
    callNext.registerOutParameter(1,Types.VARCHAR);
    callNext.execute();
    consecAjus = callNext.getInt(1);
    callNext.close();

    //calculo fecha actual
    String v_hoy                 = new String("");
    java.sql.Date hoy_date       = new java.sql.Date(4);
    CallableStatement t_cst8i_11 = v_conexion_taxb.prepareCall("{ ? = call TB_FFECHA_SIGUIENTE(-1) }");
    t_cst8i_11.registerOutParameter(1,Types.DATE);
    t_cst8i_11.executeUpdate();
    hoy_date                     = t_cst8i_11.getDate(1);
    t_cst8i_11.close();
    v_hoy                        = hoy_date.toString();
    java.sql.Date hoy = new java.sql.Date(4);/**Fecha de proceso del retiro*/

    String v_codusu      = (java.lang.String)sess.getValue("s_codigotipousuario");
    String v_coduni      = (java.lang.String)sess.getValue("s_codigounidad");
    String v_usuariopipeline = (java.lang.String)sess.getValue("s_usuariopipeline");
    String v_tipocierre  = "";
    String v_hora        ="";
    String v_horacierre  = "";
     
    if(!v_codusu.equals("XXXXXX")) //10
    {//if usuario
      /**Si la unidad de proceso es valida*/
      if(!v_coduni.equals("XXXXXX"))
      {//if unidad
       /**Consultar tipo de cierr para la unidad deproceso*/
       CallableStatement t_cst8i_d = v_conexion_taxb.prepareCall("{ ? = call TB_FTIPO_CIERRE(?) }");
       t_cst8i_d.registerOutParameter(1,Types.VARCHAR);
       t_cst8i_d.setString(2,v_coduni);
       t_cst8i_d.execute();
       v_tipocierre      = t_cst8i_d.getString(1);
       t_cst8i_d.close();

       if(v_tipocierre.equals("T"))
       { //if cierre
        /**Es cierre total entonces la fecha efectiva del retiro es proxima fecha habil*/
        CallableStatement t_cst8i_a = v_conexion_taxb.prepareCall("{ ? = call TB_FFECHA_SIGUIENTE(1) }");
        t_cst8i_a.registerOutParameter(1,Types.DATE);
        t_cst8i_a.execute();
        hoy     = t_cst8i_a.getDate(1);
        t_cst8i_a.close();


       }//cierre
       else
       {
        /**Si el cierre es parcial o aun no se ha hecho ningun tipo de cierre para el dia de hoy
        * se averigua que hora de cierre hay para la unidad y tipo de usuario*/
        String  v_consultahora = " select to_char(sysdate,'HH24MI') FROM DUAL ";
        Statement tst = v_conexion_taxb.createStatement();
        ResultSet rs1 = tst.executeQuery(v_consultahora);
        if(rs1.next())
        {
            v_hora = rs1.getString(1);
        }
        rs1.close();
        tst.close();

        CallableStatement t_cst8i_b = v_conexion_taxb.prepareCall("{ ? = call TB_FHORA_CIERRE(?,?,?) }");
        t_cst8i_b.registerOutParameter(1,Types.VARCHAR);
        t_cst8i_b.setString(2,cod_producto);
        t_cst8i_b.setString(3,v_coduni);
        t_cst8i_b.setString(4,v_codusu);
        t_cst8i_b.execute();
        v_horacierre  = t_cst8i_b.getString(1);
        t_cst8i_b.close();

        if(!v_horacierre.equals("ERROR"))
        {
          if( new Integer(v_hora).intValue() > new Integer(v_horacierre).intValue())
          {
           CallableStatement t_cst8i_c = v_conexion_taxb.prepareCall("{ ? = call TB_FFECHA_SIGUIENTE(1) }");
           t_cst8i_c.registerOutParameter(1,Types.DATE);
           t_cst8i_c.execute();
           hoy     = t_cst8i_c.getDate(1);
           t_cst8i_c.close();

          }
          else
          {
           /**Queda el dia habil*/
           CallableStatement t_cst8i_c = v_conexion_taxb.prepareCall("{ ? = call TB_FFECHA_SIGUIENTE(0)}");
           t_cst8i_c.registerOutParameter(1,Types.DATE);
           t_cst8i_c.execute();
           hoy     = t_cst8i_c.getDate(1);
           t_cst8i_c.close();

          }
        }
        else
        {
          msje_final = "No esta parametrizada la hora de cierre para el tipo de usuario "+v_codusu+"";
          return false;
        }
       }
      }
      else
      {
        msje_final = "La unidad de proceso no es valida";
        return false;
      }
     }
     else //10
     {
       msje_final = "El tipo de usuario no es valido";
       return false;
     }

     //fin calculo fecha actual
    //captura de cadena de parametros
    if((String)sess.getValue("VALRETREV")!=null)
    {
      v_parametros = (String)sess.getValue("VALRETREV");
      v_razon      = request.getParameter("obligatorio_razon");
      if(v_razon.length()>100) v_razon = v_razon.substring(0,100);
    }
    else
    {
      msje_final = "SE PRODUJO UN ERORR CON LOS PARAMETROS DE LA SESSION, INTENTE DE NUEVO";
      return false;
    }
     //fin de captura de cadena de parametros
    //CAPTURA DE RETIRO POR RETIRO; CON EL FIN DE REAPLICARLOS
    while(true)
    {
      ii++;
      v_ii     = Integer.toString(ii);
      if(request.getParameter("fecAp"+v_ii)==null)break;
      v_fecha_ajuste = request.getParameter("fecAp"+v_ii);
      v_retiro = i_fnd.TBPL_getCmp(v_parametros,"const"+v_ii);
      accion   = request.getParameter("apr"+v_ii);
      if(ii==1)
      {
        //calculo TRL_linea
        CallableStatement callLinea = v_conexion_taxb.prepareCall("{call TBPBD_TRL_Linea(?,?,?)}");
        callLinea.registerOutParameter(3,Types.NUMERIC);
        callLinea.setInt(1,Integer.parseInt(v_retiro));
        callLinea.setString(2,"R");
        callLinea.execute();
        v_linea = callLinea.getInt(3);
        callLinea.close();
      }


      String v_retestado = "";
      //fin calculo de la linea
      if(!accion.equalsIgnoreCase("SD"))
      {
         String v_estadoretiro = "SELECT RET_REF_ESTADO  "+
                              "   FROM TBRETIROS  "+
                              "  WHERE RET_CON_PRO_CODIGO = '"+cod_producto+"'  "+
                              "    AND RET_CON_NUMERO     = '"+num_contrato+"'  "+
                              "    AND RET_CONSECUTIVO    = '"+Integer.parseInt(v_retiro)+"' ";
         Statement t_stestado = v_conexion_taxb.createStatement();
         ResultSet  t_rsestado = t_stestado.executeQuery(v_estadoretiro);
         if(t_rsestado.next())
         {
           v_retestado = t_rsestado.getString(1);
         }
         t_rsestado.close();
          t_stestado.close();

        if(v_retestado.equals("SER011")||v_retestado.equals("SER010"))
        {
            msje_final = "EL RETIRO YA FUE REAPLICADO.";
            return false;
        }

        //ACTUALIZO ESTADO DEL RETIRO
        if(accion.equalsIgnoreCase("N")){ v_estado = "SER010"; }
        CallableStatement update_estado = v_conexion_taxb.prepareCall("{call TBPBD_Update_EstadoR(?,?,?,?,?)}");
        update_estado.registerOutParameter(5,Types.VARCHAR);
        update_estado.setString(1,cod_producto);
        update_estado.setString(2,num_contrato);
        update_estado.setInt(3,Integer.parseInt(v_retiro));
        update_estado.setString(4,v_estado);
        update_estado.execute();
        estado_update = update_estado.getString(5);
        update_estado.close();
        if(!estado_update.equalsIgnoreCase("BIEN"))
         {
            msj_retiros_fallidos += " LA ACTUALIZACION DEL RETIRO "+v_retiro+" FALLO. ERROR: "+estado_update+"<BR> ";
         }
        if(accion.equalsIgnoreCase("N"))
         {
           msj_retiros_reversados_no_aplicados += " EL RETIRO REVERSADO No"+v_retiro+" NO FUE APLICADO<BR>";
         }
      }//if(!accion.equalsIgnoreCase("SD"))

      if(accion.equalsIgnoreCase("Y"))
      {
         //DEBO APLICAR
        //VALIDO LLAMADO AL VALOR DE LA UNIDAD--fechas
        double valor_unidad_final = 0;
        v_fecharetiro             = i_fnd.TBPL_getCmp(v_parametros,"fechae"+v_ii);
        if(v_fecha_ajuste.equalsIgnoreCase(v_hoy))
         {
          try
            {
              //calcular valor unidad
              TBS_Modificar_Retiros    f_as      = new TBS_Modificar_Retiros();

/* Modificacion:
   Se elimina la linea que instancia a SQL_CALCULO_VALOR_UNIDAD ya que se va
   a invocar el metodo en la base de datos
*/

//            SQL_CALCULO_VALOR_UNIDAD i_valUnid = new SQL_CALCULO_VALOR_UNIDAD();

       double matUnidad                 = 0;

//              matUnidad                          = i_valUnid.TBF_CALCULO_VALOR_UNIDAD(f_as.Fecha_as400(v_hoy),f_as.Fecha_as400(v_hoy),num_contrato,cod_producto,false,0);

//              valor_unidad_final                 = matUnidad[0];

       try
       {
       CallableStatement cs = v_conexion_taxb.prepareCall("{? = call SQL_CALCULO_VALOR_UNIDAD.TBF_CALCULO_VALOR_UNIDAD_N(?,?,?,?,?,?,0)}");
       cs.registerOutParameter(1,Types.DOUBLE);
       cs.setString(2,f_as.Fecha_as400(v_hoy));
       cs.setString(3,f_as.Fecha_as400(v_hoy));
       cs.setString(4,num_contrato);
       cs.setString(5,cod_producto);
       cs.setString(6,"false");
       cs.setInt(7,0);
       cs.executeUpdate();
       matUnidad = cs.getDouble(1);
       cs.close();
       }
       catch(Exception E)
       {
        System.out.println("Error llamando a SQL_CALCULO_VALOR_UNIDAD.TBF_CALCULO_VALOR_UNIDAD_N: " + E);
       }
       valor_unidad_final                 = matUnidad;
     }

/* Final de Modificacion */

     catch(Exception ex)
     {
             msje_final = "ERROR EN EL LLAMADO A LA FUNCION DEL CALCULO DEL VALOR DE LA UNIDAD";
             return false;
     }
         }//if(v_fecha_ajuste.equalsIgnoreCase(v_hoy))
        else
         {
          //el valor de la unidad sera la del retiro
          String v_u_retiro = new String(i_fnd.TBPL_getCmp(v_parametros,"v_vu"+v_ii));
          Double a          = new Double(v_u_retiro);
          valor_unidad_final = a.doubleValue();
        }
        //fin VALIDacion LLAMADO AL VALOR DE LA UNIDAD--fechas
       //INICIO DE PROCESO DE APLICAR
       double val_neto  = 0;
       String p         = i_fnd.TBPL_getCmp(v_parametros,"valneto"+v_ii);
       Double Val_neto  = new Double(p);
       val_neto         = Val_neto.doubleValue();
        //Declaro variables para ix del retiro prima
       //declaro variables String() para mantener esquema
       String m_o = new String(" ");    String m_b = new String(" ");
       String m_p = new String(" ");    String m_c = new String(" ");
       String nat = new String(" ");    String r_n = new String(" ");
       java.sql.Date vfefec  = new java.sql.Date(4);   java.sql.Date vfecproc= new java.sql.Date(4);
       String vtiptran       = "";                     String vclastran      = "";String vtipval="";
       String vretpen        = "";String vretpro="";   String vuser          = "";
       String vunidproc      = "";                     String vbanco         = "";
       String vcuenta        = "";                     String vtransaccion   = "";
       String vcodigoafp     = "";                     String vcodti         = "";
       String vnumti         = "";                     String vretcargue     = "";
       String msgErr         = "";                     int v_codErr          = 0;
       double vvalor = 0,vvalunid = 0;
       //Capturo Ix del Retiro Original
       CallableStatement callInfRet = v_conexion_taxb.prepareCall("{call TBPBD_SelInfRetiro(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
       callInfRet.setString(1,cod_producto);   callInfRet.setString(2,num_contrato);
       callInfRet.setInt(3,Integer.parseInt(v_retiro));
       callInfRet.registerOutParameter(4,Types.DATE);     callInfRet.registerOutParameter(5,Types.DATE);
       callInfRet.registerOutParameter(6,Types.VARCHAR);  callInfRet.registerOutParameter(7,Types.VARCHAR);
       callInfRet.registerOutParameter(8,Types.NUMERIC);  callInfRet.registerOutParameter(9,Types.VARCHAR);
       callInfRet.registerOutParameter(10,Types.NUMERIC); callInfRet.registerOutParameter(11,Types.VARCHAR);
       callInfRet.registerOutParameter(12,Types.VARCHAR); callInfRet.registerOutParameter(13,Types.VARCHAR);
       callInfRet.registerOutParameter(14,Types.VARCHAR); callInfRet.registerOutParameter(15,Types.VARCHAR);
       callInfRet.registerOutParameter(16,Types.VARCHAR); callInfRet.registerOutParameter(17,Types.VARCHAR);
       callInfRet.registerOutParameter(18,Types.VARCHAR); callInfRet.registerOutParameter(19,Types.VARCHAR);
       callInfRet.registerOutParameter(20,Types.VARCHAR); callInfRet.registerOutParameter(21,Types.VARCHAR);
       callInfRet.registerOutParameter(22,Types.VARCHAR); callInfRet.registerOutParameter(23,Types.VARCHAR);
       callInfRet.registerOutParameter(24,Types.VARCHAR); callInfRet.registerOutParameter(25,Types.VARCHAR);
       callInfRet.registerOutParameter(26,Types.VARCHAR); callInfRet.registerOutParameter(27,Types.VARCHAR);
       callInfRet.registerOutParameter(28,Types.NUMERIC); callInfRet.registerOutParameter(29,Types.VARCHAR);
       callInfRet.execute();
       vfefec     = callInfRet.getDate(4);     vfecproc       = callInfRet.getDate(5);
       vtiptran   = callInfRet.getString(6);   vclastran      = callInfRet.getString(7);
       vvalor     = callInfRet.getDouble(8);   vtipval        = "STV001";
       vvalunid   = callInfRet.getDouble(10);  vretpen        = callInfRet.getString(11);
       vretpro    = callInfRet.getString(12);  vuser          = callInfRet.getString(13);
       vunidproc  = callInfRet.getString(14);  vbanco         = callInfRet.getString(15);
       vcuenta    = callInfRet.getString(16);  vtransaccion   = callInfRet.getString(17);
       vcodigoafp = callInfRet.getString(18);  vcodti         = callInfRet.getString(19);
       vnumti     = callInfRet.getString(20);  m_o            = callInfRet.getString(21);
       m_b        = callInfRet.getString(22);  m_p            = callInfRet.getString(23);
       m_c        = callInfRet.getString(24);  nat            = callInfRet.getString(25);
       r_n        = callInfRet.getString(26);  vretcargue     = callInfRet.getString(27);
       v_codErr   = callInfRet.getInt(28);     msgErr         = callInfRet.getString(29);
       callInfRet.close();
       //VALIDO ESQUEMA DEL CONTRATO
       try
       {
         if(m_o.equals(null)||m_b.equals(null)||m_p.equals(null)||m_c.equals(null)||nat.equals(null)||r_n.equals(null))
           { m_o = null;   m_b = null; m_p = null;  m_c = null;  nat = null; r_n = null;}
       }//try
       catch(Exception ex)
          { m_o = null;   m_b = null; m_p = null;  m_c = null;  nat = null; r_n = null;}//catch
       //Chequeo Penalización del Retiro
       if(vretcargue.equalsIgnoreCase("S"))  vretpen = "N";
       if(vretcargue.equalsIgnoreCase("N"))  vretpen = "S";
       //calculo fecha de proceso
       /*DateFormat currentDateFormat;
       java.util.Date currentDate = new java.util.Date();
       currentDateFormat          = DateFormat.getDateInstance(DateFormat.DEFAULT, java.util.Locale.PRC);
       String d                   = currentDateFormat.format(currentDate);
       int ano                    = Integer.parseInt(d.substring(0,4))-1900;
       int mes                    = Integer.parseInt(d.substring(5,d.lastIndexOf("-")))-1;
       int dia                    = Integer.parseInt(d.substring(d.lastIndexOf("-")+1,d.length()));
       java.sql.Date hoy          = new java.sql.Date(ano,mes,dia);*/


        /*Modificación hecha por APC para manejar el nuevo reglamento 2006-06-22*/          
        CallableStatement csP2 = v_conexion_taxb.prepareCall ( "{call TBPBD_Parametros_FuncionesAS(?,?,?,?)}");
        csP2.registerOutParameter(1,Types.VARCHAR);
        csP2.registerOutParameter(2,Types.VARCHAR);
        csP2.registerOutParameter(3,Types.VARCHAR);
        csP2.registerOutParameter(4,Types.VARCHAR);
        csP2.execute();
        
        
        String v_libreria= csP2.getString(1);
        String v_sistema = csP2.getString(2);
        String v_usumfund = csP2.getString(3);
        String v_passmfund = csP2.getString(4);
        csP2.close();
        

        String v_retorno_programa = new String("");
        String v_programa;
        CallableStatement cs2 = v_conexion_taxb.prepareCall ( "{? = call TBCL_FuncionesAs400.TBPL_ProgramaContrato(?,?,?,?,?)}");
        cs2.registerOutParameter(1,Types.CHAR);
        cs2.setString(2,num_contrato);
        cs2.setString(3,v_sistema);
        cs2.setString(4,v_usumfund);
        cs2.setString(5,v_passmfund);
        cs2.setString(6,v_libreria);
        cs2.executeUpdate();
        v_retorno_programa = cs2.getString(1);
        cs2.close();
        v_programa = v_retorno_programa.substring(0,v_retorno_programa.indexOf(";",0));
        /*FIN Modificación hecha por APC para manejar el nuevo reglamento 2006-06-22*/          


       //LLAMADO AL GENERADOR DE RETIROS
       CallableStatement l_stmt1 = v_conexion_taxb.prepareCall("{call TBPBD_RETIRO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
       l_stmt1.registerOutParameter(31,Types.VARCHAR);   
       l_stmt1.registerOutParameter(32,Types.VARCHAR);
       l_stmt1.registerOutParameter(33,Types.INTEGER);   
       l_stmt1.registerOutParameter(34,Types.DOUBLE);
       l_stmt1.registerOutParameter(35,Types.INTEGER);   
       l_stmt1.registerOutParameter(36,Types.VARCHAR);

       /*
       * MOS Se quita temporalmente :v_programa
       * Se agrega nuevamente
       */
       l_stmt1.setString(1,cod_producto);   
       l_stmt1.setString(2,num_contrato);
       l_stmt1.setString(3,v_programa);
       l_stmt1.setString(4,null);           
       l_stmt1.setDate(5,hoy);
       l_stmt1.setDate(6,vfefec);           
       l_stmt1.setString(7,vtiptran);
       l_stmt1.setString(8,vclastran);      
       l_stmt1.setDouble(9,vvalor);
       l_stmt1.setString(10,vtipval);        
       l_stmt1.setDouble(11,valor_unidad_final);
       l_stmt1.setString(12,vretpen);       
       l_stmt1.setString(13,vretpro);
       l_stmt1.setString(14,vbanco);        
       l_stmt1.setString(15,vcuenta);
       // A solicitud de AMM Caso (904) Se debe enviar la transaccion en NULL
       // l_stmt1.setString(16,vtransaccion);
       l_stmt1.setString(16,null);
       l_stmt1.setString(17,v_usuariopipeline);
       l_stmt1.setString(18,v_coduni);     
       l_stmt1.setString(19,"SER001");
       l_stmt1.setString(20,vcodigoafp);    
       l_stmt1.setString(21,vcodti);
       l_stmt1.setString(22,vnumti);        
       l_stmt1.setString(23,m_o);
       l_stmt1.setString(24,m_b);           
       l_stmt1.setString(25,m_p);
       l_stmt1.setString(26,m_c);           
       l_stmt1.setString(27,nat);
       l_stmt1.setString(28,r_n);           
       l_stmt1.setString(29,"S");
       l_stmt1.setString(30,"N");
       l_stmt1.execute();
       String v_msj_error  = new String(" ");
       String v_pro        = l_stmt1.getString(31);   
       String v_contra     = l_stmt1.getString(32);
       int v_conret        = l_stmt1.getInt(33);      
       double v_valnetret  = l_stmt1.getDouble(34);
       int v_cod_error     = l_stmt1.getInt(35);      
       v_msj_error  = l_stmt1.getString(36);
       l_stmt1.close();
       msj_consecutivo_ajustes = " ";
       if(!v_msj_error.equalsIgnoreCase("OK"))
        {
         msj_retiros_fallidos += " EL RETIRO No"+v_retiro+" NO PUDO SER REAPLICADO, EN EL PROCESO DEL RETIRO PRIMA SE PRODUJO "+
         "UN ERROR, NO SE PUDO APLICAR EL RETIRO. NO SE PUDO GENERAR EL RETIRO AJUSTADO, NO EXISTE DISPONIBILIDAD EN APORTES "+
         "PARA LAS OPCIONES ELEGIDAS. <BR>";
        }
       //continuo con proceso de ajuste si hubo retiro prima
       if(v_msj_error.equalsIgnoreCase("OK"))
       {
        //INSERTAR DISTRIBUCION DE FONDOS PARA EL NUEVO RETIRO
        CallableStatement t_cst8i_dis = v_conexion_taxb.prepareCall("{ ? = call TB_FDISFON_RETREV(?,?,?,?,?) }");
        t_cst8i_dis.registerOutParameter(1,Types.INTEGER);
        t_cst8i_dis.registerOutParameter(6,Types.VARCHAR);
        t_cst8i_dis.setString(2,cod_producto);
        t_cst8i_dis.setString(3,num_contrato);
        t_cst8i_dis.setInt(4,v_conret);
        t_cst8i_dis.setInt(5,Integer.parseInt(v_retiro));
        t_cst8i_dis.execute();
        String v_msj_error_dis = t_cst8i_dis.getString(6);
        t_cst8i_dis.close();

        if(!v_msj_error_dis.equalsIgnoreCase("OK"))
         break;
        else if(v_msj_error_dis.equalsIgnoreCase("OK"))
        {
         //CALCULO AJUSTE
         double val_difNetos = val_neto - v_valnetret;
         //CHEQUEO TRANSACCION
         String v_tipTrans=new String("");
         if((val_difNetos * -1)<0)  v_tipTrans = "UTT009";
         else                 v_tipTrans = "UTT008";
         //INSERTO AJUSTE
         CallableStatement t_cst8i_91 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Ajuste(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
         t_cst8i_91.registerOutParameter(16,Types.VARCHAR);
         t_cst8i_91.setString(1,cod_producto);          t_cst8i_91.setString(2,num_contrato);
         t_cst8i_91.setInt(3,consecAjus);               t_cst8i_91.setInt(4,1);
         t_cst8i_91.setString(5,hoy.toString());        t_cst8i_91.setDate(6,vfecproc);
         t_cst8i_91.setString(7,"STR003");              t_cst8i_91.setString(8,v_tipTrans);
         t_cst8i_91.setString(9,"UCT001");              t_cst8i_91.setDouble(10,(val_difNetos*-1));
         t_cst8i_91.setString(11,v_razon);              t_cst8i_91.setString(12,usuario);
         t_cst8i_91.setDouble(13,valor_unidad_final);   t_cst8i_91.setInt(14,Integer.parseInt(v_retiro));
         t_cst8i_91.setInt(15,v_conret);                t_cst8i_91.execute();
         estado_aplicar = t_cst8i_91.getString(16);
         t_cst8i_91.close();
         if(!estado_aplicar.equalsIgnoreCase("BIEN"))
          {
            msj_retiros_fallidos += " EL RETIRO No"+v_retiro+" NO PUDO SER REAPLICADO, ERROR EN INSERCCION DE AJUSTES: "+estado_aplicar+". <BR>";
          }
         else
         {
            v_ajustes = v_ajustes+", "+consecAjus;
           msj_consecutivo_ajustes = "SE HA GENERADO EL AJUSTE No"+v_ajustes+"  SATISFACTORIAMENTE.";
         }

         CallableStatement t_cst8i_10 = v_conexion_taxb.prepareCall("{ call TBPBD_Inserta_Cargos_Ajustes(?,?,?,?,?,?,?) }");
         t_cst8i_10.registerOutParameter(7,Types.VARCHAR);
         t_cst8i_10.setString(1,cod_producto);                       t_cst8i_10.setString(2,num_contrato);
         t_cst8i_10.setInt(3,Integer.parseInt(v_retiro));            t_cst8i_10.setInt(4,v_conret);
         t_cst8i_10.setInt(5,consecAjus);                            t_cst8i_10.setInt(6,1);
         t_cst8i_10.execute();
         estado_cargos_ajustes = t_cst8i_10.getString(7);
         t_cst8i_10.close();
         if(!estado_cargos_ajustes.equalsIgnoreCase("BIEN"))
          {
            msj_retiros_fallidos += " EL RETIRO No"+v_retiro+" NO PUDO SER REAPLICADO, ERROR EN INSERCCION DE CARGOS AJUSTES: "+estado_cargos_ajustes+". <BR>";
          }
        }// si continuo con proceso de ajuste si hubo retiro prima -insertar disribucion fondos
       }//si continuo con proceso de ajuste si hubo retiro prima
       //valido realización de commit o rollback
       if(v_msj_error.equalsIgnoreCase("OK")&&estado_aplicar.equalsIgnoreCase("BIEN")&&estado_cargos_ajustes.equalsIgnoreCase("BIEN"))
        {
          CallableStatement t_cst8i_2 = v_conexion_taxb.prepareCall("{ call TBPBD_TransaccionLog(?,?,?,?,?,?,?) }");
          t_cst8i_2.registerOutParameter(7,Types.VARCHAR);
          t_cst8i_2.setString(1,"R");
          t_cst8i_2.setInt(2,Integer.parseInt(v_retiro));
          t_cst8i_2.setInt(3,v_linea);
          t_cst8i_2.setString(4,"A");
          t_cst8i_2.setString(5,"Reaplicado Por TaxBenefits");
          t_cst8i_2.setString(6,usuario);
          t_cst8i_2.execute();
          String str_confir  = t_cst8i_2.getString(7);
          t_cst8i_2.close();
          v_conexion_taxb.commit();
          msj_ajustes_realizados  += " <BR >SE HA GENERADO EL AJUSTE No"+v_ajustes+"  SATISFACTORIAMENTE. <BR>";
          v_linea++;
          consecAjus++;
          pintar_decision = true;
        }//proceso de Reaplicacion satisfactorio
       else
        {
          v_conexion_taxb.rollback();
        }//proceso de Reaplicacion insatisfactorio
       //fin validez realización de commit o rollback
      }//if(accion.equalsIgnoreCase("Y"))

    }//while true
sess.removeValue("MODIFICADOREV");
sess.putValue("MODIFICADOREV","YES");
return true;
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


 msje_final = "ERROR REAPLICANDO, "+v_menex;
 return false;
 }
    finally{
        try{
            DataSourceWrapper.closeConnection(v_conexion_taxb);            
        }catch(SQLException sqlEx){
            out.println(sqlEx.toString());
        }
    }
}
/////////////////////////////////Traer nombres y apellidos////////////////////////////////////
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA Traer nombres y apellidos DE UN CONTRATO DETERMINADO
* <p>
* @param   NINGUNO<BR>
* @return  NINGUNO
*/
  private void TBPBD_ContrNomApel()
   {
    TBCL_HTML nombres = new TBCL_HTML();
    v_nombApel = nombres.TBPL_Nombres(cod_producto,num_contrato);
    sess.removeValue("NOMBAPEL");
    sess.putValue("NOMBAPEL",v_nombApel);
   }
///////////////////////////////////////////////////////////////////////////////////////////////////
  public String getServletInfo()
   {
    return "TBPKT_AJUSTES.TBS_Aplicar_Reversados Information";
  }

}

