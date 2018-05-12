//全局的ajax访问，处理ajax清求时sesion超时
$.ajaxSetup({
    complete : function(XMLHttpRequest, textStatus) {
    	// 通过XMLHttpRequest取得响应头，REDIRECT    
        var redirect = XMLHttpRequest.getResponseHeader("REDIRECT");//若HEADER中含有REDIRECT说明后端想重定向  
        if (redirect == "REDIRECT") {  
            var win = window;    
            while (win != win.top){  
                win = win.top;  
            }  
            var redirectUrl = XMLHttpRequest.getResponseHeader("CONTEXTPATH");
            if ("/med/index.do" == redirectUrl)
            	layer.msg('登录超时，请重新登录');
            //将后端重定向的地址取出来,使用win.location.href去实现重定向的要求  
            win.location.href= redirectUrl;    
        }  
    }
}); 
