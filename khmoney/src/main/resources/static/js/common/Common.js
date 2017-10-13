var Common;
if (!Common) Common = {};
if (!Common.optionDatePicker){
	Common.optionDatePicker = {
		changeMonth : true,
		changeYear  : true,
		showOn: "button",
		buttonImage: "../img/icon/ico_calendar.png",
		buttonImageOnly: true,
		buttonText: "Select date",
		monthNames  : ['ខែ01','ខែ02','ខែ03','ខែ04','ខែ05','ខែ06','ខែ07','ខែ08','ខែ09','ខែ10','ខែ11','ខែ12'],
		monthNamesShort : ['ខែ01','ខែ02','ខែ03','ខែ04','ខែ05','ខែ06','ខែ07','ខែ08','ខែ09','ខែ10','ខែ11','ខែ12'],
		dayNames : ['ច័ន្ទ', 'អង្គារ៍', 'ពុធ', 'ព្រហស្បិ៍ត', 'សុក្រ', 'សៅរិ៍', 'អាទិត្យ'],
		dayNamesShort : ['អា','ច', 'អ', 'ព', 'ហ', 'សុ', 'ស'],
		dayNamesMin : ['អា','ច', 'អ', 'ព', 'ហ', 'សុ', 'ស'],
		yearRange: '2009:9999',
		dateFormat : 'dd/mm/yy'/*,
		maxDate : 'getDate'*/
	}
}
Common.datePickerRang = function(dpStart,dpEnd){
	$('#'+dpEnd).datepicker(Common.optionDatePicker).datepicker("setDate", new Date());	
	var dt = new Date();
	var ndt= new Date(dt.getFullYear(),dt.getMonth() -1,dt.getDate());
	$('#'+dpStart).datepicker(Common.optionDatePicker).datepicker("setDate", ndt);
}
Common.datePicker = function(dpStart){
	$('#'+dpStart).datepicker(Common.optionDatePicker).datepicker("setDate", new Date());	
}
Common.numberWithComma = function (number,str){
	if (typeof str == 'undefined') str = ',';
	return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, str);
}
Common.formatDateToString = function(str){
	if (typeof str != null && str != '' && str.length == 10){
		return str.replace(/(\d{2})\/(\d{2})\/(\d{4})/g, '$3$2$1');
	}
	return '';
}
Common.leftPage = function(str,number,symbol){
	  if (typeof symbol == 'undefined') symbol = "0";
	  str = str.toString();
	  return str.length < number ? Common.leftPage(symbol + str, number) : str;

}
Common.phoneNumberWithComma = function (number,str){
	if (typeof str == 'undefined' ) str = '-';
	 return number.toString().replace(/(\d{3})(\d{3})(\d{4})/, '$1'+str+'$2'+str+'$3');
}
Common.phoneWithComma = function (number,str){
	var result = number;
	var a1 ='',a2='',a3='';
	if (typeof str == 'undefined' ) str = '-';
	if (number.length < 7 && number.length > 3){
		a1 = result.toString().substring(0,3);
		a2 = result.toString().substr(3,result.toString().length);
		return a1 + str + a2;
	} else	if (number.length >= 7){
		a1 = result.toString().substring(0,3);
		a2 = result.toString().substring(3,6);
		a3 = result.toString().substring(6,result.toString().length);
		return a1 + str + a2 + str + a3;
	}else{
		return number;
	}
}

Common.khmerMoney = function (str){
	var result   = '';
	var txt = '';
	var million  = '';
	var thousand = '';
	str = str.toString();
	if (str.length <= 3){
		txt = Common.ConvertNumber(str,0);
	}else if (str.length <= 6){		
		txt = Common.ConvertNumber(str.substr(str.length - 3,str.length),0);
		thousand = Common.ConvertNumber(str.substr(0,str.length - 3),1);
		
	}else if (str.length > 6 && str.length <= 9){
		
		txt = Common.ConvertNumber(str.substr(str.length - 3,str.length),0);
		thousand = Common.ConvertNumber(str.substr(str.length - 6,str.length-3),1);
		million = Common.ConvertNumber(str.substr(0,str.length - 6),2);		
	}
	
	return million+' '+thousand+' '+txt;
	
}

Common.ConvertNumber = function (str,num){
	var a1 = '';
	var a2 = '';
	var a3 = '';
	var result = '';
	if (str.length == 1){
		if (str.charAt(2) == '0' || typeof str.charAt(2) == 'undefined'){
			a1 = '';
		}else{
			a1 = Common.ConvertNumberToLatter(str.charAt(0))
		}
	}else if (str.length == 2){
		if (str.charAt(1) == '0' || typeof str.charAt(1) == 'undefined'){
			a2 = '';
		}else{
			a2 = Common.ConvertNumberToLatter(str.charAt(1));
		}
		if (str.charAt(2) == '0' || typeof str.charAt(2) == 'undefined'){
			a1 = '';
		}else{
			a1 = Common.ConvertNumberToTwoDigit(str.charAt(0));
		}
	}else{
		if (str.charAt(2) == '0' || typeof str.charAt(2) == 'undefined'){
			a3 = '';
		}else{
			a3 = Common.ConvertNumberToLatter(str.charAt(2));
		}
		if (str.charAt(1) == '0' || typeof str.charAt(1) == 'undefined'){
			a2 = '';
		}else{
			a2 = Common.ConvertNumberToTwoDigit(str.charAt(1));
		}
		if (str.charAt(0) == '0' || typeof str.charAt(0) == 'undefined'){
			a1 = '';
		}else{
			a1 = Common.ConvertNumberToLatter(str.charAt(0))+'រយ'
		}
	}
	if (num == 1){
		if (a1 == '' && a2 == '' && a3 == ''){
			result = '';
		}else{
			result = a1+''+a2+''+a3 +'ពាន់';
		}
		
	}else if (num == 2){
		result = a1+''+a2+''+a3 +'លាន';
	}else{
		result = a1+''+a2+''+a3;
	}
	return result; 
}

Common.ConvertNumberToLatter = function (ch){
	var str = '';
	if (ch == '1'){
		str = 'មួយ';
	}else if (ch == '2'){
		str = 'ពីរ';
	}else if (ch == '3'){
		str = 'បី';
	}else if (ch == '4'){
		str = 'បួន';
	}else if (ch == '5'){
		str = 'ប្រាំ';
	}else if (ch == '6'){
		str = 'ប្រាំមួយ';
	}else if (ch == '7'){
		str = 'ប្រាំពីរ';
	}else if (ch == '8'){
		str = 'ប្រាំបី';
	}else if (ch == '9'){
		str = 'ប្រាំបួន';
	}	
	return str;
	
}
Common.ConvertNumberToTwoDigit = function (ch){
	var str = '';
	if (ch == '1'){
		str = 'ដប';
	}else if (ch == '2'){
		str = 'មួយម្ភៃ';
	}else if (ch == '3'){
		str = 'សាមសិប';
	}else if (ch == '4'){
		str = 'សែសិប';
	}else if (ch == '5'){
		str = 'ហាសិប';
	}else if (ch == '6'){
		str = 'ហុកសិប';
	}else if (ch == '7'){
		str = 'ចិតសិប';
	}else if (ch == '8'){
		str = 'ប៉ែតសិប';
	}else if (ch == '9'){
		str = 'កៅសិប';
	}	
	return str;
}

Common.ConvertZeroTwoDigit = function (str){
	var one  = '0';
	var two  = '0';
	var result = '0';
	if (typeof str == 'undefined' || str.length < 3){
		return str;
	}
	one = str.substr(str.length - 1,1);
	two = str.substr(str.length - 2,1);
	result = str.substr(0,str.length - 2) + '00';
	if(one >= 5){
		two = parseInt(two) + 1;
	}
	if (two >= 5 ){
		result = parseInt(result) + 100;
	}
	return result;
}
Common.ConvertDayToKhmer = function(str){
	var ch = '';
	if (typeof str == 'undefined') return '';
	if (str.getDay() == '0' ){
		ch = 'អា'
	}else if (str.getDay() == '1'){
		ch = 'ច'
	}else if (str.getDay() == '2'){
		ch = 'អ'
	}else if (str.getDay() == '3'){
		ch = 'ពុ'
	}else if (str.getDay() == '4'){
		ch = 'ព្រ'
	}else if (str.getDay() == '5'){
		ch = 'សុ'
	}else if (str.getDay() == '6'){
		ch = 'ស'
	}
	return ch;
}
