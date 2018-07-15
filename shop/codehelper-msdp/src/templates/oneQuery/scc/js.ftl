<#assign grid = page.resource.showFields>
var ${tableName}Query=function(){
	var mmg;
	var _init=function(){
		_initGrid();
	};
	var _initGrid=function(){
		var cols=[
				<#list grid as column>
					{title: "${column.comment}",name:"${column.fieldName}" ,width:120, sortable: true, type:'string', align:'left'},
				</#list>
		];
		mmg=$('.mmg').mmGrid({
			width:'auto',
			height: 450,
			cols: cols, 
            checkCol:true,
            fullWidthRows:true,
            nowrap : true,
            autoLoad : false,
            url : '${moduleName}/${tableName?uncap_first}QueryAction!page.shtml'
		});
	};
	return{
		init:_init,
		
		/**查询*/
		load: function() {
		  	mmg.load($("#${tableName}QueryForm").serialize());
		},
		
		/**重置**/
		reset: function(){
			PUI.util.resetForm($("#${tableName}QueryForm"));
		}
	};
}();

$(function(){
	${tableName}Query.init();
});