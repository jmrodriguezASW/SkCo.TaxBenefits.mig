/*@lineinfo:filename=TBCL_Esquema*//*@lineinfo:user-code*//*@lineinfo:1^1*/package TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS;

import sqlj.runtime.*;
import sqlj.runtime.ref.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import sqlj.runtime.ref.DefaultContext ;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
/**Clase que  dibuja el esquema de retiro, dependiendo del tipo de esquema escogido por el usuario*/
public class TBCL_Esquema extends HttpServlet
{
 /**Itrator esquema retiro default producto*/
 /*@lineinfo:generated-code*//*@lineinfo:17^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

class i_producto
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_producto(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    PRC_REF_METODO_ORDENNdx = findColumn("PRC_REF_METODO_ORDEN");
    PRC_REF_METODO_BENEFICIONdx = findColumn("PRC_REF_METODO_BENEFICIO");
    PRC_REF_METODO_PENALIZACIONNdx = findColumn("PRC_REF_METODO_PENALIZACION");
    PRC_REF_METODO_CUENTANdx = findColumn("PRC_REF_METODO_CUENTA");
    PRC_REF_NATURALEZANdx = findColumn("PRC_REF_NATURALEZA");
    PRC_RESPETAR_NATURALEZANdx = findColumn("PRC_RESPETAR_NATURALEZA");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String PRC_REF_METODO_ORDEN()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRC_REF_METODO_ORDENNdx);
  }
  private int PRC_REF_METODO_ORDENNdx;
  public String PRC_REF_METODO_BENEFICIO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRC_REF_METODO_BENEFICIONdx);
  }
  private int PRC_REF_METODO_BENEFICIONdx;
  public String PRC_REF_METODO_PENALIZACION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRC_REF_METODO_PENALIZACIONNdx);
  }
  private int PRC_REF_METODO_PENALIZACIONNdx;
  public String PRC_REF_METODO_CUENTA()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRC_REF_METODO_CUENTANdx);
  }
  private int PRC_REF_METODO_CUENTANdx;
  public String PRC_REF_NATURALEZA()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRC_REF_NATURALEZANdx);
  }
  private int PRC_REF_NATURALEZANdx;
  public String PRC_RESPETAR_NATURALEZA()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(PRC_RESPETAR_NATURALEZANdx);
  }
  private int PRC_RESPETAR_NATURALEZANdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:17^210*/
 /**Itrator esquema retiro default contrato*/
 /*@lineinfo:generated-code*//*@lineinfo:19^2*/

//  ************************************************************
//  SQLJ iterator declaration:
//  ************************************************************

class i_contrato
extends sqlj.runtime.ref.ResultSetIterImpl
implements sqlj.runtime.NamedIterator
{
  public i_contrato(sqlj.runtime.profile.RTResultSet resultSet)
    throws java.sql.SQLException
  {
    super(resultSet);
    CON_REF_METODO_ORDENNdx = findColumn("CON_REF_METODO_ORDEN");
    CON_REF_METODO_BENEFICIONdx = findColumn("CON_REF_METODO_BENEFICIO");
    CON_REF_METODO_PENALIZACIONNdx = findColumn("CON_REF_METODO_PENALIZACION");
    CON_REF_METODO_CUENTANdx = findColumn("CON_REF_METODO_CUENTA");
    CON_REF_NATURALEZANdx = findColumn("CON_REF_NATURALEZA");
    CON_RESPETAR_NATURALEZANdx = findColumn("CON_RESPETAR_NATURALEZA");
    m_rs = (oracle.jdbc.OracleResultSet) resultSet.getJDBCResultSet();
  }
  private oracle.jdbc.OracleResultSet m_rs;
  public String CON_REF_METODO_ORDEN()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_REF_METODO_ORDENNdx);
  }
  private int CON_REF_METODO_ORDENNdx;
  public String CON_REF_METODO_BENEFICIO()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_REF_METODO_BENEFICIONdx);
  }
  private int CON_REF_METODO_BENEFICIONdx;
  public String CON_REF_METODO_PENALIZACION()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_REF_METODO_PENALIZACIONNdx);
  }
  private int CON_REF_METODO_PENALIZACIONNdx;
  public String CON_REF_METODO_CUENTA()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_REF_METODO_CUENTANdx);
  }
  private int CON_REF_METODO_CUENTANdx;
  public String CON_REF_NATURALEZA()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_REF_NATURALEZANdx);
  }
  private int CON_REF_NATURALEZANdx;
  public String CON_RESPETAR_NATURALEZA()
    throws java.sql.SQLException
  {
    return (String)m_rs.getString(CON_RESPETAR_NATURALEZANdx);
  }
  private int CON_RESPETAR_NATURALEZANdx;
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:19^211*/
 /**Variable tipo iterator i_producto*/
 i_producto v_producto;
 /**Variable tipo iterator i_contrato*/
 i_contrato v_contrato;

 /**Procedimiento  que consulta los esquemas de retiro y los dibuja como página de respuesta*/
 public void TBPL_Esquema(HttpSession session,HttpServletRequest request,PrintWriter out,String nuevaCadena )
 {
  /**Instancias de clase*/
  //STBCL_GenerarBaseHTML i_pagina = new STBCL_GenerarBaseHTML;/**Instancia de la clase TBCL_GenerarBaseHTML*/
  //TBCL_ConexionSqlj    i_conexion = new TBCL_ConexionSqlj();/**Instancia de la clase TBCL_ConexionSqlj*/
  /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_ValidarUsuario de la clase TBCL_Validacion, no es necesaria la instancia nueva*/ 
 //TBCL_Validacion i_valusu = new TBCL_Validacion(); 
 //TBCL_Validacion  i_valusu = new TBCL_Validacion()
  try
  {
   String[] v_valusu = new String[3];
   v_valusu=TBCL_Validacion.TBFL_ValidarUsuario();
   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   DefaultContext ctx6 = new DefaultContext(v_valusu[0],v_valusu[1],v_valusu[2],false);
   DefaultContext.setDefaultContext(ctx6);
   /**Conectar a la base de datos*/
   //i_conexion.TBCL_ConexionBaseDatos();

   /**Definicion de variables*/
   /**Tipo boolean*/
   boolean v_actualizar = false;/**Variable que indica si se dibuja opcion de actualizar esquema default contrato*/
   double v_cargomil   = 0;/**Variable para el porcentaje de4x1000*/

   /**Tipo string*/
   String v_esquema = "";/**Variable Esquema de retiro escogido por el usuario*/
   String v_maximo2 = "";/**Variable maximo de fondos*/
   String v_fondo   = "";/**Variable Distribución de fondo*/

   String v_nat_0   = "";/**Variable chequear Rendimientos*/
   String v_nat_1   = "";/**Variable chequear capital*/
   String v_nat_2   = "";/**Variable chequear proporcional*/

   String v_ord_0   = "";/**Variable chequear fifo*/
   String v_ord_1   = "";/**Variable chequear lifo*/
   String v_ord_2   = "";/**Variable chequear seleccionado*/

   String v_ben_0   = "";/**Variable chequear con beneficio*/
   String v_ben_1   = "";/**Variable chequear sin beneficio*/
   String v_ben_2   = "";/**Variable chequear no aplica*/

   String v_pen_0   = "";/**Variable chequear con penalizacion*/
   String v_pen_1   = "";/**Variable chequear sin penalización*/
   String v_pen_2   = "";/**Variable chequear no aplica*/

   String v_cuen_0  = "";/**Variable chequear con cuenta contingente*/
   String v_cuen_1  = "";/**Variable chequear sin cuenta contingente*/
   String v_cuen_2  = "";/**Variable chequear no aplica*/

   String v_res_0   = "";/**Variable chequear tomar de la otra naturaleza si*/
   String v_res_1   = "";/**Variable chequear tomar de la otra naturaleza no*/

   String i_orden   = "";/**Variable método orden producto o contrato*/
   String i_ben     = "";/**Variable método beneficio producto o contrato*/
   String i_pen     = "";/**Variable método penalización producto o contrato*/
   String i_cuen    = "";/**Variable método cuenta contingente producto o contrato*/
   String i_nat     = "";/**Variable método  naturaleza producto o contrato*/
   String i_respetar= "";/**Variable método respetar naturaleza*/

   String v_valorcli= "";/**Valor retirado de cada fondo*/
   String v_porcencli = "";/**Porcentaje retirado de cada fondo*/

   String v_contra  = "";/**Variable número del contrato*/
   String v_pro     = "";/**Variable código producto*/
   String v_nom     = "";/**Variable nombre afiliado*/
   String v_ape     = "";/**Variable apellidos afiliado*/
   String v_reftipo = "";/**Variable  tipo transacción*/
   String v_refclase= "";/**Variable clase transacción*/
   String v_pintar  = "";/**Variable dibujar cabeza página de respuesta*/
   String v_pie     = "";/**Variable dibujar pirgina de respuesta*/
   String v_idsaro  = "";/**Variable id del evento de saro*/
   
   /**Agregados para el manejo de datos de terceros 2009/09/14 */
   String v_doc_ter    = "";/**Variable con el documento del tercero*/
   String v_tipodoc_ter= "";/**Variable con el tipo de documento del tercero*/
   String v_nomb_terc  = "";/**Variable con el nombre del tercero*/
   String v_apell_terc = "";/**Variable con el apellido del tercero*/
   String v_esTercero  = "";/**Variable para saber si es una operación a tercero*/
   
   
   /**Verificar que las variables de session no expiren*/
   if((java.lang.String)session.getAttribute("s_contrato") != null ||(java.lang.String)session.getAttribute("s_producto") != null)
   {
    /**Capturar variables de session*/
    v_contra  = (java.lang.String)session.getAttribute("s_contrato");/**Tomar contrato*/
    v_pro     = (java.lang.String)session.getAttribute("s_producto");/**Tomar producto*/
    v_nom     = (java.lang.String)session.getAttribute("s_nombres");/**Tomar nombre del afiliado*/
    v_ape     = (java.lang.String)session.getAttribute("s_apellidos");/**Tomar apellido del afiliado*/
    v_reftipo = (java.lang.String)session.getAttribute("s_tipotran");//**Tomar tipo de transacción*/
    v_refclase= (java.lang.String)session.getAttribute("s_clasetran");//**Tomar clase de transacción*/
    v_fondo   =(java.lang.String)session.getAttribute("s_fondo");
    
    /**Modificado 2009/10/27 Variable Id Saro*/
    try{
      if((java.lang.String)session.getAttribute("v_idsaro")!= null){
          v_idsaro = (java.lang.String)session.getAttribute("v_idsaro");
          session.removeAttribute("v_idsaro");
          session.setAttribute("v_idsaro",v_idsaro);
      }
    }catch (Exception e)  { e.printStackTrace(); }
    
    /**Capturar variables para giros a terceros */
    try {  
          v_esTercero =(java.lang.String)session.getAttribute("esTercero"); 
          session.setAttribute("esTercero",v_esTercero); 
          if (v_esTercero.equals("S")){
              try { v_doc_ter = (java.lang.String)session.getAttribute("v_doc_ter"); } catch (Exception e) { e.printStackTrace(); }
              try { v_tipodoc_ter = (java.lang.String)session.getAttribute("v_tipodoc_ter"); } catch (Exception e) { e.printStackTrace(); }
              try { v_nomb_terc = (java.lang.String)session.getAttribute("v_nomb_terc"); } catch (Exception e) { e.printStackTrace(); }
              try { v_apell_terc = (java.lang.String)session.getAttribute("v_apell_terc"); } catch (Exception e) { e.printStackTrace(); }
              
               if ((!v_doc_ter.trim().equals("")&&!v_tipodoc_ter.trim().equals("")&&
                    !v_apell_terc.trim().equals(""))){
                 session.removeAttribute("v_doc_ter");
                 session.setAttribute("v_doc_ter",v_doc_ter);
                 session.removeAttribute("v_tipodoc_ter");
                 session.setAttribute("v_tipodoc_ter",v_tipodoc_ter);
                 session.removeAttribute("v_nomb_terc");
                 session.setAttribute("v_nomb_terc",v_nomb_terc);
                 session.removeAttribute("v_apell_terc");
                 session.setAttribute("v_apell_terc",v_apell_terc);
               }
          } 
     }catch (Exception e)  { e.printStackTrace(); }
    /**/
     
    /**Capturar esquema de retiro escogido*/
     String v_valorret = ( java.lang.String)session.getAttribute("s_valor");

    try { v_esquema = request.getParameter("v_esquema"); } catch (Exception e) { e.printStackTrace(); }
    /**Capturar maximo fondos*/
    try { v_maximo2 = request.getParameter("v_maximo2"); } catch (Exception e) { e.printStackTrace(); }
    /**Variable de session maximo fondos*/
    session.removeAttribute("s_maximo2");
    session.setAttribute("s_maximo2",v_maximo2);

    /**Capturar valor retirado de cada fondo(distribución prorrata y valor)*/
    if(v_fondo.equals("1")||v_fondo.equals("0"))
    {
     for ( int i=1;i<= new Integer(v_maximo2).intValue();i++)
     {
      try { v_valorcli = request.getParameter("valor"+i); } catch (Exception e) { e.printStackTrace(); }
      /**Variable de session valor retirado de cada fondo*/
      if(v_fondo.equals("1") && v_esTercero.equals("S")){
        /*@lineinfo:generated-code*//*@lineinfo:169^9*/

//  ************************************************************
//  #sql { SELECT NVL(REF_VALOR,0)
//                     
//                     FROM tbreferencias
//                    WHERE REF_CODIGO = 'STC006'
//             };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  oracle.jdbc.OracleResultSet __sJT_rs = null;
  try {
   String theSqlTS = "SELECT NVL(REF_VALOR,0)\n                    \n                   FROM tbreferencias\n                  WHERE REF_CODIGO = 'STC006'";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Esquema",theSqlTS);
   if (__sJT_ec.isNew())
   {
     __sJT_st.setFetchSize(2);
   }
   // execute query
   __sJT_rs = __sJT_ec.oracleExecuteQuery();
   if (__sJT_rs.getMetaData().getColumnCount() != 1) sqlj.runtime.error.RuntimeRefErrors.raise_WRONG_NUM_COLS(1,__sJT_rs.getMetaData().getColumnCount());
   if (!__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_NO_ROW_SELECT_INTO();
   // retrieve OUT parameters
   v_cargomil = __sJT_rs.getDouble(1); if (__sJT_rs.wasNull()) throw new sqlj.runtime.SQLNullException();
   if (__sJT_rs.next()) sqlj.runtime.error.RuntimeRefErrors.raise_MULTI_ROW_SELECT_INTO();
  } finally { if (__sJT_rs!=null) __sJT_rs.close(); __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:173^11*/
        if (v_cargomil>0) {
        session.removeAttribute("s_valorcli"+i+"");
        session.setAttribute("s_valorcli"+i+"",String.valueOf(Double.parseDouble(v_valorcli)/v_cargomil));
        }
        else {
          session.removeAttribute("s_valorcli"+i+"");
          session.setAttribute("s_valorcli"+i+"",v_valorcli);  
        }
      }
      else
      {
        session.removeAttribute("s_valorcli"+i+"");
        session.setAttribute("s_valorcli"+i+"",v_valorcli);  
      }
      
     }
    }
    else/**Capturar porcentaje retirado de cada fondo(distribución porcentaje)*/
    {
     if(v_fondo.equals("2"))
     {
      for ( int i=1;i<= new Integer(v_maximo2).intValue();i++)
      {
       try { v_porcencli = request.getParameter("porcen"+i); } catch (Exception e) { e.printStackTrace(); }
       /**Variable de session porcentaje retirado de cada fondo*/
       session.removeAttribute("s_porcencli"+i+"");
       session.setAttribute("s_porcencli"+i+"",v_porcencli);
      }
     }
    }
    /**Si el esquema escogido es el default de skandia*/
    if(v_esquema.equals("0"))
    {


        v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Calcular Solicitud de Retiro","Calcular Solicitud de Retiro","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_CalcularRetiro","",true,"modulo_retiros.js","return validar_retiro(this)");
        out.println(""+v_pintar+"");
        /*Cambio para manejo de referencia unica 2009/03/30 MOS */
        String v_contrato_unif = "";
        /*@lineinfo:generated-code*//*@lineinfo:213^9*/

//  ************************************************************
//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_pro,:v_contra)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Esquema",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_pro);
   __sJT_st.setString(3,v_contra);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_contrato_unif = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:213^82*/
        out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
        out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
        out.println("<br>");
        out.println("<br>");
         out.println("<center><font face='Verdana, Arial, Helvetica, sans-serif' size='2' color='#000000'>¿Desea realizar la solicitud de retiro?</font></center>");
         out.println("<br>");
         out.println("<PRE>");
         out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena +"'>");
         out.println("<center><input type=submit value='Aceptar' name ='aceptar'><input type=button value='Regresar' onclick=' history.go(-1)' name = 'regresar'><input type=button value='Cancelar' onclick='history.go(-6)' name ='cancelar'></center>");

         String v_piemin2 = STBCL_GenerarBaseHTML.TBFL_PIE;
         out.println("<br>");
         out.println("<br>");
         out.println(""+v_piemin2+"");
         out.close();
      
     /**Consultar esquema default eskandia en tbproductos_conceptos*/
/*     #sql v_producto = {SELECT PRC_REF_METODO_ORDEN
                              ,PRC_REF_METODO_BENEFICIO
                              ,PRC_REF_METODO_PENALIZACION
                              ,PRC_REF_METODO_CUENTA
                              ,PRC_REF_NATURALEZA
                              ,PRC_RESPETAR_NATURALEZA
                         FROM tbproductos_conceptos
                        WHERE prc_pro_codigo                = :v_pro
                          AND PRC_COT_REF_TRANSACCION       = 'STR001'
                          AND PRC_COT_REF_TIPO_TRANSACCION  = :v_reftipo
                          AND PRC_COT_REF_CLASE_TRANSACCION = :v_refclase
                        };

     while(v_producto.next())
     {
      i_orden   = v_producto.PRC_REF_METODO_ORDEN();
      i_ben     = v_producto.PRC_REF_METODO_BENEFICIO();
      i_pen     = v_producto.PRC_REF_METODO_PENALIZACION();
      i_cuen    = v_producto.PRC_REF_METODO_CUENTA ();
      i_nat     = v_producto.PRC_REF_NATURALEZA();
      i_respetar= v_producto.PRC_RESPETAR_NATURALEZA();
     }
     v_producto.close();
     /**Chequear Respetar naturaleza*/
/*     if(i_respetar.equals("N"))
     {
      v_res_0 ="CHECKED";
     }
     else if (i_respetar.equals("S"))
          {
           v_res_1 ="CHECKED";
          }

     /**Chequear naturaleza del retiro*/
/*     if(i_nat.equals("SNR001"))
     {
      v_nat_0 ="CHECKED";
     }
     else if (i_nat.equals("SNR002"))
          {
           v_nat_1 ="CHECKED";
          }
          else if (i_nat.equals("SNR003"))
               {
                v_nat_2 ="CHECKED";
               }
     /**Chequear método orden*/
/*     if(i_orden.equals("SMO001"))
     {
      v_ord_0 ="CHECKED";
     }
     else if (i_orden.equals("SMO002"))
          {
           v_ord_1 ="CHECKED";
          }
          else if (i_orden.equals("SMO003"))
               {
                v_ord_2 ="CHECKED";
               }
     /**Chequear método beneficio*/
/*     if(i_ben.equals("SMB001"))
     {
      v_ben_0 ="CHECKED";
     }
     else if (i_ben.equals("SMB002"))
          {
           v_ben_1 ="CHECKED";
          }
          else if (i_ben.equals("SMB003"))
               {
                v_ben_2 ="CHECKED";
               }
     /**Chequear  método penalización*/
/*     if(i_pen.equals("SMP001"))
     {
      v_pen_0 ="CHECKED";
     }
     else if (i_pen.equals("SMP002"))
          {
           v_pen_1 ="CHECKED";
          }
          else if (i_pen.equals("SMP003"))
               {
                v_pen_2 ="CHECKED";
               }
     /**Chequear método cuenta*/
/*     if(i_cuen.equals("SMC001"))
     {
      v_cuen_0 ="CHECKED";
     }
     else if (i_cuen.equals("SMC002"))
          {
           v_cuen_1 ="CHECKED";
          }
          else if (i_cuen.equals("SMC003"))
               {
                v_cuen_2 ="CHECKED";
               }
*/
    }
    else if(v_esquema.equals("1"))/**Si el esquema escogido es el default contrato*/
         {
          /**Consultar esquema del contrato*/
          /*@lineinfo:generated-code*//*@lineinfo:334^11*/

//  ************************************************************
//  #sql v_contrato = { SELECT CON_REF_METODO_ORDEN
//                                     ,CON_REF_METODO_BENEFICIO
//                                     ,CON_REF_METODO_PENALIZACION
//                                     ,CON_REF_METODO_CUENTA
//                                     ,CON_REF_NATURALEZA
//                                     ,CON_RESPETAR_NATURALEZA
//                                 FROM tbcontratos
//                                WHERE CON_NUMERO = :v_contra
//                                  AND CON_PRO_CODIGO = :v_pro
//                                };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "SELECT CON_REF_METODO_ORDEN\n                                   ,CON_REF_METODO_BENEFICIO\n                                   ,CON_REF_METODO_PENALIZACION\n                                   ,CON_REF_METODO_CUENTA\n                                   ,CON_REF_NATURALEZA\n                                   ,CON_RESPETAR_NATURALEZA\n                               FROM tbcontratos\n                              WHERE CON_NUMERO =  :1  \n                                AND CON_PRO_CODIGO =  :2 ";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Esquema",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,v_contra);
   __sJT_st.setString(2,v_pro);
   // execute query
   v_contrato = new TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Esquema.i_contrato(new sqlj.runtime.ref.OraRTResultSet(__sJT_ec.oracleExecuteQuery(),__sJT_st,"2TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Esquema",null));
  } finally { __sJT_ec.oracleCloseQuery(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:343^30*/

          while(v_contrato.next())
          {
           i_orden    = v_contrato.CON_REF_METODO_ORDEN();
           i_ben      = v_contrato.CON_REF_METODO_BENEFICIO();
           i_pen      = v_contrato.CON_REF_METODO_PENALIZACION();
           i_cuen     = v_contrato.CON_REF_METODO_CUENTA();
           i_nat      = v_contrato.CON_REF_NATURALEZA();
           i_respetar =  v_contrato. CON_RESPETAR_NATURALEZA();
          }
          v_contrato.close();
          /**Chequear naturaleza retiro*/
          v_actualizar = true;
          try
          {
           if(i_nat.equals("SNR001") )
           {
            v_nat_0 ="CHECKED";
           }
           else if (i_nat.equals("SNR002"))
                {
                 v_nat_1 ="CHECKED";
                }
                else if (i_nat.equals("SNR003"))
                     {
                      v_nat_2 ="CHECKED";
                     }

          }
          catch(Exception ex){}
          /**Chequear método orden*/
          try
          {
           if(i_orden .equals("SMO001"))
           {
            v_ord_0 ="CHECKED";
           }
           else if (i_orden .equals("SMO002"))
                {
                 v_ord_1 ="CHECKED";
                }
                else if (i_orden .equals("SMO003"))
                     {
                      v_ord_2 ="CHECKED";
                     }

          }
          catch(Exception ex){   }
          /**Chequear método beneficio*/
          try
          {
           if(i_ben.equals("SMB001"))
           {
            v_ben_0 ="CHECKED";
           }
           else if (i_ben.equals("SMB002"))
           {
            v_ben_1 ="CHECKED";
           }
           else if (i_ben.equals("SMB003"))
                {
                 v_ben_2 ="CHECKED";
                }
          }
          catch(Exception ex){}
          /**Chequear método penalización*/
          try
          {
           if(i_pen.equals("SMP001"))
           {
            v_pen_0 ="CHECKED";
           }
           else if (i_pen.equals("SMP002"))
                {
                 v_pen_1 ="CHECKED";
                }
                else if (i_pen.equals("SMP003"))
                {
                 v_pen_2 ="CHECKED";
                }
          }
          catch(Exception ex){ }
          /**Chequear método cuenta contingente*/
          try
          {
           if(i_cuen.equals("SMC001"))
           {
            v_cuen_0 ="CHECKED";
           }
           else if (i_cuen.equals("SMC002"))
                {
                 v_cuen_1 ="CHECKED";
                }
                else if (i_cuen.equals("SMC003"))
                     {
                      v_cuen_2 ="CHECKED";
                     }
          }
          catch(Exception ex){}
          /**Chequear respetar naturaleza*/
          try
          {
           if(i_respetar.equals("S"))
           {
            v_res_0 ="CHECKED";
           }
           else if (i_respetar.equals("N"))
                {
                 v_res_1 ="CHECKED";
                }
          }
          catch(Exception ex){ }
         }
         else if(v_esquema.equals("2"))/**Si el esquema es personalizado*/
              {
               v_nat_2 ="CHECKED";
               v_ord_1 ="CHECKED";
               v_ben_2 ="CHECKED";
               v_pen_2 ="CHECKED";
               v_cuen_2 ="CHECKED";
               v_res_1 ="CHECKED";
              }

   if(!v_esquema.equals("0"))
   {
      /**Dibujar página de respuesta*/
      v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Esquema de Retiro","TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBS_Distribucion","",true,"modulo_retiros.js","");
      out.println(""+v_pintar+"");
      /*Cambio para manejo de referencia unica 2009/03/30 MOS */
      String v_contrato_unif = "";
      /*@lineinfo:generated-code*//*@lineinfo:474^7*/

//  ************************************************************
//  #sql v_contrato_unif = { values(TBFBD_obtener_ref_unica(:v_pro,:v_contra)) };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = sqlj.runtime.ref.DefaultContext.getDefaultContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := TBFBD_obtener_ref_unica( :2  , :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3TBPKT_MODULO_RETIROS.TBPKT_SOLICITUD_RETIROS.TBCL_Esquema",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,v_pro);
   __sJT_st.setString(3,v_contra);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   v_contrato_unif = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:474^80*/
      out.println("<FONT color =#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Producto</b> "+v_pro+"    <b>Contrato</b>"+v_contrato_unif+" </center></font>");
      out.println("<FONT color=#000000 face='Verdana, Arial, Helvetica, sans-serif' size=1><CENTER><b>Nombres</b>  "+v_nom+"  <b> Apellidos </b>"+v_ape+" </CENTER></font>");
      out.println("<br>");
      /*out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");*/
      /*out.println("<tr><td class=\"td11\" colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Naturaleza de Retiro</b></font></center></font></td></tr>");*/
      /*/out.println("<tr><td ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Capital&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=naturaleza value='SNR002' "+v_nat_1+" ></font></center></td>");
      out.println("<td><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Rendimiento&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=naturaleza value='SNR001' "+v_nat_0+"  ></font></center></td>");*/
      /*out.println("<td ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Proporcional&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=naturaleza value='SNR003' "+v_nat_2+" ><input type=hidden name=naturaleza value='sin'></font><center></td></tr>");*/
      out.println("<input type=hidden name=naturaleza  value='SNR003'> <input type=hidden name=respetar value='N'> ");
      //out.println("</table></center>");
      /*out.println("<br>");
      out.println("<center><table width='100%' border='0' cellspacing='0' cellpadding='0'>");
      out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Aprueba sacar de la otra naturaleza</font></td>");
      out.println("<td><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><input type=radio name=respetar value='N' "+v_res_0+"  >Si</font></center></td>");
      out.println("<td ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><input type=radio name=respetar value='S'  "+v_res_1+">No<input type=hidden name=respetar value='sin'></font><center></td></tr>");
      out.println("</table></center>");
      out.println("<font face='Verdana, Arial, Helvetica, sans-serif' size='0.1' color='#000000'><b>Nota </b>Aplica para naturaleza capital o rendimiento</font>");
      out.println("<br>");
      out.println("<br>");*/
      out.println("<!--metodologia-->");
      out.println("<center><table width='100%' border='1' cellspacing='0' cellpadding='0'>");
      out.println("<tr><td class=\"td11\" colspan= 4><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1'><font color='#FFFFFF' ><b>Metodología de Retiro</b></font></center></font></td></tr>");
      out.println("<tr><td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Orden</b></font></center></td>");
      out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Beneficio Tributario</b></font></center></td>");
      out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Penalización</b></font></center></td>");
      out.println("<td class=\"td11\"><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#FFFFFF'><b>Cuenta Contingente</b></font><center></td></tr>");
      out.println("<!--orden-->");
      out.println("<tr><td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Lifo&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=orden value='SMO002' "+v_ord_1+" ></font></td>");
      out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Con Beneficio&nbsp;<input type=radio name=beneficio value='SSMB001' "+v_ben_0+" > </font></td>");
      out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Con Penalizacion<input type=radio name=penalizacion value='SSMP001' "+v_pen_0+" > </font></td>");
      out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Con Cuenta Contingente<input type=radio name=cuenta value='SSMC001' "+v_cuen_0+"></font></td></tr>");
      out.println("<!--beneficio-->");
      out.println("<tr><td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Fifo&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=orden value='SMO001' "+v_ord_0+" ></font></td>");
      out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Sin Beneficio &nbsp;<input type=radio name=beneficio value='NSMB002' "+v_ben_1+" ></font></td>");
      out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Sin Penalizacion&nbsp;<input type=radio name=penalizacion value='NSMP002' "+v_pen_1+"></font></td>");
      out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Sin Cuenta Contingente&nbsp;<input type=radio name=cuenta value='NSMC002' "+v_cuen_1+" ></font></td></tr>");
      out.println("<!--penalizacion-->");
      out.println("<input type=hidden name=v_idsaro value='"+v_idsaro+"' >");
      out.println("<tr><td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Seleccion<input type=radio name=orden value='SMO003' "+v_ord_2+"> </font></td>");
      out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>No Aplica&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp<input type=radio name=beneficio value='XSMB003' "+v_ben_2+" >  </font></td>");
      out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>No Aplica&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=penalizacion value='XSMP003' "+v_pen_2+" ></font></td>");
      out.println("<td><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>No Aplica&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=cuenta value='XSMC003' "+v_cuen_2+" ></font></td></tr>");
      out.println("<input type=hidden name=orden value='sin' ><input type=hidden name=beneficio value='sin' ><input type=hidden name=penalizacion value='sin' ><input type=hidden name=cuenta value='sin' >");
      out.println("</table></center>");
      out.println("<input name= v_retotal type=HIDDEN value='z' size = '9'>");

      out.println("<br>");
      /**Opcion de actualizar default contrato*/
      if(v_actualizar)
      {
       out.println("<center><table width='100%' border='0' cellspacing='0' cellpadding='0'>");
       out.println("<tr><td ><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'>Desea actualizar el esquema de retiro default del contrato</font></td>");
       out.println("<td><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><input type=radio name=v_actualizar value='S' >Si</font></center></td>");
       out.println("<td ><center><font face='Verdana, Arial, Helvetica, sans-serif' size='1' color='#000000'><input type=radio name=v_actualizar value='N' checked>No</font><center></td></tr>");
       out.println("</table></center>");
       out.println("<br>");
      }
      else
      {
       out.println("<input type=hidden name=v_actualizar value='N' >");
      }
      out.println(" <br>");
      out.println("<INPUT ID=cadena NAME=cadena TYPE=hidden VALUE='"+nuevaCadena+"'>");
      out.println("<center><input type=submit value='Aceptar'><input type=button value='Regresar' onclick=' history.go(-1)'><input type=button value='Cancelar' onclick=' history.go(-4)'></center>");
      v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
      out.println("<br>");
      out.println(""+v_pie+"");
      out.close();
     }
     else
     {/**Termina session*/
      v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Error de comunicación no se tiene conexión con el servidor web,por favor intente de nuevo.</center>",false);
      out.println(""+v_pintar+"");
      v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
      out.println("<br>");
      out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-4)'></center>");
      out.println(""+v_pie+"");
      out.close();
     }
     /**Desconectar base de datos*/
     //i_conexion.TBCL_DesconectarBaseDatos();
   }

   ctx6.getConnection().close();
  }
  catch(Exception ex)
  {/**Manejo de errores*/
   String v_pintar="";
   String error = ex.toString();
   if(error.trim().equals("java.sql.SQLException: Io exception: End of TNS data channel") ||  error.trim().equals("java.sql.SQLException: ORA-01034: ORACLE not available"))
   {
    v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>No se tiene comunicación con el servidor de datos  por favor ingrese nuevamente.</center>",false);
   }
   else if (error.trim().equals("java.sql.SQLException: Io exception: Connection reset by peer: socket write error"))
        {
         v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Se reinicio la base de datos por favor ingrese nuevamente.</center>",false);
        }
           else if(error.trim().equalsIgnoreCase("java.sql.SQLException: Closed Connection"))
                {
                 v_pintar = STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Error momentaneo de comunicación con el servidor de datos, por favor intente entrar de nuevo a la opción.</center>",false);
                }
                else if(error.trim().equalsIgnoreCase("java.sql.SQLException:IOEXCEPTION:DESCRIPTOR NOT A SOCKET:SOCKET WRITE ERROR"))
                     {
                      v_pintar =  STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Error momentaneo de comunicación con el servidor Web, por favor intente entrar de nuevo a la opción.</center>",false);
                     }
                  else
                  {
                   v_pintar=    STBCL_GenerarBaseHTML.TBFL_CABEZA("Solicitud de Retiro","Error Esquema de Retiro","","<center>Mensaje de Error :"+ex+".</center>",false);
                  }
   out.println(""+v_pintar+"");
   out.println("<BR>");
   out.println("<center><input type=button value='Cancelar'  onclick=' history.go(-4)'></center>");
   String v_pie = STBCL_GenerarBaseHTML.TBFL_PIE;
   out.println("<br>");
   out.println(""+v_pie+"");
   out.close();
  }
 }//metodo
}/*@lineinfo:generated-code*/