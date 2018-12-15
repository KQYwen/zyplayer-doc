/**
 * 所有需要iframe页面调用的接口必须定义在这里
 * 通过getExport()来吊起父页面的这些接口
 */
var exports = {
    regeneratePathTree: function(){
        // 定义在mg-ui.js，用于重新生成文档树目录
        regeneratePathTree();
    },
    updateUserSettings: function(setting){
        // 定义在mg-ui.js，用于更新用户设置
        updateUserSettings(setting);
    },
    exportDocument: function(){
        // 定义在mg-ui.js，用于打开导出文档框
        exportDocument();
    },
    updateGlobalParam: function(param){
        // 更新调试的全局参数变量
        debugGlobalParam = param;
    }
};