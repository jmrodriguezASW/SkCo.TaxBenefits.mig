package TBPKT_PARAMETROS.TBPKT_CONTRATOCONDICION;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_AE_Contrato_condicion extends HttpServlet{
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
       TBCL_Seguridad Seguridad = new TBCL_Seguridad();
       parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       }
    catch(Exception ex){System.out.println("");}

    v_Consulta = new TBCL_Consulta();
    String declaracion;
    Vector resultadodeclaracion;


    //Toma la opción de modificar, eliminar, adicionar o consultar
    String opcion = "";
    //Toma los diferentes codigos de producto
    String v_codpro = "";
    String v_contrato = "";

    try{
       opcion = request.getParameter("opcion");
       v_codpro = request.getParameter("v_codpro");
       v_contrato = request.getParameter("v_contrato");
       }
    catch (Exception e) { e.printStackTrace(); }

    v_Consulta.TBPL_RealizarConexion();

   if (opcion.equals("seleccion"))
     {
     TBPL_Publicar();
     }
   else if (opcion.equals("adiciona"))
     {
     try{
        String[] conpena = new String[request.getParameterValues("v_codcondpena").length];
        for (int i=0; i < request.getParameterValues("v_codcondpena").length; i++)
           conpena[i] = request.getParameterValues("v_codcondpena")[i];

        for (int i=0; i<conpena.length; i++)
           {
           declaracion = "INSERT INTO tbcontratos_condiciones (coo_con_pro_codigo, \n"+
                      " coo_con_numero, coo_cop_codigo) \n"+
                      " VALUES (UPPER('"+v_codpro+"'), UPPER('"+v_contrato+"'), \n"+
                      " UPPER('"+conpena[i]+"'))";
           resultadodeclaracion = v_Consulta.TBFL_Consulta(declaracion, true);
           }
        }
     catch (Exception e)
        {
        TBPL_Publicar_Error();
        e.printStackTrace();
        }

     
     out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de condiciones de penalización para contratos", "Administración de condiciones de penalización para contratos", "", "<BLOCKQUOTE>La adición se ha realizado con éxito</BLOCKQUOTE>", false));
     out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-2);></CENTER>");
     out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
     out.close();
     }
   else if (opcion.equals("elimina"))
     {
     try{
        String[] conpen = new String[request.getParameterValues("v_codcondpen").length];
        for (int i=0; i < request.getParameterValues("v_codcondpen").length; i++)
           conpen[i] = request.getParameterValues("v_codcondpen")[i];

        for (int i=0; i<conpen.length; i++)
           {
           declaracion = "DELETE tbcontratos_condiciones \n"+
                      " WHERE coo_cop_codigo = '"+conpen[i]+"' \n"+
                      " AND coo_con_pro_codigo = '"+v_codpro+"' \n"+
                      " AND coo_con_numero = '"+v_contrato+"'";
           resultadodeclaracion = v_Consulta.TBFL_Consulta(declaracion, false);
           }
        }
     catch (Exception e)
        {
        TBPL_Publicar_Error();
        e.printStackTrace();
        }
     
     out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de condiciones de penalización para contratos","Administración de condiciones de penalización para contratos", "", "<BLOCKQUOTE>La eliminación se ha realizado con éxito</BLOCKQUOTE>", false));
     out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-2);></CENTER>");
     out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
     out.close();
     }
  v_Consulta.TBPL_shutDown();
  }



      private void TBPL_Publicar()
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de contratos condición","Administración de contratos condición", "", "<BLOCKQUOTE>Debe seleccionar el adicionar o el eliminar contrato condición</BLOCKQUOTE>", false));
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }

   private void TBPL_Publicar_Error()
  {
  out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de contratos condición","Administración de contratos condición", "", "Seleccione algúna condición penalización", false));
  out.println("<BR>&nbsp;&nbsp;<BR><CENTER><FONT color=black face=verdana size=1><INPUT TYPE=BUTTON VALUE=Aceptar ONCLICK=history.go(-1);></FONT></CENTER>");
  out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
  out.close();
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_CONTRATOCONDICION.TBCS_AE_Contrato_condicion Information";
  }
}

