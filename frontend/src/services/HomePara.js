import axios from 'axios'
const HOME_PARA_URL ='http://localhost:8085/api/dataHomePage'

class HomePara{
   async getParaHome(){
        return await axios.get(HOME_PARA_URL);
    }
    async getDataUpdate(){
       return await axios.get('http://localhost:8085/api/datanotparse')
    }
    async getSearchData(searchData){
       return await axios.get(`http://localhost:8085/api/${searchData}`);
    }
    async getParaUnit(){
       return await axios.get('http://localhost:8085/api/getParaType');
    }
    async getParaUnitTable(id){
      return await axios.get(`http://localhost:8085/api/getParaTypeTable/${id}`);
   }
   async putUpdateInstrument(id){
      return await axios.put(`http://localhost:8085/api/update/${id}`)
   }
   //lay du lieu cho bang them moi yeu to do
   async getParameterUnitList(){
      return await axios.get('http://localhost:8085/api/param/getParamList')
   }
   async getUnitCodeList(){
      return await axios.get('http://localhost:8085/api/unit/getUnitList')
   }
}
export default new HomePara()