package TBPKT_PARAMETROS.TBPKT_PRODUCTOCONDICION;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

//Este servlet carga los datos del producto con sus datos de condiciones de penalizacion
public class TBCS_CargaProductoCondicion extends HttpServlet implements SingleThreadModel
{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
private String v_nuevaCadena ="";


  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  /**
   * Process the HTTP GET request
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
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
  String v_codigoproducto = "";
  String programa = "";
  String Consulta,Consulta1, Consulta2;

  try{
     v_codigoproducto = request.getParameter("v_codigo");
     programa = request.getParameter("programa");
     }
  catch (Exception e) { e.printStackTrace();
     }
  v_Consulta.TBPL_RealizarConexion();//Reliza conexion con la base de datos

  //Sentencias SQL
  Consulta = "SELECT pro_descripcion FROM tbproductos WHERE pro_codigo = "+"'" +v_codigoproducto+"'";//Descripción del producto
  Vector v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta, "pro_descripcion");
  //Descripcion de las condiciones por producto
  Consulta = "SELECT orc_cop_codigo FROM tbproductos_condiciones WHERE orc_pro_codigo ='"+
              v_codigoproducto+"' AND orc_prg_codigo ='"+ programa +
              "' ORDER BY orc_cop_codigo";
  Vector resultadoConsulta1 = v_Consulta.TBFL_Consulta(Consulta, "orc_cop_codigo");
  //Descripcion de todas las condiciones
/*  Consulta = "SELECT cop_descripcion FROM tbcondiciones_penalizacion ORDER BY cop_descripcion";
  Vector resultadoConsulta2 = v_Consulta.TBFL_Consulta(Consulta, "cop_descripcion");*/

  /* Modificado por Marcela Ortiz Sandoval para incluir el programa*/ 
   Consulta ="SELECT cop_codigo, cop_descripcion FROM tbcondiciones_penalizacion \n"+
            " WHERE cop_codigo NOT IN ( \n"+
            " SELECT orc_cop_codigo FROM tbproductos_condiciones WHERE orc_pro_codigo = '"+v_codigoproducto+"' \n"+
            " AND orc_prg_codigo ='"+ programa +"')";
   /*Fin del segmento modificado*/
   
   Vector resultadoConsulta2 = v_Consulta.TBFL_Consulta(Consulta);



  //publicacion en pagina html de las Consultas
  TBPL_Publicar(v_codigoproducto, programa, v_resultadoConsulta, resultadoConsulta1, resultadoConsulta2);
  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  }



  //Codigo html que se mostrara en el browser
  private void TBPL_Publicar(String codprod, String programa, Vector v_descripcion, Vector v_codconpen, Vector v_desconpen)
  {
  v_descripcion.trimToSize();
  v_codconpen.trimToSize();
  v_desconpen.trimToSize();

  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de las condiciones de penalización por productos","Administración de las condiciones de penalización por productos", "TBPKT_PARAMETROS.TBPKT_PRODUCTOCONDICION.TBCS_AEProductoCondicion", "", true));

  //Aqui van los campo que se quieren mostrar
  out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
  out.println("<BLOCKQUOTE>");
  out.println("<B>Código del producto:</B>&nbsp;&nbsp;"+codprod+"<INPUT TYPE=hidden NAME=v_codigoproducto value='"+codprod+"'><BR>");
  /* Modificado por Marcela Ortiz Sandoval para incluir el programa*/ 
  out.println("<B>Código del programa:</B>&nbsp;&nbsp;"+programa+"<INPUT TYPE=hidden NAME=programa value='"+programa+"'><BR>");
  /* Fin del segmento agregado */
  out.println("<B>Descripción del producto:</B>&nbsp;&nbsp;"+v_descripcion.elementAt(0).toString());
  out.println("</BLOCKQUOTE>");
  out.println("</font>");
  out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
              "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Condiciones de penalización para&nbsp;"+codprod+"</B></FONT></CENTER></TD></TR></TBODY></TABLE>");

  if (!(v_codconpen.elementAt(0).toString().startsWith("Hubo algun  error al realizar su transacción, retorne y verifique")||v_codconpen.elementAt(0).toString().equals("No hay elementos")))
    {
    out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=3 rules=all width=\"100%\">");
    out.println("<TR class=\"td11\" borderColor=silver><TH width=\"5%\"><FONT color=white face=verdana size=1>&nbsp;</font></TH><TH width=\"22%\"><FONT color=white face=verdana size=1>Código</font></TH><TH width=\"22%\"><FONT color=white face=verdana size=1>Descripción</font></TH></TR>");
    for (int i=0; i<v_codconpen.size(); i++)
       {
       if (i<1)
         out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=codcondpen CHECKED VALUE='"+v_codconpen.elementAt(i).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codconpen.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+TBFL_ConsultaDescripcion(v_codconpen.elementAt(i).toString())+"</font></TD></TR>");
       else
         out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=codcondpen VALUE='"+v_codconpen.elementAt(i).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_codconpen.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+TBFL_ConsultaDescripcion(v_codconpen.elementAt(i).toString())+"</font></TD></TR>");
       }
    out.println("</TABLE>");
    out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
    out.println("<BLOCKQUOTE>");
    if ((v_desconpen.elementAt(0).toString().startsWith("Hubo algun  error al realizar su transacción, retorne y verifique")||v_desconpen.elementAt(0).toString().equals("No hay elementos")))
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='eliminacion' CHECKED>Eliminar Condición de penalización");
    else
      out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='eliminacion'>Eliminar Condición de penalización");
    out.println("</BLOCKQUOTE>");
    out.println("</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
    }


  out.println("<BR>&nbsp;&nbsp;<BR>");


//------------------------------------------------------------------
  out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
              "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Condiciones de penalización para adicionar</B></FONT></CENTER></TD></TR></TBODY></TABLE>");

  if (!(v_desconpen.elementAt(0).toString().startsWith("Hubo algun  error al realizar su transacción, retorne y verifique")||v_desconpen.elementAt(0).toString().equals("No hay elementos")))
    {
    out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=3 rules=all width=\"100%\">");
    out.println("<TR class=\"td11\" borderColor=silver><TH width=\"5%\"><FONT color=white face=verdana size=1>&nbsp;</font></TH><TH width=\"22%\"><FONT color=white face=verdana size=1>Código</font></TH><TH width=\"22%\"><FONT color=white face=verdana size=1>Descripción</font></TH></TR>");
    for (int i=0; i<v_desconpen.size(); i+=2)
       {
       if (i<2)
         out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=RADIO CHECKED NAME=v_desconpen VALUE='"+v_desconpen.elementAt(i).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_desconpen.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_desconpen.elementAt(i+1).toString()+"</font></TD></TR>");
       else
         out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=RADIO NAME=v_desconpen VALUE='"+v_desconpen.elementAt(i).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_desconpen.elementAt(i).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_desconpen.elementAt(i+1).toString()+"</font></TD></TR>");
       }
    out.println("</TABLE>");
    out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
    out.println("<BLOCKQUOTE>");
    out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adicion' CHECKED>Adicionar condición de penalización");
    out.println("</BLOCKQUOTE>");
    out.println("</font>");
    }

  out.println("<BR>&nbsp;&nbsp;<BR>");
  out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");









  //Hasta aca van los campos
  out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();

  }


  private String TBFL_ConsultaDescripcion(String cod)
  {
  String v_declaracion = "SELECT cop_descripcion FROM tbcondiciones_penalizacion WHERE cop_codigo = '"+cod+"'";
  Vector v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, "cop_descripcion");
  return (v_resultadodeclaracion.elementAt(0).toString());
  }
  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo()
  {
    return "TBPKT_PRODUCTOCONDICION.TBCS_CargaProductoCondicion Information";
  }
}

