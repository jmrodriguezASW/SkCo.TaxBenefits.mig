package TBPKT_MODULO_RETIROS.TBPKT_INTERFACE_RETIROS;

import java.sql.*;
import javax.servlet.*; 
import javax.servlet.http.*; 
import java.io.*; 
import oracle.jdbc.*;
import oracle.jdbc.OracleTypes.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;


public class Hello extends HttpServlet {   public void init(ServletConfig conf)
    throws ServletException
  {
    super.init(conf);
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    out.println("<html>");
    out.println("<body>");
    out.println("<h1>Bienvenido al mundo por Get</h1>");
    
        /*Conexion con la base de datos*/
    //logger.info
    
    Connection con = null;  
    try{
        
        con =   DataSourceWrapper.getInstance().getConnection();
        
              int pos_i = 0;
      CallableStatement csFechaHabil = con.prepareCall(SQLTools.buildProcedureCall(null, "TBPBD_FECHA_CIERRE_VALIDA", 1));
      csFechaHabil.registerOutParameter(1, OracleTypes.VARCHAR);
      csFechaHabil.execute();
      String fechaSigHabil = csFechaHabil.getString(1);
      String fechaSigHabilTrunc = "";
      for (int i=0; i < fechaSigHabil.length(); i++) {
        if (fechaSigHabil.charAt(i) != '-')
          fechaSigHabilTrunc += fechaSigHabil.charAt(i);
      }
      //logger.info
      fechaSigHabil =  fechaSigHabilTrunc;
      csFechaHabil.close();
      
      /*CallableStatement csUsuario = con.prepareCall(SQLTools.buildProcedureCall(null, "TBFBD_REFERENCIAS", 2));
      csUsuario.registerOutParameter(1, OracleTypes.VARCHAR);
      csUsuario.setString(2, "UTU");
      csUsuario.execute();
      String user = csUsuario.getString(1);*/
      
      out.println("<h1>Fecha</h1>"+fechaSigHabil);
      //out.println("<h1>Usuario</h1>"+user);
        out.println("</body>");
        out.println("</html>");
        
        
            }  catch (Exception e){
         out.println("SQL_PASO10 "+e);
    } finally{
        try { 
             if( con != null ) { 
                con.commit();
                DataSourceWrapper.closeConnection(con);                  
             }
         }
         catch( SQLException ignored ){
         out.println("SQL_PASO10 "+ignored);
           } 
    }
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    out.println("<html>");
    out.println("<body>");
    out.println("<h1>Bienveniodo al mundo por Post</h1>");
    out.println("</body>");
    out.println("</html>");
  }
} 
