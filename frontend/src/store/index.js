
const storeConfigs = {
    state: {
        updatedData : {
            ins_name: '',
            ins_code: '',
            parameter_type_name: '',
            manufact: '',
            ins_model: '',
            status: '',
            is_control_enable:'',
            is_observable:'',
        },
        paraunit: [],
    },
    
    getters: {
        updatedData: state => state.updatedData,
        dataTypeUnit: state => state.paraunit
    },
    mutations: {
        setUpdatedData(state,item){
        state.updatedData.ins_name = item['ins_name'];
        state.updatedData.ins_code = item['ins_code'];
        state.updatedData.parameter_type_name = item['parameter_type_name'];
        state.updatedData.manufact = item['manufact'];
        state.updatedData.ins_model = item['ins_model'];
        state.updatedData.status = item['status'];
        state.updatedData.is_control_enable = item['is_control_enable'];
        state.updatedData.is_observable = item['is_observable'];
        },
        setDataTypeUnit(state,item){
            state.paraunit = item;
        }
    },
    actions: {
        updateProfile({commit}, item){
            commit('setUpdatedData', item)
        },
        setDataUnit({commit},item){
            commit('setDataTypeUnit',item)
        }

    }
  }
  export default storeConfigs