(function () {
    DD.createModule({
        name:"mHomePage",
        delayInit : false,
        templateUrl : BASEHTML + '/homePage/homePage.html',
        requires : [
            BASEJS + '/setting/setting.js'
        ],
        onFirstRender:function () {
            console.log(222);
        }
    })
}())