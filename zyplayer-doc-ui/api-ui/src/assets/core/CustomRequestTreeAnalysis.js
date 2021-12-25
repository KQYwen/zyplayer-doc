const methodArray = ["get", "head", "post", "put", "patch", "delete", "options", "trace"];


/**
 * 按tag分组获取左侧菜单目录树
 * @param customRequest 原始文档信息
 * @param tagPathMap 分组信息{分组名: {url: {...接口信息, path: '', url: '', method: ''}}}
 * @param keywords 过滤关键字
 * @param metaInfo 接口元信息，点击时放入URL的参数
 */
export function getTreeDataForTag(customRequest, keywords, metaInfo) {
    let firstChild = customRequest[0];
    let treeData = getTreeDataChildren(firstChild, keywords, metaInfo, 1);
    return [
        {
            key: 'main',
            title: firstChild.name || '自建API接口文档',
            children: treeData
        }
    ];
}

function getTreeDataChildren(customRequest, keywords, metaInfo, treeIndex) {
    let treeData = [];
    if (!customRequest) {
        return treeData;
    }
    let indexFolder = 1;
    let indexApi = 1;
    if (customRequest.children && customRequest.children.length > 0) {
        customRequest.children.forEach(item => {
            let tempTreeId = treeIndex + "_" + indexFolder + "_" + indexApi;
            let treeChildren = getTreeDataChildren(item, keywords, metaInfo, tempTreeId);
            treeData.push({title: item.name, key: tempTreeId, children: treeChildren});
            indexApi++;
        });
    }
    if (customRequest.apis && customRequest.apis.length > 0) {
        customRequest.apis.forEach(item => {
            let tempTreeId = treeIndex + "_" + indexFolder + "_" + indexApi;
            treeData.push({
                title: item.apiName,
                key: tempTreeId,
                isLeaf: true,
                method: item.method,
                query: {
                    ...metaInfo,
                    path: item.apiUrl,
                    method: item.method,
                }
            });
            indexApi++;
        });
    }
    indexFolder++;
    return treeData;
}

/**
 * 搜索接口是否包含某关键字，将匹配：URL、path、method、summary、description、tags 属性
 * @param url 接口URL
 * @param methodNode 接口基本信息
 * @param keywords 关键字
 * @returns {*|boolean} 是否包含
 */
function searchInPathMethods(url, methodNode, keywords) {
    if (!keywords || !url) {
        return true;
    }
    url = url.toLowerCase();
    keywords = keywords.toLowerCase();
    // 路径中有就不用再去找了
    if (url.indexOf(keywords) >= 0) {
        return true;
    }
    let searchData = methodNode.path + methodNode.method + methodNode.summary + methodNode.description + methodNode.tags;
    return (searchData && searchData.toLowerCase().indexOf(keywords) >= 0);
}
