package TBPKT_UTILIDADES.TBPKT_RTF;

import java.io.*;
import java.util.*;
import TBPKT_UTILIDADES.TBPKT_RTF.TBCL_ManageFile;

public class TBCL_RTF
{
	private static String header = "";
	private static ArrayList items;
	private static ArrayList values;
	private static RandomAccessFile out;
  protected static String nFile = "";
	
	public TBCL_RTF(String hFile)
	{
		items = new ArrayList();
		values = new ArrayList();
    TBCL_ManageFile mF = new TBCL_ManageFile();
    try 
		{
		  header = mF.TBFL_LoadString(hFile);
    }
		catch(IOException e)
		{	System.out.println("Error!!" + e);}
	}
	
  /**
   * Adiciona un Item 
   * @param sItem titulo del item.
   * @param iFile archivo del tag.
   */	
	public static void TBPL_AddItem(String sItem, String iFile)
	{
		
		items.ensureCapacity(items.size() + 1);
			
		TBCL_ManageFile mF = new TBCL_ManageFile();
		try 
		{
			items.add(mF.TBFL_LoadString(iFile) + sItem + "}");
		}
		catch(IOException e)
		{	System.out.println("Error!!" + e);}
		
	}

  /**
   * Retorna un Item 
   * @param nItem posición del item.
   * @return String Item.
   */	
	public static String TBFL_GetItem( int nItem)
	{
		return (String) items.get(nItem -1);
	}

  /**
   * Adiciona un Valor
   * @param vsItem valor del item.
   */	
	public static void TBPL_AddValue(String vsItem)
	{
		
		values.ensureCapacity(values.size() + 1);
		values.add("{\\fs18\\cf1  \\tab " + vsItem + "}");
		
	}

  /**
   * Retorna un valor de un item.
   * @param nItem posicion del item.
   * @return String el valor.
   */	
	public static String TBFL_GetValue( int nItem)
	{
		return (String) values.get(nItem -1);
	}
	
  /**
   * Establece el valor de un Item 
   * @param nItem posición del item.
   * @param vsItem valor del item.
   */	
	public static void TBPL_SetValue( int nItem, String vsItem)
	{
		values.set(nItem -1, "{\\fs18\\cf1 \\tab  " + vsItem + "}");
	}
	
	  /**
   * Abre un archivo RTF
   * @param oFile nombre del archivo.
   */	
	public static void TBPL_Open(String oFile) throws IOException 
	{
		File tmpFile = new File(oFile);
		tmpFile.delete();
		out = new RandomAccessFile(oFile, "rw"); 
		out.writeBytes(header);
		
	}
	public static void TBPL_Open() throws IOException
	{
		File tmpFile = new File(nFile);
		tmpFile.delete();
		out = new RandomAccessFile(nFile, "rw");
		out.writeBytes(header);
	}
	
	  /**
   * Cambia el nombre del archivo RTF
   * @param oFile nombre del archivo.
   */	
	public static void TBPL_SetFile( String oFile)
	{
		nFile = oFile;
	}
		
	/**
   * Cierra el archivo RTF.
   */	
	public static void TBPL_Close() throws IOException 
	{
		if (out.length() > 0)
			out.seek(out.length());
		out.write('}');	
		out.write('}');
    out.write('}');
		out.close();
	}

	/**
   * Borra todos los valores de los Items.
   */		
	public static void TBPL_ClearValues() throws IOException 
	{
    String emptyS = new String("{\\fs18\\cf1  \\tab }");
		for (int con = 1; con < items.size()+1; con++) {
      if (!emptyS.equals(TBFL_GetValue(con)))
			  TBPL_SetValue(con, "-");
    }
  }
	
	public static int length()
	{
		return values.size();
	}
	
	/**
   * Imprime los items de una página en el archivo RTF.
   */	
	public static void TBPL_PrintPage() throws IOException 
	{
		if (out.length() > 0)
			out.seek(out.length());
			
		for (int con = 0; con < items.size(); con++) {
			out.writeBytes((String) items.get(con));
			out.writeBytes((String) values.get(con));
   	}
	}
}
     