import apilist from './apilist'

var href = window.location.href;

var _fn = {
    href: href,
    // 本地启动时使用本地接口调试
    // HOST: 'http://local.zyplayer.com:8083/zyplayer-doc-manage',
    // HOST1: 'http://local.zyplayer.com:8083/zyplayer-doc-manage',
    // 也可以直接使用线上的服务调试
    // HOST: 'http://doc.zyplayer.com/zyplayer-doc-manage',
    // HOST1: 'http://doc.zyplayer.com/zyplayer-doc-manage',
    // 打包时使用下面这两行，文件就放在根目录下，所以当前路劲就好
    HOST: './',
    HOST1: './',

    mixUrl: function (host, url) {
        var p;
        if (!host || !url || _fn.isEmptyObject(url)) {
            return;
        }
        url.HOST = host;
        for (p in url) {
            if (url[p].indexOf('http') == -1) {
                url[p] = host + url[p];
            }
        }
        return url;
    },
    //判断是否空对象
    isEmptyObject: function (obj) { //判断空对象
        if (typeof obj === "object" && !(obj instanceof Array)) {
            var hasProp = false;
            for (var prop in obj) {
                hasProp = true;
                break;
            }
            if (hasProp) {
                return false;
            }
            return true;
        }
    }
};

var apilist1 = _fn.mixUrl(_fn.HOST, apilist.URL);
var apilist2 = _fn.mixUrl(_fn.HOST1, apilist.URL1);

export default {
    apilist1, apilist2, host: _fn.HOST
};

