package TBPKT_PARAMETROS.TBPKT_CONTABILIDAD;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.GTBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import org.owasp.validator.html.*; // INT20131108


//Nombre de la clase que se encarga de modificar la contabilidad
import TBPKT_UTILIDADES.TBPKT_REFERENCIAS.TBCL_REFERENCIAS;// INT20131108

public class TBCS_ModificaContabilidad extends HttpServlet implements SingleThreadModel{
private PrintWriter out;
private TBCL_Consulta v_Consulta;


  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   * Process the HTTP Get request
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //INT20131108
      String rutaFisica = TBCL_REFERENCIAS.TBCL_CONSULTA("SMM003");
      //rutaFisica  =  "c:\\Taxbenefits\\Taxbenefits\\public_html\\WEB-INF\\";
      String POLICY_FILE_LOCATION = rutaFisica+"antisamy-tinymce-1.4.4.xml"; // Path to policy file
      AntiSamy as = new AntiSamy(); // INT20131108
       out = new PrintWriter (response.getOutputStream());
       response.setContentType("text/html");

  v_Consulta = new TBCL_Consulta();
  String v_declaracion = new String();
  //Vector donde se guardara el resultado de la Consulta
  Vector v_resultadodeclaracion = new Vector();
  Vector v_resultadodeclaracion1 = new Vector();
  v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos



    String v_codpro = "";
    String linmov = "";
    String dtipmov = "";
    String tipmov = "";
    String dcompania = "";
    String compania = "";
    String dcuentauno = "";
    String cuentauno = "";
    String dcuentados = "";
    String cuentados = "";
    String dsubcuentauno = "";
    String subcuentauno = "";
    String dsubcuentados = "";
    String subcuentados = "";
    String dunineg = "";
    String unineg = "";
    String dsigno = "";
    String signo = "";
    String v_ddescripcion = "";
    String v_descripcion = "";
    String dtipoiden = "";
    String tipoiden = "";
    String dnumiden = "";
    String numiden = "";
    String dparcont = "";
    String parcont = "";

    //Toma de los parametros a modificar
    /*             try{
          v_codpro = request.getParameter("v_codpro");
          linmov = request.getParameter("obligatoriolinmov");
          dtipmov = request.getParameter("dtipmov");
          tipmov = request.getParameter("tipomov");
          dcompania = request.getParameter("dcompania");
          compania = request.getParameter("obligatoriocompania");
          dcuentauno = request.getParameter("dcuentauno");
          cuentauno = request.getParameter("obligatoriocuentauno");
          dcuentados = request.getParameter("dcuentados");
          cuentados = request.getParameter("obligatoriocuentados");
          dsubcuentauno = request.getParameter("dsubcuentauno");
          subcuentauno = request.getParameter("obligatoriosubcuentauno");
          dsubcuentados = request.getParameter("dsubcuentados");
          subcuentados = request.getParameter("subcuentados");
          dunineg = request.getParameter("dunineg");
          unineg = request.getParameter("obligatoriounineg");
          dsigno = request.getParameter("dsigno");
          signo = request.getParameter("signo");
          v_ddescripcion = request.getParameter("v_ddescripcion");
          v_descripcion = request.getParameter("obligatoriodescripcion");
          out.println(v_ddescripcion);
          out.println(v_ddescripcion);
          dtipoiden = request.getParameter("dtipoiden");
          tipoiden = request.getParameter("tipoiden");
          dnumiden = request.getParameter("dnumiden");
          numiden = request.getParameter("numiden");
          dparcont = request.getParameter("dparcont");
          parcont = request.getParameter("parcont");
          }
       catch (Exception e) { e.printStackTrace(); }*///INT20131108
           try{
              v_codpro = request.getParameter("v_codpro");
              linmov = request.getParameter("obligatoriolinmov");
              dtipmov = request.getParameter("dtipmov");
              tipmov = request.getParameter("tipomov");
              dcompania = request.getParameter("dcompania");
              compania = request.getParameter("obligatoriocompania");
              dcuentauno = request.getParameter("dcuentauno");
              cuentauno = request.getParameter("obligatoriocuentauno");
              dcuentados = request.getParameter("dcuentados");
              cuentados = request.getParameter("obligatoriocuentados");
              dsubcuentauno = request.getParameter("dsubcuentauno");
              subcuentauno = request.getParameter("obligatoriosubcuentauno");
              dsubcuentados = request.getParameter("dsubcuentados");
              subcuentados = request.getParameter("subcuentados");
              dunineg = request.getParameter("dunineg");
              unineg = request.getParameter("obligatoriounineg");
              dsigno = request.getParameter("dsigno");
              signo = request.getParameter("signo");
              v_ddescripcion = request.getParameter("v_ddescripcion");
              CleanResults cr1 = as.scan(request.getParameter("v_ddescripcion"), new File(POLICY_FILE_LOCATION));
              v_ddescripcion = cr1.getCleanHTML();
              v_descripcion = request.getParameter("obligatoriodescripcion");
              CleanResults cr2 = as.scan(request.getParameter("obligatoriodescripcion"), new File(POLICY_FILE_LOCATION));
              v_descripcion = cr2.getCleanHTML();
              out.println(v_ddescripcion);
              out.println(v_ddescripcion);
              dtipoiden = request.getParameter("dtipoiden");
              tipoiden = request.getParameter("tipoiden");
              dnumiden = request.getParameter("dnumiden");
              numiden = request.getParameter("numiden");
              dparcont = request.getParameter("dparcont");
              parcont = request.getParameter("parcont");
              }
       catch (Exception e) { e.printStackTrace(); }

  if (tipmov.equals(""))
    tipmov = dtipmov;
  if (compania.equals(""))
    compania = dcompania;
  if (cuentauno.equals(""))
    cuentauno = dcuentauno;
  if (cuentados.equals(""))
    cuentados = dcuentados;
  if (subcuentauno.equals(""))
    subcuentauno = dsubcuentauno;
  if (subcuentados.equals(""))
    subcuentados = dsubcuentados;
  if (unineg.equals(""))
    unineg = dunineg;
  if (signo.equals(""))
    signo = dsigno;
  if (v_descripcion.equals(""))
    v_descripcion = v_ddescripcion;
  //if (tipoiden.equals(""))
   // tipoiden = dtipoiden;
  if (numiden.equals(""))
    numiden = dnumiden;
  if (parcont.equals(""))
    parcont = dparcont;
  if (subcuentados.equals("null"))
    subcuentados = "";


 //Modificación de administración contable
 if ((v_codpro!=null)&&(linmov!=null)&&(tipmov!=null)&&(compania!=null)&&(cuentauno!=null)&&(cuentados!=null)&&(subcuentauno!=null)&&(subcuentados!=null)&&(unineg!=null)&&(signo!=null)&&(v_descripcion!=null)&&(tipoiden!=null)&&(numiden!=null)&&(parcont!=null))
  {
    //Sentencias SQL
    v_declaracion = "UPDATE tbcontabilidades SET "+
                  "   cob_tipo_movimiento = UPPER('"+tipmov+"')"+
                  " , cob_compania = UPPER('"+compania+"')"+
                  " , cob_cuenta_uno = UPPER('"+cuentauno+"')"+
                  " , cob_cuenta_dos = UPPER('"+cuentados+"')"+
                  " , cob_subcuenta_uno = UPPER('"+subcuentauno+"')"+
                  " , cob_subcuenta_dos = DECODE(UPPER('"+subcuentados+"'),'NU',NULL,UPPER('"+subcuentados+"'))"+
                  " , cob_unidad_negocio = UPPER('"+unineg+"')"+
                  " , cob_signo = UPPER('"+signo+"')"+
                  " , cob_descripcion = UPPER('"+v_descripcion+"')"+
                  " , cob_tipo_identificacion = DECODE(SUBSTR('"+tipoiden+"',1,1),' ',NULL,UPPER('"+tipoiden+"')) "+
                  " , cob_numero_identificacion = DECODE(UPPER('"+numiden+"'),'NULL',NULL,UPPER('"+numiden+"'))"+
                  " , cob_ref_parametro_contable = UPPER('"+parcont+"')"+
                  " WHERE cob_pro_codigo = '"+v_codpro+"' AND cob_linea_movimiento = "+linmov;


    //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
    v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
    }//--------------------------------
  //Si hay algún error muestra un error
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }


  v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  //publicacion en pagina html de las Consultas
  TBPL_Publicar(v_resultadodeclaracion);
  }

  //Codigo html que se mostrara en el browser
  private void TBPL_Publicar(Vector resultado)
  {
  resultado.trimToSize();
  out.println(GTBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de parametrización contable de conceptos de transacciones por producto","Administración de parametrización contable de conceptos de transacciones por producto","", "<BLOCKQUOTE><BR>"+resultado.elementAt(0).toString()+"</BLOCKQUOTE>", false));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></CENTER>");
  out.println(GTBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }




  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONTABILIDAD.TBCS_ModificaContabilidad Information";
  }
}

