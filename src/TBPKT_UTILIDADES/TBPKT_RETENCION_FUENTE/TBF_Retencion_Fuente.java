
// Copyright (c) 2001 skandia
package TBPKT_RETENCION_FUENTE;


import javax.swing.*;
import java.awt.*;
import oracle.jdeveloper.layout.*;
import java.util.*;
import java.sql.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import TBPKT_UTILIDADES.TBPKT_RTF.TBCL_RTF;
import TBPKT_UTILIDADES.TBPKT_PLANTILLA.*;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

/**
 * A Swing-based top level window class.
 * <P>
 * @author ALFA GL Ltda.
 */
public class TBF_Retencion_Fuente extends JFrame {
  String path = "";
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JPanel jPanel2 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JPanel jPanel3 = new JPanel();
  VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
  JLabel jLabel11 = new JLabel();
  JPanel jPanel4 = new JPanel();
  JLabel jLabel2 = new JLabel();
  JComboBox Formato = new JComboBox();
  JPanel jPanel5 = new JPanel();
  JLabel jLabel3 = new JLabel();
  JPanel jPanel6 = new JPanel();
  JButton BGenerar = new JButton();
  JButton BCerrar = new JButton();
  JTextField Archivo = new JTextField();
  JPanel jPanel7 = new JPanel();
  XYLayout xYLayout2 = new XYLayout();
  JPanel jPanel8 = new JPanel();
  JLabel jLabel4 = new JLabel();

  private Thread processThread;
  JPanel jPanel9 = new JPanel();
  VerticalFlowLayout verticalFlowLayout3 = new VerticalFlowLayout();
  int conArchivo = 1;
  JLabel jLabel1 = new JLabel();

  /**
   * Constructs a new instance.
   */
  public TBF_Retencion_Fuente() {
    super();
    try  {
      jbInit();
    }
    catch (Exception e) {
      jLabel1.setText(e.toString().substring(0, e.toString().length()/2));
      jLabel11.setText(e.toString().substring(e.toString().length()-1, e.toString().length()-1));
      System.out.print(e.toString());
    }
  }

  /**
   * Initializes the state of this instance.
   */
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Generación de Certificados de Retención en la Fuente");
    this.setSize(new Dimension(410, 322));
    xYLayout1.setHeight(40);
    jLabel11.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel2.setHorizontalTextPosition(SwingConstants.LEFT);
    jLabel2.setText("Formato");
    jPanel3.setLayout(verticalFlowLayout2);
    jPanel2.setLayout(xYLayout1);
    jPanel1.setLayout(verticalFlowLayout1);
    Archivo.setPreferredSize(new Dimension(100, 21));
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jPanel2, null);
    jPanel1.add(jPanel9, null);
    jPanel9.add(jPanel3, null);
    jPanel3.add(jLabel1, null);
    jPanel9.add(jPanel8, null);
    jPanel8.add(jLabel4, null);
    jPanel3.add(jLabel11, null);
    jPanel9.add(jPanel4, null);
    jPanel4.add(jLabel2, null);
    jPanel4.add(Formato, null);
    jPanel9.add(jPanel5, null);
    jPanel5.add(jLabel3, null);
    jPanel5.add(Archivo, null);
    jPanel5.add(jPanel7, null);
    jPanel9.add(jPanel6, null);
    jPanel6.add(BGenerar, null);
    jPanel6.add(BCerrar, null);
    jPanel8.setVisible(false);

		LinkedList listQuery;
    Iterator itQuery;
    String[] resultQuery;
		String query = "";

    // Formatos para los que se puede ejecutar la generacion
			query = "SELECT FOC_PRO_CODIGO, FOC_ANO FA, FOC_ANO, FOC_ANO "+
						"FROM TBFORMATOS_CERTIFICADO "+
						"ORDER BY FOC_PRO_CODIGO, FA ";
			listQuery = executeQuery(query, new String[0]);
			itQuery = listQuery.iterator();
    jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel1.setText("Generación de Certificados de Retención en la Fuente");
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setAutoscrolls(true);
    jLabel1.setBackground(new Color(191, 175, 112));
    jLabel1.setForeground(Color.white);
    jLabel1.setFont(new Font("SansSerif", 0, 14));
    jPanel9.setBackground(Color.white);
    jPanel9.setLayout(verticalFlowLayout3);
    jPanel2.setBackground(new Color(0, 64, 138));
    jPanel4.setBackground(Color.white);
    jPanel5.setBackground(Color.white);
    jPanel6.setBackground(Color.white);
    jPanel8.setBackground(Color.white);
    jPanel1.setBackground(new Color(0, 64, 138));
    jLabel4.setText("Este proceso puede tardar varias horas, por favor espere...");
    xYLayout2.setHeight(50);
    jPanel7.setLayout(xYLayout2);
    jLabel2.setHorizontalAlignment(SwingConstants.LEFT);
    jPanel3.setBackground(new Color(128, 128, 64));
    jLabel11.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel11.setAutoscrolls(true);
    jLabel11.setBackground(new Color(191, 175, 112));
    jLabel11.setForeground(Color.white);
    jLabel11.setFont(new Font("SansSerif", 0, 14));
    Archivo.setHorizontalAlignment(JTextField.RIGHT);
    Archivo.setMinimumSize(new Dimension(100, 21));
    Archivo.setText("crf2001");
    BCerrar.setText("Cerrar");
    BCerrar.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        BCerrar_actionPerformed(e);
      }
    });
    BGenerar.setText("Generar");
    BGenerar.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        BGenerar_actionPerformed(e);
      }
    });
    jLabel3.setText("Archivo (p.e. crf2001 sin extensión)");
			while(itQuery.hasNext()){
		     	resultQuery = (String[]) itQuery.next();
          Formato.addItem(resultQuery[0]+"-"+resultQuery[1]);
		  }

  }
        /**
   * Ejecuta una consulta con los par&aacute;metros definidos
   * @param query Consulta que desea hacerse.
   * @param params Par&aacute;metros de la consulta.
   * @return java.util.LinkedList
   */
   private LinkedList executeQuery(String query, String[] params) throws SQLException {
    LinkedList retList = new LinkedList();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      con   =   DataSourceWrapper.getInstance().getConnection();
      ps= con.prepareStatement(query);

      for(int i= 0; i< params.length; i++) {
          ps.setString(i+1,params[i]);
        }
      rs= ps.executeQuery();
      while(rs.next()) {
        int count = rs.getMetaData().getColumnCount();
        String[] record = new String[count];
        for (int i=0; i<count; i++)
          record[i] = rs.getString(i+1);
        retList.add(record);
      }

      if(rs != null) rs.close();
      if(ps != null) ps.close();
    }
    finally{
      try {
            DataSourceWrapper.closeConnection(con);
      }
      catch(SQLException sqle) {
      jLabel4.setText("Excepción: "+ sqle);
      }
    }
    return retList;
  }





  void BCerrar_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  void BGenerar_actionPerformed(ActionEvent e) {
    jPanel4.setVisible(false);
    jPanel5.setVisible(false);
    jPanel8.setVisible(true);
    BGenerar.setVisible(false);
    correrProceso();
  }

  private void correrProceso(){
      Connection v_conexion_taxb    =   null;
    String formato = (String) Formato.getSelectedItem();
    String fecha = formato.substring(formato.length()-4);
		String producto = formato.substring(0, formato.length()-5);
    String archivo = "", menerr = "";
    if(Archivo.getText() == null)
      archivo = "crf2001";
    else
      archivo = Archivo.getText();

    try {
        v_conexion_taxb =   DataSourceWrapper.getInstance().getConnection();
			CallableStatement sprocedure = v_conexion_taxb.prepareCall("{ call TBPBD_LLenarCertificados(?, ?, ?) }");
			sprocedure.setString(1, producto);
	    sprocedure.setString(2, fecha);
      sprocedure.registerOutParameter(3, Types.VARCHAR);
    	sprocedure.execute();
      menerr = sprocedure.getString(3);
        sprocedure.close();

    }
    catch(SQLException sqle) {
      jLabel4.setText("Excepcion: "+ sqle);
  	}
      finally{
                 try{
                              DataSourceWrapper.closeConnection(v_conexion_taxb);                  
                 }catch(SQLException sqlEx){
                              System.out.println(sqlEx.toString());
                 }
         }
    if(menerr.equals("OK")){
      if(TBFL_GenerarDocumento(producto, fecha, archivo)){
        jLabel4.setText("Se generaron: "+ conArchivo +" archivo(s) "+archivo+"XX.rtf");
      }
    }
    else
      jLabel4.setText("ERROR " + menerr);
  }

  protected int TBFL_Pos(ArrayList array, String elem){
    int i = 0;
    for (; i < array.size(); i++){
      String curr = array.get(i).toString();
      if(curr.equals(elem)) break;
    }
    return i;
	}

/**
   * Generar los documentos RTF con la informacion de Certificados
   * @param producto codigo del producto
   * @param fecha fecha del formato
   * @param archivoSalida nombre del archivo de salida
   * @param numero El n&uacute;mero de entrada.
   * @return boolean  1 Se genero el archivo sin problemas
     @                2 No se genero el archivo
   */
    private boolean TBFL_GenerarDocumento(String producto, String fecha, String archivoSalida) {
  	LinkedList listQuery, contratos, itemsContrato;
    Iterator itQuery, itContratos, itItems;
    String[] resultQuery, resultItems;
		String query= "", contrato = "", valor = "", nit = "";
		ArrayList items2 = new ArrayList();    
		int item, footItems;
		String pageHeader = "", pageFooter = "";
    DecimalFormat currencyNumberFormat = new DecimalFormat("¤###,###,###,###,###,###.##");
		DecimalFormat formatoID = new DecimalFormat("#,###,###,###,##0");
		boolean result= false;
    int conContrato = 1;
    TBCL_RTF rtfFile = null;
    float m;

		try{

      // Consulta del NIT para el producto seleccionado
			query=  "SELECT PRO_NUMERO_IDENTIFICACION "+
							" FROM  TBPRODUCTOS "+
							" WHERE PRO_CODIGO = ? ";

			listQuery = executeQuery(query, new String[]{producto});
			resultQuery = (String[]) listQuery.getFirst();
			nit = resultQuery[0];

      // Encabezado y pie de pagina para el certificado
			query=  "SELECT FOC_HEADER, FOC_FOOTER, FOC_HEADER, FOC_FOOTER "+
							" FROM TBFORMATOS_CERTIFICADO "+
							" WHERE FOC_PRO_CODIGO = ? "+
								" AND FOC_ANO        = ? ";

			listQuery = executeQuery(query, new String[]{producto, fecha});
			resultQuery = (String[]) listQuery.getFirst();
			pageHeader = resultQuery[0];
			pageFooter = resultQuery[1];

			//Titulo de los Items
			query=  "SELECT ANI_ITC_CODIGO, ANI_POSICION, ANI_TITULO, ANI_TITULO "+
							" FROM TBANOS_ITEMS "+
							" WHERE ANI_FOC_PRO_CODIGO = ? "+
								" AND ANI_FOC_ANO        = ? "+
							" ORDER BY ANI_POSICION";
			listQuery = executeQuery(query, new String[]{producto, fecha});
      itQuery = listQuery.iterator();

	    try{
        // Encabezado del archivo RTF
				rtfFile = new TBCL_RTF(path + "Header.dat");

        //Archivo de salida
				rtfFile.TBPL_Open(path + archivoSalida + conArchivo+".rtf");

				// Item 1  Encabezado de cada página
        rtfFile.TBPL_AddItem(nit+"\\par\\par", path + "HPage1.dat");
	      rtfFile.TBPL_AddValue("");
		    rtfFile.TBPL_AddItem(pageHeader+"\\par\\par", path + "HPage2.dat");
	      rtfFile.TBPL_AddValue("");

        // Items del documento
		   	while(itQuery.hasNext()){
		     		resultQuery = (String[]) itQuery.next();
            items2.ensureCapacity(items2.size() + 1);
            items2.add(resultQuery[1]);

          // Si la posicion es mayor que 90 va en el pie
		       if (resultQuery[1].compareTo("90") < 0){
		         if (resultQuery[2].substring(0,1).equals("*")){
		           rtfFile.TBPL_AddItem(resultQuery[2].substring(1), path + "Item1.dat");
		 		      rtfFile.TBPL_AddValue("-");
		         }
		         else
		         {
		           rtfFile.TBPL_AddItem(resultQuery[2], path + "Item2.dat");
		  	      rtfFile.TBPL_AddValue("-");
		         }
		       }
				}
          //  Pie de pagina
		     rtfFile.TBPL_AddItem("\\par "+pageFooter+"\\par\\par", path + "Item1.dat");
		     rtfFile.TBPL_AddValue("");
		     footItems = rtfFile.length();
		     itQuery = listQuery.iterator();

         // Valores para cada Item
		     while(itQuery.hasNext()){
		     	resultQuery = (String[]) itQuery.next();
		       if (resultQuery[1].compareTo("90") >= 0){
		         rtfFile.TBPL_AddItem(resultQuery[2], path + "Item3.dat");
		 		    rtfFile.TBPL_AddValue("-");
		       }
				}
		     // Ultimo valor Item
		    rtfFile.TBPL_AddItem("", path + "FPage1.dat");
		    rtfFile.TBPL_AddValue("");

				try{

          //  Todos los contratos
					query=  "SELECT 	CON_NUMERO"+
		                        ", CON_NUMERO_IDENTIFICACION"+
										" FROM	TBCONTRATOS"+
										" WHERE	CON_PRO_CODIGO 					= ? "+
										" ORDER BY 	CON_NUMERO_IDENTIFICACION"+
		                        ", CON_NUMERO";

					contratos = executeQuery(query, new String[]{producto});
					resultQuery = (String[]) contratos.getFirst();
					itContratos = contratos.iterator();
					jLabel4.setText("Escribiendo archivo" + archivoSalida + conArchivo+".rtf");

          // Para cada contrato trae sus correspondientes Items
					while(itContratos.hasNext()){
							resultQuery = (String[]) itContratos.next();
							contrato = resultQuery[0];

							query=  "SELECT 	CER_ANI_ITC_CODIGO"+
							        ", CER_VALOR"+
        							", ITC_REF_TIPO_DATO"+
				        			", ANI_POSICION "+
					  		      ", MAX(CER_VERSION) "+
							" FROM	TBCERTIFICADOS"+
      							", TBANOS_ITEMS "+
			  		    		", TBITEMS_CERTIFICADO "+
							" WHERE	CER_CON_PRO_CODIGO 	= ? "+
      							" AND CER_CON_NUMERO 		= ? "+
			      				" AND CER_ANI_FOC_ANO 	= ? "+
						      	" AND ANI_FOC_PRO_CODIGO = CER_CON_PRO_CODIGO "+
      							" AND ANI_FOC_ANO = CER_ANI_FOC_ANO "+
			      				" AND ANI_ITC_CODIGO = CER_ANI_ITC_CODIGO "+
			  			    	" AND ITC_CODIGO =	CER_ANI_ITC_CODIGO "+
							" GROUP BY 	CER_ANI_ITC_CODIGO"+
      							", CER_VALOR "+
				      			", ITC_REF_TIPO_DATO"+
							      ", ANI_POSICION "+
							" ORDER BY 	ANI_POSICION";
							itemsContrato = executeQuery(query, new String[]{producto, contrato, fecha});
              itItems = itemsContrato.iterator();

              //  Dependiendo del tipo (Moneda, identificador, etc) del Item lo formatea para ser impreso  
              if (itItems.hasNext()){
								while (itItems.hasNext()){
                  resultItems = (String[]) itItems.next();
									item = TBFL_Pos(items2, resultItems[3]);
									valor = resultItems[1];
									if (resultItems[3].compareTo("90") < 0){
										if (item < rtfFile.length()){
											if(resultItems[2].equals("STD004")){
												valor = currencyNumberFormat.format(Double.parseDouble(valor));
											}else if(resultItems[2].equals("STD001")){
												valor = ""+ Integer.parseInt(valor);
												}else if(resultItems[2].equals("STD005")){
                          valor = valor.trim();
													valor = formatoID.format(Long.parseLong(valor));
									 	  	}
													rtfFile.TBPL_SetValue(item+3, valor);
									     	}
									    }
									 else
									 {
											rtfFile.TBPL_SetValue(item+4, valor);
									 }
								}

                // Si ha escrito 2000 contratos en el archivo lo cierra y abre otro
								if (conContrato == 2000)
	            	{
		            	rtfFile.TBPL_PrintPage();
				          rtfFile.TBPL_Close();
		              conArchivo++;
                  jLabel4.setText("Escribiendo archivo" + archivoSalida + conArchivo+".rtf");
		              rtfFile.TBPL_SetFile(path + archivoSalida + conArchivo+".rtf");
		              rtfFile.TBPL_Open();
		              conContrato = 1;
			        	}
	            	else{

                  // Imprime los valores de la pagina y prepara la sigiuiente pagina
			       	    rtfFile.TBPL_PrintPage();
			       	    rtfFile.TBPL_ClearValues();
			       	    conContrato++;
	            	}
              }
			     }//end while
		      rtfFile.TBPL_Close();
				  result = true;
				}
				catch(SQLException sqle) {
			     	jLabel4.setText("Excepcion: "+ sqle);
			  }
			}
			catch(IOException ioe) {
		     	jLabel4.setText("Excepcion: "+ ioe);
		  }
		}
		catch(SQLException sqle) {
	     	jLabel4.setText("Excepcion: "+ sqle);
	  }
    return result;
  }

}

