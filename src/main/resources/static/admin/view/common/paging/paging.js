/**
 * Created by xll on 2018/4/3.
 * 分页共用
 */
(function () {
    DD.defineModule({
        className: 'dm_paging',
        templateUrl: BASEHTML + '/common/paging/paging.html',
        onBeforeFirstRender: function () {
            this.data.page = 1;
            this.data.to_page = 1;
        },
        methods: {
            /**
             * 首页
             */
            toFirst: function () {
                if(!this.data.is_change_page) {   //前往其他页数时，判断是否处于修改状态，若是修改，校验是否通过，通过前往，没有通过，不前往
                    if(this.data.page!==1){
                        this.data.page = 1;
                        this.module.send(this.module.parent.name,{page: this.data.page});
                        // this.module.methodFactory.methods.updatePage.call(this);
                    }
                }
            },
            /**
             * 尾页
             */
            toLast: function () {
                if(!this.data.change_page){
                    if(this.data.page!==this.data.all_page){
                        this.data.page = this.data.all_page;
                        this.module.send(this.module.parent.name,{page: this.data.page});
                        // this.module.methodFactory.methods.updatePage.call(this);
                    }
                }

            },
            /**
             * 上一页
             */
            toPrev: function () {
                if(!this.data.change_page){
                    if(this.data.page>1){
                        this.data.page--;
                        this.module.send(this.module.parent.name,{page: this.data.page});
                        // this.module.methodFactory.methods.updatePage.call(this);
                    }
                }

            },
            /**
             * 下一页
             */
            toNext: function () {
                if(!this.data.change_page){
                    if(this.data.page<this.data.all_page){
                        this.data.page++;
                        this.module.send(this.module.parent.name,{page: this.data.page});
                        // this.module.methodFactory.methods.updatePage.call(this);
                    }
                }
            },
            /**
             * 前往某页
             */
            goTo: function () {
                if(!this.data.change_page){
                    if(this.data.page !== this.data.to_page && this.data.to_page>=1 && this.data.to_page<=this.data.all_page){
                        this.data.page = this.data.to_page;
                        this.module.send(this.module.parent.name,{page: this.data.page});
                        // this.module.methodFactory.methods.updatePage.call(this);
                    }
                }
            }
        }
    });
}())