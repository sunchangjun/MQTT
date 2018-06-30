(function () {
    DD.createModule({			/*售货机监控*/
        name:"msellMonitor",
        el:'sellmon',
        delayInit : false,
        templateUrl : BASEHTML + '/operation/sellMonitor.html',
        data:{
        	open:false,
        	tip_open:false,
			goods_list:'',				/*商品列表*/
			boxCode:'',					/*售货机编号*/
			isEnabled:'',				/*状态*/
			com_list:'',
			pages:1,					/*默认页数*/
			pageCount:'',				/*总页数*/
			total:'',				/*总数据*/
			fp:0,
			spage:1,
			spages:'',
			tips_tit:'',
            icon_tip:'',
		},
        onBeforeFirstRender:function () {
           	var me = this;
        	me.module.methodFactory.methods.inventory_list.call(me, {		/*初始加载*/
        		pages: me.data.pages,
        		rows: 10
        	});
        },
        methods:{
			see_det:function(e,data,view){				/*查看库存*/
				var me = this;
				me.data.open = true;
				me.data.boxCode = view.dataset.id;		/*售货机编号*/
				DD.request({
					url:DATAURL + DATAURL1 + '/box/getBoxStockManage',
					params: {boxCode:me.data.boxCode},
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:200000,
					/*请求成功回调*/
					successFunc:function(r){
						if(r.result==''){
							me.data.spages = 1;
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
			close_det:function(){
				var me = this;
				me.data.open = false;
				me.data.boxCode = '';
				me.data.com_list = '';
			},
			up_page:function(){						/*列表上一页*/
				var me = this;
				if(0<me.data.pages-1){
					me.data.pages = me.data.pages - 1;
					var params = {
						boxCode: me.data.boxCode,
						isEnabled:me.data.isEnabled,
						pages: me.data.pages,
						rows: 10
					};
	        		me.module.methodFactory.methods.inventory_list.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1000);
				}
			},
			down_page:function(){					/*列表下一页*/
				var me = this;
				if(me.data.pages+1<=me.data.pageCount){
					me.data.pages = me.data.pages + 1;
					var params = {
						boxCode: me.data.boxCode,
						isEnabled:me.data.isEnabled,
						pages: me.data.pages,
						rows: 10
					};
	        		me.module.methodFactory.methods.inventory_list.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1000);
				}
				
			},
			search: function(e, data, view) {			/*搜索*/
				var me = this;
				var params = {
					boxCode: me.data.boxCode,
					isEnabled:me.data.isEnabled,
					pages: me.data.pages,
					rows: 10
				};
        		me.module.methodFactory.methods.inventory_list.call(me, params);
			},
			inventory_list: function(params) {							/*库存列表*/
				var me = this;
	            DD.request({
					url:DATAURL + DATAURL1 + '/box/getCommodityRoadPage',
					params: params,
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						r.result.content.forEach(function(item) {
							item.pickingName = [];
						});
						me.data.goods_list = r.result.content;
						me.data.pageCount = r.result.pageCount;
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
			down_sto:function(){
				var me = this;
				if(me.data.fp>=me.data.spages-1){
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1000);
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
					setTimeout(function(){ me.data.tip_open = false; },1000);
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
        }
    })
})()