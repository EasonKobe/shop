var table=function(){
	var mmg=null;
	var storage=JSON.parse(localStorage.getItem('templateBuilder'));
	var init=function(){
		//步骤提示
		$('#steps').find('.pagination').find('li:eq(2)').find('a').addClass('selected');
		//将前一个页面传过来的origin存储在本地存储中
		storage.origin=getUrlParam('origin');
		$('#prev').attr('href',$('#prev').attr('href')+'?mode='+storage.mode+'&isLayout=' + storage.isLayout);
		//刚进页面的时候，storage.selectedTable没有值
		storage.selectedTable='';
		_initGrid();
		_eventRegister();
	};
	var _eventRegister=function(){
		//下一步按钮的监听事件，如果选择了表，可以跳到下一步，如果没有，不能跳到下一步
		$('#next').on('mousedown',function(e){
			var selectedRows=mmg.selectedRows();
			if(selectedRows.length==0){
				alert('请选择表！');
			}else{
				var selectedRow=selectedRows[0];
				storage.selectedTable=selectedRow.name;
				localStorage.setItem('templateBuilder',JSON.stringify(storage));
			}
		});

		//搜索框事件
		$('#keyWord').keyup(function(){
			// 重写contains方法，不区分大小写
			jQuery.expr[':'].contains = function(a, i, m) {    
			  return jQuery(a).text().toUpperCase()    
			      .indexOf(m[3].toUpperCase()) >= 0;    
			};    
			
			var value=$.trim(this.value);
			if(value == ''){
				$('#grid').find('tr').removeClass('hide');
				return;
			}
			$('#grid').find('tr').removeClass('hide');
			$('#grid').find('tr:not(:contains('+value+'))').addClass('hide');
		});
		
	};
	//mmGrid生成代码
	var _initGrid=function(){
		//表头
		var cols=[
			// {title:'NAME', name:'name' ,width:100, sortable: true, type:'string', align:'left'},
			// {title:'COMMENT', name:'comment' ,width:130, sortable: true, type:'string', align:'left'},
			// {title:'REMARK', name:'remark' ,width:100, sortable: true, type:'string', align:'left'},
			
			{title:'表名', name:'name' ,width:120, sortable: true, type:'string', align:'left'},
			{title:'表注释', name:'comment' ,width:150, sortable: true, type:'string', align:'left'},
			{title:'类型', name:'type' ,width:80, sortable: true, type:'string', align:'left'}
		];
		mmg=$('#grid').mmGrid({
			width:'auto',
			height: 450,
			cols: cols, 
            checkCol:true,
            fullWidthRows:true,
            nowrap : true,
            autoLoad : false,
            url : 'action/tableQueryAction?type=json'
		});
		
		$.post('action/tableQueryAction?type=json',{},function(data) {
			mmg.load(data.data);
		},"json");
		
//		$.ajax({
//			url:'action/tableQueryAction?type=json',
//			dataType:'json',
//			method:'POST',
//			async:true,
//			success:function(data){
//				mmg.load(data.data);
//			}
//		});
		
		//绑定表格的双击事件
		mmg.on('itemdblclick', function(event, item, rowIndex) {
			storage.selectedTable=item.name;
			localStorage.setItem('templateBuilder',JSON.stringify(storage));
			window.location.href = 'codehelper/jsp/column.jsp';
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
	table.init();
});