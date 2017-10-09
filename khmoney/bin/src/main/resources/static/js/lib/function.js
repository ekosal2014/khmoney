// JavaScript Document

$(document).ready(function(){
	
	function staff(fullName,position){
		this.fullName	=	fullName;
		this.position	=	position;	
	}

	

	var staffs	=	[];
	$('.btn_insertStaffs').click(function(){
		var fullName	=	$('.txt_fullName').val();
		var position	=	$('.txt_position').val();
		staffs.push(new staff(fullName,position));
		viewStaff();
		$('.txt_fullName, .txt_position').val('');	
	});
	
	var lstStaffs	=	'';
	function viewStaff(){
		lstStaffs	=	'';

		$.each(staffs,function(i,v){
			lstStaffs+= '<tr>'+
							'<td><div>'+(i+1)+'</div></td>'+
							'<td><div class="txt_tblFullName">'+v.fullName+'</div></td>'+
							'<td><div class="txt_tblPosition">'+v.position+'</div></td>'+
							'<td>'+
								'<div class="t_center">'+
									'<a href="#none" staffid="'+i+'" class="btn_edit btn_tblUpdateStaff"><span class="blind">Edit</span></a>'+
									'<a href="#none" staffid="'+i+'" class="btn_delete btn_tblDeleteStaffs"><span class="blind">Delete</span></a>'+
								'</div>'+
							'</td>'+
						'</tr>';
		});

		$('.txt_getStaffs').html(lstStaffs);
	}

/*	$('.btn_deleteStaffs').click(function(){
		$('.txt_getStaffs').children('tr').each(function(){
			var $itemchecked=	$(this).find('input[type=checkbox]:checked').length;

			if($itemchecked===1){
				var itemId		=	parseInt($(this).find('input[type=checkbox]').attr('orderid'));
				$.each(staffs,function(i){
					if(i===itemId){ // 1 = number 1 json id
						console.log('Deleted Item : '+1);
						staffs.splice(i,1);
					}
				});
			}
		});
		viewStaff();
	});*/
	
	$('body').delegate('.btn_tblDeleteStaffs','click',function(){
		var staffid	=	parseInt($(this).attr('staffid'));

		$.each(staffs,function(i){
			if(i===staffid){ // 1 = number 1 json id
				staffs.splice(i,1);
			}
		});
		viewStaff();
	});
	
	$('body').delegate('.btn_tblUpdateStaff','click',function(){
		var staffid		=	$(this).attr('staffid');
		var fullName	=	$(this).parents('tr').find('.txt_tblFullName').text();
		var position	=	$(this).parents('tr').find('.txt_tblPosition').text();

		$('.txt_fullName').val(fullName);
		$('.txt_position').val(position);
		$('.btn_editStaffs').attr('staffupdateid',staffid);
	});
	
	$('.btn_editStaffs').click(function(){
		var staffupdateid=	$(this).attr('staffupdateid');
		var fullName	=	$('.txt_fullName').val();
		var position	=	$('.txt_position').val();
		updateStaffs(staffupdateid,fullName,position);
		
		$('.txt_fullName, .txt_position').val('');		
	});
	
	function updateStaffs(staffupdateid,fullName,position){
		
		staffs[staffupdateid].fullName=	fullName;
		staffs[staffupdateid].position=	position;
	
		viewStaff();
	}
});