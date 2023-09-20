export default {
	createNavigationHeading() {
		let headArr = []
		let headNode = document.querySelector('.wiki-page-content')
		if (null === headNode){
			return headArr
		}
		let headNodeArr = headNode.querySelectorAll('h1,h2,h3,h4,h5,h6')
		if (headNodeArr.length <= 0) {
			return []
		}
		headNodeArr.forEach((node) => {
			let text = node.innerHTML
				.replace(/^\s+/g, '')
				.replace(/\s+$/g, '')
				.replace(/<\/?[^>]+(>|$)/g, '')
			headArr.push({
				node: node,
				level: parseInt(node.tagName.replace(/[h]/i, ''), 10),
				text: text,
			})
		})
		return headArr
	},
}
