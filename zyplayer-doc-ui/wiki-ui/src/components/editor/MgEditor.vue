<!--
-- zyplayer-doc 自研编辑器，集各大厂商优秀的设计（拷它的css！拷它的图标！借鉴它的内容处理），造一个现代化、好用的轮子。
-- 带着情怀开写，路途曲折，希望能完美呈现。
-- 参考：飞书文档、语雀文档、wangEditor
-- @author 暮光：城中城
-- @since 2020-06-06
-->
<template>
	<div class="mg-editor-box">
		<div ref="mgEditor" class="mg-editor" contenteditable="true"></div>
		<div class="mg-editor-toolbar" :style="getMgEditorToolbarStyle(selectionInfo)">
			<span class="iconfont icon-h1" @click="handleToolbarHn('h1')"></span>
			<span class="iconfont icon-h2" @click="handleToolbarHn('h2')"></span>
			<span class="iconfont icon-h3" @click="handleToolbarHn('h3')"></span>
			<span class="iconfont icon-hn"></span>
			<span class="iconfont icon-border" @click="handleToolbarBorder()"></span>
			<span class="iconfont icon-delete" @click="handleToolbarStrikeThrough()"></span>
			<span class="iconfont icon-backcolor"></span>
			<span class="iconfont icon-orderedlist"></span>
			<span class="iconfont icon-unorderedlist"></span>
			<span class="iconfont icon-checkbox"></span>
			<span class="iconfont icon-link"></span>
			<span class="iconfont icon-more"></span>
		</div>
	</div>
</template>

<script>
	import "./css/MgEditor.css";
	import "./css/MgEditorIconfont.css";
	import utilPast from './util/past';
	import utilBase from './util/util';
	import utilSelection from './util/selection';
	import toolbarHn from "./toolbar/hn";
	import toolbarCommon from './toolbar/common';
	import toolbarCodeList from "./toolbar/inlineCodeList";
	import toolbarBorder from "./toolbar/border";
	import toolbarStrikeThrough from "./toolbar/strikeThrough";

	const $ = require("jquery");

	export default {
		name: "mg-editor",
		data() {
			return {
				editor: {},
				selectionInfo: {
					pageX: 0,
					pageY: 0,
					haveSelect: false,
				},
				compositionEnd: true,
			};
		},
		mounted: function () {
			this.editor = this.$refs.mgEditor;
			this.appendEditorLastLine();
			this.editor.addEventListener('mouseup', e => {
				// 不延时还能获取到选中的文字（选择文字，单击选中文字的中间）
				setTimeout(() => {
					let selectText = window.getSelection().getRangeAt(0).toString();
					this.selectionInfo = {
						pageX: e.pageX,
						pageY: e.pageY,
						haveSelect: !!selectText,
					};
					console.log("mouseup", selectText, e);
				}, 100);
			});
			document.body.addEventListener('click', e => {
				this.selectionInfo.haveSelect = false;
			});
			// 粘贴事件
			this.editor.addEventListener('paste', e => {
				e.preventDefault();
				let pasteText = utilPast.getPasteText(e);
				let pasteHtml = utilPast.getPasteHtml(e, true, true);
				document.execCommand('insertHTML', false, pasteText);
				setTimeout(() => this.handleUserInput(e), 300);
			});
			this.editor.addEventListener('compositionstart', () => {
				this.compositionEnd = false;
			});
			this.editor.addEventListener('compositionend', () => {
				this.compositionEnd = true;
			});
			this.editor.addEventListener('click', e => {
				this.handleUserInput(e);
			});
			this.editor.addEventListener('keyup', e => {
				this.handleUserInput(e);
			});
			this.editor.addEventListener('keydown', e => {
				if (e.which == 13) {
					let nowContainerOrigin = window.getSelection().getRangeAt(0).commonAncestorContainer;
					let nowContainer = this.findListCodeNode(nowContainerOrigin);
					// 如果是回车，而且是code行，修改行号
					if (nowContainer != null) {
						e.preventDefault();
						let newDomText = '';
						let innerText = nowContainer.innerText;
						if (!!innerText) {
							let startOffset = window.getSelection().getRangeAt(0).startOffset;
							nowContainer.innerText = innerText.substring(0, startOffset);
							newDomText = innerText.substring(startOffset, innerText.length);
						}
						let nowNum = parseInt(nowContainer.getAttribute("start")) + 1;
						let codeEle = $(`<code start='${nowNum}'>${newDomText}</code>`)[0];
						$(nowContainer).after(codeEle);
						utilBase.moveSelectionRange(codeEle, 0, 0);
						// 改后面的序号
						$(codeEle).nextAll().each(function () {
							$(this).attr("start", ++nowNum);
						});
					}
					let handleClassDom = toolbarCommon.getSelectionContainer(nowContainerOrigin);
					// 回车时上一行是最后一行，取消标记，防止复制了
					if (this.domHaveClass(handleClassDom, "locate")) {
						handleClassDom.classList.remove("locate");
						setTimeout(() => this.editor.lastChild.classList.add("locate"), 100);
					}
					// 在特定元素里面按回车，如果任由系统则会复制当前行的class等内容，所以自己处理下，插入一行空白内容
					if (toolbarCommon.domHaveClass(handleClassDom, 'head')) {
						let domInnerText = handleClassDom.innerText;
						let selectRange = window.getSelection().getRangeAt(0);
						// 光标在行的最后才执行此操作，否则用系统默认的
						if (utilSelection.isSelectionEmpty() && selectRange.endOffset == domInnerText.length) {
							e.preventDefault();
							let newEle = $(`<div><br/></div>`)[0];
							$(handleClassDom).after(newEle);
							utilBase.moveSelectionRange(newEle, 0, 0);
							this.selectionInfo.haveSelect = false;
						}
					}
				}
			});
		},
		methods: {
			handleUserInput(e) {
				if (!this.compositionEnd) return;
				// 判断输入内容，如果是markdown语法则处理成目标样式
				let nowContainer = window.getSelection().getRangeAt(0).commonAncestorContainer;
				let lineText = nowContainer.data;
				if (lineText == '```') {
					toolbarCodeList.handleCodeList();
				}
				// 判断是否是在最后一行输入，如果是就再在最后加一行
				let locate = this.findParentClassDom(nowContainer, 'locate');
				if (locate != null && !!locate.innerText && !!locate.innerText.trim()) {
					locate.classList.remove("locate");
					this.appendEditorLastLine();
				} else if (!this.domHaveClass(this.editor.lastChild, "locate")) {
					this.editor.lastChild.classList.add("locate");
				}
			},
			findListCodeNode(container) {
				// 如果自己是root，创建一个div放最后
				if (this.isRootBox(container)) {
					return null;
				}
				let parentNode = container.parentNode;
				if (this.domHaveClass(parentNode, "list-code")) {
					return container;
				}
				return this.findListCodeNode(parentNode);
			},
			findParentClassDom(container, cls) {
				if (!container || this.isRootBox(container)) return null;
				if (this.domHaveClass(container, cls)) return container;
				return this.findParentClassDom(container.parentNode, cls);
			},
			appendEditorLastLine() {
				// 最后再加一行，防止光标没法到最后了
				$(this.editor).append("<div class='locate'><br/></div>");
			},
			isRootBox(container) {
				return this.domHaveClass(container, "mg-editor");
			},
			domHaveClass(container, cls) {
				return container && container.classList && container.classList.contains(cls);
			},
			handleToolbarBorder() {
				toolbarBorder.handleBorder();
			},
			handleToolbarStrikeThrough() {
				toolbarStrikeThrough.handleStrikeThrough();
			},
			handleToolbarHn(hn) {
				toolbarHn.handleHn(hn);
			},
			getMgEditorToolbarStyle(selectionInfo) {
				let style = {};
				style.display = selectionInfo.haveSelect ? 'block' : 'none';
				if (selectionInfo.haveSelect) {
					let top = selectionInfo.pageY - 20 - 50;
					if (top < 0) top = selectionInfo.pageY + 20;
					let left = selectionInfo.pageX - 220;
					if (left < 10) left = 10;
					style.top = top + 'px';
					style.left = left + 'px';
				}
				return style;
			}
		}
	}
</script>

