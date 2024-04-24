package TBPKT_PARAMETROS.TBPKT_PRODUCTO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108


public class TBCS_ModificaProducto extends HttpServlet{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
private String v_nuevaCadena ="";

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
      //INT20131108
      String rutaFisica = TBCL_REFERENCIAS.TBCL_CONSULTA("SMM003");
      //rutaFisica  =  "c:\\Taxbenefits\\Taxbenefits\\public_html\\WEB-INF\\";
      String POLICY_FILE_LOCATION = rutaFisica+"antisamy-tinymce-1.4.4.xml"; // Path to policy file
      AntiSamy as = new AntiSamy(); // INT20131108
  String parametros[] = new String[8];
    try{
       out = new PrintWriter (response.getOutputStream());
       response.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
       //response.setHeader("Pragma", "No-Cache");
       //response.setDateHeader("Expires", 0);
       String  cadena = request.getParameter("cadena");
       v_nuevaCadena = cadena;
       String ip_tax = request.getRemoteAddr();
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

  v_Consulta = new TBCL_Consulta();
  String v_declaracion = new String();
  Vector v_resultadodeclaracion = new Vector();
  Vector v_resultadodeclaracion1 = new Vector();
  Vector v_resultadodeclaracion2 = new Vector();
  Vector v_descripcion1 = new Vector();
  v_Consulta.TBPL_RealizarConexion();

    //Toma el código del producto
    String v_codigo = "";
    String v_descripcion = "";
    String numide = "";
    String digver = "";
    String tipoide = "";
    String metord = "";
    String metben = "";
    String metpen = "";
    String metcue = "";
    String natret = "";
    String cantie = "";
    String unitie = "";
    String fecben = "";
    String maxctacon = "";
    String cominf = "";
    String retfue = "";
    String porcomret = "";
    String retmin = "";
    String diacanje = "";
    String idecont = "";
    String razmod = "";
    String v_opcion = "";

    String dcantie = "";
    String dfecben = "";
    String dmetord = "";
    String dmetben = "";
    String dmetpen = "";
    String dmetcue = "";
    String dnatret = "";
    String dmaxctacon = "";
    String dcominf = "";
    String dretfue = "";
    String dporcomret = "";    
    String dretmin = "";

    /*try{
       v_codigo = request.getParameter("obligatoriocodigo");
       v_descripcion = request.getParameter("obligatoriodescripcion");
       numide = request.getParameter("obligatorionumide");
       digver = request.getParameter("obligatoriodigver");
       tipoide = request.getParameter("obligatoriotipoide");
       for (int i=0; i < request.getParameterValues("obligatoriometord").length; i++)
          metord += request.getParameterValues("obligatoriometord")[i];
       metben = request.getParameter("obligatoriometben");
       metpen = request.getParameter("obligatoriometpen");
       metcue = request.getParameter("obligatoriometcue");
       for (int i=0; i < request.getParameterValues("obligatorionatret").length; i++)
          natret += request.getParameterValues("obligatorionatret")[i];
       cantie = request.getParameter("obligatoriocantie");
       unitie = request.getParameter("unitie");
       fecben = request.getParameter("obligatoriofecben");
       maxctacon = request.getParameter("maxctacon");
       cominf = request.getParameter("cominf");
       retfue = request.getParameter("retfue");
       porcomret = request.getParameter("porcomret");
       retmin = request.getParameter("retmin").replace(',', ' ');
       diacanje = request.getParameter("diacanje");
       idecont = request.getParameter("idecont");
       razmod = request.getParameter("obligatoriorazmod");
       v_opcion = request.getParameter("v_opcion");
       }
    catch (Exception e) { e.printStackTrace(); }*///INT20131108
    
    try{
       v_codigo = request.getParameter("obligatoriocodigo");
       CleanResults cr1 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
       v_descripcion = cr1.getCleanHTML();
       CleanResults cr2 = as.scan(request.getParameter("obligatorionumide"), new File(POLICY_FILE_LOCATION));
       numide = cr2.getCleanHTML();
       digver = request.getParameter("obligatoriodigver");
       tipoide = request.getParameter("obligatoriotipoide");
       for (int i=0; i < request.getParameterValues("obligatoriometord").length; i++)
          metord += request.getParameterValues("obligatoriometord")[i];
       metben = request.getParameter("obligatoriometben");
       metpen = request.getParameter("obligatoriometpen");
       metcue = request.getParameter("obligatoriometcue");
       for (int i=0; i < request.getParameterValues("obligatorionatret").length; i++)
          natret += request.getParameterValues("obligatorionatret")[i];
       cantie = request.getParameter("obligatoriocantie");
       unitie = request.getParameter("unitie");
       fecben = request.getParameter("obligatoriofecben");
       maxctacon = request.getParameter("maxctacon");
       cominf = request.getParameter("cominf");
       retfue = request.getParameter("retfue");
       porcomret = request.getParameter("porcomret");
       retmin = request.getParameter("retmin").replace(',', ' ');
       diacanje = request.getParameter("diacanje");
       idecont = request.getParameter("idecont");
       CleanResults cr3 = as.scan(request.getParameter("obligatoriorazmod"), new File(POLICY_FILE_LOCATION));
       razmod = cr3.getCleanHTML();
       v_opcion = request.getParameter("v_opcion");
       }
    catch (Exception e) { e.printStackTrace(); }

    Float fmaxxtacon = new Float(maxctacon);
    Float fcominf = new Float(cominf);
    Float fretfue = new Float(retfue);
    Float fporcomret = new Float(porcomret);

    if ((fmaxxtacon.floatValue()<0||fmaxxtacon.floatValue()>100)||(fcominf.floatValue()<0||fcominf.floatValue()>100)||(fretfue.floatValue()<0||fretfue.floatValue()>100)||(fporcomret.floatValue()<0||fporcomret.floatValue()>100))
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de producto","Administración de producto", "", "No debe ingresar porcentajes mayores a 100 o menosres a 0", false));
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }
    else
      {

  if (fecben.equals(""))
    fecben = "NULL";
  if (!retmin.equals(""))
    {
     StringTokenizer st= new StringTokenizer(retmin);
     retmin="";
     while (st.hasMoreTokens())
        retmin+=st.nextToken();
    }




if (v_opcion.equals("con"))
  {

     v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'SUT%' AND ref_codigo != 'SUT000'";
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);



    v_declaracion = "SELECT  \n"+
                "pro_cantidad_tiempo, \n"+
                "pro_ref_unidad_tiempo, \n"+
                "decode(pro_fecha_beneficio,' ',' ',to_char(pro_fecha_beneficio,'yyyy-mm-dd'))\n"+
                "FROM tbproductos WHERE pro_codigo  = '"+v_codigo+"'";

  //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
  v_descripcion1 = v_Consulta.TBFL_Consulta(v_declaracion);



        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de productos", "Administración de productos","TBPKT_PARAMETROS.TBPKT_PRODUCTO.TBCS_ModificaProducto", "", true, "moduloparametro.js", "return checkrequired(this)"));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                    "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Fecha de beneficio</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
        if (v_descripcion1.elementAt(2).toString().equals("null"))
          {
          v_descripcion1.setElementAt("",2);
          v_descripcion1.setElementAt("1",0);
          v_descripcion1.setElementAt("",1);
          }
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Fecha de beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+(v_descripcion1.elementAt(2).toString().length()<10?v_descripcion1.elementAt(2).toString():v_descripcion1.elementAt(2).toString().substring(0,10))+"<BR><INPUT NAME=obligatoriofecben VALUE='"+(v_descripcion1.elementAt(2).toString().length()<10?v_descripcion1.elementAt(2).toString():v_descripcion1.elementAt(2).toString().substring(0,10))+"' TYPE=text MAXLENGTH=10 SIZE=10 onBlur=\"checkdate(this)\">&nbsp;&nbsp;(YYYY-MM-DD)</font></TD></TR>");
        if (v_descripcion1.elementAt(2).toString().equals("null"))
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cantidad de tiempo</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>&nbsp;&nbsp;");
        else
          out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cantidad de tiempo</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion1.elementAt(0).toString()+"&nbsp;&nbsp;");

        if (v_descripcion1.elementAt(2).toString().equals(""))
          out.println(v_descripcion1.elementAt(1).toString());
        else
          out.println(TBFL_ConsultaDescripcion(v_descripcion1.elementAt(1).toString()));

        out.println("<BR><INPUT NAME=obligatoriocantie VALUE='"+v_descripcion1.elementAt(0).toString()+"' onblur=\"if (esNumero(obligatoriocantie)==1) return false;checkDecimals(v_codigo.obligatoriocantie,v_codigo.obligatoriocantie.value, 0),esNegativo(obligatoriocantie), esMayoraO(obligatoriocantie)\" TYPE=text MAXLENGTH=3 SIZE=3>&nbsp;&nbsp;<SELECT NAME=unitie>");
        for (int i=0; i<v_resultadodeclaracion.size(); i+=2)
           {
           if (v_descripcion1.elementAt(1).toString().equals(v_resultadodeclaracion.elementAt(i).toString()))
             out.println("<OPTION SELECTED VALUE='"+v_resultadodeclaracion.elementAt(i).toString()+"'>"+v_resultadodeclaracion.elementAt(i+1).toString());
           else
             out.println("<OPTION VALUE='"+v_resultadodeclaracion.elementAt(i).toString()+"'>"+v_resultadodeclaracion.elementAt(i+1).toString());
           }
        out.println("</SELECT></font></TD></TR>");
        out.println("</TABLE>");
//        ----------------


        out.println("<INPUT NAME=obligatoriocodigo TYPE=hidden VALUE='"+v_codigo+"'>");
        out.println("<INPUT NAME=obligatoriodescripcion TYPE=hidden VALUE='"+v_descripcion+"'>");
        out.println("<INPUT NAME=obligatorionumide TYPE=hidden VALUE='"+numide+"'>");
        out.println("<INPUT NAME=obligatoriodigver TYPE=hidden VALUE='"+digver+"'>");
        out.println("<INPUT NAME=obligatoriotipoide TYPE=hidden VALUE='"+tipoide+"'>");
        out.println("<INPUT NAME=obligatoriometord TYPE=hidden VALUE='"+metord+"'>");
        out.println("<INPUT NAME=obligatoriometben TYPE=hidden VALUE='"+metben+"'>");
        out.println("<INPUT NAME=obligatoriometpen TYPE=hidden VALUE='"+metpen+"'>");
        out.println("<INPUT NAME=obligatoriometcue TYPE=hidden VALUE='"+metcue+"'>");
        out.println("<INPUT NAME=obligatorionatret TYPE=hidden VALUE='"+natret+"'>");
        out.println("<INPUT NAME=maxctacon  TYPE=hidden VALUE='"+maxctacon+"'>");
        out.println("<INPUT NAME=cominf  TYPE=hidden VALUE='"+cominf+"'>");
        out.println("<INPUT NAME=retfue  TYPE=hidden VALUE='"+retfue+"'>");
        out.println("<INPUT NAME=porcomret  TYPE=hidden VALUE='"+porcomret+"'>");        
        out.println("<INPUT NAME=retmin  TYPE=hidden VALUE='"+retmin+"'>");
        out.println("<INPUT NAME=diacanje  TYPE=hidden VALUE='"+diacanje+"'>");
        out.println("<INPUT NAME=idecont  TYPE=hidden VALUE='"+idecont+"'>");
        out.println("<INPUT NAME=obligatoriorazmod  TYPE=hidden VALUE='"+razmod+"'>");
        out.println("<INPUT NAME=v_opcion  TYPE=hidden VALUE='sin'>");

        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER><BR>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        //Hasta aca van los campos
        out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
        out.close();
  }
else if (v_opcion.equals("eliminar"))
  {
  v_declaracion = "SELECT \n"+
                "pro_metodo_orden, \n"+
                "pro_metodo_beneficio, \n"+
                "pro_metodo_penalizacion, \n"+
                "pro_metodo_cuenta, \n"+
                "pro_naturaleza_retiro, \n"+
                "pro_cantidad_tiempo, \n"+
                "to_char(pro_fecha_beneficio,'yyyy-mm-dd'), \n"+
                "pro_max_cta_contingente, \n"+
                "pro_componente_inflacion, \n"+
                "pro_retencion_fuente, \n"+
                "pro_porcentaje_comision_reten, \n"+
                "pro_retiro_minimo \n"+
                "FROM tbproductos WHERE pro_codigo  = '"+v_codigo+"'";

  v_resultadodeclaracion2 = v_Consulta.TBFL_Consulta(v_declaracion);


  dmetord = v_resultadodeclaracion2.elementAt(0).toString();
  dmetben = v_resultadodeclaracion2.elementAt(1).toString();
  dmetpen = v_resultadodeclaracion2.elementAt(2).toString();
  dmetcue = v_resultadodeclaracion2.elementAt(3).toString();
  dnatret = v_resultadodeclaracion2.elementAt(4).toString();
  dcantie = v_resultadodeclaracion2.elementAt(5).toString();
  dfecben = v_resultadodeclaracion2.elementAt(6).toString();
  dmaxctacon = v_resultadodeclaracion2.elementAt(7).toString();
  dcominf = v_resultadodeclaracion2.elementAt(8).toString();
  dretfue = v_resultadodeclaracion2.elementAt(9).toString();
  dporcomret = v_resultadodeclaracion2.elementAt(10).toString();
  dretmin = v_resultadodeclaracion2.elementAt(11).toString();

        v_declaracion = "UPDATE tbproductos SET "+
                  "pro_descripcion = UPPER('"+v_descripcion+"')"+
                  " , pro_numero_identificacion = UPPER('"+numide+"')"+
                  " , pro_digito_verificacion = "+digver+
                  " , pro_ref_tipo_identificacion = UPPER('"+tipoide+"')"+
                  " , pro_metodo_orden = UPPER('"+metord+"')"+
                  " , pro_metodo_beneficio = UPPER('"+metben+"')"+
                  " , pro_metodo_penalizacion = UPPER('"+metpen+"')"+
                  " , pro_metodo_cuenta = UPPER('"+metcue+"')"+
                  " , pro_naturaleza_retiro = UPPER('"+natret+"')"+
                  " , pro_cantidad_tiempo = null"+
                  " , pro_ref_unidad_tiempo = null"+
                  " , pro_fecha_beneficio = null"+
                  " , pro_max_cta_contingente = "+maxctacon+
                  " , pro_componente_inflacion = "+cominf+
                  " , pro_retencion_fuente = "+retfue+
                  " , pro_porcentaje_comision_reten = "+porcomret+
                  " , pro_retiro_minimo = "+retmin+
                  " , pro_dias_canje = "+diacanje+
                  " , pro_ref_identificador_contable = '"+idecont+"'"+
                  " WHERE pro_codigo = '"+v_codigo+"'";

    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);

    if (!dfecben.equalsIgnoreCase("NULL"))
      v_declaracion = "INSERT INTO tbtransaccion_logs (trl_tipo_transaccion, trl_aporte_retiro_producto, trl_fecha, trl_linea, trl_tipo_modificacion, trl_razon_modificacion, trl_usuario, trl_metodo_orden_prod, trl_metodo_beneficio_prod, trl_metodo_penalizacion_prod, trl_metodo_cuenta_prod, trl_naturaleza_retiro_prod, trl_cantidad_tiempo_prod, trl_fecha_beneficio_prod, trl_max_cta_contingente_prod, trl_componente_inflacion_prod, trl_retencion_fuente_prod, trl_retiro_minimo_prod)"+
                    "VALUES ('P', '"+v_codigo+"', trunc(sysdate), to_char(sysdate, 'HH24MI'), 'M' , '"+razmod+"' , '"+parametros[0]+"', '"+dmetord+"', '"+dmetben+"', '"+dmetpen+"', '"+dmetcue+"', '"+dnatret+"', "+dcantie+", to_date('"+dfecben+"','YYYY-MM-DD'), "+dmaxctacon+", "+dcominf+", "+dretfue+", "+dretmin+")";
    else
      v_declaracion = "INSERT INTO tbtransaccion_logs (trl_tipo_transaccion, trl_aporte_retiro_producto, trl_fecha, trl_linea, trl_tipo_modificacion, trl_razon_modificacion, trl_usuario, trl_metodo_orden_prod, trl_metodo_beneficio_prod, trl_metodo_penalizacion_prod, trl_metodo_cuenta_prod, trl_naturaleza_retiro_prod, trl_max_cta_contingente_prod, trl_componente_inflacion_prod, trl_retencion_fuente_prod, trl_retiro_minimo_prod)"+
                    "VALUES ('P', '"+v_codigo+"', trunc(sysdate), to_char(sysdate, 'HH24MI'), 'M' , '"+razmod+"' , '"+parametros[0]+"', '"+dmetord+"', '"+dmetben+"', '"+dmetpen+"', '"+dmetcue+"', '"+dnatret+"',  "+dmaxctacon+", "+dcominf+", "+dretfue+", "+dretmin+")";

    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion.addElement(v_Consulta.TBFL_Consulta(v_declaracion, true).elementAt(0).toString());

  //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos

  //publicacion en pagina html de las Consultas
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones por producto","Administración de conceptos de transacciones por producto", "", v_resultadodeclaracion.elementAt(0).toString()+"(Modificacion en productos)<BR>"+v_resultadodeclaracion.elementAt(1).toString()+"(Adición en el log de transacciones)", false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }
else 
  {
  if ((v_codigo!=null)&&(v_descripcion!=null)&&(numide!=null)&&(digver!=null)&&(tipoide!=null)&&(metord!=null)&&(metben!=null)&&(metpen!=null)&&(metcue!=null)&&(natret!=null)&&(razmod!=null)&&(diacanje!=null)&&(idecont!=null))
    {
  v_declaracion = "SELECT \n"+
                "pro_metodo_orden, \n"+
                "pro_metodo_beneficio, \n"+
                "pro_metodo_penalizacion, \n"+
                "pro_metodo_cuenta, \n"+
                "pro_naturaleza_retiro, \n"+
                "pro_cantidad_tiempo, \n"+
                "to_char(pro_fecha_beneficio,'yyyy-mm-dd'), \n"+
                "pro_max_cta_contingente, \n"+
                "pro_componente_inflacion, \n"+
                "pro_retencion_fuente, \n"+
                "pro_porcentaje_comision_reten, \n"+
                "pro_retiro_minimo \n"+
                "FROM tbproductos WHERE pro_codigo  = '"+v_codigo+"'";

  v_resultadodeclaracion2 = v_Consulta.TBFL_Consulta(v_declaracion);

  dmetord = v_resultadodeclaracion2.elementAt(0).toString();
  dmetben = v_resultadodeclaracion2.elementAt(1).toString();
  dmetpen = v_resultadodeclaracion2.elementAt(2).toString();
  dmetcue = v_resultadodeclaracion2.elementAt(3).toString();
  dnatret = v_resultadodeclaracion2.elementAt(4).toString();
  dcantie = v_resultadodeclaracion2.elementAt(5).toString();
  dfecben = v_resultadodeclaracion2.elementAt(6).toString();
  dmaxctacon = v_resultadodeclaracion2.elementAt(7).toString();
  dcominf = v_resultadodeclaracion2.elementAt(8).toString();
  dretfue = v_resultadodeclaracion2.elementAt(9).toString();
  dporcomret = v_resultadodeclaracion2.elementAt(10).toString();
  dretmin = v_resultadodeclaracion2.elementAt(11).toString();

      v_declaracion = "UPDATE tbproductos SET "+
                  "pro_descripcion = UPPER('"+v_descripcion+"')"+
                  " , pro_numero_identificacion = UPPER('"+numide+"')"+
                  " , pro_digito_verificacion = "+digver+
                  " , pro_ref_tipo_identificacion = UPPER('"+tipoide+"')"+
                  " , pro_metodo_orden = UPPER('"+metord+"')"+
                  " , pro_metodo_beneficio = UPPER('"+metben+"')"+
                  " , pro_metodo_penalizacion = UPPER('"+metpen+"')"+
                  " , pro_metodo_cuenta = UPPER('"+metcue+"')"+
                  " , pro_naturaleza_retiro = UPPER('"+natret+"')"+
                  " , pro_cantidad_tiempo = "+cantie+
                  " , pro_ref_unidad_tiempo = UPPER('"+unitie+"')"+
                  " , pro_fecha_beneficio = to_date(decode(UPPER('"+fecben+"'),'NULL', null, '"+fecben+"'),'YYYY-MM-DD')"+
                  " , pro_max_cta_contingente = "+maxctacon+
                  " , pro_componente_inflacion = "+cominf+
                  " , pro_retencion_fuente = "+retfue+
                  " , pro_porcentaje_comision_reten = "+porcomret+
                  " , pro_retiro_minimo = "+retmin+
                  " , pro_dias_canje = "+diacanje+
                  " , pro_ref_identificador_contable = '"+idecont+"'"+
                  " WHERE pro_codigo = '"+v_codigo+"'";



    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);

    if (!dfecben.equalsIgnoreCase("NULL"))
      v_declaracion = "INSERT INTO tbtransaccion_logs (trl_tipo_transaccion, trl_aporte_retiro_producto, trl_fecha, trl_linea, trl_tipo_modificacion, trl_razon_modificacion, trl_usuario, trl_metodo_orden_prod, trl_metodo_beneficio_prod, trl_metodo_penalizacion_prod, trl_metodo_cuenta_prod, trl_naturaleza_retiro_prod, trl_cantidad_tiempo_prod, trl_fecha_beneficio_prod, trl_max_cta_contingente_prod, trl_componente_inflacion_prod, trl_retencion_fuente_prod, trl_retiro_minimo_prod)"+
                    "VALUES ('P', '"+v_codigo+"', trunc(sysdate), to_char(sysdate, 'HH24MI'), 'M' , '"+razmod+"' , '"+parametros[0]+"', '"+dmetord+"', '"+dmetben+"', '"+dmetpen+"', '"+dmetcue+"', '"+dnatret+"', "+dcantie+", to_date('"+dfecben+"','YYYY-MM-DD'), "+dmaxctacon+", "+dcominf+", "+dretfue+", "+dretmin+")";
    else
      v_declaracion = "INSERT INTO tbtransaccion_logs (trl_tipo_transaccion, trl_aporte_retiro_producto, trl_fecha, trl_linea, trl_tipo_modificacion, trl_razon_modificacion, trl_usuario, trl_metodo_orden_prod, trl_metodo_beneficio_prod, trl_metodo_penalizacion_prod, trl_metodo_cuenta_prod, trl_naturaleza_retiro_prod, trl_max_cta_contingente_prod, trl_componente_inflacion_prod, trl_retencion_fuente_prod, trl_retiro_minimo_prod)"+
                    "VALUES ('P', '"+v_codigo+"', trunc(sysdate), to_char(sysdate, 'HH24MI'), 'M' , '"+razmod+"' , '"+parametros[0]+"', '"+dmetord+"', '"+dmetben+"', '"+dmetpen+"', '"+dmetcue+"', '"+dnatret+"',  "+dmaxctacon+", "+dcominf+", "+dretfue+", "+dretmin+")";


    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion.addElement(v_Consulta.TBFL_Consulta(v_declaracion, true).elementAt(0).toString());

    }
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }


  //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos

  //publicacion en pagina html de las Consultas
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de conceptos de transacciones por producto","Administración de conceptos de transacciones por producto", "", v_resultadodeclaracion.elementAt(0).toString()+"(Modificacion en productos)<BR>"+v_resultadodeclaracion.elementAt(1).toString()+"(Adición en el log de transacciones)", false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

      }//No deja ingresar valores mayores a 100 y menores a 0
    v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos

  }


  private String TBFL_ConsultaDescripcion(String v_valor)
  {
  String v_declaracion;
   //TBCL_Consulta v_Consulta = new TBCL_Consulta();
    //v_Consulta.TBPL_RealizarConexion();
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion;
    v_declaracion =  "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_valor+"'";
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, "ref_descripcion");
    //v_Consulta.TBPL_shutDown();
    return  v_resultadodeclaracion.elementAt(0).toString();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_PRODUCTO.TBCS_ModificaProducto Information";
  }
}
