<template>
  <div>
    <QLDHome v-if="screen === 'homescreen'" @open-add-screen="openAddScreen"  @open-update-screen="openUpdateScreen"/>
    <QLDAdd v-if="screen === 'addTBDscreen'" @cancel-form="openHomeScreen" @open-add-param-screen="openAddParamScreen"/>
    <QLDUpdate v-if="screen === 'updateTBDscreen'" @cancel-form="openHomeScreen" :item="ins"/>
    <QLDAddParam v-if="screen === 'addParamScreen'" />
  </div>
  
</template>
<script> 
import QLDHome from '../view/QLDHome.vue'
import QLDAdd from '../view/QLDAdd.vue'
import QLDUpdate from '../view/QLDUpdate.vue'
import QLDAddParam from '../view/QLDAddParam.vue'
export default {
  components: {
    QLDHome,
    QLDAdd,
    QLDUpdate,
    QLDAddParam
  },
  data() {
    return {
      screen: "homescreen",
      ins: {}
    }
  },
  methods: {
    openHomeScreen(){
      this.screen = "homescreen";
      this.$emit("set-screen", this.screen)
    },
    openAddScreen(){
      this.screen = "addTBDscreen";
      this.$emit("set-screen", this.screen)
    },
    openUpdateScreen(item){
        this.screen = "updateTBDscreen";
        this.$emit("set-screen",this.screen)
        this.ins = item
    },
    openAddParamScreen(){
      this.screen = 'addParamScreen'
    }
  },
  
}
</script>