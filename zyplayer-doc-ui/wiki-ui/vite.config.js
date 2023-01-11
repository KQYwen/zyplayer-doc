const { resolve } = require('path')
import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
    server: {
        host: 'local.zyplayer.com',
        port: 80,
        // https: true
    },
    base: '',
    plugins: [
        vue(),
    ],
	resolve: {
		alias: {
			// 设置路径 这里resolve和join可自行选用
			'~': resolve(__dirname, './'),
			// 设置别名
			'@': resolve(__dirname, './src')
		},
		extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
	},
    build: {
        emptyOutDir: true,
        cssCodeSplit: false,
        outDir: '../../zyplayer-doc-wiki/src/main/resources/dist',
        rollupOptions: {
            input: {
                main: resolve(__dirname, 'doc-wiki.html'),
            },
	        output: {
		        // 拆分包
		        manualChunks: (id) => {
			        if (id.includes('node_modules')) {
				        const module = id.toString().split('node_modules/')[1].split('/')[0];
				        if (['mermaid', 'highlight.js', 'katex', 'zrender', 'vant'].includes(module)) {
							return module;
						}
				        if (module === '@vue') return 'vue';
				        if (module === '@wangeditor') return 'wangeditor';
				        if (module.indexOf('markmap') === 0) return 'markmap';
				        if (module.indexOf('markdown') === 0) return 'markdown';
						// 不可拆分：echarts、element-plus
				        if (!['echarts', 'element-plus'].includes(module)) {
					        return 'vendor';
				        }
			        }
		        },
		        assetFileNames: 'assets/[name].[hash].[ext]',
	        }
        }
    },
});
