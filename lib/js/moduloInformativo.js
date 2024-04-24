function validaGIT(form) {

  form.fechaInicial.req= true;
  form.fechaInicial.tipo= "fecha";
  form.fechaInicial.etiqueta= "Fecha inicial";
  form.fechaFinal.req= true;
  form.fechaFinal.tipo= "fecha";
  form.fechaFinal.etiqueta= "Fecha final";

  form.contrato.req= false;
  form.contrato.tipo= "num";
  form.contrato.etiqueta= "Contrato";
  form.cedula.req= false;
  form.cedula.tipo= "num";
  form.cedula.etiqueta= "Cédula";
  form.nit.req= false;
  form.nit.tipo= "num";
  form.nit.etiqueta= "NIT Compañía";

  for(intElem= 0; intElem < form.elements.length; intElem++) {
    elemento= form.elements[intElem];
    mensaje= validaElem(elemento);
    if(mensaje != "") {
      alert(mensaje);
      elemento.select();
      elemento.focus();
      return false;
    }
  }

  v= comparaFechas(form.fechaInicial,form.fechaFinal);
  if(v != 0 && v != 1) {
    if(v == "ERROR") mensaje= "Error en las fechas.";
    else if(v == -1) mensaje= "La Fecha inicial es posterior a la Fecha final.";

    form.fechaInicial.select();
    form.fechaInicial.focus();
    alert(mensaje);
    return false;
  }

  if(form.producto.selectedIndex != 0 && form.contrato.value=="" && form.nit.value=="") {
    form.contrato.focus();
    alert("Si selecciona un producto, debe indicar un\ncontrato o un NIT de Compañía.");
    return false;
  }

  if(form.contrato.value != "" && form.nit.value != "") {
//    form.contrato.select();
    form.contrato.focus();
    alert("No se puede indicar un contrato y un NIT de Compañía\nal mismo tiempo.");
    return false;
  }

  if(form.cedula.value != "" && form.nit.value != "") {
//    form.cedula.select();
    form.cedula.focus();
    alert("No se puede indicar una cédula y un NIT de Compañía\nal mismo tiempo.");
    return false;
  }

  if(form.producto.selectedIndex == 0 && form.contrato.value != "") {
    form.producto.focus();
    alert("Si indica un contrato debe seleccionar un producto.");
    return false;
  }

  if(form.producto.selectedIndex == 0 && form.nit.value != "") {
    form.producto.focus();
    alert("Si indica un NIT de Compañía debe seleccionar un producto.");
    return false;
  }

  if(form.producto.selectedIndex != 0) {
    if(form.contrato.value != "") {
      ctoTmp= "000000000"+ form.contrato.value;
      form.contrato.value= ctoTmp.substring(ctoTmp.length-9, ctoTmp.length);
      if(form.cedula.value != "") {
        cedTmp= "0000000000000"+ form.cedula.value;
        form.cedula.value= cedTmp.substring(cedTmp.length-13, cedTmp.length);
        form.tipo.value= 4;
      }
      else {
        form.tipo.value= 3;
      }
    }
    else {
      nitTmp= "0000000000000"+ form.nit.value;
      form.nit.value= nitTmp.substring(nitTmp.length-13, nitTmp.length);
      form.tipo.value= 5;      
    }
  }
  else {
    if(form.cedula.value != "") {
      cedTmp= "0000000000000"+ form.cedula.value;
      form.cedula.value= cedTmp.substring(cedTmp.length-13, cedTmp.length);
      form.tipo.value= 2;
    }
    else {
      form.tipo.value= 1;
    }
  }
  return true;
}

function validaGRMM(form) {

  form.medio.req= true;
  form.medio.tipo= "alfanum";
  form.medio.etiqueta= "Información del medio";
  form.anio_gravable.req= true;
  form.anio_gravable.tipo= "num";
  form.anio_gravable.etiqueta= "Año gravable";
  form.monto_referencia.req= true;
  form.monto_referencia.tipo= "num";
  form.monto_referencia.etiqueta= "Monto de referencia";
  form.archivo.req= true;
  form.archivo.tipo= "alfanum";
  form.archivo.etiqueta= "Nombre de archivo";

  for(intElem= 0; intElem < form.elements.length; intElem++) {
    elemento= form.elements[intElem];
    mensaje= validaElem(elemento);
    if(mensaje != "") {
      alert(mensaje);
      elemento.select();
      elemento.focus();
      return false;
    }
  }

  if(form.medio.value.length < 4) {
    form.medio.focus();
    alert("El campo información del medio\ndebe tener cuatro caracteres");
    return false;
  }

  if(form.anio_gravable.value.length < 4) {
    form.anio_gravable.focus();
    alert("El campo Año gravable\ndebe tener cuatro caracteres");
    return false;
  }

  return true;
}

function validaCRF2(form) {

  form.archivo.req= true;
  form.archivo.tipo= "alfanum";
  form.archivo.etiqueta= "Nombre del archivo";

  for(intElem= 0; intElem < form.elements.length; intElem++) {
    elemento= form.elements[intElem];
    mensaje= validaElem(elemento);
    if(mensaje != "") {
      alert(mensaje);
      elemento.select();
      elemento.focus();
      return false;
    }
  }
  return true;
}

function validaIC(form) {
  form.fecha_contabilizacion.req= true;
  form.fecha_contabilizacion.tipo= "fecha";
  form.fecha_contabilizacion.etiqueta= "Fecha de Contabilización";

  for(intElem= 0; intElem < form.elements.length; intElem++) {
    elemento= form.elements[intElem];
    mensaje= validaElem(elemento);
    if(mensaje != "") {
      alert(mensaje);
      elemento.select();
      elemento.focus();
      return false;
    }
  }
  
  return true;
}

function validaIC2(form) {
  form.fecha_contabilizacion.req= true;
  form.fecha_contabilizacion.tipo= "fecha";
  form.fecha_contabilizacion.etiqueta= "Fecha de Contabilización";

  for(intElem= 0; intElem < form.elements.length; intElem++) {
    elemento= form.elements[intElem];
    mensaje= validaElem(elemento);
    if(mensaje != "") {
      alert(mensaje);
      elemento.select();
      elemento.focus();
      return false;
    }
  }
  disableButtons(form);
  return true;
}

function disableButtons(theform) 
{
		
	for (i = 0; i < theform.length; i++) 
	{
		var tempobj = theform.elements[i];
		if (tempobj.type.toLowerCase() == "submit" || tempobj.value.toLowerCase() == "cancelar")
		tempobj.disabled = true;
	}
	return true;
}


function validaElem(element) {

  if(element.type == "text") {
    if(element.req == true && element.value == "")
      return "El campo "+ element.etiqueta +"\nes obligatorio."
    if(element.tipo=="fecha")
      return validaFecha(element);
    if(element.tipo=="num")
      return validaNum(element);
    if(element.tipo=="alfanum")
      return validaAlfanum(element);
  }
  return "";
}

function validaNum(element) {
  caracteres= "-1234567890";

  strNumero= new String(element.value);
  for(i= 0; i< strNumero.length; i++) {
    if(caracteres.indexOf(strNumero.charAt(i)) == -1)
      return "El campo "+ element.etiqueta +"\ntiene formato incorrecto.";
  }
  return "";
}

function validaAlfanum(element) {
  caracteres= "-_1234567890ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz";

  strCaracter= new String(element.value);
  for(i= 0; i< strCaracter.length; i++) {
    if(caracteres.indexOf(strCaracter.charAt(i)) == -1)
      return "El campo "+ element.etiqueta +"\ntiene formato incorrecto.";
  }
  return "";
}

function comparaFechas(fechaInicial, fechaFinal) {
  var strAnioI, strMesI, strDiaI, strAnioF, strMesF, strDiaF;
  var intAnioI, intMesI, intDiaI, intAnioF, intMesF, intDiaF;
  var strFechaInicial= fechaInicial.value;
  var strFechaFinal= fechaFinal.value;
  var strSeparadores= new Array("-"," ","/",".");
  var intElement, err= 0;

  mensaje= validaFecha(fechaInicial) + validaFecha(fechaFinal);

  if(mensaje != "")
    return "ERROR";

  for(intElement= 0; intElement< strSeparadores.length; intElement++) {
    if(strFechaInicial.indexOf(strSeparadores[intElement]) != -1) {
      strFechasI = strFechaInicial.split(strSeparadores[intElement]);
      strAnioI= strFechasI[0];
      strMesI= strFechasI[1];
      strDiaI= strFechasI[2];
    }
    if(strFechaFinal.indexOf(strSeparadores[intElement]) != -1) {
      strFechasF = strFechaFinal.split(strSeparadores[intElement]);
      strAnioF= strFechasF[0];
      strMesF= strFechasF[1];
      strDiaF= strFechasF[2];
    }
  }
  intDiaI= parseInt(strDiaI, 10);
  intMesI= parseInt(strMesI, 10);
  intAnioI= parseInt(strAnioI, 10);
  intDiaF= parseInt(strDiaF, 10);
  intMesF= parseInt(strMesF, 10);
  intAnioF= parseInt(strAnioF, 10);

  if(intAnioI > intAnioF) {
    return -1;
  }
  else {
    if(intAnioI == intAnioF) {
      if(intMesI > intMesF) {
        return -1;
      }
      else {
        if(intMesI == intMesF) {
          if(intDiaI > intDiaF) {
            return -1;
          }
          else {
            if(intDiaI == intDiaF) {
              return 0;
            }
            else {
              return 1;
            }
          }
        }
        else {
          return 1;
        }
      }
    }
    else {
      return 1;
    }
  }
}

function validaFecha(fecha) {
  var strFechas, strFecha, strDia, strMes, strAnio;
  var intDia, intMes, intAnio;
  var found= false;
  var strSeparadores= new Array("-"," ","/",".");
  var intElement, err= 0;
  
  strFecha= fecha.value;

  for(intElement= 0; intElement< strSeparadores.length; intElement++) {
    if(strFecha.indexOf(strSeparadores[intElement]) != -1) {
      strFechas = strFecha.split(strSeparadores[intElement]);
      if(strFechas.length != 3) {
        err= 1;
        return "El campo "+ fecha.etiqueta +"\ntiene formato incorrecto.";
      }
      else {
        strAnio= strFechas[0];
        strMes= strFechas[1];
        strDia= strFechas[2];
      }
      found= true;
    }
  }
  if(found == false) {
    return "El campo "+ fecha.etiqueta +"\ntiene formato incorrecto.";
  }
  intDia= parseInt(strDia, 10);
  if(isNaN(intDia)) {
    err= 2;
    return "El campo "+ fecha.etiqueta +"\ntiene formato incorrecto.";
  }
  intMes= parseInt(strMes, 10);
  if(isNaN(intMes)) {
    err = 3;
    return "El campo "+ fecha.etiqueta +"\ntiene formato incorrecto.";
  }
  intAnio = parseInt(strAnio, 10);
  if(isNaN(intAnio)) {
    err = 4;
    return "El campo "+ fecha.etiqueta +"\ntiene formato incorrecto.";
  }
  if(intMes> 12 || intMes< 1) {
    err = 5;
    return "El campo "+ fecha.etiqueta +"\ntiene formato incorrecto.";
  }
  if((intMes == 1 || intMes == 3 || intMes == 5 || intMes == 7 || intMes == 8 || intMes == 10 || intMes == 12) && (intDia > 31 || intDia < 1)) {
    err = 6;
    return "El campo "+ fecha.etiqueta +"\ntiene formato incorrecto.";
  }
  if((intMes == 4 || intMes == 6 || intMes == 9 || intMes == 11) && (intDia > 30 || intDia < 1)) {
    err = 7;
    return "El campo "+ fecha.etiqueta +"\ntiene formato incorrecto.";
  }
  if(intMes == 2) {
    if (intDia < 1) {
      err = 8;
      return "El campo "+ fecha.etiqueta +"\ntiene formato incorrecto.";
    }
    if(LeapYear(intAnio) == true) {
      if(intDia > 29) {
        err = 9;
        return "El campo "+ fecha.etiqueta +"\ntiene formato incorrecto.";
      }
    }
    else {
      if(intDia > 28) {
        err = 10;
        return "El campo "+ fecha.etiqueta +"\ntiene formato incorrecto.";
      }
    }
  }
  return "";
}

function LeapYear(intAnio) {
  if(intAnio % 100 == 0) {
    if(intAnio % 400 == 0) { return true; }
  }
  else {
    if ((intAnio % 4) == 0) { return true; }
  }
  return false;
}
