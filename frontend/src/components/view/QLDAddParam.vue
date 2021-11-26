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
            
            ref="input1"
          />
        </div>
      </div>
      <div class="form-row">
        <div class="input-label m-b-30">
          <div class="label">Vị trí lắp đặt <span>*</span></div>
          <!-- <input
            class="input-box"
            type="text"
            v-model="camera.position"
            @blur="onBlur($event.target)"
            ref="input2"
          /> -->
          <Input
            label="Mô tả"
            type="text"
            
            ref="input2"
          />
        </div>
        <div class="input-label m-b-30">
          <div class="label">Đơn vị tính <span>*</span></div>
          <Combobox
            :items="cameraStatus"
            :selectedItem="camera.status"
            @update-item="(item) => updateCombobox(item, 'status')"
            ref="cbx2"
            label="Đơn vị tính"
          />
        </div>
      </div>
      
    </div>

    <div class="wrap-button">
      <Button
        v-if="state === 'add'"
        class="btn"
        text="Lưu"
        icon="fas fa-plus"
        @click="addCamera"
      />
      
      <Button
        v-if="state === 'add'"
        class="btn"
        text="Làm mới"
        icon="fas fa-redo"
        @click="resetForm"
      />
      <Button
        v-else-if="state === 'edit'"
        class="btn"
        text="Cập nhật"
        icon="fas fa-edit"
        @click="updateCamera"
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
        <select v-model="perPage">
          <option value="10">10</option>
          <option value="25">25</option>
          <option value="50">50</option>
          <option value="100">100</option>
        </select>
        entries
      </div>
      <div class="grid">
        <Table
          :data="cameras"
          :fields="paramFields"
          :inputSearch="cameraFilter"
          iconOperation="fas fa-edit"
          @onclick-update="clickUpdate"
          @search="getCameraFilter"
        />
      </div>
    </div>

    <div class="paging-bar">
      <div class="paging-left">
        Showing {{ firstIndex }} to {{ lastIndex }} of
        {{ totalRecords }} entries
      </div>
      <div class="paging-right">
        <!-- <Pagination
          :total-pages="totalPages"
          :current-page="currentPage"
          @pagechanged="onPageChange"
        /> -->
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      state: "add",
      cameraStatus: [
        { value: 0, text: "Không dùng/Không có/Xóa" },
        { value: 1, text: "Có trong trạm" },
        { value: 2, text: "Đang lỗi" },
        { value: 3, text: "Đang mang đi bảo hành" },
      ],
      paramFields: [
        {
          name: "cameraName",
          text: "Search Tên yếu tố",
          typeSearch: "input",
          value: "",
        },
        {
          name: "insName",
          text: "Search mô tả",
          typeSearch: "input",
          value: "",
        },
        {
          name: "position",
          text: "Search đơn vị",
          typeSearch: "input",
          value: "",
        },
        
      ],
      cameras: [],
      camera: {},
      instrumentations: [],
      instrumentationCombobox: [],

      // pagination
      currentPage: 1,
      totalPages: 0,
      totalRecords: 0,
      perPage: 10,
      cameraFilter: {
        cameraName: "",
        insName: "",
        statusName: "",
        position: "",
      },

      indexInvalid: [],
    };
  },

  props: {
    station: Object,
  },
  watch: {
    perPage() {
      this.getCameraFilter(this.cameraFilter);
    },
  },
  computed: {
    firstIndex() {
      if (this.totalRecords > 0)
        return this.perPage * (this.currentPage - 1) + 1;
      else return 0;
    },
    lastIndex() {
      return this.perPage * this.currentPage < this.totalRecords
        ? this.perPage * this.currentPage
        : this.totalRecords;
    },
  },

  created() {
    this.createData();
  },

  methods: {
    // async createData() {
    //   const promise = await Promise.all([
    //     CameraApi.getPagingAndFilter(
    //       this.station.stationId,
    //       this.cameraFilter,
    //       1,
    //       this.perPage
    //     ),
    //     InstrumentationApi.getAll(),
    //   ]);

    //   this.cameras = await promise[0].data.records;
    //   this.totalPages = promise[0].data.totalPages;
    //   this.totalRecords = promise[0].data.totalRecords;
    //   this.instrumentations = await promise[1].data;
    //   var me = this;
    //   this.instrumentations.forEach(function (item, index) {
    //     me.instrumentationCombobox[index] = {
    //       value: item.insId,
    //       text: `${item.insCode} - ${item.insName} - ${item.manufact}`,
    //     };
    //   });
    //   this.instrumentationCombobox = [...this.instrumentationCombobox];
    // },
    closeScreen() {
      this.$emit("close-screen");
    },
    clickUpdate() {
      this.state = "edit";
      //this.camera = item;
    },
    updateCombobox(item, type) {
      if (type === "instrumentation") {
        this.camera = { ...this.camera, cameraId: item.value };
      } else if (type === "status") {
        this.camera = { ...this.camera, status: item.value };
      }
    },
    validate() {
      var me = this;
      let isValid = true;
      this.indexInvalid = [];
      Object.entries(this.$refs).forEach(function (item, index) {
        
          item[1].$refs.BaseInput.focus();
          item[1].$refs.BaseInput.blur();
          item[1].isShowMsg = false

        if (item[1].invalid == true) {
          isValid = false;
          me.indexInvalid.push(index);
        }
      });
      if (isValid == false) {
        // Hiển thị popup cảnh báo dữ liệu không hợp lệ
        //var errorMsg = Object.entries(this.$refs)[this.indexInvalid[0]][1].message;
        //this.setPopup(errorMsg, 'error', 'validateEmpty')
        Object.entries(this.$refs)[this.indexInvalid[0]][1].$refs.BaseInput.focus();

        Object.entries(this.$refs)[this.indexInvalid[0]][1].isShowMsg = true;
        console.log(Object.entries(this.$refs)[this.indexInvalid[0]])
      }
      return isValid;
    },
    // async addCamera() {
    //   var valid = this.validate();
    //   console.log(valid)
    //   if (valid == true) {
    //     try {
    //       this.camera = { ...this.camera, stationId: this.station.stationId };
    //       const res = await CameraApi.add(this.camera);
    //       console.log(res);
    //       if (res.status === 201) {
    //         alert("Thêm thành công");
    //       } 
    //       this.reloadData();
    //     } catch (error) {
    //       alert("Có lỗi xảy ra" + error);
    //     }
    //   } 
    // },
    // async updateCamera() {
    //   try {
    //     this.camera = { ...this.camera, stationId: this.station.stationId };
    //     console.log(this.camera);
    //     const res = await CameraApi.update(this.camera.id, this.camera);
    //     console.log(res);

    //     alert("Cập nhật thành công");
    //     this.reloadData();
    //   } catch (error) {
    //     alert("Có lỗi xảy ra" + error);
    //   }
    // },
    resetForm() {
      this.camera = {};
    },
    // async reloadData() {
    //   this.cameras = [];
    //   try {
    //     const res = await CameraApi.getPagingAndFilter(
    //       this.station.stationId,
    //       this.cameraFilter,
    //       1,
    //       this.perPage
    //     );
    //     this.cameras = res.data.records;
    //     this.totalPages = res.data.totalPages;
    //     this.totalRecords = res.data.totalRecords;
    //   } catch (error) {
    //     alert("Có lỗi xảy ra" + error);
    //   }
    // },
    // async getCameraFilter(inputSearch) {
    //   this.cameras = [];
    //   this.currentPage = 1;

    //   this.cameraFilter = inputSearch;
    //   const res = await CameraApi.getPagingAndFilter(
    //     this.station.stationId,
    //     inputSearch,
    //     this.currentPage,
    //     this.perPage
    //   );
    //   this.totalPages = res.data.totalPages;
    //   this.totalRecords = res.data.totalRecords;
    //   this.cameras = res.data.records;
    //   console.log(res.data);
    // },

    // async onPageChange(page) {
    //   console.log(page);
    //   this.currentPage = page;
    //   this.cameras = [];
    //   const res = await CameraApi.getPagingAndFilter(
    //     this.station.stationId,
    //     this.cameraFilter,
    //     page,
    //     this.perPage
    //   );
    //   this.cameras = res.data.records;
    // },
    
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