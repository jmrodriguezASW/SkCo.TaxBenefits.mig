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

public class TBCS_Consulta_Impuestos extends HttpServlet{
private PrintWriter out;
private TBCL_Consulta v_Consulta;
private double rendimientos = 0;
private double CapitalContrato = 0;
private String v_nuevaCadena ="";
private Date currentDate = new Date();
private DateFormat currentDateFormat;
Vector v_resultadoconsulta1, v_resultadoconsulta2,v_resultadoconsulta3;

  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  String parametros[] = new String[8];
  double SaldoContrato = 0;
    try{
       out = new PrintWriter (response.getOutputStream());
       
       response.setContentType("text/html");
       /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
       //response.setHeader("Pragma", "No-Cache");
       //response.setDateHeader("Expires", 0);
       String  cadena = request.getParameter("cadena");
       v_nuevaCadena = cadena;
       String ip_tax = request.getRemoteAddr();
       /*[SO_396] Se realiza modificación de llamado por ser método estático TBCL_Seguridad.TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
       // 
  
 /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_Seguridad de la clase TBCL_Seguridad, no es necesaria la instancia nueva*/ 
 //TBCL_Seguridad Seguridad    = new TBCL_Seguridad;
       //parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
       parametros = TBCL_Seguridad.TBFL_Seguridad(cadena, out, ip_tax);

       }
    catch(Exception ex){System.out.println("");}
    v_Consulta = new TBCL_Consulta();
    String v_dconsulta;
    //Vector donde se guardara el resultado de la v_dconsulta


    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos


    //Toma el código del producto
    String codproducto = "MFUND";
    //Toma el número de v_contrato
    String v_Nocontrato = "";
    //Permite saber cual es el tamaño del v_contrato introducido por el cliente(debe ser nueve)
    String p = currentDate.toString();
    currentDateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.PRC);
    String d = currentDateFormat.format(currentDate);

    int v_contratolength = 0;
    int anoactual = Integer.parseInt(d.substring(0,4));
    int anoini = 1970;
    int contanos = 0;
    int vecanos[];
    String FechaAperturaContrato = "";


    try{
       codproducto = parametros[1];
       v_Nocontrato  = parametros[0];;
       }
    catch (Exception e) { e.printStackTrace(); }

    v_contratolength = v_Nocontrato.length();


     switch (v_contratolength)
           {
           case 1:
               v_Nocontrato = "00000000"+v_Nocontrato;
           break;
           case 2:
               v_Nocontrato = "0000000"+v_Nocontrato;
           break;
           case 3:
               v_Nocontrato = "000000"+v_Nocontrato;
           break;
           case 4:
               v_Nocontrato = "00000"+v_Nocontrato;
           break;
           case 5:
               v_Nocontrato = "0000"+v_Nocontrato;
           break;
           case 6:
               v_Nocontrato = "000"+v_Nocontrato;
           break;
           case 7:
               v_Nocontrato = "00"+v_Nocontrato;
           break;
           case 8:
               v_Nocontrato = "0"+v_Nocontrato;
           break;
           default:
               v_Nocontrato = v_Nocontrato;
           break;
           }
      v_dconsulta = "SELECT\n"+
                    "        TO_CHAR(con_fecha_apertura, 'RRRR-MM-DD')\n"+
                    " FROM   tbcontratos\n"+
                    " WHERE  con_pro_codigo = '"+codproducto+"'\n"+
                    "   AND  con_numero     = '"+v_Nocontrato+"'";
      FechaAperturaContrato = v_Consulta.TBFL_Consulta(v_dconsulta).elementAt(0).toString();

      try{
         anoini = Integer.parseInt(FechaAperturaContrato.substring(0,4));

         }
      catch (Exception ex){anoini = 1970;}
      contanos=anoactual-anoini;

      if (contanos<=0)
        contanos = 2999-anoini;
      vecanos = new int [contanos+1];
      for (int i=0; i<vecanos.length ; i++)
         vecanos[i]=anoini++;



      v_dconsulta = "SELECT  NVL(SUM(apo_saldo_capital),0),\n"+
                    "        NVL(SUM(apo_saldo_cuenta_contingente),0),\n"+
                    "        NVL(SUM(apo_capital),0),\n"+
                    "        NVL(SUM(apo_saldo_numero_unidades),0)\n"+
                    "  FROM  tbaportes\n"+
                    " WHERE  apo_con_pro_codigo='"+codproducto+"'\n"+
                    "   AND  apo_con_numero    = '"+v_Nocontrato+"'\n"+
                    "   AND  apo_informacion_traslado = 'N'"+
                    "   AND  apo_ref_estado = 'SEA001'";
      v_resultadoconsulta1 = v_Consulta.TBFL_Consulta(v_dconsulta);

//      out.println("tamaño del vector = " + v_resultadoconsulta1.elementAt(0) );
      v_dconsulta = "SELECT  NVL(SUM(apr_capital),0),\n"+
                    "        NVL(SUM(apr_rendimientos),0)\n"+
                    "  FROM  tbretiros \n"+
                    "        ,tbaportes_retiros  "+
                    " WHERE  ret_con_pro_codigo =  '"+codproducto+"' "+
                    "   AND ret_con_numero =  '"+v_Nocontrato+"'  "+
                    "   AND (ret_ref_estado  =  'SER001' OR  ret_ref_estado  =  'SER006') "+
                    "   AND ret_con_pro_codigo  = apr_ret_con_pro_codigo   "+
                    "   AND ret_con_numero  = apr_ret_con_numerO  "+
                    "   AND ret_consecutivo =   apr_ret_consecutivo  ";



      v_resultadoconsulta2 = v_Consulta.TBFL_Consulta(v_dconsulta);
      
      
    TBPL_Publicar(FechaAperturaContrato, v_resultadoconsulta1, v_resultadoconsulta2, v_Nocontrato, vecanos, codproducto); //Publicación de la pagina en código HTML
    v_Consulta.TBPL_shutDown();
  }



      private void TBPL_Publicar(String FechaAperturaContrato, Vector v_descripcion1, Vector v_descripcion2, String v_Nocontrato, int anos[], String v_codpro)
      {

      v_descripcion1.trimToSize();
      v_descripcion2.trimToSize();
      double SaldoContrato = 0;
      double error = 0;
      double ValorUnidad = 0;

      double[] v_valuni     = new double[3];/**Valor unidad*/
      boolean imprimir = false;
      String MosFAC = FechaAperturaContrato;
      String v_menvalor = "";


      FechaAperturaContrato = FechaAperturaContrato.replace('-',' ');
      StringTokenizer fac= new StringTokenizer(FechaAperturaContrato);
      FechaAperturaContrato="";
      while (fac.hasMoreTokens())

      FechaAperturaContrato+=fac.nextToken();


      //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos


      //double saldonumuni = Double.parseDouble(v_resultadoconsulta3.elementAt(0).toString());
      v_valuni = SQL_VALOR_UNIDAD_CC.TBF_CALCULO_VALOR_UNIDAD(TBFL_FechaMenosUno(), TBFL_FechaMenosUno(), v_Nocontrato, v_codpro, false,0);
      if (v_valuni[2] == 0.0)
        {
          ValorUnidad = v_valuni[0];
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
                      v_menvalor =  "Hubo algún error en el calculo.v_valuni[2]=" + v_valuni[2]+ ", v_valuni[0]=" +v_valuni[0] + ", v_valuni[1]"+v_valuni[1];

      if (imprimir)
        {
        imprimir = TBFL_ConsultaRendimientos(v_codpro, v_Nocontrato, ValorUnidad);
        }
          /*Cambio para manejo de referencia unica 2009/04/01 MOS */
          String v_contrato_unif = "";
          //v_Consulta.TBPL_RealizarConexion();
          v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(v_codpro,v_Nocontrato);
          //v_Consulta.TBPL_shutDown();

      if (!v_descripcion1.elementAt(0).toString().equals("null"))
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de impuestos","Consulta de impuestos para el contrato&nbsp;"+v_contrato_unif+"", "TBPKT_INFORMATIVO.TBPKT_IMPUESTOS.TBCS_Consulta_Impuetos_BTA", "", true));
        //Aqui van los campo que se quieren mostrar
        out.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width=\"100%\">");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Fecha apertura del contrato</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+MosFAC+"</font></TD></TR>");
//        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Saldo total del contrato</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_descripcion1.elementAt(3).toString())*ValorUnidad)+"</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Saldo total del contratoñ</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(SaldoContrato)+"</font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Saldo capital</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+(imprimir?NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_descripcion1.elementAt(0).toString())):v_menvalor)+"</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Saldo rendimientos</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+(imprimir?NumberFormat.getCurrencyInstance().format((Double.parseDouble(v_descripcion1.elementAt(3).toString())*ValorUnidad)-Double.parseDouble(v_descripcion1.elementAt(0).toString())):v_menvalor)+"</font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B><SUP>*</SUP>Cuenta contingente</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_descripcion1.elementAt(1).toString()))+"</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Total capital aportado</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_descripcion1.elementAt(2).toString()))+"</font></TD></TR>");
        out.println("<TR bgColor=white borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Total capital retirado</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_descripcion2.elementAt(0).toString()))+"</font></TD></TR>");
        out.println("<TR bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><B>Total rendimiento retirado</B></font></TD><TD width=\"22%\" align=right><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(Double.parseDouble(v_descripcion2.elementAt(1).toString()))+"</font></TD></TR>");
        out.println("<TR align=middle class=\"td11\" borderColor=silver><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha inicial</B></font></TD><TD width=\"22%\"><FONT color=white face=verdana size=1><B>Fecha final</B></font></TD></TR>");
        out.println("<TR align=middle bgColor=whitesmoke borderColor=silver><TD width=\"22%\"><FONT color=black face=verdana size=1><select name=\"mesini\" size=1><option VALUE=Jan>Enero</option><option VALUE=Feb>Febrero</option><option VALUE=Mar>Marzo</option><option VALUE=apr>Abril</option><option VALUE=May>Mayo</option><option VALUE=Jun>Junio</option><option VALUE=Jul>Julio</option><option VALUE=Aug>Agosto</option><option VALUE=Sep>Septiembre</option><option VALUE=Oct>Octubre</option><option VALUE=Nov>Noviembre</option><option VALUE=Dec>Diciembre</option></select><select name=\"anoini\" size=1>");
        for (int i=0; i<anos.length; i++)
           {
           out.println("<OPTION>"+anos[i]);
           }
        out.println("</SELECT></font></TD><TD width=\"22%\">");
        out.println("<FONT color=black face=verdana size=1><select name=\"mesfin\" size=1><option VALUE=Jan>Enero</option><option VALUE=Feb>Febrero</option><option VALUE=Mar>Marzo</option><option VALUE=apr>Abril</option><option VALUE=May>Mayo</option><option VALUE=Jun>Junio</option><option VALUE=Jul>Julio</option><option VALUE=Aug>Agosto</option><option VALUE=Sep>Septiembre</option><option VALUE=Oct>Octubre</option><option VALUE=Nov>Noviembre</option><option VALUE=Dec>Diciembre</option></select><select name=\"anofin\" size=1>");
        for (int i=0; i<anos.length; i++)
           {
           out.println("<OPTION>"+anos[i]);
           }
        out.println("</SELECT></font></TD></TR>");
        out.println("</TABLE>");
        out.println("<INPUT TYPE=hidden NAME=v_codigo VALUE='"+v_codpro+"'>");
        out.println("<INPUT TYPE=hidden NAME=v_Nocontrato VALUE='"+v_Nocontrato+"'>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
        out.println("<SUP>*</SUP>Los saldos tienen descontados los retiros del día");
        out.println("</font>");
        out.println("<BLOCKQUOTE>");
        out.println("<font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'>");
        out.println("<INPUT TYPE=radio NAME=opcion VALUE='beneficio' CHECKED>Consulta de retiros de Beneficio tributario y retenciones<BR>");
        out.println("<INPUT TYPE=radio NAME=opcion VALUE='traslado'>Consulta de traslados<BR>");
        out.println("<INPUT TYPE=radio NAME=opcion VALUE='ajuste'>Consulta de Ajustes<BR>");
        out.println("<INPUT TYPE=radio NAME=opcion VALUE='conimp'>Consulta de Impuestos a otra fecha<BR>");
        out.println("</font>");
        out.println("</BLOCKQUOTE>");
        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=submit VALUE=Consultar><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");
        }
      else
        {
        out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Consulta de impuestos","Consulta de impuestos", "", "<BLOCKQUOTE>No hay elementos para este contrato</BLOCKQUOTE>", false));
        out.println("<BR>&nbsp;&nbsp;<BR><CENTER><font face='Verdana, Arial, Helvetica, sans-serif'><font size='1'><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></font></CENTER>");
        }

      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+v_nuevaCadena+"'>");
      out.println("<INPUT ID=feccon NAME=feccon TYPE=hidden VALUE='"+MosFAC+"'>");
      out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
      out.close();
      }


   private boolean TBFL_ConsultaRendimientos(String producto, String v_contrato, double ValorUnidad)
   {
   boolean imprend = false;
   //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos

   if (v_Consulta.TBFL_Rendimientos(producto, v_contrato, ValorUnidad)[1]==0)
     {
     rendimientos=v_Consulta.TBFL_Rendimientos(producto, v_contrato, ValorUnidad)[2];
     CapitalContrato = v_Consulta.TBFL_Rendimientos(producto, v_contrato, ValorUnidad)[0];
     imprend = true;
     }

   //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
   return imprend;
   }

   private String TBFL_FechaMenosUno()
   {
   String retorno;
   //v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
   retorno = v_Consulta.TBFL_Consulta("SELECT TO_CHAR(SYSDATE-1, 'RRRRMMDD') FROM DUAL").elementAt(0).toString();
   //v_Consulta.TBPL_shutDown();//Desconexion a la base de datos
   return retorno;
   }
  public String getServletInfo() {
    return "TBPKT_IMPUESTOS.TBCS_Consulta_Impuestos Information";
  }
 }


