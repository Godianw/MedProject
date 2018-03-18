/* 验证文本框是否为空 */
$.fn.validate = function(tips){
	if($(this).val() == "" || $.trim($(this).val()).length == 0){
		alert(tips + "不能为空！");
		throw SyntaxError(); //如果验证不通过，则不执行后面
	}
}

$(function(){
	/* 限制文本框只能输入数字 */  
	$(".NumText").keyup(function(){    
		$(this).val($(this).val().replace(/D|^0/g,''));  
	}).bind("paste",function(){  //CTR+V事件处理    
		$(this).val($(this).val().replace(/D|^0/g,''));     
	}).css("ime-mode", "disabled"); //CSS设置输入法不可用
	
	/* 限制文本框只能输入数字和小数点 */  
	$(".NumDecText").keyup(function(){    
	$(this).val($(this).val().replace(/[^0-9.]/g, ''));    
	})
	.bind("paste",function(){  //CTR+V事件处理    
	$(this).val($(this).val().replace(/[^0-9.]/g, ''));     
	})
	.css("ime-mode", "disabled"); //CSS设置输入法不可用    
	
	/* 限制文本框只能输入数字和横杠 */  
	$(".NumDashText").keyup(function(){    
		$(this).val($(this).val().replace(/[^0-9-]/g, ''));    
	}).bind("paste",function(){  //CTR+V事件处理    
		$(this).val($(this).val().replace(/[^0-9-]/g, ''));     
	}).css("ime-mode", "disabled"); //CSS设置输入法不可用    
});
