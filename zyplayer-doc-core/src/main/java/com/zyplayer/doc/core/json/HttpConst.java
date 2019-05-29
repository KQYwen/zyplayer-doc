package com.zyplayer.doc.core.json;

public class HttpConst {
	
	/** 每页显示条数 **/
	public static final int PAGE_NUMBER = 50;
	/** 默认当前页 **/
	public static final int CURRENT_PAGE = 1;
	
	/** 图片验证码 **/
	public static final String SESSION_VERIFY_CODE = "SESSION_VERIFY_CODE";
	/** 邮箱验证码 **/
	public static final String SESSION_EMAIL_CODE = "SESSION_EMAIL_CODE";
	/** 请求失败的原因 **/
	public static final String SESSION_FAIL_REASON = "SESSION_FAIL_REASON";
	/** operator */
	public static final String OPERATOR = "OPERATOR";
	
	/** 分页-总条数 */
	public static final String PAGE_TOTAL = "PAGE_TOTAL";
	/** 分页-当前页数 */
	public static final String PAGE_NOWPAGE = "PAGE_NOWPAGE";
	/** 分页-总页数 */
	public static final String PAGE_PAGECOUNT = "PAGE_PAGECOUNT";
	/** 分页-每页多少条 */
	public static final String PAGE_SIZE = "PAGE_SIZE";
	
	/** 会话连接 */
	public static final String ACCESS_TOKEN = "accessToken";
	/** 存在于ThreadLocal的http request */
	public static final String HTTP_SERVLET_REQUEST = "HTTP_SERVLET_REQUEST";
	/** 存在于ThreadLocal的HTTP_SESSION */
	public static final String HTTP_SESSION = "HTTP_SESSION";
	
	/** 存于operator中权限的缓存头 **/
	public static final String AUTH_CACHE_HEAD = "AUTH_CACHE_HEAD_";
	/** 存于operator中的用户信息 **/
	public static final String CACHE_OPERATOR_USER_INFO = "USER_INFO";
	/** 存于operator中的城市信息 **/
	public static final String CACHE_OPERATOR_CITY_ID = "CITY_ID";
	/** 存于operator中的token绑定的访问信息，使得换一台电脑不能使用此token **/
	public static final String CACHE_OPERATOR_ACCESS_TOKEN_VALIDATE = "ACCESS_TOKEN_VALIDATE";
	/** 存于operator中的token信息 **/
	public static final String CACHE_OPERATOR_ACCESS_TOKEN = "accessToken";
	/** 存于operator中的微信sessionKey信息 **/
	public static final String CACHE_OPERATOR_SESSION_KEY = "sessionKey";
	
	// 新版本使用的错误码
	/** 成功 **/
	public static final int SUCCESS = 200;
	/** 提示性状态 需要客户端配合展示 **/
	public static final int CONFIRM_CODE = 300;
	/** accessToken非法或过期，需要重新登录 **/
	public static final int TOKEN_TIMEOUT = 400;
	/** 业务接口缺少参数，errMsg会返回错误信息 **/
	public static final int MISSING_PARAMETER = 401;
	/** API 未授权 **/
	public static final int UNAUTHORIZED = 402;
	/** 接口调用频率超限 **/
	public static final int CALL_FREQUENCY_GAUGE = 403;
	/** 微信未扫码登录异常 **/
	public static final int WX_NOT_LOGIN = 404;
	/** 请升级至新版使用此功能 **/
	public static final int NEED_UPGRADE = 405;
	/** 服务器端未知错误 **/
	public static final int OTHER_FAIL = 500;
}
