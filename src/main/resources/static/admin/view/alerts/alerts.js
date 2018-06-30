(function () {
    DD.createModule({			/*售货机*/
        name:"alert",
        el:'#alert',
        delayInit : false,
       	templateUrl : BASEHTML + '/alerts/alerts.html',
        data:{
        	tip_open:false,
        	pop:false,
        	re_suc:false,
        	goods_list:[
				{id:1,lev:'2',nx:'货道故障',nr:'1-1-2',sta:'已解除',time:'2018-05-15 12:13:55',sh:'DLA364',dw:'DLA364',fq:'天点一区',xl:'天点一线',mod:'标准6*10*5',bz:''},
				{id:2,lev:'3',nx:'货道故障',nr:'1-1-2',sta:'已解除',time:'2018-05-15 12:13:55',sh:'DLA364',dw:'DLA364',fq:'天点一区',xl:'天点一线',mod:'标准6*10*5',bz:''},
			],
			pages:1,
			sum_pages:10,
			sum_data:100,
        },
        onBeforeFirstRender:function () {
           
        },
        methods:{
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
			remove_sta:function(){
				var me = this;
				me.data.pop = true;
			},
			suc_tip:function(){
				var me = this;
				me.data.pop = false;
				me.data.re_suc = true;
				setTimeout(function(){me.data.re_suc=false;},1000);
			},
			cancle_pop:function(){
				var me = this;
				me.data.pop = false;
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