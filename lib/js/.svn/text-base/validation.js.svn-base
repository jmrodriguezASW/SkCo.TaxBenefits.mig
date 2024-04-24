  <!--
function formValidation(form) {
	var num_fields = form.elements.length;
	var eval_str;
	var field_name;
	var val_rule;
	var field_value;
	var i;
	for (i = 0; i <= num_fields-1; i ++) {
		if (form.elements[i].id != '') {
			eval_str = form.elements[i].id;
			field_name = eval_str.slice(15);
			field_value = form.elements[i].value;
			//Check for required fields
			val_rule = eval_str.slice(0,1);
			if (val_rule == 'R') {
				if (field_value == '') {
					alert('Por favor complete el campo ' + field_name);
					form.elements[i].focus();
					enableButtons(form);
					return false;
				}
			}
			
			//Check for numeric fields
			val_rule = eval_str.slice(1,2);
			if (val_rule == 'N') {
				if (isNaN(field_value)) {
					alert('El campo ' + field_name + ' es numérico');
					form.elements[i].focus();
					form.elements[i].select();
					enableButtons(form);
					return false;
				}
			}

		}
	}

	test = dateValidation(form.ANNO.value, form.MES.value, form.DIA.value);
     if (!test) {
			enableButtons(form);
			return false;
		}


}
function enableButtons(form) {
	var num_fields = form.elements.length;
	var i;
	for (i = 0; i <= num_fields - 1; i ++) {
		if (form.elements[i].type == 'button' || form.elements[i].type == 'submit' || form.elements[i].type == 'reset') {
			form.elements[i].disabled = false;
		}
	}
}
function dateValidation(year_val, month_val, day_val) {
	var myDayStr = day_val;
	var myMonthStr = month_val;
	var myYearStr = year_val;
        if(month_val = '01')
           myMonthStr = 'Jan';
        else if(month_val = '02')
                myMonthStr = 'Feb';
             else if(month_val = '03')
                    myMonthStr = 'Mar';
                  else if(month_val = '04')
                         myMonthStr = 'Apr';
                       else if(month_val = '05')
                               myMonthStr = 'May';
                            else if(month_val = '06')
                                   myMonthStr = 'Jun';
                                 else if(month_val = '07')
                                         myMonthStr = 'Jul';
                                      else if(month_val = '08')
                                               myMonthStr = 'Aug';
                                           else if(month_val = '09')
                                                   myMonthStr = 'Sep';
                                                else if(month_val = '10')
                                                       myMonthStr = 'Oct';
                                                     else if(month_val = '11')
                                                            myMonthStr = 'Nov';
                                                          else if(month_val = '12')
                                                               myMonthStr = 'Dec';
       var myDateStr = myDayStr + ' ' + myMonthStr + ' ' + myYearStr;
	/* Using form values, create a new date object
	which looks like Wed Jan 1 00:00:00 EST 1975. */
	var myDate = new Date(myDateStr);
	//var myDate = new Date(year_val, month_val, day_val);
	
	// Convert the date to a string so we can parse it.
	var myDate_string = myDate.toGMTString();

	/* Split the string at every space and put the values into an array so,
	using the previous example, the first element in the array is Wed, the
	second element is Jan, the third element is 1, etc. */
	var myDate_array = myDate_string.split( ' ' );

	/* If we entered Feb 31, 1975 in the form, the new Date() function
	converts the value to Mar 3, 1975. Therefore, we compare the month
	in the array with the month we entered into the form. If they match,
	then the date is valid, otherwise, the date is NOT valid. */
	if ( myDate_array[2] != myMonthStr ) {
	  alert( 'La fecha ' + myDateStr + ' no es válida' );
	  return false
	}
	return true;
}


function chkDate(form,fecha) {
	var sDate;
	var qDate;
	sDate = new Date('"+fecha+"');
	qDate = new Date(form.ANNO.value + form.MES.value + form.DIA.value);
	if (qDate - sDate < 0) {
		alert('La fecha seleccionada es anterior a la fecha de inicio del contrato');
		return false;
	};
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
// -->