var Origin=function(){
	var storage=JSON.parse(localStorage.getItem('templateBuilder'));
	var init=function(){
		//步骤提示
		$('#steps').find('.pagination').find('li:eq(1)').find('a').addClass('selected');
		//将模式置为空
		storage.origin='';
		_eventRegister();
		_getData();
		//只有模式是单表查询，多表关联查询的时候才能过从sql语句创建
		var mode=storage.mode;
		if(mode == 'oneQuery' || mode == 'multiQuery'){
		}else{
			$('#sqlOrigin').css('display','none');			
		}
	};
	//获取上一个页面传过来的值（mode），并存储在本地存储中
	var _getData=function(){
		storage.mode=getUrlParam('mode');
		storage.isLayout=getUrlParam('isLayout');
		localStorage.setItem('templateBuilder',JSON.stringify(storage));
	};
	//注册事件
	var _eventRegister=function(){
		//填写sql语句对话框的确定按钮
		$('#sqlOK').click(function(){
			storage.origin="SQL";
			var sql=$.trim($('#sqlEditor').val());
			if(sql!=''){
				storage.sql=sql;
			}else{
				alert('请填写sql语句啊！');
			}
			
		});
	};
	return{
		init:init
	};
}();
//获取地址栏中传过来的参数
function getUrlParam(key) {
	var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) return unescape(r[2]); return null;
};
$(function(){
	Origin.init();
});