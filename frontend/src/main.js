import Vue from 'vue'
import App from './App.vue'
import  Vuex from 'vuex'
import '@fortawesome/fontawesome-free/css/all.css'
import '@fortawesome/fontawesome-free/js/all.js'

import Button from './components/base/Button'
import Table from './components/base/Table'
import Combobox from './components/base/Combobox'
import Input from "./components/base/Input.vue";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import storeConfigs from "./store/index"
Vue.use(Vuex)
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
const store = new Vuex.Store(storeConfigs)
Vue.component("Button", Button)
Vue.component("Table", Table)
Vue.component("Combobox", Combobox)
Vue.component("Input", Input)
Vue.config.productionTip = false

new Vue({
  render: h => h(App),
   store
}).$mount('#app')
