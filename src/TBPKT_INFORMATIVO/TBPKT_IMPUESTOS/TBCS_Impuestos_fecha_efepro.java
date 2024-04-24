package TBPKT_INFORMATIVO.TBPKT_IMPUESTOS;
 
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import TBPKT_UTILIDADES.TBPKT_VALOR_UNIDAD.SQL_VALOR_UNIDAD_CC;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.STBCL_GenerarBaseHTML;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

public class TBCS_Impuestos_fecha_efepro extends HttpServlet implements SingleThreadModel
{
   private PrintWriter out;
   private TBCL_Consulta v_Consulta;
   private double rendimientos = 0;
   private double CapitalContrato = 0;
   private String v_nuevaCadena ="";
   private Date currentDate = new Date();
   private DateFormat currentDateFormat;
   Vector v_resultadoconsulta1, v_resultadoconsulta2,v_resultadoconsulta3;
   //Toma el código del producto
   String codproducto = "MFUND";
   //Toma el número de v_contrato
   String v_Nocontrato = "";
   /**
   * Initialize global variables
   */
   public void init(ServletConfig config) throws ServletException
   {
      super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String parametros[] = new String[8];
      double SaldoContrato = 0;
      try
      {
         out = new PrintWriter (response.getOutputStream());
         response.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
       //response.setHeader("Pragma", "No-Cache");
       //response.setDateHeader("Expires", 0);
         String  cadena = request.getParameter("cadena");         
         v_nuevaCadena = cadena;
         String ip_tax = request.getRemoteAddr();
         TBCL_Seguridad Seguridad = new TBCL_Seguridad();
         parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
      }
      catch(Exception ex){System.out.println("");}
      v_Consulta = new TBCL_Consulta();
      String v_dconsulta;
      //Vector donde se guardara el resultado de la v_dconsulta





      try
      {
         codproducto = parametros[1];
         v_Nocontrato  = parametros[0];
      }
      catch (Exception e) { e.printStackTrace(); }
      String  vfecefepro = request.getParameter("FP");
      String  vano = request.getParameter("ANNO");
      String  vmes = request.getParameter("MES");
      String  vdia = request.getParameter("DIA");

         TBPL_Publicar(v_Nocontrato,codproducto,vano,vmes,vdia,vfecefepro); //Publicación de la pagina en código HTML
   }



   private void TBPL_Publicar(  String v_Nocontrato, String v_codpro,String vano,String vmes ,String vdia,String vfecefepro)
   {

      double SaldoContrato = 0;
      double error = 0;
      double ValorUnidad = 0;

      double[] v_valuni     = new double[3];/**Valor unidad*/
      double[] v_saldo     = new double[9];/**Valor unidad*/
      boolean imprimir = false;
      String v_menvalor = "";

      //consultar valor de unidad a fecha
      String vfechauni = vano+vmes+vdia;
      v_valuni = SQL_VALOR_UNIDAD_CC.TBF_CALCULO_VALOR_UNIDAD(vfechauni, vfechauni, v_Nocontrato, v_codpro, false,0);
      if (v_valuni[2] == 0.0)
        {
          ValorUnidad = v_valuni[0];
          System.out.println("valor unidad "+ValorUnidad);
          SaldoContrato = v_valuni[1];
          imprimir = true;
        }
      else
        imprimir = false;

      if(v_valuni[2] == -1)
      {
         v_menvalor =  "El contenido de la cadena para el saldo del contrato en Multifund es vacia.";
         imprimir = true;

      }
      else if(v_valuni[2] == -2)
             v_menvalor =  "Error al consultar saldo del contrato en Multifund.";
           else if(v_valuni[2] == -3)
                v_menvalor =  "El Saldo del contrato es negativo.";
                else if(v_valuni[2] == -5)
                    v_menvalor =  "El Saldo de numeros de unidades del contrato es negativo";
                     else
                      v_menvalor =  "Hubo algún error en el calculo.";



      v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
      v_saldo = v_Consulta.TBFL_ConsultaSaldos(v_codpro,v_Nocontrato,ValorUnidad,vfecefepro,vano+"-"+vmes+"-"+vdia );
      //v_Consulta.TBPL_shutDown();
      
      /*Cambio para manejo de referencia unica 2009/04/01 MOS */
      String v_contrato_unif = "";
      //v_Consulta.TBPL_RealizarConexion();
      v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_codpro,v_Nocontrato);
      v_Consulta.TBPL_shutDown();
       
      if (v_saldo[0]== 0)
      {

         out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de impuestos","Consulta de impuestos para el contrato&nbsp"+v_contrato_unif+"", "", "", true));
         //Aqui van los campo que se quieren mostrar
         out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Saldo total del contrato</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(SaldoContrato)+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Saldo capital</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(v_saldo[1])+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Saldo rendimientos</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(v_saldo[2])+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Cuenta contingente</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(v_saldo[3])+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Total capital aportado</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(v_saldo[4])+"</font></TD></TR>");
         out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Total capital retirado</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(v_saldo[5])+"</font></TD></TR>");
         out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Total rendimiento retirado</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(v_saldo[6])+"</font></TD></TR>");
         out.println("</TABLE>");
         out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");
      }
      else
      {
         out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de impuestos","Consulta de impuestos", "", "<BLOCKQUOTE>Se produjo el error ORA-"+v_saldo[7]+"</BLOCKQUOTE>", false));
         out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");
      }
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
   }




}


