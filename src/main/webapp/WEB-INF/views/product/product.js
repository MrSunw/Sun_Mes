$(function(){
 
	
	//批量启动
	var ids="";
	$(".batchStart-btn").click(function(){
		//拿到当前被选中的input-checkbox
		var checks=$(".batchStart-check:checked");
		if(checks.length!=null&&checks.length>0){
			//拿到被选中的订单号
			//mesproduct-id
			$.each(checks,function(i,check){
//				console.log($(check).closest("tr").data("id")); h5
//				console.log($(check).closest("tr").attr("data-id"));
				var id=$(check).closest("tr").attr("data-id");
				ids+=id+"&";
			});
		//	console.log(ids)   368&367&
			//console.log(ids.substr(0,ids.length-1));  368&367
			//拼装ids
			ids=ids.substr(0,ids.length-1);
			//发送ajax请求
			$.ajax({
				url : "/product/productBatchStart.json",
				data : {//左面是数据名称-键，右面是值
					ids:ids
				},
				type : 'POST',
				success : function(result) {//jsondata  jsondata.getData=pageResult  pageResult.getData=list
					loadproductList();
				}
			});
			ids="";//111&122&111&122
		}
	});
	
	//批量选择
	$(".batchStart-th").click(function(){
		var checks=$(".batchStart-check");
		$.each(checks,function(i,input){
		input.checked=input.checked==true?false:true;	
		});
	});
	
	
	//////////////////////////////////////////////
	//页面开始加载
	//执行分页逻辑
	//定义一些全局变量
	var productMap = {};//准备一个map格式的仓库，等待存储从后台返回过来的数据
	var optionStr;//选项参数
	var pageSize;//页码条数
	var pageNo;//当前页
	var url;//查询url
	var keyword;//关键字
	var search_source;//材料来源


	//加载模板内容进入html
	//01从模板中获取页面布局内容
	//productListTemplate就是mustache模板的id值
	var productListTemplate = $("#productListTemplate").html();
	//02使用mustache模板加载这串内容
	//只是把准备好的页面模板拿出来，放在解析引擎中，准备让引擎往里面填充数据（渲染视图）
	Mustache.parse(productListTemplate);
	//渲染分页列表
	//调用分页函数
	loadproductList();
	//点击刷新的时候也需要调用分页函数
	$(".research").click(function(e) {
		e.preventDefault();
		$("#productPage .pageNo").val(1);
		loadproductList();
	});
	//定义调用分页函数，一定是当前的查询条件下（keyword，search_source。。）的分页
	function loadproductList(urlnew) {
		//获取页面当前需要查询的还留在页码上的信息
		//在当前页中找到需要调用的页码条数
		pageSize = $("#pageSize").val();
		//当前页
		pageNo = $("#productPage .pageNo").val() || 1;
		if (urlnew) {
			url = urlnew;
		} else {
			url = "/product/product.json";
		}
		keyword = $("#keyword").val();
		search_source = $("#search_source").val();
		//发送请求
		$.ajax({
			url : url,
			data : {//左面是数据名称-键，右面是值
				pageNo : pageNo,
				pageSize : pageSize,
				keyword : keyword,
				fromTime : fromTime,
				toTime : toTime,
				search_source : search_source,
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
			url = "/product/product.json";
			keyword = $("#keyword").val();
			fromTime = $("#fromTime").val();
			toTime = $("#toTime").val();
			search_source = $("#search_source").val();
			//如果查询到数据库中有符合条件的product列表
			if(result.data.total>0){
			//为订单赋值--在对productlisttemplate模板进行数据填充--视图渲染
			//	Mustache.render({"name":"李四","gender":"男"});
			//Mustache.render(list=new ArrayList<String>(){"a01","a02"},{"name":"list[i].name","gender":list[i].gender});	
			
			var rendered=Mustache.render(
				productBatchListTemplate,//<script id="productListTemplate" type="x-tmpl-mustache">
			{
			 "productList" : result.data.data,//{{#productList}}--List-(result.data.data-list<Mesproduct>)	
			 "come_date" : function() {
					return function(text, render) {
						return new Date(
								this.productCometime)
								.Format("yyyy-MM-dd");
					}
				},
				"commit_date" : function() {
					return function(text, render) {
						return new Date(
								this.productCommittime)
								.Format("yyyy-MM-dd");
					}
				},
				"showStatus" : function() {
					return this.productStatus == 1 ? '有效'
							: (this.productStatus == 0 ? '无效'
									: '删除');
				},
				"bold" : function() {
					return function(text, render) {
						var status = render(text);
						if (status == '有效') {
							return "<span class='label label-sm label-success'>有效</span>";
						} else if (status == '无效') {
							return "<span class='label label-sm label-warning'>无效</span>";
						} else {
							return "<span class='label'>删除</span>";
						}
					}
				}
			 
			});
			$.each(result.data.data, function(i, product) {//java-增强for
				product.productCometime = new Date(product.productCometime)
						.Format("yyyy-MM-dd");
				product.productCommittime = new Date(product.productCommittime)
						.Format("yyyy-MM-dd");
				productMap[product.id] = product;//result.data.data等同于List<mesproduct>
				//product.id-product  map key-value
			});
			$('#productList').html(rendered);
			}else{
				$('#productList').html('');
			}
			 bindproductClick();//更新操作 修改
			var pageSize = $("#pageSize").val();
			var pageNo = $("#productPage .pageNo").val() || 1;
			
			//渲染页码
			renderPage(//page.jsp  function renderPage(url, total, pageNo, pageSize, currentSize, idElement,
				//	callback)
					url,
					result.data.total,
					pageNo,
					pageSize,
					result.data.total > 0 ? result.data.data.length : 0,
					"productPage", 
					loadproductList);
			
		}else{
			showMessage("获取订单列表",result.msg,false);
		}
	}
	//////////////////////////////////////////////
	$(".product-add").click(
			function() {
				//弹出框
				$("#dialog-product-form").dialog(
						{
							model : true,//背景不可点击
							title : "新建订单",//模态框标题
							open : function(event, ui) {
								$(".ui-dialog").css("width", "700px");//增加模态框的宽高
								$(".ui-dialog-titlebar-close",
										$(this).parent()).hide();//关闭默认叉叉
								optionStr = "";
								$("#productBatchForm")[0].reset();//清空模态框--jquery 将指定对象封装成了dom对象
							},
							buttons : {
								"添加" : function(e) {
									//阻止一下默认事件
									e.preventDefault();
									//发送新增product的数据和接收添加后的回收信息
									updateproduct(true, function(data) {
										//增加成功了
										//提示增加用户成功信息
										showMessage("新增订单", data.msg,
												true);
										$("#dialog-product-form").dialog(
												"close");
			                         	loadproductList();//根据参数查看
									}, function(data) {
										//增加失败了
										//						alert("添加失败了");
										//信息显示
										showMessage("新增订单", data.msg,
												false);
										//						$("#dialog-product-form").dialog("close");
									});
								},
								"取消" : function() {
									$("#dialog-product-form").dialog(
											"close");
								}
							}
						});
			});		
	
         //////////////////////////////////////////////
	    //修改 更新操作
	    function bindproductClick(){
	    	 $(".product-edit").click(function(e) {
					//阻止默认事件
		            e.preventDefault();
					//阻止事件传播
		            e.stopPropagation();
					//获取productid
		            var productId = $(this).attr("data-id");
					//弹出product的修改弹窗 
		            $("#dialog-productUpdate-form").dialog({
		                model: true,
		                title: "编辑订单",
		                open: function(event, ui) {
		             	    $(".ui-dialog").css("width","600px");
		                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
		                  	//将form表单中的数据清空，使用jquery转dom对象
		                    $("#productUpdateForm")[0].reset();
		                  	//拿到map中以键值对，id-product对象结构的对象,用来向form表单中传递数据
		                    var targetproduct = productMap[productId];
		                  	//如果取出这个对象
		                    if (targetproduct) {
								/////////////////////////////////////////////////////////////////
								$("#input-Id2").val(targetproduct.id);
								$("#input-productId2").val(targetproduct.productId);
								$("#input-productClientname2").val(targetproduct.productClientname);
								$("#input-productProductname2").val(targetproduct.productProductname);
								$("#input-productContractid2").val(targetproduct.productContractid);
								$("#input-productImgid2").val(targetproduct.productImgid);
								$("#input-productMaterialname2").val(targetproduct.productMaterialname);
								$("#input-productCometime2").val(targetproduct.productCometime);
								$("#input-productCommittime2").val(targetproduct.productCommittime);
								$("#input-productInventorystatus2").val(targetproduct.productInventorystatus);
								$("#input-productSalestatus2").val(targetproduct.productSalestatus);
								$("#input-productMaterialsource2").val(targetproduct.productMaterialsource);
								$("#input-productHurrystatus2").val(targetproduct.productHurrystatus);
								$("#input-productStatus2").val(targetproduct.productStatus);
								$("#input-productRemark2").val(targetproduct.productRemark);
								/////////////////////////////////////////////////////////////////
		                    }
		                },
		                buttons : {
		                    "更新": function(e) {
		                        e.preventDefault();
		                        updateproduct(false, function (data) {
		                            $("#dialog-productUpdate-form").dialog("close");
		            				$("#productPage .pageNo").val(1);
		                            loadproductList();
		                        }, function (data) {
		                            showMessage("更新订单", data.msg, false);
		                        })
		                    },
		                    "取消": function (data) {
		                        $("#dialog-productUpdate-form").dialog("close");
		                    }
		                }
		            });
		        });
	    };
	
	
	 
	////////////////////////////////////////////////////
	  //新增和修改product的通用方法-dml
		//isCreate是否是新增订单(true,false)，如果不是，执行修改
		//successCallbak function(data)  failCallbak function(data)
	function updateproduct(isCreate, successCallbak, failCallbak) {
		$.ajax({
			url :  isCreate ? "/product/insert.json"
					: "/product/update.json",
					
			data : isCreate ? $("#productBatchForm").serializeArray() : $(
			"#productUpdateForm").serializeArray(),
			type : 'POST',
			success : function(result) {
				//数据执行成功返回的消息
				if (result.ret) {
                	loadproductList(); // 带参数回调
					//带参数回调
					if (successCallbak) {
						successCallbak(result);
					}
				} else {
					//执行失败后返回的内容
					if (failCallbak) {
						failCallbak(result);
					}
				}
			}
		});
	}
	//////////////////////////////////////////////////////
	//日期显示
	$('.datepicker').datepicker({
		dateFormat : 'yy-mm-dd',
		showOtherMonths : true,
		selectOtherMonths : false
	});
	
});