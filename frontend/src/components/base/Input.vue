<template>
  <!-- <div class="label">{{label}} <span v-if="required">*</span></div> -->
  <input class="input-box" type="text" />
</template>

<script>
export default {
  props: {
    label: String,
    required: {
      type: Boolean,
      default: true,
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
.input-box {
  width: 100%;
  height: 30px;
  border: 1px solid #ced4da;
  outline: none;
  border-radius: 0.25rem;
  padding-left: 10px;
}
</style>