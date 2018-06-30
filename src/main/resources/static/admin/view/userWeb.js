DD.createModule({
	name : 'mHome',
	delayInit : false,
	requires : [
    ],
	templateUrl : BASEHTML + '/userWeb.html',
    data: {
        is_login: false,
        rows: [{
            title: '售卖机管理平台',
            class: 'vendingMachine',
            icon: '../../assets/css/imgs/company-logo.png',
            sub_menu_height: 0
        },{
            title: '首页',
            path: '/route/home',
            active: true,
            class: 'home',
            icon: '../../assets/css/imgs/edi_ico.png',
            menu_cheak:true,
            sub_menu_height: 0
        },{
            title: '售货机',
            active: false,
            class: 'home',
            menu_cheak:false,
            icon: '../../assets/css/imgs/edi_ico.png',
            sub_menu_height: 0,
            Submenu:[{
                two_name:"售货机管理",
                path: '/route/commercialMachine/machineManage',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            },{
                two_name:"售货机远程监控",
                path: '/route/commercialMachine/sellGoods',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            }]
        },{
            title: '商品',
            active: false,
            class: 'home',
            menu_cheak:false,
            sub_menu_height: 0,
            Submenu:[{
                two_name:"商品信息管理",
                path: '/route/commodity/commercialMachine',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            },{
                two_name:"商品分类管理",
                path: '/route/commodity/commercialClass',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            },{
                two_name:"饮料种类",
                path: '/route/commodity/drinkClass',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            }]
        },{
            title: '运营',
            active: false,
            class: 'home',
            menu_cheak:false,
            sub_menu_height: 0,
            Submenu:[{
                two_name:"售货机商品管理",
                path: '/route/operation/sellManage',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            },{
                two_name:"售货机库存监控",
                path: '/route/operation/sellMonitor',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            },{
                two_name:"售货机补货记录",
                path: '/route/operation/sellReplenishment',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            },{
                two_name:"支付管理",
                path: '/route/commercialMachine',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            }]
        },{
            title: '权限管理',
            active: false,
            class: 'home',
            menu_cheak:false,
            sub_menu_height: 0,
            Submenu:[{
                two_name:"管理员列表",
                path: '/route/auth/admin_list/admin_list',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            },{
                two_name:"权限管理",
                path: '/route/auth/authority/authority',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            },{
                two_name:"角色管理",
                path: '/route/auth/group/group',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            },{
                two_name:"菜单管理",
                path: '/route/auth/menu/menu',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
        	},{
                two_name:"资源管理",
                path: '/route/auth/resource/resource',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
        	},{
                two_name:"角色权限管理",
                path: '/route/auth/group_authority/group_authority',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
        	},{
                two_name:"用户角色配置",
                path: '/route/auth/group_member/group_member',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
        	},{
                two_name:"角色菜单管理",
                path: '/route/auth/group_menu/group_menu',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
        	},{
                two_name:"资源权限管理",
                path: '/route/auth/resource_authority/resource_authority',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
        	}]
        },{
            title: '告警',
            active: false,
            class: 'home',
            menu_cheak:false,
            sub_menu_height: 0,
            Submenu:[{
                two_name:"售货机商品管理",
                path: '/route/alerts/alerts',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            }]
        },{
            title: '客服',
            active: false,
            class: 'home',
            menu_cheak:false,
            sub_menu_height: 0,
            Submenu:[{
                two_name:"订单异常处理",
                path: '/route/serve/order',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            }]
        },{
            title: '广告',
            active: false,
            class: 'home',
            menu_cheak:false,
            sub_menu_height: 0,
            Submenu:[{
                two_name:"广告位",
                path: '/route/advertising/advertising',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            }]
        },{
            title: '报表',
            active: false,
            class: 'home',
            menu_cheak:false,
            sub_menu_height: 0,
            Submenu:[{
                two_name:"营业统计",
                path: '/route/commercialMachine',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            },{
                two_name:"交易明细",
                path: '/route/commercialMachine',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            },{
                two_name:"商品销量排行",
                path: '/route/commercialMachine',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            },{
                two_name:"售货机库异动",
                path: '/route/commercialMachine',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            },{
                two_name:"点位销量情况",
                path: '/route/commercialMachine',
                class:"two_menu",
                reveal:false,
                menu_cheak2:false
            }]
        }]
    },
    onBeforeFirstRender:function () {
    	var me = this;
    	// 获取当前路由，设置菜单选择
    	var url = location.href;
    	var i = url.indexOf("/route/");
    	if(i > -1) {
    		var route = url.substring(i);
    		me.data.rows.forEach(function(item) {
				item.menu_cheak = false;
    			if(item.Submenu) {
    				item.Submenu.forEach(function(item2) {
						item2.menu_cheak2 = false;
    					if(item2.path == route) {
    						item2.menu_cheak2 = true;
    						item.menu_cheak = true;
    					}
    				});
    			}
    		});
    	}
    },
    onFirstRender:function () {
    },
    methods:{
        HomeSwitcher:function (e,data,vivw) {
            var me = this;
            me.data.rows.forEach(function (item) {
            	item.menu_cheak = false;
    			item.sub_menu_height = 0;
            });
            data.menu_cheak = true;
            var len = 0;
			if(data.Submenu && data.Submenu.length)
				len = data.Submenu.length;
            data.sub_menu_height = len * 55;
            if(data.path){
                DD.Router.start(data.path);
            }
        },
        twoMenu_s:function (e,data,vivw) {
        	var me =this;
        	me.data.rows.forEach(function (item) {
            	if(item !== data){
                    if(item.Submenu){
                        item.Submenu.forEach(function (it) {
                            data.menu_cheak2=true;
		                	if(it.two_name!=data.two_name){
								it.menu_cheak2=false;
							}
                        })
                    }
                }
            });
            e.stopPropagation();
            DD.Router.start(data.path);
        }
	}
});