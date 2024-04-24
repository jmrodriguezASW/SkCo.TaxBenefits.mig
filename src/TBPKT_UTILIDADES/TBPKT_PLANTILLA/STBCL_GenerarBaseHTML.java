
// Copyright (c) 2000 SKANDIA
package TBPKT_UTILIDADES.TBPKT_PLANTILLA;

/**
 * A Class class.
 * <P>
 * @author Henry, Johana, Herbert
 */
public class STBCL_GenerarBaseHTML extends Object {

  public static String TBFL_CABEZA(String titulo,String subtitulo,String servlet,String mensaje,boolean estado,String nombre_js,String onsubmit)
  {
  //este m?todo surve para dibujar la parte est?tica de pipeline, la variable estado define el tipo
 //de p?gina a dibufar, si el estado es verdadero dibujar? la cebecera para un servlet y
//si es false dibujar? la cabecera para un mensaje(confirmaci?n, error, etc.)
  if(estado)
  return(   "<html> \n"+
            "<head>"+
            "<SCRIPT LANGUAGE = 'JAVASCRIPT' SRC = 'filesjs/"+nombre_js+"'> ALERT('EL ARCHIVO JS NO EXISTE'); </SCRIPT> \n"+
            //"<SCRIPT LANGUAGE = 'JAVASCRIPT' SRC = 'https://10.42.1.151/Servlets/filesjs/modulo_retiros.js'> ALERT('EL ARCHIVO JS NO EXISTE'); </SCRIPT> \n"+
            "<link rel=\"stylesheet\" href=\"filescss/style.css\" /> \n"+
            "<title>"+titulo+"</title> \n"+
            "<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'> \n"+
            "<meta name='description' content='Fireworks Splice HTML'> \n"+
            "</head> \n"+
            "<body> \n"+
            "<FORM onSubmit='"+onsubmit+"' METHOD=POST NAME=v_codigo ACTION=/Servlets/"+servlet+"> \n"+
            "<table class=\"table\"> \n"+
            "<tr valign='top' align='center'><td><br> \n"+
            "<table class=\"table2\" cellpadding='0' cellspacing='0'> \n"+
            "<tr> \n"+
            "<td class=\"td1\"><img src='imagenes/shim.gif' width='23' height='1' border='0'></td> \n"+
            "<td class=\"td2\"><img src='imagenes/shim.gif' width='116' height='1' border='0'></td> \n"+
            "<td class=\"td3\"><img src='imagenes/shim.gif' width='22' height='1' border='0'></td> \n"+
            "<td class=\"td4\"><img src='imagenes/shim.gif' width='1' height='1' border='0'></td></tr> \n"+
            "<tr class=\"tr1\"><!-- row 1 --> \n"+
            "<td class=\"td1\">&nbsp;</td> \n"+
            "<td class=\"td5\">&nbsp;</td> \n"+
            "<td class=\"td3\">&nbsp;</td> \n"+
            "<td class=\"td4\">&nbsp;</td></tr> \n"+
            "<tr class=\"tr1\"><!-- row 2 --> \n"+
            "<td class=\"td6\">&nbsp;</td> \n"+
            "<td class=\"td7\"> <!-- INSERTAR CONTENIDO DESDE AQUI --> \n"+
            "<TABLE class=\"table3\"> \n"+
            "<TBODY><TR><TD><span class=\"subtitulo\">"+subtitulo+"</span></TD></TR></TBODY></TABLE> \n");
  else
    return( "<html> \n"+
            "<head> \n"+            
            "<link rel=\"stylesheet\" href=\"filescss/style.css\" /> \n"+
            "<title>"+titulo+"</title> \n"+
            "<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'> \n"+
            "<meta name='description' content='Fireworks Splice HTML'> \n"+
            "</head> \n"+
            "<body> \n"+
            "<FORM METHOD=POST NAME=v_codigo ACTION=/Servlets/"+servlet+"> \n"+
            "<table class=\"table\"> \n"+
            "<tr valign='top' align='center'><td><br> \n"+
            "<table class=\"table2\" cellpadding='0' cellspacing='0'> \n"+
            "<tr> \n"+
            "<td class=\"td1\"><img src='imagenes/shim.gif' width='23' height='1' border='0'></td> \n"+
            "<td class=\"td2\"><img src='imagenes/shim.gif' width='116' height='1' border='0'></td> \n"+
            "<td class=\"td3\"><img src='imagenes/shim.gif' width='22' height='1' border='0'></td> \n"+
            "<td class=\"td4\"><img src='imagenes/shim.gif' width='1' height='1' border='0'></td></tr> \n"+
            "<tr class=\"tr1\"><!-- row 1 --> \n"+
            "<td class=\"td1\">&nbsp;</td> \n"+
            "<td class=\"td5\">&nbsp;</td> \n"+
            "<td class=\"td3\">&nbsp;</td> \n"+
            "<td class=\"td4\">&nbsp;</td></tr> \n"+
            "<tr class=\"tr1\"><!-- row 2 --> \n"+
            "<td class=\"td6\">&nbsp;</td> \n"+
            "<td class=\"td7\"> <!-- INSERTAR CONTENIDO DESDE AQUI --> \n"+
            "<TABLE class=\"table3\"> \n"+
            "<TBODY><TR><TD><span class=\"subtitulo\">"+subtitulo+"</span></TD></TR></TBODY></TABLE> \n"+
            "<p class=\"mensaje\">"+mensaje+"</p> \n");
  }

  public static String TBFL_CABEZA(String titulo,String subtitulo,String servlet,String mensaje,boolean estado)
  {
  //este m?todo surve para dibujar la parte est?tica de pipeline, la variable estado define el tipo
 //de p?gina a dibufar, si el estado es verdadero dibujar? la cebecera para un servlet y
//si es false dibujar? la cabecera para un mensaje(confirmaci?n, error, etc.)
  if(estado)
  return(   "<html> \n"+
            "<head>"+
            "<link rel=\"stylesheet\" href=\"filescss/style.css\" /> \n"+
            "<title>"+titulo+"</title> \n"+
            "<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'> \n"+
            "<meta name='description' content='Fireworks Splice HTML'> \n"+
            "</head> \n"+
            "<body> \n"+
            "<FORM  METHOD=POST NAME=v_codigo ACTION=/Servlets/"+servlet+"> \n"+
            "<table class=\"table\"> \n"+
            "<tr valign='top' align='center'><td><br> \n"+
            "<table class=\"table2\" cellpadding='0' cellspacing='0'> \n"+
            "<tr> \n"+
            "<td class=\"td1\"><img src='imagenes/shim.gif' width='23' height='1' border='0'></td> \n"+
            "<td class=\"td2\"><img src='imagenes/shim.gif' width='116' height='1' border='0'></td> \n"+
            "<td class=\"td3\"><img src='imagenes/shim.gif' width='22' height='1' border='0'></td> \n"+
            "<td class=\"td4\"><img src='imagenes/shim.gif' width='1' height='1' border='0'></td></tr> \n"+
            "<tr class=\"tr1\"><!-- row 1 --> \n"+
            "<td class=\"td1\">&nbsp;</td> \n"+
            "<td class=\"td5\">&nbsp;</td> \n"+
            "<td class=\"td3\">&nbsp;</td> \n"+
            "<td class=\"td4\">&nbsp;</td></tr> \n"+
            "<tr class=\"tr1\"><!-- row 2 --> \n"+
            "<td class=\"td6\">&nbsp;</td> \n"+
            "<td class=\"td7\"> <!-- INSERTAR CONTENIDO DESDE AQUI --> \n"+
            "<TABLE class=\"table3\"> \n"+
            "<TBODY><TR><TD><span class=\"subtitulo\">"+subtitulo+"</span></TD></TR></TBODY></TABLE> \n");
  else
    return( "<html> \n"+
            "<head> \n"+
            "<link rel=\"stylesheet\" href=\"filescss/style.css\" /> \n"+
            "<title>"+titulo+"</title> \n"+
            "<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'> \n"+
            "<meta name='description' content='Fireworks Splice HTML'> \n"+
            "</head> \n"+
            "<body> \n"+
            "<FORM METHOD=POST NAME=v_codigo ACTION=/Servlets/"+servlet+"> \n"+
            "<table class=\"table\"> \n"+
            "<tr valign='top' align='center'><td><br> \n"+
            "<table class=\"table2\" cellpadding='0' cellspacing='0'> \n"+
            "<tr> \n"+
            "<td class=\"td1\"><img src='imagenes/shim.gif' width='23' height='1' border='0'></td> \n"+
            "<td class=\"td2\"><img src='imagenes/shim.gif' width='116' height='1' border='0'></td> \n"+
            "<td class=\"td3\"><img src='imagenes/shim.gif' width='22' height='1' border='0'></td> \n"+
            "<td class=\"td4\"><img src='imagenes/shim.gif' width='1' height='1' border='0'></td></tr> \n"+
            "<tr class=\"tr1\"><!-- row 1 --> \n"+
            "<td class=\"td1\">&nbsp;</td> \n"+
            "<td class=\"td5\">&nbsp;</td> \n"+
            "<td class=\"td3\">&nbsp;</td> \n"+
            "<td class=\"td4\">&nbsp;</td></tr> \n"+
            "<tr class=\"tr1\"><!-- row 2 --> \n"+
            "<td class=\"td6\">&nbsp;</td> \n"+
            "<td class=\"td7\"> <!-- INSERTAR CONTENIDO DESDE AQUI --> \n"+
            "<TABLE class=\"table3\"> \n"+
            "<TBODY><TR><TD><span class=\"subtitulo\">"+subtitulo+"</span></TD></TR></TBODY></TABLE> \n"+
            "<p class=\"mensaje\">"+mensaje+"</p> \n");
  }
  
    public static String TBFL_CABEZA(String titulo,String subtitulo, String mensaje,String nombre_js)
    {
    return(   "<html> \n"+
              "<head>"+
              "<SCRIPT LANGUAGE = 'JAVASCRIPT' SRC = 'filesjs/"+nombre_js+"'> ALERT('EL ARCHIVO JS NO EXISTE'); </SCRIPT> \n"+              
              "<SCRIPT LANGUAGE = 'JAVASCRIPT' SRC = 'filesjs/jquery-ui.js'> ALERT('EL ARCHIVO JS NO EXISTE'); </SCRIPT> \n"+              
              "<SCRIPT LANGUAGE = 'JAVASCRIPT' SRC = 'filesjs/jquery-3.2.1.js'> ALERT('EL ARCHIVO JS NO EXISTE'); </SCRIPT> \n"+              
              "<link rel=\"stylesheet\" href=\"filescss/jquery-ui.css\" /> \n"+
              "<link rel=\"stylesheet\" href=\"filescss/style.css\" /> \n"+
              "<script> \n" +
              " $( function() { \n" +
              " $( #datepicker ).datepicker(); \n" +
              "} );  \n" +
              "</script> \n" +
              "<title>"+titulo+"</title> \n"+
              "<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'> \n"+
              "<meta name='description' content='Fireworks Splice HTML'> \n"+
              "</head> \n"+
              "<body onload=\"nobackbutton();\"> \n"+
              "<table class=\"table\"> \n"+
              "<tr valign='top' align='center'><td><br> \n"+
              "<table class=\"table2\" cellpadding='0' cellspacing='0'> \n"+
              "<tr> \n"+
              "<td class=\"td1\"><img src='imagenes/shim.gif' width='23' height='1' border='0'></td> \n"+
              "<td class=\"td2\"><img src='imagenes/shim.gif' width='116' height='1' border='0'></td> \n"+
              "<td class=\"td3\"><img src='imagenes/shim.gif' width='22' height='1' border='0'></td> \n"+
              "<td class=\"td4\"><img src='imagenes/shim.gif' width='1' height='1' border='0'></td></tr> \n"+
              "<tr class=\"tr1\"><!-- row 1 --> \n"+
              "<td class=\"td1\">&nbsp;</td> \n"+
              "<td class=\"td5\">&nbsp;</td> \n"+
              "<td class=\"td3\">&nbsp;</td> \n"+
              "<td class=\"td4\">&nbsp;</td></tr> \n"+
              "<tr class=\"tr1\"><!-- row 2 --> \n"+
              "<td class=\"td6\">&nbsp;</td> \n"+
              "<td class=\"td7\"> <!-- INSERTAR CONTENIDO DESDE AQUI --> \n"+
              "<TABLE class=\"table3\"> \n"+
              "<TBODY><TR><TD><span class=\"subtitulo\">"+subtitulo+"</span></TD></TR></TBODY></TABLE> \n");
    
    }
  
  
    public static String TBFL_DISPONIBLE_OBLIG(String STAVA, String STAVE, 
                                               String APONOCERAVA, String APONOCERAVE, 
                                               String APOHISPAVA, String APOHISPAVE, 
                                               String RETPENAVA, String RETPENAVE, 
                                               String RECONTAVA, String RECONTAVE, 
                                               String RERENDAVA, String RERENDAVE, 
                                               String COMADMAVA, String COMADMAVE,
                                               String DISPAVA, String DISPAVE, 
                                               String v_prov_comi_total_AVA, String v_prov_comi_total_AVE,
                                               String s_prov_credito, String s_credito)
    {

         return "<html>\n" + 
                     "<table  border='2' BORDERCOLOR=SILVER  style=\"border-collapse: collapse; font-size: 8pt; font-family: verdana;\" bordercolor=\"#111111\" width=\"100%\">\n" + 
                     "<tr>\n" + 
                     "  <td width=\"46%\" class=\"td11\"><center><font color='#FFFFFF' ><b>Concepto</b></font></center></td>\n" + 
                     "  <td width=\"27%\" class=\"td11\"><center><font color='#FFFFFF' ><b>Saldo AVA</b></font></center></td>\n" + 
                     "  <td width=\"27%\" class=\"td11\"><center><font color='#FFFFFF' ><b>Saldo AVE</b></font></center></td>\n" + 
                     "</tr>\n" + 
                     "<tr>\n" + 
                     "  <td>(+) Saldo Total</td>\n" + 
                     "  <td align=\"right\">"+STAVA+"</td>\n" + 
                     "  <td align=\"right\">"+STAVE+"</td>\n" + 
                     "</tr>\n" + 
                     "<tr>\n" + 
                     "  <td> (-) Aportes No Certificados</td>\n" + 
                     "  <td align=\"right\">"+APONOCERAVA+"</td>\n" + 
                     "  <td align=\"right\">"+APONOCERAVE+"</td>\n" + 
                     "</tr>\n" + 
                     "<tr>\n" + 
                     "  <td> (-) Aportes con Historia Pendiente</td>\n" + 
                     "  <td align=\"right\">"+APOHISPAVA+"</td>\n" + 
                     "  <td align=\"right\">"+APOHISPAVE+"</td>\n" + 
                     "</tr>\n" + 
                     "<tr>\n" + 
                     "  <td> (-) Retiros Pendientes</td>\n" + 
                     "  <td align=\"right\">"+RETPENAVA+"</td>\n" + 
                     "  <td align=\"right\">"+RETPENAVE+"</td>\n" + 
                     "</tr>\n" + 
                     "<tr>\n" + 
                     "  <td> (-) Retencion Contingente</td>\n" + 
                     "  <td align=\"right\">"+RECONTAVA+"</td>\n" + 
                     "  <td align=\"right\">"+RECONTAVE+"</td>\n" + 
                     "</tr>\n" + 
                     "<tr>\n" + 
                     "  <td> (-) Retencion Sobre Rendimientos</td>\n" + 
                     "  <td align=\"right\">"+RERENDAVA+"</td>\n" + 
                     "  <td align=\"right\">"+RERENDAVE+"</td>\n" + 
                     "</tr>\n" + 
                     "<tr>\n" + 
                     "  <td> (-)  Provisión crédito saldo disponible</td>\n" + 
                     "  <td align=\"right\">"+s_prov_credito+"</td>\n" + 
                     "  <td align=\"right\"></td>\n" + 
                     "</tr>\n" + 
                     "<tr>\n" + 
                     "  <td> (-)  Provisión comisión saldo disponible</td>\n" + 
                     "  <td align=\"right\">"+v_prov_comi_total_AVA+"</td>\n" + 
                     "  <td align=\"right\">"+v_prov_comi_total_AVE+"</td>\n" + 
                     "</tr>\n" + 
                     "<tr>\n" + 
                     "  <td>Créditos Vigentes</td>\n" + 
                     "  <td align=\"right\">"+s_credito+"</td>\n" + 
                     "  <td align=\"right\"></td>\n" + 
                     "</tr>\n" + 
                     "<tr>\n" + 
                     "  <td><FONT color=gray> Comisión Administración</FONT></td>\n" + 
                     "  <td align=\"right\"><FONT color=gray>"+COMADMAVA+"</FONT></td>\n" + 
                     "  <td align=\"right\"><FONT color=gray>"+COMADMAVE+"</FONT></td>\n" + 
                     "</tr>\n" + 
                     "<tr>\n" + 
                     "  <td class=\"td11\"><font color='#FFFFFF' ><b>= Saldo Disponible</b></font></td>\n" + 
                     "  <td align=\"right\" class=\"td11\"><b><font color='#FFFFFF' >"+DISPAVA+"</b></font></td>\n" + 
                     "  <td align=\"right\" class=\"td11\"><b><font color='#FFFFFF' >"+DISPAVE+"</b></font></td>\n" + 
                     "</tr>\n" + 
                     "</table><br><br>";

    }
    
    public static String TBFL_CABEZA(String titulo,String subtitulo,String servlet,String mensaje,String nombre_js,String onsubmit)
    {
        String ru = "\\";
    return(   "<html> \n"+
              "<head>"+
              "<SCRIPT LANGUAGE = 'JAVASCRIPT' SRC = 'filesjs/"+nombre_js+"'> ALERT('EL ARCHIVO JS NO EXISTE'); </SCRIPT> \n"+
              "<link rel=\"stylesheet\" href=\"filescss/style.css\" /> \n"+
              "<head> \n" +
              "<script type=\"text/javascript\"> \n" +
                  "var datefield=document.createElement(\"input\") \n" +
                  "datefield.setAttribute(\"type\", \"date\") \n" +
                  "if (datefield.type!=\"date\"){ \n" +
                      "document.write('<link href=\"http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css\" rel=\"stylesheet\" type=\"text/css\" />')\n" +
                      "document.write('<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js\"><\\/script>')\n" +
                      "document.write('<script src=\"http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js\"><\\/script>') \n" +
                  "} \n" +
              "</script> \n" +               
              "<script> \n" +
              "if (datefield.type!='date'){  \n" +
                  "jQuery(function($){ \n" +
                      "$('#datepicker').datepicker({ dateFormat: 'yy-mm-dd' }); \n" +
                  "}) \n" +
              "} \n" +
              "</script> \n" +              
              "<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'> \n"+
              "<meta name='description' content='Fireworks Splice HTML'> \n"+
              "</head> \n"+
              "<body> \n"+
              "<FORM onSubmit='"+onsubmit+"' METHOD=POST NAME=v_codigo ACTION=/Servlets/"+servlet+"> \n"+
              "<table class=\"table\"> \n"+
              "<tr valign='top' align='center'><td><br> \n"+
              "<table class=\"table2\" cellpadding='0' cellspacing='0'> \n"+
              "<tr> \n"+
              "<td class=\"td1\"><img src='imagenes/shim.gif' width='23' height='1' border='0'></td> \n"+
              "<td class=\"td2\"><img src='imagenes/shim.gif' width='116' height='1' border='0'></td> \n"+
              "<td class=\"td3\"><img src='imagenes/shim.gif' width='22' height='1' border='0'></td> \n"+
              "<td class=\"td4\"><img src='imagenes/shim.gif' width='1' height='1' border='0'></td></tr> \n"+
              "<tr class=\"tr1\"><!-- row 1 --> \n"+
              "<td class=\"td1\">&nbsp;</td> \n"+
              "<td class=\"td5\">&nbsp;</td> \n"+
              "<td class=\"td3\">&nbsp;</td> \n"+
              "<td class=\"td4\">&nbsp;</td></tr> \n"+
              "<tr class=\"tr1\"><!-- row 2 --> \n"+
              "<td class=\"td6\">&nbsp;</td> \n"+
              "<td class=\"td7\"> <!-- INSERTAR CONTENIDO DESDE AQUI --> \n"+
              "<TABLE class=\"table3\"> \n"+
              "<TBODY><TR><TD><span class=\"subtitulo\">"+subtitulo+"</span></TD></TR></TBODY></TABLE> \n");    
    }
       
 public static final String TBFL_PIE =
            "<td class=\"td6\">&nbsp;</td> \n"+
            "<td class=\"td4\">&nbsp;</td></tr> \n"+
            "<tr class=\"tr1\"><!-- row 3 --> \n"+
            "<td class=\"td9\">&nbsp;</td> \n"+
            "<td class=\"td10\">&nbsp;</td> \n"+
            "<td class=\"td9\">&nbsp;</td> \n"+
            "<td class=\"td4\">&nbsp;</td></tr> \n"+
            "<br></td></tr> \n"+
            "</table> \n"+
            "</table></form></body></html> \n";

}




