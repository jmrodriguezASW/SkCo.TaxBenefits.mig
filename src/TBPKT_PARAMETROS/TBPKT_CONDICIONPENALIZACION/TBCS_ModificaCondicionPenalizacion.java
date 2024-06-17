package TBPKT_PARAMETROS.TBPKT_CONDICIONPENALIZACION;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108

//Nombre de la clase que modificará la condición penalización
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_ModificaCondicionPenalizacion extends HttpServlet{
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
       // response.setHeader("Pragma", "No-Cache");
       // response.setDateHeader("Expires", 0);
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


    //Este es el código de la condicon de penalizacion
    String v_codigo = "";
    //Esta es la v_descripcion de la condición de penalización
    String v_descripcion = "";
    //Esta variable contiene la cantidad de tiempo
    String cantiempo = "";
    //Esta contiene la base  de años o meses
    String tiempo = "";
    //Contiene el porcentaje
    String porcentaje = "";
    //Este es el código por default de la condicon de penalizacion
    String v_ddescripcion = "";
    //Esta variable contiene la cantidad de tiempo por default
    String dcantiempo = "";
    //Esta contiene la base  de años o meses por default
    String dtiempo = "";
    //Contiene el porcentaje por default
    String dporcentaje = "";


    //Toma los parametros que se van a modificar
    /*       try{
       v_codigo = request.getParameter("v_codigo");
       v_descripcion = request.getParameter("obligatoriodescripcion");
       cantiempo = request.getParameter("obligatoriocantidadtiempo");
       tiempo = request.getParameter("tipoidentificacion");
       porcentaje = request.getParameter("obligatorioporcentaje");
       v_ddescripcion = request.getParameter("v_ddescripcion");
       dcantiempo = request.getParameter("dcantidadtiempo");
       dtiempo = request.getParameter("dtipoidentificacion");
       dporcentaje = request.getParameter("dporcentaje");
       }
    catch (Exception e) { e.printStackTrace(); }*///INT20131108
    try{
       v_codigo = request.getParameter("v_codigo");
       CleanResults cr1 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
       v_descripcion = cr1.getCleanHTML();
       cantiempo = request.getParameter("obligatoriocantidadtiempo");
       tiempo = request.getParameter("tipoidentificacion");
       porcentaje = request.getParameter("obligatorioporcentaje");
       CleanResults cr2 = as.scan(request.getParameter("v_ddescripcion"), new File(POLICY_FILE_LOCATION));
       v_ddescripcion = cr2.getCleanHTML();
       dcantiempo = request.getParameter("dcantidadtiempo");
       dtiempo = request.getParameter("dtipoidentificacion");
       dporcentaje = request.getParameter("dporcentaje");
       }
    catch (Exception e) { e.printStackTrace(); }

    //Verifica si el porcentaje es mayor a cero y menor a 100
    Float fporcentaje = new Float(porcentaje);
    if (fporcentaje.floatValue()<0||fporcentaje.floatValue()>100)
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administracion de las condiciones de penalización","Administracion de las condiciones de penalización", "", "No debe ingresar porcentajes mayores a 100 o menosres a 0", false));
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }
    //Realiza la modificación de la condición penalización
    else
      {
      v_Consulta.TBPL_RealizarConexion();//Reliza conexion con la base de datos

      if (v_descripcion.equals(" "))
        v_descripcion=v_ddescripcion;
      if (cantiempo.equals(" "))
        cantiempo=dcantiempo;
      if (tiempo.equals(" "))
        tiempo=dtiempo;
      if (porcentaje.equals(" "))
        porcentaje=dporcentaje;


      if ((v_codigo!=null)&&(v_descripcion!=null)&&(cantiempo!=null)&&(tiempo!=null)&&(porcentaje!=null))
        {
        //Sentencias SQL
        v_declaracion = "UPDATE tbcondiciones_penalizacion set "+
                  "cop_descripcion = UPPER('"+v_descripcion+"')"+
                  " , cop_cantidad_tiempo = "+cantiempo+
                  " , cop_porcentaje = "+porcentaje+
                  " , cop_ref_unidad_tiempo = UPPER('"+tiempo+"')"+
                  " WHERE cop_codigo = '"+v_codigo+"'";

        //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
        v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
        }
      else
        v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));

      v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
      //publicacion en pagina html de las Consultas
      TBPL_Publicar(v_resultadodeclaracion);
      }
  }




  //Codigo html que se mostrara en el browser
  private void TBPL_Publicar(Vector resultado)
  {
  resultado.trimToSize();
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administracion de las condiciones de penalización","Administracion de las condiciones de penalización", "", "<BLOCKQUOTE><BR>"+resultado.elementAt(0).toString()+"</BLOCKQUOTE>", false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-2);></FONT></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONDICIONPENALIZACION.TBCL_ModificaCondicionPenalizacion Information";
  }
}

