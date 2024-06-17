package TEST.TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Time;
import java.util.StringTokenizer;
import java.util.Vector;
import java.sql.*;
import java.util.*;
import java.math.*;
import oracle.jdbc.OracleTypes.*;
import com.ibm.as400.access.*;

import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.TBCL_FuncionesAs400;
import TBPKT_UTILIDADES.TBPKT_CONEXIONBASEDATOS.*;
import TBPKT_UTILIDADES.TBPKT_FUNCIONES_AS400.*;
import TBPKT_MODULO_APORTES.*;
import oracle.jdbc.driver.*;

public class Test 
{
  public static Connection con = null;
  public Test()
  {
  }

 	public static void main( String args[] ) throws Exception
	{
        
      final int iRemote = 1; // 0: local / 1: remoto
      //Date dtFinalDate;
      //Date [] dtInitialDateList ;
      int [] iPeriodList;
      /*[SO_396] Se realiza modificación de llamado por ser método estático TBFL_ValidarUsuario de la clase TBCL_Validacion, no es necesaria la instancia nueva*/
      //TBCL_Validacion valuesUser = new TBCL_Validacion();
      String[] valuesUs = new String[3];
      valuesUs = TBCL_Validacion.TBFL_ValidarUsuario();
      DriverManager.registerDriver(new OracleDriver());
      con = DriverManager.getConnection(valuesUs[0],valuesUs[1],valuesUs[2]);
      String sistema="";
      String usuario="";
      String password="";
      String libreria="";
      String contrato="000052543";
      
      System.out.println("Intentará establecer conexión con 400");
      CallableStatement csPdto =  con.prepareCall("BEGIN :1 := TB_FREFERENCIAS_MULTI( :2 ,\n :3,\n :4 ,\n :5)\n; END;");
      csPdto.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
      csPdto.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(3,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
      csPdto.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
      /* setear parametro de entrada IN */
      csPdto.setString(2,sistema);
      csPdto.setString(3,usuario);
      csPdto.setString(4,password);
      csPdto.setString(5,libreria);
      csPdto.execute();
      /* recupera parametros de salida*/
      //logger.info("Se tienen los parametros de 400");
      sistema = csPdto.getString(2);
      usuario = csPdto.getString(3);
      password = csPdto.getString(4);
      libreria = csPdto.getString(5);
      csPdto.close();

      String sUrl = "jdbc:as400://"+sistema+"/LAAMOBJ";
      DriverManager.registerDriver(new AS400JDBCDriver());
      Connection conAs400 = DriverManager.getConnection(sUrl,usuario, password);
      Statement stmtRetiros = conAs400.createStatement(); 
      ResultSet retiros = stmtRetiros.executeQuery("CALL LAAMOBJ.AJW9XFR ('       ','000052543')");

      while(retiros.next()) {
            /* Establecer el producto al que pertenece el contrato */
            String investemet = retiros.getString("WBRQCZ");
            System.out.print(investemet);
            String profile = retiros.getString("WBBCCX");
            System.out.print(profile);
            /*String contratoOriginal = retiros.getString("ret_con_numero");
            String producto = retiros.getString("ret_con_pro_codigo");*/

      }
      //ResultSet retiros = stmtRetiros.executeQuery("CALL PGM(AJW9XFR)   PARM('         ' '000053140')");

      /*int param = 2;
      String sUrl="";
      int iIteraciones = 10;
      float fDelta = 0.00001f;
      //VectorIRR DataVector = null;
      Connection Connect = null;
      Connection Conpar = null;
      String sInsLibrary = "EXTRACTOS";
      String sProduct = "MFUND"; 
      String sTypeProc = "AJW9XFR";
      long lfinDate=0;
		
      Date date1;
      Time time1;
      date1 = Date.valueOf("1970-01-01");
      date1.setTime (System.currentTimeMillis());
      time1 = new Time(System.currentTimeMillis());

      try{
        Class.forName("com.ibm.db2.jdbc.app.DB2Driver");
        if (iRemote == 0) sUrl = "jdbc:db2://*local/"+libreria;
        else sUrl = "jdbc:db2://SADC2/"+libreria;
      }
      catch(ClassNotFoundException e)
      {
        try
        {
          Class.forName("com.ibm.as400.access.AS400JDBCDriver");
          if (iRemote == 0) sUrl = "jdbc:db2://*local/"+libreria;
          else sUrl = "jdbc:db2://SADC2/"+libreria;
        }
        catch(ClassNotFoundException e1)
        {
          System.out.println("Jdbc driver could not be loaded " + e1);
          return;
        }
      }

		// Se crea la conexión con la Base de Datos
		try
		{
      sUrl = "jdbc:db2://SADC2/LAAMOBJ";
      // /QSYS.LIB/LAAMOBJ.LIB/
			if (iRemote == 0) Connect = DriverManager.getConnection(sUrl, "", "");
			else Connect = DriverManager.getConnection(sUrl, usuario, password);

	
			int iRows = 0;*/
//			***************************** Consulta Periodos **************
			/*IrrFundParameters Auxpar = new IrrFundParameters();
			Auxpar.getParameters(Conpar, sInsLibrary, sProduct, sTypeProc);
			Vector vParam = Auxpar.getPeriods();
			if(vParam.size()==0)
			{
				System.out.print("No Existen Periodos de Calculo");
				return;
			}
			iRows = vParam.size();
			Vector vMonths = Auxpar.getEndMonth();

			iPeriodList = new int[iRows];
			dtInitialDateList = new Date [iRows];
			Date dtEffectivedate;
//			System.out.println("");
			List_IRR [] AmountFlowList ;
			AmountFlowList = new List_IRR [iRows];
			for(int i=0; i < iRows; i++) AmountFlowList[i] = new List_IRR ("Nuevo");

			System.out.println("Fechas Iniciales: ");
			String sparm="";
			for(int paramt=0; paramt< vParam.size(); paramt++)
			{
				sparm = vParam.elementAt(paramt).toString();
				iPeriodList[paramt] =Integer.parseInt(sparm);
				if(sTypeProc.equalsIgnoreCase("S"))
				{
					if (iPeriodList[paramt] != 0) 
					{
						long lIniDates    =   Aux.CalculateInitialDate(dtFinalDate, iPeriodList[paramt]);
						//int iMonths = iPeriodList[paramt]/30;
						dtInitialDateList[paramt]	=	Aux.ConvertLongtoDate(lIniDates);
					}
					else
						dtInitialDateList [paramt] = Date.valueOf("1900-01-31");
				}
				else
				{
					if (iPeriodList[paramt] != 0)
						dtInitialDateList [paramt] = List_IRR.dateMinus(dtFinalDate, iPeriodList[paramt]);
					else
						dtInitialDateList [paramt] = Date.valueOf("1900-01-31");
				}

				System.out.println(dtInitialDateList[paramt]);
			}
//			***************************** Fin Consulta Periodos ************** 
*/
			
			/*lfinDate = Aux.convertDatetoLong(dtFinalDate);
			Aux.deleteData(sInsLibrary, lfinDate, Connect, iPeriodList, sTypeProc);
			DataVector = Aux.insertInitialFlow ( Connect, sLibrary, dtInitialDateList, dtFinalDate, DataVector);
			//System.out.println(DataVector.getMatrizInitial().length + " " + DataVector.getArrayFinal().length);
			DataVector = Aux.insertFinalFlow ( Connect, sLibrary, dtFinalDate, DataVector );
			Aux.movesFlow ( Connect, sLibrary, dtInitialDateList, dtFinalDate, iPeriodList, DataVector, iIteraciones, fDelta, sTypeProc );
			time1 = new Time(System.currentTimeMillis());
			System.out.println("Hora Fin: "+time1);
			System.out.println("\n*******************************************");
			System.out.println(  "**      FINALIZADO PROGRAMA IrrFund      **");
			System.out.println(  "*******************************************");
      */
		/*}
		catch (java.sql.SQLException x)
		{
			x.printStackTrace();
		}
		// Se cierra la conexión.
		finally
		{
			if (Connect != null) Connect.close();
		}*/
	}
}