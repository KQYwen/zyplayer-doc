
/**
 * 获取zyplayer后端域名
 */
export function getZyplayerApiBaseUrl() {
    let env = import.meta.env.VITE_APP_ENV;
    let baseUrl = import.meta.env.VITE_APP_BASE_URL_ONLINE;
    if ("dev" === env) {
        baseUrl = import.meta.env.VITE_APP_BASE_URL_DEV;
    }
    return baseUrl;
}

