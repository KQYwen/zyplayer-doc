import ace from 'brace';
import 'brace/ext/language_tools';
import 'brace/mode/sql';
import 'brace/snippets/sql';
import 'brace/mode/json';
import 'brace/snippets/json';
import 'brace/theme/monokai';
import complater from './DatabaseCompleter'

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
			})
		},
		width: function () {
			this.$nextTick(function () {
				this.editor.resize()
			})
		},
		source: function (source) {
			complater.change(source);
		},
	},
	beforeDestroy: function () {
		this.editor.destroy();
		this.editor.container.remove();
	},
	activated: function () {
		complater.change(this.source);
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
			// 内容改变就执行输入提示功能，todo 某些情况下应当不提示
			// console.log('change content：' + content);
			// editor.execCommand("startAutocomplete");
		});
		if (vm.options) {
			editor.setOptions(vm.options);
		}
		complater.change(this.source);
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
