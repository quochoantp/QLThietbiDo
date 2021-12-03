<template>
  <div>
    <div class="combobox" @keydown="keyEvent($event)">
      <input
        class="combobox-text"
        type="text"
        v-model="cbxInput"
        @input="autocomplete"
        @blur="onBlur($event.target)"
        ref="BaseInput"
      />
      <button @click="toggleList">
        <div class="icon-button" :class="{ reverse: isShow }">
          <i class="fas fa-chevron-down"></i>
        </div>
      </button>
      <div v-if="isShow" class="combobox-list">
        <div
          class="combobox-item"
          v-for="(cbxItem, index) in cbxItems"
          :class="{
            selected: cbxItem.isSelected,
            'focus-item': index == currentFocus,
          }"
          :key="index"
          @click="clickItem(cbxItem)"
        >
          {{ cbxItem.text }}
        </div>
      </div>
    </div>
    <div v-show="isShowMsg" class="msg" ref="Message">
      {{ message }}
    </div>
  </div>
</template>

<script>
export default {
  props: {
    items: Array,
    selectedItem: {
      type: [String, Number],
    },
    isDisable: {
      type: Boolean,
      default: false,
    },
    label: String,
  },
  data() {
    return {
      comboboxItems: [],
      isShow: false,
      isShowMsg: false,
      cbxInput: "",
      cbxItems: [],
      currentFocus: 0,
      message: "",
      invalid: false,
    };
  },

  watch: {
    selectedItem: {
      handler: function (val) {
        this.cbxInput = "";
        this.comboboxItems.forEach((item) => {
          item.isSelected = false;
          if (item.value === val) {
            this.cbxInput = item.text;
            item.isSelected = true;
          }
        });
      },
      deep: true,
    },
    items: {
      handler: function (items) {
        var me = this;
        items.forEach(function (item, index) {
          me.comboboxItems[index] = {
            value: item.value,
            text: item.text,
            isSelected: false,
          };
          if (me.comboboxItems[index].value === me.selectedItem) {
            me.comboboxItems[index].isSelected = true;
            me.cbxInput = me.comboboxItems[index].text;
          }
        });
        this.comboboxItems = [...this.comboboxItems];
        console.log(this.comboboxItems);
      },
      deep: true,
    },
  },
  created() {
    {
      var me = this;
    }
    this.items.forEach(function (item, index) {
      me.comboboxItems[index] = {
        value: item.value,
        text: item.text,
        isSelected: false,
      };

      if (me.comboboxItems[index].value === me.selectedItem) {
        me.comboboxItems[index].isSelected = true;
        me.cbxInput = me.comboboxItems[index].text;
      }
    });
  },

  methods: {
    onBlur(e) {
      if (e.value == "") {
        this.invalid = true;
        this.message = `${this.label} không được để trống`;
      } else {
        this.invalid = false;
        this.message = "";
      }
    },

    toggleList() {
      this.isShow = !this.isShow;
      //this.$refs.BaseInput.focus();

      this.cbxItems = this.comboboxItems;
      this.currentFocus = 0;
    },

    /**
     * Hàm xử lý khi chọn 1 item trong combobox
     */
    clickItem(cbxItem) {
      this.$refs.BaseInput.focus();
      this.currentFocus = 0;
      this.cbxInput = cbxItem.text;
      this.comboboxItems.forEach((item) => {
        item.isSelected = false;
        if (item.value == cbxItem.value) {
          item.isSelected = true;

          this.$emit("update-item", item);
        }
      });

      this.isShow = !this.isShow;
      console.log(this.comboboxItems);
    },

    autocomplete() {
      this.currentFocus = 0;
      var me = this;
      this.isShow = true;

      var val = this.cbxInput;
      if (val.length != 0) {
        this.cbxItems = [];
        this.comboboxItems.forEach(function (item) {
          item.isSelected = false;
          if (item.text.toUpperCase().includes(val.toUpperCase())) {
            me.cbxItems.push(item);
          }
        });
      } else {
        this.cbxItems = [];
      }
      if (this.cbxItems.length === 0) {
        this.isShow = false;
      }
    },

    /**
     * Hàm xử lý sự kiện sử dụng bàn phím keycode cua cac nut len xuong va enter
     */
    keyEvent(event) {
      if (this.cbxItems.length != 0) {
        console.log(this.cbxItems);
        if (event.keyCode == 40) {
          event.preventDefault();
          this.currentFocus++;
          if (this.currentFocus >= this.cbxItems.length) this.currentFocus = 0;
          if (this.currentFocus < 0)
            this.currentFocus = this.cbxItems.length - 1;
        } else if (event.keyCode == 38) {
          event.preventDefault();
          this.currentFocus--;
          if (this.currentFocus >= this.cbxItems.length) this.currentFocus = 0;
          if (this.currentFocus < 0)
            this.currentFocus = this.cbxItems.length - 1;
        } else if (event.keyCode == 13) {
          event.preventDefault();
          if (this.currentFocus > -1) {
            this.clickItem(this.cbxItems[this.currentFocus]);
          }
        }
      }
    },
  },
};
</script>

<style scoped>
.combobox {
  display: flex;
  position: relative;
  border: 1px solid #ced4da;
  border-radius: 4px;
  margin-right: 10px;
  width: 100%;
  height: 32px;
}

.combobox .combobox-text {
  height: 100%;
  width: calc(100% - 32px);
  padding-left: 10px;
  outline: none;
  border: none;
}
.combobox button {
  height: 100%;
  width: 32px;
  border: none;
  outline: none;
  background-color: #fff;
  cursor: pointer;
  position: relative;
}
.combobox button:hover {
  background-color: #e9ebee;
}

.combobox .icon-button {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateX(-50%) translateY(-50%) rotate(0deg);

  transition: transform 0.3s linear;
}
.combobox .reverse {
  transform: translateX(-50%) translateY(-50%) rotate(180deg);
  transition: transform 0.3s linear;
}
.combobox .combobox-list {
  position: absolute;
  top: calc(100% + 2px);
  left: -1px;
  right: -1px;
  z-index: 100;
  background-color: #fff;
  border: 1px solid #babec5;
  border-radius: 2px;
  padding: 2px 0;
  max-height: 300px;
  overflow: auto;
}

.combobox .combobox-list .combobox-item {
  padding: 8px 16px;
}
.combobox .combobox-list .combobox-item:hover {
  background-color: #ebedf0;
  color: #2ca01c;
  cursor: pointer;
}
.combobox .selected {
  background-color: #2ca01c !important;
  color: #fff !important;
  position: relative;
  align-items: center;
  line-height: 32px;
}
.msg {
  color: crimson;
}
.focus-item {
  background-color: #ebedf0;
}
</style>
