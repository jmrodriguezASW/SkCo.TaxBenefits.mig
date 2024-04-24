
// Copyright (c) 2000 SKANDIA
package TBPKT_UTILIDADES.TBPKT_PLANTILLA;

/**
 * A Class class.
 * <P>
 * @author Henry, Johana, Herbert
 */
public class GTBCL_GenerarBaseHTML extends Object {

  public static String TBFL_CABEZA(String titulo,String subtitulo,String servlet,String mensaje,boolean estado,String nombre_js,String onsubmit)
  {
  //este m?todo surve para dibujar la parte est?tica de pipeline, la variable estado define el tipo
 //de p?gina a dibufar, si el estado es verdadero dibujar? la cebecera para un servlet y
//si es false dibujar? la cabecera para un mensaje(confirmaci?n, error, etc.)
  if(estado)
  return(   "<html> \n"+
            "<head>"+
            "<SCRIPT LANGUAGE = 'JAVASCRIPT' SRC = 'filesjs/"+nombre_js+"'> ALERT('EL ARCHIVO JS NO EXISTE'); </SCRIPT> \n"+
            "<link rel=\"stylesheet\" href=\"filescss/style.css\" /> \n"+
            "<title>"+titulo+"</title> \n"+
            "<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'> \n"+
            "<meta name='description' content='Fireworks Splice HTML'> \n"+
            "</head> \n"+
            "<body> \n"+
            "<FORM onSubmit='"+onsubmit+"' METHOD=GET NAME=codigo ACTION=/Servlets/"+servlet+"> \n"+
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
            "<FORM METHOD=GET NAME=codigo ACTION=/Servlets/"+servlet+"> \n"+
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
            "<p class=\"mensaje\">"+mensaje+"</p>\n");
  }

  public static String TBFL_CABEZA(String titulo,String subtitulo,String servlet,String mensaje,boolean estado)
  {
  //este m?todo surve para dibujar la parte est?tica de pipeline, la variable estado define el tipo
 //de p?gina a dibufar, si el estado es verdadero dibujar? la cebecera para un servlet y
//si es false dibujar? la cabecera para un mensaje(confirmaci?n, error, etc.)
  if(estado)
  return(   "<html> \n"+
            "<head>"+
            "<title>"+titulo+"</title> \n"+
            "<link rel=\"stylesheet\" href=\"filescss/style.css\" /> \n"+
            "<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'> \n"+
            "<meta name='description' content='Fireworks Splice HTML'> \n"+
            "</head> \n"+
            "<body> \n"+
            "<FORM  METHOD=GET NAME=codigo ACTION=/Servlets/"+servlet+"> \n"+
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
            "<FORM METHOD=GET NAME=codigo ACTION=/Servlets/"+servlet+"> \n"+
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



 public static final String TBFL_PIE =
            "<td class=\"td8\">&nbsp;</td> \n"+
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


