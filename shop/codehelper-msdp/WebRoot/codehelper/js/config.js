var Config=function(){
	var init=function(){
		$("#steps").hide();
		_eventRegister();
		_loadData();
		
		//给表单添加验证事件
		sns.valid.init($("#userForm"));
		sns.valid.init($("#DBForm"));
		sns.valid.init($("#outForm"));
	};
	
	//注册事件
	var _eventRegister=function(){
		$('#save').on('click',function(e){
			//非法判断
			if (!$(".panel-body form").isValid()) {
				return;
			}
			//收集参数
//			var environment = new Array();
//			$("input").each(function(i, n){
//				environment.push(
//						{
//							name: this.name,
//							value: n.value
//						});
//			});
			var json={
					"author": $("input[name='author']").val(),
					"email": $("input[name='email']").val(),
					"jdbc.driverClassName": $("input[name='jdbc.driverClassName']").val(),
					"jdbc.url": $("input[name='jdbc.url']").val(),
					"jdbc.username": $("input[name='jdbc.username']").val(),
					"jdbc.password": $("input[name='jdbc.password']").val(),
					"project.path": $("input[name='project.path']").val(),
					"package.name": $("input[name='package.name']").val(),
					"module.name": $("input[name='module.name']").val(),
					"colSize": $("input[name='colSize']").val()
				};
			$.post('action/configSaveAction?type=json',{json:JSON.stringify(json)},function(data) {
				if (data.result == true){
					alert("保存成功");
				} else {
					alert("保存失败");
				}
				
			},"json");
		});
	};
	
	var _loadData=function(){
		$.ajax({
			url:'action/configQueryAction?type=json',
			type:'POST',
			dataType:'json',
			async:false,
			success:function(data){
				var environment = data.data;
				$("input[name='author']").val(environment.author);
				$("input[name='email']").val(environment.email);
				
				$("input[name='jdbc.driverClassName']").val(environment['jdbc.driverClassName']);
				$("input[name='jdbc.url']").val(environment['jdbc.url']);
				$("input[name='jdbc.username']").val(environment['jdbc.username']);
				$("input[name='jdbc.password']").val(environment['jdbc.password']);
				
				$("input[name='project.path']").val(environment['project.path']);
				$("input[name='package.name']").val(environment['package.name']);
				$("input[name='module.name']").val(environment['module.name']);
				$("input[name='colSize']").val(environment['colSize']);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				
			}
		});
	};
	return{
		init:init
	};
}();
$(function(){
	Config.init();
});
