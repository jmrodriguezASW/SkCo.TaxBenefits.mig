//boton 
function validar_retiro(which)
{
  
  v_codigo.aceptar.disabled = true;
  v_codigo.regresar.disabled= true;
  v_codigo.cancelar.disabled= true;
}


//campo requerido
function checkrequired(which) 
{
 var pass=true;
 var pass2=true;
 if (document.images) 
 {
   for (i=0;i<which.length;i++) 
   {
    var tempobj=which.elements[i];
    if ((tempobj.name.substring(0,11)=="obligatorio")) 
    { 
       if (((tempobj.type=="text"||tempobj.type=="textarea")&&tempobj.value==""||tempobj.value=="0")) 
       {
          pass=false;
          break ;
       }
    }
    
    }
  }
 if (!pass)
 { 
   shortFieldName=tempobj.name.toUpperCase();
   alert("Favor Digitar el valor a retirar");
   return false;
  }

else
return true;

}

//campo reque campo requerido
function validar_fecha(which) 
{
 var pass=true;
 if (document.images)
 {
   for (i=0;i<which.length;i++)
   {
       var tempobj=which.elements[i];
       if ((tempobj.name.substring(0,11)=="obligatorio"))
      {
         if (((tempobj.type=="text")&&tempobj.value==""))
         {
           pass=false; 
           break;
         } 
         else
         {
           if( tempobj.type=="text")
           {
             if (tb_ffecha(v_codigo.obligatoriov_fecefectiva) == false) 
             {
               v_codigo.obligatoriov_fecefectiva.select();
               alert("La fecha es invalida");  
               v_codigo.obligatoriov_fecefectiva.focus();
               v_codigo.obligatoriov_fecefectiva.value = '';
               return false;
            }
           }
       
         } 

      }
   }
    var tempobj2=which.elements[1];
    if(tempobj2.name=="v_retiro"&&tempobj2.value=="  ")
     {
  	    alert("Favor escoger el tipo de retiro.");  
        return false;
     }
   
}
if (!pass) {
shortFieldName=tempobj.name.toUpperCase();
alert("Favor Digitar la Fecha Efectiva de la Solicitud de Retiros");
return false;
}
else
return true;
}


//campo reque campo requerido
function validar_fecha2(which) 
{
 var pass=true;


 if (document.images)
 {
   for (i=0;i<which.length;i++)
   {
       var tempobj=which.elements[i];
       if ((tempobj.name.substring(0,11)=="obligatorio"))
      {
         if (((tempobj.type=="text")&&tempobj.value==""))
         {
           pass=false; 
           break;
         } 
         else
         {
           if( tempobj.type=="text")
           {
             if (tb_ffecha(v_codigo.obligatoriov_fecefectiva) == false) 
             {
               v_codigo.obligatoriov_fecefectiva.select();
               alert("La fecha es invalida");  
               v_codigo.obligatoriov_fecefectiva.focus();
               v_codigo.obligatoriov_fecefectiva.value = '';
               return false;
            }
            else
            {
              v_codigo.aceptar.disabled = true;
              v_codigo.cancelar.disabled= true;

            }
           }
       
         } 

      }
   }
    var tempobj2=which.elements[1];
    if(tempobj2.name=="v_retiro"&&tempobj2.value=="  ")
     {
  	    alert("Favor escoger el tipo de retiro.");  
        return false;
     }
   
}
if (!pass) {
shortFieldName=tempobj.name.toUpperCase();
alert("Favor Digitar la Fecha de cierre");
return false;
}
else
return true;
}



function tb_ffecha(objName) {
var strDatestyle ="US"; //United States date style
//var strDatestyle = "EU";  //European date style
if (objName.value.length < 6)
  return false;
var strDate;
var strDateArray;
var strDay;
var strMonth;
var strYear;
var intday;
var intMonth;
var intYear;
var booFound = false;
var datefield = objName;
var strSeparatorArray = new Array("-"," ","/",".","+");
var intElementNr;
var err = 0;
var strMonthArray = new Array(12);
strMonthArray[0] = "01";
strMonthArray[1] = "02";
strMonthArray[2] = "03";
strMonthArray[3] = "04";
strMonthArray[4] = "05";
strMonthArray[5] = "06";
strMonthArray[6] = "07";
strMonthArray[7] = "08";
strMonthArray[8] = "09";
strMonthArray[9] = "10";
strMonthArray[10] = "11";
strMonthArray[11] = "12";//100
strDate = datefield.value;
if (strDate.length < 1) {
return true;
}
for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) {
if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1) {
strDateArray = strDate.split(strSeparatorArray[intElementNr]);
if (strDateArray.length != 3) {
err = 1;
return false;
}
else {
strDay = strDateArray[1];
strMonth = strDateArray[2];
strYear = strDateArray[0];
}
booFound = true;
   }
}
if (booFound == false) {
if (strDate.length>5) {
strDay = strDate.substr(6);
strMonth = strDate.substr(4, 6);
strYear = strDate.substr(0, 4);
   }
}
if (strYear.length == 2) {
strYear = "20" + strYear;
}
if (strYear.length == 1) {
return false
}
if (strYear.length == 3) {
return false
}
if (strYear.length > 4) {
return false
}
// US style
if (strDatestyle == "US") {
strTemp = strDay;
strDay = strMonth;
strMonth = strTemp;
}
intday = parseInt(strDay, 10);
if (isNaN(intday)) {
err = 2;
return false;
}
intMonth = parseInt(strMonth, 10);
if (isNaN(intMonth)) {
for (i = 0;i<12;i++) {
if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase()) {
intMonth = i+1;
strMonth = strMonthArray[i];
i = 12;
   }
}
if (isNaN(intMonth)) {
err = 3;
return false;
   }
}
intYear = parseInt(strYear, 10);
if (isNaN(intYear)) {
err = 4;
return false;
}
if (intMonth>12 || intMonth<1) {
err = 5;
return false;
}
if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intday > 31 || intday < 1)) {
err = 6;
return false;
}
if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intday > 30 || intday < 1)) {
err = 7;
return false;
}
if (intMonth == 2) {
if (intday < 1) {
err = 8;
return false;
}
if (LeapYear(intYear) == true) {
if (intday > 29) {
err = 9;
return false;
}
}
else {
if (intday > 28) {
err = 10;
return false;
}
}
}
var dia;
if (strDatestyle == "US") {
if(intday == 1){
 dia = "0"+intday;
}
else if(intday == 2)
     {
          dia = "0"+intday;
     }
     else if(intday == 3)
          {
              dia = "0"+intday;
          }
          else if(intday == 4)
              {
                 dia = "0"+intday;
              } 		
              else  if(intday == 5)
                    {
                         dia = "0"+intday;
                    }
                    else if(intday == 6)
                         {
                             dia = "0"+intday;
                         }
                         else if(intday == 7)
                             {
                                dia = "0"+intday;
                             }
                             else if(intday == 8)
                                 {
                                      dia = "0"+intday;
                                 }
                                 else if(intday == 9)
                                      {
                                           dia = "0"+intday;
                                      } 		
                                      else
                                      { 
                                      	  dia =intday;
                                      	
                                     	}
 		
datefield.value = strYear + "-" + strMonthArray[intMonth-1] + "-" + dia;//200
}
else {
datefield.value = intday + "-" + strMonthArray[intMonth-1] + "-" + strYear;
}
return true;
}
function LeapYear(intYear) {
if (intYear % 100 == 0) {
if (intYear % 400 == 0) { return true; }
}
else {
if ((intYear % 4) == 0) { return true; }
}
return false;
}





//cuando se introducen en los valores negativos
function negativo(objeto)
{
  if (objeto.value<0)
  {
    alert('El valor no puede ser negativo');
    objeto.select(); 
    objeto.value ='356';
    objeto.value ='0';
//    objeto.focus();
    return true;
  }
  return false;
}
 



//verificar valo a retirar
function maximo(v_valre,totalesDesde)
{
 if(totalesDesde.value !=0)
 {
   if(totalesDesde.value < v_valre)
   {
     alert("Menor valor que el retiro. Total a retirar $"+formatCurrency(v_valre));
      return true;
   }
   else
   { if(totalesDesde.value > v_valre){
     return true;
     } 
   }
   return  false;
 }
 else
 { 
   alert("Favor digitar el valor del retiro por cada fondo");
   return true;
 } 
 return false;
}


//verificar % a retirar
function maximo2(totalesDesde)
{
 
  if(totalesDesde.value<100)
  {
     alert("Menor al 100% requerido.");
     return true;
  }
  else
  {
    if(totalesDesde.value>100)
    {
      return true;
    }
 }
 return false;
}

//se verifica el minimo de retiro permitido
function minretiro(min,objeto)
{
  if(objeto.value=='')//400
  {
    alert("Por favor Digite el valor a retirar");
    objeto.focus();
    objeto.select();
    objeto.value ='356';
    objeto.value='0';
    return true;
  }
  if ( objeto.value<min)
  {
     alert("Retiro no permitido,se debe retirar minimo: "+min);
     objeto.focus();
     objeto.select();
     objeto.value ='356';
     objeto.value='0';
     return true;
  } 
  return false;

}

//se verifica el valor a retirar
function minimo(min,objeto,saldo,neto,tipovalor)
{ 
 var resultado;
  
  
   //si es negativo
   if (negativo(objeto))
   {
       return 1;
   }
   //valiad el minimo
   if(minretiro(min,objeto))
   {
     return true;
   }
   //si el retiro es mayor que elsaldo disponible
   
   if(tipovalor.value == "STV001")//bruto
   {
     if(objeto.value>saldo)
     {
       alert("Retiro no permitido. El saldo disponible bruto para el contrato son $"+formatCurrency(saldo)+" y usted desea retirar "+formatCurrency(objeto.value)+".");
       objeto.focus();
       objeto.select();
       objeto.value ='356';
       objeto.value='0';
       return true;
     }
     else
     {
        resultado = saldo - objeto.value;
   	
     }
     
    }
   if(tipovalor.value == "STV002")//neto
   {
     if(objeto.value>neto)
     {
       alert("Retiro no permitido.El saldo disponible neto para el contrato son $"+formatCurrency(neto)+" y usted desea retirar "+formatCurrency(objeto.value)+".");
       objeto.focus();
       objeto.select();
       objeto.value ='356';
       objeto.value='0';
       return true;
     }
   
     else
     {
        resultado = saldo - objeto.value;
   	
     }
     
   }
  
     //valido decimal
   if(decimal(objeto,objeto.value))
   {
      return true;
   }
   
   
   //valor valido
   if(valido(objeto))
   {
      return true;
   }


}


//se verifica el valor a retirar
function minimo2(min,objeto,saldo,neto,tipovalor,minimocontrato)
{ 
   
   //si el retiro es mayor que elsaldo disponible
  
   if(tipovalor.value == "STV002")//neto
   {
     if(objeto.value>neto)
     {
       alert("Retiro no permitido, excede el saldo disponible neto."+neto);
       objeto.focus();
       objeto.select();
       objeto.value ='356';
       objeto.value='0';
       return true;
     }
    
   }
   if(tipovalor.value == "STV001")//bruto
   {
     if(objeto.value>saldo)
     {
       alert("Retiro no permitido, excede el saldo disponible bruto."+saldo);
       objeto.focus();
       objeto.select();
       objeto.value ='356';
       objeto.value='0';
       return true;
     }
     
   }
  
}

//valido valores validos
function valido(objeto)
{

  if(isFinite(objeto.value)==false)
  {
     alert(' Introduzca los valores validos porfa');
     objeto.focus();
     objeto.select();
     objeto.value ='356';
     objeto.value='0';
     return true;	  
  } 
  return false;
}

//validar punto en valores decimales
function decimal(objeto,valor)
{

  decallowed = 2;  //decimales 

  if (valor.indexOf(',')!= -1) 
  {
     alert(' Introduzca los valores decimales con punto  ');
     objeto.focus();
     objeto.select();
     objeto.value ='356';
     objeto.value='0';
     return true;	  
  }
  else//500
  {
     if (valor.indexOf('.') != -1) 
     {
        dectext = valor.substring(valor.indexOf('.')+1, valor.length);  
   
         if (dectext.length> decallowed )
         {
   
            alert ("Por favor digite un número con " + decallowed + " decimales.Intente de nuevo."); 
            objeto.select();
            objeto.focus();
            objeto.value ='356';
            objeto.value='0';
            return true;  
         }

      }    
   }
   return false;
}

//validar valores en distribucion de fondos por valor
function validar(saldo , fondo ,retiro, porcentaje, control, valor,maximo)
{
  var v_paso = 1; 
  var valor2
  var valper
  var val95
  var saldototal
  var valpermitido;
  //valido decimal
  if(decimal(control,control.value))
  {
     return true; 
  } 
  //valor valido
  if(valido(control))
  {
     return true;
  }

  //si es negativo
  if (negativo(control))
  {
      return 1; 
  }

  if(saldo != 0)
  {
  	
   val95   = (fondo *0.90);
    //minimo permitido a dejar en el fondo
    saldototal =  saldo - valor
    valor2 = (saldototal *(porcentaje/100));
    valper = fondo - valor2;
    
    if(retiro > fondo)
   { 
     alert("Usted desea retirar el $"+formatCurrency(retiro)+", esto supera el saldo total  del fondo.");
     control.value='0';
     return 1; 
   }
    if (retiro >valper)
    {
      alert("Solo es permitido retirar $"+formatCurrency(valper)+" del fondo.");
      control.value='0';
      return 1; 
    }
    
    if (retiro >val95) 
   {
   	
     v_paso =1; 
     alert("Debe tener precaución por que el retiro esta superando el 90% del fondo $"+formatCurrency(val95)+".");
     
     
   }
   
   if(v_paso == 1)
   {	
     totales(valor,maximo);
   }
  }
 else
 {
     alert("Retiro no  permitido,saldo disponible "+formatCurrency(saldo));
     control.value='0'
    return true;
 }
}



//dar formato a los numeros
function formatCurrency(num) 
{

  num = num.toString().replace(/\$|\,/g,'');
  if(isNaN(num)) num = "0";
  
  cents = Math.floor((num * 100 + 0.5) % 100);
  num = Math.floor((num * 100 + 0.5) / 100).toString(); 
  if(cents < 10) cents = "0" + cents;
 
  for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++) 
  num = num.substring(0,num.length - (4 * i + 3))+','+num.substring(num.length-(4 * i + 3));
  return ("" + num + "." + cents);
}// return ("$" + num + "." + cents);
  
//suma en distribucion de fondos por valor
function totales (valor,maximo)
{ 
  total = 1 * 0; 
 
  for(i=0;i<maximo;i++)
  { 
     if(document.v_codigo.item(i).value>0)
        total = total + (document.v_codigo.item(i).value * 1);
  } 

  if(total>valor)
  {
     alert("Mayor valor que el retiro. Total a retirar $"+valor+"");
      for(i=0;i<maximo;i++){
          document.v_codigo.item(i).value=0; 
          document.v_codigo.totalesDesde.value=0;}//600
      return true; 
  }
  else
  {      
      document.v_codigo.totalesDesde.value = (total);
  }
}

  
//validar valor para porcentaje
function validar2(saldo , fondo ,retiro, porcentaje, control, maximo,valret)
{
	
  var valor2
  var valor3
  var valor4
  var valper
  var saldototal
  var valpesos
  var val95
   var v_paso = 1; 
  //valido decimal
  if(decimal(control,control.value))
  {
     return true;
  }
  //valor valido 
  if(valido(control))
  {
     return true;
  }


  //si es negativo
  if (negativo(control))
  {
      return 1;
  }

  if(retiro>100)
  {
    alert("Excede el porcentaje");
    retiro.value = 0 ;
    return 1; 
  }

  if(saldo !=0)
  {
   valor2 = (valret *(retiro/100));
   val95   = (fondo * 0.90);
    
   if(valor2 > fondo)
   { 
     alert("Usted desea retirar el "+retiro+"% del fondo que equivale a $"+formatCurrency(valor2)+", esto supera el saldo total  del fondo.");
     control.value='0';
     return 1; 
   }
    
    
   saldototal = saldo - valret;
   valor3 = (saldototal *(porcentaje/100));
   valper = fondo - valor3;
   
   
   if (valor2>valper) 
   { 
   
     alert("No es permitido,se debe dejar mínimo $"+formatCurrency(valor3)+"  en el saldo total del fondo.");
     control.value='0';
     return 1; 
   }
   if ( valor2 >val95) 
   { 
      v_paso =1; 
      alert("Debe tener precaución por que el retiro esta superando el 90% del fondo $"+formatCurrency(val95)+".");
   }
   if(v_paso == 1)
   {	
     totales2(maximo);
   }
   
 }
 else
 {
  alert("Retiro no  permitido,saldo disponible "+formatCurrency(saldo));
   return true;

 }
}

//suma de  porcentaje
function totales2 (maximo){ 
 total = 1 * 0; 
 
 
for(i=0;i<maximo;i++)
{ 
    if(document.v_codigo.item(i).value>0)
         total = total + (document.v_codigo.item(i).value * 1);
} 

if(total>100)
{
 alert(" Excede el 100%. ");
 for(i=0;i<maximo;i++)document.v_codigo.item(i).value=0; 
 document.v_codigo.totalesDesde.value=0;
 return 0;
}
else      
{
document.v_codigo.totalesDesde.value = (total);
}
}


function fillWords2(objeto)
  {   
    if (objeto != "")
    {
    	
    	
   
       if(objeto.substring(2,8)=='STA001')
       { 
             document.all.section5.style.display = 'block';
       } 
       if(objeto.substring(2,8)=='STA002')
       { 
             document.all.section5.style.display = 'none';
       } 
       if(objeto.substring(2,8)=='STA003')
       { 
             document.all.section5.style.display = 'none';
             document.all.section6.style.display = 'block';
       } 

   return true;
  } 
 return false;
 }



function fillWords3(objeto)
  {   
    if (objeto.length==5){
    	 document.all.section6.style.display = 'block';
		    return true;
    } 
    else{ 
         document.all.section6.style.display = 'none';
		 return true;
    } 
	return false;
 }

