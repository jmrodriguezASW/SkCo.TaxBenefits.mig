
function checkdate(objName) {
var datefield = objName;
if (chkdate(objName) == false) {
datefield.select();
alert("La fecha es invalida");
//datefield.value="";
datefield.focus();
return false;
}
}


function fechavalida(objName) {
var datefield = objName;
if (chkdate(objName) == false) {
datefield.select();
alert("La fecha es invalida");
//datefield.value="";
datefield.focus();
return false;
}
}

function fechavalida2(objName) {
var datefield = objName;
if (chkdate(objName) == false) {
datefield.select();
alert("La fecha es invalida");
//datefield.value="";
datefield.focus();
return false;
}
}


function chkdate(objName) {
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
var strSeparatorArray = new Array("-"," ","/",".");
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
strMonthArray[11] = "12";

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

function esNumero(objeto)
{
	
if (isFinite(objeto.value)==false){
    	    objeto.select();
    	    alert("Introduzca valores numericos");
    	    objeto.value="";
	    objeto.focus();
  return true;
  }
return false;
}



function esMayor(objeto)
{
	
if (objeto.value>100){
	
  objeto.select();
  alert("Introduzca valores menores a 100");
  objeto.value="";
  objeto.focus();
 return true;
 }
if (objeto.value<0){
objeto.select();
alert("No introducir valores negativos");
objeto.value="";
objeto.focus();
return true;
}
return false;
}


function esMayoraO(objeto)
{
	
if (objeto.value==0){
	
  objeto.select();
  alert("Introducir valor diferente a 0");
  objeto.value="";
  objeto.focus();
 return true;
 }
return false;
}

function esNegativo(objeto)
{
	
if (objeto.value<0){
objeto.select();
alert("No introducir valores negativos");
objeto.value="";
objeto.focus();
return true;
}
return false;
}




function checkrequired(which) 
{
var pass=true;
if (document.images) 
{
 for (i=0;i<which.length;i++) 
 {
  var tempobj=which.elements[i];
  if ((tempobj.name.substring(0,11)=="obligatorio")) 
  {
  	
   if (((tempobj.type=="text"||tempobj.type=="textarea")&&tempobj.value==""))
   {
    pass=false;
    break;
   }
   else
   {
    if (v_codigo.obligatoriofecha != null) 
    {    
   	 if (fechavalida(v_codigo.obligatoriofecha) == false) 
     {
      v_codigo.obligatoriofecha.select();
      v_codigo.obligatoriofecha.focus();
      v_codigo.obligatoriofecha.value = '';
      return false;
     }
   	} 
   }
  }
 }
}
if (!pass) {
shortFieldName=tempobj.name.toUpperCase();
alert("Verifique que todos los campos con asterisco esten con datos");
return false;
}
else
return true;
}




function formatCurrency(num) {
num = num.toString().replace(/\$|\,/g,"");
if(isNaN(num)) num = "0";
cents = Math.floor((num*100+0.5)%100);
num = Math.floor((num*100+0.5)/100).toString();
if(cents < 10) cents = "0" + cents;
for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
num = num.substring(0,num.length-(4*i+3))+","+num.substring(num.length-(4*i+3));
return (num + "." + cents);
}



function IsValidTime(timeStr) {
var timePat = /^(\d{1,2}):(\d{2})(:(\d{2}))?(\s?(AM|am|PM|pm))?$/;
var matchArray = timeStr.match(timePat);
if (matchArray == null) {
alert("No es un formato valido.");
return false;
}
hour = matchArray[1];
minute = matchArray[2];
if (hour < 0  || hour > 23) {
alert("La hora debe ser en tre 0 y 23");
return false;
}
if (minute<0 || minute > 59) {
alert ("Los minutos son entre 0 y 59.");
return false;
}
return false;
}


function noExcede(porcentaje, val)
{
	
 if (porcentaje.value>val)
   {
   porcentaje.select();
   alert("No introducir valores mayores a " +val);
   porcentaje.value="";
   porcentaje.focus();	 
   }
 if (porcentaje.value<0)
   {
   porcentaje.select();
   alert("No introducir valores negativos");
   porcentaje.value="";
   porcentaje.focus();
   return true;
   }
}

function checkDecimals(fieldName, fieldValue, dec) {
decallowed = dec;  // how many decimals are allowed?

if (isNaN(fieldValue) || fieldValue == "") {
fieldName.select();
alert("No es un número valido");
fieldName.value="";
fieldName.focus();
}
else {
if (fieldValue.indexOf('.') == -1) fieldValue += ".";
dectext = fieldValue.substring(fieldValue.indexOf('.')+1, fieldValue.length);
if (dectext.length > decallowed)
{
fieldName.select();
alert ("Entre valores con " + decallowed + " decimales.");
fieldName.value="";
fieldName.focus();
      }
   }
}
function checkano(objeto) {
	
if (objeto.value.length < 4)
{
ano.select();
alert ("Digite valores validos.");
ano.value="";
ano.focus();
}
}
