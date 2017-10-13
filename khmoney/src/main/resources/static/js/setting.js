$(document).ready(function(){
	loadingData();
});
function loadingData() {
	 $.ajax({
         type: 'GET',
         url : '/loadingListSetting',
         success : function(json){
         	console.log(json) ;  
         	$('#wrap_content_setting').empty();
         	if (json.object == null || json.object == 'undefined'){
         		$('#wrap_content_setting').append("There are no Setting ")
         		return;
         	}
         	$.each(json.object , function( index, value ) {
         		console.log(" == "  + index + '  '+value);
         		var tbl = '';         		
         		tbl += '<!-- title_wrap -->'
		    		   +'<div class="tit_wrap cboth">'
					   +'<div class="t_left">'
					   +'<div class="tit_h4"><h4>កំណត់ការបង់រំលោះ'+index+'</h4></div>'
				       +'</div>'
					   +'</div>'
					   +'<!-- //title_wrap -->';
         		tbl += '<!-- point_text -->'
    				   +'<p class="point_text">ការកំណត់បន្ថែមចំនួនថ្ងៃត្រូវបង់ប្រាក និងអត្រាការប្រាក់ទុកមុន</p>'   
    				   +'<!-- //point_text -->';
         		tbl += '<!-- table input dot -->'
					   +'<div class="tbl_input_dot">'
					   +'<table summary="">'
					   +'<caption></caption>'
					   +'<colgroup>'
					   +'<col style="width:100px;">'
					   +'<col style="width:150px;">'
					   +'<col style="width:100px;">'
					   +'<col style="width:200px;">'
					   +'<col style="width:100px;">'
					   +'</colgroup>'
					   +'<tbody>';
		         	   for(var i= 0 ;i < value.length; i++){
		         				tbl +='<tr>'
									+'<th><div>ចំនួន</div></th>'
									+'<td><div class="value_show">'+value[i].value+' ដង</div> <div style="display:none;" class="div_value"><input type="text" value="'+value[i].value+'" class="value"></div> </td>'
									+'<th><div>អត្រាការប្រាក់</div></th>'
									+'<td><div class="rate_show">'+value[i].rate+' %</div><div style="display:none;"  class="div_rate"><input type="text" value="'+value[i].rate+'" class="rate"></div></td>'
									+'<td>'
									+'<div>'
									+'<input type="hidden" value="'+index+'"         class="columns">'
		         				    +'<input type="hidden" value="'+value[i].type+'" class="type">'
		         				    +'<input type="hidden" value="'+value[i].day+'"  class="day">'
		         				    +'<input type="hidden" value="'+value[i].id+'"   class="id">'
									+'<a href="javascript:" class="btn_edit1"  onClick="showTextBoxEditData(this);"><span class="blind">edit </span></a>'
									+'<a href="javascript:" class="btn_save"   onClick="saveEditCountAndRate(this);" style="display:none;"><span class="blind">save </span></a>'
									if (value[i].txt == '2'){
										tbl += '<a href="javascript:" class="btn_delete_1" onClick="deleteSettingById(this)"><span class="blind">delete</span></a>'
									}
		         				    if ((i+1) == value.length){
		         				    	   tbl += '<a href="javascript:" class="btn_add_1" onClick="addNewCountAndRate(this);"><span class="blind">add</span></a>'
		         				    }			         				    
								tbl	+='</div>'
									+'</td>'
									+'</tr>'
				         	
		         		}			
						+'</tbody>'
					    +'</table>'
				        +'</div>'
				        +'<!-- //table input dot -->';
						$('#wrap_content_setting').append(tbl)
						$('.value').autoNumeric('init',{ aSign: '​​ ដង',pSign: 's',aPad: false,vMax: '999',vMin: '0'});
						$('.rate').autoNumeric('init', { aSign: ' %',pSign: 's',aPad: false,vMax: '99',vMin: '00.00',mDec: 2,});
         	});
         	
         
         	
         },
         error : function(){
            console.log('error');
         }
     });
}

function showTextBoxEditData(obj){
	console.log(" hello "+this);
	$(obj).parents('tr').find('.rate_show').hide();
	$(obj).parents('tr').find('.value_show').hide();
	$(obj).parents('tr').find('.div_rate').show();
	$(obj).parents('tr').find('.div_value').show();
	$(obj).parents('tr').find('.btn_save').show();
	$(obj).hide();
}

function saveEditCountAndRate(obj){
	var data = {
			value : $(obj).parents('tr').find('.div_value').find('.value').val().replace(/\ដង/g, '').trim(),
			rate  : $(obj).parents('tr').find('.div_rate').find('.rate').val().replace(/\%/g, '').trim(),
			id    : $(obj).parents('tr').find('.id').val()
	}
	 $.ajax({
         type: 'GET',
         url : '/settingEditById',
         data: data,
         success : function(json){
        	 if (json.code == '9999'){
        		 alert(json.message);
        		 return;
        	 }
        	 alert(json.message);
        	 loadingData();
         },
         error:function(error){
        	 console.log(error);
         }
     });
}

function addNewCountAndRate(obj){
	$(obj).hide();
	var tbl ='<tr>'
		+'<th><div>ចំនួន</div></th>'
		+'<td><div class="div_value"><input type="text" value="" class="value"></div> </td>'
		+'<th><div>អត្រាការប្រាក់</div></th>'
		+'<td><div  class="div_rate"><input type="text" value="" class="rate"></div></td>'
		+'<td>'
		+'<div>'		
		+'<a href="javascript:" class="btn_save"   onClick="saveNewCountAndRate(this);"><span class="blind">save</span></a>'
		+'<a href="javascript:" class="btn_delete_1"><span class="blind">delete</span></a>'
		+'<a href="javascript:" class="btn_cancel"  onClick="showTextBoxEditData(this);"><span class="blind">cancel </span></a>'
		+'</div>'
		+'</td>'
		+'</tr>';
	$(obj).parents('tr').after(tbl);
	$('.value').autoNumeric('init',{ aSign: '​ ដង',pSign: 's',aPad: false,vMax: '999',vMin: '0'});
	$('.rate').autoNumeric('init', { aSign: ' %',pSign: 's',aPad: false,vMax: '99',vMin: '00.00',mDec: 2,});
}

function saveNewCountAndRate(obj){
	var data = {
			value   :  $(obj).parents('tr').find('.value').val().replace(/\ដង/g, '').trim(),
			rate    :  $(obj).parents('tr').find('.rate').val().replace(/\%/g, '').trim(),
			columns :  $(obj).parents('tbody').find('.columns').val(),
			type    :  $(obj).parents('tbody').find('.type').val(),
			day     :  $(obj).parents('tbody').find('.day').val()
	}
	$.ajax({
         type: 'GET',
         url : '/saveNewCountAndRate',
         data: data,
         success : function(json){
        	 if (json.code == '9999'){
        		 alert(json.message);
        		 return;
        	 }
        	 alert(json.message);
        	 loadingData();
         },
         error:function(error){
        	 console.log(error);
         }
     });
	
}

function deleteSettingById(obj){
	    var value = $(obj).parents('tr').find('.value').val();
	    var rate  = $(obj).parents('tr').find('.rate').val();
		if (confirm('Do you to delete value='+ value + 'and rate ='+rate+' this Item.' )== true){
			$.ajax({
		         type: 'GET',
		         url : '/deleteSettingById',
		         data: 'id='+$(obj).parents('tr').find('.id').val(),
		         success : function(json){
		        	 if (json.code == '9999'){
		        		 alert(json.message);
		        		 return;
		        	 }
		        	 alert(json.message);
		        	 loadingData();
		         },
		         error:function(error){
		        	 console.log(error);
		         }
		     });
		}
		
}