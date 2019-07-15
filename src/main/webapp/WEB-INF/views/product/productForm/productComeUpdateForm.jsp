<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<div id="dialog-productUpdate-form" style="display: none;">
		<form id="productUpdateForm">
			<table
				class="table table-striped table-bproducted table-hover dataTable no-footer"
				role="grid">
				<input type="hidden" name="id" id="input-Id2" value="" />
				<tr>
					<td><label for="productImgid">图号</label></td>
					<td><input id="input-productImgid2" type="text" 
					    name="productImgid" value=""
						class="text ui-widget-content ui-corner-all"></td>
					<td><label for="productMaterialname">材料名称</label></td>
					<td><input id="input-productMaterialname2" type="text"
						name="productMaterialname" value=""
						class="text ui-widget-content ui-corner-all"></td>
				</tr>
				<tr>
					<td><label for="productMaterialsource">材料来源</label></td>
					<td><input id="input-productMaterialsource2" type="text"
						name="productMaterialsource" value=""
						class="text ui-widget-content ui-corner-all"></td>
					<td><label for="productTargetweight">工艺重量</label></td>
					<td><input id="input-productTargetweight2" type="number" 
					    name="productTargetweight"value="" 
					    class="text ui-widget-content ui-corner-all"></td>
				</tr>
				<tr>
					<td><label for="productRealweight">投料重量</label></td>
					<td><input id="input-productRealweight2" type="number" 
					   name="productRealweight" value=""
						class="text ui-widget-content ui-corner-all"></td>
					<td><label for="productLeftweight">剩余重量</label></td>
					<td><input id="input-productLeftweight2" type="number"
						name="productLeftweight" value=""
						class="text ui-widget-content ui-corner-all"></td>
				</tr>
				<tr>
					<td><label for="productIrontypeweight">锭型</label></td>
					<td><input id="input-productIrontypeweight2" type="number"
					    name="productIrontypeweight"
						value="" class="datepicker text ui-widget-content ui-corner-all"></td>
					<td><label for="productIrontype">锭型类别</label></td>
					<td><input id="input-productIrontype2" type="text"
						name="productIrontype" value=""
						class="datepicker text ui-widget-content ui-corner-all"></td>
				</tr>
				<tr>
					<td><label for="productRemark">备注</label></td>
					<td><input id="input-productRemark2" type="text" name="productRemark"
						value="" class="text ui-widget-content ui-corner-all"></td>
				</tr>
			</table>
		</form>
	</div>