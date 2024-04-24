package TBPKT_PARAMETROS.TBPKT_EMPRESA;//Paquete donde se guardarán las clases correspondientes a empresas

//importa todos los paquetes que utilizaremos en esta clase
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108


//Nombre de la clase principal de administración de empresas
public class TBCS_Empresa extends HttpServlet implements SingleThreadModel{
private PrintWriter out;//Clase que se encarga de imprimir el código HTML
private TBCL_Consulta v_Consulta;//Clase que se encarga de ejecutar las sentencias SQL
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
    String v_declaracion;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadodeclaracion = new Vector();
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


    //Toma la v_descripcion de la empresa
    String empresa = "";


    //Toma de los parametros
    /*        try{
       empresa = request.getParameter("empresa").toUpperCase().trim();
       }
    catch (Exception e) { e.printStackTrace(); }*///INT20131108
    try{
       CleanResults cr1 = as.scan(request.getParameter("empresa").toUpperCase().trim(), new File(POLICY_FILE_LOCATION));
       empresa = cr1.getCleanHTML();
       }
    catch (Exception e) { e.printStackTrace(); }

      //Realización de la Consulta para luego ser mostrada
      //------------------------------------------
      v_declaracion = "SELECT emp_grupo, emp_descripcion FROM tbempresas \n"+
                    "WHERE emp_descripcion LIKE '%"+empresa+"%'\n"+
                    "ORDER BY emp_descripcion";
      v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion);
      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
      //------------------------------
      PublicarConsultar(v_resultadodeclaracion);//TBPL_Publicar la pagina
  }


      private void PublicarConsultar(Vector v_opciones)//Publicación de la pagina HTML
      {
      v_opciones.trimToSize();

      if (!v_opciones.elementAt(0).toString().equals("No hay elementos"))
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas","Administración de empresas", "TBPKT_PARAMETROS.TBPKT_EMPRESA.TBCS_ACEMEmpresa", "", true, "moduloparametro.js", "return checkrequired(this)"));
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
        //Aqui van los campo que se quieren mostrar
        out.println("<BLOCKQUOTE>");
        out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='Consulta' CHECKED>Consultar empresa<BR>");
        out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='modifica'>Modificar empresa<BR>");
        out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='elimina'>Eliminar empresa<BR>");
        out.println("<INPUT TYPE=radio NAME=v_opcion VALUE='adiciona'>Adicionar empresa");
        out.println("<BR>&nbsp;&nbsp<BR>");
        out.println("<B>Descripción de la empresa:</B>&nbsp;&nbsp<SELECT NAME=v_descripcion>");
        for (int i=0; i<v_opciones.size(); i+=2)
           {
           out.println("<OPTION VALUE='"+v_opciones.elementAt(i).toString()+"'>"+v_opciones.elementAt(i+1).toString());
           }
        out.println("</SELECT>");
        out.println("<BR>&nbsp;&nbsp<BR>");
        out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
        out.println("</BLOCKQUOTE>");
        out.println("</font>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
        }
      else
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas","Administración de empresas", "", "<BLOCKQUOTE>"+v_opciones.elementAt(0).toString()+"</BLOCKQUOTE>", false));
        out.println("<BR>&nbsp;&nbsp<BR>");
        out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></CENTER>");
        }
      //Hasta aca van los campos
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_EMPRESA.TBCS_Empresa Information";
  }
}

