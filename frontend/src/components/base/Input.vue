<template>
  <!-- <div class="label">{{label}} <span v-if="required">*</span></div> -->
  <div>
    <input
      class="input-box"
      type="text"
      @input="updateValue($event.target.value)"
      @blur="onBlur($event.target)"
      :value="value"
      ref="BaseInput"
    />
    <div v-show="isShowMsg" class="msg" ref="Message">
      {{ message }}
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      message: "",
      isShowMsg: false,
      invalid: false,
    };
  },
  props: {
    label: String,
    required: {
      type: Boolean,
      default: true,
    },
    value: {
      type: [String, Number],
    },
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
    updateValue(e) {
      this.$emit("input", e);
    },
  },
};
</script>

<style scoped>
/* .input-label{
        display: flex;
        align-items: center;
        justify-content: flex-end;
        
    } */

.label {
  margin-right: 20px;
  color: #3385ff;
}
.label span {
  color: red;
}
.msg {
  color: crimson;
}
.input-box {
  width: 100%;
  height: 30px;
  border: 1px solid #ced4da;
  outline: none;
  border-radius: 0.25rem;
  padding-left: 10px;
}
</style>
