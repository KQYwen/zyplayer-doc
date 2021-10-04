/**
 * localStorage的操作工具类
 * @author 暮光：城中城
 * @since 2021年9月25日
 */
export default {
	// 集合的操作
	set: {
		// 保存
		save(key, subKey, val, max = 100) {
			if (!window.localStorage) {
				console.log('当前浏览器不支持localStorage');
				return;
			}
			let dataList = JSON.parse(window.localStorage.getItem(key) || '[]');
			// 存在则修改值，否则新增
			let dataItem = dataList.find(val => val.key === subKey);
			if (dataItem) {
				dataItem.value = val;
				dataItem.time = new Date().getTime();
			} else {
				dataList.push({key: subKey, value: val, time: new Date().getTime()});
			}
			// 删除多余的
			dataList.sort((val1, val2) => val2.time - val1.time);
			dataList = dataList.slice(0, max + 1);
			window.localStorage.setItem(key, JSON.stringify(dataList));
		},
		// 获取
		get(key, subKey) {
			if (!window.localStorage) {
				return '';
			}
			let dataList = JSON.parse(window.localStorage.getItem(key) || '[]');
			let dataItem = dataList.find(val => val.key === subKey) || {};
			return dataItem.value || '';
		}
	}
}

