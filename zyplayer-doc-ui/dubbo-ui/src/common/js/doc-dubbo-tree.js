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
export default {
	/**
	 * 把原始的json字符串转换成对象列表的方式，方便后续使用
	 * @param json swagger的原始对象
	 * @returns
	 */
	createTreeViewByTree(treePathDataMap, json, keywords) {
		let pathIndex = [];
		if (!json) return;
		//console.log(paths);
		let lastId = "";
		for (let i = 0; i < json.length; i++) {
			let interfaceX = json[i].interface;
			//console.log(key, paths[key]);
			if (!this.findInPathsValue(json[i], keywords)) {
				continue;
			}
			if (json[i].nodeList.length <= 0) {
				continue;
			}
			let methods = json[i].nodeList[0].methods;
			let application = json[i].nodeList[0].application;
			for (let j = 0; j < methods.length; j++) {
				let interfaceTemp = interfaceX + "." + methods[j];
				let keyArr = interfaceTemp.split(".");
				let nowPathObj = null;
				keyArr.forEach((val, index) => {
					//console.log(val, index);
					if (!val && index == 0) {
						return;
					}
					let nowPath = val;
					if (nowPathObj == null) {
						nowPathObj = this.findNode(pathIndex, nowPath);
						if (nowPathObj == null) {
							nowPathObj = {
								id: pathIndex.length,
								label: nowPath, children: []
							};
							pathIndex.push(nowPathObj);
						}
						lastId = nowPathObj.id;
						nowPathObj = nowPathObj.children;
					} else {
						let tempPathObj = this.findNode(nowPathObj, nowPath);
						if (tempPathObj == null) {
							tempPathObj = {
								id: lastId + "." + nowPathObj.length,
								label: nowPath, children: []
							};
							nowPathObj.push(tempPathObj);
						}
						lastId = tempPathObj.id;
						nowPathObj = tempPathObj.children;
						if (index == keyArr.length - 1) {
							let tempPath = interfaceTemp;
							tempPathObj.children = null;
							tempPathObj.method = methods[j];
							tempPathObj.interface = tempPath;
							tempPathObj.application = application;
							treePathDataMap.set(tempPath, json[i]);
						}
					}
				});
			}
		}
		// console.log(pathIndex);
		return pathIndex;
	},
	createTreeViewByTreeWithMerge(treePathDataMap, json, keywords) {
		let pathIndex = this.createTreeViewByTree(treePathDataMap, json, keywords);
		this.mergeNode(pathIndex);
		return pathIndex;
	},
	/**
	 * 查找node节点
	 */
	findNode(arr, service) {
		for (let i = 0; i < arr.length; i++) {
			if (arr[i].label == service) {
				return arr[i];
			}
		}
		return null;
	},
	/**
	 * 多层级合并
	 */
	mergeNode(node) {
		for (let i = 0; i < node.length; i++) {
			let tempNode = node[i];
			if (tempNode.children == null
				|| tempNode.children[0].children == null
				|| tempNode.children[0].children[0].children == null) {
				continue;
			}
			if (tempNode.children.length == 1) {
				tempNode.label = tempNode.label + "." + tempNode.children[0].label;
				tempNode.children = tempNode.children[0].children;
				i--;
			}
			this.mergeNode(tempNode.children);
		}
	},
	findInPathsValue(pathsValue, keywords) {
		if (!keywords) {
			return true;
		}
		keywords = keywords.toLowerCase();
		// 找路径和说明里面包含关键字的
		let interfaceX = pathsValue.interface;
		if (!!interfaceX && interfaceX.toLowerCase().indexOf(keywords) >= 0) {
			return true;
		}
		if (pathsValue.nodeList.length > 0) {
			for (let i = 0; i < pathsValue.nodeList.length; i++) {
				let node = pathsValue.nodeList[i];
				if (!!node.application && node.application.toLowerCase().indexOf(keywords) >= 0) {
					return true;
				}
				if (!!node.methods && node.methods.length > 0) {
					for (let j = 0; j < node.methods.length; j++) {
						let method = node.methods[j];
						if (method.toLowerCase().indexOf(keywords) >= 0) {
							return true;
						}
						let path = interfaceX + "." + method;
						let docInfo = app.dubboDocMap[path];
						if (!!docInfo) {
							if (!!docInfo.explain && docInfo.explain.toLowerCase().indexOf(keywords) >= 0) {
								return true;
							}
							if (!!docInfo.explain && docInfo.explain.toLowerCase().indexOf(keywords) >= 0) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	},
};

