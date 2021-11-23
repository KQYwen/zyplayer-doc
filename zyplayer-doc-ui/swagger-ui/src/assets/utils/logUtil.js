import {message} from 'ant-design-vue';

export default {
    /**
     * 输出一个log到console
     * @param code 错误码，用于定位包错行
     * @param type 参数类型
     * @param parameter 参数对象
     */
    log(code, type, parameter) {
        console.log(code + '-遇到未处理的类型，请联系开发人员修改：' + type, parameter);
    },
    /**
     * 输出一个log到console，并且message输出信息，用于比较严重必须要处理的异常使用
     * @param code 错误码，用于定位包错行
     * @param type 参数类型
     * @param parameter 参数对象
     */
    logMessage(code, type, parameter) {
        console.log(code + '-遇到未处理的类型，请联系开发人员修改：' + type, parameter);
        message.error(code + '-遇到未处理的类型，请联系开发人员修改：' + type);
    }
}
