import formatjson from '../../assets/utils/formatjson'

/**
 * 参数解析
 * @author 暮光：城中城
 * @since 2017年5月7日
 */
export default {
    getRequestParamList(parameters, definitionsDataMap) {
        if (!parameters) {
            return [];
        }
        formatjson.annotationObject = {};
        let requestParamList = [], requestParamExample = [];
        Object.keys(parameters).forEach(key => {
            let tempParameters = parameters[key];
            let htmlStr = "", htmlStrExample = "";
            let responsesJson = [];
            let responsesExample = [];
            let required = tempParameters.required;
            let paramName = tempParameters.name || '';
            let paramType = tempParameters.type || '';
            let paramDesc = tempParameters.description || '';
            let paramIn = tempParameters.in || '';
            let example = tempParameters.example || tempParameters.default || '';
            if (tempParameters.items) {
                htmlStr = paramName + "[0]";
                htmlStrExample = paramName + "[0]";
            } else if (tempParameters.schema) {
                if ("array" === tempParameters.schema.type) {
                    let responsesObj = definitionsDataMap[tempParameters.schema.items.$ref];
                    if (responsesObj != null) {
                        responsesJson[0] = this.getResponsesJson(responsesObj, "", false, 1, definitionsDataMap);
                        responsesExample[0] = this.getResponsesJson(responsesObj, "", true, 1, definitionsDataMap);
                    } else {
                        responsesJson = [""];
                        responsesExample = [""];
                        if (tempParameters.schema.items.type === "boolean") {
                            responsesJson = [true];
                            responsesExample = [true];
                        } else if (tempParameters.schema.items.type === "integer") {
                            responsesJson = [0];
                            responsesExample = [0];
                        }
                    }
                    if (tempParameters.schema.$ref) {
                        let arrTmp = tempParameters.schema.$ref.split("/");
                        paramType = arrTmp[arrTmp.length - 1];
                    }
                    htmlStr = formatjson.processObjectToHtmlPre(responsesJson, 0, false, false, false, true);
                    htmlStrExample = formatjson.processObjectToHtmlPre(responsesExample, 0, false, false, false, false);
                } else if (tempParameters.schema.$ref) {
                    let responsesObj = definitionsDataMap[tempParameters.schema.$ref];
                    if (tempParameters.schema.$ref) {
                        let arrTmp = tempParameters.schema.$ref.split("/");
                        paramType = arrTmp[arrTmp.length - 1];
                    }
                    if (responsesObj) {
                        responsesJson = this.getResponsesJson(responsesObj, "", false, 1, definitionsDataMap);
                        responsesExample = this.getResponsesJson(responsesObj, "", true, 1, definitionsDataMap);
                        htmlStr = formatjson.processObjectToHtmlPre(responsesJson, 0, false, false, false, true);
                        htmlStrExample = formatjson.processObjectToHtmlPre(responsesExample, 0, false, false, false, false);
                    } else {
                        htmlStr = paramName;
                        htmlStrExample = paramName;
                    }
                } else if ("string" === tempParameters.schema.type) {
                    htmlStr = paramName;
                    htmlStrExample = paramName;
                } else {
                    htmlStr = paramName;
                    htmlStrExample = paramName;
                }
            } else {
                htmlStr = paramName;
                htmlStrExample = paramName;
            }
            requestParamList.push({htmlStr, paramDesc, paramType, paramIn, required});
            requestParamExample.push({htmlStrExample, paramDesc, paramType, paramIn, required});
        });
        console.log(requestParamList);
        console.log(requestParamExample);
        return requestParamList;
    },
    getResponsesJson(responsesObj, prevRef, isExample, recursiveCount, definitionsDataMap) {
        let responsesJson = {};
        recursiveCount++;// 多层递归，最多递归10层，防止无限递归
        if (!responsesObj || !responsesObj.properties || recursiveCount > 10) {
            return responsesJson;
        }
        let requiredArr = responsesObj.required;
        Object.keys(responsesObj.properties).forEach(prop => {
            let tmpData = responsesObj.properties[prop];
            if ("array" === tmpData.type) {// 数组
                formatjson.annotationObject[prop] = tmpData.description || '';
                if (prevRef !== tmpData.items.$ref) {
                    let tempObj = definitionsDataMap[tmpData.items.$ref];
                    if (tempObj != null) {
                        let tempArr = responsesJson[prop] = [];
                        tempArr[0] = this.getResponsesJson(tempObj, tmpData.items.$ref, isExample, recursiveCount, definitionsDataMap);
                    } else {
                        let responsesJsonSub = [];
                        let bodyFor = responsesJsonSub;
                        let items = tmpData.items;
                        for (let i = 0; i < 10; i++) {
                            if ("array" === items.type) {
                                bodyFor = bodyFor[0] = [];
                                items = items.items;
                            } else {
                                tempObj = definitionsDataMap[items.$ref];
                                if (tempObj != null) {
                                    bodyFor[0] = this.getResponsesJson(tempObj, items.$ref, isExample, recursiveCount, definitionsDataMap);
                                } else {
                                    if (items.type === "boolean") {
                                        bodyFor[0] = true;
                                    } else if (items.type === "integer") {
                                        bodyFor[0] = 0;
                                    } else {
                                        bodyFor[0] = "";
                                    }
                                }
                                break;
                            }
                        }
                        responsesJson[prop] = responsesJsonSub;
                    }
                } else {
                    responsesJson[prop] = "{}" + (tmpData.description || '');
                }
            } else if (tmpData.$ref) {// 对象
                formatjson.annotationObject[prop] = tmpData.description || '';
                if (prevRef !== tmpData.$ref) {
                    let tempObj = definitionsDataMap[tmpData.$ref];
                    responsesJson[prop] = this.getResponsesJson(tempObj, tmpData.$ref, isExample, recursiveCount, definitionsDataMap);
                } else {
                    responsesJson[prop] = "{}" + (tmpData.description || '');
                }
            } else {// 字段
                let enumExample = "";
                let enumObj = tmpData["enum"];
                if (enumObj && enumObj.length > 0) {
                    enumExample = "枚举值：";
                    for (let i = 0; i < enumObj.length; i++) {
                        if (i > 0) {
                            enumExample += "、";
                        }
                        enumExample += enumObj[i];
                    }
                }
                let typeStr = tmpData.format || tmpData.type || '';
                if (isExample) {
                    let tempVal = tmpData.example || '';
                    if (tempVal && enumExample) {
                        tempVal = tempVal + "，" + enumExample;
                    }
                    responsesJson[prop] = tempVal;
                } else {
                    if (requiredArr && requiredArr.indexOf(prop) >= 0) {
                        typeStr = (typeStr ? typeStr + "," : "") + "required";
                    }
                    if (typeStr) {
                        typeStr = "(" + typeStr + ")";
                    }
                    let descriptionStr = typeStr + tmpData.description || '';
                    if (descriptionStr && enumExample) {
                        descriptionStr = descriptionStr + "，" + enumExample;
                    }
                    responsesJson[prop] = descriptionStr;
                }
            }
        });
        return responsesJson;
    },
    getResponseParamList(responses, definitionsDataMap) {
        if (!responses) {
            return [];
        }
        let responsesList = [];
        formatjson.annotationObject = {};
        Object.keys(responses).forEach(key => {
            let tempRespones = responses[key];
            if (tempRespones.schema) {
                let responsesJson, responsesExample;
                if ("array" === tempRespones.schema.type) {
                    responsesJson = [];
                    responsesExample = [];
                    let responsesObj = definitionsDataMap[tempRespones.schema.items.$ref];
                    if (responsesObj != null) {
                        responsesJson[0] = this.getResponsesJson(responsesObj, "", false, 1, definitionsDataMap);
                        responsesExample[0] = this.getResponsesJson(responsesObj, "", true, 1, definitionsDataMap);
                    } else {
                        responsesJson = [""];
                        responsesExample = [""];
                        if (tempRespones.schema.items.type === "boolean") {
                            responsesJson = [true];
                            responsesExample = [true];
                        } else if (tempRespones.schema.items.type === "integer") {
                            responsesJson = [0];
                            responsesExample = [0];
                        }
                    }
                } else if (tempRespones.schema.$ref) {
                    let responsesObj = definitionsDataMap[tempRespones.schema.$ref];
                    if (!responsesObj) {
                        let arrTmp = tempRespones.schema.$ref.split("/");
                        let lastObjName = arrTmp[arrTmp.length - 1];
                        responsesJson = lastObjName;
                        responsesExample = lastObjName;
                    } else {
                        responsesJson = this.getResponsesJson(responsesObj, "", false, 1, definitionsDataMap);
                        responsesExample = this.getResponsesJson(responsesObj, "", true, 1, definitionsDataMap);
                    }
                } else {
                    responsesJson = "";
                    responsesExample = "";
                }
                if (!responsesJson) {
                    return;
                }
                let htmlStr = formatjson.processObjectToHtmlPre(responsesJson, 0, false, false, false, true);
                responsesList.push({code: key, desc: htmlStr});
                htmlStr = formatjson.processObjectToHtmlPre(responsesExample, 0, false, false, false, false);
            }
        });
        return responsesList;
    }
}

