<!--
-- zyplayer-doc 自研编辑器，集各大厂商优秀的设计（拷它的css！拷它的图标！借鉴它的内容处理），造一个现代化、好用的轮子。
-- 带着情怀开写，路途曲折，希望能完美呈现。
-- 参考：飞书文档、语雀文档、wangEditor
-- @author 暮光：城中城
-- @since 2020-06-06
-->
<template>
	<div class="mg-editor-box">
		<div ref="mgEditor" class="mg-editor" @click.stop="editorClick($event)">
			<template v-for="(item, index) in editorDom">
				<div v-if="item.type=='text'" :class="item.cls" :index="index" @click.stop="domClick(item, $event)">
					<template v-if="item.dom && item.dom.length > 0">
						<span v-for="(span, subIndex) in item.dom" :class="span.cls" :index="index" :subindex="subIndex">{{span.text}}</span>
					</template>
					<template v-else-if="!!item.text">{{item.text}}</template>
					<br v-else/>
				</div>
				<div v-else-if="item.type=='locate'" :class="item.cls" @click.stop="domClick(item, $event)">
					<br/>
				</div>
			</template>
		</div>
		<div class="mg-editor-toolbar" :style="editorToolbarStyle">
			<span class="iconfont icon-h1" @click="handleToolbarHn('h1')"></span>
			<span class="iconfont icon-h2" @click="handleToolbarHn('h2')"></span>
			<span class="iconfont icon-h3" @click="handleToolbarHn('h3')"></span>
			<span class="iconfont icon-hn"></span>
			<span class="iconfont icon-bold" @click="handleToolbarBold()"></span>
			<span class="iconfont icon-delete" @click="handleToolbarStrikeThrough()"></span>
			<span class="iconfont icon-backcolor"></span>
			<span class="iconfont icon-orderedlist"></span>
			<span class="iconfont icon-unorderedlist"></span>
			<span class="iconfont icon-checkbox"></span>
			<span class="iconfont icon-link"></span>
			<span class="iconfont icon-more"></span>
		</div>
		<textarea ref="userInput" @input="userInputDataChange()" v-model="userInputData" class="user-input" :style="userInputStyle"></textarea>
		<div class="mg-editor-cursor" :style="editorCursorStyle"></div>
	</div>
</template>

<script>
	import "./css/MgEditor.css";
	import "./css/MgEditorIconfont.css";
	import Dom from './util/dom';
	import toolbarCommon from './toolbar/common';

	const $ = require("jquery");

	export default {
		name: "mg-editor",
		data() {
			return {
				editor: {},
				editorRange: {
					startOffset: 0,
					endOffset: 0,
				},
				userInput: {},
				userInputStyle: {
					top: 0, left: 0, display: 'none'
				},
				editorCursorStyle: {
					top: 0, left: 0, display: 'none'
				},
				editorToolbarStyle: {
					top: 0, left: 0, display: 'none'
				},
				editorDom: [],
				editDom: {},
				userInputData: '',
			};
		},
		mounted: function () {
			this.editor = this.$refs.mgEditor;
			this.userInput = this.$refs.userInput;
			this.editorDom.push(new Dom('text', 'head head-h1'));
			// 监听输入框的特殊按键
			this.userInput.addEventListener('keydown', e => {
				if (e.which == 13) {
					e.preventDefault();
					this.editDom.keyEnter(this.editorDom, this.editorRange);
				}
			});
			// 鼠标选中事件
			this.editor.addEventListener('mouseup', e => {
				// 不延时还能获取到选中的文字（选择文字，单击选中文字的中间）
				let selectionRange = window.getSelection().getRangeAt(0);
				let selectText = selectionRange.toString();
				if (!!selectText) {
					let startNode = toolbarCommon.getRootDom(selectionRange.startContainer);
					let endNode = toolbarCommon.getRootDom(selectionRange.endContainer);
					let startIndex = parseInt(startNode.getAttribute("index"));
					let endIndex = parseInt(endNode.getAttribute("index"));
					let isOneDom = startIndex == endIndex;
					if (startIndex >= 0) {
						let startOffset = selectionRange.startOffset;
						let previousSibling = toolbarCommon.getRealElem(selectionRange.startContainer).previousSibling;
						for (; previousSibling;) {
							startOffset += previousSibling.innerText.length;
							previousSibling = previousSibling.previousSibling;
						}
						let domTemp = this.editorDom[startIndex];
						let endOffset = isOneDom ? selectionRange.endOffset : domTemp.text.length;
						let endPreviousSibling = toolbarCommon.getRealElem(selectionRange.endContainer).previousSibling;
						for (; endPreviousSibling;) {
							endOffset += endPreviousSibling.innerText.length;
							endPreviousSibling = endPreviousSibling.previousSibling;
						}
						domTemp.setOffset(startOffset, endOffset);
					}
					if (!isOneDom) {
						for (let i = startIndex + 1; i < endIndex; i++) {
							this.editorDom[i].setOffsetAll();
						}
						let endOffset = selectionRange.endOffset;
						let previousSibling = toolbarCommon.getRealElem(selectionRange.endContainer).previousSibling;
						for (; previousSibling;) {
							endOffset += previousSibling.innerText.length;
							previousSibling = previousSibling.previousSibling;
						}
						if (endIndex >= 0) {
							let domTemp = this.editorDom[endIndex];
							domTemp.setOffset(0, endOffset);
						}
					}
					this.editorToolbarStyle.display = 'block';
				}
				// console.log("mouseup", selectText, e);
			});
		},
		methods: {
			editorClick(event) {
				let lastDom = this.editorDom[this.editorDom.length - 1];
				if (lastDom.type != 'locate') {
					lastDom = new Dom('locate', 'locate');
					this.editorDom.push(lastDom);
				}
				setTimeout(() => event.target.lastChild.click(), 100);
			},
			domClick(dom, event) {
				this.editDom = dom;
				this.editDom.target = event.target;
				// 设置接收用户输入的输入框绝对位置
				this.userInputStyle.top = event.pageY + 'px';
				this.userInputStyle.left = event.pageX + 'px';
				this.userInputStyle.display = 'block';
				// 设置光标绝对位置
				let computedStyle = window.getComputedStyle(event.target);
				let fontSize = parseInt(computedStyle.fontSize);
				let offsetTop = event.target.offsetTop + (fontSize / 2 / 2);
				let offsetLeft = event.target.offsetLeft;
				this.editorCursorStyle.top = offsetTop + 'px';
				this.editorCursorStyle.left = offsetLeft + 'px';
				this.editorCursorStyle.height = computedStyle.fontSize;
				this.editorCursorStyle.display = 'block';
				// 设置光标所在对象的位置
				let selectionRange = window.getSelection().getRangeAt(0);
				let startNode = toolbarCommon.getRootDom(selectionRange.startContainer);
				let endNode = toolbarCommon.getRootDom(selectionRange.endContainer);
				let startIndex = startNode.getAttribute("index");
				let endIndex = endNode.getAttribute("index");
				if (startIndex != endIndex) {
					return;
				}
				let startOffset = selectionRange.startOffset;
				let previousSibling = toolbarCommon.getRealElem(selectionRange.startContainer).previousSibling;
				for (; previousSibling; previousSibling = previousSibling.previousSibling) {
					startOffset += previousSibling.innerText.length;
				}
				let endOffset = selectionRange.endOffset;
				let endPreviousSibling = toolbarCommon.getRealElem(selectionRange.endContainer).previousSibling;
				for (; endPreviousSibling; endPreviousSibling = endPreviousSibling.previousSibling) {
					endOffset += endPreviousSibling.innerText.length;
				}
				this.editorRange.startOffset = startOffset;
				this.editorRange.endOffset = endOffset;
				console.log(startOffset, endOffset);
				if (startOffset == endOffset) {
					setTimeout(() => this.userInput.focus(), 50);
				}
			},
			userInputDataChange() {
				// 如果在最后一个div里面输入，则改为非最后一个，然后在最后再加一行
				if (this.editDom.type == 'locate') {
					this.editDom.type = 'text';
					this.editDom.removeClass('locate');
					this.editorDom.push(new Dom('locate', 'locate'));
				}
				let oldText = this.editDom.text || '';
				// 如果文字的中间位置点击，则把内容放到指定位置
				let startOffset = this.editorRange.startOffset;
				this.editDom.addText(startOffset, this.userInputData);
				if (startOffset < oldText.length) {
					this.editorRange.startOffset = this.editorRange.endOffset = (startOffset + this.userInputData.length);
				} else {
					// 否则放到最后
					this.editorRange.startOffset = this.editorRange.endOffset = this.editDom.text.length;
				}
				// let newLength = this.userInputData.replace(/[\u0391-\uFFE5]/g, "aa").length / 2;
				// let letterSpacing = this.userInputData.length * 0.52;
				// this.editorCursorStyle.left = (parseInt(this.editorCursorStyle.left) + (parseInt(fontSize) * newLength) + letterSpacing) + 'px';
				this.userInputData = '';
			},
			handleToolbarBold() {
				for (let i = 0; i < this.editorDom.length; i++) {
					this.editorDom[i].addSelectionTextStyle('bold', 1);
				}
				this.editorToolbarStyle.display = 'none';
				window.getSelection().removeAllRanges();
			},
			handleToolbarStrikeThrough() {
				for (let i = 0; i < this.editorDom.length; i++) {
					this.editorDom[i].addSelectionTextStyle('strikethrough', 1);
				}
				this.editorToolbarStyle.display = 'none';
				window.getSelection().removeAllRanges();
			},
			handleToolbarHn(hn) {
				debugger
				this.editorToolbarStyle.display = 'none';
				window.getSelection().removeAllRanges();
			},
		}
	}
</script>

