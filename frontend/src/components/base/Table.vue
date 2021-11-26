<template>
    <div class="grid">
        <table class="table-Info">
             <thead>
                 <th class="tdNumber"><input type="checkbox"/></th>
                 <th class="tdNumber">#</th>
                 <th class="tdButton">Thao tác</th>
                 
                 <th class="tdInfo"
                    v-for="(field,key) in fields"
                    :key="key"
                 >
                    <input class="inputSearch" v-if="searchable" type="text" :placeholder="field.text" v-model="searchData[key].text" @keyup.enter="putSearchData"/>
                    <div v-else>{{field.text}}</div>
                 </th>
             </thead>
            <tbody>
                <tr>
                    
                    <td class="tdNumber"><input type="checkbox"></td>    
                    <td class="tdNumber">0</td>
                    <td class="tdButton"><button class="btn-icon"  @click="onClickUpdate(item) "><i class="fas fa-edit"></i></button></td>
                    
                </tr>
                <tr v-for="(item,index) in data" v-bind:key="index">
                    <td class="tdNumber"><input type="checkbox"></td>    
                    <td class="tdNumber">{{index + 1}}</td>
                    <td class="tdButton"><button class="btn-icon"  @click="onClickUpdate(item) "><i class="fas fa-edit"></i></button></td>
                    
                    <td  class="tdInfo"
                        v-for="(field, key) in fields"
                        :key="key"
                    >
                        {{getDataParse(item[field.name],field.name)}}
                        
                    </td>
                </tr>
            </tbody>
           </table>
    </div>
</template>

<script>
import {mapGetters} from "vuex"
export default {
    computed: {
    ...mapGetters(["updatedData"]),
  },
    data() {
        return{
           searchData : [
               {name: "ins_name", text: ""},
               {name: "ins_code", text: ""},
               {name: "parameter_type_name", text: ""},
               {name: "manufact", text: ""},
               {name: "ins_model", text: ""},
               {name: "status", text: ""},
               {name: "is_control_enable",text:""},
               {name: "is_observable" , text:""}
           ],
        //    updatedData : [
        //        {name: "ins_name", text: ""},
        //        {name: "ins_code", text: ""},
        //        {name: "parameter_type_name", text: ""},
        //        {name: "manufact", text: ""},
        //        {name: "ins_model", text: ""},
        //        {name: "status", text: ""},
        //        {name: "is_control_enable",text:""},
        //        {name: "is_observable" , text:""}
        //    ]
        }
    },
    props: {
        fields: Array,
        data: Array,
        
        searchable: {
            type: Boolean,
            default: false
        }
    },
    methods: {
    //    ...mapMutations(['setUpdatedData']),
    //    ...mapActions(['updateProfile']),
        onClickUpdate(item){
            this.$emit("onclick-update",item)
            //this.updateProfile(item)
            
        },

        getDataParse(data,item){
          if(item == "parameter_type_name_unit"){
              var str = "";
             data.forEach(element => {
                 
                 str += element;
                 str += "; " ;
               
             });
                str = str.substring(0, str.length - 2);
             return str;
          } else {
              return data;
          }
        },
        putSearchData(){
            this.$emit("putSearchData",this.searchData);
           console.log(this.searchData);
        }
    },
}
</script>

<style scoped>
    .grid {
        overflow: auto;
        height: 100%;
        max-width: 100%;
    }
    table{
        border-collapse: separate;   
        width: 100%;
        border-spacing: 0;
        table-layout: fixed;
        
    }
    th{
        position: sticky;
        top: 0;
        z-index: 5;
        background-color: #fff;
        border-top: 1px solid #bbb;
       
    }
    th, td {
        height: 48px;
        max-height: 200px;
        padding: 0 16px;
        text-align: left;
        border-bottom: 1px solid #bbb;
        border-right: 1px solid #bbb ;
         
    } 
    .tdNumber{
        text-align: center;
        width: 50px;
       
    }
    .tdButton{
        text-align: center;
        width: 100px;
        
    }
    .tdInfo{
       width: 220px;
       line-break:auto;
       
    }
    td:nth-child(1) {
        border-left: 1px solid #bbb;
    }
    th:nth-child(1) {
        border-left: 1px solid #bbb;
    }

    .grid::-webkit-scrollbar-track {
        margin-top: 48px;
        width: 6px;
        background-color: #bbb;
    }
    .inputSearch {
        border-radius: 15px;
        text-align: center;
        width: 100%;
        height: 60%;
    }
    .grid::-webkit-scrollbar {
        width: 5px !important;
        height: 5px !important;
    }
    .grid::-webkit-scrollbar-thumb{
        border-radius: 8px;
        background-color: #808080;
    }
    .btn-icon{
        border: none;
        outline: none;
        background: #fff;
    }
</style>