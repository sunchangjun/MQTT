/**
 * Created by xll on 2017/12/8.
 */
var token = localStorage.getItem("token");
var login = sessionStorage.getItem('login');
 var BASEURL = '/front/admin';
/*var BASEURL = '/box_admin_ui';*/

var BASEJS = BASEURL + '/view';
var BASEHTML = BASEURL + '/view';
var BASEJSON = BASEURL + '/json';
var BASECSS = BASEURL + '/css';
// 基础数据路径
var DATAURL1 = '/back';
var DATAURL2 = '/front';
//var DATAURL = 'http://box.tiand99.net:55';
var DATAURL = 'http://192.168.6.108:55';
//var DATAURL = '/back';
var COMPANYLOGOIMG = 'http://tdgou.oss-cn-qingdao.aliyuncs.com/notchange/default-company-logo.png';
var USERHEADERIMG = 'http://tdgou.oss-cn-qingdao.aliyuncs.com/notchange/default-head.jpg';
var COMAPNYSHOWIMG = 'http://tdgou.oss-cn-qingdao.aliyuncs.com/notchange/company_default_img.jpg';
document.onselectstart = function() {
	return false;
};

(function() {
	var Model = function() {
		var me = this;
	};
	Model.prototype.selectPort = function(data) {
		DD.Router.start("/setting");
		DD.Router.current.module.send("mSetting", data);
	};
	DD.Port = new Model();
}());

(function() {
	function setFontSize() {
		var width = window.innerWidth;
		// 得到默认fontsize
		var doc = DD.get('html');
		var fontSize = parseFloat(DD.css(doc, 'fontSize')) || 16;
		var x = width * 16 / (20 * fontSize);
		doc.style.fontSize = x + "px";
	}
	setFontSize();
	DD.createModule({
		name : 'app',
		el : '#app',
		root : true,
        requires : [								/*先于模块文件加载的文件集合cs/js*/
        	BASEJS + '/userWeb.js',
            BASEURL + '/router/router.js',
    		BASEJS + '/homePage/homePage.js',
    		BASEJS + '/commercialMachine/sellGoods.js',			/*售货机*/
    		BASEJS + '/commercialMachine/machineManage.js',		/*售货机管理*/
            BASEJS + '/commodity/commercialMachine.js',		/*商品*/
           	BASEJS + '/commodity/commercialClass.js',		/*分类*/
            BASEJS + '/commodity/drinkClass.js',		/*饮料*/
    		BASEJS + '/operation/sellManage.js',		/*售货机商品*/
    		BASEJS + '/operation/sellMonitor.js',		/*售货机监控*/
    		BASEJS + '/operation/sellReplenishment.js',	/*售货机补货*/
            BASEJS + '/alerts/alerts.js',	
            BASEJS + '/serve/order.js',	
            BASEJS + '/advertising/advertising.js',		/*广告*/
            BASEJS + '/auth/admin_list/admin_list.js',
            BASEJS + '/auth/admin_list/add_admin/add_admin.js',
            BASEJS + '/auth/authority/authority.js',
            BASEJS + '/auth/group/group.js',
            BASEJS + '/auth/menu/menu.js',
            BASEJS + '/auth/resource/resource.js',
            BASEJS + '/auth/group_authority/group_authority.js',
            BASEJS + '/common/paging/paging.js',
            BASEJS + '/auth/group_member/group_member.js',
            BASEJS + '/auth/group_menu/group_menu.js',
            BASEJS + '/auth/resource_authority/resource_authority.js',
		],
		data : {
			active : true
		},
		onFirstRender: function() {
			//页面调整
            var hash = location.hash;
            if(hash){
            	hash = hash.substr(1);
            	DD.Router.start("/route/" + hash);
            } else {
            	DD.Router.start("/route");
            }
		}
	});
}());