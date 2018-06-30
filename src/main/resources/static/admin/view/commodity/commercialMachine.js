
(function () {
    DD.createModule({	/*商品信息*/
        name:"mCommercialMachine",
        el:'#shop',
        delayInit : false,
        templateUrl : BASEHTML + '/commodity/commercialMachine.html',
        data:{
        	open:false,		
        	tip_open:false,
        	add_open:false,
        	edi_open:false,
        	img_open:false,
        	goodsName:'',				/*商品名称*/
        	classification:'',			/*商品分类*/
        	classification1:'',			/*选择分类后的值*/
        	state:'',					/*选择状态后的值*/
        	select1_numes:[{id:0,name:'启用'},{id:1,name:'禁用'}],
			goods_list:'',				/*商品列表*/
			pages:1,					/*默认页数*/
			sum_pages:'',				/*总页数*/
			sum_data:'',				/*总数据*/
			imageResources:'',
			img_up:false,
			classification2:'',			/*分类*/
			enabled:'',				/*状态*/
			commodityName:'',		
			price:0,
			dianBi:0,			
			biPrice:0,				
			specification:'',				/*规格*/
			content:'',					/*净含量*/
			commodityDesc:'',			/*描述*/
			stocks:'',					/*库存*/
			imageResourceId:'',			/*图片id*/
			bocCate:'',
			tem:'',
			cold:'',
			hot:'',
			commodityId :'',            /*商品id*/
			icon_tip:'',
			tips_tit:'',
			oper_tit:''
		},
        onBeforeFirstRender:function () {
        	var me = this;
        	me.module.methodFactory.methods.loadData.call(me, {
        		page: me.data.pages,
        		rows: 10
        	});
        	DD.request({								/*获取商品分类下拉*/
				url:DATAURL + DATAURL1 + '/commodityCate/getCommodityCateMap',			
				params: {},
				reqType:'GET',
				type:'json',
				async:true,
				/*设置1秒超时*/
				timeout:1000,
				/*请求成功回调*/
				successFunc:function(r){
					me.data.classification = r.result;
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
       
        methods:{
			add_goods:function(){
				var me = this;
				me.data.open = true;
				me.data.add_open = true;
				me.data.oper_tit = '添加';
			},
			see_img:function(e,data,view){
				view.children[0].style.display='block';
				setTimeout(function(){view.children[0].style.display='none';},3000)
			},
			close_see:function(e,data,view){
				view.children[0].style.display='none';
			},
			close_goods:function(){
				var me = this;
				me.data.open = false;
				me.data.add_open = false;
				me.data.edi_open =false;
				me.data.commodityId = '';
				me.data.classification2 ='';
				me.data.commodityName = '';
				me.data.commodityDesc = '';
				me.data.enabled = '';
				me.data.specification = '';
				me.data.content = '';
				me.data.price = 0;
				me.data.dianBi = 0;
				me.data.biPrice = 0;
				me.data.imageResourceId = '';
				me.data.stocks = '';
				me.data.bocCate = '';
				me.data.imageResources = '';
				me.data.img_up=false;
				me.data.open = false;
				me.data.add_open = false;
				me.data.tem = '';
				me.data.cold = '';
				me.data.hot = '';
				me.data.oper_tit = '';
				document.getElementById("tem").checked=false;
				document.getElementById("cold").checked=false;
				document.getElementById("hot").checked=false;
			},
			edi_tc:function(e,data,view){
				var me = this;
				me.data.open = true;
				me.data.edi_open = true;
				me.data.img_up =true;
				me.data.commodityId = data.commodityId;
				me.data.commodityName = data.commodityName;
				me.data.classification2 = data.commodityId;
				me.data.enabled = data.enabled;
				me.data.price = data.price;
				me.data.dianBi = data.dianBi;
				me.data.biPrice = data.biPrice;
				me.data.specification = data.specification;
				me.data.content = data.content;
				me.data.stocks = data.stocks;
				me.data.commodityDesc = data.commodityDesc;
				me.data.imageResources = data.imageResource.url;
				me.data.imageResourceId = data.imageResource.imageResourceId;
				me.data.oper_tit = '编辑';
			},
			edi_gods:function(){			/*保存商品编辑*/
				var me = this;
				var sumprice = JSON.parse(this.data.dianBi) + JSON.parse(this.data.biPrice);
				if(me.data.tem=='' && me.data.cold=='' && me.data.hot==''){
					me.data.tips_tit = '环境至少选一种';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}else{
					me.data.bocCate = me.data.tem + ',' + me.data.cold + ',' + me.data.hot;
					if(me.data.price!=sumprice){
						me.data.tips_tit = '价格与点币价不匹配';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.classification2 == ''){
						me.data.tips_tit = '请选择分类';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.commodityName == ''){
						me.data.tips_tit = '请填写商品名称';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.commodityDesc == ''){
						me.data.tips_tit = '请填写商品描述';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.enabled == ''){
						me.data.tips_tit = '请选择商品状态';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.specification == ''){
						me.data.tips_tit = '请填写商品规格';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.content == ''){
						me.data.tips_tit = '请填写商品净含量';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.imageResourceId == ''){
						me.data.tips_tit = '请上传图片';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.stocks == ''){
						me.data.tips_tit = '请填写库存';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else{
						DD.request({
							url:DATAURL + DATAURL1 + '/commodity/createOrUpdateCommodity',
							params: {
								commodityId:me.data.commodityId,
								commodityCateId:me.data.classification2,  /*分类*/
								commodityName:me.data.commodityName,	/*名称*/
								commodityDesc:me.data.commodityDesc,	/*描述*/
								enabled:me.data.enabled,				/*状态*/
								specification:me.data.specification,	/*规格*/
								content:me.data.content,			/*净含量*/
								price:me.data.price,				/*价格*/
								dianBi:me.data.dianBi,				/*点币*/
								biPrice:me.data.biPrice,			/*点币价*/
								imageResourceId:me.data.imageResourceId,	/*图片id*/
								stocks:me.data.stocks,					/*库存*/
								bocCate:me.data.bocCate,
							},
							reqType:'POST',
							type:'json',
							async:true,
							/*设置1秒超时*/
							timeout:5000,
							/*请求成功回调*/
							successFunc:function(r){
								if(r.success==true){
									me.data.tips_tit = '修改成功';
									me.data.oper_tit = '';
									me.data.icon_tip = 1;
									me.data.tip_open = true;
									setTimeout(function(){ me.data.tip_open = false; },1500);
									me.data.commodityId = '';
									me.data.classification2 ='';
									me.data.commodityName = '';
									me.data.commodityDesc = '';
									me.data.enabled = '';
									me.data.specification = '';
									me.data.content = '';
									me.data.price = 0;
									me.data.dianBi = 0;
									me.data.biPrice = 0;
									me.data.imageResourceId = '';
									me.data.stocks = '';
									me.data.bocCate = '';
									me.data.imageResources = '';
									me.data.img_up = '';
									me.data.open = false;
									me.data.edi_open = false;
									me.data.tem = '';
									me.data.cold = '';
									me.data.hot = '';
									document.getElementById("tem").checked=false;
									document.getElementById("cold").checked=false;
									document.getElementById("hot").checked=false;
									me.module.methodFactory.methods.loadData.call(me, {
						        		page: me.data.pages,
						        		rows: 10
						        	});
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
				}
			},
			search: function(e, data, view) {			/*搜索*/
				var me = this;
				var params = {
					commodityName: me.data.goodsName,
					isEnabled:me.data.state,
					classification:me.data.classification1,
					page: 1,
					rows: 10
				};
        		me.module.methodFactory.methods.loadData.call(me, params);
			},
			up_page:function(){						/*列表上一页*/
				var me = this;
				if(0<me.data.pages-1){
					me.data.pages = me.data.pages - 1;
					var params = {
						commodityName: me.data.goodsName,
						isEnabled:me.data.state,
						classification:me.data.classification1,
						page: me.data.pages,
						rows: 10
					};
	        		me.module.methodFactory.methods.loadData.call(me, params);
				}else{
					me.data.tip_open = true;
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}
			},
			down_page:function(){					/*列表下一页*/
				var me = this;
				if(me.data.pages+1<=me.data.sum_pages){
					me.data.pages = me.data.pages + 1;
					var params = {
						commodityName: me.data.goodsName,
						isEnabled:me.data.state,
						classification:me.data.classification1,
						page: me.data.pages,
						rows: 10
					};
	        		me.module.methodFactory.methods.loadData.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}
			},
			UploadImg:function(e, data, view){
				if(view.files[0]){
					var me=this;
					DD.request({
						url:DATAURL + DATAURL1 + '/imageResource/uploadImageResource',
						reqType:'POST',
						params:{
							mult:view.files[0]
						},
						type:'json',
						successFunc:function(r){
							me.data.img_up=true;
							me.data.imageResources=r.result.url;
							me.data.imageResourceId=r.result.imageResourceId;
						},
						errorFunc:function(r){
							console.log(r);
						},
					});
				}
			},
			add_gods:function(){					/*确定添加商品*/
				var me = this;
				var sumprice = JSON.parse(this.data.dianBi) + JSON.parse(this.data.biPrice);
				if(me.data.tem=='' && me.data.cold=='' && me.data.hot==''){
					me.data.tips_tit = '环境至少选一种';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}else{
					me.data.bocCate = me.data.tem + ',' + me.data.cold + ',' + me.data.hot;
					if(me.data.price!=sumprice){
						me.data.tips_tit = '价格不匹配';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.classification2 == ''){
						me.data.tips_tit = '请选择分类';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.commodityName == ''){
						me.data.tips_tit = '请填写商品名称';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.commodityDesc == ''){
						me.data.tips_tit = '请填写商品描述';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.enabled == ''){
						me.data.tips_tit = '请选择商品状态';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.specification == ''){
						me.data.tips_tit = '请填写商品规格';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.content == ''){
						me.data.tips_tit = '请填写商品净含量';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.imageResourceId == ''){
						me.data.tips_tit = '请上传图片';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else if(me.data.stocks == ''){
						me.data.tips_tit = '请填写库存';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					}else{
						DD.request({
							url:DATAURL + DATAURL1 + '/commodity/createOrUpdateCommodity',
							params: {
								commodityCateId:me.data.classification2,  /*分类*/
								commodityName:me.data.commodityName,	/*名称*/
								commodityDesc:me.data.commodityDesc,	/*描述*/
								enabled:me.data.enabled,				/*状态*/
								specification:me.data.specification,	/*规格*/
								content:me.data.content,			/*净含量*/
								price:me.data.price,				/*价格*/
								dianBi:me.data.dianBi,				/*点币*/
								biPrice:me.data.biPrice,			/*点币价*/
								imageResourceId:me.data.imageResourceId,	/*图片id*/
								stocks:me.data.stocks,					/*库存*/
								bocCate:me.data.bocCate
							},
							reqType:'POST',
							type:'json',
							async:true,
							/*设置1秒超时*/
							timeout:5000,
							/*请求成功回调*/
							successFunc:function(r){
								if(r.success==true){
									me.data.tips_tit = '添加成功';
									me.data.oper_tit = '';
									me.data.icon_tip = 2;
									me.data.tip_open = true;
									setTimeout(function(){ me.data.tip_open = false; },1500);
									me.data.classification2 ='';
									me.data.commodityName = '';
									me.data.commodityDesc = '';
									me.data.enabled = '';
									me.data.specification = '';
									me.data.content = '';
									me.data.price = 0;
									me.data.dianBi = 0;
									me.data.biPrice = 0;
									me.data.imageResourceId = '';
									me.data.stocks = '';
									me.data.bocCate = '';
									me.data.imageResources = '';
									me.data.img_up =false;
									me.data.open = false;
									me.data.add_open = false;
									me.data.tem = '';
									me.data.cold = '';
									me.data.hot = '';
									document.getElementById("tem").checked=false;
									document.getElementById("cold").checked=false;
									document.getElementById("hot").checked=false;
									me.module.methodFactory.methods.loadData.call(me, {
						        		page: me.data.pages,
						        		rows: 10
						        	});
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
				}
				
			},
			loadData: function(params) {
				var me = this;
	            DD.request({
					url:DATAURL + DATAURL1 + '/commodity/findCommodity',
					params: params,
					reqType:'GET',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.goods_list = r.result.content;
						me.data.sum_pages = r.result.pageCount;
						me.data.sum_data = r.result.total;
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
		}
    });
})()