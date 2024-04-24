package TBPKT_INFORMATIVO.TBPKT_RETENCION_FUENTE;

import javax.servlet.*;
import javax.servlet.http.*;
import oracle.jdeveloper.layout.*;
import java.util.*;
import java.sql.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import TBPKT_UTILIDADES.TBPKT_RTF.TBCL_RTF;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

public class TBCS_Retencion_Fuente extends HttpServlet {
	String rutaFisica = "", rutaVirtual = "";
  String nuevaCadena = "";

  /**
   * Initialize global variables
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   * Process the HTTP Post request
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  try {
    String formato = "", archivo = "", stropcion = "", version = "";
		stropcion= request.getParameter("opcion");
    if(stropcion == null) stropcion= "6";
    int opcion= Integer.parseInt(stropcion);

    HttpSession session = request.getSession(false);
    if (session == null) session = request.getSession(true);

    response.setContentType("text/html");
    /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
    //response.setHeader("Pragma", "No-Cache");
    //response.setDateHeader("Expires", 0);
    String contrato = "", producto = "", usuario  = "", unidad_o = "";
    String tipo_usuario = "", idworker = "";
    String parametros[] = new String[8];
    String  cadena = request.getParameter("cadena");
    nuevaCadena = cadena;
    String ip_tax = request.getRemoteAddr();
    TBCL_Seguridad Seguridad = new TBCL_Seguridad();
    PrintWriter out = new PrintWriter (response.getOutputStream());
    parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);
    contrato = parametros[0];
    producto = parametros[1];
    usuario  = parametros[2];
    unidad_o = parametros[3];
    tipo_usuario = parametros[4];
    idworker = parametros[5];
    java.util.Date D = new java.util.Date();

    TBPL_GetRutas();

    switch(opcion)
    {
    	case 2:
    		out.println(TBFL_Opcion2(producto));
    	break;
    	case 5:
    		formato = request.getParameter("formato");
    		archivo = request.getParameter("archivo");
        if (archivo == null || archivo.equals("")) archivo = "certificado"+ D.getTime();
		    out.println(TBFL_Opcion5(formato, contrato, archivo));
    	break;
    	case 6:
		    out.println(TBFL_Opcion6(producto));
    	break;
    	case 7:
    		formato = request.getParameter("formato");
		    out.println(TBFL_Opcion7(formato, contrato));
    	break;
    	case 8:
    		formato = request.getParameter("formato");
    		version = request.getParameter("version");
		    out.println(TBFL_Opcion8(formato, contrato, version));
    	break;
    }
    out.close();
    }
    catch (Exception e)
    {}
  }


private String TBFL_Opcion8(String formato, String contrato, String version)
	{
		String html = "";
		String fecha = formato.substring(formato.length()-4);
		String producto = formato.substring(0, formato.length()-5);
    java.util.Date D = new java.util.Date();
		String archivo = "certificado"+ D.getTime();
		int fechaSig = Integer.parseInt(fecha) + 1;

		html = html + STBCL_GenerarBaseHTML.TBFL_CABEZA("Certificados de Retenci&oacute;n en la Fuente","Certificado de Retenci&oacute;n en la Fuente", "TBPKT_INFORMATIVO.TBPKT_RETENCION_FUENTE.TBCS_Retencion_Fuente", "", true);

		html = html + "<TABLE BGCOLOR=\"WHITE\" BORDER=0 CELLPADDING=2 CELLSPACING=0 COLS=2 RULES=\"ALL\" WIDTH=100%>";
		html = html + "<TR>";
		html = html + "<TD WIDTH=100%>";
		html = html + TBFL_GenerarDocumento(producto, contrato, fecha, version, archivo);
		html = html + "</TD></TR>";
		html = html + "<TR><TD COLSPAN=2>&nbsp;</TD></TR>";
		html = html + "<TR>";
		html = html + "<TD WIDTH=100%><B><font face=\"verdana\"><font color=\"#000000\"><font size=-2>Descarga del archivo para impresi&oacute;n: <A HREF="+ rutaVirtual+archivo + ".rtf>";
		html = html + archivo + ".rtf </font></font></font></b></TD>";
		html = html + "</TR>";
		html = html + "<TR><TD COLSPAN=2>&nbsp;</TD></TR>";
		html = html + "<TR><TD COLSPAN=2><CENTER><INPUT TYPE=BUTTON VALUE=\"Aceptar\" ONCLICK=history.go(-1);></CENTER></TD></TR>";
		html = html + "</TABLE>";
		html = html + STBCL_GenerarBaseHTML.TBFL_PIE;
		return html;
	}

private String TBFL_Opcion7(String formato, String contrato)
	{
		LinkedList listQuery;
    Iterator itQuery;
    String[] resultQuery;
		String html = "", query = "";
		String fecha = formato.substring(formato.length()-4);
		String producto = formato.substring(0, formato.length()-5);

		html = html + STBCL_GenerarBaseHTML.TBFL_CABEZA("Certificados de Retenci&oacute;n en la Fuente","Certificado de Retenci&oacute;n en la Fuente", "TBPKT_INFORMATIVO.TBPKT_RETENCION_FUENTE.TBCS_Retencion_Fuente", "", true);
		html = html + "<TABLE BGCOLOR=\"WHITE\" BORDER=0 CELLPADDING=2 CELLSPACING=0 COLS=2 RULES=\"ALL\" WIDTH=100%>";
		html = html + "<TR>";
		html = html + "<TD><B><font face=\"verdana\"><font color=\"#000000\"><font size=-2>Formato: </font></font></font></b></TD>";
		html = html + "<TD><B><font face=\"verdana\"><font color=\"#000000\"><font size=-2>"+formato+"</font></font></font></b></TD>";
		html = html + "</TR>";
		try{
			html = html + "<TR>";
			html = html + "<TD WIDTH=30%><B><font face=\"verdana\"><font color=\"#000000\"><font size=-2>Versi&oacute;n:</font></font></font></b></TD>";
			query = "SELECT  DISTINCT(CER_VERSION) VER, CER_VERSION, CER_VERSION, CER_VERSION "+
						"FROM TBCERTIFICADOS "+
						" WHERE CER_CON_PRO_CODIGO = ?  "+
							" AND CER_CON_NUMERO = ?  "+
							" AND CER_ANI_FOC_ANO = ?  "+
							" ORDER BY VER ";
			listQuery = TBFL_Consulta(query, new String[]{producto, contrato, fecha});
			itQuery = listQuery.iterator();
      if (!itQuery.hasNext())
      {
        html = html + "<TR><TD COLSPAN=2>ESTE CONTRATO NO TIENE NINGUNA VERSION, GENERE UNA PRIMERO</TD></TR>";
		    html = html + "<TR><TD COLSPAN=2>&nbsp;</TD></TR>";
        html = html + "<TR><TD><INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+ nuevaCadena +"'></TD></TR>";
        html = html + "<TR><TD><INPUT NAME=opcion VALUE= '2' TYPE=hidden></TD></TR>";
      }
      else
      {
        html = html + "<TD WIDTH=70%><SELECT NAME=version>";
			  while(itQuery.hasNext()){
		     	resultQuery = (String[]) itQuery.next();
		     	html = html + "<option VALUE='"+resultQuery[0]+"'><font face=\"verdana\"><font color=\"#000000\"><font size=-2>"+resultQuery[0]+"</font></font>";
		    }
		    html = html + "</SELECT></TD></TR>";
		html = html + "<TR><TD COLSPAN=2>&nbsp;</TD></TR>";
    html = html + "<TR><TD><INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+ nuevaCadena +"'></TD></TR>";
    html = html + "<TR><TD><INPUT NAME=formato VALUE= '"+formato+"' TYPE=hidden></TD></TR>";
    html = html + "<TR><TD><INPUT NAME=opcion VALUE= '8' TYPE=hidden></TD></TR>";
      }
		}
   	catch(SQLException sqle) {
     	sqle.printStackTrace(System.out);
   	}
    html = html + "<TR><TD COLSPAN=2><CENTER><INPUT TYPE=SUBMIT VALUE=\"Aceptar\"><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER></TD></TR>";
		html = html + "</TABLE>";
		html = html + STBCL_GenerarBaseHTML.TBFL_PIE;
		return html;
	}
private String TBFL_Opcion6(String producto)
	{
		LinkedList listQuery;
    Iterator itQuery;
    String[] resultQuery;
		String html = "", query = "";

		html = html + STBCL_GenerarBaseHTML.TBFL_CABEZA("Certificados de Retenci&oacute;n en la Fuente","Certificado de Retenci&oacute;n en la Fuente", "TBPKT_INFORMATIVO.TBPKT_RETENCION_FUENTE.TBCS_Retencion_Fuente", "", true);
		html = html + "<TABLE BGCOLOR=\"WHITE\" BORDER=0 CELLPADDING=2 CELLSPACING=0 COLS=2 RULES=\"ALL\" WIDTH=100%>";

		try{
			html = html + "<TR>";
			html = html + "<TD><B><font face=\"verdana\"><font color=\"#000000\"><font size=-2>Formato del Certificado:</font></font></font></b></TD>";
			html = html + "<TD><SELECT NAME=formato>";
			query = "SELECT FOC_PRO_CODIGO, FOC_ANO "+
						  " FROM TBFORMATOS_CERTIFICADO "+
              " WHERE FOC_PRO_CODIGO = ? "+
						  " ORDER BY FOC_PRO_CODIGO, FOC_ANO ";
			listQuery = TBFL_Consulta(query, new String[]{producto});
			itQuery = listQuery.iterator();
			while(itQuery.hasNext()){
		     	resultQuery = (String[]) itQuery.next();
		     	html = html + "<option VALUE='"+resultQuery[0]+"-"+resultQuery[1]+"'><font face=\"verdana\"><font color=\"#000000\"><font size=-2>"+resultQuery[0]+" ("+resultQuery[1]+")</font></font>";
		  }
		  html = html + "</SELECT></TD></TR>";
		}
   	catch(SQLException sqle) {
     	sqle.printStackTrace(System.out);
   	}

		html = html + "<TR><TD COLSPAN=2>&nbsp;</TD></TR>";
    html = html + "<TR><TD><INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+ nuevaCadena +"'></TD></TR>";
		html = html + "<TR><TD><INPUT NAME=opcion VALUE= '7' TYPE=hidden></TD></TR>";
		html = html + "<TR><TD COLSPAN=2><CENTER><INPUT TYPE=SUBMIT VALUE=\"Aceptar\"><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER></TD></TR>";
		html = html + "</TABLE>";
		html = html + STBCL_GenerarBaseHTML.TBFL_PIE;
		return html;
	}

private String TBFL_Opcion5(String formato, String contrato, String archivo)
	{
	    Connection con                  = null;
	    CallableStatement sprocedure    = null;
            String html = "";
            String fecha = formato.substring(formato.length()-4);
            String producto = formato.substring(0, formato.length()-5);
            html = html + STBCL_GenerarBaseHTML.TBFL_CABEZA("Certificados de Retenci&oacute;n en la Fuente","Certificado de Retenci&oacute;n en la Fuente", "TBPKT_INFORMATIVO.TBPKT_RETENCION_FUENTE.TBCS_Retencion_Fuente", "", true);
            html = html + "<TABLE BGCOLOR=\"WHITE\" BORDER=0 CELLPADDING=2 CELLSPACING=0 COLS=2 RULES=\"ALL\" WIDTH=100%>";
    try {
        
        con   =   DataSourceWrapper.getInstance().getConnection();
        sprocedure = con.prepareCall("{ call TBPBD_LLenar1Certificado(?, ?, ?, ?) }");
        sprocedure.setString(1, producto);
        sprocedure.setString(2, contrato);
        sprocedure.setString(3, fecha);
        sprocedure.registerOutParameter(4, Types.VARCHAR);
    	sprocedure.execute();
        if (!sprocedure.getString(4).equals("OK"))
            html = html + sprocedure.getString(4);
    }
    catch(SQLException sqle) {
	     	sqle.printStackTrace(System.out);
    }
    finally{
      try {
            DataSourceWrapper.closeStatement(sprocedure);
            DataSourceWrapper.closeConnection(con); 
      }
      catch(SQLException sqle) {
      }
    }

		html = html + "<TR>";
		html = html + "<TD WIDTH=100%>";
		html = html + TBFL_GenerarDocumento(producto, contrato, fecha, "", archivo);
		html = html + "</TD></TR>";
		html = html + "<TR><TD COLSPAN=2>&nbsp;</TD></TR>";
		html = html + "<TR>";
		html = html + "<TD WIDTH=100%><B><font face=\"verdana\"><font color=\"#000000\"><font size=-2>Descarga del archivo para impresi&oacute;n: <A HREF="+rutaVirtual+ archivo + ".rtf>";
		html = html + archivo + ".rtf </font></font></font></b></TD>";
		html = html + "</TR>";
		html = html + "<TR><TD COLSPAN=2>&nbsp;</TD></TR>";
		html = html + "<TR><TD COLSPAN=2><CENTER><INPUT TYPE=BUTTON VALUE=\"Aceptar\" ONCLICK=history.go(-1);></CENTER></TD></TR>";
		html = html + "<TR><TD><INPUT NAME=opcion VALUE= '6' TYPE=hidden></TD></TR>";
		html = html + "</TABLE>";
		html = html + STBCL_GenerarBaseHTML.TBFL_PIE;
		return html;
	}

	private String TBFL_Opcion2(String producto)
	{
		LinkedList listQuery;
    Iterator itQuery;
    String[] resultQuery;
		String html = "", query = "";


		html = html + STBCL_GenerarBaseHTML.TBFL_CABEZA("Certificados de Retenci&oacute;n en la Fuente","Generar un Certificado de Retenci&oacute;n en la Fuente", "TBPKT_INFORMATIVO.TBPKT_RETENCION_FUENTE.TBCS_Retencion_Fuente", "", true, "moduloInformativo.js", "return validaCRF2(this);");
		html = html + "<TABLE BGCOLOR=\"WHITE\" BORDER=0 CELLPADDING=2 CELLSPACING=0 COLS=2 RULES=\"ALL\" WIDTH=100%>";
		try {
			html = html + "<TR>";
			html = html + "<TD><B><font face=\"verdana\"><font color=\"#000000\"><font size=-2>Formato del Certificado:</font></font></font></b></TD>";
			html = html + "<TD><SELECT NAME=formato>";
			query = "SELECT FOC_PRO_CODIGO, FOC_ANO FA, FOC_ANO "+
						" FROM TBFORMATOS_CERTIFICADO "+
            " WHERE FOC_PRO_CODIGO = ? "+
						"ORDER BY FOC_PRO_CODIGO, FA ";
			listQuery = TBFL_Consulta(query, new String[]{producto});
			itQuery = listQuery.iterator();
			while(itQuery.hasNext()){
		     	resultQuery = (String[]) itQuery.next();
		     	html = html + "<option VALUE='"+resultQuery[0]+"-"+resultQuery[1]+"'><font face=\"verdana\"><font color=\"#000000\"><font size=-2>"+resultQuery[0]+" ("+resultQuery[1]+")</font></font>";
		  }
		  html = html + "</SELECT></TD></TR>";
	 	}
   	catch(SQLException sqle) {
     	sqle.printStackTrace(System.out);
   	}
    html = html + "<TR><TD><INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+ nuevaCadena +"'></TD></TR>";
 		html = html + "<TR>";
		html = html + "<TD WIDTH=35%><B><font face=\"verdana\"><font color=\"#000000\"><font size=-2><B><SUP>*</SUP>Nombre del archivo:</B></font></font></font></b></TD>";
		html = html + "<TD WIDTH=65%><font face=\"verdana\"><font color=\"#000000\"><font size=-2><INPUT NAME=archivo TYPE=text MAXLENGTH=12 SIZE=15> (p.e. crf01012002 sin extensi&oacute;n)</font></font></font></TD>";
		html = html + "</TR>";
		html = html + "<TR><TD COLSPAN=2>&nbsp;</TD></TR>";
		html = html + "<TR><TD COLSPAN=2><CENTER><INPUT TYPE=SUBMIT VALUE=\"Aceptar\"><INPUT TYPE=BUTTON VALUE=Regresar ONCLICK=history.go(-1);></CENTER></TD></TR>";
		html = html + "<TR><TD><INPUT NAME=opcion VALUE= '5' TYPE=hidden></TD></TR>";
		html = html + "</TABLE>";
		html = html + STBCL_GenerarBaseHTML.TBFL_PIE;
		return html;
	}

  protected int TBFL_Pos(ArrayList array, String elem){
    int i = 0;
    for (; i < array.size(); i++){
      String curr = array.get(i).toString();
      if(curr.equals(elem)) break;
    }
    return i;
	}
      /**
   * Genera el archivo RTF con los certificados para imprimir.
   * @param producto Código del producto.
   * @param contrato Código del contrato.
   * @param fecha Fecha del certificado, solo año.
   * @param version Versión del certificado.
   * @param archivoSalida Nombre del archivo de salida.
   * @return String HTML con los datos del certificado.
   */
  private String TBFL_GenerarDocumento(String producto, String contrato, String fecha, String version, String archivoSalida) {
  	LinkedList listQuery;
    Iterator itQuery;
    String[] resultQuery;
		ArrayList items = new ArrayList();
		ArrayList items2 = new ArrayList();
		ArrayList values = new ArrayList();
		String query= "", valor = "", nit = "", lversion = "";
		String pageHeader = "", pageFooter = "", html = "";
    String sContrato = "000000000";
		NumberFormat currencyNumberFormat = NumberFormat.getCurrencyInstance();
		DecimalFormat formatoID = new DecimalFormat("#,###,###,###,##0");
		int item, footItems;
		TBCL_RTF rtfFile;

    try {
    	try
			{
      // Consulta del NIT para el producto seleccionado
			query=  "SELECT PRO_NUMERO_IDENTIFICACION "+
							" FROM  TBPRODUCTOS "+
							" WHERE PRO_CODIGO = ? ";

			listQuery = TBFL_Consulta(query, new String[]{producto});
			resultQuery = (String[]) listQuery.getFirst();
			nit = resultQuery[0];

		// EnTBFL_CABEZAdo y pie de pagina para el certificado
			query=  "SELECT FOC_HEADER, FOC_FOOTER "+
							" FROM TBFORMATOS_CERTIFICADO "+
							" WHERE FOC_PRO_CODIGO = ? "+
								" AND FOC_ANO        = ? ";

			listQuery = TBFL_Consulta(query, new String[]{producto, fecha});
			resultQuery = (String[]) listQuery.getFirst();
			pageHeader = resultQuery[0];
			pageFooter = resultQuery[1];

			//Titulo de los Items
			query=  "SELECT ANI_ITC_CODIGO, ANI_POSICION, ANI_TITULO "+
							" FROM TBANOS_ITEMS "+
							" WHERE ANI_FOC_PRO_CODIGO = ? "+
								" AND ANI_FOC_ANO        = ? "+
							" ORDER BY ANI_POSICION";
			listQuery = TBFL_Consulta(query, new String[]{producto, fecha});
      itQuery = listQuery.iterator();

       // EnTBFL_CABEZAdo del archivo RTF
			rtfFile = new TBCL_RTF(rutaFisica + "Header.dat");

       //Archivo de salida
			rtfFile.TBPL_Open(rutaFisica + archivoSalida +".rtf");

			// Item 1  EnTBFL_CABEZAdo de cada página
       rtfFile.TBPL_AddItem(nit+"\\par\\par", rutaFisica + "HPage1.dat");
      rtfFile.TBPL_AddValue("");
	    rtfFile.TBPL_AddItem(pageHeader+"\\par\\par", rutaFisica + "HPage2.dat");
      rtfFile.TBPL_AddValue("");

       // Items del documento
	   	while(itQuery.hasNext()){
	     		resultQuery = (String[]) itQuery.next();
          items2.ensureCapacity(items2.size() + 1);
          items2.add(resultQuery[1]);

         // Si la posicion es mayor que 90 va en el pie
	       if (resultQuery[1].compareTo("90") < 0){
	         if (resultQuery[2].substring(0,1).equals("*")){
	           rtfFile.TBPL_AddItem(resultQuery[2].substring(1), rutaFisica + "Item1.dat");
             rtfFile.TBPL_AddValue("-");
             items.ensureCapacity(items.size() + 1);
	        	 items.add(resultQuery[2].substring(1));
	        	 values.ensureCapacity(values.size() + 1);
	        	 values.add("");
	         }
	         else
	         {
	           rtfFile.TBPL_AddItem(resultQuery[2], rutaFisica + "Item2.dat");
             rtfFile.TBPL_AddValue("-");
             items.ensureCapacity(items.size() + 1);
	      	   items.add(resultQuery[2]);
	      	   values.ensureCapacity(values.size() + 1);
	      	   values.add("-");
	         }
	       }
			}

         //  Pie de pagina
	    rtfFile.TBPL_AddItem("\\par "+pageFooter+"\\par\\par", rutaFisica + "Item1.dat");
	    rtfFile.TBPL_AddValue("");
	    footItems = rtfFile.length();
	    itQuery = listQuery.iterator();

         // Valores para cada Item
		  while(itQuery.hasNext()){
		     	resultQuery = (String[]) itQuery.next();
		       if (resultQuery[1].compareTo("90") >= 0){
		         rtfFile.TBPL_AddItem(resultQuery[2], rutaFisica + "Item3.dat");
		 		    rtfFile.TBPL_AddValue("-");
		       }
			}

		     // Ultimo valor Item
		  rtfFile.TBPL_AddItem("", rutaFisica + "FPage1.dat");
		  rtfFile.TBPL_AddValue("");

		// Items del certificado para este contrato
      sContrato += contrato;
      sContrato = sContrato.substring(sContrato.length()-9);

      lversion = version;
			if(lversion.equals(""))
			{
          query=  "SELECT NVL(MAX(cer_version), 1) "+
                  " FROM tbcertificados "+
                  " WHERE cer_con_pro_codigo = ? "+
                     " AND cer_con_numero     = ? "+
                     " AND cer_ani_foc_ano    = ? ";

          listQuery = TBFL_Consulta(query, new String[]{producto, contrato, fecha});
    			resultQuery = (String[]) listQuery.getFirst();
		    	lversion = resultQuery[0];
			}
         query=  "SELECT 	CER_ANI_ITC_CODIGO"+
												", CER_VALOR"+
												", ITC_REF_TIPO_DATO"+
                        ", ANI_POSICION "+
								" FROM	TBCERTIFICADOS"+
                    ", TBANOS_ITEMS "+
										", TBITEMS_CERTIFICADO "+
								" WHERE CER_CON_PRO_CODIGO 	 = ? "+
										" AND CER_CON_NUMERO 		 = ? "+
										" AND CER_ANI_FOC_ANO 		 = ? "+
                    " AND CER_VERSION				 = ? "+
                    " AND ANI_FOC_PRO_CODIGO = CER_CON_PRO_CODIGO "+
                    " AND ANI_FOC_ANO = CER_ANI_FOC_ANO "+
                    " AND ANI_ITC_CODIGO = CER_ANI_ITC_CODIGO "+
                    " AND ITC_CODIGO =	CER_ANI_ITC_CODIGO "+
								" ORDER BY 	ANI_POSICION ";
         listQuery = TBFL_Consulta(query, new String[]{producto, contrato, fecha, lversion});
			itQuery = listQuery.iterator();

        //  Dependiendo del tipo (Moneda, identificador, etc) del Item lo formatea para ser impreso
			while (itQuery.hasNext()){
          resultQuery = (String[]) itQuery.next();
					valor = resultQuery[1];
          item = TBFL_Pos(items2, resultQuery[3]);
					if (resultQuery[3].compareTo("90") < 0){
          //
						if (item < rtfFile.length()){
							if(resultQuery[2].equals("STD004")){
								valor = currencyNumberFormat.format(Double.parseDouble(valor));
							}else if(resultQuery[2].equals("STD001")){
								valor = ""+ Integer.parseInt(valor);
								}else if(resultQuery[2].equals("STD005")){
                    valor = valor.trim();
									valor = formatoID.format(Long.parseLong(valor));
					 	  	}
									rtfFile.TBPL_SetValue(item+3, valor);
                  values.set(item, valor);
					     	}
					    }
					 else
					 {
							rtfFile.TBPL_SetValue(item+4, valor);
					 }
			}

	    html = html + "<TABLE BGCOLOR=\"WHITE\" BORDER=1 COLS=2 WIDTH=100%>";
	    for (int con = 0; con < values.size(); con++) {
					html = html + "<TR><TD WIDTH=40%><FONT face=\"verdana\"><FONT color=\"#000000\"><FONT size=-2><P>"+
									items.get(con) +"</P></FONT></FONT></FONT></TD>";
					html = html + "<TD WIDTH=60%><FONT face=\"verdana\"><FONT color=\"#000000\"><FONT size=-2><P>";
					if (values.get(con).equals(""))
						html = html + "&nbsp;";
					else
						html = html + values.get(con);
					html = html + "</P></FONT></FONT></FONT></TD></TR>";
      }
      html = html + "</TABLE>";
        // Cierra el archivo y termina
      rtfFile.TBPL_PrintPage();
    	rtfFile.TBPL_Close();
	   	}
	   	catch(SQLException sqle)
        {	html = "Excepción: "+ sqle;}
    }
		catch(IOException e)
			{	html = "IO Error!!" + e;}
    return html;
  }

private void TBPL_GetRutas ()
  {
    LinkedList listQuery;
    String[] resultQuery;
		String query = "";

		try{

			query = "SELECT  REF_VALOR, REF_VALOR, REF_VALOR, REF_VALOR "+
						"FROM TBREFERENCIAS "+
						" WHERE REF_CODIGO = ?  ";
			listQuery = TBFL_Consulta(query, new String[]{"SCR001"});
			resultQuery = (String[]) listQuery.getFirst();
      rutaFisica = resultQuery[0];
      listQuery = TBFL_Consulta(query, new String[]{"SCR002"});
      resultQuery = (String[]) listQuery.getFirst();
      rutaVirtual = resultQuery[0];
		}
   	catch(SQLException sqle) {
     	System.out.println(" ");//+ sqle
   	}
  }


  /**
   * Get Servlet information
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_INFORMATIVO.TBPKT_RETENCION_FUENTE.TBCS_Retencion_Fuente Information";
  }
  
      /**
   * Ejecuta una consulta con los par&aacute;metros definidos
   * @param query Consulta que desea hacerse.
   * @param params Par&aacute;metros de la consulta.
   * @return java.util.LinkedList
   */
   private LinkedList TBFL_Consulta(String query, String[] params) throws SQLException {
    LinkedList retList= new LinkedList();

    Connection con= null;
    PreparedStatement ps= null;
    ResultSet rs= null;

    try {
      con   =   DataSourceWrapper.getInstance().getConnection();
      ps= con.prepareStatement(query);

      for(int i= 0; i< params.length; i++) {
          ps.setString(i+1,params[i]);
        }
      rs= ps.executeQuery();
      while(rs.next()) {
        int count = rs.getMetaData().getColumnCount();
        String[] record = new String[count];
        for (int i=0; i<count; i++)
          record[i] = rs.getString(i+1);
        retList.add(record);
      }
    }
    finally{
      try {
            DataSourceWrapper.closeStatement(rs);
            DataSourceWrapper.closeStatement(ps);
            DataSourceWrapper.closeConnection(con); 
      }
      catch(SQLException sqle) {
      }
    }
    return retList;
  }

  


  /**
   * Inserta el caracter especificado en la cadena dada, hasta llenar las
   * posiciones indicadas.
   * @param cadena La cadena de entrada.
   * @param caracter El caracter con el cual se quiere llenar la cadena.
   * @param longitud La longitud deseada de la cadena de regreso.
   * @param derecha true para justificar a la derecha, false para justificar a la izquierda.
   * @return java.lang.String
   */
  private String fill(String cadena, char caracter, int longitud, boolean derecha) {
    if(cadena == null || cadena.length() >= longitud) return cadena;

    StringBuffer sb= new StringBuffer(cadena);
    for(int i= cadena.length(); i< longitud; i++ ) {
      if(derecha) sb.insert(0,caracter);
      else sb.append(caracter);
    }

    return sb.toString();
  }

  /**
   * Da formato al n&uacute;mero pasado como par&aacute;metro para su inserci&oacute;n en los
   * registros del archivo de salida.
   * @param numero El n&uacute;mero de entrada.
   * @param longitud La longitud deseada de la cadena de regreso.
   * @return java.lang.String
   */
  private String formatNumber(String numero, int longitud) {
    if(numero == null || numero.equals("")) return numero;

    StringBuffer sb= new StringBuffer();
    StringTokenizer st= new StringTokenizer(numero, ".-");

    while(st.hasMoreTokens()) sb.append(st.nextToken());

    for(int i= sb.length(); i< longitud; i++ )
      sb.insert(0,'0');

    return sb.toString();
  }
}

