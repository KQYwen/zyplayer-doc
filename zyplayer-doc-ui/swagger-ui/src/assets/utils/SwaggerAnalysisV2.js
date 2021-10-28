import {message} from 'ant-design-vue';
// 无需特殊处理的参数类型
let notNeedHandleTypeArr = ['file', 'string', 'integer', 'long', 'double', 'object', 'number', 'boolean'];
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
        let indexKey = 1;
        let requestParamList = [];
        for (let i = 0; i < parameters.length; i++) {
            let parameter = parameters[i];
            let type = parameter.type;
            let subType = undefined;
            let children = undefined;
            if (!type) {
                if (parameter.schema && parameter.schema.type) {
                    type = parameter.schema.type;
                }
            }
            if (type === 'array') {
                // 解析parameter.items.$ref 或 parameter.items.originalRef {$ref: "#/definitions/Model", originalRef: "Model"}
                // 解析parameter.items.type {type: 'file'}
                if (parameter.items && parameter.items.originalRef) {
                    children = this.getParamDefinitions(parameter.items.originalRef, definitionsDataMap, indexKey, {}, 0);
                } else if (parameter.schema && parameter.schema.items && parameter.schema.items.originalRef) {
                    children = this.getParamDefinitions(parameter.schema.items.originalRef, definitionsDataMap, indexKey, {}, 0);
                } else if (parameter.items && parameter.items.type) {
                    subType = parameter.items.type;
                } else {
                    console.log('001-遇到未处理的类型，请联系开发人员修改：' + type, parameter);
                    message.error('001-遇到未处理的类型，请联系开发人员修改：' + type);
                }
            } else if (!type) {
                if (parameter.schema && parameter.schema.originalRef) {
                    // 解析parameter.schema {originalRef: "Model", $ref: "#/definitions/Model"}
                    type = parameter.schema.originalRef;
                    children = this.getParamDefinitions(type, definitionsDataMap, indexKey, {}, 0);
                } else {
                    console.log('002-遇到未处理的类型，请联系开发人员修改：' + type, parameter);
                    message.error('002-遇到未处理的类型，请联系开发人员修改：' + type);
                }
            } else {
                if (notNeedHandleTypeArr.indexOf(type) >= 0) {
                    // 无需特殊处理的类型
                } else {
                    console.log('003-遇到未处理的类型，请联系开发人员修改：' + type, parameter);
                    message.error('003-遇到未处理的类型，请联系开发人员修改：' + type);
                }
            }
            requestParamList.push({
                type: type,
                key: indexKey,
                in: parameter.in,
                name: parameter.name,
                required: parameter.required ? '是' : '否',
                description: parameter.description,
                children: children,
            });
            indexKey++;
        }
        return requestParamList;
    },
    getResponseParamList(responses, definitionsDataMap) {
        let responsesList = [];
        let indexKey = 1;
        Object.keys(responses).forEach(code => {
            let codeResponses = responses[code];
            let type = undefined;
            let children = undefined;
            if (codeResponses.schema && codeResponses.schema.originalRef) {
                type = codeResponses.schema.originalRef;
                children = this.getParamDefinitions(codeResponses.schema.originalRef, definitionsDataMap, indexKey, {}, 0);
            }
            responsesList.push({
                code: code,
                type: type,
                key: indexKey,
                desc: codeResponses.description,
                schemas: children,
            });
            indexKey++;
        });
        return responsesList;
    },
    getParamDefinitions(ref, definitionsDataMap, indexKey, parentRef, deep) {
        let definition = definitionsDataMap[ref];
        // 层级大于5层 或 父节点已经解析过此类型了 或者 没有类型定义
        if (deep >= 5 || parentRef[ref] || !definition) {
            return undefined;
        }
        parentRef[ref] = 1;
        let paramList = [];
        let type = definition.type;
        let properties = definition.properties;
        let indexSub = 1;
        if (type === 'object') {
            Object.keys(properties).forEach(key => {
                let parameter = properties[key];
                let type = parameter.type;
                let keySub = indexKey + '_' + indexSub;
                let children = undefined;
                if (type === 'array') {
                    // 解析parameter.items {originalRef: "Model", $ref: "#/definitions/Model"}
                    if (parameter.items && parameter.items.originalRef) {
                        children = this.getParamDefinitions(parameter.items.originalRef, definitionsDataMap, keySub, parentRef, deep + 1);
                    } else {
                        console.log('004-遇到未处理的类型，请联系开发人员修改：' + type, parameter);
                        message.error('004-遇到未处理的类型，请联系开发人员修改：' + type);
                    }
                } else if (!type) {
                    if (parameter.originalRef) {
                        type = parameter.originalRef;
                        children = this.getParamDefinitions(parameter.originalRef, definitionsDataMap, keySub, parentRef, deep + 1);
                    } else {
                        console.log('005-遇到未处理的类型，请联系开发人员修改：' + type, parameter);
                        message.error('005-遇到未处理的类型，请联系开发人员修改：' + type);
                    }
                } else {
                    if (notNeedHandleTypeArr.indexOf(type) >= 0) {
                        // 无需特殊处理的类型
                    } else {
                        console.log('006-遇到未处理的类型，请联系开发人员修改：' + type, parameter);
                        message.error('006-遇到未处理的类型，请联系开发人员修改：' + type);
                    }
                }
                paramList.push({
                    type: type,
                    name: key,
                    key: keySub,
                    description: parameter.description,
                    children: children,
                });
                indexSub++;
            });
        }
        return paramList.length > 0 ? paramList : undefined;
    },

}

