<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>布局修改--CodeHelper</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	<%@ include file="/codehelper/common_codehelper.jsp" %>
    <!-- Le styles -->
    <style>
      .container-fluid{
      	margin-top:75px;
      }
    </style>
  </head>

  <body>
    <%@ include file="/codehelper/top.jsp" %>
    
    <a id="prev" href="codehelper/jsp/column.jsp" class="navigation-arrow">
    	<!--[if !IE]>-->
          <img src="codehelper/images/arrow.svg" alt="" width="28" height="42"> 
        <!--<![endif]-->
        <!--[if lt IE 9]>
          <object src="codehelper/images/arrow-left.svg" classid="image/svg+xml"
                  width="28" height="42" id="leftSVGObject"> 
          </object>
        <![endif]-->
        <!--[if gte IE 9]>
          <object data="codehelper/images/arrow-left.svg" type="image/svg+xml"
                  width="28" height="42" id="leftSVGObject">
          </object>
        <![endif]-->
    </a>
    
	<div class="container-fluid">
        <div class="row-fluid">
            <div id="element-list" class="span2 well">
                <ul class="nav nav-pills nav-stacked">
                    <li>
                        <div id="divElement" data-type='DIV' class="element-row ui-draggable inputWrap">
                            <a href="javascript:void(0);" class="remove label label-important">
                                <i class="icon-remove icon-white"></i>删除</a>
                            <span class="drag containerDrag label">
                                <i class="icon-move"></i>拖动
                            </span>
                            <div class="preview">
                                div层
                            </div>
                            <div class="view">
                                <div class='divWrap subContainer'></div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div id="formElement" data-type='FORM' class="element-row ui-draggable inputWrap">
                            <a href="javascript:void(0);" class="remove label label-important">
                                <i class="icon-remove icon-white"></i>删除</a>
                            <span class="drag containerDrag label">
                                <i class="icon-move"></i>拖动
                            </span>
                            <span class="style form-style label label-info">
                                <i class="icon-edit"></i>样式设计
                            </span>
                            <span class="add-row label label-info">
                                <i class="icon-plus"></i>加一行
                            </span>
                            <div class="preview">
                                表单
                            </div>
                            <div class="view">
                                <form class="formWrap">
                                    <table data-row="2" data-col="5">
                                        <tbody>
                                            <tr>
                                                <td style="min-height:10px;width:20%" class="cell ui-sortable"></td>
                                                <td style="min-height:10px;width:20%" class="cell ui-sortable"></td>
                                                <td style="min-height:10px;width:20%" class="cell ui-sortable"></td>
                                                <td style="min-height:10px;width:20%" class="cell ui-sortable"></td>
                                                <td style="min-height:10px;width:20%" class="cell ui-sortable"></td>
                                            </tr>
                                            <tr>
                                                <td style="min-height:10px;width:20%" class="cell ui-sortable"></td>
                                                <td style="min-height:10px;width:20%" class="cell ui-sortable"></td>
                                                <td style="min-height:10px;width:20%" class="cell ui-sortable"></td>
                                                <td style="min-height:10px;width:20%" class="cell ui-sortable"></td>
                                                <td style="min-height:10px;width:20%" class="cell ui-sortable"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </form>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div id="gridElement" data-type='GRID' class="element-row ui-draggable inputWrap">
                            <a href="javascript:void(0);" class="remove label label-important">
                                <i class="icon-remove icon-white"></i>删除</a>
                            <span class="drag containerDrag label">
                                <i class="icon-move"></i>拖动
                            </span>
                            <span class="style grid-style label label-info">
                                <i class="icon-edit"></i>样式设计
                            </span>
                            <span class="add-col label label-info">
                                <i class="icon-plus"></i>加一列
                            </span>
                            <div class="preview">
                                Grid
                            </div>
                            <div class="view">
                                <div class="gridWrap">
                                    <table class="table table-bordered table-hover table-condensed">
                                        <thead>
                                            <tr>
                                                <th>表头</th>
                                                <th>表头</th>
                                                <th>表头</th>
                                                <th>表头</th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div id="inputElement" data-type='INPUT' class="element-row ui-draggable input">
                            <a href="javascript:void(0);" class="remove label label-important">
                                <i class="icon-remove icon-white"></i>删除
                            </a>
                            <span class="drag inputDrag label">
                                <i class="icon-move"></i>拖动
                            </span>
                            <div class="preview">
                                文本框
                            </div>
                            <div class="view">
                                <div class='input-group'>
                                    <label>文字</label>
                                    <input type="text" class="input-block-level" placeholder="输入文字">
                                </div>
                            </div>
                        </div>
                    </li>


                </ul>
            </div>
            <div class="span9">
                <div id="demo" data-type='CONTAINER' data-uid='root' class='element-row'>

                </div>
            </div>

            <div id="layout"></div>
			
			
            <div id="styleModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="myModalLabel">设置表单的布局</h3>
                </div>
                <div class="modal-body">
                    <form id="styleForm" class="form-horizontal">
                        <div class="control-group">
                            <label class="control-label" for="formCol">列</label>
                            <div class="controls">
                                <input id="formCol" type="text" />
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="formRow">行</label>
                            <div class="controls">
                                <input id="formRow" type="text" />
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
                    <button id='styleSetBtn' class="btn btn-primary">确定</button>
                </div>
            </div>
            <!--GridStyleDesign -->
            <div id="gridStyleModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="gridStyleModalLabel">设置Grid的样式</h3>
                </div>
                <div class="modal-body">
                    <form id="gridStyleForm" class="form-horizontal">
                        <div class="control-group">
                            <label class="control-label" for="formCol">列</label>
                            <div class="controls">
                                <input id="formCol" type="text" />
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="formRow">行</label>
                            <div class="controls">
                                <input id="formRow" type="text" />
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
                    <button id='gridStyleSetBtn' class="btn btn-primary">确定</button>
                </div>
            </div>
            
            <!--input样式设计-->
            <div id="inputStyleModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="inputStyleModalLabel">设置文本框的样式</h3>
                </div>
                <div class="modal-body">
                    <form id="inputStyleForm" class="form-horizontal">
                        <div class="row-fluid">
                            <div class="control-group span6">
                                <label class="control-label" for="inputId">ID</label>
                                <div class="controls">
                                    <input id="inputId" type="text" class="input-block-level" placeholder='ID' />
                                </div>
                            </div>
                            <div class="control-group span6">
                                <label class="control-label" for="inputName">Name</label>
                                <div class="controls">
                                    <input id="inputName" type="text" class="input-block-level" placeholder='Name' />
                                </div>
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="control-group span6">
                                <label class="control-label" for="inputType">类型</label>
                                <div class="controls">
                                    <select id="inputType" class="input-block-level">
                                        <option value='text'>text</option>
                                        <option value='date'>date</option>
                                        <option value='select'>select</option>
                                    </select>
                                </div>
                            </div>
                            <div class="control-group span6">
                                <label class="control-label" for="inputLabel">标签名称</label>
                                <div class="controls">
                                    <input id="inputLabel" type="text" class="input-block-level" placeholder='' />
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
                    <button id='inputStyleSetBtn' class="btn btn-primary">确定</button>
                </div>
            </div>

            <!--未绑定数据的input样式设计-->
            <div id="unbindDataInputStyleModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="unbindDataInputStyleModalLabel">设置文本框的样式</h3>
                </div>
                <div class="modal-body">
                    <form id="unbindDataInputStyleForm" class="form-horizontal">
                        <div class="control-group">
                            <label class="control-label" for="ubBindColumn">绑定字段</label>
                            <div class="controls">
                                <select id="ubBindColumn" class="input-block-level">
                                    <option value='text'>text</option>
                                    <option value='date'>date</option>
                                    <option value='select'>select</option>
                                </select>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="ubInputId">ID</label>
                            <div class="controls">
                                <input id="ubInputId" type="text" class="input-block-level" placeholder='ID' />
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="ubInputName">Name</label>
                            <div class="controls">
                                <input id="ubInputName" type="text" class="input-block-level" placeholder='Name' />
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="ubInputType">类型</label>
                            <div class="controls">
                                <select id="ubInputType" class="input-block-level">
                                    <option value='text'>text</option>
                                    <option value='date'>date</option>
                                    <option value='select'>select</option>
                                </select>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="ubInputLabel">标签名称</label>
                            <div class="controls">
                                <input id="ubInputLabel" type="text" class="input-block-level" placeholder='' />
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
                    <button id='ubInputStyleSetBtn' class="btn btn-primary">确定</button>
                </div>
            </div>
            <!--Grid表头样式设计-->
            <div id="gridHeaderModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="gridHeaderModalLabel" aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="gridHeaderModalLabel">设置表头信息</h3>
                </div>
                <div class="modal-body">
                    <form id="gridHeaderForm" class="form-horizontal">
                        <div class="control-group">
                            <label class="control-label" for="gridHeaderColumn">绑定字段</label>
                            <div class="controls">
                                <select id="gridHeaderColumn" class="input-block-level">
                                    <option value='text'>text</option>
                                    <option value='date'>date</option>
                                    <option value='select'>select</option>
                                </select>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="gridHeaderInputName">表头名称</label>
                            <div class="controls">
                                <input id="gridHeaderInputName" type="text" class="input-block-level" placeholder='表头名称' />
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
                    <button id='gridHeaderSetBtn' class="btn btn-primary">确定</button>
                </div>
            </div>
            
        </div>
    </div>
    
    <a id="next" href="codehelper/jsp/template.jsp" class="navigation-arrow">
		<!--[if !IE]>-->
          <img src="codehelper/images/arrow.svg" alt="" width="28" height="42"> 
        <!--<![endif]-->
        <!--[if lt IE 9]>
          <object src="codehelper/images/arrow-right.svg" classid="image/svg+xml"
                  width="28" height="42" id="rightSVGObject"> 
          </object>
        <![endif]-->
        <!--[if gte IE 9]>
          <object data="codehelper/images/arrow-right.svg" type="image/svg+xml"
                  width="28" height="42" id="rightSVGObject">
          </object>
        <![endif]-->        

    </a>
    
    <script src="codehelper/js/layout.js"></script>
    <script src="codehelper/js/math.uuid.js"></script>
  </body>
</html>
