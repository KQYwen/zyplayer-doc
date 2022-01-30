const methodArray = ["get", "head", "post", "put", "patch", "delete", "options", "trace"];


/**
 * 按tag分组获取左侧菜单目录树
 * @param customRequest 原始文档信息
 * @param tagPathMap 分组信息{分组名: {url: {...接口信息, path: '', url: '', method: ''}}}
 * @param keywords 过滤关键字
 * @param metaInfo 接口元信息，点击时放入URL的参数
 */
export function getTreeDataForTag(customRequest, keywords, metaInfo, requestInfoMap) {
    let firstChild = customRequest[0];
    let treeData = getTreeDataChildren(firstChild, keywords, metaInfo, requestInfoMap, 1);
    return [
        {
            key: 'main',
            isLeaf: false,
            title: firstChild.name || '自建API接口文档',
            children: treeData
        }
    ];
}

function getTreeDataChildren(customRequest, keywords, metaInfo, requestInfoMap, treeIndex) {
    let treeData = [];
    if (!customRequest) {
        return treeData;
    }
    let indexFolder = 1;
    let indexApi = 1;
    if (customRequest.children && customRequest.children.length > 0) {
        customRequest.children.forEach(item => {
            requestInfoMap.originNodeMap[item.nodeId] = item;
            let tempTreeId = treeIndex + "_" + indexFolder + "_" + indexApi;
            if (item.nodeType === 1) {
                treeData.push({
                    title: item.nodeName,
                    key: tempTreeId,
                    isLeaf: true,
                    method: item.method,
                    nodeId: item.nodeId,
                    query: {
                        ...metaInfo,
                        nodeId: item.nodeId,
                    }
                });
                indexApi++;
            } else {
                let treeChildren = getTreeDataChildren(item, keywords, metaInfo, requestInfoMap, tempTreeId);
                let eureka = searchInCustomRequestFolder(item, keywords);
                if (treeChildren.length > 0 || eureka) {
                    treeData.push({
                        title: item.nodeName,
                        key: tempTreeId,
                        nodeId: item.nodeId,
                        isLeaf: false,
                        editing: false,
                        titleEditing: item.nodeName,
                        children: treeChildren
                    });
                    indexApi++;
                }
            }
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
function searchInCustomRequest(request, keywords) {
    if (!keywords || !request) {
        return true;
    }
    keywords = keywords.toLowerCase();
    let searchData = request.apiUrl + request.method + request.nodeName;
    return (searchData && searchData.toLowerCase().indexOf(keywords) >= 0);
}

function searchInCustomRequestFolder(folder, keywords) {
    if (!keywords || !folder) {
        return true;
    }
    keywords = keywords.toLowerCase();
    let searchData = folder.name;
    return (searchData && searchData.toLowerCase().indexOf(keywords) >= 0);
}
