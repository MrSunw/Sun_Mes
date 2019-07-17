$(function(){
	
	//////////////////////////////////////////////
	//页面开始加载
	//执行分页逻辑
	//定义一些全局变量
	var productMap = {};//准备一个map格式的仓库，等待存储从后台返回过来的数据
	var optionStr;//选项参数
	var url;//查询url
	var search_status;//查询状态
	var search_source;//材料来源
	var pageSize;//页码条数
	var pageNo;//当前页

	//加载模板内容进入html
	//01从模板中获取页面布局内容
	//productListTemplate就是mustache模板的id值
	var productListTemplate = $("#productListTemplate").html();
	var productBoundListTemplate=$("#productBoundListTemplate").html();
	//02使用mustache模板加载这串内容
	//只是把准备好的页面模板拿出来，放在解析引擎中，准备让引擎往里面填充数据（渲染视图）
	Mustache.parse(productListTemplate);
	Mustache.parse(productBoundListTemplate);
	//渲染分页列表
	//调用分页函数
	loadproductList();
	loadproductBoundList();
	//点击刷新的时候也需要调用分页函数
	$(".research").click(function(e) {
		e.preventDefault();
		loadproductList();
	});
	//定义调用分页函数，一定是当前的查询条件下（keyword，search_status。。）的分页
	function loadproductList(urlnew) {
		pageNo=1;
		pageSize=100000;
		if (urlnew) {
			url = urlnew;
		} else {
			url = "/product/productBind.json";
		}
		keyword = $("#keyword").val();
		search_status = $("#search_status").val();
		search_source=$("#search_source").val();
		//发送请求
		$.ajax({
			url : url,
			data : {//左面是数据名称-键，右面是值
				pageNo : pageNo,
				pageSize : pageSize,
				keyword : keyword,
				search_status : search_status,
				search_source:search_source
			},
			type : 'POST',
			success : function(result) {//jsondata  jsondata.getData=pageResult  pageResult.getData=list
				//渲染product列表和页面--列表+分页一起填充数据显示条目
				renderproductListAndPage(result, url);
			}
		});
	}
	
	//渲染所有的mustache模板页面
	//result中的存储数据，就是一个list<Mesproduct>集合,是由service访问数据库后返回给controller的数据模型
	function renderproductListAndPage(result, url) {
		if(result.ret){
		  //再次初始化查询条件
			url = "/product/productBind.json";
			keyword = $("#keyword").val();
			search_status = $("#search_status").val();
			search_source=$("#search_source").val();
			//如果查询到数据库中有符合条件的product列表
			
			//为订单赋值--在对productlisttemplate模板进行数据填充--视图渲染
			//	Mustache.render({"name":"李四","gender":"男"});
			//Mustache.render(list=new ArrayList<String>(){"a01","a02"},{"name":"list[i].name","gender":list[i].gender});	
			
			var rendered=Mustache.render(
				productListTemplate,//<script id="productListTemplate" type="x-tmpl-mustache">
			{
			 "productList" : result.data.data,//{{#productList}}--List-(result.data.data-list<Mesproduct>)	
			 
			});
			$.each(result.data.data, function(i, product) {//java-增强for
				productMap[product.id] = product;//result.data.data等同于List<mesproduct>
				//product.id-product  map key-value
			});
			$('#productList').html(rendered);
			}else{
				$('#productList').html('');
			}
			 bindproductClick();//绑定操作
			
	}
         //////////////////////////////////////////////
	    //绑定操作
	    function bindproductClick(){
	    	
	    	
	    	 $(".product-bind").click(function(e) {
	    		
					//阻止默认事件
		            e.preventDefault();
					//阻止事件传播
		            e.stopPropagation();
					//获取productid
		            var parentId=$(".bind-id").val();
		            var parentProductId=$("#keyword").val();
		            var productLeftbackweight=$(".leftback-weight").val();
		            var productchildId = $(this).attr("data-id");
		            var productChildTargetweight = $(this).attr("data-weight");
		            var temp= productLeftbackweight-productChildTargetweight;
				    if(temp>=0){
				    	$.ajax({
				    		url:"/product/productBindChild.json",
				    		data:{
				    			parentId:parentId,
				    			parentProductId:parentProductId,
				    			productLeftbackweight:productLeftbackweight,
				    			productchildId:productchildId,
				    			productChildTargetweight:productChildTargetweight
				    		},
				    		type:'POST',
				    		success:function(result){
				    			loadproductList();
				    			window.location.reload();
				    		}
				    	});
				    }else{
				    	alert("绑定钢材对象剩余重量布不够切割");
				    }
		        });
	    };
	
	//////////////////////////////////////////////////////
	   
	    function loadproductBoundList(urlnew) {
			pageNo=1;
			pageSize=100000;
			if (urlnew) {
				url = urlnew;
			} else {
				url = "/product/productBind.json";
			}
			keyword = $("#keyword").val();
			search_status = $("#search_status").val();
			search_source=$("#search_source").val();
			//发送请求
			$.ajax({
				url : url,
				data : {//左面是数据名称-键，右面是值
					pageNo : pageNo,
					pageSize : pageSize,
					keyword:keyword,
					search_status : search_status,
					search_source:search_source
				},
				type : 'POST',
				success : function(result) {//jsondata  jsondata.getData=pageResult  pageResult.getData=list
					//渲染product列表和页面--列表+分页一起填充数据显示条目
					renderproductboundListAndPage(result, url);
				}
			});
		}
		
		//渲染所有的mustache模板页面
		//result中的存储数据，就是一个list<Mesproduct>集合,是由service访问数据库后返回给controller的数据模型
		function renderproductboundListAndPage(result, url) {
			if(result.ret){
			  //再次初始化查询条件
				url = "/product/productBind.json";
				keyword = $("#keyword").val();
				search_status = $("#search_status").val();
				search_source=$("#search_source").val();
				//如果查询到数据库中有符合条件的product列表
				
				//为订单赋值--在对productlisttemplate模板进行数据填充--视图渲染
				//	Mustache.render({"name":"李四","gender":"男"});
				//Mustache.render(list=new ArrayList<String>(){"a01","a02"},{"name":"list[i].name","gender":list[i].gender});	
				
				var rendered=Mustache.render(
					productBoundListTemplate,//<script id="productListTemplate" type="x-tmpl-mustache">
				{
				 "productBoundList" : result.data.data,//{{#productList}}--List-(result.data.data-list<Mesproduct>)	
				 
				});
				$.each(result.data.data, function(i, product) {//java-增强for
					productMap[product.id] = product;//result.data.data等同于List<mesproduct>
					//product.id-product  map key-value
				});
				$('#productBoundList').html(rendered);
				}else{
					$('#productBoundList').html('');
				}
				 bindproductClick();//绑定操作
				
		}
	         //////////////////////////////////////////////
		    //绑定操作
		    function bindproductClick(){
		    	
		    	
		    	 $(".product-bind").click(function(e) {
		    		
						//阻止默认事件
			            e.preventDefault();
						//阻止事件传播
			            e.stopPropagation();
						//获取productid
			            var parentId=$(".bind-id").val();
			            var parentProductId=$("#keyword").val();
			            var productLeftbackweight=$(".leftback-weight").val();
			            var productchildId = $(this).attr("data-id");
			            var productChildTargetweight = $(this).attr("data-weight");
			            var temp= productLeftbackweight-productChildTargetweight;
					    if(temp>=0){
					    	$.ajax({
					    		url:"/product/productBindChild.json",
					    		data:{
					    			parentId:parentId,
					    			parentProductId:parentProductId,
					    			productLeftbackweight:productLeftbackweight,
					    			productchildId:productchildId,
					    			productChildTargetweight:productChildTargetweight
					    		},
					    		type:'POST',
					    		success:function(result){
					    			loadproductList();
					    			window.location.reload();
					    		}
					    	});
					    }else{
					    	alert("绑定钢材对象剩余重量布不够切割");
					    }
			        });
		    };
	
});