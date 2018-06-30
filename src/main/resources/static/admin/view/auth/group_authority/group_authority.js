// 角色权限配置
(function () {
    DD.createModule({
        name: "m_group_authority",
        delayInit: true,
        templateUrl: BASEHTML + '/auth/group_authority/group_authority.html',
        data: {
            left_page: 1,
            left_searchName: '',
            left_total:0,
            left_all_page:'',
            left_to_page: 1,
            user_list:[],
            right_page: 1,
            right_searchName: '',
            right_total:0,
            right_all_page:'',
            right_to_page: 1,
            auth_list:[],
            tips_tit:'',
            icon_tip:'',
            tip_open:false,
            group_id:'',
            bind_list:[],
            bind_total:'',
            ids:'',
        },
        onBeforeFirstRender: function () {
            var me = this;
			var params = {							/*左侧角色*/
				groupName:me.data.left_searchName,
            	page:me.data.left_page,
            	rows:10
            };
			me.module.methodFactory.methods.updateLeftPage.call(me,params);
		},
        methods: {
            /**
             * 更新左侧内容
             */
            updateLeftPage: function (params) {
                var me = this;
	            DD.request({
					url:DATAURL + '/back/auth/group/getList',
					params: params,
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.user_list = r.result.content;
						me.data.left_total = r.result.total;
						me.data.left_all_page = r.result.pageCount;
						me.data.group_id = r.result.content[0].groupId;
						me.data.user_list[0].$set('is_click',true);
						var bind = {					
							groupId:me.data.group_id,
			            };
			            me.module.methodFactory.methods.updateBinds.call(me,bind);
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
             * 点击角色项
             */
            clickItem:function(e,d,v){
            	var me = this;
            	me.data.group_id = d.groupId;
            	var bind = {					
					groupId:me.data.group_id,
	            };
	            me.module.methodFactory.methods.updateBinds.call(me,bind);
	            d.$set('is_click',true);
            	
            	me.data.user_list.forEach(function (item) {
                	if (d.groupId != item.groupId) {
	                    item.$set('is_click',false);
	                    return;
	                }
	            });
            },
            /**
             * 更新已绑定内容
             */
            updateBinds: function (params) {
                var me = this;
	            DD.request({
					url:DATAURL + '/back/auth/group/getAuth',
					params: params,
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.bind_list = r.result;
						me.data.bind_total = r.result.length;
						var param = {							/*右侧权限*/
							authorityName:me.data.right_searchName,
			            	page:me.data.right_page,
			            	rows:10
			            };
						me.module.methodFactory.methods.updateRightPage.call(me,param);
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
             * 更新右侧页面内容
             */
            updateRightPage: function (param) {
                var me = this;
	            DD.request({
					url:DATAURL + '/back/auth/authority/getList',
					params: param,
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.auth_list = r.result.content;
						me.data.right_total = r.result.total;
						me.data.right_all_page = r.result.pageCount;
						me.data.auth_list.forEach(function (item) {			/*对比列表项是否绑定*/
		                	me.data.bind_list.forEach(function (i) {
			                    if (item.authorityId == i.authorityId) {
			                        item.$set('is_bind',true);
			                        return;
			                    }
			                });
		                });
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
             	添加绑定
            */
            addBind: function (e, d, v) {
                var me = this;
                me.data.bind_list.push({
                    authorityId: d.authorityId,
                    authorityName: d.authorityName,
                    authorityCode: d.authorityCode
                });
                var arr_ids = [];
                for(var i=0;i<me.data.bind_list.length;i++){
                	var str = me.data.bind_list[i].authorityId;
                	arr_ids.push(str);
                };
                me.data.ids = arr_ids.join(",");
                DD.request({
					url:DATAURL + '/back/auth/group/updAuth',
					params: {
						groupId:me.data.group_id,
						authorityIds:me.data.ids,
					},
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						var bind = {					
							groupId:me.data.group_id,
			            };
			            me.module.methodFactory.methods.updateBinds.call(me,bind);
			            me.data.tips_tit = '绑定成功';
						me.data.icon_tip = 2;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					},
					/*请求超时回调*/
					timeoutFunc:function(r){
						console.log("系统超时");
					}
				});           
            },
            /**
             	解除绑定
            */
            removeBind:function(e,d,v){
            	var me = this;
                me.data.bind_list.forEach(function (item, index) {
                    if (item.authorityId === d.authorityId) {
                        me.data.bind_list.splice(index, 1);
                        return;
                    }
                });
                var arr_ids = [];
                for(var i=0;i<me.data.bind_list.length;i++){
                	var str = me.data.bind_list[i].authorityId;
                	arr_ids.push(str);
                };
                me.data.ids = arr_ids.join(",");
                if(me.data.ids == ''){
                	me.data.ids = ','
                }
                DD.request({
					url:DATAURL + '/back/auth/group/updAuth',
					params: {
						groupId:me.data.group_id,
						authorityIds:me.data.ids,
					},
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						var bind = {					
							groupId:me.data.group_id,
			            };
			            me.module.methodFactory.methods.updateBinds.call(me,bind);
			            me.data.tips_tit = '解绑成功';
						me.data.icon_tip = 2;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
					},
					/*请求超时回调*/
					timeoutFunc:function(r){
						console.log("系统超时");
					}
				});           
            },
            /**
             	搜索角色
             */
            userSearch: function(e, data, view) {
				var me = this;
				var params = {
					groupName:me.data.left_searchName,
	            	page:me.data.left_page,
	            	rows:10
	            };
				me.module.methodFactory.methods.updateLeftPage.call(me,params);
			},
            /**
             	 搜索权限
             */
            authSearch: function (e, d, v) {
            	var me = this;
                var param = {							
					authorityName:me.data.right_searchName,
	            	page:me.data.right_page,
	            	rows:10
	            };
				me.module.methodFactory.methods.updateRightPage.call(me,param);
            },
            right_goTo:function(){						/*指定跳转*/
				var me = this;
				if(me.data.right_to_page<=me.data.right_all_page){
					var param = {							
						authorityName:me.data.right_searchName,
		            	page:me.data.right_to_page,
		            	rows:10
		            };
	        		me.module.methodFactory.methods.updateRightPage.call(me, param);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
					me.data.right_to_page = 1;
				}
			},
			right_toPrev:function(){						/*列表上一页*/
				var me = this;
				if(0<me.data.right_page-1){
					me.data.right_page = me.data.right_page - 1;
					var param = {
						page: me.data.right_page,
						authorityName:me.data.right_searchName,
						rows: 10
					};
	        		me.module.methodFactory.methods.updateRightPage.call(me, param);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}
			},
			right_toNext:function(){					/*列表下一页*/
				var me = this;
				if(me.data.right_page+1<=me.data.right_all_page){
					me.data.right_page = me.data.right_page + 1;
					var param = {
						page: me.data.right_page,
						authorityName:me.data.right_searchName,
						rows: 10
					};
	        		me.module.methodFactory.methods.updateRightPage.call(me, param);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}
				
			},
            left_goTo:function(){						/*指定跳转*/
				var me = this;
				if(me.data.left_to_page<=me.data.left_all_page){
					var params = {							/*左侧角色*/
						groupName:me.data.left_searchName,
		            	page:me.data.left_to_page,
		            	rows:10
		            };
	        		me.module.methodFactory.methods.updateLeftPage.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
					me.data.left_to_page = 1;
				}
			},
			left_toPrev:function(){						/*列表上一页*/
				var me = this;
				if(0<me.data.left_page-1){
					me.data.left_page = me.data.left_page - 1;
					var params = {
						page: me.data.left_page,
						groupName:me.data.left_searchName,
						rows: 10
					};
	        		me.module.methodFactory.methods.updateLeftPage.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}
			},
			left_toNext:function(){					/*列表下一页*/
				var me = this;
				if(me.data.left_page+1<=me.data.left_all_page){
					me.data.left_page = me.data.left_page + 1;
					var params = {
						page: me.data.left_page,
						groupName:me.data.left_searchName,
						rows: 10
					};
	        		me.module.methodFactory.methods.updateLeftPage.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}
				
			},
		}
    })
}());