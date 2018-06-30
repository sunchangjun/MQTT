(function () {
    DD.createModule({
        name: 'm_add_admin',
        delayInit: true,
        templateUrl : BASEHTML + '/auth/admin_list/add_admin/add_admin.html',
        data: {
            real_name: '',
            mobile: '',
            user_pwd: '',
            user_pwd_again: '',
            grp_list:[],
            is_bind:false,
            no_binds:true,
            page: 1,
            rows: 10,
            total:0,
            all_page:'',
            to_page: 1,
            bind_list:[],
            user_id:'',
            ediName:'',
            ediPass:'',
            ids:'',
            type:'',
            tip_open:false,
            tips_tit:'',
            icon_tip:'',
        },
        onBeforeFirstRender: function () {
        	var me = this;
           	me.data.bind_list = [];
            me.data.ids = '';
            me.data.no_binds = true;
            var params = {
            	page:me.data.page,
            	rows:me.data.rows
            };
            me.module.methodFactory.methods.grp_list.call(me,params);
			me.data.user_id = localStorage.getItem("user_id");
			me.data.ediName = localStorage.getItem("userName");
			me.data.ediPass = localStorage.getItem("userPwd");
			me.data.type = localStorage.getItem("type");
			var edi_par = {
            	userId:me.data.user_id
            };
            if(me.data.type == 2){
            	me.module.methodFactory.methods.edi_user.call(me,edi_par);
            } else{
            	
            }
			
		},
        methods: {
           	grp_list: function(params) {
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
						me.data.grp_list = r.result.content;
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
			edi_user: function(edi_par) {			/*编辑获取用户组*/
				var me = this;
	            DD.request({
					url:DATAURL + '/back/auth/user/getGroup',
					params: edi_par,
					reqType:'POST',
					type:'json',
					async:true,
					/*设置1秒超时*/
					timeout:2000,
					/*请求成功回调*/
					successFunc:function(r){
						me.data.bind_list = r.result;
						if(me.data.bind_list.length>0){
							me.data.no_binds = false;
						}else{
							me.data.no_binds = true;
						}
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
			goTo:function(){						/*指定跳转*/
				var me = this;
				if(me.data.to_page<=me.data.all_page){
					var params = {
						page: me.data.to_page,
						rows: me.data.rows
					};
	        		me.module.methodFactory.methods.grp_list.call(me, params);
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
						rows: me.data.rows
					};
	        		me.module.methodFactory.methods.grp_list.call(me, params);
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
						rows: me.data.rows
					};
	        		me.module.methodFactory.methods.grp_list.call(me, params);
				}else{
					me.data.tips_tit = '没有更多了';
					me.data.icon_tip = 1;
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1500);
				}
				
			},
            /**
             * 添加绑定
             */
            addBind: function (e, d, v) {
            	var me = this;
                me.data.bind_list.push({
                    groupId: d.groupId,
                    groupName: d.groupName,
                    remarks: d.remarks
                });
                me.data.no_binds = false;
                var arr_ids = [];
                for(var i=0;i<me.data.bind_list.length;i++){
                	var str = me.data.bind_list[i].groupId;
                	arr_ids.push(str);
                };
                me.data.ids = arr_ids.join(",");
                
                me.data.grp_list.forEach(function (item) {
                	me.data.bind_list.forEach(function (i) {
	                    if (item.groupId == i.groupId) {
	                        item.$set('is_bind',true);
	                        return;
	                    }
	                });
                });
            },
            /**
             * 移除绑定
             */
            removeBind: function (e, d, v) {
                var me = this;
                me.data.bind_list.forEach(function (item, index) {
                    if (item.groupId === d.groupId) {
                        me.data.bind_list.splice(index, 1);
                        return;
                    }
                });
                me.data.no_binds = !me.data.bind_list.length;
                me.data.grp_list.forEach(function (item) {
                    if (item.groupId === d.groupId) {
                        item.is_bind = false;
                    }
                });
                var arr_ids = [];
                for(var i=0;i<me.data.bind_list.length;i++){
                	var str = me.data.bind_list[i].groupId;
                	arr_ids.push(str);
                };
                me.data.ids = arr_ids.join(",");
                if(me.data.ids == ''){
                	me.data.ids = ',';
                }
            },
            /**
             * 确认添加
             */
            submit: function (e, d, v) {
                var me = this;
                if(me.data.type == 1){
                	var mobile = /^[1][3,4,5,7,8][0-9]{9}$/;
	                if (!mobile.test(d.mobile)) {
	                	me.data.tips_tit = '手机号格不正确';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
	                   	return;
	                }
	                // 添加的时候，不能为空
	                if (d.user_pwd === '') {
	                	me.data.tips_tit = '密码不能为空';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
	                    return;
	                }
	                if (d.user_pwd !== d.user_pwd_again) {
	                	me.data.tips_tit = '密码不一致';
						me.data.icon_tip = 1;
						me.data.tip_open = true;
						setTimeout(function(){ me.data.tip_open = false; },1500);
	                    return;
	                }
	                DD.request({
						url:DATAURL + '/back/auth/user/addManager',
						params: {
							userName:d.mobile,
							userPwd:d.user_pwd
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
								me.data.user_id = r.result.userId;
								DD.request({
									url:DATAURL + '/back/auth/user/updGroup',
									params: {
										userId:me.data.user_id,
										ids:me.data.ids
									},
									reqType:'POST',
									type:'json',
									async:true,
									/*设置1秒超时*/
									timeout:2000,
									/*请求成功回调*/
									successFunc:function(r){
										me.data.tips_tit = '操作成功';
										me.data.icon_tip = 2;
										me.data.tip_open = true;
										setTimeout(function(){ me.data.tip_open = false; },1500);
										localStorage.clear();
										setTimeout(function(){ DD.Router.start('/route/auth/admin_list/admin_list/')},1500);
									},
									/*请求失败回调*/
									errorFunc:function(r){
										
									},
									/*请求超时回调*/
									timeoutFunc:function(r){
										console.log("系统超时");
									}
								});
							}else{
								alert('操作失败')
							}
						},
						/*请求失败回调*/
						errorFunc:function(r){
							
						},
						/*请求超时回调*/
						timeoutFunc:function(r){
							console.log("系统超时");
						}
					});
                }else{
                	if(me.data.ids==''){
                		me.data.ids=',';
                		DD.request({
							url:DATAURL + '/back/auth/user/updGroup',
							params: {
								userId:me.data.user_id,
								ids:me.data.ids
							},
							reqType:'POST',
							type:'json',
							async:true,
							/*设置1秒超时*/
							timeout:2000,
							/*请求成功回调*/
							successFunc:function(r){
								me.data.tips_tit = '操作成功';
								me.data.icon_tip = 2;
								me.data.tip_open = true;
								setTimeout(function(){ me.data.tip_open = false; },1500);
								localStorage.clear();
								setTimeout(function(){ DD.Router.start('/route/auth/admin_list/admin_list/')},1500);
								
							},
							/*请求失败回调*/
							errorFunc:function(r){
								
							},
							/*请求超时回调*/
							timeoutFunc:function(r){
								console.log("系统超时");
							}
						});
                	}else{
                		DD.request({
							url:DATAURL + '/back/auth/user/updGroup',
							params: {
								userId:me.data.user_id,
								ids:me.data.ids
							},
							reqType:'POST',
							type:'json',
							async:true,
							/*设置1秒超时*/
							timeout:2000,
							/*请求成功回调*/
							successFunc:function(r){
								me.data.tips_tit = '操作成功';
								me.data.icon_tip = 2;
								me.data.tip_open = true;
								setTimeout(function(){ me.data.tip_open = false; },1500);
								localStorage.clear();
								setTimeout(function(){ DD.Router.start('/route/auth/admin_list/admin_list/')},1500);
//								DD.Router.start('/route/auth/admin_list/admin_list/');
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
                }
            }
        }
    })
}());