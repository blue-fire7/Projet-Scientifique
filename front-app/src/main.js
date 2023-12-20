import { createApp } from 'vue';
import './style.css';
import App from './App.vue';
import { WebSocket } from 'ws';

import { createPinia } from 'pinia';

import './style/theme.css';

const pinia = createPinia();

createApp(App).use(pinia).mount('#app');

Object.assign(global, { WebSocket });
