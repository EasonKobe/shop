/*form里面的文本框应该是所见即所得的
	input和label一组，还有label自个儿一组
*/
var Layout=function(){
	var storage=JSON.parse(localStorage.getItem('templateBuilder'));
	var init=function(){
		//步骤提示
		$('#steps').find('.pagination').find('li:eq(4)').find('a').addClass('selected');
		//存储heo对象的全局变量
		HEOContainer=typeof HEOContainer=='undefined'?createHtmlElementObject('root','container'):HEOContainer;
		//注册事件
		_eventRegister();
		_initLayoutFromStorage();
		//回车键事件
		_keybordEvent();
		//回退箭头的href路径
		var origin=storage.origin;
		if(origin == 'SQL'){
			$('#prev').attr('href','codehelper/jsp/sql.jsp?origin=SQL');
		}
		
	};
	//回车键事件
	var _keybordEvent=function(){
		//Grid表头样式设计编辑表单回车键事件
		$('#gridHeaderForm').keydown(function(e){
			if(e.keyCode == 13){
				$('#gridHeaderSetBtn').trigger('click');
				e.preventDefault();
			}
			
		});
		//未绑定数据的input样式设计编辑表单回车键事件
		$('#unbindDataInputStyleForm').keydown(function(e){
			if(e.keyCode == 13){
				$('#ubInputStyleSetBtn').trigger('click');
			}
		});
		//input样式设计编辑表单回车键事件
		$('#inputStyleForm').keydown(function(e){
			if(e.keyCode == 13){
				$('#inputStyleSetBtn').trigger('click');
			}
		});
		//GridStyleDesign编辑表单回车键事件
		$('#gridStyleForm').keydown(function(e){
			if(e.keyCode == 13){
				$('#gridStyleSetBtn').trigger('click');
			}
		});
		//表单样式编辑表单回车键事件
		$('#styleForm').keydown(function(e){
			if(e.keyCode == 13){
				$('#styleSetBtn').trigger('click');
			}
		});
	};
	//从配置的数据生成一个布局来
	var _initLayoutFromStorage=function(){
		//判断是否有本地存储的数据
		if(localStorage.getItem('templateBuilder')){
			var storage=JSON.parse(localStorage.getItem('templateBuilder'));
			var mode=storage.mode;
			if(mode=='oneQuery'){
				_oneQueryLayout(storage);
			}else if(mode=='oneEdit'){
				_oneEditLayout(storage);
			}else if(mode=='manyReference'){
				
			}else if(mode=='oneToManyEdit'){
				
			}else{
				alert('模式有误，请重新开始');
			}
			//让表格的cell能够拖拽和伸缩
			initCell();
			inputDraggable();
			inputResizable();
		}else{
			return;			
		}
	};
	/**
		单表编辑模式的布局生成
	*/
	var _oneEditLayout=function(storage){
		var editCols=storage.editCols;
		//表单的HEO对象
		var formInfo={
			row:Math.ceil(editCols.length/5),
			col:5
		};
		var formHEO=createHtmlElementObject(Math.uuid(),'FORM',formInfo);
		//表单里面的文本框对象
		for(var i in editCols){
			var col=editCols[i];
			//创建inputHEO对象
			var info={
				rowIndex:Math.floor(i/5),
				colIndex:i%5,
				type:"text",
				id:col.colName,
				name:col.colName,
				label:col.comment,
				bindCol:col.colName,
				dataType:col.dataType,
				colspan:1
			};
			var inputHEO=createHtmlElementObject(Math.uuid(),'INPUT',info);
			//放入表单HEO对象中
			formHEO.wrap.push(inputHEO);
		}
		//将表单HEO对象和GridHEO对象放入HEOContainer
		HEOContainer.wrap.push(formHEO);
		//根据生成的HEO对象，生成HTML布局
		_generateHtmlLayoutFromHeoObj(HEOContainer);
	};
	/*
		单表查询模式的布局生成
		两条线，一是HTML元素的生成，另一个是HEO对象的生成
	*/
	var _oneQueryLayout=function(storage){
		if (storage.origin == "SQL") {
			var json = {
					sql:storage.sql
				};
			$.ajax({
				url : "action/resolveSQLAction?type=json",
				type : "POST",
				dataType : "json",
				data : "json=" + JSON.stringify(json),
				async : false,
				success : function(data) {
					// 遍历后台得到的值，将列名加入storage.showCols中
					var selectedRows = data.data;
					//清空数据
					storage.queryCols=[];
					storage.showCols=[];
					for ( var i in selectedRows) {
						var row = selectedRows[i];
						var data = {
							colName : row.name,
							comment : row.comment,
							dataType : row.type
						};
						storage.queryCols.push(data);
						storage.showCols.push(data);
					}
					//将storage放入本地存储中
					localStorage.setItem('templateBuilder',JSON.stringify(storage));
				}
			});
		} 
		var queryCols=storage.queryCols;
		var showCols=storage.showCols;
		//表单的HEO对象
		var formInfo={
			row:Math.ceil(queryCols.length/5),
			col:5
		};
		var formHEO=createHtmlElementObject(Math.uuid(),'FORM',formInfo);
		for(var i in queryCols){
			var col=queryCols[i];
			//创建inputHEO对象
			var info={
				rowIndex:Math.floor(i/5),
				colIndex:i%5,
				type:"text",
				id:col.colName,
				name:col.colName,
				label:col.comment,
				bindCol:col.colName,
				dataType:col.dataType,
				colspan:1
			};
			var inputHEO=createHtmlElementObject(Math.uuid(),'INPUT',info);
			//放入表单HEO对象中
			formHEO.wrap.push(inputHEO);
		}
		//GRID的HEO对象
		var gridInfo={
			bindData:[]
		};
		for(var i in showCols){
			var ele={
				order:i,
				title:showCols[i].comment,
				column:showCols[i].colName,
				dataType:showCols[i].dataType
			};
			gridInfo.bindData.push(ele);
		}
		var gridHEO=createHtmlElementObject(Math.uuid(),'GRID',gridInfo);
		//将表单HEO对象和GridHEO对象放入HEOContainer
		HEOContainer.wrap.push(formHEO);
		HEOContainer.wrap.push(gridHEO);
		//根据生成的HEO对象，生成HTML布局
		_generateHtmlLayoutFromHeoObj(HEOContainer);
		//初始化GRID事件
		_registerGridEvent();
	};
	//根据HEOContainer的内容生成一个HTML布局
	var _generateHtmlLayoutFromHeoObj=function(HEOContainer){
		for(var i in HEOContainer.wrap){
			_addChildHtmlToParentHtml('#demo',HEOContainer.wrap[i]);
		}
	};
	var _addChildHtmlToParentHtml=function(parentSelector,HEO){
		//子元素的jqueryDOM对象
		var elementHtml=_getHtmlElement(HEO);
		//父元素的jqueryDOM对象
		var parent=$(parentSelector);
		//父元素的类型
		var parentType=parent.attr('data-type');
		if(parentType=='CONTAINER'){
			//如果是container的话，字节放进去
			parent.append(elementHtml);
		}else if(parentType=='FORM'){
			//如果父节点是表单的话情况就不一样了
			parent.find('tr:eq('+HEO.info.rowIndex+')')//对应的行数
				.find('td:eq('+HEO.info.colIndex+')')//对应的列数
				.html(elementHtml);
		}else if(parentType=='GRID'){
			//目前Grid里面不能放东西
		}
		//判断当前节点是否有子节点，有的话遍历处理
		if(HEO.wrap.length>0){
			var children=HEO.wrap;
			for(var i in children){
				_addChildHtmlToParentHtml('div[data-uid="'+HEO.uid+'"]',children[i]);
			}
		}
	};
	//根据HEO对象，生成一个jqueryDOM对象
	var _getHtmlElement=function(HEO){
		//根据类型，复制不同的element，然后给该element添加一个属性（data-uid）作为唯一标识
		var id=HEO.uid,
			type=HEO.type;
		var element=null;
		if(type=="FORM"){
			element=$('#formElement').clone();
			//处理表单的样式
			var table=[];
			for(var i=0;i<parseInt(HEO.info.row);i++){
				table.push('<tr>');
				for(var j=0;j<HEO.info.col;j++){
					table.push('<td style="width:'+100/parseInt(HEO.info.col)+'%" class="cell"></td>');
				}
				table.push('</tr>');
			}
			element.find('table').attr({
				'data-row':HEO.info.row,
				'data-col':HEO.info.col
			}).html(table.join(''));
		}else if(type=="GRID"){
			element=$('#gridElement').clone();
			//在Grid中要展示的列
			var bindData=HEO.info.bindData;
			//表头数据
			var thead=[
				'<tr>'
			];
			for(var i in bindData){
				var dataHtml=[
					'<th style="width:100px;" data-datatype="'+bindData[i].dataType+'" data-column="'+bindData[i].column+'" data-order="'+bindData[i].order+'">',
					'<div class="th-content">',
					'<a data-toggle="tooltip" title="'+bindData[i].column+'">',
					bindData[i].title,
					'</a>',
					'</div>',
					'</th>'
				];
				thead.push(dataHtml.join(''));
			}
			thead.push('</tr>');
			element.find('thead').html(thead.join(''));
			//模拟数据
			var virtualData=[
				'<tr>'
			];
			for(var i in bindData){
				virtualData.push('<td>&nbsp;</td>');
			}
			virtualData.push('</tr>');
			for(var i =0;i<10;i++){
				element.find('tbody').append(virtualData.join(''));
			}
			
		}else if(type=="INPUT"){
			element=$('#inputElement').clone();
			//处理文本框的样式，展示一些信息
			element.find('.input-group:first')
				.find('label').text(HEO.info.label);
			element.find('.input-group:first')
				.find('input').attr({
					'data-bindcol':HEO.info.bindCol,
					'data-dataType':HEO.info.dataType,
					'id':HEO.info.id,
					'name':HEO.info.name,
					'placeholder':HEO.info.label
				});
		}
		element.attr('data-uid',id);
		element.removeAttr('id');
		return element;
	};
	var _eventRegister=function(){
		//在demo面板里面，各个兄弟元素能够排序
		$('#demo').sortable({
			revert: true,
			handle: ".containerDrag",
			stop:function(event,ui){
				var item=ui.item;
				buildStructure(item);
			}
		});
		//含有类inputWrap的元素能被放到demo里面,即FORM元素和GRID元素能够被拖拽
		$('.inputWrap').draggable({
			connectToSortable: '#demo,.subContainer',
			handle:'.containerDrag',
			// zIndex: 100,
			revert: "invalid",
			helper: "clone",
			start:function(event,ui){
			},
			drag:function(event,ui){
				ui.helper.width(400);
			},
			stop:function(event,ui){
				$('#demo .subContainer').sortable({
					revert: true,
					handle: ".containerDrag",
					stop:function(event,ui){
						var item=ui.item;
						buildStructure(item);
					}		
				});
				initCell();
			}
		});

		//含有类input的元素能被放到表单里面
		$('#element-list .input').draggable({
			// connectToSortable: '.cell',
			handle:'.inputDrag',
			// zIndex: 100,
			revert: "invalid",
			helper: "clone",
			start:function(event,ui){
			},
			drag:function(event,ui){
			},
			stop:function(event,ui){
			}
		});

		//删除事件
		$('#demo').on('click','.remove',function(){
			var target=$(this).parent('.element-row');
			deleteHEO(target);
			target.remove();
		});
		//文本框双击事件
		$('#demo').on('dblclick','.input',function(e){
			e.stopPropagation();
			var $this=$(this);
			$this.addClass('dblclicked');
			var $input=$this.find('input');
			var bindCol=$input.attr('data-bindcol');
			//判断当前编辑的标签是否绑定了数据
			if(typeof bindCol === 'undefined'){
				/*
					若还没有绑定数据，则先载入选中的表的数据，
					然后用下拉框展示，让选择一列
				*/
				//载入数据
				var json = {
						tableName:	storage.selectedTable
					};
				$.ajax({
					url:'action/columnQueryAction?&type=json',
					type:'POST',
					dataType:'json',
					async:false,
					data:'json='+JSON.stringify(json),
					success:function(data){
						//构造下拉框
						var options=[];
						for(var i =0;i<data.data.length;i++){
							var col=data.data[i];
							options.push('<option value="'+col.name+'">'+col.name+'</option>');
						}
						$('#ubBindColumn').html(options.join(''));
						//事件绑定
						//弹出modal
						$('#unbindDataInputStyleModal').modal({
							backdrop:false
						});
					}
				});
				//确定按钮事件
				$('#ubInputStyleSetBtn').click(function(){
					//收集数据 
					var bindData=$('#ubBindColumn').val();
					var id=$('#ubInputId').val();
					var name=$('#ubInputName').val();
					var type=$('#ubInputType').val();
					var label=$('#ubInputLabel').val();
					//置入数据
					$('#demo').find('.inputWrap').find('.dblclicked').find('input').attr({
						'id':id,
						'name':name,
//						'label':label,
						'placeholder':label,
						'data-bindcol':bindData,
						'data-type':type   //type不能被修改
					}).prev().text(label);
					//隐藏模态对话框
					$('#unbindDataInputStyleModal').modal('hide');
					//更新对应的HEO对象
					updateHEO($input.parents('.element-row:first'));
				});
			}else{
				//设置初始值
				$('#inputId').val($input.attr('id'));
				$('#inputName').val($input.attr('name'));
				$('#inputType').val($input.attr('data-type'));
				$('#inputLabel').val($input.prev().text());
				//弹窗
				$('#inputStyleModal').modal({
					backdrop:false
				});
				//确定按钮
				$('#inputStyleSetBtn').click(function(){
					//收集数据 
					var id=$('#inputId').val();
					var name=$('#inputName').val();
					var type=$('#inputType').val();
					var label=$('#inputLabel').val();
					//置入数据
					$('#demo').find('.inputWrap').find('.dblclicked').find('input').attr({
						'id':id,
						'name':name,
//						'label':label,
						'placeholder':label,
						'data-type':type   //type不能被修改
					}).prev().text(label);
					//隐藏模态对话框
					$('#inputStyleModal').modal('hide');
					//更新对应的HEO对象
					updateHEO($input.parents('.element-row:first'));
				});
			}
		});
		//form样式设计按钮
		$('#demo').on('click','.form-style',function(){
			$form=$(this).parent().find('form');
			$('#styleModal').modal({
				backdrop:false
			});
			//modal窗口关闭后，文本框的值清空
			$('#styleModal').on('hidden',function(){
				$(this).find('input').val('');
			});
			//确定按钮
			$('#styleSetBtn').on('click',function(){
				var row=$('#formRow').val();
				var col=$('#formCol').val();
				debugger;
				if(row!=''&&col!=''){
					var table=[
						'<table data-row="'+row+'" data-col="'+col+'">'
					];
					for(var i=0;i<parseInt(row);i++){
						table.push('<tr>');
						for(var j=0;j<col;j++){
							table.push('<td style="width:'+100/parseInt(col)+'%" class="cell"></td>');
						}
						table.push('</tr>');
					}
					table.push('</table>');
					//将表单中显示布局
					$form.html(table.join(''));
					//让表单中的cell样式可排序，以便于把input标签放进来
					initCell();
				}
				//隐藏模态对话框
				$('#styleModal').modal('hide');
				//更新对应的HEO对象
				updateHEO($form.parents('.element-row:first'));
			});
		});
		//form样式设置对话框关闭后的事件
		$('#unbindDataInputStyleModal').on('hidden', function () {
			$('#demo').find('.inputWrap').find('.dblclicked').removeClass('dblclicked');
		});
		$('#inputStyleModal').on('hidden', function () {
			$('#demo').find('.inputWrap').find('.dblclicked').removeClass('dblclicked');
		});
		//grid样式设计按钮
		$('#demo').on('click','.grid-style',function(){
			$form=$(this).parent().find('form');
			$('#gridStyleModal').modal({
				backdrop:false
			});
			//modal窗口关闭后，文本框的值清空
			$('#gridStyleModal').on('hidden',function(){
				$(this).find('input').val('');
			});
			//确定按钮
			$('#gridStyleSetBtn').on('click',function(){
				var row=$('#formRow').val();
				var col=$('#formCol').val();
				var table=[
					'<table>'
				];
				for(var i=0;i<parseInt(row);i++){
					table.push('<tr>');
					for(var j=0;j<col;j++){
						table.push('<td style="width:'+100/parseInt(col)+'%" class="cell"></td>');
					}
					table.push('</tr>');
				}
				table.push('</table>');
				//将表单中显示布局
				$form.html(table.join(''));
				//让表单中的cell样式可排序，以便于把input标签放进来
				initCell();
				//隐藏模态对话框
				$('#GridStyleDesign').modal('hide');
			});
		});
		/*
		 * 点击下一步，将配置好的对象缓存在本地存储中
		 * 在下一步选择完模板后一起传到后台去
		 */
		$('#next').on('mousedown',function(){
			HEOContainer.resource=storage;
			localStorage.setItem('heo',JSON.stringify(HEOContainer));
		});
		/*
			加一行 标签事件
			当表单排满input时，可以使用该标签添加新一行
		*/
	
		$('#demo').on('click','.add-row',function(){
			//获取父节点
			var $element=$(this).parent('.element-row');
			//获取父节点下面的table
			var $table=$element.find('.formWrap table');
			//table的列数
			var col=parseInt($table.attr('data-col'));
			//构造table行
			var tr=[
				'<tr>'
			];
			var td='<td style="width:'+(100/col)+'%" class="cell">&nbsp;</td>';
			for(var i=0;i<col;i++){
				tr.push(td);
			}
			tr.push('</tr>');
			//添加到table中
			$table.append(tr.join(''));
			//初始化cell里面的事件
			initCell();
		});
		
		/*
			加一列 标签事件
			当grid想添加一列新的数据展示时，可以使用该标签的事件
		*/
	
		$('#demo').on('click','.add-col',function(){
			//获取父节点
			var $element=$(this).parent('.element-row');
			//获取父节点下面的table
			var $table=$element.find('.gridWrap table');
			//给表头加一列
			var $th_tr=$table.find('thead').find('tr');
			//要添加的模板
			var boilerplate=[
				'<th style="width:100px;" data-column="" data-order="'+($th_tr.find('th').length-1)+'">',
				'<div class="th-content">',
				'<a data-toggle="tooltip" title="" data-original-title="">双击绑定数据源</a>',
				'</div>',
				'</th>'
			];
			$th_tr.append(boilerplate.join(''));
			//给tbody每一行加一列
			var tb_tr=$table.find('tbody').find('tr');
			$.each(tb_tr,function(i,v){
				$(v).append('<td>&nbsp;</td>');
			});
			//给新添加进去的表头注册事件
			$th_tr.find('th:last').dblclick(function(){
				_thEvent($(this));
			}).find('.th-content a').tooltip({
				placement:'bottom'
			});//工具提示
			
		});
	};
	//初始化表格的单元格，使得单元格能被放置其他元素
	var initCell=function(){
		$('#demo').find('table .cell').droppable({
			over:function(event,ui){
				/**
				 *20140523.1 By Ocean 不需要设置高度，保持界面不变形
				 * $(this).height(100);
				 */
				$(this).addClass('drop-over');
			},
			out:function(event,ui){
				$(this).height('auto');
				$(this).removeClass('drop-over');
			},
			drop:function(event,ui){
				var $this=$(this);
				$this.removeClass('drop-over');
				/*
					如果被拖进来的元素是来自左边菜单栏的，则复制
					如果是来自其他单元格的，则对换
				*/
				//判断被拖进来的元素是来自其他单元格的还是来自菜单栏的
				if(ui.helper.parents('#demo').length>0){
					//来自其它单元格的
					//当前cell里面的文本框给被拖动的文本框
					$this.find(".element-row:first").appendTo(ui.helper.parents('.cell'));
					//被拖动文本框给当前cell
					ui.helper.parents('.cell').find(".element-row:first").appendTo($this);					
				}else{
					//来自右侧菜单栏的
					//当前单元格的内部内容清空
					$this.empty();
					//将被拖动的元素插入该单元格里面
					ui.helper.clone().removeAttr('style').prependTo(this);					
					//将刚添加进入的input设成可拖拽
					inputDraggable();
					//将刚添加进入的input设成可伸缩
					inputResizable();
				}
				//将单元格的高度设成自适应，然后单元格里面的元素设成可变大小
				$this.height('auto');
				//更新heo对象
				buildStructure($(event.target).find('.element-row'));
				//更新form里面的input内容
				_updateInputSequenceOfForm($this.parents('.element-row:first').attr('data-uid'));
			}
		});
	};
	//Demo里面的文本框能够被拖拽
	var inputDraggable=function(){
		//含有类input的元素能被放到表单里面
		$('#demo .input').draggable({
			// connectToSortable: '.cell',
			handle:'.inputDrag',
			// zIndex: 100,
			revert: "invalid",
			helper: "clone",
			start:function(event,ui){
				
			},
			drag:function(event,ui){
				
			},
			stop:function(event,ui){
			}
		});
	};
	//demo里面的文本框能够伸缩
	var inputResizable=function(){
		//遍历demo里面的input组，如果没有ui-resizable的样式，就给当前元素添加可伸缩属性
		$('#demo .cell .input-group').each(function(i,v){
			if(!$(this).hasClass('ui-resizable')){
				//给元素添加可伸缩属性
				$(this).resizable({
					start:function(event,ui){
						//获取每个单元格的长度
						originWidth=ui.helper.width();
						//20140523.1 By Ocean 伸缩时，界面不变形
						$(this).css({"position": "absolute", "top": "-27px", "z-index": "9999999"});
						$(this).closest("td").css({"height": "55px"});
					},
					stop:function(event,ui){
						var $td=$(this).parents('td.cell');
						var colspan=$.type($td.attr('colspan'))=='undefined'?1:parseInt($td.attr('colspan'));
						var width=ui.helper.width();
						//元素拉长
						if(width>originWidth){
							//删除下一个input
							deleteHEO($td.next().find('.element-row:first'));
							$td.attr('colspan',(colspan+1)).next().remove();
							
						//元素缩小
						}else if((originWidth-width)>(originWidth/colspan)){
							$td.attr('colspan',(colspan-1)).after('<td class="cell ui-droppable"></td>');
						}
						//移出jqueryUi给input-group设置的长度，是的input充满整个元素
						ui.helper.removeAttr('style');
						initCell();
						updateHEO(ui.helper.parents('.element-row:first'));
					}
				});
			}

		});
		
	};
	//注册Grid事件，比如表头可编辑可排序
	var _registerGridEvent=function(){
		$('#demo').find('div[data-type=GRID]')
			.find('.gridWrap table').find('thead tr')
			// .sortable('destroy')
			.sortable({
				placeholder: "ui-state-highlight",
				start:function(event, ui){
					ui.item.addClass('blueOutLine')
						// .siblings('th:not(".ui-state-highlight")').addClass('greenOutLine')
						.siblings('.ui-state-highlight').addClass('greenOutLine');
				},
				stop:function(event, ui){
					ui.item.removeClass('blueOutLine');
					//更新Grid信息，最重要的是grid.info.bindData的次序
					buildStructure(ui.item.parents('.element-row:first'));
				}
			}).find('th').dblclick(function(){
				//弹窗修改表头信息
				_thEvent($(this));
			}).find('.th-content a').tooltip({
				placement:'bottom'
			});
			//模态窗口关闭后清除双击的标识
			$('#gridHeaderModal').on('hidden', function () {
				$('#demo').find('div[data-type=GRID]')
					.find('.gridWrap table').find('thead th').removeClass('dblclicked');
			});
			//模态窗口确定按钮点击后事件
			$('#gridHeaderSetBtn').click(function(){
				var bindCol=$('#gridHeaderColumn').val();
				var name=$('#gridHeaderInputName').val();
				var target=$('#demo').find('div[data-type=GRID]')
					.find('.gridWrap table').find('.dblclicked');
				target.attr('data-column',bindCol)
				.find('a').attr('data-original-title',bindCol).text(name);
				//关闭模态窗口
				$('#gridHeaderModal').modal('hide');
				//清除数据
				$('#gridHeaderColumn').val('');
				$('#gridHeaderInputName').val('');
				//更新对象
				buildStructure(target.parents('element-row:first'));
			});
			
	};
	//表头事件，双击和工具提示
	var _thEvent=function($this){
		//添加一个标识
		$this.addClass('dblclicked');
		var defaultData=$this.attr('data-column');
		var defaultName=$this.find('a').text();
		//载入数据
		var json = {
				tableName:	storage.selectedTable
			};
		$.ajax({
			url:'action/columnQueryAction?type=json',
			type:'POST',
			dataType:'json',
			async:false,
			data:'json='+JSON.stringify(json),
			success:function(data){
				//构造下拉框
				var options=[];
				for(var i =0;i<data.data.length;i++){
					var col=data.data[i];
					if(defaultData==col.name){
						options.push('<option value="'+col.name+'" selected = "selected">'+col.name+'</option>');
					}else{
						options.push('<option value="'+col.name+'">'+col.name+'</option>');
					}
				}
				$('#gridHeaderColumn').html(options.join(''));
				//弹出下拉框
				$('#gridHeaderInputName').val(defaultName);
				$('#gridHeaderModal').modal({
					backdrop:false
				});
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
	/**
		该方法用来构造一个将要传给后台的对象
		当元素被拖拽到某一元素A上时，就构造一个HtmlElementObject（HEO）对象，
		并且把该元素赋值到A元素对应的HEO对象的子元素数组中去
	*/
	var buildStructure=function(item){
		
		var uid=item.attr('data-uid');
		if(typeof uid!='undefined' && uid!=''){
		//表示当前已经有uid了,更新之
			updateHEO(item);
		}else{
			//当前元素没有uid
			//生成一个uuid
			uid=Math.uuid();
			//获取当前元素的类型
			var type=item.attr('data-type');
			//当前元素的一些信息
			var info=_createInfo(item);
			//判断当前元素的类型
			
			//构造当前元素对应的heo对象
			var HEOCurrent=createHtmlElementObject(uid,type,info);
			//将当前的uuid写入html元素中
			item.attr('data-uid',uid);
			//父节点id
			var parentUid=item.parents('.element-row:first').attr('data-uid');
			//将当前元素的信息插到父节点下面
			_addChildToOneHEO(parentUid,HEOCurrent);
		}
	};
	/**
		根据HTML元素的jquery对象的不同类型生成HEO里面的info对象
	*/
	var _createInfo=function(item){
		var type=item.attr('data-type');
		var info={};
		if(type=='DIV'){

		}else if(type=='FORM'){
			info.row=item.find('table').attr('data-row');
			info.col=item.find('table').attr('data-col');
		}else if(type=='GRID'){
			info.bindData=[];
			var ths=item.find('th');
			for(var i =0;i<ths.length;i++){
				var th=$(ths[i]),
					data={};
				data.order=i;
				data.title=th.text();
				data.column=th.attr('data-column');
				data.dataType=th.attr('data-datatype');
				info.bindData.push(data);
			}
		}else if(type=='INPUT'){
			info.rowIndex=item.parents('tr:first').index();
			info.colIndex=item.parents('td:first').index();
			var colspan=item.parents('td:first').attr('colspan');
			info.colspan=typeof colspan=='undefined'?1:colspan;
			var input=item.find('input');
			info.type=input.attr('data-type');
			info.id=input.attr('id');
			info.name=input.attr('name');
			info.label=input.prev().text();
			info.bindCol=input.attr('data-bindcol');
			info.dataType=input.attr('data-datatype');
		}
		return info;
	};
	/**
		将HEO对象插到指定父节点下
	*/
	var _addChildToOneHEO=function(parentUid,HEO){
		var result=_addChild(HEOContainer,parentUid,HEO);
	};

	var _addChild=function(parentHEO,parentUid,HEO){
		if(parentHEO.uid==parentUid){
			parentHEO.wrap.push(HEO);
			return true;
		}else{
			for(var i in parentHEO.wrap){
				if(_addChild(parentHEO.wrap[i], parentUid, HEO)){
					return true;
				}
			}
		}
	};
	/**
		当伸缩文本框或者给文本框、表单、grid设置样式时，更新该HTML元素对应的HEO对象
		参数item为class='element-row'的html的jquery对象
	*/
	var updateHEO=function(item){
		var uid=item.attr('data-uid');
		var result=findTargetAndUpdate(HEOContainer,uid,item);
	};

	var findTargetAndUpdate=function(HEO,uid,item){
		if(HEO.uid==uid){
			HEO.info=_createInfo(item);
			HEO.wrap= HEO.wrap.length===0 ? []:HEO.wrap;
			return true;
		}else{
			for(var i in HEO.wrap){
				if(findTargetAndUpdate(HEO.wrap[i],uid,item)){
					return true;
				}
			}
		}
	};
	/**
		交换表单中的指定的两个input的顺序.
		oneIndex和anotherIndex都是从0开始
	*/
	var _swapInputSequenceOfForm=function(formId,oneIndex,anotherIndex){
		var HEO=_findTargetHEO(HEOContainer,formId);
		if(typeof HEO !='undefined'){
			var children=HEO.wrap,
				max=Math.max(oneIndex,anotherIndex),
				min=Math.min(oneIndex,anotherIndex);
			var maxOne=children[max],
				minOne=children[min];
			children[min]=maxOne;
			children[max]=minOne;
		}else{
			alert('Target uid is '+formId);
			alert('Not find target Uid');
		}
	};
	//更新form里面的input的顺序
	var _updateInputSequenceOfForm=function(uid){
		var HEO=_findTargetHEO(HEOContainer,uid);
		var inputItems=$('div[data-uid='+uid+']').find('.input');
		HEO.wrap=[];
		for(var i = 0;i<inputItems.length;i++){
			var item=$(inputItems[i]);
			//唯一标识
			var uid=item.attr('data-uid');
			//获取当前元素的类型
			var type=item.attr('data-type');
			//当前元素的一些信息
			var info=_createInfo(item);
			//构造当前元素对应的heo对象
			var HEOCurrent=createHtmlElementObject(uid,type,info);
			HEO.wrap.push(HEOCurrent);
		}
	};
	//寻找指定的HEO元素
	var _findTargetHEO=function(HEO,Uid){
		//相等就返回当前的，不相等就找子元素，没找到就返回空
		if(HEO.uid === Uid){
			return HEO;
		}else{
			var children=HEO.wrap;
			if(children.length>0){
				for(var i in children){
					return _findTargetHEO(children[i],Uid);
				}
			}else{
				//没找到就返回空
				//20140523.1 By Ocean 返回空对象，而不是null
				return {};
			}
		}
	};

	/**
		删除指定的HEO对象
	*/
	var deleteHEO=function(item){
		if(item.size()>0){
			var parentUid=item.parents('.element-row:first').attr('data-uid');
			findTargetAndDelete(HEOContainer,parentUid,item.attr('data-uid'));	
		}
	};

	var findTargetAndDelete=function(HEO,parentUid,currentUid){
		if(HEO.uid==parentUid){
			var children=HEO.wrap;
			for(var i in children){
				if(children[i].uid==currentUid){
					children.splice(i,1);
					break;
				}
			}
		}else{
			for(var i in HEO.wrap){
				findTargetAndDelete(HEO.wrap[i],parentUid,currentUid);
			}
		}
	};
	return{
		init:init
	};
}();

function cleanHtml(e) {
	$(e).parent().append($(e).children().html());
}
$(function(){
	Layout.init();
});















