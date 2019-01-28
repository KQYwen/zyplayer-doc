/**
 * 在线调试页面js
 * @author 暮光：城中城
 * @since 2018年7月20日
*/

$(document).ready(function(){
	$("#debugRequstType .dropdown-menu li").click(function(){
		var text = $(this).find("a").text();
		$("#debugRequstType .options").text(text);
	});
	/**
	 * 保存参数模板
	 * @returns
	 */
	$(".save-request-template").click(function(){
		Toast.notOpen();
	});
	/**
	 * 发送请求
	 * @returns
	 */
	$(".send-request").click(function(){
		// 多行编辑状态下转成表单，下面读取表单内容
		if ($("#bulkEditFormCheck").prop('checked')) {
			var bulkEdit = $("#bulkEditForm").val();
			bulkEditToTable("#tabParamTypeForm", bulkEdit);
		}
		if ($("#bulkEditHeaderCheck").prop('checked')) {
			var bulkEdit = $("#bulkEditHeader").val();
			bulkEditToTable("#tabParamHeader", bulkEdit);
		}
		$("#tabResponseHeader table tbody").empty();
		$("#tabResponseCookie table tbody").empty();
		$("#responseBodyTextArea").val("");
		$("#responseBodyJsonDiv").html("暂无数据");

		var storeRequestParam = {};
		var docUrl = $("#docUrl").text();
		var options = $("#debugRequstType .btn .options").text();
		var postUrl = $("#postUrlInput").val();
		var requestHeaderForm = $("#requestHeaderForm").serializeArray();
		var requestParamForm = $("#requestParamForm").serializeArray();
		var paramHeaderSend = {};
		var paramFormSend = {};
		var paramBodySend = $("[name=paramBody]").val();
		var formToUrl = $("[name=formToUrl]").prop('checked') ? 1 : 0;
		// 组装空的表单对象
		var formDataToServer = new FormData();
		
		requestHeaderForm = serializeArrayToObj(requestHeaderForm);
		requestParamForm = serializeArrayToObj(requestParamForm);
		storeRequestParam.formToUrl = formToUrl;
		formDataToServer.append("formToUrl", formToUrl);
		if (isNotEmpty(paramBodySend)) {
			try {
				paramBodySend = JSON.stringify(JSON.parse(paramBodySend));
			} catch (e) {}
			storeRequestParam.body = paramBodySend;
			formDataToServer.append("body", paramBodySend);
			// 替换path参数
			Object.keys(requestParamForm).forEach(function (key) {
				postUrl = postUrl.replace("{" + key + "}", requestParamForm[key]);
			});
		}
		var reqParamStr = "";
		Object.keys(requestParamForm).forEach(function (key) {
			var value = requestParamForm[key];
			if (isNotEmpty(key) && isNotEmpty(value)) {
				if (isNotEmpty(reqParamStr)) {
					reqParamStr += "&";
				}
				reqParamStr += key + "=" + value;
				paramFormSend[key] = value;
				// 替换path参数
				postUrl = postUrl.replace("{" + key + "}", value);
			}
		});
		
		storeRequestParam.form = paramFormSend;
		// 显示加载中图标
		$(".send-request .icon").removeClass("hide");
		// 获取header
		Object.keys(requestHeaderForm).forEach(function(key){
			var value = requestHeaderForm[key];
			if(isNotEmpty(key) && isNotEmpty(value)) {
				paramHeaderSend[key] = value;
			}
		});
		storeRequestParam.header = paramHeaderSend;
		//console.log(paramBodySend);
		var beforeSendTime = new Date().getTime();
		// 拼装文件
		var fileInput = $('#requestParamForm').find(".file-input");
		for (var i = 0; i < fileInput.length; i++) {
			var fileName = $(fileInput[i]).parents("tr").find("[name=paramName]").val();
			for (var j = 0; j < fileInput[i].files.length; j++) {
				formDataToServer.append('files', fileInput[i].files[j]);
				formDataToServer.append('fileNames', fileName);
			}
			paramFormSend[fileName] = "-";
		}
		formDataToServer.append("header", JSON.stringify(paramHeaderSend));
		formDataToServer.append("url", postUrl);
		formDataToServer.append("method", options);
		// 表单参数是否拼在url上
		if(formToUrl == 1) {
			postUrl += "?" + reqParamStr;
			paramFormSend = "";
		} else {
			formDataToServer.append("form", JSON.stringify(paramFormSend));
		}
		// debugger;
		// 模拟请求开始
		postWithFile("swagger-mg-ui/http/request", formDataToServer, function(result){
			var afterSendTime = new Date().getTime();
			//console.log(result);
			var requestObj = result.data;
			// open-doc：去掉存储
			$("#httpRequestStatus").text(requestObj.status);
			$("#httpRequestTime").text((afterSendTime - beforeSendTime) + "ms");
			try {
				var htmlStr = Formatjson.processObjectToHtmlPre(JSON.parse(requestObj.data), 0, false, false, false, false);
				$("#responseBodyJsonDiv").html(htmlStr);
			} catch (e) {
				// 转json失败，应该是个页面，输出到iframe里，不能影响当前页面
				$("#responseBodyJsonDiv").html("<iframe id='responseBodyJsonIframe'></iframe>");
				setTimeout(function () {
					$("#responseBodyJsonIframe").contents().find("body").html(requestObj.data);
				}, 300);
			}
			$("#tabResponseHeader table tbody").empty();
			$("#tabResponseCookie table tbody").empty();
			var headers = requestObj.header || [];
			for (var i = 0; i < headers.length; i++) {
				var name = getNotEmptyStr(headers[i].name);
				var value = getNotEmptyStr(headers[i].value);
				$("#tabResponseHeader table tbody").append(
					'<tr>' + '<td>' + name + '</td>' + '<td>' + value + '</td>' + '</tr>'
				);
			}
			var cookies = requestObj.cookie || [];
			for (var i = 0; i < cookies.length; i++) {
				var name = getNotEmptyStr(cookies[i].name);
				var value = getNotEmptyStr(cookies[i].value);
				$("#tabResponseCookie table tbody").append(
					'<tr>' + '<td>' + name + '</td>' + '<td>' + value + '</td>' + '</tr>'
				);
			}
		}, function () {
			Toast.error("请求失败！");
		}, function () {
			$(".send-request .icon").addClass("hide");
		});
	});
	/**
	 * 输入框输入之后，如果是最后一行则在增加一行
	 * @returns
	 */
	$(".param-table").on("keyup", "input[name=paramName]", function(){
		var nextTr = $(this).parents("tr").next();
		if(nextTr.length <= 0) {
			$(this).parents(".param-table").append(getParamTableTr());
		}
	});
	/**
	 * 参数删除一行
	 * @returns
	 */
	$(".param-table").on("click", ".icon-times", function(){
		$(this).parents("tr").remove();
	});
	/**
	 * 在线调试管理
	 */
	$("#onlineDebugLi").click(function(){
		$(".tab-page,.tab-zpages").hide();
		$(".tab-online-debug-page").show();
		createOnlineDebugParamTable();
	});
	/**
	 * 在线调试管理-刷新
	 */
	$(".tab-online-debug-page .refresh").click(function(){
		createOnlineDebugParamTable();
	});
	/**
	 * 在线调试管理-展开所有
	 */
	$(".tab-online-debug-page .expand-all").click(function(){
		$("#onlineDebugParamTable .option-img").attr("src", "webjars/mg-ui/img/expanded.png");
		$("#onlineDebugParamTable .option-img").parent().next().show();
	});
	/**
	 * 在线调试管理-收起所有
	 */
	$(".tab-online-debug-page .collapse-all").click(function(){
		$("#onlineDebugParamTable .option-img").attr("src", "webjars/mg-ui/img/collapsed.png");
		$("#onlineDebugParamTable .option-img").parent().next().hide();
	});
	/**
	 * 在线调试-删除所有参数
	 */
	$(".tab-online-debug-page").on("click", ".del-all-param", function(){
		$.zui.store.forEach(function(key, value) {// 遍历所有本地存储的条目
			if(!key.startWith(cacheKeys.pRequestObjStart)) {
				return;
			}
			$.zui.store.remove(key);
		});
		createOnlineDebugParamTable();
	});
	/**
	 * 在线调试-删除参数
	 */
	$(".tab-online-debug-page").on("click", ".del-param", function(){
		var key = $(this).attr("key");
		if(isNotEmpty(key)) {
			$.zui.store.remove(key);
			$(this).parents("tr").remove();
		}
	});
	/**
	 * 提交模拟返回值
	 */
	$("#simulationResultSubmit").click(function(){
		// open-doc：去掉存储
	});
	/**
	 * 获取模拟返回值
	 */
	$("#simulationResultGet").click(function(){
		// open-doc：去掉获取存储
	});
});

/**
 * 生成在线调试管理页面
 * @returns
 */
function createOnlineDebugParamTable() {
	$("#onlineDebugParamTable tbody").empty();
	$.zui.store.forEach(function(key, value) {// 遍历所有本地存储的条目
		if(!key.startWith(cacheKeys.pRequestObjStart)) {
			return;
		}
		var newKey = key.substring(14, key.length);
		var htmlStr = Formatjson.processObjectToHtmlPre(value, 0, false, false, false, false);
		$("#onlineDebugParamTable tbody").append(
			'<tr>'
				+'<td>'+newKey+'</td>'
				+'<td>'+htmlStr+'</td>'
				+'<td><button class="btn btn-danger del-param" type="button" key="'+key+'">删除</button></td>'
			+'</tr>'
		);
	});
}

/**
 * 生成在线调试相关数据
 * @param requestParamObj
 * @returns
 */
function createOnlineDebugRequestParam(requestParamObj, url) {
	// open-doc：去掉获取存储
	createOnlineDebugRequestParamFun({}, requestParamObj, url);
}

/**
 * 生成在线调试相关数据
 * @param oldRequestObj 之前的调试参数
 * @param requestParamObj 参数列表的参数
 * @param url 请求url
 * @returns
 */
function createOnlineDebugRequestParamFun(oldRequestObj, requestParamObj, url) {
	if(isEmptyObject(oldRequestObj)) {
		oldRequestObj = {};
	}
	// 清空参数列表
	$("#tabParamHeader table tbody .new").remove();
	$("#tabParamTypeForm table tbody .new").remove();
	$("#tabResponseHeader table tbody").empty();
	$("#tabResponseCookie table tbody").empty();
	$("#tabParamHeader .form-control").val("");
	$("#tabParamTypeForm .form-control").val("");
	$("#responseBodyTextArea").val("");
	$("#responseBodyJsonDiv").html("暂无数据");
	$("#bulkEditHeaderCheck").prop("checked", false);
	$("#bulkEditFormCheck").prop("checked", false);
	$("#bulkEditHeader,#bulkEditForm").hide();
	$("#tabParamTypeForm table").show();
	$("#requestHeaderForm table").show();
	$("#tabParamTypeForm table tbody .base input[name=paramValue]")
		.attr("type", "text").removeClass("file-input")
		.removeAttr("multiple").attr("placeholder", "").val("");
	
	var options = $("#debugRequstType .btn .options").text();
	var formToUrl = oldRequestObj.formToUrl || 0;
	var formToUrlChecked = (options != "GET" && formToUrl == 1);
	$("input[name='formToUrl']").prop("checked", formToUrlChecked);
	
	var onlyUseLastParam = (userSettings.onlyUseLastParam == 1);
	var onlyUseLastHeader = onlyUseLastParam && !isEmptyObject(oldRequestObj.header);
	var onlyUseLastForm = onlyUseLastParam && !isEmptyObject(oldRequestObj.form);
	var onlyUseLastBody = onlyUseLastParam && !isEmptyObject(oldRequestObj.body);
	var headerValueCount = 0, formValueCount = 0;

	if(typeof oldRequestObj != 'object') {
		oldRequestObj = {};
	}
	if(typeof oldRequestObj.header != 'object') {
		oldRequestObj.header = {};
	}
	if(typeof oldRequestObj.form != 'object') {
		oldRequestObj.form = {};
	}
	for (var i = 0; i < debugGlobalParam.length; i++) {
		var item = debugGlobalParam[i];
		if (item.paramIn == 'header') {
			oldRequestObj.header[item.key] = item.value;
		} else if (item.paramIn == 'form') {
			oldRequestObj.form[item.key] = item.value;
		}
	}
	Object.keys(requestParamObj).forEach(function(key){
		var tempParam = requestParamObj[key];
		if (key == "p-body-obj") {
			//console.log(tempParam);
			var paramObj = onlyUseLastBody ? {} : getParamBodyTransObj(tempParam);
			var bodyObj = oldRequestObj.body;
			try {
				bodyObj = JSON.parse(bodyObj);
				if(!isEmptyObject(bodyObj)) {
					paramObj = $.extend(true, paramObj, bodyObj);
				}
				$("#tabParamTypeBody textarea").val(JSON.stringify(paramObj, null, 4));
			} catch (e) {
				var tempText = isEmptyObject(bodyObj) ? JSON.stringify(paramObj, null, 4) : bodyObj;
				$("#tabParamTypeBody textarea").val(tempText);
			}
			$("#tabParamBody .nav li").eq(1).find("a").click();
		} else {
			if (tempParam.paramIn == "header" && !onlyUseLastHeader) {
				//console.log(tempParam);
				var headerVal = getNotEmptyStr(tempParam.value);
				var headerObj = oldRequestObj.header;
				if(!isEmptyObject(headerObj) && isNotEmpty(headerObj[key])) {
					headerVal = headerObj[key];
					headerObj[key] = "";// 赋值为空，后面不再使用
				}
				if(isEmpty(headerVal)) {
					headerVal = getAutoFillValue(tempParam.paramType, key);
				}
				if(headerValueCount > 0) {
					$("#tabParamHeader table tbody").append(getParamTableTr(key, headerVal, "", tempParam.paramDesc));
				} else {
					$("#tabParamHeader table tbody .base input[name=paramName]").val(key);
					$("#tabParamHeader table tbody .base input[name=paramValue]").val(headerVal);
					$("#tabParamHeader table tbody .base input[name=paramValue]").attr("placeholder", getNotEmptyStr(tempParam.paramDesc));
				}
				headerValueCount++;
			} else {
				// 只有这几种类型，列出来后面看单独处理不
				var paramInForm = (tempParam.paramIn == "query")
						|| (tempParam.paramIn == "path")
						|| (tempParam.paramIn == "body")
						|| (tempParam.paramIn == "form")
						|| (tempParam.paramIn == "formData")
						|| isNotEmpty(tempParam.paramIn);
				paramInForm = paramInForm && !onlyUseLastForm;
				var formObj = oldRequestObj.form;
				if (onlyUseLastForm && !isEmptyObject(formObj) && isNotEmpty(formObj[key])) {
					paramInForm = onlyUseLastForm || true;
				}
				if (paramInForm) {
					//console.log(tempParam);
					var formVal = getNotEmptyStr(tempParam.value);
					if(!isEmptyObject(formObj) && isNotEmpty(formObj[key])) {
						formVal = formObj[key];
						formObj[key] = "";// 赋值为空，后面不再使用
					}
					if(isEmpty(formVal)) {
						formVal = getAutoFillValue(tempParam.paramType, key);
					}
					if(formValueCount > 0) {
						$("#tabParamTypeForm table tbody").append(getParamTableTr(key, formVal, "", tempParam.paramDesc, tempParam.paramType));
					} else {
						// 文件的input特殊处理
						if(tempParam.paramType == 'file'){
							$("#tabParamTypeForm table tbody .base input[name=paramValue]")
								.attr("type", "file").attr("multiple", "multiple")
								.addClass("file-input");
						} else {
							$("#tabParamTypeForm table tbody .base input[name=paramValue]").val(formVal);
						}
						$("#tabParamTypeForm table tbody .base input[name=paramName]").val(key);
						$("#tabParamTypeForm table tbody .base input[name=paramValue]").attr("placeholder", getNotEmptyStr(tempParam.paramDesc));
					}
					$("#tabParamBody .nav li").eq(0).find("a").click();
					formValueCount++;
				}
			}
		}
	});
	// 处理参数外的header
	var headerObj = oldRequestObj.header;
	if(!isEmptyObject(headerObj)) {
		Object.keys(headerObj).forEach(function(key){
			if(isNotEmpty(headerObj[key])) {
				if(headerValueCount > 0) {
					$("#tabParamHeader table tbody").append(getParamTableTr(key, headerObj[key], "", ""));
				} else {
					$("#tabParamHeader table tbody .base input[name=paramName]").val(key);
					$("#tabParamHeader table tbody .base input[name=paramValue]").val(headerObj[key]);
				}
				headerValueCount++;
			}
		});
	}
	// 处理参数外的form
	var formObj = oldRequestObj.form;
	if(!isEmptyObject(formObj)) {
		Object.keys(formObj).forEach(function(key){
			if(isNotEmpty(formObj[key])) {
				if(formValueCount > 0) {
					$("#tabParamTypeForm table tbody").append(getParamTableTr(key, formObj[key], "", ""));
				} else {
					$("#tabParamTypeForm table tbody .base input[name=paramName]").val(key);
					$("#tabParamTypeForm table tbody .base input[name=paramValue]").val(formObj[key]);
				}
				formValueCount++;
			}
		});
	}
	if(headerValueCount > 0) {
		$("#tabParamHeader table tbody").append(getParamTableTr("", "", "", ""));
	}
	if(formValueCount > 0) {
		$("#tabParamTypeForm table tbody").append(getParamTableTr("", "", "", ""));
	}
}

/**
 * 获取测试的对象
 */
function getParamBodyTransObj(paramObj) {
	var newObject = $.extend(true, {}, paramObj);
	Object.keys(newObject).forEach(function(key){
		var subObj = newObject[key];
		if(typeof subObj == 'object') {
			if (subObj.hasOwnProperty("isParamObj")) {
				var value = getNotEmptyStr(subObj.value);
				if(isEmpty(value) && isNotEmpty(subObj.paramType)) {
					value = getAutoFillValue(subObj.paramType, key);
				}
				newObject[key] = value;
			} else if(subObj instanceof Array) {
				subObj[0] = getParamBodyTransObj(subObj[0]);
			} else {
				newObject[key] = getParamBodyTransObj(subObj);
			}
		}
	});
	return newObject;
}

function serializeArrayToBulkEdit(formArr) {
	var formObj = serializeArrayToObj(formArr);
	var formStr = "";
	Object.keys(formObj).forEach(function(key){
		formStr += key + ":" + formObj[key] + "\n";
	});
	return formStr;
}

function serializeArrayToObj(formArr) {
	var paramObj = {};
	for (var i = 0; i < formArr.length; i++) {
		if (formArr[i].name == "paramName" && i < formArr.length) {
			var key = formArr[i].value;
			var value = formArr[i + 1].value;
			if (isNotEmpty(key) && formArr[i + 1].name != "paramName") {
				paramObj[key] = value;
			}
		}
	}
	return paramObj;
}

function bulkEditToTable(tableId, bulkEdit) {
	$(tableId + " table tbody .new").remove();
	var valueCount = 0;
	var headerArr = bulkEdit.split("\n");
	for (var i = 0; i < headerArr.length; i++) {
		var index = headerArr[i].indexOf(":");
		if(index < 0) {
			continue;
		}
		var key = headerArr[i].substring(0, index);
		var value = headerArr[i].substring(index+1, headerArr[i].length);
		if(valueCount > 0) {
			$(tableId + " table tbody").append(getParamTableTr(key, value, "", ""));
		} else {
			$(tableId + " table tbody .base input[name=paramName]").val(key);
			$(tableId + " table tbody .base input[name=paramValue]").val(value);
		}
		valueCount++;
	}
	if(valueCount > 0) {
		$(tableId + " table tbody").append(getParamTableTr("", "", "", ""));
	}
}

/**
 * 获取参数的tr
 * @param name
 * @param value
 * @param namePl
 * @param valuePl
 * @returns
 */
function getParamTableTr(name, value, namePl, valuePl, paramType) {
	name = getNotEmptyStr(name);
	namePl = getNotEmptyStr(namePl);
	value = getNotEmptyStr(value);
	valuePl = getNotEmptyStr(valuePl);
	
	var regExp = new RegExp("\"", "gm");
	name = (typeof name === 'string') ? name.replace(regExp, "&quot;") : name;
	namePl = (typeof namePl === 'string') ? namePl.replace(regExp, "&quot;") : namePl;
	value = (typeof value === 'string') ? value.replace(regExp, "&quot;") : value;
	valuePl = (typeof valuePl === 'string') ? valuePl.replace(regExp, "&quot;") : valuePl;
	
	var resultStr =
		'<tr class="new">'
			+'<td><input type="text" class="form-control" name="paramName" value="'+name+'" placeholder="'+namePl+'"></td>';
	// 文件的input特殊处理
	if(paramType == 'file') {
		resultStr += '<td><input type="file" multiple class="form-control file-input" name="paramValue" placeholder="'+valuePl+'"></td>';
	} else {
		resultStr += '<td><input type="text" class="form-control" name="paramValue" value="'+value+'" placeholder="'+valuePl+'"></td>'
	}
	resultStr += '<td><i class="icon-times"></i></td>'
		+'</tr>';
	return resultStr;
}

/**
 * 获取自动填充的值
 * @param paramType
 * @returns
 */
function getAutoFillValue(paramType, paramName) {
	if(userSettings.autoFillParam == 0 || isEmpty(paramType)) {
		return "";
	}
	paramName = getNotEmptyStr(paramName).toLowerCase();
	var isTimeColumn = (paramType.indexOf("date-time") >= 0
			|| paramName.endWith("date") || paramName.endWith("time"));
	var isTypeColumn = (paramName.endWith("type") || paramName.endWith("status")
			|| paramName.endWith("level") || paramName.endWith("num"));
	var isPriceColumn = (paramName.endWith("money") || paramName.endWith("price")
			|| paramName.endWith("cash") || paramName.endWith("coin"));
	var isBooleanColumn = (paramName.startWith("is"));
	var resultValue = "";
	if(paramType.indexOf("int") >= 0){
		if(isTypeColumn) {
			resultValue = Math.ceil(Math.random() * 5);
		} else if(paramName.endWith("age")) {
			resultValue = Math.ceil(Math.random() * 100);
		} else {
			resultValue = Math.ceil(Math.random() * 100);
		}
	} else if(paramType.indexOf("double") >= 0){
		resultValue = Math.ceil(Math.random() * 1000);
	} else if(paramType.indexOf("float") >= 0){
		resultValue = parseFloat(Math.random() * 1000).toFixed(2);
	} else if(paramType.indexOf("byte") >= 0){
		if(isTypeColumn) {
			resultValue = Math.ceil(Math.random() * 5);
		} else {
			resultValue = Math.ceil(Math.random() * 127);
		}
	} else if(paramType.indexOf("boolean") >= 0){
		resultValue = Math.random() > 0.5;
	} else if(isTimeColumn){
		resultValue = getNowDateTime();
	} else {
		if(paramName.endWith("id")) {
			resultValue = Math.ceil(Math.random() * 1000);
		} else if(paramName.endWith("age")) {
			resultValue = Math.ceil(Math.random() * 100);
		} else if(isPriceColumn){
			resultValue = parseFloat(Math.random() * 1000).toFixed(2);
		} else if(isTypeColumn){
			resultValue = Math.ceil(Math.random() * 5);
		} else if(isBooleanColumn){
			resultValue = (Math.random() > 0.5) ? 0 : 1;
		} else if(paramName.endWith("phone") || paramName.endWith("mobile")){
			var arr = ["15226645814", "15226645815", "15226645816", "15226645817", "15226645818"];
			resultValue = arr[Math.ceil(Math.random() * 5) - 1];
		} else if(paramName.endWith("ids")){
			var counts = Math.ceil(Math.random() * 5);
			for (var i = 0; i < counts; i++) {
				if(isNotEmpty(resultValue)){resultValue += ",";}
				resultValue += Math.ceil(Math.random() * 1000);
			}
		} else if(paramName.endWith("types")){
			var counts = Math.ceil(Math.random() * 3);
			for (var i = 0; i < counts; i++) {
				if(isNotEmpty(resultValue)){resultValue += ",";}
				resultValue += Math.ceil(Math.random() * 5);
			}
		} else if(paramName.endWith("md5")){
			resultValue = "5082079d92a8ef985f59e001d445ff20";
		} else if(paramName.endWith("photo")){
			resultValue = "http://www.zyplayer.com/freeplay/img/headIcon/myhead.jpg";
		} else if(paramName.endWith("url") || paramName.endWith("uri")){
			var arr = ["http://www.zyplayer.com", "http://www.kongjianzhou.com"];
			resultValue = arr[Math.ceil(Math.random() * 2) - 1];
		} else if(paramName.endWith("username")){
			var arr = ["张三", "李四", "王二", "暮光：城中城", "海贼王"];
			resultValue = arr[Math.ceil(Math.random() * 5) - 1];
		} else if(userSettings.autoFillParam == 2){
			//var arr = ["您好！","请！","对不起。","谢谢！","再见！","您早！","晚安！","请问您贵姓？","请原谅！","不用谢！","没关系！","欢迎您光临！","请坐！","请喝茶！","请多关照！","请多指教！","谢谢您的合作！","对不起，让您久等了。","没关系，我刚到。","给您添麻烦了。","我能为您做什么？","您好，请问您需要帮助吗？","您走好。","请慢走！"];
			//resultValue = arr[Math.ceil(Math.random() * 24) - 1];
			resultValue = "我是默认字符串";
		}
		//console.log(paramType);
	}
	return resultValue;
}
