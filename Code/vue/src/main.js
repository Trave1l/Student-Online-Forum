import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import '@/assets/css/global.css'
import DrawerLayout from 'vue-drawer-layout'


createApp(App).use(store).use(router).use(ElementPlus, { size:'small'}).use(DrawerLayout).mount('#app')
