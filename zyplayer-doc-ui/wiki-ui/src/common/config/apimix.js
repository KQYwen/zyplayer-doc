import apilist from './apilist'

var href = window.location.href;
var EVT = '';
var _evt = function () {
    if (href.indexOf('https://') > -1) {
        // 测试环境
        if (href.indexOf('https://test') > -1 && href.indexOf('https://test.') == -1) {
            EVT = 'https://test';
            // dev环境
        } else if (href.indexOf('https://dev') > -1 && href.indexOf('https://dev.') == -1) {
            EVT = 'https://dev';
        } else {
            EVT = 'https://'
        }
        return EVT;
    } else {
        // 测试环境
        if (href.indexOf('http://test') > -1 && href.indexOf('http://test.') == -1) {
            EVT = 'http://test';
            // dev环境
        } else if (href.indexOf('http://dev') > -1 && href.indexOf('http://dev.') == -1) {
            EVT = 'http://dev';
        } else {
            EVT = 'http://'
        }
        return EVT;
    }
};

var _fn = {
    href: href,
    HOST: EVT + 'local.zyplayer.com:8083/zyplayer-doc-manage', //这里设置接口域名
    HOST1: EVT + 'local.zyplayer.com:8083', //设置多个接口域名
    mixUrl: function (host, url) {
        var p;
        if (!host || !url || _fn.isEmptyObject(url)) {
            return;
        }
        url.EVT = _evt();
        for (p in url) {
            if (url[p].indexOf('http') == -1) {
                url[p] = url.EVT + host + url[p];
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

