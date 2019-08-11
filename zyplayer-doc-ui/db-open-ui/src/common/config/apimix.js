import apilist from './apilist'

var href = window.location.href;

var _fn = {
    href: href,
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
    apilist1, apilist2
};

