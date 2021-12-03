<template>
  <div>
    <div class="dialog-content">
      <div class="dialog-main" id="ins-info">
        <div class="title">Thông số thiết bị đo</div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Tên thiết bị <span>*</span></div>
            <Input
              label="Tên thiết bị"
              type="text"
              ref="input1"
              v-model="newInstrumentation.ins_name"
            />
          </div>
          <div class="input-label m-b-30">
            <div class="label">Mã thiết bị <span>*</span></div>
            <Input
              label="Mã thiết bị"
              type="text"
              v-model="newInstrumentation.ins_code"
              ref="input2"
            />
          </div>
          <div class="input-label" v-if="state == 'edit'">
            <div class="label">Trạng thái <span>*</span></div>
            <Combobox />
          </div>
        </div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Model <span>*</span></div>
            <Input
              label="Model"
              type="text"
              v-model="newInstrumentation.ins_model"
              ref="input3"
            />
          </div>
          <div class="input-label m-b-30">
            <div class="label">Hãng sản xuất <span>*</span></div>
            <Input
              label="Hãng sản xuất"
              type="text"
              v-model="newInstrumentation.manufact"
              ref="input4"
            />
          </div>
        </div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Có thể điều khiển<span>*</span></div>
            <Combobox
              label="Có thể điều khiển"
              :items="is_control"
              :selectedItem="newInstrumentation.is_control_enable"
              @update-item="(item) => updateCombobox(item, 'isControl')"
              ref="cbx1"
            />
          </div>
          <div class="input-label">
            <div class="label">Có thể quan sát<span>*</span></div>
            <Combobox
              label="Có thể quan sát"
              :items="is_observable"
              :selectedItem="newInstrumentation.is_observable"
              @update-item="(item) => updateCombobox(item, 'isObser')"
              ref="cbx2"
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
              @update-item="(item) => updateCombobox(item, 'addUnit')"
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
            text="Cập nhật"
            class="m-r-30"
            icon="fas fa-plus"
            @click="updateSelectedItem"
            v-show="stateYtd === 'edit'"
          />
          <Button
            text="Làm mới"
            icon="fas fa-redo"
            @click="reloadCombobox"
            v-show="stateYtd === 'add'"
          />
          <Button
            text="Xóa"
            icon="fas fa-trash"
            v-show="stateYtd === 'edit'"
            @click="deleteSelectedItem"
          />
        </div>
        <div>
          <div class="show">
            <!-- <label style="padding-right: 5px">Show</label>
            <input type="number" id="inputNumber" value="10" />
            <label>entries</label> -->
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
        <!-- <Button v-if="state == 'add'" class="m-r-30" text="Lưu" />
        <Button v-if="state == 'add'" class="m-r-30" text="Làm mới" />
        <Button v-if="state == 'edit'" class="m-r-30" text="Cập nhật" /> -->
        <Button
          class="m-r-30"
          text="Lưu"
          icon="fas fa-save"
          @click="addNewIns"
        />
        <Button
          class="m-r-30"
          text="Làm mới"
          icon="fas fa-redo"
          @click="reloadForm"
        />
        <Button text="Hủy" icon="fas fa-backspace" @click="cancelForm" />
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import Input from "../base/Input.vue";
import InstrumentationApi from "../../services/InstrumentApi";
// import HomePara from "../../services/HomePara";
export default {
  components: { Input },
  data() {
    return {
      none: "",
      newId: 1000,
      stateYtd: "add",
      newInsParamId: 1000,
      page: 4,
      newInstrumentation: {},
      fieldTables: [
        { name: "parameter_type_name", text: "Yếu tố đo" },
        { name: "unit_code", text: "Đơn vị đo" },
      ],
      dataTypeUnitTable: [],
      dataType: [],
      is_control: [
        { value: 1, text: "Có" },
        { value: 0, text: "Không" },
      ],
      is_observable: [
        { value: 1, text: "Có" },
        { value: 0, text: "Không" },
      ],
      dataTypeTransfer: {
        parametertypeid: "",
        unit_id: "",
        parameter_type_name: "",
        unit_code: "",
      },
      datagetFromTable: {},
      dataInsIdParamIdArray: [],
      dataInsIdParamId: {
        insParamId: "",
        insId: "",
        parametertypeid: "",
      },

      stateParam: "add",
    };
  },
  computed: {
    ...mapGetters(["dataTypeUnit"]),
  },
  props: {
    state: String,
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
    reloadForm() {
      this.newInstrumentation.ins_name = "";
      this.newInstrumentation.ins_code = "";
      this.newInstrumentation.ins_model = "";
      this.newInstrumentation.manufact = "";
      this.newInstrumentation.is_control_enable = "";
      this.newInstrumentation.is_observable = "";
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
      if (type == "addUnit") {
        var me = this;
        this.dataTypeUnit.forEach(function (item) {
          if (item.unit_id == itemUpdate.value) {
            me.dataTypeTransfer.parametertypeid = item.parametertypeid;
            me.dataTypeTransfer.unit_id = item.unit_id;
            me.dataTypeTransfer.parameter_type_name = item.parameter_type_name;
            me.dataTypeTransfer.unit_code = item.unit_code;
            console.log(me.dataTypeTransfer);
          }
        });
      } else if (type == "isControl") {
        this.newInstrumentation = {
          ...this.newInstrumentation,
          is_control_enable: itemUpdate.value,
        };
      } else if (type == "isObser") {
        this.newInstrumentation = {
          ...this.newInstrumentation,
          is_observable: itemUpdate.value,
        };
      }
    },
    async addNewIns() {
      if (this.validate() == true) {
        try {
          this.newInstrumentation = { ...this.newInstrumentation };
          const res = await InstrumentationApi.addNewInstrumentation(
            this.newInstrumentation
          );
          console.log(res);
          if (res.status === 200) {
            console.log(this.dataTypeUnitTable);
            this.addNewParamToIns();
            alert("Thêm thành công");
          }
          this.$emit("cancel-form");
        } catch (error) {
          alert("Có lỗi xảy ra tai them instrument " + error);
        }
      }
    },
    async addNewParamToIns() {
      var me = this;

      this.dataTypeUnitTable.forEach(async function (item) {
        // me.dataInsIdParamId.insParamId = me.newInsParamId;
        me.dataInsIdParamId.insId = me.newId;
        me.dataInsIdParamId.parametertypeid = item.parametertypeid;
        const data = Object.assign({}, me.dataInsIdParamId);
        await me.dataInsIdParamIdArray.push(data);
        // me.newInsParamId++;
        me.dataInsIdParamId = {};
      });
      await InstrumentationApi.addNewParamToIns(this.dataInsIdParamIdArray);
    },

    getNewInsParamId() {
      InstrumentationApi.getNewInsParamId().then((Response) => {
        this.newInsParamId = Response.data;
        console.log(this.newInsParamId);
      });
    },
    getNewIns() {
      InstrumentationApi.getNewId().then((Response) => {
        this.newId = Response.data;
        this.newInstrumentation.insId = this.newId;
        console.log(this.newId);
      });
    },
    resetForm() {
      this.newInstrumentation = {};
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
    cancelForm() {
      this.$emit("cancel-form");
    },
    async updateTypeUnit() {
      var me = this;
      const data = Object.assign({}, this.dataTypeTransfer);
      if (this.dataTypeUnitTable != null) {
        for (let i of this.dataTypeUnitTable) {
          if (i.unit_id == data.unit_id) {
            return;
          }
        }
      }
      if (data.unit_id != null) {
        await me.dataTypeUnitTable.push(data);
      }
      console.log(me.dataTypeUnitTable);
      me.dataTypeTransfer = {};
    },
    onClickUpdate() {
      this.stateParam = "edit";
    },
    onClickAddParam() {
      this.$emit("open-add-param-screen");
    },
  },

  created() {
    this.getDataType();
    this.getNewIns();
    this.getNewInsParamId();
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

.show {
  margin-top: 15px;
  margin-bottom: 15px;
}
.dialog-main .title {
  position: absolute;
  top: -10px;
  left: 30px;
  background-color: #ffffff;
  padding: 0 10px;
  color: #3385ff;
}
#inputNumber {
  width: 50px;
  margin-right: 5px;
}
#ins-info {
  display: flex;
  justify-content: space-between;
}
#ins-info > div {
  flex: 1;
  margin: 0 16px;
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

/* .input-label {
  
} */
.label {
  width: 150px;
  color: #3385ff;
}
.label span {
  color: red;
}

#param-measure {
  display: flex;
}
</style>
