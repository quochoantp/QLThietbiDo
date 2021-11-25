<template>
  <div id="app">
    <QLDHeader :screen="screen"/>
    <Body @set-screen="setScreen"/>
  </div>
</template>

<script>
//import QLDAdd from './components/QLDAdd.vue'
import Body from './components/layout/Body.vue'
import QLDHeader from './components/layout/QLDHeader.vue'


export default {
  name: 'App',
   components: {
    QLDHeader,
    Body
  },
  data(){
    return{
      screen: "homescreen"
    }
  },  
  methods: {
    setScreen(screen){
      this.screen = screen
    }
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
