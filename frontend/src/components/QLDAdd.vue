<template>
  <div class="dialog">
    <div class="dialog-content">
      <div class="dialog-header" v-if="state == 'add'">
        Quản lý thiết bị đo >> Thêm mới thiết bị
      </div>
      <div class="dialog-header" v-if="state == 'edit'">
        Quản lý thiết bị đo >> Cập nhật thiết bị
      </div>
      <div class="dialog-main" id="ins-info">
        <div class="title">Thông số thiết bị đo</div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Tên thiết bị <span>*</span></div>
            <input class="input-box" type="text" />
          </div>
          <div class="input-label">
            <div class="label">Tên thiết bị <span>*</span></div>
            <input class="input-box" type="text" />
          </div>
          <div class="input-label" v-if="state == 'edit'">
            <div class="label">Trạng thái <span>*</span></div>
            <Combobox />
          </div>
          
        </div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Model <span>*</span></div>
            <input class="input-box" type="text" />
          </div>
           <div class="input-label m-b-30">
            <div class="label">Hãng sản xuất <span>*</span></div>
            <input class="input-box" type="text" />
          </div>
          
        </div>
        <div>
          <div class="input-label m-b-30">
            <div class="label">Có thể điều khiển<span>*</span></div>
            <Combobox/>
          </div>
          <div class="input-label">
            <div class="label">Có thể quan sát<span>*</span></div>
            <Combobox/>
          </div>
        </div>
      </div>
      <div class="dialog-main" id="para-ins">
        <div class="title">Yếu tố đo của thiết bị</div>
        <div class="row-1 m-b-30">
          <Input label="Thông số đo" class="m-r-40" />
          <Button text="Thêm yếu tố mới" />
        </div>
        <div class="row-2">
          <Button text="Thêm" class="m-r-30" />
          <Button text="Làm mới" />
        </div>
        <div>
          <div class="show">
            <label style="padding-right: 5px">Show</label>
            <input type="number" id="inputNumber" value="10" />
            <label>entries</label>
          </div>
          <Table :fields="fieldTables" />
        </div>
      </div>
      <div class="dialog-footer">
        <Button v-if="state == 'add'" class="m-r-30" text="Lưu" />
        <Button v-if="state == 'add'" class="m-r-30" text="Làm mới" />
        <Button v-if="state == 'edit'" class="m-r-30" text="Cập nhật" />
        <Button text="Hủy" @click="cancelForm" />
      </div>
    </div>
  </div>
</template>
<script>
import Button from "./base/Button.vue";
import Combobox from "./base/Combobox.vue";
import Input from "./base/Input.vue";
import Table from "./base/Table.vue";

export default {
  components: { Input, Button, Table, Combobox },
  data() {
    return {
      fieldTables: [
        { name: "", text: "Yếu tố đo" },
        { name: "", text: "Đơn vị đo" },
      ],
    };
  },
  props: {
    state: String,
  },
  methods: {
    cancelForm() {
      this.$emit("cancel-form");
    },
  },
};
</script>
<style>
.dialog {
  position: fixed; /* Stay in place */
  z-index: 999; /* Sit on top */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0, 0, 0); /* Fallback color */
  background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

.dialog .dialog-content {
  width: 1000px;
  position: relative;
  margin: auto;
  background-color: #ffffff;
  z-index: 1000;
  top: 50%;
  transform: translateY(-50%);
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
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.label {
  margin-right: 20px;
  color: #3385ff;
}
.label span {
  color: red;
}
.input-box {
  width: 150px;
  height: 30px;
  border: 1px solid #3385ff;
  outline: none;
  border-radius: 5px;
  padding-left: 10px;
}
</style>
