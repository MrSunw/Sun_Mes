<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script id="productBoundListTemplate" type="x-tmpl-mustache">
{{#producBoundtLists}}
 <tr role="row" class="product-name odd" data-id="{{id}}"><!--even -->
	<td>{{productId}}</td>
	<td>{{productMaterialname}}</td>
	<td>{{productMaterialsource}}</td>
	<td>{{productTargetweight}}</td>
	<td>
		<div class="hidden-sm hidden-xs action-buttons">
			 <a class="btn btn-danger bind-edit" href="#" data-id="{{id}}"
                  data-weight="{{productTargetweight}}">
				 解绑
			</a>
		</div>
	</td>
</tr>
{{/producBoundtLists}}
</script>