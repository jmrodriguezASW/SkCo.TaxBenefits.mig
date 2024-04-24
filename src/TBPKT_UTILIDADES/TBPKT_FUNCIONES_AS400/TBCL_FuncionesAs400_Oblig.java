package TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400;

import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import com.ibm.as400.access.*;
import java.sql.*;

public class TBCL_FuncionesAs400_Oblig extends Object{
    
    public static String TBFL_Saldo_Contrato_OBLIG(String producto, String tipo_fecha, String fecha_consulta, String tipo_id_afiliado, String id_afiliado, String ruta,
                                                  String sistema, String usuario, String password)    
     //Esta función devuelve desde as400 el saldo del contrato de un afiliado as/400 en una cadena
      {
       try
       {    
         //realizo la conexión al sistema sadc2 con un userid y un password
         //estos parametros serán leidos desde tbrefencias desde taxb
         //AS400 sadc2 = new AS400(sistema);sadc2.setUserId(usuario);sadc2.setPassword(password);      
         AS400 sadc2 = new AS400(sistema,usuario,password);      
         //declaro y defino la lista de parametros, aquí configuro tanto los de entrada como los de salida
         //los parametros de entrada serán dummy(1),producto(8),tipo_fecha(1), fecha_consulta(8),tipo_id_afiliado(1), id_afiliado(12),     
         //Parametro Entrada salida Errores_proceso(2)  
         //los parametros de salida serán cadena1,cadena2,cadena3,cadena4 y cadena5.     
         ProgramParameter[] parametros = new ProgramParameter[12];
                         
         //parametros de entrada
         AS400Text t1         = new AS400Text(1);
         byte[] by1           = t1.toBytes(" ");
         parametros[0]        = new ProgramParameter(by1);
         AS400Text t2         = new AS400Text(8);
         byte[] by2           = t2.toBytes(producto);
         parametros[1]        = new ProgramParameter(by2);
         AS400Text t3         = new AS400Text(1);
         byte[] by3           = t3.toBytes(tipo_fecha);
         parametros[2]        = new ProgramParameter(by3);
         AS400Text t4         = new AS400Text(8);
         byte[] by4           = t4.toBytes(fecha_consulta);
         parametros[3]        = new ProgramParameter(by4);
         AS400Text t5         = new AS400Text(1);
         byte[] by5           = t5.toBytes(tipo_id_afiliado);
         parametros[4]        = new ProgramParameter(by5);
         AS400Text t6         = new AS400Text(12);
         byte[] by6           = t6.toBytes(id_afiliado);
         parametros[5]        = new ProgramParameter(by6);
         
         //parametros de salida
         parametros[6]        = new ProgramParameter(15);
         parametros[7]        = new ProgramParameter(15);
         parametros[8]        = new ProgramParameter(15);
         parametros[9]       = new ProgramParameter(15);
         parametros[10]       = new ProgramParameter(15);
         parametros[11]       = new ProgramParameter(2);

         String resul="ERROR";
         //realizo llamado al programa as400
          ProgramCall programa = new ProgramCall(sadc2,ruta+"A2WVXFR.PGM",parametros);     
         if(programa.run())
          {
           //capturo cadena1
           AS400Text tx1   = new AS400Text(15);
           byte[] b1       = parametros[6].getOutputData();
           String cadena1  = (String)tx1.toObject(b1);
           cadena1=cadena1.trim();
           //capturo cadena2
           AS400Text tx2   = new AS400Text(15);
           byte[] b2       = parametros[7].getOutputData();
           String cadena2  = (String)tx2.toObject(b2);
           cadena2=cadena2.trim();
           //capturo cadena3
           AS400Text tx3   = new AS400Text(15);
           byte[] b3       = parametros[8].getOutputData();
           String cadena3  =(String)tx3.toObject(b3);
           cadena3=cadena3.trim();
           //capturo cadena4
           AS400Text tx4   = new AS400Text(15);
           byte[] b4       = parametros[9].getOutputData();
           String cadena4  =(String)tx4.toObject(b4);
           cadena4=cadena4.trim();
           //capturo cadena5
           AS400Text tx5   = new AS400Text(15);
           byte[] b5       = parametros[10].getOutputData();
           String cadena5  =(String)tx5.toObject(b5);
           cadena5=cadena5.trim();
           
           AS400Text tx6   = new AS400Text(2);
           byte[] b6       = parametros[11].getOutputData();
           String cadena6  =(String)tx6.toObject(b6);
           cadena6=cadena6.trim();  
           
           //credito
           /*AS400Text tx7   = new AS400Text(2);
           byte[] b7       = parametros[12].getOutputData();
           String cadena7  =(String)tx7.toObject(b7);
           cadena7=cadena7.trim();        */
           
           sadc2.disconnectAllServices();
           
           if (cadena6.equals("00")) {
               String Rendimiento = String.valueOf(Double.parseDouble(cadena1)/100);
               String saldoAVA = String.valueOf(Double.parseDouble(cadena2)/100);
               String saldoObligatorio = String.valueOf(Double.parseDouble(cadena3)/100);
               String saldoAVE = String.valueOf(Double.parseDouble(cadena4)/100);
               String SaldoTotal = String.valueOf(Double.parseDouble(cadena5)/100);
               //crdito
               //String Credito = String.valueOf(Double.parseDouble(cadena7)/100);
               String Credito = "0";
               
               resul = saldoAVA + ";" + saldoAVE+ ";" + Rendimiento+ ";" + saldoObligatorio+ ";" + SaldoTotal + ";" + Credito;
               return  resul;
            }
            else
            {
                 resul = "ERROR";
                 if(cadena6.equals("01")) { resul = resul + "-Tipo Nit/CC Afiliado no existe o está en blanco";}
                 if(cadena6.equals("02")) { resul = resul + "-Contract Number no existe o está en blanco";}
                 if(cadena6.equals("03")) { resul = resul + "-Product ID =  Blank";}
                 if(cadena6.equals("04")) { resul = resul + "-Tipo de fecha <> ‘F’ o ‘P’";}
                 if(cadena6.equals("05")) { resul = resul + "-Effective Date = Blank";}
                sadc2.disconnectAllServices();
                AS400Message[] messagelist = programa.getMessageList();
                    for (int i = 0; i < messagelist.length; ++i) {
                        resul = resul + " " + messagelist[i].toString();
                    }
                return resul;
              }
          }
           return resul;
       }
       catch(Exception ex){
          return "ERROR" + ex.toString();
        }//EN FUNCIONES AS400(Saldos por Contrato) SE PRESENTO EL SIGUIENTE ERROR: "+ex
      }

        public static String TBFL_Saldo_Credito(String producto, String tipo_id_afiliado, String id_afiliado, String ruta,
                                                      String sistema, String usuario, String password)
         //Esta función devuelve desde as400 el valor de los creditos cargados que tien el afiliado
          {
           try
           {    
             //realizo la conexión al sistema sadc2 con un userid y un password
             //estos parametros serán leidos desde tbrefencias desde taxb
             //AS400 sadc2 = new AS400(sistema);sadc2.setUserId(usuario);sadc2.setPassword(password);      
             AS400 sadc2 = new AS400(sistema,usuario,password);      
             //declaro y defino la lista de parametros, aquí configuro tanto los de entrada como los de salida
             //los parametros de entrada serán dummy(1),producto(8),tipo_id_afiliado(1), id_afiliado(12),     
             //los parametros de salida serán cadena1     
             ProgramParameter[] parametros = new ProgramParameter[5];
                             
             //parametros de entrada
             AS400Text t1         = new AS400Text(1);
             byte[] by1           = t1.toBytes(" ");
             parametros[0]        = new ProgramParameter(by1);
               
             AS400Text t2         = new AS400Text(8);
             byte[] by2           = t2.toBytes(producto);
             parametros[1]        = new ProgramParameter(by2);
               
             AS400Text t3         = new AS400Text(1);
             byte[] by3           = t3.toBytes(tipo_id_afiliado);
             parametros[2]        = new ProgramParameter(by3);                  
               
             AS400Text t4         = new AS400Text(12);
             byte[] by4           = t4.toBytes(id_afiliado);
             parametros[3]        = new ProgramParameter(by4);                
             
             //parametros de salida
             parametros[4]        = new ProgramParameter(17);
             
             String resul="0";
               
             //realizo llamado al programa as400
             ProgramCall programa = new ProgramCall(sadc2,ruta+"A2WXXFR.PGM",parametros);     
             if(programa.run())
              {
               //capturo cadena1
               AS400Text tx1   = new AS400Text(17);
               byte[] b1       = parametros[4].getOutputData();
               String cadena1  = (String)tx1.toObject(b1);
               cadena1=cadena1.trim();
               //capturo cadena2           
               sadc2.disconnectAllServices();
               resul = ""+ (Double.parseDouble(cadena1)/100);
               return  resul;
               }
           }
           catch(Exception ex){
              return "ERROR" + ex.toString();
            }//EN FUNCIONES AS400(Saldos por Contrato) SE PRESENTO EL SIGUIENTE ERROR: "+ex
           return "";
          }
    //**************************************************************************************************************************************
   
    public static String TBFL_Reporte_CEI_OBLIG(String fecha_consulta, String ruta, String sistema, String usuario, String password)
    //Esta función Genera el Reporte del CEI de los retiros que se van a procesar
     {
      try
      {    
        //realizo la conexión al sistema sadc2 con un userid y un password
        //estos parametros serán leidos desde tbrefencias desde taxb
        //AS400 sadc2 = new AS400(sistema);sadc2.setUserId(usuario);sadc2.setPassword(password);      
        AS400 sadc2 = new AS400(sistema,usuario,password);      
        //declaro y defino la lista de parametros, aquí configuro tanto los de entrada como los de salida
        //los parametros de entrada serán dummy(1),producto(8),tipo_fecha(1), fecha_consulta(8),tipo_id_afiliado(1), id_afiliado(12),     
        //Parametro Entrada salida Errores_proceso(2)  
        //los parametros de salida serán cadena1,cadena2,cadena3,cadena4 y cadena5.     
        ProgramParameter[] parametros = new ProgramParameter[3];
                        
        //parametros de entrada
        AS400Text t1         = new AS400Text(1);
        byte[] by1           = t1.toBytes(" ");
        parametros[0]        = new ProgramParameter(by1);
        AS400Text t2         = new AS400Text(8);
        byte[] by2           = t2.toBytes(fecha_consulta);
        parametros[1]        = new ProgramParameter(by2);     
        
        //parametros de salida
        parametros[2]        = new ProgramParameter(1);
        String resul="ERROR";
        //realizo llamado al programa as400
        ProgramCall programa = new ProgramCall(sadc2,ruta+"ACUHXFR.PGM",parametros);     
        //ProgramCall programa = new ProgramCall(sadc2,ruta+"PRBSPOOL.PGM",parametros);     
        if(programa.run())
        {
          //capturo Respuesta
          AS400Text tx1   = new AS400Text(1);
          byte[] b1       = parametros[2].getOutputData();
          String cadena1  = (String)tx1.toObject(b1);
          cadena1=cadena1.trim();       
          
          sadc2.disconnectAllServices();       
          
          return cadena1;    
         }
          return "1";
      }
      catch(Exception ex){
         return "ERROR" + ex.toString();
       }//EN FUNCIONES AS400(REPORTE RETIROS cei) SE PRESENTO EL SIGUIENTE ERROR: "+ex
     }
    //**************************************************************************************************************************************
    
    public static String TBFL_Procesar_Retiros_OBLIG(String fecha_consulta, String ruta, String sistema, String usuario, String password)
    //Esta función Procesa en as400 los retiros
     {
      try
      {    
        //realizo la conexión al sistema sadc2 con un userid y un password
        //estos parametros serán leidos desde tbrefencias desde taxb
        //AS400 sadc2 = new AS400(sistema);sadc2.setUserId(usuario);sadc2.setPassword(password);      
        AS400 sadc2 = new AS400(sistema,usuario,password);      
        //declaro y defino la lista de parametros, aquí configuro tanto los de entrada como los de salida
        //los parametros de entrada serán dummy(1),producto(8),tipo_fecha(1), fecha_consulta(8),tipo_id_afiliado(1), id_afiliado(12),     
        //Parametro Entrada salida Errores_proceso(2)  
        //los parametros de salida serán cadena1,cadena2,cadena3,cadena4 y cadena5.     
        ProgramParameter[] parametros = new ProgramParameter[3];
                        
        //parametros de entrada
        AS400Text t1         = new AS400Text(1);
        byte[] by1           = t1.toBytes(" ");
        parametros[0]        = new ProgramParameter(by1);
        AS400Text t2         = new AS400Text(8);
        byte[] by2           = t2.toBytes(fecha_consulta);
        parametros[1]        = new ProgramParameter(by2);     
        
        //parametros de salida
        parametros[2]        = new ProgramParameter(5);
        String resul="ERROR";
        //realizo llamado al programa as400
        ProgramCall programa = new ProgramCall(sadc2,ruta+"ACUNXFR.PGM",parametros);     
        if(programa.run())
        {
          //capturo Respuesta
          AS400Text tx1   = new AS400Text(1);
          byte[] b1       = parametros[2].getOutputData();
          String cadena1  = (String)tx1.toObject(b1);
          cadena1=cadena1.trim();       
          
          sadc2.disconnectAllServices();       
          
          return cadena1;    
         }
          return "NO OK";
      }
      catch(Exception ex){
         return "ERROR" + ex.toString();
       }//EN FUNCIONES AS400(REPORTE RETIROS cei) SE PRESENTO EL SIGUIENTE ERROR: "+ex
     }          
    //**************************************************************************************************************************************
    
    public static String TBFL_COMISION_ADMIN_OBLIG (String vsistemaoblig, String vlibreriaoblig, String vusuariooblig, String vpasswordoblig, String v_pro, String v_contra){
        
        String Resultado = "0;0";
        
        try
        {
            String AVA = "0";
            String AVE = "0";
            
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
    //**************************************************************************************************************************************       
}
