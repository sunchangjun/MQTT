(function(){
	DD.createModule({			/*售货机补货*/
        name:"msellReplenishment",
        el:'sellrep',
        delayInit : false,
        templateUrl : BASEHTML + '/operation/sellReplenishment.html',
        data:{
        	open:false,
        	tip_open:false,
			goods_list:[
				{id:1,det:'明细',region:'一区',line:'1线',point:'无',ser:'SADSAD',plan:'计划',complete_time:'2018/09/12'},
				{id:2,det:'明细',region:'一区',line:'1线',point:'无',ser:'SADSAD',plan:'计划',complete_time:'2018/09/12'},
			],
			pages:1,					/*默认页数*/
			sum_pages:10,				/*总页数*/
			sum_data:'',				/*总数据*/
		},
        onBeforeFirstRender:function () {
            laydate.render({
				  elem: '#test1',
				  range: true
				});
        },
        methods:{
			see_det:function(){
				this.data.open = true;
			},
			close_det:function(){
				this.data.open = false;
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
			time_ipt:function(){
				laydate.render({
				  elem: '#test1',
				  trigger:'click',
				  range: true
				});
			},
		}
   })
})()