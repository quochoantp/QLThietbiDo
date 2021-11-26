<template>
  <div>
    <div class="dialog-content">
      
      <div class="dialog-main" id="ins-info">
        <div class="title">Thông số thiết bị đo</div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Tên thiết bị <span>*</span></div>
            <input class="input-box" type="text" v-model="inputIns_name"/>
          </div>
          <div class="input-label m-b-30">
            <div class="label">Mã thiết bị <span>*</span></div>
            <input class="input-box" type="text" v-model="inputIns_code" />
          </div>
          <div class="input-label ">
            <div class="label">Trạng thái <span>*</span></div>
            <Combobox :items ="status" :selectedItem= "inputStatus"/>
          </div>
          
        </div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Model <span>*</span></div>
            <input class="input-box" type="text" v-model="inputIns_Model"/>
          </div>
           <div class="input-label m-b-30">
            <div class="label">Hãng sản xuất <span>*</span></div>
            <input class="input-box" type="text" v-model="inputManufact" />
          </div>
          
        </div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Có thể điều khiển<span>*</span></div>
           <Combobox :items ="is_control" :selectedItem= "inputControl"/>
          </div>
          <div class="input-label m-b-30">
            <div class="label">Có thể quan sát<span>*</span></div>
            <Combobox :items ="is_observable" :selectedItem= "inputObservable"/>
          </div>
        </div>
      </div>
      <div class="dialog-main" id="para-ins">
        <div class="title">Yếu tố đo của thiết bị</div>
        <div class="row-1 m-b-30">
          <div class="input-label m-b-30">
            <div class="label">Thông số đo <span>*</span></div>
            <Combobox :items="dataType" @update-item="(item) => updateCombobox(item,'updateUnit')" />
          </div>
          <Button text="Thêm yếu tố mới" icon="fas fa-plus" />
        </div>
        <div class="row-2">
          <Button text="Thêm" class="m-r-30" icon="fas fa-plus" @click="updateTypeUnit" />
          <Button text="Làm mới" icon="fas fa-redo"/>
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
        <Button text="Cập nhật" class="m-r-30" icon="fas fa-plus" />
        <Button text="Hủy" icon="fas fa-backspace" @click="cancelForm" />
      </div>
    </div>
  </div>
</template>
<script>

 import HomePara from "../../services/HomePara"
 import { mapGetters } from "vuex";
 import Combobox from '../base/Combobox.vue';

export default {
  components: { Combobox },
   
  computed :{
    ...mapGetters(['updatedData']),
    ...mapGetters(['dataTypeUnit']),
    inputIns_name: {
      get(){
        return this.updatedData.ins_name
      },

    },
    inputIns_code: {
      get(){
        return this.updatedData.ins_code
      },
    },
     inputParameter: {
      get(){
        return this.updatedData.parameter_type_name
      },

    },
     inputManufact: {
      get(){
        return this.updatedData.manufact
      },
     },
      inputIns_Model: {
      get(){
        return this.updatedData.ins_model
      },
      },
      inputStatus: {
      get(){
        return this.updatedData.status
       },
      },
      inputControl: {
      get(){
        return this.updatedData.is_control_enable
       },
      },
      inputObservable: {
      get(){
        return this.updatedData.is_observable
      },
    },
  },
  
  data() { 
    return {
      fieldTables: [
        { name: "parameter_type_name", text: "Yếu tố đo" },
        { name: "unit_code", text: "Đơn vị đo" },
      ],
      dataType:[],
      is_control: [
        {value: "Có thể điều khiển", text :"Có"},
        {value: "Không thể điều khiển", text :"Không"}
      ],
       is_observable: [
        {value: "Có thể quan sát", text :"Có"},
        {value: "Không thể quan sát", text :"Không"}
      ],
       status: [
        {value: "Có sử dụng", text :"Có"},
        {value: "Không sử dụng", text :"Không"}
      ],
      dataTypeUnitTable: [],
      dataTypeTransfer:{ 
           unit_id : "",
           parameter_type_name :"", 
           unit_code : ""   },
    };
  },
  props: {
    state: String,
    paraunit: Array,
    item: Object
  },
  methods: {
    updateCombobox(itemUpdate,type){
      if(type == 'updateUnit'){
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
    updateTypeUnit(){
      var me = this
        const data = Object.assign({},this.dataTypeTransfer);
       for(let i of this.dataTypeUnitTable){
          if(i.unit_id == data.unit_id){
               return
           } 
        }
                 me.dataTypeUnitTable.push(data)  
                 me.dataTypeTransfer = {} 
            
      
    },
    cancelForm() {
      this.$emit("cancel-form");
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
    }    
  },
     created() {
       
       this.getDataType()
      
       HomePara.getParaUnitTable(this.item.ins_id).then((Response) =>{
              this.dataTypeUnitTable = Response.data;
              console.log(this.dataTypeUnitTable)
          })
      //this.getDataType();
      console.log(this.item)
  }
};
</script>
<style scoped>
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
.dialog-main .title {
  position: absolute;
  top: -10px;
  left: 30px;
  background-color: #ffffff;
  padding: 0 10px;
  color: #3385ff;
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
  margin-right: 15px;
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
