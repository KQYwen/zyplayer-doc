
// 官方配置文档：https://webpack.js.org/configuration/dev-server/
module.exports = {
	devServer: {
		hot: true,
		disableHostCheck: true,
		port: 80,
		host: 'local.zyplayer.com'
	},
	publicPath: './',
	productionSourceMap: false,
};
