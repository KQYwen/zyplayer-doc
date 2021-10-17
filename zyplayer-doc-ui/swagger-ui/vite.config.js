const { resolve } = require('path')
import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import styleImport from 'vite-plugin-style-import'

// https://vitejs.dev/config/
export default defineConfig({
    server: {
        host: 'local.zyplayer.com',
        port: 80,
    },
    base: '',
    plugins: [
        vue(),
        styleImport({
            libs: [
                // 使用element-plus的一些组件
                {
                    libraryName: 'element-plus',
                    esModule: true,
                    ensureStyleFile: true,
                    resolveStyle: (name) => {
                        return `element-plus/lib/theme-chalk/${name}.css`;
                    },
                    resolveComponent: (name) => {
                        return `element-plus/lib/${name}`;
                    },
                }
            ]
        })
    ],
    build: {
        emptyOutDir: true,
        cssCodeSplit: false,
        outDir: '../../zyplayer-doc-swagger-plus/src/main/resources/dist',
        rollupOptions: {
            input: {
                main: resolve(__dirname, 'doc-swagger-plus.html'),
            }
        }
    }
});
