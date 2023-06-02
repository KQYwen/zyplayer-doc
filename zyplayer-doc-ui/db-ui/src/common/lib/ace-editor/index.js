import ace from 'brace';
import 'brace/ext/language_tools';
import 'brace/mode/sql';
import 'brace/snippets/sql';
import 'brace/mode/json';
import 'brace/snippets/json';
import 'brace/theme/monokai';
import completer from './DatabaseCompleter'
import './index.css';

export default {
	render: function (h) {
		let height = this.height ? this.px(this.height) : '100%';
		let width = this.width ? this.px(this.width) : '100%';
		return h('div', {
			attrs: {
				style: "height: " + height + '; width: ' + width,
			}
		})
	},
	props: {
		value: String,
		source: Object,
		lang: true,
		theme: String,
		height: true,
		width: true,
		options: Object
	},
	data: function () {
		return {
			editor: null,
			contentBackup: ""
		}
	},
	watch: {
		value: function (val) {
			if (this.contentBackup !== val) {
				this.editor.session.setValue(val, 1);
				this.contentBackup = val;
			}
		},
		theme: function (newTheme) {
			this.editor.setTheme('ace/theme/' + newTheme);
		},
		lang: function (newLang) {
			this.editor.getSession().setMode(typeof newLang === 'string' ? ('ace/mode/' + newLang) : newLang);
		},
		options: function (newOption) {
			this.editor.setOptions(newOption);
		},
		height: function () {
			this.$nextTick(function () {
				this.editor.resize()
				//设置字体大小
				this.editor.setFontSize(14);
				//更新编辑器高度
				document.getElementById("aceEditorId").style.height = this.height + "px";;
			})
		},
		width: function () {
			this.$nextTick(function () {
				this.editor.resize()
			})
		},
		source: function (source) {
			completer.change(source);
		},
	},
	beforeDestroy: function () {
		this.editor.destroy();
		this.editor.container.remove();
	},
	activated: function () {
		completer.change(this.source);
	},
	mounted: function () {
		let vm = this;
		let lang = this.lang || 'text';
		let theme = this.theme || 'chrome';
		require('brace/ext/emmet');
		let editor = vm.editor = ace.edit(this.$el);
		editor.$blockScrolling = Infinity;
		this.$emit('init', editor);
		//editor.setOption("enableEmmet", true);
		editor.getSession().setMode(typeof lang === 'string' ? ('ace/mode/' + lang) : lang);
		editor.setTheme('ace/theme/' + theme);
		if (this.value) {
			editor.setValue(this.value, 1);
		}
		this.contentBackup = this.value;
		editor.on('change', function () {
			let content = editor.getValue();
			vm.$emit('input', content);
			vm.contentBackup = content;
			// 内容改变就执行输入提示功能，和自动的冲突了，感觉自动的就符合了，但是按空格他不出现提示框
			// console.log('change content：' + content);
			// editor.execCommand("startAutocomplete");
		});
		editor.getSession().selection.on('changeSelection', function (e) {
			let sqlValue = editor.session.getTextRange(editor.getSelectionRange());
			vm.$emit('cursorSelection',sqlValue)
		})
		editor.commands.addCommand({
			name: "start-autocomplete",
			bindKey: {win: "Alt-Enter", mac: "Alt-Enter"},
			exec: function (editor) {
				completer.startAutocomplete(editor);
			}
		});
		if (vm.options) {
			editor.setOptions(vm.options);
		}
		completer.change(this.source);
	},
	methods: {
		px: function (n) {
			if (/^\d*$/.test(n)) {
				return n + "px";
			}
			return n;
		},
	},
}
;
