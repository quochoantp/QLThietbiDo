import axios from 'axios'
class InstrumentApi{
async getNewId(){
    return await axios.get('http://localhost:8085/api/getNewIns_id')
}
async getNewInsParamId(){
    return await axios.get('http://localhost:8085/api/getMaxInsParamId')
}
async addNewInstrumentation(data){
    return await axios.post(`http://localhost:8085/api/addNewIns`, data)
}
async addNewParamToIns(data){
    return await axios.post(`http://localhost:8085/api/addInsParam`,data)
}
async update(id, data){
    return await axios.put(`http://localhost:8085/api/update/${id}`,data)
}

async delete(data){
    return await axios.post(`http://localhost:8085/api/delete`,data)
}
}
export default new InstrumentApi()