package TBPKT_PARAMETROS.TBPKT_CONDICIONPENALIZACION;//paquete donde se guardan todas las clases de condición penalizacion

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Nombre de la clase que toma las v_opciones de adicionar, modificar, eliminar o Consultar condición de penalización
public class TBCS_CondicionPenalizacion extends HttpServlet implements SingleThreadModel{
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
       // response.setHeader("Pragma", "No-Cache");
       // response.setDateHeader("Expires", 0);
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

    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

    //Dependiendo de la opción se Consulta, modifica, elimina y adiciona un producto
    String v_opcion = "";
    String codigocondicionpenalizacion = "";

    //Toma de los parametros de v_codigo de condición de penalización y la opción
    try {
        v_opcion = request.getParameter("accion");
        codigocondicionpenalizacion = request.getParameter("v_codigo");
        } catch (Exception e) { e.printStackTrace(); }
    response.setContentType("text/html");
    out = new PrintWriter (response.getOutputStream());

    if (v_opcion.equals("adicionar"))//Si se escoge adicionar hace la Consulta para la opción de adicionar
      {
      Consulta = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'SUT%' AND ref_codigo != 'SUT000'";
      v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);
      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      PublicarAdicionar(v_resultadoConsulta);//Publica la pagina de adicionar condición de penalización
      }
    else if (v_opcion.equals("eliminar"))//Si escoge eliminar realiza la Consulta para eliminar
      {
      Consulta = "SELECT cop_codigo,\n"+
"       cop_descripcion,\n"+
"       cop_cantidad_tiempo,\n"+
"       cop_porcentaje,\n"+
"       ref_descripcion\n"+
"FROM   tbcondiciones_penalizacion,\n"+
"       tbreferencias\n"+
"WHERE cop_codigo = '" +codigocondicionpenalizacion+"'\n"+
"AND    cop_ref_unidad_tiempo  = ref_codigo";

      //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
      v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);

      //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
      //publicacion en pagina html de las Consultas
      PublicarEliminar(codigocondicionpenalizacion, v_resultadoConsulta);//Publica pagina de eliminar
      }
    else if (v_opcion.equals("modificar"))//Si escoge modificar realiza la Consulta para modificar
      {
      //Sentencias SQL
      //selecciona todos los datos que hay en la tabla de condición penalizacion de la clave código
      Consulta = "SELECT cop_codigo,\n"+
"       cop_descripcion,\n"+
"       cop_cantidad_tiempo,\n"+
"       cop_porcentaje,\n"+
"       ref_descripcion\n"+
"FROM   tbcondiciones_penalizacion,\n"+
"       tbreferencias\n"+
"WHERE cop_codigo = '" +codigocondicionpenalizacion+"'\n"+
"AND    cop_ref_unidad_tiempo  = ref_codigo";

      //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
      v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);


      //Opciones de tiempo
      Consulta = "SELECT ref_codigo, ref_descripcion FROM tbreferencias WHERE ref_codigo LIKE 'SUT%' AND ref_codigo != 'SUT000'";
      resultadoConsulta1 = v_Consulta.TBFL_Consulta(Consulta);
      //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
      //publicacion en pagina html de las Consultas
      PublicarModificar(codigocondicionpenalizacion, v_resultadoConsulta, resultadoConsulta1);
      }
    else if (v_opcion.equals("Consultar"))//Si se selecciona Consultar realiza las Consultas para mostrar la Consulta de condición penalización
      {
      //Sentencias SQL
      //selecciona todos los datos que hay en la tabla de condición penalizacion de la clave código
      Consulta = "SELECT cop_codigo,\n"+
"       cop_descripcion,\n"+
"       cop_cantidad_tiempo,\n"+
"       cop_porcentaje,\n"+
"       ref_descripcion\n"+
"FROM   tbcondiciones_penalizacion,\n"+
"       tbreferencias\n"+
"WHERE cop_codigo = '" +codigocondicionpenalizacion+"'\n"+
"AND    cop_ref_unidad_tiempo  = ref_codigo";

      //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
      v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);


      //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
      //publicacion en pagina html de las Consultas
      PublicarConsultar(codigocondicionpenalizacion, v_resultadoConsulta);//Publica pagina de Consulta
      }
      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }




      private void PublicarAdicionar(Vector v_opciones)//Publicación de la pagina de adición condición penalización
      {
      v_opciones.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administracion de las condiciones de penalización", "Administracion de las condiciones de penalización", "TBPKT_PARAMETROS.TBPKT_CONDICIONPENALIZACION.TBCS_AdicionarCondicionPenalizacion", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Adicionar condición penalización</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Código de la condicion de penalizacion:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>CON<SELECT NAME=cod1><OPTION>0<OPTION>1<OPTION>2<OPTION>3<OPTION>4<OPTION>5<OPTION>6<OPTION>7<OPTION>8<OPTION>9</SELECT><SELECT NAME=cod2><OPTION>0<OPTION>1<OPTION>2<OPTION>3<OPTION>4<OPTION>5<OPTION>6<OPTION>7<OPTION>8<OPTION>9</SELECT><SELECT NAME=cod3><OPTION>0<OPTION>1<OPTION>2<OPTION>3<OPTION>4<OPTION>5<OPTION>6<OPTION>7<OPTION>8<OPTION>9</SELECT></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Descripción de la condición de penalización:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=TEXT  MAXLENGTH=50  NAME=obligatoriodescripcion  SIZE=20></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Cantidad de tiempo</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=TEXT  MAXLENGTH=3  NAME=obligatoriocantidadtiempo  SIZE=2 onblur=\"if (esNumero(obligatoriocantidadtiempo)==1) return false;checkDecimals(v_codigo.obligatoriocantidadtiempo,v_codigo.obligatoriocantidadtiempo.value, 0)\">&nbsp;&nbsp;<SELECT NAME=tipoidentificacion>");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Porcentaje:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1><INPUT TYPE=TEXT  MAXLENGTH=5  NAME=obligatorioporcentaje  VALUE=0 SIZE=3 onblur=\"if (esNumero(obligatorioporcentaje)==1) return false;checkDecimals(v_codigo.obligatorioporcentaje,v_codigo.obligatorioporcentaje.value, 2),esMayor(obligatorioporcentaje)\">%</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<CENTER><INPUT TYPE=submit VALUE=Adicionar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);><C/ENTER>");
      out.println("<BR><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }



  private void PublicarEliminar(String cod, Vector v_descripcion)//Publica la pagina de eliminar
  {
  v_descripcion.trimToSize();
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administracion de las condiciones de penalización","Administracion de las condiciones de penalización", "TBPKT_PARAMETROS.TBPKT_CONDICIONPENALIZACION.TBCS_EliminaCondicionPenalizacion", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Eliminar condición penalización</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código de la condición de penalizacion:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+cod+"<INPUT TYPE=hidden NAME=v_codigo value='"+cod+"'></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción de la condición de penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cantidad de tiempo:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"&nbsp;&nbsp;"+v_descripcion.elementAt(4).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"%</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Eliminar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
  }



  private void PublicarModificar(String cod, Vector v_descripcion, Vector v_opciones)//Publica la pagina de modificar
  {
  v_descripcion.trimToSize();
  v_opciones.trimToSize();

      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administracion de las condiciones de penalización", "Administracion de las condiciones de penalización","TBPKT_PARAMETROS.TBPKT_CONDICIONPENALIZACION.TBCS_ModificaCondicionPenalizacion", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Modificar condición penalización</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código de la condición de penalizacion:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+cod+"<INPUT TYPE=hidden NAME=v_codigo value='"+cod+"'>"+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Descripción de la condición de penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"<INPUT TYPE=hidden NAME=v_ddescripcion VALUE='"+v_descripcion.elementAt(1).toString()+"'>"+"<BR><INPUT TYPE=TEXT  MAXLENGTH=40  NAME=obligatoriodescripcion  SIZE=20 VALUE='"+v_descripcion.elementAt(1).toString()+"'></font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Cantidad de tiempo</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"<INPUT TYPE=hidden NAME=dcantidadtiempo VALUE='"+v_descripcion.elementAt(2).toString()+"'>&nbsp&nbsp"+v_descripcion.elementAt(4).toString()+"<INPUT TYPE=hidden NAME=dtipoidentificacion VALUE='"+v_descripcion.elementAt(4).toString()+"'><BR><INPUT TYPE=TEXT  MAXLENGTH=3  NAME=obligatoriocantidadtiempo  VALUE='"+v_descripcion.elementAt(2).toString()+"' SIZE=2 onblur=\"if (esNumero(obligatoriocantidadtiempo)==1) return false;checkDecimals(this,this.value, 0),esNegativo(obligatoriocantidadtiempo)\">&nbsp;&nbsp;<SELECT NAME=tipoidentificacion>");
      for (int i=0; i<v_opciones.size(); i+=2)
         {
         if (v_opciones.elementAt(i+1).toString().equals(v_descripcion.elementAt(4).toString()))
           out.println("<OPTION SELECTED VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         else
           out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
         }
      out.println("</SELECT></font></TD></TR>");

      out.println("</SELECT></font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><SUP>*</SUP><B>Porcentaje:</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"<INPUT TYPE=hidden NAME=dporcentaje VALUE='"+v_descripcion.elementAt(3).toString()+"'>%<BR><INPUT TYPE=TEXT  MAXLENGTH=5  NAME=obligatorioporcentaje VALUE='"+v_descripcion.elementAt(3).toString()+"' SIZE=3 onblur=\"if (esNumero(obligatorioporcentaje)==1) return false;checkDecimals(this,this.value, 2),esMayor(obligatorioporcentaje)\">%</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=submit VALUE=Modificar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER><BR>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>*Campos Obligatorios</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      //Hasta aca van los campos

      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
  }

  private void PublicarConsultar(String cod, Vector v_descripcion)//Publica la pagina de Consulta
  {
  v_descripcion.trimToSize();
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administracion de las condiciones de penalización","Administracion de las condiciones de penalización", "", "", true, "moduloparametro.js", "return checkrequired(this)"));
      //Aqui van los campo que se quieren mostrar
      out.println("<BR>&nbsp;&nbsp;<BR>");
      out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                  "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Consultar condición penalización</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
      out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Código de la condición de penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+cod+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Descripción de la condición de penalización</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(1).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Cantidad de tiempo</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(2).toString()+"&nbsp;&nbsp;"+v_descripcion.elementAt(4).toString()+"</font></TD></TR>");
      out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Porcentaje</B></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion.elementAt(3).toString()+"%</font></TD></TR>");
      out.println("</TABLE>");
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT SIZE=1 TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></FONT></CENTER>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
  }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONDICIONPENALIZACION.TBCL_CondicionPenalizacion Information";
  }
}

