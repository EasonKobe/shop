$(function(){
	//步骤提示
	$('#steps').find('.pagination').find('li:eq(0)').find('a').addClass('selected');
	//判断浏览器是否支持HTML5（的本地存储和CSS动画）
	//Modernizr.localstorage&&Modernizr.cssanimations
	if(localStorage.getItem('templateBuilder')){
		localStorage.removeItem('templateBuilder');
	}
	var storage={
		mode:'',//oneQuery（单表查询）、oneEdit（单表编辑）、manyReference（多表关联）、oneToManyEdit（一对多编辑）
		origin:'',//DB or SQL
		sql:'',
		selectedTable:'',
		queryCols:[],
		showCols:[],
		editCols:[],
		isLayout:''
	};
	localStorage.setItem('templateBuilder',JSON.stringify(storage));
	
	$.post("action/modeQueryAction?type=json",function(data){
		var mode = $.parseJSON(data).data;
		var html ="";
		var row = 2;
		$.each(mode,function(i,n){
			if (i!=0 && i%row == 0){
				html = html + "</div>";
			}
			if (i%row == 0){
				html = html + "<div class='row-fluid'>";
			}
			html = html + "<a href='codehelper/jsp/origin.jsp?mode=" + n.id + "&isLayout="+ n.isLayout +"' class='box shadow lazy'> "+ n.name+ "</a>";
		});
		$(".panel-body").html(html);
	});
	
});