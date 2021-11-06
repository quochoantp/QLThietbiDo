<template>
  <div id="app">
    <QLDHeader/>
    <QLDBody/>
  </div>
</template>

<script>
//import QLDAdd from './components/QLDAdd.vue'
import QLDBody from './components/QLDBody.vue'
import QLDHeader from './components/QLDHeader.vue'


export default {
  name: 'App',
  components: {
    QLDHeader,
    QLDBody
  }
}
</script>
  
<style>

* {
    box-sizing: border-box;
    padding: 0;
    margin: 0;
}
button {
  border: none;
  outline: none;
  cursor: pointer;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  
}
</style>
