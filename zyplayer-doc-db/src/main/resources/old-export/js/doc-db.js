
$(document).ready(function(){
	$('select.chosen-select').chosen({
		no_results_text: '没有找到',    // 当检索时没有找到匹配项时显示的提示文本
		disable_search_threshold: 0, // 10 个以下的选择项则不显示检索框
		search_contains: true,         // 从任意位置开始检索
		width: '100%'
	});
	$('#choiseTable').on('change', function(e){
		var tableName = $("#choiseTable").val();
		var columnList = database.columnList[tableName];
		$("#tableCloumnsTable tbody").empty();
		for (var i = 0; i < columnList.length; i++) {
			var item = columnList[i];
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
		var tableList = database.tableList;
		for (var i = 0; i < tableList.length; i++) {
			if(tableList[i].tableName == tableName) {
				$(".table-desc").text(getNotEmptyStr(tableList[i].description));
				break;
			}
		}
	});
	initData();
});

function initData(){
	$('#choiseTable').empty();
	$("#choiseTable").append('<option value=""></option>');
	var tableList = database.tableList;
	for (var i = 0; i < tableList.length; i++) {
		var tableName = tableList[i].tableName;
		var description = tableList[i].description;
		var infoShow = tableName;
		if(isNotEmpty(description)) {
			infoShow += "(" + description + ")";
		}
		$("#choiseTable").append('<option value="'+tableName+'">' + infoShow + '</option>');
	}
	$('#choiseTable').trigger('chosen:updated');
}

