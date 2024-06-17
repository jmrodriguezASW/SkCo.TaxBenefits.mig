package TBPKT_PARAMETROS.TBPKT_PARAMETRIZACION_CONTRATOS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;


public class TBCS_Modifica_Contrato extends HttpServlet{
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
  String declaracion = new String();
  Vector resultadodeclaracion = new Vector();
  String declaracion1 = new String();
  v_Consulta.TBPL_RealizarConexion();

    //Toma el código del producto
    String v_codpro = "";
    String v_contrato = "";
    String metord = "";
    String metben = "";
    String metpen = "";
    String metcue = "";
    String naturaleza = "";
    String resnat = "";



    try{
       v_codpro = request.getParameter("v_codpro");
       v_contrato = request.getParameter("v_contrato");
       metord = request.getParameter("metord");
       metben = request.getParameter("metben");
       metpen = request.getParameter("metpen");
       metcue = request.getParameter("metcue");
       naturaleza = request.getParameter("naturaleza");
       resnat = request.getParameter("resnat");
       }
    catch (Exception e) { e.printStackTrace(); }



    if ((!metord.equals("null"))&&(!metben.equals("null"))&&(!metpen.equals("null"))&&(!metcue.equals("null"))&&(!naturaleza.equals("null"))&&(!resnat.equals("null")))
      {
      declaracion = "UPDATE tbcontratos SET \n"+
                    " con_ref_metodo_orden = '"+metord+"', \n"+
                    " con_ref_metodo_beneficio = '"+metben+"', \n"+
                    " con_ref_metodo_penalizacion = '"+metpen+"', \n"+
                    " con_ref_metodo_cuenta = '"+metcue+"', \n"+
                    " con_ref_naturaleza = '"+naturaleza+"', \n"+
                    " con_respetar_naturaleza = '"+resnat+"' \n"+
                    " WHERE con_pro_codigo = '"+v_codpro+"' \n"+
                    " AND con_numero = '"+v_contrato+"'";

      resultadodeclaracion = v_Consulta.TBFL_Consulta(declaracion, true);
      //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
      TBPL_Publica_Satisfactorio(resultadodeclaracion);
      }
    else if ((metord.equals("null"))&&(metben.equals("null"))&&(metpen.equals("null"))&&(metcue.equals("null"))&&(naturaleza.equals("null"))&&(resnat.equals("null")))
      {
      declaracion = "UPDATE tbcontratos SET \n"+
                    " con_ref_metodo_orden = "+metord+", \n"+
                    " con_ref_metodo_beneficio = "+metben+", \n"+
                    " con_ref_metodo_penalizacion = "+metpen+", \n"+
                    " con_ref_metodo_cuenta = "+metcue+", \n"+
                    " con_ref_naturaleza = "+naturaleza+", \n"+
                    " con_respetar_naturaleza = "+resnat+" \n"+
                    " WHERE con_pro_codigo = '"+v_codpro+"' \n"+
                    " AND con_numero = '"+v_contrato+"'";

      resultadodeclaracion = v_Consulta.TBFL_Consulta(declaracion, true);
      //v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
      TBPL_Publica_Satisfactorio(resultadodeclaracion);
      }
    else
      {
      TBPL_Publica_Error();
      }
      v_Consulta.TBPL_shutDown();//cierra la conexion con la base de datos
  }

  private void  TBPL_Publica_Satisfactorio(Vector resultadodeclaracion)
  {
  resultadodeclaracion.trimToSize();
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas contratos","Administración de empresas contratos", "", "<BLOCKQUOTE>"+resultadodeclaracion.elementAt(0).toString()+"</BLOCKQUOTE>", false));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-2);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  private void TBPL_Publica_Error()
  {
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas contratos","Administración de empresas contratos", "", "<BLOCKQUOTE>Debe seleccionar o todos nulos o todos con un atributo asociado</BLOCKQUOTE>", false));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=regresar ONCLICK=history.go(-1);></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_PARAMETRIZACION_CONTRATOS.TBCS_Modifica_Contrato Information";
  }
}

