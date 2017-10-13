
$(document).ready(function(){
	init();		
	$('#type_payment').change(function(){
		loanShowBytime();
	});
	$(document).on('click','.btn_cancel,.btn_close',function(){
		$('.alert_wrap').bPopup().close();
	});
	
	$(window).scroll(function(e){
		var scrollTop = $(window).scrollTop();
		if (scrollTop > 401){
			$('#paymentBar').addClass('topfix');
		}else{
			$('#paymentBar').removeClass('topfix');
		}
	});
	$('#btn_payment').click(function(){
		checkForPayment();
	});
	$('#btn_update').click(function(){
		loanPaymentSaveUpdate();
	});
});

function init(){
	$('#loading').bPopup();
	if ($('#loan_id').val() == ''){
		$.ajax({
			type:'GET',
			url :'/loadingLoanview',
			data:'loaner_id='+$('#loaner_id').val()+'&id='+$('#id').val(),
			success:function(json){
				loanerGetMaxId(json);
			},error:function(json){
				console.log(json);
			}
		});
	}else{
		var data = {
				'loaner_id':$('#loaner_id').val(),
				'loan_id'  :$('#loan_id').val(),
				'id'       :$('#id').val()
		}
		$.ajax({
			type:'GET',
			url :'/loadingLoanEdit',
			data:data,
			success:function(json){
				loanerGetMaxId(json);
			},error:function(json){
				console.log(json);
			}
		});
	}
	$('#loading').bPopup().close();
	
}
function loanerGetMaxId(json){	
			console.log(json);
			if (json.code == '9999'){
				alert(json.message);
				return;
			}
			$('#loaner_code').val(Common.numberWithComma(Common.leftPage(json.object.loanObject.loaner_id,9),"-"));
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
		    
		    var opt = '';
		    if (typeof json.object.loanList != 'undefined'){
	    	   $.each(json.object.loanList,function(index,value){
			    	opt += '<option value="'+value.loan_id+'">លើទី'+(index+1)+'</option>';
			    });
	    	   $('#type_payment').append(opt);
	    	   $('#type_payment').show();
		    }else{
		    	$('#type_payment').hide();
		    }		 
		    
		    
		    $('#type_payment_txt').val(json.object.loanObject.type_payment);
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
			$('#tbl_payment tbody').empty();
			$('#tbl_payment tbody').show();
		   $.each(json.object.loanPaymentList,function(index,value){
			   var a = 0 ;
				if (parseInt(tt) - parseInt(d) > 0 ){
					a  = (parseInt(tt) - parseInt(d)) + parseInt(value.prak_derm) ;
				}
				var b  = Common.ConvertZeroTwoDigit(a+'');
				var c  = Common.numberWithComma(b) + ' ៛'
				var bg = '',readOnly='',dt='',user='',payment_id='',txt='',note='';
				if (value.txt == '9'){
					bg      = 'payment_already';
					readOnly='disabled="disabled" checked="checked"';
					dt      = moment(value.modify_date).format('DD/MM/YYYY');
					user    = value.pay_date;
					note      = value.note;
				}else{
					payment_id=value.payment_id;
					txt       ='txt';
					note      = '<input type="text" style="width:100%;" class="note" maxlength="100">';
				}
			   tbl += '<tr class="'+txt+'">'
				    +'<td><div class="t_center '+bg+'"><input type="checkbox" name="payment_id" onChange="change(this)" '+readOnly+'></div></td>'
					+'<td class="'+bg+'"><div class="t_center index">'+(index+1)+'</div><input type="hidden" value="'+payment_id+'" class="payment_id"/></td>'
					+'<td class="'+bg+'"><div class="t_center">'+Common.ConvertDayToKhmer(start_date)   +'</div></td>'
					+'<td class="'+bg+'"><div class="t_center tbl_start_date">' +moment(start_date).format('DD/MM/YYYY')+'</div></td>'
					+'<td style="display:none;"><div class="t_right tbl_prak_derm"></div></td>'
					+'<td style="display:none;"><div class="tbl_total_rate"></div></td>'
					+'<td class="'+bg+'"><div class="t_right tbl_payment">'+c+'</div></td>'
					+'<td style="display:;" class="'+bg+'"><div class="t_center">'+dt+'</div></td>'
					+'<td style="display:;" class="'+bg+'"><div>'+user+'</div></td>'
					+'<td class="'+bg+'"><div>'+note+'</div></td>'
					+'<td style="display:none;"><div class="t_right prak_derm">'+value.prak_derm+'</div></td>'
					+'<td style="display:none;"><div class="rate">'+(parseInt(tt) - parseInt(d))+'</div></td>'
					+'<td style="display:none;"><div class="total_left">'+value.total_left+'</div></td>'
				+'</tr>';
				if (index==1){
			    	$('#end_date_db').val(moment(start_date).format('YYYYMMDD'));
			    }
			    start_date.setDate(start_date.getDate() + parseInt(day));	 
			    d = parseInt(d) + parseInt(json.object.loanObject.decrement);	
		   });
			$('#tbl_payment tfoot').hide();
			$('#tbl_payment tbody').append(tbl);
		
}
function checkForPayment(){
	var i = 0,prak_derm = 0,rate = 0, total = 0,ok=false;
	$('#tbl_payment tr.txt').each(function(index,value){
		if ($('#tbl_payment tr.txt').find('input[type=checkbox]:checked').length > 0){
			if ($('#payment_all').prop('checked') == true){
				if ($(this).find('input[type=checkbox]').prop('checked') == true){
					if (i==0){
						prak_derm += parseInt($(this).find('.prak_derm').text()) + parseInt($(this).find('.total_left').text())
						i++;
					}					
					rate      += parseInt($(this).find('.rate').text());
					total  = parseInt(rate) + parseInt(prak_derm);
/*					payment_id+=$(this).find('.payment_id').val()+'|';
					note      +=$(this).find('.note').val()+'|';*/
				}
				
			}else{
				if ($(this).find('input[type=checkbox]').prop('checked') == true){
					/*prak_derm += parseInt($(this).find('.prak_derm').text());
					rate      += parseInt($(this).find('.rate').text());*/	
					total += parseInt($(this).find('.tbl_payment').text().replace(/[​\u202f\៛\,]/g,'').trim());
/*					payment_id+=$(this).find('.payment_id').val()+'|';
					note      +=$(this).find('.note').val()+'|';*/
				}
			}
			
			ok = true;
		}else{
			alert('choose');
			ok = false;
			return false;
		}
	})
	if (ok == false){
		return;
	}
	
	/*$('#popup_prak_derm').val(Common.numberWithComma(prak_derm+'')+' ៛');
	$('#popup_rate').val(Common.numberWithComma(rate)+' ៛');*/
	$('#popup_total').val(Common.numberWithComma(Common.ConvertZeroTwoDigit(total+''))+' ៛');
	$('#popu_alert_wrap').bPopup();
}
function change(obj){
	var tr = $(obj).parents('tbody');
	var i = $(obj).parents('tr').find('.index').text();
	$(tr).find('tr.txt').each(function(index,value){
		if ($(value).find('input[type=checkbox]').prop('checked') == false){
			if(parseInt(i) > parseInt($(value).find('.index').text())){
				alert('can not')
				$('#tbl_payment tbody tr.txt').find('input[type=checkbox]').prop('checked',false);
				return false;
			}
		}
	});
}

function loanPaymentSaveUpdate(){
	$('#loading').bPopup();
	var data = {},obj = [];
	$('#tbl_payment tr.txt').each(function(index,value){
		if ($(this).find('input[type=checkbox]').prop('checked') == true){
			data ={
					'payment_id':$(this).find('.payment_id').val(),
					'note'      :$(this).find('.note').val()
			}
			obj.push(data);
		}
	});
	var dt = {
			'list':obj
	}
	console.log(dt);
	$.ajax({
		type:'GET',
		url :'/loanPaymentSaveUpdate',
		data:dt,
		success:function(json){
			if (json.code == 'undefined'){
				alert(json.message);
				return;
			}
			 alert(json.message);
			 window.location.href = '/missing-payment/payment?loaner_id='+$('#loaner_id').val()+'&loan_id='+$('#loan_id').val();
		},error:function(json){
			console.log(json);
		}
	});
	$('#loading').bPopup().close();
}

