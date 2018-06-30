(function () {
    DD.createRoute([ {
        path : '/route',
        module : 'mHome',
        routes:[{
            path : '/home',						
            module : 'mHomePage'
        },{
            path : '/commodity/commercialMachine',
            module : 'mCommercialMachine'					/*商品信息*/
        },{
            path : '/commodity/commercialClass',	/*商品分类*/
            module : 'mcommercialClass'
        },{
            path : '/commodity/drinkClass',	/*饮料分类*/
            module : 'drink'
        },{
            path : '/commercialMachine/sellGoods',			/*售货机远程监控*/
            module : 'msell'
        },{
            path : '/commercialMachine/machineManage',			/*售货机管理*/
            module : 'machine'
        },{
            path : '/operation/sellManage',			/*售货机商品管理*/
            module : 'msellmanage'
        },{
            path : '/operation/sellMonitor',			/*售货机库存监控*/
            module : 'msellMonitor'
        },{
            path : '/operation/sellReplenishment',			/*售货机补货*/
            module : 'msellReplenishment'
        },{
            path : '/alerts/alerts',						/*报警*/
            module : 'alert'
        },{
            path : '/serve/order',						/*客服*/
            module : 'morder'
        },{
            path : '/advertising/advertising',						/*广告*/
            module : 'advertising'
        },{
            path : '/auth/admin_list/admin_list',						/*管理员列表*/
            module : 'm_admin_list'
        },{
            path : '/auth/admin_list/add_admin/add_admin',						/*添加管理员*/
            module : 'm_add_admin'
        },{
            path : '/auth/authority/authority',						/*权限管理*/
            module : 'm_authority'
        },{
            path : '/auth/group/group',						/*角色管理*/
            module : 'm_group'
        },{
            path : '/auth/menu/menu',						/*菜单管理*/
            module : 'm_menu'
        },{
            path : '/auth/resource/resource',						/*资源管理*/
            module : 'm_resource'
        },{
            path : '/auth/group_authority/group_authority',						/*角色组权限*/
            module : 'm_group_authority'
        },{
            path : '/auth/group_member/group_member',					
            module : 'm_group_member'
        },{
            path : '/auth/group_menu/group_menu',					
            module : 'm_group_menu'
        },{
            path : '/auth/resource_authority/resource_authority',				/*资源权限*/		
            module : 'm_resource_authority'
        },{
            path : '/serve',
            module : 'mServe'
        },{
            path : '/setting',
            module : 'mSetting'
        },{
            path : '/statement',
            module : 'mStatement'
        }]
    }, {
        path : '/setting',
        module : 'mSetting'
    },]);
}())