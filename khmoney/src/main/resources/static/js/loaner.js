$(document).ready(function(){
	goPageList(1);
	$('#btn_combo_down').click(function(){
		 $(this).parent('.combo_style').find('ul').toggle();
	});
	/*$('#btn_ul_down li').click(function(){		
		$(this).parents('.combo_style').find('#btn_combo_down span').text($(this).find('a').text());
		$(this).parent('ul').hide();
		goPageList(1);
	});*/
	$('#tbl_search').click(function(){
		goPageList(1);
	});
});

function goPageList(page){
	$('#loading').bPopup();
	var data = {
			'currentPage' : page,
			'perPage'     : $('#num_rows').val(),
			'search'      : $('#search').val()
	}
	console.log(data);
	$.ajax({
		type:'GET',
		url :'/loadingLoanerListInformation',
		data: data,
		success:function(json){
			if (json.code == '9999'){
				alert(json.message);
				return;
			}
			var loanerList = json.object.loanerList;
			var tbl = '';
			$('#tbl_loan tfoot').hide();
			$('#tbl_loan tbody').show();
			$('#tbl_loan tbody').empty();
			if (loanerList.length <= 0 || loanerList == null){
				$('#tbl_loan tfoot').show();
				$('#tbl_loan tbody').hide();
			}else{
				var i = (parseInt(page)-1) * parseInt($('#num_rows').val().replace(/[​\-]/g,''));
				$.each(loanerList,function(index,value){				
					tbl += '<tr>'
								+'<td><div><input type="checkbox"><input type="hidden" value="'+value.loaner_id+'"</div></td>'
								+'<td><div>'+(i+1)+'</div></td>'
								+'<td><div>'+Common.numberWithComma(Common.leftPage(value.loaner_id,9))+'</div></td>'
								+'<td><div>'+value.loaner_name+'</div></td>'
								+'<td><div>'+(value.gender == '1' ? 'ប្រុស' : 'ស្រី')+'</div></td>'
								+'<td><div>'+Common.phoneWithComma(value.phone)+'</div></td>'
								+'<td><div>'+Common.phoneWithComma(value.id_card)+'</div></td>'
								+'<td><div style="cursor:pointer;" onClick="loanViewDetail('+value.loaner_id+')">'+(value.txt == '9' ? 'បានបញ្ចប់' : 'រងចាំ')+'</div></td>'
								+'<td>'+ (value.txt == '1' ? '<div class="dis">ខ្ចីម្ដងទៀត</div>' : '<div style="cursor:pointer;" onclick="loanAgain('+value.loaner_id+')">ខ្ចីម្ដងទៀត</div>')+'</td>'
							+'</tr>';
					  i++;
				});
			}
			
			$('#tbl_loan tbody').append(tbl);
		    var option = {
        			total       : json.object.loadingTotalCountRows,
    				maxVisible  : 10,
    				perPage     : $('#num_rows').val(),
    				currentPage : page,
    				numPage     : 1,
    				wrapClass   :'paging',
    				eventLink   :'goPageList'
        	 }
		     pagination.showPage(option);
		     $('#loading').bPopup().close();

		},error:function(json){
			
		}
	});
	
}

function loanAgain(loaner_id){
	window.location.href = '/loan/loan-again?loaner_id='+loaner_id;
}
function loanViewDetail(loaner_id,txt){
	window.location.href = '/loan/loaner-view-detail?loaner_id='+loaner_id+'&id=loaner';
}