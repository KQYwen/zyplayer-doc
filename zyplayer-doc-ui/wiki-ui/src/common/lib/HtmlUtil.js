
export default {
	/**
	 * 生成目录树
	 * ========================================================
	 * 说明：代码修改至 yaohaixiao 的 autoc.js
	 * 项目 gitee 地址：https://gitee.com/yaohaixiao/AutocJS
	 * ========================================================
	 */
	createNavigationHeading() {
		let headArr = [];
		let headNodeArr = document.querySelector('.wiki-page-content').querySelectorAll('h1,h2,h3,h4,h5,h6');
		if (headNodeArr.length <= 0) {
			return [];
		}
		headNodeArr.forEach(node => {
			headArr.push({
				node: node,
				level: parseInt(node.tagName.replace(/[h]/i, ''), 10)
			});
		});
		let chapters = []
		let previous = 1
		let level = 0
		headArr.forEach((heading, i) => {
			let current = heading.level
			let pid = -1
			// 当前标题的（标题标签）序号 > 前一个标题的序号：两个相连的标题是父标题 -> 子标题关系；
			// h2 （前一个标题）
			// h3 （当前标题）
			if (current > previous) {
				level += 1
				// 第一层级的 pid 是 -1
				if (level === 1) {
					pid = -1
				} else {
					pid = i - 1
				}
			}
				// 当前标题的（标题标签）序号 = 前一个标题的序号
				// h2 （前一个标题）
				// h2 （当前标题）
				// 当前标题的（标题标签）序号 < 前一个标题的序号，并且当前标题序号 > 当前的级别
				// h2
				// h4 （前一个标题）
			// h3 （当前标题：这种情况我们还是任务 h3 是 h2 的下一级章节）
			else if (current === previous || (current < previous && current > level)) {
				// H1 的层级肯定是 1
				if (current === 1) {
					level = 1
					pid = -1
				} else {
					pid = chapters[i - 1].pid
				}
			} else if (current <= level) {
				// H1 的层级肯定是 1
				if (current === 1) {
					level = 1
				} else {
					level = level - (previous - current)

					if (level <= 1) {
						level = 1
					}
				}
				// 第一级的标题
				if (level === 1) {
					pid = -1
				} else {
					// 虽然看上去差点，不过能工作啊
					pid = this.generatePid(chapters, previous - current, i)
				}
			}
			previous = current
			chapters.push({
				id: i,
				pid: pid,
				level: level,
				node: heading.node,
				text: this.stripTags(this.trim(heading.node.innerHTML))
			});
		})
		console.log(chapters);
		return chapters;
	},
	trim: (str) => {
		return str.replace(/^\s+/g, '').replace(/\s+$/g, '')
	},
	stripTags: (str) => {
		return str.replace(/<\/?[^>]+(>|$)/g, '')
	},
	generatePid(chapters, differ, index) {
		let pid
		// 最大只有 5 级的差距
		switch (differ) {
			case 2:
				pid = chapters[chapters[chapters[index - 1].pid].pid].pid
				break
			case 3:
				pid = chapters[chapters[chapters[chapters[index - 1].pid].pid].pid].pid
				break
			case 4:
				pid = chapters[chapters[chapters[chapters[chapters[index - 1].pid].pid].pid].pid].pid
				break
			case 5:
				pid = chapters[chapters[chapters[chapters[chapters[chapters[index - 1].pid].pid].pid].pid].pid].pid
				break
			default:
				pid = chapters[chapters[index - 1].pid].pid
				break
		}
		return pid
	},
}

