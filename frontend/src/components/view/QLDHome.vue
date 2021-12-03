<template>
  <div class="main">
    <div class="button-bar">
      <Button
        class="btn-add"
        text="Thêm"
        icon="fas fa-plus"
        @click="openAddScreen"
      />
    </div>
    <div class="grid-container">
      <div class="grid-header">
        Show
        <div style="margin-left: 10px; margin-right: 10px">
          <select v-model="page">
            <option value="10">10</option>
            <option value="25">25</option>
            <option value="50">50</option>
            <option value="100">100</option>
          </select>
        </div>
        entries
      </div>
      <div class="grid">
        <Table
          @putSearchData="putSearchData"
          :data="homeparas"
          :fields="homeparaFields"
          :searchable="true"
          :searchData="searchData"
          :perPage="page"
          :state="stateHome"
          iconOperation="fas fa-edit"
          @onclick-update="openUpdateScreen"
        />
      </div>
    </div>
    <!-- <div class="paging-bar">
      <div class="paging-left">Showing...</div>
      <div class="paging-right">
      <Pagination />
     
      </div> -->
  </div>
</template>

<script>
import HomePara from "../../services/HomePara";
import { mapActions, mapMutations } from "vuex";
export default {
  data() {
    return {
      page: 10,
      stateHome: "Home",
      homeparas: [],
      paratypeunit: [],
      yeutodo: [],
      searchData: [
        { name: "ins_name", text: "" },
        { name: "ins_code", text: "" },
        { name: "parameter_type_name", text: "" },
        { name: "manufact", text: "" },
        { name: "ins_model", text: "" },
        { name: "status", text: "" },
        { name: "is_control_enable", text: "" },
        { name: "is_observable", text: "" },
      ],
      homeparaFields: [
        { name: "ins_name", text: "Search Tên thiết bị" },
        { name: "ins_code", text: "Search Mã thiết bị" },
        { name: "parameter_type_name_unit", text: "Search yếu tố đo" },
        { name: "manufact", text: "Search Hãng sản xuất" },
        { name: "ins_model", text: "Model" },
        { name: "status", text: "Search Trạng thái" },
        { name: "is_control_enable", text: "Search quyền điều khiển" },
        { name: "is_observable", text: "Search quan sát" },
      ],
    };
  },

  methods: {
    ...mapMutations(["setDataTypeUnit"]),
    ...mapActions(["setDataUnit"]),
    ...mapMutations(["setUpdatedData"]),
    ...mapActions(["updateProfile"]),
    openUpdateScreen(item) {
      this.$emit("open-update-screen", item);

      this.updateProfile(item);
    },
    openAddScreen() {
      this.$emit("open-add-screen");
    },
    getParaHome() {
      HomePara.getParaHome().then((Response) => {
        this.homeparas = Response.data;
      });
      console.log(this.homeparas);
    },
    getParaTypeUnit() {
      HomePara.getParaUnit().then((Response) => {
        this.paratypeunit = Response.data;
        this.setDataUnit(this.paratypeunit);
        console.log(this.paratypeunit);
      });
    },
    async putSearchData(searchData) {
      var str = "search?";
      searchData.forEach((element) => {
        str += element.name;
        str += "=";
        str += element.text;
        str += "&";
      });
      str.slice(0, -1);
      this.homeparas = [];
      const response = await HomePara.getSearchData(str);
      this.homeparas = response.data;
      console.log(this.homeparas);
    },
  },
  created() {
    this.getParaHome();
    this.getParaTypeUnit();
  },
};
</script>

<style>
.button-bar {
  display: flex;
  height: 40px;
  margin-top: 20px;
  align-items: center;
  padding-left: 16px;
}
.grid-container {
  padding: 16px 16px 0 16px;
}
.grid-header {
  margin-bottom: 16px;
  display: flex;
}
.grid-header input {
  height: 25px;
  width: 50px;
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
