/*@lineinfo:filename=Consultas_As400_OBLIG*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400;

import java.sql.*;

public class Consultas_As400_OBLIG extends Object{
       
   public String GET_COMISION_ADMIN_AS400_OBLIG (String vsistemaoblig, String vlibreriaoblig, String vusuariooblig, String vpasswordoblig, String v_pro, String v_contra){
       
       String Resultado = "0;0";
       String AVA = "0";
       String AVE = "0";
       
       try {
           
           DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver());       
           Connection connection = DriverManager.getConnection ("jdbc:as400://"+ vsistemaoblig +"/",vusuariooblig,vpasswordoblig);
           Statement st = connection.createStatement();    
           ResultSet rs = st.executeQuery("SELECT AOAWCD, SUM(AOANVL) " + 
                                          " FROM "+vlibreriaoblig+".ABAOCPP " + 
                                          " WHERE AOACES='P' AND AOAMCD = '"+v_pro+"' AND AOADNB = '"+ String.valueOf(Integer.parseInt(v_contra)) +"' GROUP BY AOAWCD ");
           while(rs.next()){ 
               if (rs.getString(1).trim().equals("AVA"))
                   AVA = rs.getString(2).trim();
               else
                if (rs.getString(1).trim().equals("AVE"))
                   AVE = rs.getString(2).trim();
           }    
                    
           Resultado = AVA+ ";" + AVE; 
       }catch (Exception e){            
            System.out.println("ERROR CONSULTA COMISION: "+e);                           
            return Resultado;
         }
       return Resultado;
   }  

public String GET_COMISION_ADMIN_AS400_OBLIG (String vsistemaoblig, String vlibreriaoblig, String vusuariooblig, String vpasswordoblig, String v_pro, String v_contra, String fecha){
       
       String Resultado = "0;0";
       String AVA = "0";
       String AVE = "0";
       
       try {
           
           DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver());       
           Connection connection = DriverManager.getConnection ("jdbc:as400://"+ vsistemaoblig +"/",vusuariooblig,vpasswordoblig);
           Statement st = connection.createStatement();    
           ResultSet rs = st.executeQuery("SELECT AOAWCD, SUM(AOANVL) " + 
                                          " FROM "+vlibreriaoblig+".ABAOCPP " + 
                                          " WHERE AOACES='P' AND AOAMCD = '"+v_pro+"' AND AOADNB = '"+ String.valueOf(Integer.parseInt(v_contra))+"'" +
                                          " AND AOJLDX <= '"+fecha+"' GROUP BY AOAWCD ");
           while(rs.next()){ 
               if (rs.getString(1).trim().equals("AVA"))
                   AVA = rs.getString(2).trim();
               else
                if (rs.getString(1).trim().equals("AVE"))
                   AVE = rs.getString(2).trim();
           }    
           Resultado = AVA+ ";" + AVE; 
       }catch (Exception e){            
            System.out.println("ERROR CONSULTA COMISION: "+e);                           
            return Resultado;
         }
       return Resultado;
   }
}/*@lineinfo:generated-code*/