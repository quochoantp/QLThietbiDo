<template>
    <div class="grid">
        <table class="table-Info">
             <thead>
                 <th><input type="checkbox"/></th>
                 <th>#</th>
                 <th>Thao tác</th>
                 
                 <th 
                    v-for="(field,key) in fields"
                    :key="key"
                 >
                    <input v-if="searchable" type="text" :placeholder="field.text"/>
                    <div v-else>{{field.text}}</div>
                 </th>
             </thead>
            <tbody>
                <tr v-for="(item,index) in data" v-bind:key="index">
                    <td><input type="checkbox"></td>    
                    <td>{{index + 1}}</td>
                    <td><button class="btn-icon" @click="onClickUpdate"><i class="fas fa-edit"></i></button></td>
                    
                    <td
                        v-for="(field, key) in fields"
                        :key="key"
                    >
                        {{ item[field.name] }}
                    </td>
                </tr>
            </tbody>
           </table>
    </div>
</template>

<script>
export default {
    props: {
        fields: Array,
        data: Array,
        searchable: {
            type: Boolean,
            default: false
        }
    },
    methods: {
        onClickUpdate(){
            this.$emit("onclick-update")
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
        padding: 0 16px;
        text-align: left;
        white-space: nowrap;
        border-bottom: 1px solid #bbb;
        border-right: 1px solid #bbb ;
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