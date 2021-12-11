const methodArray = ["get", "head", "post", "put", "patch", "delete", "options", "trace"];

/**
 * 解析swagger文档paths信息，生成后续需要的关键信息
 * @param swagger 文档内容
 * @returns {{urlMethodMap: {}, tagPathMap: {}, methodStatistic: {}}} 例：
 * {
 *   urlMethodMap: {
 *       'url + "." + method': {swagger.paths.url.method对象信息}
 *   },
 *   tagPathMap: {
 *       分组名: {url: {...接口信息, path: '', url: '', method: ''}}
 *   },
 *   methodStatistic: {
 *       POST: 132,
 *       GET: 146,
 *       TOTAL: 999
 *   }
 * }
 */
export function analysisSwaggerData(swagger) {
    let tagPathMap = {}, urlMethodMap = {}, methodStatistic = {};
    let swaggerPaths = swagger.paths;
    if (!swaggerPaths) {
        return {urlMethodMap, tagPathMap, methodStatistic};
    }
    //console.log(swaggerPaths);
    Object.keys(swaggerPaths).forEach(url => {
        //console.log(key, swaggerPaths[key]);
        let pathMethods = swaggerPaths[url];
        for (let method of methodArray) {
            if (!pathMethods[method] || !pathMethods[method].tags) {
                continue;
            }
            let methodLower = method.toLowerCase();
            methodStatistic[methodLower] = (methodStatistic[methodLower] || 0) + 1;
            methodStatistic['total'] = (methodStatistic['total'] || 0) + 1;
            pathMethods[method].tags.forEach(tag => {
                let pathTag = tagPathMap[tag];
                if (!pathTag) {
                    pathTag = tagPathMap[tag] = {};
                }
                let pathTagUrl = pathTag[url];
                if (!pathTagUrl) {
                    pathTagUrl = pathTag[url] = {};
                }
                let tempPath = url + "." + method;
                pathTagUrl[method] = pathMethods[method];
                pathTagUrl[method].path = tempPath;
                pathTagUrl[method].url = url;
                pathTagUrl[method].method = method;
                // url对应文档的映射
                urlMethodMap[tempPath] = pathMethods[method];
            });
        }
    });
    return {urlMethodMap, tagPathMap, methodStatistic};
}

/**
 * 按tag分组获取左侧菜单目录树
 * @param swagger 原始文档信息
 * @param tagPathMap 分组信息{分组名: {url: {...接口信息, path: '', url: '', method: ''}}}
 * @param keywords 过滤关键字
 * @param metaInfo 接口元信息，点击时放入URL的参数
 */
export function getTreeDataForTag(swagger, tagPathMap, keywords, metaInfo) {
    let treeData = [];
    let indexTag = 1;
    // 遍历分组
    let swaggerTags = swagger.tags || [];
    if (swaggerTags.length <= 0) {
        Object.keys(tagPathMap).forEach(tag => swaggerTags.push({name: tag}));
    }
    swaggerTags.forEach(tag => {
        let indexUrl = 1;
        let urlTree = [];
        let pathTagNode = tagPathMap[tag.name];
        if (!pathTagNode) {
            return;
        }
        // 遍历路劲
        Object.keys(pathTagNode).forEach(url => {
            let indexMethod = 1;
            let pathUrlNode = pathTagNode[url];
            // 遍历方法
            Object.keys(pathUrlNode).forEach(method => {
                let tempTreeId = indexTag + "_" + indexUrl + "_" + indexMethod;
                let methodNode = pathUrlNode[method];
                if (!searchInPathMethods(url, methodNode, keywords)) {
                    return;
                }
                methodNode.treeId = tempTreeId;
                let title = methodNode.summary || methodNode.path;
                urlTree.push({
                    title: title,
                    key: tempTreeId,
                    isLeaf: true,
                    method: methodNode.method,
                    query: {
                        ...metaInfo,
                        path: methodNode.url,
                        method: methodNode.method,
                    }
                });
                indexMethod++;
            });
            indexUrl++;
        });
        if (urlTree.length > 0) {
            treeData.push({title: tag.name, key: indexTag, children: urlTree});
        }
        indexTag++;
    });
    return [
        {
            key: 'main',
            title: swagger.info.title || 'Swagger接口文档',
            children: treeData
        }
    ];
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
