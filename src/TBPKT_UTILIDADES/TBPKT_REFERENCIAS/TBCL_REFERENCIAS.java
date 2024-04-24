// Copyright (c) 2013 SKANDIA
package TBPKT_UTILIDADES.TBPKT_REFERENCIAS;/**Paquete de la clase que se encarga de ejecutar las declaraciones*/

import java.sql.*;
import java.text.*;
import java.io.*;
import java.util.Vector;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_CONSULTA.TBCL_Consulta;


public class TBCL_REFERENCIAS {
    private static TBCL_Consulta v_Consulta; // INT20131108
    public static String TBCL_CONSULTA(String p_referencia)
    {
        v_Consulta = new TBCL_Consulta();
        Vector v_resultadodeclaracion_xml = new Vector();
        String v_declaracion_xml = new String();
        v_Consulta.TBPL_RealizarConexion();
        v_declaracion_xml = "SELECT REF_VALOR "+
                     "FROM   TBREFERENCIAS "+
                     "WHERE  REF_CODIGO =  '"+p_referencia+"' ";
        v_resultadodeclaracion_xml = v_Consulta.TBFL_Consulta(v_declaracion_xml);

        String rutaFisica = v_resultadodeclaracion_xml.elementAt(0).toString();
        v_Consulta.TBPL_shutDown();
        return (rutaFisica);
    }
}
