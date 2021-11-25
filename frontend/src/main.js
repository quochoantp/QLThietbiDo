import Vue from 'vue'
import App from './App.vue'
import  Vuex from 'vuex'
import '@fortawesome/fontawesome-free/css/all.css'
import '@fortawesome/fontawesome-free/js/all.js'

import Button from './components/base/Button'
import Table from './components/base/Table'
import Pagination from './components/base/Pagination'
import Combobox from './components/base/Combobox'
import Input from "./components/base/Input.vue";

import storeConfigs from "./store/index"
Vue.use(Vuex)

const store = new Vuex.Store(storeConfigs)
Vue.component("Button", Button)
Vue.component("Table", Table)
Vue.component("Pagination", Pagination)
Vue.component("Combobox", Combobox)
Vue.component("Input", Input)
Vue.config.productionTip = false

new Vue({
  render: h => h(App),
   store
}).$mount('#app')
