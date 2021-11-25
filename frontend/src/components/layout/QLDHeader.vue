<template>
    <div class="header">
     <span v-if= "screen === 'homescreen'">Quản lý thiết bị đo</span>
    <span v-else-if="screen === 'addTBDscreen'">Quản lý thiết bị đo >> Thêm mới thiết bị</span>
    <span v-if="screen === 'updateTBDscreen'">Quản lý thiết bị đo >> Cập nhật thiết bị</span>
    </div>

</template>
<script>

export default {
   props: {
       screen : String
   }
    
   
}
</script>
<style>
  .header {
  height: 80px;
  width: 100%;
  background-color: #CCFFFF;
  font-style: italic;
  border-bottom: 1px solid rgba(0,0,0,.125);
  display: flex;
  align-items: center;
  padding-left: 24px;
  font-size: 26px;
}

</style>
