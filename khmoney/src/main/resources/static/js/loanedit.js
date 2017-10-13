$(document).ready(function(){
	init();	
	$('#id_card').keyup(function(){
		$('#id_card').val(Common.numberWithComma($('#id_card').val().replace(/\-/g, ''),"-"));
	});
	$('#phone').keyup(function(){
		$('#phone').val(Common.phoneWithComma($('#phone').val().replace(/\-/g, ''),"-"));
	});
	
	$('#btn_addMore').click(function(){
		loadingSettingData();
	});
	
	$(document).on('click','.btn_cancel,.btn_close',function(){
		$('.alert_wrap').bPopup().close();
	});
	
	$('#type_payment').change(function(){
		$('#loadingSettingOtherValue').hide();
		loadingSettingValue($('#type_payment').val());
	});
	
	$('input[name="rd_decrement"]').change(function(){
		$('#loading').bPopup();
		if($(this).val() == 'decrement'){
			decrementTypeValue(this);
		}else{
			$('#div_decrement').hide();
			$('#loadingDecrementTypeValue').hide();
		}
		$('#loading').bPopup().close();
	});
	
	$('.btn_confirm').click(function(){
		$('#loading').bPopup();
		confrimCheck();
		$('#loading').bPopup().close();
	});
	
	$('#btn_save').click(function(){		
		loanSaveEdittem();
		
	});
	
});

function init(){
	$('#loading').bPopup();
	loanerGetMaxId();
	Common.datePicker('popup_start_date');
	$('#popup_total_money').autoNumeric('init',{ aSign: '​​ ៛',aSep:',',sGropu:'3',pSign: 's',aPad: false,vMax: '999999999',vMin: '0'});
	$('#popup_time').autoNumeric('init',{ aSign: '​​ ដង',pSign: 's',aPad: false,vMax: '999',vMin: '0'});
	$('#popup_rate').autoNumeric('init', { aSign: ' %',pSign: 's',aPad: false,vMax: '99',vMin: '00.00',mDec: 2,});
	$('#popup_decrement').autoNumeric('init', { aSign: '​​ ៛',aSep:',',sGropu:'3',pSign: 's',aPad: false,vMax: '999999',vMin: '0'});
	$('#loading').bPopup().close();
	
}

function loanerGetMaxId(){
	var data = {
			'loaner_id':$('#loaner_id').val(),
			'loan_id'  :$('#loan_id').val()
	}
	$.ajax({
		type:'GET',
		url :'/loadingLoanEdit',
		data:data,
		success:function(json){
			console.log(json);
			if (json.code == 'undefined'){
				alert(json.message);
				return;
			}
			$('#loaner_code').val(Common.numberWithComma(Common.leftPage(json.object.loanObject.loaner_id,9),'-'));
		    $('#loaner_name').val(json.object.loanObject.loaner_name);
		    $('#id_card').val(Common.phoneWithComma(json.object.loanObject.id_card));
		    $('#phone').val(Common.phoneWithComma(json.object.loanObject.phone));
		    $('input[name=gender][value='+json.object.loanObject.gender+']').prop('checked',true);
		    $('#address').val(json.object.loanObject.address);
		    
		    $('#loan_code').val(Common.numberWithComma(Common.leftPage(json.object.loanObject.loan_id,9),"-"));
		    $('#total_money_txt').val(Common.numberWithComma(json.object.loanObject.total_money) +' ៛');
		    $('#total_money_kh').val(Common.khmerMoney(json.object.loanObject.total_money) + " ៛");
		    $('#time_txt').val(Common.numberWithComma(json.object.loanObject.time) + ' ដង');
		    $('#start_date_txt').val(moment(json.object.loanObject.start_date).format('DD/MM/YYYY'));
		    $('#type_payment_txt').val(json.object.loanObject.type_payment_num);
		    $('#rate_db').val(json.object.loanObject.rate);
		    $('#type_payment_db').val(json.object.loanObject.type_payment);
		    $('#day_db').val(json.object.loanObject.count_day);
		    
		    var tt = ((parseInt(json.object.loanObject.total_money) / parseInt(json.object.loanObject.time) ) * parseFloat(json.object.loanObject.rate)) / 100;
		    tt = Math.round(tt);
		    var total_rate = Common.ConvertZeroTwoDigit(tt+'');
		   if (json.object.loanObject.decrement != 0){
			   tt = (parseInt(json.object.loanObject.total_money) * parseFloat(json.object.loanObject.time)) / 100;
			   total_rate = Common.ConvertZeroTwoDigit(tt+'');
		   }
		   
		    var dt         = moment(json.object.loanObject.start_date).format('DD/MM/YYYY').split('/');
		    var start_date = new Date(dt[2],dt[1]-1,dt[0],0,0,0);
		               day = json.object.loanObject.count_day;
			start_date.setDate(start_date.getDate()+ parseInt(day));
		   $('#first_date_txt').val(moment(start_date).format('DD/MM/YYYY'));
		   $('#rate_money_txt').val(Common.numberWithComma(total_rate) + ' ៛');
		   $('#decrement_txt').val(Common.numberWithComma(json.object.loanObject.decrement) + ' ៛');
		   $('#agent').val(json.object.loanObject.full_name);
		   
		   var tbl = '', d = 0;
			$('#tbl_lst1 tbody').empty();
			$('#tbl_lst1 tbody').show();
		   $.each(json.object.loanPaymentList,function(index,value){
			   var a = 0 ;
				if (parseInt(tt) - parseInt(d) > 0 ){
					a = (parseInt(tt) - parseInt(d)) + parseInt(value.prak_derm) ;
				}
				var b = Common.ConvertZeroTwoDigit(a+'');
				var c = Common.numberWithComma(b) + ' ៛'
				var bg = '';
				if (value.txt == '9'){
					bg = 'payment_already';
				}
			   tbl += '<tr>'
					+'<td class="'+bg+'"><div class="t_center">'+(index+1)+'</div></td>'
					+'<td class="'+bg+'"><div class="t_center">'+Common.ConvertDayToKhmer(start_date)   +'</div></td>'
					+'<td class="'+bg+'"><div class="t_right tbl_start_date">' +moment(start_date).format('DD/MM/YYYY')+'</div></td>'
					+'<td style="display:none;"><div class="t_right tbl_prak_derm"></div></td>'
					+'<td style="display:none;"><div class="tbl_total_rate"></div></td>'
					+'<td class="'+bg+'"><div class="t_right tbl_payment">'+c+'</div></td>'
					+'<td class="'+bg+'"><div></div></td>'
					+'<td style="display:none;"><div class="t_right tbl_left"></div></td>'
					+'<td style="display:none;"><div></div></td>'
					+'<td style="display:none;"><div></div></td>'
				+'</tr>';
				if (index==1){
			    	$('#end_date_db').val(moment(start_date).format('YYYYMMDD'));
			    }
			    start_date.setDate(start_date.getDate() + parseInt(day));	 
			    d = parseInt(d) + parseInt(json.object.loanObject.decrement);	
		   });
			$('#tbl_lst1 tfoot').hide();
			$('#tbl_lst1 tbody').append(tbl);
		},error:function(json){
			console.log(json);
		}
	});
}
/*ប្រភេទបង់ប្រាក់*/
function loadingSettingData(){
	if ($('#loaner_name').val() == '' || $('#phone').val() == '' || $('#id_card').val() == '' || $('#address').val() == ''){
    	alert('សូមបំពេញពត៍មានរបស់អ្នកខ្ចីមុន ទើបអាចបំពេញទឹកប្រាក់ដែលត្រូវខ្ចីបាន!!');
    	return;
    }
	$('#loadingSettingOtherValue').hide();
	$('#loadingDecrementTypeValue').hide();
	$('input:radio[name=rd_decrement]').filter('[value=""]').prop('checked', true);
	$('#div_decrement').hide();
	$.ajax({
		type:'GET',
		url :'/loadingSettingData',
		success:function(json){
			if (json.code == 'undefined'){
				alert(json.message);
				return;
			}
			console.log(json);
			var ListColumns = json.object.ListColumns;
			var settingList = json.object.settingList			
			var opt ='';
			$("#type_payment").empty();
			$.each(ListColumns,function(index, value){
				opt += '<option value="'+value.type+'" data='+value.day+'>'+value.columns+'</option>';
			});
			$("#type_payment").append(opt);			
			loadingSettingValue($('#type_payment').val());
			$('.alert_wrap').bPopup();
		},error:function(json){
			console.log(json);
		}
	});
}

/*ប្រភេទបង់ប្រាក់  loading check time*/
function loadingSettingValue(val){
	$.ajax({
		type:"GET",
		url:'/loadingSettingValue',
		success:function(json){
			if (json.code == 'undefined'){
				alert(json.message);
				return;
			}
			var label='';
			$('#div_time').empty();
			var settingList = json.object.settingList;
			$.each(settingList,function(index, value){
				if (val == value.type){
					label += '<label><input type="radio" name="time" onChange="loadingSettingOtherValue(this)" value="'+value.value+'"> '+value.value+'​ ដង</label>'
					        +'<input type="hidden" value="'+value.rate+'" id="rate_'+value.value+'">&nbsp;&nbsp;&nbsp;&nbsp;';
				}				
			});
			label += '<label><input type="radio" name="time" onchange="loadingSettingOtherValue(this)" value=""> ផ្សេងៗ</label>'
			$('#div_time').append(label);
			
		},error:function(json){
			
		}
	});
}

function loadingSettingOtherValue(obj){
	if ($(obj).val() == ''){
		$('#loadingSettingOtherValue').show();
	}else{
		$('#loadingSettingOtherValue').hide();
	}	
}

function loadingDecrementTypeValue(obj){
	if ($(obj).val() == ''){
		$('#loadingDecrementTypeValue').show();
	}else{
		$('#loadingDecrementTypeValue').hide();
	}	
}


function decrementTypeValue(obj){
	$.ajax({
		type:'GET',
		url:'/decrementTypeValue',
		success:function(json){
			if (json.code == 'undefined'){
				alert(json.message);
				return;
			}
			decrementList = json.object.decrementList;
			var rdi = '';
			$('#div_decrement').show();
			$('#div_decrement').empty();
			$.each(decrementList,function(index,value){
				rdi += '<label><input type="radio" name="decrement"  onChange="loadingDecrementTypeValue(this);" value="'+value.value+'"> '+Common.numberWithComma(value.value)+' រៀល</label>&nbsp;&nbsp;&nbsp;&nbsp;';
			});
			rdi += '<label><input type="radio" name="decrement" value="" onChange="loadingDecrementTypeValue(this);"> ផ្សេងៗ</label>';
			$('#div_decrement').append(rdi);
		},error:function(json){
			
		}
		
	});
}

function confrimCheck(){
	var time,decrement=0,rate,total_money,total_rate,day;
	if ($('#popup_total_money').val() == '')
	{
		alert("Please Input Total Money");
		return;
	}
	if ($('#type_payment').val() == ''){
		alert("Please choose type to pay money!");
		return;
	}
	if (!$('input[name=time]').is(':checked')){
		alert("Please choose time to pay money!")
		return;
	}
	if ($('input[name=time]').is(':checked')){
		if ($('input[name=time]:checked').val() == ''){
			if ($('#popup_time').val() == ''){
				alert("Please input time to pay.");
				return;
			}
			if ($('#popup_rate').val() == ''){
				alert("Please input rat to pay.");
				return;
			}
		}
	}
	if ($('input[name=rd_decrement]:checked').val() == 'decrement'){
		if ($('input[name=decrement]').is(':checked')){
			if ($('input[name=decrement]:checked').val() == ''){
				if ($('#popup_decrement').val() == ''){
					alert("Please input money decrement.");
					return;
				}	
			}
		}	
		if (!$('input[name=decrement]').is(':checked')){
			alert("Please choose money decrement.");
		}
	}
	
	total_money = $('#popup_total_money').val().replace(/[​\u202f\៛\,]/g,'').trim();
	if ($('input[name=time]:checked').val() == ''){
		time = $('#popup_time').val().replace(/[​\u202f\៛\,]/g,'');
		rate = $('#popup_rate').val().replace(/[​\u202f\%\,]/g,'');
	}else{
		time = $('input[name=time]:checked').val();
		rate = $('#rate_'+time).val();
	}
	
	var dt         = $('#popup_start_date').val().split('/');
	var start_date = new Date(dt[2],dt[1]-1,dt[0],0,0,0);
	var end_date   = new Date(dt[2],dt[1]-1,dt[0],0,0,0);
               day = $('#type_payment option:selected').attr('data');
	start_date.setDate(start_date.getDate()+ parseInt(day));
	end_date.setDate(end_date.getDate()+ (parseInt(day) * parseInt(time)))
	
	/*calculate rate and total money*/
	var tt = ((parseInt(total_money) / time ) * parseFloat(rate)) / 100;
	    tt = Math.round(tt);
	var total_rate = Common.ConvertZeroTwoDigit(tt+'');
	var prak_derm  = Math.round(parseInt(total_money) / time);	
	
	if ($('input[name=rd_decrement]:checked').val() == 'decrement'){
		if ($('input[name=decrement]').is(':checked')){
			if ($('input[name=decrement]:checked').val() == ''){
				decrement = $('#popup_decrement').val().replace(/[​\u202f\៛\,]/g,'').trim();
			}else{
				decrement = $('input[name=decrement]:checked').val();
			}
			tt = (parseInt(total_money) * parseFloat(rate)) / 100;
			total_rate = Common.ConvertZeroTwoDigit(tt+'');
		}	
	}
	
	$('#first_date_txt').val(moment(start_date).format('DD/MM/YYYY'));	
	$("#total_money_txt").val($('#popup_total_money').val());
	$('#total_money_kh').val(Common.khmerMoney(total_money) + " ៛");
	$('#type_payment_txt').val($('#type_payment option:selected').text());
	$('#start_date_txt').val($('#popup_start_date').val());
	$('#time_txt').val(time+' ដង');
	$('#rate_money_txt').val(Common.numberWithComma(total_rate)+' ៛')
	$('#decrement_txt').val(Common.numberWithComma(decrement)+ ' ៛')
	$('#rate_db').val(rate);
	$('#type_payment_db').val($('#type_payment option:selected').val());
	$('#end_date_db').val(moment(end_date).format('DD/MM/YYYY'));
	$("#day_db").val(day);
	
	var tbl = '', d = 0;
	$('#tbl_lst1 tbody').empty();
	$('#tbl_lst1 tbody').show();
	for (var i = 1; i <= time; i++){
		var a = 0 ;
		if (parseInt(tt) - parseInt(d) > 0 ){
			a = (parseInt(tt) - parseInt(d)) + parseInt(prak_derm) ;
		}
		var b = Common.ConvertZeroTwoDigit(a+'');
		var c = Common.numberWithComma(b) + ' ៛'
		
		var left =  parseInt(total_money) -  (parseInt(prak_derm) * i)
		
		tbl += '<tr>'
					+'<td><div class="t_center">'+i+'</div></td>'
					+'<td><div class="t_center">'+Common.ConvertDayToKhmer(start_date)   +'</div></td>'
					+'<td><div class="t_right tbl_start_date">' +moment(start_date).format('DD/MM/YYYY')+'</div></td>'
					+'<td style="display:none;"><div class="t_right tbl_prak_derm">'+prak_derm+'</div></td>'
					+'<td style="display:none;"><div class="tbl_total_rate">'+(parseInt(tt) - parseInt(d))+'</div></td>'
					+'<td><div class="t_right tbl_payment">'+c+'</div></td>'
					+'<td><div></div></td>'
					+'<td style="display:none;"><div class="t_right tbl_left">'+left+'</div></td>'
					+'<td style="display:none;"><div></div></td>'
					+'<td style="display:none;"><div></div></td>'
				+'</tr>';
		
		//set end date
	    if (i==time){
	    	$('#end_date_db').val(moment(start_date).format('YYYYMMDD'));
	    }
	    start_date.setDate(start_date.getDate() + parseInt(day));	 
	    d = parseInt(d) + parseInt(decrement);
	   
	}
	$('#tbl_lst1 tfoot').hide();
	$('#tbl_lst1 tbody').append(tbl);
	$('.alert_wrap').bPopup().close();
}

function loanSaveEdittem(){
    if ($('#loaner_name').val() == ''){
    	alert('Please input Loaner Name');
    	return;
    }
    if ($('#phone').val() == ''){
    	alert('Please input Phone Number');
    	return;
    }
    if ($('#id_card').val() == ''){
    	alert('Please input ID Card');
    	return;
    }
    if ($('#address').val() == ''){
    	alert('Please input Address');
    	return;
    }	
	
    $('#loading').bPopup();
	var data = {
			'loaner_id'   :$('#loaner_code').val().replace(/\-/g,'').trim(),
			'loaner_name' :$('#loaner_name').val(),
			'gender'      :$('input[name=gender]:checked').val(),
			'id_card'     :$('#id_card').val().replace(/\-/g,'').trim(),
			'phone'       :$('#phone').val().replace(/\-/g,'').trim(),
			'address'     :$('#address').val(),
			'start_date'  :Common.formatDateToString($('#start_date_txt').val().trim()),
			'end_date'    :$('#end_date_db').val().trim(),
			'loan_id'     :$('#loan_code').val().replace(/\-/g,'').trim(),
			'total_money' :$('#total_money_txt').val().replace(/[​\u202f\៛\,]/g,'').trim(),
			'rate'        :$('#rate_db').val(),
			'type_payment':$('#type_payment_db').val(),
			'time'        :$('#time_txt').val().replace(/[​\u202f\ដង\,]/g,'').trim(),
			'decrement'   :$('#decrement_txt').val().replace(/[​\u202f\៛\,]/g,'').trim(),
			'day'         :$('#day_db').val()
	};
	console.log(data);
	$.ajax({
		type:'GET',
		url :'/loanSaveUdateItem',
		data:data,
		success:function(json){
			console.log(json);
			if (json.code == 'undefined' || json.code ==  '9999'){
				alert(json.message);
				return;
			}
			 alert(json.message);
			// window.location.href = '/loan';
		},error:function(json){
			console.log(json)
		}
	});
	$('#loading').bPopup().close();
}
function errorCheck(json){
	if (json.code == 'undefined'){
		alert(json.message);
		return;
	}
}