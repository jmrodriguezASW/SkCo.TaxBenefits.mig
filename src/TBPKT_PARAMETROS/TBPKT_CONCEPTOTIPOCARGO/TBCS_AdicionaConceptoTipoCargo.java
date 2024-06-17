package TBPKT_PARAMETROS.TBPKT_CONCEPTOTIPOCARGO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_AdicionaConceptoTipoCargo extends HttpServlet{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
private String v_declaracion = new String();
private Vector v_resultadodeclaracion = new Vector();
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
  v_Consulta.TBPL_RealizarConexion();

    //Toma el código del producto
    String v_codpro = "";
    String transac = "";
    String tipotransac = "";
    String clasetransac = "";



    //Toma los parametros
    try{
       String[] cargo = new String[request.getParameterValues("cargo").length];
       v_codpro = request.getParameter("v_codpro");
       transac = request.getParameter("transac");
       tipotransac = request.getParameter("tipotransac");
       clasetransac = request.getParameter("clasetransac");
       for (int i=0; i < request.getParameterValues("cargo").length; i++)
          cargo[i] = request.getParameterValues("cargo")[i];
       TBPL_Adicionar(v_codpro, transac, tipotransac, clasetransac, cargo);
       }
    catch (Exception e)
       {
       e.printStackTrace();
       TBPL_Publicar_Error();
       }
    
      v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  }


  private void TBPL_Adicionar(String v_codpro, String transac, String tipotransac, String clasetransac, String[] cargo)
  {
  //Se encarga de adicionar un concepto tipo cargo
  //-------------------------------------------------
  if ((v_codpro!=null)&&(transac!=null)&&(tipotransac!=null)&&(clasetransac!=null))
    {
    //Sentencias SQL
    for (int i=0; i<cargo.length; i++)
       {
       //Sentencias SQL
       v_declaracion = "INSERT INTO tbconceptos_tipos_cargo (ctc_prc_pro_codigo, ctc_prc_cot_ref_transaccion, ctc_prc_cot_ref_tipo_trans, ctc_prc_cot_ref_clase_trans, ctc_ref_cargo) VALUES (UPPER('"+v_codpro+"'), UPPER('"+transac+"'), UPPER('"+tipotransac+"'), UPPER('"+clasetransac+"'), UPPER('"+cargo[i]+"'))";
       //Vectores donde se guardaran los resultados de las Consultas para luego ser publicados
       v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, true);
       }
    }   //-----------------------------------------------
    //Si no se adiciona envia un mensaje de error
  else
    {
    v_resultadodeclaracion.addElement(new String("Hubo algún error al actualizar los datos, verifique todos los datos"));
    }

  //publicacion en pagina html de las Consultas
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cargos por concepto de transacciones","Administración de cargos por concepto de transacciones", "", v_resultadodeclaracion.elementAt(0).toString(), false));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-4);></FONT></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  private void TBPL_Publicar_Error()
  {
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de cargos por concepto de transacciones","Administración de cargos por concepto de transacciones", "", "Seleccione algún tipo cargo", false));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></FONT></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONCEPTOTIPOCARGO.TBCS_AdicionaConceptoTipoCargo Information";
  }
}

