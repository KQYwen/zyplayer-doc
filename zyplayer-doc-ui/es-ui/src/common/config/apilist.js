var URL = {
    userLogin: '/login',
    userLogout: '/logout',
    getSelfUserInfo: '/user/info/selfInfo',

    datasourceList: '/zyplayer-doc-db/doc-db/getDataSourceList',
    databaseList: '/zyplayer-doc-db/doc-db/getDatabaseList',
    tableList: '/zyplayer-doc-db/doc-db/getTableList',
    tableColumnList: '/zyplayer-doc-db/doc-db/getTableColumnList',
    tableAndColumnBySearch: '/zyplayer-doc-db/doc-db/getTableAndColumnBySearch',
    updateTableDesc: '/zyplayer-doc-db/doc-db/updateTableDesc',
    updateTableColumnDesc: '/zyplayer-doc-db/doc-db/updateTableColumnDesc',

    manageDatasourceList: '/zyplayer-doc-es/datasource/list',
    manageUpdateDatasource: '/zyplayer-doc-es/datasource/update',

    esMappings: '/zyplayer-doc-es/es-mapping/mappings',
    esExecuter: '/zyplayer-doc-es/es-mapping/execute',

    systemUpgradeInfo: '/system/info/upgrade',
};

var URL1 = {};

export default {
    URL, URL1
};




