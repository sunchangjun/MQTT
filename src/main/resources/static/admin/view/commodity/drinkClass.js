
(function () {
    
    DD.createModule({							/*饮料分类*/
        name:"drink",
        el:'#drinkclass',
        delayInit : false,
        templateUrl : BASEHTML + '/commodity/drinkClass.html',
        data:{
        	dir_list:[],
        	dir_gods:[],
        	boxPortCateId:'',
        	pages:1,
        	currentPage:'',
        	total:'',
        	tip_open:false,
        	icon_tip:'',
        	tips_tit:''
		},
        onBeforeFirstRender:function () {
        	var me = this;
        	me.module.methodFactory.methods.loadData.call(me, {});
        	var params = {
        		pages:me.data.pages,
        		boxPortCateId:me.data.boxPortCateId,
        		rows:10
        	};
        	me.module.methodFactory.methods.drink_god.call(me, params);
        },
        methods:{
      		loadData: function() {
				var me = this;
	            DD.request({
					url:DATAURL + DATAURL1 + '/boxPortCate/getAllBoxPortCate',
					params: {},
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.dir_list = r.result;
					},
					/*请求失败回调*/
					errorFunc:function(r){
						
					},
					/*请求超时回调*/
					timeoutFunc:function(r){
						console.log("系统超时");
					}
				});
			},
			drink_god: function(params) {
				var me = this;
	            DD.request({
					url:DATAURL + DATAURL1 + '/commodityBoxPortCateRel/getAllCommodityBoxPortCateRel',
					params: params,
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.dir_gods=r.result.content;
						me.data.currentPage=r.result.currentPage;
						me.data.total=r.result.total;
					},
					/*请求失败回调*/
					errorFunc:function(r){
						
					},
					/*请求超时回调*/
					timeoutFunc:function(r){
						console.log("系统超时");
					}
				});
			},
			cheak_dri:function(){
				var me = this;
				var params = {
					pages: 1,
					boxPortCateId:me.data.boxPortCateId,
					rows: 10
				};
        		me.module.methodFactory.methods.drink_god.call(me, params);
			},
			up_page:function(){						/*列表上一页*/
				var me = this;
				if(0<me.data.pages-1){
					me.data.pages = me.data.pages - 1;
					var params = {
						pages: me.data.pages,
						boxPortCateId:me.data.boxPortCateId,
						rows: 10
					};
	        		me.module.methodFactory.methods.drink_god.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1000);
				}
			},
			down_page:function(){					/*列表下一页*/
				var me = this;
				if(me.data.pages+1<=me.data.currentPage){
					me.data.pages = me.data.pages + 1;
					var params = {
						pages: me.data.pages,
						boxPortCateId:me.data.boxPortCateId,
						rows: 10
					};
	        		me.module.methodFactory.methods.drink_god.call(me, params);
				}else{
					me.data.tip_open = true;
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					setTimeout(function(){ me.data.tip_open = false; },1000);
				}
				
			},
		}
    })  
    
})()