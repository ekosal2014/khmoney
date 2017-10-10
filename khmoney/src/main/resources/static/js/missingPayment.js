/**
 * 
 */
$(document).ready(function(){
	init();
	$('#btn_search').click(function(){
		init();
	});

});

function init (){
	$('#loading').bPopup();
	var data = {
			'search':$('#txt_search').val()
	}
	$.ajax({
		type:'GET',
		url :'/khmoney/missingPaymentList',
		data: data,
		success:function(json){
			console.log(json);
			if (json.code == 'undefined' || json.code == '9999'){
				alert(json.message);
				return;
			}
			$('#missingPayment tfoot').hide();
			$('#missingPayment tbody').show();
			$('#missingPayment tbody').empty();
			var tbl = '';
			if (json.object.length > 0){
				$.each(json.object,function(index,value){
					tbl += '<tr>'
								+'<td><div class="t_center"><input type="checkbox"></div></td>'
								+'<td><div class="t_center">'+(index+1)+'</div></td>'
								+'<td><div class="t_center">'+Common.numberWithComma(Common.leftPage(value.loaner_id,9),"-")+'</div></td>'
								+'<td><div>'+value.loaner_name+'</div></td>'
								+'<td><div class="t_right">'+Common.numberWithComma(Common.ConvertZeroTwoDigit(Math.round(value.total_money)+''))+' ៛</div></td>'
								+'<td><div class="t_center">'+moment(value.payment_date).format('DD/MM/YYYY')+'</div></td>'
								+'<td><div class="t_center">'+value.count_day+' ថ្ងៃ</div></td>'
								+'<td><div class="t_center payment" style="cursor:pointer;" onClick="goToPayemnt(this)" data_loan_id="'+value.loan_id+'" data_loaner_id="'+value.loaner_id+'">សង់ប្រាក់</div></td>'
							+'</tr>';
				});
				$('#missingPayment tbody').append(tbl);
			}else{
				$('#missingPayment tfoot').show();
				$('#missingPayment tbody').hide();
			}
		},error:function(json){
			
		}
		
	});
	$('#loading').bPopup().close();
}

function goToPayemnt(obj){
	var loaner_id = $(obj).attr('data_loaner_id');
	var loan_id   = $(obj).attr('data_loan_id');
	window.location.href = '/khmoney/missing-payment/payment?loaner_id='+loaner_id+'&loan_id='+loan_id;
	
}