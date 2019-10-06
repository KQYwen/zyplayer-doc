var URL = {
    userLogin: '/login',
    userLogout: '/logout',
    getSelfUserInfo: '/user/info/selfInfo',
    getUserBaseInfo: '/zyplayer-doc-wiki/common/user/base',

    getEditorData: '/zyplayer-doc-db/doc-db/getEditorData',
    datasourceList: '/zyplayer-doc-db/doc-db/getDataSourceList',
    databaseList: '/zyplayer-doc-db/doc-db/getDatabaseList',
    tableList: '/zyplayer-doc-db/doc-db/getTableList',
    tableColumnList: '/zyplayer-doc-db/doc-db/getTableColumnList',
    tableStatus: '/zyplayer-doc-db/doc-db/getTableStatus',
    tableAndColumnBySearch: '/zyplayer-doc-db/doc-db/getTableAndColumnBySearch',
    updateTableDesc: '/zyplayer-doc-db/doc-db/updateTableDesc',
    updateTableColumnDesc: '/zyplayer-doc-db/doc-db/updateTableColumnDesc',

    manageDatasourceList: '/zyplayer-doc-db/datasource/list',
    manageUpdateDatasource: '/zyplayer-doc-db/datasource/update',

    executeSql: '/zyplayer-doc-db/executor/execute',
    executeSqlCancel: '/zyplayer-doc-db/executor/cancel',
    updateFavorite: '/zyplayer-doc-db/executor/favorite/add',
    favoriteList: '/zyplayer-doc-db/executor/favorite/list',
    historyList: '/zyplayer-doc-db/executor/history/list',

    transferStart: '/zyplayer-doc-db/transfer/start',
    transferCancel: '/zyplayer-doc-db/transfer/cancel',
    transferList: '/zyplayer-doc-db/transfer/list',
    transferDetail: '/zyplayer-doc-db/transfer/detail',
    transferUpdate: '/zyplayer-doc-db/transfer/update',
    transferSqlColumns: '/zyplayer-doc-db/transfer/sqlColumns',

    assignDbUserAuth: '/zyplayer-doc-db/auth/assign',
    dbUserAuthList: '/zyplayer-doc-db/auth/list',

    systemUpgradeInfo: '/system/info/upgrade',
};

var URL1 = {};

export default {
    URL, URL1
};




