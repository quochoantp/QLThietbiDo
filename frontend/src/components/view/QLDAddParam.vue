<template>
  <div>
    <div class="info-form">
      <div class="title m-b-30">Thông tin yếu tố</div>

      <div class="form-row" id="row-1">
        <div class="input-label m-b-30">
          <div class="label">Tên yếu tố <span>*</span></div>
          <!-- <input
            class="input-box"
            type="text"
            v-model="camera.cameraName"
            @blur="onBlur($event.target)"
            ref="input1"
            :invalid="false"
          /> -->
          <Input
            label="Tên yếu tố"
            type="text"
            v-model="unitName"
            ref="input1"
          />
        </div>
      </div>
      <div class="form-row">
        <div class="input-label m-b-30">
          <div class="label">Mô tả</div>
          <!-- <input
            class="input-box"
            type="text"
            v-model="camera.position"
            @blur="onBlur($event.target)"
            ref="input2"
          /> -->
          <Input label="Mô tả" type="text" v-model="description" />
        </div>
        <div class="input-label m-b-30">
          <div class="label">Đơn vị tính <span>*</span></div>
          <Combobox
            label="Đơn vị tính"
            :items="unitListExport"
            :selectedItem="unitIdParam"
            ref="cbx1"
            @update-item="(item) => updateCombobox(item, 'updateUnit')"
          />
        </div>
      </div>
    </div>

    <div class="wrap-button">
      <Button
        v-show="state === 'add'"
        class="btn"
        text="Lưu"
        icon="fas fa-plus"
        @click="addNewUnitMethod"
      />

      <Button
        v-show="state === 'add'"
        class="btn"
        text="Làm mới"
        icon="fas fa-redo"
        @click="resetForm"
      />
      <Button
        v-show="state === 'edit'"
        class="btn"
        text="Cập nhật"
        icon="fas fa-edit"
        @click="updateUnitParam"
      />
      <Button
        class="btn"
        text="Hủy"
        icon="fas fa-long-arrow-alt-left"
        @click="closeScreen"
      />
    </div>

    <div class="grid-container">
      <div class="grid-header">
        Show
        <select style="margin-left: 10px; margin-right: 10px" v-model="page">
          <option value="10">10</option>
          <option value="25">25</option>
          <option value="50">50</option>
          <option value="100">100</option>
        </select>
        entries
      </div>
      <div class="grid">
        <Table
          :data="paraUnitList"
          :fields="paramFields"
          :state="stateUnit"
          :searchData="searchDataUnit"
          :searchable="true"
          @putSearchData="putSearchData"
          iconOperation="fas fa-edit"
          :perPage="page"
          @onclick-update="clickUpdate"
        />
      </div>
    </div>
  </div>
</template>

<script>
import HomePara from "../../services/HomePara";
import UnitApi from "../../services/UnitApi";
export default {
  data() {
    return {
      stateUnit: "Unit",
      page: 10,
      state: "add",
      unitList: [],
      unitListExport: [],
      paraUnitList: [],
      searchDataUnit: [
        { name: "unit_name", text: "" },
        { name: "unit_code", text: "" },
        { name: "description", text: "" },
      ],
      paramFields: [
        {
          name: "unit_name",
          text: "Search Tên yếu tố",
        },

        {
          name: "unit_code",
          text: "Search đơn vị",
        },
        {
          name: "description",
          text: "Search mô tả",
        },
      ],

      unitName: "",
      description: "",
      unitCode: "",
      unitIdParam: "",
      unitId: 0,
      indexInvalid: [],
      itemAdd: {
        unit_name: "",
        unit_code: "",
        description: "",
      },
      itemUpdate: {
        unit_id: "",
        unit_name: "",
        unit_code: "",
        description: "",
      },
    };
  },

  props: {},

  created() {
    this.getDataTable();
    this.getUnitCode();
  },
  methods: {
    validate() {
      var me = this;
      let isValid = true;
      this.indexInvalid = [];
      Object.entries(this.$refs).forEach(function (item, index) {
        item[1].$refs.BaseInput.focus();
        item[1].$refs.BaseInput.blur();
        item[1].isShowMsg = false;

        if (item[1].invalid == true) {
          isValid = false;
          me.indexInvalid.push(index);
        }
      });
      if (isValid == false) {
        // Hiển thị popup cảnh báo dữ liệu không hợp lệ
        alert("Dữ liệu không hợp lệ");
        Object.entries(this.$refs)[
          this.indexInvalid[0]
        ][1].$refs.BaseInput.focus();

        Object.entries(this.$refs)[this.indexInvalid[0]][1].isShowMsg = true;
        console.log(Object.entries(this.$refs)[this.indexInvalid[0]]);
      }
      return isValid;
    },
    async addNewUnitMethod() {
      if (this.validate() == true) {
        this.itemAdd.unit_name = this.unitName;
        this.itemAdd.unit_code = this.unitCode;
        this.itemAdd.description = this.description;
        console.log(this.itemAdd);
        try {
          this.itemAdd = { ...this.itemAdd };
          const res = await UnitApi.addNewUnit(this.itemAdd);
          console.log(res);
          if (res.status === 200) {
            alert("Thêm thành công");
            this.$emit("close-screen");
          }
        } catch (error) {
          alert("Có lỗi xảy ra tai addUnit " + error);
        }
      }
    },
    async putSearchData(searchData) {
      console.log(this.searchDataUnit);
      var str = "search?";
      searchData.forEach((element) => {
        str += element.name;
        str += "=";
        str += element.text;
        str += "&";
      });
      str.slice(0, -1);
      this.paraUnitList = [];
      const response = await UnitApi.findUnitTable(str);
      this.paraUnitList = response.data;
      console.log(this.paraUnitList);
    },
    getDataTable() {
      HomePara.getUnitCodeList().then((Response) => {
        this.paraUnitList = Response.data;
      });
    },
    resetForm() {
      this.unitName = "";
      this.description = "";
      this.unitCode = "";
      this.unitIdParam = "";
    },
    closeScreen() {
      this.$emit("close-screen");
    },
    clickUpdate(item) {
      this.state = "edit";
      console.log(item);
      this.unitName = item.unit_name;
      this.description = item.description;
      this.unitCode = item.unit_code;
      this.unitIdParam = item.unit_id;
    },
    async updateUnitParam() {
      this.itemUpdate.unit_name = this.unitName;
      this.itemUpdate.unit_code = this.unitCode;
      this.itemUpdate.description = this.description;
      this.itemUpdate.unit_id = this.unitIdParam;
      try {
        this.itemUpdate = { ...this.itemUpdate };
        const res = await UnitApi.updateUnit(
          this.itemUpdate,
          this.itemUpdate.unit_id
        );
        console.log(res);
        if (res.status === 200) {
          alert("Cập nhật thành công");
        }
        this.$emit("close-screen");
      } catch (error) {
        alert("Có lỗi xảy ra tai updateUnit " + error);
      }
    },
    getUnitCode() {
      var me = this;
      HomePara.getUnitCodeList().then((Response) => {
        this.unitList = Response.data;
        console.log(this.unitList);
        me.unitList.forEach(function (item, index) {
          me.unitListExport[index] = {
            value: item.unit_id,
            text: item.unit_code,
          };
        });
        console.log(this.unitListExport);
        this.unitListExport = [...this.unitListExport];
      });
    },
    updateCombobox(itemUpdate, type) {
      if (type == "updateUnit") {
        var me = this;
        this.unitList.forEach(function (item) {
          if (item.unit_id == itemUpdate.value) {
            me.unitCode = item.unit_code;
          }
        });
      }
    },
    
  },
};
</script>

<style scoped>
.info-form {
  margin: 15px 7.5px 0 7.5px;
  position: relative;
  border: 1px gray solid;
  padding: 10px;
  height: 200px;
}
.info-form .title {
  position: absolute;
  top: -10px;
  left: 20px;
  background-color: #fff;
  padding: 0 5px;
  font-weight: bold;
}
.form-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 16px;
}
#row-1 {
  margin-top: 16px;
}

.input-label {
  width: 50%;
  padding: 0 5px;
}
.label {
  margin-bottom: 8px;
  font-weight: 700;
}

.label span {
  color: red;
}
.input-box {
  width: 100%;
  height: 35px;
  border: 1px solid #ced4da;
  outline: none;
  border-radius: 0.25rem;
  padding: 6px 12px;
}
.input-box:focus {
  border-color: #80bdff;
  box-shadow: 0 0 0 0.2rem rgb(0 123 255 / 25%);
}
.wrap-button {
  display: flex;
  justify-content: center;
}
.wrap-button .btn {
  margin: 0.5em 1em;
}
.input-acc {
  display: flex;

  align-items: center;
}
.input-acc > div {
  flex: 1;
}
select {
  height: calc(1.8125rem + 2px);
  border-radius: 0.2rem;
  outline: none;
  border: 1px solid #ced4da;
}
select:focus {
  border-color: #80bdff;
  box-shadow: inset 0 1px 2px rgb(0 0 0 / 8%), 0 0 5px rgb(128 189 255 / 50%);
}
.border-red {
  border-color: red;
}
</style>
