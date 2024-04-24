package TBPKT_UTILIDADES.TBPKT_CONSULTA;


import java.io.*;
import java.net.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.*;

//import com.ibm.as400.access.*;


public class TBCL_ConsultaClienteLista 
{
  public TBCL_ConsultaClienteLista()
  {
  }
  
  public String ejecutarLlamadoWEB(String direccion_html)
  {

    String sRta = "";

    try
    {
      URL dirWEB = new URL(direccion_html);
      URLConnection yc = dirWEB.openConnection();
      yc.connect();
       BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null)
      {
          sRta+=inputLine;
      }
      in.close();
      return sRta;
    } catch (Exception e) {
      e.printStackTrace();
      return "ERROR";
    }
  }
  
  /* Recibe como parámetros:
   * 1. ID
   * devuelve en el area de datos Y/N Encaso de encontrar el cliente en la lista coloca Y.
   */
  public String ConsultarCliente(String cliente)
  {
  
    TBCL_Consulta v_Consulta = new TBCL_Consulta();
    String v_dconsulta="";
    String rta= "N";
    
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
    
    v_dconsulta = "SELECT ref_Valor\n"+
                 " FROM    tbreferencias\n"+
                 " WHERE   ref_codigo = 'SCR003'";
    String v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta,"ref_Valor").elementAt(0).toString();;

    v_Consulta.TBPL_shutDown();
    //http://10.42.1.77/
    String direccion_html = v_resultadoconsulta +"WSBLS/WSBLS.asmx/Nombres_Busquedas?Documento_Id="+
                             cliente
                             +"&Nombre=&Id_Compania=&Compania=&Tipo_Consulta=&No_Registros=&Calificacion=";

    String retorno = ejecutarLlamadoWEB(direccion_html);
    String sTagCustomer = "<ID>"+cliente+"</ID>";
    if (retorno.indexOf(sTagCustomer) != -1)
    {
          rta= "Y";
    }
      return rta;
  }
  
  /* Recibe como parámetros:
   * 1. ID
   * devuelve en el area de datos Y/N Encaso de encontrar el cliente en la lista coloca Y.
   */
  public String ConsultarDisponibleGarantias(String contrato, String producto, PrintWriter out)
  {
  
    TBCL_Consulta v_Consulta = new TBCL_Consulta();
    String v_dconsulta="";
    String rta= "0";
    
    v_Consulta.TBPL_RealizarConexion();//Realiza la conexion con la base de datos
    
    v_dconsulta = "SELECT ref_Valor\n"+
                 " FROM    tbreferencias\n"+
                 " WHERE   ref_codigo = 'SCR003'";
    String v_resultadoconsulta = v_Consulta.TBFL_Consulta(v_dconsulta,"ref_Valor").elementAt(0).toString();;
    v_Consulta.TBPL_shutDown();
    String direccion_html = v_resultadoconsulta +"SkCo.Available/AvailableService.svc/javaGet/GetAvailableDs?contract="+
                             contrato+"&product="+producto+"&dateType=E";
    String retorno = ejecutarLlamadoWEB(direccion_html);
    
    String sTagCustomer = "<Available diffgr:id=\"Available1\" msdata:rowOrder=\"0\" diffgr:hasChanges=\"inserted\">";
    int campo =-1;
    if ((campo = retorno.indexOf(sTagCustomer)) != -1)
    {
        String startTag = "<Value>";  
        String endTag = "</Value>";
        int start = retorno.indexOf(startTag,campo) + startTag.length();
        int end = retorno.indexOf(endTag,campo);
        rta = retorno.substring(start, end);
    }
      return rta;
  }
  
  public static void main(String[] args) throws Exception
  {

    TBCL_ConsultaClienteLista i_consultaC = new TBCL_ConsultaClienteLista();
    //String retorno= i_consultaC.ConsultarDisponibleGarantias("82","MFUND");
    String retorno= i_consultaC.ConsultarCliente("6095803");
    System.out.println(retorno);
  }
  
}