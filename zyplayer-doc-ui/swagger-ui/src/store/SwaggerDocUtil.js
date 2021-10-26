export function getDefinitions(definitions) {
    if (!definitions) {
        return {};
    }
    let swaggerDefinitions = {};
    Object.keys(definitions).forEach((key) => {
        swaggerDefinitions["#/definitions/" + key] = definitions[key];
    });
    return swaggerDefinitions;
}

export function createTreeViewByTag(swagger, keywords) {
    let pathIndex = {}, treePathDataMap = {};
    let paths = swagger.paths;
    let domain = swagger.domainUrl;// 服务器代理会返回此属性
    let rewriteDomainUrl = swagger.rewriteDomainUrl;// 服务器代理会返回此属性
    if (!paths) {
        return;
    }
    if (!domain) {
        domain = "http://" + swagger.host + swagger.basePath;
    }
    if (domain.substring(domain.length - 1) === "/") {
        domain = domain.substring(0, domain.length - 1);
    }
    //console.log(paths);
    Object.keys(paths).forEach(key => {
        //console.log(key, paths[key]);
        setRequestMethodForTag(rewriteDomainUrl, domain, paths[key], pathIndex, key, "get", treePathDataMap);
        setRequestMethodForTag(rewriteDomainUrl, domain, paths[key], pathIndex, key, "head", treePathDataMap);
        setRequestMethodForTag(rewriteDomainUrl, domain, paths[key], pathIndex, key, "post", treePathDataMap);
        setRequestMethodForTag(rewriteDomainUrl, domain, paths[key], pathIndex, key, "put", treePathDataMap);
        setRequestMethodForTag(rewriteDomainUrl, domain, paths[key], pathIndex, key, "patch", treePathDataMap);
        setRequestMethodForTag(rewriteDomainUrl, domain, paths[key], pathIndex, key, "delete", treePathDataMap);
        setRequestMethodForTag(rewriteDomainUrl, domain, paths[key], pathIndex, key, "options", treePathDataMap);
        setRequestMethodForTag(rewriteDomainUrl, domain, paths[key], pathIndex, key, "trace", treePathDataMap);
    });
    // console.log(pathIndex);
    // console.log(treePathDataMap);
    // console.log(treeData);
    return {pathIndex: pathIndex, treePathDataMap: treePathDataMap};
}

/**
 * 设置对象的各种请求方式，存在则复制
 * @param source 资源，原始json的paths的指定对象
 * @param pathObj 当前的待赋值对象
 * @param url url绝对路径
 * @param method 请求方式，post、get...
 * @returns
 */
function setRequestMethodForTag(rewriteDomainUrl, domain, source, pathObj, url, method, treePathDataMap) {
    if (!source[method] || !source[method].tags) {
        return;
    }
    source[method].tags.forEach(function(val, index) {
        let tempObj = pathObj[val];
        if(!tempObj) {
            tempObj = pathObj[val] = {};
        }
        let tempUrlObj = tempObj[url];
        if(!tempUrlObj) {
            tempUrlObj = tempObj[url] = {};
        }
        let tempPath = url + "." + method;
        tempUrlObj[method] = source[method];
        tempUrlObj[method].path = tempPath;
        tempUrlObj[method].url = url;
        tempUrlObj[method].method = method;
        tempUrlObj[method].domain = domain;
        tempUrlObj[method].rewriteDomainUrl = rewriteDomainUrl;
        treePathDataMap[tempPath] = source[method];
    });
}

export function getTreeDataForTag(swagger, pathData, metaInfo) {
    return [
        {
            title: swagger.title || 'Swagger接口文档',
            key: '0-0',
            children: getTreeHtmlForTag(pathData, 0, metaInfo)
        }
    ];
}

function getTreeHtmlForTag(pathData, treeId, metaInfo) {
    let treeData = [];
    let indexNow = 1;
    // get, head, post, put, patch, delete, options, trace
    let actionArrays = ["get", "head", "post", "put", "patch", "delete", "options", "trace"];
    Object.keys(pathData).forEach(key => {
        let tempNode = pathData[key];
        let tempTreeId = treeId + "_" + indexNow;
        // 只有一个子元素，而且有method元素，说明是只有一个节点
        let nodeSub = getObjectFirstAttributeIfOnly(tempNode);
        if (nodeSub && nodeSub.method) {
            nodeSub.treeId = tempTreeId;
            let title = nodeSub.summary || nodeSub.path;
            treeData.push({
                title: title,
                key: tempTreeId,
                isLeaf: true,
                method: nodeSub.method,
                query: {
                    ...metaInfo,
                    path: nodeSub.url,
                    method: nodeSub.method,
                }
            });
        } else if (actionArrays.indexOf(key) >= 0) {
            tempNode.treeId = tempTreeId;
            let title = tempNode.summary || tempNode.path;
            treeData.push({
                title: title,
                key: tempTreeId,
                isLeaf: true,
                method: tempNode.method,
                query: {
                    ...metaInfo,
                    path: tempNode.url,
                    method: tempNode.method,
                }
            });
        } else {
            treeData.push({title: key, key: key, children: getTreeHtmlForTag(tempNode, tempTreeId, metaInfo)});
        }
        indexNow++;
    });
    return treeData;
}

/**
 * 如果对象只有一个属性则返回第一个属性，否则返回null
 * @param data
 * @returns
 */
function getObjectFirstAttributeIfOnly(data) {
    let len = 0, value = "";
    for (let key in data) {
        if (++len > 1) {
            return undefined;
        }
        value = data[key];
    }
    return value;
}
