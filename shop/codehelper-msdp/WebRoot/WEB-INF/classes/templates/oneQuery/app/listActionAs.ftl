package ${packageName}.action {
	import com.sinoservices.core.action.BaseAction;
	import com.sinoservices.core.controls.form.DataCollector;
	import com.sinoservices.core.controls.grid.PagingGrid;
	import com.sinoservices.core.data.FDS;
	import com.sinoservices.core.data.Parameter;
	import ${packageName}.view.${tableName}List;
	import com.sinoservices.core.util.MsgBox;
	import mx.events.CloseEvent;
	import mx.rpc.events.ResultEvent;
	import com.sinoservices.core.util.StoreUtil;
	import com.sinoservices.core.data.Store;
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
	public class ${tableName}ListAction extends BaseAction{
		/**
		 * 查询数据收集处理器
		 * @default
		 */
		public var queryDataCollector:DataCollector;
		/**
		 * 编辑数据收集处理器
		 * @default
		 */
		public var editorDataCollector:DataCollector;
		/**
		 * 
		 * @default 
		 */
		public var pagingGrid:PagingGrid;

		/**
		 * 
		 * @default 
		 */
		public var view:${tableName}List;

		/**
		 *构造方法
		 */
		public function ${tableName}ListAction() {
		}
		
		/**
		 * 查询方法
		 */
		public function queryHandler():void {
			//给工具栏加锁
			this.view.toolbar.lock();
			//设置查询的值
			var param:Parameter=new Parameter();
			param.args=this.queryDataCollector.toQueryField();
			pagingGrid.store.load(param,this.queryCallBack);
		}
		/**
		 * 查询方法的回调函数
		 */
		public function queryCallBack(store:Store,result:Object):void {
			//解锁
			view.toolbar.unLock();
		}
		/**
		 * 重置方法
		 */
		public function resetHandler():void {
			queryDataCollector.toForm(null);
			editorDataCollector.toForm(null);
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
			if(!isSelected("请选择一条需要复制的记录",true)) return ;
			
		}
		
			/**
		 * 编辑
		 */
		public function editHandler():void {
			//校验是否选择
			if(!isSelected("请选择一条需要编辑的记录",false)) return ;
			this.pagingGrid.editorWindow.open4Modify();
		}

		/**
		 * 保存
		 */
		public function saveHandler():void {
			view.toolbar.lock();	//加锁
			var rows:Array = this.pagingGrid.store.getChangedItems();
			FDS.call("${tableName?uncap_first }Manager","saveAll",[rows],this.afterSave);
		}
		
		/**
		 * 在保护之后的操作
		 * @param result
		 */
		private function afterSave(result:ResultEvent):void {
			view.toolbar.unLock();	//解锁
			this.queryHandler();
			MsgBox.alert("保存成功");
		}
		/**
		 * 删除
		 */
		public function removeHandler():void {
			if(!isSelected("请选择一条需要删除的记录",true)) return ;
			MsgBox.confirm('你真的要删除吗？删除将不能恢复','',clickRemoveHandler);
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
		 * 在保护之后的操作
		 * @param result
		 */
		private function afterRemove(result:ResultEvent):void {
			view.toolbar.unLock();//解锁
			this.queryHandler();
			MsgBox.alert("删除成功");
		}
		/**
		 * 双击表格事件
		 */
		public function pagingGrid_dblClickItemHandler():void {
			this.pagingGrid.editorWindow.open4Modify();
		}
		
		/**
		 * 公用的验证是否选择的方法
		 * @param msg	提示信息
		 * @param type  单选或者多选false校验选择单行； true校验选择多行
		 * @return 
		 */
		private function isSelected(msg:String,type:Boolean):Boolean{
			if(type == false){
				if(pagingGrid.getSelectedRows().length != 1){
					MsgBox.alert(msg,"提示信息");
					return false ;
				}
			}else if(type == true){
				if(!pagingGrid.hasSelection()){
					MsgBox.alert(msg,"提示信息");
					return false ;
				}
			}
			return true ;
		}
	}
}