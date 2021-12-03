<template>
  <div>
    <div class="dialog-content">
      <div class="dialog-main" id="ins-info">
        <div class="title">Thông số thiết bị đo</div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Tên thiết bị <span>*</span></div>
            <!-- <input class="input-box" type="text" v-model="inputIns_name"/> -->
            <Input
              v-model="item.ins_name"
              type="text"
              label="Tên thiết bị"
              ref="input1"
            />
          </div>
          <div class="input-label m-b-30">
            <div class="label">Mã thiết bị <span>*</span></div>
            <!-- <input class="input-box" type="text" v-model="inputIns_code" /> -->
            <Input
              v-model="item.ins_code"
              type="text"
              label="Mã thiết bị"
              ref="input2"
            />
          </div>
          <div class="input-label">
            <div class="label">Trạng thái <span>*</span></div>
            <Combobox
              :items="status"
              :selectedItem="item.status"
              label="Trạng thái"
              ref="cbx1"
            />
          </div>
        </div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Model <span>*</span></div>
            <!-- <input class="input-box" type="text" v-model="inputIns_Model"/> -->
            <Input
              v-model="item.ins_model"
              type="text"
              label="Model"
              ref="input3"
            />
          </div>
          <div class="input-label m-b-30">
            <div class="label">Hãng sản xuất <span>*</span></div>
            <!-- <input class="input-box" type="text" v-model="inputManufact" /> -->
            <Input
              v-model="item.manufact"
              type="text"
              label="Hãng sản xuất"
              ref="input4"
            />
          </div>
        </div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Có thể điều khiển<span>*</span></div>
            <Combobox
              :items="is_control"
              :selectedItem="item.is_control_enable"
              label="Có thể điều khiển"
              ref="cbx2"
            />
          </div>
          <div class="input-label m-b-30">
            <div class="label">Có thể quan sát<span>*</span></div>
            <Combobox
              :items="is_observable"
              :selectedItem="item.is_observable"
              label="Có thể quan sát"
              ref="cbx3"
            />
          </div>
        </div>
      </div>
      <div class="dialog-main" id="para-ins">
        <div class="title">Yếu tố đo của thiết bị</div>
        <div class="row-1 m-b-30">
          <div class="input-label m-b-30" id="param-measure">
            <div class="label">Thông số đo <span>*</span></div>
            <Combobox
              :items="dataType"
              :selectedItem="none"
              @update-item="(item) => updateCombobox(item, 'updateUnit')"
            />
          </div>
          <Button
            text="Thêm yếu tố mới"
            icon="fas fa-plus"
            @click="onClickAddParam"
          />
        </div>
        <div class="row-2">
          <Button
            text="Thêm"
            class="m-r-30"
            icon="fas fa-plus"
            @click="updateTypeUnit"
            v-show="stateYtd === 'add'"
          />
          <Button
            v-show="stateYtd === 'add'"
            text="Làm mới"
            icon="fas fa-redo"
            @click="reloadCombobox"
          />
          <Button
            v-show="stateYtd === 'edit'"
            class="m-r-30"
            text="Cập nhật"
            icon="fas fa-edit"
            @click="updateSelectedItem"
          />
          <Button
            v-show="stateYtd === 'edit'"
            text="Xóa"
            icon="fas fa-eraser"
            @click="deleteSelectedItem"
          />
        </div>
        <div>
          <div class="show">
            <label style="padding-right: 5px">Show</label>
            <div style="margin-left: 10px; margin-right: 10px">
              <select v-model="page">
                <option value="10">10</option>
                <option value="25">25</option>
                <option value="50">50</option>
                <option value="100">100</option>
              </select>
            </div>
            <label>entries</label>
          </div>
          <Table
            :fields="fieldTables"
            :data="dataTypeUnitTable"
            :perPage="page"
            @onclick-update="clickUpdate"
          />
        </div>
      </div>
      <div class="dialog-footer">
        <Button
          text="Cập nhật"
          class="m-r-30"
          icon="fas fa-plus"
          @click="updateDataForm"
        />
        <Button text="Hủy" icon="fas fa-backspace" @click="cancelForm" />
      </div>
    </div>
  </div>
</template>
<script>
import HomePara from "../../services/HomePara";
import InstrumentationApi from "../../services/InstrumentApi";
import { mapGetters, mapActions } from "vuex";
import Combobox from "../base/Combobox.vue";

export default {
  components: { Combobox },

  computed: {
    ...mapGetters(["updatedData"]),
    ...mapActions(["updateProfile"]),
    ...mapGetters(["dataTypeUnit"]),

    // inputIns_name: {
    //   get() {
    //     return this.updatedData.ins_name;
    // },
    // inputIns_code: {
    //   get() {
    //     return this.updatedData.ins_code;
    //   },
    // },
    // inputParameter: {
    //   get() {
    //     return this.updatedData.parameter_type_name;
    //   },
    // },
    // inputManufact: {
    //   get() {
    //     return this.updatedData.manufact;
    //   },
    // },
    // inputIns_Model: {
    //   get() {
    //     return this.updatedData.ins_model;
    //   },
    // },
    // inputStatus: {
    //   get() {
    //     return this.updatedData.status;
    //   },
    // },
    // inputControl: {
    //   get() {
    //     return this.updatedData.is_control_enable;
    //   },
    // },
    // inputObservable: {
    //   get() {
    //     return this.updatedData.is_observable;
    //   },
    // },
  },

  data() {
    return {
      none: "",
      page: 10,
      stateYtd: "add",
      fieldTables: [
        { name: "parameter_type_name", text: "Yếu tố đo" },
        { name: "unit_code", text: "Đơn vị đo" },
      ],
      dataType: [],
      is_control: [
        { value: "Có thể điều khiển", text: "Có" },
        { value: "Không thể điều khiển", text: "Không" },
      ],
      is_observable: [
        { value: "Có thể quan sát", text: "Có" },
        { value: "Không thể quan sát", text: "Không" },
      ],
      status: [
        { value: "Có sử dụng", text: "Có" },
        { value: "Không sử dụng", text: "Không" },
      ],
      dataTypeUnitTable: [],
      datagetFromTable: {},
      dataInsIdParamIdArray: [],
      dataInsIdParamIdArraydelete: [],
      dataInsIdParamId: {
        insParamId: "",
        insId: "",
        parametertypeid: "",
      },
      dataInsIdParamIddelete: {
        insId: "",
        parametertypeid: "",
      },
      dataTypeTransfer: {
        unit_id: "",
        parametertypeid: "",
        parameter_type_name: "",
        unit_code: "",
      },
    };
  },
  props: {
    state: String,
    paraunit: Array,
    item: Object,
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
    clickUpdate(item) {
      this.stateYtd = "edit";
      this.datagetFromTable = item;
    },
    async updateDataForm() {
      if (this.validate() == true) {
        try {
          delete this.item.parameter_type_name_unit;
          this.item.insId = this.item.ins_id;
          if (this.item.status == "Có sử dụng") {
            this.item.status = 1;
          }
          if (this.item.status == "Không sử dụng") {
            this.item.status = 0;
          }
          if (this.item.is_control_enable == "Có thể điều khiển") {
            this.item.is_control_enable = 1;
          }
          if (this.item.is_control_enable == "Không thể điều khiển") {
            this.item.is_control_enable = 0;
          }
          if (this.item.is_observable == "Có thể quan sát") {
            this.item.is_observable = 1;
          }
          if (this.item.is_observable == "Không thể quan sát") {
            this.item.is_observable = 0;
          }
          console.log(this.item);
          this.dataInstrumentUpdate = { ...this.dataInstrumentUpdate };
          const res = await InstrumentationApi.update(
            this.item.ins_id,
            this.item
          );
          console.log(res);
          if (res.status === 200) {
            console.log(this.item);
            this.addNewParamToIns();
            alert("Cập nhật thành công!");
          }
          this.$emit("cancel-form");
        } catch (error) {
          alert("Có lỗi xảy ra tai them instrument " + error);
        }
      }

      // this.addNewParamToIns();
    },
    reloadCombobox() {
      this.none = "";
    },
    async updateSelectedItem() {
      const data = Object.assign({}, this.dataTypeTransfer);
      if (this.dataTypeUnitTable != null) {
        for (let i of this.dataTypeUnitTable) {
          if (i.unit_id == data.unit_id) {
            return;
          }
        }
      }
      if (data.unit_id != null) {
        await this.dataTypeUnitTable.push(data);
      }
      const index = this.dataTypeUnitTable.indexOf(this.datagetFromTable);
      if (index > -1) {
        this.dataTypeUnitTable.splice(index, 1);
      }
      this.dataTypeTransfer = {};
      this.stateYtd = "add";
    },
    deleteSelectedItem() {
      const index = this.dataTypeUnitTable.indexOf(this.datagetFromTable);
      if (index > -1) {
        this.dataTypeUnitTable.splice(index, 1);
      }
      this.dataTypeTransfer = {};
      this.stateYtd = "add";
    },
    updateCombobox(itemUpdate, type) {
      this.none = itemUpdate.value;
      if (type == "updateUnit") {
        var me = this;
        this.dataTypeUnit.forEach(function (item) {
          if (item.unit_id == itemUpdate.value) {
            me.dataTypeTransfer.unit_id = item.unit_id;
            me.dataTypeTransfer.parameter_type_name = item.parameter_type_name;
            me.dataTypeTransfer.unit_code = item.unit_code;
            me.dataTypeTransfer.parametertypeid = item.parametertypeid;
            console.log(me.dataTypeTransfer);
          }
        });
      }
    },
    async addNewParamToIns() {
      var me = this;

      this.dataTypeUnitTable.forEach(async function (item) {
        me.dataInsIdParamId.insId = me.item.ins_id;
        me.dataInsIdParamId.parametertypeid = item.parametertypeid;
        const data = Object.assign({}, me.dataInsIdParamId);
        await me.dataInsIdParamIdArray.push(data);
        // me.newInsParamId++;
        me.dataInsIdParamId = {};
      });
      console.log(this.dataInsIdParamIdArray);
      await InstrumentationApi.addNewParamToIns(this.dataInsIdParamIdArray);
    },
    async deleteAllParamIns() {
      var me = this;

      this.dataTypeUnitTable.forEach(async function (item) {
        me.dataInsIdParamIddelete.insId = item.ins_id;
        me.dataInsIdParamIddelete.parametertypeid = item.parametertypeid;
        const data = Object.assign({}, me.dataInsIdParamIddelete);
        await me.dataInsIdParamIdArraydelete.push(data);
        // me.newInsParamId++;
        me.dataInsIdParamIddelete = {};
      });
      console.log(this.dataInsIdParamIdArraydelete);
      await InstrumentationApi.delete(this.dataInsIdParamIdArraydelete);
    },
    getNewInsParamId() {
      InstrumentationApi.getNewInsParamId().then((Response) => {
        this.newInsParamId = Response.data;
        console.log(this.newInsParamId);
      });
    },
    onClickAddParam() {
      this.$emit("open-add-param-screen");
    },
    updateTypeUnit() {
      var me = this;
      const data = Object.assign({}, this.dataTypeTransfer);
      for (let i of this.dataTypeUnitTable) {
        if (i.unit_id == data.unit_id) {
          return;
        }
      }
      me.dataTypeUnitTable.push(data);
      me.dataTypeTransfer = {};
    },
    cancelForm() {
      this.$emit("cancel-form");
    },
    getDataType() {
      var me = this;
      this.dataTypeUnit.forEach(function (item, index) {
        me.dataType[index] = {
          value: item.unit_id,
          text: item.para_unit_code,
        };
        console.log(me.dataType);
      });
    },
  },

  created() {
    this.getDataType();
    this.getNewInsParamId();
    HomePara.getParaUnitTable(this.item.ins_id).then((Response) => {
      this.dataTypeUnitTable = Response.data;
      console.log(this.dataTypeUnitTable);
      this.deleteAllParamIns();
    });

    //this.getDataType();
    console.log(this.item);
  },
};
</script>
<style scoped>
.dialog {
  margin: 15px 7.5px 0 7.5px;
  position: relative;
  border: 1px gray solid;
  padding: 10px;
}
.show {
  width: 100%;
  display: flex;
  margin-bottom: 10px;
}
.dialog .dialog-content {
  position: absolute;
  top: -10px;
  left: 20px;
  background-color: #fff;
  padding: 0 5px;
  font-weight: bold;
}
#inputNumber {
  width: 50px;
  margin-right: 5px;
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
  margin-right: 15px;
}
.label {
  width: 150px;
  color: #3385ff;
}
.label span {
  color: red;
}
/* .input-box {
  width: 150px;
  height: 30px;
  border: 1px solid #3385ff;
  outline: none;
  border-radius: 5px;
  padding-left: 10px;
} */
.dialog-main > div {
  flex: 1;
}
#param-measure {
  display: flex;
}
</style>
