$(document).ready(function(){
	Common.datePickerRang('start_date','end_date');
	init(1);
	$('#status').change(function(){
		if ($('#status').is(':checked')){
			$('#status_date').show();
		}else{
			$('#status_date').hide();
		}
	});
	$('#tbl_search').click(function(){			
		init(1);
	});
	$('#num_rows').change(function(){
		init(1);
	});
});
function init(page){
	var object = {
			'perPage'    : $('#num_rows').val(),
			'currentPage': page,
			'search'     : $('#search').val(),
			'typePayment': '',
			'startDate'  : '',
			'endDate'    : ''
	}
	if ($('#status').is(':checked')){
		//alert($('#start_date').val());
		object['startDate'] = Common.formatDateToString($('#start_date').val().trim());
		object['endDate']   = Common.formatDateToString($('#end_date').val().trim());
	}
	if (typeof $('#type_payment').val() != null){
		object['typePayment']   = $('#type_payment').val();
	}
	console.log(object);
	$.ajax({
		type:'GET',
		url :'/loadingLoanListView',
		data:object,
		success:function(json){
			console.log(json);
			if (json.code == 'undefined' || json.code == '9999'){
				alert(json.message);
				return;
			}
			var loanList = json.object.loanList;
			var typePayment = json.object.typePaymentList;
			var tbl = '',opt='<option value="">ទាំងអស់</option>';
			$('#tbl_loan tbody').empty();
			$('#type_payment').empty();
			$('#tbl_loan tfoot').hide();
			$('#tbl_loan tbody').show();
			$.each(typePayment,function(index,value){
				opt += '<option value="'+value.type+'" '+(json.object.typePayment==value.type?'selected="selected"':'')+'>'+value.columns+'</option>'
			});
			$('#type_payment').append(opt);
			
			if (loanList.length > 0 ){
				var i = (parseInt(page)-1) * parseInt($('#num_rows').text().replace(/[​\-]/g,''));
				$.each(loanList,function(index,value){
					var rate = ((parseInt(value.total_money) / parseInt(value.time)) * parseFloat(value.rate)) / 100;
					var total   = parseFloat(rate) + (parseInt(value.total_money) / parseInt(value.time));
					var  tt   = Math.round(rate);
					    total = Math.round(total);
				    var end_date = '';
				    if (value.txt = '1'){
				    	end_date = '';
				    }else{
				    	end_date = moment(value.end_date).format('DD/MM/YYYY');
				    }
				    var amount = '0';
				    //console.log(value.amount);
				    if (typeof value.amount != 'undefined' && typeof value.amount != ''){
				    	amount = Common.numberWithComma(Common.ConvertZeroTwoDigit(Math.round(value.amount)+''));
				    }
					tbl += '<tr>'
								+'<td><div class="t_center"><input type="checkbox"><input type="hidden" class="loaner_id" value="'+value.loaner_id+'"/></div></td>'
								+'<td><div>'+(i+1)+'</div></td>'
								+'<td><div>'+Common.numberWithComma(Common.leftPage(value.loan_id,9))+'</div></td>'
								+'<td><div>'+moment(value.start_date).format('DD/MM/YYYY')+'</div></td>'
								+'<td><div class="t_right">'+Common.numberWithComma(value.total_money)+' ៛</div></td>'
								+'<td><div class="t_right">'+Common.numberWithComma(Common.ConvertZeroTwoDigit(tt+''))+' ៛</div></td>'
								+'<td><div class="t_center">'+Common.numberWithComma(value.time)+' ដង</div></td>'
								+'<td><div class="t_right">'+Common.numberWithComma(Common.ConvertZeroTwoDigit(total+''))+' ៛</div></td>'
								+'<td><div class="t_right">'+amount+' ៛</div></td>'
								+'<td><div>'+end_date+'</div></td>'
								+'<td><div class="t_center">'+(value.txt=='9'?'បានបញ្ចប់':'រង់ចាំ')+'</div></td>'
								+'<td><div><a href="/loan/loan-view-detail?loaner_id='+value.loaner_id+'&loan_id='+value.loan_id+'&id=loan" style="width:50px;">លំអិត</a><a href="/loan/loan-edit?loaner_id='+value.loaner_id+'&loan_id='+value.loan_id+'" style="width:50px;">កែប្រែ</a></div></td>'
							+'</tr>';
					i++;
				});
			}else{
				$('#tbl_loan tfoot').show();
				$('#tbl_loan tbody').hide();
			}
			$('#tbl_loan tbody').append(tbl);
			 var option = {
        			total       : json.object.total_row,
    				maxVisible  : 10,
    				perPage     : $('#num_rows').val(),
    				currentPage : page,
    				numPage     : 1,
    				wrapClass   :'paging',
    				eventLink   :'init'
        	 }
		     pagination.showPage(option);
		     $('#loading').bPopup().close();
		},error:function(){
			
		}
	});
}