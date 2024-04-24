package TBPKT_PARAMETROS.TBPKT_CONTABILIDAD;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108


//Nombre de la clase que se encarga de adicionar la condición penalización
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_AdicionaContabilidad extends HttpServlet{
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
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}
  v_Consulta = new TBCL_Consulta();
  String v_declaracion = new String();
  Vector v_resultadodeclaracion = new Vector();
  Vector v_resultadodeclaracion1 = new Vector();
  v_Consulta.TBPL_RealizarConexion();//Reliza conexion con la base de datos



    String v_codpro = "";
    String linmov = "";
    String tipmov = "";
    String compania = "";
    String cuentauno = "";
    String cuentados = "";
    String subcuentauno = "";
    String subcuentados = "";
    String unineg = "";
    String signo = "";
    String v_descripcion = "";
    String tipoiden = "";
    String numiden = "";
    String parcont = "";


    //Toma de los parametros
    /*         try{
       v_codpro = request.getParameter("v_codpro");
       linmov = request.getParameter("obligatoriolinmov");
       tipmov = request.getParameter("tipmov");
       compania = request.getParameter("obligatoriocompania");
       cuentauno = request.getParameter("obligatoriocuentauno");
       cuentados = request.getParameter("obligatoriocuentados");
       subcuentauno = request.getParameter("obligatoriosubcuentauno");
       subcuentados = request.getParameter("subcuentados");
       unineg = request.getParameter("obligatoriounineg");
       signo = request.getParameter("signo");
       v_descripcion = request.getParameter("obligatoriodescripcion");
       tipoiden = request.getParameter("tipoiden");
       numiden = request.getParameter("numiden");
       parcont = request.getParameter("parcont");
       }
    catch (Exception e) { e.printStackTrace(); }*///INT20131108
    try{
       v_codpro = request.getParameter("v_codpro");
       linmov = request.getParameter("obligatoriolinmov");
       tipmov = request.getParameter("tipmov");
       compania = request.getParameter("obligatoriocompania");
       cuentauno = request.getParameter("obligatoriocuentauno");
       cuentados = request.getParameter("obligatoriocuentados");
       subcuentauno = request.getParameter("obligatoriosubcuentauno");
       subcuentados = request.getParameter("subcuentados");
       unineg = request.getParameter("obligatoriounineg");
       signo = request.getParameter("signo");
       CleanResults cr1 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
       v_descripcion = cr1.getCleanHTML();
       tipoiden = request.getParameter("tipoiden");
       numiden = request.getParameter("numiden");
       parcont = request.getParameter("parcont");
       }
    catch (Exception e) { e.printStackTrace(); }



  //Adiciona la condición penalización
  //----------------------------------------------
  if ((v_codpro!=null)&&(linmov!=null)&&(tipmov!=null)&&(compania!=null)&&(cuentauno!=null)&&(cuentados!=null)&&(subcuentauno!=null)&&(subcuentados!=null)&&(unineg!=null)&&(signo!=null)&&(v_descripcion!=null)&&(tipoiden!=null)&&(numiden!=null))
    {
    //Sentencias SQL
    v_declaracion = " INSERT INTO tbcontabilidades \n"+
                    " (cob_pro_codigo \n"+
                    ", cob_linea_movimiento \n"+
                    ", cob_tipo_movimiento \n"+
                    ", cob_compania  \n"+
                    ", cob_cuenta_uno \n"+
                    ", cob_cuenta_dos  \n"+
                    ", cob_subcuenta_uno \n"+
                    ", cob_subcuenta_dos \n"+
                    ", cob_unidad_negocio \n"+
                    ", cob_signo \n"+
                    ", cob_descripcion \n"+
                    ", cob_tipo_identificacion \n"+
                    ", cob_numero_identificacion \n"+
                    ", cob_ref_parametro_contable) \n"+
                    " VALUES (UPPER('"+v_codpro+"') \n"+
                    ", "+linmov+"  \n"+
                    ", UPPER('"+tipmov+"') \n"+
                    ", UPPER('"+compania+"') \n"+
                    ", UPPER('"+cuentauno+"') \n"+
                    ", UPPER('"+cuentados+"') \n"+
                    ", UPPER('"+subcuentauno+"') \n"+
                    ", decode(UPPER('"+subcuentados+"'),'NU',NULL,UPPER('"+subcuentados+"')) \n"+
                    ", UPPER('"+unineg+"') \n"+
                    ", UPPER('"+signo+"') \n"+
                    ", UPPER('"+v_descripcion+"')  \n"+
                    ", DECODE(SUBSTR('"+tipoiden+"',1,1),' ',NULL,UPPER('"+tipoiden+"')) \n"+
                    ", DECODE(UPPER('"+numiden+"'),'NULL',NULL,UPPER('"+numiden+"')), UPPER('"+parcont+"'))";


    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }//------------------------------------------------------------------
  //Si hubo algún error muestra un mensaje de no adición
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algun error al adicionar los datos, verifique si todos los datos con el asterisco tienen datos"));
    }

  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //publicacion en pagina html del resultado
  TBPL_Publicar(v_resultadodeclaracion);

  }


  //Codigo html que se mostrara en el browser
  private void TBPL_Publicar(Vector resultado)
  {
  resultado.trimToSize();
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de parametrización contable de conceptos de transacciones por producto","Administración de parametrización contable de conceptos de transacciones por producto","", "<BLOCKQUOTE><BR>"+resultado.elementAt(0).toString()+"</BLOCKQUOTE>", false, "moduloparametro.js", "return checkrequired(this)"));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-3);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }



  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONTABILIDAD.TBCS_AdicionaContabilidad Information";
  }
}

