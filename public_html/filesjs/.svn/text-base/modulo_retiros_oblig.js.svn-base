//Se muestra u oculta la seccion de los datos del tercero OBLIG
 function fillWords(objeto)
  {       
    if (objeto=='2'){
    	 document.all.section6.style.display = 'block';         
        return true;
    } 
    else{ 
         document.all.section6.style.display = 'none';
         document.all.v_doc_ter.value = '';
         document.all.v_nomb_terc.value = '';
         document.all.v_apell_terc.value = '';
         return true;
    } 
	return false;
 }

//se verifica el valor a retirar para el producto OBLIG (FPOB y FPAL) de acuerdo al concepto a retirar AVA o AVE
function minimo(objVrSolicitado, objvsaldispo)
{
   var disponible = Number(parseFloat(objvsaldispo.value).toFixed(2));
   var vlrSolicitado = Number(parseFloat(objVrSolicitado.value).toFixed(2));

   if (vlrSolicitado<0)
   {
       alert('El valor no puede ser negativo');
       objVrSolicitado.focus();
       objVrSolicitado.select();
       objVrSolicitado.value='0';
       return true;
   }
   
   if(valido(objVrSolicitado))
   {
      return true;
   }
   
   if (vlrSolicitado==0)
   {
       alert('Favor Digitar el Valor a Retirar');
       objVrSolicitado.focus();
       objVrSolicitado.select();
       objVrSolicitado.value='0';
       return true;
   }    
    
    if(vlrSolicitado>disponible)
    {               
        alert("Retiro no permitido, el saldo disponible para el contrato son $"+formatCurrency(disponible)+" y usted desea retirar $"+formatCurrency(vlrSolicitado));
        objVrSolicitado.focus();
        objVrSolicitado.select();
        objVrSolicitado.value='0';
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
}

function validarFecha_tiporetiro(fecha,tiporetiro) { 
        
    if ((tiporetiro.value.length<=2)) {
        alert("Seleccione el campo Tipo de Retiro"); 
        return false;
    }  
        
    if ((fecha.value=="")) {
        alert("Fecha de solicitud ingresada no es válida."); 
        return false;
    } 
        
    var fechavalidacion=new Date();
    var fechahoy = new Date();    
    fechavalidacion.setDate(fechahoy.getDate() - 180);
    var vectorfecha =fecha.value.split('-');    
    var fechainput = new Date(vectorfecha[0],vectorfecha[1]-1,vectorfecha[2]);
            
    if (fechainput < fechavalidacion || fechainput >fechahoy || tb_ffecha(fecha) == false) {
        alert("Fecha de solicitud ingresada no es válida."); 
        fecha.value = '';
        return false;    
    }               
    
  return true;
}

function MensajeAVE() { 
            
  alert("Asegúrese de contar con la autorización del empleador.");             
  return true;
}

function validaNumericos(event) {
    if(event.keyCode >= 48 && event.keyCode <= 57){
      return true;
     }
     return false;        
}

function nobackbutton()
 {	
   window.location.hash="no-back-button";
   window.location.hash="Again-No-back-button" //chrome	
   window.onhashchange=function(){window.location.hash="no-back-button";}
 }
 
 //valido valores validos
function valido(objeto)
{
  if(isFinite(objeto.value)==false || objeto.value=="")
  {
     alert(' Introduzca los valores validos por favor');
     objeto.focus();
     objeto.select();
     objeto.value='0';
     return true;	  
  } 
  return false;
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

function validar_retiro(which)
{
  
  v_codigo.aceptar.disabled = true;
  v_codigo.regresar.disabled= true;
}