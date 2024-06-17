package TBPKT_PARAMETROS.TBPKT_PRODUCTOCONDICION;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_AEProductoCondicion extends HttpServlet
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
   * Process the HTTP Get request
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
        
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
       parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

    v_Consulta = new TBCL_Consulta();
    String v_declaracion = new String();
    Vector v_resultadodeclaracion = new Vector();
    String declaracion1 = new String();

    //Me v_confirma si se va a adicionar o eliminar una condición de penalización al producto
    String v_addblot = "";
    //Resive el v_valor de los codigos de penalizacion a eliminar
    String v_codconpen = "";
    //Resive los valores de descripción de todas las condiciones de penalizacion para adicionar
    String v_desconpen = "";
    //Toma el v_codigo del producto
    String v_codigoproducto = "";
    //MOS 20080826 Variable para tomar el programa
    String programa ="";

    try
    {
    v_addblot = request.getParameter("v_opcion");
    if (v_addblot.equals("adicion"))
      v_desconpen = request.getParameter("v_desconpen");
    else
      v_codconpen = request.getParameter("codcondpen");
    v_codigoproducto = request.getParameter("v_codigoproducto");
    programa = request.getParameter("programa");
    }
    catch (Exception e) { e.printStackTrace(); }

  v_Consulta.TBPL_RealizarConexion();//Reliza conexion con la base de datos

  if (v_addblot.equals("adicion"))
    {
    //Sentencias SQL
    /* Modificado por Marcela Ortiz Sandoval para incluir el programa*/ 
    //v_declaracion = "INSERT INTO tbproductos_condiciones (orc_pro_codigo, orc_cop_codigo, orc_prg_codigo) VALUES (UPPER('"+v_codigoproducto+"'), UPPER('"+v_desconpen+"'), UPPER('"+programa+"'))";
    v_declaracion = "INSERT INTO tbproductos_condiciones VALUES ('"+v_codigoproducto+"', '"+v_desconpen+"', '"+programa+"')";
    /* Fin del segmento modificado */
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }
  else
    {
    //Sentencias SQL
    /* Modificado por Marcela Ortiz Sandoval para incluir el programa en la busqueda */ 
    v_declaracion = "DELETE tbproductos_condiciones WHERE orc_pro_codigo = '"+v_codigoproducto+"' and orc_cop_codigo = '"+v_codconpen+"' and orc_prg_codigo='"+programa+"'";
    /* Fin del segmento modificado */
    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, false);
    }


  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //publicacion en pagina html de las Consultas
  TBPL_Publicar(v_resultadodeclaracion);
  }



  //Codigo html que se mostrara en el browser
  private void TBPL_Publicar(Vector resultado)
  {
  resultado.trimToSize();
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de las condiciones de penalización por productos","Administración de las condiciones de penalización por productos", "", "<BLOCKQUOTE><BR>"+resultado.elementAt(0).toString()+"</BLOCKQUOTE>", false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo()
  {
    return "TBPKT_PRODUCTOCONDICION.TBCS_AEProductoCondicion Information";
  }
}

