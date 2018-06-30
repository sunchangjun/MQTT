(function () {
    DD.createModule({		
        name:"advertising",
        el:'#advertising',
        delayInit : false,
       	templateUrl : BASEHTML + '/advertising/advertising.html',
        data:{
        	tip_open:false,
        	pages:1,
			sum_pages:'',
			sum_data:'',
			adv_list:[],
			open:false,
			imageResources:'',
			imageResourceId:'',
			type:'',
			linkUrl:'',
			areaId:'',
			img_up:false,
			advId:'',			/*广告id*/
			sear_type:'',
			sear_ad:'',
			tips_tit:'',
        	icon_tip:'',
        	pop:false,
        	toId:'',      /*排序id*/
        },
        onBeforeFirstRender:function () {
        	var me = this;
            var params = {
        		pages:me.data.pages,
        		rows:10
        	};
        	me.module.methodFactory.methods.loadData.call(me, params);
        },
        methods:{
        	close_add:function(){
        		var me = this;
        		me.data.open = false;
        	},
        	del_flo:function(e,d,v){
        		var me = this;
        		me.data.advId = d.advId; 
        		me.data.pop = true;
        	},
        	Upsort:function(e,d,v){				/*向上排序*/
        		var me = this; 		
        		var coor = d.$index;	
        		if(coor>0){
        			for(var i = 0;i<me.data.adv_list.length;i++){
	        			if(me.data.adv_list[i].$index == coor-1) {
	        				me.data.toId = me.data.adv_list[i].advId
	                    	break;
	                	} else {
	                		
	                	}
	                }
        			DD.request({
						url:DATAURL+ DATAURL1  + '/adv/exchangeSort',
						reqType:'POST',
						params:{
							currentId:d.advId,
							toId:me.data.toId,
						},
						type:'json',
						successFunc:function(r){
							var params = {
				        		pages:me.data.pages,
				        		rows:10
				        	};
				        	me.module.methodFactory.methods.loadData.call(me, params);
						},
						errorFunc:function(r){
							console.log(r);
						},
					});
        		}else if(coor == 0){
        			me.data.tips_tit = '已经在最前面';
					me.data.tip_open = true;
        			me.data.icon_tip = 1;
        			setTimeout(function(){ me.data.tip_open = false; },1500);
        		}
        	},
        	Downsort:function(e,d,v){			/*向下排序*/
        		var me = this; 		
        		var coor = d.$index;	
        		if(coor>=0 && coor<me.data.adv_list.length-1){
        			for(var i = 0;i<me.data.adv_list.length;i++){
	        			if(me.data.adv_list[i].$index == coor+1) {
	        				me.data.toId = me.data.adv_list[i].advId
	                    	break;
	                	} else {
	                		
	                	}
	                }
        			DD.request({
						url:DATAURL + DATAURL1 + '/adv/exchangeSort',
						reqType:'POST',
						params:{
							currentId:d.advId,
							toId:me.data.toId,
						},
						type:'json',
						successFunc:function(r){
							var params = {
				        		pages:me.data.pages,
				        		rows:10
				        	};
				        	me.module.methodFactory.methods.loadData.call(me, params);
						},
						errorFunc:function(r){
							console.log(r);
						},
					});
        		}else if(coor == me.data.adv_list.length-1){
        			me.data.tips_tit = '已经在最下面';
					me.data.tip_open = true;
        			me.data.icon_tip = 1;
        			setTimeout(function(){ me.data.tip_open = false; },1500);
        		}
        	},
        	Topsort:function(e,d,v){
        		var me = this; 		
        		var coor = d.$index;	
        		me.data.toId = me.data.adv_list[0].advId
        		DD.request({
					url:DATAURL + DATAURL1 + '/adv/exchangeSort',
					reqType:'POST',
					params:{
						currentId:d.advId,
						toId:me.data.toId,
					},
					type:'json',
					successFunc:function(r){
						var params = {
			        		pages:me.data.pages,
			        		rows:10
			        	};
			        	me.module.methodFactory.methods.loadData.call(me, params);
					},
					errorFunc:function(r){
						console.log(r);
					},
				});
        	},
        	close_del:function(){
        		var me = this;
        		me.data.pop = false;
        		me.data.advId = '';
        	},
        	add_adv:function(){
        		var me = this;
        		me.data.open = true;
        		me.data.imageResources='';
        		me.data.img_up=false;
        		me.data.advId = '';
        		me.data.linkUrl = '';
        		me.data.areaId = '';			/*区域*/
        		me.data.type = '';
        		me.data.imageResourceId = '';
        	},
        	edi_tc:function(e,d,v){					/*编辑广告*/
        		var me =this;
        		me.data.advId = d.advId;
        		me.data.open = true;
        		me.data.img_up = true;
        		me.data.linkUrl = d.linkUrl;
        		me.data.areaId = d.areaId;			/*区域*/
        		me.data.imageResources = d.imageResource.url;
        		me.data.imageResourceId = d.imageResource.imageResourceId;
        		me.data.type = d.type;
        		console.log(me.data.type);
        	},
        	kep_adv:function(){
        		var me = this;
        		console.log(me.data.type);
        		if(me.data.type==''){
					me.data.tips_tit = '请选择类型';
					me.data.tip_open = true;
        			me.data.icon_tip = 1;
        			setTimeout(function(){ me.data.tip_open = false; },1500);
        		}else if(me.data.imageResourceId==''){
        			me.data.tips_tit = '请上传图片';
					me.data.tip_open = true;
        			me.data.icon_tip = 1;
        			setTimeout(function(){ me.data.tip_open = false; },1500);
        		}
        		else{
        			DD.request({
						url:DATAURL +DATAURL1 + '/adv/addOrUpdateAdv',
						reqType:'POST',
						params:{
							advId:me.data.advId,
							type:me.data.type,
							imageResourceId:me.data.imageResourceId,
							linkUrl:me.data.linkUrl,
							areaId:me.data.areaId,
						},
						type:'json',
						successFunc:function(r){
							me.data.tips_tit = '操作成功';
							me.data.tip_open = true;
		        			me.data.icon_tip = 2;
		        			setTimeout(function(){ me.data.tip_open = false; },1500);
							me.data.open = false;
							var params = {
				        		pages:me.data.pages,
				        		rows:10
				        	};
				        	me.module.methodFactory.methods.loadData.call(me, params);
						},
						errorFunc:function(r){
							console.log(r);
						},
					});
        		}
        	},
        	UploadImg:function(e, data, view){
				if(view.files[0]){
					var me=this;
					DD.request({
						url:DATAURL+ DATAURL1 + '/imageResource/uploadImageResource',
						reqType:'POST',
						params:{
							mult:view.files[0]
						},
						type:'json',
						successFunc:function(r){
							me.data.img_up = true;
							me.data.imageResources=r.result.url;
							me.data.imageResourceId=r.result.imageResourceId;
						},
						errorFunc:function(r){
							console.log(r);
						},
					});
				}
			},
			search:function(){
				var me =this;
				var params = {
					areaId: me.data.sear_ad,
					type: me.data.sear_type,
					pages: me.data.pages,
					rows: 10
				};
        		me.module.methodFactory.methods.loadData.call(me, params);
			},
			del_adv:function(e,d,v){
				var me = this;
				DD.request({
					url:DATAURL + DATAURL1 + '/adv/deleteAdv',
					params: {
						advId:me.data.advId
					},
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.tips_tit = '操作成功';
						me.data.tip_open = true;
	        			me.data.icon_tip = 2;
	        			setTimeout(function(){ me.data.tip_open = false; },1500);
	        			me.data.pop = false;
						var params = {
							areaId: me.data.sear_ad,
							type: me.data.sear_type,
							pages: me.data.pages,
							rows: 10
						};
		        		me.module.methodFactory.methods.loadData.call(me, params);
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
        	loadData: function(params) {
				var me = this;
	            DD.request({
					url:DATAURL +DATAURL1 + '/adv/getAllAdv',
					params: params,
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.adv_list = r.result.content;
						me.data.sum_pages = r.result.pageCount;
						me.data.sum_data = r.result.total;
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
        	up_page:function(){						/*列表上一页*/
				var me = this;
				if(0<me.data.pages-1){
					me.data.pages = me.data.pages - 1;
					var params = {
						areaId: me.data.sear_ad,
						type: me.data.sear_type,
						pages: me.data.pages,
						rows: 10
					};
	        		me.module.methodFactory.methods.loadData.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.tip_open = true;
        			me.data.icon_tip = 1;
        			setTimeout(function(){ me.data.tip_open = false; },1500);
				}
			},
			down_page:function(){					/*列表下一页*/
				var me = this;
				if(me.data.pages+1<=me.data.sum_pages){
					me.data.pages = me.data.pages + 1;
					var params = {
						areaId: me.data.sear_ad,
						type: me.data.sear_type,	
						pages: me.data.pages,
						rows: 10
					};
	        		me.module.methodFactory.methods.loadData.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.tip_open = true;
        			me.data.icon_tip = 1;
        			setTimeout(function(){ me.data.tip_open = false; },1500);
				}
			},
		}
    })
})()