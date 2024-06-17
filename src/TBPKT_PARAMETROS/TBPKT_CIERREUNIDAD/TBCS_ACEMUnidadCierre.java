package TBPKT_PARAMETROS.TBPKT_CIERREUNIDAD;//Paquete donde estan las clases de cierres de unidad

// Paquetes que se utilizarán en esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Nombre de la clase
public class TBCS_ACEMUnidadCierre extends HttpServlet{
private PrintWriter out;//Me permite mostrar el código HTML
private TBCL_Consulta v_Consulta;// Clase que se encargará de las Consultas, cerrar y abrir la conexión con la Base de datos
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
       response.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
       // response.setHeader("Pragma", "No-Cache");
       // response.setDateHeader("Expires", 0);
       String parametros[] = new String[8];
       String  cadena = request.getParameter("cadena");
       v_nuevaCadena = cadena;
       String ip_tax = request.getRemoteAddr();
        
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
       out = new PrintWriter (response.getOutputStream());
       parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}


    v_Consulta = new TBCL_Consulta();
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
    String v_declaracion;//Se encarga de todas las declaraciones SQL
    //Vectores donde se guardarán los resultados de las Consultas
    Vector v_resultadodeclaracion, v_resultadodeclaracion1, v_resultadodeclaracion2, v_resultadodeclaracion3;

    //Toma la v_opcion del servlet
    String v_opcion = "Consulta";
    //Toma el v_codigo del producto
    String v_codpro = "";
    //Toma la unidad de proceso
    String v_unipro = "";
    //Toma el código de la unidad de proceso
    String v_cunipro = "";


    //Toma los parametros que envia la pagina anterior
    try{
       v_opcion = request.getParameter("v_opcion");
       v_codpro = request.getParameter("v_codpro");
       v_cunipro = request.getParameter("v_unipro");
       }
    catch (Exception e) { e.printStackTrace(); }

    v_unipro = ConsultaDescripcion(v_cunipro);

   //Si la v_opcion es Consulta reliza las siguientes Consultas
   if (v_opcion.equals("Consulta"))
     {
     v_declaracion = "SELECT ciu_pro_codigo,\n"+
"       a.ref_descripcion, --ciu_ref_unidad_proceso,\n"+
"       b.ref_descripcion, --ciu_ref_tipo_usuario,\n"+
"       ciu_hora\n"+
"FROM   tbcierres_unidades,\n"+
"       tbreferencias a,\n"+
"       tbreferencias b\n"+
"WHERE  ciu_pro_codigo = '"+v_codpro+"'\n"+
"AND    ciu_ref_unidad_proceso = '"+v_cunipro+"'\n"+
"AND    ciu_ref_unidad_proceso = a.ref_codigo\n"+
"AND    ciu_ref_tipo_usuario = b.ref_codigo";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);


      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarConsulta(v_resultadodeclaracion);//Publica la pagina de Consulta
     }
   else if (v_opcion.equals("modifica"))//Si la v_opcion es modificar realiza las siguientes Consultas
     {
      v_declaracion = "SELECT ciu_ref_tipo_usuario, \n"+
                    "ciu_pro_codigo, \n"+
                    "ciu_ref_unidad_proceso, \n"+
                    "ciu_hora FROM tbcierres_unidades WHERE ciu_pro_codigo = '"+v_codpro+"' AND ciu_ref_unidad_proceso = '"+v_cunipro+"'";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);


     // v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarModifica(v_resultadodeclaracion, v_unipro);//Publica la página de modificar
     }
   else if (v_opcion.equals("adiciona"))//Si la opción es adicionar realiza las siguientes Consultas
     {
     v_declaracion =  "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'UTU%' AND ref_codigo != 'UTU000' ORDER BY ref_descripcion";
     v_resultadodeclaracion3 = v_Consulta.TBFL_Consulta(v_declaracion);

     //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
     TBPL_PublicarAdiciona(v_codpro, v_unipro, v_resultadodeclaracion3, v_cunipro);//Publica la página de adicionar
     }
   else if (v_opcion.equals("elimina"))//Si la opción es eliminar realiza la siguiente Consulta
     {
      v_declaracion = "SELECT ciu_ref_tipo_usuario, \n"+
                    "ciu_pro_codigo, \n"+
                    "ciu_ref_unidad_proceso, \n"+
                    "ciu_hora FROM tbcierres_unidades WHERE ciu_pro_codigo = '"+v_codpro+"' AND ciu_ref_unidad_proceso = '"+v_cunipro+"'";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);


      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      TBPL_PublicarElimina(v_resultadodeclaracion, v_unipro);//Publica la página de eliminar
     }

   v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }



      private void TBPL_PublicarConsulta(Vector v_descripcion)//Publica la pagina de Consulta
      {
      v_descripcion.trimToSize();

    if (!v_descripcion.elementAt(0).equals("No hay elementos"))
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cierre de transacciones por producto","Administración de cierre de transacciones por producto","","",true));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TH><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>Codigo producto</TH><TH><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>Unidad de proceso</TH><TH><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>Tipo de usuario</TH><TH><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>Hora de cierre</TH></TR>");
      int i = -1;
      while (i+4<v_descripcion.size())
        out.println("<TR bgColor=white borderColor=silver><TD><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD><TD><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+(v_descripcion.elementAt(++i).toString())+"</TD><TD><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+v_descripcion.elementAt(++i).toString()+"</TD></TR>");
      out.println("</TABLE>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      }
     else
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cierre de transacciones por producto","Administración de cierre de transacciones por producto","","<BLOCKQUOTE>No hay elementos</BLOCKQUOTE>",false));
      //Hasta aca van los campos
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);

      out.close();
      }

      private void TBPL_PublicarModifica(Vector v_descripcion, String v_unipro)//Publica la página de modificar
      {
      v_descripcion.trimToSize();

    if (!v_descripcion.elementAt(0).equals("No hay elementos"))
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cierre de transacción por producto","Administración de cierre de transacciones por producto","TBPKT_PARAMETROS.TBPKT_CIERREUNIDAD.TBCS_ConfirmaModificaUnidadCierre","",true,"moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=5 rules=all width=\"100%\">");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TH width=\"5%\"><font face=Verdana size='1'><font color='#000000'></TH><TH width=\"22%\"><font face=Verdana size='1'><font color='#000000'>Tipo de usuario</TH><TH width=\"22%\"><font face=Verdana size='1'><font color='#000000'>Código producto</TH><TH width=\"22%\"><font face=Verdana size='1'><font color='#000000'>Unidad de proceso</TH><TH width=\"22%\"><font face=Verdana size='1'><font color='#000000'>Hora de cierre</TH></TR>");
      int i = -1;
      while (i+4<v_descripcion.size())
        {
        if (i<3)
          out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'><INPUT TYPE=radio CHECKED NAME=tipusu VALUE='"+v_descripcion.elementAt(++i).toString()+"'></TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+ConsultaDescripcion(v_descripcion.elementAt(i).toString())+"</TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'><INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_descripcion.elementAt(i).toString()+"</TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>\n"+
                      "<INPUT TYPE=hidden NAME=v_unipro VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_unipro+"</TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'><INPUT TYPE=hidden NAME=hora VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_descripcion.elementAt(i).toString()+"</TD></TR>");
        else
          out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'><INPUT TYPE=radio NAME=tipusu VALUE='"+v_descripcion.elementAt(++i).toString()+"'></TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+ConsultaDescripcion(v_descripcion.elementAt(i).toString())+"</TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'><INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_descripcion.elementAt(i).toString()+"</TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>\n"+
                      "<INPUT TYPE=hidden NAME=v_unipro VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_unipro+"</TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'><INPUT TYPE=hidden NAME=hora VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_descripcion.elementAt(i).toString()+"</TD></TR>");
        }
      out.println("</TABLE><BR>&nbsp;&nbsp;</BR>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println("<CENTER><INPUT TYPE=submit VALUE='Confirma Modifica'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      }
    else
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cierre de transacciones por producto","Administración de cierre de transacciones por producto","","<BLOCKQUOTE>No hay elementos</BLOCKQUOTE>",false));
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-2);></CENTER>");
      }
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);

      out.close();
      }


      private void TBPL_PublicarAdiciona(String v_codpro, String v_unipro, Vector v_opciones3, String v_cunipro)//Publica la pagina de adicionar
      {
      v_opciones3.trimToSize();


      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cierre de transacción por producto","Administración de cierre de transacción por productos", "TBPKT_PARAMETROS.TBPKT_CIERREUNIDAD.TBCS_AdicionaCierreUnidad", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adicionar cierre de transacción por producto</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código del producto:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codpro+"<INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_codpro+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Unidad de proceso:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_unipro+"<INPUT TYPE=hidden NAME=v_unipro VALUE='"+v_cunipro+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Tipo de usuario:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><SELECT NAME=tipusu>");
      for (int i=0; i<v_opciones3.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones3.elementAt(i).toString()+"'>"+v_opciones3.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Hora de cierre:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>");
      out.println("<SELECT NAME=hora><OPTION>00<OPTION>01<OPTION>02<OPTION>03<OPTION>04<OPTION>05<OPTION>06<OPTION>07<OPTION>08<OPTION>09<OPTION>10<OPTION>11<OPTION>12<OPTION>13<OPTION>14<OPTION>15<OPTION>16<OPTION>17<OPTION>18<OPTION>19<OPTION>20<OPTION>21<OPTION>22<OPTION>23</SELECT>");
      out.println(":<SELECT NAME=min><OPTION>00<OPTION>01<OPTION>02<OPTION>03<OPTION>04<OPTION>05<OPTION>06<OPTION>07<OPTION>08<OPTION>09<OPTION>10<OPTION>11<OPTION>12<OPTION>13<OPTION>14<OPTION>15<OPTION>16<OPTION>17<OPTION>18<OPTION>19<OPTION>20<OPTION>21<OPTION>22<OPTION>23<OPTION>24<OPTION>25<OPTION>26<OPTION>27<OPTION>28<OPTION>29<OPTION>30");
      out.println("<OPTION>31<OPTION>32<OPTION>33<OPTION>34<OPTION>35<OPTION>36<OPTION>37<OPTION>38<OPTION>39<OPTION>40<OPTION>41<OPTION>42<OPTION>43<OPTION>44<OPTION>45<OPTION>46<OPTION>47<OPTION>48<OPTION>49<OPTION>50<OPTION>51<OPTION>52<OPTION>53<OPTION>54<OPTION>55<OPTION>56<OPTION>57<OPTION>58<OPTION>59</SELECT>(HH:MM)Militar</font></TD></TR>");
      //<INPUT TYPE=text NAME=obligatoriohora MAXLENGTH=5 SIZE=5 ONBLUR=\"return IsValidTime(document.v_codigo.obligatoriohora.value);\">
      out.println("</TABLE><BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



      private void TBPL_PublicarElimina(Vector v_descripcion, String v_unipro)//TBPL_Publicar la pagina de adicionar
      {
      v_descripcion.trimToSize();

    if (!v_descripcion.elementAt(0).equals("No hay elementos"))
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cierre de transacción por producto", "Administración de cierre de transacciones por producto", "TBPKT_PARAMETROS.TBPKT_CIERREUNIDAD.TBCS_ConfirmaEliminaUnidadCierre", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TH width=\"5%\"><font face=Verdana size='1'><font color='#000000'></TH><TH width=\"22%\"><font face=Verdana size='1'><font color='#000000'>Tipo de usuario</TH><TH width=\"22%\"><font face=Verdana size='1'><font color='#000000'>Código producto</TH><TH width=\"22%\"><font face=Verdana size='1'><font color='#000000'>Unidad de proceso</TH><TH width=\"22%\"><font face=Verdana size='1'><font color='#000000'>Hora de cierre</TH></TR>");
      int i = -1;

      while (i+4<v_descripcion.size())
        {
        if (i<3)
          out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'><INPUT TYPE=radio CHECKED NAME=tipusu VALUE='"+v_descripcion.elementAt(++i).toString()+"'></TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+ConsultaDescripcion(v_descripcion.elementAt(i).toString())+"</TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'><INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_descripcion.elementAt(i).toString()+"</TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>\n"+
                      "<INPUT TYPE=hidden NAME=v_unipro VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_unipro+"</TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'><INPUT TYPE=hidden NAME=hora VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_descripcion.elementAt(i).toString()+"</TD></TR>");
        else
          out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'><INPUT TYPE=radio NAME=tipusu VALUE='"+v_descripcion.elementAt(++i).toString()+"'></TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>"+ConsultaDescripcion(v_descripcion.elementAt(i).toString())+"</TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'><INPUT TYPE=hidden NAME=v_codpro VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_descripcion.elementAt(i).toString()+"</TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>\n"+
                      "<INPUT TYPE=hidden NAME=v_unipro VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_unipro+"</TD><TD width=\"22%\"><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'><INPUT TYPE=hidden NAME=hora VALUE='"+v_descripcion.elementAt(++i).toString()+"'>"+v_descripcion.elementAt(i).toString()+"</TD></TR>");
         }
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE='Confirma Elimina'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      }
    else
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cierre de transacciones por producto","Administración de cierre de transacciones por producto","","<BLOCKQUOTE>No hay elementos</BLOCKQUOTE>",false));
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-2);></CENTER>");
      }
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


  //Consulta el tipo de usuario en la tabla de referencias de la base de datos
  private String ConsultaDescripcion(String v_valor)
  {
  //v_Consulta = new TBCL_Consulta();
  String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion;
    //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
    v_declaracion =  "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+v_valor+"'";
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
    //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
    return  v_resultadodeclaracion.elementAt(0).toString();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CIERREUNIDAD.TBCS_ACEMUnidadCierre Information";
  }
}

