const { resolve } = require('path')
import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import fs from 'fs'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
    server: {
        host: 'local.zyplayer.com',
        port: 80,
        // https: true
    },
    // 使用绝对根路径，确保通过控制器映射到 /doc-wiki 时，资源仍然从根路径 /assets 加载
    base: '/',
    publicDir: 'public',
    plugins: [
        vue(),
        // 将 jQuery 复制到 public 目录，确保以 <script src="/jquery.min.js"></script> 方式在构建产物和开发环境中可用
        {
            name: 'copy-jquery-to-public',
            apply: 'serve',
            configResolved() {
                try {
                    const src = path.resolve(__dirname, 'node_modules/jquery/dist/jquery.min.js');
                    const destDir = path.resolve(__dirname, 'public/wiki-assets');
                    if (!fs.existsSync(destDir)) fs.mkdirSync(destDir, { recursive: true });
                    const dest = path.join(destDir, 'jquery.min.js');
                    const content = fs.readFileSync(src);
                    fs.writeFileSync(dest, content);
                    // 复制 jquery-mousewheel 插件
                    const mwSrc = path.resolve(__dirname, 'node_modules/jquery-mousewheel/jquery.mousewheel.min.js');
                    const mwDest = path.join(destDir, 'jquery.mousewheel.min.js');
                    const mwContent = fs.readFileSync(mwSrc);
                    fs.writeFileSync(mwDest, mwContent);
                } catch (e) {
                    console.warn('[vite] copy-jquery-to-public failed:', e && e.message);
                }
            }
        },
        {
            name: 'copy-jquery-to-public-build',
            apply: 'build',
            buildStart() {
                try {
                    const src = path.resolve(__dirname, 'node_modules/jquery/dist/jquery.min.js');
                    const destDir = path.resolve(__dirname, 'public/wiki-assets');
                    if (!fs.existsSync(destDir)) fs.mkdirSync(destDir, { recursive: true });
                    const dest = path.join(destDir, 'jquery.min.js');
                    const content = fs.readFileSync(src);
                    fs.writeFileSync(dest, content);
                    // 复制 jquery-mousewheel 插件
                    const mwSrc = path.resolve(__dirname, 'node_modules/jquery-mousewheel/jquery.mousewheel.min.js');
                    const mwDest = path.join(destDir, 'jquery.mousewheel.min.js');
                    const mwContent = fs.readFileSync(mwSrc);
                    fs.writeFileSync(mwDest, mwContent);
                } catch (e) {
                    console.warn('[vite] copy-jquery-to-public-build failed:', e && e.message);
                }
            }
        },
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
        // 关键：不清空 dist，避免覆盖 console-ui 的构建产物（doc-console.html 等）
        emptyOutDir: false,
        cssCodeSplit: false,
        // 构建产物输出到 manage 模块，便于 DocSystemController("/doc-wiki") 直接返回 doc-wiki.html
        outDir: '../../zyplayer-doc-manage/src/main/resources/dist',
        rollupOptions: {
            input: {
                main: resolve(__dirname, 'doc-wiki.html'),
            },
            output: {
                // 统一将 wiki 的所有静态资源输出到 wiki-assets，避免与控制台 /assets 冲突
                entryFileNames: 'wiki-assets/[name]-[hash].js',
                chunkFileNames: 'wiki-assets/[name]-[hash].js',
                assetFileNames: 'wiki-assets/[name].[hash].[ext]',
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
            }
        }
    },
});
