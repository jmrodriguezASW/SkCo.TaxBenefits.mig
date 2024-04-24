package TBPKT_AJUSTES;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;
import javax.servlet.http.*;
import java.io.*;

/**
*  El objetivo de esta clase es validar y guardar los párametros de entrada que
*  envía PipeLine
*/

//public class TBCL_ParamEntr extends Object
public class TBCL_ParamEntr extends HttpServlet
{
//--
public String TBCL_ParamEntr(HttpServletRequest request,HttpSession sess)
{
    String v_keys = new String("");
    sess.setMaxInactiveInterval(3600);
    if(request.getParameter("num_contrato")!=null && request.getParameter("nom_producto")!=null)
      {
        v_keys = "producto='"+request.getParameter("nom_producto")+"' contrato='"+request.getParameter("num_contrato")+"' ";
        if(request.getParameter("usuario")!=null)
           v_keys += "usuario='"+request.getParameter("usuario")+"'";
        sess.removeAttribute("KEYS");   
        sess.setAttribute("KEYS","<"+v_keys+">");
      }
    else
      {
        if(sess.getAttribute("KEYS")!=null)
          v_keys = (String)sess.getAttribute("KEYS");
      }
   return v_keys;
}
//--
public String TBCL_ParamEntr(HttpServletRequest request,HttpSession sess,PrintWriter salida)
  {
    //INICIO seguridad
    String v_contrato           = "", v_producto = "",
    v_usuario                   = "", v_unidad   = "";
    String v_tipousu            = "", v_idworker = "";
    String parametros[]         = new String[8];
    String cadena               = request.getParameter("cadena");
    String ip_tax               = request.getRemoteAddr();
    TBCL_Seguridad Seguridad    = new TBCL_Seguridad();
    parametros                  = Seguridad.TBFL_Seguridad(cadena,salida,ip_tax);
    v_contrato                  = parametros[0];
    v_producto                  = parametros[1];
    v_usuario                   = parametros[2];
    v_unidad                    = parametros[3];
    v_tipousu                   = parametros[4];
    v_idworker                  = parametros[5];
    SQL_AJUSTE  i_unidad = new SQL_AJUSTE();
    boolean v_conectado = i_unidad.TBPBD_ConexionBD();
    if(v_conectado){
      String v_codigounidad  =i_unidad.TBPL_BuscarUnidad(v_unidad);
      String v_codigotipousuario =i_unidad.TBPL_BuscarTipoUsuario(v_tipousu);

    sess.removeAttribute("s_codigounidad");
    sess.setAttribute("s_codigounidad",v_codigounidad);
    sess.removeAttribute("s_codigotipousuario");
    sess.setAttribute("s_codigotipousuario",v_codigotipousuario);
    sess.removeAttribute("s_usuariopipeline");
    sess.setAttribute("s_usuariopipeline",v_usuario);

     //FIN seguridad
    String v_keys = new String("");
    sess.setMaxInactiveInterval(3600);
    //    if(request.getParameter("num_contrato")!=null)

    if(v_contrato!=null && v_producto!=null)
    {

        v_keys = "producto='"+v_producto+"' contrato='"+v_contrato+"' ";
        if(v_usuario!=null)
          v_keys += "usuario='"+v_usuario+"'";
        sess.removeAttribute("KEYS");
        sess.setAttribute("KEYS","<"+v_keys+">");

      }
     else
      {

        if(sess.getAttribute("KEYS")!=null)
         {

          v_keys = (String)sess.getAttribute("KEYS");
         }
      }
       i_unidad.TBPBD_CerrarConexionBD();
   return v_keys;
   }
   else return "";
  }
//--

}

