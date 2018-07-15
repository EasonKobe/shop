var columnEdit=function(){
	var firstGrid=null,secondGrid=null;
	var storage=JSON.parse(localStorage.getItem('templateBuilder'));
	var init=function(){
		//步骤提示
		$('#steps').find('.pagination').find('li:eq(3)').find('a').addClass('selected');
		// _loadData();
		if(storage.mode=='oneQuery'){
			//如果当前模式是单表查询的话，将选择列的内容变成两列，一列是搜索列，另一列是显示列
			$('.panel-body').find('.list-wrap:first').addClass('two-col')
				.find('.list-header').text('搜索列');
			oneQueryEvent();
		}else if(storage.mode=='oneEdit'){
			oneEditEvent();
		}else if (storage.mode=='pure'){
			pureEvent();
		}
		//将上一步的路径更改为table.html?origin=DB
		if(storage.origin == 'DB'){
			$('#prev').attr('href','codehelper/jsp/table.jsp?origin=DB');
		}
//		else if(storage.origin == 'SQL'){
//			$('#prev').attr('href','codehelper/jsp/sql.jsp?origin=SQL');
//		}
		
		//若isLayout是false
		if (storage.isLayout == "false"){
			$("#next").attr("href","codehelper/jsp/template.jsp");
		} else {
			$("#next").attr("href","codehelper/jsp/layout.jsp");
		}
		
		_initGrid();
	};
	//生成mmgrid对象
	var _initGrid=function(){
		var mode=storage.mode;
		var gridWidth=760;
		if(mode=='oneQuery'){
			gridWidth=gridWidth/2;
		}
		//表头
		var cols=[
			{title:'列名',name:'name',width:100,sortable: true, type:'string', align:'left'},
			{title:'列注释',name:'comment',width:100,sortable: true, type:'string', align:'left'},
			{title:'类型',name:'type',width:100,sortable: true, type:'string', align:'left'},
			{title:'是否主键',name:'isPk',width:100,sortable: true, type:'boolean', align:'left',hidden: true}
		];
		firstGrid=$('#firstGrid').mmGrid({
			width:'auto',
			height: 430,
			cols: cols, 
            checkCol:true,
            autoLoad : false,
            fullWidthRows:true,
            nowrap : true,
            multiSelect:true
		});
		
		var json = {
			tableName:	storage.selectedTable
		};
		
		secondGrid=$('#secondGrid').mmGrid({
			width:'auto',
			height: 430,
			cols: cols, 
            checkCol:true,
            autoLoad : false,
            fullWidthRows:true,
            nowrap : true,
            multiSelect:true
		});
		
//		$.ajax({
//			url:'action/columnQueryAction?type=json',
//			data:'json='+JSON.stringify(json),
//			dataType:'json',
//			method:'POST',
//			async:false,
//			success:function(data){
//				firstGrid.load(data.data);
//				secondGrid.load(data.data);
//			}
//		});
		$.post('action/columnQueryAction?type=json',{json :JSON.stringify(json)},function(data) {
			firstGrid.load(data.data);
			firstGrid.select("all");
			secondGrid.load(data.data);
			secondGrid.select("all");
		},"json");
		

	};
	// storage.editCols=[];
	//‘单表编辑’模式下
	var oneEditEvent=function(){
		$('#next').on('mousedown',function(e){
			//刚进页面时，被选择的列置为空
			storage.editCols=[];
			//获取被checked的多选框
			var selectedRows=firstGrid.selectedRows();
			if(selectedRows.length>0){
				//初始化
				storage.editCols=[];
				storage.sql="";
				//遍历选中的行，将列名加入storage.editCols
				for(var i in selectedRows){
					var row=selectedRows[i];
					var data={
							colName:row.name,
							comment:row.comment,
							dataType:row.type,
							isPk:row.isPk
					};
					storage.editCols.push(data);
				}
				//将storage放入本地存储中
				localStorage.setItem('templateBuilder',JSON.stringify(storage));
			}else{
				alert('Not Checked!');
			}
		});
	};
	//‘单表查询’模式下
	var oneQueryEvent=function(){
		$('#next').on('mousedown',function(e){
			//清空数据
			storage.queryCols=[];
			storage.showCols=[];
			storage.sql="";
			//获取被checked的多选框
			var queryCols=firstGrid.selectedRows();
			var showCols=secondGrid.selectedRows();
			if(queryCols.length>0&&showCols.length>0){
				for(var i in queryCols){
					var row=queryCols[i];
					var data={
							colName:row.name,
							comment:row.comment,
							dataType:row.type,
							isPk:row.isPk
					};
					storage.queryCols.push(data);
				}
				for(var i in showCols){
					var row=showCols[i];
					var data={
							colName:row.name,
							comment:row.comment,
							dataType:row.type,
							isPk:row.isPk
					};
					storage.showCols.push(data);
				}
				//将storage放入本地存储中
				localStorage.setItem('templateBuilder',JSON.stringify(storage));
			}else{
				alert('搜索列和显示列都必须至少选择一个字段');
			}
		});
	};
	//‘原始表’模式下
	var pureEvent=function(){
		$('#next').on('mousedown',function(e){
			//刚进页面时，被选择的列置为空
			storage.editCols=[];
			//获取被checked的多选框
			var selectedRows=firstGrid.selectedRows();
			if(selectedRows.length>0){
				//初始化
				storage.editCols=[];
				storage.sql="";
				//遍历选中的行，将列名加入storage.editCols
				for(var i in selectedRows){
					var row=selectedRows[i];
					var data={
							colName:row.name,
							comment:row.comment,
							dataType:row.type,
							isPk:row.isPk
					};
					storage.editCols.push(data);
				}
				//将storage放入本地存储中
				localStorage.setItem('templateBuilder',JSON.stringify(storage));
				HEOContainer=typeof HEOContainer=='undefined'?createHtmlElementObject('root','container'):HEOContainer;
				HEOContainer.resource=storage;
				localStorage.setItem('heo',JSON.stringify(HEOContainer));
			}else{
				alert('Not Checked!');
			}
		});
	};
	
	//创建用来表示html的对象
	var createHtmlElementObject=function(uid,type,info){
		var tempObj=new Object;
		tempObj.uid=uid;//唯一标识
		tempObj.type=type;//对象的类型：现在有DIV,FORM,Grid,INPUT
		tempObj.info=typeof info == 'undefined' ?{}:info;//存储对象的属性，比如每个元素的css、或者input的type
		tempObj.wrap=[];//该元素内嵌的元素
		return tempObj;
	};
	return{
		init:init,
		oneEdit:oneEditEvent,
		oneQuery:oneQueryEvent,
		pure:pureEvent
	};
}();
$(function(){
	columnEdit.init();
});
