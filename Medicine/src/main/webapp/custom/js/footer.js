/**
 * @action: set footer's postion
 **/
var footerHeight = 0, footerTop = 0, $footer = $(".site-footer");
$(window).bind("load", function() {
//	positionFooter();
	//	$(window).scroll(positionFooter).resize(positionFooter);
});

//定义positionFooter function
function positionFooter() {
	//取到div#footer高度
	footerHeight = $footer.height();

	//如果页面内容高度小于屏幕高度，div#footer将绝对定位到屏幕底部，否则div#footer保留它的正常静态定位
	var windowHeight = $(window).height();
	var footerMargin = $('.site-footer').css("margin-top");
	var height = windowHeight - $(document.body).height() + footerHeight;
	console.log("================================================");
	console.log("berfore setting : footerMargin = " + footerMargin);
//	alert("footerHeight = " + footerMargin);
	console.log("documentHeight = " + $(document.body).height());
	if ($(document.body).height() - footerHeight < windowHeight) {
		$('.site-footer').css("margin-top", height)
	} else {
		$('.site-footer').css("margin-top", "30px")
	}
	console.log("after setting : footerMargin = " + $('.site-footer').css("margin-top"));
	console.log("documentHeight = " + $(document.body).height());
	console.log("================================================");
}
