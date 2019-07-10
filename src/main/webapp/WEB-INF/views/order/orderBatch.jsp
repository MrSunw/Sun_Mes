<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单管理</title>
    <!-- jsp 动态导入 -->
    <jsp:include page="/common/backend_common.jsp"/>
    <jsp:include page="/common/page.jsp"/>
 <%@ include file="/template/order/orderBatchTemplate.jsp" %>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>

<div class="page-header">
    <h1>
        订单操作
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            创建于查询
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-12">
        <div class="col-xs-12">
            <div class="table-header">
                订单列表&nbsp;&nbsp;
                <a class="green" href="#">
                    <i class="ace-icon fa fa-plus-circle orange bigger-130 order-add"></i>
                </a>
            </div>
            <div>
                <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                    <div class="row">
                        <div class="col-xs-6">
                            <div class="dataTables_length" id="dynamic-table_length"><label>
                                展示
                                <select id="pageSize" name="dynamic-table_length" aria-controls="dynamic-table" class="form-control input-sm">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select> 条记录 </label>
                            </div>
                        </div>
                    </div>
                    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
                           aria-describedby="dynamic-table_info" style="font-size:14px">
                        <thead>
                        <tr role="row">
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                产品自编号
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                产品名称
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                图号
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                材料名称
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                材料来源
                            </th>
                             <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                来料预期
                            </th>
                          <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                合同交期
                            </th>
                           <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                状态
                            </th>
                             <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                操作
                            </th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                        </tr>
                        </thead>
                        <tbody id="orderList"></tbody>
                    </table>
                    <div class="row" id="orderPage">
                      <div class="col-xs-6">
    <div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">
        总共 {{total}} 条中的 {{from}} ~ {{to}}条 当前第{{pageNo}}页&nbsp;&nbsp;&nbsp;共 {{maxPageNo}}页
    </div>
</div>
<div class="col-xs-6">
    <div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate">
        <ul class="pagination">
            <li class="paginate_button previous {{^firstUrl}}disabled{{/firstUrl}}" aria-controls="dynamic-table" tabindex="0">
                <a href="#" data-target="1" data-url="{{firstUrl}}" class="page-action">首页</a>
            </li>
            <li class="paginate_button {{^beforeUrl}}disabled{{/beforeUrl}}" aria-controls="dynamic-table" tabindex="0">
                <a href="#" name="前一页" data-target="{{beforePageNo}}" data-url="{{beforeUrl}}" class="page-action">前一页</a>
            </li>
            <li class="paginate_button active" aria-controls="dynamic-table" tabindex="0">
                <a href="#" data-id="{{pageNo}}" >第{{pageNo}}页</a>
                <input type="hidden" class="pageNo" value="{{pageNo}}" />
            </li>
            <li class="paginate_button {{^nextUrl}}disabled{{/nextUrl}}" aria-controls="dynamic-table" tabindex="0">
                <a href="#" name="后一页" data-target="{{nextPageNo}}" data-url="{{nextUrl}}" class="page-action">后一页</a>
            </li>
            <li class="paginate_button next {{^lastUrl}}disabled{{/lastUrl}}" aria-controls="dynamic-table" tabindex="0">
                <a href="#" data-target="{{maxPageNo}}" data-url="{{lastUrl}}" class="page-action">尾页</a>
            </li>
            <li class="paginate_button {{^skipUrl}}disabled{{/skipUrl}}" aria-controls="dynamic-table" tabindex="0">
                <input  class="skiptxt" style="width:50px;float:left;background:lightgray;border:1px solid orange;" type="number" value="{{skipNo}}"/>
				<a href="#" data-target="{{pageNo}}" data-url="{{skipUrl}}" class="page-action skip">跳转</a>
            </li>
        </ul>
    </div>
</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="orderBatchForm.jsp" %>
<script type="text/javascript" src="orderBatch.js"></script>
</body>
</html>
