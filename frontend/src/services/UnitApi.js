import axios from 'axios'
class UnitApi{
async findUnitTable(searchData){
    return await axios.get(`http://localhost:8085/api/unit/${searchData}`)
}
async addNewUnit(newUnit){
    return await axios.post(`http://localhost:8085/api/unit`,newUnit)
}
async updateUnit(updateUnit,id){
    return await axios.put(`http://localhost:8085/api/unit/${id}`,updateUnit)
}
}
export default new UnitApi()