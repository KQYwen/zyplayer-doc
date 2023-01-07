export default {
	computeFileSize(fileSize) {
		if (!fileSize) {
			return '-'
		}
		let size = ''
		if (fileSize < 0.1 * 1024) {
			size = fileSize.toFixed(2) + 'B'
		} else if (fileSize < 0.1 * 1024 * 1024) {
			size = (fileSize / 1024).toFixed(2) + 'KB'
		} else if (fileSize < 0.1 * 1024 * 1024 * 1024) {
			size = (fileSize / (1024 * 1024)).toFixed(2) + 'MB'
		} else {
			size = (fileSize / (1024 * 1024 * 1024)).toFixed(2) + 'GB'
		}
		let sizeStr = size + ''
		let index = sizeStr.indexOf('.')
		let dou = sizeStr.substr(index + 1, 2)
		if (dou == '00') {
			return sizeStr.substring(0, index) + sizeStr.substr(index + 3, 2)
		}
		return size
	},
}
