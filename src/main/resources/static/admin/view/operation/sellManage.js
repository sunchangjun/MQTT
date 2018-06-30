(function () {
    DD.createModule({			/*售货机商品管理*/
        name:"msellmanage",
        el:'sellgoods',
        delayInit : false,
        templateUrl : BASEHTML + '/operation/sellManage.html',
        data:{
        	open:false,
        	pop:false,
        	tip_open:false,
        	change_god:false,
			goods_list:'',				/*商品列表*/
			class_list:'',				/*分类*/
			classification1:'',			/*选择分类*/
			goodsName:'',				/*商品名称*/
			goods_change:'',			/*更换商品列表*/
			boxCode:'',					/*售货机编码*/
			com_list:'',				
			pages:1,					/*默认页数*/
			pageCount:'',				/*总页数*/
			total:'',				/*总数据*/
			code:'',				/*删除货道商品的编号*/
			page:1,
			sum_pages:'',
			commodityId:'',			/*商品id*/
			inv:'',
			spage:1,
			spages:'',
			fp:0,
			tips_tit:'',
            icon_tip:'',
		},
        onBeforeFirstRender:function () {
        	var me = this;
            me.module.methodFactory.methods.goods_manage_list.call(me, {		/*初始加载*/
        		pages: me.data.pages,
        		rows: 10
        	});
        },
        methods:{
			see_det:function(){							
				var me =this;
				me.data.open = true;
			},
			close_det:function(){
				var me =this;
				me.data.open = false;
				me.data.boxCode = '';
			},
			pop_open:function(e,data,view){
				var me = this;
				me.data.pop = true;
				me.data.code = view.dataset.id;
			},
			del_class:function(){					/*确定删除*/
				var me =this;
				DD.request({
					url:DATAURL + DATAURL1 + '/commodityRoad/deleteCommodityRoad',
					params: {boxCode:me.data.boxCode,code:me.data.code},
					reqType:'POST',
					type:'json',
					/*设置1秒超时*/
					timeout:5000,
					/*请求成功回调*/
					successFunc:function(r){
						if(r.success==true){
							me.data.tips_tit = '删除成功';
							me.data.icon_tip = 2;
							me.data.tip_open = true;
							setTimeout(function(){ me.data.tip_open = false; },1500);
							me.data.pop = false;
						}else{
							me.data.pop = false;
						}
					},
					/*请求失败回调*/
					errorFunc:function(r){
						console.log(r);
					},
					/*请求超时回调*/
					timeoutFunc:function(r){
						console.log("系统超时");
					}
				});
			},
			close_open:function(){					/*取消删除*/
				var me = this;
				me.data.pop = false;
			},
			sear_god:function(){					/*货道商品搜索*/
				var me = this;
				DD.request({									/*列表*/
					url:DATAURL + DATAURL1 + '/commodity/findCommodity',
					params: {
						commodityName: me.data.goodsName,
						classification:me.data.classification1,
						page: 1,
						rows: 18
					},
					reqType:'GET',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.goods_change = r.result.content;
						me.data.sum_pages = r.result.pageCount;
					},
					/*请求失败回调*/
					errorFunc:function(r){
						console.log(r);
					},
					/*请求超时回调*/
					timeoutFunc:function(r){
						console.log("系统超时");
					}
				});
			},
			change_god:function(e,data,view){					/*更换绑定商品弹窗*/
				var me = this;
				me.data.change_god = true;
				me.data.code = view.dataset.id;
				DD.request({									/*列表*/
					url:DATAURL + DATAURL1 + '/commodity/findCommodity',
					params: {
						page: me.data.page,
						rows: 18
					},
					reqType:'GET',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.goods_change = r.result.content;
						me.data.sum_pages = r.result.pageCount;
					},
					/*请求失败回调*/
					errorFunc:function(r){
						console.log(r);
					},
					/*请求超时回调*/
					timeoutFunc:function(r){
						console.log("系统超时");
					}
				});
				DD.request({								/*获取商品分类*/
					url:DATAURL + DATAURL1 + '/commodityCate/getCommodityCateList',			
					params: {},
					reqType:'GET',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:1000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.class_list = r.result;
					},
					/*请求失败回调*/
					errorFunc:function(r){
						console.log(r);
					},
					/*请求超时回调*/
					timeoutFunc:function(r){
						console.log("系统超时");
					}
				});
			},
			cheak_god:function(e,data,view){			/*选择货道商品*/
				var me =this;
				me.data.commodityId = view.dataset.id;
				data.$set('selected',true);		/*给data添加一个判断字段*/
				me.data.goods_change.forEach(function(r,i){			
					if(r.commodityId!=data.commodityId){
						r.$set('selected',false);
					}
				});
				var inv_ipt = view.children[1].children[1].children[1];
				if('oninput' in inv_ipt){ 
	                inv_ipt.addEventListener("input",function(){me.data.inv = inv_ipt.value;},false); 
	            }else{ 
	                inv_ipt.onpropertychange = function(){me.data.inv = inv_ipt.value;}; 
	            }
			},
			sure_change:function(e,data,view){						/*确定更换*/
				var me = this;
				if(me.data.inv >　5){
					me.data.tips_tit = '库存最大为5';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				} else if(me.data.inv == null){
					me.data.tips_tit = '请填写库存';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				} else {
					DD.request({								/*确定选择货道商品更改*/
						url:DATAURL + DATAURL1 + '/commodityRoad/updateCommodityRoad',		
						params: {
							boxCode:me.data.boxCode,
							commodityId:me.data.commodityId,
							code:me.data.code,
							num:me.data.inv
						},
						reqType:'POST',
						type:'json',
						async:true,
						/*设置1秒超时*/
						timeout:1000,
						/*请求成功回调*/
						successFunc:function(r){
							me.data.tips_tit = '操作成功';
							me.data.icon_tip = 2;
							me.data.tip_open = true;
							setTimeout(function(){ me.data.tip_open = false; },1500);
							me.data.change_god = false;
							me.data.commodityId = '';
							me.data.inv = '';
						},
						/*请求失败回调*/
						errorFunc:function(r){
							console.log(r);
						},
						/*请求超时回调*/
						timeoutFunc:function(r){
							console.log("系统超时");
						}
					});
				}
			},
			Gup_page:function(){						/*货道上一页*/
				var me = this;
				if(0<me.data.page-1){
					me.data.page = me.data.page - 1;
					var r = {};
					DD.request({
						url:DATAURL + DATAURL1 + '/commodity/findCommodity',
						params: {
							commodityName: me.data.goodsName,
							classification:me.data.classification1,
							page: me.data.page,
							rows: 18
						},
						reqType:'GET',
						type:'json',
						async:true,
						/*设置1秒超时*/
						timeout:2000,
						/*请求成功回调*/
						successFunc:function(r){
							me.data.goods_change = r.result.content;
							me.data.sum_pages = r.result.pageCount;
						},
						/*请求失败回调*/
						errorFunc:function(r){
							console.log(r);
						},
						/*请求超时回调*/
						timeoutFunc:function(r){
							console.log("系统超时");
						}
					});
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}
			},
			Gdown_page:function(){						/*货道下一页*/
				var me = this;
				if(me.data.page+1<=me.data.sum_pages){
					me.data.page = me.data.page + 1;
					DD.request({
						url:DATAURL + DATAURL1 + '/commodity/findCommodity',
						params: {
							commodityName: me.data.goodsName,
							classification:me.data.classification1,
							page: me.data.page,
							rows: 18
						},
						reqType:'GET',
						type:'json',
						async:true,
						/*设置1秒超时*/
						timeout:2000,
						/*请求成功回调*/
						successFunc:function(r){
							me.data.goods_change = r.result.content;
							me.data.sum_pages = r.result.pageCount;
						},
						/*请求失败回调*/
						errorFunc:function(r){
							console.log(r);
						},
						/*请求超时回调*/
						timeoutFunc:function(r){
							console.log("系统超时");
						}
					});
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}
			},
			cancle_god:function(){					/*取消更换或绑定商品弹窗*/
				var me = this;
				me.data.change_god=false;
				me.data.commodityId = '';
			},
			search: function(e, data, view) {			/*一级搜索列表*/
				var me = this;
				var params = {
					boxCode: me.data.boxCode,
					pages: me.data.pages,
					rows: 10
				};
        		me.module.methodFactory.methods.goods_manage_list.call(me, params);
			},
			up_page:function(){						/*列表上一页*/
				var me = this;
				if(0<me.data.pages-1){
					me.data.pages = me.data.pages - 1;
						var params = {
						boxCode: me.data.boxCode,
						pages: me.data.pages,
						rows: 10
					};
	        		me.module.methodFactory.methods.goods_manage_list.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}
			},
			down_page:function(){					/*列表下一页*/
				var me = this;
				if(me.data.pages+1<=me.data.pageCount){
					me.data.pages = me.data.pages + 1;
						var params = {
						boxCode: me.data.boxCode,
						pages: me.data.pages,
						rows: 10
					};
	        		me.module.methodFactory.methods.goods_manage_list.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}
				
			},
			goods_manage_list: function(params) {							/*库存列表*/
				var me = this;
	            DD.request({
					url:DATAURL + DATAURL1 + '/box/getBoxPage',
					params: params,
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:5000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.goods_list = r.result.content;
						me.data.pageCount = r.result.pageCount;				/*总页数赋值*/
						me.data.total = r.result.total;
					},
					/*请求失败回调*/
					errorFunc:function(r){
						console.log(r);
					},
					/*请求超时回调*/
					timeoutFunc:function(r){
						console.log("系统超时");
					}
				});
			},
			see_det:function(e,data,view){				/*查看库存*/
				var me = this;
				me.data.open = true;
				me.data.boxCode = data.boxCode;		/*售货机编号*/
				DD.request({
					url:DATAURL + DATAURL1 + '/box/getBoxStockManage',
					params: {boxCode:me.data.boxCode},
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:500000,
					/*请求成功回调*/
					successFunc:function(r){
						if (r.result==''){
							me.data.com_list = '';
							console.log(me.data.com_list)
						}else{
							me.data.spages = r.result.length;
							me.data.com_list = r.result[me.data.fp].jsonArray;
							for(var i = 0;i<r.result[me.data.fp].jsonArray.length;i++){
								r.result[me.data.fp].jsonArray[i].qz = Math.ceil(r.result[me.data.fp].jsonArray[i].code/10);
								r.result[me.data.fp].jsonArray[i].num = r.result[me.data.fp].jsonArray[i].code.slice(1,2);
							}
						}
					},
					/*请求失败回调*/
					errorFunc:function(r){
						console.log(r);
					},
					/*请求超时回调*/
					timeoutFunc:function(r){
						console.log("系统超时");
					}
				});
			},
			down_sto:function(){
				var me = this;
				if(me.data.fp>=me.data.spages-1){
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				} else {
					me.data.fp = me.data.fp+1;
					me.data.spage = me.data.spage+1;
					DD.request({
						url:DATAURL + DATAURL1 + '/box/getBoxStockManage',
						params: {boxCode:me.data.boxCode},
						reqType:'POST',
						type:'json',
						async:true,
						/*设置1秒超时*/
						timeout:500000,
						/*请求成功回调*/
						successFunc:function(r){
							me.data.com_list = r.result[me.data.fp].jsonArray;
							for(var i = 0;i<r.result[me.data.fp].jsonArray.length;i++){
								r.result[me.data.fp].jsonArray[i].qz = Math.ceil(r.result[me.data.fp].jsonArray[i].code/10);
								r.result[me.data.fp].jsonArray[i].num = r.result[me.data.fp].jsonArray[i].code.slice(1,2);
							}
						},
						/*请求失败回调*/
						errorFunc:function(r){
							console.log(r);
						},
						/*请求超时回调*/
						timeoutFunc:function(r){
							console.log("系统超时");
						}
					});
				}
			},
			up_sto:function(){
				var me = this;
				if(me.data.fp<=0){
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				} else {
					me.data.fp = me.data.fp-1;
					me.data.spage = me.data.spage-1;
					DD.request({
						url:DATAURL + DATAURL1 + '/box/getBoxStockManage',
						params: {boxCode:me.data.boxCode},
						reqType:'POST',
						type:'json',
						async:true,
						/*设置1秒超时*/
						timeout:500000,
						/*请求成功回调*/
						successFunc:function(r){
							if( r.result == '' ){
								me.data.com_list = '';
								console.log(me.data.com_list)
							} else {
								me.data.com_list = r.result[me.data.fp].jsonArray;
								for(var i = 0;i<r.result[me.data.fp].jsonArray.length;i++){
									r.result[me.data.fp].jsonArray[i].qz = Math.ceil(r.result[me.data.fp].jsonArray[i].code/10);
									r.result[me.data.fp].jsonArray[i].num = r.result[me.data.fp].jsonArray[i].code.slice(1,2);
								}
							}
						},
						/*请求失败回调*/
						errorFunc:function(r){
							console.log(r);
						},
						/*请求超时回调*/
						timeoutFunc:function(r){
							console.log("系统超时");
						}
					});
				}
			},
        }
   })
})()