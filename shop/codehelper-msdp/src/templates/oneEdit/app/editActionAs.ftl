package ${packageName}.action {
	import com.sinoservices.core.action.BaseAction;
	import com.sinoservices.core.controls.form.DataCollector;
	import com.sinoservices.core.controls.grid.PagingGrid;
	import com.sinoservices.core.data.FDS;
	import com.sinoservices.core.data.Parameter;
	import ${packageName}.view.${tableName}Edit;
	import com.sinoservices.core.util.MsgBox;
	import mx.events.CloseEvent;
	import mx.rpc.events.ResultEvent;

	/**
	 * <p>Description: AS类说明 </p>
	 * @author 
	 * @version 1.00
	 * <pre>
	 * 修改记录:
	 * 修改后版本    					 修改人   				修改日期   				修改内容
	 * $!{data.dateString}.1	   $!{data.author}   		$!{data.dateString}		create
	 * 
	 * </pre>
	 */
	public class ${tableName}EditAction extends BaseAction{
		/**
		 *
		 */
		public function ${tableName}EditAction() {
		}
		/**
		 * 查询时的表单信息类
		 * @default
		 */
		public var saveDataCollector:DataCollector;


		/**
		 * 
		 * @default 
		 */
		public var pagingGrid:PagingGrid;

		/**
		 * 
		 * @default 
		 */
		public var view:${tableName}Edit;

		/**
		 * param 编辑时选中的行记录
		 * return 如果新增或者菜单访问时则返回false,编辑时返回true
		 */
		override public function doOpenTabCallback(param:Object):Boolean {
			//1.新增，如果入参为空，则返回false;
			if (null == param) {
				//清空界面及列表数据
				
				return true;
			}
			//修改
			FDS.call("${tableName?uncap_first}Manager", "getEntityById", [param.id], this.afterQueryHandler);

			return true;
		}
		/**
		 * 重置方法
		 */
		public function resetHandler():void {
			this.saveDataCollector.toForm(null);
		}

		/**
		 * 新增
		 */
		public function addHandler():void {
			this.pagingGrid.editorWindow.open4Add();
		}
		/**
		 * 复制
		 */
		public function duplicateHandler():void {
			//todo
		}
		
			/**
		 * 编辑
		 */
		public function editHandler():void {
			//校验是否选 中
			
			
			this.pagingGrid.editorWindow.open4Modify();
		}

		/**
		 * 保存
		 */
		public function saveHandler():void {
			var rows = this.pagingGrid.store.getChangedItems();
			this.view.toolbar.lock();
			FDS.call("${tableName?uncap_first }Manager","saveAll",[rows],this.afterSave);
		}
		
		/**
		 * 在保护之后的操作
		 * @param result
		 */
		private function afterSave(result:ResultEvent):void {
			this.view.toolbar.unLock();
			MsgBox.alert("保存成功");
		}
		/**
		 * 删除
		 */
		public function removeHandler():void {
			MsgBox.confirm("你真的要删除吗？删除将不能恢复","",clickRemoveHandler);
		}
		/**
		 * 点确认删除时删除操作
		 */
		public function clickRemoveHandler(evt:CloseEvent):void{
			if(evt.detail==MsgBox.OK){
				view.toolbar.lock();	//加锁
				var rows:Array=this.pagingGrid.getSelectedRows();
				this.pagingGrid.store.removeRows(rows);
				var deleteRows:Array=this.pagingGrid.store.getRemovedRows();
				var rowIds:Array = StoreUtil.getRowsField(deleteRows,"表主键");
				FDS.call("${tableName?uncap_first }Manager", "removeAllByPk", [rowIds], this.afterRemove);
			}
			if(evt.detail == MsgBox.CANCEL){
				return;
			}
		}
		/**
		 * 在删除之后的操作
		 * @param result
		 */
		private function afterRemove(result:ResultEvent):void {
			//解锁工具栏
			this.view.toolbar.unLock();
			MsgBox.alert("删除成功");
		}
		/**
		 * 双击表格事件
		 */
		public function pagingGrid_dblClickItemHandler():void {
			
		}
	}
}