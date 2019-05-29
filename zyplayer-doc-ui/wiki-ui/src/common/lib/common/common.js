import Qs from 'qs'
import global from '../../config/global'

export default {
    data: {
        accessToken: '',
    },
    setAccessToken: function (token) {
        this.data.accessToken = token;
    },
    getAccessToken: function () {
        if (!this.data.accessToken) {
            var arr, reg = new RegExp("(^| )accessToken=([^;]*)(;|$)");
            if (arr = document.cookie.match(reg)) {
                this.data.accessToken = unescape(arr[2]);
            }
        }
        return this.data.accessToken;
    },
    validateResult: function (res, callback) {
        if (res.data.errCode == 400) {
            global.vue.$message('请先登录');
            global.vue.$router.push("/user/login");
        } else if (res.data.errCode == 402) {
            global.vue.$router.push("/common/noAuth");
        } else if (res.data.errCode !== 200) {
            global.vue.$message(res.data.errMsg || "未知错误");
        } else {
            if (typeof callback == 'function') {
                callback(res.data);
            }
        }
    },
    post: function (url, param, callback) {
        param = param || {};
        param.accessToken = this.getAccessToken();
        global.vue.axios({
            method: "post",
            url: url,
            headers: {'Content-type': 'application/x-www-form-urlencoded'},
            data: Qs.stringify(param),
        }).then((res) => {
            console.log("ok", res);
            this.validateResult(res, callback);
        }).catch((res) => {
            console.log("error", res);
            this.validateResult(res);
        });
    },
}

