/**
 * nodom 相应代码重写
 */

//设置路由默认进入和离开
DD.merge(DD.Router,{
	defaultEnter:function(){},
	defaultLeave:function(){}
});
DD.requestConfig={};

DD.request = function(config){
	var req = new XMLHttpRequest();
    if(DD.isEmpty(config.url)){
        throw DD.Error.handle('invoke','DD.request',"config.url",'string');
    }
    if(config.params && !DD.isObject(config.params)){
        throw DD.Error.handle('invoke','DD.request',"config.params",'object');
    }
    // appmx 读取url 优先从数据库读，如果数据库不存在，则从文件中读
    if(window.MX && config.url.indexOf("http://") !== 0 && config.url.indexOf("https://") !== 0){
        MX.invoke("MXFileSystem","readAssetFile",{fileName:config.url},function(r){
            r = r.result;
            if(config.type === 'json'){
                r = JSON.parse(r);
            }
            
            if(DD.isFunction(config.successFunc)){
                config.successFunc(r);
            }
        });
        return;
    }else if(config.url.indexOf('.action') !== -1){  // 针对数据部分，仅在app中使用
        var isConnectNetwork = localStorage.getItem("isConnectNetwork");
        if(isConnectNetwork == "false") {
            if(DD.Router.current) {
                DD.Router.current.module.send('mLoading', {
                    show: false
                });
            }
            return;
        }
        config.params = config.params || {};
        DD.extend(config.params,DD.requestConfig);
        config.params.$rand = Math.random();
    }else{
    	var ua = window.navigator.userAgent.toLowerCase();
        if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        	config.params = config.params || {};
            config.params.$rand = Math.random();
        }
    }
    var async = config.async===false?false:true;
    // async = true;
    // 设置mime
    var mime = config.type || 'text';
    switch(mime){
        case 'html':
            req.overrideMimeType('text/html;charset=utf-8');
            break;
        case 'json':
            req.overrideMimeType('text/javascript;charset=utf-8');
            break;
        case 'js':
            req.overrideMimeType('text/javascript;charset=utf-8');
            break;
        case 'xml':
            req.overrideMimeType('text/xml;charset=utf-8');
            break;
        default:
            req.overrideMimeType('text/plain;charset=utf-8');
    }

    /**
	 * 回调函数处理
	 */
    // 成功函数
    if(typeof config.successFunc === 'function'){
        req.onload = function(e){
            switch(req.status){
                case 200:
                    var r = req.responseText;
                    switch(config.type){
                        case 'json':
                            try{
                                r = JSON.parse(r);    
                            }catch(e){
                                r = {
                                    success:false,
                                    result:{"errmsg":"未知错误"}
                                }
                            }
                            break;

                    }
                    // 为app使用统一错误提示
                    if(r.success !== undefined && r.success === false){}
                    config.successFunc.call(req,r);

                    break; 
                default:    // 服务器异常
                    if(DD.isFunction(config.errorFunc)){
                        config.errorFunc.call(req,req.status);
                    }                
            }
            
        }
    }

    // 异常函数
    if(DD.isFunction(config.errorFunc)){
        req.onerror = config.errorFunc;
    }

    // 超时函数
    if(DD.isFunction(config.timeoutFunc)){
        req.ontimeout = config.timeoutFunc;
    }

    var reqType = config.reqType||'GET';
    var url = config.url;
    // 默认60秒
    config.timeout = config.timeout || 60000;
    // 发送请求
    switch(reqType){
        case 'GET':
            // 参数
            var pa;
    
            if(DD.isObject(config.params)){
                var ar = [];
                DD.getOwnProps(config.params).forEach(function(key){
                    ar.push(key + '=' + config.params[key]);
                });
                pa = ar.join('&');
            }
            if(pa !== undefined){
                if(url.indexOf('?') !== -1){
                    url += '&' + pa;
                }else{
                    url += '?' + pa;
                }
            }
            req.open(reqType,url,async,config.user,config.pwd);
            if(async){
                req.timeout = config.timeout;
            }
            req.send(null);
            break;
        case 'POST':
            var fd = new FormData();
            for(var o in config.params){
                fd.append(o,config.params[o]);
            }
            req.open(reqType,url,async,config.user,config.pwd);
            req.timeout = config.timeout;
            req.send(fd);
            break;
    }
}

			