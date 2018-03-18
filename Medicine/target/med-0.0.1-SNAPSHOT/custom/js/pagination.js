
// 更新页码信息
function updatePage(pageNum, pageTotal) {

	$('.M-box').pagination({
		pageCount: pageTotal,	// 总页数
		current: pageNum,		// 当前页数
  	    coping: false,			// 不开启首页和末页
  	    keepShowPN : false,		// 一直显示上一页和下一页 
  	    mode: 'fixed',			// 页码数量固定
  	    callback: function (api) {
  	        var curPage = api.getCurrent();
  	        pageChange(curPage); // 更新表格数据
  	    }
    }); 
}

