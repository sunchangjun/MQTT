(function () {
    console.log(111);
    DD.createModule({
        name : 'mSetting',
        delayInit : true,
        templateUrl : BASEHTML + '/setting/setting.html',
        data : {

        },
        onReceive : function(module, data) {
            var me = this;
        },
        onBeforeFirstRender : function() {
            var me=this;
            if (window.MX) {
                MX.invoke("MXServerUtil", "allDevices", {}, function(r) {
                    r = r.result;
                    r = JSON.parse(r);
                    if(r.allDevices)
                        me.data.serialPort.allDevices=r.allDevices;
                    if(r.devicePort)
                        me.data.serialPort.devicePort=r.devicePort;
                });
            }
        }
    });
}());
