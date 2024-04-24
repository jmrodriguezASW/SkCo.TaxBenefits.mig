package TBPKT_UTILIDADES.TBPKT_RTF;
import java.io.*;

public class TBCL_ManageFile
{
	public TBCL_ManageFile()
	{}
  /**
   * Carga una cadena proveniente de un archivo
   * @param iFile archivo de entrada.
   * @return String la cadena que lee.
   */
	public String TBFL_LoadString(String iFile)throws IOException
	{
		RandomAccessFile in = new RandomAccessFile(iFile, "r");
		int currChar;
		String currStr = "", str = "";
                while ((currStr= in.readLine())  != null)
                {
                	str = str + currStr;
		}

		in.close();
		return str;
	}


  /**
   * Invierte un archivo
   * @param iFile archivo de entrada.
   * @param oFile archivo invertido.
   */
  public void TBPL_inv(String iFile, String oFile) throws IOException {
    RandomAccessFile in= null;
    BufferedWriter out= null;

    try {
      in= new RandomAccessFile(iFile, "r");
      File tmpFile = new File(oFile);
		  tmpFile.delete();

      out= new BufferedWriter(new FileWriter(oFile));

      long currPos = in.length() - 1;
      String ln = "";
      int currChar;
      while (currPos >= 0) {
        in.seek(currPos);
        currChar = in.read();
        if(currChar == 13) {
          out.write(ln);
          out.newLine();
          ln = "";
        }
        else if (currChar != 10) {
          ln = (char) currChar + ln;
        }
        currPos--;
      }
      out.write(ln);
    }
    finally {
      if(in != null) in.close();
      if(out != null) out.close();
    }
  }
}
