<template>
    <div class="input-label">
        <div class="label">{{label}} <span v-if="required">*</span></div>
        <input class="input-box" type="text">
    </div>
</template>

<script>
export default {
    props: {
        label: String,
        required: {
            type: Boolean,
            default: true
        }
    }
}
</script>

<style scoped>
    .input-label{
        display: flex;
        align-items: center;
        justify-content: flex-end;
        
    }
    .label{
        margin-right: 20px;
        color: #3385ff;
    }
    .label span {
        color: red;
    }
    .input-box{
        width: 150px;
        height: 30px;
        border: 1px solid #3385ff;
        outline: none;
        border-radius: 5px;
        padding-left: 10px;
    }

</style>