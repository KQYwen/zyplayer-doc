/**
 * zyplayer-doc-swagger是swagger的一个前端实现，使用简单、解析速度快、走心的设计
 * 支持多项目同时展示，多种文档目录的展示方案，多种自定义配置，满足各种使用习惯。
 * 使用中您有任何的意见和建议都可到源码地址处反馈哦！
 * git地址：https://gitee.com/zyplayer/zyplayer-doc
 * @author 暮光：城中城
 * @since 2018年5月20日
 */
// 树的下表
var projectTreeIdIndex = 1;
// 依据目录树存储的map全局对象
var treePathDataMap = new Map();
// dubbo列表
var dubboDocList = [];

/**
 * 网页加载完毕后的处理
 */
$(document).ready(function(){
	globalLoadingMessager = new $.zui.Messager({type: 'primary', close: false, time: 0}).show();
	showGlobalLoadingMessage('获取文档列表中，请稍候...', true);
	ajaxTemp("zyplayer-doc-dubbo/doc-dubbo/getDocList", "get", "json", {}, function (json) {
		if (validateResult(json)) {
			dubboDocList = json.data || [];
			createTreeViewByTree(dubboDocList);
			initDashboard();
			documentLoadFinish();
		}
	});
	// 定义配置的标签页
	var tabsArr = [
		{id: 'docShowConfig', url: 'webjars/doc-dubbo-pages/docShowConfig.html', type: 'iframe', icon: 'icon-cog', forbidClose: true}
	];
	$('#rightZpages').tabs({tabs: tabsArr});
	rightContentTabs = $('#rightZpages').data('zui.tabs');
});

/**
 * 增加文档-通过服务器接口
 * @returns
 */
function addDocumentByService(choiceDocList) {
	// 获取原始的swagger的json对象
	showGlobalLoadingMessage('通过服务器端获取文档中，请稍候...', true);
	ajaxTemp("swagger-mg-ui/document/docs", "post", "json", {choiceDocList: choiceDocList}, function(json){
		//console.log(json);
		showGlobalLoadingMessage('文档获取成功，解析中，请稍候...', true);
		if(isEmptyObject(json) || json.errCode !== 200) {
			Toast.error("获取文档错误，请检查！");
			return;
		}
		for (var i = 0; i < json.data.length; i++) {
			showGlobalLoadingMessage('解析第'+(i+1)+'份文档，请稍候...', true);
			var tempDoc = deserialize(json.data[i]);
			console.log(tempDoc);
			documentJsonArr.push(tempDoc);// 加到所有文档
			addHomePageDashboard(tempDoc, tempDoc.fullUrl);
			createDefinitionsMapByJson(tempDoc);
			if(userSettings.catalogShowType == 1) {
				createTreeViewByTree(tempDoc);// url分成一层一层的展示
			} else if(userSettings.catalogShowType == 2){
				createTreeViewByTag(tempDoc);// tag方式，整个url显示为一层
			} else {
				createTreeViewByTree(tempDoc);// url分成一层一层的展示
			}
		}
		documentLoadFinish();
	}, function(msg){
		Toast.error("获取文档失败，请检查！");
	});
}

/**
 * 增加文档-通过服务器接口
 * @returns
 */
function addDocumentByLocationService(choiceDocList) {
	// 获取原始的swagger的json对象
	globalLoadingMessager.show();
	showGlobalLoadingMessage('通过服务器端获取文档中，请稍候...', true);
	ajaxTemp("swagger-mg-ui/document/docs", "post", "json", {choiceLocationList: choiceDocList}, function(json){
		//console.log(json);
		showGlobalLoadingMessage('文档获取成功，解析中，请稍候...', true);
		if(isEmptyObject(json) || json.errCode !== 200) {
			Toast.error("获取文档错误，请检查！");
			return;
		}
		documentJsonArr = [];
		initDashboard();

		for (var i = 0; i < json.data.length; i++) {
			showGlobalLoadingMessage('解析第' + (i + 1) + '份文档，请稍候...', true);
			var tempDoc = deserialize(json.data[i]);
			console.log(tempDoc);
			documentJsonArr.push(tempDoc);// 加到所有文档
			addHomePageDashboard(tempDoc, tempDoc.fullUrl);
			createDefinitionsMapByJson(tempDoc);
			if (userSettings.catalogShowType == 1) {
				createTreeViewByTree(tempDoc);// url分成一层一层的展示
			} else if (userSettings.catalogShowType == 2) {
				createTreeViewByTag(tempDoc);// tag方式，整个url显示为一层
			} else {
				createTreeViewByTree(tempDoc);// url分成一层一层的展示
			}
		}
		documentLoadFinish();
	}, function(msg){
		Toast.error("获取文档失败，请检查！");
	});
}

/**
 * 自由拖动改变左右框架的宽度
 */
$("#resizableLeftRight").mgResizableWidth({
	prev:"#leftContent",
	prevWtMin: 120, prevWtMax: 999999,
	nextWtMin: 360, nextWtMax: 999999,
	onresize:function(prevWNow, nextWNow){
		changeContentWidth(prevWNow);
	},
	onstart:function(){
		$("body").addClass("unselect");
		$("#rightContentMask").show();
	},
	onfinish:function(){
		$("body").removeClass("unselect");
		$("#rightContentMask").hide();
		storeUserSettings();
	}
});

/**
 * 切换导航栏的宽度到最小或最大
 */
$("#changeContentWidth").click(function(){
	var isMinWidth = ($("#leftContent").width() == 120);
	changeContentWidth(isMinWidth ? 360 : 120);
});

/**
 * 切换文档
 */
$("#choiceLocationList").on("click", ".dropdown-menu li", function(){
	var text = $(this).find("a").text();
	var location = $(this).find("a").data("location");
	$("#choiceLocationList .choice-text").text(text);
	// 切换文档
	addDocumentByLocationService(location);
});

/**
 * 搜索框回车事件
 */
$("#searchDocInput").keyup(function(e) {
	if (e.keyCode == 13) {
		searchDoc();
	}
});

/**
 * 搜索按钮点击
 */
$("#searchDocBt").click(function(){
	searchDoc();
});

/**
 * 切换选中和非选中样式
 */
$(".choise").on("click", "li", function(){
	$(this).toggleClass("checked");
});

/**
 * 页面导航切换
 */
$("#tabZpagesNavigationUl").on("click", ".page-nav", function(){
	var id = $(this).data("id");
	var href = $(this).data("href");
	var icon = $(this).data("icon");
	var reload = $(this).data("reload");
	$(".tab-page").hide();
	$(".tab-online-debug-page").hide();
	$(".tab-zpages").show();
	if(reload || $("#tab-"+id).length <= 0) {
		var newTab = {id: id, url: href, type: 'iframe', icon: icon};
		rightContentTabs.open(newTab);
	} else {
		$("#tab-nav-item-"+id+" .tab-nav-link").click();
	}
});

/**
 * 主页li点击事件，展示主页
 */
$("#homePageLi").click(function(){
	$(".tab-page,.tab-zpages").hide();
	$(".tab-home-page").show();
});

/**
 * api文档最后的节点点击，展示文档页面
 */
$("#apiPathTree").on("click", ".show-doc", function(){
	$(".tab-page").hide();
	$(".tab-document").show();
	var path = $(this).attr("path");
	var method = $(this).attr("method");
	var data = treePathDataMap.get(path);
	var docInfo = "";//isNotEmpty(data.docInfo) ? getNotEmptyStr(data.docInfo.explain) : "";
	var docUrl = getNotEmptyStr(data.interface);
	$("#postUrlInput").val(data.interface + "#" + method + "()");
	// 处理在线文档
	$("#docUrl").text(docUrl);
	$("#docRequestMethod").text(method);
	$("#docInfo").text(docInfo);
	$("#docConsumes").text(arrToString(data.consumes));
	$("#docProduces").text(arrToString(data.produces));
	$("#tabParamTypeBody textarea").val("");
	// 遍历参数列表
	$("#docRequestParam table tbody").empty();
	$("#docRequestExample table tbody").empty();
	requestParamObj = {};
	Formatjson.annotationObject = {};
	//addRequestParamObj(requestParamObj, "Content-Type", "string", "header", false, "", arrToString(data.consumes));
	if(isNotEmpty(data.parameters)) {
		Object.keys(data.parameters).forEach(function(key){
			var tempParameters = data.parameters[key];
			var htmlStr = "", htmlStrExample = "";
			var required = tempParameters.required;
			var paramName = getNotEmptyStr(tempParameters.name);
			var paramType = getNotEmptyStr(tempParameters.type);
			var paramDesc = getNotEmptyStr(tempParameters.description);
			var paramIn = getNotEmptyStr(tempParameters.in);
			var example = getNotEmptyStr(tempParameters.example, tempParameters.default);
			$("#docRequestParam table tbody").append(
				'<tr>'
					+'<td>' + htmlStr + '</td>'
					+'<td>' + paramDesc + '</td>'
					+'<td>' + paramType + '</td>'
					+'<td>' + paramIn + '</td>'
					+'<td>' + required + '</td>'
				+'</tr>'
			);
		});
	}
//	console.log(requestParamObj);
//	var htmlStrTemp = Formatjson.processObjectToHtmlPre(requestParamObj, 0, false, false, false);
//	$("#htmlStrTemp").html(htmlStrTemp);
	$("#htmlStrTemp").parents("tr").hide();
	//createOnlineDebugRequestParam(requestParamObj, getNotEmptyStr(data.url));
	// 遍历结果集列表
	$("#docResponseModel table tbody").empty();
	$("#docResponseExample table tbody").empty();
	Formatjson.annotationObject = {};
});

/**
 * 搜索文档
 * @returns
 */
function searchDoc() {
	var keywords = $("#searchDocInput").val();
	// 重新生成
	regeneratePathTree(keywords);
	if (isEmpty(keywords)){
		return;
	}
	$('#apiPathTree .projects').tree('expand');
}

/**
 * 重新生成文档
 */
function regeneratePathTree(keywords){
	projectTreeIdIndex = 1;
	treePathDataMap = new Map();
	$('#apiPathTree').empty();
	$('#apiPathTree').append('<ul class="tree tree-lines projects"></ul>');
	createTreeViewByTree(dubboDocList, keywords);// url分成一层一层的展示
	$('#apiPathTree .projects').tree();
	updateTreeShowType();
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

/**
 * 修改左右框架的宽度
 * @param width 左侧导航栏的宽度
 * @returns
 */
function changeContentWidth(width) {
	$("#leftContent").css("width", width + 'px');
	$("#resizableLeftRight").css("left", width + 'px');
	$("#rightContent").css("left", width + 'px');
	var logoText = "zyplayer-doc-dubbo";
	if(width < 370 && width > 290){
		logoText = "zyplayer-doc";
	} else if(width < 290){
		logoText = "doc";
	}
	$("#logoText").text(logoText);
	userSettings.prevWNow = width;
}

/**
 * 增加项目文档
 * @param json
 * @returns
 */
function addHomePageDashboard(json, fullUrl) {
	var info = json.info||{};
	var contactName = "";
	if(isNotEmpty(info.contact)) {
		contactName = "昵称：" + getNotEmptyStr(info.contact.name, "-");
		contactName += "<br/>邮箱：" + getNotEmptyStr(info.contact.email, "-");
		contactName += "<br/>网站：" + getNotEmptyStr(info.contact.url, "-");
	}
	$("#homePageDashboard section").append(
		'<div class="col-md-6 col-sm-6">'
			+'<div class="panel" data-id="'+fullUrl+'">'
				+'<div class="panel-heading">'
					+'<div class="title">'+info.title+'</div>'
					+'<div class="panel-actions">'
					+'</div>'
				+'</div>'
				+'<div class="panel-body">'
					+'<div class="content">'
						+'<table class="table table-bordered setting-table">'
							+'<tr>'
								+'<td class="info">简介</td>'
								+'<td>'+info.description+'</td>'
							+'</tr>'
							+'<tr>'
								+'<td class="info">作者</td>'
								+'<td>'+contactName+'</td>'
							+'</tr>'
							+'<tr>'
								+'<td class="info">版本</td>'
								+'<td>'+info.version+'</td>'
							+'</tr>'
							+'<tr>'
								+'<td class="info">地址</td>'
								+'<td>'+decodeURI(fullUrl)+'</td>'
							+'</tr>'
						+'</table>'
					+'</div>'
				+'</div>'
			+'</div>'
		+'</div>'
	);
}

/**
 * 修改树形菜单展示类型
 * @param
 * @returns
 */
function updateTreeShowType() {
	return;
	$('#apiPathTree .projects').removeClass("tree-angles tree-menu tree-folders tree-chevrons");
	//tree-angles、2=tree-menu、3=默认，4=tree-folders、5=tree-chevrons
	$('#apiPathTree .projects').addClass("tree-lines");
	var treeShowType = "tree-angles";
	if(userSettings.treeShowType == 1) {
		treeShowType = "tree-angles";
	} else if(userSettings.treeShowType == 2) {
		treeShowType = "tree-menu";
		$('#apiPathTree .projects').removeClass("tree-lines");
	} else if(userSettings.treeShowType == 3) {
		treeShowType = "";
	} else if(userSettings.treeShowType == 4) {
		treeShowType = "tree-folders";
	} else if(userSettings.treeShowType == 5) {
		treeShowType = "tree-chevrons";
	} else {
		userSettings.treeShowType = 1;
	}
	if(isNotEmpty(treeShowType)) {
		$('#apiPathTree .projects').addClass(treeShowType);
	}
}

/**
 * 初始化用户的设置
 * @param
 * @returns
 */
function documentLoadFinish() {
	showGlobalLoadingMessage('文档解析完成！', false);
	// 隐藏提示框
	setTimeout(function() {
		globalLoadingMessager.hide();
	}, 1000);
	regeneratePathTree();
	//$('#apiPathTree .projects').tree();
	$('#homePageDashboard .dashboard').dashboard({draggable: false});
}

function showGlobalLoadingMessage(text, loading) {
	if(loading) {
		text += '<i class="icon icon-spin icon-spinner-snake hide"></i>';
	}
	globalLoadingMessager.$.find(".messager-content").html(text);
}

function initDashboard(){
	var template = $('#homePageDashboardTemplate').html();
	$('#homePageDashboard').empty();
	$('#homePageDashboard').append('<div class="dashboard" data-height="320"></div>');
	$('#homePageDashboard .dashboard').append(template);
}
