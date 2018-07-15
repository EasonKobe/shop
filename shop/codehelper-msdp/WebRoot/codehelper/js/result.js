var Result=function(){
	var mmg=null;
	var heo=JSON.parse(localStorage.getItem('heo'));
	var storage=JSON.parse(localStorage.getItem('templateBuilder'));
	var templateGroup=null;
	var init=function(){
		//步骤提示
		$('#steps').find('.pagination').find('li:eq(6)').find('a').addClass('selected');
		_initGrid();;
	};
	
	var _initGrid=function(data){
		//表头f
		var cols=[
			{title:'文件名', name:'fileName' ,width:80, sortable: true, type:'string', align:'center'},
			{title:'保存路径', name:'filePath' ,width:200, sortable: true, type:'string', align:'center',renderer:
				function(val,item,rowIndex){
				return '<input value="'+val+'" style="width:90%;margin-bottom:0;" type="text" title="'+val+'">';
			}},	
			{title:'生成结果', name:'result' ,width:20, sortable: true, type:'string', align:'center',renderer:
				function(val,item,rowIndex){
				if (true == val){
					return "成功";
				} else {
					return "失败";
				}
			}}
		];
		mmg=$('#grid').mmGrid({
			width:'auto',
			height:450,
			cols:cols,
			fullWidthRows:true,
            nowrap : true,
            multiSelect:true
		});
		
		$.post('action/generateFileAction?type=json',{json :JSON.stringify(heo)},function(data) {
			mmg.load(data.data);
		},"json");

	};
	return{
		init:init
	};
}();
$(function(){
	Result.init();
});
