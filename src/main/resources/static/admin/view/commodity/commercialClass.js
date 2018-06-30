
(function () {
    DD.createModule({							/*商品分类*/
        name:"mcommercialClass",
        el:'#classify',
        delayInit : false,
        templateUrl : BASEHTML + '/commodity/commercialClass.html',
        data:{
        	open:false,
        	pop:false,
        	class_id:'',
			class_list:'',
			commodityCateName:'',					/*分类名称*/
			commodityCateAlias:'',					/*分类别名*/
		 },
        onBeforeFirstRender:function () {
        	var me = this;
        	me.module.methodFactory.methods.cla_list.call(me, {});/*初始加载*/

        },
        methods:{
			add_cls:function(){						/*添加窗口*/
				var me = this;
				me.data.open = true;				
			},
			close_cls:function(){					/*关闭窗口*/
				var me = this;
				me.data.open = false;
				me.data.commodityCateName = '';
				me.data.commodityCateAlias = '';
			},
			pop_open:function(e,data,view){
				var me = this;
				me.data.pop = true;
				me.data.class_id = view.dataset.id;
			},
			close_open:function(){
				this.data.pop = false;
			},
			del_class:function(e,data,view){					/*删除分类*/
				var me =this;
				DD.request({
					url:DATAURL + DATAURL1 + '/commodityCate/deleteCommodityCate',
					params: {commodityCateId:me.data.class_id},
					reqType:'POST',
					type:'json',
					/*设置1秒超时*/
					timeout:5000,
					/*请求成功回调*/
					successFunc:function(r){
						if(r.result==true){
							alert("删除成功");
							me.data.pop = false;
							me.module.methodFactory.methods.cla_list.call(me, {}); 	 /*方法执行成功后，重新加载页面*/
						}else{
							alert(r.result);
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
			add_class: function(e, data, view) {			/*添加分类*/
				var me = this;
				var params = {
					commodityCateId: me.data.class_id,
					commodityCateName: me.data.commodityCateName,
					commodityCateAlias:me.data.commodityCateAlias,
				};
        		me.module.methodFactory.methods.add.call(me, params);
        	},
        	
        	edi_class:function(e, data, view){				/*编辑*/
        		var me = this;
        		me.data.class_id = view.dataset.id;
        		DD.request({
					url:DATAURL + DATAURL1 + '/commodityCate/getOneCommodityCate',
					params: {commodityCateId:me.data.class_id},
					reqType:'GET',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:1000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.open = true;
						me.data.commodityCateName = r.result.commodityCateName;
						me.data.commodityCateAlias = r.result.commodityCateAlias;
						me.module.methodFactory.methods.cla_list.call(me, {}); /*初始加载*/
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
			add: function(params) {						/*添加方法*/
				var me = this;
	            DD.request({
					url:DATAURL + DATAURL1 + '/commodityCate/addOrUpdateCommodityCate',
					params: params,
					reqType:'GET',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:1000,
					/*请求成功回调*/
					successFunc:function(r){
						if(me.data.commodityCateName==''){	
		        			alert('名称不能为空')
		        		}else if(me.data.commodityCateAlias==''){
		        			alert('别名不能为空')
		        		}else{
		        			alert('操作成功');
		        			me.data.open = false;
							me.module.methodFactory.methods.cla_list.call(me, {});/*初始加载*/
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
			cla_list:function(){
				var me = this;
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
			
        }
    })  
})()