package TBPKT_PARAMETROS.TBPKT_EMPRESAS_CONTRATOS;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_Empresas_Contratos extends HttpServlet{
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
    String Consulta;
    //Vector donde se guardara el resultado de la Consulta
    Vector v_resultadoConsulta, resultadoConsulta1, resultadoConsulta2;

    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


    //Toma los diferentes codigos de producto
    String v_codpro = "";
    String contrato = "";
    int v_contratolength = 0;

    try{
       v_codpro = request.getParameter("v_codpro");
       contrato = request.getParameter("obligatoriocontrato").trim();
       /*Cambio para manejo de referencia unica 2009/04/01 MOS */
       contrato = v_Consulta.TBFL_ConsultaSinRefUnica(contrato);
       }
    catch (Exception e) { e.printStackTrace(); }

    v_contratolength = contrato.length();

    if ((contrato.indexOf("'")==-1))
      {
      switch (v_contratolength)
            {
            case 1:
                contrato = "00000000"+contrato;
            break;
            case 2:
                contrato = "0000000"+contrato;
            break;
            case 3:
                contrato = "000000"+contrato;
            break;
            case 4:
                contrato = "00000"+contrato;
            break;
            case 5:
                contrato = "0000"+contrato;
            break;
            case 6:
                contrato = "000"+contrato;
            break;
            case 7:
                contrato = "00"+contrato;
            break;
            case 8:
                contrato = "0"+contrato;
            break;
            default:
                contrato = contrato;
            break;
            }

      Consulta = "SELECT CON_NOMBRES, CON_APELLIDOS \n"+
                 " FROM   tbcontratos \n"+
                 " WHERE CON_PRO_CODIGO = '"+v_codpro+"' \n"+
                 " AND CON_NUMERO = '"+contrato+"'";
      v_resultadoConsulta = v_Consulta.TBFL_Consulta(Consulta);

      Consulta = "SELECT emp_grupo, emp_descripcion, emc_ref_condicion, emc_detalle_condicion \n"+
                 " FROM   tbempresas,  tbempresas_contratos \n"+
                 " WHERE  emp_grupo = emc_emp_grupo \n"+
                 " AND emc_con_pro_codigo = '"+v_codpro+"' \n"+
                 " AND emc_con_numero = '"+contrato+"' ORDER BY emp_descripcion";
      resultadoConsulta1 = v_Consulta.TBFL_Consulta(Consulta);

      TBPL_Publicar(v_codpro, contrato, v_resultadoConsulta, resultadoConsulta1);
      }
    else
      {
      TBPL_PublicarError();
      }
      v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
  }


      private void TBPL_Publicar(String v_codpro, String contrato, Vector v_descripcion, Vector v_descripcion1)
      {
      v_descripcion.trimToSize();
      v_descripcion1.trimToSize();
      

      if (!v_descripcion.elementAt(0).toString().equals("No hay elementos"))
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas contratos","Administracion de empresas contratos", "TBPKT_PARAMETROS.TBPKT_EMPRESAS_CONTRATOS.TBCS_ACEM_Empresas_Contratos", "", true, "moduloparametro.js", "return checkrequired(this)"));
        /*Cambio para manejo de referencia unica 2009/04/01 MOS */
        String v_contrato_unif = "";
        //v_Consulta.TBPL_RealizarConexion();
        v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_codpro, contrato);
        //v_Consulta.TBPL_shutDown();

        //Aqui van los campo que se quieren mostrar
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
        out.println("<BLOCKQUOTE>");
        out.println("<B>Código del producto:</B>&nbsp;&nbsp;"+v_codpro+"<INPUT TYPE=hidden NAME=v_codpro value='"+v_codpro+"'><BR>");
        out.println("<B>Número del contrato:</B>&nbsp;&nbsp;"+v_contrato_unif+"<INPUT TYPE=hidden NAME=contrato value='"+contrato+"'><BR>");
        out.println("</BLOCKQUOTE>");
        out.println("</font>");


        if (!v_descripcion1.elementAt(0).toString().equals("No hay elementos"))
          {
          out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                      "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Contratos de empresas asociadas a:<BR>"+v_descripcion.elementAt(0).toString()+"&nbsp;"+v_descripcion.elementAt(1).toString()+"</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
          out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=4 rules=all width=\"100%\">");
          out.println("<TR class=\"td11\" borderColor=silver><TH width=\"5%\">&nbsp;</TH><TH width=\"22%\"><FONT color=white face=verdana size=1><B>Nombre de empresa</B></font></TH><TH width=\"22%\"><FONT color=white face=verdana size=1><B>Condición de la empresa contrato</B></font></TH><TH width=\"22%\"><FONT color=white face=verdana size=1><B>Detalle de la condición empresa contrato</B></font></TH></TR>");
          for (int i=0; i<v_descripcion1.size(); i+=4)
             {
             if (i<1)
               out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=grupo CHECKED VALUE='"+v_descripcion1.elementAt(i).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion1.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+TBFL_ConsultaDescripcion(v_descripcion1.elementAt(i+2).toString())+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion1.elementAt(i+3).toString()+"</font></TD></TR>");
             else
               out.println("<TR bgColor=white borderColor=silver><TD width=\"5%\"><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=grupo VALUE='"+v_descripcion1.elementAt(i).toString()+"'></font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion1.elementAt(i+1).toString()+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+TBFL_ConsultaDescripcion(v_descripcion1.elementAt(i+2).toString())+"</font></TD><TD width=\"22%\"><FONT color=black face=verdana size=1>"+v_descripcion1.elementAt(i+3).toString()+"</font></TD></TR>");
             }
          out.println("</TABLE>");
          out.println("<BLOCKQUOTE>");
          out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
          out.println("<INPUT TYPE=radio NAME=v_opcion VALUE=elimina>Eliminar contrato de empresa<BR>");
          out.println("<INPUT TYPE=radio NAME=v_opcion CHECKED VALUE=modifica>Modificar contrato de empresa");
          out.println("</font>");
          out.println("</BLOCKQUOTE>");
          }

        out.println("<BR>&nbsp;&nbsp;<BR>");

        out.println("<TABLE align=center class=\"td13\" border=0 cellPadding=0 cellSpacing=0>\n"+
                    "<TBODY><TR><TD><CENTER><FONT color=white face=Verdana size=2><B>Empresas para adicionar</B></FONT></CENTER></TD></TR></TBODY></TABLE>");
        out.println("<BR>&nbsp;&nbsp;<BR>");
        out.println("<B>Nombre de la empresa:</B>&nbsp;<INPUT TYPE=text NAME=empresa SIZE=15>");
        out.println("<BR>Deje el espacio en blanco para mostrar todas las empresas");
        out.println("<BLOCKQUOTE>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#000000'>");
        if (!v_descripcion1.elementAt(0).toString().equals("No hay elementos"))
          out.println("<INPUT TYPE=radio NAME=v_opcion VALUE=adiciona>Adicionar empresa a contrato<BR>");
        else
          out.println("<INPUT TYPE=radio CHECKED NAME=v_opcion VALUE=adiciona>Adicionar empresa a contrato<BR>");

        out.println("</font>");
        out.println("</BLOCKQUOTE>");

        out.println("<CENTER><INPUT TYPE=submit VALUE=Aceptar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
        }
      else
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas contratos","Administracion de empresas contratos", "", "</BLOCKQUOTE>No existe este contrato</BLOCKQUOTE>", false));
        out.println("<BR>&nbsp;&nbsp;</BR>");
        out.println("<CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
        }
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");        
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


      private void TBPL_PublicarError()
      {
      out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Administración de empresas contratos","Administración de empresas contratos", "", "<BLOCKQUOTE>Comilla sencilla no es un caractér valido</BLOCKQUOTE>", false));
      out.println("<BR>&nbsp;&nbsp;<BR><CENTER><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


  private String TBFL_ConsultaDescripcion(String cod)
  {
  //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
  String v_declaracion = "SELECT ref_descripcion FROM tbreferencias WHERE ref_codigo = '"+cod+"'";
  Vector v_resultadodeclaracion = v_Consulta.TBFL_Consulta(v_declaracion, "ref_descripcion");
  //v_Consulta.TBPL_shutDown();
  return (v_resultadodeclaracion.elementAt(0).toString());
  }

  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_EMPRESAS_CONTRATOS.TBCS_Empresas_Contratos Information";
  }
}

