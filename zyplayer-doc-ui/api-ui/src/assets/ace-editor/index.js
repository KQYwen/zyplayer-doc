import ace from 'brace';
import 'brace/ext/language_tools';
import 'brace/mode/sql';
import 'brace/snippets/sql';
import 'brace/mode/json';
import 'brace/snippets/json';
import 'brace/mode/xml';
import 'brace/snippets/xml';
import 'brace/mode/html';
import 'brace/snippets/html';
import 'brace/mode/javascript';
import 'brace/snippets/javascript';
import 'brace/mode/text';
import 'brace/snippets/text';
import 'brace/theme/monokai';
import 'brace/theme/chrome';
import './index.css';
import { h, reactive } from 'vue'

export default {
	render() {
		let height = this.height ? this.px(this.height) : '100%';
		let width = this.width ? this.px(this.width) : '100%';
		return h('div', {
			attrs: {
				style: "height: " + height + '; width: ' + width,
			}
		});
	},
	props: {
		value: String,
		lang: String,
		theme: String,
		height: String,
		width: String,
		options: Object
	},
	data() {
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
			})
		},
		width: function () {
			this.$nextTick(function () {
				this.editor.resize()
			})
		},
	},
	beforeDestroy() {
		this.editor.destroy();
		this.editor.container.remove();
	},
	mounted() {
		let vm = this;
		let lang = this.lang || 'text';
		let theme = this.theme || 'chrome';
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
			vm.$emit('update:value', content);
			vm.contentBackup = content;
			// 内容改变就执行输入提示功能，和自动的冲突了，感觉自动的就符合了，但是按空格他不出现提示框
			// console.log('change content：' + content);
			// editor.execCommand("startAutocomplete");
		});
		if (vm.options) {
			editor.setOptions(vm.options);
		}
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
