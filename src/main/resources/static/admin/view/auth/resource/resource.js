/**
 * 资源管理
 */
(function () {
    DD.createModule({
        name: "m_resource",
        delayInit: true,
        templateUrl: BASEHTML + '/auth/resource/resource.html',
        data: {
            page: 1,
            rows: 10,
            searchName: '',
            total:0,
            all_page:'',
            to_page: 1,
            res_list:[],
            add_url: '',
            add_title: '',
            is_add: false, //是否点击添加资源
            pop:false,
            type:'',
            resourceId:'',
            pop:false,
            tip_open:false,
            tips_tit:'',
            icon_tip:'',
        },
        onBeforeFirstRender: function () {
            var me = this;
            me.data.page = 1;
            me.data.searchName = '';
			var params = {
				title:me.data.searchName,
            	page:me.data.page,
            	rows:10
            };
			me.module.methodFactory.methods.res_list.call(me,params);
		},
        methods: {
            /**
             * 更新页面内容
             */
            res_list: function(params) {
				var me = this;
	            DD.request({
					url:DATAURL + '/back/auth/resource/getList',
					params: params,
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.res_list = r.result.content;
						me.data.total = r.result.total;
						me.data.all_page = r.result.pageCount;
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
			/**
             * 搜索资源
             */
            search: function(e, data, view) {			/*搜索*/
				var me = this;
				var params = {
					title:me.data.searchName,
					page: 1,
					rows: 10
				};
        		me.module.methodFactory.methods.res_list.call(me, params);
			},
			close_open:function(){
				this.data.pop = false;	
			},
            /**
             * 添加资源
             */
            add: function () {
                var me = this;
                me.data.is_add = true;
                me.data.type = 1;
            },
            /**
             * 确认添加
             */
            confirmAdd: function (e, d, v) {
                var me = this;
                if (!d.add_url) {
                	me.data.tips_tit = '链接不能为空';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
                   	return;
                }
                if (!d.add_title) {
                	me.data.tips_tit = '名称不能为空';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
                    return;
                }   
                if(me.data.type==1){
                	DD.request({
						url:DATAURL + '/back/auth/resource/add',
						params: {
							title:me.data.add_title,
							url:me.data.add_url
						},
						reqType:'POST',
						type:'json',
						async:true,
						/*设置1秒超时*/
						timeout:2000,
						/*请求成功回调*/
						successFunc:function(r){
							me.data.is_add = false;
		                    me.data.add_url = '';
		                    me.data.add_title = '';
		                    me.data.tips_tit = '添加成功';
							me.data.icon_tip = 2;
							me.data.tip_open = true;
							setTimeout(function(){ me.data.tip_open = false; },1500);
		                    var params = {
								page: 1,
								rows: 10
							};
		                    me.module.methodFactory.methods.res_list.call(me, params);
						},
						/*请求失败回调*/
						errorFunc:function(r){
							
						},
						/*请求超时回调*/
						timeoutFunc:function(r){
							console.log("系统超时");
						}
					});
                }else if(me.data.type == 2){
                	DD.request({
						url:DATAURL + '/back/auth/resource/edit',
						params: {
							resourceId:me.data.resourceId,
							title:me.data.add_title,
							url:me.data.add_url
						},
						reqType:'POST',
						type:'json',
						async:true,
						/*设置1秒超时*/
						timeout:2000,
						/*请求成功回调*/
						successFunc:function(r){
							me.data.is_add = false;
		                    me.data.add_url = '';
		                    me.data.add_title = '';
		                    me.data.tips_tit = '修改成功';
							me.data.icon_tip = 2;
							me.data.tip_open = true;
							setTimeout(function(){ me.data.tip_open = false; },1500);
		                    var params = {
								page: 1,
								rows: 10
							};
		                    me.module.methodFactory.methods.res_list.call(me, params);
						},
						/*请求失败回调*/
						errorFunc:function(r){
							
						},
						/*请求超时回调*/
						timeoutFunc:function(r){
							console.log("系统超时");
						}
					});
                }
            },
            goTo:function(){						/*指定跳转*/
				var me = this;
				if(me.data.to_page<=me.data.all_page){
					var params = {
						title:me.data.searchName,
		            	page:me.data.page,
		            	rows:10
		            };
	        		me.module.methodFactory.methods.res_list.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
					me.data.to_page = 1;
				}
			},
			toPrev:function(){						/*列表上一页*/
				var me = this;
				if(0<me.data.page-1){
					me.data.page = me.data.page - 1;
					var params = {
						title:me.data.searchName,
		            	page:me.data.page,
		            	rows:10
		            };
	        		me.module.methodFactory.methods.res_list.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}
			},
			toNext:function(){					/*列表下一页*/
				var me = this;
				if(me.data.page+1<=me.data.all_page){
					me.data.page = me.data.page + 1;
					var params = {
						title:me.data.searchName,
		            	page:me.data.page,
		            	rows:10
		            };
	        		me.module.methodFactory.methods.res_list.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}
				
			},
            /**
             * 确认取消
             */
            confirmCancel: function () {
                var me = this;
                me.data.is_add = false;
                me.data.add_url = '';
                me.data.add_title = '';
                me.data.type = '';
                me.data.resourceId = '';
            },
            /**
             * 修改列表项
             */
            alterItem: function (e, data, view) {
                var me = this;
                me.data.is_add = true;
                me.data.resourceId = data.resourceId;
                me.data.add_url = data.url;
                me.data.add_title = data.title;
                me.data.type = 2;
            },
            /**
             * 删除列表项
             */
            deleteItem: function (e, d, view) {
                var me = this;
                me.data.resourceId = d.resourceId;
                me.data.pop = true;
            },
            del_res:function(e,data,view){					
				var me =this;
				DD.request({
					url:DATAURL + '/back/auth/resource/del',
					params: {resourceId:me.data.resourceId},
					reqType:'POST',
					type:'json',
					/*设置1秒超时*/
					timeout:5000,
					/*请求成功回调*/
					successFunc:function(r){
						if(r.result==true){
							me.data.pop = false;
							me.data.tips_tit = '删除成功';
							me.data.icon_tip = 2;
							me.data.tip_open = true;
							setTimeout(function(){ me.data.tip_open = false; },1500);
							var params = {
								page: 1,
								rows: 10
							};
		                    me.module.methodFactory.methods.res_list.call(me, params);
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
			}
        }
    })
}());