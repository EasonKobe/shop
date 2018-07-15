var sql=function(){
	var storage=JSON.parse(localStorage.getItem('templateBuilder'));
	var init =function(){
		//步骤提示
		$('#steps').find('.pagination').find('li:eq(2)').find('a').addClass('selected');
		//设置上一步路径
		$('#prev').attr('href','codehelper/jsp/origin.jsp?mode=' +storage.mode+ '&isLayout=' +storage.isLayout);
		//设置源
		storage.origin=getUrlParam('origin');
		if(storage.sql != ''){
			$('#sqlEditor').val(storage.sql);
//			storage.sql='';
		}
		//事件注册
		_eventRegister();
	};
	var _eventRegister=function(){
		//下一步按钮的监听事件，如果选择了表，可以跳到下一步，如果没有，不能跳到下一步
		$('#next').on('mousedown',function(e){
			var sql_value=$('#sqlEditor').val();
			if($.trim(sql_value) == ''){
				alert('请输入SQL语句！');
			}else{
				storage.sql=sql_value;
				localStorage.setItem('templateBuilder',JSON.stringify(storage));
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
	sql.init();
});