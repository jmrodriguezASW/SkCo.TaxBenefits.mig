package TBPKT_AJUSTES;
import java.util.*;


/**
*  El objetivo de esta clase es encontrar la información de un solo componente dentro de una
*  cadena de información. Ejem: <nombre='omaira' cabello='negro' ojos='oscuros'......>
*  nombre=componente
*  información=omaira
*/

public class TBCL_FndCmp extends Object{
/////////////////////////////////constructor///////////////////////////////////////
	public TBCL_FndCmp(){
	}
//////////////////////////////devuelve la información del componente//////////////////  
	public String TBPL_getCmp(String rawXml,
                            String attrName){

		String retString="";
		String str2=new String(rawXml.toLowerCase());
		int j=str2.indexOf((attrName.toLowerCase()+'='));

		if (j>-1){                          // search first "'"

			int k=str2.indexOf("'",j);
      if(k==-1)

        k=str2.indexOf("%",j);
			if (k>-1){
                // search last "'"

				int l=str2.indexOf("'",k+1);
        if(l==-1)

          l=str2.indexOf("%",k+1);
				if (l>-1){                      // get value

					retString=new String(rawXml.substring(k+1,l));
				}
			}
		}
		return retString;
	}
////////////////////////////devuelve la fecha en formato dd-mm-aa//////////////////////////
public String TBPL_formatDate(String v_fec,String format)
 {
    String v_fecha = new String("");
    int ii = 0,jj = 0,kk = 0,ll = 0;
    if(format.equalsIgnoreCase("aaaa-mm-dd"))
    {
     ii    = 0;
     jj    = 0;
     kk    = 0;
     ii    = v_fec.indexOf("-");
     kk    = ii+1;
     jj    = v_fec.indexOf("-",kk);
     kk    = jj+1;
     v_fec = v_fec.substring(kk)+v_fec.substring(ii,kk)+v_fec.substring(2,ii);
    }
    Hashtable dicMes = new Hashtable();
    dicMes.put("01","JAN");dicMes.put("02","FEB");dicMes.put("03","MAR");dicMes.put("04","APR");
    dicMes.put("05","MAY");dicMes.put("06","JUN");dicMes.put("07","JUL");dicMes.put("08","AUG");
    dicMes.put("09","SEP");dicMes.put("10","OCT");dicMes.put("11","NOV");dicMes.put("12","DEC");
    ii      = 0;
    jj      = 0;
    ii      = v_fec.indexOf("-")+1;
    jj      = v_fec.indexOf("-",ii);
    v_fecha = v_fec.substring(0,ii)+dicMes.get(v_fec.substring(ii,jj))+v_fec.substring(jj);
    return v_fecha;
 }
//--
}


