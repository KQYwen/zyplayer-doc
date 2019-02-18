
var pageConfig = {
	host: '',
	tableRelationDataIndex: 0,
}

// 表关系图表
var tableRelationCharts;

$(document).ready(function(){
	// 无论发布在哪、如何修改源码，请勿删除本行原作者信息，感谢
	console.log("%c项目信息：\n开发者列表：暮光：城中城\n项目地址：https://gitee.com/zyplayer/zyplayer-doc","color:red");
	$('select.chosen-select').chosen({
		no_results_text: '没有找到',    // 当检索时没有找到匹配项时显示的提示文本
		disable_search_threshold: 0, // 10 个以下的选择项则不显示检索框
		search_contains: true,         // 从任意位置开始检索
		width: '100%'
	});
	$('#checkAllTable').click(function(e){
		$("#exportUl li").addClass("checked");
	});
	$('#notCheckAllTable').click(function(e){
		$("#exportUl li").removeClass("checked");
	});
	$('#choiseExportDatabase').on('change', function(e){
		var host = $('#choiseExportHost').val();
		var dbName = $('#choiseExportDatabase').val();
		postService("zyplayer-doc-db/doc-db/getTableList", {host: host, dbName: dbName}, function(result){
			$('#exportUl').empty();
			for (var i = 0; i < result.data.length; i++) {
				$("#exportUl").append('<li>'+result.data[i].tableName+'</li>');
			}
			$('#exportModal').modal('ajustPosition', 'fit');
		});
	});
	$('#choiseExportHost').on('change', function(e){
		var host = $(this).val();
		postService("zyplayer-doc-db/doc-db/getDatabaseList", {host: host}, function(result){
			$('#choiseExportDatabase').empty();
			$("#choiseExportDatabase").append('<option value=""></option>');
			for (var i = 0; i < result.data.length; i++) {
				var dbName = result.data[i].dbName;
				$("#choiseExportDatabase").append('<option value="'+dbName+'">'+dbName+'</option>');
			}
			$('#choiseExportDatabase').trigger('chosen:updated');
		});
	});
	$('#choiseFuzzyHost').on('change', function(e){
		var host = $(this).val();
		postService("zyplayer-doc-db/doc-db/getDatabaseList", {host: host}, function(result){
			$('#choiseFuzzyDatabase').empty();
			$("#choiseFuzzyDatabase").append('<option value=""></option>');
			for (var i = 0; i < result.data.length; i++) {
				var dbName = result.data[i].dbName;
				$("#choiseFuzzyDatabase").append('<option value="'+dbName+'">'+dbName+'</option>');
			}
			$('#choiseFuzzyDatabase').trigger('chosen:updated');
		});
	});
	$('#choiseHost').on('change', function(e){
		pageConfig.host = $(this).val();
		postService("zyplayer-doc-db/doc-db/getDatabaseList", {host: pageConfig.host}, function(result){
			$('#choiseDatabase').empty();
			$('#choiseTable').empty();
			$("#choiseDatabase").append('<option value=""></option>');
			$("#choiseTable").append('<option value=""></option>');
			for (var i = 0; i < result.data.length; i++) {
				var dbName = result.data[i].dbName;
				$("#choiseDatabase").append('<option value="'+dbName+'">'+dbName+'</option>');
			}
			$('#choiseDatabase').trigger('chosen:updated');
			$('#choiseTable').trigger('chosen:updated');
		});
	});
	$('#choiseDatabase').on('change', function(e){
		var dbName = $(this).val();
		var param = {host: pageConfig.host, dbName: dbName};
		postService("zyplayer-doc-db/doc-db/getTableList", param, function(result){
			$('#choiseTable').empty();
			$("#choiseTable").append('<option value=""></option>');
			for (var i = 0; i < result.data.length; i++) {
				var tableName = result.data[i].tableName;
				$("#choiseTable").append('<option value="'+tableName+'">'+tableName+'</option>');
			}
			$('#choiseTable').trigger('chosen:updated');
		});
	});
	$('#choiseTable').on('change', function(e){
		getTableColumnList();
	});
	$('#tableDescShow .icon-edit').click(function(e){
		var nowDesc = $("#tableDescShow .table-desc").html();
		$("#tableDescInput").val((nowDesc == "暂无注释") ? "" : nowDesc);
		$("#tableDescShow").hide();
		$("#tableDescInput").show().focus();
	});
	$("#tableDescInput").blur(function(){
		$("#tableDescShow").show();
		$("#tableDescInput").hide();
		updateTableDesc();
	});
	$("#fuzzySearchBtn").click(function(){
		$('#fuzzySearchModal').modal({moveable:true, backdrop: 'static'});
		$('#choiseFuzzyHost').trigger('chosen:updated');
	});
	$("#exportBtn").click(function(){
		$('#exportModal').modal({moveable:true, backdrop: 'static'});
	});
	$("#doExport").click(function(){
		var host = $('#choiseExportHost').val();
		var dbName = $('#choiseExportDatabase').val();
		var tableNames = "";
		$('#exportUl li.checked').text(function(index, value){
			if(tableNames != "") {tableNames += ",";}
			tableNames += value;
		});
		if(isEmpty(host)){
			Toast.warn("请先选择库名");return;
		}
		if(isEmpty(dbName)){
			Toast.warn("请先选择库名");return;
		}
		if(isEmpty(tableNames)){
			Toast.warn("请输选择需要导出的表");return;
		}
		window.open("zyplayer-doc-db/doc-db/exportDatabase?host="+host
				+"&dbName="+dbName
				+"&tableNames="+tableNames);
	});
	$("#exportUl").on("click", "li", function(){
		$(this).toggleClass("checked");
	});
	$("#showEchartsBtn").click(function(){
		$("#tableRelationBox").toggleClass("hide");
		if($("#tableRelationBox").hasClass("hide")){
			$(this).text("显示表关系图");
		} else {
			$(this).text("隐藏表关系图");
			tableRelationCharts.resize();
		}
	});
	$("#doSearch").click(function(){
		fuzzySearchByDesc();
	});
	$("#aboutBtn").click(function(){
		$('#aboutModal').modal({moveable:true, backdrop: 'static'});
	});
	$("#tableCloumnsTable tbody").on("click", ".column-desc", function(event){
		$(this).find("span").hide();
		$(this).find(".desc-input").show().focus();
	}).on("blur", ".column-desc", function(event){
		var newDesc = $(this).find(".desc-input").val();
		var columnName = $(this).find(".desc-input").attr("column");
		var oldDesc = $(this).find("span").text();
		$(this).find("span").show();
		$(this).find(".desc-input").hide();
		if(newDesc != oldDesc){
			var objThis = this;
			var param = getChoiseDbInfo(3);
			if(param == null){
				return;
			}
			param.newDesc = newDesc;
			param.columnName = columnName;
			postService("zyplayer-doc-db/doc-db/updateTableColumnDesc", param, function(msg){
				$(objThis).find("span").text(newDesc);
			});
		}
	});
	initData();
});

function initData(){
	postService("zyplayer-doc-db/doc-db/getDataSourceList", {}, function(result){
		$('#choiseHost,#choiseFuzzyHost,#choiseExportHost').empty();
		if(validateResult(result)) {
			$("#choiseHost,#choiseFuzzyHost,#choiseExportHost").append('<option value=""></option>');
			for (var i = 0; i < result.data.length; i++) {
				$("#choiseHost,#choiseFuzzyHost,#choiseExportHost").append('<option value="'+result.data[i]+'">'+result.data[i]+'</option>');
			}
		}
		$('#choiseHost').trigger('chosen:updated');
		$('#choiseFuzzyHost').trigger('chosen:updated');
		$('#choiseExportHost').trigger('chosen:updated');
	});
	tableRelationCharts = echarts.init(document.getElementById('tableRelationCharts'));
	tableRelationCharts.setOption(tableRelationOption);
	tableRelationCharts.on('click', function (params) {
		//console.log(params.data);
		var dataIndex = params.data.index;
		var description = params.data.description;
		// 用户ID，T:user_info.id
		// 用户ID，T:order_db.user_info.id
		var indexOf = description.indexOf("T:");
		if(indexOf < 0) {
			Toast.warn("该字段未关联表");
			return;
		}
		var dbAndTable = description.substring(indexOf + 2, description.length);
		var dbNameChoise, tableNameChoise;
		var tempSplit = dbAndTable.split(".");
		if(tempSplit.length == 3) {
			dbNameChoise = tempSplit[0];
			tableNameChoise = tempSplit[1];
		} else if(tempSplit.length == 2) {
			tableNameChoise = tempSplit[0];
		} else {
			tableNameChoise = dbAndTable;
		}
		var children = findChildren(tableRelationData, dataIndex);
		if(children != null && children.children.length <= 0) {
			// 查询表字段
			var param = getChoiseDbInfo(2);
			if(param == null){
				return;
			}
			if(isNotEmpty(dbNameChoise)){
				param.dbName = dbNameChoise;
			}
			param.tableName = tableNameChoise;
			postService("zyplayer-doc-db/doc-db/getTableColumnList", param, function(msg){
				var columnList = msg.data.columnList
				if(!columnList || columnList.length <= 0) {
					Toast.warn("未找到该字段关联的表信息");return;
				}
				changetableRelationOption(children, columnList);
				tableRelationCharts.setOption(tableRelationOption);
				var height = pageConfig.tableRelationDataIndex * 25;
				$("#tableRelationBox").css("height", height + "px");
				tableRelationCharts.resize();
			});
		}
	});
}

function changetableRelationOption(data, columnList){
	var childrenArr = [];
	for (var i = 0; i < columnList.length; i++) {
		var item = columnList[i];
		var description = getNotEmptyStr(item.description);
		var indexOf = description.indexOf("T:");
		var dbAndTable = "";
		if(indexOf >= 0) {
			dbAndTable = " " + description.substring(indexOf + 2, description.length);
		}
		childrenArr.push({
			name: item.name + " " + item.type + dbAndTable,
			children: [],
			collapsed: false,
			index: pageConfig.tableRelationDataIndex++,
			description: getNotEmptyStr(item.description),
			length: item.length
		});
	}
	data.children = childrenArr;
}

function findChildren(source, dataIndex){
	var result = null;
	echarts.util.each(source.children, function (datum, index) {
		if(result != null) {
			return;
		}
		if(datum.index == dataIndex) {
			result = datum;
		} else if(datum.children && datum.children.length > 0) {
			result = findChildren(datum, dataIndex);
		}
	});
	if(result != null) {
		return result;
	}
}

function getTableColumnList(){
	var param = getChoiseDbInfo(3);
	if(param == null){
		return;
	}
	$("#tableDescShow .icon-edit").hide();
	$("#tableCloumnsTable tbody").empty();
	$(".table-name").text(param.tableName);
	$(".table-desc").text("暂无注释");
	postService("zyplayer-doc-db/doc-db/getTableColumnList", param, function(msg){
		var columnList = msg.data.columnList
		for (var i = 0; i < columnList.length; i++) {
			var item = columnList[i];
			//var isidentity = isNull(item.isidentity) ? '-' : (1 == item.isidentity ? '是' : '否');
			$("#tableCloumnsTable tbody").append(
				'<tr>'
					+'<td>' + item.name + '</td>'
					+'<td>' + (1 == item.isidentity ? '是' : '否') + '</td>'
					+'<td>' + getNotEmptyStr(item.type) + '</td>'
					+'<td>' + getNotEmptyStr(item.length) + '</td>'
					+'<td>' + (1 == item.nullable ? '允许' : '不允许') + '</td>'
					+'<td>' + ("true" == item.ispramary ? '是' : '否') + '</td>'
					+'<td class="column-desc"><span>' + getNotEmptyStr(item.description) + '</span>'
					+'<input type="text" class="desc-input form-control" style="display:none;width: 100%;" column="' + item.name + '" value="' + (isEmpty(item.description)?'':item.description) + '">'
				+'</tr>'
			);
		}
		var tableInfo = msg.data.tableInfo;
		if (!!tableInfo && isNotEmpty(tableInfo.description)) {
			$(".table-desc").text(getNotEmptyStr(tableInfo.description));
		}
		$("#tableDescShow .icon-edit").show();
		pageConfig.tableRelationDataIndex = 0;
		changetableRelationOption(tableRelationData, columnList);
		tableRelationData.name = tableInfo.tableName;
		tableRelationCharts.setOption(tableRelationOption);
		var height = pageConfig.tableRelationDataIndex * 25;
		$("#tableRelationBox").css("height", height + "px");
		tableRelationCharts.resize();
	});
}

function updateTableDesc(){
	var tableDescInput = $("#tableDescInput").val();
	var param = getChoiseDbInfo(3);
	if(param == null){
		return;
	}
	param.newDesc = tableDescInput;
	postService("zyplayer-doc-db/doc-db/updateTableDesc", param, function(msg){
		$("#tableDescShow .table-desc").text(tableDescInput);
	});
}

function getChoiseDbInfo(type) {
	var host = $("#choiseHost").val();
	var dbName = $("#choiseDatabase").val();
	var tableName = $("#choiseTable").val();
	if(type >= 1 && isEmpty(host)){
		Toast.warn("请先选择连接");return null;
	}
	if(type >= 2 && isEmpty(dbName)){
		Toast.warn("请先选择库名");return null;
	}
	if(type >= 3 && isEmpty(tableName)){
		Toast.warn("请先选择表名");return null;
	}
	return {host: host, dbName: dbName, tableName: tableName};
}

function fuzzySearchByDesc(){
	var host = $("#choiseFuzzyHost").val();
	var dbName = $("#choiseFuzzyDatabase").val();
	var searchText = $("#searchText").val();
	if(isEmpty(host)){
		Toast.warn("请先选择库名");return;
	}
	if(isEmpty(dbName)){
		Toast.warn("请先选择库名");return;
	}
	if(isEmpty(searchText)){
		Toast.warn("请输入模糊匹配的内容");return;
	}
	$("#doSearch .icon").removeClass("hidden");
	var param = {host: host, dbName: dbName, searchText: searchText};
	postService("zyplayer-doc-db/doc-db/getTableAndColumnBySearch", param, function(msg){
		$("#fuzzySearchTable tbody").empty();
		for (var i = 0; i < msg.data.length; i++) {
			var item = msg.data[i];
			$("#fuzzySearchTable tbody").append(
				'<tr>'
					+'<td>' + item.tableName + '</td>'
					+'<td>' + item.columnName + '</td>'
					+'<td>' + getNotEmptyStr(item.description) + '</td>'
				+'</tr>'
			);
		}
		$('#fuzzySearchModal').modal('ajustPosition', 'fit');
	}, function(){
		$("#doSearch .icon").addClass("hidden");
	});
}

