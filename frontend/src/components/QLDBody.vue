<template>
    <div class="info" >
     
         <!-- <button class="btn btn-primary" v-on:click="clickAdd">{{msg_Btn}}</button> -->
        <div class="header-info">
            <Button
                class="btn-add"
                text="Thêm"
                @click="clickAdd"
            />
            <div class="show">
                <label style="padding-right:5px">Show</label>
                <input type="number" id="inputNumber" value="10"/>
                <label>entries</label>
            </div>
        </div>
        
        <div class="tableInfo">
           
           <Table
            :data="homeparas"
            :fields="homeparaFields"
            :searchable="true"
            @onclick-update="updateIns"
           />
           
        </div>
        <div class="paging-bar">
            <div class="paging-left">Showing...</div>
            <div class="paging-right">
                <Pagination/>
            </div>
        </div>
        <QLDAdd v-if="isSelectButton" @cancel-form="closeAddForm" :state="stateForm"/>
    </div>
</template>
<script>
  import HomePara from "../services/HomePara"
  import QLDAdd from "../components/QLDAdd.vue"
import Button from './base/Button.vue'
import Table from './base/Table.vue'
import Pagination from './base/Pagination.vue'
  
export default{
   name: 'qld-body',
   components :{
    QLDAdd, Button,
      Table, Pagination
   },
  
    data(){
       return{
           msg_Btn:'+Thêm',
           homeparas: [],
           isSelectButton: false,
           homeparaFields: [
               {name: "ins_name", text: "Search Tên thiết bị"},
               {name: "ins_id", text: "Search Mã thiết bị"},
               {name: "parameter_type_name", text: "Search yếu tố đo"},
               {name: "manufact", text: "Search Hãng sản xuất"},
               {name: "ins_model", text: "Model"},
               {name: "status", text: "Search Trạng thái"}
           ],
           stateForm: "Add"
       }
   },
   methods:{
       clickAdd(){
           this.stateForm = "add";
        this.isSelectButton = true;
       },
       getParaHome(){
          HomePara.getParaHome().then((Response) =>{
              this.homeparas = Response.data;
          })
       },
       closeAddForm(){
           this.isSelectButton = false
       },
       updateIns(){
           this.stateForm = "edit"
           this.isSelectButton = true;

       }
   },
   created(){
       this.getParaHome();
   }
}
</script>
<style>
.info{
    width: 100%;
    height: calc(100vh - 70px);
}
.header-info{
    width: 100%;
    height: 100px;
    padding-left: 30px;
    padding-top: 20px;
} 
    
  
    .show{
        margin-top: 20px;
    }

  #inputNumber {
     width: 50px;
    
  }
    .tableInfo {
        margin: 0 20px;
        height: calc(100% - 150px);
    }

    .paging-bar {
        height: 50px;
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0 30px;
    }
</style>
