(function () {
    DD.createModule({			/*售货机*/
        name:"machine",
        el:'#machine',
        delayInit : false,
       	templateUrl : BASEHTML + '/commercialMachine/machineManage.html',
        data:{
        	pages:1,
        	pageCount:'',
        	total:'',
        	mach_list:[],			/*售货机列表*/
        	serialnumber:'',		/*售货机编码*/
        	area_list:[],		/*区域列表*/
        	areaId:'',			/*区域id*/
        	sta:'',   		/*状态*/
        	tip_open:false,
        	open:false,
        	box_id:'',			/*售货机编号*/
    		area_id:'', 		/*区域id*/
    		code_img_url:'',	/*二维码*/
    		box_code:'',		/*售货机身号*/
    		box_key:'',			/*售货机key*/
    		longitude:'',		/*经度*/
    		latitude:'',		/*纬度*/
    		cargo_road_number:'',	/*货道数量*/
    		address:'',				/*售货机地址*/
    		status:'',				/*售货机状态*/
    		icon_tip:'',
    		tips_tit:'',
    		oper_tit:''
        },
        onBeforeFirstRender:function () {
           	var me = this;
        	me.module.methodFactory.methods.loadData.call(me, {
        		pages: me.data.pages,
        		rows: 10
        	});
        	me.module.methodFactory.methods.areaID.call(me, {});
        },
        methods:{
        	add_mach:function(){
				var me = this;
				me.data.open = true;
				me.data.oper_tit = '添加';
			},
			close_mach:function(){
				var me = this;
				me.data.box_id='';
	    		me.data.area_id=''; 	
	    		me.data.code_img_url='';	
	    		me.data.box_code='';	
	    		me.data.box_key='';			
	    		me.data.longitude='';	
	    		me.data.latitude='';		
	    		me.data.cargo_road_number='';	
	    		me.data.address='';				
	    		me.data.status='';				
	    		me.data.open = false;
	    		me.data.oper_tit = '';
			},
			see_img:function(e,data,view){
				view.children[0].style.display='block';
//				setTimeout(function(){view.children[0].style.display='none';},3000)
			},
			close_see:function(e,data,view){
				view.children[0].style.display='none';
			},
			edi_tc:function(e,data,view){			/*编辑*/
				var me = this;
				me.data.open = true;
				me.data.area_id = data.areaId;
				me.data.status = data.status;
				me.data.box_id = data.boxId;			/*售货机编号*/
	    		me.data.code_img_url = data.codeImgUrl;	/*二维码*/
	    		me.data.box_code=data.boxCode;		/*售货机身号*/
	    		me.data.box_key=data.boxKey;			/*售货机key*/
	    		me.data.longitude=data.longitude;		/*经度*/
	    		me.data.latitude=data.latitude;		/*纬度*/
	    		me.data.cargo_road_number = data.cargoRoadNumber;	/*货道数量*/
	    		me.data.address =data.address;				/*售货机地址*/
	    		me.data.status = data.status;				/*售货机状态*/
	    		me.data.oper_tit = '编辑';
			},
        	search: function(e, data, view) {			/*搜索*/
				var me = this;
				var params = {
					boxCode : me.data.serialnumber,
					areaId : me.data.areaId,
					isEnable :me.data.sta,
					pages: 1,
					rows: 10
				};
        		me.module.methodFactory.methods.loadData.call(me, params);
        	},
        	kep_mach:function(){					/*保存新增*/
        		var me = this;
        		if(me.data.area_id==''){
        			me.data.tips_tit = '请选择区域';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
        		} else if(me.data.cargo_road_number==''){
        			me.data.tips_tit = '请填写货道数量';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
        		} else if(me.data.status==''){
        			me.data.tips_tit = '请选择状态';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
        		} else {
        			DD.request({
						url:DATAURL + DATAURL1 + '/box/addOrUpdateBox',
						params: {
							boxId:me.data.box_id,			/*售货机编号*/
				    		areaId:me.data.area_id, 		/*区域id*/
				    		codeImgUrl:me.data.code_img_url,	/*二维码*/
				    		boxCode:me.data.box_code,		/*售货机身号*/
				    		boxKey:me.data.box_key,			/*售货机key*/
				    		longitude:me.data.longitude,		/*经度*/
				    		latitude:me.data.latitude,		/*纬度*/
				    		cargoRoadNumber:me.data.cargo_road_number,	/*货道数量*/
				    		address:me.data.address,				/*售货机地址*/
				    		status:me.data.status,				/*售货机状态*/
						},
						reqType:'POST',
						type:'json',
						async:true,
						/*设置1秒超时*/
						timeout:2000,
						/*请求成功回调*/
						successFunc:function(r){
							if(r.success==true){
								me.data.tips_tit = '操作成功';
								me.data.icon_tip = 2;
								me.data.tip_open = true;
								setTimeout(function(){ me.data.tip_open = false; },1500);
								me.data.oper_tit = '';
								me.data.box_id='';
					    		me.data.area_id=''; 	
					    		me.data.code_img_url='';	
					    		me.data.box_code='';	
					    		me.data.box_key='';			
					    		me.data.longitude='';	
					    		me.data.latitude='';		
					    		me.data.cargo_road_number='';	
					    		me.data.address='';				
					    		me.data.status='';	
					    		me.data.open = false;
					    		me.module.methodFactory.methods.loadData.call(me, {
					        		pages: 1,
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
        	},
        	up_page:function(){						/*列表上一页*/
				var me = this;
				if(0<me.data.pages-1){
					me.data.pages = me.data.pages - 1;
					var params = {
						boxCode : me.data.serialnumber,
						areaId : me.data.areaId,
						isEnable :me.data.sta,
						pages: me.data.pages,
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
			down_page:function(){					/*列表下一页*/
				var me = this;
				if(me.data.pages+1<=me.data.pageCount){
					me.data.pages = me.data.pages + 1;
					var params = {
						boxCode : me.data.serialnumber,
						areaId : me.data.areaId,
						isEnable :me.data.sta,
						pages: me.data.pages,
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
			areaID: function() {
				var me = this;
	            DD.request({
					url:DATAURL + DATAURL1 + '/area/getAll',
					params: {},
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.area_list = r.result;
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
        	loadData: function(params) {
				var me = this;
	            DD.request({
					url:DATAURL + DATAURL1 + '/box/getAllBox',
					params: params,
					reqType:'GET',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.mach_list = r.result.content;
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
			}
        }
    })
})()