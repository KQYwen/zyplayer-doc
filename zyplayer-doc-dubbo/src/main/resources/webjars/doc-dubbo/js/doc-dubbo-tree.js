/**
 * 以树形方式生成并展示：
 * /api
 *   /data
 *     /getDateList
 *       post
 *       get
 * @author 暮光：城中城
 * @since 2018年5月26日
*/

/**
 * 把原始的json字符串转换成对象列表的方式，方便后续使用
 * @param json swagger的原始对象
 * @returns
 */
function createTreeViewByTree(json, keywords) {
	var pathIndex = [];
	if (isEmptyObject(json)) {
		return;
	}
	//console.log(paths);
	for (var i = 0; i < json.length; i++) {
		var interface = json[i].interface;
		//console.log(key, paths[key]);
		if (!findInPathsValue(json[i], keywords)) {
			continue;
		}
		if (json[i].nodeList.length <= 0) {
			continue;
		}
		var methods = json[i].nodeList[0].methods;
		for (var j = 0; j < methods.length; j++) {
			var interfaceTemp = interface + "." + methods[j];
			var keyArr = interfaceTemp.split(".");
			var nowPathObj = null;
			keyArr.forEach(function(val, index) {
				//console.log(val, index);
				if(isEmpty(val) && index == 0) {
					return;
				}
				var nowPath = val;
				// if(nowPathObj == null) {
				// 	nowPathObj = [];
				// 	var temp = nowPathObj[0] = {};
				// 	temp[nowPath] = findNode(pathIndex, nowPath);
				// 	if (temp[nowPath] == null) {
				// 		temp[nowPath] = {};
				// 		pathIndex.push(nowPathObj);
				// 	}
				// }
				// var tempPathObj = findNode(nowPathObj, nowPath);
				// if(isEmpty(tempPathObj)) {
				// 	var temp = [];
				// 	tempPathObj = temp[0] = {};
				// 	nowPathObj.push(temp);
				// }
				// nowPathObj = tempPathObj;
				// nowPathObj.label = nowPath;
				if (nowPathObj == null) {
					nowPathObj = findNode(pathIndex, nowPath);
					if (nowPathObj == null) {
						nowPathObj = {label: nowPath, children: []};
						pathIndex.push(nowPathObj);
					}
					nowPathObj = nowPathObj.children;
				} else {
					var tempPathObj = findNode(nowPathObj, nowPath);
					if(tempPathObj == null) {
						tempPathObj = {label: nowPath, children: []};
						nowPathObj.push(tempPathObj);
					}
					nowPathObj = tempPathObj.children;
					if (index == keyArr.length - 1) {
						var tempPath = app.projectTreeIdIndex + ":" + interfaceTemp;
						tempPathObj.children = null;
						tempPathObj.method = methods[j];
						tempPathObj.interface = tempPath;
						app.treePathDataMap.set(tempPath, json[i]);
					}
				}
			});
		}
	}
	app.projectTreeIdIndex++;
	//var htmlStr = getTreeHtmlForTree(pathIndex, app.projectTreeIdIndex);
	console.log(pathIndex);
	return pathIndex;
}

function findNode(arr, service){
	for (var i = 0; i < arr.length; i++) {
		if(arr[i].label == service) {
			return arr[i];
		}
	}
	return null;
}

/**
 * 将对象列表递归的方式转换成文档格式html字符串
 * @param pathData 处理后的对象列表
 * @returns 生成的html字符串
 */
function getTreeHtmlForTree(pathData, treeIdStr) {
	var tempStr = "";
	var indexNow = 1;
	Object.keys(pathData).forEach(function (key) {
		var tempNode = pathData[key];
		var tempTreeId = treeIdStr + "_" + indexNow;
		if (isNotEmpty(tempNode.interface)) {
			tempNode.treeId = tempTreeId;
			tempStr += '<li m-id="' + tempTreeId + '"><a href="#" class="show-doc" method="' + tempNode.method + '" path="' + tempNode.interface + '">' + key + '</a></li>';
		} else {
			tempStr += '<li>';
			tempStr += '<a href="#">' + key + '</a>';
			tempStr += '<ul>';
			tempStr += getTreeHtmlForTree(tempNode, tempTreeId);
			tempStr += '</ul>';
			tempStr += '</li>';
		}
		indexNow++;
	});
	return tempStr;
}

function findInPathsValue(pathsValue, keywords) {
	if (isEmpty(keywords)) {
		return true;
	}
	keywords = keywords.toLowerCase();
	// 找路径和说明里面包含关键字的
	var interface = pathsValue.interface;
	if (isNotEmpty(interface) && interface.toLowerCase().indexOf(keywords) >= 0) {
		return true;
	}
	return false;
}

