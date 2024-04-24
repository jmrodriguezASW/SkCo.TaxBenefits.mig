package TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTMLII;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_VALOR_UNIDAD_CC;
import TBPKT_UTILIDADES.TBPKT_FECHAS.*;
import java.text.NumberFormat;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;


public class TBCS_InformativoAporte extends HttpServlet{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
private String v_nuevaCadena ="";
HttpSession session  = null;
SQL_VALOR_UNIDAD_CC i_unidad =  new SQL_VALOR_UNIDAD_CC();/**Instancia de la clase TBCL_GenerarBaseHTML*/
double[] v_valuni2     = new double[3];/**Valor unidad*/

  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   * Process the HTTP Get request
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try{
       out = new PrintWriter (response.getOutputStream());
       session = request.getSession(true);
       if(session==null)
        session = request.getSession(true);
       session.setMaxInactiveInterval(3600);
       response.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
       //response.setHeader("Pragma", "No-Cache");
       //response.setDateHeader("Expires", 0);
       String parametros[] = new String[8];
       String  cadena = request.getParameter("cadena");
       v_nuevaCadena = cadena;
       String ip_tax = request.getRemoteAddr();
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

    v_Consulta = new TBCL_Consulta();
    String Consulta;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadoConsulta, resultadoConsulta1;
    TBCL_Fecha i_fecha             = new TBCL_Fecha();/**Instancia de la clase TBCL_Fecha*/
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


    //Toma el consecutivo
    String  consecutivo2 = "0";
    double  consecutivo = 0;
    String v_opcion = "";
    String v_codigo = "";
    String v_Nocontrato = "";
    String fechaefectiva = "";
    String fechaefectiva2 = "";
    String UfechaAct = "";

    try{
       consecutivo2 = request.getParameter("consecutivo");
       consecutivo = new Double(consecutivo2).doubleValue();
       v_codigo = request.getParameter("v_codigo");
       v_Nocontrato = request.getParameter("v_Nocontrato");
       v_opcion = request.getParameter("v_opcion");
       fechaefectiva = request.getParameter("fechaefectiva");
       fechaefectiva2 = i_fecha.TBFL_Fecha(fechaefectiva);
        }
    catch (Exception e) { e.printStackTrace(); }


  

    if (v_opcion.equals("historia"))
      {

      Consulta = "SELECT  to_char(trl_fecha, 'rrrr/mm/dd'), \n"+
                 "        to_char(trl_fecha_aporte, 'rrrr/mm/dd'), trl_cuenta_contingente, \n"+
                 "        trl_aporte_certificado, trl_capital, trl_rendimientos, \n"+
                 "        trl_ajuste, trl_razon_modificacion, trl_usuario \n"+
                 " FROM   tbtransaccion_logs \n"+
                 " WHERE  trl_tipo_transaccion ='A' \n"+
                 " AND    trl_aporte_retiro_producto = '"+consecutivo+"' \n"+
                 " ORDER BY trl_fecha, trl_linea ";
      v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);

      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      PublicarHistoria(v_resultadoConsulta);
      }
     else if (v_opcion.equals("composicion"))
      {
      Consulta ="SELECT  to_char(MAX(trl_fecha), 'rrrr-mm-dd')\n"+
                "  FROM  tbtransaccion_logs\n"+
                " WHERE  trl_tipo_transaccion = 'A'\n"+
                "   AND  trl_aporte_retiro_producto = '"+consecutivo+"'\n"+
                " ORDER BY   trl_fecha";
      UfechaAct = v_Consulta.TBFL_Consulta(Consulta).elementAt(0).toString();
      
      String v_programa = new String("");
      Connection t_tax  =   null;
      CallableStatement csP2    =   null;
      CallableStatement csP     =   null;
      /*Modificación hecha por APC para manejar el nuevo reglamento 2006-06-22*/          
      //se hace conexion a taxbenefit
      try
      {
        
        t_tax   =   DataSourceWrapper.getInstance().getConnection();

        csP2 = t_tax.prepareCall ( "{call TBPBD_Parametros_FuncionesAS(?,?,?,?)}");
        csP2.registerOutParameter(1,Types.VARCHAR);
        csP2.registerOutParameter(2,Types.VARCHAR);
        csP2.registerOutParameter(3,Types.VARCHAR);
        csP2.registerOutParameter(4,Types.VARCHAR);
        csP2.execute();

        
        String v_libreria= csP2.getString(1);
        String v_sistema = csP2.getString(2);
        String v_usumfund = csP2.getString(3);
        String v_passmfund = csP2.getString(4);
        //csP2.close();
        
        csP = t_tax.prepareCall ( "{? = call TBCL_FuncionesAs400.TBPL_ProgramaContrato(?,?,?,?,?)}");
        csP.registerOutParameter(1,Types.CHAR);
        csP.setString(2,v_Nocontrato);
        csP.setString(3,v_sistema);
        csP.setString(4,v_usumfund);
        csP.setString(5,v_passmfund);
        csP.setString(6,v_libreria);
        csP.executeUpdate();

        
        String v_retorno_programa = csP.getString(1);
        //csP.close();
        v_programa = v_retorno_programa.substring(0,v_retorno_programa.indexOf(";",0));
      }
      catch (Exception e) { out.println(e.toString()); }
      finally{
          try{
              //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
              DataSourceWrapper.closeStatement(csP);
              DataSourceWrapper.closeStatement(csP2);
              DataSourceWrapper.closeConnection(t_tax);
              
          }catch(SQLException sqlEx){
              out.println(sqlEx.toString());
          }
      }
      /*FIN Modificación hecha por APC para manejar el nuevo reglamento 2006-06-22*/          

      /* HIA 2012-03-13 Se agrega la tabla tbcomision_aporte al select para obtener el valor de la comisión de los aportes */
      Consulta =" SELECT \n"+
                " apo_transaccion,\n"+
                " afp_descripcion,\n"+
                " apo_saldo_capital,\n"+
                " apo_saldo_cuenta_contingente,\n"+
                " apo_aporte_certificado,\n"+
                " apo_aporte_beneficio,\n"+
                " to_char(apo_fecha_beneficio, 'rrrr-mm-dd'),\n"+
                " apo_razon_beneficio,\n"+
                " apo_saldo_numero_unidades,\n"+
                " emp_descripcion,\n"+
                " tb_ffiltro_penalizacion(apo_con_numero,apo_con_pro_codigo,apo_consecutivo,'" + v_programa + "'), \n"+
                " nvl(cma_valor,0) cma_valor \n"+
                " FROM      tbaportes,\n"+
                "           tbcomision_aporte,\n"+
                "           tbadm_fondos_pensiones,\n"+
                "           tbempresas\n"+
                " WHERE     apo_con_pro_codigo = '"+v_codigo+"'\n"+
                " AND       apo_con_numero= "+v_Nocontrato+"\n"+
                " AND       apo_consecutivo = cma_apo_consecutivo(+)\n"+
                " and       apo_consecutivo = "+consecutivo+"\n"+
                " AND       emp_grupo (+) = apo_emp_grupo\n"+
                " AND       afp_codigo (+) = apo_afp_codigo";

 //out.println("Consulta=<br>" +    Consulta + "<br>");
      v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);

      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      PublicarComposicion(v_resultadoConsulta, v_codigo, v_Nocontrato, fechaefectiva2, consecutivo, UfechaAct,v_programa);
      }
    else if (v_opcion.equals("retiros"))
      {
      Consulta = " SELECT to_char(ret_fecha_efectiva, 'RRRR-MM-DD'),\n"+
                 " apr_capital,\n"+
                 " apr_rendimientos + apr_rendimientos_p,\n"+
                 " NVL(TBFBD_CARGOS_RETIROS(RET_CON_PRO_CODIGO,  RET_CON_NUMERO, ret_consecutivo, apr_apo_consecutivo, 'STC002'),0)\n"+
                 " FROM tbretiros, \n"+
                 " tbaportes_retiros \n"+
                 " WHERE  ret_con_pro_codigo    = '"+v_codigo+"' \n"+
                 " AND ret_con_numero           = "+v_Nocontrato+" \n"+
                 " AND ret_con_pro_codigo       = apr_ret_con_pro_codigo \n"+
                 " AND ret_con_numero           = apr_ret_con_numero \n"+
                 " AND ret_consecutivo          = apr_ret_consecutivo \n"+
                 " AND (ret_ref_estado          = 'SEROO1' OR ret_ref_estado = 'SER006')\n"+
                 " and APR_APO_CONSECUTIVO      = "+consecutivo;

      v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);
      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      PublicarRetiros(v_resultadoConsulta);
      }
      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }


      private void PublicarHistoria(Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      //Aqui van los campo que se quieren mostrar
      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        {
        for (int i = 0; i<v_descripcion.size(); i++)
           if (v_descripcion.elementAt(i).toString().equals("null"))
              v_descripcion.setElementAt(" ", i);

        out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE.TBCS_HistoriaAporte", "", true));
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td11\" border=0 cellPadding=0 cellSpacing=0 width=\"100%\">\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Historia del aporte</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=6 rules=all>");
        out.println("<TR align=middle class=\"td11\" borderColor=silver><TD><FONT color=white face=verdana size=1><B>Fecha Cambio<B></font></TD><TD><FONT color=white face=verdana size=1><B>Fecha aporte</B></font></TD><TD><FONT color=white face=verdana size=1><B>Cuenta contingente</B></font></TD><TD><FONT color=white face=verdana size=1><B>Aporte<BR>certificado</B></font>");
        out.println("</TD><TD><FONT color=white face=verdana size=1><B>Capital</B></font></TD><TD><FONT color=white face=verdana size=1><B>Rendimiento</B></font></TD><TD><FONT color=white face=verdana size=1><B>Ajuste</B></font></TD><TD><FONT color=white face=verdana size=1><B>Razón modificación</B></font></TD><TD><FONT color=white face=verdana size=1><B>Usuario</B></font></TD></TR>");
        for (int i=0; i<v_descripcion.size(); i+=9)
           {
           out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+1).toString()+"</font>\n</TD><TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+2).toString())));
           out.println("</font>\n</TD><TD><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+3).toString()+"</font>\n</TD><TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+4).toString()))+"</font>\n</TD><TD><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(new Double(v_descripcion.elementAt(i+5).toString()))+"</font></TD>");
           out.println("</font>\n</TD><TD><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+6).toString()+"</font>\n</TD><TD><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+7).toString()+"</font>\n</TD><TD><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i+8).toString()+"</font></TD></TR>");
           }
        out.println("</TABLE>");
        }
      else
        {
        out.println(STBCL_GenerarBaseHTMLII.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "", v_descripcion.elementAt(0).toString(), false));
        }

        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        out.println(STBCL_GenerarBaseHTMLII.TBFL_PIE);
        out.close();
      }


      private void PublicarComposicion(Vector v_descripcion, String v_codigo, String v_Nocontrato, String fechaefectiva, double consecutivo, String UfechaAct,String v_programa)
      {
      v_descripcion.trimToSize();
      double ValorUnidad = 0;
      boolean imprimir = true;
      double SdoRen = 0;
      double vsalcapbrudis    = 0;
      double vsalctacondis    = 0;
      double vsalrenbrudis    = 0;
      double vrComision =0;


      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        {

        if( (java.lang.String)session.getAttribute("s_consultaas") == null)
        {
           v_valuni2 =i_unidad.TBF_CALCULO_VALOR_UNIDAD(TBFL_FechaMenosUno(),TBFL_FechaMenosUno(), v_Nocontrato, v_codigo, false,0);

           if (v_valuni2[2] == 0.0)
           {
             ValorUnidad = v_valuni2[0] ;
             //out.println("Valor unidad =" + ValorUnidad + "<br>");
             imprimir = true;
             session.removeAttribute("s_consultaas");
             session.setAttribute("s_consultaas",(java.lang.Object)new Double(v_valuni2[0]).toString());

           }
           else
           {
             SdoRen = 0;
             imprimir = true;
           }
        }
        else
        {

          ValorUnidad =  new Double((java.lang.String)session.getAttribute("s_consultaas")).doubleValue();
          imprimir = true;
         }
        
         Connection t_tax = null;
         CallableStatement v_clsm = null;
         try
         {
            t_tax   =   DataSourceWrapper.getInstance().getConnection();
            
            v_clsm = t_tax.prepareCall("{call TBPBD_SaldoAporte(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            v_clsm.setString(1,v_codigo);
            v_clsm.setString(2,v_Nocontrato);
            v_clsm.setDouble(3,consecutivo);
            v_clsm.setDouble(4,ValorUnidad);
            v_clsm.setString(5,"N");
            v_clsm.setString(6,v_programa);
            v_clsm.registerOutParameter(7,Types.DOUBLE);
            v_clsm.registerOutParameter(8,Types.DOUBLE);
            v_clsm.registerOutParameter(9,Types.DOUBLE);
            v_clsm.registerOutParameter(10,Types.DOUBLE);
            v_clsm.registerOutParameter(11,Types.DOUBLE);
            v_clsm.registerOutParameter(12,Types.DOUBLE);
            v_clsm.registerOutParameter(13,Types.DOUBLE);
            v_clsm.registerOutParameter(14,Types.DOUBLE);
            v_clsm.registerOutParameter(15,Types.DOUBLE);
            v_clsm.registerOutParameter(16,Types.DOUBLE);
            v_clsm.registerOutParameter(17,Types.DOUBLE);
            v_clsm.registerOutParameter(18,Types.INTEGER);
            v_clsm.registerOutParameter(19,Types.VARCHAR);
            v_clsm.execute();
            vsalcapbrudis    = v_clsm.getDouble(7);
            vsalctacondis    = v_clsm.getDouble(8);
            vsalrenbrudis    = v_clsm.getDouble(9);
       }
       catch (Exception e) { out.println(e.toString()); }
          finally{
              try{
                    DataSourceWrapper.closeStatement(v_clsm);
                    DataSourceWrapper.closeConnection(t_tax);
              }catch(SQLException sqlEx){
                  out.println(sqlEx.toString());
              }
          }


        for (int i = 0; i<v_descripcion.size(); i++)
           if (v_descripcion.elementAt(i).toString().equals("null"))
             v_descripcion.setElementAt(" ", i);
      
        
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de Aportes","Consulta de Aportes", "", "", true));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td11\" border=0 cellPadding=0 cellSpacing=0 width=\"100%\">\n"+
                    "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Composición Actual del Aporte</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de transacción Tax</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+Math.round(consecutivo)+"</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>No de transacción MULTIFUND</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>AFP origen</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Saldo Capital</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(vsalcapbrudis)+"</font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Saldo rendimientos</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+(imprimir?NumberFormat.getInstance().format(vsalrenbrudis):"Hubo algún error en el calculo")+"</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Saldo Cuenta Contingente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(vsalctacondis)+"</font></TD></TR>");  
        /* HIA 2012-03-13 Se muestra el valor de la comision sobre el aporte  */
        vrComision = Double.valueOf(v_descripcion.elementAt(11).toString()).doubleValue();
        if (vrComision < 0)
        {
            vrComision = vrComision * -1;
        }
        if (vrComision != 0)
        {
            out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Comisión Sobre Aportes</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>$&nbsp;"+NumberFormat.getInstance().format(vrComision)+"</font></TD></TR>");        
        }
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Certificado</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(5).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha Beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(6).toString()+"&nbsp;</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Razón Beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(7).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Aporte Penalizado</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(10).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Empresa que hace el aporte</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(9).toString()+"</font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Última fecha de actualización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+UfechaAct+"</font></TD></TR>");
        out.println("</TABLE>");
        }
      else
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "", v_descripcion.elementAt(0).toString(), false));
        }

      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



      private void PublicarRetiros(Vector v_descripcion)
      {
      double  Sumacapital = 0;
      double  Sumarendimientos = 0;
      double  Sumactacontingente = 0;

      v_descripcion.trimToSize();
      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        {

         for (int i = 0; i<v_descripcion.size(); i++)
            if (v_descripcion.elementAt(i).toString().equals("null"))
              v_descripcion.setElementAt(" ", i);

        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "TBPKT_INFORMATIVO.TBPKT_CONSULTAPORTE.TBCS_HistoriaAporte", "", true));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td11\" border=0 cellPadding=0 cellSpacing=0 width=\"100%\">\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Retiros que han afectado el aporte</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=6 rules=all width=\"100%\">");
        out.println("<TR align=middle class=\"td11\" borderColor=silver><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Capital</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>rendimientos</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Cuenta contingente</B></font></TD></TR>");
        for (int i=0; i<v_descripcion.size(); i+=4)
           {
           Sumacapital += (double)(new Double(v_descripcion.elementAt(i+1).toString())).doubleValue();
           Sumarendimientos += (double)(new Double(v_descripcion.elementAt(i+2).toString())).doubleValue();
           Sumactacontingente += (double)(new Double(v_descripcion.elementAt(i+3).toString())).doubleValue();
           out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(i).toString()+"</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;"+NumberFormat.getCurrencyInstance().format(new Double(v_descripcion.elementAt(i+1).toString()))+"</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;"+NumberFormat.getCurrencyInstance().format(new Double(v_descripcion.elementAt(i+2).toString())) );
           out.println("</font>\n</TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;"+NumberFormat.getCurrencyInstance().format(new Double(v_descripcion.elementAt(i+3).toString()))+"</font></TD></TR>");
           }
        out.println("<TR align=middle bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>TOTAL</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;"+NumberFormat.getCurrencyInstance().format(Sumacapital)+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;"+NumberFormat.getCurrencyInstance().format(Sumarendimientos)+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;"+NumberFormat.getCurrencyInstance().format(Sumactacontingente)+"</B></font></TD></TR>");
        out.println("</TABLE>");
        }
      else
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de aportes","Consulta de aportes", "", v_descripcion.elementAt(0).toString(), false));
        }
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


   private String TBFL_FechaMenosUno()
   {
   String retorno;
   //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
   retorno = v_Consulta.TBFL_Consulta("SELECT TO_CHAR(SYSDATE-1, 'YYYYMMDD') FROM DUAL").elementAt(0).toString();
   //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
   return retorno;
   }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONSULTAPORTE.TBCS_InformativoAporte Information";
  }
}



