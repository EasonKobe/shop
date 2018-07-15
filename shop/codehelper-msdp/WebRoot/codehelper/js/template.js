var Template=function(){
	var mmg=null;
	var heo=JSON.parse(localStorage.getItem('heo'));
	var storage=JSON.parse(localStorage.getItem('templateBuilder'));
	var templateGroup=null;
	var init=function(){
		//步骤提示
		$('#steps').find('.pagination').find('li:eq(5)').find('a').addClass('selected');
		_eventRegister();
		_loadData();
		_generateTable();
		
		//若isLayout是false
		if (storage.isLayout == "false"){
			$("#prev").attr("href","codehelper/jsp/column.jsp");
		} else {
			$("#prev").attr("href","codehelper/jsp/layout.jsp");
		}
	};
	//注册事件
	var _eventRegister=function(){
		/*
		 * 生成按钮事件
		 * 将前台配置好的HEO对象传到后台去
		 * HEO对象里面的templateList存放选择的模板信息
		 */
		$('#generate').on('click',function(e){
			var rows=mmg.selectedRows();
			if(rows.length==0){
				alert('请至少选择一个模板！');
			}else{
				heo.templateList=rows;
				localStorage.setItem('heo',JSON.stringify(heo));
			}
		});
		
		/**
		 * 选择模板组的下拉框事件
		 * */
		$('#temptlate-group-slt').on('change',function(){
			var index=this.selectedIndex;
			mmg.load(templateGroup[index].templateList);
		});
	};
	//载入模板列表
	var _loadData=function(){
		$.ajax({
			url:'action/templateQueryAction?type=json',
			type:'POST',
			dataType:'json',
			data:'json='+localStorage.getItem('templateBuilder'),
			async:false,
			success:function(data){
				templateGroup=data.data;
				_generateTable(templateGroup[0].templateList);
				//生成下拉框信息
				var opts=[];
				for(var i in templateGroup){
					opts.push('<option name="'+i+'">'+templateGroup[i].name+'</option>');
				}
				$('#temptlate-group-slt').html(opts.join(''));
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert.log(XMLHttpRequest);
				alert.log(textStatus);
				alert.log(errorThrown);
			}
		});
	};
	var _generateTable=function(data){
		items = typeof data == 'undefined' ? [] : data;
		
		//表头
		var cols=[
			{title:'模板名', name:'name' ,width:60, sortable: true, type:'string', align:'left'},
			{title:'描述', name:'desc' ,width:100, sortable: true, type:'string', align:'left'},	
			{title:'保存路径', name:'target' ,width:260, sortable: true, type:'string', align:'left',renderer:
				function(val,item,rowIndex){
					return '<input value="'+val+'" name="saveDir" class="save-dir-input" placeholder="save/to/dir" style="width:90%;margin-bottom:0;" type="text" title="'+val+'">';
				}
			},	
		];
		mmg=$('#grid').mmGrid({
			width:'auto',
			height:450,
			cols:cols,
			items:items,
			checkCol:true,
			fullWidthRows:true,
            nowrap : true,
            multiSelect:true
		});

		mmg.on('loadSuccess',function(e,data){
			$('#grid').on('blur','.save-dir-input',function(){
				var value=$.trim(this.value);
				if(value!=''){
					var index=$(this).parents('tr:first').index();
					row=mmg.row(index);
					row.saveDir=value;
				}
			});
			mmg.select("all");
		});

	};
	return{
		init:init
	};
}();
$(function(){
	Template.init();
});
