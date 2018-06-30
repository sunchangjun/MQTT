/**
 * 添加管理员
 */
(function () {
    DD.createModule({
        name: "m_admin_list",
        delayInit: true,
        el:'.admin-list',
		templateUrl : BASEHTML + '/auth/admin_list/admin_list.html',
        data: {
            page: 1,
            rows: 10,
            searchName: '',
            no_data: true,
            open:false,
            user_list:[],
            total:0,
            all_page:'',
            to_page: 1,
            tip_open:false,
            tips_tit:'',
            icon_tip:'',
        },
        onBeforeFirstRender: function () {
            var me = this;
            
            me.data.searchName = '';
            me.data.no_data = true;
            var params = {
            	page:me.data.page,
            	rows:me.data.rows
            };
			me.module.methodFactory.methods.loadData.call(me,params);
			localStorage.clear();
        },
        methods: {
            /**
             * 更新页面
             */
            loadData: function(params) {
				var me = this;
	            DD.request({
					url:DATAURL + '/back/auth/user/getManagerList',
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
			goTo:function(){						/*指定跳转*/
				var me = this;
				if(me.data.to_page<=me.data.all_page){
					var params = {
						userName:me.data.searchName,
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
						userName:me.data.searchName,
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
						userName:me.data.searchName,
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
//          /**
//           * 搜索管理员
//           */
            search: function(e, data, view) {			/*搜索*/
				var me = this;
				var params = {
					userName:me.data.searchName,
					page: 1,
					rows: 10
				};
        		me.module.methodFactory.methods.loadData.call(me, params);
			},
//          /**
//           * 添加管理员
//           */
            addItem: function (e, d, v) {
            	localStorage.setItem("type", 1);
            	DD.Router.start('/route/auth/admin_list/add_admin/add_admin');
            },
             /**
	          * 编辑管理员
	         */
            editItem: function (e, d, v) {
            	var me =this;
            	localStorage.setItem("user_id", d.userId);
            	localStorage.setItem("userName", d.userName);
            	localStorage.setItem("userPwd", d.userPwd);
            	console.log(d);
            	localStorage.setItem("type", 2);
            	DD.Router.start('/route/auth/admin_list/add_admin/add_admin');
            },

//          /**
//           * 冻结账户
//           */
//          frozen: function (e, d, v) {
//              var me = this;
//              LoadDataCommon.getList(me, '/user/user_frozen.action', {
//                  user_id: d.user_id
//              }, function (r) {
//                  me.module.send('m_alert', {
//                      show: true,
//                      data: d.user_name + "已冻结"
//                  });
//                  d.enabled = 0;
//              })
//          },
//          /**
//           * 解冻
//           */
//          unfrozen: function (e, d, v) {
//              var me = this;
//              LoadDataCommon.getList(me, '/user/user_unfrozen.action', {
//                  user_id: d.user_id
//              }, function (r) {
//                  me.module.send('m_alert', {
//                      show: true,
//                      data: d.user_name + "已解冻"
//                  });
//                  d.enabled = 1;
//              })
//          }
        }
    })
}());