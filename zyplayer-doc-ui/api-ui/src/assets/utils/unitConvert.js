export default {
    formatSeconds(value) {
        let result = parseInt(value);
        let second = result / 1000;
        let s = Math.floor(second % 60);
        let ms = (result % 1000);
        if (s > 0) return `${s}.${ms} s`;
        return `${ms} ms`;
    },
    formatFileSize(fileSize) {
        if (!fileSize) {
            return '0 B';
        }
        let size = "";
        if (fileSize < 0.1 * 1024) {
            size = fileSize.toFixed(2) + " B"
        } else if (fileSize < 0.1 * 1024 * 1024) {
            size = (fileSize / 1024).toFixed(2) + " KB"
        } else if (fileSize < 0.1 * 1024 * 1024 * 1024) {
            size = (fileSize / (1024 * 1024)).toFixed(2) + " MB"
        } else {
            size = (fileSize / (1024 * 1024 * 1024)).toFixed(2) + " GB"
        }
        let sizeStr = size + "";
        let index = sizeStr.indexOf(".");
        let dou = sizeStr.substr(index + 1, 2);
        if (dou == "00") {
            return sizeStr.substring(0, index) + sizeStr.substr(index + 3, 2)
        }
        return size;
    },
}
