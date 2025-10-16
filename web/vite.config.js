import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

import ElementPlus from 'unplugin-element-plus/vite'

// https://vite.dev/config/
export default defineConfig({
  // 配置scss，下载命令为npm install -D sass
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@use "@/styles/element/rootConfig" as *;`,
      },
    },
  },
  plugins: [
    vue(),
    vueDevTools(),
    [ElementPlus()],
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  envPrefix: 'APP_',
})
