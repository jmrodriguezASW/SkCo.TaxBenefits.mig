package TBPKT_INFORMATIVO.TBPKT_INFORMACION_TRASLADOS;

import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;

import TBPKT_UTILIDADES.TBPKT_RTF.*;

import TBPKT_UTILIDADES.TBPKT_SEGURIDAD.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

import java.io.*;

import java.math.*;

import java.sql.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet que genera el archivo de Informaci&oacute;n de Traslados
 * <P>
 * @author ALFA GL Ltda
 */
public class TBCS_Informacion_Traslados extends HttpServlet {

  /**
   * Initializa las variables globales
   */
  String cadena = "";
  double v_difrendimientos = 0;
  String v_difrendimientos_a = "";
  String nuevaCadena = "";
  String rutaVirtual = "";
  String rutaFisica = "";
  
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   * Proceso del HTTP POST
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    /* Se quitan las siguientes dos lineas para que funcione el boton regresar */
    //response.setHeader("Pragma", "No-Cache");
    //response.setDateHeader("Expires", 0);
    String parametros[] = new String[8];
    String  cadena = request.getParameter("cadena");
    nuevaCadena = cadena;
    String ip_tax = request.getRemoteAddr();
    TBCL_Seguridad Seguridad = new TBCL_Seguridad();
    PrintWriter out = new PrintWriter (response.getOutputStream());
    parametros = Seguridad.TBFL_Seguridad(cadena, out, ip_tax);

    String strTipo= request.getParameter("tipo");
    if(strTipo == null) strTipo= "1";
    int tipo= Integer.parseInt(strTipo);
    String[] paramsTmp= TBFL_GeneraParams(tipo, request);
    String nit_afpDestino= paramsTmp[paramsTmp.length-1];

    String[] params= new String[paramsTmp.length-1];
    System.arraycopy(paramsTmp,0,params,0,paramsTmp.length-1);

    out.println(STBCL_GenerarBaseHTML.TBFL_CABEZA("Informaci&oacute;n de Traslados","Confirmaci&oacute;n de archivo de Informaci&oacute;n de Traslados", "", "", false));
    out.println("<TABLE BGCOLOR=\"WHITE\" BORDER=0 CELLPADDING=2 CELLSPACING=0 COLS=2 RULES=\"ALL\" WIDTH=100%>");


    params = TBFL_GenerarDocumento(tipo, nit_afpDestino, params);
    if(params != null)
    {
      out.print("<TR><TD WIDTH=35%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.print("AFP de destino :</FONT></TD>");
      out.println("<TD WIDTH=65%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>"+params[0]+"</FONT></TD></TR>");

      out.println("<TR><TD COLSPAN=2>&nbsp;</TD></TR>");

      out.print("<TR><TD WIDTH=35%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.print("Valor total del traslado :</FONT></TD>");
      out.println("<TD WIDTH=65%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>"+params[1]+"</FONT></TD></TR>");

      out.print("<TR><TD WIDTH=35%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.print("N&uacute;mero de afiliados :</FONT></TD>");
      out.println("<TD WIDTH=65%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>"+params[2]+"</FONT></TD></TR>");

      out.println("<TR><TD COLSPAN=2>&nbsp;</TD></TR>");

      out.print("<TR><TD WIDTH=35%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.print("Archivo generado :</FONT></TD>");
      out.print("<TD WIDTH=65%><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.print("<A HREF=\""+params[3]+"\">"+params[4]+"</A>");
      out.println("</FONT></TD></TR>");

      out.println("<TR><TD COLSPAN=2>&nbsp;</TD></TR>");

      out.println("<TR><TD COLSPAN=2><CENTER><INPUT TYPE=\"BUTTON\" VALUE=\"Aceptar\" OnClick='history.go(-1);'></CENTER></TD></TR>");
    }
    else {
      out.print("<TR><TD COLSPAN=2><CENTER><FONT FACE=\"Verdana, Arial, Helvetica, sans-serif\" SIZE=1>");
      out.print("No se encontraron registros</FONT></CENTER></TD></TR>");

      out.println("<TR><TD COLSPAN=2>&nbsp;</TD></TR>");
      out.println("<TR><TD COLSPAN=2><CENTER><INPUT TYPE=\"BUTTON\" VALUE=\"Aceptar\" OnClick='history.go(-1);'></CENTER></TD></TR>");
    }
    out.println("</TABLE>");
    out.println(STBCL_GenerarBaseHTML.TBFL_PIE);
    out.close();
  }

  /**
   * Genera el arreglo de par&aacute;metros a partir del tipo<BR>
   * @param tipo Tipo de consulta
   * @param request Objeto que recibe el servlet
   * @return java.lang.String[]
   */
  private String[] TBFL_GeneraParams(int tipo, HttpServletRequest request) {
    String afp,fechaInicial,fechaFinal,contrato,producto,cedula,nit;
    String codigo_afp, nit_afpDestino;
    String[] params;

    afp= request.getParameter("afp");
    if(afp == null) afp= "22_08001901899";
    fechaInicial= request.getParameter("fechaInicial");
    if(fechaInicial == null) fechaInicial= "2002-01-01";
    fechaFinal= request.getParameter("fechaFinal");
    if(fechaFinal == null) fechaFinal= "2002-12-31";
    contrato= request.getParameter("contrato");
    producto= request.getParameter("producto");
    cedula= request.getParameter("cedula");
    nit= request.getParameter("nit");

    StringTokenizer st= new StringTokenizer(afp,"_");
    codigo_afp= st.nextToken();
    nit_afpDestino= st.nextToken();


    switch(tipo) {
      case 1: //Básico.
        params= new String[]{codigo_afp,fechaInicial,fechaFinal,nit_afpDestino};
        break;
      case 2: //Cédula.
        params= new String[]{cedula,codigo_afp,fechaInicial,fechaFinal,nit_afpDestino};
        break;
      case 3: //Producto y contrato.
        params= new String[]{contrato,producto,codigo_afp,fechaInicial,fechaFinal,nit_afpDestino};
        break;
      case 4: //Producto, contrato y cédula.
        params= new String[]{cedula,contrato,producto,codigo_afp,fechaInicial,fechaFinal,nit_afpDestino};
        break;
      case 5: //Producto y NIT.
        params= new String[]{nit,producto,codigo_afp,fechaInicial,fechaFinal,nit_afpDestino};
        break;
      default:
        params= new String[0];
        break;
    }
    return params;
  }

  /**
   * Genera el archivo de Informaci&oacute;n de Traslados<BR>
   * @param tipo Tipo de consulta
   * @param nit_afpDestino NIT de la AFP destino de los traslados
   * @param params Par&aacute;metros de la consulta
   * @return java.lang.String[] NULL si hubo alg&uacute;n error durante el proceso
   */
  //private 
  public String[] TBFL_GenerarDocumento(int tipo, String nit_afpDestino, String[] params)
  {

    String[] retArray = null;
    /**Agregado PMT**/
        String[] retArrayAfiliados = null;
        String[] retArrayAfiliadosAux = null;
    /**Fin Agregado PMT**/
        
    LinkedList Resultado = null;
    LinkedList Resultado2 = null;
    /**Agregado PMT**/
        LinkedList Resultado3 = null;
    /**Fin Agregado PMT**/
        
    String nit_afpOrigen;
    String tipo_documento, documento, fecha_traslado, apellidos, nombres;
    String oldNit_afpOrigen= "", oldDocumento= "", oldFecha_traslado= "";
    String oldTipo_documento= "", oldApellidos= "", oldNombres= "";

    String fecha_consignacion, tipo_aporte;
    String capital, retencion_capital, rendimientos, signo;

    String registro3= "", registro2= "", registro1= "";
    String aSigno, eSigno, hoy;

    double nCapital, nRetencionCapital, nRendimientos;
    double aCapital= 0.0, aRetencionCapital= 0.0, aRendimientos= 0.0;
    double eCapital= 0.0, eRetencionCapital= 0.0, eRendimientos= 0.0;
    double tValor= 0.0;
    int tAfiliados= 0;
    
    /**Agregado PMT**/
      int cantidadProcesos=1;
    /**Fin Agregado PMT**/
      
    double vsumren = 0.0;
    double vsumren_e = 0.0;

    double vsumtotren = 0.0;
    String vsumtotren_s = " ";
    String vsumtotren_s2 = " ";
    double vsumtotren_e = 0.0;
    String vsumtotren_s_e = " ";
    String vsumtotren_s2_e = " ";
    boolean v_enc_dif  = true;

    double vdif    = 0.0;
    double vdif_e   = 0.0;
    boolean printRegistro1, printRegistro2, hayRegistros= false;
    String v_docnew = "XX";
    String v_docold = "XX";

    String[] datosTraslado;
    String[] datosTraslado2;
    
    /** Agregado PMT **/
        String[] datosTraslado3;
    /** Fin Agregado PMT **/
    
    Iterator traslados;
    Iterator traslados2;
    File archivo= null;
    /**Agregado MOS*/
    String transaccion = "";
    String transacOld  = "";
    String contrato = "";
    String contratOld  = "";
    File archivoLog= null;
    String v_redondeo ="";
    double v_diferencia =0;
    double valor_ret = 0.0;
							
    /**Fin Agregado MOS*/
    try
    {

      TBPL_GetRutas();
      java.util.Date fHoy= new java.util.Date();
      String anio= ""+ (fHoy.getYear()+1900);
      String mes= "00"+ (fHoy.getMonth()+1);
      String dia= "00"+ fHoy.getDate();
      hoy= anio+ mes.substring(mes.length()-2)+ dia.substring(dia.length()-2);
      /***MOS*/
      //rutaFisica ="Y:\\TaxBenefits\\Taxb\\Archivos\\Traslados\\";
      /***/
            
      int tmp= 1;
      String consec;
      
		
      /**Agregado MOS*/
      do {
           consec= "00"+ tmp++;
                archivoLog= new File(rutaFisica+ "PS"+ params[params.length-3]+ hoy+ consec.substring(consec.length()-2)+ "Log.txt");
																								
      }while(archivoLog.exists());
      String arTmpLog= archivoLog.getAbsolutePath();
        
      arTmpLog= arTmpLog.substring(0,arTmpLog.length()-1);
      archivoLog= new File(arTmpLog);
      PrintWriter fileOutLog= new PrintWriter(new BufferedWriter(new FileWriter(archivoLog)));
      String arTmp= "";

              
      PrintWriter fileOut= null;
      /**Fin agregado MOS*/

      /**Comentado MOS*/
      /*do {
        consec= "00"+ tmp++;
        archivo= new File(rutaFisica+ "PS"+ params[params.length-3]+ hoy+ consec.substring(consec.length()-2)+ ".txt");
      }while(archivo.exists());
      String arTmp= archivo.getAbsolutePath();

      arTmp= arTmp.substring(0,arTmp.length()-1);
      archivo= new File(arTmp);
      PrintWriter fileOut= new PrintWriter(new BufferedWriter(new FileWriter(archivo)));*/
      /**Fin comentado MOS*/

      /**Agregado PMT**/
        if(tipo==1)
        {
            Resultado3      =   TBFL_Consulta3(params);
            
            if(Resultado3 !=null)
            {
                cantidadProcesos =   Resultado3.size();
            }
            else
            {
                cantidadProcesos = 0;
            }
        }
      /**Fin Agregado PMT**/

      /**Agregado PMT**/
      for(int indice=0; indice < cantidadProcesos; indice++)
      {
		  
          if(Resultado3 != null)
          {
              tipo=3;
              datosTraslado3 = (String[]) Resultado3.get(indice);
                  
              if(params.length==3)
              {
                  String[] paramsAux = params;
				  
                  params=new String[5];
                  params[0]= datosTraslado3[1];
                  params[1]= datosTraslado3[0];
                  params[2]= paramsAux[0];
                  params[3]= paramsAux[1];
                  params[4]= paramsAux[2];
              }
              else
              {
                  params[0]= datosTraslado3[1];
                  params[1]= datosTraslado3[0];
              }
                  v_docold    = "XX";
                  v_docnew    = "XX";
                  arTmp       = "";
                  documento   = "";
                  hayRegistros= false;
                  contrato     = "";
                  contratOld   = "";
                  transaccion  = "";
                  transacOld   = "";
                  v_diferencia = 0;    
                  v_redondeo   = "";
                  valor_ret    = 0.0;
                              
                  aCapital= 0.0;
                  aRendimientos= 0.0;
                  aRetencionCapital= 0.0;
                  rendimientos="";
                  nRendimientos=0.0;
                  eCapital= 0.0;
                  eRetencionCapital= 0.0;
                  eRendimientos= 0.0;
          }
      /**Fin Agregado PMT**/
          
      Resultado = TBFL_GetTraslados(tipo, params);
      if(Resultado != null)
      {
          traslados = Resultado.iterator();
		      while(traslados.hasNext())
          {
		        hayRegistros= true;
		        datosTraslado= (String[])traslados.next();

		        nit_afpOrigen= datosTraslado[0];

		        tipo_documento= datosTraslado[1];
		        documento= datosTraslado[2];
		        fecha_traslado= datosTraslado[7];
		        apellidos= datosTraslado[3]+ "                    ";
		        nombres= datosTraslado[4]+ "                    ";

            v_docold = v_docnew;
            v_docnew = "00"+documento;
              

            if( !v_docold.trim().equals(v_docnew.trim()))
            {
             vsumtotren  = 0.0;
             vsumren     = 0.0;
             vsumtotren_e  = 0.0;
             vsumren_e     = 0.0;
             /**Agregado MOS*/
             valor_ret = 0.0;
             /**Fin Agregado MOS*/
    


             Resultado2 = TBFL_GetTraslados_TotRen(tipo,params,v_docnew);
             if(Resultado2 != null)
             {
              traslados2 = Resultado2.iterator();
               while(traslados2.hasNext())
               {
                datosTraslado2= (String[])traslados2.next();
                  if(datosTraslado2[0].equals("A"))
                     vsumtotren += (Double.parseDouble(datosTraslado2[1] ));
                  else
                     vsumtotren_e += (Double.parseDouble(datosTraslado2[1] ));
                   /**************añadir codigo por redondeo****/
                   /**Agregado MOS*/
                   valor_ret += (Double.parseDouble(datosTraslado2[1]))+ (Double.parseDouble(datosTraslado2[3]))-(Double.parseDouble(datosTraslado2[4]));
                   
                   if (!transaccion.equals(datosTraslado2[2])){
                      transacOld = transaccion;
                      contratOld = contrato;
                      if (transacOld.trim().equals("") && contratOld.trim().equals(""))
                      {
                       transacOld = datosTraslado2[2];
                       contratOld = datosTraslado2[5];
                      }
                      transaccion = datosTraslado2[2];
                      contrato = datosTraslado2[5];
                      v_redondeo = getRedondeo(transacOld, contratOld);
                   }
                   /**Fin agregado MOS*/
               }

             }
             /**Agregado MOS*/
             if (v_redondeo.equals("Y")){
               v_diferencia = Math.round(valor_ret)-(valor_ret);
             }
             /**Fin agregado MOS*/
             vsumtotren_s2 = formatNumber(vsumtotren,17);
             if(vsumtotren <0)
               vsumtotren = Double.parseDouble(vsumtotren_s2)*-1;
             else
               vsumtotren = Double.parseDouble(vsumtotren_s2);

             vsumtotren_s2_e = formatNumber(vsumtotren_e,17);
             if(vsumtotren_e <0)
               vsumtotren_e = Double.parseDouble(vsumtotren_s2_e)*-1;
             else
               vsumtotren_e = Double.parseDouble(vsumtotren_s2_e);
             /***********************************************/

            }




		        printRegistro1= !(oldNit_afpOrigen.equals(nit_afpOrigen) || oldNit_afpOrigen.equals(""));
		        printRegistro2= !((oldDocumento.equals(documento) && oldFecha_traslado.equals(fecha_traslado)) || oldDocumento.equals(""));

		        if(printRegistro1 || printRegistro2)
            {
              
 		          aSigno= (aRendimientos < 0.0)?"-":"+";
		          eSigno= (eRendimientos < 0.0)?"-":"+";

              registro2= "2"+ oldTipo_documento+ oldDocumento+ oldFecha_traslado+ oldApellidos+ oldNombres+
		                   formatNumber(aCapital, 17)+ formatNumber(aRendimientos, 17)+ aSigno+ formatNumber(aRetencionCapital, 17)+
		                   formatNumber(eCapital, 17)+ formatNumber(eRendimientos, 17)+ eSigno+ formatNumber(eRetencionCapital, 17);
		          fileOut.println(registro2);
              
              /**Agregado MOS*/
              double v_valor = aCapital+ aRendimientos+ eCapital+ eRendimientos;
              //v_redondeo = getRedondeo(transacOld);
              if(v_redondeo.equals("Y"))
              {
                v_valor=Math.round(v_valor);
              }
              
              registro1= "1"+ hoy+ fill(""+1,'0',5,true)+ formatNumber(v_valor,17)+ oldNit_afpOrigen+ nit_afpDestino;
		          fileOut.print(registro1);
              /** Comentado PMT **/
                //fileOutLog.println(oldTipo_documento+ oldDocumento+ oldFecha_traslado+ oldApellidos+ oldNombres);
              /** Fin Comentado PMT **/
              
              fileOut.close();
              
              /** Agregado y Modificado PMT **/
              if(archivo.exists())
              {
                  (new TBCL_ManageFile()).TBPL_inv(arTmp, arTmp+ "t");
                  archivo.delete();
              }
              /** Fin Agregado y Modificado PMT **/
              
              do {
                consec= "00"+ tmp++;
                archivo= new File(rutaFisica+ "PS"+ params[params.length-3]+ hoy+ consec.substring(consec.length()-2)+ documento+".txt");
              }while(archivo.exists());
              arTmp= archivo.getAbsolutePath();
        
              arTmp= arTmp.substring(0,arTmp.length()-1);
              archivo= new File(arTmp);
              fileOut= new PrintWriter(new BufferedWriter(new FileWriter(archivo)));
              /**Fin agregado MOS*/
              
		          tAfiliados++;
		          tValor+= aCapital+ aRendimientos+ eCapital+ eRendimientos;
		          aCapital= aRetencionCapital= aRendimientos= 0.0;
		          eCapital= eRetencionCapital= eRendimientos= 0.0;

		          /**Comentado MOS*/
		          /*if(printRegistro1)
              {

                registro1= "1"+ hoy+ fill(""+tAfiliados,'0',5,true)+ formatNumber(tValor,17)+ oldNit_afpOrigen+ nit_afpDestino;
		            fileOut.println(registro1);
                //tAfiliados= 0;
		            //tValor= 0.0;
		          }*/
              /**Fin comentado MOS*/

		        }

		        oldNit_afpOrigen= nit_afpOrigen;
		        oldTipo_documento= tipo_documento;
		        oldDocumento= documento;
		        oldFecha_traslado= fecha_traslado;
		        oldApellidos= apellidos;
		        oldNombres= nombres;


		        fecha_consignacion= datosTraslado[5];
		        tipo_aporte= datosTraslado[6];

		        nCapital= Double.parseDouble(datosTraslado[8]);
		        nRendimientos= Double.parseDouble(datosTraslado[9]);
		        signo= datosTraslado[10];
		        nRetencionCapital= Double.parseDouble(datosTraslado[11]);

		        if(tipo_aporte.equals("A"))
            {

		          aCapital+= nCapital;
		          aRetencionCapital+= nRetencionCapital;
		          aRendimientos+= nRendimientos;
              vsumtotren_s= formatNumber(nRendimientos,17);
              if(signo.equals("+"))
              {
       	        vsumren = vsumren + (Double.parseDouble(vsumtotren_s));
              }
              else if(signo.equals("-"))
              {
       	        vsumren = vsumren - (Double.parseDouble(vsumtotren_s));
              }
              vdif  = vsumtotren - vsumren;


              String v_ren = "";
              double v_ren2 = 0;
  
               if(Math.abs(vdif)>0 && Math.abs(vdif)<9)
               {

                v_enc_dif = false;
                if(vsumren > vsumtotren)
                {
                 v_ren = formatNumber(nRendimientos, 17);
                 if(signo.equals("-"))
                   v_ren2 = Double.parseDouble(v_ren) + Math.abs(vdif);
                 else if(signo.equals("+"))
                   v_ren2 = Double.parseDouble(v_ren) - Math.abs(vdif);


                 rendimientos= formatNumber(v_ren2/100,17);

                }
                else
                {
                 v_ren = formatNumber(nRendimientos, 17);
                 if(signo.equals("-"))
                   v_ren2 = Double.parseDouble(v_ren) - Math.abs(vdif);
                 else if(signo.equals("+"))
                   v_ren2 = Double.parseDouble(v_ren) + Math.abs(vdif);

                 rendimientos= formatNumber(v_ren2/100,17);

                }
               }
               else
               {
                rendimientos= formatNumber(nRendimientos,17);

               }


		        }
		        else
            {
		          tipo_aporte= "E";
		          eCapital+= nCapital;
		          eRetencionCapital+= nRetencionCapital;
              eRendimientos+= nRendimientos;
              vsumtotren_s_e= formatNumber(nRendimientos,17);

              if(signo.equals("+"))
              {
                vsumren_e = vsumren_e + (Double.parseDouble(vsumtotren_s_e));

              }
              else if(signo.equals("-"))
              {
                vsumren_e = vsumren_e - (Double.parseDouble(vsumtotren_s_e));

              }
                vdif_e  = vsumtotren_e - vsumren_e;


              String v_ren_e = "";
              double v_ren2_e = 0;
               if(Math.abs(vdif_e)>0 && Math.abs(vdif_e)<9)
               {


                if(vsumren_e > vsumtotren_e)
                {
                 v_ren_e = formatNumber(nRendimientos, 17);
                 if(signo.equals("-"))
                   v_ren2_e = Double.parseDouble(v_ren_e) + Math.abs(vdif_e);
                 else if(signo.equals("+"))
                   v_ren2_e = Double.parseDouble(v_ren_e) - Math.abs(vdif_e);


                 rendimientos= formatNumber(v_ren2_e/100,17);

                }
                else
                {
                 v_ren_e = formatNumber(nRendimientos, 17);
                 if(signo.equals("-"))
                   v_ren2_e = Double.parseDouble(v_ren_e) - Math.abs(vdif_e);
                 else if(signo.equals("+"))
                   v_ren2_e = Double.parseDouble(v_ren_e) + Math.abs(vdif_e);


                 rendimientos= formatNumber(v_ren2_e/100,17);

                }
               }
               else
               {
                rendimientos= formatNumber(nRendimientos,17);

               }
		        }


            /*vsumtotren_s= formatNumber(nRendimientos,17);
		        vsumren = vsumren + (Double.parseDouble(vsumtotren_s));*/


            //rendimientos= formatNumber(nRendimientos,17);
            capital= formatNumber(nCapital, 17);
		        retencion_capital= formatNumber(nRetencionCapital, 17);

              /**Agregado MOS*/
              if (arTmp.trim().equals("")){
                do {
                  consec= "00"+ tmp++;
                  archivo= new File(rutaFisica+ "PS"+ params[params.length-3]+ hoy+ consec.substring(consec.length()-2)+ documento+".txt");
                }while(archivo.exists());
                arTmp= archivo.getAbsolutePath();
          
                arTmp= arTmp.substring(0,arTmp.length()-1);
                archivo= new File(arTmp);
                fileOut= new PrintWriter(new BufferedWriter(new FileWriter(archivo)));
                //fileOutLog.println(oldTipo_documento+ oldDocumento+ oldFecha_traslado+ oldApellidos+ oldNombres);
              }
              if (v_diferencia>0 || v_diferencia<0)
              {
                rendimientos = formatNumber(Double.parseDouble(rendimientos)/100+v_diferencia,17);
                nRendimientos = nRendimientos+v_diferencia;
                aRendimientos = aRendimientos+v_diferencia;
                v_diferencia =0;
              }
              /**Fin agregado MOS*/
             registro3= "3"+ tipo_documento+ documento+ fecha_consignacion+ tipo_aporte+ capital+ retencion_capital+ rendimientos+ signo;
             fileOut.println(registro3);

		        oldDocumento= documento;
		        oldFecha_traslado= fecha_traslado;
		      }//wile
          if(hayRegistros)
          {
              
		        aSigno= (aRendimientos < 0.0)?"-":"+";
		        eSigno= (eRendimientos < 0.0)?"-":"+";

            registro2= "2"+ oldTipo_documento+ oldDocumento+ oldFecha_traslado+ oldApellidos+ oldNombres+
		                 formatNumber(aCapital, 17)+ formatNumber(aRendimientos, 17)+ aSigno+ formatNumber(aRetencionCapital, 17)+
		                 formatNumber(eCapital, 17)+ formatNumber(eRendimientos, 17)+ eSigno+ formatNumber(eRetencionCapital, 17);
		        fileOut.println(registro2);
            
            /**Agregado MOS*/
            double v_valor = aCapital+ aRendimientos+ eCapital+ eRendimientos;
            //v_redondeo = getRedondeo(transaccion, contrato);  
            if(v_redondeo.equals("Y"))
            {
              v_valor= Math.round(v_valor);
            }
            
            registro1= "1"+ hoy+ fill(""+1,'0',5,true)+ formatNumber(v_valor,17)+ oldNit_afpOrigen+ nit_afpDestino;
		        fileOut.print(registro1);
            
            /** Comentado PMT **/
                //fileOutLog.println(oldTipo_documento+ oldDocumento+ oldFecha_traslado+ oldApellidos+ oldNombres);
            /** Fin Comentado PMT **/
            
            fileOut.close();
            
            /** Agregado y Modificado PMT */
            if(archivo.exists())
            {
                (new TBCL_ManageFile()).TBPL_inv(arTmp, arTmp+ "t");
                archivo.delete();
            }
            /** Fin Agregado y Modificado PMT */
            
            /*do {
              consec= "00"+ tmp++;
              archivo= new File(rutaFisica+ "PS"+ params[params.length-3]+ hoy+ consec.substring(consec.length()-2)+ oldDocumento+".txt");
            }while(archivo.exists());
            arTmp= archivo.getAbsolutePath();
      
            arTmp= arTmp.substring(0,arTmp.length()-1);
            archivo= new File(arTmp);
            fileOut= new PrintWriter(new BufferedWriter(new FileWriter(archivo)));*/
            /**Fin agregado MOS*/
              
            /**Agregado PMT*/
            
                if(retArrayAfiliados == null)
                {
                        retArrayAfiliados = new String[1];
                        retArrayAfiliados[0] =oldDocumento;
                        fileOutLog.println(oldTipo_documento+ oldDocumento+ oldFecha_traslado+ oldApellidos+ oldNombres);
                }
                else
                {
                        retArrayAfiliadosAux =retArrayAfiliados;
                        retArrayAfiliados = new String[retArrayAfiliadosAux.length];
                        int indiceAfialiados=0;
                        boolean estaAfiliado=false;
                    
                        for (indiceAfialiados=0; indiceAfialiados<retArrayAfiliadosAux.length; indiceAfialiados++  )            
                        {
                            if(oldDocumento.equals(retArrayAfiliadosAux[indiceAfialiados]))
                            {
                                    estaAfiliado=true;
                                    indiceAfialiados = retArrayAfiliadosAux.length+1;
                            }
                        }
                    
                    if(estaAfiliado==false)
                    {
                        retArrayAfiliados = new String[retArrayAfiliadosAux.length +1 ];
                    }
                    
                    for (indiceAfialiados=0; indiceAfialiados<retArrayAfiliadosAux.length; indiceAfialiados++  )            
                    {
                        retArrayAfiliados[indiceAfialiados] = retArrayAfiliadosAux[indiceAfialiados];
                    }
                    
                    if(estaAfiliado==false)
                    {
                        retArrayAfiliados[retArrayAfiliados.length -1 ] =oldDocumento;
                        fileOutLog.println(oldTipo_documento+ oldDocumento+ oldFecha_traslado+ oldApellidos+ oldNombres);
                    }
                }
                            
                tAfiliados = retArrayAfiliados.length;
            /**Fin agregado PMT*/  
              
            /**Comentado PMT*/
                //tAfiliados++;
            /**Fin Comentado PMT*/
                
		        tValor+= aCapital+ aRendimientos+ eCapital+ eRendimientos;

            /**Comentado MOS*/
                //fileOut.print(registro1);
            /**Fin comentado MOS*/
         
		        NumberFormat format= NumberFormat.getCurrencyInstance();

		        String afp_codigo= params[params.length-3];
		        String total= format.format(tValor);
		        String link= arTmpLog.substring(arTmpLog.lastIndexOf('\\')+1)+ "t";
		        String href= rutaVirtual+ link;

		        retArray= new String[]{getDescAFP(afp_codigo),total,""+tAfiliados,href,link};
		      }

          /**Comentado MOS*/
                      /*fileOut.close();
                        (new TBCL_ManageFile()).TBPL_inv(arTmp, arTmp+ "t");
                      archivo.delete();*/
          /**FinComentado MOS*/
          
		   }//resultado null
        /**Agregado PMT**/   
      } //End For
        
        if(retArrayAfiliados != null)
        {
            registro1= "1"+ hoy+ fill(""+retArrayAfiliados.length,'0',5,true)+ formatNumber(tValor,17)+ oldNit_afpOrigen+ nit_afpDestino;
        }
        else
        {
                registro1= "1"+ hoy+ fill(""+0,'0',5,true)+ formatNumber(tValor,17)+ oldNit_afpOrigen+ nit_afpDestino;
        }
          
          /**Agregado MOS*/
              fileOutLog.print(registro1);
          /**Fin agregado MOS*/
          fileOutLog.close();
        
          /**Agregado MOS*/
            fileOutLog.close();
        
            if(archivoLog.exists())
            {
                  (new TBCL_ManageFile()).TBPL_inv(arTmpLog, arTmpLog+ "t");
                              archivoLog.delete();
            }
          /**Fin Agregado MOS*/
        
         /**Fin Agregado PMT**/
    }//try
    catch(SQLException sqle)
    {
      sqle.printStackTrace(System.out);
    }
    catch(IOException ioe)
    {
      System.out.print("");
    }
    return retArray;
  }

  /**
   * Regresa una lista de los traslados y sus datos asociados, de acuerdo con los par&aacute;metros<BR><BR>
   * @param tipo Tipo de consulta
   * @param params Par&aacute;metros de la consulta<BR><BR>
   * Si tipo es 1, params debe contener: Código de la AFP destino, fecha inicial y fecha final<BR>
   * Si tipo es 2, params debe contener: Cédula del afiliado, Código de la AFP destino, fecha inicial y fecha final<BR>
   * Si tipo es 3, params debe contener: Número de Contrato, Código del producto, Código de la AFP destino, fecha inicial y fecha final<BR>
   * Si tipo es 4, params debe contener: Cédula del afiliado, Número de Contrato, Código del producto, Código de la AFP destino, fecha inicial y fecha final<BR>
   * Si tipo es 5, params debe contener: NIT de la empresa, Código de la AFP destino, fecha inicial y fecha final<BR><BR>
   * @return java.util.LinkedList<BR>
   */
  private LinkedList TBFL_GetTraslados(int tipo, String[] params) throws SQLException {
    String query= "";

    switch(tipo) {
      case 1: //Básico.
        query= "SELECT SUBSTR(LPAD(REPLACE(pro_numero_identificacion,'.'),13,'0'),-11) "+
               "      ,SUBSTR(REPLACE(REPLACE(REPLACE(con_ref_tipo_identificacion,'UTI001','CC'),'UTI002','CE'),'UTI003','TI'),1,2) "+
               "      ,SUBSTR(LPAD(REPLACE(con_numero_identificacion,'.'),13,'0'),-11) "+
               "      ,RPAD(con_apellidos,20) "+
               "      ,RPAD(con_nombres,20) "+
               "      ,NVL(TO_CHAR(apo_fecha_aporte,'YYYYMMDD'),'00000000') "+
               "      ,SUBSTR(NVL(apo_emp_grupo,'A'), 1, 1) "+
               "      ,NVL(TO_CHAR(ret_fecha_proceso,'YYYYMMDD'),'00000000') "+
               "      ,NVL(SUM(apr_capital),0) CAPITAL "+
               "      ,TB_FCALCULAR_RENDIMIENTOS (APR_RET_CON_PRO_CODIGO, APR_RET_CON_NUMERO,APR_RET_CONSECUTIVO,APR_APO_CONSECUTIVO) RENDIMIENTOS     "+   //      ,NVL(SUM(apr_rendimientos),0) RENDIMIENTOS "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC002'),0)),0) RETENCION_CAPITAL "+  //               "      ,NVL(SUM(rCap.cae_valor),0) RETENCION_CAPITAL "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC004'),0)),0) PENALIZACION_CAPITAL "+  //                              "      ,NVL(SUM(pCap.cae_valor),0) PENALIZACION_CAPITAL "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC001'),0)),0) RETENCION_RENDIMINIENTOS "+  //  "      ,NVL(SUM(rRen.cae_valor),0) RETENCION_RENDIMINIENTOS "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC003'),0)),0) PENALIZACION_RENDIMINIENTOS "+ //   "      ,NVL(SUM(pRen.cae_valor),0) PENALIZACION_RENDIMINIENTOS "+
               "FROM   tbaportes "+
               "      ,tbaportes_retiros "+
               "      ,tbretiros "+
               "      ,tbproductos "+
               "      ,tbcontratos "+
               "WHERE  apo_con_pro_codigo              (+) = apr_ret_con_pro_codigo "+
               "  AND  apo_con_numero                  (+) = apr_ret_con_numero "+
               "  AND  apo_consecutivo                 (+) = apr_apo_consecutivo "+
               "  AND  apr_ret_con_pro_codigo          (+) = ret_con_pro_codigo "+
               "  AND  apr_ret_con_numero              (+) = ret_con_numero "+
               "  AND  apr_ret_consecutivo             (+) = ret_consecutivo "+
               "  AND  ret_con_pro_codigo                  = con_pro_codigo "+
               "  AND  ret_con_numero                      = con_numero "+
               "  AND  pro_codigo                          = con_pro_codigo "+
               "  AND  ret_ref_estado                      = 'SER006'      "+
               "  AND  ret_afp_codigo                      =  ? "+
               "  AND  ret_fecha_proceso BETWEEN TO_DATE(?, 'rrrr-mm-dd') AND TO_DATE(?, 'rrrr-mm-dd') "+
               "GROUP BY  pro_numero_identificacion "+
               "         ,con_ref_tipo_identificacion "+
               "         ,con_numero_identificacion "+
               "         ,con_apellidos "+
               "         ,con_nombres "+
               "         ,apo_fecha_aporte "+
               "         ,apo_emp_grupo "+
               "         ,ret_fecha_proceso "+
               "         , APR_RET_CON_PRO_CODIGO  "+
               "        ,APR_RET_CON_NUMERO  "+
               "        ,APR_APO_CONSECUTIVO  "+
               "        ,APR_RET_CONSECUTIVO  "+
               "ORDER BY  pro_numero_identificacion";
        break;
      case 2: //Cédula.
        query= "SELECT SUBSTR(LPAD(REPLACE(pro_numero_identificacion,'.'),13,'0'),-11) "+
               "      ,SUBSTR(REPLACE(REPLACE(REPLACE(con_ref_tipo_identificacion,'UTI001','CC'),'UTI002','CE'),'UTI003','TI'),1,2) "+
               "      ,SUBSTR(LPAD(REPLACE(con_numero_identificacion,'.'),13,'0'),-11) "+
               "      ,RPAD(con_apellidos,20) "+
               "      ,RPAD(con_nombres,20) "+
               "      ,NVL(TO_CHAR(apo_fecha_aporte,'YYYYMMDD'),'00000000') "+
               "      ,SUBSTR(NVL(apo_emp_grupo,'A'), 1, 1) "+
               "      ,NVL(TO_CHAR(ret_fecha_proceso,'YYYYMMDD'),'00000000') "+
               "      ,NVL(SUM(apr_capital),0) CAPITAL "+
               "      ,TB_FCALCULAR_RENDIMIENTOS (APR_RET_CON_PRO_CODIGO, APR_RET_CON_NUMERO,APR_RET_CONSECUTIVO,APR_APO_CONSECUTIVO) RENDIMIENTOS     "+   //      ,NVL(SUM(apr_rendimientos),0) RENDIMIENTOS "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC002'),0)),0) RETENCION_CAPITAL "+  //               "      ,NVL(SUM(rCap.cae_valor),0) RETENCION_CAPITAL "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC004'),0)),0) PENALIZACION_CAPITAL "+  //                              "      ,NVL(SUM(pCap.cae_valor),0) PENALIZACION_CAPITAL "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC001'),0)),0) RETENCION_RENDIMINIENTOS "+  //  "      ,NVL(SUM(rRen.cae_valor),0) RETENCION_RENDIMINIENTOS "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC003'),0)),0) PENALIZACION_RENDIMINIENTOS "+ //   "      ,NVL(SUM(pRen.cae_valor),0) PENALIZACION_RENDIMINIENTOS "+
               "FROM  tbaportes "+
               "      ,tbaportes_retiros "+
               "      ,tbretiros "+
               "      ,tbproductos "+
               "      ,tbcontratos "+
               "WHERE  apo_con_pro_codigo              (+) = apr_ret_con_pro_codigo "+
               "  AND  apo_con_numero                  (+) = apr_ret_con_numero "+
               "  AND  apo_consecutivo                 (+) = apr_apo_consecutivo "+
               "  AND  apr_ret_con_pro_codigo          (+) = ret_con_pro_codigo "+
               "  AND  apr_ret_con_numero              (+) = ret_con_numero "+
               "  AND  apr_ret_consecutivo             (+) = ret_consecutivo "+
               "  AND  ret_con_pro_codigo                  = con_pro_codigo "+
               "  AND  ret_con_numero                      = con_numero "+
               "  AND  pro_codigo                          = con_pro_codigo "+
               "  AND  ret_ref_estado                      = 'SER006'      "+
               "  AND  con_numero_identificacion           =  ? "+
               "  AND  ret_afp_codigo                      =  ? "+
               "  AND  ret_fecha_proceso BETWEEN TO_DATE(?, 'rrrr-mm-dd') AND TO_DATE(?, 'rrrr-mm-dd') "+
               "GROUP BY  pro_numero_identificacion "+
               "         ,con_ref_tipo_identificacion "+
               "         ,con_numero_identificacion "+
               "         ,con_apellidos "+
               "         ,con_nombres "+
               "         ,apo_fecha_aporte "+
               "         ,apo_emp_grupo "+
               "         ,ret_fecha_proceso "+
               "         , APR_RET_CON_PRO_CODIGO  "+
               "        ,APR_RET_CON_NUMERO  "+
               "        ,APR_APO_CONSECUTIVO  "+
               "        ,APR_RET_CONSECUTIVO  "+
               "ORDER BY  pro_numero_identificacion";
        break;
      case 3: //Producto y contrato.
        query= "SELECT SUBSTR(LPAD(REPLACE(pro_numero_identificacion,'.'),13,'0'),-11) "+
               "      ,SUBSTR(REPLACE(REPLACE(REPLACE(con_ref_tipo_identificacion,'UTI001','CC'),'UTI002','CE'),'UTI003','TI'),1,2) "+
               "      ,SUBSTR(LPAD(REPLACE(con_numero_identificacion,'.'),13,'0'),-11) "+
               "      ,RPAD(con_apellidos,20) "+
               "      ,RPAD(con_nombres,20) "+
               "      ,NVL(TO_CHAR(apo_fecha_aporte,'YYYYMMDD'),'00000000') "+
               "      ,SUBSTR(NVL(apo_emp_grupo,'A'), 1, 1) "+
               "      ,NVL(TO_CHAR(ret_fecha_proceso,'YYYYMMDD'),'00000000') "+
               "      ,NVL(SUM(apr_capital),0) CAPITAL "+
               "      ,TB_FCALCULAR_RENDIMIENTOS (APR_RET_CON_PRO_CODIGO, APR_RET_CON_NUMERO,APR_RET_CONSECUTIVO,APR_APO_CONSECUTIVO) RENDIMIENTOS     "+   //      ,NVL(SUM(apr_rendimientos),0) RENDIMIENTOS "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC002'),0)),0) RETENCION_CAPITAL "+  //               "      ,NVL(SUM(rCap.cae_valor),0) RETENCION_CAPITAL "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC004'),0)),0) PENALIZACION_CAPITAL "+  //                              "      ,NVL(SUM(pCap.cae_valor),0) PENALIZACION_CAPITAL "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC001'),0)),0) RETENCION_RENDIMINIENTOS "+  //  "      ,NVL(SUM(rRen.cae_valor),0) RETENCION_RENDIMINIENTOS "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC003'),0)),0) PENALIZACION_RENDIMINIENTOS "+ //   "      ,NVL(SUM(pRen.cae_valor),0) PENALIZACION_RENDIMINIENTOS "+
               "FROM  tbaportes "+
               "      ,tbaportes_retiros "+
               "      ,tbretiros "+
               "      ,tbproductos "+
               "      ,tbcontratos "+
               "WHERE  apo_con_pro_codigo              (+) = apr_ret_con_pro_codigo "+
               "  AND  apo_con_numero                  (+) = apr_ret_con_numero "+
               "  AND  apo_consecutivo                 (+) = apr_apo_consecutivo "+
               "  AND  apr_ret_con_pro_codigo          (+) = ret_con_pro_codigo "+
               "  AND  apr_ret_con_numero              (+) = ret_con_numero "+
               "  AND  apr_ret_consecutivo             (+) = ret_consecutivo "+
               "  AND  ret_con_pro_codigo                  = con_pro_codigo "+
               "  AND  ret_con_numero                      = con_numero "+
               "  AND  pro_codigo                          = con_pro_codigo "+
               "  AND  ret_ref_estado                      = 'SER006'      "+
               "  AND  ret_con_numero                      =  ? "+
               "  AND  ret_con_pro_codigo                  =  ? "+
               "  AND  ret_afp_codigo                      =  ? "+
               "  AND  ret_fecha_proceso BETWEEN TO_DATE(?, 'rrrr-mm-dd') AND TO_DATE(?, 'rrrr-mm-dd') "+
																					   
               "GROUP BY  pro_numero_identificacion "+
               "         ,con_ref_tipo_identificacion "+
               "         ,con_numero_identificacion "+
               "         ,con_apellidos "+
               "         ,con_nombres "+
               "         ,apo_fecha_aporte "+
               "         ,apo_emp_grupo "+
               "         ,ret_fecha_proceso "+
               "         , APR_RET_CON_PRO_CODIGO  "+
               "        ,APR_RET_CON_NUMERO  "+
               "        ,APR_APO_CONSECUTIVO  "+
               "        ,APR_RET_CONSECUTIVO  "+
               "ORDER BY  pro_numero_identificacion";
        break;
      case 4: //Producto, contrato y cédula.
        query= "SELECT SUBSTR(LPAD(REPLACE(pro_numero_identificacion,'.'),13,'0'),-11) "+
               "      ,SUBSTR(REPLACE(REPLACE(REPLACE(con_ref_tipo_identificacion,'UTI001','CC'),'UTI002','CE'),'UTI003','TI'),1,2) "+
               "      ,SUBSTR(LPAD(REPLACE(con_numero_identificacion,'.'),13,'0'),-11) "+
               "      ,RPAD(con_apellidos,20) "+
               "      ,RPAD(con_nombres,20) "+
               "      ,NVL(TO_CHAR(apo_fecha_aporte,'YYYYMMDD'),'00000000') "+
               "      ,SUBSTR(NVL(apo_emp_grupo,'A'), 1, 1) "+
               "      ,NVL(TO_CHAR(ret_fecha_proceso,'YYYYMMDD'),'00000000') "+
               "      ,NVL(SUM(apr_capital),0) CAPITAL "+
               "      ,TB_FCALCULAR_RENDIMIENTOS (APR_RET_CON_PRO_CODIGO, APR_RET_CON_NUMERO,APR_RET_CONSECUTIVO,APR_APO_CONSECUTIVO) RENDIMIENTOS     "+   //      ,NVL(SUM(apr_rendimientos),0) RENDIMIENTOS "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC002'),0)),0) RETENCION_CAPITAL "+  //               "      ,NVL(SUM(rCap.cae_valor),0) RETENCION_CAPITAL "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC004'),0)),0) PENALIZACION_CAPITAL "+  //                              "      ,NVL(SUM(pCap.cae_valor),0) PENALIZACION_CAPITAL "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC001'),0)),0) RETENCION_RENDIMINIENTOS "+  //  "      ,NVL(SUM(rRen.cae_valor),0) RETENCION_RENDIMINIENTOS "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC003'),0)),0) PENALIZACION_RENDIMINIENTOS "+ //   "      ,NVL(SUM(pRen.cae_valor),0) PENALIZACION_RENDIMINIENTOS "+
               "FROM  tbaportes "+
               "      ,tbaportes_retiros "+
               "      ,tbretiros "+
               "      ,tbproductos "+
               "      ,tbcontratos "+
               "WHERE  apo_con_pro_codigo              (+) = apr_ret_con_pro_codigo "+
               "  AND  apo_con_numero                  (+) = apr_ret_con_numero "+
               "  AND  apo_consecutivo                 (+) = apr_apo_consecutivo "+
               "  AND  apr_ret_con_pro_codigo          (+) = ret_con_pro_codigo "+
               "  AND  apr_ret_con_numero              (+) = ret_con_numero "+
               "  AND  apr_ret_consecutivo             (+) = ret_consecutivo "+
               "  AND  ret_con_pro_codigo                  = con_pro_codigo "+
               "  AND  ret_con_numero                      = con_numero "+
               "  AND  pro_codigo                          = con_pro_codigo "+
               "  AND  ret_ref_estado                      = 'SER006'      "+
               "  AND  con_numero_identificacion           =  ? "+
               "  AND  ret_con_numero                      =  ? "+
               "  AND  ret_con_pro_codigo                  =  ? "+
               "  AND  ret_afp_codigo                      =  ? "+
               "  AND  ret_fecha_proceso BETWEEN TO_DATE(?, 'rrrr-mm-dd') AND TO_DATE(?, 'rrrr-mm-dd') "+
               "GROUP BY  pro_numero_identificacion "+
               "         ,con_ref_tipo_identificacion "+
               "         ,con_numero_identificacion "+
               "         ,con_apellidos "+
               "         ,con_nombres "+
               "         ,apo_fecha_aporte "+
               "         ,apo_emp_grupo "+
               "         ,ret_fecha_proceso "+
               "         , APR_RET_CON_PRO_CODIGO  "+
               "        ,APR_RET_CON_NUMERO  "+
               "        ,APR_APO_CONSECUTIVO  "+
               "        ,APR_RET_CONSECUTIVO  "+
               "ORDER BY  pro_numero_identificacion";
        break;
      case 5: //Producto y NIT.
        query= "SELECT SUBSTR(LPAD(REPLACE(pro_numero_identificacion,'.'),13,'0'),-11) "+
               "      ,SUBSTR(REPLACE(REPLACE(REPLACE(con_ref_tipo_identificacion,'UTI001','CC'),'UTI002','CE'),'UTI003','TI'),1,2) "+
               "      ,SUBSTR(LPAD(REPLACE(con_numero_identificacion,'.'),13,'0'),-11) "+
               "      ,RPAD(con_apellidos,20) "+
               "      ,RPAD(con_nombres,20) "+
               "      ,NVL(TO_CHAR(apo_fecha_aporte,'YYYYMMDD'),'00000000') "+
               "      ,SUBSTR(NVL(apo_emp_grupo,'A'), 1, 1) "+
               "      ,NVL(TO_CHAR(ret_fecha_proceso,'YYYYMMDD'),'00000000') "+
               "      ,NVL(SUM(apr_capital),0) CAPITAL "+
               "      ,TB_FCALCULAR_RENDIMIENTOS (APR_RET_CON_PRO_CODIGO, APR_RET_CON_NUMERO,APR_RET_CONSECUTIVO,APR_APO_CONSECUTIVO) RENDIMIENTOS     "+   //      ,NVL(SUM(apr_rendimientos),0) RENDIMIENTOS "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC002'),0)),0) RETENCION_CAPITAL "+  //               "      ,NVL(SUM(rCap.cae_valor),0) RETENCION_CAPITAL "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC004'),0)),0) PENALIZACION_CAPITAL "+  //                              "      ,NVL(SUM(pCap.cae_valor),0) PENALIZACION_CAPITAL "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC001'),0)),0) RETENCION_RENDIMINIENTOS "+  //  "      ,NVL(SUM(rRen.cae_valor),0) RETENCION_RENDIMINIENTOS "+
               "      ,nvl(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC003'),0)),0) PENALIZACION_RENDIMINIENTOS "+ //   "      ,NVL(SUM(pRen.cae_valor),0) PENALIZACION_RENDIMINIENTOS "+
               "FROM   tbaportes "+
               "      ,tbaportes_retiros "+
               "      ,tbretiros "+
               "      ,tbproductos "+
               "      ,tbempresas "+
               "      ,tbempresas_contratos "+
               "      ,tbcontratos "+
               "WHERE   apo_con_pro_codigo             (+) = apr_ret_con_pro_codigo "+
               "  AND  apo_con_numero                  (+) = apr_ret_con_numero "+
               "  AND  apo_consecutivo                 (+) = apr_apo_consecutivo "+
               "  AND  apr_ret_con_pro_codigo          (+) = ret_con_pro_codigo "+
               "  AND  apr_ret_con_numero              (+) = ret_con_numero "+
               "  AND  apr_ret_consecutivo             (+) = ret_consecutivo "+
               "  AND  ret_con_pro_codigo                  = con_pro_codigo "+
               "  AND  ret_con_numero                      = con_numero "+
               "  AND  con_pro_codigo                      = emc_con_pro_codigo "+
               "  AND  con_numero                          = emc_con_numero "+
               "  AND  emc_emp_grupo                       = emp_grupo "+
               "  AND  emp_nit                             = ? "+
               "  AND  pro_codigo                          = con_pro_codigo "+
               "  AND  ret_ref_estado                      = 'SER006'      "+
               "  AND  ret_con_pro_codigo                  = ? "+
               "  AND  ret_afp_codigo                      = ? "+
               "  AND  ret_fecha_proceso BETWEEN TO_DATE(?, 'rrrr-mm-dd') AND TO_DATE(?, 'rrrr-mm-dd') "+
               "GROUP BY  pro_numero_identificacion "+
               "         ,con_ref_tipo_identificacion "+
               "         ,con_numero_identificacion "+
               "         ,con_apellidos "+
               "         ,con_nombres "+
               "         ,apo_fecha_aporte "+
               "         ,apo_emp_grupo "+
               "         ,ret_fecha_proceso "+
               "         , APR_RET_CON_PRO_CODIGO  "+
               "        ,APR_RET_CON_NUMERO  "+
               "        ,APR_APO_CONSECUTIVO  "+
               "        ,APR_RET_CONSECUTIVO  "+
               "ORDER BY  pro_numero_identificacion";
        break;
      default:
        break;
    }
    return TBFL_Consulta(query, params);
  }
  /*********************************************************************************************/
  /**
   * Regresa una lista de los traslados y sus datos asociados, de acuerdo con los par&aacute;metros<BR><BR>
   * @param tipo Tipo de consulta
   * @param params Par&aacute;metros de la consulta<BR><BR>
   * Si tipo es 1, params debe contener: Código de la AFP destino, fecha inicial y fecha final<BR>
   * Si tipo es 2, params debe contener: Cédula del afiliado, Código de la AFP destino, fecha inicial y fecha final<BR>
   * Si tipo es 3, params debe contener: Número de Contrato, Código del producto, Código de la AFP destino, fecha inicial y fecha final<BR>
   * Si tipo es 4, params debe contener: Cédula del afiliado, Número de Contrato, Código del producto, Código de la AFP destino, fecha inicial y fecha final<BR>
   * Si tipo es 5, params debe contener: NIT de la empresa, Código de la AFP destino, fecha inicial y fecha final<BR><BR>
   * @return java.util.LinkedList<BR>
   */
  private LinkedList TBFL_GetTraslados_TotRen(int tipo , String[] params2, String documento) throws SQLException {
    String query2= "";
    String v_afp  = "";
    String v_fecini = "";
    String v_fecfin = "";
       query2= "SELECT  SUBSTR(NVL(apo_emp_grupo,'A'), 1, 1) "+
               "       ,TB_FCALCULAR_RENDIMIENTOS (APR_RET_CON_PRO_CODIGO, APR_RET_CON_NUMERO,APR_RET_CONSECUTIVO,APR_APO_CONSECUTIVO) RENDIMIENTOS     "+
    /**Agregado MOS*/
               "       ,ret_transaccion  "+
               "       ,NVL(SUM(apr_capital),0) CAPITAL   "+
               "       ,NVL(SUM(NVL(TBFBD_CARGOS_RETIROS(APR_RET_CON_PRO_CODIGO,  APR_RET_CON_NUMERO, APR_RET_consecutivo, APR_APO_CONSECUTIVO, 'STC004'),0)),0) PENALIZACION_CAPITAL "+
               "       ,APR_RET_CON_NUMERO "+
    /**Fin agregado MOS*/
               "FROM   tbaportes "+
               "       , tbaportes_retiros "+
               "      ,tbretiros "+
               "      ,tbproductos "+
               "      ,tbcontratos "+
               "WHERE  apo_con_pro_codigo              (+) = apr_ret_con_pro_codigo "+
               "  AND  apo_con_numero                  (+) = apr_ret_con_numero "+
               "  AND  apo_consecutivo                 (+) = apr_apo_consecutivo "+
               "  and  apr_ret_con_pro_codigo           = ret_con_pro_codigo "+
               "  AND  apr_ret_con_numero               = ret_con_numero "+
               "  AND  apr_ret_consecutivo              = ret_consecutivo "+
               "  AND  ret_con_pro_codigo                  = con_pro_codigo "+
               "  AND  ret_con_numero                      = con_numero "+
               "  AND  pro_codigo                          = con_pro_codigo "+
               "  AND  ret_ref_estado                      = 'SER006'      "+
               "  AND  con_numero_identificacion           =  ? "+
               "  AND  ret_afp_codigo                      =  ? "+
               "  AND  ret_fecha_proceso BETWEEN TO_DATE(?, 'rrrr-mm-dd') AND TO_DATE(?, 'rrrr-mm-dd') "+
               "GROUP BY  apo_emp_grupo  "+
               "          ,APR_RET_CON_PRO_CODIGO  "+
               "        ,APR_RET_CON_NUMERO  "+
               "        ,APR_APO_CONSECUTIVO  "+
               "        ,APR_RET_CONSECUTIVO  "+
               /**Agregado MOS*/
               "        ,ret_transaccion ";
               /**Fin agregado MOS*/

    if(tipo == 1)
    {
        v_afp = params2[0];
        v_fecini = params2[1];
        v_fecfin = params2[2];
    }
    else if (tipo == 2)
         {
           v_afp = params2[1];
           v_fecini = params2[2];
           v_fecfin = params2[3];
         }
        else if (tipo == 3)
             {
               v_afp = params2[2];
               v_fecini = params2[3];
               v_fecfin = params2[4];
             }
             else if (tipo == 4)
                 {
                   v_afp = params2[3];
                   v_fecini = params2[4];
                   v_fecfin = params2[5];
                 }
                else if (tipo == 5)
                    {
                      v_afp = params2[2];
                      v_fecini = params2[3];
                      v_fecfin = params2[4];
                    }


    return TBFL_Consulta2(query2, v_afp,documento,v_fecini,v_fecfin);
  }
  /********************************************************************************************/
  /**
   * Ejecuta una consulta con los par&aacute;metros definidos<BR>
   * @param query Consulta que desea hacerse
   * @param params Par&aacute;metros de la consulta
   * @return java.util.LinkedList
   */
  private LinkedList TBFL_Consulta(String query, String[] params) throws SQLException {
    LinkedList retList= new LinkedList();

    Connection con= null;
    PreparedStatement ps= null;
    ResultSet rs= null;
    boolean empty = true;

    try {
        con   =   DataSourceWrapper.getInstance().getConnection();
      ps= con.prepareStatement(query);
      for(int i= 0; i< params.length; i++) {
        ps.setString(i+1,params[i]);
      }
														 
														 
									
	   
																				 
								 
		 
		
		

      rs= ps.executeQuery();
      while(rs.next()) {
        empty = false;
        int count= rs.getMetaData().getColumnCount();
        String[] record= new String[count];
        for(int i= 0; i< count; i++) {
          record[i]= rs.getString(i+1);
        }

        record[8]= Double.toString(Double.parseDouble(record[8]) - Double.parseDouble(record[11]));
        double ren= Double.parseDouble(record[9]) - Double.parseDouble(record[13]);
        record[9]=  Double.toString(ren);
        record[11]= record[10];
        record[10]= (ren< 0.0)?"-":"+";
        record[12]= "";
        record[13]= "";

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
    if (empty) retList = null;

    return retList;
  }
  /*****************************************************************************************/
  private LinkedList TBFL_Consulta2(String query, String v_afp,String documento,String v_fecini, String v_fecfin) throws SQLException
  {
    LinkedList retList= new LinkedList();

    Connection con= null;
    PreparedStatement ps= null;
    ResultSet rs= null;
    boolean empty = true;

    try {
      con   =   DataSourceWrapper.getInstance().getConnection();
      ps= con.prepareStatement(query);
      ps.setString(1,documento);
      ps.setString(2,v_afp);
      ps.setString(3,v_fecini);
      ps.setString(4,v_fecfin);
      rs= ps.executeQuery();
      while(rs.next()) {
        empty = false;
        int count= rs.getMetaData().getColumnCount();
        String[] record= new String[count];
        for(int i= 0; i< count; i++) {
          record[i]= rs.getString(i+1);
        }
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
    if (empty) retList = null;

    return retList;
  }

    /** Agregado PMT**/

    /**
          * Traer los contratos y productos relacionados en una fecha<BR>
          * @param afp_codigo C&oacue;digo de la AFP
          * @return java.lang.String
          */
      private LinkedList TBFL_Consulta3(String[] params) throws SQLException
      {
          String query=""; 
          LinkedList retList= new LinkedList();

          Connection con= null;
          PreparedStatement ps= null;
          ResultSet rs= null;
          boolean empty = true;

          query = " select r.ret_con_pro_codigo, r.ret_con_numero from tbretiros r" + 
                  " where ret_ref_estado = 'SER006'    " + 
                  " and  ret_afp_codigo = ? " + 
                  " and ret_fecha_proceso " + 
                  " BETWEEN TO_DATE( ? , 'rrrr-mm-dd') AND TO_DATE( ? , 'rrrr-mm-dd') " + 
                  " group by r.ret_con_pro_codigo, r.ret_con_numero";

          try {
              con   =   DataSourceWrapper.getInstance().getConnection();
            ps= con.prepareStatement(query);
            for(int i= 0; i< params.length; i++) {
              ps.setString(i+1,params[i]);
            }

            rs= ps.executeQuery();
            while(rs.next()) {
              empty = false;
              int count= rs.getMetaData().getColumnCount();
              String[] record= new String[count];
              for(int i= 0; i< count; i++) {
                record[i]= rs.getString(i+1);
              }

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
          if (empty) retList = null;

          return retList;
      }
    /** Agregado PMT**/

/**
   * Regresa la Descripci&oacute;n de la AFP<BR>
   * @param afp_codigo C&oacue;digo de la AFP
   * @return java.lang.String
   */
  private String getDescAFP(String afp_codigo) throws SQLException {
    String retString= "";

    Connection con= null;
    PreparedStatement ps= null;
    ResultSet rs= null;

    String query= "SELECT AFP_DESCRIPCION "+
                  "FROM TBADM_FONDOS_PENSIONES "+
                  "WHERE AFP_CODIGO = ?";

    try {
        con   =   DataSourceWrapper.getInstance().getConnection();
      ps= con.prepareStatement(query);
      ps.setString(1, afp_codigo);

      rs= ps.executeQuery();
      if(rs.next()) {
        retString = rs.getString(1);
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
    return (retString==null)?"0":retString;
  }
  

  /**
   * Regresa si es necesario hacer un redondeo del traslado<BR>
   * Creado 2009-11-03
   * @param transaccion C&oacue;digo de la transacción del traslado
   * @param contrato  N&uacue;mero del contrato del traslado
   * @return java.lang.String
   */
  private String getRedondeo(String transaccion, String contrato) throws SQLException {
    String retString= "N";

    Connection con= null;
    CallableStatement csPdto= null;
    CallableStatement cs2   = null;
    String sistema  ="";
    String usuario  ="";
    String password ="";
    String libreria ="";
    String v_retorno ="";
      
    try {
        con   =   DataSourceWrapper.getInstance().getConnection();
      /*Establecer los datos de conexion a AS400 */
      csPdto =  con.prepareCall("BEGIN :1 := TB_FREFERENCIAS_MULTI( :2 ,\n :3,\n :4 ,\n :5)\n; END;");
      csPdto.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      csPdto.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
      /* setear parametro de entrada IN */
      csPdto.setString(2,sistema);
      csPdto.setString(3,usuario);
      csPdto.setString(4,password);
      csPdto.setString(5,libreria);
      csPdto.execute();

      /* recupera parametros de salida*/
      sistema = csPdto.getString(2);
      usuario = csPdto.getString(3);
      password = csPdto.getString(4);
      libreria = csPdto.getString(5);
        
      cs2 = con.prepareCall ( "{? = call TBCL_FuncionesAs400.TBPL_TipoOperacionTraslado(?,?,?,?,?,?)}");
      cs2.registerOutParameter(1,Types.CHAR);
      cs2.setString(2,contrato);
      cs2.setString(3,transaccion);
      cs2.setString(4,sistema);
      cs2.setString(5,usuario);
      cs2.setString(6,password);
      cs2.setString(7,libreria);
      cs2.executeUpdate();
      v_retorno = cs2.getString(1);
      
      if (v_retorno.equals("ACH"))
      {
        retString ="Y";
      }
      
    }
    finally{
      try {
            DataSourceWrapper.closeStatement(csPdto);
            DataSourceWrapper.closeStatement(cs2);
            DataSourceWrapper.closeConnection(con);
      }
      catch(SQLException sqle) {
      }
    }
    return (retString==null)?"0":retString;
  }

  /**
   * Regresa la ruta para generar el archivo<BR>
   * @return java.lang.String
   */
  private void TBPL_GetRutas() throws SQLException {
    Connection con= null;
    PreparedStatement ps= null;
    ResultSet rs= null;

    try {
      con   =   DataSourceWrapper.getInstance().getConnection();

      String query= "SELECT  REF_VALOR  "+
                  "FROM   TBREFERENCIAS "+
                  "WHERE  REF_CODIGO = 'STE001'";

      ps= con.prepareStatement(query);

      rs= ps.executeQuery();
      if(rs.next()) {
        rutaFisica = rs.getString(1);
      }

      query= "SELECT  REF_VALOR  "+
                  "FROM   TBREFERENCIAS "+
                  "WHERE  REF_CODIGO = 'STE002'";
      ps= con.prepareStatement(query);
      rs= ps.executeQuery();
      if(rs.next()) {
        rutaVirtual = rs.getString(1);
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
  }

  /**
   * Inserta el caracter especificado en la cadena dada, hasta llenar las posiciones indicadas<BR>
   * @param cadena La cadena de entrada
   * @param caracter El caracter con el cual se quiere llenar la cadena
   * @param longitud La longitud deseada de la cadena de regreso
   * @param derecha true para justificar a la derecha, false para justificar a la izquierda
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
   * Da formato al n&uacute;mero pasado como par&aacute;metro para su inserci&oacute;n en los registros del archivo de salida<BR>
   * @param numero El n&uacute;mero de entrada
   * @param longitud La longitud deseada de la cadena de regreso
   * @return java.lang.String
   */
  private String formatNumber(double numero, int longitud) {

    BigDecimal big= new BigDecimal(numero);
    big= big.setScale(2, BigDecimal.ROUND_HALF_UP);

    StringBuffer sb= new StringBuffer();
    StringTokenizer st= new StringTokenizer(big.toString(), ".-");

    while(st.hasMoreTokens()) sb.append(st.nextToken());

    for(int i= sb.length(); i< longitud; i++ )
      sb.insert(0,'0');

    return sb.toString();
  }

    /**
   * Da formato a la cadena pasada como par&aacute;metro para su inserci&oacute;n en los registros del archivo de salida<BR>
   * @param cadena La cadena de entrada
   * @param longitud La longitud deseada de la cadena de regreso
   * @return java.lang.String
   */
  private String formatString(String cadena, int longitud) {
    if(cadena == null) return "                    ";
    if(cadena.length()> longitud) return cadena.substring(0,longitud);

    StringBuffer sb= new StringBuffer(cadena);

    for(int i= sb.length(); i< longitud; i++ )
      sb.append(' ');

    return sb.toString();
  }

  /**
   * Regresa informaci&oacute;n sobre el Servlet<BR>
   * @return java.lang.String
   */
  public String getServletInfo() {
    return "TBPKT_INFORMATIVO.TBPKT_INFORMACION_TRASLADOS.TBCS_MI_InformacionTraslados Information";
  }
}

