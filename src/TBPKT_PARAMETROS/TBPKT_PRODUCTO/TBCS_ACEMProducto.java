package TBPKT_PARAMETROS.TBPKT_PRODUCTO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_ACEMProducto extends HttpServlet{
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
    String v_declaracion;
    //Vector donde se guardara el resultado de la v_declaracion
    Vector v_resultadodeclaracion, v_resultadodeclaracion2, v_resultadodeclaracion3, v_resultadodeclaracion4, v_resultadodeclaracion5;
    Vector v_resultadodeclaracion1 = new Vector();
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    //Toma la opción de modificar, eliminar, adicionar o Consultar
    String v_opcion = "Consulta";
    //Toma los diferentes codigos de producto
    String v_codpro = "";

    try{
       v_opcion = request.getParameter("v_opcion");
       v_codpro = request.getParameter("v_codpro");
       }
    catch (Exception e) { e.printStackTrace(); }


   if (v_opcion.equals("consulta"))
     {
  //Sentencias SQL
  //selecciona todos los datos que hay en la tabla productos con la llave de v_codigo
    v_declaracion = "SELECT          pro_codigo,\n"+
"                pro_descripcion,\n"+
"                pro_numero_identificacion,\n"+
"                pro_digito_verificacion,\n"+
"                a.ref_descripcion,\n"+
"                pro_metodo_orden,\n"+
"                decode(pro_metodo_beneficio,'S','SI','NO'),\n"+
"                decode(pro_metodo_penalizacion ,'S','SI','NO'),\n"+
"                decode(pro_metodo_cuenta ,'S','SI','NO'),\n"+
"                pro_naturaleza_retiro,\n"+
"                pro_cantidad_tiempo,\n"+
"                g.ref_descripcion,\n"+
"                decode(pro_fecha_beneficio,' ',' ',to_char(pro_fecha_beneficio,'yyyy-mm-dd')),\n"+
"                pro_max_cta_contingente,\n"+
"                pro_componente_inflacion,\n"+
"                pro_retencion_fuente,\n"+
"                pro_porcentaje_comision_reten,\n"+
"                pro_retiro_minimo,\n"+
"                pro_dias_canje,\n"+
"                c.ref_descripcion\n"+
"                FROM tbproductos\n"+
"                    , tbreferencias c\n"+
"                    , tbreferencias a\n"+
"                    , tbreferencias g\n"+
"                WHERE pro_codigo  = ? \n"+
"                  AND c.ref_codigo (+) = pro_ref_identificador_contable\n"+
"                  AND a.ref_codigo     = pro_ref_tipo_identificacion\n"+
"                  and g.ref_codigo (+) = pro_ref_unidad_tiempo";
  //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
  String v_parametros[] = new String[1];
  v_parametros[0] = v_codpro; 
  v_resultadodeclaracion = v_Consulta.TBFL_ConsultaParametros(v_declaracion, v_parametros);




      /*Remplaza metodo orden
  ------------------*/
  String v_smetodoorden = new String();
  String v_codmetord[] = new String[v_resultadodeclaracion.elementAt(5).toString().length()/6];

  for (int i=0; i<v_resultadodeclaracion.elementAt(5).toString().length()/6; i++)
     v_codmetord[i] = v_resultadodeclaracion.elementAt(5).toString().substring(i*6,i*6+6);

    for (int i=0; i<v_codmetord.length; i++)
     {
     //Sentencias SQL
     v_declaracion = "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_codmetord[i]+"' and ref_codigo LIKE 'SMO%'";
     //Limpia el vector resultado v_declaracion para ingresar un nuevo dato

     v_resultadodeclaracion1.clear();
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);

     v_smetodoorden += v_resultadodeclaracion1.elementAt(0).toString() + ",";
     }

  //Cabia el v_valor del v_codigo por la v_descripcion en el tipo de identificación
  v_resultadodeclaracion.setElementAt(v_smetodoorden,5);

//---------------------------------------------------------------------------

      /*Remplaza naturalezaretiro
  ------------------*/
  String v_snaturalezaretiro = new String();

  String v_codnatret[] = new String[v_resultadodeclaracion.elementAt(9).toString().length()/6];

  for (int i=0; i<v_resultadodeclaracion.elementAt(9).toString().length()/6; i++)
     v_codnatret[i] = v_resultadodeclaracion.elementAt(9).toString().substring(i*6,i*6+6);

    for (int i=0; i<v_codnatret.length; i++)
     {
     //Sentencias SQL
     v_declaracion = "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_codnatret[i]+"' and ref_codigo LIKE 'SNR%'";
     //Limpia el vector resultado v_declaracion para ingresar un nuevo dato

     v_resultadodeclaracion1.clear();
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);

     v_snaturalezaretiro += v_resultadodeclaracion1.elementAt(0).toString() + ",";
     }

  //Cabia el v_valor del v_codigo por la v_descripcion en el tipo de identificación
  v_resultadodeclaracion.setElementAt(v_snaturalezaretiro,9);


  //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //publicacion en pagina html de las Consultas
  TBPL_PublicarConsulta(v_codpro, v_resultadodeclaracion);
     }
   else if (v_opcion.equals("adiciona"))
     {
     v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'UTI%' AND ref_codigo != 'UTI000'";
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);

     v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'SIC%' AND ref_codigo != 'SIC000'";
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);

     v_declaracion = "SELECT REF_CODIGO, REF_DESCRIPCION FROM tbreferencias WHERE ref_codigo like 'SMO%' AND ref_codigo > 'SMO000'";
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion2 = v_Consulta.TBFL_Consulta(v_declaracion);

     v_declaracion = "SELECT REF_CODIGO, REF_DESCRIPCION FROM tbreferencias WHERE ref_codigo like 'SNR%' AND ref_codigo > 'SNR000'";
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion3 = v_Consulta.TBFL_Consulta(v_declaracion);



     //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
     //publicacion en pagina html de las Consultas
     TBPL_PublicarAdiciona(v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2, v_resultadodeclaracion3);
     }
   else if (v_opcion.equals("elimina"))
     {
  //Sentencias SQL
    v_declaracion = "SELECT          pro_codigo,\n"+
"                pro_descripcion,\n"+
"                pro_numero_identificacion,\n"+
"                pro_digito_verificacion,\n"+
"                a.ref_descripcion,\n"+
"                pro_metodo_orden,\n"+
"                decode(pro_metodo_beneficio,'S','SI','NO'),\n"+
"                decode(pro_metodo_penalizacion ,'S','SI','NO'),\n"+
"                decode(pro_metodo_cuenta ,'S','SI','NO'),\n"+
"                pro_naturaleza_retiro,\n"+
"                pro_cantidad_tiempo,\n"+
"                g.ref_descripcion,\n"+
"                decode(pro_fecha_beneficio,' ',' ',to_char(pro_fecha_beneficio,'yyyy-mm-dd')),\n"+
"                pro_max_cta_contingente,\n"+
"                pro_componente_inflacion,\n"+
"                pro_retencion_fuente,\n"+
"                pro_porcentaje_comision_reten,\n"+
"                pro_retiro_minimo,\n"+
"                pro_dias_canje,\n"+
"                c.ref_descripcion\n"+
"                FROM tbproductos\n"+
"                    , tbreferencias c\n"+
"                    , tbreferencias a\n"+
"                    , tbreferencias g\n"+
"                WHERE pro_codigo  = ? \n"+
"                  AND c.ref_codigo (+) = pro_ref_identificador_contable\n"+
"                  AND a.ref_codigo     = pro_ref_tipo_identificacion\n"+
"                  and g.ref_codigo (+) = pro_ref_unidad_tiempo";
  //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    String v_parametros[] = new String[1];
  v_parametros[0] = v_codpro; 
  v_resultadodeclaracion = v_Consulta.TBFL_ConsultaParametros(v_declaracion, v_parametros);
 // v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);




      /*Remplaza metodo orden
  ------------------*/

  String v_smetodoorden = new String();
  String v_codmetord[] = new String[v_resultadodeclaracion.elementAt(5).toString().length()/6];

  for (int i=0; i<v_resultadodeclaracion.elementAt(5).toString().length()/6; i++)
     v_codmetord[i] = v_resultadodeclaracion.elementAt(5).toString().substring(i*6,i*6+6);

    for (int i=0; i<v_codmetord.length; i++)
     {
     //Sentencias SQL
     v_declaracion = "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_codmetord[i]+"' and ref_codigo LIKE 'SMO%'";
     //Limpia el vector resultado v_declaracion para ingresar un nuevo dato

     v_resultadodeclaracion1.clear();
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);

     v_smetodoorden += v_resultadodeclaracion1.elementAt(0).toString() + ",";
     }

  //Cabia el v_valor del v_codigo por la v_descripcion en el tipo de identificación
  v_resultadodeclaracion.setElementAt(v_smetodoorden,5);



      /*Remplaza naturalezaretiro
  ------------------*/
  String v_snaturalezaretiro = new String();

  String v_codnatret[] = new String[v_resultadodeclaracion.elementAt(9).toString().length()/6];

  for (int i=0; i<v_resultadodeclaracion.elementAt(9).toString().length()/6; i++)
     v_codnatret[i] = v_resultadodeclaracion.elementAt(9).toString().substring(i*6,i*6+6);

    for (int i=0; i<v_codnatret.length; i++)
     {
     //Sentencias SQL
     v_declaracion = "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_codnatret[i]+"' and ref_codigo LIKE 'SNR%'";
     //Limpia el vector resultado v_declaracion para ingresar un nuevo dato

     v_resultadodeclaracion1.clear();
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);

     v_snaturalezaretiro += v_resultadodeclaracion1.elementAt(0).toString() + ",";
     }

  //Cabia el v_valor del v_codigo por la v_descripcion en el tipo de identificación
  v_resultadodeclaracion.setElementAt(v_snaturalezaretiro,9);


  //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //publicacion en pagina html de las Consultas
  TBPL_PublicarElimina(v_codpro, v_resultadodeclaracion);
     }
   else if (v_opcion.equals("modifica"))
     {
  //Sentencias SQL
    v_declaracion = "SELECT          pro_codigo,\n"+
"                pro_descripcion,\n"+
"                pro_numero_identificacion,\n"+
"                pro_digito_verificacion,\n"+
"                a.ref_descripcion,\n"+
"                pro_metodo_orden,\n"+
"                decode(pro_metodo_beneficio,'S','SI','NO'),\n"+
"                decode(pro_metodo_penalizacion ,'S','SI','NO'),\n"+
"                decode(pro_metodo_cuenta ,'S','SI','NO'),\n"+
"                pro_naturaleza_retiro,\n"+
"                pro_cantidad_tiempo,\n"+
"                g.ref_descripcion,\n"+
"                decode(pro_fecha_beneficio,' ',' ',to_char(pro_fecha_beneficio,'yyyy-mm-dd')),\n"+
"                pro_max_cta_contingente,\n"+
"                pro_componente_inflacion,\n"+
"                pro_retencion_fuente,\n"+
"                pro_porcentaje_comision_reten,\n"+
"                pro_retiro_minimo,\n"+
"                pro_dias_canje,\n"+
"                c.ref_descripcion\n"+
"                FROM tbproductos\n"+
"                    , tbreferencias c\n"+
"                    , tbreferencias a\n"+
"                    , tbreferencias g\n"+
"                WHERE pro_codigo  = ? \n"+
"                  AND c.ref_codigo (+) = pro_ref_identificador_contable\n"+
"                  AND a.ref_codigo     = pro_ref_tipo_identificacion\n"+
"                  and g.ref_codigo (+) = pro_ref_unidad_tiempo";

  //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
  String v_parametros[] = new String[1];
  v_parametros[0] = v_codpro; 
  v_resultadodeclaracion = v_Consulta.TBFL_ConsultaParametros(v_declaracion, v_parametros);
  //v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);


      /*Remplaza metodo orden
  ------------------*/

  String v_smetodoorden = new String();
  String v_codmetord[] = new String[v_resultadodeclaracion.elementAt(5).toString().length()/6];

  for (int i=0; i<v_resultadodeclaracion.elementAt(5).toString().length()/6; i++)
     v_codmetord[i] = v_resultadodeclaracion.elementAt(5).toString().substring(i*6,i*6+6);

    for (int i=0; i<v_codmetord.length; i++)
     {
     //Sentencias SQL
     v_declaracion = "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_codmetord[i]+"' and ref_codigo LIKE 'SMO%'";
     //Limpia el vector resultado v_declaracion para ingresar un nuevo dato

     v_resultadodeclaracion1.clear();
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);

     v_smetodoorden += v_resultadodeclaracion1.elementAt(0).toString() + ",";
     }

  //Cabia el v_valor del v_codigo por la v_descripcion en el tipo de identificación
  v_resultadodeclaracion.setElementAt(v_smetodoorden,5);



      /*Remplaza naturalezaretiro
  ------------------*/
  String v_snaturalezaretiro = new String();

  String v_codnatret[] = new String[v_resultadodeclaracion.elementAt(9).toString().length()/6];

  for (int i=0; i<v_resultadodeclaracion.elementAt(9).toString().length()/6; i++)
     v_codnatret[i] = v_resultadodeclaracion.elementAt(9).toString().substring(i*6,i*6+6);

    for (int i=0; i<v_codnatret.length; i++)
     {
     //Sentencias SQL
     v_declaracion = "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_codnatret[i]+"' and ref_codigo LIKE 'SNR%'";
     //Limpia el vector resultado v_declaracion para ingresar un nuevo dato

     v_resultadodeclaracion1.clear();
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);

     v_snaturalezaretiro += v_resultadodeclaracion1.elementAt(0).toString() + ",";
     }

  //Cabia el v_valor del v_codigo por la v_descripcion en el tipo de identificación
  v_resultadodeclaracion.setElementAt(v_snaturalezaretiro,9);




     v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'UTI%' AND ref_codigo != 'UTI000'";
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion1 = v_Consulta.TBFL_Consulta(v_declaracion);

     v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'SUT%' AND ref_codigo != 'SUT000'";
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion2 = v_Consulta.TBFL_Consulta(v_declaracion);

     v_declaracion = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'SIC%' AND ref_codigo != 'SIC000'";
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion3 = v_Consulta.TBFL_Consulta(v_declaracion);

     v_declaracion = "SELECT REF_CODIGO, REF_DESCRIPCION FROM tbreferencias WHERE ref_codigo like 'SMO%' AND ref_codigo > 'SMO000'";
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion4 = v_Consulta.TBFL_Consulta(v_declaracion);

     v_declaracion = "SELECT REF_CODIGO, REF_DESCRIPCION FROM tbreferencias WHERE ref_codigo like 'SNR%' AND ref_codigo > 'SNR000'";
     //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
     v_resultadodeclaracion5 = v_Consulta.TBFL_Consulta(v_declaracion);


     //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
     //publicacion en pagina html de las Consultas
     TBPL_PublicarModifica(v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2, v_resultadodeclaracion3, v_resultadodeclaracion4, v_resultadodeclaracion5);
     }

  v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }

//Código en HTML a mostrar en el navegador
      private void TBPL_PublicarConsulta(String v_codpro, Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de productos","Administración de productos", "", "", true));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Consultar Producto</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de identificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Número de identificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Dígito de verificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Método orden</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(5).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Método beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(6).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Método penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(7).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Método de cuenta contingente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(8).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Naturaleza del retiro</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(9).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cantidad de tiempo para beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(10).toString()+"&nbsp;&nbsp;("+v_descripcion.elementAt(11).toString()+")"+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha de beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+(v_descripcion.elementAt(12).toString().length()<10?v_descripcion.elementAt(12).toString():v_descripcion.elementAt(12).toString().substring(0,10))+"&nbsp;(YYYY-MM-DD)</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje máximo de cuenta contingente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(13).toString()+"%"+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje de componente inflacionario</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(14).toString()+"%"+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje de retención en la fuente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(15).toString()+"%"+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje de retención sobre la comisión</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(16).toString()+"%"+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor mínimo a retirar</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>$</B>"+NumberFormat.getInstance().format(new Float(v_descripcion.elementAt(17).toString()))+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Días de canje</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(18).toString()+"&nbsp;(días)"+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Identificador contable</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(19).toString()+"</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }




//Código en HTML a mostrar en el navegador
      private void TBPL_PublicarAdiciona(Vector v_opciones, Vector v_opciones1, Vector v_opciones2, Vector v_opciones3)
      {
      v_opciones.trimToSize();
      v_opciones1.trimToSize();
      v_opciones2.trimToSize();
      v_opciones3.trimToSize();


      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de productos", "Administración de productos","TBPKT_PARAMETROS.TBPKT_PRODUCTO.TBCS_AdicionaProducto", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adicionar Producto</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=obligatoriocodigo TYPE=text MAXLENGTH=5 SIZE=5></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Descripción del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=obligatoriodescripcion TYPE=text MAXLENGTH=50 SIZE=30></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Número de identificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=obligatorionumide TYPE=text MAXLENGTH=13 SIZE=13></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Dígito de verificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=obligatoriodigver TYPE=text MAXLENGTH=1 SIZE=1 onblur='if (esNumero(obligatoriodigver)==1) return false;'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Tipo de identificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=obligatoriotipoide>");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Método orden</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT MULTIPLE SIZE=3 NAME=obligatoriometord>");
      for (int i=0; i<v_opciones2.size(); i+=2)
         {
         out.println("<OPTION SELECTED VALUE='"+v_opciones2.elementAt(i).toString()+"'>"+v_opciones2.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Método beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=obligatoriometben><OPTION VALUE=S>S<OPTION VALUE=N>N</SELECT></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Método penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=obligatoriometpen><OPTION VALUE=S>S<OPTION VALUE=N>N</SELECT></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Método de cuenta contingente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=obligatoriometcue><OPTION VALUE=S>S<OPTION VALUE=N>N</SELECT></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Naturaleza del retiro</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT MULTIPLE SIZE=3 NAME=obligatorionatret>");
      for (int i=0; i<v_opciones3.size(); i+=2)
         {
         out.println("<OPTION SELECTED VALUE='"+v_opciones3.elementAt(i).toString()+"'>"+v_opciones3.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje máximo de cuenta contingente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=maxctacon  TYPE=text MAXLENGTH=5 SIZE=5 VALUE=0 onblur=\"if (esNumero(maxctacon)==1) return false;checkDecimals(v_codigo.maxctacon,v_codigo.maxctacon.value, 2),esMayor(maxctacon)\">&nbsp;&nbsp;%</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje de componente inflacionario</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=cominf  TYPE=text MAXLENGTH=5 SIZE=5 VALUE=0 onblur=\"if (esNumero(cominf)==1) return false;checkDecimals(v_codigo.cominf,v_codigo.cominf.value, 2),esMayor(cominf)\">&nbsp;&nbsp;%</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje de retención en la fuente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=retfue  TYPE=text MAXLENGTH=5 SIZE=5 VALUE=0 onblur=\"if (esNumero(retfue)==1) return false;checkDecimals(v_codigo.retfue,v_codigo.retfue.value, 2),esMayor(retfue)\">&nbsp;&nbsp;%</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje retención sobre la comisión</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=porcomret  TYPE=text MAXLENGTH=5 SIZE=5 VALUE=0 onblur=\"if (esNumero(porcomret)==1) return false;checkDecimals(v_codigo.porcomret,v_codigo.porcomret.value, 2),esMayor(porcomret)\">&nbsp;&nbsp;%</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor mínimo a retirar</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>$</B><INPUT NAME=retmin  TYPE=text MAXLENGTH=15 SIZE=15 VALUE=0 onBlur=\"if (esNumero(retmin)==1) return false;this.value=formatCurrency(this.value);esNegativo(retmin)\"></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Días de canje</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=diacanje  TYPE=text MAXLENGTH=2 SIZE=2 VALUE=0 onblur='if (esNumero(diacanje)==1) return false;checkDecimals(v_codigo.diacanje,v_codigo.diacanje.value, 0),esNegativo(diacanje)'>&nbsp;&nbsp;(días)</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Identificador contable</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=idecont>");
      for (int i=0; i<v_opciones1.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones1.elementAt(i).toString()+"'>"+v_opciones1.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("</TABLE>");
      out.println("<INPUT NAME=obligatoriofecben TYPE=hidden VALUE=''>");
      out.println("<INPUT NAME=obligatoriocantie TYPE=hidden VALUE=''>");
      out.println("<INPUT NAME=unitie TYPE=hidden VALUE=''>");
      out.println("<BLOCKQUOTE>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='con' CHECKED>Adicionar <B>CON</B> fecha y cantidad de tiempo del beneficio tributario<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='sin'>Adicionar <B>SIN</B> fecha y cantidad de tiempo del beneficio tributario");
      out.println("</font>");
      out.println("</BLOCKQUOTE>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER><BR>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



//Código en HTML a mostrar en el navegador
      private void TBPL_PublicarElimina(String v_codpro, Vector v_descripcion)
      {
      v_descripcion.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de productos","Administración de productos", "TBPKT_PARAMETROS.TBPKT_PRODUCTO.TBCS_EliminaProducto", "", true));

      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Eliminar Producto</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT NAME=v_codigo TYPE=hidden VALUE='"+v_codpro+"'>"+v_codpro+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Tipo de identificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Número de identificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Dígito de verificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Método orden</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(5).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Método beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(6).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Método penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(7).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Método de cuenta contingente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(8).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Naturaleza del retiro</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(9).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cantidad de tiempo para beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(10).toString()+"&nbsp;&nbsp;("+v_descripcion.elementAt(11).toString()+")"+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha de beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+(v_descripcion.elementAt(12).toString().length()<10?v_descripcion.elementAt(12).toString():v_descripcion.elementAt(12).toString().substring(0,10))+"&nbsp;(YYYY-MM-DD)</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje máximo de cuenta contingente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(13).toString()+"%"+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje de componente inflacionario</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(14).toString()+"%"+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje de retención en la fuente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(15).toString()+"%"+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje de retención sobre la comisión</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(16).toString()+"%"+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor mínimo a retirar</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><B>$</B>"+NumberFormat.getInstance().format(new Float(v_descripcion.elementAt(17).toString()))+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Días de canje</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(18).toString()+"&nbsp;(días)"+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Identificador contable</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(19).toString()+"</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Eliminar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      //Código en HTML a mostrar en el navegador
      private void TBPL_PublicarModifica(Vector v_descripcion, Vector v_opciones, Vector v_opciones1, Vector v_opciones2, Vector v_opciones3, Vector v_opciones4)
      {
      v_opciones.trimToSize();
      v_opciones1.trimToSize();
      v_opciones2.trimToSize();
      v_opciones3.trimToSize();
      v_opciones4.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de productos", "Administración de productos","TBPKT_PARAMETROS.TBPKT_PRODUCTO.TBCS_ModificaProducto", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificar Producto</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(0).toString()+"<INPUT NAME=obligatoriocodigo VALUE='"+v_descripcion.elementAt(0).toString()+"' TYPE=hidden ></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Descripción del producto</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"<BR><INPUT NAME=obligatoriodescripcion VALUE='"+v_descripcion.elementAt(1).toString()+"' TYPE=text MAXLENGTH=50 SIZE=30></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Número de identificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"<BR><INPUT NAME=obligatorionumide VALUE='"+v_descripcion.elementAt(2).toString()+"' TYPE=text MAXLENGTH=13 SIZE=13></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Dígito de verificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"<BR><INPUT NAME=obligatoriodigver VALUE='"+v_descripcion.elementAt(3).toString()+"' TYPE=text MAXLENGTH=1 SIZE=1 onblur='if (esNumero(obligatoriodigver)==1) return false;'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Tipo de identificación</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(4).toString()+"<BR><SELECT NAME=obligatoriotipoide>");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         if (v_descripcion.elementAt(4).equals(v_opciones.elementAt(i+1).toString()))
           out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"' SELECTED>"+v_opciones.elementAt(i+1).toString());
         else
           out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Método orden</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(5).toString()+"<BR>");
      if (v_descripcion.elementAt(5).toString().equals("LIFO,"))
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatoriometord><OPTION SELECTED VALUE='SMO002'>LIFO<OPTION VALUE='SMO001'>FIFO<OPTION VALUE='SMO003'>APORTES SELECCIONADOS</SELECT></font></TD></TR>");
      else if (v_descripcion.elementAt(5).toString().equals("FIFO,"))
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatoriometord><OPTION VALUE='SMO002'>LIFO<OPTION SELECTED VALUE='SMO001'>FIFO<OPTION VALUE='SMO003'>APORTES SELECCIONADOS</SELECT></font></TD></TR>");
      else if (v_descripcion.elementAt(5).toString().equals("APORTES SELECCIONADOS,"))
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatoriometord><OPTION VALUE='SMO002'>LIFO<OPTION VALUE='SMO001'>FIFO<OPTION SELECTED VALUE='SMO003'>APORTES SELECCIONADOS</SELECT></font></TD></TR>");
      else if (v_descripcion.elementAt(5).toString().equals("LIFO,FIFO,"))
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatoriometord><OPTION SELECTED VALUE='SMO002'>LIFO<OPTION SELECTED VALUE='SMO001'>FIFO<OPTION VALUE='SMO003'>APORTES SELECCIONADOS</SELECT></font></TD></TR>");
      else if (v_descripcion.elementAt(5).toString().equals("LIFO,FIFO,APORTES SELECCIONADOS,"))
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatoriometord><OPTION SELECTED VALUE='SMO002'>LIFO<OPTION SELECTED VALUE='SMO001'>FIFO<OPTION SELECTED VALUE='SMO003'>APORTES SELECCIONADOS</SELECT></font></TD></TR>");
      else if (v_descripcion.elementAt(5).toString().equals("LIFO,APORTES SELECCIONADOS,"))
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatoriometord><OPTION SELECTED VALUE='SMO002'>LIFO<OPTION VALUE='SMO001'>FIFO<OPTION SELECTED VALUE='SMO003'>APORTES SELECCIONADOS</SELECT></font></TD></TR>");
      else if (v_descripcion.elementAt(5).toString().equals("FIFO,APORTES SELECCIONADOS,"))
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatoriometord><OPTION VALUE='SMO002'>LIFO<OPTION SELECTED VALUE='SMO001'>FIFO<OPTION SELECTED VALUE='SMO003'>APORTES SELECCIONADOS</SELECT></font></TD></TR>");
      else
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatoriometord><OPTION VALUE='SMO002'>LIFO<OPTION SELECTED VALUE='SMO001'>FIFO<OPTION SELECTED VALUE='SMO003'>APORTES SELECCIONADOS</SELECT></font></TD></TR>");

      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Método beneficio</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(6).toString()+"<BR><SELECT NAME=obligatoriometben>");
      if (v_descripcion.elementAt(6).equals("SI"))
        out.println("<OPTION VALUE=S SELECTED>S<OPTION VALUE=N>N");
      else
        out.println("<OPTION VALUE=S>S<OPTION VALUE=N SELECTED>N");
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Método penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(7).toString()+"<BR><SELECT NAME=obligatoriometpen>");
      if (v_descripcion.elementAt(7).equals("SI"))
        out.println("<OPTION VALUE=S SELECTED>S<OPTION VALUE=N>N");
      else
        out.println("<OPTION VALUE=S>S<OPTION VALUE=N SELECTED>N");
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Método de cuenta contingente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(8).toString()+"<BR><SELECT NAME=obligatoriometcue>");
      if (v_descripcion.elementAt(8).equals("SI"))
        out.println("<OPTION VALUE=S SELECTED>S<OPTION VALUE=N>N");
      else
        out.println("<OPTION VALUE=S>S<OPTION VALUE=N SELECTED>N");
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Naturaleza del retiro</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(9).toString()+"<BR>");
      if (v_descripcion.elementAt(9).toString().equals("CAPITAL,"))
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatorionatret><OPTION SELECTED VALUE='SNR002'>CAPITAL<OPTION VALUE='SNR001'>RENDIMIENTOS<OPTION VALUE='SNR003'>PROPORCIONAL</SELECT></font></TD></TR>");
      else if (v_descripcion.elementAt(9).toString().equals("RENDIMIENTOS,"))
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatorionatret><OPTION VALUE='SNR002'>CAPITAL<OPTION SELECTED VALUE='SNR001'>RENDIMIENTOS<OPTION VALUE='SNR003'>PROPORCIONAL</SELECT></font></TD></TR>");
      else if (v_descripcion.elementAt(9).toString().equals("PROPORCIONAL,"))
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatorionatret><OPTION VALUE='SNR002'>CAPITAL<OPTION VALUE='SNR001'>RENDIMIENTOS<OPTION SELECTED VALUE='SNR003'>PROPORCIONAL</SELECT></font></TD></TR>");
      else if (v_descripcion.elementAt(9).toString().equals("CAPITAL,RENDIMIENTOS,"))
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatorionatret><OPTION SELECTED VALUE='SNR002'>CAPITAL<OPTION SELECTED VALUE='SNR001'>RENDIMIENTOS<OPTION VALUE='SNR003'>PROPORCIONAL</SELECT></font></TD></TR>");
      else if (v_descripcion.elementAt(9).toString().equals("CAPITAL,PROPORCIONAL,"))
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatorionatret><OPTION SELECTED VALUE='SNR002'>CAPITAL<OPTION VALUE='SNR001'>RENDIMIENTOS<OPTION SELECTED VALUE='SNR003'>PROPORCIONAL</SELECT></font></TD></TR>");
      else if (v_descripcion.elementAt(9).toString().equals("RENDIMIENTOS,PROPORCIONAL,"))
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatorionatret><OPTION VALUE='SNR002'>CAPITAL<OPTION SELECTED VALUE='SNR001'>RENDIMIENTOS<OPTION SELECTED VALUE='SNR003'>PROPORCIONAL</SELECT></font></TD></TR>");
      else if (v_descripcion.elementAt(9).toString().equals("CAPITAL,RENDIMIENTOS,PROPORCIONAL,"))
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatorionatret><OPTION SELECTED VALUE='SNR002'>CAPITAL<OPTION SELECTED VALUE='SNR001'>RENDIMIENTOS<OPTION SELECTED VALUE='SNR003'>PROPORCIONAL</SELECT></font></TD></TR>");
      else
        out.println("<SELECT MULTIPLE SIZE=3 NAME=obligatorionatret><OPTION SELECTED VALUE='SNR002'>CAPITAL<OPTION SELECTED VALUE='SNR001'>RENDIMIENTOS<OPTION SELECTED VALUE='SNR003'>PROPORCIONAL</SELECT></font></TD></TR>");

      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje máximo de cuenta contingente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(13).toString()+"&nbsp;%<BR><INPUT NAME=maxctacon VALUE='"+v_descripcion.elementAt(13).toString()+"' TYPE=text MAXLENGTH=5 SIZE=5 onblur=\"if (esNumero(maxctacon)==1) return false;checkDecimals(v_codigo.maxctacon,v_codigo.maxctacon.value, 2),esMayor(maxctacon)\">&nbsp;&nbsp;%</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje de componente inflacionario</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(14).toString()+"&nbsp;%<BR><INPUT NAME=cominf VALUE='"+v_descripcion.elementAt(14).toString()+"' TYPE=text MAXLENGTH=5 SIZE=5 onblur=\"if (esNumero(cominf)==1) return false;checkDecimals(v_codigo.cominf,v_codigo.cominf.value, 2),esMayor(cominf)\">&nbsp;&nbsp;%</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje de retención en la fuente</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(15).toString()+"&nbsp;%<BR><INPUT NAME=retfue VALUE='"+v_descripcion.elementAt(15).toString()+"' TYPE=text MAXLENGTH=5 SIZE=5 onblur=\"if (esNumero(retfue)==1) return false;checkDecimals(v_codigo.retfue,v_codigo.retfue.value, 2),esMayor(retfue)\">&nbsp;&nbsp;%</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje de retención sobre la comisión</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(16).toString()+"<BR><INPUT NAME=porcomret VALUE='"+v_descripcion.elementAt(16).toString()+"' TYPE=text MAXLENGTH=5 SIZE=5 onblur=\"if (esNumero(porcomret)==1) return false;checkDecimals(v_codigo.porcomret,v_codigo.porcomret.value, 2),esMayor(porcomret)\">&nbsp;&nbsp;%</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Valor mínimo a retirar</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(17).toString()+"<BR><B>$</B><INPUT NAME=retmin VALUE='"+v_descripcion.elementAt(17).toString()+"' TYPE=text MAXLENGTH=15 SIZE=15 onBlur=\"if (esNumero(retmin)==1) return false;esNegativo(retmin)\"></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Días de canje</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(18).toString()+"&nbsp;(días)<BR><INPUT NAME=diacanje VALUE='"+v_descripcion.elementAt(18).toString()+"' TYPE=text MAXLENGTH=2 SIZE=2 onblur='if (esNumero(diacanje)==1) return false;checkDecimals(v_codigo.diacanje,v_codigo.diacanje.value, 0),esNegativo(diacanje)'>&nbsp;&nbsp;(días)</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Identificador contable</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(19).toString()+"<BR><SELECT NAME=idecont>");
      for (int i=0; i<v_opciones2.size(); i+=2)
         {
         if (v_descripcion.elementAt(19).equals(v_opciones2.elementAt(i+1).toString()))
           out.println("<OPTION VALUE='"+v_opciones2.elementAt(i).toString()+"' SELECTED>"+v_opciones2.elementAt(i+1).toString());
         else
           out.println("<OPTION VALUE='"+v_opciones2.elementAt(i).toString()+"'>"+v_opciones2.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("</TABLE><BR>");


      if (!v_descripcion.elementAt(12).toString().equalsIgnoreCase("null"))
        {
        out.println("<INPUT NAME=obligatoriofecben VALUE='"+(v_descripcion.elementAt(12).toString().length()<10?v_descripcion.elementAt(12).toString():v_descripcion.elementAt(12).toString().substring(0,10))+"' TYPE=hidden>");
        out.println("<INPUT NAME=obligatoriocantie VALUE='"+v_descripcion.elementAt(10).toString()+"' TYPE=hidden>");
        out.println("<INPUT NAME=unitie VALUE='"+TBFL_ConsultaCodigo(v_descripcion.elementAt(11).toString(),"SUT")+"' TYPE=hidden>");
        }
      else
        {
        out.println("<INPUT NAME=obligatoriofecben TYPE=hidden VALUE=''>");
        out.println("<INPUT NAME=obligatoriocantie TYPE=hidden VALUE=''>");
        out.println("<INPUT NAME=unitie TYPE=hidden VALUE=''>");
        }

      out.println("<BLOCKQUOTE>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='con'>Modificar fecha y cantidad de tiempo del beneficio tributario<BR>");
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='eliminar'>Eliminar fecha  y cantidad de tiempo del beneficio tributario<BR>");
      out.println("<INPUT TYPE=hidden NAME=v_opcion VALUE='sin'>");
      out.println("</font>");
      out.println("</BLOCKQUOTE>");
      out.println("<FONT color=black face=verdana size=1><B><SUP>*</SUP>Razón de la modificación:</B></font><BR>");
      out.println("<FONT color=black face=verdana size=1><TEXTAREA NAME=obligatoriorazmod ROWS=4 COLS=30 WRAP=HARD></TEXTAREA></font><BR>&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER><BR>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



  private String TBFL_ConsultaCodigo(String v_valor, String cod)
  {
  String v_declaracion;
   //TBCL_Consulta v_Consulta = new TBCL_Consulta();
    //v_Consulta.TBPL_RealizarConexion();
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion;
    v_declaracion =  "SELECT ref_codigo FROM tbreferencias WHERE ref_descripcion = '"+v_valor+"' AND ref_codigo LIKE '"+cod+"%'";
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, "ref_codigo");
    //v_Consulta.TBPL_shutDown();
    return  v_resultadodeclaracion.elementAt(0).toString();
  }




  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_PRODUCTO.TBCS_ACEMProducto Information";
  }
}

