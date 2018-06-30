/**
 * 菜单管理
 */
(function () {
    DD.createModule({
        name: "m_menu",
        delayInit: true,
        templateUrl: BASEHTML + '/auth/menu/menu.html',
        data: {
            page: 1,
            rows: 10,
            total:0,
            all_page:'',
            to_page: 1,
            searchName: '',
            type: 1,//1表示添加；2表示修改
            is_add: false, //是否点击添加菜单
            menu_list:[],		/*菜单*/
            menu_listF:[],		/*父级菜单*/
            menuId:'',          
            parentId:'',		/*添加获取父级id*/
            add_title: '',
            add_url: '',
            pop:false,
            tip_open:false,
            tips_tit:'',
            icon_tip:'',
        },
        onBeforeFirstRender: function () {
            var me = this;
			var params = {
				title:me.data.searchName,
				page:me.data.page,
            	rows:10
            };
            var paramF = {
				parentId:1,
            	page:me.data.page,
            	rows:10
            };
            me.module.methodFactory.methods.menu_list.call(me,params);
            me.module.methodFactory.methods.menu_listF.call(me,paramF);   /*获取父级*/
       },
        methods: {
            menu_list: function(params) {			/*列表*/
				var me = this;
	            DD.request({
					url:DATAURL + '/back/auth/menu/getList',
					params: params,
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.menu_list = r.result.content;
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
			menu_listF: function(paramF) {			/*父级菜单*/
				var me = this;
	            DD.request({
					url:DATAURL + '/back/auth/menu/getList',
					params: paramF,
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.menu_listF = r.result.content;
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
             * 搜索菜单
             */
            search: function(e, data, view) {			/*搜索*/
				var me = this;
				var params = {
					title:me.data.searchName,
					page: 1,
					rows: 10
				};
        		me.module.methodFactory.methods.menu_list.call(me, params);
			},
            /**
             * 添加菜单
             */
            add: function () {
                var me = this;
                me.data.is_add = true;
                me.data.type = 1;
                parentId = '';          /*添加获取父级id*/
	            add_title = '';
	            add_url = '';
            },
            /**
             * 确认添加
             */
            confirmAdd: function (e, d, v) {
                var me = this;
                if (!d.parentId) {
                	me.data.tips_tit = '请选择父级菜单';
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
                if (!d.add_url) {
                	me.data.tips_tit = '链接不能为空';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
                    return;
                } 
                if(me.data.type==1){
                	DD.request({
						url:DATAURL + '/back/auth/menu/add',
						params: {
							title:me.data.add_title,
							url:me.data.add_url,
							parentId:me.data.parentId 
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
		                    me.data.parentId = '';
		                    me.data.tips_tit = '添加成功';
							me.data.icon_tip = 2;
							me.data.tip_open = true;
							setTimeout(function(){ me.data.tip_open = false; },1500);
		                    var params = {
								page: 1,
								rows: 10
							};
		                    me.module.methodFactory.methods.menu_list.call(me, params);
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
						url:DATAURL + '/back/auth/menu/edit',
						params: {
							title:me.data.add_title,
							url:me.data.add_url,
							parentId:me.data.parentId,
							menuId:me.data.menuId
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
		                    me.data.parentId = '';
		                    me.data.menuId = '';
		                    me.data.tips_tit = '修改成功';
							me.data.icon_tip = 2;
							me.data.tip_open = true;
							setTimeout(function(){ me.data.tip_open = false; },1500);
		                    var params = {
								page: 1,
								rows: 10
							};
		                    me.module.methodFactory.methods.menu_list.call(me, params);
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
            /**
             * 确认取消
             */
            confirmCancel: function () {
                var me = this;
                me.data.is_add = false;
                me.data.add_url = '';
                me.data.add_title = '';
                me.data.parentId = '';
                me.data.menuId = '';
            },
            /**
             * 修改列表项
             */
            alterItem: function (e, d, v) {
                var me = this;
                me.data.is_add = true;
                me.data.type = 2;
                me.data.add_title = d.title;
                me.data.menuId = d.menuId;
                me.data.add_url = d.url;
                me.data.parentId = d.parentId;
            },
            goTo:function(){						/*指定跳转*/
				var me = this;
				if(me.data.to_page<=me.data.all_page){
					var params = {
						title:me.data.searchName,
		            	page:me.data.page,
		            	rows:10
		            };
	        		me.module.methodFactory.methods.menu_list.call(me, params);
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
	        		me.module.methodFactory.methods.menu_list.call(me, params);
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
	        		me.module.methodFactory.methods.menu_list.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}
				
			},
            /**
             * 删除列表项
             */
            deleteItem:function(e,d,v){
            	var me = this
            	me.data.pop = true;
            	me.data.menuId = d.menuId;
            },
            close_del:function(){
            	var me = this
            	me.data.pop = false;
            	me.data.menuId = '';
            },
            del_adv: function (e, d, v) {
                var me =this;
				DD.request({
					url:DATAURL + '/back/auth/menu/del',
					params: {menuId:me.data.menuId},
					reqType:'POST',
					type:'json',
					/*设置1秒超时*/
					timeout:5000,
					/*请求成功回调*/
					successFunc:function(r){
						if(r.success==true){
							me.data.pop = false;
							me.data.menuId = '';
							me.data.tips_tit = '删除成功';
							me.data.icon_tip = 2;
							me.data.tip_open = true;
							setTimeout(function(){ me.data.tip_open = false; },1500);
							var params = {
								title:me.data.searchName,
								page: 1,
								rows: 10
							};
							me.module.methodFactory.methods.menu.list.call(me, params);
						}else{
							alert(r.result);
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
				})
			}
        }
    })
}());