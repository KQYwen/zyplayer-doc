
// 官方配置文档：https://webpack.js.org/configuration/dev-server/
module.exports = {
	devServer: {
		hot: true,
		disableHostCheck: true,
		port: 80,
		host: 'local.zyplayer.com'
	},
	publicPath: './',
	outputDir: '../../zyplayer-doc-db/src/main/resources/dist',
	productionSourceMap: false,
	pages: {
		index: {
			entry: 'src/main.js',
			template: 'public/index.html',
			filename: process.env.NODE_ENV === 'production'?'doc-db.html':'index.html',
		},
	},
	// 在项目配置的时候，默认 npm 包导出的是运行时构建，即 runtime 版本，不支持编译 template 模板。
	runtimeCompiler: true
};
