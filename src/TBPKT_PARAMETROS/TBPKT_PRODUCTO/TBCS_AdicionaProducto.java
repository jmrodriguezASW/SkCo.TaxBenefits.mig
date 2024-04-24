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


public class TBCS_AdicionaProducto extends HttpServlet{
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
    try{
       out = new PrintWriter (response.getOutputStream());
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
  String v_declaracion = new String();
  Vector v_resultadodeclaracion = new Vector();
  String declaracion1 = new String();
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
    String v_opcion = "";

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
       retmin = request.getParameter("retmin").replace(',',' ');
       diacanje = request.getParameter("diacanje");
       idecont = request.getParameter("idecont");       
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
       retmin = request.getParameter("retmin").replace(',',' ');
       diacanje = request.getParameter("diacanje");
       idecont = request.getParameter("idecont");       
       v_opcion = request.getParameter("v_opcion");
       }
    catch (Exception e) { e.printStackTrace(); } //INT20131108

    Float fmaxxtacon = new Float(maxctacon);
    Float fcominf = new Float(cominf);
    Float fretfue = new Float(retfue);
    Float fporcomret = new Float(0.0);
    try {
        fporcomret = Float.valueOf(porcomret);
        }
    catch (Exception e) { Float.valueOf("0"); }

    if ((fmaxxtacon.floatValue()<0||fmaxxtacon.floatValue()>100)||(fcominf.floatValue()<0||fcominf.floatValue()>100)||(fretfue.floatValue()<0||fretfue.floatValue()>100)||(fporcomret.floatValue()<0||fporcomret.floatValue()>100))
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de producto","Administración de producto", "", "No debe ingresar porcentajes mayores a 100 o menosres a 0", false));
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }
    else
      {

if (v_opcion.equals("con"))
  {

     v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'SUT%' AND ref_codigo != 'SUT000'";
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);


        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de productos", "Administración de productos","TBPKT_PARAMETROS.TBPKT_PRODUCTO.TBCS_AdicionaProducto", "", true, "moduloparametro.js", "return checkrequired(this)"));
        //Aqui van los campo que se quieren mostrar
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                    "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Parametrización del beneficio tributario</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Cantidad de tiempo para beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=obligatoriocantie VALUE=1 onblur=\"if (esNumero(obligatoriocantie)==1) return false;checkDecimals(v_codigo.obligatoriocantie,v_codigo.obligatoriocantie.value, 0),esNegativo(obligatoriocantie), esMayoraO(obligatoriocantie)\" TYPE=text MAXLENGTH=3 SIZE=3>&nbsp;&nbsp;<SELECT NAME=unitie>");
        for (int i=0; i<v_resultadodeclaracion.size(); i+=2)
           {
           out.println("<OPTION VALUE='"+v_resultadodeclaracion.elementAt(i).toString()+"'>"+v_resultadodeclaracion.elementAt(i+1).toString());
           }
        out.println("</SELECT></font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Fecha de beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=obligatoriofecben TYPE=text MAXLENGTH=10 SIZE=10 onBlur=\"checkdate(this)\">&nbsp;&nbsp;(YYYY-MM-DD)</font></TD></TR>");
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
        out.println("<INPUT TYPE=hidden NAME=obligatorionatret VALUE='"+natret+"'>");
        out.println("<INPUT NAME=maxctacon  TYPE=hidden VALUE='"+maxctacon+"'>");
        out.println("<INPUT NAME=cominf  TYPE=hidden VALUE='"+cominf+"'>");
        out.println("<INPUT NAME=retfue  TYPE=hidden VALUE='"+retfue+"'>");
        out.println("<INPUT NAME=porcomret  TYPE=hidden VALUE='"+porcomret+"'>");        
        out.println("<INPUT NAME=retmin  TYPE=hidden VALUE='"+retmin+"'>");
        out.println("<INPUT NAME=diacanje  TYPE=hidden VALUE='"+diacanje+"'>");
        out.println("<INPUT NAME=idecont  TYPE=hidden VALUE='"+idecont+"'>");        
        out.println("<INPUT NAME=v_opcion TYPE=hidden VALUE='sin'>");



//    ------------
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Cancelar ONCLICK=history.go(-1);></CENTER><BR>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
        out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        //Hasta aca van los campos
        out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
        out.close();
  }
else
  {
  if ((v_codigo!=null)&&(v_descripcion!=null)&&(numide!=null)&&(digver!=null)&&(tipoide!=null)&&(metord!=null)&&(metben!=null)&&(metpen!=null)&&(metcue!=null)&&(natret!=null))
    {

    if (cantie.equals(""))
      cantie = "NULL";
    if (fecben.equals(""))
      fecben = "NULL";
    if (maxctacon.equals(""))
      maxctacon = "NULL";
    if (cominf.equals(""))
      cominf = "NULL";
    if (retfue.equals(""))
      retfue = "NULL";
    if (porcomret.equals(""))
      porcomret = "NULL";
    if (retmin.equals(""))
      retmin = "NULL";
    else
      {
      StringTokenizer st= new StringTokenizer(retmin);
      retmin="";
      while (st.hasMoreTokens())
         retmin+=st.nextToken();
      }
    if (diacanje.equals(""))
      diacanje = "NULL";
    if (idecont.equals(""))
      idecont = "NULL";
    //Sentencias SQL

    if (!fecben.equalsIgnoreCase("NULL"))
      v_declaracion = "INSERT INTO tbproductos (pro_codigo, pro_descripcion, pro_numero_identificacion, pro_digito_verificacion, pro_ref_tipo_identificacion, pro_metodo_orden, pro_metodo_beneficio, pro_metodo_penalizacion, pro_metodo_cuenta, pro_naturaleza_retiro, pro_cantidad_tiempo, pro_ref_unidad_tiempo, pro_fecha_beneficio, pro_max_cta_contingente, pro_componente_inflacion, pro_retencion_fuente, pro_porcentaje_comision_reten, pro_retiro_minimo, pro_dias_canje, pro_ref_identificador_contable) VALUES (UPPER('"+v_codigo+"'), UPPER('"+v_descripcion+"'), UPPER('"+numide+"'), "+digver+", UPPER('"+tipoide+"'), UPPER('"+metord+"'), UPPER('"+metben+"'), UPPER('"+metpen+"'), UPPER('"+metcue+"'), UPPER('"+natret+"'), "+cantie+", UPPER('"+unitie+"'), to_date('"+fecben+"','YYYY-MM-DD'), "+maxctacon+", "+cominf+", "+retfue+" , "+porcomret+" , "+retmin+", "+diacanje+", '"+idecont+"')";
    else
      v_declaracion = "INSERT INTO tbproductos (pro_codigo, pro_descripcion, pro_numero_identificacion, pro_digito_verificacion, pro_ref_tipo_identificacion, pro_metodo_orden, pro_metodo_beneficio, pro_metodo_penalizacion, pro_metodo_cuenta, pro_naturaleza_retiro,                                                                  pro_max_cta_contingente, pro_componente_inflacion, pro_retencion_fuente, pro_porcentaje_comision_reten, pro_retiro_minimo, pro_dias_canje, pro_ref_identificador_contable) VALUES (UPPER('"+v_codigo+"'), UPPER('"+v_descripcion+"'), UPPER('"+numide+"'), "+digver+", UPPER('"+tipoide+"'), UPPER('"+metord+"'), UPPER('"+metben+"'), UPPER('"+metpen+"'), UPPER('"+metcue+"'), UPPER('"+natret+"'),                                                                      "+maxctacon+", "+cominf+", "+retfue+" , "+porcomret+" , "+retmin+", "+diacanje+", '"+idecont+"')";
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados

    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);


    //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
    }
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }


  //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos

  //publicacion en pagina html de las Consultas
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de productos","Administración de productos", "", v_resultadodeclaracion.elementAt(0).toString(), false, "moduloparametro.js", "return checkrequired(this)"));
  if (fecben.equals("NULL"))
    out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  else
    out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

      }//No deja adicionar porcentajes mayores o menores a 100 o 0
      
      
      v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos


  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_PRODUCTO.TBCS_AdicionaProducto Information";
  }
}

