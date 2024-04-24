package co.oldmutual.taxbenefit.util;

import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.TBCL_Validacion;

import java.sql.SQLException;

import java.util.Hashtable;

import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;
import co.oldmutual.taxbenefit.util.web.WebContract;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DataSourceWrapper {
    //tengo la referencia al objeto remoto que necesito
    private static DataSource ds = null;
    private Map initResource;
    
    private static DataSourceWrapper iniCtx; 
    
    private DataSourceWrapper() {
    /*esto se hace por que desde otro contexto diferente al Web no se puede llegar al datasource*/    
    }
    
    private DataSourceWrapper(Map initResource) {
        Context ctx = null ;
        this.initResource   =   initResource;
        try{
            Hashtable props = new Hashtable () ;
            props.put( Context.INITIAL_CONTEXT_FACTORY, (String)this.initResource.get(WebContract.INITIAL_CONTEXT_FACTORY) ) ;
            props.put( Context.PROVIDER_URL,(String)this.initResource.get(WebContract.PROVIDER_URL) );  
            
            ctx = new InitialContext( props );
            System.out.println("Se obtiene el contxto");
            ds = (DataSource) ctx.lookup ((String)this.initResource.get(WebContract.JNDI)); 
            System.out.println("Se obtiene el Datasource :: " + ds.toString());   
        }
        catch ( NamingException ne ) {
                System.out.println("Error Jndi");
                ne.printStackTrace( );
        }        
        finally{
                try{                    
                    if(ctx != null){
                        ctx.close();
                    }
                }
                catch ( NamingException ne ) {
                        // Unable to obtain InitialContext.
                        // handle the NamingException here.
                        System.out.println("Error cerrando JNDI");
                        ne.printStackTrace( );
                }
        }
    }
    
    private static void createInstance(Map initResource) {
        synchronized(DataSourceWrapper.class) {
            //garantizo que tiene que existir un acceso al datasource, esto me permite bajar el objeto datasource en caliente
            if (iniCtx == null || ds == null) {
                iniCtx = new DataSourceWrapper(initResource);
            }
        }
    }

    
    public static DataSourceWrapper getInstance(Map initResource){
        if(iniCtx == null){
            createInstance(initResource);
        }
        return iniCtx;
    }
    
    public static DataSourceWrapper getInstance(){
        if(iniCtx == null){/*esto solo es llamado desde un entorno diferente al Web. existen unas clases que son desplegadas en Oracle y otrsas desde un cliente no web*/
            iniCtx = new DataSourceWrapper();
        }
        return iniCtx;
    }
    
    public java.sql.Connection getConnection() throws SQLException{
        System.out.println("Se abre coneccion con el pool");
        if(initResource == null)/*Si no se ha creado un mapa de configuracion es que no estoy en un entorno valido y creo una conecxion normal*/ {
            try {/*Esto en un futuro se debe poder cambiar*/
                Class.forName("oracle.jdbc.driver.OracleDriver");
                TBCL_Validacion  i_valusu   = new TBCL_Validacion();
                String[] v_valusu            = new String[3];
                v_valusu                     = i_valusu.TBFL_ValidarUsuario();
                Connection  ret             =   DriverManager.getConnection(v_valusu[0],v_valusu[1],v_valusu[2]);
                ret.setAutoCommit(false);
                return ret;
            } catch (ClassNotFoundException e) {
                // TODO: Add catch code
                e.printStackTrace();
            }
            
        }
        if(ds == null){// Si el objeto pierde la referencia por alguna razon intento de nuevo.
            createInstance(initResource);
        }
        Connection  ret             =   ds.getConnection();/*Esta es una conecxion de un pool*/
        System.out.println(ret.getAutoCommit());
        ret.setAutoCommit(false);
        return ret;
    }
    
    protected void closeDatasource(){
        ds  = null;//me bajo la referencia para limpiar memoria
    }
    
    public static void closeConnection(java.sql.Connection conn) throws SQLException{
        System.out.println("Se cierra coneccion con el pool");
        if(conn != null && !conn.isClosed()){
            conn.commit();
            conn.close();
        }
    }
    
    public static void closeStatement(Statement stm) throws SQLException{
        if(stm != null && !stm.isClosed()){
            stm.close();
        }
    }
    
    public static void closeStatement(ResultSet rs) throws SQLException{
        if(rs != null && !rs.isClosed()){
            rs.close();
        }
    }
    
}
