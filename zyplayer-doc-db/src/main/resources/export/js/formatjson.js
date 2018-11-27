
/**
 * 将对象处理成json格式化和着色的html
 * @author 暮光：城中城
 * @since 2017年5月7日
 */
var Formatjson = {
	tabStr: "    ",
	isArray: function(obj) {
		return obj && typeof obj === 'object' && typeof obj.length === 'number'
				&& !(obj.propertyIsEnumerable('length'));
	},
	processObjectToHtmlPre: function(obj, indent, addComma, isArray, isPropertyContent) {
		var htmlStr = this.processObject(obj, indent, addComma, isArray, isPropertyContent);
		htmlStr = '<pre class="json">' + htmlStr + '</pre>';
		return htmlStr;
	},
	processObject: function(obj, indent, addComma, isArray, isPropertyContent) {
		var html = "";
		var comma = (addComma) ? "<span class='comma'>,</span> " : "";
		var type = typeof obj;
		var clpsHtml ="";
		if (this.isArray(obj)) {
			if (obj.length == 0) {
				html += this.getRow(indent, "<span class='arrayBrace'>[ ]</span>" + comma, isPropertyContent);
			} else {
				clpsHtml = '<span><img class="option-img" src="webjars/mg-ui/img/expanded.png" onClick="Formatjson.expImgClicked(this);" /></span><span class="collapsible">';
				html += this.getRow(indent, "<span class='arrayBrace'>[</span>"+clpsHtml, isPropertyContent);
				for (var i = 0; i < obj.length; i++) {
					html += this.processObject(obj[i], indent + 1, i < (obj.length - 1), true, false);
				}
				clpsHtml = "</span>";
				html += this.getRow(indent, clpsHtml + "<span class='arrayBrace'>]</span>" + comma);
			}
		} else if (type == 'object' && obj == null) {
			html += this.formatLiteral("null", "", comma, indent, isArray, "null");
		} else if (type == 'object') {
			var numProps = 0;
			for ( var prop in obj) {
				numProps++;
			}
			if (numProps == 0) {
				html += this.getRow(indent, "<span class='objectBrace'>{ }</span>" + comma, isPropertyContent);
			} else {
				clpsHtml = '<span><img class="option-img" src="webjars/mg-ui/img/expanded.png" onClick="Formatjson.expImgClicked(this);" /></span><span class="collapsible">';
				html += this.getRow(indent, "<span class='objectBrace'>{</span>"+clpsHtml, isPropertyContent);
				var j = 0;
				for ( var prop in obj) {
					var processStr = '<span class="propertyName">"' + prop + '"</span>: ' + this.processObject(obj[prop], indent + 1, ++j < numProps, false, true);
					html += this.getRow(indent + 1, processStr);
				}
				clpsHtml = "</span>";
				html += this.getRow(indent, clpsHtml + "<span class='objectBrace'>}</span>" + comma);
			}
		} else if (type == 'number') {
			html += this.formatLiteral(obj, "", comma, indent, isArray, "number");
		} else if (type == 'boolean') {
			html += this.formatLiteral(obj, "", comma, indent, isArray, "boolean");
		} else if (type == 'function') {
			obj = this.formatFunction(indent, obj);
			html += this.formatLiteral(obj, "", comma, indent, isArray, "function");
		} else if (type == 'undefined') {
			html += this.formatLiteral("undefined", "", comma, indent, isArray, "null");
		} else {
			html += this.formatLiteral(obj, "\"", comma, indent, isArray, "string");
		}
		return html;
	},
	expImgClicked: function(img){
		var container = img.parentNode.nextSibling;
		if(!container) return;
		var disp = "none";
		var src = "webjars/mg-ui/img/collapsed.png";
		if(container.style.display == "none"){
			disp = "inline";
			src = "webjars/mg-ui/img/expanded.png";
		}
		container.style.display = disp;
		img.src = src;
	},
	formatLiteral: function(literal, quote, comma, indent, isArray, style) {
		if (typeof literal == 'string') {
			literal = literal.split("<").join("&lt;").split(">").join("&gt;");
		}
		var str = "<span class='" + style + "'>" + quote + literal + quote + comma + "</span>";
		if (isArray) {
			str = this.getRow(indent, str);
		}
		return str;
	},
	formatFunction: function(indent, obj) {
		var tabs = "";
		for (var i = 0; i < indent; i++) {
			tabs += this.tabStr;
		}
		var funcStrArray = obj.toString().split("\n");
		var str = "";
		for (var i = 0; i < funcStrArray.length; i++) {
			str += ((i == 0) ? "" : tabs) + funcStrArray[i] + "\n";
		}
		return str;
	},
	getRow: function(indent, data, isPropertyContent) {
		var tabs = "";
		for (var i = 0; i < indent && !isPropertyContent; i++) {
			tabs += this.tabStr;
		}
		if (data != null && data.length > 0 && data.charAt(data.length - 1) != "\n") {
			data = data + "\n";
		}
		return tabs + data;
	}

}

