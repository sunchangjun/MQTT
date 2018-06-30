(function () {
    DD.createModule({			/*售货机*/
        name:"msell",
        el:'sell',
        delayInit : false,
       	templateUrl : BASEHTML + '/commercialMachine/sellGoods.html',
        data:{
        	open:false,
        	tip_open:false,
        	pop:false,
        	tip_pop:false,
        	goods_list:[
				{id:1,coding:'4564798713',region:'一区',line:'1线',site:'DASJKD',god:'100%',hard:'正常',device:'4567',sta_time:'2018/09/12',end_time:'2018/10/12'},
				{id:1,coding:'4564798713',region:'一区',line:'1线',site:'DASJKD',god:'100%',hard:'正常',device:'4567',sta_time:'2018/09/12',end_time:'2018/10/12'}
			],
			pages:1,					/*默认页数*/
			sum_pages:10,				/*总页数*/
        },
        onBeforeFirstRender:function () {
           
        },
        methods:{
        	see_det:function(){
				this.data.open = true;
			},
			close_det:function(){
				this.data.open = false;
			},
			pop_open:function(){
				var me = this;
				me.data.pop = true;
			},
			cheak_sta:function(e,data,view){
				var me = this;
				data.$set('selected',true);
//				data.$set('selected',!data.selected);		/*给data添加一个判断字段*/
//				me.data.goods_change.forEach(function(r,i){			
//					if(r.commodityId!=data.commodityId){
//						r.$set('selected',false);
//					}
//				});
			},
			cancle:function(){			/*取消操作*/
				var me = this;
				me.data.pop = false;
			},
			sure:function(){			/*确定操作*/
				var me = this;
				me.data.pop = false;
				me.data.tip_pop = true;
				setTimeout(function(){ me.data.tip_pop = false; },1000);
			},
			show_sle:function(e,data,view){
				var me = this;
			},
			up_page:function(){						/*列表上一页*/
				var me = this;
				if(0<me.data.pages-1){
					me.data.pages = me.data.pages - 1;
				}else{
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1000);
				}
			},
			down_page:function(){					/*列表下一页*/
				var me = this;
				if(me.data.pages+1<=me.data.sum_pages){
					me.data.pages = me.data.pages + 1;
				}else{
					me.data.tip_open = true;
					setTimeout(function(){ me.data.tip_open = false; },1000);
				}
				
			},
        }
    })
})()