/**
 * 权限管理
 */
(function () {
    DD.createModule({
        name: "m_authority",
        delayInit: true,
        templateUrl: BASEHTML + '/auth/authority/authority.html',
        data: {
            page: 1,
            rows: 10,
            searchName: '',
            user_list:[],
            total:0,
            all_page:'',
            to_page: 1,
            is_add: false, //是否点击添加权限
            add_authority: '',
            add_name: '',
           	authorityId:'',
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
				authorityName:me.data.searchName,
            	page:me.data.page,
            	rows:10
            };
			me.module.methodFactory.methods.loadData.call(me,params);
        },
        methods: {
            /**
             * 更新页面内容
             */
            loadData: function(params) {
				var me = this;
	            DD.request({
					url:DATAURL + '/back/auth/authority/getList',
					params: params,
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.user_list = r.result.content;
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
			close_open:function(){
				this.data.pop = false;
			},
			del_auth:function(e,data,view){					
				var me =this;
				DD.request({
					url:DATAURL + '/back/auth/authority/del',
					params: {authorityId:me.data.authorityId},
					reqType:'POST',
					type:'json',
					/*设置1秒超时*/
					timeout:5000,
					/*请求成功回调*/
					successFunc:function(r){
						if(r.result==true){
							me.data.tips_tit = '删除成功';
							me.data.icon_tip = 2;
							me.data.tip_open = true;
							setTimeout(function(){ me.data.tip_open = false; },1500);
							me.data.pop = false;
							var params = {
								page: 1,
								rows: 10
							};
		                    me.module.methodFactory.methods.loadData.call(me, params);
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
			goTo:function(){						/*指定跳转*/
				var me = this;
				if(me.data.to_page<=me.data.all_page){
					var params = {
						authorityName:me.data.searchName,
						page: me.data.to_page,
						rows: 10
					};
	        		me.module.methodFactory.methods.loadData.call(me, params);
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
						page: me.data.page,
						authorityName:me.data.searchName,
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
			toNext:function(){					/*列表下一页*/
				var me = this;
				if(me.data.page+1<=me.data.all_page){
					me.data.page = me.data.page + 1;
					var params = {
						page: me.data.page,
						authorityName:me.data.searchName,
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
            /**
             * 搜索权限
             */
            search: function(e, data, view) {			/*搜索*/
				var me = this;
				var params = {
					authorityName:me.data.searchName,
					page: 1,
					rows: 10
				};
        		me.module.methodFactory.methods.loadData.call(me, params);
			},
            /**
             * 添加权限
             */
            add: function () {
                var me = this;
                me.data.is_add = true;
            },
            /**
             * 确认添加
             */
            confirmAdd: function (e, d, v) {
                var me = this;
                if (!d.add_authority) {
                	me.data.tips_tit = '权限不能为空';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
                   	return;
                }
                if (!d.add_name) {
                	me.data.tips_tit = '名称不能为空';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
                    return;
                }                
                DD.request({
					url:DATAURL + '/back/auth/authority/add',
					params: {
						authorityCode:me.data.add_authority,
						authorityName:me.data.add_name
					},
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.is_add = false;
	                    me.data.add_authority = '';
	                    me.data.add_name = '';
	                    me.data.tips_tit = '添加成功';
						me.data.icon_tip = 2;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
	                    var params = {
							page: 1,
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
            /**
             * 确认取消
             */
            confirmCancel: function () {
                var me = this;
                me.data.is_add = false;
                me.data.add_authority = '';
                me.data.add_name = '';
            },
            /**
             * 删除列表项
             */
            deleteItem: function (e, d, view) {
                var me = this;
                me.data.authorityId = d.authorityId;
                me.data.pop = true;
            }
        },
    })
}());