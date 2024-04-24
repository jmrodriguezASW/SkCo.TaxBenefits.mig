
package TBPKT_MODULO_APORTES;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;
import java.text.*;
import java.sql.*;
import java.io.*;
import java.text.NumberFormat;

/**
*CLASE QUE CONTIENE TODOS LOS PROCEDIMIENTOS QUE DIBUJAN ALGO EN EL BROWSER
*/

public class TBCL_HTML extends Object
{
   static TBCL_Consulta v_Consulta;
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
*este procedimiento sirve para dibujar la primer hoja de todas
*las peticiones de actualización desde PIPELINE
* <p>
* @param    titulo      = MENSJAE A DESPLEGAR<BR>
*           producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           respuesta   = OBJETO PRINTWRITER<BR>
*           valor       = BANDERA DE ACCION<BR>
*           clase       = NOMBRE DE LA CLASE A DIBUJAR<BR>
*           c           =  OBJETO CONEXION<BR>
*           usuario     = USUARIO QUE REALIZARA LA MODIFICACION<BR>
*           nombres     = INFORMACION DEL DUEÑO DEL CONTRATO<BR><BR>
*           nuevaCadena     =  CADENA DE ENCRIPCION<BR>
* @return  NINGUNO
*/
public static void TBPL_Hoja_Base(String titulo,String producto,String contrato
                            ,PrintWriter respuesta,int valor,String clase
                            ,Connection c,String usuario,String nombres
                            ,String nuevaCadena)
 {

  STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
  String url = "Onclick=history.go(-1);";
  String fechas[] = new String [500];
  fechas          = TBPL_Fechas_por_Contrato(producto,contrato,c);

  //el vector fechas contendrá las fechas validas para el contrato y el producto enviado por pipeline
    //primtro valido de acuerdo estado del valor el llamado a la cabecera, luego validaré campos adicioneles por
   //estado de valor
   if(valor==0)
   {//hoja base para actualizacion de disponibilidad
    respuesta.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                       "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_DISPONIBILIDAD","",true));
   }
   else if(valor==1)
        {//hoja base para actualizacion de beneficio
          respuesta.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZA EXENTOS",
                                                "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_BENEFICIO","",true));
          respuesta.println("<center><input type='hidden' name='usuario' value='"+usuario+"'></center>");

        }
        else if(valor==3)
            {//hola base para actualizacion de certificacion de aportes
             respuesta.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE APORTES",
                                                  "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_CCONTINGENTE","",true));
             respuesta.println("<center><input type='hidden' name='usuario' value='"+usuario+"'></center>");
            }
   respuesta.println("<center><table border='0' align='center' width='80%'>");
   /*Cambio para manejo de referencia unica 2009/04/01 MOS */
   String v_contrato_unif = "";
   v_Consulta= new TBCL_Consulta();
   v_Consulta.TBPL_RealizarConexion();
   v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(producto,contrato);
   v_Consulta.TBPL_shutDown();
    
   respuesta.println("<tr><td align='center'><font face='Verdana' size='1'><strong>Producto: "+producto+"&nbsp;&nbsp;Contrato: "+v_contrato_unif+"</strong></font></td></tr>");
   respuesta.println("<tr><td align='center'><font face='Verdana' size='1'><strong>"+nombres.toUpperCase()+"</strong></font></td></tr>");
   respuesta.println("</table></center>");
   //dibujo cuerpo de la hoja base
   respuesta.println("<p><font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><font size='1'></font></b><font size='1' color='#000000'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Búsqueda entre fechas efectivas de los aportes del contrato:</font></font></p>");
   respuesta.println("<p><font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><font size='1'></font></b><font size='1' color='#000000'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fecha Incial  :</font></font>");
   respuesta.println("<SELECT name=fecha_1>");int a = fechas.length;
   int i=fechas.length;
   while(i!=0)
   {
    try
    {
     if(!fechas[i].equals(null))
     respuesta.println("<OPTION>"+fechas[i]);
     i--;
    }
    catch(Exception ex){i--;}
   }
   respuesta.println("<OPTION>"+fechas[0]);
   respuesta.println("</SELECT></p>");
   respuesta.println("<p><font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><font size='1'></font></b><font size='1' color='#000000'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fecha Final  :        </font></font>");
   respuesta.println("<SELECT name=fecha_2>");
   for(i=0;i<fechas.length;i++)
   {
    try
    {
     if(!fechas[i].equals(null))
       respuesta.println("<OPTION>"+fechas[i]);
    }
    catch(Exception ex){i=fechas.length;}
   }
   respuesta.println("</SELECT></p>");
   //JAG se adiciona valor ==0 para que pinte check
   if(valor==3 || valor==0)
   {
    respuesta.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox' name='tipo_select' value='1'><FONT color=BLACK face=verdana size=1>Seleccionar todos los Aportes</font><BR><BR>");
   }
   //campos ocultos de producto y contrato
   respuesta.println("<center><input type='hidden' name='nom_producto' value='x'></center>");
   respuesta.println("<center><input type='hidden' name='num_contrato' value='x'></center>");
   //campos ocultos y ficticios que me mantendrán los valores de  producto y contrato
   respuesta.println("<center><input type='hidden' name='f_producto' value='"+producto+"'></center>");
   respuesta.println("<center><input type='hidden' name='f_contrato' value='"+contrato+"'></center>");
   respuesta.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
   respuesta.println("<center><input type='submit' value='Aceptar'>"+
                  "<input type='button' value='Cancelar' "+url+"></center>");
   respuesta.println(plantilla.TBFL_PIE);
   respuesta.close();

 }
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA CONSULTAR LA INFORMACION
* BASICA DEL DUEÑO DE UN PRODUCTO-CONTRATO
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
* @return   INFORMACION DEL CLIENTE
*/
public static String TBPL_Nombres(String producto,String contrato)
{
 String nombres             = "¡ -nombres y apellidos errados- !";
 try
 {
  TBCL_Validacion  i_valusu  = new     TBCL_Validacion ();
  String[] v_valusu          = new String[3];
  v_valusu                   = i_valusu.TBFL_ValidarUsuario();
  Class.forName("oracle.jdbc.driver.OracleDriver");
  Connection v_conexion_taxb  = DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
  String select8i_1 = "SELECT CON_NOMBRES,CON_APELLIDOS "+
                     "FROM TBCONTRATOS "+
                     "WHERE "+
                     "CON_PRO_CODIGO= ? "+
                     "AND LPAD(CON_NUMERO, 9, '0')= ? ";
  PreparedStatement t_st8i = v_conexion_taxb.prepareStatement(select8i_1);
  t_st8i.setString(1,producto);
  t_st8i.setString(2,contrato);
  ResultSet t_rs8i;
  t_rs8i = t_st8i.executeQuery();
  if(t_rs8i.next())
   {
    nombres = t_rs8i.getString(1)+" "+t_rs8i.getString(2);
    t_rs8i.close();
    t_st8i.close();
    v_conexion_taxb.close();
    return nombres;
   }
  else
   {
    v_conexion_taxb.close();
    return nombres;
   }
 }
 catch (Exception ex){System.out.println("  ");return "SE PRODUJO UN ERORR EN LA BUSQUEDA DE LA INFORMACION DEL CONTRATO(Nombres Y Apellidos)";}
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA DIBUJAR CUALQUIER TIPO DE ERROR OCURRIDO
* DURANTE EL PROCESO DE ACTRUALIZACION DE APORTES
* <p>
* @param   producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*          contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*          respuesta   = OBJETO PRINTWRITER<BR>
*          valor       = BANDERA DE ACCION<BR>
*          clase       = NOMBRE DE LA CLASE A DIBUJAR<BR>
*          titulo      = MENSAJE<BR>
* @return  NINGUNO
*/
public static void TBPL_Hoja_Error(String producto,String contrato,PrintWriter respuesta,int valor,String clase,String titulo)
//este procedimiento sirve para generar la hoja de respuesta para cuando ocurra un en error a traves del proceso
{

  STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
  respuesta.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES",titulo,
                                                "PKT_ACTUALIZA_APORTES."+clase,"",true));
  respuesta.println("<p><font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><font size='1'></font></b><font size='1' color='#000000'>  SE HA PRODUCIDO UN ERROR A LA HORA DE PROCESAR LA INFORMACION</font></font></p>");
  respuesta.println("<p><font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><font size='1'></font></b><font size='1' color='#000000'>  LOS DATOS ENVIADOS SON: </font></font></p>");
  respuesta.println("<p><font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><font size='1'></font></b><font size='1' color='#000000'>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NOMBRE PRODUCTO: "+producto+"</font></font></p>");
  respuesta.println("<p><font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><font size='1'></font></b><font size='1' color='#000000'>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NUMERO CONTRATO: "+contrato+"</font></font></p>");
  respuesta.println("<p><font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><font size='1'></font></b><font size='1' color='#000000'>  POR FAVOR VERIFIQUE LA INFORMACION E INTENTE MAS TARDE</font></font></p>");
  respuesta.println("<br><br>");
  respuesta.println("<center><input type='button' value='Aceptar' Onclick=history.go(-2); ></center>");
  respuesta.println(plantilla.TBFL_PIE);
  respuesta.flush();
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA
* <p>
* @param   <BR>
* @return  NINGUNO
*/
public static void TBPL_Publica_E(int total_aportes,String[] fechas,double[] saldo_c,double[] saldo_r,double[] saldo_cc,
                             double[] consecutivos,String usuario,String producto,String contrato,PrintWriter respuesta,String nuevaCadena)

{
STBCL_GenerarBaseHTMLII plantilla = new STBCL_GenerarBaseHTMLII();
/*String url = "Onclick=history.go('http://afscotb1.skandia.com.co/taxbenefit/actualizar_aportes.html');"+
             "history.go('http://afscotb1.skandia.com.co/taxbenefit/ACTUALIZAR_APORTES.HTML');"+
             "history.go('http://AFSCOTB1.SKANDIA.COM.CO/TAXBENEFIT/ACTUALIZAR_APORTES.HTML');";*/
if(total_aportes>0)
 {
  respuesta.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZA EXENTOS",
                                                "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_BENEFICIO","",
                                                true,"moduloparametro.js","return checkrequired(this)"));
  DecimalFormat formato = new DecimalFormat("###,###,###,###,###,###.##");
  respuesta.println("<BR>");
  respuesta.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=5 rules=all width='100%'>"+
  "<TR class=\"td11\" borderColor=silver>"+
  "<TH width='5%'><FONT color=white face=verdana size=1>&nbsp;</font></TH>"+
  "<TH width='25%'><FONT color=white face=verdana size=1>Fecha    </font></TH>"+
  "<TH width='25%'><FONT color=white face=verdana size=1>Saldo Capital  </font></TH>"+
  "<TH width='25%'><FONT color=white face=verdana size=1>Saldo Rendimientos</font></TH>"+
  "<TH width='20%'><FONT color=white face=verdana size=1>Saldo Cuenta Contingente</font></TH></TR>");
  String box  = " ";
  int i       = 0;
  for(i=0;i<total_aportes;i++)
  {
    if(i==0) box = "<TD width='5'><FONT color=black face=verdana size=1><input type='checkbox' name='checks' value='"+consecutivos[i]+"' CHECKED>"+"</font></TD>";
    else     box = "<TD width='5'><FONT color=black face=verdana size=1><input type='checkbox' name='checks' value='"+consecutivos[i]+"'        >"+"</font></TD>";
    respuesta.println(
    "<TR>"+box+
    "<TD width='33%'><FONT color=black face=verdana size=1>"+fechas[i]+"</font></TD>"+
    "<TD width='25%'><FONT color=black face=verdana size=1>$"+formato.format(saldo_c[i])+"</font></TD>"+
    "<TD width='25%'><FONT color=black face=verdana size=1>$"+formato.format(saldo_r[i])+"</font></TD>"+
    "<TD width='25%'><FONT color=black face=verdana size=1>$"+formato.format(saldo_cc[i])+"</font></TD>"+
    "</TR>");
  }
  respuesta.println("</TABLE>");
  respuesta.println("<FONT color=black face=verdana size=1>*RAZON:</font>");
  respuesta.println("<TEXTAREA NAME='obligatorio_razon' COLS='60' ROWS='2' ></TEXTAREA>");
  //oculto valores necesarios para el proceso de updete en el doget
  respuesta.println("<input type='hidden' name='total_aportes' value='"+total_aportes+"'>");
  //oculto usuario,producto y contrato
  respuesta.println("<center><input type='hidden' name='usuario' value='"+usuario+"'></center>");
  respuesta.println("<center><input type='hidden' name='f_producto' value='"+producto+"'></center>");
  respuesta.println("<center><input type='hidden' name='f_contrato' value='"+contrato+"'></center>");
  //preparo valores para ejecutar esta tarea en el doget
  respuesta.println("<center><input type='hidden' name='nom_producto' value='y'></center>");
  respuesta.println("<center><input type='hidden' name='num_contrato' value='y'></center>");
  respuesta.println("<BR>");
  respuesta.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
  respuesta.println("<center><input type='submit' value='Aceptar'>"+
  "<input type='button' value='Cancelar' Onclick=history.go(-1);></center>");
  respuesta.println(plantilla.TBFL_PIE);
  respuesta.flush();
 }//fin aporte>0
else if(total_aportes==0)
 {
  String msj  = "No hay elementos selccionados para su transaccion por las siguientes razones: ";
  String msj1 = "* Rango de fechas inválido.";
  String msj2 = "* Los aportes seleccionados estan exentos";
  respuesta.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZACION DE APORTES EXENTOS","",msj,false));
  respuesta.println("<br>");
  respuesta.println("<FONT color=black face=verdana size=1>"+msj1+"</font>");
  respuesta.println("<br>");
  respuesta.println("<FONT color=black face=verdana size=1>"+msj2+"</font>");
  respuesta.println("<br><br>");
  respuesta.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1); ></center>");
  respuesta.println(plantilla.TBFL_PIE);
  respuesta.flush();
 }
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* este procedimiento verificará la veracidad del producto y del contrato
* enviados por pipeline COMO PARAMETROS PARA CADA SERVLET
* <p>
* @param    producto    = PRODCUTO A REVISAR<BR>
*           contrato    = CONTRATO A REVISAR<BR>
* @return   BOOLEANO
*/
public static boolean TBPL_Parametros_Requeridos(String producto,String contrato)

 {
try
{

 TBCL_Validacion  i_valusu  = new     TBCL_Validacion ();
  String[] v_valusu          = new String[3];
  v_valusu                   = i_valusu.TBFL_ValidarUsuario();
 Class.forName("oracle.jdbc.driver.OracleDriver");
 Connection v_conexion_taxb  = DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);

 String select8i_1 = "SELECT CON_PRO_CODIGO,CON_NUMERO "+
                     "FROM TBCONTRATOS "+
                     "WHERE "+
                     "CON_PRO_CODIGO= ? "+
                     "AND LPAD(CON_NUMERO, 9, '0') = ? ";
 PreparedStatement t_st8i = v_conexion_taxb.prepareStatement(select8i_1);
 t_st8i.setString(1,producto);
 t_st8i.setString(2,contrato);
 ResultSet t_rs8i;
 t_rs8i = t_st8i.executeQuery();
 if(t_rs8i.next())
 {

   t_rs8i.close();
   t_st8i.close();
   return true;
 }
 else return false;
}
catch (Exception ex){System.out.println(" ");return false;}
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA MOSTRAR EN EL BROWSER
* LA INFORMACION DE APORTES VALIDOS PERTENECIENTES A UN CONTRATO
* Y A LOS CUALES SE LE PUEDE MODIFICAR SU INFORMACION EN EL
* SUBMODULO DE ACTUALIZACION DE APORTES
* <p>
@param      producto      = PRODUCTO AL CUAL PERTENECE EL APORTE A MODIFICAR<BR>
*           contrato      = CONTRATO AL CUAL PERTENECE EL APORTE A MODIFICAR<BR>
*           usuario       = USUARIO QUE REALIZARA LA MODIFICACION<BR>
*           certificado   = VECTOR DE INDICADORES DE CERTIFICACION<BR><BR>
*           cc            = VECTOR DE CUENTA CONTINGENTE CON LA CUAL CUENTA EL APORTE ANTES DE SER MODIFICADO<BR>
*           c             = VECTOR DE CAPITALES CON EL CUAL CUENTA EL APORTE ANTES DE SER MODIFICADO<BR>
*           rtos          = VECTOR DE RENDIMIENTOS CON LOS CUALES CUENTA EL APORTE ANTES DE SER MODIFICADO<BR>
*           f_a           = VECTOR DE FECHAS DEL APORTE A MODIFICAR<BR>
*           total_aportes = TOTAL DE APORTES A MOSTRAR
*           salida        = OBJETO PRINTWRITER<BR>
*           consecutivo   = VECTOR DE CONSECUTIVO DEL APORTE A MODIFICAR<BR>
*           f_E           = VECTOR DE FECHAS EFECTIVAS DEL APORTE A MODIFICAR<BR>
*           apo_beneficio = VECTOR DE INDICADORES DE BENEFICIO ANTES DE REALIZARLE LA MODIFICACION<BR>
*           nuevaCadena   = CADENA DE ENCRIPCION ENVIADA POR PIPELINE<BR>
* @return  NINGUNO
*/
public static void TBPL_Datos_Ccontingente(String producto,String contrato,String usuario,String[] certificado
                                           ,String[] cc,String[] c,String[] rtos,String[] f_a,int total_aportes
                                           ,PrintWriter respuesta,int[] consecutivos,String[] f_e,
                                            String[] apo_beneficio,String nuevaCadena)
{
STBCL_GenerarBaseHTMLII plantilla = new STBCL_GenerarBaseHTMLII();
/*String url = "Onclick=history.go('http://afscotb1.skandia.com.co/taxbenefit/actualizar_aportes.html');"+
             "history.go('http://afscotb1.skandia.com.co/taxbenefit/ACTUALIZAR_APORTES.HTML');"+
             "history.go('http://AFSCOTB1.SKANDIA.COM.CO/TAXBENEFIT/ACTUALIZAR_APORTES.HTML');";*/
String url = "Onclick=history.go('http://taxbenefit.skandia.com.co:81/taxbenefit/actualizar_aportes.html');"+
             "history.go('http://taxbenefit.skandia.com.co:81/taxbenefit/ACTUALIZAR_APORTES.HTML');"+
             "history.go('http://TAXBENEFIT.SKANDIA.COM.CO:81/TAXBENEFIT/ACTUALIZAR_APORTES.HTML');";
DecimalFormat formato = new DecimalFormat("###,###,###,###,###,###.##");
String radio      = " ";
boolean hay_datos = false;
respuesta.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE APORTES",
                                              "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_CCONTINGENTE"," ",true));
respuesta.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=7 rules=all width='100%'>"+
"<TR class=\"td11\" borderColor=silver>"+
"<TH width='5%'><FONT color=white face=verdana size=1>&nbsp;</font></TH>"+
"<TH width='15%'><FONT color=white face=verdana size=1>Fecha Efectiva</font></TH>"+
"<TH width='15%'><FONT color=white face=verdana size=1>Fecha Aporte</font></TH>"+
"<TH width='15%'><FONT color=white face=verdana size=1>Certficado</font></TH>"+
"<TH width='15%'><FONT color=white face=verdana size=1>&nbsp;Capital</font></TH>"+
"<TH width='15%'><FONT color=white face=verdana size=1>Rendimientos</font></TH>"+
"<TH width='16%'><FONT color=white face=verdana size=1>Cuenta Contingente</font></TH>"+
"</TR>");
  for(int i=0;i<total_aportes;i++)
  {
    Double cp     = new Double(c[i]);
    Double cco    = new Double(cc[i]);
    Double rt     = new Double(rtos[i]);
    //respuesta.println("Imprimio sub " + i + "<br>valor de total_aportes " + total_aportes);
    if(i==0) radio = "<TR bgColor=white borderColor=silver><TD width='5%'><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=fila CHECKED VALUE='"+f_a[i]+"$"+certificado[i]+"$"+c[i]+"$"+rtos[i]+"$"+cc[i]+"*"+apo_beneficio[i]+"@"+consecutivos[i]+"'></font></TD>";
    else     radio = "<TR bgColor=white borderColor=silver><TD width='5%'><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=fila         VALUE='"+f_a[i]+"$"+certificado[i]+"$"+c[i]+"$"+rtos[i]+"$"+cc[i]+"*"+apo_beneficio[i]+"@"+consecutivos[i]+"'></font></TD>";
    respuesta.println(radio+
    "<TD width='15%'><FONT color=black face=verdana size=1>"+f_e[i]+"</font></TD>"+
    "<TD width='15%'><FONT color=black face=verdana size=1>"+f_a[i]+"</font></TD>"+
    "<TD width='15%'><FONT color=black face=verdana size=1>"+certificado[i]+"</font></TD>"+
    "<TD width='15%'><FONT color=black face=verdana size=1>$"+formato.format(cp)+"</font></TD>"+
    "<TD width='15%'><FONT color=black face=verdana size=1>$"+formato.format(rt)+"</font></TD>"+
    "<TD width='16%'><FONT color=black face=verdana size=1>$"+formato.format(cco)+"</font></TD></TR>");
    hay_datos = true;
  }
  
  respuesta.println("</TABLE>");
  //oculto producto-contrato
  respuesta.println("<input type='hidden' name='f_producto' value='"+producto+"'>");
  respuesta.println("<input type='hidden' name='f_contrato' value='"+contrato+"'>");
  respuesta.println("<input type='hidden' name='usuario' value='"+usuario+"'>");
  //campos ocultos de producto y contrato que me dicen cual hoja dibujar
  respuesta.println("<input type='hidden' name='nom_producto' value='y'>");
  respuesta.println("<input type='hidden' name='num_contrato' value='y'>");
  if(hay_datos)
  {
    respuesta.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
    respuesta.println("<center><input type='submit' value='Aceptar'>"+
                    "<input type='button' value='Cancelar' Onclick=history.go(-1);></center>");
  }
  else{
  respuesta.println("<font face='Verdana, Arial, Helvetica, sans-serif' color='#324395'><b><font size='1'></font></b><font size='1' color='#000000'>NO HAY DATOS PARA TU SOLICITUD</font></font>");
  respuesta.println("<center><input type='button' value='Aceptar'  Onclick=history.go(-2); ></center>");
      }
  respuesta.println(plantilla.TBFL_PIE);
  respuesta.close();

}

//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA MOSTRAR EN EL BROWSER LA INFORMACION DE
* UN APORTE ELEGIDO EN EL SUBMODULO DE APORTE INTERNOS QUE SE RESULTA INVALIDO
* <p>
* @param   respuesta  = OBJETO PRINTWRITER<br>
*          msj        = MENSAJE A MOSTRAR<BR>
* @return  NINGUNO
*/
public static void TBPL_Aporte_Invalido(PrintWriter respuesta,String msj)
{
 STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
 respuesta.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS INTERNOS DE APORTES",
                                                "",msj,false));
 respuesta.println("<br><br>");
 respuesta.println("<center><input type='button' value='Aceptar'   Onclick=history.go(-1); ></center>");
 respuesta.println(plantilla.TBFL_PIE);
 respuesta.flush();
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA DIBUJAR LA INFORMACION DE UN APORTE LA
* CUAL SE LE MODIFICARÁ EN EL SUBMODULO DE ACTUALIZACIOND E INFORMACIOND E APORTES
* <p>
* @param    producto      = PRODUCTO AL CUAL PERTENECE EL APORTE A MODIFICAR<BR>
*           contrato      = CONTRATO AL CUAL PERTENECE EL APORTE A MODIFICAR<BR>
*           usuario       = USUARIO QUE REALIZARA LA MODIFICACION<BR>
*           salida        = OBJETO PRINTWRITER<BR>
*           f_a           = FECHA DEL APORTE A MODIFICAR<BR>
*           certificado   = INDICADOR DE CERTIFICACION<BR><BR>
*           k             = CAPITAL CON EL CUAL CUENTA EL APORTE ANTES DE SER MODIFICADO<BR>
*           cc            = CUENTA CONTINGENTE CON LA CUAL CUENTA EL APORTE ANTES DE SER MODIFICADO<BR>
*           rtos          = RENDIMIENTOS CON LOS CUALES CUENTA EL APORTE ANTES DE SER MODIFICADO<BR>
*           consecutivo   = CONSECUTIVO DEL APORTE A MODIFICAR<BR>
*           apo_beneficio = INDICADOR DE BEENFICIO ANTES DE REALIZARLE LA MODIFICACION<BR>
*           tiene_retiros = INDICADOR DE TENENCIA O NO DE RETIROS<BR>
*           nuevaCadena   = CADENA DE ENCRIPCION ENVIADA POR PIPELINE<BR>
* @return   NINGUNO
*/
public static void TBPL_Aporte_Elegido(String producto,String contrato,String usuario,PrintWriter salida
                                  ,String f_a,String certificado,String k,String cc,String rtos
                                  ,String consecutivo,String apo_beneficio,String tiene_retiros
                                  ,String nuevaCadena)
{
STBCL_GenerarBaseHTMLII plantilla = new STBCL_GenerarBaseHTMLII();
String opcional = " ";
if(certificado.equals("S"))opcional = "N";
else opcional = "S";
salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE APORTES",
                                              "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_CCONTINGENTE",
                                              " ",true,"moduloparametro.js","return checkrequired(this)"));
if(tiene_retiros.equals("v"))
salida.println("<FONT color=BLACK face=verdana size=1><strong>ESTE APORTE PRESENTA RETIROS ASOCIADOS. EN CASO DE ACEPTAR LA ACTUALIZACION SE GENERARA UN AJUSTE. PARA ELEGIR OTRO APORTE SELECCIONE CANCELAR.</strong></font>");
salida.println("<BR><BR>");
salida.println("<input type='hidden' name='f_ao'          value='"+f_a+"'>");
salida.println("<input type='hidden' name='certificado_o' value='"+certificado+"'>");
salida.println("<input type='hidden' name='capital_o'     value='"+k+"'>");
salida.println("<input type='hidden' name='cc_o'          value='"+cc+"'>");
salida.println("<input type='hidden' name='rto_o'         value='"+rtos+"'>");
salida.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=5 rules=all width='100%'>"+
"<TR class=\"td11\" borderColor=silver>"+
"<TH width='70'><FONT color=white face=verdana size=1>Fecha Aporte</font></TH>"+
"<TH width='15%'><FONT color=white face=verdana size=1>Certificado&nbsp;&nbsp;</font></TH>"+
"<TH width='15%'><FONT color=white face=verdana size=1>Capital</font></TH>"+
"<TH width='15%'><FONT color=white face=verdana size=1>Rendimientos</font></TH>"+
"<TH width='15%'><FONT color=white face=verdana size=1>Cuenta Contingente</font></TH>"+
"</TR>");
salida.println(
"<TR><TD width='70'><FONT color=black face=verdana size=1><input type='text' name='obligatoriofecha'     value='"+f_a+"' size='10' ></font></TD>"+
"<TD width='15%'><FONT color=black face=verdana size=1><SELECT name=certificado><OPTION>"+certificado+"<OPTION>"+opcional+"</SELECT></font></TD>"+
"<TD width='15%'><FONT color=black face=verdana size=1><input type='text' name='capital' value='"+k+"'    size='10' onBlur='if (esNumero(this)==1) return false;esNegativo(this)'></font></TD>"+
"<TD width='15%'><FONT color=black face=verdana size=1><input type='text' name='rto'     value='"+rtos+"' size='10' onBlur='if (esNumero(this)==1) return false;esNegativo(this)'></font></TD>"+
"<TD width='15%'><FONT color=black face=verdana size=1><input type='text' name='cc'      value='"+cc+"'   size='10' onBlur='if (esNumero(this)==1) return false;esNegativo(this)'></font></TD></TR>");
salida.println("</TABLE>");
salida.println("<FONT color=black face=verdana size=1>*RAZON:</font>");
salida.println("<TEXTAREA NAME='obligatorio_razon' COLS='60' ROWS='2' ></TEXTAREA>");
salida.println("<input type='hidden' name='consecutivo' value='"+consecutivo+"'>");
salida.println("<input type='hidden' name='apo_beneficio' value='"+apo_beneficio+"'>");
salida.println("<input type='hidden' name='f_producto' value='"+producto+"'>");
salida.println("<input type='hidden' name='f_contrato' value='"+contrato+"'>");
salida.println("<input type='hidden' name='usuario' value='"+usuario+"'>");
salida.println("<input type='hidden' name='nom_producto' value='z'>");
salida.println("<input type='hidden' name='num_contrato' value='z'>");
salida.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
salida.println("<center><input type='submit' value='Aceptar'>"+
               "<input type='button' value='Cancelar' Onclick=history.go(-1);></center>");
salida.println(plantilla.TBFL_PIE);
salida.flush();
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA FORMATEAR UN VARIABLE STRING
* QUE REPRESENTA UNA FECHA EN UNA VARIABLE JAVA.SQL.DATE(ANO,MES,DIA)
* <p>
* @param   f_a           = FECHA STRING QUE SUFRIRÁ LA TRANSFORMACION<BR>
* @return  JAVA.SQL.DATE = FECHA FORMATEADA
*/
public static java.sql.Date TBPL_date(String f_a)
{
  //FECHA DE ENTRADA RRRR-MM-DD
  int    añoint = 0;
  int    mesint = 0;
  int    diaint = 0;
  String año = " ";
  String mes = " ";
  String dia = " ";
  int indice = 0;
  indice     = f_a.indexOf("-");
  año        = f_a.substring(0,indice);
  f_a        = f_a.substring(indice+1,f_a.length());
  indice     = f_a.indexOf("-");
  mes        = f_a.substring(0,indice);
  dia        = f_a.substring(indice+1,f_a.length());
  //convierto año a int
  Integer i1 = new Integer(año);
  añoint     = i1.intValue();
  añoint    -= 1900;
  //convierto mes a int
  Integer i2 = new Integer(mes);
  mesint     = i2.intValue();
  mesint    -= 1;
  //convierto dia a int
  Integer i3 = new Integer(dia);
  diaint     = i3.intValue();
  java.sql.Date date = new java.sql.Date(añoint,mesint,diaint);
  return date;
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA MOSTRAR EN EL BROWSER
* LA INFORMACION DE APORTES VALIDOS PERTENECIENTES A UN CONTRATO
* Y A LOS CUALES SE LE PUEDE MODIFICAR SUS CONDICIONES DE DISPONIBILIDAD
* <p>
@param      v_Condicion   = CONDICIONES DE DISPONIBILIDAD DE CADA APORTE<BR>
*           v_Fecha       = FECHAS DE DISPONIBILIDAD DE CADA APORTE<BR>
*           v_Porcentaje  = PORCENTAJES DE DISPONIBILIODAD DE CADA APORTE<BR>
*           v_Capital     = CAPITALES DE CADA APORTE<BR>
*           v_Rtos        = RENDIMIENTOS DE CADA APORTE<BR>
*           titulo        = MENSAJE A DESPLEGAR<BR>
*           f_producto    = PRODUCTO AL CUAL PERTENECE EL APORTE A MODIFICAR<BR>
*           f_contrato    = CONTRATO AL CUAL PERTENECE EL APORTE A MODIFICAR<BR>
*           salida        = OBJETO PRINTWRITER<BR>
*           total_aportes = TOTAL DE APORTES A MOSTRAR<br>
*           consecutivo   = VECTOR DE CONSECUTIVO DEL APORTE A MODIFICAR<BR>
*           grupo_id      = EMPRESAS DE CADA APORTE<BR>
*           ref_codigos   = CODIGOS DE CADA DISPONIBILIDAD PARAMETRIZADA<BR>
*           ref_descripciones = DESCRIPCIONES DE CADA DISPONIBILIDAD PARAMETRIZADA<BR>
*           ref_valores   = VALORES DE CADA DISPONIBILIDAD PARAMETRIZADA<BR>
*           nuevaCadena   = CADENA DE ENCRIPCION ENVIADA POR PIPELINE<BR>
* @return  NINGUNO
*/
public static void TBPL_Encabezados_Disponibles(String[] v_Condicion,String[] v_Fecha,String[] v_Porcentaje,
                                           int[] v_Capital,int[] v_Cuenta_C,double[] v_Rtos,String titulo,
                                           String f_producto,String f_contrato,PrintWriter respuesta,
                                           int total_aportes,double[] v_Consecutivos,String[] grupo_id,
                                           String[] ref_codigos,String[] ref_descripciones,int[] ref_valores,
                                           String nuevaCadena,String v_fecha1,String v_fecha2)
{
STBCL_GenerarBaseHTMLII plantilla = new STBCL_GenerarBaseHTMLII();
if(total_aportes>0)
{
  DecimalFormat formato = new DecimalFormat("###,###,###,###,###,###.##");
  boolean hay_eleccion  = false;
  respuesta.println(plantilla.TBFL_CABEZA("ADMINISTRADORA DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_DISPONIBILIDAD","",true));
  respuesta.println("<BR></BR>");
  respuesta.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=7 rules=all width='100%'>"+
  "<TR class=\"td11\" borderColor=silver>"+
  "<TH width='5%'><FONT color=white face=verdana size=1>&nbsp;</font></TH>"+
  "<TH width='68'><FONT color=white face=verdana size=1>Fecha</font></TH>"+
  "<TH width='15%'><FONT color=white face=verdana size=1>Capital</font></TH>"+
  "<TH width='15%'><FONT color=white face=verdana size=1>Rendimientos</font></TH>"+
  "<TH width='15%'><FONT color=white face=verdana size=1>Cuenta Contingente</font></TH>"+
  "<TH width='15%'><FONT color=white face=verdana size=1>Porcentaje</font></TH>"+
  "<TH width='15%'><FONT color=white face=verdana size=1>Condición</font></TH>"+
  "</TR>");
  int i              = 0;
  String radio       = " ";
  String descripcion = " ";
  for(i=0;i<total_aportes;i++)
  {
    try
    {
     if(!ref_descripciones[i].equals("null"))
       descripcion = ref_descripciones[i];
    }
    catch(Exception exc){descripcion = " ";}
    if(i==0) radio = "<TR bgColor=white borderColor=silver><TD width='5%'><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=fila CHECKED VALUE='"+v_Fecha[i]+"$"+v_Capital[i]+"$"+v_Rtos[i]+"$"+v_Cuenta_C[i]+"%"+v_Porcentaje[i]+"*"+v_Condicion[i]+"@"+v_Consecutivos[i]+"|"+grupo_id[i]+"&"+ref_descripciones[i]+"="+ref_codigos[i]+"#"+ref_valores[i]+"'></font></TD>";
    else     radio = "<TR bgColor=white borderColor=silver><TD width='5%'><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=fila         VALUE='"+v_Fecha[i]+"$"+v_Capital[i]+"$"+v_Rtos[i]+"$"+v_Cuenta_C[i]+"%"+v_Porcentaje[i]+"*"+v_Condicion[i]+"@"+v_Consecutivos[i]+"|"+grupo_id[i]+"&"+ref_descripciones[i]+"="+ref_codigos[i]+"#"+ref_valores[i]+"'></font></TD>";
     respuesta.println(radio+
     "<TD width='15%'><FONT color=black face=verdana size=1>"+v_Fecha[i]+"</font></TD>"+
     "<TD width='15%'><FONT color=black face=verdana size=1>$"+formato.format(v_Capital[i])+"</font></TD>"+
     "<TD width='15%'><FONT color=black face=verdana size=1>$"+formato.format(v_Rtos[i])+"</font></TD>"+
     "<TD width='15%'><FONT color=black face=verdana size=1>$"+formato.format(v_Cuenta_C[i])+"</font></TD>"+
     "<TD width='15%'><FONT color=black face=verdana size=1>"+v_Porcentaje[i]+"%</font></TD>"+
     "<TD width='15%'><FONT color=black face=verdana size=1>"+descripcion+"</font></TD></TR>");
  }
  respuesta.println("</TABLE>");
  respuesta.println("<BR></BR>");
  //jag se adiciona opcion de todos los aportes asociados a una empresa
  respuesta.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox' name='tipo_select' value='1'><FONT color=BLACK face=verdana size=1>Seleccionar todos los Aportes asociados a empresa</font><BR><BR>");
  respuesta.println("<input type='hidden' name='tipo_opcion' value='2'>");
  //jag fechas de consulta
  respuesta.println("<input type='hidden' name='v_fecha1' value='"+v_fecha1+"'>");
  respuesta.println("<input type='hidden' name='v_fecha2' value='"+v_fecha2+"'>");
  //campos ocultos de producto y contrato
  respuesta.println("<input type='hidden' name='nom_producto' value='y'>");
  respuesta.println("<input type='hidden' name='num_contrato' value='y'>");
  //campo oculto que me mantiene el total de aportes correspondientes al contrato
  respuesta.println("<input type='hidden' name='total_aportes' value='"+total_aportes+"'>");
  //campos ocultos y ficticios que me mantendrán los valores de  producto y contrato
  respuesta.println("<input type='hidden' name='f_producto' value='"+f_producto+"'>");
  respuesta.println("<input type='hidden' name='f_contrato' value='"+f_contrato+"'>");
  respuesta.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
  respuesta.println("<center><input type='submit' value='Aceptar'>"+
  "<input type='button' value='Cancelar' Onclick=history.go(-2);></center>");
  respuesta.println(plantilla.TBFL_PIE);
  respuesta.flush();
}//fin aportes>0
else if(total_aportes==0)
{
 String msj = "NO HAY ELEMENTOS SELECCIONADOS PARA SU TRANSACCION";
 respuesta.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS INTERNOS DE APORTES","",msj,false));
 respuesta.println("<br><br>");
 respuesta.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
 respuesta.println(plantilla.TBFL_PIE);
 respuesta.flush();
}//fin aportes==0
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* este procedimiento sirve para generar las fechas, lOs porcentajes
* y los valores pertenecientes a un contrato,un producto y un
* consecutivo DE APORTE elegido; en la actualizacion de dsiponibilidad
* <p>
* @param    total_fechas    = TOTAL DE FECHAS DE DISPONIBILIDAD PARA DICHO CONTRATO-PRODCUTO <BR>
*           fechas          =  FECHAS DE DISPONIBILIDAD PARA DICHO CONTRATO-PRODCUTO <BR>
*           porcentajes     =  PORCENTAJES DE DISPONIBILIDAD PARA DICHO CONTRATO-PRODCUTO <BR>
*           valores         =  VALORES DE DISPONIBILIDAD PARA DICHO CONTRATO-PRODCUTO <BR>
*           respuesta       =  OBJETO PRINTWRITER<BR>
*           titulo          =  MENSAJE A DESPLEGAR<BR>
*           f_producto      =  PRODUCTO EN CUASTION<BR>
*           f_contrato      =  CONTRATO EN CUESTION<BR>
*           c               =  OBJETO CONEXION<BR>
*           consecutivo     =  APORTE EN CUESTION<BR>
*           detalle         =  DETALLE DE DISPONIBILIDAD ACTUAL<BR>
*           g_id            =  GRUPO ID DE LA EMPRESA DEL APORTE EN CUESTION<BR>
*           fecha           =  FECHA DE DSIPONIBILIDAD ACTUAL<BR>
*           capital         =  CAPITAL DEL APORTE EN CUESTION<BR>
*           cc              =  CUENTA CONTINGENTE DEL APORTE EN CUESTION<BR>
*           rtos            =  RENDIMIENTOS DEL APORTE EN CUESTION<BR>
*           porcentaje      =  PORCENTAJE DE DSIPONIBILIDAD ACTUAL DEL APORTE EN CUESTION<BR><BR>
*           ref_descripcion =  DESCRIPCION DE LA DISPONIBILIDAD ACTUAL DEL APORTE EN CUESTION<BR><BR>
*           ref_codigo      =  CODIGO DE LA DISPONIBIULIDAD ACTUAL DEL APORTE EN CUESTION<BR><BR>
*           ref_valor       =  VALOR DE LA DISPONIBILIDAD ACTUAL DEL APORTE EN CUESTION<BR><BR>
*           nuevaCadena     =  CADENA DE ENCRIPCION<BR>
* @return  NINGUNO
*/
public static void TBPL_Fechas_Aportes(int total_fechas,String[] fechas,String[] porcentajes,int[] valores,PrintWriter respuesta,
                                  String titulo,String f_producto,String f_contrato,Connection c,double consecutivo,String detalle,
                                  String g_id,String fecha,String capital,String cc,String rtos,String porcentaje,String ref_descripcion,
                                  String ref_codigo,String ref_valor,String nuevaCadena)
{
STBCL_GenerarBaseHTMLII plantilla = new STBCL_GenerarBaseHTMLII();
try{
  String porc_pregrabado   = "value='0' ";
  String fecha_pregrabada  = " ";
  respuesta.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","ACTUALIZADOR DE DISPONIBILIDAD DE APORTES",
                                                "TBPKT_MODULO_APORTES.TBS_ACTUALIZA_DISPONIBILIDAD",
                                                " ",true,"moduloparametro.js","return checkrequired(this)"));
  DecimalFormat formato = new DecimalFormat("###,###,###,###,###,###.##");
  Double i1 = new Double(capital);double k   = i1.doubleValue();
  Double i2 = new Double(cc);     double c_c = i2.doubleValue();
  Double i3 = new Double(rtos);   double rto = i3.doubleValue();
      //consecucuion de datos de cabecera para el contrato, el producto y el consecutivo
    //capturo grupoid
   //busco la condicion
  //campo_oculto que me sirve para mantener el valor inicial del detalle
  respuesta.println("<input type='hidden' name='pos_detalle' value='"+detalle+"'>");
  //oculto fecha_efectiva, necesaria para compararla con la fecha_x antes de insertar la nueva disponibilidad
  respuesta.println("<input type='hidden' name='f_e' value='"+fecha+"'>");
  String cambiar_detalle = " ";
  String condiciones     = " ";
  try
   {
    if(!ref_descripcion.equals("null"))
       condiciones     = "<OPTION value='"+ref_valor+"$"+ref_codigo+"|"+ref_descripcion+"'>"+ref_descripcion;
   }
   catch(Exception exc){condiciones     = "<OPTION value='"+ref_valor+"$"+ref_codigo+"|"+ref_descripcion+"'> ";}
  String select8i_1 = "SELECT REF_DESCRIPCION,REF_VALOR,REF_CODIGO "+
                      "FROM TBREFERENCIAS "+
                      "WHERE "+
                      "REF_CODIGO BETWEEN 'UDS001' AND 'UDS999' ";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  ResultSet t_rs8i         = t_st8i.executeQuery();
  while(t_rs8i.next())
   {
    try
    {if(!t_rs8i.getString(1).equals(null))
        condiciones += "<OPTION value='"+t_rs8i.getString(2)+"$"+t_rs8i.getString(3)+"|"+t_rs8i.getString(1)+"'>"+t_rs8i.getString(1)+" Con "+t_rs8i.getString(2)+" %";
     }
    catch(Exception exce){condiciones += "<OPTION value='"+t_rs8i.getString(2)+"$"+t_rs8i.getString(3)+"|"+t_rs8i.getString(1)+"'> ";}
   }
  t_rs8i.close();
  t_st8i.close();
  if(detalle.equals("null"))
   detalle=" ";
  if(!g_id.equals("null"))
  {
   cambiar_detalle = "<input type='text' readonly name='condicion' value='"+detalle+"' size='30'>";
   condiciones     = "<OPTION value='"+ref_valor+"$"+ref_codigo+"|"+ref_descripcion+"'>"+ref_descripcion;
  }
  else if(g_id.equals("null"))
  {
   cambiar_detalle = "<input type='text' name='condicion' value='"+detalle+"' size='30'>";
   if(!ref_codigo.equals("null"))
    {
     condiciones     = "<OPTION value='"+ref_valor+"$"+ref_codigo+"|"+ref_descripcion+"'>"+ref_descripcion;
    }
   else if(ref_codigo.equals("null"))
   {
    porc_pregrabado  = "value='"+ref_valor+"' readonly";
    fecha_pregrabada = "value='"+fecha+"' readonly";
   }
 }
  //dibujo encabezado
  respuesta.println("<FONT color=black face=verdana size=1>Aporte Original</font>");
  respuesta.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=4 rules=all width='100%'>"+
  "<TR class=\"td11\" borderColor=silver>"+
  "<TH width='10%'><FONT color=white face=verdana size=1>Fecha</font></TH>"+
  "<TH width='10%'><FONT color=white face=verdana size=1>Capital</font></TH>"+
  "<TH width='10%'><FONT color=white face=verdana size=1>Rendimientos</font></TH>"+
  "<TH width='10%'><FONT color=white face=verdana size=1>Cuenta Contingente</font></TH></TR>");
  respuesta.println(
  "<TR><TD width='10%'><FONT color=black face=verdana size=1>"+fecha+"</font></TD>"+
  "<TD width='10%'><FONT color=black face=verdana size=1>$"+formato.format(k)+"</font></TD>"+
  "<TD width='10%'><FONT color=black face=verdana size=1>$"+formato.format(rto)+"</font></TD>"+
  "<TD width='10%'><FONT color=black face=verdana size=1>$"+formato.format(c_c)+"</font></TD></TR>");
  respuesta.println("</TABLE>");

  respuesta.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=2 rules=all width='100%'>"+
  "<TR class=\"td11\" borderColor=silver>"+
  "<TH width='10%'><FONT color=white face=verdana size=1>Condicion</font></TH>"+
  "<TH width='10%'><FONT color=white face=verdana size=1>Detalle</font></TH></TR>");
  respuesta.println(
  "<TR><TD width='10%'><FONT color=black face=verdana size=1><select name='cadena2'>"+condiciones+"</select></font></TD>"+
  "<TD width='10%'><FONT color=black face=verdana size=1>"+cambiar_detalle+"</font></TD></TR>");
  respuesta.println("</TABLE>");

  respuesta.println("<FONT color=black face=verdana size=1>Disponibilidades</font>");
  respuesta.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=3 rules=all width='100%'>"+
  "<TR class=\"td11\" borderColor=silver>"+
  "<TH width='10%'><FONT color=white face=verdana size=1>Fecha</font></TH>"+
  "<TH width='10%'><FONT color=white face=verdana size=1>Valor</font></TH>"+
  "<TH width='10%'><FONT color=white face=verdana size=1>Porcentaje</font></TH></TR>");
  for(int i=0;i<total_fechas;i++)
    respuesta.println(
    "<TR><TD width='10%'><FONT color=black face=verdana size=1>"+fechas[i]+"</font></TD>"+
    "<TD width='10%'><FONT color=black face=verdana size=1>$"+formato.format(valores[i])+"</font></TD>"+
    "<TD width='10%'><FONT color=black face=verdana size=1>"+porcentajes[i]+"%</font></TD></TR>");
  respuesta.println("</TABLE>");
  //dibujo campos de entrada
  respuesta.println("<p><FONT color=black face=verdana size=1>*Fecha:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>");
  respuesta.println("<INPUT NAME='obligatorio_fecha_x' TYPE=text MAXLENGTH=10 SIZE=12' "+fecha_pregrabada+" onchange='checkdate(this)'> <FONT color=black face=verdana size=1> YYYY-MM-DD</font></p>");
  respuesta.println("<p><FONT color=black face=verdana size=1>*Porcentaje: </font>");
  respuesta.println("<input type='text' name='obligatorio_porcentaje_x'  MAXLENGTH=6 SIZE=6 "+porc_pregrabado+" onchange='if (esNumero(this)==1) return false;checkDecimals(this,this.value,2),esMayor(this)'></p>");
   //campos ocultos de producto y contrato que me dicen cual hoja dibujar
  respuesta.println("<input type='hidden' name='nom_producto' value='z'>");
  respuesta.println("<input type='hidden' name='num_contrato' value='z'>");
  //campos ocultos necesarios par relizar update final
  respuesta.println("<input type='hidden' name='ref_descripcion_o' value='"+ref_descripcion+"'>");
  respuesta.println("<input type='hidden' name='ref_codigo_o' value='"+ref_codigo+"'>");
  respuesta.println("<input type='hidden' name='ref_valor_o' value='"+ref_valor+"'>");
  respuesta.println("<input type='hidden' name='f_producto' value='"+f_producto+"'>");
  respuesta.println("<input type='hidden' name='f_contrato' value='"+f_contrato+"'>");
  respuesta.println("<input type='hidden' name='g_id' value='"+g_id+"'>");
  respuesta.println("<input type='hidden' name='conse' value='"+consecutivo+"'>");
  respuesta.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
  respuesta.println("<center><input type='submit' value='Aceptar' >"+
                    "<input type='button' value='Cancelar' Onclick=history.go(-2);></center>");
  respuesta.println(plantilla.TBFL_PIE);
  respuesta.close();
  }
  catch(Exception ex){ }
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA MOSTRAR LA INFORMACION DE LAS
* AFP A LA CUALES PERTENECN LOS APORTES DE UN PRODUCTO-CONTRATO
* <p>
* @param    nombre_afp  = NOMBRE DE CADA AFP A DIBUJAR<BR>
*           nit_afp     = NIT DE CADA AFP<BR>
*           total_afp   = TOTAL DE AFP A MOSTRAR<BR>
*           producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           respuesta   =  OBJETO PRINTWRITER<BR>
*           codigo_afp  =  CODIGOS DE CADA AFP A MOSTRAR<BR>
*           nuevaCadena =  CADENA DE ENCRIPCION<BR>
* @return  NINGUNO
*/
public static void TBPL_Publica_AFP(String[] nombre_afp,String[] nit_afp,int total_afp,String producto,
                               String contrato,String usuario,PrintWriter salida,String[] codigo_afp,
                               String nuevaCadena)
{
STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
String radio = " ";
if(total_afp>0)
{
salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS DE APORTES",
                                                "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS1","",true));
salida.println("<center><table border='0' align='center' width='80%'>");
String v_contrato_unif = "";
v_Consulta= new TBCL_Consulta();
v_Consulta.TBPL_RealizarConexion();
v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(producto,contrato);
v_Consulta.TBPL_shutDown();
salida.println("<tr><td align='center'><font face='Verdana' size='1'><strong>Producto: "+producto+"&nbsp;&nbsp;Contrato: "+v_contrato_unif+"</strong></font></td></tr>");
salida.println("<tr><td align='center'><font face='Verdana' size='1'><strong>"+TBPL_Nombres(producto,contrato).toUpperCase()+"</strong></font></td></tr>");
salida.println("</table></center>");
salida.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=3 rules=all width='100%'>"+
"<TR class=\"td11\" borderColor=silver>"+
"<TH width='5%'><FONT color=white face=verdana size=1>&nbsp;</font></TH>"+
"<TH width='90'><FONT color=white face=verdana size=1>Nombre Administradora</font></TH>"+
"<TH width='25%'><FONT color=white face=verdana size=1>NIT Administradora</font></TH>"+
"</TR>");
for(int i=0;i<total_afp;i++)
  {
    if(i==0) radio = "<TR bgColor=white borderColor=silver><TD width='5%'><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=fila CHECKED VALUE='"+codigo_afp[i]+"$"+nombre_afp[i]+"$"+nit_afp[i]+"'></font></TD>";
    else     radio = "<TR bgColor=white borderColor=silver><TD width='5%'><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=fila         VALUE='"+codigo_afp[i]+"$"+nombre_afp[i]+"$"+nit_afp[i]+"'></font></TD>";
    salida.println(radio+
    "<TD width='90'><FONT color=black face=verdana size=1>"+nombre_afp[i]+"</font></TD>"+
    "<TD width='25%'><FONT color=black face=verdana size=1>"+nit_afp[i]+"</font></TD></TR>");
  }
salida.println("</TABLE>");
salida.println("<input type='hidden' name='f_producto' value='"+producto+"'>");
salida.println("<input type='hidden' name='f_contrato' value='"+contrato+"'>");
salida.println("<input type='hidden' name='usuario'    value='"+usuario+"'>");
//preparo valores para ejecutar esta tarea en el doget
salida.println("<center><input type='hidden' name='nom_producto' value='A'></center>");
salida.println("<center><input type='hidden' name='num_contrato' value='A'></center>");
salida.println("<BR>");
salida.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
salida.println("<center><input type='submit' value='Aceptar'>"+
               "<input type='button' value='Cancelar' Onclick=history.go(-1);></center>");
salida.println(plantilla.TBFL_PIE);
salida.flush();
}
else
{
String msj = "NO HAY ELEMENTOS SELECCIONADOS PARA SU TRANSACCION";
salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS DE APORTES","",msj,false));
salida.println("<br><br>");
salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1); ></center>");
salida.println(plantilla.TBFL_PIE);
salida.flush();
}
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA DESPLEGAR LOS APORTES QUE AUN NO TIENE
* <p>
* @param   <BR>
* @return  NINGUNO
*/
public static void TBPL_Publica_Aportes(String producto,String contrato,String[] fecha_e,double[] cc
                                   ,double[] c,double[] rtos,int total_aportes,PrintWriter salida
                                   ,String usuario,String[] nomb_afp,int[] consecutivos
                                   ,String nombres,String codigo_afp,String nit_afp, String nuevaCadena)
{
STBCL_GenerarBaseHTMLII plantilla = new STBCL_GenerarBaseHTMLII();
String radio = " ";
if(total_aportes>0)
{
  DecimalFormat formato = new DecimalFormat("###,###,###,###,###,###.##");
//Modificado por APC para deshabilitar botones en traslados
  /*  salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS DE APORTES",
                                                "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS1","",true));*/
  salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS DE APORTES",
                                                "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS1","",true,"validation.js","disableButtons(this)"));
  salida.println("<br>");
  salida.println("<center><table border='0' align='center' width='80%'>");
  String v_contrato_unif = "";
  v_Consulta= new TBCL_Consulta();
  v_Consulta.TBPL_RealizarConexion();
  v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(producto,contrato);
  v_Consulta.TBPL_shutDown();
  
  salida.println("<tr><td align='center'><font face='Verdana' size='1'><strong>Producto: "+producto+"&nbsp;&nbsp;Contrato: "+v_contrato_unif+"</strong></font></td></tr>");
  salida.println("<tr><td align='center'><font face='Verdana' size='1'><strong>"+nombres.toUpperCase()+"</strong></font></td></tr>");
  salida.println("</table></center>");
  salida.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=6 rules=all width='100%'>");
  salida.println("<TR class=\"td11\" borderColor=silver>");
  salida.println("<TH width='5%'><FONT color=white face=verdana size=1>&nbsp;</font></TH>");
  salida.println("<TH width='68'><FONT color=white face=verdana size=1>Fecha</font></TH>");
  salida.println("<TH width='10%'><FONT color=white face=verdana size=1>Capital</font></TH>");
  salida.println("<TH width='10%'><FONT color=white face=verdana size=1>Rendimientos</font></TH>");
  salida.println("<TH width='10%'><FONT color=white face=verdana size=1>Cuenta Contingente</font></TH>");
  salida.println("<TH width='10%'><FONT color=white face=verdana size=1>Administradora</font></TH>");
  if(producto.trim().equals("FPOB") || producto.trim().equals("FPAL"))
    salida.println("<TH width='10%'><FONT color=white face=verdana size=1>Concepto</font></TH>");
  salida.println("</TR>");
  
  for(int i=0;i<total_aportes;i++)
   {
    if(i==0) radio = "<TR bgColor=white borderColor=silver><TD width='5%'><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=consecutivo CHECKED VALUE='"+consecutivos[i]+"'></font></TD>";
    else     radio = "<TR bgColor=white borderColor=silver><TD width='5%'><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=consecutivo         VALUE='"+consecutivos[i]+"'></font></TD>";
    salida.println(radio+
                       "<TD width='50'><FONT color=black face=verdana size=1>"+fecha_e[i]+"</font></TD>"+
                       "<TD width='10%'><FONT color=black face=verdana size=1>$"+formato.format(c[i])+"</font></TD>"+
                       "<TD width='10%'><FONT color=black face=verdana size=1>$"+formato.format(rtos[i])+"</font></TD>"+
                       "<TD width='10%'><FONT color=black face=verdana size=1>$"+formato.format(cc[i])+"</font></TD>"+
                       "<TD width='10%'><FONT color=black face=verdana size=1>"+nomb_afp[i]+"</font></TD>");    
    salida.println("</TR>");
   }//for
   salida.println("</TABLE>");
    salida.println("<br />");
   salida.println("<input type='hidden' name='f_producto' value='"+producto+"'>");
   salida.println("<input type='hidden' name='f_contrato' value='"+contrato+"'>");
   salida.println("<input type='hidden' name='usuario' value='"+usuario+"'>");
   salida.println("<input type='hidden' name='codigo_afp' value='"+codigo_afp+"'>");
   salida.println("<input type='hidden' name='nit_afp' value='"+nit_afp+"'>");
   //campos ocultos de producto y contrato que me dicen cual hoja dibujar
   salida.println("<input type='hidden' name='nom_producto' value='B'>");
   salida.println("<input type='hidden' name='num_contrato' value='B'>");
   salida.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
   salida.println("<center><input type='submit' value='Aceptar'>"+
   "<input type='button' value='Cancelar' Onclick=history.go(-1);></center>");
   salida.println(plantilla.TBFL_PIE);
   salida.flush();
}
else
{
  STBCL_GenerarBaseHTML plantilla1 = new STBCL_GenerarBaseHTML();
  String msj = "NO HAY ELEMENTOS SELECCIONADOS PARA SU TRANSACCION";
  salida.println(plantilla1.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOS DE APORTES","",msj,false));
  salida.println("<br><br>");
  salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1); ></center>");
  salida.println(plantilla1.TBFL_PIE);
  salida.flush();
}
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA verificar si una AFP elegida por el usuario
* pertenece en ese instante a la cargada sobre la tabla TBINTERFACE_APORTES
* <p>
* @param   afp_o      = CODIGO DE LA AFP A LA CUAL SE LE REALIZARÁ LA VALIDACIÓN<BR>
*          c          = OBJETO CONEXIÓN<BR>
* @return  BOOLEANO
*/
public static boolean TBPL_AFP(String afp_o,Connection c)
{
try
{
 String select8i_1 = "SELECT "+
                     "INT_NIT_AFP_ORIGEN "+
                     "FROM TBINTERFACE_TRASLADOS "+
                     "WHERE INT_NIT_AFP_ORIGEN = ?";
  PreparedStatement t_st8i = c.prepareStatement(select8i_1);
  afp_o = afp_o.trim();
  t_st8i.setString(1,afp_o);
  ResultSet t_rs8i;
  t_rs8i       = t_st8i.executeQuery();
  boolean next = false;
  while(t_rs8i.next())
   {
    next = true;
    break;
   }
  t_rs8i.close();
  t_st8i.close();
  if(next) return true;
  else     return false;
}
catch(Exception ex){return false;}
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA COMPARAR LA INFORMACION TOTAL DE
* TRASLADO DE UN APORTE CON RESPECTO LA DEL APORTE PADRE
* <p>
* @param    ch     = CAPITAL TOTAL DE LOS HIJOS<BR>
*           rtosh  = RENDIMIENTOS TOTALES DE LOS HIJOS<BR>
*           cp     = CAPITAL TOTAL DEL PADRE<BR>
*           rtosp  = RENDIMEINTOS TOTALES DEL PADRE  <BR>
* @return  BOOLEANO
*/
public static boolean TBPL_Compara_Totales(double ch,double rtosh,double cp,double rtosp)
{
 long   ch_1    = java.lang.Math.round(ch);     long   cp_1    = java.lang.Math.round(cp);
 long   rtosh_1 = java.lang.Math.round(rtosh);  long   rtosp_1 = java.lang.Math.round(rtosp);


 //if(ch_1==cp_1 && rtosh_1==rtosp_1)
 if(Math.abs(ch-cp)<0.1 && Math.abs(rtosh-rtosp)<0.1)
    return true;
 else
    return false;
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA DESPLEGAR LA INFORMACION DE TRASLADO
* DE UN APORTE JUNTO CON LA SUYA
* <p>
* @param    apr_capital  =  CAPITAL DE CADA APORTE HIJO<BR>
*           c_ch         =  CUENTA CONTNGENTE DE CADA APORTE HIJO<BR>
*           apr_rto      =  RENDIMIENTOS DE CADA APORTE HIJO<BR>
*           capital_papa =  CAPITAL DEL APORTE PADRE<BR>
*           c_cpapa      =  CUENTA CONTINGENTE DEL APORTE PADRE<BR>
*           rtos_papa    =  RENDIMIENTOS DEL APORTE PADRE<BR>
*           total_hijos  =  TOTAL DE APORTES HIJOS  A MOSTRAR<BR>
*           respuesta    =  OBJETO PRINTWRITER<BR>
*           msj          =  MENSAJE A DESPLEGAR<BR>
*           msj_cc       =  MENSAJE A DESPLEGAR<BR>
*           titulo       =  MENSAJE A DESPLEGAR<BR>
* @return  NINGUNO
*/
public static void TBPL_Papa_Hijos(double[] apr_capital,double[] c_ch,double[] apr_rto,double capital_papa,double c_cpapa,
                                   double rtos_papa,int total_hijos,PrintWriter respuesta,String msj,String msj_cc,String titulo)
{
STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
respuesta.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES",titulo,"",msj+msj_cc,false));
respuesta.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=3 rules=all width='100%'>"+
               "<TR class=\"td11\" borderColor=silver>"+
               "<TH width='33%'><FONT color=white face=verdana size=1>Capital</font></TH>"+
               "<TH width='33%'><FONT color=white face=verdana size=1>Rendimientos</font></TH>"+
               "<TH width='33%'><FONT color=white face=verdana size=1>Cuenta Contingente</font></TH>"+
               "</TR>");
respuesta.println("<TR bgColor=#E6E6FA borderColor=silver><TD width='33%'><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(capital_papa)+"</font></TD>"+
                  "<TD width='33%'><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(rtos_papa)+"</font></TD>"+
                  "<TD width='33%'><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(c_cpapa)+"</font></TD></TR>");
for(int i=0;i<total_hijos;i++)
{
 respuesta.println("<TR bgColor=#F0FFF0 borderColor=silver><TD width='33%'><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(apr_capital[i])+"</font></TD>"+
                   "<TD width='33%'><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(apr_rto[i])+"</font></TD>"+
                   "<TD width='33%'><FONT color=black face=verdana size=1>"+NumberFormat.getCurrencyInstance().format(c_ch[i])+"</font></TD></TR>");
}
respuesta.println("</TABLE>");
String msje = "<FONT color='#324395' face=verdana size=1><strong>Nota: El primer registro corresponde al Aporte Padre.</strong></font>";
respuesta.println(msje);
respuesta.println("<br><br>");
respuesta.println("<center><input type='button' value='Aceptar' Onclick=history.go(-2);></center>");
respuesta.println(plantilla.TBFL_PIE);
respuesta.flush();
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA
* <p>
* @param   <BR>
* @return  NINGUNO
*/
public static void TBPL_Usuario_Invalido(PrintWriter salida,String contrato,long n_i)
{
STBCL_GenerarBaseHTML plantilla = new STBCL_GenerarBaseHTML();
salida.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS EXTERNOSXXXXXXXXXXX",
                                                "TBPKT_MODULO_APORTES.TBS_CARGA_APORTES_EXTERNOS","",true));
salida.println("<BR><BR>");
salida.println("<p><B><FONT color=black face=verdana size=1>DURANTE LA CARGA DE TRASLADOS SE ENCONTRO QUE EL CONTRATO NUMERO "+
               contrato+" NO ESTA VINCULADO AL USUARIO IDENTIFICADO CON EL NUMERO "+n_i+" POR CONSIGUIENTE LA CARGA NO ES VALIDA</font></B></p>");
salida.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1); ></center>");
salida.println(plantilla.TBFL_PIE);
salida.close();
}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA MOSTRAR EN EL BROWSER
* LA INFORMACION DE APORTES VALIDOS PERTENECIENTES A UN CONTRATO
* Y A LOS CUALES SE LE PUEDE MODIFICAR SU INFORMACION DE TRASLADO
* EN EL SUBMODULO DE ACTUALIZACION DE APORTES INTERNOS
* <p>
@param      producto      = PRODUCTO AL CUAL PERTENECE EL APORTE A MODIFICAR<BR>
*           contrato      = CONTRATO AL CUAL PERTENECE EL APORTE A MODIFICAR<BR>
*           f_E           = VECTOR DE FECHAS EFECTIVAS DEL APORTE A MODIFICAR<BR>
*           cc            = VECTOR DE CUENTA CONTINGENTE CON LA CUAL CUENTA EL APORTE ANTES DE SER MODIFICADO<BR>
*           c             = VECTOR DE CAPITALES CON EL CUAL CUENTA EL APORTE ANTES DE SER MODIFICADO<BR>
*           rtos          = VECTOR DE RENDIMIENTOS CON LOS CUALES CUENTA EL APORTE ANTES DE SER MODIFICADO<BR>
*           total_aportes = TOTAL DE APORTES A MOSTRAR
*           salida        = OBJETO PRINTWRITER<BR>
*           usuario       = USUARIO QUE REALIZARA LA MODIFICACION<BR>
*           nomb_afp      = VECTOR DE NOMBRES DE LAS AFP A LAS CUALES PERTENECEN LOS APORTES AMODIFICAR<BR>
*           consecutivo   = VECTOR DE CONSECUTIVO DEL APORTE A MODIFICAR<BR>
*           nombres       = INFORMACION DEL DUEÑO DEL CONTRATO<BR><BR>
*           nuevaCadena   = CADENA DE ENCRIPCION ENVIADA POR PIPELINE<BR>
* @return  NINGUNO
*/
public static void TBPL_Datos_Informacion_Traslado(String nombre_producto,String numero_contrato,String[] fecha_e,double[] cc,
                                                  double[] c,double[] rtos,int total_aportes,PrintWriter respuesta,
                                                  String usuario,String[] nomb_afp,int[] consecutivos,String nombres,
                                                  String nuevaCadena)
{
STBCL_GenerarBaseHTMLII plantilla = new STBCL_GenerarBaseHTMLII();
if(total_aportes>0)
{
  DecimalFormat formato = new DecimalFormat("###,###,###,###,###,###.##");
  boolean hay_datos     = false;
//Modificado por APC para deshabilitar botones en traslados
/* respuesta.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS INTERNOS DE APORTES",
                                      "TBPKT_MODULO_APORTES.TBS_INFORMACION_TRASLADO","",true));*/
 respuesta.println(plantilla.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS INTERNOS DE APORTES",
                                      "TBPKT_MODULO_APORTES.TBS_INFORMACION_TRASLADO","",true,"validation.js","disableButtons(this)"));

  respuesta.println("<center><table border='0' align='center' width='80%'>");
  String v_contrato_unif = "";
  v_Consulta= new TBCL_Consulta();
  v_Consulta.TBPL_RealizarConexion();
  v_contrato_unif = v_Consulta.TBFL_ConsultaRefUnica(nombre_producto,numero_contrato);
  v_Consulta.TBPL_shutDown();
  
  respuesta.println("<tr><td align='center'><font face='Verdana' size='1'><strong>Producto: "+nombre_producto+"&nbsp;&nbsp;Contrato: "+v_contrato_unif+"</strong></font></td></tr>");
  respuesta.println("<tr><td align='center'><font face='Verdana' size='1'><strong>"+nombres.toUpperCase()+"</strong></font></td></tr>");
  respuesta.println("</table></center>");
  String radio = " ";
  respuesta.println("<TABLE bgColor=white border=2 borderColor=silver cellPadding=2 cellSpacing=0 cols=6 rules=all width='100%'>"+
  "<TR class=\"td11\" borderColor=silver>"+
  "<TH width='5%'><FONT color=white face=verdana size=1>&nbsp;</font></TH>"+
  "<TH width='70'><FONT color=white face=verdana size=1>Fecha</font></TH>"+
  "<TH width='10%'><FONT color=white face=verdana size=1>Capital</font></TH>"+
  "<TH width='10%'><FONT color=white face=verdana size=1>Rendimientos</font></TH>"+
  "<TH width='10%'><FONT color=white face=verdana size=1>Cuenta Contingente</font></TH>"+
  "<TH width='200'><FONT color=white face=verdana size=1>Administradora</font></TH>"+
  "</TR>");
  for(int i=0;i<total_aportes;i++)
   {
   try{
       if(!fecha_e[i].equals(null))
       {
        if(i==0) radio = "<TR bgColor=white borderColor=silver><TD width='5%'><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=posicion CHECKED VALUE='"+consecutivos[i]+"'></font></TD>";
        else     radio = "<TR bgColor=white borderColor=silver><TD width='5%'><FONT color=black face=verdana size=1><INPUT TYPE=radio NAME=posicion         VALUE='"+consecutivos[i]+"'></font></TD>";
        respuesta.println(radio+
        "<TD width='10%'><FONT color=black face=verdana size=1>"+fecha_e[i]+"</font></TD>"+
        "<TD width='10%'><FONT color=black face=verdana size=1>$"+formato.format(c[i])+"</font></TD>"+
        "<TD width='10%'><FONT color=black face=verdana size=1>$"+formato.format(rtos[i])+"</font></TD>"+
        "<TD width='10%'><FONT color=black face=verdana size=1>$"+formato.format(cc[i])+"</font></TD>"+
        "<TD width='10%'><FONT color=black face=verdana size=1>"+nomb_afp[i]+"</font></TD></TR>");
        hay_datos = true;
       }//si fecha <> NULL
      }//try
   catch(Exception ex){i=fecha_e.length;}
  }//for
  respuesta.println("</TABLE>");
  respuesta.println("<br>");
  //oculto producto-contrato
  respuesta.println("<input type='hidden' name='f_producto' value='"+nombre_producto+"'>");
  respuesta.println("<input type='hidden' name='f_contrato' value='"+numero_contrato+"'>");
  respuesta.println("<input type='hidden' name='usuario' value='"+usuario+"'>");
  //campos ocultos de producto y contrato que me dicen cual hoja dibujar
  respuesta.println("<input type='hidden' name='nom_producto' value='x'>");
  respuesta.println("<input type='hidden' name='num_contrato' value='x'>");
  respuesta.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
  respuesta.println("<center><input type='submit' value='Aceptar'>"+
                    "<input type='button' value='Cancelar' Onclick=history.go(-1);></center>");
  respuesta.println(plantilla.TBFL_PIE);
  respuesta.flush();
}//fin aportes>0
else if(total_aportes==0)
{
 STBCL_GenerarBaseHTML plantilla1 = new STBCL_GenerarBaseHTML();
 String msj = "NO HAY ELEMENTOS SELECCIONADOS PARA SU TRANSACCION";
 respuesta.println(plantilla1.TBFL_CABEZA("ADMINISTRADOR DE APORTES","INFORMACION DE TRASLADOS INTERNOS DE APORTES","",msj,false));
 respuesta.println("<br><br>");
 respuesta.println("<center><input type='button' value='Aceptar' Onclick=history.go(-1);></center>");
 respuesta.println(plantilla1.TBFL_PIE);
 respuesta.flush();
}//fin aportes==0

}
//-----------------------------------------------------------------------------
/**
* <font face='Verdana' size='2' color='#324395'>
* ESTE PROCEDIMIENTO SIRVE PARA ALMACENAR EN UN VECTOR LAS FECHAS
* EFECTIVAS DE LOS APORTES PERTENECIENTES A UN CONTRATO Y UN PRODUCTO
* ESPECIFICO
* <p>
* @param    producto    = PRODCUTO AL QUE PERTENECE EL APORTE <BR>
*           contrato    = CONTRATO AL QUE PERTENECE EL APORTE <BR>
*           c           =  OBJETO CONEXION<BR>
* @return   VECTOR DE FECHAS EFECTIVAS A DESPLEGAR EN EL BROWSER
*/
public static String[] TBPL_Fechas_por_Contrato(String producto,String contrato ,Connection c)
{
String v_fechas[] = new String [500];
try
 {
   //selecciono las fechas correspondientes a dicho contrato y producto
  Statement st8i    = c.createStatement();
  ResultSet rs8i;
  String select8i   = "SELECT "+
                      "DISTINCT TO_CHAR(APO_FECHA_EFECTIVA,'RRRR-MM-DD') "+
                      "FROM TBAPORTES "+
                      "WHERE APO_CON_PRO_CODIGO = '"+producto+"' "+
                      "AND APO_CON_NUMERO       = '"+contrato+"' "+
                      "ORDER BY TO_CHAR(APO_FECHA_EFECTIVA,'RRRR-MM-DD') DESC";

  rs8i              = st8i.executeQuery(select8i);
  int i=0;
  while(rs8i.next())
   {
    v_fechas[i] = rs8i.getString(1);
   i++;
   }
  return v_fechas;
 }//try
 catch(Exception ex){System.out.println(" ");return v_fechas;}
}
//-----------------------------------------------------------------------------
}

