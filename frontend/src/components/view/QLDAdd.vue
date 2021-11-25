<template>
  <div>
    <div class="dialog-content">
      
      <div class="dialog-main" id="ins-info">
        <div class="title">Thông số thiết bị đo</div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Tên thiết bị <span>*</span></div>
            <input class="input-box" type="text" />
          </div>
          <div class="input-label m-b-30">
            <div class="label">Tên thiết bị <span>*</span></div>
            <input class="input-box" type="text" />
          </div>
          <div class="input-label " v-if="state == 'edit'">
            <div class="label">Trạng thái <span>*</span></div>
            <Combobox />
          </div>
          
        </div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Model <span>*</span></div>
            <input class="input-box" type="text" />
          </div>
           <div class="input-label m-b-30">
            <div class="label">Hãng sản xuất <span>*</span></div>
            <input class="input-box" type="text" />
          </div>
          
        </div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Có thể điều khiển<span>*</span></div>
            <Combobox :items ="is_control" />
          </div>
          <div class="input-label">
            <div class="label">Có thể quan sát<span>*</span></div>
            <Combobox :items ="is_observable"/>
          </div>
        </div>
      </div>
      <div class="dialog-main" id="para-ins">
        <div class="title">Yếu tố đo của thiết bị</div>
        <div class="row-1 m-b-30">
           <div class="input-label m-b-30">
            <div class="label">Thông số đo <span>*</span></div>
            <Combobox :items="dataType" @update-item="(item) => updateCombobox(item,'AddUnit')" />
          </div>
          <Button text="Thêm yếu tố mới" icon="fas fa-plus"/>
        </div>
        <div class="row-2">
          <Button text="Thêm" class="m-r-30" icon="fas fa-plus" @click="updateTypeUnit"/>
          <Button text="Làm mới" icon="fas fa-redo" />
        </div>
        <div>
          <div class="show">
            <label style="padding-right: 5px">Show</label>
            <input type="number" id="inputNumber" value="10" />
            <label>entries</label>
          </div>
         <Table :fields="fieldTables" :data="dataTypeUnitTable" />
        </div>
      </div>
      <div class="dialog-footer">
        <!-- <Button v-if="state == 'add'" class="m-r-30" text="Lưu" />
        <Button v-if="state == 'add'" class="m-r-30" text="Làm mới" />
        <Button v-if="state == 'edit'" class="m-r-30" text="Cập nhật" /> -->
         <Button class="m-r-30" text="Lưu" icon="fas fa-save"/>
        <Button  class="m-r-30" text="Làm mới" icon="fas fa-redo" />
        <Button text="Hủy" icon="fas fa-backspace" @click="cancelForm" />
      </div>
    </div>
  </div>
</template>
<script>


import { mapGetters } from "vuex";

export default {

  data() {
    return {
      fieldTables: [
        { name: "parameter_type_name", text: "Yếu tố đo" },
        { name: "unit_code", text: "Đơn vị đo" },
      ],
       dataTypeUnitTable: [],
       dataType:[],
       is_control: [
        {value: "Có thể điều khiển", text :"Có"},
        {value: "Không thể điều khiển", text :"Không"}
      ],
       is_observable: [
        {value: "Có thể quan sát", text :"Có"},
        {value: "Không thể quan sát", text :"Không"}
      ],
        dataTypeTransfer:{ 
           unit_id : "",
           parameter_type_name :"", 
           unit_code : ""   },
    
    };
  },
  computed :{
    ...mapGetters(["dataTypeUnit"]),
  },
  props: {
    state: String,
  },
  methods: {
    updateCombobox(itemUpdate,type){
      if(type == 'AddUnit'){
      var me = this;
      this.dataTypeUnit.forEach(function(item){
         
          if(item.unit_id == itemUpdate.value){
            
           me.dataTypeTransfer.unit_id = item.unit_id;
           me.dataTypeTransfer.parameter_type_name = item.parameter_type_name;  
           me.dataTypeTransfer.unit_code = item.unit_code;   
           console.log(me.dataTypeTransfer)    
          } 
        
      }
      );
    } 
    },
     getDataType(){
           var me = this;
           this.dataTypeUnit.forEach(function(item,index){
           me.dataType[index]= {
             value: item.unit_id,
             text: item.para_unit_code
             
           }
           console.log(me.dataType);
        }
      )
    },    
    cancelForm() {
      this.$emit("cancel-form");
    },
    updateTypeUnit(){
      var me = this
        const data = Object.assign({},this.dataTypeTransfer);
       for(let i of this.dataTypeUnitTable){
          if(i.unit_id == data.unit_id){
               return
           } 
        }
                 me.dataTypeUnitTable.push(data)  
                 console.log(me.dataTypeUnitTable);
                 me.dataTypeTransfer = {} 
            
        }
    },
  
  created() {
     this.getDataType()
   
  }
};
</script>
<style>
.dialog {
 margin: 15px 7.5px 0 7.5px;
  position: relative;
  border: 1px gray solid;
  padding: 10px
}

.dialog .dialog-content {
 position: absolute;
  top: -10px;
  left: 20px;
  background-color: #fff;
  padding: 0 5px;
  font-weight: bold;
}
.dialog-header {
  height: 50px;
  padding: 10px 40px;
  border-bottom: 1px solid #3385ff;
  display: flex;
  align-items: center;
  font-size: 24px;
}
.dialog-main {
  margin: 30px 15px;
  border: 1px solid #3385ff;

  padding: 30px;
  position: relative;
}
 .show {
    margin-top: 15px;
    margin-bottom: 15px;
 } 
.dialog-main .title {
  position: absolute;
  top: -10px;
  left: 30px;
  background-color: #ffffff;
  padding: 0 10px;
  color: #3385ff;
}
#inputNumber{
    width:50px ;
    margin-right: 5px ;
}
#ins-info {
  display: flex;
  justify-content: space-between;
}
.m-b-30 {
  margin-bottom: 30px;
}
#para-ins .row-1 {
  display: flex;
  justify-content: flex-start;
}
#para-ins .row-2 {
  display: flex;
  justify-content: center;
}
.m-r-40 {
  margin-right: 40px;
}

.dialog-footer {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
}

.m-r-30 {
  margin-right: 30px;
}

.input-label {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.label {
  margin-right: 20px;
  color: #3385ff;
}
.label span {
  color: red;
}
.input-box {
  width: 150px;
  height: 30px;
  border: 1px solid #3385ff;
  outline: none;
  border-radius: 5px;
  padding-left: 10px;
}
</style>
