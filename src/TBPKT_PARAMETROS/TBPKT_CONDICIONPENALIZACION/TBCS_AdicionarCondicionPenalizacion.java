package TBPKT_PARAMETROS.TBPKT_CONDICIONPENALIZACION;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108

//Nombre de la clase 1que adiciona condición penalización
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_AdicionarCondicionPenalizacion extends HttpServlet{
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
    String cod1 = "";
    //Este es el código de la condicon de penalizacion
    String cod2 = "";
    //Este es el código de la condicon de penalizacion
    String cod3 = "";
    //Esta es la v_descripcion de la condición de penalización
    String v_descripcion = "";
    //Esta variable contiene la cantidad de tiempo
    String cantiempo = "";
    //Esta contiene la base  de años o meses
    String tiempo = "";
    //Contiene el porcentaje
    String porcentaje = "";
    //Contiene el porcentaje de aporte
    String porcentajeaporte = "";

    //Toma los parametros para poder adicionar
    
    /*           try{
    cod1 = request.getParameter("cod1");
    cod2 = request.getParameter("cod2");
    cod3 = request.getParameter("cod3");
    v_descripcion = request.getParameter("obligatoriodescripcion");
    cantiempo = request.getParameter("obligatoriocantidadtiempo");
    tiempo = request.getParameter("tipoidentificacion");
    porcentaje = request.getParameter("obligatorioporcentaje");
    }
    catch (Exception e) { e.printStackTrace(); }*///INT20131108
    try{
       cod1 = request.getParameter("cod1");
       cod2 = request.getParameter("cod2");
       cod3 = request.getParameter("cod3");
       CleanResults cr1 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
       v_descripcion = cr1.getCleanHTML();
       cantiempo = request.getParameter("obligatoriocantidadtiempo");
       tiempo = request.getParameter("tipoidentificacion");
       porcentaje = request.getParameter("obligatorioporcentaje");
       }
    catch (Exception e) { e.printStackTrace(); }

    //Verifica si el porcentaje es mayor a 100 o menos a cero para NO introducirlo en la BD
    Float fporcentaje = new Float(porcentaje);
    if (fporcentaje.floatValue()<0||fporcentaje.floatValue()>100)
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administracion de las condiciones de penalización","Administracion de las condiciones de penalización", "", "No debe ingresar porcentajes mayores a 100 o menosres a 0", false));
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }//---------------------------------------------
    //Si el porcentaje es menor a 100 o mayor a cero inserta la condición penalizacion
    else
      {
      v_Consulta.TBPL_RealizarConexion();//Reliza conexion con la base de datos

      //Inserta condición penalización
      if ((v_descripcion!=null)&&(cantiempo!=null)&&(tiempo!=null)&&(porcentaje!=null))
        {
        //Sentencias SQL
        v_declaracion = "INSERT INTO tbcondiciones_penalizacion (cop_codigo, cop_descripcion, cop_cantidad_tiempo, cop_porcentaje, cop_ref_unidad_tiempo) VALUES (UPPER('CON"+cod1+""+cod2+""+cod3+"'), UPPER('"+v_descripcion+"'), "+cantiempo+","+porcentaje+", UPPER('"+tiempo+"'))";
        //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
        v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
        }
      //Si no inserta con exito muestra un mensaje
      else
       {
       v_resultadodeclaracion.addElement(new String("Hubo algun error al adicionar los datos, verifique si todos los datos con el asterisco tienen datos"));
       }
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
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></FONT></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }




  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONDICIONPENALIZACION.TBCL_AdicionarCondicionPenalizacion Information";
  }
}

