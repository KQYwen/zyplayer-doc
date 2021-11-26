const { resolve } = require('path')
import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import styleImport from 'vite-plugin-style-import'

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
    build: {
        emptyOutDir: true,
        cssCodeSplit: false,
        outDir: '../../zyplayer-doc-api/src/main/resources/dist',
        rollupOptions: {
            input: {
                main: resolve(__dirname, 'doc-api.html'),
            }
        }
    }
});
