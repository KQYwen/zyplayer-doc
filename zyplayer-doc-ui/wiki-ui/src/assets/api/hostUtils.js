let baseApi = import.meta.env.VITE_APP_BASE_API;

export function fixRequestUrl(url) {
	return fixTwoUrl(baseApi, url);
}

export function fixTwoUrl(url1, url2) {
	if (!url1 || !url2) {
		if (!url1 && !url2) {
			return '';
		}
		return url1 || url2;
	}
	if (url1.endsWith('/') && url2.startsWith('/')) {
		return url1 + url2.substring(1);
	}
	if (!url1.endsWith('/') && !url2.startsWith('/')) {
		return url1 + '/' + url2;
	}
	return url1 + url2;
}
